package com.rajendraelectronics.entity;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "purchases")
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer quantity;

    private Double purchasePrice;

    private Double totalAmount;

    private LocalDate purchaseDate;

    public Purchase() {
    }

    public Purchase(Long id, Supplier supplier, Product product, Integer quantity,
                    Double purchasePrice, Double totalAmount, LocalDate purchaseDate) {
        this.id = id;
        this.supplier = supplier;
        this.product = product;
        this.quantity = quantity;
        this.purchasePrice = purchasePrice;
        this.totalAmount = totalAmount;
        this.purchaseDate = purchaseDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, product, purchaseDate, purchasePrice,
                quantity, supplier, totalAmount);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Purchase))
            return false;

        Purchase other = (Purchase) obj;

        return Objects.equals(id, other.id)
                && Objects.equals(product, other.product)
                && Objects.equals(purchaseDate, other.purchaseDate)
                && Objects.equals(purchasePrice, other.purchasePrice)
                && Objects.equals(quantity, other.quantity)
                && Objects.equals(supplier, other.supplier)
                && Objects.equals(totalAmount, other.totalAmount);
    }

    @Override
    public String toString() {
        return "Purchase [id=" + id
                + ", supplier=" + supplier
                + ", product=" + product
                + ", quantity=" + quantity
                + ", purchasePrice=" + purchasePrice
                + ", totalAmount=" + totalAmount
                + ", purchaseDate=" + purchaseDate + "]";
    }
}