/*******************************************************************************
 * Copyright (c) 2012-5-5 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package org.iff.sample.application.core;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.velocity.VelocityEngineUtils;

/**
 * mail sender service
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2012-5-5
 */
@SuppressWarnings("unchecked")
@Named("mailSenderService")
public class MailSenderService {
	@Inject
	JavaMailSender mailSender;
	@Inject
	VelocityEngine velocityEngine;
	@Inject
	SimpleMailMessage simpleMailMessage;
	//@Inject
	String templateName;
	//@Inject
	boolean validate = true;
	//
	Map map = new HashMap();
	//
	String to;
	String subject;

	/**
	 * send by template
	 * @param model
	 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
	 * @since 2012-5-6
	 */
	public void sendWithTemplate(Map model) {
		model = settingModel(model);
		{
			try {
				String result = VelocityEngineUtils.mergeTemplateIntoString(
						velocityEngine, templateName, "UTF-8", model);
				model.put("text", result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		settingMailMessage(model, simpleMailMessage);
		mailSender.send(simpleMailMessage);
	}

	/**
	 * send by template
	 * @param model
	 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
	 * @since 2012-5-6
	 */
	public void sendWithHtmlTemplate(Map model) {
		model = settingModel(model);
		{
			try {
				String result = VelocityEngineUtils.mergeTemplateIntoString(
						velocityEngine, templateName, "UTF-8", model);
				model.put("text", result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		{
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
			settingMailMessage(model, messageHelper);
		}
		mailSender.send(mimeMessage);
	}

	/**
	 * send plain text
	 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
	 * @since 2012-5-6
	 */
	public void sendText(Map model) {
		model = settingModel(model);
		settingMailMessage(model, simpleMailMessage);
		mailSender.send(simpleMailMessage);
	}

	/**
	 * send html
	 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
	 * @since 2012-5-6
	 */
	public void sendHtml(Map model) {
		model = settingModel(model);
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		{
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
			settingMailMessage(model, messageHelper);
		}
		mailSender.send(mimeMessage);
	}

	//	/**
	//	 * send html with picture
	//	 * @param imagePath
	//	 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
	//	 * @since 2012-5-6
	//	 */
	//	public void sendHtmlWithImage(String to, String subject, String content,
	//			String imagePath) {
	//		MimeMessage mimeMessage = mailSender.createMimeMessage();
	//		try {
	//			MimeMessageHelper messageHelper = new MimeMessageHelper(
	//					mimeMessage, true);
	//			messageHelper.setTo(to);
	//			messageHelper.setFrom(simpleMailMessage.getFrom());
	//			messageHelper.setSubject(subject);
	//			messageHelper.setText(content, true);
	//			//Content="<html><head></head><body><img src=\"cid:image\"/></body></html>";
	//			//picture must like: <img src='cid:image'/>
	//			FileSystemResource img = new FileSystemResource(new File(imagePath));
	//			messageHelper.addInline("image", img);
	//		} catch (MessagingException e) {
	//			e.printStackTrace();
	//		}
	//		mailSender.send(mimeMessage);
	//	}
	//
	//	/**
	//	 * send html with attachment
	//	 * @param filePath
	//	 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
	//	 * @since 2012-5-6
	//	 */
	//	public void sendHtmlWithAttachment(String to, String subject,
	//			String content, String filePath) {
	//		MimeMessage mimeMessage = mailSender.createMimeMessage();
	//		try {
	//			MimeMessageHelper messageHelper = new MimeMessageHelper(
	//					mimeMessage, true);
	//			messageHelper.setTo(to);
	//			messageHelper.setFrom(simpleMailMessage.getFrom());
	//			messageHelper.setSubject(subject);
	//			messageHelper.setText(content, true);
	//			FileSystemResource file = new FileSystemResource(new File(filePath));
	//			messageHelper.addAttachment(file.getFilename(), file);
	//		} catch (MessagingException e) {
	//			e.printStackTrace();
	//		}
	//		mailSender.send(mimeMessage);
	//	}

	private void settingMailMessage(Map model, MailMessage mailMessage) {
		mailMessage.setTo((String) model.get("to"));// mailto
		mailMessage.setFrom((String) model.get("from")); //send from
		mailMessage.setSubject((String) model.get("subject"));
		mailMessage.setText((String) model.get("text"));
	}

	private void settingMailMessage(Map model, MimeMessageHelper messageHelper) {
		try {
			messageHelper.setTo((String) model.get("to"));// mailto
			messageHelper.setFrom((String) model.get("from")); //send from
			messageHelper.setSubject((String) model.get("subject"));
			messageHelper.setText((String) model.get("text"), true);
			//			messageHelper.getMimeMessage().setContent(
			//					(String) model.get("text"), "text/html");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Map settingModel(Map model) {
		if (model == null) {
			model = new HashMap();
		}
		if (map == null) {
			map = new HashMap();
		}
		{
			Map temp = new HashMap(map);
			{
				temp.put("from", simpleMailMessage.getFrom());
				temp.put("to", to);
				temp.put("subject", subject);
			}
			temp.putAll(model);
			model.clear();
			model.putAll(temp);
			temp.clear();
		}
		return model;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public void setValidate(boolean validate) {
		this.validate = validate;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String toString() {
		return "MailSenderServiceImpl [mailSender=" + mailSender + ", map="
				+ map + ", simpleMailMessage=" + simpleMailMessage
				+ ", templateName=" + templateName + ", validate=" + validate
				+ ", velocityEngine=" + velocityEngine + "]";
	}

}
