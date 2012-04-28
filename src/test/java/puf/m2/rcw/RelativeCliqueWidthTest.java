package puf.m2.rcw;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStreamReader;

import static org.junit.Assert.*;
import org.junit.Test;

import puf.m2.rcw.term.Term;

public class RelativeCliqueWidthTest {
    private static final String [] DATA_TEST = {"A1.txt", "A2.txt", "A3.txt", "A4.txt", "A5.txt", "A6.txt", "A7.txt"
        , "B1.txt", "B2.txt", "B3.txt", "B4.txt", "B5.txt", "B6.txt", "B7.txt"};
    @Test
    public void testConstructSomeTerms() throws Exception {
        for (String resourceName : DATA_TEST) {
            DataInputStream dis = new DataInputStream(getClass().getResourceAsStream(resourceName));
            BufferedReader br = new BufferedReader(new InputStreamReader(dis));

            int nvertex = Integer.parseInt(br.readLine());
            String rTerm = br.readLine();
            String edgeList = br.readLine();
            String result = br.readLine();
            dis.close();
            
            Term term = Main.constructTerm(rTerm, edgeList);
            assertEquals(result, term.toString());
        }
    }

}
