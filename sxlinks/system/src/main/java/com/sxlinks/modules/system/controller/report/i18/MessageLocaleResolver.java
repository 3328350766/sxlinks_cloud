package com.sxlinks.modules.system.controller.report.i18;

import org.apache.catalina.connector.Request;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * 国际化解析器
 * bean的名字必须为localeResolver
 * @author lr
 * @since 2021-01-24

 */
public class MessageLocaleResolver implements LocaleResolver {

    private String localeHeader = "locale";

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        //从请求头中获取语言标识，类似zh-CN，注意中间要用"-"
        String language = request.getHeader(localeHeader);
        //当为空时,采用默认的
        if(StringUtils.isBlank(language)) {
            return Locale.SIMPLIFIED_CHINESE;
        }
        Locale locale = Locale.forLanguageTag(language);
        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {

    }

}
