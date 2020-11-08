// generated with ast extension for cup
// version 0.8
// 7/10/2020 17:1:49


package rs.ac.bg.etf.pp1.ast;

public class NoForCondition extends ForCond {

    public NoForCondition () {
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("NoForCondition(\n");

        buffer.append(tab);
        buffer.append(") [NoForCondition]");
        return buffer.toString();
    }
}
