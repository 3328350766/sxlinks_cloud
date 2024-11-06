<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      
      <div class="head">
        <div class="title">
          <p>
            <a-icon type="branches" />
            产品及设备状态统计
          </p>
        </div>
        <div class="list">
          <div class="con">
            设备总量
            <span>{{ deviceData.deviceCount }}</span>
          </div>
          <div class="con">
            已激活设备数
            <span>{{ deviceData.deviceEnableCount }}</span>
          </div>
          <div class="con last">
            <div class="con-l" id="con-l">
              <span>{{ lineDevice }}%</span>
              <div>在线设备</div>
            </div>
            <div class="con-r">
              <div><i></i><span>在线设备</span><code>{{ deviceData.deviceOnlineCount }}</code>个</div>
              <div><i></i><span>离线设备</span><code>{{ deviceData.deviceOfflineCount }}</code>个</div>
            </div>
          </div>
        </div>
      </div>
      <div class="foot">
        <a-icon type="line-chart" />
        产品及设备趋势表
        <a-table :columns="columns" :data-source="tableData" :pagination="false" :scroll="{ y: 300 }"
          :loading="tableLoading">
          <span slot="enableStatus" slot-scope="tags">
            <a-tag :color="tags ? 'geekblue' : 'orange'">
              {{ tags ? '已激活' : '未激活' }}
            </a-tag>
          </span>
        </a-table>
      </div>
    </div>
  </a-card>
</template>

<script>
import ProductModal from './modal'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import JDictSelectTag from '@/components/dict/JDictSelectTag'
import * as echarts from 'echarts'
import { getAction } from "@/api/product"
const columns = [
  {
    dataIndex: 'productName',
    key: 'productName',
    title: '产品名称',
  },
  {
    title: '激活状态',
    dataIndex: 'enableStatus',
    key: 'enableStatus',
    scopedSlots: { customRender: 'enableStatus' },
    align: "center"

  },
  {
    title: '设备总数',
    dataIndex: 'deviceCount',
    key: 'deviceCount',
    align: "center"

  },
  {
    title: '设备激活数量',
    key: 'deviceEnableCount',
    dataIndex: 'deviceEnableCount',
    align: "center"

  },
  {
    title: '设备在线数量',
    key: 'deviceOnlineCount',
    dataIndex: 'deviceOnlineCount',
    align: "center"

  }, {
    title: '设备离线数量',
    key: 'deviceOfflineCount',
    dataIndex: 'deviceOfflineCount',
    align: "center"
  },
];


export default {
  name: 'ProductList',
  mixins: [JeecgListMixin],
  components: {
    ProductModal,
    JDictSelectTag,
  },
  data() {
    return {
      description: '产品表管理页面',
      // 表头
      columns,
      tableData: [],
      url: {
        list: '/productCenter/product/list',
        delete: '/productCenter/product/delete',
        deleteBatch: '/productCenter/product/deleteBatch',
        exportXlsUrl: '/productCenter/product/exportXls',
        importExcelUrl: 'productCenter/product/importExcel',
      },
      deviceData: {},
      tableLoading: true
    }
  },
  computed: {
    importExcelUrl: function () {
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
    },
    lineDevice() {
      return (this.deviceData.deviceOnlineCount / this.deviceData.deviceCount * 100).toFixed(2)
    }
  },
  created() {
    getAction('/dataAnalysis/totalTrend/list').then(res => {
      console.log(res);
      this.deviceData = { ...res }
      this.tableData = res.result.records
      this.tableLoading = false
    })
  },
  mounted() {
    setTimeout(() => {
      this.drawLine()
    })
  },
  methods: {
    drawLine() {
      // 基于准备好的dom，初始化echarts实例
      let myChart = echarts.init(document.getElementById('trendCharts'))
      // 绘制图表
      myChart.setOption({
        backgroundColor: new echarts.graphic.LinearGradient(
          0,
          0,
          0,
          1,
          [
            {
              offset: 0,
              color: '#c86589',
            },
            {
              offset: 1,
              color: '#06a7ff',
            },
          ],
          false
        ),
        title: {
          text: 'OCTOBER 2015',
          left: 'center',
          bottom: '5%',
          textStyle: {
            color: '#fff',
            fontSize: 16,
          },
        },
        grid: {
          top: '20%',
          left: '10%',
          right: '10%',
          bottom: '15%',
          containLabel: true,
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: ['14', '15', '16', '17', '18', '19', '20', '21', '22', '23'],
          axisLabel: {
            margin: 30,
            color: '#ffffff63',
          },
          axisLine: {
            show: false,
          },
          axisTick: {
            show: true,
            length: 25,
            lineStyle: {
              color: '#ffffff1f',
            },
          },
          splitLine: {
            show: true,
            lineStyle: {
              color: '#ffffff1f',
            },
          },
        },
        yAxis: [
          {
            type: 'value',
            position: 'right',
            axisLabel: {
              margin: 20,
              color: '#ffffff63',
            },

            axisTick: {
              show: true,
              length: 15,
              lineStyle: {
                color: '#ffffff1f',
              },
            },
            splitLine: {
              show: true,
              lineStyle: {
                color: '#ffffff1f',
              },
            },
            axisLine: {
              lineStyle: {
                color: '#fff',
                width: 2,
              },
            },
          },
        ],
        series: [
          {
            name: '注册总量',
            type: 'line',
            smooth: true, //是否平滑曲线显示
            showAllSymbol: true,
            symbol: 'circle',
            symbolSize: 6,
            lineStyle: {
              normal: {
                color: '#fff', // 线条颜色
              },
            },
            label: {
              show: true,
              position: 'top',
              textStyle: {
                color: '#fff',
              },
            },
            itemStyle: {
              color: 'red',
              borderColor: '#fff',
              borderWidth: 3,
            },
            tooltip: {
              show: false,
            },
            areaStyle: {
              normal: {
                color: new echarts.graphic.LinearGradient(
                  0,
                  0,
                  0,
                  1,
                  [
                    {
                      offset: 0,
                      color: '#eb64fb',
                    },
                    {
                      offset: 1,
                      color: '#3fbbff0d',
                    },
                  ],
                  false
                ),
              },
            },
            data: [393, 438, 485, 631, 689, 824, 987, 1000, 1100, 1200],
          },
        ],
      })
    },
  },
}
</script>
<style scoped lang='less'>
@import '~@assets/less/common.less';

