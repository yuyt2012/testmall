package io.ecp.testmall.order.service;

import io.ecp.testmall.delivery.entity.Delivery;
import io.ecp.testmall.member.entity.Member;
import io.ecp.testmall.member.repository.MemberRepository;
import io.ecp.testmall.order.entity.Order;
import io.ecp.testmall.order.entity.OrderDTO;
import io.ecp.testmall.order.entity.OrderProductDTO;
import io.ecp.testmall.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static io.ecp.testmall.order.entity.OrderStatus.ORDER;

@Service
@Transactional(readOnly = true)
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Transactional(readOnly = false)
    public Order saveOrder(OrderDTO orderDTO) {
        Member purchaseMember = memberRepository.findById(orderDTO.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));
        Delivery delivery = orderDTO.getDelivery().toDelivery();
        Order.builder()
                .member(purchaseMember)
                .paymentMethod(orderDTO.getPaymentMethod())
                .shippingMethod(orderDTO.getShippingMethod())
                .orderStatus(ORDER)
                .totalPrice(orderDTO.getTotalPrice())
                .orderProducts()
                .delivery(delivery)
                .build();



    }
}
