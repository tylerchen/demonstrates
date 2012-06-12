/*******************************************************************************
 * Copyright (c) 2012-3-12 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package org.iff.demo.dddallinone.dto;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2012-3-12
 */
@SuppressWarnings("restriction")
@javax.xml.bind.annotation.XmlAccessorType(javax.xml.bind.annotation.XmlAccessType.PROPERTY)
@javax.xml.bind.annotation.XmlType(name = "dto")
public class EJBFacadeDTO implements Serializable {

	private static final long serialVersionUID = -8704677125421949693L;

	private String interfaceName;
	private String beanName;
	private String methodName;
	//@javax.xml.bind.annotation.XmlElementWrapper(name = "parameters")
	private Object[] parameters;
	private Object result;
	private String adapterType;
	private String parametersString;
	private String resultString;

	public EJBFacadeDTO() {
	}

	public EJBFacadeDTO(String interfaceName, String beanName,
			String methodName, Object[] parameters) {
		this.interfaceName = interfaceName;
		this.beanName = beanName;
		this.methodName = methodName;
		this.parameters = parameters;
		init();
	}

	public EJBFacadeDTO(String interfaceName, String beanName,
			String methodName, String adapterType, String parametersString) {
		this.interfaceName = interfaceName;
		this.beanName = beanName;
		this.methodName = methodName;
		this.adapterType = adapterType;
		this.parametersString = parametersString;
		init();
	}

	public EJBFacadeDTO(Object result) {
		this.result = result;
	}

	public EJBFacadeDTO(String adapterType, String resultString) {
		this.adapterType = adapterType;
		this.resultString = resultString;
	}

	private void init() {
		if (this.interfaceName == null || this.interfaceName.length() < 1) {
			throw new IllegalArgumentException("[interfaceName] is required.");
		}
		if (this.beanName == null || this.beanName.length() < 1) {
			throw new IllegalArgumentException("[beanName] is required.");
		}
		if (this.methodName == null || this.methodName.length() < 1) {
			throw new IllegalArgumentException("[methodName] is required.");
		}
		this.parameters = this.parameters == null ? new Object[0]
				: this.parameters;
	}

	public String getAdapterType() {
		return adapterType;
	}

	public void setAdapterType(String adapterType) {
		this.adapterType = adapterType;
	}

	public String getParametersString() {
		return parametersString;
	}

	public void setParametersString(String parametersString) {
		this.parametersString = parametersString;
	}

	public String getResultString() {
		return resultString;
	}

	public void setResultString(String resultString) {
		this.resultString = resultString;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Object[] getParameters() {
		return parameters;
	}

	public void setParameters(Object[] parameters) {
		this.parameters = parameters;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public String toString() {
		return "EJBFacadeDTO [beanName=" + beanName + ", methodName="
				+ methodName + ", parameters=" + Arrays.toString(parameters)
				+ ", result=" + result + "]";
	}
}
