package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * @주시스템 :   authorization-server
 * @서브 시스템        :   org.example
 * @프로그램 ID       :   AuthorizationServerApplication
 * @프로그램 개요      :
 * @변경이력 ============================================================================
 * 1.0  2024. 09. 23.	:	yghee	-	신규생성
 * ============================================================================
 */
@SpringBootApplication
@EnableResourceServer
public class AuthorizationServerApplication {

	public static void main ( String[] args ) {

		SpringApplication.run( AuthorizationServerApplication.class, args );
	}
}
