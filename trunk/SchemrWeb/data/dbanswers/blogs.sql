/* ---------------------------------------------------------------------- */
/* Script generated with: DeZign for Databases v5.2.0                     */
/* Target DBMS:           PostgreSQL 8                                    */
/* Project file:          Project1.dez                                    */
/* Project name:                                                          */
/* Author:                                                                */
/* Script type:           Database creation script                        */
/* Created on:            2009-06-24 17:30                                */
/* Model version:         Version 2009-06-24                              */
/* ---------------------------------------------------------------------- */


/* ---------------------------------------------------------------------- */
/* Tables                                                                 */
/* ---------------------------------------------------------------------- */

/* ---------------------------------------------------------------------- */
/* Add table "Blog_Comments"                                              */
/* ---------------------------------------------------------------------- */

CREATE TABLE Blog_Comments (
    entry_datetime DATE,
    blog_id NUMERIC,
    user_id NUMERIC,
    comment_datetime DATE,
    comment TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Blog_Entries"                                               */
/* ---------------------------------------------------------------------- */

CREATE TABLE Blog_Entries (
    blog_id NUMERIC,
    entry_datetime DATE,
    entry_text TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Blogs"                                                      */
/* ---------------------------------------------------------------------- */

CREATE TABLE Blogs (
    blog_id SERIAL,
    user_id NUMERIC,
    blog_created_datetime DATE,
    blog_title TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Users"                                                      */
/* ---------------------------------------------------------------------- */

CREATE TABLE Users (
    user_id SERIAL,
    user_name TEXT,
    user_nickname TEXT,
    email_address TEXT,
    password TEXT,
    other_details TEXT
);
