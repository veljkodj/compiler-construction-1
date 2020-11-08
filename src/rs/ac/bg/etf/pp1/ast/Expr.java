// generated with ast extension for cup
// version 0.8
// 7/10/2020 17:1:49


package rs.ac.bg.etf.pp1.ast;

public class Expr implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Obj obj = null;

    private TermListExt TermListExt;

    public Expr (TermListExt TermListExt) {
        this.TermListExt=TermListExt;
        if(TermListExt!=null) TermListExt.setParent(this);
    }

    public TermListExt getTermListExt() {
        return TermListExt;
    }

    public void setTermListExt(TermListExt TermListExt) {
        this.TermListExt=TermListExt;
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
        if(TermListExt!=null) TermListExt.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(TermListExt!=null) TermListExt.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(TermListExt!=null) TermListExt.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Expr(\n");

        if(TermListExt!=null)
            buffer.append(TermListExt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Expr]");
        return buffer.toString();
    }
}
