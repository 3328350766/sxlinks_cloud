<template>
      <a-modal
      v-model="visible"
      title="设置归类产品"
      @ok="handleOk"
      width="60%"
      height="100%"
      wrapClassName="full-modal"
    >
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
      <a-button @click="handleAdd" type="primary" icon="plus">新建</a-button>
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
    <BelongProductModal ref="modalForm" @callback="getDataList"></BelongProductModal>
    </a-modal>


 

</template>

<script>
import BelongProductModal from './modal'
// import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import JDictSelectTag from '@/components/dict/JDictSelectTag'
import { getQueryById, deleteProperty } from '@/api/product'

export default {
  name: 'BelongProductList',
  // mixins: [JeecgListMixin],
  components: {
    BelongProductModal,
    JDictSelectTag,
  },
  data() {
    return {
      visible:false,
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
          title: '产品名称',
          align: 'center',
          dataIndex: 'name',
        },
		{
		  title: '归类名称',
		  align: 'center',
		  dataIndex: 'belongName',
		},		
        
        {
          title: '状态',
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
        alarmId:null,
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
      description: '产品表管理页面',
      
      url: {

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
  },
  methods: {
    showAttribute(code){
      this.deviceCode=code
    },
      // 删除
    handleDelete(id) {
      deleteProperty(`/ruleEngine/dataBelong/deleteBelongProduct`,{propertyId:id}).then((res) => {
        if (res.success) {
          this.$message.success(res.message)
          this.getDataList()
        } else {
          this.$message.warning(res.message)
        }
      })
    },
    searchReset(){
      this.queryParam={
        name:'',
        pageNo:1,
        pageSize:10
      }
      this.getDataList()
    },
    changePagination(e){
      this.queryParam.pageNo=e
      this.getDataList()
    },
    searchQuery(){
      this.getDataList()
    },
    handleChange(){},
    // 新增
      handleAdd(){
        this.$refs.modalForm.model={}
        this.$refs.modalForm.type = 'add'
		this.$refs.modalForm.model.belongId=this.queryParam.belongId;
		this.$refs.modalForm.getProductList(); //加载产品列表
        this.$refs.modalForm.title = '新增'
        this.$refs.modalForm.visible = true
      },
       // 编辑修改
      functionEdit(item) {
        let data = JSON.parse(JSON.stringify(item))
        this.$refs.modalForm.getProductList(); //加载产品列表
        this.$refs.modalForm.visible = true
        this.$refs.modalForm.title = '编辑'
        this.$refs.modalForm.type = 'edit'
        this.$refs.modalForm.model = data
      },
     getDataList() {
        getQueryById('/ruleEngine/dataBelong/listBelongProduct', this.queryParam).then((res) => {
          this.loading != this.loading
          if (res.success) {
            this.dataSource = res.result.records
            this.total = res.result.total
            this.loading = false
          }
        })
      },


    
    showModal(item) {
      let data = JSON.parse(JSON.stringify(item))
      this.$refs.detailedModal.title = '查看'
      this.$refs.detailedModal.model = data
      this.$refs.detailedModal.visible = true
    },
    onSearch() {},
    callback(key) {
      console.log(key)
    },
    detailsClick() {
      this.visible = true
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
.full-modal {
  .ant-modal {
    max-width: 100%;
    top: 0;
    padding-bottom: 0;
    margin: 0;
  }
  .ant-modal-content {
    display: flex;
    flex-direction: column;
    height: calc(100vh);
  }
  .ant-modal-body {
    flex: 1;
  }
}
.pagination{
  text-align: right;
  margin: 10px 0 0 0;
}
</style>