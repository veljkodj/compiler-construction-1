// generated with ast extension for cup
// version 0.8
// 7/10/2020 17:1:49


package rs.ac.bg.etf.pp1.ast;

public class VarDeclarationsMember extends VarDeclList {

    private VarDeclListPart VarDeclListPart;

    public VarDeclarationsMember (VarDeclListPart VarDeclListPart) {
        this.VarDeclListPart=VarDeclListPart;
        if(VarDeclListPart!=null) VarDeclListPart.setParent(this);
    }

    public VarDeclListPart getVarDeclListPart() {
        return VarDeclListPart;
    }

    public void setVarDeclListPart(VarDeclListPart VarDeclListPart) {
        this.VarDeclListPart=VarDeclListPart;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VarDeclListPart!=null) VarDeclListPart.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarDeclListPart!=null) VarDeclListPart.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarDeclListPart!=null) VarDeclListPart.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarDeclarationsMember(\n");

        if(VarDeclListPart!=null)
            buffer.append(VarDeclListPart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarDeclarationsMember]");
        return buffer.toString();
    }
}
