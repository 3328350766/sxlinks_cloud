<template>
  <a-card :bordered="false">
    <!-- table区域-begin -->
    <div>
          <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">创建监控</a-button>
    </div>
      <a-table
        ref="table"
        size="middle"
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="false"
        @change="handleTableChange"
      >
        <span slot="action" slot-scope="text, record">
          <!-- <a @click="handleEdit(record)">刷新</a> -->
          <a @click="handleEdit(record)" style="margin-right:10px">编辑</a>
          <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
          </a-popconfirm>

         
        </span>
        <template slot="spread" slot-scope="text, record">
          <a-select placeholder="流传输模式" :value="item.rule" :getPopupContainer="node=>node.parentNode" @change="handleRuleChange(item,$event)">
            <a-select-option value="UDP">UDP</a-select-option>
            <a-select-option value="TCP-ACTIVE">TCP主动模式</a-select-option>
            <a-select-option value="TCP-PASSIVE">TCP被动模式</a-select-option>
          </a-select>
        </template>
      </a-table>
      <a-pagination
        v-model="queryParam.pageNo"
        :total="total"
        show-less-items
        class="pagination"
        :page-size="queryParam.pageSize"
        :default-current="queryParam.pageNo"
        @change="changePagination"
      />
    </div>
    <!-- table区域-end -->


    <a-modal
    :title="title"
    :width="800"
    :visible="visible"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭"
  >
    <a-spin :spinning="confirmLoading">
      <a-form-model ref="form" :model="model" :rules="validatorRules">
				<div class="col-2">
        <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="deviceId" required label="设备编号">
          <a-input placeholder="请输入设备编号" v-model="model.deviceId" />
        </a-form-model-item>
        <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="name"  label="设备名称">
          <a-input placeholder="请输入设备名称" v-model="model.name" />
        </a-form-model-item>
				</div>
				<div class="col-2">
        <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="password"  label="密码">
          <a-input placeholder="请输入密码" v-model="model.password" type="password" />
        </a-form-model-item>
        <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="mediaServerId"  label="流媒体ID">
          <a-select placeholder="请输入流媒体ID" v-model="model.mediaServerId">
            <a-select-option value="UDP">UDP</a-select-option>
            <a-select-option value="FQ3TF8yT83wh5Wvz">FQ3TF8yT83wh5Wvz</a-select-option>
          </a-select>
        </a-form-model-item>
				</div>
        <!-- <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="sourceDeviceId" required label="自动负载最小">
          <a-select ref="select" v-model="model.sourceDeviceId" placeholder="请输入自动负载最小" style="width:250px">
            <a-select-option :value="item.id" v-for="(item, index) in equipmentNameList" :key="index">
              {{item.deviceName}}
            </a-select-option>
          </a-select>
        </a-form-model-item> -->
<div class="col-2">
        <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="charset"  label="字符集">
          <a-select ref="select" v-model="model.charset" placeholder="请输入字符集" style="width:250px">
            <a-select-option value="GB2312" >gb2312</a-select-option>
            <a-select-option value="UTF-8" >utf-8</a-select-option>
          </a-select>
        </a-form-model-item>
        <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="geoCoordSys"  label="地理坐标系">
          <j-dict-select-tag placeholder="请选择地理坐标系" dictCode="location_type" v-model="model.geoCoordSys" />
        </a-form-model-item>
</div>
<div class="col-2">
        <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="treeType"  label="目录结构">
          <a-select ref="select" v-model="model.treeType" placeholder="请输入目录结构" style="width:250px">
            <a-select-option value="WGS84" >WGS84</a-select-option>
            <a-select-option value="GCJ02" >GCJ02</a-select-option>
          </a-select>

          <!-- <a-input placeholder="请输入目录结构" v-model="model.treeType" /> -->
        </a-form-model-item>
        <!-- <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="subscribeCycleForCatalog" required label="目录订阅">
          <a-input placeholder="请输入目录订阅" v-model="model.subscribeCycleForCatalog" />
        </a-form-model-item>
        <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="subscribeCycleForMobilePosition" required label="移动位置订阅">
          <a-input placeholder="请输入移动位置订阅" v-model="model.subscribeCycleForMobilePosition" />
        </a-form-model-item>
        <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="mobilePositionSubmissionInterval" required label="移动位置报送间隔">
          <a-input placeholder="请输入移动位置报送间隔" v-model="model.mobilePositionSubmissionInterval" />
        </a-form-model-item> -->
        <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="ssrcCheck" label="其他选项">
          <a-checkbox v-model="model.ssrcCheck">SSRC校验</a-checkbox>
        </a-form-model-item>
		</div>
      </a-form-model>
    </a-spin>
  </a-modal>
  </a-card>
