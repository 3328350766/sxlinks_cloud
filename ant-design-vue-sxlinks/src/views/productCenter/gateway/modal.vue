<template>
  <a-modal
    title="绑定子设备"
    :width="800"
    :visible="visible"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭"
  >
    <!-- <a-form layout="inline" @keyup.enter.native="searchQuery" v-bind="layout">
      <a-row :gutter="24">
        <a-col :md="8">
          <a-form-item label="设备ID">
            <a-input placeholder="请输入设备ID" v-model="queryParam.code" style="width: 150px"></a-input>
          </a-form-item>
        </a-col>
        <a-col :md="8">
          <a-form-item label="设备名称">
            <a-input placeholder="请输入设备名称" v-model="queryParam.code" style="width: 150px"></a-input>
          </a-form-item>
        </a-col>
        <a-col :md="8">
          <span style="float: right; overflow: hidden" class="table-page-search-submitButtons">
            <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
            <a-button @click="searchQuery" icon="search">重置</a-button>
          </span>
        </a-col>
      </a-row>
    </a-form> -->
    <a-table
      ref="table"
      class="table"
      size="middle"
      bordered
      rowKey="deviceCode"
      :columns="columns"
      :dataSource="dataSource"
      :loading="loading"
       :row-selection="{ selectedRowKeys: selectedRowKeys, onChange: onSelectChange }"
      @change="handleTableChange"
    >
      <span slot="action" slot-scope="text, record">
        <a @click="handleEdit(record)">编辑</a>

        <a-divider type="vertical" />
        <a-dropdown>
          <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
          <a-menu slot="overlay">
            <a-menu-item>
              <a href="javascript:;" @click="handleDetail(record)">详情</a>
            </a-menu-item>
            <a-menu-item>
              <a href="javascript:;" @click="handlePhysicalModel(record)">物模型</a>
            </a-menu-item>
            <a-menu-item>
              <a href="javascript:;" @click="handleAlaram(record)">告警设置</a>
            </a-menu-item>
            <a-menu-item>
              <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                <a>删除</a>
              </a-popconfirm>
            </a-menu-item>
          </a-menu>
        </a-dropdown>
      </span>
    </a-table>
    <a-pagination v-model="current" :total="total" show-less-items class="pagination" />
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
          title: 'id',
          align: 'center',
          dataIndex: 'id',
        },
        {
          title: '设备名称',
          align: 'center',
          dataIndex: 'deviceName',
        },

        {
          title: '产品名称',
          align: 'center',
          dataIndex: 'productName',
        },
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
        }
      ],
      dataSource: [],
      queryParam: {},
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
    onSelectChange(selectedRowKeys) {
      this.formData = new FormData()
      this.formData.append("deviceCode", this.deviceCode.deviceCode); //传入网关的code
      this.formData.append("subDeviceCode", selectedRowKeys[0]); //选择子设备的code
      // this.codeParameter.subDeviceCode=selectedRowKeys[0]
      // this.codeParameter.deviceCode=this.deviceCode.deviceCode
      this.selectedRowKeys = selectedRowKeys;
    },
    getList(){
       getlistFunction(`/productCenter/gateway/queryAllSubDeviceNoBind`).then((res) => {
          if (res.success) {
            this.dataSource=res.result
          }
        })
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
    handleOk() {
      const that = this
      if(!this.formData){
        that.$message.warning('请绑定子设备')
        return false
      }
      addFunction('/productCenter/gateway/bind',this.formData).then((res) => {
          if (res.success) {
            that.$message.success(res.message)
            that.$parent.$parent.searchQuery()
            that.close()
          }else that.$message.warning(res.message)
      })

      // 触发表单验证
      // this.$refs.form.validate((valid) => {
      //   if (valid) {
      //     that.confirmLoading = true
      //     let httpurl = ''
      //     let method = ''
      //     if (!this.model.id) {
      //       httpurl += this.url.add
      //       method = 'post'
      //     } else {
      //       httpurl += this.url.edit
      //       method = 'put'
      //     }

      //     httpAction(httpurl, this.model, method)
      //       .then((res) => {
      //         if (res.success) {
      //           that.$message.success(res.message)
      //           that.$emit('ok')
      //         } else {
      //           that.$message.warning(res.message)
      //         }
      //       })
      //       .finally(() => {
      //         that.confirmLoading = false
      //         that.close()
      //       })
      //   } else {
      //     return false
      //   }
      // })
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
