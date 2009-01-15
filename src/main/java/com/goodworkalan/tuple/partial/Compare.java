package com.goodworkalan.tuple.partial;

import com.mallardsoft.tuple.Decuple;
import com.mallardsoft.tuple.End;
import com.mallardsoft.tuple.Nonuple;
import com.mallardsoft.tuple.Octuple;
import com.mallardsoft.tuple.Pair;
import com.mallardsoft.tuple.Quadruple;
import com.mallardsoft.tuple.Quintuple;
import com.mallardsoft.tuple.Septuple;
import com.mallardsoft.tuple.Sextuple;
import com.mallardsoft.tuple.Single;
import com.mallardsoft.tuple.Triple;
import com.mallardsoft.tuple.Tuple;

/**
 * Static methods for the composition of partial tuple comparator builders. 
 *
 * @author Alan Gutierrez
 */
public class Compare
{
    /**
     * Used to create the tuple comparator using the reversed prepending
     * construction. This is the tail of a partial comparator builder,
     * which is a comparator that always returns 0. You can prepend
     * more noop tail comparators, or you can prepend working
     * comparators using the {@link Ignore#shared()} method of {@link
     * Ignore}. Thereafter you can prepend more tests using the {@link
     * Shared#shared()} method of the {@link Shared} object.
     * <p>
     * The reversed construction order necessary to create the proper
     * type tree structure. The static builder methods call this method
     * to create the structures.
     *
     * @return THe tail of a partial comparator builder.
     */
    public static <T extends Comparable<T>> Ignore<T, End> ignore()
    {
        return new Ignore<T, End>();
    }
    
    /**
     * Used to return a value other than zero when a partial match
     * succeeds. When a partial tuple matches the partial fields of a
     * full tuple, the comparitor returns 0. If you'd prefer that it
     * return -1 to indicate that the value is less than the full tuple,
     * you can wrap the comparitor using the {@link #forZero} wrapper to
     * specify a different value for a partial match.
     *
     * @param comparable The partial comparable.
     * @param forZero The value to return instead of zero when the
     * comparable returns zero.
     * @return A comparable that returns the for zero value when the
     * delegate comparator returns zero.
     */
    public static <T> Comparable<T> forZero(final Comparable<T> comparable, final int forZero)
    {
        return new Comparable<T>()
        {
            public int compareTo(T o)
            {
                int compare = comparable.compareTo(o);
                return compare == 0 ? forZero : compare;
            }
        };
    }

    /**
     * Constructs a partial comparable builder that will compare the first field
     * of a <code>Pair</code> or larger against a <code>Single</code>
     * 
     * @typedefs A structure that captures type type information necessary to
     *           create the partial comparator builder.
     * 
     * @param <A>
     *            The first parameter in the <code>Pair</code> or larger full
     *            tuple shared by the <code>Single</code>.
     * @param <B>
     *            The second parameter in the <code>Pair</code> or larger full
     *            tuple (not shared by the <code>Single</code>.)
     * @param <Rest>
     *            The rest of the full tuple.
     * @param <FullTuple>
     *            The named type of the full tuple.
     */
    public static <
        A extends Comparable<A>,
        B extends Comparable<B>,
        Rest extends Comparable<Rest>,
        FullTuple extends Tuple<A, Tuple<B, Rest>>>
    Partial<FullTuple, Single<A>> oneOf(PartialType<FullTuple, A, Tuple<B, Rest>> typedefs)
    {
        ComparableServer<Tuple<A, End>, FullTuple> full =
            new CastFullComparableServer<Tuple<A, End>, Tuple<A, Tuple<B, Rest>>, FullTuple>(
                new Ignore<B, Rest>().<A>shared());
        ComparableServer<Single<A>, FullTuple> partial = new CastPartialComparableServer<Tuple<A, End>, Single<A>, FullTuple>(full);
        return new Partial<FullTuple, Single<A>>(partial);
    }

    /**
     * Constructs a partial comparable builder that will compare the first field
     * of a <code>Triple</code> or larger against a <code>Pair</code>.
     * 
     * @typedefs A structure that captures type type information necessary to
     *           create the partial comparator builder.
     * 
     * @param <A>
     *            The first parameter in the <code>Triple</code> or larger full
     *            tuple shared by the <code>Pair</code>.
     * @param <B>
     *            The second parameter in the <code>Triple</code> or larger full
     *            tuple shared by the <code>Pair</code>.
     * @param <C>
     *            The third parameter in the <code>Triple</code> or larger full
     *            tuple (not shared by the <code>Pair</code>.)
     * @param <Rest>
     *            The rest of the full tuple.
     * @param <FullTuple>
     *            The named type of the full tuple.
     */
    public static <
        A extends Comparable<A>,
        B extends Comparable<B>,
        C extends Comparable<C>,
        Rest extends Comparable<Rest>,
        FullTuple extends Tuple<A, Tuple<B, Tuple<C, Rest>>>>
    Partial<FullTuple, Pair<A, B>> twoOf(PartialType<FullTuple, A, Tuple<B, Tuple<C, Rest>>> typedefs)
    {
        ComparableServer<Tuple<A, Tuple<B, End>>, FullTuple> full =
            new CastFullComparableServer<Tuple<A, Tuple<B, End>>, Tuple<A, Tuple<B, Tuple<C, Rest>>>, FullTuple>(
                new Ignore<C, Rest>().<B>shared().<A>shared());
        ComparableServer<Pair<A, B>, FullTuple> partial = new CastPartialComparableServer<Tuple<A, Tuple<B, End>>, Pair<A, B>, FullTuple>(full);
        return new Partial<FullTuple, Pair<A, B>>(partial);
    }
    
