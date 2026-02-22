package com.autodet.autodet.model;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CarServiceOrder {
    private Long id;
    private String customerName;
    private String phone;
    private String carBrand;
    private LocalDateTime orderDate;
    private OrderStatus status;
    private BigDecimal totalAmount;
    private List<String> services;
}
