/*******************************************************************************
 * Copyright (c) 2012-6-2 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package org.iff.sample.webservice.core;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.iff.sample.webservice.dto.MyTestWSDTO;


/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2012-6-2
 */
@Path("/MyApplication")
@Produces( { MediaType.APPLICATION_JSON })
public interface MyApplicationWS {

	//查询
	/**
	 * http://localhost:8080/webservice/v1/MyApplication/findMyTestByName/test
	 */
	@GET
	@Path("/findMyTestByName/{name}")
	List<MyTestWSDTO> findMyTestByName(@PathParam("name") String name);

	/**
	 * http://localhost:8080/webservice/v1/MyApplication/findMyTestByNameTest/test?id=1&age=2&name=3
	 */
	@GET
	@Path("/findMyTestByNameTest/{name}")
	List<MyTestWSDTO> findMyTestByNameTest(@PathParam("name") String name,
			@QueryParam("") MyTestWSDTO test);

	/**
	 * http://localhost:8080/webservice/v1/MyApplication/findMyTestByNameTest2/test;id=1;age=2;name=3
	 */
	@GET
	@Path("/findMyTestByNameTest2/{name}")
	List<MyTestWSDTO> findMyTestByNameTest2(@PathParam("name") String name,
			@MatrixParam("") MyTestWSDTO test);

	/**
	 * 	URL url = new URL( "http://localhost:8080/webservice/v1/MyApplication/findMyTestByNameTest4/test");
	 * 	HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	 * 	conn.setRequestMethod("POST");
	 * 	conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
	 * 	conn.setRequestProperty("Accept-Charset", "utf-8");
	 * 	conn.setDoOutput(true);
	 * 	conn.setDoInput(true);
	 * 	conn.setConnectTimeout(5 * 100000);
	 * 	conn.connect();
	 * 	PrintWriter out = new PrintWriter(conn.getOutputStream());//发送数据
	 * 	out.print("age=11&name=name");
	 * 	out.flush();
	 * 	out.close();
	 */
	@POST
	@Path("/findMyTestByNameTest3/{name}")
	List<MyTestWSDTO> findMyTestByNameTest3(@PathParam("name") String name,
			@FormParam("") MyTestWSDTO test);

	/**
	 * 	URL url = new URL( "http://localhost:8080/webservice/v1/MyApplication/findMyTestByNameTest4/test");
	 * 	HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	 * 	conn.setRequestMethod("POST");
	 * 	conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
	 * 	conn.setRequestProperty("Accept-Charset", "utf-8");
	 * 	conn.setDoOutput(true);
	 * 	conn.setDoInput(true);
	 * 	conn.setConnectTimeout(5 * 100000);
	 * 	conn.connect();
	 * 	PrintWriter out = new PrintWriter(conn.getOutputStream());//发送数据
	 * 	out.print("age=11&name=name");
	 * 	out.flush();
	 * 	out.close();
	 */
	@POST
	@Path("/findMyTestByNameTest4/{name}")
	List<MyTestWSDTO> findMyTestByNameTest4(@PathParam("name") String name,
			@FormParam("age") Integer age, @FormParam("name") String userName);

	//查询所有
	@GET
	@Path("/findMyTestAll")
	List<MyTestWSDTO> findMyTestAll();

	//增加
	@GET
	@Path("/saveMyTest/{name}_{age}")
	MyTestWSDTO saveMyTest(@PathParam("name") String name,
			@PathParam("age") int age);

	//删除
	@GET
	@Path("/deleteMytest/{id}")
	boolean deleteMytest(@PathParam("id") long name);

	//修改
	@GET
	@Path("/updateMyTest/{id}-{age}")
	MyTestWSDTO updateMyTest(@PathParam("id") long name,
			@PathParam("age") int age);

}
