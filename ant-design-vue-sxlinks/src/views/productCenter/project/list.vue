<template>
  <a-card :bordered="false">
    <div class="app-container">
      <!--查询条件-->
      <a-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" class="form">
        <a-form-item label="项目名称" prop="name"  :labelCol="{span:8}" :wrapperCol="{span:16}">
          <a-input size="mini" v-model.trim="queryParams.name" clearable @keyup.enter.native="handleQuery" style="width:250px" placeholder="请输入项目名称" />
        </a-form-item>
        <a-form-item>
          <a-space>
            <a-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</a-button>
             <a-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</a-button>
          </a-space>
        </a-form-item>
      </a-form>

	  <!-- 操作按钮区域 -->
	  <div class="table-operator">
	    <a-button @click="handleAdd" type="primary" icon="plus">创建项目</a-button>
		
	  </div>
	  
	  <div class="table-operator"></div>
	  
      <!--展示列表-->
      <a-table v-loading="loading"  :dataSource="projectManagementList" @selection-change="handleSelectionChange" :columns="columnsTable" :pagination="false">
        <span slot="action" slot-scope="text, scope">
          <a-space>
            <a-button
              size="mini"
              type="primary"
              @click="handleUpdateTable(scope)"
              >修改</a-button
            >
            <a-popconfirm title="确定删除吗?" @confirm="() => handleDel(scope)">
              <a-button
              size="mini"
              danger
              >删除</a-button
            >
      </a-popconfirm>
          </a-space>
 
            
        </span>

      </a-table>
      <!--分页-->
      <a-pagination
      v-show="total > 0"
      v-model="queryParams.pageNum"
      :total="total"
            show-less-items
            class="pagination"
            :page-size="queryParams.pageSize"
            :default-current="queryParams.pageNum"
            @change="getList"
      />
      <!-- 添加项目管理对话框 -->
      <a-modal :title="title" :visible="open" width="800px"  @cancel="cancel" @ok="submitForm" cancelText="关闭">
        <a-form-model ref="form" :model="form" :rules="rules" laba-width="80px">
          <div class="col-2">
		  <a-form-item label="项目名称" name="name" required  :labelCol="{span:8}" :wrapperCol="{span:16}">
            <a-input v-model="form.name" placeholder="请输入项目名称" style="width:200px" />
          </a-form-item>
		  <a-form-item label="项目状态" name="state" required  :labelCol="{span:8}" :wrapperCol="{span:16}">
		    <j-dict-select-tag v-model="form.state" placeholder="请选择项目状态" dictCode="status" style="width:200px"/>
		  </a-form-item>
		  </div>
		  <div class="col-2">
		  <a-form-item label="所属省市区" name="districtId" required  :labelCol="{span:8}" :wrapperCol="{span:16}">
		    <a-cascader
		      v-model="form.districtId"
		      :options="menuOptions"
		      expand-trigger="hover"
		      style="width:200px"
		      @change="handleChange"
		      placeholder="选择所属省市区"
		    />
		  </a-form-item>
          <a-form-item label="坐标类型" name="locationType" required :labelCol="{span:8}" :wrapperCol="{span:16}" class="locationType">
            <j-dict-select-tag v-model="form.locationType" placeholder="请选择坐标类型" dictCode="location_type"  style="width:200px"/>
            <Map ref="map" @callBack="callBack" width="463px" right="-8px" top="-71px"></Map>
            <span class="seleClick" @click="seleClick">选取</span>
          </a-form-item>
		  </div>
		  <div class="col-2">
          <a-form-item label="经度" name="lng" required  :labelCol="{span:8}" :wrapperCol="{span:16}">
            <a-input v-model="form.lng" placeholder="请输入经度" style="width:200px"/>
          </a-form-item>
          <a-form-item label="纬度" name="lat"  required  :labelCol="{span:8}" :wrapperCol="{span:16}">
            <a-input v-model="form.lat" placeholder="请输入纬度" style="width:200px"/>
          </a-form-item>
		  </div>
		  <div class="col-2">
          
          <a-form-item label="项目描述" name="description" required  :labelCol="{span:8}" :wrapperCol="{span:16}">
            <a-input v-model="form.description" placeholder="请输入项目描述" style="width:200px"/>
          </a-form-item>
		  <a-form-item>
			<a-form-item label="序号" name="sortNo" required  :labelCol="{span:8}" :wrapperCol="{span:16}">
				<a-input v-model="form.sortNo" placeholder="请输入序号" style="width:200px"/>
			</a-form-item>
		  </a-form-item>
		  </div>
          
          <!-- <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="name" required label="项目名称">
                <a-input placeholder="请输入项目名称" v-model="form.name" :read-only="readOnly" />
              </a-form-model-item>
              <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="locationType" required label="坐标类型">
                <a-input placeholder="请选择坐标类型" v-model="form.locationType" :read-only="readOnly" />
              </a-form-model-item>
              <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="lng" required label="经度">
                <a-input placeholder="请输入经度" v-model="form.lng" :read-only="readOnly" />
              </a-form-model-item>
              <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="lat" required label="纬度">
                <a-input placeholder="请输入纬度" v-model="form.lat" :read-only="readOnly" />
              </a-form-model-item>
              <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="districtId" required label="所属省市区">
                <a-input placeholder="选择所属省市区" v-model="form.districtId" :read-only="readOnly" />
              </a-form-model-item>
              <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="description" required label="项目描述">
                <a-input placeholder="请输入项目描述" v-model="form.description" :read-only="readOnly" />
              </a-form-model-item>
              <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="state" required label="项目状态">
                <a-input placeholder="请选择项目状态" v-model="form.state" :read-only="readOnly" />
              </a-form-model-item> -->
        </a-form-model>
      </a-modal>
    </div>
  </a-card>

