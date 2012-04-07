package puf.m2.rcw.operator;

public class DisjointUnion extends Operator {

    private String[] operands;
    
    public void setOperands(String ... operands) {
        this.operands = operands.clone();
    }
    
    public String toString(String operand) {
        return "oplus(" + operands[0] + "," + operands[1] + ")";
    }

}
