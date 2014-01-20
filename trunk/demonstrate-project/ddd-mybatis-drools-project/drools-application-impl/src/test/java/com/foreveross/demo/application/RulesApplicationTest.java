/*******************************************************************************
 * Copyright (c) 2013-3-1 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.foreveross.demo.application;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.dayatang.domain.InstanceFactory;
import com.foreveross.demo.sys.application.RulesApplication;
import com.foreveross.demo.sys.application.impl.RuleBaseCache;
import com.foreveross.test.AbstractIntegratedTestCase;
import com.foreveross.util.GsonHelper;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2013-3-1
 */
public class RulesApplicationTest extends AbstractIntegratedTestCase {

	@Override
	protected String[] getDataSetFilePaths() {
		return new String[] { "dataset/FieldMapper.xml" };
	}

	@Test
	public void test_rules() {
		String ruleJson = ""
				+ "{\r\n"//
				+ "    ruleSetKey: 'a-b-c-d',\r\n"//
				+ "    ruleSet:[\r\n"//
				+ "        {\r\n"//
				+ "            ruleName: 'test',\r\n"//
				+ "            salience: '1',\r\n"//
				+ "            conditions:[\r\n"//
				+ "               {logic:''   , lhs:'航线', op:'==', rhs:'1', type:'string'},\r\n"//
				+ "               {logic:'and', lhs:'航班', op:'==', rhs:'2', type:'string'},\r\n"//
				+ "               {logic:'and', lhs:'航油', op:'==', rhs:'3', type:'string'},\r\n"//
				+ "               {logic:'and', conditions:[\r\n"//
				+ "                    {logic:''   , lhs:'起飞站', op:'==', rhs:'4', type:'string'},\r\n"//
				+ "                    {logic:'and', lhs:'落地站', op:'==', rhs:'5', type:'string'},\r\n"//
				+ "                    {logic:'and', conditions:[\r\n"//
				+ "                         {logic:''   , lhs:'国内航线', op:'>', rhs:'2011-01-06', type:'date'},\r\n"//
				+ "                         {logic:'and', lhs:'国际航线', op:'==', rhs:'7', type:'int'}\r\n"//
				+ "                    ]}\r\n"//
				+ "               ]}\r\n"//
				+ "            ],\r\n"//
				+ "            result: {\r\n"//
				+ "                    a:1,\r\n"//
				+ "                    b:2\r\n"//
				+ "            }\r\n"//
				+ "        },\r\n"//
				+ "        {\r\n"//
				+ "            ruleName: 'test2',\r\n"//
				+ "            salience: '2',\r\n"//
				+ "            conditions:[\r\n"//
				+ "               {logic:''   , lhs:'航线', op:'==', rhs:'1', type:'string'},\r\n"//
				+ "               {logic:'and', lhs:'航班', op:'==', rhs:'2', type:'string'},\r\n"//
				+ "               {logic:'and', lhs:'航油', op:'==', rhs:'3', type:'string'},\r\n"//
				+ "               {logic:'and', conditions:[\r\n"//
				+ "                    {logic:''   , lhs:'起飞站', op:'==', rhs:'4', type:'string'},\r\n"//
				+ "                    {logic:'and', lhs:'落地站', op:'==', rhs:'5', type:'string'},\r\n"//
				+ "                    {logic:'and', conditions:[\r\n"//
				+ "                         {logic:''   , lhs:'国内航线', op:'>', rhs:'2011-01-06', type:'date'},\r\n"//
				+ "                         {logic:'and', lhs:'国际航线', op:'==', rhs:'7', type:'int'}\r\n"//
				+ "                    ]}\r\n"//
				+ "               ]}\r\n"//
				+ "            ],\r\n"//
				+ "            result: {\r\n"//
				+ "                    a:2,\r\n"//
				+ "                    b:3\r\n"//
				+ "            }\r\n"//
				+ "        }\r\n"//
				+ "    ],\r\n"//
				+ "    result: {\r\n"//
				+ "        a:1,\r\n"//
				+ "        b:2\r\n"//
				+ "    }\r\n"//
				+ "}\r\n"//
		;
		RulesApplication rulesApplication = InstanceFactory
				.getInstance(RulesApplication.class);
		rulesApplication.saveRules(ruleJson);
		RuleBaseCache rb = InstanceFactory.getInstance(RuleBaseCache.class);
		try {
			TimeUnit.SECONDS.sleep(10);
		} catch (Exception e) {
		}
		List data = new ArrayList();
		{
			data.add(new Test1("1", "2", "3"));
			data.add(new Test2("4", "5"));
			data.add(new Test3(new Date(), "7"));
		}
		Object executeRules = rb.executeRules(data);
		System.out.println(executeRules);
		Map map = (Map) executeRules;
		if (map.size() > 0) {
			System.out.println(GsonHelper.toJsonMap(map.values().iterator()
					.next().toString()));
		}
		try {
			TimeUnit.SECONDS.sleep(30);
		} catch (Exception e) {
		}
	}
}
