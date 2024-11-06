//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sxlinks.modules.online.cgform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.sxlinks.modules.online.cgform.entity.OnlCgformHead;

public interface OnlCgformHeadMapper extends BaseMapper<OnlCgformHead> {
  void executeDDL(@Param("sqlStr") String var1);

  List<Map<String, Object>> queryList(@Param("sqlStr") String var1);

  List<String> queryOnlinetables();

  Map<String, Object> queryOneByTableNameAndId(@Param("tbname") String var1, @Param("dataId") String var2);

  void deleteOne(@Param("sqlStr") String var1);

  Integer queryMaxCopyVersion();

  @Select({"select max(copy_version) from onl_cgform_head where physic_id = #{physicId}"})
  Integer getMaxCopyVersion(@Param("physicId") String var1);

  @Select({"select table_name from onl_cgform_head where physic_id = #{physicId}"})
  List<String> queryAllCopyTableName(@Param("physicId") String var1);

  @Select({"select physic_id from onl_cgform_head GROUP BY physic_id"})
  List<String> queryCopyPhysicId();

  String queryCategoryIdByCode(@Param("code") String var1);

  @Select({"select count(*) from ${tableName} where ${pidField} = #{pidValue}"})
  Integer queryChildNode(@Param("tableName") String var1, @Param("pidField") String var2, @Param("pidValue") String var3);
}
