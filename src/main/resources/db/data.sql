insert into department (name_department, position) VALUES ('IT', 'ADMIN');
insert into profile(is_active, login, password, role, account_non_locked ) VALUES (true, 'root','$2y$12$fXyG9.4H2U5Yby9FBKlQYOU8gqvmJIp4aYQwD2vXcF8GwjbaaEnoW','ADMIN', true);
insert into profile(is_active, login, password, role, account_non_locked) VALUES (true, 'admin','$2y$12$fXyG9.4H2U5Yby9FBKlQYOU8gqvmJIp4aYQwD2vXcF8GwjbaaEnoW','ADMIN', true);
insert into employee( middle_name, name, phone, surname, department_id, profile_id) VALUES
('fff','asdsad','3243245','asdsa',1,1);
insert into vsc_room (is_active, number_room) VALUES
(true, '404');


insert into record_table(email, is_active, record, employee_id, number_room_id) VALUES
( 'asdsad@mnas',true, '1970-01-01 00:00:01',1,1);
