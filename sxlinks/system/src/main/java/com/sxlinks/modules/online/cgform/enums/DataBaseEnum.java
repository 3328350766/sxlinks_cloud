//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sxlinks.modules.online.cgform.enums;

public enum DataBaseEnum {
  MYSQL("MYSQL", "1"),
  ORACLE("ORACLE", "2"),
  SQLSERVER("SQLSERVER", "3"),
  POSTGRESQL("POSTGRESQL", "4");

  private String name;
  private String value;

  private DataBaseEnum(String name, String value) {
    this.name = name;
    this.value = value;
  }

  public static String getDataBaseNameByValue(String value) {
    DataBaseEnum[] var1 = values();
    int var2 = var1.length;

    for(int var3 = 0; var3 < var2; ++var3) {
      DataBaseEnum var4 = var1[var3];
      if (var4.value.equals(value)) {
        return var4.name;
      }
    }

    return MYSQL.name;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getValue() {
    return this.value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
