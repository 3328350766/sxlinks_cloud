import { getAction, deleteAction, putAction, postAction, httpAction } from '@/api/manage'
import Vue from 'vue'
import {UI_CACHE_DB_DICT_DATA } from "@/store/mutation-types"

/**业务模块**/
//产品管理
const addProduct = (params)=>postAction("/productCenter/product/add",params);
const editProduct = (params)=>putAction("/productCenter/product/edit",params);
const checkProductCode = (params)=>getAction("/productCenter/product/checkCode",params);
const queryProductAll = (params)=>getAction("/productCenter/product/queryAll",params);
//设备管理
const addDevice = (params)=>postAction("/productCenter/device/add",params);
const editDevice = (params)=>putAction("/productCenter/device/edit",params);
const checkDeviceCode = (params)=>getAction("/productCenter/device/checkCode",params);
const queryDeviceAll = (params)=>getAction("/productCenter/device/queryAll",params);
//网关管理
const addGateway = (params)=>postAction("/productCenter/gateway/add",params);
const editGateway = (params)=>putAction("/productCenter/gateway/edit",params);
const checkGatewayCode = (params)=>getAction("/productCenter/gateway/checkCode",params);
const queryGatewayAll = (params)=>getAction("/productCenter/gateway/queryAll",params);
//modbus点位管理
const addModbusPoint = (params)=>postAction("/productCenter/modbusPoint/add",params);
const editModbusPoint = (params)=>putAction("/productCenter/modbusPoint/edit",params);
const checkModbusPointCode = (params)=>getAction("/productCenter/modbusPoint/checkCode",params);
const queryModbusPointAll = (params)=>getAction("/productCenter/modbusPoint/queryAll",params);
//opc点位管理
const addOpcPoint = (params)=>postAction("/productCenter/opcPoint/add",params);
const editOpcPoint = (params)=>putAction("/productCenter/opcPoint/edit",params);
const checkOpcPointCode = (params)=>getAction("/productCenter/opcPoint/checkCode",params);
const queryOpcPointAll = (params)=>getAction("/productCenter/opcPoint/queryAll",params);
//设备实时数据
const queryDeviceDataAll = (params)=>getAction("/dataCenter/deviceData/queryAll",params);
//设备历史数据
const queryDeviceDataHistoryAll = (params)=>getAction("/dataCenter/deviceDataHistory/queryAll",params);
//上行历史数据
const queryUpDataHistoryAll = (params)=>getAction("/dataCenter/upDataHistory/queryAll",params);
//下行历史数据 
const queryDownDataHistoryAll = (params)=>getAction("/dataCenter/downDataHistory/queryAll",params);
//modbus点位数据
const queryModbusPointDataAll = (params)=>getAction("/dataCenter/modbusPointData/queryAll",params);
//opc点位数据
const queryOpcPointDataAll = (params)=>getAction("/dataCenter/opcPointData/queryAll",params);
//规则管理
const addRule = (params)=>postAction("/ruleEngine/rule/add",params);
const editRule = (params)=>putAction("/ruleEngine/rule/edit",params);
const checkRuleCode = (params)=>getAction("/ruleEngine/rule/checkCode",params);
const queryRuleAll = (params)=>getAction("/ruleEngine/rule/queryAll",params);
//预警管理
const addAlarm = (params)=>postAction("/ruleEngine/alarm/add",params);
const editAlarm = (params)=>putAction("/ruleEngine/alarm/edit",params);
const checkAlarmCode = (params)=>getAction("/ruleEngine/alarm/checkCode",params);
const queryAlarmAll = (params)=>getAction("/ruleEngine/alarm/queryAll",params);
//条件联动
const addCondition = (params)=>postAction("/ruleEngine/condition/add",params);
const editCondition = (params)=>putAction("/ruleEngine/condition/edit",params);
const checkConditionCode = (params)=>getAction("/ruleEngine/condition/checkProductCode",params);
const queryConditionAll = (params)=>getAction("/ruleEngine/condition/queryProductAll",params);
//数据转发
const addDataForward= (params)=>postAction("/ruleEngine/dataForward/add",params);
const editDataForward = (params)=>putAction("/ruleEngine/dataForward/edit",params);
const checkDataForwardCode = (params)=>getAction("/ruleEngine/dataForward/checkCode",params);
const queryDataForwardAll = (params)=>getAction("/ruleEngine/dataForward/queryAll",params);

