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
    address_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Customer_Orders"                                            */
/* ---------------------------------------------------------------------- */

CREATE TABLE Customer_Orders (
    order_id SERIAL,
    customer_id INTEGER,
    order_date DATE,
    order_status_code TEXT,
    other_order_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Customers"                                                  */
/* ---------------------------------------------------------------------- */

CREATE TABLE Customers (
    customer_id SERIAL,
    address_id INTEGER,
    payment_method_code TEXT,
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
    order_item_id SERIAL,
    order_id INTEGER,
    product_id INTEGER,
    order_quantity TEXT,
    other_item_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Products"                                                   */
/* ---------------------------------------------------------------------- */

CREATE TABLE Products (
    product_id SERIAL,
    product_type_code TEXT,
    product_name TEXT,
    product_price NUMERIC,
    other_product_details TEXT
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
/* Add table "Ref_Product_Types"                                          */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Product_Types (
    product_type_code TEXT,
    product_type_description TEXT
);
