CREATE TABLE IF NOT EXISTS deliveries
(
    id      uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    orderId uuid NOT NULL
);


CREATE TABLE IF NOT EXISTS outbox
(
    id           SERIAL PRIMARY KEY,
    aggregate_id VARCHAR(255),
    type         VARCHAR(255) NOT NULL,
    event_data   TEXT         NOT NULL,
    sent         BOOLEAN      NOT NULL DEFAULT FALSE,
    created_at   TIMESTAMP    NOT NULL DEFAULT now(),
    updated_at   TIMESTAMP    NOT NULL DEFAULT now()
);