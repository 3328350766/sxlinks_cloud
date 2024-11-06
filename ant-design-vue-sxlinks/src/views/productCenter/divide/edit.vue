<template>
  <a-modal
    :title="title"
    :width="800"
    :visible="visible"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭">

    <a-spin :spinning="confirmLoading">
      <a-form-model ref="form" :model="model" :rules="validatorRules">

        <a-form-model-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          prop="code"
          required
          label="标识">
          <a-input placeholder="请输入标识" v-model="model.code" :read-only="readOnly"/>
        </a-form-model-item>
        <a-form-model-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          prop="name"
          required
          label="名称">
          <a-input placeholder="请输入分区名称" v-model="model.name" :read-only="readOnly"/>
        </a-form-model-item>
        <a-form-model-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          prop="fid"
          required
          label="上级ID">
          <a-input placeholder="请输入上级ID" v-model="model.fid" :read-only="readOnly"/>
        </a-form-model-item>
        <a-form-model-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          prop="description"
          required
          label="描述">  
          <a-textarea
              v-model="model.description"
              placeholder="请输入产品描述"
              :auto-size="{ minRows: 3, maxRows: 5 }"
              :read-only="readOnly"
            />
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
	import { addProperty,editProperty } from '@/api/product'

  let validatorCodeTimer = null

  export default {
    name: 'DivideEdit',
    components: { JDictSelectTag },
    data() {
      return {
        type:'add',
        title: '新增',
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
          code: [
              { required: true, message: '请输入产品标识' },
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
                      dataId: this.model.id
                    }).then((res) => {
                      if (res.success) {
                        callback()
                      } else {
                        callback(res.message)
                      }
                    }).catch(console.error)
                  }, 300)
                }
              }
            ],
          name: [{ required: true, message: '请输入产品名称' }] ,
          description:  [{ required: true, message: '请选择描述' }] ,
          fid: [{ required: true, message: '请输入上级ID' }] ,
        },
        url: {
          add: '/productCenter/divide/add',
          edit: '/productCenter/divide/edit',
        },
        readOnly:false
      }
    },
    created() {
    },
    methods: {
      add() {
				this.$refs.form.resetFields();
        this.edit({})
      },
      edit(record) {
        this.model = Object.assign({}, record)
        this.visible = true
        if(record.id){
          this.readOnly=true
        }else{
          this.readOnly=false
        }
      },
      close() {
        this.$emit('close')
        this.visible = false
        this.$refs.form.resetFields();
      },
      handleOk() {
        const that = this
        // 触发表单验证
        this.$refs.form.validate(valid => {
          if (valid) {
            if(this.type=='add'){
              addProperty('/productCenter/divide/add', this.model).then((res) => {
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
            }else if(this.type=='edit'){
              editProperty('productCenter/divide/edit', this.model).then((res) => {
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
            // that.confirmLoading = true
            // let httpurl = ''
            // let method = ''
            // if (!this.model.id) {
            //   httpurl += this.url.add
            //   method = 'post'
            // } else {
            //   httpurl += this.url.edit
            //   method = 'put'
            // }

            // httpAction(httpurl, this.model, method).then((res) => {
            //   if (res.success) {
            //     that.$message.success(res.message)
            //     that.$emit('ok')
            //   } else {
            //     that.$message.warning(res.message)
            //   }
            // }).finally(() => {
            //   that.confirmLoading = false
            //   that.close()
            // })
          }else{
            return false;
          }
        })
      },
      handleCancel() {
				this.$refs.form.resetFields();
        this.close()
      },


    }
  }
</script>

<style lang="less" scoped>

</style>
