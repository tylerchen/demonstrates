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

import com.foreveross.demo.sys.application.impl.Rules.Rule.Conditions.ConditionGroup.Condition;
import com.foreveross.demo.sys.domain.FieldMapper;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2013-5-9
 */
public class Rules {

	private Package pkg = new Package();
	private Imports imports = new Imports();
	private Globals globals = new Globals();
	private Functions functions = new Functions();
	private Declares declares = new Declares();
	private List<Rule> rules = new ArrayList<Rule>();
	//
	private Map<String, FieldMapper> fieldMappedToClass = new HashMap<String, FieldMapper>();

	public static void main(String[] args) {
		Rules rs = new Rules();
		rs.setRuleSetKey("a-a-a-a-a-a");
		rs.addImports("com.foreveross.*", "java.util.*");
		rs.addGlobals("Map globalMap = new HashMap()");
		Rule r = rs.new Rule();
		r.setRuleName("test");
		r.attributes.addAttributes("loop: true");
		//r.conditions.addCondition(null)
	}

	FieldMapper getFieldMapper(String fieldName) {
		return fieldMappedToClass.get(fieldName);
	}

	public static String genPackageName(String ruleSetKey) {
		// replace none "A-Za-z0-9" character to "_".
		return "com.foreveross.rules._"
				+ ruleSetKey.replaceAll("[^A-Za-z0-9]", "_");
	}

	public String setRuleSetKey(String ruleSetKey) {
		return pkg.setRuleSetKey(ruleSetKey);
	}

	public void addImports(String... impts) {
		for (String impt : impts) {
			imports.addImports(impt);
		}
	}

	public void addGlobals(String global) {
		globals.addGlobals(global);
	}

	class Package {
		String ruleSetKey;
		String packageName;

		String setRuleSetKey(String ruleSetKey) {
			this.ruleSetKey = ruleSetKey;
			packageName = genPackageName(ruleSetKey);
			return packageName;
		}

		String getPackageName() {
			return packageName;
		}

		public String getRuleSetKey() {
			return ruleSetKey;
		}

		public String toString() {
			return "package " + packageName;
		}
	}

	class Imports {
		List<String> imports = new ArrayList<String>();

		void addImports(String impt) {
			imports.add(impt);
		}

		public List<String> getImports() {
			return imports;
		}

		public String toString() {
			StringBuffer sb = new StringBuffer(128);
			for (String imp : imports) {
				sb.append("import ").append(imp).append(";\r\n");
			}
			return sb.toString().trim();
		}

	}

	class Globals {
		List<String> globals = new ArrayList<String>();

		void addGlobals(String global) {
			globals.add(global);
		}

		public List<String> getGlobals() {
			return globals;
		}

		public String toString() {
			StringBuffer sb = new StringBuffer(128);
			for (String global : globals) {
				sb.append("global ").append(global).append(";\r\n");
			}
			return sb.toString().trim();
		}
	}

	class Functions {
		List<String> functions = new ArrayList<String>();

		void addFunctions(String function) {
			functions.add(function);
		}

		public List<String> getFunctions() {
			return functions;
		}

		public String toString() {
			StringBuffer sb = new StringBuffer(128);
			for (String function : functions) {
				sb.append(function).append(";\r\n");
			}
			return sb.toString().trim();
		}
	}

	class Declares {
		List<String> declares = new ArrayList<String>();

		void addDeclares(String declare) {
			declares.add(declare);
		}

		public List<String> getDeclares() {
			return declares;
		}

		public String toString() {
			StringBuffer sb = new StringBuffer(128);
			for (String declare : declares) {
				sb.append(declare).append(";\r\n");
			}
			return sb.toString().trim();
		}
	}

	class Rule {

		String ruleName;
		Attributes attributes = new Attributes();
		Conditions conditions = new Conditions();
		Actions actions = new Actions();
		Variables variables = new Variables();

		void setRuleName(String ruleName) {
			this.ruleName = ruleName;
		}

		public String getRuleName() {
			return ruleName;
		}

		class Attributes {
			List<String> attributes = new ArrayList<String>();

			void addAttributes(String attribute) {
				attributes.add(attribute);
			}

			public List<String> getAttributes() {
				return attributes;
			}

			public void setAttributes(List<String> attributes) {
				this.attributes = attributes;
			}

			public String toString() {
				StringBuffer sb = new StringBuffer(128);
				for (String attribute : attributes) {
					sb.append(attribute).append("\r\n");
				}
				return sb.toString().trim();
			}
		}

		class Variables {
			Map<String, String> variables = new LinkedHashMap<String, String>();

			String getVariableName(String className) {
				String variableName = variables.get(className);
				if (variableName != null) {
					return variableName;
				}
				int lastIndexOf = className.lastIndexOf('.');
				if (lastIndexOf > -1) {
					variableName = "$$" + className.substring(lastIndexOf + 1);
				} else {
					variableName = "$$" + className;
				}
				variables.put(className, variableName);
				return variableName;
			}
		}

		class Conditions {

			List<ConditionGroup> conditionGroups = new ArrayList<ConditionGroup>();

			void addCondition(Condition condition) {
				if (conditionGroups.isEmpty()) {
					ConditionGroup conditionGroup = createConditionGroup();
					conditionGroup.add(condition);
					conditionGroups.add(conditionGroup);
					return;
				}
				{
					ConditionGroup conditionGroup = conditionGroups
							.get(conditionGroups.size() - 1);
					if (!conditionGroup.add(condition)) {
						conditionGroup = createConditionGroup();
						conditionGroup.add(condition);
						conditionGroups.add(conditionGroup);
						return;
					}
				}
			}

