--
-- PostgreSQL database dump
--

SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: alias; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE alias (
    id bigint NOT NULL,
    element_id bigint,
    name character varying
);


ALTER TABLE public.alias OWNER TO postgres;

--
-- Name: annotation; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE annotation (
    object_id bigint,
    attribute character varying,
    value character varying
);


ALTER TABLE public.annotation OWNER TO postgres;

--
-- Name: attribute; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE attribute (
    id bigint NOT NULL,
    name character varying(50) NOT NULL,
    description character varying(200),
    entity_id bigint,
    domain_id bigint,
    min bigint,
    max bigint
);


ALTER TABLE public.attribute OWNER TO postgres;

--
-- Name: containment; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE containment (
    id bigint NOT NULL,
    parent_id bigint NOT NULL,
    child_id bigint NOT NULL,
    min bigint,
    max bigint,
    description character varying,
    name character varying
);


ALTER TABLE public.containment OWNER TO postgres;

--
-- Name: data_source; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE data_source (
    id bigint NOT NULL,
    name character varying,
    url character varying,
    schema_id bigint
);


ALTER TABLE public.data_source OWNER TO postgres;

--
-- Name: domain; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE domain (
    id bigint NOT NULL,
    name character varying(50) NOT NULL,
    description character varying(200)
);


ALTER TABLE public.domain OWNER TO postgres;

--
-- Name: domainvalue; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE domainvalue (
    id bigint NOT NULL,
    value character varying(50),
    description character varying(200),
    domain_id bigint
);


ALTER TABLE public.domainvalue OWNER TO postgres;

--
-- Name: entity; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE entity (
    id bigint NOT NULL,
    name character varying(50) NOT NULL,
    description character varying(200)
);


ALTER TABLE public.entity OWNER TO postgres;

--
-- Name: extensions; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE extensions (
    schema_id bigint NOT NULL,
    action character varying(20),
    element_id bigint NOT NULL
);


ALTER TABLE public.extensions OWNER TO postgres;

--
-- Name: groups; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE groups (
    id integer NOT NULL,
    name character varying NOT NULL,
    parent_id integer
);


ALTER TABLE public.groups OWNER TO postgres;

--
-- Name: mapping; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE mapping (
    id bigint NOT NULL,
    author character varying,
    description character varying,
    name character varying
);


ALTER TABLE public.mapping OWNER TO postgres;

--
-- Name: mapping_cell; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE mapping_cell (
    id bigint NOT NULL,
    mapping_id bigint,
    element1_id bigint,
    element2_id bigint,
    score numeric,
    scorer character varying,
    validated boolean
);


ALTER TABLE public.mapping_cell OWNER TO postgres;

--
-- Name: mapping_schema; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE mapping_schema (
    mapping_id bigint NOT NULL,
    schema_id bigint NOT NULL
);


ALTER TABLE public.mapping_schema OWNER TO postgres;

--
-- Name: relationship; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE relationship (
    id bigint NOT NULL,
    name character varying(50) NOT NULL,
    left_id bigint NOT NULL,
    left_min integer,
    left_max integer,
    right_id bigint NOT NULL,
    right_min integer,
    right_max integer
);


ALTER TABLE public.relationship OWNER TO postgres;

--
-- Name: schema; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE schema (
    id bigint NOT NULL,
    name character varying(50) NOT NULL,
    author character varying(200) NOT NULL,
    source character varying(200) NOT NULL,
    type character varying(50) NOT NULL,
    description character varying(200),
    locked boolean NOT NULL
);


ALTER TABLE public.schema OWNER TO postgres;

--
-- Name: schema_element; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE schema_element (
    id bigint NOT NULL,
    type character varying
);


ALTER TABLE public.schema_element OWNER TO postgres;

--
-- Name: schema_group; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE schema_group (
    group_id integer NOT NULL,
    schema_id integer NOT NULL
);


ALTER TABLE public.schema_group OWNER TO postgres;

--
-- Name: subtype; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE subtype (
    id bigint,
    parent_id bigint,
    child_id bigint
);


ALTER TABLE public.subtype OWNER TO postgres;

--
-- Name: universalseq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE universalseq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.universalseq OWNER TO postgres;

--
-- Name: universalseq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('universalseq', 52363, true);


--
-- Data for Name: alias; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY alias (id, element_id, name) FROM stdin;
\.


--
-- Data for Name: annotation; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY annotation (object_id, attribute, value) FROM stdin;
\.


