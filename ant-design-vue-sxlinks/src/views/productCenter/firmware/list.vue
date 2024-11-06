<template>
  <a-card :bordered="false">

    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">

          <a-col :md="6" :sm="8">
            <a-form-item label="产品编码">
              <a-input placeholder="请输入产品编码" v-model="queryParam.code"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="产品名称">
              <a-input placeholder="请输入产品名称" v-model="queryParam.name"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="detailsClick" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>



    <!-- table区域-begin -->
    <div>

      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :loading="loading"
        @change="handleTableChange">

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>
          <a @click="detailsClick" style="margin:0 10px">查看</a>
          <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
          <a-divider type="vertical"/>
        </span>

      </a-table>
    </div>
    <!-- table区域-end -->
		<!--table块状结束-->
    <!-- 表单区域 -->
    <Product-modal ref="modalForm" @ok="modalFormOk"></Product-modal>
    <a-modal
      title="设备详情"
      :visible="visible"
      :width="900"
      :confirm-loading="confirmLoading"
      @ok="handleOk"
      @cancel="handleCancel"
    >
      <div class="deviceModal">
        <div class="equ">设备：IP DOEM</div>
        <div class="deviceTitle">
          <p>ID:3456885224552221145555</p>
          <p>产品：视频国标设备</p>
        </div>
        <a-tabs default-active-key="1" @change="callback">
          <a-tab-pane key="1" tab="实例信息">
            <div class="product">固件信息</div>
              <div class="table">
                <div class="col">
                  <div class="colName">
                    <span class="sign">固件名称</span>
                    <code>yanshi</code>
                    <span class="sign">所属产品</span>
                    <code>智能生活</code>
                    <span class="sign">版本</span>
                    <code>111</code>
                  </div>
                  <div class="colName">
                    <span class="sign">版本序号</span>
                    <code>yanshi</code>
                    <span class="sign">签名方式</span>
                    <code>智能生活</code>
                    <span class="sign">签名</span>
                    <code>直链设备</code>
                  </div>
                  <div class="colName explain">
                    <span class="sign">说明</span>
                    <code>yanshi</code>
                  </div>
                </div>
              </div>
          </a-tab-pane>
        </a-tabs>
      </div>
    </a-modal>
  </a-card>
</template>

<script>
  import ProductModal from './modal'
  import PhysicalModelModal from './physicalModel'
  import JDictSelectTag from '@/components/dict/JDictSelectTag'

  export default {
    name: 'ProductList',
    mixins: [],
    components: {
      ProductModal,
	  PhysicalModelModal,
      JDictSelectTag
    },
    data() {
      return {
        queryParam:{},
        visible: false,
        confirmLoading: false,
        description: '产品表管理页面',
        dataSource:[],
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
            }
          },
		  {
		    title: '固件名称',
		    align: 'center',
		    dataIndex: 'name'
		  },
          {
            title: '固件版本',
            align: 'center',
            dataIndex: 'code'
          },
          {
            title: '所属产品',
            align: 'center',
            dataIndex: 'typeName'
          },
		  {
		    title: '签名方式',
		    align: 'center',
		    dataIndex: 'nodeType_dictText'
		  },
		  {
		    title: '创建时间',
		    align: 'center',
		    dataIndex: 'network_dictText'
		  },
          {
            title: '操作',
            dataIndex: 'action',
            align: 'center',
            scopedSlots: { customRender: 'action' },
          }
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
      }
    },
	methods: {
    handleTableChange(){},
    handleOk(e) {
      this.ModalText = 'The modal will be closed after two seconds'
      this.confirmLoading = true
      setTimeout(() => {
        this.visible = false
        this.confirmLoading = false
      }, 2000)
    },
    handleCancel(e) {
      console.log('Clicked cancel button')
      this.visible = false
    },
	  detailsClick() {
      this.visible = true
    },
	  handleDetail(record) {
	    this.$refs.PhysicalModelModal.edit(record)
	  },
	  handleDataRule(record) {
	    this.$refs.PhysicalModelModal.edit(record)
	  },
	  handlePhysicalModel(record) {
		
	    this.$refs.PhysicalModelModal.title = "物模型";
	    this.$refs.PhysicalModelModal.disableSubmit = false;
	    this.$refs.PhysicalModelModal.edit({status:'1',permsType:'1',route:true,'parentId':record.id,menuType:1});
	  },

	}
  }
