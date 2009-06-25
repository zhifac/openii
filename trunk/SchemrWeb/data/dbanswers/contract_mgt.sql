/* ---------------------------------------------------------------------- */
/* Script generated with: DeZign for Databases v5.2.0                     */
/* Target DBMS:           PostgreSQL 8                                    */
/* Project file:          Project1.dez                                    */
/* Project name:                                                          */
/* Author:                                                                */
/* Script type:           Database creation script                        */
/* Created on:            2009-06-24 18:03                                */
/* Model version:         Version 2009-06-24                              */
/* ---------------------------------------------------------------------- */


/* ---------------------------------------------------------------------- */
/* Tables                                                                 */
/* ---------------------------------------------------------------------- */

/* ---------------------------------------------------------------------- */
/* Add table "Addresses"                                                  */
/* ---------------------------------------------------------------------- */

CREATE TABLE Addresses (
    address_id SERIAL,
    address_type_code TEXT,
    line_1_number_building TEXT,
    line_2_number_street TEXT,
    line_3_area_locality TEXT,
    city TEXT,
    zip_postcode TEXT,
    state_province_county TEXT,
    country TEXT,
    other_address_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Contract_Changes"                                           */
/* ---------------------------------------------------------------------- */

CREATE TABLE Contract_Changes (
    change_id SERIAL,
    contract_id INTEGER,
    authorised_by_staff_id INTEGER,
    role_code TEXT,
    change_datetime DATE,
    other_change_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Contract_Documents"                                         */
/* ---------------------------------------------------------------------- */

CREATE TABLE Contract_Documents (
    contract_document_id SERIAL,
    contract_id INTEGER,
    document_owner_staff_id INTEGER,
    std_document_id INTEGER,
    document_created_date DATE,
    latest_revision_date DATE,
    contract_text TEXT,
    other_document_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Contract_Services"                                          */
/* ---------------------------------------------------------------------- */

CREATE TABLE Contract_Services (
    contract_id INTEGER,
    service_id INTEGER,
    service_date_from DATE,
    service_date_to DATE,
    service_quantity TEXT,
    monthly_payment_date DATE,
    monthly_payment_amount NUMERIC,
    other_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Contracts"                                                  */
/* ---------------------------------------------------------------------- */

CREATE TABLE Contracts (
    contract_id SERIAL,
    client_id INTEGER,
    contract_title TEXT,
    contract_start_date DATE,
    contract_end_date DATE,
    contract_description TEXT,
    other_contract_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Address_Types"                                          */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Address_Types (
    address_type_code TEXT,
    address_type_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Document_Types"                                         */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Document_Types (
    document_type_code TEXT,
    document_type_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Job_Types"                                              */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Job_Types (
    job_type_code TEXT,
    job_type_name TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Roles"                                                  */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Roles (
    role_code TEXT,
    role_name TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Services"                                                   */
/* ---------------------------------------------------------------------- */

CREATE TABLE Services (
    service_id SERIAL,
    parent_service_id INTEGER,
    service_category_code TEXT,
    period_code TEXT,
    price_per_period NUMERIC,
    service_name TEXT,
    other_service_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Staff"                                                      */
/* ---------------------------------------------------------------------- */

CREATE TABLE Staff (
    staff_id SERIAL,
    job_type_code TEXT,
    gender TEXT,
    date_of_birth DATE,
    staff_first_name TEXT,
    staff_last_name TEXT,
    other_staff_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Standard_Contract_Sections"                                 */
/* ---------------------------------------------------------------------- */

CREATE TABLE Standard_Contract_Sections (
    std_section_id SERIAL,
    section_type_code TEXT,
    contract_id INTEGER,
    std_section_created_date DATE,
    latest_revision_date DATE,
    contract_text TEXT,
    other_std_section_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Supplier_Addresses"                                         */
/* ---------------------------------------------------------------------- */

CREATE TABLE Supplier_Addresses (
    supplier_id INTEGER,
    address_id INTEGER,
    date_address_from DATE,
    date_address_to DATE
);

/* ---------------------------------------------------------------------- */
/* Add table "Suppliers"                                                  */
/* ---------------------------------------------------------------------- */

CREATE TABLE Suppliers (
    supplier_id SERIAL,
    supplier_name TEXT,
    supplier_from_date DATE,
    supplier_to_date DATE,
    billings_to_date NUMERIC,
    other_supplier_details TEXT
);
