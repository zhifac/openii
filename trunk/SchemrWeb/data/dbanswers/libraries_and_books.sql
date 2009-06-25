/* ---------------------------------------------------------------------- */
/* Script generated with: DeZign for Databases v5.2.0                     */
/* Target DBMS:           PostgreSQL 8                                    */
/* Project file:          Project1.dez                                    */
/* Project name:                                                          */
/* Author:                                                                */
/* Script type:           Database creation script                        */
/* Created on:            2009-06-24 19:28                                */
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
/* Add table "Authors"                                                    */
/* ---------------------------------------------------------------------- */

CREATE TABLE Authors (
    author_id SERIAL,
    author_first_name TEXT,
    author_last_name TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Books"                                                      */
/* ---------------------------------------------------------------------- */

CREATE TABLE Books (
    isbn TEXT,
    book_title TEXT,
    date_of_publication DATE
);

/* ---------------------------------------------------------------------- */
/* Add table "Books_at_Libraries"                                         */
/* ---------------------------------------------------------------------- */

CREATE TABLE Books_at_Libraries (
    library_id INTEGER,
    isbn TEXT,
    quantity_in_stock INTEGER
);

/* ---------------------------------------------------------------------- */
/* Add table "Books_by_Author"                                            */
/* ---------------------------------------------------------------------- */

CREATE TABLE Books_by_Author (
    author_id INTEGER,
    isbn TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Books_by_Category"                                          */
/* ---------------------------------------------------------------------- */

CREATE TABLE Books_by_Category (
    category_code TEXT,
    isbn TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Books_Checked_out"                                          */
/* ---------------------------------------------------------------------- */

CREATE TABLE Books_Checked_out (
    member_id INTEGER,
    isbn TEXT,
    datetime_checked_out DATE
);

/* ---------------------------------------------------------------------- */
/* Add table "Categories"                                                 */
/* ---------------------------------------------------------------------- */

CREATE TABLE Categories (
    category_code TEXT,
    category_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Libraries"                                                  */
/* ---------------------------------------------------------------------- */

CREATE TABLE Libraries (
    library_id SERIAL,
    address_id INTEGER,
    library_name TEXT,
    library_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Member_Requests"                                            */
/* ---------------------------------------------------------------------- */

CREATE TABLE Member_Requests (
    request_id SERIAL,
    member_id INTEGER,
    isbn TEXT,
    date_requested DATE,
    date_located DATE,
    other_request_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Members"                                                    */
/* ---------------------------------------------------------------------- */

CREATE TABLE Members (
    member_id SERIAL,
    member_address_id INTEGER,
    gender TEXT,
    member_first_name TEXT,
    member_last_name TEXT,
    phone_number TEXT,
    email_address TEXT,
    other_member_details TEXT
);
