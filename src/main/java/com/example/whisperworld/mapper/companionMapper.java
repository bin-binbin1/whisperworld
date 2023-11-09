package com.example.whisperworld.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface companionMapper {
    @Select("SELECT userName FROM users WHERE userID in (SELECT friendId FROM Friends WHERE userId=#{userId} )")
    List<String> getAllFriends(Integer userId);

    @Select("SELECT u.userName FROM users AS u WHERE u.userID IN (SELECT f.friendId FROM Friends AS f WHERE f.userId = #{userId}) AND u.userName LIKE '#{prefix}%';")
    List<String> getFriendsByNAME(String prefix);

}
