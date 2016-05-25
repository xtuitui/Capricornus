drop table if exists tcs_group;

create table tcs_group (
	id int primary key auto_increment,
	name nvarchar(50) not null unique,
	description nvarchar(1000)
) 
engine = innodb;