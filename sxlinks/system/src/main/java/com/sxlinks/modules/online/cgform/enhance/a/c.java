//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sxlinks.modules.online.cgform.enhance.a;

import com.alibaba.fastjson.JSONObject;
import java.util.Map;
import com.sxlinks.common.util.oConvertUtils;
import com.sxlinks.modules.online.cgform.enhance.CgformEnhanceJavaInter;
import com.sxlinks.modules.online.config.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("cgformEnhanceImportDemo")
public class c implements CgformEnhanceJavaInter {
  private static final Logger a = LoggerFactory.getLogger(c.class);

  public c() {
  }

  public int execute(String tableName, JSONObject json) throws BusinessException {
    a.info("--------当前tableName=>" + tableName);
    a.info("===============================================================================================================");
    a.info("--------导入JSON数据=>" + json.toJSONString());
    if (oConvertUtils.isEmpty(json.get("name"))) {
      a.info("-----变更导入数据，直接返回1----");
      json.put("name", "默认值");
      return 1;
    } else if (json.getString("name").equals("error")) {
      json.put("name", "默认值");
      throw new BusinessException("测试抛出异常error");
    } else if (json.getString("name").equals("hello")) {
      a.info("-----导入数据变更新，直接返回2----");
      json.put("id", "testid123");
      json.put("name", "JAVA导入增强 测试修改");
      return 2;
    } else if (json.getString("name").equals("ok")) {
      a.info("-----丢弃导入数据，直接返回0----");
      return 0;
    } else {
      return 1;
    }
  }

  public int execute(String tableName, Map<String, Object> map) throws BusinessException {
    return 1;
  }
}
