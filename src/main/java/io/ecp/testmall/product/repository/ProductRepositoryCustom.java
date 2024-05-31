package io.ecp.testmall.product.repository;

import io.ecp.testmall.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepositoryCustom {

    Page<Product> searchAll(Pageable pageable);
}
