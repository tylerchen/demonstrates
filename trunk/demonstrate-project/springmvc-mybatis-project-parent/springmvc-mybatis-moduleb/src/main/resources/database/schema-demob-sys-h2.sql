-- drop table DEMO;
-- drop table SYS_ACCOUNT;

create table DEMOB (
    A_ID     varchar(40) not null primary key,
    USERNAME varchar(80) not null,
    PASSWORD varchar(80) not null
);