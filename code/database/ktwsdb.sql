/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2018/7/18 9:47:52                            */
/*==============================================================*/
drop database if exists ktws;
create database ktws;
use ktws;


drop table if exists Classroom;

drop table if exists Course;

drop table if exists Photo;

drop table if exists User;

/*==============================================================*/
/* Table: Classroom                                             */
/*==============================================================*/
create table Classroom
(
   classroom_id         int not null,
   location             varchar(20),
   shot_interval        int,
   primary key (classroom_id)
);

/*==============================================================*/
/* Table: Course                                                */
/*==============================================================*/
create table Course
(
   course_id            int not null,
   classroom_id         int not null,
   user_id              int,
   course_name          varchar(20),
   total                int,
   time                 varchar(50),
   primary key (course_id)
);

/*==============================================================*/
/* Table: Photo                                                 */
/*==============================================================*/
create table Photo
(
   photo_id             int not null,
   course_id            int,
   url                  varchar(100) not null,
   date                 date,
   total                int,
   concentration        int,
   primary key (photo_id)
);

/*==============================================================*/
/* Table: User                                                  */
/*==============================================================*/
create table User
(
   user_id              int not null auto_increment,
   password             varchar(20),
   role                 enum('ROLE_USER', 'ROLE_ADMIN'),
   username             varchar(20),
   primary key (user_id)
);

alter table Course add constraint FK_Arrangement foreign key (classroom_id)
      references Classroom (classroom_id) on delete restrict on update restrict;

alter table Course add constraint FK_teach foreign key (user_id)
      references User (user_id) on delete restrict on update restrict;

alter table Photo add constraint FK_shot foreign key (course_id)
      references Course (course_id) on delete restrict on update restrict;

