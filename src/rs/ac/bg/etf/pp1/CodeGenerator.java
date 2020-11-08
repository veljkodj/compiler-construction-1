package rs.ac.bg.etf.pp1;

import java.util.Stack;
import rs.ac.bg.etf.pp1.CounterVisitor.*;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;
import rs.etf.pp1.mj.runtime.Code;

public class CodeGenerator extends VisitorAdaptor {
    
    enum Operator {
        Assignment,
        AddopLeftPlus,
        AddopLeftMinus,
        MulopLeftMul,
        MulopLeftDiv,
        MulopLeftMod,
        AddopRightPlus,
        AddopRightMinus,
        MulopRightMul,
        MulopRightDiv,
        MulopRightMod,
    }	
    Stack<Operator> operatorStack = new Stack<>();
    
    private int mainPc;
    public int getMainPC() { return mainPc; }
    
    public void visit(MethodDeclName methodDeclName) {
        
        if ("main".equalsIgnoreCase(methodDeclName.getMethName()))
            mainPc = Code.pc;
        
        methodDeclName.obj.setAdr(Code.pc);
        SyntaxNode methodNode = methodDeclName.getParent();
        VarCounter varCnt = new VarCounter();
        methodNode.traverseTopDown(varCnt);
        FormParamCounter fpCnt = new FormParamCounter();
        methodNode.traverseTopDown(fpCnt);
        
        Code.put(Code.enter);
        Code.put(fpCnt.getCount());
        Code.put(fpCnt.getCount() + varCnt.getCount());
    
    }
    
    public void visit(ReturnStatementExpr returnStatementExpr){
        Code.put(Code.exit);
        Code.put(Code.return_);
    }
    
    public void visit(ReturnStatementNoExpr returnStatementNoExpr){
        Code.put(Code.exit);
        Code.put(Code.return_);
    }

    public void visit(MethodDecl methodDecl) {
        Code.put(Code.exit);
        Code.put(Code.return_);
    }
    
    public void visit(DesignatorPars designatorPars) {
        Obj functionObj = designatorPars.getDesignator().obj;
        int offset = functionObj.getAdr() - Code.pc;
        Code.put(Code.call);
        Code.put2(offset);
        if (designatorPars.getDesignator().obj.getType() != Tab.noType)
            Code.put(Code.pop);
    }
    
    public void visit(DesignatorNotElement designatorNotElement) {
        
        Obj designator = designatorNotElement.obj;
        
        if (
            designatorNotElement.getParent().getClass() == DesignatorAssignExpression.class ||
            designatorNotElement.getParent().getParent().getParent().getParent().getParent().getClass() == TermListExtRecursive.class ||
            designatorNotElement.getParent().getClass() == FactorDesignator.class ||
            designatorNotElement.getParent().getClass() == DesignatorInc.class ||
            designatorNotElement.getParent().getClass() == DesignatorDec.class
        )
            Code.load(designator); 
    
    }
    
    public void visit(DesignatorArr designatorArr) {
        Code.load(designatorArr.obj);
    }
    
    public void visit(DesignatorElement designatorElement) {
        
        Obj designator = designatorElement.obj;
        
        if (
            designatorElement.getParent().getClass() == DesignatorAssignExpression.class ||
            designatorElement.getParent().getParent().getParent().getParent().getParent().getClass() == TermListExtRecursive.class ||
            designatorElement.getParent().getClass() == DesignatorInc.class ||
            designatorElement.getParent().getClass() == DesignatorDec.class 
        ) {
            Code.put(Code.dup2);
            Code.load(designator);
        }
        else if (designatorElement.getParent().getClass() == FactorDesignator.class)
            Code.load(designator);
        
    }
    
    public void visit(FactorNumber factorNumber) { 
        Code.load(factorNumber.obj); 	
    }
    
    public void visit(FactorCharacter factorCharacter) { 
        Code.load(factorCharacter.obj); 
    }
    
    public void visit(FactorBoolean factorBoolean) { 	
        Code.load(factorBoolean.obj);	
    }
    
    public void visit(FactorNewArray factorNewArray) {
        
        Code.put(Code.newarray);
        
        if (factorNewArray.getType().struct == Tab.charType)
            Code.put(0);
        else
            Code.put(1);
    
    }
    
