package com.csair.test;

import java.util.Date;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/*******************************************************************************
 * Copyright (c) 2013-5-20 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2013-5-20
 */
public class TestLogUtil {
	private static final Log logger = LogFactory.getLog(TestLogUtil.class);
	private static final TestLogUtil TEST_LOG_UTIL = new TestLogUtil();
	private static final Stack<String[]> STACK = new Stack<String[]>();
	private static Runnable runnable;
	private Timer timer = new Timer();

	private TestLogUtil() {
		timer.schedule(new TimerTask() {
			public void run() {
				try {
					if (runnable != null) {
						runnable.run();
					}
				} catch (Exception e) {
				}
				outputLog();
			}
		}, 10 * 1000, 3 * 1000);
	}

	public static void main(String[] args) {
		try {
			TimeUnit.DAYS.sleep(1);
		} catch (Exception e) {
		}
	}

	void outputLog() {
		try {
			StringBuilder sb = new StringBuilder();
			int count = 0;
			while (!STACK.isEmpty()) {
				count++;
				String[] pop = STACK.pop();
				for (String s : pop) {
					sb.append('[').append(s).append(']');
				}
				logger.debug(sb.toString());
				sb.setLength(0);
			}
			if (count == 0) {
				logger.debug("NO LOG...");
			}
		} catch (Exception e) {
			logger.debug("==outputLog==", e);
		}
	}

	public static void registerTester(Runnable runnable) {
		TestLogUtil.runnable = runnable;
	}

	public static void log(String methodName, long start, long end) {
		STACK.push(new String[] { methodName, new Date(start).toString(),
				new Date(end).toString(), String.valueOf(end - start) });
	}

	public class Tester implements Runnable {

		public void run() {
			// Spring Context getBean("IBE").method();
			// TestLogUtil.log("methodName", start, end);
		}
	}

	//TestLogUtil.registerTester(new Tester());
}
