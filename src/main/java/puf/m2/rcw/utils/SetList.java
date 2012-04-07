package puf.m2.rcw.utils;

import java.util.ArrayList;

public class SetList<E> extends ArrayList<E> {

    private static final long serialVersionUID = -7893627388681969034L;

    public boolean add(E e) {
        if (contains(e)) {
            return false;
        }
        return super.add(e);
    }

}
