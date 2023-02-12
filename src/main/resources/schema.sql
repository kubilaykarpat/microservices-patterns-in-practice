CREATE TABLE IF NOT EXISTS orders
(
    id      uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id uuid   NOT NULL,
    items   text[] NOT NULL
);