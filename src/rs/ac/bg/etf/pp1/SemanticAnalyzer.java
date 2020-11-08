package rs.ac.bg.etf.pp1;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Stack;
import org.apache.log4j.Logger;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;
import rs.etf.pp1.symboltable.visitors.DumpSymbolTableVisitor;

public class SemanticAnalyzer extends VisitorAdaptor {
    
    public static final int boolVal = 5;
    public static Struct bool = new Struct(boolVal);
    public static final int AbstractClass = 29;
    
    Logger log = Logger.getLogger(getClass());
    boolean errorDetected = false;
    int nVars;
    boolean mainExists = false;
    DumpSymbolTableVisitor stv;
    int cntrGlobalVar = 0;
    HashMap<String, Integer> cntrLocalVariables = new HashMap<>();
    
    Struct typeStructNode = Tab.noType;
    Obj methodObjNode = Tab.noObj;
    boolean insideClass = false,
            insideAbstractClass = false,
            insideMethod = false,
            insideForLoop = false,
            visitedAbstractMethod = false,
            visitedVoid = false,
            visitedReturn = false,
            visitedEqual = false,
            visitedNotEqual = false,
            visitedConstBool = false,
            visitedConstNumber = false,
            visitedConstChar = false;
    boolean constBool;
    int constNumber;
    char constChar;
    
    enum Operator { AssignmentOperator, CombinedOperator }	
    Stack<Operator> operatorStack = new Stack<>();

    public void report_error(String message, SyntaxNode info) {
        errorDetected = true;
        StringBuilder msg = new StringBuilder(message);
        int line = (info == null) ? 0 : info.getLine();
        if (line != 0)
            msg.append(" on line ").append(line);
        log.error(msg.toString());
    }

    public void report_info(String message, SyntaxNode info) {
        StringBuilder msg = new StringBuilder(message);
        int line = (info == null) ? 0 : info.getLine();
        if (line != 0)
            msg.append(" on line ").append(line);
        log.info(msg.toString());
    }
    
    public void visit(ProgName progName) {
        
        // add bool type to symbol table
        Tab.insert(Obj.Type, "bool", bool);
        
        Obj ret = Tab.currentScope.findSymbol(progName.getProgName());
        if (ret != null)
            report_error("Error on line " + progName.getLine() + ": Program name is not valid.", null);
        
        progName.obj = Tab.insert(Obj.Prog, progName.getProgName(), Tab.noType);
        Tab.openScope();
        
    }

    public void visit(Program program) {

        nVars = Tab.currentScope.getnVars();
        
        if (!mainExists)
            report_error("No main function in a program. ", null);
        
        if (cntrGlobalVar > 65536)
            report_error("More than 65536 global variables declared in this program.", null);
        
        Iterator<Entry<String, Integer>> it = cntrLocalVariables.entrySet().iterator();
        while (it.hasNext()) {
            Entry<String, Integer> pair = (Entry<String,Integer>)it.next();
            if ((int)pair.getValue() > 265)
                report_error("More than 256 local variables declared in global function " + pair.getKey() + ".", null);
            it.remove();
        }
        
        Tab.chainLocalSymbols(program.getProgName().obj);
        Tab.closeScope();
        
    }
    
    public void visit(Type type) {

        Obj typeObjNode = Tab.find(type.getTypeName());

        if (typeObjNode == Tab.noObj) {
            report_error("Error on line " + type.getLine() + ": Type " + type.getTypeName() + " not found in symbol table", null);			
            typeStructNode = Tab.noType;
            type.struct = typeStructNode;
            return;
        } 
        
        if (typeObjNode.getKind() == Obj.Type)
            typeStructNode = typeObjNode.getType();
        else {
            report_error("Error on line " + type.getLine() + ": " + type.getTypeName() + " is not a type", null);
            typeStructNode = Tab.noType;
        }
        type.struct = typeStructNode;

    }
    
    public void visit(ConstNumber num) {
        constNumber = num.getConstNumber();
        visitedConstNumber = true;
    }
    
