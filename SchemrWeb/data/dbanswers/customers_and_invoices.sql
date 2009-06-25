/* ---------------------------------------------------------------------- */
/* Script generated with: DeZign for Databases v5.2.0                     */
/* Target DBMS:           PostgreSQL 8                                    */
/* Project file:          Project1.dez                                    */
/* Project name:                                                          */
/* Author:                                                                */
/* Script type:           Database creation script                        */
/* Created on:            2009-06-24 18:06                                */
/* Model version:         Version 2009-06-24                              */
/* ---------------------------------------------------------------------- */


/* ---------------------------------------------------------------------- */
/* Tables                                                                 */
/* ---------------------------------------------------------------------- */

/* ---------------------------------------------------------------------- */
/* Add table "Accounts"                                                   */
/* ---------------------------------------------------------------------- */

CREATE TABLE Accounts (
    account_id SERIAL,
    customer_id INTEGER,
    date_account_opened DATE,
    account_name TEXT,
    other_account_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Customers"                                                  */
/* ---------------------------------------------------------------------- */

CREATE TABLE Customers (
    customer_id SERIAL,
    customer_first_name TEXT,
    customer_middle_initial TEXT,
    customer_last_name TEXT,
    gender TEXT,
    email_address TEXT,
    login_name TEXT,
    login_password TEXT,
    phone_number TEXT,
    address_line_1 TEXT,
    address_line_2 TEXT,
    address_line_3 TEXT,
    address_line_4 TEXT,
    town_city TEXT,
    state_county_province TEXT,
    country TEXT,
    other_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Financial_Transactions"                                     */
/* ---------------------------------------------------------------------- */

CREATE TABLE Financial_Transactions (
    transaction_id SERIAL,
    account_id INTEGER,
    invoice_number INTEGER,
    transaction_type_code TEXT,
    transaction_date DATE,
    transaction_amount MONEY,
    transaction_comment TEXT,
    other_transaction_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Invoice_Line_Items"                                         */
/* ---------------------------------------------------------------------- */

CREATE TABLE Invoice_Line_Items (
    order_item_id INTEGER,
    invoice_number INTEGER,
    product_id INTEGER,
    product_title TEXT,
    product_quantity TEXT,
    product_price MONEY,
    derived_product_cost MONEY,
    derived_vat_payable MONEY,
    derived_total_cost MONEY,
    other_line_item_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Invoices"                                                   */
/* ---------------------------------------------------------------------- */

CREATE TABLE Invoices (
    invoice_number SERIAL,
    order_id INTEGER,
    invoice_date DATE,
    invoice_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Order_Items"                                                */
/* ---------------------------------------------------------------------- */

CREATE TABLE Order_Items (
    order_item_id SERIAL,
    order_id INTEGER,
    product_id INTEGER,
    product_quantity TEXT,
    other_order_item_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Orders"                                                     */
/* ---------------------------------------------------------------------- */

CREATE TABLE Orders (
    order_id SERIAL,
    customer_id INTEGER,
    date_order_placed DATE,
    order_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Product_Categories"                                         */
/* ---------------------------------------------------------------------- */

CREATE TABLE Product_Categories (
    production_type_code TEXT,
    product_type_description TEXT,
    vat_rating MONEY
);

/* ---------------------------------------------------------------------- */
/* Add table "Products"                                                   */
/* ---------------------------------------------------------------------- */

CREATE TABLE Products (
    product_id SERIAL,
    parent_product_id INTEGER,
    production_type_code TEXT,
    unit_price MONEY,
    product_name TEXT,
    product_color TEXT,
    product_size TEXT,
    product_description TEXT,
    other_product_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Transaction_Types"                                          */
/* ---------------------------------------------------------------------- */

CREATE TABLE Transaction_Types (
    transaction_type_code TEXT,
    transaction_type_description TEXT
);
