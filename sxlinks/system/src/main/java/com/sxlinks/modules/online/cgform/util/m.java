//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sxlinks.modules.online.cgform.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class m {
    static Map<String, Object> a = new HashMap();

    m() {
    }

    public static Map<String, Object> a() {
        return a;
    }

    public static boolean a(Object var0) {
        if (var0 == null) {
            return true;
        } else if (var0.equals("")) {
            return true;
        } else {
            return var0.equals("null");
        }
    }

    public static boolean b(Object var0) {
        if (var0 == null) {
            return true;
        } else if (var0 instanceof CharSequence) {
            return ((CharSequence)var0).length() == 0;
        } else if (var0 instanceof Collection) {
            return ((Collection)var0).isEmpty();
        } else if (var0 instanceof Map) {
            return ((Map)var0).isEmpty();
        } else if (!(var0 instanceof Object[])) {
            return false;
        } else {
            Object[] var1 = (Object[])((Object[])var0);
            if (var1.length == 0) {
                return true;
            } else {
                boolean var2 = true;

                for(int var3 = 0; var3 < var1.length; ++var3) {
                    if (!b(var1[var3])) {
                        var2 = false;
                        break;
                    }
                }

                return var2;
            }
        }
    }

    public static boolean c(Object var0) {
        return var0 != null && !var0.equals("") && !var0.equals("null");
    }

    public static String a(String var0, String var1, String var2) {
        String var3 = c(var0, var1, var2);
        return var3;
    }

    public static String b(String var0, String var1, String var2) {
        var0 = "";

        try {
            var0 = new String(var0.getBytes("ISO-8859-1"), "GBK");
        } catch (UnsupportedEncodingException var4) {
            var4.printStackTrace();
        }

        return var0;
    }

    private static String c(String var0, String var1, String var2) {
        String var3 = null;
        if (var0 != null && !var0.trim().equals("")) {
            try {
                byte[] var4 = var0.getBytes(var1);

                for(int var5 = 0; var5 < var4.length; ++var5) {
                    System.out.print(var4[var5] + "  ");
                }

                var3 = new String(var4, var2);
                return var3;
            } catch (Exception var6) {
                var6.printStackTrace();
                return null;
            }
        } else {
            return var0;
        }
    }

    public static int a(String var0, int var1) {
        if (var0 != null && var0 != "") {
            try {
                return Integer.parseInt(var0);
            } catch (NumberFormatException var3) {
                return var1;
            }
        } else {
            return var1;
        }
    }

    public static int a(String var0) {
        if (var0 != null && var0 != "") {
            try {
                return Integer.parseInt(var0);
            } catch (NumberFormatException var2) {
                return 0;
            }
        } else {
            return 0;
        }
    }

    public static int d(Object var0) {
        if (var0 != null && var0 != "") {
            try {
                return Integer.parseInt(var0.toString());
            } catch (NumberFormatException var2) {
                return 0;
            }
        } else {
            return 0;
        }
    }

    public static int a(String var0, Integer var1) {
        if (var0 != null && var0 != "") {
            try {
                return Integer.parseInt(var0);
            } catch (NumberFormatException var3) {
                return 0;
            }
        } else {
            return var1;
        }
    }

    public static Integer[] a(String[] var0) {
        Integer[] var1 = new Integer[var0.length];
        if (var0 == null) {
            return null;
        } else {
            for(int var2 = 0; var2 < var0.length; ++var2) {
                var1[var2] = Integer.parseInt(var0[var2]);
            }

            return var1;
        }
    }

    public static double a(String var0, double var1) {
        if (var0 != null && var0 != "") {
            try {
                return Double.parseDouble(var0);
            } catch (NumberFormatException var4) {
                return var1;
            }
        } else {
            return var1;
        }
    }

    public static double a(Double var0, double var1) {
        return var0 == null ? var1 : var0;
    }

    public static int a(Object var0, int var1) {
        if (a(var0)) {
            return var1;
        } else {
            try {
                return Integer.parseInt(var0.toString());
            } catch (NumberFormatException var3) {
                return var1;
            }
        }
    }

    public static int a(BigDecimal var0, int var1) {
        return var0 == null ? var1 : var0.intValue();
    }

    public static Integer[] b(String[] var0) {
        int var1 = var0.length;
        Integer[] var2 = new Integer[var1];

        try {
            for(int var3 = 0; var3 < var1; ++var3) {
                var2[var3] = new Integer(var0[var3].trim());
            }

            return var2;
        } catch (NumberFormatException var4) {
            return null;
        }
    }

    public static String b(String var0) {
        return a(var0, "");
    }

    public static String e(Object var0) {
        return a(var0) ? "" : var0.toString().trim();
    }

    public static String a(int var0) {
        return String.valueOf(var0);
    }

    public static String c(String[] var0) {
        if (var0.length == 0) {
            return "";
        } else {
            String var1 = "";
            String[] var2 = var0;
            int var3 = var0.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                String var5 = var2[var4];
                var1 = var1 + ",'" + var5 + "'";
            }

            return var1.substring(1);
        }
    }

    public static String a(float var0) {
        return String.valueOf(var0);
    }

    public static String a(String var0, String var1) {
        return a((Object)var0) ? var1 : var0.trim();
    }

    public static String a(Object var0, String var1) {
        return a(var0) ? var1 : var0.toString().trim();
    }

    public static long c(String var0) {
        Long var1 = new Long(0L);

        try {
            var1 = Long.valueOf(var0);
        } catch (Exception var3) {
        }

        return var1;
    }

    public static String b() {
        String var0 = null;

        try {
            InetAddress var1 = InetAddress.getLocalHost();
            var0 = var1.getHostAddress();
        } catch (UnknownHostException var2) {
            var2.printStackTrace();
        }

        return var0;
    }

    private static boolean a(Class var0) throws Exception {
        return var0.equals(String.class) || var0.equals(Integer.class) || var0.equals(Byte.class) || var0.equals(Long.class) || var0.equals(Double.class) || var0.equals(Float.class) || var0.equals(Character.class) || var0.equals(Short.class) || var0.equals(BigDecimal.class) || var0.equals(BigInteger.class) || var0.equals(Boolean.class) || var0.equals(Date.class) || var0.isPrimitive();
    }

    public static String c() throws SocketException {
        String var0 = null;
        String var1 = null;
        Enumeration var2 = NetworkInterface.getNetworkInterfaces();
        InetAddress var3 = null;
        boolean var4 = false;

        while(var2.hasMoreElements() && !var4) {
            NetworkInterface var5 = (NetworkInterface)var2.nextElement();
            Enumeration var6 = var5.getInetAddresses();

            while(var6.hasMoreElements()) {
                var3 = (InetAddress)var6.nextElement();
                if (!var3.isSiteLocalAddress() && !var3.isLoopbackAddress() && var3.getHostAddress().indexOf(":") == -1) {
                    var1 = var3.getHostAddress();
                    var4 = true;
                    break;
                }

                if (var3.isSiteLocalAddress() && !var3.isLoopbackAddress() && var3.getHostAddress().indexOf(":") == -1) {
                    var0 = var3.getHostAddress();
                }
            }
        }

        return var1 != null && !"".equals(var1) ? var1 : var0;
    }

    public static String d(String var0) {
        String var1 = "";
        if (var0 != null) {
            Pattern var2 = Pattern.compile("\\s*|\t|\r|\n");
            Matcher var3 = var2.matcher(var0);
            var1 = var3.replaceAll("");
        }

        return var1;
    }

    public static boolean a(String var0, String[] var1) {
        if (var1 != null && var1.length != 0) {
            for(int var2 = 0; var2 < var1.length; ++var2) {
                String var3 = var1[var2];
                if (var3.equals(var0)) {
                    return true;
                }
            }

            return false;
        } else {
            return false;
        }
    }

    public static boolean b(String var0, String var1) {
        String[] var2 = null;
        if (var1 != null) {
            var2 = var1.split(",");
        }

        return a(var0, var2);
    }

    public static Map<Object, Object> d() {
        return new HashMap();
    }

    public static Map<Object, Object> a(Set<Object> var0) {
        Map var1 = d();
        Iterator var2 = var0.iterator();

        while(var2.hasNext()) {
            Entry var3 = (Entry)var2.next();
            var1.put(var3.getKey().toString(), var3.getValue() == null ? "" : var3.getValue().toString().trim());
        }

        return var1;
    }

    public static boolean e(String var0) {
        boolean var1 = false;
        long var2 = f(var0);
        long var4 = f("10.0.0.0");
        long var6 = f("10.255.255.255");
        long var8 = f("172.16.0.0");
        long var10 = f("172.31.255.255");
        long var12 = f("192.168.0.0");
        long var14 = f("192.168.255.255");
        var1 = a(var2, var4, var6) || a(var2, var8, var10) || a(var2, var12, var14) || var0.equals("127.0.0.1");
        return var1;
    }

    private static long f(String var0) {
        String[] var1 = var0.split("\\.");
        long var2 = (long)Integer.parseInt(var1[0]);
        long var4 = (long)Integer.parseInt(var1[1]);
        long var6 = (long)Integer.parseInt(var1[2]);
        long var8 = (long)Integer.parseInt(var1[3]);
        long var10 = var2 * 256L * 256L * 256L + var4 * 256L * 256L + var6 * 256L + var8;
        return var10;
    }

    private static boolean a(long var0, long var2, long var4) {
        return var0 >= var2 && var0 <= var4;
    }
}
