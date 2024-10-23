CREATE DATABASE IF NOT EXISTS db_setup;

USE db_setup;

-- Create Player Table
CREATE TABLE Player (
    player_id INTEGER PRIMARY KEY AUTO_INCREMENT,
    player_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL
);

-- Create Event Table
CREATE TABLE Event (
    event_id INTEGER AUTO_INCREMENT,
    event_type VARCHAR(50) NOT NULL,
    event_date DATETIME NOT NULL,
    location VARCHAR(100) NOT NULL,
    description TEXT,
    team_sheet TEXT,
    PRIMARY KEY (event_id, event_date)
);
--     PARTITION BY HASH(TO_DAYS(event_date))
--   PARTITIONS 5;

-- Create Event Attendance Table
CREATE TABLE Event_Attendance (
    attendance_id INTEGER PRIMARY KEY AUTO_INCREMENT,
    player_id INTEGER NOT NULL,
    event_id INTEGER NOT NULL,
    status VARCHAR(50) NOT NULL,
    FOREIGN KEY (player_id) REFERENCES Player(player_id),
    FOREIGN KEY (event_id) REFERENCES Event(event_id)
);
