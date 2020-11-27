package com.dh.spike;

import com.dh.spike.domain.OrderInfo;
import com.dh.spike.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpikeApplicationTests {

    @Autowired
    OrderService orderService;

    @Test
    void contextLoads() {
        OrderInfo orderInfo = orderService.getOrderById(2);
        System.out.println(orderInfo.getGoodsId());
    }

}
