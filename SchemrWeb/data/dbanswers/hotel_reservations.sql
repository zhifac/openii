/* ---------------------------------------------------------------------- */
/* Script generated with: DeZign for Databases v5.2.0                     */
/* Target DBMS:           PostgreSQL 8                                    */
/* Project file:          Project1.dez                                    */
/* Project name:                                                          */
/* Author:                                                                */
/* Script type:           Database creation script                        */
/* Created on:            2009-06-24 19:25                                */
/* Model version:         Version 2009-06-24                              */
/* ---------------------------------------------------------------------- */


/* ---------------------------------------------------------------------- */
/* Tables                                                                 */
/* ---------------------------------------------------------------------- */

/* ---------------------------------------------------------------------- */
/* Add table "Agent_Reservations"                                         */
/* ---------------------------------------------------------------------- */

CREATE TABLE Agent_Reservations (
    agent_Reservation_ID SERIAL,
    agent_ID INTEGER,
    total_Guest_Count INTEGER,
    agent_Reservation_Made_Date DATE
);

/* ---------------------------------------------------------------------- */
/* Add table "Agents"                                                     */
/* ---------------------------------------------------------------------- */

CREATE TABLE Agents (
    agent_ID SERIAL,
    agent_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Bookings"                                                   */
/* ---------------------------------------------------------------------- */

CREATE TABLE Bookings (
    booking_ID SERIAL,
    agent_Reservation_ID INTEGER,
    booking_Status_Code TEXT,
    hotel_id INTEGER,
    room_Number TEXT,
    guest_Number INTEGER,
    date_From DATE,
    date_To DATE
);

/* ---------------------------------------------------------------------- */
/* Add table "Guests"                                                     */
/* ---------------------------------------------------------------------- */

CREATE TABLE Guests (
    guest_Number SERIAL,
    guest_Details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Hotel_Chains"                                               */
/* ---------------------------------------------------------------------- */

CREATE TABLE Hotel_Chains (
    hotel_Chain_Code TEXT,
    hotel_Chain_Name TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Hotels"                                                     */
/* ---------------------------------------------------------------------- */

CREATE TABLE Hotels (
    hotel_id INTEGER,
    hotel_Chain_Code TEXT,
    iso_country_code TEXT,
    star_Rating_Code TEXT,
    hotel_Name TEXT,
    hotel_Address TEXT,
    hotel_City TEXT,
    hotel_PostCode TEXT,
    hotel_Email TEXT,
    hotel_URL TEXT,
    other_Hotel_Details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Period_Room_Rates"                                          */
/* ---------------------------------------------------------------------- */

CREATE TABLE Period_Room_Rates (
    rate_Period_Code TEXT,
    room_Type_Code TEXT,
    room_Rate NUMERIC
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Booking_Status"                                         */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Booking_Status (
    booking_Status_Code TEXT,
    booking_Status_Description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Calendar"                                               */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Calendar (
    day_Date DATE,
    day_Number INTEGER,
    business_Day_YN TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Hotel_Characteristics"                                  */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Hotel_Characteristics (
    characteristic_Code TEXT,
    characteristic_Code_Description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_ISO_Country_Codes"                                      */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_ISO_Country_Codes (
    iso_country_code TEXT,
    country_Currency TEXT,
    country_Name TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Room_Types"                                             */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Room_Types (
    room_Type_Code TEXT,
    room_Standard_Rate NUMERIC,
    room_Type_Description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Star_Ratings"                                           */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Star_Ratings (
    star_Rating_Code TEXT,
    star_Rating_Image TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Room_Availabilty"                                           */
/* ---------------------------------------------------------------------- */

CREATE TABLE Room_Availabilty (
    hotel_id INTEGER,
    day_Date DATE,
    room_Type_Code TEXT,
    room_Availability_Count INTEGER
);

/* ---------------------------------------------------------------------- */
/* Add table "Room_Rate_Periods"                                          */
/* ---------------------------------------------------------------------- */

CREATE TABLE Room_Rate_Periods (
    rate_Period_Code TEXT,
    rate_Period_Description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Rooms"                                                      */
/* ---------------------------------------------------------------------- */

CREATE TABLE Rooms (
    hotel_id INTEGER,
    room_Number TEXT,
    room_Floor TEXT,
    room_Type_Code TEXT,
    room_Actual_Rate NUMERIC,
    smoking_YN TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Specific_Hotel_Characteristics"                             */
/* ---------------------------------------------------------------------- */

CREATE TABLE Specific_Hotel_Characteristics (
    characteristic_Code TEXT,
    hotel_id INTEGER
);
