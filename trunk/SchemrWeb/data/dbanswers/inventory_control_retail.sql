/* ---------------------------------------------------------------------- */
/* Script generated with: DeZign for Databases v5.2.0                     */
/* Target DBMS:           PostgreSQL 8                                    */
/* Project file:          Project1.dez                                    */
/* Project name:                                                          */
/* Author:                                                                */
/* Script type:           Database creation script                        */
/* Created on:            2009-06-24 19:28                                */
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
    zip_code TEXT,
    state_province_county TEXT,
    country TEXT,
    other_address_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Brands"                                                     */
/* ---------------------------------------------------------------------- */

CREATE TABLE Brands (
    brand_id INTEGER,
    brand_short_name TEXT,
    brand_full_name TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Inventory_Items"                                            */
/* ---------------------------------------------------------------------- */

CREATE TABLE Inventory_Items (
    item_id SERIAL,
    brand_id INTEGER,
    item_category_code TEXT,
    item_description TEXT,
    average_monthly_usage TEXT,
    reorder_level TEXT,
    reorder_quantity TEXT,
    other_item_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Item_Stock_Levels"                                          */
/* ---------------------------------------------------------------------- */

CREATE TABLE Item_Stock_Levels (
    item_id INTEGER,
    stock_date DATE,
    quantity_in_stock TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Item_Vendors"                                               */
/* ---------------------------------------------------------------------- */

CREATE TABLE Item_Vendors (
    item_id INTEGER,
    vendor_id INTEGER,
    value_supplied_to_date TEXT,
    total_quantity_supplied_to_date TEXT,
    first_item_supplied_date DATE,
    last_item_supplied_date DATE,
    delivery_lead_time TEXT,
    standard_price NUMERIC,
    percentage_discount TEXT,
    minimum_order_quantity TEXT,
    maximum_order_quantity TEXT,
    other_item_suppliers_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Address_Types"                                          */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Address_Types (
    address_type_code TEXT,
    address_type_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Item_Categories"                                        */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Item_Categories (
    item_category_code TEXT,
    item_category_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Vendor_Addresses"                                           */
/* ---------------------------------------------------------------------- */

CREATE TABLE Vendor_Addresses (
    vendor_id INTEGER,
    address_id INTEGER,
    address_type_code TEXT,
    date_address_from DATE,
    date_address_to DATE
);

/* ---------------------------------------------------------------------- */
/* Add table "Vendors"                                                    */
/* ---------------------------------------------------------------------- */

CREATE TABLE Vendors (
    vendor_id SERIAL,
    vendor_name TEXT,
    vendor_email TEXT,
    vendor_phone TEXT,
    vendor_cell_phone TEXT,
    other_vendor_details TEXT
);
