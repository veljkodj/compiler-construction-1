// generated with ast extension for cup
// version 0.8
// 7/10/2020 17:1:49


package rs.ac.bg.etf.pp1.ast;

public class AbstractClassMethodDeclListDefinition extends OptionalAbstractClassMethodDeclList {

    private AbstractClassMethodDeclList AbstractClassMethodDeclList;

    public AbstractClassMethodDeclListDefinition (AbstractClassMethodDeclList AbstractClassMethodDeclList) {
        this.AbstractClassMethodDeclList=AbstractClassMethodDeclList;
        if(AbstractClassMethodDeclList!=null) AbstractClassMethodDeclList.setParent(this);
    }

    public AbstractClassMethodDeclList getAbstractClassMethodDeclList() {
        return AbstractClassMethodDeclList;
    }

    public void setAbstractClassMethodDeclList(AbstractClassMethodDeclList AbstractClassMethodDeclList) {
        this.AbstractClassMethodDeclList=AbstractClassMethodDeclList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(AbstractClassMethodDeclList!=null) AbstractClassMethodDeclList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(AbstractClassMethodDeclList!=null) AbstractClassMethodDeclList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(AbstractClassMethodDeclList!=null) AbstractClassMethodDeclList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AbstractClassMethodDeclListDefinition(\n");

        if(AbstractClassMethodDeclList!=null)
            buffer.append(AbstractClassMethodDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AbstractClassMethodDeclListDefinition]");
        return buffer.toString();
    }
}
