package io.ecp.testmall.order.controller;

import io.ecp.testmall.order.entity.OrderDTO;
import io.ecp.testmall.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static io.ecp.testmall.jwt.utils.JwtUtils.validateToken;
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
    }
}
