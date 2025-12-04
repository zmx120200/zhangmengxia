package org.example.zhangmengxia.dto;

import java.util.Date;

public class BorrowRecordDTO {
    private Integer id;
    private Integer userId;
    private Integer bookId;
    private String bookName;
    private String userName;
    private Date borrowDate;
    private Date dueDate;
    private Date returnDate;
    private String status;
    private Boolean overdue;
    
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
    
    public String getBookName() {
        return bookName;
    }
    
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public Date getBorrowDate() {
        return borrowDate;
    }
    
    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }
    
    public Date getDueDate() {
        return dueDate;
    }
    
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
    
    public Date getReturnDate() {
        return returnDate;
    }
    
    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public Boolean getOverdue() {
        return overdue;
    }
    
    public void setOverdue(Boolean overdue) {
        this.overdue = overdue;
    }
    
    @Override
    public String toString() {
        return "BorrowRecordDTO{" +
                "id=" + id +
                ", userId=" + userId +
                ", bookId=" + bookId +
                ", bookName='" + bookName + '\'' +
                ", userName='" + userName + '\'' +
                ", borrowDate=" + borrowDate +
                ", dueDate=" + dueDate +
                ", returnDate=" + returnDate +
                ", status='" + status + '\'' +
                ", overdue=" + overdue +
                '}';
    }
}
