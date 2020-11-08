// generated with ast extension for cup
// version 0.8
// 7/10/2020 17:1:49


package rs.ac.bg.etf.pp1.ast;

public class ForLoop extends Statement {

    private ForDesignStmt ForDesignStmt;
    private ForCond ForCond;
    private ForDesignStmt ForDesignStmt1;
    private ForLoopEmpty ForLoopEmpty;
    private Statement Statement;

    public ForLoop (ForDesignStmt ForDesignStmt, ForCond ForCond, ForDesignStmt ForDesignStmt1, ForLoopEmpty ForLoopEmpty, Statement Statement) {
        this.ForDesignStmt=ForDesignStmt;
        if(ForDesignStmt!=null) ForDesignStmt.setParent(this);
        this.ForCond=ForCond;
        if(ForCond!=null) ForCond.setParent(this);
        this.ForDesignStmt1=ForDesignStmt1;
        if(ForDesignStmt1!=null) ForDesignStmt1.setParent(this);
        this.ForLoopEmpty=ForLoopEmpty;
        if(ForLoopEmpty!=null) ForLoopEmpty.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
    }

    public ForDesignStmt getForDesignStmt() {
        return ForDesignStmt;
    }

    public void setForDesignStmt(ForDesignStmt ForDesignStmt) {
        this.ForDesignStmt=ForDesignStmt;
    }

    public ForCond getForCond() {
        return ForCond;
    }

    public void setForCond(ForCond ForCond) {
        this.ForCond=ForCond;
    }

    public ForDesignStmt getForDesignStmt1() {
        return ForDesignStmt1;
    }

    public void setForDesignStmt1(ForDesignStmt ForDesignStmt1) {
        this.ForDesignStmt1=ForDesignStmt1;
    }

    public ForLoopEmpty getForLoopEmpty() {
        return ForLoopEmpty;
    }

    public void setForLoopEmpty(ForLoopEmpty ForLoopEmpty) {
        this.ForLoopEmpty=ForLoopEmpty;
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ForDesignStmt!=null) ForDesignStmt.accept(visitor);
        if(ForCond!=null) ForCond.accept(visitor);
        if(ForDesignStmt1!=null) ForDesignStmt1.accept(visitor);
        if(ForLoopEmpty!=null) ForLoopEmpty.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ForDesignStmt!=null) ForDesignStmt.traverseTopDown(visitor);
        if(ForCond!=null) ForCond.traverseTopDown(visitor);
        if(ForDesignStmt1!=null) ForDesignStmt1.traverseTopDown(visitor);
        if(ForLoopEmpty!=null) ForLoopEmpty.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ForDesignStmt!=null) ForDesignStmt.traverseBottomUp(visitor);
        if(ForCond!=null) ForCond.traverseBottomUp(visitor);
        if(ForDesignStmt1!=null) ForDesignStmt1.traverseBottomUp(visitor);
        if(ForLoopEmpty!=null) ForLoopEmpty.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ForLoop(\n");

        if(ForDesignStmt!=null)
            buffer.append(ForDesignStmt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ForCond!=null)
            buffer.append(ForCond.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ForDesignStmt1!=null)
            buffer.append(ForDesignStmt1.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ForLoopEmpty!=null)
            buffer.append(ForLoopEmpty.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ForLoop]");
        return buffer.toString();
    }
}
