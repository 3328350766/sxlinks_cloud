import Vue from 'vue'
import { axios } from '@/utils/request'
import signMd5Utils from '@/utils/encryption/signMd5Utils'

const api = {
  user: '/mock/api/user',
  role: '/mock/api/role',
  service: '/mock/api/service',
  permission: '/mock/api/permission',
  permissionNoPager: '/mock/api/permission/no-pager'
}

export default api

export function getDic(parameter) {
  let sign = signMd5Utils.getSign("productCenter/project/list", parameter);
  //将签名和时间戳，添加在请求接口 Header
  let signHeader = {"X-Sign": sign,"X-TIMESTAMP": signMd5Utils.getDateTimeToString()};

  return axios({
    url: "productCenter/project/list",
    method:'get' ,
    data: parameter,
    headers: signHeader
  })
}
// 复制
export function copyAction(parameter) {
  let sign = signMd5Utils.getSign("reportExcel/copy", parameter);
  //将签名和时间戳，添加在请求接口 Header
  let signHeader = {"X-Sign": sign,"X-TIMESTAMP": signMd5Utils.getDateTimeToString()};

  return axios({
    url: "reportExcel/copy",
    method:'post' ,
    data:parameter,
    headers: signHeader
  })
}
//post
export function addAction(parameter) {
  let sign = signMd5Utils.getSign("reportExcel/insert", parameter);
  //将签名和时间戳，添加在请求接口 Header
  let signHeader = {"X-Sign": sign,"X-TIMESTAMP": signMd5Utils.getDateTimeToString()};

  return axios({
    url: "reportExcel/insert",
    method:'post' ,
    data: parameter,
    headers: signHeader
  })
}

//post method= {post | put}
export function httpAction(url,parameter,method) {
  let sign = signMd5Utils.getSign(url, parameter);
  //将签名和时间戳，添加在请求接口 Header
  let signHeader = {"X-Sign": sign,"X-TIMESTAMP": signMd5Utils.getDateTimeToString()};

  return axios({
    url: url,
    method:method ,
    data: parameter,
    headers: signHeader
  })
}

//put
export function putAction(parameter) {
  let sign = signMd5Utils.getSign("reportExcel/update", parameter);
  //将签名和时间戳，添加在请求接口 Header
  let signHeader = {"X-Sign": sign,"X-TIMESTAMP": signMd5Utils.getDateTimeToString()};
  return axios({
    url: "reportExcel/update",
    method:'put',
    data: parameter
  })
}
//get
export function getDetailed(parameter) {
  let sign = signMd5Utils.getSign("reportExcel/"+parameter.id);
  //将签名和时间戳，添加在请求接口 Header
  let signHeader = {"X-Sign": sign,"X-TIMESTAMP": signMd5Utils.getDateTimeToString()};

  return axios({
    url: "reportExcel/"+parameter.id,
    method: 'get',
    headers: signHeader
  })
}

//get
export function getAction(parameter) {
  let sign = signMd5Utils.getSign("reportExcel/query", parameter);
  //将签名和时间戳，添加在请求接口 Header
  let signHeader = {"X-Sign": sign,"X-TIMESTAMP": signMd5Utils.getDateTimeToString()};

  return axios({
    url: "reportExcel/query",
    method: 'get',
    params: parameter,
    headers: signHeader
  })
}

//deleteAction
export function deleteAction(parameter) {
  return axios({
    url: "reportExcel/delete/batch",
    method: 'post',
    data: parameter,
  })
}

// 新增
export function getlistFunction(url,parameter) {
  let sign = signMd5Utils.getSign(url, parameter);
  //将签名和时间戳，添加在请求接口 Header
  let signHeader = {"X-Sign": sign,"X-TIMESTAMP": signMd5Utils.getDateTimeToString()};

  return axios({
    url: url,
    method: 'get',
    params: parameter,
    headers: signHeader
  })
}
export function addProduct(url,parameter) {
  let sign = signMd5Utils.getSign(url, parameter);
  //将签名和时间戳，添加在请求接口 Header
  let signHeader = {"X-Sign": sign,"X-TIMESTAMP": signMd5Utils.getDateTimeToString()};

  return axios({
    url: url,
    method:'post' ,
    data: parameter,
    headers: signHeader
  })
}
// 新增
export function getQueryById(url,parameter) {
  let sign = signMd5Utils.getSign(url, parameter);
  //将签名和时间戳，添加在请求接口 Header
  let signHeader = {"X-Sign": sign,"X-TIMESTAMP": signMd5Utils.getDateTimeToString()};

  return axios({
    url: url,
    method: 'get',
    params: parameter,
    headers: signHeader
  })
}
// 物模型 属性 新增
export function addProperty(url,parameter) {
  let sign = signMd5Utils.getSign(url, parameter);
  //将签名和时间戳，添加在请求接口 Header
  let signHeader = {"X-Sign": sign,"X-TIMESTAMP": signMd5Utils.getDateTimeToString()};

  return axios({
    url: url,
    method:'post' ,
    data: parameter,
    headers: signHeader
  })
}
// 物模型 属性 列表
export function getListProperty(url,parameter) {
  let sign = signMd5Utils.getSign(url, parameter);
  //将签名和时间戳，添加在请求接口 Header
  let signHeader = {"X-Sign": sign,"X-TIMESTAMP": signMd5Utils.getDateTimeToString()};

  return axios({
    url: url,
    method: 'get',
    params: parameter,
    headers: signHeader
  })
}
//  物模型 属性 列表 删除
export function deleteProperty(url,parameter) {
  return axios({
    url: url,
    method: 'delete',
    params: parameter
  })
}
//  物模型 属性 列表 修改
export function editProperty(url,parameter) {
  return axios({
    url: url,
    method:'put',
    data: parameter
  })
}

// 物模型 功能定义 新增  
export function addFunction(url,parameter) {
  let sign = signMd5Utils.getSign(url, parameter);
  //将签名和时间戳，添加在请求接口 Header
  let signHeader = {"X-Sign": sign,"X-TIMESTAMP": signMd5Utils.getDateTimeToString()};

  return axios({
    url: url,
    method:'post' ,
    data: parameter,
    headers: signHeader
  })
}
//  物模型 功能定义 列表 修改
export function editFunction(url,parameter) {
  return axios({
    url: url,
    method:'put',
    data: parameter
  })
}
// 物模型 属性 新增
export function postProperty(url,parameter) {
  let sign = signMd5Utils.getSign(url, parameter);
  //将签名和时间戳，添加在请求接口 Header
  let signHeader = {"X-Sign": sign,"X-TIMESTAMP": signMd5Utils.getDateTimeToString()};

  return axios({
    url: url,
    method:'post' ,
    params: parameter,
    headers: signHeader
  })
}