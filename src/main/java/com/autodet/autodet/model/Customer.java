package com.autodet.autodet.model;

import lombok.Data;

@Data
public class Customer {
    private Long id;
    private String name;
    private String phone;
    private CustomerType type;
}

