package org.example.zhangmengxia.service.impl;

import org.example.zhangmengxia.entity.User;
import org.example.zhangmengxia.mapper.UserMapper;
import org.example.zhangmengxia.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User login(String username, String password) {
        if (!StringUtils.hasText(username) || !StringUtils.hasText(password)) {
            return null;
        }
        return userMapper.login(username, password);
    }

    @Override
    public boolean logout(Integer userId) {
        if (userId == null) {
            return false;
        }
        return userMapper.logout(userId) > 0;
    }

    @Override
    public User getUserById(Integer userId) {
        if (userId == null) {
            return null;
        }
        return userMapper.findById(userId);
    }

    @Override
    public User getUserByUsername(String username) {
        if (!StringUtils.hasText(username)) {
            return null;
        }
        return userMapper.findByUsername(username);
    }

    @Override
    public List<User> getAllUsers() {
        return userMapper.findAll();
    }

    @Override
    public List<User> getAllReaders() {
        return userMapper.findAllReaders();
    }

    @Override
    public List<User> getAllAdmins() {
        return userMapper.findAllAdmins();
    }

    @Override
    public boolean addUser(User user) {
        if (user == null || !StringUtils.hasText(user.getUsername()) || !StringUtils.hasText(user.getPassword())) {
            return false;
        }
        
        // 检查用户名是否已存在
        User existingUser = userMapper.findByUsername(user.getUsername());
        if (existingUser != null) {
            return false;
        }
        
        // 设置默认角色为读者
        if (user.getRole() == null) {
            user.setRole(User.ROLE_READER);
        }
        
        return userMapper.add(user) > 0;
    }

    @Override
    public boolean updateUser(User user) {
        if (user == null || user.getId() == null) {
            return false;
        }
        
        // 检查用户是否存在
        User existingUser = userMapper.findById(user.getId());
        if (existingUser == null) {
            return false;
        }
        
        return userMapper.update(user) > 0;
    }

    @Override
    public boolean deleteUser(Integer id) {
        if (id == null) {
            return false;
        }
        
        // 检查用户是否存在
        User existingUser = userMapper.findById(id);
        if (existingUser == null) {
            return false;
        }
        
        return userMapper.delete(id) > 0;
    }

    @Override
    public boolean isAdmin(Integer userId) {
        if (userId == null) {
            return false;
        }
        User user = userMapper.findById(userId);
        return user != null && user.isAdmin();
    }

    @Override
    public boolean register(User user) {
        if (user == null || !StringUtils.hasText(user.getUsername()) || !StringUtils.hasText(user.getPassword())) {
            return false;
        }
        
        // 检查用户名是否已存在
        User existingUser = userMapper.findByUsername(user.getUsername());
        if (existingUser != null) {
            return false;
        }
        
        // 设置默认角色为读者
        user.setRole(User.ROLE_READER);
        
        return userMapper.add(user) > 0;
    }

    @Override
    public boolean validatePassword(Integer userId, String password) {
        if (userId == null || !StringUtils.hasText(password)) {
            return false;
        }
        
        // 获取用户信息
        User user = userMapper.findById(userId);
        if (user == null) {
            return false;
        }
        
        // 这里简化处理：直接调用login方法验证密码
        // 实际上应该有一个专门的密码验证方法
        User validatedUser = userMapper.login(user.getUsername(), password);
        return validatedUser != null;
    }
}
