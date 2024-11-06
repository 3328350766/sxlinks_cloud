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
                  <a-input placeholder="请输入功能名称" v-model="model.name" :read-only="readOnly" />
                </a-form-model-item>
                <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="identifier" required label="属性编码">
                   <a-input placeholder="请输入功能编码" v-model="model.identifier" :read-only="readOnly" />
                 </a-form-model-item>
              </div>
              <div class="col-2">
                <a-form-model-item
                  :labelCol="labelCol"
                  :wrapperCol="wrapperCol"
                  prop="unitName"
                  label="单位名称"
                >
                  <a-input placeholder="单位名称" v-model="model.unitName" :read-only="readOnly" />
                </a-form-model-item>
      		  <a-form-model-item>
      		  
      		  </a-form-model-item>
      <!--          <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="funcType" label="物模型类型">
                  <a-input placeholder="请输入物模型类型" v-model="model.funcType" :read-only="readOnly" />
                </a-form-model-item> -->
              </div>
              <div class="col-2">
                <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="dataType" required label="数据类型">
                  <j-dict-select-tag
                    placeholder="请选择类型"
                    dictCode="data_type"
                    v-model="model.dataType"
                  />
                </a-form-model-item>
                <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="描述">
                    <a-input placeholder="请输入描述" v-model="model.funcDesc" :read-only="readOnly" />
                  </a-form-model-item>
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
import { addFunction, editProperty } from '@/api/product'

let validatorCodeTimer = null

export default {
  name: 'PhysicalFuncEdit',
  components: { JDictSelectTag },
  data() {
    return {
      type: 'add',
      title: '新增',
      visible: false,
      model: {
        dataType: null,
        funcDesc: null,
        unitName: null,
        funcType: null,
				deviceCode: null,
        productCode: null,
        name: null,

        async: 0,
        attr: '',
        createBy: 0,
        createTime: '',
        dataDefine: '',
        dataType: '',
        delFlag: 0,
        eventType: '',
        identifier: '',
        inputParam: '',
        outputParam: '',
        status: 0,
        unit: '',
        updateTime: '',
        wrType: 0,
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
        code: [
          { required: true, message: '请输入联动编码' },
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
        dataType: [{ required: true, message: '请输入数据类型' }],
        funcDesc: [{ required: false, message: '请输入描述' }],
        unitName: [{ required: false, message: '请输入计量单位名称' }],
        funcType: [{ required: false, message: '请输入物模型类型' }],
        name: [{ required: true, message: '请输入名称' }],
      },
      url: {
        add: '/productCenter/device/addFunction',
        edit: '/productCenter/device/editFunction',
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
          that.confirmLoading = true
          if (this.type == 'add') {
            addFunction('/productCenter/device/addFunction', this.model)
              .then((res) => {
                if (res.success) {
                  that.$message.success(res.message)
                  that.$parent.$parent.getFunctionData()
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
            editProperty('/productCenter/device/editFunction', this.model)
              .then((res) => {
                if (res.success) {
                  that.$message.success(res.message)
                  that.$parent.$parent.getFunctionData()
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
      this.model = {
        dataType: null,
        funcDesc: null,
        unitName: null,
        funcType: null,
				deviceCode: null,
        productCode: null,
        name: null,

        async: 0,
        attr: '',
        createBy: 0,
        createTime: '',
        dataDefine: '',
        dataType: '',
        delFlag: 0,
        eventType: '',
        identifier: '',
        inputParam: '',
        outputParam: '',
        status: 0,
        unit: '',
        updateTime: '',
        wrType: 0,
      }
      this.close()
    },
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
