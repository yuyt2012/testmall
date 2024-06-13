package io.ecp.testmall.order.entity;

import io.ecp.testmall.delivery.entity.Delivery;
import io.ecp.testmall.delivery.entity.DeliveryDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetailDTO {

    private Long orderId;
    private String email;
    private String paymentMethod;
    private String shippingMethod;
    private String productName;
    private int price;
    private int quantity;
    private String orderStatus;
    private String deliveryStatus;
    private String receiverName;
    private String receiverPhone;
    private String receiverCity;
    private String receiverStreet;
    private String receiverZipcode;
    private Date orderDate;
    private int totalPrice;
}
