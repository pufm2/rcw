package puf.m2.rcw.operator;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public abstract class Operator {
    
    private static final Map<Class<? extends Operator>, Integer >PRECEDENCE_MAP = 
            new HashMap<Class<? extends Operator>, Integer>();

    static {
        PRECEDENCE_MAP.put(DisjointUnion.class, 2);
        PRECEDENCE_MAP.put(Relabelling.class, 1);
        PRECEDENCE_MAP.put(EdgeAddition.class, 0);
    }
    
    public static Comparator<Operator> COMPARATOR = new Comparator<Operator>() {

        public int compare(Operator op1, Operator op2) {
            int result = PRECEDENCE_MAP.get(op1.getClass()) - PRECEDENCE_MAP.get(op2.getClass());
            if (result != 0) {
                return result;
            }
            
            if (op1 instanceof UnaryOperator) {
                UnaryOperator unaryOp1 = (UnaryOperator) op1;
                UnaryOperator unaryOp2 = (UnaryOperator) op2;
                
                result = unaryOp1.l1 - unaryOp2.l1;
                if (result != 0) {
                    return result;
                }
                
                result = unaryOp1.l2 - unaryOp2.l2;
                return result;
            } else {
                return 0;
            }
        }
        
    };
    
    public abstract void setOperands(String ... operands);
    
}
