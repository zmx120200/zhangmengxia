package org.example.zhangmengxia.service;

import org.example.zhangmengxia.dto.BorrowRecordDTO;
import org.example.zhangmengxia.entity.BorrowRecord;

import java.util.List;

public interface BorrowRecordService {
    // 根据用户ID查询借阅历史
    List<BorrowRecord> findByUserId(Integer userId);
    
    // 根据用户ID和状态查询借阅记录
    List<BorrowRecord> findByUserIdAndStatus(Integer userId, Integer status);
    
    // 查询所有借阅记录（管理员用）
    List<BorrowRecord> findAll();
    
    // 查询所有借阅记录（包含图书和用户信息）
    List<BorrowRecordDTO> findAllWithDetails();
    
    // 添加借阅记录
    boolean addBorrowRecord(BorrowRecord borrowRecord);
    
    // 更新借阅记录
    boolean updateBorrowRecord(BorrowRecord borrowRecord);
    
    // 删除借阅记录
    boolean deleteBorrowRecord(Integer id);
    
    // 根据ID查询借阅记录
    BorrowRecord findById(Integer id);
    
    // 查询逾期借阅记录
    List<BorrowRecord> findOverdueRecords();
    
    // 统计用户借阅数量
    int countByUserId(Integer userId);
    
    // 统计图书被借阅次数
    int countByBookId(Integer bookId);
    
    // 借书
    boolean borrowBook(Integer userId, Integer bookId, Integer days);
    
    // 还书
    boolean returnBook(Integer borrowRecordId);
    
    // 续借
    boolean renewBook(Integer borrowRecordId, Integer additionalDays);
}
