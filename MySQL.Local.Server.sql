show DATABASEs;
create DATABASE petpals;
use petpals;

CREATE TABLE pets (
    pet_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age INT NOT NULL CHECK (age > 0),
    breed VARCHAR(100),
    pet_type ENUM('Dog', 'Cat') NOT NULL
);

CREATE TABLE donations (
    donation_id INT AUTO_INCREMENT PRIMARY KEY,
    donor_name VARCHAR(100) NOT NULL,
    amount DECIMAL(10, 2) NOT NULL CHECK (amount >= 10),
    donation_type ENUM('Cash', 'Item') NOT NULL,
    donation_date DATETIME DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE adoption_events (
    event_id INT AUTO_INCREMENT PRIMARY KEY,
    event_name VARCHAR(100) NOT NULL,
    event_date DATETIME NOT NULL
);
CREATE TABLE participants (
    participant_id INT AUTO_INCREMENT PRIMARY KEY,
    participant_name VARCHAR(100) NOT NULL,
    event_id INT NOT NULL,
    FOREIGN KEY (event_id) REFERENCES adoption_events(event_id)
);
-- Insert Pets
INSERT INTO pets (name, age, breed, pet_type) VALUES
('Max', 3, 'Golden Retriever', 'Dog', ''),
('Bella', 2, 'Labrador', 'Dog'),
('Milo', 1, 'Persian', 'Cat'),
('Lucy', 4, 'Siamese', 'Cat'),
('Buddy', 5, 'Bulldog', 'Dog');
show tables;

DELETE FROM pets WHERE pet_id = 1;

ALTER TABLE pets ADD COLUMN is_adopted BOOLEAN DEFAULT FALSE;
INSERT INTO pets (name, age, breed, pet_type) VALUES
('Max', 3, 'Golden Retriever', 'Dog'),
('Bella', 2, 'Labrador', 'Dog'),
('Milo', 1, 'Persian', 'Cat'),
('Lucy', 4, 'Siamese', 'Cat'),
('Buddy', 5, 'Bulldog', 'Dog');

-- Insert Adoption Events
INSERT INTO adoption_events (event_name, event_date) VALUES
('Summer Pet Adoption Drive', '2023-07-01 10:00:00'),
('Winter Pet Adoption Fair', '2023-12-15 09:00:00');

-- Insert Adoption Events
INSERT INTO adoption_events (event_name, event_date) VALUES
('Summer Pet Adoption Drive', '2023-07-01 10:00:00'),
('Winter Pet Adoption Fair', '2023-12-15 09:00:00');

UPDATE pets 
SET is_adopted = TRUE 
WHERE pet_id IN (1, 3, 5);  -- Adopt pets with pet_id 1, 3, and 5