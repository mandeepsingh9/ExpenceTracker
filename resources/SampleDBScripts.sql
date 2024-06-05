
drop table comments;
drop table expenses;
drop table main_category;
drop table sub_category;
drop table users;


create database feelathome;
use feelathome

create table users (
	id  int(8) NOT NULL AUTO_INCREMENT,
	username varchar(120) NOT NULL,
	passwd varchar(220) NOT NULL,
	name varchar(120),
    last_login timestamp,
    is_locked boolean,
    role_name varchar(10),
    inactive_sw boolean,
    created_dt datetime,
    created_by varchar(120),
	updated_dt datetime,
    updated_by varchar(120),
    comments varchar(500),
	PRIMARY KEY (id)
);


create table expenses (
	id  int(8) NOT NULL AUTO_INCREMENT,
	expense_dt date,
	income_expense varchar(220),
    descr varchar(220),
	main_category varchar(220),
    sub_category varchar(220),
    amount float,
    responsible_person varchar(120),
    inactive_sw boolean,
    created_dt datetime,
    created_by varchar(120),
	updated_dt datetime,
    updated_by varchar(120),
    comments varchar(500),
	PRIMARY KEY (id)
);


create table main_category (
	id  int(8) NOT NULL AUTO_INCREMENT,
	cat_name varchar(220),
    inactive_sw boolean,
    created_dt datetime,
    created_by varchar(120),
	updated_dt datetime,
    updated_by varchar(120),
    comments varchar(500),
	PRIMARY KEY (id)
);

create table sub_category (
	id  int(8) NOT NULL AUTO_INCREMENT,
	subcat_name varchar(220),
    maincat_id int(8),
    inactive_sw boolean,
    created_dt datetime,
    created_by varchar(120),
	updated_dt datetime,
    updated_by varchar(120),
    comments varchar(500),
	PRIMARY KEY (id)
);


create table comments (
	id  int(8) NOT NULL AUTO_INCREMENT,
	responsible_person varchar(120),
    inactive_sw boolean,
    created_dt datetime,
    created_by varchar(120),
	updated_dt datetime,
    updated_by varchar(120),
	PRIMARY KEY (id)
);

INSERT INTO comments ( responsible_person, created_dt, created_by) VALUES ( 'Cash Paid By Rajiv', now(), 'Rajiv'); 
INSERT INTO comments ( responsible_person, created_dt, created_by) VALUES ( 'Cash Paid By Rahul', now(), 'Rajiv'); 
INSERT INTO comments ( responsible_person, created_dt, created_by) VALUES ( 'Cash Paid by Dhiraj', now(), 'Rajiv'); 
INSERT INTO comments ( responsible_person, created_dt, created_by) VALUES ( 'Cash Paid by Others', now(), 'Rajiv'); 
INSERT INTO comments ( responsible_person, created_dt, created_by) VALUES ( 'Account Withdrawal', now(), 'Rajiv'); 


INSERT INTO main_category ( cat_name, created_dt, created_by, comments) VALUES ( 'Transportation', now(), 'Rajiv', 'Initial Insertion'); 
INSERT INTO main_category ( cat_name, created_dt, created_by, comments) VALUES ( 'Utilities', now(), 'Rajiv', 'Initial Insertion'); 
INSERT INTO main_category ( cat_name, created_dt, created_by, comments) VALUES ( 'Rent', now(), 'Rajiv', 'Initial Insertion'); 
INSERT INTO main_category ( cat_name, created_dt, created_by, comments) VALUES ( 'Miscellaneous', now(), 'Rajiv', 'Initial Insertion'); 
INSERT INTO main_category ( cat_name, created_dt, created_by, comments) VALUES ( 'Ration', now(), 'Rajiv', 'Initial Insertion'); 
INSERT INTO main_category ( cat_name, created_dt, created_by, comments) VALUES ( 'Staff_Salary', now(), 'Rajiv', 'Initial Insertion'); 
INSERT INTO main_category ( cat_name, created_dt, created_by, comments) VALUES ( 'Partner_Salary', now(), 'Rajiv', 'Initial Insertion'); 
INSERT INTO main_category ( cat_name, created_dt, created_by, comments) VALUES ( 'Cash_Received', now(), 'Rajiv', 'Initial Insertion'); 


