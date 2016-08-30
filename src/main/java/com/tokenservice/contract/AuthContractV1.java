package com.tokenservice.contract;

import java.util.UUID;

import com.tokenservice.configuration.AuthType;

public class AuthContractV1 extends ContractBase {
	
	private AuthType authType;
	private UUID applicationId;
	private UUID customerId;
	private String userName;
	private String password;
	private String apiKey;
	private String mobileId;
	
	public AuthContractV1() {
		
	}

	public void setApiKey(String key) {
		this.apiKey = key;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public void setApplicationId(UUID appId) {
		this.applicationId = appId;
	}
	
	public void setAuthType(AuthType type) {
		this.authType = type;
	}
	
	public void setMobileId(String mobileId) {
		this.mobileId = mobileId;
	}
	
	public void setCustomerId(UUID customerId) {
		this.customerId = customerId;
	}
	
	public String getMobileId() {
		return this.mobileId;
	}
	
	public String getApiKey() {
		return this.apiKey;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public String getUserName() {
		return this.userName;
	}
	
	public UUID getApplicationId() {
		return this.applicationId;
	}
	
	public UUID getCustomerId() {
		return this.customerId;
	}
	
	public AuthType getAuthType() {
		return this.authType;
	}
}
