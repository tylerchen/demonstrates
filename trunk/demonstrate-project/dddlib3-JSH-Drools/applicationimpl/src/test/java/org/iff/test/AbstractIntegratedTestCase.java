package org.iff.test;

import org.dbunit.operation.DatabaseOperation;
import org.junit.BeforeClass;

import com.dayatang.dbunit.DataSetStrategy;
import com.dayatang.domain.InstanceFactory;
import com.dayatang.spring.factory.SpringProvider;

public abstract class AbstractIntegratedTestCase extends Dbunit {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		InstanceFactory
				.setInstanceProvider(new SpringProvider(springXmlPath()));
	}

	private static String[] springXmlPath() {
		return new String[] { "classpath*:META-INF/spring/root-test.xml" };
	}

	@Override
	public final void setUp() throws Exception {
		super.setUp();
		action4SetUp();
	}

	@Override
	public final void tearDown() throws Exception {
		super.tearDown();
		action4TearDown();
	}

	/**
	 * 设置单元测试方法是否回滚，true=回滚
	 * 
	 * @return 单元测试方法是否回滚
	 */
	protected boolean rollback() {
		return true;
	}

	/**
	 * 单元测试方法执行前的操作
	 */
	protected void action4SetUp() {
	}

	/**
	 * 单元测试方法执行后的操作
	 */
	protected void action4TearDown() {
	}

	@Override
	protected DataSetStrategy getDataSetStrategy() {
		return DataSetStrategy.FlatXml;
	}

	@Override
	protected DatabaseOperation setUpOp() {
		return DatabaseOperation.CLEAN_INSERT;
	}

	@Override
	protected DatabaseOperation tearDownOp() {
		return DatabaseOperation.TRUNCATE_TABLE;
	}

}
