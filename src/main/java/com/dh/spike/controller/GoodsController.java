package com.dh.spike.controller;


import com.dh.spike.domain.SpikeUser;
import com.dh.spike.redis.GoodsKey;
import com.dh.spike.redis.RedisService;
import com.dh.spike.service.GoodsService;
import com.dh.spike.service.SpikeUserService;
import com.dh.spike.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * Create by DiaoHao on 2020/10/18 15:18
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    SpikeUserService spikeUserService;

    @Autowired
    RedisService redisService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    ThymeleafViewResolver resolver;

    /**
     * 10000*5请求
     * 不加入缓存
     * QPS = 2700
     * 加入缓存
     * QPS = 4962
     *
     * @param response
     * @param model
     * @param cookieToken
     * @param paramToken
     * @return
     */
    @RequestMapping(value = "/to_list", produces = "text/html")
    @ResponseBody
    public String list(HttpServletRequest request,
                       HttpServletResponse response, Model model,
                       @CookieValue(value = SpikeUserService.COOKIE_NAME_TOKEN,required = false) String cookieToken,
                       @RequestParam(value = SpikeUserService.COOKIE_NAME_TOKEN,required = false)String paramToken) {
        if (StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)) {
            return "login";
        }
        String token = StringUtils.isEmpty(cookieToken) ? paramToken : cookieToken;
        SpikeUser user = spikeUserService.getByToken(response, token);
        model.addAttribute("user", user);
        //从缓存中获取html页面
        String html = redisService.get(GoodsKey.getGoodsList, "", String.class);
        if (!StringUtils.isEmpty(html)) {
            return html;
        }
        //查询商品列表
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        model.addAttribute("goodsList", goodsList);
//        return "goods_list";

        //手动渲染网页
        WebContext context = new WebContext(request, response, request.getServletContext(),
                request.getLocale(), model.asMap());
        html = resolver.getTemplateEngine().process("goods_list", context);
        if (!StringUtils.isEmpty(html)) {
            redisService.set(GoodsKey.getGoodsList, "", html);
        }
        return html;
    }

    @RequestMapping(value = "/to_detail/{goodsId}",produces = "text/html")
    @ResponseBody
    public String detail(HttpServletRequest request,
                         HttpServletResponse response, Model model,
                         @CookieValue(value = SpikeUserService.COOKIE_NAME_TOKEN,required = false) String cookieToken,
                         @RequestParam(value = SpikeUserService.COOKIE_NAME_TOKEN,required = false)String paramToken,
                         @PathVariable("goodsId")long goodsId) {
        if (StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)) {
            return "login";
        }
        String token = StringUtils.isEmpty(cookieToken) ? paramToken : cookieToken;
        SpikeUser user = spikeUserService.getByToken(response, token);
        model.addAttribute("user", user);
        //从缓存中获取html
        String html = redisService.get(GoodsKey.getGoodsDetail, "" + goodsId, String.class);
        if (!StringUtils.isEmpty(html)) {
            return html;
        }

        GoodsVo goods = goodsService.getGoodsByGoodsId(goodsId);
        model.addAttribute("goods", goods);
        long startAt = goods.getStartDate().getTime();
        long endAt= goods.getEndDate().getTime();
        long now = System.currentTimeMillis();
        int spikeStatus = 0;
        int remainSeconds = 0;
        if (now < startAt) {
            //秒杀还未开始，进行倒计时
            spikeStatus = 0;
            remainSeconds = (int)(startAt - now) / 1000;
        } else if (now > endAt) {
            //秒杀已经结束
            spikeStatus = 2;
            remainSeconds = -1;
        } else {
            //秒杀进行中
            spikeStatus = 1;
            remainSeconds = 0;
        }
        model.addAttribute("spikeStatus", spikeStatus);
        model.addAttribute("remainSeconds", remainSeconds);
        //return "goods_detail";
        WebContext context = new WebContext(request, response, request.getServletContext(),
                request.getLocale(), model.asMap());
        html = resolver.getTemplateEngine().process("goods_detail", context);
        if (!StringUtils.isEmpty(html)) {
            redisService.set(GoodsKey.getGoodsDetail, "" + goodsId, html);
        }
        return html;
    }


}
