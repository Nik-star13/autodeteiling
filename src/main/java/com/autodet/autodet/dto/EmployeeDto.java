package com.autodet.autodet.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class EmployeeDto {
    private Long id;
    private String name;
    private String specialization;
    private BigDecimal hourlyRate;
}
