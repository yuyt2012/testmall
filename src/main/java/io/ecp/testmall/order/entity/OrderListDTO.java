package io.ecp.testmall.order.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderListDTO {

    private Long orderId;
    private String productName;
    private int quantity;
    private String orderStatus;
    private String deliveryStatus;
}
