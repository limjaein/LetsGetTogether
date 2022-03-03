package com.lgt.service;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.lgt.domain.Role;
import com.lgt.domain.SessionUser;
import com.lgt.domain.User;
import com.lgt.domain.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor 
public class OAuthService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
	
	private final UserRepository userRepository;
	private final HttpSession httpSession;
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		
		OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
		
		// Access Token (userRequest)를 통해 카카오에서 유저 정보 받음
		OAuth2User oAuth2User = delegate.loadUser(userRequest);
		
		// OAuth2 서비스 이름 (kakao)
		String serviceName = userRequest.getClientRegistration()
										.getRegistrationId();
		
		// OAuth2 로그인 시 키의 이름 (kakao = id)
		String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
												  .getUserInfoEndpoint().getUserNameAttributeName();
		
		// OAuth2 서비스의 유저 정보들
		Map<String, Object> attributes = oAuth2User.getAttributes();
		
		String email;
		
		if ("kakao".equals(serviceName)) {
			Map<String, Object> profile = (Map<String, Object>) attributes.get("kakao_account");
			email = (String) profile.get("email");
		} else {
			throw new OAuth2AuthenticationException("허용되지 않는 인증입니다.");
		}

		User user;
		Optional<User> optionalUser = userRepository.findByEmail(email);
		String accessToken = userRequest.getAccessToken().getTokenValue();
		
		if (optionalUser.isPresent()) {
			user = optionalUser.get();
		} else {
			user = new User();
			user.setEmail(email);
			user.setRole(Role.ROLE_USER);
			userRepository.save(user);
		}
		
		log.info("access token " + accessToken);
		
		httpSession.setAttribute("user", new SessionUser(user));
		httpSession.setAttribute("access_token", accessToken);
		
		return new DefaultOAuth2User(
				Collections.singleton(new SimpleGrantedAuthority(user.getRole().toString()))
				, oAuth2User.getAttributes()
				, userNameAttributeName
		);
	} 
}
