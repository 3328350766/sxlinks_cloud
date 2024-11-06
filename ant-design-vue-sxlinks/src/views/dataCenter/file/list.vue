<template>
  <a-card :bordered="false">
    <div class="app-container">
      <!--查询条件-->
      <div class="serach">
        <a-row :gutter="24">
          <a-col :span="20">
            <a-form
              :model="queryParams"
              ref="queryForm"
              size="small"
              :inline="true"
              v-show="showSearch"
              class="queryForm"
              label-width="68px"
            >
              <a-form-item label="名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-input
                  v-model="queryParams.fileName"
                  placeholder="请输入名称"
                  clearable
                  @keyup.enter.native="handleQuery"
                />
              </a-form-item>
              <a-form-item label="文件类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <j-dict-select-tag
                  placeholder="请选择文件类型"
                  dictCode="file_type"
                  v-model="queryParams.fileType"
                  width="150px"
                />
              </a-form-item>
              <a-form-item label="创建时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-date-picker v-model="queryParams.fileCreateTime" format="YYYY-MM-DD" placeholder="请选择创建时间" />
              </a-form-item>
              <a-form-item>
                <a-button
                  type="primary"
                  icon="el-icon-search"
                  size="mini"
                  style="margin-right: 15px"
                  @click="handleQuery"
                  >搜索</a-button
                >
                <a-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</a-button>
              </a-form-item>
            </a-form>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="24">
            <div class="button">
              <a-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd">新增</a-button>
            </div>
          </a-col>
        </a-row>
        <!--按钮-->
      </div>

      <!--展示列表-->
      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        :columns="columnsList"
        :dataSource="fileManagementList"
        :pagination="false"
        @change="handleTableChange"
      >
        <template slot="action" slot-scope="text, record">
          <a-space>
          <!-- <a @click="handleUpdate(record)">编辑</a> -->
          <a-menu-item>
						<a @click="downUpdate(record)">下载</a>
						<a-divider type="vertical"/>
            <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
              <a>删除</a>
            </a-popconfirm>
          </a-menu-item>
        </a-space>
        </template>
        <template slot="idslot" slot-scope="text, record">
          <a-tooltip>
            <template #title>{{ record.id }}</template>
            {{ record.id }}
          </a-tooltip>
        </template>
        
      </a-table>
      <a-pagination
        v-show="total > 0"
        v-model="queryParams.pageNo"
        :total="total"
        class="pagination"
        :page-size="Number(queryParams.pageSize)"
        :default-current="Number(queryParams.pageNo)"
        @change="getPagination"
      />

      <!-- 添加或修改文件信息对话框 -->

      <a-modal :title="title" :visible="open" width="500px" append-to-body>
        <a-form-item label="文件url" prop="fileName" :labelCol="{ span: 3 }" :wrapperCol="{ span: 21 }">
          <a-upload
            :before-upload="beforeUpload"
            :remove="handleRemove"
            :multiple="false"
            :file-list="fileList"
            :accept="accept"
          >
            <a-button> <a-icon type="upload" /> 上传 </a-button>
          </a-upload>
        </a-form-item>
        <div slot="footer" class="dialog-footer">
          <a-button type="primary" @click="submitForm">确 定</a-button>
          <a-button @click="cancel">取 消</a-button>
        </div>
      </a-modal>
    </div>
  </a-card>
</template>

