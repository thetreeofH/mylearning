package com.ts.spm.bizs.util;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServlet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;


public class ContextLoaderServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Logger log= Logger.getLogger(ContextLoaderServlet.class);
	private static int treadCount;
	private static ScheduledExecutorService executorService;
	private static ExecutorService threadPool;
	private static final int MIN_THREAD = 4;
	
	static{
		treadCount=Runtime.getRuntime().availableProcessors();
		if(treadCount < MIN_THREAD) {
			treadCount = MIN_THREAD;
		}
		executorService = Executors.newScheduledThreadPool(treadCount);
		threadPool = Executors.newCachedThreadPool();
	}
	
	public static ScheduledExecutorService getExecutorService(){
		return executorService;
	}
	
	public static ExecutorService getThreadPool() {
		return threadPool;
	}

    //Clean up resources
    @Override
	public void destroy() {
    	if(executorService!=null) {//关闭线程池
    		try {
    			executorService.shutdownNow();
    		}catch(Exception e) {
    			e.printStackTrace();
    		}
    	}
    	if(threadPool != null) {//关闭线程池
    		try {
				threadPool.shutdown();
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
    }


}