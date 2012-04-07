package puf.m2.rcw.term;

import puf.m2.rcw.graph.LabelSet;
import puf.m2.rcw.graph.Vertex;
import puf.m2.rcw.graph.VertexFamily;
import puf.m2.rcw.graph.VertexSet;
import puf.m2.rcw.operator.Operator;

public class Term {
    
    private VertexSet vertexSet;
    private LabelSet usedLabels;
    private LabelSet gammaImage;
    private VertexFamily partites;
    
    public Term() {
        
    }
    
    public Term(VertexSet vertexSet, LabelSet usedLabels,
                    LabelSet gammaImage, VertexFamily partites) {

        this.vertexSet = vertexSet;
        this.usedLabels = usedLabels;
        this.gammaImage = gammaImage;
        this.partites = partites;
    }

    public VertexSet getVertexSet() {
        return vertexSet;
    }

    public LabelSet getUsedLabels() {
        return usedLabels;
    }

    public LabelSet getGammaImage() {
        return gammaImage;
    }

    public VertexFamily getPartites() {
        return partites;
    }
    
    public String toString() {
        return toString(this, "");
    }
    
    private String toString(Term term, String st) {
        if (term instanceof ProperTerm) {
            ProperTerm properTerm = (ProperTerm) term;
            st = toString(properTerm.getLeft(), st);
            APort aPort = properTerm.getRight();
            Vertex v = aPort.getVertexSet().toArray()[0];
            st = "oplus(" + st + "," + aPort.getLabel() + "(" + v.getName() + ")" + ")";
            for (Operator operator : properTerm.getOperatorList()) {
                operator.setOperands(st);
                st = operator.toString();
            }
            return st;
        } else {
            APort aPort = (APort) term;
            Vertex v = aPort.getVertexSet().toArray()[0];
            return aPort.getLabel() + "(" + v.getName() + ")";
        }

    }
}
