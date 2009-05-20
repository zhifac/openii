header {
//  Global header starts here, at the top of all generated files
package org.mitre.schemastore.porters.schemaImporters.ddl;

import java.util.*;
import org.mitre.schemastore.model.*;
import org.mitre.schemastore.porters.schemaImporters.*;

//  Global header ends here
}
options {
	language=Java;
}

{
//  Class preamble starts here - right before the class definition in the generated class file

//  Class preamble ends here
}class SqlSQL2Lexer extends Lexer;

options {
	importVocab= SqlSQL2Imp;
	exportVocab= SqlSQL2Lex;
	defaultErrorHandler= false;
	testLiterals= false;
	k= 2;
	caseSensitive= true;
	caseSensitiveLiterals= false;
	charVocabulary= '\0' .. '\377';
	filter= ANY_CHAR;
}

{
//  Class body inset starts here - at the top within the generated class body

//  Class body inset ends here
}
PERCENT :'%' ;

// inherited from grammar DmlSQL2Lexer
REGULAR_ID :( NATIONAL_CHAR_STRING_LIT {$setType(NATIONAL_CHAR_STRING_LIT);}
	| BIT_STRING_LIT {$setType(BIT_STRING_LIT);}
	| HEX_STRING_LIT {$setType(HEX_STRING_LIT);}
	)
	// REGULAR_ID
	| (SIMPLE_LETTER) (SIMPLE_LETTER | '_' | '0'..'9')* {$setType(testLiteralsTable(REGULAR_ID));}
;

// inherited from grammar DmlSQL2Lexer
EXACT_NUM_LIT :UNSIGNED_INTEGER
		( '.' (UNSIGNED_INTEGER)?
		|	{$setType(UNSIGNED_INTEGER);}
		) ( ('E' | 'e') ('+' | '-')? UNSIGNED_INTEGER {$setType(APPROXIMATE_NUM_LIT);} )?
	| '.' UNSIGNED_INTEGER ( ('E' | 'e') ('+' | '-')? UNSIGNED_INTEGER {$setType(APPROXIMATE_NUM_LIT);} )?
	| '.' 	{$setType(PERIOD);}
	| '.' '.' {$setType(DOUBLE_PERIOD);}
;

// inherited from grammar DmlSQL2Lexer
protected UNSIGNED_INTEGER :('0'..'9')+
;

// inherited from grammar DmlSQL2Lexer
protected NATIONAL_CHAR_STRING_LIT :('N' | 'n') ('\'' (options{greedy=true;}: ~('\'' | '\r' | '\n' ) | '\'' '\'' | NEWLINE)* '\'' (SEPARATOR)? )+
;

// inherited from grammar DmlSQL2Lexer
protected BIT_STRING_LIT :('B' | 'b') ('\'' ('0' | '1')* '\'' (SEPARATOR)? )+
;

// inherited from grammar DmlSQL2Lexer
protected HEX_STRING_LIT :('X' | 'x') ('\'' ('a'..'f' | 'A'..'F' | '0'..'9')* '\'' (SEPARATOR)? )+
;

// inherited from grammar DmlSQL2Lexer
CHAR_STRING :('\'' (options{greedy=true;}: ~('\'' | '\r' | '\n') | '\'' '\'' | NEWLINE)* '\'' (SEPARATOR)? )+
	| '\'' {$setType(QUOTE);}
;

// inherited from grammar DmlSQL2Lexer
DELIMITED_ID :'"' (~('"' | '\r' | '\n') | '"' '"')+ '"'
;

// inherited from grammar DmlSQL2Lexer
AMPERSAND :'&' ;

// inherited from grammar DmlSQL2Lexer
LEFT_PAREN :'(' ;

// inherited from grammar DmlSQL2Lexer
RIGHT_PAREN :')' ;

// inherited from grammar DmlSQL2Lexer
ASTERISK :'*' ;

// inherited from grammar DmlSQL2Lexer
PLUS_SIGN :'+'	;

// inherited from grammar DmlSQL2Lexer
COMMA :',' ;

// inherited from grammar DmlSQL2Lexer
SOLIDUS :'/' ;

// inherited from grammar DmlSQL2Lexer
COLON :':'
	| ':' (SIMPLE_LETTER | '0'..'9' | '.' | '_' | '#' | '$' | '&' | '%' | '@')+ {$setType(EMBDD_VARIABLE_NAME);}
;

// inherited from grammar DmlSQL2Lexer
SEMICOLON :';' ;

// inherited from grammar DmlSQL2Lexer
LESS_THAN_OP :'<' ('>' {$setType(NOT_EQUALS_OP);} | '=' {$setType(LESS_THAN_OR_EQUALS_OP);})?;

// inherited from grammar DmlSQL2Lexer
EQUALS_OP :'=' ;

// inherited from grammar DmlSQL2Lexer
GREATER_THAN_OP :'>' ('=' {$setType(GREATER_THAN_OR_EQUALS_OP);})?;

// inherited from grammar DmlSQL2Lexer
QUESTION_MARK :'?' ;

// inherited from grammar DmlSQL2Lexer
VERTICAL_BAR :'|' ('|' {$setType(CONCATENATION_OP);})?;

// inherited from grammar DmlSQL2Lexer
LEFT_BRACKET :'[' ;

// inherited from grammar DmlSQL2Lexer
RIGHT_BRACKET :']' ;

// inherited from grammar DmlSQL2Lexer
INTRODUCER :'_' (SEPARATOR {$setType(UNDERSCORE);})?;

// inherited from grammar DmlSQL2Lexer
protected SIMPLE_LETTER :'a'..'z' | 'A'..'Z' | '\177'..'\377'
;

// inherited from grammar DmlSQL2Lexer
protected SEPARATOR :'-' {$setType(MINUS_SIGN);}
	| LINE_COMMENT //{ $setType(ANTLR_USE_NAMESPACE(antlr)Token::SKIP); }
	| (SPACE | NEWLINE)+	//{ $setType(ANTLR_USE_NAMESPACE(antlr)Token::SKIP); }
;

// inherited from grammar DmlSQL2Lexer
LINE_COMMENT :"--"
		(~('\n'|'\r'))* ('\n'|'\r'('\n')?)
		{$setType(Token.SKIP); newline();}
	;

// inherited from grammar DmlSQL2Lexer
protected NEWLINE {newline();}
:( '\r' (options{greedy=true;}: '\n')? | '\n' )
;

// inherited from grammar DmlSQL2Lexer
protected SPACE :' ' | '\t'
;

