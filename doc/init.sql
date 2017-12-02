--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.5
-- Dumped by pg_dump version 9.6.5

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

--
-- Name: spots_insert_trigger(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION spots_insert_trigger() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN

IF (
    NEW .inserted_at >= DATE '2017-01-01'
    AND NEW .inserted_at < DATE '2018-01-01'
) THEN
    INSERT INTO spots_2017
VALUES
    (NEW .*) ;
ELSEIF (
    NEW .inserted_at >= DATE '2018-01-01'
    AND NEW .inserted_at < DATE '2019-01-01'
) THEN
    INSERT INTO spots_2018
VALUES
    (NEW .*) ;
ELSEIF (
    NEW .inserted_at >= DATE '2019-01-01'
    AND NEW .inserted_at < DATE '2020-01-01'
) THEN
    INSERT INTO spots_2019
VALUES
    (NEW .*) ;
ELSE
    RAISE EXCEPTION 'Date out of range!' ;
END
IF ; RETURN NULL ;
END ; $$;


ALTER FUNCTION public.spots_insert_trigger() OWNER TO postgres;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: device; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE device (
    id integer NOT NULL,
    device_id character varying(50) NOT NULL,
    device_secret character varying(50) NOT NULL,
    device_name character varying(50) NOT NULL,
    status character varying(10) NOT NULL,
    product_id integer NOT NULL,
    user_id integer,
    create_time timestamp without time zone NOT NULL,
    last_online_time timestamp without time zone,
    last_offline_time timestamp(6) without time zone
);


ALTER TABLE device OWNER TO postgres;

--
-- Name: device_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE device_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE device_id_seq OWNER TO postgres;

--
-- Name: device_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE device_id_seq OWNED BY device.id;


--
-- Name: product; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE product (
    id integer NOT NULL,
    product_name character varying(50) NOT NULL,
    product_key character varying(50) NOT NULL,
    product_desc text,
    create_time timestamp(6) without time zone NOT NULL,
    modify_time timestamp(6) without time zone
);


ALTER TABLE product OWNER TO postgres;

--
-- Name: product_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE product_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE product_id_seq OWNER TO postgres;

--
-- Name: product_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE product_id_seq OWNED BY product.id;


--
-- Name: spots; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE spots (
    id integer NOT NULL,
    device_id integer NOT NULL,
    user_id integer NOT NULL,
    inserted_at timestamp without time zone NOT NULL,
    data jsonb
);


ALTER TABLE spots OWNER TO postgres;

--
-- Name: spots_2017; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE spots_2017 (
    CONSTRAINT spots_2017_inserted_at_check CHECK (((inserted_at >= '2017-01-01'::date) AND (inserted_at < '2018-01-01'::date)))
)
INHERITS (spots);


ALTER TABLE spots_2017 OWNER TO postgres;

--
-- Name: spots_2018; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE spots_2018 (
    CONSTRAINT spots_2018_inserted_at_check CHECK (((inserted_at >= '2018-01-01'::date) AND (inserted_at < '2019-01-01'::date)))
)
INHERITS (spots);


ALTER TABLE spots_2018 OWNER TO postgres;

--
-- Name: spots_2019; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE spots_2019 (
    CONSTRAINT spots_2019_inserted_at_check CHECK (((inserted_at >= '2019-01-01'::date) AND (inserted_at < '2020-01-01'::date)))
)
INHERITS (spots);


ALTER TABLE spots_2019 OWNER TO postgres;

--
-- Name: spots_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE spots_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE spots_id_seq OWNER TO postgres;

--
-- Name: spots_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE spots_id_seq OWNED BY spots.id;


--
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE users (
    id integer NOT NULL,
    username character varying(50) NOT NULL,
    login_name character varying(50) NOT NULL,
    encrypted_password character varying(50) NOT NULL,
    email character varying(50) NOT NULL,
    phone_number integer,
    address character varying(100),
    role_id integer NOT NULL,
    create_time timestamp without time zone NOT NULL,
    modify_time timestamp without time zone
);


ALTER TABLE users OWNER TO postgres;

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE users_id_seq OWNER TO postgres;

--
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE users_id_seq OWNED BY users.id;


--
-- Name: device id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY device ALTER COLUMN id SET DEFAULT nextval('device_id_seq'::regclass);


--
-- Name: product id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY product ALTER COLUMN id SET DEFAULT nextval('product_id_seq'::regclass);


--
-- Name: spots id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY spots ALTER COLUMN id SET DEFAULT nextval('spots_id_seq'::regclass);


--
-- Name: spots_2017 id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY spots_2017 ALTER COLUMN id SET DEFAULT nextval('spots_id_seq'::regclass);


--
-- Name: spots_2018 id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY spots_2018 ALTER COLUMN id SET DEFAULT nextval('spots_id_seq'::regclass);


--
-- Name: spots_2019 id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY spots_2019 ALTER COLUMN id SET DEFAULT nextval('spots_id_seq'::regclass);


--
-- Name: users id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY users ALTER COLUMN id SET DEFAULT nextval('users_id_seq'::regclass);


--
-- Data for Name: device; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY device (id, device_id, device_secret, device_name, status, product_id, user_id, create_time, last_online_time, last_offline_time) FROM stdin;
13	p2MUTWizQIy1KAraCWFJ	5UvZEV2ERJpEmlgaCuTTcbAKKjtEOSeJ	sc_school_203	OFFLINE	13	1	2017-12-01 10:37:23.579	2017-12-01 10:54:01	2017-12-01 10:54:01
11	CvOU80bqMTfKu0y5VKcN	0jQA5nXsE5hbMN4lnlut5HKwcWzx0L5Y	sc_school_202	OFFLINE	13	1	2017-11-28 17:05:55.34	2017-12-01 16:45:06	2017-12-01 16:45:06
\.


--
-- Name: device_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('device_id_seq', 13, true);


--
-- Data for Name: product; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY product (id, product_name, product_key, product_desc, create_time, modify_time) FROM stdin;
13	生毛豆室内1号产品	coLyASGcgao	生毛豆一代产品，可以用室内空气质量参数的收集与分析2	2017-11-28 16:40:52.327	2017-12-01 10:36:57.628
\.


--
-- Name: product_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('product_id_seq', 13, true);


--
-- Data for Name: spots; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY spots (id, device_id, user_id, inserted_at, data) FROM stdin;
\.


--
-- Data for Name: spots_2017; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY spots_2017 (id, device_id, user_id, inserted_at, data) FROM stdin;
24	13	1	2017-03-27 15:57:15.238	{"co": 2, "uv": 7, "co2": 200, "hum": 5, "ch2o": 100.1, "dust": 11, "pm25": 33.3, "noise": 7, "temper": 225, "air_pressure": 7, "light_intensity": 44.4}
25	11	1	2017-03-27 15:57:15.23	{"co": 1111111, "uv": 7, "co2": 200, "hum": 5, "ch2o": 100.1, "dust": 11, "pm25": 33.3, "noise": 7, "temper": 225, "air_pressure": 7, "light_intensity": 44.4}
\.


--
-- Data for Name: spots_2018; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY spots_2018 (id, device_id, user_id, inserted_at, data) FROM stdin;
\.


--
-- Data for Name: spots_2019; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY spots_2019 (id, device_id, user_id, inserted_at, data) FROM stdin;
\.


--
-- Name: spots_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('spots_id_seq', 25, true);


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY users (id, username, login_name, encrypted_password, email, phone_number, address, role_id, create_time, modify_time) FROM stdin;
2	测试用户	admin	123456	4354353@qq.com	123564564	\N	1	2017-11-30 20:19:58.638	\N
\.


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('users_id_seq', 5, true);


--
-- Name: device device_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY device
    ADD CONSTRAINT device_pkey PRIMARY KEY (id);


--
-- Name: product product_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY product
    ADD CONSTRAINT product_pkey PRIMARY KEY (id);


--
-- Name: spots spots_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY spots
    ADD CONSTRAINT spots_pkey PRIMARY KEY (id);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: index_product_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX index_product_id ON product USING btree (id);


--
-- Name: index_spots_2017_device_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX index_spots_2017_device_id ON spots_2017 USING btree (device_id);


--
-- Name: index_spots_2017_inserted_at; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX index_spots_2017_inserted_at ON spots_2017 USING btree (inserted_at);


--
-- Name: index_spots_2017_user_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX index_spots_2017_user_id ON spots_2017 USING btree (user_id);


--
-- Name: index_spots_2018_device_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX index_spots_2018_device_id ON spots_2018 USING btree (device_id);


--
-- Name: index_spots_2018_inserted_at; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX index_spots_2018_inserted_at ON spots_2018 USING btree (inserted_at);


--
-- Name: index_spots_2018_user_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX index_spots_2018_user_id ON spots_2018 USING btree (user_id);


--
-- Name: index_spots_2019_device_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX index_spots_2019_device_id ON spots_2019 USING btree (device_id);


--
-- Name: index_spots_2019_inserted_at; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX index_spots_2019_inserted_at ON spots_2019 USING btree (inserted_at);


--
-- Name: index_spots_2019_user_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX index_spots_2019_user_id ON spots_2019 USING btree (user_id);


--
-- Name: index_users_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX index_users_id ON users USING btree (id);


--
-- Name: spots spots_insert_trigger; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER spots_insert_trigger BEFORE INSERT ON spots FOR EACH ROW EXECUTE PROCEDURE spots_insert_trigger();


--
-- PostgreSQL database dump complete
--

