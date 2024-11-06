//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sxlinks.modules.online.cgform.enhance.a;

import com.alibaba.fastjson.JSONObject;
import java.util.Map;
import com.sxlinks.modules.online.cgform.enhance.CgformEnhanceJavaInter;
import com.sxlinks.modules.online.config.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("cgformEnhanceJavaDemo")
public class d implements CgformEnhanceJavaInter {
  private static final Logger a = LoggerFactory.getLogger(d.class);

  public d() {
  }

  public int execute(String tableName, JSONObject json) throws BusinessException {
    a.info("----------我是自定义java增强测试demo类-----------");
    a.info("--------当前tableName=>" + tableName);
    a.info("--------当前JSON数据=>" + json.toJSONString());
    return 1;
  }

  public int execute(String tableName, Map<String, Object> map) throws BusinessException {
    return 1;
  }
}
