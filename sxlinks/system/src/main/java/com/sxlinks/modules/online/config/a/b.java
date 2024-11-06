//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sxlinks.modules.online.config.a;

import com.sxlinks.common.util.oConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component("dataBaseConfig")
@ConfigurationProperties(
        prefix = "spring.datasource.dynamic.datasource.master"
)
public class b {
  @Autowired
  private c dmDataBaseConfig;
  private String a;
  private String b;
  private String c;
  private String d;
  private d e;

  public b() {
  }

  public d getDruid() {
    return this.e == null ? this.dmDataBaseConfig.getDruid() : this.e;
  }

  public void setDruid(d druid) {
    this.e = druid;
  }

  public String getUrl() {
    return oConvertUtils.getString(this.a, this.dmDataBaseConfig.getUrl());
  }

  public void setUrl(String url) {
    this.a = url;
  }

  public String getUsername() {
    return oConvertUtils.getString(this.b, this.dmDataBaseConfig.getUsername());
  }

  public void setUsername(String username) {
    this.b = username;
  }

  public String getPassword() {
    return oConvertUtils.getString(this.c, this.dmDataBaseConfig.getPassword());
  }

  public void setPassword(String password) {
    this.c = password;
  }

  public String getDriverClassName() {
    return oConvertUtils.getString(this.d, this.dmDataBaseConfig.getDriverClassName());
  }

  public void setDriverClassName(String driverClassName) {
    this.d = driverClassName;
  }
}
