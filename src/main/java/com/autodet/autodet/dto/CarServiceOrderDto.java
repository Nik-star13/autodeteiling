package com.autodet.autodet.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CarServiceOrderDto {
    private Long id;
    private String customerName;
    private String phone;
    private String carBrand;
    private LocalDateTime orderDate;
    private String status;
    private BigDecimal totalAmount;
    private List<String> services;
}
