<template>
  <a-card :bordered="false">
    <div class="identification">
      <p v-for="(item, index) in identification" :key="index"><i></i>{{ item.title }}<span>{{ item.value }}</span></p>
    </div>
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :md="6" :sm="8">
            <a-form-item label="设备编码">
              <a-input placeholder="请输入设备编码" v-model="queryParam.deviceCode"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="设备名称">
              <a-input placeholder="请输入设备名称" v-model="queryParam.deviceName"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="节点类型">
              <j-dict-select-tag v-model="queryParam.nodeType" placeholder="请选择节点" dictCode="node_type" />
            </a-form-item>
          </a-col>


          <a-col :md="6" :sm="8">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">搜索</a-button>
              <!-- <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
			  -->
              <!-- <a @click="handleToggleSearch" style="margin-left: 8px">
			    {{ toggleSearchStatus ? '收起' : '展开' }}
			    <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>
			  </a> -->
            </span>
          </a-col>

        </a-row>
      </a-form>
    </div>

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button type="primary" @click="handleAdd" icon="plus-circle">创建设备</a-button>
      <!--
      <a-button  @click="equipmentAdd" icon="border-inner">批量添加</a-button>
      <a-button  @click="handleAdd" icon="file-pdf">按产品导出</a-button>
      <a-button  @click="handleAdd" icon="delete">删除</a-button>
			-->
    </div>
    <div class="app-list">
      <a-list :grid="{ gutter: 24, lg: 4, md: 2, sm: 1, xs: 1 }" :dataSource="data">
        <a-list-item slot="renderItem" slot-scope="item, index">
          <a-card :hoverable="true">
            <div>
              <div class="meta-cardInfos">
                <div class="title">
                  <img src="./images/equipment.png" alt="">
                  <div class="right">
                    <p>{{ item.deviceName }}</p>
                    <p>{{ item.deviceCode }}</p>
                  </div>
                </div>
                <div class="contant">
                  <div class="list">
                    <p>节点类型</p>
                    <span>{{ nodeType[item.nodeType] }}</span>
                  </div>
                  <div class="list">
                    <p>在线状态 </p>
                    <code><i :class="item.activeStatus == 0 ? 'coler' : ''"></i>{{ item.activeStatus == 0 ? '离线' : '在线' }}</code>
                  </div>
                  <div class="list">
                    <p>网络类型 </p>
                    <code>{{ networkType[item.networkType] }}</code>
                  </div>
                </div>
              </div>
            </div>

            <template class="ant-card-actions" slot="actions">
              <a>
                <a-icon type="eye-invisible" @click="detailsDevClick(item.id, item.deviceCode)" />
              </a>
              <a>
                <a-icon type="edit" @click="editClick(item)" />
              </a>
              <a>
                <a-icon type="download" />
              </a>
              <!-- <a>
		            <a-icon type="share-alt"/>
		          </a> -->
              <a>
                <a-dropdown>
                  <a class="ant-dropdown-link" href="javascript:;">
                    <a-icon type="ellipsis" />
                  </a>
                  <a-menu slot="overlay">
                    <a-menu-item>
                      <a href="javascript:;" @click="delClick(item.id)">删除</a>
                    </a-menu-item>

                  </a-menu>
                </a-dropdown>
              </a>
            </template>
          </a-card>
        </a-list-item>
      </a-list>
      <a-pagination v-model="current" :total='total' show-less-items class="pagination"
        :page-size="Number(queryParam.pageSize)" :default-current="Number(queryParam.pageNo)"
        @change="changePagination" />
    </div>
    <!-- table区域-begin -->

    <!-- table区域-end -->

    <!-- 表单区域 -->
    <DeviceEdit ref="DeviceEdit" @ok="modalFormOk"></DeviceEdit>
    <PhysicalEdit ref="PhysicalEdit" @ok="modalFormOk"></PhysicalEdit>
	
    <a-modal title="设备详情" :visible="visible" :width="900" :confirm-loading="confirmLoading" @ok="handleOk"
      @cancel="handleCancel">
      <div class="deviceModal">
        <div class="equ">设备：{{ productDetails.deviceName }}</div>
        <div class="deviceTitle">
          <p>ID:{{ productDetails.id }}</p>
          <p>编码：{{ productDetails.deviceCode }}</p>
        </div>
        <a-tabs default-active-key="1" @change="callback">
          <a-tab-pane key="1" tab="实例信息">
            <div class="product">设备信息</div>
            <div class="table">
              <div class="col">
                <div class="colName">
                  <span class="sign">在线状态 </span>
                  <code>{{ productDetails.activeStatus == 0 ? '离线' : '在线' }}</code>
                  <span class="sign">最近一次上线主机地址 </span>
                  <code>{{ productDetails.devHost }}</code>
                  <span class="sign">最近一次上线端口 </span>
                  <code>{{ productDetails.devPort }}</code>
                </div>
                <div class="colName">
                  <span class="sign">产品编码 </span>
                  <code>{{ productDetails.productCode }}</code>
                  <span class="sign">设备名称 </span>
                  <code>{{ productDetails.deviceName }}</code>
                  <span class="sign">启用状态</span>
                  <code>{{ productDetails.enableStatus == 0 ? '未启用' : '启用' }}</code>
                </div>
                <div class="colName">
									<span class="sign">网络类型</span>
									<code>{{ productDetails.networkType }}</code>
                  <span class="sign">节点类型 </span>
                  <code v-if="productDetails.nodeType == 'DIRECT'">直连设备</code>
				  <code v-if="productDetails.nodeType == 'GATEWAY'">网关设备</code>
				  <code v-if="productDetails.nodeType == 'SUB'">网关子设备</code>
				  <!--判断是子设备-->
                  <span class="sign" v-if="productDetails.gwDevCode != ''">归属网关编码</span>
                  <code v-if="productDetails.gwDevCode != ''">{{ productDetails.gwDevCode }}</code>
                  <!--判断是网关-->
				  <span class="sign" v-if="productDetails.gwDevCode == ''"></span>
                  <code v-if="productDetails.gwDevCode == ''">{{ productDetails.gwDevCode }}</code>
                  
                </div>
                <div class="colName explain">
                  <span class="sign">描述</span>
                  <code></code>
                </div>
              </div>
            </div>
            <div class="product">配置</div>
            <div class="authentication">mqtt配置</div>
            <div class="table">
              <div class="col">
                <div class="colName">

                  <span class="sign">用户 </span>
                  <code>{{ productDetails.deviceCode }}</code>
                  <span class="sign">密钥 </span>
                  <code>{{ productDetails.deviceSecret }}</code>
                </div>
              </div>
            </div>
          </a-tab-pane>
          <a-tab-pane key="2" tab="运行状态">
            <div class="state">
              <div class="stateList" v-for="(item, index) in stateList" :key="index">
                <div class="stateMesses">
                  <p>{{ item.propName }}</p>
                  <div>
                    <a-icon type="sync" />
                  </div>
                </div>
                <div class="num">{{ item.value }}</div>
                <p>{{ item.arrivedTime }}</p>
                <div class="stateBottom">
                  <div class="stateL"><span @click="issue(item)">下发</span></div>
                  <div class="stateR"><span @click="details(item)">详情</span></div>
                </div>
              </div>
            </div>
          </a-tab-pane>
          <a-tab-pane key="3" tab="物模型">
            <!-- <div class="modelBut">
                <a-button>重置</a-button>
                <a-button>快速导入</a-button>
                <a-button>物模型TSL</a-button>
              </div> -->
            <a-tabs default-active-key="1">
              <a-tab-pane key="1" tab="属性定义">
                <div class="wrapTab">
                  <div class="attr">
                    <p>属性定义</p>
                    <div class="attrBut">
                      <a-button type="primary" @click="addattrBut">添加</a-button>
                    </div>
                  </div>
                  <div class="">
                    <a-table
                      ref="table"
                      size="middle"
                      rowKey="id"
                      :columns="attrColumns"
                      :dataSource="attrdata"
                      :pagination="false"
                      @change="handleTableChange"
                    >
                      <span slot="action" slot-scope="text, record">
                        <a-space>
                          <a href="javascript:;" class="pointClick" v-if="record.pointType=='modbus'||record.pointType==''"  @click="attrPont(record.pointType,record.identifier)">modbus点位</a>
                          <a href="javascript:;" class="pointClick" v-if="record.pointType=='string'||record.pointType==''"  @click="attrPont(record.pointType,record.identifier)">字符串点位</a>
                          <a href="javascript:;" class="pointClick" v-if="record.pointType=='json'||record.pointType==''"  @click="attrPont(record.pointType,record.identifier)">json点位</a>
                          <a @click="attrEdit(record)" href="javascript:;" class="pointClick">编辑</a>
                        <a-menu-item>
                          <a-popconfirm title="确定删除吗?" @confirm="() => attrDelete(record.id)">
                            <a href="javascript:;" class="pointClick">删除</a>
                          </a-popconfirm>
                        </a-menu-item>
                        </a-space>
                      </span>
                    </a-table>
                    <a-pagination
                      v-model="property.pageNo"
                      :total="attrdataTotal"
                      show-less-items
                      class="pagination"
                      @change="attrdataPagination"
                      :page-size="Number(property.pageSize)"
                      :default-current="Number(property.pageNo)"
                    />
                  </div>
                </div>
              </a-tab-pane>
              <a-tab-pane key="2" tab="功能定义">
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
                      :pagination="false"
                      :columns="attrColumns"
                      :dataSource="functionData"
                      :loading="loading"
                      @change="handleTableChange"
                    >
                      <span slot="action" slot-scope="text, record">
                        <a @click="functionEdit(record)">编辑</a>
                        <a-menu-item>
                          <a-popconfirm title="确定删除吗?" @confirm="() => functionDelete(record.id)">
                            <a>删除</a>
                          </a-popconfirm>
                        </a-menu-item>
                      </span>
                    </a-table>
                    <a-pagination
                      v-model:current="funcurrent"
                      :total="functionTotal"
                      show-less-items
                      class="pagination"
                      @change="fundataPagination"
                      :page-size="Number(listFunction.pageSize)"
                      :default-current="Number(listFunction.pageNo)"
                    />
                  </div>
                </div>
              </a-tab-pane>
            </a-tabs>
          </a-tab-pane>

          
        </a-tabs>
      </div>
    </a-modal>
    <!-- 新增告警弹窗 -->
    <a-modal title="编辑报警" :width="900" :visible="policevisible" :confirm-loading="policeconfirmLoading"
      @ok="policehandleOks" @cancel="policehandleCancels">
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
            <div class="triggerList" v-for="(item, index) in triggerList" :key="index">
              <H2>{{ item.name }} <a href="javacsript:;" class="identification" @click="triggerDelete(index)">删除</a></H2>
              <div class="select">
                <a-select default-value="lucy" style="width: 200px" @change="handleChange">
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
                <a-select default-value="lucy" style="width: 200px" @change="handleChange">
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
              <div class="triggerCol" v-for="(i, index2) in item.list" :key="index2">
                <a-input placeholder="过滤条件KEY" style="width:200px" />
                <a-select default-value="lucy" style="width: 200px" @change="handleChange">
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
                <a href="javascript:;" class="identification" @click="identificationDelete(index, index2)">删除</a>
              </div>
              <a href="javascript:;" class="identification" style="margin: 10px 20px;"
                @click="addidentification(index)">添加</a>
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
            <div class="triggerList" v-for="(item, index) in triggerList" :key="index" style="padding-top: 10px">
              <div class="triggerCol" v-for="(i, index2) in item.list" :key="index2">
                <a-input placeholder="过滤条件KEY" style="width:200px" />
                <a-select default-value="lucy" style="width: 200px" @change="handleChange">
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
                <a href="javascript:;" class="identification" @click="identificationDelete(index, index2)">删除</a>
              </div>
              <a href="javascript:;" class="identification" style="margin: 10px 20px;"
                @click="addidentification(index)">添加</a>
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
            <div class="triggerList" v-for="(item, index) in triggerList" :key="index">
              <H2>{{ item.name }} <a href="javacsript:;" class="identification" @click="triggerDelete(index)">删除</a></H2>
              <div class="triggerCol" v-for="(i, index2) in item.list" :key="index2">
                <a-input placeholder="过滤条件KEY" style="width:200px" />
                <a-select default-value="lucy" style="width: 200px" @change="handleChange">
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
                <a href="javascript:;" class="identification" @click="identificationDelete(index, index2)">删除</a>
              </div>
              <a href="javascript:;" class="identification" style="margin: 10px 20px;"
                @click="addidentification(index)">添加</a>
            </div>
            <a-button type="primary" icon="plus" class="addTrigger" @click="addTrigger">新增触发器</a-button>
          </div>
        </div>
      </div>
    </a-modal>
    <!-- 新增告警弹窗 end-->
    <!-- 新增属性model -->
    <PhysicalAttrEdit ref="PhysicalAttrEdit"></PhysicalAttrEdit>
    <!-- 新增功能定义model -->
    <PhysicalFuncEdit ref="PhysicalFuncEdit"></PhysicalFuncEdit>
    <!--属性数据详情-->
    <DevicePropertyDataList ref="DevicePropertyDataList"></DevicePropertyDataList>
    <!--属性数据详情-下发数据窗体-->
    <DevicePropertyDataSet ref="DevicePropertyDataSet"></DevicePropertyDataSet>
		<!-- modbus点位 -->
		<Modbus ref="modbus"></Modbus>
		 <!-- strings点位 -->
		<Strings ref="strings"></Strings>
		  <!-- json点位 -->
		<Json ref="json"></Json>
  </a-card>
