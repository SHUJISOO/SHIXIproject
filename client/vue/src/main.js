import Vue from 'vue'
import App from './App.vue'
// import router from './router';
import router from "@/router";
import './assets/gloable.css'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
// import store from './store'
import request from "@/utils/request";
// main.js全局注册
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
import 'swiper/dist/css/swiper.min.css'
import 'swiper/dist/js/swiper.min.js'

// use
Vue.use(mavonEditor)
Vue.config.productionTip = false
Vue.use(ElementUI, { size: "mini" });
Vue.prototype.request=request

new Vue({
  router,
  // store,
  render: h => h(App)
}).$mount('#app')
