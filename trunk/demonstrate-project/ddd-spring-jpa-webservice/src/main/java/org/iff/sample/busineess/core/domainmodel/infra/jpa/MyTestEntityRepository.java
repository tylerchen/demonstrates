/*******************************************************************************
 * Copyright (c) 2012-6-2 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package org.iff.sample.busineess.core.domainmodel.infra.jpa;

import java.util.List;

import org.iff.sample.busineess.core.domainmodel.MyTestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2012-6-2
 */
public interface MyTestEntityRepository extends
		JpaRepository<MyTestEntity, Long> {

	List<MyTestEntity> findByName(String name);
	
	@Query("from MyTestEntity where name=?1 and age=?2")
	List<MyTestEntity> findByNameAndAge(String name, Integer age);
}
