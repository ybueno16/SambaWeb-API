CREATE TABLE logs (
    id SERIAL PRIMARY KEY,
    log_description TEXT
);

CREATE TABLE serverconfig (
   id SERIAL PRIMARY KEY,
   ip VARCHAR(15) NOT NULL,
   port INT NOT NULL
);