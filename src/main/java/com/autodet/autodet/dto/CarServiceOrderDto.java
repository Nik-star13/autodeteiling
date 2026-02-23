package com.autodet.autodet.dto;

import com.autodet.autodet.model.OrderStatus;
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
    private LocalDateTime date;
    private String time;
    private OrderStatus status;
    private BigDecimal totalAmount;

    private CustomerDto customer;
    private List<String> services;
    private EmployeeDto employee;
}
