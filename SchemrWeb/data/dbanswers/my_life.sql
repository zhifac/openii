/* ---------------------------------------------------------------------- */
/* Script generated with: DeZign for Databases v5.2.0                     */
/* Target DBMS:           PostgreSQL 8                                    */
/* Project file:          Project1.dez                                    */
/* Project name:                                                          */
/* Author:                                                                */
/* Script type:           Database creation script                        */
/* Created on:            2009-06-24 19:30                                */
/* Model version:         Version 2009-06-24                              */
/* ---------------------------------------------------------------------- */


/* ---------------------------------------------------------------------- */
/* Tables                                                                 */
/* ---------------------------------------------------------------------- */

/* ---------------------------------------------------------------------- */
/* Add table "Bit_Types"                                                  */
/* ---------------------------------------------------------------------- */

CREATE TABLE Bit_Types (
    bit_type_code TEXT,
    bit_type_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Bits_of_Information"                                        */
/* ---------------------------------------------------------------------- */

CREATE TABLE Bits_of_Information (
    bit_id SERIAL,
    bit_type_code TEXT,
    format_type_code TEXT,
    location_code TEXT,
    date_of_creation DATE,
    playing_time TEXT,
    title TEXT,
    description TEXT,
    folder_name TEXT,
    filename_or_url TEXT,
    other_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Event_Categories"                                           */
/* ---------------------------------------------------------------------- */

CREATE TABLE Event_Categories (
    event_category_code TEXT,
    event_category_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Format_Types"                                               */
/* ---------------------------------------------------------------------- */

CREATE TABLE Format_Types (
    format_type_code TEXT,
    format_type_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Locations"                                                  */
/* ---------------------------------------------------------------------- */

CREATE TABLE Locations (
    location_code TEXT,
    location_name TEXT,
    location_description TEXT,
    gps_reading TEXT,
    latitude TEXT,
    longtitude TEXT,
    elevation TEXT,
    source TEXT,
    other_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "MyActivities_Bits"                                          */
/* ---------------------------------------------------------------------- */

CREATE TABLE MyActivities_Bits (
    activity_bit_id SERIAL,
    event_id INTEGER,
    bit_id INTEGER,
    activity_datetime DATE,
    activity_description TEXT,
    other_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "MyCalendar"                                                 */
/* ---------------------------------------------------------------------- */

CREATE TABLE MyCalendar (
    calendar_entry_id SERIAL,
    my_id INTEGER,
    entry_datetime DATE,
    entry_name TEXT,
    other_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "MyContacts"                                                 */
/* ---------------------------------------------------------------------- */

CREATE TABLE MyContacts (
    contact_id SERIAL,
    my_id INTEGER,
    role_code TEXT,
    first_name TEXT,
    last_name TEXT,
    gender TEXT,
    city TEXT,
    email_address TEXT,
    cell_mobile_phone TEXT,
    other_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "MyEvents"                                                   */
/* ---------------------------------------------------------------------- */

CREATE TABLE MyEvents (
    event_id SERIAL,
    calendar_entry_id INTEGER,
    event_category_code TEXT,
    my_id INTEGER,
    event_date_time DATE,
    event_address TEXT,
    event_location_code TEXT,
    event_name TEXT,
    event_description TEXT,
    other_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "MyEvents_Contacts"                                          */
/* ---------------------------------------------------------------------- */

CREATE TABLE MyEvents_Contacts (
    event_id INTEGER,
    contact_id INTEGER
);

/* ---------------------------------------------------------------------- */
/* Add table "MyLife"                                                     */
/* ---------------------------------------------------------------------- */

CREATE TABLE MyLife (
    my_id SERIAL,
    first_name TEXT,
    last_name TEXT,
    nickname TEXT,
    gender TEXT,
    city TEXT,
    other_details TEXT
);
