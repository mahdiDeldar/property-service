alter table property_service.reports add column property_id varchar(64);
alter table property_service.reports rename column text to cause;
alter table property_service.reports add column text text;