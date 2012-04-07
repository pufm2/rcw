package puf.m2.rcw.term;

import java.util.List;
import java.util.Stack;

import puf.m2.rcw.graph.LabelSet;
import puf.m2.rcw.graph.VertexFamily;
import puf.m2.rcw.graph.VertexSet;
import puf.m2.rcw.operator.Operator;

public class ProperTerm extends Term {
    private Term left;
    private APort right;
    private List<Operator> operatorList;
    
    public ProperTerm() {
        operatorList = new Stack<Operator>();
    }
    
    public ProperTerm(Term left, APort right, VertexSet vertexSet, LabelSet usedLabels,
                        LabelSet gammaImage, VertexFamily partites, List<Operator> operatorList) {
        
        super(vertexSet, usedLabels, gammaImage, partites);
        this.left = left;
        this.right = right;
        this.operatorList = operatorList;

    }

    public Term getLeft() {
        return left;
    }

    public void setLeft(Term left) {
        this.left = left;
    }

    public APort getRight() {
        return right;
    }

    public void setRight(APort right) {
        this.right = right;
    }

    public List<Operator> getOperatorList() {
        return operatorList;
    }

    public void setOperatorList(List<Operator> operatorList) {
        this.operatorList = operatorList;
    }

}
