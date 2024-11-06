<template>
  <a-card :bordered="false">
    <div class="cold-hot">
      <div class="cold-hot-top">
        <div class="conditioner">
          <div class="title">空调冷站 能源总览</div>
          <div class="listTable">
            <ul>
              <li v-for="(item, index) in listTable" :key="index">
                <p>{{ item.name }}</p>
                <p>{{ item.today }}</p>
                <p>{{ item.yesterday }}</p>
                <p>{{ item.month }}</p>
              </li>
            </ul>
          </div>
        </div>
        <div class="energy">
          <div class="title">能效总览</div>
          <div class="energyWrap">
            <div class="energyLeft" id="energyLeft"></div>
            <div class="energyRight" id="energyRight"></div>
          </div>
        </div>
        <div class="host">
          <div class="title">主机能效</div>
          <div class="hostContant">
            <div class="hostTop">
              <div class="hostLecharts" id="hostLecharts"></div>
              <div class="listTable">
                <ul>
                  <li v-for="(item, index) in hostTable" :key="index">
                    <p>{{ item.today }}</p>
                    <p>{{ item.yesterday }}</p>
                    <p>{{ item.month }}</p>
                  </li>
                </ul>
              </div>
            </div>
            <div class="hostBottom" id="hostBottom"></div>
          </div>
        </div>
      </div>
      <div class="cold-hot-bottom">
        <div class="run">
          <div class="title"></div>
          <div class="runTable">
            <div class="listTable">
              <ul>
                <li v-for="(item, index) in listTable" :key="index">
                  <p>{{ item.name }}</p>
                  <p>{{ item.today }}</p>
                  <p>{{ item.yesterday }}</p>
                  <p>{{ item.month }}</p>
                </li>
              </ul>
            </div>
            <div class="listTable">
              <ul>
                <li v-for="(item, index) in listTable" :key="index">
                  <p>{{ item.name }}</p>
                  <p>{{ item.today }}</p>
                  <p>{{ item.yesterday }}</p>
                  <p>{{ item.month }}</p>
                </li>
              </ul>
            </div>
          </div>
        </div>
        <div class="cold">
          <div class="title">空调冷站 运行趋势</div>
          <div class="coldEchaarts" id="coldEchaarts"></div>
        </div>
        <div></div>
      </div>
    </div>
  </a-card>
</template>

<script>
import ProductModal from './modal'
import PhysicalModelModal from './physicalModel'
import JDictSelectTag from '@/components/dict/JDictSelectTag'
import { getQueryById, deleteProperty, getListProperty } from '@/api/product'
import * as echarts from 'echarts'

