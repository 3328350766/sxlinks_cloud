////
//// Source code recreated from a .class file by IntelliJ IDEA
//// (powered by Fernflower decompiler)
////
//
//package com.sxlinks.modules.online.cgform.util;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.net.ConnectException;
//import java.net.HttpURLConnection;
//import java.net.InetAddress;
//import java.net.NetworkInterface;
//import java.net.SocketException;
//import java.net.URL;
//import java.net.UnknownHostException;
//import java.security.SecureRandom;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Locale;
//import java.util.ResourceBundle;
//import java.util.Scanner;
//import javax.net.ssl.KeyManager;
//import javax.net.ssl.SSLContext;
//import javax.net.ssl.SSLSocketFactory;
//import javax.net.ssl.TrustManager;
//
//public class l {
//  private static String c;
//  private static String d;
//  private static String e;
//  public static String a;
//  public static boolean b;
//
//  public l() {
//  }
//
//  public static String getssss() {
//    if ("".equals(d) || d == null) {
//      String var0 = "";
//      var0 = getMa() + getProcessorId() + getMotherboardSN();
//      a = n.c(var0);
//      d = n.c(a.toUpperCase() + getLicTime() + getNetLic() + "P5P5");
//    }
//
//    return d.toUpperCase();
//  }
//
//  public static String getmachine_code() {
//    if ("".equals(d) || d == null) {
//      String var0 = "";
//      var0 = getMa() + getProcessorId() + getMotherboardSN();
//      a = n.c(var0);
//    }
//
//    return a.toUpperCase();
//  }
//
//  public static String getssssserver() {
//    if ("".equals(e) || e == null) {
//      String var0 = "";
//      var0 = getMa() + getProcessorId() + getMotherboardSN();
//      a = n.c(var0);
//      e = n.c(a.toUpperCase() + getNetLic() + "9999");
//    }
//
//    return e.toUpperCase();
//  }
//
//  private static String getProcessorId() {
//    String var0 = "CPUID000";
//
//    try {
//      Process var1 = Runtime.getRuntime().exec(new String[]{"wmic", "cpu", "get", "ProcessorId"});
//      var1.getOutputStream().close();
//      Scanner var2 = new Scanner(var1.getInputStream());
//      String var3 = var2.next();
//      var0 = var2.next();
//    } catch (Exception var4) {
//    }
//
//    return var0;
//  }
//
//  private static String a(String var0) {
//    String var1 = "";
//
//    try {
//      File var2 = File.createTempFile("damn", ".vbs");
//      var2.deleteOnExit();
//      FileWriter var3 = new FileWriter(var2);
//      String var4 = "Set objFSO = CreateObject(\"Scripting.FileSystemObject\")\nSet colDrives = objFSO.Drives\nSet objDrive = colDrives.item(\"" + var0 + "\")\nWscript.Echo objDrive.SerialNumber";
//      var3.write(var4);
//      var3.close();
//      Process var5 = Runtime.getRuntime().exec("cscript //NoLogo " + var2.getPath());
//
//      BufferedReader var6;
//      String var7;
//      for(var6 = new BufferedReader(new InputStreamReader(var5.getInputStream())); (var7 = var6.readLine()) != null; var1 = var1 + var7) {
//      }
//
//      var6.close();
//    } catch (Exception var8) {
//      var8.printStackTrace();
//    }
//
//    return var1.trim();
//  }
//
//  private static String getMotherboardSN() {
//    String var0 = "ZBXLH000";
//
//    try {
//      File var1 = File.createTempFile("realhowto", ".vbs");
//      var1.deleteOnExit();
//      FileWriter var2 = new FileWriter(var1);
//      String var3 = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\nSet colItems = objWMIService.ExecQuery _ \n   (\"Select * from Win32_BaseBoard\") \nFor Each objItem in colItems \n    Wscript.Echo objItem.SerialNumber \n    exit for  ' do the first cpu only! \nNext \n";
//      var2.write(var3);
//      var2.close();
//      Process var4 = Runtime.getRuntime().exec("cscript //NoLogo " + var1.getPath());
//
//      BufferedReader var5;
//      String var6;
//      for(var5 = new BufferedReader(new InputStreamReader(var4.getInputStream())); (var6 = var5.readLine()) != null; var0 = var0 + var6) {
//      }
//
//      var5.close();
//    } catch (Exception var7) {
//    }
//
//    return var0.trim();
//  }
//
//  private static String getLicTime() {
//    SimpleDateFormat var0 = new SimpleDateFormat("yyyy");
//    String var1 = var0.format(new Date());
//    return var1;
//  }
//
//  public static boolean a() {
//    Date var0 = new Date();
//    String var1 = "2015-04-18";
//    SimpleDateFormat var2 = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
//    Date var3 = null;
//
//    try {
//      var3 = var2.parse(var1);
//    } catch (Exception var5) {
//      var5.printStackTrace();
//    }
//
//    boolean var4 = var3.before(var0);
//    return var4;
//  }
//
//  private static String getNetLic() {
//    String var0 = "A0001";
//
//    try {
//      ResourceBundle var1 = com.sxlinks.modules.online.cgform.util.c.a();
//      if (var1 == null) {
//        var1 = ResourceBundle.getBundle("jeecglic");
//      }
//
//      var0 = var1.getString("licence.key");
//    } catch (Exception var2) {
//    }
//
//    return var0;
//  }
//
//  private static String getMa() {
//    return com.sxlinks.modules.online.cgform.util.e.a();
//  }
//
//  private static String getMaOld() {
//    if (m.c(c)) {
//      return c;
//    } else {
//      String var0 = getOSName();
//      String var1 = getSystemRoot() + "/system32/ipconfig /all";
//      String var2 = null;
//      if (var0.startsWith("windows")) {
//        if (var0.equals("windows xp")) {
//          var2 = b(var1);
//        } else if (var0.equals("windows 2003")) {
//          var2 = b(var1);
//        } else {
//          var2 = getWindow7Ma();
//        }
//      } else if (var0.startsWith("linux")) {
//        var2 = getLinuxMa();
//      } else {
//        var2 = getUnixMa();
//      }
//
//      c = var2;
//      return var2;
//    }
//  }
//
//  private static String getOSName() {
//    return System.getProperty("os.name").toLowerCase();
//  }
//
//  private static String b(String var0) {
//    String var1 = null;
//    BufferedReader var2 = null;
//    Process var3 = null;
//
//    try {
//      var3 = Runtime.getRuntime().exec(var0);
//      var2 = new BufferedReader(new InputStreamReader(var3.getInputStream()));
//      String var4 = null;
//      boolean var5 = true;
//
//      while((var4 = var2.readLine()) != null) {
//        if (var4.indexOf("本地连接") == -1) {
//          int var16 = var4.toLowerCase().indexOf("physical address");
//          if (var16 != -1) {
//            var16 = var4.indexOf(":");
//            if (var16 != -1) {
//              var1 = var4.substring(var16 + 1).trim();
//            }
//            break;
//          }
//        }
//      }
//    } catch (IOException var14) {
//      var14.printStackTrace();
//    } finally {
//      try {
//        if (var2 != null) {
//          var2.close();
//        }
//      } catch (IOException var13) {
//        var13.printStackTrace();
//      }
//
//      var2 = null;
//      var3 = null;
//    }
//
//    return var1;
//  }
//
//  private static String getWindow7Ma() {
//    InetAddress var0 = null;
//
//    try {
//      var0 = InetAddress.getLocalHost();
//    } catch (UnknownHostException var6) {
//      var6.printStackTrace();
//    }
//
//    byte[] var1 = null;
//
//    try {
//      var1 = NetworkInterface.getByInetAddress(var0).getHardwareAddress();
//    } catch (SocketException var5) {
//    }
//
//    StringBuffer var2 = new StringBuffer();
//
//    for(int var3 = 0; var3 < var1.length; ++var3) {
//      if (var3 != 0) {
//        var2.append("-");
//      }
//
//      String var4 = Integer.toHexString(var1[var3] & 255);
//      var2.append(var4.length() == 1 ? 0 + var4 : var4);
//    }
//
//    return var2.toString().toUpperCase();
//  }
//
//  private static String getLinuxMa() {
//    String var0 = null;
//    BufferedReader var1 = null;
//    Process var2 = null;
//
//    try {
//      var2 = Runtime.getRuntime().exec("ifconfig eth0");
//      var1 = new BufferedReader(new InputStreamReader(var2.getInputStream()));
//      String var3 = null;
//      boolean var4 = true;
//
//      while((var3 = var1.readLine()) != null) {
//        int var15 = var3.toLowerCase().indexOf("硬件地址");
//        if (var15 != -1) {
//          var0 = var3.substring(var15 + 4).trim();
//          break;
//        }
//      }
//    } catch (IOException var13) {
//      var13.printStackTrace();
//    } finally {
//      try {
//        if (var1 != null) {
//          var1.close();
//        }
//      } catch (IOException var12) {
//        var12.printStackTrace();
//      }
//
//      var1 = null;
//      var2 = null;
//    }
//
//    return var0;
//  }
//
//  private static String getUnixMa() {
//    String var0 = null;
//    BufferedReader var1 = null;
//    Process var2 = null;
//
//    try {
//      var2 = Runtime.getRuntime().exec("ifconfig eth0");
//      var1 = new BufferedReader(new InputStreamReader(var2.getInputStream()));
//      String var3 = null;
//      boolean var4 = true;
//
//      while((var3 = var1.readLine()) != null) {
//        int var15 = var3.toLowerCase().indexOf("hwaddr");
//        if (var15 != -1) {
//          var0 = var3.substring(var15 + "hwaddr".length() + 1).trim();
//          break;
//        }
//      }
//    } catch (IOException var13) {
//      var13.printStackTrace();
//    } finally {
//      try {
//        if (var1 != null) {
//          var1.close();
//        }
//      } catch (IOException var12) {
//        var12.printStackTrace();
//      }
//
//      var1 = null;
//      var2 = null;
//    }
//
//    return var0;
//  }
//
//  private static String getSystemRoot() {
//    String var0 = null;
//    String var1 = null;
//    String var2 = null;
//    String var3 = "windir";
//    var1 = System.getProperty("os.name").toLowerCase();
//    if (var1.startsWith("windows")) {
//      var0 = "cmd /c SET";
//    } else {
//      var0 = "env";
//    }
//
//    try {
//      Process var4 = Runtime.getRuntime().exec(var0);
//      InputStreamReader var5 = new InputStreamReader(var4.getInputStream());
//      BufferedReader var6 = new BufferedReader(var5);
//      String var7 = null;
//
//      while((var7 = var6.readLine()) != null) {
//        var7 = var7.toLowerCase();
//        if (var7.indexOf(var3) > -1) {
//          var2 = var7.substring(var7.indexOf(var3) + var3.length() + 1);
//          return var2;
//        }
//      }
//    } catch (Exception var8) {
//    }
//
//    return null;
//  }
//
//  public static String a(String var0, String var1, String var2) {
//    Object var3 = null;
//    StringBuffer var4 = new StringBuffer();
//
//    try {
//      TrustManager[] var5 = new TrustManager[]{new com.sxlinks.modules.online.cgform.util.f()};
//      SSLContext var6 = SSLContext.getInstance("SSL", "SunJSSE");
//      var6.init((KeyManager[])null, var5, new SecureRandom());
//      SSLSocketFactory var7 = var6.getSocketFactory();
//      URL var8 = new URL(var0);
//      HttpURLConnection var9 = (HttpURLConnection)var8.openConnection();
//      var9.setConnectTimeout(4500);
//      var9.setReadTimeout(4500);
//      var9.setDoOutput(true);
//      var9.setDoInput(true);
//      var9.setUseCaches(false);
//      var9.setRequestMethod(var1);
//      if ("GET".equalsIgnoreCase(var1)) {
//        var9.connect();
//      }
//
//      OutputStream var10;
//      if (null != var2) {
//        var10 = var9.getOutputStream();
//        var10.write(var2.getBytes("UTF-8"));
//        var10.close();
//      }
//
//      InputStream var16 = var9.getInputStream();
//      InputStreamReader var11 = new InputStreamReader(var16, "utf-8");
//      BufferedReader var12 = new BufferedReader(var11);
//      String var13 = null;
//
//      while((var13 = var12.readLine()) != null) {
//        var4.append(var13);
//      }
//
//      var12.close();
//      var11.close();
//      var16.close();
//      var10 = null;
//      var9.disconnect();
//    } catch (ConnectException var14) {
//    } catch (Exception var15) {
//    }
//
//    return var4.toString();
//  }
//
//  static {
//    b = Boolean.TRUE;
//  }
//}
