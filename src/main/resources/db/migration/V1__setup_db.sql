CREATE TABLE store
(
    id   UUID NOT NULL,
    name text NOT NULL UNIQUE,
    CONSTRAINT store_pkey PRIMARY KEY (id)
);

CREATE TABLE product_group
(
    id           UUID NOT NULL,
    title        text NOT NULL,
    release_date DATE NOT NULL,
    CONSTRAINT product_group_pkey PRIMARY KEY (id)
);

CREATE TYPE distribution_type AS ENUM ('PHYSICAL', 'DIGITAL');
CREATE TYPE format_type AS ENUM ('VINYL', 'CD', 'CASSETTE', 'MP3', 'WAV', 'WAV24');

CREATE TABLE product
(
    id               UUID NOT NULL,
    title            text NOT NULL,
    distribution     distribution_type,
    format           format_type,
    price_gbp        NUMERIC,
    price_usd        NUMERIC,
    price_eur        NUMERIC,
    release_date     DATE NOT NULL,
    store_id         UUID NOT NULL,
    product_group_id UUID NOT NULL,
    CONSTRAINT product_pkey PRIMARY KEY (id),
    CONSTRAINT store_id_fkey FOREIGN KEY (store_id) REFERENCES store (id),
    CONSTRAINT product_group_id_fkey FOREIGN KEY (product_group_id) REFERENCES product_group (id)
);

CREATE INDEX store_name_idx ON store USING btree (name);
CREATE INDEX product_group_title_idx ON product_group USING btree (title);
CREATE INDEX product_group_release_date_idx ON product_group USING btree (release_date);
CREATE INDEX product_title_idx ON product USING btree (title);
CREATE INDEX product_release_date_idx ON product USING btree (release_date);