    public void visit(ConstBool bool) {
        constBool = ((int)bool.getConstBool() == 1) ? true : false;
        visitedConstBool = true;
    }

    public void visit(ConstChar character) {
        constChar = character.getConstChar();
        visitedConstChar = true;
    }
    
    public void visit(ConstListPart constListPart) {

        Obj ret = Tab.currentScope.findSymbol(constListPart.getConstName());
        
        if (ret != null) 
            report_error("Error on line " + constListPart.getLine() + ": Constant with name " + constListPart.getConstName() + " is already declared.", null);

        Obj con = Tab.insert(Obj.Con, constListPart.getConstName(), typeStructNode);

        if (visitedConstBool) {
            
            if (typeStructNode != bool)
                report_error("Error on line " + constListPart.getLine() + ": Type and constant value do not match", null);
            else
                con.setAdr(constBool ? 1 : 0);
            visitedConstBool = false;
            
        } else if (visitedConstNumber) {
            
            if (typeStructNode != Tab.intType)
                report_error("Error on line " + constListPart.getLine() + ": Type and constant value do not match", null);
            else
                con.setAdr(constNumber);
            visitedConstNumber = false;
            
        } else if (visitedConstChar) {
            
            if (typeStructNode != Tab.charType)
                report_error("Error on line " + constListPart.getLine() + ": Type and constant value do not match", null);
            else
                con.setAdr(constChar);
            visitedConstChar = false;
            
        }

        stv = new DumpSymbolTableVisitor();
        stv.visitObjNode(con);
        report_info("Declared constant: " + constListPart.getConstName() + " on line " + constListPart.getLine() + 
                    " => " + stv.getOutput(), null);
        
    }
    
    public void visit(ConstDecl constDecl) {
        typeStructNode = Tab.noType;
    }
    
    public void visit(Var var) {

        Obj ret = Tab.currentScope.findSymbol(var.getVarName());
        
        if (ret != null)
            report_error("Error on line " + var.getLine() + ": " + var.getVarName() + " already declared", null);
        
        Obj varDecl = Tab.noObj;
        stv = new DumpSymbolTableVisitor();
        
        if (!insideClass && !insideMethod) { // global variable
            
            varDecl = Tab.insert(Obj.Var, var.getVarName(), typeStructNode);
            cntrGlobalVar++;
            stv.visitObjNode(varDecl);
            report_info("Global variable " + var.getVarName() + " declared on line " + var.getLine() 
                        + " => " + stv.getOutput(), null);
            
        } else if (insideClass && !insideMethod) { // class field
            
            varDecl = Tab.insert(Obj.Fld, var.getVarName(), typeStructNode);
            stv.visitObjNode(varDecl);
            
        } else { // local variable inside method or global function
            
            cntrLocalVariables.put(methodObjNode.getName(), cntrLocalVariables.get(methodObjNode.getName()) + 1);
            
            varDecl = Tab.insert(Obj.Var, var.getVarName(), typeStructNode);
            stv.visitObjNode(varDecl);
            
        }
        
    }
    
    public void visit(VarArray varArray) {
        
        Obj ret = Tab.currentScope.findSymbol(varArray.getArrayName());

        if (ret != null)
            report_error("Error on line " + varArray.getLine() + ": " + varArray.getArrayName() + " already declared", null);
        
        Obj varDecl = Tab.noObj;
        stv = new DumpSymbolTableVisitor();
        
        Struct typeArrayStructNode = new Struct(Struct.Array, typeStructNode);
        
        if (!insideClass && !insideMethod) { // global variable
            
            varDecl = Tab.insert(Obj.Var, varArray.getArrayName(), typeArrayStructNode);
            cntrGlobalVar++;
            stv.visitObjNode(varDecl);
            report_info("Global array " + varArray.getArrayName() + " declared on line " + varArray.getLine() 
                        + " => " + stv.getOutput(), null);
            
        } else if (insideClass && !insideMethod) { // class field
            
            varDecl = Tab.insert(Obj.Fld, varArray.getArrayName(), typeArrayStructNode);
            stv.visitObjNode(varDecl);
            
        } else { // local variable inside method or global function
            
            cntrLocalVariables.put(methodObjNode.getName(), cntrLocalVariables.get(methodObjNode.getName())+1);
            
            varDecl = Tab.insert(Obj.Var, varArray.getArrayName(), typeArrayStructNode);
            stv.visitObjNode(varDecl);
            
        }
        
    }
    
