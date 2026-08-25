--changeset Mikhail:4-insert-initial-authors
INSERT INTO authors (id, author_name, author_surname)
VALUES ('a1b2c3d4-e5f6-7a8b-9c0d-1e2f3a4b5c6d', 'John', 'Doe');

INSERT INTO notes (id, text_value, status, created_at, priority, author_id)
VALUES ('11111111-2222-3333-4444-555555555555',
        'First test note via Liquibase',
        'IN_PROGRESS',
        CURRENT_TIMESTAMP,
        'HIGH',
        'a1b2c3d4-e5f6-7a8b-9c0d-1e2f3a4b5c6d');