package com.foreveross.demo.web.action.base;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import com.foreveross.demo.sys.application.vo.AccountVO;

/**
 * 扩展User类，加入一些其它属性
 * 
 * @author lingen.liu
 * 
 */
@SuppressWarnings("serial")
public class UserInfo extends User {
	private AccountVO account;

	/*
	 * 检测是否是测试用户
	 */
	protected boolean isTestUser;

	protected Map<String, Collection<ConfigAttribute>> roleEntityMap = new HashMap<String, Collection<ConfigAttribute>>();

	public UserInfo(String username, String password, boolean enabled,
			boolean accountNonExpired, boolean credentialsNonExpired,
			boolean accountNonLocked, Collection<GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired,
				credentialsNonExpired, accountNonLocked, authorities);
	}

	public boolean hasRole(String roleName) {
		if (roleName == null) {
			return false;
		}
		if (!roleName.startsWith("ROLE_")) {
			roleName = "ROLE_" + roleName;
		}
		return getAuthorities().contains(new SimpleGrantedAuthority(roleName));
	}

	public boolean hasAuthorize(String url) {
		if (url == null) {
			return false;
		}
		AntUrlPathMatcher urlMatcher = new AntUrlPathMatcher();
		for (Entry<String, Collection<ConfigAttribute>> entry : getRoleEntityMap()
				.entrySet()) {
			if (urlMatcher.pathMatchesUrl(entry.getKey(), url)) {
				return true;
			}
		}
		return false;
	}

	//	public static void main(String[] args) {
	//		AntUrlPathMatcher urlMatcher = new AntUrlPathMatcher();
	//		System.out.println(urlMatcher.pathMatchesUrl("/*",
	//				"/admin/Aadmin-test.jsp"));
	//	}

	//

	public boolean isTestUser() {
		return isTestUser;
	}

	public AccountVO getAccount() {
		return account;
	}

	public void setAccount(AccountVO account) {
		this.account = account;
	}

	public void setTestUser(boolean isTestUser) {
		this.isTestUser = isTestUser;
	}

	public Map<String, Collection<ConfigAttribute>> getRoleEntityMap() {
		return roleEntityMap;
	}

	public void setRoleEntityMap(
			Map<String, Collection<ConfigAttribute>> roleEntityMap) {
		this.roleEntityMap = roleEntityMap;
	}

	public static class AntUrlPathMatcher {
		private boolean requiresLowerCaseUrl = true;
		private PathMatcher pathMatcher = new AntPathMatcher();

		public AntUrlPathMatcher() {
			this(true);
		}

		public AntUrlPathMatcher(boolean requiresLowerCaseUrl) {
			this.requiresLowerCaseUrl = requiresLowerCaseUrl;
		}

		public Object compile(String path) {
			if (requiresLowerCaseUrl) {
				return path.toLowerCase();
			}

			return path;
		}

		public void setRequiresLowerCaseUrl(boolean requiresLowerCaseUrl) {
			this.requiresLowerCaseUrl = requiresLowerCaseUrl;
		}

		public boolean pathMatchesUrl(Object path, String url) {
			return pathMatcher.match((String) path, url);
		}

		public String getUniversalMatchPattern() {
			return "/**";
		}

		public boolean requiresLowerCaseUrl() {
			return requiresLowerCaseUrl;
		}

		public String toString() {
			return getClass().getName() + "[requiresLowerCase='"
					+ requiresLowerCaseUrl + "']";
		}
	}
}