    /**
     * Constructs a partial comparable builder that will compare the first field
     * of a <code>Quadruple</code> or larger against a <code>Triple</code>.
     * 
     * @typedefs A structure that captures type type information necessary to
     *           create the partial comparator builder.
     * 
     * @param <A>
     *            The first parameter in the <code>Quadruple</code> or larger
     *            full tuple shared by the <code>Triple</code>.
     * @param <B>
     *            The second parameter in the <code>Quadruple</code> or larger
     *            full tuple shared by the <code>Triple</code>.
     * @param <C>
     *            The third parameter in the <code>Quadruple</code> or larger
     *            full tuple shared by the <code>Triple</code>.
     * @param <D>
     *            The fifth parameter in the <code>Quadruple</code> or larger
     *            full tuple (not shared by the <code>Triple</code>.)
     * @param <Rest>
     *            The rest of the full tuple.
     * @param <FullTuple>
     *            The named type of the full tuple.
     */
    public static <
        A extends Comparable<A>,
        B extends Comparable<B>,
        C extends Comparable<C>,
        D extends Comparable<D>,
        Rest extends Comparable<Rest>,
        FullTuple extends Tuple<A, Tuple<B, Tuple<C, Tuple<D, Rest>>>>>
    Partial<FullTuple, Triple<A, B, C>> threeOf(PartialType<FullTuple, A, Tuple<B, Tuple<C, Tuple<D, Rest>>>> typedefs)
    {
        ComparableServer<Tuple<A, Tuple<B, Tuple<C, End>>>, FullTuple> full =
            new CastFullComparableServer<
                Tuple<A, Tuple<B, Tuple<C, End>>>,
                Tuple<A, Tuple<B, Tuple<C, Tuple<D, Rest>>>>, FullTuple>(
                new Ignore<D, Rest>().<C>shared().<B>shared().<A>shared());
        ComparableServer<Triple<A, B, C>, FullTuple> partial = new CastPartialComparableServer<Tuple<A, Tuple<B, Tuple<C, End>>>, Triple<A, B, C>, FullTuple>(full);
        return new Partial<FullTuple, Triple<A, B, C>>(partial);
    }
    
    /**
     * Constructs a partial comparable builder that will compare the first field
     * of a <code>Quintuple</code> or larger against a <code>Quadruple</code>.
     * 
     * @typedefs A structure that captures type type information necessary to
     *           create the partial comparator builder.
     * 
     * @param <A>
     *            The first parameter in the <code>Quintuple</code> or larger
     *            full tuple shared by the <code>Quadruple</code>.
     * @param <B>
     *            The second parameter in the <code>Quintuple</code> or larger
     *            full tuple shared by the <code>Quadruple</code>.
     * @param <C>
     *            The third parameter in the <code>Quintuple</code> or larger
     *            full tuple shared by the <code>Quadruple</code>.
     * @param <D>
     *            The fourth parameter in the <code>Quintuple</code> or larger
     *            full tuple shared by the <code>Quadruple</code>.
     * @param <E>
     *            The fifth parameter in the <code>Quintuple</code> or larger
     *            full tuple (not shared by the <code>Quadruple</code>.)
     * @param <Rest>
     *            The rest of the full tuple.
     * @param <FullTuple>
     *            The named type of the full tuple.
     */
     public static <
        A extends Comparable<A>,
        B extends Comparable<B>,
        C extends Comparable<C>,
        D extends Comparable<D>,
        E extends Comparable<E>,
        Rest extends Comparable<Rest>,
        FullTuple extends Tuple<A, Tuple<B, Tuple<C, Tuple<D, Tuple<E, Rest>>>>>>
    Partial<FullTuple, Quadruple<A, B, C, D>> fourOf(PartialType<FullTuple, A, Tuple<B, Tuple<C, Tuple<D, Tuple<E, Rest>>>>> typedefs)
    {
        ComparableServer<Tuple<A, Tuple<B, Tuple<C, Tuple<D, End>>>>, FullTuple> full =
            new CastFullComparableServer<
                Tuple<A, Tuple<B, Tuple<C, Tuple<D, End>>>>,
                Tuple<A, Tuple<B, Tuple<C, Tuple<D, Tuple<E, Rest>>>>>, FullTuple>(
                new Ignore<E, Rest>().<D>shared().<C>shared().<B>shared().<A>shared());
        ComparableServer<Quadruple<A, B, C, D>, FullTuple> partial = new CastPartialComparableServer<Tuple<A, Tuple<B, Tuple<C, Tuple<D, End>>>>, Quadruple<A, B, C, D>, FullTuple>(full);
        return new Partial<FullTuple, Quadruple<A, B, C, D>>(partial);
    }
    
