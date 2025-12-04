package org.example.zhangmengxia.controller;

import org.example.zhangmengxia.entity.User;
import org.example.zhangmengxia.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Calendar;

@Controller
public class LoginController {
    
    @Resource
    private UserService userService;
    
    @GetMapping("/")
    public String index() {
        return "index";
    }
    
    @GetMapping("/toLoginPage")
    public String toLoginPage(Model model){
        model.addAttribute("currentYear", Calendar.getInstance().get(Calendar.YEAR));
        return "login";
    }
    
    // 读者仪表板页面
    @GetMapping("/reader/dashboard")
    public String readerDashboard(HttpSession session) {
        // 检查用户是否已登录
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/toLoginPage"; // 未登录，跳转到登录页
        }
        
        // 检查用户角色
        User user = userService.getUserById(userId);
        if (user == null) {
            return "redirect:/toLoginPage"; // 用户不存在，跳转到登录页
        }
        
        // 如果是管理员，跳转到管理员仪表板
        if (user.isAdmin()) {
            return "redirect:/admin/dashboard";
        }
        
        return "reader/dashboard";
    }
    
    // 管理员仪表板页面
    @GetMapping("/admin/dashboard")
    public String adminDashboard(HttpSession session) {
        // 检查用户是否已登录且为管理员
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/toLoginPage"; // 未登录，跳转到登录页
        }
        
        User user = userService.getUserById(userId);
        if (user == null || !user.isAdmin()) {
            return "redirect:/toLoginPage"; // 不是管理员，跳转到登录页
        }
        
        return "admin/dashboard";
    }
    
    // 借阅历史页面
    @GetMapping("/reader/borrowHistory")
    public String borrowHistory(HttpSession session) {
        // 检查用户是否已登录
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/toLoginPage"; // 未登录，跳转到登录页
        }
        
        // 检查用户角色
        User user = userService.getUserById(userId);
        if (user == null) {
            return "redirect:/toLoginPage"; // 用户不存在，跳转到登录页
        }
        
        // 如果是管理员，跳转到管理员仪表板
        if (user.isAdmin()) {
            return "redirect:/admin/dashboard";
        }
        
        return "reader/borrowHistory";
    }
}