</template>

<script>
var timer = null
import DeviceEdit from './edit'
import PhysicalEdit from './physical_edit'
import JDictSelectTag from '@/components/dict/JDictSelectTag'
import { getlistFunction, getQueryById, getListProperty, deleteProperty } from '@/api/product'
import PhysicalAttrEdit from './physical_attr_edit'
import PhysicalFuncEdit from './physical_func_edit'
//设备属性数据列表
import DevicePropertyDataList from './device_property_data_list'
//设备属性数据下发窗体
import DevicePropertyDataSet from './device_property_data_set'
// 点位model
import Modbus from './point/modbus'
import Strings from './point/string'
import Json from './point/json'

export default {
  name: 'DeviceList',
  mixins: [],
  components: {
    DeviceEdit,
    JDictSelectTag,
    PhysicalEdit,
    PhysicalAttrEdit,
    PhysicalFuncEdit,
    DevicePropertyDataList,
    DevicePropertyDataSet,
	Modbus,
	Strings,
	Json
  },
  data() {
    return {
      current: 1,
      networkType: {
        wifi: 'wifi无线',
        '5g': '蜂窝网络',
        'nb-iot': '窄带网络',
        'rj-45': '以太网',
        serial: '串口',
      },
      nodeType: {
        DIRECT: '直连设备',
        GATEWAY: '网关设备',
        SUB: '网关子设备',
      },
      loading: false,
      current: 1,
      funcurrent: 1,
      // 物模型 属性 列表
      property: {
        deviceCode: '',
        name: '',
        pageNo: 1,
        pageSize: 5,
      },
      // 物模型 产品 列表
      listFunction: {
        deviceCode: '',
        pageNo: 1,
        pageSize: 5,
      },
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
      productDetails: {},
      attrdataTotal: 0,
      functionTotal: 0,
      total: 0,
      queryParam: {
        deviceCode: null,
        deviceName: null,
        nodeType: null,
        activeStatus: null,
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
      policevisible: false,
      policeconfirmLoading: false,
      layout: {
        labelCol: { span: 8 },
        wrapperCol: { span: 14 },
      },
      model: {
        dataType: null,
        funcDesc: null,
        unitName: null,
        funcType: null,
        productCode: null,
        name: null,

        async: 0,
        attr: '',
        createBy: 0,
        createTime: '',
        dataDefine: '',
        dataType: '',
        delFlag: 0,
        eventType: '',
        identifier: '',
        inputParam: '',
        outputParam: '',
        status: 0,
        unit: '',
        updateTime: '',
        wrType: 0,
      },
      validatorRules: {
        code: [
          { required: true, message: '请输入联动编码' },
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
        dataType: [{ required: true, message: '请输入数据类型' }],
        funcDesc: [{ required: true, message: '请输入描述' }],
        unitName: [{ required: true, message: '请输入计量单位名称' }],
        funcType: [{ required: true, message: '请输入物模型类型' }],
        productCode: [{ required: true, message: '请输入产品编码' }],
        name: [{ required: true, message: '请输入名称' }],
      },
      identification: [
        {
          title: '设备总数',
          value: '0',
        },
        {
          title: '激活数',
          value: '1',
        },
        {
          title: '在线数',
          value: '1',
        },
      ],
      stateList: [
        {
          propName: '', //属性名称
          value: '', //值
          identifier: '', //属性标识
          arrivedTime: '', //到达时间
        },
      ],
      ModalText: 'Content of the modal',
      visible: false,
      confirmLoading: false,
      description: '设备表管理页面',
      // 表头
      columns: [
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
          title: '设备名称',
          align: 'center',
          dataIndex: 'name',
        },
        {
          title: '设备编码',
          align: 'center',
          dataIndex: 'code',
        },

        {
          title: '产品名称',
          align: 'center',
          dataIndex: 'productName',
        },
        {
          title: '节点类型',
          align: 'center',
          dataIndex: 'nodeType',
        },

        {
          title: '网络',
          align: 'center',
          dataIndex: 'network',
        },
        {
          title: '协议',
          align: 'center',
          dataIndex: 'protocol',
        },
        {
          title: 'IP',
          align: 'center',
          dataIndex: 'ip',
        },
        {
          title: '在线状态',
          align: 'center',
          dataIndex: 'onlineState',
        },
        {
          title: '状态',
          align: 'center',
          dataIndex: 'state',
        },
        {
          title: '操作',
          dataIndex: 'action',
          align: 'center',
          scopedSlots: { customRender: 'action' },
        },
      ],
      url: {
        list: '/productCenter/device/list',
        delete: '/productCenter/device/delete',
        deleteBatch: '/productCenter/device/deleteBatch',
        exportXlsUrl: '/productCenter/device/exportXls',
        importExcelUrl: 'productCenter/device/importExcel',
      },

      intervalId: null, //定时器
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
	  // 物模型点位
	  attrPont(type,code){
	    if(type=='modbus'){
	      this.$refs.modbus.visible = true
	       this.$refs.modbus.title = 'modbus点位'
	       this.$refs.modbus.getData(code)
	    }else if(type=='string'){
	      this.$refs.strings.visible = true
	       this.$refs.strings.title = 'string点位'
	       this.$refs.strings.getData(code)
	    }else if(type=='json'){
	      this.$refs.json.visible = true
	       this.$refs.json.title = 'json点位'
	       this.$refs.json.getData(code)
	    }
	  },
    //下发
    issue(item) {
      this.$refs.DevicePropertyDataSet.visible = true
      this.$refs.DevicePropertyDataSet.title = '属性下发设置'
      this.$refs.DevicePropertyDataSet.loadData(item.identifier, this.productDetails.deviceCode)
    },
    //设备属性数据详情
    details(item) {
      this.$refs.DevicePropertyDataList.visible = true
      this.$refs.DevicePropertyDataList.title = '属性详情数据'
      this.$refs.DevicePropertyDataList.getDeviceInfo(Object.assign({}, this.productDetails, item))
    },
    wrapperCol() { },
    labelCol() { },
    readOnly() { },
    handleChange() { },
    modalFormOk() { },
    handleTableChange() { },
    // 功能调试  保存
    saveDebugging() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          addFunction('/productCenter/device/addFunction', this.model).then((res) => {
            if (res.success) {
              that.$message.success(res.message)
              this.model = {
                dataType: null,
                funcDesc: null,
                unitName: null,
                funcType: null,
                productCode: null,
                name: null,
                async: 0,
                attr: '',
                createBy: 0,
                createTime: '',
                dataDefine: '',
                dataType: '',
                delFlag: 0,
                eventType: '',
                identifier: '',
                inputParam: '',
                outputParam: '',
                status: 0,
                unit: '',
                updateTime: '',
                wrType: 0,
              }
            } else {
              that.$message.warning(res.message)
            }
          })
        }
      })
    },
    // 添加功能定义
    addFunctionBut() {
      this.$refs.PhysicalFuncEdit.type = 'add'
      this.$refs.PhysicalFuncEdit.title = '新增'
      this.$refs.PhysicalFuncEdit.visible = true
			//将设备编码传入
			this.$refs.PhysicalFuncEdit.model.deviceCode = this.productDetails.deviceCode
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
      deleteProperty(`/productCenter/device/deleteFunction?functionId=${String(id)}`).then((res) => {
        if (res.success) {
          this.$message.success(res.message)
          this.getFunctionData()
        } else {
          this.$message.warning(res.message)
        }
      })
    },
    // 获取物模型 属性列表 删除
    attrDelete(id) {
      deleteProperty(`/productCenter/device/deleteProperty?propertyId=${id}`).then((res) => {
        if (res.success) {
          this.$message.success(res.message)
          this.getProperty()
        } else {
          this.$message.warning(res.message)
        }
      })
    },
    // 获取属性实时数据-运行状态
    getRunState() {
      getListProperty(`/productCenter/device/runtime`, this.property).then((res) => {
        if (res.success) {
          this.stateList = res.data
        }
      })
    },
    //  获取物模型 属性列表 分页
    attrdataPagination(e) {
      this.property.pageNo = e
      this.getProperty()
    },
    //  获取物模型 功能列表 分页
    fundataPagination(e) {
      this.listFunction.pageNo = e
      this.getFunctionData()
    },
    // 获取物模型 属性列表
    getProperty() {
      getListProperty(`/productCenter/device/listProperty`, this.property).then((res) => {
        if (res.success) {
          this.attrdata = res.result.records
          this.attrdataTotal = res.result.total
        }
      })
    },
    // 获取物模型 功能 定义列表
    getFunctionData() {
      getListProperty(`/productCenter/device/listFunction`, this.listFunction).then((res) => {
        if (res.success) {
          this.functionData = res.result.records
          this.functionTotal = res.result.total
        }
      })
    },
    // 添加属性定义
    addattrBut() {
      this.$refs.PhysicalAttrEdit.type = 'add'
      this.$refs.PhysicalAttrEdit.title = '新增'
      this.$refs.PhysicalAttrEdit.visible = true
			//将设备编码传入
			this.$refs.PhysicalAttrEdit.model.deviceCode = this.productDetails.deviceCode
			this.$refs.PhysicalAttrEdit.model.productCode = this.productDetails.productCode
    },
    // 获取物模型 属性列表 编辑
    attrEdit(item) {
      let data = JSON.parse(JSON.stringify(item))
      this.$refs.PhysicalAttrEdit.visible = true
      this.$refs.PhysicalAttrEdit.title = '编辑'
      this.$refs.PhysicalAttrEdit.type = 'edit'
      this.$refs.PhysicalAttrEdit.model = data
    },
    // list新增
    handleAdd() {
      this.$refs.DeviceEdit.title = '新增'
      this.$refs.DeviceEdit.type = 'add'
      this.$refs.DeviceEdit.visible = true
    },
    // list编辑
    editClick(item) {
      let data = JSON.parse(JSON.stringify(item))
      this.$refs.DeviceEdit.visible = true
      this.$refs.DeviceEdit.title = '编辑'
      this.$refs.DeviceEdit.type = 'edit'
      this.$refs.DeviceEdit.model = data
    },
    // 删除list数据
    delClick(id) {
      deleteProperty(`/productCenter/device/delete?id=${id}`).then((res) => {
        if (res.success) {
          this.$message.success(res.message)
          this.searchQuery()
        } else {
          this.$message.warning(res.message)
        }
      })
    },
    // list详情
    detailsDevClick(id, deviceCode) {
      this.property.deviceCode = deviceCode
      this.listFunction.deviceCode = deviceCode
      this.visible = true
      getQueryById(`/productCenter/device/queryById?id=${id}`).then((res) => {
        if (res.success) {
          this.productDetails = res.result
          this.getRunState() //加载运行状态
          this.getProperty() //加载物模型-属性列表
          this.getFunctionData() //加载物模型-功能列表

          console.log(res.result)
        }
        if (timer) {
          clearInterval(timer)
        }
        timer = setInterval(() => {
          //5秒刷新数据
          this.getRunState()
        }, 1000 * 5)
      })
    },
    // 列表分页
    changePagination(e) {
      this.queryParam.pageNo = Number(e)
      this.searchQuery()
    },
    // 查询
    searchQuery() {
      getlistFunction(`/productCenter/device/list`, this.queryParam).then((res) => {
        if (res.success) {
          this.data = res.result.records
          this.total = res.result.total
          this.identification[0].value = res.deviceCount
          this.identification[1].value = res.deviceEnableCount
          this.identification[2].value = res.deviceOnlineCount
        }
      })
    },
    // 日期
    onChangePicker(date, dateString) {
      console.log(date, dateString)
    },
    // 报警
    policehandleOks(e) {
      this.policeconfirmLoading = true
      setTimeout(() => {
        this.policevisible = true
        this.policeconfirmLoading = false
      }, 2000)
    },
    policehandleCancels(e) {
      this.policevisible = false
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
    equipmentAdd: function () {
      this.$refs.DeviceEdit.add()
      this.$refs.DeviceEdit.title = '新建设备'
      this.$refs.DeviceEdit.disableSubmit = false
    },
    callback(key) {
      console.log(key)
    },
    detailsClick() {
      this.visible = true
    },
    handleOk(e) {
      this.ModalText = 'The modal will be closed after two seconds'
      this.confirmLoading = true
      setTimeout(() => {
        this.visible = false
        this.confirmLoading = false
      }, 1000)
    },
    handleCancel(e) {
      console.log('Clicked cancel button')
      this.visible = false
    },
  },
  destroyed() {
    if (timer) {
      clearInterval(timer)
    }
  },
}
</script>
<style scoped lang='less'>
@import '~@assets/less/common.less';

