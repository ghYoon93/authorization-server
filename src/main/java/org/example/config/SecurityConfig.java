package org.example.config;

/**
 * @주시스템 :   authorization-server
 * @서브 시스템        :   org.example.config
 * @프로그램 ID       :   SecurityConfig
 * @프로그램 개요      :
 * @변경이력 ============================================================================
 * 1.0  2024. 09. 23.	:	yghee	-	신규생성
 * ============================================================================
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@Order(1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.requestMatchers()
				.antMatchers( "/login", "/oauth/authorize" ) // 보안 설정을 "/login"과 "/oauth/authorize" 경로에만 적용
				.and()
				.authorizeRequests()
				.anyRequest().authenticated() // 모든 요청에 대해 인증을 요구
				.and()
				.formLogin().permitAll() // 폼 로그인을 활성화하고 모든 사용자가 로그인 페이지에 접근할 수 있도록 허용
				.and()
				.logout()
				.logoutUrl( "/logout" )
				.logoutSuccessHandler(this::logoutSuccessHandler)
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID");
	}

	@Override
	protected void configure( AuthenticationManagerBuilder auth ) throws Exception {

		auth.inMemoryAuthentication() // 메모리 기반 사용자 인증 사용
				.withUser("john" )
				.password(passwordEncoder().encode( "123" ) )
				.roles( "USER" ); // 역할 부여
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {

		return super.authenticationManagerBean();
	}

	private void logoutSuccessHandler(
			HttpServletRequest request,
			HttpServletResponse response,
			Authentication authentication) throws IOException {

		// SSO 세션 종료 및 토큰 무효화 로직
		// ...

		// 클라이언트 애플리케이션에 로그아웃 알림 (선택적)
		// ...

		// 로그아웃 후 리다이렉트
		response.sendRedirect("/login?logout");
	}
}