import { createRouter, createWebHistory } from 'vue-router'
import index from '../views/back/system/index.vue'
import listTransPoint from '../views/back/system/listTransPoint.vue'
import system from '../views/back/system/homeView.vue'
import foreUserManage from '../views/back/system/foreUserManage.vue'
import managerManage from '../views/back/system/managerManage.vue'
import netPointManage from '../views/back/system/netPointManage.vue'
import postmanManage from '../views/back/system/postmanManage.vue'
import roleManage from '../views/back/system/roleManage.vue'
import pickupManage from "../views/back/system/pickupManage.vue";
import deliverManage from "../views/back/system/deliverManage.vue";
import takeoverMedManage from "../views/back/system/takeoverMedManage.vue";
import transportMedManage from "../views/back/system/transportMedManage.vue";
import specifiesSendManage from "../views/back/system/specifiesSendManage.vue";
import UserOrder from "../views/fore/UserOrder.vue";
import sendManage from "../views/back/system/sendManage.vue";
import userParcelManage from "../views/fore/userParcelManage.vue";
import foreHome from "../views/fore/foreHome.vue";
import foreHomeView from "../views/fore/foreHomeView.vue";
import foreComplaint from "../views/fore/foreComplaint.vue";
import packageList from "../views/back/system/packageList.vue";
import foreAgingQuery from "../views/fore/foreAgingQuery.vue";
import agingQueryManage from "../views/back/system/agingQueryManage.vue";


const routes = [
    {
        path: '/',
        name: 'login',
        component: () => import('../components/login.vue')
    },
    {
        path: '/backLogin',
        name: 'backLogin',
        component: () => import('../views/back/backLogin.vue')
    },{
    path:'/system/',
        name: 'system',
        component:system,
    //    子路由
        children:[
         {
            path: 'index',
            name: 'index',
            component: index,
            meta: {
                title: '首页',
            }
        },
            {
        path:'listTransPoint',
            name:'listTransPoint',
            component: listTransPoint ,
            meta:{
            title:'后台用户管理',
                isTab:true
            }
        },
            {
                path:'foreUserManage',
                name:'foreUserManage',
                component: foreUserManage ,
                meta:{
                    title:'用户投诉管理',
                    isTab:true
                }
            },
            {
                path:'managerManage',
                name:'managerManage',
                component: managerManage ,
                meta:{
                    title:'角色列表',
                    isTab:true
                }
            },

            {
                path:'packageList',
                name:'packageList',
                component: packageList ,
                meta:{
                    title:'订单列表',
                    isTab:true
                }
            },
            {
              path:'agingQueryManage',
              name:'agingQueryManage',
              component: agingQueryManage,
                meta:{
                    title:'运费时效管理',
                    isTab:true
                }
            },



            {
                path:'netPointManage',
                name:'netPointManage',
                component: netPointManage ,
                meta:{
                    title:'前台用户列表',
                    isTab:true
                }
            },
            {
                path:'postmanManage',
                name:'postmanManage',
                component: postmanManage ,
                meta:{
                    title:'快递员管理',
                    isTab:true
                }
            },
            {
                path:'roleManage',
                name:'roleManage',
                component: roleManage ,
                meta:{
                    title:'权限列表',
                    isTab:true
                }
            },
            {
                path:'pickupManage',
                name:'pickupManage',
                component: pickupManage ,
                meta:{
                    title:'揽收管理',
                    isTab:true
                }
            },
            {
                path:'deliverManage',
                name:'deliverManage',
                component: deliverManage ,
                meta:{
                    title:'网点发货管理',
                    isTab:true
                }
            },
            {
                path:'takeoverMedManage',
                name:'takeoverMedManage',
                component: takeoverMedManage ,
                meta:{
                    title:'中途收货管理',
                    isTab:true
                }
            },
            {
                path:'transportMedManage',
                name:'transportMedManage',
                component: transportMedManage ,
                meta:{
                    title:'中途运输管理',
                    isTab:true
                }
            },
            {
                path:'specifiesSendManage',
                name:'specifiesSendManage',
                component: specifiesSendManage ,
                meta:{
                    title:'指定派送管理',
                    isTab:true
                }
            },
            {
                path:'sendManage',
                name:'sendManage',
                component: sendManage ,
                meta:{
                    title:'派送管理',
                    isTab:true
                }
            }


        ]

    },{
    path: '/foreHome',
        name: 'foreHome',
        component: foreHome,
        redirect: '/foreHomeView',
        children: [
            {
                path: '/foreHomeView',
                name: 'foreHomeView',
                component: foreHomeView
            },
            {
                path: '/UserOrder',
                name: 'UserOrder',
                component: UserOrder
            },
            {
                path: '/userParcelManage',
                name: 'userParcelManage',
                component: userParcelManage
            },
            {
                path: '/foreComplaint',
                name: 'foreComplaint',
                component: foreComplaint
            },{
                path: '/foreAgingQuery',
                name: 'foreAgingQuery',
                component: foreAgingQuery
            }
        ]
    }

]
const router = createRouter({
    history: createWebHistory("/"),
    routes: routes,
})
/**

 - 输出对象
 */
export default router;