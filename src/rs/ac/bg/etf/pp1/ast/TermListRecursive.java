// generated with ast extension for cup
// version 0.8
// 7/10/2020 17:1:49


package rs.ac.bg.etf.pp1.ast;

public class TermListRecursive extends TermList {

    private TermList TermList;
    private AddopLeft AddopLeft;
    private Term Term;

    public TermListRecursive (TermList TermList, AddopLeft AddopLeft, Term Term) {
        this.TermList=TermList;
        if(TermList!=null) TermList.setParent(this);
        this.AddopLeft=AddopLeft;
        if(AddopLeft!=null) AddopLeft.setParent(this);
        this.Term=Term;
        if(Term!=null) Term.setParent(this);
    }

    public TermList getTermList() {
        return TermList;
    }

    public void setTermList(TermList TermList) {
        this.TermList=TermList;
    }

    public AddopLeft getAddopLeft() {
        return AddopLeft;
    }

    public void setAddopLeft(AddopLeft AddopLeft) {
        this.AddopLeft=AddopLeft;
    }

    public Term getTerm() {
        return Term;
    }

    public void setTerm(Term Term) {
        this.Term=Term;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(TermList!=null) TermList.accept(visitor);
        if(AddopLeft!=null) AddopLeft.accept(visitor);
        if(Term!=null) Term.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(TermList!=null) TermList.traverseTopDown(visitor);
        if(AddopLeft!=null) AddopLeft.traverseTopDown(visitor);
        if(Term!=null) Term.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(TermList!=null) TermList.traverseBottomUp(visitor);
        if(AddopLeft!=null) AddopLeft.traverseBottomUp(visitor);
        if(Term!=null) Term.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("TermListRecursive(\n");

        if(TermList!=null)
            buffer.append(TermList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(AddopLeft!=null)
            buffer.append(AddopLeft.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Term!=null)
            buffer.append(Term.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [TermListRecursive]");
        return buffer.toString();
    }
}
