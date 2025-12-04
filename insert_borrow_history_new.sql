-- 插入借阅历史数据的SQL脚本（根据新表结构）
-- 用于测试借阅历史页面功能

-- 首先，确保有图书数据
-- 插入一些测试图书数据
INSERT INTO book (book_id, book_name, author, isbn, category, stock, image_url) VALUES
(1, 'Java编程思想', 'Bruce Eckel', '9787111213826', '计算机科学', 10, '/images/java.jpg'),
(2, '深入理解计算机系统', 'Randal E. Bryant', '9787111321330', '计算机科学', 8, '/images/csapp.jpg'),
(3, '算法导论', 'Thomas H. Cormen', '9787111407010', '计算机科学', 5, '/images/algorithms.jpg'),
(4, '百年孤独', '加西亚·马尔克斯', '9787544253994', '文学', 15, '/images/solitude.jpg'),
(5, '人类简史', '尤瓦尔·赫拉利', '9787508660752', '历史', 12, '/images/sapiens.jpg'),
(6, '时间简史', '史蒂芬·霍金', '9787535732309', '科普', 7, '/images/time.jpg'),
(7, '红楼梦', '曹雪芹', '9787020002207', '文学', 20, '/images/dream.jpg'),
(8, '三体', '刘慈欣', '9787536692930', '科幻', 18, '/images/threebody.jpg'),
(9, '经济学原理', '曼昆', '9787300128940', '经济学', 9, '/images/economics.jpg'),
(10, '心理学与生活', '理查德·格里格', '9787115111302', '心理学', 6, '/images/psychology.jpg')
ON DUPLICATE KEY UPDATE 
    book_name = VALUES(book_name),
    author = VALUES(author),
    isbn = VALUES(isbn),
    category = VALUES(category),
    stock = VALUES(stock),
    image_url = VALUES(image_url);

-- 确保有用户数据（读者）
-- 插入一些测试读者用户（如果不存在）
INSERT INTO user (username, password, name, role, phone, email, student_id) VALUES
('zhangsan', '123456', '张三', 0, '13800138001', 'zhangsan@example.com', '20230001'),
('lisi', '123456', '李四', 0, '13800138002', 'lisi@example.com', '20230002'),
('wangwu', '123456', '王五', 0, '13800138003', 'wangwu@example.com', '20230003'),
('zhaoliu', '123456', '赵六', 0, '13800138004', 'zhaoliu@example.com', '20230004')
ON DUPLICATE KEY UPDATE 
    username = VALUES(username),
    password = VALUES(password),
    name = VALUES(name),
    role = VALUES(role),
    phone = VALUES(phone),
    email = VALUES(email),
    student_id = VALUES(student_id);

-- 插入借阅历史数据
-- 注意：status字段：0=借阅中，1=已归还，2=已逾期

-- 张三的借阅记录
INSERT INTO borrow_record (id, user_id, book_id, borrow_time, due_time, return_time, status, renew_count) VALUES
(1, (SELECT id FROM user WHERE username = 'zhangsan'), 1, '2024-01-15 10:30:00', '2024-02-15 10:30:00', '2024-02-10 14:20:00', 1, 0),
(2, (SELECT id FROM user WHERE username = 'zhangsan'), 2, '2024-02-20 09:15:00', '2024-03-22 09:15:00', '2024-03-18 16:45:00', 1, 1),
(3, (SELECT id FROM user WHERE username = 'zhangsan'), 3, '2024-03-10 14:00:00', '2024-04-10 14:00:00', NULL, 0, 0),
(4, (SELECT id FROM user WHERE username = 'zhangsan'), 4, '2024-03-25 11:20:00', '2024-04-25 11:20:00', NULL, 0, 1),
(5, (SELECT id FROM user WHERE username = 'zhangsan'), 5, '2024-01-05 13:45:00', '2024-02-05 13:45:00', '2024-02-28 10:30:00', 2, 0);

-- 李四的借阅记录
INSERT INTO borrow_record (id, user_id, book_id, borrow_time, due_time, return_time, status, renew_count) VALUES
(6, (SELECT id FROM user WHERE username = 'lisi'), 6, '2024-02-10 15:30:00', '2024-03-12 15:30:00', '2024-03-05 09:20:00', 1, 0),
(7, (SELECT id FROM user WHERE username = 'lisi'), 7, '2024-03-01 10:00:00', '2024-04-01 10:00:00', NULL, 0, 0),
(8, (SELECT id FROM user WHERE username = 'lisi'), 8, '2024-03-15 14:45:00', '2024-04-15 14:45:00', NULL, 0, 2),
(9, (SELECT id FROM user WHERE username = 'lisi'), 9, '2024-01-20 16:20:00', '2024-02-20 16:20:00', '2024-02-15 11:10:00', 1, 0),
(10, (SELECT id FROM user WHERE username = 'lisi'), 10, '2024-02-28 09:30:00', '2024-03-30 09:30:00', '2024-04-05 15:40:00', 2, 1);

