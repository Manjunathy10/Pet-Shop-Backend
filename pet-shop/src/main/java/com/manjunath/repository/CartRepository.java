package com.manjunath.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manjunath.modal.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

}
