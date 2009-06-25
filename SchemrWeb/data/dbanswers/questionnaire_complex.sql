/* ---------------------------------------------------------------------- */
/* Script generated with: DeZign for Databases v5.2.0                     */
/* Target DBMS:           PostgreSQL 8                                    */
/* Project file:          Project1.dez                                    */
/* Project name:                                                          */
/* Author:                                                                */
/* Script type:           Database creation script                        */
/* Created on:            2009-06-24 19:35                                */
/* Model version:         Version 2009-06-24                              */
/* ---------------------------------------------------------------------- */


/* ---------------------------------------------------------------------- */
/* Tables                                                                 */
/* ---------------------------------------------------------------------- */

/* ---------------------------------------------------------------------- */
/* Add table "ANALYSIS_OF_OTHER_RESPONSES"                                */
/* ---------------------------------------------------------------------- */

CREATE TABLE ANALYSIS_OF_OTHER_RESPONSES (
    QUESTIONNAIRE_ID INTEGER,
    QUESTION_NUMBER INTEGER,
    CHOICE_NUMBER INTEGER,
    OTHER_RESPONSE_TEXT TEXT,
    RESPONSE_COUNT INTEGER
);

/* ---------------------------------------------------------------------- */
/* Add table "ANALYSIS_OF_RESPONSES"                                      */
/* ---------------------------------------------------------------------- */

CREATE TABLE ANALYSIS_OF_RESPONSES (
    QUESTIONNAIRE_ID INTEGER,
    QUESTION_NUMBER INTEGER,
    CHOICE_NUMBER INTEGER,
    RESPONSE_COUNT INTEGER
);

/* ---------------------------------------------------------------------- */
/* Add table "DEMOGRAPHICS_COUNT"                                         */
/* ---------------------------------------------------------------------- */

CREATE TABLE DEMOGRAPHICS_COUNT (
    QUESTIONNAIRE_ID INTEGER,
    AGE TEXT,
    GENDER TEXT,
    LOCATION TEXT,
    OTHER_CHARACTERISTICS TEXT,
    RESPONSE_COUNT INTEGER
);

/* ---------------------------------------------------------------------- */
/* Add table "PARTICIPANT_DEMOGRAPHICS"                                   */
/* ---------------------------------------------------------------------- */

CREATE TABLE PARTICIPANT_DEMOGRAPHICS (
    QUESTIONNAIRE_ID INTEGER,
    AGE TEXT,
    GENDER TEXT,
    LOCATION TEXT,
    OTHER_CHARACTERISTICS TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "QUESTION_CHOICES"                                           */
/* ---------------------------------------------------------------------- */

CREATE TABLE QUESTION_CHOICES (
    QUESTIONNAIRE_ID INTEGER,
    QUESTION_NUMBER INTEGER,
    CHOICE_NUMBER INTEGER,
    CHOICE_WORDING TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "QUESTIONNAIRE"                                              */
/* ---------------------------------------------------------------------- */

CREATE TABLE QUESTIONNAIRE (
    QUESTIONNAIRE_ID SERIAL,
    QUESTIONNAIRE_DESCRIPTION TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "QUESTIONS"                                                  */
/* ---------------------------------------------------------------------- */

CREATE TABLE QUESTIONS (
    QUESTIONNAIRE_ID INTEGER,
    QUESTION_NUMBER INTEGER DEFAULT 0,
    QUESTION_WORDING TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "RESPONSES"                                                  */
/* ---------------------------------------------------------------------- */

CREATE TABLE RESPONSES (
    QUESTIONNAIRE_ID INTEGER,
    QUESTION_NUMBER INTEGER,
    RESPONSE_ID INTEGER,
    CHOICE_NUMBER INTEGER,
    RESPONSE_VALUE_0_OR_1 INTEGER,
    OTHER_RESPONSE_TEXT TEXT
);
