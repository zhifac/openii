/* ---------------------------------------------------------------------- */
/* Script generated with: DeZign for Databases v5.2.0                     */
/* Target DBMS:           PostgreSQL 8                                    */
/* Project file:          Project1.dez                                    */
/* Project name:                                                          */
/* Author:                                                                */
/* Script type:           Database creation script                        */
/* Created on:            2009-06-24 19:36                                */
/* Model version:         Version 2009-06-24                              */
/* ---------------------------------------------------------------------- */


/* ---------------------------------------------------------------------- */
/* Tables                                                                 */
/* ---------------------------------------------------------------------- */

/* ---------------------------------------------------------------------- */
/* Add table "Artists"                                                    */
/* ---------------------------------------------------------------------- */

CREATE TABLE Artists (
    artist_id SERIAL,
    gender TEXT,
    artist_name TEXT,
    date_of_birth DATE,
    nationality TEXT,
    other_artist_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Disks"                                                      */
/* ---------------------------------------------------------------------- */

CREATE TABLE Disks (
    disk_id SERIAL,
    disk_category_code TEXT,
    genre_code TEXT,
    cdg_number TEXT,
    date_released DATE,
    disk_title TEXT,
    other_disk_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "dummy"                                                      */
/* ---------------------------------------------------------------------- */

CREATE TABLE dummy (
    ID SERIAL,
    Field1 TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Artist_Roles"                                           */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Artist_Roles (
    role_code TEXT,
    role_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Disc_Categories"                                        */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Disc_Categories (
    disk_category_code TEXT,
    disk_category_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Genres"                                                 */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Genres (
    genre_code TEXT,
    genre_level_number INTEGER,
    parent_genre_code TEXT,
    genre_name TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Song_Artists"                                               */
/* ---------------------------------------------------------------------- */

CREATE TABLE Song_Artists (
    artist_id INTEGER,
    song_id TEXT,
    role_code TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Songs"                                                      */
/* ---------------------------------------------------------------------- */

CREATE TABLE Songs (
    internal_song_code TEXT,
    song_key TEXT,
    song_length TEXT,
    song_title TEXT,
    other_song_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Tracks"                                                     */
/* ---------------------------------------------------------------------- */

CREATE TABLE Tracks (
    disk_id INTEGER,
    track_number INTEGER,
    song_id TEXT,
    track_length TEXT
);
