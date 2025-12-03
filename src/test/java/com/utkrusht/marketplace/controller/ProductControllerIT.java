package com.utkrusht.marketplace.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.utkrusht.marketplace.entity.Product;
import com.utkrusht.marketplace.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerIT {
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        productRepository.deleteAll();
        Product p1 = new Product();
        p1.setName("Bundle One");
        p1.setDescription("First cool thing");
        p1.setSalePrice(BigDecimal.valueOf(400));
        p1.setCostPrice(BigDecimal.valueOf(200));
        p1.setSupplierContract("Supplier #1 Contract");
        productRepository.save(p1);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void adminSeesInternalFields() throws Exception {
        String resp = mockMvc.perform(get("/api/products").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();
        List<Map<String, Object>> list = objectMapper.readValue(resp, new TypeReference<>(){});
        assertThat(list).hasSize(1);
        assertThat(list.get(0)).containsKeys("id", "name", "description", "costPrice", "supplierContract", "salePrice");
        assertThat(list.get(0).get("costPrice")).isEqualTo(200);
        assertThat(list.get(0).get("supplierContract")).isEqualTo("Supplier #1 Contract");
    }

    @Test
    @WithMockUser(roles = "USER")
    public void regularUserNeverSeesInternalFields() throws Exception {
        String resp = mockMvc.perform(get("/api/products").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();
        List<Map<String, Object>> list = objectMapper.readValue(resp, new TypeReference<>(){});
        assertThat(list).hasSize(1);
        assertThat(list.get(0)).doesNotContainKeys("costPrice", "supplierContract");
        assertThat(list.get(0)).containsKeys("id", "name", "description", "salePrice");
    }

    @Test
    public void unauthenticatedUserDenied() throws Exception {
        mockMvc.perform(get("/api/products").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isForbidden()); // security config assumes basic auth/JWT required
    }
}
