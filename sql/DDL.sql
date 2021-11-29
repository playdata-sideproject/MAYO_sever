drop database mayo_db;
create database mayo_db;
use mayo_db;


create table user (
user_idx bigint primary key auto_increment,
username varchar(100),
provider varchar(100),
provider_id varchar(100),
name varchar(20),
email varchar(50),
role varchar(10),
<<<<<<< HEAD
joined_at date,
user_status char default 0,
=======
created_at date,
user_status bool default false,
reg_completed bool default false,
>>>>>>> 2d42fedc16c001ea6581075f1a878a05fb727f4b
-- 사용자가 직접 추가할정보
phone varchar(20),
birth date,
school varchar(20)
);

<<<<<<< HEAD
=======

>>>>>>> 2d42fedc16c001ea6581075f1a878a05fb727f4b
create table work (
work_idx bigint primary key auto_increment,
user_idx bigint not null,
category varchar(20),
work_title varchar(100) not null,
work_content longtext,
work_created_at date not null,
work_status char default 0
);

create table work_img (
work_img_idx bigint primary key auto_increment,
work_idx bigint not null,
original_file_name varchar(100) not null,
stored_file_path varchar(100) not null,
file_size int not null
);

<<<<<<< HEAD
create table category (
cat_idx int primary key auto_increment,
cat_name varchar(10)
);

create table comments (
comm_idx int primary key auto_increment,
work_idx int not null,
user_idx int not null,
=======

create table comments (
comm_idx bigint primary key auto_increment,
work_idx bigint not null,
user_idx bigint not null,
>>>>>>> 2d42fedc16c001ea6581075f1a878a05fb727f4b
comm_title varchar(100) not null,
comm_content text not null,
comm_created_at date not null
);

create table likes (
<<<<<<< HEAD
like_idx int primary key auto_increment,
work_idx int not null,
user_idx int not null
);

create table wish (
wish_idx int primary key auto_increment,
work_idx int not null,
user_idx int not null
=======
like_idx bigint primary key auto_increment,
work_idx bigint not null,
user_idx bigint not null
);

create table wish (
wish_idx bigint primary key auto_increment,
work_idx bigint not null,
user_idx bigint not null
>>>>>>> 2d42fedc16c001ea6581075f1a878a05fb727f4b
);


alter table work add constraint foreign key (user_idx) references user (user_idx);
alter table comments add constraint foreign key (work_idx) references work (work_idx) on delete cascade;
alter table work_img add constraint foreign key (work_idx) references work (work_idx) on delete cascade;

alter table likes add constraint foreign key (work_idx) references work (work_idx) on delete cascade;
alter table wish add constraint foreign key (work_idx) references work (work_idx) on delete cascade;
<<<<<<< HEAD
alter table wish add constraint foreign key (user_idx) references user (user_idx) on delete cascade;
=======
alter table wish add constraint foreign key (user_idx) references user (user_idx) on delete cascade;
>>>>>>> 2d42fedc16c001ea6581075f1a878a05fb727f4b
