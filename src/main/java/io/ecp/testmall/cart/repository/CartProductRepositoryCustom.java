package io.ecp.testmall.cart.repository;

import io.ecp.testmall.cart.entity.CartProductListDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CartProductRepositoryCustom {
    Page<CartProductListDTO> searchCartProduct(Pageable pageable, Long cartId);
}
