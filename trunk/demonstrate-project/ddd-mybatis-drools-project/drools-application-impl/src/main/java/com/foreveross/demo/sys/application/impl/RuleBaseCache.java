/*******************************************************************************
 * Copyright (c) 2013-3-1 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.foreveross.demo.sys.application.impl;

import java.io.StringReader;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;

import org.drools.RuleBase;
import org.drools.RuleBaseFactory;
import org.drools.StatelessSession;
import org.drools.compiler.PackageBuilder;
import org.drools.compiler.PackageBuilderErrors;
import org.drools.rule.Package;

import com.foreveross.demo.sys.domain.Rules;
import com.foreveross.util.mybatis.plugin.Page;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2013-3-1
 */
@Named("ruleBaseCache")
public class RuleBaseCache {

	private RuleBase ruleBase;

	private static Timer updateRulesTimer;
	private long updateMark = 0;
	private long updateTimes = 0;

	public RuleBaseCache() {
		init();
	}

	private void init() {
		try {
			System.setProperty("drools.dateformat", "yyyy-MM-dd");
			ruleBase = RuleBaseFactory.newRuleBase();
		} catch (Exception e) {
			throw new RuntimeException("Init RuleBase error!", e);
		}
		try {
			new Timer().schedule(new TimerTask() {
				public void run() {
					int counter = 10;
					while (true) {
						try {
							TimeUnit.SECONDS.sleep(1);
							initRuleBaseFromDB();
						} catch (Exception e) {
							if (counter-- < 0) {
								throw new RuntimeException(
										"Init RuleBase from db error!", e);
							}
							continue;
						}
						break;
					}
					if (updateRulesTimer == null) {
						updateRulesTimer = new Timer();
					}
					updateRulesTimer.schedule(new TimerTask() {
						public void run() {
							updateFromDB();
						}
					}, 5 * 1000, 5 * 1000);
				}
			}, 2 * 1000);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Init Timer error!", e);
		}
	}

	public void addRules(Rules rules) {
		ruleBase.lock();
		try {
			{
				String pkg = DrlParserHelper.getPackage(rules.getRuleSetKey());
				if (ruleBase.getPackage(pkg) != null) {// you can improve this process by caching processed.
					ruleBase.removePackage(pkg);
				}
			}
			PackageBuilder builder = new PackageBuilder();
			builder.addPackageFromDrl(new StringReader(rules.getRule()));
			PackageBuilderErrors errors = builder.getErrors();
			if (errors != null && errors.getErrors() != null
					&& errors.getErrors().length > 0) {
				throw new RuntimeException("Rule compile error: "
						+ Arrays.toString(errors.getErrors()));
			}
			Package pkg = builder.getPackage();
			ruleBase.addPackage(pkg);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			ruleBase.unlock();
		}
	}

	public void removeRules(Rules rules) {
		String pkg = DrlParserHelper.getPackage(rules.getRuleSetKey());
		if (ruleBase.getPackage(pkg) == null) {
			return;
		}
		ruleBase.lock();
		try {
			ruleBase.removePackage(pkg);
		} finally {
			ruleBase.unlock();
		}
	}

	@SuppressWarnings("unchecked")
	public Object executeRules(List data) {
		StatelessSession session = ruleBase.newStatelessSession();
		Map globalMap = new LinkedHashMap();
		session.setGlobal("globalMap", globalMap);
		session.execute(data);
		return globalMap;
	}

	protected void initRuleBaseFromDB() {
		System.out.println("INFO: start init RuleBase from DB, time:"
				+ System.currentTimeMillis());
		int size = 1000;
		Page page = new Page(size);
		List<Rules> rows = Rules.pageFindRules(page, null, null).getRows();
		while (rows != null && !rows.isEmpty()) {
			for (Rules r : rows) {
				addRules(r);
				if (r.getUpdateMark() > updateMark) {
					updateMark = r.getUpdateMark();
				}
			}
			if (rows.size() >= size) {
				rows.clear();
				page.setCurrentPage(page.getCurrentPage() + 1);
				rows = Rules.pageFindRules(page, null, null).getRows();
			} else {
				rows.clear();
				break;
			}
		}
		System.out.println("INFO: end init RuleBase from DB, time:"
				+ System.currentTimeMillis());
	}

	public synchronized void updateFromDB() {
		System.out.println("INFO: start update RuleBase from DB, time:"
				+ System.currentTimeMillis());
		// every updateMark will update twice.
		long tempUpdateMark = 0;
		List<Rules> list = Rules.findRulesByUpdateMark(updateMark);
		if (!(list == null || list.isEmpty())) {
			for (Rules r : list) {
				addRules(r);
				if (r.getUpdateMark() > tempUpdateMark) {
					tempUpdateMark = r.getUpdateMark();
				}
			}
			if (updateTimes == 1) {
				if (updateMark == tempUpdateMark) {
					updateMark = updateMark + 1;
				} else {
					updateMark = tempUpdateMark;
				}
			}
			if (updateTimes > 0) {
				updateTimes = 0;
			} else {
				updateTimes = 1;
			}
		}
		System.out.println("INFO: end update RuleBase from DB, time:"
				+ System.currentTimeMillis());
	}
}
