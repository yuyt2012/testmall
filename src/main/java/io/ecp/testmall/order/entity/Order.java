package io.ecp.testmall.order.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.ecp.testmall.delivery.entity.Delivery;
import io.ecp.testmall.delivery.entity.DeliveryStatus;
import io.ecp.testmall.member.entity.Member;
import io.ecp.testmall.product.entity.Product;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;
    private String paymentMethod;
    private String shippingMethod;
    private int totalPrice;
    @Temporal(TemporalType.DATE)
    private Date orderDate;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    @Temporal(TemporalType.TIMESTAMP)
    private Date regDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderProduct> orderProducts;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Delivery delivery;

    @PrePersist
    protected void onRegDate() {
        regDate = new Date();
        updateDate = new Date();
        orderDate = new Date();
    }

    @PreUpdate
    protected void onUpdateDate() {
        updateDate = new Date();
    }

    public void cancelOrder() {
        if (orderStatus != OrderStatus.ORDER) {
            throw new IllegalStateException("주문이 이미 취소되었거나, 배송이 완료되었습니다.");
        }

        this.orderStatus = OrderStatus.CANCEL;
        delivery.setDeliveryStatus(DeliveryStatus.CANCEL);


        for (OrderProduct orderProduct : orderProducts) {
            Product product = orderProduct.getProduct();
            int quantity = orderProduct.getQuantity();
            product.addStockQuantity(quantity);
        }
    }

    public void updateOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
