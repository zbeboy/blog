create table users(
	username varchar(200) not null primary key,
	password varchar(500) not null,
	enabled boolean not null,
	code varchar(6),
	is_pass boolean default false
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

create table blog_simple_content(
	id int not null primary key auto_increment,
	blog_title varchar(50) not null,
	blog_create_time datetime not null DEFAULT CURRENT_TIMESTAMP,
	blog_type_id int not null,
	blog_user varchar(200) not null,
	archives_id int not null,
	foreign key(blog_type_id) references blog_content_type(id),
	foreign key(blog_user) references users(username),
	foreign key(archives_id) references archives(id)
);

create table blog_content(
	id int not null primary key auto_increment,
	blog_content varchar(1000) not null,
	blog_simple_content_id int not null,
	foreign key(blog_simple_content_id) references blog_simple_content(id)
);

create table contact(
	id int not null primary key auto_increment,
	contact_username varchar(64) not null,
	email varchar(200) not null,
	subject varchar(50) not null,
	message varchar(200) not null
);

INSERT INTO `blog`.`blog_content_type`(`id`,`type_name`)VALUES(1,'生活');
INSERT INTO `blog`.`blog_content_type`(`id`,`type_name`)VALUES(2,'人文');
INSERT INTO `blog`.`blog_content_type`(`id`,`type_name`)VALUES(3,'科学');
INSERT INTO `blog`.`blog_content_type`(`id`,`type_name`)VALUES(4,'风景');
INSERT INTO `blog`.`blog_content_type`(`id`,`type_name`)VALUES(5,'情感');
INSERT INTO `blog`.`blog_content_type`(`id`,`type_name`)VALUES(6,'搞笑');
INSERT INTO `blog`.`blog_content_type`(`id`,`type_name`)VALUES(7,'地理');
INSERT INTO `blog`.`blog_content_type`(`id`,`type_name`)VALUES(8,'名言');
INSERT INTO `blog`.`blog_content_type`(`id`,`type_name`)VALUES(9,'经济');
INSERT INTO `blog`.`blog_content_type`(`id`,`type_name`)VALUES(10,'哲学');
INSERT INTO `blog`.`blog_content_type`(`id`,`type_name`)VALUES(11,'游戏');