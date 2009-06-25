/* ---------------------------------------------------------------------- */
/* Script generated with: DeZign for Databases v5.2.0                     */
/* Target DBMS:           PostgreSQL 8                                    */
/* Project file:          Project1.dez                                    */
/* Project name:                                                          */
/* Author:                                                                */
/* Script type:           Database creation script                        */
/* Created on:            2009-06-24 18:05                                */
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
/* Add table "Customer_Contact_Channels"                                  */
/* ---------------------------------------------------------------------- */

CREATE TABLE Customer_Contact_Channels (
    customer_id INTEGER,
    channel_code TEXT,
    active_from_date DATE,
    active_to_date DATE,
    contact_number TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Customer_Orders"                                            */
/* ---------------------------------------------------------------------- */

CREATE TABLE Customer_Orders (
    order_id SERIAL,
    customer_id INTEGER,
    order_status_code TEXT,
    order_date DATE,
    order_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Customers"                                                  */
/* ---------------------------------------------------------------------- */

CREATE TABLE Customers (
    customer_id SERIAL,
    payment_method_code TEXT,
    customer_name TEXT,
    date_became_customer DATE,
    other_customer_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Order_Items"                                                */
/* ---------------------------------------------------------------------- */

CREATE TABLE Order_Items (
    order_id INTEGER,
    product_id INTEGER,
    order_quantity TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Products"                                                   */
/* ---------------------------------------------------------------------- */

CREATE TABLE Products (
    product_id SERIAL,
    product_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Address_Types"                                          */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Address_Types (
    address_type_code TEXT,
    address_type_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Contact_Channels"                                       */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Contact_Channels (
    channel_code TEXT,
    channel_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Order_Status"                                           */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Order_Status (
    order_status_code TEXT,
    order_status_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Payment_Methods"                                        */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Payment_Methods (
    payment_method_code TEXT,
    payment_method_description TEXT
);