    /**
     * Constructs a partial comparable builder that will compare the first field
     * of a <code>Sextuple</code> or larger against a <code>Quintuple</code>.
     * 
     * @typedefs A structure that captures type type information necessary to
     *           create the partial comparator builder.
     * 
     * @param <A>
     *            The first parameter in the <code>Sextuple</code> or larger full
     *            tuple shared by the <code>Quintuple</code>.
     * @param <B>
     *            The second parameter in the <code>Sextuple</code> or larger
     *            full tuple shared by the <code>Quintuple</code>.
     * @param <C>
     *            The third parameter in the <code>Sextuple</code> or larger full
     *            tuple shared by the <code>Quintuple</code>.
     * @param <D>
     *            The fourth parameter in the <code>Sextuple</code> or larger
     *            full tuple shared by the <code>Quintuple</code>.
     * @param <E>
     *            The fifth parameter in the <code>Sextuple</code> or larger full
     *            tuple shared by the <code>Quintuple</code>.
     * @param <F>
     *            The sixth parameter in the <code>Sextuple</code> or larger full
     *            tuple (not shared by the <code>Quintuple</code>.)
     * @param <Rest>
     *            The rest of the full tuple.
     * @param <FullTuple>
     *            The named type of the full tuple.
     */
    public static <
        A extends Comparable<A>,
        B extends Comparable<B>,
        C extends Comparable<C>,
        D extends Comparable<D>,
        E extends Comparable<E>,
        F extends Comparable<F>,
        Rest extends Comparable<Rest>,
        FullTuple extends Tuple<A, Tuple<B, Tuple<C, Tuple<D, Tuple<E, Tuple<F, Rest>>>>>>>
    Partial<FullTuple, Quintuple<A, B, C, D, E>> fiveOf(PartialType<FullTuple, A, Tuple<B, Tuple<C, Tuple<D, Tuple<E, Tuple<F, Rest>>>>>> typedefs)
    {
        ComparableServer<Tuple<A, Tuple<B, Tuple<C, Tuple<D, Tuple<E, End>>>>>, FullTuple> full =
            new CastFullComparableServer<
                Tuple<A, Tuple<B, Tuple<C, Tuple<D, Tuple<E, End>>>>>,
                Tuple<A, Tuple<B, Tuple<C, Tuple<D, Tuple<E, Tuple<F, Rest>>>>>>, FullTuple>(
                new Ignore<F, Rest>().<E>shared().<D>shared().<C>shared().<B>shared().<A>shared());
        ComparableServer<Quintuple<A, B, C, D, E>, FullTuple> partial = new CastPartialComparableServer<Tuple<A, Tuple<B, Tuple<C, Tuple<D, Tuple<E, End>>>>>, Quintuple<A, B, C, D, E>, FullTuple>(full);
        return new Partial<FullTuple, Quintuple<A, B, C, D, E>>(partial);
    }
    
    /**
     * Constructs a partial comparable builder that will compare the first field
     * of a <code>Septuple</code> or larger against a <code>Sextuple</code>.
     * 
     * @typedefs A structure that captures type type information necessary to
     *           create the partial comparator builder.
     * 
     * @param <A>
     *            The first parameter in the <code>Septuple</code> or larger full
     *            tuple shared by the <code>Sextuple</code>.
     * @param <B>
     *            The second parameter in the <code>Septuple</code> or larger
     *            full tuple shared by the <code>Sextuple</code>.
     * @param <C>
     *            The third parameter in the <code>Septuple</code> or larger full
     *            tuple shared by the <code>Sextuple</code>.
     * @param <D>
     *            The fourth parameter in the <code>Septuple</code> or larger
     *            full tuple shared by the <code>Sextuple</code>.
     * @param <E>
     *            The fifth parameter in the <code>Septuple</code> or larger full
     *            tuple shared by the <code>Sextuple</code>.
     * @param <F>
     *            The sixth parameter in the <code>Septuple</code> or larger full
     *            tuple shared by the <code>Sextuple</code>.
     * @param <G>
     *            The seventh parameter in the <code>Septuple</code> or larger full
     *            tuple (not shared by the <code>Sextuple</code>.)
     * @param <Rest>
     *            The rest of the full tuple.
     * @param <FullTuple>
     *            The named type of the full tuple.
     */
    public static <
        A extends Comparable<A>,
        B extends Comparable<B>,
        C extends Comparable<C>,
        D extends Comparable<D>,
        E extends Comparable<E>,
        F extends Comparable<F>,
        G extends Comparable<G>,
        Rest extends Comparable<Rest>,
        FullTuple extends Tuple<A, Tuple<B, Tuple<C, Tuple<D, Tuple<E, Tuple<F, Tuple<G, Rest>>>>>>>>
    Partial<FullTuple, Sextuple<A, B, C, D, E, F>> sixOf(PartialType<FullTuple, A, Tuple<B, Tuple<C, Tuple<D, Tuple<E, Tuple<F, Tuple<G, Rest>>>>>>> typedefs)
    {
        ComparableServer<Tuple<A, Tuple<B, Tuple<C, Tuple<D, Tuple<E, Tuple<F, End>>>>>>, FullTuple> full =
            new CastFullComparableServer<
                Tuple<A, Tuple<B, Tuple<C, Tuple<D, Tuple<E, Tuple<F, End>>>>>>,
                Tuple<A, Tuple<B, Tuple<C, Tuple<D, Tuple<E, Tuple<F, Tuple<G, Rest>>>>>>>, FullTuple>(
                new Ignore<G, Rest>().<F>shared().<E>shared().<D>shared().<C>shared().<B>shared().<A>shared());
        ComparableServer<Sextuple<A, B, C, D, E, F>, FullTuple> partial = new CastPartialComparableServer<Tuple<A, Tuple<B, Tuple<C, Tuple<D, Tuple<E, Tuple<F, End>>>>>>, Sextuple<A, B, C, D, E, F>, FullTuple>(full);
        return new Partial<FullTuple, Sextuple<A, B, C, D, E, F>>(partial);
    }
    
