/*******************************************************************************
 * Copyright (c) 2012-5-31 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package org.iff.sample.framework.ext;

import java.io.InputStream;

import org.apache.commons.collections.ExtendedProperties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.resource.Resource;
import org.apache.velocity.runtime.resource.loader.ResourceLoader;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2012-5-31
 */
public class VFSResourceLoader extends ResourceLoader {

	private static final Log log = LogFactory.getLog(VFSResourceLoader.class);

	public InputStream getResourceStream(String source)
			throws ResourceNotFoundException {
		try {
			return FileStoreHelper.getFileInputStream(source);
		} catch (Exception e) {
			throw new ResourceNotFoundException(e);
		}
	}

	public void init(ExtendedProperties configuration) {
		if (log.isTraceEnabled()) {
			log.trace("VFSResourceLoader : initialization complete.");
		}
	}

	/**
	 * @see org.apache.velocity.runtime.resource.loader.ResourceLoader#isSourceModified(org.apache.velocity.runtime.resource.Resource)
	 */
	public boolean isSourceModified(Resource resource) {
		if (log.isDebugEnabled()) {
			return true;
		}
		return resource.isSourceModified();
	}

	/**
	 * @see org.apache.velocity.runtime.resource.loader.ResourceLoader#getLastModified(org.apache.velocity.runtime.resource.Resource)
	 */
	public long getLastModified(Resource resource) {
		return resource.getLastModified();
	}

}
