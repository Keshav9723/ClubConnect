-- 1. Database and table for Clubs
CREATE DATABASE IF NOT EXISTS clubdb;
USE clubdb;
CREATE TABLE clubs (
    id INT PRIMARY KEY AUTO_INCREMENT, -- Changed to INT
    name VARCHAR(255) NOT NULL,
    description TEXT,
    category VARCHAR(255)
);
-- Inserts 4 new clubs. Their auto-generated IDs will be 1, 2, 3, and 4.
INSERT INTO clubs (name, description, category) 
VALUES 
('Coding_Club', 'A_club_for_programmers', 'Technology'),
('Photo_Club', 'For_lovers_of_photography', 'Arts'),
('Adventure_Club', 'Trekking_and_outdoor_activities', 'Sports'),
('Music_Club', 'Jamming_and_music_production', 'Culture');

-- 2. Database and table for Members
CREATE DATABASE IF NOT EXISTS memberdb;
USE memberdb;
CREATE TABLE members (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone VARCHAR(255),
    club_id INT -- Changed to INT. Cannot be a foreign key to clubdb.
);
-- Inserts 4 new members. Their auto-generated IDs will be 1, 2, 3, and 4.
INSERT INTO members (name, email, phone, club_id) 
VALUES 
('Aarav_Sharma', 'aarav@example.com', '9876543210', 1), -- Member of Coding_Club (club_id=1)
('Priya_Singh', 'priya@example.com', '9988776655', 2), -- Member of Photo_Club (club_id=2)
('Rohan_Mehta', 'rohan@example.com', '9123456789', 3), -- Member of Adventure_Club (club_id=3)
('Ananya_Gupta', 'ananya@example.com', '9876501234', 1); -- Another member of Coding_Club (club_id=1)

-- 3. Database and table for Events
CREATE DATABASE IF NOT EXISTS eventdb;
USE eventdb;
CREATE TABLE events (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    location VARCHAR(255),
    date_time DATETIME NOT NULL,
    club_id INT -- Changed to INT. Cannot be a foreign key to clubdb.
);
-- Inserts 4 new events. Their auto-generated IDs will be 1, 2, 3, and 4.
INSERT INTO events (name, description, location, date_time, club_id) 
VALUES 
('Hackathon_2025', '24-hour_coding_event', 'Main_Auditorium', '2025-10-25 09:00:00', 1), -- Event for Coding_Club (club_id=1)
('City_Photo_Walk', 'Explore_and_capture_streets', 'Old_City', '2025-10-12 07:00:00', 2), -- Event for Photo_Club (club_id=2)
('Mountain_Trek', 'Weekend_trek_to_Triund', 'Dharamshala', '2025-11-08 06:00:00', 3), -- Event for Adventure_Club (club_id=3)
('AI_Workshop', 'Intro_to_Machine_Learning', 'Tech_Park', '2025-11-22 10:00:00', 1); -- Another event for Coding_Club (club_id=1)

-- 4. Database and table for Registrations
CREATE DATABASE IF NOT EXISTS registrationdb;
USE registrationdb;
CREATE TABLE registrations (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    member_id BIGINT NOT NULL,
    event_id BIGINT NOT NULL,
    registration_date DATETIME NOT NULL,
    status VARCHAR(50),
    member_name VARCHAR(255),
    event_name VARCHAR(255)
);
-- Inserts 4 new registrations linking members to events.
INSERT INTO registrations (member_id, event_id, registration_date, status, member_name, event_name) 
VALUES 
(1, 1, '2025-09-17 10:00:00', 'CONFIRMED', 'Aarav_Sharma', 'Hackathon_2025'), -- Aarav for Hackathon
(2, 2, '2025-09-17 11:30:00', 'CONFIRMED', 'Priya_Singh', 'City_Photo_Walk'), -- Priya for Photo_Walk
(3, 3, '2025-09-18 09:00:00', 'CONFIRMED', 'Rohan_Mehta', 'Mountain_Trek'), -- Rohan for Mountain_Trek
(4, 1, '2025-09-18 15:00:00', 'CONFIRMED', 'Ananya_Gupta', 'Hackathon_2025'); -- Ananya for Hackathon