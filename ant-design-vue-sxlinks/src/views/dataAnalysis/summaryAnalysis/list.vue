
<template>
  <div class="page-header-index-wide">
    <a-row :gutter="24">
      <a-col :sm="24" :md="12" :xl="12" :style="{ marginBottom: '24px' }">
        <a-card :hoverable="true">
          <div class="card-title">
            <a-icon type="code-sandbox" />
            产品统计
          </div>
          <div class="card-content">
            <div class="card-item">
              <div class="value">{{ homeData.productCount }}</div>
              <div class="label">产品总数</div>
            </div>
            <div class="card-item">
              <div class="value">{{ homeData.deviceCount }}</div>
              <div class="label">设备总数</div>
            </div>
            <div class="card-item">
              <div class="value">{{ homeData.deviceEnableCount }}</div>
              <div class="label">激活设备数</div>
            </div>
          </div>

        </a-card>
      </a-col>
      <a-col :sm="24" :md="12" :xl="12" :style="{ marginBottom: '24px' }">
        <a-card :hoverable="true">
          <div class="card-title">
            <a-icon type="desktop" />
            设备统计
          </div>
          <div class="echart-content">
            <div class="echart-container" ref="deviceChart"></div>
            <div class="card-number">
              <div class="row">
                <div class="label">在线设备：</div>
                <div class="value">{{ homeData.deviceOnlineCount }}</div>
                <div class="unit">个</div>
              </div>
              <div class="row">
                <div class="label">离线设备：</div>
                <div class="value">{{ homeData.deviceOfflineCount }}</div>
                <div class="unit">个</div>
              </div>
            </div>
          </div>
        </a-card>
      </a-col>
    </a-row>
    <a-row :gutter="24">
      <a-col :sm="24" :md="12" :xl="12" :style="{ marginBottom: '24px' }">
        <a-card :hoverable="true">
          <div class="card-title">
            <a-icon type="stock" />
            流量次数
          </div>
          <div class="card-content">
            <div class="card-item">
              <div class="value">{{ homeData.totalIoCount }}</div>
              <div class="label">总输入输出</div>
            </div>
            <div class="card-item">
              <div class="value">{{ homeData.mqttReportCount }}</div>
              <div class="label">MQTT上报</div>
            </div>
            <div class="card-item">
              <div class="value">{{ homeData.mqttDownCount }}</div>
              <div class="label">MQTT下报</div>
            </div>
          </div>

        </a-card>
      </a-col>
      <a-col :sm="24" :md="12" :xl="12" :style="{ marginBottom: '24px' }">
        <a-card :hoverable="true">
          <div class="card-title">
            <a-icon type="thunderbolt" />
            流量统计
          </div>
          <div class="echart-content">
            <div class="echart-container" ref="flowChart"></div>
            <div class="card-number">
              <div class="row">
                <div class="label">上报流量：</div>
                <div class="value">{{ homeData.flowReportSize }}</div>
                <div class="unit">Mb</div>
              </div>
              <div class="row">
                <div class="label">下发流量：</div>
                <div class="value">{{ homeData.flowDownSize }}</div>
                <div class="unit">Mb</div>
              </div>
            </div>
          </div>
        </a-card>
      </a-col>

    </a-row>
    <a-row :gutter="24">
      <a-col :sm="24" :md="12" :xl="12" :style="{ marginBottom: '24px' }">
        <a-card :hoverable="true">
          <div class="card-title">
            <a-icon type="bell" />
            预警规则统计
          </div>
          <div class="card-content">
            <div class="card-item">
              <div class="value">{{ homeData.alarmRuleCount }}</div>
              <div class="label">预警规则</div>
            </div>
            <div class="card-item">
              <div class="value">{{ homeData.alarmMakeCount }}</div>
              <div class="label">报警次数</div>
            </div>
            <div class="card-item">
              <div class="value">{{ homeData.alarmCancelCount }}</div>
              <div class="label">消警次数</div>
            </div>
          </div>

        </a-card>
      </a-col>
      <a-col :sm="24" :md="12" :xl="12" :style="{ marginBottom: '24px' }">
        <a-card :hoverable="true">
          <div class="card-title">
            <a-icon type="apartment" />
            设备联动统计
          </div>
          <div class="echart-content">
            <div class="card-item">
              <div class="value">{{ homeData.conditionRuleCount }}</div>
              <div class="label">联动规则(个)</div>
            </div>
            <div class="card-item">
              <div class="value">{{ homeData.conditionRuleCount }}</div>
              <div class="label">触动联发(次)</div>
            </div>
          </div>
        </a-card>
      </a-col>
    </a-row>
    <a-row :gutter="24">
      <a-col :sm="24" :md="12" :xl="12" :style="{ marginBottom: '24px' }">
        <a-card :hoverable="true">
          <div class="card-title">
            <a-icon type="area-chart" />
            数据归类规则统计
          </div>
          <div class="card-content">
            <div class="card-item">
              <div class="value">{{ homeData.electricRuleCount }}</div>
              <div class="label">耗电规则</div>
            </div>
            <div class="card-item">
              <div class="value">{{ homeData.waterRuleCount }}</div>
              <div class="label">耗水规则</div>
            </div>
            <div class="card-item">
              <div class="value">{{ homeData.gasRuleCount }}</div>
              <div class="label">耗气规则</div>
            </div>
          </div>

        </a-card>
      </a-col>
      <a-col :sm="24" :md="12" :xl="12" :style="{ marginBottom: '24px' }">
        <a-card :hoverable="true">
          <div class="card-title">
            <a-icon type="pie-chart" />
            数据归类数据统计
          </div>
          <div class="echart-content">

            <div class="card-item">
              <div class="value">{{ homeData.electricUseCount }}</div>
              <div class="label">当日耗电</div>
              <div class="label">千瓦时</div>
            </div>
            <div class="card-item">
              <div class="value">{{ homeData.waterUseCount }}</div>
              <div class="label">当日耗水</div>
              <div class="label">立方米</div>

            </div>
            <div class="card-item">
              <div class="value">{{ homeData.gasUseCount }}</div>
              <div class="label">当日耗气</div>
              <div class="label">立方米</div>
            </div>

          </div>
        </a-card>
      </a-col>
    </a-row>
  </div>