    public void visit(FactorDesignatorFunction factorDesignatorFunction){
        Obj functionObj = factorDesignatorFunction.getDesignator().obj;
        int offset = functionObj.getAdr() - Code.pc;
        Code.put(Code.call);
        Code.put2(offset);
    }
    
    public void visit(TermRecursive termRecursive) {
        Operator op = operatorStack.pop();
        putOperatorCode(op);
    }
    
    public void visit(MinusFirstTerm minusFirstTerm) {
        Code.put(Code.neg);
    }
    
    public void visit(TermListRecursive termListRecursive) {
        Operator op = operatorStack.pop();
        putOperatorCode(op);
    }
    
    public void visit(TermListExtRecursive termListExtRecursive) {
        
        Operator op = operatorStack.pop();
        
        Obj designator = termListExtRecursive.getTermList().obj;
        
        if (op == Operator.Assignment) {
            
            if (designator.getKind() == Obj.Elem) { // array element

                Code.put(Code.dup_x1);
                Code.put(Code.pop);
                Code.put(Code.pop);
                Code.put(Code.dup_x2);
                Code.store(designator);
                
            } else { // variable
                
                Code.store(designator);
                Code.put(Code.pop);
                Code.load(designator);
                
            }
            
        } else if (op == Operator.AddopRightPlus) {
            
            Code.put(Code.add);

            if (designator.getKind() == Obj.Elem) { // array element
                Code.put(Code.dup_x2);
                Code.put(Code.astore);
            } else { // variable
                Code.put(Code.dup);
                Code.store(designator);
            }
            
        } else if (op == Operator.AddopRightMinus) {
            
            Code.put(Code.sub);

            if (designator.getKind() == Obj.Elem) { // array element
                Code.put(Code.dup_x2);
                Code.put(Code.astore);
            } else { // variable
                Code.put(Code.dup);
                Code.store(designator);
            }
            
        } else if (op == Operator.MulopRightMul) {
            
            Code.put(Code.mul);

            if (designator.getKind() == Obj.Elem) { // array element
                Code.put(Code.dup_x2);
                Code.put(Code.astore);
            } else { // variable
                Code.put(Code.dup);
                Code.store(designator);
            }
            
        } else if (op == Operator.MulopRightDiv) {
            
            Code.put(Code.div);

            if (designator.getKind() == Obj.Elem) { // array element
                Code.put(Code.dup_x2);
                Code.put(Code.astore);
            } else { // variable
                Code.put(Code.dup);
                Code.store(designator);
            }
            
        } else if (op == Operator.MulopRightMod) {
            
            Code.put(Code.rem);

            if (designator.getKind() == Obj.Elem) { // array element
                Code.put(Code.dup_x2);
                Code.put(Code.astore);
            } else { // variable
                Code.put(Code.dup);
                Code.store(designator);
            }
            
        }
        
    }
    
    public void visit(DesignatorAssignExpression designatorAssignExpression) {
        
        Obj designator = designatorAssignExpression.getDesignator().obj;
        Operator op = operatorStack.pop();
        
        if (op == Operator.Assignment) {
            
            if (designator.getKind() == Obj.Elem) { // array element
                Code.put(Code.dup_x1);
                Code.put(Code.pop);
                Code.put(Code.pop);
                Code.store(designator);
            } else { // variable
                Code.store(designator);
                Code.put(Code.pop);
            }
            
        } else if (op == Operator.AddopRightPlus) { 
            
            Code.put(Code.add);

            if (designator.getKind() == Obj.Elem) // array element
                Code.put(Code.astore);
            else // variable
                Code.store(designator);
    
        } else if (op == Operator.AddopRightMinus) {
            
            Code.put(Code.sub);

            if (designator.getKind() == Obj.Elem) // array element	
                Code.put(Code.astore);
            else // variable
                Code.store(designator);
    
        } else if (op == Operator.MulopRightMul) {
            
            Code.put(Code.mul);

            if (designator.getKind() == Obj.Elem) // array element	
                Code.put(Code.astore);
            else // variable
                Code.store(designator);
    
        } else if (op == Operator.MulopRightDiv) {
            
            Code.put(Code.div);

            if (designator.getKind() == Obj.Elem) // array element
                Code.put(Code.astore);
            else // variable
                Code.store(designator);
    
        } else if (op == Operator.MulopRightMod) {
            
            Code.put(Code.rem);

            if (designator.getKind() == Obj.Elem) // array element
                Code.put(Code.astore);
            else  // variable
                Code.store(designator);
    
        } 
        
    }
    
