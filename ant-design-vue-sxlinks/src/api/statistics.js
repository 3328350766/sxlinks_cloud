/*
 * @Author: haocheng123 269887421@qq.com
 * @Date: 2022-06-06 09:21:19
 * @LastEditors: haocheng123 269887421@qq.com
 * @LastEditTime: 2022-06-06 10:13:25
 * @FilePath: \sxlinks-cloud-web\src\api\Statistics.js
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
import Vue from 'vue'
import {
  axios
} from '@/utils/request'
import signMd5Utils from '@/utils/encryption/signMd5Utils'

//get
export function getstatisticList(url, parameter) {
  let sign = signMd5Utils.getSign(url, parameter);
  //将签名和时间戳，添加在请求接口 Header
  let signHeader = {
    "X-Sign": sign,
    "X-TIMESTAMP": signMd5Utils.getDateTimeToString()
  };

  return axios({
    url: url,
    method: 'get',
    params: parameter,
    headers: signHeader
  })
}

export function getEchartData(url, parameter, method) {
  let sign = signMd5Utils.getSign(url, parameter);
  //将签名和时间戳，添加在请求接口 Header
  let signHeader = { "X-Sign": sign, "X-TIMESTAMP": signMd5Utils.getDateTimeToString() };

  return axios({
    url: url,
    method: method,
    data: parameter,
    headers: signHeader
  })
}
export function downFile(url, dataer) {
  let sign = signMd5Utils.getSign(url, dataer);
  //将签名和时间戳，添加在请求接口 Header
  let signHeader = {
    "X-Sign": sign,
    "X-TIMESTAMP": signMd5Utils.getDateTimeToString()
  };
  return axios({
    url: url,
    method: 'post',
    data: dataer,
    responseType: 'blob'
  })
}