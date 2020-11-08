// generated with ast extension for cup
// version 0.8
// 7/10/2020 17:1:49


package rs.ac.bg.etf.pp1.ast;

public class NonMethodDeclarationList extends NonMethodDeclList {

    private NonMethodDeclList NonMethodDeclList;
    private NonMethodDeclListPart NonMethodDeclListPart;

    public NonMethodDeclarationList (NonMethodDeclList NonMethodDeclList, NonMethodDeclListPart NonMethodDeclListPart) {
        this.NonMethodDeclList=NonMethodDeclList;
        if(NonMethodDeclList!=null) NonMethodDeclList.setParent(this);
        this.NonMethodDeclListPart=NonMethodDeclListPart;
        if(NonMethodDeclListPart!=null) NonMethodDeclListPart.setParent(this);
    }

    public NonMethodDeclList getNonMethodDeclList() {
        return NonMethodDeclList;
    }

    public void setNonMethodDeclList(NonMethodDeclList NonMethodDeclList) {
        this.NonMethodDeclList=NonMethodDeclList;
    }

    public NonMethodDeclListPart getNonMethodDeclListPart() {
        return NonMethodDeclListPart;
    }

    public void setNonMethodDeclListPart(NonMethodDeclListPart NonMethodDeclListPart) {
        this.NonMethodDeclListPart=NonMethodDeclListPart;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(NonMethodDeclList!=null) NonMethodDeclList.accept(visitor);
        if(NonMethodDeclListPart!=null) NonMethodDeclListPart.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(NonMethodDeclList!=null) NonMethodDeclList.traverseTopDown(visitor);
        if(NonMethodDeclListPart!=null) NonMethodDeclListPart.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(NonMethodDeclList!=null) NonMethodDeclList.traverseBottomUp(visitor);
        if(NonMethodDeclListPart!=null) NonMethodDeclListPart.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("NonMethodDeclarationList(\n");

        if(NonMethodDeclList!=null)
            buffer.append(NonMethodDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(NonMethodDeclListPart!=null)
            buffer.append(NonMethodDeclListPart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [NonMethodDeclarationList]");
        return buffer.toString();
    }
}
