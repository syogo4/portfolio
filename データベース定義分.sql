
DROP DATABASE IF EXISTS LiquorStoredb;
CREATE database LiquorStoredb;
use LiquorStoredb;
CREATE TABLE members(
m_id int auto_increment primary key,
m_name VARCHAR(10),
m_root varchar(10),
m_pass CHAR(5),
m_rog int
);
INSERT INTO members(m_name, m_root, m_pass, m_rog) values
('高橋', '札幌市東区','m0001',0),
('山口', '札幌市西区', 'm0002',0),
('新家', '札幌市南区', 'm0003',0),
('田中', '札幌市北区', 'm0004',0);

CREATE TABLE beers(
b_id int auto_increment primary key,
b_name VARCHAR(10),
b_price int
);
INSERT INTO beers(b_name,b_price) values
('黒ラベル', 290),
('クラシック', 320),
('スーパードライ', 300),
('一番搾り',350),
('エビス', 370);

CREATE TABLE drivers(
d_id int auto_increment primary key,
d_name VARCHAR(10),
d_root varchar(10)
);
INSERT INTO drivers(d_name,d_root) values
('東', '札幌市東区'),
('西', '札幌市西区'),
('南', '札幌市南区'),
('北', '札幌市北区');

CREATE TABLE inventory(
iv_id int auto_increment primary key,
iv_d_id int,
iv_data DATETIME,
iv_number int
);

CREATE TABLE orders(
o_id int auto_increment primary key,
o_m_id int,
o_b_id int,
o_quantity int,
o_data DATETIME
);

CREATE TABLE shipping(
s_id int auto_increment primary key,
s_b_id int,
s_price int,
s_data DATETIME,
s_quantity int,
s_expiration DATETIME
);

CREATE TABLE arrival(
a_id int auto_increment primary key,
a_b_id int,
a_price int,
a_data DATETIME,
a_quantity int,
a_expiration DATETIME
);

CREATE TABLE emplayee(
e_id int auto_increment primary key,
e_name VARCHAR(10),
e_department VARCHAR(10),
e_pass CHAR(5)
);
INSERT INTO emplayee(e_name, e_department, e_pass) VALUE 
('太郎','営業課', 's0001'),
('二郎','営業課', 's0002'),
('三郎','営業課', 's0003'),
('田中','総合事務課', 'c0001'),
('東', '配送課', 'd0001'),
('西', '配送課', 'd0002'),
('南', '配送課', 'd0003'),
('北', '配送課', 'd0004');