// inherited from grammar DmlSQL2Lexer
protected ANY_CHAR :.
;

{
//  Class preamble starts here - right before the class definition in the generated class file

//  Class preamble ends here
}class SqlSQL2Parser extends Parser;

options {
	importVocab= SqlSQL2Lex;
	exportVocab= SqlSQL2;
	defaultErrorHandler= false;
	k= 2;
	buildAST= true;
	codeGenMakeSwitchThreshold=4;
	codeGenBitsetTestThreshold=8;
}

{
//  Class body inset starts here - at the top within the generated class body
protected SchemaBuilder builder = new SchemaBuilder();

public ArrayList<SchemaElement> getSchemaObjects()
{
    return builder.getSchemaObjects();
}


//  Class body inset ends here
}
sql_stmt :( sql_data_stmt
	| sql_schema_stmt
	| sql_transaction_stmt
	|
	( options {generateAmbigWarnings=false;}:
         // Keeping this order avoids the clash of the "set" statements
	 // due to the linear approximation of the lookahead
	    sql_session_stmt 	// LA(1) is surely "set"
	  | sql_connection_stmt
	)
	| sql_dyn_stmt
	| system_descriptor_stmt
	| get_diag_stmt
	| declare_cursor
	| temporary_table_decl
    ) SEMICOLON
;

sql_schema_stmt :sql_schema_def_stmt
	| sql_schema_manipulat_stmt
;

sql_schema_def_stmt :schema_def
	| table_def
	| view_def
	| grant_stmt
	| domain_def
	| char_set_def
	| collation_def
	| translation_def
	| assertion_def
    // | comment_def

;

sql_schema_manipulat_stmt :drop_schema_stmt
	| alter_table_stmt
	| drop_table_stmt
	| drop_view_stmt
	| revoke_stmt
	| alter_domain_stmt
	| drop_domain_stmt
	| drop_char_set_stmt
	| drop_collation_stmt
	| drop_translation_stmt
	| drop_assertion_stmt
;

sql_transaction_stmt :commit_stmt
	| rollback_stmt
	| set_constraints_mode_stmt
	| set_transaction_stmt
;

sql_connection_stmt :connect_stmt
	| disconnect_stmt
	| set_connection_stmt
;

sql_session_stmt :set_catalog_stmt
	| set_schema_stmt
	| set_names_stmt
	| set_session_author_id_stmt
	| set_local_time_zone_stmt
;

sql_dyn_stmt :prepare_stmt
//	| system_descriptor_stmt
	| deallocate_prepared_stmt
	| describe_stmt
	| execute_stmt
	| execute_immediate_stmt
	| allocate_cursor_stmt
	| fetch_stmt
	| open_stmt
	| close_stmt
;

schema_def :"create" "schema" schema_name_clause (schema_char_set_spec)? (schema_element)*
;

schema_name_clause :schema_name ("authorization" /*schema_author_id*/author_id)?
	| "authorization" /*schema_author_id*/author_id
;

schema_char_set_spec :"default" "character" "set" char_set_name
;

schema_element :table_def
	| view_def
	| grant_stmt
	| domain_def
	| assertion_def
	| char_set_def
	| collation_def
	| translation_def
    | rule_def

;

rule_def :"create" "rule" (~(SEMICOLON))* SEMICOLON
;

table_def :"create"
	  ( ("global" | "local") "temporary" "table" table_name { builder.addEntity( returnAST.toString() ); } table_element_list (on_commit_clause)?
	  | "table" table_name { builder.addEntity( returnAST.toString() ); System.out.println(returnAST.toString());} table_element_list
	  )
;

table_element_list :LEFT_PAREN table_element (COMMA table_element)* RIGHT_PAREN
;

table_element :table_constraint_def
	| column_def
;

on_commit_clause :"on" "commit" ("delete" | "preserve") "rows"
;

table_constraint_def :(constraint_name_def)? table_constraint (constraint_attributes)?
;

constraint_name_def :"constraint" constraint_name
;

constraint_attributes :constraint_check_time (options{greedy=true;}:("not")? "deferrable")?
	| ("not")? "deferrable" (constraint_check_time)?
;

constraint_check_time :"initially" ("deferred" | "immediate")
;

table_constraint :unique_constraint_def
	| ref_constraint_def
	| check_constraint_def
;

check_constraint_def :"check" LEFT_PAREN search_condition RIGHT_PAREN
;

unique_constraint_def :unique_spec (LEFT_PAREN /*unique_column_list*/column_name_list RIGHT_PAREN)? ( "using" "index" LEFT_PAREN (~RIGHT_PAREN)* RIGHT_PAREN)?
;

unique_spec :"unique"
	| "primary" "key"
;

ref_constraint_def :"foreign" "key" LEFT_PAREN /*referencing_columns*/column_name_list RIGHT_PAREN refs_spec
;

refs_spec :"references" refd_table_and_columns ( "match" ("full" | "partial") )? (ref_triggered_action)?
;

refd_table_and_columns :table_name { builder.addReferenceTo( returnAST.toString() ); } (LEFT_PAREN /*ref_column_list*/column_name_list RIGHT_PAREN)?
;

ref_triggered_action :"on" "update" ref_action ("on" "delete" ref_action)?
	| "on" "delete" ref_action ("on" "update" ref_action)?
;

ref_action :"cascade"
	| "set" "null"
	| "set" "default"
	| "no" "action"
;

column_def :column_name { builder.addAttributeToLastEntity( returnAST.toString() ); } (data_type | domain_name) (default_clause)? (column_constraint_def)* (collate_clause)?
;

default_clause :"default"
	  ( lit
	  | datetime_value_fct
	  | "user"
	  | "current_user"
	  | "session_user"
	  | "system_user"
	  | "null"
	  | "sysdate"
	  )
;

column_constraint_def :(constraint_name_def)? column_constraint (options{greedy=true;}:constraint_attributes)?
;

column_constraint :"not" "null"
    | "null"
	| unique_spec
	| refs_spec
	| check_constraint_def
;

view_def :"create" "view" table_name (LEFT_PAREN /*view_column_list*/column_name_list RIGHT_PAREN)? "as" query_exp
	  ("with" ("cascaded" | "local")? "check" "option")?
;

grant_stmt :"grant"
	  ( privileges "on" ("table")? table_name
	  | "usage" "on" object_name
	  )
	 "to" grantee (COMMA grantee)* ("with" "grant" "option")?
;

privileges :"all" "privileges"
	| action (COMMA action)*
;

