package com.example.whisperworld.mapper;

import com.example.whisperworld.entity.Crowds;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface groupMapper {

    @Insert("INSERT INTO crowds VALUES (#{groupId},#{masterId},#{managerId},#{groupName},#{createDate},#{groupNum},#{groupBackground})")
    void createGroup(Crowds crowd);//创建群组

}
