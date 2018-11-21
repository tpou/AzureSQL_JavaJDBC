drop table Acc_repair;
drop table Acc_produce;
drop table Purchase;
drop table Repair_comp;
drop table Repair;
drop table Accident;
drop table Complaint;
drop table Customer;
drop table p1_account;
drop table p2_account;
drop table p3_account;
drop table Product1;
drop table Product2;
drop table Product3;
drop table Product;
drop table Worker;
drop table QualityController;
drop table TechnicalStaff;
drop table Employee;
--. create tables:

create table Employee(
eName varchar(32), e_address varchar(32),
primary key(eName));

create table Worker(
eName varchar(32), max_prod_day int,
primary key(eName),
foreign key(eName) references Employee);

create table QualityController(
eName varchar(32), product_type varchar(32),
primary key(eName),
foreign key(eName) references Employee);

create table TechnicalStaff(
eName varchar(32), position varchar(32), degree varchar(32),
primary key(eName),
foreign key(eName) references Employee);

create table Product(
pID int, size varchar(16), prod_date date, p_time int, w_name varchar(32), con_name varchar(32),
primary key(pID),
foreign key(w_name) references Worker,
foreign key(con_name) references QualityController);

create table Product1(
pID int, software varchar(32),
primary key(pID),
foreign key(pID) references Product);

Create table Product2(
pID int, color varchar(32),
primary key(pID),
foreign key(pID) references Product);

Create table Product3(
pID int, weight int,
primary key(pID),
foreign key(pID) references Product);

Create table p1_account(
acc_number int, date_created date, cost int, pID int,
primary key(acc_number),
foreign key(pID) references Product1);

Create table p2_account(
acc_number int, date_created date, cost int, pID int,
primary key(acc_number),
foreign key(pID) references Product2);

Create table p3_account(
acc_number int, date_created date, cost int, pID int,
primary key(acc_number),
foreign key(pID) references Product3);

Create table Customer(
c_name varchar(32), c_address varchar(32),
primary key(c_name));

Create table Complaint(
c_id int, pID int, c_name varchar(32), c_date date, c_descript varchar(32), c_treatment varchar(32),
primary key(c_id),
foreign key(pID) references Product,
foreign key(c_name) references Customer);

Create table Accident(
accident_num int, accident_date date, acc_lost_days int,
primary key(accident_num));

Create table Repair(
pID int, r_date date, eName varchar(32),
primary key(pID,r_date),
foreign key(pID) references Product,
foreign key(eName) references TechnicalStaff);

Create table Repair_comp(
c_id int, pID int, r_date date,
primary key(c_id),
foreign key(c_id) references Complaint,
foreign key(pID,r_date) references Repair);

Create table Purchase(
pID int, c_name varchar(32),
primary key(pID),
foreign key(pID) references Product,
foreign key(c_name) references Customer);

Create table Acc_repair(
accident_num int, pID int, r_date date,
primary key(accident_num),
foreign key(accident_num) references Accident,
foreign key(pID,r_date) references Repair);

Create table Acc_produce(
accident_num int, pID int,
primary key(accident_num),
foreign key(accident_num) references Accident,
foreign key(pID) references Product);

-- create index for table:

--- index of attribute w_name on Product table
create index prod_worker_idx on Product(w_name);

--- index of attribute con_name on Product table
create index prod_control_idx on Product(con_name);

--- index of attribute prod_date on Product table
create index prod_date_idx on Product(prod_date);

--- index of attribute color on Product2 table
create index prod2_col_idx on Product2(color);

--- index of attribute pID on Complaint table
create index compl_pID_idx on Complaint(pID);

--- index of attribute accident_date on Accident table
create index acc_accdate_idx on Accident(accident_date);

--- index of attribute c_name on Purchase table
create index purch_cname_idx on Purchase(c_name);

--- index of attribute pID on Acc_repair table
create index accrp_pID_idx on Acc_repair(pID);


----  TRANSACT-SQL -----GO HERE-----------

CREATE PROCEDURE Query1
@eName varchar(32),
@e_address varchar(32)
AS
    BEGIN 
        insert into Employee values (@eName,@e_address);
    END


