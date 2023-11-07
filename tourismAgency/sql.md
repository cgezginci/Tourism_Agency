## USER TABLE

CREATE TABLE user (

id int NOT NULL,

name varchar(255) COLLATE utf8mb4_general_ci NOT NULL,

uname varchar(255) COLLATE utf8mb4_general_ci NOT NULL,

pass varchar(255) COLLATE utf8mb4_general_ci NOT NULL,

type enum('admin','acente çalışanı') COLLATE utf8mb4_general_ci NOT NULL

)

INSERT INTO user (id, name, uname, pass, type) VALUES

(1, 'cantuğ', 'cgezginci', '1234', 'admin'),

(2, 'aytuğ', 'ayt', '1234', 'acente çalışanı');





## HOTEL TABLE

CREATE TABLE hotel (

id int NOT NULL,

hotel_name varchar(255) COLLATE utf8mb4_general_ci NOT NULL,

city varchar(255) COLLATE utf8mb4_general_ci NOT NULL,

loc varchar(255) COLLATE utf8mb4_general_ci NOT NULL,

address varchar(255) COLLATE utf8mb4_general_ci NOT NULL,

email varchar(255) COLLATE utf8mb4_general_ci NOT NULL,

number varchar(15) COLLATE utf8mb4_general_ci NOT NULL,

star int NOT NULL,

facilities text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,

hostels text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci

)

INSERT INTO hotel (id, hotel_name, city, loc, address, email, number, star, facilities, hostels) VALUES 

(1, 'Kodluyoruz Life İstanbul', 'İstanbul', 'Beyoğlu', 'Şahkulu, Şişhane Metro Durağı, Meşrutiyet Cd. No:125, 34421', 'info@kodluyoruz.org', '0212 xxx xx xx', 5, '1,6,7', '3,5');

## FACILITY TABLE

CREATE TABLE facility (

id int NOT NULL,

facility_name varchar(255) COLLATE utf8mb4_general_ci NOT NULL

)

INSERT INTO facility (id, facility_name) VALUES

(1, 'Ücretsiz Otopark'),

(2, 'Ücretsiz WiFi'),

(3, 'Yüzme Havuzu'),

(4, 'Fitness Center'),

(5, 'Hotel Concierge'),

(6, 'SPA'),

(7, '7/24 Oda Servisi');

## HOSTEL TABLE

CREATE TABLE hostel (

id int NOT NULL,

hostel_name varchar(255) COLLATE utf8mb4_general_ci NOT NULL

)

INSERT INTO hostel (id, hostel_name) VALUES

(1, 'Ultra Herşey Dahil'),

(2, 'Herşey Dahil'),

(3, 'Oda Kahvaltı'),

(4, 'Tam Pansiyon'),

(5, 'Yarım Pansiyon'),

(6, 'Sadece Yatak'),

(7, 'Alkol Hariç Full credit');

## PERIOD TABLE

CREATE TABLE period (

id int NOT NULL,

period_name varchar(255) COLLATE utf8mb4_general_ci NOT NULL,

start_date varchar(255) COLLATE utf8mb4_general_ci NOT NULL,

end_date varchar(255) COLLATE utf8mb4_general_ci NOT NULL

)

INSERT INTO period (id, period_name, start_date, end_date) VALUES

(1, 'Kış Dönemi', '2021-01-01', '2021-05-31'),

(2, 'Yaz Dönemi', '2021-06-01', '2021-12-31');

## ROOM TABLE

CREATE TABLE room (

id int NOT NULL,

hotel_id int NOT NULL,

room_type_id int NOT NULL,

stock int NOT NULL,

bed int NOT NULL,

square_meters int NOT NULL,

room_features text COLLATE utf8mb4_general_ci NOT NULL,

period_id int NOT NULL,

hostel_id int NOT NULL,

person_type varchar(255) COLLATE utf8mb4_general_ci NOT NULL,

price int NOT NULL

)

INSERT INTO room (id, hotel_id, room_type_id, stock, bed, square_meters, room_features, period_id, hostel_id, person_type, price) VALUES 

(1, 1, 1, 10, 1, 50, '1,2,3', 1, 5, 'Yetişkin', 1500);

## ROOM_TYPES TABLE

CREATE TABLE room_types (

id int NOT NULL,

name varchar(255) COLLATE utf8mb4_general_ci NOT NULL

)

INSERT INTO room_types (id, name) VALUES

(1, 'Single'),

(2, 'Double'),

(3, 'Suit');

## ROOM_FEATURES TABLE 

CREATE TABLE room_features (

id int NOT NULL,

features varchar(255) COLLATE utf8mb4_general_ci NOT NULL

)

INSERT INTO room_features (id, features) VALUES

(1, 'TV'),

(2, 'Minibar'),

(3, 'Oyun konsolu '),

(4, 'Kasa'),

(5, 'Projeksiyon');

## RESERVATION TABLE

CREATE TABLE reservation (

id int NOT NULL,

hotel_id int DEFAULT NULL,

room_id int DEFAULT NULL,

room_type_id int DEFAULT NULL,

client_name varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,

client_tc varchar(11) COLLATE utf8mb4_general_ci DEFAULT NULL,

client_number varchar(15) COLLATE utf8mb4_general_ci DEFAULT NULL,

start_Date varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,

end_date varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,

number_of_person int DEFAULT NULL

)

INSERT INTO reservation (id, hotel_id, room_id, room_type_id, client_name, client_tc, client_number, start_Date, end_date, number_of_person) VALUES 

(1, 1, 1, 1, 'Tuğba', '193..', '0507 xxx ', '2021-02-03', '2021-02-10', 2);







