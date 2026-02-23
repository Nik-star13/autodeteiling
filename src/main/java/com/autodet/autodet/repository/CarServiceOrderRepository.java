package com.autodet.autodet.repository;

import com.autodet.autodet.model.CarServiceOrder;
import com.autodet.autodet.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarServiceOrderRepository extends JpaRepository<CarServiceOrder, Long> {


    @Query("SELECT o FROM CarServiceOrder o LEFT JOIN FETCH o.carServices cs WHERE o.id = :id")
    Optional<CarServiceOrder> findByIdWithServices(@Param("id") Long id);

    List<CarServiceOrder> findByStatus(OrderStatus status);

    @Query("SELECT o FROM CarServiceOrder o LEFT JOIN o.customer c WHERE c.id = :customerId")
    List<CarServiceOrder> findByCustomerId(@Param("customerId") Long customerId);

}
