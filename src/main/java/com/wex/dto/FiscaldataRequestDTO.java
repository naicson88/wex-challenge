package com.wex.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FiscaldataRequestDTO {

    private List<FiscaldataRequestDataDTO> data;
}
