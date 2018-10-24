/**
 * 
 */
package com.techouts.hp.supplies.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.techouts.hp.supplies.workerthread.AgingWokerThread;
import com.techouts.hp.supplies.workerthread.DOIWokerThread;
import com.techouts.hp.supplies.workerthread.EOWokerThread;
import com.techouts.hp.supplies.workerthread.OpenOrdersWokerThread;

/**
 * @author raju.p
 *
 */
@Service
@Path("/supplies")
public class SuppliesService {
	@Autowired
	private ThreadPoolTaskExecutor threadPoolExecutor;
	@Autowired
	private AgingWokerThread agingWorkerThread;
	@Autowired
	private DOIWokerThread doiWokerThread;
	@Autowired
	private EOWokerThread eoWokerThread;
	@Autowired
	private OpenOrdersWokerThread openOrdersWokerThread;
	
    @GET
   	@Produces(MediaType.TEXT_PLAIN)
   	@Path("/processsupplies")
	public Response processSuppliesFiles()
	{
		threadPoolExecutor.execute(agingWorkerThread);
		//threadPoolExecutor.execute(doiWokerThread);
		//threadPoolExecutor.execute(eoWokerThread);
		//threadPoolExecutor.execute(openOrdersWokerThread);
		return Response.ok("Supplies all thread compleated").build();
	}

}
