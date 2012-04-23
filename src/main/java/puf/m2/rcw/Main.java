package puf.m2.rcw;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

import puf.m2.rcw.exception.RcwException;
import puf.m2.rcw.graph.Graph;
import puf.m2.rcw.reducedterm.parser.AstReducedTermParse;
import puf.m2.rcw.reducedterm.parser.ParseException;
import puf.m2.rcw.reducedterm.parser.ReducedTermParser;
import puf.m2.rcw.reducedterm.parser.SimpleNode;
import puf.m2.rcw.term.Term;

public class Main {

    public static void main(String[] args) throws RcwException {
        
        if (args.length == 0) {
            System.out.println("<input.txt> [output.txt]");
            return;
        }
        
        int nvertex;
        String rTerm, edgeList;
        
        PrintStream output;
        
        try{
            DataInputStream dis = new DataInputStream(new FileInputStream(args[0]));
            BufferedReader br = new BufferedReader(new InputStreamReader(dis));

            nvertex = Integer.parseInt(br.readLine());
            rTerm = br.readLine();
            edgeList = br.readLine();
            
            dis.close();
            
            if (args.length == 2) {
                output = new PrintStream(args[1]);
            } else {
                output = System.out;
            }
            
        } catch (Exception e){
            throw new RcwException(e);
        }

        
        
        Term term = constructTerm(rTerm, edgeList);
        output.println(term);

    }

    protected static Term constructTerm(String rTerm, String edgeList) throws RcwException {
        Graph graph = new Graph(edgeList);
        
        ReducedTermParser parser = new ReducedTermParser(
                new ByteArrayInputStream(rTerm.getBytes()));
        
        AstReducedTermParse node = null;
        try {
            node = parser.reducedTermParse();
        } catch (ParseException e) {
            throw new RcwException(e);
        }
        
        RelativeCliqueWidth rcw = new RelativeCliqueWidth();
        Term term = rcw.constructTerm((SimpleNode) node.jjtGetChild(0), graph.getVertexSet());
        return term;
    }
}
