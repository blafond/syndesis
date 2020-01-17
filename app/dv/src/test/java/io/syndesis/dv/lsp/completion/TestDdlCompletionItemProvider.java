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

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.Position;
import org.junit.Test;

import io.syndesis.dv.lsp.parser.DdlAnalyzerConstants;

@SuppressWarnings("nls")
public class TestDdlCompletionItemProvider implements DdlAnalyzerConstants {

	private DdlCompletionProvider itemProcessor = new DdlCompletionProvider();
	
	
    @Test
    public void testCreateViewCompletions() throws Exception {
        System.out.println(" TEST:  testViewName()");
        
        //             01234567890123456789
        String stmt = "CREATE VIEW winelist (e1 integer primary key";
        
        // CREATE 0, 0, expect 1 items (i.e. CREATE )
        List<CompletionItem> items = itemProcessor.getCompletionItems(stmt, new Position(0, 0));
        assertEquals(12, items.size());
        
        items = itemProcessor.getCompletionItems(stmt, new Position(0, 4));
        assertEquals(12, items.size());
        
        items = itemProcessor.getCompletionItems(stmt, new Position(0, 4));
        assertEquals(12, items.size());
        
        // CREATE 0, 5, expect 12 items
        items = itemProcessor.getCompletionItems(stmt, new Position(0, 5));
        assertEquals(12, items.size());
        
        
        // CREATE 0, 6, expect 12 items
        items = itemProcessor.getCompletionItems(stmt, new Position(0, 6));
        assertEquals(12, items.size());
        
        // VIEW 0, 10, expect 1 items
        items = itemProcessor.getCompletionItems(stmt, new Position(0, 10));
        assertEquals(0, items.size());
        
        // VIEW 0, 11, expect 1 items
        items = itemProcessor.getCompletionItems(stmt, new Position(0, 11));
        assertEquals(0, items.size());
        
        // VIEW 0, 12, expect 0 items
        items = itemProcessor.getCompletionItems(stmt, new Position(0, 12));
        assertEquals(1, items.size());
        assertEquals(tokenImage[LPAREN], items.get(0).getLabel());
    }
    
    @Test
    public void testCreateVirtualViewCompletions() throws Exception {
        System.out.println(" TEST:  testViewName()");
        //             0123456789012345678901234567890
        String stmt = "CREATE VIRTUAL VIEW myView (e1 integer primary key)";
        
        // VIRTUAL 0, 12, expect 2 items
        List<CompletionItem> items = itemProcessor.getCompletionItems(stmt, new Position(0, 12));
        assertEquals(2, items.size());
        assertEquals(tokenImage[VIEW].toUpperCase(), items.get(0).getLabel());
        assertEquals(tokenImage[PROCEDURE].toUpperCase(), items.get(1).getLabel());
        
        // VIRTUAL 0, 14, expect 2 items
        items = itemProcessor.getCompletionItems(stmt, new Position(0, 14));
        assertEquals(2, items.size());
        assertEquals(tokenImage[VIEW].toUpperCase(), items.get(0).getLabel());
        assertEquals(tokenImage[PROCEDURE].toUpperCase(), items.get(1).getLabel());
        
        // VIEW 0, 15, expect 0 items
        items = itemProcessor.getCompletionItems(stmt, new Position(0, 15));
        assertEquals(0, items.size());
        
        // VIEW 0, 23, expect 0 items
        items = itemProcessor.getCompletionItems(stmt, new Position(0, 23));
        assertEquals(1, items.size());
        assertEquals(tokenImage[LPAREN], items.get(0).getLabel());
        
        // VIEW 0, 26, expect 0 items
        items = itemProcessor.getCompletionItems(stmt, new Position(0, 26));
        assertEquals(1, items.size());
        assertEquals(tokenImage[LPAREN], items.get(0).getLabel());
    }
    

}
