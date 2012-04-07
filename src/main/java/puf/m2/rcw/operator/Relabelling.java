package puf.m2.rcw.operator;

public class Relabelling extends Operator {

    private char l1, l2;

    public Relabelling(char l1, char l2) {
        this.l1 = l1;
        this.l2 = l2;
    }
    
    public String toString(String operand) {
        
        return "rel_" + l1 + "_" + l2 + "(" + operand + ")";
    }
    
    public boolean equals(Object o) {
        if (!(o instanceof Relabelling)) {
            return false;
        }
        
        Relabelling op = (Relabelling) o;
        if (l1 == op.l1 && l2 == op.l2) {
            return true;
        } else {
            return false;
        }
    }

}