</template>

<script>
import * as echarts from 'echarts'
import { getAction } from "@/api/product"
export default {
  name: "summaryAnalysis",
  components: {

  },
  data() {
    return {
      homeData: {}
    }
  },
  methods: {
    getFlowOptions() {
      const { flowDownSize, flowReportSize } = this.homeData
      console.log(flowDownSize);
      console.log(flowReportSize);
      const data = flowReportSize / (flowReportSize + flowDownSize) * 100
      let option = {
        title: {
          text: data ? data.toFixed(2) + '%' : '0%',//主标题文本
          subtext: '流量占比',//副标题文本
          left: '25%',
          top: '30%',
          textStyle: {
            fontWeight: 'normal',
            fontSize: 14,
            color: '#FF974C',
            align: 'center',
            left: 10
          },
          subtextStyle: {
            fontSize: 12,
            color: '#6c7a89',
          }
        },
        // tooltip: {
        //   trigger: "item",
        //   formatter: "{d}%",
        //   show: true,
        // },

        series: [
          {
            name: 'Access From',
            type: 'pie',
            radius: ['75%', '100%'],
            center: ["50%", "50%"],
            avoidLabelOverlap: false,
            hoverAnimation: false,
            label: {
              normal: {
                show: false,
                position: "center",
              },
              emphasis: {
                show: false,
              },
            },
            labelLine: {
              normal: {
                show: false,
              },
            },
            data: [
              {
                value: data,
                name: "测试",
                selected: false,
                itemStyle: {
                  color: "#FF974C",
                },
                // label: {
                //   normal: {
                //     // 是显示标签
                //     show: true,
                //     position: 'center',
                //     fontSize: 16,
                //     // 标签内容格式器，支持字符串模板和回调函数两种形式，字符串模板与回调函数返回的字符串均支持用 \n 换行
                //     formatter:'{d}%\n\n完成百比分',
                //   }
                // },
              },
              {
                value: data ? 100 - data : 99.99,
                name: "",
                itemStyle: {
                  color: "#E9EEF4",
                },
                label: {
                  normal: {
                    show: false,

                  }
                }
              },
            ]
          }
        ]
      };
      return option
    },
    getDeviceOptions() {
      const { deviceOnlineCount, deviceOfflineCount } = this.homeData
      let data = deviceOnlineCount / (deviceOnlineCount + deviceOfflineCount) * 100;
      let option = {
        title: {
          text: data ? data.toFixed(2) + "%" : "0%",//主标题文本
          subtext: '在线设备',//副标题文本
          left: '25%',
          top: '30%',
          textStyle: {
            fontWeight: 'normal',
            fontSize: 14,
            color: '#FF974C',
            align: 'center',
            left: 10
          },
          subtextStyle: {
            fontSize: 12,
            color: '#6c7a89',
          }
        },
        // tooltip: {
        //   trigger: "item",
        //   formatter: "{d}%",
        //   show: true,
        // },

        series: [
          {
            name: 'Access From',
            type: 'pie',
            radius: ['75%', '100%'],
            center: ["50%", "50%"],
            avoidLabelOverlap: false,
            hoverAnimation: false,
            label: {
              normal: {
                show: false,
                position: "center",
              },
              emphasis: {
                show: false,
              },
            },
            labelLine: {
              normal: {
                show: false,
              },
            },
            data: [
              {
                value: data,
                name: "测试",
                selected: false,
                itemStyle: {
                  color: "#FF974C",
                },
                // label: {
                //   normal: {
                //     // 是显示标签
                //     show: true,
                //     position: 'center',
                //     fontSize: 16,
                //     // 标签内容格式器，支持字符串模板和回调函数两种形式，字符串模板与回调函数返回的字符串均支持用 \n 换行
                //     formatter:'{d}%\n\n完成百比分',
                //   }
                // },
              },
              {
                value: data ? 100 - data : 99.99,
                name: "",
                itemStyle: {
                  color: "#E9EEF4",
                },
                label: {
                  normal: {
                    show: false,

                  }
                }
              },
            ]
          }
        ]
      };
      return option
    },
    getlinkageOptions() {
      let data = 80;
      let option = {
        title: {
          text: '67.45%',//主标题文本
          subtext: '在线设备',//副标题文本
          left: '25%',
          top: '30%',
          textStyle: {
            fontWeight: 'normal',
            fontSize: 14,
            color: '#FF974C',
            align: 'center',
            left: 10
          },
          subtextStyle: {
            fontSize: 12,
            color: '#6c7a89',
          }
        },
        // tooltip: {
        //   trigger: "item",
        //   formatter: "{d}%",
        //   show: true,
        // },

        series: [
          {
            name: 'Access From',
            type: 'pie',
            radius: ['75%', '100%'],
            center: ["50%", "50%"],
            avoidLabelOverlap: false,
            hoverAnimation: false,
            label: {
              normal: {
                show: false,
                position: "center",
              },
              emphasis: {
                show: false,
              },
            },
            labelLine: {
              normal: {
                show: false,
              },
            },
            data: [
              {
                value: data,
                name: "测试",
                selected: false,
                itemStyle: {
                  color: "#FF974C",
                },
                // label: {
                //   normal: {
                //     // 是显示标签
                //     show: true,
                //     position: 'center',
                //     fontSize: 16,
                //     // 标签内容格式器，支持字符串模板和回调函数两种形式，字符串模板与回调函数返回的字符串均支持用 \n 换行
                //     formatter:'{d}%\n\n完成百比分',
                //   }
                // },
              },
              {
                value: 100 - data,
                name: "",
                itemStyle: {
                  color: "#E9EEF4",
                },
                label: {
                  normal: {
                    show: false,

                  }
                }
              },
            ]
          }
        ]
      };
      return option
    },
    getDataOptions() {
      let data = 80;
      let option = {
        title: {
          text: '67.45%',//主标题文本
          subtext: '在线设备',//副标题文本
          left: '25%',
          top: '30%',
          textStyle: {
            fontWeight: 'normal',
            fontSize: 14,
            color: '#FF974C',
            align: 'center',
            left: 10
          },
          subtextStyle: {
            fontSize: 12,
            color: '#6c7a89',
          }
        },
        // tooltip: {
        //   trigger: "item",
        //   formatter: "{d}%",
        //   show: true,
        // },

        series: [
          {
            name: 'Access From',
            type: 'pie',
            radius: ['75%', '100%'],
            center: ["50%", "50%"],
            avoidLabelOverlap: false,
            hoverAnimation: false,
            label: {
              normal: {
                show: false,
                position: "center",
              },
              emphasis: {
                show: false,
              },
            },
            labelLine: {
              normal: {
                show: false,
              },
            },
            data: [
              {
                value: data,
                name: "测试",
                selected: false,
                itemStyle: {
                  color: "#FF974C",
                },
                // label: {
                //   normal: {
                //     // 是显示标签
                //     show: true,
                //     position: 'center',
                //     fontSize: 16,
                //     // 标签内容格式器，支持字符串模板和回调函数两种形式，字符串模板与回调函数返回的字符串均支持用 \n 换行
                //     formatter:'{d}%\n\n完成百比分',
                //   }
                // },
              },
              {
                value: 100 - data,
                name: "",
                itemStyle: {
                  color: "#E9EEF4",
                },
                label: {
                  normal: {
                    show: false,

                  }
                }
              },
            ]
          }
        ]
      };
      return option
    },
  },
  created() {
  },
  mounted() {
    getAction('/dashboard/userAnalysis/queryData').then(res => {
      this.homeData = { ...res }
      let flowChart = echarts.init(this.$refs.flowChart);
      let deviceChart = echarts.init(this.$refs.deviceChart);
      // let linkageChart = echarts.init(this.$refs.linkageChart);
      // let dataChart = echarts.init(this.$refs.dataChart);
      flowChart.setOption(this.getFlowOptions());
      deviceChart.setOption(this.getDeviceOptions());
      // linkageChart.setOption(this.getlinkageOptions());
      // dataChart.setOption(this.getDataOptions());
    })

  }
}
</script>

