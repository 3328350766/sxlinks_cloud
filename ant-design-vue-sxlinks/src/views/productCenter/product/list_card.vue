<template>
  <a-card :bordered="false">

    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">

          <a-col :md="6" :sm="8">
            <a-form-item label="产品编码">
              <a-input placeholder="请输入产品编码" v-model="queryParam.productCode"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="产品名称">
              <a-input placeholder="请输入产品名称" v-model="queryParam.productName"></a-input>
            </a-form-item>
          </a-col>
		  <a-col :md="6" :sm="8">
		    <a-form-item label="节点类型">
		      <j-dict-select-tag v-model="queryParam.nodeType" placeholder="请选择节点" dictCode="node_type"/>
		    </a-form-item>
		  </a-col>
          
          <a-col :md="6" :sm="8">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
              <a @click="handleToggleSearch" style="margin-left: 8px">
                {{ toggleSearchStatus ? '收起' : '展开' }}
                <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>
              </a>
            </span>
          </a-col>

		<template v-if="toggleSearchStatus">
            <a-col :md="6" :sm="8">
              <a-form-item label="网络类型">
                <j-dict-select-tag v-model="queryParam.networkType" placeholder="请选择网络" dictCode="network_type"/>
              </a-form-item>
            </a-col>

          </template>
		  
        </a-row>
      </a-form>
    </div>

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
			
      <!-- <a-button type="primary" icon="download" @click="handleExportXls('产品表')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload> -->
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel">
            <a-icon type="delete"/>
            删除
          </a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作
          <a-icon type="down"/>
        </a-button>
      </a-dropdown>
    </div>
		
		<div class="app-list">
		  <a-list
		    :grid="{ gutter: 24, lg: 4, md: 2, sm: 1, xs: 1 }"
		    :data-source="data">
		    <a-list-item slot="renderItem" slot-scope="item, index">
		      <a-card :hoverable="true">
		        <div>
		          <div class="meta-cardInfos">
		            <div class="title">
                  <img src="./images/product.png" alt="">
                  <div class="right">
                    <p>{{item.productName}}</p>
                    <p>{{item.productDesc}}</p>
                  </div>
                </div>
                <div class="contant">
                  <div class="list">
                    <p>节点类型</p>
                    <span>{{nodeType[item.nodeType]}}</span>
                  </div>
                  <div class="list">
                    <p>发布状态</p>
                    <code><i :class="item.status==0?'coler':''"></i>{{item.status==0?'未发布':'已发布'}}</code>
                  </div>
                  <div class="list">
                    <p>产品类型</p>
                    <code>{{item.protocolType}}</code>
                  </div>
                </div>
		          </div>
		        </div>
            
		        <template class="ant-card-actions" slot="actions">
              <a>
		            <a-icon type="eye-invisible" @click="detailsClick(item.id,item.productCode)" />
		          </a>
              <a>
		            <a-icon type="edit" @click="editClick(item)"/>
		          </a>
		          <a>
		            <a-icon type="download"/>
		          </a>
		          <!-- <a>
		            <a-icon type="share-alt"/>
		          </a> -->
		          <a>
		            <a-dropdown>
		              <a class="ant-dropdown-link" href="javascript:;">
		                <a-icon type="ellipsis"/>
		              </a>
		              <a-menu slot="overlay">
		                <a-menu-item>
		                  <a href="javascript:;" @click="delClick(item.id)">删除</a>
		                </a-menu-item>
		                <a-menu-item>
		                  <a href="javascript:;">激活</a>
		                </a-menu-item>
		                <a-menu-item>
		                  <a href="javascript:;">禁用</a>
		                </a-menu-item> 
		              </a-menu>
		            </a-dropdown>
		          </a>
		        </template>
		      </a-card>
		    </a-list-item>
		  </a-list>
      <a-pagination v-model="current" :total='total' show-less-items class="pagination"  :page-size="Number(queryParam.pageSize)" :default-current="Number(queryParam.pageNo)" @change="changePagination"/>
		</div>
		<a-modal
      title="编辑报警"
      :width="900"
      :visible="policevisible"
      :confirm-loading="policeconfirmLoading"
      @ok="policehandleOk"
      @cancel="policehandleCancel"
    >
      <div class="police">
        <div class="policeName">
          <span>告警名称：</span>
          <a-input placeholder="输入报警名称" style="width:200px" v-model="queryParam.code"></a-input>
        </div>
        <div class="condition">
          <div class="conditionHead">
            <p>触发条件</p>
            <a-tooltip placement="topLeft" title="Prompt Text">
              <a-icon type="question" />
            </a-tooltip>
            <a-switch checked-children="开通防抖" un-checked-children="关闭防抖" default-checked />
          </div>
          <div class="trigger">
            <div class="triggerList" v-for="(item,index) in triggerList" :key="index">
              <H2>{{item.name}} <a href="javacsript:;" class="identification" @click="triggerDelete(index)">删除</a></H2> 
              <div class="select">
                  <a-select default-value="lucy" style="width: 200px">
                    <a-select-option value="jack">
                      Jack
                    </a-select-option>
                    <a-select-option value="lucy">
                      Lucy
                    </a-select-option>
                    <a-select-option value="disabled">
                      Disabled
                    </a-select-option>
                    <a-select-option value="Yiminghe">
                      yiminghe
                    </a-select-option>
                  </a-select>
                  <a-select default-value="lucy" style="width: 200px">
                    <a-select-option value="jack">
                      Jack
                    </a-select-option>
                    <a-select-option value="lucy">
                      Lucy
                    </a-select-option>
                    <a-select-option value="disabled">
                      Disabled
                    </a-select-option>
                    <a-select-option value="Yiminghe">
                      yiminghe
                    </a-select-option>
                  </a-select>
              </div>
              <div class="triggerCol" v-for="(i,index2) in item.list" :key="index2">
                <a-input placeholder="过滤条件KEY" style="width:200px" />
                <a-select default-value="lucy" style="width: 200px" >
                    <a-select-option value="jack">
                      Jack
                    </a-select-option>
                    <a-select-option value="lucy">
                      Lucy
                    </a-select-option>
                    <a-select-option value="disabled">
                      Disabled
                    </a-select-option>
                    <a-select-option value="Yiminghe">
                      yiminghe
                    </a-select-option>
                  </a-select>
                   <a-input placeholder="过滤条件值" style="width:200px" />
                   <a href="javascript:;" class="identification" @click="identificationDelete(index,index2)" >删除</a>
              </div>
              <a href="javascript:;" class="identification" style="margin: 10px 20px;" @click="addidentification(index)">添加</a>
            </div>
            <a-button type="primary" icon="plus" class="addTrigger" @click="addTrigger">新增触发器</a-button>
          </div>
        </div>
        <div class="condition">
          <div class="conditionHead">
            <p>转换</p>
            <a-tooltip placement="topLeft" title="Prompt Text">
              <a-icon type="question" />
            </a-tooltip>
          </div>
          <div class="trigger">
            <div class="triggerList" v-for="(item,index) in triggerList" :key="index" style="padding-top: 10px">
              <div class="triggerCol" v-for="(i,index2) in item.list" :key="index2">
                <a-input placeholder="过滤条件KEY" style="width:200px" />
                <a-select default-value="lucy" style="width: 200px" >
                    <a-select-option value="jack">
                      Jack
                    </a-select-option>
                    <a-select-option value="lucy">
                      Lucy
                    </a-select-option>
                    <a-select-option value="disabled">
                      Disabled
                    </a-select-option>
                    <a-select-option value="Yiminghe">
                      yiminghe
                    </a-select-option>
                  </a-select>
                   <a-input placeholder="过滤条件值" style="width:200px" />
                   <a href="javascript:;" class="identification" @click="identificationDelete(index,index2)" >删除</a>
              </div>
              <a href="javascript:;" class="identification" style="margin: 10px 20px;" @click="addidentification(index)">添加</a>
            </div>
          </div>
        </div>
        <div class="condition">
          <div class="conditionHead">
            <p>执行动作</p>
            <a-tooltip placement="topLeft" title="Prompt Text">
              <a-icon type="question" />
            </a-tooltip>
            <a-switch checked-children="开通防抖" un-checked-children="关闭防抖" default-checked />
          </div>
          <div class="trigger">
            <div class="triggerList" v-for="(item,index) in triggerList" :key="index">
              <H2>{{item.name}} <a href="javacsript:;" class="identification" @click="triggerDelete(index)">删除</a></H2> 
              <div class="triggerCol" v-for="(i,index2) in item.list" :key="index2">
                <a-input placeholder="过滤条件KEY" style="width:200px" />
                <a-select default-value="lucy" style="width: 200px">
                    <a-select-option value="jack">
                      Jack
                    </a-select-option>
                    <a-select-option value="lucy">
                      Lucy
                    </a-select-option>
                    <a-select-option value="disabled">
                      Disabled
                    </a-select-option>
                    <a-select-option value="Yiminghe">
                      yiminghe
                    </a-select-option>
                  </a-select>
                   <a-input placeholder="过滤条件值" style="width:200px" />
                   <a href="javascript:;" class="identification" @click="identificationDelete(index,index2)" >删除</a>
              </div>
              <a href="javascript:;" class="identification" style="margin: 10px 20px;" @click="addidentification(index)">添加</a>
            </div>
            <a-button type="primary" icon="plus" class="addTrigger" @click="addTrigger">新增触发器</a-button>
          </div>
        </div>
      </div>
    </a-modal>
		<!--table块状结束-->
    <!-- 表单区域 -->
    <ProductEdit ref="ProductEdit" @ok="modalFormOk"></ProductEdit>
    <!--开始-->
    <!-- 详情弹窗 -->
    <a-modal
      :width="900"
      :visible="visible"
      :confirm-loading="confirmLoading"
      @ok="handleOk"
      @cancel="handleCancel"
    >
      <div>
        <a-tabs default-active-key="1" @change="callback" type="card">
          <a-tab-pane key="1" tab="产品信息">
            <div class="product">产品信息</div>
            <div class="table">
              <div class="col">
                <div class="colName">
                  <span class="sign">产品ID</span>
                  <code>{{productDetails.id}}</code>
                  <span class="sign">网络类型	</span>
                  <code>{{productDetails.networkType}}</code>
                  <span class="sign">节点类型	</span>
                  <code>{{productDetails.nodeType}}</code>
                </div>
                <div class="colName">
                  <span class="sign">产品编码	</span>
                  <code>{{productDetails.productCode}}</code>
                  <span class="sign">产品名称	</span>
                  <code>{{productDetails.productName	}}</code>
                  <span class="sign">协议编码	</span>
                  <code>{{productDetails.protocolCode	}}</code>
                </div>
                <div class="colName">
                  <span class="sign">协议类型		</span>
                  <code>{{productDetails.protocolType}}</code>
                  <span class="sign">发布状态		</span>
                  <code>{{productDetails.status}}</code>
                  <span class="sign">传输协议		</span>
                  <code>{{productDetails.transportList	}}</code>
                </div>
                <div class="colName explain">
                  <span class="sign">描述</span>
                  <code>{{productDetails.productDesc}}</code>
                </div>
              </div>
            </div>
            
          </a-tab-pane>
          <a-tab-pane key="2" tab="物模型" force-render style="position: relative;">
            <div class="modelBut">
              <!-- <a-button>快速导入</a-button>
              <a-button>物模型TSL</a-button> -->
            </div>
            <a-tabs default-active-key="1">
              <a-tab-pane key="1" tab="属性定义">
                <div class="wrapTab">
                    <div class="attr">
                        <p>属性定义</p>
                        <div class="attrBut">
                          <!-- <a-button>导入属性</a-button> -->
                          <a-button type="primary" @click="addattrBut">添加</a-button>
                        </div>
                      </div>
                      <div class="" v-if="attrPageData">
                        <a-input-search placeholder="请输入属性名称" v-model="property.name" style="width: 200px;margin-bottom:10px;margin-top:10px" @search="getProperty" />
                        <a-table
                          ref="table"
                          size="middle"
                          bordered
                          rowKey="id"
                          :columns="attrColumns"
                          :dataSource="attrdata"
                          :loading="loading"
                          :pagination="attrPageData"
                          >
                          <span slot="action" slot-scope="text, record">
                            <a @click="attrEdit(record)">编辑</a>
                              <a-menu>
                              <a-menu-item>
                                      <a-popconfirm title="确定删除吗?" @confirm="() => attrDelete(record.id)">
                                        <a>删除</a>
                                      </a-popconfirm>
                                    </a-menu-item>
                              </a-menu>
                          </span>

                        </a-table>
    
                      </div>
                </div>
              </a-tab-pane>
              <a-tab-pane key="2" tab="功能定义" >
                <div class="wrapTab">
                    <div class="attr">
                        <p>功能定义</p>
                        <div class="attrBut">
                          <a-button type="primary" @click="addFunctionBut">添加</a-button>
                        </div>
                      </div>
                      <div class="">
                        <a-table
                          ref="table"
                          size="middle"
                          bordered
                          rowKey="id"
                          :columns="attrColumns"
                          :dataSource="functionData"
                          :loading="loading"
                          @change="handleTableChange">

                          <span slot="action" slot-scope="text, record">
                            <a @click="functionEdit(record)">编辑</a>
                              <a-menu>
                              <a-menu-item>
                                      <a-popconfirm title="确定删除吗?" @confirm="() => functionDelete(record.id)">
                                        <a>删除</a>
                                      </a-popconfirm>
                                    </a-menu-item>
                              </a-menu>
                          </span>

                        </a-table>
                      </div>
                </div>
              </a-tab-pane>
              
            </a-tabs>
			
          </a-tab-pane>
        </a-tabs>
      </div>
    </a-modal>
    <!-- 新增属性model -->
    <PhysicalAttrEdit ref="PhysicalAttrEdit"></PhysicalAttrEdit>
    <!-- 新增功能定义model -->
    <PhysicalFuncEdit ref="PhysicalFuncEdit"></PhysicalFuncEdit>
  </a-card>
