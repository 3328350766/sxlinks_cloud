////
//// Source code recreated from a .class file by IntelliJ IDEA
//// (powered by Fernflower decompiler)
////
//
//package com.sxlinks.interceptor;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class OnlineConfiguration implements WebMvcConfigurer {
//  public OnlineConfiguration() {
//  }
//
//  @Bean
//  public com.sxlinks.interceptor.a onlineInterceptor() {
//    return new com.sxlinks.interceptor.a();
//  }
//
//  public void addInterceptors(InterceptorRegistry registry) {
//    String[] var2 = new String[]{"/*.html", "/html/**", "/js/**", "/css/**", "/images/**"};
//    registry.addInterceptor(this.onlineInterceptor()).excludePathPatterns(var2).addPathPatterns(new String[]{"/online/cgform/api/**"});
//  }
//}
