/*
 * @Author: zhaoguiliang 1vt_blybywvlr5@dingtalk.com
 * @Date: 2023-01-12 10:12:26
 * @LastEditors: zhaoguiliang 1vt_blybywvlr5@dingtalk.com
 * @LastEditTime: 2023-02-22 10:32:21
 * @FilePath: \xc-cloud-web\src\main.js
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
/** init domain config */
import './config'

import Vue from 'vue'
import App from './App.vue'
import Storage from 'vue-ls'
import router from './router'
import store from './store/'
import {
  VueAxios
} from "@/utils/request"

import Antd, {
  version
} from 'ant-design-vue'
import mixins from '@/mixins'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import Viser from 'viser-vue'
import 'ant-design-vue/dist/antd.less'; // or 'ant-design-vue/dist/antd.less'
import * as Echarts from 'echarts';
Vue.prototype.$echarts = Echarts;
import '@/permission' // permission control
import '@/utils/filter' // base filter
import Print from 'vue-print-nb-jeecg'
import preview from 'vue-photo-preview'
import 'vue-photo-preview/dist/skin.css'
import BaiduMap from 'vue-baidu-map'
import SSO from '@/cas/sso.js'
import {
  ACCESS_TOKEN,
  DEFAULT_COLOR,
  DEFAULT_THEME,
  DEFAULT_LAYOUT_MODE,
  DEFAULT_COLOR_WEAK,
  SIDEBAR_TYPE,
  DEFAULT_FIXED_HEADER,
  DEFAULT_FIXED_HEADER_HIDDEN,
  DEFAULT_FIXED_SIDEMENU,
  DEFAULT_CONTENT_WIDTH_TYPE,
  DEFAULT_MULTI_PAGE
} from "@/store/mutation-types"
import config from '@/defaultSettings'

import JDictSelectTag from './components/dict/index.js'
import hasPermission from '@/utils/hasPermission'
import vueBus from '@/utils/vueBus';
import JeecgComponents from '@/components/jeecg/index'
import '@/assets/less/JAreaLinkage.less'
import VueAreaLinkage from 'vue-area-linkage'
import '@/components/jeecg/JVxeTable/install'
import '@/components/JVxeCells/install'
// import '../public/rem'
//表单验证
import {
  rules
} from '@/utils/rules'
Vue.prototype.rules = rules
Vue.config.productionTip = false
Vue.use(Storage, config.storageOptions)
Vue.use(Antd)
Vue.use(VueAxios, router)
Vue.use(Viser)
Vue.use(hasPermission)
Vue.use(JDictSelectTag)
Vue.use(Print)
Vue.use(preview)
Vue.use(vueBus);
Vue.use(JeecgComponents);
Vue.use(VueAreaLinkage);
Vue.use(ElementUI);
Vue.mixin(mixins)
Vue.use(BaiduMap, {
  // ak 是在百度地图开发者平台申请的密钥 详见 http://lbsyun.baidu.com/apiconsole/key */
  ak: 'YbGRsQAfQ0UPj89GzcbWPccUIdUua932'
})

import Avue from '@smallwei/avue';
import '@smallwei/avue/lib/index.css';
Vue.use(Avue);
// anji component
import anjiCrud from '@/components/AnjiPlus/anji-crud/anji-crud'
import anjiSelect from '@/components/AnjiPlus/anji-select'
import anjiUpload from '@/components/AnjiPlus/anji-upload'
Vue.component('anji-upload', anjiUpload)
Vue.component('anji-crud', anjiCrud)
Vue.component('anji-select', anjiSelect)

// import echarts from 'echarts';
// 全局定义echarts
import ECharts from 'vue-echarts'
import 'echarts/lib/chart/bar'
import 'echarts/lib/component/tooltip'
//import 'echarts-liquidfill'
// import 'echarts-gl'
Vue.component('v-chart', ECharts)

SSO.init(() => {
  main()
})

function main() {
  new Vue({
    router,
    store,
    mounted() {
      store.commit('SET_SIDEBAR_TYPE', Vue.ls.get(SIDEBAR_TYPE, true))
      store.commit('TOGGLE_THEME', Vue.ls.get(DEFAULT_THEME, config.navTheme))
      store.commit('TOGGLE_LAYOUT_MODE', Vue.ls.get(DEFAULT_LAYOUT_MODE, config.layout))
      store.commit('TOGGLE_FIXED_HEADER', Vue.ls.get(DEFAULT_FIXED_HEADER, config.fixedHeader))
      store.commit('TOGGLE_FIXED_SIDERBAR', Vue.ls.get(DEFAULT_FIXED_SIDEMENU, config.fixSiderbar))
      store.commit('TOGGLE_CONTENT_WIDTH', Vue.ls.get(DEFAULT_CONTENT_WIDTH_TYPE, config.contentWidth))
      store.commit('TOGGLE_FIXED_HEADER_HIDDEN', Vue.ls.get(DEFAULT_FIXED_HEADER_HIDDEN, config.autoHideHeader))
      store.commit('TOGGLE_WEAK', Vue.ls.get(DEFAULT_COLOR_WEAK, config.colorWeak))
      store.commit('TOGGLE_COLOR', Vue.ls.get(DEFAULT_COLOR, config.primaryColor))
      store.commit('SET_TOKEN', Vue.ls.get(ACCESS_TOKEN))
      store.commit('SET_MULTI_PAGE', Vue.ls.get(DEFAULT_MULTI_PAGE, config.multipage))
    },
    render: h => h(App)
  }).$mount('#app')
}