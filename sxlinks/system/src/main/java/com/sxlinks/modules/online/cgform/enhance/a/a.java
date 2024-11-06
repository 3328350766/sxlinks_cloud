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

@Component("cgformEnhanceBeanDemo")
public class a implements CgformEnhanceJavaInter {
  private static final Logger a = LoggerFactory.getLogger(a.class);

  public a() {
  }

  public int execute(String tableName, JSONObject json) throws BusinessException {
    a.info("--------我是自定义java增强测试bean-----------");
    a.info("--------当前表单 tableName=> " + tableName);
    a.info("--------当前表单 JSON数据=> " + json.toJSONString());
    if (json.containsKey("phone")) {
      json.put("phone", "18611100000");
    }

    return 1;
  }

  public int execute(String tableName, Map<String, Object> map) throws BusinessException {
    return 1;
  }
}
