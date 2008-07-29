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

SELECT pg_catalog.setval('universalseq', 52762, true);


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
52369	Code		52366	-3	1	1
52380	Title		52378	-3	1	1
52400	Code		52398	-3	1	1
52418	Kodu		52416	-3	1	1
52458	Nummer		52454	-3	1	1
52459	Typ		52456	-3	1	1
52540	Code		52538	-3	1	1
52589	Name		52586	-3	1	1
52590	offeredTerm		52587	-3	1	1
52591	level		52587	-3	1	1
52592	No		52587	-3	1	1
52593	Email		52586	-3	1	1
52620	title		52616	-3	1	1
52621	day		52614	-3	1	1
52622	period		52614	-3	1	1
52623	code		52616	-3	1	1
52624	bldg		52618	-3	1	1
52625	room		52618	-3	1	1
52678	catalognumber		52676	-3	1	1
52679	subject		52676	-3	1	1
52691	Section		52689	-3	1	1
52692	CourseID		52689	-3	1	1
52716	Type		52712	-3	1	1
52736	No		52734	-3	1	1
52757	No		52754	-3	1	1
\.


--
-- Data for Name: containment; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY containment (id, parent_id, child_id, min, max, description, name) FROM stdin;
410	20	22	\N	\N	\N	\N
412	22	24	\N	\N	\N	\N
52370	52366	-3	0	1		Instructor
52371	52366	-3	0	1		Day
52372	52367	52366	0	-1		Course
52373	52365	52367	0	1		arizona
52374	52366	-3	0	1		Time
52375	52366	-3	0	1		Place
52381	52377	52378	0	-1		Course
52382	52376	52377	0	1		asu
52383	52378	-3	0	1		Description
52384	52378	-3	0	1		MoreInfo.URL
52389	52385	52386	0	1		berkeley
52390	52387	-3	0	1		Code
52391	52387	-3	0	1		Name
52392	52386	52387	0	-1		Course
52393	52387	-3	0	1		Time
52394	52387	-3	0	1		SectionCredit
52395	52387	-3	0	1		Location
52401	52398	-3	0	1		Quota2
52402	52398	-3	0	1		Schedule
52403	52398	-3	0	1		Group
52404	52398	-3	0	1		Limit2
52405	52398	-3	0	1		Instructor1
52406	52398	-3	0	1		Limit1
52407	52398	-3	0	1		CourseName
52408	52398	-3	0	1		Quota1
52409	52398	-3	0	1		Instructor2
52410	52396	52397	0	1		bilkent
52411	52398	-3	0	1		Syllabus
52412	52398	-3	0	1		GPA
52413	52397	52398	0	-1		Section
52419	52414	52415	0	1		boun
52420	52415	52416	0	-1		Ders
52421	52416	-3	0	1		ismi
52422	52416	-3	0	1		Kredi
52427	52424	-3	0	1		Room
52428	52424	-3	0	1		Title
52429	52424	-3	0	1		Instructor
52430	52423	52425	0	1		brown
52431	52425	52424	0	-1		Course
52432	52424	-3	0	1		Code
52437	52434	-3	0	1		CourseTitle
52438	52434	-3	0	1		Lecturer
52439	52434	-3	0	1		Room
52440	52434	-3	0	1		Day
52441	52434	-3	0	1		CourseX-Listed
52442	52434	-3	0	1		Code
52443	52434	-3	0	1		Time
52444	52433	52435	0	1		cmu
52445	52434	-3	0	1		Units
52446	52434	-3	0	1		Sec
52447	52435	52434	0	-1		Course
52451	52448	52449	0	1		cornell
52452	52449	-3	0	1		Course
52460	52455	52456	0	-1		UnterrichtTyp
52461	52454	-3	0	1		Titel
52462	52456	52454	0	-1		Unterricht
52463	52454	-3	0	1		Sprache
52464	52454	-3	0	1		Homepage
52465	52454	-3	0	1		Dozent
52466	52453	52455	0	1		ethz
52467	52454	-3	0	1		Umfang
52472	52470	-3	0	1		Coreq
52473	52468	52469	0	1		fiu
52474	52470	-3	0	1		Prereq
52475	52470	-3	0	1		Title
52476	52469	52470	0	-1		Course
52477	52470	-3	0	1		Description
52482	52479	-3	0	1		Title
52483	52478	52480	0	1		furman
52484	52479	-3	0	1		Code
52485	52479	-3	0	1		Times
52486	52480	52479	0	-1		Course
52487	52479	-3	0	1		Room
52488	52479	-3	0	1		Instructor
52493	52491	-3	0	1		Room
52494	52491	-3	0	1		Instructor
52495	52491	-3	0	1		CRN
52496	52491	-3	0	1		Section
52497	52491	-3	0	1		Mode
52498	52491	-3	0	1		In
52499	52491	-3	0	1		Max
52500	52491	-3	0	1		Description
52501	52491	-3	0	1		Hours
52502	52491	-3	0	1		Time
52503	52491	-3	0	1		Department
52504	52489	52490	0	1		gatech
52505	52490	52491	0	-1		Course
52506	52491	-3	0	1		Days
52507	52491	-3	0	1		Title
52508	52491	-3	0	1		Code
52509	52491	-3	0	1		Building
52514	52512	-3	0	1		Description
52515	52512	-3	0	1		Title
52516	52512	-3	0	1		Instructor
52517	52512	-3	0	1		Notes
52518	52512	-3	0	1		Prereqs
52519	52512	-3	0	1		Details
52520	52511	52512	0	-1		Course
52521	52510	52511	0	1		harvard
52522	52512	-3	0	1		Number
52528	52523	52526	0	1		itu
52529	52524	-3	0	1		Adi
52530	52524	-3	0	1		Kredisi
52531	52524	-3	0	1		Turu
52532	52526	52525	0	-1		Yariyil
52533	52525	-3	0	1		ismi
52534	52524	-3	0	1		Kodu
52535	52525	52524	0	-1		Ders
52541	52538	-3	0	1		DetailedInformation
52542	52536	52537	0	1		metu
52543	52538	-3	0	1		CreditInformation
52544	52538	-3	0	1		HomePage
52545	52538	-3	0	1		Name
52546	52538	-3	0	1		Prerequiste
52547	52537	52538	0	-1		Course
52552	52549	-3	0	1		Description
52553	52549	-3	0	1		PrereqFor
52554	52550	52549	0	-1		Course
52555	52549	-3	0	1		Prereqs
52556	52548	52550	0	1		nwu
52557	52549	-3	0	1		Instructor
52558	52549	-3	0	1		Title
52563	52560	-3	0	1		Room
52564	52559	52561	0	1		nyu
52565	52560	-3	0	1		Name
52566	52560	-3	0	1		Time
52567	52560	-3	0	1		Instructor
52568	52560	-3	0	1		Days
52569	52560	-3	0	1		No
52570	52560	-3	0	1		CallNo
52571	52561	52560	0	-1		Course
52576	52573	-3	0	1		Room
52577	52574	52573	0	-1		Course
52578	52572	52574	0	1		stanford
52579	52573	-3	0	1		Instructor
52580	52573	-3	0	1		SectionCredit
52581	52573	-3	0	1		CourseName
52582	52573	-3	0	1		DayTime
52583	52573	-3	0	1		CourseCode
52594	52587	52586	0	1		instructor
52595	52585	52587	0	-1		course
52596	52587	-3	0	1		prereq
52597	52587	-3	0	1		coursewebsite
52598	52587	-3	0	1		text
52599	52587	-3	0	1		location
52600	52584	52585	0	1		toronto
52601	52587	-3	0	1		title
52606	52604	-3	0	1		Title
52607	52604	-3	0	1		Winter2004
52608	52602	52603	0	1		ucsd
52609	52604	-3	0	1		Number
52610	52603	52604	0	-1		Course
52611	52604	-3	0	1		Spring2004
52612	52604	-3	0	1		Fall2003
52626	52617	-3	0	1		sectioncode
52627	52614	52618	0	1		building
52628	52617	52614	0	1		time
52629	52615	52616	0	-1		course
52630	52616	-3	0	1		prereq
52631	52613	52615	0	1		ufl
52632	52616	-3	0	1		description
52633	52616	52617	0	1		section
52634	52617	-3	0	1		instructor
52635	52616	-3	0	1		credit
52640	52637	-3	0	1		InternetCourse
52641	52637	-3	0	1		CourseTitle
52642	52637	-3	0	1		TimeTable
52643	52636	52638	0	1		uiuc
52644	52638	52637	0	-1		BannerNumber
52645	52637	-3	0	1		UIDirectNumber
52650	52647	-3	0	1		TitleCredits
52651	52647	-3	0	1		SchedNum
52652	52646	52648	0	1		umb
52653	52648	52647	0	-1		Course
52654	52647	-3	0	1		Times
52655	52647	-3	0	1		Days
52656	52647	-3	0	1		Code
52657	52647	-3	0	1		Room
52658	52647	-3	0	1		Instructor
52659	52647	-3	0	1		Sec
52665	52662	-3	0	1		CourseName
52666	52662	-3	0	1		GradeMethod
52667	52661	52662	0	-1		Course
52668	52660	52661	0	1		umd
52669	52663	-3	0	1		Title
52670	52663	-3	0	1		Time
52671	52662	52663	0	-1		Section
52672	52662	-3	0	1		Credits
52673	52662	-3	0	1		Code
52680	52676	-3	0	1		description
52681	52676	-3	0	1		name
52682	52676	-3	0	1		field
52683	52675	52676	0	-1		course
52684	52674	52675	0	1		umich
52685	52676	-3	0	1		prerequisite
52686	52676	-3	0	1		credits
52693	52689	-3	0	1		Room
52694	52689	-3	0	1		Time
52695	52687	52688	0	1		unsw
52696	52689	-3	0	1		Title
52697	52688	52689	0	-1		Course
52698	52689	-3	0	1		Day
52699	52689	-3	0	1		Instructor
52704	52701	52702	0	-1		Course
52705	52702	-3	0	1		Coreqs
52706	52702	-3	0	1		Title
52707	52702	-3	0	1		Description
52708	52702	-3	0	1		Credits
52709	52700	52701	0	1		uprb
52710	52702	-3	0	1		Prereqs
52717	52714	-3	0	1		Title
52718	52713	52712	0	-1		CourseType
52719	52711	52713	0	1		virginia
52720	52714	-3	0	1		Code
52721	52712	52714	0	-1		Course
52726	52724	-3	0	1		Instructor
52727	52722	52723	0	1		washu
52728	52724	-3	0	1		Title
52729	52724	-3	0	1		Number
52730	52724	-3	0	1		Time
52731	52723	52724	0	-1		Course
52737	52734	-3	0	1		CrNoCr
52738	52733	52734	0	-1		Course
52739	52732	52733	0	1		wisc
52740	52734	-3	0	1		Prerequisites
52741	52734	-3	0	1		SS
52742	52734	-3	0	1		Cr
52743	52734	-3	0	1		CourseName
52744	52734	-3	0	1		geBLC
52749	52746	52747	0	-1		Course
52750	52747	-3	0	1		Title
52751	52745	52746	0	1		wpi
52752	52747	-3	0	1		Description
52758	52755	52754	0	-1		Course
52759	52754	-3	0	1		Instructor
52760	52753	52755	0	1		yale
52761	52754	-3	0	1		CourseName
52762	52754	-3	0	1		DayTime
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
81	Species	Codes for common species
83	Gender	Codes for gender
84	Status	Codes describing a patient's willingness to participate in a study
85	Race	Codes for common human races
95	Memory	Codes for common memory tests
97	Handedness	Codes for handedness
106	Modality	Codes describing the technique used to acquire an image
107	Language	Codes for common languages
-5	Boolean	Domain covering all boolean values
-6	Any	Domain covering all values
-4	Timestamp	Domain consisting of date and/or time values
52368	DateTime	The DateTime domain
52379	DateTime	The DateTime domain
52388	DateTime	The DateTime domain
52399	DateTime	The DateTime domain
52417	DateTime	The DateTime domain
52426	DateTime	The DateTime domain
52436	DateTime	The DateTime domain
52450	DateTime	The DateTime domain
52457	DateTime	The DateTime domain
52471	DateTime	The DateTime domain
52481	DateTime	The DateTime domain
52492	DateTime	The DateTime domain
52513	DateTime	The DateTime domain
52527	DateTime	The DateTime domain
52539	DateTime	The DateTime domain
52551	DateTime	The DateTime domain
52562	DateTime	The DateTime domain
52575	DateTime	The DateTime domain
52588	DateTime	The DateTime domain
52605	DateTime	The DateTime domain
52619	DateTime	The DateTime domain
52639	DateTime	The DateTime domain
52649	DateTime	The DateTime domain
52664	DateTime	The DateTime domain
52677	DateTime	The DateTime domain
52690	DateTime	The DateTime domain
52703	DateTime	The DateTime domain
52715	DateTime	The DateTime domain
52725	DateTime	The DateTime domain
52735	DateTime	The DateTime domain
52748	DateTime	The DateTime domain
52756	DateTime	The DateTime domain
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
52366		
52367		University of Arizona
52377		Arizona State University
52378		
52386		University of Berkeley
52387		
52397		Bilkent University
52398		
52415		Bosphorus University
52416		
52424		
52425		Brown University
52434		
52435		Carnegie Mellon University
52449		Cornell University
52454		
52455		Eidgenössische Technische Hochschule Zürich
52456		
52469		Florida International University
52470		
52479		
52480		Furman University
52490		Georgia Tech
52491		
52511		Harvard University
52512		
52524		
52525		
52526		Istanbul Technical University
52537		Middle East Technical University
52538		
52549		
52550		Northwestern University
52560		
52561		New York University
52573		
52574		Stanford University
52585		University of Toronto
52586		
52587		
52603		University of California, San Diego
52604		
52614		
52615		University of Florida
52616		
52617		
52618		
52637		
52638		University of Illinois at Urbana-Champaign
52647		
52648		University of Massachusetts Boston
52661		University of Maryland
52662		
52663		
52675		University of Michigan
52676		
52688		University of New South Wales-Sydney,Australia
52689		
52701		Universidad de Puerto Rico - Bayamon
52702		
52712		
52713		University of Virginia
52714		
52723		Washington University
52724		
52733		University of Wisconsin-Madison
52734		
52746		Worcester Polytechnic Institute 
52747		
52754		
52755		Yale University
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
52365	Add	52366
52365	Add	52367
52365	Add	52368
52365	Add	52369
52365	Add	52370
52365	Add	52371
52365	Add	52372
52365	Add	52373
52365	Add	52374
52365	Add	52375
52376	Add	52377
52376	Add	52378
52376	Add	52379
52376	Add	52380
52376	Add	52381
52376	Add	52382
52376	Add	52383
52376	Add	52384
52385	Add	52386
52385	Add	52387
52385	Add	52388
52385	Add	52389
52385	Add	52390
52385	Add	52391
52385	Add	52392
52385	Add	52393
52385	Add	52394
52385	Add	52395
52396	Add	52397
52396	Add	52398
52396	Add	52399
52396	Add	52400
52396	Add	52401
52396	Add	52402
52396	Add	52403
52396	Add	52404
52396	Add	52405
52396	Add	52406
52396	Add	52407
52396	Add	52408
52396	Add	52409
52396	Add	52410
52396	Add	52411
52396	Add	52412
52396	Add	52413
52414	Add	52415
52414	Add	52416
52414	Add	52417
52414	Add	52418
52414	Add	52419
52414	Add	52420
52414	Add	52421
52414	Add	52422
52423	Add	52424
52423	Add	52425
52423	Add	52426
52423	Add	52427
52423	Add	52428
52423	Add	52429
52423	Add	52430
52423	Add	52431
52423	Add	52432
52433	Add	52434
52433	Add	52435
52433	Add	52436
52433	Add	52437
52433	Add	52438
52433	Add	52439
52433	Add	52440
52433	Add	52441
52433	Add	52442
52433	Add	52443
52433	Add	52444
52433	Add	52445
52433	Add	52446
52433	Add	52447
52448	Add	52449
52448	Add	52450
52448	Add	52451
52448	Add	52452
52453	Add	52454
52453	Add	52455
52453	Add	52456
52453	Add	52457
52453	Add	52458
52453	Add	52459
52453	Add	52460
52453	Add	52461
52453	Add	52462
52453	Add	52463
52453	Add	52464
52453	Add	52465
52453	Add	52466
52453	Add	52467
52468	Add	52469
52468	Add	52470
52468	Add	52471
52468	Add	52472
52468	Add	52473
52468	Add	52474
52468	Add	52475
52468	Add	52476
52468	Add	52477
52478	Add	52479
52478	Add	52480
52478	Add	52481
52478	Add	52482
52478	Add	52483
52478	Add	52484
52478	Add	52485
52478	Add	52486
52478	Add	52487
52478	Add	52488
52489	Add	52490
52489	Add	52491
52489	Add	52492
52489	Add	52493
52489	Add	52494
52489	Add	52495
52489	Add	52496
52489	Add	52497
52489	Add	52498
52489	Add	52499
52489	Add	52500
52489	Add	52501
52489	Add	52502
52489	Add	52503
52489	Add	52504
52489	Add	52505
52489	Add	52506
52489	Add	52507
52489	Add	52508
52489	Add	52509
52510	Add	52511
52510	Add	52512
52510	Add	52513
52510	Add	52514
52510	Add	52515
52510	Add	52516
52510	Add	52517
52510	Add	52518
52510	Add	52519
52510	Add	52520
52510	Add	52521
52510	Add	52522
52523	Add	52524
52523	Add	52525
52523	Add	52526
52523	Add	52527
52523	Add	52528
52523	Add	52529
52523	Add	52530
52523	Add	52531
52523	Add	52532
52523	Add	52533
52523	Add	52534
52523	Add	52535
52536	Add	52537
52536	Add	52538
52536	Add	52539
52536	Add	52540
52536	Add	52541
52536	Add	52542
52536	Add	52543
52536	Add	52544
52536	Add	52545
52536	Add	52546
52536	Add	52547
52548	Add	52549
52548	Add	52550
52548	Add	52551
52548	Add	52552
52548	Add	52553
52548	Add	52554
52548	Add	52555
52548	Add	52556
52548	Add	52557
52548	Add	52558
52559	Add	52560
52559	Add	52561
52559	Add	52562
52559	Add	52563
52559	Add	52564
52559	Add	52565
52559	Add	52566
52559	Add	52567
52559	Add	52568
52559	Add	52569
52559	Add	52570
52559	Add	52571
52572	Add	52573
52572	Add	52574
52572	Add	52575
52572	Add	52576
52572	Add	52577
52572	Add	52578
52572	Add	52579
52572	Add	52580
52572	Add	52581
52572	Add	52582
52572	Add	52583
52584	Add	52585
52584	Add	52586
52584	Add	52587
52584	Add	52588
52584	Add	52589
52584	Add	52590
52584	Add	52591
52584	Add	52592
52584	Add	52593
52584	Add	52594
52584	Add	52595
52584	Add	52596
52584	Add	52597
52584	Add	52598
52584	Add	52599
52584	Add	52600
52584	Add	52601
52602	Add	52603
52602	Add	52604
52602	Add	52605
52602	Add	52606
52602	Add	52607
52602	Add	52608
52602	Add	52609
52602	Add	52610
52602	Add	52611
52602	Add	52612
52613	Add	52614
52613	Add	52615
52613	Add	52616
52613	Add	52617
52613	Add	52618
52613	Add	52619
52613	Add	52620
52613	Add	52621
52613	Add	52622
52613	Add	52623
52613	Add	52624
52613	Add	52625
52613	Add	52626
52613	Add	52627
52613	Add	52628
52613	Add	52629
52613	Add	52630
52613	Add	52631
52613	Add	52632
52613	Add	52633
52613	Add	52634
52613	Add	52635
52636	Add	52637
52636	Add	52638
52636	Add	52639
52636	Add	52640
52636	Add	52641
52636	Add	52642
52636	Add	52643
52636	Add	52644
52636	Add	52645
52646	Add	52647
52646	Add	52648
52646	Add	52649
52646	Add	52650
52646	Add	52651
52646	Add	52652
52646	Add	52653
52646	Add	52654
52646	Add	52655
52646	Add	52656
52646	Add	52657
52646	Add	52658
52646	Add	52659
52660	Add	52661
52660	Add	52662
52660	Add	52663
52660	Add	52664
52660	Add	52665
52660	Add	52666
52660	Add	52667
52660	Add	52668
52660	Add	52669
52660	Add	52670
52660	Add	52671
52660	Add	52672
52660	Add	52673
52674	Add	52675
52674	Add	52676
52674	Add	52677
52674	Add	52678
52674	Add	52679
52674	Add	52680
52674	Add	52681
52674	Add	52682
52674	Add	52683
52674	Add	52684
52674	Add	52685
52674	Add	52686
52687	Add	52688
52687	Add	52689
52687	Add	52690
52687	Add	52691
52687	Add	52692
52687	Add	52693
52687	Add	52694
52687	Add	52695
52687	Add	52696
52687	Add	52697
52687	Add	52698
52687	Add	52699
52700	Add	52701
52700	Add	52702
52700	Add	52703
52700	Add	52704
52700	Add	52705
52700	Add	52706
52700	Add	52707
52700	Add	52708
52700	Add	52709
52700	Add	52710
52711	Add	52712
52711	Add	52713
52711	Add	52714
52711	Add	52715
52711	Add	52716
52711	Add	52717
52711	Add	52718
52711	Add	52719
52711	Add	52720
52711	Add	52721
52722	Add	52723
52722	Add	52724
52722	Add	52725
52722	Add	52726
52722	Add	52727
52722	Add	52728
52722	Add	52729
52722	Add	52730
52722	Add	52731
52732	Add	52733
52732	Add	52734
52732	Add	52735
52732	Add	52736
52732	Add	52737
52732	Add	52738
52732	Add	52739
52732	Add	52740
52732	Add	52741
52732	Add	52742
52732	Add	52743
52732	Add	52744
52745	Add	52746
52745	Add	52747
52745	Add	52748
52745	Add	52749
52745	Add	52750
52745	Add	52751
52745	Add	52752
52753	Add	52754
52753	Add	52755
52753	Add	52756
52753	Add	52757
52753	Add	52758
52753	Add	52759
52753	Add	52760
52753	Add	52761
52753	Add	52762
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
52364	asdf	kuangc			asdf	t
52365	arizona.xsd	kuangc	file:////Users/kuangc/workspace/openii/thalia/Testbed/arizona.xsd			t
52376	asu.xsd	kuangc	file:////Users/kuangc/workspace/openii/thalia/Testbed/asu.xsd			t
52385	berkeley.xsd	kuangc	file:////Users/kuangc/workspace/openii/thalia/Testbed/berkeley.xsd			t
52396	bilkent.xsd	kuangc	file:////Users/kuangc/workspace/openii/thalia/Testbed/bilkent.xsd			t
52414	boun.xsd	kuangc	file:////Users/kuangc/workspace/openii/thalia/Testbed/boun.xsd			t
52423	brown.xsd	kuangc	file:////Users/kuangc/workspace/openii/thalia/Testbed/brown.xsd			t
52433	cmu.xsd	kuangc	file:////Users/kuangc/workspace/openii/thalia/Testbed/cmu.xsd			t
52448	cornell.xsd	kuangc	file:////Users/kuangc/workspace/openii/thalia/Testbed/cornell.xsd			t
52453	ethz.xsd	kuangc	file:////Users/kuangc/workspace/openii/thalia/Testbed/ethz.xsd			t
52468	fiu.xsd	kuangc	file:////Users/kuangc/workspace/openii/thalia/Testbed/fiu.xsd			t
52478	furman.xsd	kuangc	file:////Users/kuangc/workspace/openii/thalia/Testbed/furman.xsd			t
52489	gatech.xsd	kuangc	file:////Users/kuangc/workspace/openii/thalia/Testbed/gatech.xsd			t
52510	harvard.xsd	kuangc	file:////Users/kuangc/workspace/openii/thalia/Testbed/harvard.xsd			t
52523	itu.xsd	kuangc	file:////Users/kuangc/workspace/openii/thalia/Testbed/itu.xsd			t
52536	metu.xsd	kuangc	file:////Users/kuangc/workspace/openii/thalia/Testbed/metu.xsd			t
52548	nwu.xsd	kuangc	file:////Users/kuangc/workspace/openii/thalia/Testbed/nwu.xsd			t
52559	nyu.xsd	kuangc	file:////Users/kuangc/workspace/openii/thalia/Testbed/nyu.xsd			t
52572	stanford.xsd	kuangc	file:////Users/kuangc/workspace/openii/thalia/Testbed/stanford.xsd			t
52584	toronto.xsd	kuangc	file:////Users/kuangc/workspace/openii/thalia/Testbed/toronto.xsd			t
52602	ucsd.xsd	kuangc	file:////Users/kuangc/workspace/openii/thalia/Testbed/ucsd.xsd			t
52613	ufl.xsd	kuangc	file:////Users/kuangc/workspace/openii/thalia/Testbed/ufl.xsd			t
52636	uiuc.xsd	kuangc	file:////Users/kuangc/workspace/openii/thalia/Testbed/uiuc.xsd			t
52646	umb.xsd	kuangc	file:////Users/kuangc/workspace/openii/thalia/Testbed/umb.xsd			t
52660	umd.xsd	kuangc	file:////Users/kuangc/workspace/openii/thalia/Testbed/umd.xsd			t
52674	umich.xsd	kuangc	file:////Users/kuangc/workspace/openii/thalia/Testbed/umich.xsd			t
52687	unsw.xsd	kuangc	file:////Users/kuangc/workspace/openii/thalia/Testbed/unsw.xsd			t
52700	uprb.xsd	kuangc	file:////Users/kuangc/workspace/openii/thalia/Testbed/uprb.xsd			t
52711	virginia.xsd	kuangc	file:////Users/kuangc/workspace/openii/thalia/Testbed/virginia.xsd			t
52722	washu.xsd	kuangc	file:////Users/kuangc/workspace/openii/thalia/Testbed/washu.xsd			t
52732	wisc.xsd	kuangc	file:////Users/kuangc/workspace/openii/thalia/Testbed/wisc.xsd			t
52745	wpi.xsd	kuangc	file:////Users/kuangc/workspace/openii/thalia/Testbed/wpi.xsd			t
52753	yale.xsd	kuangc	file:////Users/kuangc/workspace/openii/thalia/Testbed/yale.xsd			t
\.


