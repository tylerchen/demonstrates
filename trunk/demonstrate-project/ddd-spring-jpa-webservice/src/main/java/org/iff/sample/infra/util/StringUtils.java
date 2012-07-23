package org.iff.sample.infra.util;


public class StringUtils {
	
	
	/*
	 * 判断字段是否为null或者为空
	 */
	public static boolean IsNotEmpty(String str){
		boolean flag=false;
		if(null!=str&&!(str.trim()).equals("")){
			flag=true;
		}
		return flag;
	}

}
