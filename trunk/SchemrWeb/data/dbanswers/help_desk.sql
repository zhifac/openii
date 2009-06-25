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
/* Add table "Equipment_Problem_History"                                  */
/* ---------------------------------------------------------------------- */

CREATE TABLE Equipment_Problem_History (
    problem_history_id SERIAL,
    equipment_id INTEGER,
    problem_id INTEGER,
    problem_status_code TEXT,
    staff_id INTEGER,
    priority_level_code TEXT,
    fix_datetime DATE
);

/* ---------------------------------------------------------------------- */
/* Add table "Equipment_Problem_Reports"                                  */
/* ---------------------------------------------------------------------- */

CREATE TABLE Equipment_Problem_Reports (
    problem_id SERIAL,
    priority_level_code TEXT,
    problem_reported_datetime DATE
);

/* ---------------------------------------------------------------------- */
/* Add table "IT_Equipment"                                               */
/* ---------------------------------------------------------------------- */

CREATE TABLE IT_Equipment (
    equipment_id SERIAL,
    equipment_type_code TEXT,
    date_equipment_acquired DATE,
    date_equipment_disposed DATE,
    equipment_code TEXT,
    equipment_name TEXT,
    equipment_description TEXT,
    manufacturer_name TEXT,
    model_name TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Equipment_Types"                                        */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Equipment_Types (
    equipment_type_code TEXT,
    equipment_type_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Priority_Levels"                                        */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Priority_Levels (
    priority_level_code TEXT,
    priority_level_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Problem_Status_Codes"                                   */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Problem_Status_Codes (
    problem_status_code TEXT,
    problem_status_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Skill_Codes"                                            */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Skill_Codes (
    skill_code TEXT,
    skill_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_User_Types"                                             */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_User_Types (
    user_type_code TEXT,
    user_type_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Resolutions"                                                */
/* ---------------------------------------------------------------------- */

CREATE TABLE Resolutions (
    resolution_id SERIAL,
    problem_history_id INTEGER,
    resolution_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Staff_Skills"                                               */
/* ---------------------------------------------------------------------- */

CREATE TABLE Staff_Skills (
    staff_id INTEGER,
    skill_code TEXT,
    customer_id INTEGER,
    service_id INTEGER,
    date_skill_obtained DATE
);

/* ---------------------------------------------------------------------- */
/* Add table "Support_Staff"                                              */
/* ---------------------------------------------------------------------- */

CREATE TABLE Support_Staff (
    staff_id SERIAL,
    payment_method_code TEXT,
    data_joined DATE,
    date_left DATE,
    staff_name TEXT,
    staff_phone TEXT,
    staff_email TEXT,
    staff_location TEXT,
    other_staff_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "User_Equipment_History"                                     */
/* ---------------------------------------------------------------------- */

CREATE TABLE User_Equipment_History (
    equipment_id INTEGER,
    user_id INTEGER,
    date_equipment_owned DATE,
    date_equipment_released DATE,
    equipment_id_1 INTEGER
);

/* ---------------------------------------------------------------------- */
/* Add table "Users"                                                      */
/* ---------------------------------------------------------------------- */

CREATE TABLE Users (
    user_id SERIAL,
    payment_method_code TEXT,
    user_name TEXT,
    user_phone TEXT,
    user_email TEXT,
    er_address TEXT,
    other_user_details TEXT
);
