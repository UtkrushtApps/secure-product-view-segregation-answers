package com.utkrusht.marketplace.dto;

import java.math.BigDecimal;

public class ProductUserDTO {
    private Long id;
    private String name;
    private String description;
    private BigDecimal salePrice;

    public ProductUserDTO() {}
    
    public ProductUserDTO(Long id, String name, String description, BigDecimal salePrice) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.salePrice = salePrice;
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
}
