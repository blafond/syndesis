package io.syndesis.dv.lsp.diagnostics;

import java.util.ArrayList;
import java.util.List;

import javax.swing.text.BadLocationException;

import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.lsp4j.PublishDiagnosticsParams;
import org.eclipse.lsp4j.TextDocumentItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.syndesis.dv.lsp.TeiidDdlLanguageServer;
import io.syndesis.dv.lsp.parser.DdlAnalyzerException;
import io.syndesis.dv.lsp.parser.DdlTokenAnalyzer;
import io.syndesis.dv.lsp.parser.statement.CreateViewStatement;


public class DdlDiagnostics {
    private static final Logger LOGGER = LoggerFactory.getLogger(DdlDiagnostics.class);


    public DdlDiagnostics() {
    }
    
    public void clearDiagnostics(TeiidDdlLanguageServer languageServer) {
        languageServer.getClient().publishDiagnostics(new PublishDiagnosticsParams("someURI", new ArrayList<Diagnostic>(0)));
    }

    public void publishDiagnostics(TextDocumentItem ddlDocument, TeiidDdlLanguageServer languageServer) {
//        LOGGER.info( ">>>>>  publishDiagnostics()  CLEARING DIAGNOSTICS IN CLIENT");
        languageServer.getClient().publishDiagnostics(new PublishDiagnosticsParams(ddlDocument.getUri(), new ArrayList<Diagnostic>(0)));
        
        List<Diagnostic> diagnostics = new ArrayList<Diagnostic>();

            try {
//                LOGGER.info( ">>>>>  publishDiagnostics()  calling doBasicDiagnostics");
                
                doBasicDiagnostics(ddlDocument, diagnostics);

//                LOGGER.info( ">>>>>  publishDiagnostics()  SENDING DIAGNOSTICS TO CLIENT");
                languageServer.getClient().publishDiagnostics(new PublishDiagnosticsParams(ddlDocument.getUri(), diagnostics));
                
            } catch (BadLocationException e) {
//                LOGGER.error("BadLocationException thrown doing doBasicDiagnostics() in DdlDiagnostics.", e);;
            }
    }

    /**
     * Do basic validation to check the no XML valid.
     * 
     * @param ddlDocument
     * @param diagnostics
     * @param monitor
     * @throws BadLocationException
     */
    private void doBasicDiagnostics(TextDocumentItem ddlDocument, List<Diagnostic> diagnostics)
            throws BadLocationException {
        /*
         * Scanner scanner = XMLScanner.createScanner(document.getText()); TokenType
         * token = scanner.scan(); while (token != TokenType.EOS) {
         * monitor.checkCanceled(); // TODO check tokens... token = scanner.scan(); }
         */
        
        DdlTokenAnalyzer analyzer = new DdlTokenAnalyzer(ddlDocument.getText());

        CreateViewStatement createStatement = new CreateViewStatement(analyzer);
        
        for(DdlAnalyzerException exception : createStatement.getExceptions()) {
            diagnostics.add(exception.getDiagnostic());
            LOGGER.info(diagnostics.toString());
        }
    }
}