</template>

<script>
// import ProductModal from './modal'
// import PhysicalModelModal from './physicalModel'
import { putAction, getQueryById, getListProperty, deleteProperty,postAction } from '@/api/product'

export default {
  name: 'ProductList',
  components: {
  },
  data() {
    return {
      confirmLoading:false,
      model:{
        ssrcCheck:false,
      },
      title:'新增',
      visible:false,
      total:0,
      dataSource:[],
      queryParam: {
        pageNo: 1,
        pageSize: 10,
      },
      description: '监控管理页面',
      // 表头
      columns: [
        {
          title: '名称',
          align: 'center',
          dataIndex: 'name',
        },
        {
          title: '设备编码',
          align: 'center',
          dataIndex: 'deviceId',
        },
        {
          title: '地址',
          align: 'center',
          dataIndex: 'hostAddress',
        },
        {
          title: '厂家',
          align: 'center',
          dataIndex: 'manufacturer',
        },
        {
          title: '流传输模式',
          align: 'center',
          dataIndex: 'streamMode',
          // scopedSlots: { customRender: 'spread' },
        },
        {
          title: '通道数',
          align: 'center',
          dataIndex: 'channelCount',
        },
        {
          title: '状态',
          align: 'center',
          dataIndex: 'ssrcCheck',
          // customRender: function(text) {
          //   if (text == 0) {
          //     return '禁用'
          //   } else if (text == 1) {
          //     return '启用'
          //   } else {
          //     return text
          //   }
          // }
        },
        {
          title: '最近心跳',
          align: 'center',
          dataIndex: 'updateTime',
        },{
          title: '最近注册',
          align: 'center',
          dataIndex: 'registerTime',
        },
        {
          title: '操作',
          dataIndex: 'action',
          align: 'center',
          scopedSlots: { customRender: 'action' },
        },
      ],
      validatorRules: {
        deviceId: [{ required: true, message: '请输入设备编号' }]
      },
      labelCol: {
        xs: { span: 24 },
        sm: { span: 8 },
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 },
      },
    }
  },
  computed: {
  },
  mounted(){
    this.getData()
  },
  methods: {
    changePagination(e){
      this.queryParam.pageNo=e
      this.getData()
    },
    handleEdit (record) {
      this.title='编辑'
        this.model = Object.assign({}, record)
        this.visible=true     
    },
    //删除
    handleDelete(id) {
      deleteProperty('/video/device/remove/' + id).then((res) => {
        if (res.success) {
          this.$message.success(res.message)
          this.getData()
        } else {
          this.$message.warning(res.message)
        }
      })
    },
    handleAdd(){

      this.title='新增'
      this.visible=true
      this.model={
        ssrcCheck:false,
      },
      this.$nextTick(()=>{
        if(this.$refs.form){
          this.$refs.form.clearValidate();
        }
      })
    },
    handleOk(){
      this.$refs.form.validate(valid => {
        if(valid){
          if(this.title=='新增'){
            postAction('/video/device/add',this.model).then((res)=>{
              if (res.success) {
                  this.$message.success(`保存成功！`)
                  this.getData()
              } else {
                  this.$message.warn(`保存失败：` + res.message)
              }
              this.visible=false
            })
          }else{
            putAction('/video/device/edit',this.model).then((res)=>{
              if (res.success) {
                  this.$message.success(`修改成功！`)
                  this.getData()
              } else {
                  this.$message.warn(`修改失败：` + res.message)
              }
              this.visible=false
            })
          }
          
        }
      })
      this.visible=false
    },
    handleCancel(){
      this.visible=false
      this.$nextTick(()=>{
        this.$refs.form.resetFields();
      })
    },
    getData(){
      getQueryById('/video/device/list', this.queryParam).then((res) => {
        if (res.success) {
          this.dataSource = res.result.records
          this.total = res.result.total
          this.loading = false
        }
      })
    },
    handleTableChange(e){
      this.queryParam.pageNo=e
      this.getData()
    },
  },
}
</script>
<style scoped lang="less">
@import '~@assets/less/common.less';
/deep/td{
  background: #fff!important;
}
.pagination{
  text-align: right;
  margin: 10px 0 0 0;
}

.col-2 {
  display: flex;
  justify-items: flex-start;
  align-items: center;
  > .ant-row {
    flex: 1;
  }
}
</style>