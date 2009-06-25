/* ---------------------------------------------------------------------- */
/* Script generated with: DeZign for Databases v5.2.0                     */
/* Target DBMS:           PostgreSQL 8                                    */
/* Project file:          Project1.dez                                    */
/* Project name:                                                          */
/* Author:                                                                */
/* Script type:           Database creation script                        */
/* Created on:            2009-06-24 19:17                                */
/* Model version:         Version 2009-06-24                              */
/* ---------------------------------------------------------------------- */


/* ---------------------------------------------------------------------- */
/* Tables                                                                 */
/* ---------------------------------------------------------------------- */

/* ---------------------------------------------------------------------- */
/* Add table "Accounting_Dimension"                                       */
/* ---------------------------------------------------------------------- */

CREATE TABLE Accounting_Dimension (
    accounting_code TEXT,
    accounting_code_name TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Data_File"                                                  */
/* ---------------------------------------------------------------------- */

CREATE TABLE Data_File (
    file_id SERIAL,
    source_id NUMERIC,
    file_name TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Data_File_Staging_Area"                                     */
/* ---------------------------------------------------------------------- */

CREATE TABLE Data_File_Staging_Area (
    file_id NUMERIC,
    staging_table_id NUMERIC
);

/* ---------------------------------------------------------------------- */
/* Add table "Data_Marketing_ERP"                                         */
/* ---------------------------------------------------------------------- */

CREATE TABLE Data_Marketing_ERP (
    erp_indicator SERIAL
);

/* ---------------------------------------------------------------------- */
/* Add table "Data_Marketing_Risk_Mgt"                                    */
/* ---------------------------------------------------------------------- */

CREATE TABLE Data_Marketing_Risk_Mgt (
    risk_mgt_indicator SERIAL
);

/* ---------------------------------------------------------------------- */
/* Add table "Data_Mart_CRM"                                              */
/* ---------------------------------------------------------------------- */

CREATE TABLE Data_Mart_CRM (
    crm_indicator SERIAL
);

/* ---------------------------------------------------------------------- */
/* Add table "Data_Warehouse"                                             */
/* ---------------------------------------------------------------------- */

CREATE TABLE Data_Warehouse (
    fact_id SERIAL,
    accounting_code TEXT,
    geographic_area TEXT,
    product_code TEXT,
    fact_name TEXT,
    fact_description TEXT,
    other_fact_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "File_Source"                                                */
/* ---------------------------------------------------------------------- */

CREATE TABLE File_Source (
    source_id SERIAL,
    source_name TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Generic_Data"                                               */
/* ---------------------------------------------------------------------- */

CREATE TABLE Generic_Data (
    log_entry_id SERIAL,
    data_type_code TEXT,
    fact_id NUMERIC,
    staging_table_id NUMERIC
);

/* ---------------------------------------------------------------------- */
/* Add table "Geographic_Dimension"                                       */
/* ---------------------------------------------------------------------- */

CREATE TABLE Geographic_Dimension (
    geographic_area TEXT,
    geographic_area_name TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Product_Dimension"                                          */
/* ---------------------------------------------------------------------- */

CREATE TABLE Product_Dimension (
    product_code TEXT,
    product_name TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Generic_Data_Type"                                      */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Generic_Data_Type (
    data_type_code TEXT,
    data_type_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Staging_Area"                                               */
/* ---------------------------------------------------------------------- */

CREATE TABLE Staging_Area (
    staging_table_id SERIAL,
    staging_table_name TEXT,
    staging_table_description TEXT
);
