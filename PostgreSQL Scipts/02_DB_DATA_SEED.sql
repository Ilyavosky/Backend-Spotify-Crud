INSERT INTO artists (name, biography, image_url) VALUES
('The Beatles', 'Banda de rock británica formada en Liverpool en 1960. Considerada la banda más influyente de todos los tiempos.', 'https://example.com/beatles.jpg'),
('Queen', 'Banda de rock británica formada en Londres en 1970. Conocida por su estilo innovador y teatral.', 'https://example.com/queen.jpg'),
('Pink Floyd', 'Banda de rock progresivo británica formada en Londres en 1965. Pioneros del rock psicodélico.', 'https://example.com/pink-floyd.jpg'),
('Led Zeppelin', 'Banda de hard rock británica formada en Londres en 1968. Una de las bandas más exitosas de la historia.', 'https://example.com/led-zeppelin.jpg'),
('Michael Jackson', 'Cantante, compositor y bailarín estadounidense. Conocido como el Rey del Pop.', 'https://example.com/michael-jackson.jpg');


INSERT INTO albums (name, release_year, cover_url, artist_id) VALUES
-- The Beatles (artist_id: 1)
('Abbey Road', 1969, 'https://example.com/abbey-road.jpg', 1),
('Sgt. Pepper''s Lonely Hearts Club Band', 1967, 'https://example.com/sgt-pepper.jpg', 1),
('The Beatles (White Album)', 1968, 'https://example.com/white-album.jpg', 1),

-- Queen (artist_id: 2)
('A Night at the Opera', 1975, 'https://example.com/night-opera.jpg', 2),
('News of the World', 1977, 'https://example.com/news-world.jpg', 2),
('The Game', 1980, 'https://example.com/the-game.jpg', 2),

-- Pink Floyd (artist_id: 3)
('The Dark Side of the Moon', 1973, 'https://example.com/dark-side.jpg', 3),
('The Wall', 1979, 'https://example.com/the-wall.jpg', 3),
('Wish You Were Here', 1975, 'https://example.com/wish-you.jpg', 3),

-- Led Zeppelin (artist_id: 4)
('Led Zeppelin IV', 1971, 'https://example.com/zeppelin-iv.jpg', 4),
('Physical Graffiti', 1975, 'https://example.com/physical-graffiti.jpg', 4),

-- Michael Jackson (artist_id: 5)
('Thriller', 1982, 'https://example.com/thriller.jpg', 5),
('Bad', 1987, 'https://example.com/bad.jpg', 5);


INSERT INTO songs (title, duration_seconds, album_id, track_number) VALUES
-- Abbey Road (album_id: 1)
('Come Together', 259, 1, 1),
('Something', 182, 1, 2),
('Here Comes the Sun', 185, 1, 7),
('Golden Slumbers', 91, 1, 13),

-- Sgt. Pepper's (album_id: 2)
('Sgt. Pepper''s Lonely Hearts Club Band', 122, 2, 1),
('With a Little Help from My Friends', 164, 2, 2),
('Lucy in the Sky with Diamonds', 207, 2, 3),
('A Day in the Life', 337, 2, 13),

-- A Night at the Opera (album_id: 4)
('Bohemian Rhapsody', 355, 4, 11),
('Love of My Life', 218, 4, 9),
('You''re My Best Friend', 170, 4, 7),

-- News of the World (album_id: 5)
('We Will Rock You', 122, 5, 1),
('We Are the Champions', 179, 5, 2),

-- The Dark Side of the Moon (album_id: 7)
('Speak to Me', 68, 7, 1),
('Breathe', 163, 7, 2),
('Time', 413, 7, 4),
('Money', 382, 7, 6),
('Us and Them', 462, 7, 7),

-- The Wall (album_id: 8)
('Another Brick in the Wall (Part 2)', 238, 8, 3),
('Comfortably Numb', 382, 8, 13),

-- Led Zeppelin IV (album_id: 10)
('Black Dog', 296, 10, 1),
('Rock and Roll', 220, 10, 2),
('Stairway to Heaven', 482, 10, 4),
('Going to California', 215, 10, 7),

-- Thriller (album_id: 12)
('Wanna Be Startin'' Somethin''', 363, 12, 1),
('Thriller', 357, 12, 4),
('Beat It', 258, 12, 5),
('Billie Jean', 294, 12, 6),

-- Bad (album_id: 13)
('Bad', 247, 13, 1),
('The Way You Make Me Feel', 299, 13, 2),
('Man in the Mirror', 320, 13, 9),
('Smooth Criminal', 257, 13, 7);