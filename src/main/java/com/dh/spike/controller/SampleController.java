package com.dh.spike.controller;

import com.dh.spike.domain.User;
import com.dh.spike.redis.RedisService;
import com.dh.spike.redis.UserKey;
import com.dh.spike.result.Result;
import com.dh.spike.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Create by DiaoHao on 2020/10/18 15:18
 */
@Controller
@RequestMapping
public class SampleController {

    @Autowired
    UserService userService;

    @Autowired
    RedisService redisService;

    @RequestMapping("/db/get")
    @ResponseBody
    public Result<User> get() {
        User user = userService.getById(1);
        return Result.success(user);
    }

    @RequestMapping("/db/getac")
    @ResponseBody
    public Result<Boolean> getTa() {
        userService.trans();
        return Result.success(true);
    }

    @RequestMapping("/redis/get")
    @ResponseBody
    public Result<User> getRedis() {
        User user = redisService.get(UserKey.getById, "" + 1, User.class);
        return Result.success(user);
    }

    @RequestMapping("/redis/set")
    @ResponseBody
    public Result<Boolean> setRedis() {
        User user = new User();
        user.setId(1);
        user.setName("123214");
        redisService.set(UserKey.getById, "" + 1, user);
        return Result.success(true);
    }
}
