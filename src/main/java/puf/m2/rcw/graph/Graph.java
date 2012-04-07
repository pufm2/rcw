package puf.m2.rcw.graph;

import java.io.ByteArrayInputStream;

import puf.m2.rcw.edgelist.parser.AstEdge;
import puf.m2.rcw.edgelist.parser.AstEdgeParse;
import puf.m2.rcw.edgelist.parser.AstVertex;
import puf.m2.rcw.edgelist.parser.EdgeListParser;
import puf.m2.rcw.edgelist.parser.ParseException;
import puf.m2.rcw.exception.RcwException;

public class Graph {
    private VertexSet vertexSet;

    public Graph(String edgeList) throws RcwException {
        
        EdgeListParser parser = new EdgeListParser(
                new ByteArrayInputStream(edgeList.getBytes()));
        AstEdgeParse astEdgeParse = null;
        try {
            astEdgeParse = parser.edgeParse();
        } catch (ParseException e) {
            throw new RcwException(e);
        }

        vertexSet = new VertexSet();
        for (int i = 0; i < astEdgeParse.jjtGetNumChildren(); i++) {
            AstEdge edge = (AstEdge) astEdgeParse.jjtGetChild(i);
            AstVertex u = (AstVertex) edge.jjtGetChild(0);
            AstVertex v = (AstVertex) edge.jjtGetChild(1);
            addAdjacency(u, v, vertexSet);
            addAdjacency(v, u, vertexSet);
        }

    }
    
    public VertexSet getVertexSet() {
        return vertexSet;
    }
    
    public void print() {
        for (Vertex v : vertexSet.vertices()) {
            System.out.println(v);
        }
    }
    
    private void addAdjacency(AstVertex astFrom, AstVertex astTo, VertexSet vertexSet) {
        Vertex from = vertexSet.get(astFrom.getName());
        Vertex to = vertexSet.get(astTo.getName());
        
        if (from == null) {
            from = new Vertex(astFrom.getName());
            vertexSet.add(from);
        }
        if (to == null) {
            to = new Vertex(astTo.getName());
            vertexSet.add(to);
        }
        
        from.addAjacentVertex(to);

    }

}
