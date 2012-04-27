package puf.m2.rcw;

import java.io.ByteArrayInputStream;

import org.junit.Test;

import puf.m2.rcw.edgelist.parser.AstEdgeParse;
import puf.m2.rcw.edgelist.parser.EdgeListParser;
import puf.m2.rcw.edgelist.parser.ParseException;

public class EdgeListParserTest {

    @Test
	public void test() {
        //EdgeListParser parser = new EdgeListParser(ReducedTermParserTest.class.getResourceAsStream("edgelist.txt"));
        EdgeListParser parser = new EdgeListParser(
                new ByteArrayInputStream("((2,3),(2,1),(4,3),(6,5),(4,5))".getBytes()));
        try {
            AstEdgeParse p = parser.edgeParse();
            p.dump("+");
        } catch (ParseException e) {
            
        }

    }

}
