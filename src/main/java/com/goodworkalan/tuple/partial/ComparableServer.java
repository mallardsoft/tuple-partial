package com.goodworkalan.tuple.partial;


interface ComparableServer<PartialRest, Rest>
{
    public Comparable<Rest> comparable(PartialRest partial);
}
