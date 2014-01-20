/*******************************************************************************
 * Copyright (c) 2013-2-28 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.foreveross.demo.sys.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dayatang.domain.InstanceFactory;
import com.foreveross.util.mybatis.plugin.Page;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2013-2-28
 */
@SuppressWarnings("serial")
public class FieldMapper implements Serializable {
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

	public FieldMapper() {
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

	//
	private static FieldMapperRepository fieldMapperRepository;

	private static FieldMapperRepository getFieldMapperRepository() {
		if (fieldMapperRepository == null) {
			fieldMapperRepository = InstanceFactory
					.getInstance(FieldMapperRepository.class);
		}
		return fieldMapperRepository;
	}

	public static FieldMapper get(String id) {
		return getFieldMapperRepository().getFieldMapper(id);
	}

	public static void remove(String id) {
		getFieldMapperRepository().deleteFieldMapper(id);
	}

	public void save() {
		if (getId() == null) {
			getFieldMapperRepository().insertFieldMapper(this);
		} else {
			getFieldMapperRepository().updateFieldMapper(this);
		}
	}

	public void remove() {
		getFieldMapperRepository().deleteFieldMapper(getId());
	}

	public static Map<String, FieldMapper> findAllToMap() {
		List<FieldMapper> list = getFieldMapperRepository()
				.findAllFieldMapper();
		Map<String, FieldMapper> map = new HashMap<String, FieldMapper>();
		for (FieldMapper fm : list) {
			if (fm.getFieldName() != null && fm.getObjectClass() != null
					&& fm.getObjectField() != null) {
				map.put(fm.getFieldName(), fm);
			}
		}
		return map;
	}

	public static Page pageFindFieldMapper(Page page, String fieldName) {
		List<FieldMapper> list = getFieldMapperRepository()
				.pageFindFieldMapper(page, fieldName);
		page.setRows(list);
		return page;
	}

	public static void removeFieldMapperByFieldName(String fieldName) {
		getFieldMapperRepository().deleteFieldMapperByFieldName(fieldName);
	}
}
