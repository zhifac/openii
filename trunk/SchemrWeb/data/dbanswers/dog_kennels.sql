/* ---------------------------------------------------------------------- */
/* Script generated with: DeZign for Databases v5.2.0                     */
/* Target DBMS:           PostgreSQL 8                                    */
/* Project file:          Project1.dez                                    */
/* Project name:                                                          */
/* Author:                                                                */
/* Script type:           Database creation script                        */
/* Created on:            2009-06-24 19:18                                */
/* Model version:         Version 2009-06-24                              */
/* ---------------------------------------------------------------------- */


/* ---------------------------------------------------------------------- */
/* Tables                                                                 */
/* ---------------------------------------------------------------------- */

/* ---------------------------------------------------------------------- */
/* Add table "Breeds"                                                     */
/* ---------------------------------------------------------------------- */

CREATE TABLE Breeds (
    breed_code TEXT,
    breed_name TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Charges"                                                    */
/* ---------------------------------------------------------------------- */

CREATE TABLE Charges (
    charge_id SERIAL,
    charge_type TEXT,
    charge_amount MONEY,
    eg_Daily_Accommodation TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Dogs"                                                       */
/* ---------------------------------------------------------------------- */

CREATE TABLE Dogs (
    dog_id SERIAL,
    owner_id INTEGER,
    abandoned_yn TEXT,
    breed_code TEXT,
    size_code TEXT,
    name TEXT,
    age TEXT,
    date_of_birth DATE,
    gender TEXT,
    weight TEXT,
    date_arrived DATE,
    date_adopted DATE,
    date_departed DATE
);

/* ---------------------------------------------------------------------- */
/* Add table "Owners"                                                     */
/* ---------------------------------------------------------------------- */

CREATE TABLE Owners (
    owner_id SERIAL,
    first_name TEXT,
    last_name TEXT,
    street TEXT,
    city TEXT,
    state TEXT,
    zip_code TEXT,
    email_address TEXT,
    home_phone TEXT,
    cell_number TEXT,
    other_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Professionals"                                              */
/* ---------------------------------------------------------------------- */

CREATE TABLE Professionals (
    professional_id SERIAL,
    role_code TEXT,
    first_name TEXT,
    street TEXT,
    city TEXT,
    state TEXT,
    zip_code TEXT,
    last_name TEXT,
    email_address TEXT,
    home_phone TEXT,
    other_details TEXT,
    cell_number TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Professional_Role"                                      */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Professional_Role (
    role_code TEXT,
    role_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Sizes"                                                      */
/* ---------------------------------------------------------------------- */

CREATE TABLE Sizes (
    size_code TEXT,
    size_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Treatment_Types"                                            */
/* ---------------------------------------------------------------------- */

CREATE TABLE Treatment_Types (
    treatment_type_code TEXT,
    treatment_type_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Treatments"                                                 */
/* ---------------------------------------------------------------------- */

CREATE TABLE Treatments (
    treatment_id SERIAL,
    dog_id INTEGER,
    professional_id INTEGER,
    treatment_type_code TEXT,
    date_of_treatment DATE,
    cost_of_treatment MONEY,
    other_details TEXT
);
