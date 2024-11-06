////
//// Source code recreated from a .class file by IntelliJ IDEA
//// (powered by Fernflower decompiler)
////
//
//package com.sxlinks.interceptor;
//
//import com.alibaba.fastjson.JSON;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.ArrayList;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import com.sxlinks.common.api.dto.OnlineAuthDTO;
//import com.sxlinks.common.api.vo.Result;
//import com.sxlinks.common.aspect.annotation.OnlineAuth;
//import com.sxlinks.common.system.api.ISysBaseAPI;
//import com.sxlinks.common.system.util.JwtUtil;
//import com.sxlinks.common.util.SpringContextUtils;
//import com.sxlinks.common.util.oConvertUtils;
//import com.sxlinks.modules.online.cgform.service.IOnlineBaseAPI;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.web.method.HandlerMethod;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//public class a implements HandlerInterceptor {
//    private static final Logger a = LoggerFactory.getLogger(a.class);
//    private IOnlineBaseAPI b;
//    private ISysBaseAPI c;
//    private static final String d = "/online/cgform";
//    private static final String[] e = new String[]{"/online/cgformInnerTableList", "/online/cgformErpList", "/online/cgformList", "/online/cgformTreeList", "/online/cgformTabList"};
//
//    public a() {
//    }
//
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        boolean var4 = handler.getClass().isAssignableFrom(HandlerMethod.class);
//        if (var4) {
//            OnlineAuth var5 = (OnlineAuth)((HandlerMethod)handler).getMethodAnnotation(OnlineAuth.class);
//            if (var5 != null) {
//                a.debug("===== online 菜单访问拦截器 =====");
//                String var6 = request.getRequestURI().substring(request.getContextPath().length());
//                var6 = this.a(var6);
//                String var7 = var5.value();
//                String var8 = var6.substring(var6.lastIndexOf(var7) + var7.length());
//                a.debug("拦截请求(" + request.getMethod() + ")：" + var6 + ",");
//                if ("form".equals(var7) && "DELETE".equals(request.getMethod())) {
//                    var8 = var8.substring(0, var8.lastIndexOf("/"));
//                }
//
//                String var9 = request.getParameter("tabletype");
//                if (this.b == null) {
//                    this.b = (IOnlineBaseAPI)SpringContextUtils.getBean(IOnlineBaseAPI.class);
//                }
//
//                var8 = this.b.getOnlineErpCode(var8, var9);
//                ArrayList var10 = new ArrayList();
//                String[] var11 = e;
//                int var12 = var11.length;
//
//                for(int var13 = 0; var13 < var12; ++var13) {
//                    String var14 = var11[var13];
//                    var10.add(var14 + var8);
//                }
//
//                if (this.c == null) {
//                    this.c = (ISysBaseAPI)SpringContextUtils.getBean(ISysBaseAPI.class);
//                }
//
//                String var15 = JwtUtil.getUserNameByToken(request);
//                OnlineAuthDTO var16 = new OnlineAuthDTO(var15, var10, "/online/cgform");
//                boolean var17 = this.c.hasOnlineAuth(var16);
//                if (!var17) {
//                    a.info("请求无权限(" + request.getMethod() + ")：" + var6);
//                    this.a(response, var7);
//                    return false;
//                }
//            }
//        }
//
//        return true;
//    }
//
//    private String a(String var1) {
//        String var2 = "";
//        if (oConvertUtils.isNotEmpty(var1)) {
//            var2 = var1.replace("\\", "/");
//            var2 = var2.replace("//", "/");
//            if (var2.indexOf("//") >= 0) {
//                var2 = this.a(var2);
//            }
//        }
//
//        return var2;
//    }
//
//    private void a(HttpServletResponse var1, String var2) {
//        PrintWriter var3 = null;
//        var1.setCharacterEncoding("UTF-8");
//        var1.setContentType("application/json; charset=utf-8");
//        var1.setHeader("auth", "fail");
//
//        try {
//            var3 = var1.getWriter();
//            if ("exportXls".equals(var2)) {
//                var3.print("");
//            } else {
//                Result var4 = Result.error("无权限访问(操作)");
//                var3.print(JSON.toJSON(var4));
//            }
//        } catch (IOException var8) {
//            a.error(var8.getMessage());
//        } finally {
//            if (var3 != null) {
//                var3.close();
//            }
//
//        }
//
//    }
//}
