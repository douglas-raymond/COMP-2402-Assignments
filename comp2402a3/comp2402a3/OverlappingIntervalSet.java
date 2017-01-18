package comp2402a3;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * This class implements the IntervalSet interface for storing a set of
 * intervals, which may or may not be disjoint.
 *
 * @author morin
 *
 * @param <K>
 */
public class OverlappingIntervalSet<K extends Comparable<K>> implements IntervalSet<K> {

    SortedSet<Interval<K>> intervals;

    public OverlappingIntervalSet() {
        intervals = new TreeSet<Interval<K>>();
    }

    public boolean isDisjoint(Interval<K> i) {
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
        return true;
    }
    public boolean add(Interval<K> i) {
        // TODO: Add code here
        if (isDisjoint(i)) {
            intervals.add(i);
            return true;
        } else {
            SortedSet<Interval<K>> front =
                    intervals.tailSet(new Interval<K>(i.a, i.a));
            SortedSet<Interval<K>> back =
                    intervals.tailSet(new Interval<K>(i.b, i.b));
            if (front.isEmpty() && back.isEmpty()) {
                intervals.add(i);
                return true;
            }
            
            if (!front.isEmpty()){
                return trimOrExtendOverlappedIntervals(front,i);
            } 
            
            if (!back.isEmpty()){
                return trimOrExtendOverlappedIntervals(back,i);
            } 
            return true;
        }
    }
    public boolean trimOrExtendOverlappedIntervals(SortedSet<Interval<K>> intr,Interval<K> i){
        for (Interval<K> in : intr) {
                    if (i.compareTo(in) == -1) {
                        intervals.add(i);
                        return true;
                    }
                    if (i.compareTo(in) == 0) {
                        if (i.a.compareTo(in.a) < 0 && in.contains(i.b)) {
                            in.a = i.a;
                            return true;
                        } else if (in.contains(i.a) && i.b.compareTo(in.b) > 0) {
                            i.a = in.b;
                            continue;
                        } else if (i.a.compareTo(in.a) < 0 && i.b.compareTo(in.b) == 0) {
                            in.a = i.a;
                            return true;
                        } else if (i.a.compareTo(in.a) == 0 && i.b.compareTo(in.b) > 0) {
                            i.a = in.b;
                            continue;
                        } else if (i.a.compareTo(in.a) == 0 && i.b.compareTo(in.b) == 0) {
                            return true;
                        } else if (i.a.compareTo(in.a) < 0 && i.b.compareTo(in.b) > 0) {
                            in.a = i.a;
                            i.a = in.b;
                            continue;

                        } else if ((in.contains(i.a) && in.contains(i.b)) || (in.contains(i.a) && in.b == i.b) || (in.a == i.a && in.contains(i.b))) {
                            return true;
                        }
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
        // run some tests for disjoint intervals
        DumbIntervalSet.runTests(new OverlappingIntervalSet<Integer>());

        // TODO: Add some of your own tests here to test overlapping intervals
        //       Try borrowing some code from DumbIntervalSet.runTests()

        IntervalSet<Integer> is;

        is = new OverlappingIntervalSet<Integer>();
        //touching both boundary
        is = new OverlappingIntervalSet<Integer>();
        assert (is.add(new Interval<Integer>(0, 2)));
        assert (is.add(new Interval<Integer>(4, 5)));
        assert (is.add(new Interval<Integer>(2, 4)));


        is = new OverlappingIntervalSet<Integer>();
        //touching first boundary
        assert (is.add(new Interval<Integer>(0, 2)));
        assert (is.add(new Interval<Integer>(4, 6)));
        assert (is.add(new Interval<Integer>(2, 3)));


        is = new OverlappingIntervalSet<Integer>();
        //touching second boundary
        assert (is.add(new Interval<Integer>(0, 2)));
        assert (is.add(new Interval<Integer>(4, 6)));
        assert (is.add(new Interval<Integer>(3, 4)));

        is = new OverlappingIntervalSet<Integer>();
        //overlapping second until boundary
        assert (is.add(new Interval<Integer>(0, 2)));
        assert (is.add(new Interval<Integer>(4, 6)));
        assert (is.add(new Interval<Integer>(3, 4)));

        ///////////////




        is = new OverlappingIntervalSet<Integer>();
        //overlapping first
        assert (is.add(new Interval<Integer>(0, 2)));
        assert (is.add(new Interval<Integer>(4, 6)));
        assert (is.add(new Interval<Integer>(1, 3)));
        assert (is.contains(0) && is.contains(2) && !is.contains(3) && is.contains(4) && !is.contains(6));



        is = new OverlappingIntervalSet<Integer>();
        //overlapping first and ending its boundary
        assert (is.add(new Interval<Integer>(0, 2)));
        assert (is.add(new Interval<Integer>(4, 6)));
        assert (is.add(new Interval<Integer>(1, 2)));


        is = new OverlappingIntervalSet<Integer>();
        //overlapping first and ending its previous boundary
        assert (is.add(new Interval<Integer>(0, 2)));
        assert (is.add(new Interval<Integer>(4, 6)));
        assert (is.add(new Interval<Integer>(0, 3)));
        assert (is.contains(0) && is.contains(2) && !is.contains(3) && is.contains(4) && !is.contains(6));





        is = new OverlappingIntervalSet<Integer>();
        //overlapping second
        assert (is.add(new Interval<Integer>(0, 2)));
        assert (is.add(new Interval<Integer>(4, 6)));
        assert (is.add(new Interval<Integer>(3, 5)));
        assert (is.contains(0) && !is.contains(2) && is.contains(3) && is.contains(5) && !is.contains(6));





        is = new OverlappingIntervalSet<Integer>();
        //overlapping second until next boundary
        assert (is.add(new Interval<Integer>(0, 2)));
        assert (is.add(new Interval<Integer>(4, 6)));
        assert (is.add(new Interval<Integer>(3, 6)));
        assert (is.contains(0) && !is.contains(2) && is.contains(3) && is.contains(4) && !is.contains(6));




        is = new OverlappingIntervalSet<Integer>();
        //overlapping two
        assert (is.add(new Interval<Integer>(0, 2)));
        assert (is.add(new Interval<Integer>(4, 6)));
        assert (is.add(new Interval<Integer>(1, 5)));
        assert (is.contains(0) && is.contains(2) && is.contains(3) && is.contains(5) && !is.contains(6));


        is = new OverlappingIntervalSet<Integer>();
        //adding same first
        assert (is.add(new Interval<Integer>(0, 2)));
        assert (is.add(new Interval<Integer>(4, 6)));
        assert (is.add(new Interval<Integer>(0, 2)));
        assert (is.contains(0) && !is.contains(2) && is.contains(4) && !is.contains(6));


        is = new OverlappingIntervalSet<Integer>();
        //adding same second
        assert (is.add(new Interval<Integer>(0, 2)));
        assert (is.add(new Interval<Integer>(4, 6)));
        assert (is.add(new Interval<Integer>(4, 6)));
        assert (is.contains(0) && !is.contains(2) && is.contains(4) && !is.contains(6));


        is = new OverlappingIntervalSet<Integer>();
        //overlapping two interval boundaries
        assert (is.add(new Interval<Integer>(0, 2)));
        assert (is.add(new Interval<Integer>(4, 6)));
        assert (is.add(new Interval<Integer>(0, 6)));
        assert (is.contains(0) && is.contains(2) && is.contains(3) && is.contains(5) && !is.contains(6));


        is = new OverlappingIntervalSet<Integer>();
        //overlapping first start to second end interval boundaries
        assert (is.add(new Interval<Integer>(0, 2)));
        assert (is.add(new Interval<Integer>(4, 6)));
        assert (is.add(new Interval<Integer>(0, 4)));
        assert (is.contains(0) && is.contains(2) && is.contains(3) && is.contains(4) && is.contains(5) && !is.contains(6));


        is = new OverlappingIntervalSet<Integer>();
        //overlapping first end to second start boundaries
        assert (is.add(new Interval<Integer>(0, 2)));
        assert (is.add(new Interval<Integer>(4, 6)));
        assert (is.add(new Interval<Integer>(2, 6)));
        assert (is.contains(0) && is.contains(2) && is.contains(3) && is.contains(4) && is.contains(5) && !is.contains(6));

        is = new OverlappingIntervalSet<Integer>();
        //overlapping two large set
        assert (is.add(new Interval<Integer>(0, 2)));
        assert (is.add(new Interval<Integer>(4, 6)));
        assert (is.add(new Interval<Integer>(7, 8)));
        assert (is.add(new Interval<Integer>(8, 9)));
        assert (is.add(new Interval<Integer>(9, 10)));
        assert (is.add(new Interval<Integer>(0, 10)));
        assert (is.contains(0) && is.contains(2) && is.contains(3) && is.contains(6) && is.contains(8) && is.contains(9) && !is.contains(10));

        is = new OverlappingIntervalSet<Integer>();
        //overlapping two interval boundaries
        assert (is.add(new Interval<Integer>(0, 2)));
        assert (is.add(new Interval<Integer>(4, 6)));
        assert (is.add(new Interval<Integer>(7, 8)));
        assert (is.add(new Interval<Integer>(8, 9)));
        assert (is.add(new Interval<Integer>(9, 10)));
        assert (is.add(new Interval<Integer>(0, 10)));
        assert (is.add(new Interval<Integer>(10, 12)));
        assert (is.add(new Interval<Integer>(13, 15)));
        assert (is.contains(0) && is.contains(2) && is.contains(4) && is.contains(6) && is.contains(7) && is.contains(8) && is.contains(9));
        assert (is.contains(10) && !is.contains(12) && is.contains(13) && is.contains(14) && !is.contains(15));
        
        is = new OverlappingIntervalSet<Integer>();
        //overlapping two interval boundaries
        assert (is.add(new Interval<Integer>(4, 6)));
        assert (is.add(new Interval<Integer>(7, 8)));
        assert (is.add(new Interval<Integer>(8, 9)));
        assert (is.add(new Interval<Integer>(9, 11)));
        assert (is.add(new Interval<Integer>(0, 10)));
        assert (is.contains(0) && is.contains(2) && is.contains(4) && is.contains(6) && is.contains(7) && is.contains(8) && is.contains(9) && is.contains(10) && !is.contains(11));
    }
}
