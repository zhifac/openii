/* ---------------------------------------------------------------------- */
/* Script generated with: DeZign for Databases v5.2.0                     */
/* Target DBMS:           PostgreSQL 8                                    */
/* Project file:          Project1.dez                                    */
/* Project name:                                                          */
/* Author:                                                                */
/* Script type:           Database creation script                        */
/* Created on:            2009-06-24 17:13                                */
/* Model version:         Version 2009-06-24                              */
/* ---------------------------------------------------------------------- */


/* ---------------------------------------------------------------------- */
/* Tables                                                                 */
/* ---------------------------------------------------------------------- */

/* ---------------------------------------------------------------------- */
/* Add table "Apartment_Bookings"                                         */
/* ---------------------------------------------------------------------- */

CREATE TABLE Apartment_Bookings (
    apt_booking_id SERIAL,
    apt_id INTEGER,
    guest_id INTEGER,
    booking_status_code TEXT,
    booking_start_date DATE,
    booking_end_date DATE,
    other_booking_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Apartment_Buildings"                                        */
/* ---------------------------------------------------------------------- */

CREATE TABLE Apartment_Buildings (
    building_id SERIAL,
    building_short_name TEXT,
    building_full_name TEXT,
    building_description TEXT,
    building_address TEXT,
    building_manager TEXT,
    building_phone TEXT,
    other_building_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Apartment_Facilities"                                       */
/* ---------------------------------------------------------------------- */

CREATE TABLE Apartment_Facilities (
    apt_id INTEGER,
    facility_code TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Apartments"                                                 */
/* ---------------------------------------------------------------------- */

CREATE TABLE Apartments (
    apt_id SERIAL,
    building_id INTEGER,
    apt_type_code TEXT,
    apt_number TEXT,
    bathroom_count TEXT,
    bedroom_count INTEGER,
    room_count TEXT,
    other_apartment_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Guests"                                                     */
/* ---------------------------------------------------------------------- */

CREATE TABLE Guests (
    guest_id SERIAL,
    gender_code TEXT,
    guest_first_name TEXT,
    guest_last_name TEXT,
    date_of_birth DATE,
    other_guest_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Apartment_Faciltiies"                                   */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Apartment_Faciltiies (
    facility_code TEXT,
    facility_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Apartment_Types"                                        */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Apartment_Types (
    apt_type_code TEXT,
    apt_type_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Booking_Status"                                         */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Booking_Status (
    booking_status_code TEXT,
    booking_status_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Gender"                                                 */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Gender (
    gender_code TEXT,
    gender_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "View_Unit_Status"                                           */
/* ---------------------------------------------------------------------- */

CREATE TABLE View_Unit_Status (
    apt_id INTEGER,
    status_date DATE,
    available_yn TEXT,
    apt_booking_id INTEGER
);
