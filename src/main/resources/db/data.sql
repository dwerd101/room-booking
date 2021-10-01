insert into department (name_department, position) VALUES ('IT', 'ADMIN');
insert into profile(is_active, login, password, role, account_non_locked ) VALUES (true, 'root','$2y$12$fXyG9.4H2U5Yby9FBKlQYOU8gqvmJIp4aYQwD2vXcF8GwjbaaEnoW','ADMIN', true);
insert into profile(is_active, login, password, role, account_non_locked) VALUES (true, 'admin','$2y$12$fXyG9.4H2U5Yby9FBKlQYOU8gqvmJIp4aYQwD2vXcF8GwjbaaEnoW','ADMIN', false);
insert into employee( middle_name, name, phone, surname, department_id, profile_id, email) VALUES
('fff','asdsad','3243245','asdsa',1,1,'test@gmail.com');
insert into employee( middle_name, name, phone, surname, department_id, profile_id, email) VALUES
('Aleksandrovich','Andrey','3243245','Safronov',1,2,'test@gmail.com');
insert into vsc_room (is_active, number_room) VALUES
(true, '404');


insert into record_table(email, title, start_event, end_event, is_active, employee_id, number_room_id) VALUES
( 'asdsad@mnas', 'title', '2021-08-19 00:00:01', '2021-08-20 00:00:01', true,1,1);
insert into record_table(email, title, start_event, end_event, is_active, employee_id, number_room_id) VALUES
( 'asdsad@mnas', 'title', '2021-08-21 00:00:01', '2021-08-23 00:00:01', true,1,1);
insert into record_table(email, title, start_event, end_event, is_active, employee_id, number_room_id) VALUES
( 'asdsad@mnas', 'title', '2021-08-25 00:00:01', '2021-08-26 00:00:01', true,1,1);
insert into record_table(email, title, start_event, end_event, is_active, employee_id, number_room_id) VALUES
( 'asdsad@mnas', 'title', '2021-08-27 00:00:01', '2021-08-29 00:00:01', true,1,1);
