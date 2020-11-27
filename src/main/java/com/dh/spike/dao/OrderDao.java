package com.dh.spike.dao;


import com.dh.spike.domain.OrderInfo;
import com.dh.spike.domain.SpikeOrder;
import org.apache.ibatis.annotations.*;


@Mapper
public interface OrderDao {

    @Select("select * from spike_order where user_id = #{userId} and goods_id = #{goodsId}")
    public SpikeOrder getSpikeOrderByUserIdGoodsId(@Param("userId") long userId, long goodsId);

    @Insert("insert into order_info(user_id,goods_id,goods_name,goods_count,goods_price,order_channel,status,create_date)" +
            "values(#{userId},#{goodsId},#{goodsName},#{goodsCount},#{goodsPrice},#{orderChannel},#{status},#{createDate})")
    @SelectKey(keyColumn = "id", keyProperty = "id", resultType = long.class,
            before = false, statement = "select last_insert_id()")
    long insert(OrderInfo orderInfo);

    @Insert("insert into spike_order(user_id,order_id,goods_id)values(#{userId},#{orderId},#{goodsId})")
    int insertSpikeOrder(SpikeOrder spikeOrder);

    @Select("select * from order_info where id = #{orderId}")
    OrderInfo getOrderById(@Param("orderId") long orderId);
}
