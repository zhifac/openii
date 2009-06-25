/* ---------------------------------------------------------------------- */
/* Script generated with: DeZign for Databases v5.2.0                     */
/* Target DBMS:           PostgreSQL 8                                    */
/* Project file:          Project1.dez                                    */
/* Project name:                                                          */
/* Author:                                                                */
/* Script type:           Database creation script                        */
/* Created on:            2009-06-24 19:20                                */
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
    city_town TEXT,
    state_province_county TEXT,
    country TEXT,
    other_address_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Addresses_Events"                                           */
/* ---------------------------------------------------------------------- */

CREATE TABLE Addresses_Events (
    address_id INTEGER,
    event_id INTEGER
);

/* ---------------------------------------------------------------------- */
/* Add table "Addresses_People"                                           */
/* ---------------------------------------------------------------------- */

CREATE TABLE Addresses_People (
    address_id INTEGER,
    person_id INTEGER
);

/* ---------------------------------------------------------------------- */
/* Add table "Events"                                                     */
/* ---------------------------------------------------------------------- */

CREATE TABLE Events (
    event_id SERIAL,
    event_type_code TEXT,
    outcome_code TEXT,
    status_code TEXT,
    event_from_date DATE,
    event_to_date DATE,
    event_name TEXT,
    event_objective TEXT,
    event_results TEXT,
    event_description TEXT,
    other_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Events_Professionals"                                       */
/* ---------------------------------------------------------------------- */

CREATE TABLE Events_Professionals (
    event_id INTEGER,
    professional_id INTEGER
);

/* ---------------------------------------------------------------------- */
/* Add table "Events_Vehicles"                                            */
/* ---------------------------------------------------------------------- */

CREATE TABLE Events_Vehicles (
    event_id INTEGER,
    vehicle_id INTEGER
);

/* ---------------------------------------------------------------------- */
/* Add table "Organisations"                                              */
/* ---------------------------------------------------------------------- */

CREATE TABLE Organisations (
    organisation_id SERIAL,
    organisation_name TEXT,
    organisation_type_code TEXT,
    organisation_phone TEXT,
    organisation_description TEXT,
    other_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Organisations_Addresses"                                    */
/* ---------------------------------------------------------------------- */

CREATE TABLE Organisations_Addresses (
    organisation_id INTEGER,
    address_id INTEGER
);

/* ---------------------------------------------------------------------- */
/* Add table "Organisations_Events"                                       */
/* ---------------------------------------------------------------------- */

CREATE TABLE Organisations_Events (
    organisation_id INTEGER,
    event_id INTEGER
);

/* ---------------------------------------------------------------------- */
/* Add table "People"                                                     */
/* ---------------------------------------------------------------------- */

CREATE TABLE People (
    person_id SERIAL,
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
/* Add table "People_in_Events"                                           */
/* ---------------------------------------------------------------------- */

CREATE TABLE People_in_Events (
    person_id INTEGER,
    event_id INTEGER,
    role_code TEXT,
    date_from DATE,
    date_to DATE
);

/* ---------------------------------------------------------------------- */
/* Add table "Professionals"                                              */
/* ---------------------------------------------------------------------- */

CREATE TABLE Professionals (
    professional_id SERIAL,
    organisation_id INTEGER,
    role_code TEXT,
    first_name TEXT,
    last_name TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Event_Outcomes"                                         */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Event_Outcomes (
    outcome_code TEXT,
    outcome_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Event_Status"                                           */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Event_Status (
    status_code TEXT,
    status_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Event_Types"                                            */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Event_Types (
    event_type_code TEXT,
    event_type_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Organisation_Types"                                     */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Organisation_Types (
    organisation_type_code TEXT,
    organisation_type_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Roles"                                                  */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Roles (
    role_code TEXT,
    role_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Vehicle_Types"                                          */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Vehicle_Types (
    vehicle_type_code TEXT,
    vehicle_type_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Vehicles"                                                   */
/* ---------------------------------------------------------------------- */

CREATE TABLE Vehicles (
    vehicle_id SERIAL,
    vehicle_type_code TEXT,
    vehicle_details TEXT
);
