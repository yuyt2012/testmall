package io.ecp.testmall.product.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.ecp.testmall.product.entity.Product;
import io.ecp.testmall.product.entity.QProduct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static io.ecp.testmall.product.entity.QProduct.*;


public class ProductRepositoryImpl implements ProductRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<Product> searchAll(Pageable pageable) {
        JPAQueryFactory query = new JPAQueryFactory(em);
        List<Product> products = query.select(product)
                .from(product)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> count = query.select(product.count())
                .from(product);

        return PageableExecutionUtils.getPage(products, pageable, count::fetchOne);
    }
}
