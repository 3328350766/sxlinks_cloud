<template>
  <a-modal
    :title="title"
    :width="1000"
    :visible="visible"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭"
  >
    <a-spin :spinning="confirmLoading">
      <div class="group-wrap">
        <div class="left">
          <div class="head">
            <a-form layout="inline" @keyup.enter.native="searchQuery" v-bind="layout">
              <a-row :gutter="24">
                <a-col :md="18" :sm="18">
                  <a-form-item label="分组">
                    <a-input placeholder="请输入分组名称" v-model="queryParam.code" style="width:200px"></a-input>
                  </a-form-item>
                </a-col>
                <a-col :md="6" :sm="6">
                  <span style="float: left; overflow: hidden" class="table-page-search-submitButtons">
                    <a-button type="primary" @click="handleAdd" icon="plus" style="margin-right:10px">新增</a-button>
                  </span>
                </a-col>
              </a-row>
            </a-form>
          </div>
          <div class="table">
            <a-table
              :columns="recursionColumns"
              :data-source="recursionData"
              :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
			  
              :expanded-row-keys.sync="recursionExpandedRowKeys"
            >
            <span slot="action" slot-scope="text, record">
              <a-button @click="searchQueryDevice(record)">查询设备</a-button>
			  <!-- <a-button @click="searchQuery(record)">绑定设备</a-button> -->
              <!-- <a-icon type="edit" @click="handleShow()"/> -->
              <!-- <a-icon type="plus" @click="handleShow()" style="margin:0 10px"/>
              <a-icon type="close" @click="handleShow()"/> -->
            </span>
            </a-table>
          </div>
        </div>
        <div class="right">
          <div class="head">
            <a-form layout="inline" @keyup.enter.native="searchQuery">
              <a-row :gutter="24">
                <a-col :md="13" :sm="13">
                  <a-form-item label="右边">
                    <a-input placeholder="请输入名称后自动查询" v-model="queryParam.code" style="width:200px"></a-input>
                  </a-form-item>
                </a-col>
                <a-col :md="11" :sm="11">
                  <span style="float: left; overflow: hidden;display: flex" class="table-page-search-submitButtons">
                    <a-button type="danger" @click="handleAdd">绑定设备</a-button>
                    <a-button type="primary" @click="handleAdd">解绑全部</a-button>
                  </span>
                </a-col>
              </a-row>
            </a-form>
          </div>
          <div class="table">
            <a-table
              ref="table"
              size="middle"
              bordered
              rowKey="id"
              :columns="columns"
              :data-source="lsDeviceData"
              
              :loading="loading"
              
              @change="handleTableChange">
              <span slot="action" slot-scope="text, record">
                <a @click="handleShow()" style="margin:0 10px">查看</a>
                <a @click="handleEdit(record)">解绑</a>
              </span>
            </a-table>
          </div>
        </div>
      </div>
    </a-spin>
  </a-modal>
</template>

<script>
 function listToTreeList(list) { // 将普通列表转换为树结构的列表
    if (!list || !list.length) {
      return []
    }
    let treeListMap = {};
    for (let item of list) {
      treeListMap[item.id] = item
    }
    for (let i = 0; i < list.length; i++) {
      if (list[i].fid && treeListMap[list[i].fid]) {
        if (!treeListMap[list[i].fid].children) {
          treeListMap[list[i].fid].children = []
        }
        treeListMap[list[i].fid].children.push(list[i]);
        list.splice(i, 1);
        i--
      }
    }
    return list
  }

import pick from 'lodash.pick'
import { httpAction } from '@/api/manage'
import { duplicateCheck } from '@/api/api'
import JDictSelectTag from '@/components/dict/JDictSelectTag'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import { getlistFunction, getQueryById, getListProperty, deleteProperty } from '@/api/product'

