package com.techouts.re.utill;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.techouts.re.services.SkuAvalibility;
import com.techouts.re.services.SkuAvalibility1;
import com.techouts.services.CartService;


public class Test {
	private static final Logger LOG = Logger.getLogger(Test.class);
	public static void main(String[] args) throws ParseException {
		ApplicationContext ctx=new ClassPathXmlApplicationContext("com/techouts/re/utill/applicationContext.xml");
		SkuAvalibility skuAvalibility=ctx.getBean(SkuAvalibility.class);
		CartService cart=ctx.getBean(CartService.class);
		long sec=new Date().getTime();
		LOG.info("---------started at----------"+sec);		
		skuAvalibility.generateFinalQty();	
		skuAvalibility.createPipeLineCollection();
		skuAvalibility.generateSkuAvilability(true);
//		List<String> avList = new ArrayList<String>(Arrays.asList("F8B64AV","F8V97AV#AC4","F8V98AV#AC4","F8W14AV#ABM","F9L68AV","F9L75AV","F9V80AV#AC4","FG972AV","G0V58AV","G8A91AV","J2J62AV","N1U35AV","N1U38AV","N1U41AV","N3B53AV#AC4","XU979AV"));
//		cart.addtoCart(10, avList);
//		cart.removeFromCart(10, avList);
		LOG.info(sec+"---------ended at----------"+new Date().getTime());
		LOG.info("Total Time :-"+(new Date().getTime()-sec)/(1000));
		if(ctx!=null)
		{
		((ClassPathXmlApplicationContext) ctx).close();
		}
	}
	
	void GeneratePermutations(List<List<String>> Lists, List<String> result, int depth, String current)
	{
	    if(depth == Lists.size())
	    {
	       result.add(current);
	       return;
	     }

	    for(int i = 0; i < Lists.get(depth).size(); ++i)
	    {
	        GeneratePermutations(Lists, result, depth + 1, current + Lists.get(depth).get(i));
	    }
	}
	
}