CREATE PROCEDURE Query1a
@eName varchar(32),
@max_prod_day int 
AS 
    BEGIN
        insert into Worker values (@eName,@max_prod_day); 
    END 

GO
CREATE PROCEDURE Query1b
@eName varchar(32),
@product_type varchar(32)
AS
    BEGIN
        insert into QualityController values (@eName,@product_type);
    END

GO
CREATE PROCEDURE Query1c
@eName varchar(32),
@position varchar(32),
@degree varchar(32)
AS
    BEGIN
        insert into TechnicalStaff values (@eName,@position,@degree);
    END 

GO 
CREATE PROCEDURE Query2
@pID int,
@size varchar(32),
@prod_date char(10),
@p_time int,
@w_name varchar(32),
@con_name varchar(32)
AS
    BEGIN 
            insert into Product values (@pID,@size,(select convert(DATETIME,@prod_date,102)),@p_time,@w_name,@con_name);
    END



GO
CREATE PROCEDURE Query2a
@pID int,
@software varchar(32)
AS 
    BEGIN
        insert into Product1 values (@pID,@software); 
    END

GO
CREATE PROCEDURE Query2b
@pID int,
@color varchar(32)
AS 
    BEGIN
        insert into Product2 values (@pID,@color); 
    END 

GO 
CREATE PROCEDURE Query2c 
@pID int,
@weight varchar(32)
AS 
    BEGIN
        insert into Product3 values (@pID,@weight); 
    END 

GO 
CREATE PROCEDURE   Query2d
@pID int,
@r_date char(10),
@eName varchar(32)
AS 
    BEGIN
        insert into Repair values (@pID,(select convert(DATETIME,@r_date,102)),@eName); 
    END 

GO 
CREATE PROCEDURE Query3 
@c_name varchar(32),
@c_address varchar(32)
AS 
    BEGIN
        insert into Customer values (@c_name,@c_address); 
    END 

GO 
CREATE PROCEDURE Query3a 
@pID int,
@c_name varchar(32)
AS 
    BEGIN 
        insert into Purchase values (@pID,@c_name);
    END 

GO 
CREATE PROCEDURE Query41 
@acc_number int,
@date_created char(10),
@cost int,
@pID int
AS 
    BEGIN
        insert into p1_account values (@acc_number,(select convert(DATETIME,@date_created,102)),@cost,@pID); 
    END 

GO 
CREATE PROCEDURE Query42 
@acc_number int,
@date_created char(10),
@cost int,
@pID int
AS 
    BEGIN
        insert into p2_account values (@acc_number,(select convert(DATETIME,@date_created,102)),@cost,@pID); 
    END 

GO 
CREATE PROCEDURE Query43 
@acc_number int,
@date_created char(10),
@cost int,
@pID int
AS 
    BEGIN
        insert into p3_account values (@acc_number,(select convert(DATETIME,@date_created,102)),@cost,@pID); 
    END 

GO 
CREATE PROCEDURE Query5
@c_id int,
@pID int,
@c_name varchar(32),
@c_date char(10),
@c_descript varchar(32),
@c_treatment varchar(32)
AS 
    BEGIN
        insert into Complaint values (@c_id,@pID,@c_name,(select convert(DATETIME,@c_date,102)),@c_descript,@c_treatment); 
    END 

GO 
CREATE PROCEDURE   Query5a
@c_id int,
@pID int,
@r_date char(10)
AS 
    BEGIN
        insert into Repair_comp values (@c_id,@pID,(select convert(DATETIME,@r_date,102))); 
    END 

GO 
CREATE PROCEDURE Query6 
@accident_num int,
@accident_date char(10),
@acc_lost_days int
AS 
    BEGIN
        insert into Accident values (@accident_num,(select convert(DATETIME,@accident_date,102)),@acc_lost_days); 
    END 

GO 
CREATE PROCEDURE Query6a 
@accident_num int,
@pID int
AS 
    BEGIN 
        insert into Acc_produce values (@accident_num,@pID);
    END 

