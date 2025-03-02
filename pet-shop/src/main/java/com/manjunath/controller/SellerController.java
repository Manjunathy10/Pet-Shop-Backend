package com.manjunath.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.manjunath.Exception.SellerException;
import com.manjunath.domain.AccountStatus;
import com.manjunath.modal.Seller;
import com.manjunath.modal.VerificationCode;
import com.manjunath.repository.SellerRepository;
import com.manjunath.repository.VerificationCodeRepository;
import com.manjunath.request.LoginRequest;
import com.manjunath.responce.AuthResponse;
import com.manjunath.service.AuthService;
import com.manjunath.service.EmailService;
import com.manjunath.service.SellerService;
import com.manjunath.utils.OtpUtil;

/**
 * 
 */
@RestController
@RequestMapping("/sellers")
public class SellerController {

	@Autowired
	private SellerService sellerService;
	@Autowired
	private VerificationCodeRepository verificationCodeRepository;
	@Autowired
	private AuthService authService;
	@Autowired
	private EmailService emailService;
	@Autowired
	private SellerRepository sellerRepository;

	@PostMapping("/login")
	public ResponseEntity<AuthResponse> sellerLogin(@RequestBody LoginRequest req) throws Exception {
		String otp = req.getOtp();
		String email = req.getEmail();
		req.setEmail("seller_" + email);
//		req.setOtp(otp);

		AuthResponse authResponse = authService.siging(req);
		return ResponseEntity.ok(authResponse);
	}

	@PatchMapping("verify/{otp}")
	public ResponseEntity<Seller> verifySellerEmail(@PathVariable String otp) throws Exception {

		VerificationCode verificationCode = verificationCodeRepository.findByOtp(otp);
		if (verificationCode == null || !verificationCode.getOtp().equals(otp)) {
			throw new Exception("wrong otp");
		}

		Seller seller = sellerService.verifyEmail(verificationCode.getEmail(), otp);
		return new ResponseEntity<>(seller, HttpStatus.OK);

	}

	@PostMapping("/")
	public ResponseEntity<Seller> createSeller(@RequestBody Seller seller) throws Exception {

		Seller savedSeller = sellerService.createSeller(seller);

		String otp = OtpUtil.generateOtp();

		VerificationCode verificationCode = new VerificationCode();
		verificationCode.setOtp(otp);
		verificationCode.setEmail(seller.getEmail());
		verificationCodeRepository.save(verificationCode);

		String subject = "pet shop email verification code";
		String text = "welcome to pet shop , verify your account using this link : ";
		String fronted_url = "hhtp://localhost:3000/verify-seller";
		emailService.sendVerificationOtpEmail(seller.getEmail(), verificationCode.getOtp(), subject,
				text + fronted_url);

		return new ResponseEntity<>(savedSeller, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Seller> getSellerById(@PathVariable Long id) throws SellerException {

		Seller seller = sellerRepository.findById(id)
				.orElseThrow(() -> new SellerException("seller is not found "));

		return new ResponseEntity<>(seller, HttpStatus.OK);
	}

	@GetMapping("/profile")
	public ResponseEntity<Seller> getSellerByJwt(@RequestHeader("Authorization") String jwt) throws Exception {

		Seller seller = sellerService.getSellerProfile(jwt);

		return new ResponseEntity<>(seller, HttpStatus.OK);

	}
//	
//	public ResponseEntity<SellerReport> getSellerReport(@RequestHeader("Authorization") String jwt){
//		
//		
//	}

	@GetMapping("/")
	public ResponseEntity<List<Seller>> getAllSeller(@RequestParam(required = false) AccountStatus status) {
		List<Seller> allSeller = sellerService.getAllSeller(status);
		return ResponseEntity.ok(allSeller);
	}

	@PatchMapping("/update")
	public ResponseEntity<Seller> updateSeller(@RequestHeader("Authorization") String jwt, @RequestBody Seller seller)
			throws Exception {
		Seller profile = sellerService.getSellerProfile(jwt);
		Seller updateSeller = sellerService.UpdateSeller(profile.getId(), seller);
		return ResponseEntity.ok(updateSeller);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteSeller(@PathVariable Long id) throws Exception {
		sellerService.deleteSeller(id);
		return ResponseEntity.noContent().build();
	}

}
