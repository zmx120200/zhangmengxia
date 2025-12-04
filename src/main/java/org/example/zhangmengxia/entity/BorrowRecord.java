package org.example.zhangmengxia.entity;

import java.util.Date;

public class BorrowRecord {
    private Integer id;
    private Integer userId;      // 用户ID（原readerId）
    private Integer bookId;      // 图书ID
    private Date borrowTime;     // 借阅时间
    private Date dueTime;        // 应还时间
    private Date returnTime;     // 归还时间
    private Integer status;      // 借阅状态
    private Integer renewCount;  // 续借次数
    
    // Getter和Setter方法
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getUserId() {
        return userId;
    }
    
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    
    public Integer getBookId() {
        return bookId;
    }
    
    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }
    
    public Date getBorrowTime() {
        return borrowTime;
    }
    
    public void setBorrowTime(Date borrowTime) {
        this.borrowTime = borrowTime;
    }
    
    public Date getDueTime() {
        return dueTime;
    }
    
    public void setDueTime(Date dueTime) {
        this.dueTime = dueTime;
    }
    
    public Date getReturnTime() {
        return returnTime;
    }
    
    public void setReturnTime(Date returnTime) {
        this.returnTime = returnTime;
    }
    
    public Integer getStatus() {
        return status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }
    
    public Integer getRenewCount() {
        return renewCount;
    }
    
    public void setRenewCount(Integer renewCount) {
        this.renewCount = renewCount;
    }
    
    // 状态常量
    public static final int STATUS_BORROWING = 0; // 借阅中
    public static final int STATUS_RETURNED = 1;  // 已归还
    public static final int STATUS_OVERDUE = 2;   // 已逾期
    
    // 获取状态文本
    public String getStatusText() {
        switch (status) {
            case STATUS_BORROWING:
                return "借阅中";
            case STATUS_RETURNED:
                return "已归还";
            case STATUS_OVERDUE:
                return "已逾期";
            default:
                return "未知";
        }
    }
    
    @Override
    public String toString() {
        return "BorrowRecord{" +
                "id=" + id +
                ", userId=" + userId +
                ", bookId=" + bookId +
                ", borrowTime=" + borrowTime +
                ", dueTime=" + dueTime +
                ", returnTime=" + returnTime +
                ", status=" + status +
                ", renewCount=" + renewCount +
                '}';
    }
}
