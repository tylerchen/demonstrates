/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.iff.demo.dddallinone.ws;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.xml.namespace.QName;

import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.iff.demo.dddallinone.dto.EJBFacadeDTO;
import org.iff.demo.dddallinone.dto.MyUserDTO;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;

public class WeatherSpringRPCClient {

	public static void main(String[] args1) throws Exception {
		
		{
			FileInputStream fos  =  new FileInputStream("f:\\test.test");
			HessianInput in = new HessianInput(fos);
			Object obj = in.readObject(null);
			System.out.println(obj);
			fos.close();
			if(true){
				return;
			}
		}

		MyUserDTO myUser = new MyUserDTO();
		myUser.setName("3、部署web，其他的配置都不用改变，并运行tomcat。");
		EJBFacadeDTO ejbFacadeDTO = new EJBFacadeDTO(
				"org.iff.demo.dddallinone.application.MyUserApplication",
				"myUserApplication", "saveMyUser", new Object[] { myUser });
		{
			try {
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				HessianOutput out = new HessianOutput(os);
				out.writeObject(ejbFacadeDTO);
				os.close();
				System.out.println(new String(os.toByteArray(),"UTF-8")+"\nlength:"+os.size());
				ByteArrayInputStream is = new ByteArrayInputStream(new String(os.toByteArray(),"UTF-8").getBytes());
				HessianInput in = new HessianInput(is);
				Object obj = in.readObject(null);
				System.out.println(obj);
				is.close();
				{
					FileOutputStream fos = new FileOutputStream("f:\\test.test");
					fos.write(os.toByteArray());
					fos.flush();
					fos.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (true) {
				return;
			}
		}

		RPCServiceClient serviceClient = new RPCServiceClient();

		Options options = serviceClient.getOptions();

		EndpointReference targetEPR = new EndpointReference(
				"http://localhost:8080/services/AllInOneEJBFacadeWS");

		options.setTo(targetEPR);

		// Get the weather (no setting, the Spring Framework has already initialized it for us)
		QName saveMyUser = new QName(
				"http://application.dddallinone.demo.iff.org", "invoke");
		Object[] opGetWeatherArgs = new Object[] { ejbFacadeDTO };
		Class[] returnTypes = new Class[] { EJBFacadeDTO.class };

		Object[] response = serviceClient.invokeBlocking(saveMyUser,
				opGetWeatherArgs, returnTypes);
		System.out.println(response);

		EJBFacadeDTO result = (EJBFacadeDTO) response[0];

		// display results
		if (result == null) {
			System.out.println("Weather didn't initialize!");
		} else {
			System.out.println("Temperature               : "
					+ result.getResult());

		}
	}
}
