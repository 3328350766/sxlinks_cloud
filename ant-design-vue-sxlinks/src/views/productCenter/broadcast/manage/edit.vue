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
          <a-input placeholder="请输入网关名称" v-model="model.name" />
        </a-form-model-item>
		<a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="code" required label="编码">
		  <a-input placeholder="请输入网关编码" v-model="model.code" />
		</a-form-model-item>
		</div>
		<div class="col-2">
        <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="isHttp" required label="是否Http">
          <j-dict-select-tag  placeholder="请选择是否Http" dictCode="yn" v-model="model.isHttp"/>       
        </a-form-model-item>
		<a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="httpNeedLogin" required label="需要登录">
		  <j-dict-select-tag  placeholder="请选择需要登录" dictCode="yn" v-model="model.httpNeedLogin"/>       
		</a-form-model-item>
		</div>
		<div class="col-2">
		<a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="httpServer" label="http地址">
		  <a-input placeholder="http地址" v-model="model.httpServer" />
		</a-form-model-item>
		<a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="httpPort" label="http端口">
		  <a-input placeholder="http端口" v-model="model.httpPort" />
		</a-form-model-item>
		</div>
		<div class="col-2">
		<a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="httpUsername" label="用户">
		  <a-input placeholder="请输入用户" v-model="model.httpUsername" />
		</a-form-model-item>
		<a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="httpPassword" label="密码">
		  <a-input placeholder="请输入密码" v-model="model.httpPassword" />
		</a-form-model-item>
        </div>
		<div class="col-2">
        <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="firmId" required label="广播厂商">
          <a-select ref="select" v-model="model.firmId" placeholder="请选择广播厂商" style="width:250px">
            <a-select-option :value="item.id" v-for="(item, index) in broadcastFirmList" :key="index">
              {{item.name}}
            </a-select-option>
          </a-select>
        </a-form-model-item>
		<a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="projectId" required label="归属项目">
		  <a-select ref="select" v-model="model.projectId" placeholder="请选择归属项目" style="width:250px">
		    <a-select-option :value="item.id" v-for="(item, index) in projectList" :key="index">
		      {{item.name}}
		    </a-select-option>
		  </a-select>
		</a-form-model-item>
		</div>
        
		<div class="col-2">
		<a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="state" required label="状态">
		  <j-dict-select-tag placeholder="请选择状态" dictCode="operation" v-model="model.state" />
		</a-form-model-item>
		<a-form-model-item>

		</a-form-model-item>
		</div>
		<div class="col-2">
		<a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="描述">
		  <a-input placeholder="请输入描述" v-model="model.description" />
		</a-form-model-item>
		<a-form-model-item>
		
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
  name: 'BroadcastGatewayEdit',
  components: {  },
  data() {
    return {
      broadcastFirmList:[],
	  projectList:[],
      type: 'add',
      title: '操作',
      visible: false,
      model: {
		  firmId: undefined,
		  projectId: undefined,
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
        //sourceDeviceId: [{ required: true, message: '请选择指定设备' }],
        name: [{ required: true, message: '请输入名称' }],
        state: [{ required: true, message: '请输入状态' }],
		firmId: [{ required: true, message: '请选择厂商' }],
		projectId: [{ required: true, message: '请选择项目' }],
        //type: [{ required: true, message: '请选择预警类型' }],
      },
      url: {
        add: '/productCenter/boradcastGateway/add',
        edit: '/productCenter/boradcastGateway/edit',
      },
      readOnly: false,
    }
  },
  created() {

    this.getBroadcastFirmList();
	this.getProjectList();
  },
  methods: {
    // 查询所有广播网关厂家列表
    getBroadcastFirmList() {
      getQueryById('/productCenter/broadcastFirm/queryAll').then((res) => {
        if (res.success) {
          this.broadcastFirmList = res.result
        }
    })},
	//查询项目列表
	getProjectList() {
	  getQueryById('/productCenter/project/queryAll').then((res) => {
	    if (res.success) {
	      this.projectList = res.result
	    }
	})},
	
    add() {
		this.$refs.form.resetFields();
      this.edit({})
    },
    edit(record) {
		this.$refs.form.resetFields();
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
			
          // this.broadcastFirmList.forEach(element => { //遍历赋值
          //     if(element.id==this.model.firmId){
          //       this.model.sourceDeviceCode=element.deviceCode
          //     }
          // });
		  
          if (this.type == 'add') {
            addProperty('/productCenter/broadcastGateway/add', this.model)
              .then((res) => {
                if (res.success) {
                  that.$message.success(res.message)
                  that.$parent.$parent.getEnergyDataList()
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
            editProperty('/productCenter/broadcastGateway/edit', this.model)
              .then((res) => {
                if (res.success) {
                  that.$message.success(res.message)
                  that.$parent.$parent.getEnergyDataList()
                  // that.$emit('ok')
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
