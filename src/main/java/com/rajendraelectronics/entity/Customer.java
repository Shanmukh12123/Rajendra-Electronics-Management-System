package com.rajendraelectronics.entity;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "customers")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	private String customerName;

	@Pattern(regexp = "^\\d{10}$", message = "Mobile number must be 10 digits")
	private String mobileNumber;

	@Email
	private String email;

	private String address;

	public Customer() {}

	public Customer(Long id, String customerName, String mobileNumber, String email, String address) {
		this.id = id;
		this.customerName = customerName;
		this.mobileNumber = mobileNumber;
		this.email = email;
		this.address = address;
	}

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }

	public String getCustomerName() { return customerName; }
	public void setCustomerName(String customerName) { this.customerName = customerName; }

	public String getMobileNumber() { return mobileNumber; }
	public void setMobileNumber(String mobileNumber) { this.mobileNumber = mobileNumber; }

	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }

	public String getAddress() { return address; }
	public void setAddress(String address) { this.address = address; }

	@Override
	public String toString() {
		return "Customer [id=" + id + ", customerName=" + customerName + ", mobileNumber=" + mobileNumber
				+ ", email=" + email + ", address=" + address + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(address, customerName, email, id, mobileNumber);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Customer other = (Customer) obj;
		return Objects.equals(address, other.address) && Objects.equals(customerName, other.customerName)
				&& Objects.equals(email, other.email) && Objects.equals(id, other.id)
				&& Objects.equals(mobileNumber, other.mobileNumber);
	}
}
