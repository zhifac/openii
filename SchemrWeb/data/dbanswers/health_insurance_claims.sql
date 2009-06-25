/* ---------------------------------------------------------------------- */
/* Script generated with: DeZign for Databases v5.2.0                     */
/* Target DBMS:           PostgreSQL 8                                    */
/* Project file:          Project1.dez                                    */
/* Project name:                                                          */
/* Author:                                                                */
/* Script type:           Database creation script                        */
/* Created on:            2009-06-24 19:23                                */
/* Model version:         Version 2009-06-24                              */
/* ---------------------------------------------------------------------- */


/* ---------------------------------------------------------------------- */
/* Tables                                                                 */
/* ---------------------------------------------------------------------- */

/* ---------------------------------------------------------------------- */
/* Add table "Claim_Processing_Stages"                                    */
/* ---------------------------------------------------------------------- */

CREATE TABLE Claim_Processing_Stages (
    claim_id INTEGER,
    processor_staff_id INTEGER,
    start_date DATE,
    dispute_area_code TEXT,
    end_date DATE,
    step_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Claims"                                                     */
/* ---------------------------------------------------------------------- */

CREATE TABLE Claims (
    claim_id SERIAL,
    claim_status_code TEXT,
    hospital_id INTEGER,
    insurance_company_id INTEGER,
    patient_id INTEGER,
    updated_date DATE,
    claim_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Hospitals"                                                  */
/* ---------------------------------------------------------------------- */

CREATE TABLE Hospitals (
    hospital_id SERIAL,
    hospital_name TEXT,
    hospital_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Insurance_Companies"                                        */
/* ---------------------------------------------------------------------- */

CREATE TABLE Insurance_Companies (
    insurance_company_id SERIAL,
    company_name TEXT,
    company_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Patients"                                                   */
/* ---------------------------------------------------------------------- */

CREATE TABLE Patients (
    patient_id SERIAL,
    gender TEXT,
    date_of_birth DATE,
    patient_name TEXT,
    patient_address TEXT,
    other_patient_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Claim_Status"                                           */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Claim_Status (
    claim_status_code TEXT,
    claim_status_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Dispute_Areas"                                          */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Dispute_Areas (
    dispute_area_code TEXT,
    dispute_area_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Staff_Categories"                                       */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Staff_Categories (
    staff_category_code TEXT,
    staff_category_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Staff"                                                      */
/* ---------------------------------------------------------------------- */

CREATE TABLE Staff (
    staff_id SERIAL,
    staff_category_code TEXT,
    gender TEXT,
    staff_first_name TEXT,
    staff_middle_name TEXT,
    staff_last_name TEXT,
    staff_qualifications TEXT,
    staff_birth_date DATE,
    other_staff_details TEXT
);
