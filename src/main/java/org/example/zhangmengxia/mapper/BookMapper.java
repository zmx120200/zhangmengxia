package org.example.zhangmengxia.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.zhangmengxia.entity.Book;

import java.util.List;

@Mapper
public interface BookMapper {
    List<Book> getBookList();
    
    List<Book> getBookListByPage(@Param("start") int start, @Param("pageSize") int pageSize);
    
    int getBookTotalCount();
    
    Book getBookById(@Param("id") Integer id);
    
    int addBook(Book book);
    
    int updateBook(Book book);
    
    int deleteBook(@Param("id") Integer id);
    
    List<Book> searchBooks(@Param("keyword") String keyword);
    
    List<Book> getBooksByCategory(@Param("category") String category);
    
    List<String> getAllCategories();
}
