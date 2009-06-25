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
/* Add table "Customer_Payment_Methods"                                   */
/* ---------------------------------------------------------------------- */

CREATE TABLE Customer_Payment_Methods (
    customer_id INTEGER,
    payment_method_code TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Customers"                                                  */
/* ---------------------------------------------------------------------- */

CREATE TABLE Customers (
    customer_id SERIAL,
    gender_code TEXT,
    customer_first_name TEXT,
    customer_middle_initial TEXT,
    customer_last_name TEXT,
    email_address TEXT,
    login_name TEXT,
    login_password TEXT,
    phone_number TEXT,
    address_line_1 TEXT,
    address_line_2 TEXT,
    address_line_3 TEXT,
    address_line_4 TEXT,
    town_city TEXT,
    county TEXT,
    country TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Invoices"                                                   */
/* ---------------------------------------------------------------------- */

CREATE TABLE Invoices (
    invoice_number SERIAL,
    invoice_status_code TEXT,
    invoice_date DATE,
    invoice_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Order_Items"                                                */
/* ---------------------------------------------------------------------- */

CREATE TABLE Order_Items (
    order_item_id SERIAL,
    product_id INTEGER,
    order_id INTEGER,
    order_item_status_code TEXT,
    order_item_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Orders"                                                     */
/* ---------------------------------------------------------------------- */

CREATE TABLE Orders (
    order_id SERIAL,
    customer_id INTEGER,
    order_status_code TEXT,
    date_order_placed DATE,
    order_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Products"                                                   */
/* ---------------------------------------------------------------------- */

CREATE TABLE Products (
    product_id SERIAL,
    parent_product_id INTEGER,
    product_name TEXT,
    product_price MONEY DEFAULT '0',
    product_color TEXT,
    product_size TEXT,
    product_description TEXT,
    other_product_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Gender_Codes"                                           */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Gender_Codes (
    gender_code TEXT,
    gender_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Invoice_Status_Codes"                                   */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Invoice_Status_Codes (
    invoice_status_code TEXT,
    invoice_status_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Order_Item_Status_Codes"                                */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Order_Item_Status_Codes (
    order_item_status_code TEXT,
    order_item_status_description TEXT
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
/* Add table "Shipment_Items"                                             */
/* ---------------------------------------------------------------------- */

CREATE TABLE Shipment_Items (
    shipment_id INTEGER,
    order_item_id INTEGER
);

/* ---------------------------------------------------------------------- */
/* Add table "Shipments"                                                  */
/* ---------------------------------------------------------------------- */

CREATE TABLE Shipments (
    shipment_id SERIAL,
    order_id INTEGER,
    invoice_number INTEGER,
    shipment_tracking_number TEXT,
    shipment_date DATE,
    other_shipment_details TEXT
);
