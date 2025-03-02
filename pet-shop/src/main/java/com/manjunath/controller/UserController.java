package com.manjunath.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.manjunath.modal.User;
import com.manjunath.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping("users/profile")
	public ResponseEntity<User> createUserHandler(
			@RequestHeader("Authorization") String jwt
			) throws Exception {

		User user =userService.findUserByJwtToken(jwt);

		return ResponseEntity.ok(user);
	}
}
