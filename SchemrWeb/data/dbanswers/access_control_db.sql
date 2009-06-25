/* ---------------------------------------------------------------------- */
/* Script generated with: DeZign for Databases v5.2.0                     */
/* Target DBMS:           PostgreSQL 8                                    */
/* Project file:          Project1.dez                                    */
/* Project name:                                                          */
/* Author:                                                                */
/* Script type:           Database creation script                        */
/* Created on:            2009-06-24 14:36                                */
/* Model version:         Version 2009-06-24                              */
/* ---------------------------------------------------------------------- */


/* ---------------------------------------------------------------------- */
/* Tables                                                                 */
/* ---------------------------------------------------------------------- */

/* ---------------------------------------------------------------------- */
/* Add table "Facilities"                                                 */
/* ---------------------------------------------------------------------- */

CREATE TABLE Facilities (
    facility_id SERIAL  NOT NULL,
    facility_type_code TEXT  NOT NULL,
    access_count INTEGER,
    facility_name TEXT,
    facility_description TEXT,
    other_details TEXT,
    CONSTRAINT PK_Facilities PRIMARY KEY (facility_id)
);

/* ---------------------------------------------------------------------- */
/* Add table "Facility_Functional_Areas"                                  */
/* ---------------------------------------------------------------------- */

CREATE TABLE Facility_Functional_Areas (
    functional_area_code TEXT  NOT NULL,
    facility_id INTEGER  NOT NULL,
    CONSTRAINT PK_Facility_Functional_Areas PRIMARY KEY (functional_area_code, facility_id)
);

/* ---------------------------------------------------------------------- */
/* Add table "Functional_Areas"                                           */
/* ---------------------------------------------------------------------- */

CREATE TABLE Functional_Areas (
    functional_area_code TEXT  NOT NULL,
    parent_functional_area_code TEXT,
    functional_area_description TEXT  NOT NULL,
    CONSTRAINT PK_Functional_Areas PRIMARY KEY (functional_area_code)
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Facility_Types"                                         */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Facility_Types (
    facility_type_code TEXT  NOT NULL,
    facility_type_description TEXT,
    CONSTRAINT PK_Ref_Facility_Types PRIMARY KEY (facility_type_code)
);

/* ---------------------------------------------------------------------- */
/* Add table "Role_Facility_Access_Rights"                                */
/* ---------------------------------------------------------------------- */

CREATE TABLE Role_Facility_Access_Rights (
    facility_id INTEGER  NOT NULL,
    role_code TEXT  NOT NULL,
    CRUD_Value TEXT,
    CONSTRAINT PK_Role_Facility_Access_Rights PRIMARY KEY (facility_id, role_code)
);

/* ---------------------------------------------------------------------- */
/* Add table "Roles"                                                      */
/* ---------------------------------------------------------------------- */

CREATE TABLE Roles (
    role_code TEXT  NOT NULL,
    role_description TEXT,
    CONSTRAINT PK_Roles PRIMARY KEY (role_code)
);

/* ---------------------------------------------------------------------- */
/* Add table "Users"                                                      */
/* ---------------------------------------------------------------------- */

CREATE TABLE Users (
    user_id SERIAL  NOT NULL,
    role_code TEXT  NOT NULL,
    user_first_name TEXT,
    user_last_name TEXT,
    user_login TEXT,
    password TEXT,
    other_details TEXT,
    CONSTRAINT PK_Users PRIMARY KEY (user_id)
);

/* ---------------------------------------------------------------------- */
/* Foreign key constraints                                                */
/* ---------------------------------------------------------------------- */

ALTER TABLE Facilities ADD CONSTRAINT Ref_Facility_Types_Facilities 
    FOREIGN KEY (facility_type_code) REFERENCES Ref_Facility_Types (facility_type_code);

ALTER TABLE Facility_Functional_Areas ADD CONSTRAINT Facilities_Facility_Functional_Areas 
    FOREIGN KEY (facility_id) REFERENCES Facilities (facility_id);

ALTER TABLE Facility_Functional_Areas ADD CONSTRAINT Functional_Areas_Facility_Functional_Areas 
    FOREIGN KEY (functional_area_code) REFERENCES Functional_Areas (functional_area_code);

ALTER TABLE Functional_Areas ADD CONSTRAINT Functional_Areas_Functional_Areas 
    FOREIGN KEY (parent_functional_area_code) REFERENCES Functional_Areas (functional_area_code);

ALTER TABLE Role_Facility_Access_Rights ADD CONSTRAINT Facilities_Role_Facility_Access_Rights 
    FOREIGN KEY (facility_id) REFERENCES Facilities (facility_id);

ALTER TABLE Role_Facility_Access_Rights ADD CONSTRAINT Roles_Role_Facility_Access_Rights 
    FOREIGN KEY (role_code) REFERENCES Roles (role_code);

ALTER TABLE Users ADD CONSTRAINT Roles_Users 
    FOREIGN KEY (role_code) REFERENCES Roles (role_code);
