package com.dayatang.auth.domain;

import java.util.List;
import java.util.Map;

import com.dayatang.domain.BaseEntityRepository;

/**
 * @author lingen.liu
 * 帐户仓储
 */
public interface AccountRepository extends BaseEntityRepository<Account, Long> {

	/**
	 * add by vakin.jiang 2010.4.28
	 * 根据角色查询与之关联的所有账户集合
	 * @param fe
	 * @return
	 */
	public List<Account> queryAccountByRole(Role role);

	public boolean isEmailExist(String email);

	public boolean isUsernameExist(String username);

	public Account get(String username);

	public Map<String, Object> getAccountInfo(String username);

	public List<String> getRole(Long accountId);

	public List<Map<String, Object>> getAllResources();

	/**
	 * Encodes the specified raw password with an implementation specific algorithm.
	 * @param rawPass the password to encode
	 * @return
	 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
	 * @since 2011-11-23
	 */
	String encodePassword(String rawPass);

	/**
	 * Validates a specified "raw" password against an encoded password.
	 * @param encPass a pre-encoded password
	 * @param rawPass a raw password to encode and compare against the pre-encoded password
	 * @return
	 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
	 * @since 2011-11-23
	 */
	boolean isPasswordValid(String encPass, String rawPass);
}
