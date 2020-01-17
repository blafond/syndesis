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

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.teiid.query.parser.JavaCharStream;
import org.teiid.query.parser.Token;

public class DdlTokenAnalyzer implements DdlAnalyzerConstants {
    
    private final String statement;
    private final Token[] tokens;
    private final STATEMENT_TYPE statementType;
    
    public DdlTokenAnalyzer(String statement) {
        super();
        this.statement = statement;
        this.tokens = init();
        this.statementType = getStatementType();
    }
    
    private Token[] init() {

        JavaCharStream jcs = new JavaCharStream(new StringReader(this.statement));
        TeiidDdlParserTokenManager token_source = new TeiidDdlParserTokenManager(jcs);
        
        List<Token> tokensList = new ArrayList<Token>();
        
        Token nextToken = token_source.getNextToken();
        
        if( nextToken != null ) {
            
            tokensList.add(nextToken);
            
            boolean done = false;
            
            while ( !done ) {
                nextToken = token_source.getNextToken();
                if( nextToken != null && (nextToken.image.length() > 0) ) {       
                    tokensList.add(nextToken);
                } else done = true;
            }
        }
            
        return tokensList.toArray(new Token[0]);
    }

    public static void main(String[] args) {
        String stmt = null;
        DdlTokenAnalyzer analyzer = null;
        Token[] tokens = null;

        stmt = "CREATE VIRTUAL VIEW abc (c1, c2) AS SELECT * FROM T1"; //$NON-NLS-1$
        analyzer = new DdlTokenAnalyzer(stmt);
        analyzer.getStatementType();
        
        stmt = "CREATE FOREIGN TABLE abc (c1, c2) AS SELECT * FROM T1"; //$NON-NLS-1$
        analyzer = new DdlTokenAnalyzer(stmt);
        analyzer.getStatementType();
        
        stmt = "CREATE FOREIGN TEMPORARY TABLE abc (c1, c2) AS SELECT * FROM T1"; //$NON-NLS-1$
        analyzer = new DdlTokenAnalyzer(stmt);
        analyzer.getStatementType();
        
        stmt = "CREATE GLOBAL TEMPORARY TABLE abc (c1, c2) AS SELECT * FROM T1"; //$NON-NLS-1$
        analyzer = new DdlTokenAnalyzer(stmt);
        analyzer.getStatementType();
        
        stmt = "CREATE TABLE abc (c1, c2) AS SELECT * FROM T1"; //$NON-NLS-1$
        analyzer = new DdlTokenAnalyzer(stmt);
        analyzer.getStatementType();
        
        stmt = "CREATE NOTHING FOR NOTHING abc (c1, c2) AS SELECT * FROM T1"; //$NON-NLS-1$
        analyzer = new DdlTokenAnalyzer(stmt);
        analyzer.getStatementType();

        
        stmt = "CREATE VIEW abc (c1, c2) AS SELECT * FROM T1"; //$NON-NLS-1$

        analyzer = new DdlTokenAnalyzer(stmt);
        analyzer.getStatementType();
        
        analyzer.processStatement();
        
        System.out.println("===================================="); //$NON-NLS-1$
        System.out.println("TeiidDDlTokenManager getTokens(ddl)"); //$NON-NLS-1$
        System.out.println("  DDL = " + stmt); //$NON-NLS-1$
        for (Token token : tokens) {
            System.out.println(" > Tkn = " + token.image //$NON-NLS-1$
                    + "\t\t Position ( " +  //$NON-NLS-1$
                    token.beginLine + ", " + token.beginColumn + ")"); //$NON-NLS-1$  //$NON-NLS-2$
        }
        System.out.println("\n");
        System.out.println("All Perenthesis Match = " + analyzer.allParensMatch(tokens)); //$NON-NLS-1$
        System.out.println("===================================="); //$NON-NLS-1$
        System.out.println("\n"); //$NON-NLS-1$
        
        String[] words = analyzer.getNextWords(1, 6);
        for( String word : words ) {
            System.out.println("    Word = " +  word); //$NON-NLS-1$
        }
//        analyzer.getNextWords(1, 7);
//        
//        analyzer.getNextWords(1, 26);
//        analyzer.getNextWords(1, 28);
        words = analyzer.getNextWords(1, 30);
        for( String word : words ) {
            System.out.println("    Word = " +  word); //$NON-NLS-1$
        }
    }

