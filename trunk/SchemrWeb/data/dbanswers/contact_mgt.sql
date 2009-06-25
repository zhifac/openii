/* ---------------------------------------------------------------------- */
/* Script generated with: DeZign for Databases v5.2.0                     */
/* Target DBMS:           PostgreSQL 8                                    */
/* Project file:          Project1.dez                                    */
/* Project name:                                                          */
/* Author:                                                                */
/* Script type:           Database creation script                        */
/* Created on:            2009-06-24 18:03                                */
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
    line_1 TEXT,
    line_2 TEXT,
    line_3 TEXT,
    city TEXT,
    zip_postcode TEXT,
    state_province_county TEXT,
    country TEXT,
    other_address_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Contact_Activities"                                         */
/* ---------------------------------------------------------------------- */

CREATE TABLE Contact_Activities (
    activity_id SERIAL,
    contact_id INTEGER,
    activity_type_code TEXT,
    outcome_code TEXT,
    date_activity DATE,
    other_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Contacts"                                                   */
/* ---------------------------------------------------------------------- */

CREATE TABLE Contacts (
    contact_id SERIAL,
    customer_id INTEGER,
    status_code TEXT,
    email_address TEXT,
    web_site TEXT,
    salutation TEXT,
    contact_name TEXT,
    job_title TEXT,
    department TEXT,
    work_phone TEXT,
    cell_mobile_phone TEXT,
    fax_number TEXT,
    other_contact_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Customer_Addresses"                                         */
/* ---------------------------------------------------------------------- */

CREATE TABLE Customer_Addresses (
    customer_id INTEGER,
    address_id INTEGER,
    date_address_from DATE,
    date_address_to DATE,
    other_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Customers"                                                  */
/* ---------------------------------------------------------------------- */

CREATE TABLE Customers (
    customer_id SERIAL,
    organisation_or_person TEXT,
    organisation_name TEXT,
    first_name TEXT,
    last_name TEXT,
    date_became_customer DATE,
    other_customer_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Activity_Outcomes"                                      */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Activity_Outcomes (
    outcome_code TEXT,
    outcome_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Activity_Types"                                         */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Activity_Types (
    activity_type_code TEXT,
    activity_type_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Contact_Status"                                         */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Contact_Status (
    status_code TEXT,
    status_description TEXT
);
