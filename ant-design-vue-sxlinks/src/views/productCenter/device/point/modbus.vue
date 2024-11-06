<template>
  <a-modal
    :title="title"
    :width="800"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭"
  >
    <a-spin :spinning="confirmLoading">
      <a-form-model ref="form" :model="model" :rules="validatorRules">
            <div class="col-2">
              <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="address" required label="设备站点">
                <a-input placeholder="请输入设备站点" v-model="model.address" :read-only="readOnly" style="width:250px"/>
              </a-form-model-item>
              <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="dataType" required label="数据类型">
                <j-dict-select-tag
                  placeholder="请选择类型"
                  dictCode="data_type"
                  v-model="model.dataType"
                />
              </a-form-model-item>
            </div>
            <div class="col-2">
              <a-form-model-item
                :labelCol="labelCol"
                :wrapperCol="wrapperCol"
                required
                prop="code"
                label="功能码"
              >
                <a-input placeholder="请输入功能码" v-model="model.code" :read-only="readOnly" style="width:250px"/>
              </a-form-model-item>
      
              <a-form-model-item
                :labelCol="labelCol"
                :wrapperCol="wrapperCol"
                required
                prop="startRegister"
                label="寄存器地址"
              >
                <a-input placeholder="请输入寄存器地址" v-model="model.startRegister" :read-only="readOnly"  style="width:250px"/>
              </a-form-model-item>
            </div>
            <div class="col-2">
              <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="byteSort" required label="字节顺序">
                <j-dict-select-tag
                  placeholder="请选择字节顺序"
                  dictCode="byte_sort"
                  v-model="model.byteSort"
                />
              </a-form-model-item>
              <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="state" required label="操作状态">
                <j-dict-select-tag
                  placeholder="请选择状态"
                  dictCode="state"
                  v-model="model.state"
                />
              </a-form-model-item>
            </div>
          </a-form-model>
        </a-spin>
      </a-modal>
</template>

<script>
import { addProperty, getQueryById,editProperty,postProperty } from '@/api/product'
export default {
  name: 'PhysicalAttrEdit',
  components: {  },
  data() {
    return {
      propertyCode:'',
      type: 'add',
      title: '新增',
      visible: false,
      model: {
        address: undefined,
        byteSort: null,
        dataType: null,
        code: null,
        propertyCode: null,
				startRegister: null,
        state: null
      },
      labelCol: {
        xs: { span: 24 },
        sm: { span: 7 },
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 14 },
      },
      confirmLoading: false,
      validatorRules: {
        address: [{ required: true, message: '请输入设备站点' }],
        dataType: [{ required: true, message: '请选择类型' }],
        code: [{ required: true, message: '请输入功能码' }],
        startRegister: [{ required: true, message: '请输入寄存器地址' }],
        byteSort: [{ required: true, message: '请选择字节顺序' }],
        state: [{ required: true, message: '请选择用户状态' }],
      },
      readOnly: false,
    }
  },
  created() {},
  methods: {

    getData(code){
      this.propertyCode=code
      let propertyCode=code
      getQueryById('/productCenter/device/queryModbusPointByDevicePropertyCode',{propertyCode}).then(res=>{
        if(res.success){
          this.model=res.result
        }
      })
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
        console.log(this.type);
        if (valid) {
          that.confirmLoading = true
          that.model.propertyCode=that.propertyCode
          if (that.type == 'add') {
            postProperty('/productCenter/device/saveModbusPoint', that.model)
              .then((res) => {
                if (res.success) {
                  that.confirmLoading = false
                  that.$message.success(res.message)
                  // that.$parent.$parent.getProperty()
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
            // editProperty('productCenter/device/editProperty', this.model)
            //   .then((res) => {
            //     if (res.success) {
            //       that.$message.success(res.message)
            //       that.$parent.$parent.getProperty()
            //       // that.$emit('ok')
            //     } else {
            //       that.$message.warning(res.message)
            //     }
            //   })
            //   .finally(() => {
            //     that.confirmLoading = false
            //     that.close()
            //   })
          }
        } else {
          return false
        }
      })
    },
    handleCancel() {
      this.model = {
        address: undefined,
        byteSort: null,
        dataType: null,
        functionCode: null,
        propertyCode: null,
				startRegister: null,
        state: null
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
