/* ---------------------------------------------------------------------- */
/* Script generated with: DeZign for Databases v5.2.0                     */
/* Target DBMS:           PostgreSQL 8                                    */
/* Project file:          Project1.dez                                    */
/* Project name:                                                          */
/* Author:                                                                */
/* Script type:           Database creation script                        */
/* Created on:            2009-06-24 17:06                                */
/* Model version:         Version 2009-06-24                              */
/* ---------------------------------------------------------------------- */


/* ---------------------------------------------------------------------- */
/* Tables                                                                 */
/* ---------------------------------------------------------------------- */

/* ---------------------------------------------------------------------- */
/* Add table "Addresses"                                                  */
/* ---------------------------------------------------------------------- */

CREATE TABLE Addresses (
    address_id SERIAL  NOT NULL,
    line_1 TEXT,
    line_2 TEXT,
    line_3 TEXT,
    line_4 TEXT,
    city TEXT,
    zip_postcode TEXT,
    state_province_county TEXT,
    country TEXT,
    other_address_details TEXT,
    CONSTRAINT PK_Addresses PRIMARY KEY (address_id)
);

CREATE UNIQUE INDEX Index_CE0F091A_DEC4_4623 ON Addresses (address_id);

/* ---------------------------------------------------------------------- */
/* Add table "Aircraft_Parts"                                             */
/* ---------------------------------------------------------------------- */

CREATE TABLE Aircraft_Parts (
    part_number TEXT  NOT NULL,
    part_short_name TEXT,
    part_long_name TEXT,
    part_price MONEY,
    part_description TEXT,
    date_part_available DATE,
    date_part_discontinued DATE,
    other_part_details TEXT,
    CONSTRAINT PK_Aircraft_Parts PRIMARY KEY (part_number)
);

CREATE UNIQUE INDEX Index_80730ABD_CF1F_4A8C ON Aircraft_Parts (part_number);

/* ---------------------------------------------------------------------- */
/* Add table "Customer_Addresses"                                         */
/* ---------------------------------------------------------------------- */

CREATE TABLE Customer_Addresses (
    customer_id INTEGER  NOT NULL,
    address_id INTEGER  NOT NULL,
    date_address_from DATE  NOT NULL,
    address_type_code TEXT  NOT NULL,
    date_address_to DATE,
    CONSTRAINT Index_123CE964_8EA7_4187 PRIMARY KEY (customer_id, address_id, date_address_from)
);

/* ---------------------------------------------------------------------- */
/* Add table "Customer_Order_Items"                                       */
/* ---------------------------------------------------------------------- */

CREATE TABLE Customer_Order_Items (
    order_id INTEGER  NOT NULL,
    part_number TEXT  NOT NULL,
    date_delivered DATE,
    quantity_ordered TEXT,
    quantity_delivered TEXT,
    other_item_details TEXT,
    CONSTRAINT PK_Customer_Order_Items PRIMARY KEY (order_id, part_number)
);

/* ---------------------------------------------------------------------- */
/* Add table "Customer_Orders"                                            */
/* ---------------------------------------------------------------------- */

CREATE TABLE Customer_Orders (
    order_id SERIAL  NOT NULL,
    customer_id INTEGER  NOT NULL,
    order_status_code TEXT  NOT NULL,
    date_order_placed DATE,
    date_order_completed DATE,
    other_order_details TEXT,
    CONSTRAINT PK_Customer_Orders PRIMARY KEY (order_id)
);

CREATE UNIQUE INDEX Index_9A58F1ED_06A7_4B58 ON Customer_Orders (order_id);

/* ---------------------------------------------------------------------- */
/* Add table "Customer_Quotations"                                        */
/* ---------------------------------------------------------------------- */

CREATE TABLE Customer_Quotations (
    quotation_id SERIAL  NOT NULL,
    customer_id INTEGER  NOT NULL,
    quotation_status_code TEXT  NOT NULL,
    date_quotation_made DATE,
    date_valid_until DATE,
    other_quotation_details TEXT,
    CONSTRAINT PK_Customer_Quotations PRIMARY KEY (quotation_id)
);

CREATE UNIQUE INDEX Index_A0562BAD_5602_4877 ON Customer_Quotations (quotation_id);

/* ---------------------------------------------------------------------- */
/* Add table "Customer_Quotations_Items"                                  */
/* ---------------------------------------------------------------------- */

CREATE TABLE Customer_Quotations_Items (
    quotation_id INTEGER  NOT NULL,
    part_number TEXT  NOT NULL,
    item_quantity TEXT,
    quotation_item_price MONEY,
    CONSTRAINT PK_Customer_Quotations_Items PRIMARY KEY (quotation_id, part_number)
);

/* ---------------------------------------------------------------------- */
/* Add table "Customers"                                                  */
/* ---------------------------------------------------------------------- */

CREATE TABLE Customers (
    customer_id SERIAL  NOT NULL,
    customer_name TEXT,
    customer_phone TEXT,
    customer_email TEXT,
    date_became_customer DATE,
    other_customer_details TEXT,
    CONSTRAINT PK_Customers PRIMARY KEY (customer_id)
);

CREATE UNIQUE INDEX Index_317A1AE0_6A26_4900 ON Customers (customer_id);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Address_Types"                                          */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Address_Types (
    address_type_code TEXT  NOT NULL,
    address_type_description TEXT,
    CONSTRAINT PK_Ref_Address_Types PRIMARY KEY (address_type_code)
);

CREATE UNIQUE INDEX Index_C356FA27_5419_42A6 ON Ref_Address_Types (address_type_code);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Order_Status"                                           */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Order_Status (
    order_status_code TEXT  NOT NULL,
    order_status_description TEXT,
    CONSTRAINT PK_Ref_Order_Status PRIMARY KEY (order_status_code)
);

CREATE UNIQUE INDEX Index_1FD7CE21_7A1D_4E25 ON Ref_Order_Status (order_status_code);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Quotation_Status"                                       */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Quotation_Status (
    quotation_status_code TEXT  NOT NULL,
    quotation_status_description TEXT,
    CONSTRAINT PK_Ref_Quotation_Status PRIMARY KEY (quotation_status_code)
);

CREATE UNIQUE INDEX Index_C4502914_B169_45C9 ON Ref_Quotation_Status (quotation_status_code);

/* ---------------------------------------------------------------------- */
/* Add table "Related_Order_Quotations"                                   */
/* ---------------------------------------------------------------------- */

CREATE TABLE Related_Order_Quotations (
    quotation_id INTEGER  NOT NULL,
    order_id INTEGER  NOT NULL,
    CONSTRAINT PK_Related_Order_Quotations PRIMARY KEY (quotation_id, order_id)
);
