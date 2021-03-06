  drop table if exists "computer";
  drop table if exists "company";
  
  SET IGNORECASE TRUE;
  
  create table "company" (
    "id"                        INT GENERATED BY DEFAULT AS IDENTITY(START WITH 1, INCREMENT BY 1) PRIMARY KEY,
    "name"                      varchar(255))
  ;
  
  create table "computer" (
    "id"                        INT GENERATED BY DEFAULT AS IDENTITY(START WITH 1, INCREMENT BY 1) PRIMARY KEY,
    "name"                      varchar(255),
    "introduced"                date NULL,
    "discontinued"              date NULL,
    "company_id"                INT default NULL)
  ;
  	
  alter table "computer" add constraint "fk_computer_company_1" foreign key ("company_id") references "company" ("id") on delete restrict on update      restrict;
  create index "ix_computer_company_1" on "computer" ("company_id");
