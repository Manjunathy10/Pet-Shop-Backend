package com.manjunath.service;

import java.util.List;

import com.manjunath.Exception.SellerException;
import com.manjunath.domain.AccountStatus;
import com.manjunath.modal.Seller;

public interface SellerService {

		Seller getSellerProfile(String jwt) throws Exception;
		
		Seller createSeller(Seller seller) throws Exception;
		
		Seller getSellerById(Long id) throws SellerException;
		
		Seller getSellerByEmail(String email) throws Exception;
		
		List<Seller> getAllSeller(AccountStatus status);
		
		Seller UpdateSeller(Long id,Seller seller) throws Exception;
		
		void deleteSeller(Long id) throws Exception;
		
		Seller verifyEmail(String email,String otp) throws Exception;
		
		Seller updateSellerAccountStatus(Long sellerId,AccountStatus status) throws Exception;
}
