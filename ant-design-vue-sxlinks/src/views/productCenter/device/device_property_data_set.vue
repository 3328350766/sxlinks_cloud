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
          <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="value" label="值">
             <a-textarea :rows="6" placeholder="值" :maxlength="6"  v-model="model.value" :read-only="readOnly"/>
          </a-form-model-item>

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
  name: 'DevicePropertyDataSet',
  components: { JDictSelectTag },
  data() {
    return {
      type: 'add',
      title: '属性下发设置值',
      visible: false,
      model: {
        productCode: null,
		deviceCode: null,
        identifier: '',
        value: '',

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
        
        value: [{ required: true, message: '请输入值' }],
      },
      url: {
        add: 'productCenter/device/propertySetValue',
        
      },
      readOnly: false,
    }
  },
  created() {},
  methods: {
    add() {
      this.edit({})
    },
    
    close() {
      this.$emit('close')
      this.visible = false
      this.$refs.form.resetFields()
    },
	loadData(identifier,deviceCode){
		this.model.identifier=identifier;
		this.model.deviceCode=deviceCode;
	},
    handleOk() {
      const that = this
	
      // 触发表单验证
      this.$refs.form.validate((valid) => {
        if (valid) {
          that.confirmLoading = true
          if (this.type == 'add') {
			 //alert(this.model.identifier)
			 //alert(this.model.deviceCode)
            addProperty('productCenter/device/propertySetValue', this.model)
              .then((res) => {
                if (res.success) {
                  that.$message.success(res.msg)
                  that.$parent.$parent.getProperty()
                  // that.$emit('ok')
                } else {
                  that.$message.warning(res.msg)
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
