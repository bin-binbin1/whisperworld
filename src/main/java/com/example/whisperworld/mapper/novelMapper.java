package com.example.whisperworld.mapper;

import com.example.whisperworld.entity.Novel;
import com.example.whisperworld.entity.bookmark;
import com.example.whisperworld.entity.bookmarks;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface novelMapper {
    @Select("SELECT id,title,author,description,picture,chapter FROM novels")
    List<Novel> getAllNovels();
    @Select("SELECT path FROM novels WHERE id=#{id}")
    String getPathByID(int id);
    @Select("SELECT novels.title, bookmarks.chapter, bookmarks.page " +
            "FROM bookmarks " +
            "JOIN novels ON bookmarks.novel_id = novels.id WHERE " +
            "bookmark.userID=#{userID};")
    List<bookmark> getBookmarkByID(int userID);
    @Insert("INSERT INTO bookmarks (userID,novel_id,page_number,chapter) " +
            "VALUES(#{userID},#{novel_id},#{page_number},#{chapter})")
    void addmark(bookmarks marks);
}
