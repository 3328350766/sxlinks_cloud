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
      <a-form-model ref="form" :model="model" :rules="validatorRules">
        <div class="col-2">
          <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="name" required label="名称">
            <a-input placeholder="请输入名称" v-model="model.name" :read-only="readOnly" />
          </a-form-model-item>
          <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="type" required label="数据条件">
            <j-dict-select-tag placeholder="请选择数据条件" dictCode="data_condition" v-model="model.dataCondition" />
          </a-form-model-item>
        </div>
        <div class="col-2">
          <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="status" required label="状态">
            <j-dict-select-tag placeholder="请选择状态" dictCode="status" v-model="model.status" />
          </a-form-model-item>
          <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="name" required label="数据值	">
            <a-input placeholder="请输入数据值" v-model="model.dataValue" :read-only="readOnly" />
          </a-form-model-item>
        </div>
        <div class="col-2">
          <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="name" required label="条件类型	">
            <a-input placeholder="请输入条件类型	" v-model="model.type" :read-only="readOnly" />
          </a-form-model-item>
          <a-form-model-item></a-form-model-item>
        </div>
      </a-form-model>
    </a-spin>
  </a-modal>
</template>

<script>
import pick from 'lodash.pick'
import { httpAction } from '@/api/manage'
import { duplicateCheck } from '@/api/api'
import JDictSelectTag from '@/components/dict/JDictSelectTag'
import { addProperty, editProperty } from '@/api/product'

let validatorCodeTimer = null

export default {
  name: 'ProductModal',
  components: { JDictSelectTag },
  data() {
    return {
      type: 'add',
      title: '新增',
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
        name: [{ required: true, message: '请输入名称' }],
        dataCondition: [{ required: true, message: '请选择数据条件' }],
        status: [{ required: true, message: '请选择状态' }],
        dataValue: [{ required: true, message: '请输入数据值' }],
        type: [{ required: true, message: '请选择条件类型' }],
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
            addProperty('/ruleEngine/rule/addItem', this.model)
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
            editProperty('/ruleEngine/rule/editItem', this.model)
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
</style>
