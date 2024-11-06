<!--
 * @Author: haocheng123 269887421@qq.com
 * @Date: 2022-06-06 09:19:30
 * @LastEditors: zhaoguiliang 1vt_blybywvlr5@dingtalk.com
 * @LastEditTime: 2023-01-30 16:31:58
 * @FilePath: \sxlinks-cloud-web\src\views\dashboard\UserAnalysis.vue
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
-->
<template>
  <div class="page-header-index-wide">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <baidu-map class="map" :center="center" :zoom="zoom">
    <bm-point-collection :points="points" shape="BMAP_POINT_SHAPE_CIRCLE" color="red" size="BMAP_POINT_SIZE_BIG" @click="clickHandler"></bm-point-collection>
  </baidu-map>
      <!-- <baidu-map class="map" 
      ak="YbGRsQAfQ0UPj89GzcbWPccUIdUua932" 
      :center="center" 
      :zoom="zoom" 
      @ready="handler">
        <bm-marker 
          :position="{lng:item.lng,lat:item.lat}" 
           animation="BMAP_ANIMATION_DROP" 
           NavigationControlType="BMAP_NAVIGATION_CONTROL_LARGE"
           :dragging="true"
           v-for="(item,index) in dataSource1" 
           @click="infoWindowOpens"
           :key="index">
           <bm-info-window :show="show" @close="infoWindowClose" @open="infoWindowOpen">我爱北京天安门</bm-info-window>
          <bm-label :content="item.deviceName" :labelStyle="{color: '#000', fontSize : '12px'}" :offset="{width: -35, height: 35}"/>
        </bm-marker>
      </baidu-map> -->

      <div class="mapLeft">
        <a-select
          ref="select"
          class="input"
          v-model="projectParmas.projectId"
          style="width: 330px"
          @change="projectChange"
          placeholder="请选择项目"
        >
          <a-select-option :value="item.id" v-for="(item, index) in projectNameList" :key="index">{{
            item.name
          }}</a-select-option>
        </a-select>
        <a-table
          :class="'my-index-table'"
          ref="table1"
          class="table"
          size="mini"
          rowKey="id"
          style="height:85%;overflow-y:auto"
          :columns="columns"
          :customRow="rowClick"
          :dataSource="dataSource1"
          :pagination="false"
        >
        </a-table>
        <a-pagination v-model="projectParmas.pageNo" :total='total' show-less-items class="pagination"  size="small"
          :page-size="projectParmas.pageSize" :default-current="projectParmas.pageNo"
          @change="changePagination" />
      </div>
      <div class="mapRight" v-if="show">
        <div class="equipmentMesses">
          <a-tabs default-active-key="1" :tabBarStyle="{ color: '#fff' }">
            <a-tab-pane key="1" tab="设备详情">
              <h2 class="titles">设备信息</h2>
              <div class="detailedWrap">
                <div class="list"><p>设备名称</p><span>{{detailed.deviceName}}</span></div>
                <div class="list"><p>产品编号</p><span>{{detailed.productCode}}</span></div>
                <div class="list"><p>设备编码</p><span>{{detailed.deviceCode}}</span></div>
                <div class="list"><p>节点类型</p><span>{{detailed.nodeType}}</span></div>
                <div class="list"><p>网络类型</p><span>{{detailed.networkType}}</span></div>
                <div class="list"><p>协议类型</p><span>{{detailed.protocolType}}</span></div>
                <div class="list"><p>在线状态	</p><span>{{detailed.activeStatus==0?'离线':'在线'}}</span></div>
              </div>
              <div class="stateWrap">
                <div class="state" v-for="(item,index) in  stateList" :key="index">
                  <img src="../../../assets/equipment.png" alt="">
                  <div class="stateMesses">
                    <span>{{item.propName}}</span>
                    <p>{{ item.value}}<code>{{ item.unit}}</code></p>
                  </div>
                </div>
              </div>
            </a-tab-pane>
            <a-tab-pane key="2" tab="告警日志" force-render>
              <h2 class="titles">告警日志</h2>
              <div class="tabList">
                <template v-if="messes.length">
                  <div class="list" v-for="(item, index) in messes" :key="index">
                  <div class="level">
                    <p class="messesTitle"><span>{{item.type}}级</span></p>
                    <p class="messesTitle">
                      <span>{{ item.deviceName }}</span>
                    </p>
                    <p class="messesTitle"><span>{{item.createTime  }}</span></p>
                  </div>
                  <div class="content">
                    预警内容:<span>{{ item.content }}</span>
                  </div>
                </div>
                </template>
                <a-empty v-else />
              </div>
            </a-tab-pane>
          </a-tabs>
          <a-icon type="close" class="icon" @click="close" />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import JDictSelectTag from '@/components/dict/JDictSelectTag'
