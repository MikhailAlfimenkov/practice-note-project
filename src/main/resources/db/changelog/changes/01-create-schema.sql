--changeset Mikhail:1-create-authors-table
CREATE TABLE authors
(
    id             UUID PRIMARY KEY,
    author_name    VARCHAR(255) NOT NULL,
    author_surname VARCHAR(255) NOT NULL
);

--changeset Mikhail:2-create-notes-table
CREATE TABLE notes
(
    id           UUID PRIMARY KEY,
    text_value   TEXT,
    status       VARCHAR(50) NOT NULL,
    created_at   TIMESTAMP WITHOUT TIME ZONE,
    completed_at TIMESTAMP WITHOUT TIME ZONE,
    author_id    UUID,
    CONSTRAINT fk_note_author FOREIGN KEY (author_id) REFERENCES authors (id) ON DELETE SET NULL
);
