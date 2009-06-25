/* ---------------------------------------------------------------------- */
/* Script generated with: DeZign for Databases v5.2.0                     */
/* Target DBMS:           PostgreSQL 8                                    */
/* Project file:          Project1.dez                                    */
/* Project name:                                                          */
/* Author:                                                                */
/* Script type:           Database creation script                        */
/* Created on:            2009-06-24 19:34                                */
/* Model version:         Version 2009-06-24                              */
/* ---------------------------------------------------------------------- */


/* ---------------------------------------------------------------------- */
/* Tables                                                                 */
/* ---------------------------------------------------------------------- */

/* ---------------------------------------------------------------------- */
/* Add table "Projects"                                                   */
/* ---------------------------------------------------------------------- */

CREATE TABLE Projects (
    project_id SERIAL,
    project_name TEXT,
    project_start_date DATE,
    project_end_date DATE,
    project_description TEXT,
    other_project_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Calendar"                                               */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Calendar (
    day_date DATE,
    business_day_yn TEXT,
    day_number INTEGER,
    period_id INTEGER
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Resource_Types"                                         */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Resource_Types (
    resource_type_code TEXT,
    resource_type_name TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Resource_Usage"                                             */
/* ---------------------------------------------------------------------- */

CREATE TABLE Resource_Usage (
    project_id INTEGER,
    period_id DATE,
    resource_id INTEGER,
    total_resource_usage NUMERIC
);

/* ---------------------------------------------------------------------- */
/* Add table "Resources"                                                  */
/* ---------------------------------------------------------------------- */

CREATE TABLE Resources (
    resource_id SERIAL,
    resource_type_code TEXT,
    resource_name TEXT,
    other_resource_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Resources_on_Projects"                                      */
/* ---------------------------------------------------------------------- */

CREATE TABLE Resources_on_Projects (
    resources_on_project_id SERIAL,
    project_id INTEGER,
    resource_id INTEGER,
    from_datetime DATE,
    to_datetime DATE,
    hourly_usage_rate NUMERIC
);
