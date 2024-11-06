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
        <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="code" required label="编码">
          <a-input placeholder="请输入编码" v-model="model.code" />
        </a-form-model-item>
				</div>
				<div class="col-2">
        <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="tableName" required label="表名">
          <a-input placeholder="请输入表名" v-model="model.tableName" />
        </a-form-model-item>
        <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="tableField" required label="表字段">
          <a-input placeholder="请输入表字段" v-model="model.tableField" />
        </a-form-model-item>
				</div>
				<div class="col-2">

        <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="state" required label="状态">
          <j-dict-select-tag placeholder="请选择状态" dictCode="operation" v-model="model.state" />
        </a-form-model-item>
		<a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="computeType" required label="计算类型">
		  <j-dict-select-tag placeholder="请选择计算类型" dictCode="compute_type" v-model="model.computeType" />
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
        
        name: [{ required: true, message: '请输入名称' }],
        state: [{ required: true, message: '请输入联动状态'}],
        type: [{ required: true, message: '请选择预警类型'}],
        propertyId: [{ required: true, message: '请选择指定属性' }],
        dataValue: [{ required: true, message: '请选择数据值' }],
      },
      readOnly: false,
    }
  },
  created() {
  },
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
          if (this.type == 'add') {
            addProperty('/ruleEngine/dataBelong/addItem', this.model)
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
            editProperty('/ruleEngine/dataBelong/editItem', this.model)
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
