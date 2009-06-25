/* ---------------------------------------------------------------------- */
/* Script generated with: DeZign for Databases v5.2.0                     */
/* Target DBMS:           PostgreSQL 8                                    */
/* Project file:          Project1.dez                                    */
/* Project name:                                                          */
/* Author:                                                                */
/* Script type:           Database creation script                        */
/* Created on:            2009-06-24 19:36                                */
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
    town_city TEXT,
    zip_postcode TEXT,
    county_state_province TEXT,
    country TEXT,
    other_address_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Features"                                                   */
/* ---------------------------------------------------------------------- */

CREATE TABLE Features (
    feature_id SERIAL,
    feature_name TEXT,
    feature_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Properties"                                                 */
/* ---------------------------------------------------------------------- */

CREATE TABLE Properties (
    property_id SERIAL,
    property_address_id INTEGER,
    owner_user_id INTEGER,
    property_type_code TEXT,
    date_on_market DATE,
    date_off_market DATE,
    property_name TEXT,
    property_description TEXT,
    garage_yn TEXT,
    parking_yn TEXT,
    room_count TEXT,
    vendor_requested_price NUMERIC,
    price_min NUMERIC,
    price_max NUMERIC,
    other_property_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Property_Features"                                          */
/* ---------------------------------------------------------------------- */

CREATE TABLE Property_Features (
    property_id INTEGER,
    feature_id INTEGER,
    feature_value TEXT,
    property_feature_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Property_Photos"                                            */
/* ---------------------------------------------------------------------- */

CREATE TABLE Property_Photos (
    property_id INTEGER,
    photo_seq INTEGER,
    photo_title TEXT,
    photo_description TEXT,
    photo_filename TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Age_Categories"                                         */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Age_Categories (
    age_category_code TEXT,
    age_category_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Property_Types"                                         */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Property_Types (
    property_type_code TEXT,
    property_type_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Room_Types"                                             */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Room_Types (
    room_type_code TEXT,
    room_type_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_User_Categories"                                        */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_User_Categories (
    user_category_code TEXT,
    user_category_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Rooms"                                                      */
/* ---------------------------------------------------------------------- */

CREATE TABLE Rooms (
    property_id INTEGER,
    room_number TEXT,
    room_type_code TEXT,
    room_size TEXT,
    other_room_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "User_Property_History"                                      */
/* ---------------------------------------------------------------------- */

CREATE TABLE User_Property_History (
    user_id INTEGER,
    property_id INTEGER,
    datestamp DATE
);

/* ---------------------------------------------------------------------- */
/* Add table "User_Searches"                                              */
/* ---------------------------------------------------------------------- */

CREATE TABLE User_Searches (
    user_id INTEGER,
    search_seq INTEGER,
    search_datetime DATE,
    search_string TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Users"                                                      */
/* ---------------------------------------------------------------------- */

CREATE TABLE Users (
    user_id SERIAL,
    age_category_code TEXT,
    user_category_code TEXT,
    user_address_id INTEGER,
    buyer_yn TEXT,
    seller_yn TEXT,
    login_name TEXT,
    password TEXT,
    date_registered DATE,
    first_name TEXT,
    middle_name TEXT,
    last_name TEXT,
    other_user_details TEXT
);