    public void visit(DesignatorInc designatorInc) {
        
        Obj designator = designatorInc.getDesignator().obj;
        
        Code.loadConst(1);
        Code.put(Code.add);
        
        if (designator.getKind() == Obj.Elem) // array element
            Code.put(Code.astore);
        else // variable
            Code.store(designatorInc.getDesignator().obj);
        
    }
    
    public void visit(DesignatorDec designatorDec) {
        
        Obj designator = designatorDec.getDesignator().obj;
        
        Code.loadConst(1);
        Code.put(Code.sub);
        
        if (designator.getKind() == Obj.Elem) // array element
            Code.put(Code.astore);
        else //variable
            Code.store(designatorDec.getDesignator().obj);
        
    }
    
    public void visit(PrintStatement printStatement) {
        
        Obj expr = printStatement.getExpr().obj;
        
        if ((expr.getType() == Tab.intType) || (expr.getType() == SemanticAnalyzer.bool) ||
            (expr.getType().getKind() == Struct.Array && (expr.getType().getElemType() == Tab.intType || expr.getType().getElemType() == SemanticAnalyzer.bool))
        ) {
            Code.loadConst(5);
            Code.put(Code.print);
        } else {
            Code.loadConst(1);
            Code.put(Code.bprint);
        }
        
    }
    
    public void visit(ReadStatement readStatement) {
        
        Obj designator = readStatement.getDesignator().obj;
        
        if (designator.getKind() == Obj.Elem) { // array element
            
            Code.load(designator);
            Code.put(Code.dup_x1); 
            Code.put(Code.pop);
            
            if (designator.getType() == Tab.charType) 
                Code.put(Code.bread);
            else 
                Code.put(Code.read);
            
            if (designator.getType().getKind() == Struct.Char) 
                Code.put(Code.bastore);
            else 
                Code.put(Code.astore); 
            
        } else { // variable
            
            if (designator.getType() == Tab.charType) 
                Code.put(Code.bread);
            else 
                Code.put(Code.read);
            
            Code.store(designator);
        
        }
    
    }
    
    public void visit(MulopMul mulopMul) { 
        operatorStack.push(Operator.MulopLeftMul); 
    }

    public void visit(MulopDiv mulopDiv) { 
        operatorStack.push(Operator.MulopLeftDiv); 
    }

    public void visit(MulopMod mulopMod) { 
        operatorStack.push(Operator.MulopLeftMod); 
    }

    public void visit(AddopPlus addopPlus) { 
        operatorStack.push(Operator.AddopLeftPlus); 
    }

    public void visit(AddopMinus addopMinus) { 
        operatorStack.push(Operator.AddopLeftMinus); 
    }
    
    public void visit(Assignment assignment) { 
        operatorStack.push(Operator.Assignment); 
    }

    public void visit(AddopRightPlus addopRightPlus) { 
        operatorStack.push(Operator.AddopRightPlus); 
    }

    public void visit(AddopRightMinus addopRightMinus) { 
        operatorStack.push(Operator.AddopRightMinus); 
    }
    
    public void visit(MulopRightMul mulopRightMul) { 
        operatorStack.push(Operator.MulopRightMul); 
    }
    
    public void visit(MulopRightDiv mulopRightDiv) { 
        operatorStack.push(Operator.MulopRightDiv); 
    }

    public void visit(MulopRightMod mulopRightMod) { 
        operatorStack.push(Operator.MulopRightMod); 
    }
    
    public void putOperatorCode(Operator op) {
        switch (op) {
        case MulopLeftMul:
            Code.put(Code.mul);
        break;
        case MulopLeftDiv:
            Code.put(Code.div);
        break;
        case MulopLeftMod:
            Code.put(Code.rem);
        break;
        case AddopLeftPlus:
            Code.put(Code.add);
        break;
        case AddopLeftMinus:
            Code.put(Code.sub);
        break;
        default:
        break;
        }
    }
    
}
