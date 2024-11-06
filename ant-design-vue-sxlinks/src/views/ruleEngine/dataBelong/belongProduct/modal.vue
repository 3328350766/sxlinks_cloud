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
        <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="productId" required label="指定产品">
        
          <a-select ref="select" v-model="model.productId" placeholder="请选择产品" style="width:250px">
            <a-select-option :value="item.id" v-for="(item, index) in lsProduct" :key="index">
              {{item.productName}}
            </a-select-option>
          </a-select> 
        </a-form-model-item>
        <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="state" required label="状态">
          <j-dict-select-tag placeholder="请选择状态" dictCode="operation" v-model="model.state" />
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
  name: 'BelongProductModal',
  components: {  },
  data() {
    return {
      lsProduct:[],
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
       
        state: [{ required: true, message: '请输入联动状态'}],
        
        productId: [{ required: true, message: '请选择指定产品' }],
       
      },
      readOnly: false,
    }
  },
  created() {
  },
  methods: {
        // 查询产品名称列表
    getProductList() {
      
      getQueryById('/productCenter/product/queryAll').then((res) => {
        if (res.success) {
          this.lsProduct = res.result
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
            addProperty('/ruleEngine/dataBelong/addBelongProduct', this.model)
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
            editProperty('/ruleEngine/dataBelong/editBelongProduct', this.model)
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
