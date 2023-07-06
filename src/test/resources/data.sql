MERGE INTO mparate (id, name)
    VALUES (1, 'G'),
           (2, 'PG'),
           (3, 'PG-13'),
           (4, 'R'),
           (5, 'NC-17');

MERGE INTO genres (id, name)
    VALUES (1, 'Комедия'),
           (2, 'Драма'),
           (3, 'Мультфильм'),
           (4, 'Триллер'),
           (5, 'Документальный'),
           (6, 'Боевик');

INSERT INTO USERS (email, login, name, birthday) 
	VALUES ('user1@yandex.com','user1','user1','2005-05-10');
INSERT INTO USERS (email, login, name, birthday) 
	VALUES ('user2@yandex.com','user2','user2','2005-07-14');
INSERT INTO USERS (email, login, name, birthday) 
	VALUES ('user3@yandex.com','user3','user3','2005-09-18');

INSERT INTO FRIENDS (user_Id, friend_Id) VALUES (1,2);
INSERT INTO FRIENDS (user_Id, friend_Id) VALUES (1,3);
INSERT INTO FRIENDS (user_Id, friend_Id) VALUES (2,3);

INSERT INTO FILMS (name, description, release_Date, duration) VALUES 
	('film 1','description 1','2022-01-21',90);
INSERT INTO FILMS (name, description, release_Date, duration) VALUES 
	('film 2','description 2','2022-02-11',110);

INSERT INTO LIKES (film_Id, user_Id) VALUES (1,2);
INSERT INTO LIKES (film_Id, user_Id) VALUES (2,1);
INSERT INTO LIKES (film_Id, user_Id) VALUES (2,3);

INSERT INTO FILMGENRES (film_Id, genre_Id) VALUES (1, 3);
INSERT INTO FILMGENRES (film_Id, genre_Id) VALUES (1, 1);