</template>

<script>
	
import { getAction, addProduct,deleteProperty,editProperty } from '@/api/product'
import testForm from './testForm.vue'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import Treeselect from '@riophae/vue-treeselect'
import { addDateRange,resetForm } from '@/utils/util'
import JDictSelectTag from '@/components/dict/JDictSelectTag'
import Map from '@/components/map/index'
import { message} from 'ant-design-vue';

export default {
  name: 'ProjectManagement',
  components: {  Treeselect,testForm,JDictSelectTag,Map },
  //字典
  dicts: ['spl_project_status'],
  data() {
    return {
       // 表头
       columnsTable: [
        {
          title: '#',
          dataIndex: '',
          key: 'rowIndex',
          width: 60,
          align: 'center',
          customRender: function (t, r, index) {
            return parseInt(index) + 1
          },
        },
        {
          title: '项目名称',
          align: 'left',
          dataIndex: 'name',
        },
		{
		  title: '所属省市区',
		  align: 'center',
		  dataIndex: 'districtId',
		  customRender: function (t, r, index) {
		    let text=`${r.provinceName}/${r.cityName}/${r.countyName}`
		    //let text=`${r.countyName}`
		    return text
		  },
		},
		{
		  title: '坐标类型',
		  align: 'center',
		  dataIndex: 'locationType',
		  customRender: function(text) {
		     let map={
		      	'wgs84':'gps标准坐标系',
		        'gcj-02':'火星坐标系',
		        'bd-09':'百度坐标系',
		        'other':'其它'
		    }
		    return map[text]
		  }
		},
        {
          title: '经度',
          align: 'center',
          dataIndex: 'lng',
        },
        {
          title: '纬度',
          align: 'center',
          dataIndex: 'lat',
        },
        {
          title: '序号',
          align: 'center',
          dataIndex: 'sortNo',
        },
        
        // {
        //   title: '项目描述',
        //   align: 'center',
        //   dataIndex: 'description',
        // },
        {
          title: '状态',
          align: 'center',
          dataIndex: 'state',
          customRender: function(text) {
             let map={
              	'0':'禁用',
                '1':'启用'
            }
            return map[text]
          }
        },
        {
          title: '操作',
          dataIndex: 'action',
          align: 'center',
          scopedSlots: { customRender: 'action' },
        },
      ],
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 项目管理表格数据
      projectManagementList: [],
      // 菜单树选项
      menuOptions: [],
      // 菜单表格树数据
      regionList: [],
      //负责人列表
      userChargeList: [],
      //参与人列表
      userParticipantsList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      modify: false,
      serverOpen: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
      },
      // 表单参数
      form: {
        id:null,
        birth: '',
        districtId:[]
      },
      subForm: {},

      //修改表单
      modifyForm: {},
      projectSdk: {},
      // 表单校验
      rules: {
        name: [{ required: true, message: '项目名称不能为空', trigger: 'change' }],
        description: [{ required: true, message: '项目描述不能为空', trigger: 'change' }],
        state: [{ required: true, message: '状态不能为空', trigger: 'change' }],
        lat: [{ required: true, message: '纬度不能为空', trigger: 'change' }],
        lng: [{ required: true, message: '经度不能为空', trigger: 'change' }],
        locationType: [{ required: true, message: '坐标类型不能为空', trigger: 'change' }],
        districtId: [{ required: true, message: '所属省市区不能为空', trigger: 'change' }],
        state: [{ required: true, message: '项目状态不能为空', trigger: 'change' }],
      },
      serverFormData: {},
    }
  },
  created() {
    this.getList()
    this.listRegion()
  },
  mounted(){
    this.getTree()
  },
  methods: {
    // 地图经纬度回显
    callBack(point){
      this.form.lng=point.lng
      this.form.lat=point.lat
    },
    seleClick(){
      this.$refs.map.show=!this.$refs.map.show
      setTimeout(()=>{
        if(this.form.id != null){
          this.$refs.map.renderMap(this.form.lng,this.form.lat,this.form.lng,this.form.lat)
        }else{
          this.$refs.map.renderMap()
        }
      })
    },
    /** 查询菜单下拉树结构 */
    getTree(){
       getAction("/sys/area/tree").then((response) => {
        this.menuOptions=response.result
      })
    },
    /** 查询菜单列表 */
    listRegion() {
      listRegion(this.queryParams).then((response) => {
        this.regionList = this.handleTree(response.data, 'districtId', 'districtPid')
        this.loading = false
      })
    },
    /** 转换菜单数据结构 */
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children
      }
      return {
        id: node.value,
        label: node.label,
        children: node.children,
      }
    },
    // 关闭弹窗
    close() {
      this.serverOpen = false
      // 查询列表的
      this.getList()
    },
    /** 查询项目管理列表 */
    getList() {
      this.loading = true
      getAction('/productCenter/project/list',this.queryParams).then((response) => {
        this.projectManagementList = response.result.records
        this.total = response.result.total
        this.loading = false
      })
    },
	
    // 取消按钮
    cancel() {
      this.open = false
      this.modify = false
      this.serverOpen = false
      // this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        lat: null,
        name: null,
        lng: null,
        locationType: null,
        districtId: [],
        description: null,
        state: null,
		sortNo: null,
      }
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.queryParams.name=null
      this.queryParams.pageNum = 1
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      // this.form.id=null
      // this.form.name=null
      // this.form.lat=null
      // this.form.lng=null
      // this.form.locationType=null
      // this.form.districtId=null
      // this.form.description=null
      // this.form.state=null
      this.open = true
      this.title = '创建项目'
    },
    handleUpdateTable(row) {
      this.open = true
      this.form.id=row.id
      this.form.name=row.name
      this.form.lat=row.lat
      this.form.lng=row.lng
      this.form.locationType=row.locationType
      this.form.districtId=[row.province,row.city,row.county]
      this.form.description=row.description
      this.form.state=row.state
	  this.form.sortNo=row.sortNo
      // this.$refs.form.resetFields();
      this.title = '修改项目管理'
    },
    handleChange(e,form){
      if(form.length==3){
        this.form.provinceName=form[0].label
        this.form.cityName=form[1].label
        this.form.countyName=form[2].label
      }
    },
    /** 提交按钮 */
    submitForm() {
       let that=this
        this.$refs.form.validate((valid) => {
        if (valid) {
          let params=JSON.parse(JSON.stringify(this.form))
          params.province=params.districtId[0]
          params.city=params.districtId[1]
          params.county=params.districtId[2]
          if (this.form.id != null) {
            let params=JSON.parse(JSON.stringify(this.form))
            editProperty("/productCenter/project/edit",params).then((response) => {
              if(response.code==200){
                message.success('修改成功');
                this.cancel()
                this.getList()
              }
            })
          } else {
            addProduct("/productCenter/project/add",params).then((response) => {
              if(response.code==200){
                message.success('新增成功');
                this.cancel()
                this.getList()
              }
              
            })
          }
        }
      })
    },
    /** table删除按钮操作 */
    handleDel(row) {
          const projectIds = row.id
          deleteProperty("/productCenter/project/delete",{id:projectIds}).then((res)=>{
            this.getList()
            this.$modal.msgSuccess('删除成功')
          })
    },
  },
}
</script>
<style lang="less">
.form{
  display: flex;
  align-items: center;
  justify-content: space-between;
}
.float{
  display: flex;
  justify-content: flex-end;
  align-items: center;
}
.locationType{
  position: relative;
  .seleClick{
    border: 1px solid #4395ff;
    border-radius: 3px;
    padding: 0px 11px;
    color: #000;
    position: absolute;
    right: -60px;
    top: -6px;
    line-height: 30px;
    display: inline-block;
    &:hover{
      cursor: pointer;
    }
  }
}
.pagination{
  display: flex;
  justify-content: flex-end;
  margin-top: 10px;
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
