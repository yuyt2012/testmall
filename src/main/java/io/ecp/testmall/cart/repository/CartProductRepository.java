package io.ecp.testmall.cart.repository;

import io.ecp.testmall.cart.entity.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartProductRepository extends JpaRepository<CartProduct, Long>, CartProductRepositoryCustom {
    Optional<CartProduct> findByCartIdAndProductId(Long cartId, Long productId);
}
