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
              <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="matchMethod" required label="区配方式">
                <j-dict-select-tag
                  placeholder="请选择区配方式"
                  dictCode="json_type"
                  v-model="model.matchMethod"
                />
              </a-form-model-item>
              <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="matchObject" required label="匹配对象">
                <j-dict-select-tag
                  placeholder="请选择区配对象"
                  dictCode="json_obj"
                  v-model="model.matchObject"
                />
              </a-form-model-item>
            </div>
            <div class="col-2">
              <a-form-model-item
                :labelCol="labelCol"
                :wrapperCol="wrapperCol"
                required
                prop="varName"
                label="变量名"
              >
                <a-input placeholder="请输入变量名量" v-model="model.varName" :read-only="readOnly" style="width:250px" />
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
        matchMethod: undefined,
        propertyCode: null,
        matchObject: null,
        varName: null,
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
        matchMethod: [{ required: true, message: '请选择区配方式' }],
        matchObject: [{ required: true, message: '请选择区配对象' }],
        varName: [{ required: true, message: '请输入变量名量' }],
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
      getQueryById('/productCenter/device/queryJsonPointByDevicePropertyCode',{propertyCode}).then(res=>{
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
            postProperty('/productCenter/device/saveJsonPoint', that.model)
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
