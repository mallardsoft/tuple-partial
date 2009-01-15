package com.goodworkalan.tuple.partial;

import com.mallardsoft.tuple.Tuple;

/**
 * Performs type-safe partial comparisons of one smaller Java Tuple tuple
 * against a larger one.
 * <p>
 * Partial is a factory for creating <code>Comparable</code>
 * implementations that contain a partial tuple and compare the fields
 * in that tuple against a full tuple. The parital tuple has idenditical
 * types as the parallel fields in the full tuple. The comparison
 * between the parital tuple and the full tuple is type safe.
 * <p>
 * This library was developed to create a type safe implementation of of
 * searches against indexes in an object database, where partial
 * searches are commonly desired. For example, if you index a person
 * object by last name then first name, you may then want to take
 * advantage of the ordering to use the compound index to search using
 * only a last name. You would simply ignore the first name when
 * searching the index.
 * <p>
 * Using the last name first as an example...
 * <code><pre>
 * Partial&lt;Pair&lt;String, String&gt;, Single&lt;String&gt;&gt; byLastName =
 *      Partial.oneOf(Partial.&lt;String, String&gt;pair());
 * Comparable&lt;Pair&lt;String, String&lt;&gt; comparable = byLastName.comparable(Tuple.from("Gutierrez"));
 * assert comparable.compareTo(Tuple.from("Gutierrez", "Alan")) == 0;
 * </pre></code>
 * <p>
 * When a partial tuple matches the partial fields of a full tuple, the
 * comparitor returns 0. If you'd prefer that it return -1 to indicate
 * that the value is less than the full tuple, you can wrap the
 * comparitor using the {@link #forZero} wrapper to specify a different
 * value for a partial match.
 * 
 * @author Alan Gutierrez
 * 
 * @param <FullTuple> The full tuple to compare to.
 * @param <PartialTuple> The partial tuple to match.
 */
public class Partial<FullTuple extends Tuple<?, ?>, PartialTuple extends Tuple<?, ?>>
{
    private ComparableServer<PartialTuple, FullTuple> comparableServer;
    
    Partial(ComparableServer<PartialTuple, FullTuple> comparableServer)
    {
        this.comparableServer = comparableServer;
    }

    /**
     * Creates a <code>Comparable</code> that will compare the fields of
     * the partial tuple against instances of the full tuple.
     *
     * @param partial An instance of the parital tuple.
     * @return A comparable that will compare against the full tuple.
     */
    public Comparable<FullTuple> compare(PartialTuple partial)
    {
        return comparableServer.comparable(partial);
    }
}
