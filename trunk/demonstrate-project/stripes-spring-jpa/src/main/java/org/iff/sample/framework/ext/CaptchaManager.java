package org.iff.sample.framework.ext;

import com.octo.captcha.service.image.DefaultManageableImageCaptchaService;
import com.octo.captcha.service.image.ImageCaptchaService;

public class CaptchaManager {
	private static ImageCaptchaService imageCaptchaService = new DefaultManageableImageCaptchaService();

	public static ImageCaptchaService get() {
		return imageCaptchaService;
	}
}