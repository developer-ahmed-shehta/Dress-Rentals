-- -- Drop tables if they exist (for development only)
DROP TABLE IF EXISTS rental;
DROP TABLE IF EXISTS customer;
DROP TABLE IF EXISTS dress;


-- Create Dress Table
CREATE TABLE dress (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    size VARCHAR(50) NOT NULL,
    price_per_day DECIMAL(10,2) NOT NULL,
    availability BOOLEAN DEFAULT TRUE,
    photo_path VARCHAR(255) -- Store image file path
);

-- Create Customer Table
CREATE TABLE customer (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone VARCHAR(20) UNIQUE NOT NULL
);

-- Create Rental Table
CREATE TABLE rental (
    id SERIAL PRIMARY KEY,
    customer_id INT NOT NULL,
    dress_id INT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    total_price DECIMAL(10,2) NOT NULL,
    status VARCHAR(50) DEFAULT 'Pending',
    FOREIGN KEY (customer_id) REFERENCES customer(id) ON DELETE CASCADE,
    FOREIGN KEY (dress_id) REFERENCES dress(id) ON DELETE CASCADE
);