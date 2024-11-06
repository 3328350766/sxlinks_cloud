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
        <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="name" required label="名称">
          <a-input placeholder="请输入名称" v-model="model.name" />
        </a-form-model-item>
		<a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="propertyId" required label="指定属性">
		  <a-select ref="select" v-model="model.propertyId" placeholder="请选择指定属性" style="width:250px">
		    <a-select-option :value="item.id" v-for="(item, index) in lsProperty" :key="index">
		      {{item.name}}
		    </a-select-option>
		  </a-select> 
		</a-form-model-item>
		</div>
	<div class="col-2">
	<a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="dataCondition" required label="数据条件">
	  <j-dict-select-tag  placeholder="请选择数据条件" dictCode="data_condition" v-model="model.dataCondition"/>       
	</a-form-model-item>
	<a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="dataValue" required label="数据值">
	  <a-input placeholder="请输入数据值" v-model="model.dataValue" />
	</a-form-model-item>
			</div>
				<div class="col-2">
        <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="targetDeviceId" required label="目标设备">
          <a-select ref="select" v-model="model.targetDeviceId" placeholder="请选择目标设备" style="width:250px" @change="changeTargetDevice">
            <a-select-option :value="item.id" v-for="(item, index) in lsTargetDevice" :key="index">
              {{item.deviceName}}
            </a-select-option>
          </a-select>   
        </a-form-model-item>
        <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="targetPropertyId" required label="目标属性">
          <a-select ref="select" v-model="model.targetPropertyId" placeholder="请选择目标属性" style="width:250px">
            <a-select-option :value="item.id" v-for="(item, index) in lsTargetProperty" :key="index">
              {{item.name}}
            </a-select-option>
          </a-select> 
        </a-form-model-item>
				</div>
				
				<div class="col-2">
        <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="state" required label="状态">
          <j-dict-select-tag placeholder="请选择状态" dictCode="state" v-model="model.state" />
        </a-form-model-item>
        <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="targetDataValue" required label="指定值">
          <a-input placeholder="请输入目标属性数据值" v-model="model.targetDataValue" />
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
      equipmentNameList:[],
      equipmentFunctionList:[],
      lsProperty:[], //源设备的属性列表
	  lsTargetDevice:[], //目标设备列表
	  lsTargetProperty:[],//目标设备的属性列表
	  
       type: 'add',
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
        name: [{ required: true, message: '请输入名称' }],
        targetFunctionId: [{ required: true, message: '请选择目标属性' }],
        type: [{ required: true, message: '请选择联动类型'}],
        dataCondition: [{ required: true, message: '请选择数据条件'}],
        propertyId: [{ required: true, message: '请选择指定属性' }],
        dataValue: [{ required: true, message: '请选择数据值' }],
        state: [{ required: true, message: '请选择状态' }],
        deviceId: [{ required: true, message: '请选择目标设备' }],
      },
      readOnly: false,
    }
  },
  created() {
  },
  methods: {
        // 加载联动的源设备列表
    getEquipmentNameList(deviceCode,conditionShellId) {
		  this.model.conditionShellId=conditionShellId
		   getQueryById('/productCenter/device/queryAll').then((res) => {
			if (res.success) {
			  this.lsTargetDevice = res.result
			}
		})
		
		// getQueryById('/productCenter/device/queryAllFunction',{deviceCode:deviceCode}).then((res) => {
		// 	if (res.success) {
		// 	  this.equipmentFunctionList = res.result
		// 	}
		// })
		//获取源设备的属性列表
		getQueryById('/productCenter/device/queryAllProperty',{deviceCode:deviceCode}).then((res) => {
			if (res.success) {
			  this.lsProperty = res.result
			}
		})
		
		//获取目标设备的属性列表
		getQueryById('/productCenter/device/queryAllProperty',{deviceId:this.model.targetDeviceId}).then((res) => {
			if (res.success) {
			  this.lsTargetProperty = res.result
			}
		})
		
    },
	changeTargetDevice(e){
		this.lsTargetProperty=null;
		
	  getQueryById('/productCenter/device/queryAllProperty',{deviceId:this.model.targetDeviceId}).then((res) => {
	  	if (res.success) {
	  	  this.lsTargetProperty = res.result
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
        if (valid) {
          if (this.type == 'add') {
            addProperty('/ruleEngine/condition/addItem', this.model)
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
            editProperty('/ruleEngine/condition/editItem', this.model)
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
