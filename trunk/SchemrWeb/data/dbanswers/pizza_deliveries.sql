/* ---------------------------------------------------------------------- */
/* Script generated with: DeZign for Databases v5.2.0                     */
/* Target DBMS:           PostgreSQL 8                                    */
/* Project file:          Project1.dez                                    */
/* Project name:                                                          */
/* Author:                                                                */
/* Script type:           Database creation script                        */
/* Created on:            2009-06-24 19:33                                */
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
    line_1 TEXT,
    line_2 TEXT,
    line_3 TEXT,
    line_4 TEXT,
    city TEXT,
    zip_postcode TEXT,
    state_province_county TEXT,
    country TEXT,
    other_address_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Customers"                                                  */
/* ---------------------------------------------------------------------- */

CREATE TABLE Customers (
    customer_id SERIAL,
    customer_address_id INTEGER,
    payment_method_code TEXT,
    customer_name TEXT,
    customer_phone TEXT,
    customer_email TEXT,
    date_of_first_order DATE,
    other_customer_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Employees"                                                  */
/* ---------------------------------------------------------------------- */

CREATE TABLE Employees (
    employee_id SERIAL,
    employee_address_id INTEGER,
    employee_name TEXT,
    employee_phone TEXT,
    other_employee_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Orders"                                                     */
/* ---------------------------------------------------------------------- */

CREATE TABLE Orders (
    order_id SERIAL,
    customer_id INTEGER,
    taken_by_employee_id INTEGER,
    delivered_by_employee_id INTEGER,
    delivery_status_code TEXT,
    vehicle_id INTEGER,
    datetime_order_taken DATE,
    datetime_order_delivered DATE,
    total_order_price MONEY,
    other_order_details TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Pizzas_Ordered"                                             */
/* ---------------------------------------------------------------------- */

CREATE TABLE Pizzas_Ordered (
    order_id INTEGER,
    pizza_sequence_number INTEGER,
    base_type_code TEXT,
    total_pizza_price MONEY
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Base_Types"                                             */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Base_Types (
    base_type_code TEXT,
    base_type_price MONEY,
    base_type_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Delivery_Status"                                        */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Delivery_Status (
    delivery_status_code TEXT,
    delivery_status_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Payment_Methods"                                        */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Payment_Methods (
    payment_method_code TEXT,
    payment_method_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Toppings"                                               */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Toppings (
    topping_code TEXT,
    topping_price MONEY,
    topping_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Ref_Vehicle_Types"                                          */
/* ---------------------------------------------------------------------- */

CREATE TABLE Ref_Vehicle_Types (
    vehicle_type_code TEXT,
    vehicle_type_description TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Toppings"                                                   */
/* ---------------------------------------------------------------------- */

CREATE TABLE Toppings (
    order_id INTEGER,
    pizza_sequence_number INTEGER,
    topping_sequence_number INTEGER,
    topping_code TEXT
);

/* ---------------------------------------------------------------------- */
/* Add table "Vehicles"                                                   */
/* ---------------------------------------------------------------------- */

CREATE TABLE Vehicles (
    vehicle_id SERIAL,
    vehicle_type_code TEXT,
    vehicle_licence_number TEXT,
    vehicle_details TEXT
);
