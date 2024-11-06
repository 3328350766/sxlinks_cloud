<template>
  <a-modal
    title="数据转发"
    :width="800"
    :visible="visible"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭"
  >
    <a-spin :spinning="confirmLoading">
      <div class="rule-wrap">
        <div class="wrap-head"><a-icon type="gateway" />数据转发</div>
        <div class="title">
          若是发送到topic，则无需输入“目标地址”。点击保存后，系统将生成Topic和接入鉴权信息。
        </div>
        <div class="form">
          <a-form ref="form" :model="model" :rules="validatorRules" class="ant-advanced-search-form">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="nodeType" required label="选择操作">
              <j-dict-select-tag placeholder="请选择操作" dictCode="node_type" v-model="model.nodeType" />
            </a-form-item>
          </a-form>
          <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="目标地址">
            <a-input placeholder="请输入目标地址" v-model="model.code" :read-only="readOnly" />
          </a-form-item>
        </div>
      </div>
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
  name: 'ProductModal',
  components: { JDictSelectTag },
  data() {
    return {
      tooltip:
        '1:SELECT *标识获取所有。2:WHERE操作符只是“=,>=,<=,>,<,<>(不等于),and,or”.3:常用过滤字段:iot_sys_event(事件),iot_sys_devld(设备id),iot_sys_cmd(设备命令)。',
      value: 3,
      title: '操作',
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
          { required: true, message: '请输入规则编码' },
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
        name: [{ required: true, message: '请输入规则名称' }],
        postRank: [{ required: true, message: '请选择职级' }],
      },
      url: {
        add: '/ruleEngine/rule/add',
        edit: '/ruleEngine/rule/edit',
      },
      readOnly: false,
    }
  },
  created() {},
  methods: {
    onChangeNum(value) {
      console.log('changed', value)
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
.rule-wrap{
  .wrap-head{
    font-size: 14px;
    line-height: 35px;
    font-size: 18px;
    font-weight: bold;
    >i{
      margin-right: 10px;
    }
  }
  .title{
    line-height: 26px;
    margin: 10px 0;
  }
  .form{
    .contant{
      display: flex;
      justify-content: space-between;
      >p{
        flex: 1;
      }
      .contantInput{
        flex: 8;
        .select{
          display: flex;
          align-items: center;
          margin-bottom: 10px;
          span{
            margin-right: 8px;
            color: aqua;
            &:nth-of-type(1){}
          }
        }
      }
    }
  }
  .number{
    line-height: 50px;
    >span{
      margin-right: 8px;
    }
  }
}
.ant-advanced-search-form {
  display: flex;
  justify-content: space-between;
  align-items: center;
  > div {
    flex: 1;
  }
}
.tooltip {
}
</style>
