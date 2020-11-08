package rs.ac.bg.etf.pp1;

import java_cup.runtime.Symbol;


%%


%{
    // include token position information
    private Symbol new_symbol(int type) {
        return new Symbol(type, yyline+1, yycolumn);
    }
    
    // include token position information
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


" "                             { }
"\b"                            { }
"\t"                            { }
"\r\n"                          { }
"\f"                            { }
"\n"                            { }

"program"                       { return new_symbol(sym.PROG, yytext()); }
"break"                         { return new_symbol(sym.BREAK, yytext()); }
"class"                         { return new_symbol(sym.CLASS, yytext()); }
"abstract"                      { return new_symbol(sym.ABSTRACT, yytext()); }
"else"                          { return new_symbol(sym.ELSE, yytext()); }
"const"                         { return new_symbol(sym.CONST, yytext()); }
"if"                            { return new_symbol(sym.IF, yytext()); }
"new"                           { return new_symbol(sym.NEW, yytext()); }
"print"                         { return new_symbol(sym.PRINT, yytext()); }
"read"                          { return new_symbol(sym.READ, yytext()); }
"return"                        { return new_symbol(sym.RETURN, yytext()); }
"void"                          { return new_symbol(sym.VOID, yytext()); }
"for"                           { return new_symbol(sym.FOR, yytext()); }
"extends"                       { return new_symbol(sym.EXTENDS, yytext()); }
"continue"                      { return new_symbol(sym.CONTINUE, yytext()); }

"+"                             { return new_symbol(sym.PLUS, yytext()); }
"-"                             { return new_symbol(sym.MINUS, yytext()); }
"*"                             { return new_symbol(sym.MUL, yytext()); }
"/"                             { return new_symbol(sym.DIV, yytext()); }
"%"                             { return new_symbol(sym.MOD, yytext()); }
"++"                            { return new_symbol(sym.INC, yytext()); }
"--"                            { return new_symbol(sym.DEC, yytext()); }
"="                             { return new_symbol(sym.ASSIGN, yytext()); }
"+="                            { return new_symbol(sym.PLUS_ASSIGN, yytext()); }
"-="                            { return new_symbol(sym.MINUS_ASSIGN, yytext()); }
"*="                            { return new_symbol(sym.MUL_ASSIGN, yytext()); }
"/="                            { return new_symbol(sym.DIV_ASSIGN, yytext()); }
"%="                            { return new_symbol(sym.MOD_ASSIGN, yytext()); }

"||"                            { return new_symbol(sym.OR, yytext()); }
"&&"                            { return new_symbol(sym.AND, yytext()); }

"=="                            { return new_symbol(sym.EQUAL, yytext()); }
"!="                            { return new_symbol(sym.NOT_EQUAL, yytext()); }
"<"                             { return new_symbol(sym.LESS, yytext()); }
"<="                            { return new_symbol(sym.LESSE, yytext()); }
">"                             { return new_symbol(sym.GRT, yytext()); }
">="                            { return new_symbol(sym.GRTE, yytext()); }

";"                             { return new_symbol(sym.SEMI, yytext()); }
","                             { return new_symbol(sym.COMMA, yytext()); }
"."                             { return new_symbol(sym.DOT, yytext()); }
"("                             { return new_symbol(sym.LPAREN, yytext()); }
")"                             { return new_symbol(sym.RPAREN, yytext()); }
"{"                             { return new_symbol(sym.LBRACE, yytext()); }
"}"                             { return new_symbol(sym.RBRACE, yytext()); }
"["                             { return new_symbol(sym.LBOXBR, yytext()); }
"]"                             { return new_symbol(sym.RBOXBR, yytext()); }

"//"                            { yybegin(COMMENT); }
<COMMENT> .                     { yybegin(COMMENT); }
<COMMENT> "\r\n"                { yybegin(YYINITIAL); }

"true" | "false"                { return new_symbol(sym.BOOL, new Integer (yytext().equals("true") ? 1 : 0)); }
[0-9]+                          { return new_symbol(sym.NUMBER, new Integer(yytext())); }
([a-z]|[A-Z])[a-z|A-Z|0-9|_]*   { return new_symbol(sym.IDENT, yytext()); }
"'"."'"                         { return new_symbol(sym.CHAR, new Character (yytext().charAt(1))); }

.                               { System.err.println("Lexical error \"" + yytext() + "\". Line: " + (yyline+1) + ", Column: " + (yycolumn+1) + "."); }