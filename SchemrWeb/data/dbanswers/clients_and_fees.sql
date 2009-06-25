/* ---------------------------------------------------------------------- */
/* Script generated with: DeZign for Databases v5.2.0                     */
/* Target DBMS:           PostgreSQL 8                                    */
/* Project file:          Project1.dez                                    */
/* Project name:                                                          */
/* Author:                                                                */
/* Script type:           Database creation script                        */
/* Created on:            2009-06-24 18:01                                */
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
    state_province TEXT,
    country_code TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Client_Addresses"                                           */
/* ---------------------------------------------------------------------- */

CREATE TABLE Client_Addresses (
    client_id INTEGER,
    address_id INTEGER,
    date_address_from DATE,
    date_address_to DATE
);

/* ---------------------------------------------------------------------- */
/* Add table "Clients"                                                    */
/* ---------------------------------------------------------------------- */

CREATE TABLE Clients (
    client_id SERIAL,
    client_name TEXT,
    client_from_date DATE,
    kpi_avg_billable_rate NUMERIC,
    kpi_billings_to_date NUMERIC,
    kpi_client_project_count INTEGER,
    other_client_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Fees"                                                       */
/* ---------------------------------------------------------------------- */

CREATE TABLE Fees (
    client_id INTEGER,
    period_id DATE,
    total_fees NUMERIC
);

/* ---------------------------------------------------------------------- */
/* Add table "Invoices"                                                   */
/* ---------------------------------------------------------------------- */

CREATE TABLE Invoices (
    invoice_id SERIAL,
    invoice_number TEXT,
    period_id DATE,
    client_id INTEGER,
    invoice_status_code TEXT,
    date_issued DATE,
    date_paid DATE,
    total_fees NUMERIC,
    discount TEXT,
    amount_payable NUMERIC,
    other_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Projects"                                                   */
/* ---------------------------------------------------------------------- */

CREATE TABLE Projects (
    project_id SERIAL,
    client_id INTEGER,
    project_name TEXT,
    project_start_date DATE,
    project_end_date DATE,
    project_description TEXT,
    other_project_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Calendar"                                               */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Calendar (
    day_date DATE,
    business_day_yn TEXT,
    day_number INTEGER,
    period_id INTEGER
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Invoice_Status"                                         */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Invoice_Status (
    invoice_status_code TEXT,
    invoice_status_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Job_Types"                                              */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Job_Types (
    job_type_code TEXT,
    job_type_name TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Roles"                                                  */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Roles (
    role_code TEXT,
    role_name TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Skill_Levels"                                           */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Skill_Levels (
    skill_level_code TEXT,
    skill_level_name TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Skills"                                                 */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Skills (
    skill_code TEXT,
    skill_name TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Staff"                                                      */
/* ---------------------------------------------------------------------- */

CREATE TABLE Staff (
    staff_id SERIAL,
    job_type_code TEXT,
    staff_name TEXT,
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

/* ---------------------------------------------------------------------- */
/* Add table "Staff_on_Projects"                                          */
/* ---------------------------------------------------------------------- */

CREATE TABLE Staff_on_Projects (
    staff_on_project_period_id INTEGER,
    project_id INTEGER,
    staff_id INTEGER,
    role_code TEXT,
    from_datetime DATE,
    to_datetime DATE,
    hourly_rate NUMERIC
);

/* ---------------------------------------------------------------------- */
/* Add table "Staff_Skills"                                               */
/* ---------------------------------------------------------------------- */

CREATE TABLE Staff_Skills (
    staff_id INTEGER,
    skill_code TEXT,
    skill_level_code TEXT
);
