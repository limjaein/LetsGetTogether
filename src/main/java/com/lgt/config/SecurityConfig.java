package com.lgt.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.lgt.service.OAuthService;

import lombok.RequiredArgsConstructor;

@Configuration // IoC에 뜨기 위한 annotation 
@EnableWebSecurity	//spring security 설정을 활성화시켜주는 annotation
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final OAuthService oAuthService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/").permitAll()
				.antMatchers("/login/**").permitAll()
				.anyRequest().authenticated()
			.and()
				.oauth2Login() // OAuth2 로그인 설정 시작
				.defaultSuccessUrl("/home")
				.userInfoEndpoint() // OAuth2 로그인 성공 이후 사용자 정보를 가져올 때 설정을 저장 
				.userService(oAuthService); // OAuth2 로그인 성공 시, 후작업을 진행할 UserService 인터페이스 구현체 등록 
	}
}
