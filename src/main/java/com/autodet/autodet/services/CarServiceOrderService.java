package com.autodet.autodet.services;

import com.autodet.autodet.dto.CarServiceOrderCreateDto;
import com.autodet.autodet.dto.CarServiceOrderDto;
import com.autodet.autodet.mapper.CarServiceOrderMapper;
import com.autodet.autodet.model.CarServiceOrder;
import com.autodet.autodet.model.OrderStatus;
import com.autodet.autodet.repository.CarServiceOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CarServiceOrderService {

    private final CarServiceOrderRepository repository;
    private final CarServiceOrderMapper mapper;

    public List<CarServiceOrderDto> getOrdersByStatus(String statusStr) {
        OrderStatus status = OrderStatus.valueOf(statusStr.toUpperCase());
        return repository.findByStatus(status)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    public CarServiceOrderDto getOrderById(Long id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new RuntimeException("Order not found: " + id));
    }

    public CarServiceOrderDto createOrder(CarServiceOrderCreateDto createDto) {
        CarServiceOrder order = mapper.toEntity(createDto);
        order.setTotalAmount(calculateTotal(createDto.getServices()));
        CarServiceOrder saved = repository.save(order);
        return mapper.toDto(saved);
    }

    private BigDecimal calculateTotal(List<String> services) {
        return BigDecimal.valueOf(services != null ? services.size() * 150 : 0);
    }
}
