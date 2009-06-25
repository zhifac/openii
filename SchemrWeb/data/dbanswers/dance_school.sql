/* ---------------------------------------------------------------------- */
/* Script generated with: DeZign for Databases v5.2.0                     */
/* Target DBMS:           PostgreSQL 8                                    */
/* Project file:          Project1.dez                                    */
/* Project name:                                                          */
/* Author:                                                                */
/* Script type:           Database creation script                        */
/* Created on:            2009-06-24 19:16                                */
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
    line_1 TEXT,
    line_2 TEXT,
    line_3 TEXT,
    town_city TEXT,
    state_province_county TEXT,
    country TEXT,
    other_address_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Classes"                                                    */
/* ---------------------------------------------------------------------- */

CREATE TABLE Classes (
    class_id SERIAL,
    class_code TEXT,
    class_name TEXT,
    class_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Exams"                                                      */
/* ---------------------------------------------------------------------- */

CREATE TABLE Exams (
    exam_id SERIAL,
    exam_date DATE,
    exam_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Exam_Result"                                            */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Exam_Result (
    exam_result_code TEXT,
    exam_result_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Grades"                                                 */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Grades (
    grade_code TEXT,
    grade_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Skills"                                                 */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Skills (
    skill_code TEXT,
    skill_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Student_Class_Attendance"                                   */
/* ---------------------------------------------------------------------- */

CREATE TABLE Student_Class_Attendance (
    class_id NUMERIC,
    student_id NUMERIC,
    class_date_time DATE
);

/* ---------------------------------------------------------------------- */
/* Add table "Student_Exams"                                              */
/* ---------------------------------------------------------------------- */

CREATE TABLE Student_Exams (
    exam_id NUMERIC,
    student_id NUMERIC,
    exam_result_code TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Student_Skills"                                             */
/* ---------------------------------------------------------------------- */

CREATE TABLE Student_Skills (
    student_id NUMERIC,
    skill_code TEXT,
    grade_code TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Students"                                                   */
/* ---------------------------------------------------------------------- */

CREATE TABLE Students (
    student_id SERIAL,
    address_id NUMERIC,
    student_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Teacher_Skills"                                             */
/* ---------------------------------------------------------------------- */

CREATE TABLE Teacher_Skills (
    teacher_id NUMERIC,
    skill_code TEXT,
    grade_code TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Teachers"                                                   */
/* ---------------------------------------------------------------------- */

CREATE TABLE Teachers (
    teacher_id SERIAL,
    address_id NUMERIC,
    date_joined_staff DATE,
    date_left_staff DATE,
    date_of_birth DATE,
    first_name TEXT,
    last_name TEXT,
    gender TEXT,
    marital_status TEXT,
    phone TEXT,
    cell_mobile_phone TEXT,
    email_address TEXT,
    other_teacher_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Teachers_Classes"                                           */
/* ---------------------------------------------------------------------- */

CREATE TABLE Teachers_Classes (
    class_id NUMERIC,
    teacher_id NUMERIC,
    class_date_time DATE
);
