import Vue from 'vue';
import App from './App.vue';
import Vuelidate from 'vuelidate'
import VueResource from 'vue-resource';
import Vuetify from 'vuetify';
import './plugins/vuetify';
import 'vuetify/dist/vuetify.min.css';
import VueFilterDateFormat from 'vue-filter-date-format';
import moment from 'moment'
import filters from './components/CustomFilter/CustomFilters'

Vue.use(VueResource,Vuetify,VueFilterDateFormat,moment,Vuelidate,filters);
Vue.config.productionTip = false;
export const EventBus = new Vue();

new Vue({

  render: h => h(App),
}).$mount('#app');
