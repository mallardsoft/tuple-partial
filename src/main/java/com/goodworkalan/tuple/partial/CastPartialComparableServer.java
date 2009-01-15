package com.goodworkalan.tuple.partial;


class CastPartialComparableServer<PartialRest, Struct extends PartialRest, Rest>
implements ComparableServer<Struct, Rest>
{
    private final ComparableServer<PartialRest, Rest> delegate;
    
    public CastPartialComparableServer(ComparableServer<PartialRest, Rest> delegate)
    {
        this.delegate = delegate;
    }
    
    public Comparable<Rest> comparable(Struct partial)
    {
        return delegate.comparable(partial);
    };
}