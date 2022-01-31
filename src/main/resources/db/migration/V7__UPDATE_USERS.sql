-- noinspection SqlWithoutWhereForFile

alter table property_service.users add column hidden bool;
update property_service.users set hidden = false;
alter table property_service.users alter column hidden set not null;
