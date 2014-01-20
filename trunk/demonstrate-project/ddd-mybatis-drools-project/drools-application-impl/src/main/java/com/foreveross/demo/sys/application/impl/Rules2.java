/*******************************************************************************
 * Copyright (c) 2013-5-9 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.foreveross.demo.sys.application.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.foreveross.demo.sys.domain.FieldMapper;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2013-5-9
 */
public class Rules2 {

	//=================================Context===================================
	private Context context = new Context();

	class Context {
		final String TAB_WIDTH = "    ";
		Context parent;
	}

	void getTabWidth(Context ctx, StringBuffer tabWidth) {
		if (ctx.parent == null) {
			tabWidth.append(ctx.TAB_WIDTH);
		} else {
			tabWidth.append(ctx.parent).append(ctx.TAB_WIDTH);
		}
	}

	//=================================Package===================================
	private String ruleSetKey;
	private String packageName;

	public String setRuleSetKey(String ruleSetKey) {
		packageName = genPackageName(ruleSetKey);
		return this.ruleSetKey = ruleSetKey;
	}

	public static String genPackageName(String ruleSetKey) {
		// replace none "A-Za-z0-9" character to "_".
		return "com.foreveross.rules._"
				+ ruleSetKey.replaceAll("[^A-Za-z0-9]", "_");
	}

	void packageToString(StringBuffer sb, Context context) {
		sb.append("package ").append(packageName).append("\r\n");
	}

	//=================================Imports===================================
	private List<String> imports = new ArrayList<String>();

	public void addImports(String... impts) {
		for (String impt : impts) {
			imports.add(impt);
		}
	}

	void importsToString(StringBuffer sb, Context context) {
		for (String imp : imports) {
			sb.append("import ").append(imp).append(";\r\n");
		}
	}

	//=================================Globals===================================
	private List<String> globals = new ArrayList<String>();

	public void addGlobals(String... globalArr) {
		for (String global : globalArr) {
			globals.add(global);
		}
	}

	void globalsToString(StringBuffer sb, Context context) {
		for (String global : globals) {
			sb.append("global ").append(global).append(";\r\n");
		}
	}

	//=================================Functions===================================
	private List<String> functions = new ArrayList<String>();

	public void addFunctions(String... functionArr) {
		for (String function : functions) {
			functions.add(function);
		}
	}

	void functionsToString(StringBuffer sb, Context context) {
		for (String function : functions) {
			sb.append(function).append("\r\n");
		}
	}

	//=================================Declares===================================
	private List<String> declares = new ArrayList<String>();

	public void addDeclares(String... declareArr) {
		for (String declare : declareArr) {
			declares.add(declare);
		}
	}

	void declaresToString(StringBuffer sb, Context context) {
		for (String declare : declares) {
			sb.append(declare).append("\r\n");
		}
	}

	//
	private Map<String, FieldMapper> fieldMappedToClass = new HashMap<String, FieldMapper>();

	FieldMapper getFieldMapper(String fieldName) {
		return fieldMappedToClass.get(fieldName);
	}

	//=================================Rule===================================
	private List<Rule> rules = new ArrayList<Rule>();

	class Rule {
		String ruleName;
		List<String> attributes = new ArrayList<String>();
		Expression conditions = new Expression();
		Expression actions = new Expression();
		Map<String, String> variables = new LinkedHashMap<String, String>();
	}

	String getVariableName(Rule rule, String className) {
		String variableName = rule.variables.get(className);
		if (variableName != null) {
			return variableName;
		}
		int lastIndexOf = className.lastIndexOf('.');
		if (lastIndexOf > -1) {
			variableName = "$$" + className.substring(lastIndexOf + 1);
		} else {
			variableName = "$$" + className;
		}
		rule.variables.put(className, variableName);
		return variableName;
	}

	//=================================Rule===================================
	class Expression {
		List<Expression> expressionList;
		Evaluable logic;
		Evaluable operator;
		Object[] parameters;
	}

	Expression addExpression(Evaluable logic, Evaluable operator,
			Object[] parameters) {
		Expression exp = new Expression();
		exp.logic = logic;
		exp.operator = operator;
		exp.parameters = parameters;
		return exp;
	}

	void expressionToString(Context context, StringBuffer sb, Expression exp) {
		if (exp.operator != null) {
			sb.append(evaluate(exp.logic));
			if(isFunction(exp.operator)){
			}
		}
	}

