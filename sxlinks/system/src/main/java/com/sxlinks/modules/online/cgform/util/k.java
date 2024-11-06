//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sxlinks.modules.online.cgform.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import com.sxlinks.common.util.oConvertUtils;

public class k {
  public k() {
  }

  public static String a(byte[] var0, int var1) {
    return (new BigInteger(1, var0)).toString(var1);
  }

  public static String a(byte[] var0) {
    return Base64.getEncoder().encodeToString(var0);
  }

  public static byte[] a(String var0) throws Exception {
    return oConvertUtils.isEmpty(var0) ? null : Base64.getDecoder().decode(var0);
  }

  public static byte[] b(byte[] var0) throws Exception {
    MessageDigest var1 = MessageDigest.getInstance("MD5");
    var1.update(var0);
    return var1.digest();
  }

  public static byte[] b(String var0) throws Exception {
    return oConvertUtils.isEmpty(var0) ? null : b(var0.getBytes());
  }

  public static String c(String var0) throws Exception {
    return oConvertUtils.isEmpty(var0) ? null : a(b(var0));
  }

  public static byte[] a(String var0, String var1) throws Exception {
    KeyGenerator var2 = KeyGenerator.getInstance("AES");
    var2.init(128, new SecureRandom(var1.getBytes()));
    Cipher var3 = Cipher.getInstance("AES");
    var3.init(1, new SecretKeySpec(var2.generateKey().getEncoded(), "AES"));
    return var3.doFinal(var0.getBytes("utf-8"));
  }

  public static String b(String var0, String var1) throws Exception {
    return a(a(var0, var1));
  }

  public static String a(byte[] var0, String var1) throws Exception {
    KeyGenerator var2 = KeyGenerator.getInstance("AES");
    var2.init(128, new SecureRandom(var1.getBytes()));
    Cipher var3 = Cipher.getInstance("AES");
    var3.init(2, new SecretKeySpec(var2.generateKey().getEncoded(), "AES"));
    byte[] var4 = var3.doFinal(var0);
    return new String(var4);
  }

  public static String c(String var0, String var1) throws Exception {
    return oConvertUtils.isEmpty(var0) ? null : a(a(var0), var1);
  }
}
