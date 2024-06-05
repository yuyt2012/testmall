package io.ecp.testmall.cart.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.ecp.testmall.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    private Member member;

    public static Cart createCart(Member member) {
        return Cart.builder()
                .member(member)
                .build();
    }
}