package puf.m2.rcw.term;

import puf.m2.rcw.graph.LabelSet;
import puf.m2.rcw.graph.VertexFamily;
import puf.m2.rcw.graph.VertexSet;

public class Port extends Term {

    private char label;
    public Port() {
        
    }
    
    public Port(VertexSet vertexSet, LabelSet usedLabels,
            LabelSet gammaMapping, VertexFamily partites, char label) {
        super(vertexSet, usedLabels, gammaMapping, partites);
        this.label = label;
    }

    public char getLabel() {
        return label;
    }

    public boolean equals(Object o) {
        if (!(o instanceof Port)) {
            return false;
        }
        
        Port port = (Port) o;
        String portName = port.getVertexSet().toArray()[0].getName();
        String name = getVertexSet().toArray()[0].getName();
        if (name.equals(portName) && label == port.label) {
            return true;
        }
        
        return false;
    }
}
