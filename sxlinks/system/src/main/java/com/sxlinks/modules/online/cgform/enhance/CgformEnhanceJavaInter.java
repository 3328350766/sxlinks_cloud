//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sxlinks.modules.online.cgform.enhance;

import com.alibaba.fastjson.JSONObject;
import java.util.Map;
import com.sxlinks.modules.online.config.exception.BusinessException;

public interface CgformEnhanceJavaInter {
  /** @deprecated */
  @Deprecated
  int execute(String var1, Map<String, Object> var2) throws BusinessException;

  int execute(String var1, JSONObject var2) throws BusinessException;
}
