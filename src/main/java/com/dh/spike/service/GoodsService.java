package com.dh.spike.service;

import com.dh.spike.dao.GoodsDao;
import com.dh.spike.domain.SpikeGoods;
import com.dh.spike.vo.GoodsVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Create by DiaoHao on 2020/10/23 18:48
 */
@Service
public class GoodsService {

    @Resource
    GoodsDao goodsDao;

    public List<GoodsVo> listGoodsVo() {
        return goodsDao.listGoodsVo();
    }

    public GoodsVo getGoodsByGoodsId(long id) {
        return goodsDao.getGoodsByGoodsId(id);
    }

    public void reduceStock(GoodsVo goods) {
        SpikeGoods g = new SpikeGoods();
        g.setGoodsId(goods.getId());
        goodsDao.recudeStock(g);
    }
}
