package com.goodworkalan.tuple.partial;

import com.mallardsoft.tuple.Tuple;

/**
 * A structure that captures the type declarations necessary to construct an
 * instance of {@link Partial} named tuple comparator builder.
 * 
 * @author Alan Gutierrez
 * 
 * @param <Named>
 *            The type of the named tuple for the full tuple, one of the named
 *            tuple types from the Java Tuple library. (i.e Single, Pair,
 *            Triple, etc.)
 * @param <First>
 *            The first element in the tuple type tree of both the full and
 *            partial tuples.
 * @param <PartialRest>
 *            The remaining element and tuple type tree of the partial tuple.
 */
public class PartialType<
    Named extends Tuple<First, PartialRest>,
    First extends Comparable<First>,
    PartialRest extends Comparable<PartialRest>>
{
}
