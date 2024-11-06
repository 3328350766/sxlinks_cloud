////
//// Source code recreated from a .class file by IntelliJ IDEA
//// (powered by Fernflower decompiler)
////
//
//package com.sxlinks.modules.online.cgform.util;
//
//import java.io.BufferedInputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collection;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Locale;
//import java.util.PropertyResourceBundle;
//import java.util.ResourceBundle;
//import org.apache.commons.lang.ArrayUtils;
//import org.apache.commons.lang.CharSetUtils;
//import org.apache.commons.lang.CharUtils;
//import org.apache.commons.lang.ObjectUtils;
//import org.apache.commons.lang.QstrEncodeUtil;
//import org.apache.commons.lang.StringEscapeUtils;
//import org.apache.commons.lang.StringUtils;
//import org.apache.commons.lang.WordUtils;
//import org.apache.commons.lang.text.StrBuilder;
//import org.apache.commons.net.SignatureUtil;
//
//public class c {
//    public static final String a = "";
//    public static final int b = -1;
//    public static boolean c;
//    public static boolean d;
//    private static final int g = 8192;
//    public static String e;
//    static String[] f;
//
//    public static boolean a(String var0) {
//        return var0 == null || var0.length() == 0;
//    }
//
//    public static boolean b(String var0) {
//        return !StringUtils.isEmpty(var0);
//    }
//
//    public static boolean a(Object var0) {
//        return var0 != null;
//    }
//
//    public c() {
//    }
//
//    public static boolean a(String var0, List<String> var1) {
//        if (var0 != null && var1 != null) {
//            Iterator var2 = var1.iterator();
//
//            String var3;
//            do {
//                if (!var2.hasNext()) {
//                    return false;
//                }
//
//                var3 = (String)var2.next();
//            } while(var3 == null || var0.indexOf(var3) == -1);
//
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    public static boolean c(String var0) {
//        int var1;
//        if (var0 != null && (var1 = var0.length()) != 0) {
//            for(int var2 = 0; var2 < var1; ++var2) {
//                if (!Character.isWhitespace(var0.charAt(var2))) {
//                    return false;
//                }
//            }
//
//            return true;
//        } else {
//            return true;
//        }
//    }
//
//    public static boolean d(String var0) {
//        return !StringUtils.isBlank(var0);
//    }
//
//    /** @deprecated */
//    public static String e(String var0) {
//        return var0 == null ? "" : var0.trim();
//    }
//
//    public static String f(String var0) {
//        return var0 == null ? null : var0.trim();
//    }
//
//    public static String g(String var0) {
//        String var1 = f(var0);
//        return a(var1) ? null : var1;
//    }
//
//    public static String h(String var0) {
//        return var0 == null ? "" : var0.trim();
//    }
//
//    public static String i(String var0) {
//        return a((String)var0, (String)null);
//    }
//
//    public static String j(String var0) {
//        if (var0 == null) {
//            return null;
//        } else {
//            var0 = a((String)var0, (String)null);
//            return var0.length() == 0 ? null : var0;
//        }
//    }
//
//    public static String k(String var0) {
//        return var0 == null ? "" : a((String)var0, (String)null);
//    }
//
//    public static String a(String var0, String var1) {
//        if (a(var0)) {
//            return var0;
//        } else {
//            var0 = b(var0, var1);
//            return c(var0, var1);
//        }
//    }
//
//    public static String b(String var0, String var1) {
//        int var2;
//        if (var0 != null && (var2 = var0.length()) != 0) {
//            int var3 = 0;
//            if (var1 == null) {
//                while(var3 != var2 && Character.isWhitespace(var0.charAt(var3))) {
//                    ++var3;
//                }
//            } else {
//                if (var1.length() == 0) {
//                    return var0;
//                }
//
//                while(var3 != var2 && var1.indexOf(var0.charAt(var3)) != -1) {
//                    ++var3;
//                }
//            }
//
//            return var0.substring(var3);
//        } else {
//            return var0;
//        }
//    }
//
//    public static String c(String var0, String var1) {
//        int var2;
//        if (var0 != null && (var2 = var0.length()) != 0) {
//            if (var1 == null) {
//                while(var2 != 0 && Character.isWhitespace(var0.charAt(var2 - 1))) {
//                    --var2;
//                }
//            } else {
//                if (var1.length() == 0) {
//                    return var0;
//                }
//
//                while(var2 != 0 && var1.indexOf(var0.charAt(var2 - 1)) != -1) {
//                    --var2;
//                }
//            }
//
//            return var0.substring(0, var2);
//        } else {
//            return var0;
//        }
//    }
//
//    public static String[] a(String[] var0) {
//        return a((String[])var0, (String)null);
//    }
//
//    public static String[] a(String[] var0, String var1) {
//        int var2;
//        if (var0 != null && (var2 = var0.length) != 0) {
//            String[] var3 = new String[var2];
//
//            for(int var4 = 0; var4 < var2; ++var4) {
//                var3[var4] = a(var0[var4], var1);
//            }
//
//            return var3;
//        } else {
//            return var0;
//        }
//    }
//
//    public static boolean d(String var0, String var1) {
//        return var0 == null ? var1 == null : var0.equals(var1);
//    }
//
//    public static boolean e(String var0, String var1) {
//        return var0 == null ? var1 == null : var0.equalsIgnoreCase(var1);
//    }
//
//    public static int a(String var0, char var1) {
//        return a(var0) ? -1 : var0.indexOf(var1);
//    }
//
//    public static int a(String var0, char var1, int var2) {
//        return a(var0) ? -1 : var0.indexOf(var1, var2);
//    }
//
//    public static int f(String var0, String var1) {
//        return var0 != null && var1 != null ? var0.indexOf(var1) : -1;
//    }
//
//    public static int a(String var0, String var1, int var2) {
//        return a(var0, var1, var2, false);
//    }
//
//    private static int a(String var0, String var1, int var2, boolean var3) {
//        if (var0 != null && var1 != null && var2 > 0) {
//            if (var1.length() == 0) {
//                return var3 ? var0.length() : 0;
//            } else {
//                int var4 = 0;
//                int var5 = var3 ? var0.length() : -1;
//
//                do {
//                    if (var3) {
//                        var5 = var0.lastIndexOf(var1, var5 - 1);
//                    } else {
//                        var5 = var0.indexOf(var1, var5 + 1);
//                    }
//
//                    if (var5 < 0) {
//                        return var5;
//                    }
//
//                    ++var4;
//                } while(var4 < var2);
//
//                return var5;
//            }
//        } else {
//            return -1;
//        }
//    }
//
//    public static int b(String var0, String var1, int var2) {
//        if (var0 != null && var1 != null) {
//            return var1.length() == 0 && var2 >= var0.length() ? var0.length() : var0.indexOf(var1, var2);
//        } else {
//            return -1;
//        }
//    }
//
//    public static int g(String var0, String var1) {
//        return c(var0, var1, 0);
//    }
//
//    public static int c(String var0, String var1, int var2) {
//        if (var0 != null && var1 != null) {
//            if (var2 < 0) {
//                var2 = 0;
//            }
//
//            int var3 = var0.length() - var1.length() + 1;
//            if (var2 > var3) {
//                return -1;
//            } else if (var1.length() == 0) {
//                return var2;
//            } else {
//                for(int var4 = var2; var4 < var3; ++var4) {
//                    if (var0.regionMatches(true, var4, var1, 0, var1.length())) {
//                        return var4;
//                    }
//                }
//
//                return -1;
//            }
//        } else {
//            return -1;
//        }
//    }
//
//    public static int b(String var0, char var1) {
//        return a(var0) ? -1 : var0.lastIndexOf(var1);
//    }
//
//    public static int b(String var0, char var1, int var2) {
//        return a(var0) ? -1 : var0.lastIndexOf(var1, var2);
//    }
//
//    public static int h(String var0, String var1) {
//        return var0 != null && var1 != null ? var0.lastIndexOf(var1) : -1;
//    }
//
//    public static int d(String var0, String var1, int var2) {
//        return a(var0, var1, var2, true);
//    }
//
//    public static int e(String var0, String var1, int var2) {
//        return var0 != null && var1 != null ? var0.lastIndexOf(var1, var2) : -1;
//    }
//
//    public static int i(String var0, String var1) {
//        return var0 != null && var1 != null ? f(var0, var1, var0.length()) : -1;
//    }
//
//    public static int f(String var0, String var1, int var2) {
//        if (var0 != null && var1 != null) {
//            if (var2 > var0.length() - var1.length()) {
//                var2 = var0.length() - var1.length();
//            }
//
//            if (var2 < 0) {
//                return -1;
//            } else if (var1.length() == 0) {
//                return var2;
//            } else {
//                for(int var3 = var2; var3 >= 0; --var3) {
//                    if (var0.regionMatches(true, var3, var1, 0, var1.length())) {
//                        return var3;
//                    }
//                }
//
//                return -1;
//            }
//        } else {
//            return -1;
//        }
//    }
//
//    public static boolean c(String var0, char var1) {
//        if (a(var0)) {
//            return false;
//        } else {
//            return var0.indexOf(var1) >= 0;
//        }
//    }
//
//    public static boolean j(String var0, String var1) {
//        if (var0 != null && var1 != null) {
//            return var0.indexOf(var1) >= 0;
//        } else {
//            return false;
//        }
//    }
//
//    public static boolean k(String var0, String var1) {
//        if (var0 != null && var1 != null) {
//            int var2 = var1.length();
//            int var3 = var0.length() - var2;
//
//            for(int var4 = 0; var4 <= var3; ++var4) {
//                if (var0.regionMatches(true, var4, var1, 0, var2)) {
//                    return true;
//                }
//            }
//
//            return false;
//        } else {
//            return false;
//        }
//    }
//
//    public static int a(String var0, char[] var1) {
//        if (!a(var0) && !ArrayUtils.isEmpty(var1)) {
//            int var2 = var0.length();
//            int var3 = var2 - 1;
//            int var4 = var1.length;
//            int var5 = var4 - 1;
//
//            for(int var6 = 0; var6 < var2; ++var6) {
//                char var7 = var0.charAt(var6);
//
//                for(int var8 = 0; var8 < var4; ++var8) {
//                    if (var1[var8] == var7) {
//                        if (var6 >= var3 || var8 >= var5) {
//                            return var6;
//                        }
//
//                        if (var1[var8 + 1] == var0.charAt(var6 + 1)) {
//                            return var6;
//                        }
//                    }
//                }
//            }
//
//            return -1;
//        } else {
//            return -1;
//        }
//    }
//
//    public static int l(String var0, String var1) {
//        return !a(var0) && !a(var1) ? a(var0, var1.toCharArray()) : -1;
//    }
//
//    public static boolean b(String var0, char[] var1) {
//        if (!a(var0) && !ArrayUtils.isEmpty(var1)) {
//            int var2 = var0.length();
//            int var3 = var1.length;
//            int var4 = var2 - 1;
//            int var5 = var3 - 1;
//
//            for(int var6 = 0; var6 < var2; ++var6) {
//                char var7 = var0.charAt(var6);
//
//                for(int var8 = 0; var8 < var3; ++var8) {
//                    if (var1[var8] == var7) {
//                        if (var8 == var5) {
//                            return true;
//                        }
//
//                        if (var6 < var4 && var1[var8 + 1] == var0.charAt(var6 + 1)) {
//                            return true;
//                        }
//                    }
//                }
//            }
//
//            return false;
//        } else {
//            return false;
//        }
//    }
//
//    public static boolean m(String var0, String var1) {
//        return var1 == null ? false : b(var0, var1.toCharArray());
//    }
//
//    public static int c(String var0, char[] var1) {
//        if (!a(var0) && !ArrayUtils.isEmpty(var1)) {
//            int var2 = var0.length();
//            int var3 = var2 - 1;
//            int var4 = var1.length;
//            int var5 = var4 - 1;
//
//            label35:
//            for(int var6 = 0; var6 < var2; ++var6) {
//                char var7 = var0.charAt(var6);
//
//                for(int var8 = 0; var8 < var4; ++var8) {
//                    if (var1[var8] == var7 && (var6 >= var3 || var8 >= var5 || var1[var8 + 1] == var0.charAt(var6 + 1))) {
//                        continue label35;
//                    }
//                }
//
//                return var6;
//            }
//
//            return -1;
//        } else {
//            return -1;
//        }
//    }
//
//    public static int n(String var0, String var1) {
//        if (!a(var0) && !a(var1)) {
//            int var2 = var0.length();
//
//            for(int var3 = 0; var3 < var2; ++var3) {
//                char var4 = var0.charAt(var3);
//                boolean var5 = var1.indexOf(var4) >= 0;
//                if (var3 + 1 < var2) {
//                    char var6 = var0.charAt(var3 + 1);
//                    if (var5 && var1.indexOf(var6) < 0) {
//                        return var3;
//                    }
//                } else if (!var5) {
//                    return var3;
//                }
//            }
//
//            return -1;
//        } else {
//            return -1;
//        }
//    }
//
//    public static boolean d(String var0, char[] var1) {
//        if (var1 != null && var0 != null) {
//            if (var0.length() == 0) {
//                return true;
//            } else if (var1.length == 0) {
//                return false;
//            } else {
//                return c(var0, var1) == -1;
//            }
//        } else {
//            return false;
//        }
//    }
//
//    public static boolean o(String var0, String var1) {
//        return var0 != null && var1 != null ? d(var0, var1.toCharArray()) : false;
//    }
//
//    public static boolean e(String var0, char[] var1) {
//        if (var0 != null && var1 != null) {
//            int var2 = var0.length();
//            int var3 = var2 - 1;
//            int var4 = var1.length;
//            int var5 = var4 - 1;
//
//            for(int var6 = 0; var6 < var2; ++var6) {
//                char var7 = var0.charAt(var6);
//
//                for(int var8 = 0; var8 < var4; ++var8) {
//                    if (var1[var8] == var7) {
//                        if (var8 == var5) {
//                            return false;
//                        }
//
//                        if (var6 < var3 && var1[var8 + 1] == var0.charAt(var6 + 1)) {
//                            return false;
//                        }
//                    }
//                }
//            }
//
//            return true;
//        } else {
//            return true;
//        }
//    }
//
//    public static boolean p(String var0, String var1) {
//        return var0 != null && var1 != null ? e(var0, var1.toCharArray()) : true;
//    }
//
//    public static int a(String var0, String[] var1) {
//        if (var0 != null && var1 != null) {
//            int var2 = var1.length;
//            int var3 = 2147483647;
//            boolean var4 = false;
//
//            for(int var5 = 0; var5 < var2; ++var5) {
//                String var6 = var1[var5];
//                if (var6 != null) {
//                    int var7 = var0.indexOf(var6);
//                    if (var7 != -1 && var7 < var3) {
//                        var3 = var7;
//                    }
//                }
//            }
//
//            return var3 == 2147483647 ? -1 : var3;
//        } else {
//            return -1;
//        }
//    }
//
//    public static int b(String var0, String[] var1) {
//        if (var0 != null && var1 != null) {
//            int var2 = var1.length;
//            int var3 = -1;
//            boolean var4 = false;
//
//            for(int var5 = 0; var5 < var2; ++var5) {
//                String var6 = var1[var5];
//                if (var6 != null) {
//                    int var7 = var0.lastIndexOf(var6);
//                    if (var7 > var3) {
//                        var3 = var7;
//                    }
//                }
//            }
//
//            return var3;
//        } else {
//            return -1;
//        }
//    }
//
//    public static String a(String var0, int var1) {
//        if (var0 == null) {
//            return null;
//        } else {
//            if (var1 < 0) {
//                var1 += var0.length();
//            }
//
//            if (var1 < 0) {
//                var1 = 0;
//            }
//
//            return var1 > var0.length() ? "" : var0.substring(var1);
//        }
//    }
//
//    public static String a(String var0, int var1, int var2) {
//        if (var0 == null) {
//            return null;
//        } else {
//            if (var2 < 0) {
//                var2 += var0.length();
//            }
//
//            if (var1 < 0) {
//                var1 += var0.length();
//            }
//
//            if (var2 > var0.length()) {
//                var2 = var0.length();
//            }
//
//            if (var1 > var2) {
//                return "";
//            } else {
//                if (var1 < 0) {
//                    var1 = 0;
//                }
//
//                if (var2 < 0) {
//                    var2 = 0;
//                }
//
//                return var0.substring(var1, var2);
//            }
//        }
//    }
//
//    public static String b(String var0, int var1) {
//        if (var0 == null) {
//            return null;
//        } else if (var1 < 0) {
//            return "";
//        } else {
//            return var0.length() <= var1 ? var0 : var0.substring(0, var1);
//        }
//    }
//
//    public static String c(String var0, int var1) {
//        if (var0 == null) {
//            return null;
//        } else if (var1 < 0) {
//            return "";
//        } else {
//            return var0.length() <= var1 ? var0 : var0.substring(var0.length() - var1);
//        }
//    }
//
//    public static String b(String var0, int var1, int var2) {
//        if (var0 == null) {
//            return null;
//        } else if (var2 >= 0 && var1 <= var0.length()) {
//            if (var1 < 0) {
//                var1 = 0;
//            }
//
//            return var0.length() <= var1 + var2 ? var0.substring(var1) : var0.substring(var1, var1 + var2);
//        } else {
//            return "";
//        }
//    }
//
//    public static String q(String var0, String var1) {
//        if (!a(var0) && var1 != null) {
//            if (var1.length() == 0) {
//                return "";
//            } else {
//                int var2 = var0.indexOf(var1);
//                return var2 == -1 ? var0 : var0.substring(0, var2);
//            }
//        } else {
//            return var0;
//        }
//    }
//
//    public static String r(String var0, String var1) {
//        if (a(var0)) {
//            return var0;
//        } else if (var1 == null) {
//            return "";
//        } else {
//            int var2 = var0.indexOf(var1);
//            return var2 == -1 ? "" : var0.substring(var2 + var1.length());
//        }
//    }
//
//    public static String s(String var0, String var1) {
//        if (!a(var0) && !a(var1)) {
//            int var2 = var0.lastIndexOf(var1);
//            return var2 == -1 ? var0 : var0.substring(0, var2);
//        } else {
//            return var0;
//        }
//    }
//
//    public static String t(String var0, String var1) {
//        if (a(var0)) {
//            return var0;
//        } else if (a(var1)) {
//            return "";
//        } else {
//            int var2 = var0.lastIndexOf(var1);
//            return var2 != -1 && var2 != var0.length() - var1.length() ? var0.substring(var2 + var1.length()) : "";
//        }
//    }
//
//    public static String u(String var0, String var1) {
//        return a(var0, var1, var1);
//    }
//
//    public static String a(String var0, String var1, String var2) {
//        if (var0 != null && var1 != null && var2 != null) {
//            int var3 = var0.indexOf(var1);
//            if (var3 != -1) {
//                int var4 = var0.indexOf(var2, var3 + var1.length());
//                if (var4 != -1) {
//                    return var0.substring(var3 + var1.length(), var4);
//                }
//            }
//
//            return null;
//        } else {
//            return null;
//        }
//    }
//
//    public static String[] b(String var0, String var1, String var2) {
//        if (var0 != null && !a(var1) && !a(var2)) {
//            int var3 = var0.length();
//            if (var3 == 0) {
//                return ArrayUtils.EMPTY_STRING_ARRAY;
//            } else {
//                int var4 = var2.length();
//                int var5 = var1.length();
//                ArrayList var6 = new ArrayList();
//
//                int var9;
//                for(int var7 = 0; var7 < var3 - var4; var7 = var9 + var4) {
//                    int var8 = var0.indexOf(var1, var7);
//                    if (var8 < 0) {
//                        break;
//                    }
//
//                    var8 += var5;
//                    var9 = var0.indexOf(var2, var8);
//                    if (var9 < 0) {
//                        break;
//                    }
//
//                    var6.add(var0.substring(var8, var9));
//                }
//
//                return var6.isEmpty() ? null : (String[])((String[])var6.toArray(new String[var6.size()]));
//            }
//        } else {
//            return null;
//        }
//    }
//
//    /** @deprecated */
//    public static String v(String var0, String var1) {
//        return a(var0, var1, var1);
//    }
//
//    /** @deprecated */
//    public static String c(String var0, String var1, String var2) {
//        return a(var0, var1, var2);
//    }
//
//    public static String[] l(String var0) {
//        return g(var0, (String)null, -1);
//    }
//
//    public static String[] d(String var0, char var1) {
//        return a(var0, var1, false);
//    }
//
//    public static String[] w(String var0, String var1) {
//        return c(var0, var1, -1, false);
//    }
//
//    public static String[] g(String var0, String var1, int var2) {
//        return c(var0, var1, var2, false);
//    }
//
//    public static String[] x(String var0, String var1) {
//        return b(var0, var1, -1, false);
//    }
//
//    public static String[] h(String var0, String var1, int var2) {
//        return b(var0, var1, var2, false);
//    }
//
//    public static String[] y(String var0, String var1) {
//        return b(var0, var1, -1, true);
//    }
//
//    public static String[] i(String var0, String var1, int var2) {
//        return b(var0, var1, var2, true);
//    }
//
//    private static String[] b(String var0, String var1, int var2, boolean var3) {
//        if (var0 == null) {
//            return null;
//        } else {
//            int var4 = var0.length();
//            if (var4 == 0) {
//                return ArrayUtils.EMPTY_STRING_ARRAY;
//            } else if (var1 != null && !"".equals(var1)) {
//                int var5 = var1.length();
//                ArrayList var6 = new ArrayList();
//                int var7 = 0;
//                int var8 = 0;
//                int var9 = 0;
//
//                while(var9 < var4) {
//                    var9 = var0.indexOf(var1, var8);
//                    if (var9 > -1) {
//                        if (var9 > var8) {
//                            ++var7;
//                            if (var7 == var2) {
//                                var9 = var4;
//                                var6.add(var0.substring(var8));
//                            } else {
//                                var6.add(var0.substring(var8, var9));
//                                var8 = var9 + var5;
//                            }
//                        } else {
//                            if (var3) {
//                                ++var7;
//                                if (var7 == var2) {
//                                    var9 = var4;
//                                    var6.add(var0.substring(var8));
//                                } else {
//                                    var6.add("");
//                                }
//                            }
//
//                            var8 = var9 + var5;
//                        }
//                    } else {
//                        var6.add(var0.substring(var8));
//                        var9 = var4;
//                    }
//                }
//
//                return (String[])((String[])var6.toArray(new String[var6.size()]));
//            } else {
//                return c(var0, (String)null, var2, var3);
//            }
//        }
//    }
//
//    public static String[] m(String var0) {
//        return c(var0, (String)null, -1, true);
//    }
//
//    public static String[] e(String var0, char var1) {
//        return a(var0, var1, true);
//    }
//
//    private static String[] a(String var0, char var1, boolean var2) {
//        if (var0 == null) {
//            return null;
//        } else {
//            int var3 = var0.length();
//            if (var3 == 0) {
//                return ArrayUtils.EMPTY_STRING_ARRAY;
//            } else {
//                ArrayList var4 = new ArrayList();
//                int var5 = 0;
//                int var6 = 0;
//                boolean var7 = false;
//                boolean var8 = false;
//
//                while(true) {
//                    while(var5 < var3) {
//                        if (var0.charAt(var5) == var1) {
//                            if (var7 || var2) {
//                                var4.add(var0.substring(var6, var5));
//                                var7 = false;
//                                var8 = true;
//                            }
//
//                            ++var5;
//                            var6 = var5;
//                        } else {
//                            var8 = false;
//                            var7 = true;
//                            ++var5;
//                        }
//                    }
//
//                    if (var7 || var2 && var8) {
//                        var4.add(var0.substring(var6, var5));
//                    }
//
//                    return (String[])((String[])var4.toArray(new String[var4.size()]));
//                }
//            }
//        }
//    }
//
//    public static String[] z(String var0, String var1) {
//        return c(var0, var1, -1, true);
//    }
//
//    public static String[] j(String var0, String var1, int var2) {
//        return c(var0, var1, var2, true);
//    }
//
//    private static String[] c(String var0, String var1, int var2, boolean var3) {
//        if (var0 == null) {
//            return null;
//        } else {
//            int var4 = var0.length();
//            if (var4 == 0) {
//                return ArrayUtils.EMPTY_STRING_ARRAY;
//            } else {
//                ArrayList var5 = new ArrayList();
//                int var6 = 1;
//                int var7 = 0;
//                int var8 = 0;
//                boolean var9 = false;
//                boolean var10 = false;
//                if (var1 != null) {
//                    if (var1.length() != 1) {
//                        label87:
//                        while(true) {
//                            while(true) {
//                                if (var7 >= var4) {
//                                    break label87;
//                                }
//
//                                if (var1.indexOf(var0.charAt(var7)) >= 0) {
//                                    if (var9 || var3) {
//                                        var10 = true;
//                                        if (var6++ == var2) {
//                                            var7 = var4;
//                                            var10 = false;
//                                        }
//
//                                        var5.add(var0.substring(var8, var7));
//                                        var9 = false;
//                                    }
//
//                                    ++var7;
//                                    var8 = var7;
//                                } else {
//                                    var10 = false;
//                                    var9 = true;
//                                    ++var7;
//                                }
//                            }
//                        }
//                    } else {
//                        char var11 = var1.charAt(0);
//
//                        label71:
//                        while(true) {
//                            while(true) {
//                                if (var7 >= var4) {
//                                    break label71;
//                                }
//
//                                if (var0.charAt(var7) == var11) {
//                                    if (var9 || var3) {
//                                        var10 = true;
//                                        if (var6++ == var2) {
//                                            var7 = var4;
//                                            var10 = false;
//                                        }
//
//                                        var5.add(var0.substring(var8, var7));
//                                        var9 = false;
//                                    }
//
//                                    ++var7;
//                                    var8 = var7;
//                                } else {
//                                    var10 = false;
//                                    var9 = true;
//                                    ++var7;
//                                }
//                            }
//                        }
//                    }
//                } else {
//                    label103:
//                    while(true) {
//                        while(true) {
//                            if (var7 >= var4) {
//                                break label103;
//                            }
//
//                            if (Character.isWhitespace(var0.charAt(var7))) {
//                                if (var9 || var3) {
//                                    var10 = true;
//                                    if (var6++ == var2) {
//                                        var7 = var4;
//                                        var10 = false;
//                                    }
//
//                                    var5.add(var0.substring(var8, var7));
//                                    var9 = false;
//                                }
//
//                                ++var7;
//                                var8 = var7;
//                            } else {
//                                var10 = false;
//                                var9 = true;
//                                ++var7;
//                            }
//                        }
//                    }
//                }
//
//                if (var9 || var3 && var10) {
//                    var5.add(var0.substring(var8, var7));
//                }
//
//                return (String[])((String[])var5.toArray(new String[var5.size()]));
//            }
//        }
//    }
//
//    public static String[] n(String var0) {
//        return a(var0, false);
//    }
//
//    public static String[] o(String var0) {
//        return a(var0, true);
//    }
//
//    private static String[] a(String var0, boolean var1) {
//        if (var0 == null) {
//            return null;
//        } else if (var0.length() == 0) {
//            return ArrayUtils.EMPTY_STRING_ARRAY;
//        } else {
//            char[] var2 = var0.toCharArray();
//            ArrayList var3 = new ArrayList();
//            int var4 = 0;
//            int var5 = Character.getType(var2[var4]);
//
//            for(int var6 = var4 + 1; var6 < var2.length; ++var6) {
//                int var7 = Character.getType(var2[var6]);
//                if (var7 != var5) {
//                    if (var1 && var7 == 2 && var5 == 1) {
//                        int var8 = var6 - 1;
//                        if (var8 != var4) {
//                            var3.add(new String(var2, var4, var8 - var4));
//                            var4 = var8;
//                        }
//                    } else {
//                        var3.add(new String(var2, var4, var6 - var4));
//                        var4 = var6;
//                    }
//
//                    var5 = var7;
//                }
//            }
//
//            var3.add(new String(var2, var4, var2.length - var4));
//            return (String[])((String[])var3.toArray(new String[var3.size()]));
//        }
//    }
//
//    /** @deprecated */
//    public static String a(Object[] var0) {
//        return a((Object[])var0, (String)null);
//    }
//
//    public static String b(Object[] var0) {
//        return a((Object[])var0, (String)null);
//    }
//
//    public static String a(Object[] var0, char var1) {
//        return var0 == null ? null : a(var0, var1, 0, var0.length);
//    }
//
//    public static String a(Object[] var0, char var1, int var2, int var3) {
//        if (var0 == null) {
//            return null;
//        } else {
//            int var4 = var3 - var2;
//            if (var4 <= 0) {
//                return "";
//            } else {
//                var4 *= (var0[var2] == null ? 16 : var0[var2].toString().length()) + 1;
//                StrBuilder var5 = new StrBuilder(var4);
//
//                for(int var6 = var2; var6 < var3; ++var6) {
//                    if (var6 > var2) {
//                        var5.append(var1);
//                    }
//
//                    if (var0[var6] != null) {
//                        var5.append(var0[var6]);
//                    }
//                }
//
//                return var5.toString();
//            }
//        }
//    }
//
//    public static String a(Object[] var0, String var1) {
//        return var0 == null ? null : a((Object[])var0, var1, 0, var0.length);
//    }
//
//    public static String a(Object[] var0, String var1, int var2, int var3) {
//        if (var0 == null) {
//            return null;
//        } else {
//            if (var1 == null) {
//                var1 = "";
//            }
//
//            int var4 = var3 - var2;
//            if (var4 <= 0) {
//                return "";
//            } else {
//                var4 *= (var0[var2] == null ? 16 : var0[var2].toString().length()) + var1.length();
//                StrBuilder var5 = new StrBuilder(var4);
//
//                for(int var6 = var2; var6 < var3; ++var6) {
//                    if (var6 > var2) {
//                        var5.append(var1);
//                    }
//
//                    if (var0[var6] != null) {
//                        var5.append(var0[var6]);
//                    }
//                }
//
//                return var5.toString();
//            }
//        }
//    }
//
//    public static String a(Iterator var0, char var1) {
//        if (var0 == null) {
//            return null;
//        } else if (!var0.hasNext()) {
//            return "";
//        } else {
//            Object var2 = var0.next();
//            if (!var0.hasNext()) {
//                return ObjectUtils.toString(var2);
//            } else {
//                StrBuilder var3 = new StrBuilder(256);
//                if (var2 != null) {
//                    var3.append(var2);
//                }
//
//                while(var0.hasNext()) {
//                    var3.append(var1);
//                    Object var4 = var0.next();
//                    if (var4 != null) {
//                        var3.append(var4);
//                    }
//                }
//
//                return var3.toString();
//            }
//        }
//    }
//
//    public static String a(Iterator var0, String var1) {
//        if (var0 == null) {
//            return null;
//        } else if (!var0.hasNext()) {
//            return "";
//        } else {
//            Object var2 = var0.next();
//            if (!var0.hasNext()) {
//                return ObjectUtils.toString(var2);
//            } else {
//                StrBuilder var3 = new StrBuilder(256);
//                if (var2 != null) {
//                    var3.append(var2);
//                }
//
//                while(var0.hasNext()) {
//                    if (var1 != null) {
//                        var3.append(var1);
//                    }
//
//                    Object var4 = var0.next();
//                    if (var4 != null) {
//                        var3.append(var4);
//                    }
//                }
//
//                return var3.toString();
//            }
//        }
//    }
//
//    private static void b() {
//        String var0 = "kOzlPKX8Es8/FwQdXX60NZDs5Tyl/BLPPxcEHV1+tDWQ7OU8pfwSzz8XBB1dfrQ1iN5zqKZaPYep6qn8X+n2QLKRXOKp2vFQjVEFS7T75CGCnTDPggguLlLiSRDR+DN00a2paweobc4zkqYP/0gU+d4jODOhQvZ9DFAsE4kQZpsXOzNfRrTeD+fWUnMFtAcuP5wfzEsz9Gm/ENqSW/sf0JQaTCWAxHmqaMpauPUZXNWEiuzRhMaXSDRmD4nV1DOcTqvDj5BtUWWdnJQV+by4VjVceI6gYuC5E3R+m4p6QG4wiASRPe+mpGacCousLwjL6a6Zx+iA2MXhQiPM6WjQTWIWA3TKwu105/ojzopukCuQ7OU8pfwSzz8XBB1dfrQ1kOzlPKX8Es8/FwQdXX60Nb+YHwc5536J89tvlGzFHGI=";
//
//        try {
//            if (f == null || f.length == 0) {
//                ResourceBundle var1 = a();
//                if (var1 == null) {
//                    var1 = ResourceBundle.getBundle("jeecglic");
//                }
//
//                if (QstrEncodeUtil.isDocker()) {
//                    f = new String[]{SignatureUtil.getDockerLicense()};
//                } else {
//                    f = var1.getString("licence").split(",");
//                }
//            }
//
//            if (!b(f, l.getssssserver()) && !b(f, l.getssss())) {
//                System.out.println(" Computer SN ：  " + l.getmachine_code());
//                String var6 = k.c(var0, "123456");
//                System.err.println(var6);
//                System.exit(0);
//            }
//        } catch (Exception var5) {
//            try {
//                System.out.println(" Computer SN ：  " + l.getmachine_code());
//                String var2 = k.c(var0, "123456");
//                System.err.println(var2);
//                System.exit(0);
//            } catch (Exception var4) {
//            }
//        }
//
//    }
//
//    public static String a(Collection var0, char var1) {
//        return var0 == null ? null : a(var0.iterator(), var1);
//    }
//
//    public static String a(Collection var0, String var1) {
//        return var0 == null ? null : a(var0.iterator(), var1);
//    }
//
//    /** @deprecated */
//    public static String p(String var0) {
//        return var0 == null ? null : CharSetUtils.delete(var0, " \t\r\n\b");
//    }
//
//    public static String q(String var0) {
//        if (a(var0)) {
//            return var0;
//        } else {
//            int var1 = var0.length();
//            char[] var2 = new char[var1];
//            int var3 = 0;
//
//            for(int var4 = 0; var4 < var1; ++var4) {
//                if (!Character.isWhitespace(var0.charAt(var4))) {
//                    var2[var3++] = var0.charAt(var4);
//                }
//            }
//
//            if (var3 == var1) {
//                return var0;
//            } else {
//                return new String(var2, 0, var3);
//            }
//        }
//    }
//
//    public static String A(String var0, String var1) {
//        if (!a(var0) && !a(var1)) {
//            return var0.startsWith(var1) ? var0.substring(var1.length()) : var0;
//        } else {
//            return var0;
//        }
//    }
//
//    public static String B(String var0, String var1) {
//        if (!a(var0) && !a(var1)) {
//            return T(var0, var1) ? var0.substring(var1.length()) : var0;
//        } else {
//            return var0;
//        }
//    }
//
//    public static String C(String var0, String var1) {
//        if (!a(var0) && !a(var1)) {
//            return var0.endsWith(var1) ? var0.substring(0, var0.length() - var1.length()) : var0;
//        } else {
//            return var0;
//        }
//    }
//
//    public static String D(String var0, String var1) {
//        if (!a(var0) && !a(var1)) {
//            return V(var0, var1) ? var0.substring(0, var0.length() - var1.length()) : var0;
//        } else {
//            return var0;
//        }
//    }
//
//    public static String E(String var0, String var1) {
//        return !a(var0) && !a(var1) ? a(var0, var1, "", -1) : var0;
//    }
//
//    public static String f(String var0, char var1) {
//        if (!a(var0) && var0.indexOf(var1) != -1) {
//            char[] var2 = var0.toCharArray();
//            int var3 = 0;
//
//            for(int var4 = 0; var4 < var2.length; ++var4) {
//                if (var2[var4] != var1) {
//                    var2[var3++] = var2[var4];
//                }
//            }
//
//            return new String(var2, 0, var3);
//        } else {
//            return var0;
//        }
//    }
//
//    public static String d(String var0, String var1, String var2) {
//        return a(var0, var1, var2, 1);
//    }
//
//    public static String e(String var0, String var1, String var2) {
//        return a(var0, var1, var2, -1);
//    }
//
//    public static String a(String var0, String var1, String var2, int var3) {
//        if (!a(var0) && !a(var1) && var2 != null && var3 != 0) {
//            int var4 = 0;
//            int var5 = var0.indexOf(var1, var4);
//            if (var5 == -1) {
//                return var0;
//            } else {
//                int var6 = var1.length();
//                int var7 = var2.length() - var6;
//                var7 = var7 < 0 ? 0 : var7;
//                var7 *= var3 < 0 ? 16 : (var3 > 64 ? 64 : var3);
//
//                StrBuilder var8;
//                for(var8 = new StrBuilder(var0.length() + var7); var5 != -1; var5 = var0.indexOf(var1, var4)) {
//                    var8.append(var0.substring(var4, var5)).append(var2);
//                    var4 = var5 + var6;
//                    --var3;
//                    if (var3 == 0) {
//                        break;
//                    }
//                }
//
//                var8.append(var0.substring(var4));
//                return var8.toString();
//            }
//        } else {
//            return var0;
//        }
//    }
//
//    public static String a(String var0, String[] var1, String[] var2) {
//        return a(var0, var1, var2, false, 0);
//    }
//
//    private static void c() {
//        String var0 = "kOzlPKX8Es8/FwQdXX60NZDs5Tyl/BLPPxcEHV1+tDWQ7OU8pfwSzz8XBB1dfrQ1iN5zqKZaPYep6qn8X+n2QLKRXOKp2vFQjVEFS7T75CGCnTDPggguLlLiSRDR+DN00a2paweobc4zkqYP/0gU+d4jODOhQvZ9DFAsE4kQZpsXOzNfRrTeD+fWUnMFtAcuP5wfzEsz9Gm/ENqSW/sf0JQaTCWAxHmqaMpauPUZXNWEiuzRhMaXSDRmD4nV1DOcTqvDj5BtUWWdnJQV+by4VjVceI6gYuC5E3R+m4p6QG4wiASRPe+mpGacCousLwjL6a6Zx+iA2MXhQiPM6WjQTWIWA3TKwu105/ojzopukCuQ7OU8pfwSzz8XBB1dfrQ1kOzlPKX8Es8/FwQdXX60Nb+YHwc5536J89tvlGzFHGI=";
//
//        try {
//            if (f == null || f.length == 0) {
//                ResourceBundle var1 = a();
//                if (var1 == null) {
//                    var1 = ResourceBundle.getBundle("jeecglic");
//                }
//
//                if (QstrEncodeUtil.isDocker()) {
//                    f = new String[]{SignatureUtil.getDockerLicense()};
//                } else {
//                    f = var1.getString("licence").split(",");
//                }
//            }
//
//            if (!b(f, l.getssssserver()) && !b(f, l.getssss())) {
//                System.out.println(" Computer SN ：  " + l.getmachine_code());
//                String var6 = k.c(var0, "123456");
//                System.err.println(var6);
//                System.exit(0);
//            }
//        } catch (Exception var5) {
//            try {
//                System.out.println(" Computer SN ：  " + l.getmachine_code());
//                String var2 = k.c(var0, "123456");
//                System.err.println(var2);
//                System.exit(0);
//            } catch (Exception var4) {
//                System.exit(0);
//            }
//        }
//
//    }
//
//    public static String b(String var0, String[] var1, String[] var2) {
//        int var3 = var1 == null ? 0 : var1.length;
//        return a(var0, var1, var2, true, var3);
//    }
//
//    private static String a(String var0, String[] var1, String[] var2, boolean var3, int var4) {
//        if (var0 != null && var0.length() != 0 && var1 != null && var1.length != 0 && var2 != null && var2.length != 0) {
//            if (var4 < 0) {
//                throw new IllegalStateException("TimeToLive of " + var4 + " is less than 0: " + var0);
//            } else {
//                int var5 = var1.length;
//                int var6 = var2.length;
//                if (var5 != var6) {
//                    throw new IllegalArgumentException("Search and Replace array lengths don't match: " + var5 + " vs " + var6);
//                } else {
//                    boolean[] var7 = new boolean[var5];
//                    int var8 = -1;
//                    int var9 = -1;
//                    boolean var10 = true;
//
//                    int var11;
//                    int var16;
//                    for(var11 = 0; var11 < var5; ++var11) {
//                        if (!var7[var11] && var1[var11] != null && var1[var11].length() != 0 && var2[var11] != null) {
//                            var16 = var0.indexOf(var1[var11]);
//                            if (var16 == -1) {
//                                var7[var11] = true;
//                            } else if (var8 == -1 || var16 < var8) {
//                                var8 = var16;
//                                var9 = var11;
//                            }
//                        }
//                    }
//
//                    if (var8 == -1) {
//                        return var0;
//                    } else {
//                        var11 = 0;
//                        int var12 = 0;
//
//                        int var14;
//                        for(int var13 = 0; var13 < var1.length; ++var13) {
//                            if (var1[var13] != null && var2[var13] != null) {
//                                var14 = var2[var13].length() - var1[var13].length();
//                                if (var14 > 0) {
//                                    var12 += 3 * var14;
//                                }
//                            }
//                        }
//
//                        var12 = Math.min(var12, var0.length() / 5);
//                        StrBuilder var17 = new StrBuilder(var0.length() + var12);
//
//                        while(var8 != -1) {
//                            for(var14 = var11; var14 < var8; ++var14) {
//                                var17.append(var0.charAt(var14));
//                            }
//
//                            var17.append(var2[var9]);
//                            var11 = var8 + var1[var9].length();
//                            var8 = -1;
//                            var9 = -1;
//                            var10 = true;
//
//                            for(var14 = 0; var14 < var5; ++var14) {
//                                if (!var7[var14] && var1[var14] != null && var1[var14].length() != 0 && var2[var14] != null) {
//                                    var16 = var0.indexOf(var1[var14], var11);
//                                    if (var16 == -1) {
//                                        var7[var14] = true;
//                                    } else if (var8 == -1 || var16 < var8) {
//                                        var8 = var16;
//                                        var9 = var14;
//                                    }
//                                }
//                            }
//                        }
//
//                        var14 = var0.length();
//
//                        for(int var15 = var11; var15 < var14; ++var15) {
//                            var17.append(var0.charAt(var15));
//                        }
//
//                        String var18 = var17.toString();
//                        if (!var3) {
//                            return var18;
//                        } else {
//                            return a(var18, var1, var2, var3, var4 - 1);
//                        }
//                    }
//                }
//            }
//        } else {
//            return var0;
//        }
//    }
//
//    public static String a(String var0, char var1, char var2) {
//        return var0 == null ? null : var0.replace(var1, var2);
//    }
//
//    public static String f(String var0, String var1, String var2) {
//        if (!a(var0) && !a(var1)) {
//            if (var2 == null) {
//                var2 = "";
//            }
//
//            boolean var3 = false;
//            int var4 = var2.length();
//            int var5 = var0.length();
//            StrBuilder var6 = new StrBuilder(var5);
//
//            for(int var7 = 0; var7 < var5; ++var7) {
//                char var8 = var0.charAt(var7);
//                int var9 = var1.indexOf(var8);
//                if (var9 >= 0) {
//                    var3 = true;
//                    if (var9 < var4) {
//                        var6.append(var2.charAt(var9));
//                    }
//                } else {
//                    var6.append(var8);
//                }
//            }
//
//            if (var3) {
//                return var6.toString();
//            } else {
//                return var0;
//            }
//        } else {
//            return var0;
//        }
//    }
//
//    /** @deprecated */
//    public static String a(String var0, String var1, int var2, int var3) {
//        return (new StrBuilder(var2 + var1.length() + var0.length() - var3 + 1)).append(var0.substring(0, var2)).append(var1).append(var0.substring(var3)).toString();
//    }
//
//    public static String b(String var0, String var1, int var2, int var3) {
//        if (var0 == null) {
//            return null;
//        } else {
//            if (var1 == null) {
//                var1 = "";
//            }
//
//            int var4 = var0.length();
//            if (var2 < 0) {
//                var2 = 0;
//            }
//
//            if (var2 > var4) {
//                var2 = var4;
//            }
//
//            if (var3 < 0) {
//                var3 = 0;
//            }
//
//            if (var3 > var4) {
//                var3 = var4;
//            }
//
//            if (var2 > var3) {
//                int var5 = var2;
//                var2 = var3;
//                var3 = var5;
//            }
//
//            return (new StrBuilder(var4 + var2 - var3 + var1.length() + 1)).append(var0.substring(0, var2)).append(var1).append(var0.substring(var3)).toString();
//        }
//    }
//
//    public static String r(String var0) {
//        if (a(var0)) {
//            return var0;
//        } else if (var0.length() == 1) {
//            char var3 = var0.charAt(0);
//            return var3 != '\r' && var3 != '\n' ? var0 : "";
//        } else {
//            int var1 = var0.length() - 1;
//            char var2 = var0.charAt(var1);
//            if (var2 == '\n') {
//                if (var0.charAt(var1 - 1) == '\r') {
//                    --var1;
//                }
//            } else if (var2 != '\r') {
//                ++var1;
//            }
//
//            return var0.substring(0, var1);
//        }
//    }
//
//    public static String F(String var0, String var1) {
//        if (!a(var0) && var1 != null) {
//            return var0.endsWith(var1) ? var0.substring(0, var0.length() - var1.length()) : var0;
//        } else {
//            return var0;
//        }
//    }
//
//    /** @deprecated */
//    public static String s(String var0) {
//        return G(var0, "\n");
//    }
//
//    /** @deprecated */
//    public static String G(String var0, String var1) {
//        if (var0.length() == 0) {
//            return var0;
//        } else {
//            String var2 = var0.substring(var0.length() - var1.length());
//            return var1.equals(var2) ? var0.substring(0, var0.length() - var1.length()) : var0;
//        }
//    }
//
//    /** @deprecated */
//    public static String H(String var0, String var1) {
//        int var2 = var0.lastIndexOf(var1);
//        if (var2 == var0.length() - var1.length()) {
//            return var1;
//        } else {
//            return var2 != -1 ? var0.substring(var2) : "";
//        }
//    }
//
//    /** @deprecated */
//    public static String I(String var0, String var1) {
//        int var2 = var0.indexOf(var1);
//        return var2 == -1 ? var0 : var0.substring(var2 + var1.length());
//    }
//
//    /** @deprecated */
//    public static String J(String var0, String var1) {
//        int var2 = var0.indexOf(var1);
//        return var2 == -1 ? "" : var0.substring(0, var2 + var1.length());
//    }
//
//    public static String t(String var0) {
//        if (var0 == null) {
//            return null;
//        } else {
//            int var1 = var0.length();
//            if (var1 < 2) {
//                return "";
//            } else {
//                int var2 = var1 - 1;
//                String var3 = var0.substring(0, var2);
//                char var4 = var0.charAt(var2);
//                return var4 == '\n' && var3.charAt(var2 - 1) == '\r' ? var3.substring(0, var2 - 1) : var3;
//            }
//        }
//    }
//
//    /** @deprecated */
//    public static String u(String var0) {
//        int var1 = var0.length() - 1;
//        if (var1 <= 0) {
//            return "";
//        } else {
//            char var2 = var0.charAt(var1);
//            if (var2 == '\n') {
//                if (var0.charAt(var1 - 1) == '\r') {
//                    --var1;
//                }
//            } else {
//                ++var1;
//            }
//
//            return var0.substring(0, var1);
//        }
//    }
//
//    /** @deprecated */
//    public static String v(String var0) {
//        return StringEscapeUtils.escapeJava(var0);
//    }
//
//    public static String d(String var0, int var1) {
//        if (var0 == null) {
//            return null;
//        } else if (var1 <= 0) {
//            return "";
//        } else {
//            int var2 = var0.length();
//            if (var1 != 1 && var2 != 0) {
//                if (var2 == 1 && var1 <= 8192) {
//                    return a(var1, var0.charAt(0));
//                } else {
//                    int var3 = var2 * var1;
//                    switch(var2) {
//                        case 1:
//                            char var4 = var0.charAt(0);
//                            char[] var5 = new char[var3];
//
//                            for(int var11 = var1 - 1; var11 >= 0; --var11) {
//                                var5[var11] = var4;
//                            }
//
//                            return new String(var5);
//                        case 2:
//                            char var6 = var0.charAt(0);
//                            char var7 = var0.charAt(1);
//                            char[] var8 = new char[var3];
//
//                            for(int var9 = var1 * 2 - 2; var9 >= 0; --var9) {
//                                var8[var9] = var6;
//                                var8[var9 + 1] = var7;
//                                --var9;
//                            }
//
//                            return new String(var8);
//                        default:
//                            StrBuilder var12 = new StrBuilder(var3);
//
//                            for(int var10 = 0; var10 < var1; ++var10) {
//                                var12.append(var0);
//                            }
//
//                            return var12.toString();
//                    }
//                }
//            } else {
//                return var0;
//            }
//        }
//    }
//
//    public static String k(String var0, String var1, int var2) {
//        if (var0 != null && var1 != null) {
//            String var3 = d(var0 + var1, var2);
//            return C(var3, var1);
//        } else {
//            return d(var0, var2);
//        }
//    }
//
//    private static String a(int var0, char var1) throws IndexOutOfBoundsException {
//        if (var0 < 0) {
//            throw new IndexOutOfBoundsException("Cannot pad a negative amount: " + var0);
//        } else {
//            char[] var2 = new char[var0];
//
//            for(int var3 = 0; var3 < var2.length; ++var3) {
//                var2[var3] = var1;
//            }
//
//            return new String(var2);
//        }
//    }
//
//    public static String e(String var0, int var1) {
//        return a(var0, var1, ' ');
//    }
//
//    public static String a(String var0, int var1, char var2) {
//        if (var0 == null) {
//            return null;
//        } else {
//            int var3 = var1 - var0.length();
//            if (var3 <= 0) {
//                return var0;
//            } else {
//                return var3 > 8192 ? a(var0, var1, String.valueOf(var2)) : var0.concat(a(var3, var2));
//            }
//        }
//    }
//
//    public static String a(String var0, int var1, String var2) {
//        if (var0 == null) {
//            return null;
//        } else {
//            if (a(var2)) {
//                var2 = " ";
//            }
//
//            int var3 = var2.length();
//            int var4 = var0.length();
//            int var5 = var1 - var4;
//            if (var5 <= 0) {
//                return var0;
//            } else if (var3 == 1 && var5 <= 8192) {
//                return a(var0, var1, var2.charAt(0));
//            } else if (var5 == var3) {
//                return var0.concat(var2);
//            } else if (var5 < var3) {
//                return var0.concat(var2.substring(0, var5));
//            } else {
//                char[] var6 = new char[var5];
//                char[] var7 = var2.toCharArray();
//
//                for(int var8 = 0; var8 < var5; ++var8) {
//                    var6[var8] = var7[var8 % var3];
//                }
//
//                return var0.concat(new String(var6));
//            }
//        }
//    }
//
//    public static String f(String var0, int var1) {
//        return b(var0, var1, ' ');
//    }
//
//    public static String b(String var0, int var1, char var2) {
//        if (var0 == null) {
//            return null;
//        } else {
//            int var3 = var1 - var0.length();
//            if (var3 <= 0) {
//                return var0;
//            } else {
//                return var3 > 8192 ? b(var0, var1, String.valueOf(var2)) : a(var3, var2).concat(var0);
//            }
//        }
//    }
//
//    public static String b(String var0, int var1, String var2) {
//        if (var0 == null) {
//            return null;
//        } else {
//            if (a(var2)) {
//                var2 = " ";
//            }
//
//            int var3 = var2.length();
//            int var4 = var0.length();
//            int var5 = var1 - var4;
//            if (var5 <= 0) {
//                return var0;
//            } else if (var3 == 1 && var5 <= 8192) {
//                return b(var0, var1, var2.charAt(0));
//            } else if (var5 == var3) {
//                return var2.concat(var0);
//            } else if (var5 < var3) {
//                return var2.substring(0, var5).concat(var0);
//            } else {
//                char[] var6 = new char[var5];
//                char[] var7 = var2.toCharArray();
//
//                for(int var8 = 0; var8 < var5; ++var8) {
//                    var6[var8] = var7[var8 % var3];
//                }
//
//                return (new String(var6)).concat(var0);
//            }
//        }
//    }
//
//    public static int w(String var0) {
//        return var0 == null ? 0 : var0.length();
//    }
//
//    public static String g(String var0, int var1) {
//        return c(var0, var1, ' ');
//    }
//
//    public static String c(String var0, int var1, char var2) {
//        if (var0 != null && var1 > 0) {
//            int var3 = var0.length();
//            int var4 = var1 - var3;
//            if (var4 <= 0) {
//                return var0;
//            } else {
//                var0 = b(var0, var3 + var4 / 2, var2);
//                var0 = a(var0, var1, var2);
//                return var0;
//            }
//        } else {
//            return var0;
//        }
//    }
//
//    public static String c(String var0, int var1, String var2) {
//        if (var0 != null && var1 > 0) {
//            if (a(var2)) {
//                var2 = " ";
//            }
//
//            int var3 = var0.length();
//            int var4 = var1 - var3;
//            if (var4 <= 0) {
//                return var0;
//            } else {
//                var0 = b(var0, var3 + var4 / 2, var2);
//                var0 = a(var0, var1, var2);
//                return var0;
//            }
//        } else {
//            return var0;
//        }
//    }
//
//    public static String x(String var0) {
//        return var0 == null ? null : var0.toUpperCase();
//    }
//
//    public static String a(String var0, Locale var1) {
//        return var0 == null ? null : var0.toUpperCase(var1);
//    }
//
//    public static String y(String var0) {
//        return var0 == null ? null : var0.toLowerCase();
//    }
//
//    public static String b(String var0, Locale var1) {
//        return var0 == null ? null : var0.toLowerCase(var1);
//    }
//
//    public static String z(String var0) {
//        int var1;
//        return var0 != null && (var1 = var0.length()) != 0 ? (new StrBuilder(var1)).append(Character.toTitleCase(var0.charAt(0))).append(var0.substring(1)).toString() : var0;
//    }
//
//    /** @deprecated */
//    public static String A(String var0) {
//        return z(var0);
//    }
//
//    public static String B(String var0) {
//        int var1;
//        return var0 != null && (var1 = var0.length()) != 0 ? (new StrBuilder(var1)).append(Character.toLowerCase(var0.charAt(0))).append(var0.substring(1)).toString() : var0;
//    }
//
//    /** @deprecated */
//    public static String C(String var0) {
//        return B(var0);
//    }
//
//    public static String D(String var0) {
//        int var1;
//        if (var0 != null && (var1 = var0.length()) != 0) {
//            StrBuilder var2 = new StrBuilder(var1);
//            boolean var3 = false;
//
//            for(int var4 = 0; var4 < var1; ++var4) {
//                char var5 = var0.charAt(var4);
//                if (Character.isUpperCase(var5)) {
//                    var5 = Character.toLowerCase(var5);
//                } else if (Character.isTitleCase(var5)) {
//                    var5 = Character.toLowerCase(var5);
//                } else if (Character.isLowerCase(var5)) {
//                    var5 = Character.toUpperCase(var5);
//                }
//
//                var2.append(var5);
//            }
//
//            return var2.toString();
//        } else {
//            return var0;
//        }
//    }
//
//    /** @deprecated */
//    public static String E(String var0) {
//        return WordUtils.capitalize(var0);
//    }
//
//    public static int K(String var0, String var1) {
//        if (!a(var0) && !a(var1)) {
//            int var2 = 0;
//
//            for(int var3 = 0; (var3 = var0.indexOf(var1, var3)) != -1; var3 += var1.length()) {
//                ++var2;
//            }
//
//            return var2;
//        } else {
//            return 0;
//        }
//    }
//
//    public static boolean F(String var0) {
//        if (var0 == null) {
//            return false;
//        } else {
//            int var1 = var0.length();
//
//            for(int var2 = 0; var2 < var1; ++var2) {
//                if (!Character.isLetter(var0.charAt(var2))) {
//                    return false;
//                }
//            }
//
//            return true;
//        }
//    }
//
//    public static boolean G(String var0) {
//        if (var0 == null) {
//            return false;
//        } else {
//            int var1 = var0.length();
//
//            for(int var2 = 0; var2 < var1; ++var2) {
//                if (!Character.isLetter(var0.charAt(var2)) && var0.charAt(var2) != ' ') {
//                    return false;
//                }
//            }
//
//            return true;
//        }
//    }
//
//    public static boolean H(String var0) {
//        if (var0 == null) {
//            return false;
//        } else {
//            int var1 = var0.length();
//
//            for(int var2 = 0; var2 < var1; ++var2) {
//                if (!Character.isLetterOrDigit(var0.charAt(var2))) {
//                    return false;
//                }
//            }
//
//            return true;
//        }
//    }
//
//    public static boolean I(String var0) {
//        if (var0 == null) {
//            return false;
//        } else {
//            int var1 = var0.length();
//
//            for(int var2 = 0; var2 < var1; ++var2) {
//                if (!Character.isLetterOrDigit(var0.charAt(var2)) && var0.charAt(var2) != ' ') {
//                    return false;
//                }
//            }
//
//            return true;
//        }
//    }
//
//    public static boolean J(String var0) {
//        if (var0 == null) {
//            return false;
//        } else {
//            int var1 = var0.length();
//
//            for(int var2 = 0; var2 < var1; ++var2) {
//                if (!CharUtils.isAsciiPrintable(var0.charAt(var2))) {
//                    return false;
//                }
//            }
//
//            return true;
//        }
//    }
//
//    public static boolean K(String var0) {
//        if (var0 == null) {
//            return false;
//        } else {
//            int var1 = var0.length();
//
//            for(int var2 = 0; var2 < var1; ++var2) {
//                if (!Character.isDigit(var0.charAt(var2))) {
//                    return false;
//                }
//            }
//
//            return true;
//        }
//    }
//
//    public static boolean L(String var0) {
//        if (var0 == null) {
//            return false;
//        } else {
//            int var1 = var0.length();
//
//            for(int var2 = 0; var2 < var1; ++var2) {
//                if (!Character.isDigit(var0.charAt(var2)) && var0.charAt(var2) != ' ') {
//                    return false;
//                }
//            }
//
//            return true;
//        }
//    }
//
//    public static boolean M(String var0) {
//        if (var0 == null) {
//            return false;
//        } else {
//            int var1 = var0.length();
//
//            for(int var2 = 0; var2 < var1; ++var2) {
//                if (!Character.isWhitespace(var0.charAt(var2))) {
//                    return false;
//                }
//            }
//
//            return true;
//        }
//    }
//
//    public static boolean N(String var0) {
//        if (var0 != null && !a(var0)) {
//            int var1 = var0.length();
//
//            for(int var2 = 0; var2 < var1; ++var2) {
//                if (!Character.isLowerCase(var0.charAt(var2))) {
//                    return false;
//                }
//            }
//
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    public static boolean O(String var0) {
//        if (var0 != null && !a(var0)) {
//            int var1 = var0.length();
//
//            for(int var2 = 0; var2 < var1; ++var2) {
//                if (!Character.isUpperCase(var0.charAt(var2))) {
//                    return false;
//                }
//            }
//
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    public static String P(String var0) {
//        return var0 == null ? "" : var0;
//    }
//
//    public static ResourceBundle a() {
//        PropertyResourceBundle var0 = null;
//        String var2 = System.getProperty("user.dir") + File.separator + "config" + File.separator + "jeecglic.properties";
//
//        try {
//            BufferedInputStream var1 = new BufferedInputStream(new FileInputStream(var2));
//            var0 = new PropertyResourceBundle(var1);
//            var1.close();
//        } catch (FileNotFoundException var4) {
//        } catch (IOException var5) {
//        }
//
//        return var0;
//    }
//
//    public static String L(String var0, String var1) {
//        return var0 == null ? var1 : var0;
//    }
//
//    public static String M(String var0, String var1) {
//        return StringUtils.isBlank(var0) ? var1 : var0;
//    }
//
//    public static String N(String var0, String var1) {
//        return StringUtils.isEmpty(var0) ? var1 : var0;
//    }
//
//    public static String Q(String var0) {
//        return var0 == null ? null : (new StrBuilder(var0)).reverse().toString();
//    }
//
//    public static String g(String var0, char var1) {
//        if (var0 == null) {
//            return null;
//        } else {
//            String[] var2 = d(var0, var1);
//            ArrayUtils.reverse(var2);
//            return a((Object[])var2, (char)var1);
//        }
//    }
//
//    /** @deprecated */
//    public static String O(String var0, String var1) {
//        if (var0 == null) {
//            return null;
//        } else {
//            String[] var2 = w(var0, var1);
//            ArrayUtils.reverse(var2);
//            return var1 == null ? a((Object[])var2, (char)' ') : a((Object[])var2, (String)var1);
//        }
//    }
//
//    public static String h(String var0, int var1) {
//        return c(var0, 0, (int)var1);
//    }
//
//    public static String c(String var0, int var1, int var2) {
//        if (var0 == null) {
//            return null;
//        } else if (var2 < 4) {
//            throw new IllegalArgumentException("Minimum abbreviation width is 4");
//        } else if (var0.length() <= var2) {
//            return var0;
//        } else {
//            if (var1 > var0.length()) {
//                var1 = var0.length();
//            }
//
//            if (var0.length() - var1 < var2 - 3) {
//                var1 = var0.length() - (var2 - 3);
//            }
//
//            if (var1 <= 4) {
//                return var0.substring(0, var2 - 3) + "...";
//            } else if (var2 < 7) {
//                throw new IllegalArgumentException("Minimum abbreviation width with offset is 7");
//            } else {
//                return var1 + (var2 - 3) < var0.length() ? "..." + h(var0.substring(var1), var2 - 3) : "..." + var0.substring(var0.length() - (var2 - 3));
//            }
//        }
//    }
//
//    public static String l(String var0, String var1, int var2) {
//        if (!a(var0) && !a(var1)) {
//            if (var2 < var0.length() && var2 >= var1.length() + 2) {
//                int var3 = var2 - var1.length();
//                int var4 = var3 / 2 + var3 % 2;
//                int var5 = var0.length() - var3 / 2;
//                StrBuilder var6 = new StrBuilder(var2);
//                var6.append(var0.substring(0, var4));
//                var6.append(var1);
//                var6.append(var0.substring(var5));
//                return var6.toString();
//            } else {
//                return var0;
//            }
//        } else {
//            return var0;
//        }
//    }
//
//    public static String P(String var0, String var1) {
//        if (var0 == null) {
//            return var1;
//        } else if (var1 == null) {
//            return var0;
//        } else {
//            int var2 = Q(var0, var1);
//            return var2 == -1 ? "" : var1.substring(var2);
//        }
//    }
//
//    public static int Q(String var0, String var1) {
//        if (var0 == var1) {
//            return -1;
//        } else if (var0 != null && var1 != null) {
//            int var2;
//            for(var2 = 0; var2 < var0.length() && var2 < var1.length() && var0.charAt(var2) == var1.charAt(var2); ++var2) {
//            }
//
//            return var2 >= var1.length() && var2 >= var0.length() ? -1 : var2;
//        } else {
//            return 0;
//        }
//    }
//
//    public static int b(String[] var0) {
//        if (var0 != null && var0.length > 1) {
//            boolean var1 = false;
//            boolean var2 = true;
//            int var3 = var0.length;
//            int var4 = 2147483647;
//            int var5 = 0;
//
//            int var6;
//            for(var6 = 0; var6 < var3; ++var6) {
//                if (var0[var6] == null) {
//                    var1 = true;
//                    var4 = 0;
//                } else {
//                    var2 = false;
//                    var4 = Math.min(var0[var6].length(), var4);
//                    var5 = Math.max(var0[var6].length(), var5);
//                }
//            }
//
//            if (var2 || var5 == 0 && !var1) {
//                return -1;
//            } else if (var4 == 0) {
//                return 0;
//            } else {
//                var6 = -1;
//
//                for(int var7 = 0; var7 < var4; ++var7) {
//                    char var8 = var0[0].charAt(var7);
//
//                    for(int var9 = 1; var9 < var3; ++var9) {
//                        if (var0[var9].charAt(var7) != var8) {
//                            var6 = var7;
//                            break;
//                        }
//                    }
//
//                    if (var6 != -1) {
//                        break;
//                    }
//                }
//
//                return var6 == -1 && var4 != var5 ? var4 : var6;
//            }
//        } else {
//            return -1;
//        }
//    }
//
//    public static boolean b(String[] var0, String var1) {
//        List var2 = Arrays.asList(var0);
//        return var2.contains(var1);
//    }
//
//    public static String c(String[] var0) {
//        if (var0 != null && var0.length != 0) {
//            int var1 = b(var0);
//            if (var1 == -1) {
//                return var0[0] == null ? "" : var0[0];
//            } else {
//                return var1 == 0 ? "" : var0[0].substring(0, var1);
//            }
//        } else {
//            return "";
//        }
//    }
//
//    public static int R(String var0, String var1) {
//        if (var0 != null && var1 != null) {
//            int var2 = var0.length();
//            int var3 = var1.length();
//            if (var2 == 0) {
//                return var3;
//            } else if (var3 == 0) {
//                return var2;
//            } else {
//                if (var2 > var3) {
//                    String var4 = var0;
//                    var0 = var1;
//                    var1 = var4;
//                    var2 = var3;
//                    var3 = var4.length();
//                }
//
//                int[] var11 = new int[var2 + 1];
//                int[] var5 = new int[var2 + 1];
//
//                int var7;
//                for(var7 = 0; var7 <= var2; var11[var7] = var7++) {
//                }
//
//                for(int var8 = 1; var8 <= var3; ++var8) {
//                    char var9 = var1.charAt(var8 - 1);
//                    var5[0] = var8;
//
//                    for(var7 = 1; var7 <= var2; ++var7) {
//                        int var10 = var0.charAt(var7 - 1) == var9 ? 0 : 1;
//                        var5[var7] = Math.min(Math.min(var5[var7 - 1] + 1, var11[var7] + 1), var11[var7 - 1] + var10);
//                    }
//
//                    int[] var6 = var11;
//                    var11 = var5;
//                    var5 = var6;
//                }
//
//                return var11[var2];
//            }
//        } else {
//            throw new IllegalArgumentException("Strings must not be null");
//        }
//    }
//
//    public static boolean S(String var0, String var1) {
//        return a(var0, var1, false);
//    }
//
//    public static boolean T(String var0, String var1) {
//        return a(var0, var1, true);
//    }
//
//    private static boolean a(String var0, String var1, boolean var2) {
//        if (var0 != null && var1 != null) {
//            return var1.length() > var0.length() ? false : var0.regionMatches(var2, 0, var1, 0, var1.length());
//        } else {
//            return var0 == null && var1 == null;
//        }
//    }
//
//    public static boolean U(String var0, String var1) {
//        return b(var0, var1, false);
//    }
//
//    public static boolean V(String var0, String var1) {
//        return b(var0, var1, true);
//    }
//
//    private static boolean b(String var0, String var1, boolean var2) {
//        if (var0 != null && var1 != null) {
//            if (var1.length() > var0.length()) {
//                return false;
//            } else {
//                int var3 = var0.length() - var1.length();
//                return var0.regionMatches(var2, var3, var1, 0, var1.length());
//            }
//        } else {
//            return var0 == null && var1 == null;
//        }
//    }
//
//    public static String R(String var0) {
//        var0 = i(var0);
//        if (var0 != null && var0.length() > 2) {
//            StrBuilder var1 = new StrBuilder(var0.length());
//
//            for(int var2 = 0; var2 < var0.length(); ++var2) {
//                char var3 = var0.charAt(var2);
//                if (Character.isWhitespace(var3)) {
//                    if (var2 > 0 && !Character.isWhitespace(var0.charAt(var2 - 1))) {
//                        var1.append(' ');
//                    }
//                } else {
//                    var1.append(var3);
//                }
//            }
//
//            return var1.toString();
//        } else {
//            return var0;
//        }
//    }
//
//    static {
//        c = Boolean.FALSE;
//        d = Boolean.TRUE;
//        c();
//        b();
//        f = null;
//    }
//}
