delete from tcs_system_permission where permission_key = 'SystemAdmin';

insert into tcs_system_permission(permission_key, name, description) 
values('SystemAdmin', N'Capricornus - System Administrator', N'This is the largest privileges of system, you can do anything inside the system with it.');



delete from tcs_system_permission where permission_key = 'Admin';

insert into tcs_system_permission(permission_key, name, description) 
values('Admin', N'Capricornus - Administrator', N'This is the second level of system privileges, similar to the highest authority, but blocked some features, such as smtp settings, ldap settings, etc.');



delete from tcs_system_permission where permission_key = 'User';

insert into tcs_system_permission(permission_key, name, description) 
values('User', N'Capricornus - User', N'Users of the system, only to have the privilege of people are able to log on to the system.');

