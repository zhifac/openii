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

SELECT pg_catalog.setval('universalseq', 55232, true);


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
52767	Type		52765	-3	1	1
52768	MaxNumEmployees		52764	-1	1	1
52769	CurrentNumCDs		52764	-1	1	1
52770	Manager		52764	-3	1	1
52771	Artist		52765	-3	1	1
52772	Keywords		52766	-3	1	1
52773	Author		52766	-3	1	1
52774	WarehouseID		52763	-1	1	1
52775	AlbumName		52765	-3	1	1
52776	CDCapacity		52764	-1	1	1
52777	Quantity		52763	-1	1	1
52778	Rating		52765	-1	1	1
52779	Genre		52765	-3	1	1
52780	DiscountPrice		52765	-2	1	1
52781	Publisher		52766	-3	1	1
52782	TelephoneNumber		52764	-1	1	1
52783	CurrentNumEmployees		52764	-1	1	1
52784	RecordingCompany		52765	-3	1	1
52785	CurrentNumBooks		52764	-1	1	1
52786	FaxNumber		52764	-1	1	1
52787	Price		52765	-2	1	1
52788	ISBN		52766	-1	1	1
52789	Category		52766	-3	1	1
52790	Title		52763	-3	1	1
52791	BookCapacity		52764	-1	1	1
52792	Location		52764	-3	1	1
52793	ID		52763	-1	1	1
52804	Type		52801	-3	1	1
52805	ASIN		52801	-3	1	1
52806	Telephone		52796	-3	1	1
52807	ZipCode		52796	-1	1	1
52808	City		52796	-3	1	1
52809	Edition		52802	-3	1	1
52810	Description		52799	-3	1	1
52811	Fax		52796	-3	1	1
52812	FirstName		52798	-3	1	1
52813	StreetAddress		52796	-3	1	1
52814	Quantity		52795	-1	1	1
52815	DiscountPrice		52799	-2	1	1
52816	Publisher		52802	-3	1	1
52817	State		52796	-3	1	1
52818	ItemType		52795	-3	1	1
52819	Studio		52799	-3	1	1
52820	ItemId		52795	-3	1	1
52821	WarehouseId		52795	-1	1	1
52822	ManagerName		52796	-3	1	1
52823	Price		52799	-2	1	1
52824	ISBN		52800	-3	1	1
52825	GroupName		52798	-3	1	1
52826	CopyrightYear		52802	-1	1	1
52827	NumberOfEmployees		52796	-1	1	1
52828	ReleaseDate		52799	-4	1	1
52829	Country		52796	-3	1	1
52830	Category		52797	-3	1	1
52831	Title		52799	-3	1	1
52832	LastName		52798	-3	1	1
52846	item		52844	-3	1	1
52847	phone		52834	-3	1	1
52848	startDate		52844	-4	1	1
52849	cdid		52840	-3	1	1
52850	title		52838	-3	1	1
52851	publisher		52841	-1	1	1
52852	middleInitial		52843	-1	1	1
52853	firstName		52843	-3	1	1
52854	id		52843	-3	1	1
52855	mainContact		52834	-3	1	1
52856	mobilePhone		52843	-3	1	1
52857	keyword		52839	-3	1	1
52858	studio		52838	-1	1	1
52859	price		52844	-2	1	1
52860	quantity		52840	-1	1	1
52861	name		52834	-3	1	1
52862	warehouseID		52842	-3	1	1
52863	city		52834	-3	1	1
52864	datePublished		52838	-4	1	1
52865	publisherID		52837	-3	1	1
52866	warehouse		52840	-3	1	1
52867	standardPrice		52838	-2	1	1
52868	studioID		52834	-3	1	1
52869	tracks		52838	-1	1	1
52870	streetAdderss		52834	-3	1	1
52871	lastName		52843	-3	1	1
52872	managerName		52842	-3	1	1
52873	employees		52842	-1	1	1
52874	ISBN		52835	-3	1	1
52875	deliveryCharge		52842	-2	1	1
52876	pages		52841	-1	1	1
52877	cd		52845	-3	1	1
52878	book		52836	-3	1	1
52879	streetAddress		52842	-3	1	1
52880	state		52842	-3	1	1
52881	endDate		52844	-4	1	1
52882	officePhone		52843	-3	1	1
52883	genre		52838	-3	1	1
52884	zipcode		52834	-1	1	1
52885	author		52845	-6	1	1
52886	ID		52838	-3	1	1
52896	capacity		52890	-1	1	1
52897	phone		52890	-1	1	1
52898	WH#		52888	-1	1	1
52899	numDiscs		52894	-1	1	1
52900	type		52894	-1	1	1
52901	title		52895	-1	1	1
52902	album		52894	-1	1	1
52903	numPages		52895	-1	1	1
52904	manager		52890	-1	1	1
52905	publisher		52895	-1	1	1
52906	recordLabel		52894	-1	1	1
52907	fax		52890	-1	1	1
52908	Explicit		52894	-6	1	1
52909	discountPrice		52889	-2	1	1
52910	keyword		52892	-1	1	1
52911	PID		52888	-1	1	1
52912	price		52889	-2	1	1
52913	location		52890	-1	1	1
52914	genre		52894	-1	1	1
52915	quantity		52888	-1	1	1
52916	name		52893	-1	1	1
52923	Trucks		52918	-1	1	1
52924	ProductID		52919	-1	1	1
52925	Genre		52920	-3	1	1
52926	WarehouseName		52919	-3	1	1
52927	InStock		52919	-3	1	1
52928	Manager		52918	-3	1	1
52929	Author		52921	-3	1	1
52930	Artist		52920	-3	1	1
52931	City		52918	-3	1	1
52932	StaffSize		52918	-1	1	1
52933	StorageRating		52918	-1	1	1
52934	ExpectedInStock		52919	-4	1	1
52935	CustomerRating		52920	-1	1	1
52936	Album		52920	-3	1	1
52937	FaxNumber		52918	-1	1	1
52938	UniversalID		52920	-1	1	1
52939	Price		52920	-1	1	1
52940	ISBN		52921	-1	1	1
52941	PhoneNumber		52918	-1	1	1
52942	ProductType		52922	-3	1	1
52943	ZIP		52918	-1	1	1
52944	StreetAddress		52918	-3	1	1
52945	Title		52921	-3	1	1
52946	Quantity		52919	-1	1	1
52957	Type		52953	-1	1	1
52958	Capacity		52955	-1	1	1
52959	WarehouseName		52948	-1	1	1
52960	BookISBN		52954	-1	1	1
52961	Keywords		52956	-1	1	1
52962	AlbumName		52953	-1	1	1
52963	Year		52953	-1	1	1
52964	Edition		52956	-1	1	1
52965	AuthorName		52954	-1	1	1
52966	CDNumber		52949	-1	1	1
52967	Name		52955	-1	1	1
52968	QuantityInStock		52948	-1	1	1
52969	ArtistName		52949	-1	1	1
52970	DiscountPrice		52952	-2	1	1
52971	Genre		52953	-1	1	1
52972	Publisher		52956	-1	1	1
52973	ProductNumber		52953	-1	1	1
52974	RecordingCompany		52953	-1	1	1
52975	ISBN		52956	-1	1	1
52976	Price		52953	-2	1	1
52977	Contact		52950	-1	1	1
52978	Number		52951	-1	1	1
52979	CopyWrite		52956	-1	1	1
52980	Category		52956	-1	1	1
52981	Title		52956	-1	1	1
52982	Location		52955	-1	1	1
52994	phone		52992	-1	1	1
52995	cdType		52993	-1	1	1
52996	title		52991	-1	1	1
52997	buissnessVol		52988	-2	1	1
52998	productID		52985	-1	1	1
52999	address		52992	-1	1	1
53000	freeSpace		52988	-2	1	1
53001	albumName		52993	-1	1	1
53002	fax		52992	-1	1	1
53003	company		52993	-1	1	1
53004	artistID		52990	-1	1	1
53005	houseID		52988	-1	1	1
53006	keyword		52989	-1	1	1
53007	priceType		52985	-1	1	1
53008	price		52985	-2	1	1
53009	zip		52992	-1	1	1
53010	name		52990	-1	1	1
53011	quantity		52984	-1	1	1
53012	city		52992	-1	1	1
53013	productCatagory		52987	-1	1	1
53014	genreType		52986	-1	1	1
53015	numEmployees		52988	-1	1	1
53016	managerName		52992	-1	1	1
53017	ISBN		52991	-1	1	1
53018	state		52992	-1	1	1
53030	phone		53028	-1	1	1
53031	word		53027	-1	1	1
53032	title		53021	-1	1	1
53033	type		53021	-1	1	1
53034	ASIN		53023	-1	1	1
53035	productID		53020	-1	1	1
53036	manager		53028	-1	1	1
53037	artist		53023	-1	1	1
53038	fax		53028	-1	1	1
53039	discountPrice		53021	-2	1	1
53040	ISBN		53027	-1	1	1
53041	price		53021	-2	1	1
53042	streetAddress		53024	-1	1	1
53043	state		53024	-1	1	1
53044	quantity		53020	-1	1	1
53045	warehouseID		53020	-1	1	1
53046	name		53022	-1	1	1
53047	zipcode		53024	-1	1	1
53048	city		53024	-1	1	1
53049	author		53029	-1	1	1
53058	itemNum		53054	-1	1	1
53059	phone		53053	-1	1	1
53060	authorArtist		53054	-1	1	1
53061	salePrice		53057	-2	1	1
53062	type		53056	-1	1	1
53063	NunOfDiscs		53056	-1	1	1
53064	suggestion		53052	-1	1	1
53065	category		53057	-1	1	1
53066	address		53053	-1	1	1
53067	productCap		53053	-1	1	1
53068	publisher		53057	-1	1	1
53069	manager		53053	-1	1	1
53070	fax		53053	-1	1	1
53071	cover		53057	-1	1	1
53072	keyword		53055	-1	1	1
53073	employeeCap		53053	-1	1	1
53074	label		53056	-1	1	1
53075	price		53052	-2	1	1
53076	warehouseNum		53051	-1	1	1
53077	genre		53056	-1	1	1
53078	name		53052	-1	1	1
53079	quantity		53051	-1	1	1
53080	review		53052	-1	1	1
53085	Publisher		53083	-3	1	1
53086	MaxEmployees		53084	-1	1	1
53087	State		53084	-3	1	1
53088	Manager		53084	-3	1	1
53089	TelephoneNumber		53084	-3	1	1
53090	ItemType		53083	-3	1	1
53091	Zip		53084	-3	1	1
53092	OurPrice		53083	-2	1	1
53093	CurCapacity		53084	-1	1	1
53094	Author		53083	-3	1	1
53095	Keywords		53083	-3	1	1
53096	WarehouseID		53082	-3	1	1
53097	ItemID		53082	-3	1	1
53098	City		53084	-3	1	1
53099	CurEmployees		53084	-1	1	1
53100	Amount		53082	-1	1	1
53101	FaxNumber		53084	-3	1	1
53102	Description		53083	-3	1	1
53103	Address		53084	-3	1	1
53104	MaxCapacity		53084	-1	1	1
53105	Category		53083	-3	1	1
53106	Title		53083	-3	1	1
53107	SuggestedPrice		53083	-2	1	1
53116	Type		53109	-1	1	1
53117	DiscountPrice		53114	-2	1	1
53118	ProdCode		53113	-2	1	1
53119	State		53115	-1	1	1
53120	Telephone		53115	-1	1	1
53121	Manager		53115	-1	1	1
53122	ZipCode		53115	-1	1	1
53123	FAX		53115	-1	1	1
53124	City		53115	-1	1	1
53125	StreetAddr		53115	-1	1	1
53126	CAPACITY		53115	-1	1	1
53127	ISBN		53110	-2	1	1
53128	Price		53109	-2	1	1
53129	FirstName		53112	-1	1	1
53130	Category		53109	-1	1	1
53131	Title		53109	-1	1	1
53132	Name		53111	-1	1	1
53133	Quantity		53110	-1	1	1
53134	MiddleName		53112	-1	1	1
53135	WH_ID		53110	-1	1	1
53136	LastName		53112	-1	1	1
53153	estCapVolBiz		53138	-1	1	1
53154	title		53147	-1	1	1
53155	genreID		53142	-1	1	1
53156	telephone		53138	-1	1	1
53157	manager		53138	-1	1	1
53158	fax		53138	-1	1	1
53159	artistID		53139	-1	1	1
53160	authorEmailAddress		53152	-1	1	1
53161	keyword		53144	-1	1	1
53162	price		53149	-2	1	1
53163	categoryName		53140	-1	1	1
53164	authorID		53148	-1	1	1
53165	name		53149	-1	1	1
53166	warehouseID		53146	-1	1	1
53167	city		53138	-1	1	1
53168	stock		53146	-1	1	1
53169	recordingCompany		53149	-1	1	1
53170	musicID		53146	-1	1	1
53171	discounted		53147	-2	1	1
53172	type		53145	-1	1	1
53173	stAddress		53138	-1	1	1
53174	artist		53141	-1	1	1
53175	managerEmailAddress		53138	-1	1	1
53176	typeID		53149	-1	1	1
53177	ISBN		53150	-1	1	1
53178	artistEmailAddress		53141	-1	1	1
53179	estCapNumEmp		53138	-1	1	1
53180	recordingCompanyEmailAddress		53149	-1	1	1
53181	categoryID		53143	-1	1	1
53182	state		53138	-1	1	1
53183	genre		53151	-1	1	1
53184	zipcode		53138	-1	1	1
53185	author		53152	-1	1	1
53195	Type		53194	-1	1	1
53196	Capacity		53189	-1	1	1
53197	NumEmployees		53189	-1	1	1
53198	Manager		53189	-1	1	1
53199	Artist		53192	-1	1	1
53200	Explicit		53194	-6	1	1
53201	RecordLabel		53194	-1	1	1
53202	AuthorName		53190	-1	1	1
53203	Fax		53189	-1	1	1
53204	Quantity		53187	-1	1	1
53205	DiscountPrice		53188	-2	1	1
53206	Genre		53194	-1	1	1
53207	Publisher		53193	-1	1	1
53208	Keyword		53191	-1	1	1
53209	WHNum		53187	-1	1	1
53210	Phone		53189	-1	1	1
53211	Album		53194	-1	1	1
53212	NumCDs		53194	-1	1	1
53213	Price		53188	-2	1	1
53214	ISBN		53191	-1	1	1
53215	Category		53193	-1	1	1
53216	Title		53193	-1	1	1
53217	Location		53189	-1	1	1
53218	ID		53187	-1	1	1
53219	NumPages		53193	-1	1	1
53227	volBusiness		53222	-2	1	1
53228	title		53225	-1	1	1
53229	telephone		53223	-1	1	1
53230	managername		53223	-1	1	1
53231	fax		53223	-1	1	1
53232	warehousecode		53223	-1	1	1
53233	company		53221	-1	1	1
53234	price		53221	-2	1	1
53235	zip		53226	-1	1	1
53236	location		53226	-1	1	1
53237	name		53221	-1	1	1
53238	quantity		53224	-1	1	1
53239	stock		53224	-1	1	1
53240	city		53226	-1	1	1
53241	bookcategory		53225	-1	1	1
53242	capacity		53222	-2	1	1
53243	CISBN		53224	-1	1	1
53244	types		53221	-1	1	1
53245	keywords		53225	-1	1	1
53246	discprice		53225	-2	1	1
53247	category		53221	-1	1	1
53248	artist		53221	-1	1	1
53249	ISBN		53221	-1	1	1
53250	BISBN		53224	-1	1	1
53251	numEmployee		53222	-1	1	1
53252	streetAddress		53226	-1	1	1
53253	state		53226	-1	1	1
53254	author		53225	-1	1	1
53262	Type		53257	-1	1	1
53263	WareHouseCode		53260	-1	1	1
53264	Stock		53256	-1	1	1
53265	NumEmployees		53261	-1	1	1
53266	Telephone		53258	-1	1	1
53267	ZipCode		53260	-1	1	1
53268	Artist		53257	-1	1	1
53269	City		53260	-1	1	1
53270	AuthorName		53259	-1	1	1
53271	KeyWord		53259	-1	1	1
53272	price		53257	-1	1	1
53273	StreetAddress		53260	-1	1	1
53274	name		53257	-1	1	1
53275	Quantity		53256	-1	1	1
53276	State		53260	-1	1	1
53277	ISBook		53256	-1	1	1
53278	BookCategory		53259	-1	1	1
53279	ManagerName		53258	-1	1	1
53280	Price		53259	-1	1	1
53281	ISBN		53257	-1	1	1
53282	IsCD		53256	-1	1	1
53283	Category		53257	-1	1	1
53284	Volume		53261	-1	1	1
53285	Company		53257	-1	1	1
53286	Title		53259	-1	1	1
53287	DisPrice		53259	-1	1	1
53301	title		53297	-1	1	1
53302	genreID		53296	-1	1	1
53303	address		53289	-1	1	1
53304	telephone		53289	-1	1	1
53305	fax		53289	-1	1	1
53306	albumName		53296	-1	1	1
53307	firstName		53295	-1	1	1
53308	artistID		53300	-1	1	1
53309	price		53296	-2	1	1
53310	authorID		53290	-1	1	1
53311	quantity		53299	-1	1	1
53312	warehouseID		53299	-1	1	1
53313	city		53289	-1	1	1
53314	employmentCount		53289	-1	1	1
53315	genreName		53292	-1	1	1
53316	musicID		53299	-1	1	1
53317	yearPublished		53297	-1	1	1
53318	lastName		53295	-1	1	1
53319	discountPrice		53297	-2	1	1
53320	maxVolume		53289	-1	1	1
53321	managerName		53289	-1	1	1
53322	typeName		53298	-1	1	1
53323	ISBN		53290	-1	1	1
53324	genreDescription		53292	-1	1	1
53325	cdTypeID		53296	-1	1	1
53326	state		53289	-1	1	1
53335	startDate		53331	-4	1	1
53336	title		53332	-3	1	1
53337	percentOff		53331	-2	1	1
53338	qty		53328	-1	1	1
53339	publisher		53334	-3	1	1
53340	manager		53330	-3	1	1
53341	trackName		53333	-3	1	1
53342	SKU		53328	-3	1	1
53343	trackNo		53333	-1	1	1
53344	zip		53330	-1	1	1
53345	city		53330	-3	1	1
53346	releaseDate		53332	-4	1	1
53347	whId		53328	-3	1	1
53348	facilityCode		53330	-3	1	1
53349	stAddress		53330	-3	1	1
53350	productId		53329	-3	1	1
53351	category		53329	-3	1	1
53352	artist		53332	-3	1	1
53353	isbn		53334	-3	1	1
53354	authors		53333	-3	1	1
53355	playtime		53333	-3	1	1
53356	discountCode		53329	-3	1	1
53357	cdID		53332	-1	1	1
53358	cdRef		53333	-1	1	1
53359	pubPrice		53332	-2	1	1
53360	year		53334	-1	1	1
53361	pages		53334	-1	1	1
53362	label		53332	-3	1	1
53363	description		53331	-3	1	1
53364	state		53330	-3	1	1
53365	endDate		53331	-4	1	1
53372	Type		53368	-3	1	1
53373	Genre		53368	-3	1	1
53374	ZipCode		53367	-1	1	1
53375	Author		53370	-3	1	1
53376	Keywords		53370	-3	1	1
53377	Artist		53368	-3	1	1
53378	RecordCompany		53368	-3	1	1
53379	AnnualSale		53367	-6	1	1
53380	AlbumName		53368	-3	1	1
53381	SalePrice		53368	-6	1	1
53382	ISBN		53369	-3	1	1
53383	Contact		53367	-3	1	1
53384	RecordNumber		53371	-3	1	1
53385	Discount		53368	-1	1	1
53386	Category		53370	-3	1	1
53387	StoreNumber		53371	-1	1	1
53388	Title		53370	-3	1	1
53389	EmployeeNumber		53367	-1	1	1
53390	Units		53371	-1	1	1
53391	Location		53367	-3	1	1
53397	DiscountedPrice		53396	-6	1	1
53398	NumEmployees		53394	-1	1	1
53399	ItemsShippedAnnually		53394	-1	1	1
53400	Telephone		53394	-3	1	1
53401	AnnualOperatingCost		53394	-6	1	1
53402	Artist		53395	-3	1	1
53403	Keywords		53396	-3	1	1
53404	Author		53396	-3	1	1
53405	FAX		53394	-3	1	1
53406	WarehouseID		53393	-1	1	1
53407	City		53394	-3	1	1
53408	ItemNumber		53393	-3	1	1
53409	CDTitle		53395	-3	1	1
53410	BookTitle		53396	-3	1	1
53411	StreetAddress		53394	-3	1	1
53412	Quantity		53393	-1	1	1
53413	Rating		53395	-1	1	1
53414	Genre		53395	-3	1	1
53415	State		53394	-3	1	1
53416	Zip		53394	-3	1	1
53417	ManagerName		53394	-3	1	1
53418	ISBN		53396	-3	1	1
53419	CDType		53395	-3	1	1
53420	SellingPrice		53395	-6	1	1
53421	Category		53396	-3	1	1
53422	Company		53395	-3	1	1
53423	IDNumber		53395	-3	1	1
53434	faxnumber		53431	-1	1	1
53435	telephonenum		53431	-1	1	1
53436	title		53430	-1	1	1
53437	address		53431	-1	1	1
53438	prodID		53433	-1	1	1
53439	manager		53431	-1	1	1
53440	publisher		53430	-1	1	1
53441	companyRecording		53429	-1	1	1
53442	CustomerRating		53429	-1	1	1
53443	keyword		53428	-1	1	1
53444	numberofemployees		53431	-1	1	1
53445	SalesRating		53430	-1	1	1
53446	price		53426	-2	1	1
53447	musiclanguage		53429	-1	1	1
53448	stock		53433	-1	1	1
53449	discount		53426	-2	1	1
53450	type		53425	-1	1	1
53451	category		53427	-1	1	1
53452	isbn		53427	-1	1	1
53453	artist		53429	-1	1	1
53454	warehouseid		53433	-1	1	1
53455	cdID		53425	-1	1	1
53456	state		53431	-1	1	1
53457	genre		53432	-1	1	1
53458	zipcode		53431	-1	1	1
53459	author		53430	-1	1	1
53460	albumname		53429	-1	1	1
53461	volumeofbusiness		53431	-1	1	1
53485	Street2		53484	-1	1	1
53486	SSN		53476	-1	1	1
53487	ProductCategoryId		53464	-1	1	1
53488	DiscountId		53465	-1	1	1
53489	City		53484	-1	1	1
53490	CreateDate		53466	-4	1	1
53491	Review		53466	-3	1	1
53492	Fax		53473	-1	1	1
53493	FirstName		53476	-1	1	1
53494	EmployeeId		53473	-1	1	1
53495	AuthorId		53467	-1	1	1
53496	Expire		53465	-4	1	1
53497	ImageId		53463	-1	1	1
53498	Quantity		53474	-1	1	1
53499	Rating		53466	-1	1	1
53500	WharehouseId		53474	-1	1	1
53501	LoginName		53466	-1	1	1
53502	VideoId		53483	-1	1	1
53503	State		53484	-1	1	1
53504	ReviewId		53466	-1	1	1
53505	FileName		53483	-1	1	1
53506	Phone		53473	-1	1	1
53507	AddressId		53472	-1	1	1
53508	FilePath		53483	-1	1	1
53509	GroupName		53475	-1	1	1
53510	Country		53484	-1	1	1
53511	Title		53482	-1	1	1
53512	Type		53469	-1	1	1
53513	RecordLabelId		53481	-1	1	1
53514	ModDate		53480	-4	1	1
53515	AlbumName		53471	-1	1	1
53516	RecordLabel		53481	-1	1	1
53517	Description		53464	-3	1	1
53518	ProductId		53483	-1	1	1
53519	Street1		53484	-1	1	1
53520	SoundId		53470	-1	1	1
53521	Name		53468	-1	1	1
53522	Genre		53478	-1	1	1
53523	Zip		53484	-1	1	1
53524	TypeId		53469	-1	1	1
53525	MusicId		53478	-1	1	1
53526	ArtistId		53468	-1	1	1
53527	ISBN		53477	-1	1	1
53528	Price		53482	-2	1	1
53529	Email		53480	-1	1	1
53530	GroupId		53468	-1	1	1
53531	CategoryId		53477	-1	1	1
53532	GenreId		53478	-1	1	1
53533	Category		53477	-1	1	1
53534	ManagerId		53472	-1	1	1
53535	Id		53474	-1	1	1
53536	LastName		53476	-1	1	1
53537	Rate		53465	-2	1	1
53546	comments		53539	-3	1	1
53547	width		53544	-2	1	1
53548	ASIN		53542	-1	1	1
53549	login		53539	-3	1	1
53550	publisher		53544	-3	1	1
53551	prodID		53545	-1	1	1
53552	date		53542	-4	1	1
53553	editorReview		53544	-3	1	1
53554	firstName		53540	-3	1	1
53555	height		53544	-2	1	1
53556	price		53542	-2	1	1
53557	cutOff		53542	-2	1	1
53558	zip		53543	-1	1	1
53559	name		53542	-3	1	1
53560	introduction		53542	-3	1	1
53561	city		53543	-3	1	1
53562	rate		53539	-1	1	1
53563	password		53540	-3	1	1
53564	discount		53542	-2	1	1
53565	street		53543	-3	1	1
53566	length		53544	-2	1	1
53567	wID		53545	-1	1	1
53568	cover		53544	-1	1	1
53569	lastName		53540	-3	1	1
53570	amount		53545	-1	1	1
53571	ISBN		53544	-1	1	1
53572	pages		53544	-1	1	1
53573	state		53543	-3	1	1
53574	weight		53544	-2	1	1
53584	phone		53582	-3	1	1
53585	discs		53579	-1	1	1
53586	width		53583	-2	1	1
53587	release_date		53579	-4	1	1
53588	publisher		53583	-3	1	1
53589	item_uid		53577	-1	1	1
53590	fax		53582	-3	1	1
53591	id		53577	-1	1	1
53592	price		53578	-2	1	1
53593	summary		53583	-3	1	1
53594	cover_art		53579	-3	1	1
53595	street		53582	-3	1	1
53596	depth		53583	-2	1	1
53597	uid		53578	-1	1	1
53598	cd_id		53580	-1	1	1
53599	published_date		53583	-4	1	1
53600	employees		53582	-1	1	1
53601	text		53576	-3	1	1
53602	state		53582	-3	1	1
53603	source		53577	-3	1	1
53604	author		53583	-3	1	1
53605	weight		53583	-2	1	1
53606	zipcode		53582	-1	1	1
53607	format		53583	-6	1	1
53608	tracking_number		53577	-3	1	1
53609	title		53576	-3	1	1
53610	rating		53576	-1	1	1
53611	height		53583	-2	1	1
53612	url		53580	-3	1	1
53613	list_price		53579	-2	1	1
53614	name		53576	-3	1	1
53615	quantity		53577	-1	1	1
53616	city		53582	-3	1	1
53617	warehouse_id		53577	-1	1	1
53618	type		53578	-6	1	1
53619	length		53579	-1	1	1
53620	category		53583	-3	1	1
53621	cubicfeet		53582	-1	1	1
53622	eta		53577	-4	1	1
53623	artist		53579	-3	1	1
53624	tracks		53579	-1	1	1
53625	ISBN		53583	-3	1	1
53626	pages		53583	-1	1	1
53627	label		53579	-3	1	1
53628	track		53580	-1	1	1
53629	email		53576	-3	1	1
53630	genre		53579	-3	1	1
53631	cost_per_month		53582	-2	1	1
53632	squarefeet		53582	-1	1	1
53639	Type		53635	-3	1	1
53640	ASIN		53637	-3	1	1
53641	Manager		53636	-3	1	1
53642	Telephone		53636	-3	1	1
53643	Author		53638	-3	1	1
53644	Artist		53637	-3	1	1
53645	City		53636	-3	1	1
53646	EmployeeCount		53636	-1	1	1
53647	Label		53637	-3	1	1
53648	CustomerRating		53638	-2	1	1
53649	Fax		53636	-3	1	1
53650	StreetAddress		53636	-3	1	1
53651	CDListPrice		53637	-2	1	1
53652	BookSellPrice		53638	-2	1	1
53653	Genre		53637	-3	1	1
53654	InStock		53634	-6	1	1
53655	Publisher		53638	-3	1	1
53656	Keyword		53638	-3	1	1
53657	State		53636	-3	1	1
53658	LocsAvailable		53634	-1	1	1
53659	Zip		53636	-1	1	1
53660	ItemID		53634	-1	1	1
53661	Album		53637	-3	1	1
53662	ISBN		53638	-3	1	1
53663	CDType		53637	-3	1	1
53664	CDSellPrice		53637	-2	1	1
53665	BookListPrice		53638	-2	1	1
53666	ShipTime		53634	-1	1	1
53667	Category		53638	-3	1	1
53668	Title		53638	-3	1	1
53669	ChartNumber		53637	-1	1	1
53670	PubYear		53638	-1	1	1
53681	Type		53680	-1	1	1
53682	Status		53672	-6	1	1
53683	NumTracks		53678	-1	1	1
53684	Label		53678	-1	1	1
53685	Media		53678	-1	1	1
53686	CreaterID		53674	-1	1	1
53687	Review		53675	-1	1	1
53688	Pages		53679	-1	1	1
53689	Address		53680	-1	1	1
53690	SaleRank		53675	-1	1	1
53691	Releasedate		53678	-4	1	1
53692	Name		53680	-1	1	1
53693	BookID		53679	-1	1	1
53694	Genre		53673	-1	1	1
53695	Publisher		53679	-1	1	1
53696	Cost		53675	-1	1	1
53697	WareID		53672	-1	1	1
53698	ItemID		53673	-1	1	1
53699	ItemID1		53677	-1	1	1
53700	MusicID		53678	-1	1	1
53701	ItemID2		53677	-1	1	1
53702	ISBN		53679	-1	1	1
53703	Binding		53679	-1	1	1
53704	NumDiscs		53678	-1	1	1
53705	AgeLevel		53679	-1	1	1
53706	Title		53678	-1	1	1
53707	Location		53680	-1	1	1
53723	Hardcover		53721	-6	1	1
53724	Author		53711	-1	1	1
53725	City		53722	-1	1	1
53726	Street		53722	-1	1	1
53727	QID		53709	-1	1	1
53728	Count		53717	-1	1	1
53729	CID		53715	-1	1	1
53730	PID		53716	-1	1	1
53731	Pages		53721	-1	1	1
53732	DID		53716	-1	1	1
53733	ValidStart		53710	-4	1	1
53734	DatePlaced		53712	-4	1	1
53735	DateShipped		53712	-4	1	1
53736	ZIP		53722	-1	1	1
53737	Discount		53710	-2	1	1
53738	CreditCard		53715	-1	1	1
53739	BillTo		53712	-1	1	1
53740	Name		53715	-1	1	1
53741	State		53722	-1	1	1
53742	ExpDate		53715	-4	1	1
53743	Payment		53712	-2	1	1
53744	GID		53714	-1	1	1
53745	WID		53717	-1	1	1
53746	Tracks		53713	-1	1	1
53747	Price		53709	-2	1	1
53748	Country		53722	-1	1	1
53749	Duration		53713	-2	1	1
53750	ShipTo		53712	-1	1	1
53751	OID		53716	-1	1	1
53752	AID		53715	-1	1	1
53753	ValidStop		53710	-4	1	1
53762	producer		53759	-3	1	1
53763	discountedprice		53757	-2	1	1
53764	reviewerSource		53756	-3	1	1
53765	shippingrate		53755	-2	1	1
53766	title		53759	-3	1	1
53767	address		53758	-3	1	1
53768	publisher		53760	-3	1	1
53769	rating		53756	-1	1	1
53770	productid		53759	-1	1	1
53771	availability		53761	-1	1	1
53772	shippingdeal		53755	-3	1	1
53773	reviewtext		53756	-3	1	1
53774	supplierid		53759	-1	1	1
53775	baseprice		53757	-2	1	1
53776	length		53760	-1	1	1
53777	dimensions		53757	-3	1	1
53778	artist		53759	-3	1	1
53779	warehouseid		53755	-1	1	1
53780	shippingstyle		53755	-3	1	1
53781	warehousename		53758	-3	1	1
53782	ISBN		53760	-1	1	1
53783	otheroffers		53757	-3	1	1
53784	description		53759	-3	1	1
53785	zipcode		53758	-1	1	1
53786	author		53760	-3	1	1
53787	format		53759	-3	1	1
53803	sampleurl		53789	-1	1	1
53804	total_time_to_dispatch		53802	-1	1	1
53805	width		53801	-6	1	1
53806	track_number		53796	-1	1	1
53807	is_creator		53798	-6	1	1
53808	date		53798	-4	1	1
53809	id		53794	-1	1	1
53810	Publication_date		53799	-4	1	1
53811	keyword		53792	-1	1	1
53812	fedex_pickups		53794	-1	1	1
53813	num_shifts		53794	-1	1	1
53814	Format		53799	-1	1	1
53815	rev_id		53798	-1	1	1
53816	blurb		53793	-1	1	1
53817	num_discs		53793	-1	1	1
53818	other		53799	-1	1	1
53819	is_editoral		53798	-6	1	1
53820	artist_order		53795	-1	1	1
53821	usps_pickups		53794	-1	1	1
53822	reviewer_id		53798	-1	1	1
53823	num_employees		53794	-1	1	1
53824	text		53798	-1	1	1
53825	special_features		53801	-1	1	1
53826	Num_sold		53799	-1	1	1
53827	Release_date		53793	-4	1	1
53828	weight		53793	-6	1	1
53829	format		53796	-1	1	1
53830	ups_pickups		53794	-1	1	1
53831	title		53789	-1	1	1
53832	ASIN		53793	-1	1	1
53833	postal_code		53794	-1	1	1
53834	num_pages		53799	-1	1	1
53835	manager		53794	-1	1	1
53836	rating		53798	-1	1	1
53837	Label		53793	-1	1	1
53838	date_of_birth		53800	-4	1	1
53839	location_id		53802	-1	1	1
53840	height		53801	-6	1	1
53841	num_sold_at_item_location		53802	-1	1	1
53842	state_province		53794	-1	1	1
53843	additional_location_information		53794	-1	1	1
53844	num_at_location		53802	-1	1	1
53845	url		53796	-1	1	1
53846	name		53790	-1	1	1
53847	series		53789	-1	1	1
53848	city		53794	-1	1	1
53849	illustrator		53799	-1	1	1
53850	warehouse_id		53801	-1	1	1
53851	book_title_id		53799	-1	1	1
53852	loc-id		53801	-1	1	1
53853	Publisher		53799	-1	1	1
53854	artist_id		53795	-1	1	1
53855	street_address		53794	-1	1	1
53856	length		53801	-6	1	1
53857	album		53796	-1	1	1
53858	category		53791	-1	1	1
53859	product_id		53798	-1	1	1
53860	album_id		53793	-1	1	1
53861	Hours_of_operation		53794	-1	1	1
53862	ISBN		53799	-1	1	1
53863	country		53794	-1	1	1
53864	track_name		53796	-1	1	1
53865	MSRP		53799	-6	1	1
53866	affiliation		53790	-1	1	1
53867	item_id		53802	-1	1	1
53868	phone_number		53794	-1	1	1
53869	Standard_price		53799	-6	1	1
53879	Capacity		53877	-3	1	1
53880	WarehouseName		53871	-3	1	1
53881	Kind		53872	-3	1	1
53882	Telephone		53877	-3	1	1
53883	Manager		53877	-3	1	1
53884	City		53877	-3	1	1
53885	Street		53877	-3	1	1
53886	Zipcode		53877	-3	1	1
53887	Year		53874	-1	1	1
53888	Fax		53877	-3	1	1
53889	FirstName		53876	-3	1	1
53890	Month		53874	-3	1	1
53891	Name		53875	-3	1	1
53892	Quantity		53871	-1	1	1
53893	Rating		53874	-6	1	1
53894	Word		53873	-3	1	1
53895	Paperback		53878	-3	1	1
53896	State		53877	-3	1	1
53897	MaxEmployees		53877	-1	1	1
53898	Price		53874	-6	1	1
53899	ISBN		53871	-3	1	1
53900	MSRP		53874	-6	1	1
53901	Volume		53877	-1	1	1
53902	Category		53874	-3	1	1
53903	Title		53878	-3	1	1
53904	Company		53874	-3	1	1
53905	MiddleName		53876	-3	1	1
53906	LastName		53876	-3	1	1
53912	Status		53908	-3	1	1
53913	Type		53910	-3	1	1
53914	Manager		53909	-3	1	1
53915	Artist		53910	-3	1	1
53916	Author		53911	-3	1	1
53917	City		53909	-3	1	1
53918	Catalog_Number		53910	-3	1	1
53919	Number_Of_Discs		53910	-1	1	1
53920	Address		53909	-3	1	1
53921	Running_Time		53910	-3	1	1
53922	Fax_Number		53909	-3	1	1
53923	Genre		53910	-3	1	1
53924	Publisher		53911	-3	1	1
53925	Telephone_Number		53909	-3	1	1
53926	Keyword		53911	-3	1	1
53927	State		53909	-3	1	1
53928	Number_Of_Employees		53909	-3	1	1
53929	Record_Label		53910	-3	1	1
53930	Volume_Of_Business		53909	-3	1	1
53931	Amount_In_Stock		53908	-1	1	1
53932	Price		53910	-6	1	1
53933	ISBN		53911	-3	1	1
53934	Year_Released		53910	-1	1	1
53935	Zip_Code		53908	-3	1	1
53936	Category		53911	-3	1	1
53937	Title		53910	-3	1	1
53938	ID		53908	-3	1	1
53945	Type		53942	-3	1	1
53946	Warehouse_ID		53940	-1	1	1
53947	NumEmployees		53944	-1	1	1
53948	Capacity		53944	-1	1	1
53949	Artist		53942	-3	1	1
53950	Keywords		53943	-3	1	1
53951	Author		53943	-3	1	1
53952	City		53944	-3	1	1
53953	fax_n		53941	-1	1	1
53954	Street		53944	-3	1	1
53955	Edition		53943	-1	1	1
53956	Discount		53942	-2	1	1
53957	R_Company		53942	-3	1	1
53958	phone_n		53941	-1	1	1
53959	name		53941	-3	1	1
53960	Quantity		53940	-1	1	1
53961	Rating		53942	-1	1	1
53962	Product_ID		53940	-1	1	1
53963	Genre		53942	-3	1	1
53964	Publisher		53943	-3	1	1
53965	State		53944	-3	1	1
53966	category		53943	-3	1	1
53967	Zip		53944	-1	1	1
53968	CD_ID		53942	-3	1	1
53969	Price		53942	-2	1	1
53970	ISBN		53943	-3	1	1
53971	Contact_ID		53941	-1	1	1
53972	Title		53941	-3	1	1
53987	comments		53975	-3	1	1
53988	Year_of_Publication		53985	-4	1	1
53989	SSN		53976	-1	1	1
53990	title		53977	-3	1	1
53991	Date_of_Hire		53976	-4	1	1
53992	Manager		53979	-3	1	1
53993	Restrictions		53974	-3	1	1
53994	Home_Phone		53976	-3	1	1
53995	Author		53985	-3	1	1
53996	Salary		53976	-6	1	1
53997	City		53979	-3	1	1
53998	Label		53977	-3	1	1
53999	contact_info		53984	-3	1	1
54000	Warehouse		53978	-3	1	1
54001	card_number		53983	-1	1	1
54002	Year		53977	-1	1	1
54003	Description		53982	-3	1	1
54004	Phone_number		53979	-6	1	1
54005	price		53978	-6	1	1
54006	Address		53979	-3	1	1
54007	username		53984	-3	1	1
54008	zip		53986	-1	1	1
54009	quantity		53978	-1	1	1
54010	Department		53976	-3	1	1
54011	Rating		53975	-1	1	1
54012	exp_date		53983	-4	1	1
54013	date_created		53986	-4	1	1
54014	city		53986	-3	1	1
54015	First_Name		53976	-3	1	1
54016	password		53984	-3	1	1
54017	user		53983	-3	1	1
54018	Publisher		53985	-3	1	1
54019	type		53983	-3	1	1
54020	street		53986	-3	1	1
54021	Zip		53979	-6	1	1
54022	End		53974	-6	1	1
54023	User		53975	-3	1	1
54024	Last_Name		53976	-3	1	1
54025	artist		53977	-3	1	1
54026	WarehouseId		53979	-3	1	1
54027	Amount		53974	-6	1	1
54028	Start		53974	-4	1	1
54029	ISBN		53974	-1	1	1
54030	Position		53976	-3	1	1
54031	state		53986	-1	1	1
54032	Category		53981	-3	1	1
54033	DOB		53976	-4	1	1
54037	DiscountPrice		54036	-2	1	1
54038	Price		54036	-2	1	1
54039	ISBN		54036	-1	1	1
54040	Publisher		54036	-3	1	1
54041	Keywords		54036	-3	1	1
54042	Author		54036	-3	1	1
54043	Category		54036	-3	1	1
54044	Title		54035	-3	1	1
54045	Quantity		54035	-1	1	1
54046	Location		54035	-3	1	1
54047	ID		54035	-1	1	1
54051	CISBN		54049	-1	1	1
54052	ISBN		54050	-1	1	1
54053	BISBN		54049	-1	1	1
54054	title		54050	-1	1	1
54055	price		54050	-2	1	1
54056	keywords		54050	-1	1	1
54057	discprice		54050	-2	1	1
54058	warehousecode		54049	-1	1	1
54059	quantity		54049	-1	1	1
54060	stock		54049	-1	1	1
54061	author		54050	-1	1	1
54062	bookcategory		54050	-1	1	1
54068	DiscountPrice		54066	-2	1	1
54069	Publisher		54066	-3	1	1
54070	ItemType		54064	-3	1	1
54071	ItemId		54064	-3	1	1
54072	WarehouseId		54064	-1	1	1
54073	Edition		54066	-3	1	1
54074	ISBN		54065	-3	1	1
54075	Price		54066	-2	1	1
54076	Description		54066	-3	1	1
54077	FirstName		54065	-3	1	1
54078	CopyrightYear		54066	-1	1	1
54079	Category		54067	-3	1	1
54080	Title		54066	-3	1	1
54081	Quantity		54064	-1	1	1
54082	LastName		54065	-3	1	1
54091	item		54090	-3	1	1
54092	phone		54086	-3	1	1
54093	startDate		54090	-4	1	1
54094	title		54088	-3	1	1
54095	publisher		54088	-1	1	1
54096	middleInitial		54089	-1	1	1
54097	firstName		54089	-3	1	1
54098	id		54089	-3	1	1
54099	mainContact		54086	-3	1	1
54100	mobilePhone		54089	-3	1	1
54101	keyword		54087	-3	1	1
54102	price		54090	-2	1	1
54103	quantity		54084	-1	1	1
54104	name		54086	-3	1	1
54105	city		54086	-3	1	1
54106	datePublished		54088	-4	1	1
54107	publisherID		54086	-3	1	1
54108	warehouse		54084	-3	1	1
54109	standardPrice		54088	-2	1	1
54110	streetAdderss		54086	-3	1	1
54111	lastName		54089	-3	1	1
54112	ISBN		54084	-3	1	1
54113	pages		54088	-1	1	1
54114	book		54085	-3	1	1
54115	state		54086	-3	1	1
54116	endDate		54090	-4	1	1
54117	officePhone		54089	-3	1	1
54118	genre		54088	-3	1	1
54119	zipcode		54086	-1	1	1
54120	author		54085	-6	1	1
54127	discount		54122	-2	1	1
54128	title		54125	-1	1	1
54129	category		54123	-1	1	1
54130	prodID		54126	-1	1	1
54131	isbn		54123	-1	1	1
54132	publisher		54125	-1	1	1
54133	warehouseid		54126	-1	1	1
54134	CustomerRating		54125	-1	1	1
54135	keyword		54124	-1	1	1
54136	SalesRating		54125	-1	1	1
54137	price		54122	-2	1	1
54138	stock		54126	-1	1	1
54139	author		54125	-1	1	1
54144	WarehouseName		54141	-3	1	1
54145	Genre		54142	-3	1	1
54146	ISBN		54142	-1	1	1
54147	Price		54142	-1	1	1
54148	ProductID		54141	-1	1	1
54149	InStock		54141	-3	1	1
54150	ProductType		54143	-3	1	1
54151	Author		54142	-3	1	1
54152	Title		54142	-3	1	1
54153	Quantity		54141	-1	1	1
54154	ExpectedInStock		54141	-4	1	1
54155	CustomerRating		54142	-1	1	1
54162	WarehouseName		54157	-1	1	1
54163	DiscountPrice		54159	-2	1	1
54164	Publisher		54161	-1	1	1
54165	BookISBN		54160	-1	1	1
54166	Keywords		54161	-1	1	1
54167	ProductNumber		54161	-1	1	1
54168	Year		54161	-1	1	1
54169	Edition		54161	-1	1	1
54170	Price		54161	-2	1	1
54171	ISBN		54161	-1	1	1
54172	AuthorName		54160	-1	1	1
54173	Number		54158	-1	1	1
54174	CopyWrite		54161	-1	1	1
54175	Category		54161	-1	1	1
54176	Title		54161	-1	1	1
54177	QuantityInStock		54157	-1	1	1
54184	discountPrice		54180	-2	1	1
54185	word		54182	-1	1	1
54186	ISBN		54182	-1	1	1
54187	type		54180	-1	1	1
54188	title		54180	-1	1	1
54189	productID		54179	-1	1	1
54190	price		54180	-2	1	1
54191	warehouseID		54179	-1	1	1
54192	quantity		54179	-1	1	1
54193	author		54183	-1	1	1
54197	Amount		54195	-1	1	1
54198	Description		54196	-3	1	1
54199	Publisher		54196	-3	1	1
54200	ItemType		54196	-3	1	1
54201	OurPrice		54196	-2	1	1
54202	Keywords		54196	-3	1	1
54203	Author		54196	-3	1	1
54204	WarehouseID		54195	-3	1	1
54205	Category		54196	-3	1	1
54206	ItemID		54195	-3	1	1
54207	Title		54196	-3	1	1
54208	SuggestedPrice		54196	-2	1	1
54216	houseID		54210	-1	1	1
54217	keyword		54213	-1	1	1
54218	priceType		54211	-1	1	1
54219	ISBN		54215	-1	1	1
54220	title		54215	-1	1	1
54221	productID		54211	-1	1	1
54222	price		54211	-2	1	1
54223	productCatagory		54213	-1	1	1
54224	genreType		54212	-1	1	1
54225	quantity		54210	-1	1	1
54226	name		54214	-1	1	1
54227	artistID		54214	-1	1	1
54234	Type		54230	-1	1	1
54235	DiscountPrice		54230	-2	1	1
54236	Publisher		54233	-1	1	1
54237	Keyword		54232	-1	1	1
54238	WHNum		54229	-1	1	1
54239	Price		54230	-2	1	1
54240	ISBN		54232	-1	1	1
54241	AuthorName		54231	-1	1	1
54242	Category		54233	-1	1	1
54243	Title		54233	-1	1	1
54244	Quantity		54229	-1	1	1
54245	ID		54229	-1	1	1
54246	NumPages		54233	-1	1	1
54252	whId		54248	-3	1	1
54253	startDate		54250	-4	1	1
54254	percentOff		54250	-2	1	1
54255	title		54251	-3	1	1
54256	productId		54249	-3	1	1
54257	category		54249	-3	1	1
54258	qty		54248	-1	1	1
54259	isbn		54251	-3	1	1
54260	publisher		54251	-3	1	1
54261	authors		54251	-3	1	1
54262	discountCode		54249	-3	1	1
54263	pubPrice		54251	-2	1	1
54264	year		54251	-1	1	1
54265	pages		54251	-1	1	1
54266	SKU		54248	-3	1	1
54267	description		54250	-3	1	1
54268	endDate		54250	-4	1	1
54275	itemNum		54272	-1	1	1
54276	authorArtist		54272	-1	1	1
54277	salePrice		54274	-2	1	1
54278	suggestion		54271	-1	1	1
54279	category		54274	-1	1	1
54280	publisher		54274	-1	1	1
54281	cover		54274	-1	1	1
54282	keyword		54273	-1	1	1
54283	price		54271	-2	1	1
54284	warehouseNum		54270	-1	1	1
54285	name		54271	-1	1	1
54286	quantity		54270	-1	1	1
54287	review		54271	-1	1	1
54291	ISBN		54289	-3	1	1
54292	Keywords		54290	-3	1	1
54293	Discount		54290	-1	1	1
54294	Author		54290	-3	1	1
54295	Category		54290	-3	1	1
54296	StoreNumber		54289	-1	1	1
54297	Title		54290	-3	1	1
54298	SalePrice		54290	-6	1	1
54299	Units		54289	-1	1	1
54303	DiscountedPrice		54302	-6	1	1
54304	ISBN		54302	-3	1	1
54305	SellingPrice		54302	-6	1	1
54306	BookTitle		54302	-3	1	1
54307	Keywords		54302	-3	1	1
54308	Author		54302	-3	1	1
54309	WarehouseID		54301	-1	1	1
54310	Category		54302	-3	1	1
54311	ItemNumber		54301	-3	1	1
54312	Quantity		54301	-1	1	1
54313	Rating		54302	-1	1	1
54322	ProductCategoryId		54320	-1	1	1
54323	DiscountId		54317	-1	1	1
54324	ISBN		54319	-1	1	1
54325	Price		54321	-2	1	1
54326	Description		54316	-3	1	1
54327	FirstName		54318	-1	1	1
54328	CategoryId		54319	-1	1	1
54329	AuthorId		54318	-1	1	1
54330	ProductId		54315	-1	1	1
54331	Category		54319	-1	1	1
54332	Title		54321	-1	1	1
54333	Expire		54317	-4	1	1
54334	Id		54315	-1	1	1
54335	Quantity		54315	-1	1	1
54336	WharehouseId		54315	-1	1	1
54337	LastName		54318	-1	1	1
54338	Rate		54317	-2	1	1
54343	width		54341	-2	1	1
54344	discount		54341	-2	1	1
54345	length		54341	-2	1	1
54346	wID		54342	-1	1	1
54347	prodID		54342	-1	1	1
54348	publisher		54341	-3	1	1
54349	date		54341	-4	1	1
54350	editorReview		54341	-3	1	1
54351	firstName		54340	-3	1	1
54352	cover		54341	-1	1	1
54353	lastName		54340	-3	1	1
54354	amount		54342	-1	1	1
54355	ISBN		54341	-1	1	1
54356	height		54341	-2	1	1
54357	pages		54341	-1	1	1
54358	price		54341	-2	1	1
54359	cutOff		54341	-2	1	1
54360	introduction		54341	-3	1	1
54361	name		54341	-3	1	1
54362	weight		54341	-2	1	1
54363	rate		54341	-1	1	1
54370	discountPrice		54366	-2	1	1
54371	keyword		54368	-1	1	1
54372	PID		54365	-1	1	1
54373	WH#		54365	-1	1	1
54374	title		54369	-1	1	1
54375	type		54366	-1	1	1
54376	price		54366	-2	1	1
54377	numPages		54369	-1	1	1
54378	publisher		54369	-1	1	1
54379	genre		54369	-1	1	1
54380	quantity		54365	-1	1	1
54381	name		54367	-1	1	1
54386	warehouse_id		54384	-1	1	1
54387	summary		54385	-3	1	1
54388	width		54385	-2	1	1
54389	type		54383	-6	1	1
54390	title		54385	-3	1	1
54391	depth		54385	-2	1	1
54392	category		54385	-3	1	1
54393	uid		54383	-1	1	1
54394	item_uid		54384	-1	1	1
54395	publisher		54385	-3	1	1
54396	published_date		54385	-4	1	1
54397	id		54384	-1	1	1
54398	ISBN		54385	-3	1	1
54399	height		54385	-2	1	1
54400	pages		54385	-1	1	1
54401	price		54383	-2	1	1
54402	quantity		54384	-1	1	1
54403	list_price		54385	-2	1	1
54404	weight		54385	-2	1	1
54405	author		54385	-3	1	1
54406	format		54385	-6	1	1
54415	keyword		54408	-1	1	1
54416	discounted		54411	-2	1	1
54417	ISBN		54412	-1	1	1
54418	title		54411	-1	1	1
54419	price		54411	-2	1	1
54420	authorID		54410	-1	1	1
54421	categoryName		54413	-1	1	1
54422	categoryID		54409	-1	1	1
54423	warehouseID		54412	-1	1	1
54424	stock		54412	-1	1	1
54425	author		54414	-1	1	1
54426	authorEmailAddress		54414	-1	1	1
54433	yearPublished		54432	-1	1	1
54434	title		54432	-1	1	1
54435	genreID		54432	-1	1	1
54436	firstName		54429	-1	1	1
54437	lastName		54429	-1	1	1
54438	discountPrice		54432	-2	1	1
54439	ISBN		54428	-1	1	1
54440	genreDescription		54431	-1	1	1
54441	price		54432	-2	1	1
54442	authorID		54428	-1	1	1
54443	warehouseID		54430	-1	1	1
54444	quantity		54430	-1	1	1
54445	genreName		54431	-1	1	1
54451	DiscountPrice		54450	-1	1	1
54452	Publisher		54450	-1	1	1
54453	Keywords		54450	-1	1	1
54454	WarehouseID		54447	-1	1	1
54455	ExpectedInStock		54447	-1	1	1
54456	CustomerRating		54450	-1	1	1
54457	Year		54450	-1	1	1
54458	Edition		54450	-1	1	1
54459	ISBN		54447	-1	1	1
54460	Price		54450	-1	1	1
54461	Binding		54450	-1	1	1
54462	FirstName		54448	-6	1	1
54463	MiddleInitial		54448	-6	1	1
54464	ShipTime		54447	-1	1	1
54465	AuthorID		54449	-6	1	1
54466	Category		54450	-1	1	1
54467	Title		54450	-1	1	1
54468	inStock		54447	-1	1	1
54469	Quantity		54447	-1	1	1
54470	LastName		54448	-6	1	1
54471	NumPages		54450	-1	1	1
54476	Type		54474	-3	1	1
54477	Publisher		54475	-3	1	1
54478	InStock		54473	-6	1	1
54479	Keyword		54475	-3	1	1
54480	LocsAvailable		54473	-1	1	1
54481	Author		54475	-3	1	1
54482	ItemID		54473	-1	1	1
54483	CustomerRating		54475	-2	1	1
54484	ISBN		54475	-3	1	1
54485	BookListPrice		54475	-2	1	1
54486	ShipTime		54473	-1	1	1
54487	Category		54475	-3	1	1
54488	Title		54475	-3	1	1
54489	BookSellPrice		54475	-2	1	1
54490	PubYear		54475	-1	1	1
54495	DiscountPrice		54494	-2	1	1
54496	Price		54494	-2	1	1
54497	ISBN		54492	-2	1	1
54498	FirstName		54493	-1	1	1
54499	Category		54494	-1	1	1
54500	Title		54494	-1	1	1
54501	Quantity		54492	-1	1	1
54502	WH_ID		54492	-1	1	1
54503	MiddleName		54493	-1	1	1
54504	LastName		54493	-1	1	1
54508	WareHouseCode		54506	-1	1	1
54509	Stock		54506	-1	1	1
54510	Price		54507	-1	1	1
54511	ISBN		54507	-1	1	1
54512	AuthorName		54507	-1	1	1
54513	KeyWord		54507	-1	1	1
54514	IsCD		54506	-1	1	1
54515	ISBook		54506	-1	1	1
54516	Title		54507	-1	1	1
54517	DisPrice		54507	-1	1	1
54518	Quantity		54506	-1	1	1
54519	BookCategory		54507	-1	1	1
54527	Status		54521	-6	1	1
54528	Type		54523	-1	1	1
54529	BookID		54526	-1	1	1
54530	Genre		54522	-1	1	1
54531	Publisher		54526	-1	1	1
54532	Cost		54524	-1	1	1
54533	WareID		54521	-1	1	1
54534	ItemID		54522	-1	1	1
54535	CreaterID		54523	-1	1	1
54536	Review		54524	-1	1	1
54537	ISBN		54526	-1	1	1
54538	Binding		54526	-1	1	1
54539	Pages		54526	-1	1	1
54540	AgeLevel		54526	-1	1	1
54541	SaleRank		54524	-1	1	1
54542	Name		54525	-1	1	1
54545	Model	Model	54544	-6	1	1
54546	Email	Email (e.g. mark@aol.com)	54544	-6	1	1
54547	ZipCode	ZipCode	54544	-6	1	1
54548	Make	Make	54544	-6	1	1
54551	PriceRangeFrom	Price Range	54550	-6	1	1
54552	Model	Model	54550	-6	1	1
54553	Email	Email	54550	-6	1	1
54554	Color	Color	54550	-6	1	1
54555	PriceRangeTo	to	54550	-6	1	1
54556	ZipCode	ZipCode	54550	-6	1	1
54557	Make	Make	54550	-6	1	1
54558	YearMin	Year	54550	-6	1	1
54559	YearMax		54550	-6	1	1
54560	Mileage	Mileage	54550	-6	1	1
54563	Model	Model (optional)	54562	-6	1	1
54564	Sort	sort results by	54562	-6	1	1
54565	Keyword	Keyword (optional)	54562	-6	1	1
54566	MaxYear	to	54562	-6	1	1
54567	Region	Region	54562	-6	1	1
54568	MAKE	Make	54562	-6	1	1
54569	MaxPrice	Max. Price (optional)	54562	-6	1	1
54570	MinYear	Year (optional)	54562	-6	1	1
54573	comments	comments	54572	-6	1	1
54574	transmission	Transmission	54572	-6	1	1
54575	bodyStyle	choose a model OR a style	54572	-6	1	1
54576	contact.firstName	First Name	54572	-6	1	1
54577	make	Make	54572	-6	1	1
54578	contact.phone	Phone	54572	-6	1	1
54579	year	Year	54572	-6	1	1
54580	contact.email	E-mail	54572	-6	1	1
54581	price	Price	54572	-6	1	1
54582	prefix		54572	-6	1	1
54583	mileage	mileage, at or under	54572	-6	1	1
54584	contact.lastName	Last Name	54572	-6	1	1
54585	model	model or Style	54572	-6	1	1
54586	duration	eNotification Time Search	54572	-6	1	1
54589	PS	Power Steering	54588	-6	1	1
54590	ODOMETER	Mileage	54588	-6	1	1
54591	ABS	Antilock Brakes	54588	-6	1	1
54592	LEATHER	Leather Seats	54588	-6	1	1
54593	CD	CD Player	54588	-6	1	1
54594	ROOF	Special Roof	54588	-6	1	1
54595	PW	Power Windows	54588	-6	1	1
54596	RESTRAINT	Air Bag(s)	54588	-6	1	1
54597	CLASSIFICATION	-OR- Select one of these categorie	54588	-6	1	1
54598	FUELTYPE	Fuel Type	54588	-6	1	1
54599	DATE_FROM	4-Digit Year Range	54588	-6	1	1
54600	PL	Power Locks	54588	-6	1	1
54601	CELLPHONE	Cellular Phone	54588	-6	1	1
54602	PB	Power Brakes	54588	-6	1	1
54603	CRUISE	Cruise Control	54588	-6	1	1
54604	E_SEATS	Electronic Seats	54588	-6	1	1
54605	PRICE	Price	54588	-6	1	1
54606	CASSETTE	Cassette	54588	-6	1	1
54607	DATE_TO	to	54588	-6	1	1
54608	AC	Air Conditioning	54588	-6	1	1
54609	optSelltype		54588	-6	1	1
54610	BODYSTYLE	Body Style	54588	-6	1	1
54611	MAKEMODEL	Select up to five makes models OR one category	54588	-6	1	1
54612	CYLINDERS	Cylinders	54588	-6	1	1
54615	numCarsOnOnePage	Vehicles per page	54614	-6	1	1
54616	model_vch		54614	-6	1	1
54617	make_vch	Select Vehicle	54614	-6	1	1
54618	Postal_Code_vch	Zip Code	54614	-6	1	1
54619	Email_Addr_vch	E-mail	54614	-6	1	1
54620	search_mileage_int	search within	54614	-6	1	1
54621	Entered_Postal_Code_vch	miles of	54614	-6	1	1
54622	High_Price	to	54614	-6	1	1
54623	Low_Price	Price Range	54614	-6	1	1
54626	Makes	elect up to five makes to show models desired	54625	-6	1	1
54627	dealer_makes	Search by manufacturer	54625	-6	1	1
54628	PRICE	Select Price Range	54625	-6	1	1
54629	dealer	dealer	54625	-6	1	1
54630	optSelltype		54625	-6	1	1
54631	rgn_id	Search by area	54625	-6	1	1
54632	makemodel	then select up to five models	54625	-6	1	1
54633	SortOrder	Sort vehicles by...	54625	-6	1	1
54636	Name_First	First Name	54635	-6	1	1
54637	Interior	Select Color (Interior)	54635	-6	1	1
54638	DayPhone_1	Day Phone	54635	-6	1	1
54639	Notes_to_dealer	Additional Comments (optional)	54635	-6	1	1
54640	EvePhone_Ext	Ext	54635	-6	1	1
54641	make	Make	54635	-6	1	1
54642	Zip	Your Zip	54635	-6	1	1
54643	EvePhone_1	Evening Phone	54635	-6	1	1
54644	DayPhone_2		54635	-6	1	1
54645	finance	Payment Method	54635	-6	1	1
54646	Zipcode	Zip	54635	-6	1	1
54647	Best_Time_To_Contact	Best Time To Contact	54635	-6	1	1
54648	EvePhone_3		54635	-6	1	1
54649	Name_Last	Last Name	54635	-6	1	1
54650	Exterior	Select Color (Exterior)	54635	-6	1	1
54651	DayPhone_3		54635	-6	1	1
54652	year	Year	54635	-6	1	1
54653	modelname	Model	54635	-6	1	1
54654	Address	Street Address (No city or state required)	54635	-6	1	1
54655	time_frame	Your timeframe	54635	-6	1	1
54656	EvePhone_2		54635	-6	1	1
54657	E-mail	Email	54635	-6	1	1
54658	package	Select Package	54635	-6	1	1
54659	DayPhone_Ext	Ext	54635	-6	1	1
54662	SearchRadius	Radius miles	54661	-6	1	1
54663	SearchMake	Make	54661	-6	1	1
54664	SearchZip	ZipCode	54661	-6	1	1
54665	SearchDMAId	Region	54661	-6	1	1
54666	SearchModel	Model	54661	-6	1	1
54667	SearchBodyType	Body Style	54661	-6	1	1
54670	HowHeard	How did you hear about us	54669	-6	1	1
54671	VehicleType	Vehicle type	54669	-6	1	1
54672	Pricetop	Price Limit	54669	-6	1	1
54673	Color	Color Preference	54669	-6	1	1
54674	Telephone	Telephone (recommended)include Area Code	54669	-6	1	1
54675	Options	items (check preferred)	54669	-6	1	1
54676	Model	Model(s)	54669	-6	1	1
54677	Year	Made In Or After	54669	-6	1	1
54678	Contact	Mode of Contact	54669	-6	1	1
54679	SecondModel	2nd choice Model(s)	54669	-6	1	1
54680	Address	Address	54669	-6	1	1
54681	Make	Make	54669	-6	1	1
54682	email	E-mail Address (Required)	54669	-6	1	1
54683	Purchase	Approximate Purchase Date	54669	-6	1	1
54684	Name	Name (required)	54669	-6	1	1
54685	SecondMake	2nd choice Make	54669	-6	1	1
54686	City-State-Zip	City-State-Zip	54669	-6	1	1
54689	PostalCode		54688	-6	1	1
54690	SearchRadius		54688	-6	1	1
54691	Doors		54688	-6	1	1
54692	PrintNumber	Ad ID	54688	-6	1	1
54693	YearMin	Year	54688	-6	1	1
54694	VehicleTypeID	Vehicle	54688	-6	1	1
54695	ProvinceID	Province	54688	-6	1	1
54696	ForSaleBy		54688	-6	1	1
54697	Price		54688	-6	1	1
54698	TransmissionID	Transmission	54688	-6	1	1
54699	KMs		54688	-6	1	1
54700	MakeID	Make	54688	-6	1	1
54701	YearMax	to	54688	-6	1	1
54704	lstPriceMax	to	54703	-6	1	1
54705	lstCategory	Category	54703	-6	1	1
54706	lstMake	Make	54703	-6	1	1
54707	lstRegion	Region	54703	-6	1	1
54708	lstPriceMin	Price Range (between)	54703	-6	1	1
54709	lstModels	Model	54703	-6	1	1
54712	numCarsOnOnePage	Vehicles per page	54711	-6	1	1
54713	vehicle	Select Vehicle	54711	-6	1	1
54714	search_mileage_int	search within	54711	-6	1	1
54715	Entered_Postal_Code_vch	miles of	54711	-6	1	1
54716	High_Price	to	54711	-6	1	1
54717	Low_Price	Price Range	54711	-6	1	1
54720	PrivatePool		54719	-6	1	1
54721	FirePlace		54719	-6	1	1
54722	Parking	Make	54719	-6	1	1
54723	Stories	Exterior Color	54719	-6	1	1
54724	GatedEntry		54719	-6	1	1
54725	State	Select Your State	54719	-6	1	1
54726	PropertyType	Category	54719	-6	1	1
54727	ZipCode	Zip Code	54719	-6	1	1
54728	MaxPrice	MAXimum Price	54719	-6	1	1
54729	Bedrooms	Model	54719	-6	1	1
54730	County	County(s)	54719	-6	1	1
54731	YearBuilt	Oldest Year Desired	54719	-6	1	1
54732	OfferedBy	Sale Type	54719	-6	1	1
54733	MinPrice	MINimum Price	54719	-6	1	1
54734	EntryLevel	Transmission	54719	-6	1	1
54735	CommPool		54719	-6	1	1
54736	SqFootage	Engine	54719	-6	1	1
54737	MaxMiles	MAXimum Desired Mileage	54719	-6	1	1
54738	ListingType	Listing Type	54719	-6	1	1
54739	Basement		54719	-6	1	1
54742	d_vehicle_used_flag		54741	-6	1	1
54743	d_minPrice	Price Range	54741	-6	1	1
54744	x_4by4flag		54741	-6	1	1
54745	d_vehicle_new_flag		54741	-6	1	1
54746	d_Category	Vehicle Body Style	54741	-6	1	1
54747	x_number_of_rows	Vehicles per page	54741	-6	1	1
54748	d_maxPrice	to	54741	-6	1	1
54749	D_radius	Distance	54741	-6	1	1
54750	d_Make	Vehicle Make	54741	-6	1	1
54751	d_maxYear	to	54741	-6	1	1
54752	D_zip_code	ZipCode	54741	-6	1	1
54753	d_minYear	Year Range	54741	-6	1	1
54754	d_maxMileage	Vehicle Mileage	54741	-6	1	1
54755	d_model_name	Vehicle Model	54741	-6	1	1
54758	exterior	Exterior Color?	54757	-6	1	1
54759	year_min	Year Range: From	54757	-6	1	1
54760	bodystyle		54757	-6	1	1
54761	radius	Distance	54757	-6	1	1
54762	vehicle_type		54757	-6	1	1
54763	price_max	to	54757	-6	1	1
54764	price_min	Price Range: From	54757	-6	1	1
54765	mileage	Car Mileage	54757	-6	1	1
54766	make	Make	54757	-6	1	1
54767	model	Model	54757	-6	1	1
54768	year_max	to	54757	-6	1	1
54769	zipcode	from Zip Code	54757	-6	1	1
54772	keywordSearchModifier		54771	-6	1	1
54773	Generic_dt_lvl_01	Find active listings placed within	54771	-6	1	1
54774	makeSelections	Make	54771	-6	1	1
54775	yearSelections		54771	-6	1	1
54776	zc	miles of ZIP Code	54771	-6	1	1
54777	userKeywords	itemal Search Words	54771	-6	1	1
54778	Generic_dcml_lvl_03	Price Range	54771	-6	1	1
54779	modelSelections		54771	-6	1	1
54780	Generic_intgr_lvl_10	Mileage Range	54771	-6	1	1
54781	pageIncrement	Display, listings per page	54771	-6	1	1
54782	searchType	How do you want to search?	54771	-6	1	1
54783	rd	Search within	54771	-6	1	1
54784	citylist		54771	-6	1	1
54787	comments	Comments/Notes:(optional)	54786	-6	1	1
54788	phone2num		54786	-6	1	1
54789	phoneprefix		54786	-6	1	1
54790	phoneext	x	54786	-6	1	1
54791	address	Address	54786	-6	1	1
54792	phone2area	Secondary Phone	54786	-6	1	1
54793	colorext	Colors Exterior	54786	-6	1	1
54794	zip	Zip	54786	-6	1	1
54795	namelast	Last	54786	-6	1	1
54796	city	City	54786	-6	1	1
54797	phone2prefix		54786	-6	1	1
54798	buytime	I plan to buy within	54786	-6	1	1
54799	namefirst	Buyer Information   Name: First	54786	-6	1	1
54800	make	Choose Make	54786	-6	1	1
54801	tradein	Trade-In	54786	-6	1	1
54802	phonearea	Primary Phone	54786	-6	1	1
54803	year1	Year	54786	-6	1	1
54804	payment	Payment Method	54786	-6	1	1
54805	year2	Choose Newest Year	54786	-6	1	1
54806	phone2ext	x	54786	-6	1	1
54807	model	Model	54786	-6	1	1
54808	state	State	54786	-6	1	1
54809	email	E-mail	54786	-6	1	1
54810	phonenum		54786	-6	1	1
54811	contacttime	Best time to contact	54786	-6	1	1
54812	colorint	Interior	54786	-6	1	1
54815	keywords	key words	54814	-6	1	1
54816	make	Make	54814	-6	1	1
54817	date	Date	54814	-6	1	1
54820	x_make_id	Make	54819	-6	1	1
54821	x_yearqual	Year is	54819	-6	1	1
54822	sort_order	Sort By	54819	-6	1	1
54823	x_model	Model	54819	-6	1	1
54824	start		54819	-6	1	1
54825	vpp	Listings Per Page	54819	-3	1	1
54826	keywords	Or, enter some keywords:    Tips for searching models	54819	-3	1	1
54827	body_type	Body Type	54819	-6	1	1
54828	x_price_range	Price Range	54819	-6	1	1
54829	x_year		54819	-6	1	1
54830	x_dealer_id		54819	-6	1	1
54833	distance	Distance:	54832	-6	1	1
54834	new_used	Choose Inventory Criteria       Inventory Type:     New and Used     New     Used	54832	-6	1	1
54835	newint		54832	-6	1	1
54836	instate	This State Only:	54832	-6	1	1
54837	newyear	Latest Year:      Max Price:	54832	-3	1	1
54838	vehicle	Select Vehicle Make and Model. Use your Control key to select up to 4 vehicles.	54832	-6	1	1
54839	int		54832	-6	1	1
54840	minprice		54832	-3	1	1
54841	zip		54832	-3	1	1
54842	oldyear	Earliest Year:      Min Price:	54832	-3	1	1
54843	session_id		54832	-6	1	1
54844	maxprice		54832	-3	1	1
54847	phone		54846	-3	1	1
54848	Interior_Color1_ch	Interior Color:	54846	-6	1	1
54849	eveningprefix		54846	-3	1	1
54850	vehicleyear	Vehicle Year:	54846	-3	1	1
54851	First_Name_ch	Personal Information:     First Name:	54846	-3	1	1
54852	Num_Cylinders_ti	Number  of Cylinders:	54846	-6	1	1
54853	Best_Contact_Time_ch	Best Time to Contact:	54846	-6	1	1
54854	vehiclemodel	Vehicle Model:	54846	-3	1	1
54855	Exterior_Color1_ch	Exterior Color:	54846	-6	1	1
54856	eveningarea	Evening Phone:  (	54846	-3	1	1
54857	vehiclemake	Vehicle Make:	54846	-6	1	1
54858	time_line_ch	Buying How Soon:	54846	-6	1	1
54859	tradeyear	If so, what year is your trade?	54846	-3	1	1
54860	prefix		54846	-3	1	1
54861	Purchase_Type_ch	Payment Method:	54846	-6	1	1
54862	Entered_Postal_Code_vch	Postal Code:	54846	-3	1	1
54863	Num_Doors_ti	Number of Doors:	54846	-6	1	1
54864	tradein	Do you have a trade-in?	54846	-6	1	1
54865	Country_ch	Country:	54846	-6	1	1
54866	Street_Address_1_ch	Street Address:	54846	-3	1	1
54867	eveningphone		54846	-3	1	1
54868	Last_Name_ch	Last Name:	54846	-3	1	1
54869	warranty	Are you interested in extended warranty information?	54846	-6	1	1
54870	State_ch	State:	54846	-6	1	1
54871	redirect		54846	-6	1	1
54872	Transmission_ch	Transmission:	54846	-6	1	1
54873	area	Daytime Phone:  (	54846	-3	1	1
54874	City_ch	City:	54846	-3	1	1
54875	Email_Addr_ch	Email:  Vehicle Information	54846	-3	1	1
54878	minPrice	Price Range Between $	54877	-3	1	1
54879	endyear	and  (yyyy)	54877	-3	1	1
54880	attr1		54877	-6	1	1
54881	categorymap		54877	-6	1	1
54882	attr2	Vehicle Model  tips	54877	-6	1	1
54883	exclude	Words to Exclude	54877	-3	1	1
54884	maxPrice	and	54877	-3	1	1
54885	from		54877	-6	1	1
54886	startyear	Vehicle Year Between	54877	-3	1	1
54887	attr0	Vehicle Make	54877	-6	1	1
54888	siteid		54877	-6	1	1
54889	query2	Search Title	54877	-3	1	1
54890	cgiurl		54877	-6	1	1
54891	category1		54877	-6	1	1
54892	ebaytag1code	Item Location	54877	-6	1	1
54893	s_partnerid		54877	-6	1	1
54894	MfcISAPICommand		54877	-6	1	1
54895	SortProperty	Sort By	54877	-6	1	1
54896	ebaytag1		54877	-6	1	1
54899	PARAME	MODEL	54898	-6	1	1
54900	PARAMK	YEAR	54898	-6	1	1
54901	PARAMD	MAKE	54898	-6	1	1
54902	PARAML	MILEAGE	54898	-6	1	1
54905	x_make_id	Make	54904	-6	1	1
54906	x_yearqual	Year is	54904	-6	1	1
54907	sort_order	Sort By	54904	-6	1	1
54908	x_model	Model	54904	-6	1	1
54909	vpp	Listings Per Page	54904	-6	1	1
54910	keywords	Or, enter some keywords	54904	-6	1	1
54911	body_type	Body Type	54904	-6	1	1
54912	x_price_range	Price Range	54904	-6	1	1
54913	x_year		54904	-6	1	1
54914	ON	Show vehicles with prices only	54904	-6	1	1
54917	PostalCode	Postal Code	54916	-6	1	1
54918	oCountry		54916	-6	1	1
54919	Model	Model	54916	-6	1	1
54920	minPrice	Price(US$)	54916	-6	1	1
54921	oStateOrProvince		54916	-6	1	1
54922	minYear	Year	54916	-6	1	1
54923	Make	Make	54916	-6	1	1
54924	Country	Country	54916	-6	1	1
54925	maxPrice	to	54916	-6	1	1
54926	City	City	54916	-6	1	1
54927	maxYear	to	54916	-6	1	1
54928	StateOrProvince	State/Province	54916	-6	1	1
54931	showImages		54930	-6	1	1
54932	minPrice	From	54930	-6	1	1
54933	miles	Miles	54930	-6	1	1
54934	minYear	YEAR RANGE From	54930	-6	1	1
54935	MAKE	Make	54930	-6	1	1
54936	resultsPerPage	results per page	54930	-6	1	1
54937	maxPrice	To	54930	-6	1	1
54938	model	MODEL/TYPE	54930	-6	1	1
54939	maxYear	To	54930	-6	1	1
54940	SEARCH	search	54930	-6	1	1
54943	d_maxYear	to	54942	-6	1	1
54944	d_minPrice	Price Range	54942	-6	1	1
54945	d_minYear	Year Range 4-digit years, please	54942	-6	1	1
54946	d_Category	Vehicle Body Style	54942	-6	1	1
54947	d_vehicle_new_flag		54942	-6	1	1
54948	x_number_of_rows	Vehicles per page	54942	-6	1	1
54949	d_Make_required	Vehicle Make	54942	-6	1	1
54950	d_maxMileage	Vehicle Mileage	54942	-6	1	1
54951	d_maxPrice	to	54942	-6	1	1
54952	d_model_name	Vehicle Model	54942	-6	1	1
54955	SearchRadius	SearchRadius	54954	-3	1	1
54956	Doors	Doors	54954	-3	1	1
54957	Cykinders	Cylinders	54954	-3	1	1
54958	ModeOfContact	ModeOfContact	54954	-3	1	1
54959	WhenPosted	WhenPosted	54954	-3	1	1
54960	Roof	Roof	54954	-3	1	1
54961	Keywords	Keywords	54954	-3	1	1
54962	PoweWindows	PowerWindows	54954	-3	1	1
54963	FirstName	FirstName	54954	-3	1	1
54964	HomePhone	HomePhone	54954	-3	1	1
54965	Transmission	Transmission	54954	-3	1	1
54966	TradeInYear	RadeInYear	54954	-3	1	1
54967	Dealer	Dealer	54954	-3	1	1
54968	Cassette	Cassette	54954	-3	1	1
54969	SearchBy	SearchBy	54954	-3	1	1
54970	BodyStyle	BodyStyle	54954	-3	1	1
54971	HowHeard	HowHeard	54954	-3	1	1
54972	Cruise	Cruise	54954	-3	1	1
54973	KeywordModifier	KeywordModifier	54954	-3	1	1
54974	Interior	Interior	54954	-3	1	1
54975	CDPlayer	CDPlayer	54954	-3	1	1
54976	BestTime	BestTime	54954	-3	1	1
54977	YearMin	YearMin	54954	-3	1	1
54978	NewUsed	NewUsed	54954	-3	1	1
54979	Model	Model	54954	-3	1	1
54980	SaleBy	SaleBy	54954	-3	1	1
54981	PriceMin	PriceMin	54954	-3	1	1
54982	YearMax	YearMax	54954	-3	1	1
54983	Drive	Drive	54954	-3	1	1
54984	CellPhone	CellPhone	54954	-3	1	1
54985	AirConditioning	AirConditioning	54954	-3	1	1
54986	Brakes	Brakes	54954	-3	1	1
54987	Color	Color	54954	-3	1	1
54988	AirBags	AirBags	54954	-3	1	1
54989	ZipCode	ZipCode	54954	-3	1	1
54990	Prefix	Prefix	54954	-3	1	1
54991	InStateOnly	InStateOnly	54954	-3	1	1
54992	MileageMax	MileageMax	54954	-3	1	1
54993	Make	Make	54954	-3	1	1
54994	PowerSteering	PowerSteering	54954	-3	1	1
54995	WorkPhone	WorkPhone	54954	-3	1	1
54996	StreetAddress	StreetAddress	54954	-3	1	1
54997	Region - State	State	54954	-3	1	1
54998	TimeFrame	TimeFrame	54954	-3	1	1
54999	Finance	Finance	54954	-3	1	1
55000	OtherOptions	OtherOptions	54954	-3	1	1
55001	YearQualifier	YearQualifier	54954	-3	1	1
55002	Leather	Leather	54954	-3	1	1
55003	Region - City	City	54954	-3	1	1
55004	SortOrder	SortOrder	54954	-3	1	1
55005	TradeIn	TradeIn	54954	-3	1	1
55006	Email	Email	54954	-3	1	1
55007	Fuel	Fuel	54954	-3	1	1
55008	PriceMax	PriceMax	54954	-3	1	1
55009	PowerLocks	PowerLocks	54954	-3	1	1
55010	Comments	Comments	54954	-3	1	1
55011	AdNumber	AdNumber	54954	-3	1	1
55012	ShowImage	ShowImage	54954	-3	1	1
55013	NumPerPage	NumPerPage	54954	-3	1	1
55014	LastName	LastName	54954	-3	1	1
55017	distance	Distance:	55016	-6	1	1
55018	new_used	Inventory Type:	55016	-6	1	1
55019	sort	Sort by:	55016	-6	1	1
55020	newint		55016	-6	1	1
55021	make		55016	-6	1	1
55022	newyear	to:	55016	-6	1	1
55023	int		55016	-6	1	1
55024	minprice	Price	55016	-6	1	1
55025	oldyear	Narrow Your Search Year:	55016	-6	1	1
55026	minmiles	Milage	55016	-6	1	1
55027	numresults	Organize Your Results      Results per page:	55016	-6	1	1
55028	instate	This State Only:	55016	-6	1	1
55029	page		55016	-6	1	1
55030	model		55016	-6	1	1
55031	zip	Zip Code:	55016	-3	1	1
55032	maxmiles	to	55016	-6	1	1
55033	maxprice	to	55016	-6	1	1
55036	vto	to	55035	-6	1	1
55037	vType	Type	55035	-6	1	1
55038	Make	Make	55035	-6	1	1
55039	vfrom	Year	55035	-6	1	1
55042	state	State	55041	-6	1	1
55043	maxrnt	Max Rent	55041	-3	1	1
55044	city	City	55041	-3	1	1
55047	szState	Location US State	55046	-6	1	1
55048	iCategory	Category	55046	-6	1	1
55049	szCountry	Location International	55046	-6	1	1
55052	TypeUse2	Type of Use	55051	-6	1	1
55053	strCityName	City	55051	-3	1	1
55054	intStateId	State	55051	-6	1	1
55055	Sqft	Size SF	55051	-3	1	1
55056	strZip	Zipcode	55051	-3	1	1
55057	TypeUse1	Price	55051	-3	1	1
55060	catb	Sq.Ft.	55059	-6	1	1
55061	beds	Rooms	55059	-6	1	1
55062	state	State/Province	55059	-6	1	1
55063	cata	Price	55059	-6	1	1
55064	catc	Property	55059	-6	1	1
55067	size_bot	Minimum acreage	55066	-6	1	1
55068	price_top	Maximum price	55066	-6	1	1
55069	size_top	Maximum acreage	55066	-6	1	1
55070	state	state	55066	-6	1	1
55071	zip	Zip	55066	-3	1	1
55074	country	Country	55073	-6	1	1
55075	priceRange2	Price Range to	55073	-3	1	1
55076	acreage2	Acreage to	55073	-3	1	1
55077	state	State	55073	-6	1	1
55078	acreage1	Acreage	55073	-3	1	1
55079	priceRange1	Price Range	55073	-3	1	1
55082	county	County	55081	-3	1	1
55083	footage2	Size to	55081	-6	1	1
55084	type	Property Type	55081	-6	1	1
55085	bathroom2	Bathrooms to	55081	-6	1	1
55086	bedroom2	Bedrooms to	55081	-6	1	1
55087	state	State	55081	-6	1	1
55088	footage1	Size	55081	-6	1	1
55089	price1	Price	55081	-6	1	1
55090	price2	Price to	55081	-6	1	1
55091	bathroom1	Bathrooms	55081	-6	1	1
55092	bedroom1	Bedrooms	55081	-6	1	1
55093	city	City/Cities	55081	-3	1	1
55096	PriceHigh	to	55095	-6	1	1
55097	PriceLow	Price from	55095	-6	1	1
55098	market	Area	55095	-6	1	1
55099	st	State	55095	-6	1	1
55102	list price	price range	55101	-6	1	1
55103	zip code	zip	55101	-3	1	1
55104	property type	property type	55101	-6	1	1
55105	state	state	55101	-6	1	1
55106	city name	city	55101	-3	1	1
55109	numBaths		55108	-3	1	1
55110	minPrice		55108	-3	1	1
55111	pCategory		55108	-3	1	1
55112	minLeaseRate		55108	-3	1	1
55113	maxPrice		55108	-3	1	1
55114	agentName		55108	-3	1	1
55115	maxAcerage		55108	-3	1	1
55116	agencyName		55108	-3	1	1
55117	maxYear		55108	-3	1	1
55118	county		55108	-3	1	1
55119	country		55108	-3	1	1
55120	squareFootage		55108	-3	1	1
55121	maxLeaseRate		55108	-3	1	1
55122	minAcerage		55108	-3	1	1
55123	minYear		55108	-3	1	1
55124	state		55108	-3	1	1
55125	zip		55108	-3	1	1
55126	numBeds		55108	-3	1	1
55127	pCharacteristic		55108	-3	1	1
55128	city		55108	-3	1	1
55131	min_price	Price Range	55130	-6	1	1
55132	max_year	Year 4-digits to	55130	-3	1	1
55133	bdrms	Minimum Number of Bedrooms	55130	-6	1	1
55134	baths	Minimum Number of Bathrooms	55130	-6	1	1
55135	max_price	Price Range to	55130	-6	1	1
55136	state	State	55130	-6	1	1
55137	city	City	55130	-6	1	1
55138	min_year	Year 4-digits from	55130	-3	1	1
55141	PriceHigh	Price Range To	55140	-6	1	1
55142	PriceLow	Price Range	55140	-6	1	1
55143	State	State	55140	-6	1	1
55146	BATHS	# of Bathrooms	55145	-6	1	1
55147	FOOTAGE	Square footage	55145	-3	1	1
55148	PRICE	Price range	55145	-3	1	1
55149	OFFICE_ID	Real Estate Agency	55145	-6	1	1
55150	AGENT_ID	Realtor	55145	-6	1	1
55151	BEDS	# of Bedrooms	55145	-6	1	1
55154	TOTL_BED	Features Bedrooms	55153	-6	1	1
55155	TOTL_FBATH	Features Bathrooms	55153	-6	1	1
55156	PropertyType	Property Type	55153	-6	1	1
55157	TOTL_FL_SQ	Features Minimum Floor Area Sq. Ft., Sq. M.	55153	-3	1	1
55158	Property Characteristics		55153	-6	1	1
55159	Price range	Price range	55153	-6	1	1
55160	LCountry	Country	55153	-6	1	1
55161	TOTL_LOT_SQ	Features Minimum Lot Size Acres, Hectares, Sq. M., Sq. Ft.	55153	-3	1	1
55164	Garage	Features Garage	55163	-6	1	1
55165	PropertyCity	City	55163	-3	1	1
55166	PropertyState	State	55163	-6	1	1
55167	PriceMax	Price Maximum	55163	-6	1	1
55168	PropertyCountry	Country	55163	-3	1	1
55169	PropertyType	Features Property Type	55163	-6	1	1
55170	PriceMin	Price Minimum	55163	-6	1	1
55171	Bedrooms	Features Bedrooms	55163	-6	1	1
55172	Baths	Features Baths	55163	-6	1	1
55175	selHouseType		55174	-6	1	1
55176	selBaths	Baths	55174	-6	1	1
55177	selState	State	55174	-6	1	1
55178	selBeds	Bedrooms	55174	-6	1	1
55179	selListingType	Price	55174	-6	1	1
55180	selMetro	City/Metro	55174	-6	1	1
55183	tScMaxUnits	Size/Price Unit range (Apartments only)	55182	-3	1	1
55184	tScMaxSfSale	Size/Price Size range SF	55182	-3	1	1
55185	tScMinSfSale	Size/Price Size range SF	55182	-3	1	1
55186	tScMinPriceSale	Size/Price Price range $	55182	-3	1	1
55187	OptionListSelectedTypes	Property Types	55182	-3	1	1
55188	tScMaxPriceSale	Size/Price Price range $	55182	-3	1	1
55189	tScMinUnits	Size/Price Unit range (Apartments only)	55182	-3	1	1
55192	Min_Lease_Price	Lease Price (Per Sq. Ft.) Minimum	55191	-3	1	1
55193	Min_sqf_avail	Property Size (Sq. Ft.) Minimum	55191	-3	1	1
55194	Property_Type	Property Type	55191	-6	1	1
55195	State	Property Location State	55191	-6	1	1
55196	Max_sqf_avail	Property Size (Sq. Ft.) Maximum	55191	-3	1	1
55197	St_Name	Property Location Street Name	55191	-3	1	1
55198	Zip_Code	Property Location Zip Code	55191	-3	1	1
55199	Country	Property Location Country	55191	-6	1	1
55200	City	Property Location City	55191	-3	1	1
55201	Max_Lease_Price	Lease Price (Per Sq. Ft.) Maximum	55191	-3	1	1
55204	cboLotSize	acreage	55203	-6	1	1
55205	cboLotTypeID	Parcel Type	55203	-6	1	1
55206	cboCountyID	County	55203	-6	1	1
55207	cboStateID	State or Country	55203	-6	1	1
55208	cboMaxPrice	Maximum price	55203	-6	1	1
55209	cboMinPrice	Minimum price	55203	-6	1	1
55212	min_price	Sale Price USD	55211	-3	1	1
55213	min_lease	Lease Rate per sq. ft.  $	55211	-3	1	1
55214	min_sqft	Square Feet sq. ft.	55211	-3	1	1
55215	zon_id	Zoning	55211	-6	1	1
55216	min_acre	Acreage acre(s)	55211	-3	1	1
55217	max_acre	Acreage acre(s) to	55211	-3	1	1
55218	max_lease	Lease Rate per sq. ft. to $	55211	-3	1	1
55219	max_price	Sale Price USD to	55211	-3	1	1
55220	max_sqft	Square Feet sq. ft. to	55211	-3	1	1
55221	pt_id	Property Types	55211	-6	1	1
55222	zip	City/St/Zip Zip	55211	-3	1	1
55223	pa_id	Property Availability	55211	-6	1	1
55224	city	City/St/Zip City	55211	-3	1	1
55225	st	City/St/Zip St	55211	-6	1	1
55228	ch_l4pri	Price Range to	55227	-6	1	1
55229	cl_bds	Beds	55227	-6	1	1
55230	cl_l4pri	Price Range	55227	-6	1	1
55231	cl_bth	Baths	55227	-6	1	1
55232	csz	City, State, or Zip	55227	-3	1	1
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
52763	Availability	
52764	Warehouses	
52765	Music	
52766	Books	
52795	Availability	
52796	Warehouses	
52797	CDCategories	
52798	Artist	
52799	CDs	
52800	Author	
52801	CDTypes	
52802	Books	
52803	BookCategories	
52834	Studios	
52835	BookInventory	
52836	Authors	
52837	Publishers	
52838	CDs	
52839	Keywords	
52840	CDInventory	
52841	Books	
52842	Warehouse	
52843	Person	
52844	Pricing	
52845	Performers	
52888	Availability	
52889	Products	
52890	Warehouses	
52891	Authors	
52892	Keywords	
52893	artists	
52894	Music	
52895	Books	
52918	WarehouseInformation	
52919	ProductAvailability	
52920	CDs	
52921	Books	
52922	Inventory	
52948	Availability	
52949	CDToArtist	
52950	ContactInfo	
52951	Product	
52952	DiscountedItem	
52953	CD	
52954	BookToAuthor	
52955	Warehouse	
52956	Book	
52984	Stock	
52985	Price	
52986	Genre	
52987	Product	
52988	WarehouseAttrib	
52989	Keywords	
52990	Artists	
52991	Books	
52992	Warehouse	
52993	Audio	
53020	Availability	
53021	Products	
53022	Warehouses	
53023	Music2Artists	
53024	Warehouses2Locations	
53025	Music	
53026	Books	
53027	Books2Keywords	
53028	Warehouses2Contacts	
53029	Books2Authors	
53051	Stock	
53052	Products	
53053	Warehouses	
53054	AuthorsArtists	
53055	Keywords	
53056	CDs	
53057	Books	
53082	Availability	
53083	BooksAndMusic	
53084	Warehouse	
53109	CD	
53110	WareHouse2Book	
53111	Artist	
53112	Author	
53113	WareHouse2CD	
53114	Book	
53115	WareHouse	
53138	warehouse	
53139	artist2music	
53140	bookCategory	
53141	artist	
53142	music2genre	
53143	book2Category	
53144	keyword	
53145	musicType	
53146	musicStock	
53147	book	
53148	book2Author	
53149	music	
53150	bookStock	
53151	genre	
53152	author	
53187	Availability	
53188	Items	
53189	Warehouses	
53190	Authors	
53191	Keywords	
53192	Artists	
53193	Books	
53194	Music	
53221	cds	
53222	warehouse	
53223	info	
53224	available	
53225	books	
53226	location	
53256	Availability	
53257	CD	
53258	Information	
53259	Books	
53260	Location	
53261	WareHouse	
53289	Warehouses	
53290	BooksAuthors	
53291	Authors	
53292	MusicGenres	
53293	BooksWarehouses	
53294	BookGenres	
53295	Artists	
53296	Music	
53297	Books	
53298	CDTypes	
53299	MusicWarehouses	
53300	MusicArtists	
53328	Stock	
53329	Products	
53330	Warehouses	
53331	Discounts	
53332	CDs	
53333	CDTracks	
53334	Books	
53367	Warehouses	
53368	CompactDiscs	
53369	BookAvailability	
53370	Books	
53371	CdAvailability	
53393	Availability	
53394	Warehouses	
53395	Music	
53396	Books	
53425	MusicsTypes	
53426	Product	
53427	BooksCategories	
53428	BooksKeywords	
53429	Musics	
53430	Books	
53431	WarehousesInfo	
53432	MusicsGenres	
53433	Inventory	
53463	Images	
53464	Products	
53465	Discounts	
53466	Reviews	
53467	Authors	
53468	Artists	
53469	Types	
53470	Sounds	
53471	Music	
53472	Wharehouses	
53473	Managers	
53474	Availability	
53475	Groups	
53476	Employees	
53477	Categories	
53478	Genres	
53479	ProductCategories	
53480	Users	
53481	RecordLabels	
53482	Books	
53483	Videos	
53484	Addresses	
53539	Review	
53540	Reviewer	
53541	Author	
53542	Music	
53543	Warehouse	
53544	Book	
53545	Storage	
53576	Review	
53577	InventoryOrders	
53578	Item	
53579	CD	
53580	CD_Track	
53581	Inventory	
53582	Warehouse	
53583	Book	
53634	Availability	
53635	Items	
53636	Warehouses	
53637	Music	
53638	Books	
53672	Availability	
53673	Genre	
53674	CreaterShip	
53675	Product	
53676	Creater	
53677	Association	
53678	Music	
53679	Book	
53680	WareHouse	
53709	Products	
53710	Discounts	
53711	Authors	
53712	Orders	
53713	CDs	
53714	Classifications	
53715	Customers	
53716	OrderItems	
53717	Inventory	
53718	Warehouses	
53719	Producers	
53720	Genres	
53721	Books	
53722	Addresses	
53755	ShippingDeals	
53756	ProductReviews	
53757	Product	
53758	WarehouseInfo	
53759	Music	
53760	Books	
53761	Inventory	
53789	Book_title	
53790	reviewer	
53791	category	
53792	Keywords	
53793	Album_edition	
53794	Warehouse	
53795	artist_item	
53796	Track	
53797	Album	
53798	Review	
53799	book_edition	
53800	author_artist	
53801	Location	
53802	Item_location	
53871	Availability	
53872	Type	
53873	Keyword	
53874	CD	
53875	Artist	
53876	Author	
53877	Warehouse	
53878	Book	
53908	Availability	
53909	Warehouses	
53910	Music	
53911	Books	
53940	Availability	
53941	Contacts	
53942	CDs	
53943	Books	
53944	Warehouse	
53974	Discounts	
53975	Reviews	
53976	Employee	
53977	CD	
53978	Availability	
53979	Warehouses	
53980	BookClassification	
53981	CDClassification	
53982	Categories	
53983	CreditCard	
53984	Users	
53985	Books	
53986	Addresses	
54035	Availability	
54036	Books	
54049	available	
54050	books	
54064	Availability	
54065	Author	
54066	Books	
54067	BookCategories	
54084	BookInventory	
54085	Authors	
54086	Publishers	
54087	Keywords	
54088	Books	
54089	Person	
54090	Pricing	
54122	Product	
54123	BooksCategories	
54124	BooksKeywords	
54125	Books	
54126	Inventory	
54141	ProductAvailability	
54142	Books	
54143	Inventory	
54157	Availability	
54158	Product	
54159	DiscountedItem	
54160	BookToAuthor	
54161	Book	
54179	Availability	
54180	Products	
54181	Books	
54182	Books2Keywords	
54183	Books2Authors	
54195	Availability	
54196	BooksAndMusic	
54210	Stock	
54211	Price	
54212	Genre	
54213	Keywords	
54214	Artists	
54215	Books	
54229	Availability	
54230	Items	
54231	Authors	
54232	Keywords	
54233	Books	
54248	Stock	
54249	Products	
54250	Discounts	
54251	Books	
54270	Stock	
54271	Products	
54272	AuthorsArtists	
54273	Keywords	
54274	Books	
54289	BookAvailability	
54290	Books	
54301	Availability	
54302	Books	
54315	Availability	
54316	Products	
54317	Discounts	
54318	Authors	
54319	Categories	
54320	ProductCategories	
54321	Books	
54340	Author	
54341	Book	
54342	Storage	
54365	Availability	
54366	Products	
54367	Authors	
54368	Keywords	
54369	Books	
54383	Item	
54384	Inventory	
54385	Book	
54408	keyword	
54409	book2Category	
54410	book2Author	
54411	book	
54412	bookStock	
54413	bookCategory	
54414	author	
54428	BooksAuthors	
54429	Authors	
54430	BooksWarehouses	
54431	BookGenres	
54432	Books	
54447	Availability	
54448	Author	
54449	BookToAuthor	
54450	Books	
54473	Availability	
54474	Items	
54475	Books	
54492	WareHouse2Book	
54493	Author	
54494	Book	
54506	Availability	
54507	Books	
54521	Availability	
54522	Genre	
54523	CreaterShip	
54524	Product	
54525	Creater	
54526	Book	
54544	1stopauto_new	
54550	1stopauto_used	
54562	401carfinder	
54572	AtkinsKrollIncofGuam	
54588	amarilloAutos	
54614	autobytel	
54625	amarilloautochooser	
54635	AutoMob	
54661	AutoNation	
54669	Autonet	
54688	Autonetca	
54703	AutoPoint	
54711	AutoWeb	
54719	buycars	
54741	carcast	
54757	carprices	
54771	cars	
54786	carsearch	
54814	AsktheManufacturerSearch	
54819	colvinauto	
54832	dealernet	
54846	discountautopricing	
54877	ebaymotors	
54898	BondesenChevrolet_Cadillac	
54904	GetAuto	
54916	GotCars4sale	
54930	HerzogMeierAutoCenter	
54942	HoakMotorsInc	
54954	mediated	
55016	nada	
55035	Tourist	
55041	Apartments	
55046	biztrader	
55051	cityfeet	
55059	CommercialRealEstate	
55066	eLandUSA	
55073	EquestrianLIVING	
55081	eSearchHomes-1	
55095	HomeBuilders	
55101	homeseekers	
55108	mediated	
55130	NationalMobile	
55140	new-home-builders	
55145	NorthIdahoHomeseekers	
55153	PlanetProperties	
55163	Property2000-1	
55174	RealEstate	
55182	RealtyInvestor-1	
55191	SpaceForLease	
55203	USLots	
55211	webrealestate	
55227	YahooRealEstate	
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
52762	Add	52763
52762	Add	52764
52762	Add	52765
52762	Add	52766
52762	Add	52767
52762	Add	52768
52762	Add	52769
52762	Add	52770
52762	Add	52771
52762	Add	52772
52762	Add	52773
52762	Add	52774
52762	Add	52775
52762	Add	52776
52762	Add	52777
52762	Add	52778
52762	Add	52779
52762	Add	52780
52762	Add	52781
52762	Add	52782
52762	Add	52783
52762	Add	52784
52762	Add	52785
52762	Add	52786
52762	Add	52787
52762	Add	52788
52762	Add	52789
52762	Add	52790
52762	Add	52791
52762	Add	52792
52762	Add	52793
52794	Add	52795
52794	Add	52796
52794	Add	52797
52794	Add	52798
52794	Add	52799
52794	Add	52800
52794	Add	52801
52794	Add	52802
52794	Add	52803
52794	Add	52804
52794	Add	52805
52794	Add	52806
52794	Add	52807
52794	Add	52808
52794	Add	52809
52794	Add	52810
52794	Add	52811
52794	Add	52812
52794	Add	52813
52794	Add	52814
52794	Add	52815
52794	Add	52816
52794	Add	52817
52794	Add	52818
52794	Add	52819
52794	Add	52820
52794	Add	52821
52794	Add	52822
52794	Add	52823
52794	Add	52824
52794	Add	52825
52794	Add	52826
52794	Add	52827
52794	Add	52828
52794	Add	52829
52794	Add	52830
52794	Add	52831
52794	Add	52832
52833	Add	52834
52833	Add	52835
52833	Add	52836
52833	Add	52837
52833	Add	52838
52833	Add	52839
52833	Add	52840
52833	Add	52841
52833	Add	52842
52833	Add	52843
52833	Add	52844
52833	Add	52845
52833	Add	52846
52833	Add	52847
52833	Add	52848
52833	Add	52849
52833	Add	52850
52833	Add	52851
52833	Add	52852
52833	Add	52853
52833	Add	52854
52833	Add	52855
52833	Add	52856
52833	Add	52857
52833	Add	52858
52833	Add	52859
52833	Add	52860
52833	Add	52861
52833	Add	52862
52833	Add	52863
52833	Add	52864
52833	Add	52865
52833	Add	52866
52833	Add	52867
52833	Add	52868
52833	Add	52869
52833	Add	52870
52833	Add	52871
52833	Add	52872
52833	Add	52873
52833	Add	52874
52833	Add	52875
52833	Add	52876
52833	Add	52877
52833	Add	52878
52833	Add	52879
52833	Add	52880
52833	Add	52881
52833	Add	52882
52833	Add	52883
52833	Add	52884
52833	Add	52885
52833	Add	52886
52887	Add	52888
52887	Add	52889
52887	Add	52890
52887	Add	52891
52887	Add	52892
52887	Add	52893
52887	Add	52894
52887	Add	52895
52887	Add	52896
52887	Add	52897
52887	Add	52898
52887	Add	52899
52887	Add	52900
52887	Add	52901
52887	Add	52902
52887	Add	52903
52887	Add	52904
52887	Add	52905
52887	Add	52906
52887	Add	52907
52887	Add	52908
52887	Add	52909
52887	Add	52910
52887	Add	52911
52887	Add	52912
52887	Add	52913
52887	Add	52914
52887	Add	52915
52887	Add	52916
52917	Add	52918
52917	Add	52919
52917	Add	52920
52917	Add	52921
52917	Add	52922
52917	Add	52923
52917	Add	52924
52917	Add	52925
52917	Add	52926
52917	Add	52927
52917	Add	52928
52917	Add	52929
52917	Add	52930
52917	Add	52931
52917	Add	52932
52917	Add	52933
52917	Add	52934
52917	Add	52935
52917	Add	52936
52917	Add	52937
52917	Add	52938
52917	Add	52939
52917	Add	52940
52917	Add	52941
52917	Add	52942
52917	Add	52943
52917	Add	52944
52917	Add	52945
52917	Add	52946
52947	Add	52948
52947	Add	52949
52947	Add	52950
52947	Add	52951
52947	Add	52952
52947	Add	52953
52947	Add	52954
52947	Add	52955
52947	Add	52956
52947	Add	52957
52947	Add	52958
52947	Add	52959
52947	Add	52960
52947	Add	52961
52947	Add	52962
52947	Add	52963
52947	Add	52964
52947	Add	52965
52947	Add	52966
52947	Add	52967
52947	Add	52968
52947	Add	52969
52947	Add	52970
52947	Add	52971
52947	Add	52972
52947	Add	52973
52947	Add	52974
52947	Add	52975
52947	Add	52976
52947	Add	52977
52947	Add	52978
52947	Add	52979
52947	Add	52980
52947	Add	52981
52947	Add	52982
52983	Add	52984
52983	Add	52985
52983	Add	52986
52983	Add	52987
52983	Add	52988
52983	Add	52989
52983	Add	52990
52983	Add	52991
52983	Add	52992
52983	Add	52993
52983	Add	52994
52983	Add	52995
52983	Add	52996
52983	Add	52997
52983	Add	52998
52983	Add	52999
52983	Add	53000
52983	Add	53001
52983	Add	53002
52983	Add	53003
52983	Add	53004
52983	Add	53005
52983	Add	53006
52983	Add	53007
52983	Add	53008
52983	Add	53009
52983	Add	53010
52983	Add	53011
52983	Add	53012
52983	Add	53013
52983	Add	53014
52983	Add	53015
52983	Add	53016
52983	Add	53017
52983	Add	53018
53019	Add	53020
53019	Add	53021
53019	Add	53022
53019	Add	53023
53019	Add	53024
53019	Add	53025
53019	Add	53026
53019	Add	53027
53019	Add	53028
53019	Add	53029
53019	Add	53030
53019	Add	53031
53019	Add	53032
53019	Add	53033
53019	Add	53034
53019	Add	53035
53019	Add	53036
53019	Add	53037
53019	Add	53038
53019	Add	53039
53019	Add	53040
53019	Add	53041
53019	Add	53042
53019	Add	53043
53019	Add	53044
53019	Add	53045
53019	Add	53046
53019	Add	53047
53019	Add	53048
53019	Add	53049
53050	Add	53051
53050	Add	53052
53050	Add	53053
53050	Add	53054
53050	Add	53055
53050	Add	53056
53050	Add	53057
53050	Add	53058
53050	Add	53059
53050	Add	53060
53050	Add	53061
53050	Add	53062
53050	Add	53063
53050	Add	53064
53050	Add	53065
53050	Add	53066
53050	Add	53067
53050	Add	53068
53050	Add	53069
53050	Add	53070
53050	Add	53071
53050	Add	53072
53050	Add	53073
53050	Add	53074
53050	Add	53075
53050	Add	53076
53050	Add	53077
53050	Add	53078
53050	Add	53079
53050	Add	53080
53081	Add	53082
53081	Add	53083
53081	Add	53084
53081	Add	53085
53081	Add	53086
53081	Add	53087
53081	Add	53088
53081	Add	53089
53081	Add	53090
53081	Add	53091
53081	Add	53092
53081	Add	53093
53081	Add	53094
53081	Add	53095
53081	Add	53096
53081	Add	53097
53081	Add	53098
53081	Add	53099
53081	Add	53100
53081	Add	53101
53081	Add	53102
53081	Add	53103
53081	Add	53104
53081	Add	53105
53081	Add	53106
53081	Add	53107
53108	Add	53109
53108	Add	53110
53108	Add	53111
53108	Add	53112
53108	Add	53113
53108	Add	53114
53108	Add	53115
53108	Add	53116
53108	Add	53117
53108	Add	53118
53108	Add	53119
53108	Add	53120
53108	Add	53121
53108	Add	53122
53108	Add	53123
53108	Add	53124
53108	Add	53125
53108	Add	53126
53108	Add	53127
53108	Add	53128
53108	Add	53129
53108	Add	53130
53108	Add	53131
53108	Add	53132
53108	Add	53133
53108	Add	53134
53108	Add	53135
53108	Add	53136
53137	Add	53138
53137	Add	53139
53137	Add	53140
53137	Add	53141
53137	Add	53142
53137	Add	53143
53137	Add	53144
53137	Add	53145
53137	Add	53146
53137	Add	53147
53137	Add	53148
53137	Add	53149
53137	Add	53150
53137	Add	53151
53137	Add	53152
53137	Add	53153
53137	Add	53154
53137	Add	53155
53137	Add	53156
53137	Add	53157
53137	Add	53158
53137	Add	53159
53137	Add	53160
53137	Add	53161
53137	Add	53162
53137	Add	53163
53137	Add	53164
53137	Add	53165
53137	Add	53166
53137	Add	53167
53137	Add	53168
53137	Add	53169
53137	Add	53170
53137	Add	53171
53137	Add	53172
53137	Add	53173
53137	Add	53174
53137	Add	53175
53137	Add	53176
53137	Add	53177
53137	Add	53178
53137	Add	53179
53137	Add	53180
53137	Add	53181
53137	Add	53182
53137	Add	53183
53137	Add	53184
53137	Add	53185
53186	Add	53187
53186	Add	53188
53186	Add	53189
53186	Add	53190
53186	Add	53191
53186	Add	53192
53186	Add	53193
53186	Add	53194
53186	Add	53195
53186	Add	53196
53186	Add	53197
53186	Add	53198
53186	Add	53199
53186	Add	53200
53186	Add	53201
53186	Add	53202
53186	Add	53203
53186	Add	53204
53186	Add	53205
53186	Add	53206
53186	Add	53207
53186	Add	53208
53186	Add	53209
53186	Add	53210
53186	Add	53211
53186	Add	53212
53186	Add	53213
53186	Add	53214
53186	Add	53215
53186	Add	53216
53186	Add	53217
53186	Add	53218
53186	Add	53219
53220	Add	53221
53220	Add	53222
53220	Add	53223
53220	Add	53224
53220	Add	53225
53220	Add	53226
53220	Add	53227
53220	Add	53228
53220	Add	53229
53220	Add	53230
53220	Add	53231
53220	Add	53232
53220	Add	53233
53220	Add	53234
53220	Add	53235
53220	Add	53236
53220	Add	53237
53220	Add	53238
53220	Add	53239
53220	Add	53240
53220	Add	53241
53220	Add	53242
53220	Add	53243
53220	Add	53244
53220	Add	53245
53220	Add	53246
53220	Add	53247
53220	Add	53248
53220	Add	53249
53220	Add	53250
53220	Add	53251
53220	Add	53252
53220	Add	53253
53220	Add	53254
53255	Add	53256
53255	Add	53257
53255	Add	53258
53255	Add	53259
53255	Add	53260
53255	Add	53261
53255	Add	53262
53255	Add	53263
53255	Add	53264
53255	Add	53265
53255	Add	53266
53255	Add	53267
53255	Add	53268
53255	Add	53269
53255	Add	53270
53255	Add	53271
53255	Add	53272
53255	Add	53273
53255	Add	53274
53255	Add	53275
53255	Add	53276
53255	Add	53277
53255	Add	53278
53255	Add	53279
53255	Add	53280
53255	Add	53281
53255	Add	53282
53255	Add	53283
53255	Add	53284
53255	Add	53285
53255	Add	53286
53255	Add	53287
53288	Add	53289
53288	Add	53290
53288	Add	53291
53288	Add	53292
53288	Add	53293
53288	Add	53294
53288	Add	53295
53288	Add	53296
53288	Add	53297
53288	Add	53298
53288	Add	53299
53288	Add	53300
53288	Add	53301
53288	Add	53302
53288	Add	53303
53288	Add	53304
53288	Add	53305
53288	Add	53306
53288	Add	53307
53288	Add	53308
53288	Add	53309
53288	Add	53310
53288	Add	53311
53288	Add	53312
53288	Add	53313
53288	Add	53314
53288	Add	53315
53288	Add	53316
53288	Add	53317
53288	Add	53318
53288	Add	53319
53288	Add	53320
53288	Add	53321
53288	Add	53322
53288	Add	53323
53288	Add	53324
53288	Add	53325
53288	Add	53326
53327	Add	53328
53327	Add	53329
53327	Add	53330
53327	Add	53331
53327	Add	53332
53327	Add	53333
53327	Add	53334
53327	Add	53335
53327	Add	53336
53327	Add	53337
53327	Add	53338
53327	Add	53339
53327	Add	53340
53327	Add	53341
53327	Add	53342
53327	Add	53343
53327	Add	53344
53327	Add	53345
53327	Add	53346
53327	Add	53347
53327	Add	53348
53327	Add	53349
53327	Add	53350
53327	Add	53351
53327	Add	53352
53327	Add	53353
53327	Add	53354
53327	Add	53355
53327	Add	53356
53327	Add	53357
53327	Add	53358
53327	Add	53359
53327	Add	53360
53327	Add	53361
53327	Add	53362
53327	Add	53363
53327	Add	53364
53327	Add	53365
53366	Add	53367
53366	Add	53368
53366	Add	53369
53366	Add	53370
53366	Add	53371
53366	Add	53372
53366	Add	53373
53366	Add	53374
53366	Add	53375
53366	Add	53376
53366	Add	53377
53366	Add	53378
53366	Add	53379
53366	Add	53380
53366	Add	53381
53366	Add	53382
53366	Add	53383
53366	Add	53384
53366	Add	53385
53366	Add	53386
53366	Add	53387
53366	Add	53388
53366	Add	53389
53366	Add	53390
53366	Add	53391
53392	Add	53393
53392	Add	53394
53392	Add	53395
53392	Add	53396
53392	Add	53397
53392	Add	53398
53392	Add	53399
53392	Add	53400
53392	Add	53401
53392	Add	53402
53392	Add	53403
53392	Add	53404
53392	Add	53405
53392	Add	53406
53392	Add	53407
53392	Add	53408
53392	Add	53409
53392	Add	53410
53392	Add	53411
53392	Add	53412
53392	Add	53413
53392	Add	53414
53392	Add	53415
53392	Add	53416
53392	Add	53417
53392	Add	53418
53392	Add	53419
53392	Add	53420
53392	Add	53421
53392	Add	53422
53392	Add	53423
53424	Add	53425
53424	Add	53426
53424	Add	53427
53424	Add	53428
53424	Add	53429
53424	Add	53430
53424	Add	53431
53424	Add	53432
53424	Add	53433
53424	Add	53434
53424	Add	53435
53424	Add	53436
53424	Add	53437
53424	Add	53438
53424	Add	53439
53424	Add	53440
53424	Add	53441
53424	Add	53442
53424	Add	53443
53424	Add	53444
53424	Add	53445
53424	Add	53446
53424	Add	53447
53424	Add	53448
53424	Add	53449
53424	Add	53450
53424	Add	53451
53424	Add	53452
53424	Add	53453
53424	Add	53454
53424	Add	53455
53424	Add	53456
53424	Add	53457
53424	Add	53458
53424	Add	53459
53424	Add	53460
53424	Add	53461
53462	Add	53463
53462	Add	53464
53462	Add	53465
53462	Add	53466
53462	Add	53467
53462	Add	53468
53462	Add	53469
53462	Add	53470
53462	Add	53471
53462	Add	53472
53462	Add	53473
53462	Add	53474
53462	Add	53475
53462	Add	53476
53462	Add	53477
53462	Add	53478
53462	Add	53479
53462	Add	53480
53462	Add	53481
53462	Add	53482
53462	Add	53483
53462	Add	53484
53462	Add	53485
53462	Add	53486
53462	Add	53487
53462	Add	53488
53462	Add	53489
53462	Add	53490
53462	Add	53491
53462	Add	53492
53462	Add	53493
53462	Add	53494
53462	Add	53495
53462	Add	53496
53462	Add	53497
53462	Add	53498
53462	Add	53499
53462	Add	53500
53462	Add	53501
53462	Add	53502
53462	Add	53503
53462	Add	53504
53462	Add	53505
53462	Add	53506
53462	Add	53507
53462	Add	53508
53462	Add	53509
53462	Add	53510
53462	Add	53511
53462	Add	53512
53462	Add	53513
53462	Add	53514
53462	Add	53515
53462	Add	53516
53462	Add	53517
53462	Add	53518
53462	Add	53519
53462	Add	53520
53462	Add	53521
53462	Add	53522
53462	Add	53523
53462	Add	53524
53462	Add	53525
53462	Add	53526
53462	Add	53527
53462	Add	53528
53462	Add	53529
53462	Add	53530
53462	Add	53531
53462	Add	53532
53462	Add	53533
53462	Add	53534
53462	Add	53535
53462	Add	53536
53462	Add	53537
53538	Add	53539
53538	Add	53540
53538	Add	53541
53538	Add	53542
53538	Add	53543
53538	Add	53544
53538	Add	53545
53538	Add	53546
53538	Add	53547
53538	Add	53548
53538	Add	53549
53538	Add	53550
53538	Add	53551
53538	Add	53552
53538	Add	53553
53538	Add	53554
53538	Add	53555
53538	Add	53556
53538	Add	53557
53538	Add	53558
53538	Add	53559
53538	Add	53560
53538	Add	53561
53538	Add	53562
53538	Add	53563
53538	Add	53564
53538	Add	53565
53538	Add	53566
53538	Add	53567
53538	Add	53568
53538	Add	53569
53538	Add	53570
53538	Add	53571
53538	Add	53572
53538	Add	53573
53538	Add	53574
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
53633	Add	53634
53633	Add	53635
53633	Add	53636
53633	Add	53637
53633	Add	53638
53633	Add	53639
53633	Add	53640
53633	Add	53641
53633	Add	53642
53633	Add	53643
53633	Add	53644
53633	Add	53645
53633	Add	53646
53633	Add	53647
53633	Add	53648
53633	Add	53649
53633	Add	53650
53633	Add	53651
53633	Add	53652
53633	Add	53653
53633	Add	53654
53633	Add	53655
53633	Add	53656
53633	Add	53657
53633	Add	53658
53633	Add	53659
53633	Add	53660
53633	Add	53661
53633	Add	53662
53633	Add	53663
53633	Add	53664
53633	Add	53665
53633	Add	53666
53633	Add	53667
53633	Add	53668
53633	Add	53669
53633	Add	53670
53671	Add	53672
53671	Add	53673
53671	Add	53674
53671	Add	53675
53671	Add	53676
53671	Add	53677
53671	Add	53678
53671	Add	53679
53671	Add	53680
53671	Add	53681
53671	Add	53682
53671	Add	53683
53671	Add	53684
53671	Add	53685
53671	Add	53686
53671	Add	53687
53671	Add	53688
53671	Add	53689
53671	Add	53690
53671	Add	53691
53671	Add	53692
53671	Add	53693
53671	Add	53694
53671	Add	53695
53671	Add	53696
53671	Add	53697
53671	Add	53698
53671	Add	53699
53671	Add	53700
53671	Add	53701
53671	Add	53702
53671	Add	53703
53671	Add	53704
53671	Add	53705
53671	Add	53706
53671	Add	53707
53708	Add	53709
53708	Add	53710
53708	Add	53711
53708	Add	53712
53708	Add	53713
53708	Add	53714
53708	Add	53715
53708	Add	53716
53708	Add	53717
53708	Add	53718
53708	Add	53719
53708	Add	53720
53708	Add	53721
53708	Add	53722
53708	Add	53723
53708	Add	53724
53708	Add	53725
53708	Add	53726
53708	Add	53727
53708	Add	53728
53708	Add	53729
53708	Add	53730
53708	Add	53731
53708	Add	53732
53708	Add	53733
53708	Add	53734
53708	Add	53735
53708	Add	53736
53708	Add	53737
53708	Add	53738
53708	Add	53739
53708	Add	53740
53708	Add	53741
53708	Add	53742
53708	Add	53743
53708	Add	53744
53708	Add	53745
53708	Add	53746
53708	Add	53747
53708	Add	53748
53708	Add	53749
53708	Add	53750
53708	Add	53751
53708	Add	53752
53708	Add	53753
53754	Add	53755
53754	Add	53756
53754	Add	53757
53754	Add	53758
53754	Add	53759
53754	Add	53760
53754	Add	53761
53754	Add	53762
53754	Add	53763
53754	Add	53764
53754	Add	53765
53754	Add	53766
53754	Add	53767
53754	Add	53768
53754	Add	53769
53754	Add	53770
53754	Add	53771
53754	Add	53772
53754	Add	53773
53754	Add	53774
53754	Add	53775
53754	Add	53776
53754	Add	53777
53754	Add	53778
53754	Add	53779
53754	Add	53780
53754	Add	53781
53754	Add	53782
53754	Add	53783
53754	Add	53784
53754	Add	53785
53754	Add	53786
53754	Add	53787
53788	Add	53789
53788	Add	53790
53788	Add	53791
53788	Add	53792
53788	Add	53793
53788	Add	53794
53788	Add	53795
53788	Add	53796
53788	Add	53797
53788	Add	53798
53788	Add	53799
53788	Add	53800
53788	Add	53801
53788	Add	53802
53788	Add	53803
53788	Add	53804
53788	Add	53805
53788	Add	53806
53788	Add	53807
53788	Add	53808
53788	Add	53809
53788	Add	53810
53788	Add	53811
53788	Add	53812
53788	Add	53813
53788	Add	53814
53788	Add	53815
53788	Add	53816
53788	Add	53817
53788	Add	53818
53788	Add	53819
53788	Add	53820
53788	Add	53821
53788	Add	53822
53788	Add	53823
53788	Add	53824
53788	Add	53825
53788	Add	53826
53788	Add	53827
53788	Add	53828
53788	Add	53829
53788	Add	53830
53788	Add	53831
53788	Add	53832
53788	Add	53833
53788	Add	53834
53788	Add	53835
53788	Add	53836
53788	Add	53837
53788	Add	53838
53788	Add	53839
53788	Add	53840
53788	Add	53841
53788	Add	53842
53788	Add	53843
53788	Add	53844
53788	Add	53845
53788	Add	53846
53788	Add	53847
53788	Add	53848
53788	Add	53849
53788	Add	53850
53788	Add	53851
53788	Add	53852
53788	Add	53853
53788	Add	53854
53788	Add	53855
53788	Add	53856
53788	Add	53857
53788	Add	53858
53788	Add	53859
53788	Add	53860
53788	Add	53861
53788	Add	53862
53788	Add	53863
53788	Add	53864
53788	Add	53865
53788	Add	53866
53788	Add	53867
53788	Add	53868
53788	Add	53869
53870	Add	53871
53870	Add	53872
53870	Add	53873
53870	Add	53874
53870	Add	53875
53870	Add	53876
53870	Add	53877
53870	Add	53878
53870	Add	53879
53870	Add	53880
53870	Add	53881
53870	Add	53882
53870	Add	53883
53870	Add	53884
53870	Add	53885
53870	Add	53886
53870	Add	53887
53870	Add	53888
53870	Add	53889
53870	Add	53890
53870	Add	53891
53870	Add	53892
53870	Add	53893
53870	Add	53894
53870	Add	53895
53870	Add	53896
53870	Add	53897
53870	Add	53898
53870	Add	53899
53870	Add	53900
53870	Add	53901
53870	Add	53902
53870	Add	53903
53870	Add	53904
53870	Add	53905
53870	Add	53906
53907	Add	53908
53907	Add	53909
53907	Add	53910
53907	Add	53911
53907	Add	53912
53907	Add	53913
53907	Add	53914
53907	Add	53915
53907	Add	53916
53907	Add	53917
53907	Add	53918
53907	Add	53919
53907	Add	53920
53907	Add	53921
53907	Add	53922
53907	Add	53923
53907	Add	53924
53907	Add	53925
53907	Add	53926
53907	Add	53927
53907	Add	53928
53907	Add	53929
53907	Add	53930
53907	Add	53931
53907	Add	53932
53907	Add	53933
53907	Add	53934
53907	Add	53935
53907	Add	53936
53907	Add	53937
53907	Add	53938
53939	Add	53940
53939	Add	53941
53939	Add	53942
53939	Add	53943
53939	Add	53944
53939	Add	53945
53939	Add	53946
53939	Add	53947
53939	Add	53948
53939	Add	53949
53939	Add	53950
53939	Add	53951
53939	Add	53952
53939	Add	53953
53939	Add	53954
53939	Add	53955
53939	Add	53956
53939	Add	53957
53939	Add	53958
53939	Add	53959
53939	Add	53960
53939	Add	53961
53939	Add	53962
53939	Add	53963
53939	Add	53964
53939	Add	53965
53939	Add	53966
53939	Add	53967
53939	Add	53968
53939	Add	53969
53939	Add	53970
53939	Add	53971
53939	Add	53972
53973	Add	53974
53973	Add	53975
53973	Add	53976
53973	Add	53977
53973	Add	53978
53973	Add	53979
53973	Add	53980
53973	Add	53981
53973	Add	53982
53973	Add	53983
53973	Add	53984
53973	Add	53985
53973	Add	53986
53973	Add	53987
53973	Add	53988
53973	Add	53989
53973	Add	53990
53973	Add	53991
53973	Add	53992
53973	Add	53993
53973	Add	53994
53973	Add	53995
53973	Add	53996
53973	Add	53997
53973	Add	53998
53973	Add	53999
53973	Add	54000
53973	Add	54001
53973	Add	54002
53973	Add	54003
53973	Add	54004
53973	Add	54005
53973	Add	54006
53973	Add	54007
53973	Add	54008
53973	Add	54009
53973	Add	54010
53973	Add	54011
53973	Add	54012
53973	Add	54013
53973	Add	54014
53973	Add	54015
53973	Add	54016
53973	Add	54017
53973	Add	54018
53973	Add	54019
53973	Add	54020
53973	Add	54021
53973	Add	54022
53973	Add	54023
53973	Add	54024
53973	Add	54025
53973	Add	54026
53973	Add	54027
53973	Add	54028
53973	Add	54029
53973	Add	54030
53973	Add	54031
53973	Add	54032
53973	Add	54033
54034	Add	54035
54034	Add	54036
54034	Add	54037
54034	Add	54038
54034	Add	54039
54034	Add	54040
54034	Add	54041
54034	Add	54042
54034	Add	54043
54034	Add	54044
54034	Add	54045
54034	Add	54046
54034	Add	54047
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
54063	Add	54064
54063	Add	54065
54063	Add	54066
54063	Add	54067
54063	Add	54068
54063	Add	54069
54063	Add	54070
54063	Add	54071
54063	Add	54072
54063	Add	54073
54063	Add	54074
54063	Add	54075
54063	Add	54076
54063	Add	54077
54063	Add	54078
54063	Add	54079
54063	Add	54080
54063	Add	54081
54063	Add	54082
54083	Add	54084
54083	Add	54085
54083	Add	54086
54083	Add	54087
54083	Add	54088
54083	Add	54089
54083	Add	54090
54083	Add	54091
54083	Add	54092
54083	Add	54093
54083	Add	54094
54083	Add	54095
54083	Add	54096
54083	Add	54097
54083	Add	54098
54083	Add	54099
54083	Add	54100
54083	Add	54101
54083	Add	54102
54083	Add	54103
54083	Add	54104
54083	Add	54105
54083	Add	54106
54083	Add	54107
54083	Add	54108
54083	Add	54109
54083	Add	54110
54083	Add	54111
54083	Add	54112
54083	Add	54113
54083	Add	54114
54083	Add	54115
54083	Add	54116
54083	Add	54117
54083	Add	54118
54083	Add	54119
54083	Add	54120
54121	Add	54122
54121	Add	54123
54121	Add	54124
54121	Add	54125
54121	Add	54126
54121	Add	54127
54121	Add	54128
54121	Add	54129
54121	Add	54130
54121	Add	54131
54121	Add	54132
54121	Add	54133
54121	Add	54134
54121	Add	54135
54121	Add	54136
54121	Add	54137
54121	Add	54138
54121	Add	54139
54140	Add	54141
54140	Add	54142
54140	Add	54143
54140	Add	54144
54140	Add	54145
54140	Add	54146
54140	Add	54147
54140	Add	54148
54140	Add	54149
54140	Add	54150
54140	Add	54151
54140	Add	54152
54140	Add	54153
54140	Add	54154
54140	Add	54155
54156	Add	54157
54156	Add	54158
54156	Add	54159
54156	Add	54160
54156	Add	54161
54156	Add	54162
54156	Add	54163
54156	Add	54164
54156	Add	54165
54156	Add	54166
54156	Add	54167
54156	Add	54168
54156	Add	54169
54156	Add	54170
54156	Add	54171
54156	Add	54172
54156	Add	54173
54156	Add	54174
54156	Add	54175
54156	Add	54176
54156	Add	54177
54178	Add	54179
54178	Add	54180
54178	Add	54181
54178	Add	54182
54178	Add	54183
54178	Add	54184
54178	Add	54185
54178	Add	54186
54178	Add	54187
54178	Add	54188
54178	Add	54189
54178	Add	54190
54178	Add	54191
54178	Add	54192
54178	Add	54193
54194	Add	54195
54194	Add	54196
54194	Add	54197
54194	Add	54198
54194	Add	54199
54194	Add	54200
54194	Add	54201
54194	Add	54202
54194	Add	54203
54194	Add	54204
54194	Add	54205
54194	Add	54206
54194	Add	54207
54194	Add	54208
54209	Add	54210
54209	Add	54211
54209	Add	54212
54209	Add	54213
54209	Add	54214
54209	Add	54215
54209	Add	54216
54209	Add	54217
54209	Add	54218
54209	Add	54219
54209	Add	54220
54209	Add	54221
54209	Add	54222
54209	Add	54223
54209	Add	54224
54209	Add	54225
54209	Add	54226
54209	Add	54227
54228	Add	54229
54228	Add	54230
54228	Add	54231
54228	Add	54232
54228	Add	54233
54228	Add	54234
54228	Add	54235
54228	Add	54236
54228	Add	54237
54228	Add	54238
54228	Add	54239
54228	Add	54240
54228	Add	54241
54228	Add	54242
54228	Add	54243
54228	Add	54244
54228	Add	54245
54228	Add	54246
54247	Add	54248
54247	Add	54249
54247	Add	54250
54247	Add	54251
54247	Add	54252
54247	Add	54253
54247	Add	54254
54247	Add	54255
54247	Add	54256
54247	Add	54257
54247	Add	54258
54247	Add	54259
54247	Add	54260
54247	Add	54261
54247	Add	54262
54247	Add	54263
54247	Add	54264
54247	Add	54265
54247	Add	54266
54247	Add	54267
54247	Add	54268
54269	Add	54270
54269	Add	54271
54269	Add	54272
54269	Add	54273
54269	Add	54274
54269	Add	54275
54269	Add	54276
54269	Add	54277
54269	Add	54278
54269	Add	54279
54269	Add	54280
54269	Add	54281
54269	Add	54282
54269	Add	54283
54269	Add	54284
54269	Add	54285
54269	Add	54286
54269	Add	54287
54288	Add	54289
54288	Add	54290
54288	Add	54291
54288	Add	54292
54288	Add	54293
54288	Add	54294
54288	Add	54295
54288	Add	54296
54288	Add	54297
54288	Add	54298
54288	Add	54299
54300	Add	54301
54300	Add	54302
54300	Add	54303
54300	Add	54304
54300	Add	54305
54300	Add	54306
54300	Add	54307
54300	Add	54308
54300	Add	54309
54300	Add	54310
54300	Add	54311
54300	Add	54312
54300	Add	54313
54314	Add	54315
54314	Add	54316
54314	Add	54317
54314	Add	54318
54314	Add	54319
54314	Add	54320
54314	Add	54321
54314	Add	54322
54314	Add	54323
54314	Add	54324
54314	Add	54325
54314	Add	54326
54314	Add	54327
54314	Add	54328
54314	Add	54329
54314	Add	54330
54314	Add	54331
54314	Add	54332
54314	Add	54333
54314	Add	54334
54314	Add	54335
54314	Add	54336
54314	Add	54337
54314	Add	54338
54339	Add	54340
54339	Add	54341
54339	Add	54342
54339	Add	54343
54339	Add	54344
54339	Add	54345
54339	Add	54346
54339	Add	54347
54339	Add	54348
54339	Add	54349
54339	Add	54350
54339	Add	54351
54339	Add	54352
54339	Add	54353
54339	Add	54354
54339	Add	54355
54339	Add	54356
54339	Add	54357
54339	Add	54358
54339	Add	54359
54339	Add	54360
54339	Add	54361
54339	Add	54362
54339	Add	54363
54364	Add	54365
54364	Add	54366
54364	Add	54367
54364	Add	54368
54364	Add	54369
54364	Add	54370
54364	Add	54371
54364	Add	54372
54364	Add	54373
54364	Add	54374
54364	Add	54375
54364	Add	54376
54364	Add	54377
54364	Add	54378
54364	Add	54379
54364	Add	54380
54364	Add	54381
54382	Add	54383
54382	Add	54384
54382	Add	54385
54382	Add	54386
54382	Add	54387
54382	Add	54388
54382	Add	54389
54382	Add	54390
54382	Add	54391
54382	Add	54392
54382	Add	54393
54382	Add	54394
54382	Add	54395
54382	Add	54396
54382	Add	54397
54382	Add	54398
54382	Add	54399
54382	Add	54400
54382	Add	54401
54382	Add	54402
54382	Add	54403
54382	Add	54404
54382	Add	54405
54382	Add	54406
54407	Add	54408
54407	Add	54409
54407	Add	54410
54407	Add	54411
54407	Add	54412
54407	Add	54413
54407	Add	54414
54407	Add	54415
54407	Add	54416
54407	Add	54417
54407	Add	54418
54407	Add	54419
54407	Add	54420
54407	Add	54421
54407	Add	54422
54407	Add	54423
54407	Add	54424
54407	Add	54425
54407	Add	54426
54427	Add	54428
54427	Add	54429
54427	Add	54430
54427	Add	54431
54427	Add	54432
54427	Add	54433
54427	Add	54434
54427	Add	54435
54427	Add	54436
54427	Add	54437
54427	Add	54438
54427	Add	54439
54427	Add	54440
54427	Add	54441
54427	Add	54442
54427	Add	54443
54427	Add	54444
54427	Add	54445
54446	Add	54447
54446	Add	54448
54446	Add	54449
54446	Add	54450
54446	Add	54451
54446	Add	54452
54446	Add	54453
54446	Add	54454
54446	Add	54455
54446	Add	54456
54446	Add	54457
54446	Add	54458
54446	Add	54459
54446	Add	54460
54446	Add	54461
54446	Add	54462
54446	Add	54463
54446	Add	54464
54446	Add	54465
54446	Add	54466
54446	Add	54467
54446	Add	54468
54446	Add	54469
54446	Add	54470
54446	Add	54471
54472	Add	54473
54472	Add	54474
54472	Add	54475
54472	Add	54476
54472	Add	54477
54472	Add	54478
54472	Add	54479
54472	Add	54480
54472	Add	54481
54472	Add	54482
54472	Add	54483
54472	Add	54484
54472	Add	54485
54472	Add	54486
54472	Add	54487
54472	Add	54488
54472	Add	54489
54472	Add	54490
54491	Add	54492
54491	Add	54493
54491	Add	54494
54491	Add	54495
54491	Add	54496
54491	Add	54497
54491	Add	54498
54491	Add	54499
54491	Add	54500
54491	Add	54501
54491	Add	54502
54491	Add	54503
54491	Add	54504
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
54505	Add	54517
54505	Add	54518
54505	Add	54519
54520	Add	54521
54520	Add	54522
54520	Add	54523
54520	Add	54524
54520	Add	54525
54520	Add	54526
54520	Add	54527
54520	Add	54528
54520	Add	54529
54520	Add	54530
54520	Add	54531
54520	Add	54532
54520	Add	54533
54520	Add	54534
54520	Add	54535
54520	Add	54536
54520	Add	54537
54520	Add	54538
54520	Add	54539
54520	Add	54540
54520	Add	54541
54520	Add	54542
54543	Add	54544
54543	Add	54545
54543	Add	54546
54543	Add	54547
54543	Add	54548
54549	Add	54550
54549	Add	54551
54549	Add	54552
54549	Add	54553
54549	Add	54554
54549	Add	54555
54549	Add	54556
54549	Add	54557
54549	Add	54558
54549	Add	54559
54549	Add	54560
54561	Add	54562
54561	Add	54563
54561	Add	54564
54561	Add	54565
54561	Add	54566
54561	Add	54567
54561	Add	54568
54561	Add	54569
54561	Add	54570
54571	Add	54572
54571	Add	54573
54571	Add	54574
54571	Add	54575
54571	Add	54576
54571	Add	54577
54571	Add	54578
54571	Add	54579
54571	Add	54580
54571	Add	54581
54571	Add	54582
54571	Add	54583
54571	Add	54584
54571	Add	54585
54571	Add	54586
54587	Add	54588
54587	Add	54589
54587	Add	54590
54587	Add	54591
54587	Add	54592
54587	Add	54593
54587	Add	54594
54587	Add	54595
54587	Add	54596
54587	Add	54597
54587	Add	54598
54587	Add	54599
54587	Add	54600
54587	Add	54601
54587	Add	54602
54587	Add	54603
54587	Add	54604
54587	Add	54605
54587	Add	54606
54587	Add	54607
54587	Add	54608
54587	Add	54609
54587	Add	54610
54587	Add	54611
54587	Add	54612
54613	Add	54614
54613	Add	54615
54613	Add	54616
54613	Add	54617
54613	Add	54618
54613	Add	54619
54613	Add	54620
54613	Add	54621
54613	Add	54622
54613	Add	54623
54624	Add	54625
54624	Add	54626
54624	Add	54627
54624	Add	54628
54624	Add	54629
54624	Add	54630
54624	Add	54631
54624	Add	54632
54624	Add	54633
54634	Add	54635
54634	Add	54636
54634	Add	54637
54634	Add	54638
54634	Add	54639
54634	Add	54640
54634	Add	54641
54634	Add	54642
54634	Add	54643
54634	Add	54644
54634	Add	54645
54634	Add	54646
54634	Add	54647
54634	Add	54648
54634	Add	54649
54634	Add	54650
54634	Add	54651
54634	Add	54652
54634	Add	54653
54634	Add	54654
54634	Add	54655
54634	Add	54656
54634	Add	54657
54634	Add	54658
54634	Add	54659
54660	Add	54661
54660	Add	54662
54660	Add	54663
54660	Add	54664
54660	Add	54665
54660	Add	54666
54660	Add	54667
54668	Add	54669
54668	Add	54670
54668	Add	54671
54668	Add	54672
54668	Add	54673
54668	Add	54674
54668	Add	54675
54668	Add	54676
54668	Add	54677
54668	Add	54678
54668	Add	54679
54668	Add	54680
54668	Add	54681
54668	Add	54682
54668	Add	54683
54668	Add	54684
54668	Add	54685
54668	Add	54686
54687	Add	54688
54687	Add	54689
54687	Add	54690
54687	Add	54691
54687	Add	54692
54687	Add	54693
54687	Add	54694
54687	Add	54695
54687	Add	54696
54687	Add	54697
54687	Add	54698
54687	Add	54699
54687	Add	54700
54687	Add	54701
54702	Add	54703
54702	Add	54704
54702	Add	54705
54702	Add	54706
54702	Add	54707
54702	Add	54708
54702	Add	54709
54710	Add	54711
54710	Add	54712
54710	Add	54713
54710	Add	54714
54710	Add	54715
54710	Add	54716
54710	Add	54717
54718	Add	54719
54718	Add	54720
54718	Add	54721
54718	Add	54722
54718	Add	54723
54718	Add	54724
54718	Add	54725
54718	Add	54726
54718	Add	54727
54718	Add	54728
54718	Add	54729
54718	Add	54730
54718	Add	54731
54718	Add	54732
54718	Add	54733
54718	Add	54734
54718	Add	54735
54718	Add	54736
54718	Add	54737
54718	Add	54738
54718	Add	54739
54740	Add	54741
54740	Add	54742
54740	Add	54743
54740	Add	54744
54740	Add	54745
54740	Add	54746
54740	Add	54747
54740	Add	54748
54740	Add	54749
54740	Add	54750
54740	Add	54751
54740	Add	54752
54740	Add	54753
54740	Add	54754
54740	Add	54755
54756	Add	54757
54756	Add	54758
54756	Add	54759
54756	Add	54760
54756	Add	54761
54756	Add	54762
54756	Add	54763
54756	Add	54764
54756	Add	54765
54756	Add	54766
54756	Add	54767
54756	Add	54768
54756	Add	54769
54770	Add	54771
54770	Add	54772
54770	Add	54773
54770	Add	54774
54770	Add	54775
54770	Add	54776
54770	Add	54777
54770	Add	54778
54770	Add	54779
54770	Add	54780
54770	Add	54781
54770	Add	54782
54770	Add	54783
54770	Add	54784
54785	Add	54786
54785	Add	54787
54785	Add	54788
54785	Add	54789
54785	Add	54790
54785	Add	54791
54785	Add	54792
54785	Add	54793
54785	Add	54794
54785	Add	54795
54785	Add	54796
54785	Add	54797
54785	Add	54798
54785	Add	54799
54785	Add	54800
54785	Add	54801
54785	Add	54802
54785	Add	54803
54785	Add	54804
54785	Add	54805
54785	Add	54806
54785	Add	54807
54785	Add	54808
54785	Add	54809
54785	Add	54810
54785	Add	54811
54785	Add	54812
54813	Add	54814
54813	Add	54815
54813	Add	54816
54813	Add	54817
54818	Add	54819
54818	Add	54820
54818	Add	54821
54818	Add	54822
54818	Add	54823
54818	Add	54824
54818	Add	54825
54818	Add	54826
54818	Add	54827
54818	Add	54828
54818	Add	54829
54818	Add	54830
54831	Add	54832
54831	Add	54833
54831	Add	54834
54831	Add	54835
54831	Add	54836
54831	Add	54837
54831	Add	54838
54831	Add	54839
54831	Add	54840
54831	Add	54841
54831	Add	54842
54831	Add	54843
54831	Add	54844
54845	Add	54846
54845	Add	54847
54845	Add	54848
54845	Add	54849
54845	Add	54850
54845	Add	54851
54845	Add	54852
54845	Add	54853
54845	Add	54854
54845	Add	54855
54845	Add	54856
54845	Add	54857
54845	Add	54858
54845	Add	54859
54845	Add	54860
54845	Add	54861
54845	Add	54862
54845	Add	54863
54845	Add	54864
54845	Add	54865
54845	Add	54866
54845	Add	54867
54845	Add	54868
54845	Add	54869
54845	Add	54870
54845	Add	54871
54845	Add	54872
54845	Add	54873
54845	Add	54874
54845	Add	54875
54876	Add	54877
54876	Add	54878
54876	Add	54879
54876	Add	54880
54876	Add	54881
54876	Add	54882
54876	Add	54883
54876	Add	54884
54876	Add	54885
54876	Add	54886
54876	Add	54887
54876	Add	54888
54876	Add	54889
54876	Add	54890
54876	Add	54891
54876	Add	54892
54876	Add	54893
54876	Add	54894
54876	Add	54895
54876	Add	54896
54897	Add	54898
54897	Add	54899
54897	Add	54900
54897	Add	54901
54897	Add	54902
54903	Add	54904
54903	Add	54905
54903	Add	54906
54903	Add	54907
54903	Add	54908
54903	Add	54909
54903	Add	54910
54903	Add	54911
54903	Add	54912
54903	Add	54913
54903	Add	54914
54915	Add	54916
54915	Add	54917
54915	Add	54918
54915	Add	54919
54915	Add	54920
54915	Add	54921
54915	Add	54922
54915	Add	54923
54915	Add	54924
54915	Add	54925
54915	Add	54926
54915	Add	54927
54915	Add	54928
54929	Add	54930
54929	Add	54931
54929	Add	54932
54929	Add	54933
54929	Add	54934
54929	Add	54935
54929	Add	54936
54929	Add	54937
54929	Add	54938
54929	Add	54939
54929	Add	54940
54941	Add	54942
54941	Add	54943
54941	Add	54944
54941	Add	54945
54941	Add	54946
54941	Add	54947
54941	Add	54948
54941	Add	54949
54941	Add	54950
54941	Add	54951
54941	Add	54952
54953	Add	54954
54953	Add	54955
54953	Add	54956
54953	Add	54957
54953	Add	54958
54953	Add	54959
54953	Add	54960
54953	Add	54961
54953	Add	54962
54953	Add	54963
54953	Add	54964
54953	Add	54965
54953	Add	54966
54953	Add	54967
54953	Add	54968
54953	Add	54969
54953	Add	54970
54953	Add	54971
54953	Add	54972
54953	Add	54973
54953	Add	54974
54953	Add	54975
54953	Add	54976
54953	Add	54977
54953	Add	54978
54953	Add	54979
54953	Add	54980
54953	Add	54981
54953	Add	54982
54953	Add	54983
54953	Add	54984
54953	Add	54985
54953	Add	54986
54953	Add	54987
54953	Add	54988
54953	Add	54989
54953	Add	54990
54953	Add	54991
54953	Add	54992
54953	Add	54993
54953	Add	54994
54953	Add	54995
54953	Add	54996
54953	Add	54997
54953	Add	54998
54953	Add	54999
54953	Add	55000
54953	Add	55001
54953	Add	55002
54953	Add	55003
54953	Add	55004
54953	Add	55005
54953	Add	55006
54953	Add	55007
54953	Add	55008
54953	Add	55009
54953	Add	55010
54953	Add	55011
54953	Add	55012
54953	Add	55013
54953	Add	55014
55015	Add	55016
55015	Add	55017
55015	Add	55018
55015	Add	55019
55015	Add	55020
55015	Add	55021
55015	Add	55022
55015	Add	55023
55015	Add	55024
55015	Add	55025
55015	Add	55026
55015	Add	55027
55015	Add	55028
55015	Add	55029
55015	Add	55030
55015	Add	55031
55015	Add	55032
55015	Add	55033
55034	Add	55035
55034	Add	55036
55034	Add	55037
55034	Add	55038
55034	Add	55039
55040	Add	55041
55040	Add	55042
55040	Add	55043
55040	Add	55044
55045	Add	55046
55045	Add	55047
55045	Add	55048
55045	Add	55049
55050	Add	55051
55050	Add	55052
55050	Add	55053
55050	Add	55054
55050	Add	55055
55050	Add	55056
55050	Add	55057
55058	Add	55059
55058	Add	55060
55058	Add	55061
55058	Add	55062
55058	Add	55063
55058	Add	55064
55065	Add	55066
55065	Add	55067
55065	Add	55068
55065	Add	55069
55065	Add	55070
55065	Add	55071
55072	Add	55073
55072	Add	55074
55072	Add	55075
55072	Add	55076
55072	Add	55077
55072	Add	55078
55072	Add	55079
55080	Add	55081
55080	Add	55082
55080	Add	55083
55080	Add	55084
55080	Add	55085
55080	Add	55086
55080	Add	55087
55080	Add	55088
55080	Add	55089
55080	Add	55090
55080	Add	55091
55080	Add	55092
55080	Add	55093
55094	Add	55095
55094	Add	55096
55094	Add	55097
55094	Add	55098
55094	Add	55099
55100	Add	55101
55100	Add	55102
55100	Add	55103
55100	Add	55104
55100	Add	55105
55100	Add	55106
55107	Add	55108
55107	Add	55109
55107	Add	55110
55107	Add	55111
55107	Add	55112
55107	Add	55113
55107	Add	55114
55107	Add	55115
55107	Add	55116
55107	Add	55117
55107	Add	55118
55107	Add	55119
55107	Add	55120
55107	Add	55121
55107	Add	55122
55107	Add	55123
55107	Add	55124
55107	Add	55125
55107	Add	55126
55107	Add	55127
55107	Add	55128
55129	Add	55130
55129	Add	55131
55129	Add	55132
55129	Add	55133
55129	Add	55134
55129	Add	55135
55129	Add	55136
55129	Add	55137
55129	Add	55138
55139	Add	55140
55139	Add	55141
55139	Add	55142
55139	Add	55143
55144	Add	55145
55144	Add	55146
55144	Add	55147
55144	Add	55148
55144	Add	55149
55144	Add	55150
55144	Add	55151
55152	Add	55153
55152	Add	55154
55152	Add	55155
55152	Add	55156
55152	Add	55157
55152	Add	55158
55152	Add	55159
55152	Add	55160
55152	Add	55161
55162	Add	55163
55162	Add	55164
55162	Add	55165
55162	Add	55166
55162	Add	55167
55162	Add	55168
55162	Add	55169
55162	Add	55170
55162	Add	55171
55162	Add	55172
55173	Add	55174
55173	Add	55175
55173	Add	55176
55173	Add	55177
55173	Add	55178
55173	Add	55179
55173	Add	55180
55181	Add	55182
55181	Add	55183
55181	Add	55184
55181	Add	55185
55181	Add	55186
55181	Add	55187
55181	Add	55188
55181	Add	55189
55190	Add	55191
55190	Add	55192
55190	Add	55193
55190	Add	55194
55190	Add	55195
55190	Add	55196
55190	Add	55197
55190	Add	55198
55190	Add	55199
55190	Add	55200
55190	Add	55201
55202	Add	55203
55202	Add	55204
55202	Add	55205
55202	Add	55206
55202	Add	55207
55202	Add	55208
55202	Add	55209
55210	Add	55211
55210	Add	55212
55210	Add	55213
55210	Add	55214
55210	Add	55215
55210	Add	55216
55210	Add	55217
55210	Add	55218
55210	Add	55219
55210	Add	55220
55210	Add	55221
55210	Add	55222
55210	Add	55223
55210	Add	55224
55210	Add	55225
55226	Add	55227
55226	Add	55228
55226	Add	55229
55226	Add	55230
55226	Add	55231
55226	Add	55232
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
52762	Aaron_Day.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/inventory/schemas/Aaron_Day.xml			t
52794	Alex_Cho.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/inventory/schemas/Alex_Cho.xml			t
52833	alon.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/inventory/schemas/alon.xml			t
52887	askew.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/inventory/schemas/askew.xml			t
52917	Barrett_Arney.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/inventory/schemas/Barrett_Arney.xml			t
52947	bokan-tomic.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/inventory/schemas/bokan-tomic.xml			t
52983	burdick.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/inventory/schemas/burdick.xml			t
53019	chang.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/inventory/schemas/chang.xml			t
53050	christensen.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/inventory/schemas/christensen.xml			t
53081	Colin_Bleckner.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/inventory/schemas/Colin_Bleckner.xml			t
53108	collier.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/inventory/schemas/collier.xml			t
53137	cox.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/inventory/schemas/cox.xml			t
53186	dearing.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/inventory/schemas/dearing.xml			t
53220	erbad.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/inventory/schemas/erbad.xml			t
53255	haji-yusuf.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/inventory/schemas/haji-yusuf.xml			t
53288	higgins.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/inventory/schemas/higgins.xml			t
53327	igor.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/inventory/schemas/igor.xml			t
53366	Jagroop_Singh_Dhillon.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/inventory/schemas/Jagroop_Singh_Dhillon.xml			t
53392	Jake_Foster.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/inventory/schemas/Jake_Foster.xml			t
53424	jaya.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/inventory/schemas/jaya.xml			t
53462	keto.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/inventory/schemas/keto.xml			t
53538	luna.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/inventory/schemas/luna.xml			t
53575	matt.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/inventory/schemas/matt.xml			t
53633	Melissa_Garcia.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/inventory/schemas/Melissa_Garcia.xml			t
53671	nilesh.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/inventory/schemas/nilesh.xml			t
53708	peter.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/inventory/schemas/peter.xml			t
53754	pradeep.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/inventory/schemas/pradeep.xml			t
53788	rachel.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/inventory/schemas/rachel.xml			t
53870	Rachel_Hunt.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/inventory/schemas/Rachel_Hunt.xml			t
53907	Ryan_Eyers.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/inventory/schemas/Ryan_Eyers.xml			t
53939	Xenia_Hertzenberg.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/inventory/schemas/Xenia_Hertzenberg.xml			t
53973	yana.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/inventory/schemas/yana.xml			t
54034	Aaron_Day-small.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/invsmall/schemas/Aaron_Day-small.xml			t
54048	AimanErbad-small.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/invsmall/schemas/AimanErbad-small.xml			t
54063	Alex_Cho-small.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/invsmall/schemas/Alex_Cho-small.xml			t
54083	alon-small.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/invsmall/schemas/alon-small.xml			t
54121	AndyJaya-small.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/invsmall/schemas/AndyJaya-small.xml			t
54140	Barrett_Arney-small.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/invsmall/schemas/Barrett_Arney-small.xml			t
54156	BrankicaBokanTomic-small.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/invsmall/schemas/BrankicaBokanTomic-small.xml			t
54178	BrianChang-small.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/invsmall/schemas/BrianChang-small.xml			t
54194	Colin_Bleckner-small.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/invsmall/schemas/Colin_Bleckner-small.xml			t
54209	DavidBurdick-small.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/invsmall/schemas/DavidBurdick-small.xml			t
54228	DavidDearing-small.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/invsmall/schemas/DavidDearing-small.xml			t
54247	igor-small.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/invsmall/schemas/igor-small.xml			t
54269	JacobChristensen-small.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/invsmall/schemas/JacobChristensen-small.xml			t
54288	Jagroop_Singh_Dhillon-small.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/invsmall/schemas/Jagroop_Singh_Dhillon-small.xml			t
54300	Jake_Foster-small.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/invsmall/schemas/Jake_Foster-small.xml			t
54314	JohnKeto-small.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/invsmall/schemas/JohnKeto-small.xml			t
54339	luna-small.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/invsmall/schemas/luna-small.xml			t
54364	MandyAskew-small.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/invsmall/schemas/MandyAskew-small.xml			t
54382	matt-small.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/invsmall/schemas/matt-small.xml			t
54407	MattewCox-small.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/invsmall/schemas/MattewCox-small.xml			t
54427	MattewHiggins-small.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/invsmall/schemas/MattewHiggins-small.xml			t
54446	mediated-small.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/invsmall/schemas/mediated-small.xml			t
54472	Melissa_Garcia-small.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/invsmall/schemas/Melissa_Garcia-small.xml			t
54491	MichaelCollier-small.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/invsmall/schemas/MichaelCollier-small.xml			t
54505	NemoHaji-Yusuf-small.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/invsmall/schemas/NemoHaji-Yusuf-small.xml			t
54520	nilesh-small.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/invsmall/schemas/nilesh-small.xml			t
54543	1stopauto_new.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/auto/schemas/1stopauto_new.xml			t
54549	1stopauto_used.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/auto/schemas/1stopauto_used.xml			t
54561	401carfinder.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/auto/schemas/401carfinder.xml			t
54571	akguam.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/auto/schemas/akguam.xml			t
54587	amarillo.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/auto/schemas/amarillo.xml			t
54613	autobytel.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/auto/schemas/autobytel.xml			t
54624	autochooser.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/auto/schemas/autochooser.xml			t
54634	AutoMob.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/auto/schemas/AutoMob.xml			t
54660	AutoNation.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/auto/schemas/AutoNation.xml			t
54668	Autonet.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/auto/schemas/Autonet.xml			t
54687	Autonet_ca.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/auto/schemas/Autonet_ca.xml			t
54702	Autopoint.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/auto/schemas/Autopoint.xml			t
54710	Autoweb.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/auto/schemas/Autoweb.xml			t
54718	buycars.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/auto/schemas/buycars.xml			t
54740	carcast.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/auto/schemas/carcast.xml			t
54756	carprices.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/auto/schemas/carprices.xml			t
54770	cars.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/auto/schemas/cars.xml			t
54785	carsearch.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/auto/schemas/carsearch.xml			t
54813	cartalk.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/auto/schemas/cartalk.xml			t
54818	colvinauto.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/auto/schemas/colvinauto.xml			t
54831	dealernet.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/auto/schemas/dealernet.xml			t
54845	discountautopricing.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/auto/schemas/discountautopricing.xml			t
54876	ebaymotors.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/auto/schemas/ebaymotors.xml			t
54897	fredbondesen.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/auto/schemas/fredbondesen.xml			t
54903	getAuto.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/auto/schemas/getAuto.xml			t
54915	gotcars4sale.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/auto/schemas/gotcars4sale.xml			t
54929	herzogmeier.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/auto/schemas/herzogmeier.xml			t
54941	hoakmotors.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/auto/schemas/hoakmotors.xml			t
54953	mediatedSchema.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/auto/schemas/mediatedSchema.xml			t
55015	nada.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/auto/schemas/nada.xml			t
55034	tourist.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/auto/schemas/tourist.xml			t
55040	Apartments.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/realestate/schemas/Apartments.xml			t
55045	biztrader.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/realestate/schemas/biztrader.xml			t
55050	cityfeet.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/realestate/schemas/cityfeet.xml			t
55058	CommercialRealEstate.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/realestate/schemas/CommercialRealEstate.xml			t
55065	eLandUSA.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/realestate/schemas/eLandUSA.xml			t
55072	EquestrianLIVING.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/realestate/schemas/EquestrianLIVING.xml			t
55080	eSearchHomes-1.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/realestate/schemas/eSearchHomes-1.xml			t
55094	HomeBuilders.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/realestate/schemas/HomeBuilders.xml			t
55100	homeseekers.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/realestate/schemas/homeseekers.xml			t
55107	mediated.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/realestate/schemas/mediated.xml			t
55129	NationalMobile.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/realestate/schemas/NationalMobile.xml			t
55139	new-home-builders.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/realestate/schemas/new-home-builders.xml			t
55144	NorthIdahoHomeseekers.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/realestate/schemas/NorthIdahoHomeseekers.xml			t
55152	PlanetProperties.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/realestate/schemas/PlanetProperties.xml			t
55162	Property2000-1.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/realestate/schemas/Property2000-1.xml			t
55173	RealEstate.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/realestate/schemas/RealEstate.xml			t
55181	RealtyInvestor-1.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/realestate/schemas/RealtyInvestor-1.xml			t
55190	SpaceForLease.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/realestate/schemas/SpaceForLease.xml			t
55202	USLots.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/realestate/schemas/USLots.xml			t
55210	webrealestate.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/realestate/schemas/webrealestate.xml			t
55226	YahooRealEstate.xml		file:/Users/kuangc/workspace/eclipse/openii/Schemr/data/icde05data/realestate/schemas/YahooRealEstate.xml			t
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
52763	Entity
52764	Entity
52765	Entity
52766	Entity
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
52788	Attribute
52789	Attribute
52790	Attribute
52791	Attribute
52792	Attribute
52793	Attribute
52795	Entity
52796	Entity
52797	Entity
52798	Entity
52799	Entity
52800	Entity
52801	Entity
52802	Entity
52803	Entity
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
52822	Attribute
52823	Attribute
52824	Attribute
52825	Attribute
52826	Attribute
52827	Attribute
52828	Attribute
52829	Attribute
52830	Attribute
52831	Attribute
52832	Attribute
52834	Entity
52835	Entity
52836	Entity
52837	Entity
52838	Entity
52839	Entity
52840	Entity
52841	Entity
52842	Entity
52843	Entity
52844	Entity
52845	Entity
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
52857	Attribute
52858	Attribute
52859	Attribute
52860	Attribute
52861	Attribute
52862	Attribute
52863	Attribute
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
52888	Entity
52889	Entity
52890	Entity
52891	Entity
52892	Entity
52893	Entity
52894	Entity
52895	Entity
52896	Attribute
52897	Attribute
52898	Attribute
52899	Attribute
52900	Attribute
52901	Attribute
52902	Attribute
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
52918	Entity
52919	Entity
52920	Entity
52921	Entity
52922	Entity
52923	Attribute
52924	Attribute
52925	Attribute
52926	Attribute
52927	Attribute
52928	Attribute
52929	Attribute
52930	Attribute
52931	Attribute
52932	Attribute
52933	Attribute
52934	Attribute
52935	Attribute
52936	Attribute
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
52948	Entity
52949	Entity
52950	Entity
52951	Entity
52952	Entity
52953	Entity
52954	Entity
52955	Entity
52956	Entity
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
52968	Attribute
52969	Attribute
52970	Attribute
52971	Attribute
52972	Attribute
52973	Attribute
52974	Attribute
52975	Attribute
52976	Attribute
52977	Attribute
52978	Attribute
52979	Attribute
52980	Attribute
52981	Attribute
52982	Attribute
52984	Entity
52985	Entity
52986	Entity
52987	Entity
52988	Entity
52989	Entity
52990	Entity
52991	Entity
52992	Entity
52993	Entity
52994	Attribute
52995	Attribute
52996	Attribute
52997	Attribute
52998	Attribute
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
53020	Entity
53021	Entity
53022	Entity
53023	Entity
53024	Entity
53025	Entity
53026	Entity
53027	Entity
53028	Entity
53029	Entity
53030	Attribute
53031	Attribute
53032	Attribute
53033	Attribute
53034	Attribute
53035	Attribute
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
53051	Entity
53052	Entity
53053	Entity
53054	Entity
53055	Entity
53056	Entity
53057	Entity
53058	Attribute
53059	Attribute
53060	Attribute
53061	Attribute
53062	Attribute
53063	Attribute
53064	Attribute
53065	Attribute
53066	Attribute
53067	Attribute
53068	Attribute
53069	Attribute
53070	Attribute
53071	Attribute
53072	Attribute
53073	Attribute
53074	Attribute
53075	Attribute
53076	Attribute
53077	Attribute
53078	Attribute
53079	Attribute
53080	Attribute
53082	Entity
53083	Entity
53084	Entity
53085	Attribute
53086	Attribute
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
53109	Entity
53110	Entity
53111	Entity
53112	Entity
53113	Entity
53114	Entity
53115	Entity
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
53138	Entity
53139	Entity
53140	Entity
53141	Entity
53142	Entity
53143	Entity
53144	Entity
53145	Entity
53146	Entity
53147	Entity
53148	Entity
53149	Entity
53150	Entity
53151	Entity
53152	Entity
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
53177	Attribute
53178	Attribute
53179	Attribute
53180	Attribute
53181	Attribute
53182	Attribute
53183	Attribute
53184	Attribute
53185	Attribute
53187	Entity
53188	Entity
53189	Entity
53190	Entity
53191	Entity
53192	Entity
53193	Entity
53194	Entity
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
53221	Entity
53222	Entity
53223	Entity
53224	Entity
53225	Entity
53226	Entity
53227	Attribute
53228	Attribute
53229	Attribute
53230	Attribute
53231	Attribute
53232	Attribute
53233	Attribute
53234	Attribute
53235	Attribute
53236	Attribute
53237	Attribute
53238	Attribute
53239	Attribute
53240	Attribute
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
53256	Entity
53257	Entity
53258	Entity
53259	Entity
53260	Entity
53261	Entity
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
53273	Attribute
53274	Attribute
53275	Attribute
53276	Attribute
53277	Attribute
53278	Attribute
53279	Attribute
53280	Attribute
53281	Attribute
53282	Attribute
53283	Attribute
53284	Attribute
53285	Attribute
53286	Attribute
53287	Attribute
53289	Entity
53290	Entity
53291	Entity
53292	Entity
53293	Entity
53294	Entity
53295	Entity
53296	Entity
53297	Entity
53298	Entity
53299	Entity
53300	Entity
53301	Attribute
53302	Attribute
53303	Attribute
53304	Attribute
53305	Attribute
53306	Attribute
53307	Attribute
53308	Attribute
53309	Attribute
53310	Attribute
53311	Attribute
53312	Attribute
53313	Attribute
53314	Attribute
53315	Attribute
53316	Attribute
53317	Attribute
53318	Attribute
53319	Attribute
53320	Attribute
53321	Attribute
53322	Attribute
53323	Attribute
53324	Attribute
53325	Attribute
53326	Attribute
53328	Entity
53329	Entity
53330	Entity
53331	Entity
53332	Entity
53333	Entity
53334	Entity
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
53356	Attribute
53357	Attribute
53358	Attribute
53359	Attribute
53360	Attribute
53361	Attribute
53362	Attribute
53363	Attribute
53364	Attribute
53365	Attribute
53367	Entity
53368	Entity
53369	Entity
53370	Entity
53371	Entity
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
53390	Attribute
53391	Attribute
53393	Entity
53394	Entity
53395	Entity
53396	Entity
53397	Attribute
53398	Attribute
53399	Attribute
53400	Attribute
53401	Attribute
53402	Attribute
53403	Attribute
53404	Attribute
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
53425	Entity
53426	Entity
53427	Entity
53428	Entity
53429	Entity
53430	Entity
53431	Entity
53432	Entity
53433	Entity
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
53463	Entity
53464	Entity
53465	Entity
53466	Entity
53467	Entity
53468	Entity
53469	Entity
53470	Entity
53471	Entity
53472	Entity
53473	Entity
53474	Entity
53475	Entity
53476	Entity
53477	Entity
53478	Entity
53479	Entity
53480	Entity
53481	Entity
53482	Entity
53483	Entity
53484	Entity
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
53509	Attribute
53510	Attribute
53511	Attribute
53512	Attribute
53513	Attribute
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
53539	Entity
53540	Entity
53541	Entity
53542	Entity
53543	Entity
53544	Entity
53545	Entity
53546	Attribute
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
53584	Attribute
53585	Attribute
53586	Attribute
53587	Attribute
53588	Attribute
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
53634	Entity
53635	Entity
53636	Entity
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
53650	Attribute
53651	Attribute
53652	Attribute
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
53665	Attribute
53666	Attribute
53667	Attribute
53668	Attribute
53669	Attribute
53670	Attribute
53672	Entity
53673	Entity
53674	Entity
53675	Entity
53676	Entity
53677	Entity
53678	Entity
53679	Entity
53680	Entity
53681	Attribute
53682	Attribute
53683	Attribute
53684	Attribute
53685	Attribute
53686	Attribute
53687	Attribute
53688	Attribute
53689	Attribute
53690	Attribute
53691	Attribute
53692	Attribute
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
53709	Entity
53710	Entity
53711	Entity
53712	Entity
53713	Entity
53714	Entity
53715	Entity
53716	Entity
53717	Entity
53718	Entity
53719	Entity
53720	Entity
53721	Entity
53722	Entity
53723	Attribute
53724	Attribute
53725	Attribute
53726	Attribute
53727	Attribute
53728	Attribute
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
53742	Attribute
53743	Attribute
53744	Attribute
53745	Attribute
53746	Attribute
53747	Attribute
53748	Attribute
53749	Attribute
53750	Attribute
53751	Attribute
53752	Attribute
53753	Attribute
53755	Entity
53756	Entity
53757	Entity
53758	Entity
53759	Entity
53760	Entity
53761	Entity
53762	Attribute
53763	Attribute
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
53780	Attribute
53781	Attribute
53782	Attribute
53783	Attribute
53784	Attribute
53785	Attribute
53786	Attribute
53787	Attribute
53789	Entity
53790	Entity
53791	Entity
53792	Entity
53793	Entity
53794	Entity
53795	Entity
53796	Entity
53797	Entity
53798	Entity
53799	Entity
53800	Entity
53801	Entity
53802	Entity
53803	Attribute
53804	Attribute
53805	Attribute
53806	Attribute
53807	Attribute
53808	Attribute
53809	Attribute
53810	Attribute
53811	Attribute
53812	Attribute
53813	Attribute
53814	Attribute
53815	Attribute
53816	Attribute
53817	Attribute
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
53830	Attribute
53831	Attribute
53832	Attribute
53833	Attribute
53834	Attribute
53835	Attribute
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
53849	Attribute
53850	Attribute
53851	Attribute
53852	Attribute
53853	Attribute
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
53871	Entity
53872	Entity
53873	Entity
53874	Entity
53875	Entity
53876	Entity
53877	Entity
53878	Entity
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
53890	Attribute
53891	Attribute
53892	Attribute
53893	Attribute
53894	Attribute
53895	Attribute
53896	Attribute
53897	Attribute
53898	Attribute
53899	Attribute
53900	Attribute
53901	Attribute
53902	Attribute
53903	Attribute
53904	Attribute
53905	Attribute
53906	Attribute
53908	Entity
53909	Entity
53910	Entity
53911	Entity
53912	Attribute
53913	Attribute
53914	Attribute
53915	Attribute
53916	Attribute
53917	Attribute
53918	Attribute
53919	Attribute
53920	Attribute
53921	Attribute
53922	Attribute
53923	Attribute
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
53940	Entity
53941	Entity
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
53966	Attribute
53967	Attribute
53968	Attribute
53969	Attribute
53970	Attribute
53971	Attribute
53972	Attribute
53974	Entity
53975	Entity
53976	Entity
53977	Entity
53978	Entity
53979	Entity
53980	Entity
53981	Entity
53982	Entity
53983	Entity
53984	Entity
53985	Entity
53986	Entity
53987	Attribute
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
54009	Attribute
54010	Attribute
54011	Attribute
54012	Attribute
54013	Attribute
54014	Attribute
54015	Attribute
54016	Attribute
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
54029	Attribute
54030	Attribute
54031	Attribute
54032	Attribute
54033	Attribute
54035	Entity
54036	Entity
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
54051	Attribute
54052	Attribute
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
54064	Entity
54065	Entity
54066	Entity
54067	Entity
54068	Attribute
54069	Attribute
54070	Attribute
54071	Attribute
54072	Attribute
54073	Attribute
54074	Attribute
54075	Attribute
54076	Attribute
54077	Attribute
54078	Attribute
54079	Attribute
54080	Attribute
54081	Attribute
54082	Attribute
54084	Entity
54085	Entity
54086	Entity
54087	Entity
54088	Entity
54089	Entity
54090	Entity
54091	Attribute
54092	Attribute
54093	Attribute
54094	Attribute
54095	Attribute
54096	Attribute
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
54107	Attribute
54108	Attribute
54109	Attribute
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
54122	Entity
54123	Entity
54124	Entity
54125	Entity
54126	Entity
54127	Attribute
54128	Attribute
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
54141	Entity
54142	Entity
54143	Entity
54144	Attribute
54145	Attribute
54146	Attribute
54147	Attribute
54148	Attribute
54149	Attribute
54150	Attribute
54151	Attribute
54152	Attribute
54153	Attribute
54154	Attribute
54155	Attribute
54157	Entity
54158	Entity
54159	Entity
54160	Entity
54161	Entity
54162	Attribute
54163	Attribute
54164	Attribute
54165	Attribute
54166	Attribute
54167	Attribute
54168	Attribute
54169	Attribute
54170	Attribute
54171	Attribute
54172	Attribute
54173	Attribute
54174	Attribute
54175	Attribute
54176	Attribute
54177	Attribute
54179	Entity
54180	Entity
54181	Entity
54182	Entity
54183	Entity
54184	Attribute
54185	Attribute
54186	Attribute
54187	Attribute
54188	Attribute
54189	Attribute
54190	Attribute
54191	Attribute
54192	Attribute
54193	Attribute
54195	Entity
54196	Entity
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
54210	Entity
54211	Entity
54212	Entity
54213	Entity
54214	Entity
54215	Entity
54216	Attribute
54217	Attribute
54218	Attribute
54219	Attribute
54220	Attribute
54221	Attribute
54222	Attribute
54223	Attribute
54224	Attribute
54225	Attribute
54226	Attribute
54227	Attribute
54229	Entity
54230	Entity
54231	Entity
54232	Entity
54233	Entity
54234	Attribute
54235	Attribute
54236	Attribute
54237	Attribute
54238	Attribute
54239	Attribute
54240	Attribute
54241	Attribute
54242	Attribute
54243	Attribute
54244	Attribute
54245	Attribute
54246	Attribute
54248	Entity
54249	Entity
54250	Entity
54251	Entity
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
54262	Attribute
54263	Attribute
54264	Attribute
54265	Attribute
54266	Attribute
54267	Attribute
54268	Attribute
54270	Entity
54271	Entity
54272	Entity
54273	Entity
54274	Entity
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
54289	Entity
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
54301	Entity
54302	Entity
54303	Attribute
54304	Attribute
54305	Attribute
54306	Attribute
54307	Attribute
54308	Attribute
54309	Attribute
54310	Attribute
54311	Attribute
54312	Attribute
54313	Attribute
54315	Entity
54316	Entity
54317	Entity
54318	Entity
54319	Entity
54320	Entity
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
54340	Entity
54341	Entity
54342	Entity
54343	Attribute
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
54358	Attribute
54359	Attribute
54360	Attribute
54361	Attribute
54362	Attribute
54363	Attribute
54365	Entity
54366	Entity
54367	Entity
54368	Entity
54369	Entity
54370	Attribute
54371	Attribute
54372	Attribute
54373	Attribute
54374	Attribute
54375	Attribute
54376	Attribute
54377	Attribute
54378	Attribute
54379	Attribute
54380	Attribute
54381	Attribute
54383	Entity
54384	Entity
54385	Entity
54386	Attribute
54387	Attribute
54388	Attribute
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
54408	Entity
54409	Entity
54410	Entity
54411	Entity
54412	Entity
54413	Entity
54414	Entity
54415	Attribute
54416	Attribute
54417	Attribute
54418	Attribute
54419	Attribute
54420	Attribute
54421	Attribute
54422	Attribute
54423	Attribute
54424	Attribute
54425	Attribute
54426	Attribute
54428	Entity
54429	Entity
54430	Entity
54431	Entity
54432	Entity
54433	Attribute
54434	Attribute
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
54447	Entity
54448	Entity
54449	Entity
54450	Entity
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
54473	Entity
54474	Entity
54475	Entity
54476	Attribute
54477	Attribute
54478	Attribute
54479	Attribute
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
54492	Entity
54493	Entity
54494	Entity
54495	Attribute
54496	Attribute
54497	Attribute
54498	Attribute
54499	Attribute
54500	Attribute
54501	Attribute
54502	Attribute
54503	Attribute
54504	Attribute
54506	Entity
54507	Entity
54508	Attribute
54509	Attribute
54510	Attribute
54511	Attribute
54512	Attribute
54513	Attribute
54514	Attribute
54515	Attribute
54516	Attribute
54517	Attribute
54518	Attribute
54519	Attribute
54521	Entity
54522	Entity
54523	Entity
54524	Entity
54525	Entity
54526	Entity
54527	Attribute
54528	Attribute
54529	Attribute
54530	Attribute
54531	Attribute
54532	Attribute
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
54550	Entity
54551	Attribute
54552	Attribute
54553	Attribute
54554	Attribute
54555	Attribute
54556	Attribute
54557	Attribute
54558	Attribute
54559	Attribute
54560	Attribute
54562	Entity
54563	Attribute
54564	Attribute
54565	Attribute
54566	Attribute
54567	Attribute
54568	Attribute
54569	Attribute
54570	Attribute
54572	Entity
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
54588	Entity
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
54614	Entity
54615	Attribute
54616	Attribute
54617	Attribute
54618	Attribute
54619	Attribute
54620	Attribute
54621	Attribute
54622	Attribute
54623	Attribute
54625	Entity
54626	Attribute
54627	Attribute
54628	Attribute
54629	Attribute
54630	Attribute
54631	Attribute
54632	Attribute
54633	Attribute
54635	Entity
54636	Attribute
54637	Attribute
54638	Attribute
54639	Attribute
54640	Attribute
54641	Attribute
54642	Attribute
54643	Attribute
54644	Attribute
54645	Attribute
54646	Attribute
54647	Attribute
54648	Attribute
54649	Attribute
54650	Attribute
54651	Attribute
54652	Attribute
54653	Attribute
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
54667	Attribute
54669	Entity
54670	Attribute
54671	Attribute
54672	Attribute
54673	Attribute
54674	Attribute
54675	Attribute
54676	Attribute
54677	Attribute
54678	Attribute
54679	Attribute
54680	Attribute
54681	Attribute
54682	Attribute
54683	Attribute
54684	Attribute
54685	Attribute
54686	Attribute
54688	Entity
54689	Attribute
54690	Attribute
54691	Attribute
54692	Attribute
54693	Attribute
54694	Attribute
54695	Attribute
54696	Attribute
54697	Attribute
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
54709	Attribute
54711	Entity
54712	Attribute
54713	Attribute
54714	Attribute
54715	Attribute
54716	Attribute
54717	Attribute
54719	Entity
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
54731	Attribute
54732	Attribute
54733	Attribute
54734	Attribute
54735	Attribute
54736	Attribute
54737	Attribute
54738	Attribute
54739	Attribute
54741	Entity
54742	Attribute
54743	Attribute
54744	Attribute
54745	Attribute
54746	Attribute
54747	Attribute
54748	Attribute
54749	Attribute
54750	Attribute
54751	Attribute
54752	Attribute
54753	Attribute
54754	Attribute
54755	Attribute
54757	Entity
54758	Attribute
54759	Attribute
54760	Attribute
54761	Attribute
54762	Attribute
54763	Attribute
54764	Attribute
54765	Attribute
54766	Attribute
54767	Attribute
54768	Attribute
54769	Attribute
54771	Entity
54772	Attribute
54773	Attribute
54774	Attribute
54775	Attribute
54776	Attribute
54777	Attribute
54778	Attribute
54779	Attribute
54780	Attribute
54781	Attribute
54782	Attribute
54783	Attribute
54784	Attribute
54786	Entity
54787	Attribute
54788	Attribute
54789	Attribute
54790	Attribute
54791	Attribute
54792	Attribute
54793	Attribute
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
54804	Attribute
54805	Attribute
54806	Attribute
54807	Attribute
54808	Attribute
54809	Attribute
54810	Attribute
54811	Attribute
54812	Attribute
54814	Entity
54815	Attribute
54816	Attribute
54817	Attribute
54819	Entity
54820	Attribute
54821	Attribute
54822	Attribute
54823	Attribute
54824	Attribute
54825	Attribute
54826	Attribute
54827	Attribute
54828	Attribute
54829	Attribute
54830	Attribute
54832	Entity
54833	Attribute
54834	Attribute
54835	Attribute
54836	Attribute
54837	Attribute
54838	Attribute
54839	Attribute
54840	Attribute
54841	Attribute
54842	Attribute
54843	Attribute
54844	Attribute
54846	Entity
54847	Attribute
54848	Attribute
54849	Attribute
54850	Attribute
54851	Attribute
54852	Attribute
54853	Attribute
54854	Attribute
54855	Attribute
54856	Attribute
54857	Attribute
54858	Attribute
54859	Attribute
54860	Attribute
54861	Attribute
54862	Attribute
54863	Attribute
54864	Attribute
54865	Attribute
54866	Attribute
54867	Attribute
54868	Attribute
54869	Attribute
54870	Attribute
54871	Attribute
54872	Attribute
54873	Attribute
54874	Attribute
54875	Attribute
54877	Entity
54878	Attribute
54879	Attribute
54880	Attribute
54881	Attribute
54882	Attribute
54883	Attribute
54884	Attribute
54885	Attribute
54886	Attribute
54887	Attribute
54888	Attribute
54889	Attribute
54890	Attribute
54891	Attribute
54892	Attribute
54893	Attribute
54894	Attribute
54895	Attribute
54896	Attribute
54898	Entity
54899	Attribute
54900	Attribute
54901	Attribute
54902	Attribute
54904	Entity
54905	Attribute
54906	Attribute
54907	Attribute
54908	Attribute
54909	Attribute
54910	Attribute
54911	Attribute
54912	Attribute
54913	Attribute
54914	Attribute
54916	Entity
54917	Attribute
54918	Attribute
54919	Attribute
54920	Attribute
54921	Attribute
54922	Attribute
54923	Attribute
54924	Attribute
54925	Attribute
54926	Attribute
54927	Attribute
54928	Attribute
54930	Entity
54931	Attribute
54932	Attribute
54933	Attribute
54934	Attribute
54935	Attribute
54936	Attribute
54937	Attribute
54938	Attribute
54939	Attribute
54940	Attribute
54942	Entity
54943	Attribute
54944	Attribute
54945	Attribute
54946	Attribute
54947	Attribute
54948	Attribute
54949	Attribute
54950	Attribute
54951	Attribute
54952	Attribute
54954	Entity
54955	Attribute
54956	Attribute
54957	Attribute
54958	Attribute
54959	Attribute
54960	Attribute
54961	Attribute
54962	Attribute
54963	Attribute
54964	Attribute
54965	Attribute
54966	Attribute
54967	Attribute
54968	Attribute
54969	Attribute
54970	Attribute
54971	Attribute
54972	Attribute
54973	Attribute
54974	Attribute
54975	Attribute
54976	Attribute
54977	Attribute
54978	Attribute
54979	Attribute
54980	Attribute
54981	Attribute
54982	Attribute
54983	Attribute
54984	Attribute
54985	Attribute
54986	Attribute
54987	Attribute
54988	Attribute
54989	Attribute
54990	Attribute
54991	Attribute
54992	Attribute
54993	Attribute
54994	Attribute
54995	Attribute
54996	Attribute
54997	Attribute
54998	Attribute
54999	Attribute
55000	Attribute
55001	Attribute
55002	Attribute
55003	Attribute
55004	Attribute
55005	Attribute
55006	Attribute
55007	Attribute
55008	Attribute
55009	Attribute
55010	Attribute
55011	Attribute
55012	Attribute
55013	Attribute
55014	Attribute
55016	Entity
55017	Attribute
55018	Attribute
55019	Attribute
55020	Attribute
55021	Attribute
55022	Attribute
55023	Attribute
55024	Attribute
55025	Attribute
55026	Attribute
55027	Attribute
55028	Attribute
55029	Attribute
55030	Attribute
55031	Attribute
55032	Attribute
55033	Attribute
55035	Entity
55036	Attribute
55037	Attribute
55038	Attribute
55039	Attribute
55041	Entity
55042	Attribute
55043	Attribute
55044	Attribute
55046	Entity
55047	Attribute
55048	Attribute
55049	Attribute
55051	Entity
55052	Attribute
55053	Attribute
55054	Attribute
55055	Attribute
55056	Attribute
55057	Attribute
55059	Entity
55060	Attribute
55061	Attribute
55062	Attribute
55063	Attribute
55064	Attribute
55066	Entity
55067	Attribute
55068	Attribute
55069	Attribute
55070	Attribute
55071	Attribute
55073	Entity
55074	Attribute
55075	Attribute
55076	Attribute
55077	Attribute
55078	Attribute
55079	Attribute
55081	Entity
55082	Attribute
55083	Attribute
55084	Attribute
55085	Attribute
55086	Attribute
55087	Attribute
55088	Attribute
55089	Attribute
55090	Attribute
55091	Attribute
55092	Attribute
55093	Attribute
55095	Entity
55096	Attribute
55097	Attribute
55098	Attribute
55099	Attribute
55101	Entity
55102	Attribute
55103	Attribute
55104	Attribute
55105	Attribute
55106	Attribute
55108	Entity
55109	Attribute
55110	Attribute
55111	Attribute
55112	Attribute
55113	Attribute
55114	Attribute
55115	Attribute
55116	Attribute
55117	Attribute
55118	Attribute
55119	Attribute
55120	Attribute
55121	Attribute
55122	Attribute
55123	Attribute
55124	Attribute
55125	Attribute
55126	Attribute
55127	Attribute
55128	Attribute
55130	Entity
55131	Attribute
55132	Attribute
55133	Attribute
55134	Attribute
55135	Attribute
55136	Attribute
55137	Attribute
55138	Attribute
55140	Entity
55141	Attribute
55142	Attribute
55143	Attribute
55145	Entity
55146	Attribute
55147	Attribute
55148	Attribute
55149	Attribute
55150	Attribute
55151	Attribute
55153	Entity
55154	Attribute
55155	Attribute
55156	Attribute
55157	Attribute
55158	Attribute
55159	Attribute
55160	Attribute
55161	Attribute
55163	Entity
55164	Attribute
55165	Attribute
55166	Attribute
55167	Attribute
55168	Attribute
55169	Attribute
55170	Attribute
55171	Attribute
55172	Attribute
55174	Entity
55175	Attribute
55176	Attribute
55177	Attribute
55178	Attribute
55179	Attribute
55180	Attribute
55182	Entity
55183	Attribute
55184	Attribute
55185	Attribute
55186	Attribute
55187	Attribute
55188	Attribute
55189	Attribute
55191	Entity
55192	Attribute
55193	Attribute
55194	Attribute
55195	Attribute
55196	Attribute
55197	Attribute
55198	Attribute
55199	Attribute
55200	Attribute
55201	Attribute
55203	Entity
55204	Attribute
55205	Attribute
55206	Attribute
55207	Attribute
55208	Attribute
55209	Attribute
55211	Entity
55212	Attribute
55213	Attribute
55214	Attribute
55215	Attribute
55216	Attribute
55217	Attribute
55218	Attribute
55219	Attribute
55220	Attribute
55221	Attribute
55222	Attribute
55223	Attribute
55224	Attribute
55225	Attribute
55227	Entity
55228	Attribute
55229	Attribute
55230	Attribute
55231	Attribute
55232	Attribute
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

