import Vue from 'vue'
import layout from "@/components/layout";
import Video from "video.js"
import hls from "videojs-contrib-hls"
import "video.js/dist/video-js.min.css"
import "@silvermine/videojs-quality-selector/dist/css/quality-selector.css"
import router from "@/router";
import store from "@/store";
import {BootstrapVue, IconsPlugin} from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import VConsole from "vconsole"
import '@/assets/global.css'


Vue.config.productionTip = false

require('@silvermine/videojs-quality-selector')(Video);
Vue.prototype.$video = Video


if (process.env.NODE_ENV === 'cordova') {
  new VConsole()
}

Vue.use(hls)
Vue.use(BootstrapVue)
Vue.use(IconsPlugin)


new Vue({
  router,
  store,
  render: h => h(layout),
}).$mount('#app')
