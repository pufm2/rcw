package puf.m2.rcw.operator;

public class EdgeAddition extends UnaryOperator {

    public EdgeAddition(char l1, char l2) {
        super(l1, l2);
    }

    public String toString() {
        return "add_" + l1 + "_" + l2 + "(" + operand + ")";
    }
    
    public boolean equals(Object o) {
        if (!(o instanceof EdgeAddition)) {
            return false;
        }
        
        EdgeAddition op = (EdgeAddition) o;
        if (l1 == op.l1 && l2 == op.l2) {
            return true;
        } else {
            return false;
        }
    }
}
