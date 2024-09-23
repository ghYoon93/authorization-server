package org.example.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @주시스템 :   authorization-server
 * @서브 시스템        :   org.example.web.controller
 * @프로그램 ID       :   LogoutController
 * @프로그램 개요      :
 * @변경이력 ============================================================================
 * 1.0  2024. 09. 23.	:	yghee	-	신규생성
 * ============================================================================
 */
@Controller
public class LogoutController {

	@Autowired
	private TokenStore tokenStore;

	@GetMapping( "/logout" )
	public ResponseEntity<String> logout( @RequestParam("token") String token) {
		OAuth2AccessToken accessToken = tokenStore.readAccessToken(token);
		if (accessToken != null) {
			tokenStore.removeAccessToken(accessToken);
			OAuth2RefreshToken refreshToken = accessToken.getRefreshToken();
			if (refreshToken != null) {
				tokenStore.removeRefreshToken(refreshToken);
			}
		}
		return ResponseEntity.ok( "Logged out successfully" );
	}
}