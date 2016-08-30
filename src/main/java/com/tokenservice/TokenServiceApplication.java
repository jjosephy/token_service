package com.tokenservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import com.tokenservice.authenticationprovider.*;

@SpringBootApplication
public class TokenServiceApplication extends SpringBootServletInitializer {

	@Bean
	public EmbeddedServletContainerFactory servletContainer() {
		TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
		return factory;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(TokenServiceApplication.class, args);
	}
	
	public static AuthProviderLocator getServiceLocator() {
		return AuthProviderLocator.getInstance();
	}
}

