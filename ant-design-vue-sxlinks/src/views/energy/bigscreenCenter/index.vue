<template>
	<a-card :bordered="false">
		<div class="screen" :style="{ height: H + 'px' }">
			<div class="screen-left">
				<div class="screen-left-t">
					<div class="title">空调状态</div>
					<div class="air">
						<ul>
							<li class="special">
								<p>设备名称 </p>
								<p>运行状态 </p>
								<p>模式</p>
								<p>设置温度值 </p>
								<p>风速</p>
							</li>
							<li v-for="(item, index) in airList" :key="index" @click="getAircondiDetails(item.id)">
								<p>{{ item.deviceName }}</p>
								<p>{{ item.runState==0?'关机':'开机'}}</p>
								<p>{{ mode[item.mode] }}</p>
								<p>{{ item.setupTemperature }}</p>
								<p>{{ item.windSpeed }}</p>
							</li>
						</ul>
					</div>
				</div>
				<div class="screen-left-c">
					<div class="title">设备在线状态</div>
					<div class="line">
						<div class="lineLeft" id="lineLeft"></div>
						<div class="lineRight">
							<div class="lineheader">{{state}}设备编号</div>
							<div class="lineWrap">
								<ul v-if="lineList.length>0">
									<li v-for="(item, index) in lineList" :key="index">
										<i>{{ index + 1 }}</i><span>{{ item.deviceName }}</span>
									</li>
								</ul>
								<ul v-else>
									<li>暂无数据</li>
								</ul>
							</div>
						</div>
					</div>
				</div>
				<div class="screen-left-b">
					<div class="title">门店安全</div>
					<div class="security">
						<div class="securityWrap" v-for="(item, index) in securityList" :key="index">
							<span>{{ item.name }}</span>
							<p>{{ item.title==0?"无报警":"报警" }}</p>
						</div>
					</div>
				</div>
			</div>
			<div class="screen-contant">
				<div class="screen-contant-t">
					<div class="pattern">
						<div class="patternList" v-for="(item, index) in patternList" :key="index" v-show="item.state">
							<div class="pic"><img :src="item.pic" /></div>
							<div class="patternCon">
								<span>{{ item.messes }}</span>
								<code v-if="item.id=='mode'">{{ mode[item.name] }}</code>
								<code v-else-if="item.id=='runState'">{{ runState[item.name] }}</code>
								<code v-else-if="item.id=='infraredHuman'">{{ infraredHuman[item.name] }}</code>
								<code v-else>{{ item.name}}</code>
							</div>
						</div>
					</div>
				</div>
				<div class="screen-contant-c" id="address-contant">DIV</div>
				<div class="screen-contant-b">
					<div class="title">告警管理</div>
					<div class="giveAlarm">
						 <ul>
							              <li class="special">
								                <p>设备|属性|内容 </p>

								                <p>时间 </p>
								               
								               
								<!-- <p>当前值</p> -->
								              </li>
							              <li v-for="(item, index) in alarmDataHistory" :key="index">
								                <p>{{ item.deviceName }}&nbsp;|&nbsp;
									{{ item.propertyName }}&nbsp;|&nbsp;
									{{ item.content }}</p>
								               
								<!-- <p>{{ item.deviceLocation }}</p> -->
								                <p>{{ item.createTime}}</p>
								               
								               
								<!-- <p>{{ item.value }}</p> -->
								              </li>
							            </ul>
					</div>
				</div>
			</div>
			<div class="screen-right">
				<div class="screen-right-t">
					<div class="title" style="display: flex;justify-content: space-between;">电量统计<a-button type="primary" @click="block">返回</a-button>
					</div>
					<div class="electric">
						<div class="electricTitle">
							<div class="electricList" v-for="(item, index) in electricList" :key="index">
								<span>{{ item.name }}</span>
								<code>{{ item.value }}</code>
							</div>
						</div>
						<div class="electricCharts" id="electricCharts"></div>
					</div>
				</div>
				<div class="screen-right-c">
					<div class="title">环境监测</div>
					<div class="environment">
						<div class="environmentWrap" v-for="(item, index) in environmentList" :key="index">
							<div class="name">{{ item.title }}</div>
							<div class="val">
								<span>{{ item.val }}</span>
								<code>{{ item.unit }}</code>
							</div>
						</div>
					</div>
				</div>
				<div class="screen-right-b">
					<div class="title">热量统计</div>
					<div class="quantity">
						<div class="quantityHead">
							<span>瞬时流量</span>
							<div class="num">{{liuliang>1000?(liuliang/1000).toFixed(2)+'K':liuliang}}Wh</div>
						</div>
						<div class="quantityCharts">
							<div class="quantityChartsL" id="quantityChartsL"></div>
							<div class="quantityChartsr" id="quantityChartsr"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</a-card>
