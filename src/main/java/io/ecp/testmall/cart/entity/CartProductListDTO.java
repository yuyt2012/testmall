package io.ecp.testmall.cart.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class CartProductListDTO {
    private String productName;
    private int quantity;
    private int price;
    private String imageUrl;

    public CartProductListDTO(CartProduct cartProduct) {
        this.productName = cartProduct.getProduct().getName();
        this.quantity = cartProduct.getQuantity();
        this.price = cartProduct.getProduct().getPrice();
        this.imageUrl = cartProduct.getProduct().getImageUrl();
    }
}