-- 王五的借阅记录
INSERT INTO borrow_record (id, user_id, book_id, borrow_time, due_time, return_time, status, renew_count) VALUES
(11, (SELECT id FROM user WHERE username = 'wangwu'), 1, '2024-03-05 13:15:00', '2024-04-05 13:15:00', NULL, 0, 0),
(12, (SELECT id FROM user WHERE username = 'wangwu'), 3, '2024-02-15 10:45:00', '2024-03-17 10:45:00', '2024-03-10 14:30:00', 1, 0),
(13, (SELECT id FROM user WHERE username = 'wangwu'), 5, '2024-03-20 11:00:00', '2024-04-20 11:00:00', NULL, 0, 1),
(14, (SELECT id FROM user WHERE username = 'wangwu'), 7, '2024-01-25 14:20:00', '2024-02-25 14:20:00', '2024-02-20 16:15:00', 1, 0),
(15, (SELECT id FROM user WHERE username = 'wangwu'), 9, '2024-02-05 09:30:00', '2024-03-07 09:30:00', '2024-03-25 10:45:00', 2, 0);

-- 赵六的借阅记录
INSERT INTO borrow_record (id, user_id, book_id, borrow_time, due_time, return_time, status, renew_count) VALUES
(16, (SELECT id FROM user WHERE username = 'zhaoliu'), 2, '2024-03-08 15:00:00', '2024-04-08 15:00:00', NULL, 0, 0),
(17, (SELECT id FROM user WHERE username = 'zhaoliu'), 4, '2024-02-22 10:30:00', '2024-03-24 10:30:00', '2024-03-18 13:20:00', 1, 1),
(18, (SELECT id FROM user WHERE username = 'zhaoliu'), 6, '2024-03-12 14:15:00', '2024-04-12 14:15:00', NULL, 0, 0),
(19, (SELECT id FROM user WHERE username = 'zhaoliu'), 8, '2024-01-30 11:45:00', '2024-03-01 11:45:00', '2024-02-25 09:30:00', 1, 0),
(20, (SELECT id FROM user WHERE username = 'zhaoliu'), 10, '2024-02-18 13:20:00', '2024-03-20 13:20:00', '2024-03-28 16:10:00', 2, 0);

-- 更新图书库存（根据借阅状态）
-- 对于借阅中的图书（status=0），减少库存
UPDATE book b
JOIN (
    SELECT book_id, COUNT(*) as borrowing_count
    FROM borrow_record 
    WHERE status = 0
    GROUP BY book_id
) br ON b.book_id = br.book_id
SET b.stock = b.stock - br.borrowing_count;

-- 验证插入的数据
SELECT '图书表记录数:' AS description, COUNT(*) AS count FROM book
UNION ALL
SELECT '用户表记录数:', COUNT(*) FROM user
UNION ALL
SELECT '借阅记录表记录数:', COUNT(*) FROM borrow_record
UNION ALL
SELECT '借阅中记录数:', COUNT(*) FROM borrow_record WHERE status = 0
UNION ALL
SELECT '已归还记录数:', COUNT(*) FROM borrow_record WHERE status = 1
UNION ALL
SELECT '已逾期记录数:', COUNT(*) FROM borrow_record WHERE status = 2;

-- 查看各用户的借阅情况
SELECT 
    u.name AS '读者姓名',
    COUNT(br.id) AS '总借阅数',
    SUM(CASE WHEN br.status = 0 THEN 1 ELSE 0 END) AS '借阅中',
    SUM(CASE WHEN br.status = 1 THEN 1 ELSE 0 END) AS '已归还',
    SUM(CASE WHEN br.status = 2 THEN 1 ELSE 0 END) AS '已逾期',
    MAX(br.borrow_time) AS '最近借阅时间'
FROM user u
LEFT JOIN borrow_record br ON u.id = br.user_id
WHERE u.role = 0
GROUP BY u.id, u.name
ORDER BY u.name;

-- 查看各图书的借阅情况
SELECT 
    b.book_name AS '图书名称',
    b.author AS '作者',
    b.category AS '分类',
    b.stock AS '当前库存',
    COUNT(br.id) AS '总借阅次数',
    SUM(CASE WHEN br.status = 0 THEN 1 ELSE 0 END) AS '当前借出'
FROM book b
LEFT JOIN borrow_record br ON b.book_id = br.book_id
GROUP BY b.book_id, b.book_name, b.author, b.category, b.stock
ORDER BY COUNT(br.id) DESC;

-- 注意：执行此脚本前，请确保数据库表结构已正确创建
-- 如果id冲突，可以删除现有数据或调整id值
-- DELETE FROM borrow_record WHERE id BETWEEN 1 AND 20;
