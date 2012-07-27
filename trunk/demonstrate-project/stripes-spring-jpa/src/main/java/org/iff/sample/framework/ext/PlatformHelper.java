package org.iff.sample.framework.ext;

public class PlatformHelper {
	private static final int UNSPECIFIED = -1;
	private static final int MAC = 0;
	private static final int LINUX = 1;
	private static final int WINDOWS = 2;
	private static final int SOLARIS = 3;
	private static final int FREEBSD = 4;
	private static final int osType;

	static {
		String osName = System.getProperty("os.name");
		if (osName.startsWith("Linux")) {
			osType = LINUX;
		} else if (osName.startsWith("Mac") || osName.startsWith("Darwin")) {
			osType = MAC;
		} else if (osName.startsWith("Windows")) {
			osType = WINDOWS;
		} else if (osName.startsWith("Solaris") || osName.startsWith("SunOS")) {
			osType = SOLARIS;
		} else if (osName.startsWith("FreeBSD")) {
			osType = FREEBSD;
		} else {
			osType = UNSPECIFIED;
		}
	}

	private PlatformHelper() {
	}

	public static final boolean isMac() {
		return osType == MAC;
	}

	public static final boolean isLinux() {
		return osType == LINUX;
	}

	public static final boolean isWindows() {
		return osType == WINDOWS;
	}

	public static final boolean isSolaris() {
		return osType == SOLARIS;
	}

	public static final boolean isFreeBSD() {
		return osType == FREEBSD;
	}

	public static final boolean isX11() {
		// TODO: check FS or do some other X11-specific test  
		return !PlatformHelper.isWindows() && !PlatformHelper.isMac();
	}
}