--
-- Data for Name: schema_element; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY schema_element (id, type) FROM stdin;
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
435	Relationship
410	Containment
412	Containment
400	Schema
-1	Domain
-2	Domain
-3	Domain
-4	Domain
-5	Domain
-6	Domain
144	DomainValue
145	DomainValue
146	DomainValue
164	DomainValue
165	DomainValue
166	DomainValue
167	DomainValue
174	DomainValue
175	DomainValue
176	DomainValue
184	DomainValue
185	DomainValue
186	DomainValue
187	DomainValue
188	DomainValue
284	DomainValue
285	DomainValue
286	DomainValue
304	DomainValue
305	DomainValue
306	DomainValue
307	DomainValue
308	DomainValue
309	DomainValue
310	DomainValue
311	DomainValue
394	DomainValue
395	DomainValue
396	DomainValue
404	DomainValue
405	DomainValue
406	DomainValue
407	DomainValue
408	DomainValue
52366	Entity
52367	Entity
52368	Domain
52369	Attribute
52370	Containment
52371	Containment
52372	Containment
52373	Containment
52374	Containment
52375	Containment
52377	Entity
52378	Entity
52379	Domain
52380	Attribute
52381	Containment
52382	Containment
52383	Containment
52384	Containment
52386	Entity
52387	Entity
52388	Domain
52389	Containment
52390	Containment
52391	Containment
52392	Containment
52393	Containment
52394	Containment
52395	Containment
52397	Entity
52398	Entity
52399	Domain
52400	Attribute
52401	Containment
52402	Containment
52403	Containment
52404	Containment
52405	Containment
52406	Containment
52407	Containment
52408	Containment
52409	Containment
52410	Containment
52411	Containment
52412	Containment
52413	Containment
52415	Entity
52416	Entity
52417	Domain
52418	Attribute
52419	Containment
52420	Containment
52421	Containment
52422	Containment
52424	Entity
52425	Entity
52426	Domain
52427	Containment
52428	Containment
52429	Containment
52430	Containment
52431	Containment
52432	Containment
52434	Entity
52435	Entity
52436	Domain
52437	Containment
52438	Containment
52439	Containment
52440	Containment
52441	Containment
52442	Containment
52443	Containment
52444	Containment
52445	Containment
52446	Containment
52447	Containment
52449	Entity
52450	Domain
52451	Containment
52452	Containment
52454	Entity
52455	Entity
52456	Entity
52457	Domain
52458	Attribute
52459	Attribute
52460	Containment
52461	Containment
52462	Containment
52463	Containment
52464	Containment
52465	Containment
52466	Containment
52467	Containment
52469	Entity
52470	Entity
52471	Domain
52472	Containment
52473	Containment
52474	Containment
52475	Containment
52476	Containment
52477	Containment
52479	Entity
52480	Entity
52481	Domain
52482	Containment
52483	Containment
52484	Containment
52485	Containment
52486	Containment
52487	Containment
52488	Containment
52490	Entity
52491	Entity
52492	Domain
52493	Containment
52494	Containment
52495	Containment
52496	Containment
52497	Containment
52498	Containment
52499	Containment
52500	Containment
52501	Containment
52502	Containment
52503	Containment
52504	Containment
52505	Containment
52506	Containment
52507	Containment
52508	Containment
52509	Containment
52511	Entity
52512	Entity
52513	Domain
52514	Containment
52515	Containment
52516	Containment
52517	Containment
52518	Containment
52519	Containment
52520	Containment
52521	Containment
52522	Containment
52524	Entity
52525	Entity
52526	Entity
52527	Domain
52528	Containment
52529	Containment
52530	Containment
52531	Containment
52532	Containment
52533	Containment
52534	Containment
52535	Containment
52537	Entity
52538	Entity
52539	Domain
52540	Attribute
52541	Containment
52542	Containment
52543	Containment
52544	Containment
52545	Containment
52546	Containment
52547	Containment
52549	Entity
52550	Entity
52551	Domain
52552	Containment
52553	Containment
52554	Containment
52555	Containment
52556	Containment
52557	Containment
52558	Containment
52560	Entity
52561	Entity
52562	Domain
52563	Containment
52564	Containment
52565	Containment
52566	Containment
52567	Containment
52568	Containment
52569	Containment
52570	Containment
52571	Containment
52573	Entity
52574	Entity
52575	Domain
52576	Containment
52577	Containment
52578	Containment
52579	Containment
52580	Containment
52581	Containment
52582	Containment
52583	Containment
52585	Entity
52586	Entity
52587	Entity
52588	Domain
52589	Attribute
52590	Attribute
52591	Attribute
52592	Attribute
52593	Attribute
52594	Containment
52595	Containment
52596	Containment
52597	Containment
52598	Containment
52599	Containment
52600	Containment
52601	Containment
52603	Entity
52604	Entity
52605	Domain
52606	Containment
52607	Containment
52608	Containment
52609	Containment
52610	Containment
52611	Containment
52612	Containment
52614	Entity
52615	Entity
52616	Entity
52617	Entity
52618	Entity
52619	Domain
52620	Attribute
52621	Attribute
52622	Attribute
52623	Attribute
52624	Attribute
52625	Attribute
52626	Containment
52627	Containment
52628	Containment
52629	Containment
52630	Containment
52631	Containment
52632	Containment
52633	Containment
52634	Containment
52635	Containment
52637	Entity
52638	Entity
52639	Domain
52640	Containment
52641	Containment
52642	Containment
52643	Containment
52644	Containment
52645	Containment
52647	Entity
52648	Entity
52649	Domain
52650	Containment
52651	Containment
52652	Containment
52653	Containment
52654	Containment
52655	Containment
52656	Containment
52657	Containment
52658	Containment
52659	Containment
52661	Entity
52662	Entity
52663	Entity
52664	Domain
52665	Containment
52666	Containment
52667	Containment
52668	Containment
52669	Containment
52670	Containment
52671	Containment
52672	Containment
52673	Containment
52675	Entity
52676	Entity
52677	Domain
52678	Attribute
52679	Attribute
52680	Containment
52681	Containment
52682	Containment
52683	Containment
52684	Containment
52685	Containment
52686	Containment
52688	Entity
52689	Entity
52690	Domain
52691	Attribute
52692	Attribute
52693	Containment
52694	Containment
52695	Containment
52696	Containment
52697	Containment
52698	Containment
52699	Containment
52701	Entity
52702	Entity
52703	Domain
52704	Containment
52705	Containment
52706	Containment
52707	Containment
52708	Containment
52709	Containment
52710	Containment
52712	Entity
52713	Entity
52714	Entity
52715	Domain
52716	Attribute
52717	Containment
52718	Containment
52719	Containment
52720	Containment
52721	Containment
52723	Entity
52724	Entity
52725	Domain
52726	Containment
52727	Containment
52728	Containment
52729	Containment
52730	Containment
52731	Containment
52733	Entity
52734	Entity
52735	Domain
52736	Attribute
52737	Containment
52738	Containment
52739	Containment
52740	Containment
52741	Containment
52742	Containment
52743	Containment
52744	Containment
52746	Entity
52747	Entity
52748	Domain
52749	Containment
52750	Containment
52751	Containment
52752	Containment
52754	Entity
52755	Entity
52756	Domain
52757	Attribute
52758	Containment
52759	Containment
52760	Containment
52761	Containment
52762	Containment
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
-- Name: attr_domain_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY attribute
    ADD CONSTRAINT attr_domain_id_fkey FOREIGN KEY (domain_id) REFERENCES domain(id);


