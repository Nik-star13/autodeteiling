package com.autodet.autodet.controllers;

import com.autodet.autodet.dto.CarServiceOrderCreateDto;
import com.autodet.autodet.dto.CarServiceOrderDto;
import com.autodet.autodet.model.OrderStatus;
import com.autodet.autodet.services.CarServiceOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class CarServiceOrderController {
    private final CarServiceOrderService service;

    @GetMapping
    public ResponseEntity<List<CarServiceOrderDto>> getOrders(@RequestParam(required = false) OrderStatus status) {
        if (status != null) {
            return ResponseEntity.ok(service.getOrdersByStatus(status));
        }
        return ResponseEntity.ok(service.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarServiceOrderDto> getOrderById(@PathVariable Long id) {
        try {
            CarServiceOrderDto order = service.getAdminOrderById(id);
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<CarServiceOrderDto> createOrder(@RequestBody CarServiceOrderCreateDto createDto) {
        return ResponseEntity.ok(service.createOrder(createDto));
    }
}
