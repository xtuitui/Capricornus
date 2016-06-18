drop table if exists tcs_system_permission_group;

create table tcs_system_permission_group (
	id int primary key auto_increment,
	system_permission_id int not null,
	group_id int not null,
	constraint system_permission_group unique (system_permission_id, group_id),
	constraint system_permission_foreign_key foreign key (system_permission_id) references tcs_system_permission (id),
	constraint permission_group_foreign_key foreign key (group_id) references tcs_group (id)
) 
engine = innodb;