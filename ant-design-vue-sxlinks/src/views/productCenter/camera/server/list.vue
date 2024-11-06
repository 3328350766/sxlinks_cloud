<template>
  <a-card :bordered="false">
    <div class="app-list">
      <div class="col">
        <a-col :span="num" v-for="item in mediaServerList" :key="item.id">
          <a-card title="" style="width: 350px" class="server-card">
            <div class="card-img-zlm"></div>
            <div style="padding: 14px; text-align: left">
              <span style="font-size: 16px">{{ item.id }}</span>
              <el-button v-if="false" icon="el-icon-edit" style="padding: 0; float: right" type="text" @click="edit(item)">编辑</el-button>
              <el-button 
                v-if="item.defaultServer"
                icon="el-icon-show"
                style="padding: 0; float: right"
                type="text"
                @click="edit(item)"
                >查看</el-button
              >
              <el-button
                v-if="false"
                icon="el-icon-delete"
                style="margin-right: 10px; padding: 0; float: right"
                type="text"
                @click="del(item)"
                >移除</el-button
              >
              <div style="margin-top: 13px; line-height: 12px">
                <span style="font-size: 14px; color: #999; margin-top: 5px">{{ item.ip }}</span>
                <span style="font-size: 14px; color: #999; margin-top: 5px; float: right">{{ item.createTime }}</span>
              </div>
            </div>
            <a-icon  />
            <a-icon v-if="item.status" class="iconfont icon-online server-card-status-online" :style="{ color: '#359EFF' }" type="eye" title="在线"></a-icon>
            <a-icon v-if="!item.status" class="iconfont icon-online server-card-status-offline" type="eye" title="离线"></a-icon>
            <i v-if="item.defaultServer" class="server-card-default">默认</i>
          </a-card>
        </a-col>
      </div>
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
    <a-modal
     title="查看"
    :width="850"
    :visible="visible"
    @ok="handleOk"
    @cancel="handleOk"
    cancelText="关闭"
  >
    <div>
      <a-form-model ref="form" :model="model">
        <div class="a_col">
          <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="IP">
            <a-input placeholder="请输入IP" v-model="model.ip" disabled />
          </a-form-model-item>
          <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="dataValue" label="开启自动配置ZLM">
            <a-input placeholder="请输入开启自动配置ZLM" v-model="model.autoConfig" disabled />
          </a-form-model-item>
        </div>
        <div class="a_col">
          <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="是否是默认ZLM">
            <a-input placeholder="请输入是默认ZLM" v-model="model.defaultServer" disabled />
          </a-form-model-item>
          <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="dataValue" label="hook触发间隔">
            <a-input placeholder="请输入数据值" v-model="model.hookAliveInterval" disabled />
          </a-form-model-item>
        </div>
        <div class="a_col">
          <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="hook使用的IP">
            <a-input placeholder="请输入hook使用的IP" v-model="model.hookIp" disabled />
          </a-form-model-item>
          <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="dataValue" label="HTTP端口">
            <a-input placeholder="请输入HTTP端口" v-model="model.httpPort" disabled />
          </a-form-model-item>
        </div>
        <div class="a_col">
          <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="RTMP端口">
            <a-input placeholder="请输入RTMP端口" v-model="model.rtmpPort" disabled />
          </a-form-model-item>
          <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="dataValue" label="是否使用多端口模式">
            <a-input placeholder="请输入是否使用多端口模式" v-model="model.rtpEnable" disabled />
          </a-form-model-item>
        </div>
        <div class="a_col">
          <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="多端口RTP收流端口范围">
            <a-input placeholder="请输入多端口RTP收流端口范围" v-model="model.rtpPortRange" disabled />
          </a-form-model-item>
          <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="dataValue" label="RTP收流端口">
            <a-input placeholder="请输入RTP收流端口" v-model="model.rtpProxyPort" disabled />
          </a-form-model-item>
        </div>
        <div class="a_col">
          <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="RTSP端口">
            <a-input placeholder="请输入RTSP端口" v-model="model.rtspPort" disabled />
          </a-form-model-item>
          <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="dataValue" label="SDP">
            <a-input placeholder="请输入SDP" v-model="model.sdpIp" disabled />
          </a-form-model-item>
        </div>
        <div class="a_col">
          <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="ZLM鉴权参数">
            <a-input placeholder="请输入ZLM鉴权参数" v-model="model.secret" disabled />
          </a-form-model-item>
          <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="RTP发流端口范围">
            <a-input placeholder="请输入RTP发流端口范围" v-model="model.sendRtpPortRange" disabled />
          </a-form-model-item>
        </div>
        <div class="a_col">
          <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="状态">
            <a-input placeholder="请输入状态" v-model="model.status" disabled />
          </a-form-model-item>
          <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="流IP">
            <a-input placeholder="请输入流IP" v-model="model.streamIp" disabled />
          </a-form-model-item>
        </div>
        <div class="a_col">
          <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="dataValue" label="assist服务端口">
            <a-input placeholder="请输入assist服务端口" v-model="model.recordAssistPort" disabled />
          </a-form-model-item>
          <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" >
          </a-form-model-item>
        </div>
      </a-form-model>
    </div>
  </a-modal>
  </a-card>
