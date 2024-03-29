package puf.m2.rcw.operator;

public class UnaryOperator extends Operator {
    protected char l1, l2;
    
    protected String operand;

    public UnaryOperator(char l1, char l2) {
        this.l1 = l1;
        this.l2 = l2;
    }

    public void setOperands(String ... operands) {
        this.operand = operands.clone()[0];
    }
}