			Condition createCondition(String logic, Expression left,
					String operation, Expression right) {
				Condition condition = createConditionGroup().new Condition();
				condition.logic = createEvaluable(logic);
				condition.left = left;
				condition.operation = createEvaluable(operation);
				condition.right = right;
				return condition;
			}

			ConditionGroup createConditionGroup() {
				return new ConditionGroup();
			}

			class ConditionGroup {
				List<Condition> conditions = new ArrayList<Condition>();

				boolean add(Condition condition) {
					if (conditions.isEmpty()) {
						conditions.add(condition);
						return true;
					}
					{
						Condition lastCondition = conditions.get(conditions
								.size() - 1);
						if (lastCondition.left.name
								.matchClassName(condition.left.name)) {
							conditions.add(condition);
							return true;
						}
					}
					return false;
				}

				class Condition {
					Evaluable logic;
					Expression left;
					Evaluable operation;
					Expression right;

					public String toString() {
						return new StringBuffer().append(logic).append(left)
								.append(' ').append(operation).append(' ')
								.append(right).toString();
					}
				}

				public String toString() {
					// ClassName(a=b,c=d)
					if (conditions.isEmpty()) {
						return "";
					}
					Condition condition = conditions.get(0);
					String className = condition.left.name.getClassName();
					int lastIndexOf = className.lastIndexOf('.');
					if (lastIndexOf > -1) {
						className = className.substring(lastIndexOf + 1,
								className.length());
					}
					StringBuffer sb = new StringBuffer();
					sb.append(className).append('(');
					for (Condition cdt : conditions) {
						sb.append(cdt);
					}
					sb.append(')');
					return sb.toString();
				}

			}
		}

		class Actions {
			List<Action> actions = new ArrayList<Action>();

			Action createAction(Expression left, String operation,
					Expression right) {
				Action action = new Action();
				action.left = left;
				action.operation = operation;
				action.right = right;
				return action;
			}

			class Action {
				Expression left;
				String operation;
				Expression right;

				public String toString() {
					return new StringBuffer().append(left).append(' ').append(
							operation).append(' ').append(right).toString();
				}
			}
		}

		Expression constExpression(String value, String valueType) {
			Expression exp = new Expression();
			exp.name = createEvaluable(value);
			exp.type = valueType;
			return exp;
		}

		Expression constExpression(Expression[] expressions) {
			Expression exp = new Expression();
			exp.type = Expression.TYPE_CONSTANT;
			exp.parameters = expressions;
			return exp;
		}

		Expression functionExpression(String name, Expression[] parameters) {
			Expression exp = new Expression();
			exp.name = createEvaluable(name);
			exp.type = Expression.TYPE_FUNCTION;
			exp.parameters = parameters == null ? new Expression[0]
					: parameters;
			return exp;
		}

		Expression variableExpression(String name) {
			Expression exp = new Expression();
			exp.name = createEvaluable(name);
			exp.type = Expression.TYPE_VARIABLE;
			exp.parameters = null;
			return exp;
		}

		class Expression {
			static final String TYPE_FUNCTION = "_function_";
			static final String TYPE_VARIABLE = "_variable_";
			static final String TYPE_CONSTANT = "_constant_";
			Evaluable name;
			String type;
			Expression[] parameters;

			boolean isFunction() {
				return TYPE_FUNCTION.equals(type);
			}

			public String toString() {
				if (TYPE_VARIABLE.equals(type)) {
					return name.toString();
				} else if (TYPE_FUNCTION.equals(type)) {
					StringBuffer sb = new StringBuffer();
					sb.append(name).append('(');
					for (Expression parameter : parameters) {
						sb.append(parameter).append(',');
					}
					if (parameters.length > 0) {
						sb.setLength(sb.length() - 1);
					}
					sb.append(')');
					return sb.toString();
				} else if ("string".equalsIgnoreCase(type)
						|| "date".equalsIgnoreCase(type)) {
					return "\"" + name.toString().replaceAll("\"", "\\\\\"")
							+ "\"";
				} else if (type != null) {
					return name.toString();
				}
				return "";
			}
		}
	}

	Evaluable createEvaluable(String name) {
		Evaluable eval = new Evaluable();
		eval.name = name;
		return eval;
	}

	class Evaluable {
		String name;

		String evaluate() {
			FieldMapper fieldMapper = getFieldMapper(name);
			if (fieldMapper == null) {
				return name;
			}
			return fieldMapper.getObjectField();
		}

		String getClassName() {
			FieldMapper fieldMapper = getFieldMapper(name);
			if (fieldMapper == null) {
				return "";
			}
			return fieldMapper.getObjectClass();
		}

		boolean matchClassName(Evaluable eval) {
			return getClassName().equals(eval.getClassName());
		}

		public String toString() {
			return evaluate();
		}

	}

	Context createContext(Context parent) {
		Context context = new Context();
		context.parent = parent;
		return context;
	}

	class Context {
		static final String TAB_WIDTH = "    ";
		Context parent;

		public StringBuffer getTabWidth(StringBuffer tabWidth) {
			tabWidth = tabWidth == null ? new StringBuffer() : tabWidth;
			if (parent == null) {
				return tabWidth.append(TAB_WIDTH);
			}
			return tabWidth.append(parent).append(TAB_WIDTH);
		}
	}

	Printable print(Context context, String... strs) {
		Printable printable = new Printable();
		printable.strs = strs == null ? new String[0] : strs;
		printable.context = context;
		return printable;
	}

	class Printable {

		Context context;
		String[] strs;

		public String toString() {
			StringBuffer sb = context == null ? new StringBuffer() : context
					.getTabWidth(null);
			for (String str : strs) {
				sb.append(str);
			}
			return sb.toString();
		}
	}
}
