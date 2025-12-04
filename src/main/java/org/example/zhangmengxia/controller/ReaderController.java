package org.example.zhangmengxia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reader")
public class ReaderController {

    // 读者列表页面（管理员用）
    @GetMapping("/list")
    public String readerListPage() {
        return "admin/userManagement"; // 使用用户管理页面，但只显示读者
    }
    
    // 添加读者页面
    @GetMapping("/add")
    public String addReaderPage() {
        return "admin/userManagement"; // 暂时返回用户管理页面，由前端JavaScript处理
    }
}
