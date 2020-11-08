// generated with ast extension for cup
// version 0.8
// 7/10/2020 17:1:49


package rs.ac.bg.etf.pp1.ast;

public class ClassDecl implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private ClassName ClassName;
    private OptionalExtend OptionalExtend;
    private ClassVarDeclList ClassVarDeclList;
    private OptionalClassMethodDeclList OptionalClassMethodDeclList;

    public ClassDecl (ClassName ClassName, OptionalExtend OptionalExtend, ClassVarDeclList ClassVarDeclList, OptionalClassMethodDeclList OptionalClassMethodDeclList) {
        this.ClassName=ClassName;
        if(ClassName!=null) ClassName.setParent(this);
        this.OptionalExtend=OptionalExtend;
        if(OptionalExtend!=null) OptionalExtend.setParent(this);
        this.ClassVarDeclList=ClassVarDeclList;
        if(ClassVarDeclList!=null) ClassVarDeclList.setParent(this);
        this.OptionalClassMethodDeclList=OptionalClassMethodDeclList;
        if(OptionalClassMethodDeclList!=null) OptionalClassMethodDeclList.setParent(this);
    }

    public ClassName getClassName() {
        return ClassName;
    }

    public void setClassName(ClassName ClassName) {
        this.ClassName=ClassName;
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

    public OptionalClassMethodDeclList getOptionalClassMethodDeclList() {
        return OptionalClassMethodDeclList;
    }

    public void setOptionalClassMethodDeclList(OptionalClassMethodDeclList OptionalClassMethodDeclList) {
        this.OptionalClassMethodDeclList=OptionalClassMethodDeclList;
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
        if(ClassName!=null) ClassName.accept(visitor);
        if(OptionalExtend!=null) OptionalExtend.accept(visitor);
        if(ClassVarDeclList!=null) ClassVarDeclList.accept(visitor);
        if(OptionalClassMethodDeclList!=null) OptionalClassMethodDeclList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ClassName!=null) ClassName.traverseTopDown(visitor);
        if(OptionalExtend!=null) OptionalExtend.traverseTopDown(visitor);
        if(ClassVarDeclList!=null) ClassVarDeclList.traverseTopDown(visitor);
        if(OptionalClassMethodDeclList!=null) OptionalClassMethodDeclList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ClassName!=null) ClassName.traverseBottomUp(visitor);
        if(OptionalExtend!=null) OptionalExtend.traverseBottomUp(visitor);
        if(ClassVarDeclList!=null) ClassVarDeclList.traverseBottomUp(visitor);
        if(OptionalClassMethodDeclList!=null) OptionalClassMethodDeclList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassDecl(\n");

        if(ClassName!=null)
            buffer.append(ClassName.toString("  "+tab));
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

        if(OptionalClassMethodDeclList!=null)
            buffer.append(OptionalClassMethodDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassDecl]");
        return buffer.toString();
    }
}