    public String[] getNextWords(int beginLine, int beginColumn) {
        String[] words = new String[0];
        
        System.out.println(" Analyzer:  getNextWords() at Position = (" + beginLine + ", " + beginColumn + ")");
        Token currentToken = getTokenAtCursor(beginLine, beginColumn);
        if( currentToken != null ) {
            System.out.println(" Analyzer:  currentToken = " + currentToken.image);
            words = getNextWordsByKind(currentToken.kind, false);
        } else {
            System.out.println(" Analyzer:  currentToken NOT FOUND at: (" + beginLine + ", " + beginColumn + ")");
        }

        Token previousToken = getTokenAtOrBeforeCursor(beginLine, beginColumn);
        if( previousToken != null ) {
            System.out.println(" Analyzer:  previousToken = " + previousToken.image);
        }
        
        Token followingToken = getTokenAtOrAfterCursor(beginLine, beginColumn);
        if( followingToken != null ) {
            System.out.println(" Analyzer:  followingToken = " + followingToken.image);
        }     
        
        return words;
    }
    
    public Token[] getTokens() {
        return this.tokens;
    }
    
    protected Token getTokenAt(int line, int column) {
        for( Token token: tokens) {
            if( token.beginLine == line && token.beginColumn == column ) {
                return token;
            }
        }
        
        return null;
    }
    
    protected Token getCurrentTokenAt(int beginLine, int beginColumn) {
        Token lastToken = null;
        for( Token token: tokens) {
            if( token.beginLine == beginLine && token.beginColumn == beginColumn ) {
                return lastToken;
            }
            lastToken = token;
        }
        
        return null;
    }
    
    protected Token getTokenAtCursor(int line, int column) {
        for( Token token: tokens) {
//        	System.out.println("  >> getTokenAtOrAfterCursor() nextToken = " + token.image);
            if( isWithinToken(line, column, token)) {
                return token;
            }
        }
        return null;
    }
    
    /*
     * Given a cursor position
     */
    protected Token getTokenAtOrAfterCursor(int line, int column) {
//    	System.out.println("  >> getTokenAtOrAfterCursor( " + line + ", " + column);
        Token result = getTokenAtCursor(line, column);
        if( result == null ) {
            // walk through tokens with the given line #
            // Look for 2 tokens that border the column
            for( int i=0; i<tokens.length; i++) {
                Token token = tokens[i];
                if( i == tokens.length-1) return token;
                
                Token nextToken = tokens[i+1];
                if( (line >= token.beginLine && line <= token.endLine) &&
                        (line >= nextToken.beginLine && line <= nextToken.endLine)) {
                    // Check that column is after token.endColumn
                    if( column > token.endColumn && column < nextToken.beginColumn) {
                        return nextToken;
                    }
                }
            }
        } else return result;

        return null;
    }
    
    protected Token getTokenAtOrBeforeCursor(int line, int column) {
//    	System.out.println("  >> getTokenAtOrBeforeCursor( " + line + ", " + column);
        Token result = getTokenAtCursor(line, column);
        if( result == null ) {
            // walk through tokens with the given line #
            // Look for 2 tokens that border the column
            for( int i=0; i<tokens.length-1; i++) {
                Token token = tokens[i];
                
                Token nextToken = tokens[i+1];
                if( (line >= token.beginLine && line <= token.endLine) &&
                        (line >= nextToken.beginLine && line <= nextToken.endLine)) {
                    // Check that column is after token.endColumn
                    if( column > token.endColumn && column < nextToken.beginColumn) {
                        return token;
                    }
                }
            }
        } else return result;

        return null;
    }
    
