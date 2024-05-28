package io.ecp.testmall.product.repository;

import io.ecp.testmall.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
