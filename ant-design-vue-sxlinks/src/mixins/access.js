/*
 * @Author: zhaoguiliang 1vt_blybywvlr5@dingtalk.com
 * @Date: 2023-02-22 10:34:36
 * @LastEditors: zhaoguiliang 1vt_blybywvlr5@dingtalk.com
 * @LastEditTime: 2023-02-22 11:23:49
 * @FilePath: \sxlinks-cloud-web\src\mixins\access.js
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
// import { getToken, getAccessUser, hasPermission } from '@/utils/auth'
export default {
  data () {
    return {
    }
  },
  computed: {
    operator: function () {
      return this.getUser.loginName
    },
    operatorText: function () {
      return `${this.getUser.realName}[${this.getUser.loginName}]`
    },
    operatorToken: function (){
      return null
    },
    getUser: function () {
      // let user = getAccessUser()
      // if (user != null) {
      //   return user;
      // } else {
      //   return {};
      // }
    },
    opAuthorities () {
      return this.getUser == null ? [] : this.getUser.authorities
    }
  },
  created () {
  },
  mounted () {
  },
  destroyed () {
  },
  methods: {
    hasPermission (permissionStr) {
      return null
    },
  }
}
