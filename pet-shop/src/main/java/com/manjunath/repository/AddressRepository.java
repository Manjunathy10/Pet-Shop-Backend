package com.manjunath.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manjunath.modal.Address;

public interface AddressRepository extends JpaRepository<Address,Long>{

}
