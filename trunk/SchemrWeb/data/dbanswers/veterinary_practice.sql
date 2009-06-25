/* ---------------------------------------------------------------------- */
/* Script generated with: DeZign for Databases v5.2.0                     */
/* Target DBMS:           PostgreSQL 8                                    */
/* Project file:          Project1.dez                                    */
/* Project name:                                                          */
/* Author:                                                                */
/* Script type:           Database creation script                        */
/* Created on:            2009-06-24 19:40                                */
/* Model version:         Version 2009-06-24                              */
/* ---------------------------------------------------------------------- */


/* ---------------------------------------------------------------------- */
/* Tables                                                                 */
/* ---------------------------------------------------------------------- */

/* ---------------------------------------------------------------------- */
/* Add table "Bill_Items"                                                 */
/* ---------------------------------------------------------------------- */

CREATE TABLE Bill_Items (
    bill_id NUMERIC,
    treatment_id NUMERIC,
    medication_id NUMERIC,
    quantity_used TEXT,
    cost NUMERIC
);

/* ---------------------------------------------------------------------- */
/* Add table "Bills"                                                      */
/* ---------------------------------------------------------------------- */

CREATE TABLE Bills (
    bill_id SERIAL,
    bill_status_code TEXT,
    treatment_id NUMERIC,
    date_presented DATE,
    date_paid DATE,
    bill_amount NUMERIC,
    amount_outstanding NUMERIC,
    bill_comments TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Medications"                                                */
/* ---------------------------------------------------------------------- */

CREATE TABLE Medications (
    medication_id SERIAL,
    medication_name TEXT,
    normal_dosage TEXT,
    maximum_dosage TEXT,
    unit_cost NUMERIC,
    other_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Organisations_Addresses"                                    */
/* ---------------------------------------------------------------------- */

CREATE TABLE Organisations_Addresses (
    place_id NUMERIC,
    address_id NUMERIC
);

/* ---------------------------------------------------------------------- */
/* Add table "Owners"                                                     */
/* ---------------------------------------------------------------------- */

CREATE TABLE Owners (
    owner_id SERIAL,
    owner_name TEXT,
    owner_address_id INTEGER,
    other_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "People_Addresses"                                           */
/* ---------------------------------------------------------------------- */

CREATE TABLE People_Addresses (
    pet_id NUMERIC,
    address_id NUMERIC
);

/* ---------------------------------------------------------------------- */
/* Add table "Pets"                                                       */
/* ---------------------------------------------------------------------- */

CREATE TABLE Pets (
    pet_id SERIAL,
    owner_id NUMERIC,
    species_code TEXT,
    color TEXT,
    name TEXT,
    height TEXT,
    weight TEXT,
    gender TEXT,
    date_of_birth DATE,
    other_pet_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Places_Events"                                              */
/* ---------------------------------------------------------------------- */

CREATE TABLE Places_Events (
    place_id NUMERIC,
    treatment_id NUMERIC
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Bill_Status"                                            */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Bill_Status (
    bill_status_code TEXT,
    bill_status_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Species"                                                */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Species (
    species_code TEXT,
    species_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Treatment_Types"                                        */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Treatment_Types (
    treatment_type_code TEXT,
    treatment_type_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Treatment_Medications"                                      */
/* ---------------------------------------------------------------------- */

CREATE TABLE Treatment_Medications (
    treatment_id NUMERIC,
    medication_id NUMERIC,
    quantity_used NUMERIC,
    cost NUMERIC
);

/* ---------------------------------------------------------------------- */
/* Add table "Treatments"                                                 */
/* ---------------------------------------------------------------------- */

CREATE TABLE Treatments (
    treatment_id SERIAL,
    pet_id NUMERIC,
    appointment_datetime DATE,
    treatment_type_code TEXT,
    treatment_end_datetime DATE,
    treatment_name TEXT,
    treatment_description TEXT,
    other_event_details TEXT
);
