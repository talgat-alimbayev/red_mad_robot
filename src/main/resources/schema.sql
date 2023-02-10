DROP TABLE IF EXISTS users CASCADE;
CREATE TABLE IF NOT EXISTS users (
    id SERIAL NOT NULL PRIMARY KEY,
    email char(50) NOT NULL,
    password char(60) NOT NULL );

DROP TABLE IF EXISTS ads CASCADE;
CREATE TABLE IF NOT EXISTS ads (
    id SERIAL NOT NULL PRIMARY KEY,
    description char(50) NOT NULL,
    startPrice real NOT NULL,
    imageLink char(50) NOT NULL,
    status char(8) NOT NULL,
    user_id INT NOT NULL );

ALTER TABLE ads ADD CONSTRAINT FK_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;

DROP TABLE IF EXISTS bids;
CREATE TABLE IF NOT EXISTS bids (
    id SERIAL NOT NULL PRIMARY KEY,
    ad INT NOT NULL,
    bid real NOT NULL,
    user_id INT NOT NULL,
    status char(3) NOT NULL );

ALTER TABLE bids ADD CONSTRAINT FK_ad FOREIGN KEY (ad) REFERENCES ads(id) ON DELETE CASCADE;
ALTER TABLE bids ADD CONSTRAINT FK_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;