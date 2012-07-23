/*******************************************************************************
 * Copyright (c) 2012-6-3 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package org.iff.sample.core.application.impl;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.iff.sample.busineess.core.domainmodel.MyTestEntity;
import org.iff.sample.infra.util.SocketUtil;
import org.junit.Test;

import com.dayatang.cache.Cache;
import com.dayatang.domain.InstanceFactory;
import com.dayatang.springtest.PureSpringTestCase;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2012-6-3
 */
public class MemcachedTest extends PureSpringTestCase {

	protected String[] springXmlPath() {
		return new String[] { "classpath*:spring/root-test.xml" };
	}

	private Cache getCache() {
		return InstanceFactory.getInstance(Cache.class, "javaMemcached");
	}

	@Test
	public void test_memchached() {
		if (!SocketUtil.test("localhost", 11211)) {
			System.out.println("Can not connect to: localhost:11211!");
			return;
		}
		StringBuilder temp = new StringBuilder(1024);
		for (int i = 0; i < 10000; i++) {
			temp.append(getClass().getName());
		}
		temp.setLength(1024 * 1024 / 2);
		Cache cache = getCache();
		StringBuilder sb = new StringBuilder(32);
		long count = 0, start = 0;
		for (int i = 0; i < 100; i++) {
			MyTestEntity t = new MyTestEntity();
			{
				t.setId(Long.valueOf(i));
				t.setName(temp.toString());
				t.setAge(i);
			}
			{
				sb.setLength(0);
				sb.append(MyTestEntity.class.getSimpleName()).append("#")
						.append(t.getId());
			}
			start = System.nanoTime();
			cache.put(sb.toString(), t);
			count = System.nanoTime() - start + count;
		}
		System.out.println(cache.isKeyInCache(MyTestEntity.class
				.getSimpleName()
				+ "#1"));
		System.out.println("共耗时：" + (double) count / (1000 * 1000 * 1000));
	}

	@Test
	public void test_memchachedMutiThread() {
		if (!SocketUtil.test("localhost", 11211)) {
			System.out.println("Can not connect to: localhost:11211!");
			return;
		}
		try {
			int count = 20;
			final int[] threadCounter = new int[] { count };
			for (final int[] counter = new int[] { 0 }; counter[0] < count; counter[0]++) {
				new Thread() {
					@Override
					public void run() {
						try {
							Cache cache = null;
							while (cache == null) {
								try {
									sleep(new Random().nextInt(10) * 1000L);
									cache = getCache();
									cache.isKeyInCache("#1");
								} catch (Exception e) {
									cache = null;
								}
							}
							StringBuilder sb = new StringBuilder(32);
							long count = 0, start = 0;
							for (int i = 0; i < 10000; i++) {
								MyTestEntity t = new MyTestEntity();
								long id = counter[0] * 100000 + i;
								{
									t.setId(id);
									t.setName("Name-" + id);
									t.setAge((int) id);
								}
								{
									sb.setLength(0);
									sb.append(
											MyTestEntity.class.getSimpleName())
											.append("#").append(t.getId());
								}
								start = System.nanoTime();
								cache.put(sb.toString(), t);
								count = System.nanoTime() - start + count;
							}
							System.out.println(Thread.currentThread().getName()
									+ "共耗时：" + (double) count
									/ (1000 * 1000 * 1000));
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							synchronized (threadCounter) {
								threadCounter[0] = threadCounter[0] - 1;
							}
						}
					}
				}.start();
			}
			while (threadCounter[0] > 0) {
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
