package com.wex.service;

import com.wex.dto.FiscaldataRequestDTO;
import com.wex.dto.PurchaseTransactionRetrieveDTO;
import com.wex.dto.PurchaseTransactionStoreDTO;
import com.wex.entity.PurchaseTransaction;
import com.wex.repository.PurchaseTransactionRepository;
import com.wex.request.FiscaldataRequest;
import com.wex.util.NumberFormatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;

@Service
public class PurchaseTransactionService {

    private final String FIELDS = "record_date,country,currency,country_currency_desc,exchange_rate";
    private final String RECORD_DATE = "record_date:gte:";
    private final String CURRENCY = ",currency:eq:";

    Logger logger = LoggerFactory.getLogger(PurchaseTransactionService.class);

    @Autowired
    private PurchaseTransactionRepository repository;

    @Autowired
    private FiscaldataRequest fiscaldataRequest;

    @Transactional(rollbackFor = {Exception.class})
    public PurchaseTransaction create(PurchaseTransactionStoreDTO dto){

        PurchaseTransaction newPurchase = new PurchaseTransaction(dto.getDescription(),
                NumberFormatUtil.doubleDecimalFormat(dto.getPurchaseAmount()), LocalDateTime.now());

        return repository.save(newPurchase);
    }

    public PurchaseTransactionRetrieveDTO retrieve(Integer id, String currency){
        PurchaseTransaction transaction = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find Purchase Transaction with Id: " + id));

       PurchaseTransactionRetrieveDTO retrieveDTO = convertPurchaseTransactionEntityToDTO(transaction);

        double exchangeRate = findExchangeRate(currency, retrieveDTO.getTransactionDate());

        retrieveDTO.setExchangeRate(exchangeRate);
        retrieveDTO.setConvertedAmount(NumberFormatUtil.doubleDecimalFormat(exchangeRate * retrieveDTO.getPurchaseAmount()));

        return retrieveDTO;
    }

    private double findExchangeRate(String currency, LocalDateTime time) {

        String timeString = time.minusMonths(6).format(DateTimeFormatter.ofPattern("yyyy-dd-MM"));

        FiscaldataRequestDTO dto = fiscaldataRequest.getExchangeRate( FIELDS,RECORD_DATE + timeString + CURRENCY + currency);

        if(dto == null || dto.getData() == null || dto.getData().isEmpty())
            throw new NoSuchElementException("The purchase cannot be converted to the target currency");

        String exchangeRate = dto.getData().get(0).getExchange_rate();

        return NumberFormatUtil.doubleDecimalFormat(Double.parseDouble(exchangeRate));
    }

    private PurchaseTransactionRetrieveDTO convertPurchaseTransactionEntityToDTO(PurchaseTransaction transaction) throws BeansException {
           PurchaseTransactionRetrieveDTO dto = new PurchaseTransactionRetrieveDTO();
           BeanUtils.copyProperties(transaction, dto);
           return dto;
    }
}
