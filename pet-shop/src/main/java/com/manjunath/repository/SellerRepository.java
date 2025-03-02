package com.manjunath.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.manjunath.domain.AccountStatus;
import com.manjunath.modal.Seller;

@Repository
public interface SellerRepository extends JpaRepository<Seller,Long> {

	Seller findByEmail(String email);
	
	List<Seller> findByAccountStatus(AccountStatus accountStatus);
}