action :"select"
	| "delete"
	| "insert" (LEFT_PAREN column_name_list RIGHT_PAREN)?
	| "update" (LEFT_PAREN column_name_list RIGHT_PAREN)?
	| "references" (LEFT_PAREN column_name_list RIGHT_PAREN)?
;

grantee :"public"
	| author_id
;

domain_def :"create" "domain" domain_name ("as")? data_type (default_clause)? (domain_constraint)* (collate_clause)?
;

domain_constraint :(constraint_name_def)? check_constraint_def (constraint_attributes)?
;

char_set_def :"create" "character" "set" char_set_name ("as")?
	  "get" /*existing_char_set_name*/char_set_name (collate_clause | limited_collation_def)?
;

limited_collation_def :"collation" "from" collation_source
;

collation_source :collating_sequence_def
	| translation_collation
;

collating_sequence_def :external_collation
	| collation_name/*schema_collation_name*/
	| "desc" LEFT_PAREN collation_name RIGHT_PAREN
	| "default"
;

external_collation :"external" LEFT_PAREN CHAR_STRING RIGHT_PAREN
;

translation_collation :"translation" translation_name ("then" "collation" collation_name)?
;

collation_def :"create" "collation" collation_name "for" /*char_set_spec*/char_set_name "from" collation_source ("no" "pad" | "pad" "space")?
;

translation_def :"create" "translation" translation_name
	"for" /*source_char_set_spec*/char_set_name
	"to"/*target_char_set_spec*/char_set_name
	"from" /*translation_source*/translation_spec
;

translation_spec :external_translation
	| "identity"
	| translation_name
;

external_translation :"external"  LEFT_PAREN CHAR_STRING RIGHT_PAREN
;

assertion_def :"create" "assertion" constraint_name assertion_check (constraint_attributes)?
;

assertion_check :"check" LEFT_PAREN search_condition RIGHT_PAREN
;

drop_behavior :"cascade"
	| "restrict"
;

drop_schema_stmt :"drop" "schema" qualified_name drop_behavior
;

alter_table_stmt :"alter" "table" table_name { System.out.println("Alter table statement for " +  returnAST.toString()); builder.setEntityTo( returnAST.toString()); } alter_table_action
;

alter_table_action :add_column_def
	| alter_column_def
	| drop_column_def
	| add_table_constraint_def
	| drop_table_constraint_def
;

add_column_def :"add" ("column")? column_def
;

alter_column_def :"alter" ("column")? column_name alter_column_action
;

alter_column_action :"set" default_clause
	| "drop" "default"
;

drop_column_def :"drop" ("column")? column_name drop_behavior
;

add_table_constraint_def :"add" table_constraint_def
;

drop_table_constraint_def :"drop" "constraint" constraint_name drop_behavior
;

drop_table_stmt :"drop" "table" table_name drop_behavior
;

drop_view_stmt :"drop" "view" table_name drop_behavior
;

revoke_stmt :"revoke" ("grant" "option" "for")?
	  ( privileges "on" ("table")? table_name
	  | "usage" "on" object_name
	  )
	"from" grantee (COMMA grantee)* drop_behavior
;

alter_domain_stmt :"alter" "domain" domain_name alter_domain_action
;

alter_domain_action :"set" default_clause
	| "drop" "default"
	| "add" domain_constraint
	| "drop" "constraint" constraint_name
;

drop_domain_stmt :"drop" "domain" domain_name drop_behavior
;

drop_char_set_stmt :"drop" "character" "set" char_set_name
;

drop_collation_stmt :"drop" "collation" collation_name
;

drop_translation_stmt :"drop" "translation" translation_name
;

drop_assertion_stmt :"drop" "assertion" constraint_name
;

commit_stmt :"commit" ("work")?
;

rollback_stmt :"rollback" ("work")?
;

set_constraints_mode_stmt :"set" "constraints" constraint_name_list ("deferred" | "immediate")
;

constraint_name_list :"all"
	| constraint_name (COMMA constraint_name)*
;

set_transaction_stmt :"set" "transaction" transaction_mode (COMMA transaction_mode)*
;

transaction_mode :"isolation" "level" level_of_isolation
	| ("read" "only" | "read" "write")
	| "diagnostics" "size" /*number_of_conditions*/simple_value_spec
;

level_of_isolation :"read" "uncommitted"
	| "read" "committed"
	| "repeatable" "read"
	| "serializable"
;

connect_stmt :"connect" "to" connection_target
;

connection_target :/*sql_server_name*/simple_value_spec	("as" /*connection_name*/simple_value_spec)?
	      ("user" /*user_name*/simple_value_spec)?
	| "default"
;

disconnect_stmt :"disconnect"
	  ( connection_object
	  | "all"
	  | "current"
	  )
;

set_connection_stmt :"set" "connection" connection_object
;

connection_object :"default"
	| /*connection_name*/simple_value_spec
;

set_catalog_stmt :"set" "catalog" value_spec
;

set_schema_stmt :"set" "schema" value_spec
;

set_names_stmt :"set" "names" value_spec
;

set_session_author_id_stmt :"set" "session" "authorization" value_spec
;

set_local_time_zone_stmt :"set" "time" "zone"  (interval_value_exp | "local")
;

system_descriptor_stmt :"allocate" "descriptor" descriptor_name ("with" "max" /*occurrences*/simple_value_spec)?
	| "deallocate" "descriptor" descriptor_name
	| "set" "descriptor" descriptor_name set_descriptor_information
	| "get" "descriptor" descriptor_name get_descriptor_information
;

set_descriptor_information :"count" EQUALS_OP simple_value_spec
      | "value" /*item_number*/simple_value_spec set_item_information (COMMA set_item_information)*
;

set_item_information :descriptor_item_name EQUALS_OP simple_value_spec
;

descriptor_item_name :"type"
	| "length"
	| "octet_length"
	| "returned_length"
	| "returned_octet_length"
	| "precision"
	| "scale"
	| "datetime_interval_code"
	| "datetime_interval_precision"
	| "nullable"
	| "indicator"
	| "data"
	| "name"
	| "unnamed"
	| "collation_catalog"
	| "collation_schema"
	| "collation_name"
	| "character_set_catalog"
	| "character_set_schema"
	| "character_set_name"
;

get_descriptor_information :simple_target_spec EQUALS_OP "count"
	| "value" /*item_number*/simple_value_spec get_item_information (COMMA get_item_information)*
;

get_item_information :simple_target_spec EQUALS_OP descriptor_item_name
;

prepare_stmt :"prepare" sql_stmt_name "from" sql_stmt_variable
;

sql_stmt_variable :parameter_name
	| EMBDD_VARIABLE_NAME
