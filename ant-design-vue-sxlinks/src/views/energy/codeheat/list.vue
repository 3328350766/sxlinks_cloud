<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="tab">
      <a-tabs default-active-key="1" @tabClick="tabCallback">
        <a-tab-pane v-for="item in tabs" :key="item.key" :tab="item.tab">
          <a-alert :message="item.tip" type="info" show-icon />
          <div class="table-page-search-wrapper" style="margin-top: 30px">
            <a-form layout="inline" @keyup.enter.native="getTabsEnergyDataList">
              <a-row :gutter="24">
                <a-col :md="6" :sm="8">
                  <a-date-picker @change="onChangeDate" :format="dateFormat">
                    <a-icon slot="suffixIcon" type="smile" />
                  </a-date-picker>
                </a-col>
                <a-col :md="6" :sm="8">
                  <span style="float: left; overflow: hidden" class="table-page-search-submitButtons">
                    <a-button type="primary" @click="getTabsEnergyDataList" icon="search">查询</a-button>
                    <a-button type="primary" @click="downFileFunc" style="margin-left: 8px">导出Excel</a-button>
                  </span>
                </a-col>
              </a-row>
            </a-form>
          </div>
          <div class="table-title-wrapper" v-if="!totalData">暂无数据</div>
          <div class="table-title-wrapper" v-else>
            <span>{{ item.tableTitle }}</span
            ><span>功耗</span><span style="color: red; margin: 4px">{{ totalData.totalEnergy }}</span
            ><span style="margin-rignt: 4px">立方米</span>
          </div>
          <a-table ref="table" size="middle" rowKey="id" :columns="columns" :dataSource="dataSource" :loading="loading">
            <template slot="time">{{ ['时段', '日期', '月份', '年份'][Number(item.key * 1 - 1)] }}</template>
            <span slot="action" slot-scope="text, record">
              <a href="javascript:;" @click="openModal(record, item.key)">曲线分析</a>
            </span>
          </a-table>
        </a-tab-pane>
      </a-tabs>
    </div>
    <a-modal
      title="曲线分析"
      :dialog-style="{ top: '20px' }"
      :visible="modal1Visible"
      @ok="handleOk"
      @cancel="handleOk"
      footer=""
    >
      <div class="echart-box" ref="echartBox"></div>
    </a-modal>
  </a-card>
</template>

<script>
import ProductModal from './modal'
import { getQueryById, deleteProperty } from '@/api/product'
import * as echarts from 'echarts'
import { EnergyManagementMixin } from '@/mixins/EnergyManagement'

