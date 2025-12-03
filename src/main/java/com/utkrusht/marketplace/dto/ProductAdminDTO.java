package com.utkrusht.marketplace.dto;

import java.math.BigDecimal;

public class ProductAdminDTO {
    private Long id;
    private String name;
    private String description;
    private BigDecimal salePrice;
    private BigDecimal costPrice;
    private String supplierContract;

    public ProductAdminDTO() {}

    public ProductAdminDTO(Long id, String name, String description, BigDecimal salePrice, BigDecimal costPrice, String supplierContract) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.salePrice = salePrice;
        this.costPrice = costPrice;
        this.supplierContract = supplierContract;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    public String getSupplierContract() {
        return supplierContract;
    }

    public void setSupplierContract(String supplierContract) {
        this.supplierContract = supplierContract;
    }
}
