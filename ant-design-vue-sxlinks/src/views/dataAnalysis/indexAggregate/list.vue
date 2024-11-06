<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form-model ref="dynamicValidateForm" :model="dynamicValidateForm">
        <a-row :gutter="[5, 0]">
          <a-col :span="4">
            <a-form-model-item
              v-for="(device, index) in dynamicValidateForm.device"
              :key="device.key"
              :label="index === 0 ? '设备名称' : ''"
              :prop="'device.' + index + '.value'"
              :rules="{
                required: true,
                message: '请选择设备名称',
                trigger: 'blur',
              }"
            >
              <a-select v-model="device.value" placeholder="选择设备名称" @change="deviceSelectChange(index)">
                <a-select-option v-for="(item, index) in deviceList" :key="index" :value="item.deviceCode">
                  {{ item.deviceName }}
                </a-select-option>
              </a-select>
              <!-- <a-input v-model="device.value" placeholder="please input device" style="margin-right: 8px" /> -->
            </a-form-model-item>
          </a-col>
          <a-col :span="4">
            <a-form-model-item
              v-for="(attr, index) in dynamicValidateForm.attr"
              :key="attr.key"
              :label="index === 0 ? '选择属性' : ''"
              :prop="'attr.' + index + '.value'"
              :rules="{
                message: '请选择设备属性',
                trigger: 'blur',
              }"
            >
              <a-select
                v-model="attr.value"
                :placeholder="attrPlaceholder"
                @focus="attrFocus(attr, index)"
                @change="handleChange"
                :loading="attrLoading"
                :disabled="attrList[index].value.length == 0"
              >
                <a-select-option v-for="(item, index) in attrList[index].value" :key="index" :value="item.identifier">
                  {{ item.name }}
                </a-select-option>
              </a-select>
            </a-form-model-item>
          </a-col>
          <a-col :span="4">
            <a-form-model-item
              v-for="(type, index) in dynamicValidateForm.type"
              :key="type.key"
              :label="index === 0 ? '聚合类型' : ''"
              :prop="'type.' + index + '.value'"
              :rules="{
                required: true,
                message: '请选择聚合类型',
                trigger: 'blur',
              }"
            >
              <a-select v-model="type.value" placeholder="选择聚合类型" @change="handleChange">
                <a-select-option value="avg"> 平均值 </a-select-option>
                <a-select-option value="total"> 累计值 </a-select-option>
              </a-select>
              <a-icon
                v-if="dynamicValidateForm.type.length > 1"
                class="dynamic-delete-button"
                type="minus-circle-o"
                :disabled="dynamicValidateForm.type.length === 1"
                @click="removeDomain(type)"
              />
            </a-form-model-item>
          </a-col>
        </a-row>
        <a-row :gutter="[5, 0]">
          <a-col :span="3">
            <a-form-model-item
              :rules="{ required: true, message: '请选择日期', trigger: 'change' }"
              label="选择日期"
              prop="startDate"
            >
              <a-date-picker @change="onChangeDate" placeholder="选择日期" :format="dateFormat">
                <a-icon slot="suffixIcon" type="smile" />
              </a-date-picker>
            </a-form-model-item>
          </a-col>
          <a-col :span="4">
            <a-form-model-item
              :rules="{ required: true, message: '请选择聚合时段', trigger: 'blur' }"
              label="聚合时段"
              prop="splitType"
            >
              <a-select v-model="dynamicValidateForm.splitType" placeholder="选择聚合时段" @change="handleChange">
                <a-select-option value="hour"> 时段数据 </a-select-option>
                <a-select-option value="day"> 日段数据 </a-select-option>
              </a-select>
            </a-form-model-item>
          </a-col>
          <a-col :span="4" style=""> </a-col>
        </a-row>
        <a-form-model-item>
          <a-button type="dashed" style="margin-right: 15px" @click="addDomain">
            <a-icon type="plus" /> 添加设备
          </a-button>
          <a-button type="primary" html-type="submit" @click="submitForm('dynamicValidateForm')" :loading="subLoading">
            聚合分析
          </a-button>
        </a-form-model-item>
      </a-form-model>
      <div class="foot">
        <div class="title">
          <p>
            <a-icon type="branches" />
            指标聚合曲线分析
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
import { getstatisticList, getEchartData } from '@/api/statistics'
import moment from 'moment'

