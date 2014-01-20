/*******************************************************************************
 * Copyright (c) 2013-2-28 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.foreveross.demo.sys.application.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.drools.lang.dsl.DSLMapParser.mapping_file_return;

import com.foreveross.demo.sys.domain.FieldMapper;
import com.foreveross.demo.sys.domain.Rules;
import com.foreveross.util.GsonHelper;
import com.foreveross.util.StringHelper;

/**
 * <pre>
 * {
 *     ruleSetKey: 'a-b-c-d',
 *     ruleSet:[
 *         {// start of rule structure
 *             ruleName: 'test',
 *             salience: '1',
 *             conditions:[
 *                {logic:''   , lhs:'a', op:'==', rhs:'1', type:'string'},
 *                {logic:'and', lhs:'b', op:'==', rhs:'2', type:'string'},
 *                {logic:'and', lhs:'c', op:'==', rhs:'3', type:'string'},
 *                {logic:'and', conditions:[
 *                     {logic:''   , lhs:'d', op:'==', rhs:'4', type:'string'},
 *                     {logic:'and', lhs:'e', op:'==', rhs:'5', type:'string'},
 *                     {logic:'and', conditions:[
 *                          {logic:''   , lhs:'f', op:'==', rhs:'6', type:'string'},
 *                          {logic:'and', lhs:'g', op:'==', rhs:'7', type:'string'}
 *                     ]}
 *                ]}
 *             ],// end of conditions
 *             result: {
 *                 a:1,
 *                 b:2
 *             }
 *         },// end of rule structure
 *         {// start of rule structure
 *             ruleName: 'test2',
 *             salience: '2',
 *             conditions:[
 *                {logic:''   , lhs:'a', op:'==', rhs:'1', type:'string'},
 *                {logic:'and', lhs:'b', op:'==', rhs:'2', type:'string'},
 *                {logic:'and', lhs:'c', op:'==', rhs:'3', type:'string'},
 *                {logic:'and', conditions:[
 *                     {logic:''   , lhs:'d', op:'==', rhs:'4', type:'string'},
 *                     {logic:'and', lhs:'e', op:'==', rhs:'5', type:'string'},
 *                     {logic:'and', conditions:[
 *                          {logic:''   , lhs:'f', op:'==', rhs:'6', type:'string'},
 *                          {logic:'and', lhs:'g', op:'==', rhs:'7', type:'string'}
 *                     ]}
 *                ]}
 *             ],// end of conditions
 *             result: {
 *                 a:1,
 *                 b:2
 *             }
 *         }// end of rule structure
 *     ],// end of ruleSet
 * }
 * </pre>
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2013-2-28
 */
@SuppressWarnings( { "unused", "unchecked" })
public class DrlParserHelper {

	private DrlParserHelper() {
	}

	public static Rules parse(String ruleJson) {
		String pkg;
		String global = "global java.util.Map globalMap;";
		String activation_group;
		Set<String> importSet = new HashSet<String>();
		{
			importSet.add("java.util.*");
			importSet.add("com.foreveross.pe.util.*");
		}
		//
		Rules rules = new Rules();
		//
		Map map = GsonHelper.toJsonMap(ruleJson);
		{// process uniqueKey.
			String ruleSetKey = GsonHelper.accessMap(map, "ruleSetKey");
			pkg = getPackage(ruleSetKey);
			rules.setRuleSetKey(ruleSetKey);
			activation_group = "activation-group \"" + ruleSetKey + "\"";
		}
		StringBuilder ruleset = new StringBuilder();
		{// process when.
			List<Map> ruleSet = GsonHelper.accessMap(map, "ruleSet");
			for (Map rule : ruleSet) {
				StringBuilder ruleName = new StringBuilder(256);
				StringBuilder attributes = new StringBuilder(256);
				StringBuilder when = new StringBuilder(1024);
				StringBuilder result = new StringBuilder(256);
				{
					ruleName.append(rules.getRuleSetKey()).append(" ").append(
							(String) GsonHelper.accessMap(rule, "ruleName"));
					{
						String temp = GsonHelper.toJsonString(GsonHelper
								.accessMap(rule, "result"));
						result.append("    ").append("    ").append(
								"globalMap.put(").append(
								processValue(ruleName.toString(), "string"))
								.append(",").append(
										processValue(temp, "string")).append(
										"); \r\n");
						result.append("    ").append("    ").append(
								"System.out.println(\"").append(
								temp.replaceAll("\"", "'")).append("\"); \r\n");
					}
					attributes.append("    ").append("salience ").append(
							(String) GsonHelper.accessMap(rule, "salience"))
							.append(" \r\n");
					attributes.append("    ").append(activation_group);
				}
				List<Map> rs = GsonHelper.accessMap(rule, "conditions");
				//
				String[] names = // 
				new String[] { "logic", "lhs", "op", "rhs", "type" };
				//
				List list = new ArrayList();
				for (Map r : rs) {
					processConditions(list, r, names);
				}
				processWhen(list, new HashMap<String, String>(),
						getfieldMappedToClass(), importSet, when);
				String dsl = formatRules(ruleName.toString(), attributes
						.toString(), when.toString(), result.toString());
				ruleset.append(dsl).append("\r\n");
			}
		}

		StringBuilder imports = new StringBuilder(1024);
		{
			for (String imp : importSet) {
				imports.append("import ").append(imp).append("; \r\n");
			}
		}

		{
			String drl = formatDrl(pkg, imports.toString(), global, ruleset
					.toString());
			rules.setRule(drl);
			System.out.println(drl);
		}

		return rules;
	}

