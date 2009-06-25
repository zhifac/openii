/* ---------------------------------------------------------------------- */
/* Script generated with: DeZign for Databases v5.2.0                     */
/* Target DBMS:           PostgreSQL 8                                    */
/* Project file:          Project1.dez                                    */
/* Project name:                                                          */
/* Author:                                                                */
/* Script type:           Database creation script                        */
/* Created on:            2009-06-24 19:42                                */
/* Model version:         Version 2009-06-24                              */
/* ---------------------------------------------------------------------- */


/* ---------------------------------------------------------------------- */
/* Tables                                                                 */
/* ---------------------------------------------------------------------- */

/* ---------------------------------------------------------------------- */
/* Add table "Animal_Checkups"                                            */
/* ---------------------------------------------------------------------- */

CREATE TABLE Animal_Checkups (
    animal_id INTEGER,
    checkup_date DATE,
    checkup_height TEXT,
    checkup_weight TEXT,
    checkup_length TEXT,
    general_health TEXT,
    other_checkup_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Animal_Specific_Dietary_Requirements"                       */
/* ---------------------------------------------------------------------- */

CREATE TABLE Animal_Specific_Dietary_Requirements (
    animal_id INTEGER,
    food_item_id INTEGER,
    daily_requirements_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Animals_in_Zoo"                                             */
/* ---------------------------------------------------------------------- */

CREATE TABLE Animals_in_Zoo (
    animal_id SERIAL,
    animal_name TEXT,
    gender TEXT,
    birth_date DATE,
    acquired_from TEXT,
    arrival_date DATE,
    departure_date DATE,
    height_on_arrival TEXT,
    weight_on_arrival TEXT,
    other_animal_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Breed_Dietary_Requirements"                                 */
/* ---------------------------------------------------------------------- */

CREATE TABLE Breed_Dietary_Requirements (
    animal_breed_code TEXT,
    food_item_id INTEGER,
    daily_requirement TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Reference_Animal_Breeds"                                    */
/* ---------------------------------------------------------------------- */

CREATE TABLE Reference_Animal_Breeds (
    animal_breed_code TEXT,
    animal_breed_full_name TEXT,
    animal_habitat TEXT,
    countries_of_origin TEXT,
    photo_filename TEXT,
    animal_breed_other_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Reference_Animal_Breeds_Animals_in_Zoo"                     */
/* ---------------------------------------------------------------------- */

CREATE TABLE Reference_Animal_Breeds_Animals_in_Zoo (
    animal_in_zoo_id INTEGER,
    animal_breed_code TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Reference_Food_Category"                                    */
/* ---------------------------------------------------------------------- */

CREATE TABLE Reference_Food_Category (
    food_category_code TEXT,
    food_category_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Reference_Food_Items"                                       */
/* ---------------------------------------------------------------------- */

CREATE TABLE Reference_Food_Items (
    food_item_id INTEGER,
    food_category_code TEXT,
    food_item_code TEXT,
    food_item_description TEXT
);
