CREATE TABLE IF NOT EXISTS artists (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    biography TEXT,
    image_url VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT artists_name_not_empty CHECK (LENGTH(TRIM(name)) > 0)
);

COMMENT ON TABLE artists IS 'Tabla de artistas musicales';
COMMENT ON COLUMN artists.id IS 'Identificador único del artista';
COMMENT ON COLUMN artists.name IS 'Nombre del artista';
COMMENT ON COLUMN artists.biography IS 'Biografía o descripción del artista';
COMMENT ON COLUMN artists.image_url IS 'URL de la imagen/foto del artista';
COMMENT ON COLUMN artists.created_at IS 'Fecha de creación del registro';
COMMENT ON COLUMN artists.updated_at IS 'Fecha de última actualización';


CREATE TABLE IF NOT EXISTS albums (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    release_year INTEGER NOT NULL,
    cover_url VARCHAR(500),
    artist_id INTEGER NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT albums_name_not_empty CHECK (LENGTH(TRIM(name)) > 0),
    CONSTRAINT albums_year_valid CHECK (release_year >= 1900 AND release_year <= EXTRACT(YEAR FROM CURRENT_DATE) + 1),
    CONSTRAINT fk_albums_artist 
        FOREIGN KEY (artist_id) 
        REFERENCES artists(id) 
        ON DELETE CASCADE 
        ON UPDATE CASCADE
);

COMMENT ON TABLE albums IS 'Tabla de álbumes musicales';
COMMENT ON COLUMN albums.id IS 'Identificador único del álbum';
COMMENT ON COLUMN albums.name IS 'Nombre del álbum';
COMMENT ON COLUMN albums.release_year IS 'Año de lanzamiento del álbum';
COMMENT ON COLUMN albums.cover_url IS 'URL de la portada del álbum';
COMMENT ON COLUMN albums.artist_id IS 'ID del artista (clave foránea)';
COMMENT ON COLUMN albums.created_at IS 'Fecha de creación del registro';
COMMENT ON COLUMN albums.updated_at IS 'Fecha de última actualización';

-- Índice para mejorar búsquedas por artista
CREATE INDEX IF NOT EXISTS idx_albums_artist_id ON albums(artist_id);


CREATE TABLE IF NOT EXISTS songs (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    duration_seconds INTEGER NOT NULL,
    album_id INTEGER NOT NULL,
    track_number INTEGER,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT songs_title_not_empty CHECK (LENGTH(TRIM(title)) > 0),
    CONSTRAINT songs_duration_positive CHECK (duration_seconds > 0),
    CONSTRAINT songs_track_positive CHECK (track_number IS NULL OR track_number > 0),
    CONSTRAINT fk_songs_album 
        FOREIGN KEY (album_id) 
        REFERENCES albums(id) 
        ON DELETE CASCADE 
        ON UPDATE CASCADE
);

COMMENT ON TABLE songs IS 'Tabla de canciones musicales';
COMMENT ON COLUMN songs.id IS 'Identificador único de la canción';
COMMENT ON COLUMN songs.title IS 'Título de la canción';
COMMENT ON COLUMN songs.duration_seconds IS 'Duración en segundos';
COMMENT ON COLUMN songs.album_id IS 'ID del álbum (clave foránea)';
COMMENT ON COLUMN songs.track_number IS 'Número de pista en el álbum';
COMMENT ON COLUMN songs.created_at IS 'Fecha de creación del registro';
COMMENT ON COLUMN songs.updated_at IS 'Fecha de última actualización';

-- Índice para mejorar búsquedas por álbum
CREATE INDEX IF NOT EXISTS idx_songs_album_id ON albums(artist_id);


CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


-- Triggers para actualizar updated_at automáticamente
CREATE TRIGGER update_artists_updated_at
    BEFORE UPDATE ON artists
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_albums_updated_at
    BEFORE UPDATE ON albums
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_songs_updated_at
    BEFORE UPDATE ON songs
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();