/* ---------------------------------------------------------------------- */
/* Script generated with: DeZign for Databases v5.2.0                     */
/* Target DBMS:           PostgreSQL 8                                    */
/* Project file:          Project1.dez                                    */
/* Project name:                                                          */
/* Author:                                                                */
/* Script type:           Database creation script                        */
/* Created on:            2009-06-24 17:10                                */
/* Model version:         Version 2009-06-24                              */
/* ---------------------------------------------------------------------- */


/* ---------------------------------------------------------------------- */
/* Tables                                                                 */
/* ---------------------------------------------------------------------- */

/* ---------------------------------------------------------------------- */
/* Add table "Breed_Types"                                                */
/* ---------------------------------------------------------------------- */

CREATE TABLE Breed_Types (
    breed_code TEXT  NOT NULL,
    type_code TEXT  NOT NULL,
    type_description TEXT,
    CONSTRAINT PrimaryKey PRIMARY KEY (breed_code, type_code)
);

/* ---------------------------------------------------------------------- */
/* Add table "Breeds"                                                     */
/* ---------------------------------------------------------------------- */

CREATE TABLE Breeds (
    breed_code TEXT  NOT NULL,
    breed_name TEXT,
    CONSTRAINT PK_Breeds PRIMARY KEY (breed_code)
);

/* ---------------------------------------------------------------------- */
/* Add table "Customer_Types"                                             */
/* ---------------------------------------------------------------------- */

CREATE TABLE Customer_Types (
    customer_type_code TEXT  NOT NULL,
    customer_type_description TEXT,
    CONSTRAINT PK_Customer_Types PRIMARY KEY (customer_type_code)
);

/* ---------------------------------------------------------------------- */
/* Add table "Families"                                                   */
/* ---------------------------------------------------------------------- */

CREATE TABLE Families (
    family_id SERIAL  NOT NULL,
    customer_type_code TEXT  NOT NULL,
    family_name TEXT,
    street TEXT,
    city TEXT,
    state TEXT,
    zip_code TEXT,
    desired_criteria TEXT,
    contact_name TEXT,
    contact_email TEXT,
    contact_phone TEXT,
    contact_cell TEXT,
    CONSTRAINT PK_Families PRIMARY KEY (family_id)
);

/* ---------------------------------------------------------------------- */
/* Add table "Medical_Treatments"                                         */
/* ---------------------------------------------------------------------- */

CREATE TABLE Medical_Treatments (
    pet_id INTEGER  NOT NULL,
    treatment_sequence INTEGER  NOT NULL,
    date_of_treatment DATE,
    treatment_type_code TEXT  NOT NULL,
    treatment_details TEXT,
    CONSTRAINT PK_Medical_Treatments PRIMARY KEY (pet_id, treatment_sequence)
);

/* ---------------------------------------------------------------------- */
/* Add table "Pets"                                                       */
/* ---------------------------------------------------------------------- */

CREATE TABLE Pets (
    pet_id SERIAL  NOT NULL,
    breed_code TEXT,
    current_vaccinations_yn TEXT,
    sprayed_neutered_yn TEXT,
    type_code TEXT,
    name TEXT,
    age TEXT,
    date_of_birth DATE,
    gender TEXT,
    color TEXT,
    date_arrived_in_shelter DATE,
    date_adopted DATE,
    CONSTRAINT PK_Pets PRIMARY KEY (pet_id)
);

/* ---------------------------------------------------------------------- */
/* Add table "Pets_Adopted"                                               */
/* ---------------------------------------------------------------------- */

CREATE TABLE Pets_Adopted (
    family_id INTEGER  NOT NULL,
    pet_id INTEGER  NOT NULL,
    date_adopted DATE  NOT NULL,
    date_returned DATE,
    thank_you_letter_sent_yn TEXT,
    comments TEXT,
    CONSTRAINT PK_Pets_Adopted PRIMARY KEY (family_id, pet_id, date_adopted)
);

/* ---------------------------------------------------------------------- */
/* Add table "Treatment_Types"                                            */
/* ---------------------------------------------------------------------- */

CREATE TABLE Treatment_Types (
    treatment_type_code TEXT  NOT NULL,
    treatment_type_description TEXT,
    CONSTRAINT PK_Treatment_Types PRIMARY KEY (treatment_type_code)
);

/* ---------------------------------------------------------------------- */
/* Foreign key constraints                                                */
/* ---------------------------------------------------------------------- */

ALTER TABLE Breed_Types ADD CONSTRAINT Rel_B66767C4_E0CB_4887 
    FOREIGN KEY (breed_code) REFERENCES Breeds (breed_code);

ALTER TABLE Breed_Types ADD CONSTRAINT Rel_BEA8F98B_CB4C_42FE 
    FOREIGN KEY (breed_code) REFERENCES Breeds (breed_code);

ALTER TABLE Families ADD CONSTRAINT Rel_8B28BBE8_4998_4F46 
    FOREIGN KEY (customer_type_code) REFERENCES Customer_Types (customer_type_code);

ALTER TABLE Medical_Treatments ADD CONSTRAINT Rel_A1357F34_834B_43F1 
    FOREIGN KEY (pet_id) REFERENCES Pets (pet_id);

ALTER TABLE Medical_Treatments ADD CONSTRAINT Rel_BA262E68_1540_4EDA 
    FOREIGN KEY (treatment_type_code) REFERENCES Treatment_Types (treatment_type_code);

ALTER TABLE Pets ADD CONSTRAINT Rel_8E31BC89_D93C_4304 
    FOREIGN KEY (breed_code, type_code) REFERENCES Breed_Types (breed_code,type_code);

ALTER TABLE Pets_Adopted ADD CONSTRAINT Rel_A8814921_FDED_4A56 
    FOREIGN KEY (pet_id) REFERENCES Pets (pet_id);

ALTER TABLE Pets_Adopted ADD CONSTRAINT Rel_FF198FE7_28B4_46EA 
    FOREIGN KEY (family_id) REFERENCES Families (family_id);