    public void visit(VarDeclaration varDeclaration) {
        typeStructNode = Tab.noType;
    }
    
    public void visit(DesignatorNotElement designatorNotElement) {
        
        Obj ret = Tab.find(designatorNotElement.getName());
        
        if (ret == Tab.noObj)
            report_error("Error on line " + designatorNotElement.getLine() + ": Designator with name " + designatorNotElement.getName() + " is not declared", null);
        
        if (ret.getKind() == Obj.Type || ret.getKind() == Obj.Prog)
            report_error("Error on line " + designatorNotElement.getLine() + ": Type or program name is used as designator.", null);
        
        designatorNotElement.obj = ret;
    
    }
    
    public void visit(DesignatorArr designatorArr) {
        designatorArr.obj = Tab.find(designatorArr.getName());
    }
    
    public void visit(DesignatorElement designatorElement) {
        
        Obj ret = designatorElement.getDesignatorArr().obj;
        
        if (ret == Tab.noObj) {
            
            report_error("Error on line " + designatorElement.getLine() + ": Designator with name " + ret.getName() + " is not declared" ,null);
            designatorElement.obj = new Obj(Obj.Elem, "", Tab.noType);
        
        } else if (ret.getType().getKind() != Struct.Array) {
            
            report_error("Error on line " + designatorElement.getLine() + ": " + ret.getName() + " is used like an array", null);
            designatorElement.obj = new Obj(Obj.Elem, "", Tab.noType);
            
            if (designatorElement.getExpr().obj.getType() != Tab.intType)
                report_error("Error on line " + designatorElement.getLine() + ": index of an array should be a number" ,null);
        
        } else if (designatorElement.getExpr().obj.getType() != Tab.intType) {
            
            report_error("Error on line " + designatorElement.getLine() + ": index of an array should be a number", null);
            designatorElement.obj = new Obj(Obj.Elem, "", Tab.noType);
            
        } else {
                        
            designatorElement.obj = new Obj(Obj.Elem, "", ret.getType().getElemType(), ret.getAdr(), ret.getLevel());
            stv = new DumpSymbolTableVisitor();
            stv.visitObjNode(ret);
            report_info("Line " + designatorElement.getLine() + ": detected access to array " + ret.getName() + " => " + stv.getOutput(), null);

        }
    
    }
    
    public void visit(DesignatorPars designatorPars) {
        if (designatorPars.getDesignator().obj.getKind() != Obj.Meth)
            report_error("Error on line " + designatorPars.getLine() + ": " + designatorPars.getDesignator().obj.getName() + " is not a function", null);
    }
    
    public void visit(FactorNumber factorNumber) {
        factorNumber.obj = new Obj(Obj.Con, "", Tab.intType, factorNumber.getFactorNumber(), Obj.NO_VALUE);
    }
    
    public void visit(FactorCharacter factorCharacter) {
        factorCharacter.obj = new Obj(Obj.Con, "", Tab.charType, factorCharacter.getFactorCharacter(), Obj.NO_VALUE);
    }
    
    public void visit(FactorBoolean factorBoolean) {
        factorBoolean.obj = new Obj(Obj.Con, "", bool, factorBoolean.getFactorBoolean(), Obj.NO_VALUE);
    }
    
