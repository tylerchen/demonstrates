package com.foreveross.demo.web.action.base;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.foreveross.util.mybatis.plugin.Page;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.LocaleProvider;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.TextProvider;
import com.opensymphony.xwork2.TextProviderFactory;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

public class BaseAction implements Preparable, LocaleProvider {

	private static final long serialVersionUID = 5822914716005346375L;
	protected static Logger LOG = LoggerFactory.getLogger(BaseAction.class);

	public static final String LOGIN_USER = "LOGIN_USER";

	public static final String SUCCESS = "SUCCESS";
	public static final String RESULT_JSON = "JSON";
	public static final String MENU_JSON = "MENU_JSON";
	public static final String RESULT_METHOD = "METHOD";
	public static final String RESULT_ERROR_REDIRECT = "ERROR_REDIRECT";
	public static final String RESULT_DEFINED = "DEFINED";

	private final transient TextProvider textProvider;

	private String action = "index";

	public static final String RESULT_AJAXJSON = "ajaxjson";

	public static final String RESULT_HTMLERROR = "htmlError";

	public static final String RESULT_ERROR = "error";

	public static final String RESULT_JSONSTRING = "jsonstring";

	//add begin-- add by wangs , used for 
	//<result name="DEFINED">/pages/{1}/{2}-${definedMethodTemp}.jsp</result>
	//<result name="DEFINED_GMS" type="redirectAction">{1}-{2}-${definedGmsTemp}.gms</result>
	public static final String RESULT_DEFINED_GMS = "DEFINED_GSMS";
	public String definedMethodTemp;
	public String definedGsmsTemp;

	public String returnURL; //点击返回的链接
	public List<String> successMessages = new ArrayList<String>();//提示信息

	private String message = "";
	private boolean falg = false;

	public BaseAction() {
		textProvider = new TextProviderFactory().createInstance(super
				.getClass(), this);
	}

	public UserInfo getUserInfo() {
		return (UserInfo) getRequest().getSession().getAttribute(LOGIN_USER);
	}

	public void setUserInfo(UserInfo info) {
		getRequest().getSession().setAttribute(LOGIN_USER, info);
	}

	public void login(UserInfo info) {
		setUserInfo(info);
	}

	public void logout() {
		setUserInfo(null);
	}

	//	public String getUp() {
	//		try {
	//			AccountVO account = getUserInfo().getAccount();
	//			return this.encryptStringValue(account.getUsername() + "*"
	//					+ account.getPassword());
	//		} catch (Exception e) {
	//		}
	//		return "";
	//	}
	//
	//	protected String encryptStringValue(String s) {
	//		//BasicTextEncryptor bte = new BasicTextEncryptor();
	//		//StrongTextEncryptor bte = new StrongTextEncryptor();
	//		StandardPBEStringEncryptor bte = new StandardPBEStringEncryptor();
	//		bte.setPassword("@CSAIR@NRS@");
	//
	//		return bte.encrypt(s);
	//	}
	//
	//	protected String dencryptStringValue(String s) {
	//		//BasicTextEncryptor bte = new BasicTextEncryptor();
	//		//StrongTextEncryptor bte = new StrongTextEncryptor();
	//		StandardPBEStringEncryptor bte = new StandardPBEStringEncryptor();
	//		bte.setPassword("@CSAIR@NRS@");
	//		return bte.decrypt(s);
	//	}

	public void processException(Exception e) {
		String messages = "";
		messages = textProvider.getText(e.getMessage());
		LOG.info("Resources is : " + messages);
		getRequest().setAttribute("errors", messages);
	}

	public Locale getLocale() {
		ActionContext ctx = ActionContext.getContext();
		if (ctx != null) {
			return ctx.getLocale();
		}
		LOG.debug("Action context not initialized", new String[0]);
		return null;
	}

	public String getDefinedMethodTemp() {
		return definedMethodTemp;
	}

	public void setDefinedMethodTemp(String definedMethodTemp) {
		this.definedMethodTemp = definedMethodTemp;
	}

	public String getDefinedGsmsTemp() {
		return definedGsmsTemp;
	}

	public void setDefinedGmsTemp(String definedGsmsTemp) {
		this.definedGsmsTemp = definedGsmsTemp;
	}

	//add end-- add by wangs 
	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	// =============================List start========================================
	protected List<?> rows;// 分页列表集合
	protected Object voData;// 返回单个vo信息
	// =============================List end========================================

	// =============================Page start========================================
	private int currentPage = 0;
	private int totalSize = 0;
	private int pageSize = 10;

	public Page getPage() {
		Page page = new Page(getPageSize());
		page.setCurrentPage(getCurrentPage());
		return page;
	}

	public void setPage(Page page) {
		setTotalSize(page.getTotalCount());//设置总记录数
		setPageSize(page.getPageSize());//设置分页记录数
		setRows(page.getRows());//设置记录
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isFalg() {
		return falg;
	}

	public void setFalg(boolean falg) {
		this.falg = falg;
	}

	public int getCurrentPage() {
		if (currentPage > 0) {
			currentPage = currentPage - 1;
		}
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public List<?> getRows() {
		return rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}

	public Object getVoData() {
		return voData;
	}

	public void setVoData(Object voData) {
		this.voData = voData;
	}

	// =============================Page end========================================
	/**
	 * Convenience method to get the request
	 *
	 * @return current request
	 */
	protected HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	/**
	 * Convenience method to get the response
	 *
	 * @return current response
	 */
	protected HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	/**
	 * Convenience method to get the session. This will create a session if one doesn't exist.
	 *
	 * @return the session from the request (request.getSession()).
	 */
	protected HttpSession getSession() {
		return getRequest().getSession();
	}

	public void prepare() throws Exception {
		try {
			int page = 1;
			Enumeration<?> paramNames = getRequest().getParameterNames();
			while (paramNames.hasMoreElements()) {
				String name = (String) paramNames.nextElement();
				if (name != null && name.startsWith("d-")
						&& name.endsWith("-p")) {
					String pageValue = getRequest().getParameter(name);
					if (pageValue != null) {
						try {
							page = Integer.parseInt(pageValue);
							break;
						} catch (Exception e) {
						}
					}
				}
			}
			setCurrentPage(page);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected String executeMethod(String method) throws Exception {
		Class<?>[] c = null;
		Method m = this.getClass().getMethod(method, c);
		Object[] o = null;
		String result = (String) m.invoke(this, o);
		return result;
	}

	public int boolean2int(boolean value) {
		if (value) {
			return 1;
		} else {
			return 0;
		}
	}

	public boolean int2boolean(int value) {
		if (value == 0) {
			return false;
		} else {
			return true;
		}
	}
}
