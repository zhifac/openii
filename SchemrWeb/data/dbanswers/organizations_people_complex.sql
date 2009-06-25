/* ---------------------------------------------------------------------- */
/* Script generated with: DeZign for Databases v5.2.0                     */
/* Target DBMS:           PostgreSQL 8                                    */
/* Project file:          Project1.dez                                    */
/* Project name:                                                          */
/* Author:                                                                */
/* Script type:           Database creation script                        */
/* Created on:            2009-06-24 19:32                                */
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
    line_1_number_building TEXT,
    line_2_number_street TEXT,
    line_3_area_locality TEXT,
    city_town TEXT,
    state_province_county TEXT,
    country TEXT,
    other_address_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Events"                                                     */
/* ---------------------------------------------------------------------- */

CREATE TABLE Events (
    event_id SERIAL,
    organisation_id NUMERIC,
    event_type_code TEXT,
    event_from_date DATE,
    event_to_date DATE,
    event_name TEXT,
    event_objective TEXT,
    event_results TEXT,
    event_description TEXT,
    other_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Organisation_Addresses"                                     */
/* ---------------------------------------------------------------------- */

CREATE TABLE Organisation_Addresses (
    organisation_id NUMERIC,
    address_id NUMERIC,
    from_date DATE,
    to_date DATE
);

/* ---------------------------------------------------------------------- */
/* Add table "Organisation_Relationships"                                 */
/* ---------------------------------------------------------------------- */

CREATE TABLE Organisation_Relationships (
    organisation_1_id NUMERIC,
    organisation_2_id NUMERIC,
    relationship_from_date DATE,
    relationship_to_date DATE,
    relationship_type_code TEXT,
    other_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Organisations"                                              */
/* ---------------------------------------------------------------------- */

CREATE TABLE Organisations (
    organisation_id SERIAL,
    organisation_type_code TEXT,
    organisation_name TEXT,
    organisation_phone TEXT,
    organisation_description TEXT,
    other_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "People"                                                     */
/* ---------------------------------------------------------------------- */

CREATE TABLE People (
    person_id SERIAL,
    address_id NUMERIC,
    organisation_id NUMERIC,
    from_date DATE,
    people_role_code TEXT,
    to_date DATE,
    first_name TEXT,
    middle_name TEXT,
    last_name TEXT,
    gender TEXT,
    date_of_birth DATE,
    other_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "People_Addresses"                                           */
/* ---------------------------------------------------------------------- */

CREATE TABLE People_Addresses (
    address_id NUMERIC,
    person_id NUMERIC,
    from_date DATE,
    to_date DATE
);

/* ---------------------------------------------------------------------- */
/* Add table "People_Skills"                                              */
/* ---------------------------------------------------------------------- */

CREATE TABLE People_Skills (
    person_id NUMERIC,
    skill_code TEXT,
    skill_level TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Event_Types"                                            */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Event_Types (
    event_type_code SERIAL,
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
/* Add table "Ref_People_Roles"                                           */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_People_Roles (
    people_role_code TEXT,
    people_role_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Relationship_Types"                                     */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Relationship_Types (
    relationship_type_code TEXT,
    relationship_type_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Skill_Categories"                                       */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Skill_Categories (
    skill_category_code TEXT,
    skill_category_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Skills"                                                 */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Skills (
    skill_code TEXT,
    skill_description TEXT,
    skill_category_code TEXT
);
