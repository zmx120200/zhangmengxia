package org.example.zhangmengxia.service.impl;

import org.example.zhangmengxia.dto.BorrowRecordDTO;
import org.example.zhangmengxia.entity.BorrowRecord;
import org.example.zhangmengxia.mapper.BorrowRecordMapper;
import org.example.zhangmengxia.service.BorrowRecordService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class BorrowRecordServiceImpl implements BorrowRecordService {

    @Resource
    private BorrowRecordMapper borrowRecordMapper;

    @Override
    public List<BorrowRecord> findByUserId(Integer userId) {
        if (userId == null) {
            return java.util.Collections.emptyList();
        }
        return borrowRecordMapper.findByUserId(userId);
    }

    @Override
    public List<BorrowRecord> findByUserIdAndStatus(Integer userId, Integer status) {
        if (userId == null || status == null) {
            return java.util.Collections.emptyList();
        }
        return borrowRecordMapper.findByUserIdAndStatus(userId, status);
    }

    @Override
    public List<BorrowRecord> findAll() {
        return borrowRecordMapper.findAll();
    }
    
    @Override
    public List<BorrowRecordDTO> findAllWithDetails() {
        return borrowRecordMapper.findAllWithDetails();
    }

    @Override
    public boolean addBorrowRecord(BorrowRecord borrowRecord) {
        if (borrowRecord == null || borrowRecord.getUserId() == null || borrowRecord.getBookId() == null) {
            return false;
        }
        
        // 设置默认值
        if (borrowRecord.getBorrowTime() == null) {
            borrowRecord.setBorrowTime(new Date());
        }
        
        if (borrowRecord.getDueTime() == null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(borrowRecord.getBorrowTime());
            calendar.add(Calendar.DAY_OF_MONTH, 30); // 默认借阅30天
            borrowRecord.setDueTime(calendar.getTime());
        }
        
        if (borrowRecord.getStatus() == null) {
            borrowRecord.setStatus(BorrowRecord.STATUS_BORROWING);
        }
        
        if (borrowRecord.getRenewCount() == null) {
            borrowRecord.setRenewCount(0);
        }
        
        return borrowRecordMapper.add(borrowRecord) > 0;
    }

    @Override
    public boolean updateBorrowRecord(BorrowRecord borrowRecord) {
        if (borrowRecord == null || borrowRecord.getId() == null) {
            return false;
        }
        
        BorrowRecord existingRecord = borrowRecordMapper.findById(borrowRecord.getId());
        if (existingRecord == null) {
            return false;
        }
        
        return borrowRecordMapper.update(borrowRecord) > 0;
    }

    @Override
    public boolean deleteBorrowRecord(Integer id) {
        if (id == null) {
            return false;
        }
        
        BorrowRecord existingRecord = borrowRecordMapper.findById(id);
        if (existingRecord == null) {
            return false;
        }
        
        return borrowRecordMapper.delete(id) > 0;
    }

    @Override
    public BorrowRecord findById(Integer id) {
        if (id == null) {
            return null;
        }
        return borrowRecordMapper.findById(id);
    }

    @Override
    public List<BorrowRecord> findOverdueRecords() {
        return borrowRecordMapper.findOverdueRecords();
    }

    @Override
    public int countByUserId(Integer userId) {
        if (userId == null) {
            return 0;
        }
        return borrowRecordMapper.countByUserId(userId);
    }

    @Override
    public int countByBookId(Integer bookId) {
        if (bookId == null) {
            return 0;
        }
        return borrowRecordMapper.countByBookId(bookId);
    }

    @Override
    public boolean borrowBook(Integer userId, Integer bookId, Integer days) {
        if (userId == null || bookId == null) {
            return false;
        }
        
        if (days == null || days <= 0) {
            days = 30; // 默认30天
        }
        
        BorrowRecord borrowRecord = new BorrowRecord();
        borrowRecord.setUserId(userId);
        borrowRecord.setBookId(bookId);
        borrowRecord.setBorrowTime(new Date());
        
        // 计算应还时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(borrowRecord.getBorrowTime());
        calendar.add(Calendar.DAY_OF_MONTH, days);
        borrowRecord.setDueTime(calendar.getTime());
        
        borrowRecord.setStatus(BorrowRecord.STATUS_BORROWING);
        borrowRecord.setRenewCount(0);
        
        return addBorrowRecord(borrowRecord);
    }

    @Override
    public boolean returnBook(Integer borrowRecordId) {
        if (borrowRecordId == null) {
            return false;
        }
        
        BorrowRecord borrowRecord = borrowRecordMapper.findById(borrowRecordId);
        if (borrowRecord == null) {
            return false;
        }
        
        // 更新状态为已归还
        borrowRecord.setStatus(BorrowRecord.STATUS_RETURNED);
        borrowRecord.setReturnTime(new Date());
        
        return borrowRecordMapper.update(borrowRecord) > 0;
    }

    @Override
    public boolean renewBook(Integer borrowRecordId, Integer additionalDays) {
        if (borrowRecordId == null) {
            return false;
        }
        
        if (additionalDays == null || additionalDays <= 0) {
            additionalDays = 30; // 默认续借30天
        }
        
        BorrowRecord borrowRecord = borrowRecordMapper.findById(borrowRecordId);
        if (borrowRecord == null) {
            return false;
        }
        
        // 检查是否可以续借（例如：最大续借次数限制）
        if (borrowRecord.getRenewCount() != null && borrowRecord.getRenewCount() >= 2) {
            return false; // 最多续借2次
        }
        
        // 更新应还时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(borrowRecord.getDueTime());
        calendar.add(Calendar.DAY_OF_MONTH, additionalDays);
        borrowRecord.setDueTime(calendar.getTime());
        
        // 增加续借次数
        borrowRecord.setRenewCount((borrowRecord.getRenewCount() != null ? borrowRecord.getRenewCount() : 0) + 1);
        
        return borrowRecordMapper.update(borrowRecord) > 0;
    }
}
