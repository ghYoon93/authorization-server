package org.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

/**
 * @주시스템 :   authorization-server
 * @서브 시스템        :   org.example.config
 * @프로그램 ID       :   AuthServerConfig
 * @프로그램 개요      :
 * @변경이력 ============================================================================
 * 1.0  2024. 09. 23.	:	yghee	-	신규생성
 * ============================================================================
 */
@Configuration
@EnableAuthorizationServer // OAuth 2.0 인증 서버 기능을 활성화
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Override
	public void configure(
			AuthorizationServerSecurityConfigurer oauthServer) throws Exception {

		oauthServer.tokenKeyAccess("permitAll()") // 토큰 키에 대한 접근을 모두에게 허용
				.checkTokenAccess("isAuthenticated()"); // 토큰 검사 엔드포인트는 인증된 요청만 허용
	}

	@Override
	public void configure( ClientDetailsServiceConfigurer clients ) throws Exception {

		clients.inMemory() // 클라이언트 정보를 메모리에 저장
				.withClient("SampleClientId") // 클라이언트 ID를 설정
				.secret( passwordEncoder.encode( "secret" ) ) // 클라이언트 시크릿을 암호화하여 설정
				.authorizedGrantTypes( "authorization_code", "refresh_token" ) // 인증 코드, 리프레시 토큰을 그랜트 타입으로 사용
				.scopes( "user_info" ) // 사용자가 요청할 수 있는 범위 설정
				.autoApprove(true) // 사용자의 승인 없이 자동으로 인증을 승인
				.redirectUris(
						"http://client.abc.com:8082/ui/login"
						,"http://client.xyz.com:8083/ui2/login" // 인증 후 리다이렉트될 수 있는 URI들을 지정
						, "http://client.abc.com:8082/ui/logout"
						,"http://client.xyz.com:8083/ui2/logout" ); // 인증 후 리다이렉트될 수 있는 URI들을 지정
	}

	@Override
	public void configure( AuthorizationServerEndpointsConfigurer endpoints ) throws Exception {
		endpoints.authenticationManager( authenticationManager )
				.tokenStore(tokenStore());
	}

	@Bean
	public TokenStore tokenStore() {
		return new JdbcTokenStore( dataSource() );
	}

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.h2.Driver");
		dataSource.setUrl("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
		dataSource.setUsername("sa");
		dataSource.setPassword("");
		return dataSource;
	}
}
