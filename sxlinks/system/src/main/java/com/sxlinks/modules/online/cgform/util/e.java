//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sxlinks.modules.online.cgform.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

class e {
    e() {
    }

    private static String b() {
        String var0 = "";
        Object var2 = null;

        byte[] var8;
        try {
            InetAddress var1 = InetAddress.getLocalHost();
            var8 = NetworkInterface.getByInetAddress(var1).getHardwareAddress();
        } catch (Exception var7) {
            return null;
        }

        StringBuffer var3 = new StringBuffer("");
        if (var8 == null) {
            return null;
        } else {
            for(int var4 = 0; var4 < var8.length; ++var4) {
                if (var4 != 0) {
                    var3.append("-");
                }

                int var5 = var8[var4] & 255;
                String var6 = Integer.toHexString(var5);
                if (var6.length() == 1) {
                    var3.append("0" + var6);
                } else {
                    var3.append(var6);
                }
            }

            var0 = var3.toString().toUpperCase();
            if ("".equals(var0)) {
                return null;
            } else {
                return var0;
            }
        }
    }

    private static String c() {
        String var0 = "";

        try {
            Enumeration var1 = NetworkInterface.getNetworkInterfaces();

            while(true) {
                byte[] var2;
                do {
                    if (!var1.hasMoreElements()) {
                        return var0;
                    }

                    var2 = ((NetworkInterface)var1.nextElement()).getHardwareAddress();
                } while(var2 == null);

                StringBuilder var3 = new StringBuilder();
                byte[] var4 = var2;
                int var5 = var2.length;

                for(int var6 = 0; var6 < var5; ++var6) {
                    byte var7 = var4[var6];
                    var3.append(a(var7));
                    var3.append("-");
                }

                var3.deleteCharAt(var3.length() - 1);
                var0 = var3.toString().toUpperCase();
            }
        } catch (Exception var8) {
            var8.printStackTrace();
            return var0;
        }
    }

    private static String a(byte var0) {
        String var1 = "000000" + Integer.toHexString(var0);
        return var1.substring(var1.length() - 2);
    }

    private static String d() {
        return System.getProperty("os.name").toLowerCase();
    }

    public static String a() {
        String var0 = d();
        String var1 = null;
        if (var0.startsWith("windows")) {
            var1 = b();
        } else {
            var1 = c();
        }

        return var1;
    }
}
