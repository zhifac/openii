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

SELECT pg_catalog.setval('universalseq', 54834, true);


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
52369	Type		52367	-3	1	1
52370	MaxNumEmployees		52366	-1	1	1
52371	CurrentNumCDs		52366	-1	1	1
52372	Manager		52366	-3	1	1
52373	Artist		52367	-3	1	1
52374	Keywords		52368	-3	1	1
52375	Author		52368	-3	1	1
52376	WarehouseID		52365	-1	1	1
52377	AlbumName		52367	-3	1	1
52378	CDCapacity		52366	-1	1	1
52379	Quantity		52365	-1	1	1
52380	Rating		52367	-1	1	1
52381	Genre		52367	-3	1	1
52382	DiscountPrice		52367	-2	1	1
52383	Publisher		52368	-3	1	1
52384	TelephoneNumber		52366	-1	1	1
52385	CurrentNumEmployees		52366	-1	1	1
52386	RecordingCompany		52367	-3	1	1
52387	CurrentNumBooks		52366	-1	1	1
52388	FaxNumber		52366	-1	1	1
52389	Price		52367	-2	1	1
52390	ISBN		52368	-1	1	1
52391	Category		52368	-3	1	1
52392	Title		52365	-3	1	1
52393	BookCapacity		52366	-1	1	1
52394	Location		52366	-3	1	1
52395	ID		52365	-1	1	1
52406	Type		52403	-3	1	1
52407	ASIN		52403	-3	1	1
52408	Telephone		52398	-3	1	1
52409	ZipCode		52398	-1	1	1
52410	City		52398	-3	1	1
52411	Edition		52404	-3	1	1
52412	Description		52401	-3	1	1
52413	Fax		52398	-3	1	1
52414	FirstName		52400	-3	1	1
52415	StreetAddress		52398	-3	1	1
52416	Quantity		52397	-1	1	1
52417	DiscountPrice		52401	-2	1	1
52418	Publisher		52404	-3	1	1
52419	State		52398	-3	1	1
52420	ItemType		52397	-3	1	1
52421	Studio		52401	-3	1	1
52422	ItemId		52397	-3	1	1
52423	WarehouseId		52397	-1	1	1
52424	ManagerName		52398	-3	1	1
52425	Price		52401	-2	1	1
52426	ISBN		52402	-3	1	1
52427	GroupName		52400	-3	1	1
52428	CopyrightYear		52404	-1	1	1
52429	NumberOfEmployees		52398	-1	1	1
52430	ReleaseDate		52401	-4	1	1
52431	Country		52398	-3	1	1
52432	Category		52399	-3	1	1
52433	Title		52401	-3	1	1
52434	LastName		52400	-3	1	1
52448	item		52446	-3	1	1
52449	phone		52436	-3	1	1
52450	startDate		52446	-4	1	1
52451	cdid		52442	-3	1	1
52452	title		52440	-3	1	1
52453	publisher		52443	-1	1	1
52454	middleInitial		52445	-1	1	1
52455	firstName		52445	-3	1	1
52456	id		52445	-3	1	1
52457	mainContact		52436	-3	1	1
52458	mobilePhone		52445	-3	1	1
52459	keyword		52441	-3	1	1
52460	studio		52440	-1	1	1
52461	price		52446	-2	1	1
52462	quantity		52442	-1	1	1
52463	name		52436	-3	1	1
52464	warehouseID		52444	-3	1	1
52465	city		52436	-3	1	1
52466	datePublished		52440	-4	1	1
52467	publisherID		52439	-3	1	1
52468	warehouse		52442	-3	1	1
52469	standardPrice		52440	-2	1	1
52470	studioID		52436	-3	1	1
52471	tracks		52440	-1	1	1
52472	streetAdderss		52436	-3	1	1
52473	lastName		52445	-3	1	1
52474	managerName		52444	-3	1	1
52475	employees		52444	-1	1	1
52476	ISBN		52437	-3	1	1
52477	deliveryCharge		52444	-2	1	1
52478	pages		52443	-1	1	1
52479	cd		52447	-3	1	1
52480	book		52438	-3	1	1
52481	streetAddress		52444	-3	1	1
52482	state		52444	-3	1	1
52483	endDate		52446	-4	1	1
52484	officePhone		52445	-3	1	1
52485	genre		52440	-3	1	1
52486	zipcode		52436	-1	1	1
52487	author		52447	-6	1	1
52488	ID		52440	-3	1	1
52498	capacity		52492	-1	1	1
52499	phone		52492	-1	1	1
52500	WH#		52490	-1	1	1
52501	numDiscs		52496	-1	1	1
52502	type		52496	-1	1	1
52503	title		52497	-1	1	1
52504	album		52496	-1	1	1
52505	numPages		52497	-1	1	1
52506	manager		52492	-1	1	1
52507	publisher		52497	-1	1	1
52508	recordLabel		52496	-1	1	1
52509	fax		52492	-1	1	1
52510	Explicit		52496	-6	1	1
52511	discountPrice		52491	-2	1	1
52512	keyword		52494	-1	1	1
52513	PID		52490	-1	1	1
52514	price		52491	-2	1	1
52515	location		52492	-1	1	1
52516	genre		52496	-1	1	1
52517	quantity		52490	-1	1	1
52518	name		52495	-1	1	1
52525	Trucks		52520	-1	1	1
52526	ProductID		52521	-1	1	1
52527	Genre		52522	-3	1	1
52528	WarehouseName		52521	-3	1	1
52529	InStock		52521	-3	1	1
52530	Manager		52520	-3	1	1
52531	Author		52523	-3	1	1
52532	Artist		52522	-3	1	1
52533	City		52520	-3	1	1
52534	StaffSize		52520	-1	1	1
52535	StorageRating		52520	-1	1	1
52536	ExpectedInStock		52521	-4	1	1
52537	CustomerRating		52522	-1	1	1
52538	Album		52522	-3	1	1
52539	FaxNumber		52520	-1	1	1
52540	UniversalID		52522	-1	1	1
52541	Price		52522	-1	1	1
52542	ISBN		52523	-1	1	1
52543	PhoneNumber		52520	-1	1	1
52544	ProductType		52524	-3	1	1
52545	ZIP		52520	-1	1	1
52546	StreetAddress		52520	-3	1	1
52547	Title		52523	-3	1	1
52548	Quantity		52521	-1	1	1
52559	Type		52555	-1	1	1
52560	Capacity		52557	-1	1	1
52561	WarehouseName		52550	-1	1	1
52562	BookISBN		52556	-1	1	1
52563	Keywords		52558	-1	1	1
52564	AlbumName		52555	-1	1	1
52565	Year		52555	-1	1	1
52566	Edition		52558	-1	1	1
52567	AuthorName		52556	-1	1	1
52568	CDNumber		52551	-1	1	1
52569	Name		52557	-1	1	1
52570	QuantityInStock		52550	-1	1	1
52571	ArtistName		52551	-1	1	1
52572	DiscountPrice		52554	-2	1	1
52573	Genre		52555	-1	1	1
52574	Publisher		52558	-1	1	1
52575	ProductNumber		52555	-1	1	1
52576	RecordingCompany		52555	-1	1	1
52577	ISBN		52558	-1	1	1
52578	Price		52555	-2	1	1
52579	Contact		52552	-1	1	1
52580	Number		52553	-1	1	1
52581	CopyWrite		52558	-1	1	1
52582	Category		52558	-1	1	1
52583	Title		52558	-1	1	1
52584	Location		52557	-1	1	1
52596	phone		52594	-1	1	1
52597	cdType		52595	-1	1	1
52598	title		52593	-1	1	1
52599	buissnessVol		52590	-2	1	1
52600	productID		52587	-1	1	1
52601	address		52594	-1	1	1
52602	freeSpace		52590	-2	1	1
52603	albumName		52595	-1	1	1
52604	fax		52594	-1	1	1
52605	company		52595	-1	1	1
52606	artistID		52592	-1	1	1
52607	houseID		52590	-1	1	1
52608	keyword		52591	-1	1	1
52609	priceType		52587	-1	1	1
52610	price		52587	-2	1	1
52611	zip		52594	-1	1	1
52612	name		52592	-1	1	1
52613	quantity		52586	-1	1	1
52614	city		52594	-1	1	1
52615	productCatagory		52589	-1	1	1
52616	genreType		52588	-1	1	1
52617	numEmployees		52590	-1	1	1
52618	managerName		52594	-1	1	1
52619	ISBN		52593	-1	1	1
52620	state		52594	-1	1	1
52632	phone		52630	-1	1	1
52633	word		52629	-1	1	1
52634	title		52623	-1	1	1
52635	type		52623	-1	1	1
52636	ASIN		52625	-1	1	1
52637	productID		52622	-1	1	1
52638	manager		52630	-1	1	1
52639	artist		52625	-1	1	1
52640	fax		52630	-1	1	1
52641	discountPrice		52623	-2	1	1
52642	ISBN		52629	-1	1	1
52643	price		52623	-2	1	1
52644	streetAddress		52626	-1	1	1
52645	state		52626	-1	1	1
52646	quantity		52622	-1	1	1
52647	warehouseID		52622	-1	1	1
52648	name		52624	-1	1	1
52649	zipcode		52626	-1	1	1
52650	city		52626	-1	1	1
52651	author		52631	-1	1	1
52660	itemNum		52656	-1	1	1
52661	phone		52655	-1	1	1
52662	authorArtist		52656	-1	1	1
52663	salePrice		52659	-2	1	1
52664	type		52658	-1	1	1
52665	NunOfDiscs		52658	-1	1	1
52666	suggestion		52654	-1	1	1
52667	category		52659	-1	1	1
52668	address		52655	-1	1	1
52669	productCap		52655	-1	1	1
52670	publisher		52659	-1	1	1
52671	manager		52655	-1	1	1
52672	fax		52655	-1	1	1
52673	cover		52659	-1	1	1
52674	keyword		52657	-1	1	1
52675	employeeCap		52655	-1	1	1
52676	label		52658	-1	1	1
52677	price		52654	-2	1	1
52678	warehouseNum		52653	-1	1	1
52679	genre		52658	-1	1	1
52680	name		52654	-1	1	1
52681	quantity		52653	-1	1	1
52682	review		52654	-1	1	1
52687	Publisher		52685	-3	1	1
52688	MaxEmployees		52686	-1	1	1
52689	State		52686	-3	1	1
52690	Manager		52686	-3	1	1
52691	TelephoneNumber		52686	-3	1	1
52692	ItemType		52685	-3	1	1
52693	Zip		52686	-3	1	1
52694	OurPrice		52685	-2	1	1
52695	CurCapacity		52686	-1	1	1
52696	Author		52685	-3	1	1
52697	Keywords		52685	-3	1	1
52698	WarehouseID		52684	-3	1	1
52699	ItemID		52684	-3	1	1
52700	City		52686	-3	1	1
52701	CurEmployees		52686	-1	1	1
52702	Amount		52684	-1	1	1
52703	FaxNumber		52686	-3	1	1
52704	Description		52685	-3	1	1
52705	Address		52686	-3	1	1
52706	MaxCapacity		52686	-1	1	1
52707	Category		52685	-3	1	1
52708	Title		52685	-3	1	1
52709	SuggestedPrice		52685	-2	1	1
52718	Type		52711	-1	1	1
52719	DiscountPrice		52716	-2	1	1
52720	ProdCode		52715	-2	1	1
52721	State		52717	-1	1	1
52722	Telephone		52717	-1	1	1
52723	Manager		52717	-1	1	1
52724	ZipCode		52717	-1	1	1
52725	FAX		52717	-1	1	1
52726	City		52717	-1	1	1
52727	StreetAddr		52717	-1	1	1
52728	CAPACITY		52717	-1	1	1
52729	ISBN		52712	-2	1	1
52730	Price		52711	-2	1	1
52731	FirstName		52714	-1	1	1
52732	Category		52711	-1	1	1
52733	Title		52711	-1	1	1
52734	Name		52713	-1	1	1
52735	Quantity		52712	-1	1	1
52736	MiddleName		52714	-1	1	1
52737	WH_ID		52712	-1	1	1
52738	LastName		52714	-1	1	1
52755	estCapVolBiz		52740	-1	1	1
52756	title		52749	-1	1	1
52757	genreID		52744	-1	1	1
52758	telephone		52740	-1	1	1
52759	manager		52740	-1	1	1
52760	fax		52740	-1	1	1
52761	artistID		52741	-1	1	1
52762	authorEmailAddress		52754	-1	1	1
52763	keyword		52746	-1	1	1
52764	price		52751	-2	1	1
52765	categoryName		52742	-1	1	1
52766	authorID		52750	-1	1	1
52767	name		52751	-1	1	1
52768	warehouseID		52748	-1	1	1
52769	city		52740	-1	1	1
52770	stock		52748	-1	1	1
52771	recordingCompany		52751	-1	1	1
52772	musicID		52748	-1	1	1
52773	discounted		52749	-2	1	1
52774	type		52747	-1	1	1
52775	stAddress		52740	-1	1	1
52776	artist		52743	-1	1	1
52777	managerEmailAddress		52740	-1	1	1
52778	typeID		52751	-1	1	1
52779	ISBN		52752	-1	1	1
52780	artistEmailAddress		52743	-1	1	1
52781	estCapNumEmp		52740	-1	1	1
52782	recordingCompanyEmailAddress		52751	-1	1	1
52783	categoryID		52745	-1	1	1
52784	state		52740	-1	1	1
52785	genre		52753	-1	1	1
52786	zipcode		52740	-1	1	1
52787	author		52754	-1	1	1
52797	Type		52796	-1	1	1
52798	Capacity		52791	-1	1	1
52799	NumEmployees		52791	-1	1	1
52800	Manager		52791	-1	1	1
52801	Artist		52794	-1	1	1
52802	Explicit		52796	-6	1	1
52803	RecordLabel		52796	-1	1	1
52804	AuthorName		52792	-1	1	1
52805	Fax		52791	-1	1	1
52806	Quantity		52789	-1	1	1
52807	DiscountPrice		52790	-2	1	1
52808	Genre		52796	-1	1	1
52809	Publisher		52795	-1	1	1
52810	Keyword		52793	-1	1	1
52811	WHNum		52789	-1	1	1
52812	Phone		52791	-1	1	1
52813	Album		52796	-1	1	1
52814	NumCDs		52796	-1	1	1
52815	Price		52790	-2	1	1
52816	ISBN		52793	-1	1	1
52817	Category		52795	-1	1	1
52818	Title		52795	-1	1	1
52819	Location		52791	-1	1	1
52820	ID		52789	-1	1	1
52821	NumPages		52795	-1	1	1
52829	volBusiness		52824	-2	1	1
52830	title		52827	-1	1	1
52831	telephone		52825	-1	1	1
52832	managername		52825	-1	1	1
52833	fax		52825	-1	1	1
52834	warehousecode		52825	-1	1	1
52835	company		52823	-1	1	1
52836	price		52823	-2	1	1
52837	zip		52828	-1	1	1
52838	location		52828	-1	1	1
52839	name		52823	-1	1	1
52840	quantity		52826	-1	1	1
52841	stock		52826	-1	1	1
52842	city		52828	-1	1	1
52843	bookcategory		52827	-1	1	1
52844	capacity		52824	-2	1	1
52845	CISBN		52826	-1	1	1
52846	types		52823	-1	1	1
52847	keywords		52827	-1	1	1
52848	discprice		52827	-2	1	1
52849	category		52823	-1	1	1
52850	artist		52823	-1	1	1
52851	ISBN		52823	-1	1	1
52852	BISBN		52826	-1	1	1
52853	numEmployee		52824	-1	1	1
52854	streetAddress		52828	-1	1	1
52855	state		52828	-1	1	1
52856	author		52827	-1	1	1
52864	Type		52859	-1	1	1
52865	WareHouseCode		52862	-1	1	1
52866	Stock		52858	-1	1	1
52867	NumEmployees		52863	-1	1	1
52868	Telephone		52860	-1	1	1
52869	ZipCode		52862	-1	1	1
52870	Artist		52859	-1	1	1
52871	City		52862	-1	1	1
52872	AuthorName		52861	-1	1	1
52873	KeyWord		52861	-1	1	1
52874	price		52859	-1	1	1
52875	StreetAddress		52862	-1	1	1
52876	name		52859	-1	1	1
52877	Quantity		52858	-1	1	1
52878	State		52862	-1	1	1
52879	ISBook		52858	-1	1	1
52880	BookCategory		52861	-1	1	1
52881	ManagerName		52860	-1	1	1
52882	Price		52861	-1	1	1
52883	ISBN		52859	-1	1	1
52884	IsCD		52858	-1	1	1
52885	Category		52859	-1	1	1
52886	Volume		52863	-1	1	1
52887	Company		52859	-1	1	1
52888	Title		52861	-1	1	1
52889	DisPrice		52861	-1	1	1
52903	title		52899	-1	1	1
52904	genreID		52898	-1	1	1
52905	address		52891	-1	1	1
52906	telephone		52891	-1	1	1
52907	fax		52891	-1	1	1
52908	albumName		52898	-1	1	1
52909	firstName		52897	-1	1	1
52910	artistID		52902	-1	1	1
52911	price		52898	-2	1	1
52912	authorID		52892	-1	1	1
52913	quantity		52901	-1	1	1
52914	warehouseID		52901	-1	1	1
52915	city		52891	-1	1	1
52916	employmentCount		52891	-1	1	1
52917	genreName		52894	-1	1	1
52918	musicID		52901	-1	1	1
52919	yearPublished		52899	-1	1	1
52920	lastName		52897	-1	1	1
52921	discountPrice		52899	-2	1	1
52922	maxVolume		52891	-1	1	1
52923	managerName		52891	-1	1	1
52924	typeName		52900	-1	1	1
52925	ISBN		52892	-1	1	1
52926	genreDescription		52894	-1	1	1
52927	cdTypeID		52898	-1	1	1
52928	state		52891	-1	1	1
52937	startDate		52933	-4	1	1
52938	title		52934	-3	1	1
52939	percentOff		52933	-2	1	1
52940	qty		52930	-1	1	1
52941	publisher		52936	-3	1	1
52942	manager		52932	-3	1	1
52943	trackName		52935	-3	1	1
52944	SKU		52930	-3	1	1
52945	trackNo		52935	-1	1	1
52946	zip		52932	-1	1	1
52947	city		52932	-3	1	1
52948	releaseDate		52934	-4	1	1
52949	whId		52930	-3	1	1
52950	facilityCode		52932	-3	1	1
52951	stAddress		52932	-3	1	1
52952	productId		52931	-3	1	1
52953	category		52931	-3	1	1
52954	artist		52934	-3	1	1
52955	isbn		52936	-3	1	1
52956	authors		52935	-3	1	1
52957	playtime		52935	-3	1	1
52958	discountCode		52931	-3	1	1
52959	cdID		52934	-1	1	1
52960	cdRef		52935	-1	1	1
52961	pubPrice		52934	-2	1	1
52962	year		52936	-1	1	1
52963	pages		52936	-1	1	1
52964	label		52934	-3	1	1
52965	description		52933	-3	1	1
52966	state		52932	-3	1	1
52967	endDate		52933	-4	1	1
52974	Type		52970	-3	1	1
52975	Genre		52970	-3	1	1
52976	ZipCode		52969	-1	1	1
52977	Author		52972	-3	1	1
52978	Keywords		52972	-3	1	1
52979	Artist		52970	-3	1	1
52980	RecordCompany		52970	-3	1	1
52981	AnnualSale		52969	-6	1	1
52982	AlbumName		52970	-3	1	1
52983	SalePrice		52970	-6	1	1
52984	ISBN		52971	-3	1	1
52985	Contact		52969	-3	1	1
52986	RecordNumber		52973	-3	1	1
52987	Discount		52970	-1	1	1
52988	Category		52972	-3	1	1
52989	StoreNumber		52973	-1	1	1
52990	Title		52972	-3	1	1
52991	EmployeeNumber		52969	-1	1	1
52992	Units		52973	-1	1	1
52993	Location		52969	-3	1	1
52999	DiscountedPrice		52998	-6	1	1
53000	NumEmployees		52996	-1	1	1
53001	ItemsShippedAnnually		52996	-1	1	1
53002	Telephone		52996	-3	1	1
53003	AnnualOperatingCost		52996	-6	1	1
53004	Artist		52997	-3	1	1
53005	Keywords		52998	-3	1	1
53006	Author		52998	-3	1	1
53007	FAX		52996	-3	1	1
53008	WarehouseID		52995	-1	1	1
53009	City		52996	-3	1	1
53010	ItemNumber		52995	-3	1	1
53011	CDTitle		52997	-3	1	1
53012	BookTitle		52998	-3	1	1
53013	StreetAddress		52996	-3	1	1
53014	Quantity		52995	-1	1	1
53015	Rating		52997	-1	1	1
53016	Genre		52997	-3	1	1
53017	State		52996	-3	1	1
53018	Zip		52996	-3	1	1
53019	ManagerName		52996	-3	1	1
53020	ISBN		52998	-3	1	1
53021	CDType		52997	-3	1	1
53022	SellingPrice		52997	-6	1	1
53023	Category		52998	-3	1	1
53024	Company		52997	-3	1	1
53025	IDNumber		52997	-3	1	1
53036	faxnumber		53033	-1	1	1
53037	telephonenum		53033	-1	1	1
53038	title		53032	-1	1	1
53039	address		53033	-1	1	1
53040	prodID		53035	-1	1	1
53041	manager		53033	-1	1	1
53042	publisher		53032	-1	1	1
53043	companyRecording		53031	-1	1	1
53044	CustomerRating		53031	-1	1	1
53045	keyword		53030	-1	1	1
53046	numberofemployees		53033	-1	1	1
53047	SalesRating		53032	-1	1	1
53048	price		53028	-2	1	1
53049	musiclanguage		53031	-1	1	1
53050	stock		53035	-1	1	1
53051	discount		53028	-2	1	1
53052	type		53027	-1	1	1
53053	category		53029	-1	1	1
53054	isbn		53029	-1	1	1
53055	artist		53031	-1	1	1
53056	warehouseid		53035	-1	1	1
53057	cdID		53027	-1	1	1
53058	state		53033	-1	1	1
53059	genre		53034	-1	1	1
53060	zipcode		53033	-1	1	1
53061	author		53032	-1	1	1
53062	albumname		53031	-1	1	1
53063	volumeofbusiness		53033	-1	1	1
53087	Street2		53086	-1	1	1
53088	SSN		53078	-1	1	1
53089	ProductCategoryId		53066	-1	1	1
53090	DiscountId		53067	-1	1	1
53091	City		53086	-1	1	1
53092	CreateDate		53068	-4	1	1
53093	Review		53068	-3	1	1
53094	Fax		53075	-1	1	1
53095	FirstName		53078	-1	1	1
53096	EmployeeId		53075	-1	1	1
53097	AuthorId		53069	-1	1	1
53098	Expire		53067	-4	1	1
53099	ImageId		53065	-1	1	1
53100	Quantity		53076	-1	1	1
53101	Rating		53068	-1	1	1
53102	WharehouseId		53076	-1	1	1
53103	LoginName		53068	-1	1	1
53104	VideoId		53085	-1	1	1
53105	State		53086	-1	1	1
53106	ReviewId		53068	-1	1	1
53107	FileName		53085	-1	1	1
53108	Phone		53075	-1	1	1
53109	AddressId		53074	-1	1	1
53110	FilePath		53085	-1	1	1
53111	GroupName		53077	-1	1	1
53112	Country		53086	-1	1	1
53113	Title		53084	-1	1	1
53114	Type		53071	-1	1	1
53115	RecordLabelId		53083	-1	1	1
53116	ModDate		53082	-4	1	1
53117	AlbumName		53073	-1	1	1
53118	RecordLabel		53083	-1	1	1
53119	Description		53066	-3	1	1
53120	ProductId		53085	-1	1	1
53121	Street1		53086	-1	1	1
53122	SoundId		53072	-1	1	1
53123	Name		53070	-1	1	1
53124	Genre		53080	-1	1	1
53125	Zip		53086	-1	1	1
53126	TypeId		53071	-1	1	1
53127	MusicId		53080	-1	1	1
53128	ArtistId		53070	-1	1	1
53129	ISBN		53079	-1	1	1
53130	Price		53084	-2	1	1
53131	Email		53082	-1	1	1
53132	GroupId		53070	-1	1	1
53133	CategoryId		53079	-1	1	1
53134	GenreId		53080	-1	1	1
53135	Category		53079	-1	1	1
53136	ManagerId		53074	-1	1	1
53137	Id		53076	-1	1	1
53138	LastName		53078	-1	1	1
53139	Rate		53067	-2	1	1
53148	comments		53141	-3	1	1
53149	width		53146	-2	1	1
53150	ASIN		53144	-1	1	1
53151	login		53141	-3	1	1
53152	publisher		53146	-3	1	1
53153	prodID		53147	-1	1	1
53154	date		53144	-4	1	1
53155	editorReview		53146	-3	1	1
53156	firstName		53142	-3	1	1
53157	height		53146	-2	1	1
53158	price		53144	-2	1	1
53159	cutOff		53144	-2	1	1
53160	zip		53145	-1	1	1
53161	name		53144	-3	1	1
53162	introduction		53144	-3	1	1
53163	city		53145	-3	1	1
53164	rate		53141	-1	1	1
53165	password		53142	-3	1	1
53166	discount		53144	-2	1	1
53167	street		53145	-3	1	1
53168	length		53146	-2	1	1
53169	wID		53147	-1	1	1
53170	cover		53146	-1	1	1
53171	lastName		53142	-3	1	1
53172	amount		53147	-1	1	1
53173	ISBN		53146	-1	1	1
53174	pages		53146	-1	1	1
53175	state		53145	-3	1	1
53176	weight		53146	-2	1	1
53186	phone		53184	-3	1	1
53187	discs		53181	-1	1	1
53188	width		53185	-2	1	1
53189	release_date		53181	-4	1	1
53190	publisher		53185	-3	1	1
53191	item_uid		53179	-1	1	1
53192	fax		53184	-3	1	1
53193	id		53179	-1	1	1
53194	price		53180	-2	1	1
53195	summary		53185	-3	1	1
53196	cover_art		53181	-3	1	1
53197	street		53184	-3	1	1
53198	depth		53185	-2	1	1
53199	uid		53180	-1	1	1
53200	cd_id		53182	-1	1	1
53201	published_date		53185	-4	1	1
53202	employees		53184	-1	1	1
53203	text		53178	-3	1	1
53204	state		53184	-3	1	1
53205	source		53179	-3	1	1
53206	author		53185	-3	1	1
53207	weight		53185	-2	1	1
53208	zipcode		53184	-1	1	1
53209	format		53185	-6	1	1
53210	tracking_number		53179	-3	1	1
53211	title		53178	-3	1	1
53212	rating		53178	-1	1	1
53213	height		53185	-2	1	1
53214	url		53182	-3	1	1
53215	list_price		53181	-2	1	1
53216	name		53178	-3	1	1
53217	quantity		53179	-1	1	1
53218	city		53184	-3	1	1
53219	warehouse_id		53179	-1	1	1
53220	type		53180	-6	1	1
53221	length		53181	-1	1	1
53222	category		53185	-3	1	1
53223	cubicfeet		53184	-1	1	1
53224	eta		53179	-4	1	1
53225	artist		53181	-3	1	1
53226	tracks		53181	-1	1	1
53227	ISBN		53185	-3	1	1
53228	pages		53185	-1	1	1
53229	label		53181	-3	1	1
53230	track		53182	-1	1	1
53231	email		53178	-3	1	1
53232	genre		53181	-3	1	1
53233	cost_per_month		53184	-2	1	1
53234	squarefeet		53184	-1	1	1
53241	Type		53237	-3	1	1
53242	ASIN		53239	-3	1	1
53243	Manager		53238	-3	1	1
53244	Telephone		53238	-3	1	1
53245	Author		53240	-3	1	1
53246	Artist		53239	-3	1	1
53247	City		53238	-3	1	1
53248	EmployeeCount		53238	-1	1	1
53249	Label		53239	-3	1	1
53250	CustomerRating		53240	-2	1	1
53251	Fax		53238	-3	1	1
53252	StreetAddress		53238	-3	1	1
53253	CDListPrice		53239	-2	1	1
53254	BookSellPrice		53240	-2	1	1
53255	Genre		53239	-3	1	1
53256	InStock		53236	-6	1	1
53257	Publisher		53240	-3	1	1
53258	Keyword		53240	-3	1	1
53259	State		53238	-3	1	1
53260	LocsAvailable		53236	-1	1	1
53261	Zip		53238	-1	1	1
53262	ItemID		53236	-1	1	1
53263	Album		53239	-3	1	1
53264	ISBN		53240	-3	1	1
53265	CDType		53239	-3	1	1
53266	CDSellPrice		53239	-2	1	1
53267	BookListPrice		53240	-2	1	1
53268	ShipTime		53236	-1	1	1
53269	Category		53240	-3	1	1
53270	Title		53240	-3	1	1
53271	ChartNumber		53239	-1	1	1
53272	PubYear		53240	-1	1	1
53283	Type		53282	-1	1	1
53284	Status		53274	-6	1	1
53285	NumTracks		53280	-1	1	1
53286	Label		53280	-1	1	1
53287	Media		53280	-1	1	1
53288	CreaterID		53276	-1	1	1
53289	Review		53277	-1	1	1
53290	Pages		53281	-1	1	1
53291	Address		53282	-1	1	1
53292	SaleRank		53277	-1	1	1
53293	Releasedate		53280	-4	1	1
53294	Name		53282	-1	1	1
53295	BookID		53281	-1	1	1
53296	Genre		53275	-1	1	1
53297	Publisher		53281	-1	1	1
53298	Cost		53277	-1	1	1
53299	WareID		53274	-1	1	1
53300	ItemID		53275	-1	1	1
53301	ItemID1		53279	-1	1	1
53302	MusicID		53280	-1	1	1
53303	ItemID2		53279	-1	1	1
53304	ISBN		53281	-1	1	1
53305	Binding		53281	-1	1	1
53306	NumDiscs		53280	-1	1	1
53307	AgeLevel		53281	-1	1	1
53308	Title		53280	-1	1	1
53309	Location		53282	-1	1	1
53325	Hardcover		53323	-6	1	1
53326	Author		53313	-1	1	1
53327	City		53324	-1	1	1
53328	Street		53324	-1	1	1
53329	QID		53311	-1	1	1
53330	Count		53319	-1	1	1
53331	CID		53317	-1	1	1
53332	PID		53318	-1	1	1
53333	Pages		53323	-1	1	1
53334	DID		53318	-1	1	1
53335	ValidStart		53312	-4	1	1
53336	DatePlaced		53314	-4	1	1
53337	DateShipped		53314	-4	1	1
53338	ZIP		53324	-1	1	1
53339	Discount		53312	-2	1	1
53340	CreditCard		53317	-1	1	1
53341	BillTo		53314	-1	1	1
53342	Name		53317	-1	1	1
53343	State		53324	-1	1	1
53344	ExpDate		53317	-4	1	1
53345	Payment		53314	-2	1	1
53346	GID		53316	-1	1	1
53347	WID		53319	-1	1	1
53348	Tracks		53315	-1	1	1
53349	Price		53311	-2	1	1
53350	Country		53324	-1	1	1
53351	Duration		53315	-2	1	1
53352	ShipTo		53314	-1	1	1
53353	OID		53318	-1	1	1
53354	AID		53317	-1	1	1
53355	ValidStop		53312	-4	1	1
53364	producer		53361	-3	1	1
53365	discountedprice		53359	-2	1	1
53366	reviewerSource		53358	-3	1	1
53367	shippingrate		53357	-2	1	1
53368	title		53361	-3	1	1
53369	address		53360	-3	1	1
53370	publisher		53362	-3	1	1
53371	rating		53358	-1	1	1
53372	productid		53361	-1	1	1
53373	availability		53363	-1	1	1
53374	shippingdeal		53357	-3	1	1
53375	reviewtext		53358	-3	1	1
53376	supplierid		53361	-1	1	1
53377	baseprice		53359	-2	1	1
53378	length		53362	-1	1	1
53379	dimensions		53359	-3	1	1
53380	artist		53361	-3	1	1
53381	warehouseid		53357	-1	1	1
53382	shippingstyle		53357	-3	1	1
53383	warehousename		53360	-3	1	1
53384	ISBN		53362	-1	1	1
53385	otheroffers		53359	-3	1	1
53386	description		53361	-3	1	1
53387	zipcode		53360	-1	1	1
53388	author		53362	-3	1	1
53389	format		53361	-3	1	1
53405	sampleurl		53391	-1	1	1
53406	total_time_to_dispatch		53404	-1	1	1
53407	width		53403	-6	1	1
53408	track_number		53398	-1	1	1
53409	is_creator		53400	-6	1	1
53410	date		53400	-4	1	1
53411	id		53396	-1	1	1
53412	Publication_date		53401	-4	1	1
53413	keyword		53394	-1	1	1
53414	fedex_pickups		53396	-1	1	1
53415	num_shifts		53396	-1	1	1
53416	Format		53401	-1	1	1
53417	rev_id		53400	-1	1	1
53418	blurb		53395	-1	1	1
53419	num_discs		53395	-1	1	1
53420	other		53401	-1	1	1
53421	is_editoral		53400	-6	1	1
53422	artist_order		53397	-1	1	1
53423	usps_pickups		53396	-1	1	1
53424	reviewer_id		53400	-1	1	1
53425	num_employees		53396	-1	1	1
53426	text		53400	-1	1	1
53427	special_features		53403	-1	1	1
53428	Num_sold		53401	-1	1	1
53429	Release_date		53395	-4	1	1
53430	weight		53395	-6	1	1
53431	format		53398	-1	1	1
53432	ups_pickups		53396	-1	1	1
53433	title		53391	-1	1	1
53434	ASIN		53395	-1	1	1
53435	postal_code		53396	-1	1	1
53436	num_pages		53401	-1	1	1
53437	manager		53396	-1	1	1
53438	rating		53400	-1	1	1
53439	Label		53395	-1	1	1
53440	date_of_birth		53402	-4	1	1
53441	location_id		53404	-1	1	1
53442	height		53403	-6	1	1
53443	num_sold_at_item_location		53404	-1	1	1
53444	state_province		53396	-1	1	1
53445	additional_location_information		53396	-1	1	1
53446	num_at_location		53404	-1	1	1
53447	url		53398	-1	1	1
53448	name		53392	-1	1	1
53449	series		53391	-1	1	1
53450	city		53396	-1	1	1
53451	illustrator		53401	-1	1	1
53452	warehouse_id		53403	-1	1	1
53453	book_title_id		53401	-1	1	1
53454	loc-id		53403	-1	1	1
53455	Publisher		53401	-1	1	1
53456	artist_id		53397	-1	1	1
53457	street_address		53396	-1	1	1
53458	length		53403	-6	1	1
53459	album		53398	-1	1	1
53460	category		53393	-1	1	1
53461	product_id		53400	-1	1	1
53462	album_id		53395	-1	1	1
53463	Hours_of_operation		53396	-1	1	1
53464	ISBN		53401	-1	1	1
53465	country		53396	-1	1	1
53466	track_name		53398	-1	1	1
53467	MSRP		53401	-6	1	1
53468	affiliation		53392	-1	1	1
53469	item_id		53404	-1	1	1
53470	phone_number		53396	-1	1	1
53471	Standard_price		53401	-6	1	1
53481	Capacity		53479	-3	1	1
53482	WarehouseName		53473	-3	1	1
53483	Kind		53474	-3	1	1
53484	Telephone		53479	-3	1	1
53485	Manager		53479	-3	1	1
53486	City		53479	-3	1	1
53487	Street		53479	-3	1	1
53488	Zipcode		53479	-3	1	1
53489	Year		53476	-1	1	1
53490	Fax		53479	-3	1	1
53491	FirstName		53478	-3	1	1
53492	Month		53476	-3	1	1
53493	Name		53477	-3	1	1
53494	Quantity		53473	-1	1	1
53495	Rating		53476	-6	1	1
53496	Word		53475	-3	1	1
53497	Paperback		53480	-3	1	1
53498	State		53479	-3	1	1
53499	MaxEmployees		53479	-1	1	1
53500	Price		53476	-6	1	1
53501	ISBN		53473	-3	1	1
53502	MSRP		53476	-6	1	1
53503	Volume		53479	-1	1	1
53504	Category		53476	-3	1	1
53505	Title		53480	-3	1	1
53506	Company		53476	-3	1	1
53507	MiddleName		53478	-3	1	1
53508	LastName		53478	-3	1	1
53514	Status		53510	-3	1	1
53515	Type		53512	-3	1	1
53516	Manager		53511	-3	1	1
53517	Artist		53512	-3	1	1
53518	Author		53513	-3	1	1
53519	City		53511	-3	1	1
53520	Catalog_Number		53512	-3	1	1
53521	Number_Of_Discs		53512	-1	1	1
53522	Address		53511	-3	1	1
53523	Running_Time		53512	-3	1	1
53524	Fax_Number		53511	-3	1	1
53525	Genre		53512	-3	1	1
53526	Publisher		53513	-3	1	1
53527	Telephone_Number		53511	-3	1	1
53528	Keyword		53513	-3	1	1
53529	State		53511	-3	1	1
53530	Number_Of_Employees		53511	-3	1	1
53531	Record_Label		53512	-3	1	1
53532	Volume_Of_Business		53511	-3	1	1
53533	Amount_In_Stock		53510	-1	1	1
53534	Price		53512	-6	1	1
53535	ISBN		53513	-3	1	1
53536	Year_Released		53512	-1	1	1
53537	Zip_Code		53510	-3	1	1
53538	Category		53513	-3	1	1
53539	Title		53512	-3	1	1
53540	ID		53510	-3	1	1
53547	Type		53544	-3	1	1
53548	Warehouse_ID		53542	-1	1	1
53549	NumEmployees		53546	-1	1	1
53550	Capacity		53546	-1	1	1
53551	Artist		53544	-3	1	1
53552	Keywords		53545	-3	1	1
53553	Author		53545	-3	1	1
53554	City		53546	-3	1	1
53555	fax_n		53543	-1	1	1
53556	Street		53546	-3	1	1
53557	Edition		53545	-1	1	1
53558	Discount		53544	-2	1	1
53559	R_Company		53544	-3	1	1
53560	phone_n		53543	-1	1	1
53561	name		53543	-3	1	1
53562	Quantity		53542	-1	1	1
53563	Rating		53544	-1	1	1
53564	Product_ID		53542	-1	1	1
53565	Genre		53544	-3	1	1
53566	Publisher		53545	-3	1	1
53567	State		53546	-3	1	1
53568	category		53545	-3	1	1
53569	Zip		53546	-1	1	1
53570	CD_ID		53544	-3	1	1
53571	Price		53544	-2	1	1
53572	ISBN		53545	-3	1	1
53573	Contact_ID		53543	-1	1	1
53574	Title		53543	-3	1	1
53589	comments		53577	-3	1	1
53590	Year_of_Publication		53587	-4	1	1
53591	SSN		53578	-1	1	1
53592	title		53579	-3	1	1
53593	Date_of_Hire		53578	-4	1	1
53594	Manager		53581	-3	1	1
53595	Restrictions		53576	-3	1	1
53596	Home_Phone		53578	-3	1	1
53597	Author		53587	-3	1	1
53598	Salary		53578	-6	1	1
53599	City		53581	-3	1	1
53600	Label		53579	-3	1	1
53601	contact_info		53586	-3	1	1
53602	Warehouse		53580	-3	1	1
53603	card_number		53585	-1	1	1
53604	Year		53579	-1	1	1
53605	Description		53584	-3	1	1
53606	Phone_number		53581	-6	1	1
53607	price		53580	-6	1	1
53608	Address		53581	-3	1	1
53609	username		53586	-3	1	1
53610	zip		53588	-1	1	1
53611	quantity		53580	-1	1	1
53612	Department		53578	-3	1	1
53613	Rating		53577	-1	1	1
53614	exp_date		53585	-4	1	1
53615	date_created		53588	-4	1	1
53616	city		53588	-3	1	1
53617	First_Name		53578	-3	1	1
53618	password		53586	-3	1	1
53619	user		53585	-3	1	1
53620	Publisher		53587	-3	1	1
53621	type		53585	-3	1	1
53622	street		53588	-3	1	1
53623	Zip		53581	-6	1	1
53624	End		53576	-6	1	1
53625	User		53577	-3	1	1
53626	Last_Name		53578	-3	1	1
53627	artist		53579	-3	1	1
53628	WarehouseId		53581	-3	1	1
53629	Amount		53576	-6	1	1
53630	Start		53576	-4	1	1
53631	ISBN		53576	-1	1	1
53632	Position		53578	-3	1	1
53633	state		53588	-1	1	1
53634	Category		53583	-3	1	1
53635	DOB		53578	-4	1	1
53639	DiscountPrice		53638	-2	1	1
53640	Price		53638	-2	1	1
53641	ISBN		53638	-1	1	1
53642	Publisher		53638	-3	1	1
53643	Keywords		53638	-3	1	1
53644	Author		53638	-3	1	1
53645	Category		53638	-3	1	1
53646	Title		53637	-3	1	1
53647	Quantity		53637	-1	1	1
53648	Location		53637	-3	1	1
53649	ID		53637	-1	1	1
53653	CISBN		53651	-1	1	1
53654	ISBN		53652	-1	1	1
53655	BISBN		53651	-1	1	1
53656	title		53652	-1	1	1
53657	price		53652	-2	1	1
53658	keywords		53652	-1	1	1
53659	discprice		53652	-2	1	1
53660	warehousecode		53651	-1	1	1
53661	quantity		53651	-1	1	1
53662	stock		53651	-1	1	1
53663	author		53652	-1	1	1
53664	bookcategory		53652	-1	1	1
53670	DiscountPrice		53668	-2	1	1
53671	Publisher		53668	-3	1	1
53672	ItemType		53666	-3	1	1
53673	ItemId		53666	-3	1	1
53674	WarehouseId		53666	-1	1	1
53675	Edition		53668	-3	1	1
53676	ISBN		53667	-3	1	1
53677	Price		53668	-2	1	1
53678	Description		53668	-3	1	1
53679	FirstName		53667	-3	1	1
53680	CopyrightYear		53668	-1	1	1
53681	Category		53669	-3	1	1
53682	Title		53668	-3	1	1
53683	Quantity		53666	-1	1	1
53684	LastName		53667	-3	1	1
53693	item		53692	-3	1	1
53694	phone		53688	-3	1	1
53695	startDate		53692	-4	1	1
53696	title		53690	-3	1	1
53697	publisher		53690	-1	1	1
53698	middleInitial		53691	-1	1	1
53699	firstName		53691	-3	1	1
53700	id		53691	-3	1	1
53701	mainContact		53688	-3	1	1
53702	mobilePhone		53691	-3	1	1
53703	keyword		53689	-3	1	1
53704	price		53692	-2	1	1
53705	quantity		53686	-1	1	1
53706	name		53688	-3	1	1
53707	city		53688	-3	1	1
53708	datePublished		53690	-4	1	1
53709	publisherID		53688	-3	1	1
53710	warehouse		53686	-3	1	1
53711	standardPrice		53690	-2	1	1
53712	streetAdderss		53688	-3	1	1
53713	lastName		53691	-3	1	1
53714	ISBN		53686	-3	1	1
53715	pages		53690	-1	1	1
53716	book		53687	-3	1	1
53717	state		53688	-3	1	1
53718	endDate		53692	-4	1	1
53719	officePhone		53691	-3	1	1
53720	genre		53690	-3	1	1
53721	zipcode		53688	-1	1	1
53722	author		53687	-6	1	1
53729	discount		53724	-2	1	1
53730	title		53727	-1	1	1
53731	category		53725	-1	1	1
53732	prodID		53728	-1	1	1
53733	isbn		53725	-1	1	1
53734	publisher		53727	-1	1	1
53735	warehouseid		53728	-1	1	1
53736	CustomerRating		53727	-1	1	1
53737	keyword		53726	-1	1	1
53738	SalesRating		53727	-1	1	1
53739	price		53724	-2	1	1
53740	stock		53728	-1	1	1
53741	author		53727	-1	1	1
53746	WarehouseName		53743	-3	1	1
53747	Genre		53744	-3	1	1
53748	ISBN		53744	-1	1	1
53749	Price		53744	-1	1	1
53750	ProductID		53743	-1	1	1
53751	InStock		53743	-3	1	1
53752	ProductType		53745	-3	1	1
53753	Author		53744	-3	1	1
53754	Title		53744	-3	1	1
53755	Quantity		53743	-1	1	1
53756	ExpectedInStock		53743	-4	1	1
53757	CustomerRating		53744	-1	1	1
53764	WarehouseName		53759	-1	1	1
53765	DiscountPrice		53761	-2	1	1
53766	Publisher		53763	-1	1	1
53767	BookISBN		53762	-1	1	1
53768	Keywords		53763	-1	1	1
53769	ProductNumber		53763	-1	1	1
53770	Year		53763	-1	1	1
53771	Edition		53763	-1	1	1
53772	Price		53763	-2	1	1
53773	ISBN		53763	-1	1	1
53774	AuthorName		53762	-1	1	1
53775	Number		53760	-1	1	1
53776	CopyWrite		53763	-1	1	1
53777	Category		53763	-1	1	1
53778	Title		53763	-1	1	1
53779	QuantityInStock		53759	-1	1	1
53786	discountPrice		53782	-2	1	1
53787	word		53784	-1	1	1
53788	ISBN		53784	-1	1	1
53789	type		53782	-1	1	1
53790	title		53782	-1	1	1
53791	productID		53781	-1	1	1
53792	price		53782	-2	1	1
53793	warehouseID		53781	-1	1	1
53794	quantity		53781	-1	1	1
53795	author		53785	-1	1	1
53799	Amount		53797	-1	1	1
53800	Description		53798	-3	1	1
53801	Publisher		53798	-3	1	1
53802	ItemType		53798	-3	1	1
53803	OurPrice		53798	-2	1	1
53804	Keywords		53798	-3	1	1
53805	Author		53798	-3	1	1
53806	WarehouseID		53797	-3	1	1
53807	Category		53798	-3	1	1
53808	ItemID		53797	-3	1	1
53809	Title		53798	-3	1	1
53810	SuggestedPrice		53798	-2	1	1
53818	houseID		53812	-1	1	1
53819	keyword		53815	-1	1	1
53820	priceType		53813	-1	1	1
53821	ISBN		53817	-1	1	1
53822	title		53817	-1	1	1
53823	productID		53813	-1	1	1
53824	price		53813	-2	1	1
53825	productCatagory		53815	-1	1	1
53826	genreType		53814	-1	1	1
53827	quantity		53812	-1	1	1
53828	name		53816	-1	1	1
53829	artistID		53816	-1	1	1
53836	Type		53832	-1	1	1
53837	DiscountPrice		53832	-2	1	1
53838	Publisher		53835	-1	1	1
53839	Keyword		53834	-1	1	1
53840	WHNum		53831	-1	1	1
53841	Price		53832	-2	1	1
53842	ISBN		53834	-1	1	1
53843	AuthorName		53833	-1	1	1
53844	Category		53835	-1	1	1
53845	Title		53835	-1	1	1
53846	Quantity		53831	-1	1	1
53847	ID		53831	-1	1	1
53848	NumPages		53835	-1	1	1
53854	whId		53850	-3	1	1
53855	startDate		53852	-4	1	1
53856	percentOff		53852	-2	1	1
53857	title		53853	-3	1	1
53858	productId		53851	-3	1	1
53859	category		53851	-3	1	1
53860	qty		53850	-1	1	1
53861	isbn		53853	-3	1	1
53862	publisher		53853	-3	1	1
53863	authors		53853	-3	1	1
53864	discountCode		53851	-3	1	1
53865	pubPrice		53853	-2	1	1
53866	year		53853	-1	1	1
53867	pages		53853	-1	1	1
53868	SKU		53850	-3	1	1
53869	description		53852	-3	1	1
53870	endDate		53852	-4	1	1
53877	itemNum		53874	-1	1	1
53878	authorArtist		53874	-1	1	1
53879	salePrice		53876	-2	1	1
53880	suggestion		53873	-1	1	1
53881	category		53876	-1	1	1
53882	publisher		53876	-1	1	1
53883	cover		53876	-1	1	1
53884	keyword		53875	-1	1	1
53885	price		53873	-2	1	1
53886	warehouseNum		53872	-1	1	1
53887	name		53873	-1	1	1
53888	quantity		53872	-1	1	1
53889	review		53873	-1	1	1
53893	ISBN		53891	-3	1	1
53894	Keywords		53892	-3	1	1
53895	Discount		53892	-1	1	1
53896	Author		53892	-3	1	1
53897	Category		53892	-3	1	1
53898	StoreNumber		53891	-1	1	1
53899	Title		53892	-3	1	1
53900	SalePrice		53892	-6	1	1
53901	Units		53891	-1	1	1
53905	DiscountedPrice		53904	-6	1	1
53906	ISBN		53904	-3	1	1
53907	SellingPrice		53904	-6	1	1
53908	BookTitle		53904	-3	1	1
53909	Keywords		53904	-3	1	1
53910	Author		53904	-3	1	1
53911	WarehouseID		53903	-1	1	1
53912	Category		53904	-3	1	1
53913	ItemNumber		53903	-3	1	1
53914	Quantity		53903	-1	1	1
53915	Rating		53904	-1	1	1
53924	ProductCategoryId		53922	-1	1	1
53925	DiscountId		53919	-1	1	1
53926	ISBN		53921	-1	1	1
53927	Price		53923	-2	1	1
53928	Description		53918	-3	1	1
53929	FirstName		53920	-1	1	1
53930	CategoryId		53921	-1	1	1
53931	AuthorId		53920	-1	1	1
53932	ProductId		53917	-1	1	1
53933	Category		53921	-1	1	1
53934	Title		53923	-1	1	1
53935	Expire		53919	-4	1	1
53936	Id		53917	-1	1	1
53937	Quantity		53917	-1	1	1
53938	WharehouseId		53917	-1	1	1
53939	LastName		53920	-1	1	1
53940	Rate		53919	-2	1	1
53945	width		53943	-2	1	1
53946	discount		53943	-2	1	1
53947	length		53943	-2	1	1
53948	wID		53944	-1	1	1
53949	prodID		53944	-1	1	1
53950	publisher		53943	-3	1	1
53951	date		53943	-4	1	1
53952	editorReview		53943	-3	1	1
53953	firstName		53942	-3	1	1
53954	cover		53943	-1	1	1
53955	lastName		53942	-3	1	1
53956	amount		53944	-1	1	1
53957	ISBN		53943	-1	1	1
53958	height		53943	-2	1	1
53959	pages		53943	-1	1	1
53960	price		53943	-2	1	1
53961	cutOff		53943	-2	1	1
53962	introduction		53943	-3	1	1
53963	name		53943	-3	1	1
53964	weight		53943	-2	1	1
53965	rate		53943	-1	1	1
53972	discountPrice		53968	-2	1	1
53973	keyword		53970	-1	1	1
53974	PID		53967	-1	1	1
53975	WH#		53967	-1	1	1
53976	title		53971	-1	1	1
53977	type		53968	-1	1	1
53978	price		53968	-2	1	1
53979	numPages		53971	-1	1	1
53980	publisher		53971	-1	1	1
53981	genre		53971	-1	1	1
53982	quantity		53967	-1	1	1
53983	name		53969	-1	1	1
53988	warehouse_id		53986	-1	1	1
53989	summary		53987	-3	1	1
53990	width		53987	-2	1	1
53991	type		53985	-6	1	1
53992	title		53987	-3	1	1
53993	depth		53987	-2	1	1
53994	category		53987	-3	1	1
53995	uid		53985	-1	1	1
53996	item_uid		53986	-1	1	1
53997	publisher		53987	-3	1	1
53998	published_date		53987	-4	1	1
53999	id		53986	-1	1	1
54000	ISBN		53987	-3	1	1
54001	height		53987	-2	1	1
54002	pages		53987	-1	1	1
54003	price		53985	-2	1	1
54004	quantity		53986	-1	1	1
54005	list_price		53987	-2	1	1
54006	weight		53987	-2	1	1
54007	author		53987	-3	1	1
54008	format		53987	-6	1	1
54017	keyword		54010	-1	1	1
54018	discounted		54013	-2	1	1
54019	ISBN		54014	-1	1	1
54020	title		54013	-1	1	1
54021	price		54013	-2	1	1
54022	authorID		54012	-1	1	1
54023	categoryName		54015	-1	1	1
54024	categoryID		54011	-1	1	1
54025	warehouseID		54014	-1	1	1
54026	stock		54014	-1	1	1
54027	author		54016	-1	1	1
54028	authorEmailAddress		54016	-1	1	1
54035	yearPublished		54034	-1	1	1
54036	title		54034	-1	1	1
54037	genreID		54034	-1	1	1
54038	firstName		54031	-1	1	1
54039	lastName		54031	-1	1	1
54040	discountPrice		54034	-2	1	1
54041	ISBN		54030	-1	1	1
54042	genreDescription		54033	-1	1	1
54043	price		54034	-2	1	1
54044	authorID		54030	-1	1	1
54045	warehouseID		54032	-1	1	1
54046	quantity		54032	-1	1	1
54047	genreName		54033	-1	1	1
54053	DiscountPrice		54052	-1	1	1
54054	Publisher		54052	-1	1	1
54055	Keywords		54052	-1	1	1
54056	WarehouseID		54049	-1	1	1
54057	ExpectedInStock		54049	-1	1	1
54058	CustomerRating		54052	-1	1	1
54059	Year		54052	-1	1	1
54060	Edition		54052	-1	1	1
54061	ISBN		54049	-1	1	1
54062	Price		54052	-1	1	1
54063	Binding		54052	-1	1	1
54064	FirstName		54050	-6	1	1
54065	MiddleInitial		54050	-6	1	1
54066	ShipTime		54049	-1	1	1
54067	AuthorID		54051	-6	1	1
54068	Category		54052	-1	1	1
54069	Title		54052	-1	1	1
54070	inStock		54049	-1	1	1
54071	Quantity		54049	-1	1	1
54072	LastName		54050	-6	1	1
54073	NumPages		54052	-1	1	1
54078	Type		54076	-3	1	1
54079	Publisher		54077	-3	1	1
54080	InStock		54075	-6	1	1
54081	Keyword		54077	-3	1	1
54082	LocsAvailable		54075	-1	1	1
54083	Author		54077	-3	1	1
54084	ItemID		54075	-1	1	1
54085	CustomerRating		54077	-2	1	1
54086	ISBN		54077	-3	1	1
54087	BookListPrice		54077	-2	1	1
54088	ShipTime		54075	-1	1	1
54089	Category		54077	-3	1	1
54090	Title		54077	-3	1	1
54091	BookSellPrice		54077	-2	1	1
54092	PubYear		54077	-1	1	1
54097	DiscountPrice		54096	-2	1	1
54098	Price		54096	-2	1	1
54099	ISBN		54094	-2	1	1
54100	FirstName		54095	-1	1	1
54101	Category		54096	-1	1	1
54102	Title		54096	-1	1	1
54103	Quantity		54094	-1	1	1
54104	WH_ID		54094	-1	1	1
54105	MiddleName		54095	-1	1	1
54106	LastName		54095	-1	1	1
54110	WareHouseCode		54108	-1	1	1
54111	Stock		54108	-1	1	1
54112	Price		54109	-1	1	1
54113	ISBN		54109	-1	1	1
54114	AuthorName		54109	-1	1	1
54115	KeyWord		54109	-1	1	1
54116	IsCD		54108	-1	1	1
54117	ISBook		54108	-1	1	1
54118	Title		54109	-1	1	1
54119	DisPrice		54109	-1	1	1
54120	Quantity		54108	-1	1	1
54121	BookCategory		54109	-1	1	1
54129	Status		54123	-6	1	1
54130	Type		54125	-1	1	1
54131	BookID		54128	-1	1	1
54132	Genre		54124	-1	1	1
54133	Publisher		54128	-1	1	1
54134	Cost		54126	-1	1	1
54135	WareID		54123	-1	1	1
54136	ItemID		54124	-1	1	1
54137	CreaterID		54125	-1	1	1
54138	Review		54126	-1	1	1
54139	ISBN		54128	-1	1	1
54140	Binding		54128	-1	1	1
54141	Pages		54128	-1	1	1
54142	AgeLevel		54128	-1	1	1
54143	SaleRank		54126	-1	1	1
54144	Name		54127	-1	1	1
54147	Model	Model	54146	-6	1	1
54148	Email	Email (e.g. mark@aol.com)	54146	-6	1	1
54149	ZipCode	ZipCode	54146	-6	1	1
54150	Make	Make	54146	-6	1	1
54153	PriceRangeFrom	Price Range	54152	-6	1	1
54154	Model	Model	54152	-6	1	1
54155	Email	Email	54152	-6	1	1
54156	Color	Color	54152	-6	1	1
54157	PriceRangeTo	to	54152	-6	1	1
54158	ZipCode	ZipCode	54152	-6	1	1
54159	Make	Make	54152	-6	1	1
54160	YearMin	Year	54152	-6	1	1
54161	YearMax		54152	-6	1	1
54162	Mileage	Mileage	54152	-6	1	1
54165	Model	Model (optional)	54164	-6	1	1
54166	Sort	sort results by	54164	-6	1	1
54167	Keyword	Keyword (optional)	54164	-6	1	1
54168	MaxYear	to	54164	-6	1	1
54169	Region	Region	54164	-6	1	1
54170	MAKE	Make	54164	-6	1	1
54171	MaxPrice	Max. Price (optional)	54164	-6	1	1
54172	MinYear	Year (optional)	54164	-6	1	1
54175	comments	comments	54174	-6	1	1
54176	transmission	Transmission	54174	-6	1	1
54177	bodyStyle	choose a model OR a style	54174	-6	1	1
54178	contact.firstName	First Name	54174	-6	1	1
54179	make	Make	54174	-6	1	1
54180	contact.phone	Phone	54174	-6	1	1
54181	year	Year	54174	-6	1	1
54182	contact.email	E-mail	54174	-6	1	1
54183	price	Price	54174	-6	1	1
54184	prefix		54174	-6	1	1
54185	mileage	mileage, at or under	54174	-6	1	1
54186	contact.lastName	Last Name	54174	-6	1	1
54187	model	model or Style	54174	-6	1	1
54188	duration	eNotification Time Search	54174	-6	1	1
54191	PS	Power Steering	54190	-6	1	1
54192	ODOMETER	Mileage	54190	-6	1	1
54193	ABS	Antilock Brakes	54190	-6	1	1
54194	LEATHER	Leather Seats	54190	-6	1	1
54195	CD	CD Player	54190	-6	1	1
54196	ROOF	Special Roof	54190	-6	1	1
54197	PW	Power Windows	54190	-6	1	1
54198	RESTRAINT	Air Bag(s)	54190	-6	1	1
54199	CLASSIFICATION	-OR- Select one of these categorie	54190	-6	1	1
54200	FUELTYPE	Fuel Type	54190	-6	1	1
54201	DATE_FROM	4-Digit Year Range	54190	-6	1	1
54202	PL	Power Locks	54190	-6	1	1
54203	CELLPHONE	Cellular Phone	54190	-6	1	1
54204	PB	Power Brakes	54190	-6	1	1
54205	CRUISE	Cruise Control	54190	-6	1	1
54206	E_SEATS	Electronic Seats	54190	-6	1	1
54207	PRICE	Price	54190	-6	1	1
54208	CASSETTE	Cassette	54190	-6	1	1
54209	DATE_TO	to	54190	-6	1	1
54210	AC	Air Conditioning	54190	-6	1	1
54211	optSelltype		54190	-6	1	1
54212	BODYSTYLE	Body Style	54190	-6	1	1
54213	MAKEMODEL	Select up to five makes models OR one category	54190	-6	1	1
54214	CYLINDERS	Cylinders	54190	-6	1	1
54217	numCarsOnOnePage	Vehicles per page	54216	-6	1	1
54218	model_vch		54216	-6	1	1
54219	make_vch	Select Vehicle	54216	-6	1	1
54220	Postal_Code_vch	Zip Code	54216	-6	1	1
54221	Email_Addr_vch	E-mail	54216	-6	1	1
54222	search_mileage_int	search within	54216	-6	1	1
54223	Entered_Postal_Code_vch	miles of	54216	-6	1	1
54224	High_Price	to	54216	-6	1	1
54225	Low_Price	Price Range	54216	-6	1	1
54228	Makes	elect up to five makes to show models desired	54227	-6	1	1
54229	dealer_makes	Search by manufacturer	54227	-6	1	1
54230	PRICE	Select Price Range	54227	-6	1	1
54231	dealer	dealer	54227	-6	1	1
54232	optSelltype		54227	-6	1	1
54233	rgn_id	Search by area	54227	-6	1	1
54234	makemodel	then select up to five models	54227	-6	1	1
54235	SortOrder	Sort vehicles by...	54227	-6	1	1
54238	Name_First	First Name	54237	-6	1	1
54239	Interior	Select Color (Interior)	54237	-6	1	1
54240	DayPhone_1	Day Phone	54237	-6	1	1
54241	Notes_to_dealer	Additional Comments (optional)	54237	-6	1	1
54242	EvePhone_Ext	Ext	54237	-6	1	1
54243	make	Make	54237	-6	1	1
54244	Zip	Your Zip	54237	-6	1	1
54245	EvePhone_1	Evening Phone	54237	-6	1	1
54246	DayPhone_2		54237	-6	1	1
54247	finance	Payment Method	54237	-6	1	1
54248	Zipcode	Zip	54237	-6	1	1
54249	Best_Time_To_Contact	Best Time To Contact	54237	-6	1	1
54250	EvePhone_3		54237	-6	1	1
54251	Name_Last	Last Name	54237	-6	1	1
54252	Exterior	Select Color (Exterior)	54237	-6	1	1
54253	DayPhone_3		54237	-6	1	1
54254	year	Year	54237	-6	1	1
54255	modelname	Model	54237	-6	1	1
54256	Address	Street Address (No city or state required)	54237	-6	1	1
54257	time_frame	Your timeframe	54237	-6	1	1
54258	EvePhone_2		54237	-6	1	1
54259	E-mail	Email	54237	-6	1	1
54260	package	Select Package	54237	-6	1	1
54261	DayPhone_Ext	Ext	54237	-6	1	1
54264	SearchRadius	Radius miles	54263	-6	1	1
54265	SearchMake	Make	54263	-6	1	1
54266	SearchZip	ZipCode	54263	-6	1	1
54267	SearchDMAId	Region	54263	-6	1	1
54268	SearchModel	Model	54263	-6	1	1
54269	SearchBodyType	Body Style	54263	-6	1	1
54272	HowHeard	How did you hear about us	54271	-6	1	1
54273	VehicleType	Vehicle type	54271	-6	1	1
54274	Pricetop	Price Limit	54271	-6	1	1
54275	Color	Color Preference	54271	-6	1	1
54276	Telephone	Telephone (recommended)include Area Code	54271	-6	1	1
54277	Options	items (check preferred)	54271	-6	1	1
54278	Model	Model(s)	54271	-6	1	1
54279	Year	Made In Or After	54271	-6	1	1
54280	Contact	Mode of Contact	54271	-6	1	1
54281	SecondModel	2nd choice Model(s)	54271	-6	1	1
54282	Address	Address	54271	-6	1	1
54283	Make	Make	54271	-6	1	1
54284	email	E-mail Address (Required)	54271	-6	1	1
54285	Purchase	Approximate Purchase Date	54271	-6	1	1
54286	Name	Name (required)	54271	-6	1	1
54287	SecondMake	2nd choice Make	54271	-6	1	1
54288	City-State-Zip	City-State-Zip	54271	-6	1	1
54291	PostalCode		54290	-6	1	1
54292	SearchRadius		54290	-6	1	1
54293	Doors		54290	-6	1	1
54294	PrintNumber	Ad ID	54290	-6	1	1
54295	YearMin	Year	54290	-6	1	1
54296	VehicleTypeID	Vehicle	54290	-6	1	1
54297	ProvinceID	Province	54290	-6	1	1
54298	ForSaleBy		54290	-6	1	1
54299	Price		54290	-6	1	1
54300	TransmissionID	Transmission	54290	-6	1	1
54301	KMs		54290	-6	1	1
54302	MakeID	Make	54290	-6	1	1
54303	YearMax	to	54290	-6	1	1
54306	lstPriceMax	to	54305	-6	1	1
54307	lstCategory	Category	54305	-6	1	1
54308	lstMake	Make	54305	-6	1	1
54309	lstRegion	Region	54305	-6	1	1
54310	lstPriceMin	Price Range (between)	54305	-6	1	1
54311	lstModels	Model	54305	-6	1	1
54314	numCarsOnOnePage	Vehicles per page	54313	-6	1	1
54315	vehicle	Select Vehicle	54313	-6	1	1
54316	search_mileage_int	search within	54313	-6	1	1
54317	Entered_Postal_Code_vch	miles of	54313	-6	1	1
54318	High_Price	to	54313	-6	1	1
54319	Low_Price	Price Range	54313	-6	1	1
54322	PrivatePool		54321	-6	1	1
54323	FirePlace		54321	-6	1	1
54324	Parking	Make	54321	-6	1	1
54325	Stories	Exterior Color	54321	-6	1	1
54326	GatedEntry		54321	-6	1	1
54327	State	Select Your State	54321	-6	1	1
54328	PropertyType	Category	54321	-6	1	1
54329	ZipCode	Zip Code	54321	-6	1	1
54330	MaxPrice	MAXimum Price	54321	-6	1	1
54331	Bedrooms	Model	54321	-6	1	1
54332	County	County(s)	54321	-6	1	1
54333	YearBuilt	Oldest Year Desired	54321	-6	1	1
54334	OfferedBy	Sale Type	54321	-6	1	1
54335	MinPrice	MINimum Price	54321	-6	1	1
54336	EntryLevel	Transmission	54321	-6	1	1
54337	CommPool		54321	-6	1	1
54338	SqFootage	Engine	54321	-6	1	1
54339	MaxMiles	MAXimum Desired Mileage	54321	-6	1	1
54340	ListingType	Listing Type	54321	-6	1	1
54341	Basement		54321	-6	1	1
54344	d_vehicle_used_flag		54343	-6	1	1
54345	d_minPrice	Price Range	54343	-6	1	1
54346	x_4by4flag		54343	-6	1	1
54347	d_vehicle_new_flag		54343	-6	1	1
54348	d_Category	Vehicle Body Style	54343	-6	1	1
54349	x_number_of_rows	Vehicles per page	54343	-6	1	1
54350	d_maxPrice	to	54343	-6	1	1
54351	D_radius	Distance	54343	-6	1	1
54352	d_Make	Vehicle Make	54343	-6	1	1
54353	d_maxYear	to	54343	-6	1	1
54354	D_zip_code	ZipCode	54343	-6	1	1
54355	d_minYear	Year Range	54343	-6	1	1
54356	d_maxMileage	Vehicle Mileage	54343	-6	1	1
54357	d_model_name	Vehicle Model	54343	-6	1	1
54360	exterior	Exterior Color?	54359	-6	1	1
54361	year_min	Year Range: From	54359	-6	1	1
54362	bodystyle		54359	-6	1	1
54363	radius	Distance	54359	-6	1	1
54364	vehicle_type		54359	-6	1	1
54365	price_max	to	54359	-6	1	1
54366	price_min	Price Range: From	54359	-6	1	1
54367	mileage	Car Mileage	54359	-6	1	1
54368	make	Make	54359	-6	1	1
54369	model	Model	54359	-6	1	1
54370	year_max	to	54359	-6	1	1
54371	zipcode	from Zip Code	54359	-6	1	1
54374	keywordSearchModifier		54373	-6	1	1
54375	Generic_dt_lvl_01	Find active listings placed within	54373	-6	1	1
54376	makeSelections	Make	54373	-6	1	1
54377	yearSelections		54373	-6	1	1
54378	zc	miles of ZIP Code	54373	-6	1	1
54379	userKeywords	itemal Search Words	54373	-6	1	1
54380	Generic_dcml_lvl_03	Price Range	54373	-6	1	1
54381	modelSelections		54373	-6	1	1
54382	Generic_intgr_lvl_10	Mileage Range	54373	-6	1	1
54383	pageIncrement	Display, listings per page	54373	-6	1	1
54384	searchType	How do you want to search?	54373	-6	1	1
54385	rd	Search within	54373	-6	1	1
54386	citylist		54373	-6	1	1
54389	comments	Comments/Notes:(optional)	54388	-6	1	1
54390	phone2num		54388	-6	1	1
54391	phoneprefix		54388	-6	1	1
54392	phoneext	x	54388	-6	1	1
54393	address	Address	54388	-6	1	1
54394	phone2area	Secondary Phone	54388	-6	1	1
54395	colorext	Colors Exterior	54388	-6	1	1
54396	zip	Zip	54388	-6	1	1
54397	namelast	Last	54388	-6	1	1
54398	city	City	54388	-6	1	1
54399	phone2prefix		54388	-6	1	1
54400	buytime	I plan to buy within	54388	-6	1	1
54401	namefirst	Buyer Information   Name: First	54388	-6	1	1
54402	make	Choose Make	54388	-6	1	1
54403	tradein	Trade-In	54388	-6	1	1
54404	phonearea	Primary Phone	54388	-6	1	1
54405	year1	Year	54388	-6	1	1
54406	payment	Payment Method	54388	-6	1	1
54407	year2	Choose Newest Year	54388	-6	1	1
54408	phone2ext	x	54388	-6	1	1
54409	model	Model	54388	-6	1	1
54410	state	State	54388	-6	1	1
54411	email	E-mail	54388	-6	1	1
54412	phonenum		54388	-6	1	1
54413	contacttime	Best time to contact	54388	-6	1	1
54414	colorint	Interior	54388	-6	1	1
54417	keywords	key words	54416	-6	1	1
54418	make	Make	54416	-6	1	1
54419	date	Date	54416	-6	1	1
54422	x_make_id	Make	54421	-6	1	1
54423	x_yearqual	Year is	54421	-6	1	1
54424	sort_order	Sort By	54421	-6	1	1
54425	x_model	Model	54421	-6	1	1
54426	start		54421	-6	1	1
54427	vpp	Listings Per Page	54421	-3	1	1
54428	keywords	Or, enter some keywords:    Tips for searching models	54421	-3	1	1
54429	body_type	Body Type	54421	-6	1	1
54430	x_price_range	Price Range	54421	-6	1	1
54431	x_year		54421	-6	1	1
54432	x_dealer_id		54421	-6	1	1
54435	distance	Distance:	54434	-6	1	1
54436	new_used	Choose Inventory Criteria       Inventory Type:     New and Used     New     Used	54434	-6	1	1
54437	newint		54434	-6	1	1
54438	instate	This State Only:	54434	-6	1	1
54439	newyear	Latest Year:      Max Price:	54434	-3	1	1
54440	vehicle	Select Vehicle Make and Model. Use your Control key to select up to 4 vehicles.	54434	-6	1	1
54441	int		54434	-6	1	1
54442	minprice		54434	-3	1	1
54443	zip		54434	-3	1	1
54444	oldyear	Earliest Year:      Min Price:	54434	-3	1	1
54445	session_id		54434	-6	1	1
54446	maxprice		54434	-3	1	1
54449	phone		54448	-3	1	1
54450	Interior_Color1_ch	Interior Color:	54448	-6	1	1
54451	eveningprefix		54448	-3	1	1
54452	vehicleyear	Vehicle Year:	54448	-3	1	1
54453	First_Name_ch	Personal Information:     First Name:	54448	-3	1	1
54454	Num_Cylinders_ti	Number  of Cylinders:	54448	-6	1	1
54455	Best_Contact_Time_ch	Best Time to Contact:	54448	-6	1	1
54456	vehiclemodel	Vehicle Model:	54448	-3	1	1
54457	Exterior_Color1_ch	Exterior Color:	54448	-6	1	1
54458	eveningarea	Evening Phone:  (	54448	-3	1	1
54459	vehiclemake	Vehicle Make:	54448	-6	1	1
54460	time_line_ch	Buying How Soon:	54448	-6	1	1
54461	tradeyear	If so, what year is your trade?	54448	-3	1	1
54462	prefix		54448	-3	1	1
54463	Purchase_Type_ch	Payment Method:	54448	-6	1	1
54464	Entered_Postal_Code_vch	Postal Code:	54448	-3	1	1
54465	Num_Doors_ti	Number of Doors:	54448	-6	1	1
54466	tradein	Do you have a trade-in?	54448	-6	1	1
54467	Country_ch	Country:	54448	-6	1	1
54468	Street_Address_1_ch	Street Address:	54448	-3	1	1
54469	eveningphone		54448	-3	1	1
54470	Last_Name_ch	Last Name:	54448	-3	1	1
54471	warranty	Are you interested in extended warranty information?	54448	-6	1	1
54472	State_ch	State:	54448	-6	1	1
54473	redirect		54448	-6	1	1
54474	Transmission_ch	Transmission:	54448	-6	1	1
54475	area	Daytime Phone:  (	54448	-3	1	1
54476	City_ch	City:	54448	-3	1	1
54477	Email_Addr_ch	Email:  Vehicle Information	54448	-3	1	1
54480	minPrice	Price Range Between $	54479	-3	1	1
54481	endyear	and  (yyyy)	54479	-3	1	1
54482	attr1		54479	-6	1	1
54483	categorymap		54479	-6	1	1
54484	attr2	Vehicle Model  tips	54479	-6	1	1
54485	exclude	Words to Exclude	54479	-3	1	1
54486	maxPrice	and	54479	-3	1	1
54487	from		54479	-6	1	1
54488	startyear	Vehicle Year Between	54479	-3	1	1
54489	attr0	Vehicle Make	54479	-6	1	1
54490	siteid		54479	-6	1	1
54491	query2	Search Title	54479	-3	1	1
54492	cgiurl		54479	-6	1	1
54493	category1		54479	-6	1	1
54494	ebaytag1code	Item Location	54479	-6	1	1
54495	s_partnerid		54479	-6	1	1
54496	MfcISAPICommand		54479	-6	1	1
54497	SortProperty	Sort By	54479	-6	1	1
54498	ebaytag1		54479	-6	1	1
54501	PARAME	MODEL	54500	-6	1	1
54502	PARAMK	YEAR	54500	-6	1	1
54503	PARAMD	MAKE	54500	-6	1	1
54504	PARAML	MILEAGE	54500	-6	1	1
54507	x_make_id	Make	54506	-6	1	1
54508	x_yearqual	Year is	54506	-6	1	1
54509	sort_order	Sort By	54506	-6	1	1
54510	x_model	Model	54506	-6	1	1
54511	vpp	Listings Per Page	54506	-6	1	1
54512	keywords	Or, enter some keywords	54506	-6	1	1
54513	body_type	Body Type	54506	-6	1	1
54514	x_price_range	Price Range	54506	-6	1	1
54515	x_year		54506	-6	1	1
54516	ON	Show vehicles with prices only	54506	-6	1	1
54519	PostalCode	Postal Code	54518	-6	1	1
54520	oCountry		54518	-6	1	1
54521	Model	Model	54518	-6	1	1
54522	minPrice	Price(US$)	54518	-6	1	1
54523	oStateOrProvince		54518	-6	1	1
54524	minYear	Year	54518	-6	1	1
54525	Make	Make	54518	-6	1	1
54526	Country	Country	54518	-6	1	1
54527	maxPrice	to	54518	-6	1	1
54528	City	City	54518	-6	1	1
54529	maxYear	to	54518	-6	1	1
54530	StateOrProvince	State/Province	54518	-6	1	1
54533	showImages		54532	-6	1	1
54534	minPrice	From	54532	-6	1	1
54535	miles	Miles	54532	-6	1	1
54536	minYear	YEAR RANGE From	54532	-6	1	1
54537	MAKE	Make	54532	-6	1	1
54538	resultsPerPage	results per page	54532	-6	1	1
54539	maxPrice	To	54532	-6	1	1
54540	model	MODEL/TYPE	54532	-6	1	1
54541	maxYear	To	54532	-6	1	1
54542	SEARCH	search	54532	-6	1	1
54545	d_maxYear	to	54544	-6	1	1
54546	d_minPrice	Price Range	54544	-6	1	1
54547	d_minYear	Year Range 4-digit years, please	54544	-6	1	1
54548	d_Category	Vehicle Body Style	54544	-6	1	1
54549	d_vehicle_new_flag		54544	-6	1	1
54550	x_number_of_rows	Vehicles per page	54544	-6	1	1
54551	d_Make_required	Vehicle Make	54544	-6	1	1
54552	d_maxMileage	Vehicle Mileage	54544	-6	1	1
54553	d_maxPrice	to	54544	-6	1	1
54554	d_model_name	Vehicle Model	54544	-6	1	1
54557	SearchRadius	SearchRadius	54556	-3	1	1
54558	Doors	Doors	54556	-3	1	1
54559	Cykinders	Cylinders	54556	-3	1	1
54560	ModeOfContact	ModeOfContact	54556	-3	1	1
54561	WhenPosted	WhenPosted	54556	-3	1	1
54562	Roof	Roof	54556	-3	1	1
54563	Keywords	Keywords	54556	-3	1	1
54564	PoweWindows	PowerWindows	54556	-3	1	1
54565	FirstName	FirstName	54556	-3	1	1
54566	HomePhone	HomePhone	54556	-3	1	1
54567	Transmission	Transmission	54556	-3	1	1
54568	TradeInYear	RadeInYear	54556	-3	1	1
54569	Dealer	Dealer	54556	-3	1	1
54570	Cassette	Cassette	54556	-3	1	1
54571	SearchBy	SearchBy	54556	-3	1	1
54572	BodyStyle	BodyStyle	54556	-3	1	1
54573	HowHeard	HowHeard	54556	-3	1	1
54574	Cruise	Cruise	54556	-3	1	1
54575	KeywordModifier	KeywordModifier	54556	-3	1	1
54576	Interior	Interior	54556	-3	1	1
54577	CDPlayer	CDPlayer	54556	-3	1	1
54578	BestTime	BestTime	54556	-3	1	1
54579	YearMin	YearMin	54556	-3	1	1
54580	NewUsed	NewUsed	54556	-3	1	1
54581	Model	Model	54556	-3	1	1
54582	SaleBy	SaleBy	54556	-3	1	1
54583	PriceMin	PriceMin	54556	-3	1	1
54584	YearMax	YearMax	54556	-3	1	1
54585	Drive	Drive	54556	-3	1	1
54586	CellPhone	CellPhone	54556	-3	1	1
54587	AirConditioning	AirConditioning	54556	-3	1	1
54588	Brakes	Brakes	54556	-3	1	1
54589	Color	Color	54556	-3	1	1
54590	AirBags	AirBags	54556	-3	1	1
54591	ZipCode	ZipCode	54556	-3	1	1
54592	Prefix	Prefix	54556	-3	1	1
54593	InStateOnly	InStateOnly	54556	-3	1	1
54594	MileageMax	MileageMax	54556	-3	1	1
54595	Make	Make	54556	-3	1	1
54596	PowerSteering	PowerSteering	54556	-3	1	1
54597	WorkPhone	WorkPhone	54556	-3	1	1
54598	StreetAddress	StreetAddress	54556	-3	1	1
54599	Region - State	State	54556	-3	1	1
54600	TimeFrame	TimeFrame	54556	-3	1	1
54601	Finance	Finance	54556	-3	1	1
54602	OtherOptions	OtherOptions	54556	-3	1	1
54603	YearQualifier	YearQualifier	54556	-3	1	1
54604	Leather	Leather	54556	-3	1	1
54605	Region - City	City	54556	-3	1	1
54606	SortOrder	SortOrder	54556	-3	1	1
54607	TradeIn	TradeIn	54556	-3	1	1
54608	Email	Email	54556	-3	1	1
54609	Fuel	Fuel	54556	-3	1	1
54610	PriceMax	PriceMax	54556	-3	1	1
54611	PowerLocks	PowerLocks	54556	-3	1	1
54612	Comments	Comments	54556	-3	1	1
54613	AdNumber	AdNumber	54556	-3	1	1
54614	ShowImage	ShowImage	54556	-3	1	1
54615	NumPerPage	NumPerPage	54556	-3	1	1
54616	LastName	LastName	54556	-3	1	1
54619	distance	Distance:	54618	-6	1	1
54620	new_used	Inventory Type:	54618	-6	1	1
54621	sort	Sort by:	54618	-6	1	1
54622	newint		54618	-6	1	1
54623	make		54618	-6	1	1
54624	newyear	to:	54618	-6	1	1
54625	int		54618	-6	1	1
54626	minprice	Price	54618	-6	1	1
54627	oldyear	Narrow Your Search Year:	54618	-6	1	1
54628	minmiles	Milage	54618	-6	1	1
54629	numresults	Organize Your Results      Results per page:	54618	-6	1	1
54630	instate	This State Only:	54618	-6	1	1
54631	page		54618	-6	1	1
54632	model		54618	-6	1	1
54633	zip	Zip Code:	54618	-3	1	1
54634	maxmiles	to	54618	-6	1	1
54635	maxprice	to	54618	-6	1	1
54638	vto	to	54637	-6	1	1
54639	vType	Type	54637	-6	1	1
54640	Make	Make	54637	-6	1	1
54641	vfrom	Year	54637	-6	1	1
54644	state	State	54643	-6	1	1
54645	maxrnt	Max Rent	54643	-3	1	1
54646	city	City	54643	-3	1	1
54649	szState	Location US State	54648	-6	1	1
54650	iCategory	Category	54648	-6	1	1
54651	szCountry	Location International	54648	-6	1	1
54654	TypeUse2	Type of Use	54653	-6	1	1
54655	strCityName	City	54653	-3	1	1
54656	intStateId	State	54653	-6	1	1
54657	Sqft	Size SF	54653	-3	1	1
54658	strZip	Zipcode	54653	-3	1	1
54659	TypeUse1	Price	54653	-3	1	1
54662	catb	Sq.Ft.	54661	-6	1	1
54663	beds	Rooms	54661	-6	1	1
54664	state	State/Province	54661	-6	1	1
54665	cata	Price	54661	-6	1	1
54666	catc	Property	54661	-6	1	1
54669	size_bot	Minimum acreage	54668	-6	1	1
54670	price_top	Maximum price	54668	-6	1	1
54671	size_top	Maximum acreage	54668	-6	1	1
54672	state	state	54668	-6	1	1
54673	zip	Zip	54668	-3	1	1
54676	country	Country	54675	-6	1	1
54677	priceRange2	Price Range to	54675	-3	1	1
54678	acreage2	Acreage to	54675	-3	1	1
54679	state	State	54675	-6	1	1
54680	acreage1	Acreage	54675	-3	1	1
54681	priceRange1	Price Range	54675	-3	1	1
54684	county	County	54683	-3	1	1
54685	footage2	Size to	54683	-6	1	1
54686	type	Property Type	54683	-6	1	1
54687	bathroom2	Bathrooms to	54683	-6	1	1
54688	bedroom2	Bedrooms to	54683	-6	1	1
54689	state	State	54683	-6	1	1
54690	footage1	Size	54683	-6	1	1
54691	price1	Price	54683	-6	1	1
54692	price2	Price to	54683	-6	1	1
54693	bathroom1	Bathrooms	54683	-6	1	1
54694	bedroom1	Bedrooms	54683	-6	1	1
54695	city	City/Cities	54683	-3	1	1
54698	PriceHigh	to	54697	-6	1	1
54699	PriceLow	Price from	54697	-6	1	1
54700	market	Area	54697	-6	1	1
54701	st	State	54697	-6	1	1
54704	list price	price range	54703	-6	1	1
54705	zip code	zip	54703	-3	1	1
54706	property type	property type	54703	-6	1	1
54707	state	state	54703	-6	1	1
54708	city name	city	54703	-3	1	1
54711	numBaths		54710	-3	1	1
54712	minPrice		54710	-3	1	1
54713	pCategory		54710	-3	1	1
54714	minLeaseRate		54710	-3	1	1
54715	maxPrice		54710	-3	1	1
54716	agentName		54710	-3	1	1
54717	maxAcerage		54710	-3	1	1
54718	agencyName		54710	-3	1	1
54719	maxYear		54710	-3	1	1
54720	county		54710	-3	1	1
54721	country		54710	-3	1	1
54722	squareFootage		54710	-3	1	1
54723	maxLeaseRate		54710	-3	1	1
54724	minAcerage		54710	-3	1	1
54725	minYear		54710	-3	1	1
54726	state		54710	-3	1	1
54727	zip		54710	-3	1	1
54728	numBeds		54710	-3	1	1
54729	pCharacteristic		54710	-3	1	1
54730	city		54710	-3	1	1
54733	min_price	Price Range	54732	-6	1	1
54734	max_year	Year 4-digits to	54732	-3	1	1
54735	bdrms	Minimum Number of Bedrooms	54732	-6	1	1
54736	baths	Minimum Number of Bathrooms	54732	-6	1	1
54737	max_price	Price Range to	54732	-6	1	1
54738	state	State	54732	-6	1	1
54739	city	City	54732	-6	1	1
54740	min_year	Year 4-digits from	54732	-3	1	1
54743	PriceHigh	Price Range To	54742	-6	1	1
54744	PriceLow	Price Range	54742	-6	1	1
54745	State	State	54742	-6	1	1
54748	BATHS	# of Bathrooms	54747	-6	1	1
54749	FOOTAGE	Square footage	54747	-3	1	1
54750	PRICE	Price range	54747	-3	1	1
54751	OFFICE_ID	Real Estate Agency	54747	-6	1	1
54752	AGENT_ID	Realtor	54747	-6	1	1
54753	BEDS	# of Bedrooms	54747	-6	1	1
54756	TOTL_BED	Features Bedrooms	54755	-6	1	1
54757	TOTL_FBATH	Features Bathrooms	54755	-6	1	1
54758	PropertyType	Property Type	54755	-6	1	1
54759	TOTL_FL_SQ	Features Minimum Floor Area Sq. Ft., Sq. M.	54755	-3	1	1
54760	Property Characteristics		54755	-6	1	1
54761	Price range	Price range	54755	-6	1	1
54762	LCountry	Country	54755	-6	1	1
54763	TOTL_LOT_SQ	Features Minimum Lot Size Acres, Hectares, Sq. M., Sq. Ft.	54755	-3	1	1
54766	Garage	Features Garage	54765	-6	1	1
54767	PropertyCity	City	54765	-3	1	1
54768	PropertyState	State	54765	-6	1	1
54769	PriceMax	Price Maximum	54765	-6	1	1
54770	PropertyCountry	Country	54765	-3	1	1
54771	PropertyType	Features Property Type	54765	-6	1	1
54772	PriceMin	Price Minimum	54765	-6	1	1
54773	Bedrooms	Features Bedrooms	54765	-6	1	1
54774	Baths	Features Baths	54765	-6	1	1
54777	selHouseType		54776	-6	1	1
54778	selBaths	Baths	54776	-6	1	1
54779	selState	State	54776	-6	1	1
54780	selBeds	Bedrooms	54776	-6	1	1
54781	selListingType	Price	54776	-6	1	1
54782	selMetro	City/Metro	54776	-6	1	1
54785	tScMaxUnits	Size/Price Unit range (Apartments only)	54784	-3	1	1
54786	tScMaxSfSale	Size/Price Size range SF	54784	-3	1	1
54787	tScMinSfSale	Size/Price Size range SF	54784	-3	1	1
54788	tScMinPriceSale	Size/Price Price range $	54784	-3	1	1
54789	OptionListSelectedTypes	Property Types	54784	-3	1	1
54790	tScMaxPriceSale	Size/Price Price range $	54784	-3	1	1
54791	tScMinUnits	Size/Price Unit range (Apartments only)	54784	-3	1	1
54794	Min_Lease_Price	Lease Price (Per Sq. Ft.) Minimum	54793	-3	1	1
54795	Min_sqf_avail	Property Size (Sq. Ft.) Minimum	54793	-3	1	1
54796	Property_Type	Property Type	54793	-6	1	1
54797	State	Property Location State	54793	-6	1	1
54798	Max_sqf_avail	Property Size (Sq. Ft.) Maximum	54793	-3	1	1
54799	St_Name	Property Location Street Name	54793	-3	1	1
54800	Zip_Code	Property Location Zip Code	54793	-3	1	1
54801	Country	Property Location Country	54793	-6	1	1
54802	City	Property Location City	54793	-3	1	1
54803	Max_Lease_Price	Lease Price (Per Sq. Ft.) Maximum	54793	-3	1	1
54806	cboLotSize	acreage	54805	-6	1	1
54807	cboLotTypeID	Parcel Type	54805	-6	1	1
54808	cboCountyID	County	54805	-6	1	1
54809	cboStateID	State or Country	54805	-6	1	1
54810	cboMaxPrice	Maximum price	54805	-6	1	1
54811	cboMinPrice	Minimum price	54805	-6	1	1
54814	min_price	Sale Price USD	54813	-3	1	1
54815	min_lease	Lease Rate per sq. ft.  $	54813	-3	1	1
54816	min_sqft	Square Feet sq. ft.	54813	-3	1	1
54817	zon_id	Zoning	54813	-6	1	1
54818	min_acre	Acreage acre(s)	54813	-3	1	1
54819	max_acre	Acreage acre(s) to	54813	-3	1	1
54820	max_lease	Lease Rate per sq. ft. to $	54813	-3	1	1
54821	max_price	Sale Price USD to	54813	-3	1	1
54822	max_sqft	Square Feet sq. ft. to	54813	-3	1	1
54823	pt_id	Property Types	54813	-6	1	1
54824	zip	City/St/Zip Zip	54813	-3	1	1
54825	pa_id	Property Availability	54813	-6	1	1
54826	city	City/St/Zip City	54813	-3	1	1
54827	st	City/St/Zip St	54813	-6	1	1
54830	ch_l4pri	Price Range to	54829	-6	1	1
54831	cl_bds	Beds	54829	-6	1	1
54832	cl_l4pri	Price Range	54829	-6	1	1
54833	cl_bth	Baths	54829	-6	1	1
54834	csz	City, State, or Zip	54829	-3	1	1
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
52365	Availability	
52366	Warehouses	
52367	Music	
52368	Books	
52397	Availability	
52398	Warehouses	
52399	CDCategories	
52400	Artist	
52401	CDs	
52402	Author	
52403	CDTypes	
52404	Books	
52405	BookCategories	
52436	Studios	
52437	BookInventory	
52438	Authors	
52439	Publishers	
52440	CDs	
52441	Keywords	
52442	CDInventory	
52443	Books	
52444	Warehouse	
52445	Person	
52446	Pricing	
52447	Performers	
52490	Availability	
52491	Products	
52492	Warehouses	
52493	Authors	
52494	Keywords	
52495	artists	
52496	Music	
52497	Books	
52520	WarehouseInformation	
52521	ProductAvailability	
52522	CDs	
52523	Books	
52524	Inventory	
52550	Availability	
52551	CDToArtist	
52552	ContactInfo	
52553	Product	
52554	DiscountedItem	
52555	CD	
52556	BookToAuthor	
52557	Warehouse	
52558	Book	
52586	Stock	
52587	Price	
52588	Genre	
52589	Product	
52590	WarehouseAttrib	
52591	Keywords	
52592	Artists	
52593	Books	
52594	Warehouse	
52595	Audio	
52622	Availability	
52623	Products	
52624	Warehouses	
52625	Music2Artists	
52626	Warehouses2Locations	
52627	Music	
52628	Books	
52629	Books2Keywords	
52630	Warehouses2Contacts	
52631	Books2Authors	
52653	Stock	
52654	Products	
52655	Warehouses	
52656	AuthorsArtists	
52657	Keywords	
52658	CDs	
52659	Books	
52684	Availability	
52685	BooksAndMusic	
52686	Warehouse	
52711	CD	
52712	WareHouse2Book	
52713	Artist	
52714	Author	
52715	WareHouse2CD	
52716	Book	
52717	WareHouse	
52740	warehouse	
52741	artist2music	
52742	bookCategory	
52743	artist	
52744	music2genre	
52745	book2Category	
52746	keyword	
52747	musicType	
52748	musicStock	
52749	book	
52750	book2Author	
52751	music	
52752	bookStock	
52753	genre	
52754	author	
52789	Availability	
52790	Items	
52791	Warehouses	
52792	Authors	
52793	Keywords	
52794	Artists	
52795	Books	
52796	Music	
52823	cds	
52824	warehouse	
52825	info	
52826	available	
52827	books	
52828	location	
52858	Availability	
52859	CD	
52860	Information	
52861	Books	
52862	Location	
52863	WareHouse	
52891	Warehouses	
52892	BooksAuthors	
52893	Authors	
52894	MusicGenres	
52895	BooksWarehouses	
52896	BookGenres	
52897	Artists	
52898	Music	
52899	Books	
52900	CDTypes	
52901	MusicWarehouses	
52902	MusicArtists	
52930	Stock	
52931	Products	
52932	Warehouses	
52933	Discounts	
52934	CDs	
52935	CDTracks	
52936	Books	
52969	Warehouses	
52970	CompactDiscs	
52971	BookAvailability	
52972	Books	
52973	CdAvailability	
52995	Availability	
52996	Warehouses	
52997	Music	
52998	Books	
53027	MusicsTypes	
53028	Product	
53029	BooksCategories	
53030	BooksKeywords	
53031	Musics	
53032	Books	
53033	WarehousesInfo	
53034	MusicsGenres	
53035	Inventory	
53065	Images	
53066	Products	
53067	Discounts	
53068	Reviews	
53069	Authors	
53070	Artists	
53071	Types	
53072	Sounds	
53073	Music	
53074	Wharehouses	
53075	Managers	
53076	Availability	
53077	Groups	
53078	Employees	
53079	Categories	
53080	Genres	
53081	ProductCategories	
53082	Users	
53083	RecordLabels	
53084	Books	
53085	Videos	
53086	Addresses	
53141	Review	
53142	Reviewer	
53143	Author	
53144	Music	
53145	Warehouse	
53146	Book	
53147	Storage	
53178	Review	
53179	InventoryOrders	
53180	Item	
53181	CD	
53182	CD_Track	
53183	Inventory	
53184	Warehouse	
53185	Book	
53236	Availability	
53237	Items	
53238	Warehouses	
53239	Music	
53240	Books	
53274	Availability	
53275	Genre	
53276	CreaterShip	
53277	Product	
53278	Creater	
53279	Association	
53280	Music	
53281	Book	
53282	WareHouse	
53311	Products	
53312	Discounts	
53313	Authors	
53314	Orders	
53315	CDs	
53316	Classifications	
53317	Customers	
53318	OrderItems	
53319	Inventory	
53320	Warehouses	
53321	Producers	
53322	Genres	
53323	Books	
53324	Addresses	
53357	ShippingDeals	
53358	ProductReviews	
53359	Product	
53360	WarehouseInfo	
53361	Music	
53362	Books	
53363	Inventory	
53391	Book_title	
53392	reviewer	
53393	category	
53394	Keywords	
53395	Album_edition	
53396	Warehouse	
53397	artist_item	
53398	Track	
53399	Album	
53400	Review	
53401	book_edition	
53402	author_artist	
53403	Location	
53404	Item_location	
53473	Availability	
53474	Type	
53475	Keyword	
53476	CD	
53477	Artist	
53478	Author	
53479	Warehouse	
53480	Book	
53510	Availability	
53511	Warehouses	
53512	Music	
53513	Books	
53542	Availability	
53543	Contacts	
53544	CDs	
53545	Books	
53546	Warehouse	
53576	Discounts	
53577	Reviews	
53578	Employee	
53579	CD	
53580	Availability	
53581	Warehouses	
53582	BookClassification	
53583	CDClassification	
53584	Categories	
53585	CreditCard	
53586	Users	
53587	Books	
53588	Addresses	
53637	Availability	
53638	Books	
53651	available	
53652	books	
53666	Availability	
53667	Author	
53668	Books	
53669	BookCategories	
53686	BookInventory	
53687	Authors	
53688	Publishers	
53689	Keywords	
53690	Books	
53691	Person	
53692	Pricing	
53724	Product	
53725	BooksCategories	
53726	BooksKeywords	
53727	Books	
53728	Inventory	
53743	ProductAvailability	
53744	Books	
53745	Inventory	
53759	Availability	
53760	Product	
53761	DiscountedItem	
53762	BookToAuthor	
53763	Book	
53781	Availability	
53782	Products	
53783	Books	
53784	Books2Keywords	
53785	Books2Authors	
53797	Availability	
53798	BooksAndMusic	
53812	Stock	
53813	Price	
53814	Genre	
53815	Keywords	
53816	Artists	
53817	Books	
53831	Availability	
53832	Items	
53833	Authors	
53834	Keywords	
53835	Books	
53850	Stock	
53851	Products	
53852	Discounts	
53853	Books	
53872	Stock	
53873	Products	
53874	AuthorsArtists	
53875	Keywords	
53876	Books	
53891	BookAvailability	
53892	Books	
53903	Availability	
53904	Books	
53917	Availability	
53918	Products	
53919	Discounts	
53920	Authors	
53921	Categories	
53922	ProductCategories	
53923	Books	
53942	Author	
53943	Book	
53944	Storage	
53967	Availability	
53968	Products	
53969	Authors	
53970	Keywords	
53971	Books	
53985	Item	
53986	Inventory	
53987	Book	
54010	keyword	
54011	book2Category	
54012	book2Author	
54013	book	
54014	bookStock	
54015	bookCategory	
54016	author	
54030	BooksAuthors	
54031	Authors	
54032	BooksWarehouses	
54033	BookGenres	
54034	Books	
54049	Availability	
54050	Author	
54051	BookToAuthor	
54052	Books	
54075	Availability	
54076	Items	
54077	Books	
54094	WareHouse2Book	
54095	Author	
54096	Book	
54108	Availability	
54109	Books	
54123	Availability	
54124	Genre	
54125	CreaterShip	
54126	Product	
54127	Creater	
54128	Book	
54146	1stopauto_new	
54152	1stopauto_used	
54164	401carfinder	
54174	AtkinsKrollIncofGuam	
54190	amarilloAutos	
54216	autobytel	
54227	amarilloautochooser	
54237	AutoMob	
54263	AutoNation	
54271	Autonet	
54290	Autonetca	
54305	AutoPoint	
54313	AutoWeb	
54321	buycars	
54343	carcast	
54359	carprices	
54373	cars	
54388	carsearch	
54416	AsktheManufacturerSearch	
54421	colvinauto	
54434	dealernet	
54448	discountautopricing	
54479	ebaymotors	
54500	BondesenChevrolet_Cadillac	
54506	GetAuto	
54518	GotCars4sale	
54532	HerzogMeierAutoCenter	
54544	HoakMotorsInc	
54556	mediated	
54618	nada	
54637	Tourist	
54643	Apartments	
54648	biztrader	
54653	cityfeet	
54661	CommercialRealEstate	
54668	eLandUSA	
54675	EquestrianLIVING	
54683	eSearchHomes-1	
54697	HomeBuilders	
54703	homeseekers	
54710	mediated	
54732	NationalMobile	
54742	new-home-builders	
54747	NorthIdahoHomeseekers	
54755	PlanetProperties	
54765	Property2000-1	
54776	RealEstate	
54784	RealtyInvestor-1	
54793	SpaceForLease	
54805	USLots	
54813	webrealestate	
54829	YahooRealEstate	
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
52364	Add	52375
52364	Add	52376
52364	Add	52377
52364	Add	52378
52364	Add	52379
52364	Add	52380
52364	Add	52381
52364	Add	52382
52364	Add	52383
52364	Add	52384
52364	Add	52385
52364	Add	52386
52364	Add	52387
52364	Add	52388
52364	Add	52389
52364	Add	52390
52364	Add	52391
52364	Add	52392
52364	Add	52393
52364	Add	52394
52364	Add	52395
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
52396	Add	52414
52396	Add	52415
52396	Add	52416
52396	Add	52417
52396	Add	52418
52396	Add	52419
52396	Add	52420
52396	Add	52421
52396	Add	52422
52396	Add	52423
52396	Add	52424
52396	Add	52425
52396	Add	52426
52396	Add	52427
52396	Add	52428
52396	Add	52429
52396	Add	52430
52396	Add	52431
52396	Add	52432
52396	Add	52433
52396	Add	52434
52435	Add	52436
52435	Add	52437
52435	Add	52438
52435	Add	52439
52435	Add	52440
52435	Add	52441
52435	Add	52442
52435	Add	52443
52435	Add	52444
52435	Add	52445
52435	Add	52446
52435	Add	52447
52435	Add	52448
52435	Add	52449
52435	Add	52450
52435	Add	52451
52435	Add	52452
52435	Add	52453
52435	Add	52454
52435	Add	52455
52435	Add	52456
52435	Add	52457
52435	Add	52458
52435	Add	52459
52435	Add	52460
52435	Add	52461
52435	Add	52462
52435	Add	52463
52435	Add	52464
52435	Add	52465
52435	Add	52466
52435	Add	52467
52435	Add	52468
52435	Add	52469
52435	Add	52470
52435	Add	52471
52435	Add	52472
52435	Add	52473
52435	Add	52474
52435	Add	52475
52435	Add	52476
52435	Add	52477
52435	Add	52478
52435	Add	52479
52435	Add	52480
52435	Add	52481
52435	Add	52482
52435	Add	52483
52435	Add	52484
52435	Add	52485
52435	Add	52486
52435	Add	52487
52435	Add	52488
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
52489	Add	52510
52489	Add	52511
52489	Add	52512
52489	Add	52513
52489	Add	52514
52489	Add	52515
52489	Add	52516
52489	Add	52517
52489	Add	52518
52519	Add	52520
52519	Add	52521
52519	Add	52522
52519	Add	52523
52519	Add	52524
52519	Add	52525
52519	Add	52526
52519	Add	52527
52519	Add	52528
52519	Add	52529
52519	Add	52530
52519	Add	52531
52519	Add	52532
52519	Add	52533
52519	Add	52534
52519	Add	52535
52519	Add	52536
52519	Add	52537
52519	Add	52538
52519	Add	52539
52519	Add	52540
52519	Add	52541
52519	Add	52542
52519	Add	52543
52519	Add	52544
52519	Add	52545
52519	Add	52546
52519	Add	52547
52519	Add	52548
52549	Add	52550
52549	Add	52551
52549	Add	52552
52549	Add	52553
52549	Add	52554
52549	Add	52555
52549	Add	52556
52549	Add	52557
52549	Add	52558
52549	Add	52559
52549	Add	52560
52549	Add	52561
52549	Add	52562
52549	Add	52563
52549	Add	52564
52549	Add	52565
52549	Add	52566
52549	Add	52567
52549	Add	52568
52549	Add	52569
52549	Add	52570
52549	Add	52571
52549	Add	52572
52549	Add	52573
52549	Add	52574
52549	Add	52575
52549	Add	52576
52549	Add	52577
52549	Add	52578
52549	Add	52579
52549	Add	52580
52549	Add	52581
52549	Add	52582
52549	Add	52583
52549	Add	52584
52585	Add	52586
52585	Add	52587
52585	Add	52588
52585	Add	52589
52585	Add	52590
52585	Add	52591
52585	Add	52592
52585	Add	52593
52585	Add	52594
52585	Add	52595
52585	Add	52596
52585	Add	52597
52585	Add	52598
52585	Add	52599
52585	Add	52600
52585	Add	52601
52585	Add	52602
52585	Add	52603
52585	Add	52604
52585	Add	52605
52585	Add	52606
52585	Add	52607
52585	Add	52608
52585	Add	52609
52585	Add	52610
52585	Add	52611
52585	Add	52612
52585	Add	52613
52585	Add	52614
52585	Add	52615
52585	Add	52616
52585	Add	52617
52585	Add	52618
52585	Add	52619
52585	Add	52620
52621	Add	52622
52621	Add	52623
52621	Add	52624
52621	Add	52625
52621	Add	52626
52621	Add	52627
52621	Add	52628
52621	Add	52629
52621	Add	52630
52621	Add	52631
52621	Add	52632
52621	Add	52633
52621	Add	52634
52621	Add	52635
52621	Add	52636
52621	Add	52637
52621	Add	52638
52621	Add	52639
52621	Add	52640
52621	Add	52641
52621	Add	52642
52621	Add	52643
52621	Add	52644
52621	Add	52645
52621	Add	52646
52621	Add	52647
52621	Add	52648
52621	Add	52649
52621	Add	52650
52621	Add	52651
52652	Add	52653
52652	Add	52654
52652	Add	52655
52652	Add	52656
52652	Add	52657
52652	Add	52658
52652	Add	52659
52652	Add	52660
52652	Add	52661
52652	Add	52662
52652	Add	52663
52652	Add	52664
52652	Add	52665
52652	Add	52666
52652	Add	52667
52652	Add	52668
52652	Add	52669
52652	Add	52670
52652	Add	52671
52652	Add	52672
52652	Add	52673
52652	Add	52674
52652	Add	52675
52652	Add	52676
52652	Add	52677
52652	Add	52678
52652	Add	52679
52652	Add	52680
52652	Add	52681
52652	Add	52682
52683	Add	52684
52683	Add	52685
52683	Add	52686
52683	Add	52687
52683	Add	52688
52683	Add	52689
52683	Add	52690
52683	Add	52691
52683	Add	52692
52683	Add	52693
52683	Add	52694
52683	Add	52695
52683	Add	52696
52683	Add	52697
52683	Add	52698
52683	Add	52699
52683	Add	52700
52683	Add	52701
52683	Add	52702
52683	Add	52703
52683	Add	52704
52683	Add	52705
52683	Add	52706
52683	Add	52707
52683	Add	52708
52683	Add	52709
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
52710	Add	52721
52710	Add	52722
52710	Add	52723
52710	Add	52724
52710	Add	52725
52710	Add	52726
52710	Add	52727
52710	Add	52728
52710	Add	52729
52710	Add	52730
52710	Add	52731
52710	Add	52732
52710	Add	52733
52710	Add	52734
52710	Add	52735
52710	Add	52736
52710	Add	52737
52710	Add	52738
52739	Add	52740
52739	Add	52741
52739	Add	52742
52739	Add	52743
52739	Add	52744
52739	Add	52745
52739	Add	52746
52739	Add	52747
52739	Add	52748
52739	Add	52749
52739	Add	52750
52739	Add	52751
52739	Add	52752
52739	Add	52753
52739	Add	52754
52739	Add	52755
52739	Add	52756
52739	Add	52757
52739	Add	52758
52739	Add	52759
52739	Add	52760
52739	Add	52761
52739	Add	52762
52739	Add	52763
52739	Add	52764
52739	Add	52765
52739	Add	52766
52739	Add	52767
52739	Add	52768
52739	Add	52769
52739	Add	52770
52739	Add	52771
52739	Add	52772
52739	Add	52773
52739	Add	52774
52739	Add	52775
52739	Add	52776
52739	Add	52777
52739	Add	52778
52739	Add	52779
52739	Add	52780
52739	Add	52781
52739	Add	52782
52739	Add	52783
52739	Add	52784
52739	Add	52785
52739	Add	52786
52739	Add	52787
52788	Add	52789
52788	Add	52790
52788	Add	52791
52788	Add	52792
52788	Add	52793
52788	Add	52794
52788	Add	52795
52788	Add	52796
52788	Add	52797
52788	Add	52798
52788	Add	52799
52788	Add	52800
52788	Add	52801
52788	Add	52802
52788	Add	52803
52788	Add	52804
52788	Add	52805
52788	Add	52806
52788	Add	52807
52788	Add	52808
52788	Add	52809
52788	Add	52810
52788	Add	52811
52788	Add	52812
52788	Add	52813
52788	Add	52814
52788	Add	52815
52788	Add	52816
52788	Add	52817
52788	Add	52818
52788	Add	52819
52788	Add	52820
52788	Add	52821
52822	Add	52823
52822	Add	52824
52822	Add	52825
52822	Add	52826
52822	Add	52827
52822	Add	52828
52822	Add	52829
52822	Add	52830
52822	Add	52831
52822	Add	52832
52822	Add	52833
52822	Add	52834
52822	Add	52835
52822	Add	52836
52822	Add	52837
52822	Add	52838
52822	Add	52839
52822	Add	52840
52822	Add	52841
52822	Add	52842
52822	Add	52843
52822	Add	52844
52822	Add	52845
52822	Add	52846
52822	Add	52847
52822	Add	52848
52822	Add	52849
52822	Add	52850
52822	Add	52851
52822	Add	52852
52822	Add	52853
52822	Add	52854
52822	Add	52855
52822	Add	52856
52857	Add	52858
52857	Add	52859
52857	Add	52860
52857	Add	52861
52857	Add	52862
52857	Add	52863
52857	Add	52864
52857	Add	52865
52857	Add	52866
52857	Add	52867
52857	Add	52868
52857	Add	52869
52857	Add	52870
52857	Add	52871
52857	Add	52872
52857	Add	52873
52857	Add	52874
52857	Add	52875
52857	Add	52876
52857	Add	52877
52857	Add	52878
52857	Add	52879
52857	Add	52880
52857	Add	52881
52857	Add	52882
52857	Add	52883
52857	Add	52884
52857	Add	52885
52857	Add	52886
52857	Add	52887
52857	Add	52888
52857	Add	52889
52890	Add	52891
52890	Add	52892
52890	Add	52893
52890	Add	52894
52890	Add	52895
52890	Add	52896
52890	Add	52897
52890	Add	52898
52890	Add	52899
52890	Add	52900
52890	Add	52901
52890	Add	52902
52890	Add	52903
52890	Add	52904
52890	Add	52905
52890	Add	52906
52890	Add	52907
52890	Add	52908
52890	Add	52909
52890	Add	52910
52890	Add	52911
52890	Add	52912
52890	Add	52913
52890	Add	52914
52890	Add	52915
52890	Add	52916
52890	Add	52917
52890	Add	52918
52890	Add	52919
52890	Add	52920
52890	Add	52921
52890	Add	52922
52890	Add	52923
52890	Add	52924
52890	Add	52925
52890	Add	52926
52890	Add	52927
52890	Add	52928
52929	Add	52930
52929	Add	52931
52929	Add	52932
52929	Add	52933
52929	Add	52934
52929	Add	52935
52929	Add	52936
52929	Add	52937
52929	Add	52938
52929	Add	52939
52929	Add	52940
52929	Add	52941
52929	Add	52942
52929	Add	52943
52929	Add	52944
52929	Add	52945
52929	Add	52946
52929	Add	52947
52929	Add	52948
52929	Add	52949
52929	Add	52950
52929	Add	52951
52929	Add	52952
52929	Add	52953
52929	Add	52954
52929	Add	52955
52929	Add	52956
52929	Add	52957
52929	Add	52958
52929	Add	52959
52929	Add	52960
52929	Add	52961
52929	Add	52962
52929	Add	52963
52929	Add	52964
52929	Add	52965
52929	Add	52966
52929	Add	52967
52968	Add	52969
52968	Add	52970
52968	Add	52971
52968	Add	52972
52968	Add	52973
52968	Add	52974
52968	Add	52975
52968	Add	52976
52968	Add	52977
52968	Add	52978
52968	Add	52979
52968	Add	52980
52968	Add	52981
52968	Add	52982
52968	Add	52983
52968	Add	52984
52968	Add	52985
52968	Add	52986
52968	Add	52987
52968	Add	52988
52968	Add	52989
52968	Add	52990
52968	Add	52991
52968	Add	52992
52968	Add	52993
52994	Add	52995
52994	Add	52996
52994	Add	52997
52994	Add	52998
52994	Add	52999
52994	Add	53000
52994	Add	53001
52994	Add	53002
52994	Add	53003
52994	Add	53004
52994	Add	53005
52994	Add	53006
52994	Add	53007
52994	Add	53008
52994	Add	53009
52994	Add	53010
52994	Add	53011
52994	Add	53012
52994	Add	53013
52994	Add	53014
52994	Add	53015
52994	Add	53016
52994	Add	53017
52994	Add	53018
52994	Add	53019
52994	Add	53020
52994	Add	53021
52994	Add	53022
52994	Add	53023
52994	Add	53024
52994	Add	53025
53026	Add	53027
53026	Add	53028
53026	Add	53029
53026	Add	53030
53026	Add	53031
53026	Add	53032
53026	Add	53033
53026	Add	53034
53026	Add	53035
53026	Add	53036
53026	Add	53037
53026	Add	53038
53026	Add	53039
53026	Add	53040
53026	Add	53041
53026	Add	53042
53026	Add	53043
53026	Add	53044
53026	Add	53045
53026	Add	53046
53026	Add	53047
53026	Add	53048
53026	Add	53049
53026	Add	53050
53026	Add	53051
53026	Add	53052
53026	Add	53053
53026	Add	53054
53026	Add	53055
53026	Add	53056
53026	Add	53057
53026	Add	53058
53026	Add	53059
53026	Add	53060
53026	Add	53061
53026	Add	53062
53026	Add	53063
53064	Add	53065
53064	Add	53066
53064	Add	53067
53064	Add	53068
53064	Add	53069
53064	Add	53070
53064	Add	53071
53064	Add	53072
53064	Add	53073
53064	Add	53074
53064	Add	53075
53064	Add	53076
53064	Add	53077
53064	Add	53078
53064	Add	53079
53064	Add	53080
53064	Add	53081
53064	Add	53082
53064	Add	53083
53064	Add	53084
53064	Add	53085
53064	Add	53086
53064	Add	53087
53064	Add	53088
53064	Add	53089
53064	Add	53090
53064	Add	53091
53064	Add	53092
53064	Add	53093
53064	Add	53094
53064	Add	53095
53064	Add	53096
53064	Add	53097
53064	Add	53098
53064	Add	53099
53064	Add	53100
53064	Add	53101
53064	Add	53102
53064	Add	53103
53064	Add	53104
53064	Add	53105
53064	Add	53106
53064	Add	53107
53064	Add	53108
53064	Add	53109
53064	Add	53110
53064	Add	53111
53064	Add	53112
53064	Add	53113
53064	Add	53114
53064	Add	53115
53064	Add	53116
53064	Add	53117
53064	Add	53118
53064	Add	53119
53064	Add	53120
53064	Add	53121
53064	Add	53122
53064	Add	53123
53064	Add	53124
53064	Add	53125
53064	Add	53126
53064	Add	53127
53064	Add	53128
53064	Add	53129
53064	Add	53130
53064	Add	53131
53064	Add	53132
53064	Add	53133
53064	Add	53134
53064	Add	53135
53064	Add	53136
53064	Add	53137
53064	Add	53138
53064	Add	53139
53140	Add	53141
53140	Add	53142
53140	Add	53143
53140	Add	53144
53140	Add	53145
53140	Add	53146
53140	Add	53147
53140	Add	53148
53140	Add	53149
53140	Add	53150
53140	Add	53151
53140	Add	53152
53140	Add	53153
53140	Add	53154
53140	Add	53155
53140	Add	53156
53140	Add	53157
53140	Add	53158
53140	Add	53159
53140	Add	53160
53140	Add	53161
53140	Add	53162
53140	Add	53163
53140	Add	53164
53140	Add	53165
53140	Add	53166
53140	Add	53167
53140	Add	53168
53140	Add	53169
53140	Add	53170
53140	Add	53171
53140	Add	53172
53140	Add	53173
53140	Add	53174
53140	Add	53175
53140	Add	53176
53177	Add	53178
53177	Add	53179
53177	Add	53180
53177	Add	53181
53177	Add	53182
53177	Add	53183
53177	Add	53184
53177	Add	53185
53177	Add	53186
53177	Add	53187
53177	Add	53188
53177	Add	53189
53177	Add	53190
53177	Add	53191
53177	Add	53192
53177	Add	53193
53177	Add	53194
53177	Add	53195
53177	Add	53196
53177	Add	53197
53177	Add	53198
53177	Add	53199
53177	Add	53200
53177	Add	53201
53177	Add	53202
53177	Add	53203
53177	Add	53204
53177	Add	53205
53177	Add	53206
53177	Add	53207
53177	Add	53208
53177	Add	53209
53177	Add	53210
53177	Add	53211
53177	Add	53212
53177	Add	53213
53177	Add	53214
53177	Add	53215
53177	Add	53216
53177	Add	53217
53177	Add	53218
53177	Add	53219
53177	Add	53220
53177	Add	53221
53177	Add	53222
53177	Add	53223
53177	Add	53224
53177	Add	53225
53177	Add	53226
53177	Add	53227
53177	Add	53228
53177	Add	53229
53177	Add	53230
53177	Add	53231
53177	Add	53232
53177	Add	53233
53177	Add	53234
53235	Add	53236
53235	Add	53237
53235	Add	53238
53235	Add	53239
53235	Add	53240
53235	Add	53241
53235	Add	53242
53235	Add	53243
53235	Add	53244
53235	Add	53245
53235	Add	53246
53235	Add	53247
53235	Add	53248
53235	Add	53249
53235	Add	53250
53235	Add	53251
53235	Add	53252
53235	Add	53253
53235	Add	53254
53235	Add	53255
53235	Add	53256
53235	Add	53257
53235	Add	53258
53235	Add	53259
53235	Add	53260
53235	Add	53261
53235	Add	53262
53235	Add	53263
53235	Add	53264
53235	Add	53265
53235	Add	53266
53235	Add	53267
53235	Add	53268
53235	Add	53269
53235	Add	53270
53235	Add	53271
53235	Add	53272
53273	Add	53274
53273	Add	53275
53273	Add	53276
53273	Add	53277
53273	Add	53278
53273	Add	53279
53273	Add	53280
53273	Add	53281
53273	Add	53282
53273	Add	53283
53273	Add	53284
53273	Add	53285
53273	Add	53286
53273	Add	53287
53273	Add	53288
53273	Add	53289
53273	Add	53290
53273	Add	53291
53273	Add	53292
53273	Add	53293
53273	Add	53294
53273	Add	53295
53273	Add	53296
53273	Add	53297
53273	Add	53298
53273	Add	53299
53273	Add	53300
53273	Add	53301
53273	Add	53302
53273	Add	53303
53273	Add	53304
53273	Add	53305
53273	Add	53306
53273	Add	53307
53273	Add	53308
53273	Add	53309
53310	Add	53311
53310	Add	53312
53310	Add	53313
53310	Add	53314
53310	Add	53315
53310	Add	53316
53310	Add	53317
53310	Add	53318
53310	Add	53319
53310	Add	53320
53310	Add	53321
53310	Add	53322
53310	Add	53323
53310	Add	53324
53310	Add	53325
53310	Add	53326
53310	Add	53327
53310	Add	53328
53310	Add	53329
53310	Add	53330
53310	Add	53331
53310	Add	53332
53310	Add	53333
53310	Add	53334
53310	Add	53335
53310	Add	53336
53310	Add	53337
53310	Add	53338
53310	Add	53339
53310	Add	53340
53310	Add	53341
53310	Add	53342
53310	Add	53343
53310	Add	53344
53310	Add	53345
53310	Add	53346
53310	Add	53347
53310	Add	53348
53310	Add	53349
53310	Add	53350
53310	Add	53351
53310	Add	53352
53310	Add	53353
53310	Add	53354
53310	Add	53355
53356	Add	53357
53356	Add	53358
53356	Add	53359
53356	Add	53360
53356	Add	53361
53356	Add	53362
53356	Add	53363
53356	Add	53364
53356	Add	53365
53356	Add	53366
53356	Add	53367
53356	Add	53368
53356	Add	53369
53356	Add	53370
53356	Add	53371
53356	Add	53372
53356	Add	53373
53356	Add	53374
53356	Add	53375
53356	Add	53376
53356	Add	53377
53356	Add	53378
53356	Add	53379
53356	Add	53380
53356	Add	53381
53356	Add	53382
53356	Add	53383
53356	Add	53384
53356	Add	53385
53356	Add	53386
53356	Add	53387
53356	Add	53388
53356	Add	53389
53390	Add	53391
53390	Add	53392
53390	Add	53393
53390	Add	53394
53390	Add	53395
53390	Add	53396
53390	Add	53397
53390	Add	53398
53390	Add	53399
53390	Add	53400
53390	Add	53401
53390	Add	53402
53390	Add	53403
53390	Add	53404
53390	Add	53405
53390	Add	53406
53390	Add	53407
53390	Add	53408
53390	Add	53409
53390	Add	53410
53390	Add	53411
53390	Add	53412
53390	Add	53413
53390	Add	53414
53390	Add	53415
53390	Add	53416
53390	Add	53417
53390	Add	53418
53390	Add	53419
53390	Add	53420
53390	Add	53421
53390	Add	53422
53390	Add	53423
53390	Add	53424
53390	Add	53425
53390	Add	53426
53390	Add	53427
53390	Add	53428
53390	Add	53429
53390	Add	53430
53390	Add	53431
53390	Add	53432
53390	Add	53433
53390	Add	53434
53390	Add	53435
53390	Add	53436
53390	Add	53437
53390	Add	53438
53390	Add	53439
53390	Add	53440
53390	Add	53441
53390	Add	53442
53390	Add	53443
53390	Add	53444
53390	Add	53445
53390	Add	53446
53390	Add	53447
53390	Add	53448
53390	Add	53449
53390	Add	53450
53390	Add	53451
53390	Add	53452
53390	Add	53453
53390	Add	53454
53390	Add	53455
53390	Add	53456
53390	Add	53457
53390	Add	53458
53390	Add	53459
53390	Add	53460
53390	Add	53461
53390	Add	53462
53390	Add	53463
53390	Add	53464
53390	Add	53465
53390	Add	53466
53390	Add	53467
53390	Add	53468
53390	Add	53469
53390	Add	53470
53390	Add	53471
53472	Add	53473
53472	Add	53474
53472	Add	53475
53472	Add	53476
53472	Add	53477
53472	Add	53478
53472	Add	53479
53472	Add	53480
53472	Add	53481
53472	Add	53482
53472	Add	53483
53472	Add	53484
53472	Add	53485
53472	Add	53486
53472	Add	53487
53472	Add	53488
53472	Add	53489
53472	Add	53490
53472	Add	53491
53472	Add	53492
53472	Add	53493
53472	Add	53494
53472	Add	53495
53472	Add	53496
53472	Add	53497
53472	Add	53498
53472	Add	53499
53472	Add	53500
53472	Add	53501
53472	Add	53502
53472	Add	53503
53472	Add	53504
53472	Add	53505
53472	Add	53506
53472	Add	53507
53472	Add	53508
53509	Add	53510
53509	Add	53511
53509	Add	53512
53509	Add	53513
53509	Add	53514
53509	Add	53515
53509	Add	53516
53509	Add	53517
53509	Add	53518
53509	Add	53519
53509	Add	53520
53509	Add	53521
53509	Add	53522
53509	Add	53523
53509	Add	53524
53509	Add	53525
53509	Add	53526
53509	Add	53527
53509	Add	53528
53509	Add	53529
53509	Add	53530
53509	Add	53531
53509	Add	53532
53509	Add	53533
53509	Add	53534
53509	Add	53535
53509	Add	53536
53509	Add	53537
53509	Add	53538
53509	Add	53539
53509	Add	53540
53541	Add	53542
53541	Add	53543
53541	Add	53544
53541	Add	53545
53541	Add	53546
53541	Add	53547
53541	Add	53548
53541	Add	53549
53541	Add	53550
53541	Add	53551
53541	Add	53552
53541	Add	53553
53541	Add	53554
53541	Add	53555
53541	Add	53556
53541	Add	53557
53541	Add	53558
53541	Add	53559
53541	Add	53560
53541	Add	53561
53541	Add	53562
53541	Add	53563
53541	Add	53564
53541	Add	53565
53541	Add	53566
53541	Add	53567
53541	Add	53568
53541	Add	53569
53541	Add	53570
53541	Add	53571
53541	Add	53572
53541	Add	53573
53541	Add	53574
53575	Add	53576
53575	Add	53577
53575	Add	53578
53575	Add	53579
53575	Add	53580
53575	Add	53581
53575	Add	53582
53575	Add	53583
53575	Add	53584
53575	Add	53585
53575	Add	53586
53575	Add	53587
53575	Add	53588
53575	Add	53589
53575	Add	53590
53575	Add	53591
53575	Add	53592
53575	Add	53593
53575	Add	53594
53575	Add	53595
53575	Add	53596
53575	Add	53597
53575	Add	53598
53575	Add	53599
53575	Add	53600
53575	Add	53601
53575	Add	53602
53575	Add	53603
53575	Add	53604
53575	Add	53605
53575	Add	53606
53575	Add	53607
53575	Add	53608
53575	Add	53609
53575	Add	53610
53575	Add	53611
53575	Add	53612
53575	Add	53613
53575	Add	53614
53575	Add	53615
53575	Add	53616
53575	Add	53617
53575	Add	53618
53575	Add	53619
53575	Add	53620
53575	Add	53621
53575	Add	53622
53575	Add	53623
53575	Add	53624
53575	Add	53625
53575	Add	53626
53575	Add	53627
53575	Add	53628
53575	Add	53629
53575	Add	53630
53575	Add	53631
53575	Add	53632
53575	Add	53633
53575	Add	53634
53575	Add	53635
53636	Add	53637
53636	Add	53638
53636	Add	53639
53636	Add	53640
53636	Add	53641
53636	Add	53642
53636	Add	53643
53636	Add	53644
53636	Add	53645
53636	Add	53646
53636	Add	53647
53636	Add	53648
53636	Add	53649
53650	Add	53651
53650	Add	53652
53650	Add	53653
53650	Add	53654
53650	Add	53655
53650	Add	53656
53650	Add	53657
53650	Add	53658
53650	Add	53659
53650	Add	53660
53650	Add	53661
53650	Add	53662
53650	Add	53663
53650	Add	53664
53665	Add	53666
53665	Add	53667
53665	Add	53668
53665	Add	53669
53665	Add	53670
53665	Add	53671
53665	Add	53672
53665	Add	53673
53665	Add	53674
53665	Add	53675
53665	Add	53676
53665	Add	53677
53665	Add	53678
53665	Add	53679
53665	Add	53680
53665	Add	53681
53665	Add	53682
53665	Add	53683
53665	Add	53684
53685	Add	53686
53685	Add	53687
53685	Add	53688
53685	Add	53689
53685	Add	53690
53685	Add	53691
53685	Add	53692
53685	Add	53693
53685	Add	53694
53685	Add	53695
53685	Add	53696
53685	Add	53697
53685	Add	53698
53685	Add	53699
53685	Add	53700
53685	Add	53701
53685	Add	53702
53685	Add	53703
53685	Add	53704
53685	Add	53705
53685	Add	53706
53685	Add	53707
53685	Add	53708
53685	Add	53709
53685	Add	53710
53685	Add	53711
53685	Add	53712
53685	Add	53713
53685	Add	53714
53685	Add	53715
53685	Add	53716
53685	Add	53717
53685	Add	53718
53685	Add	53719
53685	Add	53720
53685	Add	53721
53685	Add	53722
53723	Add	53724
53723	Add	53725
53723	Add	53726
53723	Add	53727
53723	Add	53728
53723	Add	53729
53723	Add	53730
53723	Add	53731
53723	Add	53732
53723	Add	53733
53723	Add	53734
53723	Add	53735
53723	Add	53736
53723	Add	53737
53723	Add	53738
53723	Add	53739
53723	Add	53740
53723	Add	53741
53742	Add	53743
53742	Add	53744
53742	Add	53745
53742	Add	53746
53742	Add	53747
53742	Add	53748
53742	Add	53749
53742	Add	53750
53742	Add	53751
53742	Add	53752
53742	Add	53753
53742	Add	53754
53742	Add	53755
53742	Add	53756
53742	Add	53757
53758	Add	53759
53758	Add	53760
53758	Add	53761
53758	Add	53762
53758	Add	53763
53758	Add	53764
53758	Add	53765
53758	Add	53766
53758	Add	53767
53758	Add	53768
53758	Add	53769
53758	Add	53770
53758	Add	53771
53758	Add	53772
53758	Add	53773
53758	Add	53774
53758	Add	53775
53758	Add	53776
53758	Add	53777
53758	Add	53778
53758	Add	53779
53780	Add	53781
53780	Add	53782
53780	Add	53783
53780	Add	53784
53780	Add	53785
53780	Add	53786
53780	Add	53787
53780	Add	53788
53780	Add	53789
53780	Add	53790
53780	Add	53791
53780	Add	53792
53780	Add	53793
53780	Add	53794
53780	Add	53795
53796	Add	53797
53796	Add	53798
53796	Add	53799
53796	Add	53800
53796	Add	53801
53796	Add	53802
53796	Add	53803
53796	Add	53804
53796	Add	53805
53796	Add	53806
53796	Add	53807
53796	Add	53808
53796	Add	53809
53796	Add	53810
53811	Add	53812
53811	Add	53813
53811	Add	53814
53811	Add	53815
53811	Add	53816
53811	Add	53817
53811	Add	53818
53811	Add	53819
53811	Add	53820
53811	Add	53821
53811	Add	53822
53811	Add	53823
53811	Add	53824
53811	Add	53825
53811	Add	53826
53811	Add	53827
53811	Add	53828
53811	Add	53829
53830	Add	53831
53830	Add	53832
53830	Add	53833
53830	Add	53834
53830	Add	53835
53830	Add	53836
53830	Add	53837
53830	Add	53838
53830	Add	53839
53830	Add	53840
53830	Add	53841
53830	Add	53842
53830	Add	53843
53830	Add	53844
53830	Add	53845
53830	Add	53846
53830	Add	53847
53830	Add	53848
53849	Add	53850
53849	Add	53851
53849	Add	53852
53849	Add	53853
53849	Add	53854
53849	Add	53855
53849	Add	53856
53849	Add	53857
53849	Add	53858
53849	Add	53859
53849	Add	53860
53849	Add	53861
53849	Add	53862
53849	Add	53863
53849	Add	53864
53849	Add	53865
53849	Add	53866
53849	Add	53867
53849	Add	53868
53849	Add	53869
53849	Add	53870
53871	Add	53872
53871	Add	53873
53871	Add	53874
53871	Add	53875
53871	Add	53876
53871	Add	53877
53871	Add	53878
53871	Add	53879
53871	Add	53880
53871	Add	53881
53871	Add	53882
53871	Add	53883
53871	Add	53884
53871	Add	53885
53871	Add	53886
53871	Add	53887
53871	Add	53888
53871	Add	53889
53890	Add	53891
53890	Add	53892
53890	Add	53893
53890	Add	53894
53890	Add	53895
53890	Add	53896
53890	Add	53897
53890	Add	53898
53890	Add	53899
53890	Add	53900
53890	Add	53901
53902	Add	53903
53902	Add	53904
53902	Add	53905
53902	Add	53906
53902	Add	53907
53902	Add	53908
53902	Add	53909
53902	Add	53910
53902	Add	53911
53902	Add	53912
53902	Add	53913
53902	Add	53914
53902	Add	53915
53916	Add	53917
53916	Add	53918
53916	Add	53919
53916	Add	53920
53916	Add	53921
53916	Add	53922
53916	Add	53923
53916	Add	53924
53916	Add	53925
53916	Add	53926
53916	Add	53927
53916	Add	53928
53916	Add	53929
53916	Add	53930
53916	Add	53931
53916	Add	53932
53916	Add	53933
53916	Add	53934
53916	Add	53935
53916	Add	53936
53916	Add	53937
53916	Add	53938
53916	Add	53939
53916	Add	53940
53941	Add	53942
53941	Add	53943
53941	Add	53944
53941	Add	53945
53941	Add	53946
53941	Add	53947
53941	Add	53948
53941	Add	53949
53941	Add	53950
53941	Add	53951
53941	Add	53952
53941	Add	53953
53941	Add	53954
53941	Add	53955
53941	Add	53956
53941	Add	53957
53941	Add	53958
53941	Add	53959
53941	Add	53960
53941	Add	53961
53941	Add	53962
53941	Add	53963
53941	Add	53964
53941	Add	53965
53966	Add	53967
53966	Add	53968
53966	Add	53969
53966	Add	53970
53966	Add	53971
53966	Add	53972
53966	Add	53973
53966	Add	53974
53966	Add	53975
53966	Add	53976
53966	Add	53977
53966	Add	53978
53966	Add	53979
53966	Add	53980
53966	Add	53981
53966	Add	53982
53966	Add	53983
53984	Add	53985
53984	Add	53986
53984	Add	53987
53984	Add	53988
53984	Add	53989
53984	Add	53990
53984	Add	53991
53984	Add	53992
53984	Add	53993
53984	Add	53994
53984	Add	53995
53984	Add	53996
53984	Add	53997
53984	Add	53998
53984	Add	53999
53984	Add	54000
53984	Add	54001
53984	Add	54002
53984	Add	54003
53984	Add	54004
53984	Add	54005
53984	Add	54006
53984	Add	54007
53984	Add	54008
54009	Add	54010
54009	Add	54011
54009	Add	54012
54009	Add	54013
54009	Add	54014
54009	Add	54015
54009	Add	54016
54009	Add	54017
54009	Add	54018
54009	Add	54019
54009	Add	54020
54009	Add	54021
54009	Add	54022
54009	Add	54023
54009	Add	54024
54009	Add	54025
54009	Add	54026
54009	Add	54027
54009	Add	54028
54029	Add	54030
54029	Add	54031
54029	Add	54032
54029	Add	54033
54029	Add	54034
54029	Add	54035
54029	Add	54036
54029	Add	54037
54029	Add	54038
54029	Add	54039
54029	Add	54040
54029	Add	54041
54029	Add	54042
54029	Add	54043
54029	Add	54044
54029	Add	54045
54029	Add	54046
54029	Add	54047
54048	Add	54049
54048	Add	54050
54048	Add	54051
54048	Add	54052
54048	Add	54053
54048	Add	54054
54048	Add	54055
54048	Add	54056
54048	Add	54057
54048	Add	54058
54048	Add	54059
54048	Add	54060
54048	Add	54061
54048	Add	54062
54048	Add	54063
54048	Add	54064
54048	Add	54065
54048	Add	54066
54048	Add	54067
54048	Add	54068
54048	Add	54069
54048	Add	54070
54048	Add	54071
54048	Add	54072
54048	Add	54073
54074	Add	54075
54074	Add	54076
54074	Add	54077
54074	Add	54078
54074	Add	54079
54074	Add	54080
54074	Add	54081
54074	Add	54082
54074	Add	54083
54074	Add	54084
54074	Add	54085
54074	Add	54086
54074	Add	54087
54074	Add	54088
54074	Add	54089
54074	Add	54090
54074	Add	54091
54074	Add	54092
54093	Add	54094
54093	Add	54095
54093	Add	54096
54093	Add	54097
54093	Add	54098
54093	Add	54099
54093	Add	54100
54093	Add	54101
54093	Add	54102
54093	Add	54103
54093	Add	54104
54093	Add	54105
54093	Add	54106
54107	Add	54108
54107	Add	54109
54107	Add	54110
54107	Add	54111
54107	Add	54112
54107	Add	54113
54107	Add	54114
54107	Add	54115
54107	Add	54116
54107	Add	54117
54107	Add	54118
54107	Add	54119
54107	Add	54120
54107	Add	54121
54122	Add	54123
54122	Add	54124
54122	Add	54125
54122	Add	54126
54122	Add	54127
54122	Add	54128
54122	Add	54129
54122	Add	54130
54122	Add	54131
54122	Add	54132
54122	Add	54133
54122	Add	54134
54122	Add	54135
54122	Add	54136
54122	Add	54137
54122	Add	54138
54122	Add	54139
54122	Add	54140
54122	Add	54141
54122	Add	54142
54122	Add	54143
54122	Add	54144
54145	Add	54146
54145	Add	54147
54145	Add	54148
54145	Add	54149
54145	Add	54150
54151	Add	54152
54151	Add	54153
54151	Add	54154
54151	Add	54155
54151	Add	54156
54151	Add	54157
54151	Add	54158
54151	Add	54159
54151	Add	54160
54151	Add	54161
54151	Add	54162
54163	Add	54164
54163	Add	54165
54163	Add	54166
54163	Add	54167
54163	Add	54168
54163	Add	54169
54163	Add	54170
54163	Add	54171
54163	Add	54172
54173	Add	54174
54173	Add	54175
54173	Add	54176
54173	Add	54177
54173	Add	54178
54173	Add	54179
54173	Add	54180
54173	Add	54181
54173	Add	54182
54173	Add	54183
54173	Add	54184
54173	Add	54185
54173	Add	54186
54173	Add	54187
54173	Add	54188
54189	Add	54190
54189	Add	54191
54189	Add	54192
54189	Add	54193
54189	Add	54194
54189	Add	54195
54189	Add	54196
54189	Add	54197
54189	Add	54198
54189	Add	54199
54189	Add	54200
54189	Add	54201
54189	Add	54202
54189	Add	54203
54189	Add	54204
54189	Add	54205
54189	Add	54206
54189	Add	54207
54189	Add	54208
54189	Add	54209
54189	Add	54210
54189	Add	54211
54189	Add	54212
54189	Add	54213
54189	Add	54214
54215	Add	54216
54215	Add	54217
54215	Add	54218
54215	Add	54219
54215	Add	54220
54215	Add	54221
54215	Add	54222
54215	Add	54223
54215	Add	54224
54215	Add	54225
54226	Add	54227
54226	Add	54228
54226	Add	54229
54226	Add	54230
54226	Add	54231
54226	Add	54232
54226	Add	54233
54226	Add	54234
54226	Add	54235
54236	Add	54237
54236	Add	54238
54236	Add	54239
54236	Add	54240
54236	Add	54241
54236	Add	54242
54236	Add	54243
54236	Add	54244
54236	Add	54245
54236	Add	54246
54236	Add	54247
54236	Add	54248
54236	Add	54249
54236	Add	54250
54236	Add	54251
54236	Add	54252
54236	Add	54253
54236	Add	54254
54236	Add	54255
54236	Add	54256
54236	Add	54257
54236	Add	54258
54236	Add	54259
54236	Add	54260
54236	Add	54261
54262	Add	54263
54262	Add	54264
54262	Add	54265
54262	Add	54266
54262	Add	54267
54262	Add	54268
54262	Add	54269
54270	Add	54271
54270	Add	54272
54270	Add	54273
54270	Add	54274
54270	Add	54275
54270	Add	54276
54270	Add	54277
54270	Add	54278
54270	Add	54279
54270	Add	54280
54270	Add	54281
54270	Add	54282
54270	Add	54283
54270	Add	54284
54270	Add	54285
54270	Add	54286
54270	Add	54287
54270	Add	54288
54289	Add	54290
54289	Add	54291
54289	Add	54292
54289	Add	54293
54289	Add	54294
54289	Add	54295
54289	Add	54296
54289	Add	54297
54289	Add	54298
54289	Add	54299
54289	Add	54300
54289	Add	54301
54289	Add	54302
54289	Add	54303
54304	Add	54305
54304	Add	54306
54304	Add	54307
54304	Add	54308
54304	Add	54309
54304	Add	54310
54304	Add	54311
54312	Add	54313
54312	Add	54314
54312	Add	54315
54312	Add	54316
54312	Add	54317
54312	Add	54318
54312	Add	54319
54320	Add	54321
54320	Add	54322
54320	Add	54323
54320	Add	54324
54320	Add	54325
54320	Add	54326
54320	Add	54327
54320	Add	54328
54320	Add	54329
54320	Add	54330
54320	Add	54331
54320	Add	54332
54320	Add	54333
54320	Add	54334
54320	Add	54335
54320	Add	54336
54320	Add	54337
54320	Add	54338
54320	Add	54339
54320	Add	54340
54320	Add	54341
54342	Add	54343
54342	Add	54344
54342	Add	54345
54342	Add	54346
54342	Add	54347
54342	Add	54348
54342	Add	54349
54342	Add	54350
54342	Add	54351
54342	Add	54352
54342	Add	54353
54342	Add	54354
54342	Add	54355
54342	Add	54356
54342	Add	54357
54358	Add	54359
54358	Add	54360
54358	Add	54361
54358	Add	54362
54358	Add	54363
54358	Add	54364
54358	Add	54365
54358	Add	54366
54358	Add	54367
54358	Add	54368
54358	Add	54369
54358	Add	54370
54358	Add	54371
54372	Add	54373
54372	Add	54374
54372	Add	54375
54372	Add	54376
54372	Add	54377
54372	Add	54378
54372	Add	54379
54372	Add	54380
54372	Add	54381
54372	Add	54382
54372	Add	54383
54372	Add	54384
54372	Add	54385
54372	Add	54386
54387	Add	54388
54387	Add	54389
54387	Add	54390
54387	Add	54391
54387	Add	54392
54387	Add	54393
54387	Add	54394
54387	Add	54395
54387	Add	54396
54387	Add	54397
54387	Add	54398
54387	Add	54399
54387	Add	54400
54387	Add	54401
54387	Add	54402
54387	Add	54403
54387	Add	54404
54387	Add	54405
54387	Add	54406
54387	Add	54407
54387	Add	54408
54387	Add	54409
54387	Add	54410
54387	Add	54411
54387	Add	54412
54387	Add	54413
54387	Add	54414
54415	Add	54416
54415	Add	54417
54415	Add	54418
54415	Add	54419
54420	Add	54421
54420	Add	54422
54420	Add	54423
54420	Add	54424
54420	Add	54425
54420	Add	54426
54420	Add	54427
54420	Add	54428
54420	Add	54429
54420	Add	54430
54420	Add	54431
54420	Add	54432
54433	Add	54434
54433	Add	54435
54433	Add	54436
54433	Add	54437
54433	Add	54438
54433	Add	54439
54433	Add	54440
54433	Add	54441
54433	Add	54442
54433	Add	54443
54433	Add	54444
54433	Add	54445
54433	Add	54446
54447	Add	54448
54447	Add	54449
54447	Add	54450
54447	Add	54451
54447	Add	54452
54447	Add	54453
54447	Add	54454
54447	Add	54455
54447	Add	54456
54447	Add	54457
54447	Add	54458
54447	Add	54459
54447	Add	54460
54447	Add	54461
54447	Add	54462
54447	Add	54463
54447	Add	54464
54447	Add	54465
54447	Add	54466
54447	Add	54467
54447	Add	54468
54447	Add	54469
54447	Add	54470
54447	Add	54471
54447	Add	54472
54447	Add	54473
54447	Add	54474
54447	Add	54475
54447	Add	54476
54447	Add	54477
54478	Add	54479
54478	Add	54480
54478	Add	54481
54478	Add	54482
54478	Add	54483
54478	Add	54484
54478	Add	54485
54478	Add	54486
54478	Add	54487
54478	Add	54488
54478	Add	54489
54478	Add	54490
54478	Add	54491
54478	Add	54492
54478	Add	54493
54478	Add	54494
54478	Add	54495
54478	Add	54496
54478	Add	54497
54478	Add	54498
54499	Add	54500
54499	Add	54501
54499	Add	54502
54499	Add	54503
54499	Add	54504
54505	Add	54506
54505	Add	54507
54505	Add	54508
54505	Add	54509
54505	Add	54510
54505	Add	54511
54505	Add	54512
54505	Add	54513
54505	Add	54514
54505	Add	54515
54505	Add	54516
54517	Add	54518
54517	Add	54519
54517	Add	54520
54517	Add	54521
54517	Add	54522
54517	Add	54523
54517	Add	54524
54517	Add	54525
54517	Add	54526
54517	Add	54527
54517	Add	54528
54517	Add	54529
54517	Add	54530
54531	Add	54532
54531	Add	54533
54531	Add	54534
54531	Add	54535
54531	Add	54536
54531	Add	54537
54531	Add	54538
54531	Add	54539
54531	Add	54540
54531	Add	54541
54531	Add	54542
54543	Add	54544
54543	Add	54545
54543	Add	54546
54543	Add	54547
54543	Add	54548
54543	Add	54549
54543	Add	54550
54543	Add	54551
54543	Add	54552
54543	Add	54553
54543	Add	54554
54555	Add	54556
54555	Add	54557
54555	Add	54558
54555	Add	54559
54555	Add	54560
54555	Add	54561
54555	Add	54562
54555	Add	54563
54555	Add	54564
54555	Add	54565
54555	Add	54566
54555	Add	54567
54555	Add	54568
54555	Add	54569
54555	Add	54570
54555	Add	54571
54555	Add	54572
54555	Add	54573
54555	Add	54574
54555	Add	54575
54555	Add	54576
54555	Add	54577
54555	Add	54578
54555	Add	54579
54555	Add	54580
54555	Add	54581
54555	Add	54582
54555	Add	54583
54555	Add	54584
54555	Add	54585
54555	Add	54586
54555	Add	54587
54555	Add	54588
54555	Add	54589
54555	Add	54590
54555	Add	54591
54555	Add	54592
54555	Add	54593
54555	Add	54594
54555	Add	54595
54555	Add	54596
54555	Add	54597
54555	Add	54598
54555	Add	54599
54555	Add	54600
54555	Add	54601
54555	Add	54602
54555	Add	54603
54555	Add	54604
54555	Add	54605
54555	Add	54606
54555	Add	54607
54555	Add	54608
54555	Add	54609
54555	Add	54610
54555	Add	54611
54555	Add	54612
54555	Add	54613
54555	Add	54614
54555	Add	54615
54555	Add	54616
54617	Add	54618
54617	Add	54619
54617	Add	54620
54617	Add	54621
54617	Add	54622
54617	Add	54623
54617	Add	54624
54617	Add	54625
54617	Add	54626
54617	Add	54627
54617	Add	54628
54617	Add	54629
54617	Add	54630
54617	Add	54631
54617	Add	54632
54617	Add	54633
54617	Add	54634
54617	Add	54635
54636	Add	54637
54636	Add	54638
54636	Add	54639
54636	Add	54640
54636	Add	54641
54642	Add	54643
54642	Add	54644
54642	Add	54645
54642	Add	54646
54647	Add	54648
54647	Add	54649
54647	Add	54650
54647	Add	54651
54652	Add	54653
54652	Add	54654
54652	Add	54655
54652	Add	54656
54652	Add	54657
54652	Add	54658
54652	Add	54659
54660	Add	54661
54660	Add	54662
54660	Add	54663
54660	Add	54664
54660	Add	54665
54660	Add	54666
54667	Add	54668
54667	Add	54669
54667	Add	54670
54667	Add	54671
54667	Add	54672
54667	Add	54673
54674	Add	54675
54674	Add	54676
54674	Add	54677
54674	Add	54678
54674	Add	54679
54674	Add	54680
54674	Add	54681
54682	Add	54683
54682	Add	54684
54682	Add	54685
54682	Add	54686
54682	Add	54687
54682	Add	54688
54682	Add	54689
54682	Add	54690
54682	Add	54691
54682	Add	54692
54682	Add	54693
54682	Add	54694
54682	Add	54695
54696	Add	54697
54696	Add	54698
54696	Add	54699
54696	Add	54700
54696	Add	54701
54702	Add	54703
54702	Add	54704
54702	Add	54705
54702	Add	54706
54702	Add	54707
54702	Add	54708
54709	Add	54710
54709	Add	54711
54709	Add	54712
54709	Add	54713
54709	Add	54714
54709	Add	54715
54709	Add	54716
54709	Add	54717
54709	Add	54718
54709	Add	54719
54709	Add	54720
54709	Add	54721
54709	Add	54722
54709	Add	54723
54709	Add	54724
54709	Add	54725
54709	Add	54726
54709	Add	54727
54709	Add	54728
54709	Add	54729
54709	Add	54730
54731	Add	54732
54731	Add	54733
54731	Add	54734
54731	Add	54735
54731	Add	54736
54731	Add	54737
54731	Add	54738
54731	Add	54739
54731	Add	54740
54741	Add	54742
54741	Add	54743
54741	Add	54744
54741	Add	54745
54746	Add	54747
54746	Add	54748
54746	Add	54749
54746	Add	54750
54746	Add	54751
54746	Add	54752
54746	Add	54753
54754	Add	54755
54754	Add	54756
54754	Add	54757
54754	Add	54758
54754	Add	54759
54754	Add	54760
54754	Add	54761
54754	Add	54762
54754	Add	54763
54764	Add	54765
54764	Add	54766
54764	Add	54767
54764	Add	54768
54764	Add	54769
54764	Add	54770
54764	Add	54771
54764	Add	54772
54764	Add	54773
54764	Add	54774
54775	Add	54776
54775	Add	54777
54775	Add	54778
54775	Add	54779
54775	Add	54780
54775	Add	54781
54775	Add	54782
54783	Add	54784
54783	Add	54785
54783	Add	54786
54783	Add	54787
54783	Add	54788
54783	Add	54789
54783	Add	54790
54783	Add	54791
54792	Add	54793
54792	Add	54794
54792	Add	54795
54792	Add	54796
54792	Add	54797
54792	Add	54798
54792	Add	54799
54792	Add	54800
54792	Add	54801
54792	Add	54802
54792	Add	54803
54804	Add	54805
54804	Add	54806
54804	Add	54807
54804	Add	54808
54804	Add	54809
54804	Add	54810
54804	Add	54811
54812	Add	54813
54812	Add	54814
54812	Add	54815
54812	Add	54816
54812	Add	54817
54812	Add	54818
54812	Add	54819
54812	Add	54820
54812	Add	54821
54812	Add	54822
54812	Add	54823
54812	Add	54824
54812	Add	54825
54812	Add	54826
54812	Add	54827
54828	Add	54829
54828	Add	54830
54828	Add	54831
54828	Add	54832
54828	Add	54833
54828	Add	54834
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
52364	Aaron_Day.xml		file:/Users/kuangc/workspace/openii/data/icde05data/inventory/schemas/Aaron_Day.xml			t
52396	Alex_Cho.xml		file:/Users/kuangc/workspace/openii/data/icde05data/inventory/schemas/Alex_Cho.xml			t
52435	alon.xml		file:/Users/kuangc/workspace/openii/data/icde05data/inventory/schemas/alon.xml			t
52489	askew.xml		file:/Users/kuangc/workspace/openii/data/icde05data/inventory/schemas/askew.xml			t
52519	Barrett_Arney.xml		file:/Users/kuangc/workspace/openii/data/icde05data/inventory/schemas/Barrett_Arney.xml			t
52549	bokan-tomic.xml		file:/Users/kuangc/workspace/openii/data/icde05data/inventory/schemas/bokan-tomic.xml			t
52585	burdick.xml		file:/Users/kuangc/workspace/openii/data/icde05data/inventory/schemas/burdick.xml			t
52621	chang.xml		file:/Users/kuangc/workspace/openii/data/icde05data/inventory/schemas/chang.xml			t
52652	christensen.xml		file:/Users/kuangc/workspace/openii/data/icde05data/inventory/schemas/christensen.xml			t
52683	Colin_Bleckner.xml		file:/Users/kuangc/workspace/openii/data/icde05data/inventory/schemas/Colin_Bleckner.xml			t
52710	collier.xml		file:/Users/kuangc/workspace/openii/data/icde05data/inventory/schemas/collier.xml			t
52739	cox.xml		file:/Users/kuangc/workspace/openii/data/icde05data/inventory/schemas/cox.xml			t
52788	dearing.xml		file:/Users/kuangc/workspace/openii/data/icde05data/inventory/schemas/dearing.xml			t
52822	erbad.xml		file:/Users/kuangc/workspace/openii/data/icde05data/inventory/schemas/erbad.xml			t
52857	haji-yusuf.xml		file:/Users/kuangc/workspace/openii/data/icde05data/inventory/schemas/haji-yusuf.xml			t
52890	higgins.xml		file:/Users/kuangc/workspace/openii/data/icde05data/inventory/schemas/higgins.xml			t
52929	igor.xml		file:/Users/kuangc/workspace/openii/data/icde05data/inventory/schemas/igor.xml			t
52968	Jagroop_Singh_Dhillon.xml		file:/Users/kuangc/workspace/openii/data/icde05data/inventory/schemas/Jagroop_Singh_Dhillon.xml			t
52994	Jake_Foster.xml		file:/Users/kuangc/workspace/openii/data/icde05data/inventory/schemas/Jake_Foster.xml			t
53026	jaya.xml		file:/Users/kuangc/workspace/openii/data/icde05data/inventory/schemas/jaya.xml			t
53064	keto.xml		file:/Users/kuangc/workspace/openii/data/icde05data/inventory/schemas/keto.xml			t
53140	luna.xml		file:/Users/kuangc/workspace/openii/data/icde05data/inventory/schemas/luna.xml			t
53177	matt.xml		file:/Users/kuangc/workspace/openii/data/icde05data/inventory/schemas/matt.xml			t
53235	Melissa_Garcia.xml		file:/Users/kuangc/workspace/openii/data/icde05data/inventory/schemas/Melissa_Garcia.xml			t
53273	nilesh.xml		file:/Users/kuangc/workspace/openii/data/icde05data/inventory/schemas/nilesh.xml			t
53310	peter.xml		file:/Users/kuangc/workspace/openii/data/icde05data/inventory/schemas/peter.xml			t
53356	pradeep.xml		file:/Users/kuangc/workspace/openii/data/icde05data/inventory/schemas/pradeep.xml			t
53390	rachel.xml		file:/Users/kuangc/workspace/openii/data/icde05data/inventory/schemas/rachel.xml			t
53472	Rachel_Hunt.xml		file:/Users/kuangc/workspace/openii/data/icde05data/inventory/schemas/Rachel_Hunt.xml			t
53509	Ryan_Eyers.xml		file:/Users/kuangc/workspace/openii/data/icde05data/inventory/schemas/Ryan_Eyers.xml			t
53541	Xenia_Hertzenberg.xml		file:/Users/kuangc/workspace/openii/data/icde05data/inventory/schemas/Xenia_Hertzenberg.xml			t
53575	yana.xml		file:/Users/kuangc/workspace/openii/data/icde05data/inventory/schemas/yana.xml			t
53636	Aaron_Day-small.xml		file:/Users/kuangc/workspace/openii/data/icde05data/invsmall/schemas/Aaron_Day-small.xml			t
53650	AimanErbad-small.xml		file:/Users/kuangc/workspace/openii/data/icde05data/invsmall/schemas/AimanErbad-small.xml			t
53665	Alex_Cho-small.xml		file:/Users/kuangc/workspace/openii/data/icde05data/invsmall/schemas/Alex_Cho-small.xml			t
53685	alon-small.xml		file:/Users/kuangc/workspace/openii/data/icde05data/invsmall/schemas/alon-small.xml			t
53723	AndyJaya-small.xml		file:/Users/kuangc/workspace/openii/data/icde05data/invsmall/schemas/AndyJaya-small.xml			t
53742	Barrett_Arney-small.xml		file:/Users/kuangc/workspace/openii/data/icde05data/invsmall/schemas/Barrett_Arney-small.xml			t
53758	BrankicaBokanTomic-small.xml		file:/Users/kuangc/workspace/openii/data/icde05data/invsmall/schemas/BrankicaBokanTomic-small.xml			t
53780	BrianChang-small.xml		file:/Users/kuangc/workspace/openii/data/icde05data/invsmall/schemas/BrianChang-small.xml			t
53796	Colin_Bleckner-small.xml		file:/Users/kuangc/workspace/openii/data/icde05data/invsmall/schemas/Colin_Bleckner-small.xml			t
53811	DavidBurdick-small.xml		file:/Users/kuangc/workspace/openii/data/icde05data/invsmall/schemas/DavidBurdick-small.xml			t
53830	DavidDearing-small.xml		file:/Users/kuangc/workspace/openii/data/icde05data/invsmall/schemas/DavidDearing-small.xml			t
53849	igor-small.xml		file:/Users/kuangc/workspace/openii/data/icde05data/invsmall/schemas/igor-small.xml			t
53871	JacobChristensen-small.xml		file:/Users/kuangc/workspace/openii/data/icde05data/invsmall/schemas/JacobChristensen-small.xml			t
53890	Jagroop_Singh_Dhillon-small.xml		file:/Users/kuangc/workspace/openii/data/icde05data/invsmall/schemas/Jagroop_Singh_Dhillon-small.xml			t
53902	Jake_Foster-small.xml		file:/Users/kuangc/workspace/openii/data/icde05data/invsmall/schemas/Jake_Foster-small.xml			t
53916	JohnKeto-small.xml		file:/Users/kuangc/workspace/openii/data/icde05data/invsmall/schemas/JohnKeto-small.xml			t
53941	luna-small.xml		file:/Users/kuangc/workspace/openii/data/icde05data/invsmall/schemas/luna-small.xml			t
53966	MandyAskew-small.xml		file:/Users/kuangc/workspace/openii/data/icde05data/invsmall/schemas/MandyAskew-small.xml			t
53984	matt-small.xml		file:/Users/kuangc/workspace/openii/data/icde05data/invsmall/schemas/matt-small.xml			t
54009	MattewCox-small.xml		file:/Users/kuangc/workspace/openii/data/icde05data/invsmall/schemas/MattewCox-small.xml			t
54029	MattewHiggins-small.xml		file:/Users/kuangc/workspace/openii/data/icde05data/invsmall/schemas/MattewHiggins-small.xml			t
54048	mediated-small.xml		file:/Users/kuangc/workspace/openii/data/icde05data/invsmall/schemas/mediated-small.xml			t
54074	Melissa_Garcia-small.xml		file:/Users/kuangc/workspace/openii/data/icde05data/invsmall/schemas/Melissa_Garcia-small.xml			t
54093	MichaelCollier-small.xml		file:/Users/kuangc/workspace/openii/data/icde05data/invsmall/schemas/MichaelCollier-small.xml			t
54107	NemoHaji-Yusuf-small.xml		file:/Users/kuangc/workspace/openii/data/icde05data/invsmall/schemas/NemoHaji-Yusuf-small.xml			t
54122	nilesh-small.xml		file:/Users/kuangc/workspace/openii/data/icde05data/invsmall/schemas/nilesh-small.xml			t
54145	1stopauto_new.xml		file:/Users/kuangc/workspace/openii/data/icde05data/auto/schemas/1stopauto_new.xml			t
54151	1stopauto_used.xml		file:/Users/kuangc/workspace/openii/data/icde05data/auto/schemas/1stopauto_used.xml			t
54163	401carfinder.xml		file:/Users/kuangc/workspace/openii/data/icde05data/auto/schemas/401carfinder.xml			t
54173	akguam.xml		file:/Users/kuangc/workspace/openii/data/icde05data/auto/schemas/akguam.xml			t
54189	amarillo.xml		file:/Users/kuangc/workspace/openii/data/icde05data/auto/schemas/amarillo.xml			t
54215	autobytel.xml		file:/Users/kuangc/workspace/openii/data/icde05data/auto/schemas/autobytel.xml			t
54226	autochooser.xml		file:/Users/kuangc/workspace/openii/data/icde05data/auto/schemas/autochooser.xml			t
54236	AutoMob.xml		file:/Users/kuangc/workspace/openii/data/icde05data/auto/schemas/AutoMob.xml			t
54262	AutoNation.xml		file:/Users/kuangc/workspace/openii/data/icde05data/auto/schemas/AutoNation.xml			t
54270	Autonet.xml		file:/Users/kuangc/workspace/openii/data/icde05data/auto/schemas/Autonet.xml			t
54289	Autonet_ca.xml		file:/Users/kuangc/workspace/openii/data/icde05data/auto/schemas/Autonet_ca.xml			t
54304	Autopoint.xml		file:/Users/kuangc/workspace/openii/data/icde05data/auto/schemas/Autopoint.xml			t
54312	Autoweb.xml		file:/Users/kuangc/workspace/openii/data/icde05data/auto/schemas/Autoweb.xml			t
54320	buycars.xml		file:/Users/kuangc/workspace/openii/data/icde05data/auto/schemas/buycars.xml			t
54342	carcast.xml		file:/Users/kuangc/workspace/openii/data/icde05data/auto/schemas/carcast.xml			t
54358	carprices.xml		file:/Users/kuangc/workspace/openii/data/icde05data/auto/schemas/carprices.xml			t
54372	cars.xml		file:/Users/kuangc/workspace/openii/data/icde05data/auto/schemas/cars.xml			t
54387	carsearch.xml		file:/Users/kuangc/workspace/openii/data/icde05data/auto/schemas/carsearch.xml			t
54415	cartalk.xml		file:/Users/kuangc/workspace/openii/data/icde05data/auto/schemas/cartalk.xml			t
54420	colvinauto.xml		file:/Users/kuangc/workspace/openii/data/icde05data/auto/schemas/colvinauto.xml			t
54433	dealernet.xml		file:/Users/kuangc/workspace/openii/data/icde05data/auto/schemas/dealernet.xml			t
54447	discountautopricing.xml		file:/Users/kuangc/workspace/openii/data/icde05data/auto/schemas/discountautopricing.xml			t
54478	ebaymotors.xml		file:/Users/kuangc/workspace/openii/data/icde05data/auto/schemas/ebaymotors.xml			t
54499	fredbondesen.xml		file:/Users/kuangc/workspace/openii/data/icde05data/auto/schemas/fredbondesen.xml			t
54505	getAuto.xml		file:/Users/kuangc/workspace/openii/data/icde05data/auto/schemas/getAuto.xml			t
54517	gotcars4sale.xml		file:/Users/kuangc/workspace/openii/data/icde05data/auto/schemas/gotcars4sale.xml			t
54531	herzogmeier.xml		file:/Users/kuangc/workspace/openii/data/icde05data/auto/schemas/herzogmeier.xml			t
54543	hoakmotors.xml		file:/Users/kuangc/workspace/openii/data/icde05data/auto/schemas/hoakmotors.xml			t
54555	mediatedSchema.xml		file:/Users/kuangc/workspace/openii/data/icde05data/auto/schemas/mediatedSchema.xml			t
54617	nada.xml		file:/Users/kuangc/workspace/openii/data/icde05data/auto/schemas/nada.xml			t
54636	tourist.xml		file:/Users/kuangc/workspace/openii/data/icde05data/auto/schemas/tourist.xml			t
54642	Apartments.xml		file:/Users/kuangc/workspace/openii/data/icde05data/realestate/schemas/Apartments.xml			t
54647	biztrader.xml		file:/Users/kuangc/workspace/openii/data/icde05data/realestate/schemas/biztrader.xml			t
54652	cityfeet.xml		file:/Users/kuangc/workspace/openii/data/icde05data/realestate/schemas/cityfeet.xml			t
54660	CommercialRealEstate.xml		file:/Users/kuangc/workspace/openii/data/icde05data/realestate/schemas/CommercialRealEstate.xml			t
54667	eLandUSA.xml		file:/Users/kuangc/workspace/openii/data/icde05data/realestate/schemas/eLandUSA.xml			t
54674	EquestrianLIVING.xml		file:/Users/kuangc/workspace/openii/data/icde05data/realestate/schemas/EquestrianLIVING.xml			t
54682	eSearchHomes-1.xml		file:/Users/kuangc/workspace/openii/data/icde05data/realestate/schemas/eSearchHomes-1.xml			t
54696	HomeBuilders.xml		file:/Users/kuangc/workspace/openii/data/icde05data/realestate/schemas/HomeBuilders.xml			t
54702	homeseekers.xml		file:/Users/kuangc/workspace/openii/data/icde05data/realestate/schemas/homeseekers.xml			t
54709	mediated.xml		file:/Users/kuangc/workspace/openii/data/icde05data/realestate/schemas/mediated.xml			t
54731	NationalMobile.xml		file:/Users/kuangc/workspace/openii/data/icde05data/realestate/schemas/NationalMobile.xml			t
54741	new-home-builders.xml		file:/Users/kuangc/workspace/openii/data/icde05data/realestate/schemas/new-home-builders.xml			t
54746	NorthIdahoHomeseekers.xml		file:/Users/kuangc/workspace/openii/data/icde05data/realestate/schemas/NorthIdahoHomeseekers.xml			t
54754	PlanetProperties.xml		file:/Users/kuangc/workspace/openii/data/icde05data/realestate/schemas/PlanetProperties.xml			t
54764	Property2000-1.xml		file:/Users/kuangc/workspace/openii/data/icde05data/realestate/schemas/Property2000-1.xml			t
54775	RealEstate.xml		file:/Users/kuangc/workspace/openii/data/icde05data/realestate/schemas/RealEstate.xml			t
54783	RealtyInvestor-1.xml		file:/Users/kuangc/workspace/openii/data/icde05data/realestate/schemas/RealtyInvestor-1.xml			t
54792	SpaceForLease.xml		file:/Users/kuangc/workspace/openii/data/icde05data/realestate/schemas/SpaceForLease.xml			t
54804	USLots.xml		file:/Users/kuangc/workspace/openii/data/icde05data/realestate/schemas/USLots.xml			t
54812	webrealestate.xml		file:/Users/kuangc/workspace/openii/data/icde05data/realestate/schemas/webrealestate.xml			t
54828	YahooRealEstate.xml		file:/Users/kuangc/workspace/openii/data/icde05data/realestate/schemas/YahooRealEstate.xml			t
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
52367	Entity
52368	Entity
52369	Attribute
52370	Attribute
52371	Attribute
52372	Attribute
52373	Attribute
52374	Attribute
52375	Attribute
52376	Attribute
52377	Attribute
52378	Attribute
52379	Attribute
52380	Attribute
52381	Attribute
52382	Attribute
52383	Attribute
52384	Attribute
52385	Attribute
52386	Attribute
52387	Attribute
52388	Attribute
52389	Attribute
52390	Attribute
52391	Attribute
52392	Attribute
52393	Attribute
52394	Attribute
52395	Attribute
52397	Entity
52398	Entity
52399	Entity
52400	Entity
52401	Entity
52402	Entity
52403	Entity
52404	Entity
52405	Entity
52406	Attribute
52407	Attribute
52408	Attribute
52409	Attribute
52410	Attribute
52411	Attribute
52412	Attribute
52413	Attribute
52414	Attribute
52415	Attribute
52416	Attribute
52417	Attribute
52418	Attribute
52419	Attribute
52420	Attribute
52421	Attribute
52422	Attribute
52423	Attribute
52424	Attribute
52425	Attribute
52426	Attribute
52427	Attribute
52428	Attribute
52429	Attribute
52430	Attribute
52431	Attribute
52432	Attribute
52433	Attribute
52434	Attribute
52436	Entity
52437	Entity
52438	Entity
52439	Entity
52440	Entity
52441	Entity
52442	Entity
52443	Entity
52444	Entity
52445	Entity
52446	Entity
52447	Entity
52448	Attribute
52449	Attribute
52450	Attribute
52451	Attribute
52452	Attribute
52453	Attribute
52454	Attribute
52455	Attribute
52456	Attribute
52457	Attribute
52458	Attribute
52459	Attribute
52460	Attribute
52461	Attribute
52462	Attribute
52463	Attribute
52464	Attribute
52465	Attribute
52466	Attribute
52467	Attribute
52468	Attribute
52469	Attribute
52470	Attribute
52471	Attribute
52472	Attribute
52473	Attribute
52474	Attribute
52475	Attribute
52476	Attribute
52477	Attribute
52478	Attribute
52479	Attribute
52480	Attribute
52481	Attribute
52482	Attribute
52483	Attribute
52484	Attribute
52485	Attribute
52486	Attribute
52487	Attribute
52488	Attribute
52490	Entity
52491	Entity
52492	Entity
52493	Entity
52494	Entity
52495	Entity
52496	Entity
52497	Entity
52498	Attribute
52499	Attribute
52500	Attribute
52501	Attribute
52502	Attribute
52503	Attribute
52504	Attribute
52505	Attribute
52506	Attribute
52507	Attribute
52508	Attribute
52509	Attribute
52510	Attribute
52511	Attribute
52512	Attribute
52513	Attribute
52514	Attribute
52515	Attribute
52516	Attribute
52517	Attribute
52518	Attribute
52520	Entity
52521	Entity
52522	Entity
52523	Entity
52524	Entity
52525	Attribute
52526	Attribute
52527	Attribute
52528	Attribute
52529	Attribute
52530	Attribute
52531	Attribute
52532	Attribute
52533	Attribute
52534	Attribute
52535	Attribute
52536	Attribute
52537	Attribute
52538	Attribute
52539	Attribute
52540	Attribute
52541	Attribute
52542	Attribute
52543	Attribute
52544	Attribute
52545	Attribute
52546	Attribute
52547	Attribute
52548	Attribute
52550	Entity
52551	Entity
52552	Entity
52553	Entity
52554	Entity
52555	Entity
52556	Entity
52557	Entity
52558	Entity
52559	Attribute
52560	Attribute
52561	Attribute
52562	Attribute
52563	Attribute
52564	Attribute
52565	Attribute
52566	Attribute
52567	Attribute
52568	Attribute
52569	Attribute
52570	Attribute
52571	Attribute
52572	Attribute
52573	Attribute
52574	Attribute
52575	Attribute
52576	Attribute
52577	Attribute
52578	Attribute
52579	Attribute
52580	Attribute
52581	Attribute
52582	Attribute
52583	Attribute
52584	Attribute
52586	Entity
52587	Entity
52588	Entity
52589	Entity
52590	Entity
52591	Entity
52592	Entity
52593	Entity
52594	Entity
52595	Entity
52596	Attribute
52597	Attribute
52598	Attribute
52599	Attribute
52600	Attribute
52601	Attribute
52602	Attribute
52603	Attribute
52604	Attribute
52605	Attribute
52606	Attribute
52607	Attribute
52608	Attribute
52609	Attribute
52610	Attribute
52611	Attribute
52612	Attribute
52613	Attribute
52614	Attribute
52615	Attribute
52616	Attribute
52617	Attribute
52618	Attribute
52619	Attribute
52620	Attribute
52622	Entity
52623	Entity
52624	Entity
52625	Entity
52626	Entity
52627	Entity
52628	Entity
52629	Entity
52630	Entity
52631	Entity
52632	Attribute
52633	Attribute
52634	Attribute
52635	Attribute
52636	Attribute
52637	Attribute
52638	Attribute
52639	Attribute
52640	Attribute
52641	Attribute
52642	Attribute
52643	Attribute
52644	Attribute
52645	Attribute
52646	Attribute
52647	Attribute
52648	Attribute
52649	Attribute
52650	Attribute
52651	Attribute
52653	Entity
52654	Entity
52655	Entity
52656	Entity
52657	Entity
52658	Entity
52659	Entity
52660	Attribute
52661	Attribute
52662	Attribute
52663	Attribute
52664	Attribute
52665	Attribute
52666	Attribute
52667	Attribute
52668	Attribute
52669	Attribute
52670	Attribute
52671	Attribute
52672	Attribute
52673	Attribute
52674	Attribute
52675	Attribute
52676	Attribute
52677	Attribute
52678	Attribute
52679	Attribute
52680	Attribute
52681	Attribute
52682	Attribute
52684	Entity
52685	Entity
52686	Entity
52687	Attribute
52688	Attribute
52689	Attribute
52690	Attribute
52691	Attribute
52692	Attribute
52693	Attribute
52694	Attribute
52695	Attribute
52696	Attribute
52697	Attribute
52698	Attribute
52699	Attribute
52700	Attribute
52701	Attribute
52702	Attribute
52703	Attribute
52704	Attribute
52705	Attribute
52706	Attribute
52707	Attribute
52708	Attribute
52709	Attribute
52711	Entity
52712	Entity
52713	Entity
52714	Entity
52715	Entity
52716	Entity
52717	Entity
52718	Attribute
52719	Attribute
52720	Attribute
52721	Attribute
52722	Attribute
52723	Attribute
52724	Attribute
52725	Attribute
52726	Attribute
52727	Attribute
52728	Attribute
52729	Attribute
52730	Attribute
52731	Attribute
52732	Attribute
52733	Attribute
52734	Attribute
52735	Attribute
52736	Attribute
52737	Attribute
52738	Attribute
52740	Entity
52741	Entity
52742	Entity
52743	Entity
52744	Entity
52745	Entity
52746	Entity
52747	Entity
52748	Entity
52749	Entity
52750	Entity
52751	Entity
52752	Entity
52753	Entity
52754	Entity
52755	Attribute
52756	Attribute
52757	Attribute
52758	Attribute
52759	Attribute
52760	Attribute
52761	Attribute
52762	Attribute
52763	Attribute
52764	Attribute
52765	Attribute
52766	Attribute
52767	Attribute
52768	Attribute
52769	Attribute
52770	Attribute
52771	Attribute
52772	Attribute
52773	Attribute
52774	Attribute
52775	Attribute
52776	Attribute
52777	Attribute
52778	Attribute
52779	Attribute
52780	Attribute
52781	Attribute
52782	Attribute
52783	Attribute
52784	Attribute
52785	Attribute
52786	Attribute
52787	Attribute
52789	Entity
52790	Entity
52791	Entity
52792	Entity
52793	Entity
52794	Entity
52795	Entity
52796	Entity
52797	Attribute
52798	Attribute
52799	Attribute
52800	Attribute
52801	Attribute
52802	Attribute
52803	Attribute
52804	Attribute
52805	Attribute
52806	Attribute
52807	Attribute
52808	Attribute
52809	Attribute
52810	Attribute
52811	Attribute
52812	Attribute
52813	Attribute
52814	Attribute
52815	Attribute
52816	Attribute
52817	Attribute
52818	Attribute
52819	Attribute
52820	Attribute
52821	Attribute
52823	Entity
52824	Entity
52825	Entity
52826	Entity
52827	Entity
52828	Entity
52829	Attribute
52830	Attribute
52831	Attribute
52832	Attribute
52833	Attribute
52834	Attribute
52835	Attribute
52836	Attribute
52837	Attribute
52838	Attribute
52839	Attribute
52840	Attribute
52841	Attribute
52842	Attribute
52843	Attribute
52844	Attribute
52845	Attribute
52846	Attribute
52847	Attribute
52848	Attribute
52849	Attribute
52850	Attribute
52851	Attribute
52852	Attribute
52853	Attribute
52854	Attribute
52855	Attribute
52856	Attribute
52858	Entity
52859	Entity
52860	Entity
52861	Entity
52862	Entity
52863	Entity
52864	Attribute
52865	Attribute
52866	Attribute
52867	Attribute
52868	Attribute
52869	Attribute
52870	Attribute
52871	Attribute
52872	Attribute
52873	Attribute
52874	Attribute
52875	Attribute
52876	Attribute
52877	Attribute
52878	Attribute
52879	Attribute
52880	Attribute
52881	Attribute
52882	Attribute
52883	Attribute
52884	Attribute
52885	Attribute
52886	Attribute
52887	Attribute
52888	Attribute
52889	Attribute
52891	Entity
52892	Entity
52893	Entity
52894	Entity
52895	Entity
52896	Entity
52897	Entity
52898	Entity
52899	Entity
52900	Entity
52901	Entity
52902	Entity
52903	Attribute
52904	Attribute
52905	Attribute
52906	Attribute
52907	Attribute
52908	Attribute
52909	Attribute
52910	Attribute
52911	Attribute
52912	Attribute
52913	Attribute
52914	Attribute
52915	Attribute
52916	Attribute
52917	Attribute
52918	Attribute
52919	Attribute
52920	Attribute
52921	Attribute
52922	Attribute
52923	Attribute
52924	Attribute
52925	Attribute
52926	Attribute
52927	Attribute
52928	Attribute
52930	Entity
52931	Entity
52932	Entity
52933	Entity
52934	Entity
52935	Entity
52936	Entity
52937	Attribute
52938	Attribute
52939	Attribute
52940	Attribute
52941	Attribute
52942	Attribute
52943	Attribute
52944	Attribute
52945	Attribute
52946	Attribute
52947	Attribute
52948	Attribute
52949	Attribute
52950	Attribute
52951	Attribute
52952	Attribute
52953	Attribute
52954	Attribute
52955	Attribute
52956	Attribute
52957	Attribute
52958	Attribute
52959	Attribute
52960	Attribute
52961	Attribute
52962	Attribute
52963	Attribute
52964	Attribute
52965	Attribute
52966	Attribute
52967	Attribute
52969	Entity
52970	Entity
52971	Entity
52972	Entity
52973	Entity
52974	Attribute
52975	Attribute
52976	Attribute
52977	Attribute
52978	Attribute
52979	Attribute
52980	Attribute
52981	Attribute
52982	Attribute
52983	Attribute
52984	Attribute
52985	Attribute
52986	Attribute
52987	Attribute
52988	Attribute
52989	Attribute
52990	Attribute
52991	Attribute
52992	Attribute
52993	Attribute
52995	Entity
52996	Entity
52997	Entity
52998	Entity
52999	Attribute
53000	Attribute
53001	Attribute
53002	Attribute
53003	Attribute
53004	Attribute
53005	Attribute
53006	Attribute
53007	Attribute
53008	Attribute
53009	Attribute
53010	Attribute
53011	Attribute
53012	Attribute
53013	Attribute
53014	Attribute
53015	Attribute
53016	Attribute
53017	Attribute
53018	Attribute
53019	Attribute
53020	Attribute
53021	Attribute
53022	Attribute
53023	Attribute
53024	Attribute
53025	Attribute
53027	Entity
53028	Entity
53029	Entity
53030	Entity
53031	Entity
53032	Entity
53033	Entity
53034	Entity
53035	Entity
53036	Attribute
53037	Attribute
53038	Attribute
53039	Attribute
53040	Attribute
53041	Attribute
53042	Attribute
53043	Attribute
53044	Attribute
53045	Attribute
53046	Attribute
53047	Attribute
53048	Attribute
53049	Attribute
53050	Attribute
53051	Attribute
53052	Attribute
53053	Attribute
53054	Attribute
53055	Attribute
53056	Attribute
53057	Attribute
53058	Attribute
53059	Attribute
53060	Attribute
53061	Attribute
53062	Attribute
53063	Attribute
53065	Entity
53066	Entity
53067	Entity
53068	Entity
53069	Entity
53070	Entity
53071	Entity
53072	Entity
53073	Entity
53074	Entity
53075	Entity
53076	Entity
53077	Entity
53078	Entity
53079	Entity
53080	Entity
53081	Entity
53082	Entity
53083	Entity
53084	Entity
53085	Entity
53086	Entity
53087	Attribute
53088	Attribute
53089	Attribute
53090	Attribute
53091	Attribute
53092	Attribute
53093	Attribute
53094	Attribute
53095	Attribute
53096	Attribute
53097	Attribute
53098	Attribute
53099	Attribute
53100	Attribute
53101	Attribute
53102	Attribute
53103	Attribute
53104	Attribute
53105	Attribute
53106	Attribute
53107	Attribute
53108	Attribute
53109	Attribute
53110	Attribute
53111	Attribute
53112	Attribute
53113	Attribute
53114	Attribute
53115	Attribute
53116	Attribute
53117	Attribute
53118	Attribute
53119	Attribute
53120	Attribute
53121	Attribute
53122	Attribute
53123	Attribute
53124	Attribute
53125	Attribute
53126	Attribute
53127	Attribute
53128	Attribute
53129	Attribute
53130	Attribute
53131	Attribute
53132	Attribute
53133	Attribute
53134	Attribute
53135	Attribute
53136	Attribute
53137	Attribute
53138	Attribute
53139	Attribute
53141	Entity
53142	Entity
53143	Entity
53144	Entity
53145	Entity
53146	Entity
53147	Entity
53148	Attribute
53149	Attribute
53150	Attribute
53151	Attribute
53152	Attribute
53153	Attribute
53154	Attribute
53155	Attribute
53156	Attribute
53157	Attribute
53158	Attribute
53159	Attribute
53160	Attribute
53161	Attribute
53162	Attribute
53163	Attribute
53164	Attribute
53165	Attribute
53166	Attribute
53167	Attribute
53168	Attribute
53169	Attribute
53170	Attribute
53171	Attribute
53172	Attribute
53173	Attribute
53174	Attribute
53175	Attribute
53176	Attribute
53178	Entity
53179	Entity
53180	Entity
53181	Entity
53182	Entity
53183	Entity
53184	Entity
53185	Entity
53186	Attribute
53187	Attribute
53188	Attribute
53189	Attribute
53190	Attribute
53191	Attribute
53192	Attribute
53193	Attribute
53194	Attribute
53195	Attribute
53196	Attribute
53197	Attribute
53198	Attribute
53199	Attribute
53200	Attribute
53201	Attribute
53202	Attribute
53203	Attribute
53204	Attribute
53205	Attribute
53206	Attribute
53207	Attribute
53208	Attribute
53209	Attribute
53210	Attribute
53211	Attribute
53212	Attribute
53213	Attribute
53214	Attribute
53215	Attribute
53216	Attribute
53217	Attribute
53218	Attribute
53219	Attribute
53220	Attribute
53221	Attribute
53222	Attribute
53223	Attribute
53224	Attribute
53225	Attribute
53226	Attribute
53227	Attribute
53228	Attribute
53229	Attribute
53230	Attribute
53231	Attribute
53232	Attribute
53233	Attribute
53234	Attribute
53236	Entity
53237	Entity
53238	Entity
53239	Entity
53240	Entity
53241	Attribute
53242	Attribute
53243	Attribute
53244	Attribute
53245	Attribute
53246	Attribute
53247	Attribute
53248	Attribute
53249	Attribute
53250	Attribute
53251	Attribute
53252	Attribute
53253	Attribute
53254	Attribute
53255	Attribute
53256	Attribute
53257	Attribute
53258	Attribute
53259	Attribute
53260	Attribute
53261	Attribute
53262	Attribute
53263	Attribute
53264	Attribute
53265	Attribute
53266	Attribute
53267	Attribute
53268	Attribute
53269	Attribute
53270	Attribute
53271	Attribute
53272	Attribute
53274	Entity
53275	Entity
53276	Entity
53277	Entity
53278	Entity
53279	Entity
53280	Entity
53281	Entity
53282	Entity
53283	Attribute
53284	Attribute
53285	Attribute
53286	Attribute
53287	Attribute
53288	Attribute
53289	Attribute
53290	Attribute
53291	Attribute
53292	Attribute
53293	Attribute
53294	Attribute
53295	Attribute
53296	Attribute
53297	Attribute
53298	Attribute
53299	Attribute
53300	Attribute
53301	Attribute
53302	Attribute
53303	Attribute
53304	Attribute
53305	Attribute
53306	Attribute
53307	Attribute
53308	Attribute
53309	Attribute
53311	Entity
53312	Entity
53313	Entity
53314	Entity
53315	Entity
53316	Entity
53317	Entity
53318	Entity
53319	Entity
53320	Entity
53321	Entity
53322	Entity
53323	Entity
53324	Entity
53325	Attribute
53326	Attribute
53327	Attribute
53328	Attribute
53329	Attribute
53330	Attribute
53331	Attribute
53332	Attribute
53333	Attribute
53334	Attribute
53335	Attribute
53336	Attribute
53337	Attribute
53338	Attribute
53339	Attribute
53340	Attribute
53341	Attribute
53342	Attribute
53343	Attribute
53344	Attribute
53345	Attribute
53346	Attribute
53347	Attribute
53348	Attribute
53349	Attribute
53350	Attribute
53351	Attribute
53352	Attribute
53353	Attribute
53354	Attribute
53355	Attribute
53357	Entity
53358	Entity
53359	Entity
53360	Entity
53361	Entity
53362	Entity
53363	Entity
53364	Attribute
53365	Attribute
53366	Attribute
53367	Attribute
53368	Attribute
53369	Attribute
53370	Attribute
53371	Attribute
53372	Attribute
53373	Attribute
53374	Attribute
53375	Attribute
53376	Attribute
53377	Attribute
53378	Attribute
53379	Attribute
53380	Attribute
53381	Attribute
53382	Attribute
53383	Attribute
53384	Attribute
53385	Attribute
53386	Attribute
53387	Attribute
53388	Attribute
53389	Attribute
53391	Entity
53392	Entity
53393	Entity
53394	Entity
53395	Entity
53396	Entity
53397	Entity
53398	Entity
53399	Entity
53400	Entity
53401	Entity
53402	Entity
53403	Entity
53404	Entity
53405	Attribute
53406	Attribute
53407	Attribute
53408	Attribute
53409	Attribute
53410	Attribute
53411	Attribute
53412	Attribute
53413	Attribute
53414	Attribute
53415	Attribute
53416	Attribute
53417	Attribute
53418	Attribute
53419	Attribute
53420	Attribute
53421	Attribute
53422	Attribute
53423	Attribute
53424	Attribute
53425	Attribute
53426	Attribute
53427	Attribute
53428	Attribute
53429	Attribute
53430	Attribute
53431	Attribute
53432	Attribute
53433	Attribute
53434	Attribute
53435	Attribute
53436	Attribute
53437	Attribute
53438	Attribute
53439	Attribute
53440	Attribute
53441	Attribute
53442	Attribute
53443	Attribute
53444	Attribute
53445	Attribute
53446	Attribute
53447	Attribute
53448	Attribute
53449	Attribute
53450	Attribute
53451	Attribute
53452	Attribute
53453	Attribute
53454	Attribute
53455	Attribute
53456	Attribute
53457	Attribute
53458	Attribute
53459	Attribute
53460	Attribute
53461	Attribute
53462	Attribute
53463	Attribute
53464	Attribute
53465	Attribute
53466	Attribute
53467	Attribute
53468	Attribute
53469	Attribute
53470	Attribute
53471	Attribute
53473	Entity
53474	Entity
53475	Entity
53476	Entity
53477	Entity
53478	Entity
53479	Entity
53480	Entity
53481	Attribute
53482	Attribute
53483	Attribute
53484	Attribute
53485	Attribute
53486	Attribute
53487	Attribute
53488	Attribute
53489	Attribute
53490	Attribute
53491	Attribute
53492	Attribute
53493	Attribute
53494	Attribute
53495	Attribute
53496	Attribute
53497	Attribute
53498	Attribute
53499	Attribute
53500	Attribute
53501	Attribute
53502	Attribute
53503	Attribute
53504	Attribute
53505	Attribute
53506	Attribute
53507	Attribute
53508	Attribute
53510	Entity
53511	Entity
53512	Entity
53513	Entity
53514	Attribute
53515	Attribute
53516	Attribute
53517	Attribute
53518	Attribute
53519	Attribute
53520	Attribute
53521	Attribute
53522	Attribute
53523	Attribute
53524	Attribute
53525	Attribute
53526	Attribute
53527	Attribute
53528	Attribute
53529	Attribute
53530	Attribute
53531	Attribute
53532	Attribute
53533	Attribute
53534	Attribute
53535	Attribute
53536	Attribute
53537	Attribute
53538	Attribute
53539	Attribute
53540	Attribute
53542	Entity
53543	Entity
53544	Entity
53545	Entity
53546	Entity
53547	Attribute
53548	Attribute
53549	Attribute
53550	Attribute
53551	Attribute
53552	Attribute
53553	Attribute
53554	Attribute
53555	Attribute
53556	Attribute
53557	Attribute
53558	Attribute
53559	Attribute
53560	Attribute
53561	Attribute
53562	Attribute
53563	Attribute
53564	Attribute
53565	Attribute
53566	Attribute
53567	Attribute
53568	Attribute
53569	Attribute
53570	Attribute
53571	Attribute
53572	Attribute
53573	Attribute
53574	Attribute
53576	Entity
53577	Entity
53578	Entity
53579	Entity
53580	Entity
53581	Entity
53582	Entity
53583	Entity
53584	Entity
53585	Entity
53586	Entity
53587	Entity
53588	Entity
53589	Attribute
53590	Attribute
53591	Attribute
53592	Attribute
53593	Attribute
53594	Attribute
53595	Attribute
53596	Attribute
53597	Attribute
53598	Attribute
53599	Attribute
53600	Attribute
53601	Attribute
53602	Attribute
53603	Attribute
53604	Attribute
53605	Attribute
53606	Attribute
53607	Attribute
53608	Attribute
53609	Attribute
53610	Attribute
53611	Attribute
53612	Attribute
53613	Attribute
53614	Attribute
53615	Attribute
53616	Attribute
53617	Attribute
53618	Attribute
53619	Attribute
53620	Attribute
53621	Attribute
53622	Attribute
53623	Attribute
53624	Attribute
53625	Attribute
53626	Attribute
53627	Attribute
53628	Attribute
53629	Attribute
53630	Attribute
53631	Attribute
53632	Attribute
53633	Attribute
53634	Attribute
53635	Attribute
53637	Entity
53638	Entity
53639	Attribute
53640	Attribute
53641	Attribute
53642	Attribute
53643	Attribute
53644	Attribute
53645	Attribute
53646	Attribute
53647	Attribute
53648	Attribute
53649	Attribute
53651	Entity
53652	Entity
53653	Attribute
53654	Attribute
53655	Attribute
53656	Attribute
53657	Attribute
53658	Attribute
53659	Attribute
53660	Attribute
53661	Attribute
53662	Attribute
53663	Attribute
53664	Attribute
53666	Entity
53667	Entity
53668	Entity
53669	Entity
53670	Attribute
53671	Attribute
53672	Attribute
53673	Attribute
53674	Attribute
53675	Attribute
53676	Attribute
53677	Attribute
53678	Attribute
53679	Attribute
53680	Attribute
53681	Attribute
53682	Attribute
53683	Attribute
53684	Attribute
53686	Entity
53687	Entity
53688	Entity
53689	Entity
53690	Entity
53691	Entity
53692	Entity
53693	Attribute
53694	Attribute
53695	Attribute
53696	Attribute
53697	Attribute
53698	Attribute
53699	Attribute
53700	Attribute
53701	Attribute
53702	Attribute
53703	Attribute
53704	Attribute
53705	Attribute
53706	Attribute
53707	Attribute
53708	Attribute
53709	Attribute
53710	Attribute
53711	Attribute
53712	Attribute
53713	Attribute
53714	Attribute
53715	Attribute
53716	Attribute
53717	Attribute
53718	Attribute
53719	Attribute
53720	Attribute
53721	Attribute
53722	Attribute
53724	Entity
53725	Entity
53726	Entity
53727	Entity
53728	Entity
53729	Attribute
53730	Attribute
53731	Attribute
53732	Attribute
53733	Attribute
53734	Attribute
53735	Attribute
53736	Attribute
53737	Attribute
53738	Attribute
53739	Attribute
53740	Attribute
53741	Attribute
53743	Entity
53744	Entity
53745	Entity
53746	Attribute
53747	Attribute
53748	Attribute
53749	Attribute
53750	Attribute
53751	Attribute
53752	Attribute
53753	Attribute
53754	Attribute
53755	Attribute
53756	Attribute
53757	Attribute
53759	Entity
53760	Entity
53761	Entity
53762	Entity
53763	Entity
53764	Attribute
53765	Attribute
53766	Attribute
53767	Attribute
53768	Attribute
53769	Attribute
53770	Attribute
53771	Attribute
53772	Attribute
53773	Attribute
53774	Attribute
53775	Attribute
53776	Attribute
53777	Attribute
53778	Attribute
53779	Attribute
53781	Entity
53782	Entity
53783	Entity
53784	Entity
53785	Entity
53786	Attribute
53787	Attribute
53788	Attribute
53789	Attribute
53790	Attribute
53791	Attribute
53792	Attribute
53793	Attribute
53794	Attribute
53795	Attribute
53797	Entity
53798	Entity
53799	Attribute
53800	Attribute
53801	Attribute
53802	Attribute
53803	Attribute
53804	Attribute
53805	Attribute
53806	Attribute
53807	Attribute
53808	Attribute
53809	Attribute
53810	Attribute
53812	Entity
53813	Entity
53814	Entity
53815	Entity
53816	Entity
53817	Entity
53818	Attribute
53819	Attribute
53820	Attribute
53821	Attribute
53822	Attribute
53823	Attribute
53824	Attribute
53825	Attribute
53826	Attribute
53827	Attribute
53828	Attribute
53829	Attribute
53831	Entity
53832	Entity
53833	Entity
53834	Entity
53835	Entity
53836	Attribute
53837	Attribute
53838	Attribute
53839	Attribute
53840	Attribute
53841	Attribute
53842	Attribute
53843	Attribute
53844	Attribute
53845	Attribute
53846	Attribute
53847	Attribute
53848	Attribute
53850	Entity
53851	Entity
53852	Entity
53853	Entity
53854	Attribute
53855	Attribute
53856	Attribute
53857	Attribute
53858	Attribute
53859	Attribute
53860	Attribute
53861	Attribute
53862	Attribute
53863	Attribute
53864	Attribute
53865	Attribute
53866	Attribute
53867	Attribute
53868	Attribute
53869	Attribute
53870	Attribute
53872	Entity
53873	Entity
53874	Entity
53875	Entity
53876	Entity
53877	Attribute
53878	Attribute
53879	Attribute
53880	Attribute
53881	Attribute
53882	Attribute
53883	Attribute
53884	Attribute
53885	Attribute
53886	Attribute
53887	Attribute
53888	Attribute
53889	Attribute
53891	Entity
53892	Entity
53893	Attribute
53894	Attribute
53895	Attribute
53896	Attribute
53897	Attribute
53898	Attribute
53899	Attribute
53900	Attribute
53901	Attribute
53903	Entity
53904	Entity
53905	Attribute
53906	Attribute
53907	Attribute
53908	Attribute
53909	Attribute
53910	Attribute
53911	Attribute
53912	Attribute
53913	Attribute
53914	Attribute
53915	Attribute
53917	Entity
53918	Entity
53919	Entity
53920	Entity
53921	Entity
53922	Entity
53923	Entity
53924	Attribute
53925	Attribute
53926	Attribute
53927	Attribute
53928	Attribute
53929	Attribute
53930	Attribute
53931	Attribute
53932	Attribute
53933	Attribute
53934	Attribute
53935	Attribute
53936	Attribute
53937	Attribute
53938	Attribute
53939	Attribute
53940	Attribute
53942	Entity
53943	Entity
53944	Entity
53945	Attribute
53946	Attribute
53947	Attribute
53948	Attribute
53949	Attribute
53950	Attribute
53951	Attribute
53952	Attribute
53953	Attribute
53954	Attribute
53955	Attribute
53956	Attribute
53957	Attribute
53958	Attribute
53959	Attribute
53960	Attribute
53961	Attribute
53962	Attribute
53963	Attribute
53964	Attribute
53965	Attribute
53967	Entity
53968	Entity
53969	Entity
53970	Entity
53971	Entity
53972	Attribute
53973	Attribute
53974	Attribute
53975	Attribute
53976	Attribute
53977	Attribute
53978	Attribute
53979	Attribute
53980	Attribute
53981	Attribute
53982	Attribute
53983	Attribute
53985	Entity
53986	Entity
53987	Entity
53988	Attribute
53989	Attribute
53990	Attribute
53991	Attribute
53992	Attribute
53993	Attribute
53994	Attribute
53995	Attribute
53996	Attribute
53997	Attribute
53998	Attribute
53999	Attribute
54000	Attribute
54001	Attribute
54002	Attribute
54003	Attribute
54004	Attribute
54005	Attribute
54006	Attribute
54007	Attribute
54008	Attribute
54010	Entity
54011	Entity
54012	Entity
54013	Entity
54014	Entity
54015	Entity
54016	Entity
54017	Attribute
54018	Attribute
54019	Attribute
54020	Attribute
54021	Attribute
54022	Attribute
54023	Attribute
54024	Attribute
54025	Attribute
54026	Attribute
54027	Attribute
54028	Attribute
54030	Entity
54031	Entity
54032	Entity
54033	Entity
54034	Entity
54035	Attribute
54036	Attribute
54037	Attribute
54038	Attribute
54039	Attribute
54040	Attribute
54041	Attribute
54042	Attribute
54043	Attribute
54044	Attribute
54045	Attribute
54046	Attribute
54047	Attribute
54049	Entity
54050	Entity
54051	Entity
54052	Entity
54053	Attribute
54054	Attribute
54055	Attribute
54056	Attribute
54057	Attribute
54058	Attribute
54059	Attribute
54060	Attribute
54061	Attribute
54062	Attribute
54063	Attribute
54064	Attribute
54065	Attribute
54066	Attribute
54067	Attribute
54068	Attribute
54069	Attribute
54070	Attribute
54071	Attribute
54072	Attribute
54073	Attribute
54075	Entity
54076	Entity
54077	Entity
54078	Attribute
54079	Attribute
54080	Attribute
54081	Attribute
54082	Attribute
54083	Attribute
54084	Attribute
54085	Attribute
54086	Attribute
54087	Attribute
54088	Attribute
54089	Attribute
54090	Attribute
54091	Attribute
54092	Attribute
54094	Entity
54095	Entity
54096	Entity
54097	Attribute
54098	Attribute
54099	Attribute
54100	Attribute
54101	Attribute
54102	Attribute
54103	Attribute
54104	Attribute
54105	Attribute
54106	Attribute
54108	Entity
54109	Entity
54110	Attribute
54111	Attribute
54112	Attribute
54113	Attribute
54114	Attribute
54115	Attribute
54116	Attribute
54117	Attribute
54118	Attribute
54119	Attribute
54120	Attribute
54121	Attribute
54123	Entity
54124	Entity
54125	Entity
54126	Entity
54127	Entity
54128	Entity
54129	Attribute
54130	Attribute
54131	Attribute
54132	Attribute
54133	Attribute
54134	Attribute
54135	Attribute
54136	Attribute
54137	Attribute
54138	Attribute
54139	Attribute
54140	Attribute
54141	Attribute
54142	Attribute
54143	Attribute
54144	Attribute
54146	Entity
54147	Attribute
54148	Attribute
54149	Attribute
54150	Attribute
54152	Entity
54153	Attribute
54154	Attribute
54155	Attribute
54156	Attribute
54157	Attribute
54158	Attribute
54159	Attribute
54160	Attribute
54161	Attribute
54162	Attribute
54164	Entity
54165	Attribute
54166	Attribute
54167	Attribute
54168	Attribute
54169	Attribute
54170	Attribute
54171	Attribute
54172	Attribute
54174	Entity
54175	Attribute
54176	Attribute
54177	Attribute
54178	Attribute
54179	Attribute
54180	Attribute
54181	Attribute
54182	Attribute
54183	Attribute
54184	Attribute
54185	Attribute
54186	Attribute
54187	Attribute
54188	Attribute
54190	Entity
54191	Attribute
54192	Attribute
54193	Attribute
54194	Attribute
54195	Attribute
54196	Attribute
54197	Attribute
54198	Attribute
54199	Attribute
54200	Attribute
54201	Attribute
54202	Attribute
54203	Attribute
54204	Attribute
54205	Attribute
54206	Attribute
54207	Attribute
54208	Attribute
54209	Attribute
54210	Attribute
54211	Attribute
54212	Attribute
54213	Attribute
54214	Attribute
54216	Entity
54217	Attribute
54218	Attribute
54219	Attribute
54220	Attribute
54221	Attribute
54222	Attribute
54223	Attribute
54224	Attribute
54225	Attribute
54227	Entity
54228	Attribute
54229	Attribute
54230	Attribute
54231	Attribute
54232	Attribute
54233	Attribute
54234	Attribute
54235	Attribute
54237	Entity
54238	Attribute
54239	Attribute
54240	Attribute
54241	Attribute
54242	Attribute
54243	Attribute
54244	Attribute
54245	Attribute
54246	Attribute
54247	Attribute
54248	Attribute
54249	Attribute
54250	Attribute
54251	Attribute
54252	Attribute
54253	Attribute
54254	Attribute
54255	Attribute
54256	Attribute
54257	Attribute
54258	Attribute
54259	Attribute
54260	Attribute
54261	Attribute
54263	Entity
54264	Attribute
54265	Attribute
54266	Attribute
54267	Attribute
54268	Attribute
54269	Attribute
54271	Entity
54272	Attribute
54273	Attribute
54274	Attribute
54275	Attribute
54276	Attribute
54277	Attribute
54278	Attribute
54279	Attribute
54280	Attribute
54281	Attribute
54282	Attribute
54283	Attribute
54284	Attribute
54285	Attribute
54286	Attribute
54287	Attribute
54288	Attribute
54290	Entity
54291	Attribute
54292	Attribute
54293	Attribute
54294	Attribute
54295	Attribute
54296	Attribute
54297	Attribute
54298	Attribute
54299	Attribute
54300	Attribute
54301	Attribute
54302	Attribute
54303	Attribute
54305	Entity
54306	Attribute
54307	Attribute
54308	Attribute
54309	Attribute
54310	Attribute
54311	Attribute
54313	Entity
54314	Attribute
54315	Attribute
54316	Attribute
54317	Attribute
54318	Attribute
54319	Attribute
54321	Entity
54322	Attribute
54323	Attribute
54324	Attribute
54325	Attribute
54326	Attribute
54327	Attribute
54328	Attribute
54329	Attribute
54330	Attribute
54331	Attribute
54332	Attribute
54333	Attribute
54334	Attribute
54335	Attribute
54336	Attribute
54337	Attribute
54338	Attribute
54339	Attribute
54340	Attribute
54341	Attribute
54343	Entity
54344	Attribute
54345	Attribute
54346	Attribute
54347	Attribute
54348	Attribute
54349	Attribute
54350	Attribute
54351	Attribute
54352	Attribute
54353	Attribute
54354	Attribute
54355	Attribute
54356	Attribute
54357	Attribute
54359	Entity
54360	Attribute
54361	Attribute
54362	Attribute
54363	Attribute
54364	Attribute
54365	Attribute
54366	Attribute
54367	Attribute
54368	Attribute
54369	Attribute
54370	Attribute
54371	Attribute
54373	Entity
54374	Attribute
54375	Attribute
54376	Attribute
54377	Attribute
54378	Attribute
54379	Attribute
54380	Attribute
54381	Attribute
54382	Attribute
54383	Attribute
54384	Attribute
54385	Attribute
54386	Attribute
54388	Entity
54389	Attribute
54390	Attribute
54391	Attribute
54392	Attribute
54393	Attribute
54394	Attribute
54395	Attribute
54396	Attribute
54397	Attribute
54398	Attribute
54399	Attribute
54400	Attribute
54401	Attribute
54402	Attribute
54403	Attribute
54404	Attribute
54405	Attribute
54406	Attribute
54407	Attribute
54408	Attribute
54409	Attribute
54410	Attribute
54411	Attribute
54412	Attribute
54413	Attribute
54414	Attribute
54416	Entity
54417	Attribute
54418	Attribute
54419	Attribute
54421	Entity
54422	Attribute
54423	Attribute
54424	Attribute
54425	Attribute
54426	Attribute
54427	Attribute
54428	Attribute
54429	Attribute
54430	Attribute
54431	Attribute
54432	Attribute
54434	Entity
54435	Attribute
54436	Attribute
54437	Attribute
54438	Attribute
54439	Attribute
54440	Attribute
54441	Attribute
54442	Attribute
54443	Attribute
54444	Attribute
54445	Attribute
54446	Attribute
54448	Entity
54449	Attribute
54450	Attribute
54451	Attribute
54452	Attribute
54453	Attribute
54454	Attribute
54455	Attribute
54456	Attribute
54457	Attribute
54458	Attribute
54459	Attribute
54460	Attribute
54461	Attribute
54462	Attribute
54463	Attribute
54464	Attribute
54465	Attribute
54466	Attribute
54467	Attribute
54468	Attribute
54469	Attribute
54470	Attribute
54471	Attribute
54472	Attribute
54473	Attribute
54474	Attribute
54475	Attribute
54476	Attribute
54477	Attribute
54479	Entity
54480	Attribute
54481	Attribute
54482	Attribute
54483	Attribute
54484	Attribute
54485	Attribute
54486	Attribute
54487	Attribute
54488	Attribute
54489	Attribute
54490	Attribute
54491	Attribute
54492	Attribute
54493	Attribute
54494	Attribute
54495	Attribute
54496	Attribute
54497	Attribute
54498	Attribute
54500	Entity
54501	Attribute
54502	Attribute
54503	Attribute
54504	Attribute
54506	Entity
54507	Attribute
54508	Attribute
54509	Attribute
54510	Attribute
54511	Attribute
54512	Attribute
54513	Attribute
54514	Attribute
54515	Attribute
54516	Attribute
54518	Entity
54519	Attribute
54520	Attribute
54521	Attribute
54522	Attribute
54523	Attribute
54524	Attribute
54525	Attribute
54526	Attribute
54527	Attribute
54528	Attribute
54529	Attribute
54530	Attribute
54532	Entity
54533	Attribute
54534	Attribute
54535	Attribute
54536	Attribute
54537	Attribute
54538	Attribute
54539	Attribute
54540	Attribute
54541	Attribute
54542	Attribute
54544	Entity
54545	Attribute
54546	Attribute
54547	Attribute
54548	Attribute
54549	Attribute
54550	Attribute
54551	Attribute
54552	Attribute
54553	Attribute
54554	Attribute
54556	Entity
54557	Attribute
54558	Attribute
54559	Attribute
54560	Attribute
54561	Attribute
54562	Attribute
54563	Attribute
54564	Attribute
54565	Attribute
54566	Attribute
54567	Attribute
54568	Attribute
54569	Attribute
54570	Attribute
54571	Attribute
54572	Attribute
54573	Attribute
54574	Attribute
54575	Attribute
54576	Attribute
54577	Attribute
54578	Attribute
54579	Attribute
54580	Attribute
54581	Attribute
54582	Attribute
54583	Attribute
54584	Attribute
54585	Attribute
54586	Attribute
54587	Attribute
54588	Attribute
54589	Attribute
54590	Attribute
54591	Attribute
54592	Attribute
54593	Attribute
54594	Attribute
54595	Attribute
54596	Attribute
54597	Attribute
54598	Attribute
54599	Attribute
54600	Attribute
54601	Attribute
54602	Attribute
54603	Attribute
54604	Attribute
54605	Attribute
54606	Attribute
54607	Attribute
54608	Attribute
54609	Attribute
54610	Attribute
54611	Attribute
54612	Attribute
54613	Attribute
54614	Attribute
54615	Attribute
54616	Attribute
54618	Entity
54619	Attribute
54620	Attribute
54621	Attribute
54622	Attribute
54623	Attribute
54624	Attribute
54625	Attribute
54626	Attribute
54627	Attribute
54628	Attribute
54629	Attribute
54630	Attribute
54631	Attribute
54632	Attribute
54633	Attribute
54634	Attribute
54635	Attribute
54637	Entity
54638	Attribute
54639	Attribute
54640	Attribute
54641	Attribute
54643	Entity
54644	Attribute
54645	Attribute
54646	Attribute
54648	Entity
54649	Attribute
54650	Attribute
54651	Attribute
54653	Entity
54654	Attribute
54655	Attribute
54656	Attribute
54657	Attribute
54658	Attribute
54659	Attribute
54661	Entity
54662	Attribute
54663	Attribute
54664	Attribute
54665	Attribute
54666	Attribute
54668	Entity
54669	Attribute
54670	Attribute
54671	Attribute
54672	Attribute
54673	Attribute
54675	Entity
54676	Attribute
54677	Attribute
54678	Attribute
54679	Attribute
54680	Attribute
54681	Attribute
54683	Entity
54684	Attribute
54685	Attribute
54686	Attribute
54687	Attribute
54688	Attribute
54689	Attribute
54690	Attribute
54691	Attribute
54692	Attribute
54693	Attribute
54694	Attribute
54695	Attribute
54697	Entity
54698	Attribute
54699	Attribute
54700	Attribute
54701	Attribute
54703	Entity
54704	Attribute
54705	Attribute
54706	Attribute
54707	Attribute
54708	Attribute
54710	Entity
54711	Attribute
54712	Attribute
54713	Attribute
54714	Attribute
54715	Attribute
54716	Attribute
54717	Attribute
54718	Attribute
54719	Attribute
54720	Attribute
54721	Attribute
54722	Attribute
54723	Attribute
54724	Attribute
54725	Attribute
54726	Attribute
54727	Attribute
54728	Attribute
54729	Attribute
54730	Attribute
54732	Entity
54733	Attribute
54734	Attribute
54735	Attribute
54736	Attribute
54737	Attribute
54738	Attribute
54739	Attribute
54740	Attribute
54742	Entity
54743	Attribute
54744	Attribute
54745	Attribute
54747	Entity
54748	Attribute
54749	Attribute
54750	Attribute
54751	Attribute
54752	Attribute
54753	Attribute
54755	Entity
54756	Attribute
54757	Attribute
54758	Attribute
54759	Attribute
54760	Attribute
54761	Attribute
54762	Attribute
54763	Attribute
54765	Entity
54766	Attribute
54767	Attribute
54768	Attribute
54769	Attribute
54770	Attribute
54771	Attribute
54772	Attribute
54773	Attribute
54774	Attribute
54776	Entity
54777	Attribute
54778	Attribute
54779	Attribute
54780	Attribute
54781	Attribute
54782	Attribute
54784	Entity
54785	Attribute
54786	Attribute
54787	Attribute
54788	Attribute
54789	Attribute
54790	Attribute
54791	Attribute
54793	Entity
54794	Attribute
54795	Attribute
54796	Attribute
54797	Attribute
54798	Attribute
54799	Attribute
54800	Attribute
54801	Attribute
54802	Attribute
54803	Attribute
54805	Entity
54806	Attribute
54807	Attribute
54808	Attribute
54809	Attribute
54810	Attribute
54811	Attribute
54813	Entity
54814	Attribute
54815	Attribute
54816	Attribute
54817	Attribute
54818	Attribute
54819	Attribute
54820	Attribute
54821	Attribute
54822	Attribute
54823	Attribute
54824	Attribute
54825	Attribute
54826	Attribute
54827	Attribute
54829	Entity
54830	Attribute
54831	Attribute
54832	Attribute
54833	Attribute
54834	Attribute
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

