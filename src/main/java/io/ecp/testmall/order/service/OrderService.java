package io.ecp.testmall.order.service;

import io.ecp.testmall.delivery.entity.Delivery;
import io.ecp.testmall.member.entity.Member;
import io.ecp.testmall.member.repository.MemberRepository;
import io.ecp.testmall.order.entity.Order;
import io.ecp.testmall.order.entity.OrderDTO;
import io.ecp.testmall.order.entity.OrderProduct;
import io.ecp.testmall.order.entity.OrderProductDTO;
import io.ecp.testmall.order.repository.OrderRepository;
import io.ecp.testmall.product.entity.Product;
import io.ecp.testmall.product.repository.ProductRepository;
import java.util.List;
import java.util.stream.Collectors;
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
    @Autowired
    private ProductRepository productRepository;

    @Transactional(readOnly = false)
    public Order saveOrder(OrderDTO orderDTO) {
        Member purchaseMember = memberRepository.findByEmail(orderDTO.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));
        Delivery delivery = orderDTO.getDelivery().toDelivery();
        List<OrderProduct> orderProducts = orderDTO.getOrderProducts().stream()
                .map(orderProductDTO -> {
                    Product product = productRepository.findById(orderProductDTO.getProductId())
                            .orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다."));
                    OrderProduct orderProduct = orderProductDTO.toOrderProduct();
                    orderProduct.setProduct(product);
                    return orderProduct;
                })
                .collect(Collectors.toList());
        Order order = Order.builder()
                .member(purchaseMember)
                .paymentMethod(orderDTO.getPaymentMethod())
                .shippingMethod(orderDTO.getShippingMethod())
                .orderStatus(ORDER)
                .totalPrice(orderDTO.getTotalPrice())
                .orderProducts(orderProducts)
                .delivery(delivery)
                .build();
        return orderRepository.save(order);
    }
}
