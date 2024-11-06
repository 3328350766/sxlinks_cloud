<template>
  <a-modal
    title="查看子设备"
    :width="800"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭"
  >
    <a-table
      ref="table"
      class="table"
      size="middle"
      bordered
      rowKey="deviceCode"
      :pagination="false"
      :columns="columns"
      :dataSource="dataSource"
      :loading="loading"
    >
      <span slot="action" slot-scope="text, record">
        <a-popconfirm title="确定删除吗?" @confirm="() => confirm(record)">
                <a>解绑</a>
        </a-popconfirm>     
      </span>
    </a-table>
    <a-pagination v-model="queryParam.pageNo" :total='total' show-less-items class="pagination"
        :page-size="Number(queryParam.pageSize)" :default-current="Number(queryParam.pageNo)"
        @change="changePagination" />
  </a-modal>
</template>

<script>
import pick from 'lodash.pick'
import { httpAction } from '@/api/manage'
import { duplicateCheck } from '@/api/api'
import JDictSelectTag from '@/components/dict/JDictSelectTag'
import { getlistFunction, getQueryById, getListProperty, deleteProperty,addFunction } from '@/api/product'
let validatorCodeTimer = null

export default {
  name: 'GatewayModal',
  components: { JDictSelectTag },
  data() {
    return {
      deviceCode:{},
      total:0,
      current:0,
      loading:false,
      layout: {
        labelCol: { span: 8 },
        wrapperCol: { span: 14 },
      },
      // 表头
      columns: [
        // {
        //   title: 'id',
        //   align: 'center',
        //   dataIndex: 'id',
        // },
        {
          title: '设备名称',
          align: 'left',
          dataIndex: 'deviceName',
        },

        // {
        //   title: '产品名称',
        //   align: 'center',
        //   dataIndex: 'productName',
        // },
        {
          title: '注册时间',
          align: 'center',
          dataIndex: 'createTime',
        },

        {
          title: '状态',
          align: 'center',
          dataIndex: 'activeStatus',
          customRender: (text) => text?'在线':'离线'
        },
        {
          title: '操作',
          dataIndex: 'action',
          align: 'center',
          scopedSlots: { customRender: 'action' },
        },
      ],
      dataSource: [],
      queryParam: {
        deviceCode:'',
        deviceName:'',
        pageNo:1,
        pageSize:10
      },
      title: '操作',
      visible: false,
      model: {},
      labelCol: {
        xs: { span: 24 },
        sm: { span: 5 },
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 },
      },
      confirmLoading: false,
      validatorRules: {
        code: [
          { required: true, message: '请输入网关编码' },
          {
            validator: (rule, value, callback) => {
              // 函数消抖的简单实现，防止一段时间内发送多次请求
              if (validatorCodeTimer) {
                // 停止上次开启的定时器
                clearTimeout(validatorCodeTimer)
              }
              validatorCodeTimer = setTimeout(() => {
                duplicateCheck({
                  tableName: 'sys_position',
                  fieldName: 'code',
                  fieldVal: value,
                  dataId: this.model.id,
                })
                  .then((res) => {
                    if (res.success) {
                      callback()
                    } else {
                      callback(res.message)
                    }
                  })
                  .catch(console.error)
              }, 300)
            },
          },
        ],
        name: [{ required: true, message: '请输入网关名称' }],
        postRank: [{ required: true, message: '请选择职级' }],
      },
      url: {
        add: '/productCenter/gateway/add',
        edit: '/productCenter/gateway/edit',
      },
      readOnly: false,
      selectedRowKeys: [],
      codeParameter:{
        deviceCode:null,
        subDeviceCode:null
      }
    }
  },
  created() {
  },
  methods: {
    showData(deviceCode){
      this.queryParam.deviceCode=deviceCode
      this.getData()
      
    },
    changePagination(e){
      this.dataSource.pageNo=e
      this.getData()
    },
    getData(){
      getQueryById('/productCenter/gateway/listSubDevice',this.queryParam).then((res)=>{
        if(res.success){
          this.dataSource=res.result.records
          this.total=res.result.total
        }
      })
    },
    onSelectChange(selectedRowKeys) {
      this.formData = new FormData()
      this.formData.append("deviceCode", this.deviceCode.deviceCode); //传入网关的code
      this.formData.append("subDeviceCode", selectedRowKeys[0]); //选择子设备的code
      // this.codeParameter.subDeviceCode=selectedRowKeys[0]
      // this.codeParameter.deviceCode=this.deviceCode.deviceCode
      this.selectedRowKeys = selectedRowKeys;
    },
    
    add() {
      this.edit({})
    },
    searchQuery(){},
    handleTableChange(){},
    edit(record) {
      this.model = Object.assign({}, record)
      this.visible = true
      if (record.id) {
        this.readOnly = true
      } else {
        this.readOnly = false
      }
    },
    close() {
      this.$emit('close')
      this.visible = false
    },
     // 解绑
     confirm(i) {
      let formData = new FormData()
      formData.append('deviceCode', this.queryParam.deviceCode)
      formData.append('subDeviceCode', i.deviceCode)
      addFunction(`/productCenter/gateway/unbind`, formData).then((res) => {
        if (res.success) {
          this.$message.info(res.message)
          this.getData()
        } else {
          this.$message.warn(res.message)
        }
      })
    },
    handleCancel() {
      this.close()
    },
  },
}
</script>

<style lang="less" scoped>
.table{
  margin-top: 8px;
}
.pagination{
  text-align: right;
  margin: 10px 0 0 0;
}
</style>
