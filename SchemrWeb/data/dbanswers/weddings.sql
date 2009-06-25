/* ---------------------------------------------------------------------- */
/* Script generated with: DeZign for Databases v5.2.0                     */
/* Target DBMS:           PostgreSQL 8                                    */
/* Project file:          Project1.dez                                    */
/* Project name:                                                          */
/* Author:                                                                */
/* Script type:           Database creation script                        */
/* Created on:            2009-06-24 19:41                                */
/* Model version:         Version 2009-06-24                              */
/* ---------------------------------------------------------------------- */


/* ---------------------------------------------------------------------- */
/* Tables                                                                 */
/* ---------------------------------------------------------------------- */

/* ---------------------------------------------------------------------- */
/* Add table "Events"                                                     */
/* ---------------------------------------------------------------------- */

CREATE TABLE Events (
    event_id SERIAL,
    event_type_code TEXT,
    event_from_date DATE,
    place_id NUMERIC,
    event_to_date DATE,
    wedding_id NUMERIC,
    event_name TEXT,
    event_description TEXT,
    other_event_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Guest_List"                                                 */
/* ---------------------------------------------------------------------- */

CREATE TABLE Guest_List (
    wedding_id NUMERIC,
    person_id NUMERIC,
    role_name TEXT,
    church_yn TEXT,
    reception_yn TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "People"                                                     */
/* ---------------------------------------------------------------------- */

CREATE TABLE People (
    person_id SERIAL,
    bride_or_groom_side TEXT,
    group_leader_person_id INTEGER DEFAULT 0,
    last_name TEXT,
    first_name TEXT,
    gender TEXT,
    person_address TEXT,
    other_person_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "People_at_Events"                                           */
/* ---------------------------------------------------------------------- */

CREATE TABLE People_at_Events (
    event_id NUMERIC,
    person_id NUMERIC,
    role_code TEXT,
    other_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Places"                                                     */
/* ---------------------------------------------------------------------- */

CREATE TABLE Places (
    place_id SERIAL,
    place_name TEXT,
    place_address TEXT,
    place_type_code TEXT,
    place_description TEXT,
    other_place_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Roles"                                                  */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Roles (
    role_code TEXT,
    role_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Services"                                               */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Services (
    service_code TEXT,
    service_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Service_Suppliers"                                          */
/* ---------------------------------------------------------------------- */

CREATE TABLE Service_Suppliers (
    service_code TEXT,
    supplier_code TEXT,
    from_date DATE,
    to_date DATE,
    supplier_svc_cost TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Suppliers"                                                  */
/* ---------------------------------------------------------------------- */

CREATE TABLE Suppliers (
    supplier_code TEXT,
    supplier_name TEXT,
    comments TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Wedding_Services"                                           */
/* ---------------------------------------------------------------------- */

CREATE TABLE Wedding_Services (
    wedding_id NUMERIC,
    service_code TEXT,
    supplier_code TEXT,
    datetime_supplied DATE,
    estimated_cost NUMERIC,
    actual_cost NUMERIC
);

/* ---------------------------------------------------------------------- */
/* Add table "Weddings"                                                   */
/* ---------------------------------------------------------------------- */

CREATE TABLE Weddings (
    wedding_id SERIAL,
    place_id NUMERIC,
    wedding_date DATE,
    other_wedding_details TEXT
);
