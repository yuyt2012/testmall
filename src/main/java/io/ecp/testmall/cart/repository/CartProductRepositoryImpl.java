package io.ecp.testmall.cart.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.ecp.testmall.cart.entity.CartProduct;
import io.ecp.testmall.cart.entity.QCartProduct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static io.ecp.testmall.cart.entity.QCartProduct.*;

public class CartProductRepositoryImpl implements CartProductRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<CartProduct> searchCartProduct(Pageable pageable, Long cartId) {
        JPAQueryFactory query = new JPAQueryFactory(em);

        List<CartProduct> cartProducts = query.select(cartProduct)
                .from(cartProduct)
                .where(cartProduct.cart.id.eq(cartId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> count = query.select(cartProduct.count())
                .from(cartProduct);

        return PageableExecutionUtils.getPage(cartProducts, pageable, count::fetchOne);
    }
}
