/*******************************************************************************
 * Copyright (c) 2012-9-15 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package org.iff.demo.biz.core.domain.infra.jpa;

import java.util.List;

import org.iff.demo.biz.core.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2012-9-15
 */
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

	List<User> findByUserName(String userName);

	Page<User> findAll(Pageable pageable);
}
