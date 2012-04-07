package puf.m2.rcw.operator;

public class EdgeAddition extends Operator {
    private char l1, l2;
    
    private String operand;

    public EdgeAddition(char l1, char l2) {
        this.l1 = l1;
        this.l2 = l2;
    }

    public void setOperands(String ... operands) {
        this.operand = operands.clone()[0];
    }

    public String toString() {
        return "add_" + l1 + "_" + l2 + "(" + operand + ")";
    }
    
}
