/* ---------------------------------------------------------------------- */
/* Script generated with: DeZign for Databases v5.2.0                     */
/* Target DBMS:           PostgreSQL 8                                    */
/* Project file:          Project1.dez                                    */
/* Project name:                                                          */
/* Author:                                                                */
/* Script type:           Database creation script                        */
/* Created on:            2009-06-24 19:32                                */
/* Model version:         Version 2009-06-24                              */
/* ---------------------------------------------------------------------- */


/* ---------------------------------------------------------------------- */
/* Tables                                                                 */
/* ---------------------------------------------------------------------- */

/* ---------------------------------------------------------------------- */
/* Add table "Adjustment_Rule_Lines"                                      */
/* ---------------------------------------------------------------------- */

CREATE TABLE Adjustment_Rule_Lines (
    rule_id INTEGER,
    line_sequence INTEGER,
    rule_sql_or_text TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Adjustment_Rules"                                           */
/* ---------------------------------------------------------------------- */

CREATE TABLE Adjustment_Rules (
    rule_id SERIAL,
    deduction_type_code TEXT,
    rule_name TEXT,
    rule_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Employee_Pay"                                               */
/* ---------------------------------------------------------------------- */

CREATE TABLE Employee_Pay (
    pay_period_id INTEGER,
    employee_id INTEGER,
    gross_pay NUMERIC,
    net_pay NUMERIC
);

/* ---------------------------------------------------------------------- */
/* Add table "Employee_Pay_Adjustments"                                   */
/* ---------------------------------------------------------------------- */

CREATE TABLE Employee_Pay_Adjustments (
    pay_period_id INTEGER,
    employee_id INTEGER,
    adjustment_type_code TEXT,
    adjustment_amount NUMERIC,
    comment TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Employees"                                                  */
/* ---------------------------------------------------------------------- */

CREATE TABLE Employees (
    employee_id SERIAL,
    job_title_code TEXT,
    marital_status_code TEXT,
    first_name TEXT,
    date_of_birth DATE,
    gender TEXT,
    employee_phone TEXT,
    pay_per_period NUMERIC,
    middle_name TEXT,
    last_name TEXT,
    other_employee_details TEXT,
    employee_email TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Adjustment_Categories"                                  */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Adjustment_Categories (
    deduction_category_code TEXT,
    parent_deduction_category_code TEXT,
    deduction_or_addition TEXT,
    deduction_category_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Adjustment_Types"                                       */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Adjustment_Types (
    deduction_type_code TEXT,
    deduction_category_code TEXT,
    deduction_type_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Calendar"                                               */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Calendar (
    pay_period_id INTEGER,
    day_date DATE,
    day_number INTEGER
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Job_Titles"                                             */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Job_Titles (
    job_title_code TEXT,
    payment_frequency_code TEXT,
    standard_pay NUMERIC,
    job_title TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Marital_Status_Codes"                                   */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Marital_Status_Codes (
    marital_status_code TEXT,
    marital_status_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Payment_Frequencies"                                    */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Payment_Frequencies (
    frequency_code TEXT,
    frequency_description TEXT
);
