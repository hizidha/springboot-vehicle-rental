INSERT INTO customer (name, email, phone, address, active)
VALUES
    ('Somat', 'somat@gmail.com', '081616512534', 'Jl Kenangan Bersama 1', 'true'),
    ('Soni', 'soniw@gmail.com', '081616512535', 'Jl Kenangan Bersama 2','false'),
    ('Rucika', 'rucikaw12@gmail.com', '081616512566', 'Jl Kenangan Bersama 3','true');

INSERT INTO vehicle (brand, model, registration_number, status, active)
VALUES
    ('Toyota', 'Avanza 2015', 'B 234 XYZ', 'AVAILABLE', 'true'),
    ('Honda', 'Civic 2020', 'C 5678 ABC', 'AVAILABLE', 'false'),
    ('Suzuki', 'Swift 2021', 'D 9101 DEF', 'RENT', 'true');

INSERT INTO rental_record (vehicle_id, customer_id, rental_date, return_date, status, created_at, updated_at)
VALUES
    (1, 1, CURRENT_DATE - 1, CURRENT_DATE, 'RETURNED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (2, 3, CURRENT_DATE - 1, CURRENT_DATE , 'RETURNED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (3, 1, CURRENT_DATE - 1, CURRENT_DATE + 1, 'RENT', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);