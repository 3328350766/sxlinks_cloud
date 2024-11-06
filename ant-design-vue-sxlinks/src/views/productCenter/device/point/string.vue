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
                  dictCode="matching_method"
                  v-model="model.matchMethod"
                />
              </a-form-model-item>
              <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="splitString"  label="间隔符">
                <a-input placeholder="请输入间隔符" v-model="model.splitString" :read-only="readOnly"  style="width:250px"/>
              </a-form-model-item>
              
            </div>
			<div class="col-2">
				<a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="startPos"  label="起始位置">
				  <a-input placeholder="请输入起始位置" v-model="model.startPos" :read-only="readOnly"  style="width:250px"/>
				</a-form-model-item>
				<a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="finishPos"  label="结束位置">
				  <a-input placeholder="请输入结束位置" v-model="model.finishPos" :read-only="readOnly"  style="width:250px"/>
				</a-form-model-item>
			</div>				
            <div class="col-2">
<!--              <a-form-model-item-->
<!--                :labelCol="labelCol"-->
<!--                :wrapperCol="wrapperCol"-->
<!--                required-->
<!--                prop="varName"-->
<!--                label="变量名"-->
<!--              >-->
<!--                <a-input placeholder="请输入变量名量" v-model="model.varName" :read-only="readOnly" style="width:250px" />-->
<!--              </a-form-model-item>-->
              <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="state" required label="操作状态">
                <j-dict-select-tag
                  placeholder="请选择状态"
                  dictCode="state"
                  v-model="model.state"
                />
              </a-form-model-item>
			  <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="replaceString"  label="过滤字符串">
			    <a-input placeholder="请输入过滤字符串" v-model="model.replaceString" :read-only="readOnly"  style="width:250px"/>
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
        splitString: null,
        varName: null,
        replaceString: null,
        startPos: null,
        finishPos: null,
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
        //splitString: [{ required: true, message: '请选择间隔符' }],
        //varName: [{ required: true, message: '请输入变量名量' }],
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
      getQueryById('/productCenter/device/queryStringPointByDevicePropertyCode',{propertyCode}).then(res=>{
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
            postProperty('/productCenter/device/saveStringPoint', that.model)
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
        matchMethod: undefined,
        propertyCode: null,
        splitString: null,
        varName: null,
        replaceString: null,
        startPos: null,
        finishPos: null,
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