    /**
     * Constructs a partial comparable builder that will compare the first field
     * of a <code>Octuple</code> or larger against a <code>Septuple</code>.
     * 
     * @typedefs A structure that captures type type information necessary to
     *           create the partial comparator builder.
     * 
     * @param <A>
     *            The first parameter in the <code>Octuple</code> or larger full
     *            tuple shared by the <code>Septuple</code>.
     * @param <B>
     *            The second parameter in the <code>Octuple</code> or larger
     *            full tuple shared by the <code>Septuple</code>.
     * @param <C>
     *            The third parameter in the <code>Octuple</code> or larger full
     *            tuple shared by the <code>Septuple</code>.
     * @param <D>
     *            The fourth parameter in the <code>Octuple</code> or larger
     *            full tuple shared by the <code>Septuple</code>.
     * @param <E>
     *            The fifth parameter in the <code>Octuple</code> or larger full
     *            tuple shared by the <code>Septuple</code>.
     * @param <F>
     *            The sixth parameter in the <code>Octuple</code> or larger full
     *            tuple shared by the <code>Septuple</code>.
     * @param <G>
     *            The seventh parameter in the <code>Octuple</code> or larger
     *            full tuple shared by the <code>Septuple</code>.
     * @param <H>
     *            The eighth parameter in the <code>Octuple</code> or larger full
     *            tuple (not shared by the <code>Septuple</code>.)
     * @param <Rest>
     *            The rest of the full tuple.
     * @param <FullTuple>
     *            The named type of the full tuple.
     */
    public static <
        A extends Comparable<A>,
        B extends Comparable<B>,
        C extends Comparable<C>,
        D extends Comparable<D>,
        E extends Comparable<E>,
        F extends Comparable<F>,
        G extends Comparable<G>,
        H extends Comparable<H>,
        Rest extends Comparable<Rest>,
        FullTuple extends Tuple<A, Tuple<B, Tuple<C, Tuple<D, Tuple<E, Tuple<F, Tuple<G, Tuple<H, Rest>>>>>>>>>
    Partial<FullTuple, Septuple<A, B, C, D, E, F, G>> sevenOf(PartialType<FullTuple, A, Tuple<B, Tuple<C, Tuple<D, Tuple<E, Tuple<F, Tuple<G, Tuple<H, Rest>>>>>>>> typedefs)
    {
        ComparableServer<Tuple<A, Tuple<B, Tuple<C, Tuple<D, Tuple<E, Tuple<F, Tuple<G, End>>>>>>>, FullTuple> full =
            new CastFullComparableServer<
                Tuple<A, Tuple<B, Tuple<C, Tuple<D, Tuple<E, Tuple<F, Tuple<G, End>>>>>>>,
                Tuple<A, Tuple<B, Tuple<C, Tuple<D, Tuple<E, Tuple<F, Tuple<G, Tuple<H, Rest>>>>>>>>, FullTuple>(
                new Ignore<H, Rest>().<G>shared().<F>shared().<E>shared().<D>shared().<C>shared().<B>shared().<A>shared());
        ComparableServer<Septuple<A, B, C, D, E, F, G>, FullTuple> partial = new CastPartialComparableServer<Tuple<A, Tuple<B, Tuple<C, Tuple<D, Tuple<E, Tuple<F, Tuple<G, End>>>>>>>, Septuple<A, B, C, D, E, F, G>, FullTuple>(full);
        return new Partial<FullTuple, Septuple<A, B, C, D, E, F, G>>(partial);
    }
    
