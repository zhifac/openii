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
/* Add table "Blogs"                                                      */
/* ---------------------------------------------------------------------- */

CREATE TABLE Blogs (
    blog_id SERIAL,
    my_id INTEGER,
    blog_host TEXT,
    blog_start_date DATE,
    blog_url TEXT,
    blog_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Contact_Role"                                               */
/* ---------------------------------------------------------------------- */

CREATE TABLE Contact_Role (
    role_code TEXT,
    role_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Contacts"                                                   */
/* ---------------------------------------------------------------------- */

CREATE TABLE Contacts (
    contact_id SERIAL,
    customer_id INTEGER,
    role_code TEXT,
    date_contact_from DATE,
    email_address TEXT,
    web_site TEXT,
    salutation TEXT,
    contact_name TEXT,
    job_title TEXT,
    department TEXT,
    work_phone TEXT,
    cell_mobile_phone TEXT,
    fax_number TEXT,
    other_contact_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Me"                                                         */
/* ---------------------------------------------------------------------- */

CREATE TABLE Me (
    my_id SERIAL,
    first_name TEXT,
    last_name TEXT,
    other_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Music"                                                      */
/* ---------------------------------------------------------------------- */

CREATE TABLE Music (
    music_id SERIAL,
    my_id INTEGER,
    performers TEXT,
    music_name TEXT,
    music_description TEXT,
    playing_time TEXT,
    music_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "MyMovements"                                                */
/* ---------------------------------------------------------------------- */

CREATE TABLE MyMovements (
    movement_id SERIAL,
    my_id INTEGER,
    movement_date DATE,
    location_from TEXT,
    location_to TEXT,
    movement_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "MyProfiles"                                                 */
/* ---------------------------------------------------------------------- */

CREATE TABLE MyProfiles (
    profile_id SERIAL,
    my_id INTEGER,
    my_profile_name TEXT,
    my_profile_gender TEXT,
    profile_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Photos"                                                     */
/* ---------------------------------------------------------------------- */

CREATE TABLE Photos (
    photo_id SERIAL,
    my_id INTEGER,
    photo_title TEXT,
    photo_filename TEXT,
    other_photo_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Videos"                                                     */
/* ---------------------------------------------------------------------- */

CREATE TABLE Videos (
    video_id SERIAL,
    my_id INTEGER,
    playing_time TEXT,
    video_format TEXT,
    video_name TEXT,
    video_description TEXT,
    other_details TEXT
);
