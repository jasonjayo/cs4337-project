CREATE DATABASE IF NOT EXISTS soccer_app;

USE soccer_app;

-- Create Player Table
CREATE TABLE players (
    player_id INTEGER PRIMARY KEY AUTO_INCREMENT,
    player_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL
);
CREATE TABLE managers
(
    manager_id INTEGER PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100)
);

CREATE TABLE teams (
    team_id INTEGER PRIMARY KEY AUTO_INCREMENT,
    team_name VARCHAR(100) NOT NULL,
    manager INTEGER NOT NULL ,
    FOREIGN KEY (manager) REFERENCES managers(manager_id)
);

CREATE TABLE player_team (
    player_id INTEGER,
    team_id INTEGER,
    FOREIGN KEY (player_id) REFERENCES players(player_id),
    FOREIGN KEY (team_id) REFERENCES teams(team_id)
);

-- Create Event Table
CREATE TABLE events (
    event_id INT AUTO_INCREMENT PRIMARY KEY,
    event_type VARCHAR(50) NOT NULL,
    event_date DATETIME NOT NULL,
    location VARCHAR(255),
    description TEXT,
    team_sheet TEXT,
    title VARCHAR(255),
    start_time DATETIME,
    end_time DATETIME,
    team_id BIGINT
);

-- Create Event Attendance Table
CREATE TABLE attendance (
    attendance_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    player_id BIGINT NOT NULL,
    event_id BIGINT NOT NULL,
    status VARCHAR(50) NOT NULL,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (player_id) REFERENCES players(player_id),
    FOREIGN KEY (event_id) REFERENCES events(event_id)
);