    public void visit(FactorNewObject factorNewObject) {
        
        Obj obj = Tab.find(factorNewObject.getType().getTypeName());
        
        if (obj.getKind() == Obj.Type && obj.getType().getKind() == Struct.Class && obj.getAdr() == AbstractClass) {
            report_error("Error on line " + factorNewObject.getLine() + ": You can't instantiate abstract class" ,null);
            factorNewObject.obj = new Obj(Obj.Con, "", Tab.noType);
        } else if (obj.getType() == Tab.intType || obj.getType() == Tab.charType || obj.getType() == bool) {
            report_error("Error on line " + factorNewObject.getLine() + ": You can't allocate object with type of int, char or boolean" ,null);
            factorNewObject.obj = new Obj(Obj.Con, "", Tab.noType);
        } else
            factorNewObject.obj = new Obj(Obj.Con, "", factorNewObject.getType().struct);
    
    }
    
    public void visit(FactorNewArray factorNewArray) {
        
        Struct type = factorNewArray.getType().struct;
        
        if (type == Tab.intType || type == Tab.charType || type == bool)
            factorNewArray.obj = new Obj(Obj.Con, "", new Struct(Struct.Array, type));
        else {
            report_error("Error on line " + factorNewArray.getLine() + ": Only array of int, char or boolean supported", null);
            factorNewArray.obj = new Obj(Obj.Con, "", new Struct(Struct.Array, Tab.noType));
        }
        
        if (factorNewArray.getExpr().obj.getType() != Tab.intType)
            report_error("Error on line " + factorNewArray.getLine() + ": expression inside [] should be a number" ,null);
    
    }
    
    public void visit(FactorExpression factorExpression) {
        factorExpression.obj = new Obj(Obj.Con, "", factorExpression.getExpr().obj.getType());
    }
    
    public void visit(FactorDesignatorFunction factorDesignatorFunction) {
        
        if (factorDesignatorFunction.getDesignator().obj.getKind() == Obj.Meth) {
            
            if (factorDesignatorFunction.getDesignator().obj.getType() != Tab.noType)
                factorDesignatorFunction.obj = new Obj(Obj.Con, "", factorDesignatorFunction.getDesignator().obj.getType());
            else {
                factorDesignatorFunction.obj = new Obj(Obj.Con, "", Tab.noType);
                report_error("Error on line " + factorDesignatorFunction.getLine() + ": function " + factorDesignatorFunction.getDesignator().obj.getName() + " returns void" ,null);
            }
        
        } else {
            report_error("Error on line " + factorDesignatorFunction.getLine() + ": " + factorDesignatorFunction.getDesignator().obj.getName() + " is not a function" ,null);
            factorDesignatorFunction.obj = new Obj(Obj.Con, "", Tab.noType);
        }
    
    }
    
    public void visit(FactorDesignator factorDesignator) {	
        if (factorDesignator.getDesignator().obj.getKind() != Obj.Var && 
            factorDesignator.getDesignator().obj.getKind() != Obj.Con &&
            factorDesignator.getDesignator().obj.getKind() != Obj.Elem
        )
            report_error("Error on line " + factorDesignator.getLine() + ": Unknown constant, variable or array element", null);
        factorDesignator.obj = factorDesignator.getDesignator().obj;
    }
    
    public void visit(TermMember termMember) {
        termMember.obj = termMember.getFactor().obj;
    }
    
    public void visit(TermRecursive termRecursive) {
        
        Obj term = termRecursive.getTerm().obj;
        Obj factor = termRecursive.getFactor().obj;
        
        if(term.getType() == Tab.intType && factor.getType() == Tab.intType)
            termRecursive.obj = new Obj(Obj.Con, "", Tab.intType);
        else {
            report_error("Error on line " + termRecursive.getLine() + ": Use *, / or % only with numbers", null);
            termRecursive.obj = new Obj(Obj.Con, "", Tab.noType);
        }
    
    }
    
    public void visit(NoMinusFirstTerm noMinusFirstTerm) {
        noMinusFirstTerm.obj = noMinusFirstTerm.getTerm().obj;
    }
    