import * as echarts from 'echarts'
import { getQueryById, getAction } from '@/api/product'
export default {
  name: 'DeviceMapList',
  components: {
    JeecgListMixin,
    JDictSelectTag,
  },
  data() {
    return {
      detailed:{},
      center: { lng: 118.78221, lat: 30.944371 },
      position: { lng: 118.78221, lat: 30.94499 },
      zoom: 16,
      total:0,
      projectParmas: {
        projectId: undefined,
        pageNo: 1,
        pageSize: 10,
      },
      show: false,
      homeData: {},
      projectNameList: [],
      messes: [],
      visible: false,
      // 表头
      columns: [
        {
          title: '设备名称',
          align: 'left',
          dataIndex: 'deviceName',
        },
        {
          title: '在线状态',
          align: 'center',
          dataIndex: 'activeStatus',
          customRender: function (text) {
            if (text == 0) {
              return '离线'
            } else {
              return '在线'
            }
          },
        },
      ],
      dataSource1: [
      ],
      infoWindowOpen:false,
      points: [],
      stateList:[],
      alarmParams:{
        deviceCode:'',
        pageNo:1,
        pageSize:5
      }
    }
  },
  methods: {
    rowClick(record){
        return {
          on: {
            click: () => {
              this.center.lng = record.lng
              this.center.lat = record.lat
              this.getFloating(record.lat,record.lng)
            }
          }
        }
      },
    infoWindowOpens(){
    },
    close(){
      this.show=false
    },
    clickHandler (currentTarget, point) {
      this.show=false
      let lat=currentTarget.point.lat
      let lng=currentTarget.point.lng
      this.getFloating(lat,lng)
    },
    getFloating(lat,lng){
      this.dataSource1.forEach(res => {
        if(res.lat==lat&&res.lng==lng){
          getAction('/productCenter/device/runtime',{
            deviceCode:res.deviceCode
          }).then((result)=>{
            if(result.success){
              this.stateList=result.data
            }
          })
          this.alarmParams.deviceCode=res.deviceCode
          // 告警历史
          getAction('/dataCenter/alarmDataHistory/list',this.alarmParams).then((result)=>{
            if(result.success){
              this.messes=result.result.records
            }
          })
          this.detailed=res
        }
      });
      this.show=true
    },
    addPoints () {
      this.points=[]
      const points = [];
      for (var i = 0; i < this.dataSource1.length; i++) {
        const position = {lng: this.dataSource1[i].lng, lat: this.dataSource1[i].lat}
        points.push(position)
      }
      this.points = points
    },
    handler({ BMap, map }) {
      console.log(BMap, map)
      this.center.lng = 112.241971
      this.center.lat = 30.063172
      this.zoom = 13
    },
    projectChange(e) {
			
	  this.getProjectById(e);	//先获取项目信息		
      this.getProjectDeviceList() //再获取项目的设备列表
      this.show=false
    },
    getProjectList() {
      getQueryById('/productCenter/project/list', { pageNo: 1, pageSize: 50 }).then((res) => {
        if (res.success) {
          let result = res.result.records
          this.projectNameList = result
          if (result.length) {
            this.projectParmas.projectId = result[0].id
			//将地图中心置为项目的第一个
			this.center.lng = result[0].lng
			this.center.lat = result[0].lat
			//获取项目设备			
            this.getProjectDeviceList()
          }
        }
      })
    },
    getProjectDeviceList() {
		// //将地图中心置为该项目的中心点
		// this.center.lng = result[0].lng
		// this.center.lat = result[0].lat
      let projectParmas = JSON.parse(JSON.stringify(this.projectParmas))
      getAction('/productCenter/device/list', projectParmas).then((res) => {
        if (res.success) {
          this.dataSource1 = res.result.records
          this.total = res.result.total
          this.addPoints()

        }
      })
    },
	getProjectById(projectId) {
	  this.loading = true
	  getAction('/productCenter/project/queryById',{id:projectId}).then((response) => {
		  
	    if(response.result!=null){
			let result=response.result;
			//将地图中心置为项目的第一个
			this.center.lng = result.lng
			this.center.lat = result.lat
		}
	    
	    this.loading = false
	  })
	},
    changePagination(e){
      this.projectParmas.pageNo=e
      this.getProjectDeviceList()
    },
    renderMap() {},
  },
  created() {
    this.addPoints()
  },
  mounted() {
    this.getProjectList()
  },
}
</script>

