/*******************************************************************************
 * Copyright (c) 2013-8-12 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.foreveross.logging.impl;

import java.util.Stack;
import java.util.concurrent.TimeUnit;

import com.foreveross.logging.LogDataBus;
import com.foreveross.logging.LogProcessStep;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2013-8-12
 */
public class AsyncLocalDataBus implements LogDataBus, Runnable {

	private static final Stack<Object> stack = new Stack<Object>();

	private LogProcessStep logProcessStep;

	public AsyncLocalDataBus() {
		new Thread(this).start();
	}

	public void onProcess(Object log) {
		// persistent log
		getLogProcessStep().step7(new Object[] { log });
	}

	public void put(Object log) {
		stack.push(log);
	}

	public void run() {
		while (true) {
			try {
				onProcess(stack.pop());
			} catch (Exception e1) {
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (Exception e2) {
				}
			}
		}
	}

	public LogProcessStep getLogProcessStep() {
		return logProcessStep;
	}

	public void setLogProcessStep(LogProcessStep logProcessStep) {
		this.logProcessStep = logProcessStep;
	}

}
