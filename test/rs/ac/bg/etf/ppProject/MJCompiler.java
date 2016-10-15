package rs.ac.bg.etf.ppProject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import java_cup.runtime.Symbol;
import rs.ac.bg.etf.ppProject.util.Log4JUtils;
import rs.etf.pp1.mj.runtime.Code;

public class MJCompiler {

	static {
		DOMConfigurator.configure(Log4JUtils.instance().findLoggerConfigFile());
		Log4JUtils.instance().prepareLogFile(Logger.getRootLogger());
	}
	
	public static String help(){
		
		String s = "MJCompiler Stefan Pantelic 511/12\n"
				+ "===================================\n"
				+ "-l: run lexer\n"
				+ "First argument: path to the the file you want to compile (default program.mj)\n"
				+ "Second argument: path to log file (default console output)\n"
				+ "-c: compile"
				+ "First argument: path to the the file you want to compile (default program.mj)\n"
				+ "Second argument: path to the output file with .obj extension (default program.obj)\n"
				+ "Third argument: path to log file (default console output)\n"
				+ "if you want to replace any of arguments with default values type -d instead";
		
		return s;
	}
	
	
	public static void main(String[] args) throws Exception {
	
		Logger log = Logger.getLogger(MJCompiler.class);
		String source, destination, outlog;
		
		if(args.length == 1){		
			if(args[0].equals("-h")){
				System.out.println(help());
				System.exit(0);
			}
				
			else{
				System.out.println("wrong input , type -h for help");
				System.exit(0);
			}
		}
		else if(args.length == 3){
			source = args[1];
			outlog = args[2];
			
			if(args[0].equals("-l")){
				if(source.equals("-d"))
					source = "program.mj";
				if(outlog.equals("-d"))
					outlog = "";
				
				MJLexerPartTest lex = new MJLexerPartTest();
				
				lex.getLexerOutput(outlog, source);
				
			}
			else{
				System.out.println("wrong input , type -h for help");
				System.exit(0);
			}
			
		}
		else if(args.length == 4){
			source = args[1];
			destination = args[2];
			outlog = args[3];
			
			if(args[0].equals("-c")){
				if(source.equals("-d"))
					source = "program.mj";
				if(destination.equals("-d"))
					destination = "program.obj";
				if(outlog.equals("-d"))
					outlog = "";
				
				MJLexerPartTest lex = new MJLexerPartTest();
				
				lex.getLexerOutput(outlog, source);
				
				MJParserPartTest pars = new MJParserPartTest();
				
				pars.getParserOutput(outlog, source, destination);
				
			}
			else{
				System.out.println("wrong input , type -h for help");
				System.exit(0);
			}
			
		}
		else{
			System.out.println("wrong input , type -h for help");
			System.exit(0);
		}
		
	}
	
	
}
