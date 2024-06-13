package io.ecp.testmall.delivery.entity;

import io.ecp.testmall.member.entity.Address;
import io.ecp.testmall.order.entity.OrderDTO;
import lombok.Data;

import static io.ecp.testmall.delivery.entity.DeliveryStatus.READY;

@Data
public class DeliveryDTO {

    private String receiverName;
    private String receiverPhone;
    private String receiverCity;
    private String receiverStreet;
    private String receiverZipcode;
    private String deliveryStatus;

    public Delivery toDelivery() {
        DeliveryAddress deliveryAddress = new DeliveryAddress(this.getReceiverCity()
                , this.getReceiverStreet()
                , this.getReceiverZipcode());

        Delivery delivery = new Delivery();
        delivery.setReceiverName(this.getReceiverName());
        delivery.setReceiverPhone(this.getReceiverPhone());
        delivery.setDeliveryAddress(deliveryAddress);
        delivery.setDeliveryStatus(DeliveryStatus.valueOf(this.getDeliveryStatus()));
        return delivery;
    }
}
