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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.CompletionItemKind;
import org.eclipse.lsp4j.InsertTextFormat;
import org.eclipse.lsp4j.MarkupContent;
import org.eclipse.lsp4j.MarkupKind;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.teiid.query.parser.Token;

import io.syndesis.dv.lsp.parser.DdlParserHelper;

public class DdlCompletionProvider implements DdlCompletionConstants {
	
	public List<CompletionItem> getCompletionItems(String statement, Position position) {
		List<CompletionItem> items = new ArrayList<CompletionItem>();
		
		System.out.println("\n >> TeiidDdlCompletionProvider.getCompletionItems()  Position = " + position);
		try {
			DdlParserHelper helper = new DdlParserHelper(statement);
			Token token = helper.getToken(position);
			
			if( token == null && position.getLine() == 0 && position.getCharacter() == 0 ) {
				String[] theWords = new String[1];
				theWords[0] = tokenImage[CREATE];
				return generateCompletionItems(theWords);
			}
			
			String[] words = null;
			if( token != null ) {
				System.out.print("\n >> TeiidDdlCompletionProvider.getCompletionItems() Found Token = " + token.image);
				
				switch(token.kind) {
					case CREATE:
						words = helper.getAnalyzer().getNextWordsByKind(CREATE);
						System.out.print("\n >>> Token = CREATE found " + words.length + " words\n");
						items.addAll(generateCompletionItems(words));
						break;
						
					case VIRTUAL:
						words = helper.getAnalyzer().getNextWordsByKind(VIRTUAL);
						System.out.print("\n >>> Token = VIRTUAL found " + words.length + " words\n");
						items.addAll(generateCompletionItems(words));
						break;
						
					case VIEW:
						words = helper.getAnalyzer().getNextWordsByKind(VIEW);
						System.out.print("\n >>> Token = VIEW found " + words.length + " words\n");
						items.addAll(generateCompletionItems(words));
						break;
						
					case SELECT:
						words = helper.getAnalyzer().getNextWordsByKind(SELECT);
						System.out.print("\n >>> Token = SELECT found " + words.length + " words\n");
						items.addAll(generateCompletionItems(words));
						break;
					case ID:
						// An ID represents any variable representing a view or source name
						// i.e. table name, column name, etc....
						// If an ID is found, then we'll need to identify the context of the ID
						// Basically where in the statement does it exist
						boolean isStatementNameId = helper.getAnalyzer().isTokenStatementNameId(token);
						if( isStatementNameId ) {
							words = helper.getAnalyzer().getNextWordsByKind(ID, isStatementNameId);
							System.out.print("\n >>> Token = SELECT found " + words.length + " words\n");
							items.addAll(generateCompletionItems(words));
							break;
						}
						break;
		
					default:
				}
			} else System.out.print("\n TeiidDdlCompletionProvider.getCompletionItems() DID NOT FIND Token");
		} catch (Exception e) {
			System.out.print("\n TeiidDdlCompletionProvider.getCompletionItems() DID NOT FIND CompltionItems");
		}
		System.out.print("\n CompletionItems = " + items.size() + "\n");
		return items;
	}

    public CompletionItem getColumnCompletionItem(int data) {
        CompletionItem ci = new CompletionItem();
        ci.setLabel("CREATE VIEW...");
        ci.setInsertText(
                "CREATE VIEW ${1:name} (\n) AS SELECT * FROM ${2:name}");
        ci.setKind(CompletionItemKind.Snippet);
        ci.setInsertTextFormat(InsertTextFormat.Snippet);
        ci.setDetail(" Create View statement including ....");
        ci.setDocumentation(beautifyDocument(ci.getInsertText()));
        ci.setData(data);
        ci.setPreselect(true);
        return ci;
    }

    public CompletionItem getCreateViewCompletionItem(int data) {
        CompletionItem ci = new CompletionItem();
        ci.setLabel("column definition");
        ci.setInsertText("\\t${1:name} ${2:type}");
        ci.setKind(CompletionItemKind.Snippet);
        ci.setInsertTextFormat(InsertTextFormat.Snippet);
        ci.setDetail(" insert new column definition ....");
        ci.setDocumentation(beautifyDocument(ci.getInsertText()));
        ci.setData(data);
        ci.setPreselect(true);
        return ci;
    }

    private static Either<String, MarkupContent> beautifyDocument(String raw) {
        // remove the placeholder for the plain cursor like: ${0}, ${1:variable}
        String escapedString = raw.replaceAll("\\$\\{\\d:?(.*?)\\}", "$1");

        MarkupContent markupContent = new MarkupContent();
        markupContent.setKind(MarkupKind.MARKDOWN);
        markupContent.setValue(
                String.format("```%s\n%s\n```", "java", escapedString));
        return Either.forRight(markupContent);
    }

    public CompletionItem createKeywordItem(String label, String text,
            String detail, String documentation) {
        CompletionItem ci = new CompletionItem();
        ci.setLabel(label);
        ci.setKind(CompletionItemKind.Keyword);
        if (detail != null) {
            ci.setDetail(detail);
        }
        if (documentation != null) {
            ci.setDocumentation(documentation);
        }
        return ci;
    }
    
    public CompletionItem createKeywordItem(String label) {
    	String[] itemData = getItemData(label);
        CompletionItem ci = new CompletionItem();
        ci.setLabel(label);
        ci.setKind(CompletionItemKind.Keyword);

        String detail = itemData[1];
        if (detail != null) {
            ci.setDetail(detail);
        }

        String documentation = itemData[2];
        if (documentation != null) {
            ci.setDocumentation(documentation);
        }

        String insertText = itemData[3];
        if (insertText != null) {
            ci.setInsertText(insertText);
        }

        return ci;
    }

    public CompletionItem createFieldItem(String label, String detail,
            String documentation) {
        CompletionItem ci = new CompletionItem();
        ci.setLabel(label);
        ci.setKind(CompletionItemKind.Field);
        if (detail != null) {
            ci.setDetail(detail);
        }
        if (documentation != null) {
            ci.setDocumentation(documentation);
        }
        return ci;
    }

    public CompletionItem createSnippetItem(String label, String detail,
            String documentation, String insertText) {
        CompletionItem ci = new CompletionItem();
        ci.setLabel(label);
        ci.setKind(CompletionItemKind.Snippet);
        ci.setInsertTextFormat(InsertTextFormat.Snippet);
        ci.setInsertText(insertText);
        if (documentation != null) {
            ci.setDocumentation(documentation);
        } else {
            ci.setDocumentation(beautifyDocument(ci.getInsertText()));
        }
        if (detail != null) {
            ci.setDetail(detail);
        }

        return ci;
    }
    
    private List<CompletionItem> generateCompletionItems(String[] words) {
    	List<CompletionItem> items = new ArrayList<CompletionItem>(); 
    	
		for(String word: words ) {
			items.add(createKeywordItem(word, word, null, null));
		}
		
		return items;
    }
    
	/**
	 * 
	 * @param label
	 * @return	String[] array >>>>  
		String[0] label;
		String[1] detail;
		String[2] documentation;
		String[3] insertText;
		String[4] insertTextFormat;
	 */
	public String[] getItemData(String label) {
		String[] result = KEYWORDS_ITEM_DATA.get(label.toUpperCase());
		
		if( result == null ) {
			result = DATATYPES_ITEM_DATA.get(label);
		}
		
		return result;
	}
}
