/*******************************************************************************
 * Copyright (c) 2012-6-2 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package org.iff.sample.application.core;

import java.util.List;

import org.iff.sample.infra.dto.core.MyTestDTO;
import org.iff.sample.webservice.dto.MyTestWSDTO;



/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2012-6-2
 */
public interface MyApplication {
	List<MyTestDTO> findMyTestByName(String name);
	List<MyTestDTO> findMyTestAll();
	MyTestDTO createMyTest(MyTestWSDTO wst);
	boolean deleteMytest(long id);
	MyTestDTO updateMyTest(MyTestWSDTO wst);
	
}
