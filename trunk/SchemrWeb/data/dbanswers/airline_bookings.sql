/* ---------------------------------------------------------------------- */
/* Script generated with: DeZign for Databases v5.2.0                     */
/* Target DBMS:           PostgreSQL 8                                    */
/* Project file:          Project1.dez                                    */
/* Project name:                                                          */
/* Author:                                                                */
/* Script type:           Database creation script                        */
/* Created on:            2009-06-24 17:08                                */
/* Model version:         Version 2009-06-24                              */
/* ---------------------------------------------------------------------- */


/* ---------------------------------------------------------------------- */
/* Tables                                                                 */
/* ---------------------------------------------------------------------- */

/* ---------------------------------------------------------------------- */
/* Add table "Booking_Payments"                                           */
/* ---------------------------------------------------------------------- */

CREATE TABLE Booking_Payments (
    itinerary_booking_id INTEGER  NOT NULL,
    payment_id INTEGER  NOT NULL,
    CONSTRAINT PK_Booking_Payments PRIMARY KEY (itinerary_booking_id, payment_id)
);

/* ---------------------------------------------------------------------- */
/* Add table "dummy"                                                      */
/* ---------------------------------------------------------------------- */

CREATE TABLE dummy (
    ID SERIAL  NOT NULL,
    datestamp DATE,
    CONSTRAINT PK_dummy PRIMARY KEY (ID)
);

/* ---------------------------------------------------------------------- */
/* Add table "Itinerary_Bookings"                                         */
/* ---------------------------------------------------------------------- */

CREATE TABLE Itinerary_Bookings (
    itinerary_booking_id SERIAL  NOT NULL,
    passenger_id INTEGER  NOT NULL,
    booking_status_code TEXT  NOT NULL,
    ticket_type_code TEXT  NOT NULL,
    travel_class_code TEXT  NOT NULL,
    date_booking_made DATE,
    number_in_party INTEGER,
    CONSTRAINT PK_Itinerary_Bookings PRIMARY KEY (itinerary_booking_id)
);

CREATE UNIQUE INDEX Index_75F2B6D8_C69E_41A8 ON Itinerary_Bookings (itinerary_booking_id);

/* ---------------------------------------------------------------------- */
/* Add table "Itinerary_Legs"                                             */
/* ---------------------------------------------------------------------- */

CREATE TABLE Itinerary_Legs (
    itinerary_booking_id INTEGER  NOT NULL,
    leg_id INTEGER  NOT NULL
);

/* ---------------------------------------------------------------------- */
/* Add table "Legs"                                                       */
/* ---------------------------------------------------------------------- */

CREATE TABLE Legs (
    leg_id SERIAL  NOT NULL,
    aircraft_type_code TEXT,
    flight_number TEXT,
    origin_airport TEXT,
    destination_airport TEXT,
    departure_time DATE,
    arrival_time DATE,
    CONSTRAINT PK_Legs PRIMARY KEY (leg_id)
);

CREATE UNIQUE INDEX Index_729CA76C_33CD_4FE8 ON Legs (leg_id);

/* ---------------------------------------------------------------------- */
/* Add table "Passengers"                                                 */
/* ---------------------------------------------------------------------- */

CREATE TABLE Passengers (
    passenger_id SERIAL  NOT NULL,
    first_name TEXT,
    second_name TEXT,
    last_name TEXT,
    phone_number TEXT,
    email_address TEXT,
    address_lines TEXT,
    city TEXT,
    state_province_county TEXT,
    country TEXT,
    other_passenger_details TEXT,
    CONSTRAINT PK_Passengers PRIMARY KEY (passenger_id)
);

CREATE UNIQUE INDEX Index_41C5D2D6_B8B2_44B9 ON Passengers (passenger_id);

/* ---------------------------------------------------------------------- */
/* Add table "Payments"                                                   */
/* ---------------------------------------------------------------------- */

CREATE TABLE Payments (
    payment_id SERIAL  NOT NULL,
    payment_status_code TEXT  NOT NULL,
    payment_date DATE,
    payment_amount MONEY,
    CONSTRAINT PK_Payments PRIMARY KEY (payment_id)
);

CREATE UNIQUE INDEX Index_F6114DF8_7AE3_4A33 ON Payments (payment_id);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Aircraft_Types"                                         */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Aircraft_Types (
    aircraft_type_code TEXT  NOT NULL,
    aicraft_type_name TEXT,
    aicraft_type_capacity TEXT,
    CONSTRAINT PK_Ref_Aircraft_Types PRIMARY KEY (aircraft_type_code)
);

CREATE UNIQUE INDEX Index_45688914_F873_4A8A ON Ref_Aircraft_Types (aircraft_type_code);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Booking_Status"                                         */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Booking_Status (
    booking_status_code TEXT  NOT NULL,
    booking_status_description TEXT,
    CONSTRAINT PK_Ref_Booking_Status PRIMARY KEY (booking_status_code)
);

CREATE UNIQUE INDEX Index_E99E8B5F_C299_4BB6 ON Ref_Booking_Status (booking_status_code);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Payment_Status"                                         */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Payment_Status (
    payment_status_code TEXT  NOT NULL,
    payment_status_description TEXT,
    CONSTRAINT Index_18965B11_8C85_4ED3 PRIMARY KEY (payment_status_code)
);

CREATE UNIQUE INDEX Index_9A6D80F4_6696_4F53 ON Ref_Payment_Status (payment_status_code);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Ticket_Types"                                           */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Ticket_Types (
    ticket_type_code TEXT  NOT NULL,
    ticket_type_description TEXT,
    CONSTRAINT PK_Ref_Ticket_Types PRIMARY KEY (ticket_type_code)
);

CREATE UNIQUE INDEX Index_9AFCFC7E_080D_4923 ON Ref_Ticket_Types (ticket_type_code);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Travel_Classes"                                         */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Travel_Classes (
    travel_class_code TEXT  NOT NULL,
    travel_class_description TEXT,
    CONSTRAINT PK_Ref_Travel_Classes PRIMARY KEY (travel_class_code)
);

CREATE UNIQUE INDEX Index_5D143CC2_E86F_4645 ON Ref_Travel_Classes (travel_class_code);
