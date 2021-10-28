import Vue from 'vue'
import App from './App.vue'
import router from './router';
import store from './store';
import { library, config } from '@fortawesome/fontawesome-svg-core'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
import { fas } from '@fortawesome/free-solid-svg-icons'
import { faFacebook, faTwitter, faGoogle, faTelegram} from '@fortawesome/free-brands-svg-icons'
config.autoAddCss = false
library.add(fas, faFacebook, faTwitter, faGoogle, faTelegram)


Vue.component('font-awesome-icon', FontAwesomeIcon)

Vue.config.productionTip = false

new Vue({
  store,
  router,
  render: h => h(App),
}).$mount('#app')
