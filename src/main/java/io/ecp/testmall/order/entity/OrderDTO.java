package io.ecp.testmall.order.entity;

import io.ecp.testmall.delivery.entity.Delivery;
import io.ecp.testmall.delivery.entity.DeliveryDTO;
import io.ecp.testmall.delivery.entity.DeliveryStatus;
import io.ecp.testmall.member.entity.Address;
import lombok.Data;

import java.util.List;

import static io.ecp.testmall.delivery.entity.DeliveryStatus.*;

@Data
public class OrderDTO {
    private String email;
    private String paymentMethod;
    private String shippingMethod;
    private List<OrderProductDTO> orderProducts;
    private DeliveryDTO delivery;

    public int getTotalPrice() {
        return orderProducts.stream()
                .mapToInt(OrderProductDTO::getPrice)
                .sum();
    }
}
