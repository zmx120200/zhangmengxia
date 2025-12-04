package org.example.zhangmengxia.entity;

import java.util.Date;

public class User {
    private Integer id;
    private String username;      // 登录用户名
    private String password;      // 密码
    private String name;          // 真实姓名
    private Integer role;         // 角色：0-读者，1-管理员
    private String phone;         // 手机号
    private String email;         // 邮箱
    private String studentId;     // 学号/工号
    private Date createTime;      // 创建时间
    private Date updateTime;      // 更新时间

    // 角色常量
    public static final int ROLE_READER = 0;
    public static final int ROLE_ADMIN = 1;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", role=" + role +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", studentId='" + studentId + '\'' +
                '}';
    }

    // Getter和Setter方法
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    // 判断是否为管理员
    public boolean isAdmin() {
        return ROLE_ADMIN == (role != null ? role : ROLE_READER);
    }

    // 获取角色文本
    public String getRoleText() {
        if (role == null) {
            return "读者";
        }
        switch (role) {
            case ROLE_ADMIN:
                return "管理员";
            case ROLE_READER:
                return "读者";
            default:
                return "未知";
        }
    }
}