</template>

<script>
	var timer = null
	import * as echarts from 'echarts'
	import {
		getListProperty,
		getAction,
		getQueryById,
	} from '@/api/product'

	export default {
		name: 'EnergyBigscreenCenter',
		components: {

		},
		data() {
			return {
				liuliang: 0,
				state: '离线',
				H: 0,
				alarmDataHistory: [],
				airList: [],
				electricList: [{
						name: '电压',
						value: '234.5V',
					},
					{
						name: '电流',
						value: '12A',
					},
					{
						name: '耗电量',
						value: '234.5kWH',
					},
				],
				lineList: [],
				patternList: [{
						pic: require('./images/switch.png'),
						messes: '开关机状态',
						name: '关机',
						id: 'runState',
						state: true,
					},
					{
						pic: require('./images/mode.png'),
						messes: '模式',
						name: '制冷',
						id: "mode",
						state: true,
					},
					{
						pic: require('./images/temperature.png'),
						messes: '设置温度',
						name: '26°C',
						id: 'setupTemperature',
						state: true,
					},
					{
						pic: require('./images/speed.png'),
						messes: '风速',
						name: '3级',
						id: 'windSpeed',
						state: true,
					},
					{
						pic: require('./images/infra.png'),
						messes: '红外',
						name: '无人',
						id: 'infraredHuman',
						state: true,
					},
					{
						pic: require('./images/tempera.png'),
						messes: '温度',
						name: '14°C',
						id: 'temperatureValue',
						state: true,
					},
					{
						pic: require('./images/humidity.png'),
						messes: '湿度',
						name: '73%',
						id: 'humidnessValue',
						state: true,
					},
					{
						pic: require('./images/prohibit.png'),
						messes: '禁用',
						name: '否',
						id: 'o',
						state: true,
					},
				],
				securityList: [{
						name: '智能断路器',
						title: '合闸',
						id: 'duanluqi',
					},
					{
						name: '烟雾报警器',
						title: '雾探测',
						id: 'yanwu',
					},
					{
						name: '燃气报警器',
						title: '设备信息',
						id: 'ranqi',
					},
					{
						name: '水漫传感器',
						title: '正常',
						id: 'shuiman',
					},
					{
						name: '声光报警器',
						title: '告警',
						id: 'shengguang',
					},
					{
						name: '门窗检测器',
						title: '关门',
						id: 'menchuang',
					},
				],
				environmentList: [{
						title: 'VOC浓度',
						val: 0.1,
						unit: 'mg/m3',
						id: 'voc',
					},
					{
						title: '甲醇浓度',
						val: 0.05,
						unit: 'mg/m3',
						id: 'jiacun',
					},
					{
						title: 'co2浓度',
						val: 767,
						unit: 'ppm',
						id: 'co2',
					},
					{
						title: 'PM2.5浓度',
						val: 99,
						unit: 'ug/m3',
						id: 'pm25',
					},
				],
				mode: {
					0: '自动',
					1: '制冷',
					2: '制热',
					3: '除湿',
					4: '送风',
					5: '睡眠',
				},
				runState: {
					0: '关机',
					1: '开机',
				},
				infraredHuman: {
					0: '无人',
					1: '有人',
				},
				queryAlarmParam : {
						pageNo: 1,
						pageSize: 4,
					},
			}
		},
		created() {},
		mounted() {
			window.onresize = this.findDimensions
			this.H = document.body.clientHeight
			document.querySelector('.ant-layout-sider-dark').style.display = 'none'
			document.querySelector('.ant-layout-header').style.display = 'none'
			document.querySelector('.tab-layout-tabs').style.display = 'none'
			document.querySelector('.ant-layout-footer').style.display = 'none'
			document.querySelectorAll('.ant-layout-content>div')[1].style.margin = 0
			document.getElementsByTagName('body')[0].style.overflow = 'hidden'
			setTimeout(() => {
				this.column()
			})
			if (timer) {
				clearInterval(timer)
			}
			timer = setInterval(() => {
				this.queryOnline()
				this.quantity()
				this.getEnvironment()
				this.getSecurity()
				this.getAlarmDataHistory()
				this.getSummaryElectricData()
				this.getAirconditionData()
				this.renderMap()
			}, 1000 * 60 * 5)
			this.queryOnline()
			this.quantity()
			this.getEnvironment()
			this.getSecurity()
			this.getAlarmDataHistory()
			this.getSummaryElectricData()
			this.getAirconditionData()
			this.renderMap()
		},
		computed: {},
		destroyed() {
			document.querySelector('.ant-layout-sider-dark').style.display = 'block'
			document.querySelector('.ant-layout-header').style.display = 'block'
			document.querySelector('.tab-layout-tabs').style.display = 'block'
			document.querySelector('.ant-layout-footer').style.display = 'block'
			document.getElementsByTagName('body')[0].style.overflow = 'auto'
			document.querySelectorAll('.ant-layout-content>div')[1].style.margin = '12px'
			if (timer) {
				clearInterval(timer)
			}
			location.href = "/dashboard/analysis"
		},
		methods: {
			block() {
				location.href = "/dashboard/analysis"
			},
			renderMap() {
				let that = this
				let map = new window.BMap.Map("address-contant"); // 创建Map实例
				map.centerAndZoom(new window.BMap.Point(114.420005, 30.557869), 13); // 初始化地图,设置中心点坐标和地图级别
				var point = new window.BMap.Point(114.267206, 30.58572, 888);
				map.addControl(new window.BMap.MapTypeControl()); //添加地图类型控件
				map.setCurrentCity("武汉市"); // 设置地图显示的城市 此项是必须设置的
				map.enableScrollWheelZoom();

				let marker = new window.BMap.Marker(point); // 创建标注    
				map.addOverlay(marker);
				marker.setAnimation();
				// 创建点标记
				// var marker1 = new BMapGL.Marker(new BMapGL.Point(114.267206,30.58572));
				// map.setCurrentCity("华中科技大学同济医学院附属同济医院");
				// 在地图上添加点标记
				// map.addOverlay(marker1);
				var opts = {
					width: 200,
					height: 100,
					title: '武汉市'
				};
				var infoWindow = new BMap.InfoWindow('地址：武汉市', opts);
				// 点标记添加点击事件
				marker.addEventListener('click', function() {
					map.openInfoWindow(infoWindow, point); // 开启信息窗口
				});
				var mapStyle = {
					style: 'midnight',
				};
				map.setMapStyle(mapStyle);
			},
			queryOnline() {
				// 在线
				let queryOnline = null;
				let queryOffline = null;
				getListProperty('/productCenter/device/queryOnline').then((res) => {
					if (res.message == "未找到设备信息") {
						queryOnline = []
					} else {
						queryOnline = res.result
					}
				})
				//查询离线设备
				getListProperty('/productCenter/device/queryOffline').then((res) => {

					if (res.message == "未找到设备信息") {
						queryOffline = []
					} else {
						queryOffline = res.result
					}

				})
				//查询所有设备
				getListProperty('/productCenter/device/queryAll').then((res) => {

					if (res.success) {
						this.lineList = res.result
						this.drawLine(this.lineList.length, queryOnline, queryOffline)
					}

				})
				
			},
			// 热量统计
			quantity() {
				getListProperty('/energyCenter/energyData/queryAll').then((res) => {
					if (res.result) {
						this.liuliang = res.result.reduce((total, arr) => total + arr.liuliang, 0)
						let reliang = res.result.reduce((total, arr) => total + arr.reliang, 0)
						let lengliang = res.result.reduce((total, arr) => total + arr.lengliang, 0)
						this.meterCharts('quantityChartsL', '热量', reliang) //仪表
						this.meterCharts('quantityChartsr', '冷量', lengliang) //仪表
					}
				})
			},
			getEnvironment() {
				getListProperty('/energyCenter/environmentData/list').then((res) => {
					if (res.success) {
						this.environmentList.forEach(element => {
							for (let item in res.result.records[0]) {
								if (element.id == item) {
									element.val = res.result.records[0][item]
								}
							}
						});
					}
				})
			},
			// 门店安全
			getSecurity() {
				getListProperty('/energyCenter/safeData/list').then((res) => {
					if (res.success) {
						this.securityList.forEach(element => {
							for (let item in res.result.records[0]) {
								if (element.id == item) {
									element.title = res.result.records[0][item]
								}
							}
						});
					}
				})
			},
			// 告警管理
			getAlarmDataHistory() {
				
					getQueryById('/dataCenter/alarmData/list', this.queryAlarmParam).then((res) => {
						if (res.success) {
							this.alarmDataHistory = res.result.records
						}
					})
			},
			// 电量统计
			getSummaryElectricData() {
				getListProperty('/energyCenter/summaryElectricData/listHour').then((res) => {
					if (res.success) {
						// this.patternList
						console.log(res.result.records, "电量统计");
					}
				})
			},
			// 空调状态
			getAirconditionData() {
				getListProperty('/energyCenter/airconditionData/list', {
					pageNo: 1,
					pageSize: 100
				}).then((res) => {
					if (res.success) {
						this.airList = res.result.records
						this.getAircondiDetails(this.airList[0].id)
					}
				})
			},
			// 空调详情
			getAircondiDetails(id) {
				getListProperty('/energyCenter/airconditionData/queryById?id=' + id).then((res) => {
					if (res.success) {
						this.patternList.forEach(element => {
							for (let item in res.result) {
								if (element.id == item) {
									element.name = res.result[item]
								}
							}
						});
						//  name: '关机',
						// id:'runState',

						this.patternList.forEach(element => {
							if (element.id == 'runState' && element.name == 0) {
								for (let i = 1; i < 8; i++) {
									this.patternList[i].state = false
								}
							}
						})
						this.patternList.forEach(element => {
							if (element.id == 'runState' && element.name == 1) {
								for (let i = 0; i < 8; i++) {
									this.patternList[i].state = true
								}
							}
						})
					}
				})
			},
			findDimensions() {
				this.H = document.body.clientHeight
			},
			column() {
				// 基于准备好的dom，初始化echarts实例
				let myChart = echarts.init(document.getElementById('electricCharts'))
				// 绘制图表
				myChart.setOption({
					tooltip: {
						trigger: 'axis',
						// axisPointer: {
						//   type: "line",
						//   lineStyle: {
						//     opacity: 0,
						//   },
						// },
					},
					legend: {
						show: true,
						color: '#fff',
						icon: 'circle',
						textStyle: {
							color: '#fff',
							fontSize: 12,
						},
					},
					grid: {
						left: '7%',
						right: '7%',
						bottom: '5%',
						top: '7%',
						height: '85%',
						containLabel: true,
						z: 22,
					},
					xAxis: [{
						type: 'category',
						gridIndex: 0,
						data: ['一月', '二月', '三月', '四月', '五月', '六月', '七月'],
						axisTick: {
							alignWithLabel: true,
						},
						axisLine: {
							lineStyle: {
								color: '#0c3b71',
							},
						},
						axisLabel: {
							show: true,
							color: 'rgb(170,170,170)',
							fontSize: 12,
						},
					}, ],
					yAxis: [{
						type: 'value',
						gridIndex: 0,
						splitLine: {
							show: false,
						},
						axisTick: {
							show: false,
						},
						axisLine: {
							lineStyle: {
								color: '#0c3b71',
							},
						},
						axisLabel: {
							color: 'rgb(170,170,170)',
							formatter: '{value}',
						},
					}, ],
					series: [{
						name: '合格率',
						type: 'bar',
						barWidth: '30%',
						xAxisIndex: 0,
						yAxisIndex: 0,
						itemStyle: {
							normal: {
								barBorderRadius: 30,
								color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
										offset: 0,
										color: '#00ffff',
									},
									{
										offset: 0.5,
										color: '#027eff',
									},
									{
										offset: 1,
										color: '#0286ff',
									},
								]),
							},
						},
						data: [{
								name: '一月',
								value: 80,
							},
							{
								name: '二月',
								value: 87.8,
							},
							{
								name: '三月',
								value: 71,
							},
							{
								name: '四月',
								value: 80,
							},
							{
								name: '五月',
								value: 66,
							},
							{
								name: '六月',
								value: 80,
							},
							{
								name: '七月',
								value: 80,
							},
						],
						zlevel: 11,
					}, ],
				})
				window.addEventListener("resize", function() {
					myChart.resize();
				});
			},
			drawLine(sum, queryOnline, queryOffline) {
				// 基于准备好的dom，初始化echarts实例
				let myChart = echarts.init(document.getElementById('lineLeft'))
				// 绘制图表
				myChart.setOption({
					color: ['#A0CE3A', '#31C5C0'],
					title: {
						text: '总数',
						subtext: sum,
						textStyle: {
							color: '#f2f2f2',
							fontSize: 14,
							// align: 'center'
						},
						subtextStyle: {
							fontSize: 16,
							color: ['#ff9d19'],
						},
						x: 'center',
						y: 'center',
					},
					grid: {
						bottom: 150,
						left: 100,
						right: '10%',
					},
					legend: {
						orient: 'horizontal',
						top: '20',
						textStyle: {
							color: '#f2f2f2',
							fontSize: 12,
						},
					},
					series: [
						// 主要展示层的
						{
							radius: ['45%', '65%'],
							center: ['50%', '55%'],
							type: 'pie',
							label: {
								normal: {
									show: false,
									formatter: '{c}%',
									textStyle: {
										fontSize: 30,
									},
									position: 'outside',
								},
								emphasis: {
									show: true,
								},
							},
							labelLine: {
								normal: {
									show: true,
									length: 30,
									length2: 55,
								},
								emphasis: {
									show: true,
								},
							},
							name: '设备在线数量',
							data: [{
									name: '在线',
									array: JSON.stringify(queryOnline),
									value: queryOnline.length,
								},
								{
									name: '离线',
									array: JSON.stringify(queryOffline),
									value: queryOffline.length,
								},
							],
						},
					],
				})
				window.addEventListener("resize", function() {
					myChart.resize();
				});
				let that = this
				myChart.on('click', function(param) {
					that.lineList = JSON.parse(param.data.array)
					that.state = param.name
				});
			},
			meterCharts(id, text, value) {
				// 基于准备好的dom，初始化echarts实例
				let myChart = echarts.init(document.getElementById(id))
				// 绘制图表
				myChart.setOption({
					title: {
						text: text,
						x: 'center',
						y: '0',
						textStyle: {
							color: '#fff',
							fontSize: 12,
						},
					},
					tooltip: {
						formatter: '{a} <br/>{b} : {c}%',
					},
					toolbox: {
						feature: {},
					},
					series: [{
						name: '业务指标',
						type: 'gauge',
						detail: {
							formatter: '{value}℃',
							textStyle: {
								fontSize: 12,
								color: '#fff',
							},
						},
						axisLabel: {
							// show:false,
							distance: -5,
							fontSize: 8,
							splitNumber: 5,
							color: '#00ffff',
						},
						radius: '70%',
						axisTick: {
							distance: -10,
							length: 5,
							lineStyle: {
								width: 2,
								color: '#fff',
							},
						},
						splitLine: {
							show: false,
						},
						pointer: {
							length: '50%',
						},
						data: [{
							value: value,
							name: '',
						}, ],
						axisLine: {
							show: true,
							lineStyle: {
								width: 10,
								color: [
									[
										1,
										new echarts.graphic.LinearGradient(0, 0, 1, 0, [{
												offset: 0.1,
												color: '#FFC600',
											},
											{
												offset: 0.6,
												color: '#30D27C',
											},
											{
												offset: 1,
												color: '#0B95FF',
											},
										]),
									],
								],
							},
						},
					}, ],
				})
				window.addEventListener("resize", function() {
					myChart.resize();
				});
			},
		},
	}
