import Vue from 'vue'
import App from './App.vue'
import router from './router';
import store from './store';
import { library, config } from '@fortawesome/fontawesome-svg-core'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
import { fas } from '@fortawesome/free-solid-svg-icons'
import { faFacebook, faTwitter, faGoogle, faTelegram } from '@fortawesome/free-brands-svg-icons'
import Toast from "vue-toastification";
import "vue-toastification/dist/index.css";
config.autoAddCss = false
library.add(fas, faFacebook, faTwitter, faGoogle, faTelegram)

import VueLogger from 'vuejs-logger';
const isProduction = process.env.NODE_ENV === 'production';

const options = {
  isEnabled: true,
  logLevel : isProduction ? 'error' : 'debug',
  stringifyArguments : false,
  showLogLevel : true,
  showMethodName : true,
  separator: '|',
  showConsoleColors: true
};

Vue.use(Toast, {
  transition: "Vue-Toastification__bounce",
  maxToasts: 20,
  newestOnTop: true
});

Vue.use(VueLogger, options);

Vue.component('font-awesome-icon', FontAwesomeIcon)

Vue.config.productionTip = false

new Vue({
  store,
  router,
  render: h => h(App),
}).$mount('#app')