package com.example.demo.mapper;

import com.example.demo.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

/**
 * 用于操作 h2 数据库数据
 * @author 有梦想的咸鱼
 */
@Mapper
@Component
public interface UserMapper {
    /*插入数据*/
    @Insert("insert into user (name,account_id,token,gmt_create,gmt_modified,avatar) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatar})")
    void insert(User user);

    /*其中param告诉mybatis将方法中的 token传入到sql语句中*/
    /*根据token获取对应的用户*/
    @Select("select * from user where token=#{token}")
    User findByToken(@Param("token") String token);

    @Select("select * from user where id=#{id}")
    User findByID(@Param("id") int id);

    @Select("select * from user where account_id=#{accountId}")
    User findByAccountID(@Param("accountId") String accountId);

    @Update("update user set token=#{token}, name=#{name}, gmt_modified=#{gmtModified}, avatar=#{avatar} where account_id=#{accountId}")
    void alterToken(User user);
}
