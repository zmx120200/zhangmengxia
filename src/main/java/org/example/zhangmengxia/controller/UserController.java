package org.example.zhangmengxia.controller;

import org.example.zhangmengxia.entity.User;
import org.example.zhangmengxia.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    // 登录接口
    @PostMapping("/login")
    @ResponseBody
    public Map<String, Object> login(String username, String password, HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        User user = userService.login(username, password);
        if (user != null) {
            session.setAttribute("userId", user.getId());
            session.setAttribute("username", user.getUsername());
            session.setAttribute("role", user.getRole());
            session.setAttribute("name", user.getName());
            
            result.put("success", true);
            result.put("message", "登录成功");
            result.put("user", user);
            result.put("redirect", user.isAdmin() ? "/admin/dashboard" : "/reader/dashboard");
        } else {
            result.put("success", false);
            result.put("message", "用户名或密码错误");
        }
        return result;
    }

    // 注销接口
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId != null) {
            userService.logout(userId);
        }
        session.invalidate();
        return "redirect:/toLoginPage";
    }

    // 注册接口
    @PostMapping("/register")
    @ResponseBody
    public Map<String, Object> register(User user) {
        Map<String, Object> result = new HashMap<>();
        boolean success = userService.register(user);
        if (success) {
            result.put("success", true);
            result.put("message", "注册成功");
        } else {
            result.put("success", false);
            result.put("message", "注册失败，用户名可能已存在");
        }
        return result;
    }

    // 获取当前用户信息
    @GetMapping("/current")
    @ResponseBody
    public Map<String, Object> getCurrentUser(HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId != null) {
            User user = userService.getUserById(userId);
            if (user != null) {
                result.put("success", true);
                result.put("user", user);
            } else {
                result.put("success", false);
                result.put("message", "用户不存在");
            }
        } else {
            result.put("success", false);
            result.put("message", "用户未登录");
        }
        return result;
    }

    // 更新用户信息
    @PostMapping("/update")
    @ResponseBody
    public Map<String, Object> updateUser(User user, HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        Integer currentUserId = (Integer) session.getAttribute("userId");
        
        // 检查权限：只能更新自己的信息，除非是管理员
        if (currentUserId == null) {
            result.put("success", false);
            result.put("message", "用户未登录");
            return result;
        }
        
        User currentUser = userService.getUserById(currentUserId);
        if (currentUser == null) {
            result.put("success", false);
            result.put("message", "当前用户不存在");
            return result;
        }
        
        // 如果不是管理员，只能更新自己的信息
        if (!currentUser.isAdmin() && !currentUserId.equals(user.getId())) {
            result.put("success", false);
            result.put("message", "没有权限更新其他用户的信息");
            return result;
        }
        
        boolean success = userService.updateUser(user);
        if (success) {
            result.put("success", true);
            result.put("message", "更新成功");
        } else {
            result.put("success", false);
            result.put("message", "更新失败");
        }
        return result;
    }

    // 获取所有用户（管理员权限）
    @GetMapping("/list")
    @ResponseBody
    public Map<String, Object> getAllUsers(HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        
        // 检查是否为管理员
        if (!isAdmin(session)) {
            result.put("success", false);
            result.put("message", "需要管理员权限");
            return result;
        }
        
        List<User> users = userService.getAllUsers();
        result.put("success", true);
        result.put("users", users);
        return result;
    }

    // 获取所有读者（管理员权限）
    @GetMapping("/readers")
    @ResponseBody
    public Map<String, Object> getAllReaders(HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        
        // 检查是否为管理员
        if (!isAdmin(session)) {
            result.put("success", false);
            result.put("message", "需要管理员权限");
            return result;
        }
        
        List<User> readers = userService.getAllReaders();
        result.put("success", true);
        result.put("readers", readers);
        return result;
    }

    // 删除用户（管理员权限）
    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public Map<String, Object> deleteUser(@PathVariable Integer id, HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        
        // 检查是否为管理员
        if (!isAdmin(session)) {
            result.put("success", false);
            result.put("message", "需要管理员权限");
            return result;
        }
        
        // 不能删除自己
        Integer currentUserId = (Integer) session.getAttribute("userId");
        if (currentUserId != null && currentUserId.equals(id)) {
            result.put("success", false);
            result.put("message", "不能删除当前登录的用户");
            return result;
        }
        
        boolean success = userService.deleteUser(id);
        if (success) {
            result.put("success", true);
            result.put("message", "删除成功");
        } else {
            result.put("success", false);
            result.put("message", "删除失败，用户可能不存在");
        }
        return result;
    }

    // 验证是否为管理员（用于权限控制）
    @GetMapping("/isAdmin")
    @ResponseBody
    public boolean isAdmin(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        return userService.isAdmin(userId);
    }

    // 检查登录状态
    @GetMapping("/checkLogin")
    @ResponseBody
    public Map<String, Object> checkLogin(HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId != null) {
            User user = userService.getUserById(userId);
            result.put("loggedIn", true);
            result.put("user", user);
        } else {
            result.put("loggedIn", false);
        }
        return result;
    }

    // 删除自己的账户
    @DeleteMapping("/delete/my")
    @ResponseBody
    public Map<String, Object> deleteMyAccount(@RequestParam String password, HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        Integer userId = (Integer) session.getAttribute("userId");
        
        if (userId == null) {
            result.put("success", false);
            result.put("message", "用户未登录");
            return result;
        }
        
        // 验证密码
        User user = userService.getUserById(userId);
        if (user == null) {
            result.put("success", false);
            result.put("message", "用户不存在");
            return result;
        }
        
        // 这里需要验证密码，假设UserService有一个验证密码的方法
        // 由于我们没有存储明文密码，这里简化处理
        boolean passwordValid = userService.validatePassword(userId, password);
        if (!passwordValid) {
            result.put("success", false);
            result.put("message", "密码错误");
            return result;
        }
        
        // 删除账户
        boolean success = userService.deleteUser(userId);
        if (success) {
            // 注销会话
            session.invalidate();
            result.put("success", true);
            result.put("message", "账户删除成功");
        } else {
            result.put("success", false);
            result.put("message", "删除失败");
        }
        return result;
    }

}
