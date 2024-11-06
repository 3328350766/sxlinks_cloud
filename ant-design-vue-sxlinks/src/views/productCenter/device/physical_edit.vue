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
          <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="code" required label="设备ID">
            <a-input placeholder="请输入设备ID" v-model="model.code" :read-only="readOnly" />
          </a-form-model-item>
          <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="code" required label="设备名称">
            <a-input placeholder="请输入设备名称" v-model="model.code" :read-only="readOnly" />
          </a-form-model-item>
        </div>
        <div class="col-2">
          <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="postRank" required label="产品">
            <j-dict-select-tag placeholder="请选择产品" dictCode="position_rank" v-model="model.postRank" />
          </a-form-model-item>
          <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="所属机构">
            <j-dict-select-tag placeholder="请选择所属机构" dictCode="position_rank" v-model="model.postRank" />
          </a-form-model-item>
        </div>
        <div class="col-2">
          <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="说明">
            <a-textarea v-model="model.name" placeholder="请输入至少五个字符" :auto-size="{ minRows: 3, maxRows: 5 }" />
          </a-form-model-item>
          <a-form-model-item></a-form-model-item>
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

let validatorCodeTimer = null

export default {
  name: 'PhysicalEdit',
  components: { JDictSelectTag },
  data() {
    return {
      title: '新建设备',
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
          { required: true, message: '请输入设备编码' },
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
        name: [{ required: true, message: '请输入设备名称' }],
        postRank: [{ required: true, message: '请选择职级' }],
      },
      url: {
        add: '/productCenter/device/add',
        edit: '/productCenter/device/edit',
      },
      readOnly: false,
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
          that.confirmLoading = true
          let httpurl = ''
          let method = ''
          if (!this.model.id) {
            httpurl += this.url.add
            method = 'post'
          } else {
            httpurl += this.url.edit
            method = 'put'
          }

          httpAction(httpurl, this.model, method)
            .then((res) => {
              if (res.success) {
                that.$message.success(res.message)
                that.$emit('ok')
              } else {
                that.$message.warning(res.message)
              }
            })
            .finally(() => {
              that.confirmLoading = false
              that.close()
            })
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