.table {
  margin: 8px 0;
}

.debugging {
  font-size: 16px;
  border-bottom: 1px solid #e8e8e8;
  padding-bottom: 13px;
  margin-bottom: 13px;
  padding-left: 16px;
  display: flex;
  justify-content: space-between;
  align-items: center;

  >button {
    margin-right: 10px;
  }
}

.identification {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  margin-bottom: 24px;

  p {
    margin: 0 10px;
    color: rgb(46, 44, 44);

    &:nth-of-type(1) {
      i {
        background: red;
      }
    }

    &:nth-of-type(2) {
      i {
        background: #605596;
      }
    }

    &:nth-of-type(3) {
      i {
        background: rgb(85, 150, 94);
      }
    }

    i {
      display: inline-block;
      width: 10px;
      height: 10px;
      border-radius: 50%;
      margin: 0 10px;
    }
  }

  span {
    margin-left: 10px;
  }
}

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

      >p {
        font-size: 16px;
        font-weight: bold;
        margin: 0;
      }

      i {
        margin: 0 8px;
      }

      button {}
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
          display: flex;

          >a {
            margin-left: 15px;

            &:hover {
              cursor: pointer;
            }
          }
        }

        .select {
          margin: 0 10px;

          >div {
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

          >div {
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
    >button {
      margin-left: 5px;
    }
  }
}

.modelBut {
  position: absolute;
  right: 0;

  >button {
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

      span {}
    }

    .explain {}
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

    >img {
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

        &:nth-of-type(2) {}
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

    >div {
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

.equ {
  font-weight: bold;
  font-size: 18px;
  margin-bottom: 14px;
}

.deviceModal {
  .deviceTitle {
    display: flex;
    justify-content: flex-start;

    p {
      margin-right: 20px;
    }
  }

  .state {
    display: flex;
    justify-content: flex-start;
    flex-wrap: wrap;

    .stateList {
      width: 22%;
      background: #f3f2f2;
      margin-right: 3%;
      margin-bottom: 3%;
      border-radius: 5px;
      padding: 5px 10px;

      .stateMesses {
        display: flex;
        justify-content: space-between;
        align-items: center;

        span {
          padding: 0 6px;

          &:hover {
            cursor: pointer;
          }
        }
      }

      &:last-child {
        margin-right: 0;
      }

      p {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin: 5px 0;

        i {}
      }

      .num {
        font-size: 20px;
        font-weight: bold;
        margin-bottom: 8px;
      }

      .stateBottom {
        display: flex;
        justify-content: flex-end;
        align-items: center;
        padding: 5px;

        span {
          margin-left: 8px;
        }

        .stateL {
          color: #00bf8a;

          .coler {
            width: 8px;
            height: 8px;
            display: inline-block;
            border-radius: 50%;
            background: #00bf8a;
            margin-right: 5px;
          }
        }

        .stateR {
          color: #00bf8a;
        }
      }
    }
  }
}
</style>