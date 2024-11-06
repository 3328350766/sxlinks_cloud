//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sxlinks.modules.online.cgform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.sxlinks.modules.online.cgform.a.a;
import com.sxlinks.modules.online.cgform.entity.OnlCgformField;
import com.sxlinks.modules.online.cgform.model.TreeModel;

public interface OnlCgformFieldMapper extends BaseMapper<OnlCgformField> {
  List<Map<String, Object>> queryListBySql(@Param("sqlStr") String var1);

  Integer queryCountBySql(@Param("sqlStr") String var1);

  void deleteAutoList(@Param("sqlStr") String var1);

  void saveFormData(@Param("sqlStr") String var1);

  void editFormData(@Param("sqlStr") String var1);

  Map<String, Object> queryFormData(@Param("sqlStr") String var1);

  List<Map<String, Object>> queryListData(@Param("sqlStr") String var1);

  IPage<Map<String, Object>> selectPageBySql(Page<Map<String, Object>> var1, @Param("sqlStr") String var2);

  void executeInsertSQL(Map<String, Object> var1);

  void executeUpdatetSQL(Map<String, Object> var1);

  List<String> selectOnlineHideColumns(@Param("user_id") String var1, @Param("online_tbname") String var2);

  List<String> selectOnlineDisabledColumns(@Param("user_id") String var1, @Param("online_tbname") String var2);

  List<String> selectFlowAuthColumns(@Param("table_name") String var1, @Param("task_id") String var2, @Param("rule_type") String var3);

  /** @deprecated */
  @Deprecated
  List<TreeModel> queryDataListByLinkDown(@Param("query") a var1);
}
