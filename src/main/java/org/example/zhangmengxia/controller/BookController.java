package org.example.zhangmengxia.controller;

import org.example.zhangmengxia.entity.Book;
import org.example.zhangmengxia.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/book")
public class BookController {
    
    // 图书管理页面
    @GetMapping("/manage")
    public String bookManagePage() {
        return "bookList"; // 返回图书管理页面
    }
    
    // 添加图书页面
    @GetMapping("/add")
    public String addBookPage() {
        return "bookList"; // 暂时返回同一个页面，由前端JavaScript处理
    }

    @Resource
    private BookService bookService;

    // 获取图书列表（分页）
    @GetMapping("/list")
    @ResponseBody
    public Map<String, Object> getBookList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String category) {
        
        Map<String, Object> result = new HashMap<>();
        
        try {
            // 计算起始位置
            int start = (page - 1) * size;
            
            // 获取图书列表
            List<Book> books = bookService.getBookListByPage(start, size);
            
            // 如果数据库中没有数据，返回一些模拟数据用于测试
            if (books == null || books.isEmpty()) {
                books = createSampleBooks();
            }
            
            // 这里应该根据search和category参数进行筛选
            // 由于BookService没有实现筛选功能，暂时返回所有图书
            if (search != null && !search.trim().isEmpty()) {
                // 模拟搜索功能
                String searchLower = search.toLowerCase();
                books.removeIf(book -> 
                    !book.getBookName().toLowerCase().contains(searchLower) &&
                    !book.getAuthor().toLowerCase().contains(searchLower) &&
                    !book.getIsbn().toLowerCase().contains(searchLower)
                );
            }
            
            if (category != null && !category.trim().isEmpty()) {
                // 模拟分类筛选
                books.removeIf(book -> !category.equals(book.getCategory()));
            }
            
            // 获取总数量
            int total = bookService.getBookTotalCount();
            if (total == 0) {
                total = books.size();
            }
            
            result.put("success", true);
            result.put("books", books);
            result.put("total", total);
            result.put("page", page);
            result.put("size", size);
            result.put("totalPages", (int) Math.ceil((double) total / size));
            
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "获取图书列表失败: " + e.getMessage());
        }
        
        return result;
    }

    // 获取单个图书详情
    @GetMapping("/{id}")
    @ResponseBody
    public Map<String, Object> getBookById(@PathVariable Integer id) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            Book book = bookService.getBookById(id);
            
            if (book != null) {
                result.put("success", true);
                result.put("book", book);
            } else {
                result.put("success", false);
                result.put("message", "图书不存在");
            }
            
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "获取图书详情失败: " + e.getMessage());
        }
        
        return result;
    }

    // 添加图书
    @PostMapping("/add")
    @ResponseBody
    public Map<String, Object> addBook(@RequestBody Book book, HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        
        // 检查是否为管理员
        if (!isAdmin(session)) {
            result.put("success", false);
            result.put("message", "需要管理员权限");
            return result;
        }
        
        try {
            // 验证必填字段
            if (book.getBookName() == null || book.getBookName().trim().isEmpty()) {
                result.put("success", false);
                result.put("message", "图书名称不能为空");
                return result;
            }
            
            if (book.getAuthor() == null || book.getAuthor().trim().isEmpty()) {
                result.put("success", false);
                result.put("message", "作者不能为空");
                return result;
            }
            
            if (book.getIsbn() == null || book.getIsbn().trim().isEmpty()) {
                result.put("success", false);
                result.put("message", "ISBN不能为空");
                return result;
            }
            
            if (book.getStock() == null || book.getStock() < 0) {
                result.put("success", false);
                result.put("message", "库存数量不能为负数");
                return result;
            }
            
            // 调用BookService的添加方法
            boolean success = bookService.addBook(book);
            if (success) {
                result.put("success", true);
                result.put("message", "添加图书成功");
                result.put("bookId", book.getBookId());
            } else {
                result.put("success", false);
                result.put("message", "添加图书失败");
            }
            
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "添加图书失败: " + e.getMessage());
        }
        
        return result;
    }

    // 更新图书
    @PostMapping("/update")
    @ResponseBody
    public Map<String, Object> updateBook(@RequestBody Book book, HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        
        // 检查是否为管理员
        if (!isAdmin(session)) {
            result.put("success", false);
            result.put("message", "需要管理员权限");
            return result;
        }
        
        try {
            // 验证必填字段
            if (book.getBookId() == null) {
                result.put("success", false);
                result.put("message", "图书ID不能为空");
                return result;
            }
            
            if (book.getBookName() == null || book.getBookName().trim().isEmpty()) {
                result.put("success", false);
                result.put("message", "图书名称不能为空");
                return result;
            }
            
            if (book.getAuthor() == null || book.getAuthor().trim().isEmpty()) {
                result.put("success", false);
                result.put("message", "作者不能为空");
                return result;
            }
            
            if (book.getIsbn() == null || book.getIsbn().trim().isEmpty()) {
                result.put("success", false);
                result.put("message", "ISBN不能为空");
                return result;
            }
            
            if (book.getStock() == null || book.getStock() < 0) {
                result.put("success", false);
                result.put("message", "库存数量不能为负数");
                return result;
            }
            
            // 调用BookService的更新方法
            boolean success = bookService.updateBook(book);
            if (success) {
                result.put("success", true);
                result.put("message", "更新图书成功");
            } else {
                result.put("success", false);
                result.put("message", "更新图书失败");
            }
            
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "更新图书失败: " + e.getMessage());
        }
        
        return result;
    }

    // 删除图书
    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public Map<String, Object> deleteBook(@PathVariable Integer id, HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        
        // 检查是否为管理员
        if (!isAdmin(session)) {
            result.put("success", false);
            result.put("message", "需要管理员权限");
            return result;
        }
        
        try {
            // 调用BookService的删除方法
            boolean success = bookService.deleteBook(id);
            if (success) {
                result.put("success", true);
                result.put("message", "删除图书成功");
            } else {
                result.put("success", false);
                result.put("message", "删除图书失败");
            }
            
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "删除图书失败: " + e.getMessage());
        }
        
        return result;
    }

    // 获取所有图书分类
    @GetMapping("/categories")
    @ResponseBody
    public Map<String, Object> getAllCategories() {
        Map<String, Object> result = new HashMap<>();
        
        try {
            List<String> categories = bookService.getAllCategories();
            
            result.put("success", true);
            result.put("categories", categories);
            
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "获取分类列表失败: " + e.getMessage());
        }
        
        return result;
    }

    // 获取图书统计信息
    @GetMapping("/stats")
    @ResponseBody
    public Map<String, Object> getBookStats(HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        
        // 检查是否为管理员
        if (!isAdmin(session)) {
            result.put("success", false);
            result.put("message", "需要管理员权限");
            return result;
        }
        
        try {
            List<Book> allBooks = bookService.getBookList();
            
            int totalBooks = allBooks.size();
            int totalStock = allBooks.stream().mapToInt(Book::getStock).sum();
            int outOfStock = (int) allBooks.stream().filter(b -> b.getStock() == 0).count();
            int lowStock = (int) allBooks.stream().filter(b -> b.getStock() > 0 && b.getStock() <= 5).count();
            
            // 分类统计
            Map<String, Integer> categoryStats = new HashMap<>();
            for (Book book : allBooks) {
                String category = book.getCategory() != null ? book.getCategory() : "未分类";
                categoryStats.put(category, categoryStats.getOrDefault(category, 0) + 1);
            }
            
            result.put("success", true);
            result.put("totalBooks", totalBooks);
            result.put("totalStock", totalStock);
            result.put("outOfStock", outOfStock);
            result.put("lowStock", lowStock);
            result.put("categoryStats", categoryStats);
            
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "获取图书统计失败: " + e.getMessage());
        }
        
        return result;
    }

    // 检查是否为管理员
    private boolean isAdmin(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) return false;
        
        // 这里应该调用UserService的isAdmin方法
        // 暂时模拟：用户ID为1的是管理员
        return userId == 1;
    }
    
    // 创建示例图书数据
    private List<Book> createSampleBooks() {
        List<Book> books = new ArrayList<>();
        
        // 添加一些示例图书
        Book book1 = new Book();
        book1.setBookId(1);
        book1.setBookName("Java编程思想");
        book1.setAuthor("Bruce Eckel");
        book1.setIsbn("9787111213826");
        book1.setCategory("计算机");
        book1.setStock(10);
        book1.setPublisher("机械工业出版社");
        book1.setDescription("Java编程经典书籍");
        books.add(book1);
        
        Book book2 = new Book();
        book2.setBookId(2);
        book2.setBookName("深入理解计算机系统");
        book2.setAuthor("Randal E. Bryant");
        book2.setIsbn("9787111321330");
        book2.setCategory("计算机");
        book2.setStock(5);
        book2.setPublisher("机械工业出版社");
        book2.setDescription("计算机系统经典教材");
        books.add(book2);
        
        Book book3 = new Book();
        book3.setBookId(3);
        book3.setBookName("百年孤独");
        book3.setAuthor("加西亚·马尔克斯");
        book3.setIsbn("9787544253994");
        book3.setCategory("文学");
        book3.setStock(8);
        book3.setPublisher("南海出版公司");
        book3.setDescription("魔幻现实主义文学代表作");
        books.add(book3);
        
        Book book4 = new Book();
        book4.setBookId(4);
        book4.setBookName("人类简史");
        book4.setAuthor("尤瓦尔·赫拉利");
        book4.setIsbn("9787508647357");
        book4.setCategory("历史");
        book4.setStock(12);
        book4.setPublisher("中信出版社");
        book4.setDescription("从动物到上帝的人类发展史");
        books.add(book4);
        
        Book book5 = new Book();
        book5.setBookId(5);
        book5.setBookName("经济学原理");
        book5.setAuthor("曼昆");
        book5.setIsbn("9787301250058");
        book5.setCategory("经济");
        book5.setStock(7);
        book5.setPublisher("北京大学出版社");
        book5.setDescription("经济学入门经典教材");
        books.add(book5);
        
        return books;
    }
}
