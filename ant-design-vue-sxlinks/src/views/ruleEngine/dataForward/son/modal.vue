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
					<a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="deviceId" required label="指定设备">
					
					  <a-select ref="select" v-model="model.deviceId" placeholder="请选择指定设备" style="width:250px">
						<a-select-option :value="item.id" v-for="(item, index) in lsDevice" :key="index">
						  {{item.deviceName}}
						</a-select-option>
					  </a-select> 
					</a-form-model-item>
					<a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="state" required label="状态">
					  <j-dict-select-tag placeholder="请选择状态" dictCode="operation" v-model="model.state" />
					</a-form-model-item>
				</div>
				<div class="col-2">
					
					<a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="type" required label="转发类型">
					  <j-dict-select-tag placeholder="请选择转发类型" dictCode="forward_type" v-model="model.type" />
					</a-form-model-item>
					<a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="httpMethod" required label="http方式">
					  <j-dict-select-tag placeholder="请选择http请求方式" dictCode="http_method" v-model="model.httpMethod" />
					</a-form-model-item>
				</div>
				<div class="col-2">
					
					<a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="httpUrl" required label="url">
					  <a-textarea placeholder="请输入url" v-model="model.httpUrl" />
					</a-form-model-item>
				</div>
				<div class="col-2">

					<a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="httpHeader"  label="http header">
					  <a-textarea placeholder="请输入http header" v-model="model.httpHeader" />
					</a-form-model-item>
				</div>
				<div class="col-2">

					<a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="httpBody"  label="http body">
					  <a-textarea placeholder="请输入http body,默认为空则用此设备的物模型json结果推送" v-model="model.httpBody" />
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
import { addProperty, editProperty, getQueryById,deleteProperty } from '@/api/product'
let validatorCodeTimer = null

export default {
  name: 'ProductModal',
  components: {  },
  data() {
    return {
      lsProperty:[],
	  lsDevice:[],
       type: 'add',
      title: '操作',
      visible: false,
      model: {
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
  
        state: [{ required: true, message: '请输入状态'}],
        type: [{ required: true, message: '请选择转发类型'}],
        deviceId: [{ required: true, message: '请选择指定设备' }],
		httpUrl: [{ required: true, message: '请输入http url地址' }],
		httpMethod: [{ required: true, message: '请选择http method' }],
      },
      readOnly: false,
    }
  },
  created() {
  },
  methods: {
	  // 查询设备列表
	  getDeviceList() {
	    getQueryById('/productCenter/device/queryAll').then((res) => {
	      if (res.success) {
	        this.lsDevice = res.result
	      }
	  })},
        // 查询设备的属性列表
    getPropertyList(deviceCode,id) {
      this.model.dataForwardId=id
      getQueryById('/productCenter/device/queryAllProperty',{deviceCode:deviceCode}).then((res) => {
        if (res.success) {
          this.lsProperty = res.result
        }
    })},
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
            addProperty('/ruleEngine/dataForward/addItem', this.model)
              .then((res) => {
                if (res.success) {
                  that.$message.success(res.message)
                  that.$emit("callback")
                } else {
                  that.$message.warning(res.message)
                }
              })
              .finally(() => {
                that.confirmLoading = false
                that.close()
              })
          } else if (this.type == 'edit') {
            editProperty('/ruleEngine/dataForward/editItem', this.model)
              .then((res) => {
                if (res.success) {
                  that.$message.success(res.message)
                  that.$emit("callback")                
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
.locationType {
  position: relative;
  .seleClick {
    border: 1px solid #4395ff;
    border-radius: 3px;
    padding: 0px 6px;
    color: #000;
    position: absolute;
    right: 0px;
    top: 3px;
    line-height: 30px;
    display: inline-block;
    &:hover {
      cursor: pointer;
    }
  }
}
.col-2 {
  display: flex;
  justify-items: flex-start;
  align-items: center;
  > .ant-row {
    flex: 1;
  }
}
.remarks {
  width: 445px;
  margin: 0 auto;
}
</style>
