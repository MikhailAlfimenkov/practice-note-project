CREATE TABLE IF NOT EXISTS authors(
    id UUID PRIMARY KEY,
    author_name VARCHAR(50),
    author_surname VARCHAR(50)
);
CREATE TABLE IF NOT EXISTS notes(
    id UUID PRIMARY KEY,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    completed_at TIMESTAMP,
    text_value TEXT NOT NULL,
    author_id UUID NOT NULL,
    CONSTRAINT fk_author FOREIGN KEY (author_id) REFERENCES authors(id) ON DELETE CASCADE
);
