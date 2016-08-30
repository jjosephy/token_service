package com.tokenservice.configuration;

import java.util.Properties;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;

public class ConfigManager {
	private static volatile ConfigManager instance; 
	private final Properties properties; 
	
	private ConfigManager() {
		YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
		yaml.setResources(new ClassPathResource("application.yml"));
		properties = yaml.getObject();
	}
	
	public static ConfigManager getInstance() { 
		if (instance == null) {
			synchronized(ConfigManager.class) {
				if (instance == null) {
					instance = new ConfigManager();
				}
			}
		}
		
		return instance;
	}
	
	public String getCertPrivateKey() {
		return this.getProperty("certpath.privatekey").toString();
	}
	
	public String getCertPublicKey() {
		return this.getProperty("certpath.publickey").toString();
	}
	
	public Object getProperty(String name) {
		if (this.properties != null && this.properties.containsKey(name)) {
			return this.properties.get(name);
		}
		
		return null;
	}
}
