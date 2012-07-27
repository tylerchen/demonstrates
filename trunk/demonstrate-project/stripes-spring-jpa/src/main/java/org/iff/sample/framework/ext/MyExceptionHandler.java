/**
 * Default application error handler. Better than seeing Tomcat error.
 * Joaquin Valdez
 *
 */
package org.iff.sample.framework.ext;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sourceforge.stripes.action.ErrorResolution;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.controller.StripesFilter;
import net.sourceforge.stripes.exception.ActionBeanNotFoundException;
import net.sourceforge.stripes.exception.DefaultExceptionHandler;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.integration.spring.SpringHelper;

import org.apache.log4j.Logger;
import org.iff.sample.application.core.MailSenderService;

public class MyExceptionHandler extends DefaultExceptionHandler {

	private static final Logger logger = Logger
			.getLogger(MyExceptionHandler.class);
	private static final String VIEW = "/WEB-INF/jsp/exceptions/exception.jsp";
	private static String EXCEPTION_CLASSES = null;

	@SpringBean("errorMail")
	MailSenderService errorMail;

	public Resolution catchActionBeanNotFound(ActionBeanNotFoundException exc,
			HttpServletRequest req, HttpServletResponse resp) {
		return new ErrorResolution(HttpServletResponse.SC_NOT_FOUND);
	}

	@SuppressWarnings("unchecked")
	public Resolution catchAll(Throwable exc, HttpServletRequest req,
			HttpServletResponse resp) {
		if (errorMail == null) {
			SpringHelper.injectBeans(this, StripesFilter.getConfiguration()
					.getServletContext());
		}
		{
			if (logger.isDebugEnabled()) {
				req.setAttribute("errors", getStackTrace(exc));
			}
		}
		if (EXCEPTION_CLASSES == null) {
			EXCEPTION_CLASSES = AppConfig.getProperty(
					AppConfig.MAIL_ERROR_EXCLUDE_EXCEPTION, "");
		}
		if (EXCEPTION_CLASSES.indexOf(exc.getClass().getName()) < 0) {
			// Send email
			try {
				Map model = new HashMap();
				{
					model.put("subject", "error:" + getLastUrl(req));
					model.put("url", getLastUrl(req));
					model.put("message", getStackTrace(exc));
				}
				//errorMail.sendWithTemplate(model);
			} catch (Throwable e) {
				logger.error("Trouble sending mail: " + e.getMessage());
			}
		} else {
			logger
					.debug("NOT sending error mail, the exception class "
							+ exc.getClass().getName()
							+ " configured in property mail.error.exclude.exception of file app.properties.");
		}
		exc.printStackTrace();
		return new ForwardResolution(VIEW);
	}

	public static String getStackTrace(Throwable aThrowable) {
		final Writer result = new StringWriter();
		final PrintWriter printWriter = new PrintWriter(result);
		aThrowable.printStackTrace(printWriter);
		return result.toString();
	}

	@SuppressWarnings("unchecked")
	private String getLastUrl(HttpServletRequest req) {
		StringBuilder sb = new StringBuilder();

		// Start with the URI and the path
		String uri = (String) req
				.getAttribute("javax.servlet.forward.request_uri");
		String path = (String) req
				.getAttribute("javax.servlet.forward.path_info");
		if (uri == null) {
			uri = req.getRequestURI();
			path = req.getPathInfo();
		}
		sb.append(uri);
		if (path != null) {
			sb.append(path);
		}

		// Now the request parameters
		sb.append('?');
		Map<String, String[]> map = new HashMap<String, String[]>(req
				.getParameterMap());

		// Remove previous locale parameter, if present.
		map.remove(MyLocalePicker.LOCALE);

		// Append the parameters to the URL
		for (String key : map.keySet()) {
			String[] values = map.get(key);
			for (String value : values) {
				sb.append(key).append('=').append(value).append('&');
			}
		}
		// Remove the last '&'
		sb.deleteCharAt(sb.length() - 1);

		return sb.toString();
	}
}
