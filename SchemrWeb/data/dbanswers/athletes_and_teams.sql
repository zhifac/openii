/* ---------------------------------------------------------------------- */
/* Script generated with: DeZign for Databases v5.2.0                     */
/* Target DBMS:           PostgreSQL 8                                    */
/* Project file:          Project1.dez                                    */
/* Project name:                                                          */
/* Author:                                                                */
/* Script type:           Database creation script                        */
/* Created on:            2009-06-24 17:14                                */
/* Model version:         Version 2009-06-24                              */
/* ---------------------------------------------------------------------- */


/* ---------------------------------------------------------------------- */
/* Tables                                                                 */
/* ---------------------------------------------------------------------- */

/* ---------------------------------------------------------------------- */
/* Add table "Divisions"                                                  */
/* ---------------------------------------------------------------------- */

CREATE TABLE Divisions (
    division_code TEXT,
    division_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Event_Locations"                                            */
/* ---------------------------------------------------------------------- */

CREATE TABLE Event_Locations (
    location_id INTEGER,
    event_id TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Events"                                                     */
/* ---------------------------------------------------------------------- */

CREATE TABLE Events (
    event_id SERIAL,
    sport_code TEXT,
    event_name TEXT,
    event_location_id INTEGER,
    start_date DATE,
    end_date DATE,
    other_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Locations"                                                  */
/* ---------------------------------------------------------------------- */

CREATE TABLE Locations (
    location_id SERIAL,
    location_name TEXT,
    location_address TEXT,
    other_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Schools"                                                    */
/* ---------------------------------------------------------------------- */

CREATE TABLE Schools (
    school_id SERIAL,
    school_name TEXT,
    school_address TEXT,
    other_school_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Sports"                                                     */
/* ---------------------------------------------------------------------- */

CREATE TABLE Sports (
    sport_code TEXT,
    sport_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Student_Athletes"                                           */
/* ---------------------------------------------------------------------- */

CREATE TABLE Student_Athletes (
    student_id SERIAL,
    points_awarded_to_date INTEGER,
    first_name TEXT,
    middle_name TEXT,
    last_name TEXT,
    date_of_birth DATE,
    gender TEXT,
    student_address TEXT,
    other_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Student_Letters"                                            */
/* ---------------------------------------------------------------------- */

CREATE TABLE Student_Letters (
    student_id INTEGER,
    sport_code TEXT,
    date_awarded DATE
);

/* ---------------------------------------------------------------------- */
/* Add table "Students_in_Events"                                         */
/* ---------------------------------------------------------------------- */

CREATE TABLE Students_in_Events (
    team_id INTEGER,
    student_id INTEGER,
    event_id INTEGER,
    student_resullt TEXT,
    student_points_awarded INTEGER
);

/* ---------------------------------------------------------------------- */
/* Add table "Students_in_Teams"                                          */
/* ---------------------------------------------------------------------- */

CREATE TABLE Students_in_Teams (
    team_id INTEGER,
    student_id INTEGER
);

/* ---------------------------------------------------------------------- */
/* Add table "Teams"                                                      */
/* ---------------------------------------------------------------------- */

CREATE TABLE Teams (
    team_id SERIAL,
    gender TEXT,
    coach_name TEXT,
    division_code TEXT,
    sport_code TEXT,
    team_date DATE,
    team_name TEXT,
    team_description TEXT,
    other_details TEXT
);