    public void visit(MinusFirstTerm minusFirstTerm) {
        if (minusFirstTerm.getTerm().obj.getType() != Tab.intType)
            report_error("Error on line " + minusFirstTerm.getLine() + ": Only number (int) could follow (first) minus sign (-)" , null);
        minusFirstTerm.obj = new Obj(Obj.Con, "", minusFirstTerm.getTerm().obj.getType());
    }
    
    public void visit(TermListMember termListMember) {
        termListMember.obj = termListMember.getFirstTerm().obj;
    }
    
    public void visit(TermListRecursive termListRecursive) {
        
        Obj expr = termListRecursive.getTermList().obj;
        Obj term = termListRecursive.getTerm().obj;
        
        if(expr.getType() == Tab.intType && term.getType() == Tab.intType)
            termListRecursive.obj = new Obj(Obj.Con, "", Tab.intType);
        else {
            report_error("Error on line " + termListRecursive.getLine() + ": Use + or - only with numbers", null);
            termListRecursive.obj = new Obj(Obj.Con, "", Tab.noType);
        }
    
    }
    
    public void visit(TermListExtMember termListExtMember) {
        termListExtMember.obj = termListExtMember.getTermList().obj;
    }
    
    public void visit(TermListExtRecursive termListExtRecursive) {
    
        Obj termList = termListExtRecursive.getTermList().obj;
        Obj termListExt = termListExtRecursive.getTermListExt().obj;
        
        if (termList.getKind() != Obj.Var && termList.getKind() != Obj.Elem)
            report_error("Error on line " + termListExtRecursive.getLine() + ": On left side of (combined) assignment operator should be variable or array element", null);
        else {
            
            Operator op = operatorStack.pop();
            
            if (op == Operator.AssignmentOperator && !(termListExt.getType().assignableTo(termList.getType()))) 
                report_error("Error on line " + termListExtRecursive.getLine() + ": Incompatible types in assignment" , null);
            else if (op == Operator.CombinedOperator && (termList.getType() != Tab.intType || termListExt.getType() != Tab.intType)) 
                report_error("Error on line " + termListExtRecursive.getLine() + ": On both sides of combined operator should be number (int)" , null);
            
        }
        
        termListExtRecursive.obj = termList;
        
    }
    
    public void visit(Expr expr) {
        expr.obj = expr.getTermListExt().obj;
    }
    
    public void visit(DesignatorAssignExpression designatorAssignExpression) {
        
        Obj designator = designatorAssignExpression.getDesignator().obj;
        Obj termListExt = designatorAssignExpression.getExpr().obj;
        
        if (designator.getKind() != Obj.Var && designator.getKind() != Obj.Elem)
            report_error("Error on line " + designatorAssignExpression.getLine() + ": On left side of (combined) assignment operator should be variable or array element", null);
        else {
            Operator op = operatorStack.pop();
            if (op == Operator.AssignmentOperator && !(termListExt.getType().assignableTo(designator.getType())))
                report_error("Error on line " + designatorAssignExpression.getLine() + ": Incompatible types in assignment", null);  
            else if (op == Operator.CombinedOperator && (designator.getType() != Tab.intType || termListExt.getType() != Tab.intType))
                report_error("Error on line " + designatorAssignExpression.getLine() + ": On both sides of combined operator should be number (int type)", null);
        }
        
    }
    
    public void visit(DesignatorInc designatorInc) {
        
        Obj design = designatorInc.getDesignator().obj;
        
        if (design.getKind() != Obj.Var && design.getKind() != Obj.Elem)
            report_error("Error on line " + designatorInc.getLine() + ": On left side of ++ should be variable or element of array only", null);
        
        if (design.getType() != Tab.intType)
            report_error("Error on line " + designatorInc.getLine() + ": On left side of ++ should be variable or element of array only (int type)", null);
    
    }
    
