/*******************************************************************************
 * Copyright (c) 2012-5-23 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package org.iff.sample.framework.ext;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.FileType;
import org.apache.commons.vfs2.FileUtil;
import org.apache.commons.vfs2.VFS;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2012-5-23
 */
public class FileStoreHelper {

	private static final Log log = LogFactory.getLog(FileStoreHelper.class);

	private static FileSystemManager fileSystemManager = null;

	private FileStoreHelper() {
	}

	public synchronized static FileSystemManager getFileSystemManager() {
		if (fileSystemManager == null) {
			try {
				fileSystemManager = VFS.getManager();
			} catch (Exception e) {
				log.error("Can not get VFS manager!", e);
			}
		}
		return fileSystemManager;
	}

	public static void writeFile(byte[] bs, String fileName) throws Exception {
		writeFile(new ByteArrayInputStream(bs), fileName);
	}

	public static void writeFile(InputStream is, String fileName)
			throws Exception {
		OutputStream os = getFileObject(fileName).getContent()
				.getOutputStream();
		try {
			byte[] buffer = new byte[1024];
			while (true) {
				int nread = is.read(buffer);
				if (nread < 0) {
					break;
				}

				os.write(buffer, 0, nread);
			}
			os.flush();
		} finally {
			try {
				is.close();
			} catch (Exception e) {
			}
			try {
				os.close();
			} catch (Exception e) {
			}
		}
	}

	public static byte[] getFileContent(String fileName) throws Exception {
		return FileUtil.getContent(getFileObject(fileName));
	}

	public static InputStream getFileInputStream(String fileName)
			throws Exception {
		return getFileObject(fileName).getContent().getInputStream();
	}

	public static byte[] getFileContent(FileObject fileObject) throws Exception {
		return FileUtil.getContent(fileObject);
	}

	public static void copyFile(String srcFileName, String destFileName)
			throws Exception {
		FileUtil.copyContent(getFileObject(srcFileName),
				getFileObject(destFileName));
	}

	public static void copyFile(FileObject src, FileObject dest)
			throws Exception {
		FileUtil.copyContent(src, dest);
	}

	public static boolean isFolder(String folder, boolean create) {
		try {
			FileObject fileObject = getFileObject(folder);
			boolean isValidate = fileObject.exists();
			if (!isValidate && create) {
				fileObject.createFolder();
			}
			return fileObject.getType() == FileType.FOLDER;
		} catch (Exception e) {
		}
		return false;
	}

	public static boolean isFile(String fileName) {
		try {
			FileObject fileObject = getFileObject(fileName);
			return fileObject.getType() == FileType.FILE;
		} catch (Exception e) {
		}
		return false;
	}

	public static FolderContent listFolder(String folderPath,
			String relativeFolderPath) {
		FileObject fileObject = null;
		try {
			fileObject = getFileObject(folderPath);
		} catch (Exception e) {
		}
		return listFolder(fileObject, relativeFolderPath);
	}

	public static FolderContent listFolder(FileObject folder,
			String relativeFolderPath) {
		if (relativeFolderPath == null) {
			relativeFolderPath = "";
		}
		List<String> folders = new ArrayList<String>();
		List<String> files = new ArrayList<String>();
		{
			folders.add("..");
		}
		FolderContent folderContent = new FolderContent(relativeFolderPath,
				folders, files);
		try {
			if (folder == null || !folder.exists()) {
				return folderContent;
			}
			if (folder.getType() != FileType.FOLDER) {
				return folderContent;
			}
			for (FileObject fo : folder.getChildren()) {
				if (fo.getType() == FileType.FOLDER) {
					folders.add(fo.getName().getBaseName());
				} else if (fo.getType() == FileType.FILE) {
					files.add(fo.getName().getBaseName());
				}
			}
		} catch (Exception e) {
		}
		return folderContent;
	}

	private static FileObject getFileObject(String fileName)
			throws FileSystemException {
		return getFileSystemManager().resolveFile(fileName);
	}

	@SuppressWarnings("serial")
	public static class FolderContent implements Serializable {
		private String folderPath;
		private List<String> folders;
		private List<String> files;

		public FolderContent(String folderPath, List<String> folders,
				List<String> files) {
			this.folderPath = folderPath;
			this.folders = folders;
			this.files = files;
		}

		public String getFolderPath() {
			return folderPath;
		}

		public List<String> getFolders() {
			return folders;
		}

		public List<String> getFiles() {
			return files;
		}

		public String toString() {
			return "FolderContent [files=" + files + ", folderPath="
					+ folderPath + ", folders=" + folders + "]";
		}
	}
}
