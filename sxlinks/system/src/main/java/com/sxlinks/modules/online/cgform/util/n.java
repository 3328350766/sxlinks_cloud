//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sxlinks.modules.online.cgform.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class n {
    private static final String a = "FECO#$N*CX";
    private static final Charset b = Charset.forName("UTF-8");
    private static byte[] c;

    n() {
    }

    public static String a(String var0) {
        byte[] var1 = var0.getBytes(b);
        int var2 = 0;

        for(int var3 = var1.length; var2 < var3; ++var2) {
            byte[] var4 = c;
            int var5 = var4.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                byte var7 = var4[var6];
                var1[var2] ^= var7;
            }
        }

        return new String(var1);
    }

    public static String b(String var0) {
        byte[] var1 = var0.getBytes(b);
        byte[] var2 = var1;
        int var3 = 0;

        for(int var4 = var1.length; var3 < var4; ++var3) {
            byte[] var5 = c;
            int var6 = var5.length;

            for(int var7 = 0; var7 < var6; ++var7) {
                byte var8 = var5[var7];
                var1[var3] = (byte)(var2[var3] ^ var8);
            }
        }

        return new String(var1);
    }

    public static String c(String var0) {
        MessageDigest var1 = null;

        try {
            var1 = MessageDigest.getInstance("MD5");
            var1.reset();
            var1.update(var0.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException var5) {
        } catch (UnsupportedEncodingException var6) {
        }

        byte[] var2 = var1.digest();
        StringBuffer var3 = new StringBuffer();

        for(int var4 = 0; var4 < var2.length; ++var4) {
            if (Integer.toHexString(255 & var2[var4]).length() == 1) {
                var3.append("0").append(Integer.toHexString(255 & var2[var4]));
            } else {
                var3.append(Integer.toHexString(255 & var2[var4]));
            }
        }

        return var3.toString();
    }

    static {
        c = "FECO#$N*CX".getBytes(b);
    }
}
