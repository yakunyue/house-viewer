
package com.fxg.house.viewer.api;

import com.fxg.house.viewer.exception.ExceptionDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;


/**
 * <p> 接口应答对象,包括code:应答码,desc:应答描述,data:数据域 </p>
 */
public class HttpResult<T> {

	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	private Integer code;
	private String desc;
	private T data;


    private List<ExceptionDescriptor> exceptionDescriptors;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    public List<ExceptionDescriptor> getExceptionDescriptors() {
        return exceptionDescriptors;
    }

    public void setExceptionDescriptors(List<ExceptionDescriptor> exceptionDescriptors) {
        this.exceptionDescriptors = exceptionDescriptors;
    }

    public HttpResult() {
        this(null, null, null, null);
    }


    public HttpResult(Integer code, String desc) {

        this(code, desc, null, null);
    }

    public HttpResult(Integer code, String desc, T data) {

        this(code, desc, data, null);
    }

    public HttpResult(Integer code, String desc, T data, List<ExceptionDescriptor> exceptionDescriptors) {
        this.code = code;
        this.desc = desc;
        this.data = data;
        this.exceptionDescriptors = exceptionDescriptors;

    }

    @Override
    public String toString() {
        return "HttpResult [code=" + code + ", desc=" + desc + ", data=" + data + "]";
    }

	/**
	 * 校验接口返回状态，需自定义错误处理逻辑
	 * @param handler
	 * @return
	 */
	public T checkResult(Function<HttpResult<T>, T> handler) {
        if (this.code.equals(HttpStatus.OK)) {
            //正常返回结果了
            return this.data;
        } else {
            return Objects.isNull(handler) ? null : handler.apply(this);
        }
    }

	/**
	 * 校验接口返回状态,不是 200 的话打个 error 日志,不往外抛异常
	 * @return
	 */
	public T checkResultSilent() {
        return this.checkResult(r -> {
                    logger.error("远程服务调用返业务错误 {} {}", r.code, r.desc);
                    return null;
                }
        );
    }

    public static <X> HttpResult<X> ok(X data) {
        return new HttpResult<>(HttpStatus.OK, "ok", data);
    }


}
