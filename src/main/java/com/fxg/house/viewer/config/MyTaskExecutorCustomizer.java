package com.fxg.house.viewer.config;

import org.springframework.boot.task.TaskExecutorCustomizer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 修改 springboot 默认线程池参数
 * 可以在配 application.yml 置文件中配置，也可以在这个配置类中配置
 *
 * 说一下 ThreadPoolExecutor 其他几种拒绝策略的缺点
 * 1、AbortPolicy：终止
 * 直接抛出 RejectedExecutionException 异常，这样会终止主线程的执行，主线程中未提交的任务就无法处理了
 *
 * 2、DiscardPolicy：丢弃
 * 什么也不做，直接放弃提交这条任务。
 *
 * 3、DiscardOldestPolicy：丢弃老的
 * 把在队列最前面等待执行的任务丢弃掉
 *
 * 其实这里也可以用自定义拒绝策略，比如：
 * 1、新创建一个线程来处理任务，这种策略的缺点是如果任务量特别大，就会创建大量线程，最大线程数是受jvm配置和系统配置限制的。
 * 2、等一段时间之后再提交，executor.getQueue().offer(r, 60, TimeUnit.SECONDS);
 * 等一段时间之后再提交是可行的，但依旧会阻塞主线程。所以，综合来讲，还不如直接交给主线程处理。
 */
@Component
public class MyTaskExecutorCustomizer implements TaskExecutorCustomizer {

	@Override
	public void customize(ThreadPoolTaskExecutor taskExecutor) {
		taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());//拒绝策略
//		taskExecutor.setCorePoolSize();//核心线程数
//		taskExecutor.setMaxPoolSize();//最大线程数
//		taskExecutor.setQueueCapacity();//队列长度
//		taskExecutor.setAllowCoreThreadTimeOut();//是否允许主线程被杀死
//		taskExecutor.setKeepAliveSeconds();//线程空闲时间
	}


}
