//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sxlinks.common.util.jsonschema;

import java.io.Serializable;
import java.util.List;

public class JsonSchemaDescrip implements Serializable {
  private static final long a = 7682073117441544718L;
  private String b = "http://json-schema.org/draft-07/schema#";
  private String c;
  private String d;
  private String e;
  private List<String> f;

  public List<String> getRequired() {
    return this.f;
  }

  public void setRequired(List<String> required) {
    this.f = required;
  }

  public String get$schema() {
    return this.b;
  }

  public void set$schema(String $schema) {
    this.b = $schema;
  }

  public String getTitle() {
    return this.c;
  }

  public void setTitle(String title) {
    this.c = title;
  }

  public String getDescription() {
    return this.d;
  }

  public void setDescription(String description) {
    this.d = description;
  }

  public String getType() {
    return this.e;
  }

  public void setType(String type) {
    this.e = type;
  }

  public JsonSchemaDescrip() {
  }

  public JsonSchemaDescrip(List<String> required) {
    this.d = "我是一个jsonschema description";
    this.c = "我是一个jsonschema title";
    this.e = "object";
    this.f = required;
  }
}
