package org.iff.sample.business.core.repository;

import java.io.Serializable;

import org.iff.sample.business.core.domainmodel.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Serializable> {

	User findByUserName(String userName);

	User findByEmail(String email);

	User findByUserNameAndPassword(String userName, String password);

	@Query("select count(u.id) from User u join u.roles r where u.userName=?1 and r.name=?2")
	Number containsRoles(String userName, String roles);

	Page<User> findAll(Pageable pageable);
}
