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
            <a-input placeholder="请输入属性名称" v-model="model.name" :read-only="readOnly" />
          </a-form-model-item>
          <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="identifier" required label="属性编码">
             <a-input placeholder="请输入属性编码" v-model="model.identifier" :read-only="readOnly" :disabled="type == 'add' ? false : true"  @blur="handleChangeCheckPropertyCode"/>
           </a-form-model-item>
        </div>
        <div class="col-2">
          <a-form-model-item
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            prop="unitName"
            
            label="单位名称"
          >
            <a-input placeholder="请输入单位名称" v-model="model.unitName" :read-only="readOnly" />
          </a-form-model-item>

          <a-form-model-item>

          </a-form-model-item>

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
		<div class="col-2">
		  <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="status" required label="操作状态">
		    <j-dict-select-tag
		      placeholder="请选择状态"
		      dictCode="state"
		      v-model="model.status"
		    />
		  </a-form-model-item>
		  <a-form-model-item>
		      
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
import { addProperty, editProperty,getAction } from '@/api/product'

let validatorCodeTimer = null

export default {
  name: 'PhysicalAttrEdit',
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
        //funcDesc: [{ required: true, message: '请输入描述' }],
        unitName: [{ required: false, message: '请输入计量单位名称' }],
        //funcType: [{ required: true, message: '请输入物模型类型' }],
        name: [{ required: true, message: '请输入名称' }],
        identifier: [{ required: true, message: '请输入属性编码' }],
      },
      url: {
        add: '/productCenter/product/addProperty',
        edit: '/productCenter/product/editProperty',
      },
      readOnly: false,
	  requestCodeSuccess: true, //验证编码
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
          //that.confirmLoading = true
          if (this.type == 'add') {
			  if(this.requestCodeSuccess){
            addProperty('/productCenter/product/addProperty', this.model)
              .then((res) => {
                if (res.success) {
                  that.$message.success(res.message)
                  that.$parent.$parent.getProperty()
                  // that.$emit('ok')
                } else {
                  that.$message.warning(res.message)
                }
				handleCancel(); //清空输入框的值 
              })
              .finally(() => {
                that.confirmLoading = false
                that.close()
              })
			  }else{
			  	that.$message.warning("产品属性编码已经存在，请重新输入");
			  }		
          } else if (this.type == 'edit') {
            editProperty('/productCenter/product/editProperty', this.model)
              .then((res) => {
                if (res.success) {
                  that.$message.success(res.message)
                  that.$parent.$parent.getProperty()
                  // that.$emit('ok')
                } else {
                  that.$message.warning(res.message)
                }
              })
              .finally(() => {
                that.confirmLoading = false
                that.close()
              })
			  handleCancel(); //清空输入框的值 
          }
        } else {
          return false
        }
      })
    },
		handleChangeCheckPropertyCode(){
		  
		  getAction(`/productCenter/product/checkPropertyCode`,{code:this.model.identifier}).then(res=>{
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
.col-2{
  display: flex;
  justify-items: flex-start;
  align-items: center;
  >.ant-row{
    flex: 1;
  }
}
</style>
