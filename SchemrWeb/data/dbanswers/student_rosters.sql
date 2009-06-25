/* ---------------------------------------------------------------------- */
/* Script generated with: DeZign for Databases v5.2.0                     */
/* Target DBMS:           PostgreSQL 8                                    */
/* Project file:          Project1.dez                                    */
/* Project name:                                                          */
/* Author:                                                                */
/* Script type:           Database creation script                        */
/* Created on:            2009-06-24 19:38                                */
/* Model version:         Version 2009-06-24                              */
/* ---------------------------------------------------------------------- */


/* ---------------------------------------------------------------------- */
/* Tables                                                                 */
/* ---------------------------------------------------------------------- */

/* ---------------------------------------------------------------------- */
/* Add table "CLASSES"                                                    */
/* ---------------------------------------------------------------------- */

CREATE TABLE CLASSES (
    ClassID SERIAL,
    CourseID INTEGER,
    RoomID TEXT,
    TeacherNr INTEGER,
    ClassDescription TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "COURSES"                                                    */
/* ---------------------------------------------------------------------- */

CREATE TABLE COURSES (
    CourseID SERIAL,
    SubjectID INTEGER,
    LevelCode TEXT,
    CourseName TEXT,
    eg_French TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "LEVELS"                                                     */
/* ---------------------------------------------------------------------- */

CREATE TABLE LEVELS (
    LevelCode TEXT,
    LevelName TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "PERIODS"                                                    */
/* ---------------------------------------------------------------------- */

CREATE TABLE PERIODS (
    PeriodID INTEGER,
    PeriodName TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "ROOMS"                                                      */
/* ---------------------------------------------------------------------- */

CREATE TABLE ROOMS (
    RoomNr TEXT,
    Location TEXT,
    Description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "ROSTER"                                                     */
/* ---------------------------------------------------------------------- */

CREATE TABLE ROSTER (
    ClassID INTEGER,
    PeriodID INTEGER,
    StudentID INTEGER,
    Attended_YN TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "STUDENTS"                                                   */
/* ---------------------------------------------------------------------- */

CREATE TABLE STUDENTS (
    StudentID SERIAL,
    First_Name TEXT,
    Last_Name TEXT,
    Middle_Name TEXT,
    Date_of_Birth DATE,
    Gender TEXT,
    Other_Details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "SUBJECTS"                                                   */
/* ---------------------------------------------------------------------- */

CREATE TABLE SUBJECTS (
    SubjectID SERIAL,
    SubjectName TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "TEACHERS"                                                   */
/* ---------------------------------------------------------------------- */

CREATE TABLE TEACHERS (
    TeacherNr SERIAL,
    FirstName TEXT,
    MiddleName TEXT,
    LastName TEXT,
    Gender TEXT,
    DateAppointed DATE,
    DateLeft DATE,
    OtherDetails TEXT
);
