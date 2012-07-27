package org.iff.sample.application.core;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.iff.sample.business.core.domainmodel.User;
import org.iff.sample.business.core.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.transaction.annotation.Transactional;

@Named
@Transactional
public class UserService {

	@Inject
	UserRepository userRepository;

	public User findUserById(Integer userId) {
		User user = userRepository.findOne(userId);
		return user;
	}

	public User findUserByUserName(String userName) {
		User user = userRepository.findByUserName(userName);
		return user;
	}

	public User findUserByEmail(String email) {
		User user = userRepository.findByEmail(email);
		return user;
	}

	public User login(String userName, String password) {
		User user = userRepository
				.findByUserNameAndPassword(userName, password);
		if (user == null) {
			return user;
		}
		{//load user's roles
			if (user.getRoles() != null) {
				user.getRoles().size();
			}
		}
		return user;
	}

	public Page<User> findUser(int page, int size) {
		List<Order> orders = new ArrayList<Order>();
		{
			orders.add(new Order(Sort.Direction.ASC, "userName"));
		}
		Pageable pageable = new PageRequest(page, size, new Sort(orders));
		return userRepository.findAll(pageable);
	}
}
