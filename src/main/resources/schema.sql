DROP TABLE IF EXISTS nl_bookmark;
DROP TABLE IF EXISTS nl_book_bookmark;
DROP TABLE IF EXISTS nl_users_role;
DROP TABLE IF EXISTS nl_users;
DROP TABLE IF EXISTS nl_role;
DROP TABLE IF EXISTS nl_chapters;
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
    book_year_published varchar(4) not null,
    book_country varchar(30) not null,
    book_description varchar(800) not null,
    book_file_name varchar(200) not null,
    book_poster_name varchar(200) not null,
    book_rating numeric(3, 2) DEFAULT 0.0,
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

CREATE TABLE IF NOT EXISTS nl_chapters
(
    chapter_id serial,
    chapter_name varchar(100) not null,
    chapter_text text not null,
    book_id integer not null,
    number_in_book integer not null,
    PRIMARY KeY (chapter_id),
    FOREIGN KEY (book_id) REFERENCES nl_book (book_id)
);

CREATE INDEX chapter_book_idx ON nl_chapters (book_id);

CREATE TABLE IF NOT EXISTS nl_role
(
    role_id serial,
    role_name varchar(20) UNIQUE not null,
    PRIMARY KEY (role_id)
);

CREATE TABLE IF NOT EXISTS nl_users
(
    user_id serial,
    user_name varchar(20) UNIQUE not null,
    user_password varchar(100) not null,
    user_created date not null,
    PRIMARY KEY (user_id)
);

CREATE TABLE nl_users_role (
    user_id integer NOT NULL,
    role_id integer NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES nl_users (user_id),
    CONSTRAINT fk_role FOREIGN KEY (role_id) REFERENCES nl_role (role_id)
);

CREATE TABLE IF NOT EXISTS nl_bookmark
(
    bookmark_id serial,
    bookmark_name varchar(20) not null,
    bookmark_type varchar(20) not null,
    user_id integer not null,
    PRIMARY KEY (bookmark_id),
    FOREIGN KEY (user_id) REFERENCES nl_users (user_id)
);

CREATE TABLE IF NOT EXISTS nl_book_bookmark
(
    book_id integer not null,
    bookmark_id integer not null,
    PRIMARY KeY (book_id, bookmark_id),
    CONSTRAINT fk_book FOREIGN KEY (book_id) REFERENCES nl_book (book_id),
    CONSTRAINT fk_bookmark FOREIGN KEY (bookmark_id) REFERENCES nl_bookmark (bookmark_id)
);




