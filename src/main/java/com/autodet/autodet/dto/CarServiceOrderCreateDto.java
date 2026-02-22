package com.autodet.autodet.dto;

import lombok.Data;
import java.util.List;

@Data
public class CarServiceOrderCreateDto {
    private String customerName;
    private String phone;
    private String carBrand;
    private List<String> services;
}
