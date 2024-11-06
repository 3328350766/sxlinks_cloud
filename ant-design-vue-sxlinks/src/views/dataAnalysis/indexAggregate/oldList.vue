<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :md="6" :sm="10">
            <a-form-item label="选择产品">
              <j-dict-select-tag v-model="queryParam.state" placeholder="请选择产品" dictCode="state" />
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="10">
            <a-form-item label="">
              <a-input placeholder="请输入产品名称" v-model="queryParam.name"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="10">
            <a-form-item label="参数">
              <j-dict-select-tag v-model="queryParam.state" placeholder="请选择参数" dictCode="state" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :md="6" :sm="8">
            <a-form-item label="选择时间">
              <a-range-picker @change="onChange" />
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <span style="float: left; overflow: hidden" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查看</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">导出</a-button>
            </span>
          </a-col>
        </a-row>
      </a-form>
      <div class="foot">
        <div class="title">
          <p>
            <a-icon type="branches" />
            产品及设备状态统计
          </p>
        </div>
        <div class="charts" id="chartsAggregate"></div>
      </div>
    </div>
  </a-card>
</template>

<script>
import ProductModal from './modal'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import JDictSelectTag from '@/components/dict/JDictSelectTag'
import * as echarts from 'echarts'

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
      columns: [
        {
          title: '#',
          dataIndex: '',
          key: 'rowIndex',
          width: 60,
          align: 'center',
          customRender: function (t, r, index) {
            return parseInt(index) + 1
          },
        },
        {
          title: '产品名称',
          align: 'center',
          dataIndex: 'name',
        },
        {
          title: '产品编码',
          align: 'center',
          dataIndex: 'code',
        },
        {
          title: '类型',
          align: 'center',
          dataIndex: 'typeName',
        },
        {
          title: '节点类型',
          align: 'center',
          dataIndex: 'nodeType_dictText',
        },
        {
          title: '网络',
          align: 'center',
          dataIndex: 'network_dictText',
        },
        {
          title: '协议',
          align: 'center',
          dataIndex: 'protocol_dictText',
        },
        {
          title: '状态',
          align: 'center',
          dataIndex: 'state_dictText',
          // customRender: function(text) {
          //   if (text == 0) {
          //     return '禁用'
          //   } else if (text == 1) {
          //     return '启用'
          //   } else {
          //     return text
          //   }
          // }
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
    }
  },
  computed: {
    importExcelUrl: function () {
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
    },
  },
  mounted() {
    setTimeout(() => {
      this.drawLine()
    })
  },
  methods: {
    drawLine() {
      // 基于准备好的dom，初始化echarts实例
      let myChart = echarts.init(document.getElementById('chartsAggregate'))
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
.title,
.foot {
  font-size: 18px;
  font-weight: bold;
  border: 1px solid #d9d9d9;
  line-height: 45px;
  padding: 0 5px;
  > p {
    margin: 0;
    i {
    }
  }
}
.charts {
  height: 350px;
  width: 100%;
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