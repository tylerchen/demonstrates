/*******************************************************************************
 * Copyright (c) 2014-1-26 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.hnair.edms.common.email;

import java.util.Map;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2014-1-26
 */
public class EmailSenderApplication {
	
	/**
	 * 发送邮件，包括Text和Html形式，可以使用模板来发送
	 * <pre>
	 * 参数model与邮件相关的key描述如下：
	 * templateId: 如果设置该值则使用模板来发送
	 * emailType ：值参看EmailConstant，{TEXT, HTML, AUTO}，
	 *             如果设置该值则按该值的模式来发送邮件，
	 *             否则默认采用AUTO模式，判断第一个“有效”字符是否为“<”，
	 *             如果是则采用HTML模式发送，否则采用TEXT模式发送
	 * from      : 发送源地址
	 * to        : 发送目的地址，值如果是字符串，则邮件地址可以是使用","(逗号)或";"(分号)进行分隔，如果是字符串数组则是每个数组一个邮件地址
	 * cc        : 抄送地址，值同to
	 * bcc       : 密送地址，值同to
	 * subject   : 邮件标题
	 * text      : 邮件内容
	 * </pre>
	 * <pre>
	 * 参数model与模板相关的key描述如下，所有模板的key都应该以"template_"开头：
	 * 参数model中除了邮件相关的key，其他的key值应该是模板邮件中的填充的内容，下面是默认的模板变量key
	 * template_subject: 默认是邮件标题的变量，使用方式以Velocity的为例：${template_subject}
	 * template_text   ：默认是邮件内容的变量，使用方式以Velocity的为例：${template_text}，可以传输对象，如${template_text.username}
	 * 其他                                 : （不建议）邮件内容还需要其他的变量的，可以传其他的模板变量，以Velocity为例，
	 *                   支持对象，如template_user，使用方法${template_user.username}，不建议乱起key的名字
	 * </pre>
	 * @param model 类型Map，key包括：templateId, emailType, from, to, copyto, subject, content... 
	 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
	 * @since 2014-1-26
	 */
	public static void send(Map<String, Object> model) {

	}
	
	/**
	 * 使用模板发送邮件
	 * @param model 参见方法send的描述
	 * @param templateId
	 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
	 * @since 2014-1-26
	 */
	public static void sendWithTemplate(Map<String, Object> model, String templateId) {

	}

	/**
	 * 使用文本方式发送邮件
	 * @param from    发送源地址
	 * @param to      发送目的地址，值如果是字符串，则邮件地址可以是使用","(逗号)或";"(分号)进行分隔
	 * @param cc      抄送地址，值同to
	 * @param bcc     密送地址，值同to
	 * @param subject 邮件标题
	 * @param text 邮件内容
	 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
	 * @since 2014-1-26
	 */
	public static void sendText(String from, String to, String cc, String bcc,
			String subject, String text) {

	}

	/**
	 * 使用超文本HTML方式发送邮件
	 * @param from    发送源地址
	 * @param to      发送目的地址，值如果是字符串，则邮件地址可以是使用","(逗号)或";"(分号)进行分隔
	 * @param cc      抄送地址，值同to
	 * @param bcc     密送地址，值同to
	 * @param subject 邮件标题
	 * @param text 邮件内容
	 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
	 * @since 2014-1-26
	 */
	public static void sendHtml(String from, String to, String cc, String bcc,
			String subject, String text) {

	}

	/**
	 * 使用模板发送文本邮件
	 * @param model 参见方法send的描述
	 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
	 * @since 2014-1-26
	 */
	public static void sendTextWithTemplate(Map<String, Object> model) {

	}

	/**
	 * 使用模板发送超文本HTML邮件
	 * @param model 参见方法send的描述
	 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
	 * @since 2014-1-26
	 */
	public static void sendHtmlWithTemplate(Map<String, Object> model) {

	}
}
