package com.manjunath.service;

import com.manjunath.domain.USER_ROLE;
import com.manjunath.request.LoginRequest;
import com.manjunath.responce.AuthResponse;
import com.manjunath.responce.SignupRequest;

public interface AuthService {

	void sentLoginOtp(String email ,USER_ROLE role) throws Exception;
	
	String createUser(SignupRequest req) throws Exception;
	
	AuthResponse siging(LoginRequest req) throws Exception;

}
