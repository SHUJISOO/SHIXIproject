import Vue from 'vue'
import VueRouter from 'vue-router'
// import HomeView from '../views/HomeView.vue'
import Home from '../views/back/Home.vue'

Vue.use(VueRouter)

const routes = [
    {
      path: '/',
      component: () => import('@/views/BackPage.vue'),
      redirect: "/index",
      children: [
        { path: 'home', name: '首页', component: () => import('../views/back/Home.vue')},
        { path: 'user', name: '用户管理', component: () => import('../views/back/User.vue')},
        { path: 'company', name: '公司管理', component: () => import('../views/back/CompanyList.vue')},
        { path: 'manage', name: '首页管理', component: () => import('../views/back/Manage.vue')},     
        { path: 'file', name: '文件管理', component: () => import('../views/back/File.vue')},
        { path: 'contactList', name: '意见反馈', component: () => import('../views/back/ContactList.vue')},
        { path: 'positionList', name: '职位管理', component: () => import('../views/back/PositionList.vue')},
        { path: 'companyInfo', name: '公司信息', component: () => import('../views/back/CompanyInfo.vue')},
        { path: 'resumeList', name: '简历管理', component: () => import('../views/back/ResumeList.vue')},
        { path: 'info', name: '个人信息', component: () => import('../views/back/Info.vue')},
        { path: 'password', name: '修改密码', component: () => import('../views/back/Password.vue')},
      ]
    },
    {
      path: '/index',
      name: 'FrontPage',
      component: () => import('../views/FrontPage.vue'),
      redirect: "/index",
      children: [
        { path: '/', name: '首页', component: () => import('../views/front/Home.vue')},
        { path: 'delivery', name: '投递消息', component: () => import('../views/front/DeliveryInfo.vue')},
        { path: 'info', name: '个人中心', component: () => import('../views/front/Info.vue')},
        { path: 'job', name: '职位', component: () => import('../views/front/Job.vue')},
        { path: 'jobDetail', name: '职位详情', component: () => import('../views/front/JobDetail.vue')},
        { path: 'contact', name: '联系我们', component: () => import('../views/front/Contact.vue')},
        { path: 'community', name: '社区论坛', component: () => import('../views/front/Community.vue')},
        { path: 'communityDetail', name: '社区详情', component: () => import('../views/front/CommunityDetail.vue')},
        // { path: 'password', name: '修改密码', component: () => import('../views/back/Password.vue')},
      ]
    },
    {
      path: '/login',
      name: 'Login',
      component: () => import('../views/Login.vue')
    },
    {
      path: '/register',
      name: 'Register',
      component: () => import('../views/Register.vue')
    },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