<style lang="less" scoped>
.page-header-index-wide {
  .card-title {
    color: rgba(0, 0, 0, 0.65);
    font-weight: bold;
    font-size: 18px;
    border-bottom: 1px solid #000;
    padding-bottom: 5px;
  }

  .card-content {
    // background-color: pink;
    margin-top: 15px;
    height: 110px;
    display: flex;
    justify-content: space-between;

    .card-item {
      // background-color: red;
      flex: 1;
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      padding: 10px;
      border: 1px solid #000;
      box-shadow: 3px 3px 2px 2px rgba(0, 0, 0, 0.2);
      font-size: 16px;
      font-weight: 600;

      .value {
        font-size: 40px;
      }

      &:nth-child(2) {
        margin: 0 25px;
      }
    }


  }

  .echart-content {
    margin-top: 15px;
    height: 110px;
    display: flex;
    justify-content: center;

    .card-item {
      // background-color: red;
      // flex: 1;
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      width: 33%;
      padding: 10px;
      border: 1px solid #000;
      box-shadow: 3px 3px 2px 2px rgba(0, 0, 0, 0.2);
      font-size: 16px;
      font-weight: 600;
      margin: 0 25px;

      .value {
        font-size: 40px;
      }

      // &:nth-child(2) {
      //   margin: 0 25px;
      // }
    }

    .echart-container {
      width: 120px;
      height: 120px;
      margin-right: 10%;
    }

    .card-number {
      display: flex;
      flex-direction: column;
      font-size: 16px;
      font-weight: 600;
      justify-content: center;
      padding-top: 15px;

      .row {
        display: flex;
        flex-direction: row;
        margin-bottom: 15px;
        .label,
        .unit {
          display: flex;
          align-items: center;
        }
        .value {

          font-size: 24px;
          margin: 0 15px
        }
      }
    }
  }
}
</style>