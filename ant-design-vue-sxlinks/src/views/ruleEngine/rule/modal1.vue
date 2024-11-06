<template>
  <a-modal
    title="创建规则"
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
        <div class="wrap-head"><a-icon type="gateway" />规则信息</div>
        <div class="title">
          规则引擎能够根据设备上报的数据（或者事件）进行规则触发，进而发送通知或者将数据转发到其他位置等功能，功能上采用类似sql的方式进行处理。
        </div>
        <div class="form">
          <a-form ref="form" :model="model" :rules="validatorRules" class="ant-advanced-search-form">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="code" required label="规则编码">
              <a-input placeholder="请输入规则编码" v-model="model.code" :read-only="readOnly" />
            </a-form-item>
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="nodeType" required label="节点类型">
              <j-dict-select-tag placeholder="请选择节点类型" dictCode="node_type" v-model="model.nodeType" />
            </a-form-item>
          </a-form>
          <div class="contant">
            <p>规则内容：</p>
            <div class="contantInput">
              <div class="select">
                <span>SELECT</span>
                <a-input ref="userNameInput" v-model="userName" placeholder="*" style="width:85%">
                  <a-tooltip slot="suffix" :title="tooltip" overlayClassName="tooltip">
                    <a-icon type="info-circle" style="color: rgba(0, 0, 0, 0.45)" />
                  </a-tooltip>
                </a-input>
              </div>
              <div class="select">
                <span>WHERE</span>
                <a-textarea
                  ref="userNameInput"
                  v-model="userName"
                  placeholder="请填写过滤条件"
                  style="width:85%"
                  :auto-size="{ minRows: 3, maxRows: 5 }"
                >
                </a-textarea>
              </div>
            </div>
          </div>
        </div>
        <div class="wrap-head"><a-icon type="pic-left" />规则信息</div>
        <div class="title">
          触发间隔：2次触发规则之间间隔达到之后才会真正推送；连续次数：条件连续次数到达后才会触发真正推送
        </div>
        <div class="number">
          <span>间隔时间：</span>
          <a-input-number id="inputNumber" v-model="value" :min="1" :max="10" @change="onChangeNum" />
        </div>
        <div class="number">
          <span>连续次数：</span>
          <a-input-number id="inputNumber" v-model="value" :min="1" :max="10" @change="onChangeNum" />
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