    /**
     * Constructs a partial comparable builder that will compare the first field
     * of a <code>Nonuple</code> or larger against a <code>Octuple</code>.
     * 
     * @typedefs A structure that captures type type information necessary to
     *           create the partial comparator builder.
     * 
     * @param <A>
     *            The first parameter in the <code>Nonuple</code> or larger full
     *            tuple shared by the <code>Octuple</code>.
     * @param <B>
     *            The second parameter in the <code>Nonuple</code> or larger
     *            full tuple shared by the <code>Octuple</code>.
     * @param <C>
     *            The third parameter in the <code>Nonuple</code> or larger full
     *            tuple shared by the <code>Octuple</code>.
     * @param <D>
     *            The fourth parameter in the <code>Nonuple</code> or larger
     *            full tuple shared by the <code>Octuple</code>.
     * @param <E>
     *            The fifth parameter in the <code>Nonuple</code> or larger full
     *            tuple shared by the <code>Octuple</code>.
     * @param <F>
     *            The sixth parameter in the <code>Nonuple</code> or larger full
     *            tuple shared by the <code>Octuple</code>.
     * @param <G>
     *            The seventh parameter in the <code>Nonuple</code> or larger
     *            full tuple shared by the <code>Octuple</code>.
     * @param <H>
     *            The eighth parameter in the <code>Nonuple</code> or larger
     *            full tuple shared by the <code>Octuple</code>.
     * @param <I>
     *            The ninth parameter in the <code>Nonuple</code> or larger full
     *            tuple (not shared by the <code>Octuple</code>.)
     * @param <Rest>
     *            The rest of the full tuple.
     * @param <FullTuple>
     *            The named type of the full tuple.
     */
    public static <
        A extends Comparable<A>,
        B extends Comparable<B>,
        C extends Comparable<C>,
        D extends Comparable<D>,
        E extends Comparable<E>,
        F extends Comparable<F>,
        G extends Comparable<G>,
        H extends Comparable<H>,
        I extends Comparable<I>,
        Rest extends Comparable<Rest>,
        FullTuple extends Tuple<A, Tuple<B, Tuple<C, Tuple<D, Tuple<E, Tuple<F, Tuple<G, Tuple<H, Tuple<I, Rest>>>>>>>>>>
    Partial<FullTuple, Octuple<A, B, C, D, E, F, G, H>> eightOf(PartialType<FullTuple, A, Tuple<B, Tuple<C, Tuple<D, Tuple<E, Tuple<F, Tuple<G, Tuple<H, Tuple<I, Rest>>>>>>>>> typedefs)
    {
        ComparableServer<Tuple<A, Tuple<B, Tuple<C, Tuple<D, Tuple<E, Tuple<F, Tuple<G, Tuple<H, End>>>>>>>>, FullTuple> full =
            new CastFullComparableServer<
                Tuple<A, Tuple<B, Tuple<C, Tuple<D, Tuple<E, Tuple<F, Tuple<G, Tuple<H, End>>>>>>>>,
                Tuple<A, Tuple<B, Tuple<C, Tuple<D, Tuple<E, Tuple<F, Tuple<G, Tuple<H, Tuple<I, Rest>>>>>>>>>, FullTuple>(
                new Ignore<I, Rest>().<H>shared().<G>shared().<F>shared().<E>shared().<D>shared().<C>shared().<B>shared().<A>shared());
        ComparableServer<Octuple<A, B, C, D, E, F, G, H>, FullTuple> partial = new CastPartialComparableServer<Tuple<A, Tuple<B, Tuple<C, Tuple<D, Tuple<E, Tuple<F, Tuple<G, Tuple<H, End>>>>>>>>, Octuple<A, B, C, D, E, F, G, H>, FullTuple>(full);
        return new Partial<FullTuple, Octuple<A, B, C, D, E, F, G, H>>(partial);
    }

