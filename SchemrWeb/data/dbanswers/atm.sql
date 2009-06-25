/* ---------------------------------------------------------------------- */
/* Script generated with: DeZign for Databases v5.2.0                     */
/* Target DBMS:           PostgreSQL 8                                    */
/* Project file:          Project1.dez                                    */
/* Project name:                                                          */
/* Author:                                                                */
/* Script type:           Database creation script                        */
/* Created on:            2009-06-24 17:27                                */
/* Model version:         Version 2009-06-24                              */
/* ---------------------------------------------------------------------- */


/* ---------------------------------------------------------------------- */
/* Tables                                                                 */
/* ---------------------------------------------------------------------- */

/* ---------------------------------------------------------------------- */
/* Add table "Accounts"                                                   */
/* ---------------------------------------------------------------------- */

CREATE TABLE Accounts (
    account_id SERIAL,
    customer_id INTEGER,
    account_name TEXT,
    other_account_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "ATM_Machine_Services"                                       */
/* ---------------------------------------------------------------------- */

CREATE TABLE ATM_Machine_Services (
    atm_id INTEGER,
    service_type_code TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "ATM_Machines"                                               */
/* ---------------------------------------------------------------------- */

CREATE TABLE ATM_Machines (
    atm_id SERIAL,
    nearest_branch_id INTEGER,
    operational_yn TEXT,
    location TEXT,
    other_atm_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Bank_Branches"                                              */
/* ---------------------------------------------------------------------- */

CREATE TABLE Bank_Branches (
    branch_id SERIAL,
    bank_id INTEGER,
    branch_name TEXT,
    branch_address TEXT,
    other_branch_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Banks"                                                      */
/* ---------------------------------------------------------------------- */

CREATE TABLE Banks (
    bank_id SERIAL,
    bank_name TEXT,
    hq_address TEXT,
    other_bank_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Customers"                                                  */
/* ---------------------------------------------------------------------- */

CREATE TABLE Customers (
    customer_id SERIAL,
    customer_first_name TEXT,
    customer_last_name TEXT,
    customer_address TEXT,
    customer_phone TEXT,
    customer_email TEXT,
    other_customer_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Customers_Cards"                                            */
/* ---------------------------------------------------------------------- */

CREATE TABLE Customers_Cards (
    card_number TEXT,
    card_type_code TEXT,
    account_id INTEGER,
    date_valid_from DATE,
    date_valid_to DATE,
    daily_withdrawal_limit NUMERIC,
    other_card_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Other_Transactions"                                         */
/* ---------------------------------------------------------------------- */

CREATE TABLE Other_Transactions (
    transaction_id SERIAL,
    atm_id INTEGER,
    service_type_code TEXT,
    transaction_datetime DATE,
    other_transaction_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_ATM_Services"                                           */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_ATM_Services (
    service_type_code TEXT,
    service_type_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Card_Types"                                             */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Card_Types (
    card_type_code TEXT,
    card_type_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Withdrawals"                                                */
/* ---------------------------------------------------------------------- */

CREATE TABLE Withdrawals (
    withdrawal_id SERIAL,
    atm_id INTEGER,
    withdrawal_datetime DATE,
    withdrawal_amount NUMERIC,
    amount_withdrawn_today NUMERIC,
    other_withdrawal_details TEXT
);
