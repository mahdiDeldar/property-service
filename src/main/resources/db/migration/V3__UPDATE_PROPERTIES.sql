-- noinspection SqlWithoutWhereForFile

alter table property_service.properties add column country_id varchar(255);
alter table property_service.properties add column province_id varchar(255);
alter table property_service.properties add column city_id varchar(255);
alter table property_service.properties add column street_address_id varchar(255);
alter table property_service.properties add column finalized boolean;
update property_service.properties set finalized = true;
alter table property_service.properties alter column finalized set not null;
