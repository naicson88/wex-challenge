package com.wex.controller.v1;

import com.wex.dto.PurchaseTransactionStoreDTO;
import com.wex.entity.PurchaseTransaction;
import com.wex.service.PurchaseTransactionService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping({"api/v1/purchase-transaction"})
@Validated
public class PurchaseTransactionController {

    @Autowired
    private PurchaseTransactionService service;

    @PostMapping("/create")
    public ResponseEntity<PurchaseTransaction> create(@Valid @RequestBody PurchaseTransactionStoreDTO dto){
        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }

    @GetMapping("/retrieve/{id}/{currency}")
    public ResponseEntity<PurchaseTransaction> retrieve(@PathVariable() Integer id, @PathVariable() String currency){
        return new ResponseEntity<>(service.retrieve(id, currency), HttpStatus.OK);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    ResponseEntity<Object> handleConstraintViolationException(MethodArgumentNotValidException em) {
        List<FieldError> fieldErrors = em.getBindingResult().getFieldErrors();
        String errorMessage = fieldErrors.get(0).getDefaultMessage();
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
}
