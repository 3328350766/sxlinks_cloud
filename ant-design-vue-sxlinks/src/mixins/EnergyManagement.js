import {
  getstatisticList
} from '@/api/statistics'
import {
  downFile
} from '@/api/statistics'
import moment from 'moment'

export const EnergyManagementMixin = {
  data() {
    return {
      currentKey: "1",
      startTime: '',
      endTime: '',
      pagination: {
        pageNo: 1,
        // defaultCurrent: 1,
        total: 0, // 总数
        showSizeChanger: true,
        pageSizeOptions: ['1', '10', '20', '40', '80', '100'],
        showTotal: (total) => `共 ${total} 条`, // 分页中显示总的数据
        // hideOnSinglePage: true, // 只有一页时是否隐藏分页器
        pageSize: 100, // 每页条数，所有页设置统一的条数
        onChange: (page, pageSize) => this.onPageChange(page, pageSize), //点击页码事件
        onShowSizeChange: (current, pageSize) => this.onSizeChange(current, pageSize), // 改变每页数量时更新显示
      },
      echartData: [],
      apiSrc: "",
      totalData: null
    }
  },
  mounted() {
    this.getTabsEnergyDataList()
    console.log(this.$route.path);
  },
  computed: {
    getApiSrc() {
      if (this.$route.path === "/areaEnergy/list") {
        if (this.currentKey === "1") {
          return '/energyCenter/summaryAreaEnergyData/listHour'
        } else if (this.currentKey === "2") {
          return '/energyCenter/summaryAreaEnergyData/listDay'

        } else if (this.currentKey === "3") {
          return '/energyCenter/summaryAreaEnergyData/listMonth'

        } else if (this.currentKey === "4") {
          return '/energyCenter/summaryAreaEnergyData/listYear'

        }
      } else if (this.$route.path === "/electric/list") {
        if (this.currentKey === "1") {
          return '/energyCenter/summaryElectricData/listHour'
        } else if (this.currentKey === "2") {
          return '/energyCenter/summaryElectricData/listDay'

        } else if (this.currentKey === "3") {
          return '/energyCenter/summaryElectricData/listMonth'

        } else if (this.currentKey === "4") {
          return '/energyCenter/summaryElectricData/listYear'
        }
      } else if (this.$route.path === "/water/list") {
        if (this.currentKey === "1") {
          return '/energyCenter/summaryWaterData/listHour'
        } else if (this.currentKey === "2") {
          return '/energyCenter/summaryWaterData/listDay'

        } else if (this.currentKey === "3") {
          return '/energyCenter/summaryWaterData/listMonth'

        } else if (this.currentKey === "4") {
          return '/energyCenter/summaryWaterData/listYear'
        }
      } else if (this.$route.path === "/gas/list") {
        if (this.currentKey === "1") {
          return '/energyCenter/summaryGasData/listHour'
        } else if (this.currentKey === "2") {
          return '/energyCenter/summaryGasData/listDay'

        } else if (this.currentKey === "3") {
          return '/energyCenter/summaryGasData/listMonth'

        } else if (this.currentKey === "4") {
          return '/energyCenter/summaryGasData/listYear'
        }
      } else if (this.$route.path === "/codeheat/list") {
        if (this.currentKey === "1") {
          return '/energyCenter/summaryEnergyData/listHour'
        } else if (this.currentKey === "2") {
          return '/energyCenter/summaryEnergyData/listDay'

        } else if (this.currentKey === "3") {
          return '/energyCenter/summaryEnergyData/listMonth'

        } else if (this.currentKey === "4") {
          return '/energyCenter/summaryEnergyData/listYear'
        }
      }
    },
    getDownFileSrc() {
      if (this.$route.path === "/areaEnergy/list") {
        if (this.currentKey === "1") {
          return '/energyCenter/summaryAreaEnergyData/exportXlsByHour'
        } else if (this.currentKey === "2") {
          return '/energyCenter/summaryAreaEnergyData/exportXlsByDay'

        } else if (this.currentKey === "3") {
          return '/energyCenter/summaryAreaEnergyData/exportXlsByMonth'

        } else if (this.currentKey === "4") {
          return '/energyCenter/summaryAreaEnergyData/exportXlsByYear'

        }
      } else if (this.$route.path === "/electric/list") {
        if (this.currentKey === "1") {
          return '/energyCenter/summaryElectricData/exportXlsByHour'
        } else if (this.currentKey === "2") {
          return '/energyCenter/summaryElectricData/exportXlsByDay'

        } else if (this.currentKey === "3") {
          return '/energyCenter/summaryElectricData/exportXlsByMonth'

        } else if (this.currentKey === "4") {
          return '/energyCenter/summaryElectricData/exportXlsByYear'
        }
      } else if (this.$route.path === "/water/list") {
        if (this.currentKey === "1") {
          return '/energyCenter/summaryWaterData/exportXlsByHour'
        } else if (this.currentKey === "2") {
          return '/energyCenter/summaryWaterData/exportXlsByDay'

        } else if (this.currentKey === "3") {
          return '/energyCenter/summaryWaterData/exportXlsByMonth'

        } else if (this.currentKey === "4") {
          return '/energyCenter/summaryWaterData/exportXlsByYear'
        }
      } else if (this.$route.path === "/gas/list") {
        if (this.currentKey === "1") {
          return '/energyCenter/summaryGasData/exportXlsByHour'
        } else if (this.currentKey === "2") {
          return '/energyCenter/summaryGasData/exportXlsByDay'

        } else if (this.currentKey === "3") {
          return '/energyCenter/summaryGasData/exportXlsByMonth'

        } else if (this.currentKey === "4") {
          return '/energyCenter/summaryGasData/exportXlsByYear'
        }
      } else if (this.$route.path === "/codeheat/list") {
        if (this.currentKey === "1") {
          return '/energyCenter/summaryEnergyData/exportXlsByHour'
        } else if (this.currentKey === "2") {
          return '/energyCenter/summaryEnergyData/exportXlsByDay'

        } else if (this.currentKey === "3") {
          return '/energyCenter/summaryEnergyData/exportXlsByMonth'

        } else if (this.currentKey === "4") {
          return '/energyCenter/summaryEnergyData/exportXlsByYear'
        }
      }
    }
  },
  methods: {
    moment,
    downFileFunc() {
      downFile(this.getDownFileSrc).then(res => {
        console.log(res);
      })
    },
    getTabsEnergyDataList() {
      const params = {
        pageNo: this.pagination.pageNo,
        pageSize: this.pagination.pageSize,
        startTime: this.startTime,
      }
      getstatisticList(this.getApiSrc, params).then((res) => {
        console.log(res);
        this.dataSource = res.result.records
        this.totalData = res.totalData
        this.pagination.total = res.result.total
      })
    },

    tabCallback(key) {
      console.log(key)
      const table = {
        1: 'summaryHour',
        2: 'summaryDay',
        3: 'summaryMonth',
        4: 'summaryYear',
      }

      const item = {
        scopedSlots: {
          title: 'time',
        },
        align: 'center',
        dataIndex: table[key],
      }

      this.currentKey = key
      this.$set(this.columns, 0, item)

      this.startTime = ''
      this.pagination.pageNo = 1
      this.getTabsEnergyDataList()
    },
    onChangeDate(date, dateString) {
      console.log(dateString)
      // console.log(date)
      // console.log(dateString)
      this.startTime = dateString
      //   this.endTime = dateString[1]
      this.pageNo = 1
    },
    handleOk(e) {
      this.modal1Visible = false
    },
    openModal(record, key) {
      this.chart ? this.chart.dispose() : "";
      this.modal1Visible = true
      getstatisticList(this.getApiSrc, {
        pageSize: 99999,
        startTime: this.startTime
      }).then((res) => {
        this.echartData = res.result.records
      })
      setTimeout(() => {
        this.$nextTick(() => {
          this.chart = this.$echarts.init(this.$refs.echartBox)
          this.chart.setOption(this.getOptions(record, key))
          // this.$refs.flowChart.addEventListener('mouseout', function () {
          //   chart.dispatchAction({
          //     type: 'hideTip',
          //   })
          // })
        })
      }, 2000)
    },
    onPageChange(page, pageSize) {
      this.pagination.pageNo = page
      this.getTabsEnergyDataList()
    },
    onSizeChange(current, pageSize) {
      this.pagination.pageNo = 1
      this.pagination.pageSize = pageSize
      this.getTabsEnergyDataList()
    },
  }
}