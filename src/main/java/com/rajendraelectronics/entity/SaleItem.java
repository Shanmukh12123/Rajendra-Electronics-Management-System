package com.rajendraelectronics.entity;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "sale_items")
public class SaleItem {

	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long id;

	 @ManyToOne
	 @JoinColumn(name = "sale_id")
	 private Sale sale;
	 
	 @ManyToOne
	 @JoinColumn(name = "product_id")
	 private Product product;

	 private String productName;

	 private Integer quantity;

	 private Double unitPrice;

	 private Double totalPrice;

	 public SaleItem() {
		super();
		// TODO Auto-generated constructor stub
	 }

	 public SaleItem(Long id, Sale sale, Product product, String productName, Integer quantity, Double unitPrice,
			Double totalPrice) {
		super();
		this.id = id;
		this.sale = sale;
		this.product = product;
		this.productName = productName;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
		this.totalPrice = totalPrice;
	 }

	 public Long getId() {
		 return id;
	 }

	 public void setId(Long id) {
		 this.id = id;
	 }

	 public Sale getSale() {
		 return sale;
	 }

	 public void setSale(Sale sale) {
		 this.sale = sale;
	 }

	 public Product getProduct() {
		 return product;
	 }

	 public void setProduct(Product product) {
		 this.product = product;
	 }

	 public String getProductName() {
		 return productName;
	 }

	 public void setProductName(String productName) {
		 this.productName = productName;
	 }

	 public Integer getQuantity() {
		 return quantity;
	 }

	 public void setQuantity(Integer quantity) {
		 this.quantity = quantity;
	 }

	 public Double getUnitPrice() {
		 return unitPrice;
	 }

	 public void setUnitPrice(Double unitPrice) {
		 this.unitPrice = unitPrice;
	 }

	 public Double getTotalPrice() {
		 return totalPrice;
	 }

	 public void setTotalPrice(Double totalPrice) {
		 this.totalPrice = totalPrice;
	 }

	 @Override
	 public int hashCode() {
		return Objects.hash(id, product, productName, quantity, sale, totalPrice, unitPrice);
	 }

	 @Override
	 public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SaleItem other = (SaleItem) obj;
		return Objects.equals(id, other.id) && Objects.equals(product, other.product)
				&& Objects.equals(productName, other.productName) && Objects.equals(quantity, other.quantity)
				&& Objects.equals(sale, other.sale) && Objects.equals(totalPrice, other.totalPrice)
				&& Objects.equals(unitPrice, other.unitPrice);
	 }

	 @Override
	 public String toString() {
		return "SaleItem [id=" + id + ", sale=" + sale + ", product=" + product + ", productName=" + productName
				+ ", quantity=" + quantity + ", unitPrice=" + unitPrice + ", totalPrice=" + totalPrice + "]";
	 }

}