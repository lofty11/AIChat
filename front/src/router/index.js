import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '../layout'

export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },
  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },
  {
    path: '/user',
    component: () => import('@/views/user/index')
  },
  {
    path: '/appinfo',
    component: Layout,
    redirect: '/appinfo/plug',
    name: 'Appinfo',
    meta: {
      title: '应用信息管理',
      icon: 'nested'
    },
    children: [
      {
        path: 'plug',
        component: () => import('@/views/appInfoManagement/plugManagement/index'), // Parent router-view
        name: 'Plug',
        meta: { title: '插件管理' }
      },
      {
        path: 'appservice',
        component: () => import('@/views/appInfoManagement/appServiceAPI/index'),
        name: 'Appservice',
        meta: { title: '应用服务API' }
      }
    ]
  },
  {
    path: '/',
    component: Layout,
    redirect: '/aboutUs',
    children: [{
      path: 'aboutUs',
      name: 'AboutUs',
      component: () => import('@/views/aboutUs/index'),
      meta: { title: '关于我们', icon: 'user' }
    }
    ]
  },
  {
    path: 'external-link',
    component: Layout,
    children: [
      {
        path: 'https://github.com/lofty11/AIChat',
        meta: { title: 'GitHub', icon: 'link' }
      }
    ]
  },

  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
