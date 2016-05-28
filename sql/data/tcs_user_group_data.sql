select @capricornus_admin_id := id from tcs_user where username = 'admin';
select @capricornus_admin_group_id := id from tcs_group where name = 'capricornus-administrator';
select @capricornus_developer_group_id := id from tcs_group where name = 'capricornus-developer';
select @capricornus_user_group_id := id from tcs_group where name = 'capricornus-user';

delete from tcs_user_group where user_id = @capricornus_admin_id;

insert into tcs_user_group(user_id, group_id) values(@capricornus_admin_id, @capricornus_admin_group_id);
insert into tcs_user_group(user_id, group_id) values(@capricornus_admin_id, @capricornus_developer_group_id);
insert into tcs_user_group(user_id, group_id) values(@capricornus_admin_id, @capricornus_user_group_id);