package org.iff.sample.business.core.repository;

import java.io.Serializable;

import org.iff.sample.business.core.domainmodel.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Serializable> {

}