export default {
  name: 'ProductList',
  mixins: [EnergyManagementMixin],
  components: {
    ProductModal,
  },
  data() {
    return {
      loading: false,
      current: 1,
      total: 0,
      queryParam: {
        deviceName: '',
        deviceCode: '',
        pageNo: 1,
        pageSize: 10,
      },
      dataSource: [],
      description: '产品表管理页面',
      // 表头
      columns: [
        {
          // title: '合计',
          //可以理解为关联插槽了。我试过，scopedSlots和slots应该是columns的一个属性，改成别的就不行。
          //slots: {
          //title: 'title1'
          //},
          scopedSlots: {
            title: 'time',
          },
          align: 'center',
          dataIndex: 'summaryHour',
        },
        // {
        //   title: '时段',
        //   align: 'center',
        //   dataIndex: 'time',
        // },
        {
          title: '设备名称',
          align: 'center',
          dataIndex: 'deviceName',
        },
        {
          title: '功耗',
          align: 'center',
          dataIndex: 'power',
        },
        {
          title: '功耗起始',
          align: 'center',
          dataIndex: 'powerStart',
        },
        {
          title: '功耗结束',
          align: 'center',
          dataIndex: 'powerFinish',
        },
        {
          title: '热量',
          align: 'center',
          dataIndex: 'reliang',
        },
        {
          title: '冷量',
          align: 'center',
          dataIndex: 'lengliang',
        },
        {
          title: '流量',
          align: 'center',
          dataIndex: 'amount',
        },
        {
          title: '流速',
          align: 'center',
          dataIndex: 'liushu',
        },
        {
          title: '进水温度',
          align: 'center',
          dataIndex: 'jinshui',
        },
        {
          title: '回水温度',
          align: 'center',
          dataIndex: 'huishui',
        },

        {
          title: '操作',
          dataIndex: 'action',
          align: 'center',
          scopedSlots: { customRender: 'action' },
        },
      ],
      url: {
        list: '/productCenter/product/list',
        delete: '/productCenter/product/delete',
        deleteBatch: '/productCenter/product/deleteBatch',
        exportXlsUrl: '/productCenter/product/exportXls',
        importExcelUrl: 'productCenter/product/importExcel',
      },
      // new
      tabs: [
        {
          key: '1',
          tab: '时段耗冷热',
          tip: '提示：当天的每个时段耗冷热累计显示',
          tableTitle: '当天累计耗冷热：',
        },
        {
          key: '2',
          tab: '日耗冷热',
          tip: '提示：当月的每天耗冷热累计显示',
          tableTitle: '当月累计耗冷热：',
        },
        {
          key: '3',
          tab: '月耗冷热',
          tip: '提示：当月的每月耗冷热累计显示',
          tableTitle: '当月累计耗冷热： ',
        },
        {
          key: '4',
          tab: '年耗冷热',
          tip: '提示：所有年份耗冷热累计显示',
          tableTitle: '历年所有累计耗冷热：',
        },
      ],
      dateFormat: 'YYYY-MM-DD',
      modal1Visible: false,
    }
  },
  computed: {
    importExcelUrl: function () {
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
    },
  },
  mounted() {
    // this.getEnergyDataList()
  },
  methods: {
    getOptions(record, key) {
      console.log(record)
      console.log(key)
      let xAxisData = []
      let powerData = []
      //let electricYougongData = []
      let lengliangData = []
      let liusuData = []
      let jinshuiData = []
      let reliangData = []
      let huishuiData = []
      const table = {
        1: 'summaryHour',
        2: 'summaryDay',
        3: 'summaryMonth',
        4: 'summaryYear',
      }
      this.echartData.map((item) => {
        if (item.divideCode === record.divideCode) {
          console.log(item[table[key]])
          xAxisData.push(item[table[key]])
          powerData.push(item.electricWugong)
          //electricYougongData.push(item.electricYougong)
          liusuData.push(item.liusu)
          jinshuiData.push(item.jinshui)
          lengliangData.push(item.lengliang)
          huishuiData.push(item.huishui)
          reliangData.push(item.reliang)
        }
      })
      const series = [
        {
          name: '功耗',
          type: 'line',
          stack: 'Total',
          smooth: true,
          lineStyle: {
            width: 0,
          },
          showSymbol: false,
          areaStyle: {
            opacity: 0.8,
            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
              {
                offset: 0,
                color: 'rgb(128, 255, 165)',
              },
              {
                offset: 1,
                color: 'rgb(1, 191, 236)',
              },
            ]),
          },
          emphasis: {
            focus: 'series',
          },
          data: powerData,
        },
        {
          name: '热量',
          type: 'line',
          stack: 'Total',
          smooth: true,
          lineStyle: {
            width: 0,
          },
          showSymbol: false,
          areaStyle: {
            opacity: 0.8,
            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
              {
                offset: 0,
                color: 'rgb(0, 221, 255)',
              },
              {
                offset: 1,
                color: 'rgb(77, 119, 255)',
              },
            ]),
          },
          emphasis: {
            focus: 'series',
          },
          data: reliangData,
        },
        {
          name: '冷量',
          type: 'line',
          stack: 'Total',
          smooth: true,
          lineStyle: {
            width: 0,
          },
          showSymbol: false,
          areaStyle: {
            opacity: 0.8,
            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
              {
                offset: 0,
                color: 'rgb(55, 162, 255)',
              },
              {
                offset: 1,
                color: 'rgb(116, 21, 219)',
              },
            ]),
          },
          emphasis: {
            focus: 'series',
          },
          data: lengliangData,
        },
        {
          name: '流速',
          type: 'line',
          stack: 'Total',
          smooth: true,
          lineStyle: {
            width: 0,
          },
          showSymbol: false,
          areaStyle: {
            opacity: 0.8,
            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
              {
                offset: 0,
                color: 'rgb(255, 0, 135)',
              },
              {
                offset: 1,
                color: 'rgb(135, 0, 157)',
              },
            ]),
          },
          emphasis: {
            focus: 'series',
          },
          data: liusuData,
        },
        {
          name: '流量',
          type: 'line',
          stack: 'Total',
          smooth: true,
          lineStyle: {
            width: 0,
          },
          showSymbol: false,
          label: {
            show: true,
            position: 'top',
          },
          areaStyle: {
            opacity: 0.8,
            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
              {
                offset: 0,
                color: 'rgb(255, 191, 0)',
              },
              {
                offset: 1,
                color: 'rgb(224, 62, 76)',
              },
            ]),
          },
          emphasis: {
            focus: 'series',
          },
          data: liusuData,
        },
        {
          name: '进水温度',
          type: 'line',
          stack: 'Total',
          smooth: true,
          lineStyle: {
            width: 0,
          },
          showSymbol: false,
          label: {
            show: true,
            position: 'top',
          },
          areaStyle: {
            opacity: 0.8,
            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
              {
                offset: 0,
                color: 'rgb(64,178,125)',
              },
              {
                offset: 1,
                color: 'rgb(159,224,128)',
              },
            ]),
          },
          emphasis: {
            focus: 'series',
          },
          data: jinshuiData,
        },
        {
          name: '回水温度',
          type: 'line',
          stack: 'Total',
          smooth: true,
          lineStyle: {
            width: 0,
          },
          showSymbol: false,
          label: {
            show: true,
            position: 'top',
          },
          areaStyle: {
            opacity: 0.8,
            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
              {
                offset: 0,
                color: 'rgb(64,178,125)',
              },
              {
                offset: 1,
                color: 'rgb(159,224,128)',
              },
            ]),
          },
          emphasis: {
            focus: 'series',
          },
          data: huishuiData,
        },
      ]
      console.log(series)
      const legendData = series.map((item) => item.name)
      console.log(xAxisData)
      console.log(legendData)
      console.log(record.deviceName)
      let option = {
        color: ['#80FFA5', '#00DDFF', '#37A2FF', '#FF0087', '#FFBF00', '#40B27D'],
        title: {
          text: record.deviceName,
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross',
            label: {
              backgroundColor: '#6a7985',
            },
          },
        },
        legend: {
          data: legendData,
        },
        toolbox: {
          feature: {
            saveAsImage: {},
          },
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true,
        },
        xAxis: [
          {
            type: 'category',
            boundaryGap: false,
            data: xAxisData,
          },
        ],
        yAxis: [
          {
            type: 'value',
          },
        ],
        series,
      }
      return option
    },
  },
  watch: {},
}
</script>
<style scoped lang='less'>
@import '~@assets/less/common.less';
.col-2 {
  display: flex;
  justify-content: flex-start;
  align-items: center;

  > div {
    margin-bottom: 10px;
    flex: 1;
    &:nth-of-type(2) {
      margin-left: 10px;
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
.tab {
  ::v-deep .ant-tabs {
    .table-title-wrapper {
      padding: 10px;
      margin-bottom: 50px;
      border: 1px solid #00bf8a;
      .value {
        color: red;
      }
    }
  }
}
::v-deep .ant-modal {
  width: 1000px !important;
}
::v-deep .ant-modal-body {
  .echart-box {
    width: 100%;
    height: 60vh;
    
  background-image: linear-gradient(#B86A92, #1F9EEF)
    // background-color: pink;
  }
}
</style>