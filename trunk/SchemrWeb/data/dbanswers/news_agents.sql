/* ---------------------------------------------------------------------- */
/* Script generated with: DeZign for Databases v5.2.0                     */
/* Target DBMS:           PostgreSQL 8                                    */
/* Project file:          Project1.dez                                    */
/* Project name:                                                          */
/* Author:                                                                */
/* Script type:           Database creation script                        */
/* Created on:            2009-06-24 19:31                                */
/* Model version:         Version 2009-06-24                              */
/* ---------------------------------------------------------------------- */


/* ---------------------------------------------------------------------- */
/* Tables                                                                 */
/* ---------------------------------------------------------------------- */

/* ---------------------------------------------------------------------- */
/* Add table "Customer_Deliveries"                                        */
/* ---------------------------------------------------------------------- */

CREATE TABLE Customer_Deliveries (
    customer_delivery_id SERIAL,
    customer_order_id INTEGER,
    date_newspaper_delivered DATE,
    other_customer_delivery_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Customer_Holidays"                                          */
/* ---------------------------------------------------------------------- */

CREATE TABLE Customer_Holidays (
    customer_holiday_id SERIAL,
    customer_id INTEGER,
    holiday_start_date DATE,
    holiday_end_date DATE
);

/* ---------------------------------------------------------------------- */
/* Add table "Customer_Orders"                                            */
/* ---------------------------------------------------------------------- */

CREATE TABLE Customer_Orders (
    customer_order_id SERIAL,
    customer_id INTEGER,
    date_order_placed DATE,
    date_deliveries_started DATE,
    date_deliveries_ended DATE
);

/* ---------------------------------------------------------------------- */
/* Add table "Customers"                                                  */
/* ---------------------------------------------------------------------- */

CREATE TABLE Customers (
    customer_id SERIAL,
    newsagent_id INTEGER,
    delivery_round_id INTEGER,
    customer_name TEXT,
    customer_address TEXT,
    customer_phone TEXT,
    customer_email TEXT,
    other_customer_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Delivery_Rounds"                                            */
/* ---------------------------------------------------------------------- */

CREATE TABLE Delivery_Rounds (
    delivery_round_id SERIAL,
    start_street TEXT,
    end_street TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Newsagent_Suppliers"                                        */
/* ---------------------------------------------------------------------- */

CREATE TABLE Newsagent_Suppliers (
    newsagent_id INTEGER,
    supplier_code TEXT,
    supplied_from_date DATE,
    supplied_to_date DATE
);

/* ---------------------------------------------------------------------- */
/* Add table "Newsagents"                                                 */
/* ---------------------------------------------------------------------- */

CREATE TABLE Newsagents (
    newsagent_id SERIAL,
    newsagent_name TEXT,
    other_newsagent_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Newspaper_Suppliers"                                        */
/* ---------------------------------------------------------------------- */

CREATE TABLE Newspaper_Suppliers (
    supplier_code TEXT,
    newspaper_id INTEGER
);

/* ---------------------------------------------------------------------- */
/* Add table "Newspapers"                                                 */
/* ---------------------------------------------------------------------- */

CREATE TABLE Newspapers (
    newspaper_id SERIAL,
    newspaper_frequency_code TEXT,
    newspaper_name TEXT,
    newspaper_price MONEY
);

/* ---------------------------------------------------------------------- */
/* Add table "Order_Newspapers"                                           */
/* ---------------------------------------------------------------------- */

CREATE TABLE Order_Newspapers (
    newspaper_id INTEGER,
    frequency_code TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Calendar"                                               */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Calendar (
    day_date DATE,
    day_number INTEGER,
    public_holiday_yn TEXT,
    weekend_yn TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Frequencies"                                            */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Frequencies (
    frequency_code TEXT,
    frequency_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Suppliers"                                                  */
/* ---------------------------------------------------------------------- */

CREATE TABLE Suppliers (
    supplier_code TEXT,
    supplier_name TEXT
);
