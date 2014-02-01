/*******************************************************************************
 * Copyright (c) 2014-1-27 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.hnair.edms.common.dictionary.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2014-1-27
 */
@SuppressWarnings("serial")
public class SysDictionaryVO implements Serializable {

	private String id;
	private String type;
	private String typeCname;
	private String typeEname;
	private String code;
	private String codeCname;
	private String codeEname;
	private String status;
	private String remark;
	private Date updateTime;
	private String updateUser;
	// getter and setter
}
