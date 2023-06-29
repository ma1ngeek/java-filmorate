# java-filmorate
Template repository for Filmorate project.
https://raw.githubusercontent.com/ma1ngeek/java-filmorate/506f8ea0fbe57c8365a0644d01ff8afd67cf4ed8/db_scheme.png

https://github.com/ma1ngeek/java-filmorate/blob/add-friends-likes/dbdiagram.desc

#### Таблицы с Данными
- **USERS** список пользователей
- **FILMS** список фильмов
- **FRIENDS** связи дружбы между пользователями
- **LIKES** лайки пользователей
- **FILMRATES** связи между фильмами и рейтингом
- **MPARATE** рейтинг Ассоциации кинокомпаний
- **GENRES** список жанров
- **FILMGENRES** связь фильмов с жанрами

#### Примеры запросов
Список фильмов в жанре "Мультфильм"

*SELECT F.name FROM FILMS as F, FILMGENRES as FG, GENRES as G
WHERE F.id=FG.filmId AND FG.genreId=G.id AND G.name='Мультфильм'*

Список фильмов с рейтингом строго 18+

*SELECT F.name FROM FILMS as F, FILMRATES as R, MPARATE as M
WHERE F.id=R.filmId AND R.rateId=M.Id AND M.name='NC-17'*