    /**
     * Constructs a partial comparable builder that will compare the first field
     * of a <code>Decuple</code> or larger against a <code>Nonuple</code>.
     * 
     * @typedefs A structure that captures type type information necessary to
     *           create the partial comparator builder.
     * 
     * @param <A>
     *            The first parameter in the <code>Decuple</code> or larger full
     *            tuple shared by the <code>Nonuple</code>.
     * @param <B>
     *            The second parameter in the <code>Decuple</code> or larger
     *            full tuple shared by the <code>Nonuple</code>.
     * @param <C>
     *            The third parameter in the <code>Decuple</code> or larger full
     *            tuple shared by the <code>Nonuple</code>.
     * @param <D>
     *            The fourth parameter in the <code>Decuple</code> or larger
     *            full tuple shared by the <code>Nonuple</code>.
     * @param <E>
     *            The fifth parameter in the <code>Decuple</code> or larger full
     *            tuple shared by the <code>Nonuple</code>.
     * @param <F>
     *            The sixth parameter in the <code>Decuple</code> or larger full
     *            tuple shared by the <code>Nonuple</code>.
     * @param <G>
     *            The seventh parameter in the <code>Decuple</code> or larger
     *            full tuple shared by the <code>Nonuple</code>.
     * @param <H>
     *            The eighth parameter in the <code>Decuple</code> or larger
     *            full tuple shared by the <code>Nonuple</code>.
     * @param <I>
     *            The ninth parameter in the <code>Decuple</code> or larger full
     *            tuple shared by the <code>Nonuple</code>.
     * @param <J>
     *            The tenth parameter in the <code>Decuple</code> or larger full
     *            tuple (not shared by the <code>Nonuple</code>.)
     * @param <Rest>
     *            The rest of the full tuple.
     * @param <FullTuple>
     *            The named type of the full tuple.
     */
    public static <
        A extends Comparable<A>,
        B extends Comparable<B>,
        C extends Comparable<C>,
        D extends Comparable<D>,
        E extends Comparable<E>,
        F extends Comparable<F>,
        G extends Comparable<G>,
        H extends Comparable<H>,
        I extends Comparable<I>,
        J extends Comparable<J>,
        Rest extends Comparable<Rest>,
        FullTuple extends Tuple<A, Tuple<B, Tuple<C, Tuple<D, Tuple<E, Tuple<F, Tuple<G, Tuple<H, Tuple<I, Tuple<J, Rest>>>>>>>>>>>
    Partial<FullTuple, Nonuple<A, B, C, D, E, F, G, H, I>> nineOf(PartialType<FullTuple, A, Tuple<B, Tuple<C, Tuple<D, Tuple<E, Tuple<F, Tuple<G, Tuple<H, Tuple<I, Tuple<J, Rest>>>>>>>>>> typedefs)
    {
        ComparableServer<Tuple<A, Tuple<B, Tuple<C, Tuple<D, Tuple<E, Tuple<F, Tuple<G, Tuple<H, Tuple<I, End>>>>>>>>>, FullTuple> full =
            new CastFullComparableServer<
                Tuple<A, Tuple<B, Tuple<C, Tuple<D, Tuple<E, Tuple<F, Tuple<G, Tuple<H, Tuple<I, End>>>>>>>>>,
                Tuple<A, Tuple<B, Tuple<C, Tuple<D, Tuple<E, Tuple<F, Tuple<G, Tuple<H, Tuple<I, Tuple<J, Rest>>>>>>>>>>, FullTuple>(
                new Ignore<J, Rest>().<I>shared().<H>shared().<G>shared().<F>shared().<E>shared().<D>shared().<C>shared().<B>shared().<A>shared());
        ComparableServer<Nonuple<A, B, C, D, E, F, G, H, I>, FullTuple> partial = new CastPartialComparableServer<Tuple<A, Tuple<B, Tuple<C, Tuple<D, Tuple<E, Tuple<F, Tuple<G, Tuple<H, Tuple<I, End>>>>>>>>>, Nonuple<A, B, C, D, E, F, G, H, I>, FullTuple>(full);
        return new Partial<FullTuple, Nonuple<A, B, C, D, E, F, G, H, I>>(partial);
    }

    /**
     * Creates the type info structure to create a <code>Pair</code> full
     * tuple in a partial comparable builder that can be compared against a
     * <code>Single</code> tuple.
     * 
     * @param <A>
     *            The first parameter in the <code>Pair</code>.
     * @param <B>
     *            The second parameter in the <code>Pair</code>.
     * @return A structure that captures type type information necessary to
     *         create the partial comparator builder.
     */
    public static <
        A extends Comparable<A>,
        B extends Comparable<B>>
    PartialType<Pair<A, B>, A, Tuple<B, End>> pair()
    {
        return new PartialType<Pair<A, B>, A, Tuple<B, End>>();
    }

    /**
     * Creates the type info structure to create a <code>Triple</code> full
     * tuple in a partial comparable builder that can be compared against a
     * <code>Pair</code> or smaller tuple.
     * 
     * @param <A>
     *            The first parameter in the <code>Triple</code>.
     * @param <B>
     *            The second parameter in the <code>Triple</code>.
     * @param <C>
     *            The thrid parameter in the <code>Triple</code>.
     * @return A structure that captures type type information necessary to
     *         create the partial comparator builder.
     */
    public static <
        A extends Comparable<A>,
        B extends Comparable<B>,
        C extends Comparable<C>>
    PartialType<Triple<A, B, C>, A, Tuple<B, Tuple<C, End>>> triple()
    {
        return new PartialType<Triple<A, B, C>, A, Tuple<B, Tuple<C, End>>>();
    }

    /**
     * Creates the type info structure to create a <code>Quadruple</code> full
     * tuple in a partial comparable builder that can be compared against a
     * <code>Triple</code> or smaller tuple.
     * 
     * @param <A>
     *            The first parameter in the <code>Quadruple</code>.
     * @param <B>
     *            The second parameter in the <code>Quadruple</code>.
     * @param <C>
     *            The thrid parameter in the <code>Quadruple</code>.
     * @param <D>
     *            The fourth parameter in the <code>Quadruple</code>.
     * @return A structure that captures type type information necessary to
     *         create the partial comparator builder.
     */
    public static <
        A extends Comparable<A>,
        B extends Comparable<B>,
        C extends Comparable<C>,
        D extends Comparable<D>>
    PartialType<Quadruple<A, B, C, D>, A, Tuple<B, Tuple<C, Tuple<D, End>>>> quadruple()
    {
        return new PartialType<Quadruple<A, B, C, D>, A, Tuple<B, Tuple<C, Tuple<D, End>>>>();
    }

