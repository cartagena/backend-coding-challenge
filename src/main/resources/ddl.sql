CREATE DATABASE alchemytec;
CREATE USER 'alchemytec'@'localhost' IDENTIFIED BY 'alchemytec';
GRANT ALL ON alchemytec.* TO 'alchemytec'@'localhost';
FLUSH PRIVILEGES;
