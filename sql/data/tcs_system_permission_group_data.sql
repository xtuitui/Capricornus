select @capricornus_admin_group_id := id from tcs_group where name = 'capricornus-administrator';
select @capricornus_developer_group_id := id from tcs_group where name = 'capricornus-developer';
select @capricornus_user_group_id := id from tcs_group where name = 'capricornus-user';



select @system_admin_permission_id := id from tcs_system_permission where permission_key = 'SystemAdmin';
select @admin_permission_id := id from tcs_system_permission where permission_key = 'Admin';
select @user_permission_id := id from tcs_system_permission where permission_key = 'User';
select @browse_user_group_permission_id := id from tcs_system_permission where permission_key = 'BrowseUserGroup';
select @manage_group_permission_id := id from tcs_system_permission where permission_key = 'ManageGroup';
select @create_shared_object_permission_id := id from tcs_system_permission where permission_key = 'CreateSharedObject';
select @bulk_change_permission_id := id from tcs_system_permission where permission_key = 'BulkChange';


delete from tcs_system_permission_group where group_id in (@capricornus_admin_group_id, @capricornus_developer_group_id, @capricornus_user_group_id);

--System Admin
insert into tcs_system_permission_group(system_permission_id, group_id) values(@system_admin_permission_id, @capricornus_admin_group_id);

--Admin
insert into tcs_system_permission_group(system_permission_id, group_id) values(@admin_permission_id, @capricornus_admin_group_id);

--User
insert into tcs_system_permission_group(system_permission_id, group_id) values(@user_permission_id, @capricornus_user_group_id);

--Browse User And Group
insert into tcs_system_permission_group(system_permission_id, group_id) values(@browse_user_group_permission_id, @capricornus_user_group_id);

--Manage Group
insert into tcs_system_permission_group(system_permission_id, group_id) values(@manage_group_permission_id, @capricornus_admin_group_id);

--Create Shared Object
insert into tcs_system_permission_group(system_permission_id, group_id) values(@create_shared_object_permission_id, @capricornus_user_group_id);

--BulkChange
insert into tcs_system_permission_group(system_permission_id, group_id) values(@bulk_change_permission_id, @capricornus_admin_group_id);
