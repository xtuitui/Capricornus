drop table if exists tcs_user;

create table tcs_user (
	id int primary key auto_increment,
	username varchar(20) not null unique,
	nickname nvarchar(40),
	password varchar(50) not null,
	email varchar(50) not null,
	login_success_times int,
	last_login_fail_times int,
	last_updated_time datetime not null
) 
engine = innodb;