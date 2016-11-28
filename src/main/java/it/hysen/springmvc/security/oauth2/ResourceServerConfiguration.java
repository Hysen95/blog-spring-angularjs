package it.hysen.springmvc.security.oauth2;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

//@Configuration
//@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

	private static final String RESOURCE_ID = "rest_api";
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.anonymous().disable().requestMatchers().antMatchers("/api/**").and().authorizeRequests()
		        .antMatchers("/api/**").access("hasRole('ADMIN')").and().exceptionHandling()
		        .accessDeniedHandler(new OAuth2AccessDeniedHandler());
	}
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		resources.resourceId(ResourceServerConfiguration.RESOURCE_ID).stateless(false);
	}
	
}
