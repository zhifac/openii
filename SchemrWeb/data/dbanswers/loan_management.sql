/* ---------------------------------------------------------------------- */
/* Script generated with: DeZign for Databases v5.2.0                     */
/* Target DBMS:           PostgreSQL 8                                    */
/* Project file:          Project1.dez                                    */
/* Project name:                                                          */
/* Author:                                                                */
/* Script type:           Database creation script                        */
/* Created on:            2009-06-24 19:30                                */
/* Model version:         Version 2009-06-24                              */
/* ---------------------------------------------------------------------- */


/* ---------------------------------------------------------------------- */
/* Tables                                                                 */
/* ---------------------------------------------------------------------- */

/* ---------------------------------------------------------------------- */
/* Add table "Collectors"                                                 */
/* ---------------------------------------------------------------------- */

CREATE TABLE Collectors (
    Collector_ID SERIAL,
    Collecter_Name TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Customers"                                                  */
/* ---------------------------------------------------------------------- */

CREATE TABLE Customers (
    Customer_Number SERIAL,
    Customer_Rating_Code TEXT,
    Customer_Name TEXT,
    Address TEXT,
    Balance MONEY
);

/* ---------------------------------------------------------------------- */
/* Add table "Loan_Contracts"                                             */
/* ---------------------------------------------------------------------- */

CREATE TABLE Loan_Contracts (
    Loan_ID SERIAL,
    Collector_ID INTEGER,
    Customer_Number INTEGER,
    Loan_Status_Code TEXT,
    Date_Contract_Starts DATE,
    Date_Cotract_Ends DATE,
    Interest_Rate NUMERIC,
    Loan_Amount NUMERIC,
    Loan_Payment_Amount NUMERIC,
    Loan_Payment_Due_Date DATE,
    Loan_Payment_Frequency TEXT,
    Terms_and_Conditions TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Loan_Payments"                                              */
/* ---------------------------------------------------------------------- */

CREATE TABLE Loan_Payments (
    Payment_ID SERIAL,
    Loan_ID INTEGER,
    Payment_Type_Code TEXT,
    Date_of_Payment TEXT,
    Amount_of_Payment NUMERIC,
    Remarks TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Customer_Ratings"                                       */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Customer_Ratings (
    Customer_Rating_Code TEXT,
    Customer_Rating_Description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Loan_Status"                                            */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Loan_Status (
    Loan_Status_Code TEXT,
    Loan_Status_Description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Payment_Types"                                          */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Payment_Types (
    Payment_Type_Code TEXT,
    Payment_Type_Description TEXT
);
