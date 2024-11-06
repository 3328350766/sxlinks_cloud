<template>
  <a-modal
    :title="title"
    :width="800"
    :visible="visible"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭"
  >
    <a-spin :spinning="confirmLoading">
      <div>
        <a-tabs default-active-key="1" @change="tabCallback">
          <a-tab-pane key="1" tab="上报数据">
            <div>
              <a-table
                ref="table"
                size="middle"
                bordered
                rowKey="id"
                :pagination="false"
                :columns="columns"
                :dataSource="dataSource"
                @change="handleTableChange"
              >
                <!-- <span slot="action" slot-scope="text, record">
              <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                <a href="javascript:;">删除</a>
              </a-popconfirm>
            </span> -->
              </a-table>
              <a-pagination
                v-model="queryParam.pageNo"
                :total="total"
                show-less-items
                class="pagination"
                :page-size="Number(queryParam.limit)"
                :default-current="Number(queryParam.pageNo)"
                @change="changePagination"
              /></div
          ></a-tab-pane>
          <a-tab-pane key="2" tab="设置记录" force-render
            ><div>
              <a-table
                ref="table"
                size="middle"
                bordered
                rowKey="id"
                :columns="columns"
                :dataSource="dataSource"
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
                :page-size="Number(queryParam.limit)"
                :default-current="Number(queryParam.pageNo)"
                @change="changePagination"
              /></div
          ></a-tab-pane>
        </a-tabs>
      </div>
    </a-spin>
  </a-modal>
</template>

<script>
import pick from 'lodash.pick'
import { httpAction } from '@/api/manage'
import { duplicateCheck } from '@/api/api'
import JDictSelectTag from '@/components/dict/JDictSelectTag'
import { addFunction, editProperty } from '@/api/product'


let validatorCodeTimer = null

export default {
  name: 'DevicePropertyDataList',
  components: { JDictSelectTag },
  data() {
    return {
      deviceInfo: {},
      type: 'add',
      title: '新增',
      visible: false,
      model: {
        dataType: null,
        funcType: null,
        productCode: null,
        deviceCode: null,
        request: null,
        timestamp: null,
        identifier: '',
      },
      labelCol: {
        xs: { span: 24 },
        sm: { span: 5 },
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 },
      },
      confirmLoading: false,
      loading: true,
      current: 1,
      total: 0,
      dataSource: [],

      apiUrl: '/productCenter/device/queryPropertyDataHistoryData',
      queryParam: {
        deviceCode: '',
        funcType: '',
        productCode: '',
        identifier: '',
        pageNo: 1,
        limit: 8,
        paramData: {
          deviceCode: '',
          funcType: '',
          identifier: '',
          productCode: '',
        },
      },
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
          title: '值',
          align: 'center',
          dataIndex: 'request',
        },
        {
          title: '时间',
          align: 'center',
          dataIndex: 'timestamp',
        },
        {
          title: '操作',
          dataIndex: 'action',
          align: 'center',
          scopedSlots: { customRender: 'action' },
        },
      ],
      readOnly: false,
    }
  },
  mounted() {},

  methods: {
    getDeviceInfo(item) {
      console.log('getDeviceInfo', item)
      this.deviceInfo = item
      this.searchQuery()
    },
    tabCallback(key) {
      const url = {
        1: '/productCenter/device/queryPropertyDataHistoryData',
        2: '/productCenter/device/queryPropertySetHistoryData',
      }
      this.apiUrl = url[key]
      this.queryParam.pageNo = 1
      this.searchQuery()
    },
    // 列表分页
    changePagination(e) {
      console.log(e)
      this.queryParam.pageNo = Number(e)
      this.searchQuery()
    },
    // 确认
    handleOk() {
      this.visible = false
    },
    // 取消
    handleCancel() {
      this.visible = false
    },
    close() {
      this.$emit('close')
      this.visible = false
      this.$refs.form.resetFields()
    },
    // 获取list数据
    searchQuery() {
      this.queryParam.paramData.deviceCode = this.deviceInfo.deviceCode
      this.queryParam.paramData.productCode = this.deviceInfo.productCode
      this.queryParam.paramData.identifier = this.deviceInfo.identifier
      this.queryParam.paramData.funcType = 'PROP'
      addFunction(this.apiUrl, this.queryParam).then((res) => {
        if (res.success) {
          this.dataSource = res.data.resultData
          this.total = res.data.total
        }
      })
    },
    handleTableChange() {},
  },

  created() {
    // this.dataRefreh() //页面刷不加载
  },
  destroyed() {
    // 在页面销毁后，清除计时器
    // this.clear()
  },
}
</script>

<style lang="less" scoped>
.col-2 {
  display: flex;
  justify-items: flex-start;
  align-items: center;
  > .ant-row {
    flex: 1;
  }
}
</style>
