package com.manjunath.modal;

import com.manjunath.domain.AccountStatus;
import com.manjunath.domain.USER_ROLE;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Seller {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String sellerName;

	private String mobile;

	@Column(unique=true,nullable=false)
	private String email;

	private String password;

	@Embedded
	private BusinessDetail businessDetail = new BusinessDetail();

	@Embedded
	private BankDetails bankDetails = new BankDetails();

	@OneToOne(cascade=CascadeType.ALL)
	private Address pickupAddress = new Address();

	private String GSTIN;

	private USER_ROLE role=USER_ROLE.ROLE_SELLER;

	private boolean isEmailVarified = false;

	private AccountStatus accountStatus = AccountStatus.PENDING_VERIFICATION;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public BusinessDetail getBusinessDetail() {
		return businessDetail;
	}

	public void setBusinessDetail(BusinessDetail businessDetail) {
		this.businessDetail = businessDetail;
	}

	public BankDetails getBankDetails() {
		return bankDetails;
	}

	public void setBankDetails(BankDetails bankDetails) {
		this.bankDetails = bankDetails;
	}

	public Address getPickupAddress() {
		return pickupAddress;
	}

	public void setPickupAddress(Address pickupAddress) {
		this.pickupAddress = pickupAddress;
	}

	public String getGSTIN() {
		return GSTIN;
	}

	public void setGSTIN(String gSTIN) {
		GSTIN = gSTIN;
	}

	public USER_ROLE getRole() {
		return role;
	}

	public void setRole(USER_ROLE role) {
		this.role = role;
	}

	public boolean isEmailVarified() {
		return isEmailVarified;
	}

	public void setEmailVarified(boolean isEmailVarified) {
		this.isEmailVarified = isEmailVarified;
	}

	public AccountStatus getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(AccountStatus accountStatus) {
		this.accountStatus = accountStatus;
	}

	@Override
	public String toString() {
		return "Seller [id=" + id + ", sellerName=" + sellerName + ", mobile=" + mobile + ", email=" + email
				+ ", password=" + password + ", businessDetail=" + businessDetail + ", bankDetails=" + bankDetails
				+ ", pickupAddress=" + pickupAddress + ", GSTIN=" + GSTIN + ", role=" + role + ", isEmailVarified="
				+ isEmailVarified + ", accountStatus=" + accountStatus + "]";
	}

	public Seller() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	

}
