package com.dh.spike.service;

import com.dh.spike.dao.SpikeUserDao;
import com.dh.spike.domain.SpikeUser;
import com.dh.spike.exception.GlobalException;
import com.dh.spike.redis.RedisService;
import com.dh.spike.redis.SpikeUserKey;
import com.dh.spike.result.CodeMsg;
import com.dh.spike.util.MD5Util;
import com.dh.spike.util.UUIDUtil;
import com.dh.spike.vo.LoginVo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Create by DiaoHao on 2020/10/20 21:08
 */

@Service
public class SpikeUserService {

    public static final String COOKIE_NAME_TOKEN = "token";

    @Resource
    SpikeUserDao spikeUserDao;

    @Resource
    RedisService redisService;

    public SpikeUser getById(long id) {
        //从缓存中获取
        SpikeUser user = redisService.get(SpikeUserKey.getById, "" + id, SpikeUser.class);
        if (user != null) {
            return user;
        }
        //从数据库中获取
        user = spikeUserDao.getById(id);
        if (user != null) {
            redisService.set(SpikeUserKey.getById, "" + id, user);
        }
        return user;
    }

    public boolean login(HttpServletResponse response, LoginVo loginVo) {
        if (loginVo == null) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        String mobile = loginVo.getMobile();
        String formPass = loginVo.getPassword();
        //判断用户是否存在
        SpikeUser user = spikeUserDao.getById(Long.parseLong(mobile));
        if (user == null) {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIT);
        }
        //验证密码
        String dbPass = user.getPassword();
        String dbSalt = user.getSalt();
        String calcPass = MD5Util.formPassToDBPass(formPass, dbSalt);
        if (!calcPass.equals(dbPass)) {
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        //生成cookie
        String token = UUIDUtil.uuid();
        addCookie(user, token, response);
        return true;
    }

    public SpikeUser getByToken(HttpServletResponse response, String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        SpikeUser user = redisService.get(SpikeUserKey.token, token, SpikeUser.class);
        if (user != null) {
            addCookie(user, token, response);
        }
        return user;
    }

    public void addCookie(SpikeUser user, String token, HttpServletResponse response) {

        redisService.set(SpikeUserKey.token, token, user);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(SpikeUserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
