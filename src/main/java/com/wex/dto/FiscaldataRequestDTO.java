package com.wex.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FiscaldataRequestDTO {

    private List<FiscaldataRequestDataDTO> data;
}
