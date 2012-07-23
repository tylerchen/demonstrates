/*******************************************************************************
 * Copyright (c) 2011-12-7 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package org.iff.sample.infra.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2011-12-7
 */
public class SocketUtil {

	public static boolean test(String ip, int port) {
		Socket client = null;
		try {
			client = new Socket();
			client.connect(new InetSocketAddress(ip, port), 1000);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				client.close();
			} catch (Exception e) {
			}
		}
		return false;
	}

	public static String getContent(InputStream is, boolean notClose) {
		StringBuilder sb = new StringBuilder(128);
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(is,
					"UTF-8"));
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line).append("\n");
			}
		} catch (Exception e) {
		} finally {
			try {
				if (!notClose) {
					is.close();
				}
			} catch (Exception e) {
			}
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		System.out.println(test("10.103.117.8", 80));
	}
}
