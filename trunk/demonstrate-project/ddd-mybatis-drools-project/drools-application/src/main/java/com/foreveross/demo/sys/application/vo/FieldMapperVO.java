/*******************************************************************************
 * Copyright (c) 2013-2-28 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.foreveross.demo.sys.application.vo;

import java.io.Serializable;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2013-2-28
 */
@SuppressWarnings("serial")
public class FieldMapperVO implements Serializable {
	private String id;
	//
	private String fieldName;
	//
	private String objectClass;
	private String objectField;
	private String objectName;
	//
	private String mappedClass;
	private String mappedField;
	private String mappedName;
	//
	private String dbTable;
	private String dbCoulmn;
	private String dbName;

	public FieldMapperVO() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getObjectClass() {
		return objectClass;
	}

	public void setObjectClass(String objectClass) {
		this.objectClass = objectClass;
	}

	public String getObjectField() {
		return objectField;
	}

	public void setObjectField(String objectField) {
		this.objectField = objectField;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public String getMappedClass() {
		return mappedClass;
	}

	public void setMappedClass(String mappedClass) {
		this.mappedClass = mappedClass;
	}

	public String getMappedField() {
		return mappedField;
	}

	public void setMappedField(String mappedField) {
		this.mappedField = mappedField;
	}

	public String getMappedName() {
		return mappedName;
	}

	public void setMappedName(String mappedName) {
		this.mappedName = mappedName;
	}

	public String getDbTable() {
		return dbTable;
	}

	public void setDbTable(String dbTable) {
		this.dbTable = dbTable;
	}

	public String getDbCoulmn() {
		return dbCoulmn;
	}

	public void setDbCoulmn(String dbCoulmn) {
		this.dbCoulmn = dbCoulmn;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	@Override
	public String toString() {
		return "FieldMapperVO [dbCoulmn=" + dbCoulmn + ", dbName=" + dbName
				+ ", dbTable=" + dbTable + ", fieldName=" + fieldName + ", id="
				+ id + ", mappedClass=" + mappedClass + ", mappedField="
				+ mappedField + ", mappedName=" + mappedName + ", objectClass="
				+ objectClass + ", objectField=" + objectField
				+ ", objectName=" + objectName + "]";
	}

}
