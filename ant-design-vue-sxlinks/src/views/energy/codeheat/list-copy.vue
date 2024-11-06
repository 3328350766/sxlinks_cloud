<template>
  <a-card :bordered="false">
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
        @change="handleTableChange"
      >
        <span slot="action" slot-scope="text, record">
           <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a href="javascript:;">删除</a>
          </a-popconfirm>
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
    <!-- <Product-modal ref="modalForm" @ok="modalFormOk"></Product-modal> -->
  </a-card>
</template>

<script>
import ProductModal from './modal'
import PhysicalModelModal from './physicalModel'
import JDictSelectTag from '@/components/dict/JDictSelectTag'
import { getQueryById, deleteProperty } from '@/api/product'

export default {
  name: 'ProductList',
  mixins: [],
  components: {
    ProductModal,
    PhysicalModelModal,
  },
  data() {
    return {
      loading:true,
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
        {
          title: '#',
          dataIndex: '',
          key: 'rowIndex',
          width: 60,
          align: 'center',
          customRender: function (t, r, index) {
            return parseInt(index) + 1
          },
        },
        {
          title: '设备名称',
          align: 'center',
          dataIndex: 'deviceName',
        },
        {
          title: '设备编码',
          align: 'center',
          dataIndex: 'deviceCode',
        },
        {
          title: '回水温度',
          align: 'center',
          dataIndex: 'huishui',
        },
        {
          title: '进水温度',
          align: 'center',
          dataIndex: 'jinshui',
        },
        {
          title: '冷量',
          align: 'center',
          dataIndex: 'lengliang',
        },
        {
          title: '流量	',
          align: 'center',
          dataIndex: 'liuliang',
        },
        {
          title: '流速',
          align: 'center',
          dataIndex: 'liushu',
        },
        {
          title: '功耗',
          align: 'center',
          dataIndex: 'power',
        },
        {
          title: '热量',
          align: 'center',
          dataIndex: 'reliang',
        },
        // {
        //   title: '状态',
        //   align: 'center',
        //   dataIndex: 'state_dictText',
        //   // customRender: function(text) {
        //   //   if (text == 0) {
        //   //     return '禁用'
        //   //   } else if (text == 1) {
        //   //     return '启用'
        //   //   } else {
        //   //     return text
        //   //   }
        //   // }
        // },

        {
          title: '操作',
          dataIndex: 'action',
          align: 'center',
          scopedSlots: { customRender: 'action' },
        },
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
    },
  },
  mounted() {
    this.getEnergyDataList()
  },
  methods: {
    //删除
    handleDelete(id){
      deleteProperty('/energyCenter/energyData/delete?id'+id).then((res) => {
        this.loading!=this.loading
       if (res.success) {
          this.$message.success(res.message)
          this.getEnergyDataList()
        } else {
          this.$message.warning(res.message)
        }
        this.loading!=this.loading
      })
    },
    // 列表分页
    changePagination(e) {
      this.queryParam.pageNo = Number(e)
      this.getEnergyDataList()
    },
    // 重置
    searchReset(){
      this.queryParam={
        deviceName: '',
        deviceCode: '',
        pageNo: 1,
        pageSize: 10
      }
    },
    changePagination() {},
    getEnergyDataList() {
      getQueryById('/energyCenter/energyData/list', this.model).then((res) => {
        this.loading!=this.loading
        if (res.success) {
          this.dataSource = res.result.records
          this.total=res.result.total
          this.loading=false
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
<style scoped>
@import '~@assets/less/common.less' .app-list {
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
</style>