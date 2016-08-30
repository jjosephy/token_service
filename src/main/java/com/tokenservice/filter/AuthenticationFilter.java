package com.tokenservice.filter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Base64;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;
import com.tokenservice.authentication.AuthToken;
import com.tokenservice.authentication.TokenAuthenticationManager;
import com.tokenservice.authenticationprovider.AuthenticationProviderFactory;
import com.tokenservice.converter.ContractConverter;
import com.tokenservice.entrypoint.TokenRequestEntryPoint;
import com.tokenservice.exception.GeneralAuthException;
import com.tokenservice.exception.InvalidMethodException;
import com.tokenservice.exception.InvalidTokenException;
import com.tokenservice.exception.UnauthenticatedException;
import com.tokenservice.exception.UnsupportedAuthVersionException;
import com.tokenservice.model.AuthenticationModel;
import com.tokenservice.requestcontext.RequestContext;

@Component
public class AuthenticationFilter extends GenericFilterBean {
	
	private AuthenticationEntryPoint entryPoint;
	private AuthenticationManager authenticationManager;
	
	public AuthenticationFilter() {
		this.entryPoint = new TokenRequestEntryPoint();
		this.authenticationManager = new TokenAuthenticationManager();
	}

	@Override
	public void afterPropertiesSet() throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		RequestContext ctx = null;
		try {
			ctx = RequestContext.NewRequestContext(req);
		} catch (UnsupportedAuthVersionException iEx) {
			this.entryPoint.commence(ctx, res, iEx);
			return;
		} catch (Exception ex) {
			this.entryPoint.commence(ctx, res, new GeneralAuthException("Unexpected"));
			return;
		}
		
		String method = ctx.getMethod();
		String uri = ctx.getRequestURI();
		
		if (uri.equalsIgnoreCase("/token")) {
			if (method.equalsIgnoreCase("post")) {
				
				AuthenticationModel model = null;
				try {
					model = ContractConverter.getAuthenticationModel(ctx);
				} catch (UnsupportedAuthVersionException uex) {
					this.entryPoint.commence(ctx, res, uex);
					return;
				} catch(Exception ex) {
					this.entryPoint.commence(ctx, res, new GeneralAuthException());
					return;
				}
				
				if (AuthenticationProviderFactory.authenticate(model)) {
					chain.doFilter(request, response);
					return;
				} else {
					this.entryPoint.commence(ctx, res, new UnauthenticatedException());
					return;
				}
				
			} else {
				this.entryPoint.commence(ctx, res, new InvalidMethodException());
				return;
			}
		}
		
		try {
			String stringToken = ctx.getHeader("Authorization");
			if (stringToken == null) {
				this.entryPoint.commence(ctx, res, new InvalidTokenException());
				return;
			}
			
			String authorizationSchema = "Bearer";
			if (stringToken.indexOf(authorizationSchema) == -1) {
				//TODO: fix this exception
				throw new InsufficientAuthenticationException("Authorization schema not found");
			}
			
			stringToken = stringToken.substring(authorizationSchema.length()).trim();
			try {
				byte[] decoded = Base64.getDecoder().decode(stringToken);
				stringToken = new String(decoded, "UTF-8");
				JWT jwt = JWTParser.parse(stringToken);
				AuthToken token = new AuthToken(jwt);
				authenticationManager.authenticate(token);
				SecurityContextHolder.getContext().setAuthentication(token);
				chain.doFilter(request, response);
			} catch (ParseException e) {
				this.entryPoint.commence(ctx, res, new InvalidTokenException("cant parse token"));
				return;
			} catch (FileNotFoundException fEx) {
				this.entryPoint.commence(ctx, res, new InvalidTokenException(fEx.getMessage()));
				return;
			}
			catch (Exception e) {
				this.entryPoint.commence(ctx, res, new InvalidTokenException(e.getMessage()));
				return;
			}
		} catch (AuthenticationException e) {
			SecurityContextHolder.clearContext();
			this.entryPoint.commence(ctx, res, e);
		}
	}
}