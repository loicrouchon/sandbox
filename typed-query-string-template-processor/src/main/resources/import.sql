insert into Author (id, firstName, lastName)
values (1, 'Simone', 'de Beauvoir'),
       (2, 'Jean-Paul', 'Sartre'),
       (3, 'Richard P.', 'Feynman'),
       (4, 'Robert B.', 'Leighton'),
       (5, 'Matthew', 'Sands');
insert into Book (id, title, publishingYear)
values (1, 'The Second Sex', 1949),
       (2, 'She Came to Stay', 1943),
       (3, 'Existentialist ethics', 1944),
       (4, 'No Exit', 1944),
       (5, 'Nausea', 1938),
       (6, 'Being and Nothingness', 1943),
       (7, 'The Feynman Lectures on Physics', 1964);

insert into Book_Author (authors_id, books_id)
values (1, 1),
       (1, 2),
       (1, 3),
       (2, 4),
       (2, 5),
       (2, 6),
       (3, 7),
       (4, 7),
       (5, 7);
