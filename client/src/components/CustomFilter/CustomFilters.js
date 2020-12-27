import Vue from 'vue'

let moment = require('moment');
Vue.filter('filterDate', (aa)=>{
    return moment(aa).format("DD-MM-YYYY HH:mm:ss")
});
