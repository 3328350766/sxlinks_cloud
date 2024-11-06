<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :md="6" :sm="8">
            <a-form-item label="设备编码">
              <a-input placeholder="请输入设备编码" v-model="queryParam.deviceCode"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="设备名称">
              <a-input placeholder="请输入设备名称" v-model="queryParam.deviceName"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <span style="float: left; overflow: hidden" class="table-page-search-submitButtons">
              <a-button type="primary" @click="getList" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>

    <!-- 操作按钮区域 -->
    <!-- <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('产品表')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel">
            <a-icon type="delete"/>
            删除
          </a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作
          <a-icon type="down"/>
        </a-button>
      </a-dropdown>
    </div> -->

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
            :pagination="false"
            @change="handleTableChange"
          >
            <!-- <span slot="action" slot-scope="text, record">
              <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                <a href="javascript:;">删除</a>
              </a-popconfirm>
            </span> -->
          </a-table>
          
		  <a-pagination v-model="queryParam.pageNo" :total='total' show-less-items class="pagination"
		    :page-size="Number(queryParam.pageSize)" :default-current="Number(queryParam.pageNo)"
		    @change="changePagination" />
		</div>
    <!--table块状结束-->
    <!-- 表单区域 -->
    <!-- <Product-modal ref="modalForm" @ok="modalFormOk"></Product-modal> -->
  </a-card>
</template>

<script>
import ProductModal from './modal'
import { getQueryById, deleteProperty } from '@/api/product'

export default {
  name: 'ProductList',
  mixins: [],
  components: {
    ProductModal,
  },
  data() {
    return {
      loading: true,
      current: 1,
      total: 0,
      queryParam: {
        deviceName: '',
        deviceCode: '',
        pageNo: 1,
        pageSize: 10,
      },
      dataSource: [],
      description: '产品表管理页面',
      // 表头
      columns: [
        // {
        //   title: '#',
        //   dataIndex: '',
        //   key: 'rowIndex',
        //   width: 60,
        //   align: 'center',
        //   customRender: function (t, r, index) {
        //     return parseInt(index) + 1
        //   },
        // },
        // {
        //   title: '预警名称',
        //   align: 'center',
        //   dataIndex: 'alarmName',
        // },
        {
          title: '设备名称',
          align: 'center',
          dataIndex: 'deviceName',
        },
		{
		  title: '属性',
		  align: 'center',
		  dataIndex: 'propertyName',
		},
        {
          title: '内容',
          align: 'left',
          dataIndex: 'content',
        },
		{
		  title: '消警模式',
		  align: 'left',
		  dataIndex: 'cancelMode',
		  customRender: function (text) {
		    let map={
		      '0':'自动',
		      '1':'手动',
		    }
		      return map[text]
		    }
		},
		{
		  title: '持续时长(秒)',
		  align: 'left',
		  dataIndex: 'duration',
		},
        // {
        //   title: '设备位置',
        //   align: 'center',
        //   dataIndex: 'deviceLocation',
        // },
        {
          title: '创建时间',
          align: 'center',
          dataIndex: 'createTime',
        },
		{
          title: '结束时间',
          align: 'center',
          dataIndex: 'modifyTime',
        },
        // {
        //   title: '操作',
        //   dataIndex: 'action',
        //   align: 'center',
        //   scopedSlots: { customRender: 'action' },
        // },
      ],
      
    }
  },
  computed: {
    importExcelUrl: function () {
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
    },
  },
  mounted() {
    this.getList()
  },
  methods: {
    //删除
    handleDelete(id) {
      deleteProperty('/dataCenter/alarmDataHistory/delete?id' + id).then((res) => {
        this.loading != this.loading
        if (res.success) {
          this.$message.success(res.message)
          this.getList()
        } else {
          this.$message.warning(res.message)
        }
        this.loading != this.loading
      })
    },
    // 列表分页
    changePagination(e) {
      this.queryParam.pageNo = Number(e)
      this.getList()
	  
    },
    // 重置
    searchReset() {
      this.queryParam = {
        deviceName: '',
        deviceCode: '',
        pageNo: 1,
        pageSize: 10,
      }
    },

    getList() {
      getQueryById('/dataCenter/alarmDataHistory/list', this.queryParam).then((res) => {
        this.loading != this.loading
        if (res.success) {
          this.dataSource = res.result.records
          this.total = res.result.total
          this.loading = false
        }
      })
    },
    handleTableChange() {},
    handleDetail(record) {
      this.$refs.PhysicalModelModal.edit(record)
    },
    handleDataRule(record) {
      this.$refs.PhysicalModelModal.edit(record)
    },
    handlePhysicalModel(record) {
      this.$refs.PhysicalModelModal.title = '物模型'
      this.$refs.PhysicalModelModal.disableSubmit = false
      this.$refs.PhysicalModelModal.edit({ status: '1', permsType: '1', route: true, parentId: record.id, menuType: 1 })
    },
  },
}
</script>
<style scoped lang='less'>
@import '~@assets/less/common.less';
.col-2 {
  display: flex;
  justify-content: flex-start;
  align-items: center;

  > div {
    margin-bottom: 10px;
    flex: 1;
    &:nth-of-type(2) {
      margin-left: 10px;
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