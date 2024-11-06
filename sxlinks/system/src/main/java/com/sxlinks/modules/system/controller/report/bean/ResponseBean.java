package com.sxlinks.modules.system.controller.report.bean;




import com.sxlinks.modules.system.controller.report.code.ResponseCode;

import java.io.Serializable;
import java.util.Arrays;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/**
 * 响应体封装
 * @author lr
 * @since 2021-01-12
 */
public final class ResponseBean implements Serializable {
    public ResponseBean() {

    }

    /**
     * 响应编码
     */
    private String code;

    /**
     * 响应信息
     */
    private String message;

    /**
     * 响应参数
     */
    private Object[] args;

    private Object ext;

    /**
     * 响应数据
     */
    private Object data;

    private ResponseBean(Builder builder) {
        this.code = builder.code;
        this.args = builder.args;
        this.message = builder.message;
        this.data = builder.data;
        this.ext = builder.ext;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ResponseBean{");
        sb.append("code='").append(code).append('\'');
        sb.append(", message='").append(message).append('\'');
        sb.append(", args=").append(args == null ? "null" : Arrays.asList(args).toString());
        sb.append(", data=").append(data);
        sb.append('}');
        return sb.toString();
    }

    /**
     * 后续异步处理
     * 请求参数param
     *
     * @param consumer
     * @return
     */
    public ResponseBean thenAsync(Consumer<Object> consumer, Object param, Executor executor) {
        //异步执行
        executor.execute(() -> {
            consumer.accept(param);
        });
        return this;
    }

    /**
     * 后续同步处理
     * 请求参数param
     *
     * @param consumer
     * @return
     */
    public ResponseBean then(Consumer<Object> consumer, Object param) {
        //同步执行
        consumer.accept(param);
        return this;
    }

    /**
     * 用于创建ResponseBean实例
     *
     * @return
     */
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String code = ResponseCode.SUCCESS_CODE;
        private Object data;
        private String message;
        private Object[] args;
        private Object ext;
        private Builder() {

        }

        public Builder code(String code) {
            this.code = code;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder args(Object[] args) {
            this.args = args;
            return this;
        }

        public Builder data(Object data) {
            this.data = data;
            return this;
        }

        public Builder ext(Object data) {
            this.ext = ext;
            return this;
        }

        public ResponseBean build() {
            return new ResponseBean(this);
        }
    }

    public String getCode() {
        return code;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

	public String getMessage() {
		return message;
	}

    public void setCode(String code) {
        this.code = code;
    }

    public Object getExt(){
        return ext;
    }
    public void setExt(Object ext){
        this.ext = ext;
    }
}
