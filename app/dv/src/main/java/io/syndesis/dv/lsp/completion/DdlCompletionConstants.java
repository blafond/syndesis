/*
 * Copyright (C) 2016 Red Hat, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.syndesis.dv.lsp.completion;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.lsp4j.CompletionItemKind;

import io.syndesis.dv.lsp.parser.DdlAnalyzerConstants;

public interface DdlCompletionConstants extends DdlAnalyzerConstants {

	String[] VIEW_ITEM_DATA = {
			/*
			 * The label of this completion item. By default also the text that is inserted
			 * when selecting this completion.
			 */
			tokenImage[VIEW].toUpperCase(), // String label;
			
			/*
			 * A human-readable string with additional information about this item, like
			 * type or symbol information.
			 */
			null, // String detail;
			
			/*
			 * A human-readable string that represents a doc-comment.
			 */
			null, // Either<String, MarkupContent> documentation;
			
			/*
			 * A string that should be inserted a document when selecting this completion.
			 * When `falsy` the label is used.
			 */
			tokenImage[VIEW].toUpperCase(), // String insertText;
			
			/*
			 * The format of the insert text. The format applies to both the `insertText`
			 * property and the `newText` property of a provided `textEdit`.
			 */
			null // InsertTextFormat insertTextFormat;
	};
	
	String[] CREATE_ITEM_DATA = {
			tokenImage[CREATE].toUpperCase(),   // String label;
			null, // String detail;
			null, // Either<String, MarkupContent> documentation;
			tokenImage[CREATE].toUpperCase(), // String insertText;
			null // InsertTextFormat insertTextFormat;
	};
	
	String[] VIRTUAL_ITEM_DATA = {
			tokenImage[VIRTUAL].toUpperCase(),   // String label;
			null, // String detail;
			null, // Either<String, MarkupContent> documentation;
			tokenImage[VIRTUAL].toUpperCase(), // String insertText;
			null // InsertTextFormat insertTextFormat;
	};
	
	String[] TABLE_ITEM_DATA = {
			tokenImage[TABLE].toUpperCase(),   // String label;
			null, // String detail;
			null, // Either<String, MarkupContent> documentation;
			tokenImage[TABLE].toUpperCase(), // String insertText;
			null // InsertTextFormat insertTextFormat;
	};
	
	String[] PROCEDURE_ITEM_DATA = {
			tokenImage[PROCEDURE].toUpperCase(),   // String label;
			null, // String detail;
			null, // Either<String, MarkupContent> documentation;
			tokenImage[PROCEDURE].toUpperCase(), // String insertText;
			null // InsertTextFormat insertTextFormat;
	};

	String[] GLOBAL_ITEM_DATA = {
			tokenImage[GLOBAL].toUpperCase(),   // String label;d;
			null, // String detail;
			null, // Either<String, MarkupContent> documentation;
			tokenImage[GLOBAL].toUpperCase(), // String insertText;
			null // InsertTextFormat insertTextFormat;
	};

	String[] FOREIGN_ITEM_DATA = {
			tokenImage[FOREIGN].toUpperCase(),   // String label;
			null, // String detail;
			null, // Either<String, MarkupContent> documentation;
			tokenImage[FOREIGN].toUpperCase(), // String insertText;
			null // InsertTextFormat insertTextFormat;
	};

	String[] TRIGGER_ITEM_DATA = {
			tokenImage[TRIGGER].toUpperCase(),   // String label;
			null, // String detail;
			null, // Either<String, MarkupContent> documentation;
			tokenImage[TRIGGER].toUpperCase(), // String insertText;
			null // InsertTextFormat insertTextFormat;
	};

	String[] TEMPORARY_ITEM_DATA = {
			tokenImage[TEMPORARY].toUpperCase(),   // String label;
			null, // String detail;
			null, // Either<String, MarkupContent> documentation;
			tokenImage[TEMPORARY].toUpperCase(), // String insertText;
			null // InsertTextFormat insertTextFormat;
	};

	String[] ROLE_ITEM_DATA = {
			tokenImage[ROLE].toUpperCase(),   // String label;
			null, // String detail;
			null, // Either<String, MarkupContent> documentation;
			tokenImage[ROLE].toUpperCase(), // String insertText;
			null // InsertTextFormat insertTextFormat;
	};

	String[] SCHEMA_ITEM_DATA = {
			tokenImage[SCHEMA].toUpperCase(),   // String label;
			null, // String detail;
			null, // Either<String, MarkupContent> documentation;
			tokenImage[SCHEMA].toUpperCase(), // String insertText;
			null // InsertTextFormat insertTextFormat;
	};

	String[] SERVER_ITEM_DATA = {
			tokenImage[SERVER].toUpperCase(),   // String label;
			null, // String detail;
			null, // Either<String, MarkupContent> documentation;
			tokenImage[SERVER].toUpperCase(), // String insertText;
			null // InsertTextFormat insertTextFormat;
	};

	String[] DATABASE_ITEM_DATA = {
			tokenImage[DATABASE].toUpperCase(),   // String label;
			null, // String detail;
			null, // Either<String, MarkupContent> documentation;
			tokenImage[DATABASE].toUpperCase(), // String insertText;
			null // InsertTextFormat insertTextFormat;
	};

	String[] SELECT_ITEM_DATA = {
			tokenImage[SELECT].toUpperCase(),   // String label;
			null, // String detail;
			null, // Either<String, MarkupContent> documentation;
			tokenImage[SELECT].toUpperCase(), // String insertText;
			null // InsertTextFormat insertTextFormat;
	};
	
	public static final Map<String, String[]> KEYWORDS_ITEM_DATA = Collections
			.unmodifiableMap(new HashMap<String, String[]>() {
				private static final long serialVersionUID = 1L;

				{
					put(tokenImage[VIEW].toUpperCase(), VIEW_ITEM_DATA);
					put(tokenImage[VIEW].toUpperCase(), CREATE_ITEM_DATA);
					put(tokenImage[VIEW].toUpperCase(), VIRTUAL_ITEM_DATA);
					put(tokenImage[VIEW].toUpperCase(), GLOBAL_ITEM_DATA);
					put(tokenImage[VIEW].toUpperCase(), FOREIGN_ITEM_DATA);
					put(tokenImage[VIEW].toUpperCase(), TABLE_ITEM_DATA);
					put(tokenImage[VIEW].toUpperCase(), TEMPORARY_ITEM_DATA);
					put(tokenImage[VIEW].toUpperCase(), ROLE_ITEM_DATA);
					put(tokenImage[VIEW].toUpperCase(), SCHEMA_ITEM_DATA);
					put(tokenImage[VIEW].toUpperCase(), SERVER_ITEM_DATA);
					put(tokenImage[VIEW].toUpperCase(), DATABASE_ITEM_DATA);
					put(tokenImage[VIEW].toUpperCase(), PROCEDURE_ITEM_DATA);
				}
			});
	
	/**
	 * DATATYPES (in lower case)
	 */

	String[] STRING_ITEM_DATA = {
			tokenImage[STRING],   // String label;
			null, // String detail;
			null, // Either<String, MarkupContent> documentation;
			tokenImage[STRING], // String insertText;
			null // InsertTextFormat insertTextFormat;
	};

	String[] VARBINARY_ITEM_DATA = {
			tokenImage[VARBINARY],   // String label;
			null, // String detail;
			null, // Either<String, MarkupContent> documentation;
			tokenImage[VARBINARY], // String insertText;
			null // InsertTextFormat insertTextFormat;
	};

	String[] VARCHAR_ITEM_DATA = {
			tokenImage[VARCHAR],   // String label;
			null, // String detail;
			null, // Either<String, MarkupContent> documentation;
			tokenImage[VARCHAR], // String insertText;
			null // InsertTextFormat insertTextFormat;
	};

	String[] BOOLEAN_ITEM_DATA = {
			tokenImage[BOOLEAN],   // String label;
			null, // String detail;
			null, // Either<String, MarkupContent> documentation;
			tokenImage[BOOLEAN], // String insertText;
			null // InsertTextFormat insertTextFormat;
	};

	String[] BYTE_ITEM_DATA = {
			tokenImage[BYTE],   // String label;
			null, // String detail;
			null, // Either<String, MarkupContent> documentation;
			tokenImage[BYTE], // String insertText;
			null // InsertTextFormat insertTextFormat;
	};

	String[] TINYINT_ITEM_DATA = {
			tokenImage[TINYINT],   // String label;
			null, // String detail;
			null, // Either<String, MarkupContent> documentation;
			tokenImage[TINYINT], // String insertText;
			null // InsertTextFormat insertTextFormat;
	};

	String[] SHORT_ITEM_DATA = {
			tokenImage[SHORT],   // String label;
			null, // String detail;
			null, // Either<String, MarkupContent> documentation;
			tokenImage[SHORT], // String insertText;
			null // InsertTextFormat insertTextFormat;
	};

	String[] SMALLINT_ITEM_DATA = {
			tokenImage[SMALLINT],   // String label;
			null, // String detail;
			null, // Either<String, MarkupContent> documentation;
			tokenImage[SMALLINT], // String insertText;
			null // InsertTextFormat insertTextFormat;
	};

	String[] CHAR_ITEM_DATA = {
			tokenImage[CHAR],   // String label;
			null, // String detail;
			null, // Either<String, MarkupContent> documentation;
			tokenImage[CHAR], // String insertText;
			null // InsertTextFormat insertTextFormat;
	};

	String[] INTEGER_ITEM_DATA = {
			tokenImage[INTEGER],   // String label;

			null, // String detail;
			null, // Either<String, MarkupContent> documentation;
			tokenImage[INTEGER], // String insertText;
			null // InsertTextFormat insertTextFormat;
	};

	String[] LONG_ITEM_DATA = {
			tokenImage[LONG],   // String label;
			null, // String detail;
			null, // Either<String, MarkupContent> documentation;
			tokenImage[LONG], // String insertText;
			null // InsertTextFormat insertTextFormat;
	};

	String[] BIGINT_ITEM_DATA = {
			tokenImage[BIGINT],   // String label;

			null, // String detail;
			null, // Either<String, MarkupContent> documentation;
			tokenImage[BIGINT], // String insertText;
			null // InsertTextFormat insertTextFormat;
	};

	String[] BIGINTEGER_ITEM_DATA = {
			tokenImage[BIGINTEGER],   // String label;
			null, // String detail;
			null, // Either<String, MarkupContent> documentation;
			tokenImage[BIGINTEGER], // String insertText;
			null // InsertTextFormat insertTextFormat;
	};

	String[] FLOAT_ITEM_DATA = {
			tokenImage[FLOAT],   // String label;

			null, // String detail;
			null, // Either<String, MarkupContent> documentation;
			tokenImage[FLOAT], // String insertText;
			null // InsertTextFormat insertTextFormat;
	};

	String[] REAL_ITEM_DATA = {
			tokenImage[REAL],   // String label;
			null, // String detail;
			null, // Either<String, MarkupContent> documentation;
			tokenImage[REAL], // String insertText;
			null // InsertTextFormat insertTextFormat;
	};

	String[] DOUBLE_ITEM_DATA = {
			tokenImage[DOUBLE],   // String label;

			null, // String detail;
			null, // Either<String, MarkupContent> documentation;
			tokenImage[DOUBLE], // String insertText;
			null // InsertTextFormat insertTextFormat;
	};

	String[] BIGDECIMAL_ITEM_DATA = {
			tokenImage[BIGDECIMAL],   // String label;
			null, // String detail;
			null, // Either<String, MarkupContent> documentation;
			tokenImage[BIGDECIMAL], // String insertText;
			null // InsertTextFormat insertTextFormat;
	};

	String[] DECIMAL_ITEM_DATA = {
			tokenImage[DECIMAL],   // String label;
			null, // String detail;
			null, // Either<String, MarkupContent> documentation;
			tokenImage[DECIMAL], // String insertText;
			null // InsertTextFormat insertTextFormat;
	};

	String[] DATE_ITEM_DATA = {
			tokenImage[DATE],   // String label;
			null, // String detail;
			null, // Either<String, MarkupContent> documentation;
			tokenImage[DATE], // String insertText;
			null // InsertTextFormat insertTextFormat;
	};

	String[] TIME_ITEM_DATA = {
			tokenImage[TIME],   // String label;
			null, // String detail;
			null, // Either<String, MarkupContent> documentation;
			tokenImage[TIME], // String insertText;
			null // InsertTextFormat insertTextFormat;
	};

	String[] TIMESTAMP_ITEM_DATA = {
			tokenImage[TIMESTAMP],   // String label;
			null, // String detail;
			null, // Either<String, MarkupContent> documentation;
			tokenImage[TIMESTAMP], // String insertText;
			null // InsertTextFormat insertTextFormat;
	};

	String[] BLOB_ITEM_DATA = {
			tokenImage[BLOB],   // String label;
			null, // String detail;
			null, // Either<String, MarkupContent> documentation;
			tokenImage[BLOB], // String insertText;
			null // InsertTextFormat insertTextFormat;
	};

	String[] CLOB_ITEM_DATA = {
			tokenImage[CLOB],   // String label;
			null, // String detail;
			null, // Either<String, MarkupContent> documentation;
			tokenImage[CLOB], // String insertText;
			null // InsertTextFormat insertTextFormat;
	};

	String[] XML_ITEM_DATA = {
			tokenImage[XML],   // String label;
			null, // String detail;
			null, // Either<String, MarkupContent> documentation;
			tokenImage[XML], // String insertText;
			null // InsertTextFormat insertTextFormat;
	};

	String[] JSON_ITEM_DATA = {
			tokenImage[JSON],   // String label;
			null, // String detail;
			null, // Either<String, MarkupContent> documentation;
			tokenImage[JSON], // String insertText;
			null // InsertTextFormat insertTextFormat;
	};

	String[] GEOMETRY_ITEM_DATA = {
			tokenImage[GEOMETRY],   // String label;
			null, // String detail;
			null, // Either<String, MarkupContent> documentation;
			tokenImage[GEOMETRY], // String insertText;
			null // InsertTextFormat insertTextFormat;
	};

	String[] GEOGRAPHY_ITEM_DATA = {
			tokenImage[GEOGRAPHY],   // String label;
			null, // String detail;
			null, // Either<String, MarkupContent> documentation;
			tokenImage[GEOGRAPHY], // String insertText;
			null // InsertTextFormat insertTextFormat;
	};

	String[] OBJECT_ITEM_DATA = {
			tokenImage[OBJECT],   // String label;
			null, // String detail;
			null, // Either<String, MarkupContent> documentation;
			tokenImage[OBJECT], // String insertText;
			null // InsertTextFormat insertTextFormat;
	};
	
	public static final Map<String, String[]> DATATYPES_ITEM_DATA = Collections
			.unmodifiableMap(new HashMap<String, String[]>() {
				private static final long serialVersionUID = 1L;

				{
					put(tokenImage[VIEW], STRING_ITEM_DATA);
					put(tokenImage[VIEW], VARBINARY_ITEM_DATA);
					put(tokenImage[VIEW], VARCHAR_ITEM_DATA);
					put(tokenImage[VIEW], BOOLEAN_ITEM_DATA);
					put(tokenImage[VIEW], BYTE_ITEM_DATA);
					put(tokenImage[VIEW], TINYINT_ITEM_DATA);
					put(tokenImage[VIEW], SHORT_ITEM_DATA);
					put(tokenImage[VIEW], SMALLINT_ITEM_DATA);
					put(tokenImage[VIEW], CHAR_ITEM_DATA);
					put(tokenImage[VIEW], INTEGER_ITEM_DATA);
					put(tokenImage[VIEW], LONG_ITEM_DATA);
					put(tokenImage[VIEW], BIGINT_ITEM_DATA);
					put(tokenImage[VIEW], FLOAT_ITEM_DATA);
					put(tokenImage[VIEW], REAL_ITEM_DATA);
					put(tokenImage[VIEW], DOUBLE_ITEM_DATA);
					put(tokenImage[VIEW], BIGDECIMAL_ITEM_DATA);
					put(tokenImage[VIEW], DECIMAL_ITEM_DATA);
					put(tokenImage[VIEW], DATE_ITEM_DATA);
					put(tokenImage[VIEW], TIME_ITEM_DATA);
					put(tokenImage[VIEW], TIMESTAMP_ITEM_DATA);
					put(tokenImage[VIEW], BLOB_ITEM_DATA);
					put(tokenImage[VIEW], CLOB_ITEM_DATA);
					put(tokenImage[VIEW], XML_ITEM_DATA);
					put(tokenImage[VIEW], JSON_ITEM_DATA);
					put(tokenImage[VIEW], GEOMETRY_ITEM_DATA);
					put(tokenImage[VIEW], GEOGRAPHY_ITEM_DATA);
					put(tokenImage[VIEW], OBJECT_ITEM_DATA);
				}
			});
	
}
