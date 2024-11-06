/*
 * @Author: haocheng123 269887421@qq.com
 * @Date: 2022-06-06 09:19:30
 * @LastEditors: haocheng123 269887421@qq.com
 * @LastEditTime: 2022-06-15 14:06:59
 * @FilePath: \sxlinks-cloud-web\src\store\getters.js
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
import Vue from 'vue'
import {
  USER_INFO,
  ENHANCE_PRE
} from "@/store/mutation-types"
const getters = {
  device: state => state.app.device,
  theme: state => state.app.theme,
  color: state => state.app.color,
  token: state => state.user.token,
  avatar: state => {
    state.user.avatar = Vue.ls.get(USER_INFO).avatar;
    return state.user.avatar
  },
  username: state => state.user.username,
  nickname: state => {
    state.user.realname = Vue.ls.get(USER_INFO).realname;
    return state.user.realname
  },
  welcome: state => state.user.welcome,
  permissionList: state => state.user.permissionList,
  userInfo: state => {
    state.user.info = Vue.ls.get(USER_INFO);
    return state.user.info
  },
  addRouters: state => state.permission.addRouters,
  keepAlive: state => state.permission.keepAlive,
  onlAuthFields: state => {
    return state.online.authFields
  },
  enhanceJs: (state) => (code) => {
    state.enhance.enhanceJs[code] = Vue.ls.get(ENHANCE_PRE + code);
    return state.enhance.enhanceJs[code]
  }

}

export default getters