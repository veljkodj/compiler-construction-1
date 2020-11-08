// generated with ast extension for cup
// version 0.8
// 7/10/2020 17:1:49


package rs.ac.bg.etf.pp1.ast;

public class AbstractClassMethodDeclarations extends AbstractClassMethodDeclList {

    private AbstractClassMethodDeclList AbstractClassMethodDeclList;
    private AbstractClassMethodDeclPart AbstractClassMethodDeclPart;

    public AbstractClassMethodDeclarations (AbstractClassMethodDeclList AbstractClassMethodDeclList, AbstractClassMethodDeclPart AbstractClassMethodDeclPart) {
        this.AbstractClassMethodDeclList=AbstractClassMethodDeclList;
        if(AbstractClassMethodDeclList!=null) AbstractClassMethodDeclList.setParent(this);
        this.AbstractClassMethodDeclPart=AbstractClassMethodDeclPart;
        if(AbstractClassMethodDeclPart!=null) AbstractClassMethodDeclPart.setParent(this);
    }

    public AbstractClassMethodDeclList getAbstractClassMethodDeclList() {
        return AbstractClassMethodDeclList;
    }

    public void setAbstractClassMethodDeclList(AbstractClassMethodDeclList AbstractClassMethodDeclList) {
        this.AbstractClassMethodDeclList=AbstractClassMethodDeclList;
    }

    public AbstractClassMethodDeclPart getAbstractClassMethodDeclPart() {
        return AbstractClassMethodDeclPart;
    }

    public void setAbstractClassMethodDeclPart(AbstractClassMethodDeclPart AbstractClassMethodDeclPart) {
        this.AbstractClassMethodDeclPart=AbstractClassMethodDeclPart;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(AbstractClassMethodDeclList!=null) AbstractClassMethodDeclList.accept(visitor);
        if(AbstractClassMethodDeclPart!=null) AbstractClassMethodDeclPart.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(AbstractClassMethodDeclList!=null) AbstractClassMethodDeclList.traverseTopDown(visitor);
        if(AbstractClassMethodDeclPart!=null) AbstractClassMethodDeclPart.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(AbstractClassMethodDeclList!=null) AbstractClassMethodDeclList.traverseBottomUp(visitor);
        if(AbstractClassMethodDeclPart!=null) AbstractClassMethodDeclPart.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AbstractClassMethodDeclarations(\n");

        if(AbstractClassMethodDeclList!=null)
            buffer.append(AbstractClassMethodDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(AbstractClassMethodDeclPart!=null)
            buffer.append(AbstractClassMethodDeclPart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AbstractClassMethodDeclarations]");
        return buffer.toString();
    }
}
