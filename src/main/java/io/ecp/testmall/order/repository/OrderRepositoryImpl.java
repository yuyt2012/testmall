package io.ecp.testmall.order.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.ecp.testmall.delivery.entity.QDelivery;
import io.ecp.testmall.order.entity.OrderDetailDTO;
import io.ecp.testmall.order.entity.OrderListDTO;
import io.ecp.testmall.order.entity.QOrder;
import io.ecp.testmall.order.entity.QOrderProduct;
import io.ecp.testmall.product.entity.QProduct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.querydsl.core.types.Projections.*;
import static com.querydsl.core.types.Projections.constructor;
import static io.ecp.testmall.delivery.entity.QDelivery.*;
import static io.ecp.testmall.order.entity.QOrder.*;
import static io.ecp.testmall.order.entity.QOrderProduct.*;
import static io.ecp.testmall.product.entity.QProduct.*;

public class OrderRepositoryImpl implements OrderRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<OrderListDTO> searchOrderList(Pageable pageable, String email) {
        JPAQueryFactory query = new JPAQueryFactory(em);

        List<OrderListDTO> results = query.select(constructor(OrderListDTO.class,
                        order.id,
                        product.name,
                        orderProduct.quantity,
                        order.orderStatus.stringValue(), // Enum의 값을 문자열로 변환
                        order.delivery.deliveryStatus.stringValue())) // Enum의 값을 문자열로 변환
                .from(order)
                .join(order.orderProducts, orderProduct)
                .join(orderProduct.product, product)
                .where(order.member.email.eq(email))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> count = query.select(orderProduct.count())
                .from(orderProduct)
                .where(orderProduct.order.member.email.eq(email));

        return PageableExecutionUtils.getPage(results, pageable, count::fetchOne);
    }

    @Override
    public List<OrderDetailDTO> searchOrderDetail(Long orderId) {
        JPAQueryFactory query = new JPAQueryFactory(em);
        return query.select(constructor(OrderDetailDTO.class,
                        order.id,
                        order.member.email,
                        order.paymentMethod,
                        order.shippingMethod,
                        product.name,
                        product.price,
                        orderProduct.quantity,
                        order.orderStatus.stringValue(),
                        delivery.deliveryStatus.stringValue(),
                        delivery.receiverName,
                        delivery.receiverPhone,
                        delivery.deliveryAddress.receiverCity,
                        delivery.deliveryAddress.receiverStreet,
                        delivery.deliveryAddress.receiverZipcode,
                        order.orderDate,
                        order.totalPrice))
                .from(order)
                .join(order.orderProducts, orderProduct)
                .join(orderProduct.product, product)
                .join(order.delivery, delivery)
                .where(order.id.eq(orderId))
                .fetchResults()
                .getResults();
    }
}