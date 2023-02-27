var mu = {
	longTitle: '筋斗云物流管理系统',
	littleTitle: '后台',
	userItems:[
		{
			iconName: 'home',
			name: '首页',
			id: 1,
			routerName: 'foreHomeView',
			disabled: false
		}
		,
		{
			iconName: 'home',
			name: '运单追踪',
			id: 2,
			routerName: 'userParcelManage',
			disabled: false
		}
		,
		{
			iconName: 'home',
			name: '我要寄件',
			id: 3,
			routerName: 'UserOrder',
			disabled: false
		}
		,
		{
			iconName: 'img',
			name: '服务支持',
			id: 4,
			submenu: [ {
				iconName: 'img',
				name: '订单投诉',
				id: 2,
				routerName: 'foreComplaint',
				disabled: false
			},{
				iconName: 'img',
				name: '运费时效',
				id: 1,
				routerName: 'foreAgingQuery',
				disabled: false
			}

			]
		},
		{
			iconName: 'img',
			name: '设置',
			id: 5,
			submenu: [{
				iconName: 'img',
				name: '个人中心',
				id: 1,
				routerName: 'roleManage',
				disabled: false
			}, {
				iconName: 'img',
				name: '退出',
				id: 2,
				routerName: 'login',
				disabled: false
			}]
		},

	]
}
export default mu;
