CREATE TABLE books (
	id serial primary key,
	title VARCHAR(256) NOT NULL,
	year_of_release INT,
	codeISBN VARCHAR(256)
);

CREATE TABLE authors (
	id serial primary key,
	name VARCHAR(256) NOT NULL
);

CREATE TABLE authors_of_books (
	id serial primary key,
	book_id INT REFERENCES books(id),
	author_id INT REFERENCES authors(id)
);

INSERT INTO
books(title, year_of_release, codeISBN)
VALUES
('Harry Potter', 1997, 's-001'),
('War and peace', 1865, 's-002'),
('Loly pop', 2015, 's-003');

INSERT INTO
authors(name)
VALUES
('J.K. Rowling'),
('Leo Tolstoy'),
('Pitter'),
('Villy');

INSERT INTO
authors_of_books(book_id, author_id)
VALUES
(1, 1),
(2, 2),
(3, 4);