/* ---------------------------------------------------------------------- */
/* Script generated with: DeZign for Databases v5.2.0                     */
/* Target DBMS:           PostgreSQL 8                                    */
/* Project file:          Project1.dez                                    */
/* Project name:                                                          */
/* Author:                                                                */
/* Script type:           Database creation script                        */
/* Created on:            2009-06-24 19:40                                */
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
/* Add table "Customer_Addresses"                                         */
/* ---------------------------------------------------------------------- */

CREATE TABLE Customer_Addresses (
    customer_id INTEGER,
    address_id INTEGER,
    date_address_from DATE,
    address_type_code TEXT,
    date_address_to DATE
);

/* ---------------------------------------------------------------------- */
/* Add table "Customers"                                                  */
/* ---------------------------------------------------------------------- */

CREATE TABLE Customers (
    customer_id SERIAL,
    customer_name TEXT,
    date_became_customer DATE,
    penalty_charge NUMERIC,
    other_customer_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Address_Types"                                          */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Address_Types (
    address_type_code TEXT,
    address_type_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Order_Status"                                           */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Order_Status (
    order_status_code TEXT,
    order_status_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Service_Company_Types"                                  */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Service_Company_Types (
    company_type_code TEXT,
    company_type_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Service_Companies"                                          */
/* ---------------------------------------------------------------------- */

CREATE TABLE Service_Companies (
    company_id SERIAL,
    company_type_code TEXT,
    company_name TEXT,
    standard_fee NUMERIC,
    other_company_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Service_Company_Orders"                                     */
/* ---------------------------------------------------------------------- */

CREATE TABLE Service_Company_Orders (
    order_id INTEGER,
    company_id INTEGER,
    order_status_code TEXT,
    order_received_date DATE,
    order_completion_date DATE,
    fee_for_order NUMERIC,
    order_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Shipment_Order"                                             */
/* ---------------------------------------------------------------------- */

CREATE TABLE Shipment_Order (
    order_id INTEGER,
    agency_id INTEGER,
    customer_id INTEGER,
    order_status_code TEXT,
    shopping_from_address_id INTEGER,
    shipping_to_address_id INTEGER,
    order_received_date DATE,
    order_completion_date DATE,
    invoice_sent_count INTEGER,
    order_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Transportation_Agency"                                      */
/* ---------------------------------------------------------------------- */

CREATE TABLE Transportation_Agency (
    agency_id SERIAL,
    agency_name TEXT,
    other_agency_details TEXT
);
