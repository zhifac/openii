/* ---------------------------------------------------------------------- */
/* Script generated with: DeZign for Databases v5.2.0                     */
/* Target DBMS:           PostgreSQL 8                                    */
/* Project file:          Project1.dez                                    */
/* Project name:                                                          */
/* Author:                                                                */
/* Script type:           Database creation script                        */
/* Created on:            2009-06-24 19:24                                */
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
/* Add table "Patient_Addresses"                                          */
/* ---------------------------------------------------------------------- */

CREATE TABLE Patient_Addresses (
    patient_id INTEGER,
    address_id INTEGER,
    date_address_from DATE,
    date_address_to DATE
);

/* ---------------------------------------------------------------------- */
/* Add table "Patient_Bill_Items"                                         */
/* ---------------------------------------------------------------------- */

CREATE TABLE Patient_Bill_Items (
    patient_bill_id INTEGER,
    item_seq_nr INTEGER,
    quanity TEXT,
    total_cost NUMERIC
);

/* ---------------------------------------------------------------------- */
/* Add table "Patient_Bills"                                              */
/* ---------------------------------------------------------------------- */

CREATE TABLE Patient_Bills (
    patient_bill_id SERIAL,
    patient_id INTEGER,
    date_bill_paid DATE,
    total_amount_due NUMERIC,
    other_bill_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Patient_Payment_Methods"                                    */
/* ---------------------------------------------------------------------- */

CREATE TABLE Patient_Payment_Methods (
    patient_method_id SERIAL,
    patient_id INTEGER,
    payment_method_code TEXT,
    payment_method_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Patient_Records"                                            */
/* ---------------------------------------------------------------------- */

CREATE TABLE Patient_Records (
    patient_record_id SERIAL,
    patient_id INTEGER,
    billable_item_code TEXT,
    component_code TEXT,
    updated_by_staff_id INTEGER,
    updated_date DATE,
    admission_datetime DATE,
    medical_condition TEXT,
    patient_record_component_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Patient_Rooms"                                              */
/* ---------------------------------------------------------------------- */

CREATE TABLE Patient_Rooms (
    patient_id INTEGER,
    room_id TEXT,
    date_stay_from DATE,
    date_date_to DATE
);

/* ---------------------------------------------------------------------- */
/* Add table "patients"                                                   */
/* ---------------------------------------------------------------------- */

CREATE TABLE patients (
    patient_id SERIAL,
    outpatient_yn TEXT,
    hospital_number TEXT,
    nhs_number TEXT,
    gender TEXT,
    date_of_birth DATE,
    patient_first_name TEXT,
    patient_middle_name TEXT,
    patient_last_name TEXT,
    height TEXT,
    weight TEXT,
    next_of_kin TEXT,
    home_phone TEXT,
    work_phone TEXT,
    cell_mobile_phone TEXT,
    other_patient_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Record_Components"                                          */
/* ---------------------------------------------------------------------- */

CREATE TABLE Record_Components (
    component_code TEXT,
    component_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Billable_Items"                                         */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Billable_Items (
    billable_item_code TEXT,
    billable_item_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Payment_Methods"                                        */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Payment_Methods (
    payment_method_code TEXT,
    payment_method_description TEXT
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
    staff_job_title TEXT,
    staff_first_name TEXT,
    staff_middle_name TEXT,
    staff_last_name TEXT,
    staff_qualifications TEXT,
    staff_birth_date DATE,
    other_staff_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Staff_Addresses"                                            */
/* ---------------------------------------------------------------------- */

CREATE TABLE Staff_Addresses (
    staff_id INTEGER,
    address_id INTEGER,
    date_address_from DATE,
    date_address_to DATE
);
