// generated with ast extension for cup
// version 0.8
// 7/10/2020 17:1:49


package rs.ac.bg.etf.pp1.ast;

public class FormalParamArr extends FormPar {

    private Type Type;
    private String arrName;

    public FormalParamArr (Type Type, String arrName) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.arrName=arrName;
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public String getArrName() {
        return arrName;
    }

    public void setArrName(String arrName) {
        this.arrName=arrName;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FormalParamArr(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+arrName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FormalParamArr]");
        return buffer.toString();
    }
}
