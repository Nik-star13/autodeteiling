package com.autodet.autodet.mapper;

import com.autodet.autodet.dto.CarServiceOrderDto;
import com.autodet.autodet.dto.CustomerDto;
import com.autodet.autodet.model.CarService;
import com.autodet.autodet.model.CarServiceOrder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CarServiceOrderMapper {

    public CarServiceOrderDto toDto(CarServiceOrder entity) {
        if (entity == null) return null;

        CarServiceOrderDto dto = new CarServiceOrderDto();
        dto.setId(entity.getId());
        dto.setCustomerName(entity.getCustomerName());
        dto.setPhone(entity.getPhone());
        dto.setCarBrand(entity.getCarBrand());
        dto.setOrderDate(entity.getOrderDate());
        dto.setDate(entity.getDate());
        dto.setTime(entity.getTime());
        dto.setStatus(entity.getStatus());
        dto.setTotalAmount(entity.getTotalAmount());

        if (entity.getCustomer() != null) {
            CustomerDto customerDto = new CustomerDto();
            customerDto.setId(entity.getCustomer().getId());
            customerDto.setName(entity.getCustomer().getName());
            customerDto.setPhone(entity.getCustomer().getPhone());
            customerDto.setType(entity.getCustomer().getType());
            dto.setCustomer(customerDto);
        }

        List<String> servicesList = new ArrayList<>();

        if (entity.getCarServices() != null) {

            for (CarService service : entity.getCarServices()) {
                if (service != null && service.getName() != null) {
                    servicesList.add(service.getName());
                }
            }
        }

        dto.setServices(servicesList);

        return dto;
    }


    public CarServiceOrder toEntity(com.autodet.autodet.dto.CarServiceOrderCreateDto dto) {
        throw new UnsupportedOperationException("Используйте сервис для создания");
    }
}
