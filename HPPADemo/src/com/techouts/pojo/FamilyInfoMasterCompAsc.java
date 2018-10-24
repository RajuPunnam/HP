package com.techouts.pojo;

import java.util.Comparator;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

public class FamilyInfoMasterCompAsc implements Comparator<FamilyInfoFinal> {

    @Override
    public int compare(FamilyInfoFinal family1, FamilyInfoFinal family2) {
	return ( (Integer) family1.getQuantity() ).compareTo( family2.getQuantity() );
    }

    @Override
    public Comparator<FamilyInfoFinal> reversed() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Comparator<FamilyInfoFinal> thenComparing(
	    Comparator<? super FamilyInfoFinal> other) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public <U> Comparator<FamilyInfoFinal> thenComparing(
	    Function<? super FamilyInfoFinal, ? extends U> keyExtractor,
	    Comparator<? super U> keyComparator) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public <U extends Comparable<? super U>> Comparator<FamilyInfoFinal> thenComparing(
	    Function<? super FamilyInfoFinal, ? extends U> keyExtractor) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Comparator<FamilyInfoFinal> thenComparingInt(
	    ToIntFunction<? super FamilyInfoFinal> keyExtractor) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Comparator<FamilyInfoFinal> thenComparingLong(
	    ToLongFunction<? super FamilyInfoFinal> keyExtractor) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Comparator<FamilyInfoFinal> thenComparingDouble(
	    ToDoubleFunction<? super FamilyInfoFinal> keyExtractor) {
	// TODO Auto-generated method stub
	return null;
    }

    

}
