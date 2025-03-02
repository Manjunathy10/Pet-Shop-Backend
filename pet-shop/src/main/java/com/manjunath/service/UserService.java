package com.manjunath.service;

import com.manjunath.modal.User;

public interface UserService {

	User findUserByJwtToken(String jwt) throws Exception;

	User findUserByEmail(String email) throws Exception;	
}
