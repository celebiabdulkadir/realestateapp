-- Create the package table if it doesn't exist
CREATE TABLE IF NOT EXISTS package (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price NUMERIC(10, 2) NOT NULL,
    description TEXT,
    duration INT NOT NULL,
    advert_quantity INT NOT NULL
);

-- Insert initial data into the package table
INSERT INTO package (name, price, description, duration, advert_quantity) VALUES
('Basic Package', 10.0, 'Basic package description', 30, 10)