;

deallocate_prepared_stmt :"deallocate" "prepare" sql_stmt_name
;

describe_stmt :"describe" "input" sql_stmt_name using_descriptor
	| "describe" ("output")? sql_stmt_name using_descriptor
;

using_descriptor :("using" | "into") "sql" "descriptor" descriptor_name
;

execute_stmt :"execute" sql_stmt_name
	  (  ("into")=>/*result_using_clause*/using_clause (/*parameter_using_clause*/using_clause)?
	  |  (/*parameter_using_clause*/using_clause)?
	  )
;

execute_immediate_stmt :"execute" "immediate" sql_stmt_variable
;

allocate_cursor_stmt :"allocate" extended_cursor_name ("insensitive")? ("scroll")? "cursor" "for" extended_stmt_name
;

fetch_stmt :"fetch" ( (fetch_orientation)? "from" )? dyn_cursor_name ( ("into")=>using_clause | {false}? )
;

fetch_orientation :"next"
	| "prior"
	| "first"
	| "last"
	| ("absolute" | "relative") simple_value_spec
;

open_stmt :"open" dyn_cursor_name (using_clause)?
;

close_stmt :"close" dyn_cursor_name (using_clause)?
;

get_diag_stmt :"get" "diagnostics" (stmt_information | condition_information)
;

stmt_information :stmt_information_item (COMMA stmt_information_item)*
;

stmt_information_item :simple_target_spec EQUALS_OP
	  ( "number"
	  | "more"
	  | "command_function"
	  | "dynamic_function"
	  | "row_count"
	  )
;

condition_information :"exception" /*condition_number*/simple_value_spec
	   condition_information_item (COMMA condition_information_item)*
;

condition_information_item :simple_target_spec EQUALS_OP
	  ( "condition_number"
	  | "returned_sqlstate"
	  | "class_origin"
	  | "subclass_origin"
	  | "server_name"
	  | "connection_name"
	  | "constraint_catalog"
	  | "constraint_schema"
	  | "constraint_name"
	  | "catalog_name"
	  | "schema_name"
	  | "table_name"
	  | "column_name"
	  | "cursor_name"
	  | "message_text"
	  | "message_length"
	  | "message_octet_length"
)
;

declare_cursor :"declare" cursor_name ("insensitive")? ("scroll")? "cursor" "for"
	  ( (stmt_name)=> ( (joined_table)=> select_stmt | stmt_name )
	  | select_stmt
	  )
;

temporary_table_decl :"declare" "local" "temporary" "table" qualified_name table_element_list (on_commit_clause)?
;

query_primary :simple_table
	| (joined_table)=>joined_table
	| LEFT_PAREN query_exp RIGHT_PAREN
;

author_id :id
;

constraint_name :qualified_name
;

object_name :"domain" domain_name
	| "collation" collation_name
	| "character" "set" char_set_name
	| "translation" translation_name
;

value_spec :lit
	| general_value_spec
;

descriptor_name :("global" | "local")? simple_value_spec
;

sql_stmt_name :{LA(1) == INTRODUCER}? ((stmt_name)=>stmt_name | extended_stmt_name)
	| {LA(1) != INTRODUCER}? stmt_name
	| extended_stmt_name
;

extended_stmt_name :("global" | "local")? simple_value_spec
;

simple_target_spec :parameter_name
	| EMBDD_VARIABLE_NAME
;

using_clause :("using" | "into") /*argument*/target_spec (COMMA /*argument*/target_spec)*
	| using_descriptor
;

target_list :target_spec (COMMA target_spec)*
;

non_reserved_word :"ada"
	| "c" | "catalog_name"
	| "character_set_catalog" | "character_set_name"
	| "character_set_schema" | "class_origin" | "cobol" | "collation_catalog"
	| "collation_name" | "collation_schema" | "column_name" | "command_function"
	| "committed"
	| "condition_number" | "connection_name" | "constraint_catalog" | "constraint_name"
	| "constraint_schema" | "cursor_name"
	| "data" | "datetime_interval_code"
	| "datetime_interval_precision" | "dynamic_function"
	| "fortran"
	| "length"
	| "message_length" | "message_octet_length" | "message_text" | "more" | "mumps"
	| "name" | "nullable" | "number"
	| "pascal" | "pli"
	| "repeatable" | "returned_length" | "returned_octet_length" | "returned_sqlstate"
	| "row_count"
	| "scale" | "schema_name" | "serializable" | "server_name" | "subclass_origin"
	| "table_name" | "type"
	| "uncommitted" | "unnamed"
;

// inherited from grammar DmlSQL2Parser
any_token :!
	. // {cout << tokenNames[LA(1)] << " " << LT(1)->getText() << endl;}
;

// inherited from grammar DmlSQL2Parser
sql_script :(sql_stmt)? ( SEMICOLON (sql_stmt)? )*

;

// inherited from grammar DmlSQL2Parser
sql_single_stmt :(sql_stmt)?
;

// inherited from grammar DmlSQL2Parser
sql_data_stmt :select_stmt
	| insert_stmt
	| update_stmt
	| delete_stmt
;

// inherited from grammar DmlSQL2Parser
select_stmt :query_exp
	  ( into_clause (order_by_clause)? (updatability_clause)?
	  | order_by_clause (into_clause)? (updatability_clause)?
	  | updatability_clause (into_clause)?
	  |
	  )
;

// inherited from grammar DmlSQL2Parser
into_clause :"into" target_spec (COMMA target_spec)*
;

// inherited from grammar DmlSQL2Parser
order_by_clause :"order" "by" sort_spec_list
;

// inherited from grammar DmlSQL2Parser
sort_spec_list :sort_spec (COMMA sort_spec)*
;

// inherited from grammar DmlSQL2Parser
sort_spec :sort_key (collate_clause)? (ordering_spec)?
;

// inherited from grammar DmlSQL2Parser
sort_key :column_ref
	| UNSIGNED_INTEGER
;

// inherited from grammar DmlSQL2Parser
ordering_spec :"asc"
	| "desc"
;

// inherited from grammar DmlSQL2Parser
updatability_clause :"for" ( "read" "only" | "update" ("of" column_name_list)? )
;

// inherited from grammar DmlSQL2Parser
insert_stmt :"insert" "into" table_name insert_columns_and_source
;

// inherited from grammar DmlSQL2Parser
insert_columns_and_source :(LEFT_PAREN column_name_list/*insert_column_list*/ RIGHT_PAREN)=> LEFT_PAREN column_name_list/*insert_column_list*/ RIGHT_PAREN query_exp
	| query_exp
	| "default" "values"