export default {
  name: 'IndexAggregate',
  mixins: [],
  components: {},
  data() {
    return {
      description: '产品表管理页面',

      dateFormat: 'YYYY-MM-DD',
      attrPlaceholder: '选择设备属性',
      formItemLayout: {
        labelCol: {
          xs: { span: 24 },
          sm: { span: 3 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
      },
      formItemLayoutWithOutLabel: {
        wrapperCol: {
          xs: { span: 24, offset: 0 },
          sm: { span: 20, offset: 4 },
        },
      },
      dynamicValidateForm: {
        device: [],
        attr: [],
        type: [],
        startDate: '',
        splitType: '',
      },
      deviceList: [],
      attrList: [],
      attrLoading: false,
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
      subLoading: false,
    }
  },
  computed: {
    importExcelUrl: function () {
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
    },
  },
  mounted() {
    console.log(this.$route.meta)
    getstatisticList('/productCenter/device/queryAll').then((res) => {
      console.log('queryAll', res)
      this.deviceList = res.result
      this.addDomain()
    })

    // setTimeout(() => {
    //   this.drawLine()
    // })
  },
  methods: {
    moment,
    attrFocus(attr, index) {},
    onChangeDate(date, dateString) {
      console.log(dateString)
      // console.log(date)
      // console.log(dateString)
      // this.dynamicValidateForm.startDate = dateString
      this.$set(this.dynamicValidateForm, 'startDate', dateString)
      //   this.endTime = dateString[1]
    },
    handleChange(value) {
      console.log(`selected ${value}`)
    },
    deviceSelectChange(value) {
      console.log(`selected ${value}`)
      this.attrList.splice(value, 1, { key: Date.now(), value: [] })

      this.dynamicValidateForm.attr.splice(value, 1, {
        value: '',
        key: Date.now(),
      })
      const deviceCode = this.dynamicValidateForm.device[value].value
      if (deviceCode) {
        getstatisticList('/productCenter/device/queryAllProperty', {
          deviceCode,
        }).then((res) => {
          console.log('queryAllProperty', res)
          if (res.result) {
            this.attrList.splice(value, 1, { key: Date.now(), value: res.result })
          } else {
            console.log('该设备没用属性')
          }
        })
      }
    },
    drawLine(echartData) {
      let objKey = Object.keys(echartData)
      console.log(objKey)
      let yAxisData = []
      let xAxisData
      let series = []
      let colorList = ['#80FFA5', '#00DDFF', '#37A2FF', '#FF0087', '#FFBF00']
      if (objKey.length) {
        for (const iterator of objKey) {
          console.log(iterator)
          yAxisData.push(Object.values(echartData[iterator]))
          xAxisData = Object.keys(echartData[iterator])
        }
        console.log(yAxisData)
        console.log(xAxisData)

        for (var i = 0, len = objKey.length; i < len; i++) {
          //  for循环语法结构 注意这个带有,
          // xAxisData = this.newList[i].finishTime //  这里是时间获取时间数据

          series.push({
            name: objKey[i],
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
                color: colorList[i],
              },
            },
            itemStyle: {
              color: colorList[i],
              borderColor: '#fff',
              borderWidth: 3,
            },
            tooltip: {
              show: true,
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
            data: yAxisData[i],
          })
        }
      }
      // 基于准备好的dom，初始化echarts实例
      let myChart = echarts.init(document.getElementById('chartsAggregate'))
      // myChart.dispose()
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
        color: ['#80FFA5', '#00DDFF', '#37A2FF', '#FF0087', '#FFBF00'],
        title: {
          text: '指标聚合曲线分析',
          left: 'center',
          bottom: '5%',
          textStyle: {
            color: '#fff',
            fontSize: 16,
          },
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
          data: objKey,
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
          data: xAxisData,
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
        series,
        // series: [
        //   {
        //     name: '注册总量',
        //     type: 'line',
        //     smooth: true, //是否平滑曲线显示
        //     showAllSymbol: true,
        //     symbol: 'circle',
        //     symbolSize: 6,
        //     lineStyle: {
        //       normal: {
        //         color: '#fff', // 线条颜色
        //       },
        //     },
        //     label: {
        //       show: true,
        //       position: 'top',
        //       textStyle: {
        //         color: '#fff',
        //       },
        //     },
        //     itemStyle: {
        //       color: 'red',
        //       borderColor: '#fff',
        //       borderWidth: 3,
        //     },
        //     tooltip: {
        //       show: false,
        //     },
        //     areaStyle: {
        //       normal: {
        //         color: new echarts.graphic.LinearGradient(
        //           0,
        //           0,
        //           0,
        //           1,
        //           [
        //             {
        //               offset: 0,
        //               color: '#eb64fb',
        //             },
        //             {
        //               offset: 1,
        //               color: '#3fbbff0d',
        //             },
        //           ],
        //           false
        //         ),
        //       },
        //     },
        //     data: [393, 438, 485, 631, 689, 824, 987, 1000, 1100, 1200],
        //   },
        // ],
      })
      this.subLoading = false
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.subLoading = true
          let condition = this.dynamicValidateForm.device.map((item) => {
            let productIndex = this.deviceList.findIndex((item2) => {
              return item2.deviceCode == item.value
            })
            console.log('productIndex>>>', productIndex)
            return {
              deviceCode: item.value,
              productCode: this.deviceList[productIndex].productCode,
            }
          })
          this.dynamicValidateForm.type.forEach((item, index) => {
            condition[index].type = item.value
          })
          this.dynamicValidateForm.attr.forEach((item, index) => {
            condition[index].propertyCode = item.value
            condition[index].splitType = this.dynamicValidateForm.splitType
          })
          const params = {
            condition,
            startDate: this.dynamicValidateForm.startDate,
          }
          getEchartData('/dataAnalysis/indexAggregate/list', params, 'post').then((res) => {
            this.drawLine(res.result)
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    resetForm(formName) {
      this.$refs[formName].resetFields()
    },
    removeDomain(item) {
      console.log('>>><<')
      let index = this.dynamicValidateForm.type.indexOf(item)
      console.log(index)
      if (index !== -1) {
        this.dynamicValidateForm.device.splice(index, 1)
        this.dynamicValidateForm.type.splice(index, 1)
        this.dynamicValidateForm.attr.splice(index, 1)
      }
    },
    addDomain() {
      this.dynamicValidateForm.device.push({
        value: '',
        key: Date.now(),
      })
      this.dynamicValidateForm.attr.push({
        value: '',
        key: Date.now() * 2,
      })
      this.dynamicValidateForm.type.push({
        value: '',
        key: Date.now() * 3,
      })
      this.attrList.push({
        key: Date.now(),
        value: [],
      })
    },
  },
  beforeDestroy() {
    console.log('页面被销毁~~~')
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
.dynamic-delete-button {
  position: absolute;
  right: -8%;
  bottom: 0;
}
</style>