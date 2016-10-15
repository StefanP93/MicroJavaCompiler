package rs.ac.bg.etf.ppProject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import java_cup.runtime.Symbol;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import rs.ac.bg.etf.ppProject.util.Log4JUtils;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;


public class MJParserTest {

	static {
		DOMConfigurator.configure(Log4JUtils.instance().findLoggerConfigFile());
		Log4JUtils.instance().prepareLogFile(Logger.getRootLogger());
	}
	
	public static void main(String[] args) throws Exception {
		
		Logger log = Logger.getLogger(MJParserTest.class);
		
		Reader br = null;
		try {
			File sourceCode = new File("test/program.mj");
			log.info("Compiling source file: " + sourceCode.getAbsolutePath());
			
			br = new BufferedReader(new FileReader(sourceCode));
			Yylex lexer = new Yylex(br);
			
			MJParser p = new MJParser(lexer);
	        Symbol s = p.parse();  //pocetak parsiranja
	        log.info("=====================BROJACI=========================");
	        
	        log.info("Broj Globalnih promenjljivi:" + p.globalVCnt);
	        log.info("Broj Lokalnih promenjljivi:" + p.localVCnt);
	        log.info("Broj Globalnih konstanti:" + p.globalConst);
	        log.info("Broj Globalnih nizova:" + p.globalArray);
	        log.info("Broj Deklaracija funkcija:" + p.functionCnt);
	        log.info("Broj Blokova naredbi:" + p.blockCnt);
	        log.info("Broj Poziva funkcija u main metodi:" + p.functionCallCnt);
	        log.info("Broj Deklaracija formalnih argumenata:" + p.formParamCnt);
	        log.info("Broj Definicija unutrasnjih klasa:" + p.classCnt);
	        log.info("Broj Definicija klasnih metoda:" + p.classMethCnt);
	        log.info("Broj Definicija klasnih polja:" + p.classFieldCnt);
	        
	        p.dump();
	        
		        
		       
		    if(!p.errorFound){
		    	File objFile = new File("test/program.obj");
		    	if (objFile.exists())
		    		objFile.delete();
		    	Code.write(new FileOutputStream(objFile));
		     }
		     else
		        System.err.println("Parsiranje neuspesno");
	        
		} 
		finally {
			if (br != null) try { br.close(); } catch (IOException e1) { log.error(e1.getMessage(), e1); }
		}

	}
	
	
}
