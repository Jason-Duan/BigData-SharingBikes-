package com.jason;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * spring boot 可以自动扫描当前包及其子包中含有特殊注解的类，并且交给spring容器来管理
 */

@SpringBootApplication
public class WebbikeApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebbikeApplication.class, args);
	}
}