	//=================================Rule===================================
	class Evaluable {
		String name;
	}

	String evaluate(Evaluable eval) {
		if (eval == null) {
			return "";
		}
		FieldMapper fieldMapper = getFieldMapper(eval.name);
		if (fieldMapper == null) {
			return eval.name;
		}
		return fieldMapper.getObjectField();
	}
	
	boolean isFunction(Evaluable eval) {
		return eval.name.indexOf('(')>0;
	}

	String getClassName(Evaluable eval) {
		FieldMapper fieldMapper = getFieldMapper(eval.name);
		if (fieldMapper == null) {
			return "";
		}
		return fieldMapper.getObjectClass();
	}

	boolean matchClassName(Evaluable left, Evaluable right) {
		return getClassName(left).equals(getClassName(right));
	}

	//=================================Printable===================================

	class Printable {
		Context context;
		String[] strs;
	}

	Printable print(Context context, String... strs) {
		Printable printable = new Printable();
		printable.strs = strs == null ? new String[0] : strs;
		printable.context = context;
		return printable;
	}

	void printableToString(Printable printable, StringBuffer sb) {
		getTabWidth(printable.context, sb);
		for (String str : printable.strs) {
			sb.append(str);
		}
	}

	//	class Rule1 {
	//
	//		String ruleName;
	//		Attributes attributes = new Attributes();
	//		Conditions conditions = new Conditions();
	//		Actions actions = new Actions();
	//		Variables variables = new Variables();
	//
	//		void setRuleName(String ruleName) {
	//			this.ruleName = ruleName;
	//		}
	//
	//		public String getRuleName() {
	//			return ruleName;
	//		}
	//
	//		class Attributes {
	//			List<String> attributes = new ArrayList<String>();
	//
	//			void addAttributes(String attribute) {
	//				attributes.add(attribute);
	//			}
	//
	//			public List<String> getAttributes() {
	//				return attributes;
	//			}
	//
	//			public void setAttributes(List<String> attributes) {
	//				this.attributes = attributes;
	//			}
	//
	//			public String toString() {
	//				StringBuffer sb = new StringBuffer(128);
	//				for (String attribute : attributes) {
	//					sb.append(attribute).append("\r\n");
	//				}
	//				return sb.toString().trim();
	//			}
	//		}
	//
	//		class Variables {
	//			Map<String, String> variables = new LinkedHashMap<String, String>();
	//
	//			String getVariableName(String className) {
	//				String variableName = variables.get(className);
	//				if (variableName != null) {
	//					return variableName;
	//				}
	//				int lastIndexOf = className.lastIndexOf('.');
	//				if (lastIndexOf > -1) {
	//					variableName = "$$" + className.substring(lastIndexOf + 1);
	//				} else {
	//					variableName = "$$" + className;
	//				}
	//				variables.put(className, variableName);
	//				return variableName;
	//			}
	//		}
	//
	//		class Conditions {
	//
	//			List<ConditionGroup> conditionGroups = new ArrayList<ConditionGroup>();
	//
	//			void addCondition(Condition condition) {
	//				if (conditionGroups.isEmpty()) {
	//					ConditionGroup conditionGroup = createConditionGroup();
	//					conditionGroup.add(condition);
	//					conditionGroups.add(conditionGroup);
	//					return;
	//				}
	//				{
	//					ConditionGroup conditionGroup = conditionGroups
	//							.get(conditionGroups.size() - 1);
	//					if (!conditionGroup.add(condition)) {
	//						conditionGroup = createConditionGroup();
	//						conditionGroup.add(condition);
	//						conditionGroups.add(conditionGroup);
	//						return;
	//					}
	//				}
	//			}
	//
	//			Condition createCondition(String logic, Expression left,
	//					String operation, Expression right) {
	//				Condition condition = createConditionGroup().new Condition();
	//				condition.logic = createEvaluable(logic);
	//				condition.left = left;
	//				condition.operation = createEvaluable(operation);
	//				condition.right = right;
	//				return condition;
	//			}
	//
	//			ConditionGroup createConditionGroup() {
	//				return new ConditionGroup();
	//			}
	//
	//			class ConditionGroup {
	//				List<Condition> conditions = new ArrayList<Condition>();
	//
	//				boolean add(Condition condition) {
	//					if (conditions.isEmpty()) {
	//						conditions.add(condition);
	//						return true;
	//					}
	//					{
	//						Condition lastCondition = conditions.get(conditions
	//								.size() - 1);
	//						if (lastCondition.left.name
	//								.matchClassName(condition.left.name)) {
	//							conditions.add(condition);
	//							return true;
	//						}
	//					}
	//					return false;
	//				}
	//
	//				class Condition {
	//					Evaluable logic;
	//					Expression left;
	//					Evaluable operation;
	//					Expression right;
	//
	//					public String toString() {
	//						return new StringBuffer().append(logic).append(left)
	//								.append(' ').append(operation).append(' ')
	//								.append(right).toString();
	//					}
	//				}
	//
	//				public String toString() {
	//					// ClassName(a=b,c=d)
	//					if (conditions.isEmpty()) {
	//						return "";
	//					}
	//					Condition condition = conditions.get(0);
	//					String className = condition.left.name.getClassName();
	//					int lastIndexOf = className.lastIndexOf('.');
	//					if (lastIndexOf > -1) {
	//						className = className.substring(lastIndexOf + 1,
	//								className.length());
	//					}
	//					StringBuffer sb = new StringBuffer();
	//					sb.append(className).append('(');
	//					for (Condition cdt : conditions) {
	//						sb.append(cdt);
	//					}
	//					sb.append(')');
	//					return sb.toString();
	//				}
	//
	//			}
	//		}
	//
	//		class Actions {
	//			List<Action> actions = new ArrayList<Action>();
	//
	//			Action createAction(Expression left, String operation,
	//					Expression right) {
	//				Action action = new Action();
	//				action.left = left;
	//				action.operation = operation;
	//				action.right = right;
	//				return action;
	//			}
	//
	//			class Action {
	//				Expression left;
	//				String operation;
	//				Expression right;
	//
	//				public String toString() {
	//					return new StringBuffer().append(left).append(' ').append(
	//							operation).append(' ').append(right).toString();
	//				}
	//			}
	//		}
	//
	//		Expression constExpression(String value, String valueType) {
	//			Expression exp = new Expression();
	//			exp.name = createEvaluable(value);
	//			exp.type = valueType;
	//			return exp;
	//		}
	//
	//		Expression constExpression(Expression[] expressions) {
	//			Expression exp = new Expression();
	//			exp.type = Expression.TYPE_CONSTANT;
	//			exp.parameters = expressions;
	//			return exp;
	//		}
	//
	//		Expression functionExpression(String name, Expression[] parameters) {
	//			Expression exp = new Expression();
	//			exp.name = createEvaluable(name);
	//			exp.type = Expression.TYPE_FUNCTION;
	//			exp.parameters = parameters == null ? new Expression[0]
	//					: parameters;
	//			return exp;
	//		}
	//
	//		Expression variableExpression(String name) {
	//			Expression exp = new Expression();
	//			exp.name = createEvaluable(name);
	//			exp.type = Expression.TYPE_VARIABLE;
	//			exp.parameters = null;
	//			return exp;
	//		}
	//
	//		class Expression {
	//			static final String TYPE_FUNCTION = "_function_";
	//			static final String TYPE_VARIABLE = "_variable_";
	//			static final String TYPE_CONSTANT = "_constant_";
	//			Evaluable name;
	//			String type;
	//			Expression[] parameters;
	//
	//			boolean isFunction() {
	//				return TYPE_FUNCTION.equals(type);
	//			}
	//
	//			public String toString() {
	//				if (TYPE_VARIABLE.equals(type)) {
	//					return name.toString();
	//				} else if (TYPE_FUNCTION.equals(type)) {
	//					StringBuffer sb = new StringBuffer();
	//					sb.append(name).append('(');
	//					for (Expression parameter : parameters) {
	//						sb.append(parameter).append(',');
	//					}
	//					if (parameters.length > 0) {
	//						sb.setLength(sb.length() - 1);
	//					}
	//					sb.append(')');
	//					return sb.toString();
	//				} else if ("string".equalsIgnoreCase(type)
	//						|| "date".equalsIgnoreCase(type)) {
	//					return "\"" + name.toString().replaceAll("\"", "\\\\\"")
	//							+ "\"";
	//				} else if (type != null) {
	//					return name.toString();
	//				}
	//				return "";
	//			}
	//		}
	//	}

}