    protected boolean isWithinToken(int line, int column, Token token) {
        return line >= token.beginLine && line <= token.endLine &&
                column >= token.beginColumn && column <= token.endColumn;
    }
    
    protected String[] getDatatypesList() {
    	return DATATYPE_LIST;
    }
    
    public String[] getNextWordsByKind(int kind) {
    	return getNextWordsByKind(kind, false);
    }
    
    public String[] getNextWordsByKind(int kind, boolean isStatementId) {
        List<String> words = new ArrayList<String>();
        
        switch (kind) {
            case CREATE:
                words.add(tokenImage[VIEW].toUpperCase());
                words.add(tokenImage[VIRTUAL].toUpperCase());
                words.add(tokenImage[GLOBAL].toUpperCase());
                words.add(tokenImage[FOREIGN].toUpperCase());
                words.add(tokenImage[TABLE].toUpperCase());
                words.add(tokenImage[TRIGGER].toUpperCase());
                words.add(tokenImage[TEMPORARY].toUpperCase());
                words.add(tokenImage[ROLE].toUpperCase());
                words.add(tokenImage[SCHEMA].toUpperCase());
                words.add(tokenImage[SERVER].toUpperCase());
                words.add(tokenImage[DATABASE].toUpperCase());
                words.add(tokenImage[PROCEDURE].toUpperCase());
            break;
            
            case GLOBAL:
                words.add(tokenImage[TEMPORARY].toUpperCase());
            break;
            
            case TEMPORARY:
                words.add(tokenImage[TABLE].toUpperCase());
            break;
            
            case FOREIGN:
                words.add(tokenImage[TABLE].toUpperCase());
                words.add(tokenImage[TEMPORARY].toUpperCase());
            break;
            
            case VIRTUAL:
                words.add(tokenImage[VIEW].toUpperCase());
                words.add(tokenImage[PROCEDURE].toUpperCase());
            break;
            
            case ID:
            	if( isStatementId ) {
            		words.add(tokenImage[LPAREN]);
            	}
            break;
            
            case SELECT:
                words.add(tokenImage[STAR]);
                break;

            default:
        }
        
        return stringListToArray(words);
    }
    
    
    public void processStatement() {
        processStatement(getStatementType());
    }
 
    
    public DdlTokenExceptionInfo processStatement(STATEMENT_TYPE statementType) {
        DdlTokenExceptionInfo tokenExceptionInfo = new DdlTokenExceptionInfo();
        
        switch( statementType ) {

            case CREATE_VIEW_TYPE:
                tokenExceptionInfo.addException(checkAllParens());
                
                // Check and validate view name
                // token[2] == NAME
//                String msg = VALIDATOR.checkValidName(tokens[2].image);
//                if( msg != null ) {
//                    System.out.println("  ERROR with VIEW NAME " + tokens[2].image + " :  " + msg);
//                }
                // Check for matching brackets
                // token[3] == '('
                // find next ')' and process a partial token array
                if( tokens[3].kind != LPAREN ) {
                    System.out.println("  ERROR with CREATE VIEW unexpected token after name: " + tokens[3].image); 
                }
                try {
                    Token[] tableBodyTokens = getBracketedTokens(tokens, 3, LPAREN, RPAREN);
                    
                    System.out.println(" >>  Process table body....");
                    printTokens(tableBodyTokens, " CREATE VIEW table body tokens... ");
                    
                    // Process table body (i.e. columns definition)
                    List<Token[]> columnDefs =  processTableBodyTokens(tableBodyTokens);
                    
                    for(Token[] tkns: columnDefs ) {
                        System.out.println(" >>  Column Definition");
                        for (Token token : tkns) {
                            System.out.println("  -   " + token.image
                                    + "\t  kind = " + token.kind + "@ ( " + 
                                    token.beginLine + ", " + token.beginColumn + ")");
                        }
                    }
                    
                } catch (DdlAnalyzerException e) {
                    e.printStackTrace();
                }
                    // Process table body (i.e. columns definition)
                
            break;
            
            case CREATE_VIRTUAL_VIEW_TYPE:
                tokenExceptionInfo.addException(checkAllParens());
                
                // Check and validate view name
                // token[3] == NAME
//                msg = VALIDATOR.checkValidName(tokens[3].image);
//                if( msg != null ) {
//                    System.out.println("  ERROR with VIRTUAL VIEW NAME " + tokens[3].image + " :  " + msg);
//                }
                // Check for matching brackets
                // token[4] == '('
                // find next ')' and process a partial token array
                try {
                    Token[] tableBodyTokens = getBracketedTokens(tokens, 4, LPAREN, RPAREN);
                    
//                    System.out.println(" >>  Process table body....");
//                  printTokens(tableBodyTokens, " CREATE VIEW table body tokens... ");                   
                    // Process table body (i.e. columns definition)
                    /* List<Token[]> columnDefs = */ processTableBodyTokens(tableBodyTokens);
                    
//                    for(Token[] tkns: columnDefs ) {
//                        System.out.println(" >>  Column Definition");
//                        for (Token token : tkns) {
//                            System.out.println("    -   " + token.image
//                                    + "\t @ ( " + 
//                                    token.beginLine + ", " + token.beginColumn + ")");
//                        }
//                    }

                } catch (DdlAnalyzerException e) {
                    e.printStackTrace();
                }
                    
            break;

            case CREATE_TABLE_TYPE:
            break;
            
            case CREATE_GLOBAL_TEMPORARY_TABLE_TYPE:
            break;
            
            case CREATE_FOREIGN_TABLE_TYPE:
                tokenExceptionInfo.addException(checkAllParens());
                
                // Check and validate Foreign Table name
                // token[3] == NAME
//                msg = VALIDATOR.checkValidName(tokens[3].image);
//                if( msg != null ) {
//                    System.out.println("  ERROR with FOREIGN TABLE NAME " + tokens[3].image + " :  " + msg);
//                }
                // Check for matching brackets
                // token[4] == '('
                // find next ')' and process a partial token array
                try {
                    Token[] tableBodyTokens = getBracketedTokens(tokens, 4, LPAREN, RPAREN);
//                    printTokens(tableBodyTokens, " CREATE FOREIGN TABLE table body tokens... ");

                    // Process table body (i.e. columns definition)
                    /* List<Token[]> columnDefs = */ processTableBodyTokens(tableBodyTokens);
                    
//                    for(Token[] tkns: columnDefs ) {
//                        System.out.println(" >>  Column Definition");
//                        for (Token token : tkns) {
//                            System.out.println("    -   " + token.image
//                                    + "\t @ ( " + 
//                                    token.beginLine + ", " + token.beginColumn + ")");
//                        }
//                    }

                } catch (DdlAnalyzerException e) {
                    e.printStackTrace();
                }
            break;
            
            case CREATE_FOREIGN_TEMPORARY_TABLE_TYPE:
            break;
            
            case UNKNOWN_STATEMENT_TYPE:
            default:
        }
        
        return tokenExceptionInfo;
    }
    
