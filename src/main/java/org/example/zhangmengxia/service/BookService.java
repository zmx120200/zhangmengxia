package org.example.zhangmengxia.service;

import org.example.zhangmengxia.entity.Book;

import java.util.List;

public interface BookService {
    List<Book> getBookList();

    List<Book> getBookListByPage(int start, int pageSize);

    int getBookTotalCount();
    
    Book getBookById(Integer id);
    
    boolean addBook(Book book);
    
    boolean updateBook(Book book);
    
    boolean deleteBook(Integer id);
    
    List<Book> searchBooks(String keyword);
    
    List<Book> getBooksByCategory(String category);
    
    List<String> getAllCategories();
}