    public void visit(DesignatorDec designatorDec) {
        
        Obj design = designatorDec.getDesignator().obj;
        
        if (design.getKind() != Obj.Var && design.getKind() != Obj.Elem)
            report_error("Error on line " + designatorDec.getLine() + ": On left side of -- should be variable or element of array only", null);
        
        if (design.getType() != Tab.intType)
            report_error("Error on line " + designatorDec.getLine() + ": On left side of -- should be variable or element of array only (int type)", null);
    
    }
    
    public void visit(ReadStatement readStatement) {
        
        Obj design = readStatement.getDesignator().obj;
        
        if (design.getKind() != Obj.Var && design.getKind() != Obj.Elem)
            report_error("Error on line " + readStatement.getLine() + ": In read statement, inside () should be variable or element of array only", null);
        
        if (design.getType() != bool && design.getType() != Tab.intType && design.getType() != Tab.charType)
            report_error("Error on line " + readStatement.getLine() + ": In read statement, inside () should be a boolean, number or char type (variable or element of array only)" , null);
    
    }
    
    public void visit(PrintStatement printStatement) {
        
        Struct type = printStatement.getExpr().obj.getType();
        
        if (type != bool && type != Tab.intType && type != Tab.charType)
            report_error("Error on line " + printStatement.getLine() + ": In print statement, inside () should be a boolean, number or char" , null);
    
    }
    
    public void visit(Assignment assignment) { operatorStack.push(Operator.AssignmentOperator); }
    public void visit(AssignmentAdd assignmentAdd) { operatorStack.push(Operator.CombinedOperator); }
    public void visit(AssignmentMul assignmentMul) { operatorStack.push(Operator.CombinedOperator); }
    
    public boolean passed() {
        return !errorDetected;
    }
    
    public void visit(MethodDeclName methodDeclName) {
        
        Obj ret = Tab.currentScope.findSymbol(methodDeclName.getMethName());
        if (ret != null)
            report_error("Error on line " + methodDeclName.getLine() + ": Method (function) named " + methodDeclName.getMethName() + " is already defined.", null);
        
        cntrLocalVariables.put(methodDeclName.getMethName(), 0);
        insideMethod = true;
        
        if (methodDeclName.getMethName().equals("main")) {

            mainExists = true;
            if (visitedVoid) {
                methodObjNode = Tab.insert(Obj.Meth, methodDeclName.getMethName(), Tab.noType);
                methodDeclName.obj = methodObjNode;
                visitedVoid = false;
            } else
                report_error("Error on line " + methodDeclName.getLine() + ": Main method returns void" ,null);

        } else {
            methodObjNode = Tab.insert(Obj.Meth, methodDeclName.getMethName(), typeStructNode);
            methodDeclName.obj = methodObjNode;
        }
        
        Tab.openScope();
        
    }
    
    public void visit(MethodDecl methodDecl) {

        if (!visitedReturn && (methodObjNode.getType() != Tab.noType))
            report_error("Error on line " + methodDecl.getLine()
                    + ": Method (function) defined on line is missing return statement", null);

        Tab.chainLocalSymbols(methodObjNode);
        Tab.closeScope();

        insideMethod = false;

        visitedReturn = false;
        methodObjNode = Tab.noObj;
        typeStructNode = Tab.noType;

    }
    
    public void visit(ReturnVoid returnVoid) {
        visitedVoid = true;
        typeStructNode = Tab.noType;
    }
    
    public void visit(ReturnNonVoid returnNonVoid) {
        visitedVoid = false;
        typeStructNode = returnNonVoid.getType().struct;
    }
    
    public void visit(FormalParamVar formalParamVar) {
            
        if (methodObjNode.getName().equals("main")) {
            report_error("Error on line " + formalParamVar.getLine() + ": Main method doesn't have arguments" ,null);
            return;
        }
            
        if (Tab.currentScope.findSymbol(formalParamVar.getVarName()) != null)
            report_error("Error on line " + formalParamVar.getLine() + ": Declared parameter already exists" ,null);

        Tab.insert(Obj.Var, formalParamVar.getVarName(), typeStructNode);

    }
    