<style lang="less" scoped>
@import '~@assets/less/common.less';
/deep/.BMap_noprint {
  display: none !important;
}
/deep/.anchorBL {
  display: none !important;
}
/deep/.ant-tabs-tab{
  color: #000;
}
.pagination{
  text-align: right;
  margin: 5px 0 0 0;
}
.table{
  max-height: 666px;
  overflow: auto;
}
.map {
  width: 100%;
  height: 100%;
}
.table-page-search-wrapper {
  height: 100%;
  position: relative;
  .mapLeft {
		//opacity: 0.8;
	background:rgba(255, 255, 255, 0.7);

    position: absolute;
    left: 20px;
    top: 20px;
    width: 350px;
    height: 90%;
    color: #000;
    //background: #fff;
    border-radius: 8px;
    padding: 10px;
    .input {
      margin-bottom: 10px;
    }
    .my-index-table {
      background:rgba(255, 255, 255, 0.7);
    }
  }
  .ant-tabs-nav .ant-tabs-tab {
    color: #000;
  }
  .mapRight {
    padding: 10px;
    position: absolute;
    right: 20px;
    top: 20px;
    width: 400px;
    height: 90%;
    color: #000;
    background:rgba(255, 255, 255, 0.7);
    border-radius: 8px;
    h2 {
      color: #000;
    }
    .equipmentMesses {
      position: relative;
	  background:rgba(255, 255, 255, 0.7);
      .icon{
        position: absolute;
        right: 3px;
        top: 14px;
        &:hover{
          cursor: pointer;
        }
      }
      .titles {
      }
      .tabList {
        overflow: auto;
        max-height: 610;
		background:rgba(255, 255, 255, 0.7);
        .list {
          background:rgba(88, 88, 88, 0.7);
          border-radius: 5px;
          margin-bottom: 3px;
          padding: 6px 3px;
          box-sizing: border-box;
          .level {
            display: flex;
            justify-content: flex-start;
            align-items: center;
            line-height: 25px;
            color: #fff;
            .messesTitle {
              color: #000;
              display: flex;
              justify-content: flex-start;
              margin: 0;
              color: #fff;
              &:nth-of-type(1) {
                width: 100px;
              }
              &:nth-of-type(2) {
                flex: 1;
              }
              &:nth-of-type(3) {
                width: 161px;
              }
              span {
              }
            }
          }
          .content {
            color: #fff;
            display: flex;
            justify-content: flex-start;
            align-items: center;
            margin: 5px 0;
          }
        }
      }
      .detailedWrap{
          border-left: 1px solid #000;
          border-top: 1px solid #000;
          border-radius: 5px;
          margin-bottom: 3px;
          box-sizing: border-box;
        .list{
          border-right: 1px solid #000;
          border-bottom: 1px solid #000;
          display: flex;
          justify-content: space-between;
          align-items: center;
          height: 30px;
          color: #000;
          p{
            flex: 1;
            height: 30px;
            display: flex;
            align-items: center;
            justify-content: flex-start;
            margin-left: 5px;
            margin-bottom: 0;
          }
          span{
            flex: 1;
            height: 30px;
            display: flex;
            padding-left: 5px;
            align-items: center;
            justify-content: flex-start;
            border-left: 1px solid #000;
          }
        }
      }
      .stateWrap{
          display: flex;
          justify-content: flex-start;
          flex-wrap: wrap;
          margin-top: 10px;
          max-height: 228px;
          overflow: auto;
          .state{
            width: 50%;
            justify-content: flex-start;
            align-items: center;
            display: flex;
           height: 50px;
            img{
              width:40px;
              height:40px
            }
            .stateMesses{
              color: #000;
              display: flex;
              flex-direction: column;
              margin-left: 18px;
              span{
                font-size: 17px;
                color: #818AA9;
              }
              p{
                margin: 0;
                color: #000;
                font-size: 14px;
                code{
                  margin-left: 10px;
                  font-weight: bold;
                }
              }
            }
          }
        }
    }
    .EquipmentStatus {
      color: #fff;
      h2 {
      }
      > p {
        margin: 0;
        color: red;
        position: relative;
        padding-left: 8px;
        &::after {
          position: absolute;
          content: '';
          width: 5px;
          height: 5px;
          left: 0px;
          top: 8px;
          background: red;
          border-radius: 50%;
        }
      }
      .statusList {
        display: flex;
        justify-content: flex-start;

        flex-wrap: wrap;
        .list {
          width: 50%;
          display: flex;
          justify-content: flex-start;
          align-items: center;
          padding-left: 3%;
          > div {
            margin-left: 12%;
            margin-bottom: 10px;
          }
          p {
            margin: 0;
            line-height: 30px;
          }
          span {
          }
        }
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
.page-header-index-wide {
  height: 100%;
  .card-title {
    color: rgba(0, 0, 0, 0.65);
    font-weight: bold;
    font-size: 18px;
    border-bottom: 1px solid #000;
    padding-bottom: 5px;
  }

  .card-content {
    // background-color: pink;
    margin-top: 15px;
    height: 110px;
    display: flex;
    justify-content: space-between;

    .card-item {
      // background-color: red;
      flex: 1;
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      padding: 10px;
      border: 1px solid #000;
      box-shadow: 3px 3px 2px 2px rgba(0, 0, 0, 0.2);
      font-size: 16px;
      font-weight: 600;

      .value {
        font-size: 40px;
      }

      &:nth-child(2) {
        margin: 0 25px;
      }
    }
  }

  .echart-content {
    margin-top: 15px;
    height: 110px;
    display: flex;
    justify-content: center;

    .card-item {
      // background-color: red;
      // flex: 1;
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      width: 33%;
      padding: 10px;
      border: 1px solid #000;
      box-shadow: 3px 3px 2px 2px rgba(0, 0, 0, 0.2);
      font-size: 16px;
      font-weight: 600;
      margin: 0 25px;

      .value {
        font-size: 40px;
      }

      // &:nth-child(2) {
      //   margin: 0 25px;
      // }
    }

    .echart-container {
      width: 120px;
      height: 120px;
      margin-right: 10%;
    }

    .card-number {
      display: flex;
      flex-direction: column;
      font-size: 16px;
      font-weight: 600;
      justify-content: center;
      padding-top: 15px;

      .row {
        display: flex;
        flex-direction: row;
        margin-bottom: 15px;
        .label,
        .unit {
          display: flex;
          align-items: center;
        }
        .value {
          font-size: 24px;
          margin: 0 15px;
        }
      }
    }
  }
}
</style>