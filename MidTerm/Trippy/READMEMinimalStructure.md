# Project overview

# DB setup query
CREATE SCHEMA [IF NOT EXISTS] trippy;

CREATE TABLE trippy.user(
id SERIAL PRIMARY KEY,
username VARCHAR(20) NOT NULL,
email VARCHAR(30) NOT NULL,
phone VARCHAR NOT NULL,
city VARCHAR(20) NOT NULL,
registration_date DATE NOT NULL
);

CREATE TABLE trippy.business(
id SERIAL PRIMARY KEY,
name VARCHAR(30) NOT NULL,
"type" VARCHAR NOT NULL,
rating INT NOT NULL,
reviews_count INT NOT NULL,
email VARCHAR(30) NOT NULL,
phone VARCHAR NOT NULL,
city VARCHAR(20) NOT NULL
);

ALTER TABLE trippy.business
ALTER COLUMN reviews_count
SET DEFAULT 0;

ALTER TABLE trippy.business
ALTER COLUMN rating
SET DEFAULT 0;

CREATE TABLE trippy.review(
id SERIAL PRIMARY KEY,
user_id INT NOT NULL,
business_id INT NOT NULL,
rating INT NOT NULL,
feedback VARCHAR(100) NOT NULL,
stamp_created DATE NOT NULL,
FOREIGN KEY(user_id) REFERENCES trippy.user(id),
FOREIGN KEY(business_id) REFERENCES trippy.business(id));


# Starting the project
1.Pull the project from GitHub
2.Set the connection to the DB
3.Run Trippy.Spring

# Functionalities 
### Implemented
1.Creating users, unique by username
2.Create businesses, unique by their name and the city they are in
3.Leave a review for a specific business, by specific user
4.Show users : all/by email/by username/by id
5.Show businesses : all/by type/by city/by rating
6.Show all reviews : all/by id
7.Edit the business contact info(email or phone number)

### Not implemented
1.The rating that is shown for the business, must be /10
2.Only the author to be able to edit the review
3.24% test coverage
