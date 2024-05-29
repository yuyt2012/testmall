package io.ecp.testmall.delivery.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DeliveryStatus {
    READY("배송 준비중"),
    COMP("배송 완료");

    private final String status;
}
