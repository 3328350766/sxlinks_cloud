<template>
  <a-modal
    :title="title"
    :width="700"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭"
  >
    <a-spin :spinning="confirmLoading" class="spin">
      <a-form-model ref="form" :model="model" :rules="validatorRules">
        <a-form-item label="产品名称" 
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"> 
          <a-input placeholder="请输入产品名称" v-model="model.productName"  />
        </a-form-item>
        <a-form-item 
         label="产品编码"
         :labelCol="labelCol"
         :wrapperCol="wrapperCol" >
          <a-input placeholder="请输入产品编码" v-model="model.productCode"  :disabled="type == 'add' ? false : true" width="100" @blur="handleChangeCheckCode" />
        </a-form-item>
        <a-form-item
          label="协议类型"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol">
          <j-dict-select-tag
            placeholder="请选择协议类型"
            dictCode="protocol_type"
             width='100'
            v-model="model.protocolType"
          />
        </a-form-item>
        <!-- <a-form-item  label="产品种类">
          <j-dict-select-tag
            placeholder="请选择产品种类"
            dictCode="node_type"
            width="100"
            type="radio"
            v-model="model.status"
          />
        </a-form-item> -->
         <a-form-item label="网络协议"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
         >
           <a-radio-group :default-value="1">
            <a-radio :value="1">标准</a-radio>
          </a-radio-group>
        </a-form-item>
        <a-form-item  label="节点类型	"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
        >
          <j-dict-select-tag
            placeholder="请选择节点类型"
            dictCode="node_type"
            width="100"
            type="radio"
            v-model="model.nodeType"
          />
          </a-form-item>
          <a-form-item  label="网络类型	"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          >
          <j-dict-select-tag
            placeholder="请选择网络类型"
            dictCode="network_type"
            width="100"
            type="radio"
            v-model="model.networkType"
          />
        </a-form-item>
        <a-form-item label="产品描述"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol" >
          <a-textarea
              placeholder="产品描述"
              v-model="model.productDesc"
              :auto-size="{ minRows: 2, maxRows: 6 }"
            />
        </a-form-item>
      </a-form-model>
    </a-spin>
  </a-modal>
</template>

<script>
import pick from 'lodash.pick'
import { httpAction } from '@/api/manage'
import { duplicateCheck } from '@/api/api'
import JDictSelectTag from '@/components/dict/JDictSelectTag'
import {addProduct,editProperty,getAction} from '@/api/product'

let validatorCodeTimer = null

export default {
  name: 'ProductEdit',
  components: { JDictSelectTag },
  data() {
    return {
      type:'add',
      title: '操作',
      visible: false,
      model: {
        productName:null,
        productCode:null,
        protocolType:null, 
        nodeType:null,
        networkType:null,
        productDesc:null,  
        protocolCode:1,      
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
        productName: [
          { required: true, message: '请输入产品名称' },
        ],
        status: [{ required: true, message: '请选择节点类型' }],
        networkType: [{ required: true, message: '请选择网络协议' }],
        protocolType: [{ required: true, message: '请选择协议类型' }],
      },
      url: {
        add: '/productCenter/product/add',
        edit: '/productCenter/product/edit',
      },
      readOnly: false,
			requestCodeSuccess: true, //验证编码
    }
  },
  created() {},
  methods: {
    add() {
			this.$refs.form.resetFields();
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
          this.model.protocolCode='standard'
          if(this.type=='add'){
			if(this.requestCodeSuccess){
				addProduct(`/productCenter/product/add`,this.model).then(res=>{
				if(res.success){
					this.$message.success('添加成功');
					this.$parent.$parent.searchQuery()
					this.visible=false
				  }
				})
			}else{
				that.$message.warning("编码已经存在，请重新输入");
			}			
          }else if(this.type=='edit'){
            editProperty('/productCenter/product/edit', this.model).then((res) => {
                  if (res.success) {
                    that.$message.success(res.message)
                    that.$parent.$parent.searchQuery()
                    // that.$emit('ok')
                  } else {
                    that.$message.warning(res.message)
                  }
                }).finally(() => {
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
	  
	  getAction(`/productCenter/product/checkCode`,{code:this.model.productCode}).then(res=>{
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
      this.model={
        productName:null,
        productCode:null,
        protocolType:null, 
        nodeType:null,
        networkType:null,
        productDesc:null,  
        protocolCode:1,      
      },
      this.close()
    },
  },
}
</script>

<style lang="less" scoped>
.spin{
  height: 460px;
  overflow-y: auto;
}
</style>
