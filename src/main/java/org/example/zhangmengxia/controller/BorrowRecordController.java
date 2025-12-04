package org.example.zhangmengxia.controller;

import org.example.zhangmengxia.dto.BorrowRecordDTO;
import org.example.zhangmengxia.entity.BorrowRecord;
import org.example.zhangmengxia.entity.User;
import org.example.zhangmengxia.service.BorrowRecordService;
import org.example.zhangmengxia.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/borrow")
public class BorrowRecordController {

    @Resource
    private BorrowRecordService borrowRecordService;
    
    @Resource
    private UserService userService;
    
    // 借阅管理页面
    @GetMapping("/manage")
    public String borrowManagePage() {
        return "admin/borrowManagement"; // 需要创建这个HTML文件
    }

    // 获取当前用户的借阅历史
    @GetMapping("/history")
    @ResponseBody
    public Map<String, Object> getMyBorrowHistory(HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        Integer userId = (Integer) session.getAttribute("userId");
        
        if (userId == null) {
            result.put("success", false);
            result.put("message", "用户未登录");
            return result;
        }
        
        List<BorrowRecord> records = borrowRecordService.findByUserId(userId);
        result.put("success", true);
        result.put("records", records);
        result.put("count", records.size());
        return result;
    }

    // 获取指定用户的借阅历史（管理员权限）
    @GetMapping("/history/{userId}")
    @ResponseBody
    public Map<String, Object> getUserBorrowHistory(@PathVariable Integer userId, HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        
        // 检查是否为管理员
        if (!isAdmin(session)) {
            result.put("success", false);
            result.put("message", "需要管理员权限");
            return result;
        }
        
        List<BorrowRecord> records = borrowRecordService.findByUserId(userId);
        result.put("success", true);
        result.put("records", records);
        result.put("count", records.size());
        return result;
    }

    // 借书
    @PostMapping("/borrow")
    @ResponseBody
    public Map<String, Object> borrowBook(@RequestParam Integer bookId, 
                                          @RequestParam(defaultValue = "30") Integer days,
                                          HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        Integer userId = (Integer) session.getAttribute("userId");
        
        if (userId == null) {
            result.put("success", false);
            result.put("message", "用户未登录");
            return result;
        }
        
        boolean success = borrowRecordService.borrowBook(userId, bookId, days);
        if (success) {
            result.put("success", true);
            result.put("message", "借书成功");
        } else {
            result.put("success", false);
            result.put("message", "借书失败，可能图书库存不足或已达到借阅上限");
        }
        return result;
    }

    // 还书
    @PostMapping("/return/{id}")
    @ResponseBody
    public Map<String, Object> returnBook(@PathVariable Integer id, HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        
        // 检查权限：用户只能还自己的书，管理员可以还任何书
        Integer userId = (Integer) session.getAttribute("userId");
        boolean isAdmin = isAdmin(session);
        
        if (userId == null) {
            result.put("success", false);
            result.put("message", "用户未登录");
            return result;
        }
        
        BorrowRecord record = borrowRecordService.findById(id);
        if (record == null) {
            result.put("success", false);
            result.put("message", "借阅记录不存在");
            return result;
        }
        
        // 检查权限
        if (!isAdmin && !record.getUserId().equals(userId)) {
            result.put("success", false);
            result.put("message", "没有权限还此书");
            return result;
        }
        
        boolean success = borrowRecordService.returnBook(id);
        if (success) {
            result.put("success", true);
            result.put("message", "还书成功");
        } else {
            result.put("success", false);
            result.put("message", "还书失败");
        }
        return result;
    }

    // 续借
    @PostMapping("/renew/{id}")
    @ResponseBody
    public Map<String, Object> renewBook(@PathVariable Integer id,
                                         @RequestParam(defaultValue = "30") Integer additionalDays,
                                         HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        Integer userId = (Integer) session.getAttribute("userId");
        
        if (userId == null) {
            result.put("success", false);
            result.put("message", "用户未登录");
            return result;
        }
        
        BorrowRecord record = borrowRecordService.findById(id);
        if (record == null) {
            result.put("success", false);
            result.put("message", "借阅记录不存在");
            return result;
        }
        
        // 检查权限：用户只能续借自己的书
        if (!record.getUserId().equals(userId)) {
            result.put("success", false);
            result.put("message", "没有权限续借此书");
            return result;
        }
        
        boolean success = borrowRecordService.renewBook(id, additionalDays);
        if (success) {
            result.put("success", true);
            result.put("message", "续借成功");
        } else {
            result.put("success", false);
            result.put("message", "续借失败，可能已达到最大续借次数");
        }
        return result;
    }

    // 获取所有借阅记录（管理员权限）
    @GetMapping("/all")
    @ResponseBody
    public Map<String, Object> getAllBorrowRecords(HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        
        // 检查是否为管理员
        if (!isAdmin(session)) {
            result.put("success", false);
            result.put("message", "需要管理员权限");
            return result;
        }
        
        List<BorrowRecordDTO> records = borrowRecordService.findAllWithDetails();
        result.put("success", true);
        result.put("records", records);
        result.put("count", records.size());
        return result;
    }

    // 获取逾期借阅记录（管理员权限）
    @GetMapping("/overdue")
    @ResponseBody
    public Map<String, Object> getOverdueRecords(HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        
        // 检查是否为管理员
        if (!isAdmin(session)) {
            result.put("success", false);
            result.put("message", "需要管理员权限");
            return result;
        }
        
        List<BorrowRecord> records = borrowRecordService.findOverdueRecords();
        result.put("success", true);
        result.put("records", records);
        result.put("count", records.size());
        return result;
    }

    // 获取借阅统计信息
    @GetMapping("/stats")
    @ResponseBody
    public Map<String, Object> getBorrowStats(HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        Integer userId = (Integer) session.getAttribute("userId");
        
        if (userId == null) {
            result.put("success", false);
            result.put("message", "用户未登录");
            return result;
        }
        
        int totalBorrowed = borrowRecordService.countByUserId(userId);
        List<BorrowRecord> currentBorrows = borrowRecordService.findByUserIdAndStatus(userId, BorrowRecord.STATUS_BORROWING);
        List<BorrowRecord> overdueBorrows = borrowRecordService.findOverdueRecords();
        
        // 过滤出当前用户的逾期记录
        long userOverdueCount = overdueBorrows.stream()
                .filter(record -> record.getUserId().equals(userId))
                .count();
        
        result.put("success", true);
        result.put("totalBorrowed", totalBorrowed);
        result.put("currentBorrows", currentBorrows.size());
        result.put("overdueBorrows", userOverdueCount);
        return result;
    }

    // 检查是否为管理员
    private boolean isAdmin(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        return userService.isAdmin(userId);
    }
}
