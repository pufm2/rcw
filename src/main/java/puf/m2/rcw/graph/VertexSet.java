package puf.m2.rcw.graph;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class VertexSet {
    private Map<String, Vertex> vertexMap;

    public VertexSet() {
        vertexMap = new HashMap<String, Vertex>();
    }

    public Map<String, Vertex> getVertexMap() {
        return vertexMap;
    }
    
    public void add(Vertex v) {
        vertexMap.put(v.getName(), v);
    }
    
    public boolean contains(Vertex v) {
        return vertexMap.containsKey(v.getName());
    }
    
    public boolean equals(VertexSet vs) {
        return vertexMap.equals(vs.getVertexMap()); 
    }
    
    public void union(VertexSet vs) {
        for (String name : vs.getVertexMap().keySet()) {
            add(vs.getVertexMap().get(name));
        }
    }
    
    public LabelSet getLabelSet() {
        LabelSet ls = new LabelSet();
        for (Vertex v : vertexMap.values()) {
            ls.add(v.getLabel());
        }
        return ls;
    }
    
    public Collection<Vertex> vertices() {
        return vertexMap.values();
    }
    
    public static VertexSet union(VertexSet vs1, VertexSet vs2) {
        VertexSet uvs = new VertexSet();
        for (String name : vs1.getVertexMap().keySet()) {
            uvs.add(vs1.getVertexMap().get(name));
        }
        
        for (String name : vs2.getVertexMap().keySet()) {
            uvs.add(vs2.getVertexMap().get(name));
        }
        
        return uvs;
    }
    
    public static VertexSet subtract(VertexSet vs1, VertexSet vs2) {
    	VertexSet vs = new VertexSet();
        for (Vertex v : vs1.vertices()) {
    		if (!vs2.contains(v)) {
    		    vs.add(v);
    		}
    	}
        return vs;
    }
    
    public Vertex get(String name) {
        return vertexMap.get(name);
    }
    
    public Vertex get(Vertex v) {
        return vertexMap.get(v.getName());
    }
    
    public Vertex[] toArray() {
        return vertexMap.values().toArray(new Vertex[]{});
    }
    
    
    public void cleanAdjacency() {
        for (Vertex v : vertices()) {
            v.cleanAdjacency();
        }

    }
    
    public String toString() {
        String st = "";
        for (Vertex v : vertices()) {
            st = st + v + "\n";
        }
        return st;
    }

}
