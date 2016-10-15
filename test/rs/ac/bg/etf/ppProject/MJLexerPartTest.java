package rs.ac.bg.etf.ppProject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import java_cup.runtime.Symbol;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import rs.ac.bg.etf.ppProject.util.Log4JUtils;

public class MJLexerPartTest {

	static {
		DOMConfigurator.configure(Log4JUtils.instance().findLoggerConfigFile());
		Log4JUtils.instance().prepareLogFile(Logger.getRootLogger());
	}
	
	public void getLexerOutput(String output,String source) throws IOException {
		Logger log = Logger.getLogger(MJLexerPartTest.class);
		Reader br = null;
		Writer wr = null;
		try {
			
			
			File sourceCode = new File(source);	
			log.info("Compiling source file: " + sourceCode.getAbsolutePath());
			
			br = new BufferedReader(new FileReader(sourceCode));
			
			Yylex lexer = new Yylex(br);
			Symbol currToken = null;
			
			if(output.equals("")){
				while ((currToken = lexer.next_token()).sym != sym.EOF) {
					if (currToken != null)
						log.info(currToken.toString() + " " + currToken.value.toString());
				}				
			}
			else{
				File outputFile = new File(output);
				wr = new BufferedWriter(new FileWriter(outputFile));
				while ((currToken = lexer.next_token()).sym != sym.EOF) {
					if (currToken != null)
						wr.write(currToken.toString() + " " + currToken.value.toString());
				}
				
				wr.close();
				
			}
		} 
		finally {
			if (br != null) try { br.close(); } catch (IOException e1) { e1.printStackTrace(); }
		}
	}
	
}
