<template>
  <a-modal
    :title="title"
    :width="1200"
    :visible="visible"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭"
  >
    <a-spin :spinning="confirmLoading">
      <div class="table-page-search-wrapper">
        <a-form layout="inline" @keyup.enter.native="getEnergyDataList">
          <a-row :gutter="24">
            <a-col :md="6" :sm="8">
              <a-form-item label="名称">
                <a-input placeholder="请输入名称" v-model="queryParam.name"></a-input>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="8">
              <span style="float: left; overflow: hidden" class="table-page-search-submitButtons">
                <a-button type="primary" @click="getEnergyDataList" icon="search">查询</a-button>
                <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
                <!-- searchReset -->
              </span>
            </a-col>
          </a-row>
        </a-form>
      </div>
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
          @change="handleTableChange"
        >
          <span slot="action" slot-scope="text, record">
            <a @click="functionEdit(record)">编辑</a>
            <a-divider type="vertical" />
            <a-dropdown>
              <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
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
      <forward-mode-add ref="forwardModeAdd"></forward-mode-add>
    </a-spin>
  </a-modal>
</template>

<script>
import pick from 'lodash.pick'
import { httpAction } from '@/api/manage'
import { duplicateCheck } from '@/api/api'
import JDictSelectTag from '@/components/dict/JDictSelectTag'
import { addProperty, editProperty, getQueryById,deleteProperty } from '@/api/product'
import forwardModeAdd from './forward-mode-add'
let validatorCodeTimer = null

export default {
  name: 'ProductModal',
  components: { JDictSelectTag,forwardModeAdd },
  data() {
    return {
      loading: false,
      current: 1,
      total: 0,
      queryParam: {
        id:'',
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
          },
        },
        {
          title: '名称',
          align: 'center',
          dataIndex: 'name',
        },
        {
          title: '条件类型',
          align: 'center',
          dataIndex: 'type',
        },
        {
          title: '数据值',
          align: 'center',
          dataIndex: 'dataValue',
        },
        {
          title: '状态',
          align: 'center',
          dataIndex: 'state',
        },
        {
          title: '数据条件',
          align: 'center',
          dataIndex: 'dataCondition',
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
        },
      ],

      type: 'add',
      title: '新增',
      visible: false,
      model: {
        code: '',
        createBy: '',
        createTime: '',
        description: '',
        modifyBy: '',
        modifyTime: '',
        name: '',
        state: '',
        type: '',
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
      validatorRules: {
        name: [{ required: true, message: '请输入名称' }],
        type: [{ required: true, message: '请选择转发类型' }],
        status: [{ required: true, message: '请选择状态' }],
        description: [{ required: true, message: '请输入描述' }],
      },
      url: {
        add: '/ruleEngine/condition/add',
        edit: '/ruleEngine/condition/edit',
      },
      readOnly: false,
    }
  },
  created() {},
  methods: {
      // 删除
    handleDelete(id) {
      deleteProperty(`/ruleEngine/rule/deleteItem?id=${id}`).then((res) => {
        if (res.success) {
          this.$message.success(res.message)
          this.getEnergyDataList()
        } else {
          this.$message.warning(res.message)
        }
      })
    },
    handleTableChange() {},
    // 重置
    searchReset() {
      this.queryParam = {
        name: '',
        pageNo: 1,
        pageSize: 10,
      }
    },
    
    // 获取数据
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
    // 新增
    handleAdd() {
      this.$refs.forwardModeAdd.type = 'add'
      this.$refs.forwardModeAdd.title = '新增'
      this.$refs.forwardModeAdd.visible = true
    },
    // 编辑修改
    functionEdit(item) {
      let data = JSON.parse(JSON.stringify(item))
      this.$refs.forwardModeAdd.visible = true
      this.$refs.forwardModeAdd.title = '编辑'
      this.$refs.forwardModeAdd.type = 'edit'
      this.$refs.forwardModeAdd.model = data
    },

    add() {
      this.edit({})
    },
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
      this.$refs.form.resetFields()
    },
    handleOk() {
      const that = this
      // 触发表单验证
      this.$refs.form.validate((valid) => {
        if (valid) {
          if (this.type == 'add') {
            addProperty('/ruleEngine/rule/add', this.model)
              .then((res) => {
                if (res.success) {
                  that.$message.success(res.message)
                  that.$parent.$parent.getEnergyDataList()
                  // that.$emit('ok')
                } else {
                  that.$message.warning(res.message)
                }
              })
              .finally(() => {
                that.confirmLoading = false
                that.close()
              })
          } else if (this.type == 'edit') {
            editProperty('/ruleEngine/rule/edit', this.model)
              .then((res) => {
                if (res.success) {
                  that.$message.success(res.message)
                  that.$parent.$parent.getEnergyDataList()
                  // that.$emit('ok')
                } else {
                  that.$message.warning(res.message)
                }
              })
              .finally(() => {
                that.confirmLoading = false
                that.close()
              })
          }
        } else {
          return false
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
</style>
