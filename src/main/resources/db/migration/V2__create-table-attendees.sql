CREATE TABLE attendees (
    id VARCHAR(255) NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    event_id VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT attendees_event_id_fk FOREIGN KEY (event_id) REFERENCES events(id)
    ON DELETE RESTRICT -- não é possível deletar um evento caso haja participantes cadastrados nele
    ON UPDATE CASCADE -- se o id do evento for alterado, o id do evento associado ao participante também será alterado
);
