package com.foreveross.demo.moduleb.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DemoBController {

	@RequestMapping("/loginB")
	public String login() {
		return "login";
	}

	@RequestMapping("/indexB")
	public String index() {
		System.out.println("----------");
		return "index";
	}

}
