-- drop table DEMO;
-- drop table SYS_ACCOUNT;

create table DEMO (
    A_ID     varchar(40) not null primary key,
    USERNAME varchar(80) not null,
    PASSWORD varchar(80) not null
);

create table RULES_RULES (
	ID varchar(40) not null primary key, 
	RULE_SET_KEY varchar(40) not null unique, 
	EFFECTIVE_DATE timestamp, 
	EXPIRES_DATE timestamp, 
	RULE text, 
	DSL text, 
	UPDATE_TIME timestamp not null, 
	UPDATE_MARK bigint not null default 0, 
	DESCRIPTION varchar(100)
);

create table RULES_FIELD_MAPPER (
	ID varchar(40) not null primary key, 
	FIELD_NAME varchar(100) not null,
	
	OBJECT_CLASS varchar(100),
	OBJECT_FIELD varchar(100),
	OBJECT_NAME varchar(100),
	
	MAPPED_CLASS varchar(100),
	MAPPED_FIELD varchar(100),
	MAPPED_NAME varchar(100),
	
	DB_TABLE varchar(100),
	DB_COULMN varchar(100),
	DB_NAME varchar(100)
);
