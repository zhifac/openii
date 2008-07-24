// $ANTLR 2.7.7 (2006-11-01): "expandedSqlSQL2.g" -> "SqlSQL2Parser.java"$

//  Global header starts here, at the top of all generated files
package org.mitre.flexidata.ygg.importers.ddl;

import java.util.*;
import org.mitre.schemastore.model.*;
import org.mitre.flexidata.ygg.importers.*;

//  Global header ends here

import antlr.TokenBuffer;
import antlr.TokenStreamException;
import antlr.TokenStream;
import antlr.RecognitionException;
import antlr.NoViableAltException;
import antlr.ParserSharedInputState;
import antlr.collections.impl.BitSet;
import antlr.collections.AST;
import antlr.ASTFactory;
import antlr.ASTPair;

//  Class preamble starts here - right before the class definition in the generated class file

//  Class preamble ends here

public class SqlSQL2Parser extends antlr.LLkParser       implements SqlSQL2TokenTypes
 {

//  Class body inset starts here - at the top within the generated class body
protected SchemaBuilder builder = new SchemaBuilder();

public ArrayList<SchemaElement> getSchemaObjects()
{
    return builder.getSchemaObjects();
}


//  Class body inset ends here

protected SqlSQL2Parser(TokenBuffer tokenBuf, int k) {
  super(tokenBuf,k);
  tokenNames = _tokenNames;
  buildTokenTypeASTClassMap();
  astFactory = new ASTFactory(getTokenTypeToASTClassMap());
}

public SqlSQL2Parser(TokenBuffer tokenBuf) {
  this(tokenBuf,2);
}

protected SqlSQL2Parser(TokenStream lexer, int k) {
  super(lexer,k);
  tokenNames = _tokenNames;
  buildTokenTypeASTClassMap();
  astFactory = new ASTFactory(getTokenTypeToASTClassMap());
}

public SqlSQL2Parser(TokenStream lexer) {
  this(lexer,2);
}

public SqlSQL2Parser(ParserSharedInputState state) {
  super(state,2);
  tokenNames = _tokenNames;
  buildTokenTypeASTClassMap();
  astFactory = new ASTFactory(getTokenTypeToASTClassMap());
}

	public final void sql_stmt() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST sql_stmt_AST = null;

		{
		if ((_tokenSet_0.member(LA(1)))) {
			sql_data_stmt();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else if ((LA(1)==SQL2RW_alter||LA(1)==SQL2RW_create||LA(1)==SQL2RW_drop||LA(1)==SQL2RW_grant||LA(1)==SQL2RW_revoke||LA(1)==SQL2W_comment)) {
			sql_schema_stmt();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else if ((LA(1)==SQL2RW_commit||LA(1)==SQL2RW_rollback||LA(1)==SQL2RW_set) && (LA(2)==SQL2RW_constraints||LA(2)==SQL2RW_transaction||LA(2)==SQL2RW_work||LA(2)==SEMICOLON)) {
			sql_transaction_stmt();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else if ((LA(1)==SQL2RW_connect||LA(1)==SQL2RW_disconnect||LA(1)==SQL2RW_set) && (_tokenSet_1.member(LA(2)))) {
			{
			if ((LA(1)==SQL2RW_set) && (LA(2)==SQL2RW_catalog||LA(2)==SQL2RW_names||LA(2)==SQL2RW_schema||LA(2)==SQL2RW_session||LA(2)==SQL2RW_time)) {
				sql_session_stmt();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else if ((LA(1)==SQL2RW_connect||LA(1)==SQL2RW_disconnect||LA(1)==SQL2RW_set) && (_tokenSet_2.member(LA(2)))) {
				sql_connection_stmt();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}

			}
		}
		else if ((_tokenSet_3.member(LA(1))) && (_tokenSet_4.member(LA(2)))) {
			sql_dyn_stmt();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else if ((LA(1)==SQL2RW_allocate||LA(1)==SQL2RW_deallocate||LA(1)==SQL2RW_get||LA(1)==SQL2RW_set) && (LA(2)==SQL2RW_descriptor)) {
			system_descriptor_stmt();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else if ((LA(1)==SQL2RW_get) && (LA(2)==SQL2RW_diagnostics)) {
			get_diag_stmt();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else if ((LA(1)==SQL2RW_declare) && (_tokenSet_5.member(LA(2)))) {
			declare_cursor();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else if ((LA(1)==SQL2RW_declare) && (LA(2)==SQL2RW_local)) {
			temporary_table_decl();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		AST tmp1_AST = null;
		tmp1_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp1_AST);
		match(SEMICOLON);
		sql_stmt_AST = (AST)currentAST.root;
		returnAST = sql_stmt_AST;
	}

	public final void sql_data_stmt() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST sql_data_stmt_AST = null;

		switch ( LA(1)) {
		case SQL2NRW_ada:
		case SQL2NRW_c:
		case SQL2NRW_catalog_name:
		case SQL2NRW_character_set_catalog:
		case SQL2NRW_character_set_name:
		case SQL2NRW_character_set_schema:
		case SQL2NRW_class_origin:
		case SQL2NRW_cobol:
		case SQL2NRW_collation_catalog:
		case SQL2NRW_collation_name:
		case SQL2NRW_collation_schema:
		case SQL2NRW_column_name:
		case SQL2NRW_command_function:
		case SQL2NRW_committed:
		case SQL2NRW_condition_number:
		case SQL2NRW_connection_name:
		case SQL2NRW_constraint_catalog:
		case SQL2NRW_constraint_name:
		case SQL2NRW_constraint_schema:
		case SQL2NRW_cursor_name:
		case SQL2NRW_data:
		case SQL2NRW_datetime_interval_code:
		case SQL2NRW_datetime_interval_precision:
		case SQL2NRW_dynamic_function:
		case SQL2NRW_fortran:
		case SQL2NRW_length:
		case SQL2NRW_message_length:
		case SQL2NRW_message_octet_length:
		case SQL2NRW_message_text:
		case SQL2NRW_more:
		case SQL2NRW_mumps:
		case SQL2NRW_name:
		case SQL2NRW_nullable:
		case SQL2NRW_number:
		case SQL2NRW_pascal:
		case SQL2NRW_pli:
		case SQL2NRW_repeatable:
		case SQL2NRW_returned_length:
		case SQL2NRW_returned_octet_length:
		case SQL2NRW_returned_sqlstate:
		case SQL2NRW_row_count:
		case SQL2NRW_scale:
		case SQL2NRW_schema_name:
		case SQL2NRW_serializable:
		case SQL2NRW_server_name:
		case SQL2NRW_subclass_origin:
		case SQL2NRW_table_name:
		case SQL2NRW_type:
		case SQL2NRW_uncommitted:
		case SQL2NRW_unnamed:
		case SQL2RW_date:
		case SQL2RW_module:
		case SQL2RW_select:
		case SQL2RW_table:
		case SQL2RW_value:
		case SQL2RW_values:
		case REGULAR_ID:
		case DELIMITED_ID:
		case LEFT_PAREN:
		case INTRODUCER:
		{
			select_stmt();
			astFactory.addASTChild(currentAST, returnAST);
			sql_data_stmt_AST = (AST)currentAST.root;
			break;
		}
		case SQL2RW_insert:
		{
			insert_stmt();
			astFactory.addASTChild(currentAST, returnAST);
			sql_data_stmt_AST = (AST)currentAST.root;
			break;
		}
		case SQL2RW_update:
		{
			update_stmt();
			astFactory.addASTChild(currentAST, returnAST);
			sql_data_stmt_AST = (AST)currentAST.root;
			break;
		}
		case SQL2RW_delete:
		{
			delete_stmt();
			astFactory.addASTChild(currentAST, returnAST);
			sql_data_stmt_AST = (AST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = sql_data_stmt_AST;
	}

	public final void sql_schema_stmt() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST sql_schema_stmt_AST = null;

		if ((LA(1)==SQL2RW_create||LA(1)==SQL2RW_grant||LA(1)==SQL2W_comment)) {
			sql_schema_def_stmt();
			astFactory.addASTChild(currentAST, returnAST);
			sql_schema_stmt_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_alter||LA(1)==SQL2RW_drop||LA(1)==SQL2RW_revoke)) {
			sql_schema_manipulat_stmt();
			astFactory.addASTChild(currentAST, returnAST);
			sql_schema_stmt_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = sql_schema_stmt_AST;
	}

	public final void sql_transaction_stmt() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST sql_transaction_stmt_AST = null;

		if ((LA(1)==SQL2RW_commit)) {
			commit_stmt();
			astFactory.addASTChild(currentAST, returnAST);
			sql_transaction_stmt_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_rollback)) {
			rollback_stmt();
			astFactory.addASTChild(currentAST, returnAST);
			sql_transaction_stmt_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_set) && (LA(2)==SQL2RW_constraints)) {
			set_constraints_mode_stmt();
			astFactory.addASTChild(currentAST, returnAST);
			sql_transaction_stmt_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_set) && (LA(2)==SQL2RW_transaction)) {
			set_transaction_stmt();
			astFactory.addASTChild(currentAST, returnAST);
			sql_transaction_stmt_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = sql_transaction_stmt_AST;
	}

	public final void sql_session_stmt() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST sql_session_stmt_AST = null;

		if ((LA(1)==SQL2RW_set) && (LA(2)==SQL2RW_catalog)) {
			set_catalog_stmt();
			astFactory.addASTChild(currentAST, returnAST);
			sql_session_stmt_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_set) && (LA(2)==SQL2RW_schema)) {
			set_schema_stmt();
			astFactory.addASTChild(currentAST, returnAST);
			sql_session_stmt_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_set) && (LA(2)==SQL2RW_names)) {
			set_names_stmt();
			astFactory.addASTChild(currentAST, returnAST);
			sql_session_stmt_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_set) && (LA(2)==SQL2RW_session)) {
			set_session_author_id_stmt();
			astFactory.addASTChild(currentAST, returnAST);
			sql_session_stmt_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_set) && (LA(2)==SQL2RW_time)) {
			set_local_time_zone_stmt();
			astFactory.addASTChild(currentAST, returnAST);
			sql_session_stmt_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = sql_session_stmt_AST;
	}

	public final void sql_connection_stmt() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST sql_connection_stmt_AST = null;

		if ((LA(1)==SQL2RW_connect)) {
			connect_stmt();
			astFactory.addASTChild(currentAST, returnAST);
			sql_connection_stmt_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_disconnect)) {
			disconnect_stmt();
			astFactory.addASTChild(currentAST, returnAST);
			sql_connection_stmt_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_set)) {
			set_connection_stmt();
			astFactory.addASTChild(currentAST, returnAST);
			sql_connection_stmt_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = sql_connection_stmt_AST;
	}

	public final void sql_dyn_stmt() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST sql_dyn_stmt_AST = null;

		switch ( LA(1)) {
		case SQL2RW_prepare:
		{
			prepare_stmt();
			astFactory.addASTChild(currentAST, returnAST);
			sql_dyn_stmt_AST = (AST)currentAST.root;
			break;
		}
		case SQL2RW_deallocate:
		{
			deallocate_prepared_stmt();
			astFactory.addASTChild(currentAST, returnAST);
			sql_dyn_stmt_AST = (AST)currentAST.root;
			break;
		}
		case SQL2RW_describe:
		{
			describe_stmt();
			astFactory.addASTChild(currentAST, returnAST);
			sql_dyn_stmt_AST = (AST)currentAST.root;
			break;
		}
		case SQL2RW_allocate:
		{
			allocate_cursor_stmt();
			astFactory.addASTChild(currentAST, returnAST);
			sql_dyn_stmt_AST = (AST)currentAST.root;
			break;
		}
		case SQL2RW_fetch:
		{
			fetch_stmt();
			astFactory.addASTChild(currentAST, returnAST);
			sql_dyn_stmt_AST = (AST)currentAST.root;
			break;
		}
		case SQL2RW_open:
		{
			open_stmt();
			astFactory.addASTChild(currentAST, returnAST);
			sql_dyn_stmt_AST = (AST)currentAST.root;
			break;
		}
		case SQL2RW_close:
		{
			close_stmt();
			astFactory.addASTChild(currentAST, returnAST);
			sql_dyn_stmt_AST = (AST)currentAST.root;
			break;
		}
		default:
			if ((LA(1)==SQL2RW_execute) && (_tokenSet_6.member(LA(2)))) {
				execute_stmt();
				astFactory.addASTChild(currentAST, returnAST);
				sql_dyn_stmt_AST = (AST)currentAST.root;
			}
			else if ((LA(1)==SQL2RW_execute) && (LA(2)==SQL2RW_immediate)) {
				execute_immediate_stmt();
				astFactory.addASTChild(currentAST, returnAST);
				sql_dyn_stmt_AST = (AST)currentAST.root;
			}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = sql_dyn_stmt_AST;
	}

	public final void system_descriptor_stmt() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST system_descriptor_stmt_AST = null;

		switch ( LA(1)) {
		case SQL2RW_allocate:
		{
			AST tmp2_AST = null;
			tmp2_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp2_AST);
			match(SQL2RW_allocate);
			AST tmp3_AST = null;
			tmp3_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp3_AST);
			match(SQL2RW_descriptor);
			descriptor_name();
			astFactory.addASTChild(currentAST, returnAST);
			{
			if ((LA(1)==SQL2RW_with)) {
				AST tmp4_AST = null;
				tmp4_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp4_AST);
				match(SQL2RW_with);
				AST tmp5_AST = null;
				tmp5_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp5_AST);
				match(SQL2RW_max);
				simple_value_spec();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else if ((LA(1)==SEMICOLON)) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}

			}
			system_descriptor_stmt_AST = (AST)currentAST.root;
			break;
		}
		case SQL2RW_deallocate:
		{
			AST tmp6_AST = null;
			tmp6_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp6_AST);
			match(SQL2RW_deallocate);
			AST tmp7_AST = null;
			tmp7_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp7_AST);
			match(SQL2RW_descriptor);
			descriptor_name();
			astFactory.addASTChild(currentAST, returnAST);
			system_descriptor_stmt_AST = (AST)currentAST.root;
			break;
		}
		case SQL2RW_set:
		{
			AST tmp8_AST = null;
			tmp8_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp8_AST);
			match(SQL2RW_set);
			AST tmp9_AST = null;
			tmp9_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp9_AST);
			match(SQL2RW_descriptor);
			descriptor_name();
			astFactory.addASTChild(currentAST, returnAST);
			set_descriptor_information();
			astFactory.addASTChild(currentAST, returnAST);
			system_descriptor_stmt_AST = (AST)currentAST.root;
			break;
		}
		case SQL2RW_get:
		{
			AST tmp10_AST = null;
			tmp10_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp10_AST);
			match(SQL2RW_get);
			AST tmp11_AST = null;
			tmp11_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp11_AST);
			match(SQL2RW_descriptor);
			descriptor_name();
			astFactory.addASTChild(currentAST, returnAST);
			get_descriptor_information();
			astFactory.addASTChild(currentAST, returnAST);
			system_descriptor_stmt_AST = (AST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = system_descriptor_stmt_AST;
	}

	public final void get_diag_stmt() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST get_diag_stmt_AST = null;

		AST tmp12_AST = null;
		tmp12_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp12_AST);
		match(SQL2RW_get);
		AST tmp13_AST = null;
		tmp13_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp13_AST);
		match(SQL2RW_diagnostics);
		{
		if ((LA(1)==EMBDD_VARIABLE_NAME||LA(1)==COLON)) {
			stmt_information();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else if ((LA(1)==SQL2RW_exception)) {
			condition_information();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		get_diag_stmt_AST = (AST)currentAST.root;
		returnAST = get_diag_stmt_AST;
	}

	public final void declare_cursor() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST declare_cursor_AST = null;

		AST tmp14_AST = null;
		tmp14_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp14_AST);
		match(SQL2RW_declare);
		cursor_name();
		astFactory.addASTChild(currentAST, returnAST);
		{
		if ((LA(1)==SQL2RW_insensitive)) {
			AST tmp15_AST = null;
			tmp15_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp15_AST);
			match(SQL2RW_insensitive);
		}
		else if ((LA(1)==SQL2RW_cursor||LA(1)==SQL2RW_scroll)) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		{
		if ((LA(1)==SQL2RW_scroll)) {
			AST tmp16_AST = null;
			tmp16_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp16_AST);
			match(SQL2RW_scroll);
		}
		else if ((LA(1)==SQL2RW_cursor)) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		AST tmp17_AST = null;
		tmp17_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp17_AST);
		match(SQL2RW_cursor);
		AST tmp18_AST = null;
		tmp18_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp18_AST);
		match(SQL2RW_for);
		{
		boolean synPredMatched325 = false;
		if (((_tokenSet_7.member(LA(1))) && (_tokenSet_8.member(LA(2))))) {
			int _m325 = mark();
			synPredMatched325 = true;
			inputState.guessing++;
			try {
				{
				stmt_name();
				}
			}
			catch (RecognitionException pe) {
				synPredMatched325 = false;
			}
			rewind(_m325);
inputState.guessing--;
		}
		if ( synPredMatched325 ) {
			{
			boolean synPredMatched328 = false;
			if (((_tokenSet_7.member(LA(1))) && (_tokenSet_9.member(LA(2))))) {
				int _m328 = mark();
				synPredMatched328 = true;
				inputState.guessing++;
				try {
					{
					joined_table();
					}
				}
				catch (RecognitionException pe) {
					synPredMatched328 = false;
				}
				rewind(_m328);
inputState.guessing--;
			}
			if ( synPredMatched328 ) {
				select_stmt();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else if ((_tokenSet_5.member(LA(1))) && (_tokenSet_10.member(LA(2)))) {
				stmt_name();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}

			}
		}
		else if ((_tokenSet_7.member(LA(1))) && (_tokenSet_9.member(LA(2)))) {
			select_stmt();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		declare_cursor_AST = (AST)currentAST.root;
		returnAST = declare_cursor_AST;
	}

	public final void temporary_table_decl() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST temporary_table_decl_AST = null;

		AST tmp19_AST = null;
		tmp19_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp19_AST);
		match(SQL2RW_declare);
		AST tmp20_AST = null;
		tmp20_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp20_AST);
		match(SQL2RW_local);
		AST tmp21_AST = null;
		tmp21_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp21_AST);
		match(SQL2RW_temporary);
		AST tmp22_AST = null;
		tmp22_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp22_AST);
		match(SQL2RW_table);
		qualified_name();
		astFactory.addASTChild(currentAST, returnAST);
		table_element_list();
		astFactory.addASTChild(currentAST, returnAST);
		{
		if ((LA(1)==SQL2RW_on)) {
			on_commit_clause();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else if ((LA(1)==SEMICOLON)) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		temporary_table_decl_AST = (AST)currentAST.root;
		returnAST = temporary_table_decl_AST;
	}

	public final void sql_schema_def_stmt() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST sql_schema_def_stmt_AST = null;

		if ((LA(1)==SQL2RW_create) && (LA(2)==SQL2RW_schema)) {
			schema_def();
			astFactory.addASTChild(currentAST, returnAST);
			sql_schema_def_stmt_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_create) && (LA(2)==SQL2RW_global||LA(2)==SQL2RW_local||LA(2)==SQL2RW_table)) {
			table_def();
			astFactory.addASTChild(currentAST, returnAST);
			sql_schema_def_stmt_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_create) && (LA(2)==SQL2RW_view)) {
			view_def();
			astFactory.addASTChild(currentAST, returnAST);
			sql_schema_def_stmt_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_grant)) {
			grant_stmt();
			astFactory.addASTChild(currentAST, returnAST);
			sql_schema_def_stmt_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_create) && (LA(2)==SQL2RW_domain)) {
			domain_def();
			astFactory.addASTChild(currentAST, returnAST);
			sql_schema_def_stmt_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_create) && (LA(2)==SQL2RW_character)) {
			char_set_def();
			astFactory.addASTChild(currentAST, returnAST);
			sql_schema_def_stmt_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_create) && (LA(2)==SQL2RW_collation)) {
			collation_def();
			astFactory.addASTChild(currentAST, returnAST);
			sql_schema_def_stmt_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_create) && (LA(2)==SQL2RW_translation)) {
			translation_def();
			astFactory.addASTChild(currentAST, returnAST);
			sql_schema_def_stmt_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_create) && (LA(2)==SQL2RW_assertion)) {
			assertion_def();
			astFactory.addASTChild(currentAST, returnAST);
			sql_schema_def_stmt_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2W_comment)) {
			comment_def();
			astFactory.addASTChild(currentAST, returnAST);
			sql_schema_def_stmt_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = sql_schema_def_stmt_AST;
	}

	public final void sql_schema_manipulat_stmt() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST sql_schema_manipulat_stmt_AST = null;

		if ((LA(1)==SQL2RW_drop) && (LA(2)==SQL2RW_schema)) {
			drop_schema_stmt();
			astFactory.addASTChild(currentAST, returnAST);
			sql_schema_manipulat_stmt_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_alter) && (LA(2)==SQL2RW_table)) {
			alter_table_stmt();
			astFactory.addASTChild(currentAST, returnAST);
			sql_schema_manipulat_stmt_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_drop) && (LA(2)==SQL2RW_table)) {
			drop_table_stmt();
			astFactory.addASTChild(currentAST, returnAST);
			sql_schema_manipulat_stmt_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_drop) && (LA(2)==SQL2RW_view)) {
			drop_view_stmt();
			astFactory.addASTChild(currentAST, returnAST);
			sql_schema_manipulat_stmt_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_revoke)) {
			revoke_stmt();
			astFactory.addASTChild(currentAST, returnAST);
			sql_schema_manipulat_stmt_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_alter) && (LA(2)==SQL2RW_domain)) {
			alter_domain_stmt();
			astFactory.addASTChild(currentAST, returnAST);
			sql_schema_manipulat_stmt_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_drop) && (LA(2)==SQL2RW_domain)) {
			drop_domain_stmt();
			astFactory.addASTChild(currentAST, returnAST);
			sql_schema_manipulat_stmt_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_drop) && (LA(2)==SQL2RW_character)) {
			drop_char_set_stmt();
			astFactory.addASTChild(currentAST, returnAST);
			sql_schema_manipulat_stmt_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_drop) && (LA(2)==SQL2RW_collation)) {
			drop_collation_stmt();
			astFactory.addASTChild(currentAST, returnAST);
			sql_schema_manipulat_stmt_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_drop) && (LA(2)==SQL2RW_translation)) {
			drop_translation_stmt();
			astFactory.addASTChild(currentAST, returnAST);
			sql_schema_manipulat_stmt_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_drop) && (LA(2)==SQL2RW_assertion)) {
			drop_assertion_stmt();
			astFactory.addASTChild(currentAST, returnAST);
			sql_schema_manipulat_stmt_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = sql_schema_manipulat_stmt_AST;
	}

	public final void schema_def() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST schema_def_AST = null;

		AST tmp23_AST = null;
		tmp23_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp23_AST);
		match(SQL2RW_create);
		AST tmp24_AST = null;
		tmp24_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp24_AST);
		match(SQL2RW_schema);
		schema_name_clause();
		astFactory.addASTChild(currentAST, returnAST);
		{
		if ((LA(1)==SQL2RW_default)) {
			schema_char_set_spec();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else if ((LA(1)==SQL2RW_create||LA(1)==SQL2RW_grant||LA(1)==SEMICOLON)) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		{
		_loop106:
		do {
			if ((LA(1)==SQL2RW_create||LA(1)==SQL2RW_grant)) {
				schema_element();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop106;
			}

		} while (true);
		}
		schema_def_AST = (AST)currentAST.root;
		returnAST = schema_def_AST;
	}

	public final void table_def() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST table_def_AST = null;

		AST tmp25_AST = null;
		tmp25_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp25_AST);
		match(SQL2RW_create);
		{
		if ((LA(1)==SQL2RW_global||LA(1)==SQL2RW_local)) {
			{
			if ((LA(1)==SQL2RW_global)) {
				AST tmp26_AST = null;
				tmp26_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp26_AST);
				match(SQL2RW_global);
			}
			else if ((LA(1)==SQL2RW_local)) {
				AST tmp27_AST = null;
				tmp27_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp27_AST);
				match(SQL2RW_local);
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}

			}
			AST tmp28_AST = null;
			tmp28_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp28_AST);
			match(SQL2RW_temporary);
			AST tmp29_AST = null;
			tmp29_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp29_AST);
			match(SQL2RW_table);
			table_name();
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				builder.addEntity( returnAST.toString() );
			}
			table_element_list();
			astFactory.addASTChild(currentAST, returnAST);
			{
			if ((LA(1)==SQL2RW_on)) {
				on_commit_clause();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else if ((LA(1)==SQL2RW_create||LA(1)==SQL2RW_grant||LA(1)==SEMICOLON)) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}

			}
		}
		else if ((LA(1)==SQL2RW_table)) {
			AST tmp30_AST = null;
			tmp30_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp30_AST);
			match(SQL2RW_table);
			table_name();
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				builder.addEntity( returnAST.toString() ); System.out.println(returnAST.toString());
			}
			table_element_list();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		table_def_AST = (AST)currentAST.root;
		returnAST = table_def_AST;
	}

	public final void view_def() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST view_def_AST = null;

		AST tmp31_AST = null;
		tmp31_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp31_AST);
		match(SQL2RW_create);
		AST tmp32_AST = null;
		tmp32_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp32_AST);
		match(SQL2RW_view);
		table_name();
		astFactory.addASTChild(currentAST, returnAST);
		{
		if ((LA(1)==LEFT_PAREN)) {
			AST tmp33_AST = null;
			tmp33_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp33_AST);
			match(LEFT_PAREN);
			column_name_list();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp34_AST = null;
			tmp34_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp34_AST);
			match(RIGHT_PAREN);
		}
		else if ((LA(1)==SQL2RW_as)) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		AST tmp35_AST = null;
		tmp35_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp35_AST);
		match(SQL2RW_as);
		query_exp();
		astFactory.addASTChild(currentAST, returnAST);
		{
		if ((LA(1)==SQL2RW_with)) {
			AST tmp36_AST = null;
			tmp36_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp36_AST);
			match(SQL2RW_with);
			{
			if ((LA(1)==SQL2RW_cascaded)) {
				AST tmp37_AST = null;
				tmp37_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp37_AST);
				match(SQL2RW_cascaded);
			}
			else if ((LA(1)==SQL2RW_local)) {
				AST tmp38_AST = null;
				tmp38_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp38_AST);
				match(SQL2RW_local);
			}
			else if ((LA(1)==SQL2RW_check)) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}

			}
			AST tmp39_AST = null;
			tmp39_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp39_AST);
			match(SQL2RW_check);
			AST tmp40_AST = null;
			tmp40_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp40_AST);
			match(SQL2RW_option);
		}
		else if ((LA(1)==SQL2RW_create||LA(1)==SQL2RW_grant||LA(1)==SEMICOLON)) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		view_def_AST = (AST)currentAST.root;
		returnAST = view_def_AST;
	}

	public final void grant_stmt() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST grant_stmt_AST = null;

		AST tmp41_AST = null;
		tmp41_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp41_AST);
		match(SQL2RW_grant);
		{
		if ((LA(1)==SQL2RW_all||LA(1)==SQL2RW_delete||LA(1)==SQL2RW_insert||LA(1)==SQL2RW_references||LA(1)==SQL2RW_select||LA(1)==SQL2RW_update)) {
			privileges();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp42_AST = null;
			tmp42_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp42_AST);
			match(SQL2RW_on);
			{
			if ((LA(1)==SQL2RW_table)) {
				AST tmp43_AST = null;
				tmp43_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp43_AST);
				match(SQL2RW_table);
			}
			else if ((_tokenSet_11.member(LA(1)))) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}

			}
			table_name();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else if ((LA(1)==SQL2RW_usage)) {
			AST tmp44_AST = null;
			tmp44_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp44_AST);
			match(SQL2RW_usage);
			AST tmp45_AST = null;
			tmp45_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp45_AST);
			match(SQL2RW_on);
			object_name();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		AST tmp46_AST = null;
		tmp46_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp46_AST);
		match(SQL2RW_to);
		grantee();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop175:
		do {
			if ((LA(1)==COMMA)) {
				AST tmp47_AST = null;
				tmp47_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp47_AST);
				match(COMMA);
				grantee();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop175;
			}

		} while (true);
		}
		{
		if ((LA(1)==SQL2RW_with)) {
			AST tmp48_AST = null;
			tmp48_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp48_AST);
			match(SQL2RW_with);
			AST tmp49_AST = null;
			tmp49_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp49_AST);
			match(SQL2RW_grant);
			AST tmp50_AST = null;
			tmp50_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp50_AST);
			match(SQL2RW_option);
		}
		else if ((LA(1)==SQL2RW_create||LA(1)==SQL2RW_grant||LA(1)==SEMICOLON)) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		grant_stmt_AST = (AST)currentAST.root;
		returnAST = grant_stmt_AST;
	}

	public final void domain_def() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST domain_def_AST = null;

		AST tmp51_AST = null;
		tmp51_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp51_AST);
		match(SQL2RW_create);
		AST tmp52_AST = null;
		tmp52_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp52_AST);
		match(SQL2RW_domain);
		domain_name();
		astFactory.addASTChild(currentAST, returnAST);
		{
		if ((LA(1)==SQL2RW_as)) {
			AST tmp53_AST = null;
			tmp53_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp53_AST);
			match(SQL2RW_as);
		}
		else if ((_tokenSet_12.member(LA(1)))) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		data_type();
		astFactory.addASTChild(currentAST, returnAST);
		{
		if ((LA(1)==SQL2RW_default)) {
			default_clause();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else if ((LA(1)==SQL2RW_check||LA(1)==SQL2RW_collate||LA(1)==SQL2RW_constraint||LA(1)==SQL2RW_create||LA(1)==SQL2RW_grant||LA(1)==SEMICOLON)) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		{
		_loop189:
		do {
			if ((LA(1)==SQL2RW_check||LA(1)==SQL2RW_constraint)) {
				domain_constraint();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop189;
			}

		} while (true);
		}
		{
		if ((LA(1)==SQL2RW_collate)) {
			collate_clause();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else if ((LA(1)==SQL2RW_create||LA(1)==SQL2RW_grant||LA(1)==SEMICOLON)) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		domain_def_AST = (AST)currentAST.root;
		returnAST = domain_def_AST;
	}

	public final void char_set_def() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST char_set_def_AST = null;

		AST tmp54_AST = null;
		tmp54_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp54_AST);
		match(SQL2RW_create);
		AST tmp55_AST = null;
		tmp55_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp55_AST);
		match(SQL2RW_character);
		AST tmp56_AST = null;
		tmp56_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp56_AST);
		match(SQL2RW_set);
		char_set_name();
		astFactory.addASTChild(currentAST, returnAST);
		{
		if ((LA(1)==SQL2RW_as)) {
			AST tmp57_AST = null;
			tmp57_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp57_AST);
			match(SQL2RW_as);
		}
		else if ((LA(1)==SQL2RW_get)) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		AST tmp58_AST = null;
		tmp58_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp58_AST);
		match(SQL2RW_get);
		char_set_name();
		astFactory.addASTChild(currentAST, returnAST);
		{
		if ((LA(1)==SQL2RW_collate)) {
			collate_clause();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else if ((LA(1)==SQL2RW_collation)) {
			limited_collation_def();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else if ((LA(1)==SQL2RW_create||LA(1)==SQL2RW_grant||LA(1)==SEMICOLON)) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		char_set_def_AST = (AST)currentAST.root;
		returnAST = char_set_def_AST;
	}

	public final void collation_def() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST collation_def_AST = null;

		AST tmp59_AST = null;
		tmp59_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp59_AST);
		match(SQL2RW_create);
		AST tmp60_AST = null;
		tmp60_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp60_AST);
		match(SQL2RW_collation);
		collation_name();
		astFactory.addASTChild(currentAST, returnAST);
		AST tmp61_AST = null;
		tmp61_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp61_AST);
		match(SQL2RW_for);
		char_set_name();
		astFactory.addASTChild(currentAST, returnAST);
		AST tmp62_AST = null;
		tmp62_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp62_AST);
		match(SQL2RW_from);
		collation_source();
		astFactory.addASTChild(currentAST, returnAST);
		{
		if ((LA(1)==SQL2RW_no)) {
			AST tmp63_AST = null;
			tmp63_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp63_AST);
			match(SQL2RW_no);
			AST tmp64_AST = null;
			tmp64_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp64_AST);
			match(SQL2RW_pad);
		}
		else if ((LA(1)==SQL2RW_pad)) {
			AST tmp65_AST = null;
			tmp65_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp65_AST);
			match(SQL2RW_pad);
			AST tmp66_AST = null;
			tmp66_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp66_AST);
			match(SQL2RW_space);
		}
		else if ((LA(1)==SQL2RW_create||LA(1)==SQL2RW_grant||LA(1)==SEMICOLON)) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		collation_def_AST = (AST)currentAST.root;
		returnAST = collation_def_AST;
	}

	public final void translation_def() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST translation_def_AST = null;

		AST tmp67_AST = null;
		tmp67_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp67_AST);
		match(SQL2RW_create);
		AST tmp68_AST = null;
		tmp68_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp68_AST);
		match(SQL2RW_translation);
		translation_name();
		astFactory.addASTChild(currentAST, returnAST);
		AST tmp69_AST = null;
		tmp69_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp69_AST);
		match(SQL2RW_for);
		char_set_name();
		astFactory.addASTChild(currentAST, returnAST);
		AST tmp70_AST = null;
		tmp70_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp70_AST);
		match(SQL2RW_to);
		char_set_name();
		astFactory.addASTChild(currentAST, returnAST);
		AST tmp71_AST = null;
		tmp71_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp71_AST);
		match(SQL2RW_from);
		translation_spec();
		astFactory.addASTChild(currentAST, returnAST);
		translation_def_AST = (AST)currentAST.root;
		returnAST = translation_def_AST;
	}

	public final void assertion_def() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST assertion_def_AST = null;

		AST tmp72_AST = null;
		tmp72_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp72_AST);
		match(SQL2RW_create);
		AST tmp73_AST = null;
		tmp73_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp73_AST);
		match(SQL2RW_assertion);
		constraint_name();
		astFactory.addASTChild(currentAST, returnAST);
		assertion_check();
		astFactory.addASTChild(currentAST, returnAST);
		{
		if ((LA(1)==SQL2RW_deferrable||LA(1)==SQL2RW_initially||LA(1)==SQL2RW_not)) {
			constraint_attributes();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else if ((LA(1)==SQL2RW_create||LA(1)==SQL2RW_grant||LA(1)==SEMICOLON)) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		assertion_def_AST = (AST)currentAST.root;
		returnAST = assertion_def_AST;
	}

	public final void comment_def() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST comment_def_AST = null;
		System.out.println( "matched comment_def" );

		AST tmp74_AST = null;
		tmp74_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp74_AST);
		match(SQL2W_comment);
		AST tmp75_AST = null;
		tmp75_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp75_AST);
		match(SQL2RW_on);
		AST tmp76_AST = null;
		tmp76_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp76_AST);
		match(SQL2RW_table);
		table_name();
		astFactory.addASTChild(currentAST, returnAST);
		AST tmp77_AST = null;
		tmp77_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp77_AST);
		match(SQL2RW_is);
		{
		int _cnt98=0;
		_loop98:
		do {
			if ((LA(1)==ANY_CHAR)) {
				AST tmp78_AST = null;
				tmp78_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp78_AST);
				match(ANY_CHAR);
			}
			else {
				if ( _cnt98>=1 ) { break _loop98; } else {throw new NoViableAltException(LT(1), getFilename());}
			}

			_cnt98++;
		} while (true);
		}
		comment_def_AST = (AST)currentAST.root;
		returnAST = comment_def_AST;
	}

	public final void drop_schema_stmt() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST drop_schema_stmt_AST = null;

		AST tmp79_AST = null;
		tmp79_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp79_AST);
		match(SQL2RW_drop);
		AST tmp80_AST = null;
		tmp80_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp80_AST);
		match(SQL2RW_schema);
		qualified_name();
		astFactory.addASTChild(currentAST, returnAST);
		drop_behavior();
		astFactory.addASTChild(currentAST, returnAST);
		drop_schema_stmt_AST = (AST)currentAST.root;
		returnAST = drop_schema_stmt_AST;
	}

	public final void alter_table_stmt() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST alter_table_stmt_AST = null;

		AST tmp81_AST = null;
		tmp81_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp81_AST);
		match(SQL2RW_alter);
		AST tmp82_AST = null;
		tmp82_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp82_AST);
		match(SQL2RW_table);
		table_name();
		astFactory.addASTChild(currentAST, returnAST);
		if ( inputState.guessing==0 ) {
			System.out.println("Alter table statement for " +  returnAST.toString()); builder.setEntityTo( returnAST.toString());
		}
		alter_table_action();
		astFactory.addASTChild(currentAST, returnAST);
		alter_table_stmt_AST = (AST)currentAST.root;
		returnAST = alter_table_stmt_AST;
	}

	public final void drop_table_stmt() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST drop_table_stmt_AST = null;

		AST tmp83_AST = null;
		tmp83_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp83_AST);
		match(SQL2RW_drop);
		AST tmp84_AST = null;
		tmp84_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp84_AST);
		match(SQL2RW_table);
		table_name();
		astFactory.addASTChild(currentAST, returnAST);
		drop_behavior();
		astFactory.addASTChild(currentAST, returnAST);
		drop_table_stmt_AST = (AST)currentAST.root;
		returnAST = drop_table_stmt_AST;
	}

	public final void drop_view_stmt() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST drop_view_stmt_AST = null;

		AST tmp85_AST = null;
		tmp85_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp85_AST);
		match(SQL2RW_drop);
		AST tmp86_AST = null;
		tmp86_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp86_AST);
		match(SQL2RW_view);
		table_name();
		astFactory.addASTChild(currentAST, returnAST);
		drop_behavior();
		astFactory.addASTChild(currentAST, returnAST);
		drop_view_stmt_AST = (AST)currentAST.root;
		returnAST = drop_view_stmt_AST;
	}

	public final void revoke_stmt() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST revoke_stmt_AST = null;

		AST tmp87_AST = null;
		tmp87_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp87_AST);
		match(SQL2RW_revoke);
		{
		if ((LA(1)==SQL2RW_grant)) {
			AST tmp88_AST = null;
			tmp88_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp88_AST);
			match(SQL2RW_grant);
			AST tmp89_AST = null;
			tmp89_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp89_AST);
			match(SQL2RW_option);
			AST tmp90_AST = null;
			tmp90_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp90_AST);
			match(SQL2RW_for);
		}
		else if ((LA(1)==SQL2RW_all||LA(1)==SQL2RW_delete||LA(1)==SQL2RW_insert||LA(1)==SQL2RW_references||LA(1)==SQL2RW_select||LA(1)==SQL2RW_update||LA(1)==SQL2RW_usage)) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		{
		if ((LA(1)==SQL2RW_all||LA(1)==SQL2RW_delete||LA(1)==SQL2RW_insert||LA(1)==SQL2RW_references||LA(1)==SQL2RW_select||LA(1)==SQL2RW_update)) {
			privileges();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp91_AST = null;
			tmp91_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp91_AST);
			match(SQL2RW_on);
			{
			if ((LA(1)==SQL2RW_table)) {
				AST tmp92_AST = null;
				tmp92_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp92_AST);
				match(SQL2RW_table);
			}
			else if ((_tokenSet_11.member(LA(1)))) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}

			}
			table_name();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else if ((LA(1)==SQL2RW_usage)) {
			AST tmp93_AST = null;
			tmp93_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp93_AST);
			match(SQL2RW_usage);
			AST tmp94_AST = null;
			tmp94_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp94_AST);
			match(SQL2RW_on);
			object_name();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		AST tmp95_AST = null;
		tmp95_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp95_AST);
		match(SQL2RW_from);
		grantee();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop231:
		do {
			if ((LA(1)==COMMA)) {
				AST tmp96_AST = null;
				tmp96_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp96_AST);
				match(COMMA);
				grantee();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop231;
			}

		} while (true);
		}
		drop_behavior();
		astFactory.addASTChild(currentAST, returnAST);
		revoke_stmt_AST = (AST)currentAST.root;
		returnAST = revoke_stmt_AST;
	}

	public final void alter_domain_stmt() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST alter_domain_stmt_AST = null;

		AST tmp97_AST = null;
		tmp97_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp97_AST);
		match(SQL2RW_alter);
		AST tmp98_AST = null;
		tmp98_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp98_AST);
		match(SQL2RW_domain);
		domain_name();
		astFactory.addASTChild(currentAST, returnAST);
		alter_domain_action();
		astFactory.addASTChild(currentAST, returnAST);
		alter_domain_stmt_AST = (AST)currentAST.root;
		returnAST = alter_domain_stmt_AST;
	}

	public final void drop_domain_stmt() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST drop_domain_stmt_AST = null;

		AST tmp99_AST = null;
		tmp99_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp99_AST);
		match(SQL2RW_drop);
		AST tmp100_AST = null;
		tmp100_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp100_AST);
		match(SQL2RW_domain);
		domain_name();
		astFactory.addASTChild(currentAST, returnAST);
		drop_behavior();
		astFactory.addASTChild(currentAST, returnAST);
		drop_domain_stmt_AST = (AST)currentAST.root;
		returnAST = drop_domain_stmt_AST;
	}

	public final void drop_char_set_stmt() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST drop_char_set_stmt_AST = null;

		AST tmp101_AST = null;
		tmp101_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp101_AST);
		match(SQL2RW_drop);
		AST tmp102_AST = null;
		tmp102_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp102_AST);
		match(SQL2RW_character);
		AST tmp103_AST = null;
		tmp103_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp103_AST);
		match(SQL2RW_set);
		char_set_name();
		astFactory.addASTChild(currentAST, returnAST);
		drop_char_set_stmt_AST = (AST)currentAST.root;
		returnAST = drop_char_set_stmt_AST;
	}

	public final void drop_collation_stmt() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST drop_collation_stmt_AST = null;

		AST tmp104_AST = null;
		tmp104_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp104_AST);
		match(SQL2RW_drop);
		AST tmp105_AST = null;
		tmp105_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp105_AST);
		match(SQL2RW_collation);
		collation_name();
		astFactory.addASTChild(currentAST, returnAST);
		drop_collation_stmt_AST = (AST)currentAST.root;
		returnAST = drop_collation_stmt_AST;
	}

	public final void drop_translation_stmt() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST drop_translation_stmt_AST = null;

		AST tmp106_AST = null;
		tmp106_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp106_AST);
		match(SQL2RW_drop);
		AST tmp107_AST = null;
		tmp107_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp107_AST);
		match(SQL2RW_translation);
		translation_name();
		astFactory.addASTChild(currentAST, returnAST);
		drop_translation_stmt_AST = (AST)currentAST.root;
		returnAST = drop_translation_stmt_AST;
	}

	public final void drop_assertion_stmt() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST drop_assertion_stmt_AST = null;

		AST tmp108_AST = null;
		tmp108_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp108_AST);
		match(SQL2RW_drop);
		AST tmp109_AST = null;
		tmp109_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp109_AST);
		match(SQL2RW_assertion);
		constraint_name();
		astFactory.addASTChild(currentAST, returnAST);
		drop_assertion_stmt_AST = (AST)currentAST.root;
		returnAST = drop_assertion_stmt_AST;
	}

	public final void table_name() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST table_name_AST = null;

		if ((_tokenSet_5.member(LA(1)))) {
			qualified_name();
			astFactory.addASTChild(currentAST, returnAST);
			table_name_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_module)) {
			qualified_local_table_name();
			astFactory.addASTChild(currentAST, returnAST);
			table_name_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = table_name_AST;
	}

	public final void commit_stmt() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST commit_stmt_AST = null;

		AST tmp110_AST = null;
		tmp110_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp110_AST);
		match(SQL2RW_commit);
		{
		if ((LA(1)==SQL2RW_work)) {
			AST tmp111_AST = null;
			tmp111_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp111_AST);
			match(SQL2RW_work);
		}
		else if ((LA(1)==SEMICOLON)) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		commit_stmt_AST = (AST)currentAST.root;
		returnAST = commit_stmt_AST;
	}

	public final void rollback_stmt() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST rollback_stmt_AST = null;

		AST tmp112_AST = null;
		tmp112_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp112_AST);
		match(SQL2RW_rollback);
		{
		if ((LA(1)==SQL2RW_work)) {
			AST tmp113_AST = null;
			tmp113_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp113_AST);
			match(SQL2RW_work);
		}
		else if ((LA(1)==SEMICOLON)) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		rollback_stmt_AST = (AST)currentAST.root;
		returnAST = rollback_stmt_AST;
	}

	public final void set_constraints_mode_stmt() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST set_constraints_mode_stmt_AST = null;

		AST tmp114_AST = null;
		tmp114_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp114_AST);
		match(SQL2RW_set);
		AST tmp115_AST = null;
		tmp115_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp115_AST);
		match(SQL2RW_constraints);
		constraint_name_list();
		astFactory.addASTChild(currentAST, returnAST);
		{
		if ((LA(1)==SQL2RW_deferred)) {
			AST tmp116_AST = null;
			tmp116_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp116_AST);
			match(SQL2RW_deferred);
		}
		else if ((LA(1)==SQL2RW_immediate)) {
			AST tmp117_AST = null;
			tmp117_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp117_AST);
			match(SQL2RW_immediate);
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		set_constraints_mode_stmt_AST = (AST)currentAST.root;
		returnAST = set_constraints_mode_stmt_AST;
	}

	public final void set_transaction_stmt() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST set_transaction_stmt_AST = null;

		AST tmp118_AST = null;
		tmp118_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp118_AST);
		match(SQL2RW_set);
		AST tmp119_AST = null;
		tmp119_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp119_AST);
		match(SQL2RW_transaction);
		transaction_mode();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop250:
		do {
			if ((LA(1)==COMMA)) {
				AST tmp120_AST = null;
				tmp120_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp120_AST);
				match(COMMA);
				transaction_mode();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop250;
			}

		} while (true);
		}
		set_transaction_stmt_AST = (AST)currentAST.root;
		returnAST = set_transaction_stmt_AST;
	}

	public final void connect_stmt() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST connect_stmt_AST = null;

		AST tmp121_AST = null;
		tmp121_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp121_AST);
		match(SQL2RW_connect);
		AST tmp122_AST = null;
		tmp122_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp122_AST);
		match(SQL2RW_to);
		connection_target();
		astFactory.addASTChild(currentAST, returnAST);
		connect_stmt_AST = (AST)currentAST.root;
		returnAST = connect_stmt_AST;
	}

	public final void disconnect_stmt() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST disconnect_stmt_AST = null;

		AST tmp123_AST = null;
		tmp123_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp123_AST);
		match(SQL2RW_disconnect);
		{
		if ((_tokenSet_13.member(LA(1)))) {
			connection_object();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else if ((LA(1)==SQL2RW_all)) {
			AST tmp124_AST = null;
			tmp124_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp124_AST);
			match(SQL2RW_all);
		}
		else if ((LA(1)==SQL2RW_current)) {
			AST tmp125_AST = null;
			tmp125_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp125_AST);
			match(SQL2RW_current);
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		disconnect_stmt_AST = (AST)currentAST.root;
		returnAST = disconnect_stmt_AST;
	}

	public final void set_connection_stmt() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST set_connection_stmt_AST = null;

		AST tmp126_AST = null;
		tmp126_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp126_AST);
		match(SQL2RW_set);
		AST tmp127_AST = null;
		tmp127_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp127_AST);
		match(SQL2RW_connection);
		connection_object();
		astFactory.addASTChild(currentAST, returnAST);
		set_connection_stmt_AST = (AST)currentAST.root;
		returnAST = set_connection_stmt_AST;
	}

	public final void set_catalog_stmt() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST set_catalog_stmt_AST = null;

		AST tmp128_AST = null;
		tmp128_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp128_AST);
		match(SQL2RW_set);
		AST tmp129_AST = null;
		tmp129_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp129_AST);
		match(SQL2RW_catalog);
		value_spec();
		astFactory.addASTChild(currentAST, returnAST);
		set_catalog_stmt_AST = (AST)currentAST.root;
		returnAST = set_catalog_stmt_AST;
	}

	public final void set_schema_stmt() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST set_schema_stmt_AST = null;

		AST tmp130_AST = null;
		tmp130_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp130_AST);
		match(SQL2RW_set);
		AST tmp131_AST = null;
		tmp131_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp131_AST);
		match(SQL2RW_schema);
		value_spec();
		astFactory.addASTChild(currentAST, returnAST);
		set_schema_stmt_AST = (AST)currentAST.root;
		returnAST = set_schema_stmt_AST;
	}

	public final void set_names_stmt() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST set_names_stmt_AST = null;

		AST tmp132_AST = null;
		tmp132_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp132_AST);
		match(SQL2RW_set);
		AST tmp133_AST = null;
		tmp133_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp133_AST);
		match(SQL2RW_names);
		value_spec();
		astFactory.addASTChild(currentAST, returnAST);
		set_names_stmt_AST = (AST)currentAST.root;
		returnAST = set_names_stmt_AST;
	}

	public final void set_session_author_id_stmt() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST set_session_author_id_stmt_AST = null;

		AST tmp134_AST = null;
		tmp134_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp134_AST);
		match(SQL2RW_set);
		AST tmp135_AST = null;
		tmp135_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp135_AST);
		match(SQL2RW_session);
		AST tmp136_AST = null;
		tmp136_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp136_AST);
		match(SQL2RW_authorization);
		value_spec();
		astFactory.addASTChild(currentAST, returnAST);
		set_session_author_id_stmt_AST = (AST)currentAST.root;
		returnAST = set_session_author_id_stmt_AST;
	}

	public final void set_local_time_zone_stmt() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST set_local_time_zone_stmt_AST = null;

		AST tmp137_AST = null;
		tmp137_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp137_AST);
		match(SQL2RW_set);
		AST tmp138_AST = null;
		tmp138_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp138_AST);
		match(SQL2RW_time);
		AST tmp139_AST = null;
		tmp139_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp139_AST);
		match(SQL2RW_zone);
		{
		if ((_tokenSet_14.member(LA(1)))) {
			interval_value_exp();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else if ((LA(1)==SQL2RW_local)) {
			AST tmp140_AST = null;
			tmp140_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp140_AST);
			match(SQL2RW_local);
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		set_local_time_zone_stmt_AST = (AST)currentAST.root;
		returnAST = set_local_time_zone_stmt_AST;
	}

	public final void prepare_stmt() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST prepare_stmt_AST = null;

		AST tmp141_AST = null;
		tmp141_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp141_AST);
		match(SQL2RW_prepare);
		sql_stmt_name();
		astFactory.addASTChild(currentAST, returnAST);
		AST tmp142_AST = null;
		tmp142_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp142_AST);
		match(SQL2RW_from);
		sql_stmt_variable();
		astFactory.addASTChild(currentAST, returnAST);
		prepare_stmt_AST = (AST)currentAST.root;
		returnAST = prepare_stmt_AST;
	}

	public final void deallocate_prepared_stmt() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST deallocate_prepared_stmt_AST = null;

		AST tmp143_AST = null;
		tmp143_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp143_AST);
		match(SQL2RW_deallocate);
		AST tmp144_AST = null;
		tmp144_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp144_AST);
		match(SQL2RW_prepare);
		sql_stmt_name();
		astFactory.addASTChild(currentAST, returnAST);
		deallocate_prepared_stmt_AST = (AST)currentAST.root;
		returnAST = deallocate_prepared_stmt_AST;
	}

	public final void describe_stmt() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST describe_stmt_AST = null;

		if ((LA(1)==SQL2RW_describe) && (LA(2)==SQL2RW_input)) {
			AST tmp145_AST = null;
			tmp145_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp145_AST);
			match(SQL2RW_describe);
			AST tmp146_AST = null;
			tmp146_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp146_AST);
			match(SQL2RW_input);
			sql_stmt_name();
			astFactory.addASTChild(currentAST, returnAST);
			using_descriptor();
			astFactory.addASTChild(currentAST, returnAST);
			describe_stmt_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_describe) && (_tokenSet_15.member(LA(2)))) {
			AST tmp147_AST = null;
			tmp147_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp147_AST);
			match(SQL2RW_describe);
			{
			if ((LA(1)==SQL2RW_output)) {
				AST tmp148_AST = null;
				tmp148_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp148_AST);
				match(SQL2RW_output);
			}
			else if ((_tokenSet_6.member(LA(1)))) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}

			}
			sql_stmt_name();
			astFactory.addASTChild(currentAST, returnAST);
			using_descriptor();
			astFactory.addASTChild(currentAST, returnAST);
			describe_stmt_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = describe_stmt_AST;
	}

	public final void execute_stmt() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST execute_stmt_AST = null;

		AST tmp149_AST = null;
		tmp149_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp149_AST);
		match(SQL2RW_execute);
		sql_stmt_name();
		astFactory.addASTChild(currentAST, returnAST);
		{
		boolean synPredMatched289 = false;
		if (((LA(1)==SQL2RW_into||LA(1)==SQL2RW_using) && (LA(2)==SQL2RW_sql||LA(2)==EMBDD_VARIABLE_NAME||LA(2)==COLON))) {
			int _m289 = mark();
			synPredMatched289 = true;
			inputState.guessing++;
			try {
				{
				match(SQL2RW_into);
				}
			}
			catch (RecognitionException pe) {
				synPredMatched289 = false;
			}
			rewind(_m289);
inputState.guessing--;
		}
		if ( synPredMatched289 ) {
			using_clause();
			astFactory.addASTChild(currentAST, returnAST);
			{
			if ((LA(1)==SQL2RW_into||LA(1)==SQL2RW_using)) {
				using_clause();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else if ((LA(1)==SEMICOLON)) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}

			}
		}
		else if ((LA(1)==SQL2RW_into||LA(1)==SQL2RW_using||LA(1)==SEMICOLON) && (LA(2)==EOF||LA(2)==SQL2RW_sql||LA(2)==EMBDD_VARIABLE_NAME||LA(2)==COLON||LA(2)==SEMICOLON)) {
			{
			if ((LA(1)==SQL2RW_into||LA(1)==SQL2RW_using)) {
				using_clause();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else if ((LA(1)==SEMICOLON)) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}

			}
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		execute_stmt_AST = (AST)currentAST.root;
		returnAST = execute_stmt_AST;
	}

	public final void execute_immediate_stmt() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST execute_immediate_stmt_AST = null;

		AST tmp150_AST = null;
		tmp150_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp150_AST);
		match(SQL2RW_execute);
		AST tmp151_AST = null;
		tmp151_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp151_AST);
		match(SQL2RW_immediate);
		sql_stmt_variable();
		astFactory.addASTChild(currentAST, returnAST);
		execute_immediate_stmt_AST = (AST)currentAST.root;
		returnAST = execute_immediate_stmt_AST;
	}

	public final void allocate_cursor_stmt() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST allocate_cursor_stmt_AST = null;

		AST tmp152_AST = null;
		tmp152_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp152_AST);
		match(SQL2RW_allocate);
		extended_cursor_name();
		astFactory.addASTChild(currentAST, returnAST);
		{
		if ((LA(1)==SQL2RW_insensitive)) {
			AST tmp153_AST = null;
			tmp153_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp153_AST);
			match(SQL2RW_insensitive);
		}
		else if ((LA(1)==SQL2RW_cursor||LA(1)==SQL2RW_scroll)) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		{
		if ((LA(1)==SQL2RW_scroll)) {
			AST tmp154_AST = null;
			tmp154_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp154_AST);
			match(SQL2RW_scroll);
		}
		else if ((LA(1)==SQL2RW_cursor)) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		AST tmp155_AST = null;
		tmp155_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp155_AST);
		match(SQL2RW_cursor);
		AST tmp156_AST = null;
		tmp156_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp156_AST);
		match(SQL2RW_for);
		extended_stmt_name();
		astFactory.addASTChild(currentAST, returnAST);
		allocate_cursor_stmt_AST = (AST)currentAST.root;
		returnAST = allocate_cursor_stmt_AST;
	}

	public final void fetch_stmt() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST fetch_stmt_AST = null;

		AST tmp157_AST = null;
		tmp157_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp157_AST);
		match(SQL2RW_fetch);
		{
		if ((LA(1)==SQL2RW_absolute||LA(1)==SQL2RW_first||LA(1)==SQL2RW_from||LA(1)==SQL2RW_last||LA(1)==SQL2RW_next||LA(1)==SQL2RW_prior||LA(1)==SQL2RW_relative)) {
			{
			if ((LA(1)==SQL2RW_absolute||LA(1)==SQL2RW_first||LA(1)==SQL2RW_last||LA(1)==SQL2RW_next||LA(1)==SQL2RW_prior||LA(1)==SQL2RW_relative)) {
				fetch_orientation();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else if ((LA(1)==SQL2RW_from)) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}

			}
			AST tmp158_AST = null;
			tmp158_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp158_AST);
			match(SQL2RW_from);
		}
		else if ((_tokenSet_6.member(LA(1)))) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		dyn_cursor_name();
		astFactory.addASTChild(currentAST, returnAST);
		{
		boolean synPredMatched301 = false;
		if (((LA(1)==SQL2RW_into||LA(1)==SQL2RW_using))) {
			int _m301 = mark();
			synPredMatched301 = true;
			inputState.guessing++;
			try {
				{
				match(SQL2RW_into);
				}
			}
			catch (RecognitionException pe) {
				synPredMatched301 = false;
			}
			rewind(_m301);
inputState.guessing--;
		}
		if ( synPredMatched301 ) {
			using_clause();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else if (((LA(1)==SEMICOLON))&&(false)) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		fetch_stmt_AST = (AST)currentAST.root;
		returnAST = fetch_stmt_AST;
	}

	public final void open_stmt() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST open_stmt_AST = null;

		AST tmp159_AST = null;
		tmp159_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp159_AST);
		match(SQL2RW_open);
		dyn_cursor_name();
		astFactory.addASTChild(currentAST, returnAST);
		{
		if ((LA(1)==SQL2RW_into||LA(1)==SQL2RW_using)) {
			using_clause();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else if ((LA(1)==SEMICOLON)) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		open_stmt_AST = (AST)currentAST.root;
		returnAST = open_stmt_AST;
	}

	public final void close_stmt() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST close_stmt_AST = null;

		AST tmp160_AST = null;
		tmp160_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp160_AST);
		match(SQL2RW_close);
		dyn_cursor_name();
		astFactory.addASTChild(currentAST, returnAST);
		{
		if ((LA(1)==SQL2RW_into||LA(1)==SQL2RW_using)) {
			using_clause();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else if ((LA(1)==SEMICOLON)) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		close_stmt_AST = (AST)currentAST.root;
		returnAST = close_stmt_AST;
	}

	public final void schema_name_clause() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST schema_name_clause_AST = null;

		if ((_tokenSet_5.member(LA(1)))) {
			schema_name();
			astFactory.addASTChild(currentAST, returnAST);
			{
			if ((LA(1)==SQL2RW_authorization)) {
				AST tmp161_AST = null;
				tmp161_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp161_AST);
				match(SQL2RW_authorization);
				author_id();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else if ((LA(1)==SQL2RW_create||LA(1)==SQL2RW_default||LA(1)==SQL2RW_grant||LA(1)==SEMICOLON)) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}

			}
			schema_name_clause_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_authorization)) {
			AST tmp162_AST = null;
			tmp162_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp162_AST);
			match(SQL2RW_authorization);
			author_id();
			astFactory.addASTChild(currentAST, returnAST);
			schema_name_clause_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = schema_name_clause_AST;
	}

	public final void schema_char_set_spec() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST schema_char_set_spec_AST = null;

		AST tmp163_AST = null;
		tmp163_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp163_AST);
		match(SQL2RW_default);
		AST tmp164_AST = null;
		tmp164_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp164_AST);
		match(SQL2RW_character);
		AST tmp165_AST = null;
		tmp165_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp165_AST);
		match(SQL2RW_set);
		char_set_name();
		astFactory.addASTChild(currentAST, returnAST);
		schema_char_set_spec_AST = (AST)currentAST.root;
		returnAST = schema_char_set_spec_AST;
	}

	public final void schema_element() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST schema_element_AST = null;

		if ((LA(1)==SQL2RW_create) && (LA(2)==SQL2RW_global||LA(2)==SQL2RW_local||LA(2)==SQL2RW_table)) {
			table_def();
			astFactory.addASTChild(currentAST, returnAST);
			schema_element_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_create) && (LA(2)==SQL2RW_view)) {
			view_def();
			astFactory.addASTChild(currentAST, returnAST);
			schema_element_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_grant)) {
			grant_stmt();
			astFactory.addASTChild(currentAST, returnAST);
			schema_element_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_create) && (LA(2)==SQL2RW_domain)) {
			domain_def();
			astFactory.addASTChild(currentAST, returnAST);
			schema_element_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_create) && (LA(2)==SQL2RW_assertion)) {
			assertion_def();
			astFactory.addASTChild(currentAST, returnAST);
			schema_element_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_create) && (LA(2)==SQL2RW_character)) {
			char_set_def();
			astFactory.addASTChild(currentAST, returnAST);
			schema_element_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_create) && (LA(2)==SQL2RW_collation)) {
			collation_def();
			astFactory.addASTChild(currentAST, returnAST);
			schema_element_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_create) && (LA(2)==SQL2RW_translation)) {
			translation_def();
			astFactory.addASTChild(currentAST, returnAST);
			schema_element_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_create) && (LA(2)==LITERAL_rule)) {
			rule_def();
			astFactory.addASTChild(currentAST, returnAST);
			schema_element_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = schema_element_AST;
	}

	public final void schema_name() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST schema_name_AST = null;

		id();
		astFactory.addASTChild(currentAST, returnAST);
		{
		if ((LA(1)==PERIOD)) {
			AST tmp166_AST = null;
			tmp166_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp166_AST);
			match(PERIOD);
			id();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else if ((LA(1)==SQL2RW_authorization||LA(1)==SQL2RW_create||LA(1)==SQL2RW_default||LA(1)==SQL2RW_grant||LA(1)==SEMICOLON)) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		schema_name_AST = (AST)currentAST.root;
		returnAST = schema_name_AST;
	}

	public final void author_id() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST author_id_AST = null;

		id();
		astFactory.addASTChild(currentAST, returnAST);
		author_id_AST = (AST)currentAST.root;
		returnAST = author_id_AST;
	}

	public final void char_set_name() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST char_set_name_AST = null;

		id();
		astFactory.addASTChild(currentAST, returnAST);
		{
		if ((LA(1)==PERIOD)) {
			AST tmp167_AST = null;
			tmp167_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp167_AST);
			match(PERIOD);
			id();
			astFactory.addASTChild(currentAST, returnAST);
			{
			if ((LA(1)==PERIOD)) {
				AST tmp168_AST = null;
				tmp168_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp168_AST);
				match(PERIOD);
				id();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else if ((_tokenSet_16.member(LA(1)))) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}

			}
		}
		else if ((_tokenSet_16.member(LA(1)))) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		char_set_name_AST = (AST)currentAST.root;
		returnAST = char_set_name_AST;
	}

	public final void rule_def() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST rule_def_AST = null;

		AST tmp169_AST = null;
		tmp169_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp169_AST);
		match(SQL2RW_create);
		AST tmp170_AST = null;
		tmp170_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp170_AST);
		match(LITERAL_rule);
		{
		_loop114:
		do {
			if ((_tokenSet_17.member(LA(1)))) {
				{
				AST tmp171_AST = null;
				tmp171_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp171_AST);
				match(_tokenSet_17);
				}
			}
			else {
				break _loop114;
			}

		} while (true);
		}
		AST tmp172_AST = null;
		tmp172_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp172_AST);
		match(SEMICOLON);
		rule_def_AST = (AST)currentAST.root;
		returnAST = rule_def_AST;
	}

	public final void table_element_list() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST table_element_list_AST = null;

		AST tmp173_AST = null;
		tmp173_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp173_AST);
		match(LEFT_PAREN);
		table_element();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop121:
		do {
			if ((LA(1)==COMMA)) {
				AST tmp174_AST = null;
				tmp174_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp174_AST);
				match(COMMA);
				table_element();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop121;
			}

		} while (true);
		}
		AST tmp175_AST = null;
		tmp175_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp175_AST);
		match(RIGHT_PAREN);
		table_element_list_AST = (AST)currentAST.root;
		returnAST = table_element_list_AST;
	}

	public final void on_commit_clause() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST on_commit_clause_AST = null;

		AST tmp176_AST = null;
		tmp176_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp176_AST);
		match(SQL2RW_on);
		AST tmp177_AST = null;
		tmp177_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp177_AST);
		match(SQL2RW_commit);
		{
		if ((LA(1)==SQL2RW_delete)) {
			AST tmp178_AST = null;
			tmp178_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp178_AST);
			match(SQL2RW_delete);
		}
		else if ((LA(1)==SQL2RW_preserve)) {
			AST tmp179_AST = null;
			tmp179_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp179_AST);
			match(SQL2RW_preserve);
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		AST tmp180_AST = null;
		tmp180_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp180_AST);
		match(SQL2RW_rows);
		on_commit_clause_AST = (AST)currentAST.root;
		returnAST = on_commit_clause_AST;
	}

	public final void table_element() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST table_element_AST = null;

		if ((LA(1)==SQL2RW_check||LA(1)==SQL2RW_constraint||LA(1)==SQL2RW_foreign||LA(1)==SQL2RW_primary||LA(1)==SQL2RW_unique)) {
			table_constraint_def();
			astFactory.addASTChild(currentAST, returnAST);
			table_element_AST = (AST)currentAST.root;
		}
		else if ((_tokenSet_5.member(LA(1)))) {
			column_def();
			astFactory.addASTChild(currentAST, returnAST);
			table_element_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = table_element_AST;
	}

	public final void table_constraint_def() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST table_constraint_def_AST = null;

		{
		if ((LA(1)==SQL2RW_constraint)) {
			constraint_name_def();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else if ((LA(1)==SQL2RW_check||LA(1)==SQL2RW_foreign||LA(1)==SQL2RW_primary||LA(1)==SQL2RW_unique)) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		table_constraint();
		astFactory.addASTChild(currentAST, returnAST);
		{
		if ((LA(1)==SQL2RW_deferrable||LA(1)==SQL2RW_initially||LA(1)==SQL2RW_not)) {
			constraint_attributes();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else if ((LA(1)==RIGHT_PAREN||LA(1)==COMMA||LA(1)==SEMICOLON)) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		table_constraint_def_AST = (AST)currentAST.root;
		returnAST = table_constraint_def_AST;
	}

	public final void column_def() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST column_def_AST = null;

		column_name();
		astFactory.addASTChild(currentAST, returnAST);
		if ( inputState.guessing==0 ) {
			builder.addAttributeToLastEntity( returnAST.toString() );
		}
		{
		if ((_tokenSet_12.member(LA(1))) && (_tokenSet_18.member(LA(2)))) {
			data_type();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else if ((_tokenSet_5.member(LA(1))) && (_tokenSet_19.member(LA(2)))) {
			domain_name();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		{
		if ((LA(1)==SQL2RW_default)) {
			default_clause();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else if ((_tokenSet_20.member(LA(1)))) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		{
		_loop159:
		do {
			if ((LA(1)==SQL2RW_check||LA(1)==SQL2RW_constraint||LA(1)==SQL2RW_not||LA(1)==SQL2RW_null||LA(1)==SQL2RW_primary||LA(1)==SQL2RW_references||LA(1)==SQL2RW_unique)) {
				column_constraint_def();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop159;
			}

		} while (true);
		}
		{
		if ((LA(1)==SQL2RW_collate)) {
			collate_clause();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else if ((LA(1)==RIGHT_PAREN||LA(1)==COMMA||LA(1)==SEMICOLON)) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		column_def_AST = (AST)currentAST.root;
		returnAST = column_def_AST;
	}

	public final void constraint_name_def() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST constraint_name_def_AST = null;

		AST tmp181_AST = null;
		tmp181_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp181_AST);
		match(SQL2RW_constraint);
		constraint_name();
		astFactory.addASTChild(currentAST, returnAST);
		constraint_name_def_AST = (AST)currentAST.root;
		returnAST = constraint_name_def_AST;
	}

	public final void table_constraint() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST table_constraint_AST = null;

		if ((LA(1)==SQL2RW_primary||LA(1)==SQL2RW_unique)) {
			unique_constraint_def();
			astFactory.addASTChild(currentAST, returnAST);
			table_constraint_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_foreign)) {
			ref_constraint_def();
			astFactory.addASTChild(currentAST, returnAST);
			table_constraint_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_check)) {
			check_constraint_def();
			astFactory.addASTChild(currentAST, returnAST);
			table_constraint_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = table_constraint_AST;
	}

	public final void constraint_attributes() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST constraint_attributes_AST = null;

		if ((LA(1)==SQL2RW_initially)) {
			constraint_check_time();
			astFactory.addASTChild(currentAST, returnAST);
			{
			if ((LA(1)==SQL2RW_deferrable||LA(1)==SQL2RW_not) && (_tokenSet_21.member(LA(2)))) {
				{
				if ((LA(1)==SQL2RW_not)) {
					AST tmp182_AST = null;
					tmp182_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp182_AST);
					match(SQL2RW_not);
				}
				else if ((LA(1)==SQL2RW_deferrable)) {
				}
				else {
					throw new NoViableAltException(LT(1), getFilename());
				}

				}
				AST tmp183_AST = null;
				tmp183_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp183_AST);
				match(SQL2RW_deferrable);
			}
			else if ((_tokenSet_22.member(LA(1))) && (_tokenSet_23.member(LA(2)))) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}

			}
			constraint_attributes_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_deferrable||LA(1)==SQL2RW_not)) {
			{
			if ((LA(1)==SQL2RW_not)) {
				AST tmp184_AST = null;
				tmp184_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp184_AST);
				match(SQL2RW_not);
			}
			else if ((LA(1)==SQL2RW_deferrable)) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}

			}
			AST tmp185_AST = null;
			tmp185_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp185_AST);
			match(SQL2RW_deferrable);
			{
			if ((LA(1)==SQL2RW_initially)) {
				constraint_check_time();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else if ((_tokenSet_22.member(LA(1)))) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}

			}
			constraint_attributes_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = constraint_attributes_AST;
	}

	public final void constraint_name() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST constraint_name_AST = null;

		qualified_name();
		astFactory.addASTChild(currentAST, returnAST);
		constraint_name_AST = (AST)currentAST.root;
		returnAST = constraint_name_AST;
	}

	public final void constraint_check_time() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST constraint_check_time_AST = null;

		AST tmp186_AST = null;
		tmp186_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp186_AST);
		match(SQL2RW_initially);
		{
		if ((LA(1)==SQL2RW_deferred)) {
			AST tmp187_AST = null;
			tmp187_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp187_AST);
			match(SQL2RW_deferred);
		}
		else if ((LA(1)==SQL2RW_immediate)) {
			AST tmp188_AST = null;
			tmp188_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp188_AST);
			match(SQL2RW_immediate);
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		constraint_check_time_AST = (AST)currentAST.root;
		returnAST = constraint_check_time_AST;
	}

	public final void unique_constraint_def() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST unique_constraint_def_AST = null;

		unique_spec();
		astFactory.addASTChild(currentAST, returnAST);
		{
		if ((LA(1)==LEFT_PAREN)) {
			AST tmp189_AST = null;
			tmp189_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp189_AST);
			match(LEFT_PAREN);
			column_name_list();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp190_AST = null;
			tmp190_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp190_AST);
			match(RIGHT_PAREN);
		}
		else if ((LA(1)==SQL2RW_deferrable||LA(1)==SQL2RW_initially||LA(1)==SQL2RW_not||LA(1)==SQL2RW_using||LA(1)==RIGHT_PAREN||LA(1)==COMMA||LA(1)==SEMICOLON)) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		{
		if ((LA(1)==SQL2RW_using)) {
			AST tmp191_AST = null;
			tmp191_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp191_AST);
			match(SQL2RW_using);
			AST tmp192_AST = null;
			tmp192_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp192_AST);
			match(LITERAL_index);
			AST tmp193_AST = null;
			tmp193_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp193_AST);
			match(LEFT_PAREN);
			{
			_loop142:
			do {
				if ((_tokenSet_24.member(LA(1)))) {
					AST tmp194_AST = null;
					tmp194_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp194_AST);
					matchNot(RIGHT_PAREN);
				}
				else {
					break _loop142;
				}

			} while (true);
			}
			AST tmp195_AST = null;
			tmp195_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp195_AST);
			match(RIGHT_PAREN);
		}
		else if ((LA(1)==SQL2RW_deferrable||LA(1)==SQL2RW_initially||LA(1)==SQL2RW_not||LA(1)==RIGHT_PAREN||LA(1)==COMMA||LA(1)==SEMICOLON)) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		unique_constraint_def_AST = (AST)currentAST.root;
		returnAST = unique_constraint_def_AST;
	}

	public final void ref_constraint_def() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST ref_constraint_def_AST = null;

		AST tmp196_AST = null;
		tmp196_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp196_AST);
		match(SQL2RW_foreign);
		AST tmp197_AST = null;
		tmp197_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp197_AST);
		match(SQL2RW_key);
		AST tmp198_AST = null;
		tmp198_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp198_AST);
		match(LEFT_PAREN);
		column_name_list();
		astFactory.addASTChild(currentAST, returnAST);
		AST tmp199_AST = null;
		tmp199_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp199_AST);
		match(RIGHT_PAREN);
		refs_spec();
		astFactory.addASTChild(currentAST, returnAST);
		ref_constraint_def_AST = (AST)currentAST.root;
		returnAST = ref_constraint_def_AST;
	}

	public final void check_constraint_def() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST check_constraint_def_AST = null;

		AST tmp200_AST = null;
		tmp200_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp200_AST);
		match(SQL2RW_check);
		AST tmp201_AST = null;
		tmp201_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp201_AST);
		match(LEFT_PAREN);
		search_condition();
		astFactory.addASTChild(currentAST, returnAST);
		AST tmp202_AST = null;
		tmp202_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp202_AST);
		match(RIGHT_PAREN);
		check_constraint_def_AST = (AST)currentAST.root;
		returnAST = check_constraint_def_AST;
	}

	public final void search_condition() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST search_condition_AST = null;

		boolean_term();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop453:
		do {
			if ((LA(1)==SQL2RW_or)) {
				boolean_term_op();
				astFactory.addASTChild(currentAST, returnAST);
				boolean_term();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop453;
			}

		} while (true);
		}
		search_condition_AST = (AST)currentAST.root;
		returnAST = search_condition_AST;
	}

	public final void unique_spec() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST unique_spec_AST = null;

		if ((LA(1)==SQL2RW_unique)) {
			AST tmp203_AST = null;
			tmp203_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp203_AST);
			match(SQL2RW_unique);
			unique_spec_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_primary)) {
			AST tmp204_AST = null;
			tmp204_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp204_AST);
			match(SQL2RW_primary);
			AST tmp205_AST = null;
			tmp205_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp205_AST);
			match(SQL2RW_key);
			unique_spec_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = unique_spec_AST;
	}

	public final void column_name_list() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST column_name_list_AST = null;

		column_name();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop669:
		do {
			if ((LA(1)==COMMA)) {
				AST tmp206_AST = null;
				tmp206_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp206_AST);
				match(COMMA);
				column_name();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop669;
			}

		} while (true);
		}
		column_name_list_AST = (AST)currentAST.root;
		returnAST = column_name_list_AST;
	}

	public final void refs_spec() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST refs_spec_AST = null;

		AST tmp207_AST = null;
		tmp207_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp207_AST);
		match(SQL2RW_references);
		refd_table_and_columns();
		astFactory.addASTChild(currentAST, returnAST);
		{
		if ((LA(1)==SQL2RW_match)) {
			AST tmp208_AST = null;
			tmp208_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp208_AST);
			match(SQL2RW_match);
			{
			if ((LA(1)==SQL2RW_full)) {
				AST tmp209_AST = null;
				tmp209_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp209_AST);
				match(SQL2RW_full);
			}
			else if ((LA(1)==SQL2RW_partial)) {
				AST tmp210_AST = null;
				tmp210_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp210_AST);
				match(SQL2RW_partial);
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}

			}
		}
		else if ((_tokenSet_25.member(LA(1)))) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		{
		if ((LA(1)==SQL2RW_on)) {
			ref_triggered_action();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else if ((_tokenSet_26.member(LA(1)))) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		refs_spec_AST = (AST)currentAST.root;
		returnAST = refs_spec_AST;
	}

	public final void refd_table_and_columns() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST refd_table_and_columns_AST = null;

		table_name();
		astFactory.addASTChild(currentAST, returnAST);
		if ( inputState.guessing==0 ) {
			builder.addReferenceTo( returnAST.toString() );
		}
		{
		if ((LA(1)==LEFT_PAREN)) {
			AST tmp211_AST = null;
			tmp211_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp211_AST);
			match(LEFT_PAREN);
			column_name_list();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp212_AST = null;
			tmp212_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp212_AST);
			match(RIGHT_PAREN);
		}
		else if ((_tokenSet_27.member(LA(1)))) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		refd_table_and_columns_AST = (AST)currentAST.root;
		returnAST = refd_table_and_columns_AST;
	}

	public final void ref_triggered_action() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST ref_triggered_action_AST = null;

		if ((LA(1)==SQL2RW_on) && (LA(2)==SQL2RW_update)) {
			AST tmp213_AST = null;
			tmp213_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp213_AST);
			match(SQL2RW_on);
			AST tmp214_AST = null;
			tmp214_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp214_AST);
			match(SQL2RW_update);
			ref_action();
			astFactory.addASTChild(currentAST, returnAST);
			{
			if ((LA(1)==SQL2RW_on)) {
				AST tmp215_AST = null;
				tmp215_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp215_AST);
				match(SQL2RW_on);
				AST tmp216_AST = null;
				tmp216_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp216_AST);
				match(SQL2RW_delete);
				ref_action();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else if ((_tokenSet_26.member(LA(1)))) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}

			}
			ref_triggered_action_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_on) && (LA(2)==SQL2RW_delete)) {
			AST tmp217_AST = null;
			tmp217_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp217_AST);
			match(SQL2RW_on);
			AST tmp218_AST = null;
			tmp218_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp218_AST);
			match(SQL2RW_delete);
			ref_action();
			astFactory.addASTChild(currentAST, returnAST);
			{
			if ((LA(1)==SQL2RW_on)) {
				AST tmp219_AST = null;
				tmp219_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp219_AST);
				match(SQL2RW_on);
				AST tmp220_AST = null;
				tmp220_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp220_AST);
				match(SQL2RW_update);
				ref_action();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else if ((_tokenSet_26.member(LA(1)))) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}

			}
			ref_triggered_action_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = ref_triggered_action_AST;
	}

	public final void ref_action() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST ref_action_AST = null;

		if ((LA(1)==SQL2RW_cascade)) {
			AST tmp221_AST = null;
			tmp221_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp221_AST);
			match(SQL2RW_cascade);
			ref_action_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_set) && (LA(2)==SQL2RW_null)) {
			AST tmp222_AST = null;
			tmp222_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp222_AST);
			match(SQL2RW_set);
			AST tmp223_AST = null;
			tmp223_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp223_AST);
			match(SQL2RW_null);
			ref_action_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_set) && (LA(2)==SQL2RW_default)) {
			AST tmp224_AST = null;
			tmp224_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp224_AST);
			match(SQL2RW_set);
			AST tmp225_AST = null;
			tmp225_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp225_AST);
			match(SQL2RW_default);
			ref_action_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_no)) {
			AST tmp226_AST = null;
			tmp226_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp226_AST);
			match(SQL2RW_no);
			AST tmp227_AST = null;
			tmp227_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp227_AST);
			match(SQL2RW_action);
			ref_action_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = ref_action_AST;
	}

	public final void column_name() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST column_name_AST = null;

		id();
		astFactory.addASTChild(currentAST, returnAST);
		column_name_AST = (AST)currentAST.root;
		returnAST = column_name_AST;
	}

	public final void data_type() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST data_type_AST = null;

		switch ( LA(1)) {
		case SQL2RW_char:
		case SQL2RW_character:
		case SQL2RW_varchar:
		case LITERAL_text:
		{
			char_string_type();
			astFactory.addASTChild(currentAST, returnAST);
			{
			if ((LA(1)==SQL2RW_character)) {
				AST tmp228_AST = null;
				tmp228_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp228_AST);
				match(SQL2RW_character);
				AST tmp229_AST = null;
				tmp229_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp229_AST);
				match(SQL2RW_set);
				char_set_name();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else if ((_tokenSet_28.member(LA(1)))) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}

			}
			if ( inputState.guessing==0 ) {
				builder.setDomainOfLastAttribute( Importer.STRING );
			}
			data_type_AST = (AST)currentAST.root;
			break;
		}
		case SQL2RW_national:
		case SQL2RW_nchar:
		{
			national_char_string_type();
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				builder.setDomainOfLastAttribute( Importer.STRING );
			}
			data_type_AST = (AST)currentAST.root;
			break;
		}
		case SQL2RW_bit:
		{
			bit_string_type();
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				builder.setDomainOfLastAttribute( Importer.BOOLEAN );
			}
			data_type_AST = (AST)currentAST.root;
			break;
		}
		case SQL2NRW_number:
		case SQL2RW_dec:
		case SQL2RW_decimal:
		case SQL2RW_double:
		case SQL2RW_float:
		case SQL2RW_int:
		case SQL2RW_integer:
		case SQL2RW_numeric:
		case SQL2RW_real:
		case SQL2RW_smallint:
		{
			num_type();
			astFactory.addASTChild(currentAST, returnAST);
			data_type_AST = (AST)currentAST.root;
			break;
		}
		case SQL2RW_date:
		case SQL2RW_time:
		case SQL2RW_timestamp:
		{
			datetime_type();
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				builder.setDomainOfLastAttribute( Importer.DATETIME );
			}
			data_type_AST = (AST)currentAST.root;
			break;
		}
		case SQL2RW_interval:
		{
			interval_type();
			astFactory.addASTChild(currentAST, returnAST);
			data_type_AST = (AST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = data_type_AST;
	}

	public final void domain_name() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST domain_name_AST = null;

		qualified_name();
		astFactory.addASTChild(currentAST, returnAST);
		domain_name_AST = (AST)currentAST.root;
		returnAST = domain_name_AST;
	}

	public final void default_clause() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST default_clause_AST = null;

		AST tmp230_AST = null;
		tmp230_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp230_AST);
		match(SQL2RW_default);
		{
		switch ( LA(1)) {
		case SQL2RW_date:
		case SQL2RW_interval:
		case SQL2RW_time:
		case SQL2RW_timestamp:
		case UNSIGNED_INTEGER:
		case APPROXIMATE_NUM_LIT:
		case MINUS_SIGN:
		case NATIONAL_CHAR_STRING_LIT:
		case BIT_STRING_LIT:
		case HEX_STRING_LIT:
		case EXACT_NUM_LIT:
		case CHAR_STRING:
		case PLUS_SIGN:
		case INTRODUCER:
		{
			lit();
			astFactory.addASTChild(currentAST, returnAST);
			break;
		}
		case SQL2RW_current_date:
		case SQL2RW_current_time:
		case SQL2RW_current_timestamp:
		{
			datetime_value_fct();
			astFactory.addASTChild(currentAST, returnAST);
			break;
		}
		case SQL2RW_user:
		{
			AST tmp231_AST = null;
			tmp231_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp231_AST);
			match(SQL2RW_user);
			break;
		}
		case SQL2RW_current_user:
		{
			AST tmp232_AST = null;
			tmp232_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp232_AST);
			match(SQL2RW_current_user);
			break;
		}
		case SQL2RW_session_user:
		{
			AST tmp233_AST = null;
			tmp233_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp233_AST);
			match(SQL2RW_session_user);
			break;
		}
		case SQL2RW_system_user:
		{
			AST tmp234_AST = null;
			tmp234_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp234_AST);
			match(SQL2RW_system_user);
			break;
		}
		case SQL2RW_null:
		{
			AST tmp235_AST = null;
			tmp235_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp235_AST);
			match(SQL2RW_null);
			break;
		}
		case LITERAL_sysdate:
		{
			AST tmp236_AST = null;
			tmp236_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp236_AST);
			match(LITERAL_sysdate);
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		default_clause_AST = (AST)currentAST.root;
		returnAST = default_clause_AST;
	}

	public final void column_constraint_def() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST column_constraint_def_AST = null;

		{
		if ((LA(1)==SQL2RW_constraint)) {
			constraint_name_def();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else if ((LA(1)==SQL2RW_check||LA(1)==SQL2RW_not||LA(1)==SQL2RW_null||LA(1)==SQL2RW_primary||LA(1)==SQL2RW_references||LA(1)==SQL2RW_unique)) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		column_constraint();
		astFactory.addASTChild(currentAST, returnAST);
		{
		if ((LA(1)==SQL2RW_deferrable||LA(1)==SQL2RW_initially||LA(1)==SQL2RW_not) && (_tokenSet_29.member(LA(2)))) {
			constraint_attributes();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else if ((_tokenSet_20.member(LA(1))) && (_tokenSet_30.member(LA(2)))) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		column_constraint_def_AST = (AST)currentAST.root;
		returnAST = column_constraint_def_AST;
	}

	public final void collate_clause() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST collate_clause_AST = null;

		AST tmp237_AST = null;
		tmp237_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp237_AST);
		match(SQL2RW_collate);
		collation_name();
		astFactory.addASTChild(currentAST, returnAST);
		collate_clause_AST = (AST)currentAST.root;
		returnAST = collate_clause_AST;
	}

	public final void lit() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST lit_AST = null;

		if ((LA(1)==UNSIGNED_INTEGER||LA(1)==APPROXIMATE_NUM_LIT||LA(1)==MINUS_SIGN||LA(1)==EXACT_NUM_LIT||LA(1)==PLUS_SIGN)) {
			signed_num_lit();
			astFactory.addASTChild(currentAST, returnAST);
			lit_AST = (AST)currentAST.root;
		}
		else if ((_tokenSet_31.member(LA(1)))) {
			general_lit();
			astFactory.addASTChild(currentAST, returnAST);
			lit_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = lit_AST;
	}

	public final void datetime_value_fct() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST datetime_value_fct_AST = null;

		if ((LA(1)==SQL2RW_current_date)) {
			current_date_value_fct();
			astFactory.addASTChild(currentAST, returnAST);
			datetime_value_fct_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_current_time)) {
			current_time_value_fct();
			astFactory.addASTChild(currentAST, returnAST);
			datetime_value_fct_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_current_timestamp)) {
			currenttimestamp_value_fct();
			astFactory.addASTChild(currentAST, returnAST);
			datetime_value_fct_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = datetime_value_fct_AST;
	}

	public final void column_constraint() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST column_constraint_AST = null;

		switch ( LA(1)) {
		case SQL2RW_not:
		{
			AST tmp238_AST = null;
			tmp238_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp238_AST);
			match(SQL2RW_not);
			AST tmp239_AST = null;
			tmp239_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp239_AST);
			match(SQL2RW_null);
			column_constraint_AST = (AST)currentAST.root;
			break;
		}
		case SQL2RW_null:
		{
			AST tmp240_AST = null;
			tmp240_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp240_AST);
			match(SQL2RW_null);
			column_constraint_AST = (AST)currentAST.root;
			break;
		}
		case SQL2RW_primary:
		case SQL2RW_unique:
		{
			unique_spec();
			astFactory.addASTChild(currentAST, returnAST);
			column_constraint_AST = (AST)currentAST.root;
			break;
		}
		case SQL2RW_references:
		{
			refs_spec();
			astFactory.addASTChild(currentAST, returnAST);
			column_constraint_AST = (AST)currentAST.root;
			break;
		}
		case SQL2RW_check:
		{
			check_constraint_def();
			astFactory.addASTChild(currentAST, returnAST);
			column_constraint_AST = (AST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = column_constraint_AST;
	}

	public final void query_exp() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST query_exp_AST = null;

		query_term();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop539:
		do {
			if ((LA(1)==SQL2RW_except||LA(1)==SQL2RW_union)) {
				{
				if ((LA(1)==SQL2RW_union)) {
					AST tmp241_AST = null;
					tmp241_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp241_AST);
					match(SQL2RW_union);
				}
				else if ((LA(1)==SQL2RW_except)) {
					AST tmp242_AST = null;
					tmp242_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp242_AST);
					match(SQL2RW_except);
				}
				else {
					throw new NoViableAltException(LT(1), getFilename());
				}

				}
				{
				if ((LA(1)==SQL2RW_all)) {
					AST tmp243_AST = null;
					tmp243_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp243_AST);
					match(SQL2RW_all);
				}
				else if ((_tokenSet_32.member(LA(1)))) {
				}
				else {
					throw new NoViableAltException(LT(1), getFilename());
				}

				}
				{
				if ((LA(1)==SQL2RW_corresponding)) {
					corresponding_spec();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else if ((_tokenSet_7.member(LA(1)))) {
				}
				else {
					throw new NoViableAltException(LT(1), getFilename());
				}

				}
				query_term();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop539;
			}

		} while (true);
		}
		query_exp_AST = (AST)currentAST.root;
		returnAST = query_exp_AST;
	}

	public final void privileges() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST privileges_AST = null;

		if ((LA(1)==SQL2RW_all)) {
			AST tmp244_AST = null;
			tmp244_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp244_AST);
			match(SQL2RW_all);
			AST tmp245_AST = null;
			tmp245_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp245_AST);
			match(SQL2RW_privileges);
			privileges_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_delete||LA(1)==SQL2RW_insert||LA(1)==SQL2RW_references||LA(1)==SQL2RW_select||LA(1)==SQL2RW_update)) {
			action();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop179:
			do {
				if ((LA(1)==COMMA)) {
					AST tmp246_AST = null;
					tmp246_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp246_AST);
					match(COMMA);
					action();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop179;
				}

			} while (true);
			}
			privileges_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = privileges_AST;
	}

	public final void object_name() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST object_name_AST = null;

		switch ( LA(1)) {
		case SQL2RW_domain:
		{
			AST tmp247_AST = null;
			tmp247_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp247_AST);
			match(SQL2RW_domain);
			domain_name();
			astFactory.addASTChild(currentAST, returnAST);
			object_name_AST = (AST)currentAST.root;
			break;
		}
		case SQL2RW_collation:
		{
			AST tmp248_AST = null;
			tmp248_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp248_AST);
			match(SQL2RW_collation);
			collation_name();
			astFactory.addASTChild(currentAST, returnAST);
			object_name_AST = (AST)currentAST.root;
			break;
		}
		case SQL2RW_character:
		{
			AST tmp249_AST = null;
			tmp249_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp249_AST);
			match(SQL2RW_character);
			AST tmp250_AST = null;
			tmp250_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp250_AST);
			match(SQL2RW_set);
			char_set_name();
			astFactory.addASTChild(currentAST, returnAST);
			object_name_AST = (AST)currentAST.root;
			break;
		}
		case SQL2RW_translation:
		{
			AST tmp251_AST = null;
			tmp251_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp251_AST);
			match(SQL2RW_translation);
			translation_name();
			astFactory.addASTChild(currentAST, returnAST);
			object_name_AST = (AST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = object_name_AST;
	}

	public final void grantee() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST grantee_AST = null;

		if ((LA(1)==SQL2RW_public)) {
			AST tmp252_AST = null;
			tmp252_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp252_AST);
			match(SQL2RW_public);
			grantee_AST = (AST)currentAST.root;
		}
		else if ((_tokenSet_5.member(LA(1)))) {
			author_id();
			astFactory.addASTChild(currentAST, returnAST);
			grantee_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = grantee_AST;
	}

	public final void action() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST action_AST = null;

		switch ( LA(1)) {
		case SQL2RW_select:
		{
			AST tmp253_AST = null;
			tmp253_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp253_AST);
			match(SQL2RW_select);
			action_AST = (AST)currentAST.root;
			break;
		}
		case SQL2RW_delete:
		{
			AST tmp254_AST = null;
			tmp254_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp254_AST);
			match(SQL2RW_delete);
			action_AST = (AST)currentAST.root;
			break;
		}
		case SQL2RW_insert:
		{
			AST tmp255_AST = null;
			tmp255_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp255_AST);
			match(SQL2RW_insert);
			{
			if ((LA(1)==LEFT_PAREN)) {
				AST tmp256_AST = null;
				tmp256_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp256_AST);
				match(LEFT_PAREN);
				column_name_list();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp257_AST = null;
				tmp257_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp257_AST);
				match(RIGHT_PAREN);
			}
			else if ((LA(1)==SQL2RW_on||LA(1)==COMMA)) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}

			}
			action_AST = (AST)currentAST.root;
			break;
		}
		case SQL2RW_update:
		{
			AST tmp258_AST = null;
			tmp258_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp258_AST);
			match(SQL2RW_update);
			{
			if ((LA(1)==LEFT_PAREN)) {
				AST tmp259_AST = null;
				tmp259_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp259_AST);
				match(LEFT_PAREN);
				column_name_list();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp260_AST = null;
				tmp260_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp260_AST);
				match(RIGHT_PAREN);
			}
			else if ((LA(1)==SQL2RW_on||LA(1)==COMMA)) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}

			}
			action_AST = (AST)currentAST.root;
			break;
		}
		case SQL2RW_references:
		{
			AST tmp261_AST = null;
			tmp261_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp261_AST);
			match(SQL2RW_references);
			{
			if ((LA(1)==LEFT_PAREN)) {
				AST tmp262_AST = null;
				tmp262_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp262_AST);
				match(LEFT_PAREN);
				column_name_list();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp263_AST = null;
				tmp263_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp263_AST);
				match(RIGHT_PAREN);
			}
			else if ((LA(1)==SQL2RW_on||LA(1)==COMMA)) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}

			}
			action_AST = (AST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = action_AST;
	}

	public final void domain_constraint() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST domain_constraint_AST = null;

		{
		if ((LA(1)==SQL2RW_constraint)) {
			constraint_name_def();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else if ((LA(1)==SQL2RW_check)) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		check_constraint_def();
		astFactory.addASTChild(currentAST, returnAST);
		{
		if ((LA(1)==SQL2RW_deferrable||LA(1)==SQL2RW_initially||LA(1)==SQL2RW_not)) {
			constraint_attributes();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else if ((LA(1)==SQL2RW_check||LA(1)==SQL2RW_collate||LA(1)==SQL2RW_constraint||LA(1)==SQL2RW_create||LA(1)==SQL2RW_grant||LA(1)==SEMICOLON)) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		domain_constraint_AST = (AST)currentAST.root;
		returnAST = domain_constraint_AST;
	}

	public final void limited_collation_def() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST limited_collation_def_AST = null;

		AST tmp264_AST = null;
		tmp264_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp264_AST);
		match(SQL2RW_collation);
		AST tmp265_AST = null;
		tmp265_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp265_AST);
		match(SQL2RW_from);
		collation_source();
		astFactory.addASTChild(currentAST, returnAST);
		limited_collation_def_AST = (AST)currentAST.root;
		returnAST = limited_collation_def_AST;
	}

	public final void collation_source() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST collation_source_AST = null;

		if ((_tokenSet_33.member(LA(1)))) {
			collating_sequence_def();
			astFactory.addASTChild(currentAST, returnAST);
			collation_source_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_translation)) {
			translation_collation();
			astFactory.addASTChild(currentAST, returnAST);
			collation_source_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = collation_source_AST;
	}

	public final void collating_sequence_def() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST collating_sequence_def_AST = null;

		switch ( LA(1)) {
		case SQL2RW_external:
		{
			external_collation();
			astFactory.addASTChild(currentAST, returnAST);
			collating_sequence_def_AST = (AST)currentAST.root;
			break;
		}
		case SQL2NRW_ada:
		case SQL2NRW_c:
		case SQL2NRW_catalog_name:
		case SQL2NRW_character_set_catalog:
		case SQL2NRW_character_set_name:
		case SQL2NRW_character_set_schema:
		case SQL2NRW_class_origin:
		case SQL2NRW_cobol:
		case SQL2NRW_collation_catalog:
		case SQL2NRW_collation_name:
		case SQL2NRW_collation_schema:
		case SQL2NRW_column_name:
		case SQL2NRW_command_function:
		case SQL2NRW_committed:
		case SQL2NRW_condition_number:
		case SQL2NRW_connection_name:
		case SQL2NRW_constraint_catalog:
		case SQL2NRW_constraint_name:
		case SQL2NRW_constraint_schema:
		case SQL2NRW_cursor_name:
		case SQL2NRW_data:
		case SQL2NRW_datetime_interval_code:
		case SQL2NRW_datetime_interval_precision:
		case SQL2NRW_dynamic_function:
		case SQL2NRW_fortran:
		case SQL2NRW_length:
		case SQL2NRW_message_length:
		case SQL2NRW_message_octet_length:
		case SQL2NRW_message_text:
		case SQL2NRW_more:
		case SQL2NRW_mumps:
		case SQL2NRW_name:
		case SQL2NRW_nullable:
		case SQL2NRW_number:
		case SQL2NRW_pascal:
		case SQL2NRW_pli:
		case SQL2NRW_repeatable:
		case SQL2NRW_returned_length:
		case SQL2NRW_returned_octet_length:
		case SQL2NRW_returned_sqlstate:
		case SQL2NRW_row_count:
		case SQL2NRW_scale:
		case SQL2NRW_schema_name:
		case SQL2NRW_serializable:
		case SQL2NRW_server_name:
		case SQL2NRW_subclass_origin:
		case SQL2NRW_table_name:
		case SQL2NRW_type:
		case SQL2NRW_uncommitted:
		case SQL2NRW_unnamed:
		case SQL2RW_date:
		case SQL2RW_value:
		case REGULAR_ID:
		case DELIMITED_ID:
		case INTRODUCER:
		{
			collation_name();
			astFactory.addASTChild(currentAST, returnAST);
			collating_sequence_def_AST = (AST)currentAST.root;
			break;
		}
		case SQL2RW_desc:
		{
			AST tmp266_AST = null;
			tmp266_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp266_AST);
			match(SQL2RW_desc);
			AST tmp267_AST = null;
			tmp267_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp267_AST);
			match(LEFT_PAREN);
			collation_name();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp268_AST = null;
			tmp268_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp268_AST);
			match(RIGHT_PAREN);
			collating_sequence_def_AST = (AST)currentAST.root;
			break;
		}
		case SQL2RW_default:
		{
			AST tmp269_AST = null;
			tmp269_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp269_AST);
			match(SQL2RW_default);
			collating_sequence_def_AST = (AST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = collating_sequence_def_AST;
	}

	public final void translation_collation() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST translation_collation_AST = null;

		AST tmp270_AST = null;
		tmp270_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp270_AST);
		match(SQL2RW_translation);
		translation_name();
		astFactory.addASTChild(currentAST, returnAST);
		{
		if ((LA(1)==SQL2RW_then)) {
			AST tmp271_AST = null;
			tmp271_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp271_AST);
			match(SQL2RW_then);
			AST tmp272_AST = null;
			tmp272_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp272_AST);
			match(SQL2RW_collation);
			collation_name();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else if ((LA(1)==SQL2RW_create||LA(1)==SQL2RW_grant||LA(1)==SQL2RW_no||LA(1)==SQL2RW_pad||LA(1)==SEMICOLON)) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		translation_collation_AST = (AST)currentAST.root;
		returnAST = translation_collation_AST;
	}

	public final void external_collation() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST external_collation_AST = null;

		AST tmp273_AST = null;
		tmp273_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp273_AST);
		match(SQL2RW_external);
		AST tmp274_AST = null;
		tmp274_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp274_AST);
		match(LEFT_PAREN);
		AST tmp275_AST = null;
		tmp275_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp275_AST);
		match(CHAR_STRING);
		AST tmp276_AST = null;
		tmp276_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp276_AST);
		match(RIGHT_PAREN);
		external_collation_AST = (AST)currentAST.root;
		returnAST = external_collation_AST;
	}

	public final void collation_name() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST collation_name_AST = null;

		qualified_name();
		astFactory.addASTChild(currentAST, returnAST);
		collation_name_AST = (AST)currentAST.root;
		returnAST = collation_name_AST;
	}

	public final void translation_name() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST translation_name_AST = null;

		qualified_name();
		astFactory.addASTChild(currentAST, returnAST);
		translation_name_AST = (AST)currentAST.root;
		returnAST = translation_name_AST;
	}

	public final void translation_spec() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST translation_spec_AST = null;

		if ((LA(1)==SQL2RW_external)) {
			external_translation();
			astFactory.addASTChild(currentAST, returnAST);
			translation_spec_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_identity)) {
			AST tmp277_AST = null;
			tmp277_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp277_AST);
			match(SQL2RW_identity);
			translation_spec_AST = (AST)currentAST.root;
		}
		else if ((_tokenSet_5.member(LA(1)))) {
			translation_name();
			astFactory.addASTChild(currentAST, returnAST);
			translation_spec_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = translation_spec_AST;
	}

	public final void external_translation() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST external_translation_AST = null;

		AST tmp278_AST = null;
		tmp278_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp278_AST);
		match(SQL2RW_external);
		AST tmp279_AST = null;
		tmp279_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp279_AST);
		match(LEFT_PAREN);
		AST tmp280_AST = null;
		tmp280_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp280_AST);
		match(CHAR_STRING);
		AST tmp281_AST = null;
		tmp281_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp281_AST);
		match(RIGHT_PAREN);
		external_translation_AST = (AST)currentAST.root;
		returnAST = external_translation_AST;
	}

	public final void assertion_check() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST assertion_check_AST = null;

		AST tmp282_AST = null;
		tmp282_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp282_AST);
		match(SQL2RW_check);
		AST tmp283_AST = null;
		tmp283_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp283_AST);
		match(LEFT_PAREN);
		search_condition();
		astFactory.addASTChild(currentAST, returnAST);
		AST tmp284_AST = null;
		tmp284_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp284_AST);
		match(RIGHT_PAREN);
		assertion_check_AST = (AST)currentAST.root;
		returnAST = assertion_check_AST;
	}

	public final void drop_behavior() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST drop_behavior_AST = null;

		if ((LA(1)==SQL2RW_cascade)) {
			AST tmp285_AST = null;
			tmp285_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp285_AST);
			match(SQL2RW_cascade);
			drop_behavior_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_restrict)) {
			AST tmp286_AST = null;
			tmp286_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp286_AST);
			match(SQL2RW_restrict);
			drop_behavior_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = drop_behavior_AST;
	}

	public final void qualified_name() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST qualified_name_AST = null;

		id();
		astFactory.addASTChild(currentAST, returnAST);
		{
		if ((LA(1)==PERIOD) && (_tokenSet_5.member(LA(2)))) {
			AST tmp287_AST = null;
			tmp287_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp287_AST);
			match(PERIOD);
			id();
			astFactory.addASTChild(currentAST, returnAST);
			{
			if ((LA(1)==PERIOD) && (_tokenSet_5.member(LA(2)))) {
				AST tmp288_AST = null;
				tmp288_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp288_AST);
				match(PERIOD);
				id();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else if ((_tokenSet_34.member(LA(1))) && (_tokenSet_35.member(LA(2)))) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}

			}
		}
		else if ((_tokenSet_34.member(LA(1))) && (_tokenSet_35.member(LA(2)))) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		qualified_name_AST = (AST)currentAST.root;
		returnAST = qualified_name_AST;
	}

	public final void alter_table_action() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST alter_table_action_AST = null;

		if ((LA(1)==SQL2RW_add) && (_tokenSet_36.member(LA(2)))) {
			add_column_def();
			astFactory.addASTChild(currentAST, returnAST);
			alter_table_action_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_alter)) {
			alter_column_def();
			astFactory.addASTChild(currentAST, returnAST);
			alter_table_action_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_drop) && (_tokenSet_36.member(LA(2)))) {
			drop_column_def();
			astFactory.addASTChild(currentAST, returnAST);
			alter_table_action_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_add) && (LA(2)==SQL2RW_check||LA(2)==SQL2RW_constraint||LA(2)==SQL2RW_foreign||LA(2)==SQL2RW_primary||LA(2)==SQL2RW_unique)) {
			add_table_constraint_def();
			astFactory.addASTChild(currentAST, returnAST);
			alter_table_action_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_drop) && (LA(2)==SQL2RW_constraint)) {
			drop_table_constraint_def();
			astFactory.addASTChild(currentAST, returnAST);
			alter_table_action_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = alter_table_action_AST;
	}

	public final void add_column_def() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST add_column_def_AST = null;

		AST tmp289_AST = null;
		tmp289_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp289_AST);
		match(SQL2RW_add);
		{
		if ((LA(1)==SQL2RW_column)) {
			AST tmp290_AST = null;
			tmp290_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp290_AST);
			match(SQL2RW_column);
		}
		else if ((_tokenSet_5.member(LA(1)))) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		column_def();
		astFactory.addASTChild(currentAST, returnAST);
		add_column_def_AST = (AST)currentAST.root;
		returnAST = add_column_def_AST;
	}

	public final void alter_column_def() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST alter_column_def_AST = null;

		AST tmp291_AST = null;
		tmp291_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp291_AST);
		match(SQL2RW_alter);
		{
		if ((LA(1)==SQL2RW_column)) {
			AST tmp292_AST = null;
			tmp292_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp292_AST);
			match(SQL2RW_column);
		}
		else if ((_tokenSet_5.member(LA(1)))) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		column_name();
		astFactory.addASTChild(currentAST, returnAST);
		alter_column_action();
		astFactory.addASTChild(currentAST, returnAST);
		alter_column_def_AST = (AST)currentAST.root;
		returnAST = alter_column_def_AST;
	}

	public final void drop_column_def() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST drop_column_def_AST = null;

		AST tmp293_AST = null;
		tmp293_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp293_AST);
		match(SQL2RW_drop);
		{
		if ((LA(1)==SQL2RW_column)) {
			AST tmp294_AST = null;
			tmp294_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp294_AST);
			match(SQL2RW_column);
		}
		else if ((_tokenSet_5.member(LA(1)))) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		column_name();
		astFactory.addASTChild(currentAST, returnAST);
		drop_behavior();
		astFactory.addASTChild(currentAST, returnAST);
		drop_column_def_AST = (AST)currentAST.root;
		returnAST = drop_column_def_AST;
	}

	public final void add_table_constraint_def() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST add_table_constraint_def_AST = null;

		AST tmp295_AST = null;
		tmp295_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp295_AST);
		match(SQL2RW_add);
		table_constraint_def();
		astFactory.addASTChild(currentAST, returnAST);
		add_table_constraint_def_AST = (AST)currentAST.root;
		returnAST = add_table_constraint_def_AST;
	}

	public final void drop_table_constraint_def() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST drop_table_constraint_def_AST = null;

		AST tmp296_AST = null;
		tmp296_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp296_AST);
		match(SQL2RW_drop);
		AST tmp297_AST = null;
		tmp297_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp297_AST);
		match(SQL2RW_constraint);
		constraint_name();
		astFactory.addASTChild(currentAST, returnAST);
		drop_behavior();
		astFactory.addASTChild(currentAST, returnAST);
		drop_table_constraint_def_AST = (AST)currentAST.root;
		returnAST = drop_table_constraint_def_AST;
	}

	public final void alter_column_action() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST alter_column_action_AST = null;

		if ((LA(1)==SQL2RW_set)) {
			AST tmp298_AST = null;
			tmp298_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp298_AST);
			match(SQL2RW_set);
			default_clause();
			astFactory.addASTChild(currentAST, returnAST);
			alter_column_action_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_drop)) {
			AST tmp299_AST = null;
			tmp299_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp299_AST);
			match(SQL2RW_drop);
			AST tmp300_AST = null;
			tmp300_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp300_AST);
			match(SQL2RW_default);
			alter_column_action_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = alter_column_action_AST;
	}

	public final void alter_domain_action() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST alter_domain_action_AST = null;

		if ((LA(1)==SQL2RW_set)) {
			AST tmp301_AST = null;
			tmp301_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp301_AST);
			match(SQL2RW_set);
			default_clause();
			astFactory.addASTChild(currentAST, returnAST);
			alter_domain_action_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_drop) && (LA(2)==SQL2RW_default)) {
			AST tmp302_AST = null;
			tmp302_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp302_AST);
			match(SQL2RW_drop);
			AST tmp303_AST = null;
			tmp303_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp303_AST);
			match(SQL2RW_default);
			alter_domain_action_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_add)) {
			AST tmp304_AST = null;
			tmp304_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp304_AST);
			match(SQL2RW_add);
			domain_constraint();
			astFactory.addASTChild(currentAST, returnAST);
			alter_domain_action_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_drop) && (LA(2)==SQL2RW_constraint)) {
			AST tmp305_AST = null;
			tmp305_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp305_AST);
			match(SQL2RW_drop);
			AST tmp306_AST = null;
			tmp306_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp306_AST);
			match(SQL2RW_constraint);
			constraint_name();
			astFactory.addASTChild(currentAST, returnAST);
			alter_domain_action_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = alter_domain_action_AST;
	}

	public final void constraint_name_list() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST constraint_name_list_AST = null;

		if ((LA(1)==SQL2RW_all)) {
			AST tmp307_AST = null;
			tmp307_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp307_AST);
			match(SQL2RW_all);
			constraint_name_list_AST = (AST)currentAST.root;
		}
		else if ((_tokenSet_5.member(LA(1)))) {
			constraint_name();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop247:
			do {
				if ((LA(1)==COMMA)) {
					AST tmp308_AST = null;
					tmp308_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp308_AST);
					match(COMMA);
					constraint_name();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop247;
				}

			} while (true);
			}
			constraint_name_list_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = constraint_name_list_AST;
	}

	public final void transaction_mode() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST transaction_mode_AST = null;

		if ((LA(1)==SQL2RW_isolation)) {
			AST tmp309_AST = null;
			tmp309_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp309_AST);
			match(SQL2RW_isolation);
			AST tmp310_AST = null;
			tmp310_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp310_AST);
			match(SQL2RW_level);
			level_of_isolation();
			astFactory.addASTChild(currentAST, returnAST);
			transaction_mode_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_read)) {
			{
			if ((LA(1)==SQL2RW_read) && (LA(2)==SQL2RW_only)) {
				AST tmp311_AST = null;
				tmp311_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp311_AST);
				match(SQL2RW_read);
				AST tmp312_AST = null;
				tmp312_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp312_AST);
				match(SQL2RW_only);
			}
			else if ((LA(1)==SQL2RW_read) && (LA(2)==SQL2RW_write)) {
				AST tmp313_AST = null;
				tmp313_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp313_AST);
				match(SQL2RW_read);
				AST tmp314_AST = null;
				tmp314_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp314_AST);
				match(SQL2RW_write);
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}

			}
			transaction_mode_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_diagnostics)) {
			AST tmp315_AST = null;
			tmp315_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp315_AST);
			match(SQL2RW_diagnostics);
			AST tmp316_AST = null;
			tmp316_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp316_AST);
			match(SQL2RW_size);
			simple_value_spec();
			astFactory.addASTChild(currentAST, returnAST);
			transaction_mode_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = transaction_mode_AST;
	}

	public final void level_of_isolation() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST level_of_isolation_AST = null;

		if ((LA(1)==SQL2RW_read) && (LA(2)==SQL2NRW_uncommitted)) {
			AST tmp317_AST = null;
			tmp317_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp317_AST);
			match(SQL2RW_read);
			AST tmp318_AST = null;
			tmp318_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp318_AST);
			match(SQL2NRW_uncommitted);
			level_of_isolation_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_read) && (LA(2)==SQL2NRW_committed)) {
			AST tmp319_AST = null;
			tmp319_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp319_AST);
			match(SQL2RW_read);
			AST tmp320_AST = null;
			tmp320_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp320_AST);
			match(SQL2NRW_committed);
			level_of_isolation_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2NRW_repeatable)) {
			AST tmp321_AST = null;
			tmp321_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp321_AST);
			match(SQL2NRW_repeatable);
			AST tmp322_AST = null;
			tmp322_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp322_AST);
			match(SQL2RW_read);
			level_of_isolation_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2NRW_serializable)) {
			AST tmp323_AST = null;
			tmp323_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp323_AST);
			match(SQL2NRW_serializable);
			level_of_isolation_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = level_of_isolation_AST;
	}

	public final void simple_value_spec() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST simple_value_spec_AST = null;

		if ((LA(1)==COLON)) {
			parameter_name();
			astFactory.addASTChild(currentAST, returnAST);
			simple_value_spec_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==EMBDD_VARIABLE_NAME)) {
			AST tmp324_AST = null;
			tmp324_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp324_AST);
			match(EMBDD_VARIABLE_NAME);
			simple_value_spec_AST = (AST)currentAST.root;
		}
		else if ((_tokenSet_37.member(LA(1)))) {
			lit();
			astFactory.addASTChild(currentAST, returnAST);
			simple_value_spec_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = simple_value_spec_AST;
	}

	public final void connection_target() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST connection_target_AST = null;

		if ((_tokenSet_38.member(LA(1)))) {
			simple_value_spec();
			astFactory.addASTChild(currentAST, returnAST);
			{
			if ((LA(1)==SQL2RW_as)) {
				AST tmp325_AST = null;
				tmp325_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp325_AST);
				match(SQL2RW_as);
				simple_value_spec();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else if ((LA(1)==SQL2RW_user||LA(1)==SEMICOLON)) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}

			}
			{
			if ((LA(1)==SQL2RW_user)) {
				AST tmp326_AST = null;
				tmp326_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp326_AST);
				match(SQL2RW_user);
				simple_value_spec();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else if ((LA(1)==SEMICOLON)) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}

			}
			connection_target_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_default)) {
			AST tmp327_AST = null;
			tmp327_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp327_AST);
			match(SQL2RW_default);
			connection_target_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = connection_target_AST;
	}

	public final void connection_object() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST connection_object_AST = null;

		if ((LA(1)==SQL2RW_default)) {
			AST tmp328_AST = null;
			tmp328_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp328_AST);
			match(SQL2RW_default);
			connection_object_AST = (AST)currentAST.root;
		}
		else if ((_tokenSet_38.member(LA(1)))) {
			simple_value_spec();
			astFactory.addASTChild(currentAST, returnAST);
			connection_object_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = connection_object_AST;
	}

	public final void value_spec() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST value_spec_AST = null;

		if ((_tokenSet_37.member(LA(1)))) {
			lit();
			astFactory.addASTChild(currentAST, returnAST);
			value_spec_AST = (AST)currentAST.root;
		}
		else if ((_tokenSet_39.member(LA(1)))) {
			general_value_spec();
			astFactory.addASTChild(currentAST, returnAST);
			value_spec_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = value_spec_AST;
	}

	public final void interval_value_exp() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST interval_value_exp_AST = null;

		value_exp();
		astFactory.addASTChild(currentAST, returnAST);
		interval_value_exp_AST = (AST)currentAST.root;
		returnAST = interval_value_exp_AST;
	}

	public final void descriptor_name() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST descriptor_name_AST = null;

		{
		if ((LA(1)==SQL2RW_global)) {
			AST tmp329_AST = null;
			tmp329_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp329_AST);
			match(SQL2RW_global);
		}
		else if ((LA(1)==SQL2RW_local)) {
			AST tmp330_AST = null;
			tmp330_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp330_AST);
			match(SQL2RW_local);
		}
		else if ((_tokenSet_38.member(LA(1)))) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		simple_value_spec();
		astFactory.addASTChild(currentAST, returnAST);
		descriptor_name_AST = (AST)currentAST.root;
		returnAST = descriptor_name_AST;
	}

	public final void set_descriptor_information() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST set_descriptor_information_AST = null;

		if ((LA(1)==SQL2RW_count)) {
			AST tmp331_AST = null;
			tmp331_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp331_AST);
			match(SQL2RW_count);
			AST tmp332_AST = null;
			tmp332_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp332_AST);
			match(EQUALS_OP);
			simple_value_spec();
			astFactory.addASTChild(currentAST, returnAST);
			set_descriptor_information_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_value)) {
			AST tmp333_AST = null;
			tmp333_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp333_AST);
			match(SQL2RW_value);
			simple_value_spec();
			astFactory.addASTChild(currentAST, returnAST);
			set_item_information();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop272:
			do {
				if ((LA(1)==COMMA)) {
					AST tmp334_AST = null;
					tmp334_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp334_AST);
					match(COMMA);
					set_item_information();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop272;
				}

			} while (true);
			}
			set_descriptor_information_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = set_descriptor_information_AST;
	}

	public final void get_descriptor_information() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST get_descriptor_information_AST = null;

		if ((LA(1)==EMBDD_VARIABLE_NAME||LA(1)==COLON)) {
			simple_target_spec();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp335_AST = null;
			tmp335_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp335_AST);
			match(EQUALS_OP);
			AST tmp336_AST = null;
			tmp336_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp336_AST);
			match(SQL2RW_count);
			get_descriptor_information_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_value)) {
			AST tmp337_AST = null;
			tmp337_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp337_AST);
			match(SQL2RW_value);
			simple_value_spec();
			astFactory.addASTChild(currentAST, returnAST);
			get_item_information();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop277:
			do {
				if ((LA(1)==COMMA)) {
					AST tmp338_AST = null;
					tmp338_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp338_AST);
					match(COMMA);
					get_item_information();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop277;
				}

			} while (true);
			}
			get_descriptor_information_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = get_descriptor_information_AST;
	}

	public final void set_item_information() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST set_item_information_AST = null;

		descriptor_item_name();
		astFactory.addASTChild(currentAST, returnAST);
		AST tmp339_AST = null;
		tmp339_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp339_AST);
		match(EQUALS_OP);
		simple_value_spec();
		astFactory.addASTChild(currentAST, returnAST);
		set_item_information_AST = (AST)currentAST.root;
		returnAST = set_item_information_AST;
	}

	public final void descriptor_item_name() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST descriptor_item_name_AST = null;

		switch ( LA(1)) {
		case SQL2NRW_type:
		{
			AST tmp340_AST = null;
			tmp340_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp340_AST);
			match(SQL2NRW_type);
			descriptor_item_name_AST = (AST)currentAST.root;
			break;
		}
		case SQL2NRW_length:
		{
			AST tmp341_AST = null;
			tmp341_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp341_AST);
			match(SQL2NRW_length);
			descriptor_item_name_AST = (AST)currentAST.root;
			break;
		}
		case SQL2RW_octet_length:
		{
			AST tmp342_AST = null;
			tmp342_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp342_AST);
			match(SQL2RW_octet_length);
			descriptor_item_name_AST = (AST)currentAST.root;
			break;
		}
		case SQL2NRW_returned_length:
		{
			AST tmp343_AST = null;
			tmp343_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp343_AST);
			match(SQL2NRW_returned_length);
			descriptor_item_name_AST = (AST)currentAST.root;
			break;
		}
		case SQL2NRW_returned_octet_length:
		{
			AST tmp344_AST = null;
			tmp344_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp344_AST);
			match(SQL2NRW_returned_octet_length);
			descriptor_item_name_AST = (AST)currentAST.root;
			break;
		}
		case SQL2RW_precision:
		{
			AST tmp345_AST = null;
			tmp345_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp345_AST);
			match(SQL2RW_precision);
			descriptor_item_name_AST = (AST)currentAST.root;
			break;
		}
		case SQL2NRW_scale:
		{
			AST tmp346_AST = null;
			tmp346_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp346_AST);
			match(SQL2NRW_scale);
			descriptor_item_name_AST = (AST)currentAST.root;
			break;
		}
		case SQL2NRW_datetime_interval_code:
		{
			AST tmp347_AST = null;
			tmp347_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp347_AST);
			match(SQL2NRW_datetime_interval_code);
			descriptor_item_name_AST = (AST)currentAST.root;
			break;
		}
		case SQL2NRW_datetime_interval_precision:
		{
			AST tmp348_AST = null;
			tmp348_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp348_AST);
			match(SQL2NRW_datetime_interval_precision);
			descriptor_item_name_AST = (AST)currentAST.root;
			break;
		}
		case SQL2NRW_nullable:
		{
			AST tmp349_AST = null;
			tmp349_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp349_AST);
			match(SQL2NRW_nullable);
			descriptor_item_name_AST = (AST)currentAST.root;
			break;
		}
		case SQL2RW_indicator:
		{
			AST tmp350_AST = null;
			tmp350_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp350_AST);
			match(SQL2RW_indicator);
			descriptor_item_name_AST = (AST)currentAST.root;
			break;
		}
		case SQL2NRW_data:
		{
			AST tmp351_AST = null;
			tmp351_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp351_AST);
			match(SQL2NRW_data);
			descriptor_item_name_AST = (AST)currentAST.root;
			break;
		}
		case SQL2NRW_name:
		{
			AST tmp352_AST = null;
			tmp352_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp352_AST);
			match(SQL2NRW_name);
			descriptor_item_name_AST = (AST)currentAST.root;
			break;
		}
		case SQL2NRW_unnamed:
		{
			AST tmp353_AST = null;
			tmp353_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp353_AST);
			match(SQL2NRW_unnamed);
			descriptor_item_name_AST = (AST)currentAST.root;
			break;
		}
		case SQL2NRW_collation_catalog:
		{
			AST tmp354_AST = null;
			tmp354_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp354_AST);
			match(SQL2NRW_collation_catalog);
			descriptor_item_name_AST = (AST)currentAST.root;
			break;
		}
		case SQL2NRW_collation_schema:
		{
			AST tmp355_AST = null;
			tmp355_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp355_AST);
			match(SQL2NRW_collation_schema);
			descriptor_item_name_AST = (AST)currentAST.root;
			break;
		}
		case SQL2NRW_collation_name:
		{
			AST tmp356_AST = null;
			tmp356_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp356_AST);
			match(SQL2NRW_collation_name);
			descriptor_item_name_AST = (AST)currentAST.root;
			break;
		}
		case SQL2NRW_character_set_catalog:
		{
			AST tmp357_AST = null;
			tmp357_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp357_AST);
			match(SQL2NRW_character_set_catalog);
			descriptor_item_name_AST = (AST)currentAST.root;
			break;
		}
		case SQL2NRW_character_set_schema:
		{
			AST tmp358_AST = null;
			tmp358_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp358_AST);
			match(SQL2NRW_character_set_schema);
			descriptor_item_name_AST = (AST)currentAST.root;
			break;
		}
		case SQL2NRW_character_set_name:
		{
			AST tmp359_AST = null;
			tmp359_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp359_AST);
			match(SQL2NRW_character_set_name);
			descriptor_item_name_AST = (AST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = descriptor_item_name_AST;
	}

	public final void simple_target_spec() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST simple_target_spec_AST = null;

		if ((LA(1)==COLON)) {
			parameter_name();
			astFactory.addASTChild(currentAST, returnAST);
			simple_target_spec_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==EMBDD_VARIABLE_NAME)) {
			AST tmp360_AST = null;
			tmp360_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp360_AST);
			match(EMBDD_VARIABLE_NAME);
			simple_target_spec_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = simple_target_spec_AST;
	}

	public final void get_item_information() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST get_item_information_AST = null;

		simple_target_spec();
		astFactory.addASTChild(currentAST, returnAST);
		AST tmp361_AST = null;
		tmp361_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp361_AST);
		match(EQUALS_OP);
		descriptor_item_name();
		astFactory.addASTChild(currentAST, returnAST);
		get_item_information_AST = (AST)currentAST.root;
		returnAST = get_item_information_AST;
	}

	public final void sql_stmt_name() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST sql_stmt_name_AST = null;

		if (((_tokenSet_6.member(LA(1))) && (_tokenSet_40.member(LA(2))))&&(LA(1) == INTRODUCER)) {
			{
			boolean synPredMatched343 = false;
			if (((_tokenSet_5.member(LA(1))) && (_tokenSet_41.member(LA(2))))) {
				int _m343 = mark();
				synPredMatched343 = true;
				inputState.guessing++;
				try {
					{
					stmt_name();
					}
				}
				catch (RecognitionException pe) {
					synPredMatched343 = false;
				}
				rewind(_m343);
inputState.guessing--;
			}
			if ( synPredMatched343 ) {
				stmt_name();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else if ((_tokenSet_42.member(LA(1))) && (_tokenSet_40.member(LA(2)))) {
				extended_stmt_name();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}

			}
			sql_stmt_name_AST = (AST)currentAST.root;
		}
		else if (((_tokenSet_5.member(LA(1))) && (_tokenSet_41.member(LA(2))))&&(LA(1) != INTRODUCER)) {
			stmt_name();
			astFactory.addASTChild(currentAST, returnAST);
			sql_stmt_name_AST = (AST)currentAST.root;
		}
		else if ((_tokenSet_42.member(LA(1))) && (_tokenSet_40.member(LA(2)))) {
			extended_stmt_name();
			astFactory.addASTChild(currentAST, returnAST);
			sql_stmt_name_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = sql_stmt_name_AST;
	}

	public final void sql_stmt_variable() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST sql_stmt_variable_AST = null;

		if ((LA(1)==COLON)) {
			parameter_name();
			astFactory.addASTChild(currentAST, returnAST);
			sql_stmt_variable_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==EMBDD_VARIABLE_NAME)) {
			AST tmp362_AST = null;
			tmp362_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp362_AST);
			match(EMBDD_VARIABLE_NAME);
			sql_stmt_variable_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = sql_stmt_variable_AST;
	}

	public final void parameter_name() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST parameter_name_AST = null;

		AST tmp363_AST = null;
		tmp363_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp363_AST);
		match(COLON);
		{
		if ((_tokenSet_5.member(LA(1)))) {
			id();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop683:
			do {
				if ((LA(1)==PERIOD)) {
					AST tmp364_AST = null;
					tmp364_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp364_AST);
					match(PERIOD);
					id();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop683;
				}

			} while (true);
			}
		}
		else if ((LA(1)==UNSIGNED_INTEGER)) {
			AST tmp365_AST = null;
			tmp365_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp365_AST);
			match(UNSIGNED_INTEGER);
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		parameter_name_AST = (AST)currentAST.root;
		returnAST = parameter_name_AST;
	}

	public final void using_descriptor() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST using_descriptor_AST = null;

		{
		if ((LA(1)==SQL2RW_using)) {
			AST tmp366_AST = null;
			tmp366_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp366_AST);
			match(SQL2RW_using);
		}
		else if ((LA(1)==SQL2RW_into)) {
			AST tmp367_AST = null;
			tmp367_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp367_AST);
			match(SQL2RW_into);
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		AST tmp368_AST = null;
		tmp368_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp368_AST);
		match(SQL2RW_sql);
		AST tmp369_AST = null;
		tmp369_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp369_AST);
		match(SQL2RW_descriptor);
		descriptor_name();
		astFactory.addASTChild(currentAST, returnAST);
		using_descriptor_AST = (AST)currentAST.root;
		returnAST = using_descriptor_AST;
	}

	public final void using_clause() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST using_clause_AST = null;

		if ((LA(1)==SQL2RW_into||LA(1)==SQL2RW_using) && (LA(2)==EMBDD_VARIABLE_NAME||LA(2)==COLON)) {
			{
			if ((LA(1)==SQL2RW_using)) {
				AST tmp370_AST = null;
				tmp370_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp370_AST);
				match(SQL2RW_using);
			}
			else if ((LA(1)==SQL2RW_into)) {
				AST tmp371_AST = null;
				tmp371_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp371_AST);
				match(SQL2RW_into);
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}

			}
			target_spec();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop350:
			do {
				if ((LA(1)==COMMA)) {
					AST tmp372_AST = null;
					tmp372_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp372_AST);
					match(COMMA);
					target_spec();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop350;
				}

			} while (true);
			}
			using_clause_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_into||LA(1)==SQL2RW_using) && (LA(2)==SQL2RW_sql)) {
			using_descriptor();
			astFactory.addASTChild(currentAST, returnAST);
			using_clause_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = using_clause_AST;
	}

	public final void extended_cursor_name() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST extended_cursor_name_AST = null;

		{
		if ((LA(1)==SQL2RW_global)) {
			AST tmp373_AST = null;
			tmp373_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp373_AST);
			match(SQL2RW_global);
		}
		else if ((LA(1)==SQL2RW_local)) {
			AST tmp374_AST = null;
			tmp374_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp374_AST);
			match(SQL2RW_local);
		}
		else if ((_tokenSet_38.member(LA(1)))) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		simple_value_spec();
		astFactory.addASTChild(currentAST, returnAST);
		extended_cursor_name_AST = (AST)currentAST.root;
		returnAST = extended_cursor_name_AST;
	}

	public final void extended_stmt_name() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST extended_stmt_name_AST = null;

		{
		if ((LA(1)==SQL2RW_global)) {
			AST tmp375_AST = null;
			tmp375_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp375_AST);
			match(SQL2RW_global);
		}
		else if ((LA(1)==SQL2RW_local)) {
			AST tmp376_AST = null;
			tmp376_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp376_AST);
			match(SQL2RW_local);
		}
		else if ((_tokenSet_38.member(LA(1)))) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		simple_value_spec();
		astFactory.addASTChild(currentAST, returnAST);
		extended_stmt_name_AST = (AST)currentAST.root;
		returnAST = extended_stmt_name_AST;
	}

	public final void fetch_orientation() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST fetch_orientation_AST = null;

		switch ( LA(1)) {
		case SQL2RW_next:
		{
			AST tmp377_AST = null;
			tmp377_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp377_AST);
			match(SQL2RW_next);
			fetch_orientation_AST = (AST)currentAST.root;
			break;
		}
		case SQL2RW_prior:
		{
			AST tmp378_AST = null;
			tmp378_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp378_AST);
			match(SQL2RW_prior);
			fetch_orientation_AST = (AST)currentAST.root;
			break;
		}
		case SQL2RW_first:
		{
			AST tmp379_AST = null;
			tmp379_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp379_AST);
			match(SQL2RW_first);
			fetch_orientation_AST = (AST)currentAST.root;
			break;
		}
		case SQL2RW_last:
		{
			AST tmp380_AST = null;
			tmp380_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp380_AST);
			match(SQL2RW_last);
			fetch_orientation_AST = (AST)currentAST.root;
			break;
		}
		case SQL2RW_absolute:
		case SQL2RW_relative:
		{
			{
			if ((LA(1)==SQL2RW_absolute)) {
				AST tmp381_AST = null;
				tmp381_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp381_AST);
				match(SQL2RW_absolute);
			}
			else if ((LA(1)==SQL2RW_relative)) {
				AST tmp382_AST = null;
				tmp382_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp382_AST);
				match(SQL2RW_relative);
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}

			}
			simple_value_spec();
			astFactory.addASTChild(currentAST, returnAST);
			fetch_orientation_AST = (AST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = fetch_orientation_AST;
	}

	public final void dyn_cursor_name() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST dyn_cursor_name_AST = null;

		if (((_tokenSet_6.member(LA(1))) && (_tokenSet_43.member(LA(2))))&&(LA(1) == INTRODUCER)) {
			{
			boolean synPredMatched675 = false;
			if (((_tokenSet_5.member(LA(1))) && (_tokenSet_44.member(LA(2))))) {
				int _m675 = mark();
				synPredMatched675 = true;
				inputState.guessing++;
				try {
					{
					cursor_name();
					}
				}
				catch (RecognitionException pe) {
					synPredMatched675 = false;
				}
				rewind(_m675);
inputState.guessing--;
			}
			if ( synPredMatched675 ) {
				cursor_name();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else if ((_tokenSet_42.member(LA(1))) && (_tokenSet_43.member(LA(2)))) {
				extended_cursor_name();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}

			}
			dyn_cursor_name_AST = (AST)currentAST.root;
		}
		else if (((_tokenSet_5.member(LA(1))) && (_tokenSet_44.member(LA(2))))&&(LA(1) != INTRODUCER)) {
			cursor_name();
			astFactory.addASTChild(currentAST, returnAST);
			dyn_cursor_name_AST = (AST)currentAST.root;
		}
		else if ((_tokenSet_42.member(LA(1))) && (_tokenSet_43.member(LA(2)))) {
			extended_cursor_name();
			astFactory.addASTChild(currentAST, returnAST);
			dyn_cursor_name_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = dyn_cursor_name_AST;
	}

	public final void stmt_information() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST stmt_information_AST = null;

		stmt_information_item();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop312:
		do {
			if ((LA(1)==COMMA)) {
				AST tmp383_AST = null;
				tmp383_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp383_AST);
				match(COMMA);
				stmt_information_item();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop312;
			}

		} while (true);
		}
		stmt_information_AST = (AST)currentAST.root;
		returnAST = stmt_information_AST;
	}

	public final void condition_information() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST condition_information_AST = null;

		AST tmp384_AST = null;
		tmp384_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp384_AST);
		match(SQL2RW_exception);
		simple_value_spec();
		astFactory.addASTChild(currentAST, returnAST);
		condition_information_item();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop317:
		do {
			if ((LA(1)==COMMA)) {
				AST tmp385_AST = null;
				tmp385_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp385_AST);
				match(COMMA);
				condition_information_item();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop317;
			}

		} while (true);
		}
		condition_information_AST = (AST)currentAST.root;
		returnAST = condition_information_AST;
	}

	public final void stmt_information_item() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST stmt_information_item_AST = null;

		simple_target_spec();
		astFactory.addASTChild(currentAST, returnAST);
		AST tmp386_AST = null;
		tmp386_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp386_AST);
		match(EQUALS_OP);
		{
		switch ( LA(1)) {
		case SQL2NRW_number:
		{
			AST tmp387_AST = null;
			tmp387_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp387_AST);
			match(SQL2NRW_number);
			break;
		}
		case SQL2NRW_more:
		{
			AST tmp388_AST = null;
			tmp388_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp388_AST);
			match(SQL2NRW_more);
			break;
		}
		case SQL2NRW_command_function:
		{
			AST tmp389_AST = null;
			tmp389_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp389_AST);
			match(SQL2NRW_command_function);
			break;
		}
		case SQL2NRW_dynamic_function:
		{
			AST tmp390_AST = null;
			tmp390_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp390_AST);
			match(SQL2NRW_dynamic_function);
			break;
		}
		case SQL2NRW_row_count:
		{
			AST tmp391_AST = null;
			tmp391_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp391_AST);
			match(SQL2NRW_row_count);
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		stmt_information_item_AST = (AST)currentAST.root;
		returnAST = stmt_information_item_AST;
	}

	public final void condition_information_item() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST condition_information_item_AST = null;

		simple_target_spec();
		astFactory.addASTChild(currentAST, returnAST);
		AST tmp392_AST = null;
		tmp392_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp392_AST);
		match(EQUALS_OP);
		{
		switch ( LA(1)) {
		case SQL2NRW_condition_number:
		{
			AST tmp393_AST = null;
			tmp393_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp393_AST);
			match(SQL2NRW_condition_number);
			break;
		}
		case SQL2NRW_returned_sqlstate:
		{
			AST tmp394_AST = null;
			tmp394_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp394_AST);
			match(SQL2NRW_returned_sqlstate);
			break;
		}
		case SQL2NRW_class_origin:
		{
			AST tmp395_AST = null;
			tmp395_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp395_AST);
			match(SQL2NRW_class_origin);
			break;
		}
		case SQL2NRW_subclass_origin:
		{
			AST tmp396_AST = null;
			tmp396_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp396_AST);
			match(SQL2NRW_subclass_origin);
			break;
		}
		case SQL2NRW_server_name:
		{
			AST tmp397_AST = null;
			tmp397_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp397_AST);
			match(SQL2NRW_server_name);
			break;
		}
		case SQL2NRW_connection_name:
		{
			AST tmp398_AST = null;
			tmp398_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp398_AST);
			match(SQL2NRW_connection_name);
			break;
		}
		case SQL2NRW_constraint_catalog:
		{
			AST tmp399_AST = null;
			tmp399_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp399_AST);
			match(SQL2NRW_constraint_catalog);
			break;
		}
		case SQL2NRW_constraint_schema:
		{
			AST tmp400_AST = null;
			tmp400_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp400_AST);
			match(SQL2NRW_constraint_schema);
			break;
		}
		case SQL2NRW_constraint_name:
		{
			AST tmp401_AST = null;
			tmp401_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp401_AST);
			match(SQL2NRW_constraint_name);
			break;
		}
		case SQL2NRW_catalog_name:
		{
			AST tmp402_AST = null;
			tmp402_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp402_AST);
			match(SQL2NRW_catalog_name);
			break;
		}
		case SQL2NRW_schema_name:
		{
			AST tmp403_AST = null;
			tmp403_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp403_AST);
			match(SQL2NRW_schema_name);
			break;
		}
		case SQL2NRW_table_name:
		{
			AST tmp404_AST = null;
			tmp404_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp404_AST);
			match(SQL2NRW_table_name);
			break;
		}
		case SQL2NRW_column_name:
		{
			AST tmp405_AST = null;
			tmp405_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp405_AST);
			match(SQL2NRW_column_name);
			break;
		}
		case SQL2NRW_cursor_name:
		{
			AST tmp406_AST = null;
			tmp406_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp406_AST);
			match(SQL2NRW_cursor_name);
			break;
		}
		case SQL2NRW_message_text:
		{
			AST tmp407_AST = null;
			tmp407_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp407_AST);
			match(SQL2NRW_message_text);
			break;
		}
		case SQL2NRW_message_length:
		{
			AST tmp408_AST = null;
			tmp408_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp408_AST);
			match(SQL2NRW_message_length);
			break;
		}
		case SQL2NRW_message_octet_length:
		{
			AST tmp409_AST = null;
			tmp409_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp409_AST);
			match(SQL2NRW_message_octet_length);
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		condition_information_item_AST = (AST)currentAST.root;
		returnAST = condition_information_item_AST;
	}

	public final void cursor_name() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST cursor_name_AST = null;

		id();
		astFactory.addASTChild(currentAST, returnAST);
		cursor_name_AST = (AST)currentAST.root;
		returnAST = cursor_name_AST;
	}

	public final void stmt_name() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST stmt_name_AST = null;

		id();
		astFactory.addASTChild(currentAST, returnAST);
		stmt_name_AST = (AST)currentAST.root;
		returnAST = stmt_name_AST;
	}

	public final void joined_table() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST joined_table_AST = null;

		table_ref_aux();
		astFactory.addASTChild(currentAST, returnAST);
		{
		if ((LA(1)==SQL2RW_full||LA(1)==SQL2RW_inner||LA(1)==SQL2RW_join||LA(1)==SQL2RW_left||LA(1)==SQL2RW_natural||LA(1)==SQL2RW_right||LA(1)==SQL2RW_union)) {
			qualified_join();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else if ((LA(1)==SQL2RW_cross)) {
			cross_join();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		joined_table_AST = (AST)currentAST.root;
		returnAST = joined_table_AST;
	}

	public final void select_stmt() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST select_stmt_AST = null;

		query_exp();
		astFactory.addASTChild(currentAST, returnAST);
		{
		switch ( LA(1)) {
		case SQL2RW_into:
		{
			into_clause();
			astFactory.addASTChild(currentAST, returnAST);
			{
			if ((LA(1)==SQL2RW_order)) {
				order_by_clause();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else if ((LA(1)==SQL2RW_for||LA(1)==SEMICOLON)) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}

			}
			{
			if ((LA(1)==SQL2RW_for)) {
				updatability_clause();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else if ((LA(1)==SEMICOLON)) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}

			}
			break;
		}
		case SQL2RW_order:
		{
			order_by_clause();
			astFactory.addASTChild(currentAST, returnAST);
			{
			if ((LA(1)==SQL2RW_into)) {
				into_clause();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else if ((LA(1)==SQL2RW_for||LA(1)==SEMICOLON)) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}

			}
			{
			if ((LA(1)==SQL2RW_for)) {
				updatability_clause();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else if ((LA(1)==SEMICOLON)) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}

			}
			break;
		}
		case SQL2RW_for:
		{
			updatability_clause();
			astFactory.addASTChild(currentAST, returnAST);
			{
			if ((LA(1)==SQL2RW_into)) {
				into_clause();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else if ((LA(1)==SEMICOLON)) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}

			}
			break;
		}
		case SEMICOLON:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		select_stmt_AST = (AST)currentAST.root;
		returnAST = select_stmt_AST;
	}

	public final void query_primary() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST query_primary_AST = null;

		if ((LA(1)==SQL2RW_select||LA(1)==SQL2RW_table||LA(1)==SQL2RW_values)) {
			simple_table();
			astFactory.addASTChild(currentAST, returnAST);
			query_primary_AST = (AST)currentAST.root;
		}
		else {
			boolean synPredMatched333 = false;
			if (((_tokenSet_45.member(LA(1))) && (_tokenSet_46.member(LA(2))))) {
				int _m333 = mark();
				synPredMatched333 = true;
				inputState.guessing++;
				try {
					{
					joined_table();
					}
				}
				catch (RecognitionException pe) {
					synPredMatched333 = false;
				}
				rewind(_m333);
inputState.guessing--;
			}
			if ( synPredMatched333 ) {
				joined_table();
				astFactory.addASTChild(currentAST, returnAST);
				query_primary_AST = (AST)currentAST.root;
			}
			else if ((LA(1)==LEFT_PAREN) && (_tokenSet_7.member(LA(2)))) {
				AST tmp410_AST = null;
				tmp410_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp410_AST);
				match(LEFT_PAREN);
				query_exp();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp411_AST = null;
				tmp411_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp411_AST);
				match(RIGHT_PAREN);
				query_primary_AST = (AST)currentAST.root;
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			returnAST = query_primary_AST;
		}

	public final void simple_table() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST simple_table_AST = null;

		if ((LA(1)==SQL2RW_select)) {
			query_spec();
			astFactory.addASTChild(currentAST, returnAST);
			simple_table_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_values)) {
			table_value_constructor();
			astFactory.addASTChild(currentAST, returnAST);
			simple_table_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_table)) {
			explicit_table();
			astFactory.addASTChild(currentAST, returnAST);
			simple_table_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = simple_table_AST;
	}

	public final void id() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST id_AST = null;

		{
		if ((LA(1)==INTRODUCER)) {
			AST tmp412_AST = null;
			tmp412_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp412_AST);
			match(INTRODUCER);
			char_set_name();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else if ((_tokenSet_47.member(LA(1)))) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		{
		switch ( LA(1)) {
		case REGULAR_ID:
		{
			AST tmp413_AST = null;
			tmp413_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp413_AST);
			match(REGULAR_ID);
			break;
		}
		case DELIMITED_ID:
		{
			AST tmp414_AST = null;
			tmp414_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp414_AST);
			match(DELIMITED_ID);
			break;
		}
		case SQL2RW_value:
		{
			AST tmp415_AST = null;
			tmp415_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp415_AST);
			match(SQL2RW_value);
			break;
		}
		case SQL2RW_date:
		{
			AST tmp416_AST = null;
			tmp416_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp416_AST);
			match(SQL2RW_date);
			break;
		}
		default:
			if ((((LA(1) >= SQL2NRW_ada && LA(1) <= SQL2NRW_unnamed)))&&(true)) {
				non_reserved_word();
				astFactory.addASTChild(currentAST, returnAST);
			}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		id_AST = (AST)currentAST.root;
		returnAST = id_AST;
	}

	public final void general_value_spec() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST general_value_spec_AST = null;

		switch ( LA(1)) {
		case COLON:
		{
			parameter_spec();
			astFactory.addASTChild(currentAST, returnAST);
			general_value_spec_AST = (AST)currentAST.root;
			break;
		}
		case QUESTION_MARK:
		{
			dyn_parameter_spec();
			astFactory.addASTChild(currentAST, returnAST);
			general_value_spec_AST = (AST)currentAST.root;
			break;
		}
		case EMBDD_VARIABLE_NAME:
		{
			variable_spec();
			astFactory.addASTChild(currentAST, returnAST);
			general_value_spec_AST = (AST)currentAST.root;
			break;
		}
		case SQL2RW_user:
		{
			AST tmp417_AST = null;
			tmp417_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp417_AST);
			match(SQL2RW_user);
			general_value_spec_AST = (AST)currentAST.root;
			break;
		}
		case SQL2RW_current_user:
		{
			AST tmp418_AST = null;
			tmp418_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp418_AST);
			match(SQL2RW_current_user);
			general_value_spec_AST = (AST)currentAST.root;
			break;
		}
		case SQL2RW_session_user:
		{
			AST tmp419_AST = null;
			tmp419_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp419_AST);
			match(SQL2RW_session_user);
			general_value_spec_AST = (AST)currentAST.root;
			break;
		}
		case SQL2RW_system_user:
		{
			AST tmp420_AST = null;
			tmp420_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp420_AST);
			match(SQL2RW_system_user);
			general_value_spec_AST = (AST)currentAST.root;
			break;
		}
		case SQL2RW_value:
		{
			AST tmp421_AST = null;
			tmp421_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp421_AST);
			match(SQL2RW_value);
			general_value_spec_AST = (AST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = general_value_spec_AST;
	}

	public final void target_spec() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST target_spec_AST = null;

		if ((LA(1)==COLON)) {
			parameter_spec();
			astFactory.addASTChild(currentAST, returnAST);
			target_spec_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==EMBDD_VARIABLE_NAME)) {
			variable_spec();
			astFactory.addASTChild(currentAST, returnAST);
			target_spec_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = target_spec_AST;
	}

	public final void target_list() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST target_list_AST = null;

		target_spec();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop353:
		do {
			if ((LA(1)==COMMA)) {
				AST tmp422_AST = null;
				tmp422_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp422_AST);
				match(COMMA);
				target_spec();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop353;
			}

		} while (true);
		}
		target_list_AST = (AST)currentAST.root;
		returnAST = target_list_AST;
	}

	public final void non_reserved_word() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST non_reserved_word_AST = null;

		switch ( LA(1)) {
		case SQL2NRW_ada:
		{
			AST tmp423_AST = null;
			tmp423_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp423_AST);
			match(SQL2NRW_ada);
			non_reserved_word_AST = (AST)currentAST.root;
			break;
		}
		case SQL2NRW_c:
		{
			AST tmp424_AST = null;
			tmp424_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp424_AST);
			match(SQL2NRW_c);
			non_reserved_word_AST = (AST)currentAST.root;
			break;
		}
		case SQL2NRW_catalog_name:
		{
			AST tmp425_AST = null;
			tmp425_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp425_AST);
			match(SQL2NRW_catalog_name);
			non_reserved_word_AST = (AST)currentAST.root;
			break;
		}
		case SQL2NRW_character_set_catalog:
		{
			AST tmp426_AST = null;
			tmp426_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp426_AST);
			match(SQL2NRW_character_set_catalog);
			non_reserved_word_AST = (AST)currentAST.root;
			break;
		}
		case SQL2NRW_character_set_name:
		{
			AST tmp427_AST = null;
			tmp427_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp427_AST);
			match(SQL2NRW_character_set_name);
			non_reserved_word_AST = (AST)currentAST.root;
			break;
		}
		case SQL2NRW_character_set_schema:
		{
			AST tmp428_AST = null;
			tmp428_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp428_AST);
			match(SQL2NRW_character_set_schema);
			non_reserved_word_AST = (AST)currentAST.root;
			break;
		}
		case SQL2NRW_class_origin:
		{
			AST tmp429_AST = null;
			tmp429_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp429_AST);
			match(SQL2NRW_class_origin);
			non_reserved_word_AST = (AST)currentAST.root;
			break;
		}
		case SQL2NRW_cobol:
		{
			AST tmp430_AST = null;
			tmp430_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp430_AST);
			match(SQL2NRW_cobol);
			non_reserved_word_AST = (AST)currentAST.root;
			break;
		}
		case SQL2NRW_collation_catalog:
		{
			AST tmp431_AST = null;
			tmp431_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp431_AST);
			match(SQL2NRW_collation_catalog);
			non_reserved_word_AST = (AST)currentAST.root;
			break;
		}
		case SQL2NRW_collation_name:
		{
			AST tmp432_AST = null;
			tmp432_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp432_AST);
			match(SQL2NRW_collation_name);
			non_reserved_word_AST = (AST)currentAST.root;
			break;
		}
		case SQL2NRW_collation_schema:
		{
			AST tmp433_AST = null;
			tmp433_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp433_AST);
			match(SQL2NRW_collation_schema);
			non_reserved_word_AST = (AST)currentAST.root;
			break;
		}
		case SQL2NRW_column_name:
		{
			AST tmp434_AST = null;
			tmp434_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp434_AST);
			match(SQL2NRW_column_name);
			non_reserved_word_AST = (AST)currentAST.root;
			break;
		}
		case SQL2NRW_command_function:
		{
			AST tmp435_AST = null;
			tmp435_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp435_AST);
			match(SQL2NRW_command_function);
			non_reserved_word_AST = (AST)currentAST.root;
			break;
		}
		case SQL2NRW_committed:
		{
			AST tmp436_AST = null;
			tmp436_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp436_AST);
			match(SQL2NRW_committed);
			non_reserved_word_AST = (AST)currentAST.root;
			break;
		}
		case SQL2NRW_condition_number:
		{
			AST tmp437_AST = null;
			tmp437_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp437_AST);
			match(SQL2NRW_condition_number);
			non_reserved_word_AST = (AST)currentAST.root;
			break;
		}
		case SQL2NRW_connection_name:
		{
			AST tmp438_AST = null;
			tmp438_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp438_AST);
			match(SQL2NRW_connection_name);
			non_reserved_word_AST = (AST)currentAST.root;
			break;
		}
		case SQL2NRW_constraint_catalog:
		{
			AST tmp439_AST = null;
			tmp439_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp439_AST);
			match(SQL2NRW_constraint_catalog);
			non_reserved_word_AST = (AST)currentAST.root;
			break;
		}
		case SQL2NRW_constraint_name:
		{
			AST tmp440_AST = null;
			tmp440_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp440_AST);
			match(SQL2NRW_constraint_name);
			non_reserved_word_AST = (AST)currentAST.root;
			break;
		}
		case SQL2NRW_constraint_schema:
		{
			AST tmp441_AST = null;
			tmp441_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp441_AST);
			match(SQL2NRW_constraint_schema);
			non_reserved_word_AST = (AST)currentAST.root;
			break;
		}
		case SQL2NRW_cursor_name:
		{
			AST tmp442_AST = null;
			tmp442_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp442_AST);
			match(SQL2NRW_cursor_name);
			non_reserved_word_AST = (AST)currentAST.root;
			break;
		}
		case SQL2NRW_data:
		{
			AST tmp443_AST = null;
			tmp443_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp443_AST);
			match(SQL2NRW_data);
			non_reserved_word_AST = (AST)currentAST.root;
			break;
		}
		case SQL2NRW_datetime_interval_code:
		{
			AST tmp444_AST = null;
			tmp444_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp444_AST);
			match(SQL2NRW_datetime_interval_code);
			non_reserved_word_AST = (AST)currentAST.root;
			break;
		}
		case SQL2NRW_datetime_interval_precision:
		{
			AST tmp445_AST = null;
			tmp445_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp445_AST);
			match(SQL2NRW_datetime_interval_precision);
			non_reserved_word_AST = (AST)currentAST.root;
			break;
		}
		case SQL2NRW_dynamic_function:
		{
			AST tmp446_AST = null;
			tmp446_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp446_AST);
			match(SQL2NRW_dynamic_function);
			non_reserved_word_AST = (AST)currentAST.root;
			break;
		}
		case SQL2NRW_fortran:
		{
			AST tmp447_AST = null;
			tmp447_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp447_AST);
			match(SQL2NRW_fortran);
			non_reserved_word_AST = (AST)currentAST.root;
			break;
		}
		case SQL2NRW_length:
		{
			AST tmp448_AST = null;
			tmp448_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp448_AST);
			match(SQL2NRW_length);
			non_reserved_word_AST = (AST)currentAST.root;
			break;
		}
		case SQL2NRW_message_length:
		{
			AST tmp449_AST = null;
			tmp449_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp449_AST);
			match(SQL2NRW_message_length);
			non_reserved_word_AST = (AST)currentAST.root;
			break;
		}
		case SQL2NRW_message_octet_length:
		{
			AST tmp450_AST = null;
			tmp450_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp450_AST);
			match(SQL2NRW_message_octet_length);
			non_reserved_word_AST = (AST)currentAST.root;
			break;
		}
		case SQL2NRW_message_text:
		{
			AST tmp451_AST = null;
			tmp451_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp451_AST);
			match(SQL2NRW_message_text);
			non_reserved_word_AST = (AST)currentAST.root;
			break;
		}
		case SQL2NRW_more:
		{
			AST tmp452_AST = null;
			tmp452_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp452_AST);
			match(SQL2NRW_more);
			non_reserved_word_AST = (AST)currentAST.root;
			break;
		}
		case SQL2NRW_mumps:
		{
			AST tmp453_AST = null;
			tmp453_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp453_AST);
			match(SQL2NRW_mumps);
			non_reserved_word_AST = (AST)currentAST.root;
			break;
		}
		case SQL2NRW_name:
		{
			AST tmp454_AST = null;
			tmp454_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp454_AST);
			match(SQL2NRW_name);
			non_reserved_word_AST = (AST)currentAST.root;
			break;
		}
		case SQL2NRW_nullable:
		{
			AST tmp455_AST = null;
			tmp455_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp455_AST);
			match(SQL2NRW_nullable);
			non_reserved_word_AST = (AST)currentAST.root;
			break;
		}
		case SQL2NRW_number:
		{
			AST tmp456_AST = null;
			tmp456_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp456_AST);
			match(SQL2NRW_number);
			non_reserved_word_AST = (AST)currentAST.root;
			break;
		}
		case SQL2NRW_pascal:
		{
			AST tmp457_AST = null;
			tmp457_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp457_AST);
			match(SQL2NRW_pascal);
			non_reserved_word_AST = (AST)currentAST.root;
			break;
		}
		case SQL2NRW_pli:
		{
			AST tmp458_AST = null;
			tmp458_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp458_AST);
			match(SQL2NRW_pli);
			non_reserved_word_AST = (AST)currentAST.root;
			break;
		}
		case SQL2NRW_repeatable:
		{
			AST tmp459_AST = null;
			tmp459_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp459_AST);
			match(SQL2NRW_repeatable);
			non_reserved_word_AST = (AST)currentAST.root;
			break;
		}
		case SQL2NRW_returned_length:
		{
			AST tmp460_AST = null;
			tmp460_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp460_AST);
			match(SQL2NRW_returned_length);
			non_reserved_word_AST = (AST)currentAST.root;
			break;
		}
		case SQL2NRW_returned_octet_length:
		{
			AST tmp461_AST = null;
			tmp461_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp461_AST);
			match(SQL2NRW_returned_octet_length);
			non_reserved_word_AST = (AST)currentAST.root;
			break;
		}
		case SQL2NRW_returned_sqlstate:
		{
			AST tmp462_AST = null;
			tmp462_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp462_AST);
			match(SQL2NRW_returned_sqlstate);
			non_reserved_word_AST = (AST)currentAST.root;
			break;
		}
		case SQL2NRW_row_count:
		{
			AST tmp463_AST = null;
			tmp463_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp463_AST);
			match(SQL2NRW_row_count);
			non_reserved_word_AST = (AST)currentAST.root;
			break;
		}
		case SQL2NRW_scale:
		{
			AST tmp464_AST = null;
			tmp464_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp464_AST);
			match(SQL2NRW_scale);
			non_reserved_word_AST = (AST)currentAST.root;
			break;
		}
		case SQL2NRW_schema_name:
		{
			AST tmp465_AST = null;
			tmp465_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp465_AST);
			match(SQL2NRW_schema_name);
			non_reserved_word_AST = (AST)currentAST.root;
			break;
		}
		case SQL2NRW_serializable:
		{
			AST tmp466_AST = null;
			tmp466_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp466_AST);
			match(SQL2NRW_serializable);
			non_reserved_word_AST = (AST)currentAST.root;
			break;
		}
		case SQL2NRW_server_name:
		{
			AST tmp467_AST = null;
			tmp467_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp467_AST);
			match(SQL2NRW_server_name);
			non_reserved_word_AST = (AST)currentAST.root;
			break;
		}
		case SQL2NRW_subclass_origin:
		{
			AST tmp468_AST = null;
			tmp468_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp468_AST);
			match(SQL2NRW_subclass_origin);
			non_reserved_word_AST = (AST)currentAST.root;
			break;
		}
		case SQL2NRW_table_name:
		{
			AST tmp469_AST = null;
			tmp469_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp469_AST);
			match(SQL2NRW_table_name);
			non_reserved_word_AST = (AST)currentAST.root;
			break;
		}
		case SQL2NRW_type:
		{
			AST tmp470_AST = null;
			tmp470_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp470_AST);
			match(SQL2NRW_type);
			non_reserved_word_AST = (AST)currentAST.root;
			break;
		}
		case SQL2NRW_uncommitted:
		{
			AST tmp471_AST = null;
			tmp471_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp471_AST);
			match(SQL2NRW_uncommitted);
			non_reserved_word_AST = (AST)currentAST.root;
			break;
		}
		case SQL2NRW_unnamed:
		{
			AST tmp472_AST = null;
			tmp472_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp472_AST);
			match(SQL2NRW_unnamed);
			non_reserved_word_AST = (AST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = non_reserved_word_AST;
	}

	public final void any_token() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST any_token_AST = null;

		matchNot(EOF);
		returnAST = any_token_AST;
	}

	public final void sql_script() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST sql_script_AST = null;

		{
		if ((_tokenSet_48.member(LA(1)))) {
			sql_stmt();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else if ((LA(1)==EOF||LA(1)==SEMICOLON)) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		{
		_loop360:
		do {
			if ((LA(1)==SEMICOLON)) {
				AST tmp474_AST = null;
				tmp474_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp474_AST);
				match(SEMICOLON);
				{
				if ((_tokenSet_48.member(LA(1)))) {
					sql_stmt();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else if ((LA(1)==EOF||LA(1)==SEMICOLON)) {
				}
				else {
					throw new NoViableAltException(LT(1), getFilename());
				}

				}
			}
			else {
				break _loop360;
			}

		} while (true);
		}
		sql_script_AST = (AST)currentAST.root;
		returnAST = sql_script_AST;
	}

	public final void sql_single_stmt() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST sql_single_stmt_AST = null;

		{
		if ((_tokenSet_48.member(LA(1)))) {
			sql_stmt();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else if ((LA(1)==EOF)) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		sql_single_stmt_AST = (AST)currentAST.root;
		returnAST = sql_single_stmt_AST;
	}

	public final void insert_stmt() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST insert_stmt_AST = null;

		AST tmp475_AST = null;
		tmp475_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp475_AST);
		match(SQL2RW_insert);
		AST tmp476_AST = null;
		tmp476_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp476_AST);
		match(SQL2RW_into);
		table_name();
		astFactory.addASTChild(currentAST, returnAST);
		insert_columns_and_source();
		astFactory.addASTChild(currentAST, returnAST);
		insert_stmt_AST = (AST)currentAST.root;
		returnAST = insert_stmt_AST;
	}

	public final void update_stmt() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST update_stmt_AST = null;

		AST tmp477_AST = null;
		tmp477_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp477_AST);
		match(SQL2RW_update);
		{
		if ((_tokenSet_11.member(LA(1)))) {
			table_name();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp478_AST = null;
			tmp478_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp478_AST);
			match(SQL2RW_set);
			set_clause_list();
			astFactory.addASTChild(currentAST, returnAST);
			{
			if ((LA(1)==SQL2RW_where)) {
				AST tmp479_AST = null;
				tmp479_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp479_AST);
				match(SQL2RW_where);
				{
				if ((_tokenSet_49.member(LA(1)))) {
					search_condition();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else if ((LA(1)==SQL2RW_current)) {
					AST tmp480_AST = null;
					tmp480_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp480_AST);
					match(SQL2RW_current);
					AST tmp481_AST = null;
					tmp481_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp481_AST);
					match(SQL2RW_of);
					dyn_cursor_name();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					throw new NoViableAltException(LT(1), getFilename());
				}

				}
			}
			else if ((LA(1)==SEMICOLON)) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}

			}
		}
		else if ((LA(1)==SQL2RW_set)) {
			AST tmp482_AST = null;
			tmp482_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp482_AST);
			match(SQL2RW_set);
			set_clause_list();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp483_AST = null;
			tmp483_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp483_AST);
			match(SQL2RW_where);
			AST tmp484_AST = null;
			tmp484_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp484_AST);
			match(SQL2RW_current);
			AST tmp485_AST = null;
			tmp485_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp485_AST);
			match(SQL2RW_of);
			cursor_name();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		update_stmt_AST = (AST)currentAST.root;
		returnAST = update_stmt_AST;
	}

	public final void delete_stmt() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST delete_stmt_AST = null;

		AST tmp486_AST = null;
		tmp486_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp486_AST);
		match(SQL2RW_delete);
		{
		if ((LA(1)==SQL2RW_from)) {
			AST tmp487_AST = null;
			tmp487_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp487_AST);
			match(SQL2RW_from);
			table_name();
			astFactory.addASTChild(currentAST, returnAST);
			{
			if ((LA(1)==SQL2RW_where)) {
				AST tmp488_AST = null;
				tmp488_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp488_AST);
				match(SQL2RW_where);
				{
				if ((_tokenSet_49.member(LA(1)))) {
					search_condition();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else if ((LA(1)==SQL2RW_current)) {
					AST tmp489_AST = null;
					tmp489_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp489_AST);
					match(SQL2RW_current);
					AST tmp490_AST = null;
					tmp490_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp490_AST);
					match(SQL2RW_of);
					dyn_cursor_name();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					throw new NoViableAltException(LT(1), getFilename());
				}

				}
			}
			else if ((LA(1)==SEMICOLON)) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}

			}
		}
		else if ((LA(1)==SQL2RW_where)) {
			AST tmp491_AST = null;
			tmp491_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp491_AST);
			match(SQL2RW_where);
			AST tmp492_AST = null;
			tmp492_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp492_AST);
			match(SQL2RW_current);
			AST tmp493_AST = null;
			tmp493_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp493_AST);
			match(SQL2RW_of);
			cursor_name();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		delete_stmt_AST = (AST)currentAST.root;
		returnAST = delete_stmt_AST;
	}

	public final void into_clause() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST into_clause_AST = null;

		AST tmp494_AST = null;
		tmp494_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp494_AST);
		match(SQL2RW_into);
		target_spec();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop373:
		do {
			if ((LA(1)==COMMA)) {
				AST tmp495_AST = null;
				tmp495_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp495_AST);
				match(COMMA);
				target_spec();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop373;
			}

		} while (true);
		}
		into_clause_AST = (AST)currentAST.root;
		returnAST = into_clause_AST;
	}

	public final void order_by_clause() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST order_by_clause_AST = null;

		AST tmp496_AST = null;
		tmp496_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp496_AST);
		match(SQL2RW_order);
		AST tmp497_AST = null;
		tmp497_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp497_AST);
		match(SQL2RW_by);
		sort_spec_list();
		astFactory.addASTChild(currentAST, returnAST);
		order_by_clause_AST = (AST)currentAST.root;
		returnAST = order_by_clause_AST;
	}

	public final void updatability_clause() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST updatability_clause_AST = null;

		AST tmp498_AST = null;
		tmp498_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp498_AST);
		match(SQL2RW_for);
		{
		if ((LA(1)==SQL2RW_read)) {
			AST tmp499_AST = null;
			tmp499_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp499_AST);
			match(SQL2RW_read);
			AST tmp500_AST = null;
			tmp500_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp500_AST);
			match(SQL2RW_only);
		}
		else if ((LA(1)==SQL2RW_update)) {
			AST tmp501_AST = null;
			tmp501_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp501_AST);
			match(SQL2RW_update);
			{
			if ((LA(1)==SQL2RW_of)) {
				AST tmp502_AST = null;
				tmp502_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp502_AST);
				match(SQL2RW_of);
				column_name_list();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else if ((LA(1)==SQL2RW_into||LA(1)==SEMICOLON)) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}

			}
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		updatability_clause_AST = (AST)currentAST.root;
		returnAST = updatability_clause_AST;
	}

	public final void sort_spec_list() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST sort_spec_list_AST = null;

		sort_spec();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop377:
		do {
			if ((LA(1)==COMMA)) {
				AST tmp503_AST = null;
				tmp503_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp503_AST);
				match(COMMA);
				sort_spec();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop377;
			}

		} while (true);
		}
		sort_spec_list_AST = (AST)currentAST.root;
		returnAST = sort_spec_list_AST;
	}

	public final void sort_spec() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST sort_spec_AST = null;

		sort_key();
		astFactory.addASTChild(currentAST, returnAST);
		{
		if ((LA(1)==SQL2RW_collate)) {
			collate_clause();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else if ((LA(1)==SQL2RW_asc||LA(1)==SQL2RW_desc||LA(1)==SQL2RW_for||LA(1)==SQL2RW_into||LA(1)==COMMA||LA(1)==SEMICOLON)) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		{
		if ((LA(1)==SQL2RW_asc||LA(1)==SQL2RW_desc)) {
			ordering_spec();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else if ((LA(1)==SQL2RW_for||LA(1)==SQL2RW_into||LA(1)==COMMA||LA(1)==SEMICOLON)) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		sort_spec_AST = (AST)currentAST.root;
		returnAST = sort_spec_AST;
	}

	public final void sort_key() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST sort_key_AST = null;

		if ((_tokenSet_5.member(LA(1)))) {
			column_ref();
			astFactory.addASTChild(currentAST, returnAST);
			sort_key_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==UNSIGNED_INTEGER)) {
			AST tmp504_AST = null;
			tmp504_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp504_AST);
			match(UNSIGNED_INTEGER);
			sort_key_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = sort_key_AST;
	}

	public final void ordering_spec() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST ordering_spec_AST = null;

		if ((LA(1)==SQL2RW_asc)) {
			AST tmp505_AST = null;
			tmp505_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp505_AST);
			match(SQL2RW_asc);
			ordering_spec_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_desc)) {
			AST tmp506_AST = null;
			tmp506_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp506_AST);
			match(SQL2RW_desc);
			ordering_spec_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = ordering_spec_AST;
	}

	public final void column_ref() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST column_ref_AST = null;

		id();
		astFactory.addASTChild(currentAST, returnAST);
		{
		if ((LA(1)==PERIOD)) {
			AST tmp507_AST = null;
			tmp507_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp507_AST);
			match(PERIOD);
			id();
			astFactory.addASTChild(currentAST, returnAST);
			{
			if ((LA(1)==PERIOD)) {
				AST tmp508_AST = null;
				tmp508_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp508_AST);
				match(PERIOD);
				id();
				astFactory.addASTChild(currentAST, returnAST);
				{
				if ((LA(1)==PERIOD)) {
					AST tmp509_AST = null;
					tmp509_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp509_AST);
					match(PERIOD);
					id();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else if ((_tokenSet_50.member(LA(1)))) {
				}
				else {
					throw new NoViableAltException(LT(1), getFilename());
				}

				}
			}
			else if ((_tokenSet_50.member(LA(1)))) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}

			}
		}
		else if ((_tokenSet_50.member(LA(1)))) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		column_ref_AST = (AST)currentAST.root;
		returnAST = column_ref_AST;
	}

	public final void insert_columns_and_source() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST insert_columns_and_source_AST = null;

		boolean synPredMatched389 = false;
		if (((LA(1)==LEFT_PAREN) && (_tokenSet_5.member(LA(2))))) {
			int _m389 = mark();
			synPredMatched389 = true;
			inputState.guessing++;
			try {
				{
				match(LEFT_PAREN);
				column_name_list();
				match(RIGHT_PAREN);
				}
			}
			catch (RecognitionException pe) {
				synPredMatched389 = false;
			}
			rewind(_m389);
inputState.guessing--;
		}
		if ( synPredMatched389 ) {
			AST tmp510_AST = null;
			tmp510_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp510_AST);
			match(LEFT_PAREN);
			column_name_list();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp511_AST = null;
			tmp511_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp511_AST);
			match(RIGHT_PAREN);
			query_exp();
			astFactory.addASTChild(currentAST, returnAST);
			insert_columns_and_source_AST = (AST)currentAST.root;
		}
		else if ((_tokenSet_7.member(LA(1))) && (_tokenSet_9.member(LA(2)))) {
			query_exp();
			astFactory.addASTChild(currentAST, returnAST);
			insert_columns_and_source_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_default)) {
			AST tmp512_AST = null;
			tmp512_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp512_AST);
			match(SQL2RW_default);
			AST tmp513_AST = null;
			tmp513_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp513_AST);
			match(SQL2RW_values);
			insert_columns_and_source_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = insert_columns_and_source_AST;
	}

	public final void set_clause_list() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST set_clause_list_AST = null;

		set_clause();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop396:
		do {
			if ((LA(1)==COMMA)) {
				AST tmp514_AST = null;
				tmp514_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp514_AST);
				match(COMMA);
				set_clause();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop396;
			}

		} while (true);
		}
		set_clause_list_AST = (AST)currentAST.root;
		returnAST = set_clause_list_AST;
	}

	public final void set_clause() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST set_clause_AST = null;

		column_name();
		astFactory.addASTChild(currentAST, returnAST);
		AST tmp515_AST = null;
		tmp515_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp515_AST);
		match(EQUALS_OP);
		update_source();
		astFactory.addASTChild(currentAST, returnAST);
		set_clause_AST = (AST)currentAST.root;
		returnAST = set_clause_AST;
	}

	public final void update_source() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST update_source_AST = null;

		if ((_tokenSet_14.member(LA(1)))) {
			value_exp();
			astFactory.addASTChild(currentAST, returnAST);
			update_source_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_null)) {
			AST tmp516_AST = null;
			tmp516_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp516_AST);
			match(SQL2RW_null);
			update_source_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_default)) {
			AST tmp517_AST = null;
			tmp517_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp517_AST);
			match(SQL2RW_default);
			update_source_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = update_source_AST;
	}

	public final void value_exp() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST value_exp_AST = null;

		term();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop618:
		do {
			if ((LA(1)==MINUS_SIGN||LA(1)==CONCATENATION_OP||LA(1)==PLUS_SIGN) && (_tokenSet_14.member(LA(2)))) {
				{
				if ((LA(1)==MINUS_SIGN||LA(1)==PLUS_SIGN)) {
					term_op();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else if ((LA(1)==CONCATENATION_OP)) {
					AST tmp518_AST = null;
					tmp518_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp518_AST);
					match(CONCATENATION_OP);
				}
				else {
					throw new NoViableAltException(LT(1), getFilename());
				}

				}
				term();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop618;
			}

		} while (true);
		}
		value_exp_AST = (AST)currentAST.root;
		returnAST = value_exp_AST;
	}

	public final void select_list() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST select_list_AST = null;

		if ((LA(1)==ASTERISK)) {
			AST tmp519_AST = null;
			tmp519_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp519_AST);
			match(ASTERISK);
			select_list_AST = (AST)currentAST.root;
		}
		else if ((_tokenSet_51.member(LA(1)))) {
			select_sublist();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop416:
			do {
				if ((LA(1)==COMMA)) {
					AST tmp520_AST = null;
					tmp520_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp520_AST);
					match(COMMA);
					select_sublist();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop416;
				}

			} while (true);
			}
			select_list_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = select_list_AST;
	}

	public final void select_sublist() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST select_sublist_AST = null;

		boolean synPredMatched419 = false;
		if (((_tokenSet_11.member(LA(1))) && (_tokenSet_52.member(LA(2))))) {
			int _m419 = mark();
			synPredMatched419 = true;
			inputState.guessing++;
			try {
				{
				table_name();
				match(PERIOD);
				match(ASTERISK);
				}
			}
			catch (RecognitionException pe) {
				synPredMatched419 = false;
			}
			rewind(_m419);
inputState.guessing--;
		}
		if ( synPredMatched419 ) {
			table_name();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp521_AST = null;
			tmp521_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp521_AST);
			match(PERIOD);
			AST tmp522_AST = null;
			tmp522_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp522_AST);
			match(ASTERISK);
			select_sublist_AST = (AST)currentAST.root;
		}
		else if ((_tokenSet_14.member(LA(1))) && (_tokenSet_53.member(LA(2)))) {
			derived_column();
			astFactory.addASTChild(currentAST, returnAST);
			select_sublist_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = select_sublist_AST;
	}

	public final void derived_column() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST derived_column_AST = null;

		value_exp();
		astFactory.addASTChild(currentAST, returnAST);
		{
		if ((LA(1)==SQL2RW_as)) {
			AST tmp523_AST = null;
			tmp523_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp523_AST);
			match(SQL2RW_as);
			column_name();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else if ((LA(1)==SQL2RW_from||LA(1)==SQL2RW_into||LA(1)==COMMA)) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		derived_column_AST = (AST)currentAST.root;
		returnAST = derived_column_AST;
	}

	public final void value_exp_primary() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST value_exp_primary_AST = null;

		if ((LA(1)==SQL2RW_avg||LA(1)==SQL2RW_count||LA(1)==SQL2RW_max||LA(1)==SQL2RW_min||LA(1)==SQL2RW_sum)) {
			set_fct_spec();
			astFactory.addASTChild(currentAST, returnAST);
			value_exp_primary_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_case||LA(1)==SQL2RW_coalesce||LA(1)==SQL2RW_nullif)) {
			case_exp();
			astFactory.addASTChild(currentAST, returnAST);
			value_exp_primary_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_cast)) {
			cast_spec();
			astFactory.addASTChild(currentAST, returnAST);
			value_exp_primary_AST = (AST)currentAST.root;
		}
		else if (((_tokenSet_54.member(LA(1))) && (_tokenSet_55.member(LA(2))))&&(LA(1) == INTRODUCER)) {
			{
			boolean synPredMatched425 = false;
			if (((_tokenSet_5.member(LA(1))) && (_tokenSet_56.member(LA(2))))) {
				int _m425 = mark();
				synPredMatched425 = true;
				inputState.guessing++;
				try {
					{
					column_ref();
					}
				}
				catch (RecognitionException pe) {
					synPredMatched425 = false;
				}
				rewind(_m425);
inputState.guessing--;
			}
			if ( synPredMatched425 ) {
				column_ref();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else if ((_tokenSet_57.member(LA(1))) && (_tokenSet_58.member(LA(2)))) {
				unsigned_value_spec();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}

			}
			value_exp_primary_AST = (AST)currentAST.root;
		}
		else if (((_tokenSet_5.member(LA(1))) && (_tokenSet_56.member(LA(2))))&&(LA(1) != INTRODUCER)) {
			column_ref();
			astFactory.addASTChild(currentAST, returnAST);
			value_exp_primary_AST = (AST)currentAST.root;
		}
		else if ((_tokenSet_57.member(LA(1))) && (_tokenSet_58.member(LA(2)))) {
			unsigned_value_spec();
			astFactory.addASTChild(currentAST, returnAST);
			value_exp_primary_AST = (AST)currentAST.root;
		}
		else {
			boolean synPredMatched427 = false;
			if (((LA(1)==LEFT_PAREN) && (_tokenSet_14.member(LA(2))))) {
				int _m427 = mark();
				synPredMatched427 = true;
				inputState.guessing++;
				try {
					{
					match(LEFT_PAREN);
					value_exp();
					match(RIGHT_PAREN);
					}
				}
				catch (RecognitionException pe) {
					synPredMatched427 = false;
				}
				rewind(_m427);
inputState.guessing--;
			}
			if ( synPredMatched427 ) {
				AST tmp524_AST = null;
				tmp524_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp524_AST);
				match(LEFT_PAREN);
				value_exp();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp525_AST = null;
				tmp525_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp525_AST);
				match(RIGHT_PAREN);
				value_exp_primary_AST = (AST)currentAST.root;
			}
			else if ((LA(1)==LEFT_PAREN) && (_tokenSet_7.member(LA(2)))) {
				scalar_subquery();
				astFactory.addASTChild(currentAST, returnAST);
				value_exp_primary_AST = (AST)currentAST.root;
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			returnAST = value_exp_primary_AST;
		}

	public final void set_fct_spec() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST set_fct_spec_AST = null;

		if ((LA(1)==SQL2RW_count)) {
			AST tmp526_AST = null;
			tmp526_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp526_AST);
			match(SQL2RW_count);
			AST tmp527_AST = null;
			tmp527_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp527_AST);
			match(LEFT_PAREN);
			{
			if ((LA(1)==ASTERISK)) {
				AST tmp528_AST = null;
				tmp528_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp528_AST);
				match(ASTERISK);
			}
			else if ((_tokenSet_59.member(LA(1)))) {
				{
				if ((LA(1)==SQL2RW_all||LA(1)==SQL2RW_distinct)) {
					set_quantifier();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else if ((_tokenSet_14.member(LA(1)))) {
				}
				else {
					throw new NoViableAltException(LT(1), getFilename());
				}

				}
				value_exp();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}

			}
			AST tmp529_AST = null;
			tmp529_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp529_AST);
			match(RIGHT_PAREN);
			set_fct_spec_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_avg||LA(1)==SQL2RW_max||LA(1)==SQL2RW_min||LA(1)==SQL2RW_sum)) {
			{
			switch ( LA(1)) {
			case SQL2RW_avg:
			{
				AST tmp530_AST = null;
				tmp530_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp530_AST);
				match(SQL2RW_avg);
				break;
			}
			case SQL2RW_max:
			{
				AST tmp531_AST = null;
				tmp531_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp531_AST);
				match(SQL2RW_max);
				break;
			}
			case SQL2RW_min:
			{
				AST tmp532_AST = null;
				tmp532_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp532_AST);
				match(SQL2RW_min);
				break;
			}
			case SQL2RW_sum:
			{
				AST tmp533_AST = null;
				tmp533_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp533_AST);
				match(SQL2RW_sum);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			AST tmp534_AST = null;
			tmp534_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp534_AST);
			match(LEFT_PAREN);
			{
			if ((LA(1)==SQL2RW_all||LA(1)==SQL2RW_distinct)) {
				set_quantifier();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else if ((_tokenSet_14.member(LA(1)))) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}

			}
			value_exp();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp535_AST = null;
			tmp535_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp535_AST);
			match(RIGHT_PAREN);
			set_fct_spec_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = set_fct_spec_AST;
	}

	public final void case_exp() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST case_exp_AST = null;

		if ((LA(1)==SQL2RW_coalesce||LA(1)==SQL2RW_nullif)) {
			case_abbreviation();
			astFactory.addASTChild(currentAST, returnAST);
			case_exp_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_case)) {
			case_spec();
			astFactory.addASTChild(currentAST, returnAST);
			case_exp_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = case_exp_AST;
	}

	public final void cast_spec() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST cast_spec_AST = null;

		AST tmp536_AST = null;
		tmp536_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp536_AST);
		match(SQL2RW_cast);
		AST tmp537_AST = null;
		tmp537_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp537_AST);
		match(LEFT_PAREN);
		cast_operand();
		astFactory.addASTChild(currentAST, returnAST);
		AST tmp538_AST = null;
		tmp538_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp538_AST);
		match(SQL2RW_as);
		cast_target();
		astFactory.addASTChild(currentAST, returnAST);
		AST tmp539_AST = null;
		tmp539_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp539_AST);
		match(RIGHT_PAREN);
		cast_spec_AST = (AST)currentAST.root;
		returnAST = cast_spec_AST;
	}

	public final void unsigned_value_spec() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST unsigned_value_spec_AST = null;

		if ((_tokenSet_60.member(LA(1)))) {
			unsigned_lit();
			astFactory.addASTChild(currentAST, returnAST);
			unsigned_value_spec_AST = (AST)currentAST.root;
		}
		else if ((_tokenSet_39.member(LA(1)))) {
			general_value_spec();
			astFactory.addASTChild(currentAST, returnAST);
			unsigned_value_spec_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = unsigned_value_spec_AST;
	}

	public final void scalar_subquery() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST scalar_subquery_AST = null;

		subquery();
		astFactory.addASTChild(currentAST, returnAST);
		scalar_subquery_AST = (AST)currentAST.root;
		returnAST = scalar_subquery_AST;
	}

	public final void set_quantifier() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST set_quantifier_AST = null;

		if ((LA(1)==SQL2RW_distinct)) {
			AST tmp540_AST = null;
			tmp540_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp540_AST);
			match(SQL2RW_distinct);
			set_quantifier_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_all)) {
			AST tmp541_AST = null;
			tmp541_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp541_AST);
			match(SQL2RW_all);
			set_quantifier_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = set_quantifier_AST;
	}

	public final void case_abbreviation() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST case_abbreviation_AST = null;

		if ((LA(1)==SQL2RW_nullif)) {
			AST tmp542_AST = null;
			tmp542_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp542_AST);
			match(SQL2RW_nullif);
			AST tmp543_AST = null;
			tmp543_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp543_AST);
			match(LEFT_PAREN);
			value_exp();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp544_AST = null;
			tmp544_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp544_AST);
			match(COMMA);
			value_exp();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp545_AST = null;
			tmp545_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp545_AST);
			match(RIGHT_PAREN);
			case_abbreviation_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_coalesce)) {
			AST tmp546_AST = null;
			tmp546_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp546_AST);
			match(SQL2RW_coalesce);
			AST tmp547_AST = null;
			tmp547_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp547_AST);
			match(LEFT_PAREN);
			value_exp();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop437:
			do {
				if ((LA(1)==COMMA)) {
					AST tmp548_AST = null;
					tmp548_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp548_AST);
					match(COMMA);
					value_exp();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop437;
				}

			} while (true);
			}
			AST tmp549_AST = null;
			tmp549_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp549_AST);
			match(RIGHT_PAREN);
			case_abbreviation_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = case_abbreviation_AST;
	}

	public final void case_spec() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST case_spec_AST = null;

		if ((LA(1)==SQL2RW_case) && (_tokenSet_14.member(LA(2)))) {
			simple_case();
			astFactory.addASTChild(currentAST, returnAST);
			case_spec_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_case) && (LA(2)==SQL2RW_when)) {
			searched_case();
			astFactory.addASTChild(currentAST, returnAST);
			case_spec_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = case_spec_AST;
	}

	public final void simple_case() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST simple_case_AST = null;

		AST tmp550_AST = null;
		tmp550_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp550_AST);
		match(SQL2RW_case);
		value_exp();
		astFactory.addASTChild(currentAST, returnAST);
		{
		int _cnt441=0;
		_loop441:
		do {
			if ((LA(1)==SQL2RW_when)) {
				simple_when_clause();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				if ( _cnt441>=1 ) { break _loop441; } else {throw new NoViableAltException(LT(1), getFilename());}
			}

			_cnt441++;
		} while (true);
		}
		{
		if ((LA(1)==SQL2RW_else)) {
			else_clause();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else if ((LA(1)==SQL2RW_end)) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		AST tmp551_AST = null;
		tmp551_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp551_AST);
		match(SQL2RW_end);
		simple_case_AST = (AST)currentAST.root;
		returnAST = simple_case_AST;
	}

	public final void searched_case() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST searched_case_AST = null;

		AST tmp552_AST = null;
		tmp552_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp552_AST);
		match(SQL2RW_case);
		{
		int _cnt448=0;
		_loop448:
		do {
			if ((LA(1)==SQL2RW_when)) {
				searched_when_clause();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				if ( _cnt448>=1 ) { break _loop448; } else {throw new NoViableAltException(LT(1), getFilename());}
			}

			_cnt448++;
		} while (true);
		}
		{
		if ((LA(1)==SQL2RW_else)) {
			else_clause();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else if ((LA(1)==SQL2RW_end)) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		AST tmp553_AST = null;
		tmp553_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp553_AST);
		match(SQL2RW_end);
		searched_case_AST = (AST)currentAST.root;
		returnAST = searched_case_AST;
	}

	public final void simple_when_clause() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST simple_when_clause_AST = null;

		AST tmp554_AST = null;
		tmp554_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp554_AST);
		match(SQL2RW_when);
		value_exp();
		astFactory.addASTChild(currentAST, returnAST);
		AST tmp555_AST = null;
		tmp555_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp555_AST);
		match(SQL2RW_then);
		result();
		astFactory.addASTChild(currentAST, returnAST);
		simple_when_clause_AST = (AST)currentAST.root;
		returnAST = simple_when_clause_AST;
	}

	public final void else_clause() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST else_clause_AST = null;

		AST tmp556_AST = null;
		tmp556_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp556_AST);
		match(SQL2RW_else);
		result();
		astFactory.addASTChild(currentAST, returnAST);
		else_clause_AST = (AST)currentAST.root;
		returnAST = else_clause_AST;
	}

	public final void result() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST result_AST = null;

		if ((_tokenSet_14.member(LA(1)))) {
			value_exp();
			astFactory.addASTChild(currentAST, returnAST);
			result_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_null)) {
			AST tmp557_AST = null;
			tmp557_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp557_AST);
			match(SQL2RW_null);
			result_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = result_AST;
	}

	public final void searched_when_clause() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST searched_when_clause_AST = null;

		AST tmp558_AST = null;
		tmp558_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp558_AST);
		match(SQL2RW_when);
		search_condition();
		astFactory.addASTChild(currentAST, returnAST);
		AST tmp559_AST = null;
		tmp559_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp559_AST);
		match(SQL2RW_then);
		result();
		astFactory.addASTChild(currentAST, returnAST);
		searched_when_clause_AST = (AST)currentAST.root;
		returnAST = searched_when_clause_AST;
	}

	public final void boolean_term() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST boolean_term_AST = null;

		boolean_factor();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop457:
		do {
			if ((LA(1)==SQL2RW_and)) {
				boolean_factor_op();
				astFactory.addASTChild(currentAST, returnAST);
				boolean_factor();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop457;
			}

		} while (true);
		}
		boolean_term_AST = (AST)currentAST.root;
		returnAST = boolean_term_AST;
	}

	public final void boolean_term_op() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST boolean_term_op_AST = null;

		AST tmp560_AST = null;
		tmp560_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp560_AST);
		match(SQL2RW_or);
		boolean_term_op_AST = (AST)currentAST.root;
		returnAST = boolean_term_op_AST;
	}

	public final void boolean_factor() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST boolean_factor_AST = null;

		{
		if ((LA(1)==SQL2RW_not)) {
			AST tmp561_AST = null;
			tmp561_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp561_AST);
			match(SQL2RW_not);
		}
		else if ((_tokenSet_61.member(LA(1)))) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		boolean_test();
		astFactory.addASTChild(currentAST, returnAST);
		boolean_factor_AST = (AST)currentAST.root;
		returnAST = boolean_factor_AST;
	}

	public final void boolean_factor_op() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST boolean_factor_op_AST = null;

		AST tmp562_AST = null;
		tmp562_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp562_AST);
		match(SQL2RW_and);
		boolean_factor_op_AST = (AST)currentAST.root;
		returnAST = boolean_factor_op_AST;
	}

	public final void boolean_test() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST boolean_test_AST = null;

		boolean_primary();
		astFactory.addASTChild(currentAST, returnAST);
		{
		if ((LA(1)==SQL2RW_is)) {
			AST tmp563_AST = null;
			tmp563_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp563_AST);
			match(SQL2RW_is);
			{
			if ((LA(1)==SQL2RW_not)) {
				AST tmp564_AST = null;
				tmp564_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp564_AST);
				match(SQL2RW_not);
			}
			else if ((LA(1)==SQL2RW_false||LA(1)==SQL2RW_true||LA(1)==SQL2RW_unknown)) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}

			}
			truth_value();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else if ((_tokenSet_62.member(LA(1)))) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		boolean_test_AST = (AST)currentAST.root;
		returnAST = boolean_test_AST;
	}

	public final void boolean_primary() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST boolean_primary_AST = null;

		boolean synPredMatched467 = false;
		if (((_tokenSet_61.member(LA(1))) && (_tokenSet_63.member(LA(2))))) {
			int _m467 = mark();
			synPredMatched467 = true;
			inputState.guessing++;
			try {
				{
				predicate();
				}
			}
			catch (RecognitionException pe) {
				synPredMatched467 = false;
			}
			rewind(_m467);
inputState.guessing--;
		}
		if ( synPredMatched467 ) {
			predicate();
			astFactory.addASTChild(currentAST, returnAST);
			boolean_primary_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==LEFT_PAREN) && (_tokenSet_49.member(LA(2)))) {
			AST tmp565_AST = null;
			tmp565_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp565_AST);
			match(LEFT_PAREN);
			search_condition();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp566_AST = null;
			tmp566_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp566_AST);
			match(RIGHT_PAREN);
			boolean_primary_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = boolean_primary_AST;
	}

	public final void truth_value() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST truth_value_AST = null;

		if ((LA(1)==SQL2RW_true)) {
			AST tmp567_AST = null;
			tmp567_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp567_AST);
			match(SQL2RW_true);
			truth_value_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_false)) {
			AST tmp568_AST = null;
			tmp568_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp568_AST);
			match(SQL2RW_false);
			truth_value_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_unknown)) {
			AST tmp569_AST = null;
			tmp569_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp569_AST);
			match(SQL2RW_unknown);
			truth_value_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = truth_value_AST;
	}

	public final void predicate() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST predicate_AST = null;

		if ((_tokenSet_64.member(LA(1)))) {
			row_value_constructor();
			astFactory.addASTChild(currentAST, returnAST);
			{
			switch ( LA(1)) {
			case SQL2RW_between:
			case SQL2RW_in:
			case SQL2RW_like:
			case SQL2RW_not:
			{
				{
				if ((LA(1)==SQL2RW_not)) {
					AST tmp570_AST = null;
					tmp570_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp570_AST);
					match(SQL2RW_not);
				}
				else if ((LA(1)==SQL2RW_between||LA(1)==SQL2RW_in||LA(1)==SQL2RW_like)) {
				}
				else {
					throw new NoViableAltException(LT(1), getFilename());
				}

				}
				{
				if ((LA(1)==SQL2RW_between)) {
					between_predicate();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else if ((LA(1)==SQL2RW_in)) {
					in_predicate();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else if ((LA(1)==SQL2RW_like)) {
					like_predicate();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					throw new NoViableAltException(LT(1), getFilename());
				}

				}
				break;
			}
			case SQL2RW_is:
			{
				null_predicate();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case SQL2RW_match:
			{
				match_predicate();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case SQL2RW_overlaps:
			{
				overlaps_predicate();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			default:
				if ((LA(1)==NOT_EQUALS_OP||LA(1)==LESS_THAN_OR_EQUALS_OP||LA(1)==GREATER_THAN_OR_EQUALS_OP||LA(1)==LESS_THAN_OP||LA(1)==EQUALS_OP||LA(1)==GREATER_THAN_OP) && (_tokenSet_64.member(LA(2)))) {
					comp_predicate();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else if ((LA(1)==NOT_EQUALS_OP||LA(1)==LESS_THAN_OR_EQUALS_OP||LA(1)==GREATER_THAN_OR_EQUALS_OP||LA(1)==LESS_THAN_OP||LA(1)==EQUALS_OP||LA(1)==GREATER_THAN_OP) && (LA(2)==SQL2RW_all||LA(2)==SQL2RW_any||LA(2)==SQL2RW_some)) {
					quantified_comp_predicate();
					astFactory.addASTChild(currentAST, returnAST);
				}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			predicate_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_exists)) {
			exists_predicate();
			astFactory.addASTChild(currentAST, returnAST);
			predicate_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_unique)) {
			unique_predicate();
			astFactory.addASTChild(currentAST, returnAST);
			predicate_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = predicate_AST;
	}

	public final void row_value_constructor() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST row_value_constructor_AST = null;

		boolean synPredMatched557 = false;
		if (((_tokenSet_64.member(LA(1))) && (_tokenSet_65.member(LA(2))))) {
			int _m557 = mark();
			synPredMatched557 = true;
			inputState.guessing++;
			try {
				{
				row_value_constructor_elem();
				}
			}
			catch (RecognitionException pe) {
				synPredMatched557 = false;
			}
			rewind(_m557);
inputState.guessing--;
		}
		if ( synPredMatched557 ) {
			row_value_constructor_elem();
			astFactory.addASTChild(currentAST, returnAST);
			row_value_constructor_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==LEFT_PAREN) && (_tokenSet_64.member(LA(2)))) {
			AST tmp571_AST = null;
			tmp571_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp571_AST);
			match(LEFT_PAREN);
			row_value_const_list();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp572_AST = null;
			tmp572_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp572_AST);
			match(RIGHT_PAREN);
			row_value_constructor_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = row_value_constructor_AST;
	}

	public final void comp_predicate() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST comp_predicate_AST = null;

		comp_op();
		astFactory.addASTChild(currentAST, returnAST);
		row_value_constructor();
		astFactory.addASTChild(currentAST, returnAST);
		comp_predicate_AST = (AST)currentAST.root;
		returnAST = comp_predicate_AST;
	}

	public final void between_predicate() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST between_predicate_AST = null;

		AST tmp573_AST = null;
		tmp573_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp573_AST);
		match(SQL2RW_between);
		row_value_constructor();
		astFactory.addASTChild(currentAST, returnAST);
		AST tmp574_AST = null;
		tmp574_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp574_AST);
		match(SQL2RW_and);
		row_value_constructor();
		astFactory.addASTChild(currentAST, returnAST);
		between_predicate_AST = (AST)currentAST.root;
		returnAST = between_predicate_AST;
	}

	public final void in_predicate() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST in_predicate_AST = null;

		AST tmp575_AST = null;
		tmp575_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp575_AST);
		match(SQL2RW_in);
		in_predicate_value();
		astFactory.addASTChild(currentAST, returnAST);
		in_predicate_AST = (AST)currentAST.root;
		returnAST = in_predicate_AST;
	}

	public final void like_predicate() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST like_predicate_AST = null;

		AST tmp576_AST = null;
		tmp576_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp576_AST);
		match(SQL2RW_like);
		pattern();
		astFactory.addASTChild(currentAST, returnAST);
		{
		if ((LA(1)==SQL2RW_escape)) {
			AST tmp577_AST = null;
			tmp577_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp577_AST);
			match(SQL2RW_escape);
			escape_char();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else if ((_tokenSet_66.member(LA(1)))) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		like_predicate_AST = (AST)currentAST.root;
		returnAST = like_predicate_AST;
	}

	public final void null_predicate() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST null_predicate_AST = null;

		AST tmp578_AST = null;
		tmp578_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp578_AST);
		match(SQL2RW_is);
		{
		if ((LA(1)==SQL2RW_not)) {
			AST tmp579_AST = null;
			tmp579_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp579_AST);
			match(SQL2RW_not);
		}
		else if ((LA(1)==SQL2RW_null)) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		AST tmp580_AST = null;
		tmp580_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp580_AST);
		match(SQL2RW_null);
		null_predicate_AST = (AST)currentAST.root;
		returnAST = null_predicate_AST;
	}

	public final void quantified_comp_predicate() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST quantified_comp_predicate_AST = null;

		comp_op();
		astFactory.addASTChild(currentAST, returnAST);
		{
		if ((LA(1)==SQL2RW_all)) {
			AST tmp581_AST = null;
			tmp581_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp581_AST);
			match(SQL2RW_all);
		}
		else if ((LA(1)==SQL2RW_some)) {
			AST tmp582_AST = null;
			tmp582_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp582_AST);
			match(SQL2RW_some);
		}
		else if ((LA(1)==SQL2RW_any)) {
			AST tmp583_AST = null;
			tmp583_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp583_AST);
			match(SQL2RW_any);
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		table_subquery();
		astFactory.addASTChild(currentAST, returnAST);
		quantified_comp_predicate_AST = (AST)currentAST.root;
		returnAST = quantified_comp_predicate_AST;
	}

	public final void match_predicate() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST match_predicate_AST = null;

		AST tmp584_AST = null;
		tmp584_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp584_AST);
		match(SQL2RW_match);
		{
		if ((LA(1)==SQL2RW_unique)) {
			AST tmp585_AST = null;
			tmp585_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp585_AST);
			match(SQL2RW_unique);
		}
		else if ((LA(1)==SQL2RW_full||LA(1)==SQL2RW_partial||LA(1)==LEFT_PAREN)) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		{
		if ((LA(1)==SQL2RW_full)) {
			AST tmp586_AST = null;
			tmp586_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp586_AST);
			match(SQL2RW_full);
		}
		else if ((LA(1)==SQL2RW_partial)) {
			AST tmp587_AST = null;
			tmp587_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp587_AST);
			match(SQL2RW_partial);
		}
		else if ((LA(1)==LEFT_PAREN)) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		table_subquery();
		astFactory.addASTChild(currentAST, returnAST);
		match_predicate_AST = (AST)currentAST.root;
		returnAST = match_predicate_AST;
	}

	public final void overlaps_predicate() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST overlaps_predicate_AST = null;

		AST tmp588_AST = null;
		tmp588_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp588_AST);
		match(SQL2RW_overlaps);
		row_value_constructor();
		astFactory.addASTChild(currentAST, returnAST);
		overlaps_predicate_AST = (AST)currentAST.root;
		returnAST = overlaps_predicate_AST;
	}

	public final void exists_predicate() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST exists_predicate_AST = null;

		AST tmp589_AST = null;
		tmp589_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp589_AST);
		match(SQL2RW_exists);
		table_subquery();
		astFactory.addASTChild(currentAST, returnAST);
		exists_predicate_AST = (AST)currentAST.root;
		returnAST = exists_predicate_AST;
	}

	public final void unique_predicate() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST unique_predicate_AST = null;

		AST tmp590_AST = null;
		tmp590_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp590_AST);
		match(SQL2RW_unique);
		table_subquery();
		astFactory.addASTChild(currentAST, returnAST);
		unique_predicate_AST = (AST)currentAST.root;
		returnAST = unique_predicate_AST;
	}

	public final void comp_op() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST comp_op_AST = null;

		switch ( LA(1)) {
		case EQUALS_OP:
		{
			AST tmp591_AST = null;
			tmp591_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp591_AST);
			match(EQUALS_OP);
			comp_op_AST = (AST)currentAST.root;
			break;
		}
		case NOT_EQUALS_OP:
		{
			AST tmp592_AST = null;
			tmp592_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp592_AST);
			match(NOT_EQUALS_OP);
			comp_op_AST = (AST)currentAST.root;
			break;
		}
		case LESS_THAN_OP:
		{
			AST tmp593_AST = null;
			tmp593_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp593_AST);
			match(LESS_THAN_OP);
			comp_op_AST = (AST)currentAST.root;
			break;
		}
		case GREATER_THAN_OP:
		{
			AST tmp594_AST = null;
			tmp594_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp594_AST);
			match(GREATER_THAN_OP);
			comp_op_AST = (AST)currentAST.root;
			break;
		}
		case LESS_THAN_OR_EQUALS_OP:
		{
			AST tmp595_AST = null;
			tmp595_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp595_AST);
			match(LESS_THAN_OR_EQUALS_OP);
			comp_op_AST = (AST)currentAST.root;
			break;
		}
		case GREATER_THAN_OR_EQUALS_OP:
		{
			AST tmp596_AST = null;
			tmp596_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp596_AST);
			match(GREATER_THAN_OR_EQUALS_OP);
			comp_op_AST = (AST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = comp_op_AST;
	}

	public final void in_predicate_value() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST in_predicate_value_AST = null;

		boolean synPredMatched478 = false;
		if (((LA(1)==LEFT_PAREN) && (_tokenSet_7.member(LA(2))))) {
			int _m478 = mark();
			synPredMatched478 = true;
			inputState.guessing++;
			try {
				{
				table_subquery();
				}
			}
			catch (RecognitionException pe) {
				synPredMatched478 = false;
			}
			rewind(_m478);
inputState.guessing--;
		}
		if ( synPredMatched478 ) {
			table_subquery();
			astFactory.addASTChild(currentAST, returnAST);
			in_predicate_value_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==LEFT_PAREN) && (_tokenSet_14.member(LA(2)))) {
			AST tmp597_AST = null;
			tmp597_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp597_AST);
			match(LEFT_PAREN);
			in_value_list();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp598_AST = null;
			tmp598_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp598_AST);
			match(RIGHT_PAREN);
			in_predicate_value_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = in_predicate_value_AST;
	}

	public final void table_subquery() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST table_subquery_AST = null;

		subquery();
		astFactory.addASTChild(currentAST, returnAST);
		table_subquery_AST = (AST)currentAST.root;
		returnAST = table_subquery_AST;
	}

	public final void in_value_list() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST in_value_list_AST = null;

		value_exp();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop481:
		do {
			if ((LA(1)==COMMA)) {
				AST tmp599_AST = null;
				tmp599_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp599_AST);
				match(COMMA);
				value_exp();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop481;
			}

		} while (true);
		}
		in_value_list_AST = (AST)currentAST.root;
		returnAST = in_value_list_AST;
	}

	public final void pattern() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST pattern_AST = null;

		char_value_exp();
		astFactory.addASTChild(currentAST, returnAST);
		pattern_AST = (AST)currentAST.root;
		returnAST = pattern_AST;
	}

	public final void escape_char() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST escape_char_AST = null;

		char_value_exp();
		astFactory.addASTChild(currentAST, returnAST);
		escape_char_AST = (AST)currentAST.root;
		returnAST = escape_char_AST;
	}

	public final void char_value_exp() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST char_value_exp_AST = null;

		value_exp();
		astFactory.addASTChild(currentAST, returnAST);
		char_value_exp_AST = (AST)currentAST.root;
		returnAST = char_value_exp_AST;
	}

	public final void cast_operand() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST cast_operand_AST = null;

		if ((_tokenSet_14.member(LA(1)))) {
			value_exp();
			astFactory.addASTChild(currentAST, returnAST);
			cast_operand_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_null)) {
			AST tmp600_AST = null;
			tmp600_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp600_AST);
			match(SQL2RW_null);
			cast_operand_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = cast_operand_AST;
	}

	public final void cast_target() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST cast_target_AST = null;

		if ((_tokenSet_5.member(LA(1))) && (_tokenSet_67.member(LA(2)))) {
			domain_name();
			astFactory.addASTChild(currentAST, returnAST);
			cast_target_AST = (AST)currentAST.root;
		}
		else if ((_tokenSet_12.member(LA(1))) && (_tokenSet_68.member(LA(2)))) {
			data_type();
			astFactory.addASTChild(currentAST, returnAST);
			cast_target_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = cast_target_AST;
	}

	public final void char_string_type() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST char_string_type_AST = null;

		if ((LA(1)==SQL2RW_character) && (_tokenSet_69.member(LA(2)))) {
			AST tmp601_AST = null;
			tmp601_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp601_AST);
			match(SQL2RW_character);
			{
			if ((LA(1)==LEFT_PAREN)) {
				AST tmp602_AST = null;
				tmp602_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp602_AST);
				match(LEFT_PAREN);
				length();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp603_AST = null;
				tmp603_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp603_AST);
				match(RIGHT_PAREN);
			}
			else if ((_tokenSet_70.member(LA(1)))) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}

			}
			char_string_type_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_char) && (_tokenSet_69.member(LA(2)))) {
			AST tmp604_AST = null;
			tmp604_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp604_AST);
			match(SQL2RW_char);
			{
			if ((LA(1)==LEFT_PAREN)) {
				AST tmp605_AST = null;
				tmp605_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp605_AST);
				match(LEFT_PAREN);
				length();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp606_AST = null;
				tmp606_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp606_AST);
				match(RIGHT_PAREN);
			}
			else if ((_tokenSet_70.member(LA(1)))) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}

			}
			char_string_type_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_character) && (LA(2)==SQL2RW_varying)) {
			AST tmp607_AST = null;
			tmp607_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp607_AST);
			match(SQL2RW_character);
			AST tmp608_AST = null;
			tmp608_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp608_AST);
			match(SQL2RW_varying);
			AST tmp609_AST = null;
			tmp609_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp609_AST);
			match(LEFT_PAREN);
			length();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp610_AST = null;
			tmp610_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp610_AST);
			match(RIGHT_PAREN);
			char_string_type_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_char) && (LA(2)==SQL2RW_varying)) {
			AST tmp611_AST = null;
			tmp611_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp611_AST);
			match(SQL2RW_char);
			AST tmp612_AST = null;
			tmp612_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp612_AST);
			match(SQL2RW_varying);
			AST tmp613_AST = null;
			tmp613_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp613_AST);
			match(LEFT_PAREN);
			length();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp614_AST = null;
			tmp614_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp614_AST);
			match(RIGHT_PAREN);
			char_string_type_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_varchar)) {
			AST tmp615_AST = null;
			tmp615_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp615_AST);
			match(SQL2RW_varchar);
			AST tmp616_AST = null;
			tmp616_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp616_AST);
			match(LEFT_PAREN);
			length();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp617_AST = null;
			tmp617_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp617_AST);
			match(RIGHT_PAREN);
			char_string_type_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==LITERAL_text)) {
			AST tmp618_AST = null;
			tmp618_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp618_AST);
			match(LITERAL_text);
			char_string_type_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = char_string_type_AST;
	}

	public final void national_char_string_type() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST national_char_string_type_AST = null;

		if ((LA(1)==SQL2RW_national)) {
			AST tmp619_AST = null;
			tmp619_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp619_AST);
			match(SQL2RW_national);
			{
			if ((LA(1)==SQL2RW_character) && (_tokenSet_71.member(LA(2)))) {
				AST tmp620_AST = null;
				tmp620_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp620_AST);
				match(SQL2RW_character);
				{
				if ((LA(1)==LEFT_PAREN)) {
					AST tmp621_AST = null;
					tmp621_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp621_AST);
					match(LEFT_PAREN);
					length();
					astFactory.addASTChild(currentAST, returnAST);
					AST tmp622_AST = null;
					tmp622_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp622_AST);
					match(RIGHT_PAREN);
				}
				else if ((_tokenSet_28.member(LA(1)))) {
				}
				else {
					throw new NoViableAltException(LT(1), getFilename());
				}

				}
			}
			else if ((LA(1)==SQL2RW_char) && (_tokenSet_71.member(LA(2)))) {
				AST tmp623_AST = null;
				tmp623_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp623_AST);
				match(SQL2RW_char);
				{
				if ((LA(1)==LEFT_PAREN)) {
					AST tmp624_AST = null;
					tmp624_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp624_AST);
					match(LEFT_PAREN);
					length();
					astFactory.addASTChild(currentAST, returnAST);
					AST tmp625_AST = null;
					tmp625_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp625_AST);
					match(RIGHT_PAREN);
				}
				else if ((_tokenSet_28.member(LA(1)))) {
				}
				else {
					throw new NoViableAltException(LT(1), getFilename());
				}

				}
			}
			else if ((LA(1)==SQL2RW_character) && (LA(2)==SQL2RW_varying)) {
				AST tmp626_AST = null;
				tmp626_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp626_AST);
				match(SQL2RW_character);
				AST tmp627_AST = null;
				tmp627_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp627_AST);
				match(SQL2RW_varying);
				AST tmp628_AST = null;
				tmp628_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp628_AST);
				match(LEFT_PAREN);
				length();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp629_AST = null;
				tmp629_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp629_AST);
				match(RIGHT_PAREN);
			}
			else if ((LA(1)==SQL2RW_char) && (LA(2)==SQL2RW_varying)) {
				AST tmp630_AST = null;
				tmp630_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp630_AST);
				match(SQL2RW_char);
				AST tmp631_AST = null;
				tmp631_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp631_AST);
				match(SQL2RW_varying);
				AST tmp632_AST = null;
				tmp632_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp632_AST);
				match(LEFT_PAREN);
				length();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp633_AST = null;
				tmp633_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp633_AST);
				match(RIGHT_PAREN);
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}

			}
			national_char_string_type_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_nchar) && (_tokenSet_71.member(LA(2)))) {
			AST tmp634_AST = null;
			tmp634_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp634_AST);
			match(SQL2RW_nchar);
			{
			if ((LA(1)==LEFT_PAREN)) {
				AST tmp635_AST = null;
				tmp635_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp635_AST);
				match(LEFT_PAREN);
				length();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp636_AST = null;
				tmp636_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp636_AST);
				match(RIGHT_PAREN);
			}
			else if ((_tokenSet_28.member(LA(1)))) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}

			}
			national_char_string_type_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_nchar) && (LA(2)==SQL2RW_varying)) {
			AST tmp637_AST = null;
			tmp637_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp637_AST);
			match(SQL2RW_nchar);
			AST tmp638_AST = null;
			tmp638_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp638_AST);
			match(SQL2RW_varying);
			AST tmp639_AST = null;
			tmp639_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp639_AST);
			match(LEFT_PAREN);
			length();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp640_AST = null;
			tmp640_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp640_AST);
			match(RIGHT_PAREN);
			national_char_string_type_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = national_char_string_type_AST;
	}

	public final void bit_string_type() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST bit_string_type_AST = null;

		if ((LA(1)==SQL2RW_bit) && (_tokenSet_71.member(LA(2)))) {
			AST tmp641_AST = null;
			tmp641_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp641_AST);
			match(SQL2RW_bit);
			{
			if ((LA(1)==LEFT_PAREN)) {
				AST tmp642_AST = null;
				tmp642_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp642_AST);
				match(LEFT_PAREN);
				length();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp643_AST = null;
				tmp643_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp643_AST);
				match(RIGHT_PAREN);
			}
			else if ((_tokenSet_28.member(LA(1)))) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}

			}
			bit_string_type_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_bit) && (LA(2)==SQL2RW_varying)) {
			AST tmp644_AST = null;
			tmp644_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp644_AST);
			match(SQL2RW_bit);
			AST tmp645_AST = null;
			tmp645_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp645_AST);
			match(SQL2RW_varying);
			AST tmp646_AST = null;
			tmp646_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp646_AST);
			match(LEFT_PAREN);
			length();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp647_AST = null;
			tmp647_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp647_AST);
			match(RIGHT_PAREN);
			bit_string_type_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = bit_string_type_AST;
	}

	public final void num_type() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST num_type_AST = null;

		if ((LA(1)==SQL2NRW_number||LA(1)==SQL2RW_dec||LA(1)==SQL2RW_decimal||LA(1)==SQL2RW_int||LA(1)==SQL2RW_integer||LA(1)==SQL2RW_numeric||LA(1)==SQL2RW_smallint)) {
			exact_num_type();
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				builder.setDomainOfLastAttribute( Importer.INTEGER );
			}
			num_type_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_double||LA(1)==SQL2RW_float||LA(1)==SQL2RW_real)) {
			approximate_num_type();
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				builder.setDomainOfLastAttribute( Importer.DOUBLE );
			}
			num_type_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = num_type_AST;
	}

	public final void datetime_type() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST datetime_type_AST = null;

		if ((LA(1)==SQL2RW_date)) {
			AST tmp648_AST = null;
			tmp648_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp648_AST);
			match(SQL2RW_date);
			datetime_type_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_time)) {
			AST tmp649_AST = null;
			tmp649_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp649_AST);
			match(SQL2RW_time);
			{
			if ((LA(1)==LEFT_PAREN)) {
				AST tmp650_AST = null;
				tmp650_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp650_AST);
				match(LEFT_PAREN);
				time_precision();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp651_AST = null;
				tmp651_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp651_AST);
				match(RIGHT_PAREN);
			}
			else if ((_tokenSet_72.member(LA(1)))) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}

			}
			{
			if ((LA(1)==SQL2RW_with)) {
				AST tmp652_AST = null;
				tmp652_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp652_AST);
				match(SQL2RW_with);
				AST tmp653_AST = null;
				tmp653_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp653_AST);
				match(SQL2RW_time);
				AST tmp654_AST = null;
				tmp654_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp654_AST);
				match(SQL2RW_zone);
			}
			else if ((_tokenSet_28.member(LA(1)))) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}

			}
			datetime_type_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_timestamp)) {
			AST tmp655_AST = null;
			tmp655_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp655_AST);
			match(SQL2RW_timestamp);
			{
			if ((LA(1)==LEFT_PAREN)) {
				AST tmp656_AST = null;
				tmp656_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp656_AST);
				match(LEFT_PAREN);
				timestamp_precision();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp657_AST = null;
				tmp657_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp657_AST);
				match(RIGHT_PAREN);
			}
			else if ((_tokenSet_72.member(LA(1)))) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}

			}
			{
			if ((LA(1)==SQL2RW_with)) {
				AST tmp658_AST = null;
				tmp658_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp658_AST);
				match(SQL2RW_with);
				AST tmp659_AST = null;
				tmp659_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp659_AST);
				match(SQL2RW_time);
				AST tmp660_AST = null;
				tmp660_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp660_AST);
				match(SQL2RW_zone);
			}
			else if ((_tokenSet_28.member(LA(1)))) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}

			}
			datetime_type_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = datetime_type_AST;
	}

	public final void interval_type() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST interval_type_AST = null;

		AST tmp661_AST = null;
		tmp661_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp661_AST);
		match(SQL2RW_interval);
		interval_qualifier();
		astFactory.addASTChild(currentAST, returnAST);
		interval_type_AST = (AST)currentAST.root;
		returnAST = interval_type_AST;
	}

	public final void length() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST length_AST = null;

		AST tmp662_AST = null;
		tmp662_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp662_AST);
		match(UNSIGNED_INTEGER);
		length_AST = (AST)currentAST.root;
		returnAST = length_AST;
	}

	public final void precision() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST precision_AST = null;

		AST tmp663_AST = null;
		tmp663_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp663_AST);
		match(UNSIGNED_INTEGER);
		precision_AST = (AST)currentAST.root;
		returnAST = precision_AST;
	}

	public final void scale() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST scale_AST = null;

		AST tmp664_AST = null;
		tmp664_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp664_AST);
		match(UNSIGNED_INTEGER);
		scale_AST = (AST)currentAST.root;
		returnAST = scale_AST;
	}

	public final void exact_num_type() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST exact_num_type_AST = null;

		switch ( LA(1)) {
		case SQL2RW_numeric:
		{
			AST tmp665_AST = null;
			tmp665_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp665_AST);
			match(SQL2RW_numeric);
			{
			if ((LA(1)==LEFT_PAREN)) {
				AST tmp666_AST = null;
				tmp666_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp666_AST);
				match(LEFT_PAREN);
				precision();
				astFactory.addASTChild(currentAST, returnAST);
				{
				if ((LA(1)==COMMA)) {
					AST tmp667_AST = null;
					tmp667_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp667_AST);
					match(COMMA);
					scale();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else if ((LA(1)==RIGHT_PAREN)) {
				}
				else {
					throw new NoViableAltException(LT(1), getFilename());
				}

				}
				AST tmp668_AST = null;
				tmp668_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp668_AST);
				match(RIGHT_PAREN);
			}
			else if ((_tokenSet_28.member(LA(1)))) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}

			}
			exact_num_type_AST = (AST)currentAST.root;
			break;
		}
		case SQL2NRW_number:
		{
			AST tmp669_AST = null;
			tmp669_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp669_AST);
			match(SQL2NRW_number);
			{
			if ((LA(1)==LEFT_PAREN)) {
				AST tmp670_AST = null;
				tmp670_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp670_AST);
				match(LEFT_PAREN);
				precision();
				astFactory.addASTChild(currentAST, returnAST);
				{
				if ((LA(1)==COMMA)) {
					AST tmp671_AST = null;
					tmp671_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp671_AST);
					match(COMMA);
					scale();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else if ((LA(1)==RIGHT_PAREN)) {
				}
				else {
					throw new NoViableAltException(LT(1), getFilename());
				}

				}
				AST tmp672_AST = null;
				tmp672_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp672_AST);
				match(RIGHT_PAREN);
			}
			else if ((_tokenSet_28.member(LA(1)))) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}

			}
			exact_num_type_AST = (AST)currentAST.root;
			break;
		}
		case SQL2RW_decimal:
		{
			AST tmp673_AST = null;
			tmp673_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp673_AST);
			match(SQL2RW_decimal);
			{
			if ((LA(1)==LEFT_PAREN)) {
				AST tmp674_AST = null;
				tmp674_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp674_AST);
				match(LEFT_PAREN);
				precision();
				astFactory.addASTChild(currentAST, returnAST);
				{
				if ((LA(1)==COMMA)) {
					AST tmp675_AST = null;
					tmp675_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp675_AST);
					match(COMMA);
					scale();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else if ((LA(1)==RIGHT_PAREN)) {
				}
				else {
					throw new NoViableAltException(LT(1), getFilename());
				}

				}
				AST tmp676_AST = null;
				tmp676_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp676_AST);
				match(RIGHT_PAREN);
			}
			else if ((_tokenSet_28.member(LA(1)))) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}

			}
			exact_num_type_AST = (AST)currentAST.root;
			break;
		}
		case SQL2RW_dec:
		{
			AST tmp677_AST = null;
			tmp677_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp677_AST);
			match(SQL2RW_dec);
			{
			if ((LA(1)==LEFT_PAREN)) {
				AST tmp678_AST = null;
				tmp678_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp678_AST);
				match(LEFT_PAREN);
				precision();
				astFactory.addASTChild(currentAST, returnAST);
				{
				if ((LA(1)==COMMA)) {
					AST tmp679_AST = null;
					tmp679_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp679_AST);
					match(COMMA);
					scale();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else if ((LA(1)==RIGHT_PAREN)) {
				}
				else {
					throw new NoViableAltException(LT(1), getFilename());
				}

				}
				AST tmp680_AST = null;
				tmp680_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp680_AST);
				match(RIGHT_PAREN);
			}
			else if ((_tokenSet_28.member(LA(1)))) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}

			}
			exact_num_type_AST = (AST)currentAST.root;
			break;
		}
		case SQL2RW_integer:
		{
			AST tmp681_AST = null;
			tmp681_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp681_AST);
			match(SQL2RW_integer);
			exact_num_type_AST = (AST)currentAST.root;
			break;
		}
		case SQL2RW_int:
		{
			AST tmp682_AST = null;
			tmp682_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp682_AST);
			match(SQL2RW_int);
			exact_num_type_AST = (AST)currentAST.root;
			break;
		}
		case SQL2RW_smallint:
		{
			AST tmp683_AST = null;
			tmp683_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp683_AST);
			match(SQL2RW_smallint);
			exact_num_type_AST = (AST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = exact_num_type_AST;
	}

	public final void approximate_num_type() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST approximate_num_type_AST = null;

		if ((LA(1)==SQL2RW_float)) {
			AST tmp684_AST = null;
			tmp684_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp684_AST);
			match(SQL2RW_float);
			{
			if ((LA(1)==LEFT_PAREN)) {
				AST tmp685_AST = null;
				tmp685_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp685_AST);
				match(LEFT_PAREN);
				precision();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp686_AST = null;
				tmp686_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp686_AST);
				match(RIGHT_PAREN);
			}
			else if ((_tokenSet_28.member(LA(1)))) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}

			}
			approximate_num_type_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_real)) {
			AST tmp687_AST = null;
			tmp687_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp687_AST);
			match(SQL2RW_real);
			approximate_num_type_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_double)) {
			AST tmp688_AST = null;
			tmp688_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp688_AST);
			match(SQL2RW_double);
			AST tmp689_AST = null;
			tmp689_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp689_AST);
			match(SQL2RW_precision);
			approximate_num_type_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = approximate_num_type_AST;
	}

	public final void time_precision() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST time_precision_AST = null;

		AST tmp690_AST = null;
		tmp690_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp690_AST);
		match(UNSIGNED_INTEGER);
		time_precision_AST = (AST)currentAST.root;
		returnAST = time_precision_AST;
	}

	public final void timestamp_precision() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST timestamp_precision_AST = null;

		AST tmp691_AST = null;
		tmp691_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp691_AST);
		match(UNSIGNED_INTEGER);
		timestamp_precision_AST = (AST)currentAST.root;
		returnAST = timestamp_precision_AST;
	}

	public final void interval_qualifier() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST interval_qualifier_AST = null;

		if ((LA(1)==SQL2RW_day||LA(1)==SQL2RW_hour||LA(1)==SQL2RW_minute||LA(1)==SQL2RW_month||LA(1)==SQL2RW_year)) {
			start_field();
			astFactory.addASTChild(currentAST, returnAST);
			{
			if ((LA(1)==SQL2RW_to)) {
				AST tmp692_AST = null;
				tmp692_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp692_AST);
				match(SQL2RW_to);
				end_field();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else if ((_tokenSet_73.member(LA(1)))) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}

			}
			interval_qualifier_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_second)) {
			AST tmp693_AST = null;
			tmp693_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp693_AST);
			match(SQL2RW_second);
			{
			if ((LA(1)==LEFT_PAREN)) {
				AST tmp694_AST = null;
				tmp694_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp694_AST);
				match(LEFT_PAREN);
				AST tmp695_AST = null;
				tmp695_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp695_AST);
				match(UNSIGNED_INTEGER);
				{
				if ((LA(1)==COMMA)) {
					AST tmp696_AST = null;
					tmp696_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp696_AST);
					match(COMMA);
					AST tmp697_AST = null;
					tmp697_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp697_AST);
					match(UNSIGNED_INTEGER);
				}
				else if ((LA(1)==RIGHT_PAREN)) {
				}
				else {
					throw new NoViableAltException(LT(1), getFilename());
				}

				}
				AST tmp698_AST = null;
				tmp698_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp698_AST);
				match(RIGHT_PAREN);
			}
			else if ((_tokenSet_73.member(LA(1)))) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}

			}
			interval_qualifier_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = interval_qualifier_AST;
	}

	public final void subquery() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST subquery_AST = null;

		AST tmp699_AST = null;
		tmp699_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp699_AST);
		match(LEFT_PAREN);
		query_exp();
		astFactory.addASTChild(currentAST, returnAST);
		AST tmp700_AST = null;
		tmp700_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp700_AST);
		match(RIGHT_PAREN);
		subquery_AST = (AST)currentAST.root;
		returnAST = subquery_AST;
	}

	public final void query_term() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST query_term_AST = null;

		query_primary();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop544:
		do {
			if ((LA(1)==SQL2RW_intersect)) {
				AST tmp701_AST = null;
				tmp701_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp701_AST);
				match(SQL2RW_intersect);
				{
				if ((LA(1)==SQL2RW_all)) {
					AST tmp702_AST = null;
					tmp702_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp702_AST);
					match(SQL2RW_all);
				}
				else if ((_tokenSet_32.member(LA(1)))) {
				}
				else {
					throw new NoViableAltException(LT(1), getFilename());
				}

				}
				{
				if ((LA(1)==SQL2RW_corresponding)) {
					corresponding_spec();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else if ((_tokenSet_7.member(LA(1)))) {
				}
				else {
					throw new NoViableAltException(LT(1), getFilename());
				}

				}
				query_primary();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop544;
			}

		} while (true);
		}
		query_term_AST = (AST)currentAST.root;
		returnAST = query_term_AST;
	}

	public final void corresponding_spec() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST corresponding_spec_AST = null;

		AST tmp703_AST = null;
		tmp703_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp703_AST);
		match(SQL2RW_corresponding);
		{
		if ((LA(1)==SQL2RW_by)) {
			AST tmp704_AST = null;
			tmp704_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp704_AST);
			match(SQL2RW_by);
			AST tmp705_AST = null;
			tmp705_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp705_AST);
			match(LEFT_PAREN);
			column_name_list();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp706_AST = null;
			tmp706_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp706_AST);
			match(RIGHT_PAREN);
		}
		else if ((_tokenSet_7.member(LA(1)))) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		corresponding_spec_AST = (AST)currentAST.root;
		returnAST = corresponding_spec_AST;
	}

	public final void query_spec() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST query_spec_AST = null;

		AST tmp707_AST = null;
		tmp707_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp707_AST);
		match(SQL2RW_select);
		{
		if ((LA(1)==SQL2RW_all||LA(1)==SQL2RW_distinct)) {
			set_quantifier();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else if ((_tokenSet_74.member(LA(1)))) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		select_list();
		astFactory.addASTChild(currentAST, returnAST);
		{
		if ((LA(1)==SQL2RW_into)) {
			into_clause();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else if ((LA(1)==SQL2RW_from)) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		table_exp();
		astFactory.addASTChild(currentAST, returnAST);
		query_spec_AST = (AST)currentAST.root;
		returnAST = query_spec_AST;
	}

	public final void table_value_constructor() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST table_value_constructor_AST = null;

		AST tmp708_AST = null;
		tmp708_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp708_AST);
		match(SQL2RW_values);
		table_value_const_list();
		astFactory.addASTChild(currentAST, returnAST);
		table_value_constructor_AST = (AST)currentAST.root;
		returnAST = table_value_constructor_AST;
	}

	public final void explicit_table() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST explicit_table_AST = null;

		AST tmp709_AST = null;
		tmp709_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp709_AST);
		match(SQL2RW_table);
		table_name();
		astFactory.addASTChild(currentAST, returnAST);
		explicit_table_AST = (AST)currentAST.root;
		returnAST = explicit_table_AST;
	}

	public final void table_exp() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST table_exp_AST = null;

		from_clause();
		astFactory.addASTChild(currentAST, returnAST);
		{
		if ((LA(1)==SQL2RW_where)) {
			where_clause();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else if ((_tokenSet_75.member(LA(1)))) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		{
		if ((LA(1)==SQL2RW_group)) {
			group_by_clause();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else if ((_tokenSet_76.member(LA(1)))) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		{
		if ((LA(1)==SQL2RW_having)) {
			having_clause();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else if ((_tokenSet_77.member(LA(1)))) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		table_exp_AST = (AST)currentAST.root;
		returnAST = table_exp_AST;
	}

	public final void table_value_const_list() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST table_value_const_list_AST = null;

		row_value_constructor();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop554:
		do {
			if ((LA(1)==COMMA)) {
				AST tmp710_AST = null;
				tmp710_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp710_AST);
				match(COMMA);
				row_value_constructor();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop554;
			}

		} while (true);
		}
		table_value_const_list_AST = (AST)currentAST.root;
		returnAST = table_value_const_list_AST;
	}

	public final void row_value_constructor_elem() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST row_value_constructor_elem_AST = null;

		if ((_tokenSet_14.member(LA(1)))) {
			value_exp();
			astFactory.addASTChild(currentAST, returnAST);
			row_value_constructor_elem_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_null)) {
			AST tmp711_AST = null;
			tmp711_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp711_AST);
			match(SQL2RW_null);
			row_value_constructor_elem_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_default)) {
			AST tmp712_AST = null;
			tmp712_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp712_AST);
			match(SQL2RW_default);
			row_value_constructor_elem_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = row_value_constructor_elem_AST;
	}

	public final void row_value_const_list() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST row_value_const_list_AST = null;

		row_value_constructor_elem();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop561:
		do {
			if ((LA(1)==COMMA)) {
				AST tmp713_AST = null;
				tmp713_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp713_AST);
				match(COMMA);
				row_value_constructor_elem();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop561;
			}

		} while (true);
		}
		row_value_const_list_AST = (AST)currentAST.root;
		returnAST = row_value_const_list_AST;
	}

	public final void table_ref_aux() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST table_ref_aux_AST = null;

		{
		if ((_tokenSet_11.member(LA(1)))) {
			table_name();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else if ((LA(1)==LEFT_PAREN)) {
			table_subquery();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		{
		if ((_tokenSet_78.member(LA(1)))) {
			{
			if ((LA(1)==SQL2RW_as)) {
				AST tmp714_AST = null;
				tmp714_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp714_AST);
				match(SQL2RW_as);
			}
			else if ((_tokenSet_5.member(LA(1)))) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}

			}
			correlation_name();
			astFactory.addASTChild(currentAST, returnAST);
			{
			if ((LA(1)==LEFT_PAREN)) {
				AST tmp715_AST = null;
				tmp715_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp715_AST);
				match(LEFT_PAREN);
				derived_column_list();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp716_AST = null;
				tmp716_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp716_AST);
				match(RIGHT_PAREN);
			}
			else if ((_tokenSet_79.member(LA(1)))) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}

			}
		}
		else if ((_tokenSet_79.member(LA(1)))) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		table_ref_aux_AST = (AST)currentAST.root;
		returnAST = table_ref_aux_AST;
	}

	public final void qualified_join() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST qualified_join_AST = null;

		if ((LA(1)==SQL2RW_full||LA(1)==SQL2RW_inner||LA(1)==SQL2RW_join||LA(1)==SQL2RW_left||LA(1)==SQL2RW_right)) {
			{
			if ((LA(1)==SQL2RW_inner)) {
				AST tmp717_AST = null;
				tmp717_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp717_AST);
				match(SQL2RW_inner);
			}
			else if ((LA(1)==SQL2RW_full||LA(1)==SQL2RW_left||LA(1)==SQL2RW_right)) {
				outer_join_type();
				astFactory.addASTChild(currentAST, returnAST);
				{
				if ((LA(1)==SQL2RW_outer)) {
					AST tmp718_AST = null;
					tmp718_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp718_AST);
					match(SQL2RW_outer);
				}
				else if ((LA(1)==SQL2RW_join)) {
				}
				else {
					throw new NoViableAltException(LT(1), getFilename());
				}

				}
			}
			else if ((LA(1)==SQL2RW_join)) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}

			}
			AST tmp719_AST = null;
			tmp719_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp719_AST);
			match(SQL2RW_join);
			table_ref();
			astFactory.addASTChild(currentAST, returnAST);
			join_spec();
			astFactory.addASTChild(currentAST, returnAST);
			qualified_join_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_natural)) {
			AST tmp720_AST = null;
			tmp720_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp720_AST);
			match(SQL2RW_natural);
			{
			if ((LA(1)==SQL2RW_inner)) {
				AST tmp721_AST = null;
				tmp721_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp721_AST);
				match(SQL2RW_inner);
			}
			else if ((LA(1)==SQL2RW_full||LA(1)==SQL2RW_left||LA(1)==SQL2RW_right)) {
				outer_join_type();
				astFactory.addASTChild(currentAST, returnAST);
				{
				if ((LA(1)==SQL2RW_outer)) {
					AST tmp722_AST = null;
					tmp722_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp722_AST);
					match(SQL2RW_outer);
				}
				else if ((LA(1)==SQL2RW_join)) {
				}
				else {
					throw new NoViableAltException(LT(1), getFilename());
				}

				}
			}
			else if ((LA(1)==SQL2RW_join)) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}

			}
			AST tmp723_AST = null;
			tmp723_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp723_AST);
			match(SQL2RW_join);
			table_ref();
			astFactory.addASTChild(currentAST, returnAST);
			qualified_join_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_union)) {
			AST tmp724_AST = null;
			tmp724_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp724_AST);
			match(SQL2RW_union);
			AST tmp725_AST = null;
			tmp725_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp725_AST);
			match(SQL2RW_join);
			table_ref();
			astFactory.addASTChild(currentAST, returnAST);
			qualified_join_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = qualified_join_AST;
	}

	public final void cross_join() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST cross_join_AST = null;

		AST tmp726_AST = null;
		tmp726_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp726_AST);
		match(SQL2RW_cross);
		AST tmp727_AST = null;
		tmp727_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp727_AST);
		match(SQL2RW_join);
		table_ref();
		astFactory.addASTChild(currentAST, returnAST);
		cross_join_AST = (AST)currentAST.root;
		returnAST = cross_join_AST;
	}

	public final void table_ref() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST table_ref_AST = null;

		table_ref_aux();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop567:
		do {
			if ((LA(1)==SQL2RW_full||LA(1)==SQL2RW_inner||LA(1)==SQL2RW_join||LA(1)==SQL2RW_left||LA(1)==SQL2RW_natural||LA(1)==SQL2RW_right||LA(1)==SQL2RW_union) && (_tokenSet_80.member(LA(2)))) {
				qualified_join();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else if ((LA(1)==SQL2RW_cross) && (LA(2)==SQL2RW_join)) {
				cross_join();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop567;
			}

		} while (true);
		}
		table_ref_AST = (AST)currentAST.root;
		returnAST = table_ref_AST;
	}

	public final void correlation_name() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST correlation_name_AST = null;

		id();
		astFactory.addASTChild(currentAST, returnAST);
		correlation_name_AST = (AST)currentAST.root;
		returnAST = correlation_name_AST;
	}

	public final void derived_column_list() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST derived_column_list_AST = null;

		column_name_list();
		astFactory.addASTChild(currentAST, returnAST);
		derived_column_list_AST = (AST)currentAST.root;
		returnAST = derived_column_list_AST;
	}

	public final void outer_join_type() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST outer_join_type_AST = null;

		if ((LA(1)==SQL2RW_left)) {
			AST tmp728_AST = null;
			tmp728_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp728_AST);
			match(SQL2RW_left);
			outer_join_type_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_right)) {
			AST tmp729_AST = null;
			tmp729_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp729_AST);
			match(SQL2RW_right);
			outer_join_type_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_full)) {
			AST tmp730_AST = null;
			tmp730_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp730_AST);
			match(SQL2RW_full);
			outer_join_type_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = outer_join_type_AST;
	}

	public final void join_spec() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST join_spec_AST = null;

		if ((LA(1)==SQL2RW_on)) {
			join_condition();
			astFactory.addASTChild(currentAST, returnAST);
			join_spec_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_using)) {
			named_columns_join();
			astFactory.addASTChild(currentAST, returnAST);
			join_spec_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = join_spec_AST;
	}

	public final void join_condition() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST join_condition_AST = null;

		AST tmp731_AST = null;
		tmp731_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp731_AST);
		match(SQL2RW_on);
		search_condition();
		astFactory.addASTChild(currentAST, returnAST);
		join_condition_AST = (AST)currentAST.root;
		returnAST = join_condition_AST;
	}

	public final void named_columns_join() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST named_columns_join_AST = null;

		AST tmp732_AST = null;
		tmp732_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp732_AST);
		match(SQL2RW_using);
		AST tmp733_AST = null;
		tmp733_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp733_AST);
		match(LEFT_PAREN);
		column_name_list();
		astFactory.addASTChild(currentAST, returnAST);
		AST tmp734_AST = null;
		tmp734_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp734_AST);
		match(RIGHT_PAREN);
		named_columns_join_AST = (AST)currentAST.root;
		returnAST = named_columns_join_AST;
	}

	public final void from_clause() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST from_clause_AST = null;

		AST tmp735_AST = null;
		tmp735_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp735_AST);
		match(SQL2RW_from);
		table_ref_list();
		astFactory.addASTChild(currentAST, returnAST);
		from_clause_AST = (AST)currentAST.root;
		returnAST = from_clause_AST;
	}

	public final void where_clause() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST where_clause_AST = null;

		AST tmp736_AST = null;
		tmp736_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp736_AST);
		match(SQL2RW_where);
		search_condition();
		astFactory.addASTChild(currentAST, returnAST);
		where_clause_AST = (AST)currentAST.root;
		returnAST = where_clause_AST;
	}

	public final void group_by_clause() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST group_by_clause_AST = null;

		AST tmp737_AST = null;
		tmp737_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp737_AST);
		match(SQL2RW_group);
		AST tmp738_AST = null;
		tmp738_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp738_AST);
		match(SQL2RW_by);
		grouping_column_ref_list();
		astFactory.addASTChild(currentAST, returnAST);
		group_by_clause_AST = (AST)currentAST.root;
		returnAST = group_by_clause_AST;
	}

	public final void having_clause() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST having_clause_AST = null;

		AST tmp739_AST = null;
		tmp739_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp739_AST);
		match(SQL2RW_having);
		search_condition();
		astFactory.addASTChild(currentAST, returnAST);
		having_clause_AST = (AST)currentAST.root;
		returnAST = having_clause_AST;
	}

	public final void table_ref_list() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST table_ref_list_AST = null;

		table_ref();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop592:
		do {
			if ((LA(1)==COMMA)) {
				AST tmp740_AST = null;
				tmp740_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp740_AST);
				match(COMMA);
				table_ref();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop592;
			}

		} while (true);
		}
		table_ref_list_AST = (AST)currentAST.root;
		returnAST = table_ref_list_AST;
	}

	public final void grouping_column_ref_list() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST grouping_column_ref_list_AST = null;

		grouping_column_ref();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop599:
		do {
			if ((LA(1)==COMMA)) {
				AST tmp741_AST = null;
				tmp741_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp741_AST);
				match(COMMA);
				grouping_column_ref();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop599;
			}

		} while (true);
		}
		grouping_column_ref_list_AST = (AST)currentAST.root;
		returnAST = grouping_column_ref_list_AST;
	}

	public final void grouping_column_ref() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST grouping_column_ref_AST = null;

		column_ref();
		astFactory.addASTChild(currentAST, returnAST);
		{
		if ((LA(1)==SQL2RW_collate)) {
			collate_clause();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else if ((_tokenSet_81.member(LA(1)))) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		grouping_column_ref_AST = (AST)currentAST.root;
		returnAST = grouping_column_ref_AST;
	}

	public final void unsigned_lit() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST unsigned_lit_AST = null;

		if ((LA(1)==UNSIGNED_INTEGER||LA(1)==APPROXIMATE_NUM_LIT||LA(1)==EXACT_NUM_LIT)) {
			unsigned_num_lit();
			astFactory.addASTChild(currentAST, returnAST);
			unsigned_lit_AST = (AST)currentAST.root;
		}
		else if ((_tokenSet_31.member(LA(1)))) {
			general_lit();
			astFactory.addASTChild(currentAST, returnAST);
			unsigned_lit_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = unsigned_lit_AST;
	}

	public final void parameter_spec() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST parameter_spec_AST = null;

		parameter_name();
		astFactory.addASTChild(currentAST, returnAST);
		{
		if ((LA(1)==SQL2RW_indicator||LA(1)==COLON)) {
			{
			if ((LA(1)==SQL2RW_indicator)) {
				AST tmp742_AST = null;
				tmp742_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp742_AST);
				match(SQL2RW_indicator);
			}
			else if ((LA(1)==COLON)) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}

			}
			parameter_name();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else if ((_tokenSet_82.member(LA(1)))) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		parameter_spec_AST = (AST)currentAST.root;
		returnAST = parameter_spec_AST;
	}

	public final void dyn_parameter_spec() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST dyn_parameter_spec_AST = null;

		AST tmp743_AST = null;
		tmp743_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp743_AST);
		match(QUESTION_MARK);
		dyn_parameter_spec_AST = (AST)currentAST.root;
		returnAST = dyn_parameter_spec_AST;
	}

	public final void variable_spec() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST variable_spec_AST = null;

		AST tmp744_AST = null;
		tmp744_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp744_AST);
		match(EMBDD_VARIABLE_NAME);
		{
		if ((LA(1)==SQL2RW_indicator||LA(1)==EMBDD_VARIABLE_NAME)) {
			{
			if ((LA(1)==SQL2RW_indicator)) {
				AST tmp745_AST = null;
				tmp745_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp745_AST);
				match(SQL2RW_indicator);
			}
			else if ((LA(1)==EMBDD_VARIABLE_NAME)) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}

			}
			AST tmp746_AST = null;
			tmp746_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp746_AST);
			match(EMBDD_VARIABLE_NAME);
		}
		else if ((_tokenSet_82.member(LA(1)))) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		variable_spec_AST = (AST)currentAST.root;
		returnAST = variable_spec_AST;
	}

	public final void num_value_exp() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST num_value_exp_AST = null;

		value_exp();
		astFactory.addASTChild(currentAST, returnAST);
		num_value_exp_AST = (AST)currentAST.root;
		returnAST = num_value_exp_AST;
	}

	public final void string_value_exp() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST string_value_exp_AST = null;

		char_value_exp();
		astFactory.addASTChild(currentAST, returnAST);
		string_value_exp_AST = (AST)currentAST.root;
		returnAST = string_value_exp_AST;
	}

	public final void datetime_value_exp() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST datetime_value_exp_AST = null;

		value_exp();
		astFactory.addASTChild(currentAST, returnAST);
		datetime_value_exp_AST = (AST)currentAST.root;
		returnAST = datetime_value_exp_AST;
	}

	public final void term() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST term_AST = null;

		factor();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop634:
		do {
			if ((LA(1)==ASTERISK||LA(1)==SOLIDUS) && (_tokenSet_14.member(LA(2)))) {
				factor_op();
				astFactory.addASTChild(currentAST, returnAST);
				factor();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop634;
			}

		} while (true);
		}
		term_AST = (AST)currentAST.root;
		returnAST = term_AST;
	}

	public final void term_op() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST term_op_AST = null;

		if ((LA(1)==PLUS_SIGN)) {
			AST tmp747_AST = null;
			tmp747_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp747_AST);
			match(PLUS_SIGN);
			term_op_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==MINUS_SIGN)) {
			AST tmp748_AST = null;
			tmp748_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp748_AST);
			match(MINUS_SIGN);
			term_op_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = term_op_AST;
	}

	public final void string_value_fct() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST string_value_fct_AST = null;

		char_value_fct();
		astFactory.addASTChild(currentAST, returnAST);
		string_value_fct_AST = (AST)currentAST.root;
		returnAST = string_value_fct_AST;
	}

	public final void char_value_fct() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST char_value_fct_AST = null;

		switch ( LA(1)) {
		case SQL2RW_substring:
		{
			char_substring_fct();
			astFactory.addASTChild(currentAST, returnAST);
			char_value_fct_AST = (AST)currentAST.root;
			break;
		}
		case SQL2RW_lower:
		case SQL2RW_upper:
		{
			fold();
			astFactory.addASTChild(currentAST, returnAST);
			char_value_fct_AST = (AST)currentAST.root;
			break;
		}
		case SQL2RW_convert:
		{
			form_conversion();
			astFactory.addASTChild(currentAST, returnAST);
			char_value_fct_AST = (AST)currentAST.root;
			break;
		}
		case SQL2RW_translate:
		{
			char_translation();
			astFactory.addASTChild(currentAST, returnAST);
			char_value_fct_AST = (AST)currentAST.root;
			break;
		}
		case SQL2RW_trim:
		{
			trim_fct();
			astFactory.addASTChild(currentAST, returnAST);
			char_value_fct_AST = (AST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = char_value_fct_AST;
	}

	public final void char_substring_fct() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST char_substring_fct_AST = null;

		AST tmp749_AST = null;
		tmp749_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp749_AST);
		match(SQL2RW_substring);
		AST tmp750_AST = null;
		tmp750_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp750_AST);
		match(LEFT_PAREN);
		char_value_exp();
		astFactory.addASTChild(currentAST, returnAST);
		AST tmp751_AST = null;
		tmp751_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp751_AST);
		match(SQL2RW_from);
		num_value_exp();
		astFactory.addASTChild(currentAST, returnAST);
		{
		if ((LA(1)==SQL2RW_for)) {
			AST tmp752_AST = null;
			tmp752_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp752_AST);
			match(SQL2RW_for);
			num_value_exp();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else if ((LA(1)==RIGHT_PAREN)) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		AST tmp753_AST = null;
		tmp753_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp753_AST);
		match(RIGHT_PAREN);
		char_substring_fct_AST = (AST)currentAST.root;
		returnAST = char_substring_fct_AST;
	}

	public final void fold() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST fold_AST = null;

		{
		if ((LA(1)==SQL2RW_upper)) {
			AST tmp754_AST = null;
			tmp754_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp754_AST);
			match(SQL2RW_upper);
		}
		else if ((LA(1)==SQL2RW_lower)) {
			AST tmp755_AST = null;
			tmp755_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp755_AST);
			match(SQL2RW_lower);
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		AST tmp756_AST = null;
		tmp756_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp756_AST);
		match(LEFT_PAREN);
		char_value_exp();
		astFactory.addASTChild(currentAST, returnAST);
		AST tmp757_AST = null;
		tmp757_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp757_AST);
		match(RIGHT_PAREN);
		fold_AST = (AST)currentAST.root;
		returnAST = fold_AST;
	}

	public final void form_conversion() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST form_conversion_AST = null;

		AST tmp758_AST = null;
		tmp758_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp758_AST);
		match(SQL2RW_convert);
		AST tmp759_AST = null;
		tmp759_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp759_AST);
		match(LEFT_PAREN);
		char_value_exp();
		astFactory.addASTChild(currentAST, returnAST);
		AST tmp760_AST = null;
		tmp760_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp760_AST);
		match(SQL2RW_using);
		form_conversion_name();
		astFactory.addASTChild(currentAST, returnAST);
		AST tmp761_AST = null;
		tmp761_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp761_AST);
		match(RIGHT_PAREN);
		form_conversion_AST = (AST)currentAST.root;
		returnAST = form_conversion_AST;
	}

	public final void char_translation() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST char_translation_AST = null;

		AST tmp762_AST = null;
		tmp762_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp762_AST);
		match(SQL2RW_translate);
		AST tmp763_AST = null;
		tmp763_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp763_AST);
		match(LEFT_PAREN);
		char_value_exp();
		astFactory.addASTChild(currentAST, returnAST);
		AST tmp764_AST = null;
		tmp764_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp764_AST);
		match(SQL2RW_using);
		translation_name();
		astFactory.addASTChild(currentAST, returnAST);
		AST tmp765_AST = null;
		tmp765_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp765_AST);
		match(RIGHT_PAREN);
		char_translation_AST = (AST)currentAST.root;
		returnAST = char_translation_AST;
	}

	public final void trim_fct() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST trim_fct_AST = null;

		AST tmp766_AST = null;
		tmp766_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp766_AST);
		match(SQL2RW_trim);
		AST tmp767_AST = null;
		tmp767_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp767_AST);
		match(LEFT_PAREN);
		trim_operands();
		astFactory.addASTChild(currentAST, returnAST);
		AST tmp768_AST = null;
		tmp768_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp768_AST);
		match(RIGHT_PAREN);
		trim_fct_AST = (AST)currentAST.root;
		returnAST = trim_fct_AST;
	}

	public final void form_conversion_name() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST form_conversion_name_AST = null;

		qualified_name();
		astFactory.addASTChild(currentAST, returnAST);
		form_conversion_name_AST = (AST)currentAST.root;
		returnAST = form_conversion_name_AST;
	}

	public final void trim_operands() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST trim_operands_AST = null;

		if ((LA(1)==SQL2RW_both||LA(1)==SQL2RW_leading||LA(1)==SQL2RW_trailing) && (LA(2)==SQL2RW_from)) {
			trim_spec();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp769_AST = null;
			tmp769_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp769_AST);
			match(SQL2RW_from);
			char_value_exp();
			astFactory.addASTChild(currentAST, returnAST);
			trim_operands_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_both||LA(1)==SQL2RW_leading||LA(1)==SQL2RW_trailing) && (_tokenSet_14.member(LA(2)))) {
			trim_spec();
			astFactory.addASTChild(currentAST, returnAST);
			char_value_exp();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp770_AST = null;
			tmp770_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp770_AST);
			match(SQL2RW_from);
			char_value_exp();
			astFactory.addASTChild(currentAST, returnAST);
			trim_operands_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_from)) {
			AST tmp771_AST = null;
			tmp771_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp771_AST);
			match(SQL2RW_from);
			char_value_exp();
			astFactory.addASTChild(currentAST, returnAST);
			trim_operands_AST = (AST)currentAST.root;
		}
		else if ((_tokenSet_14.member(LA(1)))) {
			char_value_exp();
			astFactory.addASTChild(currentAST, returnAST);
			{
			if ((LA(1)==SQL2RW_from)) {
				AST tmp772_AST = null;
				tmp772_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp772_AST);
				match(SQL2RW_from);
				char_value_exp();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else if ((LA(1)==RIGHT_PAREN)) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}

			}
			trim_operands_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = trim_operands_AST;
	}

	public final void trim_spec() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST trim_spec_AST = null;

		if ((LA(1)==SQL2RW_leading)) {
			AST tmp773_AST = null;
			tmp773_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp773_AST);
			match(SQL2RW_leading);
			trim_spec_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_trailing)) {
			AST tmp774_AST = null;
			tmp774_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp774_AST);
			match(SQL2RW_trailing);
			trim_spec_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_both)) {
			AST tmp775_AST = null;
			tmp775_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp775_AST);
			match(SQL2RW_both);
			trim_spec_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = trim_spec_AST;
	}

	public final void factor() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST factor_AST = null;

		{
		if ((LA(1)==MINUS_SIGN||LA(1)==PLUS_SIGN)) {
			sign();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else if ((_tokenSet_83.member(LA(1)))) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		gen_primary();
		astFactory.addASTChild(currentAST, returnAST);
		{
		if ((LA(1)==SQL2RW_at)) {
			AST tmp776_AST = null;
			tmp776_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp776_AST);
			match(SQL2RW_at);
			time_zone_specifier();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else if ((LA(1)==SQL2RW_collate)) {
			collate_clause();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else if ((_tokenSet_84.member(LA(1)))) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		factor_AST = (AST)currentAST.root;
		returnAST = factor_AST;
	}

	public final void factor_op() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST factor_op_AST = null;

		if ((LA(1)==ASTERISK)) {
			AST tmp777_AST = null;
			tmp777_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp777_AST);
			match(ASTERISK);
			factor_op_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SOLIDUS)) {
			AST tmp778_AST = null;
			tmp778_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp778_AST);
			match(SOLIDUS);
			factor_op_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = factor_op_AST;
	}

	public final void sign() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST sign_AST = null;

		if ((LA(1)==PLUS_SIGN)) {
			AST tmp779_AST = null;
			tmp779_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp779_AST);
			match(PLUS_SIGN);
			sign_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==MINUS_SIGN)) {
			AST tmp780_AST = null;
			tmp780_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp780_AST);
			match(MINUS_SIGN);
			sign_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = sign_AST;
	}

	public final void gen_primary() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST gen_primary_AST = null;

		switch ( LA(1)) {
		case SQL2NRW_ada:
		case SQL2NRW_c:
		case SQL2NRW_catalog_name:
		case SQL2NRW_character_set_catalog:
		case SQL2NRW_character_set_name:
		case SQL2NRW_character_set_schema:
		case SQL2NRW_class_origin:
		case SQL2NRW_cobol:
		case SQL2NRW_collation_catalog:
		case SQL2NRW_collation_name:
		case SQL2NRW_collation_schema:
		case SQL2NRW_column_name:
		case SQL2NRW_command_function:
		case SQL2NRW_committed:
		case SQL2NRW_condition_number:
		case SQL2NRW_connection_name:
		case SQL2NRW_constraint_catalog:
		case SQL2NRW_constraint_name:
		case SQL2NRW_constraint_schema:
		case SQL2NRW_cursor_name:
		case SQL2NRW_data:
		case SQL2NRW_datetime_interval_code:
		case SQL2NRW_datetime_interval_precision:
		case SQL2NRW_dynamic_function:
		case SQL2NRW_fortran:
		case SQL2NRW_length:
		case SQL2NRW_message_length:
		case SQL2NRW_message_octet_length:
		case SQL2NRW_message_text:
		case SQL2NRW_more:
		case SQL2NRW_mumps:
		case SQL2NRW_name:
		case SQL2NRW_nullable:
		case SQL2NRW_number:
		case SQL2NRW_pascal:
		case SQL2NRW_pli:
		case SQL2NRW_repeatable:
		case SQL2NRW_returned_length:
		case SQL2NRW_returned_octet_length:
		case SQL2NRW_returned_sqlstate:
		case SQL2NRW_row_count:
		case SQL2NRW_scale:
		case SQL2NRW_schema_name:
		case SQL2NRW_serializable:
		case SQL2NRW_server_name:
		case SQL2NRW_subclass_origin:
		case SQL2NRW_table_name:
		case SQL2NRW_type:
		case SQL2NRW_uncommitted:
		case SQL2NRW_unnamed:
		case SQL2RW_avg:
		case SQL2RW_case:
		case SQL2RW_cast:
		case SQL2RW_coalesce:
		case SQL2RW_count:
		case SQL2RW_current_user:
		case SQL2RW_date:
		case SQL2RW_interval:
		case SQL2RW_max:
		case SQL2RW_min:
		case SQL2RW_nullif:
		case SQL2RW_session_user:
		case SQL2RW_sum:
		case SQL2RW_system_user:
		case SQL2RW_time:
		case SQL2RW_timestamp:
		case SQL2RW_user:
		case SQL2RW_value:
		case UNSIGNED_INTEGER:
		case APPROXIMATE_NUM_LIT:
		case NATIONAL_CHAR_STRING_LIT:
		case BIT_STRING_LIT:
		case HEX_STRING_LIT:
		case EMBDD_VARIABLE_NAME:
		case REGULAR_ID:
		case EXACT_NUM_LIT:
		case CHAR_STRING:
		case DELIMITED_ID:
		case LEFT_PAREN:
		case COLON:
		case QUESTION_MARK:
		case INTRODUCER:
		{
			value_exp_primary();
			astFactory.addASTChild(currentAST, returnAST);
			{
			if ((LA(1)==SQL2RW_day||LA(1)==SQL2RW_hour||LA(1)==SQL2RW_minute||LA(1)==SQL2RW_month||LA(1)==SQL2RW_second||LA(1)==SQL2RW_year)) {
				interval_qualifier();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else if ((_tokenSet_85.member(LA(1)))) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}

			}
			gen_primary_AST = (AST)currentAST.root;
			break;
		}
		case SQL2RW_bit_length:
		case SQL2RW_char_length:
		case SQL2RW_character_length:
		case SQL2RW_extract:
		case SQL2RW_octet_length:
		case SQL2RW_position:
		{
			num_value_fct();
			astFactory.addASTChild(currentAST, returnAST);
			gen_primary_AST = (AST)currentAST.root;
			break;
		}
		case SQL2RW_convert:
		case SQL2RW_lower:
		case SQL2RW_substring:
		case SQL2RW_translate:
		case SQL2RW_trim:
		case SQL2RW_upper:
		{
			string_value_fct();
			astFactory.addASTChild(currentAST, returnAST);
			gen_primary_AST = (AST)currentAST.root;
			break;
		}
		case SQL2RW_current_date:
		case SQL2RW_current_time:
		case SQL2RW_current_timestamp:
		{
			datetime_value_fct();
			astFactory.addASTChild(currentAST, returnAST);
			gen_primary_AST = (AST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = gen_primary_AST;
	}

	public final void time_zone_specifier() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST time_zone_specifier_AST = null;

		if ((LA(1)==SQL2RW_local)) {
			AST tmp781_AST = null;
			tmp781_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp781_AST);
			match(SQL2RW_local);
			time_zone_specifier_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_time)) {
			AST tmp782_AST = null;
			tmp782_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp782_AST);
			match(SQL2RW_time);
			AST tmp783_AST = null;
			tmp783_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp783_AST);
			match(SQL2RW_zone);
			interval_value_exp();
			astFactory.addASTChild(currentAST, returnAST);
			time_zone_specifier_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = time_zone_specifier_AST;
	}

	public final void num_value_fct() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST num_value_fct_AST = null;

		if ((LA(1)==SQL2RW_position)) {
			position_exp();
			astFactory.addASTChild(currentAST, returnAST);
			num_value_fct_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_extract)) {
			extract_exp();
			astFactory.addASTChild(currentAST, returnAST);
			num_value_fct_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_bit_length||LA(1)==SQL2RW_char_length||LA(1)==SQL2RW_character_length||LA(1)==SQL2RW_octet_length)) {
			length_exp();
			astFactory.addASTChild(currentAST, returnAST);
			num_value_fct_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = num_value_fct_AST;
	}

	public final void position_exp() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST position_exp_AST = null;

		AST tmp784_AST = null;
		tmp784_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp784_AST);
		match(SQL2RW_position);
		AST tmp785_AST = null;
		tmp785_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp785_AST);
		match(LEFT_PAREN);
		char_value_exp();
		astFactory.addASTChild(currentAST, returnAST);
		AST tmp786_AST = null;
		tmp786_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp786_AST);
		match(SQL2RW_in);
		char_value_exp();
		astFactory.addASTChild(currentAST, returnAST);
		AST tmp787_AST = null;
		tmp787_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp787_AST);
		match(RIGHT_PAREN);
		position_exp_AST = (AST)currentAST.root;
		returnAST = position_exp_AST;
	}

	public final void extract_exp() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST extract_exp_AST = null;

		AST tmp788_AST = null;
		tmp788_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp788_AST);
		match(SQL2RW_extract);
		AST tmp789_AST = null;
		tmp789_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp789_AST);
		match(LEFT_PAREN);
		extract_field();
		astFactory.addASTChild(currentAST, returnAST);
		AST tmp790_AST = null;
		tmp790_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp790_AST);
		match(SQL2RW_from);
		extract_source();
		astFactory.addASTChild(currentAST, returnAST);
		AST tmp791_AST = null;
		tmp791_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp791_AST);
		match(RIGHT_PAREN);
		extract_exp_AST = (AST)currentAST.root;
		returnAST = extract_exp_AST;
	}

	public final void length_exp() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST length_exp_AST = null;

		if ((LA(1)==SQL2RW_char_length||LA(1)==SQL2RW_character_length)) {
			char_length_exp();
			astFactory.addASTChild(currentAST, returnAST);
			length_exp_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_octet_length)) {
			octet_length_exp();
			astFactory.addASTChild(currentAST, returnAST);
			length_exp_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_bit_length)) {
			bit_length_exp();
			astFactory.addASTChild(currentAST, returnAST);
			length_exp_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = length_exp_AST;
	}

	public final void extract_field() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST extract_field_AST = null;

		if ((LA(1)==SQL2RW_day||LA(1)==SQL2RW_hour||LA(1)==SQL2RW_minute||LA(1)==SQL2RW_month||LA(1)==SQL2RW_second||LA(1)==SQL2RW_year)) {
			datetime_field();
			astFactory.addASTChild(currentAST, returnAST);
			extract_field_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_timezone_hour||LA(1)==SQL2RW_timezone_minute)) {
			time_zone_field();
			astFactory.addASTChild(currentAST, returnAST);
			extract_field_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = extract_field_AST;
	}

	public final void extract_source() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST extract_source_AST = null;

		datetime_value_exp();
		astFactory.addASTChild(currentAST, returnAST);
		extract_source_AST = (AST)currentAST.root;
		returnAST = extract_source_AST;
	}

	public final void datetime_field() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST datetime_field_AST = null;

		if ((LA(1)==SQL2RW_day||LA(1)==SQL2RW_hour||LA(1)==SQL2RW_minute||LA(1)==SQL2RW_month||LA(1)==SQL2RW_year)) {
			non_second_datetime_field();
			astFactory.addASTChild(currentAST, returnAST);
			datetime_field_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_second)) {
			AST tmp792_AST = null;
			tmp792_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp792_AST);
			match(SQL2RW_second);
			datetime_field_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = datetime_field_AST;
	}

	public final void time_zone_field() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST time_zone_field_AST = null;

		if ((LA(1)==SQL2RW_timezone_hour)) {
			AST tmp793_AST = null;
			tmp793_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp793_AST);
			match(SQL2RW_timezone_hour);
			time_zone_field_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_timezone_minute)) {
			AST tmp794_AST = null;
			tmp794_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp794_AST);
			match(SQL2RW_timezone_minute);
			time_zone_field_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = time_zone_field_AST;
	}

	public final void non_second_datetime_field() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST non_second_datetime_field_AST = null;

		switch ( LA(1)) {
		case SQL2RW_year:
		{
			AST tmp795_AST = null;
			tmp795_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp795_AST);
			match(SQL2RW_year);
			non_second_datetime_field_AST = (AST)currentAST.root;
			break;
		}
		case SQL2RW_month:
		{
			AST tmp796_AST = null;
			tmp796_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp796_AST);
			match(SQL2RW_month);
			non_second_datetime_field_AST = (AST)currentAST.root;
			break;
		}
		case SQL2RW_day:
		{
			AST tmp797_AST = null;
			tmp797_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp797_AST);
			match(SQL2RW_day);
			non_second_datetime_field_AST = (AST)currentAST.root;
			break;
		}
		case SQL2RW_hour:
		{
			AST tmp798_AST = null;
			tmp798_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp798_AST);
			match(SQL2RW_hour);
			non_second_datetime_field_AST = (AST)currentAST.root;
			break;
		}
		case SQL2RW_minute:
		{
			AST tmp799_AST = null;
			tmp799_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp799_AST);
			match(SQL2RW_minute);
			non_second_datetime_field_AST = (AST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = non_second_datetime_field_AST;
	}

	public final void char_length_exp() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST char_length_exp_AST = null;

		{
		if ((LA(1)==SQL2RW_char_length)) {
			AST tmp800_AST = null;
			tmp800_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp800_AST);
			match(SQL2RW_char_length);
		}
		else if ((LA(1)==SQL2RW_character_length)) {
			AST tmp801_AST = null;
			tmp801_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp801_AST);
			match(SQL2RW_character_length);
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		AST tmp802_AST = null;
		tmp802_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp802_AST);
		match(LEFT_PAREN);
		string_value_exp();
		astFactory.addASTChild(currentAST, returnAST);
		AST tmp803_AST = null;
		tmp803_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp803_AST);
		match(RIGHT_PAREN);
		char_length_exp_AST = (AST)currentAST.root;
		returnAST = char_length_exp_AST;
	}

	public final void octet_length_exp() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST octet_length_exp_AST = null;

		AST tmp804_AST = null;
		tmp804_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp804_AST);
		match(SQL2RW_octet_length);
		AST tmp805_AST = null;
		tmp805_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp805_AST);
		match(LEFT_PAREN);
		string_value_exp();
		astFactory.addASTChild(currentAST, returnAST);
		AST tmp806_AST = null;
		tmp806_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp806_AST);
		match(RIGHT_PAREN);
		octet_length_exp_AST = (AST)currentAST.root;
		returnAST = octet_length_exp_AST;
	}

	public final void bit_length_exp() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST bit_length_exp_AST = null;

		AST tmp807_AST = null;
		tmp807_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp807_AST);
		match(SQL2RW_bit_length);
		AST tmp808_AST = null;
		tmp808_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp808_AST);
		match(LEFT_PAREN);
		string_value_exp();
		astFactory.addASTChild(currentAST, returnAST);
		AST tmp809_AST = null;
		tmp809_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp809_AST);
		match(RIGHT_PAREN);
		bit_length_exp_AST = (AST)currentAST.root;
		returnAST = bit_length_exp_AST;
	}

	public final void current_date_value_fct() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST current_date_value_fct_AST = null;

		AST tmp810_AST = null;
		tmp810_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp810_AST);
		match(SQL2RW_current_date);
		current_date_value_fct_AST = (AST)currentAST.root;
		returnAST = current_date_value_fct_AST;
	}

	public final void current_time_value_fct() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST current_time_value_fct_AST = null;

		AST tmp811_AST = null;
		tmp811_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp811_AST);
		match(SQL2RW_current_time);
		{
		if ((LA(1)==LEFT_PAREN)) {
			AST tmp812_AST = null;
			tmp812_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp812_AST);
			match(LEFT_PAREN);
			time_precision();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp813_AST = null;
			tmp813_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp813_AST);
			match(RIGHT_PAREN);
		}
		else if ((_tokenSet_86.member(LA(1)))) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		current_time_value_fct_AST = (AST)currentAST.root;
		returnAST = current_time_value_fct_AST;
	}

	public final void currenttimestamp_value_fct() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST currenttimestamp_value_fct_AST = null;

		AST tmp814_AST = null;
		tmp814_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp814_AST);
		match(SQL2RW_current_timestamp);
		{
		if ((LA(1)==LEFT_PAREN)) {
			AST tmp815_AST = null;
			tmp815_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp815_AST);
			match(LEFT_PAREN);
			timestamp_precision();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp816_AST = null;
			tmp816_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp816_AST);
			match(RIGHT_PAREN);
		}
		else if ((_tokenSet_86.member(LA(1)))) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		currenttimestamp_value_fct_AST = (AST)currentAST.root;
		returnAST = currenttimestamp_value_fct_AST;
	}

	public final void qualified_local_table_name() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST qualified_local_table_name_AST = null;

		AST tmp817_AST = null;
		tmp817_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp817_AST);
		match(SQL2RW_module);
		AST tmp818_AST = null;
		tmp818_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp818_AST);
		match(PERIOD);
		id();
		astFactory.addASTChild(currentAST, returnAST);
		qualified_local_table_name_AST = (AST)currentAST.root;
		returnAST = qualified_local_table_name_AST;
	}

	public final void unsigned_num_lit() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST unsigned_num_lit_AST = null;

		if ((LA(1)==UNSIGNED_INTEGER)) {
			AST tmp819_AST = null;
			tmp819_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp819_AST);
			match(UNSIGNED_INTEGER);
			unsigned_num_lit_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==EXACT_NUM_LIT)) {
			AST tmp820_AST = null;
			tmp820_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp820_AST);
			match(EXACT_NUM_LIT);
			unsigned_num_lit_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==APPROXIMATE_NUM_LIT)) {
			AST tmp821_AST = null;
			tmp821_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp821_AST);
			match(APPROXIMATE_NUM_LIT);
			unsigned_num_lit_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = unsigned_num_lit_AST;
	}

	public final void char_string_lit() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST char_string_lit_AST = null;

		{
		if ((LA(1)==INTRODUCER)) {
			AST tmp822_AST = null;
			tmp822_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp822_AST);
			match(INTRODUCER);
			char_set_name();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else if ((LA(1)==CHAR_STRING)) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		AST tmp823_AST = null;
		tmp823_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp823_AST);
		match(CHAR_STRING);
		char_string_lit_AST = (AST)currentAST.root;
		returnAST = char_string_lit_AST;
	}

	public final void general_lit() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST general_lit_AST = null;

		switch ( LA(1)) {
		case CHAR_STRING:
		case INTRODUCER:
		{
			char_string_lit();
			astFactory.addASTChild(currentAST, returnAST);
			general_lit_AST = (AST)currentAST.root;
			break;
		}
		case NATIONAL_CHAR_STRING_LIT:
		{
			AST tmp824_AST = null;
			tmp824_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp824_AST);
			match(NATIONAL_CHAR_STRING_LIT);
			general_lit_AST = (AST)currentAST.root;
			break;
		}
		case BIT_STRING_LIT:
		{
			AST tmp825_AST = null;
			tmp825_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp825_AST);
			match(BIT_STRING_LIT);
			general_lit_AST = (AST)currentAST.root;
			break;
		}
		case HEX_STRING_LIT:
		{
			AST tmp826_AST = null;
			tmp826_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp826_AST);
			match(HEX_STRING_LIT);
			general_lit_AST = (AST)currentAST.root;
			break;
		}
		case SQL2RW_date:
		case SQL2RW_time:
		case SQL2RW_timestamp:
		{
			datetime_lit();
			astFactory.addASTChild(currentAST, returnAST);
			general_lit_AST = (AST)currentAST.root;
			break;
		}
		case SQL2RW_interval:
		{
			interval_lit();
			astFactory.addASTChild(currentAST, returnAST);
			general_lit_AST = (AST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = general_lit_AST;
	}

	public final void datetime_lit() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST datetime_lit_AST = null;

		if ((LA(1)==SQL2RW_date)) {
			date_lit();
			astFactory.addASTChild(currentAST, returnAST);
			datetime_lit_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_time)) {
			time_lit();
			astFactory.addASTChild(currentAST, returnAST);
			datetime_lit_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_timestamp)) {
			timestamp_lit();
			astFactory.addASTChild(currentAST, returnAST);
			datetime_lit_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = datetime_lit_AST;
	}

	public final void interval_lit() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST interval_lit_AST = null;

		AST tmp827_AST = null;
		tmp827_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp827_AST);
		match(SQL2RW_interval);
		{
		if ((LA(1)==MINUS_SIGN||LA(1)==PLUS_SIGN)) {
			sign();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else if ((LA(1)==CHAR_STRING)) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		AST tmp828_AST = null;
		tmp828_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp828_AST);
		match(CHAR_STRING);
		interval_qualifier();
		astFactory.addASTChild(currentAST, returnAST);
		interval_lit_AST = (AST)currentAST.root;
		returnAST = interval_lit_AST;
	}

	public final void date_lit() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST date_lit_AST = null;

		AST tmp829_AST = null;
		tmp829_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp829_AST);
		match(SQL2RW_date);
		AST tmp830_AST = null;
		tmp830_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp830_AST);
		match(CHAR_STRING);
		date_lit_AST = (AST)currentAST.root;
		returnAST = date_lit_AST;
	}

	public final void time_lit() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST time_lit_AST = null;

		AST tmp831_AST = null;
		tmp831_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp831_AST);
		match(SQL2RW_time);
		AST tmp832_AST = null;
		tmp832_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp832_AST);
		match(CHAR_STRING);
		time_lit_AST = (AST)currentAST.root;
		returnAST = time_lit_AST;
	}

	public final void timestamp_lit() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST timestamp_lit_AST = null;

		AST tmp833_AST = null;
		tmp833_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp833_AST);
		match(SQL2RW_timestamp);
		AST tmp834_AST = null;
		tmp834_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp834_AST);
		match(CHAR_STRING);
		timestamp_lit_AST = (AST)currentAST.root;
		returnAST = timestamp_lit_AST;
	}

	public final void start_field() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST start_field_AST = null;

		non_second_datetime_field();
		astFactory.addASTChild(currentAST, returnAST);
		{
		if ((LA(1)==LEFT_PAREN)) {
			AST tmp835_AST = null;
			tmp835_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp835_AST);
			match(LEFT_PAREN);
			AST tmp836_AST = null;
			tmp836_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp836_AST);
			match(UNSIGNED_INTEGER);
			AST tmp837_AST = null;
			tmp837_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp837_AST);
			match(RIGHT_PAREN);
		}
		else if ((_tokenSet_87.member(LA(1)))) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		start_field_AST = (AST)currentAST.root;
		returnAST = start_field_AST;
	}

	public final void end_field() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST end_field_AST = null;

		if ((LA(1)==SQL2RW_day||LA(1)==SQL2RW_hour||LA(1)==SQL2RW_minute||LA(1)==SQL2RW_month||LA(1)==SQL2RW_year)) {
			non_second_datetime_field();
			astFactory.addASTChild(currentAST, returnAST);
			end_field_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==SQL2RW_second)) {
			AST tmp838_AST = null;
			tmp838_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp838_AST);
			match(SQL2RW_second);
			{
			if ((LA(1)==LEFT_PAREN)) {
				AST tmp839_AST = null;
				tmp839_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp839_AST);
				match(LEFT_PAREN);
				AST tmp840_AST = null;
				tmp840_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp840_AST);
				match(UNSIGNED_INTEGER);
				AST tmp841_AST = null;
				tmp841_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp841_AST);
				match(RIGHT_PAREN);
			}
			else if ((_tokenSet_73.member(LA(1)))) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}

			}
			end_field_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = end_field_AST;
	}

	public final void signed_num_lit() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST signed_num_lit_AST = null;

		{
		if ((LA(1)==MINUS_SIGN||LA(1)==PLUS_SIGN)) {
			sign();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else if ((LA(1)==UNSIGNED_INTEGER||LA(1)==APPROXIMATE_NUM_LIT||LA(1)==EXACT_NUM_LIT)) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		}
		unsigned_num_lit();
		astFactory.addASTChild(currentAST, returnAST);
		signed_num_lit_AST = (AST)currentAST.root;
		returnAST = signed_num_lit_AST;
	}


	public static final String[] _tokenNames = {
		"<0>",
		"EOF",
		"<2>",
		"NULL_TREE_LOOKAHEAD",
		"<4>",
		"\"ada\"",
		"\"c\"",
		"\"catalog_name\"",
		"\"character_set_catalog\"",
		"\"character_set_name\"",
		"\"character_set_schema\"",
		"\"class_origin\"",
		"\"cobol\"",
		"\"collation_catalog\"",
		"\"collation_name\"",
		"\"collation_schema\"",
		"\"column_name\"",
		"\"command_function\"",
		"\"committed\"",
		"\"condition_number\"",
		"\"connection_name\"",
		"\"constraint_catalog\"",
		"\"constraint_name\"",
		"\"constraint_schema\"",
		"\"cursor_name\"",
		"\"data\"",
		"\"datetime_interval_code\"",
		"\"datetime_interval_precision\"",
		"\"dynamic_function\"",
		"\"fortran\"",
		"\"length\"",
		"\"message_length\"",
		"\"message_octet_length\"",
		"\"message_text\"",
		"\"more\"",
		"\"mumps\"",
		"\"name\"",
		"\"nullable\"",
		"\"number\"",
		"\"pascal\"",
		"\"pli\"",
		"\"repeatable\"",
		"\"returned_length\"",
		"\"returned_octet_length\"",
		"\"returned_sqlstate\"",
		"\"row_count\"",
		"\"scale\"",
		"\"schema_name\"",
		"\"serializable\"",
		"\"server_name\"",
		"\"subclass_origin\"",
		"\"table_name\"",
		"\"type\"",
		"\"uncommitted\"",
		"\"unnamed\"",
		"\"absolute\"",
		"\"action\"",
		"\"add\"",
		"\"all\"",
		"\"allocate\"",
		"\"alter\"",
		"\"and\"",
		"\"any\"",
		"\"are\"",
		"\"as\"",
		"\"asc\"",
		"\"assertion\"",
		"\"at\"",
		"\"authorization\"",
		"\"avg\"",
		"\"begin\"",
		"\"between\"",
		"\"bit\"",
		"\"bit_length\"",
		"\"both\"",
		"\"by\"",
		"\"cascade\"",
		"\"cascaded\"",
		"\"case\"",
		"\"cast\"",
		"\"catalog\"",
		"\"char\"",
		"\"character\"",
		"\"char_length\"",
		"\"character_length\"",
		"\"check\"",
		"\"close\"",
		"\"coalesce\"",
		"\"collate\"",
		"\"collation\"",
		"\"column\"",
		"\"commit\"",
		"\"connect\"",
		"\"connection\"",
		"\"constraint\"",
		"\"constraints\"",
		"\"continue\"",
		"\"convert\"",
		"\"corresponding\"",
		"\"count\"",
		"\"create\"",
		"\"cross\"",
		"\"current\"",
		"\"current_date\"",
		"\"current_time\"",
		"\"current_timestamp\"",
		"\"current_user\"",
		"\"cursor\"",
		"\"date\"",
		"\"day\"",
		"\"deallocate\"",
		"\"dec\"",
		"\"decimal\"",
		"\"declare\"",
		"\"default\"",
		"\"deferrable\"",
		"\"deferred\"",
		"\"delete\"",
		"\"desc\"",
		"\"describe\"",
		"\"descriptor\"",
		"\"diagnostics\"",
		"\"disconnect\"",
		"\"distinct\"",
		"\"domain\"",
		"\"double\"",
		"\"drop\"",
		"\"else\"",
		"\"end\"",
		"\"end-exec\"",
		"\"escape\"",
		"\"except\"",
		"\"exception\"",
		"\"exec\"",
		"\"execute\"",
		"\"exists\"",
		"\"external\"",
		"\"extract\"",
		"\"false\"",
		"\"fetch\"",
		"\"first\"",
		"\"float\"",
		"\"for\"",
		"\"foreign\"",
		"\"found\"",
		"\"from\"",
		"\"full\"",
		"\"get\"",
		"\"global\"",
		"\"go\"",
		"\"goto\"",
		"\"grant\"",
		"\"group\"",
		"\"having\"",
		"\"hour\"",
		"\"identity\"",
		"\"immediate\"",
		"\"in\"",
		"\"indicator\"",
		"\"initially\"",
		"\"inner\"",
		"\"input\"",
		"\"insensitive\"",
		"\"insert\"",
		"\"int\"",
		"\"integer\"",
		"\"intersect\"",
		"\"interval\"",
		"\"into\"",
		"\"is\"",
		"\"isolation\"",
		"\"join\"",
		"\"key\"",
		"\"language\"",
		"\"last\"",
		"\"leading\"",
		"\"left\"",
		"\"level\"",
		"\"like\"",
		"\"local\"",
		"\"lower\"",
		"\"match\"",
		"\"max\"",
		"\"min\"",
		"\"minute\"",
		"\"module\"",
		"\"month\"",
		"\"names\"",
		"\"national\"",
		"\"natural\"",
		"\"nchar\"",
		"\"next\"",
		"\"no\"",
		"\"not\"",
		"\"null\"",
		"\"nullif\"",
		"\"numeric\"",
		"\"octet_length\"",
		"\"of\"",
		"\"on\"",
		"\"only\"",
		"\"open\"",
		"\"option\"",
		"\"or\"",
		"\"order\"",
		"\"outer\"",
		"\"output\"",
		"\"overlaps\"",
		"\"pad\"",
		"\"partial\"",
		"\"position\"",
		"\"precision\"",
		"\"prepare\"",
		"\"preserve\"",
		"\"primary\"",
		"\"prior\"",
		"\"privileges\"",
		"\"procedure\"",
		"\"public\"",
		"\"read\"",
		"\"real\"",
		"\"references\"",
		"\"relative\"",
		"\"restrict\"",
		"\"revoke\"",
		"\"right\"",
		"\"rollback\"",
		"\"rows\"",
		"\"schema\"",
		"\"scroll\"",
		"\"second\"",
		"\"section\"",
		"\"select\"",
		"\"session\"",
		"\"session_user\"",
		"\"set\"",
		"\"size\"",
		"\"smallint\"",
		"\"some\"",
		"\"space\"",
		"\"sql\"",
		"\"sqlcode\"",
		"\"sqlerror\"",
		"\"sqlstate\"",
		"\"substring\"",
		"\"sum\"",
		"\"system_user\"",
		"\"table\"",
		"\"temporary\"",
		"\"then\"",
		"\"time\"",
		"\"timestamp\"",
		"\"timezone_hour\"",
		"\"timezone_minute\"",
		"\"to\"",
		"\"trailing\"",
		"\"transaction\"",
		"\"translate\"",
		"\"translation\"",
		"\"trim\"",
		"\"true\"",
		"\"union\"",
		"\"unique\"",
		"\"unknown\"",
		"\"update\"",
		"\"upper\"",
		"\"usage\"",
		"\"user\"",
		"\"using\"",
		"\"value\"",
		"\"values\"",
		"\"varchar\"",
		"\"varying\"",
		"\"view\"",
		"\"when\"",
		"\"whenever\"",
		"\"where\"",
		"\"with\"",
		"\"work\"",
		"\"write\"",
		"\"year\"",
		"\"zone\"",
		"an integer number",
		"a number",
		"'",
		".",
		"-",
		"_",
		"..",
		"<>",
		"<=",
		">=",
		"||",
		"a national character string",
		"a bit string",
		"a hexadecimal string",
		"an embedded variable",
		"an identifier",
		"a number",
		"a character string",
		"a delimited identifier",
		"%",
		"&",
		"(",
		")",
		"*",
		"+",
		",",
		"/",
		":",
		";",
		"<",
		"=",
		">",
		"?",
		"|",
		"[",
		"]",
		"introducing _",
		"a letter",
		"a separator",
		"a comment",
		"the new line character",
		"the space character",
		"any character",
		"\"comment\"",
		"\"rule\"",
		"\"index\"",
		"\"sysdate\"",
		"\"text\""
	};

	protected void buildTokenTypeASTClassMap() {
		tokenTypeToASTClassMap=null;
	};

	private static final long[] mk_tokenSet_0() {
		long[] data = new long[10];
		data[0]=36028797018963936L;
		data[1]=9024791440785408L;
		data[2]=144115222435594240L;
		data[3]=36029896530591744L;
		data[4]=4611846547125068032L;
		return data;
	}
	public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
	private static final long[] mk_tokenSet_1() {
		long[] data = new long[10];
		data[0]=288230376151711744L;
		data[1]=1143767507730432L;
		data[2]=576461302059237376L;
		data[3]=5476379414625255424L;
		data[4]=4621834374587875328L;
		return data;
	}
	public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
	private static final long[] mk_tokenSet_2() {
		long[] data = new long[10];
		data[0]=288230376151711744L;
		data[1]=1143767507664896L;
		data[2]=549755813888L;
		data[3]=5476377146882523136L;
		data[4]=4621834374587875328L;
		return data;
	}
	public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
	private static final long[] mk_tokenSet_3() {
		long[] data = new long[8];
		data[0]=576460752303423488L;
		data[1]=36099165767335936L;
		data[2]=2112L;
		data[3]=1049088L;
		return data;
	}
	public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
	private static final long[] mk_tokenSet_4() {
		long[] data = new long[10];
		data[0]=72057594037927904L;
		data[1]=17592186044416L;
		data[2]=-9221049309681545216L;
		data[3]=864691129538330624L;
		data[4]=4621854165797183488L;
		return data;
	}
	public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
	private static final long[] mk_tokenSet_5() {
		long[] data = new long[10];
		data[0]=36028797018963936L;
		data[1]=17592186044416L;
		data[4]=4611705809636696064L;
		return data;
	}
	public static final BitSet _tokenSet_5 = new BitSet(mk_tokenSet_5());
	private static final long[] mk_tokenSet_6() {
		long[] data = new long[10];
		data[0]=36028797018963936L;
		data[1]=17592186044416L;
		data[2]=2252349570547712L;
		data[3]=864691128455135232L;
		data[4]=4621854165797183488L;
		return data;
	}
	public static final BitSet _tokenSet_6 = new BitSet(mk_tokenSet_6());
	private static final long[] mk_tokenSet_7() {
		long[] data = new long[10];
		data[0]=36028797018963936L;
		data[1]=17592186044416L;
		data[2]=144115188075855872L;
		data[3]=36029896530591744L;
		data[4]=4611846547125067776L;
		return data;
	}
	public static final BitSet _tokenSet_7 = new BitSet(mk_tokenSet_7());
	private static final long[] mk_tokenSet_8() {
		long[] data = new long[10];
		data[0]=324259173170675680L;
		data[1]=577612671132156449L;
		data[2]=2508795817566142976L;
		data[3]=932250629014028332L;
		data[4]=4928802628437043754L;
		return data;
	}
	public static final BitSet _tokenSet_8 = new BitSet(mk_tokenSet_8());
	private static final long[] mk_tokenSet_9() {
		long[] data = new long[10];
		data[0]=324259173170675680L;
		data[1]=577612671132156449L;
		data[2]=2508795817566142976L;
		data[3]=932250629014028332L;
		data[4]=4910788229927561770L;
		return data;
	}
	public static final BitSet _tokenSet_9 = new BitSet(mk_tokenSet_9());
	private static final long[] mk_tokenSet_10() {
		long[] data = new long[10];
		data[0]=36028797018963936L;
		data[1]=17592186044416L;
		data[4]=4629720208146178048L;
		return data;
	}
	public static final BitSet _tokenSet_10 = new BitSet(mk_tokenSet_10());
	private static final long[] mk_tokenSet_11() {
		long[] data = new long[10];
		data[0]=36028797018963936L;
		data[1]=17592186044416L;
		data[2]=144115188075855872L;
		data[4]=4611705809636696064L;
		return data;
	}
	public static final BitSet _tokenSet_11 = new BitSet(mk_tokenSet_11());
	private static final long[] mk_tokenSet_12() {
		long[] data = new long[12];
		data[0]=274877906944L;
		data[1]=2306282813865197824L;
		data[2]=5764608278948487168L;
		data[3]=864726313095659536L;
		data[4]=32768L;
		data[5]=512L;
		return data;
	}
	public static final BitSet _tokenSet_12 = new BitSet(mk_tokenSet_12());
	private static final long[] mk_tokenSet_13() {
		long[] data = new long[10];
		data[1]=1143492092887040L;
		data[2]=549755813888L;
		data[3]=864691128455135232L;
		data[4]=4621834374587875328L;
		return data;
	}
	public static final BitSet _tokenSet_13 = new BitSet(mk_tokenSet_13());
	private static final long[] mk_tokenSet_14() {
		long[] data = new long[10];
		data[0]=36028797018963936L;
		data[1]=25881482936864L;
		data[2]=58547344911630848L;
		data[3]=896220723893501992L;
		data[4]=4910225279437253130L;
		return data;
	}
	public static final BitSet _tokenSet_14 = new BitSet(mk_tokenSet_14());
	private static final long[] mk_tokenSet_15() {
		long[] data = new long[10];
		data[0]=36028797018963936L;
		data[1]=17592186044416L;
		data[2]=2252349570547712L;
		data[3]=864691128455151616L;
		data[4]=4621854165797183488L;
		return data;
	}
	public static final BitSet _tokenSet_15 = new BitSet(mk_tokenSet_15());
	private static final long[] mk_tokenSet_16() {
		long[] data = new long[10];
		data[0]=36028797018963936L;
		data[1]=1143561938534401L;
		data[2]=9043968L;
		data[3]=4611686018968453126L;
		data[4]=20576260602208320L;
		return data;
	}
	public static final BitSet _tokenSet_16 = new BitSet(mk_tokenSet_16());
	private static final long[] mk_tokenSet_17() {
		long[] data = new long[20];
		data[0]=-16L;
		for (int i = 1; i<=3; i++) { data[i]=-1L; }
		data[4]=-18014398509481985L;
		data[5]=1023L;
		return data;
	}
	public static final BitSet _tokenSet_17 = new BitSet(mk_tokenSet_17());
	private static final long[] mk_tokenSet_18() {
		long[] data = new long[10];
		data[1]=1161085371940864L;
		data[2]=360287970256748544L;
		data[3]=275419496454L;
		data[4]=20688410807173184L;
		return data;
	}
	public static final BitSet _tokenSet_18 = new BitSet(mk_tokenSet_18());
	private static final long[] mk_tokenSet_19() {
		long[] data = new long[10];
		data[0]=36028797018963936L;
		data[1]=1143493185503232L;
		data[3]=541065222L;
		data[4]=4632253483473444928L;
		return data;
	}
	public static final BitSet _tokenSet_19 = new BitSet(mk_tokenSet_19());
	private static final long[] mk_tokenSet_20() {
		long[] data = new long[10];
		data[1]=1092616192L;
		data[3]=541065222L;
		data[4]=20547673299877952L;
		return data;
	}
	public static final BitSet _tokenSet_20 = new BitSet(mk_tokenSet_20());
	private static final long[] mk_tokenSet_21() {
		long[] data = new long[10];
		data[1]=2251869625778176L;
		data[2]=8388608L;
		data[3]=541065222L;
		data[4]=20547673299877952L;
		return data;
	}
	public static final BitSet _tokenSet_21 = new BitSet(mk_tokenSet_21());
	private static final long[] mk_tokenSet_22() {
		long[] data = new long[10];
		data[1]=69812092928L;
		data[2]=8388608L;
		data[3]=541065222L;
		data[4]=20547673299877952L;
		return data;
	}
	public static final BitSet _tokenSet_22 = new BitSet(mk_tokenSet_22());
	private static final long[] mk_tokenSet_23() {
		long[] data = new long[12];
		data[0]=324259173170675682L;
		data[1]=1164198165707227140L;
		data[2]=146384616592277504L;
		data[3]=36029897071657094L;
		data[4]=4632394220425061700L;
		data[5]=64L;
		return data;
	}
	public static final BitSet _tokenSet_23 = new BitSet(mk_tokenSet_23());
	private static final long[] mk_tokenSet_24() {
		long[] data = new long[20];
		data[0]=-16L;
		for (int i = 1; i<=3; i++) { data[i]=-1L; }
		data[4]=-281474976710657L;
		data[5]=1023L;
		return data;
	}
	public static final BitSet _tokenSet_24 = new BitSet(mk_tokenSet_24());
	private static final long[] mk_tokenSet_25() {
		long[] data = new long[10];
		data[1]=2251800906301440L;
		data[2]=2147483648L;
		data[3]=541065350L;
		data[4]=20547673299877952L;
		return data;
	}
	public static final BitSet _tokenSet_25 = new BitSet(mk_tokenSet_25());
	private static final long[] mk_tokenSet_26() {
		long[] data = new long[10];
		data[1]=2251800906301440L;
		data[2]=2147483648L;
		data[3]=541065222L;
		data[4]=20547673299877952L;
		return data;
	}
	public static final BitSet _tokenSet_26 = new BitSet(mk_tokenSet_26());
	private static final long[] mk_tokenSet_27() {
		long[] data = new long[10];
		data[1]=2251800906301440L;
		data[2]=9007201402224640L;
		data[3]=541065350L;
		data[4]=20547673299877952L;
		return data;
	}
	public static final BitSet _tokenSet_27 = new BitSet(mk_tokenSet_27());
	private static final long[] mk_tokenSet_28() {
		long[] data = new long[10];
		data[1]=1125969718935552L;
		data[2]=8388608L;
		data[3]=541065222L;
		data[4]=20547673299877952L;
		return data;
	}
	public static final BitSet _tokenSet_28 = new BitSet(mk_tokenSet_28());
	private static final long[] mk_tokenSet_29() {
		long[] data = new long[10];
		data[1]=6755400533671936L;
		data[2]=2415919104L;
		data[3]=541065222L;
		data[4]=20547673299877952L;
		return data;
	}
	public static final BitSet _tokenSet_29 = new BitSet(mk_tokenSet_29());
	private static final long[] mk_tokenSet_30() {
		long[] data = new long[10];
		data[0]=36028797018963938L;
		data[1]=2269461811822592L;
		data[2]=144132782417805312L;
		data[3]=541065350L;
		data[4]=4632394220424929344L;
		return data;
	}
	public static final BitSet _tokenSet_30 = new BitSet(mk_tokenSet_30());
	private static final long[] mk_tokenSet_31() {
		long[] data = new long[10];
		data[1]=17592186044416L;
		data[2]=549755813888L;
		data[3]=864691128455135232L;
		data[4]=4611695776593084416L;
		return data;
	}
	public static final BitSet _tokenSet_31 = new BitSet(mk_tokenSet_31());
	private static final long[] mk_tokenSet_32() {
		long[] data = new long[10];
		data[0]=36028797018963936L;
		data[1]=17609365913600L;
		data[2]=144115188075855872L;
		data[3]=36029896530591744L;
		data[4]=4611846547125067776L;
		return data;
	}
	public static final BitSet _tokenSet_32 = new BitSet(mk_tokenSet_32());
	private static final long[] mk_tokenSet_33() {
		long[] data = new long[10];
		data[0]=36028797018963936L;
		data[1]=19157890602369024L;
		data[2]=256L;
		data[4]=4611705809636696064L;
		return data;
	}
	public static final BitSet _tokenSet_33 = new BitSet(mk_tokenSet_33());
	private static final long[] mk_tokenSet_34() {
		long[] data = new long[12];
		data[0]=3638908498915360736L;
		data[1]=-2279507299453759101L;
		data[2]=8224993427189063693L;
		data[3]=5656566223501105303L;
		data[4]=4890788379508404320L;
		data[5]=512L;
		return data;
	}
	public static final BitSet _tokenSet_34 = new BitSet(mk_tokenSet_34());
	private static final long[] mk_tokenSet_35() {
		long[] data = new long[12];
		data[0]=7241788200811757538L;
		data[1]=-5170774494398190675L;
		data[2]=8646097618962146957L;
		data[3]=1076612383584008383L;
		data[4]=5188041210856996862L;
		data[5]=848L;
		return data;
	}
	public static final BitSet _tokenSet_35 = new BitSet(mk_tokenSet_35());
	private static final long[] mk_tokenSet_36() {
		long[] data = new long[10];
		data[0]=36028797018963936L;
		data[1]=17592253153280L;
		data[4]=4611705809636696064L;
		return data;
	}
	public static final BitSet _tokenSet_36 = new BitSet(mk_tokenSet_36());
	private static final long[] mk_tokenSet_37() {
		long[] data = new long[10];
		data[1]=17592186044416L;
		data[2]=549755813888L;
		data[3]=864691128455135232L;
		data[4]=4612826075821506560L;
		return data;
	}
	public static final BitSet _tokenSet_37 = new BitSet(mk_tokenSet_37());
	private static final long[] mk_tokenSet_38() {
		long[] data = new long[10];
		data[1]=17592186044416L;
		data[2]=549755813888L;
		data[3]=864691128455135232L;
		data[4]=4621834374587875328L;
		return data;
	}
	public static final BitSet _tokenSet_38 = new BitSet(mk_tokenSet_38());
	private static final long[] mk_tokenSet_39() {
		long[] data = new long[10];
		data[1]=4398046511104L;
		data[3]=18018796555993088L;
		data[4]=297238674918090752L;
		return data;
	}
	public static final BitSet _tokenSet_39 = new BitSet(mk_tokenSet_39());
	private static final long[] mk_tokenSet_40() {
		long[] data = new long[10];
		data[0]=36028797018963936L;
		data[1]=17592186044416L;
		data[2]=1649267572736L;
		data[3]=864691128455135232L;
		data[4]=4639868564306669568L;
		return data;
	}
	public static final BitSet _tokenSet_40 = new BitSet(mk_tokenSet_40());
	private static final long[] mk_tokenSet_41() {
		long[] data = new long[10];
		data[0]=36028797018963936L;
		data[1]=17592186044416L;
		data[2]=1099511758848L;
		data[4]=4629720208146182144L;
		return data;
	}
	public static final BitSet _tokenSet_41 = new BitSet(mk_tokenSet_41());
	private static final long[] mk_tokenSet_42() {
		long[] data = new long[10];
		data[1]=17592186044416L;
		data[2]=2252349570547712L;
		data[3]=864691128455135232L;
		data[4]=4621834374587875328L;
		return data;
	}
	public static final BitSet _tokenSet_42 = new BitSet(mk_tokenSet_42());
	private static final long[] mk_tokenSet_43() {
		long[] data = new long[10];
		data[0]=36028797018963936L;
		data[1]=17592186044416L;
		data[2]=1649267441664L;
		data[3]=864691128455135232L;
		data[4]=4639868564306669568L;
		return data;
	}
	public static final BitSet _tokenSet_43 = new BitSet(mk_tokenSet_43());
	private static final long[] mk_tokenSet_44() {
		long[] data = new long[10];
		data[0]=36028797018963936L;
		data[1]=17592186044416L;
		data[2]=1099511627776L;
		data[4]=4629720208146182144L;
		return data;
	}
	public static final BitSet _tokenSet_44 = new BitSet(mk_tokenSet_44());
	private static final long[] mk_tokenSet_45() {
		long[] data = new long[10];
		data[0]=36028797018963936L;
		data[1]=17592186044416L;
		data[2]=144115188075855872L;
		data[4]=4611846547125051392L;
		return data;
	}
	public static final BitSet _tokenSet_45 = new BitSet(mk_tokenSet_45());
	private static final long[] mk_tokenSet_46() {
		long[] data = new long[10];
		data[0]=36028797018963936L;
		data[1]=17729624997889L;
		data[2]=2450248472654512128L;
		data[3]=36029905120526336L;
		data[4]=4611846547661938720L;
		return data;
	}
	public static final BitSet _tokenSet_46 = new BitSet(mk_tokenSet_46());
	private static final long[] mk_tokenSet_47() {
		long[] data = new long[10];
		data[0]=36028797018963936L;
		data[1]=17592186044416L;
		data[4]=19791209308160L;
		return data;
	}
	public static final BitSet _tokenSet_47 = new BitSet(mk_tokenSet_47());
	private static final long[] mk_tokenSet_48() {
		long[] data = new long[12];
		data[0]=1765411053929234400L;
		data[1]=4945603370862772224L;
		data[2]=144115222444509248L;
		data[3]=36038714099499520L;
		data[4]=4611846547125068032L;
		data[5]=32L;
		return data;
	}
	public static final BitSet _tokenSet_48 = new BitSet(mk_tokenSet_48());
	private static final long[] mk_tokenSet_49() {
		long[] data = new long[10];
		data[0]=36028797018963936L;
		data[1]=1151781389779488L;
		data[2]=58547344911630976L;
		data[3]=896220723893501998L;
		data[4]=4910225279437253194L;
		return data;
	}
	public static final BitSet _tokenSet_49 = new BitSet(mk_tokenSet_49());
	private static final long[] mk_tokenSet_50() {
		long[] data = new long[10];
		data[0]=2305843009213693952L;
		data[1]=-9205322247797997429L;
		data[2]=2676557928005517325L;
		data[3]=144115471543736450L;
		data[4]=278941831863210016L;
		return data;
	}
	public static final BitSet _tokenSet_50 = new BitSet(mk_tokenSet_50());
	private static final long[] mk_tokenSet_51() {
		long[] data = new long[10];
		data[0]=36028797018963936L;
		data[1]=25881482936864L;
		data[2]=202662532987486720L;
		data[3]=896220723893501992L;
		data[4]=4910225279437253130L;
		return data;
	}
	public static final BitSet _tokenSet_51 = new BitSet(mk_tokenSet_51());
	private static final long[] mk_tokenSet_52() {
		long[] data = new long[10];
		data[0]=36028797018963936L;
		data[1]=17592186044416L;
		data[4]=4611705810173566976L;
		return data;
	}
	public static final BitSet _tokenSet_52 = new BitSet(mk_tokenSet_52());
	private static final long[] mk_tokenSet_53() {
		long[] data = new long[10];
		data[0]=36028797018963936L;
		data[1]=61065871802921L;
		data[2]=562951603829735936L;
		data[3]=932250895302000680L;
		data[4]=4917543698105133578L;
		return data;
	}
	public static final BitSet _tokenSet_53 = new BitSet(mk_tokenSet_53());
	private static final long[] mk_tokenSet_54() {
		long[] data = new long[10];
		data[0]=36028797018963936L;
		data[1]=21990232555520L;
		data[2]=549755813888L;
		data[3]=882709925011128320L;
		data[4]=4908958640968312832L;
		return data;
	}
	public static final BitSet _tokenSet_54 = new BitSet(mk_tokenSet_54());
	private static final long[] mk_tokenSet_55() {
		long[] data = new long[10];
		data[0]=2341871806232657888L;
		data[1]=-9223319054121434999L;
		data[2]=2676557929079259149L;
		data[3]=144115471543736450L;
		data[4]=4890657537708535840L;
		return data;
	}
	public static final BitSet _tokenSet_55 = new BitSet(mk_tokenSet_55());
	private static final long[] mk_tokenSet_56() {
		long[] data = new long[10];
		data[0]=2341871806232657888L;
		data[1]=-9223319054121434999L;
		data[2]=2676557928005517325L;
		data[3]=144115471543736450L;
		data[4]=4890647642036776992L;
		return data;
	}
	public static final BitSet _tokenSet_56 = new BitSet(mk_tokenSet_56());
	private static final long[] mk_tokenSet_57() {
		long[] data = new long[10];
		data[1]=21990232555520L;
		data[2]=549755813888L;
		data[3]=882709925011128320L;
		data[4]=4908938849759012864L;
		return data;
	}
	public static final BitSet _tokenSet_57 = new BitSet(mk_tokenSet_57());
	private static final long[] mk_tokenSet_58() {
		long[] data = new long[10];
		data[0]=2341871806232657888L;
		data[1]=-9223319054121434999L;
		data[2]=2676557929079259149L;
		data[3]=144115471543736450L;
		data[4]=4890657537171664928L;
		return data;
	}
	public static final BitSet _tokenSet_58 = new BitSet(mk_tokenSet_58());
	private static final long[] mk_tokenSet_59() {
		long[] data = new long[10];
		data[0]=324259173170675680L;
		data[1]=576486633786360352L;
		data[2]=58547344911630848L;
		data[3]=896220723893501992L;
		data[4]=4910225279437253130L;
		return data;
	}
	public static final BitSet _tokenSet_59 = new BitSet(mk_tokenSet_59());
	private static final long[] mk_tokenSet_60() {
		long[] data = new long[10];
		data[1]=17592186044416L;
		data[2]=549755813888L;
		data[3]=864691128455135232L;
		data[4]=4611700174840922112L;
		return data;
	}
	public static final BitSet _tokenSet_60 = new BitSet(mk_tokenSet_60());
	private static final long[] mk_tokenSet_61() {
		long[] data = new long[10];
		data[0]=36028797018963936L;
		data[1]=1151781389779488L;
		data[2]=58547344911630976L;
		data[3]=896220723893501996L;
		data[4]=4910225279437253194L;
		return data;
	}
	public static final BitSet _tokenSet_61 = new BitSet(mk_tokenSet_61());
	private static final long[] mk_tokenSet_62() {
		long[] data = new long[10];
		data[0]=2305843009213693952L;
		data[1]=206158430208L;
		data[2]=2306134659026927624L;
		data[3]=144115196665796736L;
		data[4]=20547673303027744L;
		return data;
	}
	public static final BitSet _tokenSet_62 = new BitSet(mk_tokenSet_62());
	private static final long[] mk_tokenSet_63() {
		long[] data = new long[10];
		data[0]=36028797018963936L;
		data[1]=1186965778645672L;
		data[2]=573085803039687168L;
		data[3]=932250895302033454L;
		data[4]=5167493537553738250L;
		return data;
	}
	public static final BitSet _tokenSet_63 = new BitSet(mk_tokenSet_63());
	private static final long[] mk_tokenSet_64() {
		long[] data = new long[10];
		data[0]=36028797018963936L;
		data[1]=1151781389779488L;
		data[2]=58547344911630848L;
		data[3]=896220723893501996L;
		data[4]=4910225279437253130L;
		return data;
	}
	public static final BitSet _tokenSet_64 = new BitSet(mk_tokenSet_64());
	private static final long[] mk_tokenSet_65() {
		long[] data = new long[10];
		data[0]=2341871806232657888L;
		data[1]=61272030233256L;
		data[2]=2879220462066614792L;
		data[3]=1076366091967830186L;
		data[4]=5188041210856765994L;
		return data;
	}
	public static final BitSet _tokenSet_65 = new BitSet(mk_tokenSet_65());
	private static final long[] mk_tokenSet_66() {
		long[] data = new long[10];
		data[0]=2305843009213693952L;
		data[1]=206158430208L;
		data[2]=2306136858050183176L;
		data[3]=144115196665796736L;
		data[4]=20547673303027744L;
		return data;
	}
	public static final BitSet _tokenSet_66 = new BitSet(mk_tokenSet_66());
	private static final long[] mk_tokenSet_67() {
		long[] data = new long[10];
		data[0]=36028797018963936L;
		data[1]=17592186044416L;
		data[4]=4611987285150277632L;
		return data;
	}
	public static final BitSet _tokenSet_67 = new BitSet(mk_tokenSet_67());
	private static final long[] mk_tokenSet_68() {
		long[] data = new long[10];
		data[1]=35184372482048L;
		data[2]=360287970256748544L;
		data[3]=274878431232L;
		data[4]=422212484005888L;
		return data;
	}
	public static final BitSet _tokenSet_68 = new BitSet(mk_tokenSet_68());
	private static final long[] mk_tokenSet_69() {
		long[] data = new long[10];
		data[1]=1125969719197696L;
		data[2]=8388608L;
		data[3]=541065222L;
		data[4]=20688410788233280L;
		return data;
	}
	public static final BitSet _tokenSet_69 = new BitSet(mk_tokenSet_69());
	private static final long[] mk_tokenSet_70() {
		long[] data = new long[10];
		data[1]=1125969719197696L;
		data[2]=8388608L;
		data[3]=541065222L;
		data[4]=20547673299877952L;
		return data;
	}
	public static final BitSet _tokenSet_70 = new BitSet(mk_tokenSet_70());
	private static final long[] mk_tokenSet_71() {
		long[] data = new long[10];
		data[1]=1125969718935552L;
		data[2]=8388608L;
		data[3]=541065222L;
		data[4]=20688410788233280L;
		return data;
	}
	public static final BitSet _tokenSet_71 = new BitSet(mk_tokenSet_71());
	private static final long[] mk_tokenSet_72() {
		long[] data = new long[10];
		data[1]=1125969718935552L;
		data[2]=8388608L;
		data[3]=541065222L;
		data[4]=20547673301975104L;
		return data;
	}
	public static final BitSet _tokenSet_72 = new BitSet(mk_tokenSet_72());
	private static final long[] mk_tokenSet_73() {
		long[] data = new long[10];
		data[0]=2328444777701369600L;
		data[1]=-9222201914872037239L;
		data[2]=2676557946259128333L;
		data[3]=144115609524279462L;
		data[4]=287950130629589088L;
		return data;
	}
	public static final BitSet _tokenSet_73 = new BitSet(mk_tokenSet_73());
	private static final long[] mk_tokenSet_74() {
		long[] data = new long[10];
		data[0]=36028797018963936L;
		data[1]=25881482936864L;
		data[2]=202662532987486720L;
		data[3]=896220723893501992L;
		data[4]=4910788229390674442L;
		return data;
	}
	public static final BitSet _tokenSet_74 = new BitSet(mk_tokenSet_74());
	private static final long[] mk_tokenSet_75() {
		long[] data = new long[10];
		data[1]=68719476736L;
		data[2]=1374448271368L;
		data[3]=4096L;
		data[4]=18295873488289824L;
		return data;
	}
	public static final BitSet _tokenSet_75 = new BitSet(mk_tokenSet_75());
	private static final long[] mk_tokenSet_76() {
		long[] data = new long[10];
		data[1]=68719476736L;
		data[2]=1374431494152L;
		data[3]=4096L;
		data[4]=18295873488289824L;
		return data;
	}
	public static final BitSet _tokenSet_76 = new BitSet(mk_tokenSet_76());
	private static final long[] mk_tokenSet_77() {
		long[] data = new long[10];
		data[1]=68719476736L;
		data[2]=1374397939720L;
		data[3]=4096L;
		data[4]=18295873488289824L;
		return data;
	}
	public static final BitSet _tokenSet_77 = new BitSet(mk_tokenSet_77());
	private static final long[] mk_tokenSet_78() {
		long[] data = new long[10];
		data[0]=36028797018963936L;
		data[1]=17592186044417L;
		data[4]=4611705809636696064L;
		return data;
	}
	public static final BitSet _tokenSet_78 = new BitSet(mk_tokenSet_78());
	private static final long[] mk_tokenSet_79() {
		long[] data = new long[10];
		data[1]=206158430208L;
		data[2]=2306134659026927624L;
		data[3]=8589938816L;
		data[4]=20547673303027744L;
		return data;
	}
	public static final BitSet _tokenSet_79 = new BitSet(mk_tokenSet_79());
	private static final long[] mk_tokenSet_80() {
		long[] data = new long[10];
		data[0]=36028797018963936L;
		data[1]=17592186044416L;
		data[2]=144405463440818176L;
		data[3]=8589942784L;
		data[4]=4611846547125051392L;
		return data;
	}
	public static final BitSet _tokenSet_80 = new BitSet(mk_tokenSet_80());
	private static final long[] mk_tokenSet_81() {
		long[] data = new long[10];
		data[1]=68719476736L;
		data[2]=1374431494152L;
		data[3]=4096L;
		data[4]=20547673301975072L;
		return data;
	}
	public static final BitSet _tokenSet_81 = new BitSet(mk_tokenSet_81());
	private static final long[] mk_tokenSet_82() {
		long[] data = new long[10];
		data[0]=2305843009213693954L;
		data[1]=-9223336646307479415L;
		data[2]=2676557928005517325L;
		data[3]=144115471543736450L;
		data[4]=278941831863210016L;
		return data;
	}
	public static final BitSet _tokenSet_82 = new BitSet(mk_tokenSet_82());
	private static final long[] mk_tokenSet_83() {
		long[] data = new long[10];
		data[0]=36028797018963936L;
		data[1]=25881482936864L;
		data[2]=58547344911630848L;
		data[3]=896220723893501992L;
		data[4]=4909099378456668682L;
		return data;
	}
	public static final BitSet _tokenSet_83 = new BitSet(mk_tokenSet_83());
	private static final long[] mk_tokenSet_84() {
		long[] data = new long[10];
		data[0]=2305843009213693952L;
		data[1]=-9223371830696345471L;
		data[2]=2316269957748768781L;
		data[3]=144115196665829506L;
		data[4]=278941831846432800L;
		return data;
	}
	public static final BitSet _tokenSet_84 = new BitSet(mk_tokenSet_84());
	private static final long[] mk_tokenSet_85() {
		long[] data = new long[10];
		data[0]=2305843009213693952L;
		data[1]=-9223371830679568247L;
		data[2]=2316269957748768781L;
		data[3]=144115196665829506L;
		data[4]=278941831846432800L;
		return data;
	}
	public static final BitSet _tokenSet_85 = new BitSet(mk_tokenSet_85());
	private static final long[] mk_tokenSet_86() {
		long[] data = new long[10];
		data[0]=2305843009213693952L;
		data[1]=-9223371829603729271L;
		data[2]=2316269957748768781L;
		data[3]=144115197206894726L;
		data[4]=278941831846432864L;
		return data;
	}
	public static final BitSet _tokenSet_86 = new BitSet(mk_tokenSet_86());
	private static final long[] mk_tokenSet_87() {
		long[] data = new long[10];
		data[0]=2328444777701369600L;
		data[1]=-9222201914872037239L;
		data[2]=2676557946259128333L;
		data[3]=4755801627951667366L;
		data[4]=287950130629589088L;
		return data;
	}
	public static final BitSet _tokenSet_87 = new BitSet(mk_tokenSet_87());

	}