</script>
<style scoped lang="less">
  @import '~@assets/less/common.less';
  .table{
  margin: 8px 0;
}
.equ {
  font-weight: bold;
  font-size: 18px;
  margin-bottom: 14px;
}
.deviceTitle {
    display: flex;
    justify-content: flex-start;
    p {
      margin-right: 20px;
    }
  }
.debugging {
  font-size: 16px;
  border-bottom: 1px solid #e8e8e8;
  padding-bottom: 13px;
  margin-bottom: 13px;
  padding-left: 16px;
}
.identification {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  margin-bottom: 24px;
  p {
    margin: 0 10px;
    color: rgb(46, 44, 44);
    &:nth-of-type(1) {
      i {
        background: red;
      }
    }
    &:nth-of-type(2) {
      i {
        background: #605596;
      }
    }
    &:nth-of-type(3) {
      i {
        background: rgb(85, 150, 94);
      }
    }
    i {
      display: inline-block;
      width: 10px;
      height: 10px;
      border-radius: 50%;
      margin: 0 10px;
    }
  }
  span {
    margin-left: 10px;
  }
}
.models {
  height: 700px;
  overflow-y: auto;
}
.identification {
  color: #00bf8a;
}
.police {
  overflow-y:auto;
    height:450px;
  .policeName {
    display: flex;
    justify-content: flex-start;
    align-items: center;
    span {
      font-size: 14px;
      color: #000;
      width: 100px;
      display: inline-block;
    }
  }
  .condition {
    .conditionHead {
      margin: 10px 0;
      display: flex;
      justify-content: flex-start;
      align-items: center;
      > p {
        font-size: 16px;
        font-weight: bold;
        margin: 0;
      }
      i {
        margin: 0 8px;
      }
      button {
      }
    }
    .trigger {
      .triggerList {
        background: #eeeaea;
        border-radius: 5px;
        padding-bottom: 10px;
        h2 {
          margin: 10px;
          font-weight: normal;
          font-size: 20px;
          padding-top: 10px;
          > a {
            margin-left: 15px;
            &:hover {
              cursor: pointer;
            }
          }
        }
        .select {
          margin: 0 10px;
          > div {
            margin-right: 15px;
          }
        }
        .triggerCol {
          display: flex;
          justify-content: flex-start;
          align-items: center;
          margin: 15px 10px;
          input {
            margin-right: 15px;
          }
          > div {
            margin-right: 15px;
          }
        }
      }
    }
    .addTrigger {
      margin: 10px 0;
    }
  }
}
.wrapTab {
  padding: 10px;
  border: 1px solid #f3eeee;
}
.attr {
  display: flex;
  justify-content: space-between;
  border-bottom: 1px solid #f3eeee;
  align-items: center;
  p {
    font-size: 16px;
    font-weight: bold;
  }
  .attrBut {
    > button {
      margin-left: 5px;
    }
  }
}
.modelBut {
  position: absolute;
  right: 0;
  > button {
    margin-left: 5px;
  }
}
.product {
  font-size: 16px;
  font-weight: bold;
}
.table {
  border: 1px solid #f3eeee;
  margin: 16px 0;
  .col {
    .colName {
      height: 50px;
      display: flex;
      justify-content: flex-start;
      align-items: center;
      border-top: 1px solid #f3eeee;
      .sign {
        background: #fafafa;
        display: inline-block;
        width: 100px;
        height: 100%;
        display: flex;
        align-items: center;
        padding-left: 12px;
      }
      code {
        padding-left: 12px;
        flex: 1;
      }
      span {
      }
    }
    .explain {
    }
  }
}
.authentication {
  font-size: 14px;
  margin-top: 16px;
}
.pagination {
  float: right;
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
            color: rgba(0, 0, 0, .45);
            font-size: 12px;
            line-height: 20px;
            margin-bottom: 4px;
          }
        }
  
      }
    }
  }
</style>