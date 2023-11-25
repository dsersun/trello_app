-- Database: trello_db

-- DROP DATABASE IF EXISTS trello_db;

CREATE DATABASE trello_db
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Russian_Russia.1251'
    LC_CTYPE = 'Russian_Russia.1251'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;

COMMENT ON DATABASE trello_db
    IS 'database for custom trello application';