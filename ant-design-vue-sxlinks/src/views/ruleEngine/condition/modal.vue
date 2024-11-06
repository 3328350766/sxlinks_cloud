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
        <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="name" required label="联动名称">
          <a-input placeholder="请输入联动名称" v-model="model.name" />
        </a-form-model-item>
		<a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="code" required label="联动编码">
		  <a-input placeholder="请输入联动编码" v-model="model.code"  :disabled="type == 'add' ? false : true" @blur="handleChangeCheckCode"/>
		</a-form-model-item>
		</div>
		<div class="col-2">
        <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="state" required label="状态">
          <j-dict-select-tag placeholder="请选择联动状态" dictCode="operation" v-model="model.state" />
        </a-form-model-item>
        <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="type" required label="联动类型">
          <j-dict-select-tag placeholder="请选择联动类型" dictCode="condition_shell_type" v-model="model.type" />
        </a-form-model-item>
		</div>
		<div class="col-2">
        <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="sourceDeviceId" required label="指定设备">
          <a-select ref="select" v-model="model.sourceDeviceId" placeholder="请选择指定设备" style="width:250px">
            <a-select-option :value="item.id" v-for="(item, index) in equipmentNameList" :key="index">
              {{item.deviceName}}
            </a-select-option>
          </a-select>        
        </a-form-model-item>
        <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="描述">
          <a-input placeholder="请输入描述" v-model="model.description" />
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
import { addProperty, editProperty, getQueryById,deleteProperty,getAction } from '@/api/product'
let validatorCodeTimer = null

export default {
  name: 'ProductModal',
  components: {  },
  data() {
    return {
       type: 'add',
      title: '操作',
      visible: false,
      model: {
        // createBy: "1",
        // modifyBy: "0",
      },
      labelCol: {
        xs: { span: 24 },
        sm: { span: 5 },
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 },
      },
      equipmentNameList:[],
      confirmLoading: false,
      validatorRules: {
        state: [{ required: true, message: '请输入联动状态' }],
        name: [{ required: true, message: '请输入联动名称' }],
        type: [{ required: true, message: '请选择联动类型' }],
        sourceDeviceId: [{ required: true, message: '请选择指定设备' }],
      },
      url: {
        add: '/ruleEngine/condition/add',
        edit: '/ruleEngine/condition/edit',
      },
      readOnly: false,
			requestCodeSuccess: true, //验证编码
    }
  },
  created() {
    this.getEquipmentNameList()
  },
  methods: {
    // 查询项目名称列表
    getEquipmentNameList() {
      getQueryById('/productCenter/device/queryAll').then((res) => {
        if (res.success) {
          this.equipmentNameList = res.result
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
          this.equipmentNameList.forEach(element => {
              if(element.id==this.model.sourceDeviceId){
                this.model.sourceDeviceCode=element.deviceCode
              }
          });
          if (this.type == 'add') {
			if(this.requestCodeSuccess){
				addProperty('/ruleEngine/condition/add', this.model)
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
			}else{
							  that.$message.warning("编码已经存在，请重新输入");
			}				
          } else if (this.type == 'edit') {
            editProperty('/ruleEngine/condition/edit', this.model)
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
	handleChangeCheckCode(){
	  
	  getAction(`/ruleEngine/condition/checkCode`,{code:this.model.code}).then(res=>{
	    if(res.success){
	      this.requestCodeSuccess=res.success
	    }else{
	      this.$message.error(res.message)
	      this.requestCodeSuccess=res.success
	    }
	  }).catch(()=>{
	    this.requestCodeSuccess=false
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
