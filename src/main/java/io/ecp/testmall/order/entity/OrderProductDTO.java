package io.ecp.testmall.order.entity;

import io.ecp.testmall.product.entity.Product;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderProductDTO {

    private Long productId;
    private String productName;
    private int price;
    private int quantity;

    public OrderProduct toOrderProduct() {
        return OrderProduct.builder()
                .price(this.price)
                .quantity(this.quantity)
                .product(new Product(this.productId))

    }
}
