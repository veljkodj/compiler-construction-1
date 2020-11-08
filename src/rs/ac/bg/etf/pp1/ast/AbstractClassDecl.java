// generated with ast extension for cup
// version 0.8
// 7/10/2020 17:1:49


package rs.ac.bg.etf.pp1.ast;

public class AbstractClassDecl implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private AbstractClassName AbstractClassName;
    private OptionalExtend OptionalExtend;
    private ClassVarDeclList ClassVarDeclList;
    private OptionalAbstractClassMethodDeclList OptionalAbstractClassMethodDeclList;

    public AbstractClassDecl (AbstractClassName AbstractClassName, OptionalExtend OptionalExtend, ClassVarDeclList ClassVarDeclList, OptionalAbstractClassMethodDeclList OptionalAbstractClassMethodDeclList) {
        this.AbstractClassName=AbstractClassName;
        if(AbstractClassName!=null) AbstractClassName.setParent(this);
        this.OptionalExtend=OptionalExtend;
        if(OptionalExtend!=null) OptionalExtend.setParent(this);
        this.ClassVarDeclList=ClassVarDeclList;
        if(ClassVarDeclList!=null) ClassVarDeclList.setParent(this);
        this.OptionalAbstractClassMethodDeclList=OptionalAbstractClassMethodDeclList;
        if(OptionalAbstractClassMethodDeclList!=null) OptionalAbstractClassMethodDeclList.setParent(this);
    }

    public AbstractClassName getAbstractClassName() {
        return AbstractClassName;
    }

    public void setAbstractClassName(AbstractClassName AbstractClassName) {
        this.AbstractClassName=AbstractClassName;
    }

    public OptionalExtend getOptionalExtend() {
        return OptionalExtend;
    }

    public void setOptionalExtend(OptionalExtend OptionalExtend) {
        this.OptionalExtend=OptionalExtend;
    }

    public ClassVarDeclList getClassVarDeclList() {
        return ClassVarDeclList;
    }

    public void setClassVarDeclList(ClassVarDeclList ClassVarDeclList) {
        this.ClassVarDeclList=ClassVarDeclList;
    }

    public OptionalAbstractClassMethodDeclList getOptionalAbstractClassMethodDeclList() {
        return OptionalAbstractClassMethodDeclList;
    }

    public void setOptionalAbstractClassMethodDeclList(OptionalAbstractClassMethodDeclList OptionalAbstractClassMethodDeclList) {
        this.OptionalAbstractClassMethodDeclList=OptionalAbstractClassMethodDeclList;
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
        if(AbstractClassName!=null) AbstractClassName.accept(visitor);
        if(OptionalExtend!=null) OptionalExtend.accept(visitor);
        if(ClassVarDeclList!=null) ClassVarDeclList.accept(visitor);
        if(OptionalAbstractClassMethodDeclList!=null) OptionalAbstractClassMethodDeclList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(AbstractClassName!=null) AbstractClassName.traverseTopDown(visitor);
        if(OptionalExtend!=null) OptionalExtend.traverseTopDown(visitor);
        if(ClassVarDeclList!=null) ClassVarDeclList.traverseTopDown(visitor);
        if(OptionalAbstractClassMethodDeclList!=null) OptionalAbstractClassMethodDeclList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(AbstractClassName!=null) AbstractClassName.traverseBottomUp(visitor);
        if(OptionalExtend!=null) OptionalExtend.traverseBottomUp(visitor);
        if(ClassVarDeclList!=null) ClassVarDeclList.traverseBottomUp(visitor);
        if(OptionalAbstractClassMethodDeclList!=null) OptionalAbstractClassMethodDeclList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AbstractClassDecl(\n");

        if(AbstractClassName!=null)
            buffer.append(AbstractClassName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(OptionalExtend!=null)
            buffer.append(OptionalExtend.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ClassVarDeclList!=null)
            buffer.append(ClassVarDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(OptionalAbstractClassMethodDeclList!=null)
            buffer.append(OptionalAbstractClassMethodDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AbstractClassDecl]");
        return buffer.toString();
    }
}
