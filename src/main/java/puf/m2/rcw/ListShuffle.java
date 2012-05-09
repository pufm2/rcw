package puf.m2.rcw;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListShuffle {

	public static void main(String[] args) {
		List<Integer> intList = new ArrayList<Integer>();
		for (int i = 1; i <= 30; i++) {
			intList.add(i);
		}
		Collections.shuffle(intList);
		
		for (int i : intList) {
			System.out.print(i + ", ");
		}
	}

}
