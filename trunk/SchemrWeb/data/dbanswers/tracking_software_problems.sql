/* ---------------------------------------------------------------------- */
/* Script generated with: DeZign for Databases v5.2.0                     */
/* Target DBMS:           PostgreSQL 8                                    */
/* Project file:          Project1.dez                                    */
/* Project name:                                                          */
/* Author:                                                                */
/* Script type:           Database creation script                        */
/* Created on:            2009-06-24 19:39                                */
/* Model version:         Version 2009-06-24                              */
/* ---------------------------------------------------------------------- */


/* ---------------------------------------------------------------------- */
/* Tables                                                                 */
/* ---------------------------------------------------------------------- */

/* ---------------------------------------------------------------------- */
/* Add table "Problem_Category_Codes"                                     */
/* ---------------------------------------------------------------------- */

CREATE TABLE Problem_Category_Codes (
    problem_category_code TEXT,
    problem_category_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Problem_Log"                                                */
/* ---------------------------------------------------------------------- */

CREATE TABLE Problem_Log (
    problem_log_id SERIAL,
    assigned_to_staff_id INTEGER,
    problem_id INTEGER,
    problem_category_code TEXT,
    problem_status_code TEXT,
    log_entry_date DATE,
    log_entry_description TEXT,
    log_entry_fix TEXT,
    other_log_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Problem_Status_Codes"                                       */
/* ---------------------------------------------------------------------- */

CREATE TABLE Problem_Status_Codes (
    problem_status_code TEXT,
    problem_status_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Problems"                                                   */
/* ---------------------------------------------------------------------- */

CREATE TABLE Problems (
    problem_id SERIAL,
    product_id INTEGER,
    closure_authorised_by_staff_id INTEGER,
    reported_by_staff_id INTEGER,
    date_problem_reported DATE,
    date_problem_closed DATE,
    problem_description TEXT,
    other_problem_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Product"                                                    */
/* ---------------------------------------------------------------------- */

CREATE TABLE Product (
    product_id SERIAL,
    product_name TEXT,
    product_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Staff"                                                      */
/* ---------------------------------------------------------------------- */

CREATE TABLE Staff (
    staff_id SERIAL,
    staff_first_name TEXT,
    staff_last_name TEXT,
    other_staff_details TEXT
);
