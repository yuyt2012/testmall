package io.ecp.testmall.order.entity;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum OrderStatus {
    ORDER("주문"),
    CANCEL("취소"),
    COMP("구매 확정");

    private final String name;


    @Override
    public String toString() {
        return name;
    }
}
