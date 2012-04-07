package puf.m2.rcw.graph;

import java.util.HashMap;
import java.util.Map;

import puf.m2.rcw.edgelist.parser.AstEdge;
import puf.m2.rcw.edgelist.parser.AstEdgeParse;
import puf.m2.rcw.edgelist.parser.AstVertex;

public class AdjacencyList {
    
    private Map<String, Vertex> vertices = new HashMap<String, Vertex>();
    
    public AdjacencyList(int n, AstEdgeParse astedgeParse) {
        for (int i = 0; i < astedgeParse.jjtGetNumChildren(); i++) {
            AstEdge edge = (AstEdge) astedgeParse.jjtGetChild(i);
            AstVertex u = (AstVertex) edge.jjtGetChild(0);
            AstVertex v = (AstVertex) edge.jjtGetChild(1);
            Vertex vertex = vertices.get(u.getName());
            if (vertex != null) {
                vertex.addAjacentVertex(new Vertex(v.getName()));
            } else {
                vertex = new Vertex(u.getName());
                vertex.addAjacentVertex(new Vertex(v.getName()));
                vertices.put(u.getName(), vertex);
            }
        }
    }
}