    public void visit(FormalParamArr formalParamArr) {
            
        if (methodObjNode.getName().equals("main")) {
            report_error("Error on line " + formalParamArr.getLine() + ": Main method does not have arguments" ,null);
            return;
        }
        
        if (Tab.currentScope.findSymbol(formalParamArr.getArrName()) != null)
            report_error("Error on line " + formalParamArr.getLine() + ": Declared parameter already exists" ,null);

        Tab.insert(Obj.Var, formalParamArr.getArrName(), typeStructNode);
    
    }
    
    public void visit(ReturnStatementExpr returnStatementExpr) {

        if (!insideMethod)
            report_error("Error on line " + returnStatementExpr.getLine()
                    + ": Return statement is not inside a method (function)", null);

        visitedReturn = true;

        if (!(methodObjNode.getType().compatibleWith(returnStatementExpr.getExpr().obj.getType())))
            report_error("Error on line " + returnStatementExpr.getLine()
                    + ": Method type and returned value are not compatible", null);

    }

    public void visit(ReturnStatementNoExpr returnStatementNoExpr) {

        if (!insideMethod)
            report_error("Error on line " + returnStatementNoExpr.getLine()
                    + ": Return statement is not inside a method (function)", null);

        visitedReturn = true;

        if (methodObjNode.getType() != Tab.noType)
            report_error("Error on line " + returnStatementNoExpr.getLine()
                    + ": Return statement with no expression should be inside VOID method (function)", null);

    }
    
    public void visit(ForLoopEmpty forLoopEmpty) {
        insideForLoop = true;
    }
    
    public void visit(ForLoop forLoop) {
        insideForLoop = false;
    }
    
    public void visit(BreakStatement breakStatement) {
        if (!insideForLoop)
            report_error("Error on line " + breakStatement.getLine()
                + ": Break statement is not inside a for loop", null);
    }
    
    public void visit(ContinueStatement continueStatement) {
        if (!insideForLoop)
            report_error("Error on line " + continueStatement.getLine()
                + ": Continue statement is not inside a for loop", null);
    }
    
    public void visit(Equal equal) {
        visitedEqual = true;
    }
    
    public void visit(NotEqual notEqual) {
        visitedNotEqual = true;
    }
    
    public void visit(CondFactNoRelationalExpression condFactNoRelationalExpression) {
        condFactNoRelationalExpression.obj = condFactNoRelationalExpression.getExpr().obj;
    }
    
    public void visit(CondFactRelationalExpression condFactRelationalExpression) {
        
        if (!condFactRelationalExpression.getExpr().obj.getType()
            .compatibleWith(condFactRelationalExpression.getExpr1().obj.getType())) {
                report_error("Error on line " + condFactRelationalExpression.getLine()
                + ": Expressions on both sides of relational operator should be compatible", null);
        }
        
        if ( 
            ((condFactRelationalExpression.getExpr().obj.getType().getKind() == Struct.Class &&
             condFactRelationalExpression.getExpr1().obj.getType().getKind() == Struct.Class) ||
            (condFactRelationalExpression.getExpr().obj.getType().getKind() == Struct.Array &&
             condFactRelationalExpression.getExpr1().obj.getType().getKind() == Struct.Array)) &&
            (!visitedEqual && !visitedNotEqual)
        )
            report_error("Error on line " + condFactRelationalExpression.getLine()
            + ": Relational operators != or == should be used with objects or arrays only", null);
        
        condFactRelationalExpression.obj = new Obj(Obj.Con, "", SemanticAnalyzer.bool);
        
        visitedEqual = false;
        visitedNotEqual = false;
        
    }
    
    public void visit(CondFactorsMember condFactorsMember) {
        condFactorsMember.obj = condFactorsMember.getCondFact().obj;
    }
    
