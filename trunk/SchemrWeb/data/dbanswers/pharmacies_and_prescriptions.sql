/* ---------------------------------------------------------------------- */
/* Script generated with: DeZign for Databases v5.2.0                     */
/* Target DBMS:           PostgreSQL 8                                    */
/* Project file:          Project1.dez                                    */
/* Project name:                                                          */
/* Author:                                                                */
/* Script type:           Database creation script                        */
/* Created on:            2009-06-24 19:33                                */
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
/* Add table "Customers"                                                  */
/* ---------------------------------------------------------------------- */

CREATE TABLE Customers (
    customer_id SERIAL,
    address_id INTEGER,
    customer_name TEXT,
    date_became_customer DATE,
    credit_card_number TEXT,
    card_expiry_date DATE,
    other_customer_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Drug_Companies"                                             */
/* ---------------------------------------------------------------------- */

CREATE TABLE Drug_Companies (
    company_id SERIAL,
    company_name TEXT,
    other_company_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Drugs_and_Medication"                                       */
/* ---------------------------------------------------------------------- */

CREATE TABLE Drugs_and_Medication (
    drug_id SERIAL,
    company_id INTEGER,
    drug_name TEXT,
    drug_cost NUMERIC,
    drug_available_date DATE,
    drug_withdrawn_date DATE,
    drug_description TEXT,
    generic_yn TEXT,
    generic_equivalent_drug_id INTEGER,
    other_drug_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Physicians"                                                 */
/* ---------------------------------------------------------------------- */

CREATE TABLE Physicians (
    physician_id SERIAL,
    address_id INTEGER,
    physician_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Prescription_Items"                                         */
/* ---------------------------------------------------------------------- */

CREATE TABLE Prescription_Items (
    prescription_id INTEGER,
    drug_id INTEGER,
    prescription_quantity TEXT,
    instructions_to_customer TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Prescriptions"                                              */
/* ---------------------------------------------------------------------- */

CREATE TABLE Prescriptions (
    prescription_id SERIAL,
    customer_id INTEGER,
    physician_id INTEGER,
    prescription_status_code TEXT,
    payment_method_code TEXT,
    prescription_issued_date DATE,
    prescription_filled_date DATE,
    other_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Payment_Methods"                                        */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Payment_Methods (
    payment_method_code TEXT,
    payment_method_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Prescription_Status"                                    */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Prescription_Status (
    prescription_status_code TEXT,
    prescription_status_description TEXT
);
