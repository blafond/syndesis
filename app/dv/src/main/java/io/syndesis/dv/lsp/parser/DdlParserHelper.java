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
package io.syndesis.dv.lsp.parser;

import org.eclipse.lsp4j.Position;
import org.teiid.query.parser.Token;

public class DdlParserHelper {
	private DdlTokenAnalyzer analyzer;
	public DdlTokenAnalyzer getAnalyzer() {
		return analyzer;
	}

	public String getDdlStatement() {
		return ddlStatement;
	}

	private String ddlStatement;
	
	public DdlParserHelper(String ddlStatement) {
		super();
		this.analyzer = new DdlTokenAnalyzer(ddlStatement);
		this.analyzer.getStatementType();
	}
	
	public DdlParserHelper() {
		super();
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Remember that Position line/column are '0' based and anaylyzer is '1' based
	 * @param position
	 * @return
	 */
	public Token getToken(Position position) {
		return this.analyzer.getTokenAtOrBeforeCursor(position.getLine()+1, position.getCharacter()+1);
	}
	
}
