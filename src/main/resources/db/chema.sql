create view record_table_view
as
select record_table.id, employee.name, employee.surname, employee.middle_name, record_table.record, vsc_room.is_active,  vsc_room.number_room
from record_table inner join employee  on record_table.employee_id = employee.id
                  join vsc_room  on record_table.number_room_id = vsc_room.id;