    public void visit(CondFactors condFactors) {
        
        Obj term = condFactors.getCondTerm().obj;
        Obj fact = condFactors.getCondFact().obj;
        
        if(term.getType() == SemanticAnalyzer.bool && fact.getType() == SemanticAnalyzer.bool)
            condFactors.obj = new Obj(Obj.Con, "", SemanticAnalyzer.bool);
        else {
            report_error("Error on line " + condFactors.getLine() + ": Logical operator AND should be used only with boolean type", null);
            condFactors.obj = new Obj(Obj.Con, "", Tab.noType);
        }
        
    }
    
    public void visit(CondTermsMember condTermsMember) {
        condTermsMember.obj = condTermsMember.getCondTerm().obj;
    }
    
    public void visit(CondTerms condTerms) {
        
        Obj condTermList = condTerms.getCondTermList().obj;
        Obj condTerm = condTerms.getCondTerm().obj;
        
        if(condTermList.getType() == SemanticAnalyzer.bool && condTerm.getType() == SemanticAnalyzer.bool)
            condTerms.obj = new Obj(Obj.Con, "", SemanticAnalyzer.bool);
        else {
            report_error("Error on line " + condTerms.getLine() + ": Logical operator OR should be used only with boolean type", null);
            condTerms.obj = new Obj(Obj.Con, "", Tab.noType);
        }
        
    }
    
    public void visit(Condition condition) {
        condition.obj = condition.getCondTermList().obj;
    }
    
    public void visit(IfStatement ifStatement) {
        
        if (ifStatement.getCondition().obj.getType() != SemanticAnalyzer.bool)
            report_error("Error on line " + ifStatement.getLine() + ": Condition inside if statement should be boolean type", null);
            
    }
    
    public void visit(ForCondition forCondition) {
        
        if (forCondition.getCondition().obj.getType() != SemanticAnalyzer.bool)
            report_error("Error on line " + forCondition.getLine() + ": Condition inside for loop should be boolean type", null);
        
    }
    
    public void visit(ClassName className) {

        insideClass = true;
        Obj ret = Tab.currentScope().findSymbol(className.getClassName());

        if (ret != null) 
            report_error("Error on line " + className.getLine() + ": " + className.getClassName() + " is already defined", null);
        else 
            className.obj = Tab.insert(Obj.Type, className.getClassName(), new Struct(Struct.Class));

        Tab.openScope();

    }
    
    public void visit(AbstractClassName abstractClassName) {
        
        insideAbstractClass = true;
        Obj ret = Tab.currentScope().findSymbol(abstractClassName.getAbsClassName());
        
        if (ret != null) 
            report_error("Error on line " + abstractClassName.getLine() + ": " + abstractClassName.getAbsClassName() + " is already defined", null);
        else {
            abstractClassName.obj = Tab.insert(Obj.Type, abstractClassName.getAbsClassName(), new Struct(Struct.Class));
            abstractClassName.obj.setAdr(AbstractClass);
        }

        Tab.openScope();

    }
    
    public void visit(Extend extend) {
        
        Obj base = Tab.find(extend.getType().getTypeName());
        
        if (base == Tab.noObj)
            report_error("Error on line " + extend.getLine() + ": Base class" + extend.getType().getTypeName() + " is not defined", null);
        
        if (base.getKind() != Obj.Type || base.getType().getKind() != Struct.Class)
            report_error("Error on line " + extend.getLine() + ": " + extend.getType().getTypeName() + " is not name of the inner class in this program.", null);
    
    }
    
    public void visit(ClassDecl classDecl) {
        
        insideClass = false;
        
        if (visitedAbstractMethod) {
            report_error("Error on line " + classDecl.getLine() + ": Abstract method is defined in non-abstract class " + classDecl.getClassName().getClassName(), null);
            visitedAbstractMethod = false;
        }
        
        Tab.chainLocalSymbols(classDecl.getClassName().obj);
        Tab.closeScope();
        
    }
    
    public void visit(AbstractClassDecl abstractClassDecl) {
        
        insideAbstractClass = false;
        visitedAbstractMethod = false;
        
        Tab.chainLocalSymbols(abstractClassDecl.getAbstractClassName().obj);
        Tab.closeScope();
        
    }
    
    
}