</template>

<script>
// import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import { getQueryById, deleteProperty } from '@/api/product'

export default {
  name: 'ProductList',
  // mixins: [JeecgListMixin],
  components: {},
  data() {
    return {
      confirmLoading:true,
      labelCol: {
        xs: { span: 12 },
        sm: { span: 12 },
      },
      wrapperCol: {
        xs: { span: 12 },
        sm: { span: 12},
      },
      visible:false,
      num: this.getNumberByWidth(),
      mediaServerList:[],
      total:0,
      model:{},
      queryParam: {
        pageNo: 1,
        pageSize: 10,
      },
    }
  },
  computed: {
  },
  mounted() {
    this.getDate()
  },
  methods: {
    edit(item){
      let model=JSON.parse(JSON.stringify(item))
      this.visible=true
      model.defaultServer?model.defaultServer='是':model.defaultServer='否'
      model.autoConfig?model.autoConfig='是':model.autoConfig='否'
      model.rtpEnable?model.rtpEnable='是':model.rtpEnable='否'
      model.status?model.status='是':model.status='否'
      this.model=model
    },
    handleOk(){
      this.visible=false
    },
    getNumberByWidth(){
        let candidateNums = [1, 2, 3, 4, 6, 8, 12, 24]
        let clientWidth = window.innerWidth - 30;
        let interval = 20;
        let itemWidth = 360;
        let num = (clientWidth + interval)/(itemWidth + interval)
        let result = Math.ceil(24/num);
        let resultVal = 24;
        for (let i = 0; i < candidateNums.length; i++) {
          let value = candidateNums[i]
          if (i + 1 >= candidateNums.length) {
            return  24;
          }
          if (value <= result && candidateNums[i + 1] > result ) {
            return  value;
          }
        }

        return resultVal;
      },
    changePagination(e){
      this.queryParam.pageNo=e
      this.getDate()
    },
    getDate() {
      getQueryById('/video/server/list', this.queryParam).then((res) => {
        if (res.success) {
          this.mediaServerList = res.result.records
          this.total = res.result.total
          this.loading = false
        }
      })
    },

  },
}
</script>
<style scoped lang="less">
@import '~@assets/less/common.less';
.app-list{
  display: flex;
  flex-direction: column;
}
.a_col{
  display: flex;
  justify-content: space-between;
  align-items: center;
  >div{
    width: 100%;
  }
}
.card-img-zlm {
  width: 200px;
  height: 200px;
  background: url('../../../../assets/zlm-logo.png') no-repeat center;
  background-position: center;
  background-size: contain;
  margin: 0 auto;
}
.server-card-default{
    position: absolute;
    left: 20px;
    top: 20px;
    color: #808080;
    font-size: 18px;
  }
	.server-card:hover {
    border: 1px solid #adadad;
  }
  .server-card-status-online{
    position: absolute;
    right: 20px;
    top: 20px;
    color: #3caf36;
    font-size: 18px;
  }
  .server-card-status-offline{
    position: absolute;
    right: 20px;
    top: 20px;
    color: #808080;
    font-size: 18px;
  }
.pagination {
  text-align: right;
  margin: 10px 0 0 0;
}
</style>