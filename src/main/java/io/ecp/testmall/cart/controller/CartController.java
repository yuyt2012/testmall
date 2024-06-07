package io.ecp.testmall.cart.controller;

import io.ecp.testmall.cart.entity.CartProduct;
import io.ecp.testmall.cart.entity.CartProductDTO;
import io.ecp.testmall.cart.entity.CartProductListDTO;
import io.ecp.testmall.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static io.ecp.testmall.utils.tokenValidUtils.tokenValid;

@RestController
@CrossOrigin(origins = "http://localhost:5174")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/cart/add")
    public ResponseEntity<?> addCart(@RequestBody CartProductDTO cartProductDTO,
                                     @RequestHeader("Authorization") String token) {
        if (tokenValid(token)) return ResponseEntity.badRequest().build();

        Long cartProductId = cartService.addCart(cartProductDTO);
        return ResponseEntity.ok(cartProductId);
    }

    @GetMapping("/cart/products/{email}")
    public Page<CartProductListDTO> cartProducts(@PageableDefault(size = 10) Pageable pageable,
                                                 @PathVariable String email,
                                                 @RequestHeader("Authorization") String token) {
        if (tokenValid(token)) return null;
        return cartService.getCartProducts(pageable, email);
    }

    @DeleteMapping("/cart/delete/{productName}/{userId}")
    public ResponseEntity<?> deleteCartProduct(@PathVariable String productName,
                                               @PathVariable Long userId,
                                               @RequestHeader("Authorization") String token) {
        if (tokenValid(token)) return ResponseEntity.badRequest().build();
        cartService.deleteCartProduct(productName, userId);
        return ResponseEntity.ok().build();
    }
}
