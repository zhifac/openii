// $ANTLR 2.7.7 (2006-11-01): "expandedSqlSQL2.g" -> "SqlSQL2Lexer.java"$

//  Global header starts here, at the top of all generated files
package org.mitre.flexidata.ygg.importers.ddl;

import java.util.*;
import org.mitre.schemastore.model.*;
import org.mitre.flexidata.ygg.importers.*;

//  Global header ends here

import java.io.InputStream;
import antlr.TokenStreamException;
import antlr.TokenStreamIOException;
import antlr.TokenStreamRecognitionException;
import antlr.CharStreamException;
import antlr.CharStreamIOException;
import antlr.ANTLRException;
import java.io.Reader;
import java.util.Hashtable;
import antlr.CharScanner;
import antlr.InputBuffer;
import antlr.ByteBuffer;
import antlr.CharBuffer;
import antlr.Token;
import antlr.CommonToken;
import antlr.RecognitionException;
import antlr.NoViableAltForCharException;
import antlr.MismatchedCharException;
import antlr.TokenStream;
import antlr.ANTLRHashString;
import antlr.LexerSharedInputState;
import antlr.collections.impl.BitSet;
import antlr.SemanticException;

//  Class preamble starts here - right before the class definition in the generated class file

//  Class preamble ends here