    private String[] stringListToArray(List<String> array) {
        return array.toArray(new String[0]);
    }
    
    public STATEMENT_TYPE getStatementType() {
        // walk through start of token[] array and return the type
        
        if( isStatementType(tokens, CREATE_VIRTUAL_VIEW_STATEMENT) ) {
//            System.out.println("DdlTokenAnalyzer::  Statement Type = CREATE VIRTUAL VIEW...."); //$NON-NLS-1$
            return STATEMENT_TYPE.CREATE_VIRTUAL_VIEW_TYPE;
        }
        
        if( isStatementType(tokens, CREATE_VIEW_STATEMENT) ) {
//            System.out.println("DdlTokenAnalyzer:  Statement Type = CREATE VIEW...."); //$NON-NLS-1$
            return STATEMENT_TYPE.CREATE_VIEW_TYPE;
        }
        
        if( isStatementType(tokens, CREATE_GLOBAL_TEMPORARY_TABLE_STATEMENT) ) {
//            System.out.println("DdlTokenAnalyzer:  Statement Type = CREATE GLOBAL TEMPORARY TABLE...."); //$NON-NLS-1$
            return STATEMENT_TYPE.CREATE_GLOBAL_TEMPORARY_TABLE_TYPE;
        }
        
        if( isStatementType(tokens, CREATE_FOREIGN_TEMPORARY_TABLE_STATEMENT) ) {
//            System.out.println("DdlTokenAnalyzer:  Statement Type = CREATE FOREIGN TEMPORARY TABLE...."); //$NON-NLS-1$
            return STATEMENT_TYPE.CREATE_FOREIGN_TEMPORARY_TABLE_TYPE;
        }
        
        if( isStatementType(tokens, CREATE_FOREIGN_TABLE_STATEMENT) ) {
//            System.out.println("DdlTokenAnalyzer:  Statement Type = CREATE FOREIGN TABLE...."); //$NON-NLS-1$
            return STATEMENT_TYPE.CREATE_FOREIGN_TABLE_TYPE;
        }
        
        if( isStatementType(tokens, CREATE_TABLE_STATEMENT) ) {
//            System.out.println("DdlTokenAnalyzer:  Statement Type = CREATE TABLE...."); //$NON-NLS-1$
            return STATEMENT_TYPE.CREATE_TABLE_TYPE;
        }

//        System.out.println("DdlTokenAnalyzer:  Statement Type = UNKNOWN ...."); //$NON-NLS-1$
        return STATEMENT_TYPE.UNKNOWN_STATEMENT_TYPE;
    }
    
