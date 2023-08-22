CREATE TABLE IF NOT EXISTS users (
    email VARCHAR(100) PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(100),
    surname VARCHAR(100),
    address VARCHAR(255)
);