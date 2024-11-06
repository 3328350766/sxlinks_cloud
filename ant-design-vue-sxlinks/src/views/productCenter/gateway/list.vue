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
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 列表 -->
    <div class="app-list">
      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        :columns="columnsList"
        :dataSource="data"
        :pagination="false"
        :loading="loading"
        @change="handleTableChange">
        <span slot="action" slot-scope="text, record">
          <!-- <a @click="handleAdd(record)" style="margin-right:8px">绑定子设备</a> -->
          <a @click="handleShow(record)">查看子设备</a>

         <!-- <a-divider type="vertical"/> -->
        </span>
      </a-table>
      <!-- <a-list :grid="{ gutter: 24, lg: 4, md: 2, sm: 1, xs: 1 }" :data-source="data">
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
                    <a-icon type="plus" @click="handleAdd(item)" />
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
                <div class="rol" v-for="(i, index2) in item.lsSubDevice" :key="index2">
                  <div class="rolL">
                    <a-icon type="smile" theme="twoTone" />
                    <p>{{ i.deviceName }}</p>
                  </div>
                  <div class="rolR">
                    <span><i :class="[i.activeStatus ? 'line' : 'off-line']"></i>{{ i.activeStatus ? '在线' : '离线'
                    }}</span>
                    <a-popconfirm title="确认解绑该设备" ok-text="Yes" cancel-text="No" @confirm="confirm(i, item.deviceCode)"
                      @cancel="cancel">
                      <code>解绑</code>
                    </a-popconfirm>
                  </div>
                </div>
              </div>
            </div>
          </a-card>
        </a-list-item>
      </a-list> -->
      <a-pagination v-model="queryParam.pageNo" :total='total' show-less-items class="pagination"  :page-size="Number(queryParam.pageSize)" :default-current="Number(queryParam.pageNo)" @change="changePagination"/>
    </div>
    <!-- 表单区域 -->
    <Gateway-modal ref="modalForm" :deviceCode="deviceCode"></Gateway-modal>
    <!-- 查看表单区域 -->
    <showGateway-modal ref="showForm" :deviceCode="deviceCode"></showGateway-modal>
  </a-card>
</template>

<script>
import GatewayModal from './modal'
import showGatewayModal from './showModal'

import { getlistFunction, getQueryById, getListProperty, deleteProperty, addFunction } from '@/api/product'
export default {
  name: 'GatewayList',
  mixins: [],
  components: {
    GatewayModal,showGatewayModal
  },
  data() {
    return {
      columnsList:[
		
        {
          title: '名称',
          align: 'left',
          dataIndex: 'deviceName',
        },
		{
		  title: '编码',
		  align: 'left',
		  dataIndex: 'deviceCode',
		},
		{
		  title: '类型',
		  align: 'center',
		  dataIndex: 'gatewayType',
		  customRender: function (text) {
					if(text=='software'){
						return "软网关";
					}
					if(text=='hardware'){
						return  "硬网关";
					}
					
		      
		    }
		},
		{
		  title: '子设备数量',
		  align: 'center',
		  dataIndex: 'lsSubDevice.length',
		},
        {
          title: '在线状态',
          align: 'center',
          dataIndex: 'activeStatus',
          customRender: function (text) {
              return text==0?"离线":'在线'
            }
        },
				{
				  title: '最近上线时间',
				  align: 'center',
				  dataIndex: 'lastOnlineTime',
				},
				{
				  title: '上线所在ip',
				  align: 'center',
				  dataIndex: 'devHost',
				},
				{
				  title: '上线所在端口',
				  align: 'center',
				  dataIndex: 'devPort',
				},
				{
				  title: '启用状态',
				  align: 'center',
				  dataIndex: 'enableStatus',
				  customRender: function (text) {
				      return text==0?"禁用":'启用'
				    }
				},
        {
          title: '操作',
          dataIndex: 'action',
          align: 'center',
          scopedSlots: { customRender: 'action' },
        },
      ],

      current: 1,
      data: [],
      description: '职务表管理页面',
     
      url: {
        list: '/productCenter/gateway/list',
        delete: '/productCenter/gateway/delete',
        deleteBatch: '/productCenter/gateway/deleteBatch',
        exportXlsUrl: '/productCenter/gateway/exportXls',
        importExcelUrl: 'productCenter/gateway/importExcel',
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
    handleShow(item){
      this.$refs.showForm.title = '查看'
      this.$refs.showForm.visible = true
      this.$refs.showForm.showData(item.deviceCode)
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
      addFunction(`/productCenter/gateway/unbind`, formData).then((res) => {
        if (res.success) {
          this.$message.info(res.message)
          this.searchQuery()
        } else {
          this.$message.warn(res.message)
        }
      })
    },
    searchReset(){
      this.queryParam={
        deviceName: null,
        pageNo: 1,
        pageSize: 8,
      },
      this.searchQuery()
    },
    // 查询
    searchQuery() {
      getlistFunction(`/productCenter/gateway/list`, this.queryParam).then((res) => {
        if (res.success) {
          this.data = res.result.records
          this.total = res.result.total
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
    getlistFunction(`/productCenter/gateway/queryAllSubDevice?deviceCode=${item.deviceCode}`).then((res) => {
      if (res.success) {
      }
    })
  },
}
</script>
<style scoped lang="less">
@import '~@assets/less/common.less';
.pagination{
  text-align: right !important;
  margin: 10px 0 0 0;
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
    height: 100px;
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