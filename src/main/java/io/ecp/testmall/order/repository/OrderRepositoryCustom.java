package io.ecp.testmall.order.repository;

import io.ecp.testmall.order.entity.OrderDetailDTO;
import io.ecp.testmall.order.entity.OrderListDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderRepositoryCustom {

    Page<OrderListDTO> searchOrderList(Pageable pageable, String email);

    List<OrderDetailDTO> searchOrderDetail(Long orderId);
}
