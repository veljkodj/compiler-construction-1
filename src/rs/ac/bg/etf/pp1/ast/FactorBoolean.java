// generated with ast extension for cup
// version 0.8
// 7/10/2020 17:1:49


package rs.ac.bg.etf.pp1.ast;

public class FactorBoolean extends Factor {

    private Integer factorBoolean;

    public FactorBoolean (Integer factorBoolean) {
        this.factorBoolean=factorBoolean;
    }

    public Integer getFactorBoolean() {
        return factorBoolean;
    }

    public void setFactorBoolean(Integer factorBoolean) {
        this.factorBoolean=factorBoolean;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FactorBoolean(\n");

        buffer.append(" "+tab+factorBoolean);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FactorBoolean]");
        return buffer.toString();
    }
}