export default {
  name: 'ProductList',
  mixins: [],
  components: {
    ProductModal,
    PhysicalModelModal,
  },
  data() {
    return {
      hostTable: [
        {
          name: '标准',
          today: '本日',
          yesterday: '上日',
          month: '本月',
        },
        {
          name: '标准',
          today: '85.8',
          yesterday: '8990',
          month: '17536',
        },
        {
          name: '标准',
          today: '85.8',
          yesterday: '8990',
          month: '17536',
        },
        {
          name: '标准',
          today: '85.8',
          yesterday: '8990',
          month: '17536',
        },
      ],
      listTable: [
        {
          name: '',
          today: '本日',
          yesterday: '上日',
          month: '本月',
        },
        {
          name: '电度',
          today: '85.8',
          yesterday: '8990',
          month: '17536',
        },
        {
          name: '电能折标',
          today: '85.8',
          yesterday: '8990',
          month: '17536',
        },
        {
          name: '冷冻水累计流量',
          today: '85.8',
          yesterday: '8990',
          month: '17536',
        },
      ],
    }
  },
  computed: {
    importExcelUrl: function () {
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
    },
  },
  mounted() {
    this.getEnergyDataList()
    this.quantity()
    this.hostEcharts('hostLecharts')
    this.hostEchartsBot('hostBottom')
    this.conditionerCharts('coldEchaarts')
  },
  methods: {
    // 空调
    conditionerCharts(id){
      const xAxisData = [
  "2020-05-11",
  "2020-05-12",
  "2020-05-13",
  "2020-05-14",
  "2020-05-15",
  "2020-05-16",
  "2020-05-17",
  "2020-05-18",
  "2020-05-19",
  "2020-05-20"
];
      let myChart = echarts.init(document.getElementById(id))
      myChart.setOption({
  textStyle: {
    fontFamily: "Din-Light",
  },
  color: [
    "#123dac",
    "#73e2e2",
    "#ff7e85",
    "#9b52ff",
    "#fac524",
    "#46caff"
  ],
  title: {
    text: "",
    left: "47%",
    textStyle: {
      fontSize: 24,
    },
  },
  legend: {
    data: [
      {
        name: "机房COP",
        icon: "path://M512 139.81262864a286.42534744 286.42534744 0 1 0 286.42534744 286.42534744 286.42534744 286.42534744 0 0 0-286.42534744-286.42534744z m0 477.3755789a190.95023144 190.95023144 0 1 1 190.95023144-190.95023146 190.95023144 190.95023144 0 0 1-190.95023144 190.95023146z",
      },
    ],
    left: "left",
    itemWidth: 10,
    itemHeight: 10,
    itemGap: 10,
    textStyle: {
      color: "#898989",
      lineHeight: 15,
    },
    type: "scroll",
  },
  tooltip: {
    backgroundColor: "#fff",
    trigger: "axis",
    axisPointer: {
      type: "none",
    },
    textStyle: {
      color: "#565656",
      lineHeight: 28,
    },
    confine: true,
    padding: 12,
    extraCssText:
      "box-shadow: 0px 2px 8px 0px #cacaca;border-radius: 4px;opacity: 0.9;max-height: 100%;",
    formatter: {
      _custom: {
        type: "function",
        display: "<span>ƒ</span> (params)",
      },
    },
  },
  grid: {
    left: 0,
    right: 0,
    top: 0,
    bottom: '10%',
  },
  xAxis: {
    type: "category",
    boundaryGap: true,
    data: xAxisData,
    axisLabel: {
      color: "#a0a9bc",
      //X轴标签 label 做了特殊处理，防止左右溢出
      formatter: (value, index) => {
        if (index === 0 || index === xAxisData.length - 1) {
          return "";
        }
        return value;
      },
    },
    axisLine: {
      show: false,
    },
    axisTick: {
      show: false,
    },
  },
  yAxis: {
    name: "",
    nameTextStyle: {
      color: "gray",
    },
    type: "value",
    axisLabel: {
      color: "#a0a9bc",
      inside: true,
      margin: 0,
      verticalAlign: "bottom",
    },
    splitLine: {
      lineStyle: {
        type: "dashed",
      },
    },
    minInterval: 1,
    axisLine: {
      show: false,
    },
    axisTick: {
      show: false,
    },
  },
  series: [
    {
      name: "机房COP",
      data: [
        43, 58, 195, 229, 320, 211, 124, 131, 124, 360
      ],
      type: "line",
      smooth: true,
      smoothMonotone: "x",
      cursor: "pointer",
      showSymbol: false,
      lineStyle: {
        shadowColor: "rgba(18,61,172,0.5)",
        shadowBlur: 10,
      },
    }
  ],
})
       window.addEventListener('resize', function () {
        myChart.resize()
      })
    },
    hostEcharts(id) {
      let myChart = echarts.init(document.getElementById(id))
      myChart.setOption({
        grid: {
          left: '5%',
          right: '5%',
          bottom: '4%',
          top: '4%',
          containLabel: true,
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'none',
          },
          formatter: function (params) {
            return (
              params[0].name +
              '<br/>' +
              "<span style='display:inline-block;margin-right:5px;border-radius:10px;width:9px;height:9px;background-color:rgba(36,207,233,0.9)'></span>" +
              params[0].seriesName +
              ' : ' +
              Number((params[0].value.toFixed(4) / 10000).toFixed(2)).toLocaleString() +
              ' 万元<br/>'
            )
          },
        },
        xAxis: {
          show: false,
          type: 'value',
        },
        yAxis: [
          {
            type: 'category',
            inverse: true,
            axisLabel: {
              show: true,
              textStyle: {
                color: '#000',
                fontSize: '10',
              },
            },
            splitLine: {
              show: false,
            },
            axisTick: {
              show: false,
            },
            axisLine: {
              show: false,
            },
            data: ['1#主机', '2#主机', '3#主机', '4#主机', '5#主机'],
          },
          {
            type: 'category',
            inverse: true,
            axisTick: 'none',
            axisLine: 'none',
            show: false,
            axisLabel: {
              textStyle: {
                color: '#000',
                fontSize: '10',
              },
              formatter: function (value) {
                if (value >= 10000) {
                  return (value / 10000).toLocaleString() + '万'
                } else {
                  return value.toLocaleString()
                }
              },
            },
            data: [50000000, 22000000, 10000000, 5000000, 1],
          },
        ],
        series: [
          {
            name: '金额',
            type: 'bar',
            zlevel: 1,
            itemStyle: {
              normal: {
                barBorderRadius: 5,
                color: new echarts.graphic.LinearGradient(0, 0, 1, 0, [
                  {
                    offset: 0,
                    color: 'rgb(57,89,255,1)',
                  },
                  {
                    offset: 1,
                    color: 'rgb(46,200,207,1)',
                  },
                ]),
              },
            },
            barWidth: 10,
            data: [50000000, 22000000, 10000000, 5000000, 1],
          },
        ],
      })
      window.addEventListener('resize', function () {
        myChart.resize()
      })
    },
    hostEchartsBot(id) {
      let myChart = echarts.init(document.getElementById(id))
      myChart.setOption({
        grid: {
          left: '5%',
          right: '5%',
          bottom: '4%',
          top: '4%',
          containLabel: true,
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'none',
          },
          formatter: function (params) {
            return (
              params[0].name +
              '<br/>' +
              "<span style='display:inline-block;margin-right:5px;border-radius:10px;width:9px;height:9px;background-color:rgba(36,207,233,0.9)'></span>" +
              params[0].seriesName +
              ' : ' +
              Number((params[0].value.toFixed(4) / 10000).toFixed(2)).toLocaleString() +
              ' 万元<br/>'
            )
          },
        },
        xAxis: {
          show: false,
          type: 'value',
        },
        yAxis: [
          {
            type: 'category',
            inverse: true,
            axisLabel: {
              show: true,
              textStyle: {
                color: '#000',
                fontSize: '10',
              },
            },
            splitLine: {
              show: false,
            },
            axisTick: {
              show: false,
            },
            axisLine: {
              show: false,
            },
            data: ['1#冷机(本月)', '2#冷机(本月)', '3#冷机(本月)', '4#冷机(本月)'],
          },
          {
            type: 'category',
            inverse: true,
            axisTick: 'none',
            axisLine: 'none',
            show: false,
            axisLabel: {
              textStyle: {
                color: '#000',
                fontSize: '10',
              },
              formatter: function (value) {
                if (value >= 10000) {
                  return (value / 10000).toLocaleString() + '万'
                } else {
                  return value.toLocaleString()
                }
              },
            },
            data: [50000, 22000, 10000, 5000, 1456],
          },
        ],
        series: [
          {
            name: '金额',
            type: 'bar',
            zlevel: 1,
            itemStyle: {
              normal: {
                barBorderRadius: 5,
                color: new echarts.graphic.LinearGradient(0, 0, 1, 0, [
                  {
                    offset: 0.1,
                    color: '#FFC600',
                  },
                  {
                    offset: 0.6,
                    color: '#30D27C',
                  },
                  {
                    offset: 1,
                    color: '#0B95FF',
                  },
                ]),
              },
            },
            barWidth: 10,
            data: [50000000, 22000000, 10000000, 5000000, 1],
          },
        ],
      })
      window.addEventListener('resize', function () {
        myChart.resize()
      })
    },
    // 热量统计
    quantity() {
      getListProperty('/energyCenter/energyData/queryAll').then((res) => {
        if (res.result) {
          this.liuliang = res.result.reduce((total, arr) => total + arr.liuliang, 0)
          let reliang = res.result.reduce((total, arr) => total + arr.reliang, 0)
          let lengliang = res.result.reduce((total, arr) => total + arr.lengliang, 0)
          this.meterCharts('energyLeft', '机房COP(KW/KW)', reliang) //仪表
          this.meterCharts('energyRight', '机房能效(KW/Rt)', lengliang) //仪表
        }
      })
    },
    meterCharts(id, text, value) {
      // 基于准备好的dom，初始化echarts实例
      let myChart = echarts.init(document.getElementById(id))
      // 绘制图表
      myChart.setOption({
        title: {
          text: text,
          x: 'center',
          y: '0',
          textStyle: {
            color: '#000',
            fontSize: 12,
          },
        },
        tooltip: {
          formatter: '{a} <br/>{b} : {c}%',
        },
        toolbox: {
          feature: {},
        },
        series: [
          {
            name: '业务指标',
            type: 'gauge',
            detail: {
              formatter: '{value}',
              textStyle: {
                fontSize: 12,
                color: '#000',
              },
            },
            axisLabel: {
              // show:false,
              distance: -5,
              fontSize: 10,
              splitNumber: 5,
              color: '#00ffff',
            },
            radius: '60%',
            axisTick: {
              distance: -10,
              length: 5,
              lineStyle: {
                width: 2,
                color: '#000',
              },
            },
            splitLine: {
              show: false,
            },
            pointer: {
              length: '50%',
            },
            data: [
              {
                value: value,
                name: '',
              },
            ],
            axisLine: {
              show: true,
              lineStyle: {
                width: 10,
                color: [
                  [
                    1,
                    new echarts.graphic.LinearGradient(0, 0, 1, 0, [
                      {
                        offset: 0.1,
                        color: '#FFC600',
                      },
                      {
                        offset: 0.6,
                        color: '#30D27C',
                      },
                      {
                        offset: 1,
                        color: '#0B95FF',
                      },
                    ]),
                  ],
                ],
              },
            },
          },
        ],
      })
      window.addEventListener('resize', function () {
        myChart.resize()
      })
    },

    //删除
    handleDelete(id) {
      deleteProperty('/energyCenter/energyData/delete?id' + id).then((res) => {
        this.loading != this.loading
        if (res.success) {
          this.$message.success(res.message)
          this.getEnergyDataList()
        } else {
          this.$message.warning(res.message)
        }
        this.loading != this.loading
      })
    },
    // 列表分页
    changePagination(e) {
      this.queryParam.pageNo = Number(e)
      this.getEnergyDataList()
    },
    // 重置
    searchReset() {
      this.queryParam = {
        deviceName: '',
        deviceCode: '',
        pageNo: 1,
        pageSize: 10,
      }
    },
    changePagination() {},
    getEnergyDataList() {
      getQueryById('/energyCenter/energyData/list', this.model).then((res) => {
        this.loading != this.loading
        if (res.success) {
          this.dataSource = res.result.records
          this.total = res.result.total
          this.loading = false
        }
      })
    },
    handleTableChange() {},
    handleDetail(record) {
      this.$refs.PhysicalModelModal.edit(record)
    },
    handleDataRule(record) {
      this.$refs.PhysicalModelModal.edit(record)
    },
    handlePhysicalModel(record) {
      this.$refs.PhysicalModelModal.title = '物模型'
      this.$refs.PhysicalModelModal.disableSubmit = false
      this.$refs.PhysicalModelModal.edit({ status: '1', permsType: '1', route: true, parentId: record.id, menuType: 1 })
    },
  },
}
</script>
<style scoped lang='less'>
@import '~@assets/less/common.less';
.cold-hot {
  .title {
    line-height: 40px;
    background: rgba(95, 212, 110, 0.3);
    font-size: 14px;
    color: #000;
    margin-bottom: 3px;
    padding-left: 6px;
    box-sizing: border-box;
  }
  .listTable {
    ul {
      padding: 0;
      li {
        display: flex;
        justify-content: flex-start;
        align-items: center;
        list-style-type: none;
        margin-bottom: 2px;
        line-height: 50px;
        background: rgba(97, 82, 82, 0.23);
        p {
          flex: 1;
          color: #000;
          text-align: center;
          margin: 0;
        }
      }
    }
  }
  .cold-hot-top {
    display: flex;
    justify-content: flex-start;
    > div {
      flex: 1;
      margin-right: 6px;
    }
    .conditioner {
    }
    .energy {
      .energyWrap {
        height: calc(100% - 40px);
        display: flex;
        justify-content: flex-start;
        align-items: center;
        .energyLeft {
          flex: 1;
          height: 100%;
        }
        .energyRight {
          flex: 1;
          height: 100%;
        }
      }
    }
    .host {
      .hostContant {
        height: calc(100% - 40px);
        .hostTop {
          height: 50%;
          display: flex;
          justify-content: flex-start;
          align-items: center;
          li {
            line-height: 25px;
          }
          .hostLecharts {
            height: 100%;
            flex: 1;
          }
          .listTable {
            height: 100%;
            flex: 1;
          }
        }
        .hostBottom {
          height: 50%;
        }
        // .hostLecharts{
        //   height: 50%;
        // }
      }
    }
  }
  .cold-hot-bottom {
    display: flex;
    justify-content: flex-start;
    > div {
      flex: 1;
      margin-right: 6px;
      li {
        line-height: 27px;
      }
    }
    .run {
      .runTable {
      }
    }
    .cold {
    }
    .coldEchaarts {
      height: calc(100% - 40px);
    }
  }
}

.app-list {
  .meta-cardInfo {
    zoom: 1;
    margin-top: 16px;

    > div {
      position: relative;
      text-align: left;
      float: left;
      width: 50%;

      p {
        line-height: 32px;
        font-size: 24px;
        margin: 0;

        &:first-child {
          color: rgba(0, 0, 0, 0.45);
          font-size: 12px;
          line-height: 20px;
          margin-bottom: 4px;
        }
      }
    }
  }
}
</style>