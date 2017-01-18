package comp2402a3;

import java.util.SortedSet;
import java.util.TreeSet;

/**
 * This class implements the IntervalSet interface for storing a set of disjoint
 * intervals
 *
 * @author morin
 *
 * @param <K>
 */
public class DisjointIntervalSet<K extends Comparable<K>> implements IntervalSet<K> {

    SortedSet<Interval<K>> intervals;

    public DisjointIntervalSet() {
        intervals = new TreeSet<Interval<K>>();
    }

    public boolean add(Interval<K> i) {
        // TODO: add checking to see if i overlaps anything in the set
        //       -- if so, don't add it and return false. Otherwise, add 
        // it and return true
        //boolean disjoint = true;
        SortedSet<Interval<K>> ts =
                intervals.tailSet(new Interval<K>(i.b, i.b)); // Find stuff >= [i,i) 
        if (!ts.isEmpty()) {
            Interval<K> u = ts.first(); // if it's there, it's in
            if (i.a == u.a) {
                return false;
            }								  // the first interval
            if (u.a.compareTo(i.b) < 0 && i.b.compareTo(u.b) < 0) {//u.contains(i.b)) {
                return false;
            }
        }
        ts = intervals.tailSet(new Interval<K>(i.a, i.a));
        if (!ts.isEmpty()) {
            Interval<K> u = ts.first();
            if (i.b == u.b) {
                return false;
            }
            if ((u.a.compareTo(i.b) < 0 && i.a.compareTo(u.b) < 0)) {
                return false;
            }
        }

        intervals.add(i);
        return true;
    }

    public void clear() {
        intervals.clear();
    }

    public boolean contains(K x) {
        // TODO Add code for searching here.  See Interval.main() for an example
        SortedSet<Interval<K>> ts =
                intervals.tailSet(new Interval<K>(x, x)); // Find stuff >= [i,i) 
        if (!ts.isEmpty()) {
            Interval<K> u = ts.first(); // if it's there, it's in
            // the first interval
            if (u.contains(x)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        DumbIntervalSet.runTests(new DisjointIntervalSet<Integer>());

        IntervalSet<Integer> is;

        is = new DisjointIntervalSet<Integer>();
        //touching both boundary
        is = new DisjointIntervalSet<Integer>();
        assert (is.add(new Interval<Integer>(0, 2)));
        assert (is.add(new Interval<Integer>(4, 5)));
        assert (is.add(new Interval<Integer>(2, 4)));


        is = new DisjointIntervalSet<Integer>();
        //touching first boundary
        assert (is.add(new Interval<Integer>(0, 2)));
        assert (is.add(new Interval<Integer>(4, 6)));
        assert (is.add(new Interval<Integer>(2, 3)));


        is = new DisjointIntervalSet<Integer>();
        //touching second boundary
        assert (is.add(new Interval<Integer>(0, 2)));
        assert (is.add(new Interval<Integer>(4, 6)));
        assert (is.add(new Interval<Integer>(3, 4)));


        is = new DisjointIntervalSet<Integer>();
        //overlapping first
        assert (is.add(new Interval<Integer>(0, 2)));
        assert (is.add(new Interval<Integer>(4, 6)));
        assert (!is.add(new Interval<Integer>(1, 3)));


        is = new DisjointIntervalSet<Integer>();
        //overlapping first and ending its boundary
        assert (is.add(new Interval<Integer>(0, 2)));
        assert (is.add(new Interval<Integer>(4, 6)));
        assert (!is.add(new Interval<Integer>(1, 2)));


        is = new DisjointIntervalSet<Integer>();
        //overlapping first and ending its previous boundary
        assert (is.add(new Interval<Integer>(0, 2)));
        assert (is.add(new Interval<Integer>(4, 6)));
        assert (!is.add(new Interval<Integer>(0, 3)));





        is = new DisjointIntervalSet<Integer>();
        //overlapping second
        assert (is.add(new Interval<Integer>(0, 2)));
        assert (is.add(new Interval<Integer>(4, 6)));
        assert (!is.add(new Interval<Integer>(3, 5)));


        is = new DisjointIntervalSet<Integer>();
        //overlapping second until boundary
        assert (is.add(new Interval<Integer>(0, 2)));
        assert (is.add(new Interval<Integer>(4, 6)));
        assert (is.add(new Interval<Integer>(3, 4)));


        is = new DisjointIntervalSet<Integer>();
        //overlapping second until next boundary
        assert (is.add(new Interval<Integer>(0, 2)));
        assert (is.add(new Interval<Integer>(4, 6)));
        assert (!is.add(new Interval<Integer>(3, 6)));



        is = new DisjointIntervalSet<Integer>();
        //overlapping two
        assert (is.add(new Interval<Integer>(0, 2)));
        assert (is.add(new Interval<Integer>(4, 6)));
        assert (!is.add(new Interval<Integer>(1, 5)));


        is = new DisjointIntervalSet<Integer>();
        //adding same first
        assert (is.add(new Interval<Integer>(0, 2)));
        assert (is.add(new Interval<Integer>(4, 6)));
        assert (!is.add(new Interval<Integer>(0, 2)));


        is = new DisjointIntervalSet<Integer>();
        //adding same second
        assert (is.add(new Interval<Integer>(0, 2)));
        assert (is.add(new Interval<Integer>(4, 6)));
        assert (!is.add(new Interval<Integer>(4, 6)));


        is = new DisjointIntervalSet<Integer>();
        //overlapping two interval boundaries
        assert (is.add(new Interval<Integer>(0, 2)));
        assert (is.add(new Interval<Integer>(4, 6)));
        assert (!is.add(new Interval<Integer>(0, 6)));


        is = new DisjointIntervalSet<Integer>();
        //overlapping first start to second end interval boundaries
        assert (is.add(new Interval<Integer>(0, 2)));
        assert (is.add(new Interval<Integer>(4, 6)));
        assert (!is.add(new Interval<Integer>(0, 4)));


        is = new DisjointIntervalSet<Integer>();
        //overlapping first end to second start boundaries
        assert (is.add(new Interval<Integer>(0, 2)));
        assert (is.add(new Interval<Integer>(4, 6)));
        assert (!is.add(new Interval<Integer>(2, 6)));



    }
}
