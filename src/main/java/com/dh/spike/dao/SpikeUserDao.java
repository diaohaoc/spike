package com.dh.spike.dao;


import com.dh.spike.domain.SpikeUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SpikeUserDao {

    @Select("select * from spike_user where id = #{id}")
    public SpikeUser getById(@Param("id") long id);
}
