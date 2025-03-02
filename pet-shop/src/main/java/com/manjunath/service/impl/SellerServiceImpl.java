package com.manjunath.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.manjunath.Exception.SellerException;
import com.manjunath.config.JwtProvider;
import com.manjunath.domain.AccountStatus;
import com.manjunath.domain.USER_ROLE;
import com.manjunath.modal.Address;
import com.manjunath.modal.Seller;
import com.manjunath.repository.AddressRepository;
import com.manjunath.repository.SellerRepository;
import com.manjunath.service.SellerService;

@Service
public class SellerServiceImpl implements SellerService {

	@Autowired
	private SellerRepository sellerRepository;

	@Autowired
	private JwtProvider jwtProvider;

	@Autowired
	private PasswordEncoder passwordEncouder;

	@Autowired
	private AddressRepository addressRepository;	


	@Override
	public Seller getSellerProfile(String jwt) throws Exception {
		String email = jwtProvider.getEmailFromJwtToken(jwt);
		return this.getSellerByEmail(email);
	}

	@Override
	public Seller createSeller(Seller seller) throws Exception {

		Seller sellerExit = sellerRepository.findByEmail(seller.getEmail());

		if (sellerExit != null) {
			throw new Exception("this emailid already use plz try another email id ");
		}

		Address saveAddress = addressRepository.save(seller.getPickupAddress());

		Seller newSeller = new Seller();

		newSeller.setEmail(seller.getEmail());
		newSeller.setPassword(passwordEncouder.encode(seller.getPassword()));
		newSeller.setSellerName(seller.getSellerName());
		newSeller.setPickupAddress(saveAddress);
		newSeller.setGSTIN(seller.getGSTIN());
		newSeller.setRole(USER_ROLE.ROLE_SELLER);
		newSeller.setMobile(seller.getMobile());
		newSeller.setBankDetails(seller.getBankDetails());
		newSeller.setBusinessDetail(seller.getBusinessDetail());

		return sellerRepository.save(newSeller);
	}

	@Override
	public Seller getSellerById(Long id) throws SellerException {

		return sellerRepository.findById(id)
				.orElseThrow(() -> new SellerException("seller is not found"));
	}
	

	@Override
	public Seller getSellerByEmail(String email) throws Exception {
		Seller seller = sellerRepository.findByEmail(email);

		if (seller == null) {
			throw new Exception("sellerr Not found ");
		}
		return seller;
	}

	@Override
	public List<Seller> getAllSeller(AccountStatus status) {

		return sellerRepository.findByAccountStatus(status);
	}

	@Override
	public Seller UpdateSeller(Long id, Seller seller) throws Exception {

		Seller existingSeller = sellerRepository.findById(id)
				.orElseThrow(() -> new Exception("Seller is not found with this email address"));

		if (seller.getSellerName() != null) {
			existingSeller.setSellerName(seller.getSellerName());
		}

		if (seller.getMobile() != null) {
			existingSeller.setMobile(seller.getMobile());
		}

		if (seller.getEmail() != null) {
			existingSeller.setEmail(seller.getEmail());
		}

		if (seller.getBusinessDetail() != null && seller.getBusinessDetail().getBusinessName() != null) {

			existingSeller.getBusinessDetail().setBusinessName(seller.getBusinessDetail().getBusinessName());
		}

		if (seller.getBankDetails() != null && seller.getBankDetails().getAccountHolderName() != null
				&& seller.getBankDetails().getIfscCode() != null
				&& seller.getBankDetails().getAccount_number() != null) {

			existingSeller.getBankDetails().setAccountHolderName(seller.getBankDetails().getAccountHolderName());

			existingSeller.getBankDetails().setAccount_number(seller.getBankDetails().getAccount_number());

			existingSeller.getBankDetails().setIfscCode(seller.getBankDetails().getIfscCode());
		}

		if (seller.getPickupAddress() != null && seller.getPickupAddress().getAddress() != null
				&& seller.getPickupAddress().getMobile() != null && seller.getPickupAddress().getCity() != null
				&& seller.getPickupAddress().getState() != null) {
			existingSeller.getPickupAddress().setAddress(seller.getPickupAddress().getAddress());

			existingSeller.getPickupAddress().setCity(seller.getPickupAddress().getCity());

			existingSeller.getPickupAddress().setState(seller.getPickupAddress().getState());

			existingSeller.getPickupAddress().setMobile(seller.getPickupAddress().getMobile());

			existingSeller.getPickupAddress().setPinCode(seller.getPickupAddress().getPinCode());
		}

		if (seller.getGSTIN() != null) {
			existingSeller.setGSTIN(seller.getGSTIN());
		}

		return sellerRepository.save(existingSeller);

	}

	@Override
	public void deleteSeller(Long id) throws Exception {

		Seller seller = sellerRepository.findById(id)
				.orElseThrow(() -> new Exception("seller not found"));
		
		sellerRepository.delete(seller);

	}

	@Override
	public Seller verifyEmail(String email, String otp) throws Exception {
		
		Seller seller =getSellerByEmail(email);
		seller.setEmailVarified(true);
		return sellerRepository.save(seller);
	}

	@Override
	public Seller updateSellerAccountStatus(Long sellerId, AccountStatus status) throws Exception {
		Seller seller =getSellerById(sellerId);
		seller.setAccountStatus(status);
		return sellerRepository.save(seller);
	}

}
