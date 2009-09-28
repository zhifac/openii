// $ANTLR 3.1.2 /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g 2009-09-28 15:37:30

  package org.mitre.schemastore.porters.schemaImporters.ddl;

  import org.mitre.schemastore.model.*;
  import org.mitre.schemastore.porters.schemaImporters.*;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;


import org.antlr.runtime.tree.*;

public class SqlParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "ALIAS", "BIND", "BIND_NAME", "BLOB_LITERAL", "COLUMN_CONSTRAINT", "COLUMN_EXPRESSION", "COLUMNS", "CONSTRAINTS", "CREATE_INDEX", "CREATE_TABLE", "DROP_INDEX", "DROP_TABLE", "FLOAT_LITERAL", "FUNCTION_LITERAL", "FUNCTION_EXPRESSION", "ID_LITERAL", "IN_VALUES", "IN_TABLE", "INTEGER_LITERAL", "IS_NULL", "NOT_NULL", "OPTIONS", "ORDERING", "SELECT_CORE", "STRING_LITERAL", "TABLE_CONSTRAINT", "TYPE", "TYPE_PARAMS", "EXPLAIN", "QUERY", "PLAN", "SEMI", "DOT", "INDEXED", "BY", "NOT", "OR", "AND", "ESCAPE", "IN", "LPAREN", "COMMA", "RPAREN", "ISNULL", "NOTNULL", "IS", "NULL", "BETWEEN", "EQUALS", "EQUALS2", "NOT_EQUALS", "NOT_EQUALS2", "LIKE", "GLOB", "REGEXP", "MATCH", "LESS", "LESS_OR_EQ", "GREATER", "GREATER_OR_EQ", "SHIFT_LEFT", "SHIFT_RIGHT", "AMPERSAND", "PIPE", "PLUS", "MINUS", "ASTERISK", "SLASH", "PERCENT", "DOUBLE_PIPE", "TILDA", "COLLATE", "ID", "DISTINCT", "CAST", "AS", "CASE", "ELSE", "END", "WHEN", "THEN", "INTEGER", "FLOAT", "STRING", "BLOB", "CURRENT_TIME", "CURRENT_DATE", "CURRENT_TIMESTAMP", "QUESTION", "COLON", "AT", "RAISE", "IGNORE", "ROLLBACK", "ABORT", "FAIL", "MAX", "BYTE", "PRAGMA", "ATTACH", "DATABASE", "DETACH", "ANALYZE", "REINDEX", "VACUUM", "REPLACE", "ASC", "DESC", "ORDER", "LIMIT", "OFFSET", "UNION", "ALL", "INTERSECT", "EXCEPT", "SELECT", "FROM", "WHERE", "GROUP", "HAVING", "NATURAL", "LEFT", "OUTER", "INNER", "CROSS", "JOIN", "ON", "USING", "INSERT", "INTO", "VALUES", "DEFAULT", "UPDATE", "SET", "DELETE", "BEGIN", "DEFERRED", "IMMEDIATE", "EXCLUSIVE", "TRANSACTION", "COMMIT", "TO", "SAVEPOINT", "RELEASE", "CONFLICT", "CREATE", "VIRTUAL", "TABLE", "TEMPORARY", "IF", "EXISTS", "CONSTRAINT", "PRIMARY", "KEY", "AUTOINCREMENT", "FOR", "REPLICATION", "UNIQUE", "CHECK", "FOREIGN", "REFERENCES", "CASCADE", "RESTRICT", "DEFERRABLE", "INITIALLY", "DROP", "ALTER", "ONLY", "RENAME", "ADD", "COLUMN", "VIEW", "INDEX", "TRIGGER", "BEFORE", "AFTER", "INSTEAD", "OF", "EACH", "ROW", "META_COMMENT", "SCHEMA", "DOLLAR", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "ID_START", "ESCAPE_SEQ", "FLOAT_EXP", "COMMENT", "LINE_COMMENT", "WS"
    };
    public static final int ROW=183;
    public static final int BLOB_LITERAL=7;
    public static final int TYPE_PARAMS=31;
    public static final int NOT=39;
    public static final int EXCEPT=118;
    public static final int EOF=-1;
    public static final int FOREIGN=163;
    public static final int TYPE=30;
    public static final int RPAREN=46;
    public static final int CREATE=149;
    public static final int STRING_LITERAL=28;
    public static final int IS_NULL=23;
    public static final int USING=131;
    public static final int BIND=5;
    public static final int BEGIN=139;
    public static final int REGEXP=58;
    public static final int ANALYZE=106;
    public static final int FUNCTION_LITERAL=17;
    public static final int CONFLICT=148;
    public static final int LESS_OR_EQ=61;
    public static final int ATTACH=103;
    public static final int VIRTUAL=150;
    public static final int D=190;
    public static final int E=191;
    public static final int F=192;
    public static final int G=193;
    public static final int BLOB=88;
    public static final int A=187;
    public static final int B=188;
    public static final int C=189;
    public static final int ASC=110;
    public static final int L=198;
    public static final int M=199;
    public static final int N=200;
    public static final int O=201;
    public static final int TRANSACTION=143;
    public static final int KEY=157;
    public static final int H=194;
    public static final int I=195;
    public static final int BIND_NAME=6;
    public static final int J=196;
    public static final int ELSE=81;
    public static final int K=197;
    public static final int U=207;
    public static final int T=206;
    public static final int W=209;
    public static final int IN_VALUES=20;
    public static final int V=208;
    public static final int Q=203;
    public static final int P=202;
    public static final int S=205;
    public static final int R=204;
    public static final int ROLLBACK=97;
    public static final int FAIL=99;
    public static final int Y=211;
    public static final int RESTRICT=166;
    public static final int X=210;
    public static final int Z=212;
    public static final int INTERSECT=117;
    public static final int GROUP=122;
    public static final int DROP_INDEX=14;
    public static final int WS=218;
    public static final int PLAN=34;
    public static final int ALIAS=4;
    public static final int END=82;
    public static final int CONSTRAINT=155;
    public static final int REPLICATION=160;
    public static final int RENAME=172;
    public static final int ALTER=170;
    public static final int ONLY=171;
    public static final int ISNULL=47;
    public static final int TABLE=151;
    public static final int FLOAT=86;
    public static final int NOTNULL=48;
    public static final int NOT_EQUALS=54;
    public static final int ASTERISK=70;
    public static final int LPAREN=44;
    public static final int NOT_NULL=24;
    public static final int GREATER_OR_EQ=63;
    public static final int DOUBLE_PIPE=73;
    public static final int AT=94;
    public static final int AS=79;
    public static final int SLASH=71;
    public static final int THEN=84;
    public static final int OFFSET=114;
    public static final int REPLACE=109;
    public static final int LEFT=125;
    public static final int COLUMN=174;
    public static final int PLUS=68;
    public static final int PIPE=67;
    public static final int EXISTS=154;
    public static final int LIKE=56;
    public static final int COLLATE=75;
    public static final int ADD=173;
    public static final int INTEGER=85;
    public static final int OUTER=126;
    public static final int BY=38;
    public static final int DEFERRABLE=167;
    public static final int TO=145;
    public static final int AMPERSAND=66;
    public static final int SET=137;
    public static final int HAVING=123;
    public static final int MINUS=69;
    public static final int IGNORE=96;
    public static final int SEMI=35;
    public static final int UNION=115;
    public static final int COLUMN_CONSTRAINT=8;
    public static final int COLON=93;
    public static final int FLOAT_EXP=215;
    public static final int COLUMNS=10;
    public static final int COMMIT=144;
    public static final int SCHEMA=185;
    public static final int IN_TABLE=21;
    public static final int DATABASE=104;
    public static final int VACUUM=108;
    public static final int DROP=169;
    public static final int DETACH=105;
    public static final int WHEN=83;
    public static final int NATURAL=124;
    public static final int BETWEEN=51;
    public static final int OPTIONS=25;
    public static final int STRING=87;
    public static final int CAST=78;
    public static final int TABLE_CONSTRAINT=29;
    public static final int CURRENT_TIME=89;
    public static final int ID_LITERAL=19;
    public static final int TRIGGER=177;
    public static final int CASE=80;
    public static final int EQUALS=52;
    public static final int CASCADE=165;
    public static final int RELEASE=147;
    public static final int EXPLAIN=32;
    public static final int GREATER=62;
    public static final int ESCAPE=42;
    public static final int INSERT=132;
    public static final int SAVEPOINT=146;
    public static final int LESS=60;
    public static final int RAISE=95;
    public static final int EACH=182;
    public static final int COMMENT=216;
    public static final int ABORT=98;
    public static final int SELECT=119;
    public static final int INTO=133;
    public static final int UNIQUE=161;
    public static final int GLOB=57;
    public static final int VIEW=175;
    public static final int LINE_COMMENT=217;
    public static final int NULL=50;
    public static final int ON=130;
    public static final int MATCH=59;
    public static final int PRIMARY=156;
    public static final int DELETE=138;
    public static final int OF=181;
    public static final int SHIFT_LEFT=64;
    public static final int SHIFT_RIGHT=65;
    public static final int META_COMMENT=184;
    public static final int INTEGER_LITERAL=22;
    public static final int FUNCTION_EXPRESSION=18;
    public static final int OR=40;
    public static final int QUERY=33;
    public static final int CHECK=162;
    public static final int FROM=120;
    public static final int DISTINCT=77;
    public static final int TEMPORARY=152;
    public static final int CURRENT_DATE=90;
    public static final int DOLLAR=186;
    public static final int CONSTRAINTS=11;
    public static final int WHERE=121;
    public static final int COLUMN_EXPRESSION=9;
    public static final int INNER=127;
    public static final int ORDER=112;
    public static final int LIMIT=113;
    public static final int PRAGMA=102;
    public static final int MAX=100;
    public static final int UPDATE=136;
    public static final int DEFERRED=140;
    public static final int FOR=159;
    public static final int SELECT_CORE=27;
    public static final int EXCLUSIVE=142;
    public static final int ID=76;
    public static final int AND=41;
    public static final int CROSS=128;
    public static final int IF=153;
    public static final int INDEX=176;
    public static final int TILDA=74;
    public static final int IN=43;
    public static final int COMMA=45;
    public static final int CREATE_TABLE=13;
    public static final int REFERENCES=164;
    public static final int IS=49;
    public static final int ALL=116;
    public static final int DOT=36;
    public static final int CURRENT_TIMESTAMP=91;
    public static final int INITIALLY=168;
    public static final int REINDEX=107;
    public static final int BYTE=101;
    public static final int EQUALS2=53;
    public static final int PERCENT=72;
    public static final int AUTOINCREMENT=158;
    public static final int NOT_EQUALS2=55;
    public static final int DEFAULT=135;
    public static final int VALUES=134;
    public static final int BEFORE=178;
    public static final int AFTER=179;
    public static final int INSTEAD=180;
    public static final int JOIN=129;
    public static final int FLOAT_LITERAL=16;
    public static final int INDEXED=37;
    public static final int CREATE_INDEX=12;
    public static final int QUESTION=92;
    public static final int ORDERING=26;
    public static final int ESCAPE_SEQ=214;
    public static final int IMMEDIATE=141;
    public static final int DESC=111;
    public static final int DROP_TABLE=15;
    public static final int ID_START=213;

    // delegates
    // delegators


        public SqlParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public SqlParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        
    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    public String[] getTokenNames() { return SqlParser.tokenNames; }
    public String getGrammarFileName() { return "/home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g"; }



    // Disable error recovery.
    protected Object recoverFromMismatchedToken(IntStream input, int ttype, BitSet follow) throws RecognitionException {
        throw new MismatchedTokenException(ttype, input);
    }

    // Delegate error reporting to caller.
    public void displayRecognitionError(String[] tokenNames, RecognitionException e) {
        String hdr = getErrorHeader(e);
        String msg = getErrorMessage(e, tokenNames);
    	throw new RuntimeException("[" + hdr + "] " + msg, e);
    }

    protected SchemaBuilder builder = new SchemaBuilder();

    public ArrayList<SchemaElement> getSchemaObjects()
    {
        return builder.getSchemaObjects();
    }



    public static class sql_stmt_list_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "sql_stmt_list"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:98:1: sql_stmt_list : ( sql_stmt )+ ;
    public final SqlParser.sql_stmt_list_return sql_stmt_list() throws RecognitionException {
        SqlParser.sql_stmt_list_return retval = new SqlParser.sql_stmt_list_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        SqlParser.sql_stmt_return sql_stmt1 = null;



        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:98:14: ( ( sql_stmt )+ )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:98:16: ( sql_stmt )+
            {
            root_0 = (Object)adaptor.nil();

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:98:16: ( sql_stmt )+
            int cnt1=0;
            loop1:
            do {
                int alt1=2;
                alt1 = dfa1.predict(input);
                switch (alt1) {
            	case 1 :
            	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:98:17: sql_stmt
            	    {
            	    pushFollow(FOLLOW_sql_stmt_in_sql_stmt_list190);
            	    sql_stmt1=sql_stmt();

            	    state._fsp--;

            	    adaptor.addChild(root_0, sql_stmt1.getTree());

            	    }
            	    break;

            	default :
            	    if ( cnt1 >= 1 ) break loop1;
                        EarlyExitException eee =
                            new EarlyExitException(1, input);
                        throw eee;
                }
                cnt1++;
            } while (true);


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "sql_stmt_list"

    public static class sql_stmt_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "sql_stmt"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:100:1: sql_stmt : ( EXPLAIN ( QUERY PLAN )? )? sql_stmt_core SEMI ;
    public final SqlParser.sql_stmt_return sql_stmt() throws RecognitionException {
        SqlParser.sql_stmt_return retval = new SqlParser.sql_stmt_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token EXPLAIN2=null;
        Token QUERY3=null;
        Token PLAN4=null;
        Token SEMI6=null;
        SqlParser.sql_stmt_core_return sql_stmt_core5 = null;


        Object EXPLAIN2_tree=null;
        Object QUERY3_tree=null;
        Object PLAN4_tree=null;
        Object SEMI6_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:100:9: ( ( EXPLAIN ( QUERY PLAN )? )? sql_stmt_core SEMI )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:100:11: ( EXPLAIN ( QUERY PLAN )? )? sql_stmt_core SEMI
            {
            root_0 = (Object)adaptor.nil();

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:100:11: ( EXPLAIN ( QUERY PLAN )? )?
            int alt3=2;
            alt3 = dfa3.predict(input);
            switch (alt3) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:100:12: EXPLAIN ( QUERY PLAN )?
                    {
                    EXPLAIN2=(Token)match(input,EXPLAIN,FOLLOW_EXPLAIN_in_sql_stmt200); 
                    EXPLAIN2_tree = (Object)adaptor.create(EXPLAIN2);
                    adaptor.addChild(root_0, EXPLAIN2_tree);

                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:100:20: ( QUERY PLAN )?
                    int alt2=2;
                    alt2 = dfa2.predict(input);
                    switch (alt2) {
                        case 1 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:100:21: QUERY PLAN
                            {
                            QUERY3=(Token)match(input,QUERY,FOLLOW_QUERY_in_sql_stmt203); 
                            QUERY3_tree = (Object)adaptor.create(QUERY3);
                            adaptor.addChild(root_0, QUERY3_tree);

                            PLAN4=(Token)match(input,PLAN,FOLLOW_PLAN_in_sql_stmt205); 
                            PLAN4_tree = (Object)adaptor.create(PLAN4);
                            adaptor.addChild(root_0, PLAN4_tree);


                            }
                            break;

                    }


                    }
                    break;

            }

            pushFollow(FOLLOW_sql_stmt_core_in_sql_stmt211);
            sql_stmt_core5=sql_stmt_core();

            state._fsp--;

            adaptor.addChild(root_0, sql_stmt_core5.getTree());
            SEMI6=(Token)match(input,SEMI,FOLLOW_SEMI_in_sql_stmt213); 

            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "sql_stmt"

    public static class sql_stmt_core_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "sql_stmt_core"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:102:1: sql_stmt_core : ( pragma_stmt | attach_stmt | detach_stmt | analyze_stmt | reindex_stmt | vacuum_stmt | select_stmt | insert_stmt | update_stmt | delete_stmt | begin_stmt | commit_stmt | rollback_stmt | savepoint_stmt | release_stmt | create_virtual_table_stmt | create_table_stmt | drop_table_stmt | alter_table_stmt | create_view_stmt | drop_view_stmt | create_index_stmt | drop_index_stmt | create_trigger_stmt | drop_trigger_stmt | table_comment | col_comment );
    public final SqlParser.sql_stmt_core_return sql_stmt_core() throws RecognitionException {
        SqlParser.sql_stmt_core_return retval = new SqlParser.sql_stmt_core_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        SqlParser.pragma_stmt_return pragma_stmt7 = null;

        SqlParser.attach_stmt_return attach_stmt8 = null;

        SqlParser.detach_stmt_return detach_stmt9 = null;

        SqlParser.analyze_stmt_return analyze_stmt10 = null;

        SqlParser.reindex_stmt_return reindex_stmt11 = null;

        SqlParser.vacuum_stmt_return vacuum_stmt12 = null;

        SqlParser.select_stmt_return select_stmt13 = null;

        SqlParser.insert_stmt_return insert_stmt14 = null;

        SqlParser.update_stmt_return update_stmt15 = null;

        SqlParser.delete_stmt_return delete_stmt16 = null;

        SqlParser.begin_stmt_return begin_stmt17 = null;

        SqlParser.commit_stmt_return commit_stmt18 = null;

        SqlParser.rollback_stmt_return rollback_stmt19 = null;

        SqlParser.savepoint_stmt_return savepoint_stmt20 = null;

        SqlParser.release_stmt_return release_stmt21 = null;

        SqlParser.create_virtual_table_stmt_return create_virtual_table_stmt22 = null;

        SqlParser.create_table_stmt_return create_table_stmt23 = null;

        SqlParser.drop_table_stmt_return drop_table_stmt24 = null;

        SqlParser.alter_table_stmt_return alter_table_stmt25 = null;

        SqlParser.create_view_stmt_return create_view_stmt26 = null;

        SqlParser.drop_view_stmt_return drop_view_stmt27 = null;

        SqlParser.create_index_stmt_return create_index_stmt28 = null;

        SqlParser.drop_index_stmt_return drop_index_stmt29 = null;

        SqlParser.create_trigger_stmt_return create_trigger_stmt30 = null;

        SqlParser.drop_trigger_stmt_return drop_trigger_stmt31 = null;

        SqlParser.table_comment_return table_comment32 = null;

        SqlParser.col_comment_return col_comment33 = null;



        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:103:3: ( pragma_stmt | attach_stmt | detach_stmt | analyze_stmt | reindex_stmt | vacuum_stmt | select_stmt | insert_stmt | update_stmt | delete_stmt | begin_stmt | commit_stmt | rollback_stmt | savepoint_stmt | release_stmt | create_virtual_table_stmt | create_table_stmt | drop_table_stmt | alter_table_stmt | create_view_stmt | drop_view_stmt | create_index_stmt | drop_index_stmt | create_trigger_stmt | drop_trigger_stmt | table_comment | col_comment )
            int alt4=27;
            alt4 = dfa4.predict(input);
            switch (alt4) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:103:5: pragma_stmt
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_pragma_stmt_in_sql_stmt_core224);
                    pragma_stmt7=pragma_stmt();

                    state._fsp--;

                    adaptor.addChild(root_0, pragma_stmt7.getTree());

                    }
                    break;
                case 2 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:104:5: attach_stmt
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_attach_stmt_in_sql_stmt_core230);
                    attach_stmt8=attach_stmt();

                    state._fsp--;

                    adaptor.addChild(root_0, attach_stmt8.getTree());

                    }
                    break;
                case 3 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:105:5: detach_stmt
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_detach_stmt_in_sql_stmt_core236);
                    detach_stmt9=detach_stmt();

                    state._fsp--;

                    adaptor.addChild(root_0, detach_stmt9.getTree());

                    }
                    break;
                case 4 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:106:5: analyze_stmt
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_analyze_stmt_in_sql_stmt_core242);
                    analyze_stmt10=analyze_stmt();

                    state._fsp--;

                    adaptor.addChild(root_0, analyze_stmt10.getTree());

                    }
                    break;
                case 5 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:107:5: reindex_stmt
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_reindex_stmt_in_sql_stmt_core248);
                    reindex_stmt11=reindex_stmt();

                    state._fsp--;

                    adaptor.addChild(root_0, reindex_stmt11.getTree());

                    }
                    break;
                case 6 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:108:5: vacuum_stmt
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_vacuum_stmt_in_sql_stmt_core254);
                    vacuum_stmt12=vacuum_stmt();

                    state._fsp--;

                    adaptor.addChild(root_0, vacuum_stmt12.getTree());

                    }
                    break;
                case 7 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:110:5: select_stmt
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_select_stmt_in_sql_stmt_core261);
                    select_stmt13=select_stmt();

                    state._fsp--;

                    adaptor.addChild(root_0, select_stmt13.getTree());

                    }
                    break;
                case 8 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:111:5: insert_stmt
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_insert_stmt_in_sql_stmt_core267);
                    insert_stmt14=insert_stmt();

                    state._fsp--;

                    adaptor.addChild(root_0, insert_stmt14.getTree());

                    }
                    break;
                case 9 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:112:5: update_stmt
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_update_stmt_in_sql_stmt_core273);
                    update_stmt15=update_stmt();

                    state._fsp--;

                    adaptor.addChild(root_0, update_stmt15.getTree());

                    }
                    break;
                case 10 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:113:5: delete_stmt
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_delete_stmt_in_sql_stmt_core279);
                    delete_stmt16=delete_stmt();

                    state._fsp--;

                    adaptor.addChild(root_0, delete_stmt16.getTree());

                    }
                    break;
                case 11 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:114:5: begin_stmt
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_begin_stmt_in_sql_stmt_core285);
                    begin_stmt17=begin_stmt();

                    state._fsp--;

                    adaptor.addChild(root_0, begin_stmt17.getTree());

                    }
                    break;
                case 12 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:115:5: commit_stmt
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_commit_stmt_in_sql_stmt_core291);
                    commit_stmt18=commit_stmt();

                    state._fsp--;

                    adaptor.addChild(root_0, commit_stmt18.getTree());

                    }
                    break;
                case 13 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:116:5: rollback_stmt
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_rollback_stmt_in_sql_stmt_core297);
                    rollback_stmt19=rollback_stmt();

                    state._fsp--;

                    adaptor.addChild(root_0, rollback_stmt19.getTree());

                    }
                    break;
                case 14 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:117:5: savepoint_stmt
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_savepoint_stmt_in_sql_stmt_core303);
                    savepoint_stmt20=savepoint_stmt();

                    state._fsp--;

                    adaptor.addChild(root_0, savepoint_stmt20.getTree());

                    }
                    break;
                case 15 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:118:5: release_stmt
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_release_stmt_in_sql_stmt_core309);
                    release_stmt21=release_stmt();

                    state._fsp--;

                    adaptor.addChild(root_0, release_stmt21.getTree());

                    }
                    break;
                case 16 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:120:5: create_virtual_table_stmt
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_create_virtual_table_stmt_in_sql_stmt_core316);
                    create_virtual_table_stmt22=create_virtual_table_stmt();

                    state._fsp--;

                    adaptor.addChild(root_0, create_virtual_table_stmt22.getTree());

                    }
                    break;
                case 17 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:121:5: create_table_stmt
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_create_table_stmt_in_sql_stmt_core322);
                    create_table_stmt23=create_table_stmt();

                    state._fsp--;

                    adaptor.addChild(root_0, create_table_stmt23.getTree());

                    }
                    break;
                case 18 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:122:5: drop_table_stmt
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_drop_table_stmt_in_sql_stmt_core328);
                    drop_table_stmt24=drop_table_stmt();

                    state._fsp--;

                    adaptor.addChild(root_0, drop_table_stmt24.getTree());

                    }
                    break;
                case 19 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:123:5: alter_table_stmt
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_alter_table_stmt_in_sql_stmt_core334);
                    alter_table_stmt25=alter_table_stmt();

                    state._fsp--;

                    adaptor.addChild(root_0, alter_table_stmt25.getTree());

                    }
                    break;
                case 20 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:124:5: create_view_stmt
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_create_view_stmt_in_sql_stmt_core340);
                    create_view_stmt26=create_view_stmt();

                    state._fsp--;

                    adaptor.addChild(root_0, create_view_stmt26.getTree());

                    }
                    break;
                case 21 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:125:5: drop_view_stmt
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_drop_view_stmt_in_sql_stmt_core346);
                    drop_view_stmt27=drop_view_stmt();

                    state._fsp--;

                    adaptor.addChild(root_0, drop_view_stmt27.getTree());

                    }
                    break;
                case 22 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:126:5: create_index_stmt
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_create_index_stmt_in_sql_stmt_core352);
                    create_index_stmt28=create_index_stmt();

                    state._fsp--;

                    adaptor.addChild(root_0, create_index_stmt28.getTree());

                    }
                    break;
                case 23 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:127:5: drop_index_stmt
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_drop_index_stmt_in_sql_stmt_core358);
                    drop_index_stmt29=drop_index_stmt();

                    state._fsp--;

                    adaptor.addChild(root_0, drop_index_stmt29.getTree());

                    }
                    break;
                case 24 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:128:5: create_trigger_stmt
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_create_trigger_stmt_in_sql_stmt_core364);
                    create_trigger_stmt30=create_trigger_stmt();

                    state._fsp--;

                    adaptor.addChild(root_0, create_trigger_stmt30.getTree());

                    }
                    break;
                case 25 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:129:5: drop_trigger_stmt
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_drop_trigger_stmt_in_sql_stmt_core370);
                    drop_trigger_stmt31=drop_trigger_stmt();

                    state._fsp--;

                    adaptor.addChild(root_0, drop_trigger_stmt31.getTree());

                    }
                    break;
                case 26 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:130:5: table_comment
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_table_comment_in_sql_stmt_core376);
                    table_comment32=table_comment();

                    state._fsp--;

                    adaptor.addChild(root_0, table_comment32.getTree());

                    }
                    break;
                case 27 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:131:5: col_comment
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_col_comment_in_sql_stmt_core382);
                    col_comment33=col_comment();

                    state._fsp--;

                    adaptor.addChild(root_0, col_comment33.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "sql_stmt_core"

    public static class qualified_table_name_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "qualified_table_name"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:134:1: qualified_table_name : (database_name= id DOT )? table_name= id ( INDEXED BY index_name= id | NOT INDEXED )? ;
    public final SqlParser.qualified_table_name_return qualified_table_name() throws RecognitionException {
        SqlParser.qualified_table_name_return retval = new SqlParser.qualified_table_name_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token DOT34=null;
        Token INDEXED35=null;
        Token BY36=null;
        Token NOT37=null;
        Token INDEXED38=null;
        SqlParser.id_return database_name = null;

        SqlParser.id_return table_name = null;

        SqlParser.id_return index_name = null;


        Object DOT34_tree=null;
        Object INDEXED35_tree=null;
        Object BY36_tree=null;
        Object NOT37_tree=null;
        Object INDEXED38_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:134:21: ( (database_name= id DOT )? table_name= id ( INDEXED BY index_name= id | NOT INDEXED )? )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:134:23: (database_name= id DOT )? table_name= id ( INDEXED BY index_name= id | NOT INDEXED )?
            {
            root_0 = (Object)adaptor.nil();

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:134:23: (database_name= id DOT )?
            int alt5=2;
            alt5 = dfa5.predict(input);
            switch (alt5) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:134:24: database_name= id DOT
                    {
                    pushFollow(FOLLOW_id_in_qualified_table_name395);
                    database_name=id();

                    state._fsp--;

                    adaptor.addChild(root_0, database_name.getTree());
                    DOT34=(Token)match(input,DOT,FOLLOW_DOT_in_qualified_table_name397); 
                    DOT34_tree = (Object)adaptor.create(DOT34);
                    adaptor.addChild(root_0, DOT34_tree);


                    }
                    break;

            }

            pushFollow(FOLLOW_id_in_qualified_table_name403);
            table_name=id();

            state._fsp--;

            adaptor.addChild(root_0, table_name.getTree());
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:134:61: ( INDEXED BY index_name= id | NOT INDEXED )?
            int alt6=3;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==INDEXED) ) {
                alt6=1;
            }
            else if ( (LA6_0==NOT) ) {
                alt6=2;
            }
            switch (alt6) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:134:62: INDEXED BY index_name= id
                    {
                    INDEXED35=(Token)match(input,INDEXED,FOLLOW_INDEXED_in_qualified_table_name406); 
                    INDEXED35_tree = (Object)adaptor.create(INDEXED35);
                    adaptor.addChild(root_0, INDEXED35_tree);

                    BY36=(Token)match(input,BY,FOLLOW_BY_in_qualified_table_name408); 
                    BY36_tree = (Object)adaptor.create(BY36);
                    adaptor.addChild(root_0, BY36_tree);

                    pushFollow(FOLLOW_id_in_qualified_table_name412);
                    index_name=id();

                    state._fsp--;

                    adaptor.addChild(root_0, index_name.getTree());

                    }
                    break;
                case 2 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:134:89: NOT INDEXED
                    {
                    NOT37=(Token)match(input,NOT,FOLLOW_NOT_in_qualified_table_name416); 
                    NOT37_tree = (Object)adaptor.create(NOT37);
                    adaptor.addChild(root_0, NOT37_tree);

                    INDEXED38=(Token)match(input,INDEXED,FOLLOW_INDEXED_in_qualified_table_name418); 
                    INDEXED38_tree = (Object)adaptor.create(INDEXED38);
                    adaptor.addChild(root_0, INDEXED38_tree);


                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "qualified_table_name"

    public static class expr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "expr"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:136:1: expr : or_subexpr ( OR or_subexpr )* ;
    public final SqlParser.expr_return expr() throws RecognitionException {
        SqlParser.expr_return retval = new SqlParser.expr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token OR40=null;
        SqlParser.or_subexpr_return or_subexpr39 = null;

        SqlParser.or_subexpr_return or_subexpr41 = null;


        Object OR40_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:136:5: ( or_subexpr ( OR or_subexpr )* )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:136:7: or_subexpr ( OR or_subexpr )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_or_subexpr_in_expr427);
            or_subexpr39=or_subexpr();

            state._fsp--;

            adaptor.addChild(root_0, or_subexpr39.getTree());
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:136:18: ( OR or_subexpr )*
            loop7:
            do {
                int alt7=2;
                alt7 = dfa7.predict(input);
                switch (alt7) {
            	case 1 :
            	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:136:19: OR or_subexpr
            	    {
            	    OR40=(Token)match(input,OR,FOLLOW_OR_in_expr430); 
            	    OR40_tree = (Object)adaptor.create(OR40);
            	    root_0 = (Object)adaptor.becomeRoot(OR40_tree, root_0);

            	    pushFollow(FOLLOW_or_subexpr_in_expr433);
            	    or_subexpr41=or_subexpr();

            	    state._fsp--;

            	    adaptor.addChild(root_0, or_subexpr41.getTree());

            	    }
            	    break;

            	default :
            	    break loop7;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "expr"

    public static class or_subexpr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "or_subexpr"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:138:1: or_subexpr : and_subexpr ( AND and_subexpr )* ;
    public final SqlParser.or_subexpr_return or_subexpr() throws RecognitionException {
        SqlParser.or_subexpr_return retval = new SqlParser.or_subexpr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token AND43=null;
        SqlParser.and_subexpr_return and_subexpr42 = null;

        SqlParser.and_subexpr_return and_subexpr44 = null;


        Object AND43_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:138:11: ( and_subexpr ( AND and_subexpr )* )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:138:13: and_subexpr ( AND and_subexpr )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_and_subexpr_in_or_subexpr442);
            and_subexpr42=and_subexpr();

            state._fsp--;

            adaptor.addChild(root_0, and_subexpr42.getTree());
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:138:25: ( AND and_subexpr )*
            loop8:
            do {
                int alt8=2;
                alt8 = dfa8.predict(input);
                switch (alt8) {
            	case 1 :
            	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:138:26: AND and_subexpr
            	    {
            	    AND43=(Token)match(input,AND,FOLLOW_AND_in_or_subexpr445); 
            	    AND43_tree = (Object)adaptor.create(AND43);
            	    root_0 = (Object)adaptor.becomeRoot(AND43_tree, root_0);

            	    pushFollow(FOLLOW_and_subexpr_in_or_subexpr448);
            	    and_subexpr44=and_subexpr();

            	    state._fsp--;

            	    adaptor.addChild(root_0, and_subexpr44.getTree());

            	    }
            	    break;

            	default :
            	    break loop8;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "or_subexpr"

    public static class and_subexpr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "and_subexpr"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:140:1: and_subexpr : eq_subexpr ( cond_expr )? ;
    public final SqlParser.and_subexpr_return and_subexpr() throws RecognitionException {
        SqlParser.and_subexpr_return retval = new SqlParser.and_subexpr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        SqlParser.eq_subexpr_return eq_subexpr45 = null;

        SqlParser.cond_expr_return cond_expr46 = null;



        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:140:12: ( eq_subexpr ( cond_expr )? )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:140:14: eq_subexpr ( cond_expr )?
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_eq_subexpr_in_and_subexpr457);
            eq_subexpr45=eq_subexpr();

            state._fsp--;

            adaptor.addChild(root_0, eq_subexpr45.getTree());
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:140:34: ( cond_expr )?
            int alt9=2;
            alt9 = dfa9.predict(input);
            switch (alt9) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:140:34: cond_expr
                    {
                    pushFollow(FOLLOW_cond_expr_in_and_subexpr459);
                    cond_expr46=cond_expr();

                    state._fsp--;

                    root_0 = (Object)adaptor.becomeRoot(cond_expr46.getTree(), root_0);

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "and_subexpr"

    public static class cond_expr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "cond_expr"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:142:1: cond_expr : ( ( NOT )? match_op match_expr= eq_subexpr ( ESCAPE escape_expr= eq_subexpr )? -> ^( match_op $match_expr ( NOT )? ( ^( ESCAPE $escape_expr) )? ) | ( NOT )? IN LPAREN expr ( COMMA expr )* RPAREN -> ^( IN_VALUES ( NOT )? ^( IN ( expr )+ ) ) | ( NOT )? IN (database_name= id DOT )? table_name= id -> ^( IN_TABLE ( NOT )? ^( IN ^( $table_name ( $database_name)? ) ) ) | ( ISNULL -> IS_NULL | NOTNULL -> NOT_NULL | IS NULL -> IS_NULL | NOT NULL -> NOT_NULL | IS NOT NULL -> NOT_NULL ) | ( NOT )? BETWEEN e1= eq_subexpr AND e2= eq_subexpr -> ^( BETWEEN ( NOT )? ^( AND $e1 $e2) ) | ( ( EQUALS | EQUALS2 | NOT_EQUALS | NOT_EQUALS2 ) eq_subexpr )+ );
    public final SqlParser.cond_expr_return cond_expr() throws RecognitionException {
        SqlParser.cond_expr_return retval = new SqlParser.cond_expr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token NOT47=null;
        Token ESCAPE49=null;
        Token NOT50=null;
        Token IN51=null;
        Token LPAREN52=null;
        Token COMMA54=null;
        Token RPAREN56=null;
        Token NOT57=null;
        Token IN58=null;
        Token DOT59=null;
        Token ISNULL60=null;
        Token NOTNULL61=null;
        Token IS62=null;
        Token NULL63=null;
        Token NOT64=null;
        Token NULL65=null;
        Token IS66=null;
        Token NOT67=null;
        Token NULL68=null;
        Token NOT69=null;
        Token BETWEEN70=null;
        Token AND71=null;
        Token set72=null;
        SqlParser.eq_subexpr_return match_expr = null;

        SqlParser.eq_subexpr_return escape_expr = null;

        SqlParser.id_return database_name = null;

        SqlParser.id_return table_name = null;

        SqlParser.eq_subexpr_return e1 = null;

        SqlParser.eq_subexpr_return e2 = null;

        SqlParser.match_op_return match_op48 = null;

        SqlParser.expr_return expr53 = null;

        SqlParser.expr_return expr55 = null;

        SqlParser.eq_subexpr_return eq_subexpr73 = null;


        Object NOT47_tree=null;
        Object ESCAPE49_tree=null;
        Object NOT50_tree=null;
        Object IN51_tree=null;
        Object LPAREN52_tree=null;
        Object COMMA54_tree=null;
        Object RPAREN56_tree=null;
        Object NOT57_tree=null;
        Object IN58_tree=null;
        Object DOT59_tree=null;
        Object ISNULL60_tree=null;
        Object NOTNULL61_tree=null;
        Object IS62_tree=null;
        Object NULL63_tree=null;
        Object NOT64_tree=null;
        Object NULL65_tree=null;
        Object IS66_tree=null;
        Object NOT67_tree=null;
        Object NULL68_tree=null;
        Object NOT69_tree=null;
        Object BETWEEN70_tree=null;
        Object AND71_tree=null;
        Object set72_tree=null;
        RewriteRuleTokenStream stream_RPAREN=new RewriteRuleTokenStream(adaptor,"token RPAREN");
        RewriteRuleTokenStream stream_IN=new RewriteRuleTokenStream(adaptor,"token IN");
        RewriteRuleTokenStream stream_ESCAPE=new RewriteRuleTokenStream(adaptor,"token ESCAPE");
        RewriteRuleTokenStream stream_COMMA=new RewriteRuleTokenStream(adaptor,"token COMMA");
        RewriteRuleTokenStream stream_IS=new RewriteRuleTokenStream(adaptor,"token IS");
        RewriteRuleTokenStream stream_NULL=new RewriteRuleTokenStream(adaptor,"token NULL");
        RewriteRuleTokenStream stream_ISNULL=new RewriteRuleTokenStream(adaptor,"token ISNULL");
        RewriteRuleTokenStream stream_NOT=new RewriteRuleTokenStream(adaptor,"token NOT");
        RewriteRuleTokenStream stream_DOT=new RewriteRuleTokenStream(adaptor,"token DOT");
        RewriteRuleTokenStream stream_NOTNULL=new RewriteRuleTokenStream(adaptor,"token NOTNULL");
        RewriteRuleTokenStream stream_AND=new RewriteRuleTokenStream(adaptor,"token AND");
        RewriteRuleTokenStream stream_BETWEEN=new RewriteRuleTokenStream(adaptor,"token BETWEEN");
        RewriteRuleTokenStream stream_LPAREN=new RewriteRuleTokenStream(adaptor,"token LPAREN");
        RewriteRuleSubtreeStream stream_id=new RewriteRuleSubtreeStream(adaptor,"rule id");
        RewriteRuleSubtreeStream stream_expr=new RewriteRuleSubtreeStream(adaptor,"rule expr");
        RewriteRuleSubtreeStream stream_match_op=new RewriteRuleSubtreeStream(adaptor,"rule match_op");
        RewriteRuleSubtreeStream stream_eq_subexpr=new RewriteRuleSubtreeStream(adaptor,"rule eq_subexpr");
        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:143:3: ( ( NOT )? match_op match_expr= eq_subexpr ( ESCAPE escape_expr= eq_subexpr )? -> ^( match_op $match_expr ( NOT )? ( ^( ESCAPE $escape_expr) )? ) | ( NOT )? IN LPAREN expr ( COMMA expr )* RPAREN -> ^( IN_VALUES ( NOT )? ^( IN ( expr )+ ) ) | ( NOT )? IN (database_name= id DOT )? table_name= id -> ^( IN_TABLE ( NOT )? ^( IN ^( $table_name ( $database_name)? ) ) ) | ( ISNULL -> IS_NULL | NOTNULL -> NOT_NULL | IS NULL -> IS_NULL | NOT NULL -> NOT_NULL | IS NOT NULL -> NOT_NULL ) | ( NOT )? BETWEEN e1= eq_subexpr AND e2= eq_subexpr -> ^( BETWEEN ( NOT )? ^( AND $e1 $e2) ) | ( ( EQUALS | EQUALS2 | NOT_EQUALS | NOT_EQUALS2 ) eq_subexpr )+ )
            int alt19=6;
            alt19 = dfa19.predict(input);
            switch (alt19) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:143:5: ( NOT )? match_op match_expr= eq_subexpr ( ESCAPE escape_expr= eq_subexpr )?
                    {
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:143:5: ( NOT )?
                    int alt10=2;
                    int LA10_0 = input.LA(1);

                    if ( (LA10_0==NOT) ) {
                        alt10=1;
                    }
                    switch (alt10) {
                        case 1 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:143:5: NOT
                            {
                            NOT47=(Token)match(input,NOT,FOLLOW_NOT_in_cond_expr471);  
                            stream_NOT.add(NOT47);


                            }
                            break;

                    }

                    pushFollow(FOLLOW_match_op_in_cond_expr474);
                    match_op48=match_op();

                    state._fsp--;

                    stream_match_op.add(match_op48.getTree());
                    pushFollow(FOLLOW_eq_subexpr_in_cond_expr478);
                    match_expr=eq_subexpr();

                    state._fsp--;

                    stream_eq_subexpr.add(match_expr.getTree());
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:143:41: ( ESCAPE escape_expr= eq_subexpr )?
                    int alt11=2;
                    alt11 = dfa11.predict(input);
                    switch (alt11) {
                        case 1 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:143:42: ESCAPE escape_expr= eq_subexpr
                            {
                            ESCAPE49=(Token)match(input,ESCAPE,FOLLOW_ESCAPE_in_cond_expr481);  
                            stream_ESCAPE.add(ESCAPE49);

                            pushFollow(FOLLOW_eq_subexpr_in_cond_expr485);
                            escape_expr=eq_subexpr();

                            state._fsp--;

                            stream_eq_subexpr.add(escape_expr.getTree());

                            }
                            break;

                    }



                    // AST REWRITE
                    // elements: NOT, ESCAPE, match_op, match_expr, escape_expr
                    // token labels: 
                    // rule labels: retval, match_expr, escape_expr
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                    RewriteRuleSubtreeStream stream_match_expr=new RewriteRuleSubtreeStream(adaptor,"rule match_expr",match_expr!=null?match_expr.tree:null);
                    RewriteRuleSubtreeStream stream_escape_expr=new RewriteRuleSubtreeStream(adaptor,"rule escape_expr",escape_expr!=null?escape_expr.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 143:74: -> ^( match_op $match_expr ( NOT )? ( ^( ESCAPE $escape_expr) )? )
                    {
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:143:77: ^( match_op $match_expr ( NOT )? ( ^( ESCAPE $escape_expr) )? )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(stream_match_op.nextNode(), root_1);

                        adaptor.addChild(root_1, stream_match_expr.nextTree());
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:143:100: ( NOT )?
                        if ( stream_NOT.hasNext() ) {
                            adaptor.addChild(root_1, stream_NOT.nextNode());

                        }
                        stream_NOT.reset();
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:143:105: ( ^( ESCAPE $escape_expr) )?
                        if ( stream_ESCAPE.hasNext()||stream_escape_expr.hasNext() ) {
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:143:105: ^( ESCAPE $escape_expr)
                            {
                            Object root_2 = (Object)adaptor.nil();
                            root_2 = (Object)adaptor.becomeRoot(stream_ESCAPE.nextNode(), root_2);

                            adaptor.addChild(root_2, stream_escape_expr.nextTree());

                            adaptor.addChild(root_1, root_2);
                            }

                        }
                        stream_ESCAPE.reset();
                        stream_escape_expr.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:144:5: ( NOT )? IN LPAREN expr ( COMMA expr )* RPAREN
                    {
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:144:5: ( NOT )?
                    int alt12=2;
                    int LA12_0 = input.LA(1);

                    if ( (LA12_0==NOT) ) {
                        alt12=1;
                    }
                    switch (alt12) {
                        case 1 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:144:5: NOT
                            {
                            NOT50=(Token)match(input,NOT,FOLLOW_NOT_in_cond_expr513);  
                            stream_NOT.add(NOT50);


                            }
                            break;

                    }

                    IN51=(Token)match(input,IN,FOLLOW_IN_in_cond_expr516);  
                    stream_IN.add(IN51);

                    LPAREN52=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_cond_expr518);  
                    stream_LPAREN.add(LPAREN52);

                    pushFollow(FOLLOW_expr_in_cond_expr520);
                    expr53=expr();

                    state._fsp--;

                    stream_expr.add(expr53.getTree());
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:144:25: ( COMMA expr )*
                    loop13:
                    do {
                        int alt13=2;
                        int LA13_0 = input.LA(1);

                        if ( (LA13_0==COMMA) ) {
                            alt13=1;
                        }


                        switch (alt13) {
                    	case 1 :
                    	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:144:26: COMMA expr
                    	    {
                    	    COMMA54=(Token)match(input,COMMA,FOLLOW_COMMA_in_cond_expr523);  
                    	    stream_COMMA.add(COMMA54);

                    	    pushFollow(FOLLOW_expr_in_cond_expr525);
                    	    expr55=expr();

                    	    state._fsp--;

                    	    stream_expr.add(expr55.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop13;
                        }
                    } while (true);

                    RPAREN56=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_cond_expr529);  
                    stream_RPAREN.add(RPAREN56);



                    // AST REWRITE
                    // elements: IN, expr, NOT
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 144:46: -> ^( IN_VALUES ( NOT )? ^( IN ( expr )+ ) )
                    {
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:144:49: ^( IN_VALUES ( NOT )? ^( IN ( expr )+ ) )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(IN_VALUES, "IN_VALUES"), root_1);

                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:144:61: ( NOT )?
                        if ( stream_NOT.hasNext() ) {
                            adaptor.addChild(root_1, stream_NOT.nextNode());

                        }
                        stream_NOT.reset();
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:144:66: ^( IN ( expr )+ )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot(stream_IN.nextNode(), root_2);

                        if ( !(stream_expr.hasNext()) ) {
                            throw new RewriteEarlyExitException();
                        }
                        while ( stream_expr.hasNext() ) {
                            adaptor.addChild(root_2, stream_expr.nextTree());

                        }
                        stream_expr.reset();

                        adaptor.addChild(root_1, root_2);
                        }

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 3 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:145:5: ( NOT )? IN (database_name= id DOT )? table_name= id
                    {
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:145:5: ( NOT )?
                    int alt14=2;
                    int LA14_0 = input.LA(1);

                    if ( (LA14_0==NOT) ) {
                        alt14=1;
                    }
                    switch (alt14) {
                        case 1 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:145:5: NOT
                            {
                            NOT57=(Token)match(input,NOT,FOLLOW_NOT_in_cond_expr551);  
                            stream_NOT.add(NOT57);


                            }
                            break;

                    }

                    IN58=(Token)match(input,IN,FOLLOW_IN_in_cond_expr554);  
                    stream_IN.add(IN58);

                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:145:13: (database_name= id DOT )?
                    int alt15=2;
                    alt15 = dfa15.predict(input);
                    switch (alt15) {
                        case 1 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:145:14: database_name= id DOT
                            {
                            pushFollow(FOLLOW_id_in_cond_expr559);
                            database_name=id();

                            state._fsp--;

                            stream_id.add(database_name.getTree());
                            DOT59=(Token)match(input,DOT,FOLLOW_DOT_in_cond_expr561);  
                            stream_DOT.add(DOT59);


                            }
                            break;

                    }

                    pushFollow(FOLLOW_id_in_cond_expr567);
                    table_name=id();

                    state._fsp--;

                    stream_id.add(table_name.getTree());


                    // AST REWRITE
                    // elements: table_name, database_name, IN, NOT
                    // token labels: 
                    // rule labels: database_name, retval, table_name
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_database_name=new RewriteRuleSubtreeStream(adaptor,"rule database_name",database_name!=null?database_name.tree:null);
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                    RewriteRuleSubtreeStream stream_table_name=new RewriteRuleSubtreeStream(adaptor,"rule table_name",table_name!=null?table_name.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 145:51: -> ^( IN_TABLE ( NOT )? ^( IN ^( $table_name ( $database_name)? ) ) )
                    {
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:145:54: ^( IN_TABLE ( NOT )? ^( IN ^( $table_name ( $database_name)? ) ) )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(IN_TABLE, "IN_TABLE"), root_1);

                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:145:65: ( NOT )?
                        if ( stream_NOT.hasNext() ) {
                            adaptor.addChild(root_1, stream_NOT.nextNode());

                        }
                        stream_NOT.reset();
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:145:70: ^( IN ^( $table_name ( $database_name)? ) )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot(stream_IN.nextNode(), root_2);

                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:145:75: ^( $table_name ( $database_name)? )
                        {
                        Object root_3 = (Object)adaptor.nil();
                        root_3 = (Object)adaptor.becomeRoot(stream_table_name.nextNode(), root_3);

                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:145:89: ( $database_name)?
                        if ( stream_database_name.hasNext() ) {
                            adaptor.addChild(root_3, stream_database_name.nextTree());

                        }
                        stream_database_name.reset();

                        adaptor.addChild(root_2, root_3);
                        }

                        adaptor.addChild(root_1, root_2);
                        }

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 4 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:148:5: ( ISNULL -> IS_NULL | NOTNULL -> NOT_NULL | IS NULL -> IS_NULL | NOT NULL -> NOT_NULL | IS NOT NULL -> NOT_NULL )
                    {
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:148:5: ( ISNULL -> IS_NULL | NOTNULL -> NOT_NULL | IS NULL -> IS_NULL | NOT NULL -> NOT_NULL | IS NOT NULL -> NOT_NULL )
                    int alt16=5;
                    switch ( input.LA(1) ) {
                    case ISNULL:
                        {
                        alt16=1;
                        }
                        break;
                    case NOTNULL:
                        {
                        alt16=2;
                        }
                        break;
                    case IS:
                        {
                        int LA16_3 = input.LA(2);

                        if ( (LA16_3==NULL) ) {
                            alt16=3;
                        }
                        else if ( (LA16_3==NOT) ) {
                            alt16=5;
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 16, 3, input);

                            throw nvae;
                        }
                        }
                        break;
                    case NOT:
                        {
                        alt16=4;
                        }
                        break;
                    default:
                        NoViableAltException nvae =
                            new NoViableAltException("", 16, 0, input);

                        throw nvae;
                    }

                    switch (alt16) {
                        case 1 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:148:6: ISNULL
                            {
                            ISNULL60=(Token)match(input,ISNULL,FOLLOW_ISNULL_in_cond_expr598);  
                            stream_ISNULL.add(ISNULL60);



                            // AST REWRITE
                            // elements: 
                            // token labels: 
                            // rule labels: retval
                            // token list labels: 
                            // rule list labels: 
                            // wildcard labels: 
                            retval.tree = root_0;
                            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                            root_0 = (Object)adaptor.nil();
                            // 148:13: -> IS_NULL
                            {
                                adaptor.addChild(root_0, (Object)adaptor.create(IS_NULL, "IS_NULL"));

                            }

                            retval.tree = root_0;
                            }
                            break;
                        case 2 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:148:26: NOTNULL
                            {
                            NOTNULL61=(Token)match(input,NOTNULL,FOLLOW_NOTNULL_in_cond_expr606);  
                            stream_NOTNULL.add(NOTNULL61);



                            // AST REWRITE
                            // elements: 
                            // token labels: 
                            // rule labels: retval
                            // token list labels: 
                            // rule list labels: 
                            // wildcard labels: 
                            retval.tree = root_0;
                            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                            root_0 = (Object)adaptor.nil();
                            // 148:34: -> NOT_NULL
                            {
                                adaptor.addChild(root_0, (Object)adaptor.create(NOT_NULL, "NOT_NULL"));

                            }

                            retval.tree = root_0;
                            }
                            break;
                        case 3 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:148:48: IS NULL
                            {
                            IS62=(Token)match(input,IS,FOLLOW_IS_in_cond_expr614);  
                            stream_IS.add(IS62);

                            NULL63=(Token)match(input,NULL,FOLLOW_NULL_in_cond_expr616);  
                            stream_NULL.add(NULL63);



                            // AST REWRITE
                            // elements: 
                            // token labels: 
                            // rule labels: retval
                            // token list labels: 
                            // rule list labels: 
                            // wildcard labels: 
                            retval.tree = root_0;
                            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                            root_0 = (Object)adaptor.nil();
                            // 148:56: -> IS_NULL
                            {
                                adaptor.addChild(root_0, (Object)adaptor.create(IS_NULL, "IS_NULL"));

                            }

                            retval.tree = root_0;
                            }
                            break;
                        case 4 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:148:69: NOT NULL
                            {
                            NOT64=(Token)match(input,NOT,FOLLOW_NOT_in_cond_expr624);  
                            stream_NOT.add(NOT64);

                            NULL65=(Token)match(input,NULL,FOLLOW_NULL_in_cond_expr626);  
                            stream_NULL.add(NULL65);



                            // AST REWRITE
                            // elements: 
                            // token labels: 
                            // rule labels: retval
                            // token list labels: 
                            // rule list labels: 
                            // wildcard labels: 
                            retval.tree = root_0;
                            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                            root_0 = (Object)adaptor.nil();
                            // 148:78: -> NOT_NULL
                            {
                                adaptor.addChild(root_0, (Object)adaptor.create(NOT_NULL, "NOT_NULL"));

                            }

                            retval.tree = root_0;
                            }
                            break;
                        case 5 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:148:92: IS NOT NULL
                            {
                            IS66=(Token)match(input,IS,FOLLOW_IS_in_cond_expr634);  
                            stream_IS.add(IS66);

                            NOT67=(Token)match(input,NOT,FOLLOW_NOT_in_cond_expr636);  
                            stream_NOT.add(NOT67);

                            NULL68=(Token)match(input,NULL,FOLLOW_NULL_in_cond_expr638);  
                            stream_NULL.add(NULL68);



                            // AST REWRITE
                            // elements: 
                            // token labels: 
                            // rule labels: retval
                            // token list labels: 
                            // rule list labels: 
                            // wildcard labels: 
                            retval.tree = root_0;
                            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                            root_0 = (Object)adaptor.nil();
                            // 148:104: -> NOT_NULL
                            {
                                adaptor.addChild(root_0, (Object)adaptor.create(NOT_NULL, "NOT_NULL"));

                            }

                            retval.tree = root_0;
                            }
                            break;

                    }


                    }
                    break;
                case 5 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:149:5: ( NOT )? BETWEEN e1= eq_subexpr AND e2= eq_subexpr
                    {
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:149:5: ( NOT )?
                    int alt17=2;
                    int LA17_0 = input.LA(1);

                    if ( (LA17_0==NOT) ) {
                        alt17=1;
                    }
                    switch (alt17) {
                        case 1 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:149:5: NOT
                            {
                            NOT69=(Token)match(input,NOT,FOLLOW_NOT_in_cond_expr649);  
                            stream_NOT.add(NOT69);


                            }
                            break;

                    }

                    BETWEEN70=(Token)match(input,BETWEEN,FOLLOW_BETWEEN_in_cond_expr652);  
                    stream_BETWEEN.add(BETWEEN70);

                    pushFollow(FOLLOW_eq_subexpr_in_cond_expr656);
                    e1=eq_subexpr();

                    state._fsp--;

                    stream_eq_subexpr.add(e1.getTree());
                    AND71=(Token)match(input,AND,FOLLOW_AND_in_cond_expr658);  
                    stream_AND.add(AND71);

                    pushFollow(FOLLOW_eq_subexpr_in_cond_expr662);
                    e2=eq_subexpr();

                    state._fsp--;

                    stream_eq_subexpr.add(e2.getTree());


                    // AST REWRITE
                    // elements: AND, BETWEEN, e1, e2, NOT
                    // token labels: 
                    // rule labels: retval, e1, e2
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                    RewriteRuleSubtreeStream stream_e1=new RewriteRuleSubtreeStream(adaptor,"rule e1",e1!=null?e1.tree:null);
                    RewriteRuleSubtreeStream stream_e2=new RewriteRuleSubtreeStream(adaptor,"rule e2",e2!=null?e2.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 149:50: -> ^( BETWEEN ( NOT )? ^( AND $e1 $e2) )
                    {
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:149:53: ^( BETWEEN ( NOT )? ^( AND $e1 $e2) )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(stream_BETWEEN.nextNode(), root_1);

                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:149:63: ( NOT )?
                        if ( stream_NOT.hasNext() ) {
                            adaptor.addChild(root_1, stream_NOT.nextNode());

                        }
                        stream_NOT.reset();
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:149:68: ^( AND $e1 $e2)
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot(stream_AND.nextNode(), root_2);

                        adaptor.addChild(root_2, stream_e1.nextTree());
                        adaptor.addChild(root_2, stream_e2.nextTree());

                        adaptor.addChild(root_1, root_2);
                        }

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 6 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:150:5: ( ( EQUALS | EQUALS2 | NOT_EQUALS | NOT_EQUALS2 ) eq_subexpr )+
                    {
                    root_0 = (Object)adaptor.nil();

                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:150:5: ( ( EQUALS | EQUALS2 | NOT_EQUALS | NOT_EQUALS2 ) eq_subexpr )+
                    int cnt18=0;
                    loop18:
                    do {
                        int alt18=2;
                        alt18 = dfa18.predict(input);
                        switch (alt18) {
                    	case 1 :
                    	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:150:6: ( EQUALS | EQUALS2 | NOT_EQUALS | NOT_EQUALS2 ) eq_subexpr
                    	    {
                    	    set72=(Token)input.LT(1);
                    	    set72=(Token)input.LT(1);
                    	    if ( (input.LA(1)>=EQUALS && input.LA(1)<=NOT_EQUALS2) ) {
                    	        input.consume();
                    	        root_0 = (Object)adaptor.becomeRoot((Object)adaptor.create(set72), root_0);
                    	        state.errorRecovery=false;
                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        throw mse;
                    	    }

                    	    pushFollow(FOLLOW_eq_subexpr_in_cond_expr705);
                    	    eq_subexpr73=eq_subexpr();

                    	    state._fsp--;

                    	    adaptor.addChild(root_0, eq_subexpr73.getTree());

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt18 >= 1 ) break loop18;
                                EarlyExitException eee =
                                    new EarlyExitException(18, input);
                                throw eee;
                        }
                        cnt18++;
                    } while (true);


                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "cond_expr"

    public static class match_op_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "match_op"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:153:1: match_op : ( LIKE | GLOB | REGEXP | MATCH );
    public final SqlParser.match_op_return match_op() throws RecognitionException {
        SqlParser.match_op_return retval = new SqlParser.match_op_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set74=null;

        Object set74_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:153:9: ( LIKE | GLOB | REGEXP | MATCH )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:
            {
            root_0 = (Object)adaptor.nil();

            set74=(Token)input.LT(1);
            if ( (input.LA(1)>=LIKE && input.LA(1)<=MATCH) ) {
                input.consume();
                adaptor.addChild(root_0, (Object)adaptor.create(set74));
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "match_op"

    public static class eq_subexpr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "eq_subexpr"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:155:1: eq_subexpr : neq_subexpr ( ( LESS | LESS_OR_EQ | GREATER | GREATER_OR_EQ ) neq_subexpr )* ;
    public final SqlParser.eq_subexpr_return eq_subexpr() throws RecognitionException {
        SqlParser.eq_subexpr_return retval = new SqlParser.eq_subexpr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set76=null;
        SqlParser.neq_subexpr_return neq_subexpr75 = null;

        SqlParser.neq_subexpr_return neq_subexpr77 = null;


        Object set76_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:155:11: ( neq_subexpr ( ( LESS | LESS_OR_EQ | GREATER | GREATER_OR_EQ ) neq_subexpr )* )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:155:13: neq_subexpr ( ( LESS | LESS_OR_EQ | GREATER | GREATER_OR_EQ ) neq_subexpr )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_neq_subexpr_in_eq_subexpr738);
            neq_subexpr75=neq_subexpr();

            state._fsp--;

            adaptor.addChild(root_0, neq_subexpr75.getTree());
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:155:25: ( ( LESS | LESS_OR_EQ | GREATER | GREATER_OR_EQ ) neq_subexpr )*
            loop20:
            do {
                int alt20=2;
                alt20 = dfa20.predict(input);
                switch (alt20) {
            	case 1 :
            	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:155:26: ( LESS | LESS_OR_EQ | GREATER | GREATER_OR_EQ ) neq_subexpr
            	    {
            	    set76=(Token)input.LT(1);
            	    set76=(Token)input.LT(1);
            	    if ( (input.LA(1)>=LESS && input.LA(1)<=GREATER_OR_EQ) ) {
            	        input.consume();
            	        root_0 = (Object)adaptor.becomeRoot((Object)adaptor.create(set76), root_0);
            	        state.errorRecovery=false;
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }

            	    pushFollow(FOLLOW_neq_subexpr_in_eq_subexpr758);
            	    neq_subexpr77=neq_subexpr();

            	    state._fsp--;

            	    adaptor.addChild(root_0, neq_subexpr77.getTree());

            	    }
            	    break;

            	default :
            	    break loop20;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "eq_subexpr"

    public static class neq_subexpr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "neq_subexpr"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:157:1: neq_subexpr : bit_subexpr ( ( SHIFT_LEFT | SHIFT_RIGHT | AMPERSAND | PIPE ) bit_subexpr )* ;
    public final SqlParser.neq_subexpr_return neq_subexpr() throws RecognitionException {
        SqlParser.neq_subexpr_return retval = new SqlParser.neq_subexpr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set79=null;
        SqlParser.bit_subexpr_return bit_subexpr78 = null;

        SqlParser.bit_subexpr_return bit_subexpr80 = null;


        Object set79_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:157:12: ( bit_subexpr ( ( SHIFT_LEFT | SHIFT_RIGHT | AMPERSAND | PIPE ) bit_subexpr )* )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:157:14: bit_subexpr ( ( SHIFT_LEFT | SHIFT_RIGHT | AMPERSAND | PIPE ) bit_subexpr )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_bit_subexpr_in_neq_subexpr767);
            bit_subexpr78=bit_subexpr();

            state._fsp--;

            adaptor.addChild(root_0, bit_subexpr78.getTree());
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:157:26: ( ( SHIFT_LEFT | SHIFT_RIGHT | AMPERSAND | PIPE ) bit_subexpr )*
            loop21:
            do {
                int alt21=2;
                alt21 = dfa21.predict(input);
                switch (alt21) {
            	case 1 :
            	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:157:27: ( SHIFT_LEFT | SHIFT_RIGHT | AMPERSAND | PIPE ) bit_subexpr
            	    {
            	    set79=(Token)input.LT(1);
            	    set79=(Token)input.LT(1);
            	    if ( (input.LA(1)>=SHIFT_LEFT && input.LA(1)<=PIPE) ) {
            	        input.consume();
            	        root_0 = (Object)adaptor.becomeRoot((Object)adaptor.create(set79), root_0);
            	        state.errorRecovery=false;
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }

            	    pushFollow(FOLLOW_bit_subexpr_in_neq_subexpr787);
            	    bit_subexpr80=bit_subexpr();

            	    state._fsp--;

            	    adaptor.addChild(root_0, bit_subexpr80.getTree());

            	    }
            	    break;

            	default :
            	    break loop21;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "neq_subexpr"

    public static class bit_subexpr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "bit_subexpr"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:159:1: bit_subexpr : add_subexpr ( ( PLUS | MINUS ) add_subexpr )* ;
    public final SqlParser.bit_subexpr_return bit_subexpr() throws RecognitionException {
        SqlParser.bit_subexpr_return retval = new SqlParser.bit_subexpr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set82=null;
        SqlParser.add_subexpr_return add_subexpr81 = null;

        SqlParser.add_subexpr_return add_subexpr83 = null;


        Object set82_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:159:12: ( add_subexpr ( ( PLUS | MINUS ) add_subexpr )* )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:159:14: add_subexpr ( ( PLUS | MINUS ) add_subexpr )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_add_subexpr_in_bit_subexpr796);
            add_subexpr81=add_subexpr();

            state._fsp--;

            adaptor.addChild(root_0, add_subexpr81.getTree());
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:159:26: ( ( PLUS | MINUS ) add_subexpr )*
            loop22:
            do {
                int alt22=2;
                alt22 = dfa22.predict(input);
                switch (alt22) {
            	case 1 :
            	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:159:27: ( PLUS | MINUS ) add_subexpr
            	    {
            	    set82=(Token)input.LT(1);
            	    set82=(Token)input.LT(1);
            	    if ( (input.LA(1)>=PLUS && input.LA(1)<=MINUS) ) {
            	        input.consume();
            	        root_0 = (Object)adaptor.becomeRoot((Object)adaptor.create(set82), root_0);
            	        state.errorRecovery=false;
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }

            	    pushFollow(FOLLOW_add_subexpr_in_bit_subexpr808);
            	    add_subexpr83=add_subexpr();

            	    state._fsp--;

            	    adaptor.addChild(root_0, add_subexpr83.getTree());

            	    }
            	    break;

            	default :
            	    break loop22;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "bit_subexpr"

    public static class add_subexpr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "add_subexpr"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:161:1: add_subexpr : mul_subexpr ( ( ASTERISK | SLASH | PERCENT ) mul_subexpr )* ;
    public final SqlParser.add_subexpr_return add_subexpr() throws RecognitionException {
        SqlParser.add_subexpr_return retval = new SqlParser.add_subexpr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set85=null;
        SqlParser.mul_subexpr_return mul_subexpr84 = null;

        SqlParser.mul_subexpr_return mul_subexpr86 = null;


        Object set85_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:161:12: ( mul_subexpr ( ( ASTERISK | SLASH | PERCENT ) mul_subexpr )* )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:161:14: mul_subexpr ( ( ASTERISK | SLASH | PERCENT ) mul_subexpr )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_mul_subexpr_in_add_subexpr817);
            mul_subexpr84=mul_subexpr();

            state._fsp--;

            adaptor.addChild(root_0, mul_subexpr84.getTree());
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:161:26: ( ( ASTERISK | SLASH | PERCENT ) mul_subexpr )*
            loop23:
            do {
                int alt23=2;
                alt23 = dfa23.predict(input);
                switch (alt23) {
            	case 1 :
            	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:161:27: ( ASTERISK | SLASH | PERCENT ) mul_subexpr
            	    {
            	    set85=(Token)input.LT(1);
            	    set85=(Token)input.LT(1);
            	    if ( (input.LA(1)>=ASTERISK && input.LA(1)<=PERCENT) ) {
            	        input.consume();
            	        root_0 = (Object)adaptor.becomeRoot((Object)adaptor.create(set85), root_0);
            	        state.errorRecovery=false;
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }

            	    pushFollow(FOLLOW_mul_subexpr_in_add_subexpr833);
            	    mul_subexpr86=mul_subexpr();

            	    state._fsp--;

            	    adaptor.addChild(root_0, mul_subexpr86.getTree());

            	    }
            	    break;

            	default :
            	    break loop23;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "add_subexpr"

    public static class mul_subexpr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "mul_subexpr"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:163:1: mul_subexpr : con_subexpr ( DOUBLE_PIPE con_subexpr )* ;
    public final SqlParser.mul_subexpr_return mul_subexpr() throws RecognitionException {
        SqlParser.mul_subexpr_return retval = new SqlParser.mul_subexpr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token DOUBLE_PIPE88=null;
        SqlParser.con_subexpr_return con_subexpr87 = null;

        SqlParser.con_subexpr_return con_subexpr89 = null;


        Object DOUBLE_PIPE88_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:163:12: ( con_subexpr ( DOUBLE_PIPE con_subexpr )* )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:163:14: con_subexpr ( DOUBLE_PIPE con_subexpr )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_con_subexpr_in_mul_subexpr842);
            con_subexpr87=con_subexpr();

            state._fsp--;

            adaptor.addChild(root_0, con_subexpr87.getTree());
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:163:26: ( DOUBLE_PIPE con_subexpr )*
            loop24:
            do {
                int alt24=2;
                alt24 = dfa24.predict(input);
                switch (alt24) {
            	case 1 :
            	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:163:27: DOUBLE_PIPE con_subexpr
            	    {
            	    DOUBLE_PIPE88=(Token)match(input,DOUBLE_PIPE,FOLLOW_DOUBLE_PIPE_in_mul_subexpr845); 
            	    DOUBLE_PIPE88_tree = (Object)adaptor.create(DOUBLE_PIPE88);
            	    root_0 = (Object)adaptor.becomeRoot(DOUBLE_PIPE88_tree, root_0);

            	    pushFollow(FOLLOW_con_subexpr_in_mul_subexpr848);
            	    con_subexpr89=con_subexpr();

            	    state._fsp--;

            	    adaptor.addChild(root_0, con_subexpr89.getTree());

            	    }
            	    break;

            	default :
            	    break loop24;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "mul_subexpr"

    public static class con_subexpr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "con_subexpr"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:165:1: con_subexpr : ( unary_subexpr | unary_op unary_subexpr -> ^( unary_op unary_subexpr ) );
    public final SqlParser.con_subexpr_return con_subexpr() throws RecognitionException {
        SqlParser.con_subexpr_return retval = new SqlParser.con_subexpr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        SqlParser.unary_subexpr_return unary_subexpr90 = null;

        SqlParser.unary_op_return unary_op91 = null;

        SqlParser.unary_subexpr_return unary_subexpr92 = null;


        RewriteRuleSubtreeStream stream_unary_subexpr=new RewriteRuleSubtreeStream(adaptor,"rule unary_subexpr");
        RewriteRuleSubtreeStream stream_unary_op=new RewriteRuleSubtreeStream(adaptor,"rule unary_op");
        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:165:12: ( unary_subexpr | unary_op unary_subexpr -> ^( unary_op unary_subexpr ) )
            int alt25=2;
            alt25 = dfa25.predict(input);
            switch (alt25) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:165:14: unary_subexpr
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_unary_subexpr_in_con_subexpr857);
                    unary_subexpr90=unary_subexpr();

                    state._fsp--;

                    adaptor.addChild(root_0, unary_subexpr90.getTree());

                    }
                    break;
                case 2 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:165:30: unary_op unary_subexpr
                    {
                    pushFollow(FOLLOW_unary_op_in_con_subexpr861);
                    unary_op91=unary_op();

                    state._fsp--;

                    stream_unary_op.add(unary_op91.getTree());
                    pushFollow(FOLLOW_unary_subexpr_in_con_subexpr863);
                    unary_subexpr92=unary_subexpr();

                    state._fsp--;

                    stream_unary_subexpr.add(unary_subexpr92.getTree());


                    // AST REWRITE
                    // elements: unary_subexpr, unary_op
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 165:53: -> ^( unary_op unary_subexpr )
                    {
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:165:56: ^( unary_op unary_subexpr )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(stream_unary_op.nextNode(), root_1);

                        adaptor.addChild(root_1, stream_unary_subexpr.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "con_subexpr"

    public static class unary_op_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "unary_op"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:167:1: unary_op : ( PLUS | MINUS | TILDA | NOT );
    public final SqlParser.unary_op_return unary_op() throws RecognitionException {
        SqlParser.unary_op_return retval = new SqlParser.unary_op_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set93=null;

        Object set93_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:167:9: ( PLUS | MINUS | TILDA | NOT )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:
            {
            root_0 = (Object)adaptor.nil();

            set93=(Token)input.LT(1);
            if ( input.LA(1)==NOT||(input.LA(1)>=PLUS && input.LA(1)<=MINUS)||input.LA(1)==TILDA ) {
                input.consume();
                adaptor.addChild(root_0, (Object)adaptor.create(set93));
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "unary_op"

    public static class unary_subexpr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "unary_subexpr"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:169:1: unary_subexpr : atom_expr ( COLLATE collation_name= ID )? ;
    public final SqlParser.unary_subexpr_return unary_subexpr() throws RecognitionException {
        SqlParser.unary_subexpr_return retval = new SqlParser.unary_subexpr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token collation_name=null;
        Token COLLATE95=null;
        SqlParser.atom_expr_return atom_expr94 = null;


        Object collation_name_tree=null;
        Object COLLATE95_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:169:14: ( atom_expr ( COLLATE collation_name= ID )? )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:169:16: atom_expr ( COLLATE collation_name= ID )?
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_atom_expr_in_unary_subexpr897);
            atom_expr94=atom_expr();

            state._fsp--;

            adaptor.addChild(root_0, atom_expr94.getTree());
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:169:26: ( COLLATE collation_name= ID )?
            int alt26=2;
            alt26 = dfa26.predict(input);
            switch (alt26) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:169:27: COLLATE collation_name= ID
                    {
                    COLLATE95=(Token)match(input,COLLATE,FOLLOW_COLLATE_in_unary_subexpr900); 
                    COLLATE95_tree = (Object)adaptor.create(COLLATE95);
                    root_0 = (Object)adaptor.becomeRoot(COLLATE95_tree, root_0);

                    collation_name=(Token)match(input,ID,FOLLOW_ID_in_unary_subexpr905); 
                    collation_name_tree = (Object)adaptor.create(collation_name);
                    adaptor.addChild(root_0, collation_name_tree);


                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "unary_subexpr"

    public static class atom_expr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "atom_expr"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:171:1: atom_expr : ( literal_value | bind_parameter | ( (database_name= id DOT )? table_name= id DOT )? column_name= ID -> ^( COLUMN_EXPRESSION ^( $column_name ( ^( $table_name ( $database_name)? ) )? ) ) | name= ID LPAREN ( ( DISTINCT )? args+= expr ( COMMA args+= expr )* | ASTERISK )? RPAREN -> ^( FUNCTION_EXPRESSION $name ( DISTINCT )? ( $args)* ( ASTERISK )? ) | LPAREN expr RPAREN | CAST LPAREN expr AS type_name RPAREN | CASE (case_expr= expr )? ( when_expr )+ ( ELSE else_expr= expr )? END -> ^( CASE ( $case_expr)? ( when_expr )+ ( $else_expr)? ) | raise_function );
    public final SqlParser.atom_expr_return atom_expr() throws RecognitionException {
        SqlParser.atom_expr_return retval = new SqlParser.atom_expr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token column_name=null;
        Token name=null;
        Token DOT98=null;
        Token DOT99=null;
        Token LPAREN100=null;
        Token DISTINCT101=null;
        Token COMMA102=null;
        Token ASTERISK103=null;
        Token RPAREN104=null;
        Token LPAREN105=null;
        Token RPAREN107=null;
        Token CAST108=null;
        Token LPAREN109=null;
        Token AS111=null;
        Token RPAREN113=null;
        Token CASE114=null;
        Token ELSE116=null;
        Token END117=null;
        List list_args=null;
        SqlParser.id_return database_name = null;

        SqlParser.id_return table_name = null;

        SqlParser.expr_return case_expr = null;

        SqlParser.expr_return else_expr = null;

        SqlParser.literal_value_return literal_value96 = null;

        SqlParser.bind_parameter_return bind_parameter97 = null;

        SqlParser.expr_return expr106 = null;

        SqlParser.expr_return expr110 = null;

        SqlParser.type_name_return type_name112 = null;

        SqlParser.when_expr_return when_expr115 = null;

        SqlParser.raise_function_return raise_function118 = null;

        SqlParser.expr_return args = null;
         args = null;
        Object column_name_tree=null;
        Object name_tree=null;
        Object DOT98_tree=null;
        Object DOT99_tree=null;
        Object LPAREN100_tree=null;
        Object DISTINCT101_tree=null;
        Object COMMA102_tree=null;
        Object ASTERISK103_tree=null;
        Object RPAREN104_tree=null;
        Object LPAREN105_tree=null;
        Object RPAREN107_tree=null;
        Object CAST108_tree=null;
        Object LPAREN109_tree=null;
        Object AS111_tree=null;
        Object RPAREN113_tree=null;
        Object CASE114_tree=null;
        Object ELSE116_tree=null;
        Object END117_tree=null;
        RewriteRuleTokenStream stream_RPAREN=new RewriteRuleTokenStream(adaptor,"token RPAREN");
        RewriteRuleTokenStream stream_END=new RewriteRuleTokenStream(adaptor,"token END");
        RewriteRuleTokenStream stream_DOT=new RewriteRuleTokenStream(adaptor,"token DOT");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_COMMA=new RewriteRuleTokenStream(adaptor,"token COMMA");
        RewriteRuleTokenStream stream_DISTINCT=new RewriteRuleTokenStream(adaptor,"token DISTINCT");
        RewriteRuleTokenStream stream_LPAREN=new RewriteRuleTokenStream(adaptor,"token LPAREN");
        RewriteRuleTokenStream stream_ASTERISK=new RewriteRuleTokenStream(adaptor,"token ASTERISK");
        RewriteRuleTokenStream stream_ELSE=new RewriteRuleTokenStream(adaptor,"token ELSE");
        RewriteRuleTokenStream stream_CASE=new RewriteRuleTokenStream(adaptor,"token CASE");
        RewriteRuleSubtreeStream stream_id=new RewriteRuleSubtreeStream(adaptor,"rule id");
        RewriteRuleSubtreeStream stream_when_expr=new RewriteRuleSubtreeStream(adaptor,"rule when_expr");
        RewriteRuleSubtreeStream stream_expr=new RewriteRuleSubtreeStream(adaptor,"rule expr");
        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:172:3: ( literal_value | bind_parameter | ( (database_name= id DOT )? table_name= id DOT )? column_name= ID -> ^( COLUMN_EXPRESSION ^( $column_name ( ^( $table_name ( $database_name)? ) )? ) ) | name= ID LPAREN ( ( DISTINCT )? args+= expr ( COMMA args+= expr )* | ASTERISK )? RPAREN -> ^( FUNCTION_EXPRESSION $name ( DISTINCT )? ( $args)* ( ASTERISK )? ) | LPAREN expr RPAREN | CAST LPAREN expr AS type_name RPAREN | CASE (case_expr= expr )? ( when_expr )+ ( ELSE else_expr= expr )? END -> ^( CASE ( $case_expr)? ( when_expr )+ ( $else_expr)? ) | raise_function )
            int alt35=8;
            alt35 = dfa35.predict(input);
            switch (alt35) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:172:5: literal_value
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_literal_value_in_atom_expr917);
                    literal_value96=literal_value();

                    state._fsp--;

                    adaptor.addChild(root_0, literal_value96.getTree());

                    }
                    break;
                case 2 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:173:5: bind_parameter
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_bind_parameter_in_atom_expr923);
                    bind_parameter97=bind_parameter();

                    state._fsp--;

                    adaptor.addChild(root_0, bind_parameter97.getTree());

                    }
                    break;
                case 3 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:174:5: ( (database_name= id DOT )? table_name= id DOT )? column_name= ID
                    {
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:174:5: ( (database_name= id DOT )? table_name= id DOT )?
                    int alt28=2;
                    alt28 = dfa28.predict(input);
                    switch (alt28) {
                        case 1 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:174:6: (database_name= id DOT )? table_name= id DOT
                            {
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:174:6: (database_name= id DOT )?
                            int alt27=2;
                            alt27 = dfa27.predict(input);
                            switch (alt27) {
                                case 1 :
                                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:174:7: database_name= id DOT
                                    {
                                    pushFollow(FOLLOW_id_in_atom_expr933);
                                    database_name=id();

                                    state._fsp--;

                                    stream_id.add(database_name.getTree());
                                    DOT98=(Token)match(input,DOT,FOLLOW_DOT_in_atom_expr935);  
                                    stream_DOT.add(DOT98);


                                    }
                                    break;

                            }

                            pushFollow(FOLLOW_id_in_atom_expr941);
                            table_name=id();

                            state._fsp--;

                            stream_id.add(table_name.getTree());
                            DOT99=(Token)match(input,DOT,FOLLOW_DOT_in_atom_expr943);  
                            stream_DOT.add(DOT99);


                            }
                            break;

                    }

                    column_name=(Token)match(input,ID,FOLLOW_ID_in_atom_expr949);  
                    stream_ID.add(column_name);



                    // AST REWRITE
                    // elements: column_name, database_name, table_name
                    // token labels: column_name
                    // rule labels: database_name, retval, table_name
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleTokenStream stream_column_name=new RewriteRuleTokenStream(adaptor,"token column_name",column_name);
                    RewriteRuleSubtreeStream stream_database_name=new RewriteRuleSubtreeStream(adaptor,"rule database_name",database_name!=null?database_name.tree:null);
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                    RewriteRuleSubtreeStream stream_table_name=new RewriteRuleSubtreeStream(adaptor,"rule table_name",table_name!=null?table_name.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 174:65: -> ^( COLUMN_EXPRESSION ^( $column_name ( ^( $table_name ( $database_name)? ) )? ) )
                    {
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:174:68: ^( COLUMN_EXPRESSION ^( $column_name ( ^( $table_name ( $database_name)? ) )? ) )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(COLUMN_EXPRESSION, "COLUMN_EXPRESSION"), root_1);

                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:174:88: ^( $column_name ( ^( $table_name ( $database_name)? ) )? )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot(stream_column_name.nextNode(), root_2);

                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:174:103: ( ^( $table_name ( $database_name)? ) )?
                        if ( stream_database_name.hasNext()||stream_table_name.hasNext() ) {
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:174:103: ^( $table_name ( $database_name)? )
                            {
                            Object root_3 = (Object)adaptor.nil();
                            root_3 = (Object)adaptor.becomeRoot(stream_table_name.nextNode(), root_3);

                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:174:117: ( $database_name)?
                            if ( stream_database_name.hasNext() ) {
                                adaptor.addChild(root_3, stream_database_name.nextTree());

                            }
                            stream_database_name.reset();

                            adaptor.addChild(root_2, root_3);
                            }

                        }
                        stream_database_name.reset();
                        stream_table_name.reset();

                        adaptor.addChild(root_1, root_2);
                        }

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 4 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:175:5: name= ID LPAREN ( ( DISTINCT )? args+= expr ( COMMA args+= expr )* | ASTERISK )? RPAREN
                    {
                    name=(Token)match(input,ID,FOLLOW_ID_in_atom_expr978);  
                    stream_ID.add(name);

                    LPAREN100=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_atom_expr980);  
                    stream_LPAREN.add(LPAREN100);

                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:175:20: ( ( DISTINCT )? args+= expr ( COMMA args+= expr )* | ASTERISK )?
                    int alt31=3;
                    alt31 = dfa31.predict(input);
                    switch (alt31) {
                        case 1 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:175:21: ( DISTINCT )? args+= expr ( COMMA args+= expr )*
                            {
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:175:21: ( DISTINCT )?
                            int alt29=2;
                            alt29 = dfa29.predict(input);
                            switch (alt29) {
                                case 1 :
                                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:175:21: DISTINCT
                                    {
                                    DISTINCT101=(Token)match(input,DISTINCT,FOLLOW_DISTINCT_in_atom_expr983);  
                                    stream_DISTINCT.add(DISTINCT101);


                                    }
                                    break;

                            }

                            pushFollow(FOLLOW_expr_in_atom_expr988);
                            args=expr();

                            state._fsp--;

                            stream_expr.add(args.getTree());
                            if (list_args==null) list_args=new ArrayList();
                            list_args.add(args.getTree());

                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:175:42: ( COMMA args+= expr )*
                            loop30:
                            do {
                                int alt30=2;
                                int LA30_0 = input.LA(1);

                                if ( (LA30_0==COMMA) ) {
                                    alt30=1;
                                }


                                switch (alt30) {
                            	case 1 :
                            	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:175:43: COMMA args+= expr
                            	    {
                            	    COMMA102=(Token)match(input,COMMA,FOLLOW_COMMA_in_atom_expr991);  
                            	    stream_COMMA.add(COMMA102);

                            	    pushFollow(FOLLOW_expr_in_atom_expr995);
                            	    args=expr();

                            	    state._fsp--;

                            	    stream_expr.add(args.getTree());
                            	    if (list_args==null) list_args=new ArrayList();
                            	    list_args.add(args.getTree());


                            	    }
                            	    break;

                            	default :
                            	    break loop30;
                                }
                            } while (true);


                            }
                            break;
                        case 2 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:175:64: ASTERISK
                            {
                            ASTERISK103=(Token)match(input,ASTERISK,FOLLOW_ASTERISK_in_atom_expr1001);  
                            stream_ASTERISK.add(ASTERISK103);


                            }
                            break;

                    }

                    RPAREN104=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_atom_expr1005);  
                    stream_RPAREN.add(RPAREN104);



                    // AST REWRITE
                    // elements: args, name, DISTINCT, ASTERISK
                    // token labels: name
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: args
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleTokenStream stream_name=new RewriteRuleTokenStream(adaptor,"token name",name);
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                    RewriteRuleSubtreeStream stream_args=new RewriteRuleSubtreeStream(adaptor,"token args",list_args);
                    root_0 = (Object)adaptor.nil();
                    // 175:82: -> ^( FUNCTION_EXPRESSION $name ( DISTINCT )? ( $args)* ( ASTERISK )? )
                    {
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:175:85: ^( FUNCTION_EXPRESSION $name ( DISTINCT )? ( $args)* ( ASTERISK )? )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(FUNCTION_EXPRESSION, "FUNCTION_EXPRESSION"), root_1);

                        adaptor.addChild(root_1, stream_name.nextNode());
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:175:113: ( DISTINCT )?
                        if ( stream_DISTINCT.hasNext() ) {
                            adaptor.addChild(root_1, stream_DISTINCT.nextNode());

                        }
                        stream_DISTINCT.reset();
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:175:123: ( $args)*
                        while ( stream_args.hasNext() ) {
                            adaptor.addChild(root_1, stream_args.nextTree());

                        }
                        stream_args.reset();
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:175:130: ( ASTERISK )?
                        if ( stream_ASTERISK.hasNext() ) {
                            adaptor.addChild(root_1, stream_ASTERISK.nextNode());

                        }
                        stream_ASTERISK.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 5 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:176:5: LPAREN expr RPAREN
                    {
                    root_0 = (Object)adaptor.nil();

                    LPAREN105=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_atom_expr1030); 
                    pushFollow(FOLLOW_expr_in_atom_expr1033);
                    expr106=expr();

                    state._fsp--;

                    adaptor.addChild(root_0, expr106.getTree());
                    RPAREN107=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_atom_expr1035); 

                    }
                    break;
                case 6 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:177:5: CAST LPAREN expr AS type_name RPAREN
                    {
                    root_0 = (Object)adaptor.nil();

                    CAST108=(Token)match(input,CAST,FOLLOW_CAST_in_atom_expr1042); 
                    CAST108_tree = (Object)adaptor.create(CAST108);
                    root_0 = (Object)adaptor.becomeRoot(CAST108_tree, root_0);

                    LPAREN109=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_atom_expr1045); 
                    pushFollow(FOLLOW_expr_in_atom_expr1048);
                    expr110=expr();

                    state._fsp--;

                    adaptor.addChild(root_0, expr110.getTree());
                    AS111=(Token)match(input,AS,FOLLOW_AS_in_atom_expr1050); 
                    pushFollow(FOLLOW_type_name_in_atom_expr1053);
                    type_name112=type_name();

                    state._fsp--;

                    adaptor.addChild(root_0, type_name112.getTree());
                    RPAREN113=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_atom_expr1055); 

                    }
                    break;
                case 7 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:180:5: CASE (case_expr= expr )? ( when_expr )+ ( ELSE else_expr= expr )? END
                    {
                    CASE114=(Token)match(input,CASE,FOLLOW_CASE_in_atom_expr1064);  
                    stream_CASE.add(CASE114);

                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:180:10: (case_expr= expr )?
                    int alt32=2;
                    alt32 = dfa32.predict(input);
                    switch (alt32) {
                        case 1 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:180:11: case_expr= expr
                            {
                            pushFollow(FOLLOW_expr_in_atom_expr1069);
                            case_expr=expr();

                            state._fsp--;

                            stream_expr.add(case_expr.getTree());

                            }
                            break;

                    }

                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:180:28: ( when_expr )+
                    int cnt33=0;
                    loop33:
                    do {
                        int alt33=2;
                        int LA33_0 = input.LA(1);

                        if ( (LA33_0==WHEN) ) {
                            alt33=1;
                        }


                        switch (alt33) {
                    	case 1 :
                    	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:180:28: when_expr
                    	    {
                    	    pushFollow(FOLLOW_when_expr_in_atom_expr1073);
                    	    when_expr115=when_expr();

                    	    state._fsp--;

                    	    stream_when_expr.add(when_expr115.getTree());

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt33 >= 1 ) break loop33;
                                EarlyExitException eee =
                                    new EarlyExitException(33, input);
                                throw eee;
                        }
                        cnt33++;
                    } while (true);

                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:180:39: ( ELSE else_expr= expr )?
                    int alt34=2;
                    int LA34_0 = input.LA(1);

                    if ( (LA34_0==ELSE) ) {
                        alt34=1;
                    }
                    switch (alt34) {
                        case 1 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:180:40: ELSE else_expr= expr
                            {
                            ELSE116=(Token)match(input,ELSE,FOLLOW_ELSE_in_atom_expr1077);  
                            stream_ELSE.add(ELSE116);

                            pushFollow(FOLLOW_expr_in_atom_expr1081);
                            else_expr=expr();

                            state._fsp--;

                            stream_expr.add(else_expr.getTree());

                            }
                            break;

                    }

                    END117=(Token)match(input,END,FOLLOW_END_in_atom_expr1085);  
                    stream_END.add(END117);



                    // AST REWRITE
                    // elements: when_expr, CASE, else_expr, case_expr
                    // token labels: 
                    // rule labels: retval, case_expr, else_expr
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                    RewriteRuleSubtreeStream stream_case_expr=new RewriteRuleSubtreeStream(adaptor,"rule case_expr",case_expr!=null?case_expr.tree:null);
                    RewriteRuleSubtreeStream stream_else_expr=new RewriteRuleSubtreeStream(adaptor,"rule else_expr",else_expr!=null?else_expr.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 180:66: -> ^( CASE ( $case_expr)? ( when_expr )+ ( $else_expr)? )
                    {
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:180:69: ^( CASE ( $case_expr)? ( when_expr )+ ( $else_expr)? )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(stream_CASE.nextNode(), root_1);

                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:180:76: ( $case_expr)?
                        if ( stream_case_expr.hasNext() ) {
                            adaptor.addChild(root_1, stream_case_expr.nextTree());

                        }
                        stream_case_expr.reset();
                        if ( !(stream_when_expr.hasNext()) ) {
                            throw new RewriteEarlyExitException();
                        }
                        while ( stream_when_expr.hasNext() ) {
                            adaptor.addChild(root_1, stream_when_expr.nextTree());

                        }
                        stream_when_expr.reset();
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:180:99: ( $else_expr)?
                        if ( stream_else_expr.hasNext() ) {
                            adaptor.addChild(root_1, stream_else_expr.nextTree());

                        }
                        stream_else_expr.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 8 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:181:5: raise_function
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_raise_function_in_atom_expr1108);
                    raise_function118=raise_function();

                    state._fsp--;

                    adaptor.addChild(root_0, raise_function118.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "atom_expr"

    public static class when_expr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "when_expr"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:184:1: when_expr : WHEN e1= expr THEN e2= expr -> ^( WHEN $e1 $e2) ;
    public final SqlParser.when_expr_return when_expr() throws RecognitionException {
        SqlParser.when_expr_return retval = new SqlParser.when_expr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token WHEN119=null;
        Token THEN120=null;
        SqlParser.expr_return e1 = null;

        SqlParser.expr_return e2 = null;


        Object WHEN119_tree=null;
        Object THEN120_tree=null;
        RewriteRuleTokenStream stream_THEN=new RewriteRuleTokenStream(adaptor,"token THEN");
        RewriteRuleTokenStream stream_WHEN=new RewriteRuleTokenStream(adaptor,"token WHEN");
        RewriteRuleSubtreeStream stream_expr=new RewriteRuleSubtreeStream(adaptor,"rule expr");
        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:184:10: ( WHEN e1= expr THEN e2= expr -> ^( WHEN $e1 $e2) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:184:12: WHEN e1= expr THEN e2= expr
            {
            WHEN119=(Token)match(input,WHEN,FOLLOW_WHEN_in_when_expr1118);  
            stream_WHEN.add(WHEN119);

            pushFollow(FOLLOW_expr_in_when_expr1122);
            e1=expr();

            state._fsp--;

            stream_expr.add(e1.getTree());
            THEN120=(Token)match(input,THEN,FOLLOW_THEN_in_when_expr1124);  
            stream_THEN.add(THEN120);

            pushFollow(FOLLOW_expr_in_when_expr1128);
            e2=expr();

            state._fsp--;

            stream_expr.add(e2.getTree());


            // AST REWRITE
            // elements: e2, e1, WHEN
            // token labels: 
            // rule labels: retval, e1, e2
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_e1=new RewriteRuleSubtreeStream(adaptor,"rule e1",e1!=null?e1.tree:null);
            RewriteRuleSubtreeStream stream_e2=new RewriteRuleSubtreeStream(adaptor,"rule e2",e2!=null?e2.tree:null);

            root_0 = (Object)adaptor.nil();
            // 184:38: -> ^( WHEN $e1 $e2)
            {
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:184:41: ^( WHEN $e1 $e2)
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(stream_WHEN.nextNode(), root_1);

                adaptor.addChild(root_1, stream_e1.nextTree());
                adaptor.addChild(root_1, stream_e2.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "when_expr"

    public static class literal_value_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "literal_value"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:186:1: literal_value : ( INTEGER -> ^( INTEGER_LITERAL INTEGER ) | FLOAT -> ^( FLOAT_LITERAL FLOAT ) | STRING -> ^( STRING_LITERAL STRING ) | BLOB -> ^( BLOB_LITERAL BLOB ) | NULL | CURRENT_TIME -> ^( FUNCTION_LITERAL CURRENT_TIME ) | CURRENT_DATE -> ^( FUNCTION_LITERAL CURRENT_DATE ) | CURRENT_TIMESTAMP -> ^( FUNCTION_LITERAL CURRENT_TIMESTAMP ) );
    public final SqlParser.literal_value_return literal_value() throws RecognitionException {
        SqlParser.literal_value_return retval = new SqlParser.literal_value_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token INTEGER121=null;
        Token FLOAT122=null;
        Token STRING123=null;
        Token BLOB124=null;
        Token NULL125=null;
        Token CURRENT_TIME126=null;
        Token CURRENT_DATE127=null;
        Token CURRENT_TIMESTAMP128=null;

        Object INTEGER121_tree=null;
        Object FLOAT122_tree=null;
        Object STRING123_tree=null;
        Object BLOB124_tree=null;
        Object NULL125_tree=null;
        Object CURRENT_TIME126_tree=null;
        Object CURRENT_DATE127_tree=null;
        Object CURRENT_TIMESTAMP128_tree=null;
        RewriteRuleTokenStream stream_INTEGER=new RewriteRuleTokenStream(adaptor,"token INTEGER");
        RewriteRuleTokenStream stream_BLOB=new RewriteRuleTokenStream(adaptor,"token BLOB");
        RewriteRuleTokenStream stream_FLOAT=new RewriteRuleTokenStream(adaptor,"token FLOAT");
        RewriteRuleTokenStream stream_CURRENT_TIMESTAMP=new RewriteRuleTokenStream(adaptor,"token CURRENT_TIMESTAMP");
        RewriteRuleTokenStream stream_CURRENT_DATE=new RewriteRuleTokenStream(adaptor,"token CURRENT_DATE");
        RewriteRuleTokenStream stream_CURRENT_TIME=new RewriteRuleTokenStream(adaptor,"token CURRENT_TIME");
        RewriteRuleTokenStream stream_STRING=new RewriteRuleTokenStream(adaptor,"token STRING");

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:187:3: ( INTEGER -> ^( INTEGER_LITERAL INTEGER ) | FLOAT -> ^( FLOAT_LITERAL FLOAT ) | STRING -> ^( STRING_LITERAL STRING ) | BLOB -> ^( BLOB_LITERAL BLOB ) | NULL | CURRENT_TIME -> ^( FUNCTION_LITERAL CURRENT_TIME ) | CURRENT_DATE -> ^( FUNCTION_LITERAL CURRENT_DATE ) | CURRENT_TIMESTAMP -> ^( FUNCTION_LITERAL CURRENT_TIMESTAMP ) )
            int alt36=8;
            switch ( input.LA(1) ) {
            case INTEGER:
                {
                alt36=1;
                }
                break;
            case FLOAT:
                {
                alt36=2;
                }
                break;
            case STRING:
                {
                alt36=3;
                }
                break;
            case BLOB:
                {
                alt36=4;
                }
                break;
            case NULL:
                {
                alt36=5;
                }
                break;
            case CURRENT_TIME:
                {
                alt36=6;
                }
                break;
            case CURRENT_DATE:
                {
                alt36=7;
                }
                break;
            case CURRENT_TIMESTAMP:
                {
                alt36=8;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 36, 0, input);

                throw nvae;
            }

            switch (alt36) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:187:5: INTEGER
                    {
                    INTEGER121=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_literal_value1150);  
                    stream_INTEGER.add(INTEGER121);



                    // AST REWRITE
                    // elements: INTEGER
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 187:13: -> ^( INTEGER_LITERAL INTEGER )
                    {
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:187:16: ^( INTEGER_LITERAL INTEGER )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(INTEGER_LITERAL, "INTEGER_LITERAL"), root_1);

                        adaptor.addChild(root_1, stream_INTEGER.nextNode());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:188:5: FLOAT
                    {
                    FLOAT122=(Token)match(input,FLOAT,FOLLOW_FLOAT_in_literal_value1164);  
                    stream_FLOAT.add(FLOAT122);



                    // AST REWRITE
                    // elements: FLOAT
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 188:11: -> ^( FLOAT_LITERAL FLOAT )
                    {
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:188:14: ^( FLOAT_LITERAL FLOAT )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(FLOAT_LITERAL, "FLOAT_LITERAL"), root_1);

                        adaptor.addChild(root_1, stream_FLOAT.nextNode());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 3 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:189:5: STRING
                    {
                    STRING123=(Token)match(input,STRING,FOLLOW_STRING_in_literal_value1178);  
                    stream_STRING.add(STRING123);



                    // AST REWRITE
                    // elements: STRING
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 189:12: -> ^( STRING_LITERAL STRING )
                    {
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:189:15: ^( STRING_LITERAL STRING )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(STRING_LITERAL, "STRING_LITERAL"), root_1);

                        adaptor.addChild(root_1, stream_STRING.nextNode());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 4 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:190:5: BLOB
                    {
                    BLOB124=(Token)match(input,BLOB,FOLLOW_BLOB_in_literal_value1192);  
                    stream_BLOB.add(BLOB124);



                    // AST REWRITE
                    // elements: BLOB
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 190:10: -> ^( BLOB_LITERAL BLOB )
                    {
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:190:13: ^( BLOB_LITERAL BLOB )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(BLOB_LITERAL, "BLOB_LITERAL"), root_1);

                        adaptor.addChild(root_1, stream_BLOB.nextNode());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 5 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:191:5: NULL
                    {
                    root_0 = (Object)adaptor.nil();

                    NULL125=(Token)match(input,NULL,FOLLOW_NULL_in_literal_value1206); 
                    NULL125_tree = (Object)adaptor.create(NULL125);
                    adaptor.addChild(root_0, NULL125_tree);


                    }
                    break;
                case 6 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:192:5: CURRENT_TIME
                    {
                    CURRENT_TIME126=(Token)match(input,CURRENT_TIME,FOLLOW_CURRENT_TIME_in_literal_value1212);  
                    stream_CURRENT_TIME.add(CURRENT_TIME126);



                    // AST REWRITE
                    // elements: CURRENT_TIME
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 192:18: -> ^( FUNCTION_LITERAL CURRENT_TIME )
                    {
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:192:21: ^( FUNCTION_LITERAL CURRENT_TIME )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(FUNCTION_LITERAL, "FUNCTION_LITERAL"), root_1);

                        adaptor.addChild(root_1, stream_CURRENT_TIME.nextNode());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 7 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:193:5: CURRENT_DATE
                    {
                    CURRENT_DATE127=(Token)match(input,CURRENT_DATE,FOLLOW_CURRENT_DATE_in_literal_value1226);  
                    stream_CURRENT_DATE.add(CURRENT_DATE127);



                    // AST REWRITE
                    // elements: CURRENT_DATE
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 193:18: -> ^( FUNCTION_LITERAL CURRENT_DATE )
                    {
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:193:21: ^( FUNCTION_LITERAL CURRENT_DATE )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(FUNCTION_LITERAL, "FUNCTION_LITERAL"), root_1);

                        adaptor.addChild(root_1, stream_CURRENT_DATE.nextNode());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 8 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:194:5: CURRENT_TIMESTAMP
                    {
                    CURRENT_TIMESTAMP128=(Token)match(input,CURRENT_TIMESTAMP,FOLLOW_CURRENT_TIMESTAMP_in_literal_value1240);  
                    stream_CURRENT_TIMESTAMP.add(CURRENT_TIMESTAMP128);



                    // AST REWRITE
                    // elements: CURRENT_TIMESTAMP
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 194:23: -> ^( FUNCTION_LITERAL CURRENT_TIMESTAMP )
                    {
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:194:26: ^( FUNCTION_LITERAL CURRENT_TIMESTAMP )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(FUNCTION_LITERAL, "FUNCTION_LITERAL"), root_1);

                        adaptor.addChild(root_1, stream_CURRENT_TIMESTAMP.nextNode());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "literal_value"

    public static class bind_parameter_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "bind_parameter"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:197:1: bind_parameter : ( QUESTION -> BIND | QUESTION position= INTEGER -> ^( BIND $position) | COLON name= id -> ^( BIND_NAME $name) | AT name= id -> ^( BIND_NAME $name) );
    public final SqlParser.bind_parameter_return bind_parameter() throws RecognitionException {
        SqlParser.bind_parameter_return retval = new SqlParser.bind_parameter_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token position=null;
        Token QUESTION129=null;
        Token QUESTION130=null;
        Token COLON131=null;
        Token AT132=null;
        SqlParser.id_return name = null;


        Object position_tree=null;
        Object QUESTION129_tree=null;
        Object QUESTION130_tree=null;
        Object COLON131_tree=null;
        Object AT132_tree=null;
        RewriteRuleTokenStream stream_AT=new RewriteRuleTokenStream(adaptor,"token AT");
        RewriteRuleTokenStream stream_COLON=new RewriteRuleTokenStream(adaptor,"token COLON");
        RewriteRuleTokenStream stream_INTEGER=new RewriteRuleTokenStream(adaptor,"token INTEGER");
        RewriteRuleTokenStream stream_QUESTION=new RewriteRuleTokenStream(adaptor,"token QUESTION");
        RewriteRuleSubtreeStream stream_id=new RewriteRuleSubtreeStream(adaptor,"rule id");
        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:198:3: ( QUESTION -> BIND | QUESTION position= INTEGER -> ^( BIND $position) | COLON name= id -> ^( BIND_NAME $name) | AT name= id -> ^( BIND_NAME $name) )
            int alt37=4;
            alt37 = dfa37.predict(input);
            switch (alt37) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:198:5: QUESTION
                    {
                    QUESTION129=(Token)match(input,QUESTION,FOLLOW_QUESTION_in_bind_parameter1261);  
                    stream_QUESTION.add(QUESTION129);



                    // AST REWRITE
                    // elements: 
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 198:14: -> BIND
                    {
                        adaptor.addChild(root_0, (Object)adaptor.create(BIND, "BIND"));

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:199:5: QUESTION position= INTEGER
                    {
                    QUESTION130=(Token)match(input,QUESTION,FOLLOW_QUESTION_in_bind_parameter1271);  
                    stream_QUESTION.add(QUESTION130);

                    position=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_bind_parameter1275);  
                    stream_INTEGER.add(position);



                    // AST REWRITE
                    // elements: position
                    // token labels: position
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleTokenStream stream_position=new RewriteRuleTokenStream(adaptor,"token position",position);
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 199:31: -> ^( BIND $position)
                    {
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:199:34: ^( BIND $position)
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(BIND, "BIND"), root_1);

                        adaptor.addChild(root_1, stream_position.nextNode());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 3 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:200:5: COLON name= id
                    {
                    COLON131=(Token)match(input,COLON,FOLLOW_COLON_in_bind_parameter1290);  
                    stream_COLON.add(COLON131);

                    pushFollow(FOLLOW_id_in_bind_parameter1294);
                    name=id();

                    state._fsp--;

                    stream_id.add(name.getTree());


                    // AST REWRITE
                    // elements: name
                    // token labels: 
                    // rule labels: retval, name
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                    RewriteRuleSubtreeStream stream_name=new RewriteRuleSubtreeStream(adaptor,"rule name",name!=null?name.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 200:19: -> ^( BIND_NAME $name)
                    {
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:200:22: ^( BIND_NAME $name)
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(BIND_NAME, "BIND_NAME"), root_1);

                        adaptor.addChild(root_1, stream_name.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 4 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:201:5: AT name= id
                    {
                    AT132=(Token)match(input,AT,FOLLOW_AT_in_bind_parameter1309);  
                    stream_AT.add(AT132);

                    pushFollow(FOLLOW_id_in_bind_parameter1313);
                    name=id();

                    state._fsp--;

                    stream_id.add(name.getTree());


                    // AST REWRITE
                    // elements: name
                    // token labels: 
                    // rule labels: retval, name
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                    RewriteRuleSubtreeStream stream_name=new RewriteRuleSubtreeStream(adaptor,"rule name",name!=null?name.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 201:16: -> ^( BIND_NAME $name)
                    {
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:201:19: ^( BIND_NAME $name)
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(BIND_NAME, "BIND_NAME"), root_1);

                        adaptor.addChild(root_1, stream_name.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "bind_parameter"

    public static class raise_function_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "raise_function"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:206:1: raise_function : RAISE LPAREN ( IGNORE | ( ROLLBACK | ABORT | FAIL ) COMMA error_message= STRING ) RPAREN ;
    public final SqlParser.raise_function_return raise_function() throws RecognitionException {
        SqlParser.raise_function_return retval = new SqlParser.raise_function_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token error_message=null;
        Token RAISE133=null;
        Token LPAREN134=null;
        Token IGNORE135=null;
        Token set136=null;
        Token COMMA137=null;
        Token RPAREN138=null;

        Object error_message_tree=null;
        Object RAISE133_tree=null;
        Object LPAREN134_tree=null;
        Object IGNORE135_tree=null;
        Object set136_tree=null;
        Object COMMA137_tree=null;
        Object RPAREN138_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:206:15: ( RAISE LPAREN ( IGNORE | ( ROLLBACK | ABORT | FAIL ) COMMA error_message= STRING ) RPAREN )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:206:17: RAISE LPAREN ( IGNORE | ( ROLLBACK | ABORT | FAIL ) COMMA error_message= STRING ) RPAREN
            {
            root_0 = (Object)adaptor.nil();

            RAISE133=(Token)match(input,RAISE,FOLLOW_RAISE_in_raise_function1334); 
            RAISE133_tree = (Object)adaptor.create(RAISE133);
            root_0 = (Object)adaptor.becomeRoot(RAISE133_tree, root_0);

            LPAREN134=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_raise_function1337); 
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:206:32: ( IGNORE | ( ROLLBACK | ABORT | FAIL ) COMMA error_message= STRING )
            int alt38=2;
            int LA38_0 = input.LA(1);

            if ( (LA38_0==IGNORE) ) {
                alt38=1;
            }
            else if ( ((LA38_0>=ROLLBACK && LA38_0<=FAIL)) ) {
                alt38=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 38, 0, input);

                throw nvae;
            }
            switch (alt38) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:206:33: IGNORE
                    {
                    IGNORE135=(Token)match(input,IGNORE,FOLLOW_IGNORE_in_raise_function1341); 
                    IGNORE135_tree = (Object)adaptor.create(IGNORE135);
                    adaptor.addChild(root_0, IGNORE135_tree);


                    }
                    break;
                case 2 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:206:42: ( ROLLBACK | ABORT | FAIL ) COMMA error_message= STRING
                    {
                    set136=(Token)input.LT(1);
                    if ( (input.LA(1)>=ROLLBACK && input.LA(1)<=FAIL) ) {
                        input.consume();
                        adaptor.addChild(root_0, (Object)adaptor.create(set136));
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    COMMA137=(Token)match(input,COMMA,FOLLOW_COMMA_in_raise_function1357); 
                    error_message=(Token)match(input,STRING,FOLLOW_STRING_in_raise_function1362); 
                    error_message_tree = (Object)adaptor.create(error_message);
                    adaptor.addChild(root_0, error_message_tree);


                    }
                    break;

            }

            RPAREN138=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_raise_function1365); 

            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "raise_function"

    public static class type_name_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "type_name"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:208:1: type_name : (names+= ID )+ ( LPAREN size1= ( signed_number | MAX | signed_number BYTE ) ( COMMA size2= signed_number )? RPAREN )? -> ^( TYPE ^( TYPE_PARAMS ( $size1)? ( $size2)? ) ( $names)+ ) ;
    public final SqlParser.type_name_return type_name() throws RecognitionException {
        SqlParser.type_name_return retval = new SqlParser.type_name_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token size1=null;
        Token LPAREN139=null;
        Token MAX141=null;
        Token BYTE143=null;
        Token COMMA144=null;
        Token RPAREN145=null;
        Token names=null;
        List list_names=null;
        SqlParser.signed_number_return size2 = null;

        SqlParser.signed_number_return signed_number140 = null;

        SqlParser.signed_number_return signed_number142 = null;


        Object size1_tree=null;
        Object LPAREN139_tree=null;
        Object MAX141_tree=null;
        Object BYTE143_tree=null;
        Object COMMA144_tree=null;
        Object RPAREN145_tree=null;
        Object names_tree=null;
        RewriteRuleTokenStream stream_MAX=new RewriteRuleTokenStream(adaptor,"token MAX");
        RewriteRuleTokenStream stream_BYTE=new RewriteRuleTokenStream(adaptor,"token BYTE");
        RewriteRuleTokenStream stream_RPAREN=new RewriteRuleTokenStream(adaptor,"token RPAREN");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_COMMA=new RewriteRuleTokenStream(adaptor,"token COMMA");
        RewriteRuleTokenStream stream_LPAREN=new RewriteRuleTokenStream(adaptor,"token LPAREN");
        RewriteRuleSubtreeStream stream_signed_number=new RewriteRuleSubtreeStream(adaptor,"rule signed_number");
        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:208:10: ( (names+= ID )+ ( LPAREN size1= ( signed_number | MAX | signed_number BYTE ) ( COMMA size2= signed_number )? RPAREN )? -> ^( TYPE ^( TYPE_PARAMS ( $size1)? ( $size2)? ) ( $names)+ ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:208:12: (names+= ID )+ ( LPAREN size1= ( signed_number | MAX | signed_number BYTE ) ( COMMA size2= signed_number )? RPAREN )?
            {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:208:17: (names+= ID )+
            int cnt39=0;
            loop39:
            do {
                int alt39=2;
                alt39 = dfa39.predict(input);
                switch (alt39) {
            	case 1 :
            	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:208:17: names+= ID
            	    {
            	    names=(Token)match(input,ID,FOLLOW_ID_in_type_name1375);  
            	    stream_ID.add(names);

            	    if (list_names==null) list_names=new ArrayList();
            	    list_names.add(names);


            	    }
            	    break;

            	default :
            	    if ( cnt39 >= 1 ) break loop39;
                        EarlyExitException eee =
                            new EarlyExitException(39, input);
                        throw eee;
                }
                cnt39++;
            } while (true);

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:208:23: ( LPAREN size1= ( signed_number | MAX | signed_number BYTE ) ( COMMA size2= signed_number )? RPAREN )?
            int alt42=2;
            alt42 = dfa42.predict(input);
            switch (alt42) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:208:24: LPAREN size1= ( signed_number | MAX | signed_number BYTE ) ( COMMA size2= signed_number )? RPAREN
                    {
                    LPAREN139=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_type_name1379);  
                    stream_LPAREN.add(LPAREN139);

                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:208:38: ( signed_number | MAX | signed_number BYTE )
                    int alt40=3;
                    alt40 = dfa40.predict(input);
                    switch (alt40) {
                        case 1 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:208:39: signed_number
                            {
                            pushFollow(FOLLOW_signed_number_in_type_name1385);
                            signed_number140=signed_number();

                            state._fsp--;

                            stream_signed_number.add(signed_number140.getTree());

                            }
                            break;
                        case 2 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:208:55: MAX
                            {
                            MAX141=(Token)match(input,MAX,FOLLOW_MAX_in_type_name1389);  
                            stream_MAX.add(MAX141);


                            }
                            break;
                        case 3 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:208:61: signed_number BYTE
                            {
                            pushFollow(FOLLOW_signed_number_in_type_name1393);
                            signed_number142=signed_number();

                            state._fsp--;

                            stream_signed_number.add(signed_number142.getTree());
                            BYTE143=(Token)match(input,BYTE,FOLLOW_BYTE_in_type_name1395);  
                            stream_BYTE.add(BYTE143);


                            }
                            break;

                    }

                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:208:81: ( COMMA size2= signed_number )?
                    int alt41=2;
                    int LA41_0 = input.LA(1);

                    if ( (LA41_0==COMMA) ) {
                        alt41=1;
                    }
                    switch (alt41) {
                        case 1 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:208:82: COMMA size2= signed_number
                            {
                            COMMA144=(Token)match(input,COMMA,FOLLOW_COMMA_in_type_name1399);  
                            stream_COMMA.add(COMMA144);

                            pushFollow(FOLLOW_signed_number_in_type_name1403);
                            size2=signed_number();

                            state._fsp--;

                            stream_signed_number.add(size2.getTree());

                            }
                            break;

                    }

                    RPAREN145=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_type_name1407);  
                    stream_RPAREN.add(RPAREN145);


                    }
                    break;

            }

            builder.setDomainOfLastAttribute(String.valueOf(list_names.get(0)).split("'")[1]);


            // AST REWRITE
            // elements: size2, size1, names
            // token labels: size1
            // rule labels: retval, size2
            // token list labels: names
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleTokenStream stream_size1=new RewriteRuleTokenStream(adaptor,"token size1",size1);
            RewriteRuleTokenStream stream_names=new RewriteRuleTokenStream(adaptor,"token names", list_names);
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_size2=new RewriteRuleSubtreeStream(adaptor,"rule size2",size2!=null?size2.tree:null);

            root_0 = (Object)adaptor.nil();
            // 209:1: -> ^( TYPE ^( TYPE_PARAMS ( $size1)? ( $size2)? ) ( $names)+ )
            {
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:209:4: ^( TYPE ^( TYPE_PARAMS ( $size1)? ( $size2)? ) ( $names)+ )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(TYPE, "TYPE"), root_1);

                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:209:11: ^( TYPE_PARAMS ( $size1)? ( $size2)? )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(TYPE_PARAMS, "TYPE_PARAMS"), root_2);

                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:209:25: ( $size1)?
                if ( stream_size1.hasNext() ) {
                    adaptor.addChild(root_2, stream_size1.nextNode());

                }
                stream_size1.reset();
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:209:33: ( $size2)?
                if ( stream_size2.hasNext() ) {
                    adaptor.addChild(root_2, stream_size2.nextTree());

                }
                stream_size2.reset();

                adaptor.addChild(root_1, root_2);
                }
                if ( !(stream_names.hasNext()) ) {
                    throw new RewriteEarlyExitException();
                }
                while ( stream_names.hasNext() ) {
                    adaptor.addChild(root_1, stream_names.nextNode());

                }
                stream_names.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "type_name"

    public static class signed_number_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "signed_number"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:211:1: signed_number : ( PLUS | MINUS )? ( INTEGER | FLOAT ) ;
    public final SqlParser.signed_number_return signed_number() throws RecognitionException {
        SqlParser.signed_number_return retval = new SqlParser.signed_number_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set146=null;
        Token set147=null;

        Object set146_tree=null;
        Object set147_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:211:14: ( ( PLUS | MINUS )? ( INTEGER | FLOAT ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:211:16: ( PLUS | MINUS )? ( INTEGER | FLOAT )
            {
            root_0 = (Object)adaptor.nil();

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:211:16: ( PLUS | MINUS )?
            int alt43=2;
            int LA43_0 = input.LA(1);

            if ( ((LA43_0>=PLUS && LA43_0<=MINUS)) ) {
                alt43=1;
            }
            switch (alt43) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:
                    {
                    set146=(Token)input.LT(1);
                    if ( (input.LA(1)>=PLUS && input.LA(1)<=MINUS) ) {
                        input.consume();
                        adaptor.addChild(root_0, (Object)adaptor.create(set146));
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }


                    }
                    break;

            }

            set147=(Token)input.LT(1);
            if ( (input.LA(1)>=INTEGER && input.LA(1)<=FLOAT) ) {
                input.consume();
                adaptor.addChild(root_0, (Object)adaptor.create(set147));
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "signed_number"

    public static class pragma_stmt_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "pragma_stmt"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:214:1: pragma_stmt : PRAGMA (database_name= id DOT )? pragma_name= id ( EQUALS pragma_value | LPAREN pragma_value RPAREN )? -> ^( PRAGMA ^( $pragma_name ( $database_name)? ) ( pragma_value )? ) ;
    public final SqlParser.pragma_stmt_return pragma_stmt() throws RecognitionException {
        SqlParser.pragma_stmt_return retval = new SqlParser.pragma_stmt_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token PRAGMA148=null;
        Token DOT149=null;
        Token EQUALS150=null;
        Token LPAREN152=null;
        Token RPAREN154=null;
        SqlParser.id_return database_name = null;

        SqlParser.id_return pragma_name = null;

        SqlParser.pragma_value_return pragma_value151 = null;

        SqlParser.pragma_value_return pragma_value153 = null;


        Object PRAGMA148_tree=null;
        Object DOT149_tree=null;
        Object EQUALS150_tree=null;
        Object LPAREN152_tree=null;
        Object RPAREN154_tree=null;
        RewriteRuleTokenStream stream_RPAREN=new RewriteRuleTokenStream(adaptor,"token RPAREN");
        RewriteRuleTokenStream stream_EQUALS=new RewriteRuleTokenStream(adaptor,"token EQUALS");
        RewriteRuleTokenStream stream_DOT=new RewriteRuleTokenStream(adaptor,"token DOT");
        RewriteRuleTokenStream stream_LPAREN=new RewriteRuleTokenStream(adaptor,"token LPAREN");
        RewriteRuleTokenStream stream_PRAGMA=new RewriteRuleTokenStream(adaptor,"token PRAGMA");
        RewriteRuleSubtreeStream stream_id=new RewriteRuleSubtreeStream(adaptor,"rule id");
        RewriteRuleSubtreeStream stream_pragma_value=new RewriteRuleSubtreeStream(adaptor,"rule pragma_value");
        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:214:12: ( PRAGMA (database_name= id DOT )? pragma_name= id ( EQUALS pragma_value | LPAREN pragma_value RPAREN )? -> ^( PRAGMA ^( $pragma_name ( $database_name)? ) ( pragma_value )? ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:214:14: PRAGMA (database_name= id DOT )? pragma_name= id ( EQUALS pragma_value | LPAREN pragma_value RPAREN )?
            {
            PRAGMA148=(Token)match(input,PRAGMA,FOLLOW_PRAGMA_in_pragma_stmt1463);  
            stream_PRAGMA.add(PRAGMA148);

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:214:21: (database_name= id DOT )?
            int alt44=2;
            alt44 = dfa44.predict(input);
            switch (alt44) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:214:22: database_name= id DOT
                    {
                    pushFollow(FOLLOW_id_in_pragma_stmt1468);
                    database_name=id();

                    state._fsp--;

                    stream_id.add(database_name.getTree());
                    DOT149=(Token)match(input,DOT,FOLLOW_DOT_in_pragma_stmt1470);  
                    stream_DOT.add(DOT149);


                    }
                    break;

            }

            pushFollow(FOLLOW_id_in_pragma_stmt1476);
            pragma_name=id();

            state._fsp--;

            stream_id.add(pragma_name.getTree());
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:214:60: ( EQUALS pragma_value | LPAREN pragma_value RPAREN )?
            int alt45=3;
            int LA45_0 = input.LA(1);

            if ( (LA45_0==EQUALS) ) {
                alt45=1;
            }
            else if ( (LA45_0==LPAREN) ) {
                alt45=2;
            }
            switch (alt45) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:214:61: EQUALS pragma_value
                    {
                    EQUALS150=(Token)match(input,EQUALS,FOLLOW_EQUALS_in_pragma_stmt1479);  
                    stream_EQUALS.add(EQUALS150);

                    pushFollow(FOLLOW_pragma_value_in_pragma_stmt1481);
                    pragma_value151=pragma_value();

                    state._fsp--;

                    stream_pragma_value.add(pragma_value151.getTree());

                    }
                    break;
                case 2 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:214:83: LPAREN pragma_value RPAREN
                    {
                    LPAREN152=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_pragma_stmt1485);  
                    stream_LPAREN.add(LPAREN152);

                    pushFollow(FOLLOW_pragma_value_in_pragma_stmt1487);
                    pragma_value153=pragma_value();

                    state._fsp--;

                    stream_pragma_value.add(pragma_value153.getTree());
                    RPAREN154=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_pragma_stmt1489);  
                    stream_RPAREN.add(RPAREN154);


                    }
                    break;

            }



            // AST REWRITE
            // elements: PRAGMA, pragma_name, pragma_value, database_name
            // token labels: 
            // rule labels: database_name, retval, pragma_name
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_database_name=new RewriteRuleSubtreeStream(adaptor,"rule database_name",database_name!=null?database_name.tree:null);
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_pragma_name=new RewriteRuleSubtreeStream(adaptor,"rule pragma_name",pragma_name!=null?pragma_name.tree:null);

            root_0 = (Object)adaptor.nil();
            // 215:1: -> ^( PRAGMA ^( $pragma_name ( $database_name)? ) ( pragma_value )? )
            {
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:215:4: ^( PRAGMA ^( $pragma_name ( $database_name)? ) ( pragma_value )? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(stream_PRAGMA.nextNode(), root_1);

                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:215:13: ^( $pragma_name ( $database_name)? )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot(stream_pragma_name.nextNode(), root_2);

                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:215:28: ( $database_name)?
                if ( stream_database_name.hasNext() ) {
                    adaptor.addChild(root_2, stream_database_name.nextTree());

                }
                stream_database_name.reset();

                adaptor.addChild(root_1, root_2);
                }
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:215:45: ( pragma_value )?
                if ( stream_pragma_value.hasNext() ) {
                    adaptor.addChild(root_1, stream_pragma_value.nextTree());

                }
                stream_pragma_value.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "pragma_stmt"

    public static class pragma_value_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "pragma_value"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:217:1: pragma_value : ( signed_number -> ^( FLOAT_LITERAL signed_number ) | id -> ^( ID_LITERAL id ) | STRING -> ^( STRING_LITERAL STRING ) );
    public final SqlParser.pragma_value_return pragma_value() throws RecognitionException {
        SqlParser.pragma_value_return retval = new SqlParser.pragma_value_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token STRING157=null;
        SqlParser.signed_number_return signed_number155 = null;

        SqlParser.id_return id156 = null;


        Object STRING157_tree=null;
        RewriteRuleTokenStream stream_STRING=new RewriteRuleTokenStream(adaptor,"token STRING");
        RewriteRuleSubtreeStream stream_id=new RewriteRuleSubtreeStream(adaptor,"rule id");
        RewriteRuleSubtreeStream stream_signed_number=new RewriteRuleSubtreeStream(adaptor,"rule signed_number");
        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:218:2: ( signed_number -> ^( FLOAT_LITERAL signed_number ) | id -> ^( ID_LITERAL id ) | STRING -> ^( STRING_LITERAL STRING ) )
            int alt46=3;
            switch ( input.LA(1) ) {
            case PLUS:
            case MINUS:
            case INTEGER:
            case FLOAT:
                {
                alt46=1;
                }
                break;
            case EXPLAIN:
            case QUERY:
            case PLAN:
            case INDEXED:
            case BY:
            case OR:
            case AND:
            case ESCAPE:
            case IS:
            case NULL:
            case BETWEEN:
            case COLLATE:
            case ID:
            case DISTINCT:
            case CAST:
            case AS:
            case CASE:
            case ELSE:
            case END:
            case WHEN:
            case THEN:
            case CURRENT_TIME:
            case CURRENT_DATE:
            case CURRENT_TIMESTAMP:
            case RAISE:
            case IGNORE:
            case ROLLBACK:
            case ABORT:
            case FAIL:
            case PRAGMA:
            case ATTACH:
            case DATABASE:
            case DETACH:
            case ANALYZE:
            case REINDEX:
            case VACUUM:
            case REPLACE:
            case ASC:
            case DESC:
            case ORDER:
            case LIMIT:
            case OFFSET:
            case UNION:
            case ALL:
            case INTERSECT:
            case EXCEPT:
            case SELECT:
            case FROM:
            case WHERE:
            case GROUP:
            case HAVING:
            case NATURAL:
            case LEFT:
            case OUTER:
            case INNER:
            case CROSS:
            case JOIN:
            case ON:
            case USING:
            case INSERT:
            case INTO:
            case VALUES:
            case DEFAULT:
            case UPDATE:
            case SET:
            case DELETE:
            case BEGIN:
            case DEFERRED:
            case IMMEDIATE:
            case EXCLUSIVE:
            case TRANSACTION:
            case COMMIT:
            case TO:
            case SAVEPOINT:
            case RELEASE:
            case CONFLICT:
            case CREATE:
            case VIRTUAL:
            case TABLE:
            case TEMPORARY:
            case IF:
            case EXISTS:
            case CONSTRAINT:
            case PRIMARY:
            case KEY:
            case AUTOINCREMENT:
            case FOR:
            case UNIQUE:
            case CHECK:
            case FOREIGN:
            case REFERENCES:
            case CASCADE:
            case RESTRICT:
            case DEFERRABLE:
            case INITIALLY:
            case DROP:
            case ALTER:
            case RENAME:
            case ADD:
            case COLUMN:
            case VIEW:
            case INDEX:
            case TRIGGER:
            case BEFORE:
            case AFTER:
            case INSTEAD:
            case OF:
            case EACH:
            case ROW:
            case META_COMMENT:
            case SCHEMA:
                {
                alt46=2;
                }
                break;
            case STRING:
                {
                alt46=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 46, 0, input);

                throw nvae;
            }

            switch (alt46) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:218:4: signed_number
                    {
                    pushFollow(FOLLOW_signed_number_in_pragma_value1518);
                    signed_number155=signed_number();

                    state._fsp--;

                    stream_signed_number.add(signed_number155.getTree());


                    // AST REWRITE
                    // elements: signed_number
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 218:18: -> ^( FLOAT_LITERAL signed_number )
                    {
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:218:21: ^( FLOAT_LITERAL signed_number )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(FLOAT_LITERAL, "FLOAT_LITERAL"), root_1);

                        adaptor.addChild(root_1, stream_signed_number.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:219:4: id
                    {
                    pushFollow(FOLLOW_id_in_pragma_value1531);
                    id156=id();

                    state._fsp--;

                    stream_id.add(id156.getTree());


                    // AST REWRITE
                    // elements: id
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 219:7: -> ^( ID_LITERAL id )
                    {
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:219:10: ^( ID_LITERAL id )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(ID_LITERAL, "ID_LITERAL"), root_1);

                        adaptor.addChild(root_1, stream_id.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 3 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:220:4: STRING
                    {
                    STRING157=(Token)match(input,STRING,FOLLOW_STRING_in_pragma_value1544);  
                    stream_STRING.add(STRING157);



                    // AST REWRITE
                    // elements: STRING
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 220:11: -> ^( STRING_LITERAL STRING )
                    {
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:220:14: ^( STRING_LITERAL STRING )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(STRING_LITERAL, "STRING_LITERAL"), root_1);

                        adaptor.addChild(root_1, stream_STRING.nextNode());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "pragma_value"

    public static class attach_stmt_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "attach_stmt"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:224:1: attach_stmt : ATTACH ( DATABASE )? filename= ( STRING | id ) AS database_name= id ;
    public final SqlParser.attach_stmt_return attach_stmt() throws RecognitionException {
        SqlParser.attach_stmt_return retval = new SqlParser.attach_stmt_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token filename=null;
        Token ATTACH158=null;
        Token DATABASE159=null;
        Token STRING160=null;
        Token AS162=null;
        SqlParser.id_return database_name = null;

        SqlParser.id_return id161 = null;


        Object filename_tree=null;
        Object ATTACH158_tree=null;
        Object DATABASE159_tree=null;
        Object STRING160_tree=null;
        Object AS162_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:224:12: ( ATTACH ( DATABASE )? filename= ( STRING | id ) AS database_name= id )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:224:14: ATTACH ( DATABASE )? filename= ( STRING | id ) AS database_name= id
            {
            root_0 = (Object)adaptor.nil();

            ATTACH158=(Token)match(input,ATTACH,FOLLOW_ATTACH_in_attach_stmt1562); 
            ATTACH158_tree = (Object)adaptor.create(ATTACH158);
            adaptor.addChild(root_0, ATTACH158_tree);

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:224:21: ( DATABASE )?
            int alt47=2;
            alt47 = dfa47.predict(input);
            switch (alt47) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:224:22: DATABASE
                    {
                    DATABASE159=(Token)match(input,DATABASE,FOLLOW_DATABASE_in_attach_stmt1565); 
                    DATABASE159_tree = (Object)adaptor.create(DATABASE159);
                    adaptor.addChild(root_0, DATABASE159_tree);


                    }
                    break;

            }

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:224:42: ( STRING | id )
            int alt48=2;
            int LA48_0 = input.LA(1);

            if ( (LA48_0==STRING) ) {
                alt48=1;
            }
            else if ( ((LA48_0>=EXPLAIN && LA48_0<=PLAN)||(LA48_0>=INDEXED && LA48_0<=BY)||(LA48_0>=OR && LA48_0<=ESCAPE)||(LA48_0>=IS && LA48_0<=BETWEEN)||(LA48_0>=COLLATE && LA48_0<=THEN)||(LA48_0>=CURRENT_TIME && LA48_0<=CURRENT_TIMESTAMP)||(LA48_0>=RAISE && LA48_0<=FAIL)||(LA48_0>=PRAGMA && LA48_0<=FOR)||(LA48_0>=UNIQUE && LA48_0<=ALTER)||(LA48_0>=RENAME && LA48_0<=SCHEMA)) ) {
                alt48=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 48, 0, input);

                throw nvae;
            }
            switch (alt48) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:224:43: STRING
                    {
                    STRING160=(Token)match(input,STRING,FOLLOW_STRING_in_attach_stmt1572); 
                    STRING160_tree = (Object)adaptor.create(STRING160);
                    adaptor.addChild(root_0, STRING160_tree);


                    }
                    break;
                case 2 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:224:52: id
                    {
                    pushFollow(FOLLOW_id_in_attach_stmt1576);
                    id161=id();

                    state._fsp--;

                    adaptor.addChild(root_0, id161.getTree());

                    }
                    break;

            }

            AS162=(Token)match(input,AS,FOLLOW_AS_in_attach_stmt1579); 
            AS162_tree = (Object)adaptor.create(AS162);
            adaptor.addChild(root_0, AS162_tree);

            pushFollow(FOLLOW_id_in_attach_stmt1583);
            database_name=id();

            state._fsp--;

            adaptor.addChild(root_0, database_name.getTree());

            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "attach_stmt"

    public static class detach_stmt_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "detach_stmt"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:227:1: detach_stmt : DETACH ( DATABASE )? database_name= id ;
    public final SqlParser.detach_stmt_return detach_stmt() throws RecognitionException {
        SqlParser.detach_stmt_return retval = new SqlParser.detach_stmt_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token DETACH163=null;
        Token DATABASE164=null;
        SqlParser.id_return database_name = null;


        Object DETACH163_tree=null;
        Object DATABASE164_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:227:12: ( DETACH ( DATABASE )? database_name= id )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:227:14: DETACH ( DATABASE )? database_name= id
            {
            root_0 = (Object)adaptor.nil();

            DETACH163=(Token)match(input,DETACH,FOLLOW_DETACH_in_detach_stmt1591); 
            DETACH163_tree = (Object)adaptor.create(DETACH163);
            adaptor.addChild(root_0, DETACH163_tree);

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:227:21: ( DATABASE )?
            int alt49=2;
            int LA49_0 = input.LA(1);

            if ( (LA49_0==DATABASE) ) {
                int LA49_1 = input.LA(2);

                if ( ((LA49_1>=EXPLAIN && LA49_1<=PLAN)||(LA49_1>=INDEXED && LA49_1<=BY)||(LA49_1>=OR && LA49_1<=ESCAPE)||(LA49_1>=IS && LA49_1<=BETWEEN)||(LA49_1>=COLLATE && LA49_1<=THEN)||(LA49_1>=CURRENT_TIME && LA49_1<=CURRENT_TIMESTAMP)||(LA49_1>=RAISE && LA49_1<=FAIL)||(LA49_1>=PRAGMA && LA49_1<=FOR)||(LA49_1>=UNIQUE && LA49_1<=ALTER)||(LA49_1>=RENAME && LA49_1<=SCHEMA)) ) {
                    alt49=1;
                }
            }
            switch (alt49) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:227:22: DATABASE
                    {
                    DATABASE164=(Token)match(input,DATABASE,FOLLOW_DATABASE_in_detach_stmt1594); 
                    DATABASE164_tree = (Object)adaptor.create(DATABASE164);
                    adaptor.addChild(root_0, DATABASE164_tree);


                    }
                    break;

            }

            pushFollow(FOLLOW_id_in_detach_stmt1600);
            database_name=id();

            state._fsp--;

            adaptor.addChild(root_0, database_name.getTree());

            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "detach_stmt"

    public static class analyze_stmt_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "analyze_stmt"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:230:1: analyze_stmt : ANALYZE (database_or_table_name= id | database_name= id DOT table_name= id )? ;
    public final SqlParser.analyze_stmt_return analyze_stmt() throws RecognitionException {
        SqlParser.analyze_stmt_return retval = new SqlParser.analyze_stmt_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ANALYZE165=null;
        Token DOT166=null;
        SqlParser.id_return database_or_table_name = null;

        SqlParser.id_return database_name = null;

        SqlParser.id_return table_name = null;


        Object ANALYZE165_tree=null;
        Object DOT166_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:230:13: ( ANALYZE (database_or_table_name= id | database_name= id DOT table_name= id )? )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:230:15: ANALYZE (database_or_table_name= id | database_name= id DOT table_name= id )?
            {
            root_0 = (Object)adaptor.nil();

            ANALYZE165=(Token)match(input,ANALYZE,FOLLOW_ANALYZE_in_analyze_stmt1608); 
            ANALYZE165_tree = (Object)adaptor.create(ANALYZE165);
            adaptor.addChild(root_0, ANALYZE165_tree);

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:230:23: (database_or_table_name= id | database_name= id DOT table_name= id )?
            int alt50=3;
            int LA50_0 = input.LA(1);

            if ( (LA50_0==ID) ) {
                int LA50_1 = input.LA(2);

                if ( (LA50_1==DOT) ) {
                    alt50=2;
                }
                else if ( (LA50_1==SEMI) ) {
                    alt50=1;
                }
            }
            else if ( ((LA50_0>=EXPLAIN && LA50_0<=PLAN)||(LA50_0>=INDEXED && LA50_0<=BY)||(LA50_0>=OR && LA50_0<=ESCAPE)||(LA50_0>=IS && LA50_0<=BETWEEN)||LA50_0==COLLATE||(LA50_0>=DISTINCT && LA50_0<=THEN)||(LA50_0>=CURRENT_TIME && LA50_0<=CURRENT_TIMESTAMP)||(LA50_0>=RAISE && LA50_0<=FAIL)||(LA50_0>=PRAGMA && LA50_0<=FOR)||(LA50_0>=UNIQUE && LA50_0<=ALTER)||(LA50_0>=RENAME && LA50_0<=SCHEMA)) ) {
                int LA50_2 = input.LA(2);

                if ( (LA50_2==SEMI) ) {
                    alt50=1;
                }
                else if ( (LA50_2==DOT) ) {
                    alt50=2;
                }
            }
            switch (alt50) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:230:24: database_or_table_name= id
                    {
                    pushFollow(FOLLOW_id_in_analyze_stmt1613);
                    database_or_table_name=id();

                    state._fsp--;

                    adaptor.addChild(root_0, database_or_table_name.getTree());

                    }
                    break;
                case 2 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:230:52: database_name= id DOT table_name= id
                    {
                    pushFollow(FOLLOW_id_in_analyze_stmt1619);
                    database_name=id();

                    state._fsp--;

                    adaptor.addChild(root_0, database_name.getTree());
                    DOT166=(Token)match(input,DOT,FOLLOW_DOT_in_analyze_stmt1621); 
                    DOT166_tree = (Object)adaptor.create(DOT166);
                    adaptor.addChild(root_0, DOT166_tree);

                    pushFollow(FOLLOW_id_in_analyze_stmt1625);
                    table_name=id();

                    state._fsp--;

                    adaptor.addChild(root_0, table_name.getTree());

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "analyze_stmt"

    public static class reindex_stmt_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "reindex_stmt"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:233:1: reindex_stmt : REINDEX (database_name= id DOT )? collation_or_table_or_index_name= id ;
    public final SqlParser.reindex_stmt_return reindex_stmt() throws RecognitionException {
        SqlParser.reindex_stmt_return retval = new SqlParser.reindex_stmt_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token REINDEX167=null;
        Token DOT168=null;
        SqlParser.id_return database_name = null;

        SqlParser.id_return collation_or_table_or_index_name = null;


        Object REINDEX167_tree=null;
        Object DOT168_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:233:13: ( REINDEX (database_name= id DOT )? collation_or_table_or_index_name= id )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:233:15: REINDEX (database_name= id DOT )? collation_or_table_or_index_name= id
            {
            root_0 = (Object)adaptor.nil();

            REINDEX167=(Token)match(input,REINDEX,FOLLOW_REINDEX_in_reindex_stmt1635); 
            REINDEX167_tree = (Object)adaptor.create(REINDEX167);
            adaptor.addChild(root_0, REINDEX167_tree);

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:233:23: (database_name= id DOT )?
            int alt51=2;
            int LA51_0 = input.LA(1);

            if ( (LA51_0==ID) ) {
                int LA51_1 = input.LA(2);

                if ( (LA51_1==DOT) ) {
                    alt51=1;
                }
            }
            else if ( ((LA51_0>=EXPLAIN && LA51_0<=PLAN)||(LA51_0>=INDEXED && LA51_0<=BY)||(LA51_0>=OR && LA51_0<=ESCAPE)||(LA51_0>=IS && LA51_0<=BETWEEN)||LA51_0==COLLATE||(LA51_0>=DISTINCT && LA51_0<=THEN)||(LA51_0>=CURRENT_TIME && LA51_0<=CURRENT_TIMESTAMP)||(LA51_0>=RAISE && LA51_0<=FAIL)||(LA51_0>=PRAGMA && LA51_0<=FOR)||(LA51_0>=UNIQUE && LA51_0<=ALTER)||(LA51_0>=RENAME && LA51_0<=SCHEMA)) ) {
                int LA51_2 = input.LA(2);

                if ( (LA51_2==DOT) ) {
                    alt51=1;
                }
            }
            switch (alt51) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:233:24: database_name= id DOT
                    {
                    pushFollow(FOLLOW_id_in_reindex_stmt1640);
                    database_name=id();

                    state._fsp--;

                    adaptor.addChild(root_0, database_name.getTree());
                    DOT168=(Token)match(input,DOT,FOLLOW_DOT_in_reindex_stmt1642); 
                    DOT168_tree = (Object)adaptor.create(DOT168);
                    adaptor.addChild(root_0, DOT168_tree);


                    }
                    break;

            }

            pushFollow(FOLLOW_id_in_reindex_stmt1648);
            collation_or_table_or_index_name=id();

            state._fsp--;

            adaptor.addChild(root_0, collation_or_table_or_index_name.getTree());

            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "reindex_stmt"

    public static class vacuum_stmt_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "vacuum_stmt"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:236:1: vacuum_stmt : VACUUM ;
    public final SqlParser.vacuum_stmt_return vacuum_stmt() throws RecognitionException {
        SqlParser.vacuum_stmt_return retval = new SqlParser.vacuum_stmt_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token VACUUM169=null;

        Object VACUUM169_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:236:12: ( VACUUM )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:236:14: VACUUM
            {
            root_0 = (Object)adaptor.nil();

            VACUUM169=(Token)match(input,VACUUM,FOLLOW_VACUUM_in_vacuum_stmt1656); 
            VACUUM169_tree = (Object)adaptor.create(VACUUM169);
            adaptor.addChild(root_0, VACUUM169_tree);


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "vacuum_stmt"

    public static class operation_conflict_clause_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "operation_conflict_clause"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:242:1: operation_conflict_clause : OR ( ROLLBACK | ABORT | FAIL | IGNORE | REPLACE ) ;
    public final SqlParser.operation_conflict_clause_return operation_conflict_clause() throws RecognitionException {
        SqlParser.operation_conflict_clause_return retval = new SqlParser.operation_conflict_clause_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token OR170=null;
        Token set171=null;

        Object OR170_tree=null;
        Object set171_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:242:26: ( OR ( ROLLBACK | ABORT | FAIL | IGNORE | REPLACE ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:242:28: OR ( ROLLBACK | ABORT | FAIL | IGNORE | REPLACE )
            {
            root_0 = (Object)adaptor.nil();

            OR170=(Token)match(input,OR,FOLLOW_OR_in_operation_conflict_clause1667); 
            OR170_tree = (Object)adaptor.create(OR170);
            adaptor.addChild(root_0, OR170_tree);

            set171=(Token)input.LT(1);
            if ( (input.LA(1)>=IGNORE && input.LA(1)<=FAIL)||input.LA(1)==REPLACE ) {
                input.consume();
                adaptor.addChild(root_0, (Object)adaptor.create(set171));
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "operation_conflict_clause"

    public static class ordering_term_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "ordering_term"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:244:1: ordering_term : expr ( ASC | DESC )? -> ^( ORDERING expr ( ASC )? ( DESC )? ) ;
    public final SqlParser.ordering_term_return ordering_term() throws RecognitionException {
        SqlParser.ordering_term_return retval = new SqlParser.ordering_term_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ASC173=null;
        Token DESC174=null;
        SqlParser.expr_return expr172 = null;


        Object ASC173_tree=null;
        Object DESC174_tree=null;
        RewriteRuleTokenStream stream_ASC=new RewriteRuleTokenStream(adaptor,"token ASC");
        RewriteRuleTokenStream stream_DESC=new RewriteRuleTokenStream(adaptor,"token DESC");
        RewriteRuleSubtreeStream stream_expr=new RewriteRuleSubtreeStream(adaptor,"rule expr");
        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:244:14: ( expr ( ASC | DESC )? -> ^( ORDERING expr ( ASC )? ( DESC )? ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:244:16: expr ( ASC | DESC )?
            {
            pushFollow(FOLLOW_expr_in_ordering_term1694);
            expr172=expr();

            state._fsp--;

            stream_expr.add(expr172.getTree());
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:244:82: ( ASC | DESC )?
            int alt52=3;
            alt52 = dfa52.predict(input);
            switch (alt52) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:244:83: ASC
                    {
                    ASC173=(Token)match(input,ASC,FOLLOW_ASC_in_ordering_term1699);  
                    stream_ASC.add(ASC173);


                    }
                    break;
                case 2 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:244:89: DESC
                    {
                    DESC174=(Token)match(input,DESC,FOLLOW_DESC_in_ordering_term1703);  
                    stream_DESC.add(DESC174);


                    }
                    break;

            }



            // AST REWRITE
            // elements: expr, DESC, ASC
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 245:1: -> ^( ORDERING expr ( ASC )? ( DESC )? )
            {
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:245:4: ^( ORDERING expr ( ASC )? ( DESC )? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(ORDERING, "ORDERING"), root_1);

                adaptor.addChild(root_1, stream_expr.nextTree());
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:245:20: ( ASC )?
                if ( stream_ASC.hasNext() ) {
                    adaptor.addChild(root_1, stream_ASC.nextNode());

                }
                stream_ASC.reset();
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:245:27: ( DESC )?
                if ( stream_DESC.hasNext() ) {
                    adaptor.addChild(root_1, stream_DESC.nextNode());

                }
                stream_DESC.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "ordering_term"

    public static class operation_limited_clause_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "operation_limited_clause"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:247:1: operation_limited_clause : ( ORDER BY ordering_term ( COMMA ordering_term )* )? LIMIT limit= INTEGER ( ( OFFSET | COMMA ) offset= INTEGER )? ;
    public final SqlParser.operation_limited_clause_return operation_limited_clause() throws RecognitionException {
        SqlParser.operation_limited_clause_return retval = new SqlParser.operation_limited_clause_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token limit=null;
        Token offset=null;
        Token ORDER175=null;
        Token BY176=null;
        Token COMMA178=null;
        Token LIMIT180=null;
        Token set181=null;
        SqlParser.ordering_term_return ordering_term177 = null;

        SqlParser.ordering_term_return ordering_term179 = null;


        Object limit_tree=null;
        Object offset_tree=null;
        Object ORDER175_tree=null;
        Object BY176_tree=null;
        Object COMMA178_tree=null;
        Object LIMIT180_tree=null;
        Object set181_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:247:25: ( ( ORDER BY ordering_term ( COMMA ordering_term )* )? LIMIT limit= INTEGER ( ( OFFSET | COMMA ) offset= INTEGER )? )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:248:3: ( ORDER BY ordering_term ( COMMA ordering_term )* )? LIMIT limit= INTEGER ( ( OFFSET | COMMA ) offset= INTEGER )?
            {
            root_0 = (Object)adaptor.nil();

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:248:3: ( ORDER BY ordering_term ( COMMA ordering_term )* )?
            int alt54=2;
            int LA54_0 = input.LA(1);

            if ( (LA54_0==ORDER) ) {
                alt54=1;
            }
            switch (alt54) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:248:4: ORDER BY ordering_term ( COMMA ordering_term )*
                    {
                    ORDER175=(Token)match(input,ORDER,FOLLOW_ORDER_in_operation_limited_clause1733); 
                    ORDER175_tree = (Object)adaptor.create(ORDER175);
                    adaptor.addChild(root_0, ORDER175_tree);

                    BY176=(Token)match(input,BY,FOLLOW_BY_in_operation_limited_clause1735); 
                    BY176_tree = (Object)adaptor.create(BY176);
                    adaptor.addChild(root_0, BY176_tree);

                    pushFollow(FOLLOW_ordering_term_in_operation_limited_clause1737);
                    ordering_term177=ordering_term();

                    state._fsp--;

                    adaptor.addChild(root_0, ordering_term177.getTree());
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:248:27: ( COMMA ordering_term )*
                    loop53:
                    do {
                        int alt53=2;
                        int LA53_0 = input.LA(1);

                        if ( (LA53_0==COMMA) ) {
                            alt53=1;
                        }


                        switch (alt53) {
                    	case 1 :
                    	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:248:28: COMMA ordering_term
                    	    {
                    	    COMMA178=(Token)match(input,COMMA,FOLLOW_COMMA_in_operation_limited_clause1740); 
                    	    COMMA178_tree = (Object)adaptor.create(COMMA178);
                    	    adaptor.addChild(root_0, COMMA178_tree);

                    	    pushFollow(FOLLOW_ordering_term_in_operation_limited_clause1742);
                    	    ordering_term179=ordering_term();

                    	    state._fsp--;

                    	    adaptor.addChild(root_0, ordering_term179.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop53;
                        }
                    } while (true);


                    }
                    break;

            }

            LIMIT180=(Token)match(input,LIMIT,FOLLOW_LIMIT_in_operation_limited_clause1750); 
            LIMIT180_tree = (Object)adaptor.create(LIMIT180);
            adaptor.addChild(root_0, LIMIT180_tree);

            limit=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_operation_limited_clause1754); 
            limit_tree = (Object)adaptor.create(limit);
            adaptor.addChild(root_0, limit_tree);

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:249:23: ( ( OFFSET | COMMA ) offset= INTEGER )?
            int alt55=2;
            int LA55_0 = input.LA(1);

            if ( (LA55_0==COMMA||LA55_0==OFFSET) ) {
                alt55=1;
            }
            switch (alt55) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:249:24: ( OFFSET | COMMA ) offset= INTEGER
                    {
                    set181=(Token)input.LT(1);
                    if ( input.LA(1)==COMMA||input.LA(1)==OFFSET ) {
                        input.consume();
                        adaptor.addChild(root_0, (Object)adaptor.create(set181));
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    offset=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_operation_limited_clause1767); 
                    offset_tree = (Object)adaptor.create(offset);
                    adaptor.addChild(root_0, offset_tree);


                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "operation_limited_clause"

    public static class select_stmt_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "select_stmt"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:252:1: select_stmt : select_list ( ORDER BY ordering_term ( COMMA ordering_term )* )? ( LIMIT limit= INTEGER ( ( OFFSET | COMMA ) offset= INTEGER )? )? -> ^( SELECT select_list ( ^( ORDER ( ordering_term )+ ) )? ( ^( LIMIT $limit ( $offset)? ) )? ) ;
    public final SqlParser.select_stmt_return select_stmt() throws RecognitionException {
        SqlParser.select_stmt_return retval = new SqlParser.select_stmt_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token limit=null;
        Token offset=null;
        Token ORDER183=null;
        Token BY184=null;
        Token COMMA186=null;
        Token LIMIT188=null;
        Token OFFSET189=null;
        Token COMMA190=null;
        SqlParser.select_list_return select_list182 = null;

        SqlParser.ordering_term_return ordering_term185 = null;

        SqlParser.ordering_term_return ordering_term187 = null;


        Object limit_tree=null;
        Object offset_tree=null;
        Object ORDER183_tree=null;
        Object BY184_tree=null;
        Object COMMA186_tree=null;
        Object LIMIT188_tree=null;
        Object OFFSET189_tree=null;
        Object COMMA190_tree=null;
        RewriteRuleTokenStream stream_INTEGER=new RewriteRuleTokenStream(adaptor,"token INTEGER");
        RewriteRuleTokenStream stream_BY=new RewriteRuleTokenStream(adaptor,"token BY");
        RewriteRuleTokenStream stream_ORDER=new RewriteRuleTokenStream(adaptor,"token ORDER");
        RewriteRuleTokenStream stream_COMMA=new RewriteRuleTokenStream(adaptor,"token COMMA");
        RewriteRuleTokenStream stream_LIMIT=new RewriteRuleTokenStream(adaptor,"token LIMIT");
        RewriteRuleTokenStream stream_OFFSET=new RewriteRuleTokenStream(adaptor,"token OFFSET");
        RewriteRuleSubtreeStream stream_select_list=new RewriteRuleSubtreeStream(adaptor,"rule select_list");
        RewriteRuleSubtreeStream stream_ordering_term=new RewriteRuleSubtreeStream(adaptor,"rule ordering_term");
        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:252:12: ( select_list ( ORDER BY ordering_term ( COMMA ordering_term )* )? ( LIMIT limit= INTEGER ( ( OFFSET | COMMA ) offset= INTEGER )? )? -> ^( SELECT select_list ( ^( ORDER ( ordering_term )+ ) )? ( ^( LIMIT $limit ( $offset)? ) )? ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:252:14: select_list ( ORDER BY ordering_term ( COMMA ordering_term )* )? ( LIMIT limit= INTEGER ( ( OFFSET | COMMA ) offset= INTEGER )? )?
            {
            pushFollow(FOLLOW_select_list_in_select_stmt1777);
            select_list182=select_list();

            state._fsp--;

            stream_select_list.add(select_list182.getTree());
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:253:3: ( ORDER BY ordering_term ( COMMA ordering_term )* )?
            int alt57=2;
            int LA57_0 = input.LA(1);

            if ( (LA57_0==ORDER) ) {
                alt57=1;
            }
            switch (alt57) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:253:4: ORDER BY ordering_term ( COMMA ordering_term )*
                    {
                    ORDER183=(Token)match(input,ORDER,FOLLOW_ORDER_in_select_stmt1782);  
                    stream_ORDER.add(ORDER183);

                    BY184=(Token)match(input,BY,FOLLOW_BY_in_select_stmt1784);  
                    stream_BY.add(BY184);

                    pushFollow(FOLLOW_ordering_term_in_select_stmt1786);
                    ordering_term185=ordering_term();

                    state._fsp--;

                    stream_ordering_term.add(ordering_term185.getTree());
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:253:27: ( COMMA ordering_term )*
                    loop56:
                    do {
                        int alt56=2;
                        int LA56_0 = input.LA(1);

                        if ( (LA56_0==COMMA) ) {
                            alt56=1;
                        }


                        switch (alt56) {
                    	case 1 :
                    	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:253:28: COMMA ordering_term
                    	    {
                    	    COMMA186=(Token)match(input,COMMA,FOLLOW_COMMA_in_select_stmt1789);  
                    	    stream_COMMA.add(COMMA186);

                    	    pushFollow(FOLLOW_ordering_term_in_select_stmt1791);
                    	    ordering_term187=ordering_term();

                    	    state._fsp--;

                    	    stream_ordering_term.add(ordering_term187.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop56;
                        }
                    } while (true);


                    }
                    break;

            }

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:254:3: ( LIMIT limit= INTEGER ( ( OFFSET | COMMA ) offset= INTEGER )? )?
            int alt60=2;
            int LA60_0 = input.LA(1);

            if ( (LA60_0==LIMIT) ) {
                alt60=1;
            }
            switch (alt60) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:254:4: LIMIT limit= INTEGER ( ( OFFSET | COMMA ) offset= INTEGER )?
                    {
                    LIMIT188=(Token)match(input,LIMIT,FOLLOW_LIMIT_in_select_stmt1800);  
                    stream_LIMIT.add(LIMIT188);

                    limit=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_select_stmt1804);  
                    stream_INTEGER.add(limit);

                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:254:24: ( ( OFFSET | COMMA ) offset= INTEGER )?
                    int alt59=2;
                    int LA59_0 = input.LA(1);

                    if ( (LA59_0==COMMA||LA59_0==OFFSET) ) {
                        alt59=1;
                    }
                    switch (alt59) {
                        case 1 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:254:25: ( OFFSET | COMMA ) offset= INTEGER
                            {
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:254:25: ( OFFSET | COMMA )
                            int alt58=2;
                            int LA58_0 = input.LA(1);

                            if ( (LA58_0==OFFSET) ) {
                                alt58=1;
                            }
                            else if ( (LA58_0==COMMA) ) {
                                alt58=2;
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 58, 0, input);

                                throw nvae;
                            }
                            switch (alt58) {
                                case 1 :
                                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:254:26: OFFSET
                                    {
                                    OFFSET189=(Token)match(input,OFFSET,FOLLOW_OFFSET_in_select_stmt1808);  
                                    stream_OFFSET.add(OFFSET189);


                                    }
                                    break;
                                case 2 :
                                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:254:35: COMMA
                                    {
                                    COMMA190=(Token)match(input,COMMA,FOLLOW_COMMA_in_select_stmt1812);  
                                    stream_COMMA.add(COMMA190);


                                    }
                                    break;

                            }

                            offset=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_select_stmt1817);  
                            stream_INTEGER.add(offset);


                            }
                            break;

                    }


                    }
                    break;

            }



            // AST REWRITE
            // elements: ORDER, LIMIT, offset, limit, select_list, ordering_term
            // token labels: limit, offset
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleTokenStream stream_limit=new RewriteRuleTokenStream(adaptor,"token limit",limit);
            RewriteRuleTokenStream stream_offset=new RewriteRuleTokenStream(adaptor,"token offset",offset);
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 255:1: -> ^( SELECT select_list ( ^( ORDER ( ordering_term )+ ) )? ( ^( LIMIT $limit ( $offset)? ) )? )
            {
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:255:4: ^( SELECT select_list ( ^( ORDER ( ordering_term )+ ) )? ( ^( LIMIT $limit ( $offset)? ) )? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(SELECT, "SELECT"), root_1);

                adaptor.addChild(root_1, stream_select_list.nextTree());
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:256:22: ( ^( ORDER ( ordering_term )+ ) )?
                if ( stream_ORDER.hasNext()||stream_ordering_term.hasNext() ) {
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:256:22: ^( ORDER ( ordering_term )+ )
                    {
                    Object root_2 = (Object)adaptor.nil();
                    root_2 = (Object)adaptor.becomeRoot(stream_ORDER.nextNode(), root_2);

                    if ( !(stream_ordering_term.hasNext()) ) {
                        throw new RewriteEarlyExitException();
                    }
                    while ( stream_ordering_term.hasNext() ) {
                        adaptor.addChild(root_2, stream_ordering_term.nextTree());

                    }
                    stream_ordering_term.reset();

                    adaptor.addChild(root_1, root_2);
                    }

                }
                stream_ORDER.reset();
                stream_ordering_term.reset();
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:256:47: ( ^( LIMIT $limit ( $offset)? ) )?
                if ( stream_LIMIT.hasNext()||stream_offset.hasNext()||stream_limit.hasNext() ) {
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:256:47: ^( LIMIT $limit ( $offset)? )
                    {
                    Object root_2 = (Object)adaptor.nil();
                    root_2 = (Object)adaptor.becomeRoot(stream_LIMIT.nextNode(), root_2);

                    adaptor.addChild(root_2, stream_limit.nextNode());
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:256:62: ( $offset)?
                    if ( stream_offset.hasNext() ) {
                        adaptor.addChild(root_2, stream_offset.nextNode());

                    }
                    stream_offset.reset();

                    adaptor.addChild(root_1, root_2);
                    }

                }
                stream_LIMIT.reset();
                stream_offset.reset();
                stream_limit.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "select_stmt"

    public static class select_list_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "select_list"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:259:1: select_list : select_core ( select_op select_core )* ;
    public final SqlParser.select_list_return select_list() throws RecognitionException {
        SqlParser.select_list_return retval = new SqlParser.select_list_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        SqlParser.select_core_return select_core191 = null;

        SqlParser.select_op_return select_op192 = null;

        SqlParser.select_core_return select_core193 = null;



        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:259:12: ( select_core ( select_op select_core )* )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:260:3: select_core ( select_op select_core )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_select_core_in_select_list1862);
            select_core191=select_core();

            state._fsp--;

            adaptor.addChild(root_0, select_core191.getTree());
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:260:15: ( select_op select_core )*
            loop61:
            do {
                int alt61=2;
                int LA61_0 = input.LA(1);

                if ( (LA61_0==UNION||(LA61_0>=INTERSECT && LA61_0<=EXCEPT)) ) {
                    alt61=1;
                }


                switch (alt61) {
            	case 1 :
            	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:260:16: select_op select_core
            	    {
            	    pushFollow(FOLLOW_select_op_in_select_list1865);
            	    select_op192=select_op();

            	    state._fsp--;

            	    root_0 = (Object)adaptor.becomeRoot(select_op192.getTree(), root_0);
            	    pushFollow(FOLLOW_select_core_in_select_list1868);
            	    select_core193=select_core();

            	    state._fsp--;

            	    adaptor.addChild(root_0, select_core193.getTree());

            	    }
            	    break;

            	default :
            	    break loop61;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "select_list"

    public static class select_op_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "select_op"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:262:1: select_op : ( UNION ( ALL )? | INTERSECT | EXCEPT );
    public final SqlParser.select_op_return select_op() throws RecognitionException {
        SqlParser.select_op_return retval = new SqlParser.select_op_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token UNION194=null;
        Token ALL195=null;
        Token INTERSECT196=null;
        Token EXCEPT197=null;

        Object UNION194_tree=null;
        Object ALL195_tree=null;
        Object INTERSECT196_tree=null;
        Object EXCEPT197_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:262:10: ( UNION ( ALL )? | INTERSECT | EXCEPT )
            int alt63=3;
            switch ( input.LA(1) ) {
            case UNION:
                {
                alt63=1;
                }
                break;
            case INTERSECT:
                {
                alt63=2;
                }
                break;
            case EXCEPT:
                {
                alt63=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 63, 0, input);

                throw nvae;
            }

            switch (alt63) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:262:12: UNION ( ALL )?
                    {
                    root_0 = (Object)adaptor.nil();

                    UNION194=(Token)match(input,UNION,FOLLOW_UNION_in_select_op1877); 
                    UNION194_tree = (Object)adaptor.create(UNION194);
                    root_0 = (Object)adaptor.becomeRoot(UNION194_tree, root_0);

                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:262:19: ( ALL )?
                    int alt62=2;
                    int LA62_0 = input.LA(1);

                    if ( (LA62_0==ALL) ) {
                        alt62=1;
                    }
                    switch (alt62) {
                        case 1 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:262:20: ALL
                            {
                            ALL195=(Token)match(input,ALL,FOLLOW_ALL_in_select_op1881); 
                            ALL195_tree = (Object)adaptor.create(ALL195);
                            adaptor.addChild(root_0, ALL195_tree);


                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:262:28: INTERSECT
                    {
                    root_0 = (Object)adaptor.nil();

                    INTERSECT196=(Token)match(input,INTERSECT,FOLLOW_INTERSECT_in_select_op1887); 
                    INTERSECT196_tree = (Object)adaptor.create(INTERSECT196);
                    adaptor.addChild(root_0, INTERSECT196_tree);


                    }
                    break;
                case 3 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:262:40: EXCEPT
                    {
                    root_0 = (Object)adaptor.nil();

                    EXCEPT197=(Token)match(input,EXCEPT,FOLLOW_EXCEPT_in_select_op1891); 
                    EXCEPT197_tree = (Object)adaptor.create(EXCEPT197);
                    adaptor.addChild(root_0, EXCEPT197_tree);


                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "select_op"

    public static class select_core_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "select_core"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:264:1: select_core : SELECT ( ALL | DISTINCT )? result_column ( COMMA result_column )* ( FROM join_source )? ( WHERE where_expr= expr )? ( GROUP BY ordering_term ( COMMA ordering_term )* ( HAVING having_expr= expr )? )? -> ^( SELECT_CORE ( DISTINCT )? ^( COLUMNS ( result_column )+ ) ( ^( FROM join_source ) )? ( ^( WHERE $where_expr) )? ( ^( GROUP ( ordering_term )+ ( ^( HAVING $having_expr) )? ) )? ) ;
    public final SqlParser.select_core_return select_core() throws RecognitionException {
        SqlParser.select_core_return retval = new SqlParser.select_core_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token SELECT198=null;
        Token ALL199=null;
        Token DISTINCT200=null;
        Token COMMA202=null;
        Token FROM204=null;
        Token WHERE206=null;
        Token GROUP207=null;
        Token BY208=null;
        Token COMMA210=null;
        Token HAVING212=null;
        SqlParser.expr_return where_expr = null;

        SqlParser.expr_return having_expr = null;

        SqlParser.result_column_return result_column201 = null;

        SqlParser.result_column_return result_column203 = null;

        SqlParser.join_source_return join_source205 = null;

        SqlParser.ordering_term_return ordering_term209 = null;

        SqlParser.ordering_term_return ordering_term211 = null;


        Object SELECT198_tree=null;
        Object ALL199_tree=null;
        Object DISTINCT200_tree=null;
        Object COMMA202_tree=null;
        Object FROM204_tree=null;
        Object WHERE206_tree=null;
        Object GROUP207_tree=null;
        Object BY208_tree=null;
        Object COMMA210_tree=null;
        Object HAVING212_tree=null;
        RewriteRuleTokenStream stream_WHERE=new RewriteRuleTokenStream(adaptor,"token WHERE");
        RewriteRuleTokenStream stream_GROUP=new RewriteRuleTokenStream(adaptor,"token GROUP");
        RewriteRuleTokenStream stream_BY=new RewriteRuleTokenStream(adaptor,"token BY");
        RewriteRuleTokenStream stream_HAVING=new RewriteRuleTokenStream(adaptor,"token HAVING");
        RewriteRuleTokenStream stream_FROM=new RewriteRuleTokenStream(adaptor,"token FROM");
        RewriteRuleTokenStream stream_COMMA=new RewriteRuleTokenStream(adaptor,"token COMMA");
        RewriteRuleTokenStream stream_SELECT=new RewriteRuleTokenStream(adaptor,"token SELECT");
        RewriteRuleTokenStream stream_DISTINCT=new RewriteRuleTokenStream(adaptor,"token DISTINCT");
        RewriteRuleTokenStream stream_ALL=new RewriteRuleTokenStream(adaptor,"token ALL");
        RewriteRuleSubtreeStream stream_ordering_term=new RewriteRuleSubtreeStream(adaptor,"rule ordering_term");
        RewriteRuleSubtreeStream stream_result_column=new RewriteRuleSubtreeStream(adaptor,"rule result_column");
        RewriteRuleSubtreeStream stream_expr=new RewriteRuleSubtreeStream(adaptor,"rule expr");
        RewriteRuleSubtreeStream stream_join_source=new RewriteRuleSubtreeStream(adaptor,"rule join_source");
        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:264:12: ( SELECT ( ALL | DISTINCT )? result_column ( COMMA result_column )* ( FROM join_source )? ( WHERE where_expr= expr )? ( GROUP BY ordering_term ( COMMA ordering_term )* ( HAVING having_expr= expr )? )? -> ^( SELECT_CORE ( DISTINCT )? ^( COLUMNS ( result_column )+ ) ( ^( FROM join_source ) )? ( ^( WHERE $where_expr) )? ( ^( GROUP ( ordering_term )+ ( ^( HAVING $having_expr) )? ) )? ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:265:3: SELECT ( ALL | DISTINCT )? result_column ( COMMA result_column )* ( FROM join_source )? ( WHERE where_expr= expr )? ( GROUP BY ordering_term ( COMMA ordering_term )* ( HAVING having_expr= expr )? )?
            {
            SELECT198=(Token)match(input,SELECT,FOLLOW_SELECT_in_select_core1900);  
            stream_SELECT.add(SELECT198);

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:265:10: ( ALL | DISTINCT )?
            int alt64=3;
            alt64 = dfa64.predict(input);
            switch (alt64) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:265:11: ALL
                    {
                    ALL199=(Token)match(input,ALL,FOLLOW_ALL_in_select_core1903);  
                    stream_ALL.add(ALL199);


                    }
                    break;
                case 2 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:265:17: DISTINCT
                    {
                    DISTINCT200=(Token)match(input,DISTINCT,FOLLOW_DISTINCT_in_select_core1907);  
                    stream_DISTINCT.add(DISTINCT200);


                    }
                    break;

            }

            pushFollow(FOLLOW_result_column_in_select_core1911);
            result_column201=result_column();

            state._fsp--;

            stream_result_column.add(result_column201.getTree());
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:265:42: ( COMMA result_column )*
            loop65:
            do {
                int alt65=2;
                alt65 = dfa65.predict(input);
                switch (alt65) {
            	case 1 :
            	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:265:43: COMMA result_column
            	    {
            	    COMMA202=(Token)match(input,COMMA,FOLLOW_COMMA_in_select_core1914);  
            	    stream_COMMA.add(COMMA202);

            	    pushFollow(FOLLOW_result_column_in_select_core1916);
            	    result_column203=result_column();

            	    state._fsp--;

            	    stream_result_column.add(result_column203.getTree());

            	    }
            	    break;

            	default :
            	    break loop65;
                }
            } while (true);

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:265:65: ( FROM join_source )?
            int alt66=2;
            alt66 = dfa66.predict(input);
            switch (alt66) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:265:66: FROM join_source
                    {
                    FROM204=(Token)match(input,FROM,FOLLOW_FROM_in_select_core1921);  
                    stream_FROM.add(FROM204);

                    pushFollow(FOLLOW_join_source_in_select_core1923);
                    join_source205=join_source();

                    state._fsp--;

                    stream_join_source.add(join_source205.getTree());

                    }
                    break;

            }

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:265:85: ( WHERE where_expr= expr )?
            int alt67=2;
            alt67 = dfa67.predict(input);
            switch (alt67) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:265:86: WHERE where_expr= expr
                    {
                    WHERE206=(Token)match(input,WHERE,FOLLOW_WHERE_in_select_core1928);  
                    stream_WHERE.add(WHERE206);

                    pushFollow(FOLLOW_expr_in_select_core1932);
                    where_expr=expr();

                    state._fsp--;

                    stream_expr.add(where_expr.getTree());

                    }
                    break;

            }

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:266:3: ( GROUP BY ordering_term ( COMMA ordering_term )* ( HAVING having_expr= expr )? )?
            int alt70=2;
            int LA70_0 = input.LA(1);

            if ( (LA70_0==GROUP) ) {
                alt70=1;
            }
            switch (alt70) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:266:5: GROUP BY ordering_term ( COMMA ordering_term )* ( HAVING having_expr= expr )?
                    {
                    GROUP207=(Token)match(input,GROUP,FOLLOW_GROUP_in_select_core1940);  
                    stream_GROUP.add(GROUP207);

                    BY208=(Token)match(input,BY,FOLLOW_BY_in_select_core1942);  
                    stream_BY.add(BY208);

                    pushFollow(FOLLOW_ordering_term_in_select_core1944);
                    ordering_term209=ordering_term();

                    state._fsp--;

                    stream_ordering_term.add(ordering_term209.getTree());
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:266:28: ( COMMA ordering_term )*
                    loop68:
                    do {
                        int alt68=2;
                        alt68 = dfa68.predict(input);
                        switch (alt68) {
                    	case 1 :
                    	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:266:29: COMMA ordering_term
                    	    {
                    	    COMMA210=(Token)match(input,COMMA,FOLLOW_COMMA_in_select_core1947);  
                    	    stream_COMMA.add(COMMA210);

                    	    pushFollow(FOLLOW_ordering_term_in_select_core1949);
                    	    ordering_term211=ordering_term();

                    	    state._fsp--;

                    	    stream_ordering_term.add(ordering_term211.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop68;
                        }
                    } while (true);

                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:266:51: ( HAVING having_expr= expr )?
                    int alt69=2;
                    int LA69_0 = input.LA(1);

                    if ( (LA69_0==HAVING) ) {
                        alt69=1;
                    }
                    switch (alt69) {
                        case 1 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:266:52: HAVING having_expr= expr
                            {
                            HAVING212=(Token)match(input,HAVING,FOLLOW_HAVING_in_select_core1954);  
                            stream_HAVING.add(HAVING212);

                            pushFollow(FOLLOW_expr_in_select_core1958);
                            having_expr=expr();

                            state._fsp--;

                            stream_expr.add(having_expr.getTree());

                            }
                            break;

                    }


                    }
                    break;

            }



            // AST REWRITE
            // elements: join_source, ordering_term, where_expr, FROM, WHERE, result_column, DISTINCT, having_expr, GROUP, HAVING
            // token labels: 
            // rule labels: retval, having_expr, where_expr
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_having_expr=new RewriteRuleSubtreeStream(adaptor,"rule having_expr",having_expr!=null?having_expr.tree:null);
            RewriteRuleSubtreeStream stream_where_expr=new RewriteRuleSubtreeStream(adaptor,"rule where_expr",where_expr!=null?where_expr.tree:null);

            root_0 = (Object)adaptor.nil();
            // 267:1: -> ^( SELECT_CORE ( DISTINCT )? ^( COLUMNS ( result_column )+ ) ( ^( FROM join_source ) )? ( ^( WHERE $where_expr) )? ( ^( GROUP ( ordering_term )+ ( ^( HAVING $having_expr) )? ) )? )
            {
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:267:4: ^( SELECT_CORE ( DISTINCT )? ^( COLUMNS ( result_column )+ ) ( ^( FROM join_source ) )? ( ^( WHERE $where_expr) )? ( ^( GROUP ( ordering_term )+ ( ^( HAVING $having_expr) )? ) )? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(SELECT_CORE, "SELECT_CORE"), root_1);

                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:268:15: ( DISTINCT )?
                if ( stream_DISTINCT.hasNext() ) {
                    adaptor.addChild(root_1, stream_DISTINCT.nextNode());

                }
                stream_DISTINCT.reset();
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:268:27: ^( COLUMNS ( result_column )+ )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(COLUMNS, "COLUMNS"), root_2);

                if ( !(stream_result_column.hasNext()) ) {
                    throw new RewriteEarlyExitException();
                }
                while ( stream_result_column.hasNext() ) {
                    adaptor.addChild(root_2, stream_result_column.nextTree());

                }
                stream_result_column.reset();

                adaptor.addChild(root_1, root_2);
                }
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:268:53: ( ^( FROM join_source ) )?
                if ( stream_join_source.hasNext()||stream_FROM.hasNext() ) {
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:268:53: ^( FROM join_source )
                    {
                    Object root_2 = (Object)adaptor.nil();
                    root_2 = (Object)adaptor.becomeRoot(stream_FROM.nextNode(), root_2);

                    adaptor.addChild(root_2, stream_join_source.nextTree());

                    adaptor.addChild(root_1, root_2);
                    }

                }
                stream_join_source.reset();
                stream_FROM.reset();
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:268:74: ( ^( WHERE $where_expr) )?
                if ( stream_where_expr.hasNext()||stream_WHERE.hasNext() ) {
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:268:74: ^( WHERE $where_expr)
                    {
                    Object root_2 = (Object)adaptor.nil();
                    root_2 = (Object)adaptor.becomeRoot(stream_WHERE.nextNode(), root_2);

                    adaptor.addChild(root_2, stream_where_expr.nextTree());

                    adaptor.addChild(root_1, root_2);
                    }

                }
                stream_where_expr.reset();
                stream_WHERE.reset();
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:269:3: ( ^( GROUP ( ordering_term )+ ( ^( HAVING $having_expr) )? ) )?
                if ( stream_ordering_term.hasNext()||stream_having_expr.hasNext()||stream_GROUP.hasNext()||stream_HAVING.hasNext() ) {
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:269:3: ^( GROUP ( ordering_term )+ ( ^( HAVING $having_expr) )? )
                    {
                    Object root_2 = (Object)adaptor.nil();
                    root_2 = (Object)adaptor.becomeRoot(stream_GROUP.nextNode(), root_2);

                    if ( !(stream_ordering_term.hasNext()) ) {
                        throw new RewriteEarlyExitException();
                    }
                    while ( stream_ordering_term.hasNext() ) {
                        adaptor.addChild(root_2, stream_ordering_term.nextTree());

                    }
                    stream_ordering_term.reset();
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:269:26: ( ^( HAVING $having_expr) )?
                    if ( stream_having_expr.hasNext()||stream_HAVING.hasNext() ) {
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:269:26: ^( HAVING $having_expr)
                        {
                        Object root_3 = (Object)adaptor.nil();
                        root_3 = (Object)adaptor.becomeRoot(stream_HAVING.nextNode(), root_3);

                        adaptor.addChild(root_3, stream_having_expr.nextTree());

                        adaptor.addChild(root_2, root_3);
                        }

                    }
                    stream_having_expr.reset();
                    stream_HAVING.reset();

                    adaptor.addChild(root_1, root_2);
                    }

                }
                stream_ordering_term.reset();
                stream_having_expr.reset();
                stream_GROUP.reset();
                stream_HAVING.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "select_core"

    public static class result_column_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "result_column"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:272:1: result_column : ( ASTERISK | table_name= id DOT ASTERISK -> ^( ASTERISK $table_name) | expr ( ( AS )? column_alias= id )? -> ^( ALIAS expr ( $column_alias)? ) );
    public final SqlParser.result_column_return result_column() throws RecognitionException {
        SqlParser.result_column_return retval = new SqlParser.result_column_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ASTERISK213=null;
        Token DOT214=null;
        Token ASTERISK215=null;
        Token AS217=null;
        SqlParser.id_return table_name = null;

        SqlParser.id_return column_alias = null;

        SqlParser.expr_return expr216 = null;


        Object ASTERISK213_tree=null;
        Object DOT214_tree=null;
        Object ASTERISK215_tree=null;
        Object AS217_tree=null;
        RewriteRuleTokenStream stream_AS=new RewriteRuleTokenStream(adaptor,"token AS");
        RewriteRuleTokenStream stream_DOT=new RewriteRuleTokenStream(adaptor,"token DOT");
        RewriteRuleTokenStream stream_ASTERISK=new RewriteRuleTokenStream(adaptor,"token ASTERISK");
        RewriteRuleSubtreeStream stream_id=new RewriteRuleSubtreeStream(adaptor,"rule id");
        RewriteRuleSubtreeStream stream_expr=new RewriteRuleSubtreeStream(adaptor,"rule expr");
        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:273:3: ( ASTERISK | table_name= id DOT ASTERISK -> ^( ASTERISK $table_name) | expr ( ( AS )? column_alias= id )? -> ^( ALIAS expr ( $column_alias)? ) )
            int alt73=3;
            alt73 = dfa73.predict(input);
            switch (alt73) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:273:5: ASTERISK
                    {
                    root_0 = (Object)adaptor.nil();

                    ASTERISK213=(Token)match(input,ASTERISK,FOLLOW_ASTERISK_in_result_column2028); 
                    ASTERISK213_tree = (Object)adaptor.create(ASTERISK213);
                    adaptor.addChild(root_0, ASTERISK213_tree);


                    }
                    break;
                case 2 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:274:5: table_name= id DOT ASTERISK
                    {
                    pushFollow(FOLLOW_id_in_result_column2036);
                    table_name=id();

                    state._fsp--;

                    stream_id.add(table_name.getTree());
                    DOT214=(Token)match(input,DOT,FOLLOW_DOT_in_result_column2038);  
                    stream_DOT.add(DOT214);

                    ASTERISK215=(Token)match(input,ASTERISK,FOLLOW_ASTERISK_in_result_column2040);  
                    stream_ASTERISK.add(ASTERISK215);



                    // AST REWRITE
                    // elements: ASTERISK, table_name
                    // token labels: 
                    // rule labels: retval, table_name
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                    RewriteRuleSubtreeStream stream_table_name=new RewriteRuleSubtreeStream(adaptor,"rule table_name",table_name!=null?table_name.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 274:32: -> ^( ASTERISK $table_name)
                    {
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:274:35: ^( ASTERISK $table_name)
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(stream_ASTERISK.nextNode(), root_1);

                        adaptor.addChild(root_1, stream_table_name.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 3 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:275:5: expr ( ( AS )? column_alias= id )?
                    {
                    pushFollow(FOLLOW_expr_in_result_column2055);
                    expr216=expr();

                    state._fsp--;

                    stream_expr.add(expr216.getTree());
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:275:10: ( ( AS )? column_alias= id )?
                    int alt72=2;
                    alt72 = dfa72.predict(input);
                    switch (alt72) {
                        case 1 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:275:11: ( AS )? column_alias= id
                            {
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:275:11: ( AS )?
                            int alt71=2;
                            alt71 = dfa71.predict(input);
                            switch (alt71) {
                                case 1 :
                                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:275:12: AS
                                    {
                                    AS217=(Token)match(input,AS,FOLLOW_AS_in_result_column2059);  
                                    stream_AS.add(AS217);


                                    }
                                    break;

                            }

                            pushFollow(FOLLOW_id_in_result_column2065);
                            column_alias=id();

                            state._fsp--;

                            stream_id.add(column_alias.getTree());

                            }
                            break;

                    }



                    // AST REWRITE
                    // elements: expr, column_alias
                    // token labels: 
                    // rule labels: retval, column_alias
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                    RewriteRuleSubtreeStream stream_column_alias=new RewriteRuleSubtreeStream(adaptor,"rule column_alias",column_alias!=null?column_alias.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 275:35: -> ^( ALIAS expr ( $column_alias)? )
                    {
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:275:38: ^( ALIAS expr ( $column_alias)? )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(ALIAS, "ALIAS"), root_1);

                        adaptor.addChild(root_1, stream_expr.nextTree());
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:275:51: ( $column_alias)?
                        if ( stream_column_alias.hasNext() ) {
                            adaptor.addChild(root_1, stream_column_alias.nextTree());

                        }
                        stream_column_alias.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "result_column"

    public static class join_source_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "join_source"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:277:1: join_source : single_source ( join_op single_source ( join_constraint )? )* ;
    public final SqlParser.join_source_return join_source() throws RecognitionException {
        SqlParser.join_source_return retval = new SqlParser.join_source_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        SqlParser.single_source_return single_source218 = null;

        SqlParser.join_op_return join_op219 = null;

        SqlParser.single_source_return single_source220 = null;

        SqlParser.join_constraint_return join_constraint221 = null;



        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:277:12: ( single_source ( join_op single_source ( join_constraint )? )* )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:277:14: single_source ( join_op single_source ( join_constraint )? )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_single_source_in_join_source2086);
            single_source218=single_source();

            state._fsp--;

            adaptor.addChild(root_0, single_source218.getTree());
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:277:28: ( join_op single_source ( join_constraint )? )*
            loop75:
            do {
                int alt75=2;
                alt75 = dfa75.predict(input);
                switch (alt75) {
            	case 1 :
            	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:277:29: join_op single_source ( join_constraint )?
            	    {
            	    pushFollow(FOLLOW_join_op_in_join_source2089);
            	    join_op219=join_op();

            	    state._fsp--;

            	    root_0 = (Object)adaptor.becomeRoot(join_op219.getTree(), root_0);
            	    pushFollow(FOLLOW_single_source_in_join_source2092);
            	    single_source220=single_source();

            	    state._fsp--;

            	    adaptor.addChild(root_0, single_source220.getTree());
            	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:277:52: ( join_constraint )?
            	    int alt74=2;
            	    alt74 = dfa74.predict(input);
            	    switch (alt74) {
            	        case 1 :
            	            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:277:53: join_constraint
            	            {
            	            pushFollow(FOLLOW_join_constraint_in_join_source2095);
            	            join_constraint221=join_constraint();

            	            state._fsp--;

            	            adaptor.addChild(root_0, join_constraint221.getTree());

            	            }
            	            break;

            	    }


            	    }
            	    break;

            	default :
            	    break loop75;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "join_source"

    public static class single_source_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "single_source"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:279:1: single_source : ( (database_name= id DOT )? table_name= ID ( ( AS )? table_alias= ID )? ( INDEXED BY index_name= id | NOT INDEXED )? -> ^( ALIAS ^( $table_name ( $database_name)? ) ( $table_alias)? ( ^( INDEXED ( NOT )? ( $index_name)? ) )? ) | LPAREN select_stmt RPAREN ( ( AS )? table_alias= ID )? -> ^( ALIAS select_stmt ( $table_alias)? ) | LPAREN join_source RPAREN );
    public final SqlParser.single_source_return single_source() throws RecognitionException {
        SqlParser.single_source_return retval = new SqlParser.single_source_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token table_name=null;
        Token table_alias=null;
        Token DOT222=null;
        Token AS223=null;
        Token INDEXED224=null;
        Token BY225=null;
        Token NOT226=null;
        Token INDEXED227=null;
        Token LPAREN228=null;
        Token RPAREN230=null;
        Token AS231=null;
        Token LPAREN232=null;
        Token RPAREN234=null;
        SqlParser.id_return database_name = null;

        SqlParser.id_return index_name = null;

        SqlParser.select_stmt_return select_stmt229 = null;

        SqlParser.join_source_return join_source233 = null;


        Object table_name_tree=null;
        Object table_alias_tree=null;
        Object DOT222_tree=null;
        Object AS223_tree=null;
        Object INDEXED224_tree=null;
        Object BY225_tree=null;
        Object NOT226_tree=null;
        Object INDEXED227_tree=null;
        Object LPAREN228_tree=null;
        Object RPAREN230_tree=null;
        Object AS231_tree=null;
        Object LPAREN232_tree=null;
        Object RPAREN234_tree=null;
        RewriteRuleTokenStream stream_INDEXED=new RewriteRuleTokenStream(adaptor,"token INDEXED");
        RewriteRuleTokenStream stream_AS=new RewriteRuleTokenStream(adaptor,"token AS");
        RewriteRuleTokenStream stream_RPAREN=new RewriteRuleTokenStream(adaptor,"token RPAREN");
        RewriteRuleTokenStream stream_BY=new RewriteRuleTokenStream(adaptor,"token BY");
        RewriteRuleTokenStream stream_NOT=new RewriteRuleTokenStream(adaptor,"token NOT");
        RewriteRuleTokenStream stream_DOT=new RewriteRuleTokenStream(adaptor,"token DOT");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_LPAREN=new RewriteRuleTokenStream(adaptor,"token LPAREN");
        RewriteRuleSubtreeStream stream_id=new RewriteRuleSubtreeStream(adaptor,"rule id");
        RewriteRuleSubtreeStream stream_select_stmt=new RewriteRuleSubtreeStream(adaptor,"rule select_stmt");
        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:280:3: ( (database_name= id DOT )? table_name= ID ( ( AS )? table_alias= ID )? ( INDEXED BY index_name= id | NOT INDEXED )? -> ^( ALIAS ^( $table_name ( $database_name)? ) ( $table_alias)? ( ^( INDEXED ( NOT )? ( $index_name)? ) )? ) | LPAREN select_stmt RPAREN ( ( AS )? table_alias= ID )? -> ^( ALIAS select_stmt ( $table_alias)? ) | LPAREN join_source RPAREN )
            int alt82=3;
            alt82 = dfa82.predict(input);
            switch (alt82) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:280:5: (database_name= id DOT )? table_name= ID ( ( AS )? table_alias= ID )? ( INDEXED BY index_name= id | NOT INDEXED )?
                    {
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:280:5: (database_name= id DOT )?
                    int alt76=2;
                    alt76 = dfa76.predict(input);
                    switch (alt76) {
                        case 1 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:280:6: database_name= id DOT
                            {
                            pushFollow(FOLLOW_id_in_single_source2112);
                            database_name=id();

                            state._fsp--;

                            stream_id.add(database_name.getTree());
                            DOT222=(Token)match(input,DOT,FOLLOW_DOT_in_single_source2114);  
                            stream_DOT.add(DOT222);


                            }
                            break;

                    }

                    table_name=(Token)match(input,ID,FOLLOW_ID_in_single_source2120);  
                    stream_ID.add(table_name);

                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:280:43: ( ( AS )? table_alias= ID )?
                    int alt78=2;
                    alt78 = dfa78.predict(input);
                    switch (alt78) {
                        case 1 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:280:44: ( AS )? table_alias= ID
                            {
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:280:44: ( AS )?
                            int alt77=2;
                            int LA77_0 = input.LA(1);

                            if ( (LA77_0==AS) ) {
                                alt77=1;
                            }
                            switch (alt77) {
                                case 1 :
                                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:280:45: AS
                                    {
                                    AS223=(Token)match(input,AS,FOLLOW_AS_in_single_source2124);  
                                    stream_AS.add(AS223);


                                    }
                                    break;

                            }

                            table_alias=(Token)match(input,ID,FOLLOW_ID_in_single_source2130);  
                            stream_ID.add(table_alias);


                            }
                            break;

                    }

                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:280:67: ( INDEXED BY index_name= id | NOT INDEXED )?
                    int alt79=3;
                    alt79 = dfa79.predict(input);
                    switch (alt79) {
                        case 1 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:280:68: INDEXED BY index_name= id
                            {
                            INDEXED224=(Token)match(input,INDEXED,FOLLOW_INDEXED_in_single_source2135);  
                            stream_INDEXED.add(INDEXED224);

                            BY225=(Token)match(input,BY,FOLLOW_BY_in_single_source2137);  
                            stream_BY.add(BY225);

                            pushFollow(FOLLOW_id_in_single_source2141);
                            index_name=id();

                            state._fsp--;

                            stream_id.add(index_name.getTree());

                            }
                            break;
                        case 2 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:280:95: NOT INDEXED
                            {
                            NOT226=(Token)match(input,NOT,FOLLOW_NOT_in_single_source2145);  
                            stream_NOT.add(NOT226);

                            INDEXED227=(Token)match(input,INDEXED,FOLLOW_INDEXED_in_single_source2147);  
                            stream_INDEXED.add(INDEXED227);


                            }
                            break;

                    }



                    // AST REWRITE
                    // elements: NOT, index_name, INDEXED, table_name, database_name, table_alias
                    // token labels: table_name, table_alias
                    // rule labels: database_name, index_name, retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleTokenStream stream_table_name=new RewriteRuleTokenStream(adaptor,"token table_name",table_name);
                    RewriteRuleTokenStream stream_table_alias=new RewriteRuleTokenStream(adaptor,"token table_alias",table_alias);
                    RewriteRuleSubtreeStream stream_database_name=new RewriteRuleSubtreeStream(adaptor,"rule database_name",database_name!=null?database_name.tree:null);
                    RewriteRuleSubtreeStream stream_index_name=new RewriteRuleSubtreeStream(adaptor,"rule index_name",index_name!=null?index_name.tree:null);
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 281:3: -> ^( ALIAS ^( $table_name ( $database_name)? ) ( $table_alias)? ( ^( INDEXED ( NOT )? ( $index_name)? ) )? )
                    {
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:281:6: ^( ALIAS ^( $table_name ( $database_name)? ) ( $table_alias)? ( ^( INDEXED ( NOT )? ( $index_name)? ) )? )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(ALIAS, "ALIAS"), root_1);

                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:281:14: ^( $table_name ( $database_name)? )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot(stream_table_name.nextNode(), root_2);

                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:281:28: ( $database_name)?
                        if ( stream_database_name.hasNext() ) {
                            adaptor.addChild(root_2, stream_database_name.nextTree());

                        }
                        stream_database_name.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:281:45: ( $table_alias)?
                        if ( stream_table_alias.hasNext() ) {
                            adaptor.addChild(root_1, stream_table_alias.nextNode());

                        }
                        stream_table_alias.reset();
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:281:59: ( ^( INDEXED ( NOT )? ( $index_name)? ) )?
                        if ( stream_NOT.hasNext()||stream_index_name.hasNext()||stream_INDEXED.hasNext() ) {
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:281:59: ^( INDEXED ( NOT )? ( $index_name)? )
                            {
                            Object root_2 = (Object)adaptor.nil();
                            root_2 = (Object)adaptor.becomeRoot(stream_INDEXED.nextNode(), root_2);

                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:281:69: ( NOT )?
                            if ( stream_NOT.hasNext() ) {
                                adaptor.addChild(root_2, stream_NOT.nextNode());

                            }
                            stream_NOT.reset();
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:281:74: ( $index_name)?
                            if ( stream_index_name.hasNext() ) {
                                adaptor.addChild(root_2, stream_index_name.nextTree());

                            }
                            stream_index_name.reset();

                            adaptor.addChild(root_1, root_2);
                            }

                        }
                        stream_NOT.reset();
                        stream_index_name.reset();
                        stream_INDEXED.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:282:5: LPAREN select_stmt RPAREN ( ( AS )? table_alias= ID )?
                    {
                    LPAREN228=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_single_source2188);  
                    stream_LPAREN.add(LPAREN228);

                    pushFollow(FOLLOW_select_stmt_in_single_source2190);
                    select_stmt229=select_stmt();

                    state._fsp--;

                    stream_select_stmt.add(select_stmt229.getTree());
                    RPAREN230=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_single_source2192);  
                    stream_RPAREN.add(RPAREN230);

                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:282:31: ( ( AS )? table_alias= ID )?
                    int alt81=2;
                    alt81 = dfa81.predict(input);
                    switch (alt81) {
                        case 1 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:282:32: ( AS )? table_alias= ID
                            {
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:282:32: ( AS )?
                            int alt80=2;
                            int LA80_0 = input.LA(1);

                            if ( (LA80_0==AS) ) {
                                alt80=1;
                            }
                            switch (alt80) {
                                case 1 :
                                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:282:33: AS
                                    {
                                    AS231=(Token)match(input,AS,FOLLOW_AS_in_single_source2196);  
                                    stream_AS.add(AS231);


                                    }
                                    break;

                            }

                            table_alias=(Token)match(input,ID,FOLLOW_ID_in_single_source2202);  
                            stream_ID.add(table_alias);


                            }
                            break;

                    }



                    // AST REWRITE
                    // elements: table_alias, select_stmt
                    // token labels: table_alias
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleTokenStream stream_table_alias=new RewriteRuleTokenStream(adaptor,"token table_alias",table_alias);
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 283:3: -> ^( ALIAS select_stmt ( $table_alias)? )
                    {
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:283:6: ^( ALIAS select_stmt ( $table_alias)? )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(ALIAS, "ALIAS"), root_1);

                        adaptor.addChild(root_1, stream_select_stmt.nextTree());
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:283:26: ( $table_alias)?
                        if ( stream_table_alias.hasNext() ) {
                            adaptor.addChild(root_1, stream_table_alias.nextNode());

                        }
                        stream_table_alias.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 3 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:284:5: LPAREN join_source RPAREN
                    {
                    root_0 = (Object)adaptor.nil();

                    LPAREN232=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_single_source2224); 
                    pushFollow(FOLLOW_join_source_in_single_source2227);
                    join_source233=join_source();

                    state._fsp--;

                    adaptor.addChild(root_0, join_source233.getTree());
                    RPAREN234=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_single_source2229); 

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "single_source"

    public static class join_op_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "join_op"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:286:1: join_op : ( COMMA | ( NATURAL )? ( ( LEFT )? ( OUTER )? | INNER | CROSS ) JOIN );
    public final SqlParser.join_op_return join_op() throws RecognitionException {
        SqlParser.join_op_return retval = new SqlParser.join_op_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token COMMA235=null;
        Token NATURAL236=null;
        Token LEFT237=null;
        Token OUTER238=null;
        Token INNER239=null;
        Token CROSS240=null;
        Token JOIN241=null;

        Object COMMA235_tree=null;
        Object NATURAL236_tree=null;
        Object LEFT237_tree=null;
        Object OUTER238_tree=null;
        Object INNER239_tree=null;
        Object CROSS240_tree=null;
        Object JOIN241_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:287:3: ( COMMA | ( NATURAL )? ( ( LEFT )? ( OUTER )? | INNER | CROSS ) JOIN )
            int alt87=2;
            int LA87_0 = input.LA(1);

            if ( (LA87_0==COMMA) ) {
                alt87=1;
            }
            else if ( ((LA87_0>=NATURAL && LA87_0<=JOIN)) ) {
                alt87=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 87, 0, input);

                throw nvae;
            }
            switch (alt87) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:287:5: COMMA
                    {
                    root_0 = (Object)adaptor.nil();

                    COMMA235=(Token)match(input,COMMA,FOLLOW_COMMA_in_join_op2240); 
                    COMMA235_tree = (Object)adaptor.create(COMMA235);
                    adaptor.addChild(root_0, COMMA235_tree);


                    }
                    break;
                case 2 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:288:5: ( NATURAL )? ( ( LEFT )? ( OUTER )? | INNER | CROSS ) JOIN
                    {
                    root_0 = (Object)adaptor.nil();

                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:288:5: ( NATURAL )?
                    int alt83=2;
                    int LA83_0 = input.LA(1);

                    if ( (LA83_0==NATURAL) ) {
                        alt83=1;
                    }
                    switch (alt83) {
                        case 1 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:288:6: NATURAL
                            {
                            NATURAL236=(Token)match(input,NATURAL,FOLLOW_NATURAL_in_join_op2247); 
                            NATURAL236_tree = (Object)adaptor.create(NATURAL236);
                            adaptor.addChild(root_0, NATURAL236_tree);


                            }
                            break;

                    }

                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:288:16: ( ( LEFT )? ( OUTER )? | INNER | CROSS )
                    int alt86=3;
                    switch ( input.LA(1) ) {
                    case LEFT:
                    case OUTER:
                    case JOIN:
                        {
                        alt86=1;
                        }
                        break;
                    case INNER:
                        {
                        alt86=2;
                        }
                        break;
                    case CROSS:
                        {
                        alt86=3;
                        }
                        break;
                    default:
                        NoViableAltException nvae =
                            new NoViableAltException("", 86, 0, input);

                        throw nvae;
                    }

                    switch (alt86) {
                        case 1 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:288:17: ( LEFT )? ( OUTER )?
                            {
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:288:17: ( LEFT )?
                            int alt84=2;
                            int LA84_0 = input.LA(1);

                            if ( (LA84_0==LEFT) ) {
                                alt84=1;
                            }
                            switch (alt84) {
                                case 1 :
                                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:288:18: LEFT
                                    {
                                    LEFT237=(Token)match(input,LEFT,FOLLOW_LEFT_in_join_op2253); 
                                    LEFT237_tree = (Object)adaptor.create(LEFT237);
                                    adaptor.addChild(root_0, LEFT237_tree);


                                    }
                                    break;

                            }

                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:288:25: ( OUTER )?
                            int alt85=2;
                            int LA85_0 = input.LA(1);

                            if ( (LA85_0==OUTER) ) {
                                alt85=1;
                            }
                            switch (alt85) {
                                case 1 :
                                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:288:26: OUTER
                                    {
                                    OUTER238=(Token)match(input,OUTER,FOLLOW_OUTER_in_join_op2258); 
                                    OUTER238_tree = (Object)adaptor.create(OUTER238);
                                    adaptor.addChild(root_0, OUTER238_tree);


                                    }
                                    break;

                            }


                            }
                            break;
                        case 2 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:288:36: INNER
                            {
                            INNER239=(Token)match(input,INNER,FOLLOW_INNER_in_join_op2264); 
                            INNER239_tree = (Object)adaptor.create(INNER239);
                            adaptor.addChild(root_0, INNER239_tree);


                            }
                            break;
                        case 3 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:288:44: CROSS
                            {
                            CROSS240=(Token)match(input,CROSS,FOLLOW_CROSS_in_join_op2268); 
                            CROSS240_tree = (Object)adaptor.create(CROSS240);
                            adaptor.addChild(root_0, CROSS240_tree);


                            }
                            break;

                    }

                    JOIN241=(Token)match(input,JOIN,FOLLOW_JOIN_in_join_op2271); 
                    JOIN241_tree = (Object)adaptor.create(JOIN241);
                    root_0 = (Object)adaptor.becomeRoot(JOIN241_tree, root_0);


                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "join_op"

    public static class join_constraint_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "join_constraint"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:290:1: join_constraint : ( ON expr | USING LPAREN column_names+= id ( COMMA column_names+= id )* RPAREN -> ^( USING ( $column_names)+ ) );
    public final SqlParser.join_constraint_return join_constraint() throws RecognitionException {
        SqlParser.join_constraint_return retval = new SqlParser.join_constraint_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ON242=null;
        Token USING244=null;
        Token LPAREN245=null;
        Token COMMA246=null;
        Token RPAREN247=null;
        List list_column_names=null;
        SqlParser.expr_return expr243 = null;

        SqlParser.id_return column_names = null;
         column_names = null;
        Object ON242_tree=null;
        Object USING244_tree=null;
        Object LPAREN245_tree=null;
        Object COMMA246_tree=null;
        Object RPAREN247_tree=null;
        RewriteRuleTokenStream stream_RPAREN=new RewriteRuleTokenStream(adaptor,"token RPAREN");
        RewriteRuleTokenStream stream_USING=new RewriteRuleTokenStream(adaptor,"token USING");
        RewriteRuleTokenStream stream_COMMA=new RewriteRuleTokenStream(adaptor,"token COMMA");
        RewriteRuleTokenStream stream_LPAREN=new RewriteRuleTokenStream(adaptor,"token LPAREN");
        RewriteRuleSubtreeStream stream_id=new RewriteRuleSubtreeStream(adaptor,"rule id");
        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:291:3: ( ON expr | USING LPAREN column_names+= id ( COMMA column_names+= id )* RPAREN -> ^( USING ( $column_names)+ ) )
            int alt89=2;
            int LA89_0 = input.LA(1);

            if ( (LA89_0==ON) ) {
                alt89=1;
            }
            else if ( (LA89_0==USING) ) {
                alt89=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 89, 0, input);

                throw nvae;
            }
            switch (alt89) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:291:5: ON expr
                    {
                    root_0 = (Object)adaptor.nil();

                    ON242=(Token)match(input,ON,FOLLOW_ON_in_join_constraint2282); 
                    ON242_tree = (Object)adaptor.create(ON242);
                    root_0 = (Object)adaptor.becomeRoot(ON242_tree, root_0);

                    pushFollow(FOLLOW_expr_in_join_constraint2285);
                    expr243=expr();

                    state._fsp--;

                    adaptor.addChild(root_0, expr243.getTree());

                    }
                    break;
                case 2 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:292:5: USING LPAREN column_names+= id ( COMMA column_names+= id )* RPAREN
                    {
                    USING244=(Token)match(input,USING,FOLLOW_USING_in_join_constraint2291);  
                    stream_USING.add(USING244);

                    LPAREN245=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_join_constraint2293);  
                    stream_LPAREN.add(LPAREN245);

                    pushFollow(FOLLOW_id_in_join_constraint2297);
                    column_names=id();

                    state._fsp--;

                    stream_id.add(column_names.getTree());
                    if (list_column_names==null) list_column_names=new ArrayList();
                    list_column_names.add(column_names.getTree());

                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:292:35: ( COMMA column_names+= id )*
                    loop88:
                    do {
                        int alt88=2;
                        int LA88_0 = input.LA(1);

                        if ( (LA88_0==COMMA) ) {
                            alt88=1;
                        }


                        switch (alt88) {
                    	case 1 :
                    	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:292:36: COMMA column_names+= id
                    	    {
                    	    COMMA246=(Token)match(input,COMMA,FOLLOW_COMMA_in_join_constraint2300);  
                    	    stream_COMMA.add(COMMA246);

                    	    pushFollow(FOLLOW_id_in_join_constraint2304);
                    	    column_names=id();

                    	    state._fsp--;

                    	    stream_id.add(column_names.getTree());
                    	    if (list_column_names==null) list_column_names=new ArrayList();
                    	    list_column_names.add(column_names.getTree());


                    	    }
                    	    break;

                    	default :
                    	    break loop88;
                        }
                    } while (true);

                    RPAREN247=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_join_constraint2308);  
                    stream_RPAREN.add(RPAREN247);



                    // AST REWRITE
                    // elements: USING, column_names
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: column_names
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                    RewriteRuleSubtreeStream stream_column_names=new RewriteRuleSubtreeStream(adaptor,"token column_names",list_column_names);
                    root_0 = (Object)adaptor.nil();
                    // 292:68: -> ^( USING ( $column_names)+ )
                    {
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:292:71: ^( USING ( $column_names)+ )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(stream_USING.nextNode(), root_1);

                        if ( !(stream_column_names.hasNext()) ) {
                            throw new RewriteEarlyExitException();
                        }
                        while ( stream_column_names.hasNext() ) {
                            adaptor.addChild(root_1, stream_column_names.nextTree());

                        }
                        stream_column_names.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "join_constraint"

    public static class insert_stmt_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "insert_stmt"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:295:1: insert_stmt : ( INSERT ( operation_conflict_clause )? | REPLACE ) INTO (database_name= id DOT )? table_name= id ( ( LPAREN column_names+= id ( COMMA column_names+= id )* RPAREN )? ( VALUES LPAREN values+= expr ( COMMA values+= expr )* RPAREN | select_stmt ) | DEFAULT VALUES ) ;
    public final SqlParser.insert_stmt_return insert_stmt() throws RecognitionException {
        SqlParser.insert_stmt_return retval = new SqlParser.insert_stmt_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token INSERT248=null;
        Token REPLACE250=null;
        Token INTO251=null;
        Token DOT252=null;
        Token LPAREN253=null;
        Token COMMA254=null;
        Token RPAREN255=null;
        Token VALUES256=null;
        Token LPAREN257=null;
        Token COMMA258=null;
        Token RPAREN259=null;
        Token DEFAULT261=null;
        Token VALUES262=null;
        List list_column_names=null;
        List list_values=null;
        SqlParser.id_return database_name = null;

        SqlParser.id_return table_name = null;

        SqlParser.operation_conflict_clause_return operation_conflict_clause249 = null;

        SqlParser.select_stmt_return select_stmt260 = null;

        SqlParser.id_return column_names = null;
         column_names = null;
        SqlParser.expr_return values = null;
         values = null;
        Object INSERT248_tree=null;
        Object REPLACE250_tree=null;
        Object INTO251_tree=null;
        Object DOT252_tree=null;
        Object LPAREN253_tree=null;
        Object COMMA254_tree=null;
        Object RPAREN255_tree=null;
        Object VALUES256_tree=null;
        Object LPAREN257_tree=null;
        Object COMMA258_tree=null;
        Object RPAREN259_tree=null;
        Object DEFAULT261_tree=null;
        Object VALUES262_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:295:12: ( ( INSERT ( operation_conflict_clause )? | REPLACE ) INTO (database_name= id DOT )? table_name= id ( ( LPAREN column_names+= id ( COMMA column_names+= id )* RPAREN )? ( VALUES LPAREN values+= expr ( COMMA values+= expr )* RPAREN | select_stmt ) | DEFAULT VALUES ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:295:14: ( INSERT ( operation_conflict_clause )? | REPLACE ) INTO (database_name= id DOT )? table_name= id ( ( LPAREN column_names+= id ( COMMA column_names+= id )* RPAREN )? ( VALUES LPAREN values+= expr ( COMMA values+= expr )* RPAREN | select_stmt ) | DEFAULT VALUES )
            {
            root_0 = (Object)adaptor.nil();

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:295:14: ( INSERT ( operation_conflict_clause )? | REPLACE )
            int alt91=2;
            int LA91_0 = input.LA(1);

            if ( (LA91_0==INSERT) ) {
                alt91=1;
            }
            else if ( (LA91_0==REPLACE) ) {
                alt91=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 91, 0, input);

                throw nvae;
            }
            switch (alt91) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:295:15: INSERT ( operation_conflict_clause )?
                    {
                    INSERT248=(Token)match(input,INSERT,FOLLOW_INSERT_in_insert_stmt2327); 
                    INSERT248_tree = (Object)adaptor.create(INSERT248);
                    adaptor.addChild(root_0, INSERT248_tree);

                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:295:22: ( operation_conflict_clause )?
                    int alt90=2;
                    int LA90_0 = input.LA(1);

                    if ( (LA90_0==OR) ) {
                        alt90=1;
                    }
                    switch (alt90) {
                        case 1 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:295:23: operation_conflict_clause
                            {
                            pushFollow(FOLLOW_operation_conflict_clause_in_insert_stmt2330);
                            operation_conflict_clause249=operation_conflict_clause();

                            state._fsp--;

                            adaptor.addChild(root_0, operation_conflict_clause249.getTree());

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:295:53: REPLACE
                    {
                    REPLACE250=(Token)match(input,REPLACE,FOLLOW_REPLACE_in_insert_stmt2336); 
                    REPLACE250_tree = (Object)adaptor.create(REPLACE250);
                    adaptor.addChild(root_0, REPLACE250_tree);


                    }
                    break;

            }

            INTO251=(Token)match(input,INTO,FOLLOW_INTO_in_insert_stmt2339); 
            INTO251_tree = (Object)adaptor.create(INTO251);
            adaptor.addChild(root_0, INTO251_tree);

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:295:67: (database_name= id DOT )?
            int alt92=2;
            alt92 = dfa92.predict(input);
            switch (alt92) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:295:68: database_name= id DOT
                    {
                    pushFollow(FOLLOW_id_in_insert_stmt2344);
                    database_name=id();

                    state._fsp--;

                    adaptor.addChild(root_0, database_name.getTree());
                    DOT252=(Token)match(input,DOT,FOLLOW_DOT_in_insert_stmt2346); 
                    DOT252_tree = (Object)adaptor.create(DOT252);
                    adaptor.addChild(root_0, DOT252_tree);


                    }
                    break;

            }

            pushFollow(FOLLOW_id_in_insert_stmt2352);
            table_name=id();

            state._fsp--;

            adaptor.addChild(root_0, table_name.getTree());
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:296:3: ( ( LPAREN column_names+= id ( COMMA column_names+= id )* RPAREN )? ( VALUES LPAREN values+= expr ( COMMA values+= expr )* RPAREN | select_stmt ) | DEFAULT VALUES )
            int alt97=2;
            int LA97_0 = input.LA(1);

            if ( (LA97_0==LPAREN||LA97_0==SELECT||LA97_0==VALUES) ) {
                alt97=1;
            }
            else if ( (LA97_0==DEFAULT) ) {
                alt97=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 97, 0, input);

                throw nvae;
            }
            switch (alt97) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:296:5: ( LPAREN column_names+= id ( COMMA column_names+= id )* RPAREN )? ( VALUES LPAREN values+= expr ( COMMA values+= expr )* RPAREN | select_stmt )
                    {
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:296:5: ( LPAREN column_names+= id ( COMMA column_names+= id )* RPAREN )?
                    int alt94=2;
                    int LA94_0 = input.LA(1);

                    if ( (LA94_0==LPAREN) ) {
                        alt94=1;
                    }
                    switch (alt94) {
                        case 1 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:296:6: LPAREN column_names+= id ( COMMA column_names+= id )* RPAREN
                            {
                            LPAREN253=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_insert_stmt2359); 
                            LPAREN253_tree = (Object)adaptor.create(LPAREN253);
                            adaptor.addChild(root_0, LPAREN253_tree);

                            pushFollow(FOLLOW_id_in_insert_stmt2363);
                            column_names=id();

                            state._fsp--;

                            adaptor.addChild(root_0, column_names.getTree());
                            if (list_column_names==null) list_column_names=new ArrayList();
                            list_column_names.add(column_names.getTree());

                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:296:30: ( COMMA column_names+= id )*
                            loop93:
                            do {
                                int alt93=2;
                                int LA93_0 = input.LA(1);

                                if ( (LA93_0==COMMA) ) {
                                    alt93=1;
                                }


                                switch (alt93) {
                            	case 1 :
                            	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:296:31: COMMA column_names+= id
                            	    {
                            	    COMMA254=(Token)match(input,COMMA,FOLLOW_COMMA_in_insert_stmt2366); 
                            	    COMMA254_tree = (Object)adaptor.create(COMMA254);
                            	    adaptor.addChild(root_0, COMMA254_tree);

                            	    pushFollow(FOLLOW_id_in_insert_stmt2370);
                            	    column_names=id();

                            	    state._fsp--;

                            	    adaptor.addChild(root_0, column_names.getTree());
                            	    if (list_column_names==null) list_column_names=new ArrayList();
                            	    list_column_names.add(column_names.getTree());


                            	    }
                            	    break;

                            	default :
                            	    break loop93;
                                }
                            } while (true);

                            RPAREN255=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_insert_stmt2374); 
                            RPAREN255_tree = (Object)adaptor.create(RPAREN255);
                            adaptor.addChild(root_0, RPAREN255_tree);


                            }
                            break;

                    }

                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:297:5: ( VALUES LPAREN values+= expr ( COMMA values+= expr )* RPAREN | select_stmt )
                    int alt96=2;
                    int LA96_0 = input.LA(1);

                    if ( (LA96_0==VALUES) ) {
                        alt96=1;
                    }
                    else if ( (LA96_0==SELECT) ) {
                        alt96=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 96, 0, input);

                        throw nvae;
                    }
                    switch (alt96) {
                        case 1 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:297:6: VALUES LPAREN values+= expr ( COMMA values+= expr )* RPAREN
                            {
                            VALUES256=(Token)match(input,VALUES,FOLLOW_VALUES_in_insert_stmt2383); 
                            VALUES256_tree = (Object)adaptor.create(VALUES256);
                            adaptor.addChild(root_0, VALUES256_tree);

                            LPAREN257=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_insert_stmt2385); 
                            LPAREN257_tree = (Object)adaptor.create(LPAREN257);
                            adaptor.addChild(root_0, LPAREN257_tree);

                            pushFollow(FOLLOW_expr_in_insert_stmt2389);
                            values=expr();

                            state._fsp--;

                            adaptor.addChild(root_0, values.getTree());
                            if (list_values==null) list_values=new ArrayList();
                            list_values.add(values.getTree());

                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:297:33: ( COMMA values+= expr )*
                            loop95:
                            do {
                                int alt95=2;
                                int LA95_0 = input.LA(1);

                                if ( (LA95_0==COMMA) ) {
                                    alt95=1;
                                }


                                switch (alt95) {
                            	case 1 :
                            	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:297:34: COMMA values+= expr
                            	    {
                            	    COMMA258=(Token)match(input,COMMA,FOLLOW_COMMA_in_insert_stmt2392); 
                            	    COMMA258_tree = (Object)adaptor.create(COMMA258);
                            	    adaptor.addChild(root_0, COMMA258_tree);

                            	    pushFollow(FOLLOW_expr_in_insert_stmt2396);
                            	    values=expr();

                            	    state._fsp--;

                            	    adaptor.addChild(root_0, values.getTree());
                            	    if (list_values==null) list_values=new ArrayList();
                            	    list_values.add(values.getTree());


                            	    }
                            	    break;

                            	default :
                            	    break loop95;
                                }
                            } while (true);

                            RPAREN259=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_insert_stmt2400); 
                            RPAREN259_tree = (Object)adaptor.create(RPAREN259);
                            adaptor.addChild(root_0, RPAREN259_tree);


                            }
                            break;
                        case 2 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:297:64: select_stmt
                            {
                            pushFollow(FOLLOW_select_stmt_in_insert_stmt2404);
                            select_stmt260=select_stmt();

                            state._fsp--;

                            adaptor.addChild(root_0, select_stmt260.getTree());

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:298:5: DEFAULT VALUES
                    {
                    DEFAULT261=(Token)match(input,DEFAULT,FOLLOW_DEFAULT_in_insert_stmt2411); 
                    DEFAULT261_tree = (Object)adaptor.create(DEFAULT261);
                    adaptor.addChild(root_0, DEFAULT261_tree);

                    VALUES262=(Token)match(input,VALUES,FOLLOW_VALUES_in_insert_stmt2413); 
                    VALUES262_tree = (Object)adaptor.create(VALUES262);
                    adaptor.addChild(root_0, VALUES262_tree);


                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "insert_stmt"

    public static class update_stmt_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "update_stmt"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:301:1: update_stmt : UPDATE ( operation_conflict_clause )? qualified_table_name SET values+= update_set ( COMMA values+= update_set )* ( WHERE expr )? ( operation_limited_clause )? ;
    public final SqlParser.update_stmt_return update_stmt() throws RecognitionException {
        SqlParser.update_stmt_return retval = new SqlParser.update_stmt_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token UPDATE263=null;
        Token SET266=null;
        Token COMMA267=null;
        Token WHERE268=null;
        List list_values=null;
        SqlParser.operation_conflict_clause_return operation_conflict_clause264 = null;

        SqlParser.qualified_table_name_return qualified_table_name265 = null;

        SqlParser.expr_return expr269 = null;

        SqlParser.operation_limited_clause_return operation_limited_clause270 = null;

        SqlParser.update_set_return values = null;
         values = null;
        Object UPDATE263_tree=null;
        Object SET266_tree=null;
        Object COMMA267_tree=null;
        Object WHERE268_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:301:12: ( UPDATE ( operation_conflict_clause )? qualified_table_name SET values+= update_set ( COMMA values+= update_set )* ( WHERE expr )? ( operation_limited_clause )? )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:301:14: UPDATE ( operation_conflict_clause )? qualified_table_name SET values+= update_set ( COMMA values+= update_set )* ( WHERE expr )? ( operation_limited_clause )?
            {
            root_0 = (Object)adaptor.nil();

            UPDATE263=(Token)match(input,UPDATE,FOLLOW_UPDATE_in_update_stmt2423); 
            UPDATE263_tree = (Object)adaptor.create(UPDATE263);
            adaptor.addChild(root_0, UPDATE263_tree);

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:301:21: ( operation_conflict_clause )?
            int alt98=2;
            int LA98_0 = input.LA(1);

            if ( (LA98_0==OR) ) {
                int LA98_1 = input.LA(2);

                if ( ((LA98_1>=IGNORE && LA98_1<=FAIL)||LA98_1==REPLACE) ) {
                    alt98=1;
                }
            }
            switch (alt98) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:301:22: operation_conflict_clause
                    {
                    pushFollow(FOLLOW_operation_conflict_clause_in_update_stmt2426);
                    operation_conflict_clause264=operation_conflict_clause();

                    state._fsp--;

                    adaptor.addChild(root_0, operation_conflict_clause264.getTree());

                    }
                    break;

            }

            pushFollow(FOLLOW_qualified_table_name_in_update_stmt2430);
            qualified_table_name265=qualified_table_name();

            state._fsp--;

            adaptor.addChild(root_0, qualified_table_name265.getTree());
            SET266=(Token)match(input,SET,FOLLOW_SET_in_update_stmt2434); 
            SET266_tree = (Object)adaptor.create(SET266);
            adaptor.addChild(root_0, SET266_tree);

            pushFollow(FOLLOW_update_set_in_update_stmt2438);
            values=update_set();

            state._fsp--;

            adaptor.addChild(root_0, values.getTree());
            if (list_values==null) list_values=new ArrayList();
            list_values.add(values.getTree());

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:302:26: ( COMMA values+= update_set )*
            loop99:
            do {
                int alt99=2;
                int LA99_0 = input.LA(1);

                if ( (LA99_0==COMMA) ) {
                    alt99=1;
                }


                switch (alt99) {
            	case 1 :
            	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:302:27: COMMA values+= update_set
            	    {
            	    COMMA267=(Token)match(input,COMMA,FOLLOW_COMMA_in_update_stmt2441); 
            	    COMMA267_tree = (Object)adaptor.create(COMMA267);
            	    adaptor.addChild(root_0, COMMA267_tree);

            	    pushFollow(FOLLOW_update_set_in_update_stmt2445);
            	    values=update_set();

            	    state._fsp--;

            	    adaptor.addChild(root_0, values.getTree());
            	    if (list_values==null) list_values=new ArrayList();
            	    list_values.add(values.getTree());


            	    }
            	    break;

            	default :
            	    break loop99;
                }
            } while (true);

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:302:54: ( WHERE expr )?
            int alt100=2;
            int LA100_0 = input.LA(1);

            if ( (LA100_0==WHERE) ) {
                alt100=1;
            }
            switch (alt100) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:302:55: WHERE expr
                    {
                    WHERE268=(Token)match(input,WHERE,FOLLOW_WHERE_in_update_stmt2450); 
                    WHERE268_tree = (Object)adaptor.create(WHERE268);
                    adaptor.addChild(root_0, WHERE268_tree);

                    pushFollow(FOLLOW_expr_in_update_stmt2452);
                    expr269=expr();

                    state._fsp--;

                    adaptor.addChild(root_0, expr269.getTree());

                    }
                    break;

            }

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:302:68: ( operation_limited_clause )?
            int alt101=2;
            int LA101_0 = input.LA(1);

            if ( ((LA101_0>=ORDER && LA101_0<=LIMIT)) ) {
                alt101=1;
            }
            switch (alt101) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:302:69: operation_limited_clause
                    {
                    pushFollow(FOLLOW_operation_limited_clause_in_update_stmt2457);
                    operation_limited_clause270=operation_limited_clause();

                    state._fsp--;

                    adaptor.addChild(root_0, operation_limited_clause270.getTree());

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "update_stmt"

    public static class update_set_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "update_set"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:304:1: update_set : column_name= id EQUALS expr ;
    public final SqlParser.update_set_return update_set() throws RecognitionException {
        SqlParser.update_set_return retval = new SqlParser.update_set_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token EQUALS271=null;
        SqlParser.id_return column_name = null;

        SqlParser.expr_return expr272 = null;


        Object EQUALS271_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:304:11: (column_name= id EQUALS expr )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:304:13: column_name= id EQUALS expr
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_id_in_update_set2468);
            column_name=id();

            state._fsp--;

            adaptor.addChild(root_0, column_name.getTree());
            EQUALS271=(Token)match(input,EQUALS,FOLLOW_EQUALS_in_update_set2470); 
            EQUALS271_tree = (Object)adaptor.create(EQUALS271);
            adaptor.addChild(root_0, EQUALS271_tree);

            pushFollow(FOLLOW_expr_in_update_set2472);
            expr272=expr();

            state._fsp--;

            adaptor.addChild(root_0, expr272.getTree());

            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "update_set"

    public static class delete_stmt_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "delete_stmt"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:307:1: delete_stmt : DELETE FROM qualified_table_name ( WHERE expr )? ( operation_limited_clause )? ;
    public final SqlParser.delete_stmt_return delete_stmt() throws RecognitionException {
        SqlParser.delete_stmt_return retval = new SqlParser.delete_stmt_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token DELETE273=null;
        Token FROM274=null;
        Token WHERE276=null;
        SqlParser.qualified_table_name_return qualified_table_name275 = null;

        SqlParser.expr_return expr277 = null;

        SqlParser.operation_limited_clause_return operation_limited_clause278 = null;


        Object DELETE273_tree=null;
        Object FROM274_tree=null;
        Object WHERE276_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:307:12: ( DELETE FROM qualified_table_name ( WHERE expr )? ( operation_limited_clause )? )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:307:14: DELETE FROM qualified_table_name ( WHERE expr )? ( operation_limited_clause )?
            {
            root_0 = (Object)adaptor.nil();

            DELETE273=(Token)match(input,DELETE,FOLLOW_DELETE_in_delete_stmt2480); 
            DELETE273_tree = (Object)adaptor.create(DELETE273);
            adaptor.addChild(root_0, DELETE273_tree);

            FROM274=(Token)match(input,FROM,FOLLOW_FROM_in_delete_stmt2482); 
            FROM274_tree = (Object)adaptor.create(FROM274);
            adaptor.addChild(root_0, FROM274_tree);

            pushFollow(FOLLOW_qualified_table_name_in_delete_stmt2484);
            qualified_table_name275=qualified_table_name();

            state._fsp--;

            adaptor.addChild(root_0, qualified_table_name275.getTree());
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:307:47: ( WHERE expr )?
            int alt102=2;
            int LA102_0 = input.LA(1);

            if ( (LA102_0==WHERE) ) {
                alt102=1;
            }
            switch (alt102) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:307:48: WHERE expr
                    {
                    WHERE276=(Token)match(input,WHERE,FOLLOW_WHERE_in_delete_stmt2487); 
                    WHERE276_tree = (Object)adaptor.create(WHERE276);
                    adaptor.addChild(root_0, WHERE276_tree);

                    pushFollow(FOLLOW_expr_in_delete_stmt2489);
                    expr277=expr();

                    state._fsp--;

                    adaptor.addChild(root_0, expr277.getTree());

                    }
                    break;

            }

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:307:61: ( operation_limited_clause )?
            int alt103=2;
            int LA103_0 = input.LA(1);

            if ( ((LA103_0>=ORDER && LA103_0<=LIMIT)) ) {
                alt103=1;
            }
            switch (alt103) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:307:62: operation_limited_clause
                    {
                    pushFollow(FOLLOW_operation_limited_clause_in_delete_stmt2494);
                    operation_limited_clause278=operation_limited_clause();

                    state._fsp--;

                    adaptor.addChild(root_0, operation_limited_clause278.getTree());

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "delete_stmt"

    public static class begin_stmt_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "begin_stmt"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:310:1: begin_stmt : BEGIN ( DEFERRED | IMMEDIATE | EXCLUSIVE )? ( TRANSACTION )? ;
    public final SqlParser.begin_stmt_return begin_stmt() throws RecognitionException {
        SqlParser.begin_stmt_return retval = new SqlParser.begin_stmt_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token BEGIN279=null;
        Token set280=null;
        Token TRANSACTION281=null;

        Object BEGIN279_tree=null;
        Object set280_tree=null;
        Object TRANSACTION281_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:310:11: ( BEGIN ( DEFERRED | IMMEDIATE | EXCLUSIVE )? ( TRANSACTION )? )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:310:13: BEGIN ( DEFERRED | IMMEDIATE | EXCLUSIVE )? ( TRANSACTION )?
            {
            root_0 = (Object)adaptor.nil();

            BEGIN279=(Token)match(input,BEGIN,FOLLOW_BEGIN_in_begin_stmt2504); 
            BEGIN279_tree = (Object)adaptor.create(BEGIN279);
            adaptor.addChild(root_0, BEGIN279_tree);

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:310:19: ( DEFERRED | IMMEDIATE | EXCLUSIVE )?
            int alt104=2;
            int LA104_0 = input.LA(1);

            if ( ((LA104_0>=DEFERRED && LA104_0<=EXCLUSIVE)) ) {
                alt104=1;
            }
            switch (alt104) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:
                    {
                    set280=(Token)input.LT(1);
                    if ( (input.LA(1)>=DEFERRED && input.LA(1)<=EXCLUSIVE) ) {
                        input.consume();
                        adaptor.addChild(root_0, (Object)adaptor.create(set280));
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }


                    }
                    break;

            }

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:310:55: ( TRANSACTION )?
            int alt105=2;
            int LA105_0 = input.LA(1);

            if ( (LA105_0==TRANSACTION) ) {
                alt105=1;
            }
            switch (alt105) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:310:56: TRANSACTION
                    {
                    TRANSACTION281=(Token)match(input,TRANSACTION,FOLLOW_TRANSACTION_in_begin_stmt2520); 
                    TRANSACTION281_tree = (Object)adaptor.create(TRANSACTION281);
                    adaptor.addChild(root_0, TRANSACTION281_tree);


                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "begin_stmt"

    public static class commit_stmt_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "commit_stmt"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:313:1: commit_stmt : ( COMMIT | END ) ( TRANSACTION )? ;
    public final SqlParser.commit_stmt_return commit_stmt() throws RecognitionException {
        SqlParser.commit_stmt_return retval = new SqlParser.commit_stmt_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set282=null;
        Token TRANSACTION283=null;

        Object set282_tree=null;
        Object TRANSACTION283_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:313:12: ( ( COMMIT | END ) ( TRANSACTION )? )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:313:14: ( COMMIT | END ) ( TRANSACTION )?
            {
            root_0 = (Object)adaptor.nil();

            set282=(Token)input.LT(1);
            if ( input.LA(1)==END||input.LA(1)==COMMIT ) {
                input.consume();
                adaptor.addChild(root_0, (Object)adaptor.create(set282));
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:313:29: ( TRANSACTION )?
            int alt106=2;
            int LA106_0 = input.LA(1);

            if ( (LA106_0==TRANSACTION) ) {
                alt106=1;
            }
            switch (alt106) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:313:30: TRANSACTION
                    {
                    TRANSACTION283=(Token)match(input,TRANSACTION,FOLLOW_TRANSACTION_in_commit_stmt2539); 
                    TRANSACTION283_tree = (Object)adaptor.create(TRANSACTION283);
                    adaptor.addChild(root_0, TRANSACTION283_tree);


                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "commit_stmt"

    public static class rollback_stmt_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "rollback_stmt"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:316:1: rollback_stmt : ROLLBACK ( TRANSACTION )? ( TO ( SAVEPOINT )? savepoint_name= id )? ;
    public final SqlParser.rollback_stmt_return rollback_stmt() throws RecognitionException {
        SqlParser.rollback_stmt_return retval = new SqlParser.rollback_stmt_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ROLLBACK284=null;
        Token TRANSACTION285=null;
        Token TO286=null;
        Token SAVEPOINT287=null;
        SqlParser.id_return savepoint_name = null;


        Object ROLLBACK284_tree=null;
        Object TRANSACTION285_tree=null;
        Object TO286_tree=null;
        Object SAVEPOINT287_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:316:14: ( ROLLBACK ( TRANSACTION )? ( TO ( SAVEPOINT )? savepoint_name= id )? )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:316:16: ROLLBACK ( TRANSACTION )? ( TO ( SAVEPOINT )? savepoint_name= id )?
            {
            root_0 = (Object)adaptor.nil();

            ROLLBACK284=(Token)match(input,ROLLBACK,FOLLOW_ROLLBACK_in_rollback_stmt2549); 
            ROLLBACK284_tree = (Object)adaptor.create(ROLLBACK284);
            adaptor.addChild(root_0, ROLLBACK284_tree);

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:316:25: ( TRANSACTION )?
            int alt107=2;
            int LA107_0 = input.LA(1);

            if ( (LA107_0==TRANSACTION) ) {
                alt107=1;
            }
            switch (alt107) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:316:26: TRANSACTION
                    {
                    TRANSACTION285=(Token)match(input,TRANSACTION,FOLLOW_TRANSACTION_in_rollback_stmt2552); 
                    TRANSACTION285_tree = (Object)adaptor.create(TRANSACTION285);
                    adaptor.addChild(root_0, TRANSACTION285_tree);


                    }
                    break;

            }

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:316:40: ( TO ( SAVEPOINT )? savepoint_name= id )?
            int alt109=2;
            int LA109_0 = input.LA(1);

            if ( (LA109_0==TO) ) {
                alt109=1;
            }
            switch (alt109) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:316:41: TO ( SAVEPOINT )? savepoint_name= id
                    {
                    TO286=(Token)match(input,TO,FOLLOW_TO_in_rollback_stmt2557); 
                    TO286_tree = (Object)adaptor.create(TO286);
                    adaptor.addChild(root_0, TO286_tree);

                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:316:44: ( SAVEPOINT )?
                    int alt108=2;
                    int LA108_0 = input.LA(1);

                    if ( (LA108_0==SAVEPOINT) ) {
                        int LA108_1 = input.LA(2);

                        if ( ((LA108_1>=EXPLAIN && LA108_1<=PLAN)||(LA108_1>=INDEXED && LA108_1<=BY)||(LA108_1>=OR && LA108_1<=ESCAPE)||(LA108_1>=IS && LA108_1<=BETWEEN)||(LA108_1>=COLLATE && LA108_1<=THEN)||(LA108_1>=CURRENT_TIME && LA108_1<=CURRENT_TIMESTAMP)||(LA108_1>=RAISE && LA108_1<=FAIL)||(LA108_1>=PRAGMA && LA108_1<=FOR)||(LA108_1>=UNIQUE && LA108_1<=ALTER)||(LA108_1>=RENAME && LA108_1<=SCHEMA)) ) {
                            alt108=1;
                        }
                    }
                    switch (alt108) {
                        case 1 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:316:45: SAVEPOINT
                            {
                            SAVEPOINT287=(Token)match(input,SAVEPOINT,FOLLOW_SAVEPOINT_in_rollback_stmt2560); 
                            SAVEPOINT287_tree = (Object)adaptor.create(SAVEPOINT287);
                            adaptor.addChild(root_0, SAVEPOINT287_tree);


                            }
                            break;

                    }

                    pushFollow(FOLLOW_id_in_rollback_stmt2566);
                    savepoint_name=id();

                    state._fsp--;

                    adaptor.addChild(root_0, savepoint_name.getTree());

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "rollback_stmt"

    public static class savepoint_stmt_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "savepoint_stmt"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:319:1: savepoint_stmt : SAVEPOINT savepoint_name= id ;
    public final SqlParser.savepoint_stmt_return savepoint_stmt() throws RecognitionException {
        SqlParser.savepoint_stmt_return retval = new SqlParser.savepoint_stmt_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token SAVEPOINT288=null;
        SqlParser.id_return savepoint_name = null;


        Object SAVEPOINT288_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:319:15: ( SAVEPOINT savepoint_name= id )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:319:17: SAVEPOINT savepoint_name= id
            {
            root_0 = (Object)adaptor.nil();

            SAVEPOINT288=(Token)match(input,SAVEPOINT,FOLLOW_SAVEPOINT_in_savepoint_stmt2576); 
            SAVEPOINT288_tree = (Object)adaptor.create(SAVEPOINT288);
            adaptor.addChild(root_0, SAVEPOINT288_tree);

            pushFollow(FOLLOW_id_in_savepoint_stmt2580);
            savepoint_name=id();

            state._fsp--;

            adaptor.addChild(root_0, savepoint_name.getTree());

            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "savepoint_stmt"

    public static class release_stmt_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "release_stmt"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:322:1: release_stmt : RELEASE ( SAVEPOINT )? savepoint_name= id ;
    public final SqlParser.release_stmt_return release_stmt() throws RecognitionException {
        SqlParser.release_stmt_return retval = new SqlParser.release_stmt_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token RELEASE289=null;
        Token SAVEPOINT290=null;
        SqlParser.id_return savepoint_name = null;


        Object RELEASE289_tree=null;
        Object SAVEPOINT290_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:322:13: ( RELEASE ( SAVEPOINT )? savepoint_name= id )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:322:15: RELEASE ( SAVEPOINT )? savepoint_name= id
            {
            root_0 = (Object)adaptor.nil();

            RELEASE289=(Token)match(input,RELEASE,FOLLOW_RELEASE_in_release_stmt2588); 
            RELEASE289_tree = (Object)adaptor.create(RELEASE289);
            adaptor.addChild(root_0, RELEASE289_tree);

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:322:23: ( SAVEPOINT )?
            int alt110=2;
            int LA110_0 = input.LA(1);

            if ( (LA110_0==SAVEPOINT) ) {
                int LA110_1 = input.LA(2);

                if ( ((LA110_1>=EXPLAIN && LA110_1<=PLAN)||(LA110_1>=INDEXED && LA110_1<=BY)||(LA110_1>=OR && LA110_1<=ESCAPE)||(LA110_1>=IS && LA110_1<=BETWEEN)||(LA110_1>=COLLATE && LA110_1<=THEN)||(LA110_1>=CURRENT_TIME && LA110_1<=CURRENT_TIMESTAMP)||(LA110_1>=RAISE && LA110_1<=FAIL)||(LA110_1>=PRAGMA && LA110_1<=FOR)||(LA110_1>=UNIQUE && LA110_1<=ALTER)||(LA110_1>=RENAME && LA110_1<=SCHEMA)) ) {
                    alt110=1;
                }
            }
            switch (alt110) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:322:24: SAVEPOINT
                    {
                    SAVEPOINT290=(Token)match(input,SAVEPOINT,FOLLOW_SAVEPOINT_in_release_stmt2591); 
                    SAVEPOINT290_tree = (Object)adaptor.create(SAVEPOINT290);
                    adaptor.addChild(root_0, SAVEPOINT290_tree);


                    }
                    break;

            }

            pushFollow(FOLLOW_id_in_release_stmt2597);
            savepoint_name=id();

            state._fsp--;

            adaptor.addChild(root_0, savepoint_name.getTree());

            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "release_stmt"

    public static class table_conflict_clause_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "table_conflict_clause"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:329:1: table_conflict_clause : ON CONFLICT ( ROLLBACK | ABORT | FAIL | IGNORE | REPLACE ) ;
    public final SqlParser.table_conflict_clause_return table_conflict_clause() throws RecognitionException {
        SqlParser.table_conflict_clause_return retval = new SqlParser.table_conflict_clause_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ON291=null;
        Token CONFLICT292=null;
        Token set293=null;

        Object ON291_tree=null;
        Object CONFLICT292_tree=null;
        Object set293_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:329:22: ( ON CONFLICT ( ROLLBACK | ABORT | FAIL | IGNORE | REPLACE ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:329:24: ON CONFLICT ( ROLLBACK | ABORT | FAIL | IGNORE | REPLACE )
            {
            root_0 = (Object)adaptor.nil();

            ON291=(Token)match(input,ON,FOLLOW_ON_in_table_conflict_clause2609); 
            CONFLICT292=(Token)match(input,CONFLICT,FOLLOW_CONFLICT_in_table_conflict_clause2612); 
            CONFLICT292_tree = (Object)adaptor.create(CONFLICT292);
            root_0 = (Object)adaptor.becomeRoot(CONFLICT292_tree, root_0);

            set293=(Token)input.LT(1);
            if ( (input.LA(1)>=IGNORE && input.LA(1)<=FAIL)||input.LA(1)==REPLACE ) {
                input.consume();
                adaptor.addChild(root_0, (Object)adaptor.create(set293));
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "table_conflict_clause"

    public static class create_virtual_table_stmt_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "create_virtual_table_stmt"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:333:1: create_virtual_table_stmt : CREATE VIRTUAL TABLE (database_name= id DOT )? table_name= id USING module_name= id ( LPAREN column_def ( COMMA column_def )* RPAREN )? ;
    public final SqlParser.create_virtual_table_stmt_return create_virtual_table_stmt() throws RecognitionException {
        SqlParser.create_virtual_table_stmt_return retval = new SqlParser.create_virtual_table_stmt_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token CREATE294=null;
        Token VIRTUAL295=null;
        Token TABLE296=null;
        Token DOT297=null;
        Token USING298=null;
        Token LPAREN299=null;
        Token COMMA301=null;
        Token RPAREN303=null;
        SqlParser.id_return database_name = null;

        SqlParser.id_return table_name = null;

        SqlParser.id_return module_name = null;

        SqlParser.column_def_return column_def300 = null;

        SqlParser.column_def_return column_def302 = null;


        Object CREATE294_tree=null;
        Object VIRTUAL295_tree=null;
        Object TABLE296_tree=null;
        Object DOT297_tree=null;
        Object USING298_tree=null;
        Object LPAREN299_tree=null;
        Object COMMA301_tree=null;
        Object RPAREN303_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:333:26: ( CREATE VIRTUAL TABLE (database_name= id DOT )? table_name= id USING module_name= id ( LPAREN column_def ( COMMA column_def )* RPAREN )? )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:333:28: CREATE VIRTUAL TABLE (database_name= id DOT )? table_name= id USING module_name= id ( LPAREN column_def ( COMMA column_def )* RPAREN )?
            {
            root_0 = (Object)adaptor.nil();

            CREATE294=(Token)match(input,CREATE,FOLLOW_CREATE_in_create_virtual_table_stmt2642); 
            CREATE294_tree = (Object)adaptor.create(CREATE294);
            adaptor.addChild(root_0, CREATE294_tree);

            VIRTUAL295=(Token)match(input,VIRTUAL,FOLLOW_VIRTUAL_in_create_virtual_table_stmt2644); 
            VIRTUAL295_tree = (Object)adaptor.create(VIRTUAL295);
            adaptor.addChild(root_0, VIRTUAL295_tree);

            TABLE296=(Token)match(input,TABLE,FOLLOW_TABLE_in_create_virtual_table_stmt2646); 
            TABLE296_tree = (Object)adaptor.create(TABLE296);
            adaptor.addChild(root_0, TABLE296_tree);

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:333:49: (database_name= id DOT )?
            int alt111=2;
            int LA111_0 = input.LA(1);

            if ( (LA111_0==ID) ) {
                int LA111_1 = input.LA(2);

                if ( (LA111_1==DOT) ) {
                    alt111=1;
                }
            }
            else if ( ((LA111_0>=EXPLAIN && LA111_0<=PLAN)||(LA111_0>=INDEXED && LA111_0<=BY)||(LA111_0>=OR && LA111_0<=ESCAPE)||(LA111_0>=IS && LA111_0<=BETWEEN)||LA111_0==COLLATE||(LA111_0>=DISTINCT && LA111_0<=THEN)||(LA111_0>=CURRENT_TIME && LA111_0<=CURRENT_TIMESTAMP)||(LA111_0>=RAISE && LA111_0<=FAIL)||(LA111_0>=PRAGMA && LA111_0<=FOR)||(LA111_0>=UNIQUE && LA111_0<=ALTER)||(LA111_0>=RENAME && LA111_0<=SCHEMA)) ) {
                int LA111_2 = input.LA(2);

                if ( (LA111_2==DOT) ) {
                    alt111=1;
                }
            }
            switch (alt111) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:333:50: database_name= id DOT
                    {
                    pushFollow(FOLLOW_id_in_create_virtual_table_stmt2651);
                    database_name=id();

                    state._fsp--;

                    adaptor.addChild(root_0, database_name.getTree());
                    DOT297=(Token)match(input,DOT,FOLLOW_DOT_in_create_virtual_table_stmt2653); 
                    DOT297_tree = (Object)adaptor.create(DOT297);
                    adaptor.addChild(root_0, DOT297_tree);


                    }
                    break;

            }

            pushFollow(FOLLOW_id_in_create_virtual_table_stmt2659);
            table_name=id();

            state._fsp--;

            adaptor.addChild(root_0, table_name.getTree());
            USING298=(Token)match(input,USING,FOLLOW_USING_in_create_virtual_table_stmt2663); 
            USING298_tree = (Object)adaptor.create(USING298);
            adaptor.addChild(root_0, USING298_tree);

            pushFollow(FOLLOW_id_in_create_virtual_table_stmt2667);
            module_name=id();

            state._fsp--;

            adaptor.addChild(root_0, module_name.getTree());
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:334:24: ( LPAREN column_def ( COMMA column_def )* RPAREN )?
            int alt113=2;
            int LA113_0 = input.LA(1);

            if ( (LA113_0==LPAREN) ) {
                alt113=1;
            }
            switch (alt113) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:334:25: LPAREN column_def ( COMMA column_def )* RPAREN
                    {
                    LPAREN299=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_create_virtual_table_stmt2670); 
                    LPAREN299_tree = (Object)adaptor.create(LPAREN299);
                    adaptor.addChild(root_0, LPAREN299_tree);

                    pushFollow(FOLLOW_column_def_in_create_virtual_table_stmt2672);
                    column_def300=column_def();

                    state._fsp--;

                    adaptor.addChild(root_0, column_def300.getTree());
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:334:43: ( COMMA column_def )*
                    loop112:
                    do {
                        int alt112=2;
                        int LA112_0 = input.LA(1);

                        if ( (LA112_0==COMMA) ) {
                            alt112=1;
                        }


                        switch (alt112) {
                    	case 1 :
                    	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:334:44: COMMA column_def
                    	    {
                    	    COMMA301=(Token)match(input,COMMA,FOLLOW_COMMA_in_create_virtual_table_stmt2675); 
                    	    COMMA301_tree = (Object)adaptor.create(COMMA301);
                    	    adaptor.addChild(root_0, COMMA301_tree);

                    	    pushFollow(FOLLOW_column_def_in_create_virtual_table_stmt2677);
                    	    column_def302=column_def();

                    	    state._fsp--;

                    	    adaptor.addChild(root_0, column_def302.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop112;
                        }
                    } while (true);

                    RPAREN303=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_create_virtual_table_stmt2681); 
                    RPAREN303_tree = (Object)adaptor.create(RPAREN303);
                    adaptor.addChild(root_0, RPAREN303_tree);


                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "create_virtual_table_stmt"

    public static class create_table_stmt_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "create_table_stmt"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:337:1: create_table_stmt : CREATE ( TEMPORARY )? TABLE ( IF NOT EXISTS )? (database_name= id DOT )? table_name= id ( LPAREN column_def ( COMMA column_def )* ( COMMA table_constraint )* RPAREN | AS select_stmt ) -> ^( CREATE_TABLE ^( OPTIONS ( TEMPORARY )? ( EXISTS )? ) ^( $table_name ( $database_name)? ) ( ^( COLUMNS ( column_def )+ ) )? ( ^( CONSTRAINTS ( table_constraint )* ) )? ( select_stmt )? ) ;
    public final SqlParser.create_table_stmt_return create_table_stmt() throws RecognitionException {
        SqlParser.create_table_stmt_return retval = new SqlParser.create_table_stmt_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token CREATE304=null;
        Token TEMPORARY305=null;
        Token TABLE306=null;
        Token IF307=null;
        Token NOT308=null;
        Token EXISTS309=null;
        Token DOT310=null;
        Token LPAREN311=null;
        Token COMMA313=null;
        Token COMMA315=null;
        Token RPAREN317=null;
        Token AS318=null;
        SqlParser.id_return database_name = null;

        SqlParser.id_return table_name = null;

        SqlParser.column_def_return column_def312 = null;

        SqlParser.column_def_return column_def314 = null;

        SqlParser.table_constraint_return table_constraint316 = null;

        SqlParser.select_stmt_return select_stmt319 = null;


        Object CREATE304_tree=null;
        Object TEMPORARY305_tree=null;
        Object TABLE306_tree=null;
        Object IF307_tree=null;
        Object NOT308_tree=null;
        Object EXISTS309_tree=null;
        Object DOT310_tree=null;
        Object LPAREN311_tree=null;
        Object COMMA313_tree=null;
        Object COMMA315_tree=null;
        Object RPAREN317_tree=null;
        Object AS318_tree=null;
        RewriteRuleTokenStream stream_TABLE=new RewriteRuleTokenStream(adaptor,"token TABLE");
        RewriteRuleTokenStream stream_AS=new RewriteRuleTokenStream(adaptor,"token AS");
        RewriteRuleTokenStream stream_RPAREN=new RewriteRuleTokenStream(adaptor,"token RPAREN");
        RewriteRuleTokenStream stream_CREATE=new RewriteRuleTokenStream(adaptor,"token CREATE");
        RewriteRuleTokenStream stream_NOT=new RewriteRuleTokenStream(adaptor,"token NOT");
        RewriteRuleTokenStream stream_EXISTS=new RewriteRuleTokenStream(adaptor,"token EXISTS");
        RewriteRuleTokenStream stream_DOT=new RewriteRuleTokenStream(adaptor,"token DOT");
        RewriteRuleTokenStream stream_COMMA=new RewriteRuleTokenStream(adaptor,"token COMMA");
        RewriteRuleTokenStream stream_TEMPORARY=new RewriteRuleTokenStream(adaptor,"token TEMPORARY");
        RewriteRuleTokenStream stream_LPAREN=new RewriteRuleTokenStream(adaptor,"token LPAREN");
        RewriteRuleTokenStream stream_IF=new RewriteRuleTokenStream(adaptor,"token IF");
        RewriteRuleSubtreeStream stream_id=new RewriteRuleSubtreeStream(adaptor,"rule id");
        RewriteRuleSubtreeStream stream_select_stmt=new RewriteRuleSubtreeStream(adaptor,"rule select_stmt");
        RewriteRuleSubtreeStream stream_column_def=new RewriteRuleSubtreeStream(adaptor,"rule column_def");
        RewriteRuleSubtreeStream stream_table_constraint=new RewriteRuleSubtreeStream(adaptor,"rule table_constraint");
        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:337:18: ( CREATE ( TEMPORARY )? TABLE ( IF NOT EXISTS )? (database_name= id DOT )? table_name= id ( LPAREN column_def ( COMMA column_def )* ( COMMA table_constraint )* RPAREN | AS select_stmt ) -> ^( CREATE_TABLE ^( OPTIONS ( TEMPORARY )? ( EXISTS )? ) ^( $table_name ( $database_name)? ) ( ^( COLUMNS ( column_def )+ ) )? ( ^( CONSTRAINTS ( table_constraint )* ) )? ( select_stmt )? ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:337:20: CREATE ( TEMPORARY )? TABLE ( IF NOT EXISTS )? (database_name= id DOT )? table_name= id ( LPAREN column_def ( COMMA column_def )* ( COMMA table_constraint )* RPAREN | AS select_stmt )
            {
            CREATE304=(Token)match(input,CREATE,FOLLOW_CREATE_in_create_table_stmt2691);  
            stream_CREATE.add(CREATE304);

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:337:27: ( TEMPORARY )?
            int alt114=2;
            int LA114_0 = input.LA(1);

            if ( (LA114_0==TEMPORARY) ) {
                alt114=1;
            }
            switch (alt114) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:337:27: TEMPORARY
                    {
                    TEMPORARY305=(Token)match(input,TEMPORARY,FOLLOW_TEMPORARY_in_create_table_stmt2693);  
                    stream_TEMPORARY.add(TEMPORARY305);


                    }
                    break;

            }

            TABLE306=(Token)match(input,TABLE,FOLLOW_TABLE_in_create_table_stmt2696);  
            stream_TABLE.add(TABLE306);

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:337:44: ( IF NOT EXISTS )?
            int alt115=2;
            int LA115_0 = input.LA(1);

            if ( (LA115_0==IF) ) {
                int LA115_1 = input.LA(2);

                if ( (LA115_1==NOT) ) {
                    alt115=1;
                }
            }
            switch (alt115) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:337:45: IF NOT EXISTS
                    {
                    IF307=(Token)match(input,IF,FOLLOW_IF_in_create_table_stmt2699);  
                    stream_IF.add(IF307);

                    NOT308=(Token)match(input,NOT,FOLLOW_NOT_in_create_table_stmt2701);  
                    stream_NOT.add(NOT308);

                    EXISTS309=(Token)match(input,EXISTS,FOLLOW_EXISTS_in_create_table_stmt2703);  
                    stream_EXISTS.add(EXISTS309);


                    }
                    break;

            }

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:337:61: (database_name= id DOT )?
            int alt116=2;
            int LA116_0 = input.LA(1);

            if ( (LA116_0==ID) ) {
                int LA116_1 = input.LA(2);

                if ( (LA116_1==DOT) ) {
                    alt116=1;
                }
            }
            else if ( ((LA116_0>=EXPLAIN && LA116_0<=PLAN)||(LA116_0>=INDEXED && LA116_0<=BY)||(LA116_0>=OR && LA116_0<=ESCAPE)||(LA116_0>=IS && LA116_0<=BETWEEN)||LA116_0==COLLATE||(LA116_0>=DISTINCT && LA116_0<=THEN)||(LA116_0>=CURRENT_TIME && LA116_0<=CURRENT_TIMESTAMP)||(LA116_0>=RAISE && LA116_0<=FAIL)||(LA116_0>=PRAGMA && LA116_0<=FOR)||(LA116_0>=UNIQUE && LA116_0<=ALTER)||(LA116_0>=RENAME && LA116_0<=SCHEMA)) ) {
                int LA116_2 = input.LA(2);

                if ( (LA116_2==DOT) ) {
                    alt116=1;
                }
            }
            switch (alt116) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:337:62: database_name= id DOT
                    {
                    pushFollow(FOLLOW_id_in_create_table_stmt2710);
                    database_name=id();

                    state._fsp--;

                    stream_id.add(database_name.getTree());
                    DOT310=(Token)match(input,DOT,FOLLOW_DOT_in_create_table_stmt2712);  
                    stream_DOT.add(DOT310);


                    }
                    break;

            }

            pushFollow(FOLLOW_id_in_create_table_stmt2718);
            table_name=id();

            state._fsp--;

            stream_id.add(table_name.getTree());
            builder.addEntity( table_name.getTree().toString() ); 
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:338:3: ( LPAREN column_def ( COMMA column_def )* ( COMMA table_constraint )* RPAREN | AS select_stmt )
            int alt119=2;
            int LA119_0 = input.LA(1);

            if ( (LA119_0==LPAREN) ) {
                alt119=1;
            }
            else if ( (LA119_0==AS) ) {
                alt119=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 119, 0, input);

                throw nvae;
            }
            switch (alt119) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:338:5: LPAREN column_def ( COMMA column_def )* ( COMMA table_constraint )* RPAREN
                    {
                    LPAREN311=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_create_table_stmt2726);  
                    stream_LPAREN.add(LPAREN311);

                    pushFollow(FOLLOW_column_def_in_create_table_stmt2728);
                    column_def312=column_def();

                    state._fsp--;

                    stream_column_def.add(column_def312.getTree());
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:338:23: ( COMMA column_def )*
                    loop117:
                    do {
                        int alt117=2;
                        alt117 = dfa117.predict(input);
                        switch (alt117) {
                    	case 1 :
                    	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:338:24: COMMA column_def
                    	    {
                    	    COMMA313=(Token)match(input,COMMA,FOLLOW_COMMA_in_create_table_stmt2731);  
                    	    stream_COMMA.add(COMMA313);

                    	    pushFollow(FOLLOW_column_def_in_create_table_stmt2733);
                    	    column_def314=column_def();

                    	    state._fsp--;

                    	    stream_column_def.add(column_def314.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop117;
                        }
                    } while (true);

                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:338:43: ( COMMA table_constraint )*
                    loop118:
                    do {
                        int alt118=2;
                        int LA118_0 = input.LA(1);

                        if ( (LA118_0==COMMA) ) {
                            alt118=1;
                        }


                        switch (alt118) {
                    	case 1 :
                    	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:338:44: COMMA table_constraint
                    	    {
                    	    COMMA315=(Token)match(input,COMMA,FOLLOW_COMMA_in_create_table_stmt2738);  
                    	    stream_COMMA.add(COMMA315);

                    	    pushFollow(FOLLOW_table_constraint_in_create_table_stmt2740);
                    	    table_constraint316=table_constraint();

                    	    state._fsp--;

                    	    stream_table_constraint.add(table_constraint316.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop118;
                        }
                    } while (true);

                    RPAREN317=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_create_table_stmt2744);  
                    stream_RPAREN.add(RPAREN317);


                    }
                    break;
                case 2 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:339:5: AS select_stmt
                    {
                    AS318=(Token)match(input,AS,FOLLOW_AS_in_create_table_stmt2750);  
                    stream_AS.add(AS318);

                    pushFollow(FOLLOW_select_stmt_in_create_table_stmt2752);
                    select_stmt319=select_stmt();

                    state._fsp--;

                    stream_select_stmt.add(select_stmt319.getTree());

                    }
                    break;

            }



            // AST REWRITE
            // elements: database_name, table_constraint, EXISTS, column_def, select_stmt, table_name, TEMPORARY
            // token labels: 
            // rule labels: database_name, retval, table_name
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_database_name=new RewriteRuleSubtreeStream(adaptor,"rule database_name",database_name!=null?database_name.tree:null);
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_table_name=new RewriteRuleSubtreeStream(adaptor,"rule table_name",table_name!=null?table_name.tree:null);

            root_0 = (Object)adaptor.nil();
            // 340:1: -> ^( CREATE_TABLE ^( OPTIONS ( TEMPORARY )? ( EXISTS )? ) ^( $table_name ( $database_name)? ) ( ^( COLUMNS ( column_def )+ ) )? ( ^( CONSTRAINTS ( table_constraint )* ) )? ( select_stmt )? )
            {
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:340:4: ^( CREATE_TABLE ^( OPTIONS ( TEMPORARY )? ( EXISTS )? ) ^( $table_name ( $database_name)? ) ( ^( COLUMNS ( column_def )+ ) )? ( ^( CONSTRAINTS ( table_constraint )* ) )? ( select_stmt )? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(CREATE_TABLE, "CREATE_TABLE"), root_1);

                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:340:19: ^( OPTIONS ( TEMPORARY )? ( EXISTS )? )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(OPTIONS, "OPTIONS"), root_2);

                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:340:29: ( TEMPORARY )?
                if ( stream_TEMPORARY.hasNext() ) {
                    adaptor.addChild(root_2, stream_TEMPORARY.nextNode());

                }
                stream_TEMPORARY.reset();
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:340:40: ( EXISTS )?
                if ( stream_EXISTS.hasNext() ) {
                    adaptor.addChild(root_2, stream_EXISTS.nextNode());

                }
                stream_EXISTS.reset();

                adaptor.addChild(root_1, root_2);
                }
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:340:49: ^( $table_name ( $database_name)? )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot(stream_table_name.nextNode(), root_2);

                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:340:63: ( $database_name)?
                if ( stream_database_name.hasNext() ) {
                    adaptor.addChild(root_2, stream_database_name.nextTree());

                }
                stream_database_name.reset();

                adaptor.addChild(root_1, root_2);
                }
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:341:3: ( ^( COLUMNS ( column_def )+ ) )?
                if ( stream_column_def.hasNext() ) {
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:341:3: ^( COLUMNS ( column_def )+ )
                    {
                    Object root_2 = (Object)adaptor.nil();
                    root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(COLUMNS, "COLUMNS"), root_2);

                    if ( !(stream_column_def.hasNext()) ) {
                        throw new RewriteEarlyExitException();
                    }
                    while ( stream_column_def.hasNext() ) {
                        adaptor.addChild(root_2, stream_column_def.nextTree());

                    }
                    stream_column_def.reset();

                    adaptor.addChild(root_1, root_2);
                    }

                }
                stream_column_def.reset();
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:341:27: ( ^( CONSTRAINTS ( table_constraint )* ) )?
                if ( stream_table_constraint.hasNext() ) {
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:341:27: ^( CONSTRAINTS ( table_constraint )* )
                    {
                    Object root_2 = (Object)adaptor.nil();
                    root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(CONSTRAINTS, "CONSTRAINTS"), root_2);

                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:341:41: ( table_constraint )*
                    while ( stream_table_constraint.hasNext() ) {
                        adaptor.addChild(root_2, stream_table_constraint.nextTree());

                    }
                    stream_table_constraint.reset();

                    adaptor.addChild(root_1, root_2);
                    }

                }
                stream_table_constraint.reset();
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:341:61: ( select_stmt )?
                if ( stream_select_stmt.hasNext() ) {
                    adaptor.addChild(root_1, stream_select_stmt.nextTree());

                }
                stream_select_stmt.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "create_table_stmt"

    public static class column_def_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "column_def"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:343:1: column_def : name= id_column_def ( type_name )? ( column_constraint )* -> ^( $name ^( CONSTRAINTS ( column_constraint )* ) ( type_name )? ) ;
    public final SqlParser.column_def_return column_def() throws RecognitionException {
        SqlParser.column_def_return retval = new SqlParser.column_def_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        SqlParser.id_column_def_return name = null;

        SqlParser.type_name_return type_name320 = null;

        SqlParser.column_constraint_return column_constraint321 = null;


        RewriteRuleSubtreeStream stream_column_constraint=new RewriteRuleSubtreeStream(adaptor,"rule column_constraint");
        RewriteRuleSubtreeStream stream_id_column_def=new RewriteRuleSubtreeStream(adaptor,"rule id_column_def");
        RewriteRuleSubtreeStream stream_type_name=new RewriteRuleSubtreeStream(adaptor,"rule type_name");
        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:343:11: (name= id_column_def ( type_name )? ( column_constraint )* -> ^( $name ^( CONSTRAINTS ( column_constraint )* ) ( type_name )? ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:343:13: name= id_column_def ( type_name )? ( column_constraint )*
            {
            pushFollow(FOLLOW_id_column_def_in_column_def2808);
            name=id_column_def();

            state._fsp--;

            stream_id_column_def.add(name.getTree());
             builder.addAttributeToLastEntity( name.getTree().toString() ); 
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:343:99: ( type_name )?
            int alt120=2;
            alt120 = dfa120.predict(input);
            switch (alt120) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:343:99: type_name
                    {
                    pushFollow(FOLLOW_type_name_in_column_def2812);
                    type_name320=type_name();

                    state._fsp--;

                    stream_type_name.add(type_name320.getTree());

                    }
                    break;

            }

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:343:111: ( column_constraint )*
            loop121:
            do {
                int alt121=2;
                alt121 = dfa121.predict(input);
                switch (alt121) {
            	case 1 :
            	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:343:111: column_constraint
            	    {
            	    pushFollow(FOLLOW_column_constraint_in_column_def2816);
            	    column_constraint321=column_constraint();

            	    state._fsp--;

            	    stream_column_constraint.add(column_constraint321.getTree());

            	    }
            	    break;

            	default :
            	    break loop121;
                }
            } while (true);



            // AST REWRITE
            // elements: column_constraint, name, type_name
            // token labels: 
            // rule labels: retval, name
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_name=new RewriteRuleSubtreeStream(adaptor,"rule name",name!=null?name.tree:null);

            root_0 = (Object)adaptor.nil();
            // 344:1: -> ^( $name ^( CONSTRAINTS ( column_constraint )* ) ( type_name )? )
            {
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:344:4: ^( $name ^( CONSTRAINTS ( column_constraint )* ) ( type_name )? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(stream_name.nextNode(), root_1);

                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:344:12: ^( CONSTRAINTS ( column_constraint )* )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(CONSTRAINTS, "CONSTRAINTS"), root_2);

                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:344:26: ( column_constraint )*
                while ( stream_column_constraint.hasNext() ) {
                    adaptor.addChild(root_2, stream_column_constraint.nextTree());

                }
                stream_column_constraint.reset();

                adaptor.addChild(root_1, root_2);
                }
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:344:46: ( type_name )?
                if ( stream_type_name.hasNext() ) {
                    adaptor.addChild(root_1, stream_type_name.nextTree());

                }
                stream_type_name.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "column_def"

    public static class column_constraint_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "column_constraint"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:346:1: column_constraint : ( CONSTRAINT name= id )? ( column_constraint_pk | column_constraint_null | column_constraint_not_null | column_constraint_not_for_replication | column_constraint_unique | column_constraint_check | column_constraint_default | column_constraint_collate | fk_clause ) -> ^( COLUMN_CONSTRAINT ( column_constraint_pk )? ( column_constraint_null )? ( column_constraint_not_null )? ( column_constraint_not_for_replication )? ( column_constraint_unique )? ( column_constraint_check )? ( column_constraint_default )? ( column_constraint_collate )? ( fk_clause )? ( $name)? ) ;
    public final SqlParser.column_constraint_return column_constraint() throws RecognitionException {
        SqlParser.column_constraint_return retval = new SqlParser.column_constraint_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token CONSTRAINT322=null;
        SqlParser.id_return name = null;

        SqlParser.column_constraint_pk_return column_constraint_pk323 = null;

        SqlParser.column_constraint_null_return column_constraint_null324 = null;

        SqlParser.column_constraint_not_null_return column_constraint_not_null325 = null;

        SqlParser.column_constraint_not_for_replication_return column_constraint_not_for_replication326 = null;

        SqlParser.column_constraint_unique_return column_constraint_unique327 = null;

        SqlParser.column_constraint_check_return column_constraint_check328 = null;

        SqlParser.column_constraint_default_return column_constraint_default329 = null;

        SqlParser.column_constraint_collate_return column_constraint_collate330 = null;

        SqlParser.fk_clause_return fk_clause331 = null;


        Object CONSTRAINT322_tree=null;
        RewriteRuleTokenStream stream_CONSTRAINT=new RewriteRuleTokenStream(adaptor,"token CONSTRAINT");
        RewriteRuleSubtreeStream stream_id=new RewriteRuleSubtreeStream(adaptor,"rule id");
        RewriteRuleSubtreeStream stream_column_constraint_default=new RewriteRuleSubtreeStream(adaptor,"rule column_constraint_default");
        RewriteRuleSubtreeStream stream_column_constraint_check=new RewriteRuleSubtreeStream(adaptor,"rule column_constraint_check");
        RewriteRuleSubtreeStream stream_column_constraint_pk=new RewriteRuleSubtreeStream(adaptor,"rule column_constraint_pk");
        RewriteRuleSubtreeStream stream_column_constraint_null=new RewriteRuleSubtreeStream(adaptor,"rule column_constraint_null");
        RewriteRuleSubtreeStream stream_column_constraint_collate=new RewriteRuleSubtreeStream(adaptor,"rule column_constraint_collate");
        RewriteRuleSubtreeStream stream_column_constraint_unique=new RewriteRuleSubtreeStream(adaptor,"rule column_constraint_unique");
        RewriteRuleSubtreeStream stream_fk_clause=new RewriteRuleSubtreeStream(adaptor,"rule fk_clause");
        RewriteRuleSubtreeStream stream_column_constraint_not_null=new RewriteRuleSubtreeStream(adaptor,"rule column_constraint_not_null");
        RewriteRuleSubtreeStream stream_column_constraint_not_for_replication=new RewriteRuleSubtreeStream(adaptor,"rule column_constraint_not_for_replication");
        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:346:18: ( ( CONSTRAINT name= id )? ( column_constraint_pk | column_constraint_null | column_constraint_not_null | column_constraint_not_for_replication | column_constraint_unique | column_constraint_check | column_constraint_default | column_constraint_collate | fk_clause ) -> ^( COLUMN_CONSTRAINT ( column_constraint_pk )? ( column_constraint_null )? ( column_constraint_not_null )? ( column_constraint_not_for_replication )? ( column_constraint_unique )? ( column_constraint_check )? ( column_constraint_default )? ( column_constraint_collate )? ( fk_clause )? ( $name)? ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:346:20: ( CONSTRAINT name= id )? ( column_constraint_pk | column_constraint_null | column_constraint_not_null | column_constraint_not_for_replication | column_constraint_unique | column_constraint_check | column_constraint_default | column_constraint_collate | fk_clause )
            {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:346:20: ( CONSTRAINT name= id )?
            int alt122=2;
            alt122 = dfa122.predict(input);
            switch (alt122) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:346:21: CONSTRAINT name= id
                    {
                    CONSTRAINT322=(Token)match(input,CONSTRAINT,FOLLOW_CONSTRAINT_in_column_constraint2842);  
                    stream_CONSTRAINT.add(CONSTRAINT322);

                    pushFollow(FOLLOW_id_in_column_constraint2846);
                    name=id();

                    state._fsp--;

                    stream_id.add(name.getTree());

                    }
                    break;

            }

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:347:3: ( column_constraint_pk | column_constraint_null | column_constraint_not_null | column_constraint_not_for_replication | column_constraint_unique | column_constraint_check | column_constraint_default | column_constraint_collate | fk_clause )
            int alt123=9;
            alt123 = dfa123.predict(input);
            switch (alt123) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:347:5: column_constraint_pk
                    {
                    pushFollow(FOLLOW_column_constraint_pk_in_column_constraint2854);
                    column_constraint_pk323=column_constraint_pk();

                    state._fsp--;

                    stream_column_constraint_pk.add(column_constraint_pk323.getTree());

                    }
                    break;
                case 2 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:348:5: column_constraint_null
                    {
                    pushFollow(FOLLOW_column_constraint_null_in_column_constraint2860);
                    column_constraint_null324=column_constraint_null();

                    state._fsp--;

                    stream_column_constraint_null.add(column_constraint_null324.getTree());

                    }
                    break;
                case 3 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:349:5: column_constraint_not_null
                    {
                    pushFollow(FOLLOW_column_constraint_not_null_in_column_constraint2866);
                    column_constraint_not_null325=column_constraint_not_null();

                    state._fsp--;

                    stream_column_constraint_not_null.add(column_constraint_not_null325.getTree());

                    }
                    break;
                case 4 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:350:5: column_constraint_not_for_replication
                    {
                    pushFollow(FOLLOW_column_constraint_not_for_replication_in_column_constraint2872);
                    column_constraint_not_for_replication326=column_constraint_not_for_replication();

                    state._fsp--;

                    stream_column_constraint_not_for_replication.add(column_constraint_not_for_replication326.getTree());

                    }
                    break;
                case 5 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:351:5: column_constraint_unique
                    {
                    pushFollow(FOLLOW_column_constraint_unique_in_column_constraint2878);
                    column_constraint_unique327=column_constraint_unique();

                    state._fsp--;

                    stream_column_constraint_unique.add(column_constraint_unique327.getTree());

                    }
                    break;
                case 6 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:352:5: column_constraint_check
                    {
                    pushFollow(FOLLOW_column_constraint_check_in_column_constraint2884);
                    column_constraint_check328=column_constraint_check();

                    state._fsp--;

                    stream_column_constraint_check.add(column_constraint_check328.getTree());

                    }
                    break;
                case 7 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:353:5: column_constraint_default
                    {
                    pushFollow(FOLLOW_column_constraint_default_in_column_constraint2890);
                    column_constraint_default329=column_constraint_default();

                    state._fsp--;

                    stream_column_constraint_default.add(column_constraint_default329.getTree());

                    }
                    break;
                case 8 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:354:5: column_constraint_collate
                    {
                    pushFollow(FOLLOW_column_constraint_collate_in_column_constraint2896);
                    column_constraint_collate330=column_constraint_collate();

                    state._fsp--;

                    stream_column_constraint_collate.add(column_constraint_collate330.getTree());

                    }
                    break;
                case 9 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:355:5: fk_clause
                    {
                    pushFollow(FOLLOW_fk_clause_in_column_constraint2902);
                    fk_clause331=fk_clause();

                    state._fsp--;

                    stream_fk_clause.add(fk_clause331.getTree());

                    }
                    break;

            }



            // AST REWRITE
            // elements: column_constraint_check, column_constraint_not_null, column_constraint_not_for_replication, column_constraint_unique, fk_clause, column_constraint_default, column_constraint_null, column_constraint_collate, column_constraint_pk, name
            // token labels: 
            // rule labels: retval, name
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_name=new RewriteRuleSubtreeStream(adaptor,"rule name",name!=null?name.tree:null);

            root_0 = (Object)adaptor.nil();
            // 356:1: -> ^( COLUMN_CONSTRAINT ( column_constraint_pk )? ( column_constraint_null )? ( column_constraint_not_null )? ( column_constraint_not_for_replication )? ( column_constraint_unique )? ( column_constraint_check )? ( column_constraint_default )? ( column_constraint_collate )? ( fk_clause )? ( $name)? )
            {
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:356:4: ^( COLUMN_CONSTRAINT ( column_constraint_pk )? ( column_constraint_null )? ( column_constraint_not_null )? ( column_constraint_not_for_replication )? ( column_constraint_unique )? ( column_constraint_check )? ( column_constraint_default )? ( column_constraint_collate )? ( fk_clause )? ( $name)? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(COLUMN_CONSTRAINT, "COLUMN_CONSTRAINT"), root_1);

                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:357:3: ( column_constraint_pk )?
                if ( stream_column_constraint_pk.hasNext() ) {
                    adaptor.addChild(root_1, stream_column_constraint_pk.nextTree());

                }
                stream_column_constraint_pk.reset();
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:358:3: ( column_constraint_null )?
                if ( stream_column_constraint_null.hasNext() ) {
                    adaptor.addChild(root_1, stream_column_constraint_null.nextTree());

                }
                stream_column_constraint_null.reset();
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:359:3: ( column_constraint_not_null )?
                if ( stream_column_constraint_not_null.hasNext() ) {
                    adaptor.addChild(root_1, stream_column_constraint_not_null.nextTree());

                }
                stream_column_constraint_not_null.reset();
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:360:3: ( column_constraint_not_for_replication )?
                if ( stream_column_constraint_not_for_replication.hasNext() ) {
                    adaptor.addChild(root_1, stream_column_constraint_not_for_replication.nextTree());

                }
                stream_column_constraint_not_for_replication.reset();
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:361:3: ( column_constraint_unique )?
                if ( stream_column_constraint_unique.hasNext() ) {
                    adaptor.addChild(root_1, stream_column_constraint_unique.nextTree());

                }
                stream_column_constraint_unique.reset();
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:362:3: ( column_constraint_check )?
                if ( stream_column_constraint_check.hasNext() ) {
                    adaptor.addChild(root_1, stream_column_constraint_check.nextTree());

                }
                stream_column_constraint_check.reset();
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:363:3: ( column_constraint_default )?
                if ( stream_column_constraint_default.hasNext() ) {
                    adaptor.addChild(root_1, stream_column_constraint_default.nextTree());

                }
                stream_column_constraint_default.reset();
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:364:3: ( column_constraint_collate )?
                if ( stream_column_constraint_collate.hasNext() ) {
                    adaptor.addChild(root_1, stream_column_constraint_collate.nextTree());

                }
                stream_column_constraint_collate.reset();
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:365:3: ( fk_clause )?
                if ( stream_fk_clause.hasNext() ) {
                    adaptor.addChild(root_1, stream_fk_clause.nextTree());

                }
                stream_fk_clause.reset();
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:366:3: ( $name)?
                if ( stream_name.hasNext() ) {
                    adaptor.addChild(root_1, stream_name.nextTree());

                }
                stream_name.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "column_constraint"

    public static class column_constraint_pk_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "column_constraint_pk"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:368:1: column_constraint_pk : PRIMARY KEY ( ASC | DESC )? ( table_conflict_clause )? ( AUTOINCREMENT )? ;
    public final SqlParser.column_constraint_pk_return column_constraint_pk() throws RecognitionException {
        SqlParser.column_constraint_pk_return retval = new SqlParser.column_constraint_pk_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token PRIMARY332=null;
        Token KEY333=null;
        Token set334=null;
        Token AUTOINCREMENT336=null;
        SqlParser.table_conflict_clause_return table_conflict_clause335 = null;


        Object PRIMARY332_tree=null;
        Object KEY333_tree=null;
        Object set334_tree=null;
        Object AUTOINCREMENT336_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:368:21: ( PRIMARY KEY ( ASC | DESC )? ( table_conflict_clause )? ( AUTOINCREMENT )? )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:368:23: PRIMARY KEY ( ASC | DESC )? ( table_conflict_clause )? ( AUTOINCREMENT )?
            {
            root_0 = (Object)adaptor.nil();

            PRIMARY332=(Token)match(input,PRIMARY,FOLLOW_PRIMARY_in_column_constraint_pk2967); 
            PRIMARY332_tree = (Object)adaptor.create(PRIMARY332);
            root_0 = (Object)adaptor.becomeRoot(PRIMARY332_tree, root_0);

            KEY333=(Token)match(input,KEY,FOLLOW_KEY_in_column_constraint_pk2970); 
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:368:37: ( ASC | DESC )?
            int alt124=2;
            alt124 = dfa124.predict(input);
            switch (alt124) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:
                    {
                    set334=(Token)input.LT(1);
                    if ( (input.LA(1)>=ASC && input.LA(1)<=DESC) ) {
                        input.consume();
                        adaptor.addChild(root_0, (Object)adaptor.create(set334));
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }


                    }
                    break;

            }

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:368:51: ( table_conflict_clause )?
            int alt125=2;
            alt125 = dfa125.predict(input);
            switch (alt125) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:368:51: table_conflict_clause
                    {
                    pushFollow(FOLLOW_table_conflict_clause_in_column_constraint_pk2982);
                    table_conflict_clause335=table_conflict_clause();

                    state._fsp--;

                    adaptor.addChild(root_0, table_conflict_clause335.getTree());

                    }
                    break;

            }

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:368:74: ( AUTOINCREMENT )?
            int alt126=2;
            alt126 = dfa126.predict(input);
            switch (alt126) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:368:75: AUTOINCREMENT
                    {
                    AUTOINCREMENT336=(Token)match(input,AUTOINCREMENT,FOLLOW_AUTOINCREMENT_in_column_constraint_pk2986); 
                    AUTOINCREMENT336_tree = (Object)adaptor.create(AUTOINCREMENT336);
                    adaptor.addChild(root_0, AUTOINCREMENT336_tree);


                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "column_constraint_pk"

    public static class column_constraint_not_null_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "column_constraint_not_null"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:370:1: column_constraint_not_null : NOT NULL ( table_conflict_clause )? -> ^( NOT_NULL ( table_conflict_clause )? ) ;
    public final SqlParser.column_constraint_not_null_return column_constraint_not_null() throws RecognitionException {
        SqlParser.column_constraint_not_null_return retval = new SqlParser.column_constraint_not_null_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token NOT337=null;
        Token NULL338=null;
        SqlParser.table_conflict_clause_return table_conflict_clause339 = null;


        Object NOT337_tree=null;
        Object NULL338_tree=null;
        RewriteRuleTokenStream stream_NOT=new RewriteRuleTokenStream(adaptor,"token NOT");
        RewriteRuleTokenStream stream_NULL=new RewriteRuleTokenStream(adaptor,"token NULL");
        RewriteRuleSubtreeStream stream_table_conflict_clause=new RewriteRuleSubtreeStream(adaptor,"rule table_conflict_clause");
        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:370:27: ( NOT NULL ( table_conflict_clause )? -> ^( NOT_NULL ( table_conflict_clause )? ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:370:29: NOT NULL ( table_conflict_clause )?
            {
            NOT337=(Token)match(input,NOT,FOLLOW_NOT_in_column_constraint_not_null2995);  
            stream_NOT.add(NOT337);

            NULL338=(Token)match(input,NULL,FOLLOW_NULL_in_column_constraint_not_null2997);  
            stream_NULL.add(NULL338);

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:370:38: ( table_conflict_clause )?
            int alt127=2;
            alt127 = dfa127.predict(input);
            switch (alt127) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:370:38: table_conflict_clause
                    {
                    pushFollow(FOLLOW_table_conflict_clause_in_column_constraint_not_null2999);
                    table_conflict_clause339=table_conflict_clause();

                    state._fsp--;

                    stream_table_conflict_clause.add(table_conflict_clause339.getTree());

                    }
                    break;

            }



            // AST REWRITE
            // elements: table_conflict_clause
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 370:61: -> ^( NOT_NULL ( table_conflict_clause )? )
            {
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:370:64: ^( NOT_NULL ( table_conflict_clause )? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(NOT_NULL, "NOT_NULL"), root_1);

                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:370:75: ( table_conflict_clause )?
                if ( stream_table_conflict_clause.hasNext() ) {
                    adaptor.addChild(root_1, stream_table_conflict_clause.nextTree());

                }
                stream_table_conflict_clause.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "column_constraint_not_null"

    public static class column_constraint_not_for_replication_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "column_constraint_not_for_replication"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:372:1: column_constraint_not_for_replication : NOT FOR REPLICATION ( table_conflict_clause )? ;
    public final SqlParser.column_constraint_not_for_replication_return column_constraint_not_for_replication() throws RecognitionException {
        SqlParser.column_constraint_not_for_replication_return retval = new SqlParser.column_constraint_not_for_replication_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token NOT340=null;
        Token FOR341=null;
        Token REPLICATION342=null;
        SqlParser.table_conflict_clause_return table_conflict_clause343 = null;


        Object NOT340_tree=null;
        Object FOR341_tree=null;
        Object REPLICATION342_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:372:38: ( NOT FOR REPLICATION ( table_conflict_clause )? )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:372:40: NOT FOR REPLICATION ( table_conflict_clause )?
            {
            root_0 = (Object)adaptor.nil();

            NOT340=(Token)match(input,NOT,FOLLOW_NOT_in_column_constraint_not_for_replication3016); 
            NOT340_tree = (Object)adaptor.create(NOT340);
            adaptor.addChild(root_0, NOT340_tree);

            FOR341=(Token)match(input,FOR,FOLLOW_FOR_in_column_constraint_not_for_replication3018); 
            FOR341_tree = (Object)adaptor.create(FOR341);
            adaptor.addChild(root_0, FOR341_tree);

            REPLICATION342=(Token)match(input,REPLICATION,FOLLOW_REPLICATION_in_column_constraint_not_for_replication3020); 
            REPLICATION342_tree = (Object)adaptor.create(REPLICATION342);
            adaptor.addChild(root_0, REPLICATION342_tree);

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:372:60: ( table_conflict_clause )?
            int alt128=2;
            alt128 = dfa128.predict(input);
            switch (alt128) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:372:60: table_conflict_clause
                    {
                    pushFollow(FOLLOW_table_conflict_clause_in_column_constraint_not_for_replication3022);
                    table_conflict_clause343=table_conflict_clause();

                    state._fsp--;

                    adaptor.addChild(root_0, table_conflict_clause343.getTree());

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "column_constraint_not_for_replication"

    public static class column_constraint_null_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "column_constraint_null"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:374:1: column_constraint_null : NULL ( table_conflict_clause )? ;
    public final SqlParser.column_constraint_null_return column_constraint_null() throws RecognitionException {
        SqlParser.column_constraint_null_return retval = new SqlParser.column_constraint_null_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token NULL344=null;
        SqlParser.table_conflict_clause_return table_conflict_clause345 = null;


        Object NULL344_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:374:23: ( NULL ( table_conflict_clause )? )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:374:25: NULL ( table_conflict_clause )?
            {
            root_0 = (Object)adaptor.nil();

            NULL344=(Token)match(input,NULL,FOLLOW_NULL_in_column_constraint_null3030); 
            NULL344_tree = (Object)adaptor.create(NULL344);
            adaptor.addChild(root_0, NULL344_tree);

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:374:30: ( table_conflict_clause )?
            int alt129=2;
            alt129 = dfa129.predict(input);
            switch (alt129) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:374:30: table_conflict_clause
                    {
                    pushFollow(FOLLOW_table_conflict_clause_in_column_constraint_null3032);
                    table_conflict_clause345=table_conflict_clause();

                    state._fsp--;

                    adaptor.addChild(root_0, table_conflict_clause345.getTree());

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "column_constraint_null"

    public static class column_constraint_unique_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "column_constraint_unique"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:376:1: column_constraint_unique : UNIQUE ( table_conflict_clause )? ;
    public final SqlParser.column_constraint_unique_return column_constraint_unique() throws RecognitionException {
        SqlParser.column_constraint_unique_return retval = new SqlParser.column_constraint_unique_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token UNIQUE346=null;
        SqlParser.table_conflict_clause_return table_conflict_clause347 = null;


        Object UNIQUE346_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:376:25: ( UNIQUE ( table_conflict_clause )? )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:376:27: UNIQUE ( table_conflict_clause )?
            {
            root_0 = (Object)adaptor.nil();

            UNIQUE346=(Token)match(input,UNIQUE,FOLLOW_UNIQUE_in_column_constraint_unique3040); 
            UNIQUE346_tree = (Object)adaptor.create(UNIQUE346);
            root_0 = (Object)adaptor.becomeRoot(UNIQUE346_tree, root_0);

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:376:35: ( table_conflict_clause )?
            int alt130=2;
            alt130 = dfa130.predict(input);
            switch (alt130) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:376:35: table_conflict_clause
                    {
                    pushFollow(FOLLOW_table_conflict_clause_in_column_constraint_unique3043);
                    table_conflict_clause347=table_conflict_clause();

                    state._fsp--;

                    adaptor.addChild(root_0, table_conflict_clause347.getTree());

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "column_constraint_unique"

    public static class column_constraint_check_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "column_constraint_check"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:378:1: column_constraint_check : CHECK LPAREN expr RPAREN ;
    public final SqlParser.column_constraint_check_return column_constraint_check() throws RecognitionException {
        SqlParser.column_constraint_check_return retval = new SqlParser.column_constraint_check_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token CHECK348=null;
        Token LPAREN349=null;
        Token RPAREN351=null;
        SqlParser.expr_return expr350 = null;


        Object CHECK348_tree=null;
        Object LPAREN349_tree=null;
        Object RPAREN351_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:378:24: ( CHECK LPAREN expr RPAREN )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:378:26: CHECK LPAREN expr RPAREN
            {
            root_0 = (Object)adaptor.nil();

            CHECK348=(Token)match(input,CHECK,FOLLOW_CHECK_in_column_constraint_check3051); 
            CHECK348_tree = (Object)adaptor.create(CHECK348);
            root_0 = (Object)adaptor.becomeRoot(CHECK348_tree, root_0);

            LPAREN349=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_column_constraint_check3054); 
            pushFollow(FOLLOW_expr_in_column_constraint_check3057);
            expr350=expr();

            state._fsp--;

            adaptor.addChild(root_0, expr350.getTree());
            RPAREN351=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_column_constraint_check3059); 

            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "column_constraint_check"

    public static class numeric_literal_value_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "numeric_literal_value"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:380:1: numeric_literal_value : ( INTEGER -> ^( INTEGER_LITERAL INTEGER ) | FLOAT -> ^( FLOAT_LITERAL FLOAT ) );
    public final SqlParser.numeric_literal_value_return numeric_literal_value() throws RecognitionException {
        SqlParser.numeric_literal_value_return retval = new SqlParser.numeric_literal_value_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token INTEGER352=null;
        Token FLOAT353=null;

        Object INTEGER352_tree=null;
        Object FLOAT353_tree=null;
        RewriteRuleTokenStream stream_INTEGER=new RewriteRuleTokenStream(adaptor,"token INTEGER");
        RewriteRuleTokenStream stream_FLOAT=new RewriteRuleTokenStream(adaptor,"token FLOAT");

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:381:3: ( INTEGER -> ^( INTEGER_LITERAL INTEGER ) | FLOAT -> ^( FLOAT_LITERAL FLOAT ) )
            int alt131=2;
            int LA131_0 = input.LA(1);

            if ( (LA131_0==INTEGER) ) {
                alt131=1;
            }
            else if ( (LA131_0==FLOAT) ) {
                alt131=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 131, 0, input);

                throw nvae;
            }
            switch (alt131) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:381:5: INTEGER
                    {
                    INTEGER352=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_numeric_literal_value3070);  
                    stream_INTEGER.add(INTEGER352);



                    // AST REWRITE
                    // elements: INTEGER
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 381:13: -> ^( INTEGER_LITERAL INTEGER )
                    {
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:381:16: ^( INTEGER_LITERAL INTEGER )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(INTEGER_LITERAL, "INTEGER_LITERAL"), root_1);

                        adaptor.addChild(root_1, stream_INTEGER.nextNode());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:382:5: FLOAT
                    {
                    FLOAT353=(Token)match(input,FLOAT,FOLLOW_FLOAT_in_numeric_literal_value3084);  
                    stream_FLOAT.add(FLOAT353);



                    // AST REWRITE
                    // elements: FLOAT
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 382:11: -> ^( FLOAT_LITERAL FLOAT )
                    {
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:382:14: ^( FLOAT_LITERAL FLOAT )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(FLOAT_LITERAL, "FLOAT_LITERAL"), root_1);

                        adaptor.addChild(root_1, stream_FLOAT.nextNode());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "numeric_literal_value"

    public static class signed_default_number_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "signed_default_number"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:385:1: signed_default_number : ( PLUS | MINUS ) numeric_literal_value ;
    public final SqlParser.signed_default_number_return signed_default_number() throws RecognitionException {
        SqlParser.signed_default_number_return retval = new SqlParser.signed_default_number_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set354=null;
        SqlParser.numeric_literal_value_return numeric_literal_value355 = null;


        Object set354_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:385:22: ( ( PLUS | MINUS ) numeric_literal_value )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:385:24: ( PLUS | MINUS ) numeric_literal_value
            {
            root_0 = (Object)adaptor.nil();

            set354=(Token)input.LT(1);
            set354=(Token)input.LT(1);
            if ( (input.LA(1)>=PLUS && input.LA(1)<=MINUS) ) {
                input.consume();
                root_0 = (Object)adaptor.becomeRoot((Object)adaptor.create(set354), root_0);
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            pushFollow(FOLLOW_numeric_literal_value_in_signed_default_number3111);
            numeric_literal_value355=numeric_literal_value();

            state._fsp--;

            adaptor.addChild(root_0, numeric_literal_value355.getTree());

            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "signed_default_number"

    public static class column_constraint_default_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "column_constraint_default"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:388:1: column_constraint_default : DEFAULT ( signed_default_number | literal_value | LPAREN expr RPAREN ) ;
    public final SqlParser.column_constraint_default_return column_constraint_default() throws RecognitionException {
        SqlParser.column_constraint_default_return retval = new SqlParser.column_constraint_default_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token DEFAULT356=null;
        Token LPAREN359=null;
        Token RPAREN361=null;
        SqlParser.signed_default_number_return signed_default_number357 = null;

        SqlParser.literal_value_return literal_value358 = null;

        SqlParser.expr_return expr360 = null;


        Object DEFAULT356_tree=null;
        Object LPAREN359_tree=null;
        Object RPAREN361_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:388:26: ( DEFAULT ( signed_default_number | literal_value | LPAREN expr RPAREN ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:388:28: DEFAULT ( signed_default_number | literal_value | LPAREN expr RPAREN )
            {
            root_0 = (Object)adaptor.nil();

            DEFAULT356=(Token)match(input,DEFAULT,FOLLOW_DEFAULT_in_column_constraint_default3119); 
            DEFAULT356_tree = (Object)adaptor.create(DEFAULT356);
            root_0 = (Object)adaptor.becomeRoot(DEFAULT356_tree, root_0);

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:388:37: ( signed_default_number | literal_value | LPAREN expr RPAREN )
            int alt132=3;
            alt132 = dfa132.predict(input);
            switch (alt132) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:388:38: signed_default_number
                    {
                    pushFollow(FOLLOW_signed_default_number_in_column_constraint_default3123);
                    signed_default_number357=signed_default_number();

                    state._fsp--;

                    adaptor.addChild(root_0, signed_default_number357.getTree());

                    }
                    break;
                case 2 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:388:62: literal_value
                    {
                    pushFollow(FOLLOW_literal_value_in_column_constraint_default3127);
                    literal_value358=literal_value();

                    state._fsp--;

                    adaptor.addChild(root_0, literal_value358.getTree());

                    }
                    break;
                case 3 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:388:78: LPAREN expr RPAREN
                    {
                    LPAREN359=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_column_constraint_default3131); 
                    pushFollow(FOLLOW_expr_in_column_constraint_default3134);
                    expr360=expr();

                    state._fsp--;

                    adaptor.addChild(root_0, expr360.getTree());
                    RPAREN361=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_column_constraint_default3136); 

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "column_constraint_default"

    public static class column_constraint_collate_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "column_constraint_collate"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:390:1: column_constraint_collate : COLLATE collation_name= id ;
    public final SqlParser.column_constraint_collate_return column_constraint_collate() throws RecognitionException {
        SqlParser.column_constraint_collate_return retval = new SqlParser.column_constraint_collate_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token COLLATE362=null;
        SqlParser.id_return collation_name = null;


        Object COLLATE362_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:390:26: ( COLLATE collation_name= id )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:390:28: COLLATE collation_name= id
            {
            root_0 = (Object)adaptor.nil();

            COLLATE362=(Token)match(input,COLLATE,FOLLOW_COLLATE_in_column_constraint_collate3145); 
            COLLATE362_tree = (Object)adaptor.create(COLLATE362);
            root_0 = (Object)adaptor.becomeRoot(COLLATE362_tree, root_0);

            pushFollow(FOLLOW_id_in_column_constraint_collate3150);
            collation_name=id();

            state._fsp--;

            adaptor.addChild(root_0, collation_name.getTree());

            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "column_constraint_collate"

    public static class table_constraint_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "table_constraint"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:392:1: table_constraint : ( CONSTRAINT name= id )? ( table_constraint_pk | table_constraint_unique | table_constraint_check | table_constraint_fk ) -> ^( TABLE_CONSTRAINT ( table_constraint_pk )? ( table_constraint_unique )? ( table_constraint_check )? ( table_constraint_fk )? ( $name)? ) ;
    public final SqlParser.table_constraint_return table_constraint() throws RecognitionException {
        SqlParser.table_constraint_return retval = new SqlParser.table_constraint_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token CONSTRAINT363=null;
        SqlParser.id_return name = null;

        SqlParser.table_constraint_pk_return table_constraint_pk364 = null;

        SqlParser.table_constraint_unique_return table_constraint_unique365 = null;

        SqlParser.table_constraint_check_return table_constraint_check366 = null;

        SqlParser.table_constraint_fk_return table_constraint_fk367 = null;


        Object CONSTRAINT363_tree=null;
        RewriteRuleTokenStream stream_CONSTRAINT=new RewriteRuleTokenStream(adaptor,"token CONSTRAINT");
        RewriteRuleSubtreeStream stream_id=new RewriteRuleSubtreeStream(adaptor,"rule id");
        RewriteRuleSubtreeStream stream_table_constraint_pk=new RewriteRuleSubtreeStream(adaptor,"rule table_constraint_pk");
        RewriteRuleSubtreeStream stream_table_constraint_fk=new RewriteRuleSubtreeStream(adaptor,"rule table_constraint_fk");
        RewriteRuleSubtreeStream stream_table_constraint_unique=new RewriteRuleSubtreeStream(adaptor,"rule table_constraint_unique");
        RewriteRuleSubtreeStream stream_table_constraint_check=new RewriteRuleSubtreeStream(adaptor,"rule table_constraint_check");
        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:392:17: ( ( CONSTRAINT name= id )? ( table_constraint_pk | table_constraint_unique | table_constraint_check | table_constraint_fk ) -> ^( TABLE_CONSTRAINT ( table_constraint_pk )? ( table_constraint_unique )? ( table_constraint_check )? ( table_constraint_fk )? ( $name)? ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:392:19: ( CONSTRAINT name= id )? ( table_constraint_pk | table_constraint_unique | table_constraint_check | table_constraint_fk )
            {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:392:19: ( CONSTRAINT name= id )?
            int alt133=2;
            int LA133_0 = input.LA(1);

            if ( (LA133_0==CONSTRAINT) ) {
                alt133=1;
            }
            switch (alt133) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:392:20: CONSTRAINT name= id
                    {
                    CONSTRAINT363=(Token)match(input,CONSTRAINT,FOLLOW_CONSTRAINT_in_table_constraint3159);  
                    stream_CONSTRAINT.add(CONSTRAINT363);

                    pushFollow(FOLLOW_id_in_table_constraint3163);
                    name=id();

                    state._fsp--;

                    stream_id.add(name.getTree());

                    }
                    break;

            }

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:393:3: ( table_constraint_pk | table_constraint_unique | table_constraint_check | table_constraint_fk )
            int alt134=4;
            switch ( input.LA(1) ) {
            case PRIMARY:
                {
                alt134=1;
                }
                break;
            case UNIQUE:
                {
                alt134=2;
                }
                break;
            case CHECK:
                {
                alt134=3;
                }
                break;
            case FOREIGN:
                {
                alt134=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 134, 0, input);

                throw nvae;
            }

            switch (alt134) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:393:5: table_constraint_pk
                    {
                    pushFollow(FOLLOW_table_constraint_pk_in_table_constraint3171);
                    table_constraint_pk364=table_constraint_pk();

                    state._fsp--;

                    stream_table_constraint_pk.add(table_constraint_pk364.getTree());

                    }
                    break;
                case 2 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:394:5: table_constraint_unique
                    {
                    pushFollow(FOLLOW_table_constraint_unique_in_table_constraint3177);
                    table_constraint_unique365=table_constraint_unique();

                    state._fsp--;

                    stream_table_constraint_unique.add(table_constraint_unique365.getTree());

                    }
                    break;
                case 3 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:395:5: table_constraint_check
                    {
                    pushFollow(FOLLOW_table_constraint_check_in_table_constraint3183);
                    table_constraint_check366=table_constraint_check();

                    state._fsp--;

                    stream_table_constraint_check.add(table_constraint_check366.getTree());

                    }
                    break;
                case 4 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:396:5: table_constraint_fk
                    {
                    pushFollow(FOLLOW_table_constraint_fk_in_table_constraint3189);
                    table_constraint_fk367=table_constraint_fk();

                    state._fsp--;

                    stream_table_constraint_fk.add(table_constraint_fk367.getTree());

                    }
                    break;

            }



            // AST REWRITE
            // elements: table_constraint_check, table_constraint_pk, name, table_constraint_fk, table_constraint_unique
            // token labels: 
            // rule labels: retval, name
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_name=new RewriteRuleSubtreeStream(adaptor,"rule name",name!=null?name.tree:null);

            root_0 = (Object)adaptor.nil();
            // 397:1: -> ^( TABLE_CONSTRAINT ( table_constraint_pk )? ( table_constraint_unique )? ( table_constraint_check )? ( table_constraint_fk )? ( $name)? )
            {
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:397:4: ^( TABLE_CONSTRAINT ( table_constraint_pk )? ( table_constraint_unique )? ( table_constraint_check )? ( table_constraint_fk )? ( $name)? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(TABLE_CONSTRAINT, "TABLE_CONSTRAINT"), root_1);

                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:398:3: ( table_constraint_pk )?
                if ( stream_table_constraint_pk.hasNext() ) {
                    adaptor.addChild(root_1, stream_table_constraint_pk.nextTree());

                }
                stream_table_constraint_pk.reset();
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:399:3: ( table_constraint_unique )?
                if ( stream_table_constraint_unique.hasNext() ) {
                    adaptor.addChild(root_1, stream_table_constraint_unique.nextTree());

                }
                stream_table_constraint_unique.reset();
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:400:3: ( table_constraint_check )?
                if ( stream_table_constraint_check.hasNext() ) {
                    adaptor.addChild(root_1, stream_table_constraint_check.nextTree());

                }
                stream_table_constraint_check.reset();
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:401:3: ( table_constraint_fk )?
                if ( stream_table_constraint_fk.hasNext() ) {
                    adaptor.addChild(root_1, stream_table_constraint_fk.nextTree());

                }
                stream_table_constraint_fk.reset();
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:402:3: ( $name)?
                if ( stream_name.hasNext() ) {
                    adaptor.addChild(root_1, stream_name.nextTree());

                }
                stream_name.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "table_constraint"

    public static class table_constraint_pk_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "table_constraint_pk"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:404:1: table_constraint_pk : PRIMARY KEY LPAREN indexed_columns+= id ( COMMA indexed_columns+= id )* RPAREN ( table_conflict_clause )? -> ^( PRIMARY ^( COLUMNS ( $indexed_columns)+ ) ( table_conflict_clause )? ) ;
    public final SqlParser.table_constraint_pk_return table_constraint_pk() throws RecognitionException {
        SqlParser.table_constraint_pk_return retval = new SqlParser.table_constraint_pk_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token PRIMARY368=null;
        Token KEY369=null;
        Token LPAREN370=null;
        Token COMMA371=null;
        Token RPAREN372=null;
        List list_indexed_columns=null;
        SqlParser.table_conflict_clause_return table_conflict_clause373 = null;

        SqlParser.id_return indexed_columns = null;
         indexed_columns = null;
        Object PRIMARY368_tree=null;
        Object KEY369_tree=null;
        Object LPAREN370_tree=null;
        Object COMMA371_tree=null;
        Object RPAREN372_tree=null;
        RewriteRuleTokenStream stream_RPAREN=new RewriteRuleTokenStream(adaptor,"token RPAREN");
        RewriteRuleTokenStream stream_PRIMARY=new RewriteRuleTokenStream(adaptor,"token PRIMARY");
        RewriteRuleTokenStream stream_COMMA=new RewriteRuleTokenStream(adaptor,"token COMMA");
        RewriteRuleTokenStream stream_KEY=new RewriteRuleTokenStream(adaptor,"token KEY");
        RewriteRuleTokenStream stream_LPAREN=new RewriteRuleTokenStream(adaptor,"token LPAREN");
        RewriteRuleSubtreeStream stream_id=new RewriteRuleSubtreeStream(adaptor,"rule id");
        RewriteRuleSubtreeStream stream_table_conflict_clause=new RewriteRuleSubtreeStream(adaptor,"rule table_conflict_clause");
        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:404:20: ( PRIMARY KEY LPAREN indexed_columns+= id ( COMMA indexed_columns+= id )* RPAREN ( table_conflict_clause )? -> ^( PRIMARY ^( COLUMNS ( $indexed_columns)+ ) ( table_conflict_clause )? ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:404:22: PRIMARY KEY LPAREN indexed_columns+= id ( COMMA indexed_columns+= id )* RPAREN ( table_conflict_clause )?
            {
            PRIMARY368=(Token)match(input,PRIMARY,FOLLOW_PRIMARY_in_table_constraint_pk3229);  
            stream_PRIMARY.add(PRIMARY368);

            KEY369=(Token)match(input,KEY,FOLLOW_KEY_in_table_constraint_pk3231);  
            stream_KEY.add(KEY369);

            LPAREN370=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_table_constraint_pk3235);  
            stream_LPAREN.add(LPAREN370);

            pushFollow(FOLLOW_id_in_table_constraint_pk3239);
            indexed_columns=id();

            state._fsp--;

            stream_id.add(indexed_columns.getTree());
            if (list_indexed_columns==null) list_indexed_columns=new ArrayList();
            list_indexed_columns.add(indexed_columns.getTree());

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:405:30: ( COMMA indexed_columns+= id )*
            loop135:
            do {
                int alt135=2;
                int LA135_0 = input.LA(1);

                if ( (LA135_0==COMMA) ) {
                    alt135=1;
                }


                switch (alt135) {
            	case 1 :
            	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:405:31: COMMA indexed_columns+= id
            	    {
            	    COMMA371=(Token)match(input,COMMA,FOLLOW_COMMA_in_table_constraint_pk3242);  
            	    stream_COMMA.add(COMMA371);

            	    pushFollow(FOLLOW_id_in_table_constraint_pk3246);
            	    indexed_columns=id();

            	    state._fsp--;

            	    stream_id.add(indexed_columns.getTree());
            	    if (list_indexed_columns==null) list_indexed_columns=new ArrayList();
            	    list_indexed_columns.add(indexed_columns.getTree());


            	    }
            	    break;

            	default :
            	    break loop135;
                }
            } while (true);

            RPAREN372=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_table_constraint_pk3250);  
            stream_RPAREN.add(RPAREN372);

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:405:66: ( table_conflict_clause )?
            int alt136=2;
            int LA136_0 = input.LA(1);

            if ( (LA136_0==ON) ) {
                alt136=1;
            }
            switch (alt136) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:405:66: table_conflict_clause
                    {
                    pushFollow(FOLLOW_table_conflict_clause_in_table_constraint_pk3252);
                    table_conflict_clause373=table_conflict_clause();

                    state._fsp--;

                    stream_table_conflict_clause.add(table_conflict_clause373.getTree());

                    }
                    break;

            }



            // AST REWRITE
            // elements: table_conflict_clause, indexed_columns, PRIMARY
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: indexed_columns
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_indexed_columns=new RewriteRuleSubtreeStream(adaptor,"token indexed_columns",list_indexed_columns);
            root_0 = (Object)adaptor.nil();
            // 406:1: -> ^( PRIMARY ^( COLUMNS ( $indexed_columns)+ ) ( table_conflict_clause )? )
            {
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:406:4: ^( PRIMARY ^( COLUMNS ( $indexed_columns)+ ) ( table_conflict_clause )? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(stream_PRIMARY.nextNode(), root_1);

                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:406:14: ^( COLUMNS ( $indexed_columns)+ )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(COLUMNS, "COLUMNS"), root_2);

                if ( !(stream_indexed_columns.hasNext()) ) {
                    throw new RewriteEarlyExitException();
                }
                while ( stream_indexed_columns.hasNext() ) {
                    adaptor.addChild(root_2, stream_indexed_columns.nextTree());

                }
                stream_indexed_columns.reset();

                adaptor.addChild(root_1, root_2);
                }
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:406:43: ( table_conflict_clause )?
                if ( stream_table_conflict_clause.hasNext() ) {
                    adaptor.addChild(root_1, stream_table_conflict_clause.nextTree());

                }
                stream_table_conflict_clause.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "table_constraint_pk"

    public static class table_constraint_unique_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "table_constraint_unique"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:408:1: table_constraint_unique : UNIQUE LPAREN indexed_columns+= id ( COMMA indexed_columns+= id )* RPAREN ( table_conflict_clause )? -> ^( UNIQUE ^( COLUMNS ( $indexed_columns)+ ) ( table_conflict_clause )? ) ;
    public final SqlParser.table_constraint_unique_return table_constraint_unique() throws RecognitionException {
        SqlParser.table_constraint_unique_return retval = new SqlParser.table_constraint_unique_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token UNIQUE374=null;
        Token LPAREN375=null;
        Token COMMA376=null;
        Token RPAREN377=null;
        List list_indexed_columns=null;
        SqlParser.table_conflict_clause_return table_conflict_clause378 = null;

        SqlParser.id_return indexed_columns = null;
         indexed_columns = null;
        Object UNIQUE374_tree=null;
        Object LPAREN375_tree=null;
        Object COMMA376_tree=null;
        Object RPAREN377_tree=null;
        RewriteRuleTokenStream stream_UNIQUE=new RewriteRuleTokenStream(adaptor,"token UNIQUE");
        RewriteRuleTokenStream stream_RPAREN=new RewriteRuleTokenStream(adaptor,"token RPAREN");
        RewriteRuleTokenStream stream_COMMA=new RewriteRuleTokenStream(adaptor,"token COMMA");
        RewriteRuleTokenStream stream_LPAREN=new RewriteRuleTokenStream(adaptor,"token LPAREN");
        RewriteRuleSubtreeStream stream_id=new RewriteRuleSubtreeStream(adaptor,"rule id");
        RewriteRuleSubtreeStream stream_table_conflict_clause=new RewriteRuleSubtreeStream(adaptor,"rule table_conflict_clause");
        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:408:24: ( UNIQUE LPAREN indexed_columns+= id ( COMMA indexed_columns+= id )* RPAREN ( table_conflict_clause )? -> ^( UNIQUE ^( COLUMNS ( $indexed_columns)+ ) ( table_conflict_clause )? ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:408:26: UNIQUE LPAREN indexed_columns+= id ( COMMA indexed_columns+= id )* RPAREN ( table_conflict_clause )?
            {
            UNIQUE374=(Token)match(input,UNIQUE,FOLLOW_UNIQUE_in_table_constraint_unique3277);  
            stream_UNIQUE.add(UNIQUE374);

            LPAREN375=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_table_constraint_unique3281);  
            stream_LPAREN.add(LPAREN375);

            pushFollow(FOLLOW_id_in_table_constraint_unique3285);
            indexed_columns=id();

            state._fsp--;

            stream_id.add(indexed_columns.getTree());
            if (list_indexed_columns==null) list_indexed_columns=new ArrayList();
            list_indexed_columns.add(indexed_columns.getTree());

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:409:30: ( COMMA indexed_columns+= id )*
            loop137:
            do {
                int alt137=2;
                int LA137_0 = input.LA(1);

                if ( (LA137_0==COMMA) ) {
                    alt137=1;
                }


                switch (alt137) {
            	case 1 :
            	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:409:31: COMMA indexed_columns+= id
            	    {
            	    COMMA376=(Token)match(input,COMMA,FOLLOW_COMMA_in_table_constraint_unique3288);  
            	    stream_COMMA.add(COMMA376);

            	    pushFollow(FOLLOW_id_in_table_constraint_unique3292);
            	    indexed_columns=id();

            	    state._fsp--;

            	    stream_id.add(indexed_columns.getTree());
            	    if (list_indexed_columns==null) list_indexed_columns=new ArrayList();
            	    list_indexed_columns.add(indexed_columns.getTree());


            	    }
            	    break;

            	default :
            	    break loop137;
                }
            } while (true);

            RPAREN377=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_table_constraint_unique3296);  
            stream_RPAREN.add(RPAREN377);

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:409:66: ( table_conflict_clause )?
            int alt138=2;
            int LA138_0 = input.LA(1);

            if ( (LA138_0==ON) ) {
                alt138=1;
            }
            switch (alt138) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:409:66: table_conflict_clause
                    {
                    pushFollow(FOLLOW_table_conflict_clause_in_table_constraint_unique3298);
                    table_conflict_clause378=table_conflict_clause();

                    state._fsp--;

                    stream_table_conflict_clause.add(table_conflict_clause378.getTree());

                    }
                    break;

            }



            // AST REWRITE
            // elements: table_conflict_clause, indexed_columns, UNIQUE
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: indexed_columns
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_indexed_columns=new RewriteRuleSubtreeStream(adaptor,"token indexed_columns",list_indexed_columns);
            root_0 = (Object)adaptor.nil();
            // 410:1: -> ^( UNIQUE ^( COLUMNS ( $indexed_columns)+ ) ( table_conflict_clause )? )
            {
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:410:4: ^( UNIQUE ^( COLUMNS ( $indexed_columns)+ ) ( table_conflict_clause )? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(stream_UNIQUE.nextNode(), root_1);

                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:410:13: ^( COLUMNS ( $indexed_columns)+ )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(COLUMNS, "COLUMNS"), root_2);

                if ( !(stream_indexed_columns.hasNext()) ) {
                    throw new RewriteEarlyExitException();
                }
                while ( stream_indexed_columns.hasNext() ) {
                    adaptor.addChild(root_2, stream_indexed_columns.nextTree());

                }
                stream_indexed_columns.reset();

                adaptor.addChild(root_1, root_2);
                }
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:410:42: ( table_conflict_clause )?
                if ( stream_table_conflict_clause.hasNext() ) {
                    adaptor.addChild(root_1, stream_table_conflict_clause.nextTree());

                }
                stream_table_conflict_clause.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "table_constraint_unique"

    public static class table_constraint_check_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "table_constraint_check"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:412:1: table_constraint_check : CHECK LPAREN expr RPAREN ;
    public final SqlParser.table_constraint_check_return table_constraint_check() throws RecognitionException {
        SqlParser.table_constraint_check_return retval = new SqlParser.table_constraint_check_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token CHECK379=null;
        Token LPAREN380=null;
        Token RPAREN382=null;
        SqlParser.expr_return expr381 = null;


        Object CHECK379_tree=null;
        Object LPAREN380_tree=null;
        Object RPAREN382_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:412:23: ( CHECK LPAREN expr RPAREN )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:412:25: CHECK LPAREN expr RPAREN
            {
            root_0 = (Object)adaptor.nil();

            CHECK379=(Token)match(input,CHECK,FOLLOW_CHECK_in_table_constraint_check3323); 
            CHECK379_tree = (Object)adaptor.create(CHECK379);
            root_0 = (Object)adaptor.becomeRoot(CHECK379_tree, root_0);

            LPAREN380=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_table_constraint_check3326); 
            pushFollow(FOLLOW_expr_in_table_constraint_check3329);
            expr381=expr();

            state._fsp--;

            adaptor.addChild(root_0, expr381.getTree());
            RPAREN382=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_table_constraint_check3331); 

            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "table_constraint_check"

    public static class table_constraint_fk_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "table_constraint_fk"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:414:1: table_constraint_fk : FOREIGN KEY LPAREN column_names+= id ( COMMA column_names+= id )* RPAREN fk_clause -> ^( FOREIGN ^( COLUMNS ( $column_names)+ ) fk_clause ) ;
    public final SqlParser.table_constraint_fk_return table_constraint_fk() throws RecognitionException {
        SqlParser.table_constraint_fk_return retval = new SqlParser.table_constraint_fk_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token FOREIGN383=null;
        Token KEY384=null;
        Token LPAREN385=null;
        Token COMMA386=null;
        Token RPAREN387=null;
        List list_column_names=null;
        SqlParser.fk_clause_return fk_clause388 = null;

        SqlParser.id_return column_names = null;
         column_names = null;
        Object FOREIGN383_tree=null;
        Object KEY384_tree=null;
        Object LPAREN385_tree=null;
        Object COMMA386_tree=null;
        Object RPAREN387_tree=null;
        RewriteRuleTokenStream stream_RPAREN=new RewriteRuleTokenStream(adaptor,"token RPAREN");
        RewriteRuleTokenStream stream_FOREIGN=new RewriteRuleTokenStream(adaptor,"token FOREIGN");
        RewriteRuleTokenStream stream_COMMA=new RewriteRuleTokenStream(adaptor,"token COMMA");
        RewriteRuleTokenStream stream_KEY=new RewriteRuleTokenStream(adaptor,"token KEY");
        RewriteRuleTokenStream stream_LPAREN=new RewriteRuleTokenStream(adaptor,"token LPAREN");
        RewriteRuleSubtreeStream stream_id=new RewriteRuleSubtreeStream(adaptor,"rule id");
        RewriteRuleSubtreeStream stream_fk_clause=new RewriteRuleSubtreeStream(adaptor,"rule fk_clause");
        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:414:20: ( FOREIGN KEY LPAREN column_names+= id ( COMMA column_names+= id )* RPAREN fk_clause -> ^( FOREIGN ^( COLUMNS ( $column_names)+ ) fk_clause ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:414:22: FOREIGN KEY LPAREN column_names+= id ( COMMA column_names+= id )* RPAREN fk_clause
            {
            FOREIGN383=(Token)match(input,FOREIGN,FOLLOW_FOREIGN_in_table_constraint_fk3339);  
            stream_FOREIGN.add(FOREIGN383);

            KEY384=(Token)match(input,KEY,FOLLOW_KEY_in_table_constraint_fk3341);  
            stream_KEY.add(KEY384);

            LPAREN385=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_table_constraint_fk3343);  
            stream_LPAREN.add(LPAREN385);

            pushFollow(FOLLOW_id_in_table_constraint_fk3347);
            column_names=id();

            state._fsp--;

            stream_id.add(column_names.getTree());
            if (list_column_names==null) list_column_names=new ArrayList();
            list_column_names.add(column_names.getTree());

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:414:58: ( COMMA column_names+= id )*
            loop139:
            do {
                int alt139=2;
                int LA139_0 = input.LA(1);

                if ( (LA139_0==COMMA) ) {
                    alt139=1;
                }


                switch (alt139) {
            	case 1 :
            	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:414:59: COMMA column_names+= id
            	    {
            	    COMMA386=(Token)match(input,COMMA,FOLLOW_COMMA_in_table_constraint_fk3350);  
            	    stream_COMMA.add(COMMA386);

            	    pushFollow(FOLLOW_id_in_table_constraint_fk3354);
            	    column_names=id();

            	    state._fsp--;

            	    stream_id.add(column_names.getTree());
            	    if (list_column_names==null) list_column_names=new ArrayList();
            	    list_column_names.add(column_names.getTree());


            	    }
            	    break;

            	default :
            	    break loop139;
                }
            } while (true);

            RPAREN387=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_table_constraint_fk3359);  
            stream_RPAREN.add(RPAREN387);

            pushFollow(FOLLOW_fk_clause_in_table_constraint_fk3361);
            fk_clause388=fk_clause();

            state._fsp--;

            stream_fk_clause.add(fk_clause388.getTree());


            // AST REWRITE
            // elements: fk_clause, column_names, FOREIGN
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: column_names
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_column_names=new RewriteRuleSubtreeStream(adaptor,"token column_names",list_column_names);
            root_0 = (Object)adaptor.nil();
            // 415:1: -> ^( FOREIGN ^( COLUMNS ( $column_names)+ ) fk_clause )
            {
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:415:4: ^( FOREIGN ^( COLUMNS ( $column_names)+ ) fk_clause )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(stream_FOREIGN.nextNode(), root_1);

                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:415:14: ^( COLUMNS ( $column_names)+ )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(COLUMNS, "COLUMNS"), root_2);

                if ( !(stream_column_names.hasNext()) ) {
                    throw new RewriteEarlyExitException();
                }
                while ( stream_column_names.hasNext() ) {
                    adaptor.addChild(root_2, stream_column_names.nextTree());

                }
                stream_column_names.reset();

                adaptor.addChild(root_1, root_2);
                }
                adaptor.addChild(root_1, stream_fk_clause.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "table_constraint_fk"

    public static class fk_clause_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "fk_clause"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:417:1: fk_clause : REFERENCES foreign_table= id ( LPAREN column_names+= id ( COMMA column_names+= id )* RPAREN )? ( fk_clause_action )* ( fk_clause_deferrable )? -> ^( REFERENCES $foreign_table ^( COLUMNS ( $column_names)+ ) ( fk_clause_action )* ( fk_clause_deferrable )? ) ;
    public final SqlParser.fk_clause_return fk_clause() throws RecognitionException {
        SqlParser.fk_clause_return retval = new SqlParser.fk_clause_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token REFERENCES389=null;
        Token LPAREN390=null;
        Token COMMA391=null;
        Token RPAREN392=null;
        List list_column_names=null;
        SqlParser.id_return foreign_table = null;

        SqlParser.fk_clause_action_return fk_clause_action393 = null;

        SqlParser.fk_clause_deferrable_return fk_clause_deferrable394 = null;

        SqlParser.id_return column_names = null;
         column_names = null;
        Object REFERENCES389_tree=null;
        Object LPAREN390_tree=null;
        Object COMMA391_tree=null;
        Object RPAREN392_tree=null;
        RewriteRuleTokenStream stream_RPAREN=new RewriteRuleTokenStream(adaptor,"token RPAREN");
        RewriteRuleTokenStream stream_REFERENCES=new RewriteRuleTokenStream(adaptor,"token REFERENCES");
        RewriteRuleTokenStream stream_COMMA=new RewriteRuleTokenStream(adaptor,"token COMMA");
        RewriteRuleTokenStream stream_LPAREN=new RewriteRuleTokenStream(adaptor,"token LPAREN");
        RewriteRuleSubtreeStream stream_id=new RewriteRuleSubtreeStream(adaptor,"rule id");
        RewriteRuleSubtreeStream stream_fk_clause_action=new RewriteRuleSubtreeStream(adaptor,"rule fk_clause_action");
        RewriteRuleSubtreeStream stream_fk_clause_deferrable=new RewriteRuleSubtreeStream(adaptor,"rule fk_clause_deferrable");
        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:417:10: ( REFERENCES foreign_table= id ( LPAREN column_names+= id ( COMMA column_names+= id )* RPAREN )? ( fk_clause_action )* ( fk_clause_deferrable )? -> ^( REFERENCES $foreign_table ^( COLUMNS ( $column_names)+ ) ( fk_clause_action )* ( fk_clause_deferrable )? ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:417:12: REFERENCES foreign_table= id ( LPAREN column_names+= id ( COMMA column_names+= id )* RPAREN )? ( fk_clause_action )* ( fk_clause_deferrable )?
            {
            REFERENCES389=(Token)match(input,REFERENCES,FOLLOW_REFERENCES_in_fk_clause3384);  
            stream_REFERENCES.add(REFERENCES389);

            pushFollow(FOLLOW_id_in_fk_clause3388);
            foreign_table=id();

            state._fsp--;

            stream_id.add(foreign_table.getTree());
             builder.addReferenceTo( foreign_table.getTree().toString() ); 
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:417:106: ( LPAREN column_names+= id ( COMMA column_names+= id )* RPAREN )?
            int alt141=2;
            alt141 = dfa141.predict(input);
            switch (alt141) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:417:107: LPAREN column_names+= id ( COMMA column_names+= id )* RPAREN
                    {
                    LPAREN390=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_fk_clause3393);  
                    stream_LPAREN.add(LPAREN390);

                    pushFollow(FOLLOW_id_in_fk_clause3397);
                    column_names=id();

                    state._fsp--;

                    stream_id.add(column_names.getTree());
                    if (list_column_names==null) list_column_names=new ArrayList();
                    list_column_names.add(column_names.getTree());

                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:417:131: ( COMMA column_names+= id )*
                    loop140:
                    do {
                        int alt140=2;
                        int LA140_0 = input.LA(1);

                        if ( (LA140_0==COMMA) ) {
                            alt140=1;
                        }


                        switch (alt140) {
                    	case 1 :
                    	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:417:132: COMMA column_names+= id
                    	    {
                    	    COMMA391=(Token)match(input,COMMA,FOLLOW_COMMA_in_fk_clause3400);  
                    	    stream_COMMA.add(COMMA391);

                    	    pushFollow(FOLLOW_id_in_fk_clause3404);
                    	    column_names=id();

                    	    state._fsp--;

                    	    stream_id.add(column_names.getTree());
                    	    if (list_column_names==null) list_column_names=new ArrayList();
                    	    list_column_names.add(column_names.getTree());


                    	    }
                    	    break;

                    	default :
                    	    break loop140;
                        }
                    } while (true);

                    RPAREN392=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_fk_clause3408);  
                    stream_RPAREN.add(RPAREN392);


                    }
                    break;

            }

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:418:3: ( fk_clause_action )*
            loop142:
            do {
                int alt142=2;
                alt142 = dfa142.predict(input);
                switch (alt142) {
            	case 1 :
            	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:418:3: fk_clause_action
            	    {
            	    pushFollow(FOLLOW_fk_clause_action_in_fk_clause3414);
            	    fk_clause_action393=fk_clause_action();

            	    state._fsp--;

            	    stream_fk_clause_action.add(fk_clause_action393.getTree());

            	    }
            	    break;

            	default :
            	    break loop142;
                }
            } while (true);

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:418:21: ( fk_clause_deferrable )?
            int alt143=2;
            alt143 = dfa143.predict(input);
            switch (alt143) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:418:21: fk_clause_deferrable
                    {
                    pushFollow(FOLLOW_fk_clause_deferrable_in_fk_clause3417);
                    fk_clause_deferrable394=fk_clause_deferrable();

                    state._fsp--;

                    stream_fk_clause_deferrable.add(fk_clause_deferrable394.getTree());

                    }
                    break;

            }



            // AST REWRITE
            // elements: column_names, foreign_table, fk_clause_deferrable, REFERENCES, fk_clause_action
            // token labels: 
            // rule labels: retval, foreign_table
            // token list labels: 
            // rule list labels: column_names
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_foreign_table=new RewriteRuleSubtreeStream(adaptor,"rule foreign_table",foreign_table!=null?foreign_table.tree:null);
            RewriteRuleSubtreeStream stream_column_names=new RewriteRuleSubtreeStream(adaptor,"token column_names",list_column_names);
            root_0 = (Object)adaptor.nil();
            // 419:1: -> ^( REFERENCES $foreign_table ^( COLUMNS ( $column_names)+ ) ( fk_clause_action )* ( fk_clause_deferrable )? )
            {
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:419:4: ^( REFERENCES $foreign_table ^( COLUMNS ( $column_names)+ ) ( fk_clause_action )* ( fk_clause_deferrable )? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(stream_REFERENCES.nextNode(), root_1);

                adaptor.addChild(root_1, stream_foreign_table.nextTree());
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:419:32: ^( COLUMNS ( $column_names)+ )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(COLUMNS, "COLUMNS"), root_2);

                if ( !(stream_column_names.hasNext()) ) {
                    throw new RewriteEarlyExitException();
                }
                while ( stream_column_names.hasNext() ) {
                    adaptor.addChild(root_2, stream_column_names.nextTree());

                }
                stream_column_names.reset();

                adaptor.addChild(root_1, root_2);
                }
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:419:58: ( fk_clause_action )*
                while ( stream_fk_clause_action.hasNext() ) {
                    adaptor.addChild(root_1, stream_fk_clause_action.nextTree());

                }
                stream_fk_clause_action.reset();
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:419:76: ( fk_clause_deferrable )?
                if ( stream_fk_clause_deferrable.hasNext() ) {
                    adaptor.addChild(root_1, stream_fk_clause_deferrable.nextTree());

                }
                stream_fk_clause_deferrable.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "fk_clause"

    public static class fk_clause_action_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "fk_clause_action"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:421:1: fk_clause_action : ( ON ( DELETE | UPDATE | INSERT ) ( SET NULL | SET DEFAULT | CASCADE | RESTRICT ) | MATCH id );
    public final SqlParser.fk_clause_action_return fk_clause_action() throws RecognitionException {
        SqlParser.fk_clause_action_return retval = new SqlParser.fk_clause_action_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ON395=null;
        Token set396=null;
        Token SET397=null;
        Token NULL398=null;
        Token SET399=null;
        Token DEFAULT400=null;
        Token CASCADE401=null;
        Token RESTRICT402=null;
        Token MATCH403=null;
        SqlParser.id_return id404 = null;


        Object ON395_tree=null;
        Object set396_tree=null;
        Object SET397_tree=null;
        Object NULL398_tree=null;
        Object SET399_tree=null;
        Object DEFAULT400_tree=null;
        Object CASCADE401_tree=null;
        Object RESTRICT402_tree=null;
        Object MATCH403_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:422:3: ( ON ( DELETE | UPDATE | INSERT ) ( SET NULL | SET DEFAULT | CASCADE | RESTRICT ) | MATCH id )
            int alt145=2;
            int LA145_0 = input.LA(1);

            if ( (LA145_0==ON) ) {
                alt145=1;
            }
            else if ( (LA145_0==MATCH) ) {
                alt145=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 145, 0, input);

                throw nvae;
            }
            switch (alt145) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:422:5: ON ( DELETE | UPDATE | INSERT ) ( SET NULL | SET DEFAULT | CASCADE | RESTRICT )
                    {
                    root_0 = (Object)adaptor.nil();

                    ON395=(Token)match(input,ON,FOLLOW_ON_in_fk_clause_action3451); 
                    ON395_tree = (Object)adaptor.create(ON395);
                    root_0 = (Object)adaptor.becomeRoot(ON395_tree, root_0);

                    set396=(Token)input.LT(1);
                    if ( input.LA(1)==INSERT||input.LA(1)==UPDATE||input.LA(1)==DELETE ) {
                        input.consume();
                        adaptor.addChild(root_0, (Object)adaptor.create(set396));
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:422:36: ( SET NULL | SET DEFAULT | CASCADE | RESTRICT )
                    int alt144=4;
                    switch ( input.LA(1) ) {
                    case SET:
                        {
                        int LA144_1 = input.LA(2);

                        if ( (LA144_1==NULL) ) {
                            alt144=1;
                        }
                        else if ( (LA144_1==DEFAULT) ) {
                            alt144=2;
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 144, 1, input);

                            throw nvae;
                        }
                        }
                        break;
                    case CASCADE:
                        {
                        alt144=3;
                        }
                        break;
                    case RESTRICT:
                        {
                        alt144=4;
                        }
                        break;
                    default:
                        NoViableAltException nvae =
                            new NoViableAltException("", 144, 0, input);

                        throw nvae;
                    }

                    switch (alt144) {
                        case 1 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:422:37: SET NULL
                            {
                            SET397=(Token)match(input,SET,FOLLOW_SET_in_fk_clause_action3467); 
                            NULL398=(Token)match(input,NULL,FOLLOW_NULL_in_fk_clause_action3470); 
                            NULL398_tree = (Object)adaptor.create(NULL398);
                            adaptor.addChild(root_0, NULL398_tree);


                            }
                            break;
                        case 2 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:422:49: SET DEFAULT
                            {
                            SET399=(Token)match(input,SET,FOLLOW_SET_in_fk_clause_action3474); 
                            DEFAULT400=(Token)match(input,DEFAULT,FOLLOW_DEFAULT_in_fk_clause_action3477); 
                            DEFAULT400_tree = (Object)adaptor.create(DEFAULT400);
                            adaptor.addChild(root_0, DEFAULT400_tree);


                            }
                            break;
                        case 3 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:422:64: CASCADE
                            {
                            CASCADE401=(Token)match(input,CASCADE,FOLLOW_CASCADE_in_fk_clause_action3481); 
                            CASCADE401_tree = (Object)adaptor.create(CASCADE401);
                            adaptor.addChild(root_0, CASCADE401_tree);


                            }
                            break;
                        case 4 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:422:74: RESTRICT
                            {
                            RESTRICT402=(Token)match(input,RESTRICT,FOLLOW_RESTRICT_in_fk_clause_action3485); 
                            RESTRICT402_tree = (Object)adaptor.create(RESTRICT402);
                            adaptor.addChild(root_0, RESTRICT402_tree);


                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:423:5: MATCH id
                    {
                    root_0 = (Object)adaptor.nil();

                    MATCH403=(Token)match(input,MATCH,FOLLOW_MATCH_in_fk_clause_action3492); 
                    MATCH403_tree = (Object)adaptor.create(MATCH403);
                    root_0 = (Object)adaptor.becomeRoot(MATCH403_tree, root_0);

                    pushFollow(FOLLOW_id_in_fk_clause_action3495);
                    id404=id();

                    state._fsp--;

                    adaptor.addChild(root_0, id404.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "fk_clause_action"

    public static class fk_clause_deferrable_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "fk_clause_deferrable"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:425:1: fk_clause_deferrable : ( NOT )? DEFERRABLE ( INITIALLY DEFERRED | INITIALLY IMMEDIATE )? ;
    public final SqlParser.fk_clause_deferrable_return fk_clause_deferrable() throws RecognitionException {
        SqlParser.fk_clause_deferrable_return retval = new SqlParser.fk_clause_deferrable_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token NOT405=null;
        Token DEFERRABLE406=null;
        Token INITIALLY407=null;
        Token DEFERRED408=null;
        Token INITIALLY409=null;
        Token IMMEDIATE410=null;

        Object NOT405_tree=null;
        Object DEFERRABLE406_tree=null;
        Object INITIALLY407_tree=null;
        Object DEFERRED408_tree=null;
        Object INITIALLY409_tree=null;
        Object IMMEDIATE410_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:425:21: ( ( NOT )? DEFERRABLE ( INITIALLY DEFERRED | INITIALLY IMMEDIATE )? )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:425:23: ( NOT )? DEFERRABLE ( INITIALLY DEFERRED | INITIALLY IMMEDIATE )?
            {
            root_0 = (Object)adaptor.nil();

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:425:23: ( NOT )?
            int alt146=2;
            int LA146_0 = input.LA(1);

            if ( (LA146_0==NOT) ) {
                alt146=1;
            }
            switch (alt146) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:425:24: NOT
                    {
                    NOT405=(Token)match(input,NOT,FOLLOW_NOT_in_fk_clause_deferrable3503); 
                    NOT405_tree = (Object)adaptor.create(NOT405);
                    adaptor.addChild(root_0, NOT405_tree);


                    }
                    break;

            }

            DEFERRABLE406=(Token)match(input,DEFERRABLE,FOLLOW_DEFERRABLE_in_fk_clause_deferrable3507); 
            DEFERRABLE406_tree = (Object)adaptor.create(DEFERRABLE406);
            root_0 = (Object)adaptor.becomeRoot(DEFERRABLE406_tree, root_0);

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:425:42: ( INITIALLY DEFERRED | INITIALLY IMMEDIATE )?
            int alt147=3;
            alt147 = dfa147.predict(input);
            switch (alt147) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:425:43: INITIALLY DEFERRED
                    {
                    INITIALLY407=(Token)match(input,INITIALLY,FOLLOW_INITIALLY_in_fk_clause_deferrable3511); 
                    DEFERRED408=(Token)match(input,DEFERRED,FOLLOW_DEFERRED_in_fk_clause_deferrable3514); 
                    DEFERRED408_tree = (Object)adaptor.create(DEFERRED408);
                    adaptor.addChild(root_0, DEFERRED408_tree);


                    }
                    break;
                case 2 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:425:65: INITIALLY IMMEDIATE
                    {
                    INITIALLY409=(Token)match(input,INITIALLY,FOLLOW_INITIALLY_in_fk_clause_deferrable3518); 
                    IMMEDIATE410=(Token)match(input,IMMEDIATE,FOLLOW_IMMEDIATE_in_fk_clause_deferrable3521); 
                    IMMEDIATE410_tree = (Object)adaptor.create(IMMEDIATE410);
                    adaptor.addChild(root_0, IMMEDIATE410_tree);


                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "fk_clause_deferrable"

    public static class drop_table_stmt_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "drop_table_stmt"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:428:1: drop_table_stmt : DROP TABLE ( IF EXISTS )? (database_name= id DOT )? table_name= id -> ^( DROP_TABLE ^( OPTIONS ( EXISTS )? ) ^( $table_name ( $database_name)? ) ) ;
    public final SqlParser.drop_table_stmt_return drop_table_stmt() throws RecognitionException {
        SqlParser.drop_table_stmt_return retval = new SqlParser.drop_table_stmt_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token DROP411=null;
        Token TABLE412=null;
        Token IF413=null;
        Token EXISTS414=null;
        Token DOT415=null;
        SqlParser.id_return database_name = null;

        SqlParser.id_return table_name = null;


        Object DROP411_tree=null;
        Object TABLE412_tree=null;
        Object IF413_tree=null;
        Object EXISTS414_tree=null;
        Object DOT415_tree=null;
        RewriteRuleTokenStream stream_TABLE=new RewriteRuleTokenStream(adaptor,"token TABLE");
        RewriteRuleTokenStream stream_EXISTS=new RewriteRuleTokenStream(adaptor,"token EXISTS");
        RewriteRuleTokenStream stream_DROP=new RewriteRuleTokenStream(adaptor,"token DROP");
        RewriteRuleTokenStream stream_DOT=new RewriteRuleTokenStream(adaptor,"token DOT");
        RewriteRuleTokenStream stream_IF=new RewriteRuleTokenStream(adaptor,"token IF");
        RewriteRuleSubtreeStream stream_id=new RewriteRuleSubtreeStream(adaptor,"rule id");
        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:428:16: ( DROP TABLE ( IF EXISTS )? (database_name= id DOT )? table_name= id -> ^( DROP_TABLE ^( OPTIONS ( EXISTS )? ) ^( $table_name ( $database_name)? ) ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:428:18: DROP TABLE ( IF EXISTS )? (database_name= id DOT )? table_name= id
            {
            DROP411=(Token)match(input,DROP,FOLLOW_DROP_in_drop_table_stmt3531);  
            stream_DROP.add(DROP411);

            TABLE412=(Token)match(input,TABLE,FOLLOW_TABLE_in_drop_table_stmt3533);  
            stream_TABLE.add(TABLE412);

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:428:29: ( IF EXISTS )?
            int alt148=2;
            int LA148_0 = input.LA(1);

            if ( (LA148_0==IF) ) {
                int LA148_1 = input.LA(2);

                if ( (LA148_1==EXISTS) ) {
                    alt148=1;
                }
            }
            switch (alt148) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:428:30: IF EXISTS
                    {
                    IF413=(Token)match(input,IF,FOLLOW_IF_in_drop_table_stmt3536);  
                    stream_IF.add(IF413);

                    EXISTS414=(Token)match(input,EXISTS,FOLLOW_EXISTS_in_drop_table_stmt3538);  
                    stream_EXISTS.add(EXISTS414);


                    }
                    break;

            }

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:428:42: (database_name= id DOT )?
            int alt149=2;
            int LA149_0 = input.LA(1);

            if ( (LA149_0==ID) ) {
                int LA149_1 = input.LA(2);

                if ( (LA149_1==DOT) ) {
                    alt149=1;
                }
            }
            else if ( ((LA149_0>=EXPLAIN && LA149_0<=PLAN)||(LA149_0>=INDEXED && LA149_0<=BY)||(LA149_0>=OR && LA149_0<=ESCAPE)||(LA149_0>=IS && LA149_0<=BETWEEN)||LA149_0==COLLATE||(LA149_0>=DISTINCT && LA149_0<=THEN)||(LA149_0>=CURRENT_TIME && LA149_0<=CURRENT_TIMESTAMP)||(LA149_0>=RAISE && LA149_0<=FAIL)||(LA149_0>=PRAGMA && LA149_0<=FOR)||(LA149_0>=UNIQUE && LA149_0<=ALTER)||(LA149_0>=RENAME && LA149_0<=SCHEMA)) ) {
                int LA149_2 = input.LA(2);

                if ( (LA149_2==DOT) ) {
                    alt149=1;
                }
            }
            switch (alt149) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:428:43: database_name= id DOT
                    {
                    pushFollow(FOLLOW_id_in_drop_table_stmt3545);
                    database_name=id();

                    state._fsp--;

                    stream_id.add(database_name.getTree());
                    DOT415=(Token)match(input,DOT,FOLLOW_DOT_in_drop_table_stmt3547);  
                    stream_DOT.add(DOT415);


                    }
                    break;

            }

            pushFollow(FOLLOW_id_in_drop_table_stmt3553);
            table_name=id();

            state._fsp--;

            stream_id.add(table_name.getTree());


            // AST REWRITE
            // elements: database_name, table_name, EXISTS
            // token labels: 
            // rule labels: database_name, retval, table_name
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_database_name=new RewriteRuleSubtreeStream(adaptor,"rule database_name",database_name!=null?database_name.tree:null);
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_table_name=new RewriteRuleSubtreeStream(adaptor,"rule table_name",table_name!=null?table_name.tree:null);

            root_0 = (Object)adaptor.nil();
            // 429:1: -> ^( DROP_TABLE ^( OPTIONS ( EXISTS )? ) ^( $table_name ( $database_name)? ) )
            {
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:429:4: ^( DROP_TABLE ^( OPTIONS ( EXISTS )? ) ^( $table_name ( $database_name)? ) )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(DROP_TABLE, "DROP_TABLE"), root_1);

                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:429:17: ^( OPTIONS ( EXISTS )? )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(OPTIONS, "OPTIONS"), root_2);

                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:429:27: ( EXISTS )?
                if ( stream_EXISTS.hasNext() ) {
                    adaptor.addChild(root_2, stream_EXISTS.nextNode());

                }
                stream_EXISTS.reset();

                adaptor.addChild(root_1, root_2);
                }
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:429:36: ^( $table_name ( $database_name)? )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot(stream_table_name.nextNode(), root_2);

                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:429:50: ( $database_name)?
                if ( stream_database_name.hasNext() ) {
                    adaptor.addChild(root_2, stream_database_name.nextTree());

                }
                stream_database_name.reset();

                adaptor.addChild(root_1, root_2);
                }

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "drop_table_stmt"

    public static class alter_table_stmt_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "alter_table_stmt"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:432:1: alter_table_stmt : ALTER TABLE ( ONLY )? (database_name= id DOT )? table_name= id ( RENAME TO new_table_name= id | ADD ( COLUMN )? column_def | ADD table_constraint ( COMMA table_constraint )* ) ;
    public final SqlParser.alter_table_stmt_return alter_table_stmt() throws RecognitionException {
        SqlParser.alter_table_stmt_return retval = new SqlParser.alter_table_stmt_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ALTER416=null;
        Token TABLE417=null;
        Token ONLY418=null;
        Token DOT419=null;
        Token RENAME420=null;
        Token TO421=null;
        Token ADD422=null;
        Token COLUMN423=null;
        Token ADD425=null;
        Token COMMA427=null;
        SqlParser.id_return database_name = null;

        SqlParser.id_return table_name = null;

        SqlParser.id_return new_table_name = null;

        SqlParser.column_def_return column_def424 = null;

        SqlParser.table_constraint_return table_constraint426 = null;

        SqlParser.table_constraint_return table_constraint428 = null;


        Object ALTER416_tree=null;
        Object TABLE417_tree=null;
        Object ONLY418_tree=null;
        Object DOT419_tree=null;
        Object RENAME420_tree=null;
        Object TO421_tree=null;
        Object ADD422_tree=null;
        Object COLUMN423_tree=null;
        Object ADD425_tree=null;
        Object COMMA427_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:432:17: ( ALTER TABLE ( ONLY )? (database_name= id DOT )? table_name= id ( RENAME TO new_table_name= id | ADD ( COLUMN )? column_def | ADD table_constraint ( COMMA table_constraint )* ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:432:19: ALTER TABLE ( ONLY )? (database_name= id DOT )? table_name= id ( RENAME TO new_table_name= id | ADD ( COLUMN )? column_def | ADD table_constraint ( COMMA table_constraint )* )
            {
            root_0 = (Object)adaptor.nil();

            ALTER416=(Token)match(input,ALTER,FOLLOW_ALTER_in_alter_table_stmt3583); 
            ALTER416_tree = (Object)adaptor.create(ALTER416);
            adaptor.addChild(root_0, ALTER416_tree);

            TABLE417=(Token)match(input,TABLE,FOLLOW_TABLE_in_alter_table_stmt3585); 
            TABLE417_tree = (Object)adaptor.create(TABLE417);
            adaptor.addChild(root_0, TABLE417_tree);

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:432:31: ( ONLY )?
            int alt150=2;
            int LA150_0 = input.LA(1);

            if ( (LA150_0==ONLY) ) {
                alt150=1;
            }
            switch (alt150) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:432:31: ONLY
                    {
                    ONLY418=(Token)match(input,ONLY,FOLLOW_ONLY_in_alter_table_stmt3587); 
                    ONLY418_tree = (Object)adaptor.create(ONLY418);
                    adaptor.addChild(root_0, ONLY418_tree);


                    }
                    break;

            }

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:432:37: (database_name= id DOT )?
            int alt151=2;
            int LA151_0 = input.LA(1);

            if ( (LA151_0==ID) ) {
                int LA151_1 = input.LA(2);

                if ( (LA151_1==DOT) ) {
                    alt151=1;
                }
            }
            else if ( ((LA151_0>=EXPLAIN && LA151_0<=PLAN)||(LA151_0>=INDEXED && LA151_0<=BY)||(LA151_0>=OR && LA151_0<=ESCAPE)||(LA151_0>=IS && LA151_0<=BETWEEN)||LA151_0==COLLATE||(LA151_0>=DISTINCT && LA151_0<=THEN)||(LA151_0>=CURRENT_TIME && LA151_0<=CURRENT_TIMESTAMP)||(LA151_0>=RAISE && LA151_0<=FAIL)||(LA151_0>=PRAGMA && LA151_0<=FOR)||(LA151_0>=UNIQUE && LA151_0<=ALTER)||(LA151_0>=RENAME && LA151_0<=SCHEMA)) ) {
                int LA151_2 = input.LA(2);

                if ( (LA151_2==DOT) ) {
                    alt151=1;
                }
            }
            switch (alt151) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:432:38: database_name= id DOT
                    {
                    pushFollow(FOLLOW_id_in_alter_table_stmt3593);
                    database_name=id();

                    state._fsp--;

                    adaptor.addChild(root_0, database_name.getTree());
                    DOT419=(Token)match(input,DOT,FOLLOW_DOT_in_alter_table_stmt3595); 
                    DOT419_tree = (Object)adaptor.create(DOT419);
                    adaptor.addChild(root_0, DOT419_tree);


                    }
                    break;

            }

            pushFollow(FOLLOW_id_in_alter_table_stmt3601);
            table_name=id();

            state._fsp--;

            adaptor.addChild(root_0, table_name.getTree());
             builder.setEntityTo( table_name.getTree().toString()); 
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:432:134: ( RENAME TO new_table_name= id | ADD ( COLUMN )? column_def | ADD table_constraint ( COMMA table_constraint )* )
            int alt154=3;
            alt154 = dfa154.predict(input);
            switch (alt154) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:432:135: RENAME TO new_table_name= id
                    {
                    RENAME420=(Token)match(input,RENAME,FOLLOW_RENAME_in_alter_table_stmt3606); 
                    RENAME420_tree = (Object)adaptor.create(RENAME420);
                    adaptor.addChild(root_0, RENAME420_tree);

                    TO421=(Token)match(input,TO,FOLLOW_TO_in_alter_table_stmt3608); 
                    TO421_tree = (Object)adaptor.create(TO421);
                    adaptor.addChild(root_0, TO421_tree);

                    pushFollow(FOLLOW_id_in_alter_table_stmt3612);
                    new_table_name=id();

                    state._fsp--;

                    adaptor.addChild(root_0, new_table_name.getTree());

                    }
                    break;
                case 2 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:432:165: ADD ( COLUMN )? column_def
                    {
                    ADD422=(Token)match(input,ADD,FOLLOW_ADD_in_alter_table_stmt3616); 
                    ADD422_tree = (Object)adaptor.create(ADD422);
                    adaptor.addChild(root_0, ADD422_tree);

                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:432:169: ( COLUMN )?
                    int alt152=2;
                    int LA152_0 = input.LA(1);

                    if ( (LA152_0==COLUMN) ) {
                        alt152=1;
                    }
                    switch (alt152) {
                        case 1 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:432:170: COLUMN
                            {
                            COLUMN423=(Token)match(input,COLUMN,FOLLOW_COLUMN_in_alter_table_stmt3619); 
                            COLUMN423_tree = (Object)adaptor.create(COLUMN423);
                            adaptor.addChild(root_0, COLUMN423_tree);


                            }
                            break;

                    }

                    pushFollow(FOLLOW_column_def_in_alter_table_stmt3623);
                    column_def424=column_def();

                    state._fsp--;

                    adaptor.addChild(root_0, column_def424.getTree());

                    }
                    break;
                case 3 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:432:192: ADD table_constraint ( COMMA table_constraint )*
                    {
                    ADD425=(Token)match(input,ADD,FOLLOW_ADD_in_alter_table_stmt3627); 
                    ADD425_tree = (Object)adaptor.create(ADD425);
                    adaptor.addChild(root_0, ADD425_tree);

                    pushFollow(FOLLOW_table_constraint_in_alter_table_stmt3629);
                    table_constraint426=table_constraint();

                    state._fsp--;

                    adaptor.addChild(root_0, table_constraint426.getTree());
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:432:213: ( COMMA table_constraint )*
                    loop153:
                    do {
                        int alt153=2;
                        int LA153_0 = input.LA(1);

                        if ( (LA153_0==COMMA) ) {
                            alt153=1;
                        }


                        switch (alt153) {
                    	case 1 :
                    	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:432:214: COMMA table_constraint
                    	    {
                    	    COMMA427=(Token)match(input,COMMA,FOLLOW_COMMA_in_alter_table_stmt3632); 
                    	    COMMA427_tree = (Object)adaptor.create(COMMA427);
                    	    adaptor.addChild(root_0, COMMA427_tree);

                    	    pushFollow(FOLLOW_table_constraint_in_alter_table_stmt3634);
                    	    table_constraint428=table_constraint();

                    	    state._fsp--;

                    	    adaptor.addChild(root_0, table_constraint428.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop153;
                        }
                    } while (true);


                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "alter_table_stmt"

    public static class create_view_stmt_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "create_view_stmt"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:435:1: create_view_stmt : CREATE ( TEMPORARY )? VIEW ( IF NOT EXISTS )? (database_name= id DOT )? view_name= id AS select_stmt ;
    public final SqlParser.create_view_stmt_return create_view_stmt() throws RecognitionException {
        SqlParser.create_view_stmt_return retval = new SqlParser.create_view_stmt_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token CREATE429=null;
        Token TEMPORARY430=null;
        Token VIEW431=null;
        Token IF432=null;
        Token NOT433=null;
        Token EXISTS434=null;
        Token DOT435=null;
        Token AS436=null;
        SqlParser.id_return database_name = null;

        SqlParser.id_return view_name = null;

        SqlParser.select_stmt_return select_stmt437 = null;


        Object CREATE429_tree=null;
        Object TEMPORARY430_tree=null;
        Object VIEW431_tree=null;
        Object IF432_tree=null;
        Object NOT433_tree=null;
        Object EXISTS434_tree=null;
        Object DOT435_tree=null;
        Object AS436_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:435:17: ( CREATE ( TEMPORARY )? VIEW ( IF NOT EXISTS )? (database_name= id DOT )? view_name= id AS select_stmt )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:435:19: CREATE ( TEMPORARY )? VIEW ( IF NOT EXISTS )? (database_name= id DOT )? view_name= id AS select_stmt
            {
            root_0 = (Object)adaptor.nil();

            CREATE429=(Token)match(input,CREATE,FOLLOW_CREATE_in_create_view_stmt3645); 
            CREATE429_tree = (Object)adaptor.create(CREATE429);
            adaptor.addChild(root_0, CREATE429_tree);

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:435:26: ( TEMPORARY )?
            int alt155=2;
            int LA155_0 = input.LA(1);

            if ( (LA155_0==TEMPORARY) ) {
                alt155=1;
            }
            switch (alt155) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:435:26: TEMPORARY
                    {
                    TEMPORARY430=(Token)match(input,TEMPORARY,FOLLOW_TEMPORARY_in_create_view_stmt3647); 
                    TEMPORARY430_tree = (Object)adaptor.create(TEMPORARY430);
                    adaptor.addChild(root_0, TEMPORARY430_tree);


                    }
                    break;

            }

            VIEW431=(Token)match(input,VIEW,FOLLOW_VIEW_in_create_view_stmt3650); 
            VIEW431_tree = (Object)adaptor.create(VIEW431);
            adaptor.addChild(root_0, VIEW431_tree);

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:435:42: ( IF NOT EXISTS )?
            int alt156=2;
            int LA156_0 = input.LA(1);

            if ( (LA156_0==IF) ) {
                int LA156_1 = input.LA(2);

                if ( (LA156_1==NOT) ) {
                    alt156=1;
                }
            }
            switch (alt156) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:435:43: IF NOT EXISTS
                    {
                    IF432=(Token)match(input,IF,FOLLOW_IF_in_create_view_stmt3653); 
                    IF432_tree = (Object)adaptor.create(IF432);
                    adaptor.addChild(root_0, IF432_tree);

                    NOT433=(Token)match(input,NOT,FOLLOW_NOT_in_create_view_stmt3655); 
                    NOT433_tree = (Object)adaptor.create(NOT433);
                    adaptor.addChild(root_0, NOT433_tree);

                    EXISTS434=(Token)match(input,EXISTS,FOLLOW_EXISTS_in_create_view_stmt3657); 
                    EXISTS434_tree = (Object)adaptor.create(EXISTS434);
                    adaptor.addChild(root_0, EXISTS434_tree);


                    }
                    break;

            }

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:435:59: (database_name= id DOT )?
            int alt157=2;
            int LA157_0 = input.LA(1);

            if ( (LA157_0==ID) ) {
                int LA157_1 = input.LA(2);

                if ( (LA157_1==DOT) ) {
                    alt157=1;
                }
            }
            else if ( ((LA157_0>=EXPLAIN && LA157_0<=PLAN)||(LA157_0>=INDEXED && LA157_0<=BY)||(LA157_0>=OR && LA157_0<=ESCAPE)||(LA157_0>=IS && LA157_0<=BETWEEN)||LA157_0==COLLATE||(LA157_0>=DISTINCT && LA157_0<=THEN)||(LA157_0>=CURRENT_TIME && LA157_0<=CURRENT_TIMESTAMP)||(LA157_0>=RAISE && LA157_0<=FAIL)||(LA157_0>=PRAGMA && LA157_0<=FOR)||(LA157_0>=UNIQUE && LA157_0<=ALTER)||(LA157_0>=RENAME && LA157_0<=SCHEMA)) ) {
                int LA157_2 = input.LA(2);

                if ( (LA157_2==DOT) ) {
                    alt157=1;
                }
            }
            switch (alt157) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:435:60: database_name= id DOT
                    {
                    pushFollow(FOLLOW_id_in_create_view_stmt3664);
                    database_name=id();

                    state._fsp--;

                    adaptor.addChild(root_0, database_name.getTree());
                    DOT435=(Token)match(input,DOT,FOLLOW_DOT_in_create_view_stmt3666); 
                    DOT435_tree = (Object)adaptor.create(DOT435);
                    adaptor.addChild(root_0, DOT435_tree);


                    }
                    break;

            }

            pushFollow(FOLLOW_id_in_create_view_stmt3672);
            view_name=id();

            state._fsp--;

            adaptor.addChild(root_0, view_name.getTree());
            AS436=(Token)match(input,AS,FOLLOW_AS_in_create_view_stmt3674); 
            AS436_tree = (Object)adaptor.create(AS436);
            adaptor.addChild(root_0, AS436_tree);

            pushFollow(FOLLOW_select_stmt_in_create_view_stmt3676);
            select_stmt437=select_stmt();

            state._fsp--;

            adaptor.addChild(root_0, select_stmt437.getTree());

            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "create_view_stmt"

    public static class drop_view_stmt_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "drop_view_stmt"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:438:1: drop_view_stmt : DROP VIEW ( IF EXISTS )? (database_name= id DOT )? view_name= id ;
    public final SqlParser.drop_view_stmt_return drop_view_stmt() throws RecognitionException {
        SqlParser.drop_view_stmt_return retval = new SqlParser.drop_view_stmt_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token DROP438=null;
        Token VIEW439=null;
        Token IF440=null;
        Token EXISTS441=null;
        Token DOT442=null;
        SqlParser.id_return database_name = null;

        SqlParser.id_return view_name = null;


        Object DROP438_tree=null;
        Object VIEW439_tree=null;
        Object IF440_tree=null;
        Object EXISTS441_tree=null;
        Object DOT442_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:438:15: ( DROP VIEW ( IF EXISTS )? (database_name= id DOT )? view_name= id )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:438:17: DROP VIEW ( IF EXISTS )? (database_name= id DOT )? view_name= id
            {
            root_0 = (Object)adaptor.nil();

            DROP438=(Token)match(input,DROP,FOLLOW_DROP_in_drop_view_stmt3684); 
            DROP438_tree = (Object)adaptor.create(DROP438);
            adaptor.addChild(root_0, DROP438_tree);

            VIEW439=(Token)match(input,VIEW,FOLLOW_VIEW_in_drop_view_stmt3686); 
            VIEW439_tree = (Object)adaptor.create(VIEW439);
            adaptor.addChild(root_0, VIEW439_tree);

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:438:27: ( IF EXISTS )?
            int alt158=2;
            int LA158_0 = input.LA(1);

            if ( (LA158_0==IF) ) {
                int LA158_1 = input.LA(2);

                if ( (LA158_1==EXISTS) ) {
                    alt158=1;
                }
            }
            switch (alt158) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:438:28: IF EXISTS
                    {
                    IF440=(Token)match(input,IF,FOLLOW_IF_in_drop_view_stmt3689); 
                    IF440_tree = (Object)adaptor.create(IF440);
                    adaptor.addChild(root_0, IF440_tree);

                    EXISTS441=(Token)match(input,EXISTS,FOLLOW_EXISTS_in_drop_view_stmt3691); 
                    EXISTS441_tree = (Object)adaptor.create(EXISTS441);
                    adaptor.addChild(root_0, EXISTS441_tree);


                    }
                    break;

            }

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:438:40: (database_name= id DOT )?
            int alt159=2;
            int LA159_0 = input.LA(1);

            if ( (LA159_0==ID) ) {
                int LA159_1 = input.LA(2);

                if ( (LA159_1==DOT) ) {
                    alt159=1;
                }
            }
            else if ( ((LA159_0>=EXPLAIN && LA159_0<=PLAN)||(LA159_0>=INDEXED && LA159_0<=BY)||(LA159_0>=OR && LA159_0<=ESCAPE)||(LA159_0>=IS && LA159_0<=BETWEEN)||LA159_0==COLLATE||(LA159_0>=DISTINCT && LA159_0<=THEN)||(LA159_0>=CURRENT_TIME && LA159_0<=CURRENT_TIMESTAMP)||(LA159_0>=RAISE && LA159_0<=FAIL)||(LA159_0>=PRAGMA && LA159_0<=FOR)||(LA159_0>=UNIQUE && LA159_0<=ALTER)||(LA159_0>=RENAME && LA159_0<=SCHEMA)) ) {
                int LA159_2 = input.LA(2);

                if ( (LA159_2==DOT) ) {
                    alt159=1;
                }
            }
            switch (alt159) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:438:41: database_name= id DOT
                    {
                    pushFollow(FOLLOW_id_in_drop_view_stmt3698);
                    database_name=id();

                    state._fsp--;

                    adaptor.addChild(root_0, database_name.getTree());
                    DOT442=(Token)match(input,DOT,FOLLOW_DOT_in_drop_view_stmt3700); 
                    DOT442_tree = (Object)adaptor.create(DOT442);
                    adaptor.addChild(root_0, DOT442_tree);


                    }
                    break;

            }

            pushFollow(FOLLOW_id_in_drop_view_stmt3706);
            view_name=id();

            state._fsp--;

            adaptor.addChild(root_0, view_name.getTree());

            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "drop_view_stmt"

    public static class create_index_stmt_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "create_index_stmt"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:441:1: create_index_stmt : CREATE ( UNIQUE )? INDEX ( IF NOT EXISTS )? (database_name= id DOT )? index_name= id ON table_name= id LPAREN columns+= indexed_column ( COMMA columns+= indexed_column )* RPAREN -> ^( CREATE_INDEX ^( OPTIONS ( UNIQUE )? ( EXISTS )? ) ^( $index_name ( $database_name)? ) $table_name ( ^( COLUMNS ( $columns)+ ) )? ) ;
    public final SqlParser.create_index_stmt_return create_index_stmt() throws RecognitionException {
        SqlParser.create_index_stmt_return retval = new SqlParser.create_index_stmt_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token CREATE443=null;
        Token UNIQUE444=null;
        Token INDEX445=null;
        Token IF446=null;
        Token NOT447=null;
        Token EXISTS448=null;
        Token DOT449=null;
        Token ON450=null;
        Token LPAREN451=null;
        Token COMMA452=null;
        Token RPAREN453=null;
        List list_columns=null;
        SqlParser.id_return database_name = null;

        SqlParser.id_return index_name = null;

        SqlParser.id_return table_name = null;

        SqlParser.indexed_column_return columns = null;
         columns = null;
        Object CREATE443_tree=null;
        Object UNIQUE444_tree=null;
        Object INDEX445_tree=null;
        Object IF446_tree=null;
        Object NOT447_tree=null;
        Object EXISTS448_tree=null;
        Object DOT449_tree=null;
        Object ON450_tree=null;
        Object LPAREN451_tree=null;
        Object COMMA452_tree=null;
        Object RPAREN453_tree=null;
        RewriteRuleTokenStream stream_INDEX=new RewriteRuleTokenStream(adaptor,"token INDEX");
        RewriteRuleTokenStream stream_ON=new RewriteRuleTokenStream(adaptor,"token ON");
        RewriteRuleTokenStream stream_UNIQUE=new RewriteRuleTokenStream(adaptor,"token UNIQUE");
        RewriteRuleTokenStream stream_RPAREN=new RewriteRuleTokenStream(adaptor,"token RPAREN");
        RewriteRuleTokenStream stream_CREATE=new RewriteRuleTokenStream(adaptor,"token CREATE");
        RewriteRuleTokenStream stream_NOT=new RewriteRuleTokenStream(adaptor,"token NOT");
        RewriteRuleTokenStream stream_EXISTS=new RewriteRuleTokenStream(adaptor,"token EXISTS");
        RewriteRuleTokenStream stream_DOT=new RewriteRuleTokenStream(adaptor,"token DOT");
        RewriteRuleTokenStream stream_COMMA=new RewriteRuleTokenStream(adaptor,"token COMMA");
        RewriteRuleTokenStream stream_LPAREN=new RewriteRuleTokenStream(adaptor,"token LPAREN");
        RewriteRuleTokenStream stream_IF=new RewriteRuleTokenStream(adaptor,"token IF");
        RewriteRuleSubtreeStream stream_id=new RewriteRuleSubtreeStream(adaptor,"rule id");
        RewriteRuleSubtreeStream stream_indexed_column=new RewriteRuleSubtreeStream(adaptor,"rule indexed_column");
        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:441:18: ( CREATE ( UNIQUE )? INDEX ( IF NOT EXISTS )? (database_name= id DOT )? index_name= id ON table_name= id LPAREN columns+= indexed_column ( COMMA columns+= indexed_column )* RPAREN -> ^( CREATE_INDEX ^( OPTIONS ( UNIQUE )? ( EXISTS )? ) ^( $index_name ( $database_name)? ) $table_name ( ^( COLUMNS ( $columns)+ ) )? ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:441:20: CREATE ( UNIQUE )? INDEX ( IF NOT EXISTS )? (database_name= id DOT )? index_name= id ON table_name= id LPAREN columns+= indexed_column ( COMMA columns+= indexed_column )* RPAREN
            {
            CREATE443=(Token)match(input,CREATE,FOLLOW_CREATE_in_create_index_stmt3714);  
            stream_CREATE.add(CREATE443);

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:441:27: ( UNIQUE )?
            int alt160=2;
            int LA160_0 = input.LA(1);

            if ( (LA160_0==UNIQUE) ) {
                alt160=1;
            }
            switch (alt160) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:441:28: UNIQUE
                    {
                    UNIQUE444=(Token)match(input,UNIQUE,FOLLOW_UNIQUE_in_create_index_stmt3717);  
                    stream_UNIQUE.add(UNIQUE444);


                    }
                    break;

            }

            INDEX445=(Token)match(input,INDEX,FOLLOW_INDEX_in_create_index_stmt3721);  
            stream_INDEX.add(INDEX445);

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:441:43: ( IF NOT EXISTS )?
            int alt161=2;
            int LA161_0 = input.LA(1);

            if ( (LA161_0==IF) ) {
                int LA161_1 = input.LA(2);

                if ( (LA161_1==NOT) ) {
                    alt161=1;
                }
            }
            switch (alt161) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:441:44: IF NOT EXISTS
                    {
                    IF446=(Token)match(input,IF,FOLLOW_IF_in_create_index_stmt3724);  
                    stream_IF.add(IF446);

                    NOT447=(Token)match(input,NOT,FOLLOW_NOT_in_create_index_stmt3726);  
                    stream_NOT.add(NOT447);

                    EXISTS448=(Token)match(input,EXISTS,FOLLOW_EXISTS_in_create_index_stmt3728);  
                    stream_EXISTS.add(EXISTS448);


                    }
                    break;

            }

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:441:60: (database_name= id DOT )?
            int alt162=2;
            int LA162_0 = input.LA(1);

            if ( (LA162_0==ID) ) {
                int LA162_1 = input.LA(2);

                if ( (LA162_1==DOT) ) {
                    alt162=1;
                }
            }
            else if ( ((LA162_0>=EXPLAIN && LA162_0<=PLAN)||(LA162_0>=INDEXED && LA162_0<=BY)||(LA162_0>=OR && LA162_0<=ESCAPE)||(LA162_0>=IS && LA162_0<=BETWEEN)||LA162_0==COLLATE||(LA162_0>=DISTINCT && LA162_0<=THEN)||(LA162_0>=CURRENT_TIME && LA162_0<=CURRENT_TIMESTAMP)||(LA162_0>=RAISE && LA162_0<=FAIL)||(LA162_0>=PRAGMA && LA162_0<=FOR)||(LA162_0>=UNIQUE && LA162_0<=ALTER)||(LA162_0>=RENAME && LA162_0<=SCHEMA)) ) {
                int LA162_2 = input.LA(2);

                if ( (LA162_2==DOT) ) {
                    alt162=1;
                }
            }
            switch (alt162) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:441:61: database_name= id DOT
                    {
                    pushFollow(FOLLOW_id_in_create_index_stmt3735);
                    database_name=id();

                    state._fsp--;

                    stream_id.add(database_name.getTree());
                    DOT449=(Token)match(input,DOT,FOLLOW_DOT_in_create_index_stmt3737);  
                    stream_DOT.add(DOT449);


                    }
                    break;

            }

            pushFollow(FOLLOW_id_in_create_index_stmt3743);
            index_name=id();

            state._fsp--;

            stream_id.add(index_name.getTree());
            ON450=(Token)match(input,ON,FOLLOW_ON_in_create_index_stmt3747);  
            stream_ON.add(ON450);

            pushFollow(FOLLOW_id_in_create_index_stmt3751);
            table_name=id();

            state._fsp--;

            stream_id.add(table_name.getTree());
            LPAREN451=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_create_index_stmt3753);  
            stream_LPAREN.add(LPAREN451);

            pushFollow(FOLLOW_indexed_column_in_create_index_stmt3757);
            columns=indexed_column();

            state._fsp--;

            stream_indexed_column.add(columns.getTree());
            if (list_columns==null) list_columns=new ArrayList();
            list_columns.add(columns.getTree());

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:442:51: ( COMMA columns+= indexed_column )*
            loop163:
            do {
                int alt163=2;
                int LA163_0 = input.LA(1);

                if ( (LA163_0==COMMA) ) {
                    alt163=1;
                }


                switch (alt163) {
            	case 1 :
            	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:442:52: COMMA columns+= indexed_column
            	    {
            	    COMMA452=(Token)match(input,COMMA,FOLLOW_COMMA_in_create_index_stmt3760);  
            	    stream_COMMA.add(COMMA452);

            	    pushFollow(FOLLOW_indexed_column_in_create_index_stmt3764);
            	    columns=indexed_column();

            	    state._fsp--;

            	    stream_indexed_column.add(columns.getTree());
            	    if (list_columns==null) list_columns=new ArrayList();
            	    list_columns.add(columns.getTree());


            	    }
            	    break;

            	default :
            	    break loop163;
                }
            } while (true);

            RPAREN453=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_create_index_stmt3768);  
            stream_RPAREN.add(RPAREN453);



            // AST REWRITE
            // elements: table_name, database_name, UNIQUE, index_name, columns, EXISTS
            // token labels: 
            // rule labels: index_name, database_name, retval, table_name
            // token list labels: 
            // rule list labels: columns
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_index_name=new RewriteRuleSubtreeStream(adaptor,"rule index_name",index_name!=null?index_name.tree:null);
            RewriteRuleSubtreeStream stream_database_name=new RewriteRuleSubtreeStream(adaptor,"rule database_name",database_name!=null?database_name.tree:null);
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_table_name=new RewriteRuleSubtreeStream(adaptor,"rule table_name",table_name!=null?table_name.tree:null);
            RewriteRuleSubtreeStream stream_columns=new RewriteRuleSubtreeStream(adaptor,"token columns",list_columns);
            root_0 = (Object)adaptor.nil();
            // 443:1: -> ^( CREATE_INDEX ^( OPTIONS ( UNIQUE )? ( EXISTS )? ) ^( $index_name ( $database_name)? ) $table_name ( ^( COLUMNS ( $columns)+ ) )? )
            {
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:443:4: ^( CREATE_INDEX ^( OPTIONS ( UNIQUE )? ( EXISTS )? ) ^( $index_name ( $database_name)? ) $table_name ( ^( COLUMNS ( $columns)+ ) )? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(CREATE_INDEX, "CREATE_INDEX"), root_1);

                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:443:19: ^( OPTIONS ( UNIQUE )? ( EXISTS )? )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(OPTIONS, "OPTIONS"), root_2);

                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:443:29: ( UNIQUE )?
                if ( stream_UNIQUE.hasNext() ) {
                    adaptor.addChild(root_2, stream_UNIQUE.nextNode());

                }
                stream_UNIQUE.reset();
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:443:37: ( EXISTS )?
                if ( stream_EXISTS.hasNext() ) {
                    adaptor.addChild(root_2, stream_EXISTS.nextNode());

                }
                stream_EXISTS.reset();

                adaptor.addChild(root_1, root_2);
                }
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:443:46: ^( $index_name ( $database_name)? )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot(stream_index_name.nextNode(), root_2);

                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:443:60: ( $database_name)?
                if ( stream_database_name.hasNext() ) {
                    adaptor.addChild(root_2, stream_database_name.nextTree());

                }
                stream_database_name.reset();

                adaptor.addChild(root_1, root_2);
                }
                adaptor.addChild(root_1, stream_table_name.nextTree());
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:443:89: ( ^( COLUMNS ( $columns)+ ) )?
                if ( stream_columns.hasNext() ) {
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:443:89: ^( COLUMNS ( $columns)+ )
                    {
                    Object root_2 = (Object)adaptor.nil();
                    root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(COLUMNS, "COLUMNS"), root_2);

                    if ( !(stream_columns.hasNext()) ) {
                        throw new RewriteEarlyExitException();
                    }
                    while ( stream_columns.hasNext() ) {
                        adaptor.addChild(root_2, stream_columns.nextTree());

                    }
                    stream_columns.reset();

                    adaptor.addChild(root_1, root_2);
                    }

                }
                stream_columns.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "create_index_stmt"

    public static class indexed_column_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "indexed_column"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:445:1: indexed_column : column_name= id ( COLLATE collation_name= id )? ( ASC | DESC )? -> ^( $column_name ( ^( COLLATE $collation_name) )? ( ASC )? ( DESC )? ) ;
    public final SqlParser.indexed_column_return indexed_column() throws RecognitionException {
        SqlParser.indexed_column_return retval = new SqlParser.indexed_column_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token COLLATE454=null;
        Token ASC455=null;
        Token DESC456=null;
        SqlParser.id_return column_name = null;

        SqlParser.id_return collation_name = null;


        Object COLLATE454_tree=null;
        Object ASC455_tree=null;
        Object DESC456_tree=null;
        RewriteRuleTokenStream stream_ASC=new RewriteRuleTokenStream(adaptor,"token ASC");
        RewriteRuleTokenStream stream_DESC=new RewriteRuleTokenStream(adaptor,"token DESC");
        RewriteRuleTokenStream stream_COLLATE=new RewriteRuleTokenStream(adaptor,"token COLLATE");
        RewriteRuleSubtreeStream stream_id=new RewriteRuleSubtreeStream(adaptor,"rule id");
        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:445:15: (column_name= id ( COLLATE collation_name= id )? ( ASC | DESC )? -> ^( $column_name ( ^( COLLATE $collation_name) )? ( ASC )? ( DESC )? ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:445:17: column_name= id ( COLLATE collation_name= id )? ( ASC | DESC )?
            {
            pushFollow(FOLLOW_id_in_indexed_column3814);
            column_name=id();

            state._fsp--;

            stream_id.add(column_name.getTree());
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:445:32: ( COLLATE collation_name= id )?
            int alt164=2;
            int LA164_0 = input.LA(1);

            if ( (LA164_0==COLLATE) ) {
                alt164=1;
            }
            switch (alt164) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:445:33: COLLATE collation_name= id
                    {
                    COLLATE454=(Token)match(input,COLLATE,FOLLOW_COLLATE_in_indexed_column3817);  
                    stream_COLLATE.add(COLLATE454);

                    pushFollow(FOLLOW_id_in_indexed_column3821);
                    collation_name=id();

                    state._fsp--;

                    stream_id.add(collation_name.getTree());

                    }
                    break;

            }

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:445:61: ( ASC | DESC )?
            int alt165=3;
            int LA165_0 = input.LA(1);

            if ( (LA165_0==ASC) ) {
                alt165=1;
            }
            else if ( (LA165_0==DESC) ) {
                alt165=2;
            }
            switch (alt165) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:445:62: ASC
                    {
                    ASC455=(Token)match(input,ASC,FOLLOW_ASC_in_indexed_column3826);  
                    stream_ASC.add(ASC455);


                    }
                    break;
                case 2 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:445:68: DESC
                    {
                    DESC456=(Token)match(input,DESC,FOLLOW_DESC_in_indexed_column3830);  
                    stream_DESC.add(DESC456);


                    }
                    break;

            }



            // AST REWRITE
            // elements: column_name, DESC, ASC, collation_name, COLLATE
            // token labels: 
            // rule labels: retval, collation_name, column_name
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_collation_name=new RewriteRuleSubtreeStream(adaptor,"rule collation_name",collation_name!=null?collation_name.tree:null);
            RewriteRuleSubtreeStream stream_column_name=new RewriteRuleSubtreeStream(adaptor,"rule column_name",column_name!=null?column_name.tree:null);

            root_0 = (Object)adaptor.nil();
            // 446:1: -> ^( $column_name ( ^( COLLATE $collation_name) )? ( ASC )? ( DESC )? )
            {
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:446:4: ^( $column_name ( ^( COLLATE $collation_name) )? ( ASC )? ( DESC )? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(stream_column_name.nextNode(), root_1);

                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:446:19: ( ^( COLLATE $collation_name) )?
                if ( stream_collation_name.hasNext()||stream_COLLATE.hasNext() ) {
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:446:19: ^( COLLATE $collation_name)
                    {
                    Object root_2 = (Object)adaptor.nil();
                    root_2 = (Object)adaptor.becomeRoot(stream_COLLATE.nextNode(), root_2);

                    adaptor.addChild(root_2, stream_collation_name.nextTree());

                    adaptor.addChild(root_1, root_2);
                    }

                }
                stream_collation_name.reset();
                stream_COLLATE.reset();
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:446:47: ( ASC )?
                if ( stream_ASC.hasNext() ) {
                    adaptor.addChild(root_1, stream_ASC.nextNode());

                }
                stream_ASC.reset();
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:446:52: ( DESC )?
                if ( stream_DESC.hasNext() ) {
                    adaptor.addChild(root_1, stream_DESC.nextNode());

                }
                stream_DESC.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "indexed_column"

    public static class drop_index_stmt_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "drop_index_stmt"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:449:1: drop_index_stmt : DROP INDEX ( IF EXISTS )? (database_name= id DOT )? index_name= id -> ^( DROP_INDEX ^( OPTIONS ( EXISTS )? ) ^( $index_name ( $database_name)? ) ) ;
    public final SqlParser.drop_index_stmt_return drop_index_stmt() throws RecognitionException {
        SqlParser.drop_index_stmt_return retval = new SqlParser.drop_index_stmt_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token DROP457=null;
        Token INDEX458=null;
        Token IF459=null;
        Token EXISTS460=null;
        Token DOT461=null;
        SqlParser.id_return database_name = null;

        SqlParser.id_return index_name = null;


        Object DROP457_tree=null;
        Object INDEX458_tree=null;
        Object IF459_tree=null;
        Object EXISTS460_tree=null;
        Object DOT461_tree=null;
        RewriteRuleTokenStream stream_INDEX=new RewriteRuleTokenStream(adaptor,"token INDEX");
        RewriteRuleTokenStream stream_EXISTS=new RewriteRuleTokenStream(adaptor,"token EXISTS");
        RewriteRuleTokenStream stream_DROP=new RewriteRuleTokenStream(adaptor,"token DROP");
        RewriteRuleTokenStream stream_DOT=new RewriteRuleTokenStream(adaptor,"token DOT");
        RewriteRuleTokenStream stream_IF=new RewriteRuleTokenStream(adaptor,"token IF");
        RewriteRuleSubtreeStream stream_id=new RewriteRuleSubtreeStream(adaptor,"rule id");
        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:449:16: ( DROP INDEX ( IF EXISTS )? (database_name= id DOT )? index_name= id -> ^( DROP_INDEX ^( OPTIONS ( EXISTS )? ) ^( $index_name ( $database_name)? ) ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:449:18: DROP INDEX ( IF EXISTS )? (database_name= id DOT )? index_name= id
            {
            DROP457=(Token)match(input,DROP,FOLLOW_DROP_in_drop_index_stmt3861);  
            stream_DROP.add(DROP457);

            INDEX458=(Token)match(input,INDEX,FOLLOW_INDEX_in_drop_index_stmt3863);  
            stream_INDEX.add(INDEX458);

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:449:29: ( IF EXISTS )?
            int alt166=2;
            int LA166_0 = input.LA(1);

            if ( (LA166_0==IF) ) {
                int LA166_1 = input.LA(2);

                if ( (LA166_1==EXISTS) ) {
                    alt166=1;
                }
            }
            switch (alt166) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:449:30: IF EXISTS
                    {
                    IF459=(Token)match(input,IF,FOLLOW_IF_in_drop_index_stmt3866);  
                    stream_IF.add(IF459);

                    EXISTS460=(Token)match(input,EXISTS,FOLLOW_EXISTS_in_drop_index_stmt3868);  
                    stream_EXISTS.add(EXISTS460);


                    }
                    break;

            }

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:449:42: (database_name= id DOT )?
            int alt167=2;
            int LA167_0 = input.LA(1);

            if ( (LA167_0==ID) ) {
                int LA167_1 = input.LA(2);

                if ( (LA167_1==DOT) ) {
                    alt167=1;
                }
            }
            else if ( ((LA167_0>=EXPLAIN && LA167_0<=PLAN)||(LA167_0>=INDEXED && LA167_0<=BY)||(LA167_0>=OR && LA167_0<=ESCAPE)||(LA167_0>=IS && LA167_0<=BETWEEN)||LA167_0==COLLATE||(LA167_0>=DISTINCT && LA167_0<=THEN)||(LA167_0>=CURRENT_TIME && LA167_0<=CURRENT_TIMESTAMP)||(LA167_0>=RAISE && LA167_0<=FAIL)||(LA167_0>=PRAGMA && LA167_0<=FOR)||(LA167_0>=UNIQUE && LA167_0<=ALTER)||(LA167_0>=RENAME && LA167_0<=SCHEMA)) ) {
                int LA167_2 = input.LA(2);

                if ( (LA167_2==DOT) ) {
                    alt167=1;
                }
            }
            switch (alt167) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:449:43: database_name= id DOT
                    {
                    pushFollow(FOLLOW_id_in_drop_index_stmt3875);
                    database_name=id();

                    state._fsp--;

                    stream_id.add(database_name.getTree());
                    DOT461=(Token)match(input,DOT,FOLLOW_DOT_in_drop_index_stmt3877);  
                    stream_DOT.add(DOT461);


                    }
                    break;

            }

            pushFollow(FOLLOW_id_in_drop_index_stmt3883);
            index_name=id();

            state._fsp--;

            stream_id.add(index_name.getTree());


            // AST REWRITE
            // elements: database_name, EXISTS, index_name
            // token labels: 
            // rule labels: index_name, database_name, retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_index_name=new RewriteRuleSubtreeStream(adaptor,"rule index_name",index_name!=null?index_name.tree:null);
            RewriteRuleSubtreeStream stream_database_name=new RewriteRuleSubtreeStream(adaptor,"rule database_name",database_name!=null?database_name.tree:null);
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 450:1: -> ^( DROP_INDEX ^( OPTIONS ( EXISTS )? ) ^( $index_name ( $database_name)? ) )
            {
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:450:4: ^( DROP_INDEX ^( OPTIONS ( EXISTS )? ) ^( $index_name ( $database_name)? ) )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(DROP_INDEX, "DROP_INDEX"), root_1);

                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:450:17: ^( OPTIONS ( EXISTS )? )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(OPTIONS, "OPTIONS"), root_2);

                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:450:27: ( EXISTS )?
                if ( stream_EXISTS.hasNext() ) {
                    adaptor.addChild(root_2, stream_EXISTS.nextNode());

                }
                stream_EXISTS.reset();

                adaptor.addChild(root_1, root_2);
                }
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:450:36: ^( $index_name ( $database_name)? )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot(stream_index_name.nextNode(), root_2);

                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:450:50: ( $database_name)?
                if ( stream_database_name.hasNext() ) {
                    adaptor.addChild(root_2, stream_database_name.nextTree());

                }
                stream_database_name.reset();

                adaptor.addChild(root_1, root_2);
                }

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "drop_index_stmt"

    public static class create_trigger_stmt_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "create_trigger_stmt"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:453:1: create_trigger_stmt : CREATE ( TEMPORARY )? TRIGGER ( IF NOT EXISTS )? (database_name= id DOT )? trigger_name= id ( BEFORE | AFTER | INSTEAD OF )? ( DELETE | INSERT | UPDATE ( OF column_names+= id ( COMMA column_names+= id )* )? ) ON table_name= id ( FOR EACH ROW )? ( WHEN expr )? BEGIN ( ( update_stmt | insert_stmt | delete_stmt | select_stmt ) SEMI )+ END ;
    public final SqlParser.create_trigger_stmt_return create_trigger_stmt() throws RecognitionException {
        SqlParser.create_trigger_stmt_return retval = new SqlParser.create_trigger_stmt_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token CREATE462=null;
        Token TEMPORARY463=null;
        Token TRIGGER464=null;
        Token IF465=null;
        Token NOT466=null;
        Token EXISTS467=null;
        Token DOT468=null;
        Token BEFORE469=null;
        Token AFTER470=null;
        Token INSTEAD471=null;
        Token OF472=null;
        Token DELETE473=null;
        Token INSERT474=null;
        Token UPDATE475=null;
        Token OF476=null;
        Token COMMA477=null;
        Token ON478=null;
        Token FOR479=null;
        Token EACH480=null;
        Token ROW481=null;
        Token WHEN482=null;
        Token BEGIN484=null;
        Token SEMI489=null;
        Token END490=null;
        List list_column_names=null;
        SqlParser.id_return database_name = null;

        SqlParser.id_return trigger_name = null;

        SqlParser.id_return table_name = null;

        SqlParser.expr_return expr483 = null;

        SqlParser.update_stmt_return update_stmt485 = null;

        SqlParser.insert_stmt_return insert_stmt486 = null;

        SqlParser.delete_stmt_return delete_stmt487 = null;

        SqlParser.select_stmt_return select_stmt488 = null;

        SqlParser.id_return column_names = null;
         column_names = null;
        Object CREATE462_tree=null;
        Object TEMPORARY463_tree=null;
        Object TRIGGER464_tree=null;
        Object IF465_tree=null;
        Object NOT466_tree=null;
        Object EXISTS467_tree=null;
        Object DOT468_tree=null;
        Object BEFORE469_tree=null;
        Object AFTER470_tree=null;
        Object INSTEAD471_tree=null;
        Object OF472_tree=null;
        Object DELETE473_tree=null;
        Object INSERT474_tree=null;
        Object UPDATE475_tree=null;
        Object OF476_tree=null;
        Object COMMA477_tree=null;
        Object ON478_tree=null;
        Object FOR479_tree=null;
        Object EACH480_tree=null;
        Object ROW481_tree=null;
        Object WHEN482_tree=null;
        Object BEGIN484_tree=null;
        Object SEMI489_tree=null;
        Object END490_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:453:20: ( CREATE ( TEMPORARY )? TRIGGER ( IF NOT EXISTS )? (database_name= id DOT )? trigger_name= id ( BEFORE | AFTER | INSTEAD OF )? ( DELETE | INSERT | UPDATE ( OF column_names+= id ( COMMA column_names+= id )* )? ) ON table_name= id ( FOR EACH ROW )? ( WHEN expr )? BEGIN ( ( update_stmt | insert_stmt | delete_stmt | select_stmt ) SEMI )+ END )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:453:22: CREATE ( TEMPORARY )? TRIGGER ( IF NOT EXISTS )? (database_name= id DOT )? trigger_name= id ( BEFORE | AFTER | INSTEAD OF )? ( DELETE | INSERT | UPDATE ( OF column_names+= id ( COMMA column_names+= id )* )? ) ON table_name= id ( FOR EACH ROW )? ( WHEN expr )? BEGIN ( ( update_stmt | insert_stmt | delete_stmt | select_stmt ) SEMI )+ END
            {
            root_0 = (Object)adaptor.nil();

            CREATE462=(Token)match(input,CREATE,FOLLOW_CREATE_in_create_trigger_stmt3913); 
            CREATE462_tree = (Object)adaptor.create(CREATE462);
            adaptor.addChild(root_0, CREATE462_tree);

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:453:29: ( TEMPORARY )?
            int alt168=2;
            int LA168_0 = input.LA(1);

            if ( (LA168_0==TEMPORARY) ) {
                alt168=1;
            }
            switch (alt168) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:453:29: TEMPORARY
                    {
                    TEMPORARY463=(Token)match(input,TEMPORARY,FOLLOW_TEMPORARY_in_create_trigger_stmt3915); 
                    TEMPORARY463_tree = (Object)adaptor.create(TEMPORARY463);
                    adaptor.addChild(root_0, TEMPORARY463_tree);


                    }
                    break;

            }

            TRIGGER464=(Token)match(input,TRIGGER,FOLLOW_TRIGGER_in_create_trigger_stmt3918); 
            TRIGGER464_tree = (Object)adaptor.create(TRIGGER464);
            adaptor.addChild(root_0, TRIGGER464_tree);

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:453:48: ( IF NOT EXISTS )?
            int alt169=2;
            alt169 = dfa169.predict(input);
            switch (alt169) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:453:49: IF NOT EXISTS
                    {
                    IF465=(Token)match(input,IF,FOLLOW_IF_in_create_trigger_stmt3921); 
                    IF465_tree = (Object)adaptor.create(IF465);
                    adaptor.addChild(root_0, IF465_tree);

                    NOT466=(Token)match(input,NOT,FOLLOW_NOT_in_create_trigger_stmt3923); 
                    NOT466_tree = (Object)adaptor.create(NOT466);
                    adaptor.addChild(root_0, NOT466_tree);

                    EXISTS467=(Token)match(input,EXISTS,FOLLOW_EXISTS_in_create_trigger_stmt3925); 
                    EXISTS467_tree = (Object)adaptor.create(EXISTS467);
                    adaptor.addChild(root_0, EXISTS467_tree);


                    }
                    break;

            }

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:453:65: (database_name= id DOT )?
            int alt170=2;
            alt170 = dfa170.predict(input);
            switch (alt170) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:453:66: database_name= id DOT
                    {
                    pushFollow(FOLLOW_id_in_create_trigger_stmt3932);
                    database_name=id();

                    state._fsp--;

                    adaptor.addChild(root_0, database_name.getTree());
                    DOT468=(Token)match(input,DOT,FOLLOW_DOT_in_create_trigger_stmt3934); 
                    DOT468_tree = (Object)adaptor.create(DOT468);
                    adaptor.addChild(root_0, DOT468_tree);


                    }
                    break;

            }

            pushFollow(FOLLOW_id_in_create_trigger_stmt3940);
            trigger_name=id();

            state._fsp--;

            adaptor.addChild(root_0, trigger_name.getTree());
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:454:3: ( BEFORE | AFTER | INSTEAD OF )?
            int alt171=4;
            switch ( input.LA(1) ) {
                case BEFORE:
                    {
                    alt171=1;
                    }
                    break;
                case AFTER:
                    {
                    alt171=2;
                    }
                    break;
                case INSTEAD:
                    {
                    alt171=3;
                    }
                    break;
            }

            switch (alt171) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:454:4: BEFORE
                    {
                    BEFORE469=(Token)match(input,BEFORE,FOLLOW_BEFORE_in_create_trigger_stmt3945); 
                    BEFORE469_tree = (Object)adaptor.create(BEFORE469);
                    adaptor.addChild(root_0, BEFORE469_tree);


                    }
                    break;
                case 2 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:454:13: AFTER
                    {
                    AFTER470=(Token)match(input,AFTER,FOLLOW_AFTER_in_create_trigger_stmt3949); 
                    AFTER470_tree = (Object)adaptor.create(AFTER470);
                    adaptor.addChild(root_0, AFTER470_tree);


                    }
                    break;
                case 3 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:454:21: INSTEAD OF
                    {
                    INSTEAD471=(Token)match(input,INSTEAD,FOLLOW_INSTEAD_in_create_trigger_stmt3953); 
                    INSTEAD471_tree = (Object)adaptor.create(INSTEAD471);
                    adaptor.addChild(root_0, INSTEAD471_tree);

                    OF472=(Token)match(input,OF,FOLLOW_OF_in_create_trigger_stmt3955); 
                    OF472_tree = (Object)adaptor.create(OF472);
                    adaptor.addChild(root_0, OF472_tree);


                    }
                    break;

            }

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:454:34: ( DELETE | INSERT | UPDATE ( OF column_names+= id ( COMMA column_names+= id )* )? )
            int alt174=3;
            switch ( input.LA(1) ) {
            case DELETE:
                {
                alt174=1;
                }
                break;
            case INSERT:
                {
                alt174=2;
                }
                break;
            case UPDATE:
                {
                alt174=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 174, 0, input);

                throw nvae;
            }

            switch (alt174) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:454:35: DELETE
                    {
                    DELETE473=(Token)match(input,DELETE,FOLLOW_DELETE_in_create_trigger_stmt3960); 
                    DELETE473_tree = (Object)adaptor.create(DELETE473);
                    adaptor.addChild(root_0, DELETE473_tree);


                    }
                    break;
                case 2 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:454:44: INSERT
                    {
                    INSERT474=(Token)match(input,INSERT,FOLLOW_INSERT_in_create_trigger_stmt3964); 
                    INSERT474_tree = (Object)adaptor.create(INSERT474);
                    adaptor.addChild(root_0, INSERT474_tree);


                    }
                    break;
                case 3 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:454:53: UPDATE ( OF column_names+= id ( COMMA column_names+= id )* )?
                    {
                    UPDATE475=(Token)match(input,UPDATE,FOLLOW_UPDATE_in_create_trigger_stmt3968); 
                    UPDATE475_tree = (Object)adaptor.create(UPDATE475);
                    adaptor.addChild(root_0, UPDATE475_tree);

                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:454:60: ( OF column_names+= id ( COMMA column_names+= id )* )?
                    int alt173=2;
                    int LA173_0 = input.LA(1);

                    if ( (LA173_0==OF) ) {
                        alt173=1;
                    }
                    switch (alt173) {
                        case 1 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:454:61: OF column_names+= id ( COMMA column_names+= id )*
                            {
                            OF476=(Token)match(input,OF,FOLLOW_OF_in_create_trigger_stmt3971); 
                            OF476_tree = (Object)adaptor.create(OF476);
                            adaptor.addChild(root_0, OF476_tree);

                            pushFollow(FOLLOW_id_in_create_trigger_stmt3975);
                            column_names=id();

                            state._fsp--;

                            adaptor.addChild(root_0, column_names.getTree());
                            if (list_column_names==null) list_column_names=new ArrayList();
                            list_column_names.add(column_names.getTree());

                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:454:81: ( COMMA column_names+= id )*
                            loop172:
                            do {
                                int alt172=2;
                                int LA172_0 = input.LA(1);

                                if ( (LA172_0==COMMA) ) {
                                    alt172=1;
                                }


                                switch (alt172) {
                            	case 1 :
                            	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:454:82: COMMA column_names+= id
                            	    {
                            	    COMMA477=(Token)match(input,COMMA,FOLLOW_COMMA_in_create_trigger_stmt3978); 
                            	    COMMA477_tree = (Object)adaptor.create(COMMA477);
                            	    adaptor.addChild(root_0, COMMA477_tree);

                            	    pushFollow(FOLLOW_id_in_create_trigger_stmt3982);
                            	    column_names=id();

                            	    state._fsp--;

                            	    adaptor.addChild(root_0, column_names.getTree());
                            	    if (list_column_names==null) list_column_names=new ArrayList();
                            	    list_column_names.add(column_names.getTree());


                            	    }
                            	    break;

                            	default :
                            	    break loop172;
                                }
                            } while (true);


                            }
                            break;

                    }


                    }
                    break;

            }

            ON478=(Token)match(input,ON,FOLLOW_ON_in_create_trigger_stmt3991); 
            ON478_tree = (Object)adaptor.create(ON478);
            adaptor.addChild(root_0, ON478_tree);

            pushFollow(FOLLOW_id_in_create_trigger_stmt3995);
            table_name=id();

            state._fsp--;

            adaptor.addChild(root_0, table_name.getTree());
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:455:20: ( FOR EACH ROW )?
            int alt175=2;
            int LA175_0 = input.LA(1);

            if ( (LA175_0==FOR) ) {
                alt175=1;
            }
            switch (alt175) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:455:21: FOR EACH ROW
                    {
                    FOR479=(Token)match(input,FOR,FOLLOW_FOR_in_create_trigger_stmt3998); 
                    FOR479_tree = (Object)adaptor.create(FOR479);
                    adaptor.addChild(root_0, FOR479_tree);

                    EACH480=(Token)match(input,EACH,FOLLOW_EACH_in_create_trigger_stmt4000); 
                    EACH480_tree = (Object)adaptor.create(EACH480);
                    adaptor.addChild(root_0, EACH480_tree);

                    ROW481=(Token)match(input,ROW,FOLLOW_ROW_in_create_trigger_stmt4002); 
                    ROW481_tree = (Object)adaptor.create(ROW481);
                    adaptor.addChild(root_0, ROW481_tree);


                    }
                    break;

            }

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:455:36: ( WHEN expr )?
            int alt176=2;
            int LA176_0 = input.LA(1);

            if ( (LA176_0==WHEN) ) {
                alt176=1;
            }
            switch (alt176) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:455:37: WHEN expr
                    {
                    WHEN482=(Token)match(input,WHEN,FOLLOW_WHEN_in_create_trigger_stmt4007); 
                    WHEN482_tree = (Object)adaptor.create(WHEN482);
                    adaptor.addChild(root_0, WHEN482_tree);

                    pushFollow(FOLLOW_expr_in_create_trigger_stmt4009);
                    expr483=expr();

                    state._fsp--;

                    adaptor.addChild(root_0, expr483.getTree());

                    }
                    break;

            }

            BEGIN484=(Token)match(input,BEGIN,FOLLOW_BEGIN_in_create_trigger_stmt4015); 
            BEGIN484_tree = (Object)adaptor.create(BEGIN484);
            adaptor.addChild(root_0, BEGIN484_tree);

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:456:9: ( ( update_stmt | insert_stmt | delete_stmt | select_stmt ) SEMI )+
            int cnt178=0;
            loop178:
            do {
                int alt178=2;
                int LA178_0 = input.LA(1);

                if ( (LA178_0==REPLACE||LA178_0==SELECT||LA178_0==INSERT||LA178_0==UPDATE||LA178_0==DELETE) ) {
                    alt178=1;
                }


                switch (alt178) {
            	case 1 :
            	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:456:10: ( update_stmt | insert_stmt | delete_stmt | select_stmt ) SEMI
            	    {
            	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:456:10: ( update_stmt | insert_stmt | delete_stmt | select_stmt )
            	    int alt177=4;
            	    switch ( input.LA(1) ) {
            	    case UPDATE:
            	        {
            	        alt177=1;
            	        }
            	        break;
            	    case REPLACE:
            	    case INSERT:
            	        {
            	        alt177=2;
            	        }
            	        break;
            	    case DELETE:
            	        {
            	        alt177=3;
            	        }
            	        break;
            	    case SELECT:
            	        {
            	        alt177=4;
            	        }
            	        break;
            	    default:
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 177, 0, input);

            	        throw nvae;
            	    }

            	    switch (alt177) {
            	        case 1 :
            	            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:456:11: update_stmt
            	            {
            	            pushFollow(FOLLOW_update_stmt_in_create_trigger_stmt4019);
            	            update_stmt485=update_stmt();

            	            state._fsp--;

            	            adaptor.addChild(root_0, update_stmt485.getTree());

            	            }
            	            break;
            	        case 2 :
            	            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:456:25: insert_stmt
            	            {
            	            pushFollow(FOLLOW_insert_stmt_in_create_trigger_stmt4023);
            	            insert_stmt486=insert_stmt();

            	            state._fsp--;

            	            adaptor.addChild(root_0, insert_stmt486.getTree());

            	            }
            	            break;
            	        case 3 :
            	            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:456:39: delete_stmt
            	            {
            	            pushFollow(FOLLOW_delete_stmt_in_create_trigger_stmt4027);
            	            delete_stmt487=delete_stmt();

            	            state._fsp--;

            	            adaptor.addChild(root_0, delete_stmt487.getTree());

            	            }
            	            break;
            	        case 4 :
            	            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:456:53: select_stmt
            	            {
            	            pushFollow(FOLLOW_select_stmt_in_create_trigger_stmt4031);
            	            select_stmt488=select_stmt();

            	            state._fsp--;

            	            adaptor.addChild(root_0, select_stmt488.getTree());

            	            }
            	            break;

            	    }

            	    SEMI489=(Token)match(input,SEMI,FOLLOW_SEMI_in_create_trigger_stmt4034); 
            	    SEMI489_tree = (Object)adaptor.create(SEMI489);
            	    adaptor.addChild(root_0, SEMI489_tree);


            	    }
            	    break;

            	default :
            	    if ( cnt178 >= 1 ) break loop178;
                        EarlyExitException eee =
                            new EarlyExitException(178, input);
                        throw eee;
                }
                cnt178++;
            } while (true);

            END490=(Token)match(input,END,FOLLOW_END_in_create_trigger_stmt4038); 
            END490_tree = (Object)adaptor.create(END490);
            adaptor.addChild(root_0, END490_tree);


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "create_trigger_stmt"

    public static class drop_trigger_stmt_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "drop_trigger_stmt"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:459:1: drop_trigger_stmt : DROP TRIGGER ( IF EXISTS )? (database_name= id DOT )? trigger_name= id ;
    public final SqlParser.drop_trigger_stmt_return drop_trigger_stmt() throws RecognitionException {
        SqlParser.drop_trigger_stmt_return retval = new SqlParser.drop_trigger_stmt_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token DROP491=null;
        Token TRIGGER492=null;
        Token IF493=null;
        Token EXISTS494=null;
        Token DOT495=null;
        SqlParser.id_return database_name = null;

        SqlParser.id_return trigger_name = null;


        Object DROP491_tree=null;
        Object TRIGGER492_tree=null;
        Object IF493_tree=null;
        Object EXISTS494_tree=null;
        Object DOT495_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:459:18: ( DROP TRIGGER ( IF EXISTS )? (database_name= id DOT )? trigger_name= id )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:459:20: DROP TRIGGER ( IF EXISTS )? (database_name= id DOT )? trigger_name= id
            {
            root_0 = (Object)adaptor.nil();

            DROP491=(Token)match(input,DROP,FOLLOW_DROP_in_drop_trigger_stmt4046); 
            DROP491_tree = (Object)adaptor.create(DROP491);
            adaptor.addChild(root_0, DROP491_tree);

            TRIGGER492=(Token)match(input,TRIGGER,FOLLOW_TRIGGER_in_drop_trigger_stmt4048); 
            TRIGGER492_tree = (Object)adaptor.create(TRIGGER492);
            adaptor.addChild(root_0, TRIGGER492_tree);

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:459:33: ( IF EXISTS )?
            int alt179=2;
            int LA179_0 = input.LA(1);

            if ( (LA179_0==IF) ) {
                int LA179_1 = input.LA(2);

                if ( (LA179_1==EXISTS) ) {
                    alt179=1;
                }
            }
            switch (alt179) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:459:34: IF EXISTS
                    {
                    IF493=(Token)match(input,IF,FOLLOW_IF_in_drop_trigger_stmt4051); 
                    IF493_tree = (Object)adaptor.create(IF493);
                    adaptor.addChild(root_0, IF493_tree);

                    EXISTS494=(Token)match(input,EXISTS,FOLLOW_EXISTS_in_drop_trigger_stmt4053); 
                    EXISTS494_tree = (Object)adaptor.create(EXISTS494);
                    adaptor.addChild(root_0, EXISTS494_tree);


                    }
                    break;

            }

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:459:46: (database_name= id DOT )?
            int alt180=2;
            int LA180_0 = input.LA(1);

            if ( (LA180_0==ID) ) {
                int LA180_1 = input.LA(2);

                if ( (LA180_1==DOT) ) {
                    alt180=1;
                }
            }
            else if ( ((LA180_0>=EXPLAIN && LA180_0<=PLAN)||(LA180_0>=INDEXED && LA180_0<=BY)||(LA180_0>=OR && LA180_0<=ESCAPE)||(LA180_0>=IS && LA180_0<=BETWEEN)||LA180_0==COLLATE||(LA180_0>=DISTINCT && LA180_0<=THEN)||(LA180_0>=CURRENT_TIME && LA180_0<=CURRENT_TIMESTAMP)||(LA180_0>=RAISE && LA180_0<=FAIL)||(LA180_0>=PRAGMA && LA180_0<=FOR)||(LA180_0>=UNIQUE && LA180_0<=ALTER)||(LA180_0>=RENAME && LA180_0<=SCHEMA)) ) {
                int LA180_2 = input.LA(2);

                if ( (LA180_2==DOT) ) {
                    alt180=1;
                }
            }
            switch (alt180) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:459:47: database_name= id DOT
                    {
                    pushFollow(FOLLOW_id_in_drop_trigger_stmt4060);
                    database_name=id();

                    state._fsp--;

                    adaptor.addChild(root_0, database_name.getTree());
                    DOT495=(Token)match(input,DOT,FOLLOW_DOT_in_drop_trigger_stmt4062); 
                    DOT495_tree = (Object)adaptor.create(DOT495);
                    adaptor.addChild(root_0, DOT495_tree);


                    }
                    break;

            }

            pushFollow(FOLLOW_id_in_drop_trigger_stmt4068);
            trigger_name=id();

            state._fsp--;

            adaptor.addChild(root_0, trigger_name.getTree());

            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "drop_trigger_stmt"

    public static class table_comment_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "table_comment"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:462:1: table_comment : META_COMMENT ON TABLE (database_name= id DOT )? name= id IS text= STRING ;
    public final SqlParser.table_comment_return table_comment() throws RecognitionException {
        SqlParser.table_comment_return retval = new SqlParser.table_comment_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token text=null;
        Token META_COMMENT496=null;
        Token ON497=null;
        Token TABLE498=null;
        Token DOT499=null;
        Token IS500=null;
        SqlParser.id_return database_name = null;

        SqlParser.id_return name = null;


        Object text_tree=null;
        Object META_COMMENT496_tree=null;
        Object ON497_tree=null;
        Object TABLE498_tree=null;
        Object DOT499_tree=null;
        Object IS500_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:462:14: ( META_COMMENT ON TABLE (database_name= id DOT )? name= id IS text= STRING )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:462:16: META_COMMENT ON TABLE (database_name= id DOT )? name= id IS text= STRING
            {
            root_0 = (Object)adaptor.nil();

            META_COMMENT496=(Token)match(input,META_COMMENT,FOLLOW_META_COMMENT_in_table_comment4076); 
            META_COMMENT496_tree = (Object)adaptor.create(META_COMMENT496);
            adaptor.addChild(root_0, META_COMMENT496_tree);

            ON497=(Token)match(input,ON,FOLLOW_ON_in_table_comment4078); 
            ON497_tree = (Object)adaptor.create(ON497);
            adaptor.addChild(root_0, ON497_tree);

            TABLE498=(Token)match(input,TABLE,FOLLOW_TABLE_in_table_comment4080); 
            TABLE498_tree = (Object)adaptor.create(TABLE498);
            adaptor.addChild(root_0, TABLE498_tree);

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:462:38: (database_name= id DOT )?
            int alt181=2;
            int LA181_0 = input.LA(1);

            if ( (LA181_0==ID) ) {
                int LA181_1 = input.LA(2);

                if ( (LA181_1==DOT) ) {
                    alt181=1;
                }
            }
            else if ( ((LA181_0>=EXPLAIN && LA181_0<=PLAN)||(LA181_0>=INDEXED && LA181_0<=BY)||(LA181_0>=OR && LA181_0<=ESCAPE)||(LA181_0>=IS && LA181_0<=BETWEEN)||LA181_0==COLLATE||(LA181_0>=DISTINCT && LA181_0<=THEN)||(LA181_0>=CURRENT_TIME && LA181_0<=CURRENT_TIMESTAMP)||(LA181_0>=RAISE && LA181_0<=FAIL)||(LA181_0>=PRAGMA && LA181_0<=FOR)||(LA181_0>=UNIQUE && LA181_0<=ALTER)||(LA181_0>=RENAME && LA181_0<=SCHEMA)) ) {
                int LA181_2 = input.LA(2);

                if ( (LA181_2==DOT) ) {
                    alt181=1;
                }
            }
            switch (alt181) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:462:39: database_name= id DOT
                    {
                    pushFollow(FOLLOW_id_in_table_comment4085);
                    database_name=id();

                    state._fsp--;

                    adaptor.addChild(root_0, database_name.getTree());
                    DOT499=(Token)match(input,DOT,FOLLOW_DOT_in_table_comment4087); 
                    DOT499_tree = (Object)adaptor.create(DOT499);
                    adaptor.addChild(root_0, DOT499_tree);


                    }
                    break;

            }

            pushFollow(FOLLOW_id_in_table_comment4093);
            name=id();

            state._fsp--;

            adaptor.addChild(root_0, name.getTree());
            IS500=(Token)match(input,IS,FOLLOW_IS_in_table_comment4095); 
            IS500_tree = (Object)adaptor.create(IS500);
            adaptor.addChild(root_0, IS500_tree);

            text=(Token)match(input,STRING,FOLLOW_STRING_in_table_comment4099); 
            text_tree = (Object)adaptor.create(text);
            adaptor.addChild(root_0, text_tree);

             builder.getEntity(name.getTree().toString()).setDescription(text.getText()); 

            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "table_comment"

    public static class col_comment_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "col_comment"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:464:1: col_comment : META_COMMENT ON COLUMN (database_name= id DOT )? name= id IS text= STRING ;
    public final SqlParser.col_comment_return col_comment() throws RecognitionException {
        SqlParser.col_comment_return retval = new SqlParser.col_comment_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token text=null;
        Token META_COMMENT501=null;
        Token ON502=null;
        Token COLUMN503=null;
        Token DOT504=null;
        Token IS505=null;
        SqlParser.id_return database_name = null;

        SqlParser.id_return name = null;


        Object text_tree=null;
        Object META_COMMENT501_tree=null;
        Object ON502_tree=null;
        Object COLUMN503_tree=null;
        Object DOT504_tree=null;
        Object IS505_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:464:12: ( META_COMMENT ON COLUMN (database_name= id DOT )? name= id IS text= STRING )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:464:14: META_COMMENT ON COLUMN (database_name= id DOT )? name= id IS text= STRING
            {
            root_0 = (Object)adaptor.nil();

            META_COMMENT501=(Token)match(input,META_COMMENT,FOLLOW_META_COMMENT_in_col_comment4108); 
            META_COMMENT501_tree = (Object)adaptor.create(META_COMMENT501);
            adaptor.addChild(root_0, META_COMMENT501_tree);

            ON502=(Token)match(input,ON,FOLLOW_ON_in_col_comment4110); 
            ON502_tree = (Object)adaptor.create(ON502);
            adaptor.addChild(root_0, ON502_tree);

            COLUMN503=(Token)match(input,COLUMN,FOLLOW_COLUMN_in_col_comment4112); 
            COLUMN503_tree = (Object)adaptor.create(COLUMN503);
            adaptor.addChild(root_0, COLUMN503_tree);

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:464:37: (database_name= id DOT )?
            int alt182=2;
            int LA182_0 = input.LA(1);

            if ( (LA182_0==ID) ) {
                int LA182_1 = input.LA(2);

                if ( (LA182_1==DOT) ) {
                    alt182=1;
                }
            }
            else if ( ((LA182_0>=EXPLAIN && LA182_0<=PLAN)||(LA182_0>=INDEXED && LA182_0<=BY)||(LA182_0>=OR && LA182_0<=ESCAPE)||(LA182_0>=IS && LA182_0<=BETWEEN)||LA182_0==COLLATE||(LA182_0>=DISTINCT && LA182_0<=THEN)||(LA182_0>=CURRENT_TIME && LA182_0<=CURRENT_TIMESTAMP)||(LA182_0>=RAISE && LA182_0<=FAIL)||(LA182_0>=PRAGMA && LA182_0<=FOR)||(LA182_0>=UNIQUE && LA182_0<=ALTER)||(LA182_0>=RENAME && LA182_0<=SCHEMA)) ) {
                int LA182_2 = input.LA(2);

                if ( (LA182_2==DOT) ) {
                    alt182=1;
                }
            }
            switch (alt182) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:464:38: database_name= id DOT
                    {
                    pushFollow(FOLLOW_id_in_col_comment4117);
                    database_name=id();

                    state._fsp--;

                    adaptor.addChild(root_0, database_name.getTree());
                    DOT504=(Token)match(input,DOT,FOLLOW_DOT_in_col_comment4119); 
                    DOT504_tree = (Object)adaptor.create(DOT504);
                    adaptor.addChild(root_0, DOT504_tree);


                    }
                    break;

            }

            pushFollow(FOLLOW_id_in_col_comment4125);
            name=id();

            state._fsp--;

            adaptor.addChild(root_0, name.getTree());
            IS505=(Token)match(input,IS,FOLLOW_IS_in_col_comment4127); 
            IS505_tree = (Object)adaptor.create(IS505);
            adaptor.addChild(root_0, IS505_tree);

            text=(Token)match(input,STRING,FOLLOW_STRING_in_col_comment4131); 
            text_tree = (Object)adaptor.create(text);
            adaptor.addChild(root_0, text_tree);

             builder.getAttribute(name.getTree().toString()).setDescription(text.getText());  

            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "col_comment"

    public static class id_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "id"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:467:1: id : ( ID | keyword );
    public final SqlParser.id_return id() throws RecognitionException {
        SqlParser.id_return retval = new SqlParser.id_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID506=null;
        SqlParser.keyword_return keyword507 = null;


        Object ID506_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:467:3: ( ID | keyword )
            int alt183=2;
            int LA183_0 = input.LA(1);

            if ( (LA183_0==ID) ) {
                alt183=1;
            }
            else if ( ((LA183_0>=EXPLAIN && LA183_0<=PLAN)||(LA183_0>=INDEXED && LA183_0<=BY)||(LA183_0>=OR && LA183_0<=ESCAPE)||(LA183_0>=IS && LA183_0<=BETWEEN)||LA183_0==COLLATE||(LA183_0>=DISTINCT && LA183_0<=THEN)||(LA183_0>=CURRENT_TIME && LA183_0<=CURRENT_TIMESTAMP)||(LA183_0>=RAISE && LA183_0<=FAIL)||(LA183_0>=PRAGMA && LA183_0<=FOR)||(LA183_0>=UNIQUE && LA183_0<=ALTER)||(LA183_0>=RENAME && LA183_0<=SCHEMA)) ) {
                alt183=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 183, 0, input);

                throw nvae;
            }
            switch (alt183) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:467:6: ID
                    {
                    root_0 = (Object)adaptor.nil();

                    ID506=(Token)match(input,ID,FOLLOW_ID_in_id4142); 
                    ID506_tree = (Object)adaptor.create(ID506);
                    adaptor.addChild(root_0, ID506_tree);


                    }
                    break;
                case 2 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:467:11: keyword
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_keyword_in_id4146);
                    keyword507=keyword();

                    state._fsp--;

                    adaptor.addChild(root_0, keyword507.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "id"

    public static class keyword_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "keyword"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:469:1: keyword : ( ABORT | ADD | AFTER | ALL | ALTER | ANALYZE | AND | AS | ASC | ATTACH | AUTOINCREMENT | BEFORE | BEGIN | BETWEEN | BY | CASCADE | CASE | CAST | CHECK | COLLATE | COLUMN | COMMIT | CONFLICT | CONSTRAINT | CREATE | CROSS | CURRENT_TIME | CURRENT_DATE | CURRENT_TIMESTAMP | DATABASE | DEFAULT | DEFERRABLE | DEFERRED | DELETE | DESC | DETACH | DISTINCT | DROP | EACH | ELSE | END | ESCAPE | EXCEPT | EXCLUSIVE | EXISTS | EXPLAIN | FAIL | FOR | FOREIGN | FROM | GROUP | HAVING | IF | IGNORE | IMMEDIATE | INDEX | INDEXED | INITIALLY | INNER | INSERT | INSTEAD | INTERSECT | INTO | IS | JOIN | KEY | LEFT | LIMIT | META_COMMENT | NATURAL | NULL | OF | OFFSET | ON | OR | ORDER | OUTER | PLAN | PRAGMA | PRIMARY | QUERY | RAISE | REFERENCES | REINDEX | RELEASE | RENAME | REPLACE | RESTRICT | ROLLBACK | ROW | SAVEPOINT | SCHEMA | SELECT | SET | TABLE | TEMPORARY | THEN | TO | TRANSACTION | TRIGGER | UNION | UNIQUE | UPDATE | USING | VACUUM | VALUES | VIEW | VIRTUAL | WHEN | WHERE ) ;
    public final SqlParser.keyword_return keyword() throws RecognitionException {
        SqlParser.keyword_return retval = new SqlParser.keyword_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set508=null;

        Object set508_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:469:8: ( ( ABORT | ADD | AFTER | ALL | ALTER | ANALYZE | AND | AS | ASC | ATTACH | AUTOINCREMENT | BEFORE | BEGIN | BETWEEN | BY | CASCADE | CASE | CAST | CHECK | COLLATE | COLUMN | COMMIT | CONFLICT | CONSTRAINT | CREATE | CROSS | CURRENT_TIME | CURRENT_DATE | CURRENT_TIMESTAMP | DATABASE | DEFAULT | DEFERRABLE | DEFERRED | DELETE | DESC | DETACH | DISTINCT | DROP | EACH | ELSE | END | ESCAPE | EXCEPT | EXCLUSIVE | EXISTS | EXPLAIN | FAIL | FOR | FOREIGN | FROM | GROUP | HAVING | IF | IGNORE | IMMEDIATE | INDEX | INDEXED | INITIALLY | INNER | INSERT | INSTEAD | INTERSECT | INTO | IS | JOIN | KEY | LEFT | LIMIT | META_COMMENT | NATURAL | NULL | OF | OFFSET | ON | OR | ORDER | OUTER | PLAN | PRAGMA | PRIMARY | QUERY | RAISE | REFERENCES | REINDEX | RELEASE | RENAME | REPLACE | RESTRICT | ROLLBACK | ROW | SAVEPOINT | SCHEMA | SELECT | SET | TABLE | TEMPORARY | THEN | TO | TRANSACTION | TRIGGER | UNION | UNIQUE | UPDATE | USING | VACUUM | VALUES | VIEW | VIRTUAL | WHEN | WHERE ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:469:10: ( ABORT | ADD | AFTER | ALL | ALTER | ANALYZE | AND | AS | ASC | ATTACH | AUTOINCREMENT | BEFORE | BEGIN | BETWEEN | BY | CASCADE | CASE | CAST | CHECK | COLLATE | COLUMN | COMMIT | CONFLICT | CONSTRAINT | CREATE | CROSS | CURRENT_TIME | CURRENT_DATE | CURRENT_TIMESTAMP | DATABASE | DEFAULT | DEFERRABLE | DEFERRED | DELETE | DESC | DETACH | DISTINCT | DROP | EACH | ELSE | END | ESCAPE | EXCEPT | EXCLUSIVE | EXISTS | EXPLAIN | FAIL | FOR | FOREIGN | FROM | GROUP | HAVING | IF | IGNORE | IMMEDIATE | INDEX | INDEXED | INITIALLY | INNER | INSERT | INSTEAD | INTERSECT | INTO | IS | JOIN | KEY | LEFT | LIMIT | META_COMMENT | NATURAL | NULL | OF | OFFSET | ON | OR | ORDER | OUTER | PLAN | PRAGMA | PRIMARY | QUERY | RAISE | REFERENCES | REINDEX | RELEASE | RENAME | REPLACE | RESTRICT | ROLLBACK | ROW | SAVEPOINT | SCHEMA | SELECT | SET | TABLE | TEMPORARY | THEN | TO | TRANSACTION | TRIGGER | UNION | UNIQUE | UPDATE | USING | VACUUM | VALUES | VIEW | VIRTUAL | WHEN | WHERE )
            {
            root_0 = (Object)adaptor.nil();

            set508=(Token)input.LT(1);
            if ( (input.LA(1)>=EXPLAIN && input.LA(1)<=PLAN)||(input.LA(1)>=INDEXED && input.LA(1)<=BY)||(input.LA(1)>=OR && input.LA(1)<=ESCAPE)||(input.LA(1)>=IS && input.LA(1)<=BETWEEN)||input.LA(1)==COLLATE||(input.LA(1)>=DISTINCT && input.LA(1)<=THEN)||(input.LA(1)>=CURRENT_TIME && input.LA(1)<=CURRENT_TIMESTAMP)||(input.LA(1)>=RAISE && input.LA(1)<=FAIL)||(input.LA(1)>=PRAGMA && input.LA(1)<=FOR)||(input.LA(1)>=UNIQUE && input.LA(1)<=ALTER)||(input.LA(1)>=RENAME && input.LA(1)<=SCHEMA) ) {
                input.consume();
                adaptor.addChild(root_0, (Object)adaptor.create(set508));
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "keyword"

    public static class id_column_def_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "id_column_def"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:590:1: id_column_def : ( ID | keyword_column_def );
    public final SqlParser.id_column_def_return id_column_def() throws RecognitionException {
        SqlParser.id_column_def_return retval = new SqlParser.id_column_def_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID509=null;
        SqlParser.keyword_column_def_return keyword_column_def510 = null;


        Object ID509_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:590:14: ( ID | keyword_column_def )
            int alt184=2;
            int LA184_0 = input.LA(1);

            if ( (LA184_0==ID) ) {
                alt184=1;
            }
            else if ( ((LA184_0>=EXPLAIN && LA184_0<=PLAN)||(LA184_0>=INDEXED && LA184_0<=IN)||(LA184_0>=ISNULL && LA184_0<=BETWEEN)||(LA184_0>=LIKE && LA184_0<=MATCH)||LA184_0==COLLATE||(LA184_0>=DISTINCT && LA184_0<=THEN)||(LA184_0>=CURRENT_TIME && LA184_0<=CURRENT_TIMESTAMP)||(LA184_0>=RAISE && LA184_0<=FAIL)||(LA184_0>=PRAGMA && LA184_0<=EXISTS)||(LA184_0>=PRIMARY && LA184_0<=FOR)||(LA184_0>=UNIQUE && LA184_0<=ALTER)||(LA184_0>=RENAME && LA184_0<=ADD)||(LA184_0>=VIEW && LA184_0<=SCHEMA)) ) {
                alt184=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 184, 0, input);

                throw nvae;
            }
            switch (alt184) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:590:16: ID
                    {
                    root_0 = (Object)adaptor.nil();

                    ID509=(Token)match(input,ID,FOLLOW_ID_in_id_column_def4832); 
                    ID509_tree = (Object)adaptor.create(ID509);
                    adaptor.addChild(root_0, ID509_tree);


                    }
                    break;
                case 2 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:590:21: keyword_column_def
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_keyword_column_def_in_id_column_def4836);
                    keyword_column_def510=keyword_column_def();

                    state._fsp--;

                    adaptor.addChild(root_0, keyword_column_def510.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "id_column_def"

    public static class keyword_column_def_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "keyword_column_def"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:592:1: keyword_column_def : ( ABORT | ADD | AFTER | ALL | ALTER | ANALYZE | AND | AS | ASC | ATTACH | AUTOINCREMENT | BEFORE | BEGIN | BETWEEN | BY | CASCADE | CASE | CAST | CHECK | COLLATE | COMMIT | CONFLICT | CREATE | CROSS | CURRENT_TIME | CURRENT_DATE | CURRENT_TIMESTAMP | DATABASE | DEFAULT | DEFERRABLE | DEFERRED | DELETE | DESC | DETACH | DISTINCT | DROP | EACH | ELSE | END | ESCAPE | EXCEPT | EXCLUSIVE | EXISTS | EXPLAIN | FAIL | FOR | FOREIGN | FROM | GLOB | GROUP | HAVING | IF | IGNORE | IMMEDIATE | IN | INDEX | INDEXED | INITIALLY | INNER | INSERT | INSTEAD | INTERSECT | INTO | IS | ISNULL | JOIN | KEY | LEFT | LIKE | LIMIT | MATCH | META_COMMENT | NATURAL | NOT | NOTNULL | NULL | OF | OFFSET | ON | OR | ORDER | OUTER | PLAN | PRAGMA | PRIMARY | QUERY | RAISE | REFERENCES | REGEXP | REINDEX | RELEASE | RENAME | REPLACE | RESTRICT | ROLLBACK | ROW | SAVEPOINT | SCHEMA | SELECT | SET | TABLE | TEMPORARY | THEN | TO | TRANSACTION | TRIGGER | UNION | UNIQUE | UPDATE | USING | VACUUM | VALUES | VIEW | VIRTUAL | WHEN | WHERE ) ;
    public final SqlParser.keyword_column_def_return keyword_column_def() throws RecognitionException {
        SqlParser.keyword_column_def_return retval = new SqlParser.keyword_column_def_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set511=null;

        Object set511_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:592:19: ( ( ABORT | ADD | AFTER | ALL | ALTER | ANALYZE | AND | AS | ASC | ATTACH | AUTOINCREMENT | BEFORE | BEGIN | BETWEEN | BY | CASCADE | CASE | CAST | CHECK | COLLATE | COMMIT | CONFLICT | CREATE | CROSS | CURRENT_TIME | CURRENT_DATE | CURRENT_TIMESTAMP | DATABASE | DEFAULT | DEFERRABLE | DEFERRED | DELETE | DESC | DETACH | DISTINCT | DROP | EACH | ELSE | END | ESCAPE | EXCEPT | EXCLUSIVE | EXISTS | EXPLAIN | FAIL | FOR | FOREIGN | FROM | GLOB | GROUP | HAVING | IF | IGNORE | IMMEDIATE | IN | INDEX | INDEXED | INITIALLY | INNER | INSERT | INSTEAD | INTERSECT | INTO | IS | ISNULL | JOIN | KEY | LEFT | LIKE | LIMIT | MATCH | META_COMMENT | NATURAL | NOT | NOTNULL | NULL | OF | OFFSET | ON | OR | ORDER | OUTER | PLAN | PRAGMA | PRIMARY | QUERY | RAISE | REFERENCES | REGEXP | REINDEX | RELEASE | RENAME | REPLACE | RESTRICT | ROLLBACK | ROW | SAVEPOINT | SCHEMA | SELECT | SET | TABLE | TEMPORARY | THEN | TO | TRANSACTION | TRIGGER | UNION | UNIQUE | UPDATE | USING | VACUUM | VALUES | VIEW | VIRTUAL | WHEN | WHERE ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:592:21: ( ABORT | ADD | AFTER | ALL | ALTER | ANALYZE | AND | AS | ASC | ATTACH | AUTOINCREMENT | BEFORE | BEGIN | BETWEEN | BY | CASCADE | CASE | CAST | CHECK | COLLATE | COMMIT | CONFLICT | CREATE | CROSS | CURRENT_TIME | CURRENT_DATE | CURRENT_TIMESTAMP | DATABASE | DEFAULT | DEFERRABLE | DEFERRED | DELETE | DESC | DETACH | DISTINCT | DROP | EACH | ELSE | END | ESCAPE | EXCEPT | EXCLUSIVE | EXISTS | EXPLAIN | FAIL | FOR | FOREIGN | FROM | GLOB | GROUP | HAVING | IF | IGNORE | IMMEDIATE | IN | INDEX | INDEXED | INITIALLY | INNER | INSERT | INSTEAD | INTERSECT | INTO | IS | ISNULL | JOIN | KEY | LEFT | LIKE | LIMIT | MATCH | META_COMMENT | NATURAL | NOT | NOTNULL | NULL | OF | OFFSET | ON | OR | ORDER | OUTER | PLAN | PRAGMA | PRIMARY | QUERY | RAISE | REFERENCES | REGEXP | REINDEX | RELEASE | RENAME | REPLACE | RESTRICT | ROLLBACK | ROW | SAVEPOINT | SCHEMA | SELECT | SET | TABLE | TEMPORARY | THEN | TO | TRANSACTION | TRIGGER | UNION | UNIQUE | UPDATE | USING | VACUUM | VALUES | VIEW | VIRTUAL | WHEN | WHERE )
            {
            root_0 = (Object)adaptor.nil();

            set511=(Token)input.LT(1);
            if ( (input.LA(1)>=EXPLAIN && input.LA(1)<=PLAN)||(input.LA(1)>=INDEXED && input.LA(1)<=IN)||(input.LA(1)>=ISNULL && input.LA(1)<=BETWEEN)||(input.LA(1)>=LIKE && input.LA(1)<=MATCH)||input.LA(1)==COLLATE||(input.LA(1)>=DISTINCT && input.LA(1)<=THEN)||(input.LA(1)>=CURRENT_TIME && input.LA(1)<=CURRENT_TIMESTAMP)||(input.LA(1)>=RAISE && input.LA(1)<=FAIL)||(input.LA(1)>=PRAGMA && input.LA(1)<=EXISTS)||(input.LA(1)>=PRIMARY && input.LA(1)<=FOR)||(input.LA(1)>=UNIQUE && input.LA(1)<=ALTER)||(input.LA(1)>=RENAME && input.LA(1)<=ADD)||(input.LA(1)>=VIEW && input.LA(1)<=SCHEMA) ) {
                input.consume();
                adaptor.addChild(root_0, (Object)adaptor.create(set511));
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "keyword_column_def"

    // Delegated rules


    protected DFA1 dfa1 = new DFA1(this);
    protected DFA3 dfa3 = new DFA3(this);
    protected DFA2 dfa2 = new DFA2(this);
    protected DFA4 dfa4 = new DFA4(this);
    protected DFA5 dfa5 = new DFA5(this);
    protected DFA7 dfa7 = new DFA7(this);
    protected DFA8 dfa8 = new DFA8(this);
    protected DFA9 dfa9 = new DFA9(this);
    protected DFA19 dfa19 = new DFA19(this);
    protected DFA11 dfa11 = new DFA11(this);
    protected DFA15 dfa15 = new DFA15(this);
    protected DFA18 dfa18 = new DFA18(this);
    protected DFA20 dfa20 = new DFA20(this);
    protected DFA21 dfa21 = new DFA21(this);
    protected DFA22 dfa22 = new DFA22(this);
    protected DFA23 dfa23 = new DFA23(this);
    protected DFA24 dfa24 = new DFA24(this);
    protected DFA25 dfa25 = new DFA25(this);
    protected DFA26 dfa26 = new DFA26(this);
    protected DFA35 dfa35 = new DFA35(this);
    protected DFA28 dfa28 = new DFA28(this);
    protected DFA27 dfa27 = new DFA27(this);
    protected DFA31 dfa31 = new DFA31(this);
    protected DFA29 dfa29 = new DFA29(this);
    protected DFA32 dfa32 = new DFA32(this);
    protected DFA37 dfa37 = new DFA37(this);
    protected DFA39 dfa39 = new DFA39(this);
    protected DFA42 dfa42 = new DFA42(this);
    protected DFA40 dfa40 = new DFA40(this);
    protected DFA44 dfa44 = new DFA44(this);
    protected DFA47 dfa47 = new DFA47(this);
    protected DFA52 dfa52 = new DFA52(this);
    protected DFA64 dfa64 = new DFA64(this);
    protected DFA65 dfa65 = new DFA65(this);
    protected DFA66 dfa66 = new DFA66(this);
    protected DFA67 dfa67 = new DFA67(this);
    protected DFA68 dfa68 = new DFA68(this);
    protected DFA73 dfa73 = new DFA73(this);
    protected DFA72 dfa72 = new DFA72(this);
    protected DFA71 dfa71 = new DFA71(this);
    protected DFA75 dfa75 = new DFA75(this);
    protected DFA74 dfa74 = new DFA74(this);
    protected DFA82 dfa82 = new DFA82(this);
    protected DFA76 dfa76 = new DFA76(this);
    protected DFA78 dfa78 = new DFA78(this);
    protected DFA79 dfa79 = new DFA79(this);
    protected DFA81 dfa81 = new DFA81(this);
    protected DFA92 dfa92 = new DFA92(this);
    protected DFA117 dfa117 = new DFA117(this);
    protected DFA120 dfa120 = new DFA120(this);
    protected DFA121 dfa121 = new DFA121(this);
    protected DFA122 dfa122 = new DFA122(this);
    protected DFA123 dfa123 = new DFA123(this);
    protected DFA124 dfa124 = new DFA124(this);
    protected DFA125 dfa125 = new DFA125(this);
    protected DFA126 dfa126 = new DFA126(this);
    protected DFA127 dfa127 = new DFA127(this);
    protected DFA128 dfa128 = new DFA128(this);
    protected DFA129 dfa129 = new DFA129(this);
    protected DFA130 dfa130 = new DFA130(this);
    protected DFA132 dfa132 = new DFA132(this);
    protected DFA141 dfa141 = new DFA141(this);
    protected DFA142 dfa142 = new DFA142(this);
    protected DFA143 dfa143 = new DFA143(this);
    protected DFA147 dfa147 = new DFA147(this);
    protected DFA154 dfa154 = new DFA154(this);
    protected DFA169 dfa169 = new DFA169(this);
    protected DFA170 dfa170 = new DFA170(this);
    static final String DFA1_eotS =
        "\27\uffff";
    static final String DFA1_eofS =
        "\1\1\26\uffff";
    static final String DFA1_minS =
        "\1\40\26\uffff";
    static final String DFA1_maxS =
        "\1\u00b8\26\uffff";
    static final String DFA1_acceptS =
        "\1\uffff\1\2\1\1\24\uffff";
    static final String DFA1_specialS =
        "\27\uffff}>";
    static final String[] DFA1_transitionS = {
            "\1\2\61\uffff\1\2\16\uffff\1\2\4\uffff\2\2\1\uffff\5\2\11\uffff"+
            "\1\2\14\uffff\1\2\3\uffff\1\2\1\uffff\2\2\4\uffff\1\2\1\uffff"+
            "\2\2\1\uffff\1\2\23\uffff\2\2\15\uffff\1\2",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA1_eot = DFA.unpackEncodedString(DFA1_eotS);
    static final short[] DFA1_eof = DFA.unpackEncodedString(DFA1_eofS);
    static final char[] DFA1_min = DFA.unpackEncodedStringToUnsignedChars(DFA1_minS);
    static final char[] DFA1_max = DFA.unpackEncodedStringToUnsignedChars(DFA1_maxS);
    static final short[] DFA1_accept = DFA.unpackEncodedString(DFA1_acceptS);
    static final short[] DFA1_special = DFA.unpackEncodedString(DFA1_specialS);
    static final short[][] DFA1_transition;

    static {
        int numStates = DFA1_transitionS.length;
        DFA1_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA1_transition[i] = DFA.unpackEncodedString(DFA1_transitionS[i]);
        }
    }

    class DFA1 extends DFA {

        public DFA1(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 1;
            this.eot = DFA1_eot;
            this.eof = DFA1_eof;
            this.min = DFA1_min;
            this.max = DFA1_max;
            this.accept = DFA1_accept;
            this.special = DFA1_special;
            this.transition = DFA1_transition;
        }
        public String getDescription() {
            return "()+ loopback of 98:16: ( sql_stmt )+";
        }
    }
    static final String DFA3_eotS =
        "\26\uffff";
    static final String DFA3_eofS =
        "\26\uffff";
    static final String DFA3_minS =
        "\1\40\25\uffff";
    static final String DFA3_maxS =
        "\1\u00b8\25\uffff";
    static final String DFA3_acceptS =
        "\1\uffff\1\1\1\2\23\uffff";
    static final String DFA3_specialS =
        "\26\uffff}>";
    static final String[] DFA3_transitionS = {
            "\1\1\61\uffff\1\2\16\uffff\1\2\4\uffff\2\2\1\uffff\5\2\11\uffff"+
            "\1\2\14\uffff\1\2\3\uffff\1\2\1\uffff\2\2\4\uffff\1\2\1\uffff"+
            "\2\2\1\uffff\1\2\23\uffff\2\2\15\uffff\1\2",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA3_eot = DFA.unpackEncodedString(DFA3_eotS);
    static final short[] DFA3_eof = DFA.unpackEncodedString(DFA3_eofS);
    static final char[] DFA3_min = DFA.unpackEncodedStringToUnsignedChars(DFA3_minS);
    static final char[] DFA3_max = DFA.unpackEncodedStringToUnsignedChars(DFA3_maxS);
    static final short[] DFA3_accept = DFA.unpackEncodedString(DFA3_acceptS);
    static final short[] DFA3_special = DFA.unpackEncodedString(DFA3_specialS);
    static final short[][] DFA3_transition;

    static {
        int numStates = DFA3_transitionS.length;
        DFA3_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA3_transition[i] = DFA.unpackEncodedString(DFA3_transitionS[i]);
        }
    }

    class DFA3 extends DFA {

        public DFA3(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 3;
            this.eot = DFA3_eot;
            this.eof = DFA3_eof;
            this.min = DFA3_min;
            this.max = DFA3_max;
            this.accept = DFA3_accept;
            this.special = DFA3_special;
            this.transition = DFA3_transition;
        }
        public String getDescription() {
            return "100:11: ( EXPLAIN ( QUERY PLAN )? )?";
        }
    }
    static final String DFA2_eotS =
        "\26\uffff";
    static final String DFA2_eofS =
        "\26\uffff";
    static final String DFA2_minS =
        "\1\41\25\uffff";
    static final String DFA2_maxS =
        "\1\u00b8\25\uffff";
    static final String DFA2_acceptS =
        "\1\uffff\1\1\1\2\23\uffff";
    static final String DFA2_specialS =
        "\26\uffff}>";
    static final String[] DFA2_transitionS = {
            "\1\1\60\uffff\1\2\16\uffff\1\2\4\uffff\2\2\1\uffff\5\2\11\uffff"+
            "\1\2\14\uffff\1\2\3\uffff\1\2\1\uffff\2\2\4\uffff\1\2\1\uffff"+
            "\2\2\1\uffff\1\2\23\uffff\2\2\15\uffff\1\2",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA2_eot = DFA.unpackEncodedString(DFA2_eotS);
    static final short[] DFA2_eof = DFA.unpackEncodedString(DFA2_eofS);
    static final char[] DFA2_min = DFA.unpackEncodedStringToUnsignedChars(DFA2_minS);
    static final char[] DFA2_max = DFA.unpackEncodedStringToUnsignedChars(DFA2_maxS);
    static final short[] DFA2_accept = DFA.unpackEncodedString(DFA2_acceptS);
    static final short[] DFA2_special = DFA.unpackEncodedString(DFA2_specialS);
    static final short[][] DFA2_transition;

    static {
        int numStates = DFA2_transitionS.length;
        DFA2_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA2_transition[i] = DFA.unpackEncodedString(DFA2_transitionS[i]);
        }
    }

    class DFA2 extends DFA {

        public DFA2(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 2;
            this.eot = DFA2_eot;
            this.eof = DFA2_eof;
            this.min = DFA2_min;
            this.max = DFA2_max;
            this.accept = DFA2_accept;
            this.special = DFA2_special;
            this.transition = DFA2_transition;
        }
        public String getDescription() {
            return "100:20: ( QUERY PLAN )?";
        }
    }
    static final String DFA4_eotS =
        "\46\uffff";
    static final String DFA4_eofS =
        "\46\uffff";
    static final String DFA4_minS =
        "\1\122\20\uffff\1\u0096\1\u0097\1\uffff\1\u0082\1\uffff\1\u0097"+
        "\11\uffff\1\u0097\5\uffff";
    static final String DFA4_maxS =
        "\1\u00b8\20\uffff\2\u00b1\1\uffff\1\u0082\1\uffff\1\u00b1\11\uffff"+
        "\1\u00ae\5\uffff";
    static final String DFA4_acceptS =
        "\1\uffff\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\uffff\1\11\1\12\1\13"+
        "\1\14\1\15\1\16\1\17\2\uffff\1\23\1\uffff\1\20\1\uffff\1\24\1\26"+
        "\1\uffff\1\30\1\21\1\22\1\25\1\27\1\31\4\uffff\1\32\1\33";
    static final String DFA4_specialS =
        "\46\uffff}>";
    static final String[] DFA4_transitionS = {
            "\1\15\16\uffff\1\16\4\uffff\1\1\1\2\1\uffff\1\3\1\4\1\5\1\6"+
            "\1\10\11\uffff\1\7\14\uffff\1\10\3\uffff\1\12\1\uffff\1\13\1"+
            "\14\4\uffff\1\15\1\uffff\1\17\1\20\1\uffff\1\21\23\uffff\1\22"+
            "\1\23\15\uffff\1\24",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\25\1\33\1\26\10\uffff\1\30\15\uffff\1\27\1\30\1\32",
            "\1\34\27\uffff\1\35\1\36\1\37",
            "",
            "\1\40",
            "",
            "\1\33\27\uffff\1\27\1\uffff\1\32",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\44\26\uffff\1\45",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA4_eot = DFA.unpackEncodedString(DFA4_eotS);
    static final short[] DFA4_eof = DFA.unpackEncodedString(DFA4_eofS);
    static final char[] DFA4_min = DFA.unpackEncodedStringToUnsignedChars(DFA4_minS);
    static final char[] DFA4_max = DFA.unpackEncodedStringToUnsignedChars(DFA4_maxS);
    static final short[] DFA4_accept = DFA.unpackEncodedString(DFA4_acceptS);
    static final short[] DFA4_special = DFA.unpackEncodedString(DFA4_specialS);
    static final short[][] DFA4_transition;

    static {
        int numStates = DFA4_transitionS.length;
        DFA4_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA4_transition[i] = DFA.unpackEncodedString(DFA4_transitionS[i]);
        }
    }

    class DFA4 extends DFA {

        public DFA4(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 4;
            this.eot = DFA4_eot;
            this.eof = DFA4_eof;
            this.min = DFA4_min;
            this.max = DFA4_max;
            this.accept = DFA4_accept;
            this.special = DFA4_special;
            this.transition = DFA4_transition;
        }
        public String getDescription() {
            return "102:1: sql_stmt_core : ( pragma_stmt | attach_stmt | detach_stmt | analyze_stmt | reindex_stmt | vacuum_stmt | select_stmt | insert_stmt | update_stmt | delete_stmt | begin_stmt | commit_stmt | rollback_stmt | savepoint_stmt | release_stmt | create_virtual_table_stmt | create_table_stmt | drop_table_stmt | alter_table_stmt | create_view_stmt | drop_view_stmt | create_index_stmt | drop_index_stmt | create_trigger_stmt | drop_trigger_stmt | table_comment | col_comment );";
        }
    }
    static final String DFA5_eotS =
        "\23\uffff";
    static final String DFA5_eofS =
        "\23\uffff";
    static final String DFA5_minS =
        "\1\40\2\43\20\uffff";
    static final String DFA5_maxS =
        "\1\u00b9\2\u0089\20\uffff";
    static final String DFA5_acceptS =
        "\3\uffff\1\1\1\2\16\uffff";
    static final String DFA5_specialS =
        "\23\uffff}>";
    static final String[] DFA5_transitionS = {
            "\3\2\2\uffff\2\2\1\uffff\3\2\6\uffff\3\2\27\uffff\1\2\1\1\10"+
            "\2\4\uffff\3\2\3\uffff\5\2\2\uffff\72\2\1\uffff\12\2\1\uffff"+
            "\16\2",
            "\1\4\1\3\1\4\1\uffff\1\4\110\uffff\2\4\7\uffff\1\4\17\uffff"+
            "\1\4",
            "\1\4\1\3\1\4\1\uffff\1\4\110\uffff\2\4\7\uffff\1\4\17\uffff"+
            "\1\4",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA5_eot = DFA.unpackEncodedString(DFA5_eotS);
    static final short[] DFA5_eof = DFA.unpackEncodedString(DFA5_eofS);
    static final char[] DFA5_min = DFA.unpackEncodedStringToUnsignedChars(DFA5_minS);
    static final char[] DFA5_max = DFA.unpackEncodedStringToUnsignedChars(DFA5_maxS);
    static final short[] DFA5_accept = DFA.unpackEncodedString(DFA5_acceptS);
    static final short[] DFA5_special = DFA.unpackEncodedString(DFA5_specialS);
    static final short[][] DFA5_transition;

    static {
        int numStates = DFA5_transitionS.length;
        DFA5_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA5_transition[i] = DFA.unpackEncodedString(DFA5_transitionS[i]);
        }
    }

    class DFA5 extends DFA {

        public DFA5(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 5;
            this.eot = DFA5_eot;
            this.eof = DFA5_eof;
            this.min = DFA5_min;
            this.max = DFA5_max;
            this.accept = DFA5_accept;
            this.special = DFA5_special;
            this.transition = DFA5_transition;
        }
        public String getDescription() {
            return "134:23: (database_name= id DOT )?";
        }
    }
    static final String DFA7_eotS =
        "\137\uffff";
    static final String DFA7_eofS =
        "\137\uffff";
    static final String DFA7_minS =
        "\1\40\33\uffff\1\40\21\uffff\1\40\2\uffff\1\40\6\44\47\uffff";
    static final String DFA7_maxS =
        "\1\u00b9\33\uffff\1\u00b9\21\uffff\1\u00b9\2\uffff\1\u00b9\1\46"+
        "\3\167\1\46\1\125\47\uffff";
    static final String DFA7_acceptS =
        "\1\uffff\1\2\34\uffff\1\1\100\uffff";
    static final String DFA7_specialS =
        "\137\uffff}>";
    static final String[] DFA7_transitionS = {
            "\4\1\1\uffff\2\1\1\uffff\1\34\2\1\2\uffff\2\1\2\uffff\3\1\27"+
            "\uffff\12\1\4\uffff\3\1\3\uffff\5\1\2\uffff\72\1\1\uffff\12"+
            "\1\1\uffff\16\1",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\3\36\1\1\1\uffff\6\36\1\uffff\1\36\2\1\2\uffff\3\36\20\uffff"+
            "\2\36\4\uffff\32\36\2\uffff\12\36\1\66\1\67\1\36\1\63\1\36\1"+
            "\64\1\65\1\36\1\56\1\61\1\62\45\36\1\uffff\12\36\1\uffff\16"+
            "\36",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\3\1\1\uffff\1\36\2\1\1\uffff\3\1\1\uffff\1\1\4\uffff\3\1\27"+
            "\uffff\12\1\4\uffff\3\1\3\uffff\5\1\2\uffff\72\1\1\uffff\12"+
            "\1\1\uffff\16\1",
            "",
            "",
            "\3\1\1\uffff\1\36\6\1\1\uffff\1\1\4\uffff\3\1\20\uffff\2\1"+
            "\4\uffff\32\1\2\uffff\72\1\1\uffff\12\1\1\uffff\16\1",
            "\1\36\1\uffff\1\1",
            "\1\36\117\uffff\1\1\2\uffff\1\1",
            "\1\36\122\uffff\1\1",
            "\1\36\122\uffff\1\1",
            "\1\36\1\uffff\1\1",
            "\1\36\60\uffff\1\1",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA7_eot = DFA.unpackEncodedString(DFA7_eotS);
    static final short[] DFA7_eof = DFA.unpackEncodedString(DFA7_eofS);
    static final char[] DFA7_min = DFA.unpackEncodedStringToUnsignedChars(DFA7_minS);
    static final char[] DFA7_max = DFA.unpackEncodedStringToUnsignedChars(DFA7_maxS);
    static final short[] DFA7_accept = DFA.unpackEncodedString(DFA7_acceptS);
    static final short[] DFA7_special = DFA.unpackEncodedString(DFA7_specialS);
    static final short[][] DFA7_transition;

    static {
        int numStates = DFA7_transitionS.length;
        DFA7_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA7_transition[i] = DFA.unpackEncodedString(DFA7_transitionS[i]);
        }
    }

    class DFA7 extends DFA {

        public DFA7(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 7;
            this.eot = DFA7_eot;
            this.eof = DFA7_eof;
            this.min = DFA7_min;
            this.max = DFA7_max;
            this.accept = DFA7_accept;
            this.special = DFA7_special;
            this.transition = DFA7_transition;
        }
        public String getDescription() {
            return "()* loopback of 136:18: ( OR or_subexpr )*";
        }
    }
    static final String DFA8_eotS =
        "\140\uffff";
    static final String DFA8_eofS =
        "\140\uffff";
    static final String DFA8_minS =
        "\1\40\34\uffff\1\40\21\uffff\1\40\2\uffff\1\40\6\44\47\uffff";
    static final String DFA8_maxS =
        "\1\u00b9\34\uffff\1\u00b9\21\uffff\1\u00b9\2\uffff\1\u00b9\1\46"+
        "\3\167\1\46\1\125\47\uffff";
    static final String DFA8_acceptS =
        "\1\uffff\1\2\35\uffff\1\1\100\uffff";
    static final String DFA8_specialS =
        "\140\uffff}>";
    static final String[] DFA8_transitionS = {
            "\4\1\1\uffff\2\1\1\uffff\1\1\1\35\1\1\2\uffff\2\1\2\uffff\3"+
            "\1\27\uffff\12\1\4\uffff\3\1\3\uffff\5\1\2\uffff\72\1\1\uffff"+
            "\12\1\1\uffff\16\1",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\3\37\1\1\1\uffff\6\37\1\uffff\1\37\2\1\2\uffff\3\37\20\uffff"+
            "\2\37\4\uffff\32\37\2\uffff\12\37\1\67\1\70\1\37\1\64\1\37\1"+
            "\65\1\66\1\37\1\57\1\62\1\63\45\37\1\uffff\12\37\1\uffff\16"+
            "\37",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\3\1\1\uffff\1\37\2\1\1\uffff\3\1\1\uffff\1\1\4\uffff\3\1\27"+
            "\uffff\12\1\4\uffff\3\1\3\uffff\5\1\2\uffff\72\1\1\uffff\12"+
            "\1\1\uffff\16\1",
            "",
            "",
            "\3\1\1\uffff\1\37\6\1\1\uffff\1\1\4\uffff\3\1\20\uffff\2\1"+
            "\4\uffff\32\1\2\uffff\72\1\1\uffff\12\1\1\uffff\16\1",
            "\1\37\1\uffff\1\1",
            "\1\37\117\uffff\1\1\2\uffff\1\1",
            "\1\37\122\uffff\1\1",
            "\1\37\122\uffff\1\1",
            "\1\37\1\uffff\1\1",
            "\1\37\60\uffff\1\1",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA8_eot = DFA.unpackEncodedString(DFA8_eotS);
    static final short[] DFA8_eof = DFA.unpackEncodedString(DFA8_eofS);
    static final char[] DFA8_min = DFA.unpackEncodedStringToUnsignedChars(DFA8_minS);
    static final char[] DFA8_max = DFA.unpackEncodedStringToUnsignedChars(DFA8_maxS);
    static final short[] DFA8_accept = DFA.unpackEncodedString(DFA8_acceptS);
    static final short[] DFA8_special = DFA.unpackEncodedString(DFA8_specialS);
    static final short[][] DFA8_transition;

    static {
        int numStates = DFA8_transitionS.length;
        DFA8_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA8_transition[i] = DFA.unpackEncodedString(DFA8_transitionS[i]);
        }
    }

    class DFA8 extends DFA {

        public DFA8(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 8;
            this.eot = DFA8_eot;
            this.eof = DFA8_eof;
            this.min = DFA8_min;
            this.max = DFA8_max;
            this.accept = DFA8_accept;
            this.special = DFA8_special;
            this.transition = DFA8_transition;
        }
        public String getDescription() {
            return "()* loopback of 138:25: ( AND and_subexpr )*";
        }
    }
    static final String DFA9_eotS =
        "\165\uffff";
    static final String DFA9_eofS =
        "\165\uffff";
    static final String DFA9_minS =
        "\1\40\5\uffff\1\43\1\40\74\uffff\1\40\2\uffff\1\40\6\44\47\uffff";
    static final String DFA9_maxS =
        "\1\u00b9\5\uffff\1\172\1\u00b9\74\uffff\1\u00b9\2\uffff\1\u00b9"+
        "\1\46\3\167\1\46\1\125\47\uffff";
    static final String DFA9_acceptS =
        "\1\uffff\1\1\7\uffff\1\2\153\uffff";
    static final String DFA9_specialS =
        "\165\uffff}>";
    static final String[] DFA9_transitionS = {
            "\4\11\1\uffff\2\11\1\1\3\11\1\1\1\uffff\2\11\2\1\1\6\1\11\1"+
            "\7\10\1\17\uffff\12\11\4\uffff\3\11\3\uffff\5\11\2\uffff\72"+
            "\11\1\uffff\12\11\1\uffff\16\11",
            "",
            "",
            "",
            "",
            "",
            "\1\11\3\uffff\1\1\5\uffff\2\11\3\uffff\1\1\75\uffff\2\11\1"+
            "\uffff\1\11\1\uffff\2\11\1\uffff\3\11",
            "\3\1\1\11\1\uffff\6\1\1\uffff\1\1\2\11\2\uffff\3\1\20\uffff"+
            "\2\1\4\uffff\32\1\2\uffff\12\1\1\114\1\115\1\1\1\111\1\1\1\112"+
            "\1\113\1\1\1\104\1\107\1\110\45\1\1\uffff\12\1\1\uffff\16\1",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\3\11\1\uffff\1\1\2\11\1\uffff\3\11\1\uffff\1\11\4\uffff\3"+
            "\11\27\uffff\12\11\4\uffff\3\11\3\uffff\5\11\2\uffff\72\11\1"+
            "\uffff\12\11\1\uffff\16\11",
            "",
            "",
            "\3\11\1\uffff\1\1\6\11\1\uffff\1\11\4\uffff\3\11\20\uffff\2"+
            "\11\4\uffff\32\11\2\uffff\72\11\1\uffff\12\11\1\uffff\16\11",
            "\1\1\1\uffff\1\11",
            "\1\1\117\uffff\1\11\2\uffff\1\11",
            "\1\1\122\uffff\1\11",
            "\1\1\122\uffff\1\11",
            "\1\1\1\uffff\1\11",
            "\1\1\60\uffff\1\11",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA9_eot = DFA.unpackEncodedString(DFA9_eotS);
    static final short[] DFA9_eof = DFA.unpackEncodedString(DFA9_eofS);
    static final char[] DFA9_min = DFA.unpackEncodedStringToUnsignedChars(DFA9_minS);
    static final char[] DFA9_max = DFA.unpackEncodedStringToUnsignedChars(DFA9_maxS);
    static final short[] DFA9_accept = DFA.unpackEncodedString(DFA9_acceptS);
    static final short[] DFA9_special = DFA.unpackEncodedString(DFA9_specialS);
    static final short[][] DFA9_transition;

    static {
        int numStates = DFA9_transitionS.length;
        DFA9_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA9_transition[i] = DFA.unpackEncodedString(DFA9_transitionS[i]);
        }
    }

    class DFA9 extends DFA {

        public DFA9(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 9;
            this.eot = DFA9_eot;
            this.eof = DFA9_eof;
            this.min = DFA9_min;
            this.max = DFA9_max;
            this.accept = DFA9_accept;
            this.special = DFA9_special;
            this.transition = DFA9_transition;
        }
        public String getDescription() {
            return "140:34: ( cond_expr )?";
        }
    }
    static final String DFA19_eotS =
        "\23\uffff";
    static final String DFA19_eofS =
        "\23\uffff";
    static final String DFA19_minS =
        "\1\47\1\53\1\uffff\1\40\7\uffff\1\40\7\uffff";
    static final String DFA19_maxS =
        "\2\73\1\uffff\1\u00b9\7\uffff\1\u00b9\7\uffff";
    static final String DFA19_acceptS =
        "\2\uffff\1\1\1\uffff\1\4\2\uffff\1\5\1\6\4\uffff\1\2\1\3\4\uffff";
    static final String DFA19_specialS =
        "\23\uffff}>";
    static final String[] DFA19_transitionS = {
            "\1\1\3\uffff\1\3\3\uffff\3\4\1\uffff\1\7\4\10\4\2",
            "\1\13\6\uffff\1\4\1\7\4\uffff\4\2",
            "",
            "\3\16\2\uffff\2\16\1\uffff\3\16\1\uffff\1\15\4\uffff\3\16\27"+
            "\uffff\12\16\4\uffff\3\16\3\uffff\5\16\2\uffff\72\16\1\uffff"+
            "\12\16\1\uffff\16\16",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\3\16\2\uffff\2\16\1\uffff\3\16\1\uffff\1\15\4\uffff\3\16\27"+
            "\uffff\12\16\4\uffff\3\16\3\uffff\5\16\2\uffff\72\16\1\uffff"+
            "\12\16\1\uffff\16\16",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA19_eot = DFA.unpackEncodedString(DFA19_eotS);
    static final short[] DFA19_eof = DFA.unpackEncodedString(DFA19_eofS);
    static final char[] DFA19_min = DFA.unpackEncodedStringToUnsignedChars(DFA19_minS);
    static final char[] DFA19_max = DFA.unpackEncodedStringToUnsignedChars(DFA19_maxS);
    static final short[] DFA19_accept = DFA.unpackEncodedString(DFA19_acceptS);
    static final short[] DFA19_special = DFA.unpackEncodedString(DFA19_specialS);
    static final short[][] DFA19_transition;

    static {
        int numStates = DFA19_transitionS.length;
        DFA19_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA19_transition[i] = DFA.unpackEncodedString(DFA19_transitionS[i]);
        }
    }

    class DFA19 extends DFA {

        public DFA19(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 19;
            this.eot = DFA19_eot;
            this.eof = DFA19_eof;
            this.min = DFA19_min;
            this.max = DFA19_max;
            this.accept = DFA19_accept;
            this.special = DFA19_special;
            this.transition = DFA19_transition;
        }
        public String getDescription() {
            return "142:1: cond_expr : ( ( NOT )? match_op match_expr= eq_subexpr ( ESCAPE escape_expr= eq_subexpr )? -> ^( match_op $match_expr ( NOT )? ( ^( ESCAPE $escape_expr) )? ) | ( NOT )? IN LPAREN expr ( COMMA expr )* RPAREN -> ^( IN_VALUES ( NOT )? ^( IN ( expr )+ ) ) | ( NOT )? IN (database_name= id DOT )? table_name= id -> ^( IN_TABLE ( NOT )? ^( IN ^( $table_name ( $database_name)? ) ) ) | ( ISNULL -> IS_NULL | NOTNULL -> NOT_NULL | IS NULL -> IS_NULL | NOT NULL -> NOT_NULL | IS NOT NULL -> NOT_NULL ) | ( NOT )? BETWEEN e1= eq_subexpr AND e2= eq_subexpr -> ^( BETWEEN ( NOT )? ^( AND $e1 $e2) ) | ( ( EQUALS | EQUALS2 | NOT_EQUALS | NOT_EQUALS2 ) eq_subexpr )+ );";
        }
    }
    static final String DFA11_eotS =
        "\141\uffff";
    static final String DFA11_eofS =
        "\141\uffff";
    static final String DFA11_minS =
        "\2\40\56\uffff\1\40\2\uffff\1\40\6\44\47\uffff";
    static final String DFA11_maxS =
        "\2\u00b9\56\uffff\1\u00b9\2\uffff\1\u00b9\1\46\3\167\1\46\1\125"+
        "\47\uffff";
    static final String DFA11_acceptS =
        "\2\uffff\1\2\35\uffff\1\1\100\uffff";
    static final String DFA11_specialS =
        "\141\uffff}>";
    static final String[] DFA11_transitionS = {
            "\4\2\1\uffff\2\2\1\uffff\2\2\1\1\2\uffff\2\2\2\uffff\3\2\27"+
            "\uffff\12\2\4\uffff\3\2\3\uffff\5\2\2\uffff\72\2\1\uffff\12"+
            "\2\1\uffff\16\2",
            "\3\40\1\2\1\uffff\6\40\1\uffff\1\40\2\2\2\uffff\3\40\20\uffff"+
            "\2\40\4\uffff\32\40\2\uffff\12\40\1\70\1\71\1\40\1\65\1\40\1"+
            "\66\1\67\1\40\1\60\1\63\1\64\45\40\1\uffff\12\40\1\uffff\16"+
            "\40",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\3\2\1\uffff\1\40\2\2\1\uffff\3\2\1\uffff\1\2\4\uffff\3\2\27"+
            "\uffff\12\2\4\uffff\3\2\3\uffff\5\2\2\uffff\72\2\1\uffff\12"+
            "\2\1\uffff\16\2",
            "",
            "",
            "\3\2\1\uffff\1\40\6\2\1\uffff\1\2\4\uffff\3\2\20\uffff\2\2"+
            "\4\uffff\32\2\2\uffff\72\2\1\uffff\12\2\1\uffff\16\2",
            "\1\40\1\uffff\1\2",
            "\1\40\117\uffff\1\2\2\uffff\1\2",
            "\1\40\122\uffff\1\2",
            "\1\40\122\uffff\1\2",
            "\1\40\1\uffff\1\2",
            "\1\40\60\uffff\1\2",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA11_eot = DFA.unpackEncodedString(DFA11_eotS);
    static final short[] DFA11_eof = DFA.unpackEncodedString(DFA11_eofS);
    static final char[] DFA11_min = DFA.unpackEncodedStringToUnsignedChars(DFA11_minS);
    static final char[] DFA11_max = DFA.unpackEncodedStringToUnsignedChars(DFA11_maxS);
    static final short[] DFA11_accept = DFA.unpackEncodedString(DFA11_acceptS);
    static final short[] DFA11_special = DFA.unpackEncodedString(DFA11_specialS);
    static final short[][] DFA11_transition;

    static {
        int numStates = DFA11_transitionS.length;
        DFA11_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA11_transition[i] = DFA.unpackEncodedString(DFA11_transitionS[i]);
        }
    }

    class DFA11 extends DFA {

        public DFA11(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 11;
            this.eot = DFA11_eot;
            this.eof = DFA11_eof;
            this.min = DFA11_min;
            this.max = DFA11_max;
            this.accept = DFA11_accept;
            this.special = DFA11_special;
            this.transition = DFA11_transition;
        }
        public String getDescription() {
            return "143:41: ( ESCAPE escape_expr= eq_subexpr )?";
        }
    }
    static final String DFA15_eotS =
        "\101\uffff";
    static final String DFA15_eofS =
        "\101\uffff";
    static final String DFA15_minS =
        "\3\40\76\uffff";
    static final String DFA15_maxS =
        "\3\u00b9\76\uffff";
    static final String DFA15_acceptS =
        "\3\uffff\1\1\1\2\74\uffff";
    static final String DFA15_specialS =
        "\101\uffff}>";
    static final String[] DFA15_transitionS = {
            "\3\2\2\uffff\2\2\1\uffff\3\2\6\uffff\3\2\27\uffff\1\2\1\1\10"+
            "\2\4\uffff\3\2\3\uffff\5\2\2\uffff\72\2\1\uffff\12\2\1\uffff"+
            "\16\2",
            "\4\4\1\3\2\4\1\uffff\3\4\2\uffff\2\4\2\uffff\3\4\27\uffff\12"+
            "\4\4\uffff\3\4\3\uffff\5\4\2\uffff\72\4\1\uffff\12\4\1\uffff"+
            "\16\4",
            "\4\4\1\3\2\4\1\uffff\3\4\2\uffff\2\4\2\uffff\3\4\27\uffff\12"+
            "\4\4\uffff\3\4\3\uffff\5\4\2\uffff\72\4\1\uffff\12\4\1\uffff"+
            "\16\4",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA15_eot = DFA.unpackEncodedString(DFA15_eotS);
    static final short[] DFA15_eof = DFA.unpackEncodedString(DFA15_eofS);
    static final char[] DFA15_min = DFA.unpackEncodedStringToUnsignedChars(DFA15_minS);
    static final char[] DFA15_max = DFA.unpackEncodedStringToUnsignedChars(DFA15_maxS);
    static final short[] DFA15_accept = DFA.unpackEncodedString(DFA15_acceptS);
    static final short[] DFA15_special = DFA.unpackEncodedString(DFA15_specialS);
    static final short[][] DFA15_transition;

    static {
        int numStates = DFA15_transitionS.length;
        DFA15_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA15_transition[i] = DFA.unpackEncodedString(DFA15_transitionS[i]);
        }
    }

    class DFA15 extends DFA {

        public DFA15(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 15;
            this.eot = DFA15_eot;
            this.eof = DFA15_eof;
            this.min = DFA15_min;
            this.max = DFA15_max;
            this.accept = DFA15_accept;
            this.special = DFA15_special;
            this.transition = DFA15_transition;
        }
        public String getDescription() {
            return "145:13: (database_name= id DOT )?";
        }
    }
    static final String DFA18_eotS =
        "\40\uffff";
    static final String DFA18_eofS =
        "\40\uffff";
    static final String DFA18_minS =
        "\1\40\37\uffff";
    static final String DFA18_maxS =
        "\1\u00b9\37\uffff";
    static final String DFA18_acceptS =
        "\1\uffff\1\2\35\uffff\1\1";
    static final String DFA18_specialS =
        "\40\uffff}>";
    static final String[] DFA18_transitionS = {
            "\4\1\1\uffff\2\1\1\uffff\3\1\2\uffff\2\1\2\uffff\3\1\4\37\23"+
            "\uffff\12\1\4\uffff\3\1\3\uffff\5\1\2\uffff\72\1\1\uffff\12"+
            "\1\1\uffff\16\1",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA18_eot = DFA.unpackEncodedString(DFA18_eotS);
    static final short[] DFA18_eof = DFA.unpackEncodedString(DFA18_eofS);
    static final char[] DFA18_min = DFA.unpackEncodedStringToUnsignedChars(DFA18_minS);
    static final char[] DFA18_max = DFA.unpackEncodedStringToUnsignedChars(DFA18_maxS);
    static final short[] DFA18_accept = DFA.unpackEncodedString(DFA18_acceptS);
    static final short[] DFA18_special = DFA.unpackEncodedString(DFA18_specialS);
    static final short[][] DFA18_transition;

    static {
        int numStates = DFA18_transitionS.length;
        DFA18_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA18_transition[i] = DFA.unpackEncodedString(DFA18_transitionS[i]);
        }
    }

    class DFA18 extends DFA {

        public DFA18(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 18;
            this.eot = DFA18_eot;
            this.eof = DFA18_eof;
            this.min = DFA18_min;
            this.max = DFA18_max;
            this.accept = DFA18_accept;
            this.special = DFA18_special;
            this.transition = DFA18_transition;
        }
        public String getDescription() {
            return "()+ loopback of 150:5: ( ( EQUALS | EQUALS2 | NOT_EQUALS | NOT_EQUALS2 ) eq_subexpr )+";
        }
    }
    static final String DFA20_eotS =
        "\51\uffff";
    static final String DFA20_eofS =
        "\51\uffff";
    static final String DFA20_minS =
        "\1\40\50\uffff";
    static final String DFA20_maxS =
        "\1\u00b9\50\uffff";
    static final String DFA20_acceptS =
        "\1\uffff\1\2\46\uffff\1\1";
    static final String DFA20_specialS =
        "\51\uffff}>";
    static final String[] DFA20_transitionS = {
            "\4\1\1\uffff\7\1\1\uffff\17\1\4\50\13\uffff\12\1\4\uffff\3\1"+
            "\3\uffff\5\1\2\uffff\72\1\1\uffff\12\1\1\uffff\16\1",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA20_eot = DFA.unpackEncodedString(DFA20_eotS);
    static final short[] DFA20_eof = DFA.unpackEncodedString(DFA20_eofS);
    static final char[] DFA20_min = DFA.unpackEncodedStringToUnsignedChars(DFA20_minS);
    static final char[] DFA20_max = DFA.unpackEncodedStringToUnsignedChars(DFA20_maxS);
    static final short[] DFA20_accept = DFA.unpackEncodedString(DFA20_acceptS);
    static final short[] DFA20_special = DFA.unpackEncodedString(DFA20_specialS);
    static final short[][] DFA20_transition;

    static {
        int numStates = DFA20_transitionS.length;
        DFA20_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA20_transition[i] = DFA.unpackEncodedString(DFA20_transitionS[i]);
        }
    }

    class DFA20 extends DFA {

        public DFA20(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 20;
            this.eot = DFA20_eot;
            this.eof = DFA20_eof;
            this.min = DFA20_min;
            this.max = DFA20_max;
            this.accept = DFA20_accept;
            this.special = DFA20_special;
            this.transition = DFA20_transition;
        }
        public String getDescription() {
            return "()* loopback of 155:25: ( ( LESS | LESS_OR_EQ | GREATER | GREATER_OR_EQ ) neq_subexpr )*";
        }
    }
    static final String DFA21_eotS =
        "\52\uffff";
    static final String DFA21_eofS =
        "\52\uffff";
    static final String DFA21_minS =
        "\1\40\51\uffff";
    static final String DFA21_maxS =
        "\1\u00b9\51\uffff";
    static final String DFA21_acceptS =
        "\1\uffff\1\2\47\uffff\1\1";
    static final String DFA21_specialS =
        "\52\uffff}>";
    static final String[] DFA21_transitionS = {
            "\4\1\1\uffff\7\1\1\uffff\23\1\4\51\7\uffff\12\1\4\uffff\3\1"+
            "\3\uffff\5\1\2\uffff\72\1\1\uffff\12\1\1\uffff\16\1",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA21_eot = DFA.unpackEncodedString(DFA21_eotS);
    static final short[] DFA21_eof = DFA.unpackEncodedString(DFA21_eofS);
    static final char[] DFA21_min = DFA.unpackEncodedStringToUnsignedChars(DFA21_minS);
    static final char[] DFA21_max = DFA.unpackEncodedStringToUnsignedChars(DFA21_maxS);
    static final short[] DFA21_accept = DFA.unpackEncodedString(DFA21_acceptS);
    static final short[] DFA21_special = DFA.unpackEncodedString(DFA21_specialS);
    static final short[][] DFA21_transition;

    static {
        int numStates = DFA21_transitionS.length;
        DFA21_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA21_transition[i] = DFA.unpackEncodedString(DFA21_transitionS[i]);
        }
    }

    class DFA21 extends DFA {

        public DFA21(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 21;
            this.eot = DFA21_eot;
            this.eof = DFA21_eof;
            this.min = DFA21_min;
            this.max = DFA21_max;
            this.accept = DFA21_accept;
            this.special = DFA21_special;
            this.transition = DFA21_transition;
        }
        public String getDescription() {
            return "()* loopback of 157:26: ( ( SHIFT_LEFT | SHIFT_RIGHT | AMPERSAND | PIPE ) bit_subexpr )*";
        }
    }
    static final String DFA22_eotS =
        "\53\uffff";
    static final String DFA22_eofS =
        "\53\uffff";
    static final String DFA22_minS =
        "\1\40\52\uffff";
    static final String DFA22_maxS =
        "\1\u00b9\52\uffff";
    static final String DFA22_acceptS =
        "\1\uffff\1\2\50\uffff\1\1";
    static final String DFA22_specialS =
        "\53\uffff}>";
    static final String[] DFA22_transitionS = {
            "\4\1\1\uffff\7\1\1\uffff\27\1\2\52\5\uffff\12\1\4\uffff\3\1"+
            "\3\uffff\5\1\2\uffff\72\1\1\uffff\12\1\1\uffff\16\1",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA22_eot = DFA.unpackEncodedString(DFA22_eotS);
    static final short[] DFA22_eof = DFA.unpackEncodedString(DFA22_eofS);
    static final char[] DFA22_min = DFA.unpackEncodedStringToUnsignedChars(DFA22_minS);
    static final char[] DFA22_max = DFA.unpackEncodedStringToUnsignedChars(DFA22_maxS);
    static final short[] DFA22_accept = DFA.unpackEncodedString(DFA22_acceptS);
    static final short[] DFA22_special = DFA.unpackEncodedString(DFA22_specialS);
    static final short[][] DFA22_transition;

    static {
        int numStates = DFA22_transitionS.length;
        DFA22_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA22_transition[i] = DFA.unpackEncodedString(DFA22_transitionS[i]);
        }
    }

    class DFA22 extends DFA {

        public DFA22(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 22;
            this.eot = DFA22_eot;
            this.eof = DFA22_eof;
            this.min = DFA22_min;
            this.max = DFA22_max;
            this.accept = DFA22_accept;
            this.special = DFA22_special;
            this.transition = DFA22_transition;
        }
        public String getDescription() {
            return "()* loopback of 159:26: ( ( PLUS | MINUS ) add_subexpr )*";
        }
    }
    static final String DFA23_eotS =
        "\54\uffff";
    static final String DFA23_eofS =
        "\54\uffff";
    static final String DFA23_minS =
        "\1\40\53\uffff";
    static final String DFA23_maxS =
        "\1\u00b9\53\uffff";
    static final String DFA23_acceptS =
        "\1\uffff\1\2\51\uffff\1\1";
    static final String DFA23_specialS =
        "\54\uffff}>";
    static final String[] DFA23_transitionS = {
            "\4\1\1\uffff\7\1\1\uffff\31\1\3\53\2\uffff\12\1\4\uffff\3\1"+
            "\3\uffff\5\1\2\uffff\72\1\1\uffff\12\1\1\uffff\16\1",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA23_eot = DFA.unpackEncodedString(DFA23_eotS);
    static final short[] DFA23_eof = DFA.unpackEncodedString(DFA23_eofS);
    static final char[] DFA23_min = DFA.unpackEncodedStringToUnsignedChars(DFA23_minS);
    static final char[] DFA23_max = DFA.unpackEncodedStringToUnsignedChars(DFA23_maxS);
    static final short[] DFA23_accept = DFA.unpackEncodedString(DFA23_acceptS);
    static final short[] DFA23_special = DFA.unpackEncodedString(DFA23_specialS);
    static final short[][] DFA23_transition;

    static {
        int numStates = DFA23_transitionS.length;
        DFA23_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA23_transition[i] = DFA.unpackEncodedString(DFA23_transitionS[i]);
        }
    }

    class DFA23 extends DFA {

        public DFA23(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 23;
            this.eot = DFA23_eot;
            this.eof = DFA23_eof;
            this.min = DFA23_min;
            this.max = DFA23_max;
            this.accept = DFA23_accept;
            this.special = DFA23_special;
            this.transition = DFA23_transition;
        }
        public String getDescription() {
            return "()* loopback of 161:26: ( ( ASTERISK | SLASH | PERCENT ) mul_subexpr )*";
        }
    }
    static final String DFA24_eotS =
        "\55\uffff";
    static final String DFA24_eofS =
        "\55\uffff";
    static final String DFA24_minS =
        "\1\40\54\uffff";
    static final String DFA24_maxS =
        "\1\u00b9\54\uffff";
    static final String DFA24_acceptS =
        "\1\uffff\1\2\52\uffff\1\1";
    static final String DFA24_specialS =
        "\55\uffff}>";
    static final String[] DFA24_transitionS = {
            "\4\1\1\uffff\7\1\1\uffff\34\1\1\54\1\uffff\12\1\4\uffff\3\1"+
            "\3\uffff\5\1\2\uffff\72\1\1\uffff\12\1\1\uffff\16\1",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA24_eot = DFA.unpackEncodedString(DFA24_eotS);
    static final short[] DFA24_eof = DFA.unpackEncodedString(DFA24_eofS);
    static final char[] DFA24_min = DFA.unpackEncodedStringToUnsignedChars(DFA24_minS);
    static final char[] DFA24_max = DFA.unpackEncodedStringToUnsignedChars(DFA24_maxS);
    static final short[] DFA24_accept = DFA.unpackEncodedString(DFA24_acceptS);
    static final short[] DFA24_special = DFA.unpackEncodedString(DFA24_specialS);
    static final short[][] DFA24_transition;

    static {
        int numStates = DFA24_transitionS.length;
        DFA24_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA24_transition[i] = DFA.unpackEncodedString(DFA24_transitionS[i]);
        }
    }

    class DFA24 extends DFA {

        public DFA24(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 24;
            this.eot = DFA24_eot;
            this.eof = DFA24_eof;
            this.min = DFA24_min;
            this.max = DFA24_max;
            this.accept = DFA24_accept;
            this.special = DFA24_special;
            this.transition = DFA24_transition;
        }
        public String getDescription() {
            return "()* loopback of 163:26: ( DOUBLE_PIPE con_subexpr )*";
        }
    }
    static final String DFA25_eotS =
        "\23\uffff";
    static final String DFA25_eofS =
        "\23\uffff";
    static final String DFA25_minS =
        "\1\40\22\uffff";
    static final String DFA25_maxS =
        "\1\u00b9\22\uffff";
    static final String DFA25_acceptS =
        "\1\uffff\1\1\20\uffff\1\2";
    static final String DFA25_specialS =
        "\23\uffff}>";
    static final String[] DFA25_transitionS = {
            "\3\1\2\uffff\2\1\1\22\3\1\1\uffff\1\1\4\uffff\3\1\20\uffff\2"+
            "\22\4\uffff\1\22\31\1\2\uffff\72\1\1\uffff\12\1\1\uffff\16\1",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA25_eot = DFA.unpackEncodedString(DFA25_eotS);
    static final short[] DFA25_eof = DFA.unpackEncodedString(DFA25_eofS);
    static final char[] DFA25_min = DFA.unpackEncodedStringToUnsignedChars(DFA25_minS);
    static final char[] DFA25_max = DFA.unpackEncodedStringToUnsignedChars(DFA25_maxS);
    static final short[] DFA25_accept = DFA.unpackEncodedString(DFA25_acceptS);
    static final short[] DFA25_special = DFA.unpackEncodedString(DFA25_specialS);
    static final short[][] DFA25_transition;

    static {
        int numStates = DFA25_transitionS.length;
        DFA25_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA25_transition[i] = DFA.unpackEncodedString(DFA25_transitionS[i]);
        }
    }

    class DFA25 extends DFA {

        public DFA25(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 25;
            this.eot = DFA25_eot;
            this.eof = DFA25_eof;
            this.min = DFA25_min;
            this.max = DFA25_max;
            this.accept = DFA25_accept;
            this.special = DFA25_special;
            this.transition = DFA25_transition;
        }
        public String getDescription() {
            return "165:1: con_subexpr : ( unary_subexpr | unary_op unary_subexpr -> ^( unary_op unary_subexpr ) );";
        }
    }
    static final String DFA26_eotS =
        "\72\uffff";
    static final String DFA26_eofS =
        "\72\uffff";
    static final String DFA26_minS =
        "\1\40\1\43\70\uffff";
    static final String DFA26_maxS =
        "\1\u00b9\1\172\70\uffff";
    static final String DFA26_acceptS =
        "\2\uffff\1\2\53\uffff\1\1\13\uffff";
    static final String DFA26_specialS =
        "\72\uffff}>";
    static final String[] DFA26_transitionS = {
            "\4\2\1\uffff\7\2\1\uffff\35\2\1\uffff\1\1\11\2\4\uffff\3\2\3"+
            "\uffff\5\2\2\uffff\72\2\1\uffff\12\2\1\uffff\16\2",
            "\1\2\11\uffff\2\2\35\uffff\1\56\43\uffff\2\2\1\uffff\1\2\1"+
            "\uffff\2\2\1\uffff\3\2",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA26_eot = DFA.unpackEncodedString(DFA26_eotS);
    static final short[] DFA26_eof = DFA.unpackEncodedString(DFA26_eofS);
    static final char[] DFA26_min = DFA.unpackEncodedStringToUnsignedChars(DFA26_minS);
    static final char[] DFA26_max = DFA.unpackEncodedStringToUnsignedChars(DFA26_maxS);
    static final short[] DFA26_accept = DFA.unpackEncodedString(DFA26_acceptS);
    static final short[] DFA26_special = DFA.unpackEncodedString(DFA26_specialS);
    static final short[][] DFA26_transition;

    static {
        int numStates = DFA26_transitionS.length;
        DFA26_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA26_transition[i] = DFA.unpackEncodedString(DFA26_transitionS[i]);
        }
    }

    class DFA26 extends DFA {

        public DFA26(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 26;
            this.eot = DFA26_eot;
            this.eof = DFA26_eof;
            this.min = DFA26_min;
            this.max = DFA26_max;
            this.accept = DFA26_accept;
            this.special = DFA26_special;
            this.transition = DFA26_transition;
        }
        public String getDescription() {
            return "169:26: ( COLLATE collation_name= ID )?";
        }
    }
    static final String DFA35_eotS =
        "\u0111\uffff";
    static final String DFA35_eofS =
        "\u0111\uffff";
    static final String DFA35_minS =
        "\1\40\4\uffff\4\40\3\uffff\1\40\1\44\1\uffff\1\40\1\44\u0100\uffff";
    static final String DFA35_maxS =
        "\1\u00b9\4\uffff\4\u00b9\3\uffff\1\u00b9\1\54\1\uffff\1\u00b9\1"+
        "\54\u0100\uffff";
    static final String DFA35_acceptS =
        "\1\uffff\1\1\7\uffff\1\2\4\uffff\1\5\2\uffff\1\3\u00b8\uffff\1\4"+
        "\56\uffff\1\6\2\uffff\1\7\22\uffff\1\10\1\uffff";
    static final String DFA35_specialS =
        "\u0111\uffff}>";
    static final String[] DFA35_transitionS = {
            "\3\21\2\uffff\2\21\1\uffff\3\21\1\uffff\1\16\4\uffff\1\21\1"+
            "\5\1\21\27\uffff\1\21\1\14\1\21\1\15\1\21\1\17\4\21\4\1\1\6"+
            "\1\7\1\10\3\11\1\20\4\21\2\uffff\72\21\1\uffff\12\21\1\uffff"+
            "\16\21",
            "",
            "",
            "",
            "",
            "\4\1\1\21\7\1\1\uffff\35\1\1\uffff\12\1\4\uffff\3\1\3\uffff"+
            "\5\1\2\uffff\72\1\1\uffff\12\1\1\uffff\16\1",
            "\4\1\1\21\7\1\1\uffff\35\1\1\uffff\12\1\4\uffff\3\1\3\uffff"+
            "\5\1\2\uffff\72\1\1\uffff\12\1\1\uffff\16\1",
            "\4\1\1\21\7\1\1\uffff\35\1\1\uffff\12\1\4\uffff\3\1\3\uffff"+
            "\5\1\2\uffff\72\1\1\uffff\12\1\1\uffff\16\1",
            "\4\1\1\21\7\1\1\uffff\35\1\1\uffff\12\1\4\uffff\3\1\3\uffff"+
            "\5\1\2\uffff\72\1\1\uffff\12\1\1\uffff\16\1",
            "",
            "",
            "",
            "\14\21\1\u00ca\35\21\1\uffff\12\21\4\uffff\3\21\3\uffff\5\21"+
            "\2\uffff\72\21\1\uffff\12\21\1\uffff\16\21",
            "\1\21\7\uffff\1\u00f9",
            "",
            "\3\u00fc\1\uffff\1\21\6\u00fc\1\uffff\1\u00fc\4\uffff\3\u00fc"+
            "\20\uffff\2\u00fc\4\uffff\32\u00fc\2\uffff\72\u00fc\1\uffff"+
            "\12\u00fc\1\uffff\16\u00fc",
            "\1\21\7\uffff\1\u010f",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA35_eot = DFA.unpackEncodedString(DFA35_eotS);
    static final short[] DFA35_eof = DFA.unpackEncodedString(DFA35_eofS);
    static final char[] DFA35_min = DFA.unpackEncodedStringToUnsignedChars(DFA35_minS);
    static final char[] DFA35_max = DFA.unpackEncodedStringToUnsignedChars(DFA35_maxS);
    static final short[] DFA35_accept = DFA.unpackEncodedString(DFA35_acceptS);
    static final short[] DFA35_special = DFA.unpackEncodedString(DFA35_specialS);
    static final short[][] DFA35_transition;

    static {
        int numStates = DFA35_transitionS.length;
        DFA35_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA35_transition[i] = DFA.unpackEncodedString(DFA35_transitionS[i]);
        }
    }

    class DFA35 extends DFA {

        public DFA35(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 35;
            this.eot = DFA35_eot;
            this.eof = DFA35_eof;
            this.min = DFA35_min;
            this.max = DFA35_max;
            this.accept = DFA35_accept;
            this.special = DFA35_special;
            this.transition = DFA35_transition;
        }
        public String getDescription() {
            return "171:1: atom_expr : ( literal_value | bind_parameter | ( (database_name= id DOT )? table_name= id DOT )? column_name= ID -> ^( COLUMN_EXPRESSION ^( $column_name ( ^( $table_name ( $database_name)? ) )? ) ) | name= ID LPAREN ( ( DISTINCT )? args+= expr ( COMMA args+= expr )* | ASTERISK )? RPAREN -> ^( FUNCTION_EXPRESSION $name ( DISTINCT )? ( $args)* ( ASTERISK )? ) | LPAREN expr RPAREN | CAST LPAREN expr AS type_name RPAREN | CASE (case_expr= expr )? ( when_expr )+ ( ELSE else_expr= expr )? END -> ^( CASE ( $case_expr)? ( when_expr )+ ( $else_expr)? ) | raise_function );";
        }
    }
    static final String DFA28_eotS =
        "\61\uffff";
    static final String DFA28_eofS =
        "\61\uffff";
    static final String DFA28_minS =
        "\2\40\57\uffff";
    static final String DFA28_maxS =
        "\2\u00b9\57\uffff";
    static final String DFA28_acceptS =
        "\2\uffff\1\1\1\2\55\uffff";
    static final String DFA28_specialS =
        "\61\uffff}>";
    static final String[] DFA28_transitionS = {
            "\3\2\2\uffff\2\2\1\uffff\3\2\6\uffff\3\2\27\uffff\1\2\1\1\10"+
            "\2\4\uffff\3\2\3\uffff\5\2\2\uffff\72\2\1\uffff\12\2\1\uffff"+
            "\16\2",
            "\4\3\1\2\7\3\1\uffff\35\3\1\uffff\12\3\4\uffff\3\3\3\uffff"+
            "\5\3\2\uffff\72\3\1\uffff\12\3\1\uffff\16\3",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA28_eot = DFA.unpackEncodedString(DFA28_eotS);
    static final short[] DFA28_eof = DFA.unpackEncodedString(DFA28_eofS);
    static final char[] DFA28_min = DFA.unpackEncodedStringToUnsignedChars(DFA28_minS);
    static final char[] DFA28_max = DFA.unpackEncodedStringToUnsignedChars(DFA28_maxS);
    static final short[] DFA28_accept = DFA.unpackEncodedString(DFA28_acceptS);
    static final short[] DFA28_special = DFA.unpackEncodedString(DFA28_specialS);
    static final short[][] DFA28_transition;

    static {
        int numStates = DFA28_transitionS.length;
        DFA28_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA28_transition[i] = DFA.unpackEncodedString(DFA28_transitionS[i]);
        }
    }

    class DFA28 extends DFA {

        public DFA28(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 28;
            this.eot = DFA28_eot;
            this.eof = DFA28_eof;
            this.min = DFA28_min;
            this.max = DFA28_max;
            this.accept = DFA28_accept;
            this.special = DFA28_special;
            this.transition = DFA28_transition;
        }
        public String getDescription() {
            return "174:5: ( (database_name= id DOT )? table_name= id DOT )?";
        }
    }
    static final String DFA27_eotS =
        "\145\uffff";
    static final String DFA27_eofS =
        "\145\uffff";
    static final String DFA27_minS =
        "\1\40\2\44\3\40\1\uffff\1\40\135\uffff";
    static final String DFA27_maxS =
        "\1\u00b9\2\44\3\u00b9\1\uffff\1\u00b9\135\uffff";
    static final String DFA27_acceptS =
        "\6\uffff\1\1\2\uffff\1\2\133\uffff";
    static final String DFA27_specialS =
        "\145\uffff}>";
    static final String[] DFA27_transitionS = {
            "\3\2\2\uffff\2\2\1\uffff\3\2\6\uffff\3\2\27\uffff\1\2\1\1\10"+
            "\2\4\uffff\3\2\3\uffff\5\2\2\uffff\72\2\1\uffff\12\2\1\uffff"+
            "\16\2",
            "\1\3",
            "\1\4",
            "\3\6\2\uffff\2\6\1\uffff\3\6\6\uffff\3\6\27\uffff\1\6\1\5\10"+
            "\6\4\uffff\3\6\3\uffff\5\6\2\uffff\72\6\1\uffff\12\6\1\uffff"+
            "\16\6",
            "\3\6\2\uffff\2\6\1\uffff\3\6\6\uffff\3\6\27\uffff\1\6\1\7\10"+
            "\6\4\uffff\3\6\3\uffff\5\6\2\uffff\72\6\1\uffff\12\6\1\uffff"+
            "\16\6",
            "\4\11\1\6\7\11\1\uffff\35\11\1\uffff\12\11\4\uffff\3\11\3\uffff"+
            "\5\11\2\uffff\72\11\1\uffff\12\11\1\uffff\16\11",
            "",
            "\4\11\1\6\7\11\1\uffff\35\11\1\uffff\12\11\4\uffff\3\11\3\uffff"+
            "\5\11\2\uffff\72\11\1\uffff\12\11\1\uffff\16\11",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA27_eot = DFA.unpackEncodedString(DFA27_eotS);
    static final short[] DFA27_eof = DFA.unpackEncodedString(DFA27_eofS);
    static final char[] DFA27_min = DFA.unpackEncodedStringToUnsignedChars(DFA27_minS);
    static final char[] DFA27_max = DFA.unpackEncodedStringToUnsignedChars(DFA27_maxS);
    static final short[] DFA27_accept = DFA.unpackEncodedString(DFA27_acceptS);
    static final short[] DFA27_special = DFA.unpackEncodedString(DFA27_specialS);
    static final short[][] DFA27_transition;

    static {
        int numStates = DFA27_transitionS.length;
        DFA27_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA27_transition[i] = DFA.unpackEncodedString(DFA27_transitionS[i]);
        }
    }

    class DFA27 extends DFA {

        public DFA27(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 27;
            this.eot = DFA27_eot;
            this.eof = DFA27_eof;
            this.min = DFA27_min;
            this.max = DFA27_max;
            this.accept = DFA27_accept;
            this.special = DFA27_special;
            this.transition = DFA27_transition;
        }
        public String getDescription() {
            return "174:6: (database_name= id DOT )?";
        }
    }
    static final String DFA31_eotS =
        "\26\uffff";
    static final String DFA31_eofS =
        "\26\uffff";
    static final String DFA31_minS =
        "\1\40\25\uffff";
    static final String DFA31_maxS =
        "\1\u00b9\25\uffff";
    static final String DFA31_acceptS =
        "\1\uffff\1\1\22\uffff\1\2\1\3";
    static final String DFA31_specialS =
        "\26\uffff}>";
    static final String[] DFA31_transitionS = {
            "\3\1\2\uffff\6\1\1\uffff\1\1\1\uffff\1\25\2\uffff\3\1\20\uffff"+
            "\2\1\1\24\3\uffff\32\1\2\uffff\72\1\1\uffff\12\1\1\uffff\16"+
            "\1",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA31_eot = DFA.unpackEncodedString(DFA31_eotS);
    static final short[] DFA31_eof = DFA.unpackEncodedString(DFA31_eofS);
    static final char[] DFA31_min = DFA.unpackEncodedStringToUnsignedChars(DFA31_minS);
    static final char[] DFA31_max = DFA.unpackEncodedStringToUnsignedChars(DFA31_maxS);
    static final short[] DFA31_accept = DFA.unpackEncodedString(DFA31_acceptS);
    static final short[] DFA31_special = DFA.unpackEncodedString(DFA31_specialS);
    static final short[][] DFA31_transition;

    static {
        int numStates = DFA31_transitionS.length;
        DFA31_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA31_transition[i] = DFA.unpackEncodedString(DFA31_transitionS[i]);
        }
    }

    class DFA31 extends DFA {

        public DFA31(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 31;
            this.eot = DFA31_eot;
            this.eof = DFA31_eof;
            this.min = DFA31_min;
            this.max = DFA31_max;
            this.accept = DFA31_accept;
            this.special = DFA31_special;
            this.transition = DFA31_transition;
        }
        public String getDescription() {
            return "175:20: ( ( DISTINCT )? args+= expr ( COMMA args+= expr )* | ASTERISK )?";
        }
    }
    static final String DFA29_eotS =
        "\47\uffff";
    static final String DFA29_eofS =
        "\47\uffff";
    static final String DFA29_minS =
        "\2\40\45\uffff";
    static final String DFA29_maxS =
        "\2\u00b9\45\uffff";
    static final String DFA29_acceptS =
        "\2\uffff\1\2\22\uffff\1\1\21\uffff";
    static final String DFA29_specialS =
        "\47\uffff}>";
    static final String[] DFA29_transitionS = {
            "\3\2\2\uffff\6\2\1\uffff\1\2\4\uffff\3\2\20\uffff\2\2\4\uffff"+
            "\3\2\1\1\26\2\2\uffff\72\2\1\uffff\12\2\1\uffff\16\2",
            "\3\25\1\uffff\1\2\6\25\1\uffff\1\25\4\uffff\3\25\20\uffff\2"+
            "\25\4\uffff\32\25\2\uffff\72\25\1\uffff\12\25\1\uffff\16\25",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA29_eot = DFA.unpackEncodedString(DFA29_eotS);
    static final short[] DFA29_eof = DFA.unpackEncodedString(DFA29_eofS);
    static final char[] DFA29_min = DFA.unpackEncodedStringToUnsignedChars(DFA29_minS);
    static final char[] DFA29_max = DFA.unpackEncodedStringToUnsignedChars(DFA29_maxS);
    static final short[] DFA29_accept = DFA.unpackEncodedString(DFA29_acceptS);
    static final short[] DFA29_special = DFA.unpackEncodedString(DFA29_specialS);
    static final short[][] DFA29_transition;

    static {
        int numStates = DFA29_transitionS.length;
        DFA29_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA29_transition[i] = DFA.unpackEncodedString(DFA29_transitionS[i]);
        }
    }

    class DFA29 extends DFA {

        public DFA29(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 29;
            this.eot = DFA29_eot;
            this.eof = DFA29_eof;
            this.min = DFA29_min;
            this.max = DFA29_max;
            this.accept = DFA29_accept;
            this.special = DFA29_special;
            this.transition = DFA29_transition;
        }
        public String getDescription() {
            return "175:21: ( DISTINCT )?";
        }
    }
    static final String DFA32_eotS =
        "\47\uffff";
    static final String DFA32_eofS =
        "\47\uffff";
    static final String DFA32_minS =
        "\1\40\20\uffff\1\40\25\uffff";
    static final String DFA32_maxS =
        "\1\u00b9\20\uffff\1\u00b9\25\uffff";
    static final String DFA32_acceptS =
        "\1\uffff\1\1\22\uffff\1\2\22\uffff";
    static final String DFA32_specialS =
        "\47\uffff}>";
    static final String[] DFA32_transitionS = {
            "\3\1\2\uffff\6\1\1\uffff\1\1\4\uffff\3\1\20\uffff\2\1\4\uffff"+
            "\11\1\1\21\20\1\2\uffff\72\1\1\uffff\12\1\1\uffff\16\1",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\3\24\1\uffff\1\1\6\24\1\uffff\1\24\4\uffff\3\24\20\uffff\2"+
            "\24\4\uffff\32\24\2\uffff\72\24\1\uffff\12\24\1\uffff\16\24",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA32_eot = DFA.unpackEncodedString(DFA32_eotS);
    static final short[] DFA32_eof = DFA.unpackEncodedString(DFA32_eofS);
    static final char[] DFA32_min = DFA.unpackEncodedStringToUnsignedChars(DFA32_minS);
    static final char[] DFA32_max = DFA.unpackEncodedStringToUnsignedChars(DFA32_maxS);
    static final short[] DFA32_accept = DFA.unpackEncodedString(DFA32_acceptS);
    static final short[] DFA32_special = DFA.unpackEncodedString(DFA32_specialS);
    static final short[][] DFA32_transition;

    static {
        int numStates = DFA32_transitionS.length;
        DFA32_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA32_transition[i] = DFA.unpackEncodedString(DFA32_transitionS[i]);
        }
    }

    class DFA32 extends DFA {

        public DFA32(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 32;
            this.eot = DFA32_eot;
            this.eof = DFA32_eof;
            this.min = DFA32_min;
            this.max = DFA32_max;
            this.accept = DFA32_accept;
            this.special = DFA32_special;
            this.transition = DFA32_transition;
        }
        public String getDescription() {
            return "180:10: (case_expr= expr )?";
        }
    }
    static final String DFA37_eotS =
        "\62\uffff";
    static final String DFA37_eofS =
        "\62\uffff";
    static final String DFA37_minS =
        "\1\134\1\40\60\uffff";
    static final String DFA37_maxS =
        "\1\136\1\u00b9\60\uffff";
    static final String DFA37_acceptS =
        "\2\uffff\1\3\1\4\1\2\1\1\54\uffff";
    static final String DFA37_specialS =
        "\62\uffff}>";
    static final String[] DFA37_transitionS = {
            "\1\1\1\2\1\3",
            "\4\5\1\uffff\7\5\1\uffff\35\5\1\uffff\12\5\1\4\3\uffff\3\5"+
            "\3\uffff\5\5\2\uffff\72\5\1\uffff\12\5\1\uffff\16\5",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA37_eot = DFA.unpackEncodedString(DFA37_eotS);
    static final short[] DFA37_eof = DFA.unpackEncodedString(DFA37_eofS);
    static final char[] DFA37_min = DFA.unpackEncodedStringToUnsignedChars(DFA37_minS);
    static final char[] DFA37_max = DFA.unpackEncodedStringToUnsignedChars(DFA37_maxS);
    static final short[] DFA37_accept = DFA.unpackEncodedString(DFA37_acceptS);
    static final short[] DFA37_special = DFA.unpackEncodedString(DFA37_specialS);
    static final short[][] DFA37_transition;

    static {
        int numStates = DFA37_transitionS.length;
        DFA37_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA37_transition[i] = DFA.unpackEncodedString(DFA37_transitionS[i]);
        }
    }

    class DFA37 extends DFA {

        public DFA37(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 37;
            this.eot = DFA37_eot;
            this.eof = DFA37_eof;
            this.min = DFA37_min;
            this.max = DFA37_max;
            this.accept = DFA37_accept;
            this.special = DFA37_special;
            this.transition = DFA37_transition;
        }
        public String getDescription() {
            return "197:1: bind_parameter : ( QUESTION -> BIND | QUESTION position= INTEGER -> ^( BIND $position) | COLON name= id -> ^( BIND_NAME $name) | AT name= id -> ^( BIND_NAME $name) );";
        }
    }
    static final String DFA39_eotS =
        "\17\uffff";
    static final String DFA39_eofS =
        "\17\uffff";
    static final String DFA39_minS =
        "\1\43\16\uffff";
    static final String DFA39_maxS =
        "\1\u00a4\16\uffff";
    static final String DFA39_acceptS =
        "\1\uffff\1\2\14\uffff\1\1";
    static final String DFA39_specialS =
        "\17\uffff}>";
    static final String[] DFA39_transitionS = {
            "\1\1\3\uffff\1\1\4\uffff\3\1\3\uffff\1\1\30\uffff\1\1\1\16\72"+
            "\uffff\1\1\23\uffff\2\1\4\uffff\2\1\1\uffff\1\1",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA39_eot = DFA.unpackEncodedString(DFA39_eotS);
    static final short[] DFA39_eof = DFA.unpackEncodedString(DFA39_eofS);
    static final char[] DFA39_min = DFA.unpackEncodedStringToUnsignedChars(DFA39_minS);
    static final char[] DFA39_max = DFA.unpackEncodedStringToUnsignedChars(DFA39_maxS);
    static final short[] DFA39_accept = DFA.unpackEncodedString(DFA39_acceptS);
    static final short[] DFA39_special = DFA.unpackEncodedString(DFA39_specialS);
    static final short[][] DFA39_transition;

    static {
        int numStates = DFA39_transitionS.length;
        DFA39_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA39_transition[i] = DFA.unpackEncodedString(DFA39_transitionS[i]);
        }
    }

    class DFA39 extends DFA {

        public DFA39(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 39;
            this.eot = DFA39_eot;
            this.eof = DFA39_eof;
            this.min = DFA39_min;
            this.max = DFA39_max;
            this.accept = DFA39_accept;
            this.special = DFA39_special;
            this.transition = DFA39_transition;
        }
        public String getDescription() {
            return "()+ loopback of 208:17: (names+= ID )+";
        }
    }
    static final String DFA42_eotS =
        "\16\uffff";
    static final String DFA42_eofS =
        "\16\uffff";
    static final String DFA42_minS =
        "\1\43\15\uffff";
    static final String DFA42_maxS =
        "\1\u00a4\15\uffff";
    static final String DFA42_acceptS =
        "\1\uffff\1\1\1\2\13\uffff";
    static final String DFA42_specialS =
        "\16\uffff}>";
    static final String[] DFA42_transitionS = {
            "\1\2\3\uffff\1\2\4\uffff\1\1\2\2\3\uffff\1\2\30\uffff\1\2\73"+
            "\uffff\1\2\23\uffff\2\2\4\uffff\2\2\1\uffff\1\2",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA42_eot = DFA.unpackEncodedString(DFA42_eotS);
    static final short[] DFA42_eof = DFA.unpackEncodedString(DFA42_eofS);
    static final char[] DFA42_min = DFA.unpackEncodedStringToUnsignedChars(DFA42_minS);
    static final char[] DFA42_max = DFA.unpackEncodedStringToUnsignedChars(DFA42_maxS);
    static final short[] DFA42_accept = DFA.unpackEncodedString(DFA42_acceptS);
    static final short[] DFA42_special = DFA.unpackEncodedString(DFA42_specialS);
    static final short[][] DFA42_transition;

    static {
        int numStates = DFA42_transitionS.length;
        DFA42_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA42_transition[i] = DFA.unpackEncodedString(DFA42_transitionS[i]);
        }
    }

    class DFA42 extends DFA {

        public DFA42(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 42;
            this.eot = DFA42_eot;
            this.eof = DFA42_eof;
            this.min = DFA42_min;
            this.max = DFA42_max;
            this.accept = DFA42_accept;
            this.special = DFA42_special;
            this.transition = DFA42_transition;
        }
        public String getDescription() {
            return "208:23: ( LPAREN size1= ( signed_number | MAX | signed_number BYTE ) ( COMMA size2= signed_number )? RPAREN )?";
        }
    }
    static final String DFA40_eotS =
        "\13\uffff";
    static final String DFA40_eofS =
        "\13\uffff";
    static final String DFA40_minS =
        "\1\104\1\125\1\55\1\uffff\1\55\6\uffff";
    static final String DFA40_maxS =
        "\1\144\1\126\1\145\1\uffff\1\145\6\uffff";
    static final String DFA40_acceptS =
        "\3\uffff\1\2\1\uffff\1\1\1\uffff\1\3\3\uffff";
    static final String DFA40_specialS =
        "\13\uffff}>";
    static final String[] DFA40_transitionS = {
            "\2\1\17\uffff\2\2\15\uffff\1\3",
            "\2\4",
            "\2\5\66\uffff\1\7",
            "",
            "\2\5\66\uffff\1\7",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA40_eot = DFA.unpackEncodedString(DFA40_eotS);
    static final short[] DFA40_eof = DFA.unpackEncodedString(DFA40_eofS);
    static final char[] DFA40_min = DFA.unpackEncodedStringToUnsignedChars(DFA40_minS);
    static final char[] DFA40_max = DFA.unpackEncodedStringToUnsignedChars(DFA40_maxS);
    static final short[] DFA40_accept = DFA.unpackEncodedString(DFA40_acceptS);
    static final short[] DFA40_special = DFA.unpackEncodedString(DFA40_specialS);
    static final short[][] DFA40_transition;

    static {
        int numStates = DFA40_transitionS.length;
        DFA40_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA40_transition[i] = DFA.unpackEncodedString(DFA40_transitionS[i]);
        }
    }

    class DFA40 extends DFA {

        public DFA40(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 40;
            this.eot = DFA40_eot;
            this.eof = DFA40_eof;
            this.min = DFA40_min;
            this.max = DFA40_max;
            this.accept = DFA40_accept;
            this.special = DFA40_special;
            this.transition = DFA40_transition;
        }
        public String getDescription() {
            return "208:38: ( signed_number | MAX | signed_number BYTE )";
        }
    }
    static final String DFA44_eotS =
        "\13\uffff";
    static final String DFA44_eofS =
        "\13\uffff";
    static final String DFA44_minS =
        "\1\40\2\43\10\uffff";
    static final String DFA44_maxS =
        "\1\u00b9\2\64\10\uffff";
    static final String DFA44_acceptS =
        "\3\uffff\1\2\2\uffff\1\1\4\uffff";
    static final String DFA44_specialS =
        "\13\uffff}>";
    static final String[] DFA44_transitionS = {
            "\3\2\2\uffff\2\2\1\uffff\3\2\6\uffff\3\2\27\uffff\1\2\1\1\10"+
            "\2\4\uffff\3\2\3\uffff\5\2\2\uffff\72\2\1\uffff\12\2\1\uffff"+
            "\16\2",
            "\1\3\1\6\7\uffff\1\3\7\uffff\1\3",
            "\1\3\1\6\7\uffff\1\3\7\uffff\1\3",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA44_eot = DFA.unpackEncodedString(DFA44_eotS);
    static final short[] DFA44_eof = DFA.unpackEncodedString(DFA44_eofS);
    static final char[] DFA44_min = DFA.unpackEncodedStringToUnsignedChars(DFA44_minS);
    static final char[] DFA44_max = DFA.unpackEncodedStringToUnsignedChars(DFA44_maxS);
    static final short[] DFA44_accept = DFA.unpackEncodedString(DFA44_acceptS);
    static final short[] DFA44_special = DFA.unpackEncodedString(DFA44_specialS);
    static final short[][] DFA44_transition;

    static {
        int numStates = DFA44_transitionS.length;
        DFA44_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA44_transition[i] = DFA.unpackEncodedString(DFA44_transitionS[i]);
        }
    }

    class DFA44 extends DFA {

        public DFA44(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 44;
            this.eot = DFA44_eot;
            this.eof = DFA44_eof;
            this.min = DFA44_min;
            this.max = DFA44_max;
            this.accept = DFA44_accept;
            this.special = DFA44_special;
            this.transition = DFA44_transition;
        }
        public String getDescription() {
            return "214:21: (database_name= id DOT )?";
        }
    }
    static final String DFA47_eotS =
        "\17\uffff";
    static final String DFA47_eofS =
        "\17\uffff";
    static final String DFA47_minS =
        "\2\40\3\uffff\1\40\3\uffff\1\40\5\uffff";
    static final String DFA47_maxS =
        "\2\u00b9\3\uffff\1\u00b9\3\uffff\1\u00b9\5\uffff";
    static final String DFA47_acceptS =
        "\2\uffff\1\2\3\uffff\1\1\10\uffff";
    static final String DFA47_specialS =
        "\17\uffff}>";
    static final String[] DFA47_transitionS = {
            "\3\2\2\uffff\2\2\1\uffff\3\2\6\uffff\3\2\27\uffff\12\2\2\uffff"+
            "\1\2\1\uffff\3\2\3\uffff\5\2\2\uffff\2\2\1\1\67\2\1\uffff\12"+
            "\2\1\uffff\16\2",
            "\3\6\2\uffff\2\6\1\uffff\3\6\6\uffff\3\6\27\uffff\4\6\1\5\5"+
            "\6\2\uffff\1\6\1\uffff\3\6\3\uffff\5\6\2\uffff\72\6\1\uffff"+
            "\12\6\1\uffff\16\6",
            "",
            "",
            "",
            "\3\2\2\uffff\2\2\1\uffff\3\2\6\uffff\3\2\27\uffff\4\2\1\11"+
            "\5\2\4\uffff\3\2\3\uffff\5\2\2\uffff\72\2\1\uffff\12\2\1\uffff"+
            "\16\2",
            "",
            "",
            "",
            "\3\6\1\2\1\uffff\2\6\1\uffff\3\6\6\uffff\3\6\27\uffff\12\6"+
            "\4\uffff\3\6\3\uffff\5\6\2\uffff\72\6\1\uffff\12\6\1\uffff\16"+
            "\6",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA47_eot = DFA.unpackEncodedString(DFA47_eotS);
    static final short[] DFA47_eof = DFA.unpackEncodedString(DFA47_eofS);
    static final char[] DFA47_min = DFA.unpackEncodedStringToUnsignedChars(DFA47_minS);
    static final char[] DFA47_max = DFA.unpackEncodedStringToUnsignedChars(DFA47_maxS);
    static final short[] DFA47_accept = DFA.unpackEncodedString(DFA47_acceptS);
    static final short[] DFA47_special = DFA.unpackEncodedString(DFA47_specialS);
    static final short[][] DFA47_transition;

    static {
        int numStates = DFA47_transitionS.length;
        DFA47_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA47_transition[i] = DFA.unpackEncodedString(DFA47_transitionS[i]);
        }
    }

    class DFA47 extends DFA {

        public DFA47(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 47;
            this.eot = DFA47_eot;
            this.eof = DFA47_eof;
            this.min = DFA47_min;
            this.max = DFA47_max;
            this.accept = DFA47_accept;
            this.special = DFA47_special;
            this.transition = DFA47_transition;
        }
        public String getDescription() {
            return "224:21: ( DATABASE )?";
        }
    }
    static final String DFA52_eotS =
        "\14\uffff";
    static final String DFA52_eofS =
        "\14\uffff";
    static final String DFA52_minS =
        "\1\43\13\uffff";
    static final String DFA52_maxS =
        "\1\173\13\uffff";
    static final String DFA52_acceptS =
        "\1\uffff\1\1\1\2\1\3\10\uffff";
    static final String DFA52_specialS =
        "\14\uffff}>";
    static final String[] DFA52_transitionS = {
            "\1\3\11\uffff\2\3\77\uffff\1\1\1\2\2\3\1\uffff\1\3\1\uffff\2"+
            "\3\4\uffff\1\3",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA52_eot = DFA.unpackEncodedString(DFA52_eotS);
    static final short[] DFA52_eof = DFA.unpackEncodedString(DFA52_eofS);
    static final char[] DFA52_min = DFA.unpackEncodedStringToUnsignedChars(DFA52_minS);
    static final char[] DFA52_max = DFA.unpackEncodedStringToUnsignedChars(DFA52_maxS);
    static final short[] DFA52_accept = DFA.unpackEncodedString(DFA52_acceptS);
    static final short[] DFA52_special = DFA.unpackEncodedString(DFA52_specialS);
    static final short[][] DFA52_transition;

    static {
        int numStates = DFA52_transitionS.length;
        DFA52_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA52_transition[i] = DFA.unpackEncodedString(DFA52_transitionS[i]);
        }
    }

    class DFA52 extends DFA {

        public DFA52(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 52;
            this.eot = DFA52_eot;
            this.eof = DFA52_eof;
            this.min = DFA52_min;
            this.max = DFA52_max;
            this.accept = DFA52_accept;
            this.special = DFA52_special;
            this.transition = DFA52_transition;
        }
        public String getDescription() {
            return "244:82: ( ASC | DESC )?";
        }
    }
    static final String DFA64_eotS =
        "\76\uffff";
    static final String DFA64_eofS =
        "\76\uffff";
    static final String DFA64_minS =
        "\3\40\73\uffff";
    static final String DFA64_maxS =
        "\3\u00b9\73\uffff";
    static final String DFA64_acceptS =
        "\3\uffff\1\3\23\uffff\1\1\23\uffff\1\2\22\uffff";
    static final String DFA64_specialS =
        "\76\uffff}>";
    static final String[] DFA64_transitionS = {
            "\3\3\2\uffff\6\3\1\uffff\1\3\4\uffff\3\3\20\uffff\3\3\3\uffff"+
            "\3\3\1\2\26\3\2\uffff\16\3\1\1\53\3\1\uffff\12\3\1\uffff\16"+
            "\3",
            "\3\27\1\uffff\1\3\6\27\1\uffff\1\27\4\uffff\3\27\20\uffff\3"+
            "\27\3\uffff\32\27\2\uffff\72\27\1\uffff\12\27\1\uffff\16\27",
            "\3\53\1\uffff\1\3\6\53\1\uffff\1\53\4\uffff\3\53\20\uffff\3"+
            "\53\3\uffff\32\53\2\uffff\72\53\1\uffff\12\53\1\uffff\16\53",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA64_eot = DFA.unpackEncodedString(DFA64_eotS);
    static final short[] DFA64_eof = DFA.unpackEncodedString(DFA64_eofS);
    static final char[] DFA64_min = DFA.unpackEncodedStringToUnsignedChars(DFA64_minS);
    static final char[] DFA64_max = DFA.unpackEncodedStringToUnsignedChars(DFA64_maxS);
    static final short[] DFA64_accept = DFA.unpackEncodedString(DFA64_acceptS);
    static final short[] DFA64_special = DFA.unpackEncodedString(DFA64_specialS);
    static final short[][] DFA64_transition;

    static {
        int numStates = DFA64_transitionS.length;
        DFA64_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA64_transition[i] = DFA.unpackEncodedString(DFA64_transitionS[i]);
        }
    }

    class DFA64 extends DFA {

        public DFA64(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 64;
            this.eot = DFA64_eot;
            this.eof = DFA64_eof;
            this.min = DFA64_min;
            this.max = DFA64_max;
            this.accept = DFA64_accept;
            this.special = DFA64_special;
            this.transition = DFA64_transition;
        }
        public String getDescription() {
            return "265:10: ( ALL | DISTINCT )?";
        }
    }
    static final String DFA65_eotS =
        "\14\uffff";
    static final String DFA65_eofS =
        "\14\uffff";
    static final String DFA65_minS =
        "\1\43\13\uffff";
    static final String DFA65_maxS =
        "\1\172\13\uffff";
    static final String DFA65_acceptS =
        "\1\uffff\1\2\11\uffff\1\1";
    static final String DFA65_specialS =
        "\14\uffff}>";
    static final String[] DFA65_transitionS = {
            "\1\1\11\uffff\1\13\1\1\101\uffff\2\1\1\uffff\1\1\1\uffff\2\1"+
            "\1\uffff\3\1",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA65_eot = DFA.unpackEncodedString(DFA65_eotS);
    static final short[] DFA65_eof = DFA.unpackEncodedString(DFA65_eofS);
    static final char[] DFA65_min = DFA.unpackEncodedStringToUnsignedChars(DFA65_minS);
    static final char[] DFA65_max = DFA.unpackEncodedStringToUnsignedChars(DFA65_maxS);
    static final short[] DFA65_accept = DFA.unpackEncodedString(DFA65_acceptS);
    static final short[] DFA65_special = DFA.unpackEncodedString(DFA65_specialS);
    static final short[][] DFA65_transition;

    static {
        int numStates = DFA65_transitionS.length;
        DFA65_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA65_transition[i] = DFA.unpackEncodedString(DFA65_transitionS[i]);
        }
    }

    class DFA65 extends DFA {

        public DFA65(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 65;
            this.eot = DFA65_eot;
            this.eof = DFA65_eof;
            this.min = DFA65_min;
            this.max = DFA65_max;
            this.accept = DFA65_accept;
            this.special = DFA65_special;
            this.transition = DFA65_transition;
        }
        public String getDescription() {
            return "()* loopback of 265:42: ( COMMA result_column )*";
        }
    }
    static final String DFA66_eotS =
        "\13\uffff";
    static final String DFA66_eofS =
        "\13\uffff";
    static final String DFA66_minS =
        "\1\43\12\uffff";
    static final String DFA66_maxS =
        "\1\172\12\uffff";
    static final String DFA66_acceptS =
        "\1\uffff\1\1\1\2\10\uffff";
    static final String DFA66_specialS =
        "\13\uffff}>";
    static final String[] DFA66_transitionS = {
            "\1\2\12\uffff\1\2\101\uffff\2\2\1\uffff\1\2\1\uffff\2\2\1\uffff"+
            "\1\1\2\2",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA66_eot = DFA.unpackEncodedString(DFA66_eotS);
    static final short[] DFA66_eof = DFA.unpackEncodedString(DFA66_eofS);
    static final char[] DFA66_min = DFA.unpackEncodedStringToUnsignedChars(DFA66_minS);
    static final char[] DFA66_max = DFA.unpackEncodedStringToUnsignedChars(DFA66_maxS);
    static final short[] DFA66_accept = DFA.unpackEncodedString(DFA66_acceptS);
    static final short[] DFA66_special = DFA.unpackEncodedString(DFA66_specialS);
    static final short[][] DFA66_transition;

    static {
        int numStates = DFA66_transitionS.length;
        DFA66_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA66_transition[i] = DFA.unpackEncodedString(DFA66_transitionS[i]);
        }
    }

    class DFA66 extends DFA {

        public DFA66(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 66;
            this.eot = DFA66_eot;
            this.eof = DFA66_eof;
            this.min = DFA66_min;
            this.max = DFA66_max;
            this.accept = DFA66_accept;
            this.special = DFA66_special;
            this.transition = DFA66_transition;
        }
        public String getDescription() {
            return "265:65: ( FROM join_source )?";
        }
    }
    static final String DFA67_eotS =
        "\12\uffff";
    static final String DFA67_eofS =
        "\12\uffff";
    static final String DFA67_minS =
        "\1\43\11\uffff";
    static final String DFA67_maxS =
        "\1\172\11\uffff";
    static final String DFA67_acceptS =
        "\1\uffff\1\1\1\2\7\uffff";
    static final String DFA67_specialS =
        "\12\uffff}>";
    static final String[] DFA67_transitionS = {
            "\1\2\12\uffff\1\2\101\uffff\2\2\1\uffff\1\2\1\uffff\2\2\2\uffff"+
            "\1\1\1\2",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA67_eot = DFA.unpackEncodedString(DFA67_eotS);
    static final short[] DFA67_eof = DFA.unpackEncodedString(DFA67_eofS);
    static final char[] DFA67_min = DFA.unpackEncodedStringToUnsignedChars(DFA67_minS);
    static final char[] DFA67_max = DFA.unpackEncodedStringToUnsignedChars(DFA67_maxS);
    static final short[] DFA67_accept = DFA.unpackEncodedString(DFA67_acceptS);
    static final short[] DFA67_special = DFA.unpackEncodedString(DFA67_specialS);
    static final short[][] DFA67_transition;

    static {
        int numStates = DFA67_transitionS.length;
        DFA67_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA67_transition[i] = DFA.unpackEncodedString(DFA67_transitionS[i]);
        }
    }

    class DFA67 extends DFA {

        public DFA67(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 67;
            this.eot = DFA67_eot;
            this.eof = DFA67_eof;
            this.min = DFA67_min;
            this.max = DFA67_max;
            this.accept = DFA67_accept;
            this.special = DFA67_special;
            this.transition = DFA67_transition;
        }
        public String getDescription() {
            return "265:85: ( WHERE where_expr= expr )?";
        }
    }
    static final String DFA68_eotS =
        "\12\uffff";
    static final String DFA68_eofS =
        "\12\uffff";
    static final String DFA68_minS =
        "\1\43\11\uffff";
    static final String DFA68_maxS =
        "\1\173\11\uffff";
    static final String DFA68_acceptS =
        "\1\uffff\1\2\7\uffff\1\1";
    static final String DFA68_specialS =
        "\12\uffff}>";
    static final String[] DFA68_transitionS = {
            "\1\1\11\uffff\1\11\1\1\101\uffff\2\1\1\uffff\1\1\1\uffff\2\1"+
            "\4\uffff\1\1",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA68_eot = DFA.unpackEncodedString(DFA68_eotS);
    static final short[] DFA68_eof = DFA.unpackEncodedString(DFA68_eofS);
    static final char[] DFA68_min = DFA.unpackEncodedStringToUnsignedChars(DFA68_minS);
    static final char[] DFA68_max = DFA.unpackEncodedStringToUnsignedChars(DFA68_maxS);
    static final short[] DFA68_accept = DFA.unpackEncodedString(DFA68_acceptS);
    static final short[] DFA68_special = DFA.unpackEncodedString(DFA68_specialS);
    static final short[][] DFA68_transition;

    static {
        int numStates = DFA68_transitionS.length;
        DFA68_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA68_transition[i] = DFA.unpackEncodedString(DFA68_transitionS[i]);
        }
    }

    class DFA68 extends DFA {

        public DFA68(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 68;
            this.eot = DFA68_eot;
            this.eof = DFA68_eof;
            this.min = DFA68_min;
            this.max = DFA68_max;
            this.accept = DFA68_accept;
            this.special = DFA68_special;
            this.transition = DFA68_transition;
        }
        public String getDescription() {
            return "()* loopback of 266:28: ( COMMA ordering_term )*";
        }
    }
    static final String DFA73_eotS =
        "\u00e4\uffff";
    static final String DFA73_eofS =
        "\u00e4\uffff";
    static final String DFA73_minS =
        "\1\40\1\uffff\2\40\4\uffff\3\40\1\44\4\uffff\1\40\2\44\40\uffff"+
        "\2\40\36\uffff\1\40\36\uffff\1\40\36\uffff\1\40\37\uffff\2\40\24"+
        "\uffff\2\40\33\uffff";
    static final String DFA73_maxS =
        "\1\u00b9\1\uffff\2\u00b9\4\uffff\3\u00b9\1\54\4\uffff\1\u00b9\1"+
        "\54\1\44\40\uffff\2\u00b9\36\uffff\1\u00b9\36\uffff\1\u00b9\36\uffff"+
        "\1\u00b9\37\uffff\2\u00b9\24\uffff\2\u00b9\33\uffff";
    static final String DFA73_acceptS =
        "\1\uffff\1\1\2\uffff\1\3\u00c4\uffff\1\2\32\uffff";
    static final String DFA73_specialS =
        "\u00e4\uffff}>";
    static final String[] DFA73_transitionS = {
            "\3\22\2\uffff\2\22\1\4\3\22\1\uffff\1\4\4\uffff\1\22\1\3\1\22"+
            "\20\uffff\2\4\1\1\3\uffff\1\4\1\22\1\2\1\22\1\13\1\22\1\20\4"+
            "\22\4\4\1\10\1\11\1\12\3\4\1\21\4\22\2\uffff\72\22\1\uffff\12"+
            "\22\1\uffff\16\22",
            "",
            "\4\4\1\63\45\4\1\uffff\12\4\4\uffff\3\4\3\uffff\5\4\2\uffff"+
            "\72\4\1\uffff\12\4\1\uffff\16\4",
            "\4\4\1\64\7\4\1\uffff\35\4\1\uffff\12\4\4\uffff\3\4\3\uffff"+
            "\5\4\2\uffff\72\4\1\uffff\12\4\1\uffff\16\4",
            "",
            "",
            "",
            "",
            "\4\4\1\123\7\4\1\uffff\35\4\1\uffff\12\4\4\uffff\3\4\3\uffff"+
            "\5\4\2\uffff\72\4\1\uffff\12\4\1\uffff\16\4",
            "\4\4\1\162\7\4\1\uffff\35\4\1\uffff\12\4\4\uffff\3\4\3\uffff"+
            "\5\4\2\uffff\72\4\1\uffff\12\4\1\uffff\16\4",
            "\4\4\1\u0091\7\4\1\uffff\35\4\1\uffff\12\4\4\uffff\3\4\3\uffff"+
            "\5\4\2\uffff\72\4\1\uffff\12\4\1\uffff\16\4",
            "\1\u00b1\7\uffff\1\4",
            "",
            "",
            "",
            "",
            "\3\4\1\uffff\1\u00b2\6\4\1\uffff\1\4\4\uffff\3\4\20\uffff\2"+
            "\4\4\uffff\32\4\2\uffff\72\4\1\uffff\12\4\1\uffff\16\4",
            "\1\u00c7\7\uffff\1\4",
            "\1\u00c8",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\3\4\2\uffff\2\4\1\uffff\3\4\6\uffff\3\4\22\uffff\1\u00c9\4"+
            "\uffff\12\4\4\uffff\3\4\3\uffff\5\4\2\uffff\72\4\1\uffff\12"+
            "\4\1\uffff\16\4",
            "\3\4\2\uffff\2\4\1\uffff\3\4\6\uffff\3\4\22\uffff\1\u00c9\4"+
            "\uffff\12\4\4\uffff\3\4\3\uffff\5\4\2\uffff\72\4\1\uffff\12"+
            "\4\1\uffff\16\4",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\3\4\2\uffff\2\4\1\uffff\3\4\6\uffff\3\4\22\uffff\1\u00c9\4"+
            "\uffff\12\4\4\uffff\3\4\3\uffff\5\4\2\uffff\72\4\1\uffff\12"+
            "\4\1\uffff\16\4",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\3\4\2\uffff\2\4\1\uffff\3\4\6\uffff\3\4\22\uffff\1\u00c9\4"+
            "\uffff\12\4\4\uffff\3\4\3\uffff\5\4\2\uffff\72\4\1\uffff\12"+
            "\4\1\uffff\16\4",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\3\4\2\uffff\2\4\1\uffff\3\4\6\uffff\3\4\22\uffff\1\u00c9\4"+
            "\uffff\12\4\4\uffff\3\4\3\uffff\5\4\2\uffff\72\4\1\uffff\12"+
            "\4\1\uffff\16\4",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\3\4\2\uffff\2\4\1\uffff\3\4\6\uffff\3\4\22\uffff\1\u00c9\4"+
            "\uffff\12\4\4\uffff\3\4\3\uffff\5\4\2\uffff\72\4\1\uffff\12"+
            "\4\1\uffff\16\4",
            "\3\4\2\uffff\2\4\1\uffff\3\4\6\uffff\3\4\22\uffff\1\u00c9\4"+
            "\uffff\12\4\4\uffff\3\4\3\uffff\5\4\2\uffff\72\4\1\uffff\12"+
            "\4\1\uffff\16\4",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\3\4\2\uffff\2\4\1\uffff\3\4\6\uffff\3\4\22\uffff\1\u00c9\4"+
            "\uffff\12\4\4\uffff\3\4\3\uffff\5\4\2\uffff\72\4\1\uffff\12"+
            "\4\1\uffff\16\4",
            "\3\4\2\uffff\2\4\1\uffff\3\4\6\uffff\3\4\22\uffff\1\u00c9\4"+
            "\uffff\12\4\4\uffff\3\4\3\uffff\5\4\2\uffff\72\4\1\uffff\12"+
            "\4\1\uffff\16\4",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA73_eot = DFA.unpackEncodedString(DFA73_eotS);
    static final short[] DFA73_eof = DFA.unpackEncodedString(DFA73_eofS);
    static final char[] DFA73_min = DFA.unpackEncodedStringToUnsignedChars(DFA73_minS);
    static final char[] DFA73_max = DFA.unpackEncodedStringToUnsignedChars(DFA73_maxS);
    static final short[] DFA73_accept = DFA.unpackEncodedString(DFA73_acceptS);
    static final short[] DFA73_special = DFA.unpackEncodedString(DFA73_specialS);
    static final short[][] DFA73_transition;

    static {
        int numStates = DFA73_transitionS.length;
        DFA73_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA73_transition[i] = DFA.unpackEncodedString(DFA73_transitionS[i]);
        }
    }

    class DFA73 extends DFA {

        public DFA73(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 73;
            this.eot = DFA73_eot;
            this.eof = DFA73_eof;
            this.min = DFA73_min;
            this.max = DFA73_max;
            this.accept = DFA73_accept;
            this.special = DFA73_special;
            this.transition = DFA73_transition;
        }
        public String getDescription() {
            return "272:1: result_column : ( ASTERISK | table_name= id DOT ASTERISK -> ^( ASTERISK $table_name) | expr ( ( AS )? column_alias= id )? -> ^( ALIAS expr ( $column_alias)? ) );";
        }
    }
    static final String DFA72_eotS =
        "\u00cb\uffff";
    static final String DFA72_eofS =
        "\u00cb\uffff";
    static final String DFA72_minS =
        "\1\40\2\uffff\1\40\1\uffff\1\40\6\43\4\uffff\2\40\6\44\25\uffff"+
        "\1\40\2\uffff\1\40\6\44\u0094\uffff";
    static final String DFA72_maxS =
        "\1\u00b9\2\uffff\1\u00b9\1\uffff\1\u00b9\6\172\4\uffff\2\u00b9\1"+
        "\46\3\167\1\46\1\125\25\uffff\1\u00b9\2\uffff\1\u00b9\1\46\3\167"+
        "\1\46\1\125\u0094\uffff";
    static final String DFA72_acceptS =
        "\1\uffff\1\1\2\uffff\1\2\u00c6\uffff";
    static final String DFA72_specialS =
        "\u00cb\uffff}>";
    static final String[] DFA72_transitionS = {
            "\3\1\1\4\1\uffff\2\1\1\uffff\3\1\2\uffff\2\4\2\uffff\3\1\27"+
            "\uffff\12\1\4\uffff\3\1\3\uffff\5\1\2\uffff\12\1\1\12\1\13\1"+
            "\1\1\7\1\1\1\10\1\11\1\1\1\3\1\5\1\6\45\1\1\uffff\12\1\1\uffff"+
            "\16\1",
            "",
            "",
            "\3\4\1\1\1\uffff\2\4\1\uffff\3\4\1\uffff\1\4\2\1\2\uffff\3"+
            "\4\27\uffff\12\4\4\uffff\3\4\3\uffff\5\4\2\uffff\12\4\1\26\1"+
            "\27\1\4\1\23\1\4\1\24\1\25\1\4\1\20\1\21\1\22\45\4\1\uffff\12"+
            "\4\1\uffff\16\4",
            "",
            "\3\4\1\1\1\uffff\6\4\1\uffff\1\4\2\1\2\uffff\3\4\20\uffff\2"+
            "\4\4\uffff\32\4\2\uffff\12\4\1\65\1\66\1\4\1\62\1\4\1\63\1\64"+
            "\1\4\1\55\1\60\1\61\45\4\1\uffff\12\4\1\uffff\16\4",
            "\1\1\2\uffff\1\4\6\uffff\2\1\101\uffff\2\1\1\uffff\1\1\1\uffff"+
            "\2\1\1\uffff\3\1",
            "\1\1\11\uffff\2\1\101\uffff\2\1\1\uffff\1\1\1\4\2\1\1\4\3\1",
            "\1\1\11\uffff\2\1\101\uffff\2\1\1\uffff\1\1\1\uffff\2\1\1\4"+
            "\3\1",
            "\1\1\11\uffff\2\1\101\uffff\2\1\1\uffff\1\1\1\uffff\2\1\1\4"+
            "\3\1",
            "\1\1\2\uffff\1\4\6\uffff\2\1\101\uffff\2\1\1\uffff\1\1\1\uffff"+
            "\2\1\1\uffff\3\1",
            "\1\1\11\uffff\2\1\46\uffff\1\4\32\uffff\2\1\1\uffff\1\1\1\uffff"+
            "\2\1\1\uffff\3\1",
            "",
            "",
            "",
            "",
            "\3\1\1\uffff\1\4\2\1\1\uffff\3\1\1\uffff\1\1\4\uffff\3\1\27"+
            "\uffff\12\1\4\uffff\3\1\3\uffff\5\1\2\uffff\72\1\1\uffff\12"+
            "\1\1\uffff\16\1",
            "\3\1\1\uffff\1\4\6\1\1\uffff\1\1\4\uffff\3\1\20\uffff\2\1\4"+
            "\uffff\32\1\2\uffff\72\1\1\uffff\12\1\1\uffff\16\1",
            "\1\4\1\uffff\1\1",
            "\1\4\117\uffff\1\1\2\uffff\1\1",
            "\1\4\122\uffff\1\1",
            "\1\4\122\uffff\1\1",
            "\1\4\1\uffff\1\1",
            "\1\4\60\uffff\1\1",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\3\1\1\uffff\1\4\2\1\1\uffff\3\1\1\uffff\1\1\4\uffff\3\1\27"+
            "\uffff\12\1\4\uffff\3\1\3\uffff\5\1\2\uffff\72\1\1\uffff\12"+
            "\1\1\uffff\16\1",
            "",
            "",
            "\3\1\1\uffff\1\4\6\1\1\uffff\1\1\4\uffff\3\1\20\uffff\2\1\4"+
            "\uffff\32\1\2\uffff\72\1\1\uffff\12\1\1\uffff\16\1",
            "\1\4\1\uffff\1\1",
            "\1\4\117\uffff\1\1\2\uffff\1\1",
            "\1\4\122\uffff\1\1",
            "\1\4\122\uffff\1\1",
            "\1\4\1\uffff\1\1",
            "\1\4\60\uffff\1\1",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA72_eot = DFA.unpackEncodedString(DFA72_eotS);
    static final short[] DFA72_eof = DFA.unpackEncodedString(DFA72_eofS);
    static final char[] DFA72_min = DFA.unpackEncodedStringToUnsignedChars(DFA72_minS);
    static final char[] DFA72_max = DFA.unpackEncodedStringToUnsignedChars(DFA72_maxS);
    static final short[] DFA72_accept = DFA.unpackEncodedString(DFA72_acceptS);
    static final short[] DFA72_special = DFA.unpackEncodedString(DFA72_specialS);
    static final short[][] DFA72_transition;

    static {
        int numStates = DFA72_transitionS.length;
        DFA72_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA72_transition[i] = DFA.unpackEncodedString(DFA72_transitionS[i]);
        }
    }

    class DFA72 extends DFA {

        public DFA72(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 72;
            this.eot = DFA72_eot;
            this.eof = DFA72_eof;
            this.min = DFA72_min;
            this.max = DFA72_max;
            this.accept = DFA72_accept;
            this.special = DFA72_special;
            this.transition = DFA72_transition;
        }
        public String getDescription() {
            return "275:10: ( ( AS )? column_alias= id )?";
        }
    }
    static final String DFA71_eotS =
        "\u00cd\uffff";
    static final String DFA71_eofS =
        "\u00cd\uffff";
    static final String DFA71_minS =
        "\2\40\3\uffff\1\40\1\uffff\1\40\6\43\4\uffff\2\40\6\44\25\uffff"+
        "\1\40\2\uffff\1\40\6\44\u0094\uffff";
    static final String DFA71_maxS =
        "\2\u00b9\3\uffff\1\u00b9\1\uffff\1\u00b9\6\172\4\uffff\2\u00b9\1"+
        "\46\3\167\1\46\1\125\25\uffff\1\u00b9\2\uffff\1\u00b9\1\46\3\167"+
        "\1\46\1\125\u0094\uffff";
    static final String DFA71_acceptS =
        "\2\uffff\1\2\1\uffff\1\1\u00c8\uffff";
    static final String DFA71_specialS =
        "\u00cd\uffff}>";
    static final String[] DFA71_transitionS = {
            "\3\2\2\uffff\2\2\1\uffff\3\2\6\uffff\3\2\27\uffff\4\2\1\1\5"+
            "\2\4\uffff\3\2\3\uffff\5\2\2\uffff\72\2\1\uffff\12\2\1\uffff"+
            "\16\2",
            "\3\4\1\2\1\uffff\2\4\1\uffff\3\4\2\uffff\2\2\2\uffff\3\4\27"+
            "\uffff\12\4\4\uffff\3\4\3\uffff\5\4\2\uffff\12\4\1\14\1\15\1"+
            "\4\1\11\1\4\1\12\1\13\1\4\1\5\1\7\1\10\45\4\1\uffff\12\4\1\uffff"+
            "\16\4",
            "",
            "",
            "",
            "\3\2\1\4\1\uffff\2\2\1\uffff\3\2\1\uffff\1\2\2\4\2\uffff\3"+
            "\2\27\uffff\12\2\4\uffff\3\2\3\uffff\5\2\2\uffff\12\2\1\30\1"+
            "\31\1\2\1\25\1\2\1\26\1\27\1\2\1\22\1\23\1\24\45\2\1\uffff\12"+
            "\2\1\uffff\16\2",
            "",
            "\3\2\1\4\1\uffff\6\2\1\uffff\1\2\2\4\2\uffff\3\2\20\uffff\2"+
            "\2\4\uffff\32\2\2\uffff\12\2\1\67\1\70\1\2\1\64\1\2\1\65\1\66"+
            "\1\2\1\57\1\62\1\63\45\2\1\uffff\12\2\1\uffff\16\2",
            "\1\4\2\uffff\1\2\6\uffff\2\4\101\uffff\2\4\1\uffff\1\4\1\uffff"+
            "\2\4\1\uffff\3\4",
            "\1\4\11\uffff\2\4\101\uffff\2\4\1\uffff\1\4\1\2\2\4\1\2\3\4",
            "\1\4\11\uffff\2\4\101\uffff\2\4\1\uffff\1\4\1\uffff\2\4\1\2"+
            "\3\4",
            "\1\4\11\uffff\2\4\101\uffff\2\4\1\uffff\1\4\1\uffff\2\4\1\2"+
            "\3\4",
            "\1\4\2\uffff\1\2\6\uffff\2\4\101\uffff\2\4\1\uffff\1\4\1\uffff"+
            "\2\4\1\uffff\3\4",
            "\1\4\11\uffff\2\4\46\uffff\1\2\32\uffff\2\4\1\uffff\1\4\1\uffff"+
            "\2\4\1\uffff\3\4",
            "",
            "",
            "",
            "",
            "\3\4\1\uffff\1\2\2\4\1\uffff\3\4\1\uffff\1\4\4\uffff\3\4\27"+
            "\uffff\12\4\4\uffff\3\4\3\uffff\5\4\2\uffff\72\4\1\uffff\12"+
            "\4\1\uffff\16\4",
            "\3\4\1\uffff\1\2\6\4\1\uffff\1\4\4\uffff\3\4\20\uffff\2\4\4"+
            "\uffff\32\4\2\uffff\72\4\1\uffff\12\4\1\uffff\16\4",
            "\1\2\1\uffff\1\4",
            "\1\2\117\uffff\1\4\2\uffff\1\4",
            "\1\2\122\uffff\1\4",
            "\1\2\122\uffff\1\4",
            "\1\2\1\uffff\1\4",
            "\1\2\60\uffff\1\4",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\3\4\1\uffff\1\2\2\4\1\uffff\3\4\1\uffff\1\4\4\uffff\3\4\27"+
            "\uffff\12\4\4\uffff\3\4\3\uffff\5\4\2\uffff\72\4\1\uffff\12"+
            "\4\1\uffff\16\4",
            "",
            "",
            "\3\4\1\uffff\1\2\6\4\1\uffff\1\4\4\uffff\3\4\20\uffff\2\4\4"+
            "\uffff\32\4\2\uffff\72\4\1\uffff\12\4\1\uffff\16\4",
            "\1\2\1\uffff\1\4",
            "\1\2\117\uffff\1\4\2\uffff\1\4",
            "\1\2\122\uffff\1\4",
            "\1\2\122\uffff\1\4",
            "\1\2\1\uffff\1\4",
            "\1\2\60\uffff\1\4",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA71_eot = DFA.unpackEncodedString(DFA71_eotS);
    static final short[] DFA71_eof = DFA.unpackEncodedString(DFA71_eofS);
    static final char[] DFA71_min = DFA.unpackEncodedStringToUnsignedChars(DFA71_minS);
    static final char[] DFA71_max = DFA.unpackEncodedStringToUnsignedChars(DFA71_maxS);
    static final short[] DFA71_accept = DFA.unpackEncodedString(DFA71_acceptS);
    static final short[] DFA71_special = DFA.unpackEncodedString(DFA71_specialS);
    static final short[][] DFA71_transition;

    static {
        int numStates = DFA71_transitionS.length;
        DFA71_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA71_transition[i] = DFA.unpackEncodedString(DFA71_transitionS[i]);
        }
    }

    class DFA71 extends DFA {

        public DFA71(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 71;
            this.eot = DFA71_eot;
            this.eof = DFA71_eof;
            this.min = DFA71_min;
            this.max = DFA71_max;
            this.accept = DFA71_accept;
            this.special = DFA71_special;
            this.transition = DFA71_transition;
        }
        public String getDescription() {
            return "275:11: ( AS )?";
        }
    }
    static final String DFA75_eotS =
        "\21\uffff";
    static final String DFA75_eofS =
        "\21\uffff";
    static final String DFA75_minS =
        "\1\43\20\uffff";
    static final String DFA75_maxS =
        "\1\u0081\20\uffff";
    static final String DFA75_acceptS =
        "\1\uffff\1\2\10\uffff\1\1\6\uffff";
    static final String DFA75_specialS =
        "\21\uffff}>";
    static final String[] DFA75_transitionS = {
            "\1\1\11\uffff\1\12\1\1\101\uffff\2\1\1\uffff\1\1\1\uffff\2\1"+
            "\2\uffff\2\1\1\uffff\6\12",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA75_eot = DFA.unpackEncodedString(DFA75_eotS);
    static final short[] DFA75_eof = DFA.unpackEncodedString(DFA75_eofS);
    static final char[] DFA75_min = DFA.unpackEncodedStringToUnsignedChars(DFA75_minS);
    static final char[] DFA75_max = DFA.unpackEncodedStringToUnsignedChars(DFA75_maxS);
    static final short[] DFA75_accept = DFA.unpackEncodedString(DFA75_acceptS);
    static final short[] DFA75_special = DFA.unpackEncodedString(DFA75_specialS);
    static final short[][] DFA75_transition;

    static {
        int numStates = DFA75_transitionS.length;
        DFA75_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA75_transition[i] = DFA.unpackEncodedString(DFA75_transitionS[i]);
        }
    }

    class DFA75 extends DFA {

        public DFA75(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 75;
            this.eot = DFA75_eot;
            this.eof = DFA75_eof;
            this.min = DFA75_min;
            this.max = DFA75_max;
            this.accept = DFA75_accept;
            this.special = DFA75_special;
            this.transition = DFA75_transition;
        }
        public String getDescription() {
            return "()* loopback of 277:28: ( join_op single_source ( join_constraint )? )*";
        }
    }
    static final String DFA74_eotS =
        "\23\uffff";
    static final String DFA74_eofS =
        "\23\uffff";
    static final String DFA74_minS =
        "\1\43\22\uffff";
    static final String DFA74_maxS =
        "\1\u0083\22\uffff";
    static final String DFA74_acceptS =
        "\1\uffff\1\1\1\uffff\1\2\17\uffff";
    static final String DFA74_specialS =
        "\23\uffff}>";
    static final String[] DFA74_transitionS = {
            "\1\3\11\uffff\2\3\101\uffff\2\3\1\uffff\1\3\1\uffff\2\3\2\uffff"+
            "\2\3\1\uffff\6\3\2\1",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA74_eot = DFA.unpackEncodedString(DFA74_eotS);
    static final short[] DFA74_eof = DFA.unpackEncodedString(DFA74_eofS);
    static final char[] DFA74_min = DFA.unpackEncodedStringToUnsignedChars(DFA74_minS);
    static final char[] DFA74_max = DFA.unpackEncodedStringToUnsignedChars(DFA74_maxS);
    static final short[] DFA74_accept = DFA.unpackEncodedString(DFA74_acceptS);
    static final short[] DFA74_special = DFA.unpackEncodedString(DFA74_specialS);
    static final short[][] DFA74_transition;

    static {
        int numStates = DFA74_transitionS.length;
        DFA74_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA74_transition[i] = DFA.unpackEncodedString(DFA74_transitionS[i]);
        }
    }

    class DFA74 extends DFA {

        public DFA74(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 74;
            this.eot = DFA74_eot;
            this.eof = DFA74_eof;
            this.min = DFA74_min;
            this.max = DFA74_max;
            this.accept = DFA74_accept;
            this.special = DFA74_special;
            this.transition = DFA74_transition;
        }
        public String getDescription() {
            return "277:52: ( join_constraint )?";
        }
    }
    static final String DFA82_eotS =
        "\36\uffff";
    static final String DFA82_eofS =
        "\36\uffff";
    static final String DFA82_minS =
        "\1\40\2\uffff\1\40\1\uffff\1\40\30\uffff";
    static final String DFA82_maxS =
        "\1\u00b9\2\uffff\1\u00b9\1\uffff\1\u00b9\30\uffff";
    static final String DFA82_acceptS =
        "\1\uffff\1\1\2\uffff\1\3\3\uffff\1\2\25\uffff";
    static final String DFA82_specialS =
        "\36\uffff}>";
    static final String[] DFA82_transitionS = {
            "\3\1\2\uffff\2\1\1\uffff\3\1\1\uffff\1\3\4\uffff\3\1\27\uffff"+
            "\12\1\4\uffff\3\1\3\uffff\5\1\2\uffff\72\1\1\uffff\12\1\1\uffff"+
            "\16\1",
            "",
            "",
            "\3\4\2\uffff\2\4\1\uffff\3\4\1\uffff\1\4\4\uffff\3\4\27\uffff"+
            "\12\4\4\uffff\3\4\3\uffff\5\4\2\uffff\21\4\1\5\50\4\1\uffff"+
            "\12\4\1\uffff\16\4",
            "",
            "\3\10\1\uffff\1\4\6\10\1\uffff\1\10\4\uffff\3\10\20\uffff\3"+
            "\10\3\uffff\32\10\2\uffff\72\10\1\uffff\12\10\1\uffff\16\10",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA82_eot = DFA.unpackEncodedString(DFA82_eotS);
    static final short[] DFA82_eof = DFA.unpackEncodedString(DFA82_eofS);
    static final char[] DFA82_min = DFA.unpackEncodedStringToUnsignedChars(DFA82_minS);
    static final char[] DFA82_max = DFA.unpackEncodedStringToUnsignedChars(DFA82_maxS);
    static final short[] DFA82_accept = DFA.unpackEncodedString(DFA82_acceptS);
    static final short[] DFA82_special = DFA.unpackEncodedString(DFA82_specialS);
    static final short[][] DFA82_transition;

    static {
        int numStates = DFA82_transitionS.length;
        DFA82_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA82_transition[i] = DFA.unpackEncodedString(DFA82_transitionS[i]);
        }
    }

    class DFA82 extends DFA {

        public DFA82(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 82;
            this.eot = DFA82_eot;
            this.eof = DFA82_eof;
            this.min = DFA82_min;
            this.max = DFA82_max;
            this.accept = DFA82_accept;
            this.special = DFA82_special;
            this.transition = DFA82_transition;
        }
        public String getDescription() {
            return "279:1: single_source : ( (database_name= id DOT )? table_name= ID ( ( AS )? table_alias= ID )? ( INDEXED BY index_name= id | NOT INDEXED )? -> ^( ALIAS ^( $table_name ( $database_name)? ) ( $table_alias)? ( ^( INDEXED ( NOT )? ( $index_name)? ) )? ) | LPAREN select_stmt RPAREN ( ( AS )? table_alias= ID )? -> ^( ALIAS select_stmt ( $table_alias)? ) | LPAREN join_source RPAREN );";
        }
    }
    static final String DFA76_eotS =
        "\32\uffff";
    static final String DFA76_eofS =
        "\32\uffff";
    static final String DFA76_minS =
        "\1\40\1\43\30\uffff";
    static final String DFA76_maxS =
        "\1\u00b9\1\u0083\30\uffff";
    static final String DFA76_acceptS =
        "\2\uffff\1\1\1\2\26\uffff";
    static final String DFA76_specialS =
        "\32\uffff}>";
    static final String[] DFA76_transitionS = {
            "\3\2\2\uffff\2\2\1\uffff\3\2\6\uffff\3\2\27\uffff\1\2\1\1\10"+
            "\2\4\uffff\3\2\3\uffff\5\2\2\uffff\72\2\1\uffff\12\2\1\uffff"+
            "\16\2",
            "\1\3\1\2\1\3\1\uffff\1\3\5\uffff\2\3\35\uffff\1\3\2\uffff\1"+
            "\3\40\uffff\2\3\1\uffff\1\3\1\uffff\2\3\2\uffff\2\3\1\uffff"+
            "\10\3",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA76_eot = DFA.unpackEncodedString(DFA76_eotS);
    static final short[] DFA76_eof = DFA.unpackEncodedString(DFA76_eofS);
    static final char[] DFA76_min = DFA.unpackEncodedStringToUnsignedChars(DFA76_minS);
    static final char[] DFA76_max = DFA.unpackEncodedStringToUnsignedChars(DFA76_maxS);
    static final short[] DFA76_accept = DFA.unpackEncodedString(DFA76_acceptS);
    static final short[] DFA76_special = DFA.unpackEncodedString(DFA76_specialS);
    static final short[][] DFA76_transition;

    static {
        int numStates = DFA76_transitionS.length;
        DFA76_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA76_transition[i] = DFA.unpackEncodedString(DFA76_transitionS[i]);
        }
    }

    class DFA76 extends DFA {

        public DFA76(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 76;
            this.eot = DFA76_eot;
            this.eof = DFA76_eof;
            this.min = DFA76_min;
            this.max = DFA76_max;
            this.accept = DFA76_accept;
            this.special = DFA76_special;
            this.transition = DFA76_transition;
        }
        public String getDescription() {
            return "280:5: (database_name= id DOT )?";
        }
    }
    static final String DFA78_eotS =
        "\27\uffff";
    static final String DFA78_eofS =
        "\27\uffff";
    static final String DFA78_minS =
        "\1\43\26\uffff";
    static final String DFA78_maxS =
        "\1\u0083\26\uffff";
    static final String DFA78_acceptS =
        "\1\uffff\1\1\1\uffff\1\2\23\uffff";
    static final String DFA78_specialS =
        "\27\uffff}>";
    static final String[] DFA78_transitionS = {
            "\1\3\1\uffff\1\3\1\uffff\1\3\5\uffff\2\3\35\uffff\1\1\2\uffff"+
            "\1\1\40\uffff\2\3\1\uffff\1\3\1\uffff\2\3\2\uffff\2\3\1\uffff"+
            "\10\3",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA78_eot = DFA.unpackEncodedString(DFA78_eotS);
    static final short[] DFA78_eof = DFA.unpackEncodedString(DFA78_eofS);
    static final char[] DFA78_min = DFA.unpackEncodedStringToUnsignedChars(DFA78_minS);
    static final char[] DFA78_max = DFA.unpackEncodedStringToUnsignedChars(DFA78_maxS);
    static final short[] DFA78_accept = DFA.unpackEncodedString(DFA78_acceptS);
    static final short[] DFA78_special = DFA.unpackEncodedString(DFA78_specialS);
    static final short[][] DFA78_transition;

    static {
        int numStates = DFA78_transitionS.length;
        DFA78_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA78_transition[i] = DFA.unpackEncodedString(DFA78_transitionS[i]);
        }
    }

    class DFA78 extends DFA {

        public DFA78(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 78;
            this.eot = DFA78_eot;
            this.eof = DFA78_eof;
            this.min = DFA78_min;
            this.max = DFA78_max;
            this.accept = DFA78_accept;
            this.special = DFA78_special;
            this.transition = DFA78_transition;
        }
        public String getDescription() {
            return "280:43: ( ( AS )? table_alias= ID )?";
        }
    }
    static final String DFA79_eotS =
        "\25\uffff";
    static final String DFA79_eofS =
        "\25\uffff";
    static final String DFA79_minS =
        "\1\43\24\uffff";
    static final String DFA79_maxS =
        "\1\u0083\24\uffff";
    static final String DFA79_acceptS =
        "\1\uffff\1\1\1\2\1\3\21\uffff";
    static final String DFA79_specialS =
        "\25\uffff}>";
    static final String[] DFA79_transitionS = {
            "\1\3\1\uffff\1\1\1\uffff\1\2\5\uffff\2\3\101\uffff\2\3\1\uffff"+
            "\1\3\1\uffff\2\3\2\uffff\2\3\1\uffff\10\3",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA79_eot = DFA.unpackEncodedString(DFA79_eotS);
    static final short[] DFA79_eof = DFA.unpackEncodedString(DFA79_eofS);
    static final char[] DFA79_min = DFA.unpackEncodedStringToUnsignedChars(DFA79_minS);
    static final char[] DFA79_max = DFA.unpackEncodedStringToUnsignedChars(DFA79_maxS);
    static final short[] DFA79_accept = DFA.unpackEncodedString(DFA79_acceptS);
    static final short[] DFA79_special = DFA.unpackEncodedString(DFA79_specialS);
    static final short[][] DFA79_transition;

    static {
        int numStates = DFA79_transitionS.length;
        DFA79_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA79_transition[i] = DFA.unpackEncodedString(DFA79_transitionS[i]);
        }
    }

    class DFA79 extends DFA {

        public DFA79(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 79;
            this.eot = DFA79_eot;
            this.eof = DFA79_eof;
            this.min = DFA79_min;
            this.max = DFA79_max;
            this.accept = DFA79_accept;
            this.special = DFA79_special;
            this.transition = DFA79_transition;
        }
        public String getDescription() {
            return "280:67: ( INDEXED BY index_name= id | NOT INDEXED )?";
        }
    }
    static final String DFA81_eotS =
        "\25\uffff";
    static final String DFA81_eofS =
        "\25\uffff";
    static final String DFA81_minS =
        "\1\43\24\uffff";
    static final String DFA81_maxS =
        "\1\u0083\24\uffff";
    static final String DFA81_acceptS =
        "\1\uffff\1\1\1\uffff\1\2\21\uffff";
    static final String DFA81_specialS =
        "\25\uffff}>";
    static final String[] DFA81_transitionS = {
            "\1\3\11\uffff\2\3\35\uffff\1\1\2\uffff\1\1\40\uffff\2\3\1\uffff"+
            "\1\3\1\uffff\2\3\2\uffff\2\3\1\uffff\10\3",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA81_eot = DFA.unpackEncodedString(DFA81_eotS);
    static final short[] DFA81_eof = DFA.unpackEncodedString(DFA81_eofS);
    static final char[] DFA81_min = DFA.unpackEncodedStringToUnsignedChars(DFA81_minS);
    static final char[] DFA81_max = DFA.unpackEncodedStringToUnsignedChars(DFA81_maxS);
    static final short[] DFA81_accept = DFA.unpackEncodedString(DFA81_acceptS);
    static final short[] DFA81_special = DFA.unpackEncodedString(DFA81_specialS);
    static final short[][] DFA81_transition;

    static {
        int numStates = DFA81_transitionS.length;
        DFA81_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA81_transition[i] = DFA.unpackEncodedString(DFA81_transitionS[i]);
        }
    }

    class DFA81 extends DFA {

        public DFA81(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 81;
            this.eot = DFA81_eot;
            this.eof = DFA81_eof;
            this.min = DFA81_min;
            this.max = DFA81_max;
            this.accept = DFA81_accept;
            this.special = DFA81_special;
            this.transition = DFA81_transition;
        }
        public String getDescription() {
            return "282:31: ( ( AS )? table_alias= ID )?";
        }
    }
    static final String DFA92_eotS =
        "\15\uffff";
    static final String DFA92_eofS =
        "\15\uffff";
    static final String DFA92_minS =
        "\1\40\2\44\12\uffff";
    static final String DFA92_maxS =
        "\1\u00b9\2\u0087\12\uffff";
    static final String DFA92_acceptS =
        "\3\uffff\1\1\1\2\10\uffff";
    static final String DFA92_specialS =
        "\15\uffff}>";
    static final String[] DFA92_transitionS = {
            "\3\2\2\uffff\2\2\1\uffff\3\2\6\uffff\3\2\27\uffff\1\2\1\1\10"+
            "\2\4\uffff\3\2\3\uffff\5\2\2\uffff\72\2\1\uffff\12\2\1\uffff"+
            "\16\2",
            "\1\3\7\uffff\1\4\112\uffff\1\4\16\uffff\2\4",
            "\1\3\7\uffff\1\4\112\uffff\1\4\16\uffff\2\4",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA92_eot = DFA.unpackEncodedString(DFA92_eotS);
    static final short[] DFA92_eof = DFA.unpackEncodedString(DFA92_eofS);
    static final char[] DFA92_min = DFA.unpackEncodedStringToUnsignedChars(DFA92_minS);
    static final char[] DFA92_max = DFA.unpackEncodedStringToUnsignedChars(DFA92_maxS);
    static final short[] DFA92_accept = DFA.unpackEncodedString(DFA92_acceptS);
    static final short[] DFA92_special = DFA.unpackEncodedString(DFA92_specialS);
    static final short[][] DFA92_transition;

    static {
        int numStates = DFA92_transitionS.length;
        DFA92_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA92_transition[i] = DFA.unpackEncodedString(DFA92_transitionS[i]);
        }
    }

    class DFA92 extends DFA {

        public DFA92(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 92;
            this.eot = DFA92_eot;
            this.eof = DFA92_eof;
            this.min = DFA92_min;
            this.max = DFA92_max;
            this.accept = DFA92_accept;
            this.special = DFA92_special;
            this.transition = DFA92_transition;
        }
        public String getDescription() {
            return "295:67: (database_name= id DOT )?";
        }
    }
    static final String DFA117_eotS =
        "\76\uffff";
    static final String DFA117_eofS =
        "\76\uffff";
    static final String DFA117_minS =
        "\1\55\1\40\2\uffff\1\47\1\uffff\3\47\65\uffff";
    static final String DFA117_maxS =
        "\1\56\1\u00b9\2\uffff\1\u00a4\1\uffff\3\u00a4\65\uffff";
    static final String DFA117_acceptS =
        "\2\uffff\1\2\1\1\72\uffff";
    static final String DFA117_specialS =
        "\76\uffff}>";
    static final String[] DFA117_transitionS = {
            "\1\1\1\2",
            "\3\3\2\uffff\7\3\3\uffff\5\3\4\uffff\4\3\17\uffff\12\3\4\uffff"+
            "\3\3\3\uffff\5\3\2\uffff\65\3\1\2\1\4\3\3\1\uffff\1\6\1\7\1"+
            "\10\7\3\1\uffff\2\3\1\uffff\13\3",
            "",
            "",
            "\1\3\5\uffff\2\3\3\uffff\1\3\30\uffff\2\3\72\uffff\1\3\23\uffff"+
            "\2\3\1\2\3\uffff\2\3\1\uffff\1\3",
            "",
            "\1\3\4\uffff\1\2\2\3\3\uffff\1\3\30\uffff\2\3\72\uffff\1\3"+
            "\23\uffff\2\3\4\uffff\2\3\1\uffff\1\3",
            "\1\3\4\uffff\1\2\2\3\3\uffff\1\3\30\uffff\2\3\72\uffff\1\3"+
            "\23\uffff\2\3\4\uffff\2\3\1\uffff\1\3",
            "\1\3\5\uffff\2\3\3\uffff\1\3\30\uffff\2\3\72\uffff\1\3\23\uffff"+
            "\2\3\1\2\3\uffff\2\3\1\uffff\1\3",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA117_eot = DFA.unpackEncodedString(DFA117_eotS);
    static final short[] DFA117_eof = DFA.unpackEncodedString(DFA117_eofS);
    static final char[] DFA117_min = DFA.unpackEncodedStringToUnsignedChars(DFA117_minS);
    static final char[] DFA117_max = DFA.unpackEncodedStringToUnsignedChars(DFA117_maxS);
    static final short[] DFA117_accept = DFA.unpackEncodedString(DFA117_acceptS);
    static final short[] DFA117_special = DFA.unpackEncodedString(DFA117_specialS);
    static final short[][] DFA117_transition;

    static {
        int numStates = DFA117_transitionS.length;
        DFA117_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA117_transition[i] = DFA.unpackEncodedString(DFA117_transitionS[i]);
        }
    }

    class DFA117 extends DFA {

        public DFA117(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 117;
            this.eot = DFA117_eot;
            this.eof = DFA117_eof;
            this.min = DFA117_min;
            this.max = DFA117_max;
            this.accept = DFA117_accept;
            this.special = DFA117_special;
            this.transition = DFA117_transition;
        }
        public String getDescription() {
            return "()* loopback of 338:23: ( COMMA column_def )*";
        }
    }
    static final String DFA120_eotS =
        "\16\uffff";
    static final String DFA120_eofS =
        "\16\uffff";
    static final String DFA120_minS =
        "\1\43\15\uffff";
    static final String DFA120_maxS =
        "\1\u00a4\15\uffff";
    static final String DFA120_acceptS =
        "\1\uffff\1\1\1\2\13\uffff";
    static final String DFA120_specialS =
        "\16\uffff}>";
    static final String[] DFA120_transitionS = {
            "\1\2\3\uffff\1\2\5\uffff\2\2\3\uffff\1\2\30\uffff\1\2\1\1\72"+
            "\uffff\1\2\23\uffff\2\2\4\uffff\2\2\1\uffff\1\2",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA120_eot = DFA.unpackEncodedString(DFA120_eotS);
    static final short[] DFA120_eof = DFA.unpackEncodedString(DFA120_eofS);
    static final char[] DFA120_min = DFA.unpackEncodedStringToUnsignedChars(DFA120_minS);
    static final char[] DFA120_max = DFA.unpackEncodedStringToUnsignedChars(DFA120_maxS);
    static final short[] DFA120_accept = DFA.unpackEncodedString(DFA120_acceptS);
    static final short[] DFA120_special = DFA.unpackEncodedString(DFA120_specialS);
    static final short[][] DFA120_transition;

    static {
        int numStates = DFA120_transitionS.length;
        DFA120_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA120_transition[i] = DFA.unpackEncodedString(DFA120_transitionS[i]);
        }
    }

    class DFA120 extends DFA {

        public DFA120(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 120;
            this.eot = DFA120_eot;
            this.eof = DFA120_eof;
            this.min = DFA120_min;
            this.max = DFA120_max;
            this.accept = DFA120_accept;
            this.special = DFA120_special;
            this.transition = DFA120_transition;
        }
        public String getDescription() {
            return "343:99: ( type_name )?";
        }
    }
    static final String DFA121_eotS =
        "\15\uffff";
    static final String DFA121_eofS =
        "\15\uffff";
    static final String DFA121_minS =
        "\1\43\14\uffff";
    static final String DFA121_maxS =
        "\1\u00a4\14\uffff";
    static final String DFA121_acceptS =
        "\1\uffff\1\2\2\uffff\1\1\10\uffff";
    static final String DFA121_specialS =
        "\15\uffff}>";
    static final String[] DFA121_transitionS = {
            "\1\1\3\uffff\1\4\5\uffff\2\1\3\uffff\1\4\30\uffff\1\4\73\uffff"+
            "\1\4\23\uffff\2\4\4\uffff\2\4\1\uffff\1\4",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA121_eot = DFA.unpackEncodedString(DFA121_eotS);
    static final short[] DFA121_eof = DFA.unpackEncodedString(DFA121_eofS);
    static final char[] DFA121_min = DFA.unpackEncodedStringToUnsignedChars(DFA121_minS);
    static final char[] DFA121_max = DFA.unpackEncodedStringToUnsignedChars(DFA121_maxS);
    static final short[] DFA121_accept = DFA.unpackEncodedString(DFA121_acceptS);
    static final short[] DFA121_special = DFA.unpackEncodedString(DFA121_specialS);
    static final short[][] DFA121_transition;

    static {
        int numStates = DFA121_transitionS.length;
        DFA121_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA121_transition[i] = DFA.unpackEncodedString(DFA121_transitionS[i]);
        }
    }

    class DFA121 extends DFA {

        public DFA121(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 121;
            this.eot = DFA121_eot;
            this.eof = DFA121_eof;
            this.min = DFA121_min;
            this.max = DFA121_max;
            this.accept = DFA121_accept;
            this.special = DFA121_special;
            this.transition = DFA121_transition;
        }
        public String getDescription() {
            return "()* loopback of 343:111: ( column_constraint )*";
        }
    }
    static final String DFA122_eotS =
        "\12\uffff";
    static final String DFA122_eofS =
        "\12\uffff";
    static final String DFA122_minS =
        "\1\47\11\uffff";
    static final String DFA122_maxS =
        "\1\u00a4\11\uffff";
    static final String DFA122_acceptS =
        "\1\uffff\1\1\1\2\7\uffff";
    static final String DFA122_specialS =
        "\12\uffff}>";
    static final String[] DFA122_transitionS = {
            "\1\2\12\uffff\1\2\30\uffff\1\2\73\uffff\1\2\23\uffff\1\1\1\2"+
            "\4\uffff\2\2\1\uffff\1\2",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA122_eot = DFA.unpackEncodedString(DFA122_eotS);
    static final short[] DFA122_eof = DFA.unpackEncodedString(DFA122_eofS);
    static final char[] DFA122_min = DFA.unpackEncodedStringToUnsignedChars(DFA122_minS);
    static final char[] DFA122_max = DFA.unpackEncodedStringToUnsignedChars(DFA122_maxS);
    static final short[] DFA122_accept = DFA.unpackEncodedString(DFA122_acceptS);
    static final short[] DFA122_special = DFA.unpackEncodedString(DFA122_specialS);
    static final short[][] DFA122_transition;

    static {
        int numStates = DFA122_transitionS.length;
        DFA122_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA122_transition[i] = DFA.unpackEncodedString(DFA122_transitionS[i]);
        }
    }

    class DFA122 extends DFA {

        public DFA122(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 122;
            this.eot = DFA122_eot;
            this.eof = DFA122_eof;
            this.min = DFA122_min;
            this.max = DFA122_max;
            this.accept = DFA122_accept;
            this.special = DFA122_special;
            this.transition = DFA122_transition;
        }
        public String getDescription() {
            return "346:20: ( CONSTRAINT name= id )?";
        }
    }
    static final String DFA123_eotS =
        "\13\uffff";
    static final String DFA123_eofS =
        "\13\uffff";
    static final String DFA123_minS =
        "\1\47\2\uffff\1\62\7\uffff";
    static final String DFA123_maxS =
        "\1\u00a4\2\uffff\1\u009f\7\uffff";
    static final String DFA123_acceptS =
        "\1\uffff\1\1\1\2\1\uffff\1\5\1\6\1\7\1\10\1\11\1\3\1\4";
    static final String DFA123_specialS =
        "\13\uffff}>";
    static final String[] DFA123_transitionS = {
            "\1\3\12\uffff\1\2\30\uffff\1\7\73\uffff\1\6\24\uffff\1\1\4\uffff"+
            "\1\4\1\5\1\uffff\1\10",
            "",
            "",
            "\1\11\154\uffff\1\12",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA123_eot = DFA.unpackEncodedString(DFA123_eotS);
    static final short[] DFA123_eof = DFA.unpackEncodedString(DFA123_eofS);
    static final char[] DFA123_min = DFA.unpackEncodedStringToUnsignedChars(DFA123_minS);
    static final char[] DFA123_max = DFA.unpackEncodedStringToUnsignedChars(DFA123_maxS);
    static final short[] DFA123_accept = DFA.unpackEncodedString(DFA123_acceptS);
    static final short[] DFA123_special = DFA.unpackEncodedString(DFA123_specialS);
    static final short[][] DFA123_transition;

    static {
        int numStates = DFA123_transitionS.length;
        DFA123_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA123_transition[i] = DFA.unpackEncodedString(DFA123_transitionS[i]);
        }
    }

    class DFA123 extends DFA {

        public DFA123(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 123;
            this.eot = DFA123_eot;
            this.eof = DFA123_eof;
            this.min = DFA123_min;
            this.max = DFA123_max;
            this.accept = DFA123_accept;
            this.special = DFA123_special;
            this.transition = DFA123_transition;
        }
        public String getDescription() {
            return "347:3: ( column_constraint_pk | column_constraint_null | column_constraint_not_null | column_constraint_not_for_replication | column_constraint_unique | column_constraint_check | column_constraint_default | column_constraint_collate | fk_clause )";
        }
    }
    static final String DFA124_eotS =
        "\20\uffff";
    static final String DFA124_eofS =
        "\20\uffff";
    static final String DFA124_minS =
        "\1\43\17\uffff";
    static final String DFA124_maxS =
        "\1\u00a4\17\uffff";
    static final String DFA124_acceptS =
        "\1\uffff\1\1\1\2\15\uffff";
    static final String DFA124_specialS =
        "\20\uffff}>";
    static final String[] DFA124_transitionS = {
            "\1\2\3\uffff\1\2\5\uffff\2\2\3\uffff\1\2\30\uffff\1\2\42\uffff"+
            "\2\1\22\uffff\1\2\4\uffff\1\2\23\uffff\2\2\1\uffff\1\2\2\uffff"+
            "\2\2\1\uffff\1\2",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA124_eot = DFA.unpackEncodedString(DFA124_eotS);
    static final short[] DFA124_eof = DFA.unpackEncodedString(DFA124_eofS);
    static final char[] DFA124_min = DFA.unpackEncodedStringToUnsignedChars(DFA124_minS);
    static final char[] DFA124_max = DFA.unpackEncodedStringToUnsignedChars(DFA124_maxS);
    static final short[] DFA124_accept = DFA.unpackEncodedString(DFA124_acceptS);
    static final short[] DFA124_special = DFA.unpackEncodedString(DFA124_specialS);
    static final short[][] DFA124_transition;

    static {
        int numStates = DFA124_transitionS.length;
        DFA124_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA124_transition[i] = DFA.unpackEncodedString(DFA124_transitionS[i]);
        }
    }

    class DFA124 extends DFA {

        public DFA124(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 124;
            this.eot = DFA124_eot;
            this.eof = DFA124_eof;
            this.min = DFA124_min;
            this.max = DFA124_max;
            this.accept = DFA124_accept;
            this.special = DFA124_special;
            this.transition = DFA124_transition;
        }
        public String getDescription() {
            return "368:37: ( ASC | DESC )?";
        }
    }
    static final String DFA125_eotS =
        "\17\uffff";
    static final String DFA125_eofS =
        "\17\uffff";
    static final String DFA125_minS =
        "\1\43\16\uffff";
    static final String DFA125_maxS =
        "\1\u00a4\16\uffff";
    static final String DFA125_acceptS =
        "\1\uffff\1\1\1\2\14\uffff";
    static final String DFA125_specialS =
        "\17\uffff}>";
    static final String[] DFA125_transitionS = {
            "\1\2\3\uffff\1\2\5\uffff\2\2\3\uffff\1\2\30\uffff\1\2\66\uffff"+
            "\1\1\4\uffff\1\2\23\uffff\2\2\1\uffff\1\2\2\uffff\2\2\1\uffff"+
            "\1\2",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA125_eot = DFA.unpackEncodedString(DFA125_eotS);
    static final short[] DFA125_eof = DFA.unpackEncodedString(DFA125_eofS);
    static final char[] DFA125_min = DFA.unpackEncodedStringToUnsignedChars(DFA125_minS);
    static final char[] DFA125_max = DFA.unpackEncodedStringToUnsignedChars(DFA125_maxS);
    static final short[] DFA125_accept = DFA.unpackEncodedString(DFA125_acceptS);
    static final short[] DFA125_special = DFA.unpackEncodedString(DFA125_specialS);
    static final short[][] DFA125_transition;

    static {
        int numStates = DFA125_transitionS.length;
        DFA125_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA125_transition[i] = DFA.unpackEncodedString(DFA125_transitionS[i]);
        }
    }

    class DFA125 extends DFA {

        public DFA125(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 125;
            this.eot = DFA125_eot;
            this.eof = DFA125_eof;
            this.min = DFA125_min;
            this.max = DFA125_max;
            this.accept = DFA125_accept;
            this.special = DFA125_special;
            this.transition = DFA125_transition;
        }
        public String getDescription() {
            return "368:51: ( table_conflict_clause )?";
        }
    }
    static final String DFA126_eotS =
        "\16\uffff";
    static final String DFA126_eofS =
        "\16\uffff";
    static final String DFA126_minS =
        "\1\43\15\uffff";
    static final String DFA126_maxS =
        "\1\u00a4\15\uffff";
    static final String DFA126_acceptS =
        "\1\uffff\1\1\1\2\13\uffff";
    static final String DFA126_specialS =
        "\16\uffff}>";
    static final String[] DFA126_transitionS = {
            "\1\2\3\uffff\1\2\5\uffff\2\2\3\uffff\1\2\30\uffff\1\2\73\uffff"+
            "\1\2\23\uffff\2\2\1\uffff\1\1\2\uffff\2\2\1\uffff\1\2",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA126_eot = DFA.unpackEncodedString(DFA126_eotS);
    static final short[] DFA126_eof = DFA.unpackEncodedString(DFA126_eofS);
    static final char[] DFA126_min = DFA.unpackEncodedStringToUnsignedChars(DFA126_minS);
    static final char[] DFA126_max = DFA.unpackEncodedStringToUnsignedChars(DFA126_maxS);
    static final short[] DFA126_accept = DFA.unpackEncodedString(DFA126_acceptS);
    static final short[] DFA126_special = DFA.unpackEncodedString(DFA126_specialS);
    static final short[][] DFA126_transition;

    static {
        int numStates = DFA126_transitionS.length;
        DFA126_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA126_transition[i] = DFA.unpackEncodedString(DFA126_transitionS[i]);
        }
    }

    class DFA126 extends DFA {

        public DFA126(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 126;
            this.eot = DFA126_eot;
            this.eof = DFA126_eof;
            this.min = DFA126_min;
            this.max = DFA126_max;
            this.accept = DFA126_accept;
            this.special = DFA126_special;
            this.transition = DFA126_transition;
        }
        public String getDescription() {
            return "368:74: ( AUTOINCREMENT )?";
        }
    }
    static final String DFA127_eotS =
        "\16\uffff";
    static final String DFA127_eofS =
        "\16\uffff";
    static final String DFA127_minS =
        "\1\43\15\uffff";
    static final String DFA127_maxS =
        "\1\u00a4\15\uffff";
    static final String DFA127_acceptS =
        "\1\uffff\1\1\1\2\13\uffff";
    static final String DFA127_specialS =
        "\16\uffff}>";
    static final String[] DFA127_transitionS = {
            "\1\2\3\uffff\1\2\5\uffff\2\2\3\uffff\1\2\30\uffff\1\2\66\uffff"+
            "\1\1\4\uffff\1\2\23\uffff\2\2\4\uffff\2\2\1\uffff\1\2",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA127_eot = DFA.unpackEncodedString(DFA127_eotS);
    static final short[] DFA127_eof = DFA.unpackEncodedString(DFA127_eofS);
    static final char[] DFA127_min = DFA.unpackEncodedStringToUnsignedChars(DFA127_minS);
    static final char[] DFA127_max = DFA.unpackEncodedStringToUnsignedChars(DFA127_maxS);
    static final short[] DFA127_accept = DFA.unpackEncodedString(DFA127_acceptS);
    static final short[] DFA127_special = DFA.unpackEncodedString(DFA127_specialS);
    static final short[][] DFA127_transition;

    static {
        int numStates = DFA127_transitionS.length;
        DFA127_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA127_transition[i] = DFA.unpackEncodedString(DFA127_transitionS[i]);
        }
    }

    class DFA127 extends DFA {

        public DFA127(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 127;
            this.eot = DFA127_eot;
            this.eof = DFA127_eof;
            this.min = DFA127_min;
            this.max = DFA127_max;
            this.accept = DFA127_accept;
            this.special = DFA127_special;
            this.transition = DFA127_transition;
        }
        public String getDescription() {
            return "370:38: ( table_conflict_clause )?";
        }
    }
    static final String DFA128_eotS =
        "\16\uffff";
    static final String DFA128_eofS =
        "\16\uffff";
    static final String DFA128_minS =
        "\1\43\15\uffff";
    static final String DFA128_maxS =
        "\1\u00a4\15\uffff";
    static final String DFA128_acceptS =
        "\1\uffff\1\1\1\2\13\uffff";
    static final String DFA128_specialS =
        "\16\uffff}>";
    static final String[] DFA128_transitionS = {
            "\1\2\3\uffff\1\2\5\uffff\2\2\3\uffff\1\2\30\uffff\1\2\66\uffff"+
            "\1\1\4\uffff\1\2\23\uffff\2\2\4\uffff\2\2\1\uffff\1\2",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA128_eot = DFA.unpackEncodedString(DFA128_eotS);
    static final short[] DFA128_eof = DFA.unpackEncodedString(DFA128_eofS);
    static final char[] DFA128_min = DFA.unpackEncodedStringToUnsignedChars(DFA128_minS);
    static final char[] DFA128_max = DFA.unpackEncodedStringToUnsignedChars(DFA128_maxS);
    static final short[] DFA128_accept = DFA.unpackEncodedString(DFA128_acceptS);
    static final short[] DFA128_special = DFA.unpackEncodedString(DFA128_specialS);
    static final short[][] DFA128_transition;

    static {
        int numStates = DFA128_transitionS.length;
        DFA128_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA128_transition[i] = DFA.unpackEncodedString(DFA128_transitionS[i]);
        }
    }

    class DFA128 extends DFA {

        public DFA128(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 128;
            this.eot = DFA128_eot;
            this.eof = DFA128_eof;
            this.min = DFA128_min;
            this.max = DFA128_max;
            this.accept = DFA128_accept;
            this.special = DFA128_special;
            this.transition = DFA128_transition;
        }
        public String getDescription() {
            return "372:60: ( table_conflict_clause )?";
        }
    }
    static final String DFA129_eotS =
        "\16\uffff";
    static final String DFA129_eofS =
        "\16\uffff";
    static final String DFA129_minS =
        "\1\43\15\uffff";
    static final String DFA129_maxS =
        "\1\u00a4\15\uffff";
    static final String DFA129_acceptS =
        "\1\uffff\1\1\1\2\13\uffff";
    static final String DFA129_specialS =
        "\16\uffff}>";
    static final String[] DFA129_transitionS = {
            "\1\2\3\uffff\1\2\5\uffff\2\2\3\uffff\1\2\30\uffff\1\2\66\uffff"+
            "\1\1\4\uffff\1\2\23\uffff\2\2\4\uffff\2\2\1\uffff\1\2",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA129_eot = DFA.unpackEncodedString(DFA129_eotS);
    static final short[] DFA129_eof = DFA.unpackEncodedString(DFA129_eofS);
    static final char[] DFA129_min = DFA.unpackEncodedStringToUnsignedChars(DFA129_minS);
    static final char[] DFA129_max = DFA.unpackEncodedStringToUnsignedChars(DFA129_maxS);
    static final short[] DFA129_accept = DFA.unpackEncodedString(DFA129_acceptS);
    static final short[] DFA129_special = DFA.unpackEncodedString(DFA129_specialS);
    static final short[][] DFA129_transition;

    static {
        int numStates = DFA129_transitionS.length;
        DFA129_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA129_transition[i] = DFA.unpackEncodedString(DFA129_transitionS[i]);
        }
    }

    class DFA129 extends DFA {

        public DFA129(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 129;
            this.eot = DFA129_eot;
            this.eof = DFA129_eof;
            this.min = DFA129_min;
            this.max = DFA129_max;
            this.accept = DFA129_accept;
            this.special = DFA129_special;
            this.transition = DFA129_transition;
        }
        public String getDescription() {
            return "374:30: ( table_conflict_clause )?";
        }
    }
    static final String DFA130_eotS =
        "\16\uffff";
    static final String DFA130_eofS =
        "\16\uffff";
    static final String DFA130_minS =
        "\1\43\15\uffff";
    static final String DFA130_maxS =
        "\1\u00a4\15\uffff";
    static final String DFA130_acceptS =
        "\1\uffff\1\1\1\2\13\uffff";
    static final String DFA130_specialS =
        "\16\uffff}>";
    static final String[] DFA130_transitionS = {
            "\1\2\3\uffff\1\2\5\uffff\2\2\3\uffff\1\2\30\uffff\1\2\66\uffff"+
            "\1\1\4\uffff\1\2\23\uffff\2\2\4\uffff\2\2\1\uffff\1\2",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA130_eot = DFA.unpackEncodedString(DFA130_eotS);
    static final short[] DFA130_eof = DFA.unpackEncodedString(DFA130_eofS);
    static final char[] DFA130_min = DFA.unpackEncodedStringToUnsignedChars(DFA130_minS);
    static final char[] DFA130_max = DFA.unpackEncodedStringToUnsignedChars(DFA130_maxS);
    static final short[] DFA130_accept = DFA.unpackEncodedString(DFA130_acceptS);
    static final short[] DFA130_special = DFA.unpackEncodedString(DFA130_specialS);
    static final short[][] DFA130_transition;

    static {
        int numStates = DFA130_transitionS.length;
        DFA130_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA130_transition[i] = DFA.unpackEncodedString(DFA130_transitionS[i]);
        }
    }

    class DFA130 extends DFA {

        public DFA130(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 130;
            this.eot = DFA130_eot;
            this.eof = DFA130_eof;
            this.min = DFA130_min;
            this.max = DFA130_max;
            this.accept = DFA130_accept;
            this.special = DFA130_special;
            this.transition = DFA130_transition;
        }
        public String getDescription() {
            return "376:35: ( table_conflict_clause )?";
        }
    }
    static final String DFA132_eotS =
        "\13\uffff";
    static final String DFA132_eofS =
        "\13\uffff";
    static final String DFA132_minS =
        "\1\54\12\uffff";
    static final String DFA132_maxS =
        "\1\133\12\uffff";
    static final String DFA132_acceptS =
        "\1\uffff\1\1\1\2\7\uffff\1\3";
    static final String DFA132_specialS =
        "\13\uffff}>";
    static final String[] DFA132_transitionS = {
            "\1\12\5\uffff\1\2\21\uffff\2\1\17\uffff\7\2",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA132_eot = DFA.unpackEncodedString(DFA132_eotS);
    static final short[] DFA132_eof = DFA.unpackEncodedString(DFA132_eofS);
    static final char[] DFA132_min = DFA.unpackEncodedStringToUnsignedChars(DFA132_minS);
    static final char[] DFA132_max = DFA.unpackEncodedStringToUnsignedChars(DFA132_maxS);
    static final short[] DFA132_accept = DFA.unpackEncodedString(DFA132_acceptS);
    static final short[] DFA132_special = DFA.unpackEncodedString(DFA132_specialS);
    static final short[][] DFA132_transition;

    static {
        int numStates = DFA132_transitionS.length;
        DFA132_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA132_transition[i] = DFA.unpackEncodedString(DFA132_transitionS[i]);
        }
    }

    class DFA132 extends DFA {

        public DFA132(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 132;
            this.eot = DFA132_eot;
            this.eof = DFA132_eof;
            this.min = DFA132_min;
            this.max = DFA132_max;
            this.accept = DFA132_accept;
            this.special = DFA132_special;
            this.transition = DFA132_transition;
        }
        public String getDescription() {
            return "388:37: ( signed_default_number | literal_value | LPAREN expr RPAREN )";
        }
    }
    static final String DFA141_eotS =
        "\21\uffff";
    static final String DFA141_eofS =
        "\21\uffff";
    static final String DFA141_minS =
        "\1\43\20\uffff";
    static final String DFA141_maxS =
        "\1\u00a7\20\uffff";
    static final String DFA141_acceptS =
        "\1\uffff\1\1\1\2\16\uffff";
    static final String DFA141_specialS =
        "\21\uffff}>";
    static final String[] DFA141_transitionS = {
            "\1\2\3\uffff\1\2\4\uffff\1\1\2\2\3\uffff\1\2\10\uffff\1\2\17"+
            "\uffff\1\2\66\uffff\1\2\4\uffff\1\2\23\uffff\2\2\4\uffff\2\2"+
            "\1\uffff\1\2\2\uffff\1\2",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA141_eot = DFA.unpackEncodedString(DFA141_eotS);
    static final short[] DFA141_eof = DFA.unpackEncodedString(DFA141_eofS);
    static final char[] DFA141_min = DFA.unpackEncodedStringToUnsignedChars(DFA141_minS);
    static final char[] DFA141_max = DFA.unpackEncodedStringToUnsignedChars(DFA141_maxS);
    static final short[] DFA141_accept = DFA.unpackEncodedString(DFA141_acceptS);
    static final short[] DFA141_special = DFA.unpackEncodedString(DFA141_specialS);
    static final short[][] DFA141_transition;

    static {
        int numStates = DFA141_transitionS.length;
        DFA141_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA141_transition[i] = DFA.unpackEncodedString(DFA141_transitionS[i]);
        }
    }

    class DFA141 extends DFA {

        public DFA141(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 141;
            this.eot = DFA141_eot;
            this.eof = DFA141_eof;
            this.min = DFA141_min;
            this.max = DFA141_max;
            this.accept = DFA141_accept;
            this.special = DFA141_special;
            this.transition = DFA141_transition;
        }
        public String getDescription() {
            return "417:106: ( LPAREN column_names+= id ( COMMA column_names+= id )* RPAREN )?";
        }
    }
    static final String DFA142_eotS =
        "\20\uffff";
    static final String DFA142_eofS =
        "\20\uffff";
    static final String DFA142_minS =
        "\1\43\17\uffff";
    static final String DFA142_maxS =
        "\1\u00a7\17\uffff";
    static final String DFA142_acceptS =
        "\1\uffff\1\2\14\uffff\1\1\1\uffff";
    static final String DFA142_specialS =
        "\20\uffff}>";
    static final String[] DFA142_transitionS = {
            "\1\1\3\uffff\1\1\5\uffff\2\1\3\uffff\1\1\10\uffff\1\16\17\uffff"+
            "\1\1\66\uffff\1\16\4\uffff\1\1\23\uffff\2\1\4\uffff\2\1\1\uffff"+
            "\1\1\2\uffff\1\1",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA142_eot = DFA.unpackEncodedString(DFA142_eotS);
    static final short[] DFA142_eof = DFA.unpackEncodedString(DFA142_eofS);
    static final char[] DFA142_min = DFA.unpackEncodedStringToUnsignedChars(DFA142_minS);
    static final char[] DFA142_max = DFA.unpackEncodedStringToUnsignedChars(DFA142_maxS);
    static final short[] DFA142_accept = DFA.unpackEncodedString(DFA142_acceptS);
    static final short[] DFA142_special = DFA.unpackEncodedString(DFA142_specialS);
    static final short[][] DFA142_transition;

    static {
        int numStates = DFA142_transitionS.length;
        DFA142_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA142_transition[i] = DFA.unpackEncodedString(DFA142_transitionS[i]);
        }
    }

    class DFA142 extends DFA {

        public DFA142(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 142;
            this.eot = DFA142_eot;
            this.eof = DFA142_eof;
            this.min = DFA142_min;
            this.max = DFA142_max;
            this.accept = DFA142_accept;
            this.special = DFA142_special;
            this.transition = DFA142_transition;
        }
        public String getDescription() {
            return "()* loopback of 418:3: ( fk_clause_action )*";
        }
    }
    static final String DFA143_eotS =
        "\21\uffff";
    static final String DFA143_eofS =
        "\21\uffff";
    static final String DFA143_minS =
        "\1\43\1\62\17\uffff";
    static final String DFA143_maxS =
        "\2\u00a7\17\uffff";
    static final String DFA143_acceptS =
        "\2\uffff\1\1\1\2\15\uffff";
    static final String DFA143_specialS =
        "\21\uffff}>";
    static final String[] DFA143_transitionS = {
            "\1\3\3\uffff\1\1\5\uffff\2\3\3\uffff\1\3\30\uffff\1\3\73\uffff"+
            "\1\3\23\uffff\2\3\4\uffff\2\3\1\uffff\1\3\2\uffff\1\2",
            "\1\3\154\uffff\1\3\7\uffff\1\2",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA143_eot = DFA.unpackEncodedString(DFA143_eotS);
    static final short[] DFA143_eof = DFA.unpackEncodedString(DFA143_eofS);
    static final char[] DFA143_min = DFA.unpackEncodedStringToUnsignedChars(DFA143_minS);
    static final char[] DFA143_max = DFA.unpackEncodedStringToUnsignedChars(DFA143_maxS);
    static final short[] DFA143_accept = DFA.unpackEncodedString(DFA143_acceptS);
    static final short[] DFA143_special = DFA.unpackEncodedString(DFA143_specialS);
    static final short[][] DFA143_transition;

    static {
        int numStates = DFA143_transitionS.length;
        DFA143_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA143_transition[i] = DFA.unpackEncodedString(DFA143_transitionS[i]);
        }
    }

    class DFA143 extends DFA {

        public DFA143(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 143;
            this.eot = DFA143_eot;
            this.eof = DFA143_eof;
            this.min = DFA143_min;
            this.max = DFA143_max;
            this.accept = DFA143_accept;
            this.special = DFA143_special;
            this.transition = DFA143_transition;
        }
        public String getDescription() {
            return "418:21: ( fk_clause_deferrable )?";
        }
    }
    static final String DFA147_eotS =
        "\20\uffff";
    static final String DFA147_eofS =
        "\20\uffff";
    static final String DFA147_minS =
        "\1\43\1\u008c\16\uffff";
    static final String DFA147_maxS =
        "\1\u00a8\1\u008d\16\uffff";
    static final String DFA147_acceptS =
        "\2\uffff\1\3\13\uffff\1\1\1\2";
    static final String DFA147_specialS =
        "\20\uffff}>";
    static final String[] DFA147_transitionS = {
            "\1\2\3\uffff\1\2\5\uffff\2\2\3\uffff\1\2\30\uffff\1\2\73\uffff"+
            "\1\2\23\uffff\2\2\4\uffff\2\2\1\uffff\1\2\3\uffff\1\1",
            "\1\16\1\17",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA147_eot = DFA.unpackEncodedString(DFA147_eotS);
    static final short[] DFA147_eof = DFA.unpackEncodedString(DFA147_eofS);
    static final char[] DFA147_min = DFA.unpackEncodedStringToUnsignedChars(DFA147_minS);
    static final char[] DFA147_max = DFA.unpackEncodedStringToUnsignedChars(DFA147_maxS);
    static final short[] DFA147_accept = DFA.unpackEncodedString(DFA147_acceptS);
    static final short[] DFA147_special = DFA.unpackEncodedString(DFA147_specialS);
    static final short[][] DFA147_transition;

    static {
        int numStates = DFA147_transitionS.length;
        DFA147_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA147_transition[i] = DFA.unpackEncodedString(DFA147_transitionS[i]);
        }
    }

    class DFA147 extends DFA {

        public DFA147(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 147;
            this.eot = DFA147_eot;
            this.eof = DFA147_eof;
            this.min = DFA147_min;
            this.max = DFA147_max;
            this.accept = DFA147_accept;
            this.special = DFA147_special;
            this.transition = DFA147_transition;
        }
        public String getDescription() {
            return "425:42: ( INITIALLY DEFERRED | INITIALLY IMMEDIATE )?";
        }
    }
    static final String DFA154_eotS =
        "\73\uffff";
    static final String DFA154_eofS =
        "\73\uffff";
    static final String DFA154_minS =
        "\1\u00ac\1\uffff\1\40\1\uffff\4\43\63\uffff";
    static final String DFA154_maxS =
        "\1\u00ad\1\uffff\1\u00b9\1\uffff\4\u00a4\63\uffff";
    static final String DFA154_acceptS =
        "\1\uffff\1\1\1\uffff\1\3\4\uffff\1\2\62\uffff";
    static final String DFA154_specialS =
        "\73\uffff}>";
    static final String[] DFA154_transitionS = {
            "\1\1\1\2",
            "",
            "\3\10\2\uffff\7\10\3\uffff\5\10\4\uffff\4\10\17\uffff\12\10"+
            "\4\uffff\3\10\3\uffff\5\10\2\uffff\65\10\1\3\1\4\3\10\1\uffff"+
            "\1\5\1\6\1\7\7\10\1\uffff\16\10",
            "",
            "\1\10\3\uffff\1\10\12\uffff\1\10\30\uffff\2\10\72\uffff\1\10"+
            "\23\uffff\2\10\1\3\3\uffff\2\10\1\uffff\1\10",
            "\1\10\3\uffff\1\10\4\uffff\1\3\5\uffff\1\10\30\uffff\2\10\72"+
            "\uffff\1\10\23\uffff\2\10\4\uffff\2\10\1\uffff\1\10",
            "\1\10\3\uffff\1\10\4\uffff\1\3\5\uffff\1\10\30\uffff\2\10\72"+
            "\uffff\1\10\23\uffff\2\10\4\uffff\2\10\1\uffff\1\10",
            "\1\10\3\uffff\1\10\12\uffff\1\10\30\uffff\2\10\72\uffff\1\10"+
            "\23\uffff\2\10\1\3\3\uffff\2\10\1\uffff\1\10",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA154_eot = DFA.unpackEncodedString(DFA154_eotS);
    static final short[] DFA154_eof = DFA.unpackEncodedString(DFA154_eofS);
    static final char[] DFA154_min = DFA.unpackEncodedStringToUnsignedChars(DFA154_minS);
    static final char[] DFA154_max = DFA.unpackEncodedStringToUnsignedChars(DFA154_maxS);
    static final short[] DFA154_accept = DFA.unpackEncodedString(DFA154_acceptS);
    static final short[] DFA154_special = DFA.unpackEncodedString(DFA154_specialS);
    static final short[][] DFA154_transition;

    static {
        int numStates = DFA154_transitionS.length;
        DFA154_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA154_transition[i] = DFA.unpackEncodedString(DFA154_transitionS[i]);
        }
    }

    class DFA154 extends DFA {

        public DFA154(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 154;
            this.eot = DFA154_eot;
            this.eof = DFA154_eof;
            this.min = DFA154_min;
            this.max = DFA154_max;
            this.accept = DFA154_accept;
            this.special = DFA154_special;
            this.transition = DFA154_transition;
        }
        public String getDescription() {
            return "432:134: ( RENAME TO new_table_name= id | ADD ( COLUMN )? column_def | ADD table_constraint ( COMMA table_constraint )* )";
        }
    }
    static final String DFA169_eotS =
        "\14\uffff";
    static final String DFA169_eofS =
        "\14\uffff";
    static final String DFA169_minS =
        "\1\40\1\44\12\uffff";
    static final String DFA169_maxS =
        "\1\u00b9\1\u00b4\12\uffff";
    static final String DFA169_acceptS =
        "\2\uffff\1\2\1\uffff\1\1\7\uffff";
    static final String DFA169_specialS =
        "\14\uffff}>";
    static final String[] DFA169_transitionS = {
            "\3\2\2\uffff\2\2\1\uffff\3\2\6\uffff\3\2\27\uffff\12\2\4\uffff"+
            "\3\2\3\uffff\5\2\2\uffff\63\2\1\1\6\2\1\uffff\12\2\1\uffff\16"+
            "\2",
            "\1\2\2\uffff\1\4\134\uffff\1\2\3\uffff\1\2\1\uffff\1\2\47\uffff"+
            "\3\2",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA169_eot = DFA.unpackEncodedString(DFA169_eotS);
    static final short[] DFA169_eof = DFA.unpackEncodedString(DFA169_eofS);
    static final char[] DFA169_min = DFA.unpackEncodedStringToUnsignedChars(DFA169_minS);
    static final char[] DFA169_max = DFA.unpackEncodedStringToUnsignedChars(DFA169_maxS);
    static final short[] DFA169_accept = DFA.unpackEncodedString(DFA169_acceptS);
    static final short[] DFA169_special = DFA.unpackEncodedString(DFA169_specialS);
    static final short[][] DFA169_transition;

    static {
        int numStates = DFA169_transitionS.length;
        DFA169_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA169_transition[i] = DFA.unpackEncodedString(DFA169_transitionS[i]);
        }
    }

    class DFA169 extends DFA {

        public DFA169(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 169;
            this.eot = DFA169_eot;
            this.eof = DFA169_eof;
            this.min = DFA169_min;
            this.max = DFA169_max;
            this.accept = DFA169_accept;
            this.special = DFA169_special;
            this.transition = DFA169_transition;
        }
        public String getDescription() {
            return "453:48: ( IF NOT EXISTS )?";
        }
    }
    static final String DFA170_eotS =
        "\21\uffff";
    static final String DFA170_eofS =
        "\21\uffff";
    static final String DFA170_minS =
        "\1\40\2\44\16\uffff";
    static final String DFA170_maxS =
        "\1\u00b9\2\u00b4\16\uffff";
    static final String DFA170_acceptS =
        "\3\uffff\1\1\1\2\14\uffff";
    static final String DFA170_specialS =
        "\21\uffff}>";
    static final String[] DFA170_transitionS = {
            "\3\2\2\uffff\2\2\1\uffff\3\2\6\uffff\3\2\27\uffff\1\2\1\1\10"+
            "\2\4\uffff\3\2\3\uffff\5\2\2\uffff\72\2\1\uffff\12\2\1\uffff"+
            "\16\2",
            "\1\3\137\uffff\1\4\3\uffff\1\4\1\uffff\1\4\47\uffff\3\4",
            "\1\3\137\uffff\1\4\3\uffff\1\4\1\uffff\1\4\47\uffff\3\4",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA170_eot = DFA.unpackEncodedString(DFA170_eotS);
    static final short[] DFA170_eof = DFA.unpackEncodedString(DFA170_eofS);
    static final char[] DFA170_min = DFA.unpackEncodedStringToUnsignedChars(DFA170_minS);
    static final char[] DFA170_max = DFA.unpackEncodedStringToUnsignedChars(DFA170_maxS);
    static final short[] DFA170_accept = DFA.unpackEncodedString(DFA170_acceptS);
    static final short[] DFA170_special = DFA.unpackEncodedString(DFA170_specialS);
    static final short[][] DFA170_transition;

    static {
        int numStates = DFA170_transitionS.length;
        DFA170_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA170_transition[i] = DFA.unpackEncodedString(DFA170_transitionS[i]);
        }
    }

    class DFA170 extends DFA {

        public DFA170(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 170;
            this.eot = DFA170_eot;
            this.eof = DFA170_eof;
            this.min = DFA170_min;
            this.max = DFA170_max;
            this.accept = DFA170_accept;
            this.special = DFA170_special;
            this.transition = DFA170_transition;
        }
        public String getDescription() {
            return "453:65: (database_name= id DOT )?";
        }
    }
 

    public static final BitSet FOLLOW_sql_stmt_in_sql_stmt_list190 = new BitSet(new long[]{0x0000000100000002L,0x00803EC200040000L,0x01000600002D0D10L});
    public static final BitSet FOLLOW_EXPLAIN_in_sql_stmt200 = new BitSet(new long[]{0x0000000B00000000L,0x00803EC200040000L,0x01000600002D0D10L});
    public static final BitSet FOLLOW_QUERY_in_sql_stmt203 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_PLAN_in_sql_stmt205 = new BitSet(new long[]{0x0000000900000000L,0x00803EC200040000L,0x01000600002D0D10L});
    public static final BitSet FOLLOW_sql_stmt_core_in_sql_stmt211 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_SEMI_in_sql_stmt213 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pragma_stmt_in_sql_stmt_core224 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_attach_stmt_in_sql_stmt_core230 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_detach_stmt_in_sql_stmt_core236 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_analyze_stmt_in_sql_stmt_core242 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_reindex_stmt_in_sql_stmt_core248 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_vacuum_stmt_in_sql_stmt_core254 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_select_stmt_in_sql_stmt_core261 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_insert_stmt_in_sql_stmt_core267 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_update_stmt_in_sql_stmt_core273 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_delete_stmt_in_sql_stmt_core279 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_begin_stmt_in_sql_stmt_core285 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_commit_stmt_in_sql_stmt_core291 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rollback_stmt_in_sql_stmt_core297 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_savepoint_stmt_in_sql_stmt_core303 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_release_stmt_in_sql_stmt_core309 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_create_virtual_table_stmt_in_sql_stmt_core316 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_create_table_stmt_in_sql_stmt_core322 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_drop_table_stmt_in_sql_stmt_core328 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_alter_table_stmt_in_sql_stmt_core334 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_create_view_stmt_in_sql_stmt_core340 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_drop_view_stmt_in_sql_stmt_core346 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_create_index_stmt_in_sql_stmt_core352 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_drop_index_stmt_in_sql_stmt_core358 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_create_trigger_stmt_in_sql_stmt_core364 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_drop_trigger_stmt_in_sql_stmt_core370 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_table_comment_in_sql_stmt_core376 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_col_comment_in_sql_stmt_core382 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_id_in_qualified_table_name395 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_DOT_in_qualified_table_name397 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_id_in_qualified_table_name403 = new BitSet(new long[]{0x000000A000000002L});
    public static final BitSet FOLLOW_INDEXED_in_qualified_table_name406 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_BY_in_qualified_table_name408 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_id_in_qualified_table_name412 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NOT_in_qualified_table_name416 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_INDEXED_in_qualified_table_name418 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_or_subexpr_in_expr427 = new BitSet(new long[]{0x0000010000000002L});
    public static final BitSet FOLLOW_OR_in_expr430 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFCFFFFFFC30L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_or_subexpr_in_expr433 = new BitSet(new long[]{0x0000010000000002L});
    public static final BitSet FOLLOW_and_subexpr_in_or_subexpr442 = new BitSet(new long[]{0x0000020000000002L});
    public static final BitSet FOLLOW_AND_in_or_subexpr445 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFCFFFFFFC30L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_and_subexpr_in_or_subexpr448 = new BitSet(new long[]{0x0000020000000002L});
    public static final BitSet FOLLOW_eq_subexpr_in_and_subexpr457 = new BitSet(new long[]{0x0FFB888000000002L});
    public static final BitSet FOLLOW_cond_expr_in_and_subexpr459 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NOT_in_cond_expr471 = new BitSet(new long[]{0x0F00008000000000L});
    public static final BitSet FOLLOW_match_op_in_cond_expr474 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFCFFFFFFC30L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_eq_subexpr_in_cond_expr478 = new BitSet(new long[]{0x0000040000000002L});
    public static final BitSet FOLLOW_ESCAPE_in_cond_expr481 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFCFFFFFFC30L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_eq_subexpr_in_cond_expr485 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NOT_in_cond_expr513 = new BitSet(new long[]{0x0000080000000000L});
    public static final BitSet FOLLOW_IN_in_cond_expr516 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_LPAREN_in_cond_expr518 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFCFFFFFFC30L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_expr_in_cond_expr520 = new BitSet(new long[]{0x0000600000000000L});
    public static final BitSet FOLLOW_COMMA_in_cond_expr523 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFCFFFFFFC30L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_expr_in_cond_expr525 = new BitSet(new long[]{0x0000600000000000L});
    public static final BitSet FOLLOW_RPAREN_in_cond_expr529 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NOT_in_cond_expr551 = new BitSet(new long[]{0x0000080000000000L});
    public static final BitSet FOLLOW_IN_in_cond_expr554 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_id_in_cond_expr559 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_DOT_in_cond_expr561 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_id_in_cond_expr567 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ISNULL_in_cond_expr598 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NOTNULL_in_cond_expr606 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IS_in_cond_expr614 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_NULL_in_cond_expr616 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NOT_in_cond_expr624 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_NULL_in_cond_expr626 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IS_in_cond_expr634 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_NOT_in_cond_expr636 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_NULL_in_cond_expr638 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NOT_in_cond_expr649 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_BETWEEN_in_cond_expr652 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFCFFFFFFC30L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_eq_subexpr_in_cond_expr656 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_AND_in_cond_expr658 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFCFFFFFFC30L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_eq_subexpr_in_cond_expr662 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_cond_expr688 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFCFFFFFFC30L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_eq_subexpr_in_cond_expr705 = new BitSet(new long[]{0x00F0000000000002L});
    public static final BitSet FOLLOW_set_in_match_op0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_neq_subexpr_in_eq_subexpr738 = new BitSet(new long[]{0xF000000000000002L});
    public static final BitSet FOLLOW_set_in_eq_subexpr741 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFCFFFFFFC30L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_neq_subexpr_in_eq_subexpr758 = new BitSet(new long[]{0xF000000000000002L});
    public static final BitSet FOLLOW_bit_subexpr_in_neq_subexpr767 = new BitSet(new long[]{0x0000000000000002L,0x000000000000000FL});
    public static final BitSet FOLLOW_set_in_neq_subexpr770 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFCFFFFFFC30L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_bit_subexpr_in_neq_subexpr787 = new BitSet(new long[]{0x0000000000000002L,0x000000000000000FL});
    public static final BitSet FOLLOW_add_subexpr_in_bit_subexpr796 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000030L});
    public static final BitSet FOLLOW_set_in_bit_subexpr799 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFCFFFFFFC30L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_add_subexpr_in_bit_subexpr808 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000030L});
    public static final BitSet FOLLOW_mul_subexpr_in_add_subexpr817 = new BitSet(new long[]{0x0000000000000002L,0x00000000000001C0L});
    public static final BitSet FOLLOW_set_in_add_subexpr820 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFCFFFFFFC30L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_mul_subexpr_in_add_subexpr833 = new BitSet(new long[]{0x0000000000000002L,0x00000000000001C0L});
    public static final BitSet FOLLOW_con_subexpr_in_mul_subexpr842 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000200L});
    public static final BitSet FOLLOW_DOUBLE_PIPE_in_mul_subexpr845 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFCFFFFFFC30L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_con_subexpr_in_mul_subexpr848 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000200L});
    public static final BitSet FOLLOW_unary_subexpr_in_con_subexpr857 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_unary_op_in_con_subexpr861 = new BitSet(new long[]{0x000E176700000000L,0xFFFFFFCFFFFFF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_unary_subexpr_in_con_subexpr863 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_unary_op0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_atom_expr_in_unary_subexpr897 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000800L});
    public static final BitSet FOLLOW_COLLATE_in_unary_subexpr900 = new BitSet(new long[]{0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_ID_in_unary_subexpr905 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_literal_value_in_atom_expr917 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_bind_parameter_in_atom_expr923 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_id_in_atom_expr933 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_DOT_in_atom_expr935 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_id_in_atom_expr941 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_DOT_in_atom_expr943 = new BitSet(new long[]{0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_ID_in_atom_expr949 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_atom_expr978 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_LPAREN_in_atom_expr980 = new BitSet(new long[]{0x000E57E700000000L,0xFFFFFFCFFFFFFC70L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_DISTINCT_in_atom_expr983 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFCFFFFFFC30L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_expr_in_atom_expr988 = new BitSet(new long[]{0x0000600000000000L});
    public static final BitSet FOLLOW_COMMA_in_atom_expr991 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFCFFFFFFC30L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_expr_in_atom_expr995 = new BitSet(new long[]{0x0000600000000000L});
    public static final BitSet FOLLOW_ASTERISK_in_atom_expr1001 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_RPAREN_in_atom_expr1005 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_atom_expr1030 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFCFFFFFFC30L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_expr_in_atom_expr1033 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_RPAREN_in_atom_expr1035 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CAST_in_atom_expr1042 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_LPAREN_in_atom_expr1045 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFCFFFFFFC30L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_expr_in_atom_expr1048 = new BitSet(new long[]{0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_AS_in_atom_expr1050 = new BitSet(new long[]{0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_type_name_in_atom_expr1053 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_RPAREN_in_atom_expr1055 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CASE_in_atom_expr1064 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFCFFFFFFC30L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_expr_in_atom_expr1069 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFCFFFFFFC30L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_when_expr_in_atom_expr1073 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFCFFFFFFC30L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_ELSE_in_atom_expr1077 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFCFFFFFFC30L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_expr_in_atom_expr1081 = new BitSet(new long[]{0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_END_in_atom_expr1085 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_raise_function_in_atom_expr1108 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_WHEN_in_when_expr1118 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFCFFFFFFC30L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_expr_in_when_expr1122 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_THEN_in_when_expr1124 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFCFFFFFFC30L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_expr_in_when_expr1128 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INTEGER_in_literal_value1150 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FLOAT_in_literal_value1164 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_literal_value1178 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_BLOB_in_literal_value1192 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NULL_in_literal_value1206 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CURRENT_TIME_in_literal_value1212 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CURRENT_DATE_in_literal_value1226 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CURRENT_TIMESTAMP_in_literal_value1240 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_QUESTION_in_bind_parameter1261 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_QUESTION_in_bind_parameter1271 = new BitSet(new long[]{0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_INTEGER_in_bind_parameter1275 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_COLON_in_bind_parameter1290 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_id_in_bind_parameter1294 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_AT_in_bind_parameter1309 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_id_in_bind_parameter1313 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RAISE_in_raise_function1334 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_LPAREN_in_raise_function1337 = new BitSet(new long[]{0x0000000000000000L,0x0000000F00000000L});
    public static final BitSet FOLLOW_IGNORE_in_raise_function1341 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_set_in_raise_function1345 = new BitSet(new long[]{0x0000200000000000L});
    public static final BitSet FOLLOW_COMMA_in_raise_function1357 = new BitSet(new long[]{0x0000000000000000L,0x0000000000800000L});
    public static final BitSet FOLLOW_STRING_in_raise_function1362 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_RPAREN_in_raise_function1365 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_type_name1375 = new BitSet(new long[]{0x0000100000000002L,0x0000000000001000L});
    public static final BitSet FOLLOW_LPAREN_in_type_name1379 = new BitSet(new long[]{0x0000000000000000L,0x0000001000600030L});
    public static final BitSet FOLLOW_signed_number_in_type_name1385 = new BitSet(new long[]{0x0000600000000000L});
    public static final BitSet FOLLOW_MAX_in_type_name1389 = new BitSet(new long[]{0x0000600000000000L});
    public static final BitSet FOLLOW_signed_number_in_type_name1393 = new BitSet(new long[]{0x0000000000000000L,0x0000002000000000L});
    public static final BitSet FOLLOW_BYTE_in_type_name1395 = new BitSet(new long[]{0x0000600000000000L});
    public static final BitSet FOLLOW_COMMA_in_type_name1399 = new BitSet(new long[]{0x0000000000000000L,0x0000000000600030L});
    public static final BitSet FOLLOW_signed_number_in_type_name1403 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_RPAREN_in_type_name1407 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_signed_number1440 = new BitSet(new long[]{0x0000000000000000L,0x0000000000600000L});
    public static final BitSet FOLLOW_set_in_signed_number1449 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PRAGMA_in_pragma_stmt1463 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_id_in_pragma_stmt1468 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_DOT_in_pragma_stmt1470 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_id_in_pragma_stmt1476 = new BitSet(new long[]{0x0010100000000002L});
    public static final BitSet FOLLOW_EQUALS_in_pragma_stmt1479 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8EFFF830L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_pragma_value_in_pragma_stmt1481 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_pragma_stmt1485 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8EFFF830L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_pragma_value_in_pragma_stmt1487 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_RPAREN_in_pragma_stmt1489 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_signed_number_in_pragma_value1518 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_id_in_pragma_value1531 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_pragma_value1544 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ATTACH_in_attach_stmt1562 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E9FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_DATABASE_in_attach_stmt1565 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E9FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_STRING_in_attach_stmt1572 = new BitSet(new long[]{0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_id_in_attach_stmt1576 = new BitSet(new long[]{0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_AS_in_attach_stmt1579 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_id_in_attach_stmt1583 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DETACH_in_detach_stmt1591 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_DATABASE_in_detach_stmt1594 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_id_in_detach_stmt1600 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ANALYZE_in_analyze_stmt1608 = new BitSet(new long[]{0x000E076700000002L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_id_in_analyze_stmt1613 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_id_in_analyze_stmt1619 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_DOT_in_analyze_stmt1621 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_id_in_analyze_stmt1625 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_REINDEX_in_reindex_stmt1635 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_id_in_reindex_stmt1640 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_DOT_in_reindex_stmt1642 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_id_in_reindex_stmt1648 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VACUUM_in_vacuum_stmt1656 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OR_in_operation_conflict_clause1667 = new BitSet(new long[]{0x0000000000000000L,0x0000200F00000000L});
    public static final BitSet FOLLOW_set_in_operation_conflict_clause1669 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expr_in_ordering_term1694 = new BitSet(new long[]{0x0000000000000002L,0x0000C00000000000L});
    public static final BitSet FOLLOW_ASC_in_ordering_term1699 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DESC_in_ordering_term1703 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ORDER_in_operation_limited_clause1733 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_BY_in_operation_limited_clause1735 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFCFFFFFFC30L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_ordering_term_in_operation_limited_clause1737 = new BitSet(new long[]{0x0000200000000000L,0x0002000000000000L});
    public static final BitSet FOLLOW_COMMA_in_operation_limited_clause1740 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFCFFFFFFC30L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_ordering_term_in_operation_limited_clause1742 = new BitSet(new long[]{0x0000200000000000L,0x0002000000000000L});
    public static final BitSet FOLLOW_LIMIT_in_operation_limited_clause1750 = new BitSet(new long[]{0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_INTEGER_in_operation_limited_clause1754 = new BitSet(new long[]{0x0000200000000002L,0x0004000000000000L});
    public static final BitSet FOLLOW_set_in_operation_limited_clause1757 = new BitSet(new long[]{0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_INTEGER_in_operation_limited_clause1767 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_select_list_in_select_stmt1777 = new BitSet(new long[]{0x0000000000000002L,0x0003000000000000L});
    public static final BitSet FOLLOW_ORDER_in_select_stmt1782 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_BY_in_select_stmt1784 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFCFFFFFFC30L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_ordering_term_in_select_stmt1786 = new BitSet(new long[]{0x0000200000000002L,0x0002000000000000L});
    public static final BitSet FOLLOW_COMMA_in_select_stmt1789 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFCFFFFFFC30L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_ordering_term_in_select_stmt1791 = new BitSet(new long[]{0x0000200000000002L,0x0002000000000000L});
    public static final BitSet FOLLOW_LIMIT_in_select_stmt1800 = new BitSet(new long[]{0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_INTEGER_in_select_stmt1804 = new BitSet(new long[]{0x0000200000000002L,0x0004000000000000L});
    public static final BitSet FOLLOW_OFFSET_in_select_stmt1808 = new BitSet(new long[]{0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_COMMA_in_select_stmt1812 = new BitSet(new long[]{0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_INTEGER_in_select_stmt1817 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_select_core_in_select_list1862 = new BitSet(new long[]{0x0000000000000002L,0x0068000000000000L});
    public static final BitSet FOLLOW_select_op_in_select_list1865 = new BitSet(new long[]{0x0000000000000000L,0x0080000000000000L});
    public static final BitSet FOLLOW_select_core_in_select_list1868 = new BitSet(new long[]{0x0000000000000002L,0x0068000000000000L});
    public static final BitSet FOLLOW_UNION_in_select_op1877 = new BitSet(new long[]{0x0000000000000002L,0x0010000000000000L});
    public static final BitSet FOLLOW_ALL_in_select_op1881 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INTERSECT_in_select_op1887 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EXCEPT_in_select_op1891 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SELECT_in_select_core1900 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFCFFFFFFC70L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_ALL_in_select_core1903 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFCFFFFFFC70L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_DISTINCT_in_select_core1907 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFCFFFFFFC70L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_result_column_in_select_core1911 = new BitSet(new long[]{0x0000200000000002L,0x0700000000000000L});
    public static final BitSet FOLLOW_COMMA_in_select_core1914 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFCFFFFFFC70L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_result_column_in_select_core1916 = new BitSet(new long[]{0x0000200000000002L,0x0700000000000000L});
    public static final BitSet FOLLOW_FROM_in_select_core1921 = new BitSet(new long[]{0x000E176700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_join_source_in_select_core1923 = new BitSet(new long[]{0x0000000000000002L,0x0600000000000000L});
    public static final BitSet FOLLOW_WHERE_in_select_core1928 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFCFFFFFFC30L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_expr_in_select_core1932 = new BitSet(new long[]{0x0000000000000002L,0x0400000000000000L});
    public static final BitSet FOLLOW_GROUP_in_select_core1940 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_BY_in_select_core1942 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFCFFFFFFC30L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_ordering_term_in_select_core1944 = new BitSet(new long[]{0x0000200000000002L,0x0800000000000000L});
    public static final BitSet FOLLOW_COMMA_in_select_core1947 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFCFFFFFFC30L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_ordering_term_in_select_core1949 = new BitSet(new long[]{0x0000200000000002L,0x0800000000000000L});
    public static final BitSet FOLLOW_HAVING_in_select_core1954 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFCFFFFFFC30L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_expr_in_select_core1958 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ASTERISK_in_result_column2028 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_id_in_result_column2036 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_DOT_in_result_column2038 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_ASTERISK_in_result_column2040 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expr_in_result_column2055 = new BitSet(new long[]{0x000E076700000002L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_AS_in_result_column2059 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_id_in_result_column2065 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_single_source_in_join_source2086 = new BitSet(new long[]{0x0000200000000002L,0xF000000000000000L,0x0000000000000003L});
    public static final BitSet FOLLOW_join_op_in_join_source2089 = new BitSet(new long[]{0x000E176700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_single_source_in_join_source2092 = new BitSet(new long[]{0x0000200000000002L,0xF000000000000000L,0x000000000000000FL});
    public static final BitSet FOLLOW_join_constraint_in_join_source2095 = new BitSet(new long[]{0x0000200000000002L,0xF000000000000000L,0x0000000000000003L});
    public static final BitSet FOLLOW_id_in_single_source2112 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_DOT_in_single_source2114 = new BitSet(new long[]{0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_ID_in_single_source2120 = new BitSet(new long[]{0x000000A000000002L,0x0000000000009000L});
    public static final BitSet FOLLOW_AS_in_single_source2124 = new BitSet(new long[]{0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_ID_in_single_source2130 = new BitSet(new long[]{0x000000A000000002L});
    public static final BitSet FOLLOW_INDEXED_in_single_source2135 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_BY_in_single_source2137 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_id_in_single_source2141 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NOT_in_single_source2145 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_INDEXED_in_single_source2147 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_single_source2188 = new BitSet(new long[]{0x0000000000000000L,0x0080000000000000L});
    public static final BitSet FOLLOW_select_stmt_in_single_source2190 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_RPAREN_in_single_source2192 = new BitSet(new long[]{0x0000000000000002L,0x0000000000009000L});
    public static final BitSet FOLLOW_AS_in_single_source2196 = new BitSet(new long[]{0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_ID_in_single_source2202 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_single_source2224 = new BitSet(new long[]{0x000E176700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_join_source_in_single_source2227 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_RPAREN_in_single_source2229 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_COMMA_in_join_op2240 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NATURAL_in_join_op2247 = new BitSet(new long[]{0x0000000000000000L,0xE000000000000000L,0x0000000000000003L});
    public static final BitSet FOLLOW_LEFT_in_join_op2253 = new BitSet(new long[]{0x0000000000000000L,0x4000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_OUTER_in_join_op2258 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_INNER_in_join_op2264 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_CROSS_in_join_op2268 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_JOIN_in_join_op2271 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ON_in_join_constraint2282 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFCFFFFFFC30L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_expr_in_join_constraint2285 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_USING_in_join_constraint2291 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_LPAREN_in_join_constraint2293 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_id_in_join_constraint2297 = new BitSet(new long[]{0x0000600000000000L});
    public static final BitSet FOLLOW_COMMA_in_join_constraint2300 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_id_in_join_constraint2304 = new BitSet(new long[]{0x0000600000000000L});
    public static final BitSet FOLLOW_RPAREN_in_join_constraint2308 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INSERT_in_insert_stmt2327 = new BitSet(new long[]{0x0000010000000000L,0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_operation_conflict_clause_in_insert_stmt2330 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_REPLACE_in_insert_stmt2336 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_INTO_in_insert_stmt2339 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_id_in_insert_stmt2344 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_DOT_in_insert_stmt2346 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_id_in_insert_stmt2352 = new BitSet(new long[]{0x0000100000000000L,0x0080000000000000L,0x00000000000000C0L});
    public static final BitSet FOLLOW_LPAREN_in_insert_stmt2359 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_id_in_insert_stmt2363 = new BitSet(new long[]{0x0000600000000000L});
    public static final BitSet FOLLOW_COMMA_in_insert_stmt2366 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_id_in_insert_stmt2370 = new BitSet(new long[]{0x0000600000000000L});
    public static final BitSet FOLLOW_RPAREN_in_insert_stmt2374 = new BitSet(new long[]{0x0000000000000000L,0x0080000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_VALUES_in_insert_stmt2383 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_LPAREN_in_insert_stmt2385 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFCFFFFFFC30L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_expr_in_insert_stmt2389 = new BitSet(new long[]{0x0000600000000000L});
    public static final BitSet FOLLOW_COMMA_in_insert_stmt2392 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFCFFFFFFC30L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_expr_in_insert_stmt2396 = new BitSet(new long[]{0x0000600000000000L});
    public static final BitSet FOLLOW_RPAREN_in_insert_stmt2400 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_select_stmt_in_insert_stmt2404 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DEFAULT_in_insert_stmt2411 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_VALUES_in_insert_stmt2413 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_UPDATE_in_update_stmt2423 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_operation_conflict_clause_in_update_stmt2426 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_qualified_table_name_in_update_stmt2430 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_SET_in_update_stmt2434 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_update_set_in_update_stmt2438 = new BitSet(new long[]{0x0000200000000002L,0x0203000000000000L});
    public static final BitSet FOLLOW_COMMA_in_update_stmt2441 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_update_set_in_update_stmt2445 = new BitSet(new long[]{0x0000200000000002L,0x0203000000000000L});
    public static final BitSet FOLLOW_WHERE_in_update_stmt2450 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFCFFFFFFC30L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_expr_in_update_stmt2452 = new BitSet(new long[]{0x0000000000000002L,0x0003000000000000L});
    public static final BitSet FOLLOW_operation_limited_clause_in_update_stmt2457 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_id_in_update_set2468 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_EQUALS_in_update_set2470 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFCFFFFFFC30L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_expr_in_update_set2472 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DELETE_in_delete_stmt2480 = new BitSet(new long[]{0x0000000000000000L,0x0100000000000000L});
    public static final BitSet FOLLOW_FROM_in_delete_stmt2482 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_qualified_table_name_in_delete_stmt2484 = new BitSet(new long[]{0x0000000000000002L,0x0203000000000000L});
    public static final BitSet FOLLOW_WHERE_in_delete_stmt2487 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFCFFFFFFC30L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_expr_in_delete_stmt2489 = new BitSet(new long[]{0x0000000000000002L,0x0003000000000000L});
    public static final BitSet FOLLOW_operation_limited_clause_in_delete_stmt2494 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_BEGIN_in_begin_stmt2504 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x000000000000F000L});
    public static final BitSet FOLLOW_set_in_begin_stmt2506 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_TRANSACTION_in_begin_stmt2520 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_commit_stmt2530 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_TRANSACTION_in_commit_stmt2539 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ROLLBACK_in_rollback_stmt2549 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000028000L});
    public static final BitSet FOLLOW_TRANSACTION_in_rollback_stmt2552 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_TO_in_rollback_stmt2557 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_SAVEPOINT_in_rollback_stmt2560 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_id_in_rollback_stmt2566 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SAVEPOINT_in_savepoint_stmt2576 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_id_in_savepoint_stmt2580 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RELEASE_in_release_stmt2588 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_SAVEPOINT_in_release_stmt2591 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_id_in_release_stmt2597 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ON_in_table_conflict_clause2609 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_CONFLICT_in_table_conflict_clause2612 = new BitSet(new long[]{0x0000000000000000L,0x0000200F00000000L});
    public static final BitSet FOLLOW_set_in_table_conflict_clause2615 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CREATE_in_create_virtual_table_stmt2642 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_VIRTUAL_in_create_virtual_table_stmt2644 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000800000L});
    public static final BitSet FOLLOW_TABLE_in_create_virtual_table_stmt2646 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_id_in_create_virtual_table_stmt2651 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_DOT_in_create_virtual_table_stmt2653 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_id_in_create_virtual_table_stmt2659 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_USING_in_create_virtual_table_stmt2663 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_id_in_create_virtual_table_stmt2667 = new BitSet(new long[]{0x0000100000000002L});
    public static final BitSet FOLLOW_LPAREN_in_create_virtual_table_stmt2670 = new BitSet(new long[]{0x0F0F8FE700000000L,0xFFFFFFCF8E1FF800L,0x03FFB7FEF7FFFFFFL});
    public static final BitSet FOLLOW_column_def_in_create_virtual_table_stmt2672 = new BitSet(new long[]{0x0000600000000000L});
    public static final BitSet FOLLOW_COMMA_in_create_virtual_table_stmt2675 = new BitSet(new long[]{0x0F0F8FE700000000L,0xFFFFFFCF8E1FF800L,0x03FFB7FEF7FFFFFFL});
    public static final BitSet FOLLOW_column_def_in_create_virtual_table_stmt2677 = new BitSet(new long[]{0x0000600000000000L});
    public static final BitSet FOLLOW_RPAREN_in_create_virtual_table_stmt2681 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CREATE_in_create_table_stmt2691 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000001800000L});
    public static final BitSet FOLLOW_TEMPORARY_in_create_table_stmt2693 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000800000L});
    public static final BitSet FOLLOW_TABLE_in_create_table_stmt2696 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_IF_in_create_table_stmt2699 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_NOT_in_create_table_stmt2701 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_EXISTS_in_create_table_stmt2703 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_id_in_create_table_stmt2710 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_DOT_in_create_table_stmt2712 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_id_in_create_table_stmt2718 = new BitSet(new long[]{0x0000100000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_LPAREN_in_create_table_stmt2726 = new BitSet(new long[]{0x0F0F8FE700000000L,0xFFFFFFCF8E1FF800L,0x03FFB7FEF7FFFFFFL});
    public static final BitSet FOLLOW_column_def_in_create_table_stmt2728 = new BitSet(new long[]{0x0000600000000000L});
    public static final BitSet FOLLOW_COMMA_in_create_table_stmt2731 = new BitSet(new long[]{0x0F0F8FE700000000L,0xFFFFFFCF8E1FF800L,0x03FFB7FEF7FFFFFFL});
    public static final BitSet FOLLOW_column_def_in_create_table_stmt2733 = new BitSet(new long[]{0x0000600000000000L});
    public static final BitSet FOLLOW_COMMA_in_create_table_stmt2738 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000E18000000L});
    public static final BitSet FOLLOW_table_constraint_in_create_table_stmt2740 = new BitSet(new long[]{0x0000600000000000L});
    public static final BitSet FOLLOW_RPAREN_in_create_table_stmt2744 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_AS_in_create_table_stmt2750 = new BitSet(new long[]{0x0000000000000000L,0x0080000000000000L});
    public static final BitSet FOLLOW_select_stmt_in_create_table_stmt2752 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_id_column_def_in_column_def2808 = new BitSet(new long[]{0x0004008000000002L,0x0000000000001800L,0x0000001618000080L});
    public static final BitSet FOLLOW_type_name_in_column_def2812 = new BitSet(new long[]{0x0004008000000002L,0x0000000000000800L,0x0000001618000080L});
    public static final BitSet FOLLOW_column_constraint_in_column_def2816 = new BitSet(new long[]{0x0004008000000002L,0x0000000000000800L,0x0000001618000080L});
    public static final BitSet FOLLOW_CONSTRAINT_in_column_constraint2842 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_id_in_column_constraint2846 = new BitSet(new long[]{0x0004008000000000L,0x0000000000000800L,0x0000001618000080L});
    public static final BitSet FOLLOW_column_constraint_pk_in_column_constraint2854 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_column_constraint_null_in_column_constraint2860 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_column_constraint_not_null_in_column_constraint2866 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_column_constraint_not_for_replication_in_column_constraint2872 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_column_constraint_unique_in_column_constraint2878 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_column_constraint_check_in_column_constraint2884 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_column_constraint_default_in_column_constraint2890 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_column_constraint_collate_in_column_constraint2896 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_fk_clause_in_column_constraint2902 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PRIMARY_in_column_constraint_pk2967 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_KEY_in_column_constraint_pk2970 = new BitSet(new long[]{0x0000000000000002L,0x0000C00000000000L,0x0000000040000004L});
    public static final BitSet FOLLOW_set_in_column_constraint_pk2973 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000040000004L});
    public static final BitSet FOLLOW_table_conflict_clause_in_column_constraint_pk2982 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_AUTOINCREMENT_in_column_constraint_pk2986 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NOT_in_column_constraint_not_null2995 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_NULL_in_column_constraint_not_null2997 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_table_conflict_clause_in_column_constraint_not_null2999 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NOT_in_column_constraint_not_for_replication3016 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000080000000L});
    public static final BitSet FOLLOW_FOR_in_column_constraint_not_for_replication3018 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000100000000L});
    public static final BitSet FOLLOW_REPLICATION_in_column_constraint_not_for_replication3020 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_table_conflict_clause_in_column_constraint_not_for_replication3022 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NULL_in_column_constraint_null3030 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_table_conflict_clause_in_column_constraint_null3032 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_UNIQUE_in_column_constraint_unique3040 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_table_conflict_clause_in_column_constraint_unique3043 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CHECK_in_column_constraint_check3051 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_LPAREN_in_column_constraint_check3054 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFCFFFFFFC30L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_expr_in_column_constraint_check3057 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_RPAREN_in_column_constraint_check3059 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INTEGER_in_numeric_literal_value3070 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FLOAT_in_numeric_literal_value3084 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_signed_default_number3102 = new BitSet(new long[]{0x0000000000000000L,0x0000000000600000L});
    public static final BitSet FOLLOW_numeric_literal_value_in_signed_default_number3111 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DEFAULT_in_column_constraint_default3119 = new BitSet(new long[]{0x0004100000000000L,0x000000000FE00030L});
    public static final BitSet FOLLOW_signed_default_number_in_column_constraint_default3123 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_literal_value_in_column_constraint_default3127 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_column_constraint_default3131 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFCFFFFFFC30L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_expr_in_column_constraint_default3134 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_RPAREN_in_column_constraint_default3136 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_COLLATE_in_column_constraint_collate3145 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_id_in_column_constraint_collate3150 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CONSTRAINT_in_table_constraint3159 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_id_in_table_constraint3163 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000E18000000L});
    public static final BitSet FOLLOW_table_constraint_pk_in_table_constraint3171 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_table_constraint_unique_in_table_constraint3177 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_table_constraint_check_in_table_constraint3183 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_table_constraint_fk_in_table_constraint3189 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PRIMARY_in_table_constraint_pk3229 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_KEY_in_table_constraint_pk3231 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_LPAREN_in_table_constraint_pk3235 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_id_in_table_constraint_pk3239 = new BitSet(new long[]{0x0000600000000000L});
    public static final BitSet FOLLOW_COMMA_in_table_constraint_pk3242 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_id_in_table_constraint_pk3246 = new BitSet(new long[]{0x0000600000000000L});
    public static final BitSet FOLLOW_RPAREN_in_table_constraint_pk3250 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_table_conflict_clause_in_table_constraint_pk3252 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_UNIQUE_in_table_constraint_unique3277 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_LPAREN_in_table_constraint_unique3281 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_id_in_table_constraint_unique3285 = new BitSet(new long[]{0x0000600000000000L});
    public static final BitSet FOLLOW_COMMA_in_table_constraint_unique3288 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_id_in_table_constraint_unique3292 = new BitSet(new long[]{0x0000600000000000L});
    public static final BitSet FOLLOW_RPAREN_in_table_constraint_unique3296 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_table_conflict_clause_in_table_constraint_unique3298 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CHECK_in_table_constraint_check3323 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_LPAREN_in_table_constraint_check3326 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFCFFFFFFC30L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_expr_in_table_constraint_check3329 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_RPAREN_in_table_constraint_check3331 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FOREIGN_in_table_constraint_fk3339 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_KEY_in_table_constraint_fk3341 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_LPAREN_in_table_constraint_fk3343 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_id_in_table_constraint_fk3347 = new BitSet(new long[]{0x0000600000000000L});
    public static final BitSet FOLLOW_COMMA_in_table_constraint_fk3350 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_id_in_table_constraint_fk3354 = new BitSet(new long[]{0x0000600000000000L});
    public static final BitSet FOLLOW_RPAREN_in_table_constraint_fk3359 = new BitSet(new long[]{0x0004008000000000L,0x0000000000000800L,0x0000001618000080L});
    public static final BitSet FOLLOW_fk_clause_in_table_constraint_fk3361 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_REFERENCES_in_fk_clause3384 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_id_in_fk_clause3388 = new BitSet(new long[]{0x0800108000000002L,0x0000000000000000L,0x0000008000000004L});
    public static final BitSet FOLLOW_LPAREN_in_fk_clause3393 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_id_in_fk_clause3397 = new BitSet(new long[]{0x0000600000000000L});
    public static final BitSet FOLLOW_COMMA_in_fk_clause3400 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_id_in_fk_clause3404 = new BitSet(new long[]{0x0000600000000000L});
    public static final BitSet FOLLOW_RPAREN_in_fk_clause3408 = new BitSet(new long[]{0x0800008000000002L,0x0000000000000000L,0x0000008000000004L});
    public static final BitSet FOLLOW_fk_clause_action_in_fk_clause3414 = new BitSet(new long[]{0x0800008000000002L,0x0000000000000000L,0x0000008000000004L});
    public static final BitSet FOLLOW_fk_clause_deferrable_in_fk_clause3417 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ON_in_fk_clause_action3451 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000510L});
    public static final BitSet FOLLOW_set_in_fk_clause_action3454 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000006000000200L});
    public static final BitSet FOLLOW_SET_in_fk_clause_action3467 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_NULL_in_fk_clause_action3470 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SET_in_fk_clause_action3474 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_DEFAULT_in_fk_clause_action3477 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CASCADE_in_fk_clause_action3481 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RESTRICT_in_fk_clause_action3485 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MATCH_in_fk_clause_action3492 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_id_in_fk_clause_action3495 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NOT_in_fk_clause_deferrable3503 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000008000000000L});
    public static final BitSet FOLLOW_DEFERRABLE_in_fk_clause_deferrable3507 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000010000000000L});
    public static final BitSet FOLLOW_INITIALLY_in_fk_clause_deferrable3511 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_DEFERRED_in_fk_clause_deferrable3514 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INITIALLY_in_fk_clause_deferrable3518 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_IMMEDIATE_in_fk_clause_deferrable3521 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DROP_in_drop_table_stmt3531 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000800000L});
    public static final BitSet FOLLOW_TABLE_in_drop_table_stmt3533 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_IF_in_drop_table_stmt3536 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_EXISTS_in_drop_table_stmt3538 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_id_in_drop_table_stmt3545 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_DOT_in_drop_table_stmt3547 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_id_in_drop_table_stmt3553 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ALTER_in_alter_table_stmt3583 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000800000L});
    public static final BitSet FOLLOW_TABLE_in_alter_table_stmt3585 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFFFFEFFFFFFFFL});
    public static final BitSet FOLLOW_ONLY_in_alter_table_stmt3587 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_id_in_alter_table_stmt3593 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_DOT_in_alter_table_stmt3595 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_id_in_alter_table_stmt3601 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000300000000000L});
    public static final BitSet FOLLOW_RENAME_in_alter_table_stmt3606 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_TO_in_alter_table_stmt3608 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_id_in_alter_table_stmt3612 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ADD_in_alter_table_stmt3616 = new BitSet(new long[]{0x0F0F8FE700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEF7FFFFFFL});
    public static final BitSet FOLLOW_COLUMN_in_alter_table_stmt3619 = new BitSet(new long[]{0x0F0F8FE700000000L,0xFFFFFFCF8E1FF800L,0x03FFB7FEF7FFFFFFL});
    public static final BitSet FOLLOW_column_def_in_alter_table_stmt3623 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ADD_in_alter_table_stmt3627 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000E18000000L});
    public static final BitSet FOLLOW_table_constraint_in_alter_table_stmt3629 = new BitSet(new long[]{0x0000200000000002L});
    public static final BitSet FOLLOW_COMMA_in_alter_table_stmt3632 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000E18000000L});
    public static final BitSet FOLLOW_table_constraint_in_alter_table_stmt3634 = new BitSet(new long[]{0x0000200000000002L});
    public static final BitSet FOLLOW_CREATE_in_create_view_stmt3645 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000800001000000L});
    public static final BitSet FOLLOW_TEMPORARY_in_create_view_stmt3647 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000800000000000L});
    public static final BitSet FOLLOW_VIEW_in_create_view_stmt3650 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_IF_in_create_view_stmt3653 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_NOT_in_create_view_stmt3655 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_EXISTS_in_create_view_stmt3657 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_id_in_create_view_stmt3664 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_DOT_in_create_view_stmt3666 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_id_in_create_view_stmt3672 = new BitSet(new long[]{0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_AS_in_create_view_stmt3674 = new BitSet(new long[]{0x0000000000000000L,0x0080000000000000L});
    public static final BitSet FOLLOW_select_stmt_in_create_view_stmt3676 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DROP_in_drop_view_stmt3684 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000800000000000L});
    public static final BitSet FOLLOW_VIEW_in_drop_view_stmt3686 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_IF_in_drop_view_stmt3689 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_EXISTS_in_drop_view_stmt3691 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_id_in_drop_view_stmt3698 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_DOT_in_drop_view_stmt3700 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_id_in_drop_view_stmt3706 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CREATE_in_create_index_stmt3714 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0001000200000000L});
    public static final BitSet FOLLOW_UNIQUE_in_create_index_stmt3717 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0001000000000000L});
    public static final BitSet FOLLOW_INDEX_in_create_index_stmt3721 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_IF_in_create_index_stmt3724 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_NOT_in_create_index_stmt3726 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_EXISTS_in_create_index_stmt3728 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_id_in_create_index_stmt3735 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_DOT_in_create_index_stmt3737 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_id_in_create_index_stmt3743 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_ON_in_create_index_stmt3747 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_id_in_create_index_stmt3751 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_LPAREN_in_create_index_stmt3753 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_indexed_column_in_create_index_stmt3757 = new BitSet(new long[]{0x0000600000000000L});
    public static final BitSet FOLLOW_COMMA_in_create_index_stmt3760 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_indexed_column_in_create_index_stmt3764 = new BitSet(new long[]{0x0000600000000000L});
    public static final BitSet FOLLOW_RPAREN_in_create_index_stmt3768 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_id_in_indexed_column3814 = new BitSet(new long[]{0x0000000000000002L,0x0000C00000000800L});
    public static final BitSet FOLLOW_COLLATE_in_indexed_column3817 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_id_in_indexed_column3821 = new BitSet(new long[]{0x0000000000000002L,0x0000C00000000000L});
    public static final BitSet FOLLOW_ASC_in_indexed_column3826 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DESC_in_indexed_column3830 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DROP_in_drop_index_stmt3861 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0001000000000000L});
    public static final BitSet FOLLOW_INDEX_in_drop_index_stmt3863 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_IF_in_drop_index_stmt3866 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_EXISTS_in_drop_index_stmt3868 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_id_in_drop_index_stmt3875 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_DOT_in_drop_index_stmt3877 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_id_in_drop_index_stmt3883 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CREATE_in_create_trigger_stmt3913 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0002000001000000L});
    public static final BitSet FOLLOW_TEMPORARY_in_create_trigger_stmt3915 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0002000000000000L});
    public static final BitSet FOLLOW_TRIGGER_in_create_trigger_stmt3918 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_IF_in_create_trigger_stmt3921 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_NOT_in_create_trigger_stmt3923 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_EXISTS_in_create_trigger_stmt3925 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_id_in_create_trigger_stmt3932 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_DOT_in_create_trigger_stmt3934 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_id_in_create_trigger_stmt3940 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x001C000000000510L});
    public static final BitSet FOLLOW_BEFORE_in_create_trigger_stmt3945 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000510L});
    public static final BitSet FOLLOW_AFTER_in_create_trigger_stmt3949 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000510L});
    public static final BitSet FOLLOW_INSTEAD_in_create_trigger_stmt3953 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_OF_in_create_trigger_stmt3955 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000510L});
    public static final BitSet FOLLOW_DELETE_in_create_trigger_stmt3960 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_INSERT_in_create_trigger_stmt3964 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_UPDATE_in_create_trigger_stmt3968 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0020000000000004L});
    public static final BitSet FOLLOW_OF_in_create_trigger_stmt3971 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_id_in_create_trigger_stmt3975 = new BitSet(new long[]{0x0000200000000000L,0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_COMMA_in_create_trigger_stmt3978 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_id_in_create_trigger_stmt3982 = new BitSet(new long[]{0x0000200000000000L,0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_ON_in_create_trigger_stmt3991 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_id_in_create_trigger_stmt3995 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L,0x0000000080000800L});
    public static final BitSet FOLLOW_FOR_in_create_trigger_stmt3998 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0040000000000000L});
    public static final BitSet FOLLOW_EACH_in_create_trigger_stmt4000 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0080000000000000L});
    public static final BitSet FOLLOW_ROW_in_create_trigger_stmt4002 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L,0x0000000000000800L});
    public static final BitSet FOLLOW_WHEN_in_create_trigger_stmt4007 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFCFFFFFFC30L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_expr_in_create_trigger_stmt4009 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_BEGIN_in_create_trigger_stmt4015 = new BitSet(new long[]{0x0000000000000000L,0x0080200000000000L,0x0000000000000510L});
    public static final BitSet FOLLOW_update_stmt_in_create_trigger_stmt4019 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_insert_stmt_in_create_trigger_stmt4023 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_delete_stmt_in_create_trigger_stmt4027 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_select_stmt_in_create_trigger_stmt4031 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_SEMI_in_create_trigger_stmt4034 = new BitSet(new long[]{0x0000000000000000L,0x0080200000040000L,0x0000000000000510L});
    public static final BitSet FOLLOW_END_in_create_trigger_stmt4038 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DROP_in_drop_trigger_stmt4046 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0002000000000000L});
    public static final BitSet FOLLOW_TRIGGER_in_drop_trigger_stmt4048 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_IF_in_drop_trigger_stmt4051 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_EXISTS_in_drop_trigger_stmt4053 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_id_in_drop_trigger_stmt4060 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_DOT_in_drop_trigger_stmt4062 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_id_in_drop_trigger_stmt4068 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_META_COMMENT_in_table_comment4076 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_ON_in_table_comment4078 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000800000L});
    public static final BitSet FOLLOW_TABLE_in_table_comment4080 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_id_in_table_comment4085 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_DOT_in_table_comment4087 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_id_in_table_comment4093 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_IS_in_table_comment4095 = new BitSet(new long[]{0x0000000000000000L,0x0000000000800000L});
    public static final BitSet FOLLOW_STRING_in_table_comment4099 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_META_COMMENT_in_col_comment4108 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_ON_in_col_comment4110 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000400000000000L});
    public static final BitSet FOLLOW_COLUMN_in_col_comment4112 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_id_in_col_comment4117 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_DOT_in_col_comment4119 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFCF8E1FF800L,0x03FFF7FEFFFFFFFFL});
    public static final BitSet FOLLOW_id_in_col_comment4125 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_IS_in_col_comment4127 = new BitSet(new long[]{0x0000000000000000L,0x0000000000800000L});
    public static final BitSet FOLLOW_STRING_in_col_comment4131 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_id4142 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_keyword_in_id4146 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_keyword4153 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_id_column_def4832 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_keyword_column_def_in_id_column_def4836 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_keyword_column_def4843 = new BitSet(new long[]{0x0000000000000002L});

}