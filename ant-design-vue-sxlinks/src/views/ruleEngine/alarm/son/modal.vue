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
        <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="state" required label="状态">
          <j-dict-select-tag placeholder="请选择状态" dictCode="operation" v-model="model.state" />
        </a-form-model-item>
				</div>
				<div class="col-2">
        
		  
        <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="propertyId" required label="指定属性">

          <a-select ref="select" v-model="model.propertyId" placeholder="请选择指定属性" style="width:250px">
            <a-select-option :value="item.id" v-for="(item, index) in lsProperty" :key="index">
              {{item.name}}
            </a-select-option>
          </a-select> 
        </a-form-model-item>
		<a-form-model-item>
		  </a-form-model-item>
				</div>
				<div class="col-2">
        <!-- <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="type" required label="预警类型">
          <j-dict-select-tag  placeholder="请选择预警类型" dictCode="alarm_type" v-model="model.type"/>       
        </a-form-model-item> -->
        <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="propertyId" required label="指定条件">
        <j-dict-select-tag  placeholder="请选择数据条件" dictCode="data_condition" v-model="model.dataCondition"/>
        
        </a-form-model-item>
		<a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="dataValue" required label="数据值">
          <a-input placeholder="请输入数据值" v-model="model.dataValue" />
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
        dataCondition: [{ required: true, message: '请选择数据条件' }],
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
        // 查询项目名称列表
    getPropertyList(deviceCode,id) {
      this.model.alarmId=id
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
            addProperty('/ruleEngine/alarm/addItem', this.model)
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
            editProperty('/ruleEngine/alarm/editItem', this.model)
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
