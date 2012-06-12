package org.iff.demo.common.uitag.test;

import com.google.gson.Gson;

public class A {
	private String name, sex;

	public A(String name, String sex) {
		this.name = name;
		this.sex = sex;
	}

	public String getName() {
		return name;
	}

	public String getSex() {
		return sex;
	}

	public String toString() {
		return name + ":" + sex;
	}

	public static void main(String[] args) {

		java.util.List list = new java.util.ArrayList();
		for (int i = 0; i < 10; i++) {
			list.add(new A("name" + i, "sex" + i));
		}
		System.out.println(new Gson().toJson(list));
	}
}