package com.autodet.autodet.services;

import com.autodet.autodet.dto.CarServiceOrderCreateDto;
import com.autodet.autodet.dto.CarServiceOrderDto;
import com.autodet.autodet.mapper.CarServiceOrderMapper;
import com.autodet.autodet.model.*;
import com.autodet.autodet.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CarServiceOrderService {

    private final CarServiceOrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final CarServiceRepository carServiceRepository;
    private final CarServiceOrderMapper mapper;

    @Transactional
    public CarServiceOrderDto createOrder(CarServiceOrderCreateDto createDto) {

        Customer customer = customerRepository.findByPhone(createDto.getPhone())
                .orElseGet(() -> {
                    Customer c = new Customer();
                    c.setName(createDto.getCustomerName());
                    c.setPhone(createDto.getPhone());
                    c.setType(CustomerType.REGULAR);
                    return customerRepository.save(c);
                });

        Set<CarService> carServices = new HashSet<>();
        List<String> serviceKeys = createDto.getServices();

        if (serviceKeys != null && !serviceKeys.isEmpty()) {
            for (String serviceKey : serviceKeys) {
                if (serviceKey != null && !serviceKey.trim().isEmpty()) {

                    CarService service = carServiceRepository.findByName(serviceKey.trim())
                            .orElseGet(() -> {
                                CarService newService = new CarService();
                                newService.setName(serviceKey.trim());

                                BigDecimal price = switch (serviceKey.trim()) {
                                    case "wash" -> new BigDecimal("150.00");
                                    case "wheels" -> new BigDecimal("200.00");
                                    case "interior" -> new BigDecimal("250.00");
                                    case "polish" -> new BigDecimal("300.00");
                                    default -> new BigDecimal("100.00");
                                };
                                newService.setPrice(price);
                                newService.setDescription("Автосервис: " + serviceKey.trim());

                                return carServiceRepository.save(newService);
                            });

                    carServices.add(service);
                }
            }
        }

        CarServiceOrder order = new CarServiceOrder();
        order.setCustomerName(createDto.getCustomerName());
        order.setPhone(createDto.getPhone());
        order.setCarBrand(createDto.getCarBrand());

        if (createDto.getDate() != null && createDto.getTime() != null) {
            order.setDate(LocalDateTime.parse(createDto.getDate() + "T" + createDto.getTime()));
            order.setTime(createDto.getTime());
        }

        order.setStatus(OrderStatus.NEW);
        order.setOrderDate(LocalDateTime.now());
        order.setCustomer(customer);

        CarServiceOrder savedOrder = orderRepository.save(order);

        for (CarService service : carServices) {
            savedOrder.getCarServices().add(service);
        }

        CarServiceOrder orderWithServices = orderRepository.save(savedOrder);
        orderRepository.flush();

        BigDecimal totalAmount = carServices.stream()
                .map(CarService::getPrice)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        orderWithServices.setTotalAmount(totalAmount);
        orderRepository.save(orderWithServices);

        return mapper.toDto(orderWithServices);
    }

    public List<CarServiceOrderDto> getAllOrders() {
        List<CarServiceOrder> orders = orderRepository.findAll();
        return orders.stream().map(mapper::toDto).toList();
    }

    public Optional<CarServiceOrderDto> getOrderById(Long id) {
        return orderRepository.findById(id).map(mapper::toDto);
    }

    public List<CarServiceOrderDto> getOrdersByStatus(OrderStatus status) {
        List<CarServiceOrder> orders = orderRepository.findByStatus(status);
        return orders.stream().map(mapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public CarServiceOrderDto getAdminOrderById(Long id) {
        Optional<CarServiceOrder> orderOpt = orderRepository.findByIdWithServices(id);
        if (orderOpt.isEmpty()) {
            throw new EntityNotFoundException("Заказ не найден: " + id);
        }
        return mapper.toDto(orderOpt.get());
    }
}
