package org.example.zhangmengxia.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.zhangmengxia.dto.BorrowRecordDTO;
import org.example.zhangmengxia.entity.BorrowRecord;

import java.util.List;

@Mapper
public interface BorrowRecordMapper {
    /**
     * 根据用户ID查询借阅历史
     */
    List<BorrowRecord> findByUserId(@Param("userId") Integer userId);

    /**
     * 根据用户ID和状态查询借阅记录
     */
    List<BorrowRecord> findByUserIdAndStatus(
            @Param("userId") Integer userId,
            @Param("status") Integer status
    );

    /**
     * 查询所有借阅记录
     */
    List<BorrowRecord> findAll();
    
    /**
     * 查询所有借阅记录（包含图书和用户信息）
     */
    List<BorrowRecordDTO> findAllWithDetails();

    /**
     * 添加借阅记录
     */
    int add(BorrowRecord borrowRecord);

    /**
     * 更新借阅记录
     */
    int update(BorrowRecord borrowRecord);

    /**
     * 删除借阅记录
     */
    int delete(@Param("id") Integer id);

    /**
     * 根据ID查询借阅记录
     */
    BorrowRecord findById(@Param("id") Integer id);

    /**
     * 查询逾期借阅记录
     */
    List<BorrowRecord> findOverdueRecords();

    /**
     * 统计用户借阅数量
     */
    int countByUserId(@Param("userId") Integer userId);

    /**
     * 统计图书被借阅次数
     */
    int countByBookId(@Param("bookId") Integer bookId);
}
