package com.wex.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wex.dto.PurchaseTransactionStoreDTO;
import com.wex.entity.PurchaseTransaction;
import com.wex.repository.PurchaseTransactionRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
 class PurchaseTransactionControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PurchaseTransactionRepository repository;

    @BeforeEach
    void up(){
        PurchaseTransaction transaction = new PurchaseTransaction();
        transaction.setDescription("Teste mock");
        transaction.setPurchaseAmount(100.00);
        transaction.setTransactionDate(LocalDateTime.now());
        repository.save(transaction);
    }

    @AfterEach
    void down(){
        repository.deleteAll();
    }

    @Test
     void testCreatePurchaseTransaction() throws Exception {
        PurchaseTransactionStoreDTO dto = new PurchaseTransactionStoreDTO();
        dto.setDescription("Teste mock");
        dto.setPurchaseAmount(100.00);

        ResultActions resultActions = mockMvc.perform(post("/api/v1/purchase-transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());

    }

    @Test
     void testCreatePurchaseTransactionFail() throws Exception {
        PurchaseTransactionStoreDTO dto = new PurchaseTransactionStoreDTO();

        ResultActions resultActions = mockMvc.perform(post("/api/v1/purchase-transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());

    }

    @Test
     void testRetrievePurchaseTransaction() throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/api/v1/purchase-transaction/{id}/{currency}", 1, "Real"))
                .andExpect(status().isOk());

    }
}
