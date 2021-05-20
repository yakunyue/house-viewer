package com.fxg.house.viewer.config;

import com.fxg.house.viewer.api.HttpResult;
import com.fxg.house.viewer.api.HttpStatus;
import com.fxg.house.viewer.exception.ExceptionDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Collections;

/**
 * 统一异常捕捉
 */
@RestControllerAdvice("org.example")
//@ControllerAdvice  这里用 @RestControllerAdvice 注解的话后面的异常处理方法上就不用加 @ResponseBody 注解了
public class ExceptionHandlerAdvice {

	Logger logger = LoggerFactory.getLogger(getClass());

	private String ipAddress;

	@Value("${spring.application.name}")
	private String applicationName;

	/**
	 *
	 */
	public ExceptionHandlerAdvice() {
		try {
			ipAddress = Inet4Address.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			logger.error("获取ip地址失败");
		}
		logger.info("当前主机地址为：{}",ipAddress);
	}

	public ExceptionDescriptor newDescriptor(Exception ex) {

		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		ExceptionDescriptor descriptor = ExceptionDescriptor.newFromException(ex, this.applicationName, this.ipAddress, request.getRequestURI(), null);
		return descriptor;
	}

	/**
	 * 参数校验异常处理
	 *
	 * @param e {@link ConstraintViolationException}
	 * @return HttpResult<Void>
	 */
	@ExceptionHandler(value = ConstraintViolationException.class)
	public HttpResult<Void> constrainValidationException(ConstraintViolationException e) {

		ExceptionDescriptor descriptor = this.newDescriptor(e);
		StringBuilder sb = new StringBuilder();

		sb.append("校验失败详细信息: \n");

		for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
			sb.append(String.format("校验失败位置:%s,校验规则%s,实际参数值:%s,校验提示信息:%s\n", violation.getPropertyPath(), violation.getConstraintDescriptor().getAnnotation(), violation.getInvalidValue(), violation.getMessage()));
		}

		descriptor.setInfo(String.format("校验失败详细信息%s", sb.toString()));
		logger.info("Validation异常{}", descriptor.toString(), e);
		return new HttpResult<>(HttpStatus.PARAM_ERROR, "validation错误", null, Collections.singletonList(descriptor));

	}

	@ExceptionHandler(value = BindException.class)
	public HttpResult<Void> bindingException(BindException e) {
		ExceptionDescriptor descriptor = this.newDescriptor(e);
		logger.info("DataBinding异常{}", descriptor.toString(), e);
		return new HttpResult<>(HttpStatus.PARAM_ERROR, "binding错误", null, Collections.singletonList(descriptor));

	}


	//  @ExceptionHandler(value = FXGException.class)
	//  public HttpResult<Void> bindingException(FXGException e) {
	//    //明确的错误信息，不提供辅助错误信息支持
	//    return new HttpResult<Void>(e.getResultCode(), e.getResultMessage() + ":" + e.getMessage());
	//
	//  }
	//    @ExceptionHandler(value = NoAuthException.class)
	//    public HttpResult<Void> noAuthException (NoAuthException e) {
	//        ExceptionDescriptor descriptor = this.newDescriptor(e);
	//        logger.warn("权限不足，拒绝访问", descriptor.toString());
	//        return new HttpResult<Void>(HttpStatus.AUTH_FAIL,"权限不足，拒绝访问",null,Collections.singletonList(descriptor));
	//    }
	//

	//  @ExceptionHandler(value = RequireLogInException.class)
	//  public HttpResult<Void> requireLogin(RequireLogInException e) {
	//    ExceptionDescriptor descriptor = this.newDescriptor(e);
	//    logger.info("当前资源需要用户登陆", descriptor.toString());
	//    return new HttpResult<Void>(HttpStatus.UN_LOGIN, "当前资源需要用户登陆", null, Collections.singletonList(descriptor));
	//  }
	/**
	 * Feign Response格式异常处理
	 *
	 *
	 * @param e {@link DecodeException}
	 *
	 * @return HttpResult<Void>
	 */
	//    @ExceptionHandler(value = DecodeException.class)
	//    public HttpResult<Void> feignDecodeExceptionHandler (DecodeException e) {
	//
	//        ExceptionDescriptor descriptor = this.newDescriptor(e);
	//        descriptor.setInfo(String.format("feign状态码%s",e.status()));
	//        logger.info("feign返回结果解析错误{}", descriptor.toString(),e);
	//        return new HttpResult<Void>(HttpStatus.SERVER_ERROR,"feign解码错误",null,Collections.singletonList(descriptor));
	//
	//    }

	/**
	 * Feign通用异常处理
	 *
	 *
	 * @param e {@link FeignException}
	 *
	 * @return HttpResult<Void>
	 */
	//    @ExceptionHandler(value = FeignException.class)
	//    public HttpResult<Void> feignGeneralExceptionHandler (FeignException e) {
	//        ExceptionDescriptor descriptor = this.newDescriptor(e);
	//        descriptor.setInfo(String.format("feign状态码%s",e.status()));
	//        logger.info("feign调用错误{}", descriptor.toString(),e);
	//        return new HttpResult<Void>(HttpStatus.SERVER_ERROR,"feign异常",null,Collections.singletonList(descriptor));
	//
	//    }


	/**
	 * 全局异常捕捉处理
	 */
	@ExceptionHandler(value = Exception.class)
	public HttpResult<Void> globalErrorHandler(Exception e) {

		ExceptionDescriptor descriptor = this.newDescriptor(e);
		logger.info("服务器发生未识别的内部错误{}", descriptor.toString(), e);
		return new HttpResult<>(HttpStatus.SERVER_ERROR, "服务器发生内部错误", null, Collections.singletonList(descriptor));

	}

	/**
	 * 远程服务调用异常处理
	 *
	 *
	 * @param e {@link ServiceCallException}
	 *
	 * @return HttpResult<Void>
	 */
	//    @ResponseBody
	//    @ExceptionHandler(value = ServiceCallException.class)
	//    public HttpResult<Void> serviceCallException (ServiceCallException e) {
	//
	//        ExceptionDescriptor descriptor = this.newDescriptor(e);
	//
	//        List<ExceptionDescriptor> exceptionDescriptors = Objects.isNull(e.getExceptionDescriptors()) ? new ArrayList<>() : e.getExceptionDescriptors();
	//        exceptionDescriptors.add(descriptor);
	//        descriptor.setInfo(StringUtils.isEmpty(e.getBody())?e.getMessage():e.getMessage()+"\n返回报文信息:\n" +e.getBody());
	//        logger.info("远程服务调用发生异常\n{} ", ExceptionDescriptor.printLocal(exceptionDescriptors),e);
	//        return new HttpResult<Void>(HttpStatus.SERVER_ERROR, "error", null, exceptionDescriptors);
	//    }

	/**
	 * 非法参数异常处理
	 */
	@ExceptionHandler(value = IllegalArgumentException.class)
	public HttpResult<Void> webErrorHandler(IllegalArgumentException e) {
		ExceptionDescriptor descriptor = this.newDescriptor(e);
		logger.info("应用程序检测到非法参数{}", descriptor.toString(), e);
		return new HttpResult<>(HttpStatus.PARAM_ERROR, "服务器检测到参数异常", null, Collections.singletonList(descriptor));

	}
}
