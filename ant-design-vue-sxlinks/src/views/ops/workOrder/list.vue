<template>
  <a-card :bordered="false">
    <div class="app-container">
      <a-row>
        <a-col :span="24">
          <!--搜索框-->
          <a-form
            class="form"
            :model="queryParams"
            ref="queryForm"
            size="small"
            :inline="true"
            v-show="showSearch"
            label-width="68px"
          >
            <a-form-item label="工单编号" prop="orderNumber" :labelCol="{ span: 8 }" :wrapperCol="{ span: 16 }">
              <a-input
                size="mini"
                v-model="queryParams.orderNumber"
                placeholder="请输入工单编号"
                clearable
                style="width: 150px"
                @keyup.enter.native="handleQuery"
              />
            </a-form-item>
            <a-form-item label="工单所属项目" prop="orderNumber" :labelCol="{ span: 8 }" :wrapperCol="{ span: 16 }">
              <a-select ref="select" v-model="queryParams.orderProject" placeholder="请选择项目" style="width:250px">
                <a-select-option :value="item.id" v-for="(item, index) in projectNameList" :key="index">{{
                  item.name
                }}</a-select-option>
              </a-select>
            </a-form-item>
            <a-form-item  style="margin-left:4%">
              <a-space>
                <a-button type="primary" icon="a-icon-search" size="mini" @click="handleQuery">搜索</a-button>
                <a-button icon="a-icon-refresh" size="mini" @click="resetQuery">重置</a-button>
              </a-space>
            </a-form-item>
          </a-form>
        </a-col>
        <div class="but">
          <a-button type="primary" plain icon="a-icon-plus" size="mini" @click="handleAdd">新增 </a-button>
        </div>
        <!-- <a-col :span="3" style="margin-top:5px" :offset="1"> -->
        <!-- 四个按钮 -->
        <!-- <a-row :gutter="10" class="mb8">
            <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar> </a-row
        ></a-col> -->
      </a-row>

      <!-- 列表展示 -->
      <a-table
        v-loading="loading"
        :dataSource="orderManagementList"
        @selection-change="handleSelectionChange"
        :columns="columnsTable"
        :pagination="false"
      >
        <span slot="action" slot-scope="text, scope">
          <a-space>
            <a-button
              type="text"
              @click="sendSingle(scope)"
              size="mini"
              :disabled="scope.orderState == 0 || scope.orderState == 2 || scope.orderState == 3"
              style="padding: 0 5px"
              >派单</a-button
            >
            <a-button
              type="text"
              @click="acceptance(scope)"
              size="mini"
              :disabled="scope.orderState == 0 || scope.orderState == 1 || scope.orderState == 3"
              style="padding: 0 5px"
              >接单</a-button
            >
            <a-button
              type="text"
              @click="toSolve(scope)"
              size="mini"
              :disabled="scope.orderState == 0 || scope.orderState == 1 || scope.orderState == 2"
              style="padding: 0 5px"
              >解决</a-button
            >
            <a-button type="text" @click="handleUpdate(scope)" size="mini" style="padding: 0 5px">修改</a-button>
            <!-- <a href="javascript:;" @click="sendSingle(scope)">派单</a> -->
            <!-- <a href="javascript:;" @click="acceptance(scope)">接单</a> -->
            <!-- <a href="javascript:;" @click="toSolve(scope)">解决</a>
            <a href="javascript:;" @click="handleUpdate(scope)">修改</a> -->
            <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(scope)">
              <a href="javascript:;">删除</a>
            </a-popconfirm>

            <!-- <a-button
              size="mini"
              type="primary"
              icon="a-icon-edit"
              @click="handleUpdate(scope)"
              >派单
            </a-button>
            <a-button
              size="mini"
              type="primary"
              icon="a-icon-edit"
              @click="handleUpdate(scope)"
              >接单
            </a-button>
            <a-button
              size="mini"
              type="primary"
              icon="a-icon-edit"
              @click="handleUpdate(scope)"
              >解决
            </a-button>
            <a-button
              size="mini"
              type="primary"
              icon="a-icon-edit"
              @click="handleUpdate(scope)"
              >修改
            </a-button>
            <a-button
              size="mini"
              type="primary"
              icon="a-icon-edit"
              @click="handleUpdate(scope)"
              >修改
            </a-button> -->
          </a-space>
        </span>
      </a-table>
      <!-- 分页 -->
      <a-pagination
        v-show="total > 0"
        v-model="queryParams.pageNum"
        :total="total"
        show-less-items
        class="pagination"
        :page-size="Number(queryParams.pageSize)"
        :default-current="Number(queryParams.pageNum)"
        @change="getList"
      />
      <!-- 派单对话框 -->
      <a-modal
        :title="title"
        :visible="asingle"
        width="1000px"
        @cancel="cancel"
        append-to-body
        :cancel-button-props="{ style: { display: 'none' } }"
        :ok-button-props="{ style: { display: 'none' } }"
      >
        <div style="height: 50vh">
          <div class="asingle">
            <a-col :span="4" :xs="7">
              <a-tree
                :tree-data="deptOptions"
                :fieldNames="defaultProps"
                :expand-on-click-node="false"
                :filter-node-method="filterNode"
                ref="tree"
                default-expand-all
                @select="treeSelect"
                highlight-current
                @node-click="handleNodeClick"
                node-key="id"
              />
            </a-col>
            <a-col :span="20" :xs="17">
              <a-table
                height="400px"
                v-loading="userLoading"
                :dataSource="userList"
                :columns="columnsAsingle"
                :pagination="false"
                rowKey="id"
                :row-selection="{ selectedRowKeys: selectedRowKeys, onChange: onSelectChange }"
                ref="multipleTable"
              >
              </a-table>
              <a-pagination
                v-show="userTotal > 0"
                v-model="sendParams.pageNo"
                :total="userTotal"
                show-less-items
                class="pagination"
                :page-size="Number(sendParams.pageSize)"
                :default-current="Number(sendParams.pageNo)"
                @change="getPagination"
              />
            </a-col>
          </div>

          <div slot="footer" class="dialog-footer" style="text-align: right">
            <a-button type="primary" @click="sendSingleSubmitForm">派单</a-button>
            <a-button @click="cancel">取 消</a-button>
          </div>
        </div>
      </a-modal>
      <!-- 解决工单对话框 -->
      <a-modal :title="title" :width="500" :visible="solve" @ok="solveSubmitForm" @cancel="cancel" cancelText="关闭">
        <a-form-model ref="form" :model="toSolveForm" :rules="rules">
          <div class="col-2">
            <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="orderCost" label="工单费用">
              <a-input
                placeholder="请输入工单费用"
                type="number"
                v-model="toSolveForm.orderCost"
                :read-only="readOnly"
                style="width: 250px"
              />
            </a-form-model-item>
            <a-form-model-item
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              required
              label="是否开票"
              prop="orderInvoice"
            >
              <j-dict-select-tag
                placeholder="请选择是否开票"
                dictCode="orderInvoice"
                v-model="toSolveForm.orderInvoice"
              />
            </a-form-model-item>
          </div>
        </a-form-model>
      </a-modal>
      <!-- <a-modal :title="title" :visible="solve" width="500px" append-to-body>
        <a-form ref="form" :model="toSolveForm" :rules="rules" label-width="80px">
          <a-form-item label="工单编号" prop="orderNumber" :labelCol="{ span: 8 }" :wrapperCol="{ span: 16 }">
            <a-input v-model="toSolveForm.orderNumber" placeholder="请输入工单编号" :disabled="true" />
          </a-form-item>

          <a-form-item label="所属项目" prop="orderProject" :labelCol="{ span: 8 }" :wrapperCol="{ span: 16 }">
            <a-select
              v-model="toSolveForm.orderProject"
              placeholder="请选择项目"
              clearable
              :disabled="true"
              style="width: 100%"
            >
              <a-option
                v-for="item in projectNameList"
                :key="item.projectId"
                :label="item.projectName"
                :value="item.projectId"
              />
            </a-select>
          </a-form-item>

          <a-form-item label="选择设备" prop="shebei" :disabled="!toSolveForm.shebei">
            <a-select
              v-model="toSolveForm.shebei"
              placeholder="请选择设备"
              clearable
              :labelCol="{ span: 8 }"
              :wrapperCol="{ span: 16 }"
            >
              <a-option v-for="dict in dict.type.shebei" :key="dict.value" :label="dict.label" :value="dict.value" />
            </a-select>
          </a-form-item>

          <a-form-item label="工单费用" prop="orderCost" :disabled="!toSolveForm.orderCost">
            <a-input v-model="toSolveForm.orderCost" placeholder="请输入工单费用信息" />
          </a-form-item>

          <a-form-item label="是否开票" prop="orderInvoice" :disabled="!toSolveForm.orderInvoice">
            <a-select v-model="toSolveForm.orderInvoice" placeholder="请选择工单开票信息" clearable :labelCol="{span:8}" :wrapperCol="{span:16}">
            <a-option v-for="dict in dict.type.slp_order" :key="dict.value" :label="dict.label" :value="dict.value" />
          </a-select> 
          </a-form-item>
          <a-form-item
            label="备注信息"
            prop="orderHandleRemark"
            :disabled="!toSolveForm.orderHandleRemark"
            :labelCol="{ span: 8 }"
            :wrapperCol="{ span: 16 }"
          >
            <a-input v-model="toSolveForm.orderHandleRemark" placeholder="请输入工单备注信息" />
          </a-form-item>
        </a-form>
        <div slot="footer" class="dialog-footer">
          <a-button type="primary" @click="solveSubmitForm">确 定</a-button>
          <a-button @click="cancel">取 消</a-button>
        </div>
      </a-modal> -->
      <!-- 添加或修改工单对话框 -->
      <a-modal :title="title" :visible="open" width="500px" @cancel="cancel" append-to-body>
        <a-form-model
          ref="form"
          :model="form"
          :rules="rules"
          :labelCol="{ span: 8 }"
          :wrapperCol="{ span: 16 }"
          :form="form"
        >
          <a-form-item label="工单编号" prop="orderNumber">
            <a-input v-model="form.orderNumber" placeholder="请输入工单编号" />
          </a-form-item>

          <a-form-item label="所属项目" prop="orderProject">
            <a-select ref="select" v-model="form.orderProject" placeholder="请选择项目">
              <a-select-option :value="item.id" v-for="(item, index) in projectNameList" :key="index">{{
                item.name
              }}</a-select-option>
            </a-select>
          </a-form-item>
          <a-form-item label="备注信息" prop="orderHandleRemark" :labelCol="{ span: 8 }" :wrapperCol="{ span: 16 }">
            <a-input v-model="form.orderHandleRemark" placeholder="请输入工单备注信息" />
          </a-form-item>
        </a-form-model>
        <div slot="footer" class="dialog-footer">
          <a-button type="primary" @click="submitForm">确 定</a-button>
          <a-button @click="cancel">取 消</a-button>
        </div>
      </a-modal>
    </div>
  </a-card>
