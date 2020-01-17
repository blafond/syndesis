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

import org.teiid.query.parser.SQLParserConstants;

public interface DdlAnalyzerConstants extends SQLParserConstants {

    enum STATEMENT_TYPE {
        UNKNOWN_STATEMENT_TYPE,
        CREATE_TABLE_TYPE,
        CREATE_FOREIGN_TABLE_TYPE,
        CREATE_FOREIGN_TEMPORARY_TABLE_TYPE,
        CREATE_GLOBAL_TEMPORARY_TABLE_TYPE,
        CREATE_VIEW_TYPE,
        CREATE_VIRTUAL_VIEW_TYPE
    }
    
    int[] DATATYPES = {
        STRING,
        VARBINARY,
        VARCHAR,
        BOOLEAN,
        BYTE,
        TINYINT,
        SHORT,
        SMALLINT,
        CHAR,
        INTEGER,
        LONG,
        BIGINT,
        BIGINTEGER,
        FLOAT,
        REAL,
        DOUBLE,
        BIGDECIMAL,
        DECIMAL,
        DATE,
        TIME,
        TIMESTAMP,
        BLOB,
        CLOB,
        XML,
        JSON,
        GEOMETRY,
        GEOGRAPHY,
        OBJECT
    };
    
    String[] DATATYPE_LIST = {
    	tokenImage[STRING].toUpperCase(),
    	tokenImage[VARBINARY].toUpperCase(),
    	tokenImage[VARCHAR].toUpperCase(),
    	tokenImage[BOOLEAN].toUpperCase(),
    	tokenImage[BYTE].toUpperCase(),
    	tokenImage[TINYINT].toUpperCase(),
    	tokenImage[SHORT].toUpperCase(),
    	tokenImage[SMALLINT].toUpperCase(),
    	tokenImage[CHAR].toUpperCase(),
    	tokenImage[INTEGER].toUpperCase(),
    	tokenImage[LONG].toUpperCase(),
    	tokenImage[BIGINT].toUpperCase(),
    	tokenImage[BIGINTEGER].toUpperCase(),
    	tokenImage[FLOAT].toUpperCase(),
    	tokenImage[REAL].toUpperCase(),
    	tokenImage[DOUBLE].toUpperCase(),
    	tokenImage[BIGDECIMAL].toUpperCase(),
    	tokenImage[DECIMAL].toUpperCase(),
    	tokenImage[DATE].toUpperCase(),
    	tokenImage[TIME].toUpperCase(),
    	tokenImage[TIMESTAMP].toUpperCase(),
    	tokenImage[BLOB].toUpperCase(),
    	tokenImage[CLOB].toUpperCase(),
    	tokenImage[XML].toUpperCase(),
    	tokenImage[JSON].toUpperCase(),
    	tokenImage[GEOMETRY].toUpperCase(),
    	tokenImage[GEOGRAPHY].toUpperCase(),
    	tokenImage[OBJECT].toUpperCase()
    };
    
    /*
     * Array of tokens that match the start of a CREATE TABLE statement
     */
    int[] CREATE_TABLE_STATEMENT = { CREATE, TABLE };

    /*
     * Array of tokens that match the start of a CREATE FOREIGNT TABLE statement
     */
    int[] CREATE_FOREIGN_TABLE_STATEMENT = { CREATE, FOREIGN, TABLE };
    
    /*
     * Array of tokens that match the start of a CREATE FOREIGN TEMPORARY TABLE
     * statement
     */
    int[] CREATE_FOREIGN_TEMPORARY_TABLE_STATEMENT = { CREATE, FOREIGN, TEMPORARY, TABLE };

    /*
     * Array of tokens that match the start of a CREATE TEMPORARY TABLE
     * statement
     */
    int[] CREATE_GLOBAL_TEMPORARY_TABLE_STATEMENT = { CREATE, GLOBAL, TEMPORARY, TABLE };
    
    /*
     * Array of tokens that match the start of a CREATE VIEW
     * statement
     */
    int[] CREATE_VIEW_STATEMENT = { CREATE, VIEW };
    
    /*
     * Array of tokens that match the start of a CREATE VIRTUAL VIEW
     * statement
     */
    int[] CREATE_VIRTUAL_VIEW_STATEMENT = { CREATE, VIRTUAL, VIEW };

}
