import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

function getAbsolutePath () {
  let path = location.pathname
  let abPath = path.substring(0, path.lastIndexOf('/') + 1)
  return abPath
}

export default new Router({
  mode: 'history',
  base: getAbsolutePath(),
  routes: [
    {
      path: '/',
      redirect: '/main',
    },
    {
      path: '/login',
      name: 'login',
      component: resolve => require(['../components/page/login.vue'], resolve),
      meta: { title: '登录' }
    },
    {
      path: '/main',
      name: 'main',
      component: resolve => require(['../components/page/main/main.vue'], resolve),
      redirect: '/dashboard',
      children: [
        {
          path: '/dashboard',
          name: 'dashboard',
          component: resolve => require(['../components/page/dashboard/dashboard.vue'], resolve),
          meta: { title: '系统首页', noclose: true },
        },
        // 系统配置
        {
          path: '/menu',
          name: 'menu',
          component: resolve => require(['../components/page/sys/menu/index.vue'], resolve),
          meta: { title: '菜单管理' },
        },
        {
          path: '/dict',
          name: 'dict',
          component: resolve => require(['../components/page/sys/sysDict/list.vue'], resolve),
          meta: { title: '字典管理' },
        },
        {
          path: '/sysParam',
          name: 'sysParam',
          component: resolve => require(['../components/page/sys/sysParam/list.vue'], resolve),
          meta: { title: '系统参数' },
        },
        {
          path: '/sysLog',
          name: 'sysLog',
          component: resolve => require(['../components/page/sys/sysLog/list.vue'], resolve),
          meta: { title: '日志查看' },
        },
        // 组织机构
        {
          path: '/sysOffice',
          name: 'sysOffice',
          component: resolve => require(['../components/page/sys/sysOffice/list.vue'], resolve),
          meta: { title: '组织机构' },
        },
        {
          path: '/sysUser',
          name: 'sysUser',
          component: resolve => require(['../components/page/sys/sysUser/list.vue'], resolve),
          meta: { title: '人员管理' },
        },
        {
          path: '/personInfo',
          name: 'personInfo',
          component: resolve => require(['../components/page/sys/sysUser/personInfo.vue'], resolve),
          meta: { title: '个人信息' },
        },
        {
          path: '/sysRole',
          name: 'sysRole',
          component: resolve => require(['../components/page/sys/sysRole/list.vue'], resolve),
          meta: { title: '角色管理' },
        },
        {
          path: '/swagger',
          name: 'swagger',
          component: resolve => require(['../components/page/sys/frametag/swaggerlog.vue'], resolve),
          meta:
            {
              title: '接口API',
              src: 'http://localhost:8081/swagger-ui.html' //window._BASE_URL + '/swagger-ui.html'
            }
        },
        {
          path: '/druidlog',
          name: 'druidlog',
          component: resolve => require(['../components/page/sys/frametag/druidlog.vue'], resolve),
          meta:
            { title: '数据库日志' ,
              src: 'http://localhost:8081/druid/index.html' //window._BASE_URL+'/druid/index.html'
            },
        },
        {
          path: '/fileManager',
          name: 'fileManager',
          component: resolve => require(['../components/page/sys/sysFileManager/index.vue'], resolve),
          meta:
            { title: '文件管理'},
        }
      ]
    }
  ]
})
