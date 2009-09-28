// $ANTLR 3.1.2 /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g 2009-09-28 15:37:32

  package org.mitre.schemastore.porters.schemaImporters.ddl;
  import java.util.*;
  import org.mitre.schemastore.model.*;
  import org.mitre.schemastore.porters.schemaImporters.*;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class SqlLexer extends Lexer {
    public static final int ROW=183;
    public static final int BLOB_LITERAL=7;
    public static final int TYPE_PARAMS=31;
    public static final int NOT=39;
    public static final int EXCEPT=118;
    public static final int FOREIGN=163;
    public static final int EOF=-1;
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
    public static final int ELSE=81;
    public static final int J=196;
    public static final int K=197;
    public static final int U=207;
    public static final int T=206;
    public static final int IN_VALUES=20;
    public static final int W=209;
    public static final int V=208;
    public static final int Q=203;
    public static final int P=202;
    public static final int S=205;
    public static final int R=204;
    public static final int ROLLBACK=97;
    public static final int FAIL=99;
    public static final int RESTRICT=166;
    public static final int Y=211;
    public static final int X=210;
    public static final int Z=212;
    public static final int GROUP=122;
    public static final int INTERSECT=117;
    public static final int DROP_INDEX=14;
    public static final int WS=218;
    public static final int PLAN=34;
    public static final int ALIAS=4;
    public static final int END=82;
    public static final int CONSTRAINT=155;
    public static final int RENAME=172;
    public static final int REPLICATION=160;
    public static final int ALTER=170;
    public static final int ONLY=171;
    public static final int ISNULL=47;
    public static final int TABLE=151;
    public static final int FLOAT=86;
    public static final int NOTNULL=48;
    public static final int NOT_EQUALS=54;
    public static final int NOT_NULL=24;
    public static final int LPAREN=44;
    public static final int ASTERISK=70;
    public static final int GREATER_OR_EQ=63;
    public static final int AT=94;
    public static final int DOUBLE_PIPE=73;
    public static final int AS=79;
    public static final int SLASH=71;
    public static final int THEN=84;
    public static final int OFFSET=114;
    public static final int LEFT=125;
    public static final int REPLACE=109;
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
    public static final int REFERENCES=164;
    public static final int CREATE_TABLE=13;
    public static final int COMMA=45;
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
    public static final int INDEXED=37;
    public static final int FLOAT_LITERAL=16;
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

    public SqlLexer() {;} 
    public SqlLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public SqlLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "/home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g"; }

    // $ANTLR start "EQUALS"
    public final void mEQUALS() throws RecognitionException {
        try {
            int _type = EQUALS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:717:7: ( '=' )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:717:16: '='
            {
            match('='); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "EQUALS"

    // $ANTLR start "EQUALS2"
    public final void mEQUALS2() throws RecognitionException {
        try {
            int _type = EQUALS2;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:718:8: ( '==' )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:718:16: '=='
            {
            match("=="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "EQUALS2"

    // $ANTLR start "NOT_EQUALS"
    public final void mNOT_EQUALS() throws RecognitionException {
        try {
            int _type = NOT_EQUALS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:719:11: ( '!=' )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:719:16: '!='
            {
            match("!="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NOT_EQUALS"

    // $ANTLR start "NOT_EQUALS2"
    public final void mNOT_EQUALS2() throws RecognitionException {
        try {
            int _type = NOT_EQUALS2;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:720:12: ( '<>' )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:720:16: '<>'
            {
            match("<>"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NOT_EQUALS2"

    // $ANTLR start "LESS"
    public final void mLESS() throws RecognitionException {
        try {
            int _type = LESS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:721:5: ( '<' )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:721:16: '<'
            {
            match('<'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LESS"

    // $ANTLR start "LESS_OR_EQ"
    public final void mLESS_OR_EQ() throws RecognitionException {
        try {
            int _type = LESS_OR_EQ;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:722:11: ( '<=' )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:722:16: '<='
            {
            match("<="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LESS_OR_EQ"

    // $ANTLR start "GREATER"
    public final void mGREATER() throws RecognitionException {
        try {
            int _type = GREATER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:723:8: ( '>' )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:723:16: '>'
            {
            match('>'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "GREATER"

    // $ANTLR start "GREATER_OR_EQ"
    public final void mGREATER_OR_EQ() throws RecognitionException {
        try {
            int _type = GREATER_OR_EQ;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:724:14: ( '>=' )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:724:16: '>='
            {
            match(">="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "GREATER_OR_EQ"

    // $ANTLR start "SHIFT_LEFT"
    public final void mSHIFT_LEFT() throws RecognitionException {
        try {
            int _type = SHIFT_LEFT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:725:11: ( '<<' )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:725:16: '<<'
            {
            match("<<"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SHIFT_LEFT"

    // $ANTLR start "SHIFT_RIGHT"
    public final void mSHIFT_RIGHT() throws RecognitionException {
        try {
            int _type = SHIFT_RIGHT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:726:12: ( '>>' )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:726:16: '>>'
            {
            match(">>"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SHIFT_RIGHT"

    // $ANTLR start "AMPERSAND"
    public final void mAMPERSAND() throws RecognitionException {
        try {
            int _type = AMPERSAND;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:727:10: ( '&' )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:727:16: '&'
            {
            match('&'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "AMPERSAND"

    // $ANTLR start "PIPE"
    public final void mPIPE() throws RecognitionException {
        try {
            int _type = PIPE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:728:5: ( '|' )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:728:16: '|'
            {
            match('|'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "PIPE"

    // $ANTLR start "DOUBLE_PIPE"
    public final void mDOUBLE_PIPE() throws RecognitionException {
        try {
            int _type = DOUBLE_PIPE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:729:12: ( '||' )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:729:16: '||'
            {
            match("||"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DOUBLE_PIPE"

    // $ANTLR start "PLUS"
    public final void mPLUS() throws RecognitionException {
        try {
            int _type = PLUS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:730:5: ( '+' )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:730:16: '+'
            {
            match('+'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "PLUS"

    // $ANTLR start "MINUS"
    public final void mMINUS() throws RecognitionException {
        try {
            int _type = MINUS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:731:6: ( '-' )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:731:16: '-'
            {
            match('-'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "MINUS"

    // $ANTLR start "TILDA"
    public final void mTILDA() throws RecognitionException {
        try {
            int _type = TILDA;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:732:6: ( '~' )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:732:16: '~'
            {
            match('~'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TILDA"

    // $ANTLR start "ASTERISK"
    public final void mASTERISK() throws RecognitionException {
        try {
            int _type = ASTERISK;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:733:9: ( '*' )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:733:16: '*'
            {
            match('*'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ASTERISK"

    // $ANTLR start "SLASH"
    public final void mSLASH() throws RecognitionException {
        try {
            int _type = SLASH;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:734:6: ( '/' )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:734:16: '/'
            {
            match('/'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SLASH"

    // $ANTLR start "PERCENT"
    public final void mPERCENT() throws RecognitionException {
        try {
            int _type = PERCENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:735:8: ( '%' )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:735:16: '%'
            {
            match('%'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "PERCENT"

    // $ANTLR start "SEMI"
    public final void mSEMI() throws RecognitionException {
        try {
            int _type = SEMI;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:736:5: ( ';' )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:736:16: ';'
            {
            match(';'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SEMI"

    // $ANTLR start "DOT"
    public final void mDOT() throws RecognitionException {
        try {
            int _type = DOT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:737:4: ( '.' )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:737:16: '.'
            {
            match('.'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DOT"

    // $ANTLR start "COMMA"
    public final void mCOMMA() throws RecognitionException {
        try {
            int _type = COMMA;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:738:6: ( ',' )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:738:16: ','
            {
            match(','); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "COMMA"

    // $ANTLR start "LPAREN"
    public final void mLPAREN() throws RecognitionException {
        try {
            int _type = LPAREN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:739:7: ( '(' )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:739:16: '('
            {
            match('('); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LPAREN"

    // $ANTLR start "RPAREN"
    public final void mRPAREN() throws RecognitionException {
        try {
            int _type = RPAREN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:740:7: ( ')' )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:740:16: ')'
            {
            match(')'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RPAREN"

    // $ANTLR start "QUESTION"
    public final void mQUESTION() throws RecognitionException {
        try {
            int _type = QUESTION;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:741:9: ( '?' )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:741:16: '?'
            {
            match('?'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "QUESTION"

    // $ANTLR start "COLON"
    public final void mCOLON() throws RecognitionException {
        try {
            int _type = COLON;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:742:6: ( ':' )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:742:16: ':'
            {
            match(':'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "COLON"

    // $ANTLR start "AT"
    public final void mAT() throws RecognitionException {
        try {
            int _type = AT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:743:3: ( '@' )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:743:16: '@'
            {
            match('@'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "AT"

    // $ANTLR start "DOLLAR"
    public final void mDOLLAR() throws RecognitionException {
        try {
            int _type = DOLLAR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:744:7: ( '$' )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:744:16: '$'
            {
            match('$'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DOLLAR"

    // $ANTLR start "A"
    public final void mA() throws RecognitionException {
        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:748:11: ( ( 'a' | 'A' ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:748:12: ( 'a' | 'A' )
            {
            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "A"

    // $ANTLR start "B"
    public final void mB() throws RecognitionException {
        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:749:11: ( ( 'b' | 'B' ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:749:12: ( 'b' | 'B' )
            {
            if ( input.LA(1)=='B'||input.LA(1)=='b' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "B"

    // $ANTLR start "C"
    public final void mC() throws RecognitionException {
        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:750:11: ( ( 'c' | 'C' ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:750:12: ( 'c' | 'C' )
            {
            if ( input.LA(1)=='C'||input.LA(1)=='c' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "C"

    // $ANTLR start "D"
    public final void mD() throws RecognitionException {
        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:751:11: ( ( 'd' | 'D' ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:751:12: ( 'd' | 'D' )
            {
            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "D"

    // $ANTLR start "E"
    public final void mE() throws RecognitionException {
        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:752:11: ( ( 'e' | 'E' ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:752:12: ( 'e' | 'E' )
            {
            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "E"

    // $ANTLR start "F"
    public final void mF() throws RecognitionException {
        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:753:11: ( ( 'f' | 'F' ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:753:12: ( 'f' | 'F' )
            {
            if ( input.LA(1)=='F'||input.LA(1)=='f' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "F"

    // $ANTLR start "G"
    public final void mG() throws RecognitionException {
        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:754:11: ( ( 'g' | 'G' ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:754:12: ( 'g' | 'G' )
            {
            if ( input.LA(1)=='G'||input.LA(1)=='g' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "G"

    // $ANTLR start "H"
    public final void mH() throws RecognitionException {
        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:755:11: ( ( 'h' | 'H' ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:755:12: ( 'h' | 'H' )
            {
            if ( input.LA(1)=='H'||input.LA(1)=='h' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "H"

    // $ANTLR start "I"
    public final void mI() throws RecognitionException {
        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:756:11: ( ( 'i' | 'I' ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:756:12: ( 'i' | 'I' )
            {
            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "I"

    // $ANTLR start "J"
    public final void mJ() throws RecognitionException {
        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:757:11: ( ( 'j' | 'J' ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:757:12: ( 'j' | 'J' )
            {
            if ( input.LA(1)=='J'||input.LA(1)=='j' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "J"

    // $ANTLR start "K"
    public final void mK() throws RecognitionException {
        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:758:11: ( ( 'k' | 'K' ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:758:12: ( 'k' | 'K' )
            {
            if ( input.LA(1)=='K'||input.LA(1)=='k' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "K"

    // $ANTLR start "L"
    public final void mL() throws RecognitionException {
        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:759:11: ( ( 'l' | 'L' ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:759:12: ( 'l' | 'L' )
            {
            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "L"

    // $ANTLR start "M"
    public final void mM() throws RecognitionException {
        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:760:11: ( ( 'm' | 'M' ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:760:12: ( 'm' | 'M' )
            {
            if ( input.LA(1)=='M'||input.LA(1)=='m' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "M"

    // $ANTLR start "N"
    public final void mN() throws RecognitionException {
        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:761:11: ( ( 'n' | 'N' ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:761:12: ( 'n' | 'N' )
            {
            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "N"

    // $ANTLR start "O"
    public final void mO() throws RecognitionException {
        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:762:11: ( ( 'o' | 'O' ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:762:12: ( 'o' | 'O' )
            {
            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "O"

    // $ANTLR start "P"
    public final void mP() throws RecognitionException {
        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:763:11: ( ( 'p' | 'P' ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:763:12: ( 'p' | 'P' )
            {
            if ( input.LA(1)=='P'||input.LA(1)=='p' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "P"

    // $ANTLR start "Q"
    public final void mQ() throws RecognitionException {
        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:764:11: ( ( 'q' | 'Q' ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:764:12: ( 'q' | 'Q' )
            {
            if ( input.LA(1)=='Q'||input.LA(1)=='q' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "Q"

    // $ANTLR start "R"
    public final void mR() throws RecognitionException {
        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:765:11: ( ( 'r' | 'R' ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:765:12: ( 'r' | 'R' )
            {
            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "R"

    // $ANTLR start "S"
    public final void mS() throws RecognitionException {
        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:766:11: ( ( 's' | 'S' ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:766:12: ( 's' | 'S' )
            {
            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "S"

    // $ANTLR start "T"
    public final void mT() throws RecognitionException {
        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:767:11: ( ( 't' | 'T' ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:767:12: ( 't' | 'T' )
            {
            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "T"

    // $ANTLR start "U"
    public final void mU() throws RecognitionException {
        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:768:11: ( ( 'u' | 'U' ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:768:12: ( 'u' | 'U' )
            {
            if ( input.LA(1)=='U'||input.LA(1)=='u' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "U"

    // $ANTLR start "V"
    public final void mV() throws RecognitionException {
        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:769:11: ( ( 'v' | 'V' ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:769:12: ( 'v' | 'V' )
            {
            if ( input.LA(1)=='V'||input.LA(1)=='v' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "V"

    // $ANTLR start "W"
    public final void mW() throws RecognitionException {
        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:770:11: ( ( 'w' | 'W' ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:770:12: ( 'w' | 'W' )
            {
            if ( input.LA(1)=='W'||input.LA(1)=='w' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "W"

    // $ANTLR start "X"
    public final void mX() throws RecognitionException {
        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:771:11: ( ( 'x' | 'X' ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:771:12: ( 'x' | 'X' )
            {
            if ( input.LA(1)=='X'||input.LA(1)=='x' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "X"

    // $ANTLR start "Y"
    public final void mY() throws RecognitionException {
        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:772:11: ( ( 'y' | 'Y' ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:772:12: ( 'y' | 'Y' )
            {
            if ( input.LA(1)=='Y'||input.LA(1)=='y' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "Y"

    // $ANTLR start "Z"
    public final void mZ() throws RecognitionException {
        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:773:11: ( ( 'z' | 'Z' ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:773:12: ( 'z' | 'Z' )
            {
            if ( input.LA(1)=='Z'||input.LA(1)=='z' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "Z"

    // $ANTLR start "ABORT"
    public final void mABORT() throws RecognitionException {
        try {
            int _type = ABORT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:775:6: ( A B O R T )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:775:8: A B O R T
            {
            mA(); 
            mB(); 
            mO(); 
            mR(); 
            mT(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ABORT"

    // $ANTLR start "ADD"
    public final void mADD() throws RecognitionException {
        try {
            int _type = ADD;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:776:4: ( A D D )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:776:6: A D D
            {
            mA(); 
            mD(); 
            mD(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ADD"

    // $ANTLR start "AFTER"
    public final void mAFTER() throws RecognitionException {
        try {
            int _type = AFTER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:777:6: ( A F T E R )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:777:8: A F T E R
            {
            mA(); 
            mF(); 
            mT(); 
            mE(); 
            mR(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "AFTER"

    // $ANTLR start "ALL"
    public final void mALL() throws RecognitionException {
        try {
            int _type = ALL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:778:4: ( A L L )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:778:6: A L L
            {
            mA(); 
            mL(); 
            mL(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ALL"

    // $ANTLR start "ALTER"
    public final void mALTER() throws RecognitionException {
        try {
            int _type = ALTER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:779:6: ( A L T E R )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:779:8: A L T E R
            {
            mA(); 
            mL(); 
            mT(); 
            mE(); 
            mR(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ALTER"

    // $ANTLR start "ANALYZE"
    public final void mANALYZE() throws RecognitionException {
        try {
            int _type = ANALYZE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:780:8: ( A N A L Y Z E )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:780:10: A N A L Y Z E
            {
            mA(); 
            mN(); 
            mA(); 
            mL(); 
            mY(); 
            mZ(); 
            mE(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ANALYZE"

    // $ANTLR start "AND"
    public final void mAND() throws RecognitionException {
        try {
            int _type = AND;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:781:4: ( A N D )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:781:6: A N D
            {
            mA(); 
            mN(); 
            mD(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "AND"

    // $ANTLR start "AS"
    public final void mAS() throws RecognitionException {
        try {
            int _type = AS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:782:3: ( A S )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:782:5: A S
            {
            mA(); 
            mS(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "AS"

    // $ANTLR start "ASC"
    public final void mASC() throws RecognitionException {
        try {
            int _type = ASC;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:783:4: ( A S C )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:783:6: A S C
            {
            mA(); 
            mS(); 
            mC(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ASC"

    // $ANTLR start "ATTACH"
    public final void mATTACH() throws RecognitionException {
        try {
            int _type = ATTACH;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:784:7: ( A T T A C H )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:784:9: A T T A C H
            {
            mA(); 
            mT(); 
            mT(); 
            mA(); 
            mC(); 
            mH(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ATTACH"

    // $ANTLR start "AUTOINCREMENT"
    public final void mAUTOINCREMENT() throws RecognitionException {
        try {
            int _type = AUTOINCREMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:785:14: ( A U T O I N C R E M E N T )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:785:16: A U T O I N C R E M E N T
            {
            mA(); 
            mU(); 
            mT(); 
            mO(); 
            mI(); 
            mN(); 
            mC(); 
            mR(); 
            mE(); 
            mM(); 
            mE(); 
            mN(); 
            mT(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "AUTOINCREMENT"

    // $ANTLR start "BEFORE"
    public final void mBEFORE() throws RecognitionException {
        try {
            int _type = BEFORE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:786:7: ( B E F O R E )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:786:9: B E F O R E
            {
            mB(); 
            mE(); 
            mF(); 
            mO(); 
            mR(); 
            mE(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "BEFORE"

    // $ANTLR start "BEGIN"
    public final void mBEGIN() throws RecognitionException {
        try {
            int _type = BEGIN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:787:6: ( B E G I N )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:787:8: B E G I N
            {
            mB(); 
            mE(); 
            mG(); 
            mI(); 
            mN(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "BEGIN"

    // $ANTLR start "BETWEEN"
    public final void mBETWEEN() throws RecognitionException {
        try {
            int _type = BETWEEN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:788:8: ( B E T W E E N )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:788:10: B E T W E E N
            {
            mB(); 
            mE(); 
            mT(); 
            mW(); 
            mE(); 
            mE(); 
            mN(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "BETWEEN"

    // $ANTLR start "BY"
    public final void mBY() throws RecognitionException {
        try {
            int _type = BY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:789:3: ( B Y )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:789:5: B Y
            {
            mB(); 
            mY(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "BY"

    // $ANTLR start "BYTE"
    public final void mBYTE() throws RecognitionException {
        try {
            int _type = BYTE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:790:6: ( B Y T E )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:790:8: B Y T E
            {
            mB(); 
            mY(); 
            mT(); 
            mE(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "BYTE"

    // $ANTLR start "CASCADE"
    public final void mCASCADE() throws RecognitionException {
        try {
            int _type = CASCADE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:791:8: ( C A S C A D E )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:791:10: C A S C A D E
            {
            mC(); 
            mA(); 
            mS(); 
            mC(); 
            mA(); 
            mD(); 
            mE(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CASCADE"

    // $ANTLR start "CASE"
    public final void mCASE() throws RecognitionException {
        try {
            int _type = CASE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:792:5: ( C A S E )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:792:7: C A S E
            {
            mC(); 
            mA(); 
            mS(); 
            mE(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CASE"

    // $ANTLR start "CAST"
    public final void mCAST() throws RecognitionException {
        try {
            int _type = CAST;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:793:5: ( C A S T )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:793:7: C A S T
            {
            mC(); 
            mA(); 
            mS(); 
            mT(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CAST"

    // $ANTLR start "CHECK"
    public final void mCHECK() throws RecognitionException {
        try {
            int _type = CHECK;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:794:6: ( C H E C K )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:794:8: C H E C K
            {
            mC(); 
            mH(); 
            mE(); 
            mC(); 
            mK(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CHECK"

    // $ANTLR start "COLLATE"
    public final void mCOLLATE() throws RecognitionException {
        try {
            int _type = COLLATE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:795:8: ( C O L L A T E )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:795:10: C O L L A T E
            {
            mC(); 
            mO(); 
            mL(); 
            mL(); 
            mA(); 
            mT(); 
            mE(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "COLLATE"

    // $ANTLR start "COLUMN"
    public final void mCOLUMN() throws RecognitionException {
        try {
            int _type = COLUMN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:796:7: ( C O L U M N )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:796:9: C O L U M N
            {
            mC(); 
            mO(); 
            mL(); 
            mU(); 
            mM(); 
            mN(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "COLUMN"

    // $ANTLR start "META_COMMENT"
    public final void mMETA_COMMENT() throws RecognitionException {
        try {
            int _type = META_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:797:13: ( C O M M E N T )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:797:15: C O M M E N T
            {
            mC(); 
            mO(); 
            mM(); 
            mM(); 
            mE(); 
            mN(); 
            mT(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "META_COMMENT"

    // $ANTLR start "COMMIT"
    public final void mCOMMIT() throws RecognitionException {
        try {
            int _type = COMMIT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:798:7: ( C O M M I T )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:798:9: C O M M I T
            {
            mC(); 
            mO(); 
            mM(); 
            mM(); 
            mI(); 
            mT(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "COMMIT"

    // $ANTLR start "CONFLICT"
    public final void mCONFLICT() throws RecognitionException {
        try {
            int _type = CONFLICT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:799:9: ( C O N F L I C T )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:799:11: C O N F L I C T
            {
            mC(); 
            mO(); 
            mN(); 
            mF(); 
            mL(); 
            mI(); 
            mC(); 
            mT(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CONFLICT"

    // $ANTLR start "CONSTRAINT"
    public final void mCONSTRAINT() throws RecognitionException {
        try {
            int _type = CONSTRAINT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:800:11: ( C O N S T R A I N T )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:800:13: C O N S T R A I N T
            {
            mC(); 
            mO(); 
            mN(); 
            mS(); 
            mT(); 
            mR(); 
            mA(); 
            mI(); 
            mN(); 
            mT(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CONSTRAINT"

    // $ANTLR start "CREATE"
    public final void mCREATE() throws RecognitionException {
        try {
            int _type = CREATE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:801:7: ( C R E A T E )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:801:9: C R E A T E
            {
            mC(); 
            mR(); 
            mE(); 
            mA(); 
            mT(); 
            mE(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CREATE"

    // $ANTLR start "CROSS"
    public final void mCROSS() throws RecognitionException {
        try {
            int _type = CROSS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:802:6: ( C R O S S )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:802:8: C R O S S
            {
            mC(); 
            mR(); 
            mO(); 
            mS(); 
            mS(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CROSS"

    // $ANTLR start "CURRENT_TIME"
    public final void mCURRENT_TIME() throws RecognitionException {
        try {
            int _type = CURRENT_TIME;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:803:13: ( C U R R E N T '_' T I M E )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:803:15: C U R R E N T '_' T I M E
            {
            mC(); 
            mU(); 
            mR(); 
            mR(); 
            mE(); 
            mN(); 
            mT(); 
            match('_'); 
            mT(); 
            mI(); 
            mM(); 
            mE(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CURRENT_TIME"

    // $ANTLR start "CURRENT_DATE"
    public final void mCURRENT_DATE() throws RecognitionException {
        try {
            int _type = CURRENT_DATE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:804:13: ( C U R R E N T '_' D A T E )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:804:15: C U R R E N T '_' D A T E
            {
            mC(); 
            mU(); 
            mR(); 
            mR(); 
            mE(); 
            mN(); 
            mT(); 
            match('_'); 
            mD(); 
            mA(); 
            mT(); 
            mE(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CURRENT_DATE"

    // $ANTLR start "CURRENT_TIMESTAMP"
    public final void mCURRENT_TIMESTAMP() throws RecognitionException {
        try {
            int _type = CURRENT_TIMESTAMP;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:805:18: ( C U R R E N T '_' T I M E S T A M P )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:805:20: C U R R E N T '_' T I M E S T A M P
            {
            mC(); 
            mU(); 
            mR(); 
            mR(); 
            mE(); 
            mN(); 
            mT(); 
            match('_'); 
            mT(); 
            mI(); 
            mM(); 
            mE(); 
            mS(); 
            mT(); 
            mA(); 
            mM(); 
            mP(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CURRENT_TIMESTAMP"

    // $ANTLR start "DATABASE"
    public final void mDATABASE() throws RecognitionException {
        try {
            int _type = DATABASE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:806:9: ( D A T A B A S E )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:806:11: D A T A B A S E
            {
            mD(); 
            mA(); 
            mT(); 
            mA(); 
            mB(); 
            mA(); 
            mS(); 
            mE(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DATABASE"

    // $ANTLR start "DEFAULT"
    public final void mDEFAULT() throws RecognitionException {
        try {
            int _type = DEFAULT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:807:8: ( D E F A U L T )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:807:10: D E F A U L T
            {
            mD(); 
            mE(); 
            mF(); 
            mA(); 
            mU(); 
            mL(); 
            mT(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DEFAULT"

    // $ANTLR start "DEFERRABLE"
    public final void mDEFERRABLE() throws RecognitionException {
        try {
            int _type = DEFERRABLE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:808:11: ( D E F E R R A B L E )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:808:13: D E F E R R A B L E
            {
            mD(); 
            mE(); 
            mF(); 
            mE(); 
            mR(); 
            mR(); 
            mA(); 
            mB(); 
            mL(); 
            mE(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DEFERRABLE"

    // $ANTLR start "DEFERRED"
    public final void mDEFERRED() throws RecognitionException {
        try {
            int _type = DEFERRED;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:809:9: ( D E F E R R E D )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:809:11: D E F E R R E D
            {
            mD(); 
            mE(); 
            mF(); 
            mE(); 
            mR(); 
            mR(); 
            mE(); 
            mD(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DEFERRED"

    // $ANTLR start "DELETE"
    public final void mDELETE() throws RecognitionException {
        try {
            int _type = DELETE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:810:7: ( D E L E T E )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:810:9: D E L E T E
            {
            mD(); 
            mE(); 
            mL(); 
            mE(); 
            mT(); 
            mE(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DELETE"

    // $ANTLR start "DESC"
    public final void mDESC() throws RecognitionException {
        try {
            int _type = DESC;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:811:5: ( D E S C )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:811:7: D E S C
            {
            mD(); 
            mE(); 
            mS(); 
            mC(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DESC"

    // $ANTLR start "DETACH"
    public final void mDETACH() throws RecognitionException {
        try {
            int _type = DETACH;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:812:7: ( D E T A C H )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:812:9: D E T A C H
            {
            mD(); 
            mE(); 
            mT(); 
            mA(); 
            mC(); 
            mH(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DETACH"

    // $ANTLR start "DISTINCT"
    public final void mDISTINCT() throws RecognitionException {
        try {
            int _type = DISTINCT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:813:9: ( D I S T I N C T )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:813:11: D I S T I N C T
            {
            mD(); 
            mI(); 
            mS(); 
            mT(); 
            mI(); 
            mN(); 
            mC(); 
            mT(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DISTINCT"

    // $ANTLR start "DROP"
    public final void mDROP() throws RecognitionException {
        try {
            int _type = DROP;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:814:5: ( D R O P )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:814:7: D R O P
            {
            mD(); 
            mR(); 
            mO(); 
            mP(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DROP"

    // $ANTLR start "EACH"
    public final void mEACH() throws RecognitionException {
        try {
            int _type = EACH;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:815:5: ( E A C H )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:815:7: E A C H
            {
            mE(); 
            mA(); 
            mC(); 
            mH(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "EACH"

    // $ANTLR start "ELSE"
    public final void mELSE() throws RecognitionException {
        try {
            int _type = ELSE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:816:5: ( E L S E )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:816:7: E L S E
            {
            mE(); 
            mL(); 
            mS(); 
            mE(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ELSE"

    // $ANTLR start "END"
    public final void mEND() throws RecognitionException {
        try {
            int _type = END;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:817:4: ( E N D )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:817:6: E N D
            {
            mE(); 
            mN(); 
            mD(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "END"

    // $ANTLR start "ESCAPE"
    public final void mESCAPE() throws RecognitionException {
        try {
            int _type = ESCAPE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:818:7: ( E S C A P E )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:818:9: E S C A P E
            {
            mE(); 
            mS(); 
            mC(); 
            mA(); 
            mP(); 
            mE(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ESCAPE"

    // $ANTLR start "EXCEPT"
    public final void mEXCEPT() throws RecognitionException {
        try {
            int _type = EXCEPT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:819:7: ( E X C E P T )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:819:9: E X C E P T
            {
            mE(); 
            mX(); 
            mC(); 
            mE(); 
            mP(); 
            mT(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "EXCEPT"

    // $ANTLR start "EXCLUSIVE"
    public final void mEXCLUSIVE() throws RecognitionException {
        try {
            int _type = EXCLUSIVE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:820:10: ( E X C L U S I V E )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:820:12: E X C L U S I V E
            {
            mE(); 
            mX(); 
            mC(); 
            mL(); 
            mU(); 
            mS(); 
            mI(); 
            mV(); 
            mE(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "EXCLUSIVE"

    // $ANTLR start "EXISTS"
    public final void mEXISTS() throws RecognitionException {
        try {
            int _type = EXISTS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:821:7: ( E X I S T S )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:821:9: E X I S T S
            {
            mE(); 
            mX(); 
            mI(); 
            mS(); 
            mT(); 
            mS(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "EXISTS"

    // $ANTLR start "EXPLAIN"
    public final void mEXPLAIN() throws RecognitionException {
        try {
            int _type = EXPLAIN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:822:8: ( E X P L A I N )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:822:10: E X P L A I N
            {
            mE(); 
            mX(); 
            mP(); 
            mL(); 
            mA(); 
            mI(); 
            mN(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "EXPLAIN"

    // $ANTLR start "FAIL"
    public final void mFAIL() throws RecognitionException {
        try {
            int _type = FAIL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:823:5: ( F A I L )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:823:7: F A I L
            {
            mF(); 
            mA(); 
            mI(); 
            mL(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "FAIL"

    // $ANTLR start "FOR"
    public final void mFOR() throws RecognitionException {
        try {
            int _type = FOR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:824:4: ( F O R )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:824:6: F O R
            {
            mF(); 
            mO(); 
            mR(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "FOR"

    // $ANTLR start "FOREIGN"
    public final void mFOREIGN() throws RecognitionException {
        try {
            int _type = FOREIGN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:825:8: ( F O R E I G N )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:825:10: F O R E I G N
            {
            mF(); 
            mO(); 
            mR(); 
            mE(); 
            mI(); 
            mG(); 
            mN(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "FOREIGN"

    // $ANTLR start "FROM"
    public final void mFROM() throws RecognitionException {
        try {
            int _type = FROM;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:826:5: ( F R O M )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:826:7: F R O M
            {
            mF(); 
            mR(); 
            mO(); 
            mM(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "FROM"

    // $ANTLR start "GLOB"
    public final void mGLOB() throws RecognitionException {
        try {
            int _type = GLOB;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:827:5: ( G L O B )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:827:7: G L O B
            {
            mG(); 
            mL(); 
            mO(); 
            mB(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "GLOB"

    // $ANTLR start "GROUP"
    public final void mGROUP() throws RecognitionException {
        try {
            int _type = GROUP;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:828:6: ( G R O U P )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:828:8: G R O U P
            {
            mG(); 
            mR(); 
            mO(); 
            mU(); 
            mP(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "GROUP"

    // $ANTLR start "HAVING"
    public final void mHAVING() throws RecognitionException {
        try {
            int _type = HAVING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:829:7: ( H A V I N G )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:829:9: H A V I N G
            {
            mH(); 
            mA(); 
            mV(); 
            mI(); 
            mN(); 
            mG(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "HAVING"

    // $ANTLR start "IF"
    public final void mIF() throws RecognitionException {
        try {
            int _type = IF;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:830:3: ( I F )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:830:5: I F
            {
            mI(); 
            mF(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "IF"

    // $ANTLR start "IGNORE"
    public final void mIGNORE() throws RecognitionException {
        try {
            int _type = IGNORE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:831:7: ( I G N O R E )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:831:9: I G N O R E
            {
            mI(); 
            mG(); 
            mN(); 
            mO(); 
            mR(); 
            mE(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "IGNORE"

    // $ANTLR start "IMMEDIATE"
    public final void mIMMEDIATE() throws RecognitionException {
        try {
            int _type = IMMEDIATE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:832:10: ( I M M E D I A T E )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:832:12: I M M E D I A T E
            {
            mI(); 
            mM(); 
            mM(); 
            mE(); 
            mD(); 
            mI(); 
            mA(); 
            mT(); 
            mE(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "IMMEDIATE"

    // $ANTLR start "IN"
    public final void mIN() throws RecognitionException {
        try {
            int _type = IN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:833:3: ( I N )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:833:5: I N
            {
            mI(); 
            mN(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "IN"

    // $ANTLR start "INDEX"
    public final void mINDEX() throws RecognitionException {
        try {
            int _type = INDEX;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:834:6: ( I N D E X )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:834:8: I N D E X
            {
            mI(); 
            mN(); 
            mD(); 
            mE(); 
            mX(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "INDEX"

    // $ANTLR start "INDEXED"
    public final void mINDEXED() throws RecognitionException {
        try {
            int _type = INDEXED;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:835:8: ( I N D E X E D )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:835:10: I N D E X E D
            {
            mI(); 
            mN(); 
            mD(); 
            mE(); 
            mX(); 
            mE(); 
            mD(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "INDEXED"

    // $ANTLR start "INITIALLY"
    public final void mINITIALLY() throws RecognitionException {
        try {
            int _type = INITIALLY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:836:10: ( I N I T I A L L Y )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:836:12: I N I T I A L L Y
            {
            mI(); 
            mN(); 
            mI(); 
            mT(); 
            mI(); 
            mA(); 
            mL(); 
            mL(); 
            mY(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "INITIALLY"

    // $ANTLR start "INNER"
    public final void mINNER() throws RecognitionException {
        try {
            int _type = INNER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:837:6: ( I N N E R )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:837:8: I N N E R
            {
            mI(); 
            mN(); 
            mN(); 
            mE(); 
            mR(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "INNER"

    // $ANTLR start "INSERT"
    public final void mINSERT() throws RecognitionException {
        try {
            int _type = INSERT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:838:7: ( I N S E R T )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:838:9: I N S E R T
            {
            mI(); 
            mN(); 
            mS(); 
            mE(); 
            mR(); 
            mT(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "INSERT"

    // $ANTLR start "INSTEAD"
    public final void mINSTEAD() throws RecognitionException {
        try {
            int _type = INSTEAD;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:839:8: ( I N S T E A D )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:839:10: I N S T E A D
            {
            mI(); 
            mN(); 
            mS(); 
            mT(); 
            mE(); 
            mA(); 
            mD(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "INSTEAD"

    // $ANTLR start "INTERSECT"
    public final void mINTERSECT() throws RecognitionException {
        try {
            int _type = INTERSECT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:840:10: ( I N T E R S E C T )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:840:12: I N T E R S E C T
            {
            mI(); 
            mN(); 
            mT(); 
            mE(); 
            mR(); 
            mS(); 
            mE(); 
            mC(); 
            mT(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "INTERSECT"

    // $ANTLR start "INTO"
    public final void mINTO() throws RecognitionException {
        try {
            int _type = INTO;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:841:5: ( I N T O )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:841:7: I N T O
            {
            mI(); 
            mN(); 
            mT(); 
            mO(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "INTO"

    // $ANTLR start "IS"
    public final void mIS() throws RecognitionException {
        try {
            int _type = IS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:842:3: ( I S )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:842:5: I S
            {
            mI(); 
            mS(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "IS"

    // $ANTLR start "ISNULL"
    public final void mISNULL() throws RecognitionException {
        try {
            int _type = ISNULL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:843:7: ( I S N U L L )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:843:9: I S N U L L
            {
            mI(); 
            mS(); 
            mN(); 
            mU(); 
            mL(); 
            mL(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ISNULL"

    // $ANTLR start "JOIN"
    public final void mJOIN() throws RecognitionException {
        try {
            int _type = JOIN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:844:5: ( J O I N )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:844:7: J O I N
            {
            mJ(); 
            mO(); 
            mI(); 
            mN(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "JOIN"

    // $ANTLR start "KEY"
    public final void mKEY() throws RecognitionException {
        try {
            int _type = KEY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:845:4: ( K E Y )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:845:6: K E Y
            {
            mK(); 
            mE(); 
            mY(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KEY"

    // $ANTLR start "LEFT"
    public final void mLEFT() throws RecognitionException {
        try {
            int _type = LEFT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:846:5: ( L E F T )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:846:7: L E F T
            {
            mL(); 
            mE(); 
            mF(); 
            mT(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LEFT"

    // $ANTLR start "LIKE"
    public final void mLIKE() throws RecognitionException {
        try {
            int _type = LIKE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:847:5: ( L I K E )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:847:7: L I K E
            {
            mL(); 
            mI(); 
            mK(); 
            mE(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LIKE"

    // $ANTLR start "LIMIT"
    public final void mLIMIT() throws RecognitionException {
        try {
            int _type = LIMIT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:848:6: ( L I M I T )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:848:8: L I M I T
            {
            mL(); 
            mI(); 
            mM(); 
            mI(); 
            mT(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LIMIT"

    // $ANTLR start "MATCH"
    public final void mMATCH() throws RecognitionException {
        try {
            int _type = MATCH;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:849:6: ( M A T C H )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:849:8: M A T C H
            {
            mM(); 
            mA(); 
            mT(); 
            mC(); 
            mH(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "MATCH"

    // $ANTLR start "MAX"
    public final void mMAX() throws RecognitionException {
        try {
            int _type = MAX;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:850:4: ( M A X )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:850:6: M A X
            {
            mM(); 
            mA(); 
            mX(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "MAX"

    // $ANTLR start "NATURAL"
    public final void mNATURAL() throws RecognitionException {
        try {
            int _type = NATURAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:851:8: ( N A T U R A L )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:851:10: N A T U R A L
            {
            mN(); 
            mA(); 
            mT(); 
            mU(); 
            mR(); 
            mA(); 
            mL(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NATURAL"

    // $ANTLR start "NOT"
    public final void mNOT() throws RecognitionException {
        try {
            int _type = NOT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:852:4: ( N O T )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:852:6: N O T
            {
            mN(); 
            mO(); 
            mT(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NOT"

    // $ANTLR start "NOTNULL"
    public final void mNOTNULL() throws RecognitionException {
        try {
            int _type = NOTNULL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:853:8: ( N O T N U L L )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:853:10: N O T N U L L
            {
            mN(); 
            mO(); 
            mT(); 
            mN(); 
            mU(); 
            mL(); 
            mL(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NOTNULL"

    // $ANTLR start "NULL"
    public final void mNULL() throws RecognitionException {
        try {
            int _type = NULL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:854:5: ( N U L L )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:854:7: N U L L
            {
            mN(); 
            mU(); 
            mL(); 
            mL(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NULL"

    // $ANTLR start "OF"
    public final void mOF() throws RecognitionException {
        try {
            int _type = OF;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:855:3: ( O F )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:855:5: O F
            {
            mO(); 
            mF(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "OF"

    // $ANTLR start "OFFSET"
    public final void mOFFSET() throws RecognitionException {
        try {
            int _type = OFFSET;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:856:7: ( O F F S E T )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:856:9: O F F S E T
            {
            mO(); 
            mF(); 
            mF(); 
            mS(); 
            mE(); 
            mT(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "OFFSET"

    // $ANTLR start "ON"
    public final void mON() throws RecognitionException {
        try {
            int _type = ON;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:857:3: ( O N )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:857:5: O N
            {
            mO(); 
            mN(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ON"

    // $ANTLR start "ONLY"
    public final void mONLY() throws RecognitionException {
        try {
            int _type = ONLY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:858:6: ( O N L Y )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:858:8: O N L Y
            {
            mO(); 
            mN(); 
            mL(); 
            mY(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ONLY"

    // $ANTLR start "OR"
    public final void mOR() throws RecognitionException {
        try {
            int _type = OR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:859:3: ( O R )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:859:5: O R
            {
            mO(); 
            mR(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "OR"

    // $ANTLR start "ORDER"
    public final void mORDER() throws RecognitionException {
        try {
            int _type = ORDER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:860:6: ( O R D E R )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:860:8: O R D E R
            {
            mO(); 
            mR(); 
            mD(); 
            mE(); 
            mR(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ORDER"

    // $ANTLR start "OUTER"
    public final void mOUTER() throws RecognitionException {
        try {
            int _type = OUTER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:861:6: ( O U T E R )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:861:8: O U T E R
            {
            mO(); 
            mU(); 
            mT(); 
            mE(); 
            mR(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "OUTER"

    // $ANTLR start "PLAN"
    public final void mPLAN() throws RecognitionException {
        try {
            int _type = PLAN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:862:5: ( P L A N )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:862:7: P L A N
            {
            mP(); 
            mL(); 
            mA(); 
            mN(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "PLAN"

    // $ANTLR start "PRAGMA"
    public final void mPRAGMA() throws RecognitionException {
        try {
            int _type = PRAGMA;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:863:7: ( P R A G M A )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:863:9: P R A G M A
            {
            mP(); 
            mR(); 
            mA(); 
            mG(); 
            mM(); 
            mA(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "PRAGMA"

    // $ANTLR start "PRIMARY"
    public final void mPRIMARY() throws RecognitionException {
        try {
            int _type = PRIMARY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:864:8: ( P R I M A R Y )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:864:10: P R I M A R Y
            {
            mP(); 
            mR(); 
            mI(); 
            mM(); 
            mA(); 
            mR(); 
            mY(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "PRIMARY"

    // $ANTLR start "QUERY"
    public final void mQUERY() throws RecognitionException {
        try {
            int _type = QUERY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:865:6: ( Q U E R Y )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:865:8: Q U E R Y
            {
            mQ(); 
            mU(); 
            mE(); 
            mR(); 
            mY(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "QUERY"

    // $ANTLR start "RAISE"
    public final void mRAISE() throws RecognitionException {
        try {
            int _type = RAISE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:866:6: ( R A I S E )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:866:8: R A I S E
            {
            mR(); 
            mA(); 
            mI(); 
            mS(); 
            mE(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RAISE"

    // $ANTLR start "REFERENCES"
    public final void mREFERENCES() throws RecognitionException {
        try {
            int _type = REFERENCES;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:867:11: ( R E F E R E N C E S )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:867:13: R E F E R E N C E S
            {
            mR(); 
            mE(); 
            mF(); 
            mE(); 
            mR(); 
            mE(); 
            mN(); 
            mC(); 
            mE(); 
            mS(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "REFERENCES"

    // $ANTLR start "REGEXP"
    public final void mREGEXP() throws RecognitionException {
        try {
            int _type = REGEXP;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:868:7: ( R E G E X P )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:868:9: R E G E X P
            {
            mR(); 
            mE(); 
            mG(); 
            mE(); 
            mX(); 
            mP(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "REGEXP"

    // $ANTLR start "REINDEX"
    public final void mREINDEX() throws RecognitionException {
        try {
            int _type = REINDEX;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:869:8: ( R E I N D E X )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:869:10: R E I N D E X
            {
            mR(); 
            mE(); 
            mI(); 
            mN(); 
            mD(); 
            mE(); 
            mX(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "REINDEX"

    // $ANTLR start "RELEASE"
    public final void mRELEASE() throws RecognitionException {
        try {
            int _type = RELEASE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:870:8: ( R E L E A S E )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:870:10: R E L E A S E
            {
            mR(); 
            mE(); 
            mL(); 
            mE(); 
            mA(); 
            mS(); 
            mE(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RELEASE"

    // $ANTLR start "RENAME"
    public final void mRENAME() throws RecognitionException {
        try {
            int _type = RENAME;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:871:7: ( R E N A M E )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:871:9: R E N A M E
            {
            mR(); 
            mE(); 
            mN(); 
            mA(); 
            mM(); 
            mE(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RENAME"

    // $ANTLR start "REPLACE"
    public final void mREPLACE() throws RecognitionException {
        try {
            int _type = REPLACE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:872:8: ( R E P L A C E )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:872:10: R E P L A C E
            {
            mR(); 
            mE(); 
            mP(); 
            mL(); 
            mA(); 
            mC(); 
            mE(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "REPLACE"

    // $ANTLR start "REPLICATION"
    public final void mREPLICATION() throws RecognitionException {
        try {
            int _type = REPLICATION;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:873:12: ( R E P L I C A T I O N )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:873:14: R E P L I C A T I O N
            {
            mR(); 
            mE(); 
            mP(); 
            mL(); 
            mI(); 
            mC(); 
            mA(); 
            mT(); 
            mI(); 
            mO(); 
            mN(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "REPLICATION"

    // $ANTLR start "RESTRICT"
    public final void mRESTRICT() throws RecognitionException {
        try {
            int _type = RESTRICT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:874:9: ( R E S T R I C T )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:874:11: R E S T R I C T
            {
            mR(); 
            mE(); 
            mS(); 
            mT(); 
            mR(); 
            mI(); 
            mC(); 
            mT(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RESTRICT"

    // $ANTLR start "ROLLBACK"
    public final void mROLLBACK() throws RecognitionException {
        try {
            int _type = ROLLBACK;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:875:9: ( R O L L B A C K )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:875:11: R O L L B A C K
            {
            mR(); 
            mO(); 
            mL(); 
            mL(); 
            mB(); 
            mA(); 
            mC(); 
            mK(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ROLLBACK"

    // $ANTLR start "ROW"
    public final void mROW() throws RecognitionException {
        try {
            int _type = ROW;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:876:4: ( R O W )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:876:6: R O W
            {
            mR(); 
            mO(); 
            mW(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ROW"

    // $ANTLR start "SAVEPOINT"
    public final void mSAVEPOINT() throws RecognitionException {
        try {
            int _type = SAVEPOINT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:877:10: ( S A V E P O I N T )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:877:12: S A V E P O I N T
            {
            mS(); 
            mA(); 
            mV(); 
            mE(); 
            mP(); 
            mO(); 
            mI(); 
            mN(); 
            mT(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SAVEPOINT"

    // $ANTLR start "SCHEMA"
    public final void mSCHEMA() throws RecognitionException {
        try {
            int _type = SCHEMA;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:878:7: ( S C H E M A )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:878:9: S C H E M A
            {
            mS(); 
            mC(); 
            mH(); 
            mE(); 
            mM(); 
            mA(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SCHEMA"

    // $ANTLR start "SELECT"
    public final void mSELECT() throws RecognitionException {
        try {
            int _type = SELECT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:879:7: ( S E L E C T )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:879:9: S E L E C T
            {
            mS(); 
            mE(); 
            mL(); 
            mE(); 
            mC(); 
            mT(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SELECT"

    // $ANTLR start "SET"
    public final void mSET() throws RecognitionException {
        try {
            int _type = SET;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:880:4: ( S E T )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:880:6: S E T
            {
            mS(); 
            mE(); 
            mT(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SET"

    // $ANTLR start "TABLE"
    public final void mTABLE() throws RecognitionException {
        try {
            int _type = TABLE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:881:6: ( T A B L E )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:881:8: T A B L E
            {
            mT(); 
            mA(); 
            mB(); 
            mL(); 
            mE(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TABLE"

    // $ANTLR start "TEMPORARY"
    public final void mTEMPORARY() throws RecognitionException {
        try {
            int _type = TEMPORARY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:882:10: ( T E M P ( O R A R Y )? )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:882:12: T E M P ( O R A R Y )?
            {
            mT(); 
            mE(); 
            mM(); 
            mP(); 
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:882:20: ( O R A R Y )?
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0=='O'||LA1_0=='o') ) {
                alt1=1;
            }
            switch (alt1) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:882:22: O R A R Y
                    {
                    mO(); 
                    mR(); 
                    mA(); 
                    mR(); 
                    mY(); 

                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TEMPORARY"

    // $ANTLR start "THEN"
    public final void mTHEN() throws RecognitionException {
        try {
            int _type = THEN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:883:5: ( T H E N )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:883:7: T H E N
            {
            mT(); 
            mH(); 
            mE(); 
            mN(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "THEN"

    // $ANTLR start "TO"
    public final void mTO() throws RecognitionException {
        try {
            int _type = TO;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:884:3: ( T O )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:884:5: T O
            {
            mT(); 
            mO(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TO"

    // $ANTLR start "TRANSACTION"
    public final void mTRANSACTION() throws RecognitionException {
        try {
            int _type = TRANSACTION;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:885:12: ( T R A N S A C T I O N )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:885:14: T R A N S A C T I O N
            {
            mT(); 
            mR(); 
            mA(); 
            mN(); 
            mS(); 
            mA(); 
            mC(); 
            mT(); 
            mI(); 
            mO(); 
            mN(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TRANSACTION"

    // $ANTLR start "TRIGGER"
    public final void mTRIGGER() throws RecognitionException {
        try {
            int _type = TRIGGER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:886:8: ( T R I G G E R )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:886:10: T R I G G E R
            {
            mT(); 
            mR(); 
            mI(); 
            mG(); 
            mG(); 
            mE(); 
            mR(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TRIGGER"

    // $ANTLR start "UNION"
    public final void mUNION() throws RecognitionException {
        try {
            int _type = UNION;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:887:6: ( U N I O N )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:887:8: U N I O N
            {
            mU(); 
            mN(); 
            mI(); 
            mO(); 
            mN(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "UNION"

    // $ANTLR start "UNIQUE"
    public final void mUNIQUE() throws RecognitionException {
        try {
            int _type = UNIQUE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:888:7: ( U N I Q U E )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:888:9: U N I Q U E
            {
            mU(); 
            mN(); 
            mI(); 
            mQ(); 
            mU(); 
            mE(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "UNIQUE"

    // $ANTLR start "UPDATE"
    public final void mUPDATE() throws RecognitionException {
        try {
            int _type = UPDATE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:889:7: ( U P D A T E )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:889:9: U P D A T E
            {
            mU(); 
            mP(); 
            mD(); 
            mA(); 
            mT(); 
            mE(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "UPDATE"

    // $ANTLR start "USING"
    public final void mUSING() throws RecognitionException {
        try {
            int _type = USING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:890:6: ( U S I N G )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:890:8: U S I N G
            {
            mU(); 
            mS(); 
            mI(); 
            mN(); 
            mG(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "USING"

    // $ANTLR start "VACUUM"
    public final void mVACUUM() throws RecognitionException {
        try {
            int _type = VACUUM;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:891:7: ( V A C U U M )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:891:9: V A C U U M
            {
            mV(); 
            mA(); 
            mC(); 
            mU(); 
            mU(); 
            mM(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "VACUUM"

    // $ANTLR start "VALUES"
    public final void mVALUES() throws RecognitionException {
        try {
            int _type = VALUES;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:892:7: ( V A L U E S )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:892:9: V A L U E S
            {
            mV(); 
            mA(); 
            mL(); 
            mU(); 
            mE(); 
            mS(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "VALUES"

    // $ANTLR start "VIEW"
    public final void mVIEW() throws RecognitionException {
        try {
            int _type = VIEW;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:893:5: ( V I E W )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:893:7: V I E W
            {
            mV(); 
            mI(); 
            mE(); 
            mW(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "VIEW"

    // $ANTLR start "VIRTUAL"
    public final void mVIRTUAL() throws RecognitionException {
        try {
            int _type = VIRTUAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:894:8: ( V I R T U A L )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:894:10: V I R T U A L
            {
            mV(); 
            mI(); 
            mR(); 
            mT(); 
            mU(); 
            mA(); 
            mL(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "VIRTUAL"

    // $ANTLR start "WHEN"
    public final void mWHEN() throws RecognitionException {
        try {
            int _type = WHEN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:895:5: ( W H E N )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:895:7: W H E N
            {
            mW(); 
            mH(); 
            mE(); 
            mN(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WHEN"

    // $ANTLR start "WHERE"
    public final void mWHERE() throws RecognitionException {
        try {
            int _type = WHERE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:896:6: ( W H E R E )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:896:8: W H E R E
            {
            mW(); 
            mH(); 
            mE(); 
            mR(); 
            mE(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WHERE"

    // $ANTLR start "ID_START"
    public final void mID_START() throws RecognitionException {
        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:898:18: ( ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '\"' ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:898:20: ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '\"' )
            {
            if ( input.LA(1)=='\"'||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "ID_START"

    // $ANTLR start "ID"
    public final void mID() throws RecognitionException {
        try {
            int _type = ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:899:3: ( ID_START ( ID_START | '0' .. '9' )* )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:899:5: ID_START ( ID_START | '0' .. '9' )*
            {
            mID_START(); 
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:899:14: ( ID_START | '0' .. '9' )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0=='\"'||(LA2_0>='0' && LA2_0<='9')||(LA2_0>='A' && LA2_0<='Z')||LA2_0=='_'||(LA2_0>='a' && LA2_0<='z')) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:
            	    {
            	    if ( input.LA(1)=='\"'||(input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ID"

    // $ANTLR start "ESCAPE_SEQ"
    public final void mESCAPE_SEQ() throws RecognitionException {
        try {
            int _type = ESCAPE_SEQ;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:901:11: ( '\\\\' ( '\\\"' | '\\'' | '\\\\' ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:901:13: '\\\\' ( '\\\"' | '\\'' | '\\\\' )
            {
            match('\\'); 
            if ( input.LA(1)=='\"'||input.LA(1)=='\''||input.LA(1)=='\\' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ESCAPE_SEQ"

    // $ANTLR start "STRING"
    public final void mSTRING() throws RecognitionException {
        try {
            int _type = STRING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:903:2: ( '\"' ( ESCAPE_SEQ | ~ ( '\\\\' | '\"' ) )* '\"' | '\\'' ( ESCAPE_SEQ | '\\'\\'' | ~ ( '\\\\' | '\\'' ) )* '\\'' )
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0=='\"') ) {
                alt5=1;
            }
            else if ( (LA5_0=='\'') ) {
                alt5=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;
            }
            switch (alt5) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:903:4: '\"' ( ESCAPE_SEQ | ~ ( '\\\\' | '\"' ) )* '\"'
                    {
                    match('\"'); 
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:903:8: ( ESCAPE_SEQ | ~ ( '\\\\' | '\"' ) )*
                    loop3:
                    do {
                        int alt3=3;
                        int LA3_0 = input.LA(1);

                        if ( (LA3_0=='\\') ) {
                            alt3=1;
                        }
                        else if ( ((LA3_0>='\u0000' && LA3_0<='!')||(LA3_0>='#' && LA3_0<='[')||(LA3_0>=']' && LA3_0<='\uFFFF')) ) {
                            alt3=2;
                        }


                        switch (alt3) {
                    	case 1 :
                    	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:903:10: ESCAPE_SEQ
                    	    {
                    	    mESCAPE_SEQ(); 

                    	    }
                    	    break;
                    	case 2 :
                    	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:903:24: ~ ( '\\\\' | '\"' )
                    	    {
                    	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='!')||(input.LA(1)>='#' && input.LA(1)<='[')||(input.LA(1)>=']' && input.LA(1)<='\uFFFF') ) {
                    	        input.consume();

                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;}


                    	    }
                    	    break;

                    	default :
                    	    break loop3;
                        }
                    } while (true);

                    match('\"'); 

                    }
                    break;
                case 2 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:904:4: '\\'' ( ESCAPE_SEQ | '\\'\\'' | ~ ( '\\\\' | '\\'' ) )* '\\''
                    {
                    match('\''); 
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:904:9: ( ESCAPE_SEQ | '\\'\\'' | ~ ( '\\\\' | '\\'' ) )*
                    loop4:
                    do {
                        int alt4=4;
                        int LA4_0 = input.LA(1);

                        if ( (LA4_0=='\'') ) {
                            int LA4_1 = input.LA(2);

                            if ( (LA4_1=='\'') ) {
                                alt4=2;
                            }


                        }
                        else if ( (LA4_0=='\\') ) {
                            alt4=1;
                        }
                        else if ( ((LA4_0>='\u0000' && LA4_0<='&')||(LA4_0>='(' && LA4_0<='[')||(LA4_0>=']' && LA4_0<='\uFFFF')) ) {
                            alt4=3;
                        }


                        switch (alt4) {
                    	case 1 :
                    	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:904:11: ESCAPE_SEQ
                    	    {
                    	    mESCAPE_SEQ(); 

                    	    }
                    	    break;
                    	case 2 :
                    	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:904:24: '\\'\\''
                    	    {
                    	    match("''"); 


                    	    }
                    	    break;
                    	case 3 :
                    	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:904:33: ~ ( '\\\\' | '\\'' )
                    	    {
                    	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='&')||(input.LA(1)>='(' && input.LA(1)<='[')||(input.LA(1)>=']' && input.LA(1)<='\uFFFF') ) {
                    	        input.consume();

                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;}


                    	    }
                    	    break;

                    	default :
                    	    break loop4;
                        }
                    } while (true);

                    match('\''); 

                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "STRING"

    // $ANTLR start "INTEGER"
    public final void mINTEGER() throws RecognitionException {
        try {
            int _type = INTEGER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:906:8: ( ( '0' .. '9' )+ )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:906:10: ( '0' .. '9' )+
            {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:906:10: ( '0' .. '9' )+
            int cnt6=0;
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( ((LA6_0>='0' && LA6_0<='9')) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:906:11: '0' .. '9'
            	    {
            	    matchRange('0','9'); 

            	    }
            	    break;

            	default :
            	    if ( cnt6 >= 1 ) break loop6;
                        EarlyExitException eee =
                            new EarlyExitException(6, input);
                        throw eee;
                }
                cnt6++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "INTEGER"

    // $ANTLR start "FLOAT_EXP"
    public final void mFLOAT_EXP() throws RecognitionException {
        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:907:20: ( ( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+ )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:907:22: ( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+
            {
            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:907:32: ( '+' | '-' )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0=='+'||LA7_0=='-') ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:
                    {
                    if ( input.LA(1)=='+'||input.LA(1)=='-' ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;}


                    }
                    break;

            }

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:907:43: ( '0' .. '9' )+
            int cnt8=0;
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( ((LA8_0>='0' && LA8_0<='9')) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:907:44: '0' .. '9'
            	    {
            	    matchRange('0','9'); 

            	    }
            	    break;

            	default :
            	    if ( cnt8 >= 1 ) break loop8;
                        EarlyExitException eee =
                            new EarlyExitException(8, input);
                        throw eee;
                }
                cnt8++;
            } while (true);


            }

        }
        finally {
        }
    }
    // $ANTLR end "FLOAT_EXP"

    // $ANTLR start "FLOAT"
    public final void mFLOAT() throws RecognitionException {
        try {
            int _type = FLOAT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:909:5: ( ( '0' .. '9' )+ '.' ( '0' .. '9' )* ( FLOAT_EXP )? | '.' ( '0' .. '9' )+ ( FLOAT_EXP )? | ( '0' .. '9' )+ FLOAT_EXP )
            int alt15=3;
            alt15 = dfa15.predict(input);
            switch (alt15) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:909:9: ( '0' .. '9' )+ '.' ( '0' .. '9' )* ( FLOAT_EXP )?
                    {
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:909:9: ( '0' .. '9' )+
                    int cnt9=0;
                    loop9:
                    do {
                        int alt9=2;
                        int LA9_0 = input.LA(1);

                        if ( ((LA9_0>='0' && LA9_0<='9')) ) {
                            alt9=1;
                        }


                        switch (alt9) {
                    	case 1 :
                    	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:909:10: '0' .. '9'
                    	    {
                    	    matchRange('0','9'); 

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt9 >= 1 ) break loop9;
                                EarlyExitException eee =
                                    new EarlyExitException(9, input);
                                throw eee;
                        }
                        cnt9++;
                    } while (true);

                    match('.'); 
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:909:25: ( '0' .. '9' )*
                    loop10:
                    do {
                        int alt10=2;
                        int LA10_0 = input.LA(1);

                        if ( ((LA10_0>='0' && LA10_0<='9')) ) {
                            alt10=1;
                        }


                        switch (alt10) {
                    	case 1 :
                    	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:909:26: '0' .. '9'
                    	    {
                    	    matchRange('0','9'); 

                    	    }
                    	    break;

                    	default :
                    	    break loop10;
                        }
                    } while (true);

                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:909:37: ( FLOAT_EXP )?
                    int alt11=2;
                    int LA11_0 = input.LA(1);

                    if ( (LA11_0=='E'||LA11_0=='e') ) {
                        alt11=1;
                    }
                    switch (alt11) {
                        case 1 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:909:37: FLOAT_EXP
                            {
                            mFLOAT_EXP(); 

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:910:9: '.' ( '0' .. '9' )+ ( FLOAT_EXP )?
                    {
                    match('.'); 
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:910:13: ( '0' .. '9' )+
                    int cnt12=0;
                    loop12:
                    do {
                        int alt12=2;
                        int LA12_0 = input.LA(1);

                        if ( ((LA12_0>='0' && LA12_0<='9')) ) {
                            alt12=1;
                        }


                        switch (alt12) {
                    	case 1 :
                    	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:910:14: '0' .. '9'
                    	    {
                    	    matchRange('0','9'); 

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt12 >= 1 ) break loop12;
                                EarlyExitException eee =
                                    new EarlyExitException(12, input);
                                throw eee;
                        }
                        cnt12++;
                    } while (true);

                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:910:25: ( FLOAT_EXP )?
                    int alt13=2;
                    int LA13_0 = input.LA(1);

                    if ( (LA13_0=='E'||LA13_0=='e') ) {
                        alt13=1;
                    }
                    switch (alt13) {
                        case 1 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:910:25: FLOAT_EXP
                            {
                            mFLOAT_EXP(); 

                            }
                            break;

                    }


                    }
                    break;
                case 3 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:911:9: ( '0' .. '9' )+ FLOAT_EXP
                    {
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:911:9: ( '0' .. '9' )+
                    int cnt14=0;
                    loop14:
                    do {
                        int alt14=2;
                        int LA14_0 = input.LA(1);

                        if ( ((LA14_0>='0' && LA14_0<='9')) ) {
                            alt14=1;
                        }


                        switch (alt14) {
                    	case 1 :
                    	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:911:10: '0' .. '9'
                    	    {
                    	    matchRange('0','9'); 

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt14 >= 1 ) break loop14;
                                EarlyExitException eee =
                                    new EarlyExitException(14, input);
                                throw eee;
                        }
                        cnt14++;
                    } while (true);

                    mFLOAT_EXP(); 

                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "FLOAT"

    // $ANTLR start "BLOB"
    public final void mBLOB() throws RecognitionException {
        try {
            int _type = BLOB;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:913:5: ( ( 'x' | 'X' ) '\\'' ( '0' .. '9' | 'a' .. 'f' | 'A' .. 'F' )+ '\\'' )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:913:7: ( 'x' | 'X' ) '\\'' ( '0' .. '9' | 'a' .. 'f' | 'A' .. 'F' )+ '\\''
            {
            if ( input.LA(1)=='X'||input.LA(1)=='x' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            match('\''); 
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:913:22: ( '0' .. '9' | 'a' .. 'f' | 'A' .. 'F' )+
            int cnt16=0;
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( ((LA16_0>='0' && LA16_0<='9')||(LA16_0>='A' && LA16_0<='F')||(LA16_0>='a' && LA16_0<='f')) ) {
                    alt16=1;
                }


                switch (alt16) {
            	case 1 :
            	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:
            	    {
            	    if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='F')||(input.LA(1)>='a' && input.LA(1)<='f') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    if ( cnt16 >= 1 ) break loop16;
                        EarlyExitException eee =
                            new EarlyExitException(16, input);
                        throw eee;
                }
                cnt16++;
            } while (true);

            match('\''); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "BLOB"

    // $ANTLR start "COMMENT"
    public final void mCOMMENT() throws RecognitionException {
        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:915:17: ( '/*' ( options {greedy=false; } : . )* '*/' )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:915:19: '/*' ( options {greedy=false; } : . )* '*/'
            {
            match("/*"); 

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:915:24: ( options {greedy=false; } : . )*
            loop17:
            do {
                int alt17=2;
                int LA17_0 = input.LA(1);

                if ( (LA17_0=='*') ) {
                    int LA17_1 = input.LA(2);

                    if ( (LA17_1=='/') ) {
                        alt17=2;
                    }
                    else if ( ((LA17_1>='\u0000' && LA17_1<='.')||(LA17_1>='0' && LA17_1<='\uFFFF')) ) {
                        alt17=1;
                    }


                }
                else if ( ((LA17_0>='\u0000' && LA17_0<=')')||(LA17_0>='+' && LA17_0<='\uFFFF')) ) {
                    alt17=1;
                }


                switch (alt17) {
            	case 1 :
            	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:915:52: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop17;
                }
            } while (true);

            match("*/"); 


            }

        }
        finally {
        }
    }
    // $ANTLR end "COMMENT"

    // $ANTLR start "LINE_COMMENT"
    public final void mLINE_COMMENT() throws RecognitionException {
        try {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:916:22: ( '--' (~ ( '\\n' | '\\r' ) )* ( ( '\\r' )? '\\n' | EOF ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:916:24: '--' (~ ( '\\n' | '\\r' ) )* ( ( '\\r' )? '\\n' | EOF )
            {
            match("--"); 

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:916:29: (~ ( '\\n' | '\\r' ) )*
            loop18:
            do {
                int alt18=2;
                int LA18_0 = input.LA(1);

                if ( ((LA18_0>='\u0000' && LA18_0<='\t')||(LA18_0>='\u000B' && LA18_0<='\f')||(LA18_0>='\u000E' && LA18_0<='\uFFFF')) ) {
                    alt18=1;
                }


                switch (alt18) {
            	case 1 :
            	    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:916:29: ~ ( '\\n' | '\\r' )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='\t')||(input.LA(1)>='\u000B' && input.LA(1)<='\f')||(input.LA(1)>='\u000E' && input.LA(1)<='\uFFFF') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop18;
                }
            } while (true);

            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:916:43: ( ( '\\r' )? '\\n' | EOF )
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0=='\n'||LA20_0=='\r') ) {
                alt20=1;
            }
            else {
                alt20=2;}
            switch (alt20) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:916:44: ( '\\r' )? '\\n'
                    {
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:916:44: ( '\\r' )?
                    int alt19=2;
                    int LA19_0 = input.LA(1);

                    if ( (LA19_0=='\r') ) {
                        alt19=1;
                    }
                    switch (alt19) {
                        case 1 :
                            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:916:44: '\\r'
                            {
                            match('\r'); 

                            }
                            break;

                    }

                    match('\n'); 

                    }
                    break;
                case 2 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:916:55: EOF
                    {
                    match(EOF); 

                    }
                    break;

            }


            }

        }
        finally {
        }
    }
    // $ANTLR end "LINE_COMMENT"

    // $ANTLR start "WS"
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:917:3: ( ( ' ' | '\\r' | '\\t' | '\\u000C' | '\\n' | COMMENT | LINE_COMMENT ) )
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:917:5: ( ' ' | '\\r' | '\\t' | '\\u000C' | '\\n' | COMMENT | LINE_COMMENT )
            {
            // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:917:5: ( ' ' | '\\r' | '\\t' | '\\u000C' | '\\n' | COMMENT | LINE_COMMENT )
            int alt21=7;
            switch ( input.LA(1) ) {
            case ' ':
                {
                alt21=1;
                }
                break;
            case '\r':
                {
                alt21=2;
                }
                break;
            case '\t':
                {
                alt21=3;
                }
                break;
            case '\f':
                {
                alt21=4;
                }
                break;
            case '\n':
                {
                alt21=5;
                }
                break;
            case '/':
                {
                alt21=6;
                }
                break;
            case '-':
                {
                alt21=7;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 21, 0, input);

                throw nvae;
            }

            switch (alt21) {
                case 1 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:917:6: ' '
                    {
                    match(' '); 

                    }
                    break;
                case 2 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:917:10: '\\r'
                    {
                    match('\r'); 

                    }
                    break;
                case 3 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:917:15: '\\t'
                    {
                    match('\t'); 

                    }
                    break;
                case 4 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:917:20: '\\u000C'
                    {
                    match('\f'); 

                    }
                    break;
                case 5 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:917:29: '\\n'
                    {
                    match('\n'); 

                    }
                    break;
                case 6 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:917:34: COMMENT
                    {
                    mCOMMENT(); 

                    }
                    break;
                case 7 :
                    // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:917:42: LINE_COMMENT
                    {
                    mLINE_COMMENT(); 

                    }
                    break;

            }

            _channel=HIDDEN;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WS"

    public void mTokens() throws RecognitionException {
        // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:8: ( EQUALS | EQUALS2 | NOT_EQUALS | NOT_EQUALS2 | LESS | LESS_OR_EQ | GREATER | GREATER_OR_EQ | SHIFT_LEFT | SHIFT_RIGHT | AMPERSAND | PIPE | DOUBLE_PIPE | PLUS | MINUS | TILDA | ASTERISK | SLASH | PERCENT | SEMI | DOT | COMMA | LPAREN | RPAREN | QUESTION | COLON | AT | DOLLAR | ABORT | ADD | AFTER | ALL | ALTER | ANALYZE | AND | AS | ASC | ATTACH | AUTOINCREMENT | BEFORE | BEGIN | BETWEEN | BY | BYTE | CASCADE | CASE | CAST | CHECK | COLLATE | COLUMN | META_COMMENT | COMMIT | CONFLICT | CONSTRAINT | CREATE | CROSS | CURRENT_TIME | CURRENT_DATE | CURRENT_TIMESTAMP | DATABASE | DEFAULT | DEFERRABLE | DEFERRED | DELETE | DESC | DETACH | DISTINCT | DROP | EACH | ELSE | END | ESCAPE | EXCEPT | EXCLUSIVE | EXISTS | EXPLAIN | FAIL | FOR | FOREIGN | FROM | GLOB | GROUP | HAVING | IF | IGNORE | IMMEDIATE | IN | INDEX | INDEXED | INITIALLY | INNER | INSERT | INSTEAD | INTERSECT | INTO | IS | ISNULL | JOIN | KEY | LEFT | LIKE | LIMIT | MATCH | MAX | NATURAL | NOT | NOTNULL | NULL | OF | OFFSET | ON | ONLY | OR | ORDER | OUTER | PLAN | PRAGMA | PRIMARY | QUERY | RAISE | REFERENCES | REGEXP | REINDEX | RELEASE | RENAME | REPLACE | REPLICATION | RESTRICT | ROLLBACK | ROW | SAVEPOINT | SCHEMA | SELECT | SET | TABLE | TEMPORARY | THEN | TO | TRANSACTION | TRIGGER | UNION | UNIQUE | UPDATE | USING | VACUUM | VALUES | VIEW | VIRTUAL | WHEN | WHERE | ID | ESCAPE_SEQ | STRING | INTEGER | FLOAT | BLOB | WS )
        int alt22=157;
        alt22 = dfa22.predict(input);
        switch (alt22) {
            case 1 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:10: EQUALS
                {
                mEQUALS(); 

                }
                break;
            case 2 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:17: EQUALS2
                {
                mEQUALS2(); 

                }
                break;
            case 3 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:25: NOT_EQUALS
                {
                mNOT_EQUALS(); 

                }
                break;
            case 4 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:36: NOT_EQUALS2
                {
                mNOT_EQUALS2(); 

                }
                break;
            case 5 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:48: LESS
                {
                mLESS(); 

                }
                break;
            case 6 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:53: LESS_OR_EQ
                {
                mLESS_OR_EQ(); 

                }
                break;
            case 7 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:64: GREATER
                {
                mGREATER(); 

                }
                break;
            case 8 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:72: GREATER_OR_EQ
                {
                mGREATER_OR_EQ(); 

                }
                break;
            case 9 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:86: SHIFT_LEFT
                {
                mSHIFT_LEFT(); 

                }
                break;
            case 10 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:97: SHIFT_RIGHT
                {
                mSHIFT_RIGHT(); 

                }
                break;
            case 11 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:109: AMPERSAND
                {
                mAMPERSAND(); 

                }
                break;
            case 12 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:119: PIPE
                {
                mPIPE(); 

                }
                break;
            case 13 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:124: DOUBLE_PIPE
                {
                mDOUBLE_PIPE(); 

                }
                break;
            case 14 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:136: PLUS
                {
                mPLUS(); 

                }
                break;
            case 15 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:141: MINUS
                {
                mMINUS(); 

                }
                break;
            case 16 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:147: TILDA
                {
                mTILDA(); 

                }
                break;
            case 17 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:153: ASTERISK
                {
                mASTERISK(); 

                }
                break;
            case 18 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:162: SLASH
                {
                mSLASH(); 

                }
                break;
            case 19 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:168: PERCENT
                {
                mPERCENT(); 

                }
                break;
            case 20 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:176: SEMI
                {
                mSEMI(); 

                }
                break;
            case 21 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:181: DOT
                {
                mDOT(); 

                }
                break;
            case 22 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:185: COMMA
                {
                mCOMMA(); 

                }
                break;
            case 23 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:191: LPAREN
                {
                mLPAREN(); 

                }
                break;
            case 24 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:198: RPAREN
                {
                mRPAREN(); 

                }
                break;
            case 25 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:205: QUESTION
                {
                mQUESTION(); 

                }
                break;
            case 26 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:214: COLON
                {
                mCOLON(); 

                }
                break;
            case 27 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:220: AT
                {
                mAT(); 

                }
                break;
            case 28 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:223: DOLLAR
                {
                mDOLLAR(); 

                }
                break;
            case 29 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:230: ABORT
                {
                mABORT(); 

                }
                break;
            case 30 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:236: ADD
                {
                mADD(); 

                }
                break;
            case 31 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:240: AFTER
                {
                mAFTER(); 

                }
                break;
            case 32 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:246: ALL
                {
                mALL(); 

                }
                break;
            case 33 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:250: ALTER
                {
                mALTER(); 

                }
                break;
            case 34 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:256: ANALYZE
                {
                mANALYZE(); 

                }
                break;
            case 35 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:264: AND
                {
                mAND(); 

                }
                break;
            case 36 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:268: AS
                {
                mAS(); 

                }
                break;
            case 37 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:271: ASC
                {
                mASC(); 

                }
                break;
            case 38 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:275: ATTACH
                {
                mATTACH(); 

                }
                break;
            case 39 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:282: AUTOINCREMENT
                {
                mAUTOINCREMENT(); 

                }
                break;
            case 40 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:296: BEFORE
                {
                mBEFORE(); 

                }
                break;
            case 41 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:303: BEGIN
                {
                mBEGIN(); 

                }
                break;
            case 42 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:309: BETWEEN
                {
                mBETWEEN(); 

                }
                break;
            case 43 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:317: BY
                {
                mBY(); 

                }
                break;
            case 44 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:320: BYTE
                {
                mBYTE(); 

                }
                break;
            case 45 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:325: CASCADE
                {
                mCASCADE(); 

                }
                break;
            case 46 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:333: CASE
                {
                mCASE(); 

                }
                break;
            case 47 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:338: CAST
                {
                mCAST(); 

                }
                break;
            case 48 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:343: CHECK
                {
                mCHECK(); 

                }
                break;
            case 49 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:349: COLLATE
                {
                mCOLLATE(); 

                }
                break;
            case 50 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:357: COLUMN
                {
                mCOLUMN(); 

                }
                break;
            case 51 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:364: META_COMMENT
                {
                mMETA_COMMENT(); 

                }
                break;
            case 52 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:377: COMMIT
                {
                mCOMMIT(); 

                }
                break;
            case 53 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:384: CONFLICT
                {
                mCONFLICT(); 

                }
                break;
            case 54 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:393: CONSTRAINT
                {
                mCONSTRAINT(); 

                }
                break;
            case 55 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:404: CREATE
                {
                mCREATE(); 

                }
                break;
            case 56 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:411: CROSS
                {
                mCROSS(); 

                }
                break;
            case 57 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:417: CURRENT_TIME
                {
                mCURRENT_TIME(); 

                }
                break;
            case 58 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:430: CURRENT_DATE
                {
                mCURRENT_DATE(); 

                }
                break;
            case 59 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:443: CURRENT_TIMESTAMP
                {
                mCURRENT_TIMESTAMP(); 

                }
                break;
            case 60 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:461: DATABASE
                {
                mDATABASE(); 

                }
                break;
            case 61 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:470: DEFAULT
                {
                mDEFAULT(); 

                }
                break;
            case 62 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:478: DEFERRABLE
                {
                mDEFERRABLE(); 

                }
                break;
            case 63 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:489: DEFERRED
                {
                mDEFERRED(); 

                }
                break;
            case 64 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:498: DELETE
                {
                mDELETE(); 

                }
                break;
            case 65 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:505: DESC
                {
                mDESC(); 

                }
                break;
            case 66 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:510: DETACH
                {
                mDETACH(); 

                }
                break;
            case 67 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:517: DISTINCT
                {
                mDISTINCT(); 

                }
                break;
            case 68 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:526: DROP
                {
                mDROP(); 

                }
                break;
            case 69 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:531: EACH
                {
                mEACH(); 

                }
                break;
            case 70 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:536: ELSE
                {
                mELSE(); 

                }
                break;
            case 71 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:541: END
                {
                mEND(); 

                }
                break;
            case 72 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:545: ESCAPE
                {
                mESCAPE(); 

                }
                break;
            case 73 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:552: EXCEPT
                {
                mEXCEPT(); 

                }
                break;
            case 74 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:559: EXCLUSIVE
                {
                mEXCLUSIVE(); 

                }
                break;
            case 75 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:569: EXISTS
                {
                mEXISTS(); 

                }
                break;
            case 76 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:576: EXPLAIN
                {
                mEXPLAIN(); 

                }
                break;
            case 77 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:584: FAIL
                {
                mFAIL(); 

                }
                break;
            case 78 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:589: FOR
                {
                mFOR(); 

                }
                break;
            case 79 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:593: FOREIGN
                {
                mFOREIGN(); 

                }
                break;
            case 80 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:601: FROM
                {
                mFROM(); 

                }
                break;
            case 81 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:606: GLOB
                {
                mGLOB(); 

                }
                break;
            case 82 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:611: GROUP
                {
                mGROUP(); 

                }
                break;
            case 83 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:617: HAVING
                {
                mHAVING(); 

                }
                break;
            case 84 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:624: IF
                {
                mIF(); 

                }
                break;
            case 85 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:627: IGNORE
                {
                mIGNORE(); 

                }
                break;
            case 86 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:634: IMMEDIATE
                {
                mIMMEDIATE(); 

                }
                break;
            case 87 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:644: IN
                {
                mIN(); 

                }
                break;
            case 88 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:647: INDEX
                {
                mINDEX(); 

                }
                break;
            case 89 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:653: INDEXED
                {
                mINDEXED(); 

                }
                break;
            case 90 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:661: INITIALLY
                {
                mINITIALLY(); 

                }
                break;
            case 91 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:671: INNER
                {
                mINNER(); 

                }
                break;
            case 92 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:677: INSERT
                {
                mINSERT(); 

                }
                break;
            case 93 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:684: INSTEAD
                {
                mINSTEAD(); 

                }
                break;
            case 94 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:692: INTERSECT
                {
                mINTERSECT(); 

                }
                break;
            case 95 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:702: INTO
                {
                mINTO(); 

                }
                break;
            case 96 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:707: IS
                {
                mIS(); 

                }
                break;
            case 97 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:710: ISNULL
                {
                mISNULL(); 

                }
                break;
            case 98 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:717: JOIN
                {
                mJOIN(); 

                }
                break;
            case 99 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:722: KEY
                {
                mKEY(); 

                }
                break;
            case 100 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:726: LEFT
                {
                mLEFT(); 

                }
                break;
            case 101 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:731: LIKE
                {
                mLIKE(); 

                }
                break;
            case 102 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:736: LIMIT
                {
                mLIMIT(); 

                }
                break;
            case 103 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:742: MATCH
                {
                mMATCH(); 

                }
                break;
            case 104 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:748: MAX
                {
                mMAX(); 

                }
                break;
            case 105 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:752: NATURAL
                {
                mNATURAL(); 

                }
                break;
            case 106 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:760: NOT
                {
                mNOT(); 

                }
                break;
            case 107 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:764: NOTNULL
                {
                mNOTNULL(); 

                }
                break;
            case 108 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:772: NULL
                {
                mNULL(); 

                }
                break;
            case 109 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:777: OF
                {
                mOF(); 

                }
                break;
            case 110 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:780: OFFSET
                {
                mOFFSET(); 

                }
                break;
            case 111 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:787: ON
                {
                mON(); 

                }
                break;
            case 112 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:790: ONLY
                {
                mONLY(); 

                }
                break;
            case 113 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:795: OR
                {
                mOR(); 

                }
                break;
            case 114 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:798: ORDER
                {
                mORDER(); 

                }
                break;
            case 115 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:804: OUTER
                {
                mOUTER(); 

                }
                break;
            case 116 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:810: PLAN
                {
                mPLAN(); 

                }
                break;
            case 117 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:815: PRAGMA
                {
                mPRAGMA(); 

                }
                break;
            case 118 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:822: PRIMARY
                {
                mPRIMARY(); 

                }
                break;
            case 119 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:830: QUERY
                {
                mQUERY(); 

                }
                break;
            case 120 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:836: RAISE
                {
                mRAISE(); 

                }
                break;
            case 121 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:842: REFERENCES
                {
                mREFERENCES(); 

                }
                break;
            case 122 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:853: REGEXP
                {
                mREGEXP(); 

                }
                break;
            case 123 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:860: REINDEX
                {
                mREINDEX(); 

                }
                break;
            case 124 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:868: RELEASE
                {
                mRELEASE(); 

                }
                break;
            case 125 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:876: RENAME
                {
                mRENAME(); 

                }
                break;
            case 126 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:883: REPLACE
                {
                mREPLACE(); 

                }
                break;
            case 127 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:891: REPLICATION
                {
                mREPLICATION(); 

                }
                break;
            case 128 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:903: RESTRICT
                {
                mRESTRICT(); 

                }
                break;
            case 129 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:912: ROLLBACK
                {
                mROLLBACK(); 

                }
                break;
            case 130 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:921: ROW
                {
                mROW(); 

                }
                break;
            case 131 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:925: SAVEPOINT
                {
                mSAVEPOINT(); 

                }
                break;
            case 132 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:935: SCHEMA
                {
                mSCHEMA(); 

                }
                break;
            case 133 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:942: SELECT
                {
                mSELECT(); 

                }
                break;
            case 134 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:949: SET
                {
                mSET(); 

                }
                break;
            case 135 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:953: TABLE
                {
                mTABLE(); 

                }
                break;
            case 136 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:959: TEMPORARY
                {
                mTEMPORARY(); 

                }
                break;
            case 137 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:969: THEN
                {
                mTHEN(); 

                }
                break;
            case 138 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:974: TO
                {
                mTO(); 

                }
                break;
            case 139 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:977: TRANSACTION
                {
                mTRANSACTION(); 

                }
                break;
            case 140 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:989: TRIGGER
                {
                mTRIGGER(); 

                }
                break;
            case 141 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:997: UNION
                {
                mUNION(); 

                }
                break;
            case 142 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:1003: UNIQUE
                {
                mUNIQUE(); 

                }
                break;
            case 143 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:1010: UPDATE
                {
                mUPDATE(); 

                }
                break;
            case 144 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:1017: USING
                {
                mUSING(); 

                }
                break;
            case 145 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:1023: VACUUM
                {
                mVACUUM(); 

                }
                break;
            case 146 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:1030: VALUES
                {
                mVALUES(); 

                }
                break;
            case 147 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:1037: VIEW
                {
                mVIEW(); 

                }
                break;
            case 148 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:1042: VIRTUAL
                {
                mVIRTUAL(); 

                }
                break;
            case 149 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:1050: WHEN
                {
                mWHEN(); 

                }
                break;
            case 150 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:1055: WHERE
                {
                mWHERE(); 

                }
                break;
            case 151 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:1061: ID
                {
                mID(); 

                }
                break;
            case 152 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:1064: ESCAPE_SEQ
                {
                mESCAPE_SEQ(); 

                }
                break;
            case 153 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:1075: STRING
                {
                mSTRING(); 

                }
                break;
            case 154 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:1082: INTEGER
                {
                mINTEGER(); 

                }
                break;
            case 155 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:1090: FLOAT
                {
                mFLOAT(); 

                }
                break;
            case 156 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:1096: BLOB
                {
                mBLOB(); 

                }
                break;
            case 157 :
                // /home/jchoyt/devel/openii/SchemaStoreClient/src/org/mitre/schemastore/porters/schemaImporters/ddl/Sql.g:1:1101: WS
                {
                mWS(); 

                }
                break;

        }

    }


    protected DFA15 dfa15 = new DFA15(this);
    protected DFA22 dfa22 = new DFA22(this);
    static final String DFA15_eotS =
        "\5\uffff";
    static final String DFA15_eofS =
        "\5\uffff";
    static final String DFA15_minS =
        "\2\56\3\uffff";
    static final String DFA15_maxS =
        "\1\71\1\145\3\uffff";
    static final String DFA15_acceptS =
        "\2\uffff\1\2\1\3\1\1";
    static final String DFA15_specialS =
        "\5\uffff}>";
    static final String[] DFA15_transitionS = {
            "\1\2\1\uffff\12\1",
            "\1\4\1\uffff\12\1\13\uffff\1\3\37\uffff\1\3",
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
            return "908:1: FLOAT : ( ( '0' .. '9' )+ '.' ( '0' .. '9' )* ( FLOAT_EXP )? | '.' ( '0' .. '9' )+ ( FLOAT_EXP )? | ( '0' .. '9' )+ FLOAT_EXP );";
        }
    }
    static final String DFA22_eotS =
        "\1\uffff\1\65\1\uffff\1\71\1\74\1\uffff\1\76\1\uffff\1\77\2\uffff"+
        "\1\100\2\uffff\1\101\7\uffff\30\62\1\uffff\1\62\1\uffff\1\u0089"+
        "\21\uffff\6\62\1\u0092\2\62\1\u0098\24\62\1\u00b6\1\u00bc\1\62\1"+
        "\u00bf\12\62\1\u00cc\1\u00ce\1\u00d0\14\62\1\u00e8\10\62\3\uffff"+
        "\1\u00f2\2\62\1\u00f5\1\62\1\u00f7\2\62\1\uffff\1\u00fa\4\62\1\uffff"+
        "\26\62\1\u011b\1\62\1\u011d\4\62\1\uffff\5\62\1\uffff\2\62\1\uffff"+
        "\2\62\1\u012e\3\62\1\u0132\1\62\1\u0134\3\62\1\uffff\1\62\1\uffff"+
        "\1\62\1\uffff\6\62\1\u0141\11\62\1\u014b\6\62\1\uffff\11\62\1\uffff"+
        "\2\62\1\uffff\1\62\1\uffff\2\62\1\uffff\4\62\1\u0166\12\62\1\u0172"+
        "\1\u0173\3\62\1\u0177\2\62\1\u017a\1\62\1\u017c\1\u017d\5\62\1\uffff"+
        "\1\u0183\1\uffff\1\62\1\u0185\1\u0186\6\62\1\u018d\5\62\1\u0193"+
        "\1\uffff\1\u0194\1\62\1\u0196\1\uffff\1\62\1\uffff\1\62\1\u0199"+
        "\2\62\1\u019c\2\62\1\u019f\4\62\1\uffff\11\62\1\uffff\4\62\1\u01b2"+
        "\1\u01b3\10\62\1\u01bd\1\62\1\u01bf\1\u01c0\1\u01c1\3\62\1\u01c5"+
        "\2\62\1\u01c8\1\uffff\7\62\1\u01d0\1\62\1\u01d2\1\62\2\uffff\3\62"+
        "\1\uffff\2\62\1\uffff\1\62\2\uffff\5\62\1\uffff\1\62\2\uffff\1\u01e0"+
        "\2\62\1\u01e3\2\62\1\uffff\1\62\1\u01e8\3\62\2\uffff\1\u01ec\1\uffff"+
        "\1\u01ed\1\62\1\uffff\1\62\1\u01f0\1\uffff\1\u01f1\1\62\1\uffff"+
        "\2\62\1\u01f5\11\62\1\u01ff\5\62\2\uffff\1\62\1\u0206\2\62\1\u0209"+
        "\1\u020a\3\62\1\uffff\1\u020e\3\uffff\1\u020f\2\62\1\uffff\1\u0212"+
        "\1\62\1\uffff\2\62\1\u0216\2\62\1\u0219\1\u021a\1\uffff\1\62\1\uffff"+
        "\4\62\1\u0221\1\u0222\2\62\1\u0225\1\u0226\1\62\1\u0228\1\62\1\uffff"+
        "\1\u022a\1\62\1\uffff\1\62\1\u022d\2\62\1\uffff\1\u0230\1\u0231"+
        "\1\62\2\uffff\2\62\2\uffff\1\u0235\1\62\1\u0237\1\uffff\2\62\1\u023a"+
        "\4\62\1\u023f\1\62\1\uffff\1\u0241\1\u0242\4\62\1\uffff\1\u0247"+
        "\1\u0248\2\uffff\1\u0249\1\u024a\1\62\2\uffff\1\u024c\1\62\1\uffff"+
        "\1\u024e\2\62\1\uffff\1\u0251\1\u0252\2\uffff\1\62\1\u0254\3\62"+
        "\1\u0258\2\uffff\2\62\2\uffff\1\u025b\1\uffff\1\u025c\1\uffff\1"+
        "\62\1\u025e\1\uffff\1\u025f\1\62\2\uffff\1\62\1\u0262\1\u0263\1"+
        "\uffff\1\u0264\1\uffff\2\62\1\uffff\1\62\1\u0268\1\62\1\u026a\1"+
        "\uffff\1\u026b\2\uffff\2\62\1\u026e\1\62\4\uffff\1\u0270\1\uffff"+
        "\1\62\1\uffff\1\62\1\u0273\2\uffff\1\62\1\uffff\1\u0276\1\u0277"+
        "\1\62\1\uffff\1\u0279\1\62\2\uffff\1\62\2\uffff\2\62\3\uffff\1\u027e"+
        "\1\62\1\u0280\1\uffff\1\62\2\uffff\2\62\1\uffff\1\62\1\uffff\2\62"+
        "\1\uffff\2\62\2\uffff\1\62\1\uffff\1\u028a\1\u028b\1\u028c\1\u028d"+
        "\1\uffff\1\62\1\uffff\1\62\1\u0290\1\62\1\u01b3\1\62\1\u0293\2\62"+
        "\1\u0296\4\uffff\1\u0297\1\62\1\uffff\2\62\1\uffff\2\62\2\uffff"+
        "\1\u029d\1\u029e\1\62\1\u02a0\1\u02a2\2\uffff\1\u02a3\1\uffff\1"+
        "\62\2\uffff\3\62\1\u02a8\1\uffff";
    static final String DFA22_eofS =
        "\u02a9\uffff";
    static final String DFA22_minS =
        "\1\11\1\75\1\uffff\1\74\1\75\1\uffff\1\174\1\uffff\1\55\2\uffff"+
        "\1\52\2\uffff\1\60\7\uffff\1\102\1\105\4\101\1\114\1\101\1\106\1"+
        "\117\2\105\2\101\1\106\1\114\1\125\3\101\1\116\1\101\1\110\1\0\1"+
        "\uffff\1\47\1\uffff\1\56\21\uffff\1\104\1\117\1\114\1\124\1\101"+
        "\1\124\1\42\1\124\1\106\1\42\1\114\1\105\1\122\1\105\2\123\1\106"+
        "\1\117\1\124\1\123\3\103\1\104\1\111\1\122\3\117\1\126\2\42\1\116"+
        "\1\42\1\115\1\111\1\131\1\113\1\106\2\124\1\114\2\124\3\42\2\101"+
        "\1\105\1\114\1\106\1\111\1\114\1\110\1\126\1\101\1\105\1\115\1\42"+
        "\1\102\1\104\2\111\1\103\2\105\1\0\3\uffff\1\42\1\122\1\105\1\42"+
        "\1\101\1\42\1\114\1\117\1\uffff\1\42\1\105\1\117\1\127\1\111\1\uffff"+
        "\1\105\1\106\1\114\1\115\1\101\1\123\1\122\2\103\1\124\1\101\1\103"+
        "\1\105\1\101\1\120\1\101\1\105\1\110\1\105\1\123\1\114\1\101\1\42"+
        "\1\114\1\42\1\115\1\102\1\125\1\111\1\uffff\1\124\4\105\1\uffff"+
        "\1\125\1\117\1\uffff\1\105\1\116\1\42\1\105\1\111\1\124\1\42\1\103"+
        "\1\42\1\114\1\125\1\105\1\uffff\1\131\1\uffff\1\105\1\uffff\1\123"+
        "\1\116\1\115\1\107\1\122\1\114\1\42\1\105\1\101\1\124\1\105\1\114"+
        "\1\105\1\116\1\123\1\105\1\42\2\105\1\116\1\107\1\116\1\120\1\uffff"+
        "\1\114\1\101\1\117\1\116\2\125\1\124\1\127\1\116\1\uffff\1\124\1"+
        "\122\1\uffff\1\103\1\uffff\1\131\1\111\1\uffff\2\122\1\105\1\116"+
        "\1\42\1\124\1\114\1\115\1\101\1\105\1\124\1\123\1\105\1\113\1\101"+
        "\2\42\1\111\1\122\1\125\1\42\1\124\1\103\1\42\1\102\2\42\1\125\1"+
        "\120\1\124\1\101\1\120\1\uffff\1\42\1\uffff\1\111\2\42\1\120\1\116"+
        "\1\111\1\130\1\122\1\105\1\42\2\122\1\114\1\122\1\104\1\42\1\uffff"+
        "\1\42\1\124\1\42\1\uffff\1\110\1\uffff\1\125\1\42\2\122\1\42\1\122"+
        "\1\105\1\42\1\101\1\115\1\131\1\102\1\uffff\1\122\1\115\1\122\2"+
        "\101\1\130\1\104\1\105\1\103\1\uffff\1\115\1\120\1\123\1\107\2\42"+
        "\1\105\1\124\1\125\1\116\1\107\1\105\2\125\1\42\1\105\3\42\1\110"+
        "\1\132\1\116\1\42\2\105\1\42\1\uffff\1\122\1\111\1\116\1\124\1\116"+
        "\1\124\1\105\1\42\1\116\1\42\1\104\2\uffff\1\116\1\122\1\114\1\uffff"+
        "\1\105\1\110\1\uffff\1\101\2\uffff\1\123\1\124\1\123\1\111\1\105"+
        "\1\uffff\1\107\2\uffff\1\42\1\107\1\101\1\42\1\124\1\101\1\uffff"+
        "\1\123\1\42\1\114\1\105\1\111\2\uffff\1\42\1\uffff\1\42\1\114\1"+
        "\uffff\1\101\1\42\1\uffff\1\42\1\124\1\uffff\1\122\1\101\1\42\1"+
        "\101\2\105\1\111\1\123\2\103\1\120\1\105\1\42\1\124\1\101\1\117"+
        "\1\101\1\105\2\uffff\1\122\1\42\2\105\2\42\1\123\1\115\1\101\1\uffff"+
        "\1\42\3\uffff\1\42\1\105\1\103\1\uffff\1\42\1\116\1\uffff\1\101"+
        "\1\103\1\42\1\105\1\124\2\42\1\uffff\1\124\1\uffff\1\105\1\103\1"+
        "\101\1\124\2\42\1\123\1\111\2\42\1\116\1\42\1\116\1\uffff\1\42\1"+
        "\114\1\uffff\1\104\1\42\1\104\1\105\1\uffff\2\42\1\101\2\uffff\2"+
        "\114\2\uffff\1\42\1\131\1\42\1\uffff\1\103\1\116\1\42\1\103\1\105"+
        "\1\101\1\105\1\42\1\130\1\uffff\2\42\1\111\1\103\1\122\1\101\1\uffff"+
        "\2\42\2\uffff\2\42\1\114\2\uffff\1\42\1\122\1\uffff\1\42\1\111\1"+
        "\124\1\uffff\2\42\2\uffff\1\137\1\42\1\124\1\104\1\102\1\42\2\uffff"+
        "\1\105\1\126\2\uffff\1\42\1\uffff\1\42\1\uffff\1\114\1\42\1\uffff"+
        "\1\42\1\103\2\uffff\1\124\2\42\1\uffff\1\42\1\uffff\1\113\1\103"+
        "\1\uffff\1\124\1\42\1\124\1\42\1\uffff\1\42\2\uffff\1\116\1\124"+
        "\1\42\1\122\4\uffff\1\42\1\uffff\1\105\1\uffff\1\116\1\42\2\uffff"+
        "\1\104\1\uffff\2\42\1\114\1\uffff\1\42\1\105\2\uffff\1\131\2\uffff"+
        "\1\124\1\105\3\uffff\1\42\1\105\1\42\1\uffff\1\111\2\uffff\1\124"+
        "\1\111\1\uffff\1\131\1\uffff\1\115\1\124\1\uffff\1\111\1\101\2\uffff"+
        "\1\105\1\uffff\4\42\1\uffff\1\123\1\uffff\1\117\1\42\1\117\1\42"+
        "\1\105\1\42\1\115\1\124\1\42\4\uffff\1\42\1\116\1\uffff\2\116\1"+
        "\uffff\2\105\2\uffff\2\42\1\124\2\42\2\uffff\1\42\1\uffff\1\124"+
        "\2\uffff\1\101\1\115\1\120\1\42\1\uffff";
    static final String DFA22_maxS =
        "\1\176\1\75\1\uffff\2\76\1\uffff\1\174\1\uffff\1\55\2\uffff\1\52"+
        "\2\uffff\1\71\7\uffff\1\165\1\171\1\165\1\162\1\170\2\162\1\141"+
        "\1\163\1\157\1\145\1\151\1\141\2\165\1\162\1\165\1\157\1\145\1\162"+
        "\1\163\1\151\1\150\1\uffff\1\uffff\1\47\1\uffff\1\145\21\uffff\1"+
        "\144\1\157\2\164\1\144\1\164\1\172\2\164\1\172\1\156\1\157\1\162"+
        "\1\145\2\163\1\164\1\157\1\164\1\163\1\143\1\160\1\143\1\144\1\151"+
        "\1\162\3\157\1\166\2\172\1\156\1\172\1\155\1\151\1\171\1\155\1\146"+
        "\1\170\1\164\1\154\2\164\3\172\1\141\1\151\1\145\1\167\1\163\1\151"+
        "\1\164\1\150\1\166\1\151\1\145\1\155\1\172\1\142\1\144\2\151\1\154"+
        "\1\162\1\145\1\uffff\3\uffff\1\172\1\162\1\145\1\172\1\141\1\172"+
        "\1\154\1\157\1\uffff\1\172\1\145\1\157\1\167\1\151\1\uffff\1\145"+
        "\1\163\1\165\1\155\1\141\1\163\1\162\1\143\2\164\1\145\1\143\1\145"+
        "\1\141\1\160\1\141\1\145\1\150\1\154\1\163\1\154\1\141\1\172\1\154"+
        "\1\172\1\155\1\142\1\165\1\151\1\uffff\1\164\1\145\1\164\1\157\1"+
        "\145\1\uffff\1\165\1\157\1\uffff\1\145\1\156\1\172\1\145\1\151\1"+
        "\164\1\172\1\143\1\172\1\154\1\165\1\145\1\uffff\1\171\1\uffff\1"+
        "\145\1\uffff\1\163\1\156\1\155\1\147\1\162\1\154\1\172\1\145\1\141"+
        "\1\164\1\145\1\154\1\145\1\156\1\163\1\145\1\172\2\145\1\156\1\147"+
        "\1\156\1\160\1\uffff\1\154\1\141\1\161\1\156\2\165\1\164\1\167\1"+
        "\162\1\uffff\1\164\1\162\1\uffff\1\143\1\uffff\1\171\1\151\1\uffff"+
        "\2\162\1\145\1\156\1\172\1\164\1\154\1\155\1\141\1\151\1\164\1\163"+
        "\1\145\1\153\1\141\2\172\1\151\1\162\1\165\1\172\1\164\1\143\1\172"+
        "\1\142\2\172\1\165\1\160\1\164\1\141\1\160\1\uffff\1\172\1\uffff"+
        "\1\151\2\172\1\160\1\156\1\151\1\170\1\162\1\145\1\172\2\162\1\154"+
        "\1\162\1\144\1\172\1\uffff\1\172\1\164\1\172\1\uffff\1\150\1\uffff"+
        "\1\165\1\172\2\162\1\172\1\162\1\145\1\172\1\141\1\155\1\171\1\142"+
        "\1\uffff\1\162\1\155\1\162\1\141\1\151\1\170\1\144\1\145\1\143\1"+
        "\uffff\1\155\1\160\1\163\1\147\2\172\1\145\1\164\1\165\1\156\1\147"+
        "\1\145\2\165\1\172\1\145\3\172\1\150\1\172\1\156\1\172\2\145\1\172"+
        "\1\uffff\1\162\1\151\1\156\1\164\1\156\1\164\1\145\1\172\1\156\1"+
        "\172\1\144\2\uffff\1\156\1\162\1\154\1\uffff\1\145\1\150\1\uffff"+
        "\1\141\2\uffff\1\163\1\164\1\163\1\151\1\145\1\uffff\1\147\2\uffff"+
        "\1\172\1\147\1\141\1\172\1\164\1\141\1\uffff\1\163\1\172\1\154\1"+
        "\145\1\151\2\uffff\1\172\1\uffff\1\172\1\154\1\uffff\1\141\1\172"+
        "\1\uffff\1\172\1\164\1\uffff\1\162\1\141\1\172\1\141\2\145\1\151"+
        "\1\163\2\143\1\160\1\145\1\172\1\164\1\141\1\157\1\141\1\145\2\uffff"+
        "\1\162\1\172\2\145\2\172\1\163\1\155\1\141\1\uffff\1\172\3\uffff"+
        "\1\172\1\145\1\143\1\uffff\1\172\1\156\1\uffff\1\141\1\143\1\172"+
        "\1\145\1\164\2\172\1\uffff\1\164\1\uffff\1\145\1\143\1\145\1\164"+
        "\2\172\1\163\1\151\2\172\1\156\1\172\1\156\1\uffff\1\172\1\154\1"+
        "\uffff\1\144\1\172\1\144\1\145\1\uffff\2\172\1\141\2\uffff\2\154"+
        "\2\uffff\1\172\1\171\1\172\1\uffff\1\143\1\156\1\172\1\143\1\145"+
        "\1\141\1\145\1\172\1\170\1\uffff\2\172\1\151\1\143\1\162\1\141\1"+
        "\uffff\2\172\2\uffff\2\172\1\154\2\uffff\1\172\1\162\1\uffff\1\172"+
        "\1\151\1\164\1\uffff\2\172\2\uffff\1\137\1\172\1\164\1\144\1\142"+
        "\1\172\2\uffff\1\145\1\166\2\uffff\1\172\1\uffff\1\172\1\uffff\1"+
        "\154\1\172\1\uffff\1\172\1\143\2\uffff\1\164\2\172\1\uffff\1\172"+
        "\1\uffff\1\153\1\143\1\uffff\1\164\1\172\1\164\1\172\1\uffff\1\172"+
        "\2\uffff\1\156\1\164\1\172\1\162\4\uffff\1\172\1\uffff\1\145\1\uffff"+
        "\1\156\1\172\2\uffff\1\164\1\uffff\2\172\1\154\1\uffff\1\172\1\145"+
        "\2\uffff\1\171\2\uffff\1\164\1\145\3\uffff\1\172\1\145\1\172\1\uffff"+
        "\1\151\2\uffff\1\164\1\151\1\uffff\1\171\1\uffff\1\155\1\164\1\uffff"+
        "\1\151\1\141\2\uffff\1\145\1\uffff\4\172\1\uffff\1\163\1\uffff\1"+
        "\157\1\172\1\157\1\172\1\145\1\172\1\155\1\164\1\172\4\uffff\1\172"+
        "\1\156\1\uffff\2\156\1\uffff\2\145\2\uffff\2\172\1\164\2\172\2\uffff"+
        "\1\172\1\uffff\1\164\2\uffff\1\141\1\155\1\160\1\172\1\uffff";
    static final String DFA22_acceptS =
        "\2\uffff\1\3\2\uffff\1\13\1\uffff\1\16\1\uffff\1\20\1\21\1\uffff"+
        "\1\23\1\24\1\uffff\1\26\1\27\1\30\1\31\1\32\1\33\1\34\30\uffff\1"+
        "\u0098\1\uffff\1\u0099\1\uffff\1\u0097\1\u009d\1\2\1\1\1\4\1\6\1"+
        "\11\1\5\1\10\1\12\1\7\1\15\1\14\1\17\1\22\1\25\1\u009b\104\uffff"+
        "\1\u0097\1\u009c\1\u009a\10\uffff\1\44\5\uffff\1\53\35\uffff\1\127"+
        "\5\uffff\1\140\2\uffff\1\124\14\uffff\1\157\1\uffff\1\161\1\uffff"+
        "\1\155\27\uffff\1\u008a\11\uffff\1\36\2\uffff\1\40\1\uffff\1\43"+
        "\2\uffff\1\45\40\uffff\1\107\1\uffff\1\116\20\uffff\1\143\3\uffff"+
        "\1\150\1\uffff\1\152\14\uffff\1\u0082\11\uffff\1\u0086\32\uffff"+
        "\1\54\13\uffff\1\57\1\56\3\uffff\1\101\2\uffff\1\104\1\uffff\1\106"+
        "\1\105\5\uffff\1\115\1\uffff\1\120\1\121\6\uffff\1\137\5\uffff\1"+
        "\142\1\145\1\uffff\1\144\2\uffff\1\154\2\uffff\1\160\2\uffff\1\164"+
        "\22\uffff\1\u0089\1\u0088\11\uffff\1\u0093\1\uffff\1\u0095\1\35"+
        "\1\41\3\uffff\1\37\2\uffff\1\51\7\uffff\1\70\1\uffff\1\60\15\uffff"+
        "\1\122\2\uffff\1\130\4\uffff\1\133\3\uffff\1\146\1\147\2\uffff\1"+
        "\163\1\162\3\uffff\1\167\11\uffff\1\170\6\uffff\1\u0087\2\uffff"+
        "\1\u008d\1\u0090\3\uffff\1\u0096\1\46\2\uffff\1\50\3\uffff\1\62"+
        "\2\uffff\1\64\1\67\6\uffff\1\100\1\102\2\uffff\1\111\1\113\1\uffff"+
        "\1\110\1\uffff\1\123\2\uffff\1\134\2\uffff\1\141\1\125\3\uffff\1"+
        "\156\1\uffff\1\165\2\uffff\1\175\4\uffff\1\172\1\uffff\1\u0085\1"+
        "\u0084\4\uffff\1\u008f\1\u008e\1\u0092\1\u0091\1\uffff\1\42\1\uffff"+
        "\1\52\2\uffff\1\61\1\63\1\uffff\1\55\3\uffff\1\75\2\uffff\1\114"+
        "\1\117\1\uffff\1\131\1\135\2\uffff\1\153\1\151\1\166\3\uffff\1\174"+
        "\1\uffff\1\176\1\173\2\uffff\1\u008c\1\uffff\1\u0094\2\uffff\1\65"+
        "\2\uffff\1\103\1\77\1\uffff\1\74\4\uffff\1\u0081\1\uffff\1\u0080"+
        "\11\uffff\1\112\1\132\1\136\1\126\2\uffff\1\u0083\2\uffff\1\66\2"+
        "\uffff\1\76\1\171\5\uffff\1\177\1\u008b\1\uffff\1\71\1\uffff\1\72"+
        "\1\47\4\uffff\1\73";
    static final String DFA22_specialS =
        "\55\uffff\1\1\130\uffff\1\0\u0222\uffff}>";
    static final String[] DFA22_transitionS = {
            "\2\63\1\uffff\2\63\22\uffff\1\63\1\2\1\55\1\uffff\1\25\1\14"+
            "\1\5\1\60\1\20\1\21\1\12\1\7\1\17\1\10\1\16\1\13\12\61\1\23"+
            "\1\15\1\3\1\1\1\4\1\22\1\24\1\26\1\27\1\30\1\31\1\32\1\33\1"+
            "\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\1\45\1\46\1\47\1"+
            "\50\1\51\1\52\1\53\1\54\1\57\2\62\1\uffff\1\56\2\uffff\1\62"+
            "\1\uffff\1\26\1\27\1\30\1\31\1\32\1\33\1\34\1\35\1\36\1\37\1"+
            "\40\1\41\1\42\1\43\1\44\1\45\1\46\1\47\1\50\1\51\1\52\1\53\1"+
            "\54\1\57\2\62\1\uffff\1\6\1\uffff\1\11",
            "\1\64",
            "",
            "\1\70\1\67\1\66",
            "\1\72\1\73",
            "",
            "\1\75",
            "",
            "\1\63",
            "",
            "",
            "\1\63",
            "",
            "",
            "\12\102",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\104\1\uffff\1\103\1\uffff\1\112\5\uffff\1\105\1\uffff\1"+
            "\107\4\uffff\1\111\1\106\1\110\14\uffff\1\104\1\uffff\1\103"+
            "\1\uffff\1\112\5\uffff\1\105\1\uffff\1\107\4\uffff\1\111\1\106"+
            "\1\110",
            "\1\113\23\uffff\1\114\13\uffff\1\113\23\uffff\1\114",
            "\1\121\6\uffff\1\120\6\uffff\1\115\2\uffff\1\116\2\uffff\1"+
            "\117\13\uffff\1\121\6\uffff\1\120\6\uffff\1\115\2\uffff\1\116"+
            "\2\uffff\1\117",
            "\1\125\3\uffff\1\123\3\uffff\1\122\10\uffff\1\124\16\uffff"+
            "\1\125\3\uffff\1\123\3\uffff\1\122\10\uffff\1\124",
            "\1\127\12\uffff\1\126\1\uffff\1\132\4\uffff\1\131\4\uffff\1"+
            "\130\10\uffff\1\127\12\uffff\1\126\1\uffff\1\132\4\uffff\1\131"+
            "\4\uffff\1\130",
            "\1\133\15\uffff\1\134\2\uffff\1\135\16\uffff\1\133\15\uffff"+
            "\1\134\2\uffff\1\135",
            "\1\136\5\uffff\1\137\31\uffff\1\136\5\uffff\1\137",
            "\1\140\37\uffff\1\140",
            "\1\144\1\143\5\uffff\1\145\1\141\4\uffff\1\142\22\uffff\1\144"+
            "\1\143\5\uffff\1\145\1\141\4\uffff\1\142",
            "\1\146\37\uffff\1\146",
            "\1\147\37\uffff\1\147",
            "\1\151\3\uffff\1\150\33\uffff\1\151\3\uffff\1\150",
            "\1\152\37\uffff\1\152",
            "\1\155\15\uffff\1\153\5\uffff\1\154\13\uffff\1\155\15\uffff"+
            "\1\153\5\uffff\1\154",
            "\1\161\7\uffff\1\157\3\uffff\1\160\2\uffff\1\156\20\uffff\1"+
            "\161\7\uffff\1\157\3\uffff\1\160\2\uffff\1\156",
            "\1\162\5\uffff\1\163\31\uffff\1\162\5\uffff\1\163",
            "\1\164\37\uffff\1\164",
            "\1\167\3\uffff\1\166\11\uffff\1\165\21\uffff\1\167\3\uffff"+
            "\1\166\11\uffff\1\165",
            "\1\172\1\uffff\1\171\1\uffff\1\170\33\uffff\1\172\1\uffff\1"+
            "\171\1\uffff\1\170",
            "\1\177\3\uffff\1\175\2\uffff\1\174\6\uffff\1\176\2\uffff\1"+
            "\173\16\uffff\1\177\3\uffff\1\175\2\uffff\1\174\6\uffff\1\176"+
            "\2\uffff\1\173",
            "\1\u0081\1\uffff\1\u0080\2\uffff\1\u0082\32\uffff\1\u0081\1"+
            "\uffff\1\u0080\2\uffff\1\u0082",
            "\1\u0083\7\uffff\1\u0084\27\uffff\1\u0083\7\uffff\1\u0084",
            "\1\u0085\37\uffff\1\u0085",
            "\42\60\1\u0087\15\60\12\u0086\7\60\32\u0086\4\60\1\u0086\1"+
            "\60\32\u0086\uff85\60",
            "",
            "\1\u0088",
            "",
            "\1\102\1\uffff\12\61\13\uffff\1\102\37\uffff\1\102",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\u008a\37\uffff\1\u008a",
            "\1\u008b\37\uffff\1\u008b",
            "\1\u008d\7\uffff\1\u008c\27\uffff\1\u008d\7\uffff\1\u008c",
            "\1\u008e\37\uffff\1\u008e",
            "\1\u0090\2\uffff\1\u008f\34\uffff\1\u0090\2\uffff\1\u008f",
            "\1\u0091\37\uffff\1\u0091",
            "\1\62\15\uffff\12\62\7\uffff\2\62\1\u0093\27\62\4\uffff\1\62"+
            "\1\uffff\2\62\1\u0093\27\62",
            "\1\u0094\37\uffff\1\u0094",
            "\1\u0095\1\u0097\14\uffff\1\u0096\21\uffff\1\u0095\1\u0097"+
            "\14\uffff\1\u0096",
            "\1\62\15\uffff\12\62\7\uffff\23\62\1\u0099\6\62\4\uffff\1\62"+
            "\1\uffff\23\62\1\u0099\6\62",
            "\1\u009b\1\u009c\1\u009a\35\uffff\1\u009b\1\u009c\1\u009a",
            "\1\u009d\11\uffff\1\u009e\25\uffff\1\u009d\11\uffff\1\u009e",
            "\1\u009f\37\uffff\1\u009f",
            "\1\u00a0\37\uffff\1\u00a0",
            "\1\u00a1\37\uffff\1\u00a1",
            "\1\u00a2\37\uffff\1\u00a2",
            "\1\u00a3\5\uffff\1\u00a5\6\uffff\1\u00a4\1\u00a6\21\uffff\1"+
            "\u00a3\5\uffff\1\u00a5\6\uffff\1\u00a4\1\u00a6",
            "\1\u00a7\37\uffff\1\u00a7",
            "\1\u00a8\37\uffff\1\u00a8",
            "\1\u00a9\37\uffff\1\u00a9",
            "\1\u00aa\37\uffff\1\u00aa",
            "\1\u00ab\5\uffff\1\u00ac\6\uffff\1\u00ad\22\uffff\1\u00ab\5"+
            "\uffff\1\u00ac\6\uffff\1\u00ad",
            "\1\u00ae\37\uffff\1\u00ae",
            "\1\u00af\37\uffff\1\u00af",
            "\1\u00b0\37\uffff\1\u00b0",
            "\1\u00b1\37\uffff\1\u00b1",
            "\1\u00b2\37\uffff\1\u00b2",
            "\1\u00b3\37\uffff\1\u00b3",
            "\1\u00b4\37\uffff\1\u00b4",
            "\1\u00b5\37\uffff\1\u00b5",
            "\1\62\15\uffff\12\62\7\uffff\3\62\1\u00b8\4\62\1\u00b7\4\62"+
            "\1\u00bb\4\62\1\u00b9\1\u00ba\6\62\4\uffff\1\62\1\uffff\3\62"+
            "\1\u00b8\4\62\1\u00b7\4\62\1\u00bb\4\62\1\u00b9\1\u00ba\6\62",
            "\1\62\15\uffff\12\62\7\uffff\15\62\1\u00bd\14\62\4\uffff\1"+
            "\62\1\uffff\15\62\1\u00bd\14\62",
            "\1\u00be\37\uffff\1\u00be",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\u00c0\37\uffff\1\u00c0",
            "\1\u00c1\37\uffff\1\u00c1",
            "\1\u00c2\37\uffff\1\u00c2",
            "\1\u00c3\1\uffff\1\u00c4\35\uffff\1\u00c3\1\uffff\1\u00c4",
            "\1\u00c5\37\uffff\1\u00c5",
            "\1\u00c7\3\uffff\1\u00c6\33\uffff\1\u00c7\3\uffff\1\u00c6",
            "\1\u00c8\37\uffff\1\u00c8",
            "\1\u00c9\37\uffff\1\u00c9",
            "\1\u00ca\37\uffff\1\u00ca",
            "\1\u00cb\37\uffff\1\u00cb",
            "\1\62\15\uffff\12\62\7\uffff\13\62\1\u00cd\16\62\4\uffff\1"+
            "\62\1\uffff\13\62\1\u00cd\16\62",
            "\1\62\15\uffff\12\62\7\uffff\3\62\1\u00cf\26\62\4\uffff\1\62"+
            "\1\uffff\3\62\1\u00cf\26\62",
            "\1\62\15\uffff\12\62\7\uffff\5\62\1\u00d1\24\62\4\uffff\1\62"+
            "\1\uffff\5\62\1\u00d1\24\62",
            "\1\u00d2\37\uffff\1\u00d2",
            "\1\u00d4\7\uffff\1\u00d3\27\uffff\1\u00d4\7\uffff\1\u00d3",
            "\1\u00d5\37\uffff\1\u00d5",
            "\1\u00d6\12\uffff\1\u00d7\24\uffff\1\u00d6\12\uffff\1\u00d7",
            "\1\u00d8\1\u00dd\1\uffff\1\u00de\2\uffff\1\u00db\1\uffff\1"+
            "\u00d9\1\uffff\1\u00dc\2\uffff\1\u00da\22\uffff\1\u00d8\1\u00dd"+
            "\1\uffff\1\u00de\2\uffff\1\u00db\1\uffff\1\u00d9\1\uffff\1\u00dc"+
            "\2\uffff\1\u00da",
            "\1\u00df\37\uffff\1\u00df",
            "\1\u00e0\7\uffff\1\u00e1\27\uffff\1\u00e0\7\uffff\1\u00e1",
            "\1\u00e2\37\uffff\1\u00e2",
            "\1\u00e3\37\uffff\1\u00e3",
            "\1\u00e4\7\uffff\1\u00e5\27\uffff\1\u00e4\7\uffff\1\u00e5",
            "\1\u00e6\37\uffff\1\u00e6",
            "\1\u00e7\37\uffff\1\u00e7",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\u00e9\37\uffff\1\u00e9",
            "\1\u00ea\37\uffff\1\u00ea",
            "\1\u00eb\37\uffff\1\u00eb",
            "\1\u00ec\37\uffff\1\u00ec",
            "\1\u00ee\10\uffff\1\u00ed\26\uffff\1\u00ee\10\uffff\1\u00ed",
            "\1\u00f0\14\uffff\1\u00ef\22\uffff\1\u00f0\14\uffff\1\u00ef",
            "\1\u00f1\37\uffff\1\u00f1",
            "\42\60\1\u0087\15\60\12\u0086\7\60\32\u0086\4\60\1\u0086\1"+
            "\60\32\u0086\uff85\60",
            "",
            "",
            "",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\u00f3\37\uffff\1\u00f3",
            "\1\u00f4\37\uffff\1\u00f4",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\u00f6\37\uffff\1\u00f6",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\u00f8\37\uffff\1\u00f8",
            "\1\u00f9\37\uffff\1\u00f9",
            "",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\u00fb\37\uffff\1\u00fb",
            "\1\u00fc\37\uffff\1\u00fc",
            "\1\u00fd\37\uffff\1\u00fd",
            "\1\u00fe\37\uffff\1\u00fe",
            "",
            "\1\u00ff\37\uffff\1\u00ff",
            "\1\u0101\14\uffff\1\u0100\22\uffff\1\u0101\14\uffff\1\u0100",
            "\1\u0103\10\uffff\1\u0102\26\uffff\1\u0103\10\uffff\1\u0102",
            "\1\u0104\37\uffff\1\u0104",
            "\1\u0105\37\uffff\1\u0105",
            "\1\u0106\37\uffff\1\u0106",
            "\1\u0107\37\uffff\1\u0107",
            "\1\u0108\37\uffff\1\u0108",
            "\1\u0109\1\uffff\1\u010b\16\uffff\1\u010a\16\uffff\1\u0109"+
            "\1\uffff\1\u010b\16\uffff\1\u010a",
            "\1\u010c\37\uffff\1\u010c",
            "\1\u010e\3\uffff\1\u010d\33\uffff\1\u010e\3\uffff\1\u010d",
            "\1\u010f\37\uffff\1\u010f",
            "\1\u0110\37\uffff\1\u0110",
            "\1\u0111\37\uffff\1\u0111",
            "\1\u0112\37\uffff\1\u0112",
            "\1\u0113\37\uffff\1\u0113",
            "\1\u0114\37\uffff\1\u0114",
            "\1\u0115\37\uffff\1\u0115",
            "\1\u0117\6\uffff\1\u0116\30\uffff\1\u0117\6\uffff\1\u0116",
            "\1\u0118\37\uffff\1\u0118",
            "\1\u0119\37\uffff\1\u0119",
            "\1\u011a\37\uffff\1\u011a",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\u011c\37\uffff\1\u011c",
            "\1\62\15\uffff\12\62\7\uffff\4\62\1\u011e\25\62\4\uffff\1\62"+
            "\1\uffff\4\62\1\u011e\25\62",
            "\1\u011f\37\uffff\1\u011f",
            "\1\u0120\37\uffff\1\u0120",
            "\1\u0121\37\uffff\1\u0121",
            "\1\u0122\37\uffff\1\u0122",
            "",
            "\1\u0123\37\uffff\1\u0123",
            "\1\u0124\37\uffff\1\u0124",
            "\1\u0125\16\uffff\1\u0126\20\uffff\1\u0125\16\uffff\1\u0126",
            "\1\u0128\11\uffff\1\u0127\25\uffff\1\u0128\11\uffff\1\u0127",
            "\1\u0129\37\uffff\1\u0129",
            "",
            "\1\u012a\37\uffff\1\u012a",
            "\1\u012b\37\uffff\1\u012b",
            "",
            "\1\u012c\37\uffff\1\u012c",
            "\1\u012d\37\uffff\1\u012d",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\u012f\37\uffff\1\u012f",
            "\1\u0130\37\uffff\1\u0130",
            "\1\u0131\37\uffff\1\u0131",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\u0133\37\uffff\1\u0133",
            "\1\62\15\uffff\12\62\7\uffff\15\62\1\u0135\14\62\4\uffff\1"+
            "\62\1\uffff\15\62\1\u0135\14\62",
            "\1\u0136\37\uffff\1\u0136",
            "\1\u0137\37\uffff\1\u0137",
            "\1\u0138\37\uffff\1\u0138",
            "",
            "\1\u0139\37\uffff\1\u0139",
            "",
            "\1\u013a\37\uffff\1\u013a",
            "",
            "\1\u013b\37\uffff\1\u013b",
            "\1\u013c\37\uffff\1\u013c",
            "\1\u013d\37\uffff\1\u013d",
            "\1\u013e\37\uffff\1\u013e",
            "\1\u013f\37\uffff\1\u013f",
            "\1\u0140\37\uffff\1\u0140",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\u0142\37\uffff\1\u0142",
            "\1\u0143\37\uffff\1\u0143",
            "\1\u0144\37\uffff\1\u0144",
            "\1\u0145\37\uffff\1\u0145",
            "\1\u0146\37\uffff\1\u0146",
            "\1\u0147\37\uffff\1\u0147",
            "\1\u0148\37\uffff\1\u0148",
            "\1\u0149\37\uffff\1\u0149",
            "\1\u014a\37\uffff\1\u014a",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\u014c\37\uffff\1\u014c",
            "\1\u014d\37\uffff\1\u014d",
            "\1\u014e\37\uffff\1\u014e",
            "\1\u014f\37\uffff\1\u014f",
            "\1\u0150\37\uffff\1\u0150",
            "\1\u0151\37\uffff\1\u0151",
            "",
            "\1\u0152\37\uffff\1\u0152",
            "\1\u0153\37\uffff\1\u0153",
            "\1\u0155\1\uffff\1\u0154\35\uffff\1\u0155\1\uffff\1\u0154",
            "\1\u0156\37\uffff\1\u0156",
            "\1\u0157\37\uffff\1\u0157",
            "\1\u0158\37\uffff\1\u0158",
            "\1\u0159\37\uffff\1\u0159",
            "\1\u015a\37\uffff\1\u015a",
            "\1\u015c\3\uffff\1\u015b\33\uffff\1\u015c\3\uffff\1\u015b",
            "",
            "\1\u015d\37\uffff\1\u015d",
            "\1\u015e\37\uffff\1\u015e",
            "",
            "\1\u015f\37\uffff\1\u015f",
            "",
            "\1\u0160\37\uffff\1\u0160",
            "\1\u0161\37\uffff\1\u0161",
            "",
            "\1\u0162\37\uffff\1\u0162",
            "\1\u0163\37\uffff\1\u0163",
            "\1\u0164\37\uffff\1\u0164",
            "\1\u0165\37\uffff\1\u0165",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\u0167\37\uffff\1\u0167",
            "\1\u0168\37\uffff\1\u0168",
            "\1\u0169\37\uffff\1\u0169",
            "\1\u016a\37\uffff\1\u016a",
            "\1\u016b\3\uffff\1\u016c\33\uffff\1\u016b\3\uffff\1\u016c",
            "\1\u016d\37\uffff\1\u016d",
            "\1\u016e\37\uffff\1\u016e",
            "\1\u016f\37\uffff\1\u016f",
            "\1\u0170\37\uffff\1\u0170",
            "\1\u0171\37\uffff\1\u0171",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\u0174\37\uffff\1\u0174",
            "\1\u0175\37\uffff\1\u0175",
            "\1\u0176\37\uffff\1\u0176",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\u0178\37\uffff\1\u0178",
            "\1\u0179\37\uffff\1\u0179",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\u017b\37\uffff\1\u017b",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\u017e\37\uffff\1\u017e",
            "\1\u017f\37\uffff\1\u017f",
            "\1\u0180\37\uffff\1\u0180",
            "\1\u0181\37\uffff\1\u0181",
            "\1\u0182\37\uffff\1\u0182",
            "",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "",
            "\1\u0184\37\uffff\1\u0184",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\u0187\37\uffff\1\u0187",
            "\1\u0188\37\uffff\1\u0188",
            "\1\u0189\37\uffff\1\u0189",
            "\1\u018a\37\uffff\1\u018a",
            "\1\u018b\37\uffff\1\u018b",
            "\1\u018c\37\uffff\1\u018c",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\u018e\37\uffff\1\u018e",
            "\1\u018f\37\uffff\1\u018f",
            "\1\u0190\37\uffff\1\u0190",
            "\1\u0191\37\uffff\1\u0191",
            "\1\u0192\37\uffff\1\u0192",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\u0195\37\uffff\1\u0195",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "",
            "\1\u0197\37\uffff\1\u0197",
            "",
            "\1\u0198\37\uffff\1\u0198",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\u019a\37\uffff\1\u019a",
            "\1\u019b\37\uffff\1\u019b",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\u019d\37\uffff\1\u019d",
            "\1\u019e\37\uffff\1\u019e",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\u01a0\37\uffff\1\u01a0",
            "\1\u01a1\37\uffff\1\u01a1",
            "\1\u01a2\37\uffff\1\u01a2",
            "\1\u01a3\37\uffff\1\u01a3",
            "",
            "\1\u01a4\37\uffff\1\u01a4",
            "\1\u01a5\37\uffff\1\u01a5",
            "\1\u01a6\37\uffff\1\u01a6",
            "\1\u01a7\37\uffff\1\u01a7",
            "\1\u01a9\7\uffff\1\u01a8\27\uffff\1\u01a9\7\uffff\1\u01a8",
            "\1\u01aa\37\uffff\1\u01aa",
            "\1\u01ab\37\uffff\1\u01ab",
            "\1\u01ac\37\uffff\1\u01ac",
            "\1\u01ad\37\uffff\1\u01ad",
            "",
            "\1\u01ae\37\uffff\1\u01ae",
            "\1\u01af\37\uffff\1\u01af",
            "\1\u01b0\37\uffff\1\u01b0",
            "\1\u01b1\37\uffff\1\u01b1",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\62\15\uffff\12\62\7\uffff\16\62\1\u01b4\13\62\4\uffff\1"+
            "\62\1\uffff\16\62\1\u01b4\13\62",
            "\1\u01b5\37\uffff\1\u01b5",
            "\1\u01b6\37\uffff\1\u01b6",
            "\1\u01b7\37\uffff\1\u01b7",
            "\1\u01b8\37\uffff\1\u01b8",
            "\1\u01b9\37\uffff\1\u01b9",
            "\1\u01ba\37\uffff\1\u01ba",
            "\1\u01bb\37\uffff\1\u01bb",
            "\1\u01bc\37\uffff\1\u01bc",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\u01be\37\uffff\1\u01be",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\u01c2\37\uffff\1\u01c2",
            "\1\u01c3\37\uffff\1\u01c3",
            "\1\u01c4\37\uffff\1\u01c4",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\u01c6\37\uffff\1\u01c6",
            "\1\u01c7\37\uffff\1\u01c7",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "",
            "\1\u01c9\37\uffff\1\u01c9",
            "\1\u01ca\37\uffff\1\u01ca",
            "\1\u01cb\37\uffff\1\u01cb",
            "\1\u01cc\37\uffff\1\u01cc",
            "\1\u01cd\37\uffff\1\u01cd",
            "\1\u01ce\37\uffff\1\u01ce",
            "\1\u01cf\37\uffff\1\u01cf",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\u01d1\37\uffff\1\u01d1",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\u01d3\37\uffff\1\u01d3",
            "",
            "",
            "\1\u01d4\37\uffff\1\u01d4",
            "\1\u01d5\37\uffff\1\u01d5",
            "\1\u01d6\37\uffff\1\u01d6",
            "",
            "\1\u01d7\37\uffff\1\u01d7",
            "\1\u01d8\37\uffff\1\u01d8",
            "",
            "\1\u01d9\37\uffff\1\u01d9",
            "",
            "",
            "\1\u01da\37\uffff\1\u01da",
            "\1\u01db\37\uffff\1\u01db",
            "\1\u01dc\37\uffff\1\u01dc",
            "\1\u01dd\37\uffff\1\u01dd",
            "\1\u01de\37\uffff\1\u01de",
            "",
            "\1\u01df\37\uffff\1\u01df",
            "",
            "",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\u01e1\37\uffff\1\u01e1",
            "\1\u01e2\37\uffff\1\u01e2",
            "\1\62\15\uffff\12\62\7\uffff\4\62\1\u01e4\25\62\4\uffff\1\62"+
            "\1\uffff\4\62\1\u01e4\25\62",
            "\1\u01e5\37\uffff\1\u01e5",
            "\1\u01e6\37\uffff\1\u01e6",
            "",
            "\1\u01e7\37\uffff\1\u01e7",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\u01e9\37\uffff\1\u01e9",
            "\1\u01ea\37\uffff\1\u01ea",
            "\1\u01eb\37\uffff\1\u01eb",
            "",
            "",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\u01ee\37\uffff\1\u01ee",
            "",
            "\1\u01ef\37\uffff\1\u01ef",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\u01f2\37\uffff\1\u01f2",
            "",
            "\1\u01f3\37\uffff\1\u01f3",
            "\1\u01f4\37\uffff\1\u01f4",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\u01f6\37\uffff\1\u01f6",
            "\1\u01f7\37\uffff\1\u01f7",
            "\1\u01f8\37\uffff\1\u01f8",
            "\1\u01f9\37\uffff\1\u01f9",
            "\1\u01fa\37\uffff\1\u01fa",
            "\1\u01fb\37\uffff\1\u01fb",
            "\1\u01fc\37\uffff\1\u01fc",
            "\1\u01fd\37\uffff\1\u01fd",
            "\1\u01fe\37\uffff\1\u01fe",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\u0200\37\uffff\1\u0200",
            "\1\u0201\37\uffff\1\u0201",
            "\1\u0202\37\uffff\1\u0202",
            "\1\u0203\37\uffff\1\u0203",
            "\1\u0204\37\uffff\1\u0204",
            "",
            "",
            "\1\u0205\37\uffff\1\u0205",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\u0207\37\uffff\1\u0207",
            "\1\u0208\37\uffff\1\u0208",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\u020b\37\uffff\1\u020b",
            "\1\u020c\37\uffff\1\u020c",
            "\1\u020d\37\uffff\1\u020d",
            "",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "",
            "",
            "",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\u0210\37\uffff\1\u0210",
            "\1\u0211\37\uffff\1\u0211",
            "",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\u0213\37\uffff\1\u0213",
            "",
            "\1\u0214\37\uffff\1\u0214",
            "\1\u0215\37\uffff\1\u0215",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\u0217\37\uffff\1\u0217",
            "\1\u0218\37\uffff\1\u0218",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "",
            "\1\u021b\37\uffff\1\u021b",
            "",
            "\1\u021c\37\uffff\1\u021c",
            "\1\u021d\37\uffff\1\u021d",
            "\1\u021f\3\uffff\1\u021e\33\uffff\1\u021f\3\uffff\1\u021e",
            "\1\u0220\37\uffff\1\u0220",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\u0223\37\uffff\1\u0223",
            "\1\u0224\37\uffff\1\u0224",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\u0227\37\uffff\1\u0227",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\u0229\37\uffff\1\u0229",
            "",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\u022b\37\uffff\1\u022b",
            "",
            "\1\u022c\37\uffff\1\u022c",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\u022e\37\uffff\1\u022e",
            "\1\u022f\37\uffff\1\u022f",
            "",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\u0232\37\uffff\1\u0232",
            "",
            "",
            "\1\u0233\37\uffff\1\u0233",
            "\1\u0234\37\uffff\1\u0234",
            "",
            "",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\u0236\37\uffff\1\u0236",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "",
            "\1\u0238\37\uffff\1\u0238",
            "\1\u0239\37\uffff\1\u0239",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\u023b\37\uffff\1\u023b",
            "\1\u023c\37\uffff\1\u023c",
            "\1\u023d\37\uffff\1\u023d",
            "\1\u023e\37\uffff\1\u023e",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\u0240\37\uffff\1\u0240",
            "",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\u0243\37\uffff\1\u0243",
            "\1\u0244\37\uffff\1\u0244",
            "\1\u0245\37\uffff\1\u0245",
            "\1\u0246\37\uffff\1\u0246",
            "",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "",
            "",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\u024b\37\uffff\1\u024b",
            "",
            "",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\u024d\37\uffff\1\u024d",
            "",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\u024f\37\uffff\1\u024f",
            "\1\u0250\37\uffff\1\u0250",
            "",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "",
            "",
            "\1\u0253",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\u0255\37\uffff\1\u0255",
            "\1\u0256\37\uffff\1\u0256",
            "\1\u0257\37\uffff\1\u0257",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "",
            "",
            "\1\u0259\37\uffff\1\u0259",
            "\1\u025a\37\uffff\1\u025a",
            "",
            "",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "",
            "\1\u025d\37\uffff\1\u025d",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\u0260\37\uffff\1\u0260",
            "",
            "",
            "\1\u0261\37\uffff\1\u0261",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "",
            "\1\u0265\37\uffff\1\u0265",
            "\1\u0266\37\uffff\1\u0266",
            "",
            "\1\u0267\37\uffff\1\u0267",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\u0269\37\uffff\1\u0269",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "",
            "",
            "\1\u026c\37\uffff\1\u026c",
            "\1\u026d\37\uffff\1\u026d",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\u026f\37\uffff\1\u026f",
            "",
            "",
            "",
            "",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "",
            "\1\u0271\37\uffff\1\u0271",
            "",
            "\1\u0272\37\uffff\1\u0272",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "",
            "",
            "\1\u0275\17\uffff\1\u0274\17\uffff\1\u0275\17\uffff\1\u0274",
            "",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\u0278\37\uffff\1\u0278",
            "",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\u027a\37\uffff\1\u027a",
            "",
            "",
            "\1\u027b\37\uffff\1\u027b",
            "",
            "",
            "\1\u027c\37\uffff\1\u027c",
            "\1\u027d\37\uffff\1\u027d",
            "",
            "",
            "",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\u027f\37\uffff\1\u027f",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "",
            "\1\u0281\37\uffff\1\u0281",
            "",
            "",
            "\1\u0282\37\uffff\1\u0282",
            "\1\u0283\37\uffff\1\u0283",
            "",
            "\1\u0284\37\uffff\1\u0284",
            "",
            "\1\u0285\37\uffff\1\u0285",
            "\1\u0286\37\uffff\1\u0286",
            "",
            "\1\u0287\37\uffff\1\u0287",
            "\1\u0288\37\uffff\1\u0288",
            "",
            "",
            "\1\u0289\37\uffff\1\u0289",
            "",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "",
            "\1\u028e\37\uffff\1\u028e",
            "",
            "\1\u028f\37\uffff\1\u028f",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\u0291\37\uffff\1\u0291",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\u0292\37\uffff\1\u0292",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\u0294\37\uffff\1\u0294",
            "\1\u0295\37\uffff\1\u0295",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "",
            "",
            "",
            "",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\u0298\37\uffff\1\u0298",
            "",
            "\1\u0299\37\uffff\1\u0299",
            "\1\u029a\37\uffff\1\u029a",
            "",
            "\1\u029b\37\uffff\1\u029b",
            "\1\u029c\37\uffff\1\u029c",
            "",
            "",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "\1\u029f\37\uffff\1\u029f",
            "\1\62\15\uffff\12\62\7\uffff\22\62\1\u02a1\7\62\4\uffff\1\62"+
            "\1\uffff\22\62\1\u02a1\7\62",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "",
            "",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
            "",
            "\1\u02a4\37\uffff\1\u02a4",
            "",
            "",
            "\1\u02a5\37\uffff\1\u02a5",
            "\1\u02a6\37\uffff\1\u02a6",
            "\1\u02a7\37\uffff\1\u02a7",
            "\1\62\15\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32"+
            "\62",
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
            return "1:1: Tokens : ( EQUALS | EQUALS2 | NOT_EQUALS | NOT_EQUALS2 | LESS | LESS_OR_EQ | GREATER | GREATER_OR_EQ | SHIFT_LEFT | SHIFT_RIGHT | AMPERSAND | PIPE | DOUBLE_PIPE | PLUS | MINUS | TILDA | ASTERISK | SLASH | PERCENT | SEMI | DOT | COMMA | LPAREN | RPAREN | QUESTION | COLON | AT | DOLLAR | ABORT | ADD | AFTER | ALL | ALTER | ANALYZE | AND | AS | ASC | ATTACH | AUTOINCREMENT | BEFORE | BEGIN | BETWEEN | BY | BYTE | CASCADE | CASE | CAST | CHECK | COLLATE | COLUMN | META_COMMENT | COMMIT | CONFLICT | CONSTRAINT | CREATE | CROSS | CURRENT_TIME | CURRENT_DATE | CURRENT_TIMESTAMP | DATABASE | DEFAULT | DEFERRABLE | DEFERRED | DELETE | DESC | DETACH | DISTINCT | DROP | EACH | ELSE | END | ESCAPE | EXCEPT | EXCLUSIVE | EXISTS | EXPLAIN | FAIL | FOR | FOREIGN | FROM | GLOB | GROUP | HAVING | IF | IGNORE | IMMEDIATE | IN | INDEX | INDEXED | INITIALLY | INNER | INSERT | INSTEAD | INTERSECT | INTO | IS | ISNULL | JOIN | KEY | LEFT | LIKE | LIMIT | MATCH | MAX | NATURAL | NOT | NOTNULL | NULL | OF | OFFSET | ON | ONLY | OR | ORDER | OUTER | PLAN | PRAGMA | PRIMARY | QUERY | RAISE | REFERENCES | REGEXP | REINDEX | RELEASE | RENAME | REPLACE | REPLICATION | RESTRICT | ROLLBACK | ROW | SAVEPOINT | SCHEMA | SELECT | SET | TABLE | TEMPORARY | THEN | TO | TRANSACTION | TRIGGER | UNION | UNIQUE | UPDATE | USING | VACUUM | VALUES | VIEW | VIRTUAL | WHEN | WHERE | ID | ESCAPE_SEQ | STRING | INTEGER | FLOAT | BLOB | WS );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA22_134 = input.LA(1);

                        s = -1;
                        if ( (LA22_134=='\"') ) {s = 135;}

                        else if ( ((LA22_134>='0' && LA22_134<='9')||(LA22_134>='A' && LA22_134<='Z')||LA22_134=='_'||(LA22_134>='a' && LA22_134<='z')) ) {s = 134;}

                        else if ( ((LA22_134>='\u0000' && LA22_134<='!')||(LA22_134>='#' && LA22_134<='/')||(LA22_134>=':' && LA22_134<='@')||(LA22_134>='[' && LA22_134<='^')||LA22_134=='`'||(LA22_134>='{' && LA22_134<='\uFFFF')) ) {s = 48;}

                        else s = 50;

                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA22_45 = input.LA(1);

                        s = -1;
                        if ( ((LA22_45>='\u0000' && LA22_45<='!')||(LA22_45>='#' && LA22_45<='/')||(LA22_45>=':' && LA22_45<='@')||(LA22_45>='[' && LA22_45<='^')||LA22_45=='`'||(LA22_45>='{' && LA22_45<='\uFFFF')) ) {s = 48;}

                        else if ( ((LA22_45>='0' && LA22_45<='9')||(LA22_45>='A' && LA22_45<='Z')||LA22_45=='_'||(LA22_45>='a' && LA22_45<='z')) ) {s = 134;}

                        else if ( (LA22_45=='\"') ) {s = 135;}

                        else s = 50;

                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 22, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

}