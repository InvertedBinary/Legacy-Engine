package spam;

import java.util.ArrayList;

public class spam {

	public static void main(String[] args) {
		ArrayList<Comparable> alist = new ArrayList<>();
			alist.add(new Integer(1));
			alist.add(new Integer(11));
			alist.add(new Integer(9));
			alist.add(new Integer(2));
			alist.add(new Integer(3));
			alist.add(new Integer(10));
			alist.add(new Integer(5));
			alist.add(new Integer(4));
			alist.add(new Integer(7));
			alist.add(new Integer(8));

		ArrayList<Comparable> blist = new ArrayList<>();
			blist.add(new Integer(4));
			blist.add(new Integer(5));
			blist.add(new Integer(6));
			blist.add(new Integer(7));
			blist.add(new Integer(8));
			
		ArrayList<Comparable> result = new ArrayList<>();
		recursiveMergeSort(alist);
		for (int i = 0; i < alist.size(); i++) {
			System.out.println("I: " + alist.get(i));
		}
	}
	
	public static void recursiveMergeSort(ArrayList<Comparable> a) {
		recursiveMergeSort(a, 0, a.size());
	}
	
	public static void recursiveMergeSort(ArrayList<Comparable> a, int first, int last) {
		if (a.size() < 2) return;
		int middle = (first + last) / 2;
		
		ArrayList<Comparable> l = new ArrayList<>();
		ArrayList<Comparable> r = new ArrayList<>();
		for(int i = 0; i < middle; i++) l.add(a.remove(0));
		while (a.size() > 0) r.add(a.remove(0));

		recursiveMergeSort(l);
		recursiveMergeSort(r);
		
		while (l.size() != 0 && r.size() != 0) {
			Comparable il = l.get(0);
			Comparable ir = r.get(0);
			if (il.compareTo(ir) == 1) {
				a.add(r.remove(0));
			} else {
				a.add(l.remove(0));
			}
		}
		
		a.addAll(l);
		a.addAll(r);
	}
	
	public static void merged(ArrayList<Comparable> a, ArrayList<Comparable> b, ArrayList<Comparable> c) {
		int ia = 0, ib = 0;
		while (ia < a.size() && ib < b.size()) {
			Comparable itema = a.get(ia);
			Comparable itemb = b.get(ib);
			if (itema.compareTo(itemb) == 1) {
				c.add(itemb);
				ib++;
			} else {
				c.add(itema);
				ia++;
			}
		}
		for (int i = ia; i < a.size(); i++) c.add(a.get(i));
		for (int i = ib; i < b.size(); i++) c.add(b.get(i));
	}

}
