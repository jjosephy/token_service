package com.tokenservice.contract;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonGetter;

public abstract class ContractBase {

	private final UUID id; 
	
	public ContractBase() {
		this.id = UUID.randomUUID();
	}
	
	@JsonGetter("id")
	public UUID getId() {
		return this.id;
	}
	
}
