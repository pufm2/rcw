package puf.m2.rcw.term;

import puf.m2.rcw.graph.LabelSet;
import puf.m2.rcw.graph.VertexFamily;
import puf.m2.rcw.graph.VertexSet;

public class APort extends Term {

    private char label;
    public APort() {
        
    }
    
    public APort(VertexSet vertexSet, LabelSet usedLabels,
            LabelSet gammaMapping, VertexFamily partites, char label) {
        super(vertexSet, usedLabels, gammaMapping, partites);
        this.label = label;
    }

    public char getLabel() {
        return label;
    }

}
