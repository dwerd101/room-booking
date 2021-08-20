create view record_table_view
as
select record_table.id, employee.name, employee.surname, employee.middle_name, record_table.record, vsc_room.is_active,  vsc_room.number_room
from record_table inner join employee  on record_table.employee_id = employee.id
                  join vsc_room  on record_table.number_room_id = vsc_room.id;


create view profile_view
as
select profile.id, employee.name, employee.surname, employee.middle_name, employee.phone, employee.email, profile.banned
from employee inner join profile  on employee.profile_id = profile.id;

