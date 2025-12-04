package org.example.zhangmengxia.service.impl;

import org.example.zhangmengxia.mapper.BookMapper;
import org.example.zhangmengxia.service.BookService;
import org.example.zhangmengxia.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    BookMapper bookMapper;

    @Override
    public List<Book> getBookList() {
        return bookMapper.getBookList();
    }

    @Override
    public List<Book> getBookListByPage(int start, int pageSize) {
        return bookMapper.getBookListByPage(start, pageSize);
    }

    @Override
    public int getBookTotalCount() {
        return bookMapper.getBookTotalCount();
    }

    @Override
    public Book getBookById(Integer id) {
        return bookMapper.getBookById(id);
    }

    @Override
    public boolean addBook(Book book) {
        book.setCreateTime(new Date());
        book.setUpdateTime(new Date());
        return bookMapper.addBook(book) > 0;
    }

    @Override
    public boolean updateBook(Book book) {
        book.setUpdateTime(new Date());
        return bookMapper.updateBook(book) > 0;
    }

    @Override
    public boolean deleteBook(Integer id) {
        return bookMapper.deleteBook(id) > 0;
    }

    @Override
    public List<Book> searchBooks(String keyword) {
        return bookMapper.searchBooks(keyword);
    }

    @Override
    public List<Book> getBooksByCategory(String category) {
        return bookMapper.getBooksByCategory(category);
    }

    @Override
    public List<String> getAllCategories() {
        return bookMapper.getAllCategories();
    }
}
