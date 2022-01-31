create table reports
(
    id varchar(255) not null
        constraint reports_pkey
            primary key,
    type varchar(255) not null,
    text text not null,
    reporter_id varchar(255) not null
        constraint fk77k2h9s50s0ukftb8hj497ef8
            references users,

    create_date timestamp not null,
    last_modified_date timestamp not null,
    version integer not null
);
