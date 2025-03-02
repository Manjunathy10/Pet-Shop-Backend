package com.manjunath.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.manjunath.modal.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long>{

	
}
