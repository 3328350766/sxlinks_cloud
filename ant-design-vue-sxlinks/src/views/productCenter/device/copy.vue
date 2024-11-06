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
      <a-form-model ref="form" :model="model" :rules="validatorRules" :form="model">
        
       
        <div class="col-2">
          <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="deviceName" required label="设备名称">
            <a-input placeholder="请输入设备名称" v-model="model.deviceName" />
          </a-form-model-item>
          <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="deviceCode" required label="设备编码">
            <a-input placeholder="请输入设备编码" v-model="model.deviceCode"  @blur="handleChangeCheckCode"/>
          </a-form-model-item>
        </div>
       <div class="col-2">
          <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="projectId" required label="归属项目">
          						 <a-select style="width: 100%" v-model="model.projectId" placeholder="请选择项目" @change="getProjectById">
          							 <a-select-option v-for="(item, index) in projectOption" :key="index" :value="item.id">
          								 {{ item.name }}
          							 </a-select-option>
          						 </a-select>
          </a-form-model-item>	
					 	
           <a-form-model-item
             :labelCol="labelCol"
             :wrapperCol="wrapperCol"
             prop="deviceSecret"
             required
             label="设备密钥"
           >
             <a-input placeholder="请输入设备密钥" v-model="model.deviceSecret" />
           </a-form-model-item>
         </div>
        <div class="col-2 locationType">
          <a-form-model-item
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            prop="enableStatus"
            required
            label="激活状态"
          >
            <j-dict-select-tag placeholder="请选择激活状态" dictCode="state" v-model="model.enableStatus" />
          </a-form-model-item>
          <a-form-model-item
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            prop="locationType"
            required
            label="坐标类型"
          >
            <j-dict-select-tag placeholder="请选择坐标类型" dictCode="location_type" v-model="model.locationType" />
          </a-form-model-item>
          <Map ref="map" @callBack="callBack" top="-190px" right="48px"></Map>
          <span class="seleClick" @click="seleClick">选取</span>
        </div>
        <div class="col-2">
          <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="lng" required label="经度">
            <a-input v-model="model.lng" placeholder="请输入经度" style="width: 250px" />
          </a-form-model-item>
          <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="lat" required label="纬度">
            <a-input placeholder="请输入纬度" v-model="model.lat" />
          </a-form-model-item>
        </div>
       
       
      </a-form-model>
    </a-spin>
  </a-modal>
</template>

<script>
import pick from 'lodash.pick'
import { httpAction } from '@/api/manage'
import { queryProductAll } from '@/api/api'
import JDictSelectTag from '@/components/dict/JDictSelectTag'
import { getlistFunction, getQueryById, getListProperty, deleteProperty, addProduct, editProperty,getAction } from '@/api/product'
import Map from '@/components/map/index'

let validatorCodeTimer = null

