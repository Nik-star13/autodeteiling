package com.autodet.autodet.repository;

import com.autodet.autodet.model.CarServiceOrder;
import com.autodet.autodet.model.OrderStatus;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CarServiceOrderRepository {
    private final List<CarServiceOrder> orders = new ArrayList<>();
    private Long sequence = 1L;

    public CarServiceOrderRepository() {
        initData();
    }

    private void initData() {
        CarServiceOrder order1 = new CarServiceOrder();
        order1.setId(1L);
        order1.setCustomerName("Иван Иванов");
        order1.setPhone("+375291234567");
        order1.setCarBrand("Toyota Camry");
        order1.setStatus(OrderStatus.NEW);
        order1.setServices(List.of("Комплексная мойка", "Химчистка"));
        orders.add(order1);

        CarServiceOrder order2 = new CarServiceOrder();
        order2.setId(2L);
        order2.setCustomerName("Мария Петрова");
        order2.setPhone("+375292345678");
        order2.setCarBrand("BMW X5");
        order2.setStatus(OrderStatus.CONFIRMED);
        order2.setServices(List.of("Полировка", "Керамика"));
        orders.add(order2);

        CarServiceOrder order3 = new CarServiceOrder();
        order3.setId(3L);
        order3.setCustomerName("Иван Иванов");
        order3.setPhone("+375291234567");
        order3.setCarBrand("Toyota Camry");
        order3.setStatus(OrderStatus.NEW);
        order3.setServices(List.of("Комплексная мойка", "Химчистка"));
        orders.add(order3);
    }

    public List<CarServiceOrder> findByStatus(OrderStatus status) {
        return orders.stream()
                .filter(order -> order.getStatus() == status)
                .toList();
    }

    public Optional<CarServiceOrder> findById(Long id) {
        return orders.stream()
                .filter(order -> order.getId().equals(id))
                .findFirst();
    }

    public CarServiceOrder save(CarServiceOrder order) {
        if (order.getId() == null) {
            order.setId(sequence++);
        }
        orders.removeIf(o -> o.getId().equals(order.getId()));
        orders.add(order);
        return order;
    }
}