--
-- Data for Name: attribute; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY attribute (id, name, description, entity_id, domain_id, min, max) FROM stdin;
48	DOB	The organism's date of birth	20	-4	\N	\N
52	VisitDate	The date associated with a particular diagnosis	21	-4	\N	\N
53	Diagnosis	The organism's health status on a particular date	21	-3	\N	\N
54	Start	The date the protocol started	23	-4	\N	\N
55	Stop	The projected or actual stop date	23	-4	\N	\N
56	Funding	The name of the agency funding the protocol	23	-3	\N	\N
57	GrantNum	The grant identifier provided by the funding agency	23	-1	\N	\N
58	MeasurementDate	Date on which the measurements were acquired	22	-4	\N	\N
59	Ht	The organism's height (in inches)	22	-1	\N	\N
60	Wt	The organism's weight (in pounds)	22	-1	\N	\N
64	Hglb	Hemoglobin count	22	-1	\N	\N
65	TotProt	Total protein count	22	-1	\N	\N
66	Alb	Albumin count	22	-1	\N	\N
67	hdl	Indicates the measured high-density lipids	22	-1	\N	\N
68	ldl	Indicates the measured low-density lipids	22	-1	\N	\N
69	XRes	X-resolution	24	-2	\N	\N
70	YRes	Y-resolution	24	-2	\N	\N
71	ZRes	Z-resolution	24	-2	\N	\N
47	Species	The species of the test organism	20	81	\N	\N
49	Gender	The organism's gender	20	83	\N	\N
50	ConsentStatus	Indicates the subject's willingness to be contacted	20	84	\N	\N
51	Race	Indicates a human subject's race/ethnicity	20	85	\N	\N
61	VerbalMemory	Stores the subject's score on a verbal memory test	22	95	\N	\N
62	VisualMemory	Stores the subject's score on a visual memory test	22	95	\N	\N
63	Handedness	Stores the subject's observed handedness	22	97	\N	\N
72	Modality	Modality	24	106	\N	\N
73	PrimaryLanguage	NULL	22	107	\N	\N
\.


--
-- Data for Name: containment; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY containment (id, parent_id, child_id, min, max, description, name) FROM stdin;
410	20	22	\N	\N	\N	\N
412	22	24	\N	\N	\N	\N
\.


--
-- Data for Name: data_source; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY data_source (id, name, url, schema_id) FROM stdin;
426	ProtocolSubject	http://localhost:8080/mrald?database=db_ProtocolSubject.props	10
430	ObesityStudy	http://localhost:8080/mrald?database=db_ObesityStudy.props	14
432	NeuroExtendedLanguage	http://localhost:8080/mrald?database=db_NeuroExtendedLanguage.props	16
433	AfricanNeuro	http://localhost:8080/mrald?database=db_AfricanNeuro.props	17
424	Organism	http://localhost:8080/mrald?database=db_Organism.props	400
\.


--
-- Data for Name: domain; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY domain (id, name, description) FROM stdin;
-1	Integer	Domain covering all integer values
-2	Double	Domain covering all double values
-3	String	Domain covering all strings
-4	Timestamp	Domain consisting of date and/or time values
-5	Boolean	Domain covering all boolean values
-6	Any	Domain covering all values
81	Species	Codes for common species
83	Gender	Codes for gender
84	Status	Codes describing a patient's willingness to participate in a study
85	Race	Codes for common human races
95	Memory	Codes for common memory tests
97	Handedness	Codes for handedness
106	Modality	Codes describing the technique used to acquire an image
107	Language	Codes for common languages
\.


--
-- Data for Name: domainvalue; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY domainvalue (id, value, description, domain_id) FROM stdin;
144	homo Sapiens		81
145	mauesi Mittermeier		81
146	cinerea Nadler		81
164	Male	XY male	83
165	Female	XX female	83
166	Male (ZZ)	ZZ male (for certain species)	83
167	Female (WZ)	WZ female (for certain species)	83
174	Contacted	Subject was contacted, but has not responded	84
175	Accepted	Subject agreed to be contacted about future studies	84
176	Declined	Subject asked not to be contacted further	84
184	W	W (from the DoJ)	85
185	A	Asian or Pacific Islander (from the DoJ)	85
186	B	Black (from the DoJ)	85
187	I	American Indian or Pacific Islander (from the DoJ)	85
188	U	Unknown (from the DoJ)	85
284	Lo	A score below 25	95
285	Med	A score between 25 and 75	95
286	Hi	A score above 75	95
304	L	Left-handed	97
305	R	Right-handed	97
306	A	Ambidextrous	97
307	0	Completely left-handed	97
308	1	Mostly left-handed	97
309	2	Ambidextrous	97
310	3	Mostly right-handed	97
311	4	Completely right-handed	97
394	MRI	MRI	106
395	CT	CT	106
396	DTI	DTI	106
404	Eng	English	107
405	Fr	French	107
406	Sp	Spanish	107
407	Bi	Bilingual	107
408	Bi	Kirundi (spoken in Burundi)	107
\.


