package io.ecp.testmall.cart.repository;

import io.ecp.testmall.cart.entity.CartProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CartProductRepositoryCustom {
    Page<CartProduct> searchCartProduct(Pageable pageable, Long cartId);
}
