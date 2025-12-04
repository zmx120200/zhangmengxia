package org.example.zhangmengxia.service;

import org.example.zhangmengxia.entity.User;

import java.util.List;

public interface UserService {
    // 登录认证
    User login(String username, String password);
    
    // 注销
    boolean logout(Integer userId);
    
    // 用户管理
    User getUserById(Integer userId);
    User getUserByUsername(String username);
    List<User> getAllUsers();
    List<User> getAllReaders();
    List<User> getAllAdmins();
    boolean addUser(User user);
    boolean updateUser(User user);
    boolean deleteUser(Integer id);
    
    // 权限检查
    boolean isAdmin(Integer userId);
    
    // 注册新用户
    boolean register(User user);
    
    // 验证密码
    boolean validatePassword(Integer userId, String password);
}
