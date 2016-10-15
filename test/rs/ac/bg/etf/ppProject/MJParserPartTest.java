package rs.ac.bg.etf.ppProject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.io.Writer;

import java_cup.runtime.Symbol;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import rs.ac.bg.etf.ppProject.util.Log4JUtils;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;


public class MJParserPartTest {

	static {
		DOMConfigurator.configure(Log4JUtils.instance().findLoggerConfigFile());
		Log4JUtils.instance().prepareLogFile(Logger.getRootLogger());
	}
	
	public void getParserOutput (String outlog, String source , String destination) throws Exception {
		
		Logger log = Logger.getLogger(MJParserPartTest.class);
		Reader br = null;
		Writer wr = null;
		try {
			File sourceCode = new File(source);	
			log.info("Compiling source file: " + sourceCode.getAbsolutePath());
			
			br = new BufferedReader(new FileReader(sourceCode));
			
			if(!outlog.equals("")){
				String outlogErr = "";
				for(int i=0;i<outlog.length()-4;i++)
					outlogErr+=outlog.charAt(i);
				outlogErr+="Err.err";
				
				
				System.setOut(new PrintStream(new BufferedOutputStream(new FileOutputStream(outlog)),true));
				System.setErr(new PrintStream(new BufferedOutputStream(new FileOutputStream(outlogErr)),true));
				
			}
			Yylex lexer = new Yylex(br);
			Symbol currToken = null;
			MJParser p = new MJParser(lexer);
			
	        Symbol s = p.parse();  //pocetak parsiranja
	        
	        System.out.println("\n=====================BROJACI=========================");
	        
	        System.out.println("Broj Globalnih promenjljivi:" + p.globalVCnt);
	        System.out.println("Broj Lokalnih promenjljivi:" + p.localVCnt);
	        System.out.println("Broj Globalnih konstanti:" + p.globalConst);
	        System.out.println("Broj Globalnih nizova:" + p.globalArray);
	        System.out.println("Broj Deklaracija funkcija:" + p.functionCnt);
	        System.out.println("Broj Blokova naredbi:" + p.blockCnt);
	        System.out.println("Broj Poziva funkcija u main metodi:" + p.functionCallCnt);
	        System.out.println("Broj Deklaracija formalnih argumenata:" + p.formParamCnt);
	        System.out.println("Broj Definicija unutrasnjih klasa:" + p.classCnt);
	        System.out.println("Broj Definicija klasnih metoda:" + p.classMethCnt);
	        System.out.println("Broj Definicija klasnih polja:" + p.classFieldCnt);
	        
	        p.dump();
	        
	        if(!p.errorFound){
	        File objFile = new File(destination);
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
