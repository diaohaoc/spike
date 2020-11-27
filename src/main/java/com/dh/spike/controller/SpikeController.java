package com.dh.spike.controller;


import com.dh.spike.domain.OrderInfo;
import com.dh.spike.domain.SpikeOrder;
import com.dh.spike.domain.SpikeUser;
import com.dh.spike.redis.RedisService;
import com.dh.spike.result.CodeMsg;
import com.dh.spike.service.GoodsService;
import com.dh.spike.service.OrderService;
import com.dh.spike.service.SpikeService;
import com.dh.spike.service.SpikeUserService;
import com.dh.spike.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;


/**
 * Create by DiaoHao on 2020/10/18 15:18
 */
@Controller
@RequestMapping("/spike")
public class SpikeController {

    @Autowired
    SpikeUserService spikeUserService;

    @Autowired
    RedisService redisService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    SpikeService spikeService;

    @RequestMapping("/do_spike")
    public String list(HttpServletResponse response, Model model,
            @CookieValue(value = SpikeUserService.COOKIE_NAME_TOKEN,required = false) String cookieToken,
            @RequestParam(value = SpikeUserService.COOKIE_NAME_TOKEN,required = false)String paramToken,
                       @RequestParam("goodsId")long goodsId) {
        if (StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)) {
            return "login";
        }
        String token = StringUtils.isEmpty(cookieToken) ? paramToken : cookieToken;
        SpikeUser user = spikeUserService.getByToken(response, token);
        model.addAttribute("user", user);
        if (user == null) {
            return "login";
        }
        GoodsVo goods = goodsService.getGoodsByGoodsId(goodsId);
        int stockDown = goods.getStockCount();
        //判断库存
        if (stockDown <= 0) {
            model.addAttribute("errmsg", CodeMsg.SPIKE_OVER.getMsg());
            return "spike_fail";
        }
        //判断是否已经得到该商品
        SpikeOrder order = orderService.getSpikeOrderByUserIdGoodsId(user.getId(), goodsId);
        if (order != null) {
            model.addAttribute("errmsg",CodeMsg.REPEAT_SPIKE);
            return "spike_fail";
        }
        //减库存、下订单、写入秒杀订单
        OrderInfo orderInfo = spikeService.spike(user, goods);
        model.addAttribute("orderInfo", orderInfo);
        model.addAttribute("goods", goods);
        return "order_detail";
    }



}