--
-- Data for Name: entity; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY entity (id, name, description) FROM stdin;
20	Organism	Information about any organism for which health information is available
21	HealthHistory	Information about an organism's health history
22	Measurement	Data recorded about a test subject at a particular time
23	Protocol	Information about the scientific protocol associated with a measurement
24	Image	Metadata associated with a particular image
\.


--
-- Data for Name: extensions; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY extensions (schema_id, action, element_id) FROM stdin;
10	Base Schema	9
11	Base Schema	10
12	Base Schema	10
13	Base Schema	11
13	Base Schema	12
14	Base Schema	13
15	Base Schema	12
16	Base Schema	15
17	Base Schema	15
10	Add	22
10	Add	23
12	Add	24
10	Add	54
9	Add	50
9	Add	51
10	Add	55
10	Add	56
10	Add	57
10	Add	58
10	Add	59
10	Add	60
15	Add	61
15	Add	62
15	Add	63
11	Add	64
11	Add	65
11	Add	66
14	Add	67
14	Add	68
12	Add	69
12	Add	70
12	Add	71
12	Add	72
15	Add	73
9	Add	174
9	Add	175
9	Add	176
9	Add	184
9	Add	185
9	Add	186
9	Add	187
9	Add	188
15	Add	284
15	Add	285
15	Add	286
15	Add	304
15	Add	305
17	Add	306
16	Add	307
16	Add	308
16	Add	309
16	Add	310
16	Add	311
12	Add	394
12	Add	395
12	Add	396
15	Add	404
15	Add	405
15	Add	406
16	Add	407
17	Add	408
10	Add	410
12	Add	412
10	Add	435
9	Add	84
9	Add	85
15	Add	95
15	Add	97
12	Add	106
15	Add	107
400	Add	20
400	Add	21
400	Add	47
400	Add	48
400	Add	49
400	Add	52
400	Add	53
400	Add	144
400	Add	145
400	Add	146
400	Add	164
400	Add	165
400	Add	166
400	Add	167
400	Add	81
400	Add	83
9	Base Schema	400
\.


--
-- Data for Name: groups; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY groups (id, name, parent_id) FROM stdin;
\.


--
-- Data for Name: mapping; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY mapping (id, author, description, name) FROM stdin;
\.


--
-- Data for Name: mapping_cell; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY mapping_cell (id, mapping_id, element1_id, element2_id, score, scorer, validated) FROM stdin;
\.


--
-- Data for Name: mapping_schema; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY mapping_schema (mapping_id, schema_id) FROM stdin;
\.


--
-- Data for Name: relationship; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY relationship (id, name, left_id, left_min, left_max, right_id, right_min, right_max) FROM stdin;
435	CapturedFor	22	1	1	23	0	\N
\.


--
-- Data for Name: schema; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY schema (id, name, author, source, type, description, locked) FROM stdin;
9	Human Patient	Chris Wolf		Manual	Information about a human patient that could be recruited into a study	t
10	Protocol Subject	Chris Wolf		Manual	Information about a subject enrolled in one or more protocols	t
11	Chemlab Subject	Chris Wolf		Manual	Information about a subject with chem-lab analyses	t
12	Imaging Subject	Chris Wolf		Manual	Additional information about imaging studies and associated metadata	t
13	Lab + Imaging	Chris Wolf		Manual	Merge of laboratory and imaging measurements	t
15	Neuro-Imaging Study	Chris Wolf		Manual	Additional measurements necessary to interpret brain scans	t
16	Neuro w/ Extended Language	Chris Wolf		Manual	Adds additional language codes needed in many locations	t
17	African Neuro	Chris Wolf		Manual	Adds languages common in Africa	t
14	Obesity Study	Chris Wolf		Manual	Additional lab measurements for obesity-related studies	t
400	Organism	Chris Wolf		Manual	Root for living things	t
\.


--
-- Data for Name: schema_element; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY schema_element (id, type) FROM stdin;
-1	Domain
-2	Domain
-3	Domain
-4	Domain
-5	Domain
-6	Domain
9	Schema
10	Schema
11	Schema
12	Schema
13	Schema
15	Schema
20	Entity
21	Entity
22	Entity
23	Entity
24	Entity
48	Attribute
52	Attribute
53	Attribute
54	Attribute
55	Attribute
56	Attribute
57	Attribute
58	Attribute
59	Attribute
60	Attribute
64	Attribute
65	Attribute
66	Attribute
67	Attribute
68	Attribute
69	Attribute
70	Attribute
71	Attribute
47	Attribute
49	Attribute
50	Attribute
51	Attribute
61	Attribute
62	Attribute
63	Attribute
72	Attribute
73	Attribute
81	Domain
83	Domain
84	Domain
85	Domain
95	Domain
97	Domain
106	Domain
107	Domain
144	Values
145	Values
146	Values
164	Values
165	Values
166	Values
167	Values
174	Values
175	Values
176	Values
184	Values
185	Values
186	Values
187	Values
188	Values
284	Values
285	Values
286	Values
304	Values
305	Values
306	Values
307	Values
308	Values
309	Values
310	Values
311	Values
394	Values
395	Values
396	Values
404	Values
405	Values
406	Values
407	Values
408	Values
435	Relationship
410	Containment
412	Containment
400	Schema
\.


