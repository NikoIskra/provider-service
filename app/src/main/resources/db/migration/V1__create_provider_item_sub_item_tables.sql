CREATE TABLE IF NOT EXISTS "provider-service".provider
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1000 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
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
)

CREATE TABLE IF NOT EXISTS item
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
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
        ON DELETE NO ACTION
)

CREATE TABLE IF NOT EXISTS sub_item
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
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
        ON DELETE NO ACTION
)