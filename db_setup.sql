CREATE DATABASE IF NOT EXISTS soccer_app;

USE soccer_app;

-- Create Player Table
CREATE TABLE players (
    player_id INTEGER PRIMARY KEY AUTO_INCREMENT,
    player_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL
);

-- Create Event Table
CREATE TABLE events (
    event_id INT AUTO_INCREMENT PRIMARY KEY,
    event_type VARCHAR(50) NOT NULL,
    event_date DATETIME NOT NULL,
    location VARCHAR(255) COMMENT,
    description TEXT COMMENT,
    team_sheet TEXT COMMENT,
    title VARCHAR(255),
    start_time DATETIME,
    end_time DATETIME,
    team_id BIGINT
);

--     PARTITION BY HASH(TO_DAYS(event_date))
--   PARTITIONS 5;

-- Create Event Attendance Table
CREATE TABLE event_attendances (
    attendance_id INTEGER PRIMARY KEY AUTO_INCREMENT,
    player_id INTEGER NOT NULL,
    event_id INTEGER NOT NULL,
    status VARCHAR(50) NOT NULL,
    FOREIGN KEY (player_id) REFERENCES players(player_id),
    FOREIGN KEY (event_id) REFERENCES events(event_id)
);
