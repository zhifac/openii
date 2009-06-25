/* ---------------------------------------------------------------------- */
/* Script generated with: DeZign for Databases v5.2.0                     */
/* Target DBMS:           PostgreSQL 8                                    */
/* Project file:          Project1.dez                                    */
/* Project name:                                                          */
/* Author:                                                                */
/* Script type:           Database creation script                        */
/* Created on:            2009-06-24 19:19                                */
/* Model version:         Version 2009-06-24                              */
/* ---------------------------------------------------------------------- */


/* ---------------------------------------------------------------------- */
/* Tables                                                                 */
/* ---------------------------------------------------------------------- */

/* ---------------------------------------------------------------------- */
/* Add table "Addresses"                                                  */
/* ---------------------------------------------------------------------- */

CREATE TABLE Addresses (
    address_id SERIAL,
    line_1_number_building TEXT,
    line_2_number_street TEXT,
    line_3_area_locality TEXT,
    city TEXT,
    zip_postcode TEXT,
    state_province_county TEXT,
    country TEXT,
    other_address_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Customer_Payments"                                          */
/* ---------------------------------------------------------------------- */

CREATE TABLE Customer_Payments (
    customer_id INTEGER,
    datetime_payment DATE,
    payment_method_code TEXT,
    amount_payment NUMERIC,
    other_payment_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Customers"                                                  */
/* ---------------------------------------------------------------------- */

CREATE TABLE Customers (
    customer_id SERIAL,
    customer_address_id INTEGER,
    customer_status_code TEXT,
    date_became_customer DATE,
    date_of_birth DATE,
    first_name TEXT,
    last_name TEXT,
    amount_outstanding NUMERIC,
    email_address TEXT,
    phone_number TEXT,
    cell_mobile_phone_number TEXT,
    other_customer_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Lessons"                                                    */
/* ---------------------------------------------------------------------- */

CREATE TABLE Lessons (
    lesson_id SERIAL,
    customer_id INTEGER,
    lesson_status_code TEXT,
    staff_id INTEGER,
    vehicle_id INTEGER,
    lesson_date DATE,
    lesson_time TEXT,
    price NUMERIC,
    other_lesson_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Customer_Status"                                        */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Customer_Status (
    customer_status_code TEXT,
    customer_status_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Lesson_Status"                                          */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Lesson_Status (
    lesson_status_code TEXT,
    lesson_status_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Payment_Methods"                                        */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Payment_Methods (
    payment_method_code TEXT,
    payment_method_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Staff"                                                      */
/* ---------------------------------------------------------------------- */

CREATE TABLE Staff (
    staff_id SERIAL,
    staff_address_id INTEGER,
    nickname TEXT,
    first_name TEXT,
    middle_name TEXT,
    last_name TEXT,
    date_of_birth DATE,
    date_joined_staff DATE,
    date_left_staff DATE,
    other_staff_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Vehicles"                                                   */
/* ---------------------------------------------------------------------- */

CREATE TABLE Vehicles (
    vehicle_id SERIAL,
    vehicle_details TEXT
);
