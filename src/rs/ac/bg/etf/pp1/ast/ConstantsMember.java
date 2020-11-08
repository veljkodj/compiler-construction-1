// generated with ast extension for cup
// version 0.8
// 7/10/2020 17:1:49


package rs.ac.bg.etf.pp1.ast;

public class ConstantsMember extends ConstList {

    private ConstListPart ConstListPart;

    public ConstantsMember (ConstListPart ConstListPart) {
        this.ConstListPart=ConstListPart;
        if(ConstListPart!=null) ConstListPart.setParent(this);
    }

    public ConstListPart getConstListPart() {
        return ConstListPart;
    }

    public void setConstListPart(ConstListPart ConstListPart) {
        this.ConstListPart=ConstListPart;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConstListPart!=null) ConstListPart.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstListPart!=null) ConstListPart.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstListPart!=null) ConstListPart.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstantsMember(\n");

        if(ConstListPart!=null)
            buffer.append(ConstListPart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstantsMember]");
        return buffer.toString();
    }
}
