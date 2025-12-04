package org.example.zhangmengxia.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.zhangmengxia.entity.User;

import java.util.List;

@Mapper
public interface UserMapper {
    // 登录：根据用户名和密码查询用户
    User login(@Param("username") String username, @Param("password") String password);

    // 注销：更新用户登录状态
    int logout(@Param("userId") Integer userId);

    // 根据ID查询用户
    User findById(@Param("userId") Integer userId);

    // 根据用户名查找用户
    User findByUsername(@Param("username") String username);

    // 查找所有用户
    List<User> findAll();

    // 添加用户
    int add(User user);

    // 更新用户
    int update(User user);

    // 删除用户
    int delete(@Param("id") Integer id);

    // 查找所有读者（role=0）
    List<User> findAllReaders();

    // 查找所有管理员（role=1）
    List<User> findAllAdmins();
}
