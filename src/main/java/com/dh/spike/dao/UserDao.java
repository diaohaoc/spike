package com.dh.spike.dao;

import com.dh.spike.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Create by DiaoHao on 2020/10/18 15:21
 */
@Mapper
public interface UserDao {

    @Select("select * from user where id = #{id}")
    public User getById(@Param("id") int id);

    @Insert("inseret into user(id, name) values (#{id}, #{name})")
    public int insert(User user);
}
