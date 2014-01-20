/*******************************************************************************
 * Copyright (c) 2013-5-14 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.foreveross.demo.sys.application.impl.drl;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2013-5-14
 */
public class Rules {
	private RulesPackage pkg = new RulesPackage();
	private Imports imports = new Imports();
	private Globals globals = new Globals();
	private Functions functions = new Functions();
	private Declares declares = new Declares();
	private List<Rule> rules = new ArrayList<Rule>();
}
