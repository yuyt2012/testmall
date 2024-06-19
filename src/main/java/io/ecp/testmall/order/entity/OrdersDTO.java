package io.ecp.testmall.order.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdersDTO {

    private Long orderId;
    private String email;
    private String orderStatus;
    private String deliveryStatus;
    private int totalPrice;
    private Date orderDate;
}
