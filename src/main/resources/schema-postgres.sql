DROP TABLE IF EXISTS nl_book_genre;
DROP TABLE IF EXISTS nl_book_author;
DROP TABLE IF EXISTS nl_genre;
DROP TABLE IF EXISTS nl_author;
DROP TABLE IF EXISTS nl_book;

CREATE TABLE IF NOT EXISTS nl_book
(
    book_id serial,
    book_title varchar(100) not null,
    book_publisher varchar(50) not null,
    book_date_of_published date not null,
    book_country varchar(30) not null,
    book_description varchar(500) not null,
    book_file_name varchar(200) not null,
    PRIMARY KEY (book_id)
);

CREATE TABLE IF NOT EXISTS nl_author
(
    author_id serial,
    author_name varchar(50) not null UNIQUE,
    PRIMARY KEY (author_id)
);

CREATE TABLE IF NOT EXISTS nl_genre
(
    genre_id serial,
    genre_title varchar(50) not null UNIQUE,
    PRIMARY KEY (genre_id)
);

CREATE TABLE IF NOT EXISTS nl_book_author
(
    book_id integer not null,
    author_id integer not null,
    PRIMARY KeY (book_id, author_id),
    CONSTRAINT fk_book FOREIGN KEY (book_id) REFERENCES nl_book (book_id),
    CONSTRAINT fk_author FOREIGN KEY (author_id) REFERENCES nl_author (author_id)
);

CREATE TABLE IF NOT EXISTS nl_book_genre
(
    book_id integer not null,
    genre_id integer not null,
    PRIMARY KeY (book_id, genre_id),
    CONSTRAINT fk_book FOREIGN KEY (book_id) REFERENCES nl_book (book_id),
    CONSTRAINT fk_genre FOREIGN KEY (genre_id) REFERENCES nl_genre (genre_id)
);