package com.goodworkalan.tuple.partial;

import com.mallardsoft.tuple.Tuple;
import com.mallardsoft.tuple.Variable;

/**
 * A type structure that represents a field shared by the full and partial
 * tuples. The test type node implements the logic to compare a field common to
 * the full and partial tuples. This class is used to construct the tree-like
 * type structure used to define the partial comparator builder. The tree-like
 * type structure must be built by prepending the tuple field types in reverse
 * order. The <code>Test</code> type node is prepended after the
 * <code>Tail</code> type nodes to describe the fields in common, while the
 * <code>Tail</code> type nodes describe the fields that are only the full
 * tuple.
 * 
 * @author Alan Gutierrez
 * @param <First>
 *            The first type field in the type tree.
 * @param <PartialRest>
 *            The remainder of the tree-like type structure in the partial
 *            tuple.
 * @param <Rest>
 *            The remainder of the tree-like type structure in the full tuple.
 */
public class Shared<First extends Comparable<First>,
                    PartialRest extends Comparable<PartialRest>,
                    Rest extends Comparable<Rest>>
implements ComparableServer<Tuple<First, PartialRest>, Tuple<First, Rest>>
{
    ComparableServer<PartialRest, Rest> newComparable;
    
    Shared(ComparableServer<PartialRest, Rest> newComparable)
    {
        this.newComparable = newComparable;
    }
    
    /**
     * Prepend a field that is shared by both the full and partial tuples to the
     * tree-like structure, beginning the portion of the type structure that
     * describes the fields shared by both the full and partial tuples.
     * 
     * @param <T>
     *            Type tuple field type.
     * @return A common node containing the specified field type followed by the
     *         rest of the fields in the comparison.
     */
    public <T extends Comparable<T>> Shared<T, Tuple<First, PartialRest>, Tuple<First ,Rest>> shared()
    {
        return new Shared<T, Tuple<First, PartialRest>, Tuple<First, Rest>>(this);
    }

    /**
     * Create a comparable that will compare the field common to both the full
     * and partial tuples that and then pass the rest of the full and partial
     * tuples to the next test in the tree-like structure.
     * 
     * @param partial
     *            The partial structure.
     * @return A negative integer, zero, or a positive integer as this field is
     *         less than, equal to, or greater than the field in the specified
     *         partial tuple.
     */
    public Comparable<Tuple<First, Rest>> comparable(Tuple<First, PartialRest> partial)
    {
        Variable<First> v1 = new Variable<First>();
        final PartialRest r1 = partial.extract(v1);
        final First f1 = v1.get();
        return new Comparable<Tuple<First,Rest>>()
        {
            public int compareTo(Tuple<First, Rest> o)
            {
                Variable<First> v2 = new Variable<First>();
                Rest r2 = o.extract(v2);
                First f2 = v2.get();
                if (f1 == null)
                {
                    if (f2 == null)
                    {
                        return newComparable.comparable(r1).compareTo(r2);
                    }
                    return -1;
                }
                else if (f2 == null)
                {
                    return 1;
                }
                int compare = f1.compareTo(v2.get());
                if (compare == 0)
                {
                    return newComparable.comparable(r1).compareTo(r2);
                }
                return compare;
            }
        };
    }
}