;

// inherited from grammar DmlSQL2Parser
update_stmt :"update"
	    ( table_name "set" set_clause_list ( "where" (search_condition|"current" "of" dyn_cursor_name) )?
	    | "set" set_clause_list "where" "current" "of" cursor_name
	    )
;

// inherited from grammar DmlSQL2Parser
set_clause_list :set_clause (COMMA set_clause)*
;

// inherited from grammar DmlSQL2Parser
set_clause :column_name/*object_column*/ EQUALS_OP update_source
;

// inherited from grammar DmlSQL2Parser
update_source :value_exp
	| "null"
	| "default"
;

// inherited from grammar DmlSQL2Parser
delete_stmt :"delete"
	    ( "from" table_name ( "where" (search_condition|"current" "of" dyn_cursor_name) )?
	    | "where" "current" "of" cursor_name
	    )
;

// inherited from grammar DmlSQL2Parser
id :(INTRODUCER char_set_name)?
	( REGULAR_ID
    | DELIMITED_ID
    | "value"
    | "date"
    | {true}? non_reserved_word
	)
;

// inherited from grammar DmlSQL2Parser
char_set_name :id (PERIOD id (PERIOD id)?)?
;

// inherited from grammar DmlSQL2Parser
schema_name :id (PERIOD id)?
;

// inherited from grammar DmlSQL2Parser
qualified_name :id (options{greedy=true;}:PERIOD id (options{greedy=true;}:PERIOD id)?)?
;

// inherited from grammar DmlSQL2Parser
select_list :ASTERISK
	| select_sublist (COMMA select_sublist)*
;

// inherited from grammar DmlSQL2Parser
select_sublist :(table_name PERIOD ASTERISK)=> table_name PERIOD ASTERISK
	| derived_column
;

// inherited from grammar DmlSQL2Parser
derived_column :value_exp ("as" column_name)?
;

// inherited from grammar DmlSQL2Parser
value_exp_primary :set_fct_spec
	| case_exp
	| cast_spec
	| {LA(1) == INTRODUCER}? ((column_ref)=>column_ref | unsigned_value_spec)
	| {LA(1) != INTRODUCER}? column_ref
	| unsigned_value_spec
	| (LEFT_PAREN value_exp RIGHT_PAREN)=> LEFT_PAREN value_exp RIGHT_PAREN
	| scalar_subquery
;

// inherited from grammar DmlSQL2Parser
set_fct_spec :"count" LEFT_PAREN ( ASTERISK | (set_quantifier)? value_exp ) RIGHT_PAREN
	| ( "avg"
	  | "max"
	  | "min"
	  | "sum" ) LEFT_PAREN (set_quantifier)? value_exp RIGHT_PAREN
;

// inherited from grammar DmlSQL2Parser
set_quantifier :"distinct"
	| "all"
;

// inherited from grammar DmlSQL2Parser
case_exp :case_abbreviation
	| case_spec
;

// inherited from grammar DmlSQL2Parser
case_abbreviation :"nullif" LEFT_PAREN value_exp COMMA value_exp RIGHT_PAREN
	| "coalesce" LEFT_PAREN value_exp (COMMA value_exp)* RIGHT_PAREN
;

// inherited from grammar DmlSQL2Parser
case_spec :simple_case
	| searched_case
;

// inherited from grammar DmlSQL2Parser
simple_case :"case" value_exp/*case_operand*/ (simple_when_clause)+ (else_clause)? "end"
;

// inherited from grammar DmlSQL2Parser
simple_when_clause :"when" value_exp/*when_operand*/ "then" result
;

// inherited from grammar DmlSQL2Parser
else_clause :"else" result
;

// inherited from grammar DmlSQL2Parser
result :value_exp /*result_exp*/
	| "null"
;

// inherited from grammar DmlSQL2Parser
searched_case :"case" (searched_when_clause)+ (else_clause)? "end"
;

// inherited from grammar DmlSQL2Parser
searched_when_clause :"when" search_condition "then" result
;

// inherited from grammar DmlSQL2Parser
search_condition :boolean_term (boolean_term_op boolean_term)*
;

// inherited from grammar DmlSQL2Parser
boolean_term_op :"or"
;

// inherited from grammar DmlSQL2Parser
boolean_term :boolean_factor (boolean_factor_op boolean_factor)*
;

// inherited from grammar DmlSQL2Parser
boolean_factor_op :"and"
;

// inherited from grammar DmlSQL2Parser
boolean_factor :("not")? boolean_test
;

// inherited from grammar DmlSQL2Parser
boolean_test :boolean_primary ("is" ("not")? truth_value)?
;

// inherited from grammar DmlSQL2Parser
truth_value :"true"
	| "false"
	| "unknown"
;

// inherited from grammar DmlSQL2Parser
boolean_primary :(predicate)=> predicate
	| LEFT_PAREN search_condition RIGHT_PAREN
;

// inherited from grammar DmlSQL2Parser
predicate :row_value_constructor
	    ( comp_predicate
	    | ("not")? ( between_predicate
	               | in_predicate
	               | like_predicate
	               )
	    | null_predicate
	    | quantified_comp_predicate
	    | match_predicate
	    | overlaps_predicate
	    )
	| exists_predicate
	| unique_predicate
;

// inherited from grammar DmlSQL2Parser
comp_predicate ://	row_value_constructor
	comp_op row_value_constructor
;

// inherited from grammar DmlSQL2Parser
comp_op :EQUALS_OP
	| NOT_EQUALS_OP
	| LESS_THAN_OP
	| GREATER_THAN_OP
	| LESS_THAN_OR_EQUALS_OP
	| GREATER_THAN_OR_EQUALS_OP
;

// inherited from grammar DmlSQL2Parser
between_predicate ://	row_value_constructor ("not")?
	"between" row_value_constructor "and" row_value_constructor
;

// inherited from grammar DmlSQL2Parser
in_predicate ://	row_value_constructor ("not")?
	"in" in_predicate_value
;

// inherited from grammar DmlSQL2Parser
in_predicate_value :(table_subquery)=> table_subquery
	| LEFT_PAREN in_value_list RIGHT_PAREN
;

// inherited from grammar DmlSQL2Parser
in_value_list :value_exp (COMMA value_exp)*
;

// inherited from grammar DmlSQL2Parser
like_predicate ://	match_value ("not")?
	"like" pattern ("escape" escape_char)?
;

// inherited from grammar DmlSQL2Parser
pattern :char_value_exp
;

