CREATE TABLE Link
(
    id             BIGINT GENERATED ALWAYS AS IDENTITY,
    uri            TEXT                     NOT NULL,
    last_update_at TIMESTAMP WITH TIME ZONE NOT NULL,
    last_check_at TIMESTAMP WITH TIME ZONE NOT NULL,
    PRIMARY KEY (id)
);
