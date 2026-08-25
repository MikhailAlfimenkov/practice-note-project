--changeset Mikhail:3-add-priority-to-note
ALTER TABLE notes
    ADD COLUMN priority VARCHAR(20) DEFAULT 'MEDIUM';