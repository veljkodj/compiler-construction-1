// generated with ast extension for cup
// version 0.8
// 7/10/2020 17:1:49


package rs.ac.bg.etf.pp1.ast;

public class NoClassMethodDeclListDefinition extends OptionalClassMethodDeclList {

    public NoClassMethodDeclListDefinition () {
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
        buffer.append("NoClassMethodDeclListDefinition(\n");

        buffer.append(tab);
        buffer.append(") [NoClassMethodDeclListDefinition]");
        return buffer.toString();
    }
}
