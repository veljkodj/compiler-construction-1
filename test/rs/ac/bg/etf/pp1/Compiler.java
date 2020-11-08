package rs.ac.bg.etf.pp1;

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

import rs.ac.bg.etf.pp1.ast.Program;
import rs.ac.bg.etf.pp1.util.Log4JUtils;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class Compiler {

    static { 
        DOMConfigurator.configure(Log4JUtils.instance().findLoggerConfigFile());
        Log4JUtils.instance().prepareLogFile(Logger.getRootLogger());
    }

    public static void main(String[] args) throws Exception {
        
        if (args.length != 1) {
            System.out.println("Please enter name of a input MicroJava file stored in test folder (program.mj for example)");
            return;
        }
        
        Logger log = Logger.getLogger(Compiler.class);

        Reader br = null;
        
        try {
            
            File sourceCode = new File("test/" + args[0]);
            log.info("Compiling source file: " + sourceCode.getAbsolutePath());

            br = new BufferedReader(new FileReader(sourceCode));
            Yylex lexer = new Yylex(br);

            MJParser p = new MJParser(lexer);

            // syntax analysis (parsing)
            Symbol s = p.parse();

            Program prog = (Program) (s.value);

            // print syntax tree
            log.info(prog.toString(""));

            // semantic analysis
            Tab.init();
             
            SemanticAnalyzer v = new SemanticAnalyzer();
            prog.traverseBottomUp(v);

            Tab.dump();

            if (!p.errorDetected && v.passed()) {
                
                log.info("Semantic analysis passed!");
                
                File objFile = new File("test/" + args[0].substring(0, args[0].indexOf(".mj")) + ".obj");
                if (objFile.exists()) objFile.delete();
                
                CodeGenerator codeGenerator = new CodeGenerator();
                prog.traverseBottomUp(codeGenerator);
                
                Code.dataSize = v.nVars;
                Code.mainPc = codeGenerator.getMainPC();
                Code.write(new FileOutputStream(objFile));
                
            } else 
                log.error("Semantic analysis failed!");

        } finally {
            if (br != null)
                try {
                    br.close();
                } catch (IOException e1) {
                    log.error(e1.getMessage(), e1);
                }
        }
        
    }

}
