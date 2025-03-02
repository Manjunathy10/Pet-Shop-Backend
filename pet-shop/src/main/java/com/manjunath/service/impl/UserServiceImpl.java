package com.manjunath.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manjunath.config.JwtProvider;
import com.manjunath.modal.User;
import com.manjunath.repository.UserRepository;
import com.manjunath.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JwtProvider jwtProvider;

	@Override
	public User findUserByJwtToken(String jwt) throws Exception {
		
		String email=jwtProvider.getEmailFromJwtToken(jwt);
	
		return this.findUserByEmail(email);
	}

	@Override
	public User findUserByEmail(String email) throws Exception {
		
		User user=userRepository.findByEmail(email);
		
		if(user==null) {
			throw new Exception("user not found for with email -"+email);
		}
		return user;
	}

}
