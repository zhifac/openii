/* ---------------------------------------------------------------------- */
/* Script generated with: DeZign for Databases v5.2.0                     */
/* Target DBMS:           PostgreSQL 8                                    */
/* Project file:          Project1.dez                                    */
/* Project name:                                                          */
/* Author:                                                                */
/* Script type:           Database creation script                        */
/* Created on:            2009-06-24 19:27                                */
/* Model version:         Version 2009-06-24                              */
/* ---------------------------------------------------------------------- */


/* ---------------------------------------------------------------------- */
/* Tables                                                                 */
/* ---------------------------------------------------------------------- */

/* ---------------------------------------------------------------------- */
/* Add table "ADDRESSES"                                                  */
/* ---------------------------------------------------------------------- */

CREATE TABLE ADDRESSES (
    ADDRESS_ID SERIAL,
    LINE_1_NUMBER_BUILDING TEXT,
    LINE_2_NUMBER_STREET TEXT,
    LINE_3_AREA_SUBURB TEXT,
    TOWN_CITY TEXT,
    STATE_COUNTY TEXT,
    COUNTRY TEXT,
    POSTAL_ZIPCODE TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "BROKERS"                                                    */
/* ---------------------------------------------------------------------- */

CREATE TABLE BROKERS (
    BROKER_ID SERIAL,
    ADDRESS_ID INTEGER,
    BROKER_NAME TEXT,
    OTHER_BROKER_DETAILS TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "CLAIMS"                                                     */
/* ---------------------------------------------------------------------- */

CREATE TABLE CLAIMS (
    CLAIM_ID SERIAL,
    CLAIM_STATUS_CODE TEXT,
    CUSTOMER_POLICY_ID INTEGER,
    OUTCOME_CODE TEXT,
    DATE_OF_CLAIM DATE,
    AMOUNT_OF_CLAIM MONEY,
    DETAILS_OF_CLAIM TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "CUSTOMERS"                                                  */
/* ---------------------------------------------------------------------- */

CREATE TABLE CUSTOMERS (
    CUSTOMER_ID SERIAL,
    CUSTOMER_DETAILS TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "CUSTOMERS_ADDRESSES"                                        */
/* ---------------------------------------------------------------------- */

CREATE TABLE CUSTOMERS_ADDRESSES (
    CUSTOMER_ID INTEGER,
    ADDRESS_ID INTEGER,
    DATE_ADDRESS_FROM DATE,
    DATE_ADDRESS_TO DATE
);

/* ---------------------------------------------------------------------- */
/* Add table "CUSTOMERS_POLICIES"                                         */
/* ---------------------------------------------------------------------- */

CREATE TABLE CUSTOMERS_POLICIES (
    CUSTOMER_ID INTEGER,
    POLICY_ID INTEGER,
    CUSTOMER_POLICY_DETAILS TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "HOUSEHOLD"                                                  */
/* ---------------------------------------------------------------------- */

CREATE TABLE HOUSEHOLD (
    POLICY_ID INTEGER,
    BROKER_ID INTEGER,
    INSURANCE_TYPE_CODE TEXT,
    AGE_OF_PROPERTY INTEGER,
    POLICY_START_DATE DATE,
    POLICY_RENEWAL_DATE DATE,
    PREMIUM_PAYABLE MONEY,
    OTHER_POLICY_DETAILS TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "INSURANCE_COMPANIES"                                        */
/* ---------------------------------------------------------------------- */

CREATE TABLE INSURANCE_COMPANIES (
    COMPANY_ID SERIAL,
    ADDRESS_ID INTEGER,
    COMPANY_NAME TEXT,
    OTHER_DETAILS TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "LIFE"                                                       */
/* ---------------------------------------------------------------------- */

CREATE TABLE LIFE (
    POLICY_ID INTEGER,
    BROKER_ID INTEGER,
    INSURANCE_TYPE_CODE TEXT,
    OCCUPATION_CODE TEXT,
    POLICY_START_DATE DATE,
    POLICY_RENEWAL_DATE DATE,
    PREMIUM_PAYABLE MONEY,
    LIFE_EXPECTANCY TEXT,
    OTHER_POLICY_DETAILS TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "MOTOR"                                                      */
/* ---------------------------------------------------------------------- */

CREATE TABLE MOTOR (
    POLICY_ID INTEGER,
    BROKER_ID INTEGER,
    INSURANCE_TYPE_CODE TEXT,
    POLICY_START_DATE DATE,
    POLICY_RENEWAL_DATE DATE,
    PREMIUM_PAYABLE MONEY,
    VEHICLE_DETAILS TEXT,
    OTHER_POLICY_DETAILS TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "POLICIES"                                                   */
/* ---------------------------------------------------------------------- */

CREATE TABLE POLICIES (
    POLICY_ID SERIAL,
    INSURANCE_TYPE_CODE TEXT,
    BROKER_ID INTEGER,
    POLICY_START_DATE DATE,
    POLICY_RENEWAL_DATE DATE,
    PREMIUM_PAYABLE MONEY,
    OTHER_POLICY_DETAILS TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "REF_CLAIM_OUTCOME"                                          */
/* ---------------------------------------------------------------------- */

CREATE TABLE REF_CLAIM_OUTCOME (
    OUTCOME_CODE TEXT,
    OUTCOME_DESCRIPTION TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "REF_CLAIM_STATUS"                                           */
/* ---------------------------------------------------------------------- */

CREATE TABLE REF_CLAIM_STATUS (
    CLAIM_STATUS_CODE TEXT,
    CLAIM_STATUS_DESCRIPTION TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "REF_INSURANCE_TYPES"                                        */
/* ---------------------------------------------------------------------- */

CREATE TABLE REF_INSURANCE_TYPES (
    INSURANCE_TYPE_CODE TEXT,
    INSURANCE_TYPE_DESCRIPTION TEXT
);
