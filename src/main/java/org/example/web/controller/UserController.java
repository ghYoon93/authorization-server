package org.example.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @주시스템 :   authorization-server
 * @서브 시스템        :   org.example.web.controller
 * @프로그램 ID       :   UserController
 * @프로그램 개요      :
 * @변경이력 ============================================================================
 * 1.0  2024. 09. 23.	:	yghee	-	신규생성
 * ============================================================================
 */
@RestController
public class UserController {
	@GetMapping( "/user/me" )
	public Principal user( Principal principal ) {
		return principal;
	}
}