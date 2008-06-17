--
-- PostgreSQL database dump
--

SET client_encoding = 'SQL_ASCII';
SET check_function_bodies = false;

SET SESSION AUTHORIZATION 'postgres';

--
-- TOC entry 4 (OID 2200)
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
GRANT ALL ON SCHEMA public TO PUBLIC;


SET SESSION AUTHORIZATION 'postgres';

SET search_path = public, pg_catalog;

--
-- TOC entry 5 (OID 109877097)
-- Name: schema; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE "schema" (
    id integer NOT NULL,
    name character varying(50) NOT NULL,
    description character varying(200)
);


--
-- TOC entry 6 (OID 109877099)
-- Name: entity; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE entity (
    id integer NOT NULL,
    name character varying(50) NOT NULL,
    description character varying(200)
);


--
-- TOC entry 7 (OID 109877101)
-- Name: attribute; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE attribute (
    id integer NOT NULL,
    name character varying(50) NOT NULL,
    description character varying(200),
    entity_id integer,
    domain_id integer
);


--
-- TOC entry 8 (OID 109877103)
-- Name: domain; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE "domain" (
    id integer NOT NULL,
    name character varying(50) NOT NULL,
    description character varying(200)
);


--
-- TOC entry 9 (OID 109877105)
-- Name: values; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE "values" (
    id integer NOT NULL,
    value character varying(50),
    description character varying(200),
    domain_id integer
);


--
-- TOC entry 10 (OID 112412138)
-- Name: containment; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE containment (
    id integer NOT NULL,
    parent_id integer NOT NULL,
    child_id integer NOT NULL
);


--
-- TOC entry 11 (OID 114271187)
-- Name: relationship; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE relationship (
    id integer NOT NULL,
    name character varying(50) NOT NULL,
    left_id integer NOT NULL,
    left_min integer,
    left_max integer,
    right_id integer NOT NULL,
    right_min integer,
    right_max integer
);


--
-- TOC entry 12 (OID 114470417)
-- Name: extensions; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE extensions (
    source_id integer NOT NULL,
    attribute character varying(20),
    value integer NOT NULL
);


--
-- TOC entry 13 (OID 177304534)
-- Name: data_source; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE data_source (
    id integer NOT NULL,
    name character varying,
    url character varying,
    schema_id integer
);


--
-- Data for TOC entry 22 (OID 109877097)
-- Name: schema; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY "schema" (id, name, description) FROM stdin;
0	SIGACT Core	SIGACT Core
1	SIGACT + IED	S1
2	Casualties	S2
3	Vehicle	S3
4	Vehicle + Casualties	S4
6	Vehicle Ambush	S6
5	Convoy	S5
7	Convoy + Lost	S7
8	Convoy Ambush	S8
\.


--
-- Data for TOC entry 23 (OID 109877099)
-- Name: entity; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY entity (id, name, description) FROM stdin;
0	SigAct	Stores significant events
1	VehicleIncident	Stores information about vehicle associated with incident
\.


--
-- Data for TOC entry 24 (OID 109877101)
-- Name: attribute; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY attribute (id, name, description, entity_id, domain_id) FROM stdin;
12	VehicleStatus	Status of vehicle involved in action	1	1
13	ECMStatus	Status of ECM used during engagement	1	2
16	Detonator	Detonator used in IED	0	3
17	Concealment	Concealment method used for IED	0	4
18	HowSpotted	Method by which IED was spotted	0	5
20	PositionRelativetoVehicleHit	Position of vehicle relative to position of vehicle hit	1	6
4	Category	Type of event which occurred	0	0
0	SigactID	Significant Action ID	0	-1
2	Lat	Latitude associated with event	0	-1
3	Long	Longitude associated with event	0	-1
5	BlueKIA	Soldiers Killed	0	-1
6	BlueWIA	Soldiers Wounded	0	-1
7	EnemyKIA	Enemies Killed	0	-1
8	EnemyWIA	Enemies Wounded	0	-1
9	Detained	Enemies Detained	0	-1
10	ConvoyLength	Length of Convoy	0	-1
19	PositionInConvoy	Position of vehicle within convoy	1	-1
15	DistanceFromECMtoIED	Distance between the ECM and IED	1	-2
11	WireType	Type of wire found in IED	0	-3
14	ReasonIfECMOff	Reason why ECM was turned off during event	1	-3
1	EventTime	Time at which significant action occurred	0	-4
21	ECMLoadsetDate	Date on which ECM was installed on vehicle	1	-4
\.


