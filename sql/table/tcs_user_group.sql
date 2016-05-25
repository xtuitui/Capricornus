drop table if exists tcs_user_group;

create table tcs_user_group (
	id int primary key auto_increment,
	user_id int not null,
	group_id int not null,
	constraint user_group unique (user_id, group_id),
	constraint user_foreign_key foreign key (user_id) references tcs_user (id),
	constraint group_foreign_key foreign key (group_id) references tcs_group (id)
) 
engine = innodb;