import Vue from 'vue'
import Vuetify from 'vuetify'
import 'api/resource'
import App from 'pages/App.vue'
import router from 'router/router'
import 'vuetify/dist/vuetify.min.css'
import {connect} from './util/ws'

if (frontendData.profile) {
    connect()
}

Vue.use(Vuetify)

new Vue({
    el: '#app',
    vuetify: new Vuetify(),
    router,
    render: a => a(App)
})
