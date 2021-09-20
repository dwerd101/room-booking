create table profile(
                        id serial primary key ,
                        account_non_locked boolean ,
                        is_active boolean ,
                        password varchar(70),
                        login varchar(70),
                        role varchar(70)
);