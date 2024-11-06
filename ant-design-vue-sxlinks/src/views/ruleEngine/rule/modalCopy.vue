<template>
  <a-drawer
    title="新增数据转发"
    :width="720"
    :visible="visible"
    :body-style="{ paddingBottom: '80px' }"
    @close="onClose"
  >
    <div class="drawer">
      <div class="head">
        <div class="inputLeft">
          <span>名称</span>
          <a-input placeholder="Basic usage" style="width: 150px" />
        </div>
        <div class="inputRight">
          <span>类型</span>
          <a-input placeholder="Basic usage" style="width: 280px" />
        </div>
      </div>
      <div class="sql">
        <div class="inputLeft" style="display: flex; align-items: baseline">
          <span>SQL</span>
          <a-textarea placeholder="Basic usage" :rows="6" style="flex: 1" />
        </div>
      </div>
      <div class="implement">
        <div class="input">
          <span>执行动作</span>
        </div>
        <div class="implementRight">
          <div class="trigger">
            <div class="triggerList" v-for="(item, index) in triggerList" :key="index">
              <H2
                >{{ item.name }}
                <a href="javacsript:;" class="identification" @click="triggerDelete(index)">删除</a></H2
              >
              <div class="triggerCol" v-for="(i, index2) in item.list" :key="index2">
                <a-select default-value="lucy" style="width: 200px" @change="handleChange">
                  <a-select-option value="jack"> Jack </a-select-option>
                  <a-select-option value="lucy"> Lucy </a-select-option>
                  <a-select-option value="disabled"> Disabled </a-select-option>
                  <a-select-option value="Yiminghe"> yiminghe </a-select-option>
                </a-select>
              </div>
            </div>
            <a-button type="primary" icon="plus" class="addTrigger" @click="addTrigger">执行动作</a-button>
          </div>
        </div>
      </div>
      <div class="implement">
        <div class="input">
          <span>失效执行动作</span>
        </div>
        <div class="implementRight">
          <div class="trigger">
            <div class="triggerList" v-for="(item, index) in triggerList" :key="index">
              <H2
                >{{ item.name }}
                <a href="javacsript:;" class="identification" @click="triggerDelete(index)">删除</a></H2
              >
              <div class="triggerCol" v-for="(i, index2) in item.list" :key="index2">
                <a-select default-value="lucy" style="width: 200px" @change="handleChange">
                  <a-select-option value="jack"> Jack </a-select-option>
                  <a-select-option value="lucy"> Lucy </a-select-option>
                  <a-select-option value="disabled"> Disabled </a-select-option>
                  <a-select-option value="Yiminghe"> yiminghe </a-select-option>
                </a-select>
              </div>
            </div>
            <a-button type="primary" icon="plus" class="addTrigger" @click="addTrigger">执行动作</a-button>
          </div>
        </div>
      </div>
    </div>
    <div
      :style="{
        position: 'absolute',
        right: 0,
        bottom: 0,
        width: '100%',
        borderTop: '1px solid #e9e9e9',
        padding: '10px 16px',
        background: '#fff',
        textAlign: 'right',
        zIndex: 1,
      }"
    >
      <a-button :style="{ marginRight: '8px' }" @click="onClose"> 取消 </a-button>
      <a-button type="primary" @click="onClose"> 确定 </a-button>
    </div>
  </a-drawer>
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
      triggerList: [
        {
          name: '执行动作1',
          list: [
            {
              value: '10',
            },
          ],
        },
      ],
      form: this.$form.createForm(this),
      visible: false,
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
          { required: true, message: '请输入转发编码' },
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
        name: [{ required: true, message: '请输入转发名称' }],
        postRank: [{ required: true, message: '请选择职级' }],
      },
      url: {
        add: '/ruleEngine/dataForward/add',
        edit: '/ruleEngine/dataForward/edit',
      },
      readOnly: false,
    }
  },
  created() {},
  methods: {
    identificationDelete(index, index2) {
      this.triggerList[index].list.splice(index, 1)
    },
    addidentification(index) {
      this.triggerList[index].list.push({ value: '10' })
    },
    addTrigger() {
      this.triggerList.push({
        name: '触发器1',
        list: [
          {
            value: '10',
          },
        ],
      })
    },
    triggerDelete(index) {
      this.triggerList.splice(index, 1)
    },
    handleAdd() {
      this.visible = true
    },
    onClose() {
      this.visible = false
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
.addTrigger {
  margin: 15px 0;
}
.drawer {
  .head {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
  .sql {
    margin: 20px 0;
    .inputLeft {
      display: flex;
      justify-content: space-between;
      align-items: center;
    }
  }
  .implement {
  }
  .inputLeft {
    display: flex;
    justify-content: space-between;
    align-items: center;
    flex: 1;
    span {
      display: inline-block;
      width: 90px;
      text-align: right;
      padding-right: 10px;
    }
    input {
      flex: 1;
    }
  }
  .implement {
    display: flex;
    justify-content: space-between;
    align-items: baseline;
    .input {
      display: inline-block;
      width: 90px;
      text-align: right;
      padding-right: 10px;
    }
    .implementRight {
      flex: 1;
    }
  }
  .inputRight {
    display: flex;
    justify-content: space-between;
    align-items: center;
    flex: 1;
    margin-left: 10px;
    span {
      display: inline-block;
      width: 90px;
      text-align: right;
      padding-right: 10px;
    }
    input {
    }
  }
  .triggerList {
    background: #eeeaea;
    border-radius: 5px;
    padding-bottom: 10px;
    h2 {
      margin: 10px;
      font-weight: normal;
      font-size: 16px;
      padding-top: 10px;
      > a {
        margin-left: 15px;
        &:hover {
          cursor: pointer;
        }
      }
    }
    .select {
      margin: 0 10px;
      > div {
        margin-right: 15px;
      }
    }
    .triggerCol {
      display: flex;
      justify-content: flex-start;
      align-items: center;
      margin: 15px 10px;
      input {
        margin-right: 15px;
      }
      > div {
        margin-right: 15px;
      }
    }
  }
}
</style>
