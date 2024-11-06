<template>
  <a-card :bordered="false">

    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">

          <a-col :md="6" :sm="8">
            <a-form-item label="产品编码">
              <a-input placeholder="请输入产品编码" v-model="queryParam.code"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="产品名称">
              <a-input placeholder="请输入产品名称" v-model="queryParam.name"></a-input>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
            <a-col :md="6" :sm="8">
              <a-form-item label="状态">
                <j-dict-select-tag v-model="queryParam.state" placeholder="请选择状态" dictCode="state"/>
              </a-form-item>
            </a-col>

          </template>
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

        </a-row>
      </a-form>
    </div>

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('产品表')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
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

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange">

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>

          <a-divider type="vertical"/>
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down"/></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>
    <!-- table区域-end -->

		<!--table块状开始-->
		<!--
		<div class="app-list">
		  <a-list
		    :grid="{ gutter: 24, lg: 3, md: 2, sm: 1, xs: 1 }"
		    :dataSource="dataSource">
		    <a-list-item slot="renderItem" slot-scope="item, index">
		      <a-card :hoverable="true">
		        <a-card-meta>
		          <div style="margin-bottom: 3px" slot="title">{{ item.name }}</div>
		          <a-avatar class="card-avatar" slot="avatar" :src="item.avatar" size="small"/>
		          <div class="meta-cardInfo" slot="description">
		            <div>
		              <p>产品数量</p>
		              <p>
		                <span>{{ item.activeUser }}<span>万</span></span>
		              </p>
		            </div>
		            <div>
		              <p>发布状态</p>
		              <p>{{ item.newUser | NumberFormat }}</p>
		            </div>
					<div>
					  <p>产品类型</p>
					  <p>{{ item.newUser | NumberFormat }}</p>
					</div>
		          </div>
		        </a-card-meta>
		        <template class="ant-card-actions" slot="actions">
		          <a>
		            <a-icon type="download"/>
		          </a>
		          <a>
		            <a-icon type="edit"/>
		          </a>
		          <a>
		            <a-icon type="share-alt"/>
		          </a>
		          <a>
		            <a-dropdown>
		              <a class="ant-dropdown-link" href="javascript:;">
		                <a-icon type="ellipsis"/>
		              </a>
		              <a-menu slot="overlay">
		                <a-menu-item>
		                  <a href="javascript:;">1st menu item</a>
		                </a-menu-item>
		                <a-menu-item>
		                  <a href="javascript:;">2nd menu item</a>
		                </a-menu-item>
		                <a-menu-item>
		                  <a href="javascript:;">3rd menu item</a>
		                </a-menu-item>
		              </a-menu>
		            </a-dropdown>
		          </a>
		        </template>
		      </a-card>
		    </a-list-item>
		  </a-list>
		
		</div>
		-->
		<!--table块状结束-->
    <!-- 表单区域 -->
    <Product-modal ref="modalForm" @ok="modalFormOk"></Product-modal>
  </a-card>
</template>

<script>
  import ProductModal from './modal'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import JDictSelectTag from '@/components/dict/JDictSelectTag'

  export default {
    name: 'ProductList',
    mixins: [JeecgListMixin],
    components: {
      ProductModal,
      JDictSelectTag
    },
    data() {
      return {
        description: '产品表管理页面',
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
            }
          },
		  {
		    title: '产品名称',
		    align: 'center',
		    dataIndex: 'name'
		  },
          {
            title: '产品编码',
            align: 'center',
            dataIndex: 'code'
          },
          {
            title: '类型',
            align: 'center',
            dataIndex: 'typeName'
          },
		  {
		    title: '节点类型',
		    align: 'center',
		    dataIndex: 'nodeType_dictText'
		  },
		  {
		    title: '网络',
		    align: 'center',
		    dataIndex: 'network_dictText'
		  },
		  {
		    title: '协议',
		    align: 'center',
		    dataIndex: 'protocol_dictText'
		  },
          {
            title: '状态',
            align: 'center',
            dataIndex: 'state_dictText',
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
            title: '操作',
            dataIndex: 'action',
            align: 'center',
            scopedSlots: { customRender: 'action' },
          }
        ],
        url: {
          list: '/productCenter/product/list',
          delete: '/productCenter/product/delete',
          deleteBatch: '/productCenter/product/deleteBatch',
          exportXlsUrl: '/productCenter/product/exportXls',
          importExcelUrl: 'productCenter/product/importExcel',
        },
      }
    },
    computed: {
      importExcelUrl: function () {
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
  
  
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
            color: rgba(0, 0, 0, .45);
            font-size: 12px;
            line-height: 20px;
            margin-bottom: 4px;
          }
        }
  
      }
    }
  }
</style>