GO 
CREATE PROCEDURE Query6b
@accident_num int,
@pID int,
@r_date char(10)
AS 
    BEGIN
        insert into Acc_repair values (@accident_num,@pID,(select convert(DATETIME,@r_date,102))); 
    END 

GO 
CREATE PROCEDURE Query7
@pID int
AS 
    BEGIN 
        select * from Product where pID = @pID;
    END 

GO 
CREATE PROCEDURE Query8 
@w_name varchar(32)
AS 
    BEGIN
        select * from Product where w_name = @w_name; 
    END 

GO
CREATE PROCEDURE Query9
@con_name varchar(32)
AS 
    BEGIN
        select count(c_id) from Complaint where pID in (select pID from Product where con_name=@con_name);  
    END 

GO 
CREATE PROCEDURE Query10
@con_name varchar(32)
AS 
BEGIN 
    select sum(cost) from p3_account where pID in (select pID from Repair where pID in (select pID from Product where con_name = @con_name));
END 

GO 
CREATE PROCEDURE Query11
@color varchar(32)
AS 
BEGIN
    select c_name from Customer where c_name in (select c_name from Purchase where pID in (select pID from Product2 where color=@color));
END 

GO
CREATE PROCEDURE Query12
AS 
BEGIN
    select sum(acc_lost_days) from Accident where accident_num in (select accident_num from Acc_repair where pID in (select pID from Repair_comp));
END 

GO 
CREATE PROCEDURE Query13
AS 
BEGIN
    select c_name from Customer where c_name in (select eName from Worker);
END 

GO
CREATE PROCEDURE Query14
AS 
BEGIN
    select c_name from Customer where c_name in 
        (select c_name from Purchase where ((c_name in 
            (select con_name from Product) or c_name in
            (select w_name from Product)) and pID in 
            (select pID from Product)) or (c_name in 
            (select eName from Repair) and pID in (select pID from Repair))); 
END 

GO 
CREATE PROCEDURE Query15
@year int
AS 
BEGIN
    select avg(cost)
    from
    (select pID,cost from p1_account
    union
    select pID,cost from p2_account
    union 
    select pID,cost from p3_account) as avg_cost
    where pID in (select pID from Product where YEAR(prod_date)=@year);
END 

GO 
CREATE PROCEDURE Query16a
@t_name varchar(32)
AS 
BEGIN 
    select position, degree from TechnicalStaff where eName = @t_name;
END 

GO 
CREATE PROCEDURE Query16b
@con_name varchar(32)
AS 
BEGIN 
    select product_type from QualityController where eName = @con_name;
END

GO 
CREATE PROCEDURE Query16c
@con_name varchar(32),
@t_name varchar(32)
AS 
BEGIN 
    update Product set con_name = @t_name where con_name = @con_name;
END

GO 
CREATE PROCEDURE Query16d
@con_name varchar(32),
@t_name varchar(32)
AS 
BEGIN 
    update Repair set eName = @con_name where eName = @t_name;
END

GO 
CREATE PROCEDURE Query16e
@con_name varchar(32),
@t_name varchar(32)
AS 
BEGIN 
    delete from QualityController where eName = @con_name
    delete from TechnicalStaff where eName = @t_name;

END 

GO
CREATE PROCEDURE Query17
@date1 varchar(10),
@date2 varchar(10)
AS 
BEGIN 
    delete from Acc_produce 
    where accident_num in (select accident_num from Accident where accident_date between (select convert(DATETIME,@date1,102)) and (select convert(DATETIME,@date2,102)))
    delete from Acc_repair 
    where accident_num in (select accident_num from Accident where accident_date between (select convert(DATETIME,@date1,102)) and (select convert(DATETIME,@date2,102)))
    delete from Accident 
    where accident_date between (select convert(DATETIME,@date1,102)) and (select convert(DATETIME,@date2,102))

END 

GO
CREATE PROCEDURE Query19
AS 
BEGIN
   select c_name,c_address from Customer order by c_name;
END 
drop PROCEDURE Query19


select * from Product