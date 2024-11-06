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
      <a-form-model ref="form" :model="model" :rules="validatorRules" :form="model">
        
       
        <div class="col-2">
        	<a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="deviceId" required label="指定设备">
        	
        	  <a-select ref="select" v-model="model.deviceId" placeholder="请选择指定设备" style="width:250px">
        		<a-select-option :value="item.id" v-for="(item, index) in lsDevice" :key="index">
        		  {{item.deviceName}}
        		</a-select-option>
        	  </a-select> 
        	</a-form-model-item>
        	<a-form-model-item ></a-form-model-item>
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
import { queryProductAll } from '@/api/api'
import JDictSelectTag from '@/components/dict/JDictSelectTag'
import { getlistFunction, getQueryById, getListProperty, deleteProperty, addProduct, editProperty,getAction } from '@/api/product'

let validatorCodeTimer = null

export default {
  name: 'DeviceCopy',
  components: { JDictSelectTag, Map },
  data() {
    return {
      type: 'add',
      title: '复制子项',
      visible: false,
      projectOption: [],
      gatewayOption: [],
      model: {
        
        id: '',
      },
      productSelect: [],
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
          { required: true, message: '请输入设备编码', trigger: 'change' },
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
        type: [{ required: true, message: '请选择转发类型'}],
        deviceId: [{ required: true, message: '请选择指定设备' }],
        httpUrl: [{ required: true, message: '请输入http url地址' }],
        httpMethod: [{ required: true, message: '请选择http method' }],
      },
      url: {
        add: '/productCenter/device/add',
        edit: '/productCenter/device/edit',
      },
      readOnly: false,
		project:null,
		requestCodeSuccess: true, //验证编码
    }
  },
  created() {
   
    this.getDeviceList()
  },
  methods: {
    empty() {
      this.model = {
        
        id: '',
      }
    },
    // 查询设备列表
    getDeviceList() {
      getQueryById('/productCenter/device/queryAll').then((res) => {
        if (res.success) {
          this.lsDevice = res.result
        }
    })},

    add() {
      this.$refs.form.resetFields()
      this.edit({})
    },
    edit(record) {
      this.model = Object.assign({}, record)
      //this.$refs.form.deviceCode.readOnly=true; //设备编码不可修改
      this.visible = true
      if (record.id) {
        this.readOnly = true
      } else {
        this.readOnly = false
      }
    },
    close() {
      this.$emit('close')
      this.$refs.form.resetFields()
      this.visible = false
    },
    handleOk() {
      const that = this
      this.$refs.form.validate((valid) => {
        if (valid) {
          //let model = JSON.parse(JSON.stringify(this.model))
		 
          if (this.type == 'add') {
			//   if(this.requestCodeSuccess){
			// 	addProduct(`/productCenter/device/copy`, model).then((res) => {
			// 	  if (res.success) {
			// 		this.$message.success('添加成功')
			// 		this.$parent.$parent.searchQuery()
			// 		this.visible = false
			// 		this.handleCancel()
			// 	  }
			// 	})
			// }else{
			// 	that.$message.warning("编码已经存在，请重新输入");
			// }
          } else if (this.type == 'copy') {
			
				editProperty('/ruleEngine/dataForward/copyItem', this.model)
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
	handleChangeCheckCode(){
	  
	  getAction(`/productCenter/device/checkCode`,{code:this.model.deviceCode}).then(res=>{
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
      this.empty()
      this.visible = false
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
