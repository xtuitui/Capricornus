delete from tcs_system_permission where permission_key = 'SystemAdmin';

insert into tcs_system_permission(permission_key, name, description) 
values('SystemAdmin', N'Capricornus - System Administrator', N'Permission to perform all Capricornus administration functions.');



delete from tcs_system_permission where permission_key = 'Admin';

insert into tcs_system_permission(permission_key, name, description) 
values('Admin', N'Capricornus - Administrator', N'Permission to perform most Capricornus administration functions except smtp settings, ldap settings, etc.');



delete from tcs_system_permission where permission_key = 'User';

insert into tcs_system_permission(permission_key, name, description) 
values('User', N'Capricornus - User', N'Permission to log in to Capricornus.');



delete from tcs_system_permission where permission_key = 'BrowseUserGroup';

insert into tcs_system_permission(permission_key, name, description) 
values('BrowseUserGroup', N'Browse Users And Groups', N'Permission to view a list of all Capricornus user names and group names. Used for selecting users/groups in popup screens (such as the ''User Picker'').');




delete from tcs_system_permission where permission_key = 'ManageGroup';

insert into tcs_system_permission(permission_key, name, description) 
values('ManageGroup', N'Manage Group Filter Subscriptions', N'Permission to manage (create and delete) group filter subscriptions.');




delete from tcs_system_permission where permission_key = 'CreateSharedObject';

insert into tcs_system_permission(permission_key, name, description) 
values('CreateSharedObject', N'Create Shared Objects', N'Permission to share a filter or dashboard globally or with groups of users.');





delete from tcs_system_permission where permission_key = 'BulkChange';

insert into tcs_system_permission(permission_key, name, description) 
values('BulkChange', N'Bulk Change', N'Permission to execute the bulk operations within Capricornus: (Bulk Edit), (Bulk Move), (Bulk Workflow Transition), (Bulk Delete).');