/*******************************************************************************
 * Copyright (c) 2012-11-21 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package org.iff.demo.application.impl;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;
import javax.rules.RuleRuntime;
import javax.rules.RuleServiceProvider;
import javax.rules.RuleServiceProviderManager;
import javax.rules.StatelessRuleSession;
import javax.rules.admin.LocalRuleExecutionSetProvider;
import javax.rules.admin.RuleAdministrator;
import javax.rules.admin.RuleExecutionSet;

import org.springframework.context.annotation.Scope;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2012-11-21
 */
@Named
@Scope(value = "prototype")
public class DemoDroolsApplicationImpl {
	public DemoDroolsApplicationImpl() {
		System.out.println(System.currentTimeMillis());
	}

	public void customSession() throws Exception {

		// RuleServiceProviderImpl is registered to "http://drools.org/"

		// via a static initialization block

		Class.forName("org.drools.jsr94.rules.RuleServiceProviderImpl");

		// Get the rule service provider from the provider manager.

		RuleServiceProvider ruleServiceProvider =

		RuleServiceProviderManager.getRuleServiceProvider("http://drools.org/");

		// Get the RuleAdministration

		RuleAdministrator ruleAdministrator = ruleServiceProvider
				.getRuleAdministrator();

		LocalRuleExecutionSetProvider ruleExecutionSetProvider =

		ruleAdministrator.getLocalRuleExecutionSetProvider(null);

		// Create a Reader for the drl

		Reader drlReader = new InputStreamReader(getClass()
				.getResourceAsStream("/drools/test.dsl"));

		// Create the RuleExecutionSet for the drl

		RuleExecutionSet ruleExecutionSet =

		ruleExecutionSetProvider.createRuleExecutionSet(drlReader, null);

		// Register the RuleExecutionSet with the RuleAdministrator

		String uri = ruleExecutionSet.getName();

		ruleAdministrator.registerRuleExecutionSet(uri, ruleExecutionSet, null);
		
		RuleRuntime ruleRuntime = ruleServiceProvider.getRuleRuntime();

		StatelessRuleSession session = (StatelessRuleSession)

		ruleRuntime.createRuleSession(uri,

		new HashMap(),

		RuleRuntime.STATELESS_SESSION_TYPE);

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		{
			{
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("tail_number", "B2080");
				list.add(map);
			}
			{
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("tail_number", "B6909");
				list.add(map);
			}
			{
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("tail_number", "B2081");
				list.add(map);
			}
		}

		List results = new ArrayList();

		results = session.executeRules(list);
	}
}
