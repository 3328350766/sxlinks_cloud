//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sxlinks.modules.online.config.a;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component("dmDataBaseConfig")
@ConfigurationProperties(
        prefix = "spring.datasource.druid"
)
public class c {
  private String a;
  private String b;
  private String c;
  private String d;
  private d e;

  public c() {
  }

  public d getDruid() {
    return this.e;
  }

  public void setDruid(d druid) {
    this.e = druid;
  }

  public String getUrl() {
    return this.a;
  }

  public void setUrl(String url) {
    this.a = url;
  }

  public String getUsername() {
    return this.b;
  }

  public void setUsername(String username) {
    this.b = username;
  }

  public String getPassword() {
    return this.c;
  }

  public void setPassword(String password) {
    this.c = password;
  }

  public String getDriverClassName() {
    return this.d;
  }

  public void setDriverClassName(String driverClassName) {
    this.d = driverClassName;
  }
}