    /**
     * Creates the type info structure to create a <code>Quintuple</code> full
     * tuple in a partial comparable builder that can be compared against a
     * <code>Quadruple</code> or smaller tuple.
     * 
     * @param <A>
     *            The first parameter in the <code>Quintuple</code>.
     * @param <B>
     *            The second parameter in the <code>Quintuple</code>.
     * @param <C>
     *            The thrid parameter in the <code>Quintuple</code>.
     * @param <D>
     *            The fourth parameter in the <code>Quintuple</code>.
     * @param <E>
     *            The fifth parameter in the <code>Quintuple</code>.
     * @return A structure that captures type type information necessary to
     *         create the partial comparator builder.
     */
    public static <
        A extends Comparable<A>,
        B extends Comparable<B>,
        C extends Comparable<C>,
        D extends Comparable<D>,
        E extends Comparable<E>>
    PartialType<Quintuple<A, B, C, D, E>, A, Tuple<B, Tuple<C, Tuple<D, Tuple<E, End>>>>> quintuple()
    {
        return new PartialType<Quintuple<A, B, C, D, E>, A, Tuple<B, Tuple<C, Tuple<D, Tuple<E, End>>>>>();
    }

    /**
     * Creates the type info structure to create a <code>Sextuple</code> full
     * tuple in a partial comparable builder that can be compared against a
     * <code>Quintuple</code> or smaller tuple.
     * 
     * @param <A>
     *            The first parameter in the <code>Sextuple</code>.
     * @param <B>
     *            The second parameter in the <code>Septuple</code>.
     * @param <C>
     *            The thrid parameter in the <code>Septuple</code>.
     * @param <D>
     *            The fourth parameter in the <code>Sextuple</code>.
     * @param <E>
     *            The fifth parameter in the <code>Sextuple</code>.
     * @param <F>
     *            The sixth parameter in the <code>Sextuple</code>.
     * @return A structure that captures type type information necessary to
     *         create the partial comparator builder.
     */
    public static <
        A extends Comparable<A>,
        B extends Comparable<B>,
        C extends Comparable<C>,
        D extends Comparable<D>,
        E extends Comparable<E>,
        F extends Comparable<F>>
    PartialType<Sextuple<A, B, C, D, E, F>, A, Tuple<B, Tuple<C, Tuple<D, Tuple<E, Tuple<F, End>>>>>> sextuple()
    {
        return new PartialType<Sextuple<A, B, C, D, E, F>, A, Tuple<B, Tuple<C, Tuple<D, Tuple<E, Tuple<F, End>>>>>>();
    }

    /**
     * Creates the type info structure to create a <code>Septuple</code> full
     * tuple in a partial comparable builder that can be compared against a
     * <code>Sextuple</code> or smaller tuple.
     * 
     * @param <A>
     *            The first parameter in the <code>Septuple</code>.
     * @param <B>
     *            The second parameter in the <code>Septuple</code>.
     * @param <C>
     *            The thrid parameter in the <code>Septuple</code>.
     * @param <D>
     *            The fourth parameter in the <code>Septuple</code>.
     * @param <E>
     *            The fifth parameter in the <code>Septuple</code>.
     * @param <F>
     *            The sixth parameter in the <code>Septuple</code>.
     * @param <G>
     *            The seventh parameter in the <code>Septuple</code>.
     * @return A structure that captures type type information necessary to
     *         create the partial comparator builder.
     */
    public static <
        A extends Comparable<A>,
        B extends Comparable<B>,
        C extends Comparable<C>,
        D extends Comparable<D>,
        E extends Comparable<E>,
        F extends Comparable<F>,
        G extends Comparable<G>>
    PartialType<Septuple<A, B, C, D, E, F, G>, A, Tuple<B, Tuple<C, Tuple<D, Tuple<E, Tuple<F, Tuple<G, End>>>>>>> septuple()
    {
        return new PartialType<Septuple<A, B, C, D, E, F, G>, A, Tuple<B, Tuple<C, Tuple<D, Tuple<E, Tuple<F, Tuple<G, End>>>>>>>();
    }

