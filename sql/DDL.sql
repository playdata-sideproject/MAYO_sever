drop database mayo_db;
create database mayo_db;
use mayo_db;


create table user (
idx bigint primary key auto_increment,
username varchar(100),
provider varchar(20),
provider_id varchar(100),
name varchar(20),
email varchar(50),
role varchar(10),
created_at date,
status bool default false,
reg_completed bool default false,
-- 사용자가 직접 추가할정보
phone varchar(20),
birth date,
school varchar(20)
);


create table work (
idx bigint primary key auto_increment,
user_idx bigint not null,
category varchar(20),
-- cat_idx int not null,
title varchar(100) not null,
content longtext,
created_at date not null,
-- work_image int,
status varchar(20)
);

create table work_img (
idx bigint primary key auto_increment,
work_idx bigint not null,
original_file_name varchar(100) not null,
stored_file_path varchar(100) not null,
file_size int not null
);


create table comments (
idx bigint primary key auto_increment,
work_idx bigint not null,
user_idx bigint not null,
title varchar(100) not null,
content text not null,
created_at date not null,
hidden bool default false
);

create table likes (
idx bigint primary key auto_increment,
work_idx bigint not null,
user_idx bigint not null
);

create table wish (
idx bigint primary key auto_increment,
work_idx bigint not null,
user_idx bigint not null
);


alter table work add constraint foreign key (user_idx) references user (idx);
alter table comments add constraint foreign key (work_idx) references work (idx) on delete cascade;
alter table work_img add constraint foreign key (work_idx) references work (idx) on delete cascade;

alter table likes add constraint foreign key (work_idx) references work (idx) on delete cascade;
alter table wish add constraint foreign key (work_idx) references work (idx) on delete cascade;
alter table wish add constraint foreign key (user_idx) references user (idx) on delete cascade;