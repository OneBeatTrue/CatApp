drop table if exists masters CASCADE;
drop table if exists cats CASCADE;
drop table if exists friendship CASCADE;

create table if not exists masters (
    id serial primary key ,
    name varchar(255) not null,
    birth_date date not null
);

create table if not exists cats (
    id serial primary key,
    name varchar(255) not null,
    birth_date date not null,
    breed varchar(255) not null,
    color varchar(255) not null,
    master_id bigint,
    foreign key (master_id) references masters(id)
);


create table if not exists friendship (
    cat_id bigint,
    friend_id bigint,
    foreign key (cat_id) references cats(id),
    foreign key (friend_id) references cats(id)
);

insert into masters (name, birth_date) values ('Ivan', '1995-11-12');
insert into masters (name, birth_date) values ('Oleg', '2005-02-15');
insert into cats (name, birth_date, breed, color, master_id) values ('Whiskers', '2023-02-15', 'Maine Coon', 'GINGER', 1);
insert into cats (name, birth_date, breed, color, master_id) values ('Mittens', '2023-02-16', 'Persian', 'WHITE', 2);
insert into cats (name, birth_date, breed, color, master_id) values ('Felix', '2023-02-17', 'Siamese', 'BLACK', 1);