//地理分布
const queryLocationAnalysisAll = (params)=>getAction("/productCenter/product/queryAll",params);
//总体趋势
const queryTotalTrendAll = (params)=>getAction("/productCenter/product/queryAll",params);
//指标趋势
const queryIndexTrendAll = (params)=>getAction("/productCenter/product/queryAll",params);
//指标聚合
const queryIndexArggregateAll = (params)=>getAction("/productCenter/product/queryAll",params);

/**系统管理模块**/
//角色管理
const addRole = (params)=>postAction("/sys/role/add",params);
const editRole = (params)=>putAction("/sys/role/edit",params);
const checkRoleCode = (params)=>getAction("/sys/role/checkRoleCode",params);
const queryall = (params)=>getAction("/sys/role/queryall",params);

//用户管理
const addUser = (params)=>postAction("/sys/user/add",params);
const editUser = (params)=>putAction("/sys/user/edit",params);
const queryUserRole = (params)=>getAction("/sys/user/queryUserRole",params);
const getUserList = (params)=>getAction("/sys/user/list",params);
const frozenBatch = (params)=>putAction("/sys/user/frozenBatch",params);
//验证用户是否存在
const checkOnlyUser = (params)=>getAction("/sys/user/checkOnlyUser",params);
//改变密码
const changePassword = (params)=>putAction("/sys/user/changePassword",params);

//权限管理
const addPermission= (params)=>postAction("/sys/permission/add",params);
const editPermission= (params)=>putAction("/sys/permission/edit",params);
const getPermissionList = (params)=>getAction("/sys/permission/list",params);
const getSystemMenuList = (params)=>getAction("/sys/permission/getSystemMenuList",params);
const getSystemSubmenu = (params)=>getAction("/sys/permission/getSystemSubmenu",params);
const getSystemSubmenuBatch = (params) => getAction('/sys/permission/getSystemSubmenuBatch', params)
const queryTreeList = (params)=>getAction("/sys/permission/queryTreeList",params);
const queryTreeListForRole = (params)=>getAction("/sys/role/queryTreeList",params);
const queryListAsync = (params)=>getAction("/sys/permission/queryListAsync",params);
const queryRolePermission = (params)=>getAction("/sys/permission/queryRolePermission",params);
const saveRolePermission = (params)=>postAction("/sys/permission/saveRolePermission",params);
const queryPermissionsByUser = ()=>getAction("/sys/permission/getUserPermissionByToken");
const loadAllRoleIds = (params)=>getAction("/sys/permission/loadAllRoleIds",params);
const getPermissionRuleList = (params)=>getAction("/sys/permission/getPermRuleListByPermId",params);
const queryPermissionRule = (params)=>getAction("/sys/permission/queryPermissionRule",params);

// 部门管理
const queryDepartTreeList = (params)=>getAction("/sys/sysDepart/queryTreeList",params);
const queryDepartTreeSync = (params)=>getAction("/sys/sysDepart/queryDepartTreeSync",params);
const queryIdTree = (params)=>getAction("/sys/sysDepart/queryIdTree",params);
const queryParentName   = (params)=>getAction("/sys/sysDepart/queryParentName",params);
const searchByKeywords   = (params)=>getAction("/sys/sysDepart/searchBy",params);
const deleteByDepartId   = (params)=>deleteAction("/sys/sysDepart/delete",params);

//二级部门管理
const queryDepartPermission = (params)=>getAction("/sys/permission/queryDepartPermission",params);
const saveDepartPermission = (params)=>postAction("/sys/permission/saveDepartPermission",params);
const queryTreeListForDeptRole = (params)=>getAction("/sys/sysDepartPermission/queryTreeListForDeptRole",params);
const queryDeptRolePermission = (params)=>getAction("/sys/sysDepartPermission/queryDeptRolePermission",params);
const saveDeptRolePermission = (params)=>postAction("/sys/sysDepartPermission/saveDeptRolePermission",params);
const queryMyDepartTreeList = (params)=>getAction("/sys/sysDepart/queryMyDeptTreeList",params);

