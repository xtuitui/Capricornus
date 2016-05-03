delete from tcs_user where username = 'admin';

insert into tcs_user(username, nickname, password, email, last_updated_time, login_success_times, last_login_fail_times) 
values('admin', N'admin', 'admin', '1329469914@qq.com', now(), 0, 0);

delete from tcs_user where username = 'ASNPH35';

insert into tcs_user(username, nickname, password, email, last_updated_time, login_success_times, last_login_fail_times) 
values('ASNPH35', N'Liang, Jackie-D', 'admin', '1329469914@qq.com', now(), 0, 0);