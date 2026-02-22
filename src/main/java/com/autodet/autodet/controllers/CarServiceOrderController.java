package com.autodet.autodet.controllers;

import com.autodet.autodet.dto.CarServiceOrderCreateDto;
import com.autodet.autodet.dto.CarServiceOrderDto;
import com.autodet.autodet.services.CarServiceOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class CarServiceOrderController {

    private final CarServiceOrderService service;

    @GetMapping
    public ResponseEntity<List<CarServiceOrderDto>> getOrders(
            @RequestParam(defaultValue = "NEW") String status) {
        List<CarServiceOrderDto> orders = service.getOrdersByStatus(status);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarServiceOrderDto> getOrder(@PathVariable Long id) {
        CarServiceOrderDto order = service.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    @PostMapping
    public ResponseEntity<CarServiceOrderDto> createOrder(
            @RequestBody CarServiceOrderCreateDto createDto) {
        CarServiceOrderDto order = service.createOrder(createDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }
}
