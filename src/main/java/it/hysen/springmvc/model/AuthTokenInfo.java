package it.hysen.springmvc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "auth_token_info")
@NamedQuery(name = "AuthTokenInfo.findAll", query = "SELECT ati FROM AuthTokenInfo ati")
public class AuthTokenInfo extends AbstractEntity<Long> {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "auth_token_info_id")
	private Long id;
	
	@Column(name = "access_token")
	private String accessToken;
	
	@Column(name = "token_type")
	private String tokenType;
	
	@Column(name = "refresh_token")
	private String refreshToken;

	@Column(name = "expires_in")
	private int expiresIn;
	
	private String scope;
	
	public String getAccessToken() {
		return this.accessToken;
	}
	
	public int getExpiresIn() {
		return this.expiresIn;
	}
	
	@Override
	public Long getId() {
		return this.id;
	}
	
	public String getRefreshToken() {
		return this.refreshToken;
	}
	
	public String getScope() {
		return this.scope;
	}
	
	public String getTokenType() {
		return this.tokenType;
	}
	
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}
	
	@Override
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	
	public void setScope(String scope) {
		this.scope = scope;
	}
	
	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

}
