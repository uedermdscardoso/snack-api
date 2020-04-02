package dev.uedercardoso.snack.domain.model.token;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AuthenticationToken implements Serializable {

	private static final long serialVersionUID = 2190479047702916527L;
	
	private String access_token;
	private String token_type;
	private Long expires_in;
	private String scope;
	private String jti;
	
	public AuthenticationToken() {
		
	}

	public AuthenticationToken(String access_token, String token_type, Long expires_in, String scope, String jti) {
		this.access_token = access_token;
		this.token_type = token_type;
		this.expires_in = expires_in;
		this.scope = scope;
		this.jti = jti;
	}
	
}