    private boolean isStatementType(Token[] tkns, int[] statementTokens) {
        int iTkn = 0;
        for(int kind : statementTokens ) {
            // Check each token for kind
            if( tkns[iTkn].kind == kind) {
                if( ++iTkn == statementTokens.length) return true;
                continue;
            };
            break;
        }
        return false;
    }
    
    public boolean allParensMatch(Token[] tkns) {
        return parensMatch(tkns, 0);
    }
    
    
    
    public DdlAnalyzerException checkAllParens() {
    	return checkAllBrackets(LPAREN, RPAREN);
    }
    
    public DdlAnalyzerException checkAllBrackets(int leftBracketKind, int rightBracketKind) {
        int numUnmatchedParens = 0;
        DdlAnalyzerException exception = null;
        
        for(int iTkn= 0; iTkn<tokens.length; iTkn++) {
            Token token = tokens[iTkn];
            if( token.kind == leftBracketKind) numUnmatchedParens++;
            if( token.kind == rightBracketKind) numUnmatchedParens--;
            
            // If the ## goes < 0 throw exception because they should be correctly nested
            //  VALID:  (  () () )
            //  INVALID (  )) () (
            //              ^ would occur here
            if( exception == null && numUnmatchedParens < 0 ) {
                exception = new DdlAnalyzerException("Bracket at location " //$NON-NLS-1$ 
                        + getPositionString(token) + " does not properly match previous bracket"); //$NON-NLS-1$ 
            }
        }
        return exception;
    }
    
    private String getPositionString(Token tkn) {
        return "( " + tkn.beginLine + ", " + tkn.beginColumn + " )"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }
    
    public boolean bracketsMatch(Token[] tkns, int startTokenId, int leftBracket, int rightBracket) {
        int numUnmatchedParens = 0;
        
        for(int iTkn= 0; iTkn<tokens.length; iTkn++) {
            if( iTkn < startTokenId) continue;
            
            Token token = tkns[iTkn];
            if( token.kind == leftBracket) numUnmatchedParens++;
            if( token.kind == rightBracket) numUnmatchedParens--;
        }
        return numUnmatchedParens == 0;
    }
    
