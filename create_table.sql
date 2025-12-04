create table if not exists zhangmengxia.book
(
    book_id   int         not null
    primary key,
    book_name varchar(64) null,
    author    varchar(32) null,
    isbn      varchar(32) null,
    category  varchar(32) null,
    stock     int         null,
    image_url varchar(64) null
    );

create table if not exists zhangmengxia.borrow_record
(
    id          int           not null
    primary key,
    user_id     int           null,
    book_id     int           null,
    borrow_time datetime      null,
    due_time    datetime      null,
    return_time datetime      null,
    status      int           null,
    renew_count int default 0 null
);

create index idx_borrow_record_book_id
    on zhangmengxia.borrow_record (book_id);

create index idx_borrow_record_due_time
    on zhangmengxia.borrow_record (due_time);

create index idx_borrow_record_status
    on zhangmengxia.borrow_record (status);

create index idx_borrow_record_user_id
    on zhangmengxia.borrow_record (user_id);

create table if not exists zhangmengxia.user
(
    id          int auto_increment
    primary key,
    username    varchar(64)                        null,
    name        varchar(64)                        null,
    identity    varchar(32)                        null,
    student_id  varchar(32)                        null,
    create_time datetime default CURRENT_TIMESTAMP null,
    update_time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    password    varchar(32)                        null,
    role        int      default 0                 not null,
    phone       varchar(20)                        null,
    email       varchar(32)                        null
    );

create index idx_user_role
    on zhangmengxia.user (role);

create index idx_user_username
    on zhangmengxia.user (username);

