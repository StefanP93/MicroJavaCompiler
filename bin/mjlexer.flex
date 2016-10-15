package rs.ac.bg.etf.ppProject;

import java_cup.runtime.Symbol;

%%

%{

	// ukljucivanje informacije o poziciji tokena
	private Symbol new_symbol(int type) {
		return new Symbol(type, yyline+1, yycolumn);
	}
	
	// ukljucivanje informacije o poziciji tokena
	private Symbol new_symbol(int type, Object value) {
		return new Symbol(type, yyline+1, yycolumn, value);
	}

%}

%cup
%line
%column

%xstate COMMENT

%eofval{
	return new_symbol(sym.EOF);
%eofval}

%%

" " 	{ }
"\b" 	{ }
"\t" 	{ }
"\r\n" 	{ }
"\f" 	{ }

"program"   { return new_symbol(sym.PROG, yytext()); }
"print" 	{ return new_symbol(sym.PRINT, yytext()); }
"return" 	{ return new_symbol(sym.RETURN, yytext()); }
"void" 		{ return new_symbol(sym.VOID, yytext()); }
"break"   { return new_symbol(sym.BREAK, yytext()); }
"class" 	{ return new_symbol(sym.CLASS, yytext()); }
"else" 	{ return new_symbol(sym.ELSE, yytext()); }
"if" 		{ return new_symbol(sym.IF, yytext()); }
"new"   { return new_symbol(sym.NEW, yytext()); }
"read" 	{ return new_symbol(sym.READ, yytext()); }
"while" 	{ return new_symbol(sym.WHILE, yytext()); }
"extends" 		{ return new_symbol(sym.EXTENDS, yytext()); }
"const" 		{ return new_symbol(sym.CONST, yytext()); }

"+" 		{ return new_symbol(sym.PLUS, yytext()); }
"-" 		{ return new_symbol(sym.MINUS, yytext()); }
"=" 		{ return new_symbol(sym.EQUAL, yytext()); }
"*" 		{ return new_symbol(sym.MUL, yytext()); }
"/" 		{ return new_symbol(sym.DIV, yytext()); }
"%" 		{ return new_symbol(sym.PERCENTAGE, yytext()); }
"=="        { return new_symbol(sym.IS_EQUAL, yytext()); }
"!="        { return new_symbol(sym.IS_DIFFERENT, yytext()); }
">"         { return new_symbol(sym.IS_GREATER, yytext()); }
">="        { return new_symbol(sym.IS_GREATER_EQUAL, yytext()); }
"<"         { return new_symbol(sym.IS_LESS, yytext()); }
"<="        { return new_symbol(sym.IS_LESS_EQUAL, yytext()); }
"&&"        { return new_symbol(sym.LOGICAL_AND, yytext()); }
"||"        { return new_symbol(sym.LOGICAL_OR, yytext()); }
"++"        { return new_symbol(sym.INCREMENT, yytext()); }
"--"        { return new_symbol(sym.DECREMENT, yytext()); }
";" 		{ return new_symbol(sym.SEMI, yytext()); }
"," 		{ return new_symbol(sym.COMMA, yytext()); }
"." 		{ return new_symbol(sym.DOT, yytext()); }
"(" 		{ return new_symbol(sym.LPAREN, yytext()); }
")" 		{ return new_symbol(sym.RPAREN, yytext()); }
"{" 		{ return new_symbol(sym.LBRACE, yytext()); }
"}"			{ return new_symbol(sym.RBRACE, yytext()); }
"[" 		{ return new_symbol(sym.LINDEX, yytext()); }
"]"			{ return new_symbol(sym.RINDEX, yytext()); }

"true"      { return new_symbol(sym.BOOL_CONST, new Boolean (yytext())); }
"false"     { return new_symbol(sym.BOOL_CONST, new Boolean (yytext())); }

"//" 		     { yybegin(COMMENT); }
<COMMENT> .      { yybegin(COMMENT); }
<COMMENT> "\r\n" { yybegin(YYINITIAL); }

[0-9]+  { return new_symbol(sym.NUMBER, new Integer (yytext())); }
([a-z]|[A-Z])[a-z|A-Z|0-9|_]* 	{return new_symbol (sym.IDENT, yytext()); }
"\'"[\040-\176]"\'"   { return new_symbol(sym.CHAR_CONST, new Character (yytext().charAt(1))); }
"\""[\040-\176]*"\""   { return new_symbol(sym.STR_CONST, yytext().substring(1,yytext().length()-1)); }

. { System.err.println("Leksicka greska ("+yytext()+") na liniji "+(yyline+1)); }






