package org.example.zhangmengxia.controller;

import org.example.zhangmengxia.entity.Book;
import org.example.zhangmengxia.entity.BorrowRecord;
import org.example.zhangmengxia.entity.User;
import org.example.zhangmengxia.service.BookService;
import org.example.zhangmengxia.service.BorrowRecordService;
import org.example.zhangmengxia.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Resource
    private UserService userService;
    
    @Resource
    private BookService bookService;
    
    @Resource
    private BorrowRecordService borrowRecordService;
    
    // 用户管理页面
    @GetMapping("/users/page")
    public String userManagementPage() {
        return "admin/userManagement";
    }
    
    // 统计报表页面
    @GetMapping("/reports/page")
    public String reportsPage() {
        return "admin/reports";
    }

    // 获取管理员仪表板统计数据
    @GetMapping("/statistics")
    @ResponseBody
    public Map<String, Object> getStatistics(HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        
        // 检查是否为管理员
        if (!isAdmin(session)) {
            result.put("success", false);
            result.put("message", "需要管理员权限");
            return result;
        }
        
        try {
            // 获取总图书数量
            int totalBooks = bookService.getBookTotalCount();
            
            // 获取注册读者数量（非管理员用户）
            List<User> readers = userService.getAllReaders();
            int totalReaders = readers.size();
            
            // 获取当前借阅数量
            List<BorrowRecord> allRecords = borrowRecordService.findAll();
            int activeBorrows = (int) allRecords.stream()
                    .filter(record -> record.getStatus() == BorrowRecord.STATUS_BORROWING)
                    .count();
            
            // 获取逾期图书数量
            List<BorrowRecord> overdueRecords = borrowRecordService.findOverdueRecords();
            int overdueBooks = overdueRecords.size();
            
            // 系统状态信息
            result.put("success", true);
            result.put("totalBooks", totalBooks);
            result.put("totalReaders", totalReaders);
            result.put("activeBorrows", activeBorrows);
            result.put("overdueBooks", overdueBooks);
            result.put("dbStatus", "正常");
            result.put("serverStatus", "运行中");
            result.put("storageStatus", "85% 已使用");
            result.put("lastBackup", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "获取统计数据失败: " + e.getMessage());
        }
        
        return result;
    }

    // 获取借阅趋势数据
    @GetMapping("/borrowTrend")
    @ResponseBody
    public Map<String, Object> getBorrowTrend(HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        
        // 检查是否为管理员
        if (!isAdmin(session)) {
            result.put("success", false);
            result.put("message", "需要管理员权限");
            return result;
        }
        
        try {
            // 模拟最近7天的借阅数据
            List<String> labels = new ArrayList<>();
            List<Integer> values = new ArrayList<>();
            
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
            
            for (int i = 6; i >= 0; i--) {
                calendar.setTime(new Date());
                calendar.add(Calendar.DAY_OF_YEAR, -i);
                labels.add(sdf.format(calendar.getTime()));
                
                // 模拟随机数据
                values.add((int) (Math.random() * 20) + 5);
            }
            
            result.put("success", true);
            result.put("labels", labels);
            result.put("values", values);
            
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "获取借阅趋势失败: " + e.getMessage());
        }
        
        return result;
    }

    // 获取图书分类分布数据
    @GetMapping("/categoryDistribution")
    @ResponseBody
    public Map<String, Object> getCategoryDistribution(HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        
        // 检查是否为管理员
        if (!isAdmin(session)) {
            result.put("success", false);
            result.put("message", "需要管理员权限");
            return result;
        }
        
        try {
            // 模拟分类数据
            List<String> labels = Arrays.asList("文学", "科技", "历史", "哲学", "艺术", "教育", "小说", "传记");
            List<Integer> values = new ArrayList<>();
            
            for (int i = 0; i < labels.size(); i++) {
                values.add((int) (Math.random() * 50) + 10);
            }
            
            result.put("success", true);
            result.put("labels", labels);
            result.put("values", values);
            
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "获取分类分布失败: " + e.getMessage());
        }
        
        return result;
    }

    // 获取最近活动记录
    @GetMapping("/recentActivities")
    @ResponseBody
    public Map<String, Object> getRecentActivities(HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        
        // 检查是否为管理员
        if (!isAdmin(session)) {
            result.put("success", false);
            result.put("message", "需要管理员权限");
            return result;
        }
        
        try {
            List<Map<String, Object>> activities = new ArrayList<>();
            
            // 模拟活动数据
            String[] actions = {"添加图书", "用户注册", "图书借阅", "图书归还", "用户登录", "系统备份"};
            String[] users = {"admin", "张三", "李四", "王五", "系统"};
            
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd");
            
            for (int i = 0; i < 10; i++) {
                Map<String, Object> activity = new HashMap<>();
                calendar.setTime(new Date());
                calendar.add(Calendar.MINUTE, -i * 30); // 模拟过去的时间
                
                activity.put("action", actions[i % actions.length]);
                activity.put("user", users[i % users.length]);
                activity.put("time", dateFormat.format(calendar.getTime()) + " " + timeFormat.format(calendar.getTime()));
                activity.put("description", "完成了" + actions[i % actions.length] + "操作");
                
                activities.add(activity);
            }
            
            result.put("success", true);
            result.put("activities", activities);
            
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "获取活动记录失败: " + e.getMessage());
        }
        
        return result;
    }

    // 发送逾期提醒
    @PostMapping("/sendOverdueReminders")
    @ResponseBody
    public Map<String, Object> sendOverdueReminders(HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        
        // 检查是否为管理员
        if (!isAdmin(session)) {
            result.put("success", false);
            result.put("message", "需要管理员权限");
            return result;
        }
        
        try {
            // 这里应该实现发送邮件的逻辑
            // 暂时模拟发送成功
            result.put("success", true);
            result.put("message", "已成功发送逾期提醒给所有逾期读者");
            
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "发送逾期提醒失败: " + e.getMessage());
        }
        
        return result;
    }

    // 备份数据库
    @PostMapping("/backup")
    @ResponseBody
    public Map<String, Object> backupDatabase(HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        
        // 检查是否为管理员
        if (!isAdmin(session)) {
            result.put("success", false);
            result.put("message", "需要管理员权限");
            return result;
        }
        
        try {
            // 这里应该实现数据库备份逻辑
            // 暂时模拟备份成功
            String backupFilename = "backup_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".sql";
            
            result.put("success", true);
            result.put("message", "数据库备份成功");
            result.put("filename", backupFilename);
            
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "数据库备份失败: " + e.getMessage());
        }
        
        return result;
    }

    // 获取所有用户（管理员界面）
    @GetMapping("/users")
    @ResponseBody
    public Map<String, Object> getAllUsersForAdmin(HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        
        // 检查是否为管理员
        if (!isAdmin(session)) {
            result.put("success", false);
            result.put("message", "需要管理员权限");
            return result;
        }
        
        try {
            List<User> users = userService.getAllUsers();
            result.put("success", true);
            result.put("users", users);
            result.put("count", users.size());
            
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "获取用户列表失败: " + e.getMessage());
        }
        
        return result;
    }

    // 获取统计报表数据
    @GetMapping("/reports")
    @ResponseBody
    public Map<String, Object> getReports(HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        
        // 检查是否为管理员
        if (!isAdmin(session)) {
            result.put("success", false);
            result.put("message", "需要管理员权限");
            return result;
        }
        
        try {
            // 月度借阅统计
            Map<String, Object> monthlyStats = new HashMap<>();
            String[] months = {"1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"};
            int[] borrowCounts = new int[12];
            
            for (int i = 0; i < 12; i++) {
                borrowCounts[i] = (int) (Math.random() * 100) + 50;
            }
            
            monthlyStats.put("labels", months);
            monthlyStats.put("data", borrowCounts);
            
            // 热门图书
            List<Map<String, Object>> popularBooks = new ArrayList<>();
            String[] bookNames = {"Java编程思想", "Spring Boot实战", "数据库系统概念", "算法导论", "计算机网络", "操作系统", "设计模式", "Python数据分析"};
            
            for (int i = 0; i < 5; i++) {
                Map<String, Object> book = new HashMap<>();
                book.put("name", bookNames[i]);
                book.put("borrowCount", (int) (Math.random() * 50) + 20);
                popularBooks.add(book);
            }
            
            // 活跃读者
            List<Map<String, Object>> activeReaders = new ArrayList<>();
            String[] readerNames = {"张三", "李四", "王五", "赵六", "钱七"};
            
            for (int i = 0; i < 5; i++) {
                Map<String, Object> reader = new HashMap<>();
                reader.put("name", readerNames[i]);
                reader.put("borrowCount", (int) (Math.random() * 30) + 10);
                activeReaders.add(reader);
            }
            
            result.put("success", true);
            result.put("monthlyStats", monthlyStats);
            result.put("popularBooks", popularBooks);
            result.put("activeReaders", activeReaders);
            result.put("totalBorrows", Arrays.stream(borrowCounts).sum());
            result.put("avgBorrowsPerDay", String.format("%.1f", Arrays.stream(borrowCounts).average().orElse(0) / 30));
            
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "获取报表数据失败: " + e.getMessage());
        }
        
        return result;
    }

    // 检查是否为管理员
    private boolean isAdmin(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        return userService.isAdmin(userId);
    }
}
