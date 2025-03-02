package com.manjunath.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.manjunath.config.JwtProvider;
import com.manjunath.domain.USER_ROLE;
import com.manjunath.modal.Cart;
import com.manjunath.modal.Seller;
import com.manjunath.modal.User;
import com.manjunath.modal.VerificationCode;
import com.manjunath.repository.CartRepository;
import com.manjunath.repository.SellerRepository;
import com.manjunath.repository.UserRepository;
import com.manjunath.repository.VerificationCodeRepository;
import com.manjunath.request.LoginRequest;
import com.manjunath.responce.AuthResponse;
import com.manjunath.responce.SignupRequest;
import com.manjunath.service.AuthService;
import com.manjunath.service.EmailService;
import com.manjunath.utils.OtpUtil;

@Service
//@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
	@Autowired
	private UserRepository userRepository = null;
	@Autowired
	private PasswordEncoder passwordEncoder = null;
	@Autowired
	private CartRepository CartRepository = null;
	@Autowired
	private JwtProvider jwtProvider = new JwtProvider();

	@Autowired
	private VerificationCodeRepository verificationCodeRepository;

	@Autowired
	private EmailService emailService;

	@Autowired
	private CustomUserServiceImpl customUserService;

	@Autowired
	private SellerRepository sellerRepository;

	@Override
	public String createUser(SignupRequest req) throws Exception {

		VerificationCode verificationCode = verificationCodeRepository.findByEmail(req.getEmail());

		if (verificationCode == null || !verificationCode.getOtp().equals(req.getOtp())) {

			throw new Exception("wrong otp");
		}

		User user = userRepository.findByEmail(req.getEmail());

		if (user == null) {
			User createdUser = new User();
			createdUser.setEmail(req.getEmail());
			createdUser.setFullName(req.getFullname());
			createdUser.setRole(USER_ROLE.ROLE_CUSTOMER);
			createdUser.setMobile("7387911652");
			createdUser.setPassword(passwordEncoder.encode(req.getOtp()));

			user = userRepository.save(createdUser);

			Cart cart = new Cart();
			cart.setUser(user);
			CartRepository.save(cart);

		}

		List<GrantedAuthority> authorities = new ArrayList<>();

		authorities.add(new SimpleGrantedAuthority(USER_ROLE.ROLE_CUSTOMER.toString()));

		Authentication authentication = new UsernamePasswordAuthenticationToken(req.getEmail(), null, authorities);

		SecurityContextHolder.getContext().setAuthentication(authentication);

		return jwtProvider.generateToken(authentication);
	}

	@Override
	public void sentLoginOtp(String email, USER_ROLE role) throws Exception {
		String SIGNING_PREFIX = "signing_";

		if (email.startsWith(SIGNING_PREFIX)) {
			email = email.substring(SIGNING_PREFIX.length());

			if (role.equals(USER_ROLE.ROLE_SELLER)) {

				Seller seller = sellerRepository.findByEmail(email);
				if (seller == null) {
					throw new Exception("seller not found");
				}

			} else {
				User user = userRepository.findByEmail(email);
				if (user == null) {
					throw new Exception("user not exit with provided email");
				}
			}

		}

		VerificationCode isExit = verificationCodeRepository.findByEmail(email);

		if (isExit != null) {
			verificationCodeRepository.delete(isExit);
		}

		String otp = OtpUtil.generateOtp();

		VerificationCode verificationCode = new VerificationCode();
		verificationCode.setOtp(otp);
		verificationCode.setEmail(email);
		verificationCodeRepository.save(verificationCode);

		String subject = "manjunath's pet shop ";
		String text = "your login otp is -" + otp + " do not share any one";

		emailService.sendVerificationOtpEmail(email, otp, subject, text);

	}

	@Override
	public AuthResponse siging(LoginRequest req) throws Exception {

		String username = req.getEmail();
		String otp = req.getOtp();

		Authentication authentication = authenticat(username, otp);

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = jwtProvider.generateToken(authentication);

		AuthResponse authResponse = new AuthResponse();

		authResponse.setJwt(token);
		authResponse.setMessage("Login Success");

		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

		String roleName = authorities.isEmpty() ? null : authorities.iterator().next().getAuthority();

		authResponse.setRole(USER_ROLE.valueOf(roleName));

		return authResponse;
	}

	private Authentication authenticat(String username, String otp) throws Exception {

		UserDetails userDetails = customUserService.loadUserByUsername(username);

		
		String SELLER_PREFIX = "seller_";
		
		if (username.startsWith(SELLER_PREFIX)) {

			username=username.substring(SELLER_PREFIX.length());

		}

		if (userDetails == null) {
			throw new BadCredentialsException("Invalid UserName");
		}

		VerificationCode verificationCode = verificationCodeRepository.findByEmail(username);

		if (verificationCode == null || !verificationCode.getOtp().equals(otp)) {

			throw new Exception("wrong otp");
		}

		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	}

}