    public boolean parensMatch(Token[] tkns, int startTokenId) {
    	return bracketsMatch(tkns, startTokenId, LPAREN, RPAREN);
    }
    
    public Token[] getBracketedTokens(Token[] tkns, int startTokenId, int bracketStart, int bracketEnd) throws DdlAnalyzerException {
        int numUnmatchedParens = 0;
        
        for(int iTkn = 0; iTkn<tokens.length; iTkn++) {
            if( iTkn < startTokenId) continue;
            Token token = tkns[iTkn];
            if( token.kind == bracketStart) numUnmatchedParens++;
            if( token.kind == bracketEnd) numUnmatchedParens--;
            
            if( numUnmatchedParens == 0) {
                List<Token> bracketedTokens = new ArrayList<Token>(tkns.length);
                for(int jTkn = startTokenId+1; jTkn < iTkn; jTkn++) {
                    bracketedTokens.add(tkns[jTkn]);
                }
                return bracketedTokens.toArray(new Token[0]);
            }
        }
        
        Token startTkn = tkns[startTokenId];
        throw new DdlAnalyzerException(
                "Brackets do not match for bracket type '" + startTkn.image +
                "' at position (" + startTkn.beginLine + ", " + startTkn.beginColumn + ")");
    }
    
    
    private List<Token[]> getTableBodyElements() throws DdlAnalyzerException {
    	List<Token[]> tableBodyElements = null;
    	// Check brackets
    	DdlAnalyzerException result = checkAllBrackets(LPAREN, RPAREN);
    	if( result == null ) {
    		int startTkn = getStatementStartLength(statementType);
    		Token[] tableBodyTokens = getBracketedTokens(getTokens(), startTkn, LPAREN, LPAREN);
    		tableBodyElements = processTableBodyTokens(tableBodyTokens);
    	}
    	
    	return tableBodyElements;
    }
    
    /**
     * This method returns an array list of token arrays representing column definitions
     * 
     *   Example:  e1 integer primary key, e2 varchar(10) unique, e3 date not null unique" +
     * 
     * @param tkns
     * @return
     */
    public List<Token[]> processTableBodyTokens(Token[] tkns) throws DdlAnalyzerException{
        List<Token[]> tknTknList = new ArrayList<Token[]>();
        
        List<Token> columnTkns = null;
        
        for(int iTkn = 0; iTkn<tkns.length; iTkn++) {
            Token tkn = tkns[iTkn];
            
            if( columnTkns == null ) columnTkns = new ArrayList<Token>();
            if( tkn.kind != COMMA) {
                if( isDatatype(tkn) ) {
                    columnTkns.add(tkn); // the datatype
                    // process table body (column) OPTIONS() tokens
                    Token[] datatypeTokens = getBracketedTokens(tkns, iTkn+1, LPAREN, RPAREN);
                    
                    if( datatypeTokens.length > 0 ) {
                        iTkn++;
                        columnTkns.add(tkns[iTkn]); // '('

                        for(Token optTkn: datatypeTokens) {
                            columnTkns.add(optTkn);
                        }
                        iTkn = iTkn + datatypeTokens.length;
                        iTkn++;
                        columnTkns.add(tkns[iTkn]); // ')'
                    }
                } else if( tkn.kind == OPTIONS ) {
                    columnTkns.add(tkn); // OPTIONS
                    
                    // process table body (column) OPTIONS() tokens
                    Token[] optionsTokens = getBracketedTokens(tkns, iTkn+1, LPAREN, RPAREN);
                    iTkn++;
                    columnTkns.add(tkns[iTkn]); // '('

                    for(Token optTkn: optionsTokens) {
                        columnTkns.add(optTkn);
                    }
                    iTkn = iTkn + optionsTokens.length;
                    iTkn++;
                    columnTkns.add(tkns[iTkn]); // ')'
                } else {
                    columnTkns.add(tkn);
                }
            } else {
                if( columnTkns.isEmpty() ) throw new DdlAnalyzerException("Error in column definition");
                
                // found comma, so need to reset tkn list and add
                tknTknList.add(columnTkns.toArray(new Token[0]));
                columnTkns = null;
            }
        }
        
        if( columnTkns != null && !columnTkns.isEmpty() ) 
            tknTknList.add(columnTkns.toArray(new Token[0]));
        
        return tknTknList;
    }
    