// inherited from grammar DmlSQL2Parser
escape_char :char_value_exp
;

// inherited from grammar DmlSQL2Parser
null_predicate ://	row_value_constructor
	"is" ("not")? "null"
;

// inherited from grammar DmlSQL2Parser
quantified_comp_predicate ://	row_value_constructor
	comp_op ("all" | "some" | "any") table_subquery
;

// inherited from grammar DmlSQL2Parser
exists_predicate :"exists" table_subquery
;

// inherited from grammar DmlSQL2Parser
unique_predicate :"unique" table_subquery
;

// inherited from grammar DmlSQL2Parser
match_predicate ://	row_value_constructor
	"match" ("unique")? ("full" | "partial")? table_subquery
;

// inherited from grammar DmlSQL2Parser
overlaps_predicate ://	row_value_constructor
	"overlaps" row_value_constructor
;

// inherited from grammar DmlSQL2Parser
cast_spec :"cast" LEFT_PAREN cast_operand "as" cast_target RIGHT_PAREN
;

// inherited from grammar DmlSQL2Parser
cast_operand :value_exp
	| "null"
;

// inherited from grammar DmlSQL2Parser
cast_target :domain_name
	| data_type
;

// inherited from grammar DmlSQL2Parser
data_type :char_string_type ("character" "set" char_set_name)? { builder.setDomainOfLastAttribute( SchemaImporter.STRING ); }
	| national_char_string_type { builder.setDomainOfLastAttribute( SchemaImporter.STRING ); }
	| bit_string_type { builder.setDomainOfLastAttribute( SchemaImporter.BOOLEAN ); }
	| num_type // this covers integer and double....
	| datetime_type { builder.setDomainOfLastAttribute( SchemaImporter.DATETIME ); }
	| interval_type
;

// inherited from grammar DmlSQL2Parser
length :UNSIGNED_INTEGER
;

// inherited from grammar DmlSQL2Parser
char_string_type :"character" (LEFT_PAREN length RIGHT_PAREN)?
	| "char" (LEFT_PAREN length RIGHT_PAREN)?
	| "character" "varying" LEFT_PAREN length RIGHT_PAREN
	| "char" "varying" LEFT_PAREN length RIGHT_PAREN
	| "varchar" LEFT_PAREN length RIGHT_PAREN
	| "text"
;

// inherited from grammar DmlSQL2Parser
national_char_string_type :"national"
		( "character" (LEFT_PAREN length RIGHT_PAREN)?
		| "char" (LEFT_PAREN length RIGHT_PAREN)?
		| "character" "varying" LEFT_PAREN length RIGHT_PAREN
		| "char" "varying" LEFT_PAREN length RIGHT_PAREN
		)
	| "nchar" (LEFT_PAREN length RIGHT_PAREN)?
	| "nchar" "varying" LEFT_PAREN length RIGHT_PAREN
;

// inherited from grammar DmlSQL2Parser
bit_string_type :"bit" (LEFT_PAREN length RIGHT_PAREN)?
        | "bit" "varying" LEFT_PAREN length RIGHT_PAREN
;

// inherited from grammar DmlSQL2Parser
precision :UNSIGNED_INTEGER
;

// inherited from grammar DmlSQL2Parser
scale :UNSIGNED_INTEGER
;

// inherited from grammar DmlSQL2Parser
num_type :exact_num_type { builder.setDomainOfLastAttribute( SchemaImporter.INTEGER ); }
	| approximate_num_type { builder.setDomainOfLastAttribute( SchemaImporter.REAL ); }
;

// inherited from grammar DmlSQL2Parser
approximate_num_type :"float" (LEFT_PAREN precision RIGHT_PAREN)?
	| "real"
	| "double" "precision"
;

// inherited from grammar DmlSQL2Parser
exact_num_type :"numeric" ( LEFT_PAREN precision (COMMA scale)? RIGHT_PAREN )?
    | "number" ( LEFT_PAREN precision (COMMA scale)? RIGHT_PAREN )?
    | "decimal" ( LEFT_PAREN precision (COMMA scale)? RIGHT_PAREN )?
	| "dec" ( LEFT_PAREN precision (COMMA scale)? RIGHT_PAREN )?
	| "integer"
	| "int"
	| "smallint"
;

// inherited from grammar DmlSQL2Parser
datetime_type :"date"
	| "time" (LEFT_PAREN time_precision RIGHT_PAREN)? ("with" "time" "zone")?
	| "timestamp" (LEFT_PAREN timestamp_precision RIGHT_PAREN)? ("with" "time" "zone")?
;

// inherited from grammar DmlSQL2Parser
interval_type :"interval" interval_qualifier
;

// inherited from grammar DmlSQL2Parser
scalar_subquery :subquery
;

// inherited from grammar DmlSQL2Parser
subquery :LEFT_PAREN query_exp RIGHT_PAREN
;

// inherited from grammar DmlSQL2Parser
query_exp :query_term ( ("union" | "except") ("all")? (corresponding_spec)? query_term )*
;

// inherited from grammar DmlSQL2Parser
query_term :query_primary ( "intersect" ("all")? (corresponding_spec)? query_primary)*
;

// inherited from grammar DmlSQL2Parser
corresponding_spec :"corresponding" ("by" LEFT_PAREN column_name_list/*corresponding_column_list*/ RIGHT_PAREN)?
;

// inherited from grammar DmlSQL2Parser
simple_table :query_spec
	| table_value_constructor
	| explicit_table
;

// inherited from grammar DmlSQL2Parser
query_spec :"select" (set_quantifier)? select_list (into_clause)? table_exp
;

// inherited from grammar DmlSQL2Parser
table_value_constructor :"values" table_value_const_list
;

// inherited from grammar DmlSQL2Parser
table_value_const_list :row_value_constructor (COMMA row_value_constructor)*
;

// inherited from grammar DmlSQL2Parser
row_value_constructor :(row_value_constructor_elem)=>row_value_constructor_elem
	| LEFT_PAREN row_value_const_list RIGHT_PAREN
//	| row_subquery
;

// inherited from grammar DmlSQL2Parser
row_value_constructor_elem :value_exp
	| "null"
	| "default"
;

// inherited from grammar DmlSQL2Parser
row_value_const_list :row_value_constructor_elem (COMMA row_value_constructor_elem)*
;

// inherited from grammar DmlSQL2Parser
explicit_table :"table" table_name
;

// inherited from grammar DmlSQL2Parser
joined_table :table_ref_aux (qualified_join | cross_join)
;

// inherited from grammar DmlSQL2Parser
table_ref :table_ref_aux (options{greedy=true;}:qualified_join | cross_join)*
;

