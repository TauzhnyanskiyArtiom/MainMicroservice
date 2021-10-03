import Vue from "vue";
import VueResource from 'vue-resource'
import App from 'pages/App.vue'
import router from 'router/router'
import 'vuetify/dist/vuetify.min.css'
import Vuetify from 'vuetify';
Vue.use(Vuetify);

Vue.use(VueResource)

new Vue({
    el: '#app',
    vuetify: new Vuetify(),
    router,
    render: a => a(App)
})
