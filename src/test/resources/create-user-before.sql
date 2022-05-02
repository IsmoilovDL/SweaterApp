delete from user_role;
delete from usr;
insert into ust(id, active, password, username) values
           (1, true ,'$2a$08$sHJcwSMA3bVN9glhiB.H5.mHyc2L4aonjFOogORjvmPYuVNvD4cQC', 'dru'),
           (2, true ,'$2a$08$sHJcwSMA3bVN9glhiB.H5.mHyc2L4aonjFOogORjvmPYuVNvD4cQC', 'mike');

insert into user_role(user_id, roles) values (1, 'ADMIN'), (2, 'USER');