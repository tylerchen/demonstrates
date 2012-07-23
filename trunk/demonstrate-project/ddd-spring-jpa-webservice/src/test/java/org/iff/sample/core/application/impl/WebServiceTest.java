/*******************************************************************************
 * Copyright (c) 2012-6-3 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package org.iff.sample.core.application.impl;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.iff.sample.infra.util.SocketUtil;
import org.junit.Test;

import com.dayatang.springtest.PureSpringTestCase;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2012-6-3
 */
public class WebServiceTest extends PureSpringTestCase {

	protected String[] springXmlPath() {
		return new String[] { "classpath*:spring/root-test.xml" };
	}

	@Test
	public void test() {
		try {
			if (!SocketUtil.test("localhost", 8080)) {
				System.out.println("Can not connect to: localhost:8080!");
				return;
			}
			/*URL url = new URL(
					"http://localhost:8080/webservice/v1/MyApplication/findMyTestByNameTest4/test");*/
			URL url = new URL(
					"http://localhost:8080/webservice/user/userregister");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("content-type",
					"application/x-www-form-urlencoded");
			conn.setRequestProperty("Accept-Charset", "utf-8");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setConnectTimeout(5 * 100000);
			conn.connect();
			PrintWriter out = new PrintWriter(conn.getOutputStream());//发送数据
			out.print("age=11&name=name");
			out.flush();
			out.close();

			if (conn.getResponseCode() == 200) {
				System.out.println("成功---------------");
				System.out.println(SocketUtil.getContent(conn.getInputStream(),
						false));
			} else {
				System.out.println("失败---------------");
				System.out.println(SocketUtil.getContent(conn.getInputStream(),
						false));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
