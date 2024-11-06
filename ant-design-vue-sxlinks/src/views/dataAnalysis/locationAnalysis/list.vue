<!--
 * @Author: haocheng123 269887421@qq.com
 * @Date: 2022-06-15 15:10:57
 * @LastEditors: haocheng123 269887421@qq.com
 * @LastEditTime: 2022-06-15 18:00:22
 * @FilePath: \sxlinks-cloud-web\src\views\dataAnalysis\locationAnalysis\list.vue
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
-->
<template>
  <div class="location-analysis-container" ref="mapChart"></div>
</template>
 
<script>
import * as echarts from 'echarts'
import chinaMap from '@/assets/json/china.json'

export default {
  name: '',
  data() {
    return {
      chart: null,
    }
  },
  mounted() {
    this.getChartData()
  },
  methods: {
    getChartData() {
      this.$nextTick(() => {
        echarts.registerMap('china', { geoJSON: chinaMap })
        this.chart = echarts.init(this.$refs.mapChart)
        const optionRes = this.getOptions()
        console.log(optionRes)
        this.chart.setOption(optionRes)
        var count = 0
        var timeTicket = null
        var dataLength = optionRes.series[0].data.length
        var _this = this

        timeTicket && clearInterval(timeTicket)
        timeTicket = setInterval(function () {
          _this.chart.dispatchAction({
            type: 'downplay',
            seriesIndex: 0,
          })
          _this.chart.dispatchAction({
            type: 'highlight',
            seriesIndex: 0,
            dataIndex: count % dataLength,
          })
          _this.chart.dispatchAction({
            type: 'showTip',
            seriesIndex: 0,
            dataIndex: count % dataLength,
          })
          count++
        }, 1000)

        this.chart.on('mouseover', function (params) {
          console.log(params)
          clearInterval(timeTicket)
          _this.chart.dispatchAction({
            type: 'downplay',
            seriesIndex: 0,
          })
          _this.chart.dispatchAction({
            type: 'highlight',
            seriesIndex: 0,
            dataIndex: params.dataIndex,
          })
          _this.chart.dispatchAction({
            type: 'showTip',
            seriesIndex: 0,
            dataIndex: params.dataIndex,
          })
        })
        this.chart.on('mouseout', function (params) {
          timeTicket && clearInterval(timeTicket)
          timeTicket = setInterval(function () {
            _this.chart.dispatchAction({
              type: 'downplay',
              seriesIndex: 0,
            })
            _this.chart.dispatchAction({
              type: 'highlight',
              seriesIndex: 0,
              dataIndex: count % dataLength,
            })
            _this.chart.dispatchAction({
              type: 'showTip',
              seriesIndex: 0,
              dataIndex: count % dataLength,
            })
            count++
          }, 1000)
        })
      })
    },
    getOptions() {
      var mapIcons = {
        sign: '/asset/get/s/data-1593933773676-FDpSjX2lK.png',
        bg: '/asset/get/s/data-1593933762711-IHRMda0Rg.png',
      }

      // 全国省份列表

      let option = {
        backgroundColor: '#0E1C47',

        visualMap: {
          min: 0,
          max: 1000,
          left: 'left',
          top: 'bottom',
          text: ['高', '低'],
          calculable: false,
          orient: 'horizontal',
          inRange: {
            color: ['#e0ffff', '#006edd'],
            symbolSize: [30, 100],
          },
        },
        tooltip: {
          padding: 8,
          enterable: true,
          transitionDuration: 1,
          backgroundColor: 'rgba(0,0,0,0.6)',

          textStyle: {
            color: '#fff',
            decoration: 'none',
          },
        },

        series: [
          {
            name: '设备数量',
            type: 'map',
            mapType: 'china',
            itemStyle: {
              normal: {
                borderColor: 'rgba(147, 235, 248, 1)',
                borderWidth: 1,
                // areaColor: {
                //   type: 'radial',
                //   x: 0.5,
                //   y: 0.5,
                //   r: 0.8,
                //   colorStops: [
                //     {
                //       offset: 0,
                //       color: 'rgba(147, 235, 248, 0)', // 0% 处的颜色
                //     },
                //     {
                //       offset: 1,
                //       color: 'rgba(147, 235, 248, .5)', // 100% 处的颜色
                //     },
                //   ],
                //   globalCoord: false, // 缺省为 false
                // },
                // shadowColor: 'rgba(128, 217, 248, 1)',
                // shadowColor: 'rgba(255, 255, 255, 1)',
                shadowOffsetX: -5,
                shadowOffsetY: 12,
                shadowBlur: 10,
              },
              emphasis: {
                areaColor: '#389BB7',
                borderWidth: 0,
              },
            },
            label: {
              normal: {
                //静态的时候展示样式
                show: true, //是否显示地图省份得名称
                textStyle: {
                  color: '#fff',
                  fontSize: 12,
                },
              },
              emphasis: {
                //动态展示的样式
                color: '#fff',
              },
            },
            data: [
              {
                name: '北京',
                value: Math.round(Math.random() * 1000),
                tipData: [Math.round(Math.random() * 1000), Math.round(Math.random() * 1000)],
              },
              {
                name: '天津',
                value: Math.round(Math.random() * 1000),
                tipData: [Math.round(Math.random() * 1000), Math.round(Math.random() * 1000)],
              },
              {
                name: '上海',
                value: Math.round(Math.random() * 1000),
                tipData: [Math.round(Math.random() * 1000), Math.round(Math.random() * 1000)],
              },
              {
                name: '重庆',
                value: Math.round(Math.random() * 1000),
                tipData: [Math.round(Math.random() * 1000), Math.round(Math.random() * 1000)],
              },
              {
                name: '河北',
                value: Math.round(Math.random() * 1000),
                tipData: [Math.round(Math.random() * 1000), Math.round(Math.random() * 1000)],
              },
              {
                name: '河南',
                value: Math.round(Math.random() * 1000),
                tipData: [Math.round(Math.random() * 1000), Math.round(Math.random() * 1000)],
              },
              {
                name: '云南',
                value: Math.round(Math.random() * 1000),
                tipData: [Math.round(Math.random() * 1000), Math.round(Math.random() * 1000)],
              },
              {
                name: '辽宁',
                value: Math.round(Math.random() * 1000),
                tipData: [Math.round(Math.random() * 1000), Math.round(Math.random() * 1000)],
              },
              {
                name: '黑龙江',
                value: Math.round(Math.random() * 1000),
                tipData: [Math.round(Math.random() * 1000), Math.round(Math.random() * 1000)],
              },
              {
                name: '湖南',
                value: Math.round(Math.random() * 1000),
                tipData: [Math.round(Math.random() * 1000), Math.round(Math.random() * 1000)],
              },
              {
                name: '安徽',
                value: Math.round(Math.random() * 1000),
                tipData: [Math.round(Math.random() * 1000), Math.round(Math.random() * 1000)],
              },
              {
                name: '山东',
                value: Math.round(Math.random() * 1000),
                tipData: [Math.round(Math.random() * 1000), Math.round(Math.random() * 1000)],
              },
              {
                name: '新疆',
                value: Math.round(Math.random() * 1000),
                tipData: [Math.round(Math.random() * 1000), Math.round(Math.random() * 1000)],
              },
              {
                name: '江苏',
                value: Math.round(Math.random() * 1000),
                tipData: [Math.round(Math.random() * 1000), Math.round(Math.random() * 1000)],
              },
              {
                name: '浙江',
                value: Math.round(Math.random() * 1000),
                tipData: [Math.round(Math.random() * 1000), Math.round(Math.random() * 1000)],
              },
              {
                name: '江西',
                value: Math.round(Math.random() * 1000),
                tipData: [Math.round(Math.random() * 1000), Math.round(Math.random() * 1000)],
              },
              {
                name: '湖北',
                value: Math.round(Math.random() * 1000),
                tipData: [Math.round(Math.random() * 1000), Math.round(Math.random() * 1000)],
              },
              {
                name: '广西',
                value: Math.round(Math.random() * 1000),
                tipData: [Math.round(Math.random() * 1000), Math.round(Math.random() * 1000)],
              },
              {
                name: '甘肃',
                value: Math.round(Math.random() * 1000),
                tipData: [Math.round(Math.random() * 1000), Math.round(Math.random() * 1000)],
              },
              {
                name: '山西',
                value: Math.round(Math.random() * 1000),
                tipData: [Math.round(Math.random() * 1000), Math.round(Math.random() * 1000)],
              },
              {
                name: '内蒙古',
                value: Math.round(Math.random() * 1000),
                tipData: [Math.round(Math.random() * 1000), Math.round(Math.random() * 1000)],
              },
              {
                name: '陕西',
                value: Math.round(Math.random() * 1000),
                tipData: [Math.round(Math.random() * 1000), Math.round(Math.random() * 1000)],
              },
              {
                name: '吉林',
                value: Math.round(Math.random() * 1000),
                tipData: [Math.round(Math.random() * 1000), Math.round(Math.random() * 1000)],
              },
              {
                name: '福建',
                value: Math.round(Math.random() * 1000),
                tipData: [Math.round(Math.random() * 1000), Math.round(Math.random() * 1000)],
              },
              {
                name: '贵州',
                value: Math.round(Math.random() * 1000),
                tipData: [Math.round(Math.random() * 1000), Math.round(Math.random() * 1000)],
              },
              {
                name: '广东',
                value: Math.round(Math.random() * 1000),
                tipData: [Math.round(Math.random() * 1000), Math.round(Math.random() * 1000)],
              },
              {
                name: '青海',
                value: Math.round(Math.random() * 1000),
                tipData: [Math.round(Math.random() * 1000), Math.round(Math.random() * 1000)],
              },
              {
                name: '西藏',
                value: Math.round(Math.random() * 1000),
                tipData: [Math.round(Math.random() * 1000), Math.round(Math.random() * 1000)],
              },
              {
                name: '四川',
                value: Math.round(Math.random() * 1000),
                tipData: [Math.round(Math.random() * 1000), Math.round(Math.random() * 1000)],
              },
              {
                name: '宁夏',
                value: Math.round(Math.random() * 1000),
                tipData: [Math.round(Math.random() * 1000), Math.round(Math.random() * 1000)],
              },
              {
                name: '海南',
                value: Math.round(Math.random() * 1000),
                tipData: [Math.round(Math.random() * 1000), Math.round(Math.random() * 1000)],
              },
              {
                name: '台湾',
                value: Math.round(Math.random() * 1000),
                tipData: [Math.round(Math.random() * 1000), Math.round(Math.random() * 1000)],
              },
              {
                name: '香港',
                value: Math.round(Math.random() * 1000),
                tipData: [Math.round(Math.random() * 1000), Math.round(Math.random() * 1000)],
              },
              {
                name: '澳门',
                value: Math.round(Math.random() * 1000),
                tipData: [Math.round(Math.random() * 1000), Math.round(Math.random() * 1000)],
              },
            ],
          },
        ],
      }
      return option
    },
  },
}
</script>
 
<style lang = "less" scoped>
.location-analysis-container {
  background: #fff;
  border-radius: 2px;
  padding: 15px;
  height: calc(100vh - 228px);
}
</style>
<style lang = "less">
</style>