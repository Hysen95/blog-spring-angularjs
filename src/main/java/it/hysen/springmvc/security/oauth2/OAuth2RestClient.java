package it.hysen.springmvc.security.oauth2;

import java.net.URI;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import it.hysen.springmvc.model.AuthTokenInfo;
import it.hysen.springmvc.model.User;

public class OAuth2RestClient {
	
	public static final String REST_SERVICE_URI = "http://localhost:8080/spring-project/api";
	
	public static final String AUTH_SERVER_URI = "http://localhost:8080/spring-project/oauth/token";
	
	public static final String QPM_PASSWORD_GRANT = "?grant_type=password&username=gabriele&password=gabriele";
	
	public static final String QPM_ACCESS_TOKEN = "?access_token=";
	
	/*
	 * Send a POST request to create a new user.
	 */
	private static void createUser(AuthTokenInfo tokenInfo) {
		Assert.notNull(tokenInfo, "Authenticate first please......");
		System.out.println("\nTesting create User API----------");
		RestTemplate restTemplate = new RestTemplate();
		User user = new User();
		HttpEntity<Object> request = new HttpEntity<Object>(user, OAuth2RestClient.getHeaders());
		URI uri = restTemplate.postForLocation(OAuth2RestClient.REST_SERVICE_URI + "/user/"
		        + OAuth2RestClient.QPM_ACCESS_TOKEN + tokenInfo.getAccessToken(), request, User.class);
		System.out.println("Location : " + uri.toASCIIString());
	}
	
	/*
	 * Send a DELETE request to delete all users.
	 */
	private static void deleteAllUsers(AuthTokenInfo tokenInfo) {
		Assert.notNull(tokenInfo, "Authenticate first please......");
		System.out.println("\nTesting all delete Users API----------");
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<String> request = new HttpEntity<>(OAuth2RestClient.getHeaders());
		restTemplate.exchange(OAuth2RestClient.REST_SERVICE_URI + "/user/" + OAuth2RestClient.QPM_ACCESS_TOKEN
		        + tokenInfo.getAccessToken(), HttpMethod.DELETE, request, User.class);
	}
	
	/*
	 * Send a DELETE request to delete a specific user.
	 */
	private static void deleteUser(AuthTokenInfo tokenInfo) {
		Assert.notNull(tokenInfo, "Authenticate first please......");
		System.out.println("\nTesting delete User API----------");
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<String> request = new HttpEntity<>(OAuth2RestClient.getHeaders());
		restTemplate.exchange(OAuth2RestClient.REST_SERVICE_URI + "/user/3" + OAuth2RestClient.QPM_ACCESS_TOKEN
		        + tokenInfo.getAccessToken(), HttpMethod.DELETE, request, User.class);
	}
	
