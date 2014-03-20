DROP DATABASE IF EXISTS mozakin;
DROP ROLE IF EXISTS mozakin_user;

CREATE ROLE mozakin_user WITH LOGIN PASSWORD 'password';
CREATE DATABASE mozakin OWNER mozakin_user encoding 'utf8';
