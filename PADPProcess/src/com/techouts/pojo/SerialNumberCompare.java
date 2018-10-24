package com.techouts.pojo;

import java.util.Comparator;

public class SerialNumberCompare implements Comparator<FlexBom> {

	@Override
	public int compare(FlexBom o1, FlexBom o2) {
		//------------< for descending      > for ascending -------------
		if(o1.getSrno() >o2.getSrno()){
            return 1;
        } else {
            return -1;
        }
	}

}
