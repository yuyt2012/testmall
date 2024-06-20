package io.ecp.testmall.order.entity;

import lombok.Data;

@Data
public class UpdateDeliveryStatusDTO {
    private Long orderId;
    private String deliveryStatus;
}