	/*
	 * Prepare HTTP Headers.
	 */
	private static HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		return headers;
	}
	
	/*
	 * Add HTTP Authorization header, using Basic-Authentication to send
	 * client-credentials.
	 */
	private static HttpHeaders getHeadersWithClientCredentials() {
		String plainClientCredentials = "my-trusted-client:secret";
		String base64ClientCredentials = new String(Base64.encodeBase64(plainClientCredentials.getBytes()));
		
		HttpHeaders headers = OAuth2RestClient.getHeaders();
		headers.add("Authorization", "Basic " + base64ClientCredentials);
		return headers;
	}
	
	/*
	 * Send a GET request to get a specific user.
	 */
	private static void getUser(AuthTokenInfo tokenInfo) {
		Assert.notNull(tokenInfo, "Authenticate first please......");
		System.out.println("\nTesting getUser API----------");
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<String> request = new HttpEntity<>(OAuth2RestClient.getHeaders());
		ResponseEntity<User> response = restTemplate.exchange(OAuth2RestClient.REST_SERVICE_URI + "/user/1"
		        + OAuth2RestClient.QPM_ACCESS_TOKEN + tokenInfo.getAccessToken(), HttpMethod.GET, request, User.class);
		User user = response.getBody();
		System.out.println(user);
	}
	
	/*
	 * Send a GET request to get list of all users.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static void listAllUsers(AuthTokenInfo tokenInfo) {
		Assert.notNull(tokenInfo, "Authenticate first please......");
		
		System.out.println("\nTesting listAllUsers API-----------");
		RestTemplate restTemplate = new RestTemplate();
		
		HttpEntity<String> request = new HttpEntity<>(OAuth2RestClient.getHeaders());
		ResponseEntity<List> response = restTemplate.exchange(OAuth2RestClient.REST_SERVICE_URI + "/user/"
		        + OAuth2RestClient.QPM_ACCESS_TOKEN + tokenInfo.getAccessToken(), HttpMethod.GET, request, List.class);
		List<LinkedHashMap<String, Object>> usersMap = response.getBody();
		
		if (usersMap != null) {
			for (LinkedHashMap<String, Object> map : usersMap) {
				System.out.println("User : id=" + map.get("id") + ", Name=" + map.get("name") + ", Age="
				        + map.get("age") + ", Salary=" + map.get("salary"));
				;
			}
		}
		else {
			System.out.println("No user exist----------");
		}
	}
	
	public static void main(String args[]) {
		AuthTokenInfo tokenInfo = OAuth2RestClient.sendTokenRequest();
		OAuth2RestClient.listAllUsers(tokenInfo);
		
		OAuth2RestClient.getUser(tokenInfo);
		
		OAuth2RestClient.createUser(tokenInfo);
		OAuth2RestClient.listAllUsers(tokenInfo);
		
		OAuth2RestClient.updateUser(tokenInfo);
		OAuth2RestClient.listAllUsers(tokenInfo);
		
		OAuth2RestClient.deleteUser(tokenInfo);
		OAuth2RestClient.listAllUsers(tokenInfo);
		
		OAuth2RestClient.deleteAllUsers(tokenInfo);
		OAuth2RestClient.listAllUsers(tokenInfo);
	}
	
	/*
	 * Send a POST request [on /oauth/token] to get an access-token, which will
	 * then be send with each request.
	 */
	@SuppressWarnings({ "unchecked" })
	private static AuthTokenInfo sendTokenRequest() {
		RestTemplate restTemplate = new RestTemplate();
		
		HttpEntity<String> request = new HttpEntity<>(OAuth2RestClient.getHeadersWithClientCredentials());
		ResponseEntity<Object> response = restTemplate.exchange(
		        OAuth2RestClient.AUTH_SERVER_URI + OAuth2RestClient.QPM_PASSWORD_GRANT, HttpMethod.POST, request,
		        Object.class);
		LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) response.getBody();
		AuthTokenInfo tokenInfo = null;
		
		if (map != null) {
			tokenInfo = new AuthTokenInfo();
			tokenInfo.setAccessToken((String) map.get("access_token"));
			tokenInfo.setTokenType((String) map.get("token_type"));
			tokenInfo.setRefreshToken((String) map.get("refresh_token"));
			tokenInfo.setExpiresIn((int) map.get("expires_in"));
			tokenInfo.setScope((String) map.get("scope"));
			System.out.println(tokenInfo);
			// System.out.println("access_token ="+map.get("access_token")+",
			// token_type="+map.get("token_type")+",
			// refresh_token="+map.get("refresh_token")
			// +", expires_in="+map.get("expires_in")+",
			// scope="+map.get("scope"));;
		}
		else {
			System.out.println("No user exist----------");
			
		}
		return tokenInfo;
	}
	
	/*
	 * Send a PUT request to update an existing user.
	 */
	private static void updateUser(AuthTokenInfo tokenInfo) {
		Assert.notNull(tokenInfo, "Authenticate first please......");
		System.out.println("\nTesting update User API----------");
		RestTemplate restTemplate = new RestTemplate();
		User user = new User();
		HttpEntity<Object> request = new HttpEntity<Object>(user, OAuth2RestClient.getHeaders());
		ResponseEntity<User> response = restTemplate.exchange(OAuth2RestClient.REST_SERVICE_URI + "/user/1"
		        + OAuth2RestClient.QPM_ACCESS_TOKEN + tokenInfo.getAccessToken(), HttpMethod.PUT, request, User.class);
		System.out.println(response.getBody());
	}
}
