drop database mayo_db;
create database mayo_db;
use mayo_db;


create table user (
user_idx int primary key auto_increment,
username varchar(100),
provider varchar(100),
provider_id varchar(100),
name varchar(20),
email varchar(50),
role varchar(10),
created_at date,
user_status char default 0,
-- 사용자가 직접 추가할정보
phone varchar(20),
birth date,
school varchar(20)
);


create table work (
work_idx int primary key auto_increment,
user_idx int not null,
cat_idx int not null,
work_title varchar(100) not null,
work_content longtext,
work_created_at date not null,
work_image int,
work_status char default 0
);

create table category (
cat_idx int primary key auto_increment,
cat_name varchar(10)
);

create table comments (
comm_idx int primary key auto_increment,
work_idx int not null,
user_idx int not null,
comm_title varchar(100) not null,
comm_content text not null,
comm_created_at date not null
);

create table likes (
like_idx int primary key auto_increment,
work_idx int not null,
user_idx int not null
);

create table wish (
wish_idx int primary key auto_increment,
work_idx int not null,
user_idx int not null
);


alter table work add constraint foreign key (user_idx) references user (user_idx);
alter table work add constraint foreign key (cat_idx) references category (cat_idx);
alter table comments add constraint foreign key (work_idx) references work (work_idx) on delete cascade;
alter table likes add constraint foreign key (work_idx) references work (work_idx) on delete cascade;
alter table wish add constraint foreign key (work_idx) references work (work_idx) on delete cascade;
alter table wish add constraint foreign key (user_idx) references user (user_idx) on delete cascade;