<script>
function downloadIamge(url, fileName) {
  //下载图片地址和图片名
  const a = document.createElement('a');
	      a.style.display = 'none';
	      a.setAttribute('target', '_blank');
	      /*
	       * download的属性是HTML5新增的属性
	       * href属性的地址必须是非跨域的地址，如果引用的是第三方的网站或者说是前后端分离的项目(调用后台的接口)，这时download就会不起作用。
	       * 此时，如果是下载浏览器无法解析的文件，例如.exe,.xlsx..那么浏览器会自动下载，但是如果使用浏览器可以解析的文件，比如.txt,.png,.pdf....浏览器就会采取预览模式
	       * 所以，对于.txt,.png,.pdf等的预览功能我们就可以直接不设置download属性(前提是后端响应头的Content-Type: application/octet-stream，如果为application/pdf浏览器则会判断文件为 pdf ，自动执行预览的策略)
	       */
	      fileName && a.setAttribute('download', fileName);
	      a.href = url;
	      document.body.appendChild(a);
	      a.click();
	      document.body.removeChild(a);
}
import {
  getlistFunction,
  Upload,
  postAction,
  deleteProperty,
  putAction
  // listFileManagement
  // getFileManagement,
  // delFileManagement,
  // addFileManagement,
  // updateFileManagement,
} from '@/api/file/fileManagement'
import JDictSelectTag from '@/components/dict/JDictSelectTag'
// import Child from "@/components/FileUpload";
export default {
  name: 'fileManagement',
  // 字典
  components: {
    JDictSelectTag,
  },
  data() {
    return {
      filrQuery: {},
      accept: [
        '.doc',
        '.xls',
        '.ppt',
        '.txt',
        '.pdf',
        '.mp4',
        '.rmvb',
        '.avi',
        '.mp3',
        '.wma',
        '.wav',
        '.flac',
        '.mkv',
        '.jpg',
        '.png',
        '.jpeg',
        '.gif',
      ],
      fileType: {
        '.doc': 4,
        '.xls': 4,
        '.ppt': 4,
        '.txt': 4,
        '.mp4': 2,
        '.rmvb': 2,
        '.avi': 2,
        '.mp3': 3,
        '.wma': 3,
        '.wav': 3,
        '.flac': 3,
        '.mkv': 1,
        '.jpg': 1,
        '.png': 1,
        '.gif': 1,
        '.jpeg': 1,
      },
      fileList: [],
      columnsList: [
		  {
		    title: '文件名称',
		    align: 'left',
		    dataIndex: 'fileName',
		    // customRender: function (text) {
		    //     if(text.fileType==1){
		    //       return <a-image src={{text.fileUrl}}/>
		    //     }else{
		    //       return null
		  
		    //     }
		    //   console.log(text,'text')
		    // }
		    // customRender: function (text) {
		    //     return text==0?"未发布":'已发布'
		    //   }
		  },
        {
          title: '文件id',
          align: 'left',
          dataIndex: 'id',
          //   width: 100,
          //   onCell: () => {
          //     return {
          //       style: {
          //         maxWidth: 100,
          //         overflow: 'hidden',
          //         whiteSpace: 'nowrap',
          //         textOverflow: 'ellipsis',
          //         cursor: 'pointer',
          //       },
          //     }
          //   },
          //   scopedSlots: { customRender: 'idslot' },
        },

        // {
        //   title: '文件预览',
        //   align: 'center',
        //   dataIndex: 'fileUrl',
        //   width: 120,
        //   scopedSlots: { customRender: 'avatarslot' },
        // },
        
        {
          title: '文件大小(kb)',
          align: 'center',
          dataIndex: 'fileSize',
        },
        {
          title: '文件类型',
          align: 'center',
          dataIndex: 'fileTypeName',
        },
        {
          title: '创建人',
          align: 'center',
          dataIndex: 'createBy',
        },
        {
          title: '创建时间',
          align: 'center',
          dataIndex: 'createTime',
        },
        {
          title: '操作',
          dataIndex: 'action',
          align: 'center',
          width: 100,
          scopedSlots: { customRender: 'action' },
        },
      ],
      labelCol: {
        xs: { span: 24 },
        sm: { span: 7 },
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 14 },
      },
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 文件信息表格数据
      fileManagementList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      updateOpen: false,
      // 查询参数
      queryParams: {
        pageNo: 1,
        pageSize: 6,
        fileId: null,
        fileType: undefined,
        fileSize: null,
        fileCreateUser: null,
        fileCreateTime: '',
      },
      // 表单参数
      form: {},
    }
  },
  created() {
    this.getList()
  },
  methods: {
    downUpdate(r) {
      downloadIamge(r.fileUrl, r.fileName)
    },
    beforeUpload(file, fileList) {
      var isLt50M
      if (file.type.includes('mp4')) {
        isLt50M = file.size / 1024 / 1024 < 50
      } else {
        isLt50M = file.size / 1024 / 1024 < 20
      }
      if (!isLt50M) {
        this.$message.error(file.name + '文件大小超出限制，请修改后重新上传')
        return false
      }
      if (this.fileList.length) {
        this.$message.warn('只允许上传一个文件,如需要更换请先删除')
        return false
      }
      // this.queryParams.
      let formData = new FormData()
      formData.append('file', file)
      this.fileList = [...this.fileList, file]
      console.log(this.fileList, 'fffffffffffffffffffffff')
      Upload('/file/upload', formData).then((res) => {
        if (res.success) {
          if (res.success) {
            this.param = res.result
            this.$message.success(res.message)
          } else {
            this.$message.warning(res.message)
          }
        }
      })
      return false
    },
    // 文件移除
    handleRemove(file) {
      const index = this.fileList.indexOf(file)
      const newFileList = this.fileList.slice()
      // this.param.splice(index, 1)
      newFileList.splice(index, 1)
      this.fileList = newFileList
    },
    getPagination(e) {
      this.queryParams.pageNo = e
      this.getList()
    },
    /** 查询文件信息列表 */
    getList() {
      this.loading = true
      getlistFunction(`/file/list`, this.queryParams).then((response) => {
        response.result.records.forEach((element) => {
          if (element.extendType) {
            element.file = './file/' + element.extendType.substr(1) + '.png'
          }
        })
        this.fileManagementList = response.result.records
        this.total = response.result.total
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.updateOpen = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        fileId: null,
        fileType: null,
        fileSize: null,
        fileCreateUser: null,
        fileCreateTime: '',
        delFlag: null,
      }
      // this.resetForm('form')
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNo = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      ;(this.queryParams = {
        pageNo: 1,
        pageSize: 6,
        fileId: null,
        fileType: undefined,
        fileSize: null,
        fileCreateUser: null,
        fileCreateTime: '',
      }),
        this.getList()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.fileId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    onSuccess(fileList) {
      //this.form.fileName= fileList.name
      this.form.fileSize = fileList.size
      this.form.fileUrl = fileList.rul
      this.form.fileType = '3'
      this.form.fileUrl = this.form.imgUrl
      // var b = response.data.name.substr(s.lastIndexOf(".")+1);
      var b = fileList.name.substr(0, fileList.name.indexOf('_'))
      this.form.fileName = b
      //}
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = '添加文件信息'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.open = true
      this.title = '修改文件信息'
      this.fileList=[]
    },
    /** 提交按钮 */
    submitForm() {
      if (this.fileList.length == 0) {
        this.$message.warning('请上传资源')
        return
      }
      this.filrQuery = this.param
      this.filrQuery.fileType = this.fileType[this.param.extendType]
      console.log(this.filrQuery)
      if(this.title = '添加文件信息'){
        postAction('/file/add', this.filrQuery).then((res) => {
          if (res.success) {
            this.open = false
            this.$message.success(res.message)
            this.getList()
          }
        })
      }else{
        putAction('/file/edit',this.fileList).then((res)=>{
          if (res.success) {
            this.open = false
            this.$message.success(res.message)
            this.getList()
          }
        })
      }
      

      // if (this.form.fileId != null) {
      //   updateFileManagement(this.form).then((response) => {
      //     this.$modal.msgSuccess('修改成功')
      //     this.updateOpen = false
      //     this.getList()
      //   })
      // } else {
      //   addFileManagement(this.form).then((response) => {
      //     this.$modal.msgSuccess('新增成功')
      //     this.open = false
      //     this.getList()
      //   })
      // }
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      deleteProperty('/file/delete',{id:row}).then((res)=>{
        if(res.success){
          this.getList()
          this.$message.success(res.message)

        }

      })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download(
        'file/fileManagement/export',
        {
          ...this.queryParams,
        },
        `fileManagement_${new Date().getTime()}.xlsx`
      )
    },
  },
}
</script>
<style scoped lang="less">
.queryForm {
  display: flex;
  justify-content: flex-start;
  align-items: center;
}
.button {
  justify-content: flex-end;
  display: flex;
  margin-bottom: 10px;
}
.search {
}
video {
  width: 300px;
  height: 54px;
}
.pagination {
  text-align: right;
  margin: 10px 0 0 0;
}
</style>
