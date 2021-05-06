package com.web.bigdata;

import javax.servlet.http.HttpSessionListener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BdBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BdBackendApplication.class, args);
	}
	@Bean
	  public HttpSessionListener httpSessionListener(){
	    return new SessionListener();
	 }

}
