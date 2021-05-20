package com.fxg.house.viewer.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.UUID;

/**
 * 以面向切面编程的方式打印接口请求日志
 */
@Aspect
@Component
public class ApiLogAspect {
	private Logger logger = LoggerFactory.getLogger(ApiLogAspect.class);

	@Autowired
	private ObjectMapper objectMapper;

	@Pointcut("execution(public * com.fxg.house.viewer.controller.*Controller.*(..))")
	public void controller() {
	}


	@Around(value = "controller()")
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {

		ServletRequestAttributes attributes =
				(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		if (Objects.nonNull(attributes)) {
			Object[] args=pjp.getArgs();
			HttpServletRequest request = attributes.getRequest();
			String url=request.getRequestURL().toString();
			String uuid= UUID.randomUUID().toString();
			StringBuilder sb = new StringBuilder();
			sb.append("\n===========================================================\n");
			sb.append(String.format("=原始请求数据 请求惟一号 %s\n",uuid));
			sb.append(String.format("=url : %s\n",url));
			//            sb.append(String.format("=request method : %s\n",request.getMethod()));
			//            sb.append(String.format("=remote ip : %s\n",request.getRemoteAddr()));
			//            sb.append(String.format("=class method : %s\n",pjp.getSignature().getDeclaringTypeName() + "." + pjp.getSignature().getName()));
			for (int i = 0; i <args.length ; i++) {
				if (Objects.isNull(args[i]))
				{
					sb.append(String.format("=param index %s : %s\n",i, "null"));
				}
				else
				{
					//这里不用json了，因为可能是一些特殊参数，无法序列化的，比如httprequest等

					try{
						sb.append(String.format("=param index %s : %s\n",i, objectMapper.writeValueAsString(args[i])));
					}catch (Exception e){
						sb.append(String.format("=param index %s : %s\n",i, args[i].toString()));
					}
				}

			}
			sb.append("===========================================================\n");
			logger.info(sb.toString());
			long startTime = System.currentTimeMillis();
			Object result = pjp.proceed();
			long endTime = System.currentTimeMillis();
			sb = new StringBuilder();
			sb.append("\n===========================================================\n");
			sb.append(String.format("=执行结果及分析数据 uuid %s url %s\n",uuid, url));
			sb.append(String.format("=%s execute time :%s ms\n", pjp.getSignature(), endTime - startTime));
			sb.append(String.format("=response : %s\n", result));
			sb.append("===========================================================\n\n");
			logger.info(sb.toString());
			return result;
		}
		else
		{
			return pjp.proceed();
		}
	}
}