let validatorCodeTimer = null
const rowSelection = {
  onChange: (selectedRowKeys, selectedRows) => {
    console.log(`selectedRowKeys: ${selectedRowKeys}`, 'selectedRows: ', selectedRows);
  },
  onSelect: (record, selected, selectedRows) => {
	 
	  // searchQueryDevice(selected);
    console.log(record, selected, selectedRows);
  },
  onSelectAll: (selected, selectedRows, changeRows) => {
    console.log(selected, selectedRows, changeRows);
  },
};
export default {
  name: 'DivideDeviceList',
  mixins: [JeecgListMixin],
  components: { JDictSelectTag },
  data() {
    return {
      recursionExpandedRowKeys: [],
      recursionColumns: [
        {
          title: '标识',
          dataIndex: 'code',
          key: 'code',
          width: '40%',
        },
        {
          title: '名称',
          dataIndex: 'name',
          key: 'name',
          width: '30%',
        },
        {
          title: '操作',
          width: '30%',
          dataIndex: 'action',
          align: 'center',
          scopedSlots: { customRender: 'action' },
        },
      ],
      rowSelection,
      recursionData: [
      ],
	 
      queryParam: {},
      title: '操作',
      visible: false,
      model: {},
      labelCol: {
        xs: { span: 24 },
        sm: { span: 5 },
      },
      layout: {
        labelCol: { span: 8 },
        wrapperCol: { span: 14 },
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 },
      },
      confirmLoading: false,
      // validatorRules: {
      //   code: [
      //     { required: true, message: '请输入产品编码' },
      //     {
      //       validator: (rule, value, callback) => {
      //         // 函数消抖的简单实现，防止一段时间内发送多次请求
      //         if (validatorCodeTimer) {
      //           // 停止上次开启的定时器
      //           clearTimeout(validatorCodeTimer)
      //         }
      //         validatorCodeTimer = setTimeout(() => {
      //           duplicateCheck({
      //             tableName: 'sys_position',
      //             fieldName: 'code',
      //             fieldVal: value,
      //             dataId: this.model.id,
      //           })
      //             .then((res) => {
      //               if (res.success) {
      //                 callback()
      //               } else {
      //                 callback(res.message)
      //               }
      //             })
      //             .catch(console.error)
      //         }, 300)
      //       },
      //     },
      //   ],
      //   name: [{ required: true, message: '请输入产品名称' }],
      //   postRank: [{ required: true, message: '请选择职级' }],
      // },
	  lsDeviceData:[
	  		  
	  ],
      // 设备列表表头
        columns: [
          {
            title: '#',
            dataIndex: '',
            key: 'rowIndex',
            width: 60,
            align: 'center',
            customRender: function (t, r, index) {
              return parseInt(index) + 1
            }
          },
		  // {
		  //   title: 'ID',
		  //   align: 'center',
		  //   dataIndex: 'name'
		  // },
          {
            title: '名称',
            align: 'center',
            dataIndex: 'deviceName'
			
          },
          {
            title: '在线状态',
            align: 'center',
            dataIndex: 'activeStatus',
			customRender: function(text) {
			              if (text == "1") {
			                return '在线'
			              }
			              if (text == "0") {
			                return '离线'
			              }
			}
          },

          {
            title: '状态',
            dataIndex: 'action',
            align: 'center',
            scopedSlots: { customRender: 'action' },
          }
        ],
        url: {
          list: '/productCenter/divide/list',
          delete: '/productCenter/divide/unbindById',
          //deleteBatch: '/productCenter/device/deleteBatch',
          exportXlsUrl: '/productCenter/divide/exportXls',
          importExcelUrl: 'productCenter/divide/importExcel',
          add: '/productCenter/divide/add',
          edit: '/productCenter/divide/edit',
        },
      readOnly: false,
      queryParam: {
            name: null,
            pageNo: 1,
            pageSize: 8,
          },
		queryParamDevice: {
		  		divideId:null,
		        name: null,
		        pageNo: 1,
		        pageSize: 8,
		      },
    }
  },
  created() {},
  methods: {
    // 获取list数据
    searchQuery() {
		
      getlistFunction(`/productCenter/divide/list`, this.queryParam).then((res) => {
          if (res.success) {
           this.recursionData=listToTreeList(res.result.records)
            console.log(res.result.records, listToTreeList(res.result.records),'/////');
            // console.log(res.result.records);
            // this.total = res.result.total
          }
        })
    },

// 获取设备list数据
    searchQueryDevice(item) {
		this.lsDeviceData=null;
		this.queryParamDevice.divideId=item.id;
      getlistFunction(`/productCenter/divide/queryAllDeviceByDivideId`, this.queryParamDevice).then((res) => {
          if (res.success) {
           this.lsDeviceData=res.result; //读取的是result,并非redusl.records
           
            // console.log(res.result.records);
            // this.total = res.result.total
          }
        })
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
.group-wrap{
  display: flex;
  justify-content: flex-start;
  .head{
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 10px;
      .ant-form-item-label{
        text-align: center;
      }
      button{
        margin: 0 10px;
      }
    }
    .left{
      flex: 2.5;
    }
    .right{
      margin-left: 8px;
      flex: 3;
    }
}

</style>
