package com.tokenservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TokenServiceApplication extends SpringBootServletInitializer {
	
	private static ApplicationContext context = null; 
	private static int port = 0;
	
	@Bean
	public EmbeddedServletContainerFactory servletContainer() {
		TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
		if (port != 0) {
			factory.setPort(port);
		}
		return factory;
	}
	
	public static void main(String[] args) {
		if (args.length > 0 && args[0].equals("UnitTest")) {
			port = Integer.parseInt(args[1]);
		}
		context = SpringApplication.run(TokenServiceApplication.class, args);
	}
	
	public static ApplicationContext getApplicationContext() {
		return context;
	}
}

