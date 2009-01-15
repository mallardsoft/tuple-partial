package com.goodworkalan.tuple.partial;

import com.mallardsoft.tuple.End;
import com.mallardsoft.tuple.Tuple;

/**
 * A type structure that represents a field in the full tuple that is not in the
 * partial tuple. The tail type node implements the logic to terminate
 * comparison and return zero to indicate a successful partial match.
 * <p>
 * The partial comparator builder is defined by a type tree where no operation
 * type nodes, tails are prepended to create the un-compared fields of the full
 * tuple. The compared fields of the partial comparitor builder are specified by
 * calling the {@link #shared()} method and prepending the common fields between
 * the full and partial tuples.
 * 
 * @author Alan Gutierrez
 * 
 * @param <First>
 *            The first type field in the type tree.
 * @param <Rest>
 *            The remainder of the type tree.
 */
public class Ignore<First extends Comparable<First>, Rest extends Comparable<Rest>>
        implements ComparableServer<End, Tuple<First, Rest>>,
        Comparable<Tuple<First, Rest>>
{
    /**
     * Return a comparable that will always return zero.
     * 
     * @param end An object indicating the end of a tuple.
     */
    public Comparable<Tuple<First, Rest>> comparable(End end)
    {
        return this;
    }

    /**
     * Always return zero indicating success.
     * 
     * @param tuple The rest of the full tuple.
     */
    public int compareTo(Tuple<First, Rest> tuple)
    {
        return 0;
    }

    /**
     * Prepend a field to the tree-like structure that is only in the full tuple
     * and not in the partial tuple.
     * 
     * @param <T>
     *            Type tuple field type.
     * @return A tail node starting with the specified field type and followed
     *         by the tree-like structure describing the fields only in the full
     *         tuple and not in the partial tuple.
     */
    public <T extends Comparable<T>> Ignore<T, Tuple<First, Rest>> ignore()
    {
        return new Ignore<T, Tuple<First, Rest>>();
    }

    /**
     * Prepend a field that is shared by both the full and partial tuples to the
     * tree-like structure, beginning the portion of the type structure that
     * describes the fields shared by both the full and partial tuples.
     * 
     * @param <T>
     *            Type tuple field type.
     * @return A common node containing the specified field type followed by the
     *         fields found only in the full tuple.
     */
    public <T extends Comparable<T>> Shared<T, End, Tuple<First, Rest>> shared()
    {
        return new Shared<T, End, Tuple<First, Rest>>(this);
    }
}
