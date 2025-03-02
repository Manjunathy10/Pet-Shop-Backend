package com.manjunath.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.manjunath.modal.VerificationCode;

@Repository
public interface VerificationCodeRepository extends JpaRepository<VerificationCode,Long> {

	VerificationCode findByEmail(String email);
	
	VerificationCode findByOtp(String otp);
	
}