    public boolean isDatatype(Token token) {
        for( int dType: DATATYPES) {
            if( token.kind == dType) return true;
        }
        return false;
    }
    
    /*
     * if there is 
     */
    public Token[] processDatatypeTokens(Token datatypeTkn, Token[] bodyTokens) {
        List<Token> tknTknList = new ArrayList<Token>();
        switch( datatypeTkn.kind ) {
            // LENGTH DATATYPES
            case CHAR:
            case CLOB:
            case JSON:
            case BLOB:
            case OBJECT:
            case XML:
            case STRING:
            case VARBINARY:
            case BIGINTEGER:
            case VARCHAR:
                // These should all have bodyTokens.length == 1
                if( bodyTokens.length > 1 ) {
                    tknTknList.add(bodyTokens[0]);
                }
//                body
            break;
            
            case DECIMAL:
            break;
        }
        return tknTknList.toArray(new Token[0]);
    }
    

    public int getStatementStartLength(STATEMENT_TYPE type) {
    	switch(type) {
	    	case CREATE_VIEW_TYPE:
	    	case CREATE_TABLE_TYPE:
	    		return 2;
	    	case CREATE_FOREIGN_TABLE_TYPE:
	    	case CREATE_VIRTUAL_VIEW_TYPE:
	    		return 3;
	    	case CREATE_GLOBAL_TEMPORARY_TABLE_TYPE:
	    	case CREATE_FOREIGN_TEMPORARY_TABLE_TYPE:
	    		return 4;
	    	case UNKNOWN_STATEMENT_TYPE:
	    	default:
    	}
		return 0;
    }
    
    private void printTokens(Token[] tkns, String headerMessage) {
        System.out.println(headerMessage);
        for (Token token : tkns) {
            System.out.println("  >> Token = " + token.image
                    + " Position ( " + 
                    token.beginLine + ", " + token.beginColumn + ")");
        }
    }
    
    /*
     * An ID (token Kind == 414) can occur a in a number of positions within a DDL statement
     * 
     * First and foremost, the statement "name" (i.e View, Table or Procedure Name) will occur
     * after the statement start tokens
     */
    public boolean isTokenStatementNameId(Token namedToken) {
    	STATEMENT_TYPE type = getStatementType();
    	int tokenId = getTokenPlacement(namedToken);
    	if( type == STATEMENT_TYPE.CREATE_VIEW_TYPE && tokenId == 2) {
    		return true;
    	} else if( (type == STATEMENT_TYPE.CREATE_FOREIGN_TABLE_TYPE ||
    			type == STATEMENT_TYPE.CREATE_VIRTUAL_VIEW_TYPE) && tokenId == 3 ) {
    		return true;
    	} else if( (type == STATEMENT_TYPE.CREATE_FOREIGN_TEMPORARY_TABLE_TYPE ||
    			type == STATEMENT_TYPE.CREATE_GLOBAL_TEMPORARY_TABLE_TYPE) && tokenId == 4 ) {
    		return true;
    	}
    	
    	return false;
    }
    
    private int getTokenPlacement(Token token) {
    	int id = 0;
    	for( Token tkn: this.tokens ) {
    		if( token == tkn) {
    			return id;
    		}
    		id++;
    	}
    	
    	return -1;
    }
}
