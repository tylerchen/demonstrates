package com.dayatang.auth.core;

import com.dayatang.i18n.impl.ResourceBundleI18nService;
import com.dayatang.i18n.support.I18nServiceAccessor;


public class AuthResourceBundleI18nService extends ResourceBundleI18nService {

	public AuthResourceBundleI18nService() {
		setBasename("com.redhat.auth.messages");
		
	}

	// ~ Methods
	// ========================================================================================================

	private static I18nServiceAccessor accessor;
	private static I18nServiceAccessor getAccessor() {
		if(accessor==null)
			accessor = new I18nServiceAccessor(new AuthResourceBundleI18nService());
		return accessor;
	}
	
	public static String getMessage(String code, String defaultMessage){
		return getAccessor().getMessage(code,defaultMessage);
	}
}
