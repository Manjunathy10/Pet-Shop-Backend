package com.manjunath.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manjunath.domain.USER_ROLE;
import com.manjunath.modal.VerificationCode;
import com.manjunath.repository.UserRepository;
import com.manjunath.request.LoginOtpRequest;
import com.manjunath.request.LoginRequest;
import com.manjunath.responce.ApiResponce;
import com.manjunath.responce.AuthResponse;
import com.manjunath.responce.SignupRequest;
import com.manjunath.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AuthService authService;

	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> createUserHandler(@RequestBody SignupRequest req) throws Exception {

		String jwt =authService.createUser(req);
		
		AuthResponse res = new AuthResponse();
		
		res.setJwt(jwt);
		res.setMessage("register sucess");
		res.setRole(USER_ROLE.ROLE_CUSTOMER);
		return ResponseEntity.ok(res);
	}
	
	@PostMapping("/sent/login-signup-otp")
	public ResponseEntity<ApiResponce> sentOtpHandler(@RequestBody LoginOtpRequest req) throws Exception {

		authService.sentLoginOtp(req.getEmail(),req.getRole());
		
		ApiResponce res = new ApiResponce();
		
		res.setMessage("otp sent sucessfully sucess");
	
		return ResponseEntity.ok(res);
	}
	
	@PostMapping("/signin")
	public ResponseEntity<AuthResponse> loginHandler(@RequestBody LoginRequest req) throws Exception {

		AuthResponse authResponse=authService.siging(req);
	
		return ResponseEntity.ok(authResponse);
	}
}