--
-- Data for TOC entry 25 (OID 109877103)
-- Name: domain; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY "domain" (id, name, description) FROM stdin;
0	SigAct Categories	Domain containing significant actions which may occur
1	Vehicle Status	Domain containing various options for the current vehicle status
4	Concealment	Domain containing various concealment methods for IEDs
5	HowSpotted	Domain containing ways in which the IED may have been spotted
6	PositionRelativeToVehicleHit	Domain containing various positionings of the IED to the vehicle associated with this event
2	ECM Status	Domain containing various options for the current ECM status
3	Detonator	Domain containing various IED detonator types
-1	Integer	Domain covering all integer values
-2	Double	Domain covering all double values
-3	String	Domain covering all strings
-4	Date/Time	Domain consisting of date and/or time values
\.


--
-- Data for TOC entry 26 (OID 109877105)
-- Name: values; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY "values" (id, value, description, domain_id) FROM stdin;
3	IED	Action relates to IED encounter	0
0	SpecialOps	Action relates to special operations mission	0
1	Firefight	Action relates to firefight with combatants	0
2	Ambush	Action relates to ambush by combatants	0
4	disabled	Indicates that vehicle is disabled	1
5	operational	Indicates that vehicle is operational	1
6	on	Indicates that electronic counter measures were on during incident	2
7	off	Indicates that electronic counter measures were off during incident	2
8	N/A	Indicates that electronic counter measures were not available in vehicle involved in incident	2
9	CommandLink	Wired detonator	3
10	Proximity	Proximity sensing detonator	3
11	RF	Radio-frequency triggered detonator	3
12	bag	IED concealed in bag	4
13	dirt	IED concealed in dirt	4
14	concrete	IED concealed in concrete	4
15	animal carcass	IED concealed in animal carcass	4
16	blast crater	IED concealed in blast crater	4
17	foam	IED concealed in foam	4
18	garbage pile	IED concealed in garbage pile	4
19	vehicle	IED concealed in vehicle	4
20	tire	IED concealed in tire	4
21	tree	IED concealed in tree	4
22	guard rail	IED concealed in guard rail	4
23	unknown	IED concealment method unknown	4
24	none	IED not concealed	4
25	other	IED concealed by some unlisted method	4
26	visual	IED spotted visually	5
27	exploded	IED spotted when exploded	5
29	other	IED spotted through an unlisted method	5
28	thermal	IED spotted through thermal visioning	5
30	lost	Indicates that vehicle was destroyed	1
31	lost	Indicates that vehicle was stolen	1
32	self	Indicates that vehicle was vehicle attacked by IED	6
33	previous	Indicates that vehicle was in front of vehicle attacked by IED	6
34	next	Indicates that vehicle was behind vehicle attacked by IED	6
35	other	Indicates that vehicle was positioned in way other than listed	6
\.


--
-- Data for TOC entry 27 (OID 112412138)
-- Name: containment; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY containment (id, parent_id, child_id) FROM stdin;
\.


--
-- Data for TOC entry 28 (OID 114271187)
-- Name: relationship; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY relationship (id, name, left_id, left_min, left_max, right_id, right_min, right_max) FROM stdin;
0	SigAct-Vehicle Relationship	0	1	1	1	\N	\N
\.


--
-- Data for TOC entry 29 (OID 114470417)
-- Name: extensions; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY extensions (source_id, attribute, value) FROM stdin;
3	Add Relationship	0
0	Add Domain Value	0
0	Add Domain Value	1
0	Add Domain Value	2
1	Add Domain Value	3
3	Add Domain Value	4
3	Add Domain Value	5
3	Add Domain Value	6
3	Add Domain Value	7
3	Add Domain Value	8
5	Add Domain Value	9
5	Add Domain Value	10
5	Add Domain Value	11
6	Add Domain Value	12
6	Add Domain Value	13
6	Add Domain Value	14
6	Add Domain Value	15
6	Add Domain Value	16
6	Add Domain Value	17
6	Add Domain Value	18
6	Add Domain Value	19
6	Add Domain Value	20
6	Add Domain Value	21
6	Add Domain Value	22
6	Add Domain Value	23
6	Add Domain Value	24
6	Add Domain Value	25
6	Add Domain Value	26
6	Add Domain Value	27
6	Add Domain Value	28
6	Add Domain Value	29
6	Add Domain Value	30
7	Add Domain Value	31
8	Add Domain Value	32
8	Add Domain Value	33
8	Add Domain Value	34
8	Add Domain Value	35
0	Add Attribute	0
0	Add Attribute	1
0	Add Attribute	2
0	Add Attribute	3
0	Add Attribute	4
2	Add Attribute	5
2	Add Attribute	6
2	Add Attribute	7
2	Add Attribute	8
2	Add Attribute	9
3	Add Attribute	10
3	Add Attribute	11
3	Add Attribute	12
3	Add Attribute	13
3	Add Attribute	14
3	Add Attribute	15
5	Add Attribute	16
6	Add Attribute	17
6	Add Attribute	18
8	Add Attribute	19
8	Add Attribute	20
8	Add Attribute	21
0	Add Entity	0
1	Base Schema	0
2	Base Schema	1
4	Base Schema	2
4	Base Schema	3
5	Base Schema	4
6	Base Schema	4
7	Base Schema	5
8	Base Schema	5
8	Base Schema	6
3	Add Entity	1
3	Base Schema	1
\.