export default {
  name: 'DeviceCopy',
  components: { JDictSelectTag, Map },
  data() {
    return {
      type: 'add',
      title: '创建设备',
      visible: false,
      projectOption: [],
      gatewayOption: [],
      model: {
        deviceCode: '',
        deviceName: '',
        deviceSecret: '',
        // gwDevCode: '',
        nodeType: undefined,
        networkType: undefined,
        protocolType: undefined,
        protocolCode: '',
        gatewayType: '',
        productCode: undefined,
        lat: '',
        lng: '',
        locationType: null,
        projectId: undefined,
        id: '',
      },
      productSelect: [],
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
          { required: true, message: '请输入设备编码', trigger: 'change' },
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
        deviceCode: [{ required: true, message: '请输入设备编码', trigger: 'change' }],
        deviceName: [{ required: true, message: '请输入设备名称', trigger: 'change' }],
        deviceSecret: [{ required: true, message: '请输入设备密钥', trigger: 'change' }],
       
        locationType: [{ required: true, message: '请选择坐标类型', trigger: 'change' }],
        projectId: [{ required: true, message: '请选择项目', trigger: 'change' }],
        lat: [{ required: true, message: '请选择纬度', trigger: 'change' }],
        lng: [{ required: true, message: '请选择经度', trigger: 'change' }],
      },
      url: {
        add: '/productCenter/device/add',
        edit: '/productCenter/device/edit',
      },
      readOnly: false,
		project:null,
		requestCodeSuccess: true, //验证编码
    }
  },
  created() {
    this.aa()
    this.getProject()
  },
  methods: {
    empty() {
      this.model = {
        deviceCode: '',
        deviceName: '',
        deviceSecret: '',
        // gwDevCode: '',
        nodeType: undefined,
        networkType: undefined,
        protocolType: undefined,
        protocolCode: '',
        gatewayType: '',
        productCode: undefined,
        lat: '',
        lng: '',
        locationType: null,
        projectId: undefined,
        id: '',
      }
    },
    // 归属项目
    getProject() {
      getQueryById('/productCenter/project/list', { pageNo: 1, pageSize: 50 }).then((res) => {
        if (res.success) {
          this.projectOption = res.result.records
        }
      })
      getQueryById('/productCenter/gateway/queryAll').then((res) => {
        if (res.success) {
          this.gatewayOption = res.result
        }
      })
    },
    seleClick() {
	if(this.model.projectId==null){
			  alert('必须先选择项目');
			  return;
	}else{
		
      this.$refs.map.show = !this.$refs.map.show
      setTimeout(() => {
		  //获取项目的基本信息
		  this.getProjectById(this.model.projectId);
        if(this.type == 'add'){
		  
		  this.$refs.map.renderMap('','',this.project.lng,this.project.lat)
        }else{
          this.$refs.map.renderMap(this.model.lng,this.model.lat,this.project.lng,this.project.lat)
        }
      })
	  }
    },
    // 地图经纬度回显
    callBack(point) {
      this.model.lng = point.lng
      this.model.lat = point.lat
    },
    aa() {
      getlistFunction(`/productCenter/product/queryAll`).then((res) => {
        if (res.success) {
          this.productSelect = res.result
        }
      })
    },
	getProjectById(projectId) {
		
	  this.loading = true
	  getAction('/productCenter/project/queryById',{id:projectId}).then((response) => {
	    if(response.result!=null){
			this.project=response.result;
			// //将地图中心置为项目的选中的那个
			// this.$refs.map.center.lng = result.lng
			// this.$refs.map.center.lat = result.lat
			
		}
		this.loading = false
	  })
	  
	},
    add() {
      this.$refs.form.resetFields()
      this.edit({})
    },
    edit(record) {
      this.model = Object.assign({}, record)
      //this.$refs.form.deviceCode.readOnly=true; //设备编码不可修改
      this.visible = true
      if (record.id) {
        this.readOnly = true
      } else {
        this.readOnly = false
      }
    },
    close() {
      this.$emit('close')
      this.$refs.form.resetFields()
      this.visible = false
    },
    handleOk() {
      const that = this
      this.$refs.form.validate((valid) => {
        if (valid) {
          //let model = JSON.parse(JSON.stringify(this.model))
		 
          if (this.type == 'add') {
			//   if(this.requestCodeSuccess){
			// 	addProduct(`/productCenter/device/copy`, model).then((res) => {
			// 	  if (res.success) {
			// 		this.$message.success('添加成功')
			// 		this.$parent.$parent.searchQuery()
			// 		this.visible = false
			// 		this.handleCancel()
			// 	  }
			// 	})
			// }else{
			// 	that.$message.warning("编码已经存在，请重新输入");
			// }
          } else if (this.type == 'copy') {
			  if(this.requestCodeSuccess){
				editProperty('/productCenter/device/copy', this.model)
				  .then((res) => {
					if (res.success) {
					  that.$message.success(res.message)
					  that.$parent.$parent.searchQuery()
					  // that.$emit('ok')
					} else {
					  that.$message.warning(res.message)
					}
					this.handleCancel()
				  })
				  .finally(() => {
					that.confirmLoading = false
					that.empty()
				  })
			  }else{
				that.$message.warning("编码已经存在，请重新输入");
			}
		  }
        } else {
          return false
        }
      })
    },
	handleChangeCheckCode(){
	  
	  getAction(`/productCenter/device/checkCode`,{code:this.model.deviceCode}).then(res=>{
	    if(res.success){
	      this.requestCodeSuccess=res.success
	    }else{
	      this.$message.error(res.message)
	      this.requestCodeSuccess=res.success
	    }
	  }).catch(()=>{
	    this.requestCodeSuccess=false
	  })
	},
    handleCancel() {
      this.empty()
      this.visible = false
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
