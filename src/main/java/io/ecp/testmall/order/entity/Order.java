package io.ecp.testmall.order.entity;

import io.ecp.testmall.delivery.entity.Delivery;
import io.ecp.testmall.member.entity.Member;
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
    @OneToOne(fetch = FetchType.LAZY)
    private Delivery delivery;

    @PrePersist
    protected void onRegDate() {
        regDate = new Date();
        updateDate = new Date();
    }

    @PreUpdate
    protected void onUpdateDate() {
        updateDate = new Date();
    }

    public void addOrderProduct(OrderProduct orderProduct) {
        this.orderProducts.add(orderProduct);
        orderProduct.setOrder(this);
    }

    public void removeOrderProduct(OrderProduct orderProduct) {
        this.orderProducts.remove(orderProduct);
        orderProduct.setOrder(null);
    }
}