</template>

<script>
import JDictSelectTag from '@/components/dict/JDictSelectTag'
import { message, Modal } from 'ant-design-vue'
import { getlistFunction, getQueryById, getListProperty, deleteProperty, addProduct, editProperty } from '@/api/product'

export default {
  //字典
  dicts: ['slp_order', 'spl_project'],
  name: 'OrderManagement',
  components: {
    JDictSelectTag,
  },
  data() {
    return {
      labelCol: {
        xs: { span: 24 },
        sm: { span: 5 },
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 },
      },
      single: {
        ifOrderState: 0,
        orderId: '',
        orderDispatched: '',
        orderCost: null,
        orderInvoice: null,
      },
      // 派单表头
      columnsAsingle: [
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
          title: '用户编号',
          align: 'center',
          dataIndex: 'workNo',
        },
        {
          title: '用户名称',
          align: 'center',
          dataIndex: 'username',
        },
        {
          title: '部门',
          align: 'center',
          dataIndex: 'post',
        },
        {
          title: '手机号码',
          align: 'center',
          dataIndex: 'phone',
        },
      ],
      // 表头
      columnsTable: [
        // {
        //   title: '#',
        //   dataIndex: '',
        //   key: 'rowIndex',
        //   width: 60,
        //   align: 'center',
        //   customRender: function (t, r, index) {
        //     return parseInt(index) + 1
        //   },
        // },
        {
          title: '工单编号',
          align: 'left',
          dataIndex: 'orderNumber',
        },
        {
          title: '状态',
          align: 'center',
          dataIndex: 'stateName',
        },
        {
          title: '费用',
          align: 'center',
          dataIndex: 'orderCost',
        },
        {
          title: '开票',
          align: 'center',
          dataIndex: 'invoice',
        },
        {
          title: '所属项目',
          align: 'center',
          dataIndex: 'projectName',
        },
        {
          title: '创建人',
          align: 'center',
          dataIndex: 'createBy',
        },
        {
          title: '创建时间',
          align: 'center',
          //width: 120,
          dataIndex: 'createTime',
        },
        {
          title: '更新时间',
          align: 'center',
          dataIndex: 'updateTime',
        },
        {
          title: '操作',
          dataIndex: 'action',
          align: 'center',
          scopedSlots: { customRender: 'action' },
        },
      ],
      sendParams: {
        deptId: '',
        pageNo: 1,
        pageSize: 5,
        userName: '',
      },
      // 部门名称
      deptName: undefined,
      // 派单用户表格数据
      userList: null,
      //状态名称集合
      stateNameList: [],
      //项目名称集合
      projectNameList: [],
      // 遮罩层
      loading: true,
      userLoading: true,
      // 选中数组
      ids: [],
      // 日期范围
      dateRange: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 用户总条数
      userTotal: 0,
      // 工单表格数据
      orderManagementList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 是否显示解决弹出层
      solve: false,
      // 是否显示派单弹出框
      asingle: false,
      //派单时点击某一行的数据
      deptUser: [],
      // 列信息
      columns: [
        { key: 0, label: `用户编号`, visible: true },
        { key: 1, label: `用户名称`, visible: true },
        { key: 2, label: `用户昵称`, visible: true },
        { key: 3, label: `部门`, visible: true },
        { key: 4, label: `手机号码`, visible: true },
      ],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderNumber: null,
        orderState: null,
        orderCost: null,
        orderInvoice: null,
        orderProject: undefined,
        orderCreator: null,
        orderHandler: null,
        orderAuditor: null,
        orderCreateTime: null,
        orderSendTime: null,
        orderAcceptTime: null,
        orderHandleTime: null,
        orderHandleSrc: null,
        orderHandleRemark: null,
        orderAuditTime: null,
        orderAuditRemark: null,
        orderPoles: null,
        orderIsDelete: null,
        orderUpdateTime: null,
        birth: '',
      },
      // 表单参数
      form: {
        orderId: null,
      },
      // 解决-表单参数
      toSolveForm: {
        ifOrderState: 2,
        orderId: '',
        orderInvoice: undefined,
        orderCost: 0,
      },
      // 表单校验
      rules: {
        orderInvoice: [
          {
            required: true,
            message: '是否开票不能为空',
            trigger: 'blur',
          },
        ],
        orderCost: [
          {
            required: true,
            message: '工单费用不能为空',
            trigger: 'blur',
          },
        ],
      },
      // 树数据
      deptOptions: [],
      defaultProps: { children: 'children', title: 'label', key: 'users' },
      selectedRowKeys: [],
      // {
      //   children: 'children',
      //   users: 'users',
      //   label: 'label',
      // }
    }
  },
  watch: {
    // 根据名称筛选部门树
    // deptName(val) {
    //   this.$refs.tree.filter(val);
    // },
  },
  computed: {},
  created() {
    // this.dateRange = [];
    this.getDeptTree()
    // 所属项目
    this.queryProjectNameList()
    // this.queryStateNameList();
  },
  methods: {
    getPagination(e) {
      this.sendParams.pageNo = e
      this.getList()
    },
    onSelectChange(selectedRowKeys, selectedRows) {
      this.selectedRowKeys = selectedRowKeys
    },
    // 节点单击事件
    handleNodeClick(data) {
      this.queryParams.deptId = data.id
      this.handleUserQuery()
      node.expanded = !node.expanded //在组件点击事件 中使用
    },
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true
      return data.label.indexOf(value) !== -1
    },
    /** 查询部门下拉树结构 */
    getDeptTree() {
      getQueryById('/sys/sysDepart/queryTreeList').then((response) => {
        this.deptOptions = response.result
        if (response.result.length) {
          this.sendParams.deptId = response.result[0].id
          this.getUserList()
        }
      })
    },
    // 点击树事件
    treeSelect(e) {
      this.sendParams.deptId = e[0]
      this.getUserList()
    },
    /** 查询部门员工列表 */
    getUserList() {
      this.userLoading = true
      getQueryById('/sys/user/getUserByDepId', this.sendParams).then((response) => {
        this.userList = response.result.records
        this.userTotal = response.result.total
        this.userLoading = false
      })
    },
    // 接单按钮
    acceptance(row) {
      let that = this
      Modal.confirm({
        title: () => `是否处理编号为${row.orderNumber}的工单`,
        onOk() {
          that.single = {
            ifOrderState: 1,
            orderId: row.orderId,
          }
          editProperty('/orderManagement/edit', that.single).then((res) => {
            if (res.success) {
              message.success('接单成功')
              that.asingle = false
              that.handleQuery()
            }
          })
        },
        onCancel() {
          console.log('Cancel')
        },
        class: 'test',
      })
    },
    // 查询状态名称列表
    queryStateNameList() {
      getStateNameList(this.queryParams).then((response) => {
        this.stateNameList = response
      })
    },
    // 查询项目名称列表
    queryProjectNameList() {
      getQueryById('/productCenter/project/list', { pageNo: 1, pageSize: 50 }).then((res) => {
        if (res.success) {
          this.projectNameList = res.result.records
          this.getList()
        }
      })

      // getProjectNameList().then((response) => {
      //   this.projectNameList = response
      //   console.log(this.projectNameList, '项目名称')
      // })
    },
    /** 查询工单列表 */
    getList() {
      this.loading = true
      let queryParams=JSON.parse(JSON.stringify(this.queryParams))
      getQueryById('/orderManagement/list', queryParams).then((response) => {
        // response.result.records.forEach((i) => {
        //   this.projectNameList.forEach((j) => {
        //     if (j.id == i.orderProject) {
        //       i.orderProjectName = j.name
        //     }
        //   })
        // })
        this.orderManagementList = response.result.records
        this.total = response.result.total
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.solve = false
      this.asingle = false
      // this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        orderId: null,
        orderManagementId: null,
        orderNumber: null,
        orderState: null,
        orderCost: null,
        orderInvoice: null,
        orderProject: undefined,
        orderCreator: null,
        orderHandler: null,
        orderAuditor: null,
        orderCreateTime: null,
        orderSendTime: null,
        orderAcceptTime: null,
        orderHandleTime: null,
        orderHandleSrc: null,
        orderHandleRemark: null,
        orderAuditTime: null,
        orderAuditRemark: null,
        orderPoles: null,
        orderIsDelete: null,
        orderUpdateTime: null,
      }
      // this.resetForm('form')
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 搜索部门用户按钮操作 */
    handleUserQuery() {
      this.queryParams.pageNum = 1
      this.getUserList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.queryParams.orderProject=undefined
      this.queryParams.orderNumber=''
      // this.resetForm('queryForm')
      this.handleQuery()
    },
    //判断选中数量
    selectAll(selection) {
      if (selection.length > 1) {
        selection.length = 1
      }
    },
    // 部门人员多选框选中数据
    deptSelectionChange(selection) {
      this.ids = selection.map((item) => item.orderManagementId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
      this.deptUser = selection
      if (selection.length > 1) {
        let del_row = selection.shift()
        this.$refs.multipleTable.toggleRowSelection(del_row, false)
      }
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.orderManagementId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      // this.reset()
      this.open = true
      this.title = '添加工单'
      this.clearvalue()
    },

    /** 派单按钮操作*/
    sendSingle(row) {
      this.asingle = true
      this.title = '派单'
      this.single = {
        ifOrderState: 0,
        orderId: row.orderId,
        orderDispatched: '',
        orderCost: null,
        orderInvoice: null,
      }
      // this.getUserList()
      // this.reset()
      // const orderManagementId = row.orderId || this.ids
      // getOrderManagement(orderManagementId).then((response) => {
      //   this.toSolveForm = response.data
      //   this.title = '派单'
      // })
    },

    /** 解决按钮回显操作 */
    toSolve(row) {
      ;(this.toSolveForm.orderId = row.orderId), (this.solve = true)
      this.title = '解决工单'
    },
    /**派单弹出框确认按钮 */
    sendSingleSubmitForm() {
      if (this.selectedRowKeys.length == 0) {
        message.error('请选择用户')
        return false
      }
      this.single.orderDispatched = this.selectedRowKeys[0]
      editProperty('/orderManagement/edit', this.single).then((res) => {
        if (res.success) {
          this.selectedRowKeys = []
          message.success('派单成功')
          this.asingle = false
          this.handleQuery()
        }
      })
    },
    /**解决弹出框提交按钮*/
    solveSubmitForm() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          editProperty('/orderManagement/edit', this.toSolveForm).then((res) => {
            if (res.success) {
              message.success('解决成功')
              this.solve = false
              this.handleQuery()
            }
          })
          // this.toSolveForm.ifOrderState = 2
          // console.log(this.toSolveForm)
          // updateOrderManagement(this.toSolveForm).then((response) => {
          //   this.$modal.msgSuccess('解决工单成功')
          //   this.solve = false
          //   this.toSolveForm.ifOrderState = null
          //   this.getList()
          // })
        }
      })
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      getQueryById('/orderManagement/query/' + row.orderId).then((res) => {
        res.result.orderProject = Number(res.result.orderProject)
        res.result.ifOrderState = 3
        this.form = res.result
      })
      this.open = true
      this.title = '修改工单'
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          if (this.form.orderId != null) {
            editProperty('/orderManagement/edit', this.form).then((res) => {
              this.$message.success('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addProduct('/orderManagement/add', this.form).then((res) => {
              this.$message.success('添加成功')
              this.open = false
              this.getList()
            })
            // addOrderManagement(this.form).then((response) => {
            //   this.$modal.msgSuccess('新增成功')
            //   this.open = false
            //   this.getList()
            // })
          }
        }
      })
    },
    clearvalue() {
      this.form = {}
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const projectIds = row.orderId
      deleteProperty('/orderManagement/remove/' + projectIds).then((res) => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download(
        'iot/orderManagement/export',
        {
          ...this.queryParams,
        },
        `iot_${new Date().getTime()}.xlsx`
      )
    },
  },
}
</script>
<style lang="less">
.but{
  width: 100%;
  margin-bottom: 10px;
  margin-top: -10px;
  display: flex;
  justify-content: flex-end;
}
.asingle {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: space-between;
}
.head-container {
  display: flex;
  justify-content: flex-start;
  align-items: center;
}
//main-container全局样式
.app-container {
  padding: 20px;
  .form {
    display: flex;
    align-items: center;
    justify-content: flex-start;
  }
}
.pagination {
  display: flex;
  justify-content: flex-end;
  margin-top: 10px;
}
</style>