.head {

  .title,
  .foot {
    font-size: 18px;
    font-weight: bold;
    border: 1px solid #d9d9d9;
    line-height: 45px;
    padding: 0 20px;

    >p {
      margin: 0;

      i {}
    }
  }

  .list {
    display: flex;
    justify-content: flex-start;
    height: 140px;
    margin: 15px 0;

    .con {
      flex: 1;
      font-size: 20px;
      border-radius: 5px;
      text-align: center;
      line-height: 140px;
      color: #fff;

      &:nth-of-type(1) {
        background: #2f94f3 url(./images/border_bg01.png) no-repeat center center;
        background-size: 100% 100%;
      }

      &:nth-of-type(2) {
        margin: 0 25px;
        background: #2f94f3 url(./images/border_bg01.png) no-repeat center center;
        background-size: 100% 100%;
      }
    }

    .last {
      flex: 1;
      color: #000;
      display: flex;
      justify-content: flex-start;
      align-items: center;

      >.con-l {
        display: flex;
        justify-content: center;
        flex-direction: column;
        border-radius: 50%;
        border: 1px solid #d9d9d9;
        box-shadow: 0px 0px 5px 5px #d9d9d9;
        height: 140px;
        width: 140px;

        >div {
          display: inline-block;
          height: 30px;
          line-height: 30px;
          font-size: 16px;
        }
      }

      span {
        font-weight: bold;
        display: inline-block;
        height: 30px;
        line-height: 30px;
      }
    }

    #con-l {
      width: 120px;
      height: 120px;
    }

    .con-r {
      flex: 1;
      height: 70px;
      display: flex;
      flex-direction: column;

      >div {
        height: 35px;
        display: flex;
        justify-content: flex-start;
        align-items: center;
        padding-left: 10%;
        box-sizing: border-box;

        &:nth-of-type(1) {}

        &:nth-of-type(2) {
          flex: 1;
        }

        color: #c0bfbf;

        span {
          font-size: 14px;
          margin-right: 5px;
        }

        code {
          color: #000;
        }

        i {
          display: inline-block;
          box-sizing: content-box;
          width: 6px;
          height: 6px;
          border-radius: 50%;
          background: #fff;
          border: 2px solid #000;
          margin-right: 6px;
        }
      }
    }
  }
}

.foot {
  font-size: 18px;
  font-weight: bold;
  border: 1px solid #d9d9d9;
  line-height: 45px;
  padding: 0 20px;
  margin-top: 20px;

  >div {
    display: flex;
    justify-content: space-between;
    align-items: center;

    >p {}

    .data {
      font-size: 14px;

      >span {
        margin-left: 8px;
      }
    }
  }

  >p {
    margin: 0;
  }

  .trend {
    height: 250px;
    width: 100%;

    >div {
      width: 100%;
      height: 100%;
    }
  }
}

.app-list {
  .meta-cardInfo {
    zoom: 1;
    margin-top: 16px;

    >div {
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

::v-deep .ant-table-wrapper {
  width: 100%;

  .ant-spin-nested-loading {
    width: 100%;
  }
}
</style>