package io.ecp.testmall.order.controller;

import io.ecp.testmall.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:5174")
public class OrderController {

    @Autowired
    private OrderService orderService;
}
