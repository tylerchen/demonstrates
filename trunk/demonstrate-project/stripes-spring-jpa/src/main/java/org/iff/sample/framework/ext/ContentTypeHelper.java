package org.iff.sample.framework.ext;

import java.util.HashMap;
import java.util.Map;

public class ContentTypeHelper {

	private static final Map<String, String> contentTypeMap = new HashMap<String, String>();

	static {
		contentTypeMap.put(".*", "application/octet-stream");

		contentTypeMap.put(".001", "application/x-001");

		contentTypeMap.put(".301", "application/x-301");

		contentTypeMap.put(".323", "text/h323");

		contentTypeMap.put(".906", "application/x-906");

		contentTypeMap.put(".907", "drawing/907");

		contentTypeMap.put(".a11", "application/x-a11");

		contentTypeMap.put(".acp", "audio/x-mei-aac");

		contentTypeMap.put(".ai", "application/postscript");

		contentTypeMap.put(".aif", "audio/aiff");

		contentTypeMap.put(".aifc", "audio/aiff");

		contentTypeMap.put(".aiff", "audio/aiff");

		contentTypeMap.put(".anv", "application/x-anv");

		contentTypeMap.put(".asa", "text/asa");

		contentTypeMap.put(".asf", "video/x-ms-asf");

		contentTypeMap.put(".asp", "text/asp");

		contentTypeMap.put(".asx", "video/x-ms-asf");

		contentTypeMap.put(".au", "audio/basic");

		contentTypeMap.put(".avi", "video/avi");

		contentTypeMap.put(".awf", "application/vnd.adobe.workflow");

		contentTypeMap.put(".biz", "text/xml");

		contentTypeMap.put(".bmp", "application/x-bmp");

		contentTypeMap.put(".bot", "application/x-bot");

		contentTypeMap.put(".c4t", "application/x-c4t");

		contentTypeMap.put(".c90", "application/x-c90");

		contentTypeMap.put(".cal", "application/x-cals");

		contentTypeMap.put(".cat", "application/vnd.ms-pki.seccat");

		contentTypeMap.put(".cdf", "application/x-netcdf");

		contentTypeMap.put(".cdr", "application/x-cdr");

		contentTypeMap.put(".cel", "application/x-cel");

		contentTypeMap.put(".cer", "application/x-x509-ca-cert");

		contentTypeMap.put(".cg4", "application/x-g4");

		contentTypeMap.put(".cgm", "application/x-cgm");

		contentTypeMap.put(".cit", "application/x-cit");

		contentTypeMap.put(".class", "java/*");

		contentTypeMap.put(".cml", "text/xml");

		contentTypeMap.put(".cmp", "application/x-cmp");

		contentTypeMap.put(".cmx", "application/x-cmx");

		contentTypeMap.put(".cot", "application/x-cot");

		contentTypeMap.put(".crl", "application/pkix-crl");

		contentTypeMap.put(".crt", "application/x-x509-ca-cert");

		contentTypeMap.put(".csi", "application/x-csi");

		contentTypeMap.put(".css", "text/css");

		contentTypeMap.put(".cut", "application/x-cut");

		contentTypeMap.put(".dbf", "application/x-dbf");

		contentTypeMap.put(".dbm", "application/x-dbm");

		contentTypeMap.put(".dbx", "application/x-dbx");

		contentTypeMap.put(".dcd", "text/xml");

		contentTypeMap.put(".dcx", "application/x-dcx");

		contentTypeMap.put(".der", "application/x-x509-ca-cert");

		contentTypeMap.put(".dgn", "application/x-dgn");

		contentTypeMap.put(".dib", "application/x-dib");

		contentTypeMap.put(".dll", "application/x-msdownload");

		contentTypeMap.put(".doc", "application/msword");

		contentTypeMap.put(".dot", "application/msword");

		contentTypeMap.put(".drw", "application/x-drw");

		contentTypeMap.put(".dtd", "text/xml");

		contentTypeMap.put(".dwf", "Model/vnd.dwf");

		contentTypeMap.put(".dwg", "application/x-dwg");

		contentTypeMap.put(".dxb", "application/x-dxb");

		contentTypeMap.put(".dxf", "application/x-dxf");

		contentTypeMap.put(".edn", "application/vnd.adobe.edn");

		contentTypeMap.put(".emf", "application/x-emf");

		contentTypeMap.put(".eml", "message/rfc822");

		contentTypeMap.put(".ent", "text/xml");

		contentTypeMap.put(".epi", "application/x-epi");

		contentTypeMap.put(".eps", "application/postscript");

		contentTypeMap.put(".etd", "application/x-ebx");

		contentTypeMap.put(".exe", "application/x-msdownload");

		contentTypeMap.put(".fax", "image/fax");

		contentTypeMap.put(".fdf", "application/vnd.fdf");

		contentTypeMap.put(".fif", "application/fractals");

		contentTypeMap.put(".fo", "text/xml");

		contentTypeMap.put(".frm", "application/x-frm");

		contentTypeMap.put(".g4", "application/x-g4");

		contentTypeMap.put(".gbr", "application/x-gbr");

		contentTypeMap.put(".gcd", "application/x-gcd");

		contentTypeMap.put(".gif", "image/gif");

		contentTypeMap.put(".gl2", "application/x-gl2");

		contentTypeMap.put(".gp4", "application/x-gp4");

		contentTypeMap.put(".hgl", "application/x-hgl");

		contentTypeMap.put(".hmr", "application/x-hmr");

		contentTypeMap.put(".hpg", "application/x-hpgl");

		contentTypeMap.put(".hpl", "application/x-hpl");

		contentTypeMap.put(".hqx", "application/mac-binhex40");

		contentTypeMap.put(".hrf", "application/x-hrf");

		contentTypeMap.put(".hta", "application/hta");

		contentTypeMap.put(".htc", "text/x-component");

		contentTypeMap.put(".htm", "text/html");

		contentTypeMap.put(".html", "text/html");

		contentTypeMap.put(".htt", "text/webviewhtml");

		contentTypeMap.put(".htx", "text/html");

		contentTypeMap.put(".icb", "application/x-icb");

		contentTypeMap.put(".ico", "image/x-icon");

		contentTypeMap.put(".iff", "application/x-iff");

		contentTypeMap.put(".ig4", "application/x-g4");

		contentTypeMap.put(".igs", "application/x-igs");

		contentTypeMap.put(".iii", "application/x-iphone");

		contentTypeMap.put(".img", "application/x-img");

		contentTypeMap.put(".ins", "application/x-internet-signup");

		contentTypeMap.put(".isp", "application/x-internet-signup");

		contentTypeMap.put(".IVF", "video/x-ivf");

		contentTypeMap.put(".java", "java/*");

		contentTypeMap.put(".jfif", "image/jpeg");

		contentTypeMap.put(".jpe", "image/jpeg");

		contentTypeMap.put(".jpeg", "image/jpeg");

		contentTypeMap.put(".jpg", "image/jpeg");

		contentTypeMap.put(".js", "application/x-javascript");

		contentTypeMap.put(".jsp", "text/html");

		contentTypeMap.put(".la1", "audio/x-liquid-file");

		contentTypeMap.put(".lar", "application/x-laplayer-reg");

		contentTypeMap.put(".latex", "application/x-latex");

		contentTypeMap.put(".lavs", "audio/x-liquid-secure");

		contentTypeMap.put(".lbm", "application/x-lbm");

		contentTypeMap.put(".lmsff", "audio/x-la-lms");

		contentTypeMap.put(".ls", "application/x-javascript");

		contentTypeMap.put(".ltr", "application/x-ltr");

		contentTypeMap.put(".m1v", "video/x-mpeg");

		contentTypeMap.put(".m2v", "video/x-mpeg");

		contentTypeMap.put(".m3u", "audio/mpegurl");

		contentTypeMap.put(".m4e", "video/mpeg4");

		contentTypeMap.put(".mac", "application/x-mac");

		contentTypeMap.put(".man", "application/x-troff-man");

		contentTypeMap.put(".math", "text/xml");

		contentTypeMap.put(".mdb", "application/msaccess");

		contentTypeMap.put(".mfp", "application/x-shockwave-flash");

		contentTypeMap.put(".mht", "message/rfc822");

		contentTypeMap.put(".mhtml", "message/rfc822");

		contentTypeMap.put(".mi", "application/x-mi");

		contentTypeMap.put(".mid", "audio/mid");

		contentTypeMap.put(".midi", "audio/mid");

		contentTypeMap.put(".mil", "application/x-mil");

		contentTypeMap.put(".mml", "text/xml");

		contentTypeMap.put(".mnd", "audio/x-musicnet-download");

		contentTypeMap.put(".mns", "audio/x-musicnet-stream");

		contentTypeMap.put(".mocha", "application/x-javascript");

		contentTypeMap.put(".movie", "video/x-sgi-movie");

		contentTypeMap.put(".mp1", "audio/mp1");

		contentTypeMap.put(".mp2", "audio/mp2");

		contentTypeMap.put(".mp2v", "video/mpeg");

		contentTypeMap.put(".mp3", "audio/mp3");

		contentTypeMap.put(".mp4", "video/mpeg4");

		contentTypeMap.put(".mpa", "video/x-mpg");

		contentTypeMap.put(".mpd", "application/vnd.ms-project");

		contentTypeMap.put(".mpe", "video/x-mpeg");

		contentTypeMap.put(".mpeg", "video/mpg");

		contentTypeMap.put(".mpg", "video/mpg");

		contentTypeMap.put(".mpga", "audio/rn-mpeg");

		contentTypeMap.put(".mpp", "application/vnd.ms-project");

		contentTypeMap.put(".mps", "video/x-mpeg");

		contentTypeMap.put(".mpt", "application/vnd.ms-project");

		contentTypeMap.put(".mpv", "video/mpg");

		contentTypeMap.put(".mpv2", "video/mpeg");

		contentTypeMap.put(".mpw", "application/vnd.ms-project");

		contentTypeMap.put(".mpx", "application/vnd.ms-project");

		contentTypeMap.put(".mtx", "text/xml");

		contentTypeMap.put(".mxp", "application/x-mmxp");

		contentTypeMap.put(".net", "image/pnetvue");

		contentTypeMap.put(".nrf", "application/x-nrf");

		contentTypeMap.put(".nws", "message/rfc822");

		contentTypeMap.put(".odc", "text/x-ms-odc");

		contentTypeMap.put(".out", "application/x-out");

		contentTypeMap.put(".p10", "application/pkcs10");

		contentTypeMap.put(".p12", "application/x-pkcs12");

		contentTypeMap.put(".p7b", "application/x-pkcs7-certificates");

		contentTypeMap.put(".p7c", "application/pkcs7-mime");

		contentTypeMap.put(".p7m", "application/pkcs7-mime");

		contentTypeMap.put(".p7r", "application/x-pkcs7-certreqresp");

		contentTypeMap.put(".p7s", "application/pkcs7-signature");

		contentTypeMap.put(".pc5", "application/x-pc5");

		contentTypeMap.put(".pci", "application/x-pci");

		contentTypeMap.put(".pcl", "application/x-pcl");

		contentTypeMap.put(".pcx", "application/x-pcx");

		contentTypeMap.put(".pdf", "application/pdf");

		contentTypeMap.put(".pdx", "application/vnd.adobe.pdx");

		contentTypeMap.put(".pfx", "application/x-pkcs12");

		contentTypeMap.put(".pgl", "application/x-pgl");

		contentTypeMap.put(".pic", "application/x-pic");

		contentTypeMap.put(".pko", "application/vnd.ms-pki.pko");

		contentTypeMap.put(".pl", "application/x-perl");

		contentTypeMap.put(".plg", "text/html");

		contentTypeMap.put(".pls", "audio/scpls");

		contentTypeMap.put(".plt", "application/x-plt");

		contentTypeMap.put(".png", "image/png");

		contentTypeMap.put(".pot", "application/vnd.ms-powerpoint");

		contentTypeMap.put(".ppa", "application/vnd.ms-powerpoint");

		contentTypeMap.put(".ppm", "application/x-ppm");

		contentTypeMap.put(".pps", "application/vnd.ms-powerpoint");

		contentTypeMap.put(".ppt", "application/vnd.ms-powerpoint");

		contentTypeMap.put(".pr", "application/x-pr");

		contentTypeMap.put(".prf", "application/pics-rules");

		contentTypeMap.put(".prn", "application/x-prn");

		contentTypeMap.put(".prt", "application/x-prt");

		contentTypeMap.put(".ps", "application/postscript");

		contentTypeMap.put(".ptn", "application/x-ptn");

		contentTypeMap.put(".pwz", "application/vnd.ms-powerpoint");

		contentTypeMap.put(".r3t", "text/vnd.rn-realtext3d");

		contentTypeMap.put(".ra", "audio/vnd.rn-realaudio");

		contentTypeMap.put(".ram", "audio/x-pn-realaudio");

		contentTypeMap.put(".ras", "application/x-ras");

		contentTypeMap.put(".rat", "application/rat-file");

		contentTypeMap.put(".rdf", "text/xml");

		contentTypeMap.put(".rec", "application/vnd.rn-recording");

		contentTypeMap.put(".red", "application/x-red");

		contentTypeMap.put(".rgb", "application/x-rgb");

		contentTypeMap.put(".rjs", "application/vnd.rn-realsystem-rjs");

		contentTypeMap.put(".rjt", "application/vnd.rn-realsystem-rjt");

		contentTypeMap.put(".rlc", "application/x-rlc");

		contentTypeMap.put(".rle", "application/x-rle");

		contentTypeMap.put(".rm", "application/vnd.rn-realmedia");

		contentTypeMap.put(".rmf", "application/vnd.adobe.rmf");

		contentTypeMap.put(".rmi", "audio/mid");

		contentTypeMap.put(".rmj", "application/vnd.rn-realsystem-rmj");

		contentTypeMap.put(".rmm", "audio/x-pn-realaudio");

		contentTypeMap.put(".rmp", "application/vnd.rn-rn_music_package");

		contentTypeMap.put(".rms", "application/vnd.rn-realmedia-secure");

		contentTypeMap.put(".rmvb", "application/vnd.rn-realmedia-vbr");

		contentTypeMap.put(".rmx", "application/vnd.rn-realsystem-rmx");

		contentTypeMap.put(".rnx", "application/vnd.rn-realplayer");

		contentTypeMap.put(".rp", "image/vnd.rn-realpix");

		contentTypeMap.put(".rpm", "audio/x-pn-realaudio-plugin");

		contentTypeMap.put(".rsml", "application/vnd.rn-rsml");

		contentTypeMap.put(".rt", "text/vnd.rn-realtext");

		contentTypeMap.put(".rtf", "application/msword");

		contentTypeMap.put(".rv", "video/vnd.rn-realvideo");

		contentTypeMap.put(".sam", "application/x-sam");

		contentTypeMap.put(".sat", "application/x-sat");

		contentTypeMap.put(".sdp", "application/sdp");

		contentTypeMap.put(".sdw", "application/x-sdw");

		contentTypeMap.put(".sit", "application/x-stuffit");

		contentTypeMap.put(".slb", "application/x-slb");

		contentTypeMap.put(".sld", "application/x-sld");

		contentTypeMap.put(".slk", "drawing/x-slk");

		contentTypeMap.put(".smi", "application/smil");

		contentTypeMap.put(".smil", "application/smil");

		contentTypeMap.put(".smk", "application/x-smk");

		contentTypeMap.put(".snd", "audio/basic");

		contentTypeMap.put(".sol", "text/plain");

		contentTypeMap.put(".sor", "text/plain");

		contentTypeMap.put(".spc", "application/x-pkcs7-certificates");

		contentTypeMap.put(".spl", "application/futuresplash");

		contentTypeMap.put(".spp", "text/xml");

		contentTypeMap.put(".ssm", "application/streamingmedia");

		contentTypeMap.put(".sst", "application/vnd.ms-pki.certstore");

		contentTypeMap.put(".stl", "application/vnd.ms-pki.stl");

		contentTypeMap.put(".stm", "text/html");

		contentTypeMap.put(".sty", "application/x-sty");

		contentTypeMap.put(".svg", "text/xml");

		contentTypeMap.put(".swf", "application/x-shockwave-flash");

		contentTypeMap.put(".tdf", "application/x-tdf");

		contentTypeMap.put(".tg4", "application/x-tg4");

		contentTypeMap.put(".tga", "application/x-tga");

		contentTypeMap.put(".tif", "image/tiff");

		contentTypeMap.put(".tiff", "image/tiff");

		contentTypeMap.put(".tld", "text/xml");

		contentTypeMap.put(".top", "drawing/x-top");

		contentTypeMap.put(".torrent", "application/x-bittorrent");

		contentTypeMap.put(".tsd", "text/xml");

		contentTypeMap.put(".txt", "text/plain");

		contentTypeMap.put(".uin", "application/x-icq");

		contentTypeMap.put(".uls", "text/iuls");

		contentTypeMap.put(".vcf", "text/x-vcard");

		contentTypeMap.put(".vda", "application/x-vda");

		contentTypeMap.put(".vdx", "application/vnd.visio");

		contentTypeMap.put(".vml", "text/xml");

		contentTypeMap.put(".vpg", "application/x-vpeg005");

		contentTypeMap.put(".vsd", "application/vnd.visio");

		contentTypeMap.put(".vss", "application/vnd.visio");

		contentTypeMap.put(".vst", "application/vnd.visio");

		contentTypeMap.put(".vsw", "application/vnd.visio");

		contentTypeMap.put(".vsx", "application/vnd.visio");

		contentTypeMap.put(".vtx", "application/vnd.visio");

		contentTypeMap.put(".vxml", "text/xml");

		contentTypeMap.put(".wav", "audio/wav");

		contentTypeMap.put(".wax", "audio/x-ms-wax");

		contentTypeMap.put(".wb1", "application/x-wb1");

		contentTypeMap.put(".wb2", "application/x-wb2");

		contentTypeMap.put(".wb3", "application/x-wb3");

		contentTypeMap.put(".wbmp", "image/vnd.wap.wbmp");

		contentTypeMap.put(".wiz", "application/msword");

		contentTypeMap.put(".wk3", "application/x-wk3");

		contentTypeMap.put(".wk4", "application/x-wk4");

		contentTypeMap.put(".wkq", "application/x-wkq");

		contentTypeMap.put(".wks", "application/x-wks");

		contentTypeMap.put(".wm", "video/x-ms-wm");

		contentTypeMap.put(".wma", "audio/x-ms-wma");

		contentTypeMap.put(".wmd", "application/x-ms-wmd");

		contentTypeMap.put(".wmf", "application/x-wmf");

		contentTypeMap.put(".wml", "text/vnd.wap.wml");

		contentTypeMap.put(".wmv", "video/x-ms-wmv");

		contentTypeMap.put(".wmx", "video/x-ms-wmx");

		contentTypeMap.put(".wmz", "application/x-ms-wmz");

		contentTypeMap.put(".wp6", "application/x-wp6");

		contentTypeMap.put(".wpd", "application/x-wpd");

		contentTypeMap.put(".wpg", "application/x-wpg");

		contentTypeMap.put(".wpl", "application/vnd.ms-wpl");

		contentTypeMap.put(".wq1", "application/x-wq1");

		contentTypeMap.put(".wr1", "application/x-wr1");

		contentTypeMap.put(".wri", "application/x-wri");

		contentTypeMap.put(".wrk", "application/x-wrk");

		contentTypeMap.put(".ws", "application/x-ws");

		contentTypeMap.put(".ws2", "application/x-ws");

		contentTypeMap.put(".wsc", "text/scriptlet");

		contentTypeMap.put(".wsdl", "text/xml");

		contentTypeMap.put(".wvx", "video/x-ms-wvx");

		contentTypeMap.put(".xdp", "application/vnd.adobe.xdp");

		contentTypeMap.put(".xdr", "text/xml");

		contentTypeMap.put(".xfd", "application/vnd.adobe.xfd");

		contentTypeMap.put(".xfdf", "application/vnd.adobe.xfdf");

		contentTypeMap.put(".xhtml", "text/html");

		contentTypeMap.put(".xls", "application/vnd.ms-excel");

		contentTypeMap.put(".xlw", "application/x-xlw");

		contentTypeMap.put(".xml", "text/xml");

		contentTypeMap.put(".xpl", "audio/scpls");

		contentTypeMap.put(".xq", "text/xml");

		contentTypeMap.put(".xql", "text/xml");

		contentTypeMap.put(".xquery", "text/xml");

		contentTypeMap.put(".xsd", "text/xml");

		contentTypeMap.put(".xsl", "text/xml");

		contentTypeMap.put(".xslt", "text/xml");

		contentTypeMap.put(".xwd", "application/x-xwd");

		contentTypeMap.put(".x_b", "application/x-x_b");

		contentTypeMap.put(".x_t", "application/x-x_t");
	}

	private ContentTypeHelper() {
	}

	public static String getContentType(String fileName) {
		int lastIndexOf = fileName.lastIndexOf('.');
		if (lastIndexOf == 0) {
			//nothing
		} else if (lastIndexOf > 0) {
			fileName = fileName.substring(lastIndexOf);
		} else {
			fileName = "." + fileName;
		}
		String contentType = contentTypeMap.get(fileName.toLowerCase());
		return contentType == null ? "application/octet-stream" : contentType;
	}
}
