package com.tokenservice.converter;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tokenservice.contract.AuthContractV1;
import com.tokenservice.exception.UnsupportedAuthVersionException;
import com.tokenservice.model.AuthenticationModel;
import com.tokenservice.requestcontext.RequestContext;

public class ContractConverter {
	
	public static  AuthenticationModel getAuthenticationModel(RequestContext context) 
			throws JsonParseException, JsonMappingException, IOException {
		if (context.getVersion() == 1.0) {ObjectMapper mapper = new ObjectMapper();
			AuthContractV1 c = mapper.readValue(context.getBody(),AuthContractV1.class);
			return new AuthenticationModel(c);
		}
		
		throw new UnsupportedAuthVersionException();
	}
}
