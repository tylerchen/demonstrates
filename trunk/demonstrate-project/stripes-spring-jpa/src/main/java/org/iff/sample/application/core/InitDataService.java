package org.iff.sample.application.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.iff.sample.business.core.domainmodel.Role;
import org.iff.sample.business.core.domainmodel.User;
import org.iff.sample.business.core.repository.RoleRepository;
import org.iff.sample.business.core.repository.UserRepository;
import org.iff.sample.framework.converter.PasswordTypeConverter;
import org.iff.sample.framework.ext.AppConfig;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * initialize datas for development environment.
 * you should remove this class when deploy in product environment.
 * @author Tyler
 */

@Named
public class InitDataService {

	private static boolean init = false;
	@Inject
	UserRepository userRepository;
	@Inject
	RoleRepository roleRepository;

	public void init() {
		if (init) {
			return;
		}
		try {
			boolean isDev = false;
			try {
				String value = AppConfig.getProperty(AppConfig.APP_DEV, "");
				isDev = "true".equals(value);
			} catch (Exception e) {
			}
			if (!isDev) {
				return;
			}
			{
				System.out
						.println("=========================================================================================================");
				System.out
						.println("== NOTICE!!                                                                                            ==");
				System.out
						.println("== Initializing datas for the development/test environment.                                            ==");
				System.out
						.println("== You should only can see this in development/test environment.                                       ==");
				System.out
						.println("== You can shutdown the development/test data init by remove APP_DEV property in file app.properties.  ==");
				System.out
						.println("=========================================================================================================");
			}
			addUser();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			init = true;
		}
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public List<User> addUser() {
		List<Role> rolesList = new ArrayList<Role>();
		{
			rolesList.add(Role.createSuperAdmin());
			rolesList.add(Role.createPublic());
			roleRepository.save(rolesList);
		}
		List<User> list = new ArrayList<User>();
		for (int i = 0; i < 20; i++) {
			User user = new User();
			{
				user.setEmail("outute@163.com");
				user.setName("Tyler Chen");
				user.setPassword(new PasswordTypeConverter().convert("test",
						null, null));
				user.setUserName("test" + (i == 0 ? "" : i));
				user.setRoles(Arrays.asList(rolesList.get(i % 2)));
			}
			userRepository.save(user);
			list.add(user);
		}
		return list;
	}

}
