/* ---------------------------------------------------------------------- */
/* Script generated with: DeZign for Databases v5.2.0                     */
/* Target DBMS:           PostgreSQL 8                                    */
/* Project file:          Project1.dez                                    */
/* Project name:                                                          */
/* Author:                                                                */
/* Script type:           Database creation script                        */
/* Created on:            2009-06-24 19:29                                */
/* Model version:         Version 2009-06-24                              */
/* ---------------------------------------------------------------------- */


/* ---------------------------------------------------------------------- */
/* Tables                                                                 */
/* ---------------------------------------------------------------------- */

/* ---------------------------------------------------------------------- */
/* Add table "Assigned_Books"                                             */
/* ---------------------------------------------------------------------- */

CREATE TABLE Assigned_Books (
    course_code TEXT,
    isbn TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Authors"                                                    */
/* ---------------------------------------------------------------------- */

CREATE TABLE Authors (
    author_id SERIAL,
    author_first_name TEXT,
    author_middle_name TEXT,
    author_last_name TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Books"                                                      */
/* ---------------------------------------------------------------------- */

CREATE TABLE Books (
    isbn TEXT,
    genre_code TEXT,
    number_of_copies INTEGER,
    book_title TEXT,
    book_price TEXT,
    date_of_publication DATE
);

/* ---------------------------------------------------------------------- */
/* Add table "Books_by_Author"                                            */
/* ---------------------------------------------------------------------- */

CREATE TABLE Books_by_Author (
    author_id INTEGER,
    isbn TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Books_Out_on_Loan"                                          */
/* ---------------------------------------------------------------------- */

CREATE TABLE Books_Out_on_Loan (
    book_borrowing_id SERIAL,
    isbn TEXT,
    pupil_id INTEGER,
    fine_paid_yn TEXT,
    lost_yn TEXT,
    overdue_yn TEXT,
    date_issued DATE,
    date_due_for_return DATE,
    date_returned DATE
);

/* ---------------------------------------------------------------------- */
/* Add table "Courses"                                                    */
/* ---------------------------------------------------------------------- */

CREATE TABLE Courses (
    course_code TEXT,
    course_name TEXT,
    eg French  GCSE TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Genres"                                                     */
/* ---------------------------------------------------------------------- */

CREATE TABLE Genres (
    genre_code TEXT,
    genre_name TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Pupils"                                                     */
/* ---------------------------------------------------------------------- */

CREATE TABLE Pupils (
    pupil_id SERIAL,
    first_name TEXT,
    middle_name TEXT,
    last_name TEXT,
    gender TEXT,
    user_address TEXT,
    home_phone_number TEXT,
    mobile_phone_number TEXT,
    email_address TEXT,
    other_pupil_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Pupils_on_Courses"                                          */
/* ---------------------------------------------------------------------- */

CREATE TABLE Pupils_on_Courses (
    pupil_id INTEGER,
    course_code TEXT,
    date_from DATE,
    date_to DATE
);

/* ---------------------------------------------------------------------- */
/* Add table "Students"                                                   */
/* ---------------------------------------------------------------------- */

CREATE TABLE Students (
    student_id INTEGER,
    first_name TEXT,
    middle_name TEXT,
    last_name TEXT,
    gender TEXT,
    user_address TEXT,
    home_phone_number TEXT,
    cellphone_number TEXT,
    email_address TEXT,
    other_student_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Subjects"                                                   */
/* ---------------------------------------------------------------------- */

CREATE TABLE Subjects (
    subject_code TEXT,
    subject_name TEXT
);