--
-- Data for Name: schema_group; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY schema_group (group_id, schema_id) FROM stdin;
\.


--
-- Data for Name: subtype; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY subtype (id, parent_id, child_id) FROM stdin;
\.


--
-- Name: alias_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY alias
    ADD CONSTRAINT alias_pkey PRIMARY KEY (id);


--
-- Name: attribute_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY attribute
    ADD CONSTRAINT attribute_pkey PRIMARY KEY (id);


--
-- Name: containment_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY containment
    ADD CONSTRAINT containment_pkey PRIMARY KEY (id);


--
-- Name: domain_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY domain
    ADD CONSTRAINT domain_pkey PRIMARY KEY (id);


--
-- Name: entity_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY entity
    ADD CONSTRAINT entity_pkey PRIMARY KEY (id);


--
-- Name: groups_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY groups
    ADD CONSTRAINT groups_pkey PRIMARY KEY (id);


--
-- Name: instance_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY data_source
    ADD CONSTRAINT instance_pkey PRIMARY KEY (id);


--
-- Name: mapping_cell_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY mapping_cell
    ADD CONSTRAINT mapping_cell_pkey PRIMARY KEY (id);


--
-- Name: mapping_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY mapping
    ADD CONSTRAINT mapping_pkey PRIMARY KEY (id);


--
-- Name: relationship_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY relationship
    ADD CONSTRAINT relationship_pkey PRIMARY KEY (id);


--
-- Name: schema_object_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY schema_element
    ADD CONSTRAINT schema_object_pkey PRIMARY KEY (id);


--
-- Name: schema_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY schema
    ADD CONSTRAINT schema_pkey PRIMARY KEY (id);


--
-- Name: values_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY domainvalue
    ADD CONSTRAINT values_pkey PRIMARY KEY (id);


--
-- Name: schema_group_idx; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE UNIQUE INDEX schema_group_idx ON schema_group USING btree (schema_id, group_id);


--
-- Name: $1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY domainvalue
    ADD CONSTRAINT "$1" FOREIGN KEY (domain_id) REFERENCES domain(id);


--
-- Name: $1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY attribute
    ADD CONSTRAINT "$1" FOREIGN KEY (entity_id) REFERENCES entity(id);


--
-- Name: $1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY relationship
    ADD CONSTRAINT "$1" FOREIGN KEY (left_id) REFERENCES entity(id);


--
-- Name: $1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY extensions
    ADD CONSTRAINT "$1" FOREIGN KEY (schema_id) REFERENCES schema(id);


--
-- Name: $1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY data_source
    ADD CONSTRAINT "$1" FOREIGN KEY (schema_id) REFERENCES schema(id);


--
-- Name: $1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY groups
    ADD CONSTRAINT "$1" FOREIGN KEY (parent_id) REFERENCES groups(id);


--
-- Name: $1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY schema_group
    ADD CONSTRAINT "$1" FOREIGN KEY (group_id) REFERENCES groups(id);


--
-- Name: $2; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY attribute
    ADD CONSTRAINT "$2" FOREIGN KEY (domain_id) REFERENCES domain(id);


--
-- Name: $2; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY relationship
    ADD CONSTRAINT "$2" FOREIGN KEY (right_id) REFERENCES entity(id);


--
-- Name: $4; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY schema_group
    ADD CONSTRAINT "$4" FOREIGN KEY (schema_id) REFERENCES schema(id);


--
-- Name: attribute_domain_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY attribute
    ADD CONSTRAINT attribute_domain_id_fkey FOREIGN KEY (domain_id) REFERENCES domain(id);


--
-- Name: mapping_cell_mapping_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY mapping_cell
    ADD CONSTRAINT mapping_cell_mapping_id_fkey FOREIGN KEY (mapping_id) REFERENCES mapping(id);


--
-- Name: mapping_schema_mapping_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY mapping_schema
    ADD CONSTRAINT mapping_schema_mapping_id_fkey FOREIGN KEY (mapping_id) REFERENCES mapping(id);


--
-- Name: mapping_schema_schema_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY mapping_schema
    ADD CONSTRAINT mapping_schema_schema_id_fkey FOREIGN KEY (schema_id) REFERENCES schema(id);


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

