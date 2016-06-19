drop table if exists tcs_category;

create table tcs_category (
	id int primary key auto_increment,
	name nvarchar(50) not null unique,
	description nvarchar(1000)
) 
engine = innodb;