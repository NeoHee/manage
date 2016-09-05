drop database if exists manage;
create database manage DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
use manage;
CREATE TABLE tb_user(
	id int primary key AUTO_INCREMENT,
	userName varchar(32),
	passWord varchar(32)
) CHARACTER SET utf8;
CREATE TABLE tb_type(
	id int primary key AUTO_INCREMENT,
	typeName varchar(32)
) CHARACTER SET utf8;
insert into tb_type(typeName) values("学生会会长"),("宣传委员"),("学习委员"),("体育委员"),("学生");
CREATE TABLE tb_student(
	code int unique not null,
	name varchar(32) ,
	remark varchar(235) ,
	state int,
	createDate date,
	endLogDate datetime,
	typeId int ,
	userId int,
	CONSTRAINT FOREIGN KEY (typeId) REFERENCES tb_type(id),
	CONSTRAINT FOREIGN KEY (userId) REFERENCES tb_user(id)
) CHARACTER SET utf8;