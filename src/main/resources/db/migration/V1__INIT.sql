create table users
(
	id varchar(255) not null
		constraint users_pkey
			primary key,
	name varchar(255),
	image_url varchar(255),
	username varchar(255),
	email varchar(255),
	birthday date,
	gender varchar(255),
	descriptor varchar(255),
	drinks varchar(255),
	smokes varchar(255),
	description varchar(255),
	location varchar(255),
	budget varchar(255),
	move_in_date date,
	verified boolean not null,
	deleted boolean not null,

	create_date timestamp not null,
	last_modified_date timestamp not null,
	version integer not null
);

create table properties
(
	id varchar(255) not null
		constraint properties_pkey
			primary key,
    type varchar(255) not null,
	bathrooms_count integer,
	bedrooms_count integer,
	shared_bathroom boolean,
	shared_cooking_facilities boolean,
    availability_date date not null,
    city varchar(255),
	description varchar(300),
	furnishing boolean,
	latitude double precision,
	longitude double precision,
	lease_term varchar(255),
	main_image varchar(255),
	monthly_rent bigint not null,
	rent_currency varchar(255) not null,
	optional_address varchar(255),
	pets boolean,
	postal_code varchar(255),
	province varchar(255),
	country varchar(255),
	smoking boolean,
	square_footage varchar(255),
	street_address varchar(255),
	living_landlord boolean,
	owner_id varchar(255) not null
		constraint fk32k2h9s30s0ukftb8hj947ef2
			references users,
	verified boolean not null,
	deleted boolean not null,
	create_date timestamp not null,
	last_modified_date timestamp not null,
	version integer not null
);

create table property_amenities
(
	property_id varchar(255) not null
		constraint fkflie0u6fwgptlapkkqgpyh05
			references properties,
	amenities varchar(255) not null
);

create table property_images
(
	property_id varchar(255) not null
		constraint fkemw5i1cysiorfaxfba7tgtpiu
			references properties,
	images varchar(255) not null,
	images_order integer not null,
	constraint property_images_pkey
		primary key (property_id, images_order)
);

create table property_videos
(
	property_id varchar(255) not null
		constraint fkn7un2067gor03evs4p37eu0ln
			references properties,
	videos varchar(255) not null,
	videos_order integer not null,
	constraint property_videos_pkey
		primary key (property_id, videos_order)
);

create table users_favorites
(
	user_id varchar(255) not null
		constraint fkjkspbcfcy7pqc88p789pg50jm
			references users,
	favorites_id varchar(255) not null
		constraint fk21cjnkgpip8ionpnhwoxuqklk
			references properties,
	constraint users_favorites_pkey
		primary key (user_id, favorites_id)
);
create table property_service.user_allergies
(
    user_id varchar(255) not null
        constraint fk_user_allergies_user_id
            references property_service.users,
    allergy varchar(255) not null,
    constraint user_allergies_pkey
        primary key (user_id, allergy)
);

create table property_service.user_languages
(
    user_id varchar(255) not null
        constraint fk_user_allergies_user_id
            references property_service.users,
    language varchar(255) not null,
    constraint user_languages_pkey
        primary key (user_id, language)
);

create table property_service.user_applicable
(
    user_id varchar(255) not null
        constraint fk_user_allergies_user_id
            references property_service.users,
    applicable varchar(255) not null,
    constraint user_applicable_pkey
        primary key (user_id, applicable)
);


create table property_service.user_photos
(
    user_id varchar(255) not null
        constraint fk_user_allergies_user_id
            references property_service.users,
    photo varchar(255) not null,
    constraint user_photos_pkey
        primary key (user_id, photo)
);
