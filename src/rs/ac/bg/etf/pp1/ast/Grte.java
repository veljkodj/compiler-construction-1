// generated with ast extension for cup
// version 0.8
// 7/10/2020 17:1:49


package rs.ac.bg.etf.pp1.ast;

public class Grte extends Relop {

    public Grte () {
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
        buffer.append("Grte(\n");

        buffer.append(tab);
        buffer.append(") [Grte]");
        return buffer.toString();
    }
}