--
-- Data for TOC entry 30 (OID 177304534)
-- Name: data_source; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY data_source (id, name, url, schema_id) FROM stdin;
0	Kirkuk	http://brainsrv2.mitre.org:8090/mrald?database=db_ds1.props	0
2	Amarah	http://brainsrv2.mitre.org:8090/mrald?database=db_ds3.props	2
3	Karbala North	http://brainsrv2.mitre.org:8090/mrald?database=db_ds4.props	3
4	Karbala South	http://mm124911-pc.mitre.org:8080/mrald?database=db_ds5.props	3
5	Diwaniyan	http://mm124911-pc.mitre.org:8080/mrald?database=db_ds6.props	5
6	Ba'qubah West	http://mm124911-pc.mitre.org:8080/mrald?database=db_ds7.props	6
7	Ba'qubah East	http://mm124911-pc.mitre.org:8080/mrald?database=db_ds8.props	6
8	Basrah	http://powersim.mitre.org:8080/mrald?database=db_ds9.props	7
9	Baghdad	http://powersim.mitre.org:8080/mrald?database=db_ds10.props	8
10	Central Baghdad	http://powersim.mitre.org:8080/mrald?database=db_ds11.props	8
1	al-Rutbah	http://brainsrv2.mitre.org:8090/mrald?database=db_ds2.props	1
\.


--
-- TOC entry 14 (OID 109877111)
-- Name: schema_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "schema"
    ADD CONSTRAINT schema_pkey PRIMARY KEY (id);


--
-- TOC entry 15 (OID 109877113)
-- Name: entity_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY entity
    ADD CONSTRAINT entity_pkey PRIMARY KEY (id);


--
-- TOC entry 16 (OID 109877115)
-- Name: attribute_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY attribute
    ADD CONSTRAINT attribute_pkey PRIMARY KEY (id);


--
-- TOC entry 17 (OID 109877117)
-- Name: domain_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "domain"
    ADD CONSTRAINT domain_pkey PRIMARY KEY (id);


--
-- TOC entry 18 (OID 109877119)
-- Name: values_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "values"
    ADD CONSTRAINT values_pkey PRIMARY KEY (id);


--
-- TOC entry 19 (OID 112495734)
-- Name: containment_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY containment
    ADD CONSTRAINT containment_pkey PRIMARY KEY (id);


--
-- TOC entry 20 (OID 114327182)
-- Name: relationship_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY relationship
    ADD CONSTRAINT relationship_pkey PRIMARY KEY (id);


--
-- TOC entry 21 (OID 177304539)
-- Name: instance_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY data_source
    ADD CONSTRAINT instance_pkey PRIMARY KEY (id);


--
-- TOC entry 34 (OID 112515442)
-- Name: $1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY containment
    ADD CONSTRAINT "$1" FOREIGN KEY (parent_id) REFERENCES entity(id);


--
-- TOC entry 35 (OID 112518848)
-- Name: $2; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY containment
    ADD CONSTRAINT "$2" FOREIGN KEY (child_id) REFERENCES entity(id);


--
-- TOC entry 36 (OID 114346520)
-- Name: $1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY relationship
    ADD CONSTRAINT "$1" FOREIGN KEY (left_id) REFERENCES entity(id);


--
-- TOC entry 37 (OID 114349512)
-- Name: $2; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY relationship
    ADD CONSTRAINT "$2" FOREIGN KEY (right_id) REFERENCES entity(id);


--
-- TOC entry 38 (OID 114492817)
-- Name: $1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY extensions
    ADD CONSTRAINT "$1" FOREIGN KEY (source_id) REFERENCES "schema"(id);


--
-- TOC entry 31 (OID 177304526)
-- Name: $1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY attribute
    ADD CONSTRAINT "$1" FOREIGN KEY (entity_id) REFERENCES entity(id);


--
-- TOC entry 33 (OID 177304530)
-- Name: $1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "values"
    ADD CONSTRAINT "$1" FOREIGN KEY (domain_id) REFERENCES "domain"(id);


--
-- TOC entry 39 (OID 177304541)
-- Name: $1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY data_source
    ADD CONSTRAINT "$1" FOREIGN KEY (schema_id) REFERENCES "schema"(id);


--
-- TOC entry 32 (OID 193412948)
-- Name: $2; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY attribute
    ADD CONSTRAINT "$2" FOREIGN KEY (domain_id) REFERENCES "domain"(id);


--
-- TOC entry 3 (OID 2200)
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON SCHEMA public IS 'Standard public schema';


