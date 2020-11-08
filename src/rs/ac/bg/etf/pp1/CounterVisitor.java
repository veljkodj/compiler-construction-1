package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.*;

public class CounterVisitor extends VisitorAdaptor {
    
    protected int count;
    
    public int getCount() { return count; }
    
    public static class VarCounter extends CounterVisitor {
        
        public void visit(Var Var) { count++; }
        public void visit(VarArray varArray) { count++; }
        
    }
    
    public static class FormParamCounter extends CounterVisitor {
        
        public void visit(FormalParamVar formalParamVar) { count++; }
        public void visit(FormalParamArr formalParamArr) { count++; }
        
    }
    
}
