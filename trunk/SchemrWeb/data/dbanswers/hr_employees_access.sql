/* ---------------------------------------------------------------------- */
/* Script generated with: DeZign for Databases v5.2.0                     */
/* Target DBMS:           PostgreSQL 8                                    */
/* Project file:          Project1.dez                                    */
/* Project name:                                                          */
/* Author:                                                                */
/* Script type:           Database creation script                        */
/* Created on:            2009-06-24 19:26                                */
/* Model version:         Version 2009-06-24                              */
/* ---------------------------------------------------------------------- */


/* ---------------------------------------------------------------------- */
/* Tables                                                                 */
/* ---------------------------------------------------------------------- */

/* ---------------------------------------------------------------------- */
/* Add table "Complaints"                                                 */
/* ---------------------------------------------------------------------- */

CREATE TABLE Complaints (
    complaint_id SERIAL,
    complaint_outcome_code TEXT,
    complaint_status_code TEXT,
    complaint_type_code TEXT,
    raised_by_employee_id INTEGER,
    reported_to_employee_id INTEGER,
    date_complaint_raised DATE,
    date_complaint_closed DATE,
    complaint_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Departments"                                                */
/* ---------------------------------------------------------------------- */

CREATE TABLE Departments (
    Dept_ID SERIAL,
    Dept_Name TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Employee_Skills"                                            */
/* ---------------------------------------------------------------------- */

CREATE TABLE Employee_Skills (
    employee_id INTEGER,
    skill_code TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Employee_Training"                                          */
/* ---------------------------------------------------------------------- */

CREATE TABLE Employee_Training (
    employee_id INTEGER,
    course_code TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Employees"                                                  */
/* ---------------------------------------------------------------------- */

CREATE TABLE Employees (
    employee_id SERIAL,
    role_code TEXT,
    dept_code TEXT,
    supervisor_id INTEGER,
    education_details TEXT,
    personal_details TEXT,
    salary_details TEXT,
    other_employee_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Personal_Details"                                           */
/* ---------------------------------------------------------------------- */

CREATE TABLE Personal_Details (
    PD_ID SERIAL,
    Firstname TEXT,
    Surname TEXT,
    Job_Title TEXT,
    Department INTEGER DEFAULT 0,
    Phone_No TEXT,
    Salary INTEGER DEFAULT 0,
    Skills TEXT,
    Last_Updated DATE
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Complaint_Outcome"                                      */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Complaint_Outcome (
    complaint_outcome_code TEXT,
    complaint_outcome_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Complaint_Status"                                       */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Complaint_Status (
    complaint_status_code TEXT,
    compaint_status_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Complaint_Topic"                                        */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Complaint_Topic (
    complaint_type_code TEXT,
    complaint_type_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Departments"                                            */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Departments (
    dept_code TEXT,
    dept_name TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Employee_Role"                                          */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Employee_Role (
    role_code TEXT,
    role_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Skill_Codes"                                            */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Skill_Codes (
    skill_code TEXT,
    skill_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Training_Courses"                                       */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Training_Courses (
    course_code TEXT,
    course_description TEXT
);