</script>
<style scoped lang='less'>
	@import '~@assets/less/common.less';

	/deep/.ant-card-body {
		padding: 0;
	}

	.title {
		height: 30px;
		line-height: 30px;
		padding-left: 8px;
		border-left: 4px solid rgb(7, 95, 91);
		font-size: 16px;
		color: #fff;
		color: #fafafa;
		letter-spacing: 0;
		margin-bottom: 10px;
		text-shadow: 0px 1px 0px #999, 0px 2px 0px #888, 0px 3px 0px #777, 0px 4px 0px #666, 0px 5px 0px #555,
			0px 6px 0px #444, 0px 7px 0px #333, 0px 8px 7px #001135;
	}

	.screen {
		background: url(./images/bg05.png) no-repeat center center;
		background-size: 100% 100%;
		display: flex;
		padding: 5px;
		box-sizing: border-box;
		overflow: hidden;

		.screen-left {
			flex: 1;
			display: flex;
			flex-direction: column;

			.screen-left-t {
				height: 35%;

				.air {
					height: calc(100% - 30px);
					overflow-y: auto;

					ul {
						margin: 0;
						padding: 0;

						li {
							color: #fff;
							display: flex;
							justify-content: center;
							align-items: center;
							line-height: 42px;

							&:hover {
								cursor: pointer;
								background: rgba(2, 126, 255, 0.2);
							}

							// &:nth-of-type(1){
							//   background: #2f9cb4;
							// }
							&:nth-child(odd) {
								background: rgba(16, 42, 47, 0.5);
							}

							p {
								text-align: center;
								flex: 1;
								display: flex;
								justify-content: center;
								align-items: center;
								margin: 0;
							}
						}
					}
				}
			}

			.screen-left-c {
				height: 31%;
				margin: 1% 0;

				.line {
					display: flex;
					justify-content: flex-start;
					align-items: center;
					height: calc(100% - 40px);
					background: url(./images/dataBg.png) no-repeat center center;
					background-size: 100% 100%;

					.lineLeft {
						flex: 1;
						height: 100%;
					}

					.lineRight {
						flex: 1.2;
						height: 100%;
						padding: 12px 0;
						box-sizing: border-box;

						.lineheader {
							color: #fff;
							height: 30px;
							font-size: 12px;
							line-height: 30px;
							padding-left: 31px;
							background: rgba(48, 82, 174, 0.1);
						}

						.lineWrap {
							height: calc(100% - 30px);
							overflow-y: auto;

							ul {
								margin: 0;
								padding: 0;

								li {
									color: #fff;
									display: flex;
									justify-content: flex-start;
									align-items: center;
									list-style-type: none;
									height: 30px;
									font-size: 12px;

									&:nth-child(2n) {
										background: rgba(48, 82, 174, 0.1);
									}

									i {
										margin-left: 5px;
										display: inline-block;
										width: 20px;
										height: 20px;
										border-radius: 50%;
										background: #027eff;
										text-align: center;
										line-height: 20px;
										font-size: 12px;
										margin-right: 8px;
									}

									span {}
								}
							}
						}
					}
				}
			}

			.screen-left-b {
				height: 33%;

				.security {
					display: flex;
					justify-content: flex-start;
					flex-wrap: wrap;
					height: calc(100% - 40px);
					background: url(./images/dataBg.png) no-repeat center center;
					background-size: 100% 100%;

					.securityWrap {
						height: 50%;
						width: 33%;
						color: #fff;
						display: flex;
						flex-direction: column;
						align-items: center;
						justify-content: center;
						position: relative;

						span {
							text-align: center;
							display: block;
							line-height: 48px;
							font-size: 12px;
						}

						p {
							text-align: center;
							font-size: 20px;
							margin: 0;
							font-weight: bold;
							color: #00ffff;
						}

						&:nth-of-type(1),
						&:nth-of-type(4),
						&:nth-of-type(2),
						&:nth-of-type(5) {
							&::after {
								position: absolute;
								right: 0;
								top: 43%;
								width: 2px;
								height: 31%;
								content: '';
								background: #00ffff;
							}
						}
					}
				}
			}
		}

		.screen-contant {
			flex: 2;
			display: flex;
			flex-direction: column;
			margin: 0 20px;

			.screen-contant-t {
				height: 25%;
				display: flex;
				justify-content: center;
				align-items: center;

				.pattern {
					height: 80%;
					width: 90%;
					display: flex;
					justify-content: flex-start;
					flex-wrap: wrap;

					.patternList {
						width: 22%;
						margin-right: 4%;
						display: flex;
						justify-content: flex-start;
						align-items: center;
						background: url(./images/border_bg01.png) no-repeat;
						background-size: 100% 100%;
						height: 44%;
						padding: 10px;

						&:nth-of-type(1),
						&:nth-of-type(2),
						&:nth-of-type(3),
						&:nth-of-type(4) {
							margin-bottom: 3%;
						}

						&:nth-of-type(4),
						&:nth-of-type(8) {
							margin-right: 0%;
						}

						.pic {
							width: 40px;
							height: 40px;
							border-radius: 50%;
							margin-right: 15px;

							img {
								width: 100%;
								height: 100%;
							}
						}

						.patternCon {
							flex: 1;
							color: #fff;
							display: flex;
							flex-direction: column;
							align-items: center;
							justify-content: center;

							span {
								text-align: left;
								display: block;
								font-size: 12px;
								width: 100%;
								line-height: 30px;
							}

							code {
								text-align: left;
								display: block;
								color: #00ffff;
								font-size: 16px;
								width: 100%;
								font-weight: bold;
							}
						}
					}
				}
			}

			.screen-contant-c {
				height: 46%;
				margin: 1% 0;
			}

			.screen-contant-b {
				height: 25%;

				.giveAlarm {
					height: calc(100% - 30px);
					overflow-y: auto;
					background: url(./images/dataBg.png) no-repeat center center;
					background-size: 100% 100%;
					padding: 15px;

					ul {
						margin: 0;
						padding: 0;

						li {
							color: #fff;
							display: flex;
							justify-content: center;
							align-items: center;
							line-height: 33px;

							// &:nth-of-type(1){
							//   background: #2f9cb4;
							// }
							&:nth-child(odd) {
								background: rgba(16, 42, 47, 0.5)
							}

							p {
								text-align: center;
								flex: 1;
								display: flex;
								justify-content: center;
								align-items: center;
								margin: 0;
							}
						}
					}
				}
			}
		}

		.screen-right {
			flex: 1;
			display: flex;
			flex-direction: column;

			.screen-right-t {
				height: 33%;

				.electric {
					height: calc(100% - 40px);
					background: url(./images/dataBg.png) no-repeat center center;
					background-size: 100% 100%;

					.electricTitle {
						display: flex;
						justify-content: flex-start;
						align-items: center;
						color: #fff;
						height: 80px;

						.electricList {
							flex: 1;
							display: flex;
							flex-direction: column;
							align-items: center;
							position: relative;

							span {
								line-height: 30px;
							}

							code {
								color: #00ffff;
								font-weight: bold;
								font-size: 16px;
							}

							&::after {
								content: '';
								position: absolute;
								right: 0;
								top: 19%;
								width: 2px;
								height: 75%;
								background: #00ffff;
							}

							&:nth-of-type(3) {
								&::after {
									content: '';
									position: absolute;
									right: 0;
									top: 19%;
									width: 0px;
									height: 0;
								}
							}
						}
					}

					.electricCharts {
						height: calc(100% - 80px);
					}
				}
			}

			.screen-right-c {
				height: 31%;
				margin: 1% 0;

				.environment {
					display: flex;
					justify-content: flex-start;
					flex-wrap: wrap;
					height: calc(100% - 40px);
					background: url(./images/dataBg.png) no-repeat center center;
					background-size: 100% 100%;
					padding: 15px 10px;

					.environmentWrap {
						height: 50%;
						width: 50%;
						color: #fff;
						display: flex;
						flex-direction: column;
						align-items: center;
						justify-content: center;

						.name {
							width: 100%;
							padding-left: 30%;
							text-align: left;
							display: block;
							line-height: 30px;
							font-size: 16px;
						}

						.val {
							width: 100%;
							padding-left: 30%;

							span {
								color: #00ffff;
								font-size: 32px;
								font-weight: bold;
								margin-right: 10px;
							}

							code {
								font-size: 16px;
							}
						}
					}
				}
			}

			.screen-right-b {
				height: 35%;

				.quantity {
					height: calc(100% - 40px);
					background: url(./images/dataBg.png) no-repeat center center;
					background-size: 100% 100%;

					.quantityHead {
						color: #fff;
						display: flex;
						justify-content: center;
						align-items: center;
						flex-direction: column;
						height: 90px;

						span {
							font-size: 14px;
							line-height: 30px;
						}

						div {
							font-size: 26px;
							color: #00ffff;
							font-weight: bold;
						}
					}

					.quantityCharts {
						height: calc(100% - 90px);
						display: flex;

						>div {
							flex: 1;
						}
					}
				}
			}
		}
	}

	.special {
		background: rgb(36, 123, 123, 0.3) !important;
	}
</style>
