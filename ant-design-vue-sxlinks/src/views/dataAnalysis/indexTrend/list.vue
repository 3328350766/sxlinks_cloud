<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :md="6" :sm="10">
            <a-form-item label="选择产品">
              <a-select placeholder="请选择产品" :options="productList" @change="handleChange">
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <span style="float: left; overflow: hidden" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" style="margin-left: 8px">分析</a-button>
            </span>
          </a-col>
        </a-row>
      </a-form>
      <div class="foot">
        <div class="title">
          <p>
            <a-icon type="branches" />
            产品及设备状态统计
          </p>
        </div>
        <a-table :columns="columns" :data-source="tableData" :loading="tableLoading">
          <span slot="enableStatus" slot-scope="tags">
            <a-tag :color="tags ? 'geekblue' : 'green'">
              {{ tags ? '已激活' : '未激活' }}
            </a-tag>
          </span>
          <span slot="activeStatus" slot-scope="tags">
            <a-tag :color="tags ? '#87d068' : '#f50'">
              {{ tags ? '在线' : '离线' }}
            </a-tag>
          </span>
          <span slot="lastOnlineTime" slot-scope="time">
            {{ new Date(time).toLocaleString() }}
          </span>
        </a-table>
      </div>
    </div>
  </a-card>
</template>

<script>
import JDictSelectTag from '@/components/dict/JDictSelectTag'
import * as echarts from 'echarts'
import { getAction } from "@/api/product"
import { message } from "ant-design-vue"
const columns = [
  {
    dataIndex: 'deviceName',
    key: 'deviceName',
    title: '产品名称',
  },
  {
    title: '激活状态',
    dataIndex: 'enableStatus',
    key: 'enableStatus',
    scopedSlots: { customRender: 'enableStatus' },
    align: "center"
  },
  {
    title: '在线状态',
    dataIndex: 'activeStatus',
    key: 'activeStatus',
    scopedSlots: { customRender: 'activeStatus' },
    align: "center"
  },
  {
    title: '属性数量',
    dataIndex: 'propertyCount',
    key: 'propertyCount',
    align: "center"

  },
  {
    title: '属性激活数量',
    key: 'propertyEnableCount',
    dataIndex: 'propertyEnableCount',
    align: "center"

  },
  {
    title: '最新上报时间',
    key: 'lastOnlineTime',
    dataIndex: 'lastOnlineTime',
    align: "center",
    scopedSlots: { customRender: 'lastOnlineTime' },
  }
];
export default {
  name: 'ProductList',
  components: {
    JDictSelectTag,
  },
  data() {
    return {
      description: '产品表管理页面',
      // 表头
      columns,
      productList: [],
      selected: "",
      tableData: [],
      tableLoading: false
    }
  },
  computed: {
    importExcelUrl: function () {
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
    },
  },
  mounted() {
    getAction('/productCenter/product/queryAll').then(res => {
      console.log(res);
      this.productList = res.result.map(item => {
        return {
          label: item.productName,
          value: item.productCode
        }
      })
    })
  },

  methods: {
    searchQuery() {
      if (!this.selected) {
        message.error({
          content: '请选择产品',
          duration: 2,
          key: "product"
        })
        return
      }
      this.tableLoading = true
      getAction('/dataAnalysis/indexTrend/list', { productCode: this.selected }).then(res => {
        this.tableData = res.result.records
        this.tableLoading = false

      })
    },
    handleChange(e) {
      this.selected = e
    }
  },
}
</script>
<style scoped lang='less'>
@import '~@assets/less/common.less';

.title,
.foot {
  font-size: 18px;
  font-weight: bold;
  line-height: 45px;

  >p {
    margin: 0;

    i {}
  }
}

.foot {
  border: 1px solid #d9d9d9;

}

.title {
  border-bottom: 1px solid #d9d9d9;
  padding-left: 5px;

}

.charts {
  height: 350px;
  width: 100%;
}

.app-list {
  .meta-cardInfo {
    zoom: 1;
    margin-top: 16px;

    >div {
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