create table users(
	username varchar(200) not null primary key,
	password varchar(500) not null,
	enabled boolean not null
);

create table authorities(
	username varchar(200) not null,
	authority varchar(50) not null,
	foreign key(username) references users(username)
);

create table blog_content_type(
	id int not null primary key auto_increment,
	type_name varchar(20) not null unique
);

create table archives(
	id int not null primary key auto_increment,
	times varchar(20)
);

create table blog_content(
	id int not null primary key auto_increment,
	blog_title varchar(50) not null,
	blog_content varchar(1000) not null,
	blog_create_time datetime not null DEFAULT CURRENT_TIMESTAMP,
	blog_type_id int not null,
	blog_user varchar(200) not null,
	archives_id int not null,
	foreign key(blog_type_id) references blog_content_type(id),
	foreign key(blog_user) references users(username),
	foreign key(archives_id) references archives(id)
);

create table contact(
	id int not null primary key auto_increment,
	contact_username varchar(64) not null,
	email varchar(200) not null,
	subject varchar(50) not null,
	message varchar(200) not null
);