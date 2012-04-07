package puf.m2.rcw.graph;

import java.util.ArrayList;
import java.util.List;

public class LabelSet {
    
    public static final LabelSet AB_LS = createAlphabeticalLabelSet();
    public static final LabelSet EMPTY_LS = new LabelSet();

    private List<Character> labelList;
    
    public LabelSet() {
        labelList = new ArrayList<Character>();
    }
    
    public LabelSet(LabelSet ls) {
        labelList = new ArrayList<Character>(ls.labelList);
    }

    public void add(char label) {
        int i = 0;
        for (; i < labelList.size(); i++) {
            if (labelList.get(i) > label) {
                break;
            } else if (labelList.get(i) == label) {
                return;
            }
        }
        labelList.add(i, label);
    }
    
    public void union(LabelSet ls) {
        for (Character label : ls.labelList) {
            add(label);
        }
    }
    
    public static LabelSet union(LabelSet ls1,LabelSet ls2) {
        LabelSet ls = new LabelSet();
        
        for (Character label : ls1.labelList) {
            ls.add(label);
        }
        
        for (Character label : ls2.labelList) {
            ls.add(label);
        }
        
        return ls;
    }
    
    public static LabelSet subtract(LabelSet ls1, LabelSet ls2) {
        LabelSet ls = new LabelSet();
        for (Character label : ls1.labelList) {
            if (!ls2.labelList.contains(label)) {
                ls.add(label);
            }
        }
        
        return ls;
    }
    
    public char extractOne() {
        try {
            return labelList.remove(0);
        } catch (RuntimeException re) {
            throw re;
        }
    }
    
    public static LabelSet createAlphabeticalLabelSet() {
        LabelSet ls = new LabelSet();
        for (char c = 'a'; c <= 'z'; c++) {
            ls.add(c);
        }
        return ls;
    }
    
}
