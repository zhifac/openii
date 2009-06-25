/* ---------------------------------------------------------------------- */
/* Script generated with: DeZign for Databases v5.2.0                     */
/* Target DBMS:           PostgreSQL 8                                    */
/* Project file:          Project1.dez                                    */
/* Project name:                                                          */
/* Author:                                                                */
/* Script type:           Database creation script                        */
/* Created on:            2009-06-24 19:21                                */
/* Model version:         Version 2009-06-24                              */
/* ---------------------------------------------------------------------- */


/* ---------------------------------------------------------------------- */
/* Tables                                                                 */
/* ---------------------------------------------------------------------- */

/* ---------------------------------------------------------------------- */
/* Add table "Customer_Bookings"                                          */
/* ---------------------------------------------------------------------- */

CREATE TABLE Customer_Bookings (
    booking_id SERIAL,
    customer_id INTEGER,
    event_id INTEGER,
    event_datetime DATE,
    booking_made_date DATE,
    booking_seat_count INTEGER
);

/* ---------------------------------------------------------------------- */
/* Add table "Customers"                                                  */
/* ---------------------------------------------------------------------- */

CREATE TABLE Customers (
    customer_id SERIAL,
    payment_method_code TEXT,
    customer_name TEXT,
    customer_phone TEXT,
    customer_email TEXT,
    customer_address TEXT,
    customer_payment_method_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Events"                                                     */
/* ---------------------------------------------------------------------- */

CREATE TABLE Events (
    event_id SERIAL,
    event_type_code TEXT,
    venue_id INTEGER,
    event_name TEXT,
    event_start_date DATE,
    event_end_date DATE
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Event_Types"                                            */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Event_Types (
    event_type_code TEXT,
    event_type_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Payment_Methods"                                        */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Payment_Methods (
    payment_method_code TEXT,
    payment_method_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Seat_Bookings"                                              */
/* ---------------------------------------------------------------------- */

CREATE TABLE Seat_Bookings (
    seat_booking_id SERIAL,
    booking_id INTEGER,
    seat_booking_datetime DATE,
    row_number TEXT,
    seat_number INTEGER,
    venue_id INTEGER
);

/* ---------------------------------------------------------------------- */
/* Add table "Venue_Rows"                                                 */
/* ---------------------------------------------------------------------- */

CREATE TABLE Venue_Rows (
    venue_id INTEGER,
    row_number TEXT,
    row_seat_count INTEGER
);

/* ---------------------------------------------------------------------- */
/* Add table "Venues"                                                     */
/* ---------------------------------------------------------------------- */

CREATE TABLE Venues (
    venue_id SERIAL,
    venue_name TEXT,
    venue_seat_capacity INTEGER,
    venue_address TEXT
);
