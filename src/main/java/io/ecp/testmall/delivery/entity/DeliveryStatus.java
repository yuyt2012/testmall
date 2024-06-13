package io.ecp.testmall.delivery.entity;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DeliveryStatus {
    READY("배송 준비중"),
    SHIPPING("배송 중"),
    CANCEL("배송 취소"),
    COMP("배송 완료");

    private final String name;


    @Override
    public String toString() {
        return name;
    }
}
