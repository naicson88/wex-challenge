package com.wex.request;

import com.wex.dto.FiscaldataRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "fiscaldata", url = "${url.fiscaldata.treasury}")
public interface FiscaldataRequest {

    @GetMapping("/v1/accounting/od/rates_of_exchange")
    public FiscaldataRequestDTO getExchangeRate(@RequestParam String fields, @RequestParam()String filter);
}
