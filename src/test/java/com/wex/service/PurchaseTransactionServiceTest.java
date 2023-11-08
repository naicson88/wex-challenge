package com.wex.service;

import com.wex.dto.FiscaldataRequestDTO;
import com.wex.dto.PurchaseTransactionRetrieveDTO;
import com.wex.entity.PurchaseTransaction;
import com.wex.repository.PurchaseTransactionRepository;
import com.wex.request.FiscaldataRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
public class PurchaseTransactionServiceTest {

    @InjectMocks
    @Spy
    PurchaseTransactionService service;

    @Mock
    PurchaseTransactionRepository repository;
    @Mock
    FiscaldataRequest request;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testPurchaseTransactionFoundSuccessfully(){
        PurchaseTransaction transaction = new PurchaseTransaction("description", 2.95, LocalDateTime.now());
        Mockito.when(repository.findById(anyInt())).thenReturn(Optional.of(transaction));

        PurchaseTransactionRetrieveDTO retrieveDTO = service.retrieve(1, "test");

        assertEquals(transaction.getDescription(), retrieveDTO.getDescription());
        assertEquals(transaction.getPurchaseAmount(), retrieveDTO.getPurchaseAmount());

    }
    @Test
    public void testeWhenPurchaseNotFound(){

        Mockito.when(repository.findById(anyInt())).thenReturn(Optional.empty());

        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            service.retrieve(1, "test");
        });

        String expected = "Cannot find Purchase Transaction with Id: " + 1;
        String actual = exception.getMessage();

        assertTrue(actual.contains(expected));
    }

    @Test
    public void testeWhenNoExchangeRateIsNull(){

        PurchaseTransaction transaction = new PurchaseTransaction("description", 2.95, LocalDateTime.now());
        Mockito.when(repository.findById(anyInt())).thenReturn(Optional.of(transaction));
        Mockito.when(request.getExchangeRate(anyString(),anyString())).thenReturn(null);

        NoSuchElementException exception = Assertions.assertThrows(NoSuchElementException.class, () -> {
            service.retrieve(1, "Real");
        });

        String expected = "The purchase cannot be converted to the target currency";
        String actual = exception.getMessage();

        assertTrue(actual.contains(expected));
    }

    @Test
    public void testeWhenNoExchangeRateIsEmpty(){

        PurchaseTransaction transaction = new PurchaseTransaction("description", 2.95, LocalDateTime.now());

        FiscaldataRequestDTO dto = new FiscaldataRequestDTO();
        dto.setData(Collections.emptyList());

        Mockito.when(repository.findById(anyInt())).thenReturn(Optional.of(transaction));
        Mockito.when(request.getExchangeRate(anyString(),anyString())).thenReturn(dto);

        NoSuchElementException exception = Assertions.assertThrows(NoSuchElementException.class, () -> {
            service.retrieve(1, "Real");
        });

        String expected = "The purchase cannot be converted to the target currency";
        String actual = exception.getMessage();

        assertTrue(actual.contains(expected));
    }

}
