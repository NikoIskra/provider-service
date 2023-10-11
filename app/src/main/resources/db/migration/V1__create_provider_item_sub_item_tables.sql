CREATE SEQUENCE IF NOT EXISTS provider_id_seq
    START 1000
    INCREMENT 1
    MINVALUE 1
    CACHE 1;

CREATE SEQUENCE IF NOT EXISTS item_id_seq
    START 1
    INCREMENT 1
    MINVALUE 1
    CACHE 1;

CREATE SEQUENCE IF NOT EXISTS sub_item_id_seq
    START 1
    INCREMENT 1
    MINVALUE 1
    CACHE 1;

CREATE TABLE IF NOT EXISTS provider
(
    id bigint NOT NULL DEFAULT nextval('provider_id_seq'),
    owner_id uuid NOT NULL,
    name character varying(64) NOT NULL,
    title character varying(128) NOT NULL,
    description character varying(512),
    phone_number character varying(16) NOT NULL,
    status character varying(16) NOT NULL,
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone,
    PRIMARY KEY (id),
    UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS item
(
    id bigint NOT NULL DEFAULT nextval('item_id_seq'),
    provider_id bigint,
    title character varying(128) NOT NULL,
    description character varying(512),
    price_cents integer NOT NULL,
    status character varying(16) NOT NULL,
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone,
    CONSTRAINT item_pkey PRIMARY KEY (id),
    CONSTRAINT provider_id_fk FOREIGN KEY (provider_id)
        REFERENCES provider (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS sub_item
(
    id bigint NOT NULL DEFAULT nextval('sub_item_id_seq'),
    item_id bigint,
    title character varying(128) NOT NULL,
    description character varying(512),
    price_cents integer NOT NULL,
    status character varying(16) NOT NULL,
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone,
    CONSTRAINT sub_item_pkey PRIMARY KEY (id),
    CONSTRAINT fk_item_id FOREIGN KEY (item_id)
        REFERENCES item (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
);