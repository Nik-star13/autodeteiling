package com.autodet.autodet.dto;

import com.autodet.autodet.model.CustomerType;
import lombok.Data;

@Data
public class CustomerDto {
    private Long id;
    private String name;
    private String phone;
    private CustomerType type;
}
