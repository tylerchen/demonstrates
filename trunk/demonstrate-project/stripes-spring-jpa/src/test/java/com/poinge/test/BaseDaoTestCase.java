package com.poinge.test;

import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.IOperationListener;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.CachedDataSet;
import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlProducer;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.InputSource;

/**
 * Base class for running DAO tests.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(defaultRollback = true)
@TestExecutionListeners()
@ContextConfiguration(locations = { "classpath*:spring/root-for-test.xml" })
public abstract class BaseDaoTestCase extends
		AbstractTransactionalJUnit4SpringContextTests {
	@PersistenceContext
	private EntityManager entityManager;
	/**
	 * Log variable for all child classes. Uses LogFactory.getLog(getClass()) from Commons Logging
	 */
	protected final Log log = LogFactory.getLog(getClass());
	/**
	 * ResourceBundle loaded from src/test/resources/${package.name}/ClassName.properties (if exists)
	 */
	protected ResourceBundle rb;

	private DataSourceDatabaseTester dataSourceDatabaseTester = null;

	/**
	 * Default constructor - populates "rb" variable if properties file exists for the class in
	 * src/test/resources.
	 */
	public BaseDaoTestCase() {
		// Since a ResourceBundle is not required for each class, just
		// do a simple check to see if one exists
		String className = this.getClass().getName();

		try {
			rb = ResourceBundle.getBundle(className);
		} catch (MissingResourceException mre) {
			log.trace("No resource bundle found for: " + className);
		}
	}

	/**
	 * Utility method to populate a javabean-style object with values
	 * from a Properties file
	 *
	 * @param obj the model object to populate
	 * @return Object populated object
	 * @throws Exception if BeanUtils fails to copy properly
	 */
	protected Object populate(final Object obj) throws Exception {
		// loop through all the beans methods and set its properties from its .properties file
		Map<String, String> map = new HashMap<String, String>();

		for (Enumeration<String> keys = rb.getKeys(); keys.hasMoreElements();) {
			String key = keys.nextElement();
			map.put(key, rb.getString(key));
		}

		BeanUtils.copyProperties(obj, map);

		return obj;
	}

	/**
	 * Create a HibernateTemplate from the SessionFactory and call flush() and clear() on it.
	 * Designed to be used after "save" methods in tests: http://issues.appfuse.org/browse/APF-178.
	 *
	 * @throws org.springframework.beans.BeansException
	 *          when can't find 'sessionFactory' bean
	 */
	protected void flush() throws BeansException {
		entityManager.flush();
		entityManager.clear();
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	@Before
	public void setUp() throws Exception {
		importDataSet();
	}

	@After
	public void tearDown() throws Exception {
		removeDataSet();
	}

	private void importDataSet() {
		if (getDataSetFilePaths() == null || getDataSetFilePaths().length == 0) {
			return;
		}
		try {
			String[] dataSetPaths = getDataSetFilePaths();
			IDataSet[] dataSet = new IDataSet[dataSetPaths.length];
			for (int i = 0; i < dataSetPaths.length; i++) {
				String path = dataSetPaths[i].startsWith("/") ? dataSetPaths[i]
						: "/" + dataSetPaths[i];
				// dataSet[i] = new FlatXmlDataSet(DbUnit.class.getResourceAsStream(path));
				dataSet[i] = getDataSetObject(path);
				logger.debug("载入数据库资源文件：" + path);
			}
			CompositeDataSet compositeDateSet = new CompositeDataSet(
					(IDataSet[]) dataSet);
			DataSource dataSource = (DataSource) super.applicationContext
					.getBean(getDataSourceName(), DataSource.class);
			if (dataSourceDatabaseTester == null) {
				dataSourceDatabaseTester = new DataSourceDatabaseTester(
						dataSource);
				dataSourceDatabaseTester
						.setOperationListener(new IOperationListener() {
							public void operationTearDownFinished(
									IDatabaseConnection connection) {
								closeConnection(connection);
							}

							public void operationSetUpFinished(
									IDatabaseConnection connection) {
								closeConnection(connection);
							}

							public void connectionRetrieved(
									IDatabaseConnection connection) {
								System.out.println("IDatabaseConnection:"
										+ connection);
							}

							private void closeConnection(
									IDatabaseConnection connection) {
								try {
									connection.getConnection().commit();
									connection.getConnection().close();
								} catch (Exception e) {
								}
							}
						});
				dataSourceDatabaseTester.setSetUpOperation(setUpOp());
				dataSourceDatabaseTester.setDataSet(compositeDateSet);
				dataSourceDatabaseTester.onSetup();
			}
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}

	private void removeDataSet() {
		if (dataSourceDatabaseTester != null) {
			try {
				dataSourceDatabaseTester.setTearDownOperation(tearDownOp());
				dataSourceDatabaseTester.onTearDown();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private IDataSet getDataSetObject(String path) throws Exception {
		boolean enableColumnSensing = true;
		InputStreamReader inReader = new InputStreamReader(getClass()
				.getResourceAsStream(path), "UTF-8");
		return new CachedDataSet(new FlatXmlProducer(new InputSource(inReader),
				true, enableColumnSensing, false));
	}

	public String getDataSourceName() {
		return "dataSource";
	}

	public String[] getDataSetFilePaths() {
		return new String[0];
	}

	protected DatabaseOperation setUpOp() {
		return DatabaseOperation.REFRESH;
	}

	protected DatabaseOperation tearDownOp() {
		return DatabaseOperation.NONE;
	}
}
