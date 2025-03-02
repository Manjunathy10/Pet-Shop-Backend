package com.manjunath.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manjunath.modal.User;

public interface UserRepository extends JpaRepository<User,Long> {
	
	User findByEmail(String email);

}
