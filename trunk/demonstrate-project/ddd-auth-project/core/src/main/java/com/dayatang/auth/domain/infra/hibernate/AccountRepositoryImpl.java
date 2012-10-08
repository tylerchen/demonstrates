package com.dayatang.auth.domain.infra.hibernate;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.dayatang.auth.core.DefaultPasswordEncoder;
import com.dayatang.auth.core.PasswordEncoder;
import com.dayatang.auth.domain.Account;
import com.dayatang.auth.domain.AccountRepository;
import com.dayatang.auth.domain.Role;
import com.dayatang.spring.repository.BaseEntityRepositoryHibernateSpring;

/**
 * 
 * @author lingen.liu
 * Account 仓储的实现
 */
@Repository
public class AccountRepositoryImpl extends
		BaseEntityRepositoryHibernateSpring<Account, Long> implements
		AccountRepository {

	@Autowired(required = false)
	private PasswordEncoder ramsPasswordEncoder;

	public void setRamsPasswordEncoder(PasswordEncoder ramsPasswordEncoder) {
		this.ramsPasswordEncoder = ramsPasswordEncoder;
	}

	private PasswordEncoder getRamsPasswordEncoder() {
		return ramsPasswordEncoder;
	}

	public AccountRepositoryImpl() {
		super(Account.class);
		setRamsPasswordEncoder(new DefaultPasswordEncoder());
	}

	public String encodePassword(String rawPass) {
		return getRamsPasswordEncoder().encodePassword(rawPass);
	}

	public boolean isPasswordValid(String encPass, String rawPass) {
		return getRamsPasswordEncoder().isPasswordValid(encPass, rawPass);
	}

	@SuppressWarnings("unchecked")
	public List<Account> queryAccountByRole(Role role) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Account.class);
		criteria.createAlias("roles", "rs");
		criteria.add(Restrictions.eq("rs.id", role.getId()));
		criteria.setFetchMode("roles", FetchMode.JOIN);
		List<Account> acountList = getHibernateTemplate().findByCriteria(
				criteria);
		return acountList;

	}

	public boolean isEmailExist(String email) {
		Object account = getSingleResult("from Account a where a.email = ?",
				new Object[] { email });
		if (account != null)
			return true;
		return false;
	}

	public boolean isUsernameExist(String username) {
		Object account = getSingleResult("from Account a where a.username = ?",
				new Object[] { username });
		if (account != null)
			return true;
		return false;
	}

	/**
	 * 根据用户的姓名获取用户信息,并且连同角色信息一同获取
	 */
	public Account get(String username) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Account.class);
		criteria.add(Restrictions.eq("username", username));
		criteria.setFetchMode("roles", FetchMode.JOIN);
		List<Account> acountList = getHibernateTemplate().findByCriteria(
				criteria);
		if (acountList.size() == 0)
			return null;
		return acountList.get(0);
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> getAccountInfo(final String username) {
		final String sql = "select ac.USERNAME USERNAME,ac.PASSWORD PASSWORD,ac.REAL_NAME REAL_NAME,ac.EMAIL EMAIL,ac.REGISTRY_DATE REGISTRY_DATE,ac.IS_LOCKED IS_LOCKED,ac.PASSWORD_LAST_UPDATE_DATE PASSWORD_LAST_UPDATE_DATE,ac.DESCRIPTION DESCRIPTION from RAMS_ACCOUNT ac where ac.USERNAME = ?";
		List<Map<String, Object>> list = getHibernateTemplate().executeFind(
				new HibernateCallback<List<Map<String, Object>>>() {
					public List<Map<String, Object>> doInHibernate(
							Session session) throws HibernateException,
							SQLException {
						List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
						try {
							SQLQuery query = session.createSQLQuery(sql);
							query.setParameter(0, username);
							List queryList = query.list();
							if (!queryList.isEmpty()) {
								Map<String, Object> map = new HashMap<String, Object>();
								Object[] o = (Object[]) queryList.get(0);
								map.put("USERNAME", o[0]);
								map.put("PASSWORD", o[1]);
								map.put("REAL_NAME", o[2]);
								map.put("EMAIL", o[3]);
								map.put("REGISTRY_DATE", o[4]);
								map.put("IS_LOCKED", "Y".equals(String
										.valueOf(o[5])) ? true : false);
								map.put("PASSWORD_LAST_UPDATE_DATE", o[6]);
								map.put("DESCRIPTION", o[7]);
								result.add(map);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						return result;
					}
				});
		return list.isEmpty() ? null : list.get(0);
	}

	@SuppressWarnings("unchecked")
	public List<String> getRole(final Long accountId) {
		final String sql = "select R.NAME as ROLE_NAME from RAMS_ROLE R,RAMS_ACCOUNT_R_ROLE RA where RA.ACCOUNT_ID = ? and RA.ROLE_ID = R.id";
		return getHibernateTemplate().executeFind(
				new HibernateCallback<List<String>>() {
					public List<String> doInHibernate(Session session)
							throws HibernateException, SQLException {
						List<String> result = new ArrayList<String>();
						try {
							SQLQuery query = session.createSQLQuery(sql);
							query.setParameter(0, accountId);
							result = query.list();
						} catch (Exception e) {
							e.printStackTrace();
						}
						return result;
					}
				});
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getAllResources() {
		final String sql = "select ROLE.NAME as ROLE, ENTITY.NAME as URL from RAMS_ROLE ROLE,RAMS_FUNCTION_ENTITY ENTITY,RAMS_ROLE_R_FUNCTION_ENTITY ROLE_ENTITY where ROLE.ID = ROLE_ENTITY.ROLE_ID AND ENTITY.ID=ROLE_ENTITY.FUNCTION_ENTITY_ID";
		return getHibernateTemplate().executeFind(
				new HibernateCallback<List>() {
					public List doInHibernate(Session session)
							throws HibernateException, SQLException {
						List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
						try {
							SQLQuery query = session.createSQLQuery(sql);
							List queryList = query.list();
							for (int i = 0; i < queryList.size(); i++) {
								Object[] o = (Object[]) queryList.get(i);
								Map<String, Object> map = new HashMap<String, Object>();
								map.put("ROLE", o[0]);
								map.put("URL", o[1]);
								result.add(map);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						return result;
					}
				});
	}
}
