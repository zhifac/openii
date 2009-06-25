/* ---------------------------------------------------------------------- */
/* Script generated with: DeZign for Databases v5.2.0                     */
/* Target DBMS:           PostgreSQL 8                                    */
/* Project file:          Project1.dez                                    */
/* Project name:                                                          */
/* Author:                                                                */
/* Script type:           Database creation script                        */
/* Created on:            2009-06-24 19:37                                */
/* Model version:         Version 2009-06-24                              */
/* ---------------------------------------------------------------------- */


/* ---------------------------------------------------------------------- */
/* Tables                                                                 */
/* ---------------------------------------------------------------------- */

/* ---------------------------------------------------------------------- */
/* Add table "ADDRESSES"                                                  */
/* ---------------------------------------------------------------------- */

CREATE TABLE ADDRESSES (
    ADDRESS_ID SERIAL,
    ADDRESS_DETAILS TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "CLASSES"                                                    */
/* ---------------------------------------------------------------------- */

CREATE TABLE CLASSES (
    CLASS_ID SERIAL,
    SUBJECT_ID INTEGER,
    TEACHER_ID INTEGER,
    CLASS_CODE TEXT,
    CLASS_NAME TEXT,
    DATE_FROM DATE,
    DATE_TO DATE
);

/* ---------------------------------------------------------------------- */
/* Add table "FAMILIES"                                                   */
/* ---------------------------------------------------------------------- */

CREATE TABLE FAMILIES (
    FAMILY_ID SERIAL,
    HEAD_OF_FAMILY_PARENT_ID INTEGER,
    FAMILY_NAME TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "FAMILY_MEMBERS"                                             */
/* ---------------------------------------------------------------------- */

CREATE TABLE FAMILY_MEMBERS (
    FAMILY_MEMBER_ID SERIAL,
    FAMILY_ID INTEGER,
    PARENT_OR_STUDENT_MEMBER TEXT,
    PARENT_ID INTEGER,
    STUDENT_ID INTEGER
);

/* ---------------------------------------------------------------------- */
/* Add table "HOMEWORK"                                                   */
/* ---------------------------------------------------------------------- */

CREATE TABLE HOMEWORK (
    HOMEWORK_ID SERIAL,
    STUDENT_ID INTEGER,
    DATE_CREATED DATE,
    HOMEWORK_CONTENT TEXT,
    GRADE TEXT,
    OTHER_HOMEWORK_DETAILS TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "PARENT_ADDRESSES"                                           */
/* ---------------------------------------------------------------------- */

CREATE TABLE PARENT_ADDRESSES (
    PARENT_ID INTEGER,
    ADDRESS_ID INTEGER,
    DATE_ADDRESS_FROM DATE,
    DATE_ADDRESS_TO DATE
);

/* ---------------------------------------------------------------------- */
/* Add table "PARENTS"                                                    */
/* ---------------------------------------------------------------------- */

CREATE TABLE PARENTS (
    PARENT_ID SERIAL,
    GENDER TEXT,
    FIRST_NAME TEXT,
    MIDDLE_NAME TEXT,
    LAST_NAME TEXT,
    OTHER_PARENT_DETAILS TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "REF_PERSON_TYPE"                                            */
/* ---------------------------------------------------------------------- */

CREATE TABLE REF_PERSON_TYPE (
    PERSON_TYPE_CODE TEXT,
    PERSON_TYPE_DESCRIPTION TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "REPORTS"                                                    */
/* ---------------------------------------------------------------------- */

CREATE TABLE REPORTS (
    REPORT_ID SERIAL,
    STUDENT_ID INTEGER,
    DATE_CREATED DATE,
    REPORT_CONTENT TEXT,
    TEACHERS_COMMENTS TEXT,
    OTHER_REPORT_DETAILS TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "SCHOOLS"                                                    */
/* ---------------------------------------------------------------------- */

CREATE TABLE SCHOOLS (
    SCHOOL_ID SERIAL,
    ADDRESS_ID INTEGER,
    SCHOOL_NAME TEXT,
    SCHOOL_PRINCIPAL TEXT,
    OTHER_SCHOOL_DETAILS TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "STUDENT_CLASSES"                                            */
/* ---------------------------------------------------------------------- */

CREATE TABLE STUDENT_CLASSES (
    STUDENT_ID INTEGER,
    CLASS_ID INTEGER,
    DATE_FROM DATE,
    DATE_TO DATE
);

/* ---------------------------------------------------------------------- */
/* Add table "STUDENT_PARENTS"                                            */
/* ---------------------------------------------------------------------- */

CREATE TABLE STUDENT_PARENTS (
    STUDENT_ID INTEGER,
    PARENT_ID INTEGER
);

/* ---------------------------------------------------------------------- */
/* Add table "STUDENTS"                                                   */
/* ---------------------------------------------------------------------- */

CREATE TABLE STUDENTS (
    STUDENT_ID SERIAL,
    GENDER TEXT,
    FIRST_NAME TEXT,
    MIDDLE_NAME TEXT,
    LAST_NAME TEXT,
    DATE_OF_BIRTH DATE,
    OTHER_STUDENT_DETAILS TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "SUBJECTS"                                                   */
/* ---------------------------------------------------------------------- */

CREATE TABLE SUBJECTS (
    SUBJECT_ID SERIAL,
    SUBJECT_NAME TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "TEACHERS"                                                   */
/* ---------------------------------------------------------------------- */

CREATE TABLE TEACHERS (
    TEACHER_ID SERIAL,
    SCHOOL_ID INTEGER,
    GENDER TEXT,
    FIRST_NAME TEXT,
    MIDDLE_NAME TEXT,
    LAST_NAME TEXT,
    OTHER_TEACHER_DETAILS TEXT
);