INSERT INTO sub_category (SUBCAT_NAME, MAINCAT_ID, CREATED_DT, CREATED_BY ) VALUES ( 'Vegetables for Staff',5, NOW(), 'RAJIV'); 
INSERT INTO sub_category (SUBCAT_NAME, MAINCAT_ID, CREATED_DT, CREATED_BY ) VALUES ( 'Vegetables for Customers',5, NOW(), 'RAJIV'); 
INSERT INTO sub_category (SUBCAT_NAME, MAINCAT_ID, CREATED_DT, CREATED_BY ) VALUES ( 'Milk/Bread/Eggs',5, NOW(), 'RAJIV'); 
INSERT INTO sub_category (SUBCAT_NAME, MAINCAT_ID, CREATED_DT, CREATED_BY ) VALUES ( 'Food Items for Customers',5, NOW(), 'RAJIV'); 
INSERT INTO sub_category (SUBCAT_NAME, MAINCAT_ID, CREATED_DT, CREATED_BY ) VALUES ( 'Mineral Water/Beverages',5, NOW(), 'RAJIV'); 
INSERT INTO sub_category (SUBCAT_NAME, MAINCAT_ID, CREATED_DT, CREATED_BY ) VALUES ( 'Fish',5, NOW(), 'RAJIV'); 
INSERT INTO sub_category (SUBCAT_NAME, MAINCAT_ID, CREATED_DT, CREATED_BY ) VALUES ( 'Mutton',5, NOW(), 'RAJIV'); 
INSERT INTO sub_category (SUBCAT_NAME, MAINCAT_ID, CREATED_DT, CREATED_BY ) VALUES ( 'Chicken',5, NOW(), 'RAJIV'); 
INSERT INTO sub_category (SUBCAT_NAME, MAINCAT_ID, CREATED_DT, CREATED_BY ) VALUES ( 'Petrol',1, NOW(), 'RAJIV'); 
INSERT INTO sub_category (SUBCAT_NAME, MAINCAT_ID, CREATED_DT, CREATED_BY ) VALUES ( 'Toll',1, NOW(), 'RAJIV'); 
INSERT INTO sub_category (SUBCAT_NAME, MAINCAT_ID, CREATED_DT, CREATED_BY ) VALUES ( 'Taxi',1, NOW(), 'RAJIV'); 
INSERT INTO sub_category (SUBCAT_NAME, MAINCAT_ID, CREATED_DT, CREATED_BY ) VALUES ( 'Miscellaneous',1, NOW(), 'RAJIV'); 
INSERT INTO sub_category (SUBCAT_NAME, MAINCAT_ID, CREATED_DT, CREATED_BY ) VALUES ( 'Ticket (Bus/Train)',1, NOW(), 'RAJIV'); 
INSERT INTO sub_category (SUBCAT_NAME, MAINCAT_ID, CREATED_DT, CREATED_BY ) VALUES ( 'Servicing',1, NOW(), 'RAJIV'); 
INSERT INTO sub_category (SUBCAT_NAME, MAINCAT_ID, CREATED_DT, CREATED_BY ) VALUES ( 'Internet',2, NOW(), 'RAJIV'); 
INSERT INTO sub_category (SUBCAT_NAME, MAINCAT_ID, CREATED_DT, CREATED_BY ) VALUES ( 'Electricity',2, NOW(), 'RAJIV'); 
INSERT INTO sub_category (SUBCAT_NAME, MAINCAT_ID, CREATED_DT, CREATED_BY ) VALUES ( 'Telephone',2, NOW(), 'RAJIV'); 
INSERT INTO sub_category (SUBCAT_NAME, MAINCAT_ID, CREATED_DT, CREATED_BY ) VALUES ( 'Mobile',2, NOW(), 'RAJIV'); 
INSERT INTO sub_category (SUBCAT_NAME, MAINCAT_ID, CREATED_DT, CREATED_BY ) VALUES ( 'Gas',2, NOW(), 'RAJIV'); 
INSERT INTO sub_category (SUBCAT_NAME, MAINCAT_ID, CREATED_DT, CREATED_BY ) VALUES ( 'TV Recharge',2, NOW(), 'RAJIV'); 
INSERT INTO sub_category (SUBCAT_NAME, MAINCAT_ID, CREATED_DT, CREATED_BY ) VALUES ( 'Rent for 1101',3, NOW(), 'RAJIV'); 
INSERT INTO sub_category (SUBCAT_NAME, MAINCAT_ID, CREATED_DT, CREATED_BY ) VALUES ( 'Rent for 1204',3, NOW(), 'RAJIV'); 
INSERT INTO sub_category (SUBCAT_NAME, MAINCAT_ID, CREATED_DT, CREATED_BY ) VALUES ( 'Rent for 1303',3, NOW(), 'RAJIV'); 
INSERT INTO sub_category (SUBCAT_NAME, MAINCAT_ID, CREATED_DT, CREATED_BY ) VALUES ( 'Rent for 1304',3, NOW(), 'RAJIV'); 
INSERT INTO sub_category (SUBCAT_NAME, MAINCAT_ID, CREATED_DT, CREATED_BY ) VALUES ( 'Rent for 11-3',3, NOW(), 'RAJIV'); 
INSERT INTO sub_category (SUBCAT_NAME, MAINCAT_ID, CREATED_DT, CREATED_BY ) VALUES ( 'Rent for 11-4',3, NOW(), 'RAJIV'); 
INSERT INTO sub_category (SUBCAT_NAME, MAINCAT_ID, CREATED_DT, CREATED_BY ) VALUES ( 'Other',4, NOW(), 'RAJIV'); 
INSERT INTO sub_category (SUBCAT_NAME, MAINCAT_ID, CREATED_DT, CREATED_BY ) VALUES ( 'Housekeeping Items',4, NOW(), 'RAJIV'); 
INSERT INTO sub_category (SUBCAT_NAME, MAINCAT_ID, CREATED_DT, CREATED_BY ) VALUES ( 'Maintenance',4, NOW(), 'RAJIV'); 
INSERT INTO sub_category (SUBCAT_NAME, MAINCAT_ID, CREATED_DT, CREATED_BY ) VALUES ( 'CA Fees',4, NOW(), 'RAJIV'); 
INSERT INTO sub_category (SUBCAT_NAME, MAINCAT_ID, CREATED_DT, CREATED_BY ) VALUES ( 'EMI',4, NOW(), 'RAJIV'); 
INSERT INTO sub_category (SUBCAT_NAME, MAINCAT_ID, CREATED_DT, CREATED_BY ) VALUES ( 'Staff-1',6, NOW(), 'RAJIV'); 
INSERT INTO sub_category (SUBCAT_NAME, MAINCAT_ID, CREATED_DT, CREATED_BY ) VALUES ( 'Staff-2',6, NOW(), 'RAJIV'); 
INSERT INTO sub_category (SUBCAT_NAME, MAINCAT_ID, CREATED_DT, CREATED_BY ) VALUES ( 'Staff-3',6, NOW(), 'RAJIV'); 
INSERT INTO sub_category (SUBCAT_NAME, MAINCAT_ID, CREATED_DT, CREATED_BY ) VALUES ( 'Room',8, NOW(), 'RAJIV'); 
INSERT INTO sub_category (SUBCAT_NAME, MAINCAT_ID, CREATED_DT, CREATED_BY ) VALUES ( 'Food',8, NOW(), 'RAJIV'); 
INSERT INTO sub_category (SUBCAT_NAME, MAINCAT_ID, CREATED_DT, CREATED_BY ) VALUES ( 'Miscellaneous',8, NOW(), 'RAJIV'); 
INSERT INTO sub_category (SUBCAT_NAME, MAINCAT_ID, CREATED_DT, CREATED_BY ) VALUES ( 'Partner Name 1',7, NOW(), 'RAJIV'); 
INSERT INTO sub_category (SUBCAT_NAME, MAINCAT_ID, CREATED_DT, CREATED_BY ) VALUES ( 'Partner Name 2',7, NOW(), 'RAJIV'); 


