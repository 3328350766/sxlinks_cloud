<template>
  <a-card :bordered="false">

    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :md="6" :sm="8">
            <a-form-item label="分组名称" >
              <a-input placeholder="请输入分组名称" v-model="queryParam.name"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="handleAdds" icon="plus">新建分组</a-button>   
			  &nbsp;&nbsp;
			<a-button type="primary" @click="searchQuery" icon="search">搜索</a-button>
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
        :dataSource="dataSources"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange">

        <span slot="action" slot-scope="text, record">
          <a @click="detailed(record)" > 设备绑定 </a>
          <a @click="editClick(record)" style="margin:0 10px">编辑</a>
          <a @click="delClick(record.id)">删除</a>
        </span>

      </a-table>
    </div>
    <!-- table区域-end -->

		<!--table块状开始-->
    <!--table块状结束-->
    <!-- 表单区域 -->
    <DivideEdit ref="DivideEdit" @ok="modalFormOk"></DivideEdit>
    <DivideDeviceList ref="DivideDeviceList"></DivideDeviceList>
    
  </a-card>
</template>

<script>
  import DivideEdit from './edit'
  import DivideDeviceList from './divide_device_list'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import JDictSelectTag from '@/components/dict/JDictSelectTag'
  import { getlistFunction, getQueryById, getListProperty, deleteProperty } from '@/api/product'

  export default {
    name: 'DivideList',
    mixins: [JeecgListMixin],
    components: {
      DivideEdit,
	  DivideDeviceList,
      JDictSelectTag
    },
    data() {
      return {
        description: '分组表管理页面',
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
		    title: '名称',
		    align: 'left',
		    dataIndex: 'name'
		  },
		  {
		    title: '标识',
		    align: 'left',
		    dataIndex: 'code'
		  },
          
          {
            title: '描述',
            align: 'left',
            dataIndex: 'description'
          },

          {
            title: '操作',
            dataIndex: 'action',
            align: 'center',
            scopedSlots: { customRender: 'action' },
          }
        ],
        dataSources:[],
        url: {
          list: '/productCenter/divide/list',
          delete: '/productCenter/divide/delete',
          deleteBatch: '/productCenter/divide/deleteBatch',
          exportXlsUrl: '/productCenter/divide/exportXls',
          importExcelUrl: 'productCenter/divide/importExcel',
        },
        queryParam: {
            name: null,
            pageNo: 1,
            pageSize: 8,
          },
      }
    },
    computed: {
      importExcelUrl: function () {
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
      }
    },
    mounted(){
      this.searchQuery()
    },
	methods: {
    detailed(item){
      //  getQueryById(`/productCenter/divide/queryById?id=${item.id}`).then((res) => {
      //   if (res.success) {
      //     console.log(res)
      //   }
      // })
      this.$refs.DivideDeviceList.title = "分组绑定设备管理";
      this.$refs.DivideDeviceList.visible = true;
      this.$refs.DivideDeviceList.searchQuery()
    },
        // 删除list数据
    delClick(id){
      deleteProperty(`/productCenter/divide/delete?id=${id}`).then((res) => {
        if(res.success){
           this.$message.success(res.message)
           this.searchQuery()
        } else {
          this.$message.warning(res.message)
        }
      })
    },
    handleAdds(){
      this.$refs.DivideEdit.title = "新增";
      this.$refs.DivideEdit.type = "add";
      this.$refs.DivideEdit.visible = true;
    },
    // list编辑
    editClick(item){
      let data = JSON.parse(JSON.stringify(item))
      this.$refs.DivideEdit.visible = true
      this.$refs.DivideEdit.title = '编辑'
      this.$refs.DivideEdit.type = 'edit'
      this.$refs.DivideEdit.model = data
    },
    // 获取list数据
    searchQuery() {
      getlistFunction(`/productCenter/divide/list`, this.queryParam).then((res) => {
          if (res.success) {
            this.dataSources = res.result.records.filter(v=>v.fid==0)
            // console.log(res.result.records);
            // this.total = res.result.total
          }
        })
      },
    },
    editClicks(item) {
      debugger
      getQueryById(`/productCenter/divide/queryById?id=${item.id}`).then((res) => {
        if (res.success) {
          // this.productDetails = res.result
          console.log(res)
        }
      })
      console.log(item);
      this.$refs.DivideDeviceList.title = "查看功能";
      this.$refs.DivideDeviceList.visible = true;
    },
	 
  }
</script>
<style scoped lang='less'>
  @import '~@assets/less/common.less';
  
  
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