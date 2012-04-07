package puf.m2.rcw.graph;

import puf.m2.rcw.edgelist.parser.AstEdge;
import puf.m2.rcw.edgelist.parser.AstEdgeParse;
import puf.m2.rcw.edgelist.parser.AstVertex;


public class AdjacencyMatrix {
    Boolean[][] matrix;
    public AdjacencyMatrix(int n, AstEdgeParse astedgeParse) {
        matrix = new Boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = false;
            }
        }
        for (int i = 0; i < astedgeParse.jjtGetNumChildren(); i++) {
            AstEdge edge = (AstEdge) astedgeParse.jjtGetChild(i);
            AstVertex u = (AstVertex) edge.jjtGetChild(0);
            AstVertex v = (AstVertex) edge.jjtGetChild(1);
            matrix[Integer.parseInt(u.getName()) - 1][Integer.parseInt(v.getName()) - 1] = true;
        }
    }
}
