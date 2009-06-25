/* ---------------------------------------------------------------------- */
/* Script generated with: DeZign for Databases v5.2.0                     */
/* Target DBMS:           PostgreSQL 8                                    */
/* Project file:          Project1.dez                                    */
/* Project name:                                                          */
/* Author:                                                                */
/* Script type:           Database creation script                        */
/* Created on:            2009-06-24 19:22                                */
/* Model version:         Version 2009-06-24                              */
/* ---------------------------------------------------------------------- */


/* ---------------------------------------------------------------------- */
/* Tables                                                                 */
/* ---------------------------------------------------------------------- */

/* ---------------------------------------------------------------------- */
/* Add table "Courses"                                                    */
/* ---------------------------------------------------------------------- */

CREATE TABLE Courses (
    golf_course_code TEXT,
    golf_course_name TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Golf_Clubs"                                                 */
/* ---------------------------------------------------------------------- */

CREATE TABLE Golf_Clubs (
    golf_club_code TEXT,
    gold_club_name TEXT,
    golf_club_address TEXT,
    golf_club_other_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Holes"                                                      */
/* ---------------------------------------------------------------------- */

CREATE TABLE Holes (
    golf_course_code TEXT,
    hole_number INTEGER,
    length_in_yards INTEGER,
    par INTEGER,
    hole_comments TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Membership_Levels"                                          */
/* ---------------------------------------------------------------------- */

CREATE TABLE Membership_Levels (
    membership_level_code TEXT,
    membership_level_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Player_Golf_Club_Memberships"                               */
/* ---------------------------------------------------------------------- */

CREATE TABLE Player_Golf_Club_Memberships (
    player_id INTEGER,
    golf_club_code TEXT,
    membership_level_code TEXT,
    date_member_from DATE,
    date_member_to DATE
);

/* ---------------------------------------------------------------------- */
/* Add table "Player_Results_by_Hole"                                     */
/* ---------------------------------------------------------------------- */

CREATE TABLE Player_Results_by_Hole (
    tournament_code TEXT,
    golf_course_code TEXT,
    tournament_start_date DATE,
    hole_number INTEGER,
    player_id INTEGER,
    strokes_taken INTEGER,
    hit_fairway_yn TEXT,
    no_of_putts INTEGER,
    results_comments TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Players"                                                    */
/* ---------------------------------------------------------------------- */

CREATE TABLE Players (
    player_id SERIAL,
    player_first_name TEXT,
    player_last_name TEXT,
    handicap INTEGER,
    player_address TEXT,
    player_other_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Tournament_Participation"                                   */
/* ---------------------------------------------------------------------- */

CREATE TABLE Tournament_Participation (
    tournament_code TEXT,
    golf_course_code TEXT,
    tournament_start_date DATE,
    player_id INTEGER,
    start_date_time DATE,
    player_score INTEGER,
    result TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Tournaments"                                                */
/* ---------------------------------------------------------------------- */

CREATE TABLE Tournaments (
    tournament_code TEXT,
    tournament_name TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Tournaments_Held"                                           */
/* ---------------------------------------------------------------------- */

CREATE TABLE Tournaments_Held (
    tournament_code TEXT,
    golf_course_code TEXT,
    tournament_start_date DATE,
    tournament_sponsor TEXT,
    tournament_end_date DATE,
    tournament_other_details TEXT
);
