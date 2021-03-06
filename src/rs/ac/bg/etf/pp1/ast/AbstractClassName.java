// generated with ast extension for cup
// version 0.8
// 7/10/2020 17:1:49


package rs.ac.bg.etf.pp1.ast;

public class AbstractClassName implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Obj obj = null;

    private String absClassName;

    public AbstractClassName (String absClassName) {
        this.absClassName=absClassName;
    }

    public String getAbsClassName() {
        return absClassName;
    }

    public void setAbsClassName(String absClassName) {
        this.absClassName=absClassName;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
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
        buffer.append("AbstractClassName(\n");

        buffer.append(" "+tab+absClassName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AbstractClassName]");
        return buffer.toString();
    }
}
