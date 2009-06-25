/* ---------------------------------------------------------------------- */
/* Script generated with: DeZign for Databases v5.2.0                     */
/* Target DBMS:           PostgreSQL 8                                    */
/* Project file:          Project1.dez                                    */
/* Project name:                                                          */
/* Author:                                                                */
/* Script type:           Database creation script                        */
/* Created on:            2009-06-24 18:04                                */
/* Model version:         Version 2009-06-24                              */
/* ---------------------------------------------------------------------- */


/* ---------------------------------------------------------------------- */
/* Tables                                                                 */
/* ---------------------------------------------------------------------- */

/* ---------------------------------------------------------------------- */
/* Add table "Contact_History"                                            */
/* ---------------------------------------------------------------------- */

CREATE TABLE Contact_History (
    contact_history_id SERIAL,
    customer_id INTEGER,
    outcome_status_code TEXT,
    contact_datetime DATE,
    contact_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Customer_Assets"                                            */
/* ---------------------------------------------------------------------- */

CREATE TABLE Customer_Assets (
    customer_asset_id SERIAL,
    customer_id INTEGER,
    asset_type_code TEXT,
    date_asset_acquired DATE
);

/* ---------------------------------------------------------------------- */
/* Add table "Customer_Loyalty"                                           */
/* ---------------------------------------------------------------------- */

CREATE TABLE Customer_Loyalty (
    customer_id INTEGER,
    date_first_purchase DATE,
    date_last_purchase DATE,
    other_loyalty_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Customer_Offers"                                            */
/* ---------------------------------------------------------------------- */

CREATE TABLE Customer_Offers (
    customer_id INTEGER,
    special_offer_id INTEGER,
    date_offer_accepted DATE
);

/* ---------------------------------------------------------------------- */
/* Add table "Customer_Preferences"                                       */
/* ---------------------------------------------------------------------- */

CREATE TABLE Customer_Preferences (
    customer_id INTEGER,
    factor_code TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Customer_Product_Holdings"                                  */
/* ---------------------------------------------------------------------- */

CREATE TABLE Customer_Product_Holdings (
    customer_id INTEGER,
    product_id INTEGER,
    date_acquired DATE,
    date_discontinued DATE
);

/* ---------------------------------------------------------------------- */
/* Add table "Customer_Profiles"                                          */
/* ---------------------------------------------------------------------- */

CREATE TABLE Customer_Profiles (
    customer_id SERIAL,
    customer_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Household_Members"                                          */
/* ---------------------------------------------------------------------- */

CREATE TABLE Household_Members (
    customer_id INTEGER,
    member_count INTEGER,
    count_of_children INTEGER,
    count_of_wage_earners INTEGER
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Asset_Types"                                            */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Asset_Types (
    asset_type_code TEXT,
    asset_type_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Contact_Outcomes"                                       */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Contact_Outcomes (
    outcome_status_code TEXT,
    outcome_status_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Preference_Factors"                                     */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Preference_Factors (
    factor_code TEXT,
    factor_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Services_and_Products"                                      */
/* ---------------------------------------------------------------------- */

CREATE TABLE Services_and_Products (
    product_id SERIAL,
    product_name TEXT,
    product_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Special_Offers"                                             */
/* ---------------------------------------------------------------------- */

CREATE TABLE Special_Offers (
    special_offer_id SERIAL,
    special_offer_details TEXT
);
