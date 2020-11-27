package com.dh.spike.controller;


import com.dh.spike.domain.OrderInfo;
import com.dh.spike.domain.SpikeUser;
import com.dh.spike.redis.RedisService;
import com.dh.spike.result.CodeMsg;
import com.dh.spike.result.Result;
import com.dh.spike.service.GoodsService;
import com.dh.spike.service.OrderService;
import com.dh.spike.service.SpikeUserService;
import com.dh.spike.vo.GoodsVo;
import com.dh.spike.vo.OrderDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;


/**
 * Create by DiaoHao on 2020/10/18 15:18
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    SpikeUserService spikeUserService;

    @Autowired
    RedisService redisService;

    @Autowired
    OrderService orderService;

    @Autowired
    GoodsService goodsService;

    @RequestMapping("/detail")
    @ResponseBody
    public Result<OrderDetailVo> info(HttpServletResponse response, Model model,
                                      @CookieValue(value = SpikeUserService.COOKIE_NAME_TOKEN,required = false) String cookieToken,
                                      @RequestParam(value = SpikeUserService.COOKIE_NAME_TOKEN,required = false)String paramToken,
                                      @RequestParam("orderId") long orderId) {
        String token = StringUtils.isEmpty(cookieToken) ? paramToken : cookieToken;
        SpikeUser user = spikeUserService.getByToken(response, token);
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        OrderInfo orderInfo = orderService.getOrderById(orderId);
        if (orderInfo == null) {
            return Result.error(CodeMsg.ORDER_NOT_EXIT);
        }
        GoodsVo goods = goodsService.getGoodsByGoodsId(orderInfo.getGoodsId());
        OrderDetailVo vo = new OrderDetailVo();
        vo.setOrder(orderInfo);
        vo.setGoods(goods);
        return Result.success(vo);
    }


}
