package io.ecp.testmall.order.entity;

import lombok.Data;

@Data
public class OrderDTO {
    private String productName;
    private int quantity;
    private String senderName;
    private String receiverName;
    private String receiverPhone;
    private String receiverCity;
    private String receiverStreet;
    private String receiverZipcode;
    private String orderStatus;
    private int totalPrice;
}
