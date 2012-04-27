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
            Port port = properTerm.getRight();
            Vertex v = port.getVertexSet().toArray()[0];
            st = "oplus(" + st + "," + port.getLabel() + "(" + v.getName() + ")" + ")";
            for (Operator operator : properTerm.getOperatorList()) {
                operator.setOperands(st);
                st = operator.toString();
            }
            return st;
        } else {
            Port port = (Port) term;
            Vertex v = port.getVertexSet().toArray()[0];
            return port.getLabel() + "(" + v.getName() + ")";
        }

    }
}
