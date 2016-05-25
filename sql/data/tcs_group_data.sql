declare user_id int
declare group_id int

set user_id = id from tcs_user where username = 'admin';
print user_id;