    /**
     * Creates the type info structure to create a <code>Octuple</code> full
     * tuple in a partial comparable builder that can be compared against a
     * <code>Septuple</code> or smaller tuple.
     * 
     * @param <A>
     *            The first parameter in the <code>Octuple</code>.
     * @param <B>
     *            The second parameter in the <code>Octuple</code>.
     * @param <C>
     *            The thrid parameter in the <code>Octuple</code>.
     * @param <D>
     *            The fourth parameter in the <code>Octuple</code>.
     * @param <E>
     *            The fifth parameter in the <code>Octuple</code>.
     * @param <F>
     *            The sixth parameter in the <code>Octuple</code>.
     * @param <G>
     *            The seventh parameter in the <code>Octuple</code>.
     * @param <H>
     *            The eighth parameter in the <code>Octuple</code>.
     * @return A structure that captures type type information necessary to
     *         create the partial comparator builder.
     */
    public static <
        A extends Comparable<A>,
        B extends Comparable<B>,
        C extends Comparable<C>,
        D extends Comparable<D>,
        E extends Comparable<E>,
        F extends Comparable<F>,
        G extends Comparable<G>,
        H extends Comparable<H>>
    PartialType<Octuple<A, B, C, D, E, F, G, H>, A, Tuple<B, Tuple<C, Tuple<D, Tuple<E, Tuple<F, Tuple<G, Tuple<H, End>>>>>>>> octuple()
    {
        return new PartialType<Octuple<A, B, C, D, E, F, G, H>, A, Tuple<B, Tuple<C, Tuple<D, Tuple<E, Tuple<F, Tuple<G, Tuple<H, End>>>>>>>>();
    }

    /**
     * Creates the type info structure to create a <code>Nonuple</code> full
     * tuple in a partial comparable builder that can be compared against a
     * <code>Octuple</code> or smaller tuple.
     * 
     * @param <A>
     *            The first parameter in the <code>Nonuple</code>.
     * @param <B>
     *            The second parameter in the <code>Nonuple</code>.
     * @param <C>
     *            The thrid parameter in the <code>Nonuple</code>.
     * @param <D>
     *            The fourth parameter in the <code>Nonuple</code>.
     * @param <E>
     *            The fifth parameter in the <code>Nonuple</code>.
     * @param <F>
     *            The sixth parameter in the <code>Nonuple</code>.
     * @param <G>
     *            The seventh parameter in the <code>Nonuple</code>.
     * @param <H>
     *            The eighth parameter in the <code>Nonuple</code>.
     * @param <I>
     *            The ninth parameter in the <code>Nonuple</code>.
     * @return A structure that captures type type information necessary to
     *         create the partial comparator builder.
     */
    public static <
        A extends Comparable<A>,
        B extends Comparable<B>,
        C extends Comparable<C>,
        D extends Comparable<D>,
        E extends Comparable<E>,
        F extends Comparable<F>,
        G extends Comparable<G>,
        H extends Comparable<H>,
        I extends Comparable<I>>
    PartialType<Nonuple<A, B, C, D, E, F, G, H, I>, A, Tuple<B, Tuple<C, Tuple<D, Tuple<E, Tuple<F, Tuple<G, Tuple<H, Tuple<I, End>>>>>>>>> nonuple()
    {
        return new PartialType<Nonuple<A, B, C, D, E, F, G, H, I>, A, Tuple<B, Tuple<C, Tuple<D, Tuple<E, Tuple<F, Tuple<G, Tuple<H, Tuple<I, End>>>>>>>>>();
    }

    /**
     * Creates the type info structure to create a <code>Decuple</code> full
     * tuple in a partial comparable builder that can be compared against a
     * <code>Nonuple</code> or smaller tuple.
     * 
     * @param <A>
     *            The first parameter in the <code>Decuple</code>.
     * @param <B>
     *            The second parameter in the <code>Decuple</code>.
     * @param <C>
     *            The thrid parameter in the <code>Decuple</code>.
     * @param <D>
     *            The fourth parameter in the <code>Decuple</code>.
     * @param <E>
     *            The fifth parameter in the <code>Decuple</code>.
     * @param <F>
     *            The sixth parameter in the <code>Decuple</code>.
     * @param <G>
     *            The seventh parameter in the <code>Decuple</code>.
     * @param <H>
     *            The eighth parameter in the <code>Decuple</code>.
     * @param <I>
     *            The ninth parameter in the <code>Decuple</code>.
     * @param <J>
     *            The tenth parameter in the <code>Decuple</code>.
     * @return A structure that captures type type information necessary to
     *         create the partial comparator builder.
     */
    public static <
        A extends Comparable<A>,
        B extends Comparable<B>,
        C extends Comparable<C>,
        D extends Comparable<D>,
        E extends Comparable<E>,
        F extends Comparable<F>,
        G extends Comparable<G>,
        H extends Comparable<H>,
        I extends Comparable<I>,
        J extends Comparable<J>>
    PartialType<Decuple<A, B, C, D, E, F, G, H, I, J>, A, Tuple<B, Tuple<C, Tuple<D, Tuple<E, Tuple<F, Tuple<G, Tuple<H, Tuple<I, Tuple<J, End>>>>>>>>>> decuple()
    {
        return new PartialType<Decuple<A, B, C, D, E, F, G, H, I, J>, A, Tuple<B, Tuple<C, Tuple<D, Tuple<E, Tuple<F, Tuple<G, Tuple<H, Tuple<I, Tuple<J, End>>>>>>>>>>();
    }
}
