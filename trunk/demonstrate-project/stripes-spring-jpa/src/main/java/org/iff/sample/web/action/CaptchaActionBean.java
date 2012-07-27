package org.iff.sample.web.action;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import javax.annotation.security.PermitAll;
import javax.imageio.ImageIO;

import org.iff.sample.framework.ext.CaptchaManager;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.HttpCache;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StreamingResolution;
import net.sourceforge.stripes.action.StrictBinding;
import net.sourceforge.stripes.action.UrlBinding;


@StrictBinding
@HttpCache(allow = false)
@UrlBinding("/action/verifycode")
@PermitAll
public class CaptchaActionBean extends BaseActionBean {

	@DontValidate
	@DefaultHandler
	@HttpCache(allow = false)
	public Resolution display() {
		byte[] captchaChallengeAsJpeg = null;
		ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
		try {
			String captchaId = getContext().getRequest().getSession().getId();
			BufferedImage challenge = CaptchaManager.get()
					.getImageChallengeForID(captchaId);
			ImageIO.write(challenge, "jpeg", jpegOutputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
		captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
		return new StreamingResolution("image/jpeg", new ByteArrayInputStream(
				captchaChallengeAsJpeg));
	}
}