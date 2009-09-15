// $ANTLR 3.1.2 /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g 2009-09-15 11:14:07

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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "ALIAS", "BIND", "BIND_NAME", "BLOB_LITERAL", "COLUMN_CONSTRAINT", "COLUMN_EXPRESSION", "COLUMNS", "CONSTRAINTS", "CREATE_INDEX", "CREATE_TABLE", "DROP_INDEX", "DROP_TABLE", "FLOAT_LITERAL", "FUNCTION_LITERAL", "FUNCTION_EXPRESSION", "ID_LITERAL", "IN_VALUES", "IN_TABLE", "INTEGER_LITERAL", "IS_NULL", "NOT_NULL", "OPTIONS", "ORDERING", "SELECT_CORE", "STRING_LITERAL", "TABLE_CONSTRAINT", "TYPE", "TYPE_PARAMS", "EXPLAIN", "QUERY", "PLAN", "SEMI", "DOT", "INDEXED", "BY", "NOT", "OR", "AND", "ESCAPE", "IN", "LPAREN", "COMMA", "RPAREN", "ISNULL", "NOTNULL", "IS", "NULL", "BETWEEN", "EQUALS", "EQUALS2", "NOT_EQUALS", "NOT_EQUALS2", "LIKE", "GLOB", "REGEXP", "MATCH", "LESS", "LESS_OR_EQ", "GREATER", "GREATER_OR_EQ", "SHIFT_LEFT", "SHIFT_RIGHT", "AMPERSAND", "PIPE", "PLUS", "MINUS", "ASTERISK", "SLASH", "PERCENT", "DOUBLE_PIPE", "TILDA", "COLLATE", "ID", "DISTINCT", "CAST", "AS", "CASE", "ELSE", "END", "WHEN", "THEN", "INTEGER", "FLOAT", "STRING", "BLOB", "CURRENT_TIME", "CURRENT_DATE", "CURRENT_TIMESTAMP", "QUESTION", "COLON", "AT", "RAISE", "IGNORE", "ROLLBACK", "ABORT", "FAIL", "MAX", "PRAGMA", "ATTACH", "DATABASE", "DETACH", "ANALYZE", "REINDEX", "VACUUM", "REPLACE", "ASC", "DESC", "ORDER", "LIMIT", "OFFSET", "UNION", "ALL", "INTERSECT", "EXCEPT", "SELECT", "FROM", "WHERE", "GROUP", "HAVING", "NATURAL", "LEFT", "OUTER", "INNER", "CROSS", "JOIN", "ON", "USING", "INSERT", "INTO", "VALUES", "DEFAULT", "UPDATE", "SET", "DELETE", "BEGIN", "DEFERRED", "IMMEDIATE", "EXCLUSIVE", "TRANSACTION", "COMMIT", "TO", "SAVEPOINT", "RELEASE", "CONFLICT", "CREATE", "VIRTUAL", "TABLE", "TEMPORARY", "IF", "EXISTS", "CONSTRAINT", "PRIMARY", "KEY", "AUTOINCREMENT", "FOR", "REPLICATION", "UNIQUE", "CHECK", "FOREIGN", "REFERENCES", "CASCADE", "RESTRICT", "DEFERRABLE", "INITIALLY", "DROP", "ALTER", "RENAME", "ADD", "COLUMN", "VIEW", "INDEX", "TRIGGER", "BEFORE", "AFTER", "INSTEAD", "OF", "EACH", "ROW", "DOLLAR", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "ID_START", "ESCAPE_SEQ", "FLOAT_EXP", "COMMENT", "LINE_COMMENT", "WS"
    };
    public static final int ROW=181;
    public static final int BLOB_LITERAL=7;
    public static final int TYPE_PARAMS=31;
    public static final int NOT=39;
    public static final int EXCEPT=117;
    public static final int EOF=-1;
    public static final int FOREIGN=162;
    public static final int TYPE=30;
    public static final int RPAREN=46;
    public static final int CREATE=148;
    public static final int STRING_LITERAL=28;
    public static final int IS_NULL=23;
    public static final int USING=130;
    public static final int BIND=5;
    public static final int BEGIN=138;
    public static final int REGEXP=58;
    public static final int ANALYZE=105;
    public static final int FUNCTION_LITERAL=17;
    public static final int CONFLICT=147;
    public static final int LESS_OR_EQ=61;
    public static final int ATTACH=102;
    public static final int VIRTUAL=149;
    public static final int D=186;
    public static final int E=187;
    public static final int F=188;
    public static final int G=189;
    public static final int BLOB=88;
    public static final int A=183;
    public static final int B=184;
    public static final int ASC=109;
    public static final int C=185;
    public static final int L=194;
    public static final int M=195;
    public static final int N=196;
    public static final int O=197;
    public static final int TRANSACTION=142;
    public static final int KEY=156;
    public static final int H=190;
    public static final int I=191;
    public static final int BIND_NAME=6;
    public static final int J=192;
    public static final int ELSE=81;
    public static final int K=193;
    public static final int U=203;
    public static final int T=202;
    public static final int W=205;
    public static final int IN_VALUES=20;
    public static final int V=204;
    public static final int Q=199;
    public static final int P=198;
    public static final int S=201;
    public static final int R=200;
    public static final int ROLLBACK=97;
    public static final int FAIL=99;
    public static final int Y=207;
    public static final int RESTRICT=165;
    public static final int X=206;
    public static final int Z=208;
    public static final int INTERSECT=116;
    public static final int GROUP=121;
    public static final int DROP_INDEX=14;
    public static final int WS=214;
    public static final int PLAN=34;
    public static final int ALIAS=4;
    public static final int END=82;
    public static final int CONSTRAINT=154;
    public static final int REPLICATION=159;
    public static final int RENAME=170;
    public static final int ALTER=169;
    public static final int ISNULL=47;
    public static final int TABLE=150;
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
    public static final int OFFSET=113;
    public static final int REPLACE=108;
    public static final int LEFT=124;
    public static final int COLUMN=172;
    public static final int PLUS=68;
    public static final int PIPE=67;
    public static final int EXISTS=153;
    public static final int LIKE=56;
    public static final int COLLATE=75;
    public static final int ADD=171;
    public static final int INTEGER=85;
    public static final int OUTER=125;
    public static final int BY=38;
    public static final int DEFERRABLE=166;
    public static final int TO=144;
    public static final int AMPERSAND=66;
    public static final int SET=136;
    public static final int HAVING=122;
    public static final int MINUS=69;
    public static final int IGNORE=96;
    public static final int SEMI=35;
    public static final int UNION=114;
    public static final int COLUMN_CONSTRAINT=8;
    public static final int COLON=93;
    public static final int FLOAT_EXP=211;
    public static final int COLUMNS=10;
    public static final int COMMIT=143;
    public static final int IN_TABLE=21;
    public static final int DATABASE=103;
    public static final int VACUUM=107;
    public static final int DROP=168;
    public static final int DETACH=104;
    public static final int WHEN=83;
    public static final int NATURAL=123;
    public static final int BETWEEN=51;
    public static final int OPTIONS=25;
    public static final int STRING=87;
    public static final int CAST=78;
    public static final int TABLE_CONSTRAINT=29;
    public static final int CURRENT_TIME=89;
    public static final int ID_LITERAL=19;
    public static final int TRIGGER=175;
    public static final int CASE=80;
    public static final int EQUALS=52;
    public static final int CASCADE=164;
    public static final int RELEASE=146;
    public static final int EXPLAIN=32;
    public static final int GREATER=62;
    public static final int ESCAPE=42;
    public static final int INSERT=131;
    public static final int SAVEPOINT=145;
    public static final int LESS=60;
    public static final int RAISE=95;
    public static final int EACH=180;
    public static final int COMMENT=212;
    public static final int ABORT=98;
    public static final int SELECT=118;
    public static final int INTO=132;
    public static final int UNIQUE=160;
    public static final int GLOB=57;
    public static final int VIEW=173;
    public static final int LINE_COMMENT=213;
    public static final int NULL=50;
    public static final int ON=129;
    public static final int MATCH=59;
    public static final int PRIMARY=155;
    public static final int DELETE=137;
    public static final int OF=179;
    public static final int SHIFT_LEFT=64;
    public static final int SHIFT_RIGHT=65;
    public static final int INTEGER_LITERAL=22;
    public static final int FUNCTION_EXPRESSION=18;
    public static final int OR=40;
    public static final int QUERY=33;
    public static final int CHECK=161;
    public static final int FROM=119;
    public static final int DISTINCT=77;
    public static final int TEMPORARY=151;
    public static final int CURRENT_DATE=90;
    public static final int DOLLAR=182;
    public static final int CONSTRAINTS=11;
    public static final int WHERE=120;
    public static final int COLUMN_EXPRESSION=9;
    public static final int INNER=126;
    public static final int ORDER=111;
    public static final int LIMIT=112;
    public static final int PRAGMA=101;
    public static final int MAX=100;
    public static final int UPDATE=135;
    public static final int DEFERRED=139;
    public static final int FOR=158;
    public static final int SELECT_CORE=27;
    public static final int EXCLUSIVE=141;
    public static final int ID=76;
    public static final int AND=41;
    public static final int CROSS=127;
    public static final int IF=152;
    public static final int INDEX=174;
    public static final int TILDA=74;
    public static final int IN=43;
    public static final int COMMA=45;
    public static final int CREATE_TABLE=13;
    public static final int REFERENCES=163;
    public static final int IS=49;
    public static final int ALL=115;
    public static final int DOT=36;
    public static final int CURRENT_TIMESTAMP=91;
    public static final int INITIALLY=167;
    public static final int REINDEX=106;
    public static final int EQUALS2=53;
    public static final int PERCENT=72;
    public static final int AUTOINCREMENT=157;
    public static final int NOT_EQUALS2=55;
    public static final int DEFAULT=134;
    public static final int VALUES=133;
    public static final int BEFORE=176;
    public static final int AFTER=177;
    public static final int INSTEAD=178;
    public static final int JOIN=128;
    public static final int FLOAT_LITERAL=16;
    public static final int INDEXED=37;
    public static final int CREATE_INDEX=12;
    public static final int QUESTION=92;
    public static final int ORDERING=26;
    public static final int ESCAPE_SEQ=210;
    public static final int IMMEDIATE=140;
    public static final int DESC=110;
    public static final int DROP_TABLE=15;
    public static final int ID_START=209;

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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:102:1: sql_stmt_core : ( pragma_stmt | attach_stmt | detach_stmt | analyze_stmt | reindex_stmt | vacuum_stmt | select_stmt | insert_stmt | update_stmt | delete_stmt | begin_stmt | commit_stmt | rollback_stmt | savepoint_stmt | release_stmt | create_virtual_table_stmt | create_table_stmt | drop_table_stmt | alter_table_stmt | create_view_stmt | drop_view_stmt | create_index_stmt | drop_index_stmt | create_trigger_stmt | drop_trigger_stmt );
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



        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:103:3: ( pragma_stmt | attach_stmt | detach_stmt | analyze_stmt | reindex_stmt | vacuum_stmt | select_stmt | insert_stmt | update_stmt | delete_stmt | begin_stmt | commit_stmt | rollback_stmt | savepoint_stmt | release_stmt | create_virtual_table_stmt | create_table_stmt | drop_table_stmt | alter_table_stmt | create_view_stmt | drop_view_stmt | create_index_stmt | drop_index_stmt | create_trigger_stmt | drop_trigger_stmt )
            int alt4=25;
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

                    pushFollow(FOLLOW_select_stmt_in_sql_stmt_core263);
                    select_stmt13=select_stmt();

                    state._fsp--;

                    adaptor.addChild(root_0, select_stmt13.getTree());

                    }
                    break;
                case 8 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:111:5: insert_stmt
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_insert_stmt_in_sql_stmt_core269);
                    insert_stmt14=insert_stmt();

                    state._fsp--;

                    adaptor.addChild(root_0, insert_stmt14.getTree());

                    }
                    break;
                case 9 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:112:5: update_stmt
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_update_stmt_in_sql_stmt_core275);
                    update_stmt15=update_stmt();

                    state._fsp--;

                    adaptor.addChild(root_0, update_stmt15.getTree());

                    }
                    break;
                case 10 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:113:5: delete_stmt
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_delete_stmt_in_sql_stmt_core281);
                    delete_stmt16=delete_stmt();

                    state._fsp--;

                    adaptor.addChild(root_0, delete_stmt16.getTree());

                    }
                    break;
                case 11 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:114:5: begin_stmt
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_begin_stmt_in_sql_stmt_core287);
                    begin_stmt17=begin_stmt();

                    state._fsp--;

                    adaptor.addChild(root_0, begin_stmt17.getTree());

                    }
                    break;
                case 12 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:115:5: commit_stmt
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_commit_stmt_in_sql_stmt_core293);
                    commit_stmt18=commit_stmt();

                    state._fsp--;

                    adaptor.addChild(root_0, commit_stmt18.getTree());

                    }
                    break;
                case 13 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:116:5: rollback_stmt
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_rollback_stmt_in_sql_stmt_core299);
                    rollback_stmt19=rollback_stmt();

                    state._fsp--;

                    adaptor.addChild(root_0, rollback_stmt19.getTree());

                    }
                    break;
                case 14 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:117:5: savepoint_stmt
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_savepoint_stmt_in_sql_stmt_core305);
                    savepoint_stmt20=savepoint_stmt();

                    state._fsp--;

                    adaptor.addChild(root_0, savepoint_stmt20.getTree());

                    }
                    break;
                case 15 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:118:5: release_stmt
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_release_stmt_in_sql_stmt_core311);
                    release_stmt21=release_stmt();

                    state._fsp--;

                    adaptor.addChild(root_0, release_stmt21.getTree());

                    }
                    break;
                case 16 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:120:5: create_virtual_table_stmt
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_create_virtual_table_stmt_in_sql_stmt_core320);
                    create_virtual_table_stmt22=create_virtual_table_stmt();

                    state._fsp--;

                    adaptor.addChild(root_0, create_virtual_table_stmt22.getTree());

                    }
                    break;
                case 17 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:121:5: create_table_stmt
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_create_table_stmt_in_sql_stmt_core326);
                    create_table_stmt23=create_table_stmt();

                    state._fsp--;

                    adaptor.addChild(root_0, create_table_stmt23.getTree());

                    }
                    break;
                case 18 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:122:5: drop_table_stmt
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_drop_table_stmt_in_sql_stmt_core332);
                    drop_table_stmt24=drop_table_stmt();

                    state._fsp--;

                    adaptor.addChild(root_0, drop_table_stmt24.getTree());

                    }
                    break;
                case 19 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:123:5: alter_table_stmt
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_alter_table_stmt_in_sql_stmt_core338);
                    alter_table_stmt25=alter_table_stmt();

                    state._fsp--;

                    adaptor.addChild(root_0, alter_table_stmt25.getTree());

                    }
                    break;
                case 20 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:124:5: create_view_stmt
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_create_view_stmt_in_sql_stmt_core344);
                    create_view_stmt26=create_view_stmt();

                    state._fsp--;

                    adaptor.addChild(root_0, create_view_stmt26.getTree());

                    }
                    break;
                case 21 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:125:5: drop_view_stmt
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_drop_view_stmt_in_sql_stmt_core350);
                    drop_view_stmt27=drop_view_stmt();

                    state._fsp--;

                    adaptor.addChild(root_0, drop_view_stmt27.getTree());

                    }
                    break;
                case 22 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:126:5: create_index_stmt
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_create_index_stmt_in_sql_stmt_core356);
                    create_index_stmt28=create_index_stmt();

                    state._fsp--;

                    adaptor.addChild(root_0, create_index_stmt28.getTree());

                    }
                    break;
                case 23 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:127:5: drop_index_stmt
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_drop_index_stmt_in_sql_stmt_core362);
                    drop_index_stmt29=drop_index_stmt();

                    state._fsp--;

                    adaptor.addChild(root_0, drop_index_stmt29.getTree());

                    }
                    break;
                case 24 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:128:5: create_trigger_stmt
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_create_trigger_stmt_in_sql_stmt_core368);
                    create_trigger_stmt30=create_trigger_stmt();

                    state._fsp--;

                    adaptor.addChild(root_0, create_trigger_stmt30.getTree());

                    }
                    break;
                case 25 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:129:5: drop_trigger_stmt
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_drop_trigger_stmt_in_sql_stmt_core374);
                    drop_trigger_stmt31=drop_trigger_stmt();

                    state._fsp--;

                    adaptor.addChild(root_0, drop_trigger_stmt31.getTree());

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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:132:1: qualified_table_name : (database_name= id DOT )? table_name= id ( INDEXED BY index_name= id | NOT INDEXED )? ;
    public final SqlParser.qualified_table_name_return qualified_table_name() throws RecognitionException {
        SqlParser.qualified_table_name_return retval = new SqlParser.qualified_table_name_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token DOT32=null;
        Token INDEXED33=null;
        Token BY34=null;
        Token NOT35=null;
        Token INDEXED36=null;
        SqlParser.id_return database_name = null;

        SqlParser.id_return table_name = null;

        SqlParser.id_return index_name = null;


        Object DOT32_tree=null;
        Object INDEXED33_tree=null;
        Object BY34_tree=null;
        Object NOT35_tree=null;
        Object INDEXED36_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:132:21: ( (database_name= id DOT )? table_name= id ( INDEXED BY index_name= id | NOT INDEXED )? )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:132:23: (database_name= id DOT )? table_name= id ( INDEXED BY index_name= id | NOT INDEXED )?
            {
            root_0 = (Object)adaptor.nil();

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:132:23: (database_name= id DOT )?
            int alt5=2;
            alt5 = dfa5.predict(input);
            switch (alt5) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:132:24: database_name= id DOT
                    {
                    pushFollow(FOLLOW_id_in_qualified_table_name387);
                    database_name=id();

                    state._fsp--;

                    adaptor.addChild(root_0, database_name.getTree());
                    DOT32=(Token)match(input,DOT,FOLLOW_DOT_in_qualified_table_name389); 
                    DOT32_tree = (Object)adaptor.create(DOT32);
                    adaptor.addChild(root_0, DOT32_tree);


                    }
                    break;

            }

            pushFollow(FOLLOW_id_in_qualified_table_name395);
            table_name=id();

            state._fsp--;

            adaptor.addChild(root_0, table_name.getTree());
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:132:61: ( INDEXED BY index_name= id | NOT INDEXED )?
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
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:132:62: INDEXED BY index_name= id
                    {
                    INDEXED33=(Token)match(input,INDEXED,FOLLOW_INDEXED_in_qualified_table_name398); 
                    INDEXED33_tree = (Object)adaptor.create(INDEXED33);
                    adaptor.addChild(root_0, INDEXED33_tree);

                    BY34=(Token)match(input,BY,FOLLOW_BY_in_qualified_table_name400); 
                    BY34_tree = (Object)adaptor.create(BY34);
                    adaptor.addChild(root_0, BY34_tree);

                    pushFollow(FOLLOW_id_in_qualified_table_name404);
                    index_name=id();

                    state._fsp--;

                    adaptor.addChild(root_0, index_name.getTree());

                    }
                    break;
                case 2 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:132:89: NOT INDEXED
                    {
                    NOT35=(Token)match(input,NOT,FOLLOW_NOT_in_qualified_table_name408); 
                    NOT35_tree = (Object)adaptor.create(NOT35);
                    adaptor.addChild(root_0, NOT35_tree);

                    INDEXED36=(Token)match(input,INDEXED,FOLLOW_INDEXED_in_qualified_table_name410); 
                    INDEXED36_tree = (Object)adaptor.create(INDEXED36);
                    adaptor.addChild(root_0, INDEXED36_tree);


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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:134:1: expr : or_subexpr ( OR or_subexpr )* ;
    public final SqlParser.expr_return expr() throws RecognitionException {
        SqlParser.expr_return retval = new SqlParser.expr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token OR38=null;
        SqlParser.or_subexpr_return or_subexpr37 = null;

        SqlParser.or_subexpr_return or_subexpr39 = null;


        Object OR38_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:134:5: ( or_subexpr ( OR or_subexpr )* )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:134:7: or_subexpr ( OR or_subexpr )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_or_subexpr_in_expr419);
            or_subexpr37=or_subexpr();

            state._fsp--;

            adaptor.addChild(root_0, or_subexpr37.getTree());
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:134:18: ( OR or_subexpr )*
            loop7:
            do {
                int alt7=2;
                alt7 = dfa7.predict(input);
                switch (alt7) {
            	case 1 :
            	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:134:19: OR or_subexpr
            	    {
            	    OR38=(Token)match(input,OR,FOLLOW_OR_in_expr422); 
            	    OR38_tree = (Object)adaptor.create(OR38);
            	    root_0 = (Object)adaptor.becomeRoot(OR38_tree, root_0);

            	    pushFollow(FOLLOW_or_subexpr_in_expr425);
            	    or_subexpr39=or_subexpr();

            	    state._fsp--;

            	    adaptor.addChild(root_0, or_subexpr39.getTree());

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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:136:1: or_subexpr : and_subexpr ( AND and_subexpr )* ;
    public final SqlParser.or_subexpr_return or_subexpr() throws RecognitionException {
        SqlParser.or_subexpr_return retval = new SqlParser.or_subexpr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token AND41=null;
        SqlParser.and_subexpr_return and_subexpr40 = null;

        SqlParser.and_subexpr_return and_subexpr42 = null;


        Object AND41_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:136:11: ( and_subexpr ( AND and_subexpr )* )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:136:13: and_subexpr ( AND and_subexpr )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_and_subexpr_in_or_subexpr434);
            and_subexpr40=and_subexpr();

            state._fsp--;

            adaptor.addChild(root_0, and_subexpr40.getTree());
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:136:25: ( AND and_subexpr )*
            loop8:
            do {
                int alt8=2;
                alt8 = dfa8.predict(input);
                switch (alt8) {
            	case 1 :
            	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:136:26: AND and_subexpr
            	    {
            	    AND41=(Token)match(input,AND,FOLLOW_AND_in_or_subexpr437); 
            	    AND41_tree = (Object)adaptor.create(AND41);
            	    root_0 = (Object)adaptor.becomeRoot(AND41_tree, root_0);

            	    pushFollow(FOLLOW_and_subexpr_in_or_subexpr440);
            	    and_subexpr42=and_subexpr();

            	    state._fsp--;

            	    adaptor.addChild(root_0, and_subexpr42.getTree());

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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:138:1: and_subexpr : eq_subexpr ( cond_expr )? ;
    public final SqlParser.and_subexpr_return and_subexpr() throws RecognitionException {
        SqlParser.and_subexpr_return retval = new SqlParser.and_subexpr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        SqlParser.eq_subexpr_return eq_subexpr43 = null;

        SqlParser.cond_expr_return cond_expr44 = null;



        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:138:12: ( eq_subexpr ( cond_expr )? )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:138:14: eq_subexpr ( cond_expr )?
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_eq_subexpr_in_and_subexpr449);
            eq_subexpr43=eq_subexpr();

            state._fsp--;

            adaptor.addChild(root_0, eq_subexpr43.getTree());
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:138:34: ( cond_expr )?
            int alt9=2;
            alt9 = dfa9.predict(input);
            switch (alt9) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:138:34: cond_expr
                    {
                    pushFollow(FOLLOW_cond_expr_in_and_subexpr451);
                    cond_expr44=cond_expr();

                    state._fsp--;

                    root_0 = (Object)adaptor.becomeRoot(cond_expr44.getTree(), root_0);

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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:140:1: cond_expr : ( ( NOT )? match_op match_expr= eq_subexpr ( ESCAPE escape_expr= eq_subexpr )? -> ^( match_op $match_expr ( NOT )? ( ^( ESCAPE $escape_expr) )? ) | ( NOT )? IN LPAREN expr ( COMMA expr )* RPAREN -> ^( IN_VALUES ( NOT )? ^( IN ( expr )+ ) ) | ( NOT )? IN (database_name= id DOT )? table_name= id -> ^( IN_TABLE ( NOT )? ^( IN ^( $table_name ( $database_name)? ) ) ) | ( ISNULL -> IS_NULL | NOTNULL -> NOT_NULL | IS NULL -> IS_NULL | NOT NULL -> NOT_NULL | IS NOT NULL -> NOT_NULL ) | ( NOT )? BETWEEN e1= eq_subexpr AND e2= eq_subexpr -> ^( BETWEEN ( NOT )? ^( AND $e1 $e2) ) | ( ( EQUALS | EQUALS2 | NOT_EQUALS | NOT_EQUALS2 ) eq_subexpr )+ );
    public final SqlParser.cond_expr_return cond_expr() throws RecognitionException {
        SqlParser.cond_expr_return retval = new SqlParser.cond_expr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token NOT45=null;
        Token ESCAPE47=null;
        Token NOT48=null;
        Token IN49=null;
        Token LPAREN50=null;
        Token COMMA52=null;
        Token RPAREN54=null;
        Token NOT55=null;
        Token IN56=null;
        Token DOT57=null;
        Token ISNULL58=null;
        Token NOTNULL59=null;
        Token IS60=null;
        Token NULL61=null;
        Token NOT62=null;
        Token NULL63=null;
        Token IS64=null;
        Token NOT65=null;
        Token NULL66=null;
        Token NOT67=null;
        Token BETWEEN68=null;
        Token AND69=null;
        Token set70=null;
        SqlParser.eq_subexpr_return match_expr = null;

        SqlParser.eq_subexpr_return escape_expr = null;

        SqlParser.id_return database_name = null;

        SqlParser.id_return table_name = null;

        SqlParser.eq_subexpr_return e1 = null;

        SqlParser.eq_subexpr_return e2 = null;

        SqlParser.match_op_return match_op46 = null;

        SqlParser.expr_return expr51 = null;

        SqlParser.expr_return expr53 = null;

        SqlParser.eq_subexpr_return eq_subexpr71 = null;


        Object NOT45_tree=null;
        Object ESCAPE47_tree=null;
        Object NOT48_tree=null;
        Object IN49_tree=null;
        Object LPAREN50_tree=null;
        Object COMMA52_tree=null;
        Object RPAREN54_tree=null;
        Object NOT55_tree=null;
        Object IN56_tree=null;
        Object DOT57_tree=null;
        Object ISNULL58_tree=null;
        Object NOTNULL59_tree=null;
        Object IS60_tree=null;
        Object NULL61_tree=null;
        Object NOT62_tree=null;
        Object NULL63_tree=null;
        Object IS64_tree=null;
        Object NOT65_tree=null;
        Object NULL66_tree=null;
        Object NOT67_tree=null;
        Object BETWEEN68_tree=null;
        Object AND69_tree=null;
        Object set70_tree=null;
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
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:141:3: ( ( NOT )? match_op match_expr= eq_subexpr ( ESCAPE escape_expr= eq_subexpr )? -> ^( match_op $match_expr ( NOT )? ( ^( ESCAPE $escape_expr) )? ) | ( NOT )? IN LPAREN expr ( COMMA expr )* RPAREN -> ^( IN_VALUES ( NOT )? ^( IN ( expr )+ ) ) | ( NOT )? IN (database_name= id DOT )? table_name= id -> ^( IN_TABLE ( NOT )? ^( IN ^( $table_name ( $database_name)? ) ) ) | ( ISNULL -> IS_NULL | NOTNULL -> NOT_NULL | IS NULL -> IS_NULL | NOT NULL -> NOT_NULL | IS NOT NULL -> NOT_NULL ) | ( NOT )? BETWEEN e1= eq_subexpr AND e2= eq_subexpr -> ^( BETWEEN ( NOT )? ^( AND $e1 $e2) ) | ( ( EQUALS | EQUALS2 | NOT_EQUALS | NOT_EQUALS2 ) eq_subexpr )+ )
            int alt19=6;
            alt19 = dfa19.predict(input);
            switch (alt19) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:141:5: ( NOT )? match_op match_expr= eq_subexpr ( ESCAPE escape_expr= eq_subexpr )?
                    {
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:141:5: ( NOT )?
                    int alt10=2;
                    int LA10_0 = input.LA(1);

                    if ( (LA10_0==NOT) ) {
                        alt10=1;
                    }
                    switch (alt10) {
                        case 1 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:141:5: NOT
                            {
                            NOT45=(Token)match(input,NOT,FOLLOW_NOT_in_cond_expr463);  
                            stream_NOT.add(NOT45);


                            }
                            break;

                    }

                    pushFollow(FOLLOW_match_op_in_cond_expr466);
                    match_op46=match_op();

                    state._fsp--;

                    stream_match_op.add(match_op46.getTree());
                    pushFollow(FOLLOW_eq_subexpr_in_cond_expr470);
                    match_expr=eq_subexpr();

                    state._fsp--;

                    stream_eq_subexpr.add(match_expr.getTree());
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:141:41: ( ESCAPE escape_expr= eq_subexpr )?
                    int alt11=2;
                    alt11 = dfa11.predict(input);
                    switch (alt11) {
                        case 1 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:141:42: ESCAPE escape_expr= eq_subexpr
                            {
                            ESCAPE47=(Token)match(input,ESCAPE,FOLLOW_ESCAPE_in_cond_expr473);  
                            stream_ESCAPE.add(ESCAPE47);

                            pushFollow(FOLLOW_eq_subexpr_in_cond_expr477);
                            escape_expr=eq_subexpr();

                            state._fsp--;

                            stream_eq_subexpr.add(escape_expr.getTree());

                            }
                            break;

                    }



                    // AST REWRITE
                    // elements: ESCAPE, match_op, NOT, escape_expr, match_expr
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
                    // 141:74: -> ^( match_op $match_expr ( NOT )? ( ^( ESCAPE $escape_expr) )? )
                    {
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:141:77: ^( match_op $match_expr ( NOT )? ( ^( ESCAPE $escape_expr) )? )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(stream_match_op.nextNode(), root_1);

                        adaptor.addChild(root_1, stream_match_expr.nextTree());
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:141:100: ( NOT )?
                        if ( stream_NOT.hasNext() ) {
                            adaptor.addChild(root_1, stream_NOT.nextNode());

                        }
                        stream_NOT.reset();
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:141:105: ( ^( ESCAPE $escape_expr) )?
                        if ( stream_ESCAPE.hasNext()||stream_escape_expr.hasNext() ) {
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:141:105: ^( ESCAPE $escape_expr)
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
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:142:5: ( NOT )? IN LPAREN expr ( COMMA expr )* RPAREN
                    {
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:142:5: ( NOT )?
                    int alt12=2;
                    int LA12_0 = input.LA(1);

                    if ( (LA12_0==NOT) ) {
                        alt12=1;
                    }
                    switch (alt12) {
                        case 1 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:142:5: NOT
                            {
                            NOT48=(Token)match(input,NOT,FOLLOW_NOT_in_cond_expr505);  
                            stream_NOT.add(NOT48);


                            }
                            break;

                    }

                    IN49=(Token)match(input,IN,FOLLOW_IN_in_cond_expr508);  
                    stream_IN.add(IN49);

                    LPAREN50=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_cond_expr510);  
                    stream_LPAREN.add(LPAREN50);

                    pushFollow(FOLLOW_expr_in_cond_expr512);
                    expr51=expr();

                    state._fsp--;

                    stream_expr.add(expr51.getTree());
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:142:25: ( COMMA expr )*
                    loop13:
                    do {
                        int alt13=2;
                        int LA13_0 = input.LA(1);

                        if ( (LA13_0==COMMA) ) {
                            alt13=1;
                        }


                        switch (alt13) {
                    	case 1 :
                    	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:142:26: COMMA expr
                    	    {
                    	    COMMA52=(Token)match(input,COMMA,FOLLOW_COMMA_in_cond_expr515);  
                    	    stream_COMMA.add(COMMA52);

                    	    pushFollow(FOLLOW_expr_in_cond_expr517);
                    	    expr53=expr();

                    	    state._fsp--;

                    	    stream_expr.add(expr53.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop13;
                        }
                    } while (true);

                    RPAREN54=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_cond_expr521);  
                    stream_RPAREN.add(RPAREN54);



                    // AST REWRITE
                    // elements: NOT, IN, expr
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 142:46: -> ^( IN_VALUES ( NOT )? ^( IN ( expr )+ ) )
                    {
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:142:49: ^( IN_VALUES ( NOT )? ^( IN ( expr )+ ) )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(IN_VALUES, "IN_VALUES"), root_1);

                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:142:61: ( NOT )?
                        if ( stream_NOT.hasNext() ) {
                            adaptor.addChild(root_1, stream_NOT.nextNode());

                        }
                        stream_NOT.reset();
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:142:66: ^( IN ( expr )+ )
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
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:143:5: ( NOT )? IN (database_name= id DOT )? table_name= id
                    {
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:143:5: ( NOT )?
                    int alt14=2;
                    int LA14_0 = input.LA(1);

                    if ( (LA14_0==NOT) ) {
                        alt14=1;
                    }
                    switch (alt14) {
                        case 1 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:143:5: NOT
                            {
                            NOT55=(Token)match(input,NOT,FOLLOW_NOT_in_cond_expr543);  
                            stream_NOT.add(NOT55);


                            }
                            break;

                    }

                    IN56=(Token)match(input,IN,FOLLOW_IN_in_cond_expr546);  
                    stream_IN.add(IN56);

                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:143:13: (database_name= id DOT )?
                    int alt15=2;
                    alt15 = dfa15.predict(input);
                    switch (alt15) {
                        case 1 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:143:14: database_name= id DOT
                            {
                            pushFollow(FOLLOW_id_in_cond_expr551);
                            database_name=id();

                            state._fsp--;

                            stream_id.add(database_name.getTree());
                            DOT57=(Token)match(input,DOT,FOLLOW_DOT_in_cond_expr553);  
                            stream_DOT.add(DOT57);


                            }
                            break;

                    }

                    pushFollow(FOLLOW_id_in_cond_expr559);
                    table_name=id();

                    state._fsp--;

                    stream_id.add(table_name.getTree());


                    // AST REWRITE
                    // elements: NOT, database_name, IN, table_name
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
                    // 143:51: -> ^( IN_TABLE ( NOT )? ^( IN ^( $table_name ( $database_name)? ) ) )
                    {
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:143:54: ^( IN_TABLE ( NOT )? ^( IN ^( $table_name ( $database_name)? ) ) )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(IN_TABLE, "IN_TABLE"), root_1);

                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:143:65: ( NOT )?
                        if ( stream_NOT.hasNext() ) {
                            adaptor.addChild(root_1, stream_NOT.nextNode());

                        }
                        stream_NOT.reset();
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:143:70: ^( IN ^( $table_name ( $database_name)? ) )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot(stream_IN.nextNode(), root_2);

                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:143:75: ^( $table_name ( $database_name)? )
                        {
                        Object root_3 = (Object)adaptor.nil();
                        root_3 = (Object)adaptor.becomeRoot(stream_table_name.nextNode(), root_3);

                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:143:89: ( $database_name)?
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
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:146:5: ( ISNULL -> IS_NULL | NOTNULL -> NOT_NULL | IS NULL -> IS_NULL | NOT NULL -> NOT_NULL | IS NOT NULL -> NOT_NULL )
                    {
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:146:5: ( ISNULL -> IS_NULL | NOTNULL -> NOT_NULL | IS NULL -> IS_NULL | NOT NULL -> NOT_NULL | IS NOT NULL -> NOT_NULL )
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
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:146:6: ISNULL
                            {
                            ISNULL58=(Token)match(input,ISNULL,FOLLOW_ISNULL_in_cond_expr590);  
                            stream_ISNULL.add(ISNULL58);



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
                            // 146:13: -> IS_NULL
                            {
                                adaptor.addChild(root_0, (Object)adaptor.create(IS_NULL, "IS_NULL"));

                            }

                            retval.tree = root_0;
                            }
                            break;
                        case 2 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:146:26: NOTNULL
                            {
                            NOTNULL59=(Token)match(input,NOTNULL,FOLLOW_NOTNULL_in_cond_expr598);  
                            stream_NOTNULL.add(NOTNULL59);



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
                            // 146:34: -> NOT_NULL
                            {
                                adaptor.addChild(root_0, (Object)adaptor.create(NOT_NULL, "NOT_NULL"));

                            }

                            retval.tree = root_0;
                            }
                            break;
                        case 3 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:146:48: IS NULL
                            {
                            IS60=(Token)match(input,IS,FOLLOW_IS_in_cond_expr606);  
                            stream_IS.add(IS60);

                            NULL61=(Token)match(input,NULL,FOLLOW_NULL_in_cond_expr608);  
                            stream_NULL.add(NULL61);



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
                            // 146:56: -> IS_NULL
                            {
                                adaptor.addChild(root_0, (Object)adaptor.create(IS_NULL, "IS_NULL"));

                            }

                            retval.tree = root_0;
                            }
                            break;
                        case 4 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:146:69: NOT NULL
                            {
                            NOT62=(Token)match(input,NOT,FOLLOW_NOT_in_cond_expr616);  
                            stream_NOT.add(NOT62);

                            NULL63=(Token)match(input,NULL,FOLLOW_NULL_in_cond_expr618);  
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
                            // 146:78: -> NOT_NULL
                            {
                                adaptor.addChild(root_0, (Object)adaptor.create(NOT_NULL, "NOT_NULL"));

                            }

                            retval.tree = root_0;
                            }
                            break;
                        case 5 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:146:92: IS NOT NULL
                            {
                            IS64=(Token)match(input,IS,FOLLOW_IS_in_cond_expr626);  
                            stream_IS.add(IS64);

                            NOT65=(Token)match(input,NOT,FOLLOW_NOT_in_cond_expr628);  
                            stream_NOT.add(NOT65);

                            NULL66=(Token)match(input,NULL,FOLLOW_NULL_in_cond_expr630);  
                            stream_NULL.add(NULL66);



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
                            // 146:104: -> NOT_NULL
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
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:147:5: ( NOT )? BETWEEN e1= eq_subexpr AND e2= eq_subexpr
                    {
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:147:5: ( NOT )?
                    int alt17=2;
                    int LA17_0 = input.LA(1);

                    if ( (LA17_0==NOT) ) {
                        alt17=1;
                    }
                    switch (alt17) {
                        case 1 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:147:5: NOT
                            {
                            NOT67=(Token)match(input,NOT,FOLLOW_NOT_in_cond_expr641);  
                            stream_NOT.add(NOT67);


                            }
                            break;

                    }

                    BETWEEN68=(Token)match(input,BETWEEN,FOLLOW_BETWEEN_in_cond_expr644);  
                    stream_BETWEEN.add(BETWEEN68);

                    pushFollow(FOLLOW_eq_subexpr_in_cond_expr648);
                    e1=eq_subexpr();

                    state._fsp--;

                    stream_eq_subexpr.add(e1.getTree());
                    AND69=(Token)match(input,AND,FOLLOW_AND_in_cond_expr650);  
                    stream_AND.add(AND69);

                    pushFollow(FOLLOW_eq_subexpr_in_cond_expr654);
                    e2=eq_subexpr();

                    state._fsp--;

                    stream_eq_subexpr.add(e2.getTree());


                    // AST REWRITE
                    // elements: e2, AND, e1, NOT, BETWEEN
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
                    // 147:50: -> ^( BETWEEN ( NOT )? ^( AND $e1 $e2) )
                    {
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:147:53: ^( BETWEEN ( NOT )? ^( AND $e1 $e2) )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(stream_BETWEEN.nextNode(), root_1);

                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:147:63: ( NOT )?
                        if ( stream_NOT.hasNext() ) {
                            adaptor.addChild(root_1, stream_NOT.nextNode());

                        }
                        stream_NOT.reset();
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:147:68: ^( AND $e1 $e2)
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
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:148:5: ( ( EQUALS | EQUALS2 | NOT_EQUALS | NOT_EQUALS2 ) eq_subexpr )+
                    {
                    root_0 = (Object)adaptor.nil();

                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:148:5: ( ( EQUALS | EQUALS2 | NOT_EQUALS | NOT_EQUALS2 ) eq_subexpr )+
                    int cnt18=0;
                    loop18:
                    do {
                        int alt18=2;
                        alt18 = dfa18.predict(input);
                        switch (alt18) {
                    	case 1 :
                    	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:148:6: ( EQUALS | EQUALS2 | NOT_EQUALS | NOT_EQUALS2 ) eq_subexpr
                    	    {
                    	    set70=(Token)input.LT(1);
                    	    set70=(Token)input.LT(1);
                    	    if ( (input.LA(1)>=EQUALS && input.LA(1)<=NOT_EQUALS2) ) {
                    	        input.consume();
                    	        root_0 = (Object)adaptor.becomeRoot((Object)adaptor.create(set70), root_0);
                    	        state.errorRecovery=false;
                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        throw mse;
                    	    }

                    	    pushFollow(FOLLOW_eq_subexpr_in_cond_expr697);
                    	    eq_subexpr71=eq_subexpr();

                    	    state._fsp--;

                    	    adaptor.addChild(root_0, eq_subexpr71.getTree());

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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:151:1: match_op : ( LIKE | GLOB | REGEXP | MATCH );
    public final SqlParser.match_op_return match_op() throws RecognitionException {
        SqlParser.match_op_return retval = new SqlParser.match_op_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set72=null;

        Object set72_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:151:9: ( LIKE | GLOB | REGEXP | MATCH )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:
            {
            root_0 = (Object)adaptor.nil();

            set72=(Token)input.LT(1);
            if ( (input.LA(1)>=LIKE && input.LA(1)<=MATCH) ) {
                input.consume();
                adaptor.addChild(root_0, (Object)adaptor.create(set72));
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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:153:1: eq_subexpr : neq_subexpr ( ( LESS | LESS_OR_EQ | GREATER | GREATER_OR_EQ ) neq_subexpr )* ;
    public final SqlParser.eq_subexpr_return eq_subexpr() throws RecognitionException {
        SqlParser.eq_subexpr_return retval = new SqlParser.eq_subexpr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set74=null;
        SqlParser.neq_subexpr_return neq_subexpr73 = null;

        SqlParser.neq_subexpr_return neq_subexpr75 = null;


        Object set74_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:153:11: ( neq_subexpr ( ( LESS | LESS_OR_EQ | GREATER | GREATER_OR_EQ ) neq_subexpr )* )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:153:13: neq_subexpr ( ( LESS | LESS_OR_EQ | GREATER | GREATER_OR_EQ ) neq_subexpr )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_neq_subexpr_in_eq_subexpr730);
            neq_subexpr73=neq_subexpr();

            state._fsp--;

            adaptor.addChild(root_0, neq_subexpr73.getTree());
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:153:25: ( ( LESS | LESS_OR_EQ | GREATER | GREATER_OR_EQ ) neq_subexpr )*
            loop20:
            do {
                int alt20=2;
                alt20 = dfa20.predict(input);
                switch (alt20) {
            	case 1 :
            	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:153:26: ( LESS | LESS_OR_EQ | GREATER | GREATER_OR_EQ ) neq_subexpr
            	    {
            	    set74=(Token)input.LT(1);
            	    set74=(Token)input.LT(1);
            	    if ( (input.LA(1)>=LESS && input.LA(1)<=GREATER_OR_EQ) ) {
            	        input.consume();
            	        root_0 = (Object)adaptor.becomeRoot((Object)adaptor.create(set74), root_0);
            	        state.errorRecovery=false;
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }

            	    pushFollow(FOLLOW_neq_subexpr_in_eq_subexpr750);
            	    neq_subexpr75=neq_subexpr();

            	    state._fsp--;

            	    adaptor.addChild(root_0, neq_subexpr75.getTree());

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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:155:1: neq_subexpr : bit_subexpr ( ( SHIFT_LEFT | SHIFT_RIGHT | AMPERSAND | PIPE ) bit_subexpr )* ;
    public final SqlParser.neq_subexpr_return neq_subexpr() throws RecognitionException {
        SqlParser.neq_subexpr_return retval = new SqlParser.neq_subexpr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set77=null;
        SqlParser.bit_subexpr_return bit_subexpr76 = null;

        SqlParser.bit_subexpr_return bit_subexpr78 = null;


        Object set77_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:155:12: ( bit_subexpr ( ( SHIFT_LEFT | SHIFT_RIGHT | AMPERSAND | PIPE ) bit_subexpr )* )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:155:14: bit_subexpr ( ( SHIFT_LEFT | SHIFT_RIGHT | AMPERSAND | PIPE ) bit_subexpr )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_bit_subexpr_in_neq_subexpr759);
            bit_subexpr76=bit_subexpr();

            state._fsp--;

            adaptor.addChild(root_0, bit_subexpr76.getTree());
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:155:26: ( ( SHIFT_LEFT | SHIFT_RIGHT | AMPERSAND | PIPE ) bit_subexpr )*
            loop21:
            do {
                int alt21=2;
                alt21 = dfa21.predict(input);
                switch (alt21) {
            	case 1 :
            	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:155:27: ( SHIFT_LEFT | SHIFT_RIGHT | AMPERSAND | PIPE ) bit_subexpr
            	    {
            	    set77=(Token)input.LT(1);
            	    set77=(Token)input.LT(1);
            	    if ( (input.LA(1)>=SHIFT_LEFT && input.LA(1)<=PIPE) ) {
            	        input.consume();
            	        root_0 = (Object)adaptor.becomeRoot((Object)adaptor.create(set77), root_0);
            	        state.errorRecovery=false;
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }

            	    pushFollow(FOLLOW_bit_subexpr_in_neq_subexpr779);
            	    bit_subexpr78=bit_subexpr();

            	    state._fsp--;

            	    adaptor.addChild(root_0, bit_subexpr78.getTree());

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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:157:1: bit_subexpr : add_subexpr ( ( PLUS | MINUS ) add_subexpr )* ;
    public final SqlParser.bit_subexpr_return bit_subexpr() throws RecognitionException {
        SqlParser.bit_subexpr_return retval = new SqlParser.bit_subexpr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set80=null;
        SqlParser.add_subexpr_return add_subexpr79 = null;

        SqlParser.add_subexpr_return add_subexpr81 = null;


        Object set80_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:157:12: ( add_subexpr ( ( PLUS | MINUS ) add_subexpr )* )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:157:14: add_subexpr ( ( PLUS | MINUS ) add_subexpr )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_add_subexpr_in_bit_subexpr788);
            add_subexpr79=add_subexpr();

            state._fsp--;

            adaptor.addChild(root_0, add_subexpr79.getTree());
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:157:26: ( ( PLUS | MINUS ) add_subexpr )*
            loop22:
            do {
                int alt22=2;
                alt22 = dfa22.predict(input);
                switch (alt22) {
            	case 1 :
            	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:157:27: ( PLUS | MINUS ) add_subexpr
            	    {
            	    set80=(Token)input.LT(1);
            	    set80=(Token)input.LT(1);
            	    if ( (input.LA(1)>=PLUS && input.LA(1)<=MINUS) ) {
            	        input.consume();
            	        root_0 = (Object)adaptor.becomeRoot((Object)adaptor.create(set80), root_0);
            	        state.errorRecovery=false;
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }

            	    pushFollow(FOLLOW_add_subexpr_in_bit_subexpr800);
            	    add_subexpr81=add_subexpr();

            	    state._fsp--;

            	    adaptor.addChild(root_0, add_subexpr81.getTree());

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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:159:1: add_subexpr : mul_subexpr ( ( ASTERISK | SLASH | PERCENT ) mul_subexpr )* ;
    public final SqlParser.add_subexpr_return add_subexpr() throws RecognitionException {
        SqlParser.add_subexpr_return retval = new SqlParser.add_subexpr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set83=null;
        SqlParser.mul_subexpr_return mul_subexpr82 = null;

        SqlParser.mul_subexpr_return mul_subexpr84 = null;


        Object set83_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:159:12: ( mul_subexpr ( ( ASTERISK | SLASH | PERCENT ) mul_subexpr )* )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:159:14: mul_subexpr ( ( ASTERISK | SLASH | PERCENT ) mul_subexpr )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_mul_subexpr_in_add_subexpr809);
            mul_subexpr82=mul_subexpr();

            state._fsp--;

            adaptor.addChild(root_0, mul_subexpr82.getTree());
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:159:26: ( ( ASTERISK | SLASH | PERCENT ) mul_subexpr )*
            loop23:
            do {
                int alt23=2;
                alt23 = dfa23.predict(input);
                switch (alt23) {
            	case 1 :
            	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:159:27: ( ASTERISK | SLASH | PERCENT ) mul_subexpr
            	    {
            	    set83=(Token)input.LT(1);
            	    set83=(Token)input.LT(1);
            	    if ( (input.LA(1)>=ASTERISK && input.LA(1)<=PERCENT) ) {
            	        input.consume();
            	        root_0 = (Object)adaptor.becomeRoot((Object)adaptor.create(set83), root_0);
            	        state.errorRecovery=false;
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }

            	    pushFollow(FOLLOW_mul_subexpr_in_add_subexpr825);
            	    mul_subexpr84=mul_subexpr();

            	    state._fsp--;

            	    adaptor.addChild(root_0, mul_subexpr84.getTree());

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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:161:1: mul_subexpr : con_subexpr ( DOUBLE_PIPE con_subexpr )* ;
    public final SqlParser.mul_subexpr_return mul_subexpr() throws RecognitionException {
        SqlParser.mul_subexpr_return retval = new SqlParser.mul_subexpr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token DOUBLE_PIPE86=null;
        SqlParser.con_subexpr_return con_subexpr85 = null;

        SqlParser.con_subexpr_return con_subexpr87 = null;


        Object DOUBLE_PIPE86_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:161:12: ( con_subexpr ( DOUBLE_PIPE con_subexpr )* )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:161:14: con_subexpr ( DOUBLE_PIPE con_subexpr )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_con_subexpr_in_mul_subexpr834);
            con_subexpr85=con_subexpr();

            state._fsp--;

            adaptor.addChild(root_0, con_subexpr85.getTree());
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:161:26: ( DOUBLE_PIPE con_subexpr )*
            loop24:
            do {
                int alt24=2;
                alt24 = dfa24.predict(input);
                switch (alt24) {
            	case 1 :
            	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:161:27: DOUBLE_PIPE con_subexpr
            	    {
            	    DOUBLE_PIPE86=(Token)match(input,DOUBLE_PIPE,FOLLOW_DOUBLE_PIPE_in_mul_subexpr837); 
            	    DOUBLE_PIPE86_tree = (Object)adaptor.create(DOUBLE_PIPE86);
            	    root_0 = (Object)adaptor.becomeRoot(DOUBLE_PIPE86_tree, root_0);

            	    pushFollow(FOLLOW_con_subexpr_in_mul_subexpr840);
            	    con_subexpr87=con_subexpr();

            	    state._fsp--;

            	    adaptor.addChild(root_0, con_subexpr87.getTree());

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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:163:1: con_subexpr : ( unary_subexpr | unary_op unary_subexpr -> ^( unary_op unary_subexpr ) );
    public final SqlParser.con_subexpr_return con_subexpr() throws RecognitionException {
        SqlParser.con_subexpr_return retval = new SqlParser.con_subexpr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        SqlParser.unary_subexpr_return unary_subexpr88 = null;

        SqlParser.unary_op_return unary_op89 = null;

        SqlParser.unary_subexpr_return unary_subexpr90 = null;


        RewriteRuleSubtreeStream stream_unary_subexpr=new RewriteRuleSubtreeStream(adaptor,"rule unary_subexpr");
        RewriteRuleSubtreeStream stream_unary_op=new RewriteRuleSubtreeStream(adaptor,"rule unary_op");
        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:163:12: ( unary_subexpr | unary_op unary_subexpr -> ^( unary_op unary_subexpr ) )
            int alt25=2;
            alt25 = dfa25.predict(input);
            switch (alt25) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:163:14: unary_subexpr
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_unary_subexpr_in_con_subexpr849);
                    unary_subexpr88=unary_subexpr();

                    state._fsp--;

                    adaptor.addChild(root_0, unary_subexpr88.getTree());

                    }
                    break;
                case 2 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:163:30: unary_op unary_subexpr
                    {
                    pushFollow(FOLLOW_unary_op_in_con_subexpr853);
                    unary_op89=unary_op();

                    state._fsp--;

                    stream_unary_op.add(unary_op89.getTree());
                    pushFollow(FOLLOW_unary_subexpr_in_con_subexpr855);
                    unary_subexpr90=unary_subexpr();

                    state._fsp--;

                    stream_unary_subexpr.add(unary_subexpr90.getTree());


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
                    // 163:53: -> ^( unary_op unary_subexpr )
                    {
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:163:56: ^( unary_op unary_subexpr )
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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:165:1: unary_op : ( PLUS | MINUS | TILDA | NOT );
    public final SqlParser.unary_op_return unary_op() throws RecognitionException {
        SqlParser.unary_op_return retval = new SqlParser.unary_op_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set91=null;

        Object set91_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:165:9: ( PLUS | MINUS | TILDA | NOT )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:
            {
            root_0 = (Object)adaptor.nil();

            set91=(Token)input.LT(1);
            if ( input.LA(1)==NOT||(input.LA(1)>=PLUS && input.LA(1)<=MINUS)||input.LA(1)==TILDA ) {
                input.consume();
                adaptor.addChild(root_0, (Object)adaptor.create(set91));
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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:167:1: unary_subexpr : atom_expr ( COLLATE collation_name= ID )? ;
    public final SqlParser.unary_subexpr_return unary_subexpr() throws RecognitionException {
        SqlParser.unary_subexpr_return retval = new SqlParser.unary_subexpr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token collation_name=null;
        Token COLLATE93=null;
        SqlParser.atom_expr_return atom_expr92 = null;


        Object collation_name_tree=null;
        Object COLLATE93_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:167:14: ( atom_expr ( COLLATE collation_name= ID )? )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:167:16: atom_expr ( COLLATE collation_name= ID )?
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_atom_expr_in_unary_subexpr889);
            atom_expr92=atom_expr();

            state._fsp--;

            adaptor.addChild(root_0, atom_expr92.getTree());
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:167:26: ( COLLATE collation_name= ID )?
            int alt26=2;
            alt26 = dfa26.predict(input);
            switch (alt26) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:167:27: COLLATE collation_name= ID
                    {
                    COLLATE93=(Token)match(input,COLLATE,FOLLOW_COLLATE_in_unary_subexpr892); 
                    COLLATE93_tree = (Object)adaptor.create(COLLATE93);
                    root_0 = (Object)adaptor.becomeRoot(COLLATE93_tree, root_0);

                    collation_name=(Token)match(input,ID,FOLLOW_ID_in_unary_subexpr897); 
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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:169:1: atom_expr : ( literal_value | bind_parameter | ( (database_name= id DOT )? table_name= id DOT )? column_name= ID -> ^( COLUMN_EXPRESSION ^( $column_name ( ^( $table_name ( $database_name)? ) )? ) ) | name= ID LPAREN ( ( DISTINCT )? args+= expr ( COMMA args+= expr )* | ASTERISK )? RPAREN -> ^( FUNCTION_EXPRESSION $name ( DISTINCT )? ( $args)* ( ASTERISK )? ) | LPAREN expr RPAREN | CAST LPAREN expr AS type_name RPAREN | CASE (case_expr= expr )? ( when_expr )+ ( ELSE else_expr= expr )? END -> ^( CASE ( $case_expr)? ( when_expr )+ ( $else_expr)? ) | raise_function );
    public final SqlParser.atom_expr_return atom_expr() throws RecognitionException {
        SqlParser.atom_expr_return retval = new SqlParser.atom_expr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token column_name=null;
        Token name=null;
        Token DOT96=null;
        Token DOT97=null;
        Token LPAREN98=null;
        Token DISTINCT99=null;
        Token COMMA100=null;
        Token ASTERISK101=null;
        Token RPAREN102=null;
        Token LPAREN103=null;
        Token RPAREN105=null;
        Token CAST106=null;
        Token LPAREN107=null;
        Token AS109=null;
        Token RPAREN111=null;
        Token CASE112=null;
        Token ELSE114=null;
        Token END115=null;
        List list_args=null;
        SqlParser.id_return database_name = null;

        SqlParser.id_return table_name = null;

        SqlParser.expr_return case_expr = null;

        SqlParser.expr_return else_expr = null;

        SqlParser.literal_value_return literal_value94 = null;

        SqlParser.bind_parameter_return bind_parameter95 = null;

        SqlParser.expr_return expr104 = null;

        SqlParser.expr_return expr108 = null;

        SqlParser.type_name_return type_name110 = null;

        SqlParser.when_expr_return when_expr113 = null;

        SqlParser.raise_function_return raise_function116 = null;

        SqlParser.expr_return args = null;
         args = null;
        Object column_name_tree=null;
        Object name_tree=null;
        Object DOT96_tree=null;
        Object DOT97_tree=null;
        Object LPAREN98_tree=null;
        Object DISTINCT99_tree=null;
        Object COMMA100_tree=null;
        Object ASTERISK101_tree=null;
        Object RPAREN102_tree=null;
        Object LPAREN103_tree=null;
        Object RPAREN105_tree=null;
        Object CAST106_tree=null;
        Object LPAREN107_tree=null;
        Object AS109_tree=null;
        Object RPAREN111_tree=null;
        Object CASE112_tree=null;
        Object ELSE114_tree=null;
        Object END115_tree=null;
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
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:170:3: ( literal_value | bind_parameter | ( (database_name= id DOT )? table_name= id DOT )? column_name= ID -> ^( COLUMN_EXPRESSION ^( $column_name ( ^( $table_name ( $database_name)? ) )? ) ) | name= ID LPAREN ( ( DISTINCT )? args+= expr ( COMMA args+= expr )* | ASTERISK )? RPAREN -> ^( FUNCTION_EXPRESSION $name ( DISTINCT )? ( $args)* ( ASTERISK )? ) | LPAREN expr RPAREN | CAST LPAREN expr AS type_name RPAREN | CASE (case_expr= expr )? ( when_expr )+ ( ELSE else_expr= expr )? END -> ^( CASE ( $case_expr)? ( when_expr )+ ( $else_expr)? ) | raise_function )
            int alt35=8;
            alt35 = dfa35.predict(input);
            switch (alt35) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:170:5: literal_value
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_literal_value_in_atom_expr909);
                    literal_value94=literal_value();

                    state._fsp--;

                    adaptor.addChild(root_0, literal_value94.getTree());

                    }
                    break;
                case 2 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:171:5: bind_parameter
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_bind_parameter_in_atom_expr915);
                    bind_parameter95=bind_parameter();

                    state._fsp--;

                    adaptor.addChild(root_0, bind_parameter95.getTree());

                    }
                    break;
                case 3 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:172:5: ( (database_name= id DOT )? table_name= id DOT )? column_name= ID
                    {
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:172:5: ( (database_name= id DOT )? table_name= id DOT )?
                    int alt28=2;
                    alt28 = dfa28.predict(input);
                    switch (alt28) {
                        case 1 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:172:6: (database_name= id DOT )? table_name= id DOT
                            {
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:172:6: (database_name= id DOT )?
                            int alt27=2;
                            alt27 = dfa27.predict(input);
                            switch (alt27) {
                                case 1 :
                                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:172:7: database_name= id DOT
                                    {
                                    pushFollow(FOLLOW_id_in_atom_expr925);
                                    database_name=id();

                                    state._fsp--;

                                    stream_id.add(database_name.getTree());
                                    DOT96=(Token)match(input,DOT,FOLLOW_DOT_in_atom_expr927);  
                                    stream_DOT.add(DOT96);


                                    }
                                    break;

                            }

                            pushFollow(FOLLOW_id_in_atom_expr933);
                            table_name=id();

                            state._fsp--;

                            stream_id.add(table_name.getTree());
                            DOT97=(Token)match(input,DOT,FOLLOW_DOT_in_atom_expr935);  
                            stream_DOT.add(DOT97);


                            }
                            break;

                    }

                    column_name=(Token)match(input,ID,FOLLOW_ID_in_atom_expr941);  
                    stream_ID.add(column_name);



                    // AST REWRITE
                    // elements: column_name, table_name, database_name
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
                    // 172:65: -> ^( COLUMN_EXPRESSION ^( $column_name ( ^( $table_name ( $database_name)? ) )? ) )
                    {
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:172:68: ^( COLUMN_EXPRESSION ^( $column_name ( ^( $table_name ( $database_name)? ) )? ) )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(COLUMN_EXPRESSION, "COLUMN_EXPRESSION"), root_1);

                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:172:88: ^( $column_name ( ^( $table_name ( $database_name)? ) )? )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot(stream_column_name.nextNode(), root_2);

                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:172:103: ( ^( $table_name ( $database_name)? ) )?
                        if ( stream_table_name.hasNext()||stream_database_name.hasNext() ) {
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:172:103: ^( $table_name ( $database_name)? )
                            {
                            Object root_3 = (Object)adaptor.nil();
                            root_3 = (Object)adaptor.becomeRoot(stream_table_name.nextNode(), root_3);

                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:172:117: ( $database_name)?
                            if ( stream_database_name.hasNext() ) {
                                adaptor.addChild(root_3, stream_database_name.nextTree());

                            }
                            stream_database_name.reset();

                            adaptor.addChild(root_2, root_3);
                            }

                        }
                        stream_table_name.reset();
                        stream_database_name.reset();

                        adaptor.addChild(root_1, root_2);
                        }

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 4 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:173:5: name= ID LPAREN ( ( DISTINCT )? args+= expr ( COMMA args+= expr )* | ASTERISK )? RPAREN
                    {
                    name=(Token)match(input,ID,FOLLOW_ID_in_atom_expr970);  
                    stream_ID.add(name);

                    LPAREN98=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_atom_expr972);  
                    stream_LPAREN.add(LPAREN98);

                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:173:20: ( ( DISTINCT )? args+= expr ( COMMA args+= expr )* | ASTERISK )?
                    int alt31=3;
                    alt31 = dfa31.predict(input);
                    switch (alt31) {
                        case 1 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:173:21: ( DISTINCT )? args+= expr ( COMMA args+= expr )*
                            {
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:173:21: ( DISTINCT )?
                            int alt29=2;
                            alt29 = dfa29.predict(input);
                            switch (alt29) {
                                case 1 :
                                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:173:21: DISTINCT
                                    {
                                    DISTINCT99=(Token)match(input,DISTINCT,FOLLOW_DISTINCT_in_atom_expr975);  
                                    stream_DISTINCT.add(DISTINCT99);


                                    }
                                    break;

                            }

                            pushFollow(FOLLOW_expr_in_atom_expr980);
                            args=expr();

                            state._fsp--;

                            stream_expr.add(args.getTree());
                            if (list_args==null) list_args=new ArrayList();
                            list_args.add(args.getTree());

                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:173:42: ( COMMA args+= expr )*
                            loop30:
                            do {
                                int alt30=2;
                                int LA30_0 = input.LA(1);

                                if ( (LA30_0==COMMA) ) {
                                    alt30=1;
                                }


                                switch (alt30) {
                            	case 1 :
                            	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:173:43: COMMA args+= expr
                            	    {
                            	    COMMA100=(Token)match(input,COMMA,FOLLOW_COMMA_in_atom_expr983);  
                            	    stream_COMMA.add(COMMA100);

                            	    pushFollow(FOLLOW_expr_in_atom_expr987);
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
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:173:64: ASTERISK
                            {
                            ASTERISK101=(Token)match(input,ASTERISK,FOLLOW_ASTERISK_in_atom_expr993);  
                            stream_ASTERISK.add(ASTERISK101);


                            }
                            break;

                    }

                    RPAREN102=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_atom_expr997);  
                    stream_RPAREN.add(RPAREN102);



                    // AST REWRITE
                    // elements: ASTERISK, DISTINCT, name, args
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
                    // 173:82: -> ^( FUNCTION_EXPRESSION $name ( DISTINCT )? ( $args)* ( ASTERISK )? )
                    {
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:173:85: ^( FUNCTION_EXPRESSION $name ( DISTINCT )? ( $args)* ( ASTERISK )? )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(FUNCTION_EXPRESSION, "FUNCTION_EXPRESSION"), root_1);

                        adaptor.addChild(root_1, stream_name.nextNode());
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:173:113: ( DISTINCT )?
                        if ( stream_DISTINCT.hasNext() ) {
                            adaptor.addChild(root_1, stream_DISTINCT.nextNode());

                        }
                        stream_DISTINCT.reset();
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:173:123: ( $args)*
                        while ( stream_args.hasNext() ) {
                            adaptor.addChild(root_1, stream_args.nextTree());

                        }
                        stream_args.reset();
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:173:130: ( ASTERISK )?
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
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:174:5: LPAREN expr RPAREN
                    {
                    root_0 = (Object)adaptor.nil();

                    LPAREN103=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_atom_expr1022); 
                    pushFollow(FOLLOW_expr_in_atom_expr1025);
                    expr104=expr();

                    state._fsp--;

                    adaptor.addChild(root_0, expr104.getTree());
                    RPAREN105=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_atom_expr1027); 

                    }
                    break;
                case 6 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:175:5: CAST LPAREN expr AS type_name RPAREN
                    {
                    root_0 = (Object)adaptor.nil();

                    CAST106=(Token)match(input,CAST,FOLLOW_CAST_in_atom_expr1034); 
                    CAST106_tree = (Object)adaptor.create(CAST106);
                    root_0 = (Object)adaptor.becomeRoot(CAST106_tree, root_0);

                    LPAREN107=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_atom_expr1037); 
                    pushFollow(FOLLOW_expr_in_atom_expr1040);
                    expr108=expr();

                    state._fsp--;

                    adaptor.addChild(root_0, expr108.getTree());
                    AS109=(Token)match(input,AS,FOLLOW_AS_in_atom_expr1042); 
                    pushFollow(FOLLOW_type_name_in_atom_expr1045);
                    type_name110=type_name();

                    state._fsp--;

                    adaptor.addChild(root_0, type_name110.getTree());
                    RPAREN111=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_atom_expr1047); 

                    }
                    break;
                case 7 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:178:5: CASE (case_expr= expr )? ( when_expr )+ ( ELSE else_expr= expr )? END
                    {
                    CASE112=(Token)match(input,CASE,FOLLOW_CASE_in_atom_expr1056);  
                    stream_CASE.add(CASE112);

                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:178:10: (case_expr= expr )?
                    int alt32=2;
                    alt32 = dfa32.predict(input);
                    switch (alt32) {
                        case 1 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:178:11: case_expr= expr
                            {
                            pushFollow(FOLLOW_expr_in_atom_expr1061);
                            case_expr=expr();

                            state._fsp--;

                            stream_expr.add(case_expr.getTree());

                            }
                            break;

                    }

                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:178:28: ( when_expr )+
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
                    	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:178:28: when_expr
                    	    {
                    	    pushFollow(FOLLOW_when_expr_in_atom_expr1065);
                    	    when_expr113=when_expr();

                    	    state._fsp--;

                    	    stream_when_expr.add(when_expr113.getTree());

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

                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:178:39: ( ELSE else_expr= expr )?
                    int alt34=2;
                    int LA34_0 = input.LA(1);

                    if ( (LA34_0==ELSE) ) {
                        alt34=1;
                    }
                    switch (alt34) {
                        case 1 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:178:40: ELSE else_expr= expr
                            {
                            ELSE114=(Token)match(input,ELSE,FOLLOW_ELSE_in_atom_expr1069);  
                            stream_ELSE.add(ELSE114);

                            pushFollow(FOLLOW_expr_in_atom_expr1073);
                            else_expr=expr();

                            state._fsp--;

                            stream_expr.add(else_expr.getTree());

                            }
                            break;

                    }

                    END115=(Token)match(input,END,FOLLOW_END_in_atom_expr1077);  
                    stream_END.add(END115);



                    // AST REWRITE
                    // elements: case_expr, else_expr, CASE, when_expr
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
                    // 178:66: -> ^( CASE ( $case_expr)? ( when_expr )+ ( $else_expr)? )
                    {
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:178:69: ^( CASE ( $case_expr)? ( when_expr )+ ( $else_expr)? )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(stream_CASE.nextNode(), root_1);

                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:178:76: ( $case_expr)?
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
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:178:99: ( $else_expr)?
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
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:179:5: raise_function
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_raise_function_in_atom_expr1100);
                    raise_function116=raise_function();

                    state._fsp--;

                    adaptor.addChild(root_0, raise_function116.getTree());

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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:182:1: when_expr : WHEN e1= expr THEN e2= expr -> ^( WHEN $e1 $e2) ;
    public final SqlParser.when_expr_return when_expr() throws RecognitionException {
        SqlParser.when_expr_return retval = new SqlParser.when_expr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token WHEN117=null;
        Token THEN118=null;
        SqlParser.expr_return e1 = null;

        SqlParser.expr_return e2 = null;


        Object WHEN117_tree=null;
        Object THEN118_tree=null;
        RewriteRuleTokenStream stream_THEN=new RewriteRuleTokenStream(adaptor,"token THEN");
        RewriteRuleTokenStream stream_WHEN=new RewriteRuleTokenStream(adaptor,"token WHEN");
        RewriteRuleSubtreeStream stream_expr=new RewriteRuleSubtreeStream(adaptor,"rule expr");
        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:182:10: ( WHEN e1= expr THEN e2= expr -> ^( WHEN $e1 $e2) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:182:12: WHEN e1= expr THEN e2= expr
            {
            WHEN117=(Token)match(input,WHEN,FOLLOW_WHEN_in_when_expr1110);  
            stream_WHEN.add(WHEN117);

            pushFollow(FOLLOW_expr_in_when_expr1114);
            e1=expr();

            state._fsp--;

            stream_expr.add(e1.getTree());
            THEN118=(Token)match(input,THEN,FOLLOW_THEN_in_when_expr1116);  
            stream_THEN.add(THEN118);

            pushFollow(FOLLOW_expr_in_when_expr1120);
            e2=expr();

            state._fsp--;

            stream_expr.add(e2.getTree());


            // AST REWRITE
            // elements: WHEN, e2, e1
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
            // 182:38: -> ^( WHEN $e1 $e2)
            {
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:182:41: ^( WHEN $e1 $e2)
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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:184:1: literal_value : ( INTEGER -> ^( INTEGER_LITERAL INTEGER ) | FLOAT -> ^( FLOAT_LITERAL FLOAT ) | STRING -> ^( STRING_LITERAL STRING ) | BLOB -> ^( BLOB_LITERAL BLOB ) | NULL | CURRENT_TIME -> ^( FUNCTION_LITERAL CURRENT_TIME ) | CURRENT_DATE -> ^( FUNCTION_LITERAL CURRENT_DATE ) | CURRENT_TIMESTAMP -> ^( FUNCTION_LITERAL CURRENT_TIMESTAMP ) );
    public final SqlParser.literal_value_return literal_value() throws RecognitionException {
        SqlParser.literal_value_return retval = new SqlParser.literal_value_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token INTEGER119=null;
        Token FLOAT120=null;
        Token STRING121=null;
        Token BLOB122=null;
        Token NULL123=null;
        Token CURRENT_TIME124=null;
        Token CURRENT_DATE125=null;
        Token CURRENT_TIMESTAMP126=null;

        Object INTEGER119_tree=null;
        Object FLOAT120_tree=null;
        Object STRING121_tree=null;
        Object BLOB122_tree=null;
        Object NULL123_tree=null;
        Object CURRENT_TIME124_tree=null;
        Object CURRENT_DATE125_tree=null;
        Object CURRENT_TIMESTAMP126_tree=null;
        RewriteRuleTokenStream stream_INTEGER=new RewriteRuleTokenStream(adaptor,"token INTEGER");
        RewriteRuleTokenStream stream_BLOB=new RewriteRuleTokenStream(adaptor,"token BLOB");
        RewriteRuleTokenStream stream_FLOAT=new RewriteRuleTokenStream(adaptor,"token FLOAT");
        RewriteRuleTokenStream stream_CURRENT_TIMESTAMP=new RewriteRuleTokenStream(adaptor,"token CURRENT_TIMESTAMP");
        RewriteRuleTokenStream stream_CURRENT_DATE=new RewriteRuleTokenStream(adaptor,"token CURRENT_DATE");
        RewriteRuleTokenStream stream_CURRENT_TIME=new RewriteRuleTokenStream(adaptor,"token CURRENT_TIME");
        RewriteRuleTokenStream stream_STRING=new RewriteRuleTokenStream(adaptor,"token STRING");

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:185:3: ( INTEGER -> ^( INTEGER_LITERAL INTEGER ) | FLOAT -> ^( FLOAT_LITERAL FLOAT ) | STRING -> ^( STRING_LITERAL STRING ) | BLOB -> ^( BLOB_LITERAL BLOB ) | NULL | CURRENT_TIME -> ^( FUNCTION_LITERAL CURRENT_TIME ) | CURRENT_DATE -> ^( FUNCTION_LITERAL CURRENT_DATE ) | CURRENT_TIMESTAMP -> ^( FUNCTION_LITERAL CURRENT_TIMESTAMP ) )
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
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:185:5: INTEGER
                    {
                    INTEGER119=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_literal_value1142);  
                    stream_INTEGER.add(INTEGER119);



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
                    // 185:13: -> ^( INTEGER_LITERAL INTEGER )
                    {
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:185:16: ^( INTEGER_LITERAL INTEGER )
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
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:186:5: FLOAT
                    {
                    FLOAT120=(Token)match(input,FLOAT,FOLLOW_FLOAT_in_literal_value1156);  
                    stream_FLOAT.add(FLOAT120);



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
                    // 186:11: -> ^( FLOAT_LITERAL FLOAT )
                    {
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:186:14: ^( FLOAT_LITERAL FLOAT )
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
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:187:5: STRING
                    {
                    STRING121=(Token)match(input,STRING,FOLLOW_STRING_in_literal_value1170);  
                    stream_STRING.add(STRING121);



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
                    // 187:12: -> ^( STRING_LITERAL STRING )
                    {
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:187:15: ^( STRING_LITERAL STRING )
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
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:188:5: BLOB
                    {
                    BLOB122=(Token)match(input,BLOB,FOLLOW_BLOB_in_literal_value1184);  
                    stream_BLOB.add(BLOB122);



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
                    // 188:10: -> ^( BLOB_LITERAL BLOB )
                    {
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:188:13: ^( BLOB_LITERAL BLOB )
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
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:189:5: NULL
                    {
                    root_0 = (Object)adaptor.nil();

                    NULL123=(Token)match(input,NULL,FOLLOW_NULL_in_literal_value1198); 
                    NULL123_tree = (Object)adaptor.create(NULL123);
                    adaptor.addChild(root_0, NULL123_tree);


                    }
                    break;
                case 6 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:190:5: CURRENT_TIME
                    {
                    CURRENT_TIME124=(Token)match(input,CURRENT_TIME,FOLLOW_CURRENT_TIME_in_literal_value1204);  
                    stream_CURRENT_TIME.add(CURRENT_TIME124);



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
                    // 190:18: -> ^( FUNCTION_LITERAL CURRENT_TIME )
                    {
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:190:21: ^( FUNCTION_LITERAL CURRENT_TIME )
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
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:191:5: CURRENT_DATE
                    {
                    CURRENT_DATE125=(Token)match(input,CURRENT_DATE,FOLLOW_CURRENT_DATE_in_literal_value1218);  
                    stream_CURRENT_DATE.add(CURRENT_DATE125);



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
                    // 191:18: -> ^( FUNCTION_LITERAL CURRENT_DATE )
                    {
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:191:21: ^( FUNCTION_LITERAL CURRENT_DATE )
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
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:192:5: CURRENT_TIMESTAMP
                    {
                    CURRENT_TIMESTAMP126=(Token)match(input,CURRENT_TIMESTAMP,FOLLOW_CURRENT_TIMESTAMP_in_literal_value1232);  
                    stream_CURRENT_TIMESTAMP.add(CURRENT_TIMESTAMP126);



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
                    // 192:23: -> ^( FUNCTION_LITERAL CURRENT_TIMESTAMP )
                    {
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:192:26: ^( FUNCTION_LITERAL CURRENT_TIMESTAMP )
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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:195:1: bind_parameter : ( QUESTION -> BIND | QUESTION position= INTEGER -> ^( BIND $position) | COLON name= id -> ^( BIND_NAME $name) | AT name= id -> ^( BIND_NAME $name) );
    public final SqlParser.bind_parameter_return bind_parameter() throws RecognitionException {
        SqlParser.bind_parameter_return retval = new SqlParser.bind_parameter_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token position=null;
        Token QUESTION127=null;
        Token QUESTION128=null;
        Token COLON129=null;
        Token AT130=null;
        SqlParser.id_return name = null;


        Object position_tree=null;
        Object QUESTION127_tree=null;
        Object QUESTION128_tree=null;
        Object COLON129_tree=null;
        Object AT130_tree=null;
        RewriteRuleTokenStream stream_AT=new RewriteRuleTokenStream(adaptor,"token AT");
        RewriteRuleTokenStream stream_COLON=new RewriteRuleTokenStream(adaptor,"token COLON");
        RewriteRuleTokenStream stream_INTEGER=new RewriteRuleTokenStream(adaptor,"token INTEGER");
        RewriteRuleTokenStream stream_QUESTION=new RewriteRuleTokenStream(adaptor,"token QUESTION");
        RewriteRuleSubtreeStream stream_id=new RewriteRuleSubtreeStream(adaptor,"rule id");
        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:196:3: ( QUESTION -> BIND | QUESTION position= INTEGER -> ^( BIND $position) | COLON name= id -> ^( BIND_NAME $name) | AT name= id -> ^( BIND_NAME $name) )
            int alt37=4;
            alt37 = dfa37.predict(input);
            switch (alt37) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:196:5: QUESTION
                    {
                    QUESTION127=(Token)match(input,QUESTION,FOLLOW_QUESTION_in_bind_parameter1253);  
                    stream_QUESTION.add(QUESTION127);



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
                    // 196:14: -> BIND
                    {
                        adaptor.addChild(root_0, (Object)adaptor.create(BIND, "BIND"));

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:197:5: QUESTION position= INTEGER
                    {
                    QUESTION128=(Token)match(input,QUESTION,FOLLOW_QUESTION_in_bind_parameter1263);  
                    stream_QUESTION.add(QUESTION128);

                    position=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_bind_parameter1267);  
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
                    // 197:31: -> ^( BIND $position)
                    {
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:197:34: ^( BIND $position)
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
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:198:5: COLON name= id
                    {
                    COLON129=(Token)match(input,COLON,FOLLOW_COLON_in_bind_parameter1282);  
                    stream_COLON.add(COLON129);

                    pushFollow(FOLLOW_id_in_bind_parameter1286);
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
                    // 198:19: -> ^( BIND_NAME $name)
                    {
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:198:22: ^( BIND_NAME $name)
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
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:199:5: AT name= id
                    {
                    AT130=(Token)match(input,AT,FOLLOW_AT_in_bind_parameter1301);  
                    stream_AT.add(AT130);

                    pushFollow(FOLLOW_id_in_bind_parameter1305);
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
                    // 199:16: -> ^( BIND_NAME $name)
                    {
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:199:19: ^( BIND_NAME $name)
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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:204:1: raise_function : RAISE LPAREN ( IGNORE | ( ROLLBACK | ABORT | FAIL ) COMMA error_message= STRING ) RPAREN ;
    public final SqlParser.raise_function_return raise_function() throws RecognitionException {
        SqlParser.raise_function_return retval = new SqlParser.raise_function_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token error_message=null;
        Token RAISE131=null;
        Token LPAREN132=null;
        Token IGNORE133=null;
        Token set134=null;
        Token COMMA135=null;
        Token RPAREN136=null;

        Object error_message_tree=null;
        Object RAISE131_tree=null;
        Object LPAREN132_tree=null;
        Object IGNORE133_tree=null;
        Object set134_tree=null;
        Object COMMA135_tree=null;
        Object RPAREN136_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:204:15: ( RAISE LPAREN ( IGNORE | ( ROLLBACK | ABORT | FAIL ) COMMA error_message= STRING ) RPAREN )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:204:17: RAISE LPAREN ( IGNORE | ( ROLLBACK | ABORT | FAIL ) COMMA error_message= STRING ) RPAREN
            {
            root_0 = (Object)adaptor.nil();

            RAISE131=(Token)match(input,RAISE,FOLLOW_RAISE_in_raise_function1326); 
            RAISE131_tree = (Object)adaptor.create(RAISE131);
            root_0 = (Object)adaptor.becomeRoot(RAISE131_tree, root_0);

            LPAREN132=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_raise_function1329); 
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:204:32: ( IGNORE | ( ROLLBACK | ABORT | FAIL ) COMMA error_message= STRING )
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
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:204:33: IGNORE
                    {
                    IGNORE133=(Token)match(input,IGNORE,FOLLOW_IGNORE_in_raise_function1333); 
                    IGNORE133_tree = (Object)adaptor.create(IGNORE133);
                    adaptor.addChild(root_0, IGNORE133_tree);


                    }
                    break;
                case 2 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:204:42: ( ROLLBACK | ABORT | FAIL ) COMMA error_message= STRING
                    {
                    set134=(Token)input.LT(1);
                    if ( (input.LA(1)>=ROLLBACK && input.LA(1)<=FAIL) ) {
                        input.consume();
                        adaptor.addChild(root_0, (Object)adaptor.create(set134));
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    COMMA135=(Token)match(input,COMMA,FOLLOW_COMMA_in_raise_function1349); 
                    error_message=(Token)match(input,STRING,FOLLOW_STRING_in_raise_function1354); 
                    error_message_tree = (Object)adaptor.create(error_message);
                    adaptor.addChild(root_0, error_message_tree);


                    }
                    break;

            }

            RPAREN136=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_raise_function1357); 

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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:206:1: type_name : (names+= ID )+ ( LPAREN size1= ( signed_number | MAX ) ( COMMA size2= signed_number )? RPAREN )? -> ^( TYPE ^( TYPE_PARAMS ( $size1)? ( $size2)? ) ( $names)+ ) ;
    public final SqlParser.type_name_return type_name() throws RecognitionException {
        SqlParser.type_name_return retval = new SqlParser.type_name_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token size1=null;
        Token LPAREN137=null;
        Token MAX139=null;
        Token COMMA140=null;
        Token RPAREN141=null;
        Token names=null;
        List list_names=null;
        SqlParser.signed_number_return size2 = null;

        SqlParser.signed_number_return signed_number138 = null;


        Object size1_tree=null;
        Object LPAREN137_tree=null;
        Object MAX139_tree=null;
        Object COMMA140_tree=null;
        Object RPAREN141_tree=null;
        Object names_tree=null;
        RewriteRuleTokenStream stream_MAX=new RewriteRuleTokenStream(adaptor,"token MAX");
        RewriteRuleTokenStream stream_RPAREN=new RewriteRuleTokenStream(adaptor,"token RPAREN");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_COMMA=new RewriteRuleTokenStream(adaptor,"token COMMA");
        RewriteRuleTokenStream stream_LPAREN=new RewriteRuleTokenStream(adaptor,"token LPAREN");
        RewriteRuleSubtreeStream stream_signed_number=new RewriteRuleSubtreeStream(adaptor,"rule signed_number");
        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:206:10: ( (names+= ID )+ ( LPAREN size1= ( signed_number | MAX ) ( COMMA size2= signed_number )? RPAREN )? -> ^( TYPE ^( TYPE_PARAMS ( $size1)? ( $size2)? ) ( $names)+ ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:206:12: (names+= ID )+ ( LPAREN size1= ( signed_number | MAX ) ( COMMA size2= signed_number )? RPAREN )?
            {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:206:17: (names+= ID )+
            int cnt39=0;
            loop39:
            do {
                int alt39=2;
                alt39 = dfa39.predict(input);
                switch (alt39) {
            	case 1 :
            	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:206:17: names+= ID
            	    {
            	    names=(Token)match(input,ID,FOLLOW_ID_in_type_name1367);  
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

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:206:23: ( LPAREN size1= ( signed_number | MAX ) ( COMMA size2= signed_number )? RPAREN )?
            int alt42=2;
            alt42 = dfa42.predict(input);
            switch (alt42) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:206:24: LPAREN size1= ( signed_number | MAX ) ( COMMA size2= signed_number )? RPAREN
                    {
                    LPAREN137=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_type_name1371);  
                    stream_LPAREN.add(LPAREN137);

                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:206:38: ( signed_number | MAX )
                    int alt40=2;
                    int LA40_0 = input.LA(1);

                    if ( ((LA40_0>=PLUS && LA40_0<=MINUS)||(LA40_0>=INTEGER && LA40_0<=FLOAT)) ) {
                        alt40=1;
                    }
                    else if ( (LA40_0==MAX) ) {
                        alt40=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 40, 0, input);

                        throw nvae;
                    }
                    switch (alt40) {
                        case 1 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:206:39: signed_number
                            {
                            pushFollow(FOLLOW_signed_number_in_type_name1377);
                            signed_number138=signed_number();

                            state._fsp--;

                            stream_signed_number.add(signed_number138.getTree());

                            }
                            break;
                        case 2 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:206:55: MAX
                            {
                            MAX139=(Token)match(input,MAX,FOLLOW_MAX_in_type_name1381);  
                            stream_MAX.add(MAX139);


                            }
                            break;

                    }

                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:206:61: ( COMMA size2= signed_number )?
                    int alt41=2;
                    int LA41_0 = input.LA(1);

                    if ( (LA41_0==COMMA) ) {
                        alt41=1;
                    }
                    switch (alt41) {
                        case 1 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:206:62: COMMA size2= signed_number
                            {
                            COMMA140=(Token)match(input,COMMA,FOLLOW_COMMA_in_type_name1386);  
                            stream_COMMA.add(COMMA140);

                            pushFollow(FOLLOW_signed_number_in_type_name1390);
                            size2=signed_number();

                            state._fsp--;

                            stream_signed_number.add(size2.getTree());

                            }
                            break;

                    }

                    RPAREN141=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_type_name1394);  
                    stream_RPAREN.add(RPAREN141);


                    }
                    break;

            }

            builder.setDomainOfLastAttribute(String.valueOf(list_names.get(0)).split("'")[1]);


            // AST REWRITE
            // elements: size1, names, size2
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
            // 207:1: -> ^( TYPE ^( TYPE_PARAMS ( $size1)? ( $size2)? ) ( $names)+ )
            {
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:207:4: ^( TYPE ^( TYPE_PARAMS ( $size1)? ( $size2)? ) ( $names)+ )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(TYPE, "TYPE"), root_1);

                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:207:11: ^( TYPE_PARAMS ( $size1)? ( $size2)? )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(TYPE_PARAMS, "TYPE_PARAMS"), root_2);

                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:207:25: ( $size1)?
                if ( stream_size1.hasNext() ) {
                    adaptor.addChild(root_2, stream_size1.nextNode());

                }
                stream_size1.reset();
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:207:33: ( $size2)?
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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:209:1: signed_number : ( PLUS | MINUS )? ( INTEGER | FLOAT ) ;
    public final SqlParser.signed_number_return signed_number() throws RecognitionException {
        SqlParser.signed_number_return retval = new SqlParser.signed_number_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set142=null;
        Token set143=null;

        Object set142_tree=null;
        Object set143_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:209:14: ( ( PLUS | MINUS )? ( INTEGER | FLOAT ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:209:16: ( PLUS | MINUS )? ( INTEGER | FLOAT )
            {
            root_0 = (Object)adaptor.nil();

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:209:16: ( PLUS | MINUS )?
            int alt43=2;
            int LA43_0 = input.LA(1);

            if ( ((LA43_0>=PLUS && LA43_0<=MINUS)) ) {
                alt43=1;
            }
            switch (alt43) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:
                    {
                    set142=(Token)input.LT(1);
                    if ( (input.LA(1)>=PLUS && input.LA(1)<=MINUS) ) {
                        input.consume();
                        adaptor.addChild(root_0, (Object)adaptor.create(set142));
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }


                    }
                    break;

            }

            set143=(Token)input.LT(1);
            if ( (input.LA(1)>=INTEGER && input.LA(1)<=FLOAT) ) {
                input.consume();
                adaptor.addChild(root_0, (Object)adaptor.create(set143));
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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:212:1: pragma_stmt : PRAGMA (database_name= id DOT )? pragma_name= id ( EQUALS pragma_value | LPAREN pragma_value RPAREN )? -> ^( PRAGMA ^( $pragma_name ( $database_name)? ) ( pragma_value )? ) ;
    public final SqlParser.pragma_stmt_return pragma_stmt() throws RecognitionException {
        SqlParser.pragma_stmt_return retval = new SqlParser.pragma_stmt_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token PRAGMA144=null;
        Token DOT145=null;
        Token EQUALS146=null;
        Token LPAREN148=null;
        Token RPAREN150=null;
        SqlParser.id_return database_name = null;

        SqlParser.id_return pragma_name = null;

        SqlParser.pragma_value_return pragma_value147 = null;

        SqlParser.pragma_value_return pragma_value149 = null;


        Object PRAGMA144_tree=null;
        Object DOT145_tree=null;
        Object EQUALS146_tree=null;
        Object LPAREN148_tree=null;
        Object RPAREN150_tree=null;
        RewriteRuleTokenStream stream_RPAREN=new RewriteRuleTokenStream(adaptor,"token RPAREN");
        RewriteRuleTokenStream stream_EQUALS=new RewriteRuleTokenStream(adaptor,"token EQUALS");
        RewriteRuleTokenStream stream_DOT=new RewriteRuleTokenStream(adaptor,"token DOT");
        RewriteRuleTokenStream stream_LPAREN=new RewriteRuleTokenStream(adaptor,"token LPAREN");
        RewriteRuleTokenStream stream_PRAGMA=new RewriteRuleTokenStream(adaptor,"token PRAGMA");
        RewriteRuleSubtreeStream stream_id=new RewriteRuleSubtreeStream(adaptor,"rule id");
        RewriteRuleSubtreeStream stream_pragma_value=new RewriteRuleSubtreeStream(adaptor,"rule pragma_value");
        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:212:12: ( PRAGMA (database_name= id DOT )? pragma_name= id ( EQUALS pragma_value | LPAREN pragma_value RPAREN )? -> ^( PRAGMA ^( $pragma_name ( $database_name)? ) ( pragma_value )? ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:212:14: PRAGMA (database_name= id DOT )? pragma_name= id ( EQUALS pragma_value | LPAREN pragma_value RPAREN )?
            {
            PRAGMA144=(Token)match(input,PRAGMA,FOLLOW_PRAGMA_in_pragma_stmt1450);  
            stream_PRAGMA.add(PRAGMA144);

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:212:21: (database_name= id DOT )?
            int alt44=2;
            alt44 = dfa44.predict(input);
            switch (alt44) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:212:22: database_name= id DOT
                    {
                    pushFollow(FOLLOW_id_in_pragma_stmt1455);
                    database_name=id();

                    state._fsp--;

                    stream_id.add(database_name.getTree());
                    DOT145=(Token)match(input,DOT,FOLLOW_DOT_in_pragma_stmt1457);  
                    stream_DOT.add(DOT145);


                    }
                    break;

            }

            pushFollow(FOLLOW_id_in_pragma_stmt1463);
            pragma_name=id();

            state._fsp--;

            stream_id.add(pragma_name.getTree());
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:212:60: ( EQUALS pragma_value | LPAREN pragma_value RPAREN )?
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
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:212:61: EQUALS pragma_value
                    {
                    EQUALS146=(Token)match(input,EQUALS,FOLLOW_EQUALS_in_pragma_stmt1466);  
                    stream_EQUALS.add(EQUALS146);

                    pushFollow(FOLLOW_pragma_value_in_pragma_stmt1468);
                    pragma_value147=pragma_value();

                    state._fsp--;

                    stream_pragma_value.add(pragma_value147.getTree());

                    }
                    break;
                case 2 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:212:83: LPAREN pragma_value RPAREN
                    {
                    LPAREN148=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_pragma_stmt1472);  
                    stream_LPAREN.add(LPAREN148);

                    pushFollow(FOLLOW_pragma_value_in_pragma_stmt1474);
                    pragma_value149=pragma_value();

                    state._fsp--;

                    stream_pragma_value.add(pragma_value149.getTree());
                    RPAREN150=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_pragma_stmt1476);  
                    stream_RPAREN.add(RPAREN150);


                    }
                    break;

            }



            // AST REWRITE
            // elements: pragma_name, pragma_value, database_name, PRAGMA
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
            // 213:1: -> ^( PRAGMA ^( $pragma_name ( $database_name)? ) ( pragma_value )? )
            {
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:213:4: ^( PRAGMA ^( $pragma_name ( $database_name)? ) ( pragma_value )? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(stream_PRAGMA.nextNode(), root_1);

                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:213:13: ^( $pragma_name ( $database_name)? )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot(stream_pragma_name.nextNode(), root_2);

                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:213:28: ( $database_name)?
                if ( stream_database_name.hasNext() ) {
                    adaptor.addChild(root_2, stream_database_name.nextTree());

                }
                stream_database_name.reset();

                adaptor.addChild(root_1, root_2);
                }
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:213:45: ( pragma_value )?
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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:215:1: pragma_value : ( signed_number -> ^( FLOAT_LITERAL signed_number ) | id -> ^( ID_LITERAL id ) | STRING -> ^( STRING_LITERAL STRING ) );
    public final SqlParser.pragma_value_return pragma_value() throws RecognitionException {
        SqlParser.pragma_value_return retval = new SqlParser.pragma_value_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token STRING153=null;
        SqlParser.signed_number_return signed_number151 = null;

        SqlParser.id_return id152 = null;


        Object STRING153_tree=null;
        RewriteRuleTokenStream stream_STRING=new RewriteRuleTokenStream(adaptor,"token STRING");
        RewriteRuleSubtreeStream stream_id=new RewriteRuleSubtreeStream(adaptor,"rule id");
        RewriteRuleSubtreeStream stream_signed_number=new RewriteRuleSubtreeStream(adaptor,"rule signed_number");
        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:216:2: ( signed_number -> ^( FLOAT_LITERAL signed_number ) | id -> ^( ID_LITERAL id ) | STRING -> ^( STRING_LITERAL STRING ) )
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
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:216:4: signed_number
                    {
                    pushFollow(FOLLOW_signed_number_in_pragma_value1505);
                    signed_number151=signed_number();

                    state._fsp--;

                    stream_signed_number.add(signed_number151.getTree());


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
                    // 216:18: -> ^( FLOAT_LITERAL signed_number )
                    {
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:216:21: ^( FLOAT_LITERAL signed_number )
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
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:217:4: id
                    {
                    pushFollow(FOLLOW_id_in_pragma_value1518);
                    id152=id();

                    state._fsp--;

                    stream_id.add(id152.getTree());


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
                    // 217:7: -> ^( ID_LITERAL id )
                    {
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:217:10: ^( ID_LITERAL id )
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
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:218:4: STRING
                    {
                    STRING153=(Token)match(input,STRING,FOLLOW_STRING_in_pragma_value1531);  
                    stream_STRING.add(STRING153);



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
                    // 218:11: -> ^( STRING_LITERAL STRING )
                    {
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:218:14: ^( STRING_LITERAL STRING )
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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:222:1: attach_stmt : ATTACH ( DATABASE )? filename= ( STRING | id ) AS database_name= id ;
    public final SqlParser.attach_stmt_return attach_stmt() throws RecognitionException {
        SqlParser.attach_stmt_return retval = new SqlParser.attach_stmt_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token filename=null;
        Token ATTACH154=null;
        Token DATABASE155=null;
        Token STRING156=null;
        Token AS158=null;
        SqlParser.id_return database_name = null;

        SqlParser.id_return id157 = null;


        Object filename_tree=null;
        Object ATTACH154_tree=null;
        Object DATABASE155_tree=null;
        Object STRING156_tree=null;
        Object AS158_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:222:12: ( ATTACH ( DATABASE )? filename= ( STRING | id ) AS database_name= id )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:222:14: ATTACH ( DATABASE )? filename= ( STRING | id ) AS database_name= id
            {
            root_0 = (Object)adaptor.nil();

            ATTACH154=(Token)match(input,ATTACH,FOLLOW_ATTACH_in_attach_stmt1549); 
            ATTACH154_tree = (Object)adaptor.create(ATTACH154);
            adaptor.addChild(root_0, ATTACH154_tree);

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:222:21: ( DATABASE )?
            int alt47=2;
            alt47 = dfa47.predict(input);
            switch (alt47) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:222:22: DATABASE
                    {
                    DATABASE155=(Token)match(input,DATABASE,FOLLOW_DATABASE_in_attach_stmt1552); 
                    DATABASE155_tree = (Object)adaptor.create(DATABASE155);
                    adaptor.addChild(root_0, DATABASE155_tree);


                    }
                    break;

            }

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:222:42: ( STRING | id )
            int alt48=2;
            int LA48_0 = input.LA(1);

            if ( (LA48_0==STRING) ) {
                alt48=1;
            }
            else if ( ((LA48_0>=EXPLAIN && LA48_0<=PLAN)||(LA48_0>=INDEXED && LA48_0<=BY)||(LA48_0>=OR && LA48_0<=ESCAPE)||(LA48_0>=IS && LA48_0<=BETWEEN)||(LA48_0>=COLLATE && LA48_0<=THEN)||(LA48_0>=CURRENT_TIME && LA48_0<=CURRENT_TIMESTAMP)||(LA48_0>=RAISE && LA48_0<=FAIL)||(LA48_0>=PRAGMA && LA48_0<=FOR)||(LA48_0>=UNIQUE && LA48_0<=ROW)) ) {
                alt48=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 48, 0, input);

                throw nvae;
            }
            switch (alt48) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:222:43: STRING
                    {
                    STRING156=(Token)match(input,STRING,FOLLOW_STRING_in_attach_stmt1559); 
                    STRING156_tree = (Object)adaptor.create(STRING156);
                    adaptor.addChild(root_0, STRING156_tree);


                    }
                    break;
                case 2 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:222:52: id
                    {
                    pushFollow(FOLLOW_id_in_attach_stmt1563);
                    id157=id();

                    state._fsp--;

                    adaptor.addChild(root_0, id157.getTree());

                    }
                    break;

            }

            AS158=(Token)match(input,AS,FOLLOW_AS_in_attach_stmt1566); 
            AS158_tree = (Object)adaptor.create(AS158);
            adaptor.addChild(root_0, AS158_tree);

            pushFollow(FOLLOW_id_in_attach_stmt1570);
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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:225:1: detach_stmt : DETACH ( DATABASE )? database_name= id ;
    public final SqlParser.detach_stmt_return detach_stmt() throws RecognitionException {
        SqlParser.detach_stmt_return retval = new SqlParser.detach_stmt_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token DETACH159=null;
        Token DATABASE160=null;
        SqlParser.id_return database_name = null;


        Object DETACH159_tree=null;
        Object DATABASE160_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:225:12: ( DETACH ( DATABASE )? database_name= id )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:225:14: DETACH ( DATABASE )? database_name= id
            {
            root_0 = (Object)adaptor.nil();

            DETACH159=(Token)match(input,DETACH,FOLLOW_DETACH_in_detach_stmt1578); 
            DETACH159_tree = (Object)adaptor.create(DETACH159);
            adaptor.addChild(root_0, DETACH159_tree);

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:225:21: ( DATABASE )?
            int alt49=2;
            int LA49_0 = input.LA(1);

            if ( (LA49_0==DATABASE) ) {
                int LA49_1 = input.LA(2);

                if ( ((LA49_1>=EXPLAIN && LA49_1<=PLAN)||(LA49_1>=INDEXED && LA49_1<=BY)||(LA49_1>=OR && LA49_1<=ESCAPE)||(LA49_1>=IS && LA49_1<=BETWEEN)||(LA49_1>=COLLATE && LA49_1<=THEN)||(LA49_1>=CURRENT_TIME && LA49_1<=CURRENT_TIMESTAMP)||(LA49_1>=RAISE && LA49_1<=FAIL)||(LA49_1>=PRAGMA && LA49_1<=FOR)||(LA49_1>=UNIQUE && LA49_1<=ROW)) ) {
                    alt49=1;
                }
            }
            switch (alt49) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:225:22: DATABASE
                    {
                    DATABASE160=(Token)match(input,DATABASE,FOLLOW_DATABASE_in_detach_stmt1581); 
                    DATABASE160_tree = (Object)adaptor.create(DATABASE160);
                    adaptor.addChild(root_0, DATABASE160_tree);


                    }
                    break;

            }

            pushFollow(FOLLOW_id_in_detach_stmt1587);
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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:228:1: analyze_stmt : ANALYZE (database_or_table_name= id | database_name= id DOT table_name= id )? ;
    public final SqlParser.analyze_stmt_return analyze_stmt() throws RecognitionException {
        SqlParser.analyze_stmt_return retval = new SqlParser.analyze_stmt_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ANALYZE161=null;
        Token DOT162=null;
        SqlParser.id_return database_or_table_name = null;

        SqlParser.id_return database_name = null;

        SqlParser.id_return table_name = null;


        Object ANALYZE161_tree=null;
        Object DOT162_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:228:13: ( ANALYZE (database_or_table_name= id | database_name= id DOT table_name= id )? )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:228:15: ANALYZE (database_or_table_name= id | database_name= id DOT table_name= id )?
            {
            root_0 = (Object)adaptor.nil();

            ANALYZE161=(Token)match(input,ANALYZE,FOLLOW_ANALYZE_in_analyze_stmt1595); 
            ANALYZE161_tree = (Object)adaptor.create(ANALYZE161);
            adaptor.addChild(root_0, ANALYZE161_tree);

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:228:23: (database_or_table_name= id | database_name= id DOT table_name= id )?
            int alt50=3;
            int LA50_0 = input.LA(1);

            if ( (LA50_0==ID) ) {
                int LA50_1 = input.LA(2);

                if ( (LA50_1==SEMI) ) {
                    alt50=1;
                }
                else if ( (LA50_1==DOT) ) {
                    alt50=2;
                }
            }
            else if ( ((LA50_0>=EXPLAIN && LA50_0<=PLAN)||(LA50_0>=INDEXED && LA50_0<=BY)||(LA50_0>=OR && LA50_0<=ESCAPE)||(LA50_0>=IS && LA50_0<=BETWEEN)||LA50_0==COLLATE||(LA50_0>=DISTINCT && LA50_0<=THEN)||(LA50_0>=CURRENT_TIME && LA50_0<=CURRENT_TIMESTAMP)||(LA50_0>=RAISE && LA50_0<=FAIL)||(LA50_0>=PRAGMA && LA50_0<=FOR)||(LA50_0>=UNIQUE && LA50_0<=ROW)) ) {
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
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:228:24: database_or_table_name= id
                    {
                    pushFollow(FOLLOW_id_in_analyze_stmt1600);
                    database_or_table_name=id();

                    state._fsp--;

                    adaptor.addChild(root_0, database_or_table_name.getTree());

                    }
                    break;
                case 2 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:228:52: database_name= id DOT table_name= id
                    {
                    pushFollow(FOLLOW_id_in_analyze_stmt1606);
                    database_name=id();

                    state._fsp--;

                    adaptor.addChild(root_0, database_name.getTree());
                    DOT162=(Token)match(input,DOT,FOLLOW_DOT_in_analyze_stmt1608); 
                    DOT162_tree = (Object)adaptor.create(DOT162);
                    adaptor.addChild(root_0, DOT162_tree);

                    pushFollow(FOLLOW_id_in_analyze_stmt1612);
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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:231:1: reindex_stmt : REINDEX (database_name= id DOT )? collation_or_table_or_index_name= id ;
    public final SqlParser.reindex_stmt_return reindex_stmt() throws RecognitionException {
        SqlParser.reindex_stmt_return retval = new SqlParser.reindex_stmt_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token REINDEX163=null;
        Token DOT164=null;
        SqlParser.id_return database_name = null;

        SqlParser.id_return collation_or_table_or_index_name = null;


        Object REINDEX163_tree=null;
        Object DOT164_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:231:13: ( REINDEX (database_name= id DOT )? collation_or_table_or_index_name= id )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:231:15: REINDEX (database_name= id DOT )? collation_or_table_or_index_name= id
            {
            root_0 = (Object)adaptor.nil();

            REINDEX163=(Token)match(input,REINDEX,FOLLOW_REINDEX_in_reindex_stmt1622); 
            REINDEX163_tree = (Object)adaptor.create(REINDEX163);
            adaptor.addChild(root_0, REINDEX163_tree);

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:231:23: (database_name= id DOT )?
            int alt51=2;
            int LA51_0 = input.LA(1);

            if ( (LA51_0==ID) ) {
                int LA51_1 = input.LA(2);

                if ( (LA51_1==DOT) ) {
                    alt51=1;
                }
            }
            else if ( ((LA51_0>=EXPLAIN && LA51_0<=PLAN)||(LA51_0>=INDEXED && LA51_0<=BY)||(LA51_0>=OR && LA51_0<=ESCAPE)||(LA51_0>=IS && LA51_0<=BETWEEN)||LA51_0==COLLATE||(LA51_0>=DISTINCT && LA51_0<=THEN)||(LA51_0>=CURRENT_TIME && LA51_0<=CURRENT_TIMESTAMP)||(LA51_0>=RAISE && LA51_0<=FAIL)||(LA51_0>=PRAGMA && LA51_0<=FOR)||(LA51_0>=UNIQUE && LA51_0<=ROW)) ) {
                int LA51_2 = input.LA(2);

                if ( (LA51_2==DOT) ) {
                    alt51=1;
                }
            }
            switch (alt51) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:231:24: database_name= id DOT
                    {
                    pushFollow(FOLLOW_id_in_reindex_stmt1627);
                    database_name=id();

                    state._fsp--;

                    adaptor.addChild(root_0, database_name.getTree());
                    DOT164=(Token)match(input,DOT,FOLLOW_DOT_in_reindex_stmt1629); 
                    DOT164_tree = (Object)adaptor.create(DOT164);
                    adaptor.addChild(root_0, DOT164_tree);


                    }
                    break;

            }

            pushFollow(FOLLOW_id_in_reindex_stmt1635);
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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:234:1: vacuum_stmt : VACUUM ;
    public final SqlParser.vacuum_stmt_return vacuum_stmt() throws RecognitionException {
        SqlParser.vacuum_stmt_return retval = new SqlParser.vacuum_stmt_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token VACUUM165=null;

        Object VACUUM165_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:234:12: ( VACUUM )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:234:14: VACUUM
            {
            root_0 = (Object)adaptor.nil();

            VACUUM165=(Token)match(input,VACUUM,FOLLOW_VACUUM_in_vacuum_stmt1643); 
            VACUUM165_tree = (Object)adaptor.create(VACUUM165);
            adaptor.addChild(root_0, VACUUM165_tree);


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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:240:1: operation_conflict_clause : OR ( ROLLBACK | ABORT | FAIL | IGNORE | REPLACE ) ;
    public final SqlParser.operation_conflict_clause_return operation_conflict_clause() throws RecognitionException {
        SqlParser.operation_conflict_clause_return retval = new SqlParser.operation_conflict_clause_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token OR166=null;
        Token set167=null;

        Object OR166_tree=null;
        Object set167_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:240:26: ( OR ( ROLLBACK | ABORT | FAIL | IGNORE | REPLACE ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:240:28: OR ( ROLLBACK | ABORT | FAIL | IGNORE | REPLACE )
            {
            root_0 = (Object)adaptor.nil();

            OR166=(Token)match(input,OR,FOLLOW_OR_in_operation_conflict_clause1654); 
            OR166_tree = (Object)adaptor.create(OR166);
            adaptor.addChild(root_0, OR166_tree);

            set167=(Token)input.LT(1);
            if ( (input.LA(1)>=IGNORE && input.LA(1)<=FAIL)||input.LA(1)==REPLACE ) {
                input.consume();
                adaptor.addChild(root_0, (Object)adaptor.create(set167));
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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:242:1: ordering_term : expr ( ASC | DESC )? -> ^( ORDERING expr ( ASC )? ( DESC )? ) ;
    public final SqlParser.ordering_term_return ordering_term() throws RecognitionException {
        SqlParser.ordering_term_return retval = new SqlParser.ordering_term_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ASC169=null;
        Token DESC170=null;
        SqlParser.expr_return expr168 = null;


        Object ASC169_tree=null;
        Object DESC170_tree=null;
        RewriteRuleTokenStream stream_ASC=new RewriteRuleTokenStream(adaptor,"token ASC");
        RewriteRuleTokenStream stream_DESC=new RewriteRuleTokenStream(adaptor,"token DESC");
        RewriteRuleSubtreeStream stream_expr=new RewriteRuleSubtreeStream(adaptor,"rule expr");
        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:242:14: ( expr ( ASC | DESC )? -> ^( ORDERING expr ( ASC )? ( DESC )? ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:242:16: expr ( ASC | DESC )?
            {
            pushFollow(FOLLOW_expr_in_ordering_term1681);
            expr168=expr();

            state._fsp--;

            stream_expr.add(expr168.getTree());
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:242:82: ( ASC | DESC )?
            int alt52=3;
            alt52 = dfa52.predict(input);
            switch (alt52) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:242:83: ASC
                    {
                    ASC169=(Token)match(input,ASC,FOLLOW_ASC_in_ordering_term1686);  
                    stream_ASC.add(ASC169);


                    }
                    break;
                case 2 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:242:89: DESC
                    {
                    DESC170=(Token)match(input,DESC,FOLLOW_DESC_in_ordering_term1690);  
                    stream_DESC.add(DESC170);


                    }
                    break;

            }



            // AST REWRITE
            // elements: DESC, expr, ASC
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 243:1: -> ^( ORDERING expr ( ASC )? ( DESC )? )
            {
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:243:4: ^( ORDERING expr ( ASC )? ( DESC )? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(ORDERING, "ORDERING"), root_1);

                adaptor.addChild(root_1, stream_expr.nextTree());
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:243:20: ( ASC )?
                if ( stream_ASC.hasNext() ) {
                    adaptor.addChild(root_1, stream_ASC.nextNode());

                }
                stream_ASC.reset();
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:243:27: ( DESC )?
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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:245:1: operation_limited_clause : ( ORDER BY ordering_term ( COMMA ordering_term )* )? LIMIT limit= INTEGER ( ( OFFSET | COMMA ) offset= INTEGER )? ;
    public final SqlParser.operation_limited_clause_return operation_limited_clause() throws RecognitionException {
        SqlParser.operation_limited_clause_return retval = new SqlParser.operation_limited_clause_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token limit=null;
        Token offset=null;
        Token ORDER171=null;
        Token BY172=null;
        Token COMMA174=null;
        Token LIMIT176=null;
        Token set177=null;
        SqlParser.ordering_term_return ordering_term173 = null;

        SqlParser.ordering_term_return ordering_term175 = null;


        Object limit_tree=null;
        Object offset_tree=null;
        Object ORDER171_tree=null;
        Object BY172_tree=null;
        Object COMMA174_tree=null;
        Object LIMIT176_tree=null;
        Object set177_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:245:25: ( ( ORDER BY ordering_term ( COMMA ordering_term )* )? LIMIT limit= INTEGER ( ( OFFSET | COMMA ) offset= INTEGER )? )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:246:3: ( ORDER BY ordering_term ( COMMA ordering_term )* )? LIMIT limit= INTEGER ( ( OFFSET | COMMA ) offset= INTEGER )?
            {
            root_0 = (Object)adaptor.nil();

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:246:3: ( ORDER BY ordering_term ( COMMA ordering_term )* )?
            int alt54=2;
            int LA54_0 = input.LA(1);

            if ( (LA54_0==ORDER) ) {
                alt54=1;
            }
            switch (alt54) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:246:4: ORDER BY ordering_term ( COMMA ordering_term )*
                    {
                    ORDER171=(Token)match(input,ORDER,FOLLOW_ORDER_in_operation_limited_clause1720); 
                    ORDER171_tree = (Object)adaptor.create(ORDER171);
                    adaptor.addChild(root_0, ORDER171_tree);

                    BY172=(Token)match(input,BY,FOLLOW_BY_in_operation_limited_clause1722); 
                    BY172_tree = (Object)adaptor.create(BY172);
                    adaptor.addChild(root_0, BY172_tree);

                    pushFollow(FOLLOW_ordering_term_in_operation_limited_clause1724);
                    ordering_term173=ordering_term();

                    state._fsp--;

                    adaptor.addChild(root_0, ordering_term173.getTree());
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:246:27: ( COMMA ordering_term )*
                    loop53:
                    do {
                        int alt53=2;
                        int LA53_0 = input.LA(1);

                        if ( (LA53_0==COMMA) ) {
                            alt53=1;
                        }


                        switch (alt53) {
                    	case 1 :
                    	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:246:28: COMMA ordering_term
                    	    {
                    	    COMMA174=(Token)match(input,COMMA,FOLLOW_COMMA_in_operation_limited_clause1727); 
                    	    COMMA174_tree = (Object)adaptor.create(COMMA174);
                    	    adaptor.addChild(root_0, COMMA174_tree);

                    	    pushFollow(FOLLOW_ordering_term_in_operation_limited_clause1729);
                    	    ordering_term175=ordering_term();

                    	    state._fsp--;

                    	    adaptor.addChild(root_0, ordering_term175.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop53;
                        }
                    } while (true);


                    }
                    break;

            }

            LIMIT176=(Token)match(input,LIMIT,FOLLOW_LIMIT_in_operation_limited_clause1737); 
            LIMIT176_tree = (Object)adaptor.create(LIMIT176);
            adaptor.addChild(root_0, LIMIT176_tree);

            limit=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_operation_limited_clause1741); 
            limit_tree = (Object)adaptor.create(limit);
            adaptor.addChild(root_0, limit_tree);

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:247:23: ( ( OFFSET | COMMA ) offset= INTEGER )?
            int alt55=2;
            int LA55_0 = input.LA(1);

            if ( (LA55_0==COMMA||LA55_0==OFFSET) ) {
                alt55=1;
            }
            switch (alt55) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:247:24: ( OFFSET | COMMA ) offset= INTEGER
                    {
                    set177=(Token)input.LT(1);
                    if ( input.LA(1)==COMMA||input.LA(1)==OFFSET ) {
                        input.consume();
                        adaptor.addChild(root_0, (Object)adaptor.create(set177));
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    offset=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_operation_limited_clause1754); 
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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:250:1: select_stmt : select_list ( ORDER BY ordering_term ( COMMA ordering_term )* )? ( LIMIT limit= INTEGER ( ( OFFSET | COMMA ) offset= INTEGER )? )? -> ^( SELECT select_list ( ^( ORDER ( ordering_term )+ ) )? ( ^( LIMIT $limit ( $offset)? ) )? ) ;
    public final SqlParser.select_stmt_return select_stmt() throws RecognitionException {
        SqlParser.select_stmt_return retval = new SqlParser.select_stmt_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token limit=null;
        Token offset=null;
        Token ORDER179=null;
        Token BY180=null;
        Token COMMA182=null;
        Token LIMIT184=null;
        Token OFFSET185=null;
        Token COMMA186=null;
        SqlParser.select_list_return select_list178 = null;

        SqlParser.ordering_term_return ordering_term181 = null;

        SqlParser.ordering_term_return ordering_term183 = null;


        Object limit_tree=null;
        Object offset_tree=null;
        Object ORDER179_tree=null;
        Object BY180_tree=null;
        Object COMMA182_tree=null;
        Object LIMIT184_tree=null;
        Object OFFSET185_tree=null;
        Object COMMA186_tree=null;
        RewriteRuleTokenStream stream_INTEGER=new RewriteRuleTokenStream(adaptor,"token INTEGER");
        RewriteRuleTokenStream stream_BY=new RewriteRuleTokenStream(adaptor,"token BY");
        RewriteRuleTokenStream stream_ORDER=new RewriteRuleTokenStream(adaptor,"token ORDER");
        RewriteRuleTokenStream stream_COMMA=new RewriteRuleTokenStream(adaptor,"token COMMA");
        RewriteRuleTokenStream stream_LIMIT=new RewriteRuleTokenStream(adaptor,"token LIMIT");
        RewriteRuleTokenStream stream_OFFSET=new RewriteRuleTokenStream(adaptor,"token OFFSET");
        RewriteRuleSubtreeStream stream_select_list=new RewriteRuleSubtreeStream(adaptor,"rule select_list");
        RewriteRuleSubtreeStream stream_ordering_term=new RewriteRuleSubtreeStream(adaptor,"rule ordering_term");
        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:250:12: ( select_list ( ORDER BY ordering_term ( COMMA ordering_term )* )? ( LIMIT limit= INTEGER ( ( OFFSET | COMMA ) offset= INTEGER )? )? -> ^( SELECT select_list ( ^( ORDER ( ordering_term )+ ) )? ( ^( LIMIT $limit ( $offset)? ) )? ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:250:14: select_list ( ORDER BY ordering_term ( COMMA ordering_term )* )? ( LIMIT limit= INTEGER ( ( OFFSET | COMMA ) offset= INTEGER )? )?
            {
            pushFollow(FOLLOW_select_list_in_select_stmt1764);
            select_list178=select_list();

            state._fsp--;

            stream_select_list.add(select_list178.getTree());
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:251:3: ( ORDER BY ordering_term ( COMMA ordering_term )* )?
            int alt57=2;
            int LA57_0 = input.LA(1);

            if ( (LA57_0==ORDER) ) {
                alt57=1;
            }
            switch (alt57) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:251:4: ORDER BY ordering_term ( COMMA ordering_term )*
                    {
                    ORDER179=(Token)match(input,ORDER,FOLLOW_ORDER_in_select_stmt1769);  
                    stream_ORDER.add(ORDER179);

                    BY180=(Token)match(input,BY,FOLLOW_BY_in_select_stmt1771);  
                    stream_BY.add(BY180);

                    pushFollow(FOLLOW_ordering_term_in_select_stmt1773);
                    ordering_term181=ordering_term();

                    state._fsp--;

                    stream_ordering_term.add(ordering_term181.getTree());
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:251:27: ( COMMA ordering_term )*
                    loop56:
                    do {
                        int alt56=2;
                        int LA56_0 = input.LA(1);

                        if ( (LA56_0==COMMA) ) {
                            alt56=1;
                        }


                        switch (alt56) {
                    	case 1 :
                    	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:251:28: COMMA ordering_term
                    	    {
                    	    COMMA182=(Token)match(input,COMMA,FOLLOW_COMMA_in_select_stmt1776);  
                    	    stream_COMMA.add(COMMA182);

                    	    pushFollow(FOLLOW_ordering_term_in_select_stmt1778);
                    	    ordering_term183=ordering_term();

                    	    state._fsp--;

                    	    stream_ordering_term.add(ordering_term183.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop56;
                        }
                    } while (true);


                    }
                    break;

            }

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:252:3: ( LIMIT limit= INTEGER ( ( OFFSET | COMMA ) offset= INTEGER )? )?
            int alt60=2;
            int LA60_0 = input.LA(1);

            if ( (LA60_0==LIMIT) ) {
                alt60=1;
            }
            switch (alt60) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:252:4: LIMIT limit= INTEGER ( ( OFFSET | COMMA ) offset= INTEGER )?
                    {
                    LIMIT184=(Token)match(input,LIMIT,FOLLOW_LIMIT_in_select_stmt1787);  
                    stream_LIMIT.add(LIMIT184);

                    limit=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_select_stmt1791);  
                    stream_INTEGER.add(limit);

                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:252:24: ( ( OFFSET | COMMA ) offset= INTEGER )?
                    int alt59=2;
                    int LA59_0 = input.LA(1);

                    if ( (LA59_0==COMMA||LA59_0==OFFSET) ) {
                        alt59=1;
                    }
                    switch (alt59) {
                        case 1 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:252:25: ( OFFSET | COMMA ) offset= INTEGER
                            {
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:252:25: ( OFFSET | COMMA )
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
                                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:252:26: OFFSET
                                    {
                                    OFFSET185=(Token)match(input,OFFSET,FOLLOW_OFFSET_in_select_stmt1795);  
                                    stream_OFFSET.add(OFFSET185);


                                    }
                                    break;
                                case 2 :
                                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:252:35: COMMA
                                    {
                                    COMMA186=(Token)match(input,COMMA,FOLLOW_COMMA_in_select_stmt1799);  
                                    stream_COMMA.add(COMMA186);


                                    }
                                    break;

                            }

                            offset=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_select_stmt1804);  
                            stream_INTEGER.add(offset);


                            }
                            break;

                    }


                    }
                    break;

            }



            // AST REWRITE
            // elements: ORDER, ordering_term, select_list, offset, limit, LIMIT
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
            // 253:1: -> ^( SELECT select_list ( ^( ORDER ( ordering_term )+ ) )? ( ^( LIMIT $limit ( $offset)? ) )? )
            {
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:253:4: ^( SELECT select_list ( ^( ORDER ( ordering_term )+ ) )? ( ^( LIMIT $limit ( $offset)? ) )? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(SELECT, "SELECT"), root_1);

                adaptor.addChild(root_1, stream_select_list.nextTree());
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:254:22: ( ^( ORDER ( ordering_term )+ ) )?
                if ( stream_ORDER.hasNext()||stream_ordering_term.hasNext() ) {
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:254:22: ^( ORDER ( ordering_term )+ )
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
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:254:47: ( ^( LIMIT $limit ( $offset)? ) )?
                if ( stream_offset.hasNext()||stream_limit.hasNext()||stream_LIMIT.hasNext() ) {
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:254:47: ^( LIMIT $limit ( $offset)? )
                    {
                    Object root_2 = (Object)adaptor.nil();
                    root_2 = (Object)adaptor.becomeRoot(stream_LIMIT.nextNode(), root_2);

                    adaptor.addChild(root_2, stream_limit.nextNode());
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:254:62: ( $offset)?
                    if ( stream_offset.hasNext() ) {
                        adaptor.addChild(root_2, stream_offset.nextNode());

                    }
                    stream_offset.reset();

                    adaptor.addChild(root_1, root_2);
                    }

                }
                stream_offset.reset();
                stream_limit.reset();
                stream_LIMIT.reset();

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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:257:1: select_list : select_core ( select_op select_core )* ;
    public final SqlParser.select_list_return select_list() throws RecognitionException {
        SqlParser.select_list_return retval = new SqlParser.select_list_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        SqlParser.select_core_return select_core187 = null;

        SqlParser.select_op_return select_op188 = null;

        SqlParser.select_core_return select_core189 = null;



        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:257:12: ( select_core ( select_op select_core )* )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:258:3: select_core ( select_op select_core )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_select_core_in_select_list1849);
            select_core187=select_core();

            state._fsp--;

            adaptor.addChild(root_0, select_core187.getTree());
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:258:15: ( select_op select_core )*
            loop61:
            do {
                int alt61=2;
                int LA61_0 = input.LA(1);

                if ( (LA61_0==UNION||(LA61_0>=INTERSECT && LA61_0<=EXCEPT)) ) {
                    alt61=1;
                }


                switch (alt61) {
            	case 1 :
            	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:258:16: select_op select_core
            	    {
            	    pushFollow(FOLLOW_select_op_in_select_list1852);
            	    select_op188=select_op();

            	    state._fsp--;

            	    root_0 = (Object)adaptor.becomeRoot(select_op188.getTree(), root_0);
            	    pushFollow(FOLLOW_select_core_in_select_list1855);
            	    select_core189=select_core();

            	    state._fsp--;

            	    adaptor.addChild(root_0, select_core189.getTree());

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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:260:1: select_op : ( UNION ( ALL )? | INTERSECT | EXCEPT );
    public final SqlParser.select_op_return select_op() throws RecognitionException {
        SqlParser.select_op_return retval = new SqlParser.select_op_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token UNION190=null;
        Token ALL191=null;
        Token INTERSECT192=null;
        Token EXCEPT193=null;

        Object UNION190_tree=null;
        Object ALL191_tree=null;
        Object INTERSECT192_tree=null;
        Object EXCEPT193_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:260:10: ( UNION ( ALL )? | INTERSECT | EXCEPT )
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
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:260:12: UNION ( ALL )?
                    {
                    root_0 = (Object)adaptor.nil();

                    UNION190=(Token)match(input,UNION,FOLLOW_UNION_in_select_op1864); 
                    UNION190_tree = (Object)adaptor.create(UNION190);
                    root_0 = (Object)adaptor.becomeRoot(UNION190_tree, root_0);

                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:260:19: ( ALL )?
                    int alt62=2;
                    int LA62_0 = input.LA(1);

                    if ( (LA62_0==ALL) ) {
                        alt62=1;
                    }
                    switch (alt62) {
                        case 1 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:260:20: ALL
                            {
                            ALL191=(Token)match(input,ALL,FOLLOW_ALL_in_select_op1868); 
                            ALL191_tree = (Object)adaptor.create(ALL191);
                            adaptor.addChild(root_0, ALL191_tree);


                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:260:28: INTERSECT
                    {
                    root_0 = (Object)adaptor.nil();

                    INTERSECT192=(Token)match(input,INTERSECT,FOLLOW_INTERSECT_in_select_op1874); 
                    INTERSECT192_tree = (Object)adaptor.create(INTERSECT192);
                    adaptor.addChild(root_0, INTERSECT192_tree);


                    }
                    break;
                case 3 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:260:40: EXCEPT
                    {
                    root_0 = (Object)adaptor.nil();

                    EXCEPT193=(Token)match(input,EXCEPT,FOLLOW_EXCEPT_in_select_op1878); 
                    EXCEPT193_tree = (Object)adaptor.create(EXCEPT193);
                    adaptor.addChild(root_0, EXCEPT193_tree);


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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:262:1: select_core : SELECT ( ALL | DISTINCT )? result_column ( COMMA result_column )* ( FROM join_source )? ( WHERE where_expr= expr )? ( GROUP BY ordering_term ( COMMA ordering_term )* ( HAVING having_expr= expr )? )? -> ^( SELECT_CORE ( DISTINCT )? ^( COLUMNS ( result_column )+ ) ( ^( FROM join_source ) )? ( ^( WHERE $where_expr) )? ( ^( GROUP ( ordering_term )+ ( ^( HAVING $having_expr) )? ) )? ) ;
    public final SqlParser.select_core_return select_core() throws RecognitionException {
        SqlParser.select_core_return retval = new SqlParser.select_core_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token SELECT194=null;
        Token ALL195=null;
        Token DISTINCT196=null;
        Token COMMA198=null;
        Token FROM200=null;
        Token WHERE202=null;
        Token GROUP203=null;
        Token BY204=null;
        Token COMMA206=null;
        Token HAVING208=null;
        SqlParser.expr_return where_expr = null;

        SqlParser.expr_return having_expr = null;

        SqlParser.result_column_return result_column197 = null;

        SqlParser.result_column_return result_column199 = null;

        SqlParser.join_source_return join_source201 = null;

        SqlParser.ordering_term_return ordering_term205 = null;

        SqlParser.ordering_term_return ordering_term207 = null;


        Object SELECT194_tree=null;
        Object ALL195_tree=null;
        Object DISTINCT196_tree=null;
        Object COMMA198_tree=null;
        Object FROM200_tree=null;
        Object WHERE202_tree=null;
        Object GROUP203_tree=null;
        Object BY204_tree=null;
        Object COMMA206_tree=null;
        Object HAVING208_tree=null;
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
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:262:12: ( SELECT ( ALL | DISTINCT )? result_column ( COMMA result_column )* ( FROM join_source )? ( WHERE where_expr= expr )? ( GROUP BY ordering_term ( COMMA ordering_term )* ( HAVING having_expr= expr )? )? -> ^( SELECT_CORE ( DISTINCT )? ^( COLUMNS ( result_column )+ ) ( ^( FROM join_source ) )? ( ^( WHERE $where_expr) )? ( ^( GROUP ( ordering_term )+ ( ^( HAVING $having_expr) )? ) )? ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:263:3: SELECT ( ALL | DISTINCT )? result_column ( COMMA result_column )* ( FROM join_source )? ( WHERE where_expr= expr )? ( GROUP BY ordering_term ( COMMA ordering_term )* ( HAVING having_expr= expr )? )?
            {
            SELECT194=(Token)match(input,SELECT,FOLLOW_SELECT_in_select_core1887);  
            stream_SELECT.add(SELECT194);

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:263:10: ( ALL | DISTINCT )?
            int alt64=3;
            alt64 = dfa64.predict(input);
            switch (alt64) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:263:11: ALL
                    {
                    ALL195=(Token)match(input,ALL,FOLLOW_ALL_in_select_core1890);  
                    stream_ALL.add(ALL195);


                    }
                    break;
                case 2 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:263:17: DISTINCT
                    {
                    DISTINCT196=(Token)match(input,DISTINCT,FOLLOW_DISTINCT_in_select_core1894);  
                    stream_DISTINCT.add(DISTINCT196);


                    }
                    break;

            }

            pushFollow(FOLLOW_result_column_in_select_core1898);
            result_column197=result_column();

            state._fsp--;

            stream_result_column.add(result_column197.getTree());
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:263:42: ( COMMA result_column )*
            loop65:
            do {
                int alt65=2;
                alt65 = dfa65.predict(input);
                switch (alt65) {
            	case 1 :
            	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:263:43: COMMA result_column
            	    {
            	    COMMA198=(Token)match(input,COMMA,FOLLOW_COMMA_in_select_core1901);  
            	    stream_COMMA.add(COMMA198);

            	    pushFollow(FOLLOW_result_column_in_select_core1903);
            	    result_column199=result_column();

            	    state._fsp--;

            	    stream_result_column.add(result_column199.getTree());

            	    }
            	    break;

            	default :
            	    break loop65;
                }
            } while (true);

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:263:65: ( FROM join_source )?
            int alt66=2;
            alt66 = dfa66.predict(input);
            switch (alt66) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:263:66: FROM join_source
                    {
                    FROM200=(Token)match(input,FROM,FOLLOW_FROM_in_select_core1908);  
                    stream_FROM.add(FROM200);

                    pushFollow(FOLLOW_join_source_in_select_core1910);
                    join_source201=join_source();

                    state._fsp--;

                    stream_join_source.add(join_source201.getTree());

                    }
                    break;

            }

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:263:85: ( WHERE where_expr= expr )?
            int alt67=2;
            alt67 = dfa67.predict(input);
            switch (alt67) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:263:86: WHERE where_expr= expr
                    {
                    WHERE202=(Token)match(input,WHERE,FOLLOW_WHERE_in_select_core1915);  
                    stream_WHERE.add(WHERE202);

                    pushFollow(FOLLOW_expr_in_select_core1919);
                    where_expr=expr();

                    state._fsp--;

                    stream_expr.add(where_expr.getTree());

                    }
                    break;

            }

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:264:3: ( GROUP BY ordering_term ( COMMA ordering_term )* ( HAVING having_expr= expr )? )?
            int alt70=2;
            int LA70_0 = input.LA(1);

            if ( (LA70_0==GROUP) ) {
                alt70=1;
            }
            switch (alt70) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:264:5: GROUP BY ordering_term ( COMMA ordering_term )* ( HAVING having_expr= expr )?
                    {
                    GROUP203=(Token)match(input,GROUP,FOLLOW_GROUP_in_select_core1927);  
                    stream_GROUP.add(GROUP203);

                    BY204=(Token)match(input,BY,FOLLOW_BY_in_select_core1929);  
                    stream_BY.add(BY204);

                    pushFollow(FOLLOW_ordering_term_in_select_core1931);
                    ordering_term205=ordering_term();

                    state._fsp--;

                    stream_ordering_term.add(ordering_term205.getTree());
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:264:28: ( COMMA ordering_term )*
                    loop68:
                    do {
                        int alt68=2;
                        alt68 = dfa68.predict(input);
                        switch (alt68) {
                    	case 1 :
                    	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:264:29: COMMA ordering_term
                    	    {
                    	    COMMA206=(Token)match(input,COMMA,FOLLOW_COMMA_in_select_core1934);  
                    	    stream_COMMA.add(COMMA206);

                    	    pushFollow(FOLLOW_ordering_term_in_select_core1936);
                    	    ordering_term207=ordering_term();

                    	    state._fsp--;

                    	    stream_ordering_term.add(ordering_term207.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop68;
                        }
                    } while (true);

                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:264:51: ( HAVING having_expr= expr )?
                    int alt69=2;
                    int LA69_0 = input.LA(1);

                    if ( (LA69_0==HAVING) ) {
                        alt69=1;
                    }
                    switch (alt69) {
                        case 1 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:264:52: HAVING having_expr= expr
                            {
                            HAVING208=(Token)match(input,HAVING,FOLLOW_HAVING_in_select_core1941);  
                            stream_HAVING.add(HAVING208);

                            pushFollow(FOLLOW_expr_in_select_core1945);
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
            // elements: join_source, WHERE, ordering_term, HAVING, having_expr, result_column, GROUP, where_expr, DISTINCT, FROM
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
            // 265:1: -> ^( SELECT_CORE ( DISTINCT )? ^( COLUMNS ( result_column )+ ) ( ^( FROM join_source ) )? ( ^( WHERE $where_expr) )? ( ^( GROUP ( ordering_term )+ ( ^( HAVING $having_expr) )? ) )? )
            {
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:265:4: ^( SELECT_CORE ( DISTINCT )? ^( COLUMNS ( result_column )+ ) ( ^( FROM join_source ) )? ( ^( WHERE $where_expr) )? ( ^( GROUP ( ordering_term )+ ( ^( HAVING $having_expr) )? ) )? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(SELECT_CORE, "SELECT_CORE"), root_1);

                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:266:15: ( DISTINCT )?
                if ( stream_DISTINCT.hasNext() ) {
                    adaptor.addChild(root_1, stream_DISTINCT.nextNode());

                }
                stream_DISTINCT.reset();
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:266:27: ^( COLUMNS ( result_column )+ )
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
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:266:53: ( ^( FROM join_source ) )?
                if ( stream_join_source.hasNext()||stream_FROM.hasNext() ) {
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:266:53: ^( FROM join_source )
                    {
                    Object root_2 = (Object)adaptor.nil();
                    root_2 = (Object)adaptor.becomeRoot(stream_FROM.nextNode(), root_2);

                    adaptor.addChild(root_2, stream_join_source.nextTree());

                    adaptor.addChild(root_1, root_2);
                    }

                }
                stream_join_source.reset();
                stream_FROM.reset();
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:266:74: ( ^( WHERE $where_expr) )?
                if ( stream_WHERE.hasNext()||stream_where_expr.hasNext() ) {
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:266:74: ^( WHERE $where_expr)
                    {
                    Object root_2 = (Object)adaptor.nil();
                    root_2 = (Object)adaptor.becomeRoot(stream_WHERE.nextNode(), root_2);

                    adaptor.addChild(root_2, stream_where_expr.nextTree());

                    adaptor.addChild(root_1, root_2);
                    }

                }
                stream_WHERE.reset();
                stream_where_expr.reset();
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:267:3: ( ^( GROUP ( ordering_term )+ ( ^( HAVING $having_expr) )? ) )?
                if ( stream_ordering_term.hasNext()||stream_HAVING.hasNext()||stream_having_expr.hasNext()||stream_GROUP.hasNext() ) {
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:267:3: ^( GROUP ( ordering_term )+ ( ^( HAVING $having_expr) )? )
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
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:267:26: ( ^( HAVING $having_expr) )?
                    if ( stream_HAVING.hasNext()||stream_having_expr.hasNext() ) {
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:267:26: ^( HAVING $having_expr)
                        {
                        Object root_3 = (Object)adaptor.nil();
                        root_3 = (Object)adaptor.becomeRoot(stream_HAVING.nextNode(), root_3);

                        adaptor.addChild(root_3, stream_having_expr.nextTree());

                        adaptor.addChild(root_2, root_3);
                        }

                    }
                    stream_HAVING.reset();
                    stream_having_expr.reset();

                    adaptor.addChild(root_1, root_2);
                    }

                }
                stream_ordering_term.reset();
                stream_HAVING.reset();
                stream_having_expr.reset();
                stream_GROUP.reset();

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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:270:1: result_column : ( ASTERISK | table_name= id DOT ASTERISK -> ^( ASTERISK $table_name) | expr ( ( AS )? column_alias= id )? -> ^( ALIAS expr ( $column_alias)? ) );
    public final SqlParser.result_column_return result_column() throws RecognitionException {
        SqlParser.result_column_return retval = new SqlParser.result_column_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ASTERISK209=null;
        Token DOT210=null;
        Token ASTERISK211=null;
        Token AS213=null;
        SqlParser.id_return table_name = null;

        SqlParser.id_return column_alias = null;

        SqlParser.expr_return expr212 = null;


        Object ASTERISK209_tree=null;
        Object DOT210_tree=null;
        Object ASTERISK211_tree=null;
        Object AS213_tree=null;
        RewriteRuleTokenStream stream_AS=new RewriteRuleTokenStream(adaptor,"token AS");
        RewriteRuleTokenStream stream_DOT=new RewriteRuleTokenStream(adaptor,"token DOT");
        RewriteRuleTokenStream stream_ASTERISK=new RewriteRuleTokenStream(adaptor,"token ASTERISK");
        RewriteRuleSubtreeStream stream_id=new RewriteRuleSubtreeStream(adaptor,"rule id");
        RewriteRuleSubtreeStream stream_expr=new RewriteRuleSubtreeStream(adaptor,"rule expr");
        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:271:3: ( ASTERISK | table_name= id DOT ASTERISK -> ^( ASTERISK $table_name) | expr ( ( AS )? column_alias= id )? -> ^( ALIAS expr ( $column_alias)? ) )
            int alt73=3;
            alt73 = dfa73.predict(input);
            switch (alt73) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:271:5: ASTERISK
                    {
                    root_0 = (Object)adaptor.nil();

                    ASTERISK209=(Token)match(input,ASTERISK,FOLLOW_ASTERISK_in_result_column2015); 
                    ASTERISK209_tree = (Object)adaptor.create(ASTERISK209);
                    adaptor.addChild(root_0, ASTERISK209_tree);


                    }
                    break;
                case 2 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:272:5: table_name= id DOT ASTERISK
                    {
                    pushFollow(FOLLOW_id_in_result_column2023);
                    table_name=id();

                    state._fsp--;

                    stream_id.add(table_name.getTree());
                    DOT210=(Token)match(input,DOT,FOLLOW_DOT_in_result_column2025);  
                    stream_DOT.add(DOT210);

                    ASTERISK211=(Token)match(input,ASTERISK,FOLLOW_ASTERISK_in_result_column2027);  
                    stream_ASTERISK.add(ASTERISK211);



                    // AST REWRITE
                    // elements: table_name, ASTERISK
                    // token labels: 
                    // rule labels: retval, table_name
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                    RewriteRuleSubtreeStream stream_table_name=new RewriteRuleSubtreeStream(adaptor,"rule table_name",table_name!=null?table_name.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 272:32: -> ^( ASTERISK $table_name)
                    {
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:272:35: ^( ASTERISK $table_name)
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
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:273:5: expr ( ( AS )? column_alias= id )?
                    {
                    pushFollow(FOLLOW_expr_in_result_column2042);
                    expr212=expr();

                    state._fsp--;

                    stream_expr.add(expr212.getTree());
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:273:10: ( ( AS )? column_alias= id )?
                    int alt72=2;
                    alt72 = dfa72.predict(input);
                    switch (alt72) {
                        case 1 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:273:11: ( AS )? column_alias= id
                            {
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:273:11: ( AS )?
                            int alt71=2;
                            alt71 = dfa71.predict(input);
                            switch (alt71) {
                                case 1 :
                                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:273:12: AS
                                    {
                                    AS213=(Token)match(input,AS,FOLLOW_AS_in_result_column2046);  
                                    stream_AS.add(AS213);


                                    }
                                    break;

                            }

                            pushFollow(FOLLOW_id_in_result_column2052);
                            column_alias=id();

                            state._fsp--;

                            stream_id.add(column_alias.getTree());

                            }
                            break;

                    }



                    // AST REWRITE
                    // elements: column_alias, expr
                    // token labels: 
                    // rule labels: retval, column_alias
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                    RewriteRuleSubtreeStream stream_column_alias=new RewriteRuleSubtreeStream(adaptor,"rule column_alias",column_alias!=null?column_alias.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 273:35: -> ^( ALIAS expr ( $column_alias)? )
                    {
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:273:38: ^( ALIAS expr ( $column_alias)? )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(ALIAS, "ALIAS"), root_1);

                        adaptor.addChild(root_1, stream_expr.nextTree());
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:273:51: ( $column_alias)?
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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:275:1: join_source : single_source ( join_op single_source ( join_constraint )? )* ;
    public final SqlParser.join_source_return join_source() throws RecognitionException {
        SqlParser.join_source_return retval = new SqlParser.join_source_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        SqlParser.single_source_return single_source214 = null;

        SqlParser.join_op_return join_op215 = null;

        SqlParser.single_source_return single_source216 = null;

        SqlParser.join_constraint_return join_constraint217 = null;



        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:275:12: ( single_source ( join_op single_source ( join_constraint )? )* )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:275:14: single_source ( join_op single_source ( join_constraint )? )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_single_source_in_join_source2073);
            single_source214=single_source();

            state._fsp--;

            adaptor.addChild(root_0, single_source214.getTree());
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:275:28: ( join_op single_source ( join_constraint )? )*
            loop75:
            do {
                int alt75=2;
                alt75 = dfa75.predict(input);
                switch (alt75) {
            	case 1 :
            	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:275:29: join_op single_source ( join_constraint )?
            	    {
            	    pushFollow(FOLLOW_join_op_in_join_source2076);
            	    join_op215=join_op();

            	    state._fsp--;

            	    root_0 = (Object)adaptor.becomeRoot(join_op215.getTree(), root_0);
            	    pushFollow(FOLLOW_single_source_in_join_source2079);
            	    single_source216=single_source();

            	    state._fsp--;

            	    adaptor.addChild(root_0, single_source216.getTree());
            	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:275:52: ( join_constraint )?
            	    int alt74=2;
            	    alt74 = dfa74.predict(input);
            	    switch (alt74) {
            	        case 1 :
            	            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:275:53: join_constraint
            	            {
            	            pushFollow(FOLLOW_join_constraint_in_join_source2082);
            	            join_constraint217=join_constraint();

            	            state._fsp--;

            	            adaptor.addChild(root_0, join_constraint217.getTree());

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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:277:1: single_source : ( (database_name= id DOT )? table_name= ID ( ( AS )? table_alias= ID )? ( INDEXED BY index_name= id | NOT INDEXED )? -> ^( ALIAS ^( $table_name ( $database_name)? ) ( $table_alias)? ( ^( INDEXED ( NOT )? ( $index_name)? ) )? ) | LPAREN select_stmt RPAREN ( ( AS )? table_alias= ID )? -> ^( ALIAS select_stmt ( $table_alias)? ) | LPAREN join_source RPAREN );
    public final SqlParser.single_source_return single_source() throws RecognitionException {
        SqlParser.single_source_return retval = new SqlParser.single_source_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token table_name=null;
        Token table_alias=null;
        Token DOT218=null;
        Token AS219=null;
        Token INDEXED220=null;
        Token BY221=null;
        Token NOT222=null;
        Token INDEXED223=null;
        Token LPAREN224=null;
        Token RPAREN226=null;
        Token AS227=null;
        Token LPAREN228=null;
        Token RPAREN230=null;
        SqlParser.id_return database_name = null;

        SqlParser.id_return index_name = null;

        SqlParser.select_stmt_return select_stmt225 = null;

        SqlParser.join_source_return join_source229 = null;


        Object table_name_tree=null;
        Object table_alias_tree=null;
        Object DOT218_tree=null;
        Object AS219_tree=null;
        Object INDEXED220_tree=null;
        Object BY221_tree=null;
        Object NOT222_tree=null;
        Object INDEXED223_tree=null;
        Object LPAREN224_tree=null;
        Object RPAREN226_tree=null;
        Object AS227_tree=null;
        Object LPAREN228_tree=null;
        Object RPAREN230_tree=null;
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
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:278:3: ( (database_name= id DOT )? table_name= ID ( ( AS )? table_alias= ID )? ( INDEXED BY index_name= id | NOT INDEXED )? -> ^( ALIAS ^( $table_name ( $database_name)? ) ( $table_alias)? ( ^( INDEXED ( NOT )? ( $index_name)? ) )? ) | LPAREN select_stmt RPAREN ( ( AS )? table_alias= ID )? -> ^( ALIAS select_stmt ( $table_alias)? ) | LPAREN join_source RPAREN )
            int alt82=3;
            alt82 = dfa82.predict(input);
            switch (alt82) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:278:5: (database_name= id DOT )? table_name= ID ( ( AS )? table_alias= ID )? ( INDEXED BY index_name= id | NOT INDEXED )?
                    {
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:278:5: (database_name= id DOT )?
                    int alt76=2;
                    alt76 = dfa76.predict(input);
                    switch (alt76) {
                        case 1 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:278:6: database_name= id DOT
                            {
                            pushFollow(FOLLOW_id_in_single_source2099);
                            database_name=id();

                            state._fsp--;

                            stream_id.add(database_name.getTree());
                            DOT218=(Token)match(input,DOT,FOLLOW_DOT_in_single_source2101);  
                            stream_DOT.add(DOT218);


                            }
                            break;

                    }

                    table_name=(Token)match(input,ID,FOLLOW_ID_in_single_source2107);  
                    stream_ID.add(table_name);

                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:278:43: ( ( AS )? table_alias= ID )?
                    int alt78=2;
                    alt78 = dfa78.predict(input);
                    switch (alt78) {
                        case 1 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:278:44: ( AS )? table_alias= ID
                            {
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:278:44: ( AS )?
                            int alt77=2;
                            int LA77_0 = input.LA(1);

                            if ( (LA77_0==AS) ) {
                                alt77=1;
                            }
                            switch (alt77) {
                                case 1 :
                                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:278:45: AS
                                    {
                                    AS219=(Token)match(input,AS,FOLLOW_AS_in_single_source2111);  
                                    stream_AS.add(AS219);


                                    }
                                    break;

                            }

                            table_alias=(Token)match(input,ID,FOLLOW_ID_in_single_source2117);  
                            stream_ID.add(table_alias);


                            }
                            break;

                    }

                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:278:67: ( INDEXED BY index_name= id | NOT INDEXED )?
                    int alt79=3;
                    alt79 = dfa79.predict(input);
                    switch (alt79) {
                        case 1 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:278:68: INDEXED BY index_name= id
                            {
                            INDEXED220=(Token)match(input,INDEXED,FOLLOW_INDEXED_in_single_source2122);  
                            stream_INDEXED.add(INDEXED220);

                            BY221=(Token)match(input,BY,FOLLOW_BY_in_single_source2124);  
                            stream_BY.add(BY221);

                            pushFollow(FOLLOW_id_in_single_source2128);
                            index_name=id();

                            state._fsp--;

                            stream_id.add(index_name.getTree());

                            }
                            break;
                        case 2 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:278:95: NOT INDEXED
                            {
                            NOT222=(Token)match(input,NOT,FOLLOW_NOT_in_single_source2132);  
                            stream_NOT.add(NOT222);

                            INDEXED223=(Token)match(input,INDEXED,FOLLOW_INDEXED_in_single_source2134);  
                            stream_INDEXED.add(INDEXED223);


                            }
                            break;

                    }



                    // AST REWRITE
                    // elements: NOT, table_name, database_name, table_alias, INDEXED, index_name
                    // token labels: table_name, table_alias
                    // rule labels: index_name, database_name, retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleTokenStream stream_table_name=new RewriteRuleTokenStream(adaptor,"token table_name",table_name);
                    RewriteRuleTokenStream stream_table_alias=new RewriteRuleTokenStream(adaptor,"token table_alias",table_alias);
                    RewriteRuleSubtreeStream stream_index_name=new RewriteRuleSubtreeStream(adaptor,"rule index_name",index_name!=null?index_name.tree:null);
                    RewriteRuleSubtreeStream stream_database_name=new RewriteRuleSubtreeStream(adaptor,"rule database_name",database_name!=null?database_name.tree:null);
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 279:3: -> ^( ALIAS ^( $table_name ( $database_name)? ) ( $table_alias)? ( ^( INDEXED ( NOT )? ( $index_name)? ) )? )
                    {
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:279:6: ^( ALIAS ^( $table_name ( $database_name)? ) ( $table_alias)? ( ^( INDEXED ( NOT )? ( $index_name)? ) )? )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(ALIAS, "ALIAS"), root_1);

                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:279:14: ^( $table_name ( $database_name)? )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot(stream_table_name.nextNode(), root_2);

                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:279:28: ( $database_name)?
                        if ( stream_database_name.hasNext() ) {
                            adaptor.addChild(root_2, stream_database_name.nextTree());

                        }
                        stream_database_name.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:279:45: ( $table_alias)?
                        if ( stream_table_alias.hasNext() ) {
                            adaptor.addChild(root_1, stream_table_alias.nextNode());

                        }
                        stream_table_alias.reset();
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:279:59: ( ^( INDEXED ( NOT )? ( $index_name)? ) )?
                        if ( stream_NOT.hasNext()||stream_INDEXED.hasNext()||stream_index_name.hasNext() ) {
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:279:59: ^( INDEXED ( NOT )? ( $index_name)? )
                            {
                            Object root_2 = (Object)adaptor.nil();
                            root_2 = (Object)adaptor.becomeRoot(stream_INDEXED.nextNode(), root_2);

                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:279:69: ( NOT )?
                            if ( stream_NOT.hasNext() ) {
                                adaptor.addChild(root_2, stream_NOT.nextNode());

                            }
                            stream_NOT.reset();
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:279:74: ( $index_name)?
                            if ( stream_index_name.hasNext() ) {
                                adaptor.addChild(root_2, stream_index_name.nextTree());

                            }
                            stream_index_name.reset();

                            adaptor.addChild(root_1, root_2);
                            }

                        }
                        stream_NOT.reset();
                        stream_INDEXED.reset();
                        stream_index_name.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:280:5: LPAREN select_stmt RPAREN ( ( AS )? table_alias= ID )?
                    {
                    LPAREN224=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_single_source2175);  
                    stream_LPAREN.add(LPAREN224);

                    pushFollow(FOLLOW_select_stmt_in_single_source2177);
                    select_stmt225=select_stmt();

                    state._fsp--;

                    stream_select_stmt.add(select_stmt225.getTree());
                    RPAREN226=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_single_source2179);  
                    stream_RPAREN.add(RPAREN226);

                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:280:31: ( ( AS )? table_alias= ID )?
                    int alt81=2;
                    alt81 = dfa81.predict(input);
                    switch (alt81) {
                        case 1 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:280:32: ( AS )? table_alias= ID
                            {
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:280:32: ( AS )?
                            int alt80=2;
                            int LA80_0 = input.LA(1);

                            if ( (LA80_0==AS) ) {
                                alt80=1;
                            }
                            switch (alt80) {
                                case 1 :
                                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:280:33: AS
                                    {
                                    AS227=(Token)match(input,AS,FOLLOW_AS_in_single_source2183);  
                                    stream_AS.add(AS227);


                                    }
                                    break;

                            }

                            table_alias=(Token)match(input,ID,FOLLOW_ID_in_single_source2189);  
                            stream_ID.add(table_alias);


                            }
                            break;

                    }



                    // AST REWRITE
                    // elements: select_stmt, table_alias
                    // token labels: table_alias
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleTokenStream stream_table_alias=new RewriteRuleTokenStream(adaptor,"token table_alias",table_alias);
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 281:3: -> ^( ALIAS select_stmt ( $table_alias)? )
                    {
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:281:6: ^( ALIAS select_stmt ( $table_alias)? )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(ALIAS, "ALIAS"), root_1);

                        adaptor.addChild(root_1, stream_select_stmt.nextTree());
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:281:26: ( $table_alias)?
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
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:282:5: LPAREN join_source RPAREN
                    {
                    root_0 = (Object)adaptor.nil();

                    LPAREN228=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_single_source2211); 
                    pushFollow(FOLLOW_join_source_in_single_source2214);
                    join_source229=join_source();

                    state._fsp--;

                    adaptor.addChild(root_0, join_source229.getTree());
                    RPAREN230=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_single_source2216); 

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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:284:1: join_op : ( COMMA | ( NATURAL )? ( ( LEFT )? ( OUTER )? | INNER | CROSS ) JOIN );
    public final SqlParser.join_op_return join_op() throws RecognitionException {
        SqlParser.join_op_return retval = new SqlParser.join_op_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token COMMA231=null;
        Token NATURAL232=null;
        Token LEFT233=null;
        Token OUTER234=null;
        Token INNER235=null;
        Token CROSS236=null;
        Token JOIN237=null;

        Object COMMA231_tree=null;
        Object NATURAL232_tree=null;
        Object LEFT233_tree=null;
        Object OUTER234_tree=null;
        Object INNER235_tree=null;
        Object CROSS236_tree=null;
        Object JOIN237_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:285:3: ( COMMA | ( NATURAL )? ( ( LEFT )? ( OUTER )? | INNER | CROSS ) JOIN )
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
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:285:5: COMMA
                    {
                    root_0 = (Object)adaptor.nil();

                    COMMA231=(Token)match(input,COMMA,FOLLOW_COMMA_in_join_op2227); 
                    COMMA231_tree = (Object)adaptor.create(COMMA231);
                    adaptor.addChild(root_0, COMMA231_tree);


                    }
                    break;
                case 2 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:286:5: ( NATURAL )? ( ( LEFT )? ( OUTER )? | INNER | CROSS ) JOIN
                    {
                    root_0 = (Object)adaptor.nil();

                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:286:5: ( NATURAL )?
                    int alt83=2;
                    int LA83_0 = input.LA(1);

                    if ( (LA83_0==NATURAL) ) {
                        alt83=1;
                    }
                    switch (alt83) {
                        case 1 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:286:6: NATURAL
                            {
                            NATURAL232=(Token)match(input,NATURAL,FOLLOW_NATURAL_in_join_op2234); 
                            NATURAL232_tree = (Object)adaptor.create(NATURAL232);
                            adaptor.addChild(root_0, NATURAL232_tree);


                            }
                            break;

                    }

                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:286:16: ( ( LEFT )? ( OUTER )? | INNER | CROSS )
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
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:286:17: ( LEFT )? ( OUTER )?
                            {
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:286:17: ( LEFT )?
                            int alt84=2;
                            int LA84_0 = input.LA(1);

                            if ( (LA84_0==LEFT) ) {
                                alt84=1;
                            }
                            switch (alt84) {
                                case 1 :
                                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:286:18: LEFT
                                    {
                                    LEFT233=(Token)match(input,LEFT,FOLLOW_LEFT_in_join_op2240); 
                                    LEFT233_tree = (Object)adaptor.create(LEFT233);
                                    adaptor.addChild(root_0, LEFT233_tree);


                                    }
                                    break;

                            }

                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:286:25: ( OUTER )?
                            int alt85=2;
                            int LA85_0 = input.LA(1);

                            if ( (LA85_0==OUTER) ) {
                                alt85=1;
                            }
                            switch (alt85) {
                                case 1 :
                                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:286:26: OUTER
                                    {
                                    OUTER234=(Token)match(input,OUTER,FOLLOW_OUTER_in_join_op2245); 
                                    OUTER234_tree = (Object)adaptor.create(OUTER234);
                                    adaptor.addChild(root_0, OUTER234_tree);


                                    }
                                    break;

                            }


                            }
                            break;
                        case 2 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:286:36: INNER
                            {
                            INNER235=(Token)match(input,INNER,FOLLOW_INNER_in_join_op2251); 
                            INNER235_tree = (Object)adaptor.create(INNER235);
                            adaptor.addChild(root_0, INNER235_tree);


                            }
                            break;
                        case 3 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:286:44: CROSS
                            {
                            CROSS236=(Token)match(input,CROSS,FOLLOW_CROSS_in_join_op2255); 
                            CROSS236_tree = (Object)adaptor.create(CROSS236);
                            adaptor.addChild(root_0, CROSS236_tree);


                            }
                            break;

                    }

                    JOIN237=(Token)match(input,JOIN,FOLLOW_JOIN_in_join_op2258); 
                    JOIN237_tree = (Object)adaptor.create(JOIN237);
                    root_0 = (Object)adaptor.becomeRoot(JOIN237_tree, root_0);


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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:288:1: join_constraint : ( ON expr | USING LPAREN column_names+= id ( COMMA column_names+= id )* RPAREN -> ^( USING ( $column_names)+ ) );
    public final SqlParser.join_constraint_return join_constraint() throws RecognitionException {
        SqlParser.join_constraint_return retval = new SqlParser.join_constraint_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ON238=null;
        Token USING240=null;
        Token LPAREN241=null;
        Token COMMA242=null;
        Token RPAREN243=null;
        List list_column_names=null;
        SqlParser.expr_return expr239 = null;

        SqlParser.id_return column_names = null;
         column_names = null;
        Object ON238_tree=null;
        Object USING240_tree=null;
        Object LPAREN241_tree=null;
        Object COMMA242_tree=null;
        Object RPAREN243_tree=null;
        RewriteRuleTokenStream stream_RPAREN=new RewriteRuleTokenStream(adaptor,"token RPAREN");
        RewriteRuleTokenStream stream_USING=new RewriteRuleTokenStream(adaptor,"token USING");
        RewriteRuleTokenStream stream_COMMA=new RewriteRuleTokenStream(adaptor,"token COMMA");
        RewriteRuleTokenStream stream_LPAREN=new RewriteRuleTokenStream(adaptor,"token LPAREN");
        RewriteRuleSubtreeStream stream_id=new RewriteRuleSubtreeStream(adaptor,"rule id");
        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:289:3: ( ON expr | USING LPAREN column_names+= id ( COMMA column_names+= id )* RPAREN -> ^( USING ( $column_names)+ ) )
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
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:289:5: ON expr
                    {
                    root_0 = (Object)adaptor.nil();

                    ON238=(Token)match(input,ON,FOLLOW_ON_in_join_constraint2269); 
                    ON238_tree = (Object)adaptor.create(ON238);
                    root_0 = (Object)adaptor.becomeRoot(ON238_tree, root_0);

                    pushFollow(FOLLOW_expr_in_join_constraint2272);
                    expr239=expr();

                    state._fsp--;

                    adaptor.addChild(root_0, expr239.getTree());

                    }
                    break;
                case 2 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:290:5: USING LPAREN column_names+= id ( COMMA column_names+= id )* RPAREN
                    {
                    USING240=(Token)match(input,USING,FOLLOW_USING_in_join_constraint2278);  
                    stream_USING.add(USING240);

                    LPAREN241=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_join_constraint2280);  
                    stream_LPAREN.add(LPAREN241);

                    pushFollow(FOLLOW_id_in_join_constraint2284);
                    column_names=id();

                    state._fsp--;

                    stream_id.add(column_names.getTree());
                    if (list_column_names==null) list_column_names=new ArrayList();
                    list_column_names.add(column_names.getTree());

                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:290:35: ( COMMA column_names+= id )*
                    loop88:
                    do {
                        int alt88=2;
                        int LA88_0 = input.LA(1);

                        if ( (LA88_0==COMMA) ) {
                            alt88=1;
                        }


                        switch (alt88) {
                    	case 1 :
                    	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:290:36: COMMA column_names+= id
                    	    {
                    	    COMMA242=(Token)match(input,COMMA,FOLLOW_COMMA_in_join_constraint2287);  
                    	    stream_COMMA.add(COMMA242);

                    	    pushFollow(FOLLOW_id_in_join_constraint2291);
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

                    RPAREN243=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_join_constraint2295);  
                    stream_RPAREN.add(RPAREN243);



                    // AST REWRITE
                    // elements: column_names, USING
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: column_names
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                    RewriteRuleSubtreeStream stream_column_names=new RewriteRuleSubtreeStream(adaptor,"token column_names",list_column_names);
                    root_0 = (Object)adaptor.nil();
                    // 290:68: -> ^( USING ( $column_names)+ )
                    {
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:290:71: ^( USING ( $column_names)+ )
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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:293:1: insert_stmt : ( INSERT ( operation_conflict_clause )? | REPLACE ) INTO (database_name= id DOT )? table_name= id ( ( LPAREN column_names+= id ( COMMA column_names+= id )* RPAREN )? ( VALUES LPAREN values+= expr ( COMMA values+= expr )* RPAREN | select_stmt ) | DEFAULT VALUES ) ;
    public final SqlParser.insert_stmt_return insert_stmt() throws RecognitionException {
        SqlParser.insert_stmt_return retval = new SqlParser.insert_stmt_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token INSERT244=null;
        Token REPLACE246=null;
        Token INTO247=null;
        Token DOT248=null;
        Token LPAREN249=null;
        Token COMMA250=null;
        Token RPAREN251=null;
        Token VALUES252=null;
        Token LPAREN253=null;
        Token COMMA254=null;
        Token RPAREN255=null;
        Token DEFAULT257=null;
        Token VALUES258=null;
        List list_column_names=null;
        List list_values=null;
        SqlParser.id_return database_name = null;

        SqlParser.id_return table_name = null;

        SqlParser.operation_conflict_clause_return operation_conflict_clause245 = null;

        SqlParser.select_stmt_return select_stmt256 = null;

        SqlParser.id_return column_names = null;
         column_names = null;
        SqlParser.expr_return values = null;
         values = null;
        Object INSERT244_tree=null;
        Object REPLACE246_tree=null;
        Object INTO247_tree=null;
        Object DOT248_tree=null;
        Object LPAREN249_tree=null;
        Object COMMA250_tree=null;
        Object RPAREN251_tree=null;
        Object VALUES252_tree=null;
        Object LPAREN253_tree=null;
        Object COMMA254_tree=null;
        Object RPAREN255_tree=null;
        Object DEFAULT257_tree=null;
        Object VALUES258_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:293:12: ( ( INSERT ( operation_conflict_clause )? | REPLACE ) INTO (database_name= id DOT )? table_name= id ( ( LPAREN column_names+= id ( COMMA column_names+= id )* RPAREN )? ( VALUES LPAREN values+= expr ( COMMA values+= expr )* RPAREN | select_stmt ) | DEFAULT VALUES ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:293:14: ( INSERT ( operation_conflict_clause )? | REPLACE ) INTO (database_name= id DOT )? table_name= id ( ( LPAREN column_names+= id ( COMMA column_names+= id )* RPAREN )? ( VALUES LPAREN values+= expr ( COMMA values+= expr )* RPAREN | select_stmt ) | DEFAULT VALUES )
            {
            root_0 = (Object)adaptor.nil();

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:293:14: ( INSERT ( operation_conflict_clause )? | REPLACE )
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
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:293:15: INSERT ( operation_conflict_clause )?
                    {
                    INSERT244=(Token)match(input,INSERT,FOLLOW_INSERT_in_insert_stmt2314); 
                    INSERT244_tree = (Object)adaptor.create(INSERT244);
                    adaptor.addChild(root_0, INSERT244_tree);

                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:293:22: ( operation_conflict_clause )?
                    int alt90=2;
                    int LA90_0 = input.LA(1);

                    if ( (LA90_0==OR) ) {
                        alt90=1;
                    }
                    switch (alt90) {
                        case 1 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:293:23: operation_conflict_clause
                            {
                            pushFollow(FOLLOW_operation_conflict_clause_in_insert_stmt2317);
                            operation_conflict_clause245=operation_conflict_clause();

                            state._fsp--;

                            adaptor.addChild(root_0, operation_conflict_clause245.getTree());

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:293:53: REPLACE
                    {
                    REPLACE246=(Token)match(input,REPLACE,FOLLOW_REPLACE_in_insert_stmt2323); 
                    REPLACE246_tree = (Object)adaptor.create(REPLACE246);
                    adaptor.addChild(root_0, REPLACE246_tree);


                    }
                    break;

            }

            INTO247=(Token)match(input,INTO,FOLLOW_INTO_in_insert_stmt2326); 
            INTO247_tree = (Object)adaptor.create(INTO247);
            adaptor.addChild(root_0, INTO247_tree);

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:293:67: (database_name= id DOT )?
            int alt92=2;
            alt92 = dfa92.predict(input);
            switch (alt92) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:293:68: database_name= id DOT
                    {
                    pushFollow(FOLLOW_id_in_insert_stmt2331);
                    database_name=id();

                    state._fsp--;

                    adaptor.addChild(root_0, database_name.getTree());
                    DOT248=(Token)match(input,DOT,FOLLOW_DOT_in_insert_stmt2333); 
                    DOT248_tree = (Object)adaptor.create(DOT248);
                    adaptor.addChild(root_0, DOT248_tree);


                    }
                    break;

            }

            pushFollow(FOLLOW_id_in_insert_stmt2339);
            table_name=id();

            state._fsp--;

            adaptor.addChild(root_0, table_name.getTree());
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:294:3: ( ( LPAREN column_names+= id ( COMMA column_names+= id )* RPAREN )? ( VALUES LPAREN values+= expr ( COMMA values+= expr )* RPAREN | select_stmt ) | DEFAULT VALUES )
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
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:294:5: ( LPAREN column_names+= id ( COMMA column_names+= id )* RPAREN )? ( VALUES LPAREN values+= expr ( COMMA values+= expr )* RPAREN | select_stmt )
                    {
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:294:5: ( LPAREN column_names+= id ( COMMA column_names+= id )* RPAREN )?
                    int alt94=2;
                    int LA94_0 = input.LA(1);

                    if ( (LA94_0==LPAREN) ) {
                        alt94=1;
                    }
                    switch (alt94) {
                        case 1 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:294:6: LPAREN column_names+= id ( COMMA column_names+= id )* RPAREN
                            {
                            LPAREN249=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_insert_stmt2346); 
                            LPAREN249_tree = (Object)adaptor.create(LPAREN249);
                            adaptor.addChild(root_0, LPAREN249_tree);

                            pushFollow(FOLLOW_id_in_insert_stmt2350);
                            column_names=id();

                            state._fsp--;

                            adaptor.addChild(root_0, column_names.getTree());
                            if (list_column_names==null) list_column_names=new ArrayList();
                            list_column_names.add(column_names.getTree());

                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:294:30: ( COMMA column_names+= id )*
                            loop93:
                            do {
                                int alt93=2;
                                int LA93_0 = input.LA(1);

                                if ( (LA93_0==COMMA) ) {
                                    alt93=1;
                                }


                                switch (alt93) {
                            	case 1 :
                            	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:294:31: COMMA column_names+= id
                            	    {
                            	    COMMA250=(Token)match(input,COMMA,FOLLOW_COMMA_in_insert_stmt2353); 
                            	    COMMA250_tree = (Object)adaptor.create(COMMA250);
                            	    adaptor.addChild(root_0, COMMA250_tree);

                            	    pushFollow(FOLLOW_id_in_insert_stmt2357);
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

                            RPAREN251=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_insert_stmt2361); 
                            RPAREN251_tree = (Object)adaptor.create(RPAREN251);
                            adaptor.addChild(root_0, RPAREN251_tree);


                            }
                            break;

                    }

                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:295:5: ( VALUES LPAREN values+= expr ( COMMA values+= expr )* RPAREN | select_stmt )
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
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:295:6: VALUES LPAREN values+= expr ( COMMA values+= expr )* RPAREN
                            {
                            VALUES252=(Token)match(input,VALUES,FOLLOW_VALUES_in_insert_stmt2370); 
                            VALUES252_tree = (Object)adaptor.create(VALUES252);
                            adaptor.addChild(root_0, VALUES252_tree);

                            LPAREN253=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_insert_stmt2372); 
                            LPAREN253_tree = (Object)adaptor.create(LPAREN253);
                            adaptor.addChild(root_0, LPAREN253_tree);

                            pushFollow(FOLLOW_expr_in_insert_stmt2376);
                            values=expr();

                            state._fsp--;

                            adaptor.addChild(root_0, values.getTree());
                            if (list_values==null) list_values=new ArrayList();
                            list_values.add(values.getTree());

                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:295:33: ( COMMA values+= expr )*
                            loop95:
                            do {
                                int alt95=2;
                                int LA95_0 = input.LA(1);

                                if ( (LA95_0==COMMA) ) {
                                    alt95=1;
                                }


                                switch (alt95) {
                            	case 1 :
                            	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:295:34: COMMA values+= expr
                            	    {
                            	    COMMA254=(Token)match(input,COMMA,FOLLOW_COMMA_in_insert_stmt2379); 
                            	    COMMA254_tree = (Object)adaptor.create(COMMA254);
                            	    adaptor.addChild(root_0, COMMA254_tree);

                            	    pushFollow(FOLLOW_expr_in_insert_stmt2383);
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

                            RPAREN255=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_insert_stmt2387); 
                            RPAREN255_tree = (Object)adaptor.create(RPAREN255);
                            adaptor.addChild(root_0, RPAREN255_tree);


                            }
                            break;
                        case 2 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:295:64: select_stmt
                            {
                            pushFollow(FOLLOW_select_stmt_in_insert_stmt2391);
                            select_stmt256=select_stmt();

                            state._fsp--;

                            adaptor.addChild(root_0, select_stmt256.getTree());

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:296:5: DEFAULT VALUES
                    {
                    DEFAULT257=(Token)match(input,DEFAULT,FOLLOW_DEFAULT_in_insert_stmt2398); 
                    DEFAULT257_tree = (Object)adaptor.create(DEFAULT257);
                    adaptor.addChild(root_0, DEFAULT257_tree);

                    VALUES258=(Token)match(input,VALUES,FOLLOW_VALUES_in_insert_stmt2400); 
                    VALUES258_tree = (Object)adaptor.create(VALUES258);
                    adaptor.addChild(root_0, VALUES258_tree);


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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:299:1: update_stmt : UPDATE ( operation_conflict_clause )? qualified_table_name SET values+= update_set ( COMMA values+= update_set )* ( WHERE expr )? ( operation_limited_clause )? ;
    public final SqlParser.update_stmt_return update_stmt() throws RecognitionException {
        SqlParser.update_stmt_return retval = new SqlParser.update_stmt_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token UPDATE259=null;
        Token SET262=null;
        Token COMMA263=null;
        Token WHERE264=null;
        List list_values=null;
        SqlParser.operation_conflict_clause_return operation_conflict_clause260 = null;

        SqlParser.qualified_table_name_return qualified_table_name261 = null;

        SqlParser.expr_return expr265 = null;

        SqlParser.operation_limited_clause_return operation_limited_clause266 = null;

        SqlParser.update_set_return values = null;
         values = null;
        Object UPDATE259_tree=null;
        Object SET262_tree=null;
        Object COMMA263_tree=null;
        Object WHERE264_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:299:12: ( UPDATE ( operation_conflict_clause )? qualified_table_name SET values+= update_set ( COMMA values+= update_set )* ( WHERE expr )? ( operation_limited_clause )? )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:299:14: UPDATE ( operation_conflict_clause )? qualified_table_name SET values+= update_set ( COMMA values+= update_set )* ( WHERE expr )? ( operation_limited_clause )?
            {
            root_0 = (Object)adaptor.nil();

            UPDATE259=(Token)match(input,UPDATE,FOLLOW_UPDATE_in_update_stmt2410); 
            UPDATE259_tree = (Object)adaptor.create(UPDATE259);
            adaptor.addChild(root_0, UPDATE259_tree);

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:299:21: ( operation_conflict_clause )?
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
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:299:22: operation_conflict_clause
                    {
                    pushFollow(FOLLOW_operation_conflict_clause_in_update_stmt2413);
                    operation_conflict_clause260=operation_conflict_clause();

                    state._fsp--;

                    adaptor.addChild(root_0, operation_conflict_clause260.getTree());

                    }
                    break;

            }

            pushFollow(FOLLOW_qualified_table_name_in_update_stmt2417);
            qualified_table_name261=qualified_table_name();

            state._fsp--;

            adaptor.addChild(root_0, qualified_table_name261.getTree());
            SET262=(Token)match(input,SET,FOLLOW_SET_in_update_stmt2421); 
            SET262_tree = (Object)adaptor.create(SET262);
            adaptor.addChild(root_0, SET262_tree);

            pushFollow(FOLLOW_update_set_in_update_stmt2425);
            values=update_set();

            state._fsp--;

            adaptor.addChild(root_0, values.getTree());
            if (list_values==null) list_values=new ArrayList();
            list_values.add(values.getTree());

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:300:26: ( COMMA values+= update_set )*
            loop99:
            do {
                int alt99=2;
                int LA99_0 = input.LA(1);

                if ( (LA99_0==COMMA) ) {
                    alt99=1;
                }


                switch (alt99) {
            	case 1 :
            	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:300:27: COMMA values+= update_set
            	    {
            	    COMMA263=(Token)match(input,COMMA,FOLLOW_COMMA_in_update_stmt2428); 
            	    COMMA263_tree = (Object)adaptor.create(COMMA263);
            	    adaptor.addChild(root_0, COMMA263_tree);

            	    pushFollow(FOLLOW_update_set_in_update_stmt2432);
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

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:300:54: ( WHERE expr )?
            int alt100=2;
            int LA100_0 = input.LA(1);

            if ( (LA100_0==WHERE) ) {
                alt100=1;
            }
            switch (alt100) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:300:55: WHERE expr
                    {
                    WHERE264=(Token)match(input,WHERE,FOLLOW_WHERE_in_update_stmt2437); 
                    WHERE264_tree = (Object)adaptor.create(WHERE264);
                    adaptor.addChild(root_0, WHERE264_tree);

                    pushFollow(FOLLOW_expr_in_update_stmt2439);
                    expr265=expr();

                    state._fsp--;

                    adaptor.addChild(root_0, expr265.getTree());

                    }
                    break;

            }

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:300:68: ( operation_limited_clause )?
            int alt101=2;
            int LA101_0 = input.LA(1);

            if ( ((LA101_0>=ORDER && LA101_0<=LIMIT)) ) {
                alt101=1;
            }
            switch (alt101) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:300:69: operation_limited_clause
                    {
                    pushFollow(FOLLOW_operation_limited_clause_in_update_stmt2444);
                    operation_limited_clause266=operation_limited_clause();

                    state._fsp--;

                    adaptor.addChild(root_0, operation_limited_clause266.getTree());

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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:302:1: update_set : column_name= id EQUALS expr ;
    public final SqlParser.update_set_return update_set() throws RecognitionException {
        SqlParser.update_set_return retval = new SqlParser.update_set_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token EQUALS267=null;
        SqlParser.id_return column_name = null;

        SqlParser.expr_return expr268 = null;


        Object EQUALS267_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:302:11: (column_name= id EQUALS expr )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:302:13: column_name= id EQUALS expr
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_id_in_update_set2455);
            column_name=id();

            state._fsp--;

            adaptor.addChild(root_0, column_name.getTree());
            EQUALS267=(Token)match(input,EQUALS,FOLLOW_EQUALS_in_update_set2457); 
            EQUALS267_tree = (Object)adaptor.create(EQUALS267);
            adaptor.addChild(root_0, EQUALS267_tree);

            pushFollow(FOLLOW_expr_in_update_set2459);
            expr268=expr();

            state._fsp--;

            adaptor.addChild(root_0, expr268.getTree());

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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:305:1: delete_stmt : DELETE FROM qualified_table_name ( WHERE expr )? ( operation_limited_clause )? ;
    public final SqlParser.delete_stmt_return delete_stmt() throws RecognitionException {
        SqlParser.delete_stmt_return retval = new SqlParser.delete_stmt_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token DELETE269=null;
        Token FROM270=null;
        Token WHERE272=null;
        SqlParser.qualified_table_name_return qualified_table_name271 = null;

        SqlParser.expr_return expr273 = null;

        SqlParser.operation_limited_clause_return operation_limited_clause274 = null;


        Object DELETE269_tree=null;
        Object FROM270_tree=null;
        Object WHERE272_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:305:12: ( DELETE FROM qualified_table_name ( WHERE expr )? ( operation_limited_clause )? )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:305:14: DELETE FROM qualified_table_name ( WHERE expr )? ( operation_limited_clause )?
            {
            root_0 = (Object)adaptor.nil();

            DELETE269=(Token)match(input,DELETE,FOLLOW_DELETE_in_delete_stmt2467); 
            DELETE269_tree = (Object)adaptor.create(DELETE269);
            adaptor.addChild(root_0, DELETE269_tree);

            FROM270=(Token)match(input,FROM,FOLLOW_FROM_in_delete_stmt2469); 
            FROM270_tree = (Object)adaptor.create(FROM270);
            adaptor.addChild(root_0, FROM270_tree);

            pushFollow(FOLLOW_qualified_table_name_in_delete_stmt2471);
            qualified_table_name271=qualified_table_name();

            state._fsp--;

            adaptor.addChild(root_0, qualified_table_name271.getTree());
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:305:47: ( WHERE expr )?
            int alt102=2;
            int LA102_0 = input.LA(1);

            if ( (LA102_0==WHERE) ) {
                alt102=1;
            }
            switch (alt102) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:305:48: WHERE expr
                    {
                    WHERE272=(Token)match(input,WHERE,FOLLOW_WHERE_in_delete_stmt2474); 
                    WHERE272_tree = (Object)adaptor.create(WHERE272);
                    adaptor.addChild(root_0, WHERE272_tree);

                    pushFollow(FOLLOW_expr_in_delete_stmt2476);
                    expr273=expr();

                    state._fsp--;

                    adaptor.addChild(root_0, expr273.getTree());

                    }
                    break;

            }

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:305:61: ( operation_limited_clause )?
            int alt103=2;
            int LA103_0 = input.LA(1);

            if ( ((LA103_0>=ORDER && LA103_0<=LIMIT)) ) {
                alt103=1;
            }
            switch (alt103) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:305:62: operation_limited_clause
                    {
                    pushFollow(FOLLOW_operation_limited_clause_in_delete_stmt2481);
                    operation_limited_clause274=operation_limited_clause();

                    state._fsp--;

                    adaptor.addChild(root_0, operation_limited_clause274.getTree());

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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:308:1: begin_stmt : BEGIN ( DEFERRED | IMMEDIATE | EXCLUSIVE )? ( TRANSACTION )? ;
    public final SqlParser.begin_stmt_return begin_stmt() throws RecognitionException {
        SqlParser.begin_stmt_return retval = new SqlParser.begin_stmt_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token BEGIN275=null;
        Token set276=null;
        Token TRANSACTION277=null;

        Object BEGIN275_tree=null;
        Object set276_tree=null;
        Object TRANSACTION277_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:308:11: ( BEGIN ( DEFERRED | IMMEDIATE | EXCLUSIVE )? ( TRANSACTION )? )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:308:13: BEGIN ( DEFERRED | IMMEDIATE | EXCLUSIVE )? ( TRANSACTION )?
            {
            root_0 = (Object)adaptor.nil();

            BEGIN275=(Token)match(input,BEGIN,FOLLOW_BEGIN_in_begin_stmt2491); 
            BEGIN275_tree = (Object)adaptor.create(BEGIN275);
            adaptor.addChild(root_0, BEGIN275_tree);

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:308:19: ( DEFERRED | IMMEDIATE | EXCLUSIVE )?
            int alt104=2;
            int LA104_0 = input.LA(1);

            if ( ((LA104_0>=DEFERRED && LA104_0<=EXCLUSIVE)) ) {
                alt104=1;
            }
            switch (alt104) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:
                    {
                    set276=(Token)input.LT(1);
                    if ( (input.LA(1)>=DEFERRED && input.LA(1)<=EXCLUSIVE) ) {
                        input.consume();
                        adaptor.addChild(root_0, (Object)adaptor.create(set276));
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }


                    }
                    break;

            }

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:308:55: ( TRANSACTION )?
            int alt105=2;
            int LA105_0 = input.LA(1);

            if ( (LA105_0==TRANSACTION) ) {
                alt105=1;
            }
            switch (alt105) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:308:56: TRANSACTION
                    {
                    TRANSACTION277=(Token)match(input,TRANSACTION,FOLLOW_TRANSACTION_in_begin_stmt2507); 
                    TRANSACTION277_tree = (Object)adaptor.create(TRANSACTION277);
                    adaptor.addChild(root_0, TRANSACTION277_tree);


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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:311:1: commit_stmt : ( COMMIT | END ) ( TRANSACTION )? ;
    public final SqlParser.commit_stmt_return commit_stmt() throws RecognitionException {
        SqlParser.commit_stmt_return retval = new SqlParser.commit_stmt_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set278=null;
        Token TRANSACTION279=null;

        Object set278_tree=null;
        Object TRANSACTION279_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:311:12: ( ( COMMIT | END ) ( TRANSACTION )? )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:311:14: ( COMMIT | END ) ( TRANSACTION )?
            {
            root_0 = (Object)adaptor.nil();

            set278=(Token)input.LT(1);
            if ( input.LA(1)==END||input.LA(1)==COMMIT ) {
                input.consume();
                adaptor.addChild(root_0, (Object)adaptor.create(set278));
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:311:29: ( TRANSACTION )?
            int alt106=2;
            int LA106_0 = input.LA(1);

            if ( (LA106_0==TRANSACTION) ) {
                alt106=1;
            }
            switch (alt106) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:311:30: TRANSACTION
                    {
                    TRANSACTION279=(Token)match(input,TRANSACTION,FOLLOW_TRANSACTION_in_commit_stmt2526); 
                    TRANSACTION279_tree = (Object)adaptor.create(TRANSACTION279);
                    adaptor.addChild(root_0, TRANSACTION279_tree);


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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:314:1: rollback_stmt : ROLLBACK ( TRANSACTION )? ( TO ( SAVEPOINT )? savepoint_name= id )? ;
    public final SqlParser.rollback_stmt_return rollback_stmt() throws RecognitionException {
        SqlParser.rollback_stmt_return retval = new SqlParser.rollback_stmt_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ROLLBACK280=null;
        Token TRANSACTION281=null;
        Token TO282=null;
        Token SAVEPOINT283=null;
        SqlParser.id_return savepoint_name = null;


        Object ROLLBACK280_tree=null;
        Object TRANSACTION281_tree=null;
        Object TO282_tree=null;
        Object SAVEPOINT283_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:314:14: ( ROLLBACK ( TRANSACTION )? ( TO ( SAVEPOINT )? savepoint_name= id )? )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:314:16: ROLLBACK ( TRANSACTION )? ( TO ( SAVEPOINT )? savepoint_name= id )?
            {
            root_0 = (Object)adaptor.nil();

            ROLLBACK280=(Token)match(input,ROLLBACK,FOLLOW_ROLLBACK_in_rollback_stmt2536); 
            ROLLBACK280_tree = (Object)adaptor.create(ROLLBACK280);
            adaptor.addChild(root_0, ROLLBACK280_tree);

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:314:25: ( TRANSACTION )?
            int alt107=2;
            int LA107_0 = input.LA(1);

            if ( (LA107_0==TRANSACTION) ) {
                alt107=1;
            }
            switch (alt107) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:314:26: TRANSACTION
                    {
                    TRANSACTION281=(Token)match(input,TRANSACTION,FOLLOW_TRANSACTION_in_rollback_stmt2539); 
                    TRANSACTION281_tree = (Object)adaptor.create(TRANSACTION281);
                    adaptor.addChild(root_0, TRANSACTION281_tree);


                    }
                    break;

            }

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:314:40: ( TO ( SAVEPOINT )? savepoint_name= id )?
            int alt109=2;
            int LA109_0 = input.LA(1);

            if ( (LA109_0==TO) ) {
                alt109=1;
            }
            switch (alt109) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:314:41: TO ( SAVEPOINT )? savepoint_name= id
                    {
                    TO282=(Token)match(input,TO,FOLLOW_TO_in_rollback_stmt2544); 
                    TO282_tree = (Object)adaptor.create(TO282);
                    adaptor.addChild(root_0, TO282_tree);

                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:314:44: ( SAVEPOINT )?
                    int alt108=2;
                    int LA108_0 = input.LA(1);

                    if ( (LA108_0==SAVEPOINT) ) {
                        int LA108_1 = input.LA(2);

                        if ( ((LA108_1>=EXPLAIN && LA108_1<=PLAN)||(LA108_1>=INDEXED && LA108_1<=BY)||(LA108_1>=OR && LA108_1<=ESCAPE)||(LA108_1>=IS && LA108_1<=BETWEEN)||(LA108_1>=COLLATE && LA108_1<=THEN)||(LA108_1>=CURRENT_TIME && LA108_1<=CURRENT_TIMESTAMP)||(LA108_1>=RAISE && LA108_1<=FAIL)||(LA108_1>=PRAGMA && LA108_1<=FOR)||(LA108_1>=UNIQUE && LA108_1<=ROW)) ) {
                            alt108=1;
                        }
                    }
                    switch (alt108) {
                        case 1 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:314:45: SAVEPOINT
                            {
                            SAVEPOINT283=(Token)match(input,SAVEPOINT,FOLLOW_SAVEPOINT_in_rollback_stmt2547); 
                            SAVEPOINT283_tree = (Object)adaptor.create(SAVEPOINT283);
                            adaptor.addChild(root_0, SAVEPOINT283_tree);


                            }
                            break;

                    }

                    pushFollow(FOLLOW_id_in_rollback_stmt2553);
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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:317:1: savepoint_stmt : SAVEPOINT savepoint_name= id ;
    public final SqlParser.savepoint_stmt_return savepoint_stmt() throws RecognitionException {
        SqlParser.savepoint_stmt_return retval = new SqlParser.savepoint_stmt_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token SAVEPOINT284=null;
        SqlParser.id_return savepoint_name = null;


        Object SAVEPOINT284_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:317:15: ( SAVEPOINT savepoint_name= id )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:317:17: SAVEPOINT savepoint_name= id
            {
            root_0 = (Object)adaptor.nil();

            SAVEPOINT284=(Token)match(input,SAVEPOINT,FOLLOW_SAVEPOINT_in_savepoint_stmt2563); 
            SAVEPOINT284_tree = (Object)adaptor.create(SAVEPOINT284);
            adaptor.addChild(root_0, SAVEPOINT284_tree);

            pushFollow(FOLLOW_id_in_savepoint_stmt2567);
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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:320:1: release_stmt : RELEASE ( SAVEPOINT )? savepoint_name= id ;
    public final SqlParser.release_stmt_return release_stmt() throws RecognitionException {
        SqlParser.release_stmt_return retval = new SqlParser.release_stmt_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token RELEASE285=null;
        Token SAVEPOINT286=null;
        SqlParser.id_return savepoint_name = null;


        Object RELEASE285_tree=null;
        Object SAVEPOINT286_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:320:13: ( RELEASE ( SAVEPOINT )? savepoint_name= id )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:320:15: RELEASE ( SAVEPOINT )? savepoint_name= id
            {
            root_0 = (Object)adaptor.nil();

            RELEASE285=(Token)match(input,RELEASE,FOLLOW_RELEASE_in_release_stmt2575); 
            RELEASE285_tree = (Object)adaptor.create(RELEASE285);
            adaptor.addChild(root_0, RELEASE285_tree);

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:320:23: ( SAVEPOINT )?
            int alt110=2;
            int LA110_0 = input.LA(1);

            if ( (LA110_0==SAVEPOINT) ) {
                int LA110_1 = input.LA(2);

                if ( ((LA110_1>=EXPLAIN && LA110_1<=PLAN)||(LA110_1>=INDEXED && LA110_1<=BY)||(LA110_1>=OR && LA110_1<=ESCAPE)||(LA110_1>=IS && LA110_1<=BETWEEN)||(LA110_1>=COLLATE && LA110_1<=THEN)||(LA110_1>=CURRENT_TIME && LA110_1<=CURRENT_TIMESTAMP)||(LA110_1>=RAISE && LA110_1<=FAIL)||(LA110_1>=PRAGMA && LA110_1<=FOR)||(LA110_1>=UNIQUE && LA110_1<=ROW)) ) {
                    alt110=1;
                }
            }
            switch (alt110) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:320:24: SAVEPOINT
                    {
                    SAVEPOINT286=(Token)match(input,SAVEPOINT,FOLLOW_SAVEPOINT_in_release_stmt2578); 
                    SAVEPOINT286_tree = (Object)adaptor.create(SAVEPOINT286);
                    adaptor.addChild(root_0, SAVEPOINT286_tree);


                    }
                    break;

            }

            pushFollow(FOLLOW_id_in_release_stmt2584);
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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:327:1: table_conflict_clause : ON CONFLICT ( ROLLBACK | ABORT | FAIL | IGNORE | REPLACE ) ;
    public final SqlParser.table_conflict_clause_return table_conflict_clause() throws RecognitionException {
        SqlParser.table_conflict_clause_return retval = new SqlParser.table_conflict_clause_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ON287=null;
        Token CONFLICT288=null;
        Token set289=null;

        Object ON287_tree=null;
        Object CONFLICT288_tree=null;
        Object set289_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:327:22: ( ON CONFLICT ( ROLLBACK | ABORT | FAIL | IGNORE | REPLACE ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:327:24: ON CONFLICT ( ROLLBACK | ABORT | FAIL | IGNORE | REPLACE )
            {
            root_0 = (Object)adaptor.nil();

            ON287=(Token)match(input,ON,FOLLOW_ON_in_table_conflict_clause2596); 
            CONFLICT288=(Token)match(input,CONFLICT,FOLLOW_CONFLICT_in_table_conflict_clause2599); 
            CONFLICT288_tree = (Object)adaptor.create(CONFLICT288);
            root_0 = (Object)adaptor.becomeRoot(CONFLICT288_tree, root_0);

            set289=(Token)input.LT(1);
            if ( (input.LA(1)>=IGNORE && input.LA(1)<=FAIL)||input.LA(1)==REPLACE ) {
                input.consume();
                adaptor.addChild(root_0, (Object)adaptor.create(set289));
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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:331:1: create_virtual_table_stmt : CREATE VIRTUAL TABLE (database_name= id DOT )? table_name= id USING module_name= id ( LPAREN column_def ( COMMA column_def )* RPAREN )? ;
    public final SqlParser.create_virtual_table_stmt_return create_virtual_table_stmt() throws RecognitionException {
        SqlParser.create_virtual_table_stmt_return retval = new SqlParser.create_virtual_table_stmt_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token CREATE290=null;
        Token VIRTUAL291=null;
        Token TABLE292=null;
        Token DOT293=null;
        Token USING294=null;
        Token LPAREN295=null;
        Token COMMA297=null;
        Token RPAREN299=null;
        SqlParser.id_return database_name = null;

        SqlParser.id_return table_name = null;

        SqlParser.id_return module_name = null;

        SqlParser.column_def_return column_def296 = null;

        SqlParser.column_def_return column_def298 = null;


        Object CREATE290_tree=null;
        Object VIRTUAL291_tree=null;
        Object TABLE292_tree=null;
        Object DOT293_tree=null;
        Object USING294_tree=null;
        Object LPAREN295_tree=null;
        Object COMMA297_tree=null;
        Object RPAREN299_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:331:26: ( CREATE VIRTUAL TABLE (database_name= id DOT )? table_name= id USING module_name= id ( LPAREN column_def ( COMMA column_def )* RPAREN )? )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:331:28: CREATE VIRTUAL TABLE (database_name= id DOT )? table_name= id USING module_name= id ( LPAREN column_def ( COMMA column_def )* RPAREN )?
            {
            root_0 = (Object)adaptor.nil();

            CREATE290=(Token)match(input,CREATE,FOLLOW_CREATE_in_create_virtual_table_stmt2629); 
            CREATE290_tree = (Object)adaptor.create(CREATE290);
            adaptor.addChild(root_0, CREATE290_tree);

            VIRTUAL291=(Token)match(input,VIRTUAL,FOLLOW_VIRTUAL_in_create_virtual_table_stmt2631); 
            VIRTUAL291_tree = (Object)adaptor.create(VIRTUAL291);
            adaptor.addChild(root_0, VIRTUAL291_tree);

            TABLE292=(Token)match(input,TABLE,FOLLOW_TABLE_in_create_virtual_table_stmt2633); 
            TABLE292_tree = (Object)adaptor.create(TABLE292);
            adaptor.addChild(root_0, TABLE292_tree);

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:331:49: (database_name= id DOT )?
            int alt111=2;
            int LA111_0 = input.LA(1);

            if ( (LA111_0==ID) ) {
                int LA111_1 = input.LA(2);

                if ( (LA111_1==DOT) ) {
                    alt111=1;
                }
            }
            else if ( ((LA111_0>=EXPLAIN && LA111_0<=PLAN)||(LA111_0>=INDEXED && LA111_0<=BY)||(LA111_0>=OR && LA111_0<=ESCAPE)||(LA111_0>=IS && LA111_0<=BETWEEN)||LA111_0==COLLATE||(LA111_0>=DISTINCT && LA111_0<=THEN)||(LA111_0>=CURRENT_TIME && LA111_0<=CURRENT_TIMESTAMP)||(LA111_0>=RAISE && LA111_0<=FAIL)||(LA111_0>=PRAGMA && LA111_0<=FOR)||(LA111_0>=UNIQUE && LA111_0<=ROW)) ) {
                int LA111_2 = input.LA(2);

                if ( (LA111_2==DOT) ) {
                    alt111=1;
                }
            }
            switch (alt111) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:331:50: database_name= id DOT
                    {
                    pushFollow(FOLLOW_id_in_create_virtual_table_stmt2638);
                    database_name=id();

                    state._fsp--;

                    adaptor.addChild(root_0, database_name.getTree());
                    DOT293=(Token)match(input,DOT,FOLLOW_DOT_in_create_virtual_table_stmt2640); 
                    DOT293_tree = (Object)adaptor.create(DOT293);
                    adaptor.addChild(root_0, DOT293_tree);


                    }
                    break;

            }

            pushFollow(FOLLOW_id_in_create_virtual_table_stmt2646);
            table_name=id();

            state._fsp--;

            adaptor.addChild(root_0, table_name.getTree());
            USING294=(Token)match(input,USING,FOLLOW_USING_in_create_virtual_table_stmt2650); 
            USING294_tree = (Object)adaptor.create(USING294);
            adaptor.addChild(root_0, USING294_tree);

            pushFollow(FOLLOW_id_in_create_virtual_table_stmt2654);
            module_name=id();

            state._fsp--;

            adaptor.addChild(root_0, module_name.getTree());
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:332:24: ( LPAREN column_def ( COMMA column_def )* RPAREN )?
            int alt113=2;
            int LA113_0 = input.LA(1);

            if ( (LA113_0==LPAREN) ) {
                alt113=1;
            }
            switch (alt113) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:332:25: LPAREN column_def ( COMMA column_def )* RPAREN
                    {
                    LPAREN295=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_create_virtual_table_stmt2657); 
                    LPAREN295_tree = (Object)adaptor.create(LPAREN295);
                    adaptor.addChild(root_0, LPAREN295_tree);

                    pushFollow(FOLLOW_column_def_in_create_virtual_table_stmt2659);
                    column_def296=column_def();

                    state._fsp--;

                    adaptor.addChild(root_0, column_def296.getTree());
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:332:43: ( COMMA column_def )*
                    loop112:
                    do {
                        int alt112=2;
                        int LA112_0 = input.LA(1);

                        if ( (LA112_0==COMMA) ) {
                            alt112=1;
                        }


                        switch (alt112) {
                    	case 1 :
                    	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:332:44: COMMA column_def
                    	    {
                    	    COMMA297=(Token)match(input,COMMA,FOLLOW_COMMA_in_create_virtual_table_stmt2662); 
                    	    COMMA297_tree = (Object)adaptor.create(COMMA297);
                    	    adaptor.addChild(root_0, COMMA297_tree);

                    	    pushFollow(FOLLOW_column_def_in_create_virtual_table_stmt2664);
                    	    column_def298=column_def();

                    	    state._fsp--;

                    	    adaptor.addChild(root_0, column_def298.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop112;
                        }
                    } while (true);

                    RPAREN299=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_create_virtual_table_stmt2668); 
                    RPAREN299_tree = (Object)adaptor.create(RPAREN299);
                    adaptor.addChild(root_0, RPAREN299_tree);


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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:335:1: create_table_stmt : CREATE ( TEMPORARY )? TABLE ( IF NOT EXISTS )? (database_name= id DOT )? table_name= id ( LPAREN column_def ( COMMA column_def )* ( COMMA table_constraint )* RPAREN | AS select_stmt ) -> ^( CREATE_TABLE ^( OPTIONS ( TEMPORARY )? ( EXISTS )? ) ^( $table_name ( $database_name)? ) ( ^( COLUMNS ( column_def )+ ) )? ( ^( CONSTRAINTS ( table_constraint )* ) )? ( select_stmt )? ) ;
    public final SqlParser.create_table_stmt_return create_table_stmt() throws RecognitionException {
        SqlParser.create_table_stmt_return retval = new SqlParser.create_table_stmt_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token CREATE300=null;
        Token TEMPORARY301=null;
        Token TABLE302=null;
        Token IF303=null;
        Token NOT304=null;
        Token EXISTS305=null;
        Token DOT306=null;
        Token LPAREN307=null;
        Token COMMA309=null;
        Token COMMA311=null;
        Token RPAREN313=null;
        Token AS314=null;
        SqlParser.id_return database_name = null;

        SqlParser.id_return table_name = null;

        SqlParser.column_def_return column_def308 = null;

        SqlParser.column_def_return column_def310 = null;

        SqlParser.table_constraint_return table_constraint312 = null;

        SqlParser.select_stmt_return select_stmt315 = null;


        Object CREATE300_tree=null;
        Object TEMPORARY301_tree=null;
        Object TABLE302_tree=null;
        Object IF303_tree=null;
        Object NOT304_tree=null;
        Object EXISTS305_tree=null;
        Object DOT306_tree=null;
        Object LPAREN307_tree=null;
        Object COMMA309_tree=null;
        Object COMMA311_tree=null;
        Object RPAREN313_tree=null;
        Object AS314_tree=null;
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
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:335:18: ( CREATE ( TEMPORARY )? TABLE ( IF NOT EXISTS )? (database_name= id DOT )? table_name= id ( LPAREN column_def ( COMMA column_def )* ( COMMA table_constraint )* RPAREN | AS select_stmt ) -> ^( CREATE_TABLE ^( OPTIONS ( TEMPORARY )? ( EXISTS )? ) ^( $table_name ( $database_name)? ) ( ^( COLUMNS ( column_def )+ ) )? ( ^( CONSTRAINTS ( table_constraint )* ) )? ( select_stmt )? ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:335:20: CREATE ( TEMPORARY )? TABLE ( IF NOT EXISTS )? (database_name= id DOT )? table_name= id ( LPAREN column_def ( COMMA column_def )* ( COMMA table_constraint )* RPAREN | AS select_stmt )
            {
            CREATE300=(Token)match(input,CREATE,FOLLOW_CREATE_in_create_table_stmt2678);  
            stream_CREATE.add(CREATE300);

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:335:27: ( TEMPORARY )?
            int alt114=2;
            int LA114_0 = input.LA(1);

            if ( (LA114_0==TEMPORARY) ) {
                alt114=1;
            }
            switch (alt114) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:335:27: TEMPORARY
                    {
                    TEMPORARY301=(Token)match(input,TEMPORARY,FOLLOW_TEMPORARY_in_create_table_stmt2680);  
                    stream_TEMPORARY.add(TEMPORARY301);


                    }
                    break;

            }

            TABLE302=(Token)match(input,TABLE,FOLLOW_TABLE_in_create_table_stmt2683);  
            stream_TABLE.add(TABLE302);

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:335:44: ( IF NOT EXISTS )?
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
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:335:45: IF NOT EXISTS
                    {
                    IF303=(Token)match(input,IF,FOLLOW_IF_in_create_table_stmt2686);  
                    stream_IF.add(IF303);

                    NOT304=(Token)match(input,NOT,FOLLOW_NOT_in_create_table_stmt2688);  
                    stream_NOT.add(NOT304);

                    EXISTS305=(Token)match(input,EXISTS,FOLLOW_EXISTS_in_create_table_stmt2690);  
                    stream_EXISTS.add(EXISTS305);


                    }
                    break;

            }

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:335:61: (database_name= id DOT )?
            int alt116=2;
            int LA116_0 = input.LA(1);

            if ( (LA116_0==ID) ) {
                int LA116_1 = input.LA(2);

                if ( (LA116_1==DOT) ) {
                    alt116=1;
                }
            }
            else if ( ((LA116_0>=EXPLAIN && LA116_0<=PLAN)||(LA116_0>=INDEXED && LA116_0<=BY)||(LA116_0>=OR && LA116_0<=ESCAPE)||(LA116_0>=IS && LA116_0<=BETWEEN)||LA116_0==COLLATE||(LA116_0>=DISTINCT && LA116_0<=THEN)||(LA116_0>=CURRENT_TIME && LA116_0<=CURRENT_TIMESTAMP)||(LA116_0>=RAISE && LA116_0<=FAIL)||(LA116_0>=PRAGMA && LA116_0<=FOR)||(LA116_0>=UNIQUE && LA116_0<=ROW)) ) {
                int LA116_2 = input.LA(2);

                if ( (LA116_2==DOT) ) {
                    alt116=1;
                }
            }
            switch (alt116) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:335:62: database_name= id DOT
                    {
                    pushFollow(FOLLOW_id_in_create_table_stmt2697);
                    database_name=id();

                    state._fsp--;

                    stream_id.add(database_name.getTree());
                    DOT306=(Token)match(input,DOT,FOLLOW_DOT_in_create_table_stmt2699);  
                    stream_DOT.add(DOT306);


                    }
                    break;

            }

            pushFollow(FOLLOW_id_in_create_table_stmt2705);
            table_name=id();

            state._fsp--;

            stream_id.add(table_name.getTree());
            builder.addEntity( table_name.getTree().toString() ); System.out.println(table_name.getTree().toString());
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:336:3: ( LPAREN column_def ( COMMA column_def )* ( COMMA table_constraint )* RPAREN | AS select_stmt )
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
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:336:5: LPAREN column_def ( COMMA column_def )* ( COMMA table_constraint )* RPAREN
                    {
                    LPAREN307=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_create_table_stmt2713);  
                    stream_LPAREN.add(LPAREN307);

                    pushFollow(FOLLOW_column_def_in_create_table_stmt2715);
                    column_def308=column_def();

                    state._fsp--;

                    stream_column_def.add(column_def308.getTree());
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:336:23: ( COMMA column_def )*
                    loop117:
                    do {
                        int alt117=2;
                        alt117 = dfa117.predict(input);
                        switch (alt117) {
                    	case 1 :
                    	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:336:24: COMMA column_def
                    	    {
                    	    COMMA309=(Token)match(input,COMMA,FOLLOW_COMMA_in_create_table_stmt2718);  
                    	    stream_COMMA.add(COMMA309);

                    	    pushFollow(FOLLOW_column_def_in_create_table_stmt2720);
                    	    column_def310=column_def();

                    	    state._fsp--;

                    	    stream_column_def.add(column_def310.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop117;
                        }
                    } while (true);

                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:336:43: ( COMMA table_constraint )*
                    loop118:
                    do {
                        int alt118=2;
                        int LA118_0 = input.LA(1);

                        if ( (LA118_0==COMMA) ) {
                            alt118=1;
                        }


                        switch (alt118) {
                    	case 1 :
                    	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:336:44: COMMA table_constraint
                    	    {
                    	    COMMA311=(Token)match(input,COMMA,FOLLOW_COMMA_in_create_table_stmt2725);  
                    	    stream_COMMA.add(COMMA311);

                    	    pushFollow(FOLLOW_table_constraint_in_create_table_stmt2727);
                    	    table_constraint312=table_constraint();

                    	    state._fsp--;

                    	    stream_table_constraint.add(table_constraint312.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop118;
                        }
                    } while (true);

                    RPAREN313=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_create_table_stmt2731);  
                    stream_RPAREN.add(RPAREN313);


                    }
                    break;
                case 2 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:337:5: AS select_stmt
                    {
                    AS314=(Token)match(input,AS,FOLLOW_AS_in_create_table_stmt2737);  
                    stream_AS.add(AS314);

                    pushFollow(FOLLOW_select_stmt_in_create_table_stmt2739);
                    select_stmt315=select_stmt();

                    state._fsp--;

                    stream_select_stmt.add(select_stmt315.getTree());

                    }
                    break;

            }



            // AST REWRITE
            // elements: EXISTS, select_stmt, TEMPORARY, table_constraint, database_name, column_def, table_name
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
            // 338:1: -> ^( CREATE_TABLE ^( OPTIONS ( TEMPORARY )? ( EXISTS )? ) ^( $table_name ( $database_name)? ) ( ^( COLUMNS ( column_def )+ ) )? ( ^( CONSTRAINTS ( table_constraint )* ) )? ( select_stmt )? )
            {
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:338:4: ^( CREATE_TABLE ^( OPTIONS ( TEMPORARY )? ( EXISTS )? ) ^( $table_name ( $database_name)? ) ( ^( COLUMNS ( column_def )+ ) )? ( ^( CONSTRAINTS ( table_constraint )* ) )? ( select_stmt )? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(CREATE_TABLE, "CREATE_TABLE"), root_1);

                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:338:19: ^( OPTIONS ( TEMPORARY )? ( EXISTS )? )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(OPTIONS, "OPTIONS"), root_2);

                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:338:29: ( TEMPORARY )?
                if ( stream_TEMPORARY.hasNext() ) {
                    adaptor.addChild(root_2, stream_TEMPORARY.nextNode());

                }
                stream_TEMPORARY.reset();
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:338:40: ( EXISTS )?
                if ( stream_EXISTS.hasNext() ) {
                    adaptor.addChild(root_2, stream_EXISTS.nextNode());

                }
                stream_EXISTS.reset();

                adaptor.addChild(root_1, root_2);
                }
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:338:49: ^( $table_name ( $database_name)? )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot(stream_table_name.nextNode(), root_2);

                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:338:63: ( $database_name)?
                if ( stream_database_name.hasNext() ) {
                    adaptor.addChild(root_2, stream_database_name.nextTree());

                }
                stream_database_name.reset();

                adaptor.addChild(root_1, root_2);
                }
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:339:3: ( ^( COLUMNS ( column_def )+ ) )?
                if ( stream_column_def.hasNext() ) {
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:339:3: ^( COLUMNS ( column_def )+ )
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
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:339:27: ( ^( CONSTRAINTS ( table_constraint )* ) )?
                if ( stream_table_constraint.hasNext() ) {
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:339:27: ^( CONSTRAINTS ( table_constraint )* )
                    {
                    Object root_2 = (Object)adaptor.nil();
                    root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(CONSTRAINTS, "CONSTRAINTS"), root_2);

                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:339:41: ( table_constraint )*
                    while ( stream_table_constraint.hasNext() ) {
                        adaptor.addChild(root_2, stream_table_constraint.nextTree());

                    }
                    stream_table_constraint.reset();

                    adaptor.addChild(root_1, root_2);
                    }

                }
                stream_table_constraint.reset();
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:339:61: ( select_stmt )?
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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:341:1: column_def : name= id_column_def ( type_name )? ( column_constraint )* -> ^( $name ^( CONSTRAINTS ( column_constraint )* ) ( type_name )? ) ;
    public final SqlParser.column_def_return column_def() throws RecognitionException {
        SqlParser.column_def_return retval = new SqlParser.column_def_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        SqlParser.id_column_def_return name = null;

        SqlParser.type_name_return type_name316 = null;

        SqlParser.column_constraint_return column_constraint317 = null;


        RewriteRuleSubtreeStream stream_column_constraint=new RewriteRuleSubtreeStream(adaptor,"rule column_constraint");
        RewriteRuleSubtreeStream stream_id_column_def=new RewriteRuleSubtreeStream(adaptor,"rule id_column_def");
        RewriteRuleSubtreeStream stream_type_name=new RewriteRuleSubtreeStream(adaptor,"rule type_name");
        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:341:11: (name= id_column_def ( type_name )? ( column_constraint )* -> ^( $name ^( CONSTRAINTS ( column_constraint )* ) ( type_name )? ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:341:13: name= id_column_def ( type_name )? ( column_constraint )*
            {
            pushFollow(FOLLOW_id_column_def_in_column_def2796);
            name=id_column_def();

            state._fsp--;

            stream_id_column_def.add(name.getTree());
             builder.addAttributeToLastEntity( name.getTree().toString() ); 
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:341:99: ( type_name )?
            int alt120=2;
            alt120 = dfa120.predict(input);
            switch (alt120) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:341:99: type_name
                    {
                    pushFollow(FOLLOW_type_name_in_column_def2800);
                    type_name316=type_name();

                    state._fsp--;

                    stream_type_name.add(type_name316.getTree());

                    }
                    break;

            }

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:341:111: ( column_constraint )*
            loop121:
            do {
                int alt121=2;
                alt121 = dfa121.predict(input);
                switch (alt121) {
            	case 1 :
            	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:341:111: column_constraint
            	    {
            	    pushFollow(FOLLOW_column_constraint_in_column_def2804);
            	    column_constraint317=column_constraint();

            	    state._fsp--;

            	    stream_column_constraint.add(column_constraint317.getTree());

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
            // 342:1: -> ^( $name ^( CONSTRAINTS ( column_constraint )* ) ( type_name )? )
            {
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:342:4: ^( $name ^( CONSTRAINTS ( column_constraint )* ) ( type_name )? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(stream_name.nextNode(), root_1);

                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:342:12: ^( CONSTRAINTS ( column_constraint )* )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(CONSTRAINTS, "CONSTRAINTS"), root_2);

                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:342:26: ( column_constraint )*
                while ( stream_column_constraint.hasNext() ) {
                    adaptor.addChild(root_2, stream_column_constraint.nextTree());

                }
                stream_column_constraint.reset();

                adaptor.addChild(root_1, root_2);
                }
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:342:46: ( type_name )?
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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:344:1: column_constraint : ( CONSTRAINT name= id )? ( column_constraint_pk | column_constraint_null | column_constraint_not_null | column_constraint_not_for_replication | column_constraint_unique | column_constraint_check | column_constraint_default | column_constraint_collate | fk_clause ) -> ^( COLUMN_CONSTRAINT ( column_constraint_pk )? ( column_constraint_null )? ( column_constraint_not_null )? ( column_constraint_not_for_replication )? ( column_constraint_unique )? ( column_constraint_check )? ( column_constraint_default )? ( column_constraint_collate )? ( fk_clause )? ( $name)? ) ;
    public final SqlParser.column_constraint_return column_constraint() throws RecognitionException {
        SqlParser.column_constraint_return retval = new SqlParser.column_constraint_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token CONSTRAINT318=null;
        SqlParser.id_return name = null;

        SqlParser.column_constraint_pk_return column_constraint_pk319 = null;

        SqlParser.column_constraint_null_return column_constraint_null320 = null;

        SqlParser.column_constraint_not_null_return column_constraint_not_null321 = null;

        SqlParser.column_constraint_not_for_replication_return column_constraint_not_for_replication322 = null;

        SqlParser.column_constraint_unique_return column_constraint_unique323 = null;

        SqlParser.column_constraint_check_return column_constraint_check324 = null;

        SqlParser.column_constraint_default_return column_constraint_default325 = null;

        SqlParser.column_constraint_collate_return column_constraint_collate326 = null;

        SqlParser.fk_clause_return fk_clause327 = null;


        Object CONSTRAINT318_tree=null;
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
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:344:18: ( ( CONSTRAINT name= id )? ( column_constraint_pk | column_constraint_null | column_constraint_not_null | column_constraint_not_for_replication | column_constraint_unique | column_constraint_check | column_constraint_default | column_constraint_collate | fk_clause ) -> ^( COLUMN_CONSTRAINT ( column_constraint_pk )? ( column_constraint_null )? ( column_constraint_not_null )? ( column_constraint_not_for_replication )? ( column_constraint_unique )? ( column_constraint_check )? ( column_constraint_default )? ( column_constraint_collate )? ( fk_clause )? ( $name)? ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:344:20: ( CONSTRAINT name= id )? ( column_constraint_pk | column_constraint_null | column_constraint_not_null | column_constraint_not_for_replication | column_constraint_unique | column_constraint_check | column_constraint_default | column_constraint_collate | fk_clause )
            {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:344:20: ( CONSTRAINT name= id )?
            int alt122=2;
            alt122 = dfa122.predict(input);
            switch (alt122) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:344:21: CONSTRAINT name= id
                    {
                    CONSTRAINT318=(Token)match(input,CONSTRAINT,FOLLOW_CONSTRAINT_in_column_constraint2830);  
                    stream_CONSTRAINT.add(CONSTRAINT318);

                    pushFollow(FOLLOW_id_in_column_constraint2834);
                    name=id();

                    state._fsp--;

                    stream_id.add(name.getTree());

                    }
                    break;

            }

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:345:3: ( column_constraint_pk | column_constraint_null | column_constraint_not_null | column_constraint_not_for_replication | column_constraint_unique | column_constraint_check | column_constraint_default | column_constraint_collate | fk_clause )
            int alt123=9;
            alt123 = dfa123.predict(input);
            switch (alt123) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:345:5: column_constraint_pk
                    {
                    pushFollow(FOLLOW_column_constraint_pk_in_column_constraint2842);
                    column_constraint_pk319=column_constraint_pk();

                    state._fsp--;

                    stream_column_constraint_pk.add(column_constraint_pk319.getTree());

                    }
                    break;
                case 2 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:346:5: column_constraint_null
                    {
                    pushFollow(FOLLOW_column_constraint_null_in_column_constraint2848);
                    column_constraint_null320=column_constraint_null();

                    state._fsp--;

                    stream_column_constraint_null.add(column_constraint_null320.getTree());

                    }
                    break;
                case 3 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:347:5: column_constraint_not_null
                    {
                    pushFollow(FOLLOW_column_constraint_not_null_in_column_constraint2854);
                    column_constraint_not_null321=column_constraint_not_null();

                    state._fsp--;

                    stream_column_constraint_not_null.add(column_constraint_not_null321.getTree());

                    }
                    break;
                case 4 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:348:5: column_constraint_not_for_replication
                    {
                    pushFollow(FOLLOW_column_constraint_not_for_replication_in_column_constraint2860);
                    column_constraint_not_for_replication322=column_constraint_not_for_replication();

                    state._fsp--;

                    stream_column_constraint_not_for_replication.add(column_constraint_not_for_replication322.getTree());

                    }
                    break;
                case 5 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:349:5: column_constraint_unique
                    {
                    pushFollow(FOLLOW_column_constraint_unique_in_column_constraint2867);
                    column_constraint_unique323=column_constraint_unique();

                    state._fsp--;

                    stream_column_constraint_unique.add(column_constraint_unique323.getTree());

                    }
                    break;
                case 6 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:350:5: column_constraint_check
                    {
                    pushFollow(FOLLOW_column_constraint_check_in_column_constraint2873);
                    column_constraint_check324=column_constraint_check();

                    state._fsp--;

                    stream_column_constraint_check.add(column_constraint_check324.getTree());

                    }
                    break;
                case 7 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:351:5: column_constraint_default
                    {
                    pushFollow(FOLLOW_column_constraint_default_in_column_constraint2879);
                    column_constraint_default325=column_constraint_default();

                    state._fsp--;

                    stream_column_constraint_default.add(column_constraint_default325.getTree());

                    }
                    break;
                case 8 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:352:5: column_constraint_collate
                    {
                    pushFollow(FOLLOW_column_constraint_collate_in_column_constraint2885);
                    column_constraint_collate326=column_constraint_collate();

                    state._fsp--;

                    stream_column_constraint_collate.add(column_constraint_collate326.getTree());

                    }
                    break;
                case 9 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:353:5: fk_clause
                    {
                    pushFollow(FOLLOW_fk_clause_in_column_constraint2891);
                    fk_clause327=fk_clause();

                    state._fsp--;

                    stream_fk_clause.add(fk_clause327.getTree());

                    }
                    break;

            }



            // AST REWRITE
            // elements: column_constraint_collate, column_constraint_default, column_constraint_pk, column_constraint_null, column_constraint_check, column_constraint_not_null, column_constraint_unique, name, fk_clause, column_constraint_not_for_replication
            // token labels: 
            // rule labels: retval, name
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_name=new RewriteRuleSubtreeStream(adaptor,"rule name",name!=null?name.tree:null);

            root_0 = (Object)adaptor.nil();
            // 354:1: -> ^( COLUMN_CONSTRAINT ( column_constraint_pk )? ( column_constraint_null )? ( column_constraint_not_null )? ( column_constraint_not_for_replication )? ( column_constraint_unique )? ( column_constraint_check )? ( column_constraint_default )? ( column_constraint_collate )? ( fk_clause )? ( $name)? )
            {
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:354:4: ^( COLUMN_CONSTRAINT ( column_constraint_pk )? ( column_constraint_null )? ( column_constraint_not_null )? ( column_constraint_not_for_replication )? ( column_constraint_unique )? ( column_constraint_check )? ( column_constraint_default )? ( column_constraint_collate )? ( fk_clause )? ( $name)? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(COLUMN_CONSTRAINT, "COLUMN_CONSTRAINT"), root_1);

                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:355:3: ( column_constraint_pk )?
                if ( stream_column_constraint_pk.hasNext() ) {
                    adaptor.addChild(root_1, stream_column_constraint_pk.nextTree());

                }
                stream_column_constraint_pk.reset();
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:356:3: ( column_constraint_null )?
                if ( stream_column_constraint_null.hasNext() ) {
                    adaptor.addChild(root_1, stream_column_constraint_null.nextTree());

                }
                stream_column_constraint_null.reset();
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:357:3: ( column_constraint_not_null )?
                if ( stream_column_constraint_not_null.hasNext() ) {
                    adaptor.addChild(root_1, stream_column_constraint_not_null.nextTree());

                }
                stream_column_constraint_not_null.reset();
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:358:3: ( column_constraint_not_for_replication )?
                if ( stream_column_constraint_not_for_replication.hasNext() ) {
                    adaptor.addChild(root_1, stream_column_constraint_not_for_replication.nextTree());

                }
                stream_column_constraint_not_for_replication.reset();
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:359:3: ( column_constraint_unique )?
                if ( stream_column_constraint_unique.hasNext() ) {
                    adaptor.addChild(root_1, stream_column_constraint_unique.nextTree());

                }
                stream_column_constraint_unique.reset();
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:360:3: ( column_constraint_check )?
                if ( stream_column_constraint_check.hasNext() ) {
                    adaptor.addChild(root_1, stream_column_constraint_check.nextTree());

                }
                stream_column_constraint_check.reset();
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:361:3: ( column_constraint_default )?
                if ( stream_column_constraint_default.hasNext() ) {
                    adaptor.addChild(root_1, stream_column_constraint_default.nextTree());

                }
                stream_column_constraint_default.reset();
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:362:3: ( column_constraint_collate )?
                if ( stream_column_constraint_collate.hasNext() ) {
                    adaptor.addChild(root_1, stream_column_constraint_collate.nextTree());

                }
                stream_column_constraint_collate.reset();
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:363:3: ( fk_clause )?
                if ( stream_fk_clause.hasNext() ) {
                    adaptor.addChild(root_1, stream_fk_clause.nextTree());

                }
                stream_fk_clause.reset();
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:364:3: ( $name)?
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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:366:1: column_constraint_pk : PRIMARY KEY ( ASC | DESC )? ( table_conflict_clause )? ( AUTOINCREMENT )? ;
    public final SqlParser.column_constraint_pk_return column_constraint_pk() throws RecognitionException {
        SqlParser.column_constraint_pk_return retval = new SqlParser.column_constraint_pk_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token PRIMARY328=null;
        Token KEY329=null;
        Token set330=null;
        Token AUTOINCREMENT332=null;
        SqlParser.table_conflict_clause_return table_conflict_clause331 = null;


        Object PRIMARY328_tree=null;
        Object KEY329_tree=null;
        Object set330_tree=null;
        Object AUTOINCREMENT332_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:366:21: ( PRIMARY KEY ( ASC | DESC )? ( table_conflict_clause )? ( AUTOINCREMENT )? )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:366:23: PRIMARY KEY ( ASC | DESC )? ( table_conflict_clause )? ( AUTOINCREMENT )?
            {
            root_0 = (Object)adaptor.nil();

            PRIMARY328=(Token)match(input,PRIMARY,FOLLOW_PRIMARY_in_column_constraint_pk2956); 
            PRIMARY328_tree = (Object)adaptor.create(PRIMARY328);
            root_0 = (Object)adaptor.becomeRoot(PRIMARY328_tree, root_0);

            KEY329=(Token)match(input,KEY,FOLLOW_KEY_in_column_constraint_pk2959); 
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:366:37: ( ASC | DESC )?
            int alt124=2;
            alt124 = dfa124.predict(input);
            switch (alt124) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:
                    {
                    set330=(Token)input.LT(1);
                    if ( (input.LA(1)>=ASC && input.LA(1)<=DESC) ) {
                        input.consume();
                        adaptor.addChild(root_0, (Object)adaptor.create(set330));
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }


                    }
                    break;

            }

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:366:51: ( table_conflict_clause )?
            int alt125=2;
            alt125 = dfa125.predict(input);
            switch (alt125) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:366:51: table_conflict_clause
                    {
                    pushFollow(FOLLOW_table_conflict_clause_in_column_constraint_pk2971);
                    table_conflict_clause331=table_conflict_clause();

                    state._fsp--;

                    adaptor.addChild(root_0, table_conflict_clause331.getTree());

                    }
                    break;

            }

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:366:74: ( AUTOINCREMENT )?
            int alt126=2;
            alt126 = dfa126.predict(input);
            switch (alt126) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:366:75: AUTOINCREMENT
                    {
                    AUTOINCREMENT332=(Token)match(input,AUTOINCREMENT,FOLLOW_AUTOINCREMENT_in_column_constraint_pk2975); 
                    AUTOINCREMENT332_tree = (Object)adaptor.create(AUTOINCREMENT332);
                    adaptor.addChild(root_0, AUTOINCREMENT332_tree);


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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:368:1: column_constraint_not_null : NOT NULL ( table_conflict_clause )? -> ^( NOT_NULL ( table_conflict_clause )? ) ;
    public final SqlParser.column_constraint_not_null_return column_constraint_not_null() throws RecognitionException {
        SqlParser.column_constraint_not_null_return retval = new SqlParser.column_constraint_not_null_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token NOT333=null;
        Token NULL334=null;
        SqlParser.table_conflict_clause_return table_conflict_clause335 = null;


        Object NOT333_tree=null;
        Object NULL334_tree=null;
        RewriteRuleTokenStream stream_NOT=new RewriteRuleTokenStream(adaptor,"token NOT");
        RewriteRuleTokenStream stream_NULL=new RewriteRuleTokenStream(adaptor,"token NULL");
        RewriteRuleSubtreeStream stream_table_conflict_clause=new RewriteRuleSubtreeStream(adaptor,"rule table_conflict_clause");
        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:368:27: ( NOT NULL ( table_conflict_clause )? -> ^( NOT_NULL ( table_conflict_clause )? ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:368:29: NOT NULL ( table_conflict_clause )?
            {
            NOT333=(Token)match(input,NOT,FOLLOW_NOT_in_column_constraint_not_null2984);  
            stream_NOT.add(NOT333);

            NULL334=(Token)match(input,NULL,FOLLOW_NULL_in_column_constraint_not_null2986);  
            stream_NULL.add(NULL334);

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:368:38: ( table_conflict_clause )?
            int alt127=2;
            alt127 = dfa127.predict(input);
            switch (alt127) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:368:38: table_conflict_clause
                    {
                    pushFollow(FOLLOW_table_conflict_clause_in_column_constraint_not_null2988);
                    table_conflict_clause335=table_conflict_clause();

                    state._fsp--;

                    stream_table_conflict_clause.add(table_conflict_clause335.getTree());

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
            // 368:61: -> ^( NOT_NULL ( table_conflict_clause )? )
            {
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:368:64: ^( NOT_NULL ( table_conflict_clause )? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(NOT_NULL, "NOT_NULL"), root_1);

                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:368:75: ( table_conflict_clause )?
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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:370:1: column_constraint_not_for_replication : NOT FOR REPLICATION ( table_conflict_clause )? ;
    public final SqlParser.column_constraint_not_for_replication_return column_constraint_not_for_replication() throws RecognitionException {
        SqlParser.column_constraint_not_for_replication_return retval = new SqlParser.column_constraint_not_for_replication_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token NOT336=null;
        Token FOR337=null;
        Token REPLICATION338=null;
        SqlParser.table_conflict_clause_return table_conflict_clause339 = null;


        Object NOT336_tree=null;
        Object FOR337_tree=null;
        Object REPLICATION338_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:370:38: ( NOT FOR REPLICATION ( table_conflict_clause )? )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:370:40: NOT FOR REPLICATION ( table_conflict_clause )?
            {
            root_0 = (Object)adaptor.nil();

            NOT336=(Token)match(input,NOT,FOLLOW_NOT_in_column_constraint_not_for_replication3005); 
            NOT336_tree = (Object)adaptor.create(NOT336);
            adaptor.addChild(root_0, NOT336_tree);

            FOR337=(Token)match(input,FOR,FOLLOW_FOR_in_column_constraint_not_for_replication3007); 
            FOR337_tree = (Object)adaptor.create(FOR337);
            adaptor.addChild(root_0, FOR337_tree);

            REPLICATION338=(Token)match(input,REPLICATION,FOLLOW_REPLICATION_in_column_constraint_not_for_replication3009); 
            REPLICATION338_tree = (Object)adaptor.create(REPLICATION338);
            adaptor.addChild(root_0, REPLICATION338_tree);

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:370:60: ( table_conflict_clause )?
            int alt128=2;
            alt128 = dfa128.predict(input);
            switch (alt128) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:370:60: table_conflict_clause
                    {
                    pushFollow(FOLLOW_table_conflict_clause_in_column_constraint_not_for_replication3011);
                    table_conflict_clause339=table_conflict_clause();

                    state._fsp--;

                    adaptor.addChild(root_0, table_conflict_clause339.getTree());

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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:372:1: column_constraint_null : NULL ( table_conflict_clause )? ;
    public final SqlParser.column_constraint_null_return column_constraint_null() throws RecognitionException {
        SqlParser.column_constraint_null_return retval = new SqlParser.column_constraint_null_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token NULL340=null;
        SqlParser.table_conflict_clause_return table_conflict_clause341 = null;


        Object NULL340_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:372:23: ( NULL ( table_conflict_clause )? )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:372:25: NULL ( table_conflict_clause )?
            {
            root_0 = (Object)adaptor.nil();

            NULL340=(Token)match(input,NULL,FOLLOW_NULL_in_column_constraint_null3019); 
            NULL340_tree = (Object)adaptor.create(NULL340);
            adaptor.addChild(root_0, NULL340_tree);

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:372:30: ( table_conflict_clause )?
            int alt129=2;
            alt129 = dfa129.predict(input);
            switch (alt129) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:372:30: table_conflict_clause
                    {
                    pushFollow(FOLLOW_table_conflict_clause_in_column_constraint_null3021);
                    table_conflict_clause341=table_conflict_clause();

                    state._fsp--;

                    adaptor.addChild(root_0, table_conflict_clause341.getTree());

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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:374:1: column_constraint_unique : UNIQUE ( table_conflict_clause )? ;
    public final SqlParser.column_constraint_unique_return column_constraint_unique() throws RecognitionException {
        SqlParser.column_constraint_unique_return retval = new SqlParser.column_constraint_unique_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token UNIQUE342=null;
        SqlParser.table_conflict_clause_return table_conflict_clause343 = null;


        Object UNIQUE342_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:374:25: ( UNIQUE ( table_conflict_clause )? )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:374:27: UNIQUE ( table_conflict_clause )?
            {
            root_0 = (Object)adaptor.nil();

            UNIQUE342=(Token)match(input,UNIQUE,FOLLOW_UNIQUE_in_column_constraint_unique3029); 
            UNIQUE342_tree = (Object)adaptor.create(UNIQUE342);
            root_0 = (Object)adaptor.becomeRoot(UNIQUE342_tree, root_0);

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:374:35: ( table_conflict_clause )?
            int alt130=2;
            alt130 = dfa130.predict(input);
            switch (alt130) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:374:35: table_conflict_clause
                    {
                    pushFollow(FOLLOW_table_conflict_clause_in_column_constraint_unique3032);
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
    // $ANTLR end "column_constraint_unique"

    public static class column_constraint_check_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "column_constraint_check"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:376:1: column_constraint_check : CHECK LPAREN expr RPAREN ;
    public final SqlParser.column_constraint_check_return column_constraint_check() throws RecognitionException {
        SqlParser.column_constraint_check_return retval = new SqlParser.column_constraint_check_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token CHECK344=null;
        Token LPAREN345=null;
        Token RPAREN347=null;
        SqlParser.expr_return expr346 = null;


        Object CHECK344_tree=null;
        Object LPAREN345_tree=null;
        Object RPAREN347_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:376:24: ( CHECK LPAREN expr RPAREN )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:376:26: CHECK LPAREN expr RPAREN
            {
            root_0 = (Object)adaptor.nil();

            CHECK344=(Token)match(input,CHECK,FOLLOW_CHECK_in_column_constraint_check3040); 
            CHECK344_tree = (Object)adaptor.create(CHECK344);
            root_0 = (Object)adaptor.becomeRoot(CHECK344_tree, root_0);

            LPAREN345=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_column_constraint_check3043); 
            pushFollow(FOLLOW_expr_in_column_constraint_check3046);
            expr346=expr();

            state._fsp--;

            adaptor.addChild(root_0, expr346.getTree());
            RPAREN347=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_column_constraint_check3048); 

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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:378:1: numeric_literal_value : ( INTEGER -> ^( INTEGER_LITERAL INTEGER ) | FLOAT -> ^( FLOAT_LITERAL FLOAT ) );
    public final SqlParser.numeric_literal_value_return numeric_literal_value() throws RecognitionException {
        SqlParser.numeric_literal_value_return retval = new SqlParser.numeric_literal_value_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token INTEGER348=null;
        Token FLOAT349=null;

        Object INTEGER348_tree=null;
        Object FLOAT349_tree=null;
        RewriteRuleTokenStream stream_INTEGER=new RewriteRuleTokenStream(adaptor,"token INTEGER");
        RewriteRuleTokenStream stream_FLOAT=new RewriteRuleTokenStream(adaptor,"token FLOAT");

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:379:3: ( INTEGER -> ^( INTEGER_LITERAL INTEGER ) | FLOAT -> ^( FLOAT_LITERAL FLOAT ) )
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
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:379:5: INTEGER
                    {
                    INTEGER348=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_numeric_literal_value3059);  
                    stream_INTEGER.add(INTEGER348);



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
                    // 379:13: -> ^( INTEGER_LITERAL INTEGER )
                    {
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:379:16: ^( INTEGER_LITERAL INTEGER )
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
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:380:5: FLOAT
                    {
                    FLOAT349=(Token)match(input,FLOAT,FOLLOW_FLOAT_in_numeric_literal_value3073);  
                    stream_FLOAT.add(FLOAT349);



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
                    // 380:11: -> ^( FLOAT_LITERAL FLOAT )
                    {
                        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:380:14: ^( FLOAT_LITERAL FLOAT )
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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:383:1: signed_default_number : ( PLUS | MINUS ) numeric_literal_value ;
    public final SqlParser.signed_default_number_return signed_default_number() throws RecognitionException {
        SqlParser.signed_default_number_return retval = new SqlParser.signed_default_number_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set350=null;
        SqlParser.numeric_literal_value_return numeric_literal_value351 = null;


        Object set350_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:383:22: ( ( PLUS | MINUS ) numeric_literal_value )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:383:24: ( PLUS | MINUS ) numeric_literal_value
            {
            root_0 = (Object)adaptor.nil();

            set350=(Token)input.LT(1);
            set350=(Token)input.LT(1);
            if ( (input.LA(1)>=PLUS && input.LA(1)<=MINUS) ) {
                input.consume();
                root_0 = (Object)adaptor.becomeRoot((Object)adaptor.create(set350), root_0);
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            pushFollow(FOLLOW_numeric_literal_value_in_signed_default_number3100);
            numeric_literal_value351=numeric_literal_value();

            state._fsp--;

            adaptor.addChild(root_0, numeric_literal_value351.getTree());

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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:386:1: column_constraint_default : DEFAULT ( signed_default_number | literal_value | LPAREN expr RPAREN ) ;
    public final SqlParser.column_constraint_default_return column_constraint_default() throws RecognitionException {
        SqlParser.column_constraint_default_return retval = new SqlParser.column_constraint_default_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token DEFAULT352=null;
        Token LPAREN355=null;
        Token RPAREN357=null;
        SqlParser.signed_default_number_return signed_default_number353 = null;

        SqlParser.literal_value_return literal_value354 = null;

        SqlParser.expr_return expr356 = null;


        Object DEFAULT352_tree=null;
        Object LPAREN355_tree=null;
        Object RPAREN357_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:386:26: ( DEFAULT ( signed_default_number | literal_value | LPAREN expr RPAREN ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:386:28: DEFAULT ( signed_default_number | literal_value | LPAREN expr RPAREN )
            {
            root_0 = (Object)adaptor.nil();

            DEFAULT352=(Token)match(input,DEFAULT,FOLLOW_DEFAULT_in_column_constraint_default3108); 
            DEFAULT352_tree = (Object)adaptor.create(DEFAULT352);
            root_0 = (Object)adaptor.becomeRoot(DEFAULT352_tree, root_0);

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:386:37: ( signed_default_number | literal_value | LPAREN expr RPAREN )
            int alt132=3;
            alt132 = dfa132.predict(input);
            switch (alt132) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:386:38: signed_default_number
                    {
                    pushFollow(FOLLOW_signed_default_number_in_column_constraint_default3112);
                    signed_default_number353=signed_default_number();

                    state._fsp--;

                    adaptor.addChild(root_0, signed_default_number353.getTree());

                    }
                    break;
                case 2 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:386:62: literal_value
                    {
                    pushFollow(FOLLOW_literal_value_in_column_constraint_default3116);
                    literal_value354=literal_value();

                    state._fsp--;

                    adaptor.addChild(root_0, literal_value354.getTree());

                    }
                    break;
                case 3 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:386:78: LPAREN expr RPAREN
                    {
                    LPAREN355=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_column_constraint_default3120); 
                    pushFollow(FOLLOW_expr_in_column_constraint_default3123);
                    expr356=expr();

                    state._fsp--;

                    adaptor.addChild(root_0, expr356.getTree());
                    RPAREN357=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_column_constraint_default3125); 

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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:388:1: column_constraint_collate : COLLATE collation_name= id ;
    public final SqlParser.column_constraint_collate_return column_constraint_collate() throws RecognitionException {
        SqlParser.column_constraint_collate_return retval = new SqlParser.column_constraint_collate_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token COLLATE358=null;
        SqlParser.id_return collation_name = null;


        Object COLLATE358_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:388:26: ( COLLATE collation_name= id )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:388:28: COLLATE collation_name= id
            {
            root_0 = (Object)adaptor.nil();

            COLLATE358=(Token)match(input,COLLATE,FOLLOW_COLLATE_in_column_constraint_collate3134); 
            COLLATE358_tree = (Object)adaptor.create(COLLATE358);
            root_0 = (Object)adaptor.becomeRoot(COLLATE358_tree, root_0);

            pushFollow(FOLLOW_id_in_column_constraint_collate3139);
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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:390:1: table_constraint : ( CONSTRAINT name= id )? ( table_constraint_pk | table_constraint_unique | table_constraint_check | table_constraint_fk ) -> ^( TABLE_CONSTRAINT ( table_constraint_pk )? ( table_constraint_unique )? ( table_constraint_check )? ( table_constraint_fk )? ( $name)? ) ;
    public final SqlParser.table_constraint_return table_constraint() throws RecognitionException {
        SqlParser.table_constraint_return retval = new SqlParser.table_constraint_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token CONSTRAINT359=null;
        SqlParser.id_return name = null;

        SqlParser.table_constraint_pk_return table_constraint_pk360 = null;

        SqlParser.table_constraint_unique_return table_constraint_unique361 = null;

        SqlParser.table_constraint_check_return table_constraint_check362 = null;

        SqlParser.table_constraint_fk_return table_constraint_fk363 = null;


        Object CONSTRAINT359_tree=null;
        RewriteRuleTokenStream stream_CONSTRAINT=new RewriteRuleTokenStream(adaptor,"token CONSTRAINT");
        RewriteRuleSubtreeStream stream_id=new RewriteRuleSubtreeStream(adaptor,"rule id");
        RewriteRuleSubtreeStream stream_table_constraint_pk=new RewriteRuleSubtreeStream(adaptor,"rule table_constraint_pk");
        RewriteRuleSubtreeStream stream_table_constraint_fk=new RewriteRuleSubtreeStream(adaptor,"rule table_constraint_fk");
        RewriteRuleSubtreeStream stream_table_constraint_unique=new RewriteRuleSubtreeStream(adaptor,"rule table_constraint_unique");
        RewriteRuleSubtreeStream stream_table_constraint_check=new RewriteRuleSubtreeStream(adaptor,"rule table_constraint_check");
        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:390:17: ( ( CONSTRAINT name= id )? ( table_constraint_pk | table_constraint_unique | table_constraint_check | table_constraint_fk ) -> ^( TABLE_CONSTRAINT ( table_constraint_pk )? ( table_constraint_unique )? ( table_constraint_check )? ( table_constraint_fk )? ( $name)? ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:390:19: ( CONSTRAINT name= id )? ( table_constraint_pk | table_constraint_unique | table_constraint_check | table_constraint_fk )
            {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:390:19: ( CONSTRAINT name= id )?
            int alt133=2;
            int LA133_0 = input.LA(1);

            if ( (LA133_0==CONSTRAINT) ) {
                alt133=1;
            }
            switch (alt133) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:390:20: CONSTRAINT name= id
                    {
                    CONSTRAINT359=(Token)match(input,CONSTRAINT,FOLLOW_CONSTRAINT_in_table_constraint3148);  
                    stream_CONSTRAINT.add(CONSTRAINT359);

                    pushFollow(FOLLOW_id_in_table_constraint3152);
                    name=id();

                    state._fsp--;

                    stream_id.add(name.getTree());

                    }
                    break;

            }

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:391:3: ( table_constraint_pk | table_constraint_unique | table_constraint_check | table_constraint_fk )
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
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:391:5: table_constraint_pk
                    {
                    pushFollow(FOLLOW_table_constraint_pk_in_table_constraint3160);
                    table_constraint_pk360=table_constraint_pk();

                    state._fsp--;

                    stream_table_constraint_pk.add(table_constraint_pk360.getTree());
                    System.out.println("\tPK constraint");

                    }
                    break;
                case 2 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:392:5: table_constraint_unique
                    {
                    pushFollow(FOLLOW_table_constraint_unique_in_table_constraint3168);
                    table_constraint_unique361=table_constraint_unique();

                    state._fsp--;

                    stream_table_constraint_unique.add(table_constraint_unique361.getTree());

                    }
                    break;
                case 3 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:393:5: table_constraint_check
                    {
                    pushFollow(FOLLOW_table_constraint_check_in_table_constraint3174);
                    table_constraint_check362=table_constraint_check();

                    state._fsp--;

                    stream_table_constraint_check.add(table_constraint_check362.getTree());

                    }
                    break;
                case 4 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:394:5: table_constraint_fk
                    {
                    pushFollow(FOLLOW_table_constraint_fk_in_table_constraint3180);
                    table_constraint_fk363=table_constraint_fk();

                    state._fsp--;

                    stream_table_constraint_fk.add(table_constraint_fk363.getTree());

                    }
                    break;

            }



            // AST REWRITE
            // elements: name, table_constraint_fk, table_constraint_check, table_constraint_pk, table_constraint_unique
            // token labels: 
            // rule labels: retval, name
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_name=new RewriteRuleSubtreeStream(adaptor,"rule name",name!=null?name.tree:null);

            root_0 = (Object)adaptor.nil();
            // 395:1: -> ^( TABLE_CONSTRAINT ( table_constraint_pk )? ( table_constraint_unique )? ( table_constraint_check )? ( table_constraint_fk )? ( $name)? )
            {
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:395:4: ^( TABLE_CONSTRAINT ( table_constraint_pk )? ( table_constraint_unique )? ( table_constraint_check )? ( table_constraint_fk )? ( $name)? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(TABLE_CONSTRAINT, "TABLE_CONSTRAINT"), root_1);

                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:396:3: ( table_constraint_pk )?
                if ( stream_table_constraint_pk.hasNext() ) {
                    adaptor.addChild(root_1, stream_table_constraint_pk.nextTree());

                }
                stream_table_constraint_pk.reset();
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:397:3: ( table_constraint_unique )?
                if ( stream_table_constraint_unique.hasNext() ) {
                    adaptor.addChild(root_1, stream_table_constraint_unique.nextTree());

                }
                stream_table_constraint_unique.reset();
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:398:3: ( table_constraint_check )?
                if ( stream_table_constraint_check.hasNext() ) {
                    adaptor.addChild(root_1, stream_table_constraint_check.nextTree());

                }
                stream_table_constraint_check.reset();
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:399:3: ( table_constraint_fk )?
                if ( stream_table_constraint_fk.hasNext() ) {
                    adaptor.addChild(root_1, stream_table_constraint_fk.nextTree());

                }
                stream_table_constraint_fk.reset();
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:400:3: ( $name)?
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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:402:1: table_constraint_pk : PRIMARY KEY LPAREN indexed_columns+= id ( COMMA indexed_columns+= id )* RPAREN ( table_conflict_clause )? -> ^( PRIMARY ^( COLUMNS ( $indexed_columns)+ ) ( table_conflict_clause )? ) ;
    public final SqlParser.table_constraint_pk_return table_constraint_pk() throws RecognitionException {
        SqlParser.table_constraint_pk_return retval = new SqlParser.table_constraint_pk_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token PRIMARY364=null;
        Token KEY365=null;
        Token LPAREN366=null;
        Token COMMA367=null;
        Token RPAREN368=null;
        List list_indexed_columns=null;
        SqlParser.table_conflict_clause_return table_conflict_clause369 = null;

        SqlParser.id_return indexed_columns = null;
         indexed_columns = null;
        Object PRIMARY364_tree=null;
        Object KEY365_tree=null;
        Object LPAREN366_tree=null;
        Object COMMA367_tree=null;
        Object RPAREN368_tree=null;
        RewriteRuleTokenStream stream_RPAREN=new RewriteRuleTokenStream(adaptor,"token RPAREN");
        RewriteRuleTokenStream stream_PRIMARY=new RewriteRuleTokenStream(adaptor,"token PRIMARY");
        RewriteRuleTokenStream stream_COMMA=new RewriteRuleTokenStream(adaptor,"token COMMA");
        RewriteRuleTokenStream stream_KEY=new RewriteRuleTokenStream(adaptor,"token KEY");
        RewriteRuleTokenStream stream_LPAREN=new RewriteRuleTokenStream(adaptor,"token LPAREN");
        RewriteRuleSubtreeStream stream_id=new RewriteRuleSubtreeStream(adaptor,"rule id");
        RewriteRuleSubtreeStream stream_table_conflict_clause=new RewriteRuleSubtreeStream(adaptor,"rule table_conflict_clause");
        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:402:20: ( PRIMARY KEY LPAREN indexed_columns+= id ( COMMA indexed_columns+= id )* RPAREN ( table_conflict_clause )? -> ^( PRIMARY ^( COLUMNS ( $indexed_columns)+ ) ( table_conflict_clause )? ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:402:22: PRIMARY KEY LPAREN indexed_columns+= id ( COMMA indexed_columns+= id )* RPAREN ( table_conflict_clause )?
            {
            PRIMARY364=(Token)match(input,PRIMARY,FOLLOW_PRIMARY_in_table_constraint_pk3221);  
            stream_PRIMARY.add(PRIMARY364);

            KEY365=(Token)match(input,KEY,FOLLOW_KEY_in_table_constraint_pk3223);  
            stream_KEY.add(KEY365);

            LPAREN366=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_table_constraint_pk3227);  
            stream_LPAREN.add(LPAREN366);

            pushFollow(FOLLOW_id_in_table_constraint_pk3231);
            indexed_columns=id();

            state._fsp--;

            stream_id.add(indexed_columns.getTree());
            if (list_indexed_columns==null) list_indexed_columns=new ArrayList();
            list_indexed_columns.add(indexed_columns.getTree());

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:403:30: ( COMMA indexed_columns+= id )*
            loop135:
            do {
                int alt135=2;
                int LA135_0 = input.LA(1);

                if ( (LA135_0==COMMA) ) {
                    alt135=1;
                }


                switch (alt135) {
            	case 1 :
            	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:403:31: COMMA indexed_columns+= id
            	    {
            	    COMMA367=(Token)match(input,COMMA,FOLLOW_COMMA_in_table_constraint_pk3234);  
            	    stream_COMMA.add(COMMA367);

            	    pushFollow(FOLLOW_id_in_table_constraint_pk3238);
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

            RPAREN368=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_table_constraint_pk3242);  
            stream_RPAREN.add(RPAREN368);

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:403:66: ( table_conflict_clause )?
            int alt136=2;
            int LA136_0 = input.LA(1);

            if ( (LA136_0==ON) ) {
                alt136=1;
            }
            switch (alt136) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:403:66: table_conflict_clause
                    {
                    pushFollow(FOLLOW_table_conflict_clause_in_table_constraint_pk3244);
                    table_conflict_clause369=table_conflict_clause();

                    state._fsp--;

                    stream_table_conflict_clause.add(table_conflict_clause369.getTree());

                    }
                    break;

            }



            // AST REWRITE
            // elements: table_conflict_clause, PRIMARY, indexed_columns
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: indexed_columns
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_indexed_columns=new RewriteRuleSubtreeStream(adaptor,"token indexed_columns",list_indexed_columns);
            root_0 = (Object)adaptor.nil();
            // 404:1: -> ^( PRIMARY ^( COLUMNS ( $indexed_columns)+ ) ( table_conflict_clause )? )
            {
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:404:4: ^( PRIMARY ^( COLUMNS ( $indexed_columns)+ ) ( table_conflict_clause )? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(stream_PRIMARY.nextNode(), root_1);

                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:404:14: ^( COLUMNS ( $indexed_columns)+ )
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
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:404:43: ( table_conflict_clause )?
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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:406:1: table_constraint_unique : UNIQUE LPAREN indexed_columns+= id ( COMMA indexed_columns+= id )* RPAREN ( table_conflict_clause )? -> ^( UNIQUE ^( COLUMNS ( $indexed_columns)+ ) ( table_conflict_clause )? ) ;
    public final SqlParser.table_constraint_unique_return table_constraint_unique() throws RecognitionException {
        SqlParser.table_constraint_unique_return retval = new SqlParser.table_constraint_unique_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token UNIQUE370=null;
        Token LPAREN371=null;
        Token COMMA372=null;
        Token RPAREN373=null;
        List list_indexed_columns=null;
        SqlParser.table_conflict_clause_return table_conflict_clause374 = null;

        SqlParser.id_return indexed_columns = null;
         indexed_columns = null;
        Object UNIQUE370_tree=null;
        Object LPAREN371_tree=null;
        Object COMMA372_tree=null;
        Object RPAREN373_tree=null;
        RewriteRuleTokenStream stream_UNIQUE=new RewriteRuleTokenStream(adaptor,"token UNIQUE");
        RewriteRuleTokenStream stream_RPAREN=new RewriteRuleTokenStream(adaptor,"token RPAREN");
        RewriteRuleTokenStream stream_COMMA=new RewriteRuleTokenStream(adaptor,"token COMMA");
        RewriteRuleTokenStream stream_LPAREN=new RewriteRuleTokenStream(adaptor,"token LPAREN");
        RewriteRuleSubtreeStream stream_id=new RewriteRuleSubtreeStream(adaptor,"rule id");
        RewriteRuleSubtreeStream stream_table_conflict_clause=new RewriteRuleSubtreeStream(adaptor,"rule table_conflict_clause");
        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:406:24: ( UNIQUE LPAREN indexed_columns+= id ( COMMA indexed_columns+= id )* RPAREN ( table_conflict_clause )? -> ^( UNIQUE ^( COLUMNS ( $indexed_columns)+ ) ( table_conflict_clause )? ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:406:26: UNIQUE LPAREN indexed_columns+= id ( COMMA indexed_columns+= id )* RPAREN ( table_conflict_clause )?
            {
            UNIQUE370=(Token)match(input,UNIQUE,FOLLOW_UNIQUE_in_table_constraint_unique3269);  
            stream_UNIQUE.add(UNIQUE370);

            LPAREN371=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_table_constraint_unique3273);  
            stream_LPAREN.add(LPAREN371);

            pushFollow(FOLLOW_id_in_table_constraint_unique3277);
            indexed_columns=id();

            state._fsp--;

            stream_id.add(indexed_columns.getTree());
            if (list_indexed_columns==null) list_indexed_columns=new ArrayList();
            list_indexed_columns.add(indexed_columns.getTree());

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:407:30: ( COMMA indexed_columns+= id )*
            loop137:
            do {
                int alt137=2;
                int LA137_0 = input.LA(1);

                if ( (LA137_0==COMMA) ) {
                    alt137=1;
                }


                switch (alt137) {
            	case 1 :
            	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:407:31: COMMA indexed_columns+= id
            	    {
            	    COMMA372=(Token)match(input,COMMA,FOLLOW_COMMA_in_table_constraint_unique3280);  
            	    stream_COMMA.add(COMMA372);

            	    pushFollow(FOLLOW_id_in_table_constraint_unique3284);
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

            RPAREN373=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_table_constraint_unique3288);  
            stream_RPAREN.add(RPAREN373);

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:407:66: ( table_conflict_clause )?
            int alt138=2;
            int LA138_0 = input.LA(1);

            if ( (LA138_0==ON) ) {
                alt138=1;
            }
            switch (alt138) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:407:66: table_conflict_clause
                    {
                    pushFollow(FOLLOW_table_conflict_clause_in_table_constraint_unique3290);
                    table_conflict_clause374=table_conflict_clause();

                    state._fsp--;

                    stream_table_conflict_clause.add(table_conflict_clause374.getTree());

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
            // 408:1: -> ^( UNIQUE ^( COLUMNS ( $indexed_columns)+ ) ( table_conflict_clause )? )
            {
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:408:4: ^( UNIQUE ^( COLUMNS ( $indexed_columns)+ ) ( table_conflict_clause )? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(stream_UNIQUE.nextNode(), root_1);

                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:408:13: ^( COLUMNS ( $indexed_columns)+ )
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
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:408:42: ( table_conflict_clause )?
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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:410:1: table_constraint_check : CHECK LPAREN expr RPAREN ;
    public final SqlParser.table_constraint_check_return table_constraint_check() throws RecognitionException {
        SqlParser.table_constraint_check_return retval = new SqlParser.table_constraint_check_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token CHECK375=null;
        Token LPAREN376=null;
        Token RPAREN378=null;
        SqlParser.expr_return expr377 = null;


        Object CHECK375_tree=null;
        Object LPAREN376_tree=null;
        Object RPAREN378_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:410:23: ( CHECK LPAREN expr RPAREN )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:410:25: CHECK LPAREN expr RPAREN
            {
            root_0 = (Object)adaptor.nil();

            CHECK375=(Token)match(input,CHECK,FOLLOW_CHECK_in_table_constraint_check3315); 
            CHECK375_tree = (Object)adaptor.create(CHECK375);
            root_0 = (Object)adaptor.becomeRoot(CHECK375_tree, root_0);

            LPAREN376=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_table_constraint_check3318); 
            pushFollow(FOLLOW_expr_in_table_constraint_check3321);
            expr377=expr();

            state._fsp--;

            adaptor.addChild(root_0, expr377.getTree());
            RPAREN378=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_table_constraint_check3323); 

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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:412:1: table_constraint_fk : FOREIGN KEY LPAREN column_names+= id ( COMMA column_names+= id )* RPAREN fk_clause -> ^( FOREIGN ^( COLUMNS ( $column_names)+ ) fk_clause ) ;
    public final SqlParser.table_constraint_fk_return table_constraint_fk() throws RecognitionException {
        SqlParser.table_constraint_fk_return retval = new SqlParser.table_constraint_fk_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token FOREIGN379=null;
        Token KEY380=null;
        Token LPAREN381=null;
        Token COMMA382=null;
        Token RPAREN383=null;
        List list_column_names=null;
        SqlParser.fk_clause_return fk_clause384 = null;

        SqlParser.id_return column_names = null;
         column_names = null;
        Object FOREIGN379_tree=null;
        Object KEY380_tree=null;
        Object LPAREN381_tree=null;
        Object COMMA382_tree=null;
        Object RPAREN383_tree=null;
        RewriteRuleTokenStream stream_RPAREN=new RewriteRuleTokenStream(adaptor,"token RPAREN");
        RewriteRuleTokenStream stream_FOREIGN=new RewriteRuleTokenStream(adaptor,"token FOREIGN");
        RewriteRuleTokenStream stream_COMMA=new RewriteRuleTokenStream(adaptor,"token COMMA");
        RewriteRuleTokenStream stream_KEY=new RewriteRuleTokenStream(adaptor,"token KEY");
        RewriteRuleTokenStream stream_LPAREN=new RewriteRuleTokenStream(adaptor,"token LPAREN");
        RewriteRuleSubtreeStream stream_id=new RewriteRuleSubtreeStream(adaptor,"rule id");
        RewriteRuleSubtreeStream stream_fk_clause=new RewriteRuleSubtreeStream(adaptor,"rule fk_clause");
        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:412:20: ( FOREIGN KEY LPAREN column_names+= id ( COMMA column_names+= id )* RPAREN fk_clause -> ^( FOREIGN ^( COLUMNS ( $column_names)+ ) fk_clause ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:412:22: FOREIGN KEY LPAREN column_names+= id ( COMMA column_names+= id )* RPAREN fk_clause
            {
            FOREIGN379=(Token)match(input,FOREIGN,FOLLOW_FOREIGN_in_table_constraint_fk3331);  
            stream_FOREIGN.add(FOREIGN379);

            KEY380=(Token)match(input,KEY,FOLLOW_KEY_in_table_constraint_fk3333);  
            stream_KEY.add(KEY380);

            LPAREN381=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_table_constraint_fk3335);  
            stream_LPAREN.add(LPAREN381);

            pushFollow(FOLLOW_id_in_table_constraint_fk3339);
            column_names=id();

            state._fsp--;

            stream_id.add(column_names.getTree());
            if (list_column_names==null) list_column_names=new ArrayList();
            list_column_names.add(column_names.getTree());

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:412:58: ( COMMA column_names+= id )*
            loop139:
            do {
                int alt139=2;
                int LA139_0 = input.LA(1);

                if ( (LA139_0==COMMA) ) {
                    alt139=1;
                }


                switch (alt139) {
            	case 1 :
            	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:412:59: COMMA column_names+= id
            	    {
            	    COMMA382=(Token)match(input,COMMA,FOLLOW_COMMA_in_table_constraint_fk3342);  
            	    stream_COMMA.add(COMMA382);

            	    pushFollow(FOLLOW_id_in_table_constraint_fk3346);
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

            RPAREN383=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_table_constraint_fk3351);  
            stream_RPAREN.add(RPAREN383);

            pushFollow(FOLLOW_fk_clause_in_table_constraint_fk3353);
            fk_clause384=fk_clause();

            state._fsp--;

            stream_fk_clause.add(fk_clause384.getTree());


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
            // 413:1: -> ^( FOREIGN ^( COLUMNS ( $column_names)+ ) fk_clause )
            {
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:413:4: ^( FOREIGN ^( COLUMNS ( $column_names)+ ) fk_clause )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(stream_FOREIGN.nextNode(), root_1);

                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:413:14: ^( COLUMNS ( $column_names)+ )
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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:415:1: fk_clause : REFERENCES foreign_table= id ( LPAREN column_names+= id ( COMMA column_names+= id )* RPAREN )? ( fk_clause_action )* ( fk_clause_deferrable )? -> ^( REFERENCES $foreign_table ^( COLUMNS ( $column_names)+ ) ( fk_clause_action )* ( fk_clause_deferrable )? ) ;
    public final SqlParser.fk_clause_return fk_clause() throws RecognitionException {
        SqlParser.fk_clause_return retval = new SqlParser.fk_clause_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token REFERENCES385=null;
        Token LPAREN386=null;
        Token COMMA387=null;
        Token RPAREN388=null;
        List list_column_names=null;
        SqlParser.id_return foreign_table = null;

        SqlParser.fk_clause_action_return fk_clause_action389 = null;

        SqlParser.fk_clause_deferrable_return fk_clause_deferrable390 = null;

        SqlParser.id_return column_names = null;
         column_names = null;
        Object REFERENCES385_tree=null;
        Object LPAREN386_tree=null;
        Object COMMA387_tree=null;
        Object RPAREN388_tree=null;
        RewriteRuleTokenStream stream_RPAREN=new RewriteRuleTokenStream(adaptor,"token RPAREN");
        RewriteRuleTokenStream stream_REFERENCES=new RewriteRuleTokenStream(adaptor,"token REFERENCES");
        RewriteRuleTokenStream stream_COMMA=new RewriteRuleTokenStream(adaptor,"token COMMA");
        RewriteRuleTokenStream stream_LPAREN=new RewriteRuleTokenStream(adaptor,"token LPAREN");
        RewriteRuleSubtreeStream stream_id=new RewriteRuleSubtreeStream(adaptor,"rule id");
        RewriteRuleSubtreeStream stream_fk_clause_action=new RewriteRuleSubtreeStream(adaptor,"rule fk_clause_action");
        RewriteRuleSubtreeStream stream_fk_clause_deferrable=new RewriteRuleSubtreeStream(adaptor,"rule fk_clause_deferrable");
        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:415:10: ( REFERENCES foreign_table= id ( LPAREN column_names+= id ( COMMA column_names+= id )* RPAREN )? ( fk_clause_action )* ( fk_clause_deferrable )? -> ^( REFERENCES $foreign_table ^( COLUMNS ( $column_names)+ ) ( fk_clause_action )* ( fk_clause_deferrable )? ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:415:12: REFERENCES foreign_table= id ( LPAREN column_names+= id ( COMMA column_names+= id )* RPAREN )? ( fk_clause_action )* ( fk_clause_deferrable )?
            {
            REFERENCES385=(Token)match(input,REFERENCES,FOLLOW_REFERENCES_in_fk_clause3376);  
            stream_REFERENCES.add(REFERENCES385);

            pushFollow(FOLLOW_id_in_fk_clause3380);
            foreign_table=id();

            state._fsp--;

            stream_id.add(foreign_table.getTree());
             builder.addReferenceTo( foreign_table.getTree().toString() ); 
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:415:106: ( LPAREN column_names+= id ( COMMA column_names+= id )* RPAREN )?
            int alt141=2;
            alt141 = dfa141.predict(input);
            switch (alt141) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:415:107: LPAREN column_names+= id ( COMMA column_names+= id )* RPAREN
                    {
                    LPAREN386=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_fk_clause3385);  
                    stream_LPAREN.add(LPAREN386);

                    pushFollow(FOLLOW_id_in_fk_clause3389);
                    column_names=id();

                    state._fsp--;

                    stream_id.add(column_names.getTree());
                    if (list_column_names==null) list_column_names=new ArrayList();
                    list_column_names.add(column_names.getTree());

                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:415:131: ( COMMA column_names+= id )*
                    loop140:
                    do {
                        int alt140=2;
                        int LA140_0 = input.LA(1);

                        if ( (LA140_0==COMMA) ) {
                            alt140=1;
                        }


                        switch (alt140) {
                    	case 1 :
                    	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:415:132: COMMA column_names+= id
                    	    {
                    	    COMMA387=(Token)match(input,COMMA,FOLLOW_COMMA_in_fk_clause3392);  
                    	    stream_COMMA.add(COMMA387);

                    	    pushFollow(FOLLOW_id_in_fk_clause3396);
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

                    RPAREN388=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_fk_clause3400);  
                    stream_RPAREN.add(RPAREN388);


                    }
                    break;

            }

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:416:3: ( fk_clause_action )*
            loop142:
            do {
                int alt142=2;
                alt142 = dfa142.predict(input);
                switch (alt142) {
            	case 1 :
            	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:416:3: fk_clause_action
            	    {
            	    pushFollow(FOLLOW_fk_clause_action_in_fk_clause3407);
            	    fk_clause_action389=fk_clause_action();

            	    state._fsp--;

            	    stream_fk_clause_action.add(fk_clause_action389.getTree());

            	    }
            	    break;

            	default :
            	    break loop142;
                }
            } while (true);

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:416:21: ( fk_clause_deferrable )?
            int alt143=2;
            alt143 = dfa143.predict(input);
            switch (alt143) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:416:21: fk_clause_deferrable
                    {
                    pushFollow(FOLLOW_fk_clause_deferrable_in_fk_clause3410);
                    fk_clause_deferrable390=fk_clause_deferrable();

                    state._fsp--;

                    stream_fk_clause_deferrable.add(fk_clause_deferrable390.getTree());

                    }
                    break;

            }



            // AST REWRITE
            // elements: foreign_table, fk_clause_action, column_names, REFERENCES, fk_clause_deferrable
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
            // 417:1: -> ^( REFERENCES $foreign_table ^( COLUMNS ( $column_names)+ ) ( fk_clause_action )* ( fk_clause_deferrable )? )
            {
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:417:4: ^( REFERENCES $foreign_table ^( COLUMNS ( $column_names)+ ) ( fk_clause_action )* ( fk_clause_deferrable )? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(stream_REFERENCES.nextNode(), root_1);

                adaptor.addChild(root_1, stream_foreign_table.nextTree());
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:417:32: ^( COLUMNS ( $column_names)+ )
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
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:417:58: ( fk_clause_action )*
                while ( stream_fk_clause_action.hasNext() ) {
                    adaptor.addChild(root_1, stream_fk_clause_action.nextTree());

                }
                stream_fk_clause_action.reset();
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:417:76: ( fk_clause_deferrable )?
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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:419:1: fk_clause_action : ( ON ( DELETE | UPDATE | INSERT ) ( SET NULL | SET DEFAULT | CASCADE | RESTRICT ) | MATCH id );
    public final SqlParser.fk_clause_action_return fk_clause_action() throws RecognitionException {
        SqlParser.fk_clause_action_return retval = new SqlParser.fk_clause_action_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ON391=null;
        Token set392=null;
        Token SET393=null;
        Token NULL394=null;
        Token SET395=null;
        Token DEFAULT396=null;
        Token CASCADE397=null;
        Token RESTRICT398=null;
        Token MATCH399=null;
        SqlParser.id_return id400 = null;


        Object ON391_tree=null;
        Object set392_tree=null;
        Object SET393_tree=null;
        Object NULL394_tree=null;
        Object SET395_tree=null;
        Object DEFAULT396_tree=null;
        Object CASCADE397_tree=null;
        Object RESTRICT398_tree=null;
        Object MATCH399_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:420:3: ( ON ( DELETE | UPDATE | INSERT ) ( SET NULL | SET DEFAULT | CASCADE | RESTRICT ) | MATCH id )
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
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:420:5: ON ( DELETE | UPDATE | INSERT ) ( SET NULL | SET DEFAULT | CASCADE | RESTRICT )
                    {
                    root_0 = (Object)adaptor.nil();

                    ON391=(Token)match(input,ON,FOLLOW_ON_in_fk_clause_action3444); 
                    ON391_tree = (Object)adaptor.create(ON391);
                    root_0 = (Object)adaptor.becomeRoot(ON391_tree, root_0);

                    set392=(Token)input.LT(1);
                    if ( input.LA(1)==INSERT||input.LA(1)==UPDATE||input.LA(1)==DELETE ) {
                        input.consume();
                        adaptor.addChild(root_0, (Object)adaptor.create(set392));
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:420:36: ( SET NULL | SET DEFAULT | CASCADE | RESTRICT )
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
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:420:37: SET NULL
                            {
                            SET393=(Token)match(input,SET,FOLLOW_SET_in_fk_clause_action3460); 
                            NULL394=(Token)match(input,NULL,FOLLOW_NULL_in_fk_clause_action3463); 
                            NULL394_tree = (Object)adaptor.create(NULL394);
                            adaptor.addChild(root_0, NULL394_tree);


                            }
                            break;
                        case 2 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:420:49: SET DEFAULT
                            {
                            SET395=(Token)match(input,SET,FOLLOW_SET_in_fk_clause_action3467); 
                            DEFAULT396=(Token)match(input,DEFAULT,FOLLOW_DEFAULT_in_fk_clause_action3470); 
                            DEFAULT396_tree = (Object)adaptor.create(DEFAULT396);
                            adaptor.addChild(root_0, DEFAULT396_tree);


                            }
                            break;
                        case 3 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:420:64: CASCADE
                            {
                            CASCADE397=(Token)match(input,CASCADE,FOLLOW_CASCADE_in_fk_clause_action3474); 
                            CASCADE397_tree = (Object)adaptor.create(CASCADE397);
                            adaptor.addChild(root_0, CASCADE397_tree);


                            }
                            break;
                        case 4 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:420:74: RESTRICT
                            {
                            RESTRICT398=(Token)match(input,RESTRICT,FOLLOW_RESTRICT_in_fk_clause_action3478); 
                            RESTRICT398_tree = (Object)adaptor.create(RESTRICT398);
                            adaptor.addChild(root_0, RESTRICT398_tree);


                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:421:5: MATCH id
                    {
                    root_0 = (Object)adaptor.nil();

                    MATCH399=(Token)match(input,MATCH,FOLLOW_MATCH_in_fk_clause_action3485); 
                    MATCH399_tree = (Object)adaptor.create(MATCH399);
                    root_0 = (Object)adaptor.becomeRoot(MATCH399_tree, root_0);

                    pushFollow(FOLLOW_id_in_fk_clause_action3488);
                    id400=id();

                    state._fsp--;

                    adaptor.addChild(root_0, id400.getTree());

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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:423:1: fk_clause_deferrable : ( NOT )? DEFERRABLE ( INITIALLY DEFERRED | INITIALLY IMMEDIATE )? ;
    public final SqlParser.fk_clause_deferrable_return fk_clause_deferrable() throws RecognitionException {
        SqlParser.fk_clause_deferrable_return retval = new SqlParser.fk_clause_deferrable_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token NOT401=null;
        Token DEFERRABLE402=null;
        Token INITIALLY403=null;
        Token DEFERRED404=null;
        Token INITIALLY405=null;
        Token IMMEDIATE406=null;

        Object NOT401_tree=null;
        Object DEFERRABLE402_tree=null;
        Object INITIALLY403_tree=null;
        Object DEFERRED404_tree=null;
        Object INITIALLY405_tree=null;
        Object IMMEDIATE406_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:423:21: ( ( NOT )? DEFERRABLE ( INITIALLY DEFERRED | INITIALLY IMMEDIATE )? )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:423:23: ( NOT )? DEFERRABLE ( INITIALLY DEFERRED | INITIALLY IMMEDIATE )?
            {
            root_0 = (Object)adaptor.nil();

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:423:23: ( NOT )?
            int alt146=2;
            int LA146_0 = input.LA(1);

            if ( (LA146_0==NOT) ) {
                alt146=1;
            }
            switch (alt146) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:423:24: NOT
                    {
                    NOT401=(Token)match(input,NOT,FOLLOW_NOT_in_fk_clause_deferrable3496); 
                    NOT401_tree = (Object)adaptor.create(NOT401);
                    adaptor.addChild(root_0, NOT401_tree);


                    }
                    break;

            }

            DEFERRABLE402=(Token)match(input,DEFERRABLE,FOLLOW_DEFERRABLE_in_fk_clause_deferrable3500); 
            DEFERRABLE402_tree = (Object)adaptor.create(DEFERRABLE402);
            root_0 = (Object)adaptor.becomeRoot(DEFERRABLE402_tree, root_0);

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:423:42: ( INITIALLY DEFERRED | INITIALLY IMMEDIATE )?
            int alt147=3;
            alt147 = dfa147.predict(input);
            switch (alt147) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:423:43: INITIALLY DEFERRED
                    {
                    INITIALLY403=(Token)match(input,INITIALLY,FOLLOW_INITIALLY_in_fk_clause_deferrable3504); 
                    DEFERRED404=(Token)match(input,DEFERRED,FOLLOW_DEFERRED_in_fk_clause_deferrable3507); 
                    DEFERRED404_tree = (Object)adaptor.create(DEFERRED404);
                    adaptor.addChild(root_0, DEFERRED404_tree);


                    }
                    break;
                case 2 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:423:65: INITIALLY IMMEDIATE
                    {
                    INITIALLY405=(Token)match(input,INITIALLY,FOLLOW_INITIALLY_in_fk_clause_deferrable3511); 
                    IMMEDIATE406=(Token)match(input,IMMEDIATE,FOLLOW_IMMEDIATE_in_fk_clause_deferrable3514); 
                    IMMEDIATE406_tree = (Object)adaptor.create(IMMEDIATE406);
                    adaptor.addChild(root_0, IMMEDIATE406_tree);


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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:426:1: drop_table_stmt : DROP TABLE ( IF EXISTS )? (database_name= id DOT )? table_name= id -> ^( DROP_TABLE ^( OPTIONS ( EXISTS )? ) ^( $table_name ( $database_name)? ) ) ;
    public final SqlParser.drop_table_stmt_return drop_table_stmt() throws RecognitionException {
        SqlParser.drop_table_stmt_return retval = new SqlParser.drop_table_stmt_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token DROP407=null;
        Token TABLE408=null;
        Token IF409=null;
        Token EXISTS410=null;
        Token DOT411=null;
        SqlParser.id_return database_name = null;

        SqlParser.id_return table_name = null;


        Object DROP407_tree=null;
        Object TABLE408_tree=null;
        Object IF409_tree=null;
        Object EXISTS410_tree=null;
        Object DOT411_tree=null;
        RewriteRuleTokenStream stream_TABLE=new RewriteRuleTokenStream(adaptor,"token TABLE");
        RewriteRuleTokenStream stream_EXISTS=new RewriteRuleTokenStream(adaptor,"token EXISTS");
        RewriteRuleTokenStream stream_DROP=new RewriteRuleTokenStream(adaptor,"token DROP");
        RewriteRuleTokenStream stream_DOT=new RewriteRuleTokenStream(adaptor,"token DOT");
        RewriteRuleTokenStream stream_IF=new RewriteRuleTokenStream(adaptor,"token IF");
        RewriteRuleSubtreeStream stream_id=new RewriteRuleSubtreeStream(adaptor,"rule id");
        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:426:16: ( DROP TABLE ( IF EXISTS )? (database_name= id DOT )? table_name= id -> ^( DROP_TABLE ^( OPTIONS ( EXISTS )? ) ^( $table_name ( $database_name)? ) ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:426:18: DROP TABLE ( IF EXISTS )? (database_name= id DOT )? table_name= id
            {
            DROP407=(Token)match(input,DROP,FOLLOW_DROP_in_drop_table_stmt3524);  
            stream_DROP.add(DROP407);

            TABLE408=(Token)match(input,TABLE,FOLLOW_TABLE_in_drop_table_stmt3526);  
            stream_TABLE.add(TABLE408);

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:426:29: ( IF EXISTS )?
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
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:426:30: IF EXISTS
                    {
                    IF409=(Token)match(input,IF,FOLLOW_IF_in_drop_table_stmt3529);  
                    stream_IF.add(IF409);

                    EXISTS410=(Token)match(input,EXISTS,FOLLOW_EXISTS_in_drop_table_stmt3531);  
                    stream_EXISTS.add(EXISTS410);


                    }
                    break;

            }

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:426:42: (database_name= id DOT )?
            int alt149=2;
            int LA149_0 = input.LA(1);

            if ( (LA149_0==ID) ) {
                int LA149_1 = input.LA(2);

                if ( (LA149_1==DOT) ) {
                    alt149=1;
                }
            }
            else if ( ((LA149_0>=EXPLAIN && LA149_0<=PLAN)||(LA149_0>=INDEXED && LA149_0<=BY)||(LA149_0>=OR && LA149_0<=ESCAPE)||(LA149_0>=IS && LA149_0<=BETWEEN)||LA149_0==COLLATE||(LA149_0>=DISTINCT && LA149_0<=THEN)||(LA149_0>=CURRENT_TIME && LA149_0<=CURRENT_TIMESTAMP)||(LA149_0>=RAISE && LA149_0<=FAIL)||(LA149_0>=PRAGMA && LA149_0<=FOR)||(LA149_0>=UNIQUE && LA149_0<=ROW)) ) {
                int LA149_2 = input.LA(2);

                if ( (LA149_2==DOT) ) {
                    alt149=1;
                }
            }
            switch (alt149) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:426:43: database_name= id DOT
                    {
                    pushFollow(FOLLOW_id_in_drop_table_stmt3538);
                    database_name=id();

                    state._fsp--;

                    stream_id.add(database_name.getTree());
                    DOT411=(Token)match(input,DOT,FOLLOW_DOT_in_drop_table_stmt3540);  
                    stream_DOT.add(DOT411);


                    }
                    break;

            }

            pushFollow(FOLLOW_id_in_drop_table_stmt3546);
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
            // 427:1: -> ^( DROP_TABLE ^( OPTIONS ( EXISTS )? ) ^( $table_name ( $database_name)? ) )
            {
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:427:4: ^( DROP_TABLE ^( OPTIONS ( EXISTS )? ) ^( $table_name ( $database_name)? ) )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(DROP_TABLE, "DROP_TABLE"), root_1);

                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:427:17: ^( OPTIONS ( EXISTS )? )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(OPTIONS, "OPTIONS"), root_2);

                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:427:27: ( EXISTS )?
                if ( stream_EXISTS.hasNext() ) {
                    adaptor.addChild(root_2, stream_EXISTS.nextNode());

                }
                stream_EXISTS.reset();

                adaptor.addChild(root_1, root_2);
                }
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:427:36: ^( $table_name ( $database_name)? )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot(stream_table_name.nextNode(), root_2);

                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:427:50: ( $database_name)?
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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:430:1: alter_table_stmt : ALTER TABLE (database_name= id DOT )? table_name= id ( RENAME TO new_table_name= id | ADD ( COLUMN )? column_def ) ;
    public final SqlParser.alter_table_stmt_return alter_table_stmt() throws RecognitionException {
        SqlParser.alter_table_stmt_return retval = new SqlParser.alter_table_stmt_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ALTER412=null;
        Token TABLE413=null;
        Token DOT414=null;
        Token RENAME415=null;
        Token TO416=null;
        Token ADD417=null;
        Token COLUMN418=null;
        SqlParser.id_return database_name = null;

        SqlParser.id_return table_name = null;

        SqlParser.id_return new_table_name = null;

        SqlParser.column_def_return column_def419 = null;


        Object ALTER412_tree=null;
        Object TABLE413_tree=null;
        Object DOT414_tree=null;
        Object RENAME415_tree=null;
        Object TO416_tree=null;
        Object ADD417_tree=null;
        Object COLUMN418_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:430:17: ( ALTER TABLE (database_name= id DOT )? table_name= id ( RENAME TO new_table_name= id | ADD ( COLUMN )? column_def ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:430:19: ALTER TABLE (database_name= id DOT )? table_name= id ( RENAME TO new_table_name= id | ADD ( COLUMN )? column_def )
            {
            root_0 = (Object)adaptor.nil();

            ALTER412=(Token)match(input,ALTER,FOLLOW_ALTER_in_alter_table_stmt3576); 
            ALTER412_tree = (Object)adaptor.create(ALTER412);
            adaptor.addChild(root_0, ALTER412_tree);

            TABLE413=(Token)match(input,TABLE,FOLLOW_TABLE_in_alter_table_stmt3578); 
            TABLE413_tree = (Object)adaptor.create(TABLE413);
            adaptor.addChild(root_0, TABLE413_tree);

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:430:31: (database_name= id DOT )?
            int alt150=2;
            int LA150_0 = input.LA(1);

            if ( (LA150_0==ID) ) {
                int LA150_1 = input.LA(2);

                if ( (LA150_1==DOT) ) {
                    alt150=1;
                }
            }
            else if ( ((LA150_0>=EXPLAIN && LA150_0<=PLAN)||(LA150_0>=INDEXED && LA150_0<=BY)||(LA150_0>=OR && LA150_0<=ESCAPE)||(LA150_0>=IS && LA150_0<=BETWEEN)||LA150_0==COLLATE||(LA150_0>=DISTINCT && LA150_0<=THEN)||(LA150_0>=CURRENT_TIME && LA150_0<=CURRENT_TIMESTAMP)||(LA150_0>=RAISE && LA150_0<=FAIL)||(LA150_0>=PRAGMA && LA150_0<=FOR)||(LA150_0>=UNIQUE && LA150_0<=ROW)) ) {
                int LA150_2 = input.LA(2);

                if ( (LA150_2==DOT) ) {
                    alt150=1;
                }
            }
            switch (alt150) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:430:32: database_name= id DOT
                    {
                    pushFollow(FOLLOW_id_in_alter_table_stmt3583);
                    database_name=id();

                    state._fsp--;

                    adaptor.addChild(root_0, database_name.getTree());
                    DOT414=(Token)match(input,DOT,FOLLOW_DOT_in_alter_table_stmt3585); 
                    DOT414_tree = (Object)adaptor.create(DOT414);
                    adaptor.addChild(root_0, DOT414_tree);


                    }
                    break;

            }

            pushFollow(FOLLOW_id_in_alter_table_stmt3591);
            table_name=id();

            state._fsp--;

            adaptor.addChild(root_0, table_name.getTree());
             builder.setEntityTo( table_name.getTree().toString()); 
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:430:128: ( RENAME TO new_table_name= id | ADD ( COLUMN )? column_def )
            int alt152=2;
            int LA152_0 = input.LA(1);

            if ( (LA152_0==RENAME) ) {
                alt152=1;
            }
            else if ( (LA152_0==ADD) ) {
                alt152=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 152, 0, input);

                throw nvae;
            }
            switch (alt152) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:430:129: RENAME TO new_table_name= id
                    {
                    RENAME415=(Token)match(input,RENAME,FOLLOW_RENAME_in_alter_table_stmt3596); 
                    RENAME415_tree = (Object)adaptor.create(RENAME415);
                    adaptor.addChild(root_0, RENAME415_tree);

                    TO416=(Token)match(input,TO,FOLLOW_TO_in_alter_table_stmt3598); 
                    TO416_tree = (Object)adaptor.create(TO416);
                    adaptor.addChild(root_0, TO416_tree);

                    pushFollow(FOLLOW_id_in_alter_table_stmt3602);
                    new_table_name=id();

                    state._fsp--;

                    adaptor.addChild(root_0, new_table_name.getTree());

                    }
                    break;
                case 2 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:430:159: ADD ( COLUMN )? column_def
                    {
                    ADD417=(Token)match(input,ADD,FOLLOW_ADD_in_alter_table_stmt3606); 
                    ADD417_tree = (Object)adaptor.create(ADD417);
                    adaptor.addChild(root_0, ADD417_tree);

                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:430:163: ( COLUMN )?
                    int alt151=2;
                    int LA151_0 = input.LA(1);

                    if ( (LA151_0==COLUMN) ) {
                        alt151=1;
                    }
                    switch (alt151) {
                        case 1 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:430:164: COLUMN
                            {
                            COLUMN418=(Token)match(input,COLUMN,FOLLOW_COLUMN_in_alter_table_stmt3609); 
                            COLUMN418_tree = (Object)adaptor.create(COLUMN418);
                            adaptor.addChild(root_0, COLUMN418_tree);


                            }
                            break;

                    }

                    pushFollow(FOLLOW_column_def_in_alter_table_stmt3613);
                    column_def419=column_def();

                    state._fsp--;

                    adaptor.addChild(root_0, column_def419.getTree());

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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:433:1: create_view_stmt : CREATE ( TEMPORARY )? VIEW ( IF NOT EXISTS )? (database_name= id DOT )? view_name= id AS select_stmt ;
    public final SqlParser.create_view_stmt_return create_view_stmt() throws RecognitionException {
        SqlParser.create_view_stmt_return retval = new SqlParser.create_view_stmt_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token CREATE420=null;
        Token TEMPORARY421=null;
        Token VIEW422=null;
        Token IF423=null;
        Token NOT424=null;
        Token EXISTS425=null;
        Token DOT426=null;
        Token AS427=null;
        SqlParser.id_return database_name = null;

        SqlParser.id_return view_name = null;

        SqlParser.select_stmt_return select_stmt428 = null;


        Object CREATE420_tree=null;
        Object TEMPORARY421_tree=null;
        Object VIEW422_tree=null;
        Object IF423_tree=null;
        Object NOT424_tree=null;
        Object EXISTS425_tree=null;
        Object DOT426_tree=null;
        Object AS427_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:433:17: ( CREATE ( TEMPORARY )? VIEW ( IF NOT EXISTS )? (database_name= id DOT )? view_name= id AS select_stmt )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:433:19: CREATE ( TEMPORARY )? VIEW ( IF NOT EXISTS )? (database_name= id DOT )? view_name= id AS select_stmt
            {
            root_0 = (Object)adaptor.nil();

            CREATE420=(Token)match(input,CREATE,FOLLOW_CREATE_in_create_view_stmt3622); 
            CREATE420_tree = (Object)adaptor.create(CREATE420);
            adaptor.addChild(root_0, CREATE420_tree);

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:433:26: ( TEMPORARY )?
            int alt153=2;
            int LA153_0 = input.LA(1);

            if ( (LA153_0==TEMPORARY) ) {
                alt153=1;
            }
            switch (alt153) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:433:26: TEMPORARY
                    {
                    TEMPORARY421=(Token)match(input,TEMPORARY,FOLLOW_TEMPORARY_in_create_view_stmt3624); 
                    TEMPORARY421_tree = (Object)adaptor.create(TEMPORARY421);
                    adaptor.addChild(root_0, TEMPORARY421_tree);


                    }
                    break;

            }

            VIEW422=(Token)match(input,VIEW,FOLLOW_VIEW_in_create_view_stmt3627); 
            VIEW422_tree = (Object)adaptor.create(VIEW422);
            adaptor.addChild(root_0, VIEW422_tree);

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:433:42: ( IF NOT EXISTS )?
            int alt154=2;
            int LA154_0 = input.LA(1);

            if ( (LA154_0==IF) ) {
                int LA154_1 = input.LA(2);

                if ( (LA154_1==NOT) ) {
                    alt154=1;
                }
            }
            switch (alt154) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:433:43: IF NOT EXISTS
                    {
                    IF423=(Token)match(input,IF,FOLLOW_IF_in_create_view_stmt3630); 
                    IF423_tree = (Object)adaptor.create(IF423);
                    adaptor.addChild(root_0, IF423_tree);

                    NOT424=(Token)match(input,NOT,FOLLOW_NOT_in_create_view_stmt3632); 
                    NOT424_tree = (Object)adaptor.create(NOT424);
                    adaptor.addChild(root_0, NOT424_tree);

                    EXISTS425=(Token)match(input,EXISTS,FOLLOW_EXISTS_in_create_view_stmt3634); 
                    EXISTS425_tree = (Object)adaptor.create(EXISTS425);
                    adaptor.addChild(root_0, EXISTS425_tree);


                    }
                    break;

            }

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:433:59: (database_name= id DOT )?
            int alt155=2;
            int LA155_0 = input.LA(1);

            if ( (LA155_0==ID) ) {
                int LA155_1 = input.LA(2);

                if ( (LA155_1==DOT) ) {
                    alt155=1;
                }
            }
            else if ( ((LA155_0>=EXPLAIN && LA155_0<=PLAN)||(LA155_0>=INDEXED && LA155_0<=BY)||(LA155_0>=OR && LA155_0<=ESCAPE)||(LA155_0>=IS && LA155_0<=BETWEEN)||LA155_0==COLLATE||(LA155_0>=DISTINCT && LA155_0<=THEN)||(LA155_0>=CURRENT_TIME && LA155_0<=CURRENT_TIMESTAMP)||(LA155_0>=RAISE && LA155_0<=FAIL)||(LA155_0>=PRAGMA && LA155_0<=FOR)||(LA155_0>=UNIQUE && LA155_0<=ROW)) ) {
                int LA155_2 = input.LA(2);

                if ( (LA155_2==DOT) ) {
                    alt155=1;
                }
            }
            switch (alt155) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:433:60: database_name= id DOT
                    {
                    pushFollow(FOLLOW_id_in_create_view_stmt3641);
                    database_name=id();

                    state._fsp--;

                    adaptor.addChild(root_0, database_name.getTree());
                    DOT426=(Token)match(input,DOT,FOLLOW_DOT_in_create_view_stmt3643); 
                    DOT426_tree = (Object)adaptor.create(DOT426);
                    adaptor.addChild(root_0, DOT426_tree);


                    }
                    break;

            }

            pushFollow(FOLLOW_id_in_create_view_stmt3649);
            view_name=id();

            state._fsp--;

            adaptor.addChild(root_0, view_name.getTree());
            AS427=(Token)match(input,AS,FOLLOW_AS_in_create_view_stmt3651); 
            AS427_tree = (Object)adaptor.create(AS427);
            adaptor.addChild(root_0, AS427_tree);

            pushFollow(FOLLOW_select_stmt_in_create_view_stmt3653);
            select_stmt428=select_stmt();

            state._fsp--;

            adaptor.addChild(root_0, select_stmt428.getTree());

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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:436:1: drop_view_stmt : DROP VIEW ( IF EXISTS )? (database_name= id DOT )? view_name= id ;
    public final SqlParser.drop_view_stmt_return drop_view_stmt() throws RecognitionException {
        SqlParser.drop_view_stmt_return retval = new SqlParser.drop_view_stmt_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token DROP429=null;
        Token VIEW430=null;
        Token IF431=null;
        Token EXISTS432=null;
        Token DOT433=null;
        SqlParser.id_return database_name = null;

        SqlParser.id_return view_name = null;


        Object DROP429_tree=null;
        Object VIEW430_tree=null;
        Object IF431_tree=null;
        Object EXISTS432_tree=null;
        Object DOT433_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:436:15: ( DROP VIEW ( IF EXISTS )? (database_name= id DOT )? view_name= id )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:436:17: DROP VIEW ( IF EXISTS )? (database_name= id DOT )? view_name= id
            {
            root_0 = (Object)adaptor.nil();

            DROP429=(Token)match(input,DROP,FOLLOW_DROP_in_drop_view_stmt3661); 
            DROP429_tree = (Object)adaptor.create(DROP429);
            adaptor.addChild(root_0, DROP429_tree);

            VIEW430=(Token)match(input,VIEW,FOLLOW_VIEW_in_drop_view_stmt3663); 
            VIEW430_tree = (Object)adaptor.create(VIEW430);
            adaptor.addChild(root_0, VIEW430_tree);

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:436:27: ( IF EXISTS )?
            int alt156=2;
            int LA156_0 = input.LA(1);

            if ( (LA156_0==IF) ) {
                int LA156_1 = input.LA(2);

                if ( (LA156_1==EXISTS) ) {
                    alt156=1;
                }
            }
            switch (alt156) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:436:28: IF EXISTS
                    {
                    IF431=(Token)match(input,IF,FOLLOW_IF_in_drop_view_stmt3666); 
                    IF431_tree = (Object)adaptor.create(IF431);
                    adaptor.addChild(root_0, IF431_tree);

                    EXISTS432=(Token)match(input,EXISTS,FOLLOW_EXISTS_in_drop_view_stmt3668); 
                    EXISTS432_tree = (Object)adaptor.create(EXISTS432);
                    adaptor.addChild(root_0, EXISTS432_tree);


                    }
                    break;

            }

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:436:40: (database_name= id DOT )?
            int alt157=2;
            int LA157_0 = input.LA(1);

            if ( (LA157_0==ID) ) {
                int LA157_1 = input.LA(2);

                if ( (LA157_1==DOT) ) {
                    alt157=1;
                }
            }
            else if ( ((LA157_0>=EXPLAIN && LA157_0<=PLAN)||(LA157_0>=INDEXED && LA157_0<=BY)||(LA157_0>=OR && LA157_0<=ESCAPE)||(LA157_0>=IS && LA157_0<=BETWEEN)||LA157_0==COLLATE||(LA157_0>=DISTINCT && LA157_0<=THEN)||(LA157_0>=CURRENT_TIME && LA157_0<=CURRENT_TIMESTAMP)||(LA157_0>=RAISE && LA157_0<=FAIL)||(LA157_0>=PRAGMA && LA157_0<=FOR)||(LA157_0>=UNIQUE && LA157_0<=ROW)) ) {
                int LA157_2 = input.LA(2);

                if ( (LA157_2==DOT) ) {
                    alt157=1;
                }
            }
            switch (alt157) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:436:41: database_name= id DOT
                    {
                    pushFollow(FOLLOW_id_in_drop_view_stmt3675);
                    database_name=id();

                    state._fsp--;

                    adaptor.addChild(root_0, database_name.getTree());
                    DOT433=(Token)match(input,DOT,FOLLOW_DOT_in_drop_view_stmt3677); 
                    DOT433_tree = (Object)adaptor.create(DOT433);
                    adaptor.addChild(root_0, DOT433_tree);


                    }
                    break;

            }

            pushFollow(FOLLOW_id_in_drop_view_stmt3683);
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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:439:1: create_index_stmt : CREATE ( UNIQUE )? INDEX ( IF NOT EXISTS )? (database_name= id DOT )? index_name= id ON table_name= id LPAREN columns+= indexed_column ( COMMA columns+= indexed_column )* RPAREN -> ^( CREATE_INDEX ^( OPTIONS ( UNIQUE )? ( EXISTS )? ) ^( $index_name ( $database_name)? ) $table_name ( ^( COLUMNS ( $columns)+ ) )? ) ;
    public final SqlParser.create_index_stmt_return create_index_stmt() throws RecognitionException {
        SqlParser.create_index_stmt_return retval = new SqlParser.create_index_stmt_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token CREATE434=null;
        Token UNIQUE435=null;
        Token INDEX436=null;
        Token IF437=null;
        Token NOT438=null;
        Token EXISTS439=null;
        Token DOT440=null;
        Token ON441=null;
        Token LPAREN442=null;
        Token COMMA443=null;
        Token RPAREN444=null;
        List list_columns=null;
        SqlParser.id_return database_name = null;

        SqlParser.id_return index_name = null;

        SqlParser.id_return table_name = null;

        SqlParser.indexed_column_return columns = null;
         columns = null;
        Object CREATE434_tree=null;
        Object UNIQUE435_tree=null;
        Object INDEX436_tree=null;
        Object IF437_tree=null;
        Object NOT438_tree=null;
        Object EXISTS439_tree=null;
        Object DOT440_tree=null;
        Object ON441_tree=null;
        Object LPAREN442_tree=null;
        Object COMMA443_tree=null;
        Object RPAREN444_tree=null;
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
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:439:18: ( CREATE ( UNIQUE )? INDEX ( IF NOT EXISTS )? (database_name= id DOT )? index_name= id ON table_name= id LPAREN columns+= indexed_column ( COMMA columns+= indexed_column )* RPAREN -> ^( CREATE_INDEX ^( OPTIONS ( UNIQUE )? ( EXISTS )? ) ^( $index_name ( $database_name)? ) $table_name ( ^( COLUMNS ( $columns)+ ) )? ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:439:20: CREATE ( UNIQUE )? INDEX ( IF NOT EXISTS )? (database_name= id DOT )? index_name= id ON table_name= id LPAREN columns+= indexed_column ( COMMA columns+= indexed_column )* RPAREN
            {
            CREATE434=(Token)match(input,CREATE,FOLLOW_CREATE_in_create_index_stmt3691);  
            stream_CREATE.add(CREATE434);

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:439:27: ( UNIQUE )?
            int alt158=2;
            int LA158_0 = input.LA(1);

            if ( (LA158_0==UNIQUE) ) {
                alt158=1;
            }
            switch (alt158) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:439:28: UNIQUE
                    {
                    UNIQUE435=(Token)match(input,UNIQUE,FOLLOW_UNIQUE_in_create_index_stmt3694);  
                    stream_UNIQUE.add(UNIQUE435);


                    }
                    break;

            }

            INDEX436=(Token)match(input,INDEX,FOLLOW_INDEX_in_create_index_stmt3698);  
            stream_INDEX.add(INDEX436);

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:439:43: ( IF NOT EXISTS )?
            int alt159=2;
            int LA159_0 = input.LA(1);

            if ( (LA159_0==IF) ) {
                int LA159_1 = input.LA(2);

                if ( (LA159_1==NOT) ) {
                    alt159=1;
                }
            }
            switch (alt159) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:439:44: IF NOT EXISTS
                    {
                    IF437=(Token)match(input,IF,FOLLOW_IF_in_create_index_stmt3701);  
                    stream_IF.add(IF437);

                    NOT438=(Token)match(input,NOT,FOLLOW_NOT_in_create_index_stmt3703);  
                    stream_NOT.add(NOT438);

                    EXISTS439=(Token)match(input,EXISTS,FOLLOW_EXISTS_in_create_index_stmt3705);  
                    stream_EXISTS.add(EXISTS439);


                    }
                    break;

            }

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:439:60: (database_name= id DOT )?
            int alt160=2;
            int LA160_0 = input.LA(1);

            if ( (LA160_0==ID) ) {
                int LA160_1 = input.LA(2);

                if ( (LA160_1==DOT) ) {
                    alt160=1;
                }
            }
            else if ( ((LA160_0>=EXPLAIN && LA160_0<=PLAN)||(LA160_0>=INDEXED && LA160_0<=BY)||(LA160_0>=OR && LA160_0<=ESCAPE)||(LA160_0>=IS && LA160_0<=BETWEEN)||LA160_0==COLLATE||(LA160_0>=DISTINCT && LA160_0<=THEN)||(LA160_0>=CURRENT_TIME && LA160_0<=CURRENT_TIMESTAMP)||(LA160_0>=RAISE && LA160_0<=FAIL)||(LA160_0>=PRAGMA && LA160_0<=FOR)||(LA160_0>=UNIQUE && LA160_0<=ROW)) ) {
                int LA160_2 = input.LA(2);

                if ( (LA160_2==DOT) ) {
                    alt160=1;
                }
            }
            switch (alt160) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:439:61: database_name= id DOT
                    {
                    pushFollow(FOLLOW_id_in_create_index_stmt3712);
                    database_name=id();

                    state._fsp--;

                    stream_id.add(database_name.getTree());
                    DOT440=(Token)match(input,DOT,FOLLOW_DOT_in_create_index_stmt3714);  
                    stream_DOT.add(DOT440);


                    }
                    break;

            }

            pushFollow(FOLLOW_id_in_create_index_stmt3720);
            index_name=id();

            state._fsp--;

            stream_id.add(index_name.getTree());
            ON441=(Token)match(input,ON,FOLLOW_ON_in_create_index_stmt3724);  
            stream_ON.add(ON441);

            pushFollow(FOLLOW_id_in_create_index_stmt3728);
            table_name=id();

            state._fsp--;

            stream_id.add(table_name.getTree());
            LPAREN442=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_create_index_stmt3730);  
            stream_LPAREN.add(LPAREN442);

            pushFollow(FOLLOW_indexed_column_in_create_index_stmt3734);
            columns=indexed_column();

            state._fsp--;

            stream_indexed_column.add(columns.getTree());
            if (list_columns==null) list_columns=new ArrayList();
            list_columns.add(columns.getTree());

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:440:51: ( COMMA columns+= indexed_column )*
            loop161:
            do {
                int alt161=2;
                int LA161_0 = input.LA(1);

                if ( (LA161_0==COMMA) ) {
                    alt161=1;
                }


                switch (alt161) {
            	case 1 :
            	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:440:52: COMMA columns+= indexed_column
            	    {
            	    COMMA443=(Token)match(input,COMMA,FOLLOW_COMMA_in_create_index_stmt3737);  
            	    stream_COMMA.add(COMMA443);

            	    pushFollow(FOLLOW_indexed_column_in_create_index_stmt3741);
            	    columns=indexed_column();

            	    state._fsp--;

            	    stream_indexed_column.add(columns.getTree());
            	    if (list_columns==null) list_columns=new ArrayList();
            	    list_columns.add(columns.getTree());


            	    }
            	    break;

            	default :
            	    break loop161;
                }
            } while (true);

            RPAREN444=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_create_index_stmt3745);  
            stream_RPAREN.add(RPAREN444);



            // AST REWRITE
            // elements: UNIQUE, columns, table_name, database_name, EXISTS, index_name
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
            // 441:1: -> ^( CREATE_INDEX ^( OPTIONS ( UNIQUE )? ( EXISTS )? ) ^( $index_name ( $database_name)? ) $table_name ( ^( COLUMNS ( $columns)+ ) )? )
            {
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:441:4: ^( CREATE_INDEX ^( OPTIONS ( UNIQUE )? ( EXISTS )? ) ^( $index_name ( $database_name)? ) $table_name ( ^( COLUMNS ( $columns)+ ) )? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(CREATE_INDEX, "CREATE_INDEX"), root_1);

                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:441:19: ^( OPTIONS ( UNIQUE )? ( EXISTS )? )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(OPTIONS, "OPTIONS"), root_2);

                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:441:29: ( UNIQUE )?
                if ( stream_UNIQUE.hasNext() ) {
                    adaptor.addChild(root_2, stream_UNIQUE.nextNode());

                }
                stream_UNIQUE.reset();
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:441:37: ( EXISTS )?
                if ( stream_EXISTS.hasNext() ) {
                    adaptor.addChild(root_2, stream_EXISTS.nextNode());

                }
                stream_EXISTS.reset();

                adaptor.addChild(root_1, root_2);
                }
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:441:46: ^( $index_name ( $database_name)? )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot(stream_index_name.nextNode(), root_2);

                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:441:60: ( $database_name)?
                if ( stream_database_name.hasNext() ) {
                    adaptor.addChild(root_2, stream_database_name.nextTree());

                }
                stream_database_name.reset();

                adaptor.addChild(root_1, root_2);
                }
                adaptor.addChild(root_1, stream_table_name.nextTree());
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:441:89: ( ^( COLUMNS ( $columns)+ ) )?
                if ( stream_columns.hasNext() ) {
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:441:89: ^( COLUMNS ( $columns)+ )
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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:443:1: indexed_column : column_name= id ( COLLATE collation_name= id )? ( ASC | DESC )? -> ^( $column_name ( ^( COLLATE $collation_name) )? ( ASC )? ( DESC )? ) ;
    public final SqlParser.indexed_column_return indexed_column() throws RecognitionException {
        SqlParser.indexed_column_return retval = new SqlParser.indexed_column_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token COLLATE445=null;
        Token ASC446=null;
        Token DESC447=null;
        SqlParser.id_return column_name = null;

        SqlParser.id_return collation_name = null;


        Object COLLATE445_tree=null;
        Object ASC446_tree=null;
        Object DESC447_tree=null;
        RewriteRuleTokenStream stream_ASC=new RewriteRuleTokenStream(adaptor,"token ASC");
        RewriteRuleTokenStream stream_DESC=new RewriteRuleTokenStream(adaptor,"token DESC");
        RewriteRuleTokenStream stream_COLLATE=new RewriteRuleTokenStream(adaptor,"token COLLATE");
        RewriteRuleSubtreeStream stream_id=new RewriteRuleSubtreeStream(adaptor,"rule id");
        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:443:15: (column_name= id ( COLLATE collation_name= id )? ( ASC | DESC )? -> ^( $column_name ( ^( COLLATE $collation_name) )? ( ASC )? ( DESC )? ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:443:17: column_name= id ( COLLATE collation_name= id )? ( ASC | DESC )?
            {
            pushFollow(FOLLOW_id_in_indexed_column3791);
            column_name=id();

            state._fsp--;

            stream_id.add(column_name.getTree());
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:443:32: ( COLLATE collation_name= id )?
            int alt162=2;
            int LA162_0 = input.LA(1);

            if ( (LA162_0==COLLATE) ) {
                alt162=1;
            }
            switch (alt162) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:443:33: COLLATE collation_name= id
                    {
                    COLLATE445=(Token)match(input,COLLATE,FOLLOW_COLLATE_in_indexed_column3794);  
                    stream_COLLATE.add(COLLATE445);

                    pushFollow(FOLLOW_id_in_indexed_column3798);
                    collation_name=id();

                    state._fsp--;

                    stream_id.add(collation_name.getTree());

                    }
                    break;

            }

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:443:61: ( ASC | DESC )?
            int alt163=3;
            int LA163_0 = input.LA(1);

            if ( (LA163_0==ASC) ) {
                alt163=1;
            }
            else if ( (LA163_0==DESC) ) {
                alt163=2;
            }
            switch (alt163) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:443:62: ASC
                    {
                    ASC446=(Token)match(input,ASC,FOLLOW_ASC_in_indexed_column3803);  
                    stream_ASC.add(ASC446);


                    }
                    break;
                case 2 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:443:68: DESC
                    {
                    DESC447=(Token)match(input,DESC,FOLLOW_DESC_in_indexed_column3807);  
                    stream_DESC.add(DESC447);


                    }
                    break;

            }



            // AST REWRITE
            // elements: DESC, collation_name, ASC, column_name, COLLATE
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
            // 444:1: -> ^( $column_name ( ^( COLLATE $collation_name) )? ( ASC )? ( DESC )? )
            {
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:444:4: ^( $column_name ( ^( COLLATE $collation_name) )? ( ASC )? ( DESC )? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(stream_column_name.nextNode(), root_1);

                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:444:19: ( ^( COLLATE $collation_name) )?
                if ( stream_collation_name.hasNext()||stream_COLLATE.hasNext() ) {
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:444:19: ^( COLLATE $collation_name)
                    {
                    Object root_2 = (Object)adaptor.nil();
                    root_2 = (Object)adaptor.becomeRoot(stream_COLLATE.nextNode(), root_2);

                    adaptor.addChild(root_2, stream_collation_name.nextTree());

                    adaptor.addChild(root_1, root_2);
                    }

                }
                stream_collation_name.reset();
                stream_COLLATE.reset();
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:444:47: ( ASC )?
                if ( stream_ASC.hasNext() ) {
                    adaptor.addChild(root_1, stream_ASC.nextNode());

                }
                stream_ASC.reset();
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:444:52: ( DESC )?
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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:447:1: drop_index_stmt : DROP INDEX ( IF EXISTS )? (database_name= id DOT )? index_name= id -> ^( DROP_INDEX ^( OPTIONS ( EXISTS )? ) ^( $index_name ( $database_name)? ) ) ;
    public final SqlParser.drop_index_stmt_return drop_index_stmt() throws RecognitionException {
        SqlParser.drop_index_stmt_return retval = new SqlParser.drop_index_stmt_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token DROP448=null;
        Token INDEX449=null;
        Token IF450=null;
        Token EXISTS451=null;
        Token DOT452=null;
        SqlParser.id_return database_name = null;

        SqlParser.id_return index_name = null;


        Object DROP448_tree=null;
        Object INDEX449_tree=null;
        Object IF450_tree=null;
        Object EXISTS451_tree=null;
        Object DOT452_tree=null;
        RewriteRuleTokenStream stream_INDEX=new RewriteRuleTokenStream(adaptor,"token INDEX");
        RewriteRuleTokenStream stream_EXISTS=new RewriteRuleTokenStream(adaptor,"token EXISTS");
        RewriteRuleTokenStream stream_DROP=new RewriteRuleTokenStream(adaptor,"token DROP");
        RewriteRuleTokenStream stream_DOT=new RewriteRuleTokenStream(adaptor,"token DOT");
        RewriteRuleTokenStream stream_IF=new RewriteRuleTokenStream(adaptor,"token IF");
        RewriteRuleSubtreeStream stream_id=new RewriteRuleSubtreeStream(adaptor,"rule id");
        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:447:16: ( DROP INDEX ( IF EXISTS )? (database_name= id DOT )? index_name= id -> ^( DROP_INDEX ^( OPTIONS ( EXISTS )? ) ^( $index_name ( $database_name)? ) ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:447:18: DROP INDEX ( IF EXISTS )? (database_name= id DOT )? index_name= id
            {
            DROP448=(Token)match(input,DROP,FOLLOW_DROP_in_drop_index_stmt3838);  
            stream_DROP.add(DROP448);

            INDEX449=(Token)match(input,INDEX,FOLLOW_INDEX_in_drop_index_stmt3840);  
            stream_INDEX.add(INDEX449);

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:447:29: ( IF EXISTS )?
            int alt164=2;
            int LA164_0 = input.LA(1);

            if ( (LA164_0==IF) ) {
                int LA164_1 = input.LA(2);

                if ( (LA164_1==EXISTS) ) {
                    alt164=1;
                }
            }
            switch (alt164) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:447:30: IF EXISTS
                    {
                    IF450=(Token)match(input,IF,FOLLOW_IF_in_drop_index_stmt3843);  
                    stream_IF.add(IF450);

                    EXISTS451=(Token)match(input,EXISTS,FOLLOW_EXISTS_in_drop_index_stmt3845);  
                    stream_EXISTS.add(EXISTS451);


                    }
                    break;

            }

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:447:42: (database_name= id DOT )?
            int alt165=2;
            int LA165_0 = input.LA(1);

            if ( (LA165_0==ID) ) {
                int LA165_1 = input.LA(2);

                if ( (LA165_1==DOT) ) {
                    alt165=1;
                }
            }
            else if ( ((LA165_0>=EXPLAIN && LA165_0<=PLAN)||(LA165_0>=INDEXED && LA165_0<=BY)||(LA165_0>=OR && LA165_0<=ESCAPE)||(LA165_0>=IS && LA165_0<=BETWEEN)||LA165_0==COLLATE||(LA165_0>=DISTINCT && LA165_0<=THEN)||(LA165_0>=CURRENT_TIME && LA165_0<=CURRENT_TIMESTAMP)||(LA165_0>=RAISE && LA165_0<=FAIL)||(LA165_0>=PRAGMA && LA165_0<=FOR)||(LA165_0>=UNIQUE && LA165_0<=ROW)) ) {
                int LA165_2 = input.LA(2);

                if ( (LA165_2==DOT) ) {
                    alt165=1;
                }
            }
            switch (alt165) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:447:43: database_name= id DOT
                    {
                    pushFollow(FOLLOW_id_in_drop_index_stmt3852);
                    database_name=id();

                    state._fsp--;

                    stream_id.add(database_name.getTree());
                    DOT452=(Token)match(input,DOT,FOLLOW_DOT_in_drop_index_stmt3854);  
                    stream_DOT.add(DOT452);


                    }
                    break;

            }

            pushFollow(FOLLOW_id_in_drop_index_stmt3860);
            index_name=id();

            state._fsp--;

            stream_id.add(index_name.getTree());


            // AST REWRITE
            // elements: index_name, database_name, EXISTS
            // token labels: 
            // rule labels: database_name, index_name, retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_database_name=new RewriteRuleSubtreeStream(adaptor,"rule database_name",database_name!=null?database_name.tree:null);
            RewriteRuleSubtreeStream stream_index_name=new RewriteRuleSubtreeStream(adaptor,"rule index_name",index_name!=null?index_name.tree:null);
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 448:1: -> ^( DROP_INDEX ^( OPTIONS ( EXISTS )? ) ^( $index_name ( $database_name)? ) )
            {
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:448:4: ^( DROP_INDEX ^( OPTIONS ( EXISTS )? ) ^( $index_name ( $database_name)? ) )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(DROP_INDEX, "DROP_INDEX"), root_1);

                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:448:17: ^( OPTIONS ( EXISTS )? )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(OPTIONS, "OPTIONS"), root_2);

                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:448:27: ( EXISTS )?
                if ( stream_EXISTS.hasNext() ) {
                    adaptor.addChild(root_2, stream_EXISTS.nextNode());

                }
                stream_EXISTS.reset();

                adaptor.addChild(root_1, root_2);
                }
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:448:36: ^( $index_name ( $database_name)? )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot(stream_index_name.nextNode(), root_2);

                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:448:50: ( $database_name)?
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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:451:1: create_trigger_stmt : CREATE ( TEMPORARY )? TRIGGER ( IF NOT EXISTS )? (database_name= id DOT )? trigger_name= id ( BEFORE | AFTER | INSTEAD OF )? ( DELETE | INSERT | UPDATE ( OF column_names+= id ( COMMA column_names+= id )* )? ) ON table_name= id ( FOR EACH ROW )? ( WHEN expr )? BEGIN ( ( update_stmt | insert_stmt | delete_stmt | select_stmt ) SEMI )+ END ;
    public final SqlParser.create_trigger_stmt_return create_trigger_stmt() throws RecognitionException {
        SqlParser.create_trigger_stmt_return retval = new SqlParser.create_trigger_stmt_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token CREATE453=null;
        Token TEMPORARY454=null;
        Token TRIGGER455=null;
        Token IF456=null;
        Token NOT457=null;
        Token EXISTS458=null;
        Token DOT459=null;
        Token BEFORE460=null;
        Token AFTER461=null;
        Token INSTEAD462=null;
        Token OF463=null;
        Token DELETE464=null;
        Token INSERT465=null;
        Token UPDATE466=null;
        Token OF467=null;
        Token COMMA468=null;
        Token ON469=null;
        Token FOR470=null;
        Token EACH471=null;
        Token ROW472=null;
        Token WHEN473=null;
        Token BEGIN475=null;
        Token SEMI480=null;
        Token END481=null;
        List list_column_names=null;
        SqlParser.id_return database_name = null;

        SqlParser.id_return trigger_name = null;

        SqlParser.id_return table_name = null;

        SqlParser.expr_return expr474 = null;

        SqlParser.update_stmt_return update_stmt476 = null;

        SqlParser.insert_stmt_return insert_stmt477 = null;

        SqlParser.delete_stmt_return delete_stmt478 = null;

        SqlParser.select_stmt_return select_stmt479 = null;

        SqlParser.id_return column_names = null;
         column_names = null;
        Object CREATE453_tree=null;
        Object TEMPORARY454_tree=null;
        Object TRIGGER455_tree=null;
        Object IF456_tree=null;
        Object NOT457_tree=null;
        Object EXISTS458_tree=null;
        Object DOT459_tree=null;
        Object BEFORE460_tree=null;
        Object AFTER461_tree=null;
        Object INSTEAD462_tree=null;
        Object OF463_tree=null;
        Object DELETE464_tree=null;
        Object INSERT465_tree=null;
        Object UPDATE466_tree=null;
        Object OF467_tree=null;
        Object COMMA468_tree=null;
        Object ON469_tree=null;
        Object FOR470_tree=null;
        Object EACH471_tree=null;
        Object ROW472_tree=null;
        Object WHEN473_tree=null;
        Object BEGIN475_tree=null;
        Object SEMI480_tree=null;
        Object END481_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:451:20: ( CREATE ( TEMPORARY )? TRIGGER ( IF NOT EXISTS )? (database_name= id DOT )? trigger_name= id ( BEFORE | AFTER | INSTEAD OF )? ( DELETE | INSERT | UPDATE ( OF column_names+= id ( COMMA column_names+= id )* )? ) ON table_name= id ( FOR EACH ROW )? ( WHEN expr )? BEGIN ( ( update_stmt | insert_stmt | delete_stmt | select_stmt ) SEMI )+ END )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:451:22: CREATE ( TEMPORARY )? TRIGGER ( IF NOT EXISTS )? (database_name= id DOT )? trigger_name= id ( BEFORE | AFTER | INSTEAD OF )? ( DELETE | INSERT | UPDATE ( OF column_names+= id ( COMMA column_names+= id )* )? ) ON table_name= id ( FOR EACH ROW )? ( WHEN expr )? BEGIN ( ( update_stmt | insert_stmt | delete_stmt | select_stmt ) SEMI )+ END
            {
            root_0 = (Object)adaptor.nil();

            CREATE453=(Token)match(input,CREATE,FOLLOW_CREATE_in_create_trigger_stmt3890); 
            CREATE453_tree = (Object)adaptor.create(CREATE453);
            adaptor.addChild(root_0, CREATE453_tree);

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:451:29: ( TEMPORARY )?
            int alt166=2;
            int LA166_0 = input.LA(1);

            if ( (LA166_0==TEMPORARY) ) {
                alt166=1;
            }
            switch (alt166) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:451:29: TEMPORARY
                    {
                    TEMPORARY454=(Token)match(input,TEMPORARY,FOLLOW_TEMPORARY_in_create_trigger_stmt3892); 
                    TEMPORARY454_tree = (Object)adaptor.create(TEMPORARY454);
                    adaptor.addChild(root_0, TEMPORARY454_tree);


                    }
                    break;

            }

            TRIGGER455=(Token)match(input,TRIGGER,FOLLOW_TRIGGER_in_create_trigger_stmt3895); 
            TRIGGER455_tree = (Object)adaptor.create(TRIGGER455);
            adaptor.addChild(root_0, TRIGGER455_tree);

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:451:48: ( IF NOT EXISTS )?
            int alt167=2;
            alt167 = dfa167.predict(input);
            switch (alt167) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:451:49: IF NOT EXISTS
                    {
                    IF456=(Token)match(input,IF,FOLLOW_IF_in_create_trigger_stmt3898); 
                    IF456_tree = (Object)adaptor.create(IF456);
                    adaptor.addChild(root_0, IF456_tree);

                    NOT457=(Token)match(input,NOT,FOLLOW_NOT_in_create_trigger_stmt3900); 
                    NOT457_tree = (Object)adaptor.create(NOT457);
                    adaptor.addChild(root_0, NOT457_tree);

                    EXISTS458=(Token)match(input,EXISTS,FOLLOW_EXISTS_in_create_trigger_stmt3902); 
                    EXISTS458_tree = (Object)adaptor.create(EXISTS458);
                    adaptor.addChild(root_0, EXISTS458_tree);


                    }
                    break;

            }

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:451:65: (database_name= id DOT )?
            int alt168=2;
            alt168 = dfa168.predict(input);
            switch (alt168) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:451:66: database_name= id DOT
                    {
                    pushFollow(FOLLOW_id_in_create_trigger_stmt3909);
                    database_name=id();

                    state._fsp--;

                    adaptor.addChild(root_0, database_name.getTree());
                    DOT459=(Token)match(input,DOT,FOLLOW_DOT_in_create_trigger_stmt3911); 
                    DOT459_tree = (Object)adaptor.create(DOT459);
                    adaptor.addChild(root_0, DOT459_tree);


                    }
                    break;

            }

            pushFollow(FOLLOW_id_in_create_trigger_stmt3917);
            trigger_name=id();

            state._fsp--;

            adaptor.addChild(root_0, trigger_name.getTree());
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:452:3: ( BEFORE | AFTER | INSTEAD OF )?
            int alt169=4;
            switch ( input.LA(1) ) {
                case BEFORE:
                    {
                    alt169=1;
                    }
                    break;
                case AFTER:
                    {
                    alt169=2;
                    }
                    break;
                case INSTEAD:
                    {
                    alt169=3;
                    }
                    break;
            }

            switch (alt169) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:452:4: BEFORE
                    {
                    BEFORE460=(Token)match(input,BEFORE,FOLLOW_BEFORE_in_create_trigger_stmt3922); 
                    BEFORE460_tree = (Object)adaptor.create(BEFORE460);
                    adaptor.addChild(root_0, BEFORE460_tree);


                    }
                    break;
                case 2 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:452:13: AFTER
                    {
                    AFTER461=(Token)match(input,AFTER,FOLLOW_AFTER_in_create_trigger_stmt3926); 
                    AFTER461_tree = (Object)adaptor.create(AFTER461);
                    adaptor.addChild(root_0, AFTER461_tree);


                    }
                    break;
                case 3 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:452:21: INSTEAD OF
                    {
                    INSTEAD462=(Token)match(input,INSTEAD,FOLLOW_INSTEAD_in_create_trigger_stmt3930); 
                    INSTEAD462_tree = (Object)adaptor.create(INSTEAD462);
                    adaptor.addChild(root_0, INSTEAD462_tree);

                    OF463=(Token)match(input,OF,FOLLOW_OF_in_create_trigger_stmt3932); 
                    OF463_tree = (Object)adaptor.create(OF463);
                    adaptor.addChild(root_0, OF463_tree);


                    }
                    break;

            }

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:452:34: ( DELETE | INSERT | UPDATE ( OF column_names+= id ( COMMA column_names+= id )* )? )
            int alt172=3;
            switch ( input.LA(1) ) {
            case DELETE:
                {
                alt172=1;
                }
                break;
            case INSERT:
                {
                alt172=2;
                }
                break;
            case UPDATE:
                {
                alt172=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 172, 0, input);

                throw nvae;
            }

            switch (alt172) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:452:35: DELETE
                    {
                    DELETE464=(Token)match(input,DELETE,FOLLOW_DELETE_in_create_trigger_stmt3937); 
                    DELETE464_tree = (Object)adaptor.create(DELETE464);
                    adaptor.addChild(root_0, DELETE464_tree);


                    }
                    break;
                case 2 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:452:44: INSERT
                    {
                    INSERT465=(Token)match(input,INSERT,FOLLOW_INSERT_in_create_trigger_stmt3941); 
                    INSERT465_tree = (Object)adaptor.create(INSERT465);
                    adaptor.addChild(root_0, INSERT465_tree);


                    }
                    break;
                case 3 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:452:53: UPDATE ( OF column_names+= id ( COMMA column_names+= id )* )?
                    {
                    UPDATE466=(Token)match(input,UPDATE,FOLLOW_UPDATE_in_create_trigger_stmt3945); 
                    UPDATE466_tree = (Object)adaptor.create(UPDATE466);
                    adaptor.addChild(root_0, UPDATE466_tree);

                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:452:60: ( OF column_names+= id ( COMMA column_names+= id )* )?
                    int alt171=2;
                    int LA171_0 = input.LA(1);

                    if ( (LA171_0==OF) ) {
                        alt171=1;
                    }
                    switch (alt171) {
                        case 1 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:452:61: OF column_names+= id ( COMMA column_names+= id )*
                            {
                            OF467=(Token)match(input,OF,FOLLOW_OF_in_create_trigger_stmt3948); 
                            OF467_tree = (Object)adaptor.create(OF467);
                            adaptor.addChild(root_0, OF467_tree);

                            pushFollow(FOLLOW_id_in_create_trigger_stmt3952);
                            column_names=id();

                            state._fsp--;

                            adaptor.addChild(root_0, column_names.getTree());
                            if (list_column_names==null) list_column_names=new ArrayList();
                            list_column_names.add(column_names.getTree());

                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:452:81: ( COMMA column_names+= id )*
                            loop170:
                            do {
                                int alt170=2;
                                int LA170_0 = input.LA(1);

                                if ( (LA170_0==COMMA) ) {
                                    alt170=1;
                                }


                                switch (alt170) {
                            	case 1 :
                            	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:452:82: COMMA column_names+= id
                            	    {
                            	    COMMA468=(Token)match(input,COMMA,FOLLOW_COMMA_in_create_trigger_stmt3955); 
                            	    COMMA468_tree = (Object)adaptor.create(COMMA468);
                            	    adaptor.addChild(root_0, COMMA468_tree);

                            	    pushFollow(FOLLOW_id_in_create_trigger_stmt3959);
                            	    column_names=id();

                            	    state._fsp--;

                            	    adaptor.addChild(root_0, column_names.getTree());
                            	    if (list_column_names==null) list_column_names=new ArrayList();
                            	    list_column_names.add(column_names.getTree());


                            	    }
                            	    break;

                            	default :
                            	    break loop170;
                                }
                            } while (true);


                            }
                            break;

                    }


                    }
                    break;

            }

            ON469=(Token)match(input,ON,FOLLOW_ON_in_create_trigger_stmt3968); 
            ON469_tree = (Object)adaptor.create(ON469);
            adaptor.addChild(root_0, ON469_tree);

            pushFollow(FOLLOW_id_in_create_trigger_stmt3972);
            table_name=id();

            state._fsp--;

            adaptor.addChild(root_0, table_name.getTree());
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:453:20: ( FOR EACH ROW )?
            int alt173=2;
            int LA173_0 = input.LA(1);

            if ( (LA173_0==FOR) ) {
                alt173=1;
            }
            switch (alt173) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:453:21: FOR EACH ROW
                    {
                    FOR470=(Token)match(input,FOR,FOLLOW_FOR_in_create_trigger_stmt3975); 
                    FOR470_tree = (Object)adaptor.create(FOR470);
                    adaptor.addChild(root_0, FOR470_tree);

                    EACH471=(Token)match(input,EACH,FOLLOW_EACH_in_create_trigger_stmt3977); 
                    EACH471_tree = (Object)adaptor.create(EACH471);
                    adaptor.addChild(root_0, EACH471_tree);

                    ROW472=(Token)match(input,ROW,FOLLOW_ROW_in_create_trigger_stmt3979); 
                    ROW472_tree = (Object)adaptor.create(ROW472);
                    adaptor.addChild(root_0, ROW472_tree);


                    }
                    break;

            }

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:453:36: ( WHEN expr )?
            int alt174=2;
            int LA174_0 = input.LA(1);

            if ( (LA174_0==WHEN) ) {
                alt174=1;
            }
            switch (alt174) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:453:37: WHEN expr
                    {
                    WHEN473=(Token)match(input,WHEN,FOLLOW_WHEN_in_create_trigger_stmt3984); 
                    WHEN473_tree = (Object)adaptor.create(WHEN473);
                    adaptor.addChild(root_0, WHEN473_tree);

                    pushFollow(FOLLOW_expr_in_create_trigger_stmt3986);
                    expr474=expr();

                    state._fsp--;

                    adaptor.addChild(root_0, expr474.getTree());

                    }
                    break;

            }

            BEGIN475=(Token)match(input,BEGIN,FOLLOW_BEGIN_in_create_trigger_stmt3992); 
            BEGIN475_tree = (Object)adaptor.create(BEGIN475);
            adaptor.addChild(root_0, BEGIN475_tree);

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:454:9: ( ( update_stmt | insert_stmt | delete_stmt | select_stmt ) SEMI )+
            int cnt176=0;
            loop176:
            do {
                int alt176=2;
                int LA176_0 = input.LA(1);

                if ( (LA176_0==REPLACE||LA176_0==SELECT||LA176_0==INSERT||LA176_0==UPDATE||LA176_0==DELETE) ) {
                    alt176=1;
                }


                switch (alt176) {
            	case 1 :
            	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:454:10: ( update_stmt | insert_stmt | delete_stmt | select_stmt ) SEMI
            	    {
            	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:454:10: ( update_stmt | insert_stmt | delete_stmt | select_stmt )
            	    int alt175=4;
            	    switch ( input.LA(1) ) {
            	    case UPDATE:
            	        {
            	        alt175=1;
            	        }
            	        break;
            	    case REPLACE:
            	    case INSERT:
            	        {
            	        alt175=2;
            	        }
            	        break;
            	    case DELETE:
            	        {
            	        alt175=3;
            	        }
            	        break;
            	    case SELECT:
            	        {
            	        alt175=4;
            	        }
            	        break;
            	    default:
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 175, 0, input);

            	        throw nvae;
            	    }

            	    switch (alt175) {
            	        case 1 :
            	            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:454:11: update_stmt
            	            {
            	            pushFollow(FOLLOW_update_stmt_in_create_trigger_stmt3996);
            	            update_stmt476=update_stmt();

            	            state._fsp--;

            	            adaptor.addChild(root_0, update_stmt476.getTree());

            	            }
            	            break;
            	        case 2 :
            	            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:454:25: insert_stmt
            	            {
            	            pushFollow(FOLLOW_insert_stmt_in_create_trigger_stmt4000);
            	            insert_stmt477=insert_stmt();

            	            state._fsp--;

            	            adaptor.addChild(root_0, insert_stmt477.getTree());

            	            }
            	            break;
            	        case 3 :
            	            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:454:39: delete_stmt
            	            {
            	            pushFollow(FOLLOW_delete_stmt_in_create_trigger_stmt4004);
            	            delete_stmt478=delete_stmt();

            	            state._fsp--;

            	            adaptor.addChild(root_0, delete_stmt478.getTree());

            	            }
            	            break;
            	        case 4 :
            	            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:454:53: select_stmt
            	            {
            	            pushFollow(FOLLOW_select_stmt_in_create_trigger_stmt4008);
            	            select_stmt479=select_stmt();

            	            state._fsp--;

            	            adaptor.addChild(root_0, select_stmt479.getTree());

            	            }
            	            break;

            	    }

            	    SEMI480=(Token)match(input,SEMI,FOLLOW_SEMI_in_create_trigger_stmt4011); 
            	    SEMI480_tree = (Object)adaptor.create(SEMI480);
            	    adaptor.addChild(root_0, SEMI480_tree);


            	    }
            	    break;

            	default :
            	    if ( cnt176 >= 1 ) break loop176;
                        EarlyExitException eee =
                            new EarlyExitException(176, input);
                        throw eee;
                }
                cnt176++;
            } while (true);

            END481=(Token)match(input,END,FOLLOW_END_in_create_trigger_stmt4015); 
            END481_tree = (Object)adaptor.create(END481);
            adaptor.addChild(root_0, END481_tree);


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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:457:1: drop_trigger_stmt : DROP TRIGGER ( IF EXISTS )? (database_name= id DOT )? trigger_name= id ;
    public final SqlParser.drop_trigger_stmt_return drop_trigger_stmt() throws RecognitionException {
        SqlParser.drop_trigger_stmt_return retval = new SqlParser.drop_trigger_stmt_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token DROP482=null;
        Token TRIGGER483=null;
        Token IF484=null;
        Token EXISTS485=null;
        Token DOT486=null;
        SqlParser.id_return database_name = null;

        SqlParser.id_return trigger_name = null;


        Object DROP482_tree=null;
        Object TRIGGER483_tree=null;
        Object IF484_tree=null;
        Object EXISTS485_tree=null;
        Object DOT486_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:457:18: ( DROP TRIGGER ( IF EXISTS )? (database_name= id DOT )? trigger_name= id )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:457:20: DROP TRIGGER ( IF EXISTS )? (database_name= id DOT )? trigger_name= id
            {
            root_0 = (Object)adaptor.nil();

            DROP482=(Token)match(input,DROP,FOLLOW_DROP_in_drop_trigger_stmt4023); 
            DROP482_tree = (Object)adaptor.create(DROP482);
            adaptor.addChild(root_0, DROP482_tree);

            TRIGGER483=(Token)match(input,TRIGGER,FOLLOW_TRIGGER_in_drop_trigger_stmt4025); 
            TRIGGER483_tree = (Object)adaptor.create(TRIGGER483);
            adaptor.addChild(root_0, TRIGGER483_tree);

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:457:33: ( IF EXISTS )?
            int alt177=2;
            int LA177_0 = input.LA(1);

            if ( (LA177_0==IF) ) {
                int LA177_1 = input.LA(2);

                if ( (LA177_1==EXISTS) ) {
                    alt177=1;
                }
            }
            switch (alt177) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:457:34: IF EXISTS
                    {
                    IF484=(Token)match(input,IF,FOLLOW_IF_in_drop_trigger_stmt4028); 
                    IF484_tree = (Object)adaptor.create(IF484);
                    adaptor.addChild(root_0, IF484_tree);

                    EXISTS485=(Token)match(input,EXISTS,FOLLOW_EXISTS_in_drop_trigger_stmt4030); 
                    EXISTS485_tree = (Object)adaptor.create(EXISTS485);
                    adaptor.addChild(root_0, EXISTS485_tree);


                    }
                    break;

            }

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:457:46: (database_name= id DOT )?
            int alt178=2;
            int LA178_0 = input.LA(1);

            if ( (LA178_0==ID) ) {
                int LA178_1 = input.LA(2);

                if ( (LA178_1==DOT) ) {
                    alt178=1;
                }
            }
            else if ( ((LA178_0>=EXPLAIN && LA178_0<=PLAN)||(LA178_0>=INDEXED && LA178_0<=BY)||(LA178_0>=OR && LA178_0<=ESCAPE)||(LA178_0>=IS && LA178_0<=BETWEEN)||LA178_0==COLLATE||(LA178_0>=DISTINCT && LA178_0<=THEN)||(LA178_0>=CURRENT_TIME && LA178_0<=CURRENT_TIMESTAMP)||(LA178_0>=RAISE && LA178_0<=FAIL)||(LA178_0>=PRAGMA && LA178_0<=FOR)||(LA178_0>=UNIQUE && LA178_0<=ROW)) ) {
                int LA178_2 = input.LA(2);

                if ( (LA178_2==DOT) ) {
                    alt178=1;
                }
            }
            switch (alt178) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:457:47: database_name= id DOT
                    {
                    pushFollow(FOLLOW_id_in_drop_trigger_stmt4037);
                    database_name=id();

                    state._fsp--;

                    adaptor.addChild(root_0, database_name.getTree());
                    DOT486=(Token)match(input,DOT,FOLLOW_DOT_in_drop_trigger_stmt4039); 
                    DOT486_tree = (Object)adaptor.create(DOT486);
                    adaptor.addChild(root_0, DOT486_tree);


                    }
                    break;

            }

            pushFollow(FOLLOW_id_in_drop_trigger_stmt4045);
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

    public static class id_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "id"
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:462:1: id : ( ID | keyword );
    public final SqlParser.id_return id() throws RecognitionException {
        SqlParser.id_return retval = new SqlParser.id_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID487=null;
        SqlParser.keyword_return keyword488 = null;


        Object ID487_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:462:3: ( ID | keyword )
            int alt179=2;
            int LA179_0 = input.LA(1);

            if ( (LA179_0==ID) ) {
                alt179=1;
            }
            else if ( ((LA179_0>=EXPLAIN && LA179_0<=PLAN)||(LA179_0>=INDEXED && LA179_0<=BY)||(LA179_0>=OR && LA179_0<=ESCAPE)||(LA179_0>=IS && LA179_0<=BETWEEN)||LA179_0==COLLATE||(LA179_0>=DISTINCT && LA179_0<=THEN)||(LA179_0>=CURRENT_TIME && LA179_0<=CURRENT_TIMESTAMP)||(LA179_0>=RAISE && LA179_0<=FAIL)||(LA179_0>=PRAGMA && LA179_0<=FOR)||(LA179_0>=UNIQUE && LA179_0<=ROW)) ) {
                alt179=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 179, 0, input);

                throw nvae;
            }
            switch (alt179) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:462:5: ID
                    {
                    root_0 = (Object)adaptor.nil();

                    ID487=(Token)match(input,ID,FOLLOW_ID_in_id4055); 
                    ID487_tree = (Object)adaptor.create(ID487);
                    adaptor.addChild(root_0, ID487_tree);


                    }
                    break;
                case 2 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:462:10: keyword
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_keyword_in_id4059);
                    keyword488=keyword();

                    state._fsp--;

                    adaptor.addChild(root_0, keyword488.getTree());

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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:464:1: keyword : ( ABORT | ADD | AFTER | ALL | ALTER | ANALYZE | AND | AS | ASC | ATTACH | AUTOINCREMENT | BEFORE | BEGIN | BETWEEN | BY | CASCADE | CASE | CAST | CHECK | COLLATE | COLUMN | COMMIT | CONFLICT | CONSTRAINT | CREATE | CROSS | CURRENT_TIME | CURRENT_DATE | CURRENT_TIMESTAMP | DATABASE | DEFAULT | DEFERRABLE | DEFERRED | DELETE | DESC | DETACH | DISTINCT | DROP | EACH | ELSE | END | ESCAPE | EXCEPT | EXCLUSIVE | EXISTS | EXPLAIN | FAIL | FOR | FOREIGN | FROM | GROUP | HAVING | IF | IGNORE | IMMEDIATE | INDEX | INDEXED | INITIALLY | INNER | INSERT | INSTEAD | INTERSECT | INTO | IS | JOIN | KEY | LEFT | LIMIT | NATURAL | NULL | OF | OFFSET | ON | OR | ORDER | OUTER | PLAN | PRAGMA | PRIMARY | QUERY | RAISE | REFERENCES | REINDEX | RELEASE | RENAME | REPLACE | RESTRICT | ROLLBACK | ROW | SAVEPOINT | SELECT | SET | TABLE | TEMPORARY | THEN | TO | TRANSACTION | TRIGGER | UNION | UNIQUE | UPDATE | USING | VACUUM | VALUES | VIEW | VIRTUAL | WHEN | WHERE ) ;
    public final SqlParser.keyword_return keyword() throws RecognitionException {
        SqlParser.keyword_return retval = new SqlParser.keyword_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set489=null;

        Object set489_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:464:8: ( ( ABORT | ADD | AFTER | ALL | ALTER | ANALYZE | AND | AS | ASC | ATTACH | AUTOINCREMENT | BEFORE | BEGIN | BETWEEN | BY | CASCADE | CASE | CAST | CHECK | COLLATE | COLUMN | COMMIT | CONFLICT | CONSTRAINT | CREATE | CROSS | CURRENT_TIME | CURRENT_DATE | CURRENT_TIMESTAMP | DATABASE | DEFAULT | DEFERRABLE | DEFERRED | DELETE | DESC | DETACH | DISTINCT | DROP | EACH | ELSE | END | ESCAPE | EXCEPT | EXCLUSIVE | EXISTS | EXPLAIN | FAIL | FOR | FOREIGN | FROM | GROUP | HAVING | IF | IGNORE | IMMEDIATE | INDEX | INDEXED | INITIALLY | INNER | INSERT | INSTEAD | INTERSECT | INTO | IS | JOIN | KEY | LEFT | LIMIT | NATURAL | NULL | OF | OFFSET | ON | OR | ORDER | OUTER | PLAN | PRAGMA | PRIMARY | QUERY | RAISE | REFERENCES | REINDEX | RELEASE | RENAME | REPLACE | RESTRICT | ROLLBACK | ROW | SAVEPOINT | SELECT | SET | TABLE | TEMPORARY | THEN | TO | TRANSACTION | TRIGGER | UNION | UNIQUE | UPDATE | USING | VACUUM | VALUES | VIEW | VIRTUAL | WHEN | WHERE ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:464:10: ( ABORT | ADD | AFTER | ALL | ALTER | ANALYZE | AND | AS | ASC | ATTACH | AUTOINCREMENT | BEFORE | BEGIN | BETWEEN | BY | CASCADE | CASE | CAST | CHECK | COLLATE | COLUMN | COMMIT | CONFLICT | CONSTRAINT | CREATE | CROSS | CURRENT_TIME | CURRENT_DATE | CURRENT_TIMESTAMP | DATABASE | DEFAULT | DEFERRABLE | DEFERRED | DELETE | DESC | DETACH | DISTINCT | DROP | EACH | ELSE | END | ESCAPE | EXCEPT | EXCLUSIVE | EXISTS | EXPLAIN | FAIL | FOR | FOREIGN | FROM | GROUP | HAVING | IF | IGNORE | IMMEDIATE | INDEX | INDEXED | INITIALLY | INNER | INSERT | INSTEAD | INTERSECT | INTO | IS | JOIN | KEY | LEFT | LIMIT | NATURAL | NULL | OF | OFFSET | ON | OR | ORDER | OUTER | PLAN | PRAGMA | PRIMARY | QUERY | RAISE | REFERENCES | REINDEX | RELEASE | RENAME | REPLACE | RESTRICT | ROLLBACK | ROW | SAVEPOINT | SELECT | SET | TABLE | TEMPORARY | THEN | TO | TRANSACTION | TRIGGER | UNION | UNIQUE | UPDATE | USING | VACUUM | VALUES | VIEW | VIRTUAL | WHEN | WHERE )
            {
            root_0 = (Object)adaptor.nil();

            set489=(Token)input.LT(1);
            if ( (input.LA(1)>=EXPLAIN && input.LA(1)<=PLAN)||(input.LA(1)>=INDEXED && input.LA(1)<=BY)||(input.LA(1)>=OR && input.LA(1)<=ESCAPE)||(input.LA(1)>=IS && input.LA(1)<=BETWEEN)||input.LA(1)==COLLATE||(input.LA(1)>=DISTINCT && input.LA(1)<=THEN)||(input.LA(1)>=CURRENT_TIME && input.LA(1)<=CURRENT_TIMESTAMP)||(input.LA(1)>=RAISE && input.LA(1)<=FAIL)||(input.LA(1)>=PRAGMA && input.LA(1)<=FOR)||(input.LA(1)>=UNIQUE && input.LA(1)<=ROW) ) {
                input.consume();
                adaptor.addChild(root_0, (Object)adaptor.create(set489));
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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:583:1: id_column_def : ( ID | keyword_column_def );
    public final SqlParser.id_column_def_return id_column_def() throws RecognitionException {
        SqlParser.id_column_def_return retval = new SqlParser.id_column_def_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID490=null;
        SqlParser.keyword_column_def_return keyword_column_def491 = null;


        Object ID490_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:583:14: ( ID | keyword_column_def )
            int alt180=2;
            int LA180_0 = input.LA(1);

            if ( (LA180_0==ID) ) {
                alt180=1;
            }
            else if ( ((LA180_0>=EXPLAIN && LA180_0<=PLAN)||(LA180_0>=INDEXED && LA180_0<=IN)||(LA180_0>=ISNULL && LA180_0<=BETWEEN)||(LA180_0>=LIKE && LA180_0<=MATCH)||LA180_0==COLLATE||(LA180_0>=DISTINCT && LA180_0<=THEN)||(LA180_0>=CURRENT_TIME && LA180_0<=CURRENT_TIMESTAMP)||(LA180_0>=RAISE && LA180_0<=FAIL)||(LA180_0>=PRAGMA && LA180_0<=EXISTS)||(LA180_0>=PRIMARY && LA180_0<=FOR)||(LA180_0>=UNIQUE && LA180_0<=ADD)||(LA180_0>=VIEW && LA180_0<=ROW)) ) {
                alt180=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 180, 0, input);

                throw nvae;
            }
            switch (alt180) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:583:16: ID
                    {
                    root_0 = (Object)adaptor.nil();

                    ID490=(Token)match(input,ID,FOLLOW_ID_in_id_column_def4733); 
                    ID490_tree = (Object)adaptor.create(ID490);
                    adaptor.addChild(root_0, ID490_tree);


                    }
                    break;
                case 2 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:583:21: keyword_column_def
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_keyword_column_def_in_id_column_def4737);
                    keyword_column_def491=keyword_column_def();

                    state._fsp--;

                    adaptor.addChild(root_0, keyword_column_def491.getTree());

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
    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:585:1: keyword_column_def : ( ABORT | ADD | AFTER | ALL | ALTER | ANALYZE | AND | AS | ASC | ATTACH | AUTOINCREMENT | BEFORE | BEGIN | BETWEEN | BY | CASCADE | CASE | CAST | CHECK | COLLATE | COMMIT | CONFLICT | CREATE | CROSS | CURRENT_TIME | CURRENT_DATE | CURRENT_TIMESTAMP | DATABASE | DEFAULT | DEFERRABLE | DEFERRED | DELETE | DESC | DETACH | DISTINCT | DROP | EACH | ELSE | END | ESCAPE | EXCEPT | EXCLUSIVE | EXISTS | EXPLAIN | FAIL | FOR | FOREIGN | FROM | GLOB | GROUP | HAVING | IF | IGNORE | IMMEDIATE | IN | INDEX | INDEXED | INITIALLY | INNER | INSERT | INSTEAD | INTERSECT | INTO | IS | ISNULL | JOIN | KEY | LEFT | LIKE | LIMIT | MATCH | NATURAL | NOT | NOTNULL | NULL | OF | OFFSET | ON | OR | ORDER | OUTER | PLAN | PRAGMA | PRIMARY | QUERY | RAISE | REFERENCES | REGEXP | REINDEX | RELEASE | RENAME | REPLACE | RESTRICT | ROLLBACK | ROW | SAVEPOINT | SELECT | SET | TABLE | TEMPORARY | THEN | TO | TRANSACTION | TRIGGER | UNION | UNIQUE | UPDATE | USING | VACUUM | VALUES | VIEW | VIRTUAL | WHEN | WHERE ) ;
    public final SqlParser.keyword_column_def_return keyword_column_def() throws RecognitionException {
        SqlParser.keyword_column_def_return retval = new SqlParser.keyword_column_def_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set492=null;

        Object set492_tree=null;

        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:585:19: ( ( ABORT | ADD | AFTER | ALL | ALTER | ANALYZE | AND | AS | ASC | ATTACH | AUTOINCREMENT | BEFORE | BEGIN | BETWEEN | BY | CASCADE | CASE | CAST | CHECK | COLLATE | COMMIT | CONFLICT | CREATE | CROSS | CURRENT_TIME | CURRENT_DATE | CURRENT_TIMESTAMP | DATABASE | DEFAULT | DEFERRABLE | DEFERRED | DELETE | DESC | DETACH | DISTINCT | DROP | EACH | ELSE | END | ESCAPE | EXCEPT | EXCLUSIVE | EXISTS | EXPLAIN | FAIL | FOR | FOREIGN | FROM | GLOB | GROUP | HAVING | IF | IGNORE | IMMEDIATE | IN | INDEX | INDEXED | INITIALLY | INNER | INSERT | INSTEAD | INTERSECT | INTO | IS | ISNULL | JOIN | KEY | LEFT | LIKE | LIMIT | MATCH | NATURAL | NOT | NOTNULL | NULL | OF | OFFSET | ON | OR | ORDER | OUTER | PLAN | PRAGMA | PRIMARY | QUERY | RAISE | REFERENCES | REGEXP | REINDEX | RELEASE | RENAME | REPLACE | RESTRICT | ROLLBACK | ROW | SAVEPOINT | SELECT | SET | TABLE | TEMPORARY | THEN | TO | TRANSACTION | TRIGGER | UNION | UNIQUE | UPDATE | USING | VACUUM | VALUES | VIEW | VIRTUAL | WHEN | WHERE ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:585:21: ( ABORT | ADD | AFTER | ALL | ALTER | ANALYZE | AND | AS | ASC | ATTACH | AUTOINCREMENT | BEFORE | BEGIN | BETWEEN | BY | CASCADE | CASE | CAST | CHECK | COLLATE | COMMIT | CONFLICT | CREATE | CROSS | CURRENT_TIME | CURRENT_DATE | CURRENT_TIMESTAMP | DATABASE | DEFAULT | DEFERRABLE | DEFERRED | DELETE | DESC | DETACH | DISTINCT | DROP | EACH | ELSE | END | ESCAPE | EXCEPT | EXCLUSIVE | EXISTS | EXPLAIN | FAIL | FOR | FOREIGN | FROM | GLOB | GROUP | HAVING | IF | IGNORE | IMMEDIATE | IN | INDEX | INDEXED | INITIALLY | INNER | INSERT | INSTEAD | INTERSECT | INTO | IS | ISNULL | JOIN | KEY | LEFT | LIKE | LIMIT | MATCH | NATURAL | NOT | NOTNULL | NULL | OF | OFFSET | ON | OR | ORDER | OUTER | PLAN | PRAGMA | PRIMARY | QUERY | RAISE | REFERENCES | REGEXP | REINDEX | RELEASE | RENAME | REPLACE | RESTRICT | ROLLBACK | ROW | SAVEPOINT | SELECT | SET | TABLE | TEMPORARY | THEN | TO | TRANSACTION | TRIGGER | UNION | UNIQUE | UPDATE | USING | VACUUM | VALUES | VIEW | VIRTUAL | WHEN | WHERE )
            {
            root_0 = (Object)adaptor.nil();

            set492=(Token)input.LT(1);
            if ( (input.LA(1)>=EXPLAIN && input.LA(1)<=PLAN)||(input.LA(1)>=INDEXED && input.LA(1)<=IN)||(input.LA(1)>=ISNULL && input.LA(1)<=BETWEEN)||(input.LA(1)>=LIKE && input.LA(1)<=MATCH)||input.LA(1)==COLLATE||(input.LA(1)>=DISTINCT && input.LA(1)<=THEN)||(input.LA(1)>=CURRENT_TIME && input.LA(1)<=CURRENT_TIMESTAMP)||(input.LA(1)>=RAISE && input.LA(1)<=FAIL)||(input.LA(1)>=PRAGMA && input.LA(1)<=EXISTS)||(input.LA(1)>=PRIMARY && input.LA(1)<=FOR)||(input.LA(1)>=UNIQUE && input.LA(1)<=ADD)||(input.LA(1)>=VIEW && input.LA(1)<=ROW) ) {
                input.consume();
                adaptor.addChild(root_0, (Object)adaptor.create(set492));
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
    protected DFA167 dfa167 = new DFA167(this);
    protected DFA168 dfa168 = new DFA168(this);
    static final String DFA1_eotS =
        "\26\uffff";
    static final String DFA1_eofS =
        "\1\1\25\uffff";
    static final String DFA1_minS =
        "\1\40\25\uffff";
    static final String DFA1_maxS =
        "\1\u00a9\25\uffff";
    static final String DFA1_acceptS =
        "\1\uffff\1\2\1\1\23\uffff";
    static final String DFA1_specialS =
        "\26\uffff}>";
    static final String[] DFA1_transitionS = {
            "\1\2\61\uffff\1\2\16\uffff\1\2\3\uffff\2\2\1\uffff\5\2\11\uffff"+
            "\1\2\14\uffff\1\2\3\uffff\1\2\1\uffff\2\2\4\uffff\1\2\1\uffff"+
            "\2\2\1\uffff\1\2\23\uffff\2\2",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
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
        "\25\uffff";
    static final String DFA3_eofS =
        "\25\uffff";
    static final String DFA3_minS =
        "\1\40\24\uffff";
    static final String DFA3_maxS =
        "\1\u00a9\24\uffff";
    static final String DFA3_acceptS =
        "\1\uffff\1\1\1\2\22\uffff";
    static final String DFA3_specialS =
        "\25\uffff}>";
    static final String[] DFA3_transitionS = {
            "\1\1\61\uffff\1\2\16\uffff\1\2\3\uffff\2\2\1\uffff\5\2\11\uffff"+
            "\1\2\14\uffff\1\2\3\uffff\1\2\1\uffff\2\2\4\uffff\1\2\1\uffff"+
            "\2\2\1\uffff\1\2\23\uffff\2\2",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
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
        "\25\uffff";
    static final String DFA2_eofS =
        "\25\uffff";
    static final String DFA2_minS =
        "\1\41\24\uffff";
    static final String DFA2_maxS =
        "\1\u00a9\24\uffff";
    static final String DFA2_acceptS =
        "\1\uffff\1\1\1\2\22\uffff";
    static final String DFA2_specialS =
        "\25\uffff}>";
    static final String[] DFA2_transitionS = {
            "\1\1\60\uffff\1\2\16\uffff\1\2\3\uffff\2\2\1\uffff\5\2\11\uffff"+
            "\1\2\14\uffff\1\2\3\uffff\1\2\1\uffff\2\2\4\uffff\1\2\1\uffff"+
            "\2\2\1\uffff\1\2\23\uffff\2\2",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
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
        "\42\uffff";
    static final String DFA4_eofS =
        "\42\uffff";
    static final String DFA4_minS =
        "\1\122\20\uffff\1\u0095\1\u0096\2\uffff\1\u0096\14\uffff";
    static final String DFA4_maxS =
        "\1\u00a9\20\uffff\2\u00af\2\uffff\1\u00af\14\uffff";
    static final String DFA4_acceptS =
        "\1\uffff\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\uffff\1\11\1\12\1\13"+
        "\1\14\1\15\1\16\1\17\2\uffff\1\23\1\20\1\uffff\1\21\1\30\1\24\1"+
        "\26\1\uffff\1\22\1\25\1\27\1\31\3\uffff";
    static final String DFA4_specialS =
        "\42\uffff}>";
    static final String[] DFA4_transitionS = {
            "\1\15\16\uffff\1\16\3\uffff\1\1\1\2\1\uffff\1\3\1\4\1\5\1\6"+
            "\1\10\11\uffff\1\7\14\uffff\1\10\3\uffff\1\12\1\uffff\1\13\1"+
            "\14\4\uffff\1\15\1\uffff\1\17\1\20\1\uffff\1\21\23\uffff\1\22"+
            "\1\23",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\24\1\26\1\25\10\uffff\1\31\14\uffff\1\30\1\31\1\27",
            "\1\33\26\uffff\1\34\1\35\1\36",
            "",
            "",
            "\1\26\26\uffff\1\30\1\uffff\1\27",
            "",
            "",
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
            return "102:1: sql_stmt_core : ( pragma_stmt | attach_stmt | detach_stmt | analyze_stmt | reindex_stmt | vacuum_stmt | select_stmt | insert_stmt | update_stmt | delete_stmt | begin_stmt | commit_stmt | rollback_stmt | savepoint_stmt | release_stmt | create_virtual_table_stmt | create_table_stmt | drop_table_stmt | alter_table_stmt | create_view_stmt | drop_view_stmt | create_index_stmt | drop_index_stmt | create_trigger_stmt | drop_trigger_stmt );";
        }
    }
    static final String DFA5_eotS =
        "\23\uffff";
    static final String DFA5_eofS =
        "\23\uffff";
    static final String DFA5_minS =
        "\1\40\2\43\20\uffff";
    static final String DFA5_maxS =
        "\1\u00b5\2\u0088\20\uffff";
    static final String DFA5_acceptS =
        "\3\uffff\1\1\1\2\16\uffff";
    static final String DFA5_specialS =
        "\23\uffff}>";
    static final String[] DFA5_transitionS = {
            "\3\2\2\uffff\2\2\1\uffff\3\2\6\uffff\3\2\27\uffff\1\2\1\1\10"+
            "\2\4\uffff\3\2\3\uffff\5\2\1\uffff\72\2\1\uffff\26\2",
            "\1\4\1\3\1\4\1\uffff\1\4\107\uffff\2\4\7\uffff\1\4\17\uffff"+
            "\1\4",
            "\1\4\1\3\1\4\1\uffff\1\4\107\uffff\2\4\7\uffff\1\4\17\uffff"+
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
            return "132:23: (database_name= id DOT )?";
        }
    }
    static final String DFA7_eotS =
        "\137\uffff";
    static final String DFA7_eofS =
        "\137\uffff";
    static final String DFA7_minS =
        "\1\40\33\uffff\1\40\21\uffff\1\40\2\uffff\1\40\6\44\47\uffff";
    static final String DFA7_maxS =
        "\1\u00b5\33\uffff\1\u00b5\21\uffff\1\u00b5\2\uffff\1\u00b5\1\46"+
        "\3\166\1\46\1\125\47\uffff";
    static final String DFA7_acceptS =
        "\1\uffff\1\2\34\uffff\1\1\100\uffff";
    static final String DFA7_specialS =
        "\137\uffff}>";
    static final String[] DFA7_transitionS = {
            "\4\1\1\uffff\2\1\1\uffff\1\34\2\1\2\uffff\2\1\2\uffff\3\1\27"+
            "\uffff\12\1\4\uffff\3\1\3\uffff\5\1\1\uffff\72\1\1\uffff\26"+
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
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\3\36\1\1\1\uffff\6\36\1\uffff\1\36\2\1\2\uffff\3\36\20\uffff"+
            "\2\36\4\uffff\32\36\1\uffff\12\36\1\66\1\67\1\36\1\63\1\36\1"+
            "\64\1\65\1\36\1\56\1\61\1\62\45\36\1\uffff\26\36",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
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
            "\uffff\12\1\4\uffff\3\1\3\uffff\5\1\1\uffff\72\1\1\uffff\26"+
            "\1",
            "",
            "",
            "\3\1\1\uffff\1\36\6\1\1\uffff\1\1\4\uffff\3\1\20\uffff\2\1"+
            "\4\uffff\32\1\1\uffff\72\1\1\uffff\26\1",
            "\1\36\1\uffff\1\1",
            "\1\36\116\uffff\1\1\2\uffff\1\1",
            "\1\36\121\uffff\1\1",
            "\1\36\121\uffff\1\1",
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
            return "()* loopback of 134:18: ( OR or_subexpr )*";
        }
    }
    static final String DFA8_eotS =
        "\140\uffff";
    static final String DFA8_eofS =
        "\140\uffff";
    static final String DFA8_minS =
        "\1\40\34\uffff\1\40\2\uffff\2\40\6\44\70\uffff";
    static final String DFA8_maxS =
        "\1\u00b5\34\uffff\1\u00b5\2\uffff\2\u00b5\1\46\3\166\1\46\1\125"+
        "\70\uffff";
    static final String DFA8_acceptS =
        "\1\uffff\1\2\50\uffff\1\1\65\uffff";
    static final String DFA8_specialS =
        "\140\uffff}>";
    static final String[] DFA8_transitionS = {
            "\4\1\1\uffff\2\1\1\uffff\1\1\1\35\1\1\2\uffff\2\1\2\uffff\3"+
            "\1\27\uffff\12\1\4\uffff\3\1\3\uffff\5\1\1\uffff\72\1\1\uffff"+
            "\26\1",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\3\52\1\1\1\uffff\6\52\1\uffff\1\52\2\1\2\uffff\3\52\20\uffff"+
            "\2\52\4\uffff\32\52\1\uffff\12\52\1\46\1\47\1\52\1\43\1\52\1"+
            "\44\1\45\1\52\1\40\1\41\1\42\45\52\1\uffff\26\52",
            "",
            "",
            "\3\1\1\uffff\1\52\2\1\1\uffff\3\1\1\uffff\1\1\4\uffff\3\1\27"+
            "\uffff\12\1\4\uffff\3\1\3\uffff\5\1\1\uffff\72\1\1\uffff\26"+
            "\1",
            "\3\1\1\uffff\1\52\6\1\1\uffff\1\1\4\uffff\3\1\20\uffff\2\1"+
            "\4\uffff\32\1\1\uffff\72\1\1\uffff\26\1",
            "\1\52\1\uffff\1\1",
            "\1\52\116\uffff\1\1\2\uffff\1\1",
            "\1\52\121\uffff\1\1",
            "\1\52\121\uffff\1\1",
            "\1\52\1\uffff\1\1",
            "\1\52\60\uffff\1\1",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
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
            return "()* loopback of 136:25: ( AND and_subexpr )*";
        }
    }
    static final String DFA9_eotS =
        "\165\uffff";
    static final String DFA9_eofS =
        "\165\uffff";
    static final String DFA9_minS =
        "\1\40\5\uffff\1\43\1\40\55\uffff\2\40\6\44\70\uffff";
    static final String DFA9_maxS =
        "\1\u00b5\5\uffff\1\171\1\u00b5\55\uffff\2\u00b5\1\46\3\166\1\46"+
        "\1\125\70\uffff";
    static final String DFA9_acceptS =
        "\1\uffff\1\1\7\uffff\1\2\153\uffff";
    static final String DFA9_specialS =
        "\165\uffff}>";
    static final String[] DFA9_transitionS = {
            "\4\11\1\uffff\2\11\1\1\3\11\1\1\1\uffff\2\11\2\1\1\6\1\11\1"+
            "\7\10\1\17\uffff\12\11\4\uffff\3\11\3\uffff\5\11\1\uffff\72"+
            "\11\1\uffff\26\11",
            "",
            "",
            "",
            "",
            "",
            "\1\11\3\uffff\1\1\5\uffff\2\11\3\uffff\1\1\74\uffff\2\11\1"+
            "\uffff\1\11\1\uffff\2\11\1\uffff\3\11",
            "\3\1\1\11\1\uffff\6\1\1\uffff\1\1\2\11\2\uffff\3\1\20\uffff"+
            "\2\1\4\uffff\32\1\1\uffff\12\1\1\73\1\74\1\1\1\70\1\1\1\71\1"+
            "\72\1\1\1\65\1\66\1\67\45\1\1\uffff\26\1",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
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
            "\11\27\uffff\12\11\4\uffff\3\11\3\uffff\5\11\1\uffff\72\11\1"+
            "\uffff\26\11",
            "\3\11\1\uffff\1\1\6\11\1\uffff\1\11\4\uffff\3\11\20\uffff\2"+
            "\11\4\uffff\32\11\1\uffff\72\11\1\uffff\26\11",
            "\1\1\1\uffff\1\11",
            "\1\1\116\uffff\1\11\2\uffff\1\11",
            "\1\1\121\uffff\1\11",
            "\1\1\121\uffff\1\11",
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
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
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
            return "138:34: ( cond_expr )?";
        }
    }
    static final String DFA19_eotS =
        "\23\uffff";
    static final String DFA19_eofS =
        "\23\uffff";
    static final String DFA19_minS =
        "\1\47\1\53\1\uffff\1\40\6\uffff\1\40\10\uffff";
    static final String DFA19_maxS =
        "\2\73\1\uffff\1\u00b5\6\uffff\1\u00b5\10\uffff";
    static final String DFA19_acceptS =
        "\2\uffff\1\1\1\uffff\1\4\2\uffff\1\5\1\6\4\uffff\1\2\1\3\4\uffff";
    static final String DFA19_specialS =
        "\23\uffff}>";
    static final String[] DFA19_transitionS = {
            "\1\1\3\uffff\1\3\3\uffff\3\4\1\uffff\1\7\4\10\4\2",
            "\1\12\6\uffff\1\4\1\7\4\uffff\4\2",
            "",
            "\3\16\2\uffff\2\16\1\uffff\3\16\1\uffff\1\15\4\uffff\3\16\27"+
            "\uffff\12\16\4\uffff\3\16\3\uffff\5\16\1\uffff\72\16\1\uffff"+
            "\26\16",
            "",
            "",
            "",
            "",
            "",
            "",
            "\3\16\2\uffff\2\16\1\uffff\3\16\1\uffff\1\15\4\uffff\3\16\27"+
            "\uffff\12\16\4\uffff\3\16\3\uffff\5\16\1\uffff\72\16\1\uffff"+
            "\26\16",
            "",
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
            return "140:1: cond_expr : ( ( NOT )? match_op match_expr= eq_subexpr ( ESCAPE escape_expr= eq_subexpr )? -> ^( match_op $match_expr ( NOT )? ( ^( ESCAPE $escape_expr) )? ) | ( NOT )? IN LPAREN expr ( COMMA expr )* RPAREN -> ^( IN_VALUES ( NOT )? ^( IN ( expr )+ ) ) | ( NOT )? IN (database_name= id DOT )? table_name= id -> ^( IN_TABLE ( NOT )? ^( IN ^( $table_name ( $database_name)? ) ) ) | ( ISNULL -> IS_NULL | NOTNULL -> NOT_NULL | IS NULL -> IS_NULL | NOT NULL -> NOT_NULL | IS NOT NULL -> NOT_NULL ) | ( NOT )? BETWEEN e1= eq_subexpr AND e2= eq_subexpr -> ^( BETWEEN ( NOT )? ^( AND $e1 $e2) ) | ( ( EQUALS | EQUALS2 | NOT_EQUALS | NOT_EQUALS2 ) eq_subexpr )+ );";
        }
    }
    static final String DFA11_eotS =
        "\141\uffff";
    static final String DFA11_eofS =
        "\141\uffff";
    static final String DFA11_minS =
        "\2\40\37\uffff\2\40\6\44\70\uffff";
    static final String DFA11_maxS =
        "\2\u00b5\37\uffff\2\u00b5\1\46\3\166\1\46\1\125\70\uffff";
    static final String DFA11_acceptS =
        "\2\uffff\1\2\50\uffff\1\1\65\uffff";
    static final String DFA11_specialS =
        "\141\uffff}>";
    static final String[] DFA11_transitionS = {
            "\4\2\1\uffff\2\2\1\uffff\2\2\1\1\2\uffff\2\2\2\uffff\3\2\27"+
            "\uffff\12\2\4\uffff\3\2\3\uffff\5\2\1\uffff\72\2\1\uffff\26"+
            "\2",
            "\3\53\1\2\1\uffff\6\53\1\uffff\1\53\2\2\2\uffff\3\53\20\uffff"+
            "\2\53\4\uffff\32\53\1\uffff\12\53\1\47\1\50\1\53\1\44\1\53\1"+
            "\45\1\46\1\53\1\41\1\42\1\43\45\53\1\uffff\26\53",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\3\2\1\uffff\1\53\2\2\1\uffff\3\2\1\uffff\1\2\4\uffff\3\2\27"+
            "\uffff\12\2\4\uffff\3\2\3\uffff\5\2\1\uffff\72\2\1\uffff\26"+
            "\2",
            "\3\2\1\uffff\1\53\6\2\1\uffff\1\2\4\uffff\3\2\20\uffff\2\2"+
            "\4\uffff\32\2\1\uffff\72\2\1\uffff\26\2",
            "\1\53\1\uffff\1\2",
            "\1\53\116\uffff\1\2\2\uffff\1\2",
            "\1\53\121\uffff\1\2",
            "\1\53\121\uffff\1\2",
            "\1\53\1\uffff\1\2",
            "\1\53\60\uffff\1\2",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
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
            return "141:41: ( ESCAPE escape_expr= eq_subexpr )?";
        }
    }
    static final String DFA15_eotS =
        "\101\uffff";
    static final String DFA15_eofS =
        "\101\uffff";
    static final String DFA15_minS =
        "\3\40\76\uffff";
    static final String DFA15_maxS =
        "\3\u00b5\76\uffff";
    static final String DFA15_acceptS =
        "\3\uffff\1\1\1\2\74\uffff";
    static final String DFA15_specialS =
        "\101\uffff}>";
    static final String[] DFA15_transitionS = {
            "\3\2\2\uffff\2\2\1\uffff\3\2\6\uffff\3\2\27\uffff\1\2\1\1\10"+
            "\2\4\uffff\3\2\3\uffff\5\2\1\uffff\72\2\1\uffff\26\2",
            "\4\4\1\3\2\4\1\uffff\3\4\2\uffff\2\4\2\uffff\3\4\27\uffff\12"+
            "\4\4\uffff\3\4\3\uffff\5\4\1\uffff\72\4\1\uffff\26\4",
            "\4\4\1\3\2\4\1\uffff\3\4\2\uffff\2\4\2\uffff\3\4\27\uffff\12"+
            "\4\4\uffff\3\4\3\uffff\5\4\1\uffff\72\4\1\uffff\26\4",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
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
            return "143:13: (database_name= id DOT )?";
        }
    }
    static final String DFA18_eotS =
        "\40\uffff";
    static final String DFA18_eofS =
        "\40\uffff";
    static final String DFA18_minS =
        "\1\40\37\uffff";
    static final String DFA18_maxS =
        "\1\u00b5\37\uffff";
    static final String DFA18_acceptS =
        "\1\uffff\1\2\35\uffff\1\1";
    static final String DFA18_specialS =
        "\40\uffff}>";
    static final String[] DFA18_transitionS = {
            "\4\1\1\uffff\2\1\1\uffff\3\1\2\uffff\2\1\2\uffff\3\1\4\37\23"+
            "\uffff\12\1\4\uffff\3\1\3\uffff\5\1\1\uffff\72\1\1\uffff\26"+
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
            "",
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
            return "()+ loopback of 148:5: ( ( EQUALS | EQUALS2 | NOT_EQUALS | NOT_EQUALS2 ) eq_subexpr )+";
        }
    }
    static final String DFA20_eotS =
        "\51\uffff";
    static final String DFA20_eofS =
        "\51\uffff";
    static final String DFA20_minS =
        "\1\40\50\uffff";
    static final String DFA20_maxS =
        "\1\u00b5\50\uffff";
    static final String DFA20_acceptS =
        "\1\uffff\1\2\46\uffff\1\1";
    static final String DFA20_specialS =
        "\51\uffff}>";
    static final String[] DFA20_transitionS = {
            "\4\1\1\uffff\7\1\1\uffff\17\1\4\50\13\uffff\12\1\4\uffff\3\1"+
            "\3\uffff\5\1\1\uffff\72\1\1\uffff\26\1",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
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
            return "()* loopback of 153:25: ( ( LESS | LESS_OR_EQ | GREATER | GREATER_OR_EQ ) neq_subexpr )*";
        }
    }
    static final String DFA21_eotS =
        "\52\uffff";
    static final String DFA21_eofS =
        "\52\uffff";
    static final String DFA21_minS =
        "\1\40\51\uffff";
    static final String DFA21_maxS =
        "\1\u00b5\51\uffff";
    static final String DFA21_acceptS =
        "\1\uffff\1\2\47\uffff\1\1";
    static final String DFA21_specialS =
        "\52\uffff}>";
    static final String[] DFA21_transitionS = {
            "\4\1\1\uffff\7\1\1\uffff\23\1\4\51\7\uffff\12\1\4\uffff\3\1"+
            "\3\uffff\5\1\1\uffff\72\1\1\uffff\26\1",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
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
            return "()* loopback of 155:26: ( ( SHIFT_LEFT | SHIFT_RIGHT | AMPERSAND | PIPE ) bit_subexpr )*";
        }
    }
    static final String DFA22_eotS =
        "\53\uffff";
    static final String DFA22_eofS =
        "\53\uffff";
    static final String DFA22_minS =
        "\1\40\52\uffff";
    static final String DFA22_maxS =
        "\1\u00b5\52\uffff";
    static final String DFA22_acceptS =
        "\1\uffff\1\2\50\uffff\1\1";
    static final String DFA22_specialS =
        "\53\uffff}>";
    static final String[] DFA22_transitionS = {
            "\4\1\1\uffff\7\1\1\uffff\27\1\2\52\5\uffff\12\1\4\uffff\3\1"+
            "\3\uffff\5\1\1\uffff\72\1\1\uffff\26\1",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
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
            return "()* loopback of 157:26: ( ( PLUS | MINUS ) add_subexpr )*";
        }
    }
    static final String DFA23_eotS =
        "\54\uffff";
    static final String DFA23_eofS =
        "\54\uffff";
    static final String DFA23_minS =
        "\1\40\53\uffff";
    static final String DFA23_maxS =
        "\1\u00b5\53\uffff";
    static final String DFA23_acceptS =
        "\1\uffff\1\2\51\uffff\1\1";
    static final String DFA23_specialS =
        "\54\uffff}>";
    static final String[] DFA23_transitionS = {
            "\4\1\1\uffff\7\1\1\uffff\31\1\3\53\2\uffff\12\1\4\uffff\3\1"+
            "\3\uffff\5\1\1\uffff\72\1\1\uffff\26\1",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
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
            return "()* loopback of 159:26: ( ( ASTERISK | SLASH | PERCENT ) mul_subexpr )*";
        }
    }
    static final String DFA24_eotS =
        "\55\uffff";
    static final String DFA24_eofS =
        "\55\uffff";
    static final String DFA24_minS =
        "\1\40\54\uffff";
    static final String DFA24_maxS =
        "\1\u00b5\54\uffff";
    static final String DFA24_acceptS =
        "\1\uffff\1\2\52\uffff\1\1";
    static final String DFA24_specialS =
        "\55\uffff}>";
    static final String[] DFA24_transitionS = {
            "\4\1\1\uffff\7\1\1\uffff\34\1\1\54\1\uffff\12\1\4\uffff\3\1"+
            "\3\uffff\5\1\1\uffff\72\1\1\uffff\26\1",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
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
            return "()* loopback of 161:26: ( DOUBLE_PIPE con_subexpr )*";
        }
    }
    static final String DFA25_eotS =
        "\23\uffff";
    static final String DFA25_eofS =
        "\23\uffff";
    static final String DFA25_minS =
        "\1\40\22\uffff";
    static final String DFA25_maxS =
        "\1\u00b5\22\uffff";
    static final String DFA25_acceptS =
        "\1\uffff\1\1\20\uffff\1\2";
    static final String DFA25_specialS =
        "\23\uffff}>";
    static final String[] DFA25_transitionS = {
            "\3\1\2\uffff\2\1\1\22\3\1\1\uffff\1\1\4\uffff\3\1\20\uffff\2"+
            "\22\4\uffff\1\22\31\1\1\uffff\72\1\1\uffff\26\1",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
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
            return "163:1: con_subexpr : ( unary_subexpr | unary_op unary_subexpr -> ^( unary_op unary_subexpr ) );";
        }
    }
    static final String DFA26_eotS =
        "\72\uffff";
    static final String DFA26_eofS =
        "\72\uffff";
    static final String DFA26_minS =
        "\1\40\1\43\70\uffff";
    static final String DFA26_maxS =
        "\1\u00b5\1\171\70\uffff";
    static final String DFA26_acceptS =
        "\2\uffff\1\2\53\uffff\1\1\13\uffff";
    static final String DFA26_specialS =
        "\72\uffff}>";
    static final String[] DFA26_transitionS = {
            "\4\2\1\uffff\7\2\1\uffff\35\2\1\uffff\1\1\11\2\4\uffff\3\2\3"+
            "\uffff\5\2\1\uffff\72\2\1\uffff\26\2",
            "\1\2\11\uffff\2\2\35\uffff\1\56\42\uffff\2\2\1\uffff\1\2\1"+
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
            return "167:26: ( COLLATE collation_name= ID )?";
        }
    }
    static final String DFA35_eotS =
        "\u0111\uffff";
    static final String DFA35_eofS =
        "\u0111\uffff";
    static final String DFA35_minS =
        "\1\40\4\uffff\4\40\3\uffff\1\40\1\44\1\uffff\1\40\1\44\u0100\uffff";
    static final String DFA35_maxS =
        "\1\u00b5\4\uffff\4\u00b5\3\uffff\1\u00b5\1\54\1\uffff\1\u00b5\1"+
        "\54\u0100\uffff";
    static final String DFA35_acceptS =
        "\1\uffff\1\1\7\uffff\1\2\4\uffff\1\5\2\uffff\1\3\u00b8\uffff\1\4"+
        "\56\uffff\1\6\1\uffff\1\7\23\uffff\1\10\1\uffff";
    static final String DFA35_specialS =
        "\u0111\uffff}>";
    static final String[] DFA35_transitionS = {
            "\3\21\2\uffff\2\21\1\uffff\3\21\1\uffff\1\16\4\uffff\1\21\1"+
            "\5\1\21\27\uffff\1\21\1\14\1\21\1\15\1\21\1\17\4\21\4\1\1\6"+
            "\1\7\1\10\3\11\1\20\4\21\1\uffff\72\21\1\uffff\26\21",
            "",
            "",
            "",
            "",
            "\4\1\1\21\7\1\1\uffff\35\1\1\uffff\12\1\4\uffff\3\1\3\uffff"+
            "\5\1\1\uffff\72\1\1\uffff\26\1",
            "\4\1\1\21\7\1\1\uffff\35\1\1\uffff\12\1\4\uffff\3\1\3\uffff"+
            "\5\1\1\uffff\72\1\1\uffff\26\1",
            "\4\1\1\21\7\1\1\uffff\35\1\1\uffff\12\1\4\uffff\3\1\3\uffff"+
            "\5\1\1\uffff\72\1\1\uffff\26\1",
            "\4\1\1\21\7\1\1\uffff\35\1\1\uffff\12\1\4\uffff\3\1\3\uffff"+
            "\5\1\1\uffff\72\1\1\uffff\26\1",
            "",
            "",
            "",
            "\14\21\1\u00ca\35\21\1\uffff\12\21\4\uffff\3\21\3\uffff\5\21"+
            "\1\uffff\72\21\1\uffff\26\21",
            "\1\21\7\uffff\1\u00f9",
            "",
            "\3\u00fb\1\uffff\1\21\6\u00fb\1\uffff\1\u00fb\4\uffff\3\u00fb"+
            "\20\uffff\2\u00fb\4\uffff\32\u00fb\1\uffff\72\u00fb\1\uffff"+
            "\26\u00fb",
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
            return "169:1: atom_expr : ( literal_value | bind_parameter | ( (database_name= id DOT )? table_name= id DOT )? column_name= ID -> ^( COLUMN_EXPRESSION ^( $column_name ( ^( $table_name ( $database_name)? ) )? ) ) | name= ID LPAREN ( ( DISTINCT )? args+= expr ( COMMA args+= expr )* | ASTERISK )? RPAREN -> ^( FUNCTION_EXPRESSION $name ( DISTINCT )? ( $args)* ( ASTERISK )? ) | LPAREN expr RPAREN | CAST LPAREN expr AS type_name RPAREN | CASE (case_expr= expr )? ( when_expr )+ ( ELSE else_expr= expr )? END -> ^( CASE ( $case_expr)? ( when_expr )+ ( $else_expr)? ) | raise_function );";
        }
    }
    static final String DFA28_eotS =
        "\61\uffff";
    static final String DFA28_eofS =
        "\61\uffff";
    static final String DFA28_minS =
        "\2\40\57\uffff";
    static final String DFA28_maxS =
        "\2\u00b5\57\uffff";
    static final String DFA28_acceptS =
        "\2\uffff\1\1\1\uffff\1\2\54\uffff";
    static final String DFA28_specialS =
        "\61\uffff}>";
    static final String[] DFA28_transitionS = {
            "\3\2\2\uffff\2\2\1\uffff\3\2\6\uffff\3\2\27\uffff\1\2\1\1\10"+
            "\2\4\uffff\3\2\3\uffff\5\2\1\uffff\72\2\1\uffff\26\2",
            "\4\4\1\2\7\4\1\uffff\35\4\1\uffff\12\4\4\uffff\3\4\3\uffff"+
            "\5\4\1\uffff\72\4\1\uffff\26\4",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
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
            return "172:5: ( (database_name= id DOT )? table_name= id DOT )?";
        }
    }
    static final String DFA27_eotS =
        "\145\uffff";
    static final String DFA27_eofS =
        "\145\uffff";
    static final String DFA27_minS =
        "\1\40\2\44\3\40\1\uffff\1\40\135\uffff";
    static final String DFA27_maxS =
        "\1\u00b5\2\44\3\u00b5\1\uffff\1\u00b5\135\uffff";
    static final String DFA27_acceptS =
        "\6\uffff\1\1\3\uffff\1\2\132\uffff";
    static final String DFA27_specialS =
        "\145\uffff}>";
    static final String[] DFA27_transitionS = {
            "\3\2\2\uffff\2\2\1\uffff\3\2\6\uffff\3\2\27\uffff\1\2\1\1\10"+
            "\2\4\uffff\3\2\3\uffff\5\2\1\uffff\72\2\1\uffff\26\2",
            "\1\3",
            "\1\4",
            "\3\6\2\uffff\2\6\1\uffff\3\6\6\uffff\3\6\27\uffff\1\6\1\5\10"+
            "\6\4\uffff\3\6\3\uffff\5\6\1\uffff\72\6\1\uffff\26\6",
            "\3\6\2\uffff\2\6\1\uffff\3\6\6\uffff\3\6\27\uffff\1\6\1\7\10"+
            "\6\4\uffff\3\6\3\uffff\5\6\1\uffff\72\6\1\uffff\26\6",
            "\4\12\1\6\7\12\1\uffff\35\12\1\uffff\12\12\4\uffff\3\12\3\uffff"+
            "\5\12\1\uffff\72\12\1\uffff\26\12",
            "",
            "\4\12\1\6\7\12\1\uffff\35\12\1\uffff\12\12\4\uffff\3\12\3\uffff"+
            "\5\12\1\uffff\72\12\1\uffff\26\12",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
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
            return "172:6: (database_name= id DOT )?";
        }
    }
    static final String DFA31_eotS =
        "\26\uffff";
    static final String DFA31_eofS =
        "\26\uffff";
    static final String DFA31_minS =
        "\1\40\25\uffff";
    static final String DFA31_maxS =
        "\1\u00b5\25\uffff";
    static final String DFA31_acceptS =
        "\1\uffff\1\1\22\uffff\1\2\1\3";
    static final String DFA31_specialS =
        "\26\uffff}>";
    static final String[] DFA31_transitionS = {
            "\3\1\2\uffff\6\1\1\uffff\1\1\1\uffff\1\25\2\uffff\3\1\20\uffff"+
            "\2\1\1\24\3\uffff\32\1\1\uffff\72\1\1\uffff\26\1",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
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
            return "173:20: ( ( DISTINCT )? args+= expr ( COMMA args+= expr )* | ASTERISK )?";
        }
    }
    static final String DFA29_eotS =
        "\47\uffff";
    static final String DFA29_eofS =
        "\47\uffff";
    static final String DFA29_minS =
        "\2\40\45\uffff";
    static final String DFA29_maxS =
        "\2\u00b5\45\uffff";
    static final String DFA29_acceptS =
        "\2\uffff\1\2\22\uffff\1\1\21\uffff";
    static final String DFA29_specialS =
        "\47\uffff}>";
    static final String[] DFA29_transitionS = {
            "\3\2\2\uffff\6\2\1\uffff\1\2\4\uffff\3\2\20\uffff\2\2\4\uffff"+
            "\3\2\1\1\26\2\1\uffff\72\2\1\uffff\26\2",
            "\3\25\1\uffff\1\2\6\25\1\uffff\1\25\4\uffff\3\25\20\uffff\2"+
            "\25\4\uffff\32\25\1\uffff\72\25\1\uffff\26\25",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
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
            return "173:21: ( DISTINCT )?";
        }
    }
    static final String DFA32_eotS =
        "\47\uffff";
    static final String DFA32_eofS =
        "\47\uffff";
    static final String DFA32_minS =
        "\1\40\20\uffff\1\40\25\uffff";
    static final String DFA32_maxS =
        "\1\u00b5\20\uffff\1\u00b5\25\uffff";
    static final String DFA32_acceptS =
        "\1\uffff\1\1\22\uffff\1\2\22\uffff";
    static final String DFA32_specialS =
        "\47\uffff}>";
    static final String[] DFA32_transitionS = {
            "\3\1\2\uffff\6\1\1\uffff\1\1\4\uffff\3\1\20\uffff\2\1\4\uffff"+
            "\11\1\1\21\20\1\1\uffff\72\1\1\uffff\26\1",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
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
            "\24\4\uffff\32\24\1\uffff\72\24\1\uffff\26\24",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
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
            return "178:10: (case_expr= expr )?";
        }
    }
    static final String DFA37_eotS =
        "\62\uffff";
    static final String DFA37_eofS =
        "\62\uffff";
    static final String DFA37_minS =
        "\1\134\1\40\60\uffff";
    static final String DFA37_maxS =
        "\1\136\1\u00b5\60\uffff";
    static final String DFA37_acceptS =
        "\2\uffff\1\3\1\4\1\2\1\1\54\uffff";
    static final String DFA37_specialS =
        "\62\uffff}>";
    static final String[] DFA37_transitionS = {
            "\1\1\1\2\1\3",
            "\4\5\1\uffff\7\5\1\uffff\35\5\1\uffff\12\5\1\4\3\uffff\3\5"+
            "\3\uffff\5\5\1\uffff\72\5\1\uffff\26\5",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
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
            return "195:1: bind_parameter : ( QUESTION -> BIND | QUESTION position= INTEGER -> ^( BIND $position) | COLON name= id -> ^( BIND_NAME $name) | AT name= id -> ^( BIND_NAME $name) );";
        }
    }
    static final String DFA39_eotS =
        "\17\uffff";
    static final String DFA39_eofS =
        "\17\uffff";
    static final String DFA39_minS =
        "\1\43\16\uffff";
    static final String DFA39_maxS =
        "\1\u00a3\16\uffff";
    static final String DFA39_acceptS =
        "\1\uffff\1\2\14\uffff\1\1";
    static final String DFA39_specialS =
        "\17\uffff}>";
    static final String[] DFA39_transitionS = {
            "\1\1\3\uffff\1\1\4\uffff\3\1\3\uffff\1\1\30\uffff\1\1\1\16\71"+
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
            return "()+ loopback of 206:17: (names+= ID )+";
        }
    }
    static final String DFA42_eotS =
        "\16\uffff";
    static final String DFA42_eofS =
        "\16\uffff";
    static final String DFA42_minS =
        "\1\43\15\uffff";
    static final String DFA42_maxS =
        "\1\u00a3\15\uffff";
    static final String DFA42_acceptS =
        "\1\uffff\1\1\1\2\13\uffff";
    static final String DFA42_specialS =
        "\16\uffff}>";
    static final String[] DFA42_transitionS = {
            "\1\2\3\uffff\1\2\4\uffff\1\1\2\2\3\uffff\1\2\30\uffff\1\2\72"+
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
            return "206:23: ( LPAREN size1= ( signed_number | MAX ) ( COMMA size2= signed_number )? RPAREN )?";
        }
    }
    static final String DFA44_eotS =
        "\13\uffff";
    static final String DFA44_eofS =
        "\13\uffff";
    static final String DFA44_minS =
        "\1\40\2\43\10\uffff";
    static final String DFA44_maxS =
        "\1\u00b5\2\64\10\uffff";
    static final String DFA44_acceptS =
        "\3\uffff\1\2\2\uffff\1\1\4\uffff";
    static final String DFA44_specialS =
        "\13\uffff}>";
    static final String[] DFA44_transitionS = {
            "\3\2\2\uffff\2\2\1\uffff\3\2\6\uffff\3\2\27\uffff\1\2\1\1\10"+
            "\2\4\uffff\3\2\3\uffff\5\2\1\uffff\72\2\1\uffff\26\2",
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
            return "212:21: (database_name= id DOT )?";
        }
    }
    static final String DFA47_eotS =
        "\17\uffff";
    static final String DFA47_eofS =
        "\17\uffff";
    static final String DFA47_minS =
        "\2\40\3\uffff\1\40\3\uffff\1\40\5\uffff";
    static final String DFA47_maxS =
        "\2\u00b5\3\uffff\1\u00b5\3\uffff\1\u00b5\5\uffff";
    static final String DFA47_acceptS =
        "\2\uffff\1\2\3\uffff\1\1\10\uffff";
    static final String DFA47_specialS =
        "\17\uffff}>";
    static final String[] DFA47_transitionS = {
            "\3\2\2\uffff\2\2\1\uffff\3\2\6\uffff\3\2\27\uffff\12\2\2\uffff"+
            "\1\2\1\uffff\3\2\3\uffff\5\2\1\uffff\2\2\1\1\67\2\1\uffff\26"+
            "\2",
            "\3\6\2\uffff\2\6\1\uffff\3\6\6\uffff\3\6\27\uffff\4\6\1\5\5"+
            "\6\2\uffff\1\6\1\uffff\3\6\3\uffff\5\6\1\uffff\72\6\1\uffff"+
            "\26\6",
            "",
            "",
            "",
            "\3\2\2\uffff\2\2\1\uffff\3\2\6\uffff\3\2\27\uffff\4\2\1\11"+
            "\5\2\4\uffff\3\2\3\uffff\5\2\1\uffff\72\2\1\uffff\26\2",
            "",
            "",
            "",
            "\3\6\1\2\1\uffff\2\6\1\uffff\3\6\6\uffff\3\6\27\uffff\12\6"+
            "\4\uffff\3\6\3\uffff\5\6\1\uffff\72\6\1\uffff\26\6",
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
            return "222:21: ( DATABASE )?";
        }
    }
    static final String DFA52_eotS =
        "\14\uffff";
    static final String DFA52_eofS =
        "\14\uffff";
    static final String DFA52_minS =
        "\1\43\13\uffff";
    static final String DFA52_maxS =
        "\1\172\13\uffff";
    static final String DFA52_acceptS =
        "\1\uffff\1\1\1\2\1\3\10\uffff";
    static final String DFA52_specialS =
        "\14\uffff}>";
    static final String[] DFA52_transitionS = {
            "\1\3\11\uffff\2\3\76\uffff\1\1\1\2\2\3\1\uffff\1\3\1\uffff\2"+
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
            return "242:82: ( ASC | DESC )?";
        }
    }
    static final String DFA64_eotS =
        "\76\uffff";
    static final String DFA64_eofS =
        "\76\uffff";
    static final String DFA64_minS =
        "\3\40\73\uffff";
    static final String DFA64_maxS =
        "\3\u00b5\73\uffff";
    static final String DFA64_acceptS =
        "\3\uffff\1\3\23\uffff\1\1\23\uffff\1\2\22\uffff";
    static final String DFA64_specialS =
        "\76\uffff}>";
    static final String[] DFA64_transitionS = {
            "\3\3\2\uffff\6\3\1\uffff\1\3\4\uffff\3\3\20\uffff\3\3\3\uffff"+
            "\3\3\1\2\26\3\1\uffff\16\3\1\1\53\3\1\uffff\26\3",
            "\3\27\1\uffff\1\3\6\27\1\uffff\1\27\4\uffff\3\27\20\uffff\3"+
            "\27\3\uffff\32\27\1\uffff\72\27\1\uffff\26\27",
            "\3\53\1\uffff\1\3\6\53\1\uffff\1\53\4\uffff\3\53\20\uffff\3"+
            "\53\3\uffff\32\53\1\uffff\72\53\1\uffff\26\53",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
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
            return "263:10: ( ALL | DISTINCT )?";
        }
    }
    static final String DFA65_eotS =
        "\14\uffff";
    static final String DFA65_eofS =
        "\14\uffff";
    static final String DFA65_minS =
        "\1\43\13\uffff";
    static final String DFA65_maxS =
        "\1\171\13\uffff";
    static final String DFA65_acceptS =
        "\1\uffff\1\2\11\uffff\1\1";
    static final String DFA65_specialS =
        "\14\uffff}>";
    static final String[] DFA65_transitionS = {
            "\1\1\11\uffff\1\13\1\1\100\uffff\2\1\1\uffff\1\1\1\uffff\2\1"+
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
            return "()* loopback of 263:42: ( COMMA result_column )*";
        }
    }
    static final String DFA66_eotS =
        "\13\uffff";
    static final String DFA66_eofS =
        "\13\uffff";
    static final String DFA66_minS =
        "\1\43\12\uffff";
    static final String DFA66_maxS =
        "\1\171\12\uffff";
    static final String DFA66_acceptS =
        "\1\uffff\1\1\1\2\10\uffff";
    static final String DFA66_specialS =
        "\13\uffff}>";
    static final String[] DFA66_transitionS = {
            "\1\2\12\uffff\1\2\100\uffff\2\2\1\uffff\1\2\1\uffff\2\2\1\uffff"+
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
            return "263:65: ( FROM join_source )?";
        }
    }
    static final String DFA67_eotS =
        "\12\uffff";
    static final String DFA67_eofS =
        "\12\uffff";
    static final String DFA67_minS =
        "\1\43\11\uffff";
    static final String DFA67_maxS =
        "\1\171\11\uffff";
    static final String DFA67_acceptS =
        "\1\uffff\1\1\1\2\7\uffff";
    static final String DFA67_specialS =
        "\12\uffff}>";
    static final String[] DFA67_transitionS = {
            "\1\2\12\uffff\1\2\100\uffff\2\2\1\uffff\1\2\1\uffff\2\2\2\uffff"+
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
            return "263:85: ( WHERE where_expr= expr )?";
        }
    }
    static final String DFA68_eotS =
        "\12\uffff";
    static final String DFA68_eofS =
        "\12\uffff";
    static final String DFA68_minS =
        "\1\43\11\uffff";
    static final String DFA68_maxS =
        "\1\172\11\uffff";
    static final String DFA68_acceptS =
        "\1\uffff\1\2\7\uffff\1\1";
    static final String DFA68_specialS =
        "\12\uffff}>";
    static final String[] DFA68_transitionS = {
            "\1\1\11\uffff\1\11\1\1\100\uffff\2\1\1\uffff\1\1\1\uffff\2\1"+
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
            return "()* loopback of 264:28: ( COMMA ordering_term )*";
        }
    }
    static final String DFA73_eotS =
        "\u00e4\uffff";
    static final String DFA73_eofS =
        "\u00e4\uffff";
    static final String DFA73_minS =
        "\1\40\1\uffff\2\40\4\uffff\3\40\1\44\4\uffff\1\40\2\44\2\uffff\1"+
        "\40\36\uffff\1\40\36\uffff\1\40\36\uffff\1\40\36\uffff\1\40\37\uffff"+
        "\2\40\24\uffff\2\40\33\uffff";
    static final String DFA73_maxS =
        "\1\u00b5\1\uffff\2\u00b5\4\uffff\3\u00b5\1\54\4\uffff\1\u00b5\1"+
        "\54\1\44\2\uffff\1\u00b5\36\uffff\1\u00b5\36\uffff\1\u00b5\36\uffff"+
        "\1\u00b5\36\uffff\1\u00b5\37\uffff\2\u00b5\24\uffff\2\u00b5\33\uffff";
    static final String DFA73_acceptS =
        "\1\uffff\1\1\2\uffff\1\3\u00c4\uffff\1\2\32\uffff";
    static final String DFA73_specialS =
        "\u00e4\uffff}>";
    static final String[] DFA73_transitionS = {
            "\3\22\2\uffff\2\22\1\4\3\22\1\uffff\1\4\4\uffff\1\22\1\3\1\22"+
            "\20\uffff\2\4\1\1\3\uffff\1\4\1\22\1\2\1\22\1\13\1\22\1\20\4"+
            "\22\4\4\1\10\1\11\1\12\3\4\1\21\4\22\1\uffff\72\22\1\uffff\26"+
            "\22",
            "",
            "\4\4\1\25\45\4\1\uffff\12\4\4\uffff\3\4\3\uffff\5\4\1\uffff"+
            "\72\4\1\uffff\26\4",
            "\4\4\1\64\7\4\1\uffff\35\4\1\uffff\12\4\4\uffff\3\4\3\uffff"+
            "\5\4\1\uffff\72\4\1\uffff\26\4",
            "",
            "",
            "",
            "",
            "\4\4\1\123\7\4\1\uffff\35\4\1\uffff\12\4\4\uffff\3\4\3\uffff"+
            "\5\4\1\uffff\72\4\1\uffff\26\4",
            "\4\4\1\162\7\4\1\uffff\35\4\1\uffff\12\4\4\uffff\3\4\3\uffff"+
            "\5\4\1\uffff\72\4\1\uffff\26\4",
            "\4\4\1\u0091\7\4\1\uffff\35\4\1\uffff\12\4\4\uffff\3\4\3\uffff"+
            "\5\4\1\uffff\72\4\1\uffff\26\4",
            "\1\u00b1\7\uffff\1\4",
            "",
            "",
            "",
            "",
            "\3\4\1\uffff\1\u00b2\6\4\1\uffff\1\4\4\uffff\3\4\20\uffff\2"+
            "\4\4\uffff\32\4\1\uffff\72\4\1\uffff\26\4",
            "\1\u00c7\7\uffff\1\4",
            "\1\u00c8",
            "",
            "",
            "\3\4\2\uffff\2\4\1\uffff\3\4\6\uffff\3\4\22\uffff\1\u00c9\4"+
            "\uffff\12\4\4\uffff\3\4\3\uffff\5\4\1\uffff\72\4\1\uffff\26"+
            "\4",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
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
            "\uffff\12\4\4\uffff\3\4\3\uffff\5\4\1\uffff\72\4\1\uffff\26"+
            "\4",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
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
            "\uffff\12\4\4\uffff\3\4\3\uffff\5\4\1\uffff\72\4\1\uffff\26"+
            "\4",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
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
            "\uffff\12\4\4\uffff\3\4\3\uffff\5\4\1\uffff\72\4\1\uffff\26"+
            "\4",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
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
            "\uffff\12\4\4\uffff\3\4\3\uffff\5\4\1\uffff\72\4\1\uffff\26"+
            "\4",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
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
            "\uffff\12\4\4\uffff\3\4\3\uffff\5\4\1\uffff\72\4\1\uffff\26"+
            "\4",
            "\3\4\2\uffff\2\4\1\uffff\3\4\6\uffff\3\4\22\uffff\1\u00c9\4"+
            "\uffff\12\4\4\uffff\3\4\3\uffff\5\4\1\uffff\72\4\1\uffff\26"+
            "\4",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
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
            "\uffff\12\4\4\uffff\3\4\3\uffff\5\4\1\uffff\72\4\1\uffff\26"+
            "\4",
            "\3\4\2\uffff\2\4\1\uffff\3\4\6\uffff\3\4\22\uffff\1\u00c9\4"+
            "\uffff\12\4\4\uffff\3\4\3\uffff\5\4\1\uffff\72\4\1\uffff\26"+
            "\4",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
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
            return "270:1: result_column : ( ASTERISK | table_name= id DOT ASTERISK -> ^( ASTERISK $table_name) | expr ( ( AS )? column_alias= id )? -> ^( ALIAS expr ( $column_alias)? ) );";
        }
    }
    static final String DFA72_eotS =
        "\u00cb\uffff";
    static final String DFA72_eofS =
        "\u00cb\uffff";
    static final String DFA72_minS =
        "\1\40\2\uffff\1\40\1\uffff\1\40\6\43\4\uffff\1\40\2\uffff\1\40\6"+
        "\44\23\uffff\1\40\2\uffff\1\40\6\44\u0094\uffff";
    static final String DFA72_maxS =
        "\1\u00b5\2\uffff\1\u00b5\1\uffff\1\u00b5\6\171\4\uffff\1\u00b5\2"+
        "\uffff\1\u00b5\1\46\3\166\1\46\1\125\23\uffff\1\u00b5\2\uffff\1"+
        "\u00b5\1\46\3\166\1\46\1\125\u0094\uffff";
    static final String DFA72_acceptS =
        "\1\uffff\1\1\2\uffff\1\2\u00c6\uffff";
    static final String DFA72_specialS =
        "\u00cb\uffff}>";
    static final String[] DFA72_transitionS = {
            "\3\1\1\4\1\uffff\2\1\1\uffff\3\1\2\uffff\2\4\2\uffff\3\1\27"+
            "\uffff\12\1\4\uffff\3\1\3\uffff\5\1\1\uffff\12\1\1\12\1\13\1"+
            "\1\1\7\1\1\1\10\1\11\1\1\1\3\1\5\1\6\45\1\1\uffff\26\1",
            "",
            "",
            "\3\4\1\1\1\uffff\2\4\1\uffff\3\4\1\uffff\1\4\2\1\2\uffff\3"+
            "\4\27\uffff\12\4\4\uffff\3\4\3\uffff\5\4\1\uffff\12\4\1\30\1"+
            "\31\1\4\1\25\1\4\1\26\1\27\1\4\1\20\1\23\1\24\45\4\1\uffff\26"+
            "\4",
            "",
            "\3\4\1\1\1\uffff\6\4\1\uffff\1\4\2\1\2\uffff\3\4\20\uffff\2"+
            "\4\4\uffff\32\4\1\uffff\12\4\1\65\1\66\1\4\1\62\1\4\1\63\1\64"+
            "\1\4\1\55\1\60\1\61\45\4\1\uffff\26\4",
            "\1\1\2\uffff\1\4\6\uffff\2\1\100\uffff\2\1\1\uffff\1\1\1\uffff"+
            "\2\1\1\uffff\3\1",
            "\1\1\11\uffff\2\1\100\uffff\2\1\1\uffff\1\1\1\4\2\1\1\4\3\1",
            "\1\1\11\uffff\2\1\100\uffff\2\1\1\uffff\1\1\1\uffff\2\1\1\4"+
            "\3\1",
            "\1\1\11\uffff\2\1\100\uffff\2\1\1\uffff\1\1\1\uffff\2\1\1\4"+
            "\3\1",
            "\1\1\2\uffff\1\4\6\uffff\2\1\100\uffff\2\1\1\uffff\1\1\1\uffff"+
            "\2\1\1\uffff\3\1",
            "\1\1\11\uffff\2\1\46\uffff\1\4\31\uffff\2\1\1\uffff\1\1\1\uffff"+
            "\2\1\1\uffff\3\1",
            "",
            "",
            "",
            "",
            "\3\1\1\uffff\1\4\2\1\1\uffff\3\1\1\uffff\1\1\4\uffff\3\1\27"+
            "\uffff\12\1\4\uffff\3\1\3\uffff\5\1\1\uffff\72\1\1\uffff\26"+
            "\1",
            "",
            "",
            "\3\1\1\uffff\1\4\6\1\1\uffff\1\1\4\uffff\3\1\20\uffff\2\1\4"+
            "\uffff\32\1\1\uffff\72\1\1\uffff\26\1",
            "\1\4\1\uffff\1\1",
            "\1\4\116\uffff\1\1\2\uffff\1\1",
            "\1\4\121\uffff\1\1",
            "\1\4\121\uffff\1\1",
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
            "\3\1\1\uffff\1\4\2\1\1\uffff\3\1\1\uffff\1\1\4\uffff\3\1\27"+
            "\uffff\12\1\4\uffff\3\1\3\uffff\5\1\1\uffff\72\1\1\uffff\26"+
            "\1",
            "",
            "",
            "\3\1\1\uffff\1\4\6\1\1\uffff\1\1\4\uffff\3\1\20\uffff\2\1\4"+
            "\uffff\32\1\1\uffff\72\1\1\uffff\26\1",
            "\1\4\1\uffff\1\1",
            "\1\4\116\uffff\1\1\2\uffff\1\1",
            "\1\4\121\uffff\1\1",
            "\1\4\121\uffff\1\1",
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
            return "273:10: ( ( AS )? column_alias= id )?";
        }
    }
    static final String DFA71_eotS =
        "\u00cd\uffff";
    static final String DFA71_eofS =
        "\u00cd\uffff";
    static final String DFA71_minS =
        "\2\40\3\uffff\2\40\6\43\5\uffff\2\40\6\44\25\uffff\1\40\2\uffff"+
        "\1\40\6\44\u0094\uffff";
    static final String DFA71_maxS =
        "\2\u00b5\3\uffff\2\u00b5\6\171\5\uffff\2\u00b5\1\46\3\166\1\46\1"+
        "\125\25\uffff\1\u00b5\2\uffff\1\u00b5\1\46\3\166\1\46\1\125\u0094"+
        "\uffff";
    static final String DFA71_acceptS =
        "\2\uffff\1\2\14\uffff\1\1\u00bd\uffff";
    static final String DFA71_specialS =
        "\u00cd\uffff}>";
    static final String[] DFA71_transitionS = {
            "\3\2\2\uffff\2\2\1\uffff\3\2\6\uffff\3\2\27\uffff\4\2\1\1\5"+
            "\2\4\uffff\3\2\3\uffff\5\2\1\uffff\72\2\1\uffff\26\2",
            "\3\17\1\2\1\uffff\2\17\1\uffff\3\17\2\uffff\2\2\2\uffff\3\17"+
            "\27\uffff\12\17\4\uffff\3\17\3\uffff\5\17\1\uffff\12\17\1\13"+
            "\1\14\1\17\1\10\1\17\1\11\1\12\1\17\1\5\1\6\1\7\45\17\1\uffff"+
            "\26\17",
            "",
            "",
            "",
            "\3\2\1\17\1\uffff\2\2\1\uffff\3\2\1\uffff\1\2\2\17\2\uffff"+
            "\3\2\27\uffff\12\2\4\uffff\3\2\3\uffff\5\2\1\uffff\12\2\1\30"+
            "\1\31\1\2\1\25\1\2\1\26\1\27\1\2\1\22\1\23\1\24\45\2\1\uffff"+
            "\26\2",
            "\3\2\1\17\1\uffff\6\2\1\uffff\1\2\2\17\2\uffff\3\2\20\uffff"+
            "\2\2\4\uffff\32\2\1\uffff\12\2\1\67\1\70\1\2\1\64\1\2\1\65\1"+
            "\66\1\2\1\57\1\62\1\63\45\2\1\uffff\26\2",
            "\1\17\2\uffff\1\2\6\uffff\2\17\100\uffff\2\17\1\uffff\1\17"+
            "\1\uffff\2\17\1\uffff\3\17",
            "\1\17\11\uffff\2\17\100\uffff\2\17\1\uffff\1\17\1\2\2\17\1"+
            "\2\3\17",
            "\1\17\11\uffff\2\17\100\uffff\2\17\1\uffff\1\17\1\uffff\2\17"+
            "\1\2\3\17",
            "\1\17\11\uffff\2\17\100\uffff\2\17\1\uffff\1\17\1\uffff\2\17"+
            "\1\2\3\17",
            "\1\17\2\uffff\1\2\6\uffff\2\17\100\uffff\2\17\1\uffff\1\17"+
            "\1\uffff\2\17\1\uffff\3\17",
            "\1\17\11\uffff\2\17\46\uffff\1\2\31\uffff\2\17\1\uffff\1\17"+
            "\1\uffff\2\17\1\uffff\3\17",
            "",
            "",
            "",
            "",
            "",
            "\3\17\1\uffff\1\2\2\17\1\uffff\3\17\1\uffff\1\17\4\uffff\3"+
            "\17\27\uffff\12\17\4\uffff\3\17\3\uffff\5\17\1\uffff\72\17\1"+
            "\uffff\26\17",
            "\3\17\1\uffff\1\2\6\17\1\uffff\1\17\4\uffff\3\17\20\uffff\2"+
            "\17\4\uffff\32\17\1\uffff\72\17\1\uffff\26\17",
            "\1\2\1\uffff\1\17",
            "\1\2\116\uffff\1\17\2\uffff\1\17",
            "\1\2\121\uffff\1\17",
            "\1\2\121\uffff\1\17",
            "\1\2\1\uffff\1\17",
            "\1\2\60\uffff\1\17",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\3\17\1\uffff\1\2\2\17\1\uffff\3\17\1\uffff\1\17\4\uffff\3"+
            "\17\27\uffff\12\17\4\uffff\3\17\3\uffff\5\17\1\uffff\72\17\1"+
            "\uffff\26\17",
            "",
            "",
            "\3\17\1\uffff\1\2\6\17\1\uffff\1\17\4\uffff\3\17\20\uffff\2"+
            "\17\4\uffff\32\17\1\uffff\72\17\1\uffff\26\17",
            "\1\2\1\uffff\1\17",
            "\1\2\116\uffff\1\17\2\uffff\1\17",
            "\1\2\121\uffff\1\17",
            "\1\2\121\uffff\1\17",
            "\1\2\1\uffff\1\17",
            "\1\2\60\uffff\1\17",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
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
            return "273:11: ( AS )?";
        }
    }
    static final String DFA75_eotS =
        "\21\uffff";
    static final String DFA75_eofS =
        "\21\uffff";
    static final String DFA75_minS =
        "\1\43\20\uffff";
    static final String DFA75_maxS =
        "\1\u0080\20\uffff";
    static final String DFA75_acceptS =
        "\1\uffff\1\2\10\uffff\1\1\6\uffff";
    static final String DFA75_specialS =
        "\21\uffff}>";
    static final String[] DFA75_transitionS = {
            "\1\1\11\uffff\1\12\1\1\100\uffff\2\1\1\uffff\1\1\1\uffff\2\1"+
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
            return "()* loopback of 275:28: ( join_op single_source ( join_constraint )? )*";
        }
    }
    static final String DFA74_eotS =
        "\23\uffff";
    static final String DFA74_eofS =
        "\23\uffff";
    static final String DFA74_minS =
        "\1\43\22\uffff";
    static final String DFA74_maxS =
        "\1\u0082\22\uffff";
    static final String DFA74_acceptS =
        "\1\uffff\1\1\1\uffff\1\2\17\uffff";
    static final String DFA74_specialS =
        "\23\uffff}>";
    static final String[] DFA74_transitionS = {
            "\1\3\11\uffff\2\3\100\uffff\2\3\1\uffff\1\3\1\uffff\2\3\2\uffff"+
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
            return "275:52: ( join_constraint )?";
        }
    }
    static final String DFA82_eotS =
        "\36\uffff";
    static final String DFA82_eofS =
        "\36\uffff";
    static final String DFA82_minS =
        "\1\40\2\uffff\2\40\31\uffff";
    static final String DFA82_maxS =
        "\1\u00b5\2\uffff\2\u00b5\31\uffff";
    static final String DFA82_acceptS =
        "\1\uffff\1\1\3\uffff\1\3\3\uffff\1\2\24\uffff";
    static final String DFA82_specialS =
        "\36\uffff}>";
    static final String[] DFA82_transitionS = {
            "\3\1\2\uffff\2\1\1\uffff\3\1\1\uffff\1\3\4\uffff\3\1\27\uffff"+
            "\12\1\4\uffff\3\1\3\uffff\5\1\1\uffff\72\1\1\uffff\26\1",
            "",
            "",
            "\3\5\2\uffff\2\5\1\uffff\3\5\1\uffff\1\5\4\uffff\3\5\27\uffff"+
            "\12\5\4\uffff\3\5\3\uffff\5\5\1\uffff\21\5\1\4\50\5\1\uffff"+
            "\26\5",
            "\3\11\1\uffff\1\5\6\11\1\uffff\1\11\4\uffff\3\11\20\uffff\3"+
            "\11\3\uffff\32\11\1\uffff\72\11\1\uffff\26\11",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
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
            return "277:1: single_source : ( (database_name= id DOT )? table_name= ID ( ( AS )? table_alias= ID )? ( INDEXED BY index_name= id | NOT INDEXED )? -> ^( ALIAS ^( $table_name ( $database_name)? ) ( $table_alias)? ( ^( INDEXED ( NOT )? ( $index_name)? ) )? ) | LPAREN select_stmt RPAREN ( ( AS )? table_alias= ID )? -> ^( ALIAS select_stmt ( $table_alias)? ) | LPAREN join_source RPAREN );";
        }
    }
    static final String DFA76_eotS =
        "\32\uffff";
    static final String DFA76_eofS =
        "\32\uffff";
    static final String DFA76_minS =
        "\1\40\1\43\30\uffff";
    static final String DFA76_maxS =
        "\1\u00b5\1\u0082\30\uffff";
    static final String DFA76_acceptS =
        "\2\uffff\1\1\1\2\26\uffff";
    static final String DFA76_specialS =
        "\32\uffff}>";
    static final String[] DFA76_transitionS = {
            "\3\2\2\uffff\2\2\1\uffff\3\2\6\uffff\3\2\27\uffff\1\2\1\1\10"+
            "\2\4\uffff\3\2\3\uffff\5\2\1\uffff\72\2\1\uffff\26\2",
            "\1\3\1\2\1\3\1\uffff\1\3\5\uffff\2\3\35\uffff\1\3\2\uffff\1"+
            "\3\37\uffff\2\3\1\uffff\1\3\1\uffff\2\3\2\uffff\2\3\1\uffff"+
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
            return "278:5: (database_name= id DOT )?";
        }
    }
    static final String DFA78_eotS =
        "\27\uffff";
    static final String DFA78_eofS =
        "\27\uffff";
    static final String DFA78_minS =
        "\1\43\26\uffff";
    static final String DFA78_maxS =
        "\1\u0082\26\uffff";
    static final String DFA78_acceptS =
        "\1\uffff\1\1\1\uffff\1\2\23\uffff";
    static final String DFA78_specialS =
        "\27\uffff}>";
    static final String[] DFA78_transitionS = {
            "\1\3\1\uffff\1\3\1\uffff\1\3\5\uffff\2\3\35\uffff\1\1\2\uffff"+
            "\1\1\37\uffff\2\3\1\uffff\1\3\1\uffff\2\3\2\uffff\2\3\1\uffff"+
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
            return "278:43: ( ( AS )? table_alias= ID )?";
        }
    }
    static final String DFA79_eotS =
        "\25\uffff";
    static final String DFA79_eofS =
        "\25\uffff";
    static final String DFA79_minS =
        "\1\43\24\uffff";
    static final String DFA79_maxS =
        "\1\u0082\24\uffff";
    static final String DFA79_acceptS =
        "\1\uffff\1\1\1\2\1\3\21\uffff";
    static final String DFA79_specialS =
        "\25\uffff}>";
    static final String[] DFA79_transitionS = {
            "\1\3\1\uffff\1\1\1\uffff\1\2\5\uffff\2\3\100\uffff\2\3\1\uffff"+
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
            return "278:67: ( INDEXED BY index_name= id | NOT INDEXED )?";
        }
    }
    static final String DFA81_eotS =
        "\25\uffff";
    static final String DFA81_eofS =
        "\25\uffff";
    static final String DFA81_minS =
        "\1\43\24\uffff";
    static final String DFA81_maxS =
        "\1\u0082\24\uffff";
    static final String DFA81_acceptS =
        "\1\uffff\1\1\1\uffff\1\2\21\uffff";
    static final String DFA81_specialS =
        "\25\uffff}>";
    static final String[] DFA81_transitionS = {
            "\1\3\11\uffff\2\3\35\uffff\1\1\2\uffff\1\1\37\uffff\2\3\1\uffff"+
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
            return "280:31: ( ( AS )? table_alias= ID )?";
        }
    }
    static final String DFA92_eotS =
        "\15\uffff";
    static final String DFA92_eofS =
        "\15\uffff";
    static final String DFA92_minS =
        "\1\40\2\44\12\uffff";
    static final String DFA92_maxS =
        "\1\u00b5\2\u0086\12\uffff";
    static final String DFA92_acceptS =
        "\3\uffff\1\2\3\uffff\1\1\5\uffff";
    static final String DFA92_specialS =
        "\15\uffff}>";
    static final String[] DFA92_transitionS = {
            "\3\2\2\uffff\2\2\1\uffff\3\2\6\uffff\3\2\27\uffff\1\2\1\1\10"+
            "\2\4\uffff\3\2\3\uffff\5\2\1\uffff\72\2\1\uffff\26\2",
            "\1\7\7\uffff\1\3\111\uffff\1\3\16\uffff\2\3",
            "\1\7\7\uffff\1\3\111\uffff\1\3\16\uffff\2\3",
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
            return "293:67: (database_name= id DOT )?";
        }
    }
    static final String DFA117_eotS =
        "\76\uffff";
    static final String DFA117_eofS =
        "\76\uffff";
    static final String DFA117_minS =
        "\1\55\1\40\2\uffff\1\47\1\uffff\3\47\65\uffff";
    static final String DFA117_maxS =
        "\1\56\1\u00b5\2\uffff\1\u00a3\1\uffff\3\u00a3\65\uffff";
    static final String DFA117_acceptS =
        "\2\uffff\1\2\1\1\72\uffff";
    static final String DFA117_specialS =
        "\76\uffff}>";
    static final String[] DFA117_transitionS = {
            "\1\1\1\2",
            "\3\3\2\uffff\7\3\3\uffff\5\3\4\uffff\4\3\17\uffff\12\3\4\uffff"+
            "\3\3\3\uffff\5\3\1\uffff\65\3\1\2\1\4\3\3\1\uffff\1\6\1\7\1"+
            "\10\11\3\1\uffff\11\3",
            "",
            "",
            "\1\3\5\uffff\2\3\3\uffff\1\3\30\uffff\2\3\71\uffff\1\3\23\uffff"+
            "\2\3\1\2\3\uffff\2\3\1\uffff\1\3",
            "",
            "\1\3\4\uffff\1\2\2\3\3\uffff\1\3\30\uffff\2\3\71\uffff\1\3"+
            "\23\uffff\2\3\4\uffff\2\3\1\uffff\1\3",
            "\1\3\4\uffff\1\2\2\3\3\uffff\1\3\30\uffff\2\3\71\uffff\1\3"+
            "\23\uffff\2\3\4\uffff\2\3\1\uffff\1\3",
            "\1\3\5\uffff\2\3\3\uffff\1\3\30\uffff\2\3\71\uffff\1\3\23\uffff"+
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
            return "()* loopback of 336:23: ( COMMA column_def )*";
        }
    }
    static final String DFA120_eotS =
        "\16\uffff";
    static final String DFA120_eofS =
        "\16\uffff";
    static final String DFA120_minS =
        "\1\43\15\uffff";
    static final String DFA120_maxS =
        "\1\u00a3\15\uffff";
    static final String DFA120_acceptS =
        "\1\uffff\1\1\1\2\13\uffff";
    static final String DFA120_specialS =
        "\16\uffff}>";
    static final String[] DFA120_transitionS = {
            "\1\2\3\uffff\1\2\5\uffff\2\2\3\uffff\1\2\30\uffff\1\2\1\1\71"+
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
            return "341:99: ( type_name )?";
        }
    }
    static final String DFA121_eotS =
        "\15\uffff";
    static final String DFA121_eofS =
        "\15\uffff";
    static final String DFA121_minS =
        "\1\43\14\uffff";
    static final String DFA121_maxS =
        "\1\u00a3\14\uffff";
    static final String DFA121_acceptS =
        "\1\uffff\1\2\2\uffff\1\1\10\uffff";
    static final String DFA121_specialS =
        "\15\uffff}>";
    static final String[] DFA121_transitionS = {
            "\1\1\3\uffff\1\4\5\uffff\2\1\3\uffff\1\4\30\uffff\1\4\72\uffff"+
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
            return "()* loopback of 341:111: ( column_constraint )*";
        }
    }
    static final String DFA122_eotS =
        "\12\uffff";
    static final String DFA122_eofS =
        "\12\uffff";
    static final String DFA122_minS =
        "\1\47\11\uffff";
    static final String DFA122_maxS =
        "\1\u00a3\11\uffff";
    static final String DFA122_acceptS =
        "\1\uffff\1\1\1\2\7\uffff";
    static final String DFA122_specialS =
        "\12\uffff}>";
    static final String[] DFA122_transitionS = {
            "\1\2\12\uffff\1\2\30\uffff\1\2\72\uffff\1\2\23\uffff\1\1\1\2"+
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
            return "344:20: ( CONSTRAINT name= id )?";
        }
    }
    static final String DFA123_eotS =
        "\13\uffff";
    static final String DFA123_eofS =
        "\13\uffff";
    static final String DFA123_minS =
        "\1\47\2\uffff\1\62\7\uffff";
    static final String DFA123_maxS =
        "\1\u00a3\2\uffff\1\u009e\7\uffff";
    static final String DFA123_acceptS =
        "\1\uffff\1\1\1\2\1\uffff\1\5\1\6\1\7\1\10\1\11\1\3\1\4";
    static final String DFA123_specialS =
        "\13\uffff}>";
    static final String[] DFA123_transitionS = {
            "\1\3\12\uffff\1\2\30\uffff\1\7\72\uffff\1\6\24\uffff\1\1\4\uffff"+
            "\1\4\1\5\1\uffff\1\10",
            "",
            "",
            "\1\11\153\uffff\1\12",
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
            return "345:3: ( column_constraint_pk | column_constraint_null | column_constraint_not_null | column_constraint_not_for_replication | column_constraint_unique | column_constraint_check | column_constraint_default | column_constraint_collate | fk_clause )";
        }
    }
    static final String DFA124_eotS =
        "\20\uffff";
    static final String DFA124_eofS =
        "\20\uffff";
    static final String DFA124_minS =
        "\1\43\17\uffff";
    static final String DFA124_maxS =
        "\1\u00a3\17\uffff";
    static final String DFA124_acceptS =
        "\1\uffff\1\1\1\2\15\uffff";
    static final String DFA124_specialS =
        "\20\uffff}>";
    static final String[] DFA124_transitionS = {
            "\1\2\3\uffff\1\2\5\uffff\2\2\3\uffff\1\2\30\uffff\1\2\41\uffff"+
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
            return "366:37: ( ASC | DESC )?";
        }
    }
    static final String DFA125_eotS =
        "\17\uffff";
    static final String DFA125_eofS =
        "\17\uffff";
    static final String DFA125_minS =
        "\1\43\16\uffff";
    static final String DFA125_maxS =
        "\1\u00a3\16\uffff";
    static final String DFA125_acceptS =
        "\1\uffff\1\1\1\2\14\uffff";
    static final String DFA125_specialS =
        "\17\uffff}>";
    static final String[] DFA125_transitionS = {
            "\1\2\3\uffff\1\2\5\uffff\2\2\3\uffff\1\2\30\uffff\1\2\65\uffff"+
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
            return "366:51: ( table_conflict_clause )?";
        }
    }
    static final String DFA126_eotS =
        "\16\uffff";
    static final String DFA126_eofS =
        "\16\uffff";
    static final String DFA126_minS =
        "\1\43\15\uffff";
    static final String DFA126_maxS =
        "\1\u00a3\15\uffff";
    static final String DFA126_acceptS =
        "\1\uffff\1\1\1\2\13\uffff";
    static final String DFA126_specialS =
        "\16\uffff}>";
    static final String[] DFA126_transitionS = {
            "\1\2\3\uffff\1\2\5\uffff\2\2\3\uffff\1\2\30\uffff\1\2\72\uffff"+
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
            return "366:74: ( AUTOINCREMENT )?";
        }
    }
    static final String DFA127_eotS =
        "\16\uffff";
    static final String DFA127_eofS =
        "\16\uffff";
    static final String DFA127_minS =
        "\1\43\15\uffff";
    static final String DFA127_maxS =
        "\1\u00a3\15\uffff";
    static final String DFA127_acceptS =
        "\1\uffff\1\1\1\2\13\uffff";
    static final String DFA127_specialS =
        "\16\uffff}>";
    static final String[] DFA127_transitionS = {
            "\1\2\3\uffff\1\2\5\uffff\2\2\3\uffff\1\2\30\uffff\1\2\65\uffff"+
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
            return "368:38: ( table_conflict_clause )?";
        }
    }
    static final String DFA128_eotS =
        "\16\uffff";
    static final String DFA128_eofS =
        "\16\uffff";
    static final String DFA128_minS =
        "\1\43\15\uffff";
    static final String DFA128_maxS =
        "\1\u00a3\15\uffff";
    static final String DFA128_acceptS =
        "\1\uffff\1\1\1\2\13\uffff";
    static final String DFA128_specialS =
        "\16\uffff}>";
    static final String[] DFA128_transitionS = {
            "\1\2\3\uffff\1\2\5\uffff\2\2\3\uffff\1\2\30\uffff\1\2\65\uffff"+
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
            return "370:60: ( table_conflict_clause )?";
        }
    }
    static final String DFA129_eotS =
        "\16\uffff";
    static final String DFA129_eofS =
        "\16\uffff";
    static final String DFA129_minS =
        "\1\43\15\uffff";
    static final String DFA129_maxS =
        "\1\u00a3\15\uffff";
    static final String DFA129_acceptS =
        "\1\uffff\1\1\1\2\13\uffff";
    static final String DFA129_specialS =
        "\16\uffff}>";
    static final String[] DFA129_transitionS = {
            "\1\2\3\uffff\1\2\5\uffff\2\2\3\uffff\1\2\30\uffff\1\2\65\uffff"+
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
            return "372:30: ( table_conflict_clause )?";
        }
    }
    static final String DFA130_eotS =
        "\16\uffff";
    static final String DFA130_eofS =
        "\16\uffff";
    static final String DFA130_minS =
        "\1\43\15\uffff";
    static final String DFA130_maxS =
        "\1\u00a3\15\uffff";
    static final String DFA130_acceptS =
        "\1\uffff\1\1\1\2\13\uffff";
    static final String DFA130_specialS =
        "\16\uffff}>";
    static final String[] DFA130_transitionS = {
            "\1\2\3\uffff\1\2\5\uffff\2\2\3\uffff\1\2\30\uffff\1\2\65\uffff"+
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
            return "374:35: ( table_conflict_clause )?";
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
            return "386:37: ( signed_default_number | literal_value | LPAREN expr RPAREN )";
        }
    }
    static final String DFA141_eotS =
        "\21\uffff";
    static final String DFA141_eofS =
        "\21\uffff";
    static final String DFA141_minS =
        "\1\43\20\uffff";
    static final String DFA141_maxS =
        "\1\u00a6\20\uffff";
    static final String DFA141_acceptS =
        "\1\uffff\1\1\1\2\16\uffff";
    static final String DFA141_specialS =
        "\21\uffff}>";
    static final String[] DFA141_transitionS = {
            "\1\2\3\uffff\1\2\4\uffff\1\1\2\2\3\uffff\1\2\10\uffff\1\2\17"+
            "\uffff\1\2\65\uffff\1\2\4\uffff\1\2\23\uffff\2\2\4\uffff\2\2"+
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
            return "415:106: ( LPAREN column_names+= id ( COMMA column_names+= id )* RPAREN )?";
        }
    }
    static final String DFA142_eotS =
        "\20\uffff";
    static final String DFA142_eofS =
        "\20\uffff";
    static final String DFA142_minS =
        "\1\43\17\uffff";
    static final String DFA142_maxS =
        "\1\u00a6\17\uffff";
    static final String DFA142_acceptS =
        "\1\uffff\1\2\14\uffff\1\1\1\uffff";
    static final String DFA142_specialS =
        "\20\uffff}>";
    static final String[] DFA142_transitionS = {
            "\1\1\3\uffff\1\1\5\uffff\2\1\3\uffff\1\1\10\uffff\1\16\17\uffff"+
            "\1\1\65\uffff\1\16\4\uffff\1\1\23\uffff\2\1\4\uffff\2\1\1\uffff"+
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
            return "()* loopback of 416:3: ( fk_clause_action )*";
        }
    }
    static final String DFA143_eotS =
        "\21\uffff";
    static final String DFA143_eofS =
        "\21\uffff";
    static final String DFA143_minS =
        "\1\43\1\62\17\uffff";
    static final String DFA143_maxS =
        "\2\u00a6\17\uffff";
    static final String DFA143_acceptS =
        "\2\uffff\1\1\1\2\15\uffff";
    static final String DFA143_specialS =
        "\21\uffff}>";
    static final String[] DFA143_transitionS = {
            "\1\3\3\uffff\1\1\5\uffff\2\3\3\uffff\1\3\30\uffff\1\3\72\uffff"+
            "\1\3\23\uffff\2\3\4\uffff\2\3\1\uffff\1\3\2\uffff\1\2",
            "\1\3\153\uffff\1\3\7\uffff\1\2",
            "",
            "",
            "",
            "",
            "",
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
            return "416:21: ( fk_clause_deferrable )?";
        }
    }
    static final String DFA147_eotS =
        "\20\uffff";
    static final String DFA147_eofS =
        "\20\uffff";
    static final String DFA147_minS =
        "\1\43\1\u008b\16\uffff";
    static final String DFA147_maxS =
        "\1\u00a7\1\u008c\16\uffff";
    static final String DFA147_acceptS =
        "\2\uffff\1\3\13\uffff\1\1\1\2";
    static final String DFA147_specialS =
        "\20\uffff}>";
    static final String[] DFA147_transitionS = {
            "\1\2\3\uffff\1\2\5\uffff\2\2\3\uffff\1\2\30\uffff\1\2\72\uffff"+
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
            return "423:42: ( INITIALLY DEFERRED | INITIALLY IMMEDIATE )?";
        }
    }
    static final String DFA167_eotS =
        "\14\uffff";
    static final String DFA167_eofS =
        "\14\uffff";
    static final String DFA167_minS =
        "\1\40\1\44\12\uffff";
    static final String DFA167_maxS =
        "\1\u00b5\1\u00b2\12\uffff";
    static final String DFA167_acceptS =
        "\2\uffff\1\2\1\uffff\1\1\7\uffff";
    static final String DFA167_specialS =
        "\14\uffff}>";
    static final String[] DFA167_transitionS = {
            "\3\2\2\uffff\2\2\1\uffff\3\2\6\uffff\3\2\27\uffff\12\2\4\uffff"+
            "\3\2\3\uffff\5\2\1\uffff\63\2\1\1\6\2\1\uffff\26\2",
            "\1\2\2\uffff\1\4\133\uffff\1\2\3\uffff\1\2\1\uffff\1\2\46\uffff"+
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

    static final short[] DFA167_eot = DFA.unpackEncodedString(DFA167_eotS);
    static final short[] DFA167_eof = DFA.unpackEncodedString(DFA167_eofS);
    static final char[] DFA167_min = DFA.unpackEncodedStringToUnsignedChars(DFA167_minS);
    static final char[] DFA167_max = DFA.unpackEncodedStringToUnsignedChars(DFA167_maxS);
    static final short[] DFA167_accept = DFA.unpackEncodedString(DFA167_acceptS);
    static final short[] DFA167_special = DFA.unpackEncodedString(DFA167_specialS);
    static final short[][] DFA167_transition;

    static {
        int numStates = DFA167_transitionS.length;
        DFA167_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA167_transition[i] = DFA.unpackEncodedString(DFA167_transitionS[i]);
        }
    }

    class DFA167 extends DFA {

        public DFA167(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 167;
            this.eot = DFA167_eot;
            this.eof = DFA167_eof;
            this.min = DFA167_min;
            this.max = DFA167_max;
            this.accept = DFA167_accept;
            this.special = DFA167_special;
            this.transition = DFA167_transition;
        }
        public String getDescription() {
            return "451:48: ( IF NOT EXISTS )?";
        }
    }
    static final String DFA168_eotS =
        "\21\uffff";
    static final String DFA168_eofS =
        "\21\uffff";
    static final String DFA168_minS =
        "\1\40\2\44\16\uffff";
    static final String DFA168_maxS =
        "\1\u00b5\2\u00b2\16\uffff";
    static final String DFA168_acceptS =
        "\3\uffff\1\1\1\2\14\uffff";
    static final String DFA168_specialS =
        "\21\uffff}>";
    static final String[] DFA168_transitionS = {
            "\3\2\2\uffff\2\2\1\uffff\3\2\6\uffff\3\2\27\uffff\1\2\1\1\10"+
            "\2\4\uffff\3\2\3\uffff\5\2\1\uffff\72\2\1\uffff\26\2",
            "\1\3\136\uffff\1\4\3\uffff\1\4\1\uffff\1\4\46\uffff\3\4",
            "\1\3\136\uffff\1\4\3\uffff\1\4\1\uffff\1\4\46\uffff\3\4",
            "",
            "",
            "",
            "",
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

    static final short[] DFA168_eot = DFA.unpackEncodedString(DFA168_eotS);
    static final short[] DFA168_eof = DFA.unpackEncodedString(DFA168_eofS);
    static final char[] DFA168_min = DFA.unpackEncodedStringToUnsignedChars(DFA168_minS);
    static final char[] DFA168_max = DFA.unpackEncodedStringToUnsignedChars(DFA168_maxS);
    static final short[] DFA168_accept = DFA.unpackEncodedString(DFA168_acceptS);
    static final short[] DFA168_special = DFA.unpackEncodedString(DFA168_specialS);
    static final short[][] DFA168_transition;

    static {
        int numStates = DFA168_transitionS.length;
        DFA168_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA168_transition[i] = DFA.unpackEncodedString(DFA168_transitionS[i]);
        }
    }

    class DFA168 extends DFA {

        public DFA168(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 168;
            this.eot = DFA168_eot;
            this.eof = DFA168_eof;
            this.min = DFA168_min;
            this.max = DFA168_max;
            this.accept = DFA168_accept;
            this.special = DFA168_special;
            this.transition = DFA168_transition;
        }
        public String getDescription() {
            return "451:65: (database_name= id DOT )?";
        }
    }
 

    public static final BitSet FOLLOW_sql_stmt_in_sql_stmt_list190 = new BitSet(new long[]{0x0000000100000002L,0x00401F6200040000L,0x0000030000168688L});
    public static final BitSet FOLLOW_EXPLAIN_in_sql_stmt200 = new BitSet(new long[]{0x0000000B00000000L,0x00401F6200040000L,0x0000030000168688L});
    public static final BitSet FOLLOW_QUERY_in_sql_stmt203 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_PLAN_in_sql_stmt205 = new BitSet(new long[]{0x0000000900000000L,0x00401F6200040000L,0x0000030000168688L});
    public static final BitSet FOLLOW_sql_stmt_core_in_sql_stmt211 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_SEMI_in_sql_stmt213 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pragma_stmt_in_sql_stmt_core224 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_attach_stmt_in_sql_stmt_core230 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_detach_stmt_in_sql_stmt_core236 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_analyze_stmt_in_sql_stmt_core242 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_reindex_stmt_in_sql_stmt_core248 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_vacuum_stmt_in_sql_stmt_core254 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_select_stmt_in_sql_stmt_core263 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_insert_stmt_in_sql_stmt_core269 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_update_stmt_in_sql_stmt_core275 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_delete_stmt_in_sql_stmt_core281 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_begin_stmt_in_sql_stmt_core287 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_commit_stmt_in_sql_stmt_core293 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rollback_stmt_in_sql_stmt_core299 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_savepoint_stmt_in_sql_stmt_core305 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_release_stmt_in_sql_stmt_core311 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_create_virtual_table_stmt_in_sql_stmt_core320 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_create_table_stmt_in_sql_stmt_core326 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_drop_table_stmt_in_sql_stmt_core332 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_alter_table_stmt_in_sql_stmt_core338 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_create_view_stmt_in_sql_stmt_core344 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_drop_view_stmt_in_sql_stmt_core350 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_create_index_stmt_in_sql_stmt_core356 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_drop_index_stmt_in_sql_stmt_core362 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_create_trigger_stmt_in_sql_stmt_core368 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_drop_trigger_stmt_in_sql_stmt_core374 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_id_in_qualified_table_name387 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_DOT_in_qualified_table_name389 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_id_in_qualified_table_name395 = new BitSet(new long[]{0x000000A000000002L});
    public static final BitSet FOLLOW_INDEXED_in_qualified_table_name398 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_BY_in_qualified_table_name400 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_id_in_qualified_table_name404 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NOT_in_qualified_table_name408 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_INDEXED_in_qualified_table_name410 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_or_subexpr_in_expr419 = new BitSet(new long[]{0x0000010000000002L});
    public static final BitSet FOLLOW_OR_in_expr422 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFEFFFFFFC30L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_or_subexpr_in_expr425 = new BitSet(new long[]{0x0000010000000002L});
    public static final BitSet FOLLOW_and_subexpr_in_or_subexpr434 = new BitSet(new long[]{0x0000020000000002L});
    public static final BitSet FOLLOW_AND_in_or_subexpr437 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFEFFFFFFC30L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_and_subexpr_in_or_subexpr440 = new BitSet(new long[]{0x0000020000000002L});
    public static final BitSet FOLLOW_eq_subexpr_in_and_subexpr449 = new BitSet(new long[]{0x0FFB888000000002L});
    public static final BitSet FOLLOW_cond_expr_in_and_subexpr451 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NOT_in_cond_expr463 = new BitSet(new long[]{0x0F00008000000000L});
    public static final BitSet FOLLOW_match_op_in_cond_expr466 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFEFFFFFFC30L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_eq_subexpr_in_cond_expr470 = new BitSet(new long[]{0x0000040000000002L});
    public static final BitSet FOLLOW_ESCAPE_in_cond_expr473 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFEFFFFFFC30L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_eq_subexpr_in_cond_expr477 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NOT_in_cond_expr505 = new BitSet(new long[]{0x0000080000000000L});
    public static final BitSet FOLLOW_IN_in_cond_expr508 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_LPAREN_in_cond_expr510 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFEFFFFFFC30L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_expr_in_cond_expr512 = new BitSet(new long[]{0x0000600000000000L});
    public static final BitSet FOLLOW_COMMA_in_cond_expr515 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFEFFFFFFC30L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_expr_in_cond_expr517 = new BitSet(new long[]{0x0000600000000000L});
    public static final BitSet FOLLOW_RPAREN_in_cond_expr521 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NOT_in_cond_expr543 = new BitSet(new long[]{0x0000080000000000L});
    public static final BitSet FOLLOW_IN_in_cond_expr546 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_id_in_cond_expr551 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_DOT_in_cond_expr553 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_id_in_cond_expr559 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ISNULL_in_cond_expr590 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NOTNULL_in_cond_expr598 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IS_in_cond_expr606 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_NULL_in_cond_expr608 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NOT_in_cond_expr616 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_NULL_in_cond_expr618 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IS_in_cond_expr626 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_NOT_in_cond_expr628 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_NULL_in_cond_expr630 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NOT_in_cond_expr641 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_BETWEEN_in_cond_expr644 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFEFFFFFFC30L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_eq_subexpr_in_cond_expr648 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_AND_in_cond_expr650 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFEFFFFFFC30L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_eq_subexpr_in_cond_expr654 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_cond_expr680 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFEFFFFFFC30L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_eq_subexpr_in_cond_expr697 = new BitSet(new long[]{0x00F0000000000002L});
    public static final BitSet FOLLOW_set_in_match_op0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_neq_subexpr_in_eq_subexpr730 = new BitSet(new long[]{0xF000000000000002L});
    public static final BitSet FOLLOW_set_in_eq_subexpr733 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFEFFFFFFC30L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_neq_subexpr_in_eq_subexpr750 = new BitSet(new long[]{0xF000000000000002L});
    public static final BitSet FOLLOW_bit_subexpr_in_neq_subexpr759 = new BitSet(new long[]{0x0000000000000002L,0x000000000000000FL});
    public static final BitSet FOLLOW_set_in_neq_subexpr762 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFEFFFFFFC30L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_bit_subexpr_in_neq_subexpr779 = new BitSet(new long[]{0x0000000000000002L,0x000000000000000FL});
    public static final BitSet FOLLOW_add_subexpr_in_bit_subexpr788 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000030L});
    public static final BitSet FOLLOW_set_in_bit_subexpr791 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFEFFFFFFC30L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_add_subexpr_in_bit_subexpr800 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000030L});
    public static final BitSet FOLLOW_mul_subexpr_in_add_subexpr809 = new BitSet(new long[]{0x0000000000000002L,0x00000000000001C0L});
    public static final BitSet FOLLOW_set_in_add_subexpr812 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFEFFFFFFC30L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_mul_subexpr_in_add_subexpr825 = new BitSet(new long[]{0x0000000000000002L,0x00000000000001C0L});
    public static final BitSet FOLLOW_con_subexpr_in_mul_subexpr834 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000200L});
    public static final BitSet FOLLOW_DOUBLE_PIPE_in_mul_subexpr837 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFEFFFFFFC30L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_con_subexpr_in_mul_subexpr840 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000200L});
    public static final BitSet FOLLOW_unary_subexpr_in_con_subexpr849 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_unary_op_in_con_subexpr853 = new BitSet(new long[]{0x000E176700000000L,0xFFFFFFEFFFFFF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_unary_subexpr_in_con_subexpr855 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_unary_op0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_atom_expr_in_unary_subexpr889 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000800L});
    public static final BitSet FOLLOW_COLLATE_in_unary_subexpr892 = new BitSet(new long[]{0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_ID_in_unary_subexpr897 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_literal_value_in_atom_expr909 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_bind_parameter_in_atom_expr915 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_id_in_atom_expr925 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_DOT_in_atom_expr927 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_id_in_atom_expr933 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_DOT_in_atom_expr935 = new BitSet(new long[]{0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_ID_in_atom_expr941 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_atom_expr970 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_LPAREN_in_atom_expr972 = new BitSet(new long[]{0x000E57E700000000L,0xFFFFFFEFFFFFFC70L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_DISTINCT_in_atom_expr975 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFEFFFFFFC30L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_expr_in_atom_expr980 = new BitSet(new long[]{0x0000600000000000L});
    public static final BitSet FOLLOW_COMMA_in_atom_expr983 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFEFFFFFFC30L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_expr_in_atom_expr987 = new BitSet(new long[]{0x0000600000000000L});
    public static final BitSet FOLLOW_ASTERISK_in_atom_expr993 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_RPAREN_in_atom_expr997 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_atom_expr1022 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFEFFFFFFC30L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_expr_in_atom_expr1025 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_RPAREN_in_atom_expr1027 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CAST_in_atom_expr1034 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_LPAREN_in_atom_expr1037 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFEFFFFFFC30L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_expr_in_atom_expr1040 = new BitSet(new long[]{0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_AS_in_atom_expr1042 = new BitSet(new long[]{0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_type_name_in_atom_expr1045 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_RPAREN_in_atom_expr1047 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CASE_in_atom_expr1056 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFEFFFFFFC30L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_expr_in_atom_expr1061 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFEFFFFFFC30L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_when_expr_in_atom_expr1065 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFEFFFFFFC30L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_ELSE_in_atom_expr1069 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFEFFFFFFC30L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_expr_in_atom_expr1073 = new BitSet(new long[]{0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_END_in_atom_expr1077 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_raise_function_in_atom_expr1100 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_WHEN_in_when_expr1110 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFEFFFFFFC30L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_expr_in_when_expr1114 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_THEN_in_when_expr1116 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFEFFFFFFC30L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_expr_in_when_expr1120 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INTEGER_in_literal_value1142 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FLOAT_in_literal_value1156 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_literal_value1170 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_BLOB_in_literal_value1184 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NULL_in_literal_value1198 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CURRENT_TIME_in_literal_value1204 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CURRENT_DATE_in_literal_value1218 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CURRENT_TIMESTAMP_in_literal_value1232 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_QUESTION_in_bind_parameter1253 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_QUESTION_in_bind_parameter1263 = new BitSet(new long[]{0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_INTEGER_in_bind_parameter1267 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_COLON_in_bind_parameter1282 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_id_in_bind_parameter1286 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_AT_in_bind_parameter1301 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_id_in_bind_parameter1305 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RAISE_in_raise_function1326 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_LPAREN_in_raise_function1329 = new BitSet(new long[]{0x0000000000000000L,0x0000000F00000000L});
    public static final BitSet FOLLOW_IGNORE_in_raise_function1333 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_set_in_raise_function1337 = new BitSet(new long[]{0x0000200000000000L});
    public static final BitSet FOLLOW_COMMA_in_raise_function1349 = new BitSet(new long[]{0x0000000000000000L,0x0000000000800000L});
    public static final BitSet FOLLOW_STRING_in_raise_function1354 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_RPAREN_in_raise_function1357 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_type_name1367 = new BitSet(new long[]{0x0000100000000002L,0x0000000000001000L});
    public static final BitSet FOLLOW_LPAREN_in_type_name1371 = new BitSet(new long[]{0x0000000000000000L,0x0000001000600030L});
    public static final BitSet FOLLOW_signed_number_in_type_name1377 = new BitSet(new long[]{0x0000600000000000L});
    public static final BitSet FOLLOW_MAX_in_type_name1381 = new BitSet(new long[]{0x0000600000000000L});
    public static final BitSet FOLLOW_COMMA_in_type_name1386 = new BitSet(new long[]{0x0000000000000000L,0x0000000000600030L});
    public static final BitSet FOLLOW_signed_number_in_type_name1390 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_RPAREN_in_type_name1394 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_signed_number1427 = new BitSet(new long[]{0x0000000000000000L,0x0000000000600000L});
    public static final BitSet FOLLOW_set_in_signed_number1436 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PRAGMA_in_pragma_stmt1450 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_id_in_pragma_stmt1455 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_DOT_in_pragma_stmt1457 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_id_in_pragma_stmt1463 = new BitSet(new long[]{0x0010100000000002L});
    public static final BitSet FOLLOW_EQUALS_in_pragma_stmt1466 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8EFFF830L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_pragma_value_in_pragma_stmt1468 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_pragma_stmt1472 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8EFFF830L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_pragma_value_in_pragma_stmt1474 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_RPAREN_in_pragma_stmt1476 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_signed_number_in_pragma_value1505 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_id_in_pragma_value1518 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_pragma_value1531 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ATTACH_in_attach_stmt1549 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E9FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_DATABASE_in_attach_stmt1552 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E9FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_STRING_in_attach_stmt1559 = new BitSet(new long[]{0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_id_in_attach_stmt1563 = new BitSet(new long[]{0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_AS_in_attach_stmt1566 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_id_in_attach_stmt1570 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DETACH_in_detach_stmt1578 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_DATABASE_in_detach_stmt1581 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_id_in_detach_stmt1587 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ANALYZE_in_analyze_stmt1595 = new BitSet(new long[]{0x000E076700000002L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_id_in_analyze_stmt1600 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_id_in_analyze_stmt1606 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_DOT_in_analyze_stmt1608 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_id_in_analyze_stmt1612 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_REINDEX_in_reindex_stmt1622 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_id_in_reindex_stmt1627 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_DOT_in_reindex_stmt1629 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_id_in_reindex_stmt1635 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VACUUM_in_vacuum_stmt1643 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OR_in_operation_conflict_clause1654 = new BitSet(new long[]{0x0000000000000000L,0x0000100F00000000L});
    public static final BitSet FOLLOW_set_in_operation_conflict_clause1656 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expr_in_ordering_term1681 = new BitSet(new long[]{0x0000000000000002L,0x0000600000000000L});
    public static final BitSet FOLLOW_ASC_in_ordering_term1686 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DESC_in_ordering_term1690 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ORDER_in_operation_limited_clause1720 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_BY_in_operation_limited_clause1722 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFEFFFFFFC30L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_ordering_term_in_operation_limited_clause1724 = new BitSet(new long[]{0x0000200000000000L,0x0001000000000000L});
    public static final BitSet FOLLOW_COMMA_in_operation_limited_clause1727 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFEFFFFFFC30L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_ordering_term_in_operation_limited_clause1729 = new BitSet(new long[]{0x0000200000000000L,0x0001000000000000L});
    public static final BitSet FOLLOW_LIMIT_in_operation_limited_clause1737 = new BitSet(new long[]{0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_INTEGER_in_operation_limited_clause1741 = new BitSet(new long[]{0x0000200000000002L,0x0002000000000000L});
    public static final BitSet FOLLOW_set_in_operation_limited_clause1744 = new BitSet(new long[]{0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_INTEGER_in_operation_limited_clause1754 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_select_list_in_select_stmt1764 = new BitSet(new long[]{0x0000000000000002L,0x0001800000000000L});
    public static final BitSet FOLLOW_ORDER_in_select_stmt1769 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_BY_in_select_stmt1771 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFEFFFFFFC30L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_ordering_term_in_select_stmt1773 = new BitSet(new long[]{0x0000200000000002L,0x0001000000000000L});
    public static final BitSet FOLLOW_COMMA_in_select_stmt1776 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFEFFFFFFC30L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_ordering_term_in_select_stmt1778 = new BitSet(new long[]{0x0000200000000002L,0x0001000000000000L});
    public static final BitSet FOLLOW_LIMIT_in_select_stmt1787 = new BitSet(new long[]{0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_INTEGER_in_select_stmt1791 = new BitSet(new long[]{0x0000200000000002L,0x0002000000000000L});
    public static final BitSet FOLLOW_OFFSET_in_select_stmt1795 = new BitSet(new long[]{0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_COMMA_in_select_stmt1799 = new BitSet(new long[]{0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_INTEGER_in_select_stmt1804 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_select_core_in_select_list1849 = new BitSet(new long[]{0x0000000000000002L,0x0034000000000000L});
    public static final BitSet FOLLOW_select_op_in_select_list1852 = new BitSet(new long[]{0x0000000000000000L,0x0040000000000000L});
    public static final BitSet FOLLOW_select_core_in_select_list1855 = new BitSet(new long[]{0x0000000000000002L,0x0034000000000000L});
    public static final BitSet FOLLOW_UNION_in_select_op1864 = new BitSet(new long[]{0x0000000000000002L,0x0008000000000000L});
    public static final BitSet FOLLOW_ALL_in_select_op1868 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INTERSECT_in_select_op1874 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EXCEPT_in_select_op1878 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SELECT_in_select_core1887 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFEFFFFFFC70L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_ALL_in_select_core1890 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFEFFFFFFC70L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_DISTINCT_in_select_core1894 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFEFFFFFFC70L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_result_column_in_select_core1898 = new BitSet(new long[]{0x0000200000000002L,0x0380000000000000L});
    public static final BitSet FOLLOW_COMMA_in_select_core1901 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFEFFFFFFC70L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_result_column_in_select_core1903 = new BitSet(new long[]{0x0000200000000002L,0x0380000000000000L});
    public static final BitSet FOLLOW_FROM_in_select_core1908 = new BitSet(new long[]{0x000E176700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_join_source_in_select_core1910 = new BitSet(new long[]{0x0000000000000002L,0x0300000000000000L});
    public static final BitSet FOLLOW_WHERE_in_select_core1915 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFEFFFFFFC30L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_expr_in_select_core1919 = new BitSet(new long[]{0x0000000000000002L,0x0200000000000000L});
    public static final BitSet FOLLOW_GROUP_in_select_core1927 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_BY_in_select_core1929 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFEFFFFFFC30L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_ordering_term_in_select_core1931 = new BitSet(new long[]{0x0000200000000002L,0x0400000000000000L});
    public static final BitSet FOLLOW_COMMA_in_select_core1934 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFEFFFFFFC30L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_ordering_term_in_select_core1936 = new BitSet(new long[]{0x0000200000000002L,0x0400000000000000L});
    public static final BitSet FOLLOW_HAVING_in_select_core1941 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFEFFFFFFC30L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_expr_in_select_core1945 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ASTERISK_in_result_column2015 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_id_in_result_column2023 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_DOT_in_result_column2025 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_ASTERISK_in_result_column2027 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expr_in_result_column2042 = new BitSet(new long[]{0x000E076700000002L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_AS_in_result_column2046 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_id_in_result_column2052 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_single_source_in_join_source2073 = new BitSet(new long[]{0x0000200000000002L,0xF800000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_join_op_in_join_source2076 = new BitSet(new long[]{0x000E176700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_single_source_in_join_source2079 = new BitSet(new long[]{0x0000200000000002L,0xF800000000000000L,0x0000000000000007L});
    public static final BitSet FOLLOW_join_constraint_in_join_source2082 = new BitSet(new long[]{0x0000200000000002L,0xF800000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_id_in_single_source2099 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_DOT_in_single_source2101 = new BitSet(new long[]{0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_ID_in_single_source2107 = new BitSet(new long[]{0x000000A000000002L,0x0000000000009000L});
    public static final BitSet FOLLOW_AS_in_single_source2111 = new BitSet(new long[]{0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_ID_in_single_source2117 = new BitSet(new long[]{0x000000A000000002L});
    public static final BitSet FOLLOW_INDEXED_in_single_source2122 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_BY_in_single_source2124 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_id_in_single_source2128 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NOT_in_single_source2132 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_INDEXED_in_single_source2134 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_single_source2175 = new BitSet(new long[]{0x0000000000000000L,0x0040000000000000L});
    public static final BitSet FOLLOW_select_stmt_in_single_source2177 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_RPAREN_in_single_source2179 = new BitSet(new long[]{0x0000000000000002L,0x0000000000009000L});
    public static final BitSet FOLLOW_AS_in_single_source2183 = new BitSet(new long[]{0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_ID_in_single_source2189 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_single_source2211 = new BitSet(new long[]{0x000E176700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_join_source_in_single_source2214 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_RPAREN_in_single_source2216 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_COMMA_in_join_op2227 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NATURAL_in_join_op2234 = new BitSet(new long[]{0x0000000000000000L,0xF000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_LEFT_in_join_op2240 = new BitSet(new long[]{0x0000000000000000L,0x2000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_OUTER_in_join_op2245 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_INNER_in_join_op2251 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_CROSS_in_join_op2255 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_JOIN_in_join_op2258 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ON_in_join_constraint2269 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFEFFFFFFC30L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_expr_in_join_constraint2272 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_USING_in_join_constraint2278 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_LPAREN_in_join_constraint2280 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_id_in_join_constraint2284 = new BitSet(new long[]{0x0000600000000000L});
    public static final BitSet FOLLOW_COMMA_in_join_constraint2287 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_id_in_join_constraint2291 = new BitSet(new long[]{0x0000600000000000L});
    public static final BitSet FOLLOW_RPAREN_in_join_constraint2295 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INSERT_in_insert_stmt2314 = new BitSet(new long[]{0x0000010000000000L,0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_operation_conflict_clause_in_insert_stmt2317 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_REPLACE_in_insert_stmt2323 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_INTO_in_insert_stmt2326 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_id_in_insert_stmt2331 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_DOT_in_insert_stmt2333 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_id_in_insert_stmt2339 = new BitSet(new long[]{0x0000100000000000L,0x0040000000000000L,0x0000000000000060L});
    public static final BitSet FOLLOW_LPAREN_in_insert_stmt2346 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_id_in_insert_stmt2350 = new BitSet(new long[]{0x0000600000000000L});
    public static final BitSet FOLLOW_COMMA_in_insert_stmt2353 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_id_in_insert_stmt2357 = new BitSet(new long[]{0x0000600000000000L});
    public static final BitSet FOLLOW_RPAREN_in_insert_stmt2361 = new BitSet(new long[]{0x0000000000000000L,0x0040000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_VALUES_in_insert_stmt2370 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_LPAREN_in_insert_stmt2372 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFEFFFFFFC30L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_expr_in_insert_stmt2376 = new BitSet(new long[]{0x0000600000000000L});
    public static final BitSet FOLLOW_COMMA_in_insert_stmt2379 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFEFFFFFFC30L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_expr_in_insert_stmt2383 = new BitSet(new long[]{0x0000600000000000L});
    public static final BitSet FOLLOW_RPAREN_in_insert_stmt2387 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_select_stmt_in_insert_stmt2391 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DEFAULT_in_insert_stmt2398 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_VALUES_in_insert_stmt2400 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_UPDATE_in_update_stmt2410 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_operation_conflict_clause_in_update_stmt2413 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_qualified_table_name_in_update_stmt2417 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_SET_in_update_stmt2421 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_update_set_in_update_stmt2425 = new BitSet(new long[]{0x0000200000000002L,0x0101800000000000L});
    public static final BitSet FOLLOW_COMMA_in_update_stmt2428 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_update_set_in_update_stmt2432 = new BitSet(new long[]{0x0000200000000002L,0x0101800000000000L});
    public static final BitSet FOLLOW_WHERE_in_update_stmt2437 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFEFFFFFFC30L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_expr_in_update_stmt2439 = new BitSet(new long[]{0x0000000000000002L,0x0001800000000000L});
    public static final BitSet FOLLOW_operation_limited_clause_in_update_stmt2444 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_id_in_update_set2455 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_EQUALS_in_update_set2457 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFEFFFFFFC30L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_expr_in_update_set2459 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DELETE_in_delete_stmt2467 = new BitSet(new long[]{0x0000000000000000L,0x0080000000000000L});
    public static final BitSet FOLLOW_FROM_in_delete_stmt2469 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_qualified_table_name_in_delete_stmt2471 = new BitSet(new long[]{0x0000000000000002L,0x0101800000000000L});
    public static final BitSet FOLLOW_WHERE_in_delete_stmt2474 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFEFFFFFFC30L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_expr_in_delete_stmt2476 = new BitSet(new long[]{0x0000000000000002L,0x0001800000000000L});
    public static final BitSet FOLLOW_operation_limited_clause_in_delete_stmt2481 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_BEGIN_in_begin_stmt2491 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000007800L});
    public static final BitSet FOLLOW_set_in_begin_stmt2493 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_TRANSACTION_in_begin_stmt2507 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_commit_stmt2517 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_TRANSACTION_in_commit_stmt2526 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ROLLBACK_in_rollback_stmt2536 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000014000L});
    public static final BitSet FOLLOW_TRANSACTION_in_rollback_stmt2539 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_TO_in_rollback_stmt2544 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_SAVEPOINT_in_rollback_stmt2547 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_id_in_rollback_stmt2553 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SAVEPOINT_in_savepoint_stmt2563 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_id_in_savepoint_stmt2567 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RELEASE_in_release_stmt2575 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_SAVEPOINT_in_release_stmt2578 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_id_in_release_stmt2584 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ON_in_table_conflict_clause2596 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_CONFLICT_in_table_conflict_clause2599 = new BitSet(new long[]{0x0000000000000000L,0x0000100F00000000L});
    public static final BitSet FOLLOW_set_in_table_conflict_clause2602 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CREATE_in_create_virtual_table_stmt2629 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_VIRTUAL_in_create_virtual_table_stmt2631 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_TABLE_in_create_virtual_table_stmt2633 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_id_in_create_virtual_table_stmt2638 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_DOT_in_create_virtual_table_stmt2640 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_id_in_create_virtual_table_stmt2646 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_USING_in_create_virtual_table_stmt2650 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_id_in_create_virtual_table_stmt2654 = new BitSet(new long[]{0x0000100000000002L});
    public static final BitSet FOLLOW_LPAREN_in_create_virtual_table_stmt2657 = new BitSet(new long[]{0x0F0F8FE700000000L,0xFFFFFFEF8E1FF800L,0x003FEFFF7BFFFFFFL});
    public static final BitSet FOLLOW_column_def_in_create_virtual_table_stmt2659 = new BitSet(new long[]{0x0000600000000000L});
    public static final BitSet FOLLOW_COMMA_in_create_virtual_table_stmt2662 = new BitSet(new long[]{0x0F0F8FE700000000L,0xFFFFFFEF8E1FF800L,0x003FEFFF7BFFFFFFL});
    public static final BitSet FOLLOW_column_def_in_create_virtual_table_stmt2664 = new BitSet(new long[]{0x0000600000000000L});
    public static final BitSet FOLLOW_RPAREN_in_create_virtual_table_stmt2668 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CREATE_in_create_table_stmt2678 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000C00000L});
    public static final BitSet FOLLOW_TEMPORARY_in_create_table_stmt2680 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_TABLE_in_create_table_stmt2683 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_IF_in_create_table_stmt2686 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_NOT_in_create_table_stmt2688 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000002000000L});
    public static final BitSet FOLLOW_EXISTS_in_create_table_stmt2690 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_id_in_create_table_stmt2697 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_DOT_in_create_table_stmt2699 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_id_in_create_table_stmt2705 = new BitSet(new long[]{0x0000100000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_LPAREN_in_create_table_stmt2713 = new BitSet(new long[]{0x0F0F8FE700000000L,0xFFFFFFEF8E1FF800L,0x003FEFFF7BFFFFFFL});
    public static final BitSet FOLLOW_column_def_in_create_table_stmt2715 = new BitSet(new long[]{0x0000600000000000L});
    public static final BitSet FOLLOW_COMMA_in_create_table_stmt2718 = new BitSet(new long[]{0x0F0F8FE700000000L,0xFFFFFFEF8E1FF800L,0x003FEFFF7BFFFFFFL});
    public static final BitSet FOLLOW_column_def_in_create_table_stmt2720 = new BitSet(new long[]{0x0000600000000000L});
    public static final BitSet FOLLOW_COMMA_in_create_table_stmt2725 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x000000070C000000L});
    public static final BitSet FOLLOW_table_constraint_in_create_table_stmt2727 = new BitSet(new long[]{0x0000600000000000L});
    public static final BitSet FOLLOW_RPAREN_in_create_table_stmt2731 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_AS_in_create_table_stmt2737 = new BitSet(new long[]{0x0000000000000000L,0x0040000000000000L});
    public static final BitSet FOLLOW_select_stmt_in_create_table_stmt2739 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_id_column_def_in_column_def2796 = new BitSet(new long[]{0x0004008000000002L,0x0000000000001800L,0x0000000B0C000040L});
    public static final BitSet FOLLOW_type_name_in_column_def2800 = new BitSet(new long[]{0x0004008000000002L,0x0000000000000800L,0x0000000B0C000040L});
    public static final BitSet FOLLOW_column_constraint_in_column_def2804 = new BitSet(new long[]{0x0004008000000002L,0x0000000000000800L,0x0000000B0C000040L});
    public static final BitSet FOLLOW_CONSTRAINT_in_column_constraint2830 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_id_in_column_constraint2834 = new BitSet(new long[]{0x0004008000000000L,0x0000000000000800L,0x0000000B0C000040L});
    public static final BitSet FOLLOW_column_constraint_pk_in_column_constraint2842 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_column_constraint_null_in_column_constraint2848 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_column_constraint_not_null_in_column_constraint2854 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_column_constraint_not_for_replication_in_column_constraint2860 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_column_constraint_unique_in_column_constraint2867 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_column_constraint_check_in_column_constraint2873 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_column_constraint_default_in_column_constraint2879 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_column_constraint_collate_in_column_constraint2885 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_fk_clause_in_column_constraint2891 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PRIMARY_in_column_constraint_pk2956 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_KEY_in_column_constraint_pk2959 = new BitSet(new long[]{0x0000000000000002L,0x0000600000000000L,0x0000000020000002L});
    public static final BitSet FOLLOW_set_in_column_constraint_pk2962 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000020000002L});
    public static final BitSet FOLLOW_table_conflict_clause_in_column_constraint_pk2971 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_AUTOINCREMENT_in_column_constraint_pk2975 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NOT_in_column_constraint_not_null2984 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_NULL_in_column_constraint_not_null2986 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_table_conflict_clause_in_column_constraint_not_null2988 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NOT_in_column_constraint_not_for_replication3005 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_FOR_in_column_constraint_not_for_replication3007 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000080000000L});
    public static final BitSet FOLLOW_REPLICATION_in_column_constraint_not_for_replication3009 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_table_conflict_clause_in_column_constraint_not_for_replication3011 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NULL_in_column_constraint_null3019 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_table_conflict_clause_in_column_constraint_null3021 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_UNIQUE_in_column_constraint_unique3029 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_table_conflict_clause_in_column_constraint_unique3032 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CHECK_in_column_constraint_check3040 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_LPAREN_in_column_constraint_check3043 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFEFFFFFFC30L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_expr_in_column_constraint_check3046 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_RPAREN_in_column_constraint_check3048 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INTEGER_in_numeric_literal_value3059 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FLOAT_in_numeric_literal_value3073 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_signed_default_number3091 = new BitSet(new long[]{0x0000000000000000L,0x0000000000600000L});
    public static final BitSet FOLLOW_numeric_literal_value_in_signed_default_number3100 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DEFAULT_in_column_constraint_default3108 = new BitSet(new long[]{0x0004100000000000L,0x000000000FE00030L});
    public static final BitSet FOLLOW_signed_default_number_in_column_constraint_default3112 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_literal_value_in_column_constraint_default3116 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_column_constraint_default3120 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFEFFFFFFC30L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_expr_in_column_constraint_default3123 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_RPAREN_in_column_constraint_default3125 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_COLLATE_in_column_constraint_collate3134 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_id_in_column_constraint_collate3139 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CONSTRAINT_in_table_constraint3148 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_id_in_table_constraint3152 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x000000070C000000L});
    public static final BitSet FOLLOW_table_constraint_pk_in_table_constraint3160 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_table_constraint_unique_in_table_constraint3168 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_table_constraint_check_in_table_constraint3174 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_table_constraint_fk_in_table_constraint3180 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PRIMARY_in_table_constraint_pk3221 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_KEY_in_table_constraint_pk3223 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_LPAREN_in_table_constraint_pk3227 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_id_in_table_constraint_pk3231 = new BitSet(new long[]{0x0000600000000000L});
    public static final BitSet FOLLOW_COMMA_in_table_constraint_pk3234 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_id_in_table_constraint_pk3238 = new BitSet(new long[]{0x0000600000000000L});
    public static final BitSet FOLLOW_RPAREN_in_table_constraint_pk3242 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_table_conflict_clause_in_table_constraint_pk3244 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_UNIQUE_in_table_constraint_unique3269 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_LPAREN_in_table_constraint_unique3273 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_id_in_table_constraint_unique3277 = new BitSet(new long[]{0x0000600000000000L});
    public static final BitSet FOLLOW_COMMA_in_table_constraint_unique3280 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_id_in_table_constraint_unique3284 = new BitSet(new long[]{0x0000600000000000L});
    public static final BitSet FOLLOW_RPAREN_in_table_constraint_unique3288 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_table_conflict_clause_in_table_constraint_unique3290 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CHECK_in_table_constraint_check3315 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_LPAREN_in_table_constraint_check3318 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFEFFFFFFC30L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_expr_in_table_constraint_check3321 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_RPAREN_in_table_constraint_check3323 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FOREIGN_in_table_constraint_fk3331 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_KEY_in_table_constraint_fk3333 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_LPAREN_in_table_constraint_fk3335 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_id_in_table_constraint_fk3339 = new BitSet(new long[]{0x0000600000000000L});
    public static final BitSet FOLLOW_COMMA_in_table_constraint_fk3342 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_id_in_table_constraint_fk3346 = new BitSet(new long[]{0x0000600000000000L});
    public static final BitSet FOLLOW_RPAREN_in_table_constraint_fk3351 = new BitSet(new long[]{0x0004008000000000L,0x0000000000000800L,0x0000000B0C000040L});
    public static final BitSet FOLLOW_fk_clause_in_table_constraint_fk3353 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_REFERENCES_in_fk_clause3376 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_id_in_fk_clause3380 = new BitSet(new long[]{0x0800108000000002L,0x0000000000000000L,0x0000004000000002L});
    public static final BitSet FOLLOW_LPAREN_in_fk_clause3385 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_id_in_fk_clause3389 = new BitSet(new long[]{0x0000600000000000L});
    public static final BitSet FOLLOW_COMMA_in_fk_clause3392 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_id_in_fk_clause3396 = new BitSet(new long[]{0x0000600000000000L});
    public static final BitSet FOLLOW_RPAREN_in_fk_clause3400 = new BitSet(new long[]{0x0800008000000002L,0x0000000000000000L,0x0000004000000002L});
    public static final BitSet FOLLOW_fk_clause_action_in_fk_clause3407 = new BitSet(new long[]{0x0800008000000002L,0x0000000000000000L,0x0000004000000002L});
    public static final BitSet FOLLOW_fk_clause_deferrable_in_fk_clause3410 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ON_in_fk_clause_action3444 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000288L});
    public static final BitSet FOLLOW_set_in_fk_clause_action3447 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000003000000100L});
    public static final BitSet FOLLOW_SET_in_fk_clause_action3460 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_NULL_in_fk_clause_action3463 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SET_in_fk_clause_action3467 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_DEFAULT_in_fk_clause_action3470 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CASCADE_in_fk_clause_action3474 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RESTRICT_in_fk_clause_action3478 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MATCH_in_fk_clause_action3485 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_id_in_fk_clause_action3488 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NOT_in_fk_clause_deferrable3496 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000004000000000L});
    public static final BitSet FOLLOW_DEFERRABLE_in_fk_clause_deferrable3500 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000008000000000L});
    public static final BitSet FOLLOW_INITIALLY_in_fk_clause_deferrable3504 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_DEFERRED_in_fk_clause_deferrable3507 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INITIALLY_in_fk_clause_deferrable3511 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_IMMEDIATE_in_fk_clause_deferrable3514 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DROP_in_drop_table_stmt3524 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_TABLE_in_drop_table_stmt3526 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_IF_in_drop_table_stmt3529 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000002000000L});
    public static final BitSet FOLLOW_EXISTS_in_drop_table_stmt3531 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_id_in_drop_table_stmt3538 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_DOT_in_drop_table_stmt3540 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_id_in_drop_table_stmt3546 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ALTER_in_alter_table_stmt3576 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_TABLE_in_alter_table_stmt3578 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_id_in_alter_table_stmt3583 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_DOT_in_alter_table_stmt3585 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_id_in_alter_table_stmt3591 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x00000C0000000000L});
    public static final BitSet FOLLOW_RENAME_in_alter_table_stmt3596 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_TO_in_alter_table_stmt3598 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_id_in_alter_table_stmt3602 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ADD_in_alter_table_stmt3606 = new BitSet(new long[]{0x0F0F8FE700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7BFFFFFFL});
    public static final BitSet FOLLOW_COLUMN_in_alter_table_stmt3609 = new BitSet(new long[]{0x0F0F8FE700000000L,0xFFFFFFEF8E1FF800L,0x003FEFFF7BFFFFFFL});
    public static final BitSet FOLLOW_column_def_in_alter_table_stmt3613 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CREATE_in_create_view_stmt3622 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000200000800000L});
    public static final BitSet FOLLOW_TEMPORARY_in_create_view_stmt3624 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000200000000000L});
    public static final BitSet FOLLOW_VIEW_in_create_view_stmt3627 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_IF_in_create_view_stmt3630 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_NOT_in_create_view_stmt3632 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000002000000L});
    public static final BitSet FOLLOW_EXISTS_in_create_view_stmt3634 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_id_in_create_view_stmt3641 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_DOT_in_create_view_stmt3643 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_id_in_create_view_stmt3649 = new BitSet(new long[]{0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_AS_in_create_view_stmt3651 = new BitSet(new long[]{0x0000000000000000L,0x0040000000000000L});
    public static final BitSet FOLLOW_select_stmt_in_create_view_stmt3653 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DROP_in_drop_view_stmt3661 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000200000000000L});
    public static final BitSet FOLLOW_VIEW_in_drop_view_stmt3663 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_IF_in_drop_view_stmt3666 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000002000000L});
    public static final BitSet FOLLOW_EXISTS_in_drop_view_stmt3668 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_id_in_drop_view_stmt3675 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_DOT_in_drop_view_stmt3677 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_id_in_drop_view_stmt3683 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CREATE_in_create_index_stmt3691 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000400100000000L});
    public static final BitSet FOLLOW_UNIQUE_in_create_index_stmt3694 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000400000000000L});
    public static final BitSet FOLLOW_INDEX_in_create_index_stmt3698 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_IF_in_create_index_stmt3701 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_NOT_in_create_index_stmt3703 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000002000000L});
    public static final BitSet FOLLOW_EXISTS_in_create_index_stmt3705 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_id_in_create_index_stmt3712 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_DOT_in_create_index_stmt3714 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_id_in_create_index_stmt3720 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_ON_in_create_index_stmt3724 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_id_in_create_index_stmt3728 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_LPAREN_in_create_index_stmt3730 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_indexed_column_in_create_index_stmt3734 = new BitSet(new long[]{0x0000600000000000L});
    public static final BitSet FOLLOW_COMMA_in_create_index_stmt3737 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_indexed_column_in_create_index_stmt3741 = new BitSet(new long[]{0x0000600000000000L});
    public static final BitSet FOLLOW_RPAREN_in_create_index_stmt3745 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_id_in_indexed_column3791 = new BitSet(new long[]{0x0000000000000002L,0x0000600000000800L});
    public static final BitSet FOLLOW_COLLATE_in_indexed_column3794 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_id_in_indexed_column3798 = new BitSet(new long[]{0x0000000000000002L,0x0000600000000000L});
    public static final BitSet FOLLOW_ASC_in_indexed_column3803 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DESC_in_indexed_column3807 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DROP_in_drop_index_stmt3838 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000400000000000L});
    public static final BitSet FOLLOW_INDEX_in_drop_index_stmt3840 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_IF_in_drop_index_stmt3843 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000002000000L});
    public static final BitSet FOLLOW_EXISTS_in_drop_index_stmt3845 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_id_in_drop_index_stmt3852 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_DOT_in_drop_index_stmt3854 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_id_in_drop_index_stmt3860 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CREATE_in_create_trigger_stmt3890 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000800000800000L});
    public static final BitSet FOLLOW_TEMPORARY_in_create_trigger_stmt3892 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000800000000000L});
    public static final BitSet FOLLOW_TRIGGER_in_create_trigger_stmt3895 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_IF_in_create_trigger_stmt3898 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_NOT_in_create_trigger_stmt3900 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000002000000L});
    public static final BitSet FOLLOW_EXISTS_in_create_trigger_stmt3902 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_id_in_create_trigger_stmt3909 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_DOT_in_create_trigger_stmt3911 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_id_in_create_trigger_stmt3917 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0007000000000288L});
    public static final BitSet FOLLOW_BEFORE_in_create_trigger_stmt3922 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000288L});
    public static final BitSet FOLLOW_AFTER_in_create_trigger_stmt3926 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000288L});
    public static final BitSet FOLLOW_INSTEAD_in_create_trigger_stmt3930 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0008000000000000L});
    public static final BitSet FOLLOW_OF_in_create_trigger_stmt3932 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000288L});
    public static final BitSet FOLLOW_DELETE_in_create_trigger_stmt3937 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_INSERT_in_create_trigger_stmt3941 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_UPDATE_in_create_trigger_stmt3945 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0008000000000002L});
    public static final BitSet FOLLOW_OF_in_create_trigger_stmt3948 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_id_in_create_trigger_stmt3952 = new BitSet(new long[]{0x0000200000000000L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_COMMA_in_create_trigger_stmt3955 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_id_in_create_trigger_stmt3959 = new BitSet(new long[]{0x0000200000000000L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_ON_in_create_trigger_stmt3968 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_id_in_create_trigger_stmt3972 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L,0x0000000040000400L});
    public static final BitSet FOLLOW_FOR_in_create_trigger_stmt3975 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0010000000000000L});
    public static final BitSet FOLLOW_EACH_in_create_trigger_stmt3977 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_ROW_in_create_trigger_stmt3979 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L,0x0000000000000400L});
    public static final BitSet FOLLOW_WHEN_in_create_trigger_stmt3984 = new BitSet(new long[]{0x000E17E700000000L,0xFFFFFFEFFFFFFC30L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_expr_in_create_trigger_stmt3986 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_BEGIN_in_create_trigger_stmt3992 = new BitSet(new long[]{0x0000000000000000L,0x0040100000000000L,0x0000000000000288L});
    public static final BitSet FOLLOW_update_stmt_in_create_trigger_stmt3996 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_insert_stmt_in_create_trigger_stmt4000 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_delete_stmt_in_create_trigger_stmt4004 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_select_stmt_in_create_trigger_stmt4008 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_SEMI_in_create_trigger_stmt4011 = new BitSet(new long[]{0x0000000000000000L,0x0040100000040000L,0x0000000000000288L});
    public static final BitSet FOLLOW_END_in_create_trigger_stmt4015 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DROP_in_drop_trigger_stmt4023 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000800000000000L});
    public static final BitSet FOLLOW_TRIGGER_in_drop_trigger_stmt4025 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_IF_in_drop_trigger_stmt4028 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000002000000L});
    public static final BitSet FOLLOW_EXISTS_in_drop_trigger_stmt4030 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_id_in_drop_trigger_stmt4037 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_DOT_in_drop_trigger_stmt4039 = new BitSet(new long[]{0x000E076700000000L,0xFFFFFFEF8E1FF800L,0x003FFFFF7FFFFFFFL});
    public static final BitSet FOLLOW_id_in_drop_trigger_stmt4045 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_id4055 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_keyword_in_id4059 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_keyword4066 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_id_column_def4733 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_keyword_column_def_in_id_column_def4737 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_keyword_column_def4744 = new BitSet(new long[]{0x0000000000000002L});

}