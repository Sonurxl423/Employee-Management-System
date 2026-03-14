-- Drop user first if they exist
DROP USER if exists 'emptrack'@'localhost' ;

-- Now create user with prop privileges
CREATE USER 'emptrack'@'localhost' IDENTIFIED BY 'emptrack';

GRANT ALL PRIVILEGES ON * . * TO 'emptrack'@'localhost';