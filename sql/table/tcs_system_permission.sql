drop table if exists tcs_system_permission;

create table tcs_system_permission (
	id int primary key auto_increment,
	permission_key varchar(20) not null unique,
	name nvarchar(50) not null unique,
	description nvarchar(1000)
) 
engine = innodb;