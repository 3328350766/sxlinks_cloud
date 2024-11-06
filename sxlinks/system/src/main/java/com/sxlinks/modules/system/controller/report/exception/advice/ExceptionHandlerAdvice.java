package com.sxlinks.modules.system.controller.report.exception.advice;


import com.sxlinks.modules.system.controller.report.bean.ResponseBean;
import com.sxlinks.modules.system.controller.report.exception.BusinessException;
import com.sxlinks.modules.system.controller.report.exception.ErrorFieldResolver;
import com.sxlinks.modules.system.controller.report.i18.MessageSourceHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.NoSuchMessageException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.sxlinks.modules.system.controller.report.code.ResponseCode.FAIL_CODE;


/**
 * 全局异常处理
 *
 * @author lr
 * @since 2021-01-02
 */
@RestControllerAdvice
public class ExceptionHandlerAdvice {

    private Logger logger = LoggerFactory.getLogger(ExceptionHandlerAdvice.class);

    @Autowired
    private MessageSourceHolder messageSourceHolder;

    @Autowired
    private ErrorFieldResolver resolver;

    /**
     * 业务异常
     *
     * @param businessException
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseBean handleBusinessException(BusinessException businessException) {
        return ResponseBean.builder().code(businessException.getCode()).args(businessException.getArgs()).build();
    }

    /**
     * 参数校验异常
     *
     * @param ex
     * @return
     */
    @NotEmpty
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseBean methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        // 获取实体中，校验注解所在的字段名，注解名称
        FieldError fieldError = ex.getBindingResult().getFieldError();
        String message = fieldError.getDefaultMessage(); // Validation框架默认的提示信息
        String validationName = fieldError.getCode(); // code=Null\NotNull\NotBlank\NotEmpty\Size，与resource\i18n\message中的key对应
        String fieldName = fieldError.getField();
        fieldName = resolver.getFieldName(fieldName, ex.getParameter());
        List messageArgs = new ArrayList();
        messageArgs.add(fieldName);

        // 对于有参数的注解，提取格式化参数 @Size(min max) @Digits(integer,fraction) @Min(value) @Max(value) 参数是Integer @Pattern(value)是String
        String hasArgusValidationNames = "DecimalMax DecimalMin Max Min Size Digits Pattern";
        Object[] arguments = fieldError.getArguments();
        if (hasArgusValidationNames.indexOf(validationName) > -1 && arguments != null) {
            List<Object> list = Arrays.stream(arguments).filter(argusment -> (argusment instanceof Integer) || (argusment instanceof String) || (argusment instanceof Long)).collect(Collectors.toList());
            messageArgs.addAll(list);
        }

        try {
            Object[] args = messageArgs.toArray();
            message = messageSourceHolder.getMessage(validationName, args);
        } catch (NoSuchMessageException exception) {
            try {
                message = fieldName + messageSourceHolder.getMessage(message, null);
            } catch (Exception e) {
                message = fieldName + message;
            }
        }
        return ResponseBean.builder().code(FAIL_CODE).message(message).build();
    }

    /**
     * 业务异常
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResponseBean exception(Exception exception) {
        //返回值构建
        logger.error("系统异常", exception);
        ResponseBean.Builder builder = ResponseBean.builder();
        builder.code(FAIL_CODE);
        builder.message(messageSourceHolder.getMessage(FAIL_CODE, null));
        builder.args(new String[]{exception.getMessage()});
        return builder.build();
    }

}
