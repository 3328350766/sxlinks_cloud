<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :md="6" :sm="8">
            <a-form-item label="设备名称">
              <a-input placeholder="请输入设备名称" v-model="queryParam.deviceName"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <span style="float: left; overflow: hidden" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 列表 -->
    <div class="app-list">
      <a-list :grid="{ gutter: 24, lg: 4, md: 2, sm: 1, xs: 1 }" :data-source="data">
        <a-list-item slot="renderItem" slot-scope="item, index">
          <a-card :hoverable="true">
            <div class="list">
              <div class="top">
                <div class="pic">
                  <img src="./images/gateway.png" alt="" />
                </div>
                <div class="contant">
                  <div class="contantTop">
                    <p>{{ item.deviceCode }}</p>
                    <!-- <a-icon type="plus" @click="handleAdd(item)" /> -->
                  </div>
                  <div class="contantBottom">
                    <p>{{ item.deviceName }}</p>
                    <div class="line">
                      <i :class="[item.activeStatus ? 'line' : 'off-line']"></i>{{ item.activeStatus ? '在线' : '离线' }}
                    </div>
                  </div>
                </div>
              </div>
              <div class="bottomList">
                <div class="rol" v-for="(i, index2) in item.lsPropertyData" :key="index2">
                  <div class="rolL">
                    <a-icon type="smile" theme="twoTone" />
                    <p>{{ i.propName }}</p>
                  </div>
                  <div class="rolR">
                    <span>{{ i.value }}</span>
                    <code><span @click="details(i,item)">历史</span></code>
                  </div>
                </div>
              </div>
            </div>
          </a-card>
        </a-list-item>
      </a-list>
      <a-pagination v-model="current" :total='total' show-less-items class="pagination"  :page-size="Number(queryParam.pageSize)" :default-current="Number(queryParam.pageNo)" @change="changePagination"/>
      
    </div>
    <!-- 表单区域 -->
    <DeviceDataHistory ref="modalForm" :deviceCode="deviceCode"></DeviceDataHistory>
		<!--属性数据详情-->
		<DevicePropertyDataList ref="DevicePropertyDataList"></DevicePropertyDataList>
		<!--属性数据详情-下发数据窗体-->
		<DevicePropertyDataSet ref="DevicePropertyDataSet"></DevicePropertyDataSet>
  </a-card>
</template>

<script>
import DeviceDataHistory from './modal'
//设备属性数据列表
import DevicePropertyDataList from '../../productCenter/device/device_property_data_list'
//设备属性数据下发窗体
import DevicePropertyDataSet from '../../productCenter/device/device_property_data_set'
import { getlistFunction, getQueryById, getListProperty, deleteProperty, addFunction } from '@/api/product'
export default {
  name: 'DeviceDataHistoryList',
  mixins: [],
  components: {
    DeviceDataHistory,
		DevicePropertyDataList,
		DevicePropertyDataSet,
  },
  data() {
    return {
      current: 1,
      data: [],
      description: '设备实时数据页面',
      // 表头
      columns: [
        
      ],
      url: {
        list: '/dataCenter/deviceDataHistory/list',
        delete: '/dataCenter/deviceDataHistory/delete',
        deleteBatch: '/dataCenter/deviceDataHistory/deleteBatch',
        exportXlsUrl: '/dataCenter/deviceDataHistory/exportXls',
        importExcelUrl: '/dataCenter/deviceDataHistory/importExcel',
      },
      queryParam: {
        deviceName: null,
        pageNo: 1,
        pageSize: 8,
      },
      total: 0,
      deviceCode: {},
    }
  },
  computed: {
    importExcelUrl: function () {
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
    },
  },
  mounted() {
    this.searchQuery()
  },
  methods: {

	  //设备属性数据详情
	  details(item,i) {
	    this.$refs.DevicePropertyDataList.visible = true
	    this.$refs.DevicePropertyDataList.title = '属性详情数据'
	    this.$refs.DevicePropertyDataList.getDeviceInfo(Object.assign({}, i, item))
	  },
    handleAdd(item) {
      this.deviceCode = item.deviceCode
      this.$refs.modalForm.title = '新增'
      this.$refs.modalForm.visible = true
      this.$refs.modalForm.deviceCode = item
      this.$refs.modalForm.getList()
    },
    cancel() { },
    // 解绑
    confirm(i, deviceCode) {
      let formData = new FormData()
      formData.append('deviceCode', deviceCode)
      formData.append('subDeviceCode', i.deviceCode)
      addFunction(`/dataCenter/deviceDataHistory/unbind`, formData).then((res) => {
        if (res.success) {
          this.$message.info(res.message)
          this.searchQuery()
        } else {
          this.$message.warn(res.message)
        }
      })
    },
    // 查询
    searchQuery() {
      getlistFunction(`/dataCenter/deviceDataHistory/list`, this.queryParam).then((res) => {
        if (res.success) {
          this.data = res.lsData; //单独封装过的
          this.total = res.total;
		  this.current=res.current;
		 
        }
      })
    },
	// 列表分页
	changePagination(e) {
	  this.queryParam.pageNo = Number(e)
	  this.searchQuery()
	},
  },
  
  // 查询网关下的所有子设备
  subEquipment(item) {
    getlistFunction(`/dataCenter/deviceDataHistory/queryAllSubDevice?deviceCode=${item.deviceCode}`).then((res) => {
      if (res.success) {
      }
    })
  },
}
</script>
<style scoped lang="less">
@import '~@assets/less/common.less';

.pagination {
  text-align: right !important;
}

.list {
  .top {
    display: flex;
    justify-content: space-between;
    align-items: center;

    .pic {
      width: 40px;
      height: 40px;
      margin-right: 8px;

      >img {
        width: 100%;
        height: 100%;
      }
    }

    .contant {
      flex: 1;

      .contantTop {
        display: flex;
        justify-content: space-between;
        align-items: center;

        p {
          margin: 0;
          color: rgb(134, 131, 131);
        }
      }

      .contantBottom {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-top: 10px;

        p {
          color: rgb(24, 144, 255);
          margin: 0;
          font-size: 14px;
        }

        .line {
          font-size: 12px;

          i {
            display: inline-block;
            width: 6px;
            height: 6px;
            border-radius: 50%;
            margin-right: 3px;
          }

          .off-line {
            background: red;
          }

          .line {
            background: green;

          }
        }
      }
    }
  }

  .bottomList {
    height: 130px;
    overflow-y: auto;

    .rol {
      display: flex;
      justify-content: space-between;
      align-items: center;
      font-size: 12px;
      line-height: 30px;
      border-bottom: 1px solid #f3eeee;

      .rolL {
        display: flex;
        justify-content: space-between;
        align-items: center;

        p {
          margin: 0 0 0 8px;
        }
      }

      .rolR {
        display: flex;
        justify-content: space-between;
        align-items: center;

        span {
          display: flex;
          justify-content: space-between;
          align-items: center;

          i {
            display: inline-block;
            width: 6px;
            height: 6px;
            border-radius: 50%;
            margin-right: 6px;
          }

          .line {
            background-color: green;

          }

          .off-line {
            background: red;

          }
        }

        code {
          color: rgb(24, 144, 255);
          margin: 0 8px;
          display: flex;
          justify-content: space-between;
          align-items: center;
          margin: 0 8px -3px 8px;

          &:hover {
            cursor: pointer;
          }
        }
      }
    }
  }
}
</style>