<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :md="6" :sm="8">
            <a-form-item label="名称">
              <a-input placeholder="请输入名称" v-model="queryParam.name"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="8" :sm="10">
            <a-form-model-item
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              prop="type"
              label="类型"
            >
              <j-dict-select-tag
                placeholder="请选择转发类型"
                dictCode="dataForward_type"
                v-model="queryParam.type"
              />
            </a-form-model-item>
          </a-col>
          
          <a-col :md="4" :sm="6">
            <span style="float: left; overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">创建转发</a-button>
    </div>

    <div class="app-list">
      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        :columns="columnsList"
        :dataSource="dataSource"
        :pagination="false"
        :loading="loading"
        @change="handleTableChange">

        <span slot="action" slot-scope="text, record">
          <a @click="showModal(record)" style="margin-right:8px">设置转发子项</a>
		  <a-divider type="vertical"/>
          <a @click="functionEdit(record)">编辑</a>
          <a-divider type="vertical"/>
          <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
            </a-popconfirm>
        </span>
      </a-table>
      <a-pagination v-model="queryParam.pageNo" :total='total' show-less-items class="pagination"
        :page-size="Number(queryParam.pageSize)" :default-current="Number(queryParam.pageNo)"
        @change="changePagination" />
    </div>
    
    <!--table块状结束-->
    <!-- 表单区域 -->
    <Product-modal ref="modalForm"></Product-modal>
    <son-modal ref="detailedModal"></son-modal>
    <!--开始-->
  </a-card>
</template>

<script>
import ProductModal from './modal'
import sonModal from './son/list'

// import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import JDictSelectTag from '@/components/dict/JDictSelectTag'
import { getQueryById, deleteProperty } from '@/api/product'

export default {
  name: 'ProductList',
  // mixins: [JeecgListMixin],
  components: {
    ProductModal,
    JDictSelectTag,
    sonModal
  },
  data() {
    return {
      labelCol: {
        xs: { span: 24 },
        sm: { span: 5 },
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 },
      },
      columnsList:[
        {
          title: '名称',
          align: 'center',
          dataIndex: 'name',
        },
		{
		  title: '类型',
		  align: 'center',
		  dataIndex: 'type',
		  customRender: function (text) {
		    let map={
		      'http':'http',
		    }
		      return map[text]
		    }
		},
		{
		  title: '延时间隔(秒)',
		  align: 'center',
		  dataIndex: 'sleepTime',
		},
		
        
        {
          title: '时间',
          align: 'center',
          dataIndex: 'createTime',
        },
        {
          title: '操作状态',
          align: 'center',
          dataIndex: 'state',
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
      queryParam:{
        type:undefined,
        name:'',
        pageNo:1,
        pageSize:10
      },
      dataSource:[],
      triggerList: [
        {
          name: '触发器1',
          list: [
            {
              value: '10',
            },
          ],
        },
      ],
      current: 2,
      description: '转发设置表管理页面',
      
      url: {
        list: '/ruleEngine/dataForward/list',
        delete: '/ruleEngine/dataForward/delete',
        deleteBatch: '/ruleEngine/dataForward/deleteBatch',
        exportXlsUrl: '/ruleEngine/dataForward/exportXls',
        importExcelUrl: 'ruleEngine/dataForward/importExcel',
      },
      visible: false,
      confirmLoading: false,
      policevisible: false,
      policeconfirmLoading: false,
    }
  },
  computed: {
    importExcelUrl: function () {
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
    },
  },
  mounted(){
    this.getEnergyDataList()
  },
  methods: {
      // 删除
    handleDelete(id) {
      deleteProperty(`/ruleEngine/dataForward/delete?id=${id}`).then((res) => {
        if (res.success) {
          this.$message.success(res.message)
          this.getEnergyDataList()
        } else {
          this.$message.warning(res.message)
        }
      })
    },
    searchReset(){
      this.queryParam={
        type:undefined,
        name:'',
        pageNo:1,
        pageSize:10
      }
      this.getEnergyDataList()
    },
    changePagination(e){
      this.queryParam.pageNo=e
      this.getEnergyDataList()
    },
    searchQuery(){
      this.getEnergyDataList()
    },
    handleChange(){},
    // 新增
      handleAdd(){
        this.$refs.modalForm.type = 'add'
        this.$refs.modalForm.title = '新增'
        this.$refs.modalForm.visible = true
        this.$refs.modalForm.model = {}
      },
       // 编辑修改
      functionEdit(item) {
        let data = JSON.parse(JSON.stringify(item))
        this.$refs.modalForm.visible = true
        this.$refs.modalForm.title = '编辑'
        this.$refs.modalForm.type = 'edit'
        this.$refs.modalForm.model = data
      },
     getEnergyDataList() {
        getQueryById('/ruleEngine/dataForward/list', this.queryParam).then((res) => {
          this.loading != this.loading
          if (res.success) {
            this.dataSource = res.result.records
            this.total = res.result.total
            this.loading = false
          }
        })
      },


    identificationDelete(index, index2) {
      this.triggerList[index].list.splice(index, 1)
    },
    addidentification(index) {
      this.triggerList[index].list.push({ value: '10' })
    },
    showModal(item) {
      this.$refs.detailedModal.queryParam.dataForwardId = item.id
      this.$refs.detailedModal.getEnergyDataList()
      this.$refs.detailedModal.showAttribute(item.sourceDeviceCode)
      this.$refs.detailedModal.visible = true
    },
    onSearch() {},
    callback(key) {
      console.log(key)
    },
    detailsClick() {
      this.visible = true
    },
    // 报警
    policehandleOk(e) {
      this.policeconfirmLoading = true
      setTimeout(() => {
        this.policevisible = true
        this.policeconfirmLoading = false
      }, 2000)
    },
    policehandleCancel(e) {
      this.policevisible = false
    },

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

  },
}
</script>
<style scoped lang="less">
@import '~@assets/less/common.less';
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
.meta-cardInfos {
  display: flex;
  flex-direction: column;
  .title {
    width: 100%;
    display: flex;
    justify-content: flex-start;
    align-items: flex-start;
    padding-left: 20px;
    box-sizing: border-box;
    .right {
      flex: 1;
      margin-left: 14px;
      p {
        font-size: 16px;
        &:nth-of-type(1) {
          font-weight: bold;
          margin-bottom: 4px;
        }
        &:nth-of-type(2) {
        }
      }
    }
  }
  .contant {
    display: flex;
    justify-content: flex-start;
    align-items: center;
    .list {
      flex: 1;
      p {
        text-align: center;
        font-size: 16px;
      }
      span {
        display: block;
        text-align: center;
      }
      code {
        display: block;
        text-align: center;
        font-weight: bold;
      }
      i {
        display: inline-block;
        width: 6px;
        height: 6px;
        border-radius: 50%;
        background: #52c41a;
        margin-right: 3px;
      }
      .coler {
        background: red;
      }
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
.pagination{
  text-align: right;
  margin: 10px 0 0 0;
}
</style>