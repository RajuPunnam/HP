package com.inventory.utill;

public class DoubleUtill {
	public static double getValue(Object obj){
        double value = 0;
		if(obj instanceof Double){
			value = (Double)obj;
		}else if(obj instanceof Integer){
			value = (Integer)obj;
		}else if(obj instanceof String){
			String str=(String)obj;
			if(str!=null && !str.trim().equals("")){
				str.trim();
				   if(str.contains(",")){
					  value = Double.parseDouble(str.replaceAll(",", ""));
				   } else{
					   try{
					   value = Double.parseDouble(str);
					   }catch(java.lang.NumberFormatException e){
						   value=0d;  
					   }
					  }
				   }else{
					   value = 0d;
				   }
		}
	  return value;
	}
	
	
	public static int getIntValue(Object obj){
		int value = 0;
		if(obj instanceof Double){
			value =(int)obj;
		}else if(obj instanceof Integer){
			value = (Integer)obj;
		}else if(obj instanceof String){
			String str=(String)obj;
			if(str!=null && !str.trim().equals("")){
				str.trim();
				   if(str.contains(",")){
					  value = Integer.parseInt(str.replaceAll(",", ""));
				   } else{
					   try{
					   value = Integer.parseInt(str);
					   }catch(java.lang.NumberFormatException e){
						   value=0;  
					   }
					  }
				   }else{
					   value = 0;
				   }
		}
	  return value;
	}
	
	
}
