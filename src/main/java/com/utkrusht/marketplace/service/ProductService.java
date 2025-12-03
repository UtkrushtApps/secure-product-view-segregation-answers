package com.utkrusht.marketplace.service;

import com.utkrusht.marketplace.dto.ProductAdminDTO;
import com.utkrusht.marketplace.dto.ProductUserDTO;
import com.utkrusht.marketplace.entity.Product;
import com.utkrusht.marketplace.exception.NotFoundException;
import com.utkrusht.marketplace.repository.ProductRepository;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    @PersistenceContext
    private EntityManager entityManager;
    
    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Returns true if current user is ADMIN
    private boolean isAdmin() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getAuthorities() == null) return false;
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        for (GrantedAuthority ga : authorities) {
            if ("ROLE_ADMIN".equals(ga.getAuthority())) {
                return true;
            }
        }
        return false;
    }

    @Transactional(readOnly = true)
    public List<?> getAllProducts() {
        if (isAdmin()) {
            // Eagerly fetch suppliers if supplierContract is a heavy/mapped field
            List<Product> products = productRepository.findAllWithSupplierDetails();
            return products.stream().map(this::toAdminDTO).collect(Collectors.toList());
        } else {
            List<Product> products = productRepository.findAll();
            return products.stream().map(this::toUserDTO).collect(Collectors.toList());
        }
    }

    @Transactional(readOnly = true)
    public Object getProductById(Long id) {
        if (isAdmin()) {
            // Use custom query if needed for eager fetching
            Product product = productRepository.findByIdWithSupplierDetails(id)
                .orElseThrow(() -> new NotFoundException("Product not found with id: " + id));
            return toAdminDTO(product);
        } else {
            Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found with id: " + id));
            return toUserDTO(product);
        }
    }

    private ProductAdminDTO toAdminDTO(Product p) {
        return new ProductAdminDTO(
                p.getId(),
                p.getName(),
                p.getDescription(),
                p.getSalePrice(),
                p.getCostPrice(),
                p.getSupplierContract()
        );
    }

    private ProductUserDTO toUserDTO(Product p) {
        return new ProductUserDTO(
                p.getId(),
                p.getName(),
                p.getDescription(),
                p.getSalePrice()
        );
    }
}
