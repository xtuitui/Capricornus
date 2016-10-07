drop table if exists tcs_project;

create table tcs_project (
	id int primary key auto_increment,
	name nvarchar(50) not null unique,
	project_key varchar(30) not null unique,
	category_id int,
	description nvarchar(1000),
	constraint category_foreign_key foreign key (category_id) references tcs_category (id)
) 
engine = innodb;