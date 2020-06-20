package com.example.demo.mapper;

import com.example.demo.model.Question;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 用于实现问题提交部分的数据库操作
 * @author 有梦想的咸鱼
 * 在 count 方法中使用 count(1) 而不是 count(*) 是因为 1 的执行效率要比 * 快，*会匹配所有列，而1只需要匹配1列，
 * 当然count(主键名)在有主键索引的情况下是最好的
 * */

@Component
@Mapper
public interface QuestionMapper {
    @Insert("insert into question(title, description, gmt_create, gmt_modified, creator, tag) values(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void create(Question question);

    @Select("select * from question limit #{offset}, #{size}")
    List<Question> questionList(@Param("offset") int offset, @Param("size") int size);

    @Select("select count(1) from question")
    int count();

    @Select("select count(1) from question where creator=#{userId}")
    int countOfUser(@Param("userId") String userId);

    @Select("select * from question where creator=#{userId} limit #{offset}, #{size}")
    List<Question> questionUserList(@Param("userId") String userId, @Param("offset") int offset, @Param("size") int size);

    @Select("select * from question where id=#{id}")
    Question getById(@Param("id") int id);

    @Update("update question set title=#{title}, description=#{description}, gmt_modified=#{gmtModified}, tag=#{tag} where id = #{id}")
    void update(Question question);
}








































































