package puf.m2.rcw;

import java.io.ByteArrayInputStream;

import puf.m2.rcw.reducedterm.parser.AstReducedTermParse;
import puf.m2.rcw.reducedterm.parser.ParseException;
import puf.m2.rcw.reducedterm.parser.ReducedTermParser;

public class ReducedTermParserTest {

    public static void main(String[] args) throws ParseException  {
        //ReducedTermParser parser = new ReducedTermParser(ReducedTermParserTest.class.getResourceAsStream("test.txt"));
        ReducedTermParser parser = new ReducedTermParser(
                new ByteArrayInputStream("oplus(oplus(oplus(oplus(1,oplus(2,3)),4),5),6)".getBytes()));
        AstReducedTermParse node = parser.reducedTermParse();
        node.dump("");

    }

}
