==== migrate for jboss eap6/as7 ====

1. Try to deploy again.
   Using the domain console to deploy a war artifact could fail.
   I don't know why, it is weird, you should upload again and try for many times.

2. Avoid the jboss load the jpa configure.
   Rename the META-INF/persistence.xml to META-INF/jpa-persistence.xml 
   to avoid the jboss auto load the jpa configure. Instead, you need to
   add a property to entityManagerFactory bean, such as:
   <property name="persistenceXmlLocation" value="classpath*:META-INF/jpa-persistence.xml" />

3. Using jboss transaction look up.
   Adding a 'jpaProperties' property to entityManagerFactory bean, such as:
	<property name="jpaProperties">
		<props>
			<prop key="hibernate.transaction.manager_lookup_class">
				org.hibernate.transaction.JBossTransactionManagerLookup
            </prop>
		</props>
	</property>

4. Using the jta data source.
   In file META-INF/jpa-persistence.xml, using data source, such as:
   <jta-data-source>java:jboss/datasources/ExampleDS</jta-data-source>

5. Specify the entity class.
   Specify the entity class in file META-INF/jpa-persistence.xml, such as:
   <class>org.iff.sample.business.core.domainmodel.Role</class>

6. Forbidden the jboss load the hibernate module.
   Add 'jboss-deployment-structure.xml' file to WEB-INF, and tell the jboss not to load hibernate:
	<jboss-deployment-structure xmlns="urn:jboss:deployment-structure:1.0">
	    <deployment>
	        <dependencies>
	            <module name="org.slf4j" />
	        </dependencies>
	        <exclusions>
	            <module name="org.hibernate" />
	            <module name="org.dom4j" />
	        </exclusions>
	    </deployment>
	</jboss-deployment-structure>

7. Understand the errors:
7.1 Application error, key word 'org.jboss.msc.service.fail', it means the app configuration is not right(for the jboss eap6/as7).
    Solutions 1: check the configurations, specially, about the jboss eap6/as7 migration configurations.
    Solutions 2: try to upload and deploy again if you deploy success before or you sure the configurations is fine.
	[Server:server-one] 14:34:00,329 INFO  [org.jboss.as.server.deployment] (MSC service thread 1-3) JBAS015876: ......
	[Server:server-one] 14:34:00,495 ERROR [org.jboss.msc.service.fail] (MSC service thread 1-3) MSC000001: Failed to start service ......
	......
	[Server:server-one] Caused by: org.jboss.as.server.deployment.DeploymentUnitProcessingException: JBAS018740: Failed to mount deployment content
	......
	[Server:server-one] Caused by: java.util.zip.ZipException: error in opening zip file