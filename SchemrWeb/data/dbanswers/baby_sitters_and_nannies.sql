/* ---------------------------------------------------------------------- */
/* Script generated with: DeZign for Databases v5.2.0                     */
/* Target DBMS:           PostgreSQL 8                                    */
/* Project file:          Project1.dez                                    */
/* Project name:                                                          */
/* Author:                                                                */
/* Script type:           Database creation script                        */
/* Created on:            2009-06-24 17:29                                */
/* Model version:         Version 2009-06-24                              */
/* ---------------------------------------------------------------------- */


/* ---------------------------------------------------------------------- */
/* Tables                                                                 */
/* ---------------------------------------------------------------------- */

/* ---------------------------------------------------------------------- */
/* Add table "Baby_Sitters_and_Nannies"                                   */
/* ---------------------------------------------------------------------- */

CREATE TABLE Baby_Sitters_and_Nannies (
    sitter_id SERIAL,
    agency_id INTEGER,
    gender TEXT,
    first_name TEXT,
    last_name TEXT,
    hourly_charge MONEY,
    age SMALLINT,
    date_of_birth TEXT,
    address TEXT,
    cell_mobile_phone TEXT,
    home_phone TEXT,
    email_address TEXT,
    other_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Baby_Sitters_Qualifications"                                */
/* ---------------------------------------------------------------------- */

CREATE TABLE Baby_Sitters_Qualifications (
    sitter_id INTEGER,
    qualification_code TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Baby_Sitting_Sessions"                                      */
/* ---------------------------------------------------------------------- */

CREATE TABLE Baby_Sitting_Sessions (
    session_id SERIAL,
    parent_id INTEGER,
    start_datetime DATE,
    end_datetime DATE,
    other_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Children"                                                   */
/* ---------------------------------------------------------------------- */

CREATE TABLE Children (
    child_id SERIAL,
    parent_id INTEGER,
    gender TEXT,
    first_name TEXT,
    last_name TEXT,
    age SMALLINT,
    date_of_birth DATE,
    other_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Fees"                                                       */
/* ---------------------------------------------------------------------- */

CREATE TABLE Fees (
    fees_id SERIAL,
    agency_id INTEGER,
    sitter_id INTEGER,
    session_id INTEGER,
    paid_in_full_yn TEXT,
    date_payment_due DATE,
    amount_due MONEY,
    date_payment_made DATE,
    amount_paid MONEY,
    amount_outstanding MONEY,
    other_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Nanny_Agencies"                                             */
/* ---------------------------------------------------------------------- */

CREATE TABLE Nanny_Agencies (
    agency_id SERIAL,
    agency_name TEXT,
    address TEXT,
    business_phone TEXT,
    cell_mobile_phone TEXT,
    email_address TEXT,
    other_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Parents"                                                    */
/* ---------------------------------------------------------------------- */

CREATE TABLE Parents (
    parent_id SERIAL,
    parent_name TEXT,
    gender TEXT,
    parent_address TEXT,
    home_phone TEXT,
    cell_mobile_phone TEXT,
    other_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Qualifications"                                             */
/* ---------------------------------------------------------------------- */

CREATE TABLE Qualifications (
    qualification_code TEXT,
    qualification_description TEXT
);
