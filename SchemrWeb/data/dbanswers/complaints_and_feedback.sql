/* ---------------------------------------------------------------------- */
/* Script generated with: DeZign for Databases v5.2.0                     */
/* Target DBMS:           PostgreSQL 8                                    */
/* Project file:          Project1.dez                                    */
/* Project name:                                                          */
/* Author:                                                                */
/* Script type:           Database creation script                        */
/* Created on:            2009-06-24 18:01                                */
/* Model version:         Version 2009-06-24                              */
/* ---------------------------------------------------------------------- */


/* ---------------------------------------------------------------------- */
/* Tables                                                                 */
/* ---------------------------------------------------------------------- */

/* ---------------------------------------------------------------------- */
/* Add table "Action_Types"                                               */
/* ---------------------------------------------------------------------- */

CREATE TABLE Action_Types (
    action_type_code TEXT,
    action_type_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Actions"                                                    */
/* ---------------------------------------------------------------------- */

CREATE TABLE Actions (
    action_id SERIAL,
    action_by_staff_id INTEGER,
    action_date DATE,
    action_type_code TEXT,
    feedback_id INTEGER,
    action_results TEXT,
    action_description TEXT,
    other_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Addresses"                                                  */
/* ---------------------------------------------------------------------- */

CREATE TABLE Addresses (
    address_id SERIAL,
    line_1 TEXT,
    line_2 TEXT,
    line_3 TEXT,
    city_town TEXT,
    state_province_county TEXT,
    country TEXT,
    other_address_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Feedback"                                                   */
/* ---------------------------------------------------------------------- */

CREATE TABLE Feedback (
    feedback_id SERIAL,
    organisation_id INTEGER,
    feedback_topic_code TEXT,
    feedback_date DATE,
    person_id INTEGER,
    feedback_type_code TEXT,
    feedback_outcome_code TEXT,
    feedback_status_code TEXT,
    feedback_results TEXT,
    feedback_comment TEXT,
    other_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Feedback_Outcome_Codes"                                     */
/* ---------------------------------------------------------------------- */

CREATE TABLE Feedback_Outcome_Codes (
    feedback_outcome_code TEXT,
    feedback_outcome_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Feedback_Status_Codes"                                      */
/* ---------------------------------------------------------------------- */

CREATE TABLE Feedback_Status_Codes (
    feedback_status_code TEXT,
    feedback_status_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Feedback_Topics"                                            */
/* ---------------------------------------------------------------------- */

CREATE TABLE Feedback_Topics (
    feedback_topic_code TEXT,
    feedback_topic_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Feedback_Types"                                             */
/* ---------------------------------------------------------------------- */

CREATE TABLE Feedback_Types (
    feedback_type_code TEXT,
    feedback_type_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Organisations"                                              */
/* ---------------------------------------------------------------------- */

CREATE TABLE Organisations (
    organisation_id SERIAL,
    organisation_name TEXT,
    parent_organisation_id INTEGER,
    address_id INTEGER,
    organisation_phone TEXT,
    organisation_email TEXT,
    organisation_description TEXT,
    other_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "People"                                                     */
/* ---------------------------------------------------------------------- */

CREATE TABLE People (
    person_id SERIAL,
    address_id INTEGER,
    from_date DATE,
    to_date DATE,
    first_name TEXT,
    middle_name TEXT,
    last_name TEXT,
    gender TEXT,
    date_of_birth DATE,
    other_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Staff"                                                      */
/* ---------------------------------------------------------------------- */

CREATE TABLE Staff (
    staff_id SERIAL,
    organisation_id INTEGER,
    job_title TEXT,
    email TEXT,
    first_name TEXT,
    phone_number TEXT,
    last_name TEXT,
    gender TEXT,
    date_of_birth DATE,
    other_details TEXT
);
