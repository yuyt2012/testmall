package io.ecp.testmall.cart.entity;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CartProductDTO {
    @NotEmpty
    private Long productId;
    @NotNull
    private int quantity;
    @NotEmpty
    private String email;
}
