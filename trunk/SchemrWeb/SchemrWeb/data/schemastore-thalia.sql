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

SELECT pg_catalog.setval('universalseq', 52761, true);


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
52368	Code		52365	-3	1	1
52379	Title		52377	-3	1	1
52399	Code		52397	-3	1	1
52417	Kodu		52415	-3	1	1
52457	Nummer		52453	-3	1	1
52458	Typ		52455	-3	1	1
52539	Code		52537	-3	1	1
52588	Name		52585	-3	1	1
52589	offeredTerm		52586	-3	1	1
52590	level		52586	-3	1	1
52591	No		52586	-3	1	1
52592	Email		52585	-3	1	1
52619	title		52615	-3	1	1
52620	day		52613	-3	1	1
52621	period		52613	-3	1	1
52622	code		52615	-3	1	1
52623	bldg		52617	-3	1	1
52624	room		52617	-3	1	1
52677	catalognumber		52675	-3	1	1
52678	subject		52675	-3	1	1
52690	Section		52688	-3	1	1
52691	CourseID		52688	-3	1	1
52715	Type		52711	-3	1	1
52735	No		52733	-3	1	1
52756	No		52753	-3	1	1
\.


--
-- Data for Name: containment; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY containment (id, parent_id, child_id, min, max, description, name) FROM stdin;
410	20	22	\N	\N	\N	\N
412	22	24	\N	\N	\N	\N
52369	52365	-3	0	1		Instructor
52370	52365	-3	0	1		Day
52371	52366	52365	0	-1		Course
52372	52364	52366	0	1		arizona
52373	52365	-3	0	1		Time
52374	52365	-3	0	1		Place
52380	52376	52377	0	-1		Course
52381	52375	52376	0	1		asu
52382	52377	-3	0	1		Description
52383	52377	-3	0	1		MoreInfo.URL
52388	52384	52385	0	1		berkeley
52389	52386	-3	0	1		Code
52390	52386	-3	0	1		Name
52391	52385	52386	0	-1		Course
52392	52386	-3	0	1		Time
52393	52386	-3	0	1		SectionCredit
52394	52386	-3	0	1		Location
52400	52397	-3	0	1		Quota2
52401	52397	-3	0	1		Schedule
52402	52397	-3	0	1		Group
52403	52397	-3	0	1		Limit2
52404	52397	-3	0	1		Instructor1
52405	52397	-3	0	1		Limit1
52406	52397	-3	0	1		CourseName
52407	52397	-3	0	1		Quota1
52408	52397	-3	0	1		Instructor2
52409	52395	52396	0	1		bilkent
52410	52397	-3	0	1		Syllabus
52411	52397	-3	0	1		GPA
52412	52396	52397	0	-1		Section
52418	52413	52414	0	1		boun
52419	52414	52415	0	-1		Ders
52420	52415	-3	0	1		ismi
52421	52415	-3	0	1		Kredi
52426	52423	-3	0	1		Room
52427	52423	-3	0	1		Title
52428	52423	-3	0	1		Instructor
52429	52422	52424	0	1		brown
52430	52424	52423	0	-1		Course
52431	52423	-3	0	1		Code
52436	52433	-3	0	1		CourseTitle
52437	52433	-3	0	1		Lecturer
52438	52433	-3	0	1		Room
52439	52433	-3	0	1		Day
52440	52433	-3	0	1		CourseX-Listed
52441	52433	-3	0	1		Code
52442	52433	-3	0	1		Time
52443	52432	52434	0	1		cmu
52444	52433	-3	0	1		Units
52445	52433	-3	0	1		Sec
52446	52434	52433	0	-1		Course
52450	52447	52448	0	1		cornell
52451	52448	-3	0	1		Course
52459	52454	52455	0	-1		UnterrichtTyp
52460	52453	-3	0	1		Titel
52461	52455	52453	0	-1		Unterricht
52462	52453	-3	0	1		Sprache
52463	52453	-3	0	1		Homepage
52464	52453	-3	0	1		Dozent
52465	52452	52454	0	1		ethz
52466	52453	-3	0	1		Umfang
52471	52469	-3	0	1		Coreq
52472	52467	52468	0	1		fiu
52473	52469	-3	0	1		Prereq
52474	52469	-3	0	1		Title
52475	52468	52469	0	-1		Course
52476	52469	-3	0	1		Description
52481	52478	-3	0	1		Title
52482	52477	52479	0	1		furman
52483	52478	-3	0	1		Code
52484	52478	-3	0	1		Times
52485	52479	52478	0	-1		Course
52486	52478	-3	0	1		Room
52487	52478	-3	0	1		Instructor
52492	52490	-3	0	1		Room
52493	52490	-3	0	1		Instructor
52494	52490	-3	0	1		CRN
52495	52490	-3	0	1		Section
52496	52490	-3	0	1		Mode
52497	52490	-3	0	1		In
52498	52490	-3	0	1		Max
52499	52490	-3	0	1		Description
52500	52490	-3	0	1		Hours
52501	52490	-3	0	1		Time
52502	52490	-3	0	1		Department
52503	52488	52489	0	1		gatech
52504	52489	52490	0	-1		Course
52505	52490	-3	0	1		Days
52506	52490	-3	0	1		Title
52507	52490	-3	0	1		Code
52508	52490	-3	0	1		Building
52513	52511	-3	0	1		Description
52514	52511	-3	0	1		Title
52515	52511	-3	0	1		Instructor
52516	52511	-3	0	1		Notes
52517	52511	-3	0	1		Prereqs
52518	52511	-3	0	1		Details
52519	52510	52511	0	-1		Course
52520	52509	52510	0	1		harvard
52521	52511	-3	0	1		Number
52527	52522	52525	0	1		itu
52528	52523	-3	0	1		Adi
52529	52523	-3	0	1		Kredisi
52530	52523	-3	0	1		Turu
52531	52525	52524	0	-1		Yariyil
52532	52524	-3	0	1		ismi
52533	52523	-3	0	1		Kodu
52534	52524	52523	0	-1		Ders
52540	52537	-3	0	1		DetailedInformation
52541	52535	52536	0	1		metu
52542	52537	-3	0	1		CreditInformation
52543	52537	-3	0	1		HomePage
52544	52537	-3	0	1		Name
52545	52537	-3	0	1		Prerequiste
52546	52536	52537	0	-1		Course
52551	52548	-3	0	1		Description
52552	52548	-3	0	1		PrereqFor
52553	52549	52548	0	-1		Course
52554	52548	-3	0	1		Prereqs
52555	52547	52549	0	1		nwu
52556	52548	-3	0	1		Instructor
52557	52548	-3	0	1		Title
52562	52559	-3	0	1		Room
52563	52558	52560	0	1		nyu
52564	52559	-3	0	1		Name
52565	52559	-3	0	1		Time
52566	52559	-3	0	1		Instructor
52567	52559	-3	0	1		Days
52568	52559	-3	0	1		No
52569	52559	-3	0	1		CallNo
52570	52560	52559	0	-1		Course
52575	52572	-3	0	1		Room
52576	52573	52572	0	-1		Course
52577	52571	52573	0	1		stanford
52578	52572	-3	0	1		Instructor
52579	52572	-3	0	1		SectionCredit
52580	52572	-3	0	1		CourseName
52581	52572	-3	0	1		DayTime
52582	52572	-3	0	1		CourseCode
52593	52586	52585	0	1		instructor
52594	52584	52586	0	-1		course
52595	52586	-3	0	1		prereq
52596	52586	-3	0	1		coursewebsite
52597	52586	-3	0	1		text
52598	52586	-3	0	1		location
52599	52583	52584	0	1		toronto
52600	52586	-3	0	1		title
52605	52603	-3	0	1		Title
52606	52603	-3	0	1		Winter2004
52607	52601	52602	0	1		ucsd
52608	52603	-3	0	1		Number
52609	52602	52603	0	-1		Course
52610	52603	-3	0	1		Spring2004
52611	52603	-3	0	1		Fall2003
52625	52616	-3	0	1		sectioncode
52626	52613	52617	0	1		building
52627	52616	52613	0	1		time
52628	52614	52615	0	-1		course
52629	52615	-3	0	1		prereq
52630	52612	52614	0	1		ufl
52631	52615	-3	0	1		description
52632	52615	52616	0	1		section
52633	52616	-3	0	1		instructor
52634	52615	-3	0	1		credit
52639	52636	-3	0	1		InternetCourse
52640	52636	-3	0	1		CourseTitle
52641	52636	-3	0	1		TimeTable
52642	52635	52637	0	1		uiuc
52643	52637	52636	0	-1		BannerNumber
52644	52636	-3	0	1		UIDirectNumber
52649	52646	-3	0	1		TitleCredits
52650	52646	-3	0	1		SchedNum
52651	52645	52647	0	1		umb
52652	52647	52646	0	-1		Course
52653	52646	-3	0	1		Times
52654	52646	-3	0	1		Days
52655	52646	-3	0	1		Code
52656	52646	-3	0	1		Room
52657	52646	-3	0	1		Instructor
52658	52646	-3	0	1		Sec
52664	52661	-3	0	1		CourseName
52665	52661	-3	0	1		GradeMethod
52666	52660	52661	0	-1		Course
52667	52659	52660	0	1		umd
52668	52662	-3	0	1		Title
52669	52662	-3	0	1		Time
52670	52661	52662	0	-1		Section
52671	52661	-3	0	1		Credits
52672	52661	-3	0	1		Code
52679	52675	-3	0	1		description
52680	52675	-3	0	1		name
52681	52675	-3	0	1		field
52682	52674	52675	0	-1		course
52683	52673	52674	0	1		umich
52684	52675	-3	0	1		prerequisite
52685	52675	-3	0	1		credits
52692	52688	-3	0	1		Room
52693	52688	-3	0	1		Time
52694	52686	52687	0	1		unsw
52695	52688	-3	0	1		Title
52696	52687	52688	0	-1		Course
52697	52688	-3	0	1		Day
52698	52688	-3	0	1		Instructor
52703	52700	52701	0	-1		Course
52704	52701	-3	0	1		Coreqs
52705	52701	-3	0	1		Title
52706	52701	-3	0	1		Description
52707	52701	-3	0	1		Credits
52708	52699	52700	0	1		uprb
52709	52701	-3	0	1		Prereqs
52716	52713	-3	0	1		Title
52717	52712	52711	0	-1		CourseType
52718	52710	52712	0	1		virginia
52719	52713	-3	0	1		Code
52720	52711	52713	0	-1		Course
52725	52723	-3	0	1		Instructor
52726	52721	52722	0	1		washu
52727	52723	-3	0	1		Title
52728	52723	-3	0	1		Number
52729	52723	-3	0	1		Time
52730	52722	52723	0	-1		Course
52736	52733	-3	0	1		CrNoCr
52737	52732	52733	0	-1		Course
52738	52731	52732	0	1		wisc
52739	52733	-3	0	1		Prerequisites
52740	52733	-3	0	1		SS
52741	52733	-3	0	1		Cr
52742	52733	-3	0	1		CourseName
52743	52733	-3	0	1		geBLC
52748	52745	52746	0	-1		Course
52749	52746	-3	0	1		Title
52750	52744	52745	0	1		wpi
52751	52746	-3	0	1		Description
52757	52754	52753	0	-1		Course
52758	52753	-3	0	1		Instructor
52759	52752	52754	0	1		yale
52760	52753	-3	0	1		CourseName
52761	52753	-3	0	1		DayTime
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
52367	DateTime	The DateTime domain
52378	DateTime	The DateTime domain
52387	DateTime	The DateTime domain
52398	DateTime	The DateTime domain
52416	DateTime	The DateTime domain
52425	DateTime	The DateTime domain
52435	DateTime	The DateTime domain
52449	DateTime	The DateTime domain
52456	DateTime	The DateTime domain
52470	DateTime	The DateTime domain
52480	DateTime	The DateTime domain
52491	DateTime	The DateTime domain
52512	DateTime	The DateTime domain
52526	DateTime	The DateTime domain
52538	DateTime	The DateTime domain
52550	DateTime	The DateTime domain
52561	DateTime	The DateTime domain
52574	DateTime	The DateTime domain
52587	DateTime	The DateTime domain
52604	DateTime	The DateTime domain
52618	DateTime	The DateTime domain
52638	DateTime	The DateTime domain
52648	DateTime	The DateTime domain
52663	DateTime	The DateTime domain
52676	DateTime	The DateTime domain
52689	DateTime	The DateTime domain
52702	DateTime	The DateTime domain
52714	DateTime	The DateTime domain
52724	DateTime	The DateTime domain
52734	DateTime	The DateTime domain
52747	DateTime	The DateTime domain
52755	DateTime	The DateTime domain
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
52365		
52366		University of Arizona
52376		Arizona State University
52377		
52385		University of Berkeley
52386		
52396		Bilkent University
52397		
52414		Bosphorus University
52415		
52423		
52424		Brown University
52433		
52434		Carnegie Mellon University
52448		Cornell University
52453		
52454		Eidgenössische Technische Hochschule Zürich
52455		
52468		Florida International University
52469		
52478		
52479		Furman University
52489		Georgia Tech
52490		
52510		Harvard University
52511		
52523		
52524		
52525		Istanbul Technical University
52536		Middle East Technical University
52537		
52548		
52549		Northwestern University
52559		
52560		New York University
52572		
52573		Stanford University
52584		University of Toronto
52585		
52586		
52602		University of California, San Diego
52603		
52613		
52614		University of Florida
52615		
52616		
52617		
52636		
52637		University of Illinois at Urbana-Champaign
52646		
52647		University of Massachusetts Boston
52660		University of Maryland
52661		
52662		
52674		University of Michigan
52675		
52687		University of New South Wales-Sydney,Australia
52688		
52700		Universidad de Puerto Rico - Bayamon
52701		
52711		
52712		University of Virginia
52713		
52722		Washington University
52723		
52732		University of Wisconsin-Madison
52733		
52745		Worcester Polytechnic Institute 
52746		
52753		
52754		Yale University
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
52364	Add	52365
52364	Add	52366
52364	Add	52367
52364	Add	52368
52364	Add	52369
52364	Add	52370
52364	Add	52371
52364	Add	52372
52364	Add	52373
52364	Add	52374
52375	Add	52376
52375	Add	52377
52375	Add	52378
52375	Add	52379
52375	Add	52380
52375	Add	52381
52375	Add	52382
52375	Add	52383
52384	Add	52385
52384	Add	52386
52384	Add	52387
52384	Add	52388
52384	Add	52389
52384	Add	52390
52384	Add	52391
52384	Add	52392
52384	Add	52393
52384	Add	52394
52395	Add	52396
52395	Add	52397
52395	Add	52398
52395	Add	52399
52395	Add	52400
52395	Add	52401
52395	Add	52402
52395	Add	52403
52395	Add	52404
52395	Add	52405
52395	Add	52406
52395	Add	52407
52395	Add	52408
52395	Add	52409
52395	Add	52410
52395	Add	52411
52395	Add	52412
52413	Add	52414
52413	Add	52415
52413	Add	52416
52413	Add	52417
52413	Add	52418
52413	Add	52419
52413	Add	52420
52413	Add	52421
52422	Add	52423
52422	Add	52424
52422	Add	52425
52422	Add	52426
52422	Add	52427
52422	Add	52428
52422	Add	52429
52422	Add	52430
52422	Add	52431
52432	Add	52433
52432	Add	52434
52432	Add	52435
52432	Add	52436
52432	Add	52437
52432	Add	52438
52432	Add	52439
52432	Add	52440
52432	Add	52441
52432	Add	52442
52432	Add	52443
52432	Add	52444
52432	Add	52445
52432	Add	52446
52447	Add	52448
52447	Add	52449
52447	Add	52450
52447	Add	52451
52452	Add	52453
52452	Add	52454
52452	Add	52455
52452	Add	52456
52452	Add	52457
52452	Add	52458
52452	Add	52459
52452	Add	52460
52452	Add	52461
52452	Add	52462
52452	Add	52463
52452	Add	52464
52452	Add	52465
52452	Add	52466
52467	Add	52468
52467	Add	52469
52467	Add	52470
52467	Add	52471
52467	Add	52472
52467	Add	52473
52467	Add	52474
52467	Add	52475
52467	Add	52476
52477	Add	52478
52477	Add	52479
52477	Add	52480
52477	Add	52481
52477	Add	52482
52477	Add	52483
52477	Add	52484
52477	Add	52485
52477	Add	52486
52477	Add	52487
52488	Add	52489
52488	Add	52490
52488	Add	52491
52488	Add	52492
52488	Add	52493
52488	Add	52494
52488	Add	52495
52488	Add	52496
52488	Add	52497
52488	Add	52498
52488	Add	52499
52488	Add	52500
52488	Add	52501
52488	Add	52502
52488	Add	52503
52488	Add	52504
52488	Add	52505
52488	Add	52506
52488	Add	52507
52488	Add	52508
52509	Add	52510
52509	Add	52511
52509	Add	52512
52509	Add	52513
52509	Add	52514
52509	Add	52515
52509	Add	52516
52509	Add	52517
52509	Add	52518
52509	Add	52519
52509	Add	52520
52509	Add	52521
52522	Add	52523
52522	Add	52524
52522	Add	52525
52522	Add	52526
52522	Add	52527
52522	Add	52528
52522	Add	52529
52522	Add	52530
52522	Add	52531
52522	Add	52532
52522	Add	52533
52522	Add	52534
52535	Add	52536
52535	Add	52537
52535	Add	52538
52535	Add	52539
52535	Add	52540
52535	Add	52541
52535	Add	52542
52535	Add	52543
52535	Add	52544
52535	Add	52545
52535	Add	52546
52547	Add	52548
52547	Add	52549
52547	Add	52550
52547	Add	52551
52547	Add	52552
52547	Add	52553
52547	Add	52554
52547	Add	52555
52547	Add	52556
52547	Add	52557
52558	Add	52559
52558	Add	52560
52558	Add	52561
52558	Add	52562
52558	Add	52563
52558	Add	52564
52558	Add	52565
52558	Add	52566
52558	Add	52567
52558	Add	52568
52558	Add	52569
52558	Add	52570
52571	Add	52572
52571	Add	52573
52571	Add	52574
52571	Add	52575
52571	Add	52576
52571	Add	52577
52571	Add	52578
52571	Add	52579
52571	Add	52580
52571	Add	52581
52571	Add	52582
52583	Add	52584
52583	Add	52585
52583	Add	52586
52583	Add	52587
52583	Add	52588
52583	Add	52589
52583	Add	52590
52583	Add	52591
52583	Add	52592
52583	Add	52593
52583	Add	52594
52583	Add	52595
52583	Add	52596
52583	Add	52597
52583	Add	52598
52583	Add	52599
52583	Add	52600
52601	Add	52602
52601	Add	52603
52601	Add	52604
52601	Add	52605
52601	Add	52606
52601	Add	52607
52601	Add	52608
52601	Add	52609
52601	Add	52610
52601	Add	52611
52612	Add	52613
52612	Add	52614
52612	Add	52615
52612	Add	52616
52612	Add	52617
52612	Add	52618
52612	Add	52619
52612	Add	52620
52612	Add	52621
52612	Add	52622
52612	Add	52623
52612	Add	52624
52612	Add	52625
52612	Add	52626
52612	Add	52627
52612	Add	52628
52612	Add	52629
52612	Add	52630
52612	Add	52631
52612	Add	52632
52612	Add	52633
52612	Add	52634
52635	Add	52636
52635	Add	52637
52635	Add	52638
52635	Add	52639
52635	Add	52640
52635	Add	52641
52635	Add	52642
52635	Add	52643
52635	Add	52644
52645	Add	52646
52645	Add	52647
52645	Add	52648
52645	Add	52649
52645	Add	52650
52645	Add	52651
52645	Add	52652
52645	Add	52653
52645	Add	52654
52645	Add	52655
52645	Add	52656
52645	Add	52657
52645	Add	52658
52659	Add	52660
52659	Add	52661
52659	Add	52662
52659	Add	52663
52659	Add	52664
52659	Add	52665
52659	Add	52666
52659	Add	52667
52659	Add	52668
52659	Add	52669
52659	Add	52670
52659	Add	52671
52659	Add	52672
52673	Add	52674
52673	Add	52675
52673	Add	52676
52673	Add	52677
52673	Add	52678
52673	Add	52679
52673	Add	52680
52673	Add	52681
52673	Add	52682
52673	Add	52683
52673	Add	52684
52673	Add	52685
52686	Add	52687
52686	Add	52688
52686	Add	52689
52686	Add	52690
52686	Add	52691
52686	Add	52692
52686	Add	52693
52686	Add	52694
52686	Add	52695
52686	Add	52696
52686	Add	52697
52686	Add	52698
52699	Add	52700
52699	Add	52701
52699	Add	52702
52699	Add	52703
52699	Add	52704
52699	Add	52705
52699	Add	52706
52699	Add	52707
52699	Add	52708
52699	Add	52709
52710	Add	52711
52710	Add	52712
52710	Add	52713
52710	Add	52714
52710	Add	52715
52710	Add	52716
52710	Add	52717
52710	Add	52718
52710	Add	52719
52710	Add	52720
52721	Add	52722
52721	Add	52723
52721	Add	52724
52721	Add	52725
52721	Add	52726
52721	Add	52727
52721	Add	52728
52721	Add	52729
52721	Add	52730
52731	Add	52732
52731	Add	52733
52731	Add	52734
52731	Add	52735
52731	Add	52736
52731	Add	52737
52731	Add	52738
52731	Add	52739
52731	Add	52740
52731	Add	52741
52731	Add	52742
52731	Add	52743
52744	Add	52745
52744	Add	52746
52744	Add	52747
52744	Add	52748
52744	Add	52749
52744	Add	52750
52744	Add	52751
52752	Add	52753
52752	Add	52754
52752	Add	52755
52752	Add	52756
52752	Add	52757
52752	Add	52758
52752	Add	52759
52752	Add	52760
52752	Add	52761
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
52364	arizona	kuangc	file:////Users/kuangc/workspace/eclipse/openii/Schemr/data/thalia/arizona.xsd		Thalia	t
52375	asu	kuangc	file:////Users/kuangc/workspace/eclipse/openii/Schemr/data/thalia/asu.xsd		Thalia	t
52384	berkeley	kuangc	file:////Users/kuangc/workspace/eclipse/openii/Schemr/data/thalia/berkeley.xsd		Thalia	t
52395	bilkent	kuangc	file:////Users/kuangc/workspace/eclipse/openii/Schemr/data/thalia/bilkent.xsd		Thalia	t
52413	boun	kuangc	file:////Users/kuangc/workspace/eclipse/openii/Schemr/data/thalia/boun.xsd		Thalia	t
52422	brown	kuangc	file:////Users/kuangc/workspace/eclipse/openii/Schemr/data/thalia/brown.xsd		Thalia	t
52432	cmu	kuangc	file:////Users/kuangc/workspace/eclipse/openii/Schemr/data/thalia/cmu.xsd		Thalia	t
52447	cornell	kuangc	file:////Users/kuangc/workspace/eclipse/openii/Schemr/data/thalia/cornell.xsd		Thalia	t
52452	ethz	kuangc	file:////Users/kuangc/workspace/eclipse/openii/Schemr/data/thalia/ethz.xsd		Thalia	t
52467	fiu	kuangc	file:////Users/kuangc/workspace/eclipse/openii/Schemr/data/thalia/fiu.xsd		Thalia	t
52477	furman	kuangc	file:////Users/kuangc/workspace/eclipse/openii/Schemr/data/thalia/furman.xsd		Thalia	t
52488	gatech	kuangc	file:////Users/kuangc/workspace/eclipse/openii/Schemr/data/thalia/gatech.xsd		Thalia	t
52509	harvard	kuangc	file:////Users/kuangc/workspace/eclipse/openii/Schemr/data/thalia/harvard.xsd		Thalia	t
52522	itu	kuangc	file:////Users/kuangc/workspace/eclipse/openii/Schemr/data/thalia/itu.xsd		Thalia	t
52535	metu	kuangc	file:////Users/kuangc/workspace/eclipse/openii/Schemr/data/thalia/metu.xsd		Thalia	t
52547	nwu	kuangc	file:////Users/kuangc/workspace/eclipse/openii/Schemr/data/thalia/nwu.xsd		Thalia	t
52558	nyu	kuangc	file:////Users/kuangc/workspace/eclipse/openii/Schemr/data/thalia/nyu.xsd		Thalia	t
52571	stanford	kuangc	file:////Users/kuangc/workspace/eclipse/openii/Schemr/data/thalia/stanford.xsd		Thalia	t
52583	toronto	kuangc	file:////Users/kuangc/workspace/eclipse/openii/Schemr/data/thalia/toronto.xsd		Thalia	t
52601	ucsd	kuangc	file:////Users/kuangc/workspace/eclipse/openii/Schemr/data/thalia/ucsd.xsd		Thalia	t
52612	ufl	kuangc	file:////Users/kuangc/workspace/eclipse/openii/Schemr/data/thalia/ufl.xsd		Thalia	t
52635	uiuc	kuangc	file:////Users/kuangc/workspace/eclipse/openii/Schemr/data/thalia/uiuc.xsd		Thalia	t
52645	umb	kuangc	file:////Users/kuangc/workspace/eclipse/openii/Schemr/data/thalia/umb.xsd		Thalia	t
52659	umd	kuangc	file:////Users/kuangc/workspace/eclipse/openii/Schemr/data/thalia/umd.xsd		Thalia	t
52673	umich	kuangc	file:////Users/kuangc/workspace/eclipse/openii/Schemr/data/thalia/umich.xsd		Thalia	t
52686	unsw	kuangc	file:////Users/kuangc/workspace/eclipse/openii/Schemr/data/thalia/unsw.xsd		Thalia	t
52699	uprb	kuangc	file:////Users/kuangc/workspace/eclipse/openii/Schemr/data/thalia/uprb.xsd		Thalia	t
52710	virginia	kuangc	file:////Users/kuangc/workspace/eclipse/openii/Schemr/data/thalia/virginia.xsd		Thalia	t
52721	washu	kuangc	file:////Users/kuangc/workspace/eclipse/openii/Schemr/data/thalia/washu.xsd		Thalia	t
52731	wisc	kuangc	file:////Users/kuangc/workspace/eclipse/openii/Schemr/data/thalia/wisc.xsd		Thalia	t
52744	wpi	kuangc	file:////Users/kuangc/workspace/eclipse/openii/Schemr/data/thalia/wpi.xsd		Thalia	t
52752	yale	kuangc	file:////Users/kuangc/workspace/eclipse/openii/Schemr/data/thalia/yale.xsd		Thalia	t
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
52365	Entity
52366	Entity
52367	Domain
52368	Attribute
52369	Containment
52370	Containment
52371	Containment
52372	Containment
52373	Containment
52374	Containment
52376	Entity
52377	Entity
52378	Domain
52379	Attribute
52380	Containment
52381	Containment
52382	Containment
52383	Containment
52385	Entity
52386	Entity
52387	Domain
52388	Containment
52389	Containment
52390	Containment
52391	Containment
52392	Containment
52393	Containment
52394	Containment
52396	Entity
52397	Entity
52398	Domain
52399	Attribute
52400	Containment
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
52414	Entity
52415	Entity
52416	Domain
52417	Attribute
52418	Containment
52419	Containment
52420	Containment
52421	Containment
52423	Entity
52424	Entity
52425	Domain
52426	Containment
52427	Containment
52428	Containment
52429	Containment
52430	Containment
52431	Containment
52433	Entity
52434	Entity
52435	Domain
52436	Containment
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
52448	Entity
52449	Domain
52450	Containment
52451	Containment
52453	Entity
52454	Entity
52455	Entity
52456	Domain
52457	Attribute
52458	Attribute
52459	Containment
52460	Containment
52461	Containment
52462	Containment
52463	Containment
52464	Containment
52465	Containment
52466	Containment
52468	Entity
52469	Entity
52470	Domain
52471	Containment
52472	Containment
52473	Containment
52474	Containment
52475	Containment
52476	Containment
52478	Entity
52479	Entity
52480	Domain
52481	Containment
52482	Containment
52483	Containment
52484	Containment
52485	Containment
52486	Containment
52487	Containment
52489	Entity
52490	Entity
52491	Domain
52492	Containment
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
52510	Entity
52511	Entity
52512	Domain
52513	Containment
52514	Containment
52515	Containment
52516	Containment
52517	Containment
52518	Containment
52519	Containment
52520	Containment
52521	Containment
52523	Entity
52524	Entity
52525	Entity
52526	Domain
52527	Containment
52528	Containment
52529	Containment
52530	Containment
52531	Containment
52532	Containment
52533	Containment
52534	Containment
52536	Entity
52537	Entity
52538	Domain
52539	Attribute
52540	Containment
52541	Containment
52542	Containment
52543	Containment
52544	Containment
52545	Containment
52546	Containment
52548	Entity
52549	Entity
52550	Domain
52551	Containment
52552	Containment
52553	Containment
52554	Containment
52555	Containment
52556	Containment
52557	Containment
52559	Entity
52560	Entity
52561	Domain
52562	Containment
52563	Containment
52564	Containment
52565	Containment
52566	Containment
52567	Containment
52568	Containment
52569	Containment
52570	Containment
52572	Entity
52573	Entity
52574	Domain
52575	Containment
52576	Containment
52577	Containment
52578	Containment
52579	Containment
52580	Containment
52581	Containment
52582	Containment
52584	Entity
52585	Entity
52586	Entity
52587	Domain
52588	Attribute
52589	Attribute
52590	Attribute
52591	Attribute
52592	Attribute
52593	Containment
52594	Containment
52595	Containment
52596	Containment
52597	Containment
52598	Containment
52599	Containment
52600	Containment
52602	Entity
52603	Entity
52604	Domain
52605	Containment
52606	Containment
52607	Containment
52608	Containment
52609	Containment
52610	Containment
52611	Containment
52613	Entity
52614	Entity
52615	Entity
52616	Entity
52617	Entity
52618	Domain
52619	Attribute
52620	Attribute
52621	Attribute
52622	Attribute
52623	Attribute
52624	Attribute
52625	Containment
52626	Containment
52627	Containment
52628	Containment
52629	Containment
52630	Containment
52631	Containment
52632	Containment
52633	Containment
52634	Containment
52636	Entity
52637	Entity
52638	Domain
52639	Containment
52640	Containment
52641	Containment
52642	Containment
52643	Containment
52644	Containment
52646	Entity
52647	Entity
52648	Domain
52649	Containment
52650	Containment
52651	Containment
52652	Containment
52653	Containment
52654	Containment
52655	Containment
52656	Containment
52657	Containment
52658	Containment
52660	Entity
52661	Entity
52662	Entity
52663	Domain
52664	Containment
52665	Containment
52666	Containment
52667	Containment
52668	Containment
52669	Containment
52670	Containment
52671	Containment
52672	Containment
52674	Entity
52675	Entity
52676	Domain
52677	Attribute
52678	Attribute
52679	Containment
52680	Containment
52681	Containment
52682	Containment
52683	Containment
52684	Containment
52685	Containment
52687	Entity
52688	Entity
52689	Domain
52690	Attribute
52691	Attribute
52692	Containment
52693	Containment
52694	Containment
52695	Containment
52696	Containment
52697	Containment
52698	Containment
52700	Entity
52701	Entity
52702	Domain
52703	Containment
52704	Containment
52705	Containment
52706	Containment
52707	Containment
52708	Containment
52709	Containment
52711	Entity
52712	Entity
52713	Entity
52714	Domain
52715	Attribute
52716	Containment
52717	Containment
52718	Containment
52719	Containment
52720	Containment
52722	Entity
52723	Entity
52724	Domain
52725	Containment
52726	Containment
52727	Containment
52728	Containment
52729	Containment
52730	Containment
52732	Entity
52733	Entity
52734	Domain
52735	Attribute
52736	Containment
52737	Containment
52738	Containment
52739	Containment
52740	Containment
52741	Containment
52742	Containment
52743	Containment
52745	Entity
52746	Entity
52747	Domain
52748	Containment
52749	Containment
52750	Containment
52751	Containment
52753	Entity
52754	Entity
52755	Domain
52756	Attribute
52757	Containment
52758	Containment
52759	Containment
52760	Containment
52761	Containment
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

