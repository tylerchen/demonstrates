/*******************************************************************************
 * Copyright (c) 2014-1-27 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.hnair.edms.common.dictionary;

import java.util.List;

import com.hnair.edms.common.dictionary.vo.SysDictionaryVO;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2014-1-27
 */
public interface SysDictionaryApplication {

	/**
	 * 按字典类型代码查找该类型的字典代码的信息
	 * @param type 字典类型代码
	 * @return List<SysDictionaryVO>
	 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
	 * @since 2014-1-27
	 */
	List<SysDictionaryVO> findSysDictionaryByType(String type);

	/**
	 * 按字典类型代码和字典代码查找字典代码信息
	 * @param type 字典类型代码
	 * @param code 字典代码
	 * @return SysDictionaryVO
	 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
	 * @since 2014-1-27
	 */
	SysDictionaryVO findSysDictionaryByTypeAndCode(String type, String code);
	
	/**
	 * 添加一个系统字典代码，如果该字典类型不存在，需要同时保存该字典类型
	 * @param sysDictionaryVO
	 * @return
	 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
	 * @since 2014-1-27
	 */
	SysDictionaryVO addSysDictionary(SysDictionaryVO sysDictionaryVO);
	
	/**
	 * 删除一个“非系统默认”的系统字典代码
	 * @param type 系统字典类型代码
	 * @param code 系统字典代码
	 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
	 * @since 2014-1-27
	 */
	void removeSysDictionary(String type, String code);
}
