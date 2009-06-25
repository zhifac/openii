/* ---------------------------------------------------------------------- */
/* Script generated with: DeZign for Databases v5.2.0                     */
/* Target DBMS:           PostgreSQL 8                                    */
/* Project file:          Project1.dez                                    */
/* Project name:                                                          */
/* Author:                                                                */
/* Script type:           Database creation script                        */
/* Created on:            2009-06-24 19:18                                */
/* Model version:         Version 2009-06-24                              */
/* ---------------------------------------------------------------------- */


/* ---------------------------------------------------------------------- */
/* Tables                                                                 */
/* ---------------------------------------------------------------------- */

/* ---------------------------------------------------------------------- */
/* Add table "Appointment_Status_Codes"                                   */
/* ---------------------------------------------------------------------- */

CREATE TABLE Appointment_Status_Codes (
    appointment_status_code TEXT,
    appointment_status_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Appointments"                                               */
/* ---------------------------------------------------------------------- */

CREATE TABLE Appointments (
    appointment_id SERIAL,
    appointment_status_code TEXT,
    patient_id INTEGER,
    staff_id INTEGER,
    appointment_datetime DATE,
    appointment_end_datetime DATE,
    appointment_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Patient_Records"                                            */
/* ---------------------------------------------------------------------- */

CREATE TABLE Patient_Records (
    patient_record_id SERIAL,
    patient_id INTEGER,
    component_code TEXT,
    updated_by_staff_id INTEGER,
    updated_date DATE,
    patient_record_component_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Patients"                                                   */
/* ---------------------------------------------------------------------- */

CREATE TABLE Patients (
    patient_id SERIAL,
    comanaged_yn TEXT,
    nhs_number TEXT,
    gender TEXT,
    date_of_birth DATE,
    patient_name TEXT,
    patient_address TEXT,
    height TEXT,
    weight TEXT,
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
/* Add table "Roles"                                                      */
/* ---------------------------------------------------------------------- */

CREATE TABLE Roles (
    role_code TEXT,
    role_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Staff"                                                      */
/* ---------------------------------------------------------------------- */

CREATE TABLE Staff (
    staff_id SERIAL,
    staff_category_code TEXT,
    role_code TEXT,
    gender TEXT,
    staff_first_name TEXT,
    staff_middle_name TEXT,
    staff_last_name TEXT,
    staff_qualifications TEXT,
    staff_birth_date DATE,
    other_staff_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Staff_Categories"                                           */
/* ---------------------------------------------------------------------- */

CREATE TABLE Staff_Categories (
    staff_category_code TEXT,
    staff_category_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Staff_Patient_Associations"                                 */
/* ---------------------------------------------------------------------- */

CREATE TABLE Staff_Patient_Associations (
    association_id SERIAL,
    patient_id INTEGER,
    staff_id INTEGER,
    association_start_date DATE,
    association_end_date DATE,
    association_details TEXT
);