INSERT INTO users ( `username`, `passwd`, `name`, `last_login`, `is_locked`, `role_name`, `inactive_sw`, `created_dt`, `created_by`, `updated_dt`, `updated_by`, `comments`) VALUES ( 'admin', 'admin', 'Admin', now(), null, 'ADMIN', null, now(), 'Rajiv', null, null, null); 
INSERT INTO users ( `username`, `passwd`, `name`, `last_login`, `is_locked`, `role_name`, `inactive_sw`, `created_dt`, `created_by`, `updated_dt`, `updated_by`, `comments`) VALUES ( 'staff', 'staff', 'Staff', now(), null, 'STAFF', null, now(), 'Rajiv', null, null, null); 



INSERT INTO expenses (expense_dt, income_expense, descr, main_category, sub_category, amount, responsible_person, created_dt, created_by, comments) VALUES ( '2021-03-23','Expense','Grocery ( For Staff+ Customer)','5','4',2950,'2',now(), 'Rajiv', 'Bulk Upload'); 
INSERT INTO expenses (expense_dt, income_expense, descr, main_category, sub_category, amount, responsible_person, created_dt, created_by, comments) VALUES ( '2021-03-24','Expense','Grocery( Customer)','5','4',1360,'2',now(), 'Rajiv', 'Bulk Upload'); 
INSERT INTO expenses (expense_dt, income_expense, descr, main_category, sub_category, amount, responsible_person, created_dt, created_by, comments) VALUES ( '2021-03-25','Expense','Grocery( Customer)','5','4',1330,'2',now(), 'Rajiv', 'Bulk Upload'); 
INSERT INTO expenses (expense_dt, income_expense, descr, main_category, sub_category, amount, responsible_person, created_dt, created_by, comments) VALUES ( '2021-03-25','Expense','TV Rechearge For Customer','2','20',650,'2',now(), 'Rajiv', 'Bulk Upload'); 
INSERT INTO expenses (expense_dt, income_expense, descr, main_category, sub_category, amount, responsible_person, created_dt, created_by, comments) VALUES ( '2021-03-25','Expense','Petrol 2 Wheeler','1','9',200,'2',now(), 'Rajiv', 'Bulk Upload'); 
INSERT INTO expenses (expense_dt, income_expense, descr, main_category, sub_category, amount, responsible_person, created_dt, created_by, comments) VALUES ( '2021-03-25','Expense','Petrol 4Wheeler','1','9',1000,'2',now(), 'Rajiv', 'Bulk Upload'); 
INSERT INTO expenses (expense_dt, income_expense, descr, main_category, sub_category, amount, responsible_person, created_dt, created_by, comments) VALUES ( '2021-08-25','Expense','Internet Installation Charges','4','27',2750,'5',now(), 'Rajiv', 'Bulk Upload'); 
INSERT INTO expenses (expense_dt, income_expense, descr, main_category, sub_category, amount, responsible_person, created_dt, created_by, comments) VALUES ( '2021-08-25','Expense','Cash','4','27',1000,'5',now(), 'Rajiv', 'Bulk Upload'); 


