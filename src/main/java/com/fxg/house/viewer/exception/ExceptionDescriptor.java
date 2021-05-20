package com.fxg.house.viewer.exception;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ExceptionDescriptor {


    private static final String PRE_FIX="org.example";

    private String serviceName;
    private String ipAddress;
    private String url;

    private List<ExceptionSummary> cause;

    private List<StackSummary> stack;

    private String info;

    public ExceptionDescriptor() {
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<ExceptionSummary> getCause() {
        return cause;
    }

    public void setCause(List<ExceptionSummary> cause) {
        this.cause = cause;
    }

    public List<StackSummary> getStack() {
        return stack;
    }

    public void setStack(List<StackSummary> stack) {
        this.stack = stack;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public static ExceptionDescriptor newFromException(Exception ex,String serviceName,String ipAddress,String url,String info) {

        ExceptionDescriptor descriptor = new ExceptionDescriptor();
        descriptor.setServiceName(serviceName);
        descriptor.setIpAddress(ipAddress);
        descriptor.setUrl(url);
        descriptor.setInfo(info);

        List<ExceptionSummary> exceptionSummaryList = new ArrayList<>();
        //提取cause摘要信息
        int j = 0;
        Throwable t = ex;
        while (Objects.nonNull(t)) {
            j = j + 1;
            exceptionSummaryList.add(new ExceptionSummary(t));
            t=t.getCause();
        }
        descriptor.setCause(exceptionSummaryList);
        descriptor.setStack(
                Arrays.asList(ex.getStackTrace())
                .stream()
                .filter((o) -> (o.getClassName().startsWith(PRE_FIX)))
                .map((o)->(new StackSummary(o)))
                .collect(Collectors.toList()));
        return descriptor;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (Objects.nonNull(this.serviceName)) {
            sb.append("\n");
            sb.append("服务提供者:");
            sb.append(this.serviceName.trim());
            sb.append(" ");
            if (Objects.isNull(this.getIpAddress())) {
                sb.append(this.ipAddress);
            }

        }
        if (Objects.nonNull(this.url)) {
            sb.append("\n");
            sb.append("请求地址:");
            sb.append(this.url.trim());
        }
        LambadaCounter counter=new LambadaCounter();

        if (Objects.nonNull(this.cause) && this.cause.size()>0) {
            sb.append("\n");
            sb.append("异常原因：");
            sb.append("\n");
            this.cause.forEach((o)->{
                counter.add();
                sb.append("【");
                sb.append(counter.current());
                sb.append("】 ");
                sb.append(o.getExceptionType());
                sb.append(" ");
                sb.append(o.getMessage());
                sb.append("\n");
            });
        }
        counter.reset();
        if (Objects.nonNull(this.stack) && this.stack.size()>0) {
            sb.append("\n");
            sb.append("异常发生位置：");
            sb.append("\n");
            this.stack.forEach((o)->{
                counter.add();
                sb.append("【");
                sb.append(counter.current());
                sb.append("】 ");
                sb.append(o.getTargetClass());
                sb.append("#");
                sb.append(o.getTargetMethod());
                sb.append(" [");
                sb.append(o.getLineNo());
                sb.append("]");
                sb.append("\n");
            });
        }
        else
        {
            sb.append("\n");
            sb.append("无定位提示信息");
        }

        if (Objects.nonNull(this.info)) {
            sb.append("\n");
            sb.append("补充提示信息：");
            sb.append("\n");
            sb.append(this.info);
        }
        return sb.toString();
    }

    public static String printLocal(List<ExceptionDescriptor> data) {
        String result=null;
        if (Objects.nonNull(data)) {
            StringBuilder sb = new StringBuilder();
            data.forEach((o)->{
                sb.append("\n");
                sb.append("--------------------------------------------------------------------------------------------------------------------------");
                sb.append(o.toString());
            });
            result = sb.toString();
        }
        return result;
    }

    public static class  LambadaCounter
    {
        private Integer count;
        public LambadaCounter()
        {
            this(0);
        }
        public LambadaCounter(Integer start)
        {
            this.count = Objects.isNull(start) ? 0 : start;
        }
        public LambadaCounter add()
        {
            this.count++;
            return this;
        }

        public Integer current()
        {
            return this.count;
        }

        public LambadaCounter reset(Integer start)
        {
            this.count=Objects.isNull(start) ? 0 : start;
            return this;
        }
        public LambadaCounter reset()
        {
            return this.reset(0);
        }

    }
}