	private static void processConditions(List results, Map r, String[] names) {
		List<Map> conditions = GsonHelper.accessMap(r, "conditions");
		if (!(conditions == null || conditions.size() < 1)) {
			String logic = GsonHelper.accessMap(r, "logic");
			results.add(new String[] { logic, null, null, null, null });
			List conditionGroup = new ArrayList();
			for (Map m : conditions) {
				processConditions(conditionGroup, m, names);
			}
			results.add(conditionGroup);
		} else {
			String[] values = new String[5];
			for (int i = 0; i < names.length; i++) {
				values[i] = GsonHelper.accessMap(r, names[i]);
			}
			results.add(values);
		}
	}

	private static String getCanonicalClassName(String className) {
		int lastIndexOf = className.lastIndexOf('.');
		if (lastIndexOf > -1) {
			return className.substring(lastIndexOf + 1);
		}
		return className;
	}

	private static String getVariableName(Map<String, String> variables,
			Map<String, FieldMapper> fieldMappedToClass, Set<String> importSet,
			String className) {
		String variableName = variables.get(className);
		if (variableName != null) {
			return variableName;
		}
		int lastIndexOf = className.lastIndexOf('.');
		if (lastIndexOf > -1) {
			variableName = "$"
					+ className.substring(lastIndexOf + 1).toLowerCase();
		} else {
			variableName = "$" + className.toLowerCase();
		}
		variables.put(className, variableName);
		importSet.add(className);
		return variableName;
	}

	private static String getObjectField(
			Map<String, FieldMapper> fieldMappedToClass, String fieldName) {
		FieldMapper fieldMapper = fieldMappedToClass.get(fieldName);
		if (fieldMapper == null) {
			return fieldName;
		}
		return fieldMapper.getObjectField();
	}

	private static void processWhen(List list, Map<String, String> variables,
			Map<String, FieldMapper> fieldMappedToClass, Set<String> importSet,
			StringBuilder sb) {
		for (Object o : list) {
			if (o instanceof String[]) {
				String[] strs = (String[]) o;
				StringBuilder temp = new StringBuilder();
				if (strs[1] == null) {
					temp.append("        ").append(strs[0]).append(" \r\n");
				} else {
					String className = fieldMappedToClass.get(strs[1])
							.getObjectClass();
					boolean containsClassName = variables
							.containsKey(className);
					String variableName = getVariableName(variables,
							fieldMappedToClass, importSet, className);
					temp.append("        ");
					if (variables.size() > 0 && strs[0] != null
							&& strs[0].length() > 0) {
						temp.append(strs[0]).append(" ");
					}
					if (!containsClassName) {
						temp.append(variableName).append(" : ");
					}
					temp.append(getCanonicalClassName(className)).append("(");
					if (containsClassName) {
						temp.append("this == ").append(variableName).append(
								" && ");
					}
					temp.append(getObjectField(fieldMappedToClass, strs[1]))
							.append(strs[2]).append(
									processValue(strs[3], strs[4]));
					temp.append(") \r\n");
				}
				sb.append(temp);
			} else if (o instanceof List) {
				List conditions = (List) o;
				StringBuilder temp = new StringBuilder();
				processWhen(conditions, variables, fieldMappedToClass,
						importSet, temp);
				sb.append("        ").append("(\r\n").append(temp).append(
						"        ").append(") \r\n");
			}
		}
	}

	private static String processValue(String value, String type) {
		if ("string".equalsIgnoreCase(type) || "date".equalsIgnoreCase(type)) {
			return "\"" + value.replaceAll("\"", "\\\\\"") + "\"";
		}
		return value;
	}

	private static Map<String, FieldMapper> getfieldMappedToClass() {
		return FieldMapper.findAllToMap();
	}

	private static String formatDrl(String pkg, String imports, String global,
			String rules) {
		String drlTemplate = //
		""//
				+ "package {pkg} \r\n"//
				+ "{imports} \r\n"//
				+ "{global} \r\n"//
				+ "{rules} \r\n";
		return StringHelper.replaceBlock(drlTemplate, new String[] { pkg,
				imports, global, rules }, null);
	}

	private static String formatRules(String ruleName, String attributes,
			String conditions, String actions) {
		String ruleTemplate = //
		"" //
				+ "rule \"{ruleName}\" \r\n"//
				+ "{attributes} \r\n"//
				+ "    when \r\n"//
				+ "{conditions} \r\n"//
				+ "    then \r\n"//
				+ "{actions} \r\n"//
				+ "end \r\n";
		return StringHelper.replaceBlock(ruleTemplate, new String[] { ruleName,
				attributes, conditions, actions }, null);
	}

	public static String getPackage(String uniqueKey) {
		// remove the character not in [A-Za-z0-9].
		return "com.foreveross._" + uniqueKey.replaceAll("[^A-Za-z0-9]", "_");
	}
}
