package com.autodet.autodet.mapper;

import com.autodet.autodet.dto.CarServiceOrderCreateDto;
import com.autodet.autodet.dto.CarServiceOrderDto;
import com.autodet.autodet.model.CarServiceOrder;
import org.springframework.stereotype.Component;

@Component
public class CarServiceOrderMapper {

    public CarServiceOrderDto toDto(CarServiceOrder entity) {
        CarServiceOrderDto dto = new CarServiceOrderDto();
        dto.setId(entity.getId());
        dto.setCustomerName(entity.getCustomerName());
        dto.setPhone(entity.getPhone());
        dto.setCarBrand(entity.getCarBrand());
        dto.setOrderDate(entity.getOrderDate());
        dto.setStatus(entity.getStatus() != null ? entity.getStatus().name() : null);
        dto.setTotalAmount(entity.getTotalAmount());
        dto.setServices(entity.getServices());
        return dto;
    }

    public CarServiceOrder toEntity(CarServiceOrderCreateDto dto) {
        CarServiceOrder entity = new CarServiceOrder();
        entity.setCustomerName(dto.getCustomerName());
        entity.setPhone(dto.getPhone());
        entity.setCarBrand(dto.getCarBrand());
        entity.setServices(dto.getServices());
        entity.setStatus(com.autodet.autodet.model.OrderStatus.NEW);
        return entity;
    }
}