--
-- Name: attr_entity_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY attribute
    ADD CONSTRAINT attr_entity_id_fkey FOREIGN KEY (entity_id) REFERENCES entity(id);


--
-- Name: attribute_domain_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY attribute
    ADD CONSTRAINT attribute_domain_id_fkey FOREIGN KEY (domain_id) REFERENCES domain(id);


--
-- Name: data_source_schema_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY data_source
    ADD CONSTRAINT data_source_schema_id_fkey FOREIGN KEY (schema_id) REFERENCES schema(id);


--
-- Name: ext_schema_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY extensions
    ADD CONSTRAINT ext_schema_id_fkey FOREIGN KEY (schema_id) REFERENCES schema(id);


--
-- Name: groups_parent_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY groups
    ADD CONSTRAINT groups_parent_id_fkey FOREIGN KEY (parent_id) REFERENCES groups(id);


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
-- Name: rel_left_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY relationship
    ADD CONSTRAINT rel_left_id_fkey FOREIGN KEY (left_id) REFERENCES entity(id);


--
-- Name: rel_right_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY relationship
    ADD CONSTRAINT rel_right_id_fkey FOREIGN KEY (right_id) REFERENCES entity(id);


--
-- Name: schema_group_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY schema_group
    ADD CONSTRAINT schema_group_id_fkey FOREIGN KEY (group_id) REFERENCES groups(id);


--
-- Name: schema_group_schema_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY schema_group
    ADD CONSTRAINT schema_group_schema_id_fkey FOREIGN KEY (schema_id) REFERENCES schema(id);


--
-- Name: value_domain_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY domainvalue
    ADD CONSTRAINT value_domain_id_fkey FOREIGN KEY (domain_id) REFERENCES domain(id);


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

