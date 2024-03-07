CREATE TABLE Link
(
    id             BIGINT GENERATED ALWAYS AS IDENTITY,
    url            TEXT                     NOT NULL,
    last_update_at TIMESTAMP WITH TIME ZONE NOT NULL,
    PRIMARY KEY (id)
);
