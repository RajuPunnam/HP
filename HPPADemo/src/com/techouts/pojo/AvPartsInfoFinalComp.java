package com.techouts.pojo;

import java.util.Comparator;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

public class AvPartsInfoFinalComp implements Comparator<AvPartsInfoFinal> {

    @Override
    public int compare(AvPartsInfoFinal part1, AvPartsInfoFinal part2) {
	return ((Integer)part1.getLeadTime()).compareTo(part2.getLeadTime());
    }

    @Override
    public Comparator<AvPartsInfoFinal> reversed() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Comparator<AvPartsInfoFinal> thenComparing(
	    Comparator<? super AvPartsInfoFinal> other) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public <U> Comparator<AvPartsInfoFinal> thenComparing(
	    Function<? super AvPartsInfoFinal, ? extends U> keyExtractor,
	    Comparator<? super U> keyComparator) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public <U extends Comparable<? super U>> Comparator<AvPartsInfoFinal> thenComparing(
	    Function<? super AvPartsInfoFinal, ? extends U> keyExtractor) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Comparator<AvPartsInfoFinal> thenComparingInt(
	    ToIntFunction<? super AvPartsInfoFinal> keyExtractor) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Comparator<AvPartsInfoFinal> thenComparingLong(
	    ToLongFunction<? super AvPartsInfoFinal> keyExtractor) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Comparator<AvPartsInfoFinal> thenComparingDouble(
	    ToDoubleFunction<? super AvPartsInfoFinal> keyExtractor) {
	// TODO Auto-generated method stub
	return null;
    }

    

    
}