// inherited from grammar DmlSQL2Parser
table_ref_aux :(table_name | /*derived_table*/table_subquery) (("as")? correlation_name (LEFT_PAREN derived_column_list RIGHT_PAREN)?)?
;

// inherited from grammar DmlSQL2Parser
derived_column_list :column_name_list
;

// inherited from grammar DmlSQL2Parser
table_subquery :subquery
;

// inherited from grammar DmlSQL2Parser
cross_join :"cross" "join" table_ref
;

// inherited from grammar DmlSQL2Parser
qualified_join ://	("natural")? (join_type)? "join" table_ref (options{greedy=true;}:join_spec)?
	  ( "inner" | outer_join_type ("outer")? )? "join" table_ref join_spec
	| "natural" ( "inner" | outer_join_type ("outer")? )? "join" table_ref
	| "union" "join" table_ref
;

// inherited from grammar DmlSQL2Parser
outer_join_type :"left"
	| "right"
	| "full"
;

// inherited from grammar DmlSQL2Parser
join_spec :join_condition
	| named_columns_join
;

// inherited from grammar DmlSQL2Parser
join_condition :"on" search_condition
;

// inherited from grammar DmlSQL2Parser
named_columns_join :"using" LEFT_PAREN column_name_list/*join_column_list*/ RIGHT_PAREN
;

// inherited from grammar DmlSQL2Parser
table_exp :from_clause
	(where_clause)?
	(group_by_clause)?
	(having_clause)?
;

// inherited from grammar DmlSQL2Parser
from_clause :"from" table_ref_list
;

// inherited from grammar DmlSQL2Parser
table_ref_list :table_ref (COMMA table_ref)*
;

// inherited from grammar DmlSQL2Parser
where_clause :"where" search_condition
;

// inherited from grammar DmlSQL2Parser
group_by_clause :"group" "by" grouping_column_ref_list
;

// inherited from grammar DmlSQL2Parser
grouping_column_ref :column_ref (collate_clause)?
;

// inherited from grammar DmlSQL2Parser
grouping_column_ref_list :grouping_column_ref (COMMA grouping_column_ref)*
;

// inherited from grammar DmlSQL2Parser
having_clause :"having" search_condition
;

// inherited from grammar DmlSQL2Parser
unsigned_value_spec :unsigned_lit
	| general_value_spec ;

// inherited from grammar DmlSQL2Parser
general_value_spec :parameter_spec
	| dyn_parameter_spec
	| variable_spec
	| "user"
	| "current_user"
	| "session_user"
	| "system_user"
	| "value"
;

// inherited from grammar DmlSQL2Parser
parameter_spec :parameter_name (/*indicator_parameter*/("indicator")? parameter_name)?
;

// inherited from grammar DmlSQL2Parser
dyn_parameter_spec :QUESTION_MARK
;

// inherited from grammar DmlSQL2Parser
variable_spec :EMBDD_VARIABLE_NAME (/*indicator_variable*/("indicator")? EMBDD_VARIABLE_NAME)?
;

// inherited from grammar DmlSQL2Parser
num_value_exp :value_exp
;

// inherited from grammar DmlSQL2Parser
string_value_exp :char_value_exp
;

// inherited from grammar DmlSQL2Parser
datetime_value_exp :value_exp
;

// inherited from grammar DmlSQL2Parser
interval_value_exp :value_exp
;

// inherited from grammar DmlSQL2Parser
char_value_exp :value_exp
;

// inherited from grammar DmlSQL2Parser
value_exp :term (options{greedy=true;}:(term_op | CONCATENATION_OP) term )*
;

// inherited from grammar DmlSQL2Parser
string_value_fct :char_value_fct
//	| bit_value_fct
;

// inherited from grammar DmlSQL2Parser
char_value_fct :char_substring_fct
	| fold
	| form_conversion
	| char_translation
	| trim_fct
;

// inherited from grammar DmlSQL2Parser
char_substring_fct :"substring" LEFT_PAREN char_value_exp "from" num_value_exp/*start_position */
	("for" num_value_exp/*string_length*/)?
	RIGHT_PAREN
;

// inherited from grammar DmlSQL2Parser
fold :("upper" | "lower") LEFT_PAREN char_value_exp RIGHT_PAREN
;

// inherited from grammar DmlSQL2Parser
form_conversion :"convert" LEFT_PAREN char_value_exp "using" form_conversion_name RIGHT_PAREN
;

// inherited from grammar DmlSQL2Parser
char_translation :"translate" LEFT_PAREN char_value_exp "using" translation_name RIGHT_PAREN
;

// inherited from grammar DmlSQL2Parser
trim_fct :"trim" LEFT_PAREN trim_operands RIGHT_PAREN
;

// inherited from grammar DmlSQL2Parser
trim_operands :trim_spec "from" char_value_exp/*trim_source*/
	| trim_spec char_value_exp/*trim_char*/ "from" char_value_exp/*trim_source*/
	| "from" char_value_exp/*trim_source*/
	| char_value_exp/*trim_char or trim_source*/ ("from" char_value_exp/*trim_source*/)?
;

// inherited from grammar DmlSQL2Parser
trim_spec :"leading"
	| "trailing"
	| "both"
;

// inherited from grammar DmlSQL2Parser
term_op :PLUS_SIGN | MINUS_SIGN
;

// inherited from grammar DmlSQL2Parser
term :factor (options{greedy=true;}:factor_op factor)*
;

// inherited from grammar DmlSQL2Parser
factor_op :ASTERISK | SOLIDUS
;

// inherited from grammar DmlSQL2Parser
factor :(sign)? gen_primary ("at" time_zone_specifier | collate_clause)?
;

// inherited from grammar DmlSQL2Parser
collate_clause :"collate" collation_name
;

// inherited from grammar DmlSQL2Parser
gen_primary :value_exp_primary (interval_qualifier)?
	| num_value_fct
	| string_value_fct
	| datetime_value_fct
;

// inherited from grammar DmlSQL2Parser
num_value_fct :position_exp
	| extract_exp
	| length_exp
;

// inherited from grammar DmlSQL2Parser
position_exp :"position" LEFT_PAREN char_value_exp "in" char_value_exp RIGHT_PAREN
;

// inherited from grammar DmlSQL2Parser
extract_exp :"extract" LEFT_PAREN extract_field "from" extract_source RIGHT_PAREN
;

// inherited from grammar DmlSQL2Parser
extract_field :datetime_field
	| time_zone_field
;

