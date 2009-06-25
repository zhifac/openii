/* ---------------------------------------------------------------------- */
/* Script generated with: DeZign for Databases v5.2.0                     */
/* Target DBMS:           PostgreSQL 8                                    */
/* Project file:          Project1.dez                                    */
/* Project name:                                                          */
/* Author:                                                                */
/* Script type:           Database creation script                        */
/* Created on:            2009-06-24 14:41                                */
/* Model version:         Version 2009-06-24                              */
/* ---------------------------------------------------------------------- */


/* ---------------------------------------------------------------------- */
/* Tables                                                                 */
/* ---------------------------------------------------------------------- */

/* ---------------------------------------------------------------------- */
/* Add table "Actions"                                                    */
/* ---------------------------------------------------------------------- */

CREATE TABLE Actions (
    action_id SERIAL  NOT NULL,
    action_type TEXT,
    status TEXT,
    start_date DATE,
    end_date DATE,
    other_details TEXT,
    CONSTRAINT PK_Actions PRIMARY KEY (action_id)
);

/* ---------------------------------------------------------------------- */
/* Add table "Decisions"                                                  */
/* ---------------------------------------------------------------------- */

CREATE TABLE Decisions (
    decision_id SERIAL  NOT NULL,
    outcome TEXT,
    status TEXT,
    other_details TEXT,
    CONSTRAINT PK_Decisions PRIMARY KEY (decision_id)
);

/* ---------------------------------------------------------------------- */
/* Add table "Facts"                                                      */
/* ---------------------------------------------------------------------- */

CREATE TABLE Facts (
    fact_id SERIAL  NOT NULL,
    action_id INTEGER,
    decision_id INTEGER,
    person_id INTEGER,
    property_id INTEGER,
    fact_start_date DATE,
    fact_end_date DATE,
    fact_description TEXT,
    other_fact_details TEXT,
    CONSTRAINT PK_Facts PRIMARY KEY (fact_id)
);

/* ---------------------------------------------------------------------- */
/* Add table "People"                                                     */
/* ---------------------------------------------------------------------- */

CREATE TABLE People (
    person_id SERIAL  NOT NULL,
    first_name TEXT,
    middle_name TEXT,
    last_name TEXT,
    date_of_birth DATE,
    gender TEXT,
    address TEXT,
    home_phone_number TEXT,
    cell_or_mobile_number TEXT,
    email_address TEXT,
    other_details TEXT,
    CONSTRAINT PK_People PRIMARY KEY (person_id)
);

/* ---------------------------------------------------------------------- */
/* Add table "Properties"                                                 */
/* ---------------------------------------------------------------------- */

CREATE TABLE Properties (
    property_id SERIAL  NOT NULL,
    property_type TEXT,
    address_line1 TEXT,
    address_line2 TEXT,
    town_city TEXT,
    state_county TEXT,
    other_details TEXT,
    CONSTRAINT PK_Properties PRIMARY KEY (property_id)
);
