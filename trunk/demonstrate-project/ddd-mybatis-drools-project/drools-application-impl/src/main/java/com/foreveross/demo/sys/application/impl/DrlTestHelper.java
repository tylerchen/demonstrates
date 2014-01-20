/*******************************************************************************
 * Copyright (c) 2013-3-3 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
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

import org.drools.RuleBase;
import org.drools.RuleBaseFactory;
import org.drools.StatelessSession;
import org.drools.compiler.PackageBuilder;
import org.drools.compiler.PackageBuilderErrors;
import org.drools.rule.Package;

import com.foreveross.demo.sys.domain.Rules;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2013-3-3
 */
@SuppressWarnings("unchecked")
public class DrlTestHelper {

	private DrlTestHelper() {
	}

	public static void testRule(String ruleJson) {
		DrlParserHelper.parse(ruleJson);
	}

	public static Object testExecuteRules(String ruleJson, List data) {
		try {
			Rules rules = DrlParserHelper.parse(ruleJson);
			RuleBase ruleBase = RuleBaseFactory.newRuleBase();
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
			StatelessSession session = ruleBase.newStatelessSession();
			session.setGlobal("globalMap", new LinkedHashMap());
			return session.executeWithResults(data).getGlobal("globalMap");
		} catch (Exception e) {
			throw new RuntimeException("Test RuleBase error!", e);
		}
	}

}
