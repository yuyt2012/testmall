package io.ecp.testmall.order.controller;

import io.ecp.testmall.order.entity.OrderDTO;
import io.ecp.testmall.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static io.ecp.testmall.utils.tokenValidUtils.tokenValid;

@RestController
@CrossOrigin(origins = "http://localhost:5174")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/order")
    public ResponseEntity<?> saveOrder(@RequestBody OrderDTO orderDTO,
                                       @RequestHeader("Authorization") String token) {
        if (tokenValid(token)) return ResponseEntity.badRequest().build();
        orderService.saveOrder(orderDTO);
        return ResponseEntity.ok("주문이 완료되었습니다.");
    }

    @GetMapping("/getorders/{email}")
    public ResponseEntity<?> getOrders(@PageableDefault(size = 10) Pageable pageable,
                                       @PathVariable String email,
                                       @RequestHeader("Authorization") String token) {
        if (tokenValid(token)) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(orderService.getOrders(pageable, email));
    }

    @PutMapping("/cancelorder/{orderId}")
    public ResponseEntity<?> cancelOrder(@PathVariable Long orderId,
                                         @RequestHeader("Authorization") String token) {
        if (tokenValid(token)) return ResponseEntity.badRequest().build();
        orderService.cancelOrder(orderId);
        return ResponseEntity.ok("주문이 취소되었습니다.");
    }

    @GetMapping("/getorderdetail/{orderId}")
    public ResponseEntity<?> getOrderDetail(@PathVariable Long orderId,
                                            @RequestHeader("Authorization") String token) {
        if (tokenValid(token)) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(orderService.getOrderDetail(orderId));
    }

}
