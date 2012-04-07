package puf.m2.rcw.graph;

import java.util.HashSet;
import java.util.Set;

public class VertexFamily {
    private Set<VertexSet> vertexFamily;

    public VertexFamily() {
        vertexFamily = new HashSet<VertexSet>();
    }

    public VertexFamily(VertexFamily vf1) {

        this.vertexFamily = new HashSet<VertexSet>(vf1.getVertexFamily());
    }
    
    public Set<VertexSet> getVertexFamily() {
        return vertexFamily;
    }
    
    public void add(VertexSet vs) {
        vertexFamily.add(vs);
    }
    
    public boolean isEmpty() {
        return vertexFamily.isEmpty();
    }

    public boolean remove(VertexSet vs) {
        return vertexFamily.remove(vs);
    }

    public static VertexFamily union(VertexFamily vf1, VertexFamily vf2) {
        VertexFamily vf = new VertexFamily(vf1);
        for (VertexSet vs : vf2.getVertexFamily()) {
            vf.add(vs);
        }
        return vf;
    }

}