public class SqlSQL2Lexer extends antlr.CharScanner implements SqlSQL2LexTokenTypes, TokenStream
 {

//  Class body inset starts here - at the top within the generated class body

//  Class body inset ends here
public SqlSQL2Lexer(InputStream in) {
	this(new ByteBuffer(in));
}
public SqlSQL2Lexer(Reader in) {
	this(new CharBuffer(in));
}
public SqlSQL2Lexer(InputBuffer ib) {
	this(new LexerSharedInputState(ib));
}
public SqlSQL2Lexer(LexerSharedInputState state) {
	super(state);
	caseSensitiveLiterals = false;
	setCaseSensitive(true);
	literals = new Hashtable();
	literals.put(new ANTLRHashString("year", this), new Integer(280));
	literals.put(new ANTLRHashString("no", this), new Integer(192));
	literals.put(new ANTLRHashString("catalog_name", this), new Integer(7));
	literals.put(new ANTLRHashString("both", this), new Integer(74));
	literals.put(new ANTLRHashString("trim", this), new Integer(259));
	literals.put(new ANTLRHashString("character_set_name", this), new Integer(9));
	literals.put(new ANTLRHashString("unknown", this), new Integer(263));
	literals.put(new ANTLRHashString("timestamp", this), new Integer(251));
	literals.put(new ANTLRHashString("distinct", this), new Integer(123));
	literals.put(new ANTLRHashString("ada", this), new Integer(5));
	literals.put(new ANTLRHashString("session_user", this), new Integer(234));
	literals.put(new ANTLRHashString("match", this), new Integer(181));
	literals.put(new ANTLRHashString("at", this), new Integer(67));
	literals.put(new ANTLRHashString("cross", this), new Integer(101));
	literals.put(new ANTLRHashString("restrict", this), new Integer(223));
	literals.put(new ANTLRHashString("trailing", this), new Integer(255));
	literals.put(new ANTLRHashString("deferrable", this), new Integer(115));
	literals.put(new ANTLRHashString("translate", this), new Integer(257));
	literals.put(new ANTLRHashString("is", this), new Integer(169));
	literals.put(new ANTLRHashString("identity", this), new Integer(155));
	literals.put(new ANTLRHashString("local", this), new Integer(179));
	literals.put(new ANTLRHashString("as", this), new Integer(64));
	literals.put(new ANTLRHashString("end", this), new Integer(128));
	literals.put(new ANTLRHashString("create", this), new Integer(100));
	literals.put(new ANTLRHashString("domain", this), new Integer(124));
	literals.put(new ANTLRHashString("language", this), new Integer(173));
	literals.put(new ANTLRHashString("bit", this), new Integer(72));
	literals.put(new ANTLRHashString("execute", this), new Integer(134));
	literals.put(new ANTLRHashString("primary", this), new Integer(214));
	literals.put(new ANTLRHashString("diagnostics", this), new Integer(121));
	literals.put(new ANTLRHashString("dynamic_function", this), new Integer(28));
	literals.put(new ANTLRHashString("int", this), new Integer(164));
	literals.put(new ANTLRHashString("immediate", this), new Integer(156));
	literals.put(new ANTLRHashString("assertion", this), new Integer(66));
	literals.put(new ANTLRHashString("declare", this), new Integer(113));
	literals.put(new ANTLRHashString("fortran", this), new Integer(29));
	literals.put(new ANTLRHashString("dec", this), new Integer(111));
	literals.put(new ANTLRHashString("whenever", this), new Integer(275));
	literals.put(new ANTLRHashString("deferred", this), new Integer(116));
	literals.put(new ANTLRHashString("prior", this), new Integer(215));
	literals.put(new ANTLRHashString("disconnect", this), new Integer(122));
	literals.put(new ANTLRHashString("in", this), new Integer(157));
	literals.put(new ANTLRHashString("option", this), new Integer(202));
	literals.put(new ANTLRHashString("octet_length", this), new Integer(197));
	literals.put(new ANTLRHashString("are", this), new Integer(63));
	literals.put(new ANTLRHashString("column_name", this), new Integer(16));
	literals.put(new ANTLRHashString("close", this), new Integer(86));
	literals.put(new ANTLRHashString("left", this), new Integer(176));
	literals.put(new ANTLRHashString("day", this), new Integer(109));
	literals.put(new ANTLRHashString("where", this), new Integer(276));
	literals.put(new ANTLRHashString("group", this), new Integer(152));
	literals.put(new ANTLRHashString("grant", this), new Integer(151));
	literals.put(new ANTLRHashString("found", this), new Integer(144));
	literals.put(new ANTLRHashString("prepare", this), new Integer(212));
	literals.put(new ANTLRHashString("catalog", this), new Integer(80));
	literals.put(new ANTLRHashString("level", this), new Integer(177));
	literals.put(new ANTLRHashString("connect", this), new Integer(92));
	literals.put(new ANTLRHashString("union", this), new Integer(261));
	literals.put(new ANTLRHashString("from", this), new Integer(145));
	literals.put(new ANTLRHashString("continue", this), new Integer(96));
	literals.put(new ANTLRHashString("sqlerror", this), new Integer(242));
	literals.put(new ANTLRHashString("action", this), new Integer(56));
	literals.put(new ANTLRHashString("to", this), new Integer(254));
	literals.put(new ANTLRHashString("more", this), new Integer(34));
	literals.put(new ANTLRHashString("message_length", this), new Integer(31));
	literals.put(new ANTLRHashString("constraint_name", this), new Integer(22));
	literals.put(new ANTLRHashString("collation_name", this), new Integer(14));
	literals.put(new ANTLRHashString("right", this), new Integer(225));
	literals.put(new ANTLRHashString("datetime_interval_code", this), new Integer(26));
	literals.put(new ANTLRHashString("extract", this), new Integer(137));
	literals.put(new ANTLRHashString("datetime_interval_precision", this), new Integer(27));
	literals.put(new ANTLRHashString("cascade", this), new Integer(76));
	literals.put(new ANTLRHashString("any", this), new Integer(62));
	literals.put(new ANTLRHashString("number", this), new Integer(38));
	literals.put(new ANTLRHashString("table_name", this), new Integer(51));
	literals.put(new ANTLRHashString("upper", this), new Integer(265));
	literals.put(new ANTLRHashString("lower", this), new Integer(180));
	literals.put(new ANTLRHashString("update", this), new Integer(264));
	literals.put(new ANTLRHashString("preserve", this), new Integer(213));
	literals.put(new ANTLRHashString("sql", this), new Integer(240));
	literals.put(new ANTLRHashString("section", this), new Integer(231));
	literals.put(new ANTLRHashString("by", this), new Integer(75));
	literals.put(new ANTLRHashString("or", this), new Integer(203));
	literals.put(new ANTLRHashString("hour", this), new Integer(154));
	literals.put(new ANTLRHashString("inner", this), new Integer(160));
	literals.put(new ANTLRHashString("time", this), new Integer(250));
	literals.put(new ANTLRHashString("select", this), new Integer(232));
	literals.put(new ANTLRHashString("national", this), new Integer(188));
	literals.put(new ANTLRHashString("end-exec", this), new Integer(129));
	literals.put(new ANTLRHashString("serializable", this), new Integer(48));
	literals.put(new ANTLRHashString("isolation", this), new Integer(170));
	literals.put(new ANTLRHashString("natural", this), new Integer(189));
	literals.put(new ANTLRHashString("column", this), new Integer(90));
	literals.put(new ANTLRHashString("current_timestamp", this), new Integer(105));
	literals.put(new ANTLRHashString("escape", this), new Integer(130));
	literals.put(new ANTLRHashString("commit", this), new Integer(91));
	literals.put(new ANTLRHashString("user", this), new Integer(267));
	literals.put(new ANTLRHashString("order", this), new Integer(204));
	literals.put(new ANTLRHashString("between", this), new Integer(71));
	literals.put(new ANTLRHashString("insensitive", this), new Integer(162));
	literals.put(new ANTLRHashString("go", this), new Integer(149));
	literals.put(new ANTLRHashString("row_count", this), new Integer(45));
	literals.put(new ANTLRHashString("on", this), new Integer(199));
	literals.put(new ANTLRHashString("sqlstate", this), new Integer(243));
	literals.put(new ANTLRHashString("zone", this), new Integer(281));
	literals.put(new ANTLRHashString("float", this), new Integer(141));
	literals.put(new ANTLRHashString("table", this), new Integer(247));
	literals.put(new ANTLRHashString("constraint_catalog", this), new Integer(21));
	literals.put(new ANTLRHashString("constraint", this), new Integer(94));
	literals.put(new ANTLRHashString("revoke", this), new Integer(224));
	literals.put(new ANTLRHashString("coalesce", this), new Integer(87));
	literals.put(new ANTLRHashString("describe", this), new Integer(119));
	literals.put(new ANTLRHashString("global", this), new Integer(148));
	literals.put(new ANTLRHashString("class_origin", this), new Integer(11));
	literals.put(new ANTLRHashString("not", this), new Integer(193));
	literals.put(new ANTLRHashString("schema_name", this), new Integer(47));
	literals.put(new ANTLRHashString("length", this), new Integer(30));
	literals.put(new ANTLRHashString("integer", this), new Integer(165));
	literals.put(new ANTLRHashString("name", this), new Integer(36));
	literals.put(new ANTLRHashString("scale", this), new Integer(46));
	literals.put(new ANTLRHashString("pascal", this), new Integer(39));
	literals.put(new ANTLRHashString("collation", this), new Integer(89));
	literals.put(new ANTLRHashString("cast", this), new Integer(79));
	literals.put(new ANTLRHashString("input", this), new Integer(161));
	literals.put(new ANTLRHashString("constraint_schema", this), new Integer(23));
	literals.put(new ANTLRHashString("smallint", this), new Integer(237));
	literals.put(new ANTLRHashString("procedure", this), new Integer(217));
	literals.put(new ANTLRHashString("returned_sqlstate", this), new Integer(44));
	literals.put(new ANTLRHashString("drop", this), new Integer(126));
	literals.put(new ANTLRHashString("alter", this), new Integer(60));
	literals.put(new ANTLRHashString("second", this), new Integer(230));
	literals.put(new ANTLRHashString("allocate", this), new Integer(59));
	literals.put(new ANTLRHashString("usage", this), new Integer(266));
	literals.put(new ANTLRHashString("date", this), new Integer(108));
	literals.put(new ANTLRHashString("values", this), new Integer(270));
	literals.put(new ANTLRHashString("session", this), new Integer(233));
	literals.put(new ANTLRHashString("numeric", this), new Integer(196));
	literals.put(new ANTLRHashString("names", this), new Integer(187));
	literals.put(new ANTLRHashString("cursor", this), new Integer(107));
	literals.put(new ANTLRHashString("nullable", this), new Integer(37));
	literals.put(new ANTLRHashString("delete", this), new Integer(117));
	literals.put(new ANTLRHashString("open", this), new Integer(201));
	literals.put(new ANTLRHashString("authorization", this), new Integer(68));
	literals.put(new ANTLRHashString("desc", this), new Integer(118));
	literals.put(new ANTLRHashString("varying", this), new Integer(272));
	literals.put(new ANTLRHashString("some", this), new Integer(238));
	literals.put(new ANTLRHashString("temporary", this), new Integer(248));
	literals.put(new ANTLRHashString("max", this), new Integer(182));
	literals.put(new ANTLRHashString("references", this), new Integer(221));
	literals.put(new ANTLRHashString("character", this), new Integer(82));
	literals.put(new ANTLRHashString("timezone_minute", this), new Integer(253));
	literals.put(new ANTLRHashString("character_length", this), new Integer(84));
	literals.put(new ANTLRHashString("external", this), new Integer(136));
	literals.put(new ANTLRHashString("substring", this), new Integer(244));
	literals.put(new ANTLRHashString("get", this), new Integer(147));
	literals.put(new ANTLRHashString("of", this), new Integer(198));
	literals.put(new ANTLRHashString("then", this), new Integer(249));
	literals.put(new ANTLRHashString("data", this), new Integer(25));
	literals.put(new ANTLRHashString("nullif", this), new Integer(195));
	literals.put(new ANTLRHashString("insert", this), new Integer(163));
	literals.put(new ANTLRHashString("message_octet_length", this), new Integer(32));
	literals.put(new ANTLRHashString("asc", this), new Integer(65));
	literals.put(new ANTLRHashString("transaction", this), new Integer(256));
	literals.put(new ANTLRHashString("constraints", this), new Integer(95));
	literals.put(new ANTLRHashString("rollback", this), new Integer(226));
	literals.put(new ANTLRHashString("exception", this), new Integer(132));
	literals.put(new ANTLRHashString("public", this), new Integer(218));
	literals.put(new ANTLRHashString("real", this), new Integer(220));
	literals.put(new ANTLRHashString("avg", this), new Integer(69));
	literals.put(new ANTLRHashString("rows", this), new Integer(227));
	literals.put(new ANTLRHashString("next", this), new Integer(191));
	literals.put(new ANTLRHashString("join", this), new Integer(171));
	literals.put(new ANTLRHashString("count", this), new Integer(99));
	literals.put(new ANTLRHashString("overlaps", this), new Integer(207));
	literals.put(new ANTLRHashString("collate", this), new Integer(88));
	literals.put(new ANTLRHashString("when", this), new Integer(274));
	literals.put(new ANTLRHashString("server_name", this), new Integer(49));
	literals.put(new ANTLRHashString("goto", this), new Integer(150));
	literals.put(new ANTLRHashString("true", this), new Integer(260));
	literals.put(new ANTLRHashString("decimal", this), new Integer(112));
	literals.put(new ANTLRHashString("current_time", this), new Integer(104));
	literals.put(new ANTLRHashString("char_length", this), new Integer(83));
	literals.put(new ANTLRHashString("current", this), new Integer(102));
	literals.put(new ANTLRHashString("set", this), new Integer(235));
	literals.put(new ANTLRHashString("foreign", this), new Integer(143));
	literals.put(new ANTLRHashString("last", this), new Integer(174));
	literals.put(new ANTLRHashString("mumps", this), new Integer(35));
	literals.put(new ANTLRHashString("current_user", this), new Integer(106));
	literals.put(new ANTLRHashString("character_set_schema", this), new Integer(10));
	literals.put(new ANTLRHashString("c", this), new Integer(6));
	literals.put(new ANTLRHashString("view", this), new Integer(273));
	literals.put(new ANTLRHashString("and", this), new Integer(61));
	literals.put(new ANTLRHashString("convert", this), new Integer(97));
	literals.put(new ANTLRHashString("work", this), new Integer(278));
	literals.put(new ANTLRHashString("pad", this), new Integer(208));
	literals.put(new ANTLRHashString("leading", this), new Integer(175));
	literals.put(new ANTLRHashString("like", this), new Integer(178));
	literals.put(new ANTLRHashString("check", this), new Integer(85));
	literals.put(new ANTLRHashString("condition_number", this), new Integer(19));
	literals.put(new ANTLRHashString("pli", this), new Integer(40));
	literals.put(new ANTLRHashString("only", this), new Integer(200));
	literals.put(new ANTLRHashString("min", this), new Integer(183));
	literals.put(new ANTLRHashString("false", this), new Integer(138));
	literals.put(new ANTLRHashString("space", this), new Integer(239));
	literals.put(new ANTLRHashString("cursor_name", this), new Integer(24));
	literals.put(new ANTLRHashString("for", this), new Integer(142));
	literals.put(new ANTLRHashString("default", this), new Integer(114));
	literals.put(new ANTLRHashString("unique", this), new Integer(262));
	literals.put(new ANTLRHashString("deallocate", this), new Integer(110));
	literals.put(new ANTLRHashString("scroll", this), new Integer(229));
	literals.put(new ANTLRHashString("case", this), new Integer(78));
	literals.put(new ANTLRHashString("returned_length", this), new Integer(42));
	literals.put(new ANTLRHashString("repeatable", this), new Integer(41));
	literals.put(new ANTLRHashString("initially", this), new Integer(159));
	literals.put(new ANTLRHashString("cascaded", this), new Integer(77));
	literals.put(new ANTLRHashString("translation", this), new Integer(258));
	literals.put(new ANTLRHashString("into", this), new Integer(168));
	literals.put(new ANTLRHashString("collation_schema", this), new Integer(15));
	literals.put(new ANTLRHashString("null", this), new Integer(194));
	literals.put(new ANTLRHashString("read", this), new Integer(219));
	literals.put(new ANTLRHashString("nchar", this), new Integer(190));
	literals.put(new ANTLRHashString("all", this), new Integer(58));
	literals.put(new ANTLRHashString("sum", this), new Integer(245));
	literals.put(new ANTLRHashString("using", this), new Integer(268));
	literals.put(new ANTLRHashString("having", this), new Integer(153));
	literals.put(new ANTLRHashString("connection", this), new Integer(93));
	literals.put(new ANTLRHashString("bit_length", this), new Integer(73));
	literals.put(new ANTLRHashString("committed", this), new Integer(18));
	literals.put(new ANTLRHashString("exists", this), new Integer(135));
	literals.put(new ANTLRHashString("current_date", this), new Integer(103));
	literals.put(new ANTLRHashString("system_user", this), new Integer(246));
	literals.put(new ANTLRHashString("key", this), new Integer(172));
	literals.put(new ANTLRHashString("double", this), new Integer(125));
	literals.put(new ANTLRHashString("size", this), new Integer(236));
	literals.put(new ANTLRHashString("precision", this), new Integer(211));
	literals.put(new ANTLRHashString("first", this), new Integer(140));
	literals.put(new ANTLRHashString("command_function", this), new Integer(17));
	literals.put(new ANTLRHashString("month", this), new Integer(186));
	literals.put(new ANTLRHashString("uncommitted", this), new Integer(53));
	literals.put(new ANTLRHashString("absolute", this), new Integer(55));
	literals.put(new ANTLRHashString("intersect", this), new Integer(166));
	literals.put(new ANTLRHashString("schema", this), new Integer(228));
	literals.put(new ANTLRHashString("fetch", this), new Integer(139));
	literals.put(new ANTLRHashString("sqlcode", this), new Integer(241));
	literals.put(new ANTLRHashString("except", this), new Integer(131));
	literals.put(new ANTLRHashString("connection_name", this), new Integer(20));
	literals.put(new ANTLRHashString("output", this), new Integer(206));
	literals.put(new ANTLRHashString("corresponding", this), new Integer(98));
	literals.put(new ANTLRHashString("value", this), new Integer(269));
	literals.put(new ANTLRHashString("write", this), new Integer(279));
	literals.put(new ANTLRHashString("exec", this), new Integer(133));
	literals.put(new ANTLRHashString("comment", this), new Integer(325));
	literals.put(new ANTLRHashString("collation_catalog", this), new Integer(13));
	literals.put(new ANTLRHashString("unnamed", this), new Integer(54));
	literals.put(new ANTLRHashString("char", this), new Integer(81));
	literals.put(new ANTLRHashString("outer", this), new Integer(205));
	literals.put(new ANTLRHashString("indicator", this), new Integer(158));
	literals.put(new ANTLRHashString("full", this), new Integer(146));
	literals.put(new ANTLRHashString("cobol", this), new Integer(12));
	literals.put(new ANTLRHashString("character_set_catalog", this), new Integer(8));
	literals.put(new ANTLRHashString("returned_octet_length", this), new Integer(43));
	literals.put(new ANTLRHashString("timezone_hour", this), new Integer(252));
	literals.put(new ANTLRHashString("type", this), new Integer(52));
	literals.put(new ANTLRHashString("begin", this), new Integer(70));
	literals.put(new ANTLRHashString("privileges", this), new Integer(216));
	literals.put(new ANTLRHashString("descriptor", this), new Integer(120));
	literals.put(new ANTLRHashString("add", this), new Integer(57));
	literals.put(new ANTLRHashString("module", this), new Integer(185));
	literals.put(new ANTLRHashString("position", this), new Integer(210));
	literals.put(new ANTLRHashString("with", this), new Integer(277));
	literals.put(new ANTLRHashString("relative", this), new Integer(222));
	literals.put(new ANTLRHashString("interval", this), new Integer(167));
	literals.put(new ANTLRHashString("varchar", this), new Integer(271));
	literals.put(new ANTLRHashString("partial", this), new Integer(209));
	literals.put(new ANTLRHashString("else", this), new Integer(127));
	literals.put(new ANTLRHashString("message_text", this), new Integer(33));
	literals.put(new ANTLRHashString("subclass_origin", this), new Integer(50));
	literals.put(new ANTLRHashString("minute", this), new Integer(184));
}

public Token nextToken() throws TokenStreamException {
	Token theRetToken=null;
tryAgain:
	for (;;) {
		Token _token = null;
		int _ttype = Token.INVALID_TYPE;
		setCommitToPath(false);
		int _m;
		_m = mark();
		resetText();
		try {   // for char stream error handling
			try {   // for lexical error handling
				switch ( LA(1)) {
				case '%':
				{
					mPERCENT(true);
					theRetToken=_returnToken;
					break;
				}
				case '.':  case '0':  case '1':  case '2':
				case '3':  case '4':  case '5':  case '6':
				case '7':  case '8':  case '9':
				{
					mEXACT_NUM_LIT(true);
					theRetToken=_returnToken;
					break;
				}
				case '\'':
				{
					mCHAR_STRING(true);
					theRetToken=_returnToken;
					break;
				}
				case '"':
				{
					mDELIMITED_ID(true);
					theRetToken=_returnToken;
					break;
				}
				case '&':
				{
					mAMPERSAND(true);
					theRetToken=_returnToken;
					break;
				}
				case '(':
				{
					mLEFT_PAREN(true);
					theRetToken=_returnToken;
					break;
				}
				case ')':
				{
					mRIGHT_PAREN(true);
					theRetToken=_returnToken;
					break;
				}
				case '*':
				{
					mASTERISK(true);
					theRetToken=_returnToken;
					break;
				}
				case '+':
				{
					mPLUS_SIGN(true);
					theRetToken=_returnToken;
					break;
				}
				case ',':
				{
					mCOMMA(true);
					theRetToken=_returnToken;
					break;
				}
				case '/':
				{
					mSOLIDUS(true);
					theRetToken=_returnToken;
					break;
				}
				case ':':
				{
					mCOLON(true);
					theRetToken=_returnToken;
					break;
				}
				case ';':
				{
					mSEMICOLON(true);
					theRetToken=_returnToken;
					break;
				}
				case '<':
				{
					mLESS_THAN_OP(true);
					theRetToken=_returnToken;
					break;
				}
				case '=':
				{
					mEQUALS_OP(true);
					theRetToken=_returnToken;
					break;
				}
				case '>':
				{
					mGREATER_THAN_OP(true);
					theRetToken=_returnToken;
					break;
				}
				case '?':
				{
					mQUESTION_MARK(true);
					theRetToken=_returnToken;
					break;
				}
				case '|':
				{
					mVERTICAL_BAR(true);
					theRetToken=_returnToken;
					break;
				}
				case '[':
				{
					mLEFT_BRACKET(true);
					theRetToken=_returnToken;
					break;
				}
				case ']':
				{
					mRIGHT_BRACKET(true);
					theRetToken=_returnToken;
					break;
				}
				case '_':
				{
					mINTRODUCER(true);
					theRetToken=_returnToken;
					break;
				}
				case '-':
				{
					mLINE_COMMENT(true);
					theRetToken=_returnToken;
					break;
				}
				default:
					if ((_tokenSet_0.member(LA(1)))) {
						mREGULAR_ID(true);
						theRetToken=_returnToken;
					}
				else {
					if (LA(1)==EOF_CHAR) {uponEOF(); _returnToken = makeToken(Token.EOF_TYPE);}
				else {
					commit();
					try {mANY_CHAR(false);}
					catch(RecognitionException e) {
						// catastrophic failure
						reportError(e);
						consume();
					}
					continue tryAgain;
				}
				}
				}
				commit();
				if ( _returnToken==null ) continue tryAgain; // found SKIP token
				_ttype = _returnToken.getType();
				_returnToken.setType(_ttype);
				return _returnToken;
			}
			catch (RecognitionException e) {
				if ( !getCommitToPath() ) {
					rewind(_m);
					resetText();
					try {mANY_CHAR(false);}
					catch(RecognitionException ee) {
						// horrendous failure: error in filter rule
						reportError(ee);
						consume();
					}
					continue tryAgain;
				}
				throw new TokenStreamRecognitionException(e);
			}
		}
		catch (CharStreamException cse) {
			if ( cse instanceof CharStreamIOException ) {
				throw new TokenStreamIOException(((CharStreamIOException)cse).io);
			}
			else {
				throw new TokenStreamException(cse.getMessage());
			}
		}
	}
}

	public final void mPERCENT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = PERCENT;
		int _saveIndex;

		match('%');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	public final void mREGULAR_ID(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = REGULAR_ID;
		int _saveIndex;

		if ((_tokenSet_1.member(LA(1))) && (LA(2)=='\'')) {
			{
			switch ( LA(1)) {
			case 'N':  case 'n':
			{
				mNATIONAL_CHAR_STRING_LIT(false);
				_ttype = NATIONAL_CHAR_STRING_LIT;
				break;
			}
			case 'B':  case 'b':
			{
				mBIT_STRING_LIT(false);
				_ttype = BIT_STRING_LIT;
				break;
			}
			case 'X':  case 'x':
			{
				mHEX_STRING_LIT(false);
				_ttype = HEX_STRING_LIT;
				break;
			}
			default:
			{
				throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
			}
			}
			}
		}
		else if ((_tokenSet_0.member(LA(1))) && (true)) {
			{
			mSIMPLE_LETTER(false);
			}
			{
			_loop6:
			do {
				switch ( LA(1)) {
				case '_':
				{
					match('_');
					break;
				}
				case '0':  case '1':  case '2':  case '3':
				case '4':  case '5':  case '6':  case '7':
				case '8':  case '9':
				{
					matchRange('0','9');
					break;
				}
				default:
					if ((_tokenSet_0.member(LA(1)))) {
						mSIMPLE_LETTER(false);
					}
				else {
					break _loop6;
				}
				}
			} while (true);
			}
			_ttype = testLiteralsTable(REGULAR_ID);
		}
		else {
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
		}

		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	protected final void mNATIONAL_CHAR_STRING_LIT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = NATIONAL_CHAR_STRING_LIT;
		int _saveIndex;

		{
		switch ( LA(1)) {
		case 'N':
		{
			match('N');
			break;
		}
		case 'n':
		{
			match('n');
			break;
		}
		default:
		{
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
		}
		}
		}
		{
		int _cnt26=0;
		_loop26:
		do {
			if ((LA(1)=='\'')) {
				match('\'');
				{
				_loop24:
				do {
					if ((LA(1)=='\'') && (LA(2)=='\'')) {
						match('\'');
						match('\'');
					}
					else if ((_tokenSet_2.member(LA(1)))) {
						{
						match(_tokenSet_2);
						}
					}
					else if ((LA(1)=='\n'||LA(1)=='\r')) {
						mNEWLINE(false);
					}
					else {
						break _loop24;
					}

				} while (true);
				}
				match('\'');
				{
				if ((_tokenSet_3.member(LA(1)))) {
					mSEPARATOR(false);
				}
				else {
				}

				}
			}
			else {
				if ( _cnt26>=1 ) { break _loop26; } else {throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());}
			}

			_cnt26++;
		} while (true);
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	protected final void mBIT_STRING_LIT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = BIT_STRING_LIT;
		int _saveIndex;

		{
		switch ( LA(1)) {
		case 'B':
		{
			match('B');
			break;
		}
		case 'b':
		{
			match('b');
			break;
		}
		default:
		{
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
		}
		}
		}
		{
		int _cnt33=0;
		_loop33:
		do {
			if ((LA(1)=='\'')) {
				match('\'');
				{
				_loop31:
				do {
					switch ( LA(1)) {
					case '0':
					{
						match('0');
						break;
					}
					case '1':
					{
						match('1');
						break;
					}
					default:
					{
						break _loop31;
					}
					}
				} while (true);
				}
				match('\'');
				{
				if ((_tokenSet_3.member(LA(1)))) {
					mSEPARATOR(false);
				}
				else {
				}

				}
			}
			else {
				if ( _cnt33>=1 ) { break _loop33; } else {throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());}
			}

			_cnt33++;
		} while (true);
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	protected final void mHEX_STRING_LIT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = HEX_STRING_LIT;
		int _saveIndex;

		{
		switch ( LA(1)) {
		case 'X':
		{
			match('X');
			break;
		}
		case 'x':
		{
			match('x');
			break;
		}
		default:
		{
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
		}
		}
		}
		{
		int _cnt40=0;
		_loop40:
		do {
			if ((LA(1)=='\'')) {
				match('\'');
				{
				_loop38:
				do {
					switch ( LA(1)) {
					case 'a':  case 'b':  case 'c':  case 'd':
					case 'e':  case 'f':
					{
						matchRange('a','f');
						break;
					}
					case 'A':  case 'B':  case 'C':  case 'D':
					case 'E':  case 'F':
					{
						matchRange('A','F');
						break;
					}
					case '0':  case '1':  case '2':  case '3':
					case '4':  case '5':  case '6':  case '7':
					case '8':  case '9':
					{
						matchRange('0','9');
						break;
					}
					default:
					{
						break _loop38;
					}
					}
				} while (true);
				}
				match('\'');
				{
				if ((_tokenSet_3.member(LA(1)))) {
					mSEPARATOR(false);
				}
				else {
				}

				}
			}
			else {
				if ( _cnt40>=1 ) { break _loop40; } else {throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());}
			}

			_cnt40++;
		} while (true);
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	protected final void mSIMPLE_LETTER(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = SIMPLE_LETTER;
		int _saveIndex;

		switch ( LA(1)) {
		case 'a':  case 'b':  case 'c':  case 'd':
		case 'e':  case 'f':  case 'g':  case 'h':
		case 'i':  case 'j':  case 'k':  case 'l':
		case 'm':  case 'n':  case 'o':  case 'p':
		case 'q':  case 'r':  case 's':  case 't':
		case 'u':  case 'v':  case 'w':  case 'x':
		case 'y':  case 'z':
		{
			matchRange('a','z');
			break;
		}
		case 'A':  case 'B':  case 'C':  case 'D':
		case 'E':  case 'F':  case 'G':  case 'H':
		case 'I':  case 'J':  case 'K':  case 'L':
		case 'M':  case 'N':  case 'O':  case 'P':
		case 'Q':  case 'R':  case 'S':  case 'T':
		case 'U':  case 'V':  case 'W':  case 'X':
		case 'Y':  case 'Z':
		{
			matchRange('A','Z');
			break;
		}
		default:
			if (((LA(1) >= '\u007f' && LA(1) <= '\u00ff'))) {
				matchRange('\177','\377');
			}
		else {
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
		}
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	public final void mEXACT_NUM_LIT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = EXACT_NUM_LIT;
		int _saveIndex;

		if ((LA(1)=='.') && ((LA(2) >= '0' && LA(2) <= '9'))) {
			match('.');
			mUNSIGNED_INTEGER(false);
			{
			if ((LA(1)=='E'||LA(1)=='e')) {
				{
				switch ( LA(1)) {
				case 'E':
				{
					match('E');
					break;
				}
				case 'e':
				{
					match('e');
					break;
				}
				default:
				{
					throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
				}
				}
				}
				{
				switch ( LA(1)) {
				case '+':
				{
					match('+');
					break;
				}
				case '-':
				{
					match('-');
					break;
				}
				case '0':  case '1':  case '2':  case '3':
				case '4':  case '5':  case '6':  case '7':
				case '8':  case '9':
				{
					break;
				}
				default:
				{
					throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
				}
				}
				}
				mUNSIGNED_INTEGER(false);
				_ttype = APPROXIMATE_NUM_LIT;
			}
			else {
			}

			}
		}
		else if ((LA(1)=='.') && (LA(2)=='.')) {
			match('.');
			match('.');
			_ttype = DOUBLE_PERIOD;
		}
		else if (((LA(1) >= '0' && LA(1) <= '9'))) {
			mUNSIGNED_INTEGER(false);
			{
			if ((LA(1)=='.')) {
				match('.');
				{
				if (((LA(1) >= '0' && LA(1) <= '9'))) {
					mUNSIGNED_INTEGER(false);
				}
				else {
				}

				}
			}
			else {
				_ttype = UNSIGNED_INTEGER;
			}

			}
			{
			if ((LA(1)=='E'||LA(1)=='e')) {
				{
				switch ( LA(1)) {
				case 'E':
				{
					match('E');
					break;
				}
				case 'e':
				{
					match('e');
					break;
				}
				default:
				{
					throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
				}
				}
				}
				{
				switch ( LA(1)) {
				case '+':
				{
					match('+');
					break;
				}
				case '-':
				{
					match('-');
					break;
				}
				case '0':  case '1':  case '2':  case '3':
				case '4':  case '5':  case '6':  case '7':
				case '8':  case '9':
				{
					break;
				}
				default:
				{
					throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
				}
				}
				}
				mUNSIGNED_INTEGER(false);
				_ttype = APPROXIMATE_NUM_LIT;
			}
			else {
			}

			}
		}
		else if ((LA(1)=='.') && (true)) {
			match('.');
			_ttype = PERIOD;
		}
		else {
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
		}

		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	protected final void mUNSIGNED_INTEGER(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = UNSIGNED_INTEGER;
		int _saveIndex;

		{
		int _cnt18=0;
		_loop18:
		do {
			if (((LA(1) >= '0' && LA(1) <= '9'))) {
				matchRange('0','9');
			}
			else {
				if ( _cnt18>=1 ) { break _loop18; } else {throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());}
			}

			_cnt18++;
		} while (true);
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	protected final void mNEWLINE(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = NEWLINE;
		int _saveIndex;
		newline();

		{
		switch ( LA(1)) {
		case '\r':
		{
			match('\r');
			{
			if ((LA(1)=='\n') && (true)) {
				match('\n');
			}
			else {
			}

			}
			break;
		}
		case '\n':
		{
			match('\n');
			break;
		}
		default:
		{
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
		}
		}
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	protected final void mSEPARATOR(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = SEPARATOR;
		int _saveIndex;

		if ((LA(1)=='-') && (LA(2)=='-')) {
			mLINE_COMMENT(false);
		}
		else if ((LA(1)=='-') && (true)) {
			match('-');
			_ttype = MINUS_SIGN;
		}
		else if ((_tokenSet_4.member(LA(1)))) {
			{
			int _cnt78=0;
			_loop78:
			do {
				switch ( LA(1)) {
				case '\t':  case ' ':
				{
					mSPACE(false);
					break;
				}
				case '\n':  case '\r':
				{
					mNEWLINE(false);
					break;
				}
				default:
				{
					if ( _cnt78>=1 ) { break _loop78; } else {throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());}
				}
				}
				_cnt78++;
			} while (true);
			}
		}
		else {
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
		}

		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	public final void mCHAR_STRING(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = CHAR_STRING;
		int _saveIndex;

		if ((LA(1)=='\'') && ((LA(2) >= '\u0000' && LA(2) <= '\u00ff'))) {
			{
			int _cnt47=0;
			_loop47:
			do {
				if ((LA(1)=='\'')) {
					match('\'');
					{
					_loop45:
					do {
						if ((LA(1)=='\'') && (LA(2)=='\'')) {
							match('\'');
							match('\'');
						}
						else if ((_tokenSet_2.member(LA(1)))) {
							{
							match(_tokenSet_2);
							}
						}
						else if ((LA(1)=='\n'||LA(1)=='\r')) {
							mNEWLINE(false);
						}
						else {
							break _loop45;
						}

					} while (true);
					}
					match('\'');
					{
					if ((_tokenSet_3.member(LA(1)))) {
						mSEPARATOR(false);
					}
					else {
					}

					}
				}
				else {
					if ( _cnt47>=1 ) { break _loop47; } else {throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());}
				}

				_cnt47++;
			} while (true);
			}
		}
		else if ((LA(1)=='\'') && (true)) {
			match('\'');
			_ttype = QUOTE;
		}
		else {
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
		}

		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	public final void mDELIMITED_ID(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = DELIMITED_ID;
		int _saveIndex;

		match('"');
		{
		int _cnt51=0;
		_loop51:
		do {
			if ((LA(1)=='"') && (LA(2)=='"')) {
				match('"');
				match('"');
			}
			else if ((_tokenSet_5.member(LA(1)))) {
				{
				match(_tokenSet_5);
				}
			}
			else {
				if ( _cnt51>=1 ) { break _loop51; } else {throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());}
			}

			_cnt51++;
		} while (true);
		}
		match('"');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	public final void mAMPERSAND(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = AMPERSAND;
		int _saveIndex;

		match('&');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	public final void mLEFT_PAREN(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = LEFT_PAREN;
		int _saveIndex;

		match('(');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	public final void mRIGHT_PAREN(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = RIGHT_PAREN;
		int _saveIndex;

		match(')');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	public final void mASTERISK(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = ASTERISK;
		int _saveIndex;

		match('*');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	public final void mPLUS_SIGN(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = PLUS_SIGN;
		int _saveIndex;

		match('+');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	public final void mCOMMA(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = COMMA;
		int _saveIndex;

		match(',');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	public final void mSOLIDUS(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = SOLIDUS;
		int _saveIndex;

		match('/');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	public final void mCOLON(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = COLON;
		int _saveIndex;

		if ((LA(1)==':') && (_tokenSet_6.member(LA(2)))) {
			match(':');
			{
			int _cnt61=0;
			_loop61:
			do {
				switch ( LA(1)) {
				case '0':  case '1':  case '2':  case '3':
				case '4':  case '5':  case '6':  case '7':
				case '8':  case '9':
				{
					matchRange('0','9');
					break;
				}
				case '.':
				{
					match('.');
					break;
				}
				case '_':
				{
					match('_');
					break;
				}
				case '#':
				{
					match('#');
					break;
				}
				case '$':
				{
					match('$');
					break;
				}
				case '&':
				{
					match('&');
					break;
				}
				case '%':
				{
					match('%');
					break;
				}
				case '@':
				{
					match('@');
					break;
				}
				default:
					if ((_tokenSet_0.member(LA(1)))) {
						mSIMPLE_LETTER(false);
					}
				else {
					if ( _cnt61>=1 ) { break _loop61; } else {throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());}
				}
				}
				_cnt61++;
			} while (true);
			}
			_ttype = EMBDD_VARIABLE_NAME;
		}
		else if ((LA(1)==':') && (true)) {
			match(':');
		}
		else {
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
		}

		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	public final void mSEMICOLON(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = SEMICOLON;
		int _saveIndex;

		match(';');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	public final void mLESS_THAN_OP(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = LESS_THAN_OP;
		int _saveIndex;

		match('<');
		{
		switch ( LA(1)) {
		case '>':
		{
			match('>');
			_ttype = NOT_EQUALS_OP;
			break;
		}
		case '=':
		{
			match('=');
			_ttype = LESS_THAN_OR_EQUALS_OP;
			break;
		}
		default:
			{
			}
		}
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	public final void mEQUALS_OP(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = EQUALS_OP;
		int _saveIndex;

		match('=');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	public final void mGREATER_THAN_OP(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = GREATER_THAN_OP;
		int _saveIndex;

		match('>');
		{
		if ((LA(1)=='=')) {
			match('=');
			_ttype = GREATER_THAN_OR_EQUALS_OP;
		}
		else {
		}

		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	public final void mQUESTION_MARK(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = QUESTION_MARK;
		int _saveIndex;

		match('?');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	public final void mVERTICAL_BAR(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = VERTICAL_BAR;
		int _saveIndex;

		match('|');
		{
		if ((LA(1)=='|')) {
			match('|');
			_ttype = CONCATENATION_OP;
		}
		else {
		}

		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	public final void mLEFT_BRACKET(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = LEFT_BRACKET;
		int _saveIndex;

		match('[');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	public final void mRIGHT_BRACKET(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = RIGHT_BRACKET;
		int _saveIndex;

		match(']');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	public final void mINTRODUCER(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = INTRODUCER;
		int _saveIndex;

		match('_');
		{
		if ((_tokenSet_3.member(LA(1)))) {
			mSEPARATOR(false);
			_ttype = UNDERSCORE;
		}
		else {
		}

		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	public final void mLINE_COMMENT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = LINE_COMMENT;
		int _saveIndex;

		match("--");
		{
		_loop82:
		do {
			if ((_tokenSet_7.member(LA(1)))) {
				{
				match(_tokenSet_7);
				}
			}
			else {
				break _loop82;
			}

		} while (true);
		}
		{
		switch ( LA(1)) {
		case '\n':
		{
			match('\n');
			break;
		}
		case '\r':
		{
			match('\r');
			{
			if ((LA(1)=='\n')) {
				match('\n');
			}
			else {
			}

			}
			break;
		}
		default:
		{
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
		}
		}
		}
		_ttype = Token.SKIP; newline();
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	protected final void mSPACE(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = SPACE;
		int _saveIndex;

		switch ( LA(1)) {
		case ' ':
		{
			match(' ');
			break;
		}
		case '\t':
		{
			match('\t');
			break;
		}
		default:
		{
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
		}
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	protected final void mANY_CHAR(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = ANY_CHAR;
		int _saveIndex;

		matchNot(EOF_CHAR);
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}


	private static final long[] mk_tokenSet_0() {
		long[] data = new long[8];
		data[1]=-8646911293007069186L;
		for (int i = 2; i<=3; i++) { data[i]=-1L; }
		return data;
	}
	public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
	private static final long[] mk_tokenSet_1() {
		long[] data = { 0L, 72127979978768388L, 0L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
	private static final long[] mk_tokenSet_2() {
		long[] data = new long[8];
		data[0]=-549755823105L;
		for (int i = 1; i<=3; i++) { data[i]=-1L; }
		return data;
	}
	public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
	private static final long[] mk_tokenSet_3() {
		long[] data = { 35188667065856L, 0L, 0L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
	private static final long[] mk_tokenSet_4() {
		long[] data = { 4294977024L, 0L, 0L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
	private static final long[] mk_tokenSet_5() {
		long[] data = new long[8];
		data[0]=-17179878401L;
		for (int i = 1; i<=3; i++) { data[i]=-1L; }
		return data;
	}
	public static final BitSet _tokenSet_5 = new BitSet(mk_tokenSet_5());
	private static final long[] mk_tokenSet_6() {
		long[] data = new long[8];
		data[0]=288019785315254272L;
		data[1]=-8646911290859585537L;
		for (int i = 2; i<=3; i++) { data[i]=-1L; }
		return data;
	}
	public static final BitSet _tokenSet_6 = new BitSet(mk_tokenSet_6());
	private static final long[] mk_tokenSet_7() {
		long[] data = new long[8];
		data[0]=-9217L;
		for (int i = 1; i<=3; i++) { data[i]=-1L; }
		return data;
	}
	public static final BitSet _tokenSet_7 = new BitSet(mk_tokenSet_7());

	}
