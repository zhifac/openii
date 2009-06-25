/* ---------------------------------------------------------------------- */
/* Script generated with: DeZign for Databases v5.2.0                     */
/* Target DBMS:           PostgreSQL 8                                    */
/* Project file:          Project1.dez                                    */
/* Project name:                                                          */
/* Author:                                                                */
/* Script type:           Database creation script                        */
/* Created on:            2009-06-24 19:14                                */
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
    date_address_to DATE
);

/* ---------------------------------------------------------------------- */
/* Add table "Customer_Orders"                                            */
/* ---------------------------------------------------------------------- */

CREATE TABLE Customer_Orders (
    order_id SERIAL,
    customer_id INTEGER,
    order_status_code TEXT,
    order_date DATE,
    start_date DATE,
    end_date DATE,
    first_payment_date DATE,
    last_payment_date DATE,
    other_order_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Customer_Payment_Details"                                   */
/* ---------------------------------------------------------------------- */

CREATE TABLE Customer_Payment_Details (
    customer_payment_details_id SERIAL,
    customer_id INTEGER,
    payment_method_code TEXT,
    payment_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Customers"                                                  */
/* ---------------------------------------------------------------------- */

CREATE TABLE Customers (
    customer_id SERIAL,
    customer_number TEXT,
    customer_name TEXT,
    customer_address TEXT,
    customer_phone TEXT,
    customer_email TEXT,
    other_customer_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Order_Items"                                                */
/* ---------------------------------------------------------------------- */

CREATE TABLE Order_Items (
    order_item_id INTEGER,
    order_id INTEGER,
    service_id INTEGER,
    order_quantity TEXT,
    monthly_payment_date DATE,
    monthly_payment_amount NUMERIC,
    other_item_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Order_Status_Codes"                                     */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Order_Status_Codes (
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

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Period_Codes"                                           */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Period_Codes (
    period_code TEXT,
    period_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Service_Categories"                                     */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Service_Categories (
    service_category_code TEXT,
    parent_service_category_code TEXT,
    service_category_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Services"                                                   */
/* ---------------------------------------------------------------------- */

CREATE TABLE Services (
    service_id SERIAL,
    parent_service_id INTEGER,
    service_category_code TEXT,
    service_name TEXT,
    price_per_period NUMERIC,
    period_code TEXT,
    other_service_details TEXT
);
