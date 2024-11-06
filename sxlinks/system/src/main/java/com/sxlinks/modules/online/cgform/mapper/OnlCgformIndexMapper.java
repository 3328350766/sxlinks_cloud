//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sxlinks.modules.online.cgform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import com.sxlinks.modules.online.cgform.entity.OnlCgformIndex;

public interface OnlCgformIndexMapper extends BaseMapper<OnlCgformIndex> {
  int queryIndexCount(@Param("sqlStr") String var1);
}
