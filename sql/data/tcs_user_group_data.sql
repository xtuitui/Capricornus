delete from tcs_group where name = 'capricornus-administrator';

insert into tcs_group(name, description) 
values(N'capricornus-administrator', N'This is capricornus default group - capricornus-administrator.');



delete from tcs_group where name = 'capricornus-developer';

insert into tcs_group(name, description) 
values(N'capricornus-developer', N'This is capricornus default group - capricornus-developer.');



delete from tcs_group where name = 'capricornus-user';

insert into tcs_group(name, description) 
values(N'capricornus-user', N'This is capricornus default group - capricornus-user.');
