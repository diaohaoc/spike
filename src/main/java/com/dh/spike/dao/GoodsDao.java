package com.dh.spike.dao;


import com.dh.spike.domain.SpikeGoods;
import com.dh.spike.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface GoodsDao {

    @Select("select " +
            "g.*, sg.stock_count, sg.start_date, sg.end_date, sg.spike_price " +
            "from spike_goods sg left join goods g on sg.goods_id = g.id")
    public List<GoodsVo> listGoodsVo();

    @Select("select " +
            "g.*, sg.stock_count, sg.start_date, sg.end_date, sg.spike_price " +
            "from spike_goods sg left join goods g on sg.goods_id = g.id where g.id = #{goodsId}")
    public GoodsVo getGoodsByGoodsId(@Param("goodsId") long id);

    @Update("update spike_goods set stock_count = stock_count - 1 where goods_id = #{goodsId}")
    public int recudeStock(SpikeGoods g);
}
