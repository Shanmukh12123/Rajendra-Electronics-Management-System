package com.rajendraelectronics.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "sales")
public class Sale {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String invoiceNumber;
	
	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;
	
	@OneToMany(mappedBy = "sale", cascade = CascadeType.ALL)
	private List<SaleItem> saleItems;

    private Double totalAmount;

    private Double paidAmount;

    private Double remainingAmount;
    
    private String paymentType;

    private LocalDate saleDate;

	public Sale() {
		super();
	}

	public Sale(Long id, String invoiceNumber, Customer customer, List<SaleItem> saleItems, Double totalAmount,
			Double paidAmount, Double remainingAmount, String paymentType, LocalDate saleDate) {
		super();
		this.id = id;
		this.invoiceNumber = invoiceNumber;
		this.customer = customer;
		this.saleItems = saleItems;
		this.totalAmount = totalAmount;
		this.paidAmount = paidAmount;
		this.remainingAmount = remainingAmount;
		this.paymentType = paymentType;
		this.saleDate = saleDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<SaleItem> getSaleItems() {
		return saleItems;
	}

	public void setSaleItems(List<SaleItem> saleItems) {
		this.saleItems = saleItems;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Double getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(Double paidAmount) {
		this.paidAmount = paidAmount;
	}

	public Double getRemainingAmount() {
		return remainingAmount;
	}

	public void setRemainingAmount(Double remainingAmount) {
		this.remainingAmount = remainingAmount;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public LocalDate getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(LocalDate saleDate) {
		this.saleDate = saleDate;
	}

	@Override
	public int hashCode() {
		return Objects.hash(customer, id, invoiceNumber, paidAmount, paymentType, remainingAmount, saleDate, saleItems,
				totalAmount);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sale other = (Sale) obj;
		return Objects.equals(customer, other.customer) && Objects.equals(id, other.id)
				&& Objects.equals(invoiceNumber, other.invoiceNumber) && Objects.equals(paidAmount, other.paidAmount)
				&& Objects.equals(paymentType, other.paymentType)
				&& Objects.equals(remainingAmount, other.remainingAmount) && Objects.equals(saleDate, other.saleDate)
				&& Objects.equals(saleItems, other.saleItems) && Objects.equals(totalAmount, other.totalAmount);
	}

	@Override
	public String toString() {
		return "Sale [id=" + id + ", invoiceNumber=" + invoiceNumber + ", customer=" + customer + ", saleItems="
				+ saleItems + ", totalAmount=" + totalAmount + ", paidAmount=" + paidAmount + ", remainingAmount="
				+ remainingAmount + ", paymentType=" + paymentType + ", saleDate=" + saleDate + "]";
	}

}
