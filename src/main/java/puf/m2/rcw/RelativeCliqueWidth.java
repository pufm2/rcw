package puf.m2.rcw;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import puf.m2.rcw.exception.RcwException;
import puf.m2.rcw.graph.Graph;
import puf.m2.rcw.graph.LabelSet;
import puf.m2.rcw.graph.Vertex;
import puf.m2.rcw.graph.VertexFamily;
import puf.m2.rcw.graph.VertexSet;
import puf.m2.rcw.operator.DisjointUnion;
import puf.m2.rcw.operator.EdgeAddition;
import puf.m2.rcw.operator.Operator;
import puf.m2.rcw.operator.Relabelling;
import puf.m2.rcw.reducedterm.parser.AstReducedTerm;
import puf.m2.rcw.reducedterm.parser.AstReducedTermParse;
import puf.m2.rcw.reducedterm.parser.AstVertex;
import puf.m2.rcw.reducedterm.parser.Node;
import puf.m2.rcw.reducedterm.parser.ParseException;
import puf.m2.rcw.reducedterm.parser.ReducedTermParser;
import puf.m2.rcw.reducedterm.parser.SimpleNode;
import puf.m2.rcw.term.APort;
import puf.m2.rcw.term.ProperTerm;
import puf.m2.rcw.term.Term;
import puf.m2.rcw.utils.SetList;

public class RelativeCliqueWidth {

    public static void main(String[] args) throws RcwException {
        //Graph graph = new Graph("((2,3),(2,1),(4,3),(6,5),(4,5))");
        Graph graph = new Graph("((1,2),(2,3),(3,4),(4,5),(5,6))");
        //graph.print();
        
        ReducedTermParser parser = new ReducedTermParser(
                new ByteArrayInputStream("oplus(oplus(oplus(oplus(1,oplus(2,3)),4),5),6)".getBytes()));
        
        AstReducedTermParse node = null;
        try {
            node = parser.reducedTermParse();
        } catch (ParseException e) {
            throw new RcwException(e);
        }
        
        RelativeCliqueWidth rcw = new RelativeCliqueWidth();
        Term term = rcw.constructTerm((SimpleNode) node.jjtGetChild(0), graph.getVertexSet());
        System.out.println(rcw.toString(term, ""));
        

    }
    
    public Term constructTerm(SimpleNode sn, VertexSet graphVs) {
        Node n1 = sn.jjtGetChild(0);
        Node n2 = sn.jjtGetChild(1);
        if (n1 instanceof AstReducedTerm) {
            return constructTerm((AstReducedTerm) n1, (AstVertex) n2, graphVs);
        } else {
            return constructTerm((AstReducedTerm) n2, (AstVertex) n1, graphVs);
        }
    }

    public Term constructTerm(SimpleNode sn, AstVertex av, VertexSet graphVs) {

    	Term snTerm = null;
        if (sn.jjtGetNumChildren() == 2) {
            
            Node n1 = sn.jjtGetChild(0);
            Node n2 = sn.jjtGetChild(1);

            if (n1 instanceof AstReducedTerm) {
                snTerm = constructTerm((AstReducedTerm) n1, (AstVertex) n2, graphVs);
            } else {
                snTerm = constructTerm((AstReducedTerm) n2, (AstVertex) n1, graphVs);
            }
        } else {

            snTerm = constructAPort((AstVertex) sn.jjtGetChild(0), graphVs, snTerm);
        }
        
        APort aPort = constructAPort(av, graphVs, snTerm);
        
        LabelSet usedlabel = LabelSet.union(snTerm.getUsedLabels(), aPort.getUsedLabels());

        snTerm.getVertexSet().cleanAdjacency();
        VertexSet termVs = VertexSet.union(snTerm.getVertexSet(), aPort.getVertexSet());
        constructHs(termVs, graphVs);
        VertexFamily partites = constructHsPartites(VertexFamily.union(snTerm.getPartites(), aPort.getPartites()));
        LabelSet gammaImage = new LabelSet();
        List<Operator> operatorList = constructOperatorList(snTerm, aPort, partites, graphVs, gammaImage);
        
        Term term = new ProperTerm(snTerm, aPort, termVs, usedlabel, gammaImage, partites, operatorList);
        //System.out.println(toString(term, ""));
        return term;
        
    }

	private APort constructAPort(AstVertex av, VertexSet graphVs, Term companyingTerm) {
		String name = av.getName();
		
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
		
		APort aPort = new APort(vs, ls, ls, partites, label);
		
		return aPort;
	}

	private void constructHs(VertexSet vs, VertexSet graphVs) {
		Map<String, VertexSet> neighbourMap = new HashMap<String, VertexSet>();
		for (Vertex v : vs.vertices()) {
		    
		    VertexSet neighbourV = neighbourMap.get(v.getName());
		    if (neighbourV == null) {
		        Vertex vInGraph = graphVs.get(v.getName());
		        neighbourV = VertexSet.subtract(vInGraph.getAdjacentVertices(), vs);
		        neighbourMap.put(v.getName(), neighbourV);
		    }
		    
			for (Vertex u : vs.vertices()) {
			    VertexSet neighbourU = neighbourMap.get(u.getName());
	            if (neighbourU == null) {
	                Vertex uInGraph = graphVs.get(u.getName());
	                neighbourU = VertexSet.subtract(uInGraph.getAdjacentVertices(), vs);
	                neighbourMap.put(u.getName(), neighbourU);
	            }
	            if (!neighbourV.equals(neighbourU)) {
	                v.addAjacentVertex(u);
	            }
			}
		}
	}
	
    private VertexFamily constructHsPartites(VertexFamily partites) {
        
        VertexFamily hsPartites = new VertexFamily();
        while (!partites.isEmpty()) {
            VertexSet vertexSet = new VertexSet();

            for (Iterator<VertexSet> iter = partites.getVertexFamily().iterator(); iter.hasNext();) {
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
    
	private List<Operator> constructOperatorList(Term term, APort aPort, VertexFamily partites, VertexSet graphVs,
	                                                LabelSet gammaImage) {
	    List<Operator> operatorList = new SetList<Operator>();
	    
	    Vertex aPortVertex = aPort.getVertexSet().toArray()[0];
	    Vertex uInGraph = graphVs.get(aPortVertex.getName());
	    for (Vertex v : term.getVertexSet().vertices()) {
	        Vertex vInGraph = graphVs.get(v.getName());
	        if (uInGraph.getAdjacentVertices().contains(vInGraph)) {
	            operatorList.add(new EdgeAddition(aPortVertex.getLabel(), v.getLabel()));
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
	    return operatorList;
	}
	
	private String toString(Term term, String st) {
	    if (term instanceof ProperTerm) {
	        ProperTerm properTerm = (ProperTerm) term;
	        st = toString(properTerm.getLeft(), st);
	        APort aPort = properTerm.getRight();
	        Vertex v = aPort.getVertexSet().toArray()[0];
	        st = "oplus(" + st + "," + aPort.getLabel() + "(" + v.getName() + ")" + ")";
	        for (Operator operator : properTerm.getOperatorList()) {
	            st = operator.toString(st);
	        }
	        return st;
	    } else {
	        APort aPort = (APort) term;
	        Vertex v = aPort.getVertexSet().toArray()[0];
	        return aPort.getLabel() + "(" + v.getName() + ")";
	    }

	}
}
