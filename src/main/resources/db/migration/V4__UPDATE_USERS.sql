-- noinspection SqlWithoutWhereForFile
alter table property_service.users drop column descriptor;

create table property_service.user_descriptors
(
    user_id varchar(255) not null
        constraint fk_user_descriptors_user_id
            references property_service.users,
    descriptor varchar(255) not null,
    constraint user_descriptors_pkey
        primary key (user_id, descriptor)
);

drop table property_service.user_allergies;

alter table property_service.users add column allergy varchar(255);
