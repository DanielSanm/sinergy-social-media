package com.sinergy.user.security;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class ApiKeyAuthenticationToken extends AbstractAuthenticationToken {

	private static final long serialVersionUID = -9054704671142073672L;
	
	private final String apiKey;
	private final Object principal;

	public ApiKeyAuthenticationToken(String apiKey) {
		super(null);
		this.apiKey = apiKey;
		this.principal = null;
		setAuthenticated(false);
	}

	public ApiKeyAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.apiKey = null;
		this.principal = principal;
		setAuthenticated(true);
	}

	@Override
	public Object getCredentials() {
		return apiKey;
	}

	@Override
	public Object getPrincipal() {
		return principal;
	}
}
