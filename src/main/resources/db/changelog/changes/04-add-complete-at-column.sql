--changeset Mikhail:add-complete-at-column
ALTER TABLE notes
    ADD COLUMN complete_at TIMESTAMP;