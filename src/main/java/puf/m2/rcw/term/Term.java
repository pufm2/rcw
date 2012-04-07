package puf.m2.rcw.term;

import puf.m2.rcw.graph.LabelSet;
import puf.m2.rcw.graph.VertexFamily;
import puf.m2.rcw.graph.VertexSet;

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
}