// inherited from grammar DmlSQL2Parser
datetime_field :non_second_datetime_field
	| "second"
;

// inherited from grammar DmlSQL2Parser
time_zone_field :"timezone_hour"
	| "timezone_minute"
;

// inherited from grammar DmlSQL2Parser
extract_source :datetime_value_exp
//	| interval_value_exp
;

// inherited from grammar DmlSQL2Parser
length_exp :char_length_exp
	| octet_length_exp
	| bit_length_exp
;

// inherited from grammar DmlSQL2Parser
char_length_exp :("char_length" | "character_length") LEFT_PAREN string_value_exp RIGHT_PAREN
;

// inherited from grammar DmlSQL2Parser
octet_length_exp :"octet_length" LEFT_PAREN string_value_exp RIGHT_PAREN
;

// inherited from grammar DmlSQL2Parser
bit_length_exp :"bit_length" LEFT_PAREN string_value_exp RIGHT_PAREN
;

// inherited from grammar DmlSQL2Parser
time_zone_specifier :"local"
	| "time" "zone" interval_value_exp
;

// inherited from grammar DmlSQL2Parser
datetime_value_fct :current_date_value_fct
	| current_time_value_fct
	| currenttimestamp_value_fct
;

// inherited from grammar DmlSQL2Parser
currenttimestamp_value_fct :"current_timestamp" (LEFT_PAREN timestamp_precision RIGHT_PAREN)?
;

// inherited from grammar DmlSQL2Parser
current_date_value_fct :"current_date"
;

// inherited from grammar DmlSQL2Parser
current_time_value_fct :"current_time" (LEFT_PAREN time_precision RIGHT_PAREN)?
;

// inherited from grammar DmlSQL2Parser
timestamp_precision :UNSIGNED_INTEGER/*time_frac_seconds_prec*/
;

// inherited from grammar DmlSQL2Parser
time_precision :UNSIGNED_INTEGER/*time_frac_seconds_prec*/
;

// inherited from grammar DmlSQL2Parser
table_name :qualified_name
	| qualified_local_table_name
;

// inherited from grammar DmlSQL2Parser
qualified_local_table_name :"module" PERIOD id
;

// inherited from grammar DmlSQL2Parser
domain_name :qualified_name
;

// inherited from grammar DmlSQL2Parser
column_name :id
    //{ builder.addAttribute(returnAST.toString(); }
;

// inherited from grammar DmlSQL2Parser
column_name_list :column_name (COMMA column_name)*
;

// inherited from grammar DmlSQL2Parser
correlation_name :id
;

// inherited from grammar DmlSQL2Parser
cursor_name :id
;

// inherited from grammar DmlSQL2Parser
dyn_cursor_name :{LA(1) == INTRODUCER}? ((cursor_name)=>cursor_name | extended_cursor_name)
	| {LA(1) != INTRODUCER}? cursor_name
	| extended_cursor_name
;

// inherited from grammar DmlSQL2Parser
extended_cursor_name :("global" | "local")? simple_value_spec
;

// inherited from grammar DmlSQL2Parser
simple_value_spec :parameter_name
	| EMBDD_VARIABLE_NAME
	| lit
;

// inherited from grammar DmlSQL2Parser
stmt_name :id
;

// inherited from grammar DmlSQL2Parser
parameter_name ://	COLON id
	COLON
	  ( id (PERIOD id)*
	  | UNSIGNED_INTEGER
	  )
;

// inherited from grammar DmlSQL2Parser
target_spec :parameter_spec
	| variable_spec
;

// inherited from grammar DmlSQL2Parser
column_ref :id (PERIOD id (PERIOD id (PERIOD id)?)?)?
;

// inherited from grammar DmlSQL2Parser
translation_name :qualified_name
;

// inherited from grammar DmlSQL2Parser
collation_name :qualified_name
;

// inherited from grammar DmlSQL2Parser
form_conversion_name :qualified_name
;

// inherited from grammar DmlSQL2Parser
sign :PLUS_SIGN | MINUS_SIGN
;

// inherited from grammar DmlSQL2Parser
unsigned_num_lit :UNSIGNED_INTEGER
	| EXACT_NUM_LIT
	| APPROXIMATE_NUM_LIT
;

// inherited from grammar DmlSQL2Parser
char_string_lit :(INTRODUCER char_set_name)? CHAR_STRING
;

// inherited from grammar DmlSQL2Parser
unsigned_lit :unsigned_num_lit
	| general_lit
;

// inherited from grammar DmlSQL2Parser
general_lit :char_string_lit
	| NATIONAL_CHAR_STRING_LIT
	| BIT_STRING_LIT
	| HEX_STRING_LIT
	| datetime_lit
	| interval_lit
;

// inherited from grammar DmlSQL2Parser
datetime_lit :date_lit
	| time_lit
	| timestamp_lit
;

// inherited from grammar DmlSQL2Parser
date_lit :"date" CHAR_STRING/*date_string*/
;

// inherited from grammar DmlSQL2Parser
interval_lit :"interval" (sign)? CHAR_STRING/*interval_string*/ interval_qualifier
;

// inherited from grammar DmlSQL2Parser
interval_qualifier :start_field
	    ( "to" end_field
	    | /*single_datetime_field*/
	    )
	| "second" /*single_datetime_field*/
	    ( LEFT_PAREN UNSIGNED_INTEGER/*interval_leading_fieldprec*/
		(COMMA UNSIGNED_INTEGER/*interval_frac_seconds_prec*/)?
	      RIGHT_PAREN
	    )?
;

// inherited from grammar DmlSQL2Parser
start_field :non_second_datetime_field (LEFT_PAREN UNSIGNED_INTEGER/*interval_leading_fieldprec*/ RIGHT_PAREN)?
;

// inherited from grammar DmlSQL2Parser
end_field :non_second_datetime_field
	| "second" (LEFT_PAREN UNSIGNED_INTEGER/*interval_frac_seconds_prec*/ RIGHT_PAREN)?
;

// inherited from grammar DmlSQL2Parser
non_second_datetime_field :"year"
	| "month"
	| "day"
	| "hour"
	| "minute"
;

// inherited from grammar DmlSQL2Parser
lit :signed_num_lit
	| general_lit
;

// inherited from grammar DmlSQL2Parser
signed_num_lit :(sign)? unsigned_num_lit
;

// inherited from grammar DmlSQL2Parser
timestamp_lit :"timestamp" CHAR_STRING/*timestamp_string*/
;

// inherited from grammar DmlSQL2Parser
time_lit :"time" CHAR_STRING/*time_string*/
;