//日志管理
const deleteLog = (params)=>deleteAction("/sys/log/delete",params);
const deleteLogList = (params)=>deleteAction("/sys/log/deleteBatch",params);

//数据字典
const addDict = (params)=>postAction("/sys/dict/add",params);
const editDict = (params)=>putAction("/sys/dict/edit",params);
const treeList = (params)=>getAction("/sys/dict/treeList",params);
const addDictItem = (params)=>postAction("/sys/dictItem/add",params);
const editDictItem = (params)=>putAction("/sys/dictItem/edit",params);

//字典标签专用（通过code获取字典数组）
export const ajaxGetDictItems = (code, params)=>getAction(`/sys/dict/getDictItems/${code}`,params);
//从缓存中获取字典配置
function getDictItemsFromCache(dictCode) {
  if (Vue.ls.get(UI_CACHE_DB_DICT_DATA) && Vue.ls.get(UI_CACHE_DB_DICT_DATA)[dictCode]) {
    let dictItems = Vue.ls.get(UI_CACHE_DB_DICT_DATA)[dictCode];
    //console.log("-----------getDictItemsFromCache----------dictCode="+dictCode+"---- dictItems=",dictItems)
    return dictItems;
  }
}

//系统通告
const doReleaseData = (params)=>getAction("/sys/annountCement/doReleaseData",params);
const doReovkeData = (params)=>getAction("/sys/annountCement/doReovkeData",params);
//获取系统访问量
const getLoginfo = (params)=>getAction("/sys/loginfo",params);
const getVisitInfo = (params)=>getAction("/sys/visitInfo",params);

// 根据部门主键查询用户信息
const queryUserByDepId = (params)=>getAction("/sys/user/queryUserByDepId",params);

// 重复校验
const duplicateCheck = (params)=>getAction("/sys/duplicate/check",params);
// 加载分类字典
const loadCategoryData = (params)=>getAction("/sys/category/loadAllData",params);
const checkRuleByCode = (params) => getAction('/sys/checkRule/checkByCode', params)
//加载我的通告信息
const getUserNoticeInfo= (params)=>getAction("/sys/sysAnnouncementSend/getMyAnnouncementSend",params);
const getTransitURL = url => `/sys/common/transitRESTful?url=${encodeURIComponent(url)}`
// 中转HTTP请求
export const transitRESTful = {
  get: (url, parameter) => getAction(getTransitURL(url), parameter),
  post: (url, parameter) => postAction(getTransitURL(url), parameter),
  put: (url, parameter) => putAction(getTransitURL(url), parameter),
  http: (url, parameter) => httpAction(getTransitURL(url), parameter),
}

export {
  // imgView,
  // doMian,
  addRole,
  editRole,
  checkRoleCode,
  addUser,
  editUser,
  queryUserRole,
  getUserList,
  queryall,
  frozenBatch,
  checkOnlyUser,
  changePassword,
  getPermissionList,
  addPermission,
  editPermission,
  queryTreeList,
  queryListAsync,
  queryRolePermission,
  saveRolePermission,
  queryPermissionsByUser,
  loadAllRoleIds,
  getPermissionRuleList,
  queryPermissionRule,
  queryDepartTreeList,
  queryDepartTreeSync,
  queryIdTree,
  queryParentName,
  searchByKeywords,
  deleteByDepartId,
  deleteLog,
  deleteLogList,
  addDict,
  editDict,
  treeList,
  addDictItem,
  editDictItem,
  doReleaseData,
  doReovkeData,
  getLoginfo,
  getVisitInfo,
  queryUserByDepId,
  duplicateCheck,
  queryTreeListForRole,
  getSystemMenuList,
  getSystemSubmenu,
  getSystemSubmenuBatch,
  loadCategoryData,
  checkRuleByCode,
  queryDepartPermission,
  saveDepartPermission,
  queryTreeListForDeptRole,
  queryDeptRolePermission,
  saveDeptRolePermission,
  queryMyDepartTreeList,
  getUserNoticeInfo,
  getDictItemsFromCache
}



