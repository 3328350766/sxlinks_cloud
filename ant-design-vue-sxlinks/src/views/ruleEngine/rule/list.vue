<template>
  <a-card :bordered="false">

    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="getEnergyDataList">
        <a-row :gutter="24">

          <a-col :md="6" :sm="8">
            <a-form-item label="名称">
              <a-input placeholder="请输入名称" v-model="queryParam.name"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="getEnergyDataList" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
              <!-- searchReset -->
            </span>
          </a-col>

        </a-row>
      </a-form>
    </div>

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
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
          <a @click="functionEdit(record)">编辑</a>
          <a @click="sonModal(record)" style="margin:0 5px">添加转发子顶</a>
          <a-divider type="vertical"/>
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down"/></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
       <a-pagination
            v-model="current"
            :total="total"
            show-less-items
            class="pagination"
            :page-size="Number(queryParam.pageSize)"
            :default-current="Number(queryParam.pageNo)"
            @change="changePagination"
          />
    </div>
		<!--table块状结束-->
    <!-- 表单区域 -->
    <Product-modal ref="formanceModal"></Product-modal>
    <forward-modal ref="sonModal"></forward-modal>
  </a-card>
</template>

<script>
  import ProductModal from './modal'
  import forwardModal from './forward-modal'

  
  import JDictSelectTag from '@/components/dict/JDictSelectTag'
import { getQueryById, deleteProperty } from '@/api/product'

  export default {
    name: 'ProductList',
    components: {
      ProductModal,
      forwardModal,
      JDictSelectTag
    },
    data() {
      return {
        loading: true,
        current: 1,
        total: 0,
        queryParam: {
          name: '',
          pageNo: 1,
          pageSize: 10,
        },
        dataSource: [],
        description: '转发表管理页面',
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
		    align: 'center',
		    dataIndex: 'name'
		  },
          {
            title: '类型',
            align: 'center',
            dataIndex: 'type'
          },
          {
            title: '预警id',
            align: 'center',
            dataIndex: 'state'
          },
          {
            title: '时间',
            align: 'center',
            dataIndex: 'createTime',
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
          }
        ],
        url: {
          list: '/ruleEngine/dataForward/list',
          delete: '/ruleEngine/dataForward/delete',
          deleteBatch: '/ruleEngine/dataForward/deleteBatch',
          exportXlsUrl: '/ruleEngine/dataForward/exportXls',
          importExcelUrl: 'ruleEngine/dataForward/importExcel',
        },
      }
    },
    computed: {
      importExcelUrl: function () {
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
      }
    },
    mounted() {
      this.getEnergyDataList()
    },
    methods:{
      // 删除
    handleDelete(id) {
      deleteProperty(`/ruleEngine/rule/delete?id=${id}`).then((res) => {
        if (res.success) {
          this.$message.success(res.message)
          this.getEnergyDataList()
        } else {
          this.$message.warning(res.message)
        }
      })
    },
    sonModal(item){
        this.$refs.sonModal.title = '数据转发的属性列表'
        this.$refs.sonModal.queryParam.id=item.id
        this.$refs.sonModal.getEnergyDataList()
        this.$refs.sonModal.visible = true
    },
      // 新增
      handleAdd(){
        this.$refs.formanceModal.type = 'add'
        this.$refs.formanceModal.title = '新增'
        this.$refs.formanceModal.visible = true
      },
      // 编辑修改
      functionEdit(item) {
        let data = JSON.parse(JSON.stringify(item))
        this.$refs.formanceModal.visible = true
        this.$refs.formanceModal.title = '编辑'
        this.$refs.formanceModal.type = 'edit'
        this.$refs.formanceModal.model = data
        console.log(data);
      },
      // 添加转发子顶
      handleSon(item){},
      // 重置
      searchReset(){
        this.queryParam={
          name: '',
          pageNo: 1,
          pageSize: 10,
        }
      },
      // 列表分页
    changePagination(e) {
      this.queryParam.pageNo = Number(e)
      this.getEnergyDataList()
    },
      getEnergyDataList() {
        getQueryById('/ruleEngine/rule/list', this.queryParam).then((res) => {
          this.loading != this.loading
          if (res.success) {
            this.dataSource = res.result.records
            this.total = res.result.total
            this.loading = false
          }
        })
      },
      handleTableChange(){},
    }
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