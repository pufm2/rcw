package puf.m2.rcw;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import puf.m2.rcw.graph.LabelSet;
import puf.m2.rcw.graph.Vertex;
import puf.m2.rcw.graph.VertexFamily;
import puf.m2.rcw.graph.VertexSet;
import puf.m2.rcw.operator.EdgeAddition;
import puf.m2.rcw.operator.Operator;
import puf.m2.rcw.operator.Relabelling;
import puf.m2.rcw.reducedterm.parser.AstReducedTerm;
import puf.m2.rcw.reducedterm.parser.AstVertex;
import puf.m2.rcw.reducedterm.parser.Node;
import puf.m2.rcw.reducedterm.parser.SimpleNode;
import puf.m2.rcw.term.Port;
import puf.m2.rcw.term.ProperTerm;
import puf.m2.rcw.term.Term;
import puf.m2.rcw.utils.SetList;

public class RelativeCliqueWidth {

    public static Term constructTerm(SimpleNode sn, VertexSet graphVs) {
        Node n1 = sn.jjtGetChild(0);
        Node n2 = sn.jjtGetChild(1);
        if (n1 instanceof AstReducedTerm) {
            return constructTerm((AstReducedTerm) n1, (AstVertex) n2, graphVs);
        } else {
            return constructTerm((AstReducedTerm) n2, (AstVertex) n1, graphVs);
        }
    }

    public static Term constructTerm(String rTerm, VertexSet graphVs) {
        rTerm = rTerm.replaceAll("\\s+", "");
        StringTokenizer tokenizer = new StringTokenizer(rTerm, ",");
        
        Term term = null;
        if (tokenizer.hasMoreTokens()) {
            term = constructPort(tokenizer.nextToken(), graphVs, null);
            
            while (tokenizer.hasMoreTokens()) {
                Port port = constructPort(tokenizer.nextToken(), graphVs, term);
                
                term = constructTerm(term, port, graphVs);
                System.out.println(term);
            }
        }
        
        return term;
    }
    
    public static Term constructTerm(SimpleNode sn, AstVertex av, VertexSet graphVs) {

        Term snTerm = null;
        if (sn.jjtGetNumChildren() == 2) {

            Node n1 = sn.jjtGetChild(0);
            Node n2 = sn.jjtGetChild(1);

            if (n1 instanceof AstReducedTerm) {
                snTerm = constructTerm((AstReducedTerm) n1, (AstVertex) n2,
                        graphVs);
            } else {
                snTerm = constructTerm((AstReducedTerm) n2, (AstVertex) n1,
                        graphVs);
            }
        } else {

            snTerm = constructPort((AstVertex) sn.jjtGetChild(0), graphVs,
                    snTerm);
        }

        Port port = constructPort(av, graphVs, snTerm);
        Term term = constructTerm(snTerm, port, graphVs);
        System.out.println(term);
        return term;

    }

    private static Term constructTerm(Term term, Port addedPort, VertexSet graphVs) {
        LabelSet usedlabel = LabelSet.union(term.getUsedLabels(),
                addedPort.getUsedLabels());

        term.getVertexSet().cleanAdjacency();
        VertexSet termVs = VertexSet.union(term.getVertexSet(),
                addedPort.getVertexSet());
        constructHs(termVs, graphVs);
        VertexFamily partites = constructHsPartites(VertexFamily.union(
                term.getPartites(), addedPort.getPartites()));
        LabelSet gammaImage = new LabelSet();
        List<Operator> operatorList = constructOperatorList(term, addedPort,
                partites, graphVs, gammaImage);

        term = new ProperTerm(term, addedPort, termVs, usedlabel,
                gammaImage, partites, operatorList);
        return term;
    }
    
    private static Port constructPort(AstVertex av, VertexSet graphVs,
            Term companyingTerm) {

        return constructPort(av.getName(), graphVs, companyingTerm);
    }
    
    private static Port constructPort(String name, VertexSet graphVs,
            Term companyingTerm) {
        
        VertexSet vs = new VertexSet();
        Vertex v = graphVs.get(name).clone();

        LabelSet gammaImage = null;
        if (companyingTerm != null) {
            gammaImage = companyingTerm.getGammaImage();
        } else {
            gammaImage = LabelSet.EMPTY_LS;
        }

        char label = LabelSet.subtract(LabelSet.AB_LS, gammaImage).extractOne();
        v.setLabel(label);
        vs.add(v);

        LabelSet ls = new LabelSet();
        ls.add(label);

        VertexFamily partites = new VertexFamily();
        partites.add(vs);

        Port port = new Port(vs, ls, ls, partites, label);

        return port;
    }

    private static void constructHs(VertexSet vs, VertexSet graphVs) {
        Map<String, VertexSet> neighbourMap = new HashMap<String, VertexSet>();
        for (Vertex v : vs.vertices()) {

            VertexSet neighbourV = neighbourMap.get(v.getName());
            if (neighbourV == null) {
                Vertex vInGraph = graphVs.get(v.getName());
                neighbourV = VertexSet.subtract(vInGraph.getAdjacentVertices(),
                        vs);
                neighbourMap.put(v.getName(), neighbourV);
            }

            for (Vertex u : vs.vertices()) {
                VertexSet neighbourU = neighbourMap.get(u.getName());
                if (neighbourU == null) {
                    Vertex uInGraph = graphVs.get(u.getName());
                    neighbourU = VertexSet.subtract(
                            uInGraph.getAdjacentVertices(), vs);
                    neighbourMap.put(u.getName(), neighbourU);
                }
                if (!neighbourV.equals(neighbourU)) {
                    v.addAjacentVertex(u);
                }
            }
        }
    }

    private static VertexFamily constructHsPartites(VertexFamily partites) {

        VertexFamily hsPartites = new VertexFamily();
        while (!partites.isEmpty()) {
            VertexSet vertexSet = new VertexSet();

            for (Iterator<VertexSet> iter = partites.getVertexFamily()
                    .iterator(); iter.hasNext();) {
                VertexSet vs = iter.next();
                Vertex v = (Vertex) vs.vertices().toArray()[0];
                boolean contained = true;
                for (Vertex u : vertexSet.vertices()) {
                    if (v.isAdjacentTo(u)) {
                        contained = false;
                        break;
                    }
                }
                if (contained) {
                    vertexSet.union(vs);
                    iter.remove();
                }

            }
            hsPartites.add(vertexSet);

        }
        return hsPartites;
    }

    private static List<Operator> constructOperatorList(Term term, Port port,
            VertexFamily partites, VertexSet graphVs, LabelSet gammaImage) {
        List<Operator> operatorList = new SetList<Operator>();

        Vertex portVertex = port.getVertexSet().toArray()[0];
        Vertex uInGraph = graphVs.get(portVertex.getName());
        for (Vertex v : term.getVertexSet().vertices()) {
            Vertex vInGraph = graphVs.get(v.getName());
            if (uInGraph.getAdjacentVertices().contains(vInGraph)) {
                operatorList.add(new EdgeAddition(portVertex.getLabel(), v.getLabel()));
            }
        }

        for (VertexSet vs : partites.getVertexFamily()) {
            LabelSet ls = vs.getLabelSet();
            char label = ls.extractOne();
            for (Vertex v : vs.vertices()) {
                if (label != v.getLabel()) {
                    operatorList.add(new Relabelling(v.getLabel(), label));
                    v.setLabel(label);
                }
            }

            gammaImage.add(label);

        }
        
        Collections.sort(operatorList, Operator.COMPARATOR);
        return operatorList;
    }

}
