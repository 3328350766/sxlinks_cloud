
<template>
  <div class="popup" v-if="show" :style="{ right: right, top: top, width: width }">
    <div class="map" id="popupMap"></div>
  </div>
</template>

<script>
export default {
  name: 'SideMenu',
  components: {},
  mixins: [],
  data() {
    return {
      show: false,
    }
  },
  props: {
    right: {
      type: String,
      default: '-141px',
    },
    top: {
      type: String,
      default: '27px',
    },
    width: {
      type: String,
      default: '700px',
    },
  },
  computed: {},
  mounted() {},
  methods: {
    location() {},
    renderMap(lng, lat,centerLng='112.241971',centerLat='30.063172') {
      let that = this
      var map = new BMap.Map('popupMap', {}) // 创建Map实例
      map.centerAndZoom(new BMap.Point(centerLng, centerLat), 13) // 初始化地图,设置中心点坐标和地图级别
      map.enableScrollWheelZoom() //启用滚轮放大缩小
      map.addEventListener('click', function (e) {
        let point = {
          lng: e.point.lng,
          lat: e.point.lat,
        }
        that.$emit('callBack', point)
        that.show = false
      })
	  if(lat!=null && lng!=null){
      var marker2 = new window.BMap.Marker(new window.BMap.Point(lng,lat));
      map.addOverlay(marker2);
	  }
    },
  },
}
</script>
<style lang="less" scoped>
/* update_end author:sunjianlei date:20190509 for: 修改侧边导航栏滚动条的样式 */
</style>

<!-- update_begin author:sunjianlei date:20190530 for: 选中首页的时候不显示背景颜色 -->
<style lang="less">
/deep/.BMap_noprint {
  display: none !important;
}
/deep/.anchorBL {
  display: none !important;
}
.popup {
  width: 700px;
  height: 440px;
  position: absolute;
  background: red;
  z-index: 1;
  .map {
    height: 100%;
    height: 100%;
  }
}
</style>
<!-- update_end author:sunjianlei date:20190530 for: 选中首页的时候不显示背景颜色 -->
