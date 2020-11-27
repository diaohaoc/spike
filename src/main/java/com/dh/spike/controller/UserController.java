package com.dh.spike.controller;


import com.dh.spike.domain.SpikeUser;
import com.dh.spike.redis.RedisService;
import com.dh.spike.result.Result;
import com.dh.spike.service.GoodsService;
import com.dh.spike.service.SpikeUserService;
import com.dh.spike.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * Create by DiaoHao on 2020/10/18 15:18
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    SpikeUserService spikeUserService;

    @Autowired
    RedisService redisService;


    @RequestMapping("/info")
    @ResponseBody
    public Result<SpikeUser> info(HttpServletResponse response, Model model,
                       @CookieValue(value = SpikeUserService.COOKIE_NAME_TOKEN,required = false) String cookieToken,
                       @RequestParam(value = SpikeUserService.COOKIE_NAME_TOKEN,required = false)String paramToken) {
//        if (StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)) {
//            return "login";
//        }
        String token = StringUtils.isEmpty(cookieToken) ? paramToken : cookieToken;
        SpikeUser user = spikeUserService.getByToken(response, token);
        return Result.success(user);
    }


}
