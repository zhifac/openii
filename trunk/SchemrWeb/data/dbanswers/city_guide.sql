/* ---------------------------------------------------------------------- */
/* Script generated with: DeZign for Databases v5.2.0                     */
/* Target DBMS:           PostgreSQL 8                                    */
/* Project file:          Project1.dez                                    */
/* Project name:                                                          */
/* Author:                                                                */
/* Script type:           Database creation script                        */
/* Created on:            2009-06-24 17:58                                */
/* Model version:         Version 2009-06-24                              */
/* ---------------------------------------------------------------------- */


/* ---------------------------------------------------------------------- */
/* Tables                                                                 */
/* ---------------------------------------------------------------------- */

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Hotel_Star_Ratings"                                     */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Hotel_Star_Ratings (
    star_rating_code TEXT,
    star_rating_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_TOI_Categories"                                         */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_TOI_Categories (
    toi_category_code TEXT,
    toi_category_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_TOI_Types"                                              */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_TOI_Types (
    toi_type_code TEXT,
    toi_type_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Types_of_Food"                                          */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Types_of_Food (
    food_type_code TEXT,
    food_type_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Things_of_Interest"                                         */
/* ---------------------------------------------------------------------- */

CREATE TABLE Things_of_Interest (
    toi_id SERIAL,
    toi_category_code TEXT,
    toi_type_code TEXT,
    hotel_star_rating_code TEXT,
    restaurant_food_type_code TEXT,
    pets_allowed_yn TEXT,
    no_smoking_area_yn TEXT,
    contact_person_first_name TEXT,
    contact_person_last_name TEXT,
    contact_person_gender TEXT,
    contact_person_role TEXT,
    event_date DATE,
    event_oneoff_or_recurring TEXT,
    hotel_price_range TEXT,
    toi_name TEXT,
    toi_description TEXT,
    toi_address TEXT,
    toi_email TEXT,
    toi_how_to_get_there TEXT,
    toi_opening_hours TEXT,
    toi_phone_number TEXT,
    toi_picture_filename TEXT,
    other_toi_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Visitors_Comments"                                          */
/* ---------------------------------------------------------------------- */

CREATE TABLE Visitors_Comments (
    comment_id SERIAL,
    toi_id INTEGER,
    recommended_yn TEXT,
    comment_contributed_by TEXT,
    comment_date DATE,
    comment_details TEXT
);