</template>

<script>
import ProductEdit from './edit'
import PhysicalAttrEdit from './physical_attr_edit'
import PhysicalFuncEdit from './physical_func_edit.vue'
import PhysicalEdit from './physical_edit'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import JDictSelectTag from '@/components/dict/JDictSelectTag'
import { getlistFunction, getQueryById, getListProperty, deleteProperty } from '@/api/product'

export default {
  name: 'ProductList',
  mixins: [JeecgListMixin],
  components: {
    ProductEdit,
    PhysicalEdit,
    JDictSelectTag,
    PhysicalAttrEdit,
    PhysicalFuncEdit,
  },
  data() {
    return {
      nodeType: {
        DIRECT: '直连设备',
        GATEWAY: '网关设备',
        SUB: '网关子设备',
      },
      ipagination: {
        current: 1,
        pageSize: 8,
        showQuickJumper: true,
        showSizeChanger: true,
        total: 0,
      },
      // 物模型 属性 列表
      property: {
        productCode: '',
        name: '',
        pageNo: 1,
        pageSize: 8,
      },
      // 物模型 产品 列表
      listFunction: {
        productCode: '',
        pageNo: 1,
        pageSize: 8,
      },
      attrdataTotal: 0,
      attrPageData: {
        current: 1, //当前页码
        pageSize: 8, // 每页数据条数
        // showTotal: () => (
        //   <span>总共{total}项</span>
        // ),
        total: 0, // 总条数
        onChange: (page) => this.handlePageChange(page), //改变页码的函数
        hideOnSinglePage: false,
        showSizeChanger: false,
      },
      // 产品详情
      productDetails: {
        networkType: null,
        nodeType: null,
        productCode: null,
        productDesc: null,
        productName: null,
        protocolCode: null,
        protocolType: null,
        status: null,
        transportList: null,
        id: null,
      },
      total: 0,
      queryParam: {
        productCode: null,
        productName: null,
        nodeType: null,
        networkType: null,
        pageNo: 1,
        pageSize: 20,
      },
      data: [],
      triggerList: [
        {
          name: '触发器1',
          list: [
            {
              value: '10',
            },
          ],
        },
      ],
      current: 1,
      description: '产品表管理页面',
      // 属性定义表头
      attrColumns: [
        {
          title: '名称',
          dataIndex: 'name',
        },
        {
          title: '属性编码',
          dataIndex: 'identifier',
        },
        {
          title: '数据类型',
          dataIndex: 'dataType',
        },
        // {
        //   title: '描述	',
        //   dataIndex: 'funcDesc',
        // },
        {
          title: '单位',
          dataIndex: 'unitName',
        },

        {
          title: '操作',
          dataIndex: 'action',
          align: 'center',
          scopedSlots: { customRender: 'action' },
        },
      ],
      attrdata: [],
      functionData: [],
      // 表头
      columns: [
        {
          title: '事件标识',
          align: 'center',
          dataIndex: 'name',
        },
        {
          title: '属性名称',
          align: 'center',
          dataIndex: 'code',
        },
        {
          title: '数据类型',
          align: 'center',
          dataIndex: 'dataType',
        },
        {
          title: '属性值来源',
          align: 'center',
          dataIndex: 'nodeType_dictText',
        },
        {
          title: '是否只读',
          align: 'center',
          dataIndex: 'network_dictText',
        },
        {
          title: '说明',
          align: 'center',
          dataIndex: 'protocol_dictText',
        },
        {
          title: '操作',
          dataIndex: 'action',
          align: 'center',
          scopedSlots: { customRender: 'action' },
        },
      ],
      url: {
        list: '/productCenter/product/list',
        delete: '/productCenter/product/delete',
        deleteBatch: '/productCenter/product/deleteBatch',
        exportXlsUrl: '/productCenter/product/exportXls',
        importExcelUrl: 'productCenter/product/importExcel',
      },
      visible: false,
      confirmLoading: false,
      policevisible: false,
      policeconfirmLoading: false,
    }
  },
  computed: {
    importExcelUrl: function () {
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
    },
  },
  mounted() {
    this.searchQuery()
  },
  methods: {
    // 添加功能定义
    addFunctionBut() {
      this.$refs.PhysicalFuncEdit.type = 'add'
      this.$refs.PhysicalFuncEdit.title = '新增'
      this.$refs.PhysicalFuncEdit.visible = true
      //将产品编码传入
      this.$refs.PhysicalFuncEdit.model.productCode = this.productDetails.productCode
    },
    // 获取物模型 功能定义 编辑
    functionEdit(item) {
      let data = JSON.parse(JSON.stringify(item))
      this.$refs.PhysicalFuncEdit.visible = true
      this.$refs.PhysicalFuncEdit.title = '编辑'
      this.$refs.PhysicalFuncEdit.type = 'edit'
      this.$refs.PhysicalFuncEdit.model = data
    },
    // 获取物模型 功能定义 删除
    functionDelete(id) {
      deleteProperty(`/productCenter/product/deleteFunction?functionId=${String(id)}`).then((res) => {
        if (res.success) {
          this.$message.success(res.message)
          this.getFunctionData()
        } else {
          this.$message.warning(res.message)
        }
      })
    },
    // 添加属性定义
    addattrBut() {
      this.$refs.PhysicalAttrEdit.type = 'add'
      this.$refs.PhysicalAttrEdit.title = '新增'
      this.$refs.PhysicalAttrEdit.visible = true
      //将产品编码传入
      this.$refs.PhysicalAttrEdit.model.productCode = this.productDetails.productCode
    },
    // 获取物模型 属性列表 编辑
    attrEdit(item) {
      console.log(item)
      console.log(this.attrdata)
      let data = JSON.parse(JSON.stringify(item))
      this.$refs.PhysicalAttrEdit.visible = true
      this.$refs.PhysicalAttrEdit.title = '编辑'
      this.$refs.PhysicalAttrEdit.type = 'edit'
      this.$refs.PhysicalAttrEdit.model = data
    },
    // 获取物模型 属性列表 删除
    attrDelete(id) {
      deleteProperty(`/productCenter/product/deleteProperty?propertyId=${id}`).then((res) => {
        if (res.success) {
          this.$message.success(res.message)
          this.getProperty()
        } else {
          this.$message.warning(res.message)
        }
      })
    },
    //  获取物模型 属性列表 分页
    attrdataPagination(e) {
      console.log(e)
      // this.property.pageNo = 1
      this.getProperty()
    },
    onShowSizeChange() {},
    handlePageChange(e) {
      this.$set(this.attrPageData, 'current', e)
      this.getProperty();
    },
    // 获取物模型 属性列表
    getProperty() {
      console.log(">>>>>>>>>>><<<<<<<<<");
      const params = {
        productCode: this.property.productCode,
        name: this.property.name,
        pageNo: this.attrPageData.current,
        pageSize: this.attrPageData.pageSize,
      }
      getListProperty(`/productCenter/product/listProperty`, params).then((res) => {
        if (res.success) {
          console.log('getProperty>>>>>>>>', res)
          this.attrdata = res.result.records
          this.attrdataTotal = res.result.total
          this.$set(this.attrPageData,'pageSize',res.result.size)
          // this.$set(this.attrPageData,'pageSize',res.result.pageSize)
          this.$set(this.attrPageData, 'total', res.result.total)
        }
      })
    },
    // 获取物模型 功能 定义列表
    getFunctionData() {
      getListProperty(`/productCenter/product/listFunction`, this.listFunction).then((res) => {
        if (res.success) {
          this.functionData = res.result.records
          this.attrdataTotal = res.result.total
        }
      })
    },

    // 列表分页
    changePagination(e) {
      this.queryParam.pageNo = Number(e)
      this.searchQuery()
    },
    // 重置
    searchReset() {
      this.queryParam = {
        productCode: null,
        unitName: null,
        status: null,
        pageNo: 1,
        pageSize: 8,
      }
    },
    // 查询
    searchQuery() {
      getlistFunction(`/productCenter/product/list`, this.queryParam).then((res) => {
        if (res.success) {
          this.data = res.result.records
          this.total = res.result.total
        }
      })
    },
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
    showModal() {
      this.policevisible = true
    },
    onSearch() {},
    callback(key) {
      console.log(key)
    },
    // list详情
    detailsClick(id, productCode) {
      this.property.productCode = productCode
      this.listFunction.productCode = productCode
      this.visible = true
      getQueryById(`/productCenter/product/queryById?id=${id}`).then((res) => {
        if (res.success) {
          this.productDetails = res.result
          this.getProperty()
          this.getFunctionData()
        }
      })
    },
    handleAdd() {
      this.$refs.ProductEdit.title = '新增'
      this.$refs.ProductEdit.type = 'add'
      this.$refs.ProductEdit.visible = true
    },
    // list编辑
    editClick(item) {
      let data = JSON.parse(JSON.stringify(item))
      this.$refs.ProductEdit.visible = true
      this.$refs.ProductEdit.title = '编辑'
      this.$refs.ProductEdit.type = 'edit'
      this.$refs.ProductEdit.model = data
    },
    // 删除list数据
    delClick(id) {
      deleteProperty(`/productCenter/product/delete?id=${id}`).then((res) => {
        if (res.success) {
          this.$message.success(res.message)
          this.searchQuery()
        } else {
          this.$message.warning(res.message)
        }
      })
    },
    // 报警
    policehandleOk(e) {
      this.policeconfirmLoading = true
      setTimeout(() => {
        this.policevisible = true
        this.policeconfirmLoading = false
      }, 2000)
    },
    policehandleCancel(e) {
      this.policevisible = false
    },

    handleOk(e) {
      this.ModalText = 'The modal will be closed after two seconds'
      this.confirmLoading = true
      setTimeout(() => {
        this.visible = false
        this.confirmLoading = false
      }, 2000)
    },
    handleCancel(e) {
      console.log('Clicked cancel button')
      this.visible = false
    },

    handleDetail(record) {
      this.$refs.PhysicalModelModal.edit(record)
    },
    handleDataRule(record) {
      this.$refs.PhysicalModelModal.edit(record)
    },
    handlePhysicalModel(record) {
      this.$refs.PhysicalModelModal.title = '物模型'
      this.$refs.PhysicalModelModal.disableSubmit = false
      this.$refs.PhysicalModelModal.edit({ status: '1', permsType: '1', route: true, parentId: record.id, menuType: 1 })
    },
  },
}
</script>
<style scoped lang="less">
@import '~@assets/less/common.less';
.models {
  height: 700px;
  overflow-y: auto;
}
.identification {
  color: #00bf8a;
}
.police {
  overflow-y: auto;
  height: 450px;
  .policeName {
    display: flex;
    justify-content: flex-start;
    align-items: center;
    span {
      font-size: 14px;
      color: #000;
      width: 100px;
      display: inline-block;
    }
  }
  .condition {
    .conditionHead {
      margin: 10px 0;
      display: flex;
      justify-content: flex-start;
      align-items: center;
      > p {
        font-size: 16px;
        font-weight: bold;
        margin: 0;
      }
      i {
        margin: 0 8px;
      }
      button {
      }
    }
    .trigger {
      .triggerList {
        background: #eeeaea;
        border-radius: 5px;
        padding-bottom: 10px;
        h2 {
          margin: 10px;
          font-weight: normal;
          font-size: 20px;
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
    .addTrigger {
      margin: 10px 0;
    }
  }
}
.wrapTab {
  padding: 10px;
  border: 1px solid #f3eeee;
}
.attr {
  display: flex;
  justify-content: space-between;
  border-bottom: 1px solid #f3eeee;
  align-items: center;
  p {
    font-size: 16px;
    font-weight: bold;
  }
  .attrBut {
    > button {
      margin-left: 5px;
    }
  }
}
.modelBut {
  position: absolute;
  right: 0;
  > button {
    margin-left: 5px;
  }
}
.product {
  font-size: 16px;
  font-weight: bold;
}
.table {
  border: 1px solid #f3eeee;
  margin: 16px 0;
  .col {
    .colName {
      height: 50px;
      display: flex;
      justify-content: flex-start;
      align-items: center;
      border-top: 1px solid #f3eeee;
      .sign {
        background: #fafafa;
        display: inline-block;
        width: 100px;
        height: 100%;
        display: flex;
        align-items: center;
        padding-left: 12px;
      }
      code {
        padding-left: 12px;
        flex: 1;
      }
      span {
      }
    }
    .explain {
    }
  }
}
.authentication {
  font-size: 14px;
  margin-top: 16px;
}
.pagination {
  float: right;
}
.meta-cardInfos {
  display: flex;
  flex-direction: column;
  .title {
    width: 100%;
    display: flex;
    justify-content: flex-start;
    align-items: flex-start;
    padding-left: 20px;
    box-sizing: border-box;
    > img {
      width: 40px;
      height: 40px;
    }
    .right {
      flex: 1;
      margin-left: 14px;
      p {
        font-size: 16px;
        &:nth-of-type(1) {
          font-weight: bold;
        }
        &:nth-of-type(2) {
        }
      }
    }
  }
  .contant {
    display: flex;
    justify-content: flex-start;
    align-items: center;
    .list {
      flex: 1;
      p {
        text-align: center;
        font-size: 16px;
      }
      span {
        display: block;
        text-align: center;
      }
      code {
        display: block;
        text-align: center;
        font-weight: bold;
      }
      i {
        display: inline-block;
        width: 6px;
        height: 6px;
        border-radius: 50%;
        background: #52c41a;
        margin-right: 3px;
      }
      .coler {
        background: red;
      }
    }
  }
}

.app-list {
  .meta-cardInfo {
    zoom: 1;
    margin-top: 16px;

    > div {
      position: relative;
      text-align: left;
      float: left;
      width: 50%;

      p {
        line-height: 32px;
        font-size: 24px;
        margin: 0;

        &:first-child {
          color: rgba(0, 0, 0, 0.45);
          font-size: 12px;
          line-height: 20px;
          margin-bottom: 4px;
        }
      }
    }
  }
}
</style>