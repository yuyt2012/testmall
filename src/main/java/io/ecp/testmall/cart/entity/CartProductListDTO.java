package io.ecp.testmall.cart.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class CartProductListDTO {
    private Long id;
    private String name;
    private int quantity;
    private int price;
    private String imageUrl;

    public CartProductListDTO(CartProduct cartProduct) {
        this.id = cartProduct.getProduct().getId();
        this.name = cartProduct.getProduct().getName();
        this.quantity = cartProduct.getQuantity();
        this.price = cartProduct.getProduct().getPrice();
        this.imageUrl = cartProduct.getProduct().getImageUrl();
    }
}
