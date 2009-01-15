package com.goodworkalan.tuple.partial;

class CastFullComparableServer<PartialRest, Rest extends Comparable<Rest>, Struct extends Rest>
implements ComparableServer<PartialRest, Struct>
{
    private final ComparableServer<PartialRest, Rest> delegate;
    
    public CastFullComparableServer(ComparableServer<PartialRest, Rest> delegate)
    {
        this.delegate = delegate;
    }
    
    public Comparable<Struct> comparable(PartialRest partial)
    {
        final Comparable<Rest> comparable = delegate.comparable(partial);
        return new Comparable<Struct>()
        {
            public int compareTo(Struct o)
            {
                return comparable.compareTo(o);
            }
        };
    };
}
