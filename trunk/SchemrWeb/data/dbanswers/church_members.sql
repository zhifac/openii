/* ---------------------------------------------------------------------- */
/* Script generated with: DeZign for Databases v5.2.0                     */
/* Target DBMS:           PostgreSQL 8                                    */
/* Project file:          Project1.dez                                    */
/* Project name:                                                          */
/* Author:                                                                */
/* Script type:           Database creation script                        */
/* Created on:            2009-06-24 17:57                                */
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
/* Add table "Cases_Trials"                                               */
/* ---------------------------------------------------------------------- */

CREATE TABLE Cases_Trials (
    case_trial_id SERIAL,
    case_reference_number TEXT,
    trial_outcome_code TEXT,
    trial_status_code TEXT,
    trial_address_id INTEGER,
    trial_location_name TEXT,
    trial_start_date DATE,
    expected_duration TEXT,
    trial_end_date DATE,
    other_trial_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Organization_Addresses"                                     */
/* ---------------------------------------------------------------------- */

CREATE TABLE Organization_Addresses (
    organization_id INTEGER,
    address_id INTEGER,
    address_type_code TEXT,
    date_address_from DATE,
    date_address_to DATE
);

/* ---------------------------------------------------------------------- */
/* Add table "Organizations"                                              */
/* ---------------------------------------------------------------------- */

CREATE TABLE Organizations (
    organization_id SERIAL,
    organization_type_code TEXT,
    organization_name TEXT,
    other_organization_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "People"                                                     */
/* ---------------------------------------------------------------------- */

CREATE TABLE People (
    person_id SERIAL,
    organization_id INTEGER,
    first_name TEXT,
    middle_name TEXT,
    last_name TEXT,
    date_of_birth DATE,
    gender TEXT,
    other_person_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "People_Addresses"                                           */
/* ---------------------------------------------------------------------- */

CREATE TABLE People_Addresses (
    person_id INTEGER,
    address_id INTEGER,
    date_address_from DATE,
    address_type_code TEXT,
    date_address_to DATE
);

/* ---------------------------------------------------------------------- */
/* Add table "People_at_Trial"                                            */
/* ---------------------------------------------------------------------- */

CREATE TABLE People_at_Trial (
    appearance_nr SERIAL,
    trial_id INTEGER,
    person_id INTEGER,
    role_code TEXT,
    appearance_start_datetime DATE,
    appearance_end_datetime DATE,
    other_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "People_Roles"                                               */
/* ---------------------------------------------------------------------- */

CREATE TABLE People_Roles (
    person_id INTEGER,
    role_code TEXT,
    date_role_from DATE,
    date_role_to DATE
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Address_Types"                                          */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Address_Types (
    address_type_code TEXT,
    address_type_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Organization_Types"                                     */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Organization_Types (
    organization_type_code TEXT,
    organization_type_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Role_Codes"                                             */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Role_Codes (
    role_code TEXT,
    role_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Trial_Outcomes"                                         */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Trial_Outcomes (
    trial_outcome_code TEXT,
    trial_outcome_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Trial_Status"                                           */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Trial_Status (
    trial_status_code TEXT,
    trial_status_description TEXT
);
