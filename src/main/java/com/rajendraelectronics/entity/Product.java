package com.rajendraelectronics.entity;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String brand;

    private String category; 
    
    private Double purchasePrice; 
    
    private Double sellingPrice; 
    
    private Integer quantityInStock; 
    
    private Integer reorderThreshold;

	public Product() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Product(Long id, String name, String brand, String category, Double purchasePrice, Double sellingPrice,
			Integer quantityInStock, Integer reorderThreshold) {
		super();
		this.id = id;
		this.name = name;
		this.brand = brand;
		this.category = category;
		this.purchasePrice = purchasePrice;
		this.sellingPrice = sellingPrice;
		this.quantityInStock = quantityInStock;
		this.reorderThreshold = reorderThreshold;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Double getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(Double purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public Double getSellingPrice() {
		return sellingPrice;
	}

	public void setSellingPrice(Double sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	public Integer getQuantityInStock() {
		return quantityInStock;
	}

	public void setQuantityInStock(Integer quantityInStock) {
		this.quantityInStock = quantityInStock;
	}

	public Integer getReorderThreshold() {
		return reorderThreshold;
	}

	public void setReorderThreshold(Integer reorderThreshold) {
		this.reorderThreshold = reorderThreshold;
	}

	@Override
	public int hashCode() {
		return Objects.hash(brand, category, id, name, purchasePrice, quantityInStock, reorderThreshold, sellingPrice);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		return Objects.equals(brand, other.brand) && Objects.equals(category, other.category)
				&& Objects.equals(id, other.id) && Objects.equals(name, other.name)
				&& Objects.equals(purchasePrice, other.purchasePrice)
				&& Objects.equals(quantityInStock, other.quantityInStock)
				&& Objects.equals(reorderThreshold, other.reorderThreshold)
				&& Objects.equals(sellingPrice, other.sellingPrice);
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", brand=" + brand + ", category=" + category
				+ ", purchasePrice=" + purchasePrice + ", sellingPrice=" + sellingPrice + ", quantityInStock="
				+ quantityInStock + ", reorderThreshold=" + reorderThreshold + "]";
